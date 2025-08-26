package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.Dumpable;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.EventsKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.GroupByKt;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateSelector;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxy;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class MobileConnectionsRepositoryKairosImpl implements MobileConnectionsRepositoryKairos, Dumpable, KairosBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final StateInit activeMobileDataRepository;
    public final StateInit activeMobileDataSubscriptionId;
    public final Events activeSubChangedInGroupEvent;
    public final CoroutineDispatcher bgDispatcher;
    public final EventsInit carrierConfigChangedEvent;
    public final StateSelector carrierMergedSelector;
    public final State carrierMergedSubId;
    public final Context context;
    public final StateInit defaultConnectionIsValidated;
    public final State defaultDataSubId;
    public final State defaultDataSubRatConfig;
    public final State defaultMobileIconGroup;
    public final State defaultMobileIconMapping;
    public DumpCache dumpCache;
    public final StateInit hasCarrierMergedConnection;
    public final State isAnySimSecure;
    public final State isDeviceEmergencyCallCapable;
    public final StateInit isInEcmMode;
    public final StateInit isInEcmModeTopLevel;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MobileInputLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final Incremental mobileConnectionsBySubId;
    public final StateInit mobileIsDefault;
    public final Lazy mobileRepoFactory;
    public final Events mobileSubscriptionsChangeEvent;
    public final Events serviceStateChangedEvent;
    public final SubscriptionManager subscriptionManager;
    public final SubscriptionManagerProxy subscriptionManagerProxy;
    public final State subscriptions;
    public final StateInit subscriptionsById;
    public final TableLogBuffer tableLogger;
    public final TelephonyManager telephonyManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface ConnectionRepoFactory {
    }

    public final class DumpCache {
        public final Map repos;

        public DumpCache(Map<Integer, ? extends MobileConnectionRepositoryKairos> map) {
            this.repos = map;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DumpCache) && Intrinsics.areEqual(this.repos, ((DumpCache) obj).repos);
        }

        public final int hashCode() {
            return this.repos.hashCode();
        }

        public final String toString() {
            return "DumpCache(repos=" + this.repos + ")";
        }
    }

    public final class Module {
        public static final Module INSTANCE = new Module();

        private Module() {
        }
    }

    static {
        new Companion(null);
    }

    public MobileConnectionsRepositoryKairosImpl(final ConnectivityRepository connectivityRepository, SubscriptionManager subscriptionManager, SubscriptionManagerProxy subscriptionManagerProxy, TelephonyManager telephonyManager, MobileInputLogger mobileInputLogger, TableLogBuffer tableLogBuffer, final MobileMappingsProxy mobileMappingsProxy, final BroadcastDispatcher broadcastDispatcher, Context context, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, final AirplaneModeRepository airplaneModeRepository, final WifiRepository wifiRepository, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Lazy lazy) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subscriptionManager = subscriptionManager;
        this.subscriptionManagerProxy = subscriptionManagerProxy;
        this.telephonyManager = telephonyManager;
        this.logger = mobileInputLogger;
        this.tableLogger = tableLogBuffer;
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
        this.mainDispatcher = coroutineDispatcher2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mobileRepoFactory = lazy;
        dumpManager.registerNormalDumpable("MobileConnectionsRepositoryKairos", this);
        final State stateBuildState = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                int i = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                StateFlow wifiNetwork = wifiRepository.getWifiNetwork();
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                buildScopeImpl.getClass();
                StateInit stateInitCombine = CombineKt.combine(BuildScope.DefaultImpls.toState(buildScopeImpl, wifiNetwork), BuildScope.DefaultImpls.toState(buildScopeImpl, ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections), BuildScope.DefaultImpls.toState(buildScopeImpl, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda28());
                DiffableKt.logIntDiffsForTable(buildScope, stateInitCombine, this.tableLogger, "Repo", "carrierMergedSubId");
                return stateInitCombine;
            }
        });
        this.carrierMergedSubId = stateBuildState;
        this.mobileSubscriptionsChangeEvent = kairosBuilderImpl.buildEvents(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 1));
        final int i = 1;
        this.serviceStateChangedEvent = kairosBuilderImpl.buildEvents(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = broadcastDispatcher;
                BuildScope buildScope = (BuildScope) obj;
                switch (i) {
                    case 0:
                        int i2 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 14);
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, flowBroadcastFlow$default);
                    case 1:
                        int i3 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default2 = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(7), 14);
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flowBroadcastFlow$default2);
                    default:
                        int i4 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).defaultConnections;
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, readonlyStateFlow);
                }
            }
        });
        this.isDeviceEmergencyCallCapable = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 6));
        State stateBuildState2 = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 7));
        this.subscriptions = stateBuildState2;
        this.subscriptionsById = StateKt.map(stateBuildState2, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(4));
        Incremental incrementalBuildIncremental = kairosBuilderImpl.buildIncremental(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 8));
        this.mobileConnectionsBySubId = incrementalBuildIncremental;
        State stateBuildState3 = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 3));
        final StateInit map = StateKt.map(stateBuildState3, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(5));
        final int i2 = 3;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i2) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Repo", "mobileIsDefault");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Repo", "hasCarrierMergedConnection");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.tableLogger, "Repo", "defaultConnectionIsValidated");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map, this.tableLogger, "Repo", "activeSubId");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.activeMobileDataSubscriptionId = map;
        this.activeMobileDataRepository = CombineKt.combine(map, incrementalBuildIncremental, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda1());
        this.defaultDataSubId = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda2(broadcastDispatcher, this));
        final int i3 = 0;
        this.carrierConfigChangedEvent = EventsKt.map(kairosBuilderImpl.buildEvents(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = broadcastDispatcher;
                BuildScope buildScope = (BuildScope) obj;
                switch (i3) {
                    case 0:
                        int i22 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 14);
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, flowBroadcastFlow$default);
                    case 1:
                        int i32 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default2 = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(7), 14);
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flowBroadcastFlow$default2);
                    default:
                        int i4 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).defaultConnections;
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, readonlyStateFlow);
                }
            }
        }), new EventsKt$$ExternalSyntheticLambda0(1, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(this, 0)));
        this.defaultDataSubRatConfig = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 10));
        final int i4 = 0;
        this.defaultMobileIconMapping = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda6
            public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i4) {
                    case 0:
                        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.f$0;
                        final MobileMappingsProxy mobileMappingsProxy2 = mobileMappingsProxy;
                        final int i5 = 0;
                        StateInit map2 = StateKt.map(mobileConnectionsRepositoryKairosImpl.defaultDataSubRatConfig, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda33
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                MobileMappingsProxy mobileMappingsProxy3 = mobileMappingsProxy2;
                                MobileMappings.Config config = (MobileMappings.Config) obj3;
                                switch (i5) {
                                    case 0:
                                        int i6 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy3).getClass();
                                        return MobileMappings.mapIconSets(config);
                                    default:
                                        int i7 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        return ((MobileMappingsProxyImpl) mobileMappingsProxy3).getDefaultIcons(config);
                                }
                            }
                        });
                        ((BuildScopeImpl) buildScope).observe(map2, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(8));
                        return map2;
                    default:
                        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl2 = this.f$0;
                        final MobileMappingsProxy mobileMappingsProxy3 = mobileMappingsProxy;
                        final int i6 = 1;
                        StateInit map3 = StateKt.map(mobileConnectionsRepositoryKairosImpl2.defaultDataSubRatConfig, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda33
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                MobileMappingsProxy mobileMappingsProxy32 = mobileMappingsProxy3;
                                MobileMappings.Config config = (MobileMappings.Config) obj3;
                                switch (i6) {
                                    case 0:
                                        int i62 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy32).getClass();
                                        return MobileMappings.mapIconSets(config);
                                    default:
                                        int i7 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        return ((MobileMappingsProxyImpl) mobileMappingsProxy32).getDefaultIcons(config);
                                }
                            }
                        });
                        ((BuildScopeImpl) buildScope).observe(map3, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(mobileConnectionsRepositoryKairosImpl2, 4));
                        return map3;
                }
            }
        });
        final int i5 = 1;
        this.defaultMobileIconGroup = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda6
            public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i5) {
                    case 0:
                        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.f$0;
                        final MobileMappingsProxy mobileMappingsProxy2 = mobileMappingsProxy;
                        final int i52 = 0;
                        StateInit map2 = StateKt.map(mobileConnectionsRepositoryKairosImpl.defaultDataSubRatConfig, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda33
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                MobileMappingsProxy mobileMappingsProxy32 = mobileMappingsProxy2;
                                MobileMappings.Config config = (MobileMappings.Config) obj3;
                                switch (i52) {
                                    case 0:
                                        int i62 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy32).getClass();
                                        return MobileMappings.mapIconSets(config);
                                    default:
                                        int i7 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        return ((MobileMappingsProxyImpl) mobileMappingsProxy32).getDefaultIcons(config);
                                }
                            }
                        });
                        ((BuildScopeImpl) buildScope).observe(map2, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(8));
                        return map2;
                    default:
                        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl2 = this.f$0;
                        final MobileMappingsProxy mobileMappingsProxy3 = mobileMappingsProxy;
                        final int i6 = 1;
                        StateInit map3 = StateKt.map(mobileConnectionsRepositoryKairosImpl2.defaultDataSubRatConfig, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda33
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                MobileMappingsProxy mobileMappingsProxy32 = mobileMappingsProxy3;
                                MobileMappings.Config config = (MobileMappings.Config) obj3;
                                switch (i6) {
                                    case 0:
                                        int i62 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy32).getClass();
                                        return MobileMappings.mapIconSets(config);
                                    default:
                                        int i7 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                                        return ((MobileMappingsProxyImpl) mobileMappingsProxy32).getDefaultIcons(config);
                                }
                            }
                        });
                        ((BuildScopeImpl) buildScope).observe(map3, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(mobileConnectionsRepositoryKairosImpl2, 4));
                        return map3;
                }
            }
        });
        this.isAnySimSecure = kairosBuilderImpl.buildState(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 0));
        final int i6 = 2;
        State stateBuildState4 = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object obj2 = connectivityRepository;
                BuildScope buildScope = (BuildScope) obj;
                switch (i6) {
                    case 0:
                        int i22 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 14);
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl, flowBroadcastFlow$default);
                    case 1:
                        int i32 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        Flow flowBroadcastFlow$default2 = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) obj2, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(7), 14);
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flowBroadcastFlow$default2);
                    default:
                        int i42 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                        ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).defaultConnections;
                        BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                        buildScopeImpl3.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl3, readonlyStateFlow);
                }
            }
        });
        final StateInit map2 = StateKt.map(stateBuildState4, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(0));
        final int i7 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i7) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogger, "Repo", "mobileIsDefault");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogger, "Repo", "hasCarrierMergedConnection");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map2, this.tableLogger, "Repo", "defaultConnectionIsValidated");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map2, this.tableLogger, "Repo", "activeSubId");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.mobileIsDefault = map2;
        final StateInit map3 = StateKt.map(stateBuildState, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(1));
        final int i8 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i8) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogger, "Repo", "mobileIsDefault");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogger, "Repo", "hasCarrierMergedConnection");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map3, this.tableLogger, "Repo", "defaultConnectionIsValidated");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map3, this.tableLogger, "Repo", "activeSubId");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.hasCarrierMergedConnection = map3;
        final StateInit map4 = StateKt.map(stateBuildState4, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(2));
        final int i9 = 2;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogger, "Repo", "mobileIsDefault");
                        break;
                    case 1:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogger, "Repo", "hasCarrierMergedConnection");
                        break;
                    case 2:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map4, this.tableLogger, "Repo", "defaultConnectionIsValidated");
                        break;
                    default:
                        DiffableKt.logIntDiffsForTable(buildScope, map4, this.tableLogger, "Repo", "activeSubId");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.defaultConnectionIsValidated = map4;
        this.activeSubChangedInGroupEvent = kairosBuilderImpl.buildEvents(new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(this, 2));
        StateInit map5 = StateKt.map(stateBuildState3, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(3));
        this.isInEcmModeTopLevel = map5;
        this.isInEcmMode = StateKt.flatMap(map5, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(this, 1));
        this.carrierMergedSelector = new StateSelector(stateBuildState, GroupByKt.groupByKey(EventsKt.map(StateKt.getChanges(stateBuildState), new Function2() { // from class: com.android.systemui.kairos.SelectorKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return MapsKt__MapsKt.mapOf(new Pair(obj2, Boolean.TRUE), new Pair(((TransactionScope) obj).sampleDeferred(stateBuildState).unwrapped.getValue(), Boolean.FALSE));
            }
        }), null));
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        FullMobileConnectionRepositoryKairos fullMobileConnectionRepositoryKairos;
        FullMobileConnectionRepositoryKairos.DumpCache dumpCache;
        DumpCache dumpCache2 = this.dumpCache;
        if (dumpCache2 == null) {
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
        indentingPrintWriter.println("Connection cache:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry entry : dumpCache2.repos.entrySet()) {
            indentingPrintWriter.println(((Number) entry.getKey()).intValue() + ": " + ((MobileConnectionRepositoryKairos) entry.getValue()));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Connections (" + dumpCache2.repos.size() + " total):");
        indentingPrintWriter.increaseIndent();
        for (MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos : dumpCache2.repos.values()) {
            if ((mobileConnectionRepositoryKairos instanceof FullMobileConnectionRepositoryKairos) && (dumpCache = (fullMobileConnectionRepositoryKairos = (FullMobileConnectionRepositoryKairos) mobileConnectionRepositoryKairos).dumpCache) != null) {
                IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(indentingPrintWriter, "  ");
                indentingPrintWriter2.println("MobileConnectionRepository[" + fullMobileConnectionRepositoryKairos.subId + "]");
                indentingPrintWriter2.increaseIndent();
                indentingPrintWriter2.println("carrierMerged=" + dumpCache.isCarrierMerged);
                indentingPrintWriter2.print("Type (cellular or carrier merged): ");
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos2 = dumpCache.activeRepo;
                if (mobileConnectionRepositoryKairos2 instanceof CarrierMergedConnectionRepositoryKairos) {
                    indentingPrintWriter2.println("Carrier merged");
                } else if (mobileConnectionRepositoryKairos2 instanceof MobileConnectionRepositoryKairosImpl) {
                    indentingPrintWriter2.println("Cellular");
                }
                indentingPrintWriter2.increaseIndent();
                indentingPrintWriter2.println("Provider: " + mobileConnectionRepositoryKairos2);
                indentingPrintWriter2.decreaseIndent();
                indentingPrintWriter2.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
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
        return this.mobileConnectionsBySubId;
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
