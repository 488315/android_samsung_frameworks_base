package com.samsung.android.authenticator;

import android.content.res.AssetFileDescriptor;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes6.dex */
public class SemTrustedApplicationExecutor {
    private static final String PERMISSION_REQUEST_AUTHNR_SERVICE = "com.samsung.android.permission.REQUEST_AUTHNR_SERVICE";

    public enum TrustedAppAssetType {
        PASS_AUTHENTICATOR,
        PASS_ESE
    }

    public enum TrustedAppType {
        FINGERPRINT_TRUSTED_APP,
        DEVICE_ROOT_KEY_TRUSTED_APP,
        ASSET_DOWNLOADER_TRUSTED_APP
    }

    public int load(TrustedAppType trustedAppType) {
        com.samsung.android.authenticator.TrustedAppType trustedAppType2;
        AuthenticatorManager authenticatorManager = AuthenticatorManager.getInstance();
        if (trustedAppType == TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP) {
            trustedAppType2 = com.samsung.android.authenticator.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP;
        } else if (trustedAppType == TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP) {
            trustedAppType2 = com.samsung.android.authenticator.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP;
        } else {
            trustedAppType2 = com.samsung.android.authenticator.TrustedAppType.FINGERPRINT_TRUSTED_APP;
        }
        return authenticatorManager.load(trustedAppType2);
    }

    public int load(TrustedAppAssetType trustedAppAssetType, AssetFileDescriptor assetFileDescriptor) {
        com.samsung.android.authenticator.TrustedAppAssetType trustedAppAssetType2;
        AuthenticatorManager authenticatorManager = AuthenticatorManager.getInstance();
        if (trustedAppAssetType == TrustedAppAssetType.PASS_ESE) {
            trustedAppAssetType2 = com.samsung.android.authenticator.TrustedAppAssetType.PASS_ESE;
        } else {
            trustedAppAssetType2 = com.samsung.android.authenticator.TrustedAppAssetType.PASS_AUTHENTICATOR;
        }
        return authenticatorManager.load(trustedAppAssetType2, assetFileDescriptor);
    }

    public byte[] execute(int i, byte[] bArr) {
        return AuthenticatorManager.getInstance().execute(i, bArr);
    }

    public boolean unload(int i) {
        return AuthenticatorManager.getInstance().unload(i);
    }

    public int getCommandVersion() {
        return AuthenticatorManager.getInstance().getCommandVersion();
    }
}
