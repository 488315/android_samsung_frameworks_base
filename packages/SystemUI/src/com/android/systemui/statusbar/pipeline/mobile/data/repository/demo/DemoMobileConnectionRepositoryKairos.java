package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.MergeKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.kairos.util.Either;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda9;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes3.dex */
public final class DemoMobileConnectionRepositoryKairos implements MobileConnectionRepositoryKairos, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit allowNetworkSliceIndicator;
    public final StateInit carrierId;
    public final StateInit carrierName;
    public final StateInit carrierNetworkChangeActive;
    public final StateInit cdmaLevel;
    public final StateInit cdmaRoaming;
    public final StateInit dataActivityDirection;
    public final State dataConnectionState;
    public final StateInit dataEnabled;
    public final StateInit hasPrioritizedNetworkCapabilities;
    public final State inflateSignalStrength;
    public final FakeNetworkEventModel.Mobile initialState;
    public final StateInit isAllowedDuringAirplaneMode;
    public final StateInit isEmergencyOnly;
    public final StateInit isGsm;
    public final StateInit isInEcmMode;
    public final StateInit isInService;
    public final State isNonTerrestrial;
    public final StateInit isRoaming;
    public final State lastEvent;
    public final State lastMobileEvent;
    public final State mobileMappingsReverseLookup;
    public final StateInit networkName;
    public final StateInit numberOfLevels;
    public final StateInit operatorAlphaShort;
    public final StateInit primaryLevel;
    public final State resolvedNetworkType;
    public final StateInit satelliteLevel;
    public final int subId;
    public final TableLogBuffer tableLogBuffer;

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

    public DemoMobileConnectionRepositoryKairos(int i, TableLogBuffer tableLogBuffer, final Events events, final Events events2, final Events events3, State state) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        this.mobileMappingsReverseLookup = state;
        this.initialState = new FakeNetworkEventModel.Mobile(null, null, Integer.valueOf(i), null, false, null, false, false, "Demo Carrier", false, false, 1552, null);
        final int i2 = 0;
        this.lastMobileEvent = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                Events events4 = events;
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(events4, demoMobileConnectionRepositoryKairos.initialState);
                    case 1:
                        int i3 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(14)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "inflate");
                        return stateInitHoldState;
                    default:
                        int i4 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState2 = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(17)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState2, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "isNtn");
                        return stateInitHoldState2;
                }
            }
        });
        State stateBuildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i3 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                EventsInit eventsInitMapCheap = EventsKt.mapCheap(events, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(15));
                EventsInit eventsInitMapCheap2 = EventsKt.mapCheap(events3, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(16));
                DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(MergeKt.mergeLeft(eventsInitMapCheap, eventsInitMapCheap2, EventsKt.mapCheap(events2, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34(demoMobileConnectionRepositoryKairos, 0))), Either.First.m2586boximpl(demoMobileConnectionRepositoryKairos.initialState));
            }
        });
        this.lastEvent = stateBuildState;
        final StateInit map = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(9));
        final int i3 = 3;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierId = map;
        final int i4 = 1;
        State stateBuildState2 = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                Events events4 = events;
                BuildScope buildScope = (BuildScope) obj;
                switch (i4) {
                    case 0:
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(events4, demoMobileConnectionRepositoryKairos.initialState);
                    case 1:
                        int i32 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(14)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "inflate");
                        return stateInitHoldState;
                    default:
                        int i42 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState2 = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(17)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState2, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "isNtn");
                        return stateInitHoldState2;
                }
            }
        });
        this.inflateSignalStrength = stateBuildState2;
        Boolean bool = Boolean.TRUE;
        this.allowNetworkSliceIndicator = StateKt.stateOf(bool);
        Boolean bool2 = Boolean.FALSE;
        this.isEmergencyOnly = StateKt.stateOf(bool2);
        final StateInit map2 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(10));
        final int i5 = 4;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i5) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map2, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map2;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map2, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isRoaming = map2;
        final StateInit map3 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(11));
        final int i6 = 5;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i6) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map3, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map3;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map3, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.operatorAlphaShort = map3;
        final StateInit map4 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(12));
        final int i7 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i7) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map4, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map4;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map4, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isInService = map4;
        final int i8 = 2;
        this.isNonTerrestrial = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                Events events4 = events;
                BuildScope buildScope = (BuildScope) obj;
                switch (i8) {
                    case 0:
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(events4, demoMobileConnectionRepositoryKairos.initialState);
                    case 1:
                        int i32 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(14)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "inflate");
                        return stateInitHoldState;
                    default:
                        int i42 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        StateInit stateInitHoldState2 = ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(events4, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(17)), Boolean.FALSE);
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState2, demoMobileConnectionRepositoryKairos.tableLogBuffer, "", "isNtn");
                        return stateInitHoldState2;
                }
            }
        });
        this.isGsm = StateKt.stateOf(bool2);
        final StateInit map5 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(0));
        final int i9 = 6;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map5, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map5;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map5, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.cdmaLevel = map5;
        final StateInit map6 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(18));
        final int i10 = 7;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i10) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map6, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map6;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map6, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.primaryLevel = map6;
        final StateInit stateInitStateOf = StateKt.stateOf(0);
        final int i11 = 8;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i11) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = stateInitStateOf;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, stateInitStateOf, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.satelliteLevel = stateInitStateOf;
        final int i12 = 0;
        final State stateBuildState3 = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = events;
                Object obj3 = events3;
                BuildScope buildScope = (BuildScope) obj;
                switch (i12) {
                    case 0:
                        int i13 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(MergeKt.mergeLeft((Events) obj2, (Events) obj3), new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(13)), DataConnectionState.Disconnected);
                    default:
                        DiffableKt.logDiffsForTable(buildScope, (State) obj2, ((DemoMobileConnectionRepositoryKairos) obj3).tableLogBuffer, "");
                        return Unit.INSTANCE;
                }
            }
        });
        final int i13 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = stateBuildState3;
                Object obj3 = this;
                BuildScope buildScope = (BuildScope) obj;
                switch (i13) {
                    case 0:
                        int i132 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(EventsKt.map(MergeKt.mergeLeft((Events) obj2, (Events) obj3), new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(13)), DataConnectionState.Disconnected);
                    default:
                        DiffableKt.logDiffsForTable(buildScope, (State) obj2, ((DemoMobileConnectionRepositoryKairos) obj3).tableLogBuffer, "");
                        return Unit.INSTANCE;
                }
            }
        });
        this.dataConnectionState = stateBuildState3;
        final StateInit map7 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(1));
        final int i14 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i14) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map7, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map7;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map7, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.dataActivityDirection = map7;
        final StateInit map8 = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(2));
        final int i15 = 2;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i15) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "isInService");
                        break;
                    case 1:
                        DiffableKt.logDiffsForTable(buildScope, map8, this.tableLogBuffer, "");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "carrierNetworkChangeActive");
                        break;
                    case 3:
                        DiffableKt.logIntDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "carrierId");
                        break;
                    case 4:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "roaming");
                        break;
                    case 5:
                        DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this;
                        StateInit stateInit = map8;
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        ref$BooleanRef.element = true;
                        ((BuildScopeImpl) buildScope).observe(stateInit, new DiffableKt$$ExternalSyntheticLambda9(demoMobileConnectionRepositoryKairos.tableLogBuffer, ref$BooleanRef));
                        break;
                    case 6:
                        DiffableKt.logIntDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "cdmaLevel");
                        break;
                    case 7:
                        DiffableKt.logIntDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "primaryLevel");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map8, this.tableLogBuffer, "", "satelliteLevel");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.carrierNetworkChangeActive = map8;
        this.resolvedNetworkType = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                DemoMobileConnectionRepositoryKairos demoMobileConnectionRepositoryKairos = this.f$0;
                DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34 demoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34 = new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34(demoMobileConnectionRepositoryKairos, 1);
                StateScopeImpl stateScopeImpl = ((BuildScopeImpl) buildScope).stateScope;
                stateScopeImpl.getClass();
                StateInit stateInitSampleTransactionals = stateScopeImpl.sampleTransactionals(StateKt.map(demoMobileConnectionRepositoryKairos.lastEvent, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(demoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda34, 0)));
                DiffableKt.logDiffsForTable(buildScope, stateInitSampleTransactionals, demoMobileConnectionRepositoryKairos.tableLogBuffer, "");
                return stateInitSampleTransactionals;
            }
        });
        this.numberOfLevels = StateKt.map(stateBuildState2, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(3));
        this.dataEnabled = StateKt.stateOf(bool);
        this.cdmaRoaming = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(4));
        this.networkName = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(5));
        this.carrierName = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(6));
        this.isAllowedDuringAirplaneMode = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(7));
        this.hasPrioritizedNetworkCapabilities = StateKt.map(stateBuildState, new DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(8));
        this.isInEcmMode = StateKt.stateOf(bool2);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getAllowNetworkSliceIndicator() {
        return this.allowNetworkSliceIndicator;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierName() {
        return this.carrierName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaLevel() {
        return this.cdmaLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaRoaming() {
        return this.cdmaRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataActivityDirection() {
        return this.dataActivityDirection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataConnectionState() {
        return this.dataConnectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataEnabled() {
        return this.dataEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getHasPrioritizedNetworkCapabilities() {
        return this.hasPrioritizedNetworkCapabilities;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getInflateSignalStrength() {
        return this.inflateSignalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNumberOfLevels() {
        return this.numberOfLevels;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getOperatorAlphaShort() {
        return this.operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getPrimaryLevel() {
        return this.primaryLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getResolvedNetworkType() {
        return this.resolvedNetworkType;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getSatelliteLevel() {
        return this.satelliteLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final int getSubId() {
        return this.subId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isGsm() {
        return this.isGsm;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInEcmMode() {
        return this.isInEcmMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isNonTerrestrial() {
        return this.isNonTerrestrial;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
