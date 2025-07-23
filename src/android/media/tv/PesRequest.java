package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class PesRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<PesRequest> CREATOR = new Parcelable.Creator<PesRequest>() { // from class: android.media.tv.PesRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PesRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return PesRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PesRequest[] newArray(int i) {
            return new PesRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 4;
    private final int mStreamId;
    private final int mTsPid;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static PesRequest createFromParcelBody(Parcel parcel) {
        return new PesRequest(parcel);
    }

    public PesRequest(int i, int i2, int i3, int i4) {
        super(4, i, i2);
        this.mTsPid = i3;
        this.mStreamId = i4;
    }

    PesRequest(Parcel parcel) {
        super(4, parcel);
        this.mTsPid = parcel.readInt();
        this.mStreamId = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
        parcel.writeInt(this.mStreamId);
    }
}
