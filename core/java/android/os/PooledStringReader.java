package android.os;

public class PooledStringReader {
    private final Parcel mIn;
    private final String[] mPool;

    public PooledStringReader(Parcel in) {
        this.mIn = in;
        int size = in.readInt();
        this.mPool = new String[size];
    }

    public int getStringCount() {
        return this.mPool.length;
    }

    public String readString() {
        int idx = this.mIn.readInt();
        if (idx >= 0) {
            return this.mPool[idx];
        }
        int idx2 = (-idx) - 1;
        String str = this.mIn.readString();
        this.mPool[idx2] = str;
        return str;
    }
}
