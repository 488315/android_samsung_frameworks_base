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

    SystemDataTransferRequest(int associationId, int dataType) {
        this.mUserConsented = false;
        this.mAssociationId = associationId;
        this.mDataType = dataType;
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

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public void setUserConsented(boolean isUserConsented) {
        this.mUserConsented = isUserConsented;
    }

    SystemDataTransferRequest(Parcel in) {
        this.mUserConsented = false;
        this.mAssociationId = in.readInt();
        this.mDataType = in.readInt();
        this.mUserId = in.readInt();
        this.mUserConsented = in.readBoolean();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAssociationId);
        dest.writeInt(this.mDataType);
        dest.writeInt(this.mUserId);
        dest.writeBoolean(this.mUserConsented);
    }

    public int describeContents() {
        return 0;
    }
}
