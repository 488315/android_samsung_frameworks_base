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
    private final PerformanceHintManager mPerfHintManager;
    public long mTraceTag;
    private final SurfaceControl.Transaction mTransaction;

    public interface DisplayRootProvider {
        SurfaceControl getRootForDisplay(int i);
    }

    private @interface HintFlags {}

    public class HighPerfSession implements AutoCloseable {
        private final int displayId;
        private final int hintFlags;
        private String mTraceName;
        private final String reason;

        protected HighPerfSession(int hintFlags, int displayId, String reason) {
            this.hintFlags = hintFlags;
            this.reason = reason;
            this.displayId = displayId;
        }

        public void start() {
            if (!SystemPerformanceHinter.this.mActiveSessions.contains(this)) {
                SystemPerformanceHinter.this.startSession(this);
            }
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
                this.mTraceName =
                        "PerfSession-d"
                                + this.displayId
                                + NativeLibraryHelper.CLEAR_ABI_OVERRIDE
                                + this.reason;
            }
            Trace.asyncTraceForTrackBegin(
                    SystemPerformanceHinter.this.mTraceTag,
                    SystemPerformanceHinter.TAG,
                    this.mTraceName,
                    System.identityHashCode(this));
            return true;
        }

        boolean asyncTraceEnd() {
            if (this.mTraceName == null) {
                return false;
            }
            Trace.asyncTraceForTrackEnd(
                    SystemPerformanceHinter.this.mTraceTag,
                    SystemPerformanceHinter.TAG,
                    System.identityHashCode(this));
            return true;
        }
    }

    private class NoOpHighPerfSession extends HighPerfSession {
        public NoOpHighPerfSession() {
            super(0, -1, "");
        }

        @Override // android.window.SystemPerformanceHinter.HighPerfSession
        public void start() {}

        @Override // android.window.SystemPerformanceHinter.HighPerfSession, java.lang.AutoCloseable
        public void close() {}
    }

    public SystemPerformanceHinter(Context context, DisplayRootProvider displayRootProvider) {
        this(context, displayRootProvider, null);
    }

    public SystemPerformanceHinter(
            Context context,
            DisplayRootProvider displayRootProvider,
            Supplier<SurfaceControl.Transaction> transactionSupplier) {
        SurfaceControl.Transaction transaction;
        this.mTraceTag = 4096L;
        this.mActiveSessions = new ArrayList<>();
        this.mDisplayRootProvider = displayRootProvider;
        this.mPerfHintManager =
                (PerformanceHintManager) context.getSystemService(PerformanceHintManager.class);
        if (transactionSupplier != null) {
            transaction = transactionSupplier.get();
        } else {
            transaction = new SurfaceControl.Transaction();
        }
        this.mTransaction = transaction;
    }

    public void setAdpfSession(PerformanceHintManager.Session adpfSession) {
        this.mAdpfSession = adpfSession;
    }

    public HighPerfSession createSession(int hintFlags, int displayId, String reason) {
        if (hintFlags == 0) {
            throw new IllegalArgumentException("Not allow empty hint flags");
        }
        if (this.mDisplayRootProvider == null && (hintFlags & 2) != 0) {
            throw new IllegalArgumentException(
                    "Using SF frame rate hints requires a valid display root provider");
        }
        if (this.mAdpfSession == null && (hintFlags & 4) != 0) {
            throw new IllegalArgumentException("Using ADPF hints requires an ADPF session");
        }
        if ((hintFlags & 2) != 0
                && this.mDisplayRootProvider.getRootForDisplay(displayId) == null) {
            Log.v(TAG, "No display root for displayId=" + displayId);
            Trace.instant(32L, "PerfHint-NoDisplayRoot: " + displayId);
            return new NoOpHighPerfSession();
        }
        return new HighPerfSession(hintFlags, displayId, reason);
    }

    public HighPerfSession startSession(int hintFlags, int displayId, String reason) {
        HighPerfSession session = createSession(hintFlags, displayId, reason);
        if (session.hintFlags != 0) {
            startSession(session);
        }
        return session;
    }

    public void startSession(HighPerfSession session) {
        boolean isTraceEnabled = session.asyncTraceBegin();
        int oldGlobalFlags = calculateActiveHintFlags(5);
        int oldPerDisplayFlags = calculateActiveHintFlagsForDisplay(2, session.displayId);
        this.mActiveSessions.add(session);
        int newGlobalFlags = calculateActiveHintFlags(5);
        int newPerDisplayFlags = calculateActiveHintFlagsForDisplay(2, session.displayId);
        boolean transactionChanged = false;
        if (nowEnabled(oldPerDisplayFlags, newPerDisplayFlags, 2)) {
            SurfaceControl displaySurfaceControl =
                    this.mDisplayRootProvider.getRootForDisplay(session.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(displaySurfaceControl, 1);
            this.mTransaction.setFrameRateCategory(displaySurfaceControl, 5, false);
            transactionChanged = true;
            if (isTraceEnabled) {
                asyncTraceBegin(2, session.displayId);
            }
        }
        if (nowEnabled(oldGlobalFlags, newGlobalFlags, 1)) {
            this.mTransaction.setEarlyWakeupStart();
            transactionChanged = true;
            if (isTraceEnabled) {
                asyncTraceBegin(1, -1);
            }
        }
        if (nowEnabled(oldGlobalFlags, newGlobalFlags, 4)) {
            this.mAdpfSession.sendHint(0);
            if (isTraceEnabled) {
                asyncTraceBegin(4, -1);
            }
        }
        if (transactionChanged) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    public void endSession(HighPerfSession session) {
        boolean isTraceEnabled = session.asyncTraceEnd();
        int oldGlobalFlags = calculateActiveHintFlags(5);
        int oldPerDisplayFlags = calculateActiveHintFlagsForDisplay(2, session.displayId);
        this.mActiveSessions.remove(session);
        int newGlobalFlags = calculateActiveHintFlags(5);
        int newPerDisplayFlags = calculateActiveHintFlagsForDisplay(2, session.displayId);
        boolean transactionChanged = false;
        if (nowDisabled(oldPerDisplayFlags, newPerDisplayFlags, 2)) {
            SurfaceControl displaySurfaceControl =
                    this.mDisplayRootProvider.getRootForDisplay(session.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(displaySurfaceControl, 0);
            this.mTransaction.setFrameRateCategory(displaySurfaceControl, 0, false);
            transactionChanged = true;
            if (isTraceEnabled) {
                asyncTraceEnd(2);
            }
        }
        if (nowDisabled(oldGlobalFlags, newGlobalFlags, 1)) {
            this.mTransaction.setEarlyWakeupEnd();
            transactionChanged = true;
            if (isTraceEnabled) {
                asyncTraceEnd(1);
            }
        }
        if (nowDisabled(oldGlobalFlags, newGlobalFlags, 4)) {
            this.mAdpfSession.sendHint(2);
            if (isTraceEnabled) {
                asyncTraceEnd(4);
            }
        }
        if (transactionChanged) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    private boolean nowEnabled(int oldFlags, int newFlags, int checkFlags) {
        return (oldFlags & checkFlags) == 0 && (newFlags & checkFlags) != 0;
    }

    private boolean nowDisabled(int oldFlags, int newFlags, int checkFlags) {
        return (oldFlags & checkFlags) != 0 && (newFlags & checkFlags) == 0;
    }

    private int calculateActiveHintFlags(int filterFlags) {
        int flags = 0;
        for (int i = 0; i < this.mActiveSessions.size(); i++) {
            flags |= this.mActiveSessions.get(i).hintFlags & filterFlags;
        }
        return flags;
    }

    private int calculateActiveHintFlagsForDisplay(int filterFlags, int displayId) {
        int flags = 0;
        for (int i = 0; i < this.mActiveSessions.size(); i++) {
            HighPerfSession session = this.mActiveSessions.get(i);
            if (session.displayId == displayId) {
                flags |= this.mActiveSessions.get(i).hintFlags & filterFlags;
            }
        }
        return flags;
    }

    private void asyncTraceBegin(int flag, int displayId) {
        String prefix;
        switch (flag) {
            case 1:
                prefix = "PerfHint-early_wakeup";
                break;
            case 2:
                prefix = "PerfHint-framerate";
                break;
            case 3:
            default:
                prefix = "PerfHint-" + flag;
                break;
            case 4:
                prefix = "PerfHint-adpf";
                break;
        }
        String name = displayId != -1 ? prefix + "-d" + displayId : prefix;
        Trace.asyncTraceForTrackBegin(
                this.mTraceTag, TAG, name, System.identityHashCode(this) ^ flag);
    }

    private void asyncTraceEnd(int flag) {
        Trace.asyncTraceForTrackEnd(this.mTraceTag, TAG, System.identityHashCode(this) ^ flag);
    }

    public void dump(PrintWriter pw, String prefix) {
        String innerPrefix = prefix + "  ";
        pw.println(prefix + TAG + ":");
        pw.println(innerPrefix + "Active sessions (" + this.mActiveSessions.size() + "):");
        for (int i = 0; i < this.mActiveSessions.size(); i++) {
            HighPerfSession s = this.mActiveSessions.get(i);
            pw.println(
                    innerPrefix
                            + "  reason="
                            + s.reason
                            + " flags="
                            + s.hintFlags
                            + " display="
                            + s.displayId);
        }
    }
}
