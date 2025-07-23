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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RemoteHomeControlsDataSourceDelegator extends FlowDumperImpl implements HomeControlsDataSource {
    public final ObservableServiceConnection.Callback<HomeControlsRemoteProxy> callback;
    public final Flow componentInfo;
    public final Lazy connectionManager$delegate;
    public final DreamLogger logger;
    public final MutableStateFlow proxyState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return ((DaggerReferenceGlobalRootComponent.HomeControlsRemoteServiceComponentImpl) HomeControlsRemoteServiceComponent.Factory.this.create(this.callback)).getConnectionManager();
            }
        });
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        final StateFlow subscriptionCount = MutableStateFlow.getSubscriptionCount();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1$2$1 r0 = (com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1$2$1 r0 = new com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        if (r5 <= 0) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$proxyState$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new RemoteHomeControlsDataSourceDelegator$proxyState$1$2(null))), new RemoteHomeControlsDataSourceDelegator$proxyState$1$3(this, null)), coroutineScope);
        Unit unit = Unit.INSTANCE;
        MutableStateFlow mutableStateFlow = (MutableStateFlow) dumpValue(MutableStateFlow, "proxyState");
        this.proxyState = mutableStateFlow;
        this.callback = new ObservableServiceConnection.Callback<HomeControlsRemoteProxy>() { // from class: com.android.systemui.dreams.homecontrols.service.RemoteHomeControlsDataSourceDelegator$callback$1
            @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
            public final void onConnected(ObservableServiceConnection<HomeControlsRemoteProxy> observableServiceConnection, HomeControlsRemoteProxy homeControlsRemoteProxy) {
                RemoteHomeControlsDataSourceDelegator remoteHomeControlsDataSourceDelegator = RemoteHomeControlsDataSourceDelegator.this;
                Logger.d$default(remoteHomeControlsDataSourceDelegator.logger, "Service connected", null, 2, null);
                remoteHomeControlsDataSourceDelegator.proxyState.setValue(homeControlsRemoteProxy);
            }

            @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
            public final void onDisconnected(ObservableServiceConnection<HomeControlsRemoteProxy> observableServiceConnection, int i) {
                RemoteHomeControlsDataSourceDelegator remoteHomeControlsDataSourceDelegator = RemoteHomeControlsDataSourceDelegator.this;
                DreamLogger dreamLogger = remoteHomeControlsDataSourceDelegator.logger;
                RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 = new RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0(0);
                LogMessage obtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0, null);
                obtain.setInt1(i);
                dreamLogger.getBuffer().commit(obtain);
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
