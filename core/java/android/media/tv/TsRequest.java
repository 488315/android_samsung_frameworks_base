package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TsRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<TsRequest> CREATOR = new Parcelable.Creator<TsRequest>() { // from class: android.media.tv.TsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsRequest createFromParcel(Parcel source) {
            source.readInt();
            return TsRequest.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TsRequest[] newArray(int size) {
            return new TsRequest[size];
        }
    };
    private static final int REQUEST_TYPE = 1;
    private final int mTsPid;

    static TsRequest createFromParcelBody(Parcel in) {
        return new TsRequest(in);
    }

    public TsRequest(int requestId, int option, int tsPid) {
        super(1, requestId, option);
        this.mTsPid = tsPid;
    }

    TsRequest(Parcel source) {
        super(1, source);
        this.mTsPid = source.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mTsPid);
    }
}
