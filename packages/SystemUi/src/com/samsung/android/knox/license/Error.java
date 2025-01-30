package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Error implements Parcelable {
    public static final Parcelable.Creator<Error> CREATOR = new Parcelable.Creator<Error>() { // from class: com.samsung.android.knox.license.Error.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Error[] newArray(int i) {
            return new Error[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Error createFromParcel(Parcel parcel) {
            return new Error(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Error[] newArray(int i) {
            return new Error[i];
        }
    };
    public int errorCode;
    public String errorDescription;
    public int httpResponseCode;

    public /* synthetic */ Error(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getErrorCode() {
        return this.errorCode;
    }

    public final String getErrorDescription() {
        return this.errorDescription;
    }

    public final int getHttpResponseCode() {
        return this.httpResponseCode;
    }

    public final void readFromParcel(Parcel parcel) {
        this.httpResponseCode = parcel.readInt();
        this.errorCode = parcel.readInt();
        this.errorDescription = parcel.readString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.httpResponseCode);
        parcel.writeInt(this.errorCode);
        parcel.writeString(this.errorDescription);
    }

    public Error(int i, int i2, String str) {
        this.httpResponseCode = i;
        this.errorCode = i2;
        this.errorDescription = str;
    }

    private Error(Parcel parcel) {
        readFromParcel(parcel);
    }

    public Error() {
    }
}
