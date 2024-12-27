package com.android.server.usb;

import com.android.internal.util.dump.DualDumpOutputStream;

public final class DualOutputStreamDumpSink {
    public final DualDumpOutputStream mDumpOutputStream;
    public final long mId;

    public DualOutputStreamDumpSink(DualDumpOutputStream dualDumpOutputStream, long j) {
        this.mDumpOutputStream = dualDumpOutputStream;
        this.mId = j;
    }
}
