package com.android.systemui.tracing;

import android.content.Context;
import android.os.Trace;
import android.util.Log;
import com.android.internal.util.TraceBuffer;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceFileProto;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ProtoTracer implements Dumpable, FrameProtoTracer.ProtoTraceParams {
    public final Context mContext;
    public final FrameProtoTracer mProtoTracer = new FrameProtoTracer(this);

    public ProtoTracer(Context context, DumpManager dumpManager) {
        this.mContext = context;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ProtoTracer:");
        printWriter.print("    ");
        printWriter.println("enabled: " + this.mProtoTracer.mEnabled);
        printWriter.print("    ");
        printWriter.println("usagePct: " + (this.mProtoTracer.mBuffer.getBufferSize() / 1048576.0f));
        printWriter.print("    ");
        printWriter.println("file: " + new File(this.mContext.getFilesDir(), "sysui_trace.pb"));
    }

    public final void stop() {
        FrameProtoTracer frameProtoTracer = this.mProtoTracer;
        synchronized (frameProtoTracer.mLock) {
            if (frameProtoTracer.mEnabled) {
                frameProtoTracer.mEnabled = false;
                try {
                    try {
                        Trace.beginSection("ProtoTracer.writeToFile");
                        TraceBuffer traceBuffer = frameProtoTracer.mBuffer;
                        File file = frameProtoTracer.mTraceFile;
                        ((ProtoTracer) frameProtoTracer.mParams).getClass();
                        traceBuffer.writeTraceToFile(file, new SystemUiTraceFileProto());
                    } catch (IOException e) {
                        Log.e("FrameProtoTracer", "Unable to write buffer to file", e);
                    }
                } finally {
                    Trace.endSection();
                }
            }
        }
    }
}
