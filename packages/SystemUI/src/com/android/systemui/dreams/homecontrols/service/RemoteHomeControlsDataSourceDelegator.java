package com.android.systemui.dreams.homecontrols.service;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.dreams.homecontrols.dagger.HomeControlsRemoteServiceComponent;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.service.ObservableServiceConnection;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class RemoteHomeControlsDataSourceDelegator extends FlowDumperImpl implements HomeControlsDataSource {
    public final ObservableServiceConnection.Callback<HomeControlsRemoteProxy> callback;
    public final Flow componentInfo;
    public final Lazy connectionManager$delegate;
    public final DreamLogger logger;
    public final MutableStateFlow proxyState;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public RemoteHomeControlsDataSourceDelegator(CoroutineScope coroutineScope, final HomeControlsRemoteServiceComponent.Factory factory, LogBuffer logBuffer, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.logger = new DreamLogger(logBuffer, "HomeControlsRemoteDataSourceDelegator");
        this.connectionManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((DaggerReferenceGlobalRootComponent.HomeControlsRemoteServiceComponentImpl) factory.create(this.callback)).getConnectionManager();
            }
        });
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        final StateFlow subscriptionCount = stateFlowImplMutableStateFlow.getSubscriptionCount();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1

            /* renamed from: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).intValue() > 0);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = subscriptionCount.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new RemoteHomeControlsDataSourceDelegator$proxyState$1$2(null))), new RemoteHomeControlsDataSourceDelegator$proxyState$1$3(this, null)), coroutineScope);
        Unit unit = Unit.INSTANCE;
        MutableStateFlow mutableStateFlow = (MutableStateFlow) dumpValue(stateFlowImplMutableStateFlow, "proxyState");
        this.proxyState = mutableStateFlow;
        this.callback = new ObservableServiceConnection.Callback<HomeControlsRemoteProxy>() { // from class: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$callback$1
            @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
            public final void onConnected(ObservableServiceConnection<HomeControlsRemoteProxy> observableServiceConnection, HomeControlsRemoteProxy homeControlsRemoteProxy) {
                RemoteHomeControlsDataSourceDelegator remoteHomeControlsDataSourceDelegator = this.this$0;
                Logger.d$default(remoteHomeControlsDataSourceDelegator.logger, "Service connected", null, 2, null);
                remoteHomeControlsDataSourceDelegator.proxyState.setValue(homeControlsRemoteProxy);
            }

            @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
            public final void onDisconnected(ObservableServiceConnection<HomeControlsRemoteProxy> observableServiceConnection, int i) {
                RemoteHomeControlsDataSourceDelegator remoteHomeControlsDataSourceDelegator = this.this$0;
                DreamLogger dreamLogger = remoteHomeControlsDataSourceDelegator.logger;
                RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 = new RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0(0);
                LogMessage logMessageObtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0, null);
                logMessageObtain.setInt1(i);
                dreamLogger.getBuffer().commit(logMessageObtain);
                remoteHomeControlsDataSourceDelegator.proxyState.setValue(null);
            }
        };
        this.componentInfo = dumpWhileCollecting(FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(mutableStateFlow), new RemoteHomeControlsDataSourceDelegator$special$$inlined$flatMapLatest$1(null)), "componentInfo");
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource
    public final Flow getComponentInfo() {
        return this.componentInfo;
    }
}
