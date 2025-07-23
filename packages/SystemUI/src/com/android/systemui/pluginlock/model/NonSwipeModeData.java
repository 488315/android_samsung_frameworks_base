package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NonSwipeModeData {

    @SerializedName("mode")
    private Integer mNonSwipeMode = 0;

    @SerializedName("angle")
    private Integer mAngle = 45;

    public boolean equals(Object obj) {
        if (obj instanceof NonSwipeModeData) {
            NonSwipeModeData nonSwipeModeData = (NonSwipeModeData) obj;
            Integer num = this.mNonSwipeMode;
            if ((num == null && nonSwipeModeData.mNonSwipeMode == null) || (num != null && num.equals(nonSwipeModeData.mNonSwipeMode))) {
                Integer num2 = this.mAngle;
                if (num2 == null && nonSwipeModeData.mAngle == null) {
                    return true;
                }
                if (num2 != null && num2.equals(nonSwipeModeData.mAngle)) {
                    return true;
                }
            }
        }
        return false;
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
    public NonSwipeModeData m2660clone() throws CloneNotSupportedException {
        return (NonSwipeModeData) super.clone();
    }
}
