package com.samsung.android.authenticator;

final class DeviceRootKeyTrustedApplication implements TrustedApplication {
    private static final String TAG = "DRTA";
    private final int mHandle;

    DeviceRootKeyTrustedApplication(int handle) {
        this.mHandle = handle;
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
    public byte[] execute(byte[] command) {
        return AuthenticatorService.getDrkKeyHandle();
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        if (!AuthenticatorService.terminateDrk()) {
            AuthenticatorLog.e(TAG, "td failed");
            return -1;
        }
        return 0;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}
