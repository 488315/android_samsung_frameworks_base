package com.android.systemui.shade.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.model.ShadeModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.shade.data.repository.ShadeRepositoryImpl$shadeModel$1", m277f = "ShadeRepository.kt", m278l = {74}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ShadeRepositoryImpl$shadeModel$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ShadeExpansionStateManager $shadeExpansionStateManager;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeRepositoryImpl$shadeModel$1(ShadeExpansionStateManager shadeExpansionStateManager, Continuation<? super ShadeRepositoryImpl$shadeModel$1> continuation) {
        super(2, continuation);
        this.$shadeExpansionStateManager = shadeExpansionStateManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeRepositoryImpl$shadeModel$1 shadeRepositoryImpl$shadeModel$1 = new ShadeRepositoryImpl$shadeModel$1(this.$shadeExpansionStateManager, continuation);
        shadeRepositoryImpl$shadeModel$1.L$0 = obj;
        return shadeRepositoryImpl$shadeModel$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeRepositoryImpl$shadeModel$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.ShadeExpansionListener, com.android.systemui.shade.data.repository.ShadeRepositoryImpl$shadeModel$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ShadeExpansionListener() { // from class: com.android.systemui.shade.data.repository.ShadeRepositoryImpl$shadeModel$1$callback$1
                @Override // com.android.systemui.shade.ShadeExpansionListener
                public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                    ShadeModel shadeModel = new ShadeModel(shadeExpansionChangeEvent.fraction, shadeExpansionChangeEvent.expanded, shadeExpansionChangeEvent.tracking);
                    ChannelExt.INSTANCE.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, shadeModel, "ShadeRepository", "updated shade expansion info");
                }
            };
            r1.onPanelExpansionChanged(this.$shadeExpansionStateManager.addExpansionListener(r1));
            ChannelExt channelExt = ChannelExt.INSTANCE;
            ShadeModel shadeModel = new ShadeModel(0.0f, false, false, 7, null);
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, shadeModel, "ShadeRepository", "initial shade expansion info");
            final ShadeExpansionStateManager shadeExpansionStateManager = this.$shadeExpansionStateManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shade.data.repository.ShadeRepositoryImpl$shadeModel$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ShadeExpansionStateManager shadeExpansionStateManager2 = ShadeExpansionStateManager.this;
                    shadeExpansionStateManager2.expansionListeners.remove(r1);
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
