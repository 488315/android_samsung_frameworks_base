package androidx.room;

import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TransactorKt {
    public static final Object execSQL(PooledConnection pooledConnection, String str, ContinuationImpl continuationImpl) {
        Object usePrepared = pooledConnection.usePrepared(str, new TransactorKt$$ExternalSyntheticLambda0(), continuationImpl);
        return usePrepared == CoroutineSingletons.COROUTINE_SUSPENDED ? usePrepared : Unit.INSTANCE;
    }
}
