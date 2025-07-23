package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TsResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<TsResponse> CREATOR = new Parcelable.Creator<TsResponse>() { // from class: android.media.tv.TsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TsResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsResponse[] newArray(int i) {
            return new TsResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 1;
    private final String mSharedFilterToken;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TsResponse createFromParcelBody(Parcel parcel) {
        return new TsResponse(parcel);
    }

    public TsResponse(int i, int i2, int i3, String str) {
        super(1, i, i2, i3);
        this.mSharedFilterToken = str;
    }

    TsResponse(Parcel parcel) {
        super(1, parcel);
        this.mSharedFilterToken = parcel.readString();
    }

    public String getSharedFilterToken() {
        return this.mSharedFilterToken;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mSharedFilterToken);
    }
}
