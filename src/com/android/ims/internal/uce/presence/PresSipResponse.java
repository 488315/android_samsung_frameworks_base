package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class PresSipResponse implements Parcelable {
    public static final Parcelable.Creator<PresSipResponse> CREATOR = new Parcelable.Creator<PresSipResponse>() { // from class: com.android.ims.internal.uce.presence.PresSipResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresSipResponse createFromParcel(Parcel parcel) {
            return new PresSipResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresSipResponse[] newArray(int i) {
            return new PresSipResponse[i];
        }
    };
    private PresCmdId mCmdId;
    private String mReasonHeader;
    private String mReasonPhrase;
    private int mRequestId;
    private int mRetryAfter;
    private int mSipResponseCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PresCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public int getSipResponseCode() {
        return this.mSipResponseCode;
    }

    public void setSipResponseCode(int i) {
        this.mSipResponseCode = i;
    }

    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public void setReasonPhrase(String str) {
        this.mReasonPhrase = str;
    }

    public int getRetryAfter() {
        return this.mRetryAfter;
    }

    public void setRetryAfter(int i) {
        this.mRetryAfter = i;
    }

    public String getReasonHeader() {
        return this.mReasonHeader;
    }

    public void setReasonHeader(String str) {
        this.mReasonHeader = str;
    }

    public PresSipResponse() {
        this.mCmdId = new PresCmdId();
        this.mRequestId = 0;
        this.mSipResponseCode = 0;
        this.mRetryAfter = 0;
        this.mReasonPhrase = "";
        this.mReasonHeader = "";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mSipResponseCode);
        parcel.writeString(this.mReasonPhrase);
        parcel.writeParcelable(this.mCmdId, i);
        parcel.writeInt(this.mRetryAfter);
        parcel.writeString(this.mReasonHeader);
    }

    private PresSipResponse(Parcel parcel) {
        this.mCmdId = new PresCmdId();
        this.mRequestId = 0;
        this.mSipResponseCode = 0;
        this.mRetryAfter = 0;
        this.mReasonPhrase = "";
        this.mReasonHeader = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mSipResponseCode = parcel.readInt();
        this.mReasonPhrase = parcel.readString();
        this.mCmdId = (PresCmdId) parcel.readParcelable(PresCmdId.class.getClassLoader(), PresCmdId.class);
        this.mRetryAfter = parcel.readInt();
        this.mReasonHeader = parcel.readString();
    }
}
