package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class SectionRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<SectionRequest> CREATOR = new Parcelable.Creator<SectionRequest>() { // from class: android.media.tv.SectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SectionRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return SectionRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SectionRequest[] newArray(int i) {
            return new SectionRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 3;
    private final int mTableId;
    private final int mTsPid;
    private final int mVersion;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static SectionRequest createFromParcelBody(Parcel parcel) {
        return new SectionRequest(parcel);
    }

    public SectionRequest(int i, int i2, int i3, int i4, int i5) {
        super(3, i, i2);
        this.mTsPid = i3;
        this.mTableId = i4;
        this.mVersion = i5;
    }

    SectionRequest(Parcel parcel) {
        super(3, parcel);
        this.mTsPid = parcel.readInt();
        this.mTableId = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
        parcel.writeInt(this.mTableId);
        parcel.writeInt(this.mVersion);
    }
}
