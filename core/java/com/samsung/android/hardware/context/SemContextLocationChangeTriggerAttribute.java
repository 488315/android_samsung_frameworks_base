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
        public SemContextLocationChangeTriggerAttribute createFromParcel(Parcel in) {
            return new SemContextLocationChangeTriggerAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTriggerAttribute[] newArray(int size) {
            return new SemContextLocationChangeTriggerAttribute[size];
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

    SemContextLocationChangeTriggerAttribute(Parcel src) {
        super(src);
        this.mTriggerType = 1;
        this.mDuration = 10;
    }

    public SemContextLocationChangeTriggerAttribute(int type, int duration) {
        this.mTriggerType = 1;
        this.mDuration = 10;
        this.mTriggerType = type;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mTriggerType >= 1 && this.mTriggerType <= 3) {
            return this.mDuration >= 0;
        }
        Log.e(TAG, "The display status is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("trigger_type", this.mTriggerType);
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(54, attribute);
    }
}
