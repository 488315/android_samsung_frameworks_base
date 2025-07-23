package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TUIProperty implements Parcelable {
    public static final Parcelable.Creator<TUIProperty> CREATOR = new Parcelable.Creator<TUIProperty>() { // from class: com.samsung.android.knox.keystore.TUIProperty.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TUIProperty createFromParcel(Parcel parcel) {
            return new TUIProperty(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TUIProperty[] newArray(int i) {
            return new TUIProperty[i];
        }
    };
    public int loginExpirationPeriod;
    public int loginRetry;
    public byte[] pin;
    public byte[] secretImage;

    public /* synthetic */ TUIProperty(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.loginRetry = parcel.readInt();
        this.loginExpirationPeriod = parcel.readInt();
        this.pin = parcel.createByteArray();
        this.secretImage = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.loginRetry);
        parcel.writeInt(this.loginExpirationPeriod);
        parcel.writeByteArray(this.pin);
        parcel.writeByteArray(this.secretImage);
    }

    public TUIProperty() {
        this.loginRetry = 2;
        this.loginExpirationPeriod = 120;
        this.pin = null;
        this.secretImage = null;
    }

    private TUIProperty(Parcel parcel) {
        this.loginRetry = 2;
        this.loginExpirationPeriod = 120;
        this.pin = null;
        this.secretImage = null;
        readFromParcel(parcel);
    }
}
