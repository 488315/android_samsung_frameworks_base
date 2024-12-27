package com.android.systemui.qs.tiles.base.viewmodel;

import android.os.UserHandle;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.tiles.base.analytics.QSTileAnalytics;
import com.android.systemui.qs.tiles.base.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.qs.tiles.viewmodel.QSTileViewModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class QSTileViewModelImpl implements QSTileViewModel, Dumpable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final QSTileConfig config;
    public final DisabledByPolicyInteractor disabledByPolicyInteractor;
    public final FalsingManager falsingManager;
    public final SharedFlowImpl forceUpdates;
    public final ReadonlyStateFlow isAvailable;
    public final Function0 mapper;
    public final QSTileAnalytics qsTileAnalytics;
    public final QSTileLogger qsTileLogger;
    public final ReadonlySharedFlow state;
    public final SystemClock systemClock;
    public final ReadonlySharedFlow tileData;
    public final Function0 tileDataInteractor;
    public final CoroutineScope tileScope;
    public final Function0 userActionInteractor;
    public final SharedFlowImpl userInputs;
    public final StateFlowImpl users;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSTileViewModelImpl(QSTileConfig qSTileConfig, Function0 function0, Function0 function02, Function0 function03, DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.config = qSTileConfig;
        this.userActionInteractor = function0;
        this.tileDataInteractor = function02;
        this.mapper = function03;
        this.disabledByPolicyInteractor = disabledByPolicyInteractor;
        this.falsingManager = falsingManager;
        this.qsTileAnalytics = qSTileAnalytics;
        this.qsTileLogger = qSTileLogger;
        this.systemClock = systemClock;
        this.backgroundDispatcher = coroutineDispatcher;
        this.tileScope = coroutineScope;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle());
        this.users = MutableStateFlow;
        this.userInputs = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.forceUpdates = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(MutableStateFlow, new QSTileViewModelImpl$createTileDataFlow$$inlined$flatMapLatest$1(null, this)));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlySharedFlow shareIn = FlowKt.shareIn(distinctUntilChanged, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        this.tileData = shareIn;
        this.state = FlowKt.shareIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSTileViewModelImpl this$0;

                /* renamed from: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelImpl qSTileViewModelImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = qSTileViewModelImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl r7 = r5.this$0
                        kotlin.jvm.functions.Function0 r2 = r7.mapper
                        java.lang.Object r2 = r2.invoke()
                        com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper r2 = (com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper) r2
                        com.android.systemui.qs.tiles.viewmodel.QSTileConfig r4 = r7.config
                        com.android.systemui.qs.tiles.viewmodel.QSTileState r2 = r2.map(r4, r6)
                        com.android.systemui.qs.pipeline.shared.TileSpec r4 = r4.tileSpec
                        com.android.systemui.qs.tiles.base.logging.QSTileLogger r7 = r7.qsTileLogger
                        r7.logStateUpdate(r4, r2, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r2, r0)
                        if (r5 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        this.isAvailable = FlowKt.stateIn(FlowKt.flowOn(FlowKt.transformLatest(MutableStateFlow, new QSTileViewModelImpl$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void destroy() {
        CoroutineScopeKt.cancel(this.tileScope, null);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(this.config.tileSpec.getSpec() + ":");
        printWriter.print("    ");
        printWriter.println(String.valueOf(CollectionsKt___CollectionsKt.lastOrNull(this.state.$$delegate_0.getReplayCache())));
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void forceUpdate() {
        BuildersKt.launch$default(this.tileScope, null, null, new QSTileViewModelImpl$forceUpdate$1(this, null), 3);
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final QSTileConfig getConfig() {
        return this.config;
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final SharedFlow getState() {
        return this.state;
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final StateFlow isAvailable() {
        return this.isAvailable;
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void onActionPerformed(QSTileUserAction qSTileUserAction) {
        this.qsTileLogger.logUserAction(qSTileUserAction, this.config.tileSpec, !this.tileData.$$delegate_0.getReplayCache().isEmpty(), !this.state.$$delegate_0.getReplayCache().isEmpty());
        BuildersKt.launch$default(this.tileScope, null, null, new QSTileViewModelImpl$onActionPerformed$1(this, qSTileUserAction, null), 3);
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void onUserChanged(UserHandle userHandle) {
        this.users.updateState(null, userHandle);
    }
}
