package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TsRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<TsRequest> CREATOR = new Parcelable.Creator<TsRequest>() { // from class: android.media.tv.TsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TsRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsRequest[] newArray(int i) {
            return new TsRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 1;
    private final int mTsPid;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TsRequest createFromParcelBody(Parcel parcel) {
        return new TsRequest(parcel);
    }

    public TsRequest(int i, int i2, int i3) {
        super(1, i, i2);
        this.mTsPid = i3;
    }

    TsRequest(Parcel parcel) {
        super(1, parcel);
        this.mTsPid = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
    }
}
