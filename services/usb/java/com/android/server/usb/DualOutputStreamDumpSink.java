package com.android.server.usb;

import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.utils.EventLogger;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class DualOutputStreamDumpSink implements EventLogger.DumpSink {
    public final DualDumpOutputStream mDumpOutputStream;
    public final long mId;

    public DualOutputStreamDumpSink(DualDumpOutputStream dualDumpOutputStream, long j) {
        this.mDumpOutputStream = dualDumpOutputStream;
        this.mId = j;
    }

    @Override // com.android.server.utils.EventLogger.DumpSink
    public void sink(String str, List list) {
        this.mDumpOutputStream.write("USB Event Log", this.mId, str);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mDumpOutputStream.write("USB Event", this.mId, ((EventLogger.Event) it.next()).toString());
        }
    }
}
