package android.companion.datatransfer;

import android.os.Parcel;

/* loaded from: classes.dex */
public abstract class SystemDataTransferRequest {
    public static final int DATA_TYPE_PERMISSION_SYNC = 1;
    final int mAssociationId;
    final int mDataType;
    boolean mUserConsented;
    int mUserId;

    public abstract SystemDataTransferRequest copyWithNewId(int i);

    public int describeContents() {
        return 0;
    }

    SystemDataTransferRequest(int i, int i2) {
        this.mUserConsented = false;
        this.mAssociationId = i;
        this.mDataType = i2;
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean isUserConsented() {
        return this.mUserConsented;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public void setUserConsented(boolean z) {
        this.mUserConsented = z;
    }

    SystemDataTransferRequest(Parcel parcel) {
        this.mUserConsented = false;
        this.mAssociationId = parcel.readInt();
        this.mDataType = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mUserConsented = parcel.readBoolean();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAssociationId);
        parcel.writeInt(this.mDataType);
        parcel.writeInt(this.mUserId);
        parcel.writeBoolean(this.mUserConsented);
    }
}
