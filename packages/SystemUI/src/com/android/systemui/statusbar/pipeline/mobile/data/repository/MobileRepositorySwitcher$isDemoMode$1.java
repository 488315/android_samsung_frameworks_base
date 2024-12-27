package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.os.Bundle;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileRepositorySwitcher$isDemoMode$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DemoModeController $demoModeController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileRepositorySwitcher this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileRepositorySwitcher$isDemoMode$1(DemoModeController demoModeController, MobileRepositorySwitcher mobileRepositorySwitcher, Continuation continuation) {
        super(2, continuation);
        this.$demoModeController = demoModeController;
        this.this$0 = mobileRepositorySwitcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileRepositorySwitcher$isDemoMode$1 mobileRepositorySwitcher$isDemoMode$1 = new MobileRepositorySwitcher$isDemoMode$1(this.$demoModeController, this.this$0, continuation);
        mobileRepositorySwitcher$isDemoMode$1.L$0 = obj;
        return mobileRepositorySwitcher$isDemoMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileRepositorySwitcher$isDemoMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.demomode.DemoMode, com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher$isDemoMode$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileRepositorySwitcher mobileRepositorySwitcher = this.this$0;
            final ?? r1 = new DemoMode() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher$isDemoMode$1$callback$1
                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void onDemoModeFinished() {
                    DemoMobileConnectionsRepository demoMobileConnectionsRepository = MobileRepositorySwitcher.this.demoMobileConnectionsRepository;
                    Job job = demoMobileConnectionsRepository.mobileDemoCommandJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    Job job2 = demoMobileConnectionsRepository.wifiDemoCommandJob;
                    if (job2 != null) {
                        job2.cancel(null);
                    }
                    demoMobileConnectionsRepository._subscriptions.setValue(EmptyList.INSTANCE);
                    ((LinkedHashMap) demoMobileConnectionsRepository.connectionRepoCache).clear();
                    ((LinkedHashMap) demoMobileConnectionsRepository.subscriptionInfoCache).clear();
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.FALSE);
                }

                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void dispatchDemoCommand(Bundle bundle, String str) {
                }
            };
            this.$demoModeController.addCallback((DemoMode) r1);
            final DemoModeController demoModeController = this.$demoModeController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher$isDemoMode$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DemoModeController.this.removeCallback((DemoMode) r1);
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
