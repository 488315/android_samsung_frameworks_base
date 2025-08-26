package com.android.systemui.communal.domain.interactor;

import android.R;
import android.content.res.Resources;
import com.android.systemui.communal.data.model.SuppressionReason;
import com.android.systemui.communal.data.repository.CommunalSettingsRepository;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
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

                /* JADX WARN: Removed duplicated region for block: B:27:0x006d A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
                    Boolean boolValueOf;
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
                        List list = (List) obj;
                        if ((list instanceof Collection) && list.isEmpty()) {
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            }
                        } else {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                if ((((SuppressionReason) it.next()).getSuppressedFeatures() & this.$feature$inlined) != 0) {
                                    z = false;
                                    break;
                                }
                            }
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

                /* JADX WARN: Removed duplicated region for block: B:27:0x006d A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
                    Boolean boolValueOf;
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
                        List list = (List) obj;
                        if ((list instanceof Collection) && list.isEmpty()) {
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            }
                        } else {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                if ((((SuppressionReason) it.next()).getSuppressedFeatures() & this.$feature$inlined) != 0) {
                                    z = false;
                                    break;
                                }
                            }
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImpl2.collect(new AnonymousClass2(flowCollector, i2), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        final StateFlowImpl stateFlowImpl3 = communalSettingsRepositoryImpl._suppressionReasons;
        final int i3 = 1;
        this.autoOpenEnabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$isEnabled$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:27:0x006d A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
                    Boolean boolValueOf;
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
                        List list = (List) obj;
                        if ((list instanceof Collection) && list.isEmpty()) {
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            }
                        } else {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                if ((((SuppressionReason) it.next()).getSuppressedFeatures() & this.$feature$inlined) != 0) {
                                    z = false;
                                    break;
                                }
                            }
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImpl3.collect(new AnonymousClass2(flowCollector, i3), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

    public final boolean isCommunalFlagEnabled() throws Resources.NotFoundException {
        CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = (CommunalSettingsRepositoryImpl) this.repository;
        communalSettingsRepositoryImpl.resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
        return ((FeatureFlagsClassicRelease) communalSettingsRepositoryImpl.featureFlagsClassic).isEnabled(Flags.COMMUNAL_SERVICE_ENABLED);
    }

    public final void isV2FlagEnabled() {
        ((CommunalSettingsRepositoryImpl) this.repository).resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
    }
}
