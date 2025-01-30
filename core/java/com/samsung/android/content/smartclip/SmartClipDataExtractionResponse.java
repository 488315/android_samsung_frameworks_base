package com.samsung.android.content.smartclip;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SmartClipDataExtractionResponse implements Parcelable {
  public static final Parcelable.Creator<SmartClipDataExtractionResponse> CREATOR =
      new Parcelable.Creator<
          SmartClipDataExtractionResponse>() { // from class:
                                               // com.samsung.android.content.smartclip.SmartClipDataExtractionResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionResponse createFromParcel(Parcel in) {
          SmartClipDataExtractionResponse data = new SmartClipDataExtractionResponse(0, 0, null);
          data.readFromParcel(in);
          return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionResponse[] newArray(int size) {
          return new SmartClipDataExtractionResponse[size];
        }
      };
  public int mExtractionMode;
  public SemSmartClipDataRepository mRepository;
  public int mRequestId;

  public SmartClipDataExtractionResponse(
      int requestId, int extractionMode, SemSmartClipDataRepository repository) {
    this.mRequestId = 0;
    this.mExtractionMode = 0;
    this.mRepository = null;
    this.mRequestId = requestId;
    this.mExtractionMode = extractionMode;
    this.mRepository = repository;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.mRequestId);
    out.writeInt(this.mExtractionMode);
    out.writeParcelable(this.mRepository, flags);
  }

  public void readFromParcel(Parcel in) {
    this.mRequestId = in.readInt();
    this.mExtractionMode = in.readInt();
    this.mRepository =
        (SemSmartClipDataRepository)
            in.readParcelable(SemSmartClipDataRepository.class.getClassLoader());
  }
}
