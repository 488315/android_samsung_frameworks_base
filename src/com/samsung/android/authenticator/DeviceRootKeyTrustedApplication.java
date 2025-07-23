package com.samsung.android.authenticator;

/* loaded from: classes6.dex */
final class DeviceRootKeyTrustedApplication implements TrustedApplication {
    private static final String TAG = "DRTA";
    private final int mHandle;

    DeviceRootKeyTrustedApplication(int i) {
        this.mHandle = i;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        if (!AuthenticatorService.initializeDrk()) {
            AuthenticatorLog.e(TAG, "id failed");
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] bArr) {
        return AuthenticatorService.getDrkKeyHandle();
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        if (AuthenticatorService.terminateDrk()) {
            return 0;
        }
        AuthenticatorLog.e(TAG, "td failed");
        return -1;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}
