package android.media.p008tv;

import android.net.Uri;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SharedMemory;

/* loaded from: classes2.dex */
public final class TableResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<TableResponse> CREATOR = new Parcelable.Creator<TableResponse>() { // from class: android.media.tv.TableResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableResponse createFromParcel(Parcel source) {
            source.readInt();
            return TableResponse.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableResponse[] newArray(int size) {
            return new TableResponse[size];
        }
    };
    private static final int RESPONSE_TYPE = 2;
    private final int mSize;
    private final byte[] mTableByteArray;
    private final SharedMemory mTableSharedMemory;
    private final Uri mTableUri;
    private final int mVersion;

    static TableResponse createFromParcelBody(Parcel in) {
        return new TableResponse(in);
    }

    @Deprecated
    public TableResponse(int requestId, int sequence, int responseResult, Uri tableUri, int version, int size) {
        super(2, requestId, sequence, responseResult);
        this.mVersion = version;
        this.mSize = size;
        this.mTableUri = tableUri;
        this.mTableByteArray = null;
        this.mTableSharedMemory = null;
    }

    private TableResponse(int requestId, int sequence, int responseResult, int version, int size, Uri tableUri, byte[] tableByteArray, SharedMemory tableSharedMemory) {
        super(2, requestId, sequence, responseResult);
        this.mVersion = version;
        this.mSize = size;
        this.mTableUri = tableUri;
        this.mTableByteArray = tableByteArray;
        this.mTableSharedMemory = tableSharedMemory;
    }

    public static final class Builder {
        private final int mRequestId;
        private final int mResponseResult;
        private final int mSequence;
        private final int mSize;
        private byte[] mTableByteArray;
        private SharedMemory mTableSharedMemory;
        private Uri mTableUri;
        private final int mVersion;

        public Builder(int requestId, int sequence, int responseResult, int version, int size) {
            this.mRequestId = requestId;
            this.mSequence = sequence;
            this.mResponseResult = responseResult;
            this.mVersion = version;
            this.mSize = size;
        }

        public Builder setTableUri(Uri uri) {
            this.mTableUri = uri;
            this.mTableByteArray = null;
            this.mTableSharedMemory = null;
            return this;
        }

        public Builder setTableByteArray(byte[] bytes) {
            this.mTableByteArray = bytes;
            this.mTableUri = null;
            this.mTableSharedMemory = null;
            return this;
        }

        public Builder setTableSharedMemory(SharedMemory sharedMemory) {
            this.mTableSharedMemory = sharedMemory;
            this.mTableUri = null;
            this.mTableByteArray = null;
            return this;
        }

        public TableResponse build() {
            return new TableResponse(this.mRequestId, this.mSequence, this.mResponseResult, this.mVersion, this.mSize, this.mTableUri, this.mTableByteArray, this.mTableSharedMemory);
        }
    }

    TableResponse(Parcel source) {
        super(2, source);
        String uriString = source.readString();
        this.mTableUri = uriString == null ? null : Uri.parse(uriString);
        this.mVersion = source.readInt();
        this.mSize = source.readInt();
        int arrayLength = source.readInt();
        if (arrayLength >= 0) {
            byte[] bArr = new byte[arrayLength];
            this.mTableByteArray = bArr;
            source.readByteArray(bArr);
        } else {
            this.mTableByteArray = null;
        }
        this.mTableSharedMemory = (SharedMemory) source.readTypedObject(SharedMemory.CREATOR);
    }

    public Uri getTableUri() {
        return this.mTableUri;
    }

    public byte[] getTableByteArray() {
        return this.mTableByteArray;
    }

    public SharedMemory getTableSharedMemory() {
        return this.mTableSharedMemory;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getSize() {
        return this.mSize;
    }

    @Override // android.media.p008tv.BroadcastInfoResponse, android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.p008tv.BroadcastInfoResponse, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        Uri uri = this.mTableUri;
        String uriString = uri == null ? null : uri.toString();
        dest.writeString(uriString);
        dest.writeInt(this.mVersion);
        dest.writeInt(this.mSize);
        byte[] bArr = this.mTableByteArray;
        if (bArr != null) {
            dest.writeInt(bArr.length);
            dest.writeByteArray(this.mTableByteArray);
        } else {
            dest.writeInt(-1);
        }
        dest.writeTypedObject(this.mTableSharedMemory, flags);
    }
}
