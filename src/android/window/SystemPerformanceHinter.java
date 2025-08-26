package android.window;

import android.content.Context;
import android.os.PerformanceHintManager;
import android.os.Trace;
import android.util.Log;
import android.view.SurfaceControl;
import com.android.internal.content.NativeLibraryHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class SystemPerformanceHinter {
    public static final int HINT_ADPF = 4;
    public static final int HINT_ALL = 7;
    private static final int HINT_GLOBAL = 5;
    private static final int HINT_NO_OP = 0;
    private static final int HINT_PER_DISPLAY = 2;
    public static final int HINT_SF = 3;
    public static final int HINT_SF_EARLY_WAKEUP = 1;
    public static final int HINT_SF_FRAME_RATE = 2;
    private static final String TAG = "SystemPerformanceHinter";
    private final ArrayList<HighPerfSession> mActiveSessions;
    private PerformanceHintManager.Session mAdpfSession;
    private DisplayRootProvider mDisplayRootProvider;
    public long mTraceTag;
    private final SurfaceControl.Transaction mTransaction;

    public interface DisplayRootProvider {
        SurfaceControl getRootForDisplay(int i);
    }

    private @interface HintFlags {
    }

    private boolean nowDisabled(int i, int i2, int i3) {
        return (i & i3) != 0 && (i2 & i3) == 0;
    }

    private boolean nowEnabled(int i, int i2, int i3) {
        return (i & i3) == 0 && (i2 & i3) != 0;
    }

    public class HighPerfSession implements AutoCloseable {
        private final int displayId;
        private final int hintFlags;
        private String mTraceName;
        private final String reason;

        protected HighPerfSession(int i, int i2, String str) {
            this.hintFlags = i;
            this.reason = str;
            this.displayId = i2;
        }

        public void start() {
            if (SystemPerformanceHinter.this.mActiveSessions.contains(this)) {
                return;
            }
            SystemPerformanceHinter.this.startSession(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            SystemPerformanceHinter.this.endSession(this);
        }

        public void finalize() {
            close();
        }

        boolean asyncTraceBegin() {
            if (!Trace.isTagEnabled(SystemPerformanceHinter.this.mTraceTag)) {
                this.mTraceName = null;
                return false;
            }
            if (this.mTraceName == null) {
                this.mTraceName = "PerfSession-d" + this.displayId + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.reason;
            }
            Trace.asyncTraceForTrackBegin(SystemPerformanceHinter.this.mTraceTag, SystemPerformanceHinter.TAG, this.mTraceName, System.identityHashCode(this));
            return true;
        }

        boolean asyncTraceEnd() {
            if (this.mTraceName == null) {
                return false;
            }
            Trace.asyncTraceForTrackEnd(SystemPerformanceHinter.this.mTraceTag, SystemPerformanceHinter.TAG, System.identityHashCode(this));
            return true;
        }
    }

    private class NoOpHighPerfSession extends HighPerfSession {
        @Override // android.window.SystemPerformanceHinter.HighPerfSession, java.lang.AutoCloseable
        public void close() {
        }

        @Override // android.window.SystemPerformanceHinter.HighPerfSession
        public void start() {
        }

        public NoOpHighPerfSession(SystemPerformanceHinter systemPerformanceHinter) {
            super(0, -1, "");
        }
    }

    public SystemPerformanceHinter(Context context, DisplayRootProvider displayRootProvider) {
        this(context, displayRootProvider, null);
    }

    public SystemPerformanceHinter(Context context, DisplayRootProvider displayRootProvider, Supplier<SurfaceControl.Transaction> supplier) {
        SurfaceControl.Transaction transaction;
        this.mTraceTag = 4096L;
        this.mActiveSessions = new ArrayList<>();
        this.mDisplayRootProvider = displayRootProvider;
        if (supplier != null) {
            transaction = supplier.get();
        } else {
            transaction = new SurfaceControl.Transaction();
        }
        this.mTransaction = transaction;
    }

    public void setAdpfSession(PerformanceHintManager.Session session) {
        this.mAdpfSession = session;
    }

    public HighPerfSession createSession(int i, int i2, String str) {
        if (i == 0) {
            throw new IllegalArgumentException("Not allow empty hint flags");
        }
        DisplayRootProvider displayRootProvider = this.mDisplayRootProvider;
        if (displayRootProvider == null && (i & 2) != 0) {
            throw new IllegalArgumentException("Using SF frame rate hints requires a valid display root provider");
        }
        if (this.mAdpfSession == null && (i & 4) != 0) {
            throw new IllegalArgumentException("Using ADPF hints requires an ADPF session");
        }
        if ((i & 2) != 0 && displayRootProvider.getRootForDisplay(i2) == null) {
            Log.v(TAG, "No display root for displayId=" + i2);
            Trace.instant(32L, "PerfHint-NoDisplayRoot: " + i2);
            return new NoOpHighPerfSession(this);
        }
        return new HighPerfSession(i, i2, str);
    }

    public HighPerfSession startSession(int i, int i2, String str) {
        HighPerfSession highPerfSessionCreateSession = createSession(i, i2, str);
        if (highPerfSessionCreateSession.hintFlags != 0) {
            startSession(highPerfSessionCreateSession);
        }
        return highPerfSessionCreateSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSession(HighPerfSession highPerfSession) {
        boolean z;
        boolean zAsyncTraceBegin = highPerfSession.asyncTraceBegin();
        int iCalculateActiveHintFlags = calculateActiveHintFlags(5);
        int iCalculateActiveHintFlagsForDisplay = calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId);
        this.mActiveSessions.add(highPerfSession);
        int iCalculateActiveHintFlags2 = calculateActiveHintFlags(5);
        boolean z2 = true;
        if (nowEnabled(iCalculateActiveHintFlagsForDisplay, calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId), 2)) {
            SurfaceControl rootForDisplay = this.mDisplayRootProvider.getRootForDisplay(highPerfSession.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(rootForDisplay, 1);
            this.mTransaction.setFrameRateCategory(rootForDisplay, 6, false);
            if (zAsyncTraceBegin) {
                asyncTraceBegin(2, highPerfSession.displayId);
            }
            z = true;
        } else {
            z = false;
        }
        if (nowEnabled(iCalculateActiveHintFlags, iCalculateActiveHintFlags2, 1)) {
            this.mTransaction.setEarlyWakeupStart();
            if (zAsyncTraceBegin) {
                asyncTraceBegin(1, -1);
            }
        } else {
            z2 = z;
        }
        if (this.mAdpfSession != null && nowEnabled(iCalculateActiveHintFlags, iCalculateActiveHintFlags2, 4)) {
            this.mAdpfSession.sendHint(0);
            if (zAsyncTraceBegin) {
                asyncTraceBegin(4, -1);
            }
        }
        if (z2) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endSession(HighPerfSession highPerfSession) {
        boolean zAsyncTraceEnd = highPerfSession.asyncTraceEnd();
        int iCalculateActiveHintFlags = calculateActiveHintFlags(5);
        int iCalculateActiveHintFlagsForDisplay = calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId);
        this.mActiveSessions.remove(highPerfSession);
        int iCalculateActiveHintFlags2 = calculateActiveHintFlags(5);
        boolean z = true;
        boolean z2 = false;
        if (nowDisabled(iCalculateActiveHintFlagsForDisplay, calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId), 2)) {
            SurfaceControl rootForDisplay = this.mDisplayRootProvider.getRootForDisplay(highPerfSession.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(rootForDisplay, 0);
            this.mTransaction.setFrameRateCategory(rootForDisplay, 0, false);
            if (zAsyncTraceEnd) {
                asyncTraceEnd(2);
            }
            z2 = true;
        }
        if (nowDisabled(iCalculateActiveHintFlags, iCalculateActiveHintFlags2, 1)) {
            this.mTransaction.setEarlyWakeupEnd();
            if (zAsyncTraceEnd) {
                asyncTraceEnd(1);
            }
        } else {
            z = z2;
        }
        if (this.mAdpfSession != null && nowDisabled(iCalculateActiveHintFlags, iCalculateActiveHintFlags2, 4)) {
            this.mAdpfSession.sendHint(2);
            if (zAsyncTraceEnd) {
                asyncTraceEnd(4);
            }
        }
        if (z) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    private int calculateActiveHintFlags(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mActiveSessions.size(); i3++) {
            i2 |= this.mActiveSessions.get(i3).hintFlags & i;
        }
        return i2;
    }

    private int calculateActiveHintFlagsForDisplay(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < this.mActiveSessions.size(); i4++) {
            if (this.mActiveSessions.get(i4).displayId == i2) {
                i3 |= this.mActiveSessions.get(i4).hintFlags & i;
            }
        }
        return i3;
    }

    private void asyncTraceBegin(int i, int i2) {
        String str;
        if (i == 1) {
            str = "PerfHint-early_wakeup";
        } else if (i == 2) {
            str = "PerfHint-framerate";
        } else if (i == 4) {
            str = "PerfHint-adpf";
        } else {
            str = "PerfHint-" + i;
        }
        if (i2 != -1) {
            str = str + "-d" + i2;
        }
        Trace.asyncTraceForTrackBegin(this.mTraceTag, TAG, str, System.identityHashCode(this) ^ i);
    }

    private void asyncTraceEnd(int i) {
        Trace.asyncTraceForTrackEnd(this.mTraceTag, TAG, System.identityHashCode(this) ^ i);
    }

    public void dump(PrintWriter printWriter, String str) {
        String str2 = str + "  ";
        printWriter.println(str + "SystemPerformanceHinter:");
        printWriter.println(str2 + "Active sessions (" + this.mActiveSessions.size() + "):");
        for (int i = 0; i < this.mActiveSessions.size(); i++) {
            HighPerfSession highPerfSession = this.mActiveSessions.get(i);
            printWriter.println(str2 + "  reason=" + highPerfSession.reason + " flags=" + highPerfSession.hintFlags + " display=" + highPerfSession.displayId);
        }
    }
}
