package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextShakeMotionAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextShakeMotionAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextShakeMotionAttribute>() { // from class:
                                                        // com.samsung.android.hardware.context.SemContextShakeMotionAttribute.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextShakeMotionAttribute createFromParcel(Parcel in) {
                    return new SemContextShakeMotionAttribute(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextShakeMotionAttribute[] newArray(int size) {
                    return new SemContextShakeMotionAttribute[size];
                }
            };
    private static final String TAG = "SemContextShakeMotionAttribute";
    private int mDuration;
    private int mStrength;

    SemContextShakeMotionAttribute() {
        this.mStrength = 2;
        this.mDuration = 800;
        setAttribute();
    }

    SemContextShakeMotionAttribute(Parcel src) {
        super(src);
        this.mStrength = 2;
        this.mDuration = 800;
    }

    public SemContextShakeMotionAttribute(int strength, int duration) {
        this.mStrength = 2;
        this.mDuration = 800;
        this.mStrength = strength;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStrength < 0) {
            Log.e(TAG, "The strength is wrong.");
            return false;
        }
        if (this.mDuration < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("strength", this.mStrength);
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(12, attribute);
    }
}
