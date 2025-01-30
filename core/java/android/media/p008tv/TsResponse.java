package android.media.p008tv;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes2.dex */
public final class TsResponse extends BroadcastInfoResponse implements Parcelable {
  public static final Parcelable.Creator<TsResponse> CREATOR =
      new Parcelable.Creator<TsResponse>() { // from class: android.media.tv.TsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsResponse createFromParcel(Parcel source) {
          source.readInt();
          return TsResponse.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsResponse[] newArray(int size) {
          return new TsResponse[size];
        }
      };
  private static final int RESPONSE_TYPE = 1;
  private final String mSharedFilterToken;

  static TsResponse createFromParcelBody(Parcel in) {
    return new TsResponse(in);
  }

  public TsResponse(int requestId, int sequence, int responseResult, String sharedFilterToken) {
    super(1, requestId, sequence, responseResult);
    this.mSharedFilterToken = sharedFilterToken;
  }

  TsResponse(Parcel source) {
    super(1, source);
    this.mSharedFilterToken = source.readString();
  }

  public String getSharedFilterToken() {
    return this.mSharedFilterToken;
  }

  @Override // android.media.p008tv.BroadcastInfoResponse, android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.media.p008tv.BroadcastInfoResponse, android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.mSharedFilterToken);
  }
}
