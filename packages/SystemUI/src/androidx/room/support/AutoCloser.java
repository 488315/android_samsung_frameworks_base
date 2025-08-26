package androidx.room.support;

import android.os.SystemClock;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Watch {
    }

    /* renamed from: androidx.room.support.AutoCloser$decrementCountAndScheduleClose$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AutoCloser.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = AutoCloser.this.autoCloseTimeoutInMs;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            AutoCloser autoCloser = AutoCloser.this;
            synchronized (autoCloser.lock) {
                try {
                    ((AutoCloser$$ExternalSyntheticLambda0) autoCloser.watch).getClass();
                    if (SystemClock.uptimeMillis() - autoCloser.lastDecrementRefCountTimeStamp.get() >= autoCloser.autoCloseTimeoutInMs && autoCloser.referenceCount.get() == 0) {
                        Function0 function0 = autoCloser.onAutoCloseCallback;
                        if (function0 == null) {
                            throw new IllegalStateException("onAutoCloseCallback is null but it should  have been set before use. Please file a bug against Room at: https://issuetracker.google.com/issues/new?component=413107&template=1096568");
                        }
                        function0.invoke();
                        SupportSQLiteDatabase supportSQLiteDatabase = autoCloser.delegateDatabase;
                        if (supportSQLiteDatabase != null && supportSQLiteDatabase.isOpen()) {
                            supportSQLiteDatabase.close();
                        }
                        autoCloser.delegateDatabase = null;
                        Unit unit = Unit.INSTANCE;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return Unit.INSTANCE;
        }
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
        int iDecrementAndGet = this.referenceCount.decrementAndGet();
        if (iDecrementAndGet < 0) {
            throw new IllegalStateException("Unbalanced reference count.");
        }
        AtomicLong atomicLong = this.lastDecrementRefCountTimeStamp;
        ((AutoCloser$$ExternalSyntheticLambda0) this.watch).getClass();
        atomicLong.set(SystemClock.uptimeMillis());
        if (iDecrementAndGet == 0) {
            CoroutineScope coroutineScope = this.coroutineScope;
            if (coroutineScope == null) {
                coroutineScope = null;
            }
            this.autoCloseJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
        }
    }

    public final Object executeRefCountingFunction(Function1 function1) {
        try {
            return function1.mo781invoke(incrementCountAndEnsureDbIsOpen());
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
