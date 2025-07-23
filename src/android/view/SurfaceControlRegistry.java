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
import android.view.SurfaceControl;
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
    static final String APPLY = "apply";
    private static final int DUMP_LIMIT = 256;
    private static final int MAX_LAYERS_REPORTING_THRESHOLD = 1024;
    private static final SurfaceControlRegistry NO_OP_REGISTRY;
    private static final int RESET_REPORTING_THRESHOLD = 256;
    private static final String TAG = "SurfaceControlRegistry";
    static boolean sCallStackDebuggingEnabled;
    static boolean sCallStackDebuggingInitialized;
    private static String sCallStackDebuggingMatchCall;
    private static String sCallStackDebuggingMatchName;
    private static final DefaultReporter sDefaultReporter;
    private static final Object sLock = new Object();
    static boolean sLogAllTxCallsOnApply;
    private static volatile SurfaceControlRegistry sProcessRegistry;
    private boolean mHasReportedExceedingMaxThreshold;
    private int mMaxLayersReportingThreshold;
    private Reporter mReporter;
    private int mResetReportingThreshold;
    private final WeakHashMap<SurfaceControl, Long> mSurfaceControls;

    public interface Reporter {
        void onMaxLayersExceeded(WeakHashMap<SurfaceControl, Long> weakHashMap, int i, PrintWriter printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DefaultReporter implements Reporter {
        private DefaultReporter() {
        }

        @Override // android.view.SurfaceControlRegistry.Reporter
        public void onMaxLayersExceeded(WeakHashMap<SurfaceControl, Long> weakHashMap, int i, PrintWriter printWriter) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<SurfaceControl, Long>> it = weakHashMap.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            arrayList.sort(new Comparator() { // from class: android.view.SurfaceControlRegistry$DefaultReporter$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compare;
                    compare = Long.compare(((Long) ((Map.Entry) obj).getValue()).longValue(), ((Long) ((Map.Entry) obj2).getValue()).longValue());
                    return compare;
                }
            });
            int min = Math.min(arrayList.size(), i);
            printWriter.println(SurfaceControlRegistry.TAG);
            printWriter.println("----------------------");
            printWriter.println("Listing oldest " + min + " of " + weakHashMap.size());
            for (int i2 = 0; i2 < min; i2++) {
                Map.Entry entry = (Map.Entry) arrayList.get(i2);
                SurfaceControl surfaceControl = (SurfaceControl) entry.getKey();
                if (surfaceControl != null) {
                    long longValue = ((Long) entry.getValue()).longValue();
                    printWriter.print("  ");
                    printWriter.print(surfaceControl.getName());
                    printWriter.print(" (" + surfaceControl.getCallsite() + NavigationBarInflaterView.KEY_CODE_END);
                    printWriter.println(" [" + ((elapsedRealtime - longValue) / 1000) + "s ago]");
                }
            }
        }
    }

    static {
        NO_OP_REGISTRY = new NoOpRegistry();
        sDefaultReporter = new DefaultReporter();
    }

    private SurfaceControlRegistry() {
        this.mMaxLayersReportingThreshold = 1024;
        this.mResetReportingThreshold = 256;
        this.mHasReportedExceedingMaxThreshold = false;
        this.mReporter = sDefaultReporter;
        this.mSurfaceControls = new WeakHashMap<>(256);
    }

    public void setReportingThresholds(int i, int i2, Reporter reporter) {
        synchronized (sLock) {
            if (i <= 0 || i2 >= i) {
                throw new IllegalArgumentException("Expected maxLayersReportingThreshold (" + i + ") to be > 0 and resetReportingThreshold (" + i2 + ") to be < maxLayersReportingThreshold");
            }
            if (reporter == null) {
                throw new IllegalArgumentException("Expected non-null reporter");
            }
            this.mMaxLayersReportingThreshold = i;
            this.mResetReportingThreshold = i2;
            this.mHasReportedExceedingMaxThreshold = false;
            this.mReporter = reporter;
        }
    }

    public void setCallStackDebuggingParams(String str, String str2) {
        sCallStackDebuggingMatchName = str.toLowerCase();
        String lowerCase = str2.toLowerCase();
        sCallStackDebuggingMatchCall = lowerCase;
        sLogAllTxCallsOnApply = lowerCase.contains(APPLY);
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

    void add(SurfaceControl surfaceControl) {
        synchronized (sLock) {
            this.mSurfaceControls.put(surfaceControl, Long.valueOf(SystemClock.elapsedRealtime()));
            if (!this.mHasReportedExceedingMaxThreshold && this.mSurfaceControls.size() >= this.mMaxLayersReportingThreshold) {
                this.mReporter.onMaxLayersExceeded(this.mSurfaceControls, 256, new PrintWriter((OutputStream) System.out, true));
                this.mHasReportedExceedingMaxThreshold = true;
            }
        }
    }

    void remove(SurfaceControl surfaceControl) {
        synchronized (sLock) {
            this.mSurfaceControls.remove(surfaceControl);
            if (this.mHasReportedExceedingMaxThreshold && this.mSurfaceControls.size() <= this.mResetReportingThreshold) {
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
        sCallStackDebuggingInitialized = true;
        updateCallStackDebuggingParams();
        if (sCallStackDebuggingEnabled) {
            Log.d(TAG, "Enabling transaction call stack debugging: matchCall=" + sCallStackDebuggingMatchCall + " matchName=" + sCallStackDebuggingMatchName + " logCallsWithApply=" + sLogAllTxCallsOnApply);
        }
    }

    final void checkCallStackDebugging(String str, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, String str2) {
        checkCallStackDebugging(str, transaction, surfaceControl, str2, false);
    }

    final void checkCallStackDebugging(String str, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, String str2, boolean z) {
        if (sCallStackDebuggingInitialized && sCallStackDebuggingEnabled) {
            updateCallStackDebuggingParams();
        }
        if (sCallStackDebuggingEnabled || z) {
            String str3 = "";
            String str4 = transaction != null ? "tx=" + transaction.getId() + " " : "";
            String str5 = surfaceControl == null ? "" : " sc=" + surfaceControl.getName() + "";
            if (z) {
                if (transaction != null && !TextUtils.isEmpty(transaction.mDebugName)) {
                    str3 = " t=" + transaction.mDebugName;
                }
                Log.i(TAG, (str2 != null ? str + "," + str3 + str5 + ", " + str2 : str + "," + str3 + str5) + ", caller=" + Debug.getCallers(6));
                return;
            }
            String str6 = str2 != null ? str + " (" + str4 + str5 + ") " + str2 : str + " (" + str4 + str5 + NavigationBarInflaterView.KEY_CODE_END;
            if (!sLogAllTxCallsOnApply || transaction == null) {
                if (matchesForCallStackDebugging(surfaceControl != null ? surfaceControl.getName() : null, str)) {
                    Log.e(TAG, str6, new Throwable());
                    return;
                }
                return;
            }
            if (str != APPLY) {
                if (matchesForCallStackDebugging(surfaceControl != null ? surfaceControl.getName() : null, str)) {
                    Log.e(TAG, str6, new Throwable());
                    if (transaction.mCalls != null) {
                        transaction.mCalls.add(str6);
                        return;
                    }
                    return;
                }
                return;
            }
            Log.e(TAG, str6, new Throwable());
            if (transaction.mCalls != null) {
                for (int i = 0; i < transaction.mCalls.size(); i++) {
                    Log.d(TAG, "        " + transaction.mCalls.get(i));
                }
            }
        }
    }

    private static void updateCallStackDebuggingParams() {
        sCallStackDebuggingMatchCall = SystemProperties.get("persist.wm.debug.sc.tx.log_match_call", null).toLowerCase();
        sCallStackDebuggingMatchName = SystemProperties.get("persist.wm.debug.sc.tx.log_match_name", null).toLowerCase();
        sLogAllTxCallsOnApply = sCallStackDebuggingMatchCall.contains(APPLY);
        sCallStackDebuggingEnabled = (sCallStackDebuggingMatchCall.isEmpty() && sCallStackDebuggingMatchName.isEmpty()) ? false : true;
    }

    public final boolean matchesForCallStackDebugging(String str, String str2) {
        String str3 = sCallStackDebuggingMatchCall;
        if (str3 == null || sCallStackDebuggingMatchName == null) {
            return false;
        }
        if (!str3.isEmpty() && !sCallStackDebuggingMatchCall.contains(str2.toLowerCase())) {
            return false;
        }
        if (sCallStackDebuggingMatchName.isEmpty()) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return sCallStackDebuggingMatchName.contains(str.toLowerCase()) || str.toLowerCase().contains(sCallStackDebuggingMatchName);
    }

    static final boolean isCallStackDebuggingEnabled() {
        return sCallStackDebuggingEnabled;
    }

    private static void runGcAndFinalizers() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        GcUtils.runGcAndFinalizersSync();
        Log.i(TAG, "Ran gc and finalizers (" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms)");
    }

    public static void dump(int i, boolean z, PrintWriter printWriter) {
        if (z) {
            runGcAndFinalizers();
        }
        synchronized (sLock) {
            if (sProcessRegistry != null) {
                sDefaultReporter.onMaxLayersExceeded(sProcessRegistry.mSurfaceControls, i, printWriter);
                printWriter.println("sCallStackDebuggingInitialized=" + sCallStackDebuggingInitialized);
                printWriter.println("sCallStackDebuggingEnabled=" + sCallStackDebuggingEnabled);
                printWriter.println("sCallStackDebuggingMatchName=" + sCallStackDebuggingMatchName);
                printWriter.println("sCallStackDebuggingMatchCall=" + sCallStackDebuggingMatchCall);
                printWriter.println("sLogAllTxCallsOnApply=" + sLogAllTxCallsOnApply);
            }
        }
    }

    private static class NoOpRegistry extends SurfaceControlRegistry {
        @Override // android.view.SurfaceControlRegistry
        void add(SurfaceControl surfaceControl) {
        }

        @Override // android.view.SurfaceControlRegistry
        void remove(SurfaceControl surfaceControl) {
        }

        @Override // android.view.SurfaceControlRegistry
        public void setReportingThresholds(int i, int i2, Reporter reporter) {
        }

        private NoOpRegistry() {
            super();
        }
    }
}
