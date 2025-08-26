package com.samsung.android.knox.cmfa;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public enum AuthFactorType implements Parcelable {
    DEVICE_INTEGRITY,
    FACE_DETECTION,
    TOUCH_DETECTION,
    WATCH_ON,
    LAPTOP_PROXIMITY,
    ON_BODY_DETECTION,
    TRUSTED_LOCATION,
    TRUSTED_DEVICE,
    TRUSTED_SERVICE,
    PASSIVE_AUTH,
    PROCESS_ACTIVITY,
    LOCK_DETECTION,
    CRITICAL_EVENT_DETECTION;

    public static final Parcelable.Creator<AuthFactorType> CREATOR = new Parcelable.Creator<AuthFactorType>() { // from class: com.samsung.android.knox.cmfa.AuthFactorType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthFactorType createFromParcel(Parcel parcel) {
            return AuthFactorType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthFactorType[] newArray(int i) {
            return new AuthFactorType[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
