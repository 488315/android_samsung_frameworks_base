package com.android.wm.shell.desktopmode.education.data;

import android.util.Slog;
import java.io.IOException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AppToWebEducationDatastoreRepository$dataStoreFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public AppToWebEducationDatastoreRepository$dataStoreFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppToWebEducationDatastoreRepository$dataStoreFlow$1 appToWebEducationDatastoreRepository$dataStoreFlow$1 = new AppToWebEducationDatastoreRepository$dataStoreFlow$1((Continuation) obj3);
        appToWebEducationDatastoreRepository$dataStoreFlow$1.L$0 = (Throwable) obj2;
        return appToWebEducationDatastoreRepository$dataStoreFlow$1.invokeSuspend(Unit.INSTANCE);
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
        Slog.e("AppToWebEducationDatastoreRepository", "Error in reading App-to-Web education related data from datastore,data is stored in a file namedapp_to_web_education.pb", th);
        return Unit.INSTANCE;
    }
}
