package android.media;

import android.os.Parcel;

/* loaded from: classes2.dex */
public final class TimedMetaData {
    private static final String TAG = "TimedMetaData";
    private byte[] mMetaData;
    private long mTimestampUs;

    static TimedMetaData createTimedMetaDataFromParcel(Parcel parcel) {
        return new TimedMetaData(parcel);
    }

    private TimedMetaData(Parcel parcel) {
        if (!parseParcel(parcel)) {
            throw new IllegalArgumentException("parseParcel() fails");
        }
    }

    public TimedMetaData(long j, byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("null metaData is not allowed");
        }
        this.mTimestampUs = j;
        this.mMetaData = bArr;
    }

    public long getTimestamp() {
        return this.mTimestampUs;
    }

    public byte[] getMetaData() {
        return this.mMetaData;
    }

    private boolean parseParcel(Parcel parcel) {
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        this.mTimestampUs = parcel.readLong();
        byte[] bArr = new byte[parcel.readInt()];
        this.mMetaData = bArr;
        parcel.readByteArray(bArr);
        return true;
    }
}
