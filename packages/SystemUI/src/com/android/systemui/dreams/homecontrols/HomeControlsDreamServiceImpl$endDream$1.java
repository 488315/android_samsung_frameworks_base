package com.android.systemui.dreams.homecontrols;

import androidx.lifecycle.LifecycleKt;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class HomeControlsDreamServiceImpl$endDream$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HomeControlsDreamServiceImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsDreamServiceImpl$endDream$1(HomeControlsDreamServiceImpl homeControlsDreamServiceImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsDreamServiceImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeControlsDreamServiceImpl$endDream$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsDreamServiceImpl$endDream$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HomeControlsDreamServiceImpl.Companion.getClass();
            long j = HomeControlsDreamServiceImpl.ACTIVITY_RESTART_DELAY;
            this.label = 1;
            if (DelayKt.m3449delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = this.this$0;
        HomeControlsDreamServiceImpl.Companion companion = HomeControlsDreamServiceImpl.Companion;
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(homeControlsDreamServiceImpl.$$delegate_0.getLifecycle()), null, null, new HomeControlsDreamServiceImpl$launchActivity$1(homeControlsDreamServiceImpl, null), 3);
        return Unit.INSTANCE;
    }
}
