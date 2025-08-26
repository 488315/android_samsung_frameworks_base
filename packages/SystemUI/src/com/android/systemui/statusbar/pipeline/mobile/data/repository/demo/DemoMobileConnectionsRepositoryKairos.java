package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.content.Context;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.EmptyEvents;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.FilterKt;
import com.android.systemui.kairos.FilterKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.GroupByKt;
import com.android.systemui.kairos.GroupedEvents;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.IncrementalKt;
import com.android.systemui.kairos.MergeKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel;
import java.util.Collections;
import kotlin.Pair;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* loaded from: classes3.dex */
public final class DemoMobileConnectionsRepositoryKairos implements MobileConnectionsRepositoryKairos, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activeMobileDataRepository;
    public final StateInit activeMobileDataSubscriptionId;
    public final State activeMobileSubscriptions;
    public final EmptyEvents activeSubChangedInGroupEvent;
    public final EventsInit carrierMergedEvents;
    public final StateInit defaultConnectionIsValidated;
    public final StateInit defaultDataSubId;
    public final StateInit defaultDataSubRatConfig;
    public final StateInit defaultMobileIconGroup;
    public final StateInit defaultMobileIconMapping;
    public final StateInit hasCarrierMergedConnection;
    public final StateInit isAnySimSecure;
    public final StateInit isDeviceEmergencyCallCapable;
    public final StateInit isInEcmMode;
    public final State lastSeenSubId;
    public final TableLogBufferFactory logFactory;
    public final GroupedEvents mobileEventsBySubId;
    public final EventsInit mobileEventsWithSubId;
    public final StateInit mobileIsDefault;
    public final StateInit mobileMappingsReverseLookup;
    public final Incremental reposBySubId;
    public final StateInit subscriptionIds;
    public final StateInit subscriptions;
    public final StateInit subscriptionsById;
    public final DemoModeWifiDataSource wifiDataSource;
    public final Events wifiEvents;
    public final GroupedEvents wifiEventsBySubId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        DemoMobileConnectionsRepositoryKairos create();
    }

    static {
        new Companion(null);
    }

    public DemoMobileConnectionsRepositoryKairos(final DemoModeMobileConnectionDataSourceKairos demoModeMobileConnectionDataSourceKairos, DemoModeWifiDataSource demoModeWifiDataSource, Context context, TableLogBufferFactory tableLogBufferFactory) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.wifiDataSource = demoModeWifiDataSource;
        this.logFactory = tableLogBufferFactory;
        final int i = 0;
        Events eventsBuildEvents = kairosBuilderImpl.buildEvents(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i) {
                    case 0:
                        ReadonlySharedFlow readonlySharedFlow = this.wifiDataSource.wifiEvents;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, readonlySharedFlow);
                    case 1:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos = this;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(IncrementalKt.asIncremental(demoMobileConnectionsRepositoryKairos.subscriptionsById), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(2, demoMobileConnectionsRepositoryKairos)));
                    case 2:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos2 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.mobileEventsWithSubId, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(6)), EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(7))), null);
                    default:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos3 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos3.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(3)), EventsKt.map(FilterKt.filter(demoMobileConnectionsRepositoryKairos3.wifiEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(4)), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(5))), null);
                }
            }
        });
        this.wifiEvents = eventsBuildEvents;
        EventsInit eventsInitMapNotNull = EventsKt.mapNotNull(((DemoModeMobileConnectionDataSourceKairosImpl) demoModeMobileConnectionDataSourceKairos).mobileEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(3, this));
        this.mobileEventsWithSubId = eventsInitMapNotNull;
        this.mobileEventsBySubId = GroupByKt.groupByKey(EventsKt.map(eventsInitMapNotNull, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(8)), null);
        EventsInit eventsInitFilterPresent = FilterKt.filterPresent(EventsKt.mapCheap(EventsKt.mapCheap(eventsBuildEvents, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$special$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                if (!(obj2 instanceof FakeWifiEventModel.CarrierMerged)) {
                    obj2 = null;
                }
                return (FakeWifiEventModel.CarrierMerged) obj2;
            }
        }), new FilterKt$$ExternalSyntheticLambda0()));
        this.carrierMergedEvents = eventsInitFilterPresent;
        final DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2 demoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2 = new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(9);
        this.wifiEventsBySubId = GroupByKt.groupByKey(EventsKt.map(eventsInitFilterPresent, new Function2() { // from class: com.android.systemui.kairos.GroupByKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Pair pair = new Pair(demoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2.invoke((TransactionScope) obj, obj2), obj2);
                return Collections.singletonMap(pair.getFirst(), pair.getSecond());
            }
        }), null);
        final int i2 = 2;
        this.lastSeenSubId = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        ReadonlySharedFlow readonlySharedFlow = this.wifiDataSource.wifiEvents;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, readonlySharedFlow);
                    case 1:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos = this;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(IncrementalKt.asIncremental(demoMobileConnectionsRepositoryKairos.subscriptionsById), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(2, demoMobileConnectionsRepositoryKairos)));
                    case 2:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos2 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.mobileEventsWithSubId, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(6)), EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(7))), null);
                    default:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos3 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos3.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(3)), EventsKt.map(FilterKt.filter(demoMobileConnectionsRepositoryKairos3.wifiEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(4)), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(5))), null);
                }
            }
        });
        final int i3 = 3;
        State stateBuildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        ReadonlySharedFlow readonlySharedFlow = this.wifiDataSource.wifiEvents;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, readonlySharedFlow);
                    case 1:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos = this;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(IncrementalKt.asIncremental(demoMobileConnectionsRepositoryKairos.subscriptionsById), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(2, demoMobileConnectionsRepositoryKairos)));
                    case 2:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos2 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.mobileEventsWithSubId, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(6)), EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(7))), null);
                    default:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos3 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos3.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(3)), EventsKt.map(FilterKt.filter(demoMobileConnectionsRepositoryKairos3.wifiEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(4)), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(5))), null);
                }
            }
        });
        State stateBuildState2 = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i4 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                DemoModeMobileConnectionDataSourceKairosImpl demoModeMobileConnectionDataSourceKairosImpl = (DemoModeMobileConnectionDataSourceKairosImpl) demoModeMobileConnectionDataSourceKairos;
                return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.foldState(EventsKt.mapNotNull(demoModeMobileConnectionDataSourceKairosImpl.mobileEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(1, this)), EmptySet.INSTANCE, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda3(2));
            }
        });
        this.activeMobileSubscriptions = stateBuildState2;
        StateInit stateInitCombine = CombineKt.combine(stateBuildState2, stateBuildState, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda3(1));
        this.subscriptionIds = stateInitCombine;
        StateInit map = StateKt.map(stateInitCombine, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(1));
        this.subscriptionsById = map;
        StateInit map2 = StateKt.map(map, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(2));
        this.subscriptions = map2;
        final int i4 = 1;
        Incremental incrementalBuildIncremental = kairosBuilderImpl.buildIncremental(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i4) {
                    case 0:
                        ReadonlySharedFlow readonlySharedFlow = this.wifiDataSource.wifiEvents;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, readonlySharedFlow);
                    case 1:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos = this;
                        return BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(IncrementalKt.asIncremental(demoMobileConnectionsRepositoryKairos.subscriptionsById), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(2, demoMobileConnectionsRepositoryKairos)));
                    case 2:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos2 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.mobileEventsWithSubId, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(6)), EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos2.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(7))), null);
                    default:
                        DemoMobileConnectionsRepositoryKairos demoMobileConnectionsRepositoryKairos3 = this;
                        return ((BuildScopeImpl) buildScope).stateScope.holdState(MergeKt.mergeLeft(EventsKt.mapCheap(demoMobileConnectionsRepositoryKairos3.carrierMergedEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(3)), EventsKt.map(FilterKt.filter(demoMobileConnectionsRepositoryKairos3.wifiEvents, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(4)), new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(5))), null);
                }
            }
        });
        this.reposBySubId = incrementalBuildIncremental;
        StateInit map3 = StateKt.map(map2, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda2(0));
        this.activeMobileDataSubscriptionId = map3;
        this.activeMobileDataRepository = CombineKt.combine(map3, incrementalBuildIncremental, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda3(0));
        this.activeSubChangedInGroupEvent = EventsKt.emptyEvents;
        this.defaultDataSubRatConfig = StateKt.stateOf(MobileMappings.Config.readConfig(context));
        this.defaultMobileIconGroup = StateKt.stateOf(TelephonyIcons.THREE_G);
        Boolean bool = Boolean.FALSE;
        this.isDeviceEmergencyCallCapable = StateKt.stateOf(bool);
        this.isAnySimSecure = StateKt.stateOf(bool);
        StateInit stateInitStateOf = StateKt.stateOf(TelephonyIcons.ICON_NAME_TO_ICON);
        this.defaultMobileIconMapping = stateInitStateOf;
        this.mobileMappingsReverseLookup = StateKt.map(stateInitStateOf, new DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda4(0, this));
        this.defaultDataSubId = StateKt.stateOf(null);
        Boolean bool2 = Boolean.TRUE;
        this.mobileIsDefault = StateKt.stateOf(bool2);
        this.hasCarrierMergedConnection = StateKt.stateOf(bool);
        this.defaultConnectionIsValidated = StateKt.stateOf(bool2);
        this.isInEcmMode = StateKt.stateOf(bool);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getActiveMobileDataRepository() {
        return this.activeMobileDataRepository;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getActiveMobileDataSubscriptionId() {
        return this.activeMobileDataSubscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final Events getActiveSubChangedInGroupEvent() {
        return this.activeSubChangedInGroupEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultConnectionIsValidated() {
        return this.defaultConnectionIsValidated;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultDataSubId() {
        return this.defaultDataSubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultDataSubRatConfig() {
        return this.defaultDataSubRatConfig;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultMobileIconGroup() {
        return this.defaultMobileIconGroup;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getDefaultMobileIconMapping() {
        return this.defaultMobileIconMapping;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final Incremental getMobileConnectionsBySubId() {
        return this.reposBySubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isAnySimSecure() {
        return this.isAnySimSecure;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isDeviceEmergencyCallCapable() {
        return this.isDeviceEmergencyCallCapable;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos
    public final State isInEcmMode() {
        return this.isInEcmMode;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
