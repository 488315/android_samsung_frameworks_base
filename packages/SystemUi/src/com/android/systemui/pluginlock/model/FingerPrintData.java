package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FingerPrintData {

    @SerializedName("enabled")
    private Boolean mEnabled;

    @SerializedName("height")
    private Integer mFingerPrintHeight;

    @SerializedName("image_size")
    private Integer mFingerPrintImageSize;

    @SerializedName("margin_bottom")
    private Integer mFingerPrintMarginBottom;

    public final Object clone() {
        return (FingerPrintData) super.clone();
    }

    public final boolean equals(Object obj) {
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

    public final void setEnabled(Boolean bool) {
        this.mEnabled = bool;
    }

    public final void setHeight(Integer num) {
        this.mFingerPrintHeight = num;
    }

    public final void setImageSize(Integer num) {
        this.mFingerPrintImageSize = num;
    }

    public final void setPaddingBottom(Integer num) {
        this.mFingerPrintMarginBottom = num;
    }
}
