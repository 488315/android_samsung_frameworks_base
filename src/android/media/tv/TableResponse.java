package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;

/* loaded from: classes3.dex */
public final class TableResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<TableResponse> CREATOR = new Parcelable.Creator<TableResponse>() { // from class: android.media.tv.TableResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TableResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableResponse[] newArray(int i) {
            return new TableResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 2;
    private final int mSize;
    private final byte[] mTableByteArray;
    private final SharedMemory mTableSharedMemory;
    private final Uri mTableUri;
    private final int mVersion;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TableResponse createFromParcelBody(Parcel parcel) {
        return new TableResponse(parcel);
    }

    @Deprecated
    public TableResponse(int i, int i2, int i3, Uri uri, int i4, int i5) {
        super(2, i, i2, i3);
        this.mVersion = i4;
        this.mSize = i5;
        this.mTableUri = uri;
        this.mTableByteArray = null;
        this.mTableSharedMemory = null;
    }

    private TableResponse(int i, int i2, int i3, int i4, int i5, Uri uri, byte[] bArr, SharedMemory sharedMemory) {
        super(2, i, i2, i3);
        this.mVersion = i4;
        this.mSize = i5;
        this.mTableUri = uri;
        this.mTableByteArray = bArr;
        this.mTableSharedMemory = sharedMemory;
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

        public Builder(int i, int i2, int i3, int i4, int i5) {
            this.mRequestId = i;
            this.mSequence = i2;
            this.mResponseResult = i3;
            this.mVersion = i4;
            this.mSize = i5;
        }

        public Builder setTableUri(Uri uri) {
            this.mTableUri = uri;
            this.mTableByteArray = null;
            this.mTableSharedMemory = null;
            return this;
        }

        public Builder setTableByteArray(byte[] bArr) {
            this.mTableByteArray = bArr;
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

    TableResponse(Parcel parcel) {
        super(2, parcel);
        String readString = parcel.readString();
        this.mTableUri = readString == null ? null : Uri.parse(readString);
        this.mVersion = parcel.readInt();
        this.mSize = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            byte[] bArr = new byte[readInt];
            this.mTableByteArray = bArr;
            parcel.readByteArray(bArr);
        } else {
            this.mTableByteArray = null;
        }
        this.mTableSharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
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

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        Uri uri = this.mTableUri;
        parcel.writeString(uri == null ? null : uri.toString());
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mSize);
        byte[] bArr = this.mTableByteArray;
        if (bArr != null) {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mTableByteArray);
        } else {
            parcel.writeInt(-1);
        }
        parcel.writeTypedObject(this.mTableSharedMemory, i);
    }
}
