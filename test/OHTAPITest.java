import com.oht.OHTAPI;

import com.oht.OHTException;
import com.oht.entities.*;
import org.apache.commons.codec.binary.Base64;
import org.junit.*;
import org.junit.Test;

import java.io.*;
import java.util.Collection;

public class OHTAPITest {

    private static OHTAPI api = new OHTAPI("bdf7befc604c2c9c1b5a8396775c7eba", "n6h9dCJwRjGxbQTLBm8Y", true);
    private static OHTAPI api_wrong = new OHTAPI("bdf7befc604c2c9c1b5a8396775c7eb", "n6h9dCJwRjGxbQTLBm8", true);

    private static String resource1String = "The quick brown fox jumps over the lazy dog.";
    private static String resource2String = "<text>The five boxing wizards jump quickly.</text>";

    private static String custom0String = "This is custom 0 field";
    private static String custom1String = "This is custom 1 field";
    private static String custom2String = "This is custom 2 field";
    private static String custom3String = "This is custom 3 field";
    private static String custom4String = "This is custom 4 field";
    private static String custom5String = "This is custom 5 field";
    private static String custom6String = "This is custom 6 field";
    private static String custom7String = "This is custom 7 field";
    private static String custom8String = "This is custom 8 field";
    private static String custom9String = "This is custom 9 field";

    @Test()
    public void testWrongApiKeys() throws Exception {
        try {
            api_wrong.getAccountDetails();
            Assert.fail("expected an OHTException to be thrown");
        } catch (OHTException ex) {
            Assert.assertEquals(ex.getStatusCode(), 101);
        }
    }

    @Test
    public void testGetAccountDetails() throws Exception {
        AccountDetails accountDetails = api.getAccountDetails();
        Assert.assertNotNull(accountDetails);
    }

    @Test
    public void testResources() throws Exception {
        // write test data to file
        File tmpResource1 = File.createTempFile("res1", "tmp");
        tmpResource1.deleteOnExit();
        PrintWriter writer = new PrintWriter(tmpResource1);
        writer.write(resource1String);
        writer.close();

        // upload resource1 from filesystem
        Assert.assertNotNull(tmpResource1);
        Assert.assertNotNull(api);
        String resource1UUID = api.uploadFileResource("My test resource 1", null, tmpResource1.getAbsolutePath(), null);
        Assert.assertNotNull(resource1UUID);
        Assert.assertFalse(resource1UUID.isEmpty());

        // upload resource2 from string
        String resource2UUID = api.uploadFileResource("My test resource 2", "application/xml", null, resource2String);
        Assert.assertNotNull(resource2UUID);
        Assert.assertFalse(resource2UUID.isEmpty());

        // get resource1 as base64 encoded string
        Resource resourceDetails1 = api.getResource(resource1UUID, null, "base64");
        Assert.assertNotNull(resourceDetails1.getDownloadUrl());
        String resource1Contents = new String(Base64.decodeBase64(resourceDetails1.getContent()), "UTF-8");
        Assert.assertEquals(resource1String, resource1Contents);

        File tmpResource2 = File.createTempFile("res2", "tmp");
        tmpResource2.deleteOnExit();
        api.downloadResource(resource2UUID, null, tmpResource2.getAbsolutePath());

        // read contents from downloaded file
        BufferedReader reader = new BufferedReader(new FileReader(tmpResource2));
        StringBuilder fileData = new StringBuilder("");
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();

        // compare with original string
        Assert.assertEquals(resource2String, fileData.toString());
    }

    @Test
    public void testTools() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        String resource2UUID = api.uploadFileResource("My test resource 2", "application/xml", null, resource2String);

        String[] resources = new String[]{resource1UUID, resource2UUID};

        Quote quote = api.getQuote(resources, 1000, "en-us", "fr-fr", "transcription", null, null, "USD");
        Assert.assertNotNull(quote);
        Assert.assertTrue(quote.getTotalWordCount() > 0);

