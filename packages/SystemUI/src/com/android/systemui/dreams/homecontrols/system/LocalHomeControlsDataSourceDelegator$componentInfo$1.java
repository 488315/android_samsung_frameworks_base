package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsComponentInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class LocalHomeControlsDataSourceDelegator$componentInfo$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public LocalHomeControlsDataSourceDelegator$componentInfo$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        LocalHomeControlsDataSourceDelegator$componentInfo$1 localHomeControlsDataSourceDelegator$componentInfo$1 = new LocalHomeControlsDataSourceDelegator$componentInfo$1((Continuation) obj3);
        localHomeControlsDataSourceDelegator$componentInfo$1.L$0 = (ComponentName) obj;
        localHomeControlsDataSourceDelegator$componentInfo$1.Z$0 = zBooleanValue;
        return localHomeControlsDataSourceDelegator$componentInfo$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new HomeControlsComponentInfo((ComponentName) this.L$0, this.Z$0);
    }
}
