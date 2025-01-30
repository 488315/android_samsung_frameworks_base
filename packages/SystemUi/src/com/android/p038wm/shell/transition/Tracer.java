package com.android.p038wm.shell.transition;

import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import com.android.internal.util.TraceBuffer;
import com.android.p038wm.shell.nano.HandlerMapping;
import com.android.p038wm.shell.nano.Transition;
import com.android.p038wm.shell.nano.WmShellTransitionTraceProto;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.google.protobuf.nano.MessageNano;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Tracer implements ShellCommandHandler.ShellCommandActionHandler {
    public final Object mEnabledLock = new Object();
    public final Map mHandlerIds;
    public final Map mHandlerUseCountInTrace;
    public final C41501 mProtoProvider;
    public final Map mRemovedFromTraceCallbacks;
    public final TraceBuffer mTraceBuffer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LogAndPrintln {
        /* renamed from: -$$Nest$sme, reason: not valid java name */
        public static void m2760$$Nest$sme(PrintWriter printWriter) {
            Log.e("ShellTransitionTracer", "Tracing is not supported on user builds.");
            if (printWriter != null) {
                printWriter.println("ERROR: Tracing is not supported on user builds.");
                printWriter.flush();
            }
        }

        private LogAndPrintln() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.internal.util.TraceBuffer$ProtoProvider, com.android.wm.shell.transition.Tracer$1] */
    public Tracer() {
        ?? r0 = new TraceBuffer.ProtoProvider(this) { // from class: com.android.wm.shell.transition.Tracer.1
            public final byte[] getBytes(Object obj) {
                return MessageNano.toByteArray((MessageNano) obj);
            }

            public final int getItemSize(Object obj) {
                return ((MessageNano) obj).getCachedSize();
            }

            public final void write(Object obj, Queue queue, OutputStream outputStream) {
                WmShellTransitionTraceProto wmShellTransitionTraceProto = (WmShellTransitionTraceProto) obj;
                wmShellTransitionTraceProto.transitions = (Transition[]) queue.toArray(new Transition[0]);
                outputStream.write(MessageNano.toByteArray(wmShellTransitionTraceProto));
            }
        };
        this.mProtoProvider = r0;
        this.mTraceBuffer = new TraceBuffer(15360, (TraceBuffer.ProtoProvider) r0, new Consumer() { // from class: com.android.wm.shell.transition.Tracer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                HashMap hashMap = (HashMap) Tracer.this.mRemovedFromTraceCallbacks;
                if (hashMap.containsKey(obj)) {
                    ((Runnable) hashMap.get(obj)).run();
                    hashMap.remove(obj);
                }
            }
        });
        this.mRemovedFromTraceCallbacks = new HashMap();
        this.mHandlerIds = new HashMap();
        this.mHandlerUseCountInTrace = new HashMap();
    }

    public final void logDispatched(int i, final Transitions.TransitionHandler transitionHandler) {
        int i2;
        Map map = this.mHandlerIds;
        if (((HashMap) map).containsKey(transitionHandler)) {
            i2 = ((Integer) ((HashMap) map).get(transitionHandler)).intValue();
        } else {
            int size = ((HashMap) map).size() + 1;
            ((HashMap) map).put(transitionHandler, Integer.valueOf(size));
            i2 = size;
        }
        Transition transition = new Transition();
        transition.f455id = i;
        transition.dispatchTimeNs = SystemClock.elapsedRealtimeNanos();
        transition.handler = i2;
        Map map2 = this.mHandlerUseCountInTrace;
        ((HashMap) map2).put(transitionHandler, Integer.valueOf(((Integer) ((HashMap) map2).getOrDefault(transitionHandler, 0)).intValue() + 1));
        ((HashMap) this.mRemovedFromTraceCallbacks).put(transition, new Runnable() { // from class: com.android.wm.shell.transition.Tracer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Tracer tracer = Tracer.this;
                Transitions.TransitionHandler transitionHandler2 = transitionHandler;
                ((HashMap) tracer.mHandlerUseCountInTrace).put(transitionHandler2, Integer.valueOf(((Integer) r0.get(transitionHandler2)).intValue() - 1));
            }
        });
        this.mTraceBuffer.add(transition);
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        String str = strArr[0];
        str.getClass();
        str.hashCode();
        switch (str) {
            case "save-for-bugreport":
                if (Build.IS_USER) {
                    LogAndPrintln.m2760$$Nest$sme(printWriter);
                } else {
                    Trace.beginSection("TransitionTracer#saveForBugreport");
                    synchronized (this.mEnabledLock) {
                        writeTraceToFileLocked(printWriter, new File("/data/misc/wmtrace/shell_transition_trace.winscope"));
                    }
                    Trace.endSection();
                }
                return true;
            case "stop":
                File file = new File("/data/misc/wmtrace/shell_transition_trace.winscope");
                if (Build.IS_USER) {
                    LogAndPrintln.m2760$$Nest$sme(printWriter);
                } else {
                    Trace.beginSection("Tracer#stopTrace");
                    Log.i("ShellTransitionTracer", "Stopping shell transition trace.");
                    if (printWriter != null) {
                        printWriter.println("Stopping shell transition trace.");
                        printWriter.flush();
                    }
                    synchronized (this.mEnabledLock) {
                        writeTraceToFileLocked(printWriter, file);
                        this.mTraceBuffer.resetBuffer();
                        this.mTraceBuffer.setCapacity(15360);
                    }
                    Trace.endSection();
                }
                return true;
            case "start":
                if (Build.IS_USER) {
                    LogAndPrintln.m2760$$Nest$sme(printWriter);
                } else {
                    Trace.beginSection("Tracer#startTrace");
                    Log.i("ShellTransitionTracer", "Starting shell transition trace.");
                    if (printWriter != null) {
                        printWriter.println("Starting shell transition trace.");
                        printWriter.flush();
                    }
                    synchronized (this.mEnabledLock) {
                        this.mTraceBuffer.resetBuffer();
                        this.mTraceBuffer.setCapacity(5120000);
                    }
                    Trace.endSection();
                }
                return true;
            default:
                KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
                printShellCommandHelp(printWriter, "");
                return false;
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println(str + NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        printWriter.println(str + "  Start tracing the transitions.");
        printWriter.println(str + "stop");
        printWriter.println(str + "  Stop tracing the transitions.");
        printWriter.println(str + "save-for-bugreport");
        printWriter.println(str + "  Flush in memory transition trace to file.");
    }

    public final void writeHandlerMappingToProto(WmShellTransitionTraceProto wmShellTransitionTraceProto) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = (HashMap) this.mHandlerUseCountInTrace;
        for (Transitions.TransitionHandler transitionHandler : hashMap.keySet()) {
            if (((Integer) hashMap.get(transitionHandler)).intValue() > 0) {
                HandlerMapping handlerMapping = new HandlerMapping();
                handlerMapping.f454id = ((Integer) ((HashMap) this.mHandlerIds).get(transitionHandler)).intValue();
                handlerMapping.name = transitionHandler.getClass().getName();
                arrayList.add(handlerMapping);
            }
        }
        wmShellTransitionTraceProto.handlerMappings = (HandlerMapping[]) arrayList.toArray(new HandlerMapping[0]);
    }

    public final void writeTraceToFileLocked(PrintWriter printWriter, File file) {
        Trace.beginSection("TransitionTracer#writeTraceToFileLocked");
        try {
            WmShellTransitionTraceProto wmShellTransitionTraceProto = new WmShellTransitionTraceProto();
            wmShellTransitionTraceProto.magicNumber = 4990904633914510679L;
            writeHandlerMappingToProto(wmShellTransitionTraceProto);
            wmShellTransitionTraceProto.realToElapsedTimeOffsetNanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos();
            String str = "Writing file to " + file.getAbsolutePath() + " from process " + Process.myPid();
            Log.i("ShellTransitionTracer", str);
            if (printWriter != null) {
                printWriter.println(str);
                printWriter.flush();
            }
            this.mTraceBuffer.writeTraceToFile(file, wmShellTransitionTraceProto);
        } catch (IOException e) {
            Log.e("ShellTransitionTracer", "Unable to write buffer to file", e);
            if (printWriter != null) {
                printWriter.println("ERROR: Unable to write buffer to file ::\n " + e);
                printWriter.flush();
            }
        }
        Trace.endSection();
    }
}
