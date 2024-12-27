package com.android.server.inputmethod;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.ImeTracker;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IImeTracker;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.inputmethod.ImeTrackerService;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Locale;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ImeTrackerService extends IImeTracker.Stub {
    public final Handler mHandler;
    public final History mHistory = new History();
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class History {
        public static final AtomicInteger sSequenceNumber = new AtomicInteger(0);
        public final ArrayDeque mEntries = new ArrayDeque(100);
        public final WeakHashMap mLiveEntries = new WeakHashMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Entry {
            public final boolean mFromUser;
            public final int mOrigin;
            public final int mReason;
            public final String mTag;
            public final int mType;
            public final int mUid;
            public final int mSequenceNumber = History.sSequenceNumber.getAndIncrement();
            public final long mStartTime = System.currentTimeMillis();
            public long mDuration = 0;
            public int mPhase = 0;
            public String mRequestWindowName = "not set";
            public int mStatus = 1;

            public Entry(int i, int i2, String str, int i3, int i4, boolean z) {
                this.mTag = str;
                this.mUid = i;
                this.mType = i2;
                this.mOrigin = i3;
                this.mReason = i4;
                this.mFromUser = z;
            }
        }

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m587$$Nest$mdump(History history, PrintWriter printWriter) {
            history.getClass();
            DateTimeFormatter withZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).withZone(ZoneId.systemDefault());
            printWriter.print("    ");
            printWriter.println("mLiveEntries: " + history.mLiveEntries.size() + " elements");
            Iterator it = history.mLiveEntries.values().iterator();
            while (it.hasNext()) {
                dumpEntry((Entry) it.next(), printWriter, "      ", withZone);
            }
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "    ", "mEntries: ");
            m.append(history.mEntries.size());
            m.append(" elements");
            printWriter.println(m.toString());
            Iterator it2 = history.mEntries.iterator();
            while (it2.hasNext()) {
                dumpEntry((Entry) it2.next(), printWriter, "      ", withZone);
            }
        }

        /* renamed from: -$$Nest$msetFinished, reason: not valid java name */
        public static void m588$$Nest$msetFinished(History history, ImeTracker.Token token, int i, int i2) {
            Entry entry = (Entry) history.mLiveEntries.remove(token.getBinder());
            if (entry == null) {
                if (i != 5) {
                    Log.i("ImeTracker", token.getTag() + ": setFinished on previously finished token at " + ImeTracker.Debug.phaseToString(i2) + " with " + ImeTracker.Debug.statusToString(i));
                    return;
                }
                return;
            }
            entry.mDuration = System.currentTimeMillis() - entry.mStartTime;
            entry.mStatus = i;
            if (i2 != 0) {
                entry.mPhase = i2;
            }
            if (i == 5) {
                Log.i("ImeTracker", token.getTag() + ": setFinished at " + ImeTracker.Debug.phaseToString(entry.mPhase) + " with " + ImeTracker.Debug.statusToString(i));
            }
            while (history.mEntries.size() >= 100) {
                history.mEntries.remove();
            }
            history.mEntries.offer(entry);
            FrameworkStatsLog.write(FrameworkStatsLog.IME_REQUEST_FINISHED, entry.mUid, entry.mDuration, entry.mType, entry.mStatus, entry.mReason, entry.mOrigin, entry.mPhase, entry.mFromUser);
        }

        public static void dumpEntry(Entry entry, PrintWriter printWriter, String str, DateTimeFormatter dateTimeFormatter) {
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str, "#");
            m.append(entry.mSequenceNumber);
            printWriter.print(m.toString());
            printWriter.print(" " + ImeTracker.Debug.typeToString(entry.mType));
            printWriter.print(" - " + ImeTracker.Debug.statusToString(entry.mStatus));
            printWriter.print(" - " + entry.mTag);
            printWriter.println(" (" + entry.mDuration + "ms):");
            StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str, "  startTime=");
            m2.append(dateTimeFormatter.format(Instant.ofEpochMilli(entry.mStartTime)));
            printWriter.print(m2.toString());
            printWriter.println(" " + ImeTracker.Debug.originToString(entry.mOrigin));
            printWriter.print(str);
            printWriter.print("  reason=" + InputMethodDebug.softInputDisplayReasonToString(entry.mReason));
            printWriter.println(" " + ImeTracker.Debug.phaseToString(entry.mPhase));
            printWriter.print(str);
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  requestWindowName="), entry.mRequestWindowName, printWriter);
        }
    }

    public ImeTrackerService(Looper looper) {
        this.mHandler = new Handler(looper, null, true);
    }

    public final void finishTrackingPendingImeVisibilityRequests(AndroidFuture androidFuture) {
        finishTrackingPendingImeVisibilityRequests_enforcePermission();
        try {
            synchronized (this.mLock) {
                this.mHistory.mLiveEntries.clear();
            }
            androidFuture.complete((Object) null);
        } catch (Throwable th) {
            androidFuture.completeExceptionally(th);
        }
    }

    public final boolean hasPendingImeVisibilityRequests() {
        boolean z;
        hasPendingImeVisibilityRequests_enforcePermission();
        synchronized (this.mLock) {
            z = !this.mHistory.mLiveEntries.isEmpty();
        }
        return z;
    }

    public final void onCancelled(ImeTracker.Token token, int i) {
        synchronized (this.mLock) {
            History.m588$$Nest$msetFinished(this.mHistory, token, 2, i);
        }
    }

    public final void onDispatched(ImeTracker.Token token) {
        synchronized (this.mLock) {
            History.m588$$Nest$msetFinished(this.mHistory, token, 4, 0);
        }
    }

    public final void onFailed(ImeTracker.Token token, int i) {
        synchronized (this.mLock) {
            History.m588$$Nest$msetFinished(this.mHistory, token, 3, i);
        }
    }

    public final void onHidden(ImeTracker.Token token) {
        synchronized (this.mLock) {
            History.m588$$Nest$msetFinished(this.mHistory, token, 4, 0);
        }
    }

    public final void onProgress(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                History.Entry entry = (History.Entry) this.mHistory.mLiveEntries.get(iBinder);
                if (entry == null) {
                    return;
                }
                entry.mPhase = i;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onShown(ImeTracker.Token token) {
        synchronized (this.mLock) {
            History.m588$$Nest$msetFinished(this.mHistory, token, 4, 0);
        }
    }

    public final ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) {
        Binder binder = new Binder();
        final ImeTracker.Token token = new ImeTracker.Token(binder, str);
        History.Entry entry = new History.Entry(i, i2, str, i3, i4, z);
        synchronized (this.mLock) {
            this.mHistory.mLiveEntries.put(binder, entry);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.inputmethod.ImeTrackerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImeTrackerService imeTrackerService = ImeTrackerService.this;
                    ImeTracker.Token token2 = token;
                    synchronized (imeTrackerService.mLock) {
                        ImeTrackerService.History.m588$$Nest$msetFinished(imeTrackerService.mHistory, token2, 5, 0);
                    }
                }
            }, 10000L);
        }
        return token;
    }
}
