package androidx.room;

import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public abstract class TransactorKt {
    public static final Object execSQL(PooledConnection pooledConnection, String str, ContinuationImpl continuationImpl) {
        Object objUsePrepared = pooledConnection.usePrepared(str, new TransactorKt$$ExternalSyntheticLambda0(), continuationImpl);
        return objUsePrepared == CoroutineSingletons.COROUTINE_SUSPENDED ? objUsePrepared : Unit.INSTANCE;
    }
}
