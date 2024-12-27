package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;

public final class SecFaceMsg {
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
