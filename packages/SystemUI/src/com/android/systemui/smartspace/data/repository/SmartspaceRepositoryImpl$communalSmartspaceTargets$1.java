package com.android.systemui.smartspace.data.repository;

import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTargetEvent;
import android.util.Log;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.preconditions.LockscreenPrecondition;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class SmartspaceRepositoryImpl$communalSmartspaceTargets$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SmartspaceRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartspaceRepositoryImpl$communalSmartspaceTargets$1(SmartspaceRepositoryImpl smartspaceRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = smartspaceRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SmartspaceRepositoryImpl$communalSmartspaceTargets$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartspaceRepositoryImpl$communalSmartspaceTargets$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final SmartspaceRepositoryImpl smartspaceRepositoryImpl = this.this$0;
        smartspaceRepositoryImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.smartspace.data.repository.SmartspaceRepositoryImpl$communalSmartspaceTargets$1.1
            @Override // java.lang.Runnable
            public final void run() {
                SmartspaceRepositoryImpl smartspaceRepositoryImpl2 = SmartspaceRepositoryImpl.this;
                final CommunalSmartspaceController communalSmartspaceController = smartspaceRepositoryImpl2.communalSmartspaceController;
                communalSmartspaceController.execution.assertIsMainThread();
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = communalSmartspaceController.plugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.registerListener(smartspaceRepositoryImpl2);
                }
                communalSmartspaceController.listeners.add(smartspaceRepositoryImpl2);
                if (communalSmartspaceController.smartspaceManager != null && bcSmartspaceDataPlugin != null && communalSmartspaceController.session == null && (!communalSmartspaceController.listeners.isEmpty())) {
                    LockscreenPrecondition lockscreenPrecondition = (LockscreenPrecondition) communalSmartspaceController.precondition;
                    lockscreenPrecondition.execution.assertIsMainThread();
                    if (lockscreenPrecondition.deviceReady) {
                        SmartspaceSession createSmartspaceSession = communalSmartspaceController.smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(communalSmartspaceController.context, BcSmartspaceDataPlugin.UI_SURFACE_GLANCEABLE_HUB).build());
                        Log.d("CommunalSmartspaceCtrlr", "Starting smartspace session for communal");
                        createSmartspaceSession.addOnTargetsAvailableListener(communalSmartspaceController.uiExecutor, communalSmartspaceController.sessionListener);
                        communalSmartspaceController.session = createSmartspaceSession;
                        bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$connectSession$1
                            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                            public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                                SmartspaceSession smartspaceSession = CommunalSmartspaceController.this.session;
                                if (smartspaceSession != null) {
                                    smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
                                }
                            }
                        });
                        SmartspaceSession smartspaceSession = communalSmartspaceController.session;
                        if (smartspaceSession != null) {
                            smartspaceSession.requestSmartspaceUpdate();
                        }
                    }
                }
            }
        });
        return Unit.INSTANCE;
    }
}
