package com.samsung.android.wifi.intelligence.ins.data;

/* loaded from: classes6.dex */
public abstract class BaseData {
    int cmLabel;
    String flushTimestamp;
    int label;
    String timestamp;

    public abstract String toCsvString();

    public String getTimestamp() {
        return this.timestamp;
    }

    public int getLabel() {
        return this.label;
    }

    public void setLabel(int i) {
        this.label = i;
    }

    public int getCmLabel() {
        return this.cmLabel;
    }

    public void setCmLabel(int i) {
        this.cmLabel = i;
    }

    public void setFlushTimestamp(String str) {
        this.flushTimestamp = str;
    }

    public String getFlushTimestamp() {
        return this.flushTimestamp;
    }
}
