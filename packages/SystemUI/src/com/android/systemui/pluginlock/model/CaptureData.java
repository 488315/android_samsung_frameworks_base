package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

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
    public CaptureData m2670clone() throws CloneNotSupportedException {
        return (CaptureData) super.clone();
    }
}
