package com.sec.ims.p053im;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImParticipantData implements Parcelable {
    public static final Parcelable.Creator<ImParticipantData> CREATOR = new Parcelable.Creator<ImParticipantData>() { // from class: com.sec.ims.im.ImParticipantData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImParticipantData createFromParcel(Parcel parcel) {
            return new ImParticipantData(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImParticipantData[] newArray(int i) {
            return new ImParticipantData[i];
        }
    };
    private String mChatId;
    private int mId;
    private int mStatus;
    private String mUriString;
    private String mUserAlias;

    public /* synthetic */ ImParticipantData(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getChatId() {
        return this.mChatId;
    }

    public int getId() {
        return this.mId;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String getUriString() {
        return this.mUriString;
    }

    public String getUserAlias() {
        return this.mUserAlias;
    }

    public void setChatId(String str) {
        this.mChatId = str;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public void setUriString(String str) {
        this.mUriString = str;
    }

    public void setUserAlias(String str) {
        this.mUserAlias = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ImParticipant [mChatId=");
        sb.append(this.mChatId);
        sb.append(", mUriString=");
        sb.append(this.mUriString);
        sb.append(", mId=");
        sb.append(this.mId);
        sb.append(", mStatus=");
        sb.append(this.mStatus);
        sb.append(", mUserAlias=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mUserAlias, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mChatId);
        parcel.writeString(this.mUriString);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mUserAlias);
    }

    public ImParticipantData(String str, String str2, int i, int i2, String str3) {
        this.mChatId = str;
        this.mUriString = str2;
        this.mId = i;
        this.mStatus = i2;
        this.mUserAlias = str3;
    }

    private ImParticipantData(Parcel parcel) {
        this.mChatId = parcel.readString();
        this.mUriString = parcel.readString();
        this.mId = parcel.readInt();
        this.mStatus = parcel.readInt();
        this.mUserAlias = parcel.readString();
    }
}
