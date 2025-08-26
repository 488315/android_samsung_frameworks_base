package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class FingerPrintData {

    @SerializedName("enabled")
    private Boolean mEnabled;

    @SerializedName("height")
    private Integer mFingerPrintHeight;

    @SerializedName("image_size")
    private Integer mFingerPrintImageSize;

    @SerializedName("margin_bottom")
    private Integer mFingerPrintMarginBottom;

    public boolean equals(Object obj) {
        Integer num;
        if (obj instanceof FingerPrintData) {
            FingerPrintData fingerPrintData = (FingerPrintData) obj;
            Integer num2 = this.mFingerPrintHeight;
            if (((num2 == null && fingerPrintData.mFingerPrintHeight == null) || (num2 != null && num2.equals(fingerPrintData.mFingerPrintHeight))) && (((num = this.mFingerPrintImageSize) == null && fingerPrintData.mFingerPrintImageSize == null) || (num != null && num.equals(fingerPrintData.mFingerPrintImageSize)))) {
                Integer num3 = this.mFingerPrintMarginBottom;
                if (num3 == null && fingerPrintData.mFingerPrintMarginBottom == null) {
                    return true;
                }
                if (num3 != null && num3.equals(fingerPrintData.mFingerPrintMarginBottom)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean getEnabled() {
        return this.mEnabled;
    }

    public Integer getHeight() {
        return this.mFingerPrintHeight;
    }

    public Integer getImageSize() {
        return this.mFingerPrintImageSize;
    }

    public Integer getPaddingBottom() {
        return this.mFingerPrintMarginBottom;
    }

    public void setEnabled(Boolean bool) {
        this.mEnabled = bool;
    }

    public void setHeight(Integer num) {
        this.mFingerPrintHeight = num;
    }

    public void setImageSize(Integer num) {
        this.mFingerPrintImageSize = num;
    }

    public void setPaddingBottom(Integer num) {
        this.mFingerPrintMarginBottom = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public FingerPrintData m2674clone() throws CloneNotSupportedException {
        return (FingerPrintData) super.clone();
    }
}
