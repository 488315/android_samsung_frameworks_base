package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda10;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda7;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.FilterKt;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.IncrementalInit;
import com.android.systemui.kairos.IncrementalKt;
import com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda1;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda7;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda5;
import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.IncrementalImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.MuxKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.MuxLifecycle;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.MuxPromptActivator;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.kairos.internal.StateScopeImpl$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.internal.store.ConcurrentHashMapK;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class MobileIconsInteractorKairosImpl implements MobileIconsInteractorKairos, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activeDataConnectionHasDataEnabled;
    public final StateInit activeDataIconInteractor;
    public final StateInit alwaysShowDataRatIcon;
    public final StateInit alwaysUseCdmaLevel;
    public final CarrierConfigTracker carrierConfigTracker;
    public final Context context;
    public final FeatureFlagsClassic featureFlagsClassic;
    public final State filteredSubscriptions;
    public final Incremental icons;
    public final StateInit isDefaultConnectionFailed;
    public final State isForceHidden;
    public final StateInit isSingleCarrier;
    public final StateInit isStackable;
    public final MobileConnectionsRepositoryKairos mobileConnectionsRepo;
    public final StateInit mobileIsDefault;
    public final StateInit subscriptionsBasedFilteredSubs;
    public final TableLogBuffer tableLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Module {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    static {
        new Companion(null);
    }

    public MobileIconsInteractorKairosImpl(MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos, CarrierConfigTracker carrierConfigTracker, TableLogBuffer tableLogBuffer, final ConnectivityRepository connectivityRepository, final UserSetupRepository userSetupRepository, Context context, FeatureFlagsClassic featureFlagsClassic) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.mobileConnectionsRepo = mobileConnectionsRepositoryKairos;
        this.carrierConfigTracker = carrierConfigTracker;
        this.tableLogger = tableLogBuffer;
        this.context = context;
        this.featureFlagsClassic = featureFlagsClassic;
        final int i = 0;
        final StateInit stateInitCombine = CombineKt.combine(mobileConnectionsRepositoryKairos.getMobileIsDefault(), mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection(), new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i) {
                    case 0:
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
                        int i2 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        return Boolean.valueOf(zBooleanValue || zBooleanValue2);
                    default:
                        Integer num = (Integer) obj2;
                        Map map = (Map) obj3;
                        int i3 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        if (num != null) {
                            return (MobileIconInteractorKairos) map.get(num);
                        }
                        return null;
                }
            }
        });
        final int i2 = 2;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, this.tableLogger, "Intr", "isSingleCarrier");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, this.tableLogger, "Intr", "isDefaultConnectionFailed");
                        break;
                    default:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine, this.tableLogger, "Intr", "mobileIsDefault");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.mobileIsDefault = stateInitCombine;
        this.activeDataConnectionHasDataEnabled = StateKt.flatMap(mobileConnectionsRepositoryKairos.getActiveMobileDataRepository(), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(6));
        this.subscriptionsBasedFilteredSubs = StateKt.map(mobileConnectionsRepositoryKairos.getSubscriptions(), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(this, 0));
        this.filteredSubscriptions = kairosBuilderImpl.buildState(new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda11(this, connectivityRepository, 0));
        final int i3 = 0;
        Incremental incrementalBuildIncremental = kairosBuilderImpl.buildIncremental(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda12
            public final /* synthetic */ MobileIconsInteractorKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl = this.f$0;
                        StateInit map = StateKt.map(mobileIconsInteractorKairosImpl.filteredSubscriptions, new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(4));
                        Incremental mobileConnectionsBySubId = mobileIconsInteractorKairosImpl.mobileConnectionsRepo.getMobileConnectionsBySubId();
                        MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10 mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10 = new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(map, 2);
                        StateScopeImpl stateScopeImpl = ((BuildScopeImpl) buildScope).stateScope;
                        stateScopeImpl.getClass();
                        IncrementalInit incrementalInitMapValues = IncrementalKt.mapValues(mobileConnectionsBySubId, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10, 2), 4));
                        stateScopeImpl.getClass();
                        final IncrementalInit incrementalInitMapValues2 = IncrementalKt.mapValues(incrementalInitMapValues, new StateScope$DefaultImpls$$ExternalSyntheticLambda5(1));
                        final MergeKt$$ExternalSyntheticLambda1 mergeKt$$ExternalSyntheticLambda1 = new MergeKt$$ExternalSyntheticLambda1();
                        final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1
                            @Override // com.android.systemui.kairos.internal.EventsImpl
                            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                                ActivationResult activationResultActivate = ((IncrementalImpl) incrementalInitMapValues2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).patches.activate(evalScope, schedulable);
                                if (activationResultActivate == null) {
                                    return null;
                                }
                                NodeConnection nodeConnection = activationResultActivate.connection;
                                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, mergeKt$$ExternalSyntheticLambda1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                            }
                        };
                        final int i4 = 0;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                EvalScope evalScope = (EvalScope) obj2;
                                switch (i4) {
                                    case 0:
                                        Map map2 = (Map) ((IncrementalImpl) ((IncrementalInit) incrementalInitMapValues2).init.connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst();
                                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map2.size()));
                                        for (Map.Entry entry : map2.entrySet()) {
                                            linkedHashMap.put(entry.getKey(), (EventsImpl) EventsKt.getInit((Events) entry.getValue()).connect(evalScope));
                                        }
                                        return linkedHashMap.entrySet();
                                    default:
                                        return (MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1) incrementalInitMapValues2;
                                }
                            }
                        };
                        final int i5 = 1;
                        final MuxLifecycle muxLifecycle = new MuxLifecycle(new MuxLifecycleState.Inactive(new MuxPromptActivator("mergeEventsIncrementallyPromptly", function1, new ConcurrentHashMapK.Factory(), new Function1() { // from class: com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                EvalScope evalScope = (EvalScope) obj2;
                                switch (i5) {
                                    case 0:
                                        Map map2 = (Map) ((IncrementalImpl) ((IncrementalInit) eventsImpl).init.connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst();
                                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map2.size()));
                                        for (Map.Entry entry : map2.entrySet()) {
                                            linkedHashMap.put(entry.getKey(), (EventsImpl) EventsKt.getInit((Events) entry.getValue()).connect(evalScope));
                                        }
                                        return linkedHashMap.entrySet();
                                    default:
                                        return (MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1) eventsImpl;
                                }
                            }
                        })));
                        final MuxKt$$ExternalSyntheticLambda0 muxKt$$ExternalSyntheticLambda0 = new MuxKt$$ExternalSyntheticLambda0();
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(stateScopeImpl.foldStateMapIncrementally(new EventsInit(new Init("mergeEventsIncrementallyPromptly", new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.MuxKt$awaitValues$$inlined$mapImpl$1
                            @Override // com.android.systemui.kairos.internal.EventsImpl
                            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                                ActivationResult activationResultActivate = muxLifecycle.activate(evalScope, schedulable);
                                if (activationResultActivate == null) {
                                    return null;
                                }
                                NodeConnection nodeConnection = activationResultActivate.connection;
                                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxKt$$ExternalSyntheticLambda0), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                            }
                        }))), new DeferredValue(stateScopeImpl.evalScope.deferAsync(new StateScopeImpl$$ExternalSyntheticLambda3(new StateScope$DefaultImpls$$ExternalSyntheticLambda3(incrementalInitMapValues, 1), stateScopeImpl, 1)))), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(mobileIconsInteractorKairosImpl, 1)));
                    default:
                        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl2 = this.f$0;
                        MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos2 = mobileIconsInteractorKairosImpl2.mobileConnectionsRepo;
                        Events activeSubChangedInGroupEvent = mobileConnectionsRepositoryKairos2.getActiveSubChangedInGroupEvent();
                        final State defaultConnectionIsValidated = mobileConnectionsRepositoryKairos2.getDefaultConnectionIsValidated();
                        EventsInit eventsInitFilter = FilterKt.filter(activeSubChangedInGroupEvent, new Function2() { // from class: com.android.systemui.kairos.FilterKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Boolean bool = (Boolean) ((TransactionScope) obj2).sample(defaultConnectionIsValidated);
                                bool.booleanValue();
                                return bool;
                            }
                        });
                        MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1 mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1 = new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(1);
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        StateInit stateInitFlatMap = StateKt.flatMap(buildScopeImpl.stateScope.holdState((Events) buildScopeImpl.applyLatestSpec(EventsKt.mapCheap(eventsInitFilter, new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(2, mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1)), new BuildScope$DefaultImpls$$ExternalSyntheticLambda10()).getFirst(), StateKt.stateOf(Boolean.FALSE)), new StateKt$$ExternalSyntheticLambda7());
                        DiffableKt.logBooleanDiffsForTable(buildScopeImpl, stateInitFlatMap, mobileIconsInteractorKairosImpl2.tableLogger, "Intr", "forcingValidation");
                        return stateInitFlatMap;
                }
            }
        });
        this.icons = incrementalBuildIncremental;
        this.isStackable = StateKt.stateOf(Boolean.FALSE);
        final int i4 = 1;
        this.activeDataIconInteractor = CombineKt.combine(mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId(), incrementalBuildIncremental, new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i4) {
                    case 0:
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
                        int i22 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        return Boolean.valueOf(zBooleanValue || zBooleanValue2);
                    default:
                        Integer num = (Integer) obj2;
                        Map map = (Map) obj3;
                        int i32 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        if (num != null) {
                            return (MobileIconInteractorKairos) map.get(num);
                        }
                        return null;
                }
            }
        });
        final int i5 = 1;
        State stateBuildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda12
            public final /* synthetic */ MobileIconsInteractorKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i5) {
                    case 0:
                        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl = this.f$0;
                        StateInit map = StateKt.map(mobileIconsInteractorKairosImpl.filteredSubscriptions, new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(4));
                        Incremental mobileConnectionsBySubId = mobileIconsInteractorKairosImpl.mobileConnectionsRepo.getMobileConnectionsBySubId();
                        MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10 mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10 = new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(map, 2);
                        StateScopeImpl stateScopeImpl = ((BuildScopeImpl) buildScope).stateScope;
                        stateScopeImpl.getClass();
                        IncrementalInit incrementalInitMapValues = IncrementalKt.mapValues(mobileConnectionsBySubId, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10, 2), 4));
                        stateScopeImpl.getClass();
                        final Object incrementalInitMapValues2 = IncrementalKt.mapValues(incrementalInitMapValues, new StateScope$DefaultImpls$$ExternalSyntheticLambda5(1));
                        final Function3 mergeKt$$ExternalSyntheticLambda1 = new MergeKt$$ExternalSyntheticLambda1();
                        final Object eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1
                            @Override // com.android.systemui.kairos.internal.EventsImpl
                            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                                ActivationResult activationResultActivate = ((IncrementalImpl) incrementalInitMapValues2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).patches.activate(evalScope, schedulable);
                                if (activationResultActivate == null) {
                                    return null;
                                }
                                NodeConnection nodeConnection = activationResultActivate.connection;
                                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, mergeKt$$ExternalSyntheticLambda1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                            }
                        };
                        final int i42 = 0;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                EvalScope evalScope = (EvalScope) obj2;
                                switch (i42) {
                                    case 0:
                                        Map map2 = (Map) ((IncrementalImpl) ((IncrementalInit) incrementalInitMapValues2).init.connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst();
                                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map2.size()));
                                        for (Map.Entry entry : map2.entrySet()) {
                                            linkedHashMap.put(entry.getKey(), (EventsImpl) EventsKt.getInit((Events) entry.getValue()).connect(evalScope));
                                        }
                                        return linkedHashMap.entrySet();
                                    default:
                                        return (MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1) incrementalInitMapValues2;
                                }
                            }
                        };
                        final int i52 = 1;
                        final EventsImpl muxLifecycle = new MuxLifecycle(new MuxLifecycleState.Inactive(new MuxPromptActivator("mergeEventsIncrementallyPromptly", function1, new ConcurrentHashMapK.Factory(), new Function1() { // from class: com.android.systemui.kairos.MergeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                EvalScope evalScope = (EvalScope) obj2;
                                switch (i52) {
                                    case 0:
                                        Map map2 = (Map) ((IncrementalImpl) ((IncrementalInit) eventsImpl).init.connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst();
                                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map2.size()));
                                        for (Map.Entry entry : map2.entrySet()) {
                                            linkedHashMap.put(entry.getKey(), (EventsImpl) EventsKt.getInit((Events) entry.getValue()).connect(evalScope));
                                        }
                                        return linkedHashMap.entrySet();
                                    default:
                                        return (MergeKt$mergeEventsIncrementallyPromptly$$inlined$mapImpl$1) eventsImpl;
                                }
                            }
                        })));
                        final Function3 muxKt$$ExternalSyntheticLambda0 = new MuxKt$$ExternalSyntheticLambda0();
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(stateScopeImpl.foldStateMapIncrementally(new EventsInit(new Init("mergeEventsIncrementallyPromptly", new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.MuxKt$awaitValues$$inlined$mapImpl$1
                            @Override // com.android.systemui.kairos.internal.EventsImpl
                            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                                ActivationResult activationResultActivate = muxLifecycle.activate(evalScope, schedulable);
                                if (activationResultActivate == null) {
                                    return null;
                                }
                                NodeConnection nodeConnection = activationResultActivate.connection;
                                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxKt$$ExternalSyntheticLambda0), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                            }
                        }))), new DeferredValue(stateScopeImpl.evalScope.deferAsync(new StateScopeImpl$$ExternalSyntheticLambda3(new StateScope$DefaultImpls$$ExternalSyntheticLambda3(incrementalInitMapValues, 1), stateScopeImpl, 1)))), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda10(mobileIconsInteractorKairosImpl, 1)));
                    default:
                        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl2 = this.f$0;
                        MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos2 = mobileIconsInteractorKairosImpl2.mobileConnectionsRepo;
                        Events activeSubChangedInGroupEvent = mobileConnectionsRepositoryKairos2.getActiveSubChangedInGroupEvent();
                        final State defaultConnectionIsValidated = mobileConnectionsRepositoryKairos2.getDefaultConnectionIsValidated();
                        EventsInit eventsInitFilter = FilterKt.filter(activeSubChangedInGroupEvent, new Function2() { // from class: com.android.systemui.kairos.FilterKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Boolean bool = (Boolean) ((TransactionScope) obj2).sample(defaultConnectionIsValidated);
                                bool.booleanValue();
                                return bool;
                            }
                        });
                        MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1 mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1 = new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(1);
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        StateInit stateInitFlatMap = StateKt.flatMap(buildScopeImpl.stateScope.holdState((Events) buildScopeImpl.applyLatestSpec(EventsKt.mapCheap(eventsInitFilter, new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(2, mobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1)), new BuildScope$DefaultImpls$$ExternalSyntheticLambda10()).getFirst(), StateKt.stateOf(Boolean.FALSE)), new StateKt$$ExternalSyntheticLambda7());
                        DiffableKt.logBooleanDiffsForTable(buildScopeImpl, stateInitFlatMap, mobileIconsInteractorKairosImpl2.tableLogger, "Intr", "forcingValidation");
                        return stateInitFlatMap;
                }
            }
        });
        this.alwaysShowDataRatIcon = StateKt.map(mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig(), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(2));
        this.alwaysUseCdmaLevel = StateKt.map(mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig(), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(0));
        final StateInit map = StateKt.map(mobileConnectionsRepositoryKairos.getSubscriptions(), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(5));
        final int i6 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i6) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Intr", "isSingleCarrier");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Intr", "isDefaultConnectionFailed");
                        break;
                    default:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Intr", "mobileIsDefault");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isSingleCarrier = map;
        final StateInit stateInitCombine2 = CombineKt.combine(stateInitCombine, mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated(), stateBuildState, new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda4());
        final int i7 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i7) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine2, this.tableLogger, "Intr", "isSingleCarrier");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine2, this.tableLogger, "Intr", "isDefaultConnectionFailed");
                        break;
                    default:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitCombine2, this.tableLogger, "Intr", "mobileIsDefault");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isDefaultConnectionFailed = stateInitCombine2;
        final int i8 = 0;
        this.$$delegate_0.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = userSetupRepository;
                BuildScope buildScope = (BuildScope) obj;
                switch (i8) {
                    case 0:
                        int i9 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) ((UserSetupRepository) obj2)).isUserSetUp;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        int i10 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow2 = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).forceHiddenSlots;
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return StateKt.map(BuildScope.DefaultImpls.toState(buildScopeImpl2, readonlyStateFlow2), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(3));
                }
            }
        });
        final int i9 = 1;
        this.isForceHidden = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = connectivityRepository;
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        int i92 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) ((UserSetupRepository) obj2)).isUserSetUp;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        int i10 = MobileIconsInteractorKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow2 = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).forceHiddenSlots;
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return StateKt.map(BuildScope.DefaultImpls.toState(buildScopeImpl2, readonlyStateFlow2), new MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda1(3));
                }
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
