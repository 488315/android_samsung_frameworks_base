package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptureData {

    @SerializedName("type")
    private Integer mCaptureType = 0;

    public final Object clone() {
        return (CaptureData) super.clone();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof CaptureData)) {
            return false;
        }
        CaptureData captureData = (CaptureData) obj;
        Integer num = this.mCaptureType;
        return (num == null && captureData.mCaptureType == null) || (num != null && num.equals(captureData.mCaptureType));
    }

    public final Integer getType() {
        return this.mCaptureType;
    }
}
