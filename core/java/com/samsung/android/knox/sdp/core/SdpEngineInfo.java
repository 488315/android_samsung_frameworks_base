package com.samsung.android.knox.sdp.core;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SdpEngineInfo implements Parcelable {
    private String mAlias;
    private int mFlags;
    private int mId;
    private boolean mIsMigrating;
    private String mPackageName;
    private int mState;
    private int mType;
    private int mUserId;
    private int mVersion;
    private static String PERSONA_PWD_RESET_TOKEN = "PersonaPwdResetToken";
    private static String PWD_RESET_TOKEN = "PwdResetToken";
    public static final Parcelable.Creator<SdpEngineInfo> CREATOR = new Parcelable.Creator<SdpEngineInfo>() { // from class: com.samsung.android.knox.sdp.core.SdpEngineInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpEngineInfo createFromParcel(Parcel source) {
            return new SdpEngineInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpEngineInfo[] newArray(int size) {
            return new SdpEngineInfo[size];
        }
    };

    public SdpEngineInfo(String alias, int id, int userId, int state, int flags, int version, boolean isMigrating) {
        int i;
        String str;
        this.mIsMigrating = false;
        this.mPackageName = "";
        if (alias == null) {
            if (id < 0 || id > 999) {
                str = "";
            } else {
                str = "android_" + id;
            }
            this.mAlias = str;
        } else {
            this.mAlias = alias;
        }
        this.mId = id;
        this.mUserId = userId;
        this.mState = state;
        this.mFlags = flags;
        this.mVersion = version;
        this.mPackageName = "";
        if (this.mAlias != null && !this.mAlias.isEmpty()) {
            if (this.mAlias.equals("android_" + id)) {
                i = 1;
            } else {
                i = 2;
            }
            this.mType = i;
        } else {
            this.mType = -1;
        }
        this.mIsMigrating = isMigrating;
    }

    public SdpEngineInfo() {
        this.mIsMigrating = false;
        this.mPackageName = "";
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
        if (this.mType == 1) {
            return PERSONA_PWD_RESET_TOKEN + this.mId;
        }
        if (this.mType == 2) {
            return PWD_RESET_TOKEN + this.mId;
        }
        return null;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        if (packageName != null) {
            this.mPackageName = packageName;
        }
    }

    public int getId() {
        return this.mId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getState() {
        return this.mState;
    }

    public void setFlag(int flag) {
        this.mFlags = flag;
    }

    public int getFlag() {
        return this.mFlags;
    }

    public void setVersion(int version) {
        this.mVersion = version;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setIsMigrating(boolean isMigrating) {
        this.mIsMigrating = isMigrating;
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
    public int describeContents() {
        return 0;
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

    private SdpEngineInfo(Parcel source) {
        this.mIsMigrating = false;
        this.mPackageName = "";
        this.mAlias = source.readString();
        this.mPackageName = source.readString();
        this.mId = source.readInt();
        this.mUserId = source.readInt();
        this.mState = source.readInt();
        this.mFlags = source.readInt();
        this.mVersion = source.readInt();
        this.mType = source.readInt();
        this.mIsMigrating = source.readInt() != 0;
    }
}
