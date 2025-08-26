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
        int i = this.mIn.readInt();
        if (i >= 0) {
            return this.mPool[i];
        }
        String string = this.mIn.readString();
        this.mPool[(-i) - 1] = string;
        return string;
    }
}
