package android.view;

import android.Manifest;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Debug;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.GcUtils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class SurfaceControlRegistry {
    private static final int DUMP_LIMIT = 256;
    private static final int MAX_LAYERS_REPORTING_THRESHOLD = 1024;
    private static final int RESET_REPORTING_THRESHOLD = 256;
    private static final String TAG = "SurfaceControlRegistry";
    static boolean sCallStackDebuggingEnabled;
    static boolean sCallStackDebuggingInitialized;
    private static String sCallStackDebuggingMatchCall;
    private static String sCallStackDebuggingMatchName;
    private static volatile SurfaceControlRegistry sProcessRegistry;
    private boolean mHasReportedExceedingMaxThreshold;
    private int mMaxLayersReportingThreshold;
    private Reporter mReporter;
    private int mResetReportingThreshold;
    private final WeakHashMap<SurfaceControl, Long> mSurfaceControls;
    private static final SurfaceControlRegistry NO_OP_REGISTRY = new NoOpRegistry();
    private static final Object sLock = new Object();
    private static final DefaultReporter sDefaultReporter = new DefaultReporter();

    public interface Reporter {
        void onMaxLayersExceeded(
                WeakHashMap<SurfaceControl, Long> weakHashMap, int i, PrintWriter printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DefaultReporter implements Reporter {
        private DefaultReporter() {}

        @Override // android.view.SurfaceControlRegistry.Reporter
        public void onMaxLayersExceeded(
                WeakHashMap<SurfaceControl, Long> surfaceControls, int limit, PrintWriter pw) {
            long now = SystemClock.elapsedRealtime();
            ArrayList<Map.Entry<SurfaceControl, Long>> entries = new ArrayList<>();
            Iterator<Map.Entry<SurfaceControl, Long>> it = surfaceControls.entrySet().iterator();
            while (it.hasNext()) {
                entries.add(it.next());
            }
            entries.sort(
                    new Comparator() { // from class:
                                       // android.view.SurfaceControlRegistry$DefaultReporter$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return SurfaceControlRegistry.DefaultReporter
                                    .lambda$onMaxLayersExceeded$0(
                                            (Map.Entry) obj, (Map.Entry) obj2);
                        }
                    });
            int size = Math.min(entries.size(), limit);
            pw.println(SurfaceControlRegistry.TAG);
            pw.println("----------------------");
            pw.println("Listing oldest " + size + " of " + surfaceControls.size());
            for (int i = 0; i < size; i++) {
                Map.Entry<SurfaceControl, Long> entry = entries.get(i);
                SurfaceControl sc = entry.getKey();
                if (sc != null) {
                    long timeRegistered = entry.getValue().longValue();
                    pw.print("  ");
                    pw.print(sc.getName());
                    pw.print(" (" + sc.getCallsite() + NavigationBarInflaterView.KEY_CODE_END);
                    pw.println(" [" + ((now - timeRegistered) / 1000) + "s ago]");
                }
            }
        }

        static /* synthetic */ int lambda$onMaxLayersExceeded$0(Map.Entry o1, Map.Entry o2) {
            return (int) (((Long) o1.getValue()).longValue() - ((Long) o2.getValue()).longValue());
        }
    }

    private SurfaceControlRegistry() {
        this.mMaxLayersReportingThreshold = 1024;
        this.mResetReportingThreshold = 256;
        this.mHasReportedExceedingMaxThreshold = false;
        this.mReporter = sDefaultReporter;
        this.mSurfaceControls = new WeakHashMap<>(256);
    }

    public void setReportingThresholds(
            int maxLayersReportingThreshold, int resetReportingThreshold, Reporter reporter) {
        synchronized (sLock) {
            if (maxLayersReportingThreshold <= 0
                    || resetReportingThreshold >= maxLayersReportingThreshold) {
                throw new IllegalArgumentException(
                        "Expected maxLayersReportingThreshold ("
                                + maxLayersReportingThreshold
                                + ") to be > 0 and resetReportingThreshold ("
                                + resetReportingThreshold
                                + ") to be < maxLayersReportingThreshold");
            }
            if (reporter == null) {
                throw new IllegalArgumentException("Expected non-null reporter");
            }
            this.mMaxLayersReportingThreshold = maxLayersReportingThreshold;
            this.mResetReportingThreshold = resetReportingThreshold;
            this.mHasReportedExceedingMaxThreshold = false;
            this.mReporter = reporter;
        }
    }

    public void setCallStackDebuggingParams(String matchName, String matchCall) {
        sCallStackDebuggingMatchName = matchName.toLowerCase();
        sCallStackDebuggingMatchCall = matchCall.toLowerCase();
    }

    public static void createProcessInstance(Context context) {
        if (context.checkSelfPermission(Manifest.permission.READ_FRAME_BUFFER) != 0) {
            throw new SecurityException("Expected caller to hold READ_FRAME_BUFFER");
        }
        synchronized (sLock) {
            if (sProcessRegistry == null) {
                sProcessRegistry = new SurfaceControlRegistry();
            }
        }
    }

    public static void destroyProcessInstance() {
        synchronized (sLock) {
            if (sProcessRegistry == null) {
                return;
            }
            sProcessRegistry = null;
        }
    }

    public static SurfaceControlRegistry getProcessInstance() {
        SurfaceControlRegistry surfaceControlRegistry;
        synchronized (sLock) {
            surfaceControlRegistry = sProcessRegistry != null ? sProcessRegistry : NO_OP_REGISTRY;
        }
        return surfaceControlRegistry;
    }

    void add(SurfaceControl sc) {
        synchronized (sLock) {
            this.mSurfaceControls.put(sc, Long.valueOf(SystemClock.elapsedRealtime()));
            if (!this.mHasReportedExceedingMaxThreshold
                    && this.mSurfaceControls.size() >= this.mMaxLayersReportingThreshold) {
                PrintWriter pw = new PrintWriter((OutputStream) System.out, true);
                this.mReporter.onMaxLayersExceeded(this.mSurfaceControls, 256, pw);
                this.mHasReportedExceedingMaxThreshold = true;
            }
        }
    }

    void remove(SurfaceControl sc) {
        synchronized (sLock) {
            this.mSurfaceControls.remove(sc);
            if (this.mHasReportedExceedingMaxThreshold
                    && this.mSurfaceControls.size() <= this.mResetReportingThreshold) {
                this.mHasReportedExceedingMaxThreshold = false;
            }
        }
    }

    public int hashCode() {
        int hashCode;
        synchronized (sLock) {
            hashCode = this.mSurfaceControls.keySet().hashCode();
        }
        return hashCode;
    }

    static final void initializeCallStackDebugging() {
        if (sCallStackDebuggingInitialized) {
            return;
        }
        boolean z = Build.IS_DEBUGGABLE;
        boolean z2 = true;
        sCallStackDebuggingInitialized = true;
        sCallStackDebuggingMatchCall =
                SystemProperties.get("persist.wm.debug.sc.tx.log_match_call", null).toLowerCase();
        sCallStackDebuggingMatchName =
                SystemProperties.get("persist.wm.debug.sc.tx.log_match_name", null).toLowerCase();
        if (sCallStackDebuggingMatchCall.isEmpty() && sCallStackDebuggingMatchName.isEmpty()) {
            z2 = false;
        }
        sCallStackDebuggingEnabled = z2;
        if (sCallStackDebuggingEnabled) {
            Log.d(
                    TAG,
                    "Enabling transaction call stack debugging: matchCall="
                            + sCallStackDebuggingMatchCall
                            + " matchName="
                            + sCallStackDebuggingMatchName);
        }
    }

    final void checkCallStackDebugging(
            String call, SurfaceControl.Transaction tx, SurfaceControl sc, String details) {
        checkCallStackDebugging(call, tx, sc, details, false);
    }

    final void checkCallStackDebugging(
            String call,
            SurfaceControl.Transaction tx,
            SurfaceControl sc,
            String details,
            boolean forceEnabled) {
        if (sCallStackDebuggingEnabled || forceEnabled) {
            if (!matchesForCallStackDebugging(sc != null ? sc.getName() : null, call)) {
                return;
            }
            String debugMsg = "";
            String txMsg = tx != null ? "tx=" + tx.getId() + " " : "";
            String scMsg = sc != null ? " sc=" + sc.getName() + "" : "";
            if (forceEnabled) {
                if (tx != null && !TextUtils.isEmpty(tx.mDebugName)) {
                    debugMsg = " t=" + tx.mDebugName;
                }
                String msg =
                        details != null
                                ? call + "," + debugMsg + scMsg + ", " + details
                                : call + "," + debugMsg + scMsg;
                Log.i(TAG, msg + ", caller=" + Debug.getCallers(6));
                return;
            }
            String msg2 =
                    details != null
                            ? call + " (" + txMsg + scMsg + ") " + details
                            : call + " (" + txMsg + scMsg + NavigationBarInflaterView.KEY_CODE_END;
            Log.e(TAG, msg2, new Throwable());
        }
    }

    public final boolean matchesForCallStackDebugging(String name, String call) {
        if (sCallStackDebuggingMatchCall == null || sCallStackDebuggingMatchName == null) {
            return false;
        }
        boolean matchCall = !sCallStackDebuggingMatchCall.isEmpty();
        if (matchCall && !sCallStackDebuggingMatchCall.contains(call.toLowerCase())) {
            return false;
        }
        boolean matchName = !sCallStackDebuggingMatchName.isEmpty();
        if (!matchName) {
            return true;
        }
        if (name == null) {
            return false;
        }
        return sCallStackDebuggingMatchName.contains(name.toLowerCase())
                || name.toLowerCase().contains(sCallStackDebuggingMatchName);
    }

    static final boolean isCallStackDebuggingEnabled() {
        return sCallStackDebuggingEnabled;
    }

    private static void runGcAndFinalizers() {
        long t = SystemClock.elapsedRealtime();
        GcUtils.runGcAndFinalizersSync();
        Log.i(TAG, "Ran gc and finalizers (" + (SystemClock.elapsedRealtime() - t) + "ms)");
    }

    public static void dump(int limit, boolean runGc, PrintWriter pw) {
        if (runGc) {
            runGcAndFinalizers();
        }
        synchronized (sLock) {
            if (sProcessRegistry != null) {
                sDefaultReporter.onMaxLayersExceeded(sProcessRegistry.mSurfaceControls, limit, pw);
                pw.println("sCallStackDebuggingInitialized=" + sCallStackDebuggingInitialized);
                pw.println("sCallStackDebuggingEnabled=" + sCallStackDebuggingEnabled);
                pw.println("sCallStackDebuggingMatchName=" + sCallStackDebuggingMatchName);
                pw.println("sCallStackDebuggingMatchCall=" + sCallStackDebuggingMatchCall);
            }
        }
    }

    private static class NoOpRegistry extends SurfaceControlRegistry {
        private NoOpRegistry() {
            super();
        }

        @Override // android.view.SurfaceControlRegistry
        public void setReportingThresholds(
                int maxLayersReportingThreshold, int resetReportingThreshold, Reporter reporter) {}

        @Override // android.view.SurfaceControlRegistry
        void add(SurfaceControl sc) {}

        @Override // android.view.SurfaceControlRegistry
        void remove(SurfaceControl sc) {}
    }
}
