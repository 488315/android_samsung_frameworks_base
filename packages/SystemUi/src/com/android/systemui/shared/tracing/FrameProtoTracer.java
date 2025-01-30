package com.android.systemui.shared.tracing;

import android.os.SystemClock;
import android.view.Choreographer;
import com.android.internal.util.TraceBuffer;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceEntryProto;
import com.android.systemui.tracing.nano.SystemUiTraceFileProto;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.google.protobuf.nano.MessageNano;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FrameProtoTracer implements Choreographer.FrameCallback {
    public final TraceBuffer mBuffer;
    public Choreographer mChoreographer;
    public volatile boolean mEnabled;
    public boolean mFrameScheduled;
    public final ProtoTraceParams mParams;
    public final C24971 mProvider;
    public final File mTraceFile;
    public final Object mLock = new Object();
    public final Queue mPool = new ArrayDeque();
    public final ArrayList mTraceables = new ArrayList();
    public final ArrayList mTmpTraceables = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ProtoTraceParams {
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.internal.util.TraceBuffer$ProtoProvider, com.android.systemui.shared.tracing.FrameProtoTracer$1] */
    public FrameProtoTracer(ProtoTraceParams protoTraceParams) {
        ?? r0 = new TraceBuffer.ProtoProvider() { // from class: com.android.systemui.shared.tracing.FrameProtoTracer.1
            public final byte[] getBytes(Object obj) {
                ((ProtoTracer) FrameProtoTracer.this.mParams).getClass();
                return MessageNano.toByteArray((MessageNano) obj);
            }

            public final int getItemSize(Object obj) {
                ((ProtoTracer) FrameProtoTracer.this.mParams).getClass();
                return ((MessageNano) obj).getCachedSize();
            }

            public final void write(Object obj, Queue queue, OutputStream outputStream) {
                ((ProtoTracer) FrameProtoTracer.this.mParams).getClass();
                SystemUiTraceFileProto systemUiTraceFileProto = (SystemUiTraceFileProto) obj;
                systemUiTraceFileProto.magicNumber = 4851032422572317011L;
                systemUiTraceFileProto.entry = (SystemUiTraceEntryProto[]) queue.toArray(new SystemUiTraceEntryProto[0]);
                outputStream.write(MessageNano.toByteArray(systemUiTraceFileProto));
            }
        };
        this.mProvider = r0;
        this.mParams = protoTraceParams;
        this.mBuffer = new TraceBuffer(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, (TraceBuffer.ProtoProvider) r0, new Consumer() { // from class: com.android.systemui.shared.tracing.FrameProtoTracer.2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ArrayDeque) FrameProtoTracer.this.mPool).add(obj);
            }
        });
        ProtoTracer protoTracer = (ProtoTracer) protoTraceParams;
        protoTracer.getClass();
        this.mTraceFile = new File(protoTracer.mContext.getFilesDir(), "sysui_trace.pb");
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        logState();
    }

    public final void logState() {
        synchronized (this.mLock) {
            this.mTmpTraceables.addAll(this.mTraceables);
        }
        TraceBuffer traceBuffer = this.mBuffer;
        ProtoTraceParams protoTraceParams = this.mParams;
        Object poll = ((ArrayDeque) this.mPool).poll();
        ArrayList arrayList = this.mTmpTraceables;
        ((ProtoTracer) protoTraceParams).getClass();
        SystemUiTraceEntryProto systemUiTraceEntryProto = (SystemUiTraceEntryProto) poll;
        if (systemUiTraceEntryProto == null) {
            systemUiTraceEntryProto = new SystemUiTraceEntryProto();
        }
        systemUiTraceEntryProto.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        SystemUiTraceProto systemUiTraceProto = systemUiTraceEntryProto.systemUi;
        if (systemUiTraceProto == null) {
            systemUiTraceProto = new SystemUiTraceProto();
        }
        systemUiTraceEntryProto.systemUi = systemUiTraceProto;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((ProtoTraceable) it.next()).writeToProto(systemUiTraceEntryProto.systemUi);
        }
        traceBuffer.add(systemUiTraceEntryProto);
        this.mTmpTraceables.clear();
        this.mFrameScheduled = false;
    }
}
