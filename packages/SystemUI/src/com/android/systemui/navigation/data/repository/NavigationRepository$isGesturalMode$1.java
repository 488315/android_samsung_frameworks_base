package com.android.systemui.navigation.data.repository;

import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.shared.system.QuickStepContract;
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

/* loaded from: classes2.dex */
final class NavigationRepository$isGesturalMode$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NavigationRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavigationRepository$isGesturalMode$1(NavigationRepository navigationRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = navigationRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NavigationRepository$isGesturalMode$1 navigationRepository$isGesturalMode$1 = new NavigationRepository$isGesturalMode$1(this.this$0, continuation);
        navigationRepository$isGesturalMode$1.L$0 = obj;
        return navigationRepository$isGesturalMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NavigationRepository$isGesturalMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.navigation.data.repository.NavigationRepository$isGesturalMode$1$listener$1, com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.navigation.data.repository.NavigationRepository$isGesturalMode$1$listener$1
                @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
                public final void onNavigationModeChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(QuickStepContract.isGesturalMode(i2)));
                }
            };
            ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(QuickStepContract.isGesturalMode(this.this$0.controller.addListener(r1))));
            final NavigationRepository navigationRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.navigation.data.repository.NavigationRepository$isGesturalMode$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    navigationRepository.controller.removeListener(r1);
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
