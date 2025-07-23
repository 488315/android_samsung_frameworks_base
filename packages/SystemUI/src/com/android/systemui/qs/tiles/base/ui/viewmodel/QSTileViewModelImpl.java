package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dumpable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.base.ui.analytics.QSTileAnalytics;
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
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
    public final ReadonlyStateFlow state;
    public final SystemClock systemClock;
    public final ReadonlyStateFlow tileData;
    public final Function0 tileDataInteractor;
    public final TileDetailsViewModel tileDetailsViewModel;
    public final CoroutineScope tileScope;
    public final Function0 userActionInteractor;
    public final SharedFlowImpl userInputs;
    public final StateFlowImpl users;

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

    public QSTileViewModelImpl(QSTileConfig qSTileConfig, Function0 function0, Function0 function02, Function0 function03, DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, TileDetailsViewModel tileDetailsViewModel) {
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
        this.tileDetailsViewModel = tileDetailsViewModel;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle());
        this.users = MutableStateFlow;
        this.userInputs = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.forceUpdates = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        Flow flowOn = FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(MutableStateFlow, new QSTileViewModelImpl$createTileDataFlow$1(this, null))), coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.tileData = stateIn;
        this.state = FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSTileViewModelImpl this$0;

                /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L81
                    L27:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r11)
                        r11 = 0
                        if (r10 == 0) goto L76
                        com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl r2 = r9.this$0
                        kotlin.jvm.functions.Function0 r4 = r2.mapper
                        java.lang.Object r4 = r4.invoke()
                        com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper r4 = (com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper) r4
                        com.android.systemui.qs.tiles.base.shared.model.QSTileConfig r5 = r2.config
                        com.android.systemui.qs.tiles.base.shared.model.QSTileState r4 = r4.map(r5, r10)
                        com.android.systemui.qs.pipeline.shared.TileSpec r5 = r5.tileSpec
                        com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger r2 = r2.qsTileLogger
                        com.android.systemui.log.LogBuffer r2 = r2.getLogBuffer(r5)
                        java.lang.String r5 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.getLogTag(r5)
                        com.android.systemui.log.core.LogLevel r6 = com.android.systemui.log.core.LogLevel.DEBUG
                        com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0 r7 = new com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0
                        r8 = 7
                        r7.<init>(r8)
                        com.android.systemui.log.core.LogMessage r11 = r2.obtain(r5, r6, r7, r11)
                        java.lang.String r5 = com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger.toLogString(r4)
                        r6 = r11
                        com.android.systemui.log.LogMessageImpl r6 = (com.android.systemui.log.LogMessageImpl) r6
                        r6.str1 = r5
                        java.lang.String r10 = java.lang.String.valueOf(r10)
                        r5 = 50
                        java.lang.String r10 = kotlin.text.StringsKt___StringsKt.take(r5, r10)
                        r6.str2 = r10
                        r2.commit(r11)
                        r11 = r4
                    L76:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r11, r0)
                        if (r9 != r1) goto L81
                        return r1
                    L81:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher2), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isAvailable = FlowKt.stateIn(FlowKt.flowOn(FlowKt.transformLatest(MutableStateFlow, new QSTileViewModelImpl$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void destroy() {
        CoroutineScopeKt.cancel(this.tileScope, null);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(this.config.tileSpec.getSpec() + ":");
        printWriter.print("    ");
        printWriter.println(String.valueOf(CollectionsKt___CollectionsKt.lastOrNull(this.state.$$delegate_0.getReplayCache())));
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void forceUpdate() {
        CoroutineTracingKt.launchTraced$default(this.tileScope, this.backgroundDispatcher, null, new QSTileViewModelImpl$forceUpdate$1(this, null), 5);
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final QSTileConfig getConfig() {
        return this.config;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final int getCurrentTileUser() {
        return ((UserHandle) this.users.getValue()).getIdentifier();
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final StateFlow getState() {
        return this.state;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final TileDetailsViewModel getTileDetailsViewModel() {
        return this.tileDetailsViewModel;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final StateFlow isAvailable() {
        return this.isAvailable;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void onActionPerformed(QSTileUserAction qSTileUserAction) {
        TileSpec tileSpec = this.config.tileSpec;
        boolean z = !this.tileData.$$delegate_0.getReplayCache().isEmpty();
        boolean z2 = !this.state.$$delegate_0.getReplayCache().isEmpty();
        QSTileLogger qSTileLogger = this.qsTileLogger;
        LogBuffer logBuffer = qSTileLogger.getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(5), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = QSTileLogger.toLogString(qSTileUserAction);
        logMessageImpl.int1 = qSTileLogger.mStatusBarStateController.getState();
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z;
        logBuffer.commit(obtain);
        CoroutineTracingKt.launchTraced$default(this.tileScope, this.backgroundDispatcher, null, new QSTileViewModelImpl$onActionPerformed$1(this, qSTileUserAction, null), 5);
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void onUserChanged(UserHandle userHandle) {
        this.users.setValue(userHandle);
    }

    public /* synthetic */ QSTileViewModelImpl(QSTileConfig qSTileConfig, Function0 function0, Function0 function02, Function0 function03, DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, TileDetailsViewModel tileDetailsViewModel, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qSTileConfig, function0, function02, function03, disabledByPolicyInteractor, userRepository, falsingManager, qSTileAnalytics, qSTileLogger, systemClock, coroutineDispatcher, coroutineDispatcher2, coroutineScope, (i & 8192) != 0 ? null : tileDetailsViewModel);
    }
}
