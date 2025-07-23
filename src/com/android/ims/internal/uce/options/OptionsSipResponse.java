package com.android.ims.internal.uce.options;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class OptionsSipResponse implements Parcelable {
    public static final Parcelable.Creator<OptionsSipResponse> CREATOR = new Parcelable.Creator<OptionsSipResponse>() { // from class: com.android.ims.internal.uce.options.OptionsSipResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsSipResponse createFromParcel(Parcel parcel) {
            return new OptionsSipResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsSipResponse[] newArray(int i) {
            return new OptionsSipResponse[i];
        }
    };
    private OptionsCmdId mCmdId;
    private String mReasonHeader;
    private String mReasonPhrase;
    private int mRequestId;
    private int mRetryAfter;
    private int mSipResponseCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OptionsCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(OptionsCmdId optionsCmdId) {
        this.mCmdId = optionsCmdId;
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

    public OptionsSipResponse() {
        this.mRequestId = 0;
        this.mSipResponseCode = 0;
        this.mRetryAfter = 0;
        this.mReasonPhrase = "";
        this.mReasonHeader = "";
        this.mCmdId = new OptionsCmdId();
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

    private OptionsSipResponse(Parcel parcel) {
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
        this.mCmdId = (OptionsCmdId) parcel.readParcelable(OptionsCmdId.class.getClassLoader(), OptionsCmdId.class);
        this.mRetryAfter = parcel.readInt();
        this.mReasonHeader = parcel.readString();
    }
}
