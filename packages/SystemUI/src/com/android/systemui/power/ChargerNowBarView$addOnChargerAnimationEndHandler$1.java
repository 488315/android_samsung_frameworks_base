package com.android.systemui.power;

import android.util.Slog;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class ChargerNowBarView$addOnChargerAnimationEndHandler$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $totalDuration;
    int label;
    final /* synthetic */ ChargerNowBarView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChargerNowBarView$addOnChargerAnimationEndHandler$1(long j, ChargerNowBarView chargerNowBarView, Continuation continuation) {
        super(2, continuation);
        this.$totalDuration = j;
        this.this$0 = chargerNowBarView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ChargerNowBarView$addOnChargerAnimationEndHandler$1(this.$totalDuration, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChargerNowBarView$addOnChargerAnimationEndHandler$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$totalDuration;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Slog.i("PowerUI.ChargerNowBarView", "addOnChargerAnimationEndHandler, call onChargerAnimationEnd()");
        ChargerAnimationListener chargerAnimationListener = this.this$0.animationListener;
        if (chargerAnimationListener != null) {
            SecPowerUI secPowerUI = (SecPowerUI) chargerAnimationListener;
            Slog.i("PowerUI", "onChargerAnimationEnd");
            secPowerUI.removeChargerView();
            secPowerUI.mDozeChargingHelper.restoreDisplayStateWhenDozeCharging();
        }
        return Unit.INSTANCE;
    }
}
