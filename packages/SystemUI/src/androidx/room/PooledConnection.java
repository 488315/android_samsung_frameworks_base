package androidx.room;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PooledConnection {
    Object usePrepared(String str, Function1 function1, ContinuationImpl continuationImpl);
}
