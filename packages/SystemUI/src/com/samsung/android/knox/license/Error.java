package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class Error implements Parcelable {
    public static final Parcelable.Creator<Error> CREATOR = new Parcelable.Creator<Error>() { // from class: com.samsung.android.knox.license.Error.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Error createFromParcel(Parcel parcel) {
            return new Error(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Error[] newArray(int i) {
            return new Error[i];
        }
    };
    private int errorCode;
    private String errorDescription;
    private int httpResponseCode;

    public /* synthetic */ Error(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public int getHttpResponseCode() {
        return this.httpResponseCode;
    }

    public void readFromParcel(Parcel parcel) {
        this.httpResponseCode = parcel.readInt();
        this.errorCode = parcel.readInt();
        this.errorDescription = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
