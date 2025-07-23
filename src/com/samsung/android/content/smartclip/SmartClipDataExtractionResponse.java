package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SmartClipDataExtractionResponse implements Parcelable {
    public static final Parcelable.Creator<SmartClipDataExtractionResponse> CREATOR = new Parcelable.Creator<SmartClipDataExtractionResponse>() { // from class: com.samsung.android.content.smartclip.SmartClipDataExtractionResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionResponse createFromParcel(Parcel parcel) {
            SmartClipDataExtractionResponse smartClipDataExtractionResponse = new SmartClipDataExtractionResponse(0, 0, null);
            smartClipDataExtractionResponse.readFromParcel(parcel);
            return smartClipDataExtractionResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionResponse[] newArray(int i) {
            return new SmartClipDataExtractionResponse[i];
        }
    };
    public int mExtractionMode;
    public SemSmartClipDataRepository mRepository;
    public int mRequestId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmartClipDataExtractionResponse(int i, int i2, SemSmartClipDataRepository semSmartClipDataRepository) {
        this.mRequestId = i;
        this.mExtractionMode = i2;
        this.mRepository = semSmartClipDataRepository;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mExtractionMode);
        parcel.writeParcelable(this.mRepository, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mExtractionMode = parcel.readInt();
        this.mRepository = (SemSmartClipDataRepository) parcel.readParcelable(SemSmartClipDataRepository.class.getClassLoader());
    }
}
