package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class PresRlmiInfo implements Parcelable {
    public static final Parcelable.Creator<PresRlmiInfo> CREATOR = new Parcelable.Creator<PresRlmiInfo>() { // from class: com.android.ims.internal.uce.presence.PresRlmiInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresRlmiInfo createFromParcel(Parcel parcel) {
            return new PresRlmiInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresRlmiInfo[] newArray(int i) {
            return new PresRlmiInfo[i];
        }
    };
    private boolean mFullState;
    private String mListName;
    private PresSubscriptionState mPresSubscriptionState;
    private int mRequestId;
    private int mSubscriptionExpireTime;
    private String mSubscriptionTerminatedReason;
    private String mUri;
    private int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getUri() {
        return this.mUri;
    }

    public void setUri(String str) {
        this.mUri = str;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public boolean isFullState() {
        return this.mFullState;
    }

    public void setFullState(boolean z) {
        this.mFullState = z;
    }

    public String getListName() {
        return this.mListName;
    }

    public void setListName(String str) {
        this.mListName = str;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public PresSubscriptionState getPresSubscriptionState() {
        return this.mPresSubscriptionState;
    }

    public void setPresSubscriptionState(PresSubscriptionState presSubscriptionState) {
        this.mPresSubscriptionState = presSubscriptionState;
    }

    public int getSubscriptionExpireTime() {
        return this.mSubscriptionExpireTime;
    }

    public void setSubscriptionExpireTime(int i) {
        this.mSubscriptionExpireTime = i;
    }

    public String getSubscriptionTerminatedReason() {
        return this.mSubscriptionTerminatedReason;
    }

    public void setSubscriptionTerminatedReason(String str) {
        this.mSubscriptionTerminatedReason = str;
    }

    public PresRlmiInfo() {
        this.mUri = "";
        this.mListName = "";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUri);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mFullState ? 1 : 0);
        parcel.writeString(this.mListName);
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mPresSubscriptionState, i);
        parcel.writeInt(this.mSubscriptionExpireTime);
        parcel.writeString(this.mSubscriptionTerminatedReason);
    }

    private PresRlmiInfo(Parcel parcel) {
        this.mUri = "";
        this.mListName = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUri = parcel.readString();
        this.mVersion = parcel.readInt();
        this.mFullState = parcel.readInt() != 0;
        this.mListName = parcel.readString();
        this.mRequestId = parcel.readInt();
        this.mPresSubscriptionState = (PresSubscriptionState) parcel.readParcelable(PresSubscriptionState.class.getClassLoader(), PresSubscriptionState.class);
        this.mSubscriptionExpireTime = parcel.readInt();
        this.mSubscriptionTerminatedReason = parcel.readString();
    }
}
