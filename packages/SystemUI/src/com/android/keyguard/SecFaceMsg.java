package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecFaceMsg {
    public int arg;
    public CharSequence msgString;
    public SemBioFaceManager.AuthenticationResult result;
    public int type;

    public static SecFaceMsg obtain(int i, int i2, CharSequence charSequence, SemBioFaceManager.AuthenticationResult authenticationResult) {
        SecFaceMsg secFaceMsg = new SecFaceMsg();
        secFaceMsg.type = i;
        secFaceMsg.arg = i2;
        secFaceMsg.msgString = charSequence;
        secFaceMsg.result = authenticationResult;
        return secFaceMsg;
    }
}
