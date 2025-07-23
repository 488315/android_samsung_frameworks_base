package com.samsung.android.authenticator;

import java.util.List;

/* loaded from: classes6.dex */
public class SemAuthenticatorDataManager {
    private static final String PERMISSION_REQUEST_AUTHNR_SERVICE = "com.samsung.android.permission.REQUEST_AUTHNR_SERVICE";

    public boolean writeFile(String str, byte[] bArr) {
        return AuthenticatorManager.getInstance().writeFile(str, bArr);
    }

    public boolean deleteFile(String str) {
        return AuthenticatorManager.getInstance().deleteFile(str);
    }

    public String readFile(String str) {
        return AuthenticatorManager.getInstance().readFile(str);
    }

    public List<String> getFiles(String str, String str2) {
        return AuthenticatorManager.getInstance().getFiles(str, str2);
    }
}
