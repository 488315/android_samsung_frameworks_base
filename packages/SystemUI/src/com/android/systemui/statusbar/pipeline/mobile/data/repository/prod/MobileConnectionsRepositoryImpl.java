package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ServiceStateModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileDataIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxy;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class MobileConnectionsRepositoryImpl implements MobileConnectionsRepository, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeMobileDataRepository;
    public final ReadonlyStateFlow activeMobileDataSubscriptionId;
    public final Flow activeSubChangedInGroupEvent;
    public final AirplaneModeRepository airplaneModeRepository;
    public final CoroutineDispatcher bgDispatcher;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 carrierConfigChangedEvent;
    public final ReadonlyStateFlow carrierMergedSubId;
    public final Context context;
    public final ReadonlyStateFlow defaultConnectionIsValidated;
    public final ReadonlyStateFlow defaultDataSubId;
    public final ReadonlyStateFlow defaultDataSubRatConfig;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 defaultMobileIconGroup;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 defaultMobileIconMapping;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 defaultMobileIconMappingTable;
    public final NetworkNameModel.Default defaultNetworkName;
    public final ReadonlyStateFlow deviceOnTheCall;
    public final ReadonlyStateFlow deviceServiceState;
    public final FullMobileConnectionRepository.Factory fullMobileRepoFactory;
    public final ReadonlyStateFlow hasCarrierMergedConnection;
    public final Flow isAnySimSecure;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MobileInputLogger logger;
    public final MobileDataIconResource mobileDataIconResource;
    public final Map mobileIconMappingTable;
    public final ReadonlyStateFlow mobileIsDefault;
    public final String networkNameSeparator;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 semCarrierChangedEvent;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 semOMCChangedEvent;
    private final SettingsHelper settingsHelper;
    public final Map subIdRepositoryCache = new LinkedHashMap();
    public final SubscriptionManager subscriptionManager;
    public final SubscriptionManagerProxy subscriptionManagerProxy;
    public final ReadonlyStateFlow subscriptions;
    public final TelephonyManager telephonyManager;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MobileConnectionsRepositoryImpl(ConnectivityRepository connectivityRepository, SubscriptionManager subscriptionManager, SubscriptionManagerProxy subscriptionManagerProxy, TelephonyManager telephonyManager, MobileInputLogger mobileInputLogger, TableLogBuffer tableLogBuffer, final MobileMappingsProxy mobileMappingsProxy, BroadcastDispatcher broadcastDispatcher, Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher2, AirplaneModeRepository airplaneModeRepository, WifiRepository wifiRepository, FullMobileConnectionRepository.Factory factory, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, SimCardInfoUtil simCardInfoUtil, MobileDataIconResource mobileDataIconResource, SettingsHelper settingsHelper) {
        this.subscriptionManager = subscriptionManager;
        this.subscriptionManagerProxy = subscriptionManagerProxy;
        this.telephonyManager = telephonyManager;
        this.logger = mobileInputLogger;
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
        this.airplaneModeRepository = airplaneModeRepository;
        this.fullMobileRepoFactory = factory;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mobileDataIconResource = mobileDataIconResource;
        this.settingsHelper = settingsHelper;
        DeviceType.isEngOrUTBinary();
        this.defaultNetworkName = new NetworkNameModel.Default(context.getString(R.string.permdesc_runInBackground));
        this.networkNameSeparator = context.getString(com.android.systemui.R.string.status_bar_network_name_separator);
        dumpManager.registerNormalDumpable("MobileConnectionsRepository", this);
        this.mobileIconMappingTable = new LinkedHashMap();
        StateFlow wifiNetwork = wifiRepository.getWifiNetwork();
        MobileConnectionsRepositoryImpl$carrierMergedSubId$1 mobileConnectionsRepositoryImpl$carrierMergedSubId$1 = new MobileConnectionsRepositoryImpl$carrierMergedSubId$1(null);
        final ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(wifiNetwork, readonlyStateFlow, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode, mobileConnectionsRepositoryImpl$carrierMergedSubId$1)), tableLogBuffer, "carrierMergedSubId", (Integer) null);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.carrierMergedSubId = stateIn;
        Flow flowOn = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(this, null)), coroutineDispatcher);
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1(this, null));
        this.deviceServiceState = FlowKt.stateIn(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$deviceServiceState$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Intent intent = (Intent) obj;
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                Bundle extras = intent.getExtras();
                if (extras == null) {
                    MobileConnectionsRepositoryImpl.this.logger.logTopLevelServiceStateBroadcastMissingExtras(intExtra);
                    return null;
                }
                ServiceState newFromBundle = ServiceState.newFromBundle(extras);
                MobileConnectionsRepositoryImpl.this.logger.logTopLevelServiceStateBroadcastEmergencyOnly(intExtra, newFromBundle);
                if (intExtra != -1) {
                    return null;
                }
                ServiceStateModel.Companion.getClass();
                return new ServiceStateModel(newFromBundle.isEmergencyOnly());
            }
        }, 14)), coroutineScope, SharingStarted.Companion.Eagerly, null);
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.mapLatest(FlowKt.merge(flowOn, stateIn, conflatedCallbackFlow), new MobileConnectionsRepositoryImpl$subscriptions$1(this, null)), new MobileConnectionsRepositoryImpl$subscriptions$2(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        this.subscriptions = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged, tableLogBuffer, "Repo", "subscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1(this, null)), coroutineDispatcher)), tableLogBuffer, "activeSubId", (Integer) (-1)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activeMobileDataSubscriptionId = stateIn2;
        this.activeMobileDataRepository = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Integer r5 = (java.lang.Integer) r5
                        if (r5 != 0) goto L38
                        r5 = 0
                        goto L44
                    L38:
                        int r5 = r5.intValue()
                        int r6 = com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl.$r8$clinit
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl r6 = r4.this$0
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository r5 = r6.getOrCreateRepoForSubId(r5)
                    L44:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubId$2(this, null), DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$defaultDataSubId$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((Intent) obj).getIntExtra("subscription", -1));
            }
        }, 14)), tableLogBuffer, "Repo", "defaultSubId", -1)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), -1);
        this.defaultDataSubId = stateIn3;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 0, null, 14), new MobileConnectionsRepositoryImpl$carrierConfigChangedEvent$1(this, null));
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.samsung.carrier.action.CARRIER_CHANGED"), null, 0, null, 14), new MobileConnectionsRepositoryImpl$semCarrierChangedEvent$1(this, null));
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$13 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.samsung.intent.action.OMC_CHANGED"), null, 0, null, 14), new MobileConnectionsRepositoryImpl$semOMCChangedEvent$1(this, null));
        final ReadonlyStateFlow stateIn4 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$1(null), FlowKt.merge(stateIn3, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, stateIn2)), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$2(this, null))), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MobileMappings.Config.readConfig(context));
        this.defaultDataSubRatConfig = stateIn4;
        final ChannelLimitedFlowMerge merge = FlowKt.merge(flowOn, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, flowKt__TransformKt$onEach$$inlined$unsafeTransform$13, conflatedCallbackFlow);
        this.defaultMobileIconMappingTable = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
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
                        boolean r0 = r9 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto La1
                    L28:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlin.Unit r8 = (kotlin.Unit) r8
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl r8 = r7.this$0
                        android.telephony.SubscriptionManager r9 = r8.subscriptionManager
                        java.util.List r9 = r9.getCompleteActiveSubscriptionInfoList()
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r4 = 10
                        int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r9, r4)
                        r2.<init>(r4)
                        java.util.Iterator r9 = r9.iterator()
                    L4e:
                        boolean r4 = r9.hasNext()
                        if (r4 == 0) goto L62
                        java.lang.Object r4 = r9.next()
                        android.telephony.SubscriptionInfo r4 = (android.telephony.SubscriptionInfo) r4
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r4 = com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl.access$toSubscriptionModel(r8, r4)
                        r2.add(r4)
                        goto L4e
                    L62:
                        java.util.Iterator r9 = r2.iterator()
                    L66:
                        boolean r2 = r9.hasNext()
                        if (r2 == 0) goto L94
                        java.lang.Object r2 = r9.next()
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r2 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r2
                        int r4 = r2.subscriptionId
                        int r4 = android.telephony.SubscriptionManager.getSlotIndex(r4)
                        r5 = -1
                        if (r4 != r5) goto L7d
                        r2 = 0
                        goto L83
                    L7d:
                        int r2 = r2.subscriptionId
                        int r2 = android.telephony.SubscriptionManager.getSlotIndex(r2)
                    L83:
                        java.lang.Integer r4 = new java.lang.Integer
                        r4.<init>(r2)
                        java.util.Map r5 = r8.mobileIconMappingTable
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileDataIconResource r6 = r8.mobileDataIconResource
                        java.util.Map r2 = r6.mapIconSets(r2)
                        r5.put(r4, r2)
                        goto L66
                    L94:
                        java.util.Map r8 = r8.mobileIconMappingTable
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r8, r0)
                        if (r7 != r1) goto La1
                        return r1
                    La1:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(this, null));
        this.defaultMobileIconMapping = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, MobileMappingsProxy mobileMappingsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.settingslib.mobile.MobileMappings$Config r5 = (com.android.settingslib.mobile.MobileMappings.Config) r5
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl r5 = r4.this$0
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileDataIconResource r5 = r5.mobileDataIconResource
                        int r6 = android.telephony.SubscriptionManager.getActiveDataSubscriptionId()
                        int r6 = android.telephony.SubscriptionManager.getSlotIndex(r6)
                        java.util.Map r5 = r5.mapIconSets(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, mobileMappingsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new MobileConnectionsRepositoryImpl$defaultMobileIconMapping$2(this, null));
        this.defaultMobileIconGroup = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ MobileMappingsProxy $mobileMappingsProxy$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileMappingsProxy mobileMappingsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mobileMappingsProxy$inlined = mobileMappingsProxy;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.settingslib.mobile.MobileMappings$Config r5 = (com.android.settingslib.mobile.MobileMappings.Config) r5
                        com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy r6 = r4.$mobileMappingsProxy$inlined
                        com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl r6 = (com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl) r6
                        r6.getClass()
                        boolean r5 = r5.showAtLeast3G
                        if (r5 != 0) goto L42
                        com.android.settingslib.SignalIcon$MobileIconGroup r5 = com.android.settingslib.mobile.TelephonyIcons.G
                        goto L44
                    L42:
                        com.android.settingslib.SignalIcon$MobileIconGroup r5 = com.android.settingslib.mobile.TelephonyIcons.THREE_G
                    L44:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, mobileMappingsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new MobileConnectionsRepositoryImpl$defaultMobileIconGroup$2(this, null));
        this.isAnySimSecure = FlowKt.distinctUntilChanged(DiffableKt.logDiffsForTable(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$isAnySimSecure$1(this, null)), coroutineDispatcher2), tableLogBuffer, "Repo", "isAnySimSecure", false));
        Flow logDiffsForTable2 = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$deviceOnTheCall$1(this, null))), tableLogBuffer, "Repo", "deviceOnTheCall", false);
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.deviceOnTheCall = FlowKt.stateIn(logDiffsForTable2, coroutineScope, WhileSubscribed$default, bool);
        this.mobileIsDefault = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel) r5
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Mobile r5 = r5.mobile
                        boolean r5 = r5.isDefault
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Repo", "mobileIsDefault", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.hasCarrierMergedConnection = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Integer r5 = (java.lang.Integer) r5
                        if (r5 == 0) goto L38
                        r5 = r3
                        goto L39
                    L38:
                        r5 = 0
                    L39:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Repo", "hasCarrierMergedConnection", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.defaultConnectionIsValidated = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel) r5
                        boolean r5 = r5.isValidated
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "defaultConnectionIsValidated", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(stateIn2);
        this.activeSubChangedInGroupEvent = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L83
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.util.kotlin.WithPrev r7 = (com.android.systemui.util.kotlin.WithPrev) r7
                        java.lang.Object r8 = r7.component1()
                        java.lang.Integer r8 = (java.lang.Integer) r8
                        java.lang.Object r7 = r7.component2()
                        java.lang.Integer r7 = (java.lang.Integer) r7
                        r2 = 0
                        if (r8 == 0) goto L76
                        if (r7 != 0) goto L46
                        goto L76
                    L46:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl r4 = r6.this$0
                        android.telephony.SubscriptionManager r5 = r4.subscriptionManager
                        int r8 = r8.intValue()
                        android.telephony.SubscriptionInfo r8 = r5.getActiveSubscriptionInfo(r8)
                        if (r8 == 0) goto L59
                        android.os.ParcelUuid r8 = r8.getGroupUuid()
                        goto L5a
                    L59:
                        r8 = r2
                    L5a:
                        android.telephony.SubscriptionManager r4 = r4.subscriptionManager
                        int r7 = r7.intValue()
                        android.telephony.SubscriptionInfo r7 = r4.getActiveSubscriptionInfo(r7)
                        if (r7 == 0) goto L6b
                        android.os.ParcelUuid r7 = r7.getGroupUuid()
                        goto L6c
                    L6b:
                        r7 = r2
                    L6c:
                        if (r8 == 0) goto L76
                        boolean r7 = r8.equals(r7)
                        if (r7 == 0) goto L76
                        kotlin.Unit r2 = kotlin.Unit.INSTANCE
                    L76:
                        if (r2 == 0) goto L83
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r2, r0)
                        if (r6 != r1) goto L83
                        return r1
                    L83:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final SubscriptionModel access$toSubscriptionModel(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, SubscriptionInfo subscriptionInfo) {
        mobileConnectionsRepositoryImpl.getClass();
        return new SubscriptionModel(subscriptionInfo.getSubscriptionId(), subscriptionInfo.isOpportunistic(), subscriptionInfo.isOnlyNonTerrestrialNetwork(), subscriptionInfo.getGroupUuid(), subscriptionInfo.getCarrierName().toString(), subscriptionInfo.getProfileClass(), subscriptionInfo.isEmbedded(), subscriptionInfo.semGetProfileClass() == 1);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean bootstrapProfile(int i) {
        for (SubscriptionModel subscriptionModel : (List) this.subscriptions.$$delegate_0.getValue()) {
            if (subscriptionModel.subscriptionId == i) {
                return subscriptionModel.bootstrap;
            }
        }
        return false;
    }

    public final FullMobileConnectionRepository createRepositoryForSubId(final int i) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, SubscriptionManager.getSlotIndex(i), "createRepositoryForSubId(", "), slotId: ", "MobileConnectionsRepository");
        Integer num = (Integer) this.carrierMergedSubId.$$delegate_0.getValue();
        boolean z = num != null && i == num.intValue();
        final ReadonlyStateFlow readonlyStateFlow = this.subscriptions;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $subId$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1$2$1, reason: invalid class name */
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
                    this.$subId$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.Iterator r6 = r6.iterator()
                    L3a:
                        boolean r7 = r6.hasNext()
                        if (r7 == 0) goto L4e
                        java.lang.Object r7 = r6.next()
                        r2 = r7
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r2 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r2
                        int r2 = r2.subscriptionId
                        int r4 = r5.$subId$inlined
                        if (r2 != r4) goto L3a
                        goto L4f
                    L4e:
                        r7 = 0
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$subscriptionModelForSubId$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FullMobileConnectionRepository.Factory factory = this.fullMobileRepoFactory;
        factory.getClass();
        FullMobileConnectionRepository.Factory.Companion.getClass();
        String str = "MobileConnectionLog[" + i + "]";
        TableLogBufferFactory tableLogBufferFactory = factory.logFactory;
        Object obj = ((LinkedHashMap) tableLogBufferFactory.existingBuffers).get(str);
        if (obj == null) {
            obj = tableLogBufferFactory.create(100, str);
            tableLogBufferFactory.existingBuffers.put(str, obj);
        }
        return new FullMobileConnectionRepository(i, z, (TableLogBuffer) obj, flow, this.defaultNetworkName, this.networkNameSeparator, factory.scope, factory.mobileRepoFactory, factory.carrierMergedRepoFactory, factory.dummyRepoFactory);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
        indentingPrintWriter.println("Connection cache:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry entry : ((LinkedHashMap) this.subIdRepositoryCache).entrySet()) {
            indentingPrintWriter.println(((Number) entry.getKey()).intValue() + ": " + ((WeakReference) entry.getValue()).get());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Connections (" + this.subIdRepositoryCache.size() + " total):");
        indentingPrintWriter.increaseIndent();
        Iterator it = ((LinkedHashMap) this.subIdRepositoryCache).values().iterator();
        while (it.hasNext()) {
            FullMobileConnectionRepository fullMobileConnectionRepository = (FullMobileConnectionRepository) ((WeakReference) it.next()).get();
            if (fullMobileConnectionRepository != null) {
                IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(indentingPrintWriter, "  ");
                indentingPrintWriter2.println("MobileConnectionRepository[" + fullMobileConnectionRepository.subId + "]");
                indentingPrintWriter2.increaseIndent();
                indentingPrintWriter2.println("carrierMerged=" + fullMobileConnectionRepository._isCarrierMerged.getValue());
                indentingPrintWriter2.print("Type (cellular or carrier merged): ");
                ReadonlyStateFlow readonlyStateFlow = fullMobileConnectionRepository.activeRepo;
                MobileConnectionRepository mobileConnectionRepository = (MobileConnectionRepository) readonlyStateFlow.$$delegate_0.getValue();
                if (mobileConnectionRepository instanceof CarrierMergedConnectionRepository) {
                    indentingPrintWriter2.println("Carrier merged");
                } else if (mobileConnectionRepository instanceof MobileConnectionRepositoryImpl) {
                    indentingPrintWriter2.println("Cellular");
                }
                indentingPrintWriter2.increaseIndent();
                indentingPrintWriter2.println("Provider: " + readonlyStateFlow.$$delegate_0.getValue());
                indentingPrintWriter2.decreaseIndent();
                indentingPrintWriter2.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getActiveMobileDataRepository() {
        return this.activeMobileDataRepository;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getActiveMobileDataSubscriptionId() {
        return this.activeMobileDataSubscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getActiveSubChangedInGroupEvent() {
        return this.activeSubChangedInGroupEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultConnectionIsValidated() {
        return this.defaultConnectionIsValidated;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultDataSubId() {
        return this.defaultDataSubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultDataSubRatConfig() {
        return this.defaultDataSubRatConfig;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconGroup() {
        return this.defaultMobileIconGroup;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconMapping() {
        return this.defaultMobileIconMapping;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconMappingTable() {
        return this.defaultMobileIconMappingTable;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDeviceOnTheCall() {
        return this.deviceOnTheCall;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDeviceServiceState() {
        return this.deviceServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean getIsAnySimSecure() {
        return this.keyguardUpdateMonitor.isSimPinSecure();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final CoverScreenNetworkSignalModel getNoServiceInfo() {
        int i = 0;
        if (this.subIdRepositoryCache.size() == 1) {
            FullMobileConnectionRepository fullMobileConnectionRepository = (FullMobileConnectionRepository) ((WeakReference) ((Map.Entry) CollectionsKt___CollectionsKt.first(((LinkedHashMap) this.subIdRepositoryCache).entrySet())).getValue()).get();
            if (fullMobileConnectionRepository != null && !((Boolean) fullMobileConnectionRepository.isInService.$$delegate_0.getValue()).booleanValue()) {
                i = 1;
            }
        } else if (this.subIdRepositoryCache.size() == 2) {
            for (Map.Entry entry : ((LinkedHashMap) this.subIdRepositoryCache).entrySet()) {
                FullMobileConnectionRepository fullMobileConnectionRepository2 = (FullMobileConnectionRepository) ((WeakReference) entry.getValue()).get();
                if (fullMobileConnectionRepository2 != null && !((Boolean) fullMobileConnectionRepository2.isInService.$$delegate_0.getValue()).booleanValue()) {
                    i = SubscriptionManager.getSlotIndex(((Number) entry.getKey()).intValue()) == 0 ? i | 16 : i | 256;
                }
            }
        }
        return new CoverScreenNetworkSignalModel(((Boolean) ((AirplaneModeRepositoryImpl) this.airplaneModeRepository).isAirplaneMode.$$delegate_0.getValue()).booleanValue(), i);
    }

    public final FullMobileConnectionRepository getOrCreateRepoForSubId(int i) {
        FullMobileConnectionRepository fullMobileConnectionRepository;
        FullMobileConnectionRepository fullMobileConnectionRepository2;
        WeakReference weakReference = (WeakReference) this.subIdRepositoryCache.get(Integer.valueOf(i));
        if (weakReference != null && (fullMobileConnectionRepository = (FullMobileConnectionRepository) weakReference.get()) != null) {
            if (fullMobileConnectionRepository.slotId == SubscriptionManager.getSlotIndex(i)) {
                WeakReference weakReference2 = (WeakReference) this.subIdRepositoryCache.get(Integer.valueOf(i));
                if (weakReference2 != null && (fullMobileConnectionRepository2 = (FullMobileConnectionRepository) weakReference2.get()) != null) {
                    return fullMobileConnectionRepository2;
                }
                FullMobileConnectionRepository createRepositoryForSubId = createRepositoryForSubId(i);
                this.subIdRepositoryCache.put(Integer.valueOf(i), new WeakReference(createRepositoryForSubId));
                return createRepositoryForSubId;
            }
        }
        FullMobileConnectionRepository createRepositoryForSubId2 = createRepositoryForSubId(i);
        this.subIdRepositoryCache.put(Integer.valueOf(i), new WeakReference(createRepositoryForSubId2));
        return createRepositoryForSubId2;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final MobileConnectionRepository getRepoForSubId(int i) {
        return getOrCreateRepoForSubId(i);
    }

    public final Map<Integer, WeakReference<FullMobileConnectionRepository>> getSubIdRepoCache() {
        return this.subIdRepositoryCache;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow isAnySimSecure() {
        return this.isAnySimSecure;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0086 -> B:10:0x0089). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isInEcmMode(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 != r4) goto L30
            java.lang.Object r6 = r0.L$1
            java.util.Iterator r6 = (java.util.Iterator) r6
            java.lang.Object r2 = r0.L$0
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl r2 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl) r2
            kotlin.ResultKt.throwOnFailure(r7)
            goto L89
        L30:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            android.telephony.TelephonyManager r7 = r6.telephonyManager
            boolean r7 = r7.getEmergencyCallbackMode()
            if (r7 == 0) goto L46
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            return r6
        L46:
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r6.subscriptions
            kotlinx.coroutines.flow.StateFlow r7 = r7.$$delegate_0
            java.lang.Object r7 = r7.getValue()
            java.util.List r7 = (java.util.List) r7
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            boolean r2 = r7 instanceof java.util.Collection
            if (r2 == 0) goto L60
            r2 = r7
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L60
            goto L95
        L60:
            java.util.Iterator r7 = r7.iterator()
            r5 = r7
            r7 = r6
            r6 = r5
        L67:
            boolean r2 = r6.hasNext()
            if (r2 == 0) goto L95
            java.lang.Object r2 = r6.next()
            com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r2 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r2
            int r2 = r2.subscriptionId
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository r2 = r7.getOrCreateRepoForSubId(r2)
            r0.L$0 = r7
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r2 = r2.isInEcmMode(r0)
            if (r2 != r1) goto L86
            return r1
        L86:
            r5 = r2
            r2 = r7
            r7 = r5
        L89:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L93
            r3 = r4
            goto L95
        L93:
            r7 = r2
            goto L67
        L95:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl.isInEcmMode(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
