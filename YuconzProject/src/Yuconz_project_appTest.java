import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bramreth on 2/14/17.
 */
class Yuconz_project_appTest {

    private Yuconz_project_app app;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        app = new Yuconz_project_app();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        Document temp = app.getDatabase().fetchPersonalDetails("hruser2");
        temp.setName("John");
        app.getDatabase().amendUserPersonalDetails(temp);
        app = null;

    }

    //stage 3

    @org.junit.jupiter.api.Test
    public void testValidLogin(){
        System.out.println("Valid login");
        boolean result = app.login("user1","pass1");
        assertTrue(result);
    }

    //add inlalid password valid login

    @org.junit.jupiter.api.Test
    public void testInValidLogin(){
        System.out.println("Inalid login");
        boolean result = app.login("notARealName","INVALIDPASSWORD");
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    public void noLogin(){
        System.out.println("no login given");
        boolean result = app.login("","");
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    public void testInValidLogout(){
        System.out.println("inalid logout");
        app.logout();
        assertFalse(app.getLoggedIn());
    }

    @org.junit.jupiter.api.Test
    public void testValidLogout(){
        System.out.println("Valid logout");
        boolean resultA = app.login("andy","example");
        app.logout();
        assertFalse(app.getLoggedIn());
    }

    //stage 4

    @org.junit.jupiter.api.Test
    public void validAuthorisationCheck(){
        System.out.println("valid authorisation check");
        app.login("user3","pass3");//has permission
        assertTrue(app.getAuthorisation().authorisationCheck(app.getCurrentUser(),"user3","readPersonalDetails"));
    }

    @org.junit.jupiter.api.Test
    public void invalidAuthorisationCheck(){
        System.out.println("invalid authorisation check");
        app.login("user1","pass1");//doesnt have permission
        assertFalse(app.getAuthorisation().authorisationCheck(app.getCurrentUser(),"user3", "ammendPersonalDetails"));

    }

    @org.junit.jupiter.api.Test
    public void allowedReadPersonalDetails(){
        System.out.println("valid read personal details");
        app.login("hruser2","password2");//has permission
        assertTrue(app.readPersonalDetails("hruser2"));
    }

    @org.junit.jupiter.api.Test
    public void disallowedReadPersonalDetails(){
        System.out.println("invalid read personal details");
        app.login("user1","pass1");//doesn't have permission
        assertFalse(app.readPersonalDetails("hruser2"));
    }

    @org.junit.jupiter.api.Test
    public void allowedCreatePersonalDetails(){
        System.out.println("valid create personal details");
        app.login("hruser1","password1");//is in hr
        assertTrue(app.createPersonalDetails("hruser1"));
    }
    //invalid case
    @org.junit.jupiter.api.Test
    public void disallowedCreatePersonalDetails(){
        System.out.println("invalid create personal details");
        app.login("user3","pass3");//not in hr
        assertFalse(app.createPersonalDetails("user3"));
    }
    //ammend valid details

    @org.junit.jupiter.api.Test
    public void allowedAmendPersonalDetails(){
        System.out.println("valid ammend personal details");
        app.login("hruser2","password2");//is in hr
        assertTrue(app.amendPersonalDetails("hruser2"));
    }
    //invalid ammend details
    @org.junit.jupiter.api.Test
    public void disallowedAmendPersonalDetails(){
        System.out.println("valid ammend personal details");
        app.login("user2","pass2");//isnt in hr
        assertFalse(app.amendPersonalDetails("hruser2"));
    }

    @org.junit.jupiter.api.Test
    public void validAmendPersonalDetails(){
        System.out.println("valid ammend personal details");
        app.login("hruser2","password2");//is in hr
        Document temp1Doc = app.getDatabase().fetchPersonalDetails("hruser2");
        Document temp2Doc = app.getDatabase().fetchPersonalDetails("hruser2");
        temp2Doc.setName("Enid");
        app.getDatabase().amendUserPersonalDetails(temp2Doc);
        Document temp3Doc = app.getDatabase().fetchPersonalDetails("hruser2");
        assertNotEquals(temp1Doc.getName(),temp3Doc.getName());
    }
}