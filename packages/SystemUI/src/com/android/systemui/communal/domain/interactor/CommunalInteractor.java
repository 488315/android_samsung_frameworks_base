package com.android.systemui.communal.domain.interactor;

import android.content.IntentFilter;
import android.os.UserManager;
import com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.Flags;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.data.repository.CommunalMediaRepository;
import com.android.systemui.communal.data.repository.CommunalMediaRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalPrefsRepository;
import com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalWidgetRepository;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.communal.widgets.EditWidgetsActivityStarter;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.smartspace.data.repository.SmartspaceRepository;
import com.android.systemui.smartspace.data.repository.SmartspaceRepositoryImpl;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalInteractor {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _editModeOpen;
    public final SharedFlowImpl _userActivity;
    public final ActivityStarter activityStarter;
    public final CommunalAppWidgetHost appWidgetHost;
    public final CoroutineDispatcher bgDispatcher;
    public final CommunalPrefsRepository communalPrefsRepository;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final ChannelFlowTransformLatest communalWidgets;
    public final CommunalInteractor$special$$inlined$map$5 ctaTileContent;
    public final StateFlow desiredScene;
    public final ReadonlyStateFlow dreamFromOccluded;
    public final ReadonlyStateFlow editModeOpen;
    public final EditWidgetsActivityStarter editWidgetsActivityStarter;
    public final ReadonlySharedFlow isCommunalAvailable;
    public final ReadonlyStateFlow isCommunalEnabled;
    public final ReadonlySharedFlow isCommunalShowing;
    public final CommunalSceneInteractor$special$$inlined$map$2 isCommunalVisible;
    public final ReadonlyStateFlow isIdleOnCommunal;
    public final Logger logger;
    public final CommunalMediaRepository mediaRepository;
    public final ReadonlyStateFlow showCommunalFromOccluded;
    public final Flow smartspaceTargets;
    public final StateFlow transitionState;
    public final List tutorialContent;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 updateOnWorkProfileBroadcastReceived;
    public final ReadonlySharedFlow userActivity;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 widgetContent;
    public final CommunalWidgetRepository widgetRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r2v27, types: [com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5] */
    public CommunalInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BroadcastDispatcher broadcastDispatcher, CommunalWidgetRepository communalWidgetRepository, CommunalPrefsRepository communalPrefsRepository, CommunalMediaRepository communalMediaRepository, SmartspaceRepository smartspaceRepository, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalAppWidgetHost communalAppWidgetHost, EditWidgetsActivityStarter editWidgetsActivityStarter, UserTracker userTracker, ActivityStarter activityStarter, UserManager userManager, CommunalSceneInteractor communalSceneInteractor, SceneInteractor sceneInteractor, LogBuffer logBuffer, TableLogBuffer tableLogBuffer) {
        Flow flow;
        this.bgDispatcher = coroutineDispatcher;
        this.widgetRepository = communalWidgetRepository;
        this.communalPrefsRepository = communalPrefsRepository;
        this.mediaRepository = communalMediaRepository;
        this.appWidgetHost = communalAppWidgetHost;
        this.editWidgetsActivityStarter = editWidgetsActivityStarter;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.userManager = userManager;
        this.communalSceneInteractor = communalSceneInteractor;
        this.logger = new Logger(logBuffer, "CommunalInteractor");
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._editModeOpen = MutableStateFlow;
        this.editModeOpen = FlowKt.asStateFlow(MutableStateFlow);
        ReadonlyStateFlow readonlyStateFlow = communalSettingsInteractor.isCommunalEnabled;
        this.isCommunalEnabled = readonlyStateFlow;
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(booleanFlowOperators.allOf(readonlyStateFlow, booleanFlowOperators.not(keyguardInteractor.isEncryptedOrLockdown), keyguardInteractor.isKeyguardShowing)), new CommunalInteractor$isCommunalAvailable$1(this, null)), tableLogBuffer, "", "isCommunalAvailable", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlySharedFlow shareIn = FlowKt.shareIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        this.isCommunalAvailable = shareIn;
        final KeyguardTransitionInteractor$special$$inlined$filter$2 keyguardTransitionInteractor$special$$inlined$filter$2 = keyguardTransitionInteractor.startedKeyguardTransitionStep;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, shareIn, CommunalInteractor$showCommunalFromOccluded$3.INSTANCE);
        this.showCommunalFromOccluded = FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r6 = r5.component1()
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        java.lang.Object r5 = r5.component2()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L4e
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r6.from
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GLANCEABLE_HUB
                        if (r5 != r6) goto L4e
                        r5 = r3
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5e
                        return r1
                    L5e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final Flow transition = keyguardTransitionInteractor.transition((Edge) Edge.Companion.create$default(Edge.Companion, null, KeyguardState.OCCLUDED, 1));
        this.dreamFromOccluded = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2$1
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r5.from
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.DREAMING
                        if (r5 != r6) goto L3c
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.Eagerly, bool);
        this.desiredScene = communalSceneInteractor.currentScene;
        this.transitionState = communalSceneInteractor.transitionState;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 1, BufferOverflow.DROP_OLDEST, 1);
        this._userActivity = MutableSharedFlow$default;
        this.userActivity = FlowKt.asSharedFlow(MutableSharedFlow$default);
        this.isCommunalShowing = FlowKt.shareIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.transformLatest(new SafeFlow(new CommunalInteractor$isCommunalShowing$1(null)), new CommunalInteractor$special$$inlined$flatMapLatest$1(null, sceneInteractor, this))), new CommunalInteractor$isCommunalShowing$3(this, null)), tableLogBuffer, "", "isCommunalShowing", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        this.isIdleOnCommunal = communalSceneInteractor.isIdleOnCommunal;
        this.isCommunalVisible = communalSceneInteractor.isCommunalVisible;
        IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        Unit unit = Unit.INSTANCE;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, m, null, 0, null, 14));
        this.communalWidgets = FlowKt.transformLatest(shareIn, new CommunalInteractor$special$$inlined$flatMapLatest$2(null, this));
        final Flow flow2 = ((CommunalWidgetRepositoryImpl) communalWidgetRepository).communalWidgets;
        this.widgetContent = FlowKt.combine(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CommunalInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalInteractor communalInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto Lbe
                    L28:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.util.List r8 = (java.util.List) r8
                        com.android.systemui.communal.domain.interactor.CommunalInteractor r9 = r7.this$0
                        com.android.systemui.settings.UserTracker r9 = r9.userTracker
                        com.android.systemui.settings.UserTrackerImpl r9 = (com.android.systemui.settings.UserTrackerImpl) r9
                        java.util.List r9 = r9.getUserProfiles()
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r4 = 10
                        int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r9, r4)
                        r2.<init>(r4)
                        java.util.Iterator r9 = r9.iterator()
                    L50:
                        boolean r4 = r9.hasNext()
                        if (r4 == 0) goto L66
                        java.lang.Object r4 = r9.next()
                        android.content.pm.UserInfo r4 = (android.content.pm.UserInfo) r4
                        int r4 = r4.id
                        java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                        r2.add(r4)
                        goto L50
                    L66:
                        java.util.Set r9 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r2.<init>()
                        java.util.Iterator r8 = r8.iterator()
                    L75:
                        boolean r4 = r8.hasNext()
                        if (r4 == 0) goto Lb3
                        java.lang.Object r4 = r8.next()
                        r5 = r4
                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel r5 = (com.android.systemui.communal.shared.model.CommunalWidgetContentModel) r5
                        boolean r6 = r5 instanceof com.android.systemui.communal.shared.model.CommunalWidgetContentModel.Available
                        if (r6 == 0) goto La2
                        r6 = r9
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Available r5 = (com.android.systemui.communal.shared.model.CommunalWidgetContentModel.Available) r5
                        android.appwidget.AppWidgetProviderInfo r5 = r5.providerInfo
                        android.os.UserHandle r5 = r5.getProfile()
                        if (r5 == 0) goto L9c
                        int r5 = r5.getIdentifier()
                        java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                        goto L9d
                    L9c:
                        r5 = 0
                    L9d:
                        boolean r5 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r6, r5)
                        goto La7
                    La2:
                        boolean r5 = r5 instanceof com.android.systemui.communal.shared.model.CommunalWidgetContentModel.Pending
                        if (r5 == 0) goto Lad
                        r5 = r3
                    La7:
                        if (r5 == 0) goto L75
                        r2.add(r4)
                        goto L75
                    Lad:
                        kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
                        r7.<init>()
                        throw r7
                    Lb3:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r2, r0)
                        if (r7 != r1) goto Lbe
                        return r1
                    Lbe:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, communalSettingsInteractor.allowedByDevicePolicyForWorkProfile, new CommunalInteractor$widgetContent$2(this, null)), communalSettingsInteractor.communalWidgetCategories, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new CommunalInteractor$widgetContent$3(this, null));
        if (Flags.remoteViews()) {
            final FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 flowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 = ((SmartspaceRepositoryImpl) smartspaceRepository).communalSmartspaceTargets;
            flow = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                        /*
                            r7 = this;
                            boolean r0 = r9 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r9
                            com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4$2$1
                            r0.<init>(r9)
                        L18:
                            java.lang.Object r9 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L69
                        L27:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r8)
                            throw r7
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r9)
                            java.util.List r8 = (java.util.List) r8
                            java.lang.Iterable r8 = (java.lang.Iterable) r8
                            java.util.ArrayList r9 = new java.util.ArrayList
                            r9.<init>()
                            java.util.Iterator r8 = r8.iterator()
                        L3f:
                            boolean r2 = r8.hasNext()
                            if (r2 == 0) goto L5e
                            java.lang.Object r2 = r8.next()
                            r4 = r2
                            android.app.smartspace.SmartspaceTarget r4 = (android.app.smartspace.SmartspaceTarget) r4
                            int r5 = r4.getFeatureType()
                            r6 = 21
                            if (r5 != r6) goto L3f
                            android.widget.RemoteViews r4 = r4.getRemoteViews()
                            if (r4 == 0) goto L3f
                            r9.add(r2)
                            goto L3f
                        L5e:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                            java.lang.Object r7 = r7.emit(r9, r0)
                            if (r7 != r1) goto L69
                            return r1
                        L69:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        } else {
            flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptyList.INSTANCE);
        }
        this.smartspaceTargets = flow;
        final ReadonlyStateFlow readonlyStateFlow2 = ((CommunalPrefsRepositoryImpl) communalPrefsRepository).isCtaDismissed;
        this.ctaTileContent = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L3d
                        kotlin.collections.EmptyList r5 = kotlin.collections.EmptyList.INSTANCE
                        goto L46
                    L3d:
                        com.android.systemui.communal.domain.model.CommunalContentModel$CtaTileInViewMode r5 = new com.android.systemui.communal.domain.model.CommunalContentModel$CtaTileInViewMode
                        r5.<init>()
                        java.util.List r5 = java.util.Collections.singletonList(r5)
                    L46:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        CommunalContentModel.Tutorial tutorial = new CommunalContentModel.Tutorial(0, CommunalContentSize.FULL);
        CommunalContentSize communalContentSize = CommunalContentSize.THIRD;
        CommunalContentModel.Tutorial tutorial2 = new CommunalContentModel.Tutorial(1, communalContentSize);
        CommunalContentModel.Tutorial tutorial3 = new CommunalContentModel.Tutorial(2, communalContentSize);
        CommunalContentModel.Tutorial tutorial4 = new CommunalContentModel.Tutorial(3, communalContentSize);
        CommunalContentSize communalContentSize2 = CommunalContentSize.HALF;
        this.tutorialContent = CollectionsKt__CollectionsKt.listOf(tutorial, tutorial2, tutorial3, tutorial4, new CommunalContentModel.Tutorial(4, communalContentSize2), new CommunalContentModel.Tutorial(5, communalContentSize2), new CommunalContentModel.Tutorial(6, communalContentSize2), new CommunalContentModel.Tutorial(7, communalContentSize2));
    }

    public final Flow getOngoingContent(boolean z) {
        return FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.smartspaceTargets, ((CommunalMediaRepositoryImpl) this.mediaRepository).mediaModel, new CommunalInteractor$getOngoingContent$1(z, null)), this.bgDispatcher);
    }
}
