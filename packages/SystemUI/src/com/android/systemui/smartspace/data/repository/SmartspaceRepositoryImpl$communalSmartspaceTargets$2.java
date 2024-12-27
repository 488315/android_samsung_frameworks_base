package com.android.systemui.smartspace.data.repository;

import android.app.smartspace.SmartspaceSession;
import android.util.Log;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.concurrency.Execution;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SmartspaceRepositoryImpl$communalSmartspaceTargets$2 extends SuspendLambda implements Function3 {
    int label;
    final /* synthetic */ SmartspaceRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartspaceRepositoryImpl$communalSmartspaceTargets$2(SmartspaceRepositoryImpl smartspaceRepositoryImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = smartspaceRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new SmartspaceRepositoryImpl$communalSmartspaceTargets$2(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final SmartspaceRepositoryImpl smartspaceRepositoryImpl = this.this$0;
        smartspaceRepositoryImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.smartspace.data.repository.SmartspaceRepositoryImpl$communalSmartspaceTargets$2.1
            @Override // java.lang.Runnable
            public final void run() {
                SmartspaceRepositoryImpl smartspaceRepositoryImpl2 = SmartspaceRepositoryImpl.this;
                CommunalSmartspaceController communalSmartspaceController = smartspaceRepositoryImpl2.communalSmartspaceController;
                Execution execution = communalSmartspaceController.execution;
                execution.assertIsMainThread();
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = communalSmartspaceController.plugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.unregisterListener(smartspaceRepositoryImpl2);
                }
                communalSmartspaceController.listeners.remove(smartspaceRepositoryImpl2);
                if (!communalSmartspaceController.listeners.isEmpty()) {
                    return;
                }
                execution.assertIsMainThread();
                SmartspaceSession smartspaceSession = communalSmartspaceController.session;
                if (smartspaceSession == null) {
                    return;
                }
                smartspaceSession.removeOnTargetsAvailableListener(communalSmartspaceController.sessionListener);
                smartspaceSession.close();
                communalSmartspaceController.session = null;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(null);
                }
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(EmptyList.INSTANCE);
                }
                Log.d("CommunalSmartspaceCtrlr", "Ending smartspace session for communal");
            }
        });
        return Unit.INSTANCE;
    }
}
