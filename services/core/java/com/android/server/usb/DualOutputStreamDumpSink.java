package com.android.server.usb;

import com.android.internal.util.dump.DualDumpOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DualOutputStreamDumpSink {
    public final DualDumpOutputStream mDumpOutputStream;
    public final long mId;

    public DualOutputStreamDumpSink(DualDumpOutputStream dualDumpOutputStream, long j) {
        this.mDumpOutputStream = dualDumpOutputStream;
        this.mId = j;
    }
}
