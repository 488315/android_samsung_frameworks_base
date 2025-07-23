package com.android.ims.internal.uce.options;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class OptionsCmdId implements Parcelable {
    public static final Parcelable.Creator<OptionsCmdId> CREATOR = new Parcelable.Creator<OptionsCmdId>() { // from class: com.android.ims.internal.uce.options.OptionsCmdId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsCmdId createFromParcel(Parcel parcel) {
            return new OptionsCmdId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsCmdId[] newArray(int i) {
            return new OptionsCmdId[i];
        }
    };
    public static final int UCE_OPTIONS_CMD_GETCONTACTCAP = 2;
    public static final int UCE_OPTIONS_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_OPTIONS_CMD_GETMYCDINFO = 0;
    public static final int UCE_OPTIONS_CMD_GET_VERSION = 5;
    public static final int UCE_OPTIONS_CMD_RESPONSEINCOMINGOPTIONS = 4;
    public static final int UCE_OPTIONS_CMD_SETMYCDINFO = 1;
    public static final int UCE_OPTIONS_CMD_UNKNOWN = 6;
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

    public OptionsCmdId() {
        this.mCmdId = 6;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCmdId);
    }

    private OptionsCmdId(Parcel parcel) {
        this.mCmdId = 6;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mCmdId = parcel.readInt();
    }
}
