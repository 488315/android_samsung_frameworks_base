package androidx.room.support;

import android.os.SystemClock;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AutoCloser {
    public StandaloneCoroutine autoCloseJob;
    public final long autoCloseTimeoutInMs;
    public CoroutineScope coroutineScope;
    public SupportSQLiteDatabase delegateDatabase;
    public SupportSQLiteOpenHelper delegateOpenHelper;
    public final AtomicLong lastDecrementRefCountTimeStamp;
    public final Object lock;
    public boolean manuallyClosed;
    public Function0 onAutoCloseCallback;
    public final AtomicInteger referenceCount;
    public final Watch watch;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Watch {
    }

    static {
        new Companion(null);
    }

    public AutoCloser(long j, TimeUnit timeUnit, Watch watch) {
        this.watch = watch;
        this.lock = new Object();
        this.autoCloseTimeoutInMs = timeUnit.toMillis(j);
        this.referenceCount = new AtomicInteger(0);
        ((AutoCloser$$ExternalSyntheticLambda0) watch).getClass();
        this.lastDecrementRefCountTimeStamp = new AtomicLong(SystemClock.uptimeMillis());
    }

    public final void decrementCountAndScheduleClose() {
        int decrementAndGet = this.referenceCount.decrementAndGet();
        if (decrementAndGet < 0) {
            throw new IllegalStateException("Unbalanced reference count.");
        }
        AtomicLong atomicLong = this.lastDecrementRefCountTimeStamp;
        ((AutoCloser$$ExternalSyntheticLambda0) this.watch).getClass();
        atomicLong.set(SystemClock.uptimeMillis());
        if (decrementAndGet == 0) {
            CoroutineScope coroutineScope = this.coroutineScope;
            if (coroutineScope == null) {
                coroutineScope = null;
            }
            this.autoCloseJob = BuildersKt.launch$default(coroutineScope, null, null, new AutoCloser$decrementCountAndScheduleClose$2(this, null), 3);
        }
    }

    public final Object executeRefCountingFunction(Function1 function1) {
        try {
            return function1.mo779invoke(incrementCountAndEnsureDbIsOpen());
        } finally {
            decrementCountAndScheduleClose();
        }
    }

    public final SupportSQLiteDatabase incrementCountAndEnsureDbIsOpen() {
        StandaloneCoroutine standaloneCoroutine = this.autoCloseJob;
        SupportSQLiteOpenHelper supportSQLiteOpenHelper = null;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.autoCloseJob = null;
        this.referenceCount.incrementAndGet();
        if (this.manuallyClosed) {
            throw new IllegalStateException("Attempting to open already closed database.");
        }
        synchronized (this.lock) {
            SupportSQLiteDatabase supportSQLiteDatabase = this.delegateDatabase;
            if (supportSQLiteDatabase != null && supportSQLiteDatabase.isOpen()) {
                return supportSQLiteDatabase;
            }
            SupportSQLiteOpenHelper supportSQLiteOpenHelper2 = this.delegateOpenHelper;
            if (supportSQLiteOpenHelper2 != null) {
                supportSQLiteOpenHelper = supportSQLiteOpenHelper2;
            }
            SupportSQLiteDatabase writableDatabase = supportSQLiteOpenHelper.getWritableDatabase();
            this.delegateDatabase = writableDatabase;
            return writableDatabase;
        }
    }

    public /* synthetic */ AutoCloser(long j, TimeUnit timeUnit, Watch watch, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, timeUnit, (i & 4) != 0 ? new AutoCloser$$ExternalSyntheticLambda0() : watch);
    }
}
