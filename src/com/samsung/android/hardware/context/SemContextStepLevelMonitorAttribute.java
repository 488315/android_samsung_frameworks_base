package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes6.dex */
public class SemContextStepLevelMonitorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextStepLevelMonitorAttribute> CREATOR = new Parcelable.Creator<SemContextStepLevelMonitorAttribute>() { // from class: com.samsung.android.hardware.context.SemContextStepLevelMonitorAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute createFromParcel(Parcel parcel) {
            return new SemContextStepLevelMonitorAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute[] newArray(int i) {
            return new SemContextStepLevelMonitorAttribute[i];
        }
    };
    private static final String TAG = "SemContextStepLevelMonitorAttribute";
    private int mDuration;

    SemContextStepLevelMonitorAttribute() {
        this.mDuration = 300;
        setAttribute();
    }

    SemContextStepLevelMonitorAttribute(Parcel parcel) {
        super(parcel);
        this.mDuration = 300;
    }

    public SemContextStepLevelMonitorAttribute(int i) {
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
        super.setAttribute(33, bundle);
    }
}
