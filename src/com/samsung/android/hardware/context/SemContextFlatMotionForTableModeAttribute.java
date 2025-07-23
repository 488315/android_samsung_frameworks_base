package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextFlatMotionForTableModeAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextFlatMotionForTableModeAttribute> CREATOR = new Parcelable.Creator<SemContextFlatMotionForTableModeAttribute>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotionForTableModeAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute createFromParcel(Parcel parcel) {
            return new SemContextFlatMotionForTableModeAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute[] newArray(int i) {
            return new SemContextFlatMotionForTableModeAttribute[i];
        }
    };
    private static final String TAG = "SemContextFlatMotionForTableModeAttribute";
    private int mDuration;

    SemContextFlatMotionForTableModeAttribute() {
        this.mDuration = 500;
        setAttribute();
    }

    SemContextFlatMotionForTableModeAttribute(Parcel parcel) {
        super(parcel);
        this.mDuration = 500;
    }

    public SemContextFlatMotionForTableModeAttribute(int i) {
        this.mDuration = i;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDuration >= 0) {
            return true;
        }
        Log.e(TAG, "The duration is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(36, bundle);
    }
}
