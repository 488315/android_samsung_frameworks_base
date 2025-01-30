package com.samsung.android.ims.p039im;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemImParticipantData implements Parcelable {
    public static final Parcelable.Creator<SemImParticipantData> CREATOR = new Parcelable.Creator<SemImParticipantData>() { // from class: com.samsung.android.ims.im.SemImParticipantData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImParticipantData createFromParcel(Parcel in) {
            return new SemImParticipantData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImParticipantData[] newArray(int size) {
            return new SemImParticipantData[size];
        }
    };
    private String mChatId;
    private int mId;
    private int mStatus;
    private String mUriString;
    private String mUserAlias;

    public SemImParticipantData(String chatId, String uri, int id, int status, String userAlias) {
        this.mChatId = chatId;
        this.mUriString = uri;
        this.mId = id;
        this.mStatus = status;
        this.mUserAlias = userAlias;
    }

    public String getChatId() {
        return this.mChatId;
    }

    public String getUriString() {
        return this.mUriString;
    }

    public int getId() {
        return this.mId;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String getUserAlias() {
        return this.mUserAlias;
    }

    public void setChatId(String mChatId) {
        this.mChatId = mChatId;
    }

    public void setUriString(String mUriString) {
        this.mUriString = mUriString;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public void setUserAlias(String mUserAlias) {
        this.mUserAlias = mUserAlias;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mChatId);
        out.writeString(this.mUriString);
        out.writeInt(this.mId);
        out.writeInt(this.mStatus);
        out.writeString(this.mUserAlias);
    }

    private SemImParticipantData(Parcel in) {
        this.mChatId = in.readString();
        this.mUriString = in.readString();
        this.mId = in.readInt();
        this.mStatus = in.readInt();
        this.mUserAlias = in.readString();
    }
}
