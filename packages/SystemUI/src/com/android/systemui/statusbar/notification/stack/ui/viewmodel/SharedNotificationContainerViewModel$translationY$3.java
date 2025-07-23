package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$translationY$3 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ boolean Z$0;
    int label;

    public SharedNotificationContainerViewModel$translationY$3(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj).floatValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        float floatValue2 = ((Number) obj3).floatValue();
        SharedNotificationContainerViewModel$translationY$3 sharedNotificationContainerViewModel$translationY$3 = new SharedNotificationContainerViewModel$translationY$3((Continuation) obj4);
        sharedNotificationContainerViewModel$translationY$3.F$0 = floatValue;
        sharedNotificationContainerViewModel$translationY$3.Z$0 = booleanValue;
        sharedNotificationContainerViewModel$translationY$3.F$1 = floatValue2;
        return sharedNotificationContainerViewModel$translationY$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        boolean z = this.Z$0;
        float f2 = this.F$1;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        return new Float(z ? f + f2 : 0.0f);
    }
}
