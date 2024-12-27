package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAttribute implements Parcelable {
    public static final Parcelable.Creator<SemContextAttribute> CREATOR = new Parcelable.Creator<SemContextAttribute>() { // from class: com.samsung.android.hardware.context.SemContextAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAttribute createFromParcel(Parcel in) {
            return new SemContextAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAttribute[] newArray(int size) {
            return new SemContextAttribute[size];
        }
    };
    private Bundle mAttribute = new Bundle();

    public SemContextAttribute() {
    }

    public SemContextAttribute(Parcel src) {
        readFromParcel(src);
    }

    static SemContextAttribute getDefaultAttribute(int service) {
        switch (service) {
            case 1:
                SemContextAttribute attribute = new SemContextApproachAttribute();
                return attribute;
            case 2:
                SemContextAttribute attribute2 = new SemContextPedometerAttribute();
                return attribute2;
            case 3:
                SemContextAttribute attribute3 = new SemContextStepCountAlertAttribute();
                return attribute3;
            case 6:
                SemContextAttribute attribute4 = new SemContextAutoRotationAttribute();
                return attribute4;
            case 12:
                SemContextAttribute attribute5 = new SemContextShakeMotionAttribute();
                return attribute5;
            case 24:
                SemContextAttribute attribute6 = new SemContextActivityLocationLoggingAttribute();
                return attribute6;
            case 27:
                SemContextAttribute attribute7 = new SemContextActivityNotificationAttribute();
                return attribute7;
            case 28:
                SemContextAttribute attribute8 = new SemContextSpecificPoseAlertAttribute();
                return attribute8;
            case 30:
                SemContextAttribute attribute9 = new SemContextActivityNotificationExAttribute();
                return attribute9;
            case 33:
                SemContextAttribute attribute10 = new SemContextStepLevelMonitorAttribute();
                return attribute10;
            case 35:
                SemContextAttribute attribute11 = new SemContextSedentaryTimerAttribute();
                return attribute11;
            case 36:
                SemContextAttribute attribute12 = new SemContextFlatMotionForTableModeAttribute();
                return attribute12;
            case 39:
                SemContextAttribute attribute13 = new SemContextAutoBrightnessAttribute();
                return attribute13;
            case 47:
                SemContextAttribute attribute14 = new SemContextLocationCoreAttribute();
                return attribute14;
            case 48:
                SemContextAttribute attribute15 = new SemContextInterruptedGyroAttribute();
                return attribute15;
            case 51:
                SemContextAttribute attribute16 = new SemContextCarryingDetectionAttribute();
                return attribute16;
            case 53:
                SemContextAttribute attribute17 = new SemContextActivityCalibrationAttribute();
                return attribute17;
            case 54:
                SemContextAttribute attribute18 = new SemContextLocationChangeTriggerAttribute();
                return attribute18;
            case 56:
                SemContextAttribute attribute19 = new SemContextSlocationArDistanceAttribute();
                return attribute19;
            default:
                SemContextAttribute attribute20 = new SemContextAttribute();
                return attribute20;
        }
    }

    public boolean checkAttribute() {
        return true;
    }

    public Bundle getAttribute(int service) {
        String key = Integer.toString(service);
        if (this.mAttribute.containsKey(key)) {
            return this.mAttribute.getBundle(key);
        }
        return null;
    }

    public void setAttribute(int service, Bundle attribute) {
        this.mAttribute.putBundle(Integer.toString(service), attribute);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mAttribute);
    }

    private void readFromParcel(Parcel src) {
        this.mAttribute = src.readBundle(getClass().getClassLoader());
    }
}
