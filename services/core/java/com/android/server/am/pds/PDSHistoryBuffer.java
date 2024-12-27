package com.android.server.am.pds;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PDSHistoryBuffer {
    public String[] buffer;
    public int pointer;
    public int size;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
