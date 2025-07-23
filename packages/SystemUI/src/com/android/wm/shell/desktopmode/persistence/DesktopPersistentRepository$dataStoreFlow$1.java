package com.android.wm.shell.desktopmode.persistence;

import android.util.Log;
import java.io.IOException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopPersistentRepository$dataStoreFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public DesktopPersistentRepository$dataStoreFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DesktopPersistentRepository$dataStoreFlow$1 desktopPersistentRepository$dataStoreFlow$1 = new DesktopPersistentRepository$dataStoreFlow$1((Continuation) obj3);
        desktopPersistentRepository$dataStoreFlow$1.L$0 = (Throwable) obj2;
        return desktopPersistentRepository$dataStoreFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Throwable th = (Throwable) this.L$0;
        if (!(th instanceof IOException)) {
            throw th;
        }
        Log.e("DesktopPersistenceRepo", "Error in reading desktop mode related data from datastore, data is stored in a file named desktop_persistent_repositories.pb", th);
        return Unit.INSTANCE;
    }
}
