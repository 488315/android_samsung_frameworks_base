package com.android.internal.telephony.gsm;

import java.util.Objects;

/* loaded from: classes4.dex */
public final class SmsBroadcastConfigInfo {
    private int mFromCodeScheme;
    private int mFromServiceId;
    private boolean mSelected;
    private int mToCodeScheme;
    private int mToServiceId;

    public SmsBroadcastConfigInfo(int i, int i2, int i3, int i4, boolean z) {
        this.mFromServiceId = i;
        this.mToServiceId = i2;
        this.mFromCodeScheme = i3;
        this.mToCodeScheme = i4;
        this.mSelected = z;
    }

    public void setFromServiceId(int i) {
        this.mFromServiceId = i;
    }

    public int getFromServiceId() {
        return this.mFromServiceId;
    }

    public void setToServiceId(int i) {
        this.mToServiceId = i;
    }

    public int getToServiceId() {
        return this.mToServiceId;
    }

    public void setFromCodeScheme(int i) {
        this.mFromCodeScheme = i;
    }

    public int getFromCodeScheme() {
        return this.mFromCodeScheme;
    }

    public void setToCodeScheme(int i) {
        this.mToCodeScheme = i;
    }

    public int getToCodeScheme() {
        return this.mToCodeScheme;
    }

    public void setSelected(boolean z) {
        this.mSelected = z;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SmsBroadcastConfigInfo: Id [");
        sb.append(this.mFromServiceId);
        sb.append(',');
        sb.append(this.mToServiceId);
        sb.append("] Code [");
        sb.append(this.mFromCodeScheme);
        sb.append(',');
        sb.append(this.mToCodeScheme);
        sb.append("] ");
        sb.append(this.mSelected ? "ENABLED" : "DISABLED");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mFromServiceId), Integer.valueOf(this.mToServiceId), Integer.valueOf(this.mFromCodeScheme), Integer.valueOf(this.mToCodeScheme), Boolean.valueOf(this.mSelected));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SmsBroadcastConfigInfo)) {
            return false;
        }
        SmsBroadcastConfigInfo smsBroadcastConfigInfo = (SmsBroadcastConfigInfo) obj;
        return this.mFromServiceId == smsBroadcastConfigInfo.mFromServiceId && this.mToServiceId == smsBroadcastConfigInfo.mToServiceId && this.mFromCodeScheme == smsBroadcastConfigInfo.mFromCodeScheme && this.mToCodeScheme == smsBroadcastConfigInfo.mToCodeScheme && this.mSelected == smsBroadcastConfigInfo.mSelected;
    }
}
