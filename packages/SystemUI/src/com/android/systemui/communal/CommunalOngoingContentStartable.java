package com.android.systemui.communal;

import android.app.smartspace.SmartspaceSession;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.data.repository.CommunalMediaRepository;
import com.android.systemui.communal.data.repository.CommunalMediaRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepository;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
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

/* loaded from: classes2.dex */
public final class CommunalOngoingContentStartable implements CoreStartable {
    public final CoroutineScope bgScope;
    public final CommunalInteractor communalInteractor;
    public final CommunalMediaRepository communalMediaRepository;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalSmartspaceRepository communalSmartspaceRepository;
    public final boolean showUmoOnHub;

    /* renamed from: com.android.systemui.communal.CommunalOngoingContentStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalOngoingContentStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CommunalOngoingContentStartable communalOngoingContentStartable = CommunalOngoingContentStartable.this;
                ReadonlyStateFlow readonlyStateFlow = communalOngoingContentStartable.communalInteractor.isCommunalEnabled;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.CommunalOngoingContentStartable.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        CommunalOngoingContentStartable communalOngoingContentStartable2 = communalOngoingContentStartable;
                        if (zBooleanValue) {
                            if (communalOngoingContentStartable2.showUmoOnHub) {
                                CommunalMediaRepositoryImpl communalMediaRepositoryImpl = (CommunalMediaRepositoryImpl) communalOngoingContentStartable2.communalMediaRepository;
                                communalMediaRepositoryImpl.mediaDataManager.addListener(communalMediaRepositoryImpl);
                            }
                            final CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl = (CommunalSmartspaceRepositoryImpl) communalOngoingContentStartable2.communalSmartspaceRepository;
                            communalSmartspaceRepositoryImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl$startListening$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl2 = communalSmartspaceRepositoryImpl;
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
                                    CommunalSmartspaceRepositoryImpl communalSmartspaceRepositoryImpl3 = communalSmartspaceRepositoryImpl2;
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

    public CommunalOngoingContentStartable(CoroutineScope coroutineScope, CommunalInteractor communalInteractor, CommunalMediaRepository communalMediaRepository, CommunalSettingsInteractor communalSettingsInteractor, CommunalSmartspaceRepository communalSmartspaceRepository, boolean z) {
        this.bgScope = coroutineScope;
        this.communalInteractor = communalInteractor;
        this.communalMediaRepository = communalMediaRepository;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSmartspaceRepository = communalSmartspaceRepository;
        this.showUmoOnHub = z;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 7);
        }
    }
}