        WordCount wordCount = api.getWordCount(resources);
        Assert.assertNotNull(wordCount);
        Assert.assertTrue(wordCount.getTotalWordCount() > 0);
    }

    @Test
    public void testMachineTranslation() throws Exception {
        String machineTranslationResult = api.machineTranslation("en-us", "uk-ua", resource1String);
        Assert.assertNotNull(machineTranslationResult);
        Assert.assertFalse(machineTranslationResult.isEmpty());

        String detectLangResult = api.detectLanguage(resource1String);
        Assert.assertNotNull(detectLangResult);
        Assert.assertEquals("English", detectLangResult);
    }

    @Test
    public void testDiscovery() throws Exception {
        Collection<Language> supportedLanguages = api.getSupportedLanguages();
        Assert.assertNotNull(supportedLanguages);
        Assert.assertFalse(supportedLanguages.isEmpty());

        Collection<LanguagePair> supportedLanguagePairs = api.getSupportedLanguagePairs();
        Assert.assertNotNull(supportedLanguagePairs);
        Assert.assertFalse(supportedLanguagePairs.isEmpty());

        Collection<Expertise> supportedExpertises = api.getSupportedExpertises("en-us", "fr-fr");
        Assert.assertNotNull(supportedExpertises);
        Assert.assertFalse(supportedExpertises.isEmpty());
    }

    @Test
    public void testTranslationProject() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        String resource2UUID = api.uploadFileResource("My test resource 2", "application/xml", null, resource2String);
        Assert.assertNotNull(resource2UUID);

        String[] resources = new String[]{resource1UUID, resource2UUID};
        Project project = api.createTranslationProject
                ("en-us"
                , "fr-fr"
                , resources
                , null
                , "test note"
                , null
                , null
                , "my translation project"
                , custom0String, custom1String, custom2String, custom3String, custom4String, custom5String, custom6String, custom7String, custom8String, custom9String);

        Assert.assertNotNull(project);
        Assert.assertTrue(project.getProjectId() > 0);
        Assert.assertTrue(project.getWordCount() > 0);

        ProjectDetails projectDetails = api.getProjectDetails(project.getProjectId());
        Assert.assertNotNull(projectDetails);
        Assert.assertEquals("Translation", projectDetails.getType());

        // compare custom fields
        Assert.assertEquals(custom0String, projectDetails.getCustom0());
        Assert.assertEquals(custom1String, projectDetails.getCustom1());
        Assert.assertEquals(custom2String, projectDetails.getCustom2());
        Assert.assertEquals(custom3String, projectDetails.getCustom3());
        Assert.assertEquals(custom4String, projectDetails.getCustom4());
        Assert.assertEquals(custom5String, projectDetails.getCustom5());
        Assert.assertEquals(custom6String, projectDetails.getCustom6());
        Assert.assertEquals(custom7String, projectDetails.getCustom7());
        Assert.assertEquals(custom8String, projectDetails.getCustom8());
        Assert.assertEquals(custom9String, projectDetails.getCustom9());
    }

    @Test
    public void testProofreadingProject() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        Project project = api.createProofreadingProject
                ("en-us"
                , new String[]{resource1UUID}
                , null // word count
                , "proofreading project note"
                , null // expertise
                , null // callback url
                , "my proofreading project"
                , custom0String, custom1String, custom2String, custom3String, custom4String, custom5String, custom6String, custom7String, custom8String, custom9String);

        Assert.assertNotNull(project);
        Assert.assertTrue(project.getProjectId() > 0);
        Assert.assertTrue(project.getWordCount() > 0);

        ProjectDetails projectDetails = api.getProjectDetails(project.getProjectId());
        Assert.assertNotNull(projectDetails);
        Assert.assertEquals("Proofreading", projectDetails.getType());

        // compare custom fields
        Assert.assertEquals(custom0String, projectDetails.getCustom0());
        Assert.assertEquals(custom1String, projectDetails.getCustom1());
        Assert.assertEquals(custom2String, projectDetails.getCustom2());
        Assert.assertEquals(custom3String, projectDetails.getCustom3());
        Assert.assertEquals(custom4String, projectDetails.getCustom4());
        Assert.assertEquals(custom5String, projectDetails.getCustom5());
        Assert.assertEquals(custom6String, projectDetails.getCustom6());
        Assert.assertEquals(custom7String, projectDetails.getCustom7());
        Assert.assertEquals(custom8String, projectDetails.getCustom8());
        Assert.assertEquals(custom9String, projectDetails.getCustom9());
    }

    @Test
    public void testProofTranslatedProject() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        String resource2UUID = api.uploadFileResource("My test resource 2", "application/xml", null, resource2String);
        Assert.assertNotNull(resource2UUID);

        Project project = api.createProofTranslatedProject
                ("en-us"
                , "fr-fr"
                , new String[]{resource1UUID}
                , new String[]{resource2UUID}
                , null // word count
                , "proof translated project note"
                , null // callback url
                , "my proof translated project"
                , custom0String, custom1String, custom2String, custom3String, custom4String, custom5String, custom6String, custom7String, custom8String, custom9String);

        Assert.assertNotNull(project);
        Assert.assertTrue(project.getProjectId() > 0);
        Assert.assertTrue(project.getWordCount() > 0);

        ProjectDetails projectDetails = api.getProjectDetails(project.getProjectId());
        Assert.assertNotNull(projectDetails);
        Assert.assertEquals("Proofreading", projectDetails.getType());

        // compare custom fields
        Assert.assertEquals(custom0String, projectDetails.getCustom0());
        Assert.assertEquals(custom1String, projectDetails.getCustom1());
        Assert.assertEquals(custom2String, projectDetails.getCustom2());
        Assert.assertEquals(custom3String, projectDetails.getCustom3());
        Assert.assertEquals(custom4String, projectDetails.getCustom4());
        Assert.assertEquals(custom5String, projectDetails.getCustom5());
        Assert.assertEquals(custom6String, projectDetails.getCustom6());
        Assert.assertEquals(custom7String, projectDetails.getCustom7());
        Assert.assertEquals(custom8String, projectDetails.getCustom8());
        Assert.assertEquals(custom9String, projectDetails.getCustom9());
    }

    @Test
    public void testTranscriptionProject() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        Project project = api.createTranscriptionProject
                ("en-us"
                , new String[]{resource1UUID}
                , null // length
                , "transcription project note"
                , null // callback url
                , "my transcription  project"
                , custom0String, custom1String, custom2String, custom3String, custom4String, custom5String, custom6String, custom7String, custom8String, custom9String);

        Assert.assertNotNull(project);
        Assert.assertTrue(project.getProjectId() > 0);
        Assert.assertTrue(project.getWordCount() > 0);

        ProjectDetails projectDetails = api.getProjectDetails(project.getProjectId());
        Assert.assertNotNull(projectDetails);
        Assert.assertEquals("Transcription", projectDetails.getType());

        // compare custom fields
        Assert.assertEquals(custom0String, projectDetails.getCustom0());
        Assert.assertEquals(custom1String, projectDetails.getCustom1());
        Assert.assertEquals(custom2String, projectDetails.getCustom2());
        Assert.assertEquals(custom3String, projectDetails.getCustom3());
        Assert.assertEquals(custom4String, projectDetails.getCustom4());
        Assert.assertEquals(custom5String, projectDetails.getCustom5());
        Assert.assertEquals(custom6String, projectDetails.getCustom6());
        Assert.assertEquals(custom7String, projectDetails.getCustom7());
        Assert.assertEquals(custom8String, projectDetails.getCustom8());
        Assert.assertEquals(custom9String, projectDetails.getCustom9());
    }

    @Test
    public void testProjectComments() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        Project project = api.createTranscriptionProject
                ("en-us"
                , new String[]{resource1UUID}
                , null // length
                , "transcription project note"
                , null // callback url
                , "my transcription  project"
                , null, null, null, null, null, null, null, null, null, null);

        api.postProjectComment(project.getProjectId(), "this is my first project comment");
        api.postProjectComment(project.getProjectId(), "this is my second project comment");

        Collection<Comment> comments = api.getProjectComments(project.getProjectId());
        Assert.assertNotNull(comments);
        Assert.assertFalse(comments.isEmpty());
    }

    @Test
    public void testProjectRatings() throws Exception {
        String resource1UUID = api.uploadFileResource("My test resource 1", null, null, resource1String);
        Assert.assertNotNull(resource1UUID);

        Project project = api.createTranscriptionProject
                ("en-us"
                , new String[]{resource1UUID}
                , null // length
                , "transcription project note"
                , null // callback url
                , "my transcription  project"
                , null, null, null, null, null, null, null, null, null, null);

        try {
            api.postProjectRating(project.getProjectId(), "Service", 8, "my first service remark");
            Assert.fail("expected an OHTException to be thrown");
        } catch (OHTException ex) {
            Assert.assertEquals(ex.getStatusCode(), 102);
        }

        try {
            api.postProjectRating(project.getProjectId(), "Customer", 10, "my first customer's remark");
            Assert.fail("expected an OHTException to be thrown");
        } catch (OHTException ex) {
            Assert.assertEquals(ex.getStatusCode(), 102);
        }

        Collection<Rating> ratings = api.retrieveProjectRatings(project.getProjectId());
        Assert.assertNotNull(ratings);

        // TODO: uncomment when postProjectRating call will be success
        // Assert.assertFalse(ratings.isEmpty());
    }
}
