package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SemSmartClipExtendedMetaTag extends SemSmartClipMetaTag implements Parcelable {
    public static final Parcelable.Creator<SemSmartClipExtendedMetaTag> CREATOR =
            new Parcelable.Creator<
                    SemSmartClipExtendedMetaTag>() { // from class:
                                                     // com.samsung.android.content.smartclip.SemSmartClipExtendedMetaTag.1
                @Override // android.os.Parcelable.Creator
                public SemSmartClipExtendedMetaTag createFromParcel(Parcel in) {
                    Log.d(
                            SemSmartClipExtendedMetaTag.TAG,
                            "SemSmartClipExtendedMetaTag.createFromParcel called");
                    SemSmartClipExtendedMetaTag data = new SemSmartClipExtendedMetaTag(null, null);
                    data.readFromParcel(in);
                    return data;
                }

                @Override // android.os.Parcelable.Creator
                public SemSmartClipExtendedMetaTag[] newArray(int size) {
                    return new SemSmartClipExtendedMetaTag[size];
                }
            };
    public static final String TAG = "SemSmartClipExtendedMetaTag";
    protected byte[] mExtraData;
    protected Parcelable mParcelableData;

    public byte[] getExtraData() {
        return this.mExtraData;
    }

    public Parcelable getParcelableData() {
        return this.mParcelableData;
    }

    public SemSmartClipExtendedMetaTag(String tagType, String value) {
        super(tagType, value);
        this.mExtraData = null;
        this.mParcelableData = null;
    }

    public SemSmartClipExtendedMetaTag(String tagType, String value, byte[] extraData) {
        super(tagType, value);
        this.mExtraData = null;
        this.mParcelableData = null;
        this.mExtraData = extraData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getType());
        out.writeString(getValue());
        if (this.mExtraData != null) {
            out.writeInt(this.mExtraData.length);
            out.writeByteArray(this.mExtraData);
        } else {
            out.writeInt(-1);
        }
        if (this.mParcelableData != null) {
            out.writeInt(1);
            out.writeParcelable(this.mParcelableData, 0);
        } else {
            out.writeInt(0);
        }
    }

    public void readFromParcel(Parcel in) {
        String type = in.readString();
        String value = in.readString();
        setType(type);
        setValue(value);
        int extraDataLen = in.readInt();
        if (extraDataLen >= 0) {
            this.mExtraData = new byte[extraDataLen];
            in.readByteArray(this.mExtraData);
        } else {
            this.mExtraData = null;
        }
        boolean parcelableDataExist = in.readInt() != 0;
        if (parcelableDataExist) {
            this.mParcelableData = in.readParcelable(null);
        } else {
            this.mParcelableData = null;
        }
    }
}
