package java;

public class BaseTest {
    protected static final String user          = "ABCDEFGHIJKLMNOPRSTUVWYZ";
    protected static final String pass          = "asdfgdhfgfdsfdghjkgsa";
    static final String urlLogin      = "/user/login";
    static final String username      = "username";
    static final String password      = "password";
    static final String usernameParam = "sa";
    static final String passwordParam = "1234";
    static final int STATUS_CODE_SUCCESS = 200;

    protected String fillUrl(String url) {
        return "{API_ADDRESS}" + url;
    }
}
