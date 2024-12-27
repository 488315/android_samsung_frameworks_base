package com.samsung.android.authenticator;

import android.content.res.AssetFileDescriptor;

@Deprecated(forRemoval = true, since = "15.5")
public class SemTrustedApplicationExecutor {
    private static final String PERMISSION_REQUEST_AUTHNR_SERVICE =
            "com.samsung.android.permission.REQUEST_AUTHNR_SERVICE";

    public enum TrustedAppAssetType {
        PASS_AUTHENTICATOR,
        PASS_ESE
    }

    public enum TrustedAppType {
        FINGERPRINT_TRUSTED_APP,
        DEVICE_ROOT_KEY_TRUSTED_APP,
        ASSET_DOWNLOADER_TRUSTED_APP
    }

    public int load(TrustedAppType type) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.load(type);
    }

    public int load(TrustedAppAssetType type, AssetFileDescriptor file) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.load(type, file);
    }

    public byte[] execute(int taHandle, byte[] command) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.execute(taHandle, command);
    }

    public boolean unload(int taHandle) {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.unload(taHandle);
    }

    public int getCommandVersion() {
        AuthenticatorManager manager = AuthenticatorManager.getInstance();
        return manager.getCommandVersion();
    }
}
