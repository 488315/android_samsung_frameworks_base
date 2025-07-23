package androidx.room.support;

import android.os.SystemClock;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AutoCloser$decrementCountAndScheduleClose$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AutoCloser this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoCloser$decrementCountAndScheduleClose$2(AutoCloser autoCloser, Continuation continuation) {
        super(2, continuation);
        this.this$0 = autoCloser;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AutoCloser$decrementCountAndScheduleClose$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AutoCloser$decrementCountAndScheduleClose$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.this$0.autoCloseTimeoutInMs;
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
        AutoCloser autoCloser = this.this$0;
        synchronized (autoCloser.lock) {
            try {
                ((AutoCloser$$ExternalSyntheticLambda0) autoCloser.watch).getClass();
                if (SystemClock.uptimeMillis() - autoCloser.lastDecrementRefCountTimeStamp.get() >= autoCloser.autoCloseTimeoutInMs) {
                    if (autoCloser.referenceCount.get() == 0) {
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
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return Unit.INSTANCE;
    }
}
