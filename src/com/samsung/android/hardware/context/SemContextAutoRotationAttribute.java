package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "13.0")
/* loaded from: classes6.dex */
public class SemContextAutoRotationAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextAutoRotationAttribute> CREATOR = new Parcelable.Creator<SemContextAutoRotationAttribute>() { // from class: com.samsung.android.hardware.context.SemContextAutoRotationAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoRotationAttribute createFromParcel(Parcel parcel) {
            return new SemContextAutoRotationAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoRotationAttribute[] newArray(int i) {
            return new SemContextAutoRotationAttribute[i];
        }
    };
    private static final String TAG = "SemContextAutoRotationAttribute";
    private int mDeviceType;

    SemContextAutoRotationAttribute() {
        this.mDeviceType = 0;
        setAttribute();
    }

    SemContextAutoRotationAttribute(Parcel parcel) {
        super(parcel);
        this.mDeviceType = 0;
    }

    public SemContextAutoRotationAttribute(int i) {
        this.mDeviceType = i;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i == 0 || i == 2 || i == 4) {
            return true;
        }
        Log.e(TAG, "The device type is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("device_type", this.mDeviceType);
        super.setAttribute(6, bundle);
    }
}
