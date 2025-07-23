package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class PesResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<PesResponse> CREATOR = new Parcelable.Creator<PesResponse>() { // from class: android.media.tv.PesResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PesResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return PesResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PesResponse[] newArray(int i) {
            return new PesResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 4;
    private final String mSharedFilterToken;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static PesResponse createFromParcelBody(Parcel parcel) {
        return new PesResponse(parcel);
    }

    public PesResponse(int i, int i2, int i3, String str) {
        super(4, i, i2, i3);
        this.mSharedFilterToken = str;
    }

    PesResponse(Parcel parcel) {
        super(4, parcel);
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
