package com.android.systemui.communal.domain.interactor;

import android.R;
import com.android.systemui.communal.data.repository.CommunalSettingsRepository;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
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
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSettingsInteractor {
    public final Flow allowedForCurrentUserByDevicePolicy;
    public final ReadonlyStateFlow autoOpenEnabled;
    public final Executor bgExecutor;
    public final Flow communalBackground;
    public final ReadonlyStateFlow isCommunalEnabled;
    public final ReadonlyStateFlow manualOpenEnabled;
    public final CommunalSettingsRepository repository;
    public final Flow settingEnabledForCurrentUser;
    public final UserTracker userTracker;
    public final Flow whenToDream;
    public final ChannelFlowTransformLatest whenToStartHub;
    public final ReadonlyStateFlow workProfileUserDisallowedByDevicePolicy;

    public CommunalSettingsInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Executor executor, CommunalSettingsRepository communalSettingsRepository, SelectedUserInteractor selectedUserInteractor, UserTracker userTracker) {
        this.bgExecutor = executor;
        this.repository = communalSettingsRepository;
        this.userTracker = userTracker;
        CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = (CommunalSettingsRepositoryImpl) communalSettingsRepository;
        final StateFlowImpl stateFlowImpl = communalSettingsRepositoryImpl._suppressionReasons;
        final int i = 4;
        Flow flow = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $feature$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$feature$inlined = i;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        boolean r6 = r5 instanceof java.util.Collection
                        if (r6 == 0) goto L45
                        r6 = r5
                        java.util.Collection r6 = (java.util.Collection) r6
                        boolean r6 = r6.isEmpty()
                        if (r6 == 0) goto L45
                    L43:
                        r5 = r3
                        goto L5f
                    L45:
                        java.util.Iterator r5 = r5.iterator()
                    L49:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L43
                        java.lang.Object r6 = r5.next()
                        com.android.systemui.communal.data.model.SuppressionReason r6 = (com.android.systemui.communal.data.model.SuppressionReason) r6
                        int r6 = r6.getSuppressedFeatures()
                        int r2 = r4.$feature$inlined
                        r6 = r6 & r2
                        if (r6 == 0) goto L49
                        r5 = 0
                    L5f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isCommunalEnabled = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        final StateFlowImpl stateFlowImpl2 = communalSettingsRepositoryImpl._suppressionReasons;
        final int i2 = 2;
        this.manualOpenEnabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $feature$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$feature$inlined = i;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        boolean r6 = r5 instanceof java.util.Collection
                        if (r6 == 0) goto L45
                        r6 = r5
                        java.util.Collection r6 = (java.util.Collection) r6
                        boolean r6 = r6.isEmpty()
                        if (r6 == 0) goto L45
                    L43:
                        r5 = r3
                        goto L5f
                    L45:
                        java.util.Iterator r5 = r5.iterator()
                    L49:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L43
                        java.lang.Object r6 = r5.next()
                        com.android.systemui.communal.data.model.SuppressionReason r6 = (com.android.systemui.communal.data.model.SuppressionReason) r6
                        int r6 = r6.getSuppressedFeatures()
                        int r2 = r4.$feature$inlined
                        r6 = r6 & r2
                        if (r6 == 0) goto L49
                        r5 = 0
                    L5f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        final StateFlowImpl stateFlowImpl3 = communalSettingsRepositoryImpl._suppressionReasons;
        final int i3 = 1;
        this.autoOpenEnabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $feature$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$feature$inlined = i;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        boolean r6 = r5 instanceof java.util.Collection
                        if (r6 == 0) goto L45
                        r6 = r5
                        java.util.Collection r6 = (java.util.Collection) r6
                        boolean r6 = r6.isEmpty()
                        if (r6 == 0) goto L45
                    L43:
                        r5 = r3
                        goto L5f
                    L45:
                        java.util.Iterator r5 = r5.iterator()
                    L49:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L43
                        java.lang.Object r6 = r5.next()
                        com.android.systemui.communal.data.model.SuppressionReason r6 = (com.android.systemui.communal.data.model.SuppressionReason) r6
                        int r6 = r6.getSuppressedFeatures()
                        int r2 = r4.$feature$inlined
                        r6 = r6 & r2
                        if (r6 == 0) goto L49
                        r5 = 0
                    L5f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i3), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        this.whenToDream = LatestConflatedKt.flatMapLatestConflated(selectedUserInteractor.selectedUserInfo, new CommunalSettingsInteractor$whenToDream$1(this, null));
        CommunalSettingsInteractor$special$$inlined$flatMapLatest$1 communalSettingsInteractor$special$$inlined$flatMapLatest$1 = new CommunalSettingsInteractor$special$$inlined$flatMapLatest$1(null, this);
        UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = selectedUserInteractor.selectedUserInfo;
        this.whenToStartHub = FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, communalSettingsInteractor$special$$inlined$flatMapLatest$1);
        this.allowedForCurrentUserByDevicePolicy = LatestConflatedKt.flatMapLatestConflated(userRepositoryImpl$special$$inlined$map$2, new CommunalSettingsInteractor$allowedForCurrentUserByDevicePolicy$1(this, null));
        this.settingEnabledForCurrentUser = LatestConflatedKt.flatMapLatestConflated(userRepositoryImpl$special$$inlined$map$2, new CommunalSettingsInteractor$settingEnabledForCurrentUser$1(this, null));
        this.communalBackground = FlowKt.flowOn(FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, new CommunalSettingsInteractor$special$$inlined$flatMapLatest$2(null, this)), coroutineDispatcher);
        this.workProfileUserDisallowedByDevicePolicy = FlowKt.stateIn(FlowKt.transformLatest(FlowConflatedKt.conflatedCallbackFlow(new CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1(this, null)), new CommunalSettingsInteractor$special$$inlined$flatMapLatest$3(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }

    public final boolean isCommunalFlagEnabled() {
        CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = (CommunalSettingsRepositoryImpl) this.repository;
        communalSettingsRepositoryImpl.resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
        return ((FeatureFlagsClassicRelease) communalSettingsRepositoryImpl.featureFlagsClassic).isEnabled(Flags.COMMUNAL_SERVICE_ENABLED);
    }

    public final void isV2FlagEnabled() {
        ((CommunalSettingsRepositoryImpl) this.repository).resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
    }
}
