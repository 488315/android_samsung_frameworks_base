package com.samsung.android.ims;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemImsRegistrationError implements Parcelable {
    public static final Parcelable.Creator<SemImsRegistrationError> CREATOR = new Parcelable.Creator<SemImsRegistrationError>() { // from class: com.samsung.android.ims.SemImsRegistrationError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistrationError createFromParcel(Parcel parcel) {
            return new SemImsRegistrationError(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistrationError[] newArray(int i) {
            return new SemImsRegistrationError[i];
        }
    };
    private int mDeregistrationReason;
    private int mDetailedDeregiReason;
    private int mSipErrorCode;
    private String mSipErrorReason;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemImsRegistrationError() {
        this.mSipErrorCode = 0;
        this.mSipErrorReason = "";
        this.mDetailedDeregiReason = 0;
        this.mDeregistrationReason = 0;
    }

    public SemImsRegistrationError(int i) {
        this.mSipErrorCode = 0;
    }

    public SemImsRegistrationError(int i, String str, int i2, int i3) {
        this.mSipErrorCode = i;
        this.mSipErrorReason = str;
        this.mDetailedDeregiReason = i2;
        this.mDeregistrationReason = i3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSipErrorCode);
        parcel.writeString(this.mSipErrorReason);
        parcel.writeInt(this.mDetailedDeregiReason);
        parcel.writeInt(this.mDeregistrationReason);
    }

    public int getSipErrorCode() {
        return this.mSipErrorCode;
    }

    public String getSipErrorReason() {
        return this.mSipErrorReason;
    }

    public int getDetailedDeregiReason() {
        return this.mDetailedDeregiReason;
    }

    public int getDeregistrationReason() {
        return this.mDeregistrationReason;
    }

    private SemImsRegistrationError(Parcel parcel) {
        this.mSipErrorCode = parcel.readInt();
        this.mSipErrorReason = parcel.readString();
        this.mDetailedDeregiReason = parcel.readInt();
        this.mDeregistrationReason = parcel.readInt();
    }
}
