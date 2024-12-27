package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class CaptureData {

    @SerializedName("type")
    private Integer mCaptureType = 0;

    public boolean equals(Object obj) {
        if (!(obj instanceof CaptureData)) {
            return false;
        }
        CaptureData captureData = (CaptureData) obj;
        Integer num = this.mCaptureType;
        return (num == null && captureData.mCaptureType == null) || (num != null && num.equals(captureData.mCaptureType));
    }

    public Integer getType() {
        return this.mCaptureType;
    }

    public void setCaptureType(Integer num) {
        this.mCaptureType = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CaptureData m2016clone() throws CloneNotSupportedException {
        return (CaptureData) super.clone();
    }
}
