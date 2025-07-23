package com.android.keyguard;

import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecFpMsg {
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
