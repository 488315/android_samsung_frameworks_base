package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class PresCmdId implements Parcelable {
    public static final Parcelable.Creator<PresCmdId> CREATOR = new Parcelable.Creator<PresCmdId>() { // from class: com.android.ims.internal.uce.presence.PresCmdId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCmdId createFromParcel(Parcel parcel) {
            return new PresCmdId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCmdId[] newArray(int i) {
            return new PresCmdId[i];
        }
    };
    public static final int UCE_PRES_CMD_GETCONTACTCAP = 2;
    public static final int UCE_PRES_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_PRES_CMD_GET_VERSION = 0;
    public static final int UCE_PRES_CMD_PUBLISHMYCAP = 1;
    public static final int UCE_PRES_CMD_REENABLE_SERVICE = 5;
    public static final int UCE_PRES_CMD_SETNEWFEATURETAG = 4;
    public static final int UCE_PRES_CMD_UNKNOWN = 6;
    private int mCmdId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(int i) {
        this.mCmdId = i;
    }

    public PresCmdId() {
        this.mCmdId = 6;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCmdId);
    }

    private PresCmdId(Parcel parcel) {
        this.mCmdId = 6;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mCmdId = parcel.readInt();
    }
}
