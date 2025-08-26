package com.samsung.android.knox.zt.config;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public enum TrustActionType implements Parcelable {
    PHONE_LOCK,
    PHONE_UNLOCK,
    CONTAINER_LOCK,
    CONTAINER_UNLOCK;

    public static final Parcelable.Creator<TrustActionType> CREATOR = new Parcelable.Creator<TrustActionType>() { // from class: com.samsung.android.knox.zt.config.TrustActionType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustActionType createFromParcel(Parcel parcel) {
            return TrustActionType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustActionType[] newArray(int i) {
            return new TrustActionType[i];
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
