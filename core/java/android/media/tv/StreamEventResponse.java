package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

public final class StreamEventResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<StreamEventResponse> CREATOR =
            new Parcelable.Creator<
                    StreamEventResponse>() { // from class: android.media.tv.StreamEventResponse.1
                @Override // android.os.Parcelable.Creator
                public StreamEventResponse createFromParcel(Parcel source) {
                    source.readInt();
                    return StreamEventResponse.createFromParcelBody(source);
                }

                @Override // android.os.Parcelable.Creator
                public StreamEventResponse[] newArray(int size) {
                    return new StreamEventResponse[size];
                }
            };
    private static final int RESPONSE_TYPE = 5;
    private final byte[] mData;
    private final int mEventId;
    private final long mNptMillis;

    static StreamEventResponse createFromParcelBody(Parcel in) {
        return new StreamEventResponse(in);
    }

    public StreamEventResponse(
            int requestId,
            int sequence,
            int responseResult,
            int eventId,
            long nptMillis,
            byte[] data) {
        super(5, requestId, sequence, responseResult);
        this.mEventId = eventId;
        this.mNptMillis = nptMillis;
        this.mData = data;
    }

    private StreamEventResponse(Parcel source) {
        super(5, source);
        this.mEventId = source.readInt();
        this.mNptMillis = source.readLong();
        int dataLength = source.readInt();
        if (dataLength > 0) {
            this.mData = new byte[dataLength];
            source.readByteArray(this.mData);
        } else {
            this.mData = null;
        }
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
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mEventId);
        dest.writeLong(this.mNptMillis);
        if (this.mData != null && this.mData.length > 0) {
            dest.writeInt(this.mData.length);
            dest.writeByteArray(this.mData);
        } else {
            dest.writeInt(0);
        }
    }
}
