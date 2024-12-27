package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

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
        if (!(obj instanceof FingerPrintData)) {
            return false;
        }
        FingerPrintData fingerPrintData = (FingerPrintData) obj;
        Integer num = this.mFingerPrintHeight;
        if (!(num == null && fingerPrintData.mFingerPrintHeight == null) && (num == null || !num.equals(fingerPrintData.mFingerPrintHeight))) {
            return false;
        }
        Integer num2 = this.mFingerPrintImageSize;
        if (!(num2 == null && fingerPrintData.mFingerPrintImageSize == null) && (num2 == null || !num2.equals(fingerPrintData.mFingerPrintImageSize))) {
            return false;
        }
        Integer num3 = this.mFingerPrintMarginBottom;
        return (num3 == null && fingerPrintData.mFingerPrintMarginBottom == null) || (num3 != null && num3.equals(fingerPrintData.mFingerPrintMarginBottom));
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
    public FingerPrintData m2018clone() throws CloneNotSupportedException {
        return (FingerPrintData) super.clone();
    }
}
