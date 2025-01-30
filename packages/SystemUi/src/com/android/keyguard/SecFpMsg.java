package com.android.keyguard;

import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
