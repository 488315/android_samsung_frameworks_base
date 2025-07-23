package com.android.systemui.communal;

import android.app.smartspace.SmartspaceSession;
import android.util.Log;
import com.android.systemui.communal.data.repository.CommunalMediaRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.concurrency.Execution;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalOngoingContentStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalOngoingContentStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalOngoingContentStartable$start$1(CommunalOngoingContentStartable communalOngoingContentStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalOngoingContentStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalOngoingContentStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalOngoingContentStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CommunalOngoingContentStartable communalOngoingContentStartable = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = communalOngoingContentStartable.communalInteractor.isCommunalEnabled;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.CommunalOngoingContentStartable$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    CommunalOngoingContentStartable communalOngoingContentStartable2 = CommunalOngoingContentStartable.this;
                    if (booleanValue) {
                        if (communalOngoingContentStartable2.showUmoOnHub) {
                            CommunalMediaRepositoryImpl communalMediaRepositoryImpl = (CommunalMediaRepositoryImpl) communalOngoingContentStartable2.communalMediaRepository;
                            communalMediaRepositoryImpl.mediaDataManager.addListener(communalMediaRepositoryImpl);
                        }
                        final CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl = (CommunalSmartspaceRepositoryImpl) communalOngoingContentStartable2.communalSmartspaceRepository;
                        communalSmartspaceRepositoryImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl$startListening$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl2 = CommunalSmartspaceRepositoryImpl.this;
                                CommunalSmartspaceController communalSmartspaceController = communalSmartspaceRepositoryImpl2.communalSmartspaceController;
                                communalSmartspaceController.execution.assertIsMainThread();
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = communalSmartspaceController.plugin;
                                if (bcSmartspaceDataPlugin != null) {
                                    bcSmartspaceDataPlugin.registerListener(communalSmartspaceRepositoryImpl2);
                                }
                                communalSmartspaceController.listeners.add(communalSmartspaceRepositoryImpl2);
                                communalSmartspaceController.connectSession();
                            }
                        });
                    } else {
                        if (communalOngoingContentStartable2.showUmoOnHub) {
                            CommunalMediaRepositoryImpl communalMediaRepositoryImpl2 = (CommunalMediaRepositoryImpl) communalOngoingContentStartable2.communalMediaRepository;
                            communalMediaRepositoryImpl2.mediaDataManager.removeListener(communalMediaRepositoryImpl2);
                        }
                        final CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl2 = (CommunalSmartspaceRepositoryImpl) communalOngoingContentStartable2.communalSmartspaceRepository;
                        communalSmartspaceRepositoryImpl2.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl$stopListening$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl3 = CommunalSmartspaceRepositoryImpl.this;
                                CommunalSmartspaceController communalSmartspaceController = communalSmartspaceRepositoryImpl3.communalSmartspaceController;
                                Execution execution = communalSmartspaceController.execution;
                                execution.assertIsMainThread();
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = communalSmartspaceController.plugin;
                                if (bcSmartspaceDataPlugin != null) {
                                    bcSmartspaceDataPlugin.unregisterListener(communalSmartspaceRepositoryImpl3);
                                }
                                communalSmartspaceController.listeners.remove(communalSmartspaceRepositoryImpl3);
                                if (communalSmartspaceController.listeners.isEmpty()) {
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
                            }
                        });
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
