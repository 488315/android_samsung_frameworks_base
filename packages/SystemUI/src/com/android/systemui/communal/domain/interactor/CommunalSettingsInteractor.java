package com.android.systemui.communal.domain.interactor;

import com.android.settingslib.flags.Flags;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.communal.data.model.CommunalEnabledState;
import com.android.systemui.communal.data.model.CommunalWidgetCategories;
import com.android.systemui.communal.data.model.DisabledReason;
import com.android.systemui.communal.data.repository.CommunalSettingsRepository;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.EnumSet;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class CommunalSettingsInteractor {
    public final ReadonlyStateFlow allowedByDevicePolicyForWorkProfile;
    public final Executor bgExecutor;
    public final Flow communalBackground;
    public final ReadonlyStateFlow communalWidgetCategories;
    public final ReadonlyStateFlow isCommunalEnabled;
    public final CommunalSettingsRepository repository;
    public final UserTracker userTracker;

    public CommunalSettingsInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Executor executor, CommunalSettingsRepository communalSettingsRepository, SelectedUserInteractor selectedUserInteractor, UserTracker userTracker, TableLogBuffer tableLogBuffer) {
        this.bgExecutor = executor;
        this.repository = communalSettingsRepository;
        this.userTracker = userTracker;
        CommunalSettingsInteractor$special$$inlined$flatMapLatest$1 communalSettingsInteractor$special$$inlined$flatMapLatest$1 = new CommunalSettingsInteractor$special$$inlined$flatMapLatest$1(null, this);
        Flow flow = selectedUserInteractor.selectedUserInfo;
        final Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.transformLatest(flow, communalSettingsInteractor$special$$inlined$flatMapLatest$1), tableLogBuffer, "disabledReason", CommunalEnabledState.m939boximpl(EnumSet.noneOf(DisabledReason.class)));
        Flow flow2 = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.communal.data.model.CommunalEnabledState r5 = (com.android.systemui.communal.data.model.CommunalEnabledState) r5
                        java.util.EnumSet r5 = r5.disabledReasons
                        boolean r5 = r5.isEmpty()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isCommunalEnabled = FlowKt.stateIn(flow2, coroutineScope, startedEagerly, bool);
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(flow, new CommunalSettingsInteractor$special$$inlined$flatMapLatest$2(null, this));
        Flow flow3 = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2$2$1
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
                        com.android.systemui.communal.data.model.CommunalWidgetCategories r5 = (com.android.systemui.communal.data.model.CommunalWidgetCategories) r5
                        int r5 = r5.categories
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        CommunalWidgetCategories.Companion.getClass();
        Flags.FEATURE_FLAGS.getClass();
        this.communalWidgetCategories = FlowKt.stateIn(flow3, coroutineScope, startedEagerly, 2);
        this.communalBackground = FlowKt.flowOn(FlowKt.transformLatest(flow, new CommunalSettingsInteractor$special$$inlined$flatMapLatest$3(null, this)), coroutineDispatcher);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1 communalSettingsInteractor$workProfileUserInfoCallbackFlow$1 = new CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1(this, null);
        conflatedCallbackFlow.getClass();
        this.allowedByDevicePolicyForWorkProfile = FlowKt.stateIn(FlowKt.transformLatest(FlowConflatedKt.conflatedCallbackFlow(communalSettingsInteractor$workProfileUserInfoCallbackFlow$1), new CommunalSettingsInteractor$special$$inlined$flatMapLatest$4(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }
}
