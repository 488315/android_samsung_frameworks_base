package com.android.systemui.statusbar.disableflags.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DisableFlagsRepositoryImpl$disableFlags$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CommandQueue $commandQueue;
    final /* synthetic */ RemoteInputQuickSettingsDisabler $remoteInputQuickSettingsDisabler;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisableFlagsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisableFlagsRepositoryImpl$disableFlags$1(CommandQueue commandQueue, DisableFlagsRepositoryImpl disableFlagsRepositoryImpl, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, Continuation continuation) {
        super(2, continuation);
        this.$commandQueue = commandQueue;
        this.this$0 = disableFlagsRepositoryImpl;
        this.$remoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisableFlagsRepositoryImpl$disableFlags$1 disableFlagsRepositoryImpl$disableFlags$1 = new DisableFlagsRepositoryImpl$disableFlags$1(this.$commandQueue, this.this$0, this.$remoteInputQuickSettingsDisabler, continuation);
        disableFlagsRepositoryImpl$disableFlags$1.L$0 = obj;
        return disableFlagsRepositoryImpl$disableFlags$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisableFlagsRepositoryImpl$disableFlags$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.CommandQueue$Callbacks, com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl$disableFlags$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DisableFlagsRepositoryImpl disableFlagsRepositoryImpl = this.this$0;
            final RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler = this.$remoteInputQuickSettingsDisabler;
            final ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl$disableFlags$1$callback$1
                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void disable(int i2, int i3, int i4, boolean z) {
                    if (i2 != DisableFlagsRepositoryImpl.this.thisDisplayId) {
                        return;
                    }
                    remoteInputQuickSettingsDisabler.getClass();
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(new DisableFlagsModel(i3, i4));
                }
            };
            this.$commandQueue.addCallback((CommandQueue.Callbacks) r1);
            final CommandQueue commandQueue = this.$commandQueue;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.disableflags.data.repository.DisableFlagsRepositoryImpl$disableFlags$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CommandQueue.this.removeCallback((CommandQueue.Callbacks) r1);
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
