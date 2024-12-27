package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SmartClipRemoteRequestResult implements Parcelable {
    public static final Parcelable.Creator<SmartClipRemoteRequestResult> CREATOR = new Parcelable.Creator<SmartClipRemoteRequestResult>() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestResult createFromParcel(Parcel in) {
            SmartClipRemoteRequestResult data = new SmartClipRemoteRequestResult(0, 0, null);
            data.readFromParcel(in);
            return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestResult[] newArray(int size) {
            return new SmartClipRemoteRequestResult[size];
        }
    };
    public int mRequestId;
    public int mRequestType = 0;
    public Parcelable mResultData;

    public SmartClipRemoteRequestResult(int requestId, int requestType, Parcelable resultData) {
        this.mRequestId = 0;
        this.mResultData = null;
        this.mRequestId = requestId;
        this.mResultData = resultData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mRequestId);
        out.writeInt(this.mRequestType);
        out.writeParcelable(this.mResultData, flags);
    }

    public void readFromParcel(Parcel in) {
        this.mRequestId = in.readInt();
        this.mRequestType = in.readInt();
        this.mResultData = in.readParcelable(null);
    }
}
