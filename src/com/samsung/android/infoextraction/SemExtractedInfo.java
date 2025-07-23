package com.samsung.android.infoextraction;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemExtractedInfo implements Parcelable {
    public static final Parcelable.Creator<SemExtractedInfo> CREATOR = new Parcelable.Creator<SemExtractedInfo>() { // from class: com.samsung.android.infoextraction.SemExtractedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExtractedInfo createFromParcel(Parcel parcel) {
            SemExtractedInfo semExtractedInfo = new SemExtractedInfo();
            semExtractedInfo.readFromParcel(parcel);
            return semExtractedInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemExtractedInfo[] newArray(int i) {
            return new SemExtractedInfo[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemExtractedInfo() {
    }

    public SemExtractedInfo(int i, String str, Object obj, Object obj2, int i2, int i3, float f) {
        this.mResultType = i;
        this.mSrc = str;
        this.mResult = obj;
        this.mExtraDatas = obj2;
        this.mStartPos = i2;
        this.mEndPos = i3;
        this.mAccuracy = f;
    }

    public SemExtractedInfo(int i, String str, Object obj, Object obj2, int i2, int i3, float f, int i4, int i5) {
        this.mResultType = i;
        this.mSrc = str;
        this.mResult = obj;
        this.mExtraDatas = obj2;
        this.mStartPos = i2;
        this.mEndPos = i3;
        this.mAccuracy = f;
        this.mUIState = i4;
        this.mUsingData = i5;
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultType);
        parcel.writeString(this.mSrc);
        parcel.writeValue(this.mResult);
        parcel.writeValue(this.mExtraDatas);
        parcel.writeInt(this.mStartPos);
        parcel.writeInt(this.mEndPos);
        parcel.writeFloat(this.mAccuracy);
        parcel.writeInt(this.mUIState);
        parcel.writeInt(this.mUsingData);
    }

    public void readFromParcel(Parcel parcel) {
        this.mResultType = parcel.readInt();
        this.mSrc = parcel.readString();
        this.mResult = parcel.readValue(Object.class.getClassLoader());
        this.mExtraDatas = parcel.readValue(Object.class.getClassLoader());
        this.mStartPos = parcel.readInt();
        this.mEndPos = parcel.readInt();
        this.mAccuracy = parcel.readFloat();
        this.mUIState = parcel.readInt();
        this.mUsingData = parcel.readInt();
    }
}
