package com.android.systemui.dock;

import com.android.systemui.dock.DockManager;
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

final class DockManagerExtensionsKt$retrieveIsDocked$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DockManager $this_retrieveIsDocked;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DockManagerExtensionsKt$retrieveIsDocked$1(DockManager dockManager, Continuation continuation) {
        super(2, continuation);
        this.$this_retrieveIsDocked = dockManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DockManagerExtensionsKt$retrieveIsDocked$1 dockManagerExtensionsKt$retrieveIsDocked$1 = new DockManagerExtensionsKt$retrieveIsDocked$1(this.$this_retrieveIsDocked, continuation);
        dockManagerExtensionsKt$retrieveIsDocked$1.L$0 = obj;
        return dockManagerExtensionsKt$retrieveIsDocked$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DockManagerExtensionsKt$retrieveIsDocked$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DockManager dockManager = this.$this_retrieveIsDocked;
            final DockManager.DockEventListener dockEventListener = new DockManager.DockEventListener(producerScope, dockManager) { // from class: com.android.systemui.dock.DockManagerExtensionsKt$retrieveIsDocked$1$callback$1
            };
            this.$this_retrieveIsDocked.getClass();
            this.$this_retrieveIsDocked.getClass();
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.FALSE);
            final DockManager dockManager2 = this.$this_retrieveIsDocked;
            Function0 function0 = new Function0() { // from class: com.android.systemui.dock.DockManagerExtensionsKt$retrieveIsDocked$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DockManager.this.getClass();
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
