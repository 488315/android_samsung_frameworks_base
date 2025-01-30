package com.samsung.android.ims;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemImsRegistrationError implements Parcelable {
    public static final Parcelable.Creator<SemImsRegistrationError> CREATOR = new Parcelable.Creator<SemImsRegistrationError>() { // from class: com.samsung.android.ims.SemImsRegistrationError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistrationError createFromParcel(Parcel in) {
            return new SemImsRegistrationError(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistrationError[] newArray(int size) {
            return new SemImsRegistrationError[size];
        }
    };
    private int mDeregistrationReason;
    private int mDetailedDeregiReason;
    private int mSipErrorCode;
    private String mSipErrorReason;

    public SemImsRegistrationError() {
        this.mSipErrorCode = 0;
        this.mSipErrorReason = "";
        this.mDetailedDeregiReason = 0;
        this.mDeregistrationReason = 0;
    }

    public SemImsRegistrationError(int sipErrorCode) {
        this.mSipErrorCode = 0;
    }

    public SemImsRegistrationError(int sipErrorCode, String sipErrorReason, int detailedDeregiReason, int deregistrationReason) {
        this.mSipErrorCode = sipErrorCode;
        this.mSipErrorReason = sipErrorReason;
        this.mDetailedDeregiReason = detailedDeregiReason;
        this.mDeregistrationReason = deregistrationReason;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mSipErrorCode);
        out.writeString(this.mSipErrorReason);
        out.writeInt(this.mDetailedDeregiReason);
        out.writeInt(this.mDeregistrationReason);
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

    private SemImsRegistrationError(Parcel in) {
        this.mSipErrorCode = in.readInt();
        this.mSipErrorReason = in.readString();
        this.mDetailedDeregiReason = in.readInt();
        this.mDeregistrationReason = in.readInt();
    }
}
