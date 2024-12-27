package com.android.keyguard;

import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecFpMsg {
    public int arg;
    public CharSequence msgString;
    public FingerprintManager.AuthenticationResult result;
    public int sequence;
    public int type;

    public static SecFpMsg obtain(int i, int i2, int i3, CharSequence charSequence, FingerprintManager.AuthenticationResult authenticationResult) {
        SecFpMsg secFpMsg = new SecFpMsg();
        secFpMsg.type = i;
        secFpMsg.sequence = i2;
        secFpMsg.arg = i3;
        secFpMsg.msgString = charSequence;
        secFpMsg.result = authenticationResult;
        return secFpMsg;
    }
}
