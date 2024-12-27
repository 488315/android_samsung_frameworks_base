package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
