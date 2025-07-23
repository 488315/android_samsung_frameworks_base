package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.Dependency;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DoNotDisturbQuickAffordanceConfig$lockScreenState$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$2(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = doNotDisturbQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$2 doNotDisturbQuickAffordanceConfig$lockScreenState$2 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(this.this$0, continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$2.L$0 = obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DoNotDisturbQuickAffordanceConfig$lockScreenState$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = this.this$0;
            ?? r1 = new ZenModeController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1
                @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
                public final void onZenAvailableChanged(boolean z) {
                    DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = DoNotDisturbQuickAffordanceConfig.this;
                    doNotDisturbQuickAffordanceConfig2.oldIsAvailable = z;
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, DoNotDisturbQuickAffordanceConfig.access$updateState(doNotDisturbQuickAffordanceConfig2), "DoNotDisturbQuickAffordanceConfig");
                    ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
                }

                @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
                public final void onZenChanged(int i2) {
                    DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = DoNotDisturbQuickAffordanceConfig.this;
                    doNotDisturbQuickAffordanceConfig2.zenMode = i2;
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, DoNotDisturbQuickAffordanceConfig.access$updateState(doNotDisturbQuickAffordanceConfig2), "DoNotDisturbQuickAffordanceConfig");
                    ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
                }
            };
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = this.this$0;
            doNotDisturbQuickAffordanceConfig2.zenMode = ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig2.controller).mZenMode;
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig3 = this.this$0;
            doNotDisturbQuickAffordanceConfig3.oldIsAvailable = ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig3.controller).isZenAvailable();
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, DoNotDisturbQuickAffordanceConfig.access$updateState(this.this$0), "DoNotDisturbQuickAffordanceConfig");
            ((ZenModeControllerImpl) this.this$0.controller).addCallback(r1);
            DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1 doNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1 = new DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1(this.this$0, (DoNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1) r1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, doNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
