package com.samsung.android.knox.sdp.core;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SdpEngineInfo implements Parcelable {
    public static final Parcelable.Creator<SdpEngineInfo> CREATOR = new Parcelable.Creator<SdpEngineInfo>() { // from class: com.samsung.android.knox.sdp.core.SdpEngineInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpEngineInfo createFromParcel(Parcel parcel) {
            return new SdpEngineInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpEngineInfo[] newArray(int i) {
            return new SdpEngineInfo[i];
        }
    };
    private static String PERSONA_PWD_RESET_TOKEN = "PersonaPwdResetToken";
    private static String PWD_RESET_TOKEN = "PwdResetToken";
    private String mAlias;
    private int mFlags;
    private int mId;
    private boolean mIsMigrating;
    private String mPackageName;
    private int mState;
    private int mType;
    private int mUserId;
    private int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SdpEngineInfo(String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        String str2;
        this.mIsMigrating = false;
        this.mPackageName = "";
        if (str == null) {
            if (i < 0 || i > 999) {
                str2 = "";
            } else {
                str2 = "android_" + i;
            }
            this.mAlias = str2;
        } else {
            this.mAlias = str;
        }
        this.mId = i;
        this.mUserId = i2;
        this.mState = i3;
        this.mFlags = i4;
        this.mVersion = i5;
        this.mPackageName = "";
        String str3 = this.mAlias;
        if (str3 != null && !str3.isEmpty()) {
            String str4 = this.mAlias;
            StringBuilder sb = new StringBuilder("android_");
            sb.append(i);
            this.mType = str4.equals(sb.toString()) ? 1 : 2;
        } else {
            this.mType = -1;
        }
        this.mIsMigrating = z;
    }

    public SdpEngineInfo() {
        this.mAlias = null;
        this.mPackageName = "";
        this.mId = -1;
        this.mUserId = -1;
        this.mState = -1;
        this.mFlags = -1;
        this.mVersion = -1;
        this.mType = -1;
        this.mIsMigrating = false;
    }

    public String getResetTokenTimaAlias() {
        int i = this.mType;
        if (i == 1) {
            return PERSONA_PWD_RESET_TOKEN + this.mId;
        }
        if (i != 2) {
            return null;
        }
        return PWD_RESET_TOKEN + this.mId;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String str) {
        if (str != null) {
            this.mPackageName = str;
        }
    }

    public int getId() {
        return this.mId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public int getState() {
        return this.mState;
    }

    public void setFlag(int i) {
        this.mFlags = i;
    }

    public int getFlag() {
        return this.mFlags;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setIsMigrating(boolean z) {
        this.mIsMigrating = z;
    }

    public boolean isMigrating() {
        return this.mIsMigrating;
    }

    public boolean isCustomEngine() {
        return this.mType == 2;
    }

    public boolean isMinor() {
        return (this.mFlags & 1) == 1;
    }

    public boolean isMdfpp() {
        return !isMinor();
    }

    public boolean isAndroidDefaultEngine() {
        return this.mType == 1;
    }

    public String toString() {
        return "SdpEngineInfo { alias:" + this.mAlias + " pkg: " + this.mPackageName + " id:" + this.mId + " userid:" + this.mUserId + " state:" + this.mState + " flags:" + this.mFlags + " version:" + this.mVersion + " type:" + this.mType + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAlias);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mIsMigrating ? 1 : 0);
    }

    private SdpEngineInfo(Parcel parcel) {
        this.mIsMigrating = false;
        this.mPackageName = "";
        this.mAlias = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mId = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mState = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mType = parcel.readInt();
        this.mIsMigrating = parcel.readInt() != 0;
    }
}
