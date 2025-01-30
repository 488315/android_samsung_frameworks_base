package com.samsung.android.content.smartclip;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemSmartClipExtendedMetaTag extends SemSmartClipMetaTag implements Parcelable {
  public static final Parcelable.Creator<SemSmartClipExtendedMetaTag> CREATOR =
      new Parcelable.Creator<
          SemSmartClipExtendedMetaTag>() { // from class:
                                           // com.samsung.android.content.smartclip.SemSmartClipExtendedMetaTag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSmartClipExtendedMetaTag createFromParcel(Parcel in) {
          Log.m94d(
              SemSmartClipExtendedMetaTag.TAG,
              "SemSmartClipExtendedMetaTag.createFromParcel called");
          SemSmartClipExtendedMetaTag data = new SemSmartClipExtendedMetaTag(null, null);
          data.readFromParcel(in);
          return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(getType());
    out.writeString(getValue());
    byte[] bArr = this.mExtraData;
    if (bArr != null) {
      out.writeInt(bArr.length);
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
      byte[] bArr = new byte[extraDataLen];
      this.mExtraData = bArr;
      in.readByteArray(bArr);
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
