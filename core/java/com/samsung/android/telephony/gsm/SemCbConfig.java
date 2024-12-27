package com.samsung.android.telephony.gsm;

/* loaded from: classes6.dex */
public class SemCbConfig {
    public boolean bCBEnabled;
    public int msgIdCount;
    public int msgIdMaxCount;
    public int[] msgIds;
    public int selectedId;

    public String toString() {
        return super.toString()
                + "CB ENABLED: "
                + this.bCBEnabled
                + "selectedId"
                + this.selectedId
                + " msgIdMaxCount:"
                + this.msgIdMaxCount
                + "msgIdCount"
                + this.msgIdCount;
    }
}
