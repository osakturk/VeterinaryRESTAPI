package controller.auth;

import java.util.Base64;

public class BaseController {
    protected byte[] getApiKeyBinary() {
        return Base64.getDecoder().decode("PEKYDbc6bpiNff7n+dt1pQ==");
    }
}
