package com.samsung.android.authenticator;

import java.util.Arrays;

/* loaded from: classes6.dex */
final class FingerprintTrustedApplication implements TrustedApplication {
    private static final String TAG = "FTA";
    private final int mHandle;
    static final byte[] SET_AUTH_CHALLENGE_COMMAND = {1};
    static final byte[] GET_AUTH_RESULT_COMMAND = {2};
    private static final byte[] SUCCESS = {0};

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        return 0;
    }

    FingerprintTrustedApplication(int i) {
        this.mHandle = i;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] bArr) {
        if (bArr == null || bArr.length != 1) {
            AuthenticatorLog.e(TAG, "command is invalid");
            return new byte[0];
        }
        byte[] bArr2 = new byte[0];
        if (Arrays.equals(SET_AUTH_CHALLENGE_COMMAND, bArr)) {
            if (AuthenticatorService.setChallenge(bArr)) {
                bArr2 = SUCCESS;
            }
        } else if (Arrays.equals(GET_AUTH_RESULT_COMMAND, bArr)) {
            bArr2 = AuthenticatorService.getWrappedObject(bArr);
        }
        AuthenticatorLog.e(TAG, "command is not supported");
        return bArr2;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}
