package com.android.server.am.mars;


public final class MARsHistoryBuffer {
    public String[] buffer;
    public int pointer;
    public int size;

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
