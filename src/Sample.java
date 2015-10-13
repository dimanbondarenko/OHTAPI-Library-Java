import com.oht.OHTAPI;
import com.oht.OHTException;
import com.oht.entities.AccountDetails;
import com.oht.entities.ProjectDetails;

public class Sample {

    public static void main(String[] args) {
        OHTAPI api = new OHTAPI("<secret key here>", "<public key here>", true);

        try {
            // get account details
            AccountDetails acc = api.getAccountDetails();
            System.out.printf("Account id=%d, name=%s\n", acc.getAccountId(), acc.getAccountUsername());

            // get project details
            ProjectDetails project = api.getProjectDetails(34);
            System.out.printf("Project status is: %s", project.getStatusCode());

        } catch (OHTException ex) {
            ex.printStackTrace();
        }
    }
}
