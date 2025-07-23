package android.os;

/* loaded from: classes3.dex */
public class PooledStringReader {
    private final Parcel mIn;
    private final String[] mPool;

    public PooledStringReader(Parcel parcel) {
        this.mIn = parcel;
        this.mPool = new String[parcel.readInt()];
    }

    public int getStringCount() {
        return this.mPool.length;
    }

    public String readString() {
        int readInt = this.mIn.readInt();
        if (readInt >= 0) {
            return this.mPool[readInt];
        }
        String readString = this.mIn.readString();
        this.mPool[(-readInt) - 1] = readString;
        return readString;
    }
}
