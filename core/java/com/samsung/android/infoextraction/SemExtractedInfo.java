package com.samsung.android.infoextraction;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemExtractedInfo implements Parcelable {
  public static final Parcelable.Creator<SemExtractedInfo> CREATOR =
      new Parcelable.Creator<
          SemExtractedInfo>() { // from class: com.samsung.android.infoextraction.SemExtractedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExtractedInfo createFromParcel(Parcel in) {
          SemExtractedInfo data = new SemExtractedInfo();
          data.readFromParcel(in);
          return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExtractedInfo[] newArray(int size) {
          return new SemExtractedInfo[size];
        }
      };
  private static final int HIDE_HERMES_UI = 2;
  private static final int SUPPORT_HERMES_UI = 1;
  private static final int USE_EXTRA = 2;
  private static final int USE_RESULT = 1;
  private float mAccuracy;
  private int mEndPos;
  private Object mExtraDatas;
  private Object mResult;
  private int mResultType;
  private String mSrc;
  private int mStartPos;
  private int mUIState;
  private int mUsingData;

  public SemExtractedInfo() {}

  public SemExtractedInfo(
      int resultType,
      String src,
      Object result,
      Object extraDatas,
      int startPos,
      int endPos,
      float accuracy) {
    this.mResultType = resultType;
    this.mSrc = src;
    this.mResult = result;
    this.mExtraDatas = extraDatas;
    this.mStartPos = startPos;
    this.mEndPos = endPos;
    this.mAccuracy = accuracy;
  }

  public SemExtractedInfo(
      int resultType,
      String src,
      Object result,
      Object extraDatas,
      int startPos,
      int endPos,
      float accuracy,
      int uiState,
      int usingData) {
    this.mResultType = resultType;
    this.mSrc = src;
    this.mResult = result;
    this.mExtraDatas = extraDatas;
    this.mStartPos = startPos;
    this.mEndPos = endPos;
    this.mAccuracy = accuracy;
    this.mUIState = uiState;
    this.mUsingData = usingData;
  }

  public int getResultType() {
    return this.mResultType;
  }

  public String getSource() {
    return this.mSrc;
  }

  public Object getResult() {
    return this.mResult;
  }

  public Object getExtraData() {
    return this.mExtraDatas;
  }

  public int getStartPosition() {
    return this.mStartPos;
  }

  public int getEndPosition() {
    return this.mEndPos;
  }

  public float getAccuracy() {
    return this.mAccuracy;
  }

  public Object getAdaptableData() {
    if (this.mUsingData == 2) {
      return this.mExtraDatas;
    }
    return this.mResult;
  }

  public boolean isPossibleToShow() {
    return this.mUIState == 1;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.mResultType);
    out.writeString(this.mSrc);
    out.writeValue(this.mResult);
    out.writeValue(this.mExtraDatas);
    out.writeInt(this.mStartPos);
    out.writeInt(this.mEndPos);
    out.writeFloat(this.mAccuracy);
    out.writeInt(this.mUIState);
    out.writeInt(this.mUsingData);
  }

  public void readFromParcel(Parcel in) {
    this.mResultType = in.readInt();
    this.mSrc = in.readString();
    this.mResult = in.readValue(Object.class.getClassLoader());
    this.mExtraDatas = in.readValue(Object.class.getClassLoader());
    this.mStartPos = in.readInt();
    this.mEndPos = in.readInt();
    this.mAccuracy = in.readFloat();
    this.mUIState = in.readInt();
    this.mUsingData = in.readInt();
  }
}
