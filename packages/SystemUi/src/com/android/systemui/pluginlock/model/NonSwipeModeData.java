package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NonSwipeModeData {

    @SerializedName("mode")
    private Integer mNonSwipeMode = 0;

    @SerializedName("angle")
    private Integer mAngle = 45;

    public final Object clone() {
        return (NonSwipeModeData) super.clone();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof NonSwipeModeData)) {
            return false;
        }
        NonSwipeModeData nonSwipeModeData = (NonSwipeModeData) obj;
        Integer num = this.mNonSwipeMode;
        if (!(num == null && nonSwipeModeData.mNonSwipeMode == null) && (num == null || !num.equals(nonSwipeModeData.mNonSwipeMode))) {
            return false;
        }
        Integer num2 = this.mAngle;
        return (num2 == null && nonSwipeModeData.mAngle == null) || (num2 != null && num2.equals(nonSwipeModeData.mAngle));
    }

    public final Integer getAngle() {
        return this.mAngle;
    }

    public final Integer getMode() {
        return this.mNonSwipeMode;
    }
}
