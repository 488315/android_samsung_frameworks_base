package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SmartClipRemoteRequestResult implements Parcelable {
    public static final Parcelable.Creator<SmartClipRemoteRequestResult> CREATOR = new Parcelable.Creator<SmartClipRemoteRequestResult>() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestResult createFromParcel(Parcel parcel) {
            SmartClipRemoteRequestResult smartClipRemoteRequestResult = new SmartClipRemoteRequestResult(0, 0, null);
            smartClipRemoteRequestResult.readFromParcel(parcel);
            return smartClipRemoteRequestResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestResult[] newArray(int i) {
            return new SmartClipRemoteRequestResult[i];
        }
    };
    public int mRequestId;
    public int mRequestType = 0;
    public Parcelable mResultData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmartClipRemoteRequestResult(int i, int i2, Parcelable parcelable) {
        this.mRequestId = i;
        this.mResultData = parcelable;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mRequestType);
        parcel.writeParcelable(this.mResultData, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mRequestType = parcel.readInt();
        this.mResultData = parcel.readParcelable(null);
    }
}
