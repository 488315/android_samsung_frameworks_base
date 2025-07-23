package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextLocationChangeTriggerAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextLocationChangeTriggerAttribute> CREATOR = new Parcelable.Creator<SemContextLocationChangeTriggerAttribute>() { // from class: com.samsung.android.hardware.context.SemContextLocationChangeTriggerAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTriggerAttribute createFromParcel(Parcel parcel) {
            return new SemContextLocationChangeTriggerAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTriggerAttribute[] newArray(int i) {
            return new SemContextLocationChangeTriggerAttribute[i];
        }
    };
    private static final String TAG = "SemContextLocationChangeTriggerAttribute";
    private int mDuration;
    private int mTriggerType;

    SemContextLocationChangeTriggerAttribute() {
        this.mTriggerType = 1;
        this.mDuration = 10;
        setAttribute();
    }

    SemContextLocationChangeTriggerAttribute(Parcel parcel) {
        super(parcel);
        this.mTriggerType = 1;
        this.mDuration = 10;
    }

    public SemContextLocationChangeTriggerAttribute(int i, int i2) {
        this.mTriggerType = i;
        this.mDuration = i2;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mTriggerType;
        if (i >= 1 && i <= 3) {
            return this.mDuration >= 0;
        }
        Log.e(TAG, "The display status is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("trigger_type", this.mTriggerType);
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(54, bundle);
    }
}
