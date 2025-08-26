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
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.base.ui.analytics.QSTileAnalytics;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt___StringsKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$forceUpdate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSTileViewModelImpl.this.new AnonymousClass1(continuation);
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
                SharedFlowImpl sharedFlowImpl = QSTileViewModelImpl.this.forceUpdates;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (sharedFlowImpl.emit(unit, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$onActionPerformed$1, reason: invalid class name and case insensitive filesystem */
    final class C10131 extends SuspendLambda implements Function2 {
        final /* synthetic */ QSTileUserAction $userAction;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10131(QSTileUserAction qSTileUserAction, Continuation continuation) {
            super(2, continuation);
            this.$userAction = qSTileUserAction;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSTileViewModelImpl.this.new C10131(this.$userAction, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10131) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlowImpl sharedFlowImpl = QSTileViewModelImpl.this.userInputs;
                QSTileUserAction qSTileUserAction = this.$userAction;
                this.label = 1;
                if (sharedFlowImpl.emit(qSTileUserAction, this) == coroutineSingletons) {
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle());
        this.users = stateFlowImplMutableStateFlow;
        this.userInputs = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.forceUpdates = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        Flow flowFlowOn = FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new QSTileViewModelImpl$createTileDataFlow$1(this, null))), coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.tileData = readonlyStateFlowStateIn;
        this.state = FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$special$$inlined$map$1

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
                        QSTileState qSTileState = null;
                        if (obj != null) {
                            QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
                            QSTileDataToStateMapper qSTileDataToStateMapper = (QSTileDataToStateMapper) qSTileViewModelImpl.mapper.invoke();
                            QSTileConfig qSTileConfig = qSTileViewModelImpl.config;
                            QSTileState map = qSTileDataToStateMapper.map(qSTileConfig, obj);
                            TileSpec tileSpec = qSTileConfig.tileSpec;
                            LogBuffer logBuffer = qSTileViewModelImpl.qsTileLogger.getLogBuffer(tileSpec);
                            LogMessage logMessageObtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(7), null);
                            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                            logMessageImpl.str1 = QSTileLogger.toLogString(map);
                            logMessageImpl.str2 = StringsKt___StringsKt.take(50, String.valueOf(obj));
                            logBuffer.commit(logMessageObtain);
                            qSTileState = map;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(qSTileState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher2), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isAvailable = FlowKt.stateIn(FlowKt.flowOn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new QSTileViewModelImpl$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
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
        CoroutineTracingKt.launchTraced$default(this.tileScope, this.backgroundDispatcher, null, new AnonymousClass1(null), 5);
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
        LogMessage logMessageObtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(5), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = QSTileLogger.toLogString(qSTileUserAction);
        logMessageImpl.int1 = qSTileLogger.mStatusBarStateController.getState();
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z;
        logBuffer.commit(logMessageObtain);
        CoroutineTracingKt.launchTraced$default(this.tileScope, this.backgroundDispatcher, null, new C10131(qSTileUserAction, null), 5);
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void onUserChanged(UserHandle userHandle) {
        this.users.setValue(userHandle);
    }

    public /* synthetic */ QSTileViewModelImpl(QSTileConfig qSTileConfig, Function0 function0, Function0 function02, Function0 function03, DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, TileDetailsViewModel tileDetailsViewModel, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qSTileConfig, function0, function02, function03, disabledByPolicyInteractor, userRepository, falsingManager, qSTileAnalytics, qSTileLogger, systemClock, coroutineDispatcher, coroutineDispatcher2, coroutineScope, (i & 8192) != 0 ? null : tileDetailsViewModel);
    }
}
