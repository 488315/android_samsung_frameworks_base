package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAttribute implements Parcelable {
    public static final Parcelable.Creator<SemContextAttribute> CREATOR = new Parcelable.Creator<SemContextAttribute>() { // from class: com.samsung.android.hardware.context.SemContextAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAttribute createFromParcel(Parcel parcel) {
            return new SemContextAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAttribute[] newArray(int i) {
            return new SemContextAttribute[i];
        }
    };
    private Bundle mAttribute = new Bundle();

    public boolean checkAttribute() {
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemContextAttribute() {
    }

    public SemContextAttribute(Parcel parcel) {
        readFromParcel(parcel);
    }

    static SemContextAttribute getDefaultAttribute(int i) {
        if (i == 1) {
            return new SemContextApproachAttribute();
        }
        if (i == 2) {
            return new SemContextPedometerAttribute();
        }
        if (i == 3) {
            return new SemContextStepCountAlertAttribute();
        }
        if (i == 6) {
            return new SemContextAutoRotationAttribute();
        }
        if (i == 12) {
            return new SemContextShakeMotionAttribute();
        }
        if (i == 24) {
            return new SemContextActivityLocationLoggingAttribute();
        }
        if (i == 30) {
            return new SemContextActivityNotificationExAttribute();
        }
        if (i == 33) {
            return new SemContextStepLevelMonitorAttribute();
        }
        if (i == 39) {
            return new SemContextAutoBrightnessAttribute();
        }
        if (i == 51) {
            return new SemContextCarryingDetectionAttribute();
        }
        if (i == 56) {
            return new SemContextSlocationArDistanceAttribute();
        }
        if (i == 27) {
            return new SemContextActivityNotificationAttribute();
        }
        if (i == 28) {
            return new SemContextSpecificPoseAlertAttribute();
        }
        if (i == 35) {
            return new SemContextSedentaryTimerAttribute();
        }
        if (i == 36) {
            return new SemContextFlatMotionForTableModeAttribute();
        }
        if (i == 47) {
            return new SemContextLocationCoreAttribute();
        }
        if (i == 48) {
            return new SemContextInterruptedGyroAttribute();
        }
        if (i == 53) {
            return new SemContextActivityCalibrationAttribute();
        }
        if (i == 54) {
            return new SemContextLocationChangeTriggerAttribute();
        }
        return new SemContextAttribute();
    }

    public Bundle getAttribute(int i) {
        String num = Integer.toString(i);
        if (this.mAttribute.containsKey(num)) {
            return this.mAttribute.getBundle(num);
        }
        return null;
    }

    public void setAttribute(int i, Bundle bundle) {
        this.mAttribute.putBundle(Integer.toString(i), bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mAttribute);
    }

    private void readFromParcel(Parcel parcel) {
        this.mAttribute = parcel.readBundle(getClass().getClassLoader());
    }
}
