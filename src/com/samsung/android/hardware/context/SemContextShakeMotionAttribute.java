package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextShakeMotionAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextShakeMotionAttribute> CREATOR = new Parcelable.Creator<SemContextShakeMotionAttribute>() { // from class: com.samsung.android.hardware.context.SemContextShakeMotionAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotionAttribute createFromParcel(Parcel parcel) {
            return new SemContextShakeMotionAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotionAttribute[] newArray(int i) {
            return new SemContextShakeMotionAttribute[i];
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

    SemContextShakeMotionAttribute(Parcel parcel) {
        super(parcel);
        this.mStrength = 2;
        this.mDuration = 800;
    }

    public SemContextShakeMotionAttribute(int i, int i2) {
        this.mStrength = i;
        this.mDuration = i2;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStrength < 0) {
            Log.e(TAG, "The strength is wrong.");
            return false;
        }
        if (this.mDuration >= 0) {
            return true;
        }
        Log.e(TAG, "The duration is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("strength", this.mStrength);
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(12, bundle);
    }
}
