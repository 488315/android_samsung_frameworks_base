package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.os.Bundle;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class MobileRepositorySwitcherKairos$isDemoMode$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DemoModeController $demoModeController;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileRepositorySwitcherKairos$isDemoMode$1$1(DemoModeController demoModeController, Continuation continuation) {
        super(2, continuation);
        this.$demoModeController = demoModeController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileRepositorySwitcherKairos$isDemoMode$1$1 mobileRepositorySwitcherKairos$isDemoMode$1$1 = new MobileRepositorySwitcherKairos$isDemoMode$1$1(this.$demoModeController, continuation);
        mobileRepositorySwitcherKairos$isDemoMode$1$1.L$0 = obj;
        return mobileRepositorySwitcherKairos$isDemoMode$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileRepositorySwitcherKairos$isDemoMode$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.demomode.DemoMode, com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$isDemoMode$1$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new DemoMode() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$isDemoMode$1$1$callback$1
                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void onDemoModeFinished() {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.FALSE);
                }

                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void dispatchDemoCommand(Bundle bundle, String str) {
                }
            };
            this.$demoModeController.addCallback((DemoMode) r1);
            final DemoModeController demoModeController = this.$demoModeController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcherKairos$isDemoMode$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    demoModeController.removeCallback((DemoMode) r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
