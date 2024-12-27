package com.samsung.android.authenticator;

/* loaded from: classes5.dex */
final class AuthenticatorTrustedApplication implements TrustedApplication {
    private static final String APP_ID = "authnr";
    private static final String TAG = "ATA";
    private final int mHandle;

    AuthenticatorTrustedApplication(int handle) {
        this.mHandle = handle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        if (!AuthenticatorService.initializeWithPreloadedTa()) {
            AuthenticatorLog.e(TAG, "iwpt failed");
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] command) {
        return AuthenticatorService.processWithPreloadedTa(command, APP_ID);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        if (!AuthenticatorService.terminateWithPreloadedTa()) {
            AuthenticatorLog.e(TAG, "twpt failed");
            return -1;
        }
        return 0;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}
