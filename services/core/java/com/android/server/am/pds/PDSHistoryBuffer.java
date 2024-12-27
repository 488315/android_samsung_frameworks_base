package com.android.server.am.pds;

public final class PDSHistoryBuffer {
    public String[] buffer;
    public int pointer;
    public int size;

    public abstract class PDSHistoryBufferHolder {
        public static final PDSHistoryBuffer INSTANCE;

        static {
            PDSHistoryBuffer pDSHistoryBuffer = new PDSHistoryBuffer();
            pDSHistoryBuffer.size = 0;
            pDSHistoryBuffer.pointer = 0;
            INSTANCE = pDSHistoryBuffer;
        }
    }
}
