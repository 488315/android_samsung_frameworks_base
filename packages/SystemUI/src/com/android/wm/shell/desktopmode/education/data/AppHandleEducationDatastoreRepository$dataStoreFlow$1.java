package com.android.wm.shell.desktopmode.education.data;

import android.util.Log;
import java.io.IOException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class AppHandleEducationDatastoreRepository$dataStoreFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public AppHandleEducationDatastoreRepository$dataStoreFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppHandleEducationDatastoreRepository$dataStoreFlow$1 appHandleEducationDatastoreRepository$dataStoreFlow$1 = new AppHandleEducationDatastoreRepository$dataStoreFlow$1((Continuation) obj3);
        appHandleEducationDatastoreRepository$dataStoreFlow$1.L$0 = (Throwable) obj2;
        return appHandleEducationDatastoreRepository$dataStoreFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Throwable th = (Throwable) this.L$0;
        if (!(th instanceof IOException)) {
            throw th;
        }
        Log.e("AppHandleEducationDatastoreRepository", "Error in reading app handle education related data from datastore, data is stored in a file named app_handle_education.pb", th);
        return Unit.INSTANCE;
    }
}
