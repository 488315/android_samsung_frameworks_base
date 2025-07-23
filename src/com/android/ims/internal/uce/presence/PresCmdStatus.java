package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.StatusCode;

/* loaded from: classes5.dex */
public class PresCmdStatus implements Parcelable {
    public static final Parcelable.Creator<PresCmdStatus> CREATOR = new Parcelable.Creator<PresCmdStatus>() { // from class: com.android.ims.internal.uce.presence.PresCmdStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCmdStatus createFromParcel(Parcel parcel) {
            return new PresCmdStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCmdStatus[] newArray(int i) {
            return new PresCmdStatus[i];
        }
    };
    private PresCmdId mCmdId;
    private int mRequestId;
    private StatusCode mStatus;
    private int mUserData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PresCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
    }

    public int getUserData() {
        return this.mUserData;
    }

    public void setUserData(int i) {
        this.mUserData = i;
    }

    public StatusCode getStatus() {
        return this.mStatus;
    }

    public void setStatus(StatusCode statusCode) {
        this.mStatus = statusCode;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public PresCmdStatus() {
        this.mCmdId = new PresCmdId();
        this.mStatus = new StatusCode();
        this.mStatus = new StatusCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUserData);
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mCmdId, i);
        parcel.writeParcelable(this.mStatus, i);
    }

    private PresCmdStatus(Parcel parcel) {
        this.mCmdId = new PresCmdId();
        this.mStatus = new StatusCode();
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUserData = parcel.readInt();
        this.mRequestId = parcel.readInt();
        this.mCmdId = (PresCmdId) parcel.readParcelable(PresCmdId.class.getClassLoader(), PresCmdId.class);
        this.mStatus = (StatusCode) parcel.readParcelable(StatusCode.class.getClassLoader(), StatusCode.class);
    }
}
