package com.samsung.android.authenticator;

import java.util.List;

/* loaded from: classes5.dex */
public class SemAuthenticatorDataManager {
    private static final String PERMISSION_REQUEST_AUTHNR_SERVICE =
            "com.samsung.android.permission.REQUEST_AUTHNR_SERVICE";

    public boolean writeFile(String path, byte[] data) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.writeFile(path, data);
    }

    public boolean deleteFile(String path) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.deleteFile(path);
    }

    public String readFile(String path) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.readFile(path);
    }

    public List<String> getFiles(String path, String filter) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.getFiles(path, filter);
    }
}
