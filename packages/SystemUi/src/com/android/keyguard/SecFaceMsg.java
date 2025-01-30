package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
