package com.android.server.utils;

import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import com.android.internal.util.RingBuffer;

import java.io.PrintWriter;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AnrTimer implements AutoCloseable {
    public static final Injector sDefaultInjector = new Injector();
    public final FeatureDisabled mFeature;
    public final Handler mHandler;
    public final String mLabel;
    public final Object mLock = new Object();
    public final SparseArray mTimerArgMap;
    public final int mWhat;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.utils.AnrTimer$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((AnrTimer) obj).mLabel.compareTo(((AnrTimer) obj2).mLabel);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Args {
        public final Injector mInjector = AnrTimer.sDefaultInjector;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Error {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FeatureDisabled {
        public final /* synthetic */ AnrTimer this$0$1;

        public FeatureDisabled() {
            this.this$0$1 = AnrTimer.this;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    static {
        new RingBuffer(Error.class, 20);
        new LongSparseArray();
        Comparator.nullsLast(new AnonymousClass1());
    }

    public AnrTimer(Handler handler, int i, String str, Args args) {
        new ArrayMap();
        this.mTimerArgMap = new SparseArray();
        this.mHandler = handler;
        this.mWhat = i;
        this.mLabel = str;
        args.mInjector.getClass();
        Flags.anrTimerService();
        this.mFeature = new FeatureDisabled();
    }

    public static void dump(PrintWriter printWriter, boolean z, Injector injector) {
        injector.getClass();
        Flags.anrTimerService();
    }

    private boolean expire(int i, int i2, int i3, long j) {
        Trace.instantForTrack(
                64L,
                "AnrTimerTrack",
                TextUtils.formatSimple(
                        "%s(%d,%d,%d,%s,%d)",
                        new Object[] {
                            "expired",
                            Integer.valueOf(i),
                            Integer.valueOf(i2),
                            Integer.valueOf(i3),
                            this.mLabel,
                            Long.valueOf(j)
                        }));
        synchronized (this.mLock) {
            try {
                Object obj = this.mTimerArgMap.get(i);
                if (obj == null) {
                    Log.e(
                            "AnrTimer",
                            TextUtils.formatSimple(
                                    "failed to expire timer %s:%d : arg not found",
                                    new Object[] {this.mLabel, Integer.valueOf(i)}));
                    return false;
                }
                Handler handler = this.mHandler;
                handler.sendMessage(Message.obtain(handler, this.mWhat, obj));
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static native boolean nativeAnrTimerAccept(long j, int i);

    private static native boolean nativeAnrTimerCancel(long j, int i);

    private static native int nativeAnrTimerClose(long j);

    private native long nativeAnrTimerCreate(String str, boolean z, boolean z2);

    private static native boolean nativeAnrTimerDiscard(long j, int i);

    private static native String[] nativeAnrTimerDump(long j);

    private static native boolean nativeAnrTimerRelease(long j, int i);

    private static native int nativeAnrTimerStart(long j, int i, int i2, long j2);

    private static native boolean nativeAnrTimerSupported();

    public final void cancel(Object obj) {
        AnrTimer anrTimer = AnrTimer.this;
        anrTimer.mHandler.removeMessages(anrTimer.mWhat, obj);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mFeature.getClass();
    }

    public final void discard() {
        this.mFeature.getClass();
    }

    public final void finalize() {
        close();
        super.finalize();
    }

    public abstract int getPid(Object obj);

    public abstract int getUid(Object obj);

    public final void start(long j, Object obj) {
        if (j < 0) {
            j = 0;
        }
        FeatureDisabled featureDisabled = this.mFeature;
        getPid(obj);
        getUid(obj);
        AnrTimer anrTimer = AnrTimer.this;
        anrTimer.mHandler.sendMessageDelayed(
                anrTimer.mHandler.obtainMessage(anrTimer.mWhat, obj), j);
    }
}
