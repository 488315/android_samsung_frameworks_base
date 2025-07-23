package com.samsung.android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class MultiResolutionChangeRequestInfo implements Parcelable {
    public static final Parcelable.Creator<MultiResolutionChangeRequestInfo> CREATOR = new Parcelable.Creator<MultiResolutionChangeRequestInfo>() { // from class: com.samsung.android.view.MultiResolutionChangeRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MultiResolutionChangeRequestInfo createFromParcel(Parcel parcel) {
            MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo = new MultiResolutionChangeRequestInfo();
            multiResolutionChangeRequestInfo.readFromParcel(parcel);
            return multiResolutionChangeRequestInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MultiResolutionChangeRequestInfo[] newArray(int i) {
            return new MultiResolutionChangeRequestInfo[i];
        }
    };
    private String mCallerInfo;
    private int mDensity;
    private int mDisplayId;
    private int mForcedHideCutout;
    private int mHeight;
    private boolean mSaveToSettings;
    private int mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MultiResolutionChangeRequestInfo() {
        this.mForcedHideCutout = -1;
    }

    private MultiResolutionChangeRequestInfo(int i, int i2, int i3, int i4, boolean z) {
        this.mForcedHideCutout = -1;
        this.mDisplayId = i;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mDensity = i4;
        this.mSaveToSettings = z;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getDensity() {
        return this.mDensity;
    }

    public boolean getSaveToSettings() {
        return this.mSaveToSettings;
    }

    public String getCallerInfo() {
        return this.mCallerInfo;
    }

    public int getForcedHideCutout() {
        return this.mForcedHideCutout;
    }

    public static class Builder {
        public int mDisplayId;
        public int mWidth = -1;
        public int mHeight = -1;
        public int mDensity = -1;
        public boolean mSaveToSettings = false;
        public String mCallerInfo = null;
        public int mForcedHideCutout = -1;

        public Builder(int i) {
            this.mDisplayId = i;
        }

        public MultiResolutionChangeRequestInfo build() {
            MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo = new MultiResolutionChangeRequestInfo(this.mDisplayId, this.mWidth, this.mHeight, this.mDensity, this.mSaveToSettings);
            multiResolutionChangeRequestInfo.mForcedHideCutout = this.mForcedHideCutout;
            return multiResolutionChangeRequestInfo;
        }

        public Builder setWidth(int i) {
            this.mWidth = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.mHeight = i;
            return this;
        }

        public Builder setDensity(int i) {
            this.mDensity = i;
            return this;
        }

        public Builder setSaveToSettings(boolean z) {
            this.mSaveToSettings = z;
            return this;
        }

        public Builder setCallerInfo(String str) {
            this.mCallerInfo = str;
            return this;
        }

        public Builder setForcedHideCutout(int i) {
            this.mForcedHideCutout = i;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDensity);
        parcel.writeBoolean(this.mSaveToSettings);
        parcel.writeString(this.mCallerInfo);
        parcel.writeInt(this.mForcedHideCutout);
    }

    public void readFromParcel(Parcel parcel) {
        this.mDisplayId = parcel.readInt();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDensity = parcel.readInt();
        this.mSaveToSettings = parcel.readBoolean();
        this.mCallerInfo = parcel.readString();
        this.mForcedHideCutout = parcel.readInt();
    }
}
