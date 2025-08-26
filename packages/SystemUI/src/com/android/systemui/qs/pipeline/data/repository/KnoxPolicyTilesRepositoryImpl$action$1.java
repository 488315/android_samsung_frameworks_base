package com.android.systemui.qs.pipeline.data.repository;

import android.util.Log;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class KnoxPolicyTilesRepositoryImpl$action$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KnoxPolicyTilesRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnoxPolicyTilesRepositoryImpl$action$1(KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = knoxPolicyTilesRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KnoxPolicyTilesRepositoryImpl$action$1 knoxPolicyTilesRepositoryImpl$action$1 = new KnoxPolicyTilesRepositoryImpl$action$1(this.this$0, continuation);
        knoxPolicyTilesRepositoryImpl$action$1.L$0 = obj;
        return knoxPolicyTilesRepositoryImpl$action$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KnoxPolicyTilesRepositoryImpl$action$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.knox.KnoxStateMonitorCallback, com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$action$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl = this.this$0;
            final ?? r1 = new KnoxStateMonitorCallback() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$action$1$callback$1
                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateQuickPanelButtons() {
                    KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl2 = knoxPolicyTilesRepositoryImpl;
                    Log.d("KnoxPolicyTilesRepository", "onUpdateQuickPanelButtons : " + ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl2.knoxStateMonitor).isBrightnessControllerEnabled());
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new KnoxPolicyTilesRepositoryImpl.KnoxAction.UpdateEnable(((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl2.knoxStateMonitor).isBrightnessControllerEnabled()));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateQuickPanelItems() {
                    List quickPanelItems = ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl.knoxStateMonitor).getQuickPanelItems();
                    if (quickPanelItems == null) {
                        quickPanelItems = EmptyList.INSTANCE;
                    }
                    Log.d("KnoxPolicyTilesRepository", "onUpdateQuickPanelItems : " + quickPanelItems);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new KnoxPolicyTilesRepositoryImpl.KnoxAction.BlockTile(quickPanelItems));
                }

                @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                public final void onUpdateQuickPanelUnavailableButtons() {
                    List quickPanelUnavailableButtons = ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl.knoxStateMonitor).getQuickPanelUnavailableButtons();
                    if (quickPanelUnavailableButtons == null) {
                        quickPanelUnavailableButtons = EmptyList.INSTANCE;
                    }
                    Log.d("KnoxPolicyTilesRepository", "onUpdateQuickPanelUnavailableButtons : " + quickPanelUnavailableButtons);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new KnoxPolicyTilesRepositoryImpl.KnoxAction.UnavailableTile(quickPanelUnavailableButtons));
                }
            };
            ((KnoxStateMonitorImpl) this.this$0.knoxStateMonitor).registerCallback(r1);
            final KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$action$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl2.knoxStateMonitor).removeCallback(r1);
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
