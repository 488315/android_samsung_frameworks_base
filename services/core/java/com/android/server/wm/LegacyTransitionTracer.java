package com.android.server.wm;

import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.TraceBuffer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LegacyTransitionTracer implements TransitionTracer {
    public final TraceBuffer mTraceBuffer = new TraceBuffer(15360);
    public final Object mEnabledLock = new Object();
    public volatile boolean mActiveTracingEnabled = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LogAndPrintln {
        /* renamed from: -$$Nest$sme, reason: not valid java name */
        public static void m1064$$Nest$sme(PrintWriter printWriter) {
            Log.e("TransitionTracer", "Tracing is not supported on user builds.");
            if (printWriter != null) {
                printWriter.println("ERROR: Tracing is not supported on user builds.");
                printWriter.flush();
            }
        }
    }

    public final void dumpTransitionTargetsToProto(
            ProtoOutputStream protoOutputStream, Transition transition, ArrayList arrayList) {
        Trace.beginSection("TransitionTracer#dumpTransitionTargetsToProto");
        if (this.mActiveTracingEnabled) {
            protoOutputStream.write(1120986464257L, transition.mSyncId);
        }
        protoOutputStream.write(1120986464263L, transition.mType);
        protoOutputStream.write(1120986464265L, transition.mFlags);
        for (int i = 0; i < arrayList.size(); i++) {
            long start = protoOutputStream.start(2246267895816L);
            Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) arrayList.get(i);
            int layerId =
                    changeInfo.mContainer.mSurfaceControl.isValid()
                            ? changeInfo.mContainer.mSurfaceControl.getLayerId()
                            : -1;
            protoOutputStream.write(1120986464257L, changeInfo.mReadyMode);
            protoOutputStream.write(1120986464260L, changeInfo.mReadyFlags);
            protoOutputStream.write(1120986464258L, layerId);
            if (this.mActiveTracingEnabled) {
                protoOutputStream.write(
                        1120986464259L, System.identityHashCode(changeInfo.mContainer));
            }
            protoOutputStream.end(start);
        }
        Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public final boolean isTracing() {
        return this.mActiveTracingEnabled;
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logAbortedTransition(Transition transition) {
        try {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, transition.mSyncId);
            protoOutputStream.write(1112396529674L, transition.mLogger.mAbortTimeNs);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (Exception e) {
            Log.e("TransitionTracer", "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logFinishedTransition(Transition transition) {
        try {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, transition.mSyncId);
            protoOutputStream.write(1112396529670L, transition.mLogger.mFinishTimeNs);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (Exception e) {
            Log.e("TransitionTracer", "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logRemovingStartingWindow(StartingData startingData) {
        if (startingData.mTransitionId == 0) {
            return;
        }
        try {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, startingData.mTransitionId);
            protoOutputStream.write(1112396529675L, SystemClock.elapsedRealtimeNanos());
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (Exception e) {
            Log.e("TransitionTracer", "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void logSentTransition(Transition transition, ArrayList arrayList) {
        try {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            int i = transition.mSyncId;
            TransitionController.Logger logger = transition.mLogger;
            protoOutputStream.write(1120986464257L, i);
            protoOutputStream.write(1112396529668L, logger.mCreateTimeNs);
            protoOutputStream.write(1112396529669L, logger.mSendTimeNs);
            protoOutputStream.write(1116691496962L, transition.getStartTransaction().getId());
            protoOutputStream.write(1116691496963L, transition.getFinishTransaction().getId());
            dumpTransitionTargetsToProto(protoOutputStream, transition, arrayList);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (Exception e) {
            Log.e("TransitionTracer", "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void saveForBugreport(PrintWriter printWriter) {
        if (Build.IS_USER) {
            LogAndPrintln.m1064$$Nest$sme(printWriter);
            return;
        }
        Trace.beginSection("TransitionTracer#saveForBugreport");
        synchronized (this.mEnabledLock) {
            writeTraceToFileLocked(
                    new File("/data/misc/wmtrace/wm_transition_trace.winscope"), printWriter);
        }
        Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void startTrace(PrintWriter printWriter) {
        if (Build.IS_USER) {
            LogAndPrintln.m1064$$Nest$sme(printWriter);
            return;
        }
        Trace.beginSection("TransitionTracer#startTrace");
        Log.i("TransitionTracer", "Starting shell transition trace.");
        if (printWriter != null) {
            printWriter.println("Starting shell transition trace.");
            printWriter.flush();
        }
        synchronized (this.mEnabledLock) {
            this.mActiveTracingEnabled = true;
            this.mTraceBuffer.resetBuffer();
            this.mTraceBuffer.setCapacity(5120000);
        }
        Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public final void stopTrace(PrintWriter printWriter) {
        File file = new File("/data/misc/wmtrace/wm_transition_trace.winscope");
        if (Build.IS_USER) {
            LogAndPrintln.m1064$$Nest$sme(printWriter);
            return;
        }
        Trace.beginSection("TransitionTracer#stopTrace");
        Log.i("TransitionTracer", "Stopping shell transition trace.");
        if (printWriter != null) {
            printWriter.println("Stopping shell transition trace.");
            printWriter.flush();
        }
        synchronized (this.mEnabledLock) {
            this.mActiveTracingEnabled = false;
            writeTraceToFileLocked(file, printWriter);
            this.mTraceBuffer.resetBuffer();
            this.mTraceBuffer.setCapacity(15360);
        }
        Trace.endSection();
    }

    public final void writeTraceToFileLocked(File file, PrintWriter printWriter) {
        Trace.beginSection("TransitionTracer#writeTraceToFileLocked");
        try {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(64);
            protoOutputStream.write(1125281431553L, 4990904633914184276L);
            protoOutputStream.write(
                    1125281431555L,
                    TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis())
                            - SystemClock.elapsedRealtimeNanos());
            String str =
                    "Writing file to "
                            + file.getAbsolutePath()
                            + " from process "
                            + Process.myPid();
            Log.i("TransitionTracer", str);
            if (printWriter != null) {
                printWriter.println(str);
                printWriter.flush();
            }
            this.mTraceBuffer.writeTraceToFile(file, protoOutputStream);
        } catch (IOException e) {
            Log.e("TransitionTracer", "Unable to write buffer to file", e);
            if (printWriter != null) {
                printWriter.println("ERROR: Unable to write buffer to file ::\n " + e);
                printWriter.flush();
            }
        }
        Trace.endSection();
    }
}
