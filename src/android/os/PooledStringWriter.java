package android.os;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class PooledStringWriter {
    private int mNext;
    private final Parcel mOut;
    private final HashMap<String, Integer> mPool = new HashMap<>();
    private int mStart;

    public PooledStringWriter(Parcel parcel) {
        this.mOut = parcel;
        this.mStart = parcel.dataPosition();
        parcel.writeInt(0);
    }

    public void writeString(String str) {
        Integer num = this.mPool.get(str);
        if (num != null) {
            this.mOut.writeInt(num.intValue());
            return;
        }
        this.mPool.put(str, Integer.valueOf(this.mNext));
        this.mOut.writeInt(-(this.mNext + 1));
        this.mOut.writeString(str);
        this.mNext++;
    }

    public int getStringCount() {
        return this.mPool.size();
    }

    public void finish() {
        int iDataPosition = this.mOut.dataPosition();
        this.mOut.setDataPosition(this.mStart);
        this.mOut.writeInt(this.mNext);
        this.mOut.setDataPosition(iDataPosition);
    }
}
