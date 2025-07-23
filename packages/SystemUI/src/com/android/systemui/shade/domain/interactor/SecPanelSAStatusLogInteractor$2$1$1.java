package com.android.systemui.shade.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SecPanelSAStatusLogInteractor$2$1$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SecPanelSAStatusLogInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPanelSAStatusLogInteractor$2$1$1(SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secPanelSAStatusLogInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecPanelSAStatusLogInteractor$2$1$1 secPanelSAStatusLogInteractor$2$1$1 = new SecPanelSAStatusLogInteractor$2$1$1(this.this$0, continuation);
        secPanelSAStatusLogInteractor$2$1$1.Z$0 = ((Boolean) obj).booleanValue();
        return secPanelSAStatusLogInteractor$2$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((SecPanelSAStatusLogInteractor$2$1$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("expanded: ", "SecPanelSAStatusLogInteractor", z);
        if (!z) {
            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(this.this$0, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
        }
        return Unit.INSTANCE;
    }
}
