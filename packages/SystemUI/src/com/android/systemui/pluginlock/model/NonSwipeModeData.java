package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NonSwipeModeData {

    @SerializedName("mode")
    private Integer mNonSwipeMode = 0;

    @SerializedName("angle")
    private Integer mAngle = 45;

    public boolean equals(Object obj) {
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

    public Integer getAngle() {
        return this.mAngle;
    }

    public Integer getMode() {
        return this.mNonSwipeMode;
    }

    public void setAngle(Integer num) {
        this.mAngle = num;
    }

    public void setNonSwipeMode(Integer num) {
        this.mNonSwipeMode = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NonSwipeModeData m2022clone() throws CloneNotSupportedException {
        return (NonSwipeModeData) super.clone();
    }
}
