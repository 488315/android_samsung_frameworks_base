package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class StreamEventResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<StreamEventResponse> CREATOR = new Parcelable.Creator<StreamEventResponse>() { // from class: android.media.tv.StreamEventResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return StreamEventResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamEventResponse[] newArray(int i) {
            return new StreamEventResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 5;
    private final byte[] mData;
    private final int mEventId;
    private final long mNptMillis;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static StreamEventResponse createFromParcelBody(Parcel parcel) {
        return new StreamEventResponse(parcel);
    }

    public StreamEventResponse(int i, int i2, int i3, int i4, long j, byte[] bArr) {
        super(5, i, i2, i3);
        this.mEventId = i4;
        this.mNptMillis = j;
        this.mData = bArr;
    }

    private StreamEventResponse(Parcel parcel) {
        super(5, parcel);
        this.mEventId = parcel.readInt();
        this.mNptMillis = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.mData = bArr;
            parcel.readByteArray(bArr);
            return;
        }
        this.mData = null;
    }

    public int getEventId() {
        return this.mEventId;
    }

    public long getNptMillis() {
        return this.mNptMillis;
    }

    public byte[] getData() {
        return this.mData;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mEventId);
        parcel.writeLong(this.mNptMillis);
        byte[] bArr = this.mData;
        if (bArr != null && bArr.length > 0) {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mData);
        } else {
            parcel.writeInt(0);
        }
    }
}
