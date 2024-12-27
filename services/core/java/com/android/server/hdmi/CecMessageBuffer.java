package com.android.server.hdmi;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CecMessageBuffer {
    public List mBuffer;
    public HdmiControlService mHdmiControlService;

    public final boolean replaceMessageIfBuffered(int i, HdmiCecMessage hdmiCecMessage) {
        for (int i2 = 0; i2 < ((ArrayList) this.mBuffer).size(); i2++) {
            if (((HdmiCecMessage) ((ArrayList) this.mBuffer).get(i2)).mOpcode == i) {
                ((ArrayList) this.mBuffer).set(i2, hdmiCecMessage);
                return true;
            }
        }
        return false;
    }
}
