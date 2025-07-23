package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CaptureData {

    @SerializedName("type")
    private Integer mCaptureType = 0;

    public boolean equals(Object obj) {
        if (obj instanceof CaptureData) {
            CaptureData captureData = (CaptureData) obj;
            Integer num = this.mCaptureType;
            if (num == null && captureData.mCaptureType == null) {
                return true;
            }
            if (num != null && num.equals(captureData.mCaptureType)) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return this.mCaptureType;
    }

    public void setCaptureType(Integer num) {
        this.mCaptureType = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CaptureData m2654clone() throws CloneNotSupportedException {
        return (CaptureData) super.clone();
    }
}
