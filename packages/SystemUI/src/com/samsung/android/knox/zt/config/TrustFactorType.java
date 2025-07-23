package com.samsung.android.knox.zt.config;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum TrustFactorType implements Parcelable {
    DEVICE_INTEGRITY,
    FACE_DETECTION,
    TOUCH_DETECTION,
    WATCH_ON,
    ON_BODY_DETECTION,
    TRUSTED_DEVICE,
    TRUSTED_SERVICE,
    PASSIVE_AUTH,
    PROCESS_ACTIVITY,
    CRITICAL_EVENT_DETECTION,
    LOCK_DETECTION,
    CERT_PROVISION,
    PHISHING_DETECTION,
    SYSTEM_SOFTWARE,
    FRAMEWORK_MONITORING,
    APP_MONITORING,
    SYSTEM_MONITORING,
    NETWORK_MONITORING,
    USER_MONITORING;

    public static final Parcelable.Creator<TrustFactorType> CREATOR = new Parcelable.Creator<TrustFactorType>() { // from class: com.samsung.android.knox.zt.config.TrustFactorType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustFactorType createFromParcel(Parcel parcel) {
            return TrustFactorType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustFactorType[] newArray(int i) {
            return new TrustFactorType[i];
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
