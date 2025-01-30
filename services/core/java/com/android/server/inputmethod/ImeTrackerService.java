package com.android.server.inputmethod;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.IImeTracker;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.util.FrameworkStatsLog;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Locale;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public final class ImeTrackerService extends IImeTracker.Stub {
    public final Handler mHandler;
    public final History mHistory = new History();

    public ImeTrackerService(Looper looper) {
        this.mHandler = new Handler(looper, null, true);
    }

    public synchronized ImeTracker.Token onRequestShow(String str, int i, int i2, int i3) {
        final ImeTracker.Token token;
        Binder binder = new Binder();
        token = new ImeTracker.Token(binder, str);
        this.mHistory.addEntry(binder, new History.Entry(str, i, 1, 1, i2, i3));
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.inputmethod.ImeTrackerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ImeTrackerService.this.lambda$onRequestShow$0(token);
            }
        }, 10000L);
        return token;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestShow$0(ImeTracker.Token token) {
        synchronized (this) {
            this.mHistory.setFinished(token, 5, 0);
        }
    }

    public synchronized ImeTracker.Token onRequestHide(String str, int i, int i2, int i3) {
        final ImeTracker.Token token;
        Binder binder = new Binder();
        token = new ImeTracker.Token(binder, str);
        this.mHistory.addEntry(binder, new History.Entry(str, i, 2, 1, i2, i3));
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.inputmethod.ImeTrackerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ImeTrackerService.this.lambda$onRequestHide$1(token);
            }
        }, 10000L);
        return token;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestHide$1(ImeTracker.Token token) {
        synchronized (this) {
            this.mHistory.setFinished(token, 5, 0);
        }
    }

    public synchronized void onProgress(IBinder iBinder, int i) {
        History.Entry entry = this.mHistory.getEntry(iBinder);
        if (entry == null) {
            return;
        }
        entry.mPhase = i;
    }

    public synchronized void onFailed(ImeTracker.Token token, int i) {
        this.mHistory.setFinished(token, 3, i);
    }

    public synchronized void onCancelled(ImeTracker.Token token, int i) {
        this.mHistory.setFinished(token, 2, i);
    }

    public synchronized void onShown(ImeTracker.Token token) {
        this.mHistory.setFinished(token, 4, 0);
    }

    public synchronized void onHidden(ImeTracker.Token token) {
        this.mHistory.setFinished(token, 4, 0);
    }

    public synchronized void onImmsUpdate(ImeTracker.Token token, String str) {
        History.Entry entry = this.mHistory.getEntry(token.getBinder());
        if (entry == null) {
            return;
        }
        entry.mRequestWindowName = str;
    }

    public synchronized void dump(PrintWriter printWriter, String str) {
        this.mHistory.dump(printWriter, str);
    }

    public synchronized boolean hasPendingImeVisibilityRequests() {
        super.hasPendingImeVisibilityRequests_enforcePermission();
        return !this.mHistory.mLiveEntries.isEmpty();
    }

    public final class History {
        public static final AtomicInteger sSequenceNumber = new AtomicInteger(0);
        public final ArrayDeque mEntries;
        public final WeakHashMap mLiveEntries;

        public History() {
            this.mEntries = new ArrayDeque(100);
            this.mLiveEntries = new WeakHashMap();
        }

        public final void addEntry(IBinder iBinder, Entry entry) {
            this.mLiveEntries.put(iBinder, entry);
        }

        public final Entry getEntry(IBinder iBinder) {
            return (Entry) this.mLiveEntries.get(iBinder);
        }

        public final void setFinished(ImeTracker.Token token, int i, int i2) {
            Entry entry = (Entry) this.mLiveEntries.remove(token.getBinder());
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
            while (this.mEntries.size() >= 100) {
                this.mEntries.remove();
            }
            this.mEntries.offer(entry);
            FrameworkStatsLog.write(FrameworkStatsLog.IME_REQUEST_FINISHED, entry.mUid, entry.mDuration, entry.mType, entry.mStatus, entry.mReason, entry.mOrigin, entry.mPhase);
        }

        public final void dump(PrintWriter printWriter, String str) {
            DateTimeFormatter withZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).withZone(ZoneId.systemDefault());
            printWriter.print(str);
            printWriter.println("ImeTrackerService#History.mLiveEntries: " + this.mLiveEntries.size() + " elements");
            Iterator it = this.mLiveEntries.values().iterator();
            while (it.hasNext()) {
                dumpEntry((Entry) it.next(), printWriter, str, withZone);
            }
            printWriter.print(str);
            printWriter.println("ImeTrackerService#History.mEntries: " + this.mEntries.size() + " elements");
            Iterator it2 = this.mEntries.iterator();
            while (it2.hasNext()) {
                dumpEntry((Entry) it2.next(), printWriter, str, withZone);
            }
        }

        public final void dumpEntry(Entry entry, PrintWriter printWriter, String str, DateTimeFormatter dateTimeFormatter) {
            printWriter.print(str);
            printWriter.print(" #" + entry.mSequenceNumber);
            printWriter.print(" " + ImeTracker.Debug.typeToString(entry.mType));
            printWriter.print(" - " + ImeTracker.Debug.statusToString(entry.mStatus));
            printWriter.print(" - " + entry.mTag);
            printWriter.println(" (" + entry.mDuration + "ms):");
            printWriter.print(str);
            printWriter.print("   startTime=" + dateTimeFormatter.format(Instant.ofEpochMilli(entry.mStartTime)));
            printWriter.println(" " + ImeTracker.Debug.originToString(entry.mOrigin));
            printWriter.print(str);
            printWriter.print("   reason=" + InputMethodDebug.softInputDisplayReasonToString(entry.mReason));
            printWriter.println(" " + ImeTracker.Debug.phaseToString(entry.mPhase));
            printWriter.print(str);
            printWriter.println("   requestWindowName=" + entry.mRequestWindowName);
        }

        public final class Entry {
            public long mDuration;
            public final int mOrigin;
            public int mPhase;
            public final int mReason;
            public String mRequestWindowName;
            public final int mSequenceNumber;
            public final long mStartTime;
            public int mStatus;
            public final String mTag;
            public final int mType;
            public final int mUid;

            public Entry(String str, int i, int i2, int i3, int i4, int i5) {
                this.mSequenceNumber = History.sSequenceNumber.getAndIncrement();
                this.mStartTime = System.currentTimeMillis();
                this.mDuration = 0L;
                this.mPhase = 0;
                this.mRequestWindowName = "not set";
                this.mTag = str;
                this.mUid = i;
                this.mType = i2;
                this.mStatus = i3;
                this.mOrigin = i4;
                this.mReason = i5;
            }
        }
    }
}
