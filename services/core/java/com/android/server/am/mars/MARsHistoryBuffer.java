package com.android.server.am.mars;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MARsHistoryBuffer {
    public String[] buffer;
    public int pointer;
    public int size;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsHistoryBufferHolder {
        public static final MARsHistoryBuffer INSTANCE;

        static {
            MARsHistoryBuffer mARsHistoryBuffer = new MARsHistoryBuffer();
            mARsHistoryBuffer.size = 0;
            mARsHistoryBuffer.pointer = 0;
            INSTANCE = mARsHistoryBuffer;
        }
    }

    public final synchronized void put(String str) {
        String[] strArr = this.buffer;
        int i = this.pointer;
        int i2 = i + 1;
        this.pointer = i2;
        strArr[i] = str;
        if (i2 >= this.size) {
            MARsHistoryLog.MARsHistoryLogHolder.INSTANCE.saveLogToFile(true, false);
            this.pointer = 0;
        }
    }
}
