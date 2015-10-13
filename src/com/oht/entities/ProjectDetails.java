package com.oht.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents detailed specification of the project
 */
public class ProjectDetails {

    public ProjectDetails() {
    }

    public ProjectDetails(JsonObject json) {
        this();

        this.projectId = json.get("project_id").getAsInt();
        this.type = json.get("project_type").getAsString();

        this.statusCode = json.get("project_status_code").getAsString();
        this.sourceLanguage = json.get("source_language").getAsString();
        this.targetLanguage = json.get("target_language").getAsString();

        if (json.has("resources")) {
            JsonObject resources = json.get("resources").getAsJsonObject();

            // sources
            if (resources.has("sources") && resources.get("sources").isJsonArray()) {
                this.sources = new ArrayList<String>();
                JsonArray array = resources.get("sources").getAsJsonArray();
                for (JsonElement e : array)
                    this.sources.add(e.getAsString());
            }

            // translations
            if (resources.has("translations") && resources.get("translations").isJsonArray()) {
                this.translations = new ArrayList<String>();
                JsonArray array = resources.get("translations").getAsJsonArray();
                for (JsonElement e : array)
                    this.translations.add(e.getAsString());
            }

            // proofs
            if (resources.has("proofs") && resources.get("proofs").isJsonArray()) {
                this.proofs = new ArrayList<String>();
                JsonArray array = resources.get("proofs").getAsJsonArray();
                for (JsonElement e : array)
                    this.proofs.add(e.getAsString());
            }

            // transcriptions
            if (resources.has("transcriptions") && resources.get("transcriptions").isJsonArray()) {
                this.transcriptions = new ArrayList<String>();
                JsonArray array = resources.get("transcriptions").getAsJsonArray();
                for (JsonElement e : array)
                    this.transcriptions.add(e.getAsString());
            }
        }

        if (json.has("wordcount"))
            this.wordCount = json.get("wordcount").getAsInt();

        if (json.has("length"))
            this.length = json.get("length").getAsInt();

        if (json.has("custom")) {
            JsonObject custom = json.get("custom").getAsJsonObject();

            this.custom0 = custom.get("api_custom_0").getAsString();
            this.custom1 = custom.get("api_custom_1").getAsString();
            this.custom2 = custom.get("api_custom_2").getAsString();
            this.custom3 = custom.get("api_custom_3").getAsString();
            this.custom4 = custom.get("api_custom_4").getAsString();
            this.custom5 = custom.get("api_custom_5").getAsString();
            this.custom6 = custom.get("api_custom_6").getAsString();
            this.custom7 = custom.get("api_custom_7").getAsString();
            this.custom8 = custom.get("api_custom_8").getAsString();
            this.custom9 = custom.get("api_custom_9").getAsString();
        }
    }

    /**
     * The unique id of the requested project
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Project type (Translation | Expert Translation | Proofreading | Transcription | Translation + Proofreading)
     */
    public String getType() {
        return type;
    }

    /**
     * Status code:
     *  * <b>pending</b> - project submitted to OHT, but professional worker (translator/proofreader) did not start working yet
     *  * <b>in_progress</b> - worker started working on this project
     *  * <b>submitted</b> - the worker uploaded the first target resource to the project.
     *  This does not mean that the project is completed.
     *  * <b>signed</b> - the worker declared (with his signature) that he finished working on this project and all
     *  resources have been uploaded.
     *  * <b>completed</b> - final state of the project, after which we cannot guarantee fixes or corrections.
     *  This state is automatically enforced after 4 days of inactivity on the project.
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Source Language
     */
    public String getSourceLanguage() {
        return sourceLanguage;
    }

    /**
     * Target language
     */
    public String getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * List of source resource UUIDs related to the requested project
     */
    public Collection<String> getSources() {
        return sources;
    }

    /**
     * List of translation resource UUIDs related to the requested project
     */
    public Collection<String> getTranslations() {
        return translations;
    }

    /**
     * List of proofreading resource UUIDs related to the requested project
     */
    public Collection<String> getProofs() {
        return proofs;
    }

    /**
     * List of transcription resource UUIDs related to the requested project
     */
    public Collection<String> getTranscriptions() {
        return transcriptions;
    }

    /**
     * Words count
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Length in seconds (transcription projects only)
     */
    public int getLength() {
        return length;
    }

    public String getCustom0() {
        return custom0;
    }

    public String getCustom1() {
        return custom1;
    }

    public String getCustom2() {
        return custom2;
    }

    public String getCustom3() {
        return custom3;
    }

    public String getCustom4() {
        return custom4;
    }

    public String getCustom5() {
        return custom5;
    }

    public String getCustom6() {
        return custom6;
    }

    public String getCustom7() {
        return custom7;
    }

    public String getCustom8() {
        return custom8;
    }

    public String getCustom9() {
        return custom9;
    }

    // <editor-fold desc="private members">
    private int projectId;
    private String type;
    private String statusCode;
    private String sourceLanguage;
    private String targetLanguage;
    private Collection<String> sources;
    private Collection<String> translations;
    private Collection<String> proofs;
    private Collection<String> transcriptions;
    private int wordCount;
    private int length;
    private String custom0;
    private String custom1;
    private String custom2;
    private String custom3;
    private String custom4;
    private String custom5;
    private String custom6;
    private String custom7;
    private String custom8;
    private String custom9;

    //</editor-fold>
}
