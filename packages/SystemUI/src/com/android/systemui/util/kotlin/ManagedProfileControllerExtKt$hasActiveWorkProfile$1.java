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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ManagedProfileControllerExtKt$hasActiveWorkProfile$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ManagedProfileController $this_hasActiveWorkProfile;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ManagedProfileControllerExtKt$hasActiveWorkProfile$1(ManagedProfileController managedProfileController, Continuation continuation) {
        super(2, continuation);
        this.$this_hasActiveWorkProfile = managedProfileController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(ManagedProfileController managedProfileController, ManagedProfileControllerExtKt$hasActiveWorkProfile$1$callback$1 managedProfileControllerExtKt$hasActiveWorkProfile$1$callback$1) {
        ((ManagedProfileControllerImpl) managedProfileController).removeCallback(managedProfileControllerExtKt$hasActiveWorkProfile$1$callback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ManagedProfileControllerExtKt$hasActiveWorkProfile$1 managedProfileControllerExtKt$hasActiveWorkProfile$1 = new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(this.$this_hasActiveWorkProfile, continuation);
        managedProfileControllerExtKt$hasActiveWorkProfile$1.L$0 = obj;
        return managedProfileControllerExtKt$hasActiveWorkProfile$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.util.kotlin.ManagedProfileControllerExtKt$hasActiveWorkProfile$1$callback$1, java.lang.Object] */
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
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Boolean.valueOf(((ManagedProfileControllerImpl) managedProfileController).hasActiveProfile()));
                }

                @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
                public void onManagedProfileRemoved() {
                }
            };
            ((ManagedProfileControllerImpl) this.$this_hasActiveWorkProfile).addCallback(r1);
            final ManagedProfileController managedProfileController2 = this.$this_hasActiveWorkProfile;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.ManagedProfileControllerExtKt$hasActiveWorkProfile$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit invokeSuspend$lambda$0;
                    invokeSuspend$lambda$0 = ManagedProfileControllerExtKt$hasActiveWorkProfile$1.invokeSuspend$lambda$0(ManagedProfileController.this, r1);
                    return invokeSuspend$lambda$0;
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
