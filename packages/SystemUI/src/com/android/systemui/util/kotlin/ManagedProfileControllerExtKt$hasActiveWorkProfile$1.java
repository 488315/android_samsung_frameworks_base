package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
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

final class ManagedProfileControllerExtKt$hasActiveWorkProfile$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ManagedProfileController $this_hasActiveWorkProfile;
    private /* synthetic */ Object L$0;
    int label;

    public ManagedProfileControllerExtKt$hasActiveWorkProfile$1(ManagedProfileController managedProfileController, Continuation continuation) {
        super(2, continuation);
        this.$this_hasActiveWorkProfile = managedProfileController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ManagedProfileControllerExtKt$hasActiveWorkProfile$1 managedProfileControllerExtKt$hasActiveWorkProfile$1 = new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(this.$this_hasActiveWorkProfile, continuation);
        managedProfileControllerExtKt$hasActiveWorkProfile$1.L$0 = obj;
        return managedProfileControllerExtKt$hasActiveWorkProfile$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ManagedProfileController managedProfileController = this.$this_hasActiveWorkProfile;
            final ?? r1 = new ManagedProfileController.Callback() { // from class: com.android.systemui.util.kotlin.ManagedProfileControllerExtKt$hasActiveWorkProfile$1$callback$1
                @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
                public void onManagedProfileChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(((ManagedProfileControllerImpl) managedProfileController).hasActiveProfile()));
                }

                @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
                public void onManagedProfileRemoved() {
                }
            };
            ((ManagedProfileControllerImpl) this.$this_hasActiveWorkProfile).addCallback(r1);
            final ManagedProfileController managedProfileController2 = this.$this_hasActiveWorkProfile;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.ManagedProfileControllerExtKt$hasActiveWorkProfile$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2317invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2317invoke() {
                    ((ManagedProfileControllerImpl) ManagedProfileController.this).removeCallback(r1);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((ManagedProfileControllerExtKt$hasActiveWorkProfile$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
