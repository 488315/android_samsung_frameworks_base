package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileDataIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxy;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.WithPrev;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileConnectionsRepositoryImpl implements MobileConnectionsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeMobileDataRepository;
    public final ReadonlyStateFlow activeMobileDataSubscriptionId;
    public final Flow activeSubChangedInGroupEvent;
    public final AirplaneModeRepository airplaneModeRepository;
    public final CoroutineDispatcher bgDispatcher;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 carrierConfigChangedEvent;
    public final CarrierInfraMediator carrierInfraMediator;
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
    public final FullMobileConnectionRepository.Factory fullMobileRepoFactory;
    public final ReadonlyStateFlow hasCarrierMergedConnection;
    public final MobileInputLogger logger;
    public final MobileDataIconResource mobileDataIconResource;
    public final Map mobileIconMappingTable;
    public final ReadonlyStateFlow mobileIsDefault;
    public final String networkNameSeparator;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 semCarrierChangedEvent;
    public final SettingsHelper settingsHelper;
    public final SimCardInfoUtil simCardInfoUtil;
    public Map subIdRepositoryCache = new LinkedHashMap();
    public final SubscriptionManager subscriptionManager;
    public final SubscriptionManagerProxy subscriptionManagerProxy;
    public final ReadonlyStateFlow subscriptions;
    public final TelephonyManager telephonyManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public MobileConnectionsRepositoryImpl(ConnectivityRepository connectivityRepository, SubscriptionManager subscriptionManager, SubscriptionManagerProxy subscriptionManagerProxy, TelephonyManager telephonyManager, MobileInputLogger mobileInputLogger, TableLogBuffer tableLogBuffer, final MobileMappingsProxy mobileMappingsProxy, BroadcastDispatcher broadcastDispatcher, Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, AirplaneModeRepository airplaneModeRepository, WifiRepository wifiRepository, FullMobileConnectionRepository.Factory factory, SimCardInfoUtil simCardInfoUtil, SettingsHelper settingsHelper, MobileDataIconResource mobileDataIconResource, CarrierInfraMediator carrierInfraMediator) {
        this.subscriptionManager = subscriptionManager;
        this.subscriptionManagerProxy = subscriptionManagerProxy;
        this.telephonyManager = telephonyManager;
        this.logger = mobileInputLogger;
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
        this.airplaneModeRepository = airplaneModeRepository;
        this.fullMobileRepoFactory = factory;
        this.simCardInfoUtil = simCardInfoUtil;
        this.settingsHelper = settingsHelper;
        this.mobileDataIconResource = mobileDataIconResource;
        this.carrierInfraMediator = carrierInfraMediator;
        DeviceType.isEngOrUTBinary();
        this.defaultNetworkName = new NetworkNameModel.Default(context.getString(R.string.permdesc_accessWimaxState));
        this.networkNameSeparator = context.getString(com.android.systemui.R.string.status_bar_network_name_separator);
        this.mobileIconMappingTable = new LinkedHashMap();
        StateFlow wifiNetwork = wifiRepository.getWifiNetwork();
        final ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(wifiNetwork, readonlyStateFlow, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode, new MobileConnectionsRepositoryImpl$carrierMergedSubId$1(null))), tableLogBuffer, "carrierMergedSubId", (Integer) null);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.carrierMergedSubId = stateIn;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1 = new MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(mobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1);
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1(this, null))), tableLogBuffer, "activeSubId", (Integer) (-1)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.activeMobileDataSubscriptionId = stateIn2;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.mapLatest(FlowKt.merge(conflatedCallbackFlow2, stateIn, stateIn2), new MobileConnectionsRepositoryImpl$subscriptions$1(this, null)), new MobileConnectionsRepositoryImpl$subscriptions$2(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        this.subscriptions = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged, tableLogBuffer, "Repo", "subscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), emptyList);
        this.activeMobileDataRepository = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2 */
            public final class C32922 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$1$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32922.this.emit(null, this);
                    }
                }

                public C32922(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    FullMobileConnectionRepository orCreateRepoForSubId;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Integer num = (Integer) obj;
                                if (num == null) {
                                    orCreateRepoForSubId = null;
                                } else {
                                    int intValue = num.intValue();
                                    int i3 = MobileConnectionsRepositoryImpl.$r8$clinit;
                                    orCreateRepoForSubId = this.this$0.getOrCreateRepoForSubId(intValue);
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(orCreateRepoForSubId, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32922(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubId$2(this, null), DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$defaultDataSubId$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((Intent) obj).getIntExtra("subscription", -1));
            }
        }, 14)), tableLogBuffer, "Repo", "defaultSubId", -1)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), -1);
        this.defaultDataSubId = stateIn3;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 0, null, 14), new MobileConnectionsRepositoryImpl$carrierConfigChangedEvent$1(this, null));
        this.carrierConfigChangedEvent = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.samsung.carrier.action.CARRIER_CHANGED"), null, 0, null, 14), new MobileConnectionsRepositoryImpl$semCarrierChangedEvent$1(this, null));
        this.semCarrierChangedEvent = flowKt__TransformKt$onEach$$inlined$unsafeTransform$12;
        final ReadonlyStateFlow stateIn4 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$1(null), FlowKt.merge(stateIn3, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, stateIn2)), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$2(this, null)), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MobileMappings.Config.readConfig(context));
        this.defaultDataSubRatConfig = stateIn4;
        final ChannelLimitedFlowMerge merge = FlowKt.merge(conflatedCallbackFlow2, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12);
        this.defaultMobileIconMappingTable = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2 */
            public final class C32932 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$2$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32932.this.emit(null, this);
                    }
                }

                public C32932(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
                                List access$fetchSubscriptionsList = MobileConnectionsRepositoryImpl.access$fetchSubscriptionsList(mobileConnectionsRepositoryImpl);
                                ArrayList<SubscriptionModel> arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(access$fetchSubscriptionsList, 10));
                                Iterator it = access$fetchSubscriptionsList.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(MobileConnectionsRepositoryImpl.access$toSubscriptionModel(mobileConnectionsRepositoryImpl, (SubscriptionInfo) it.next()));
                                }
                                for (SubscriptionModel subscriptionModel : arrayList) {
                                    int slotIndex = SubscriptionManager.getSlotIndex(subscriptionModel.subscriptionId) == -1 ? 0 : SubscriptionManager.getSlotIndex(subscriptionModel.subscriptionId);
                                    mobileConnectionsRepositoryImpl.mobileIconMappingTable.put(new Integer(slotIndex), mobileConnectionsRepositoryImpl.mobileDataIconResource.mapIconSets(slotIndex));
                                }
                                Map map = mobileConnectionsRepositoryImpl.mobileIconMappingTable;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(map, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32932(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(this, null));
        this.defaultMobileIconMapping = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2 */
            public final class C32942 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$3$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32942.this.emit(null, this);
                    }
                }

                public C32942(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, MobileMappingsProxy mobileMappingsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Map mapIconSets = this.this$0.mobileDataIconResource.mapIconSets(SubscriptionManager.getSlotIndex(SubscriptionManager.getActiveDataSubscriptionId()));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(mapIconSets, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32942(flowCollector, this, mobileMappingsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new MobileConnectionsRepositoryImpl$defaultMobileIconMapping$2(this, null));
        this.defaultMobileIconGroup = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2 */
            public final class C32952 implements FlowCollector {
                public final /* synthetic */ MobileMappingsProxy $mobileMappingsProxy$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$4$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32952.this.emit(null, this);
                    }
                }

                public C32952(FlowCollector flowCollector, MobileMappingsProxy mobileMappingsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mobileMappingsProxy$inlined = mobileMappingsProxy;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                ((MobileMappingsProxyImpl) this.$mobileMappingsProxy$inlined).getClass();
                                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = !((MobileMappings.Config) obj).showAtLeast3G ? TelephonyIcons.f221G : TelephonyIcons.THREE_G;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(signalIcon$MobileIconGroup, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32952(flowCollector, mobileMappingsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new MobileConnectionsRepositoryImpl$defaultMobileIconGroup$2(this, null));
        SafeFlow logDiffsForTable2 = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$deviceOnTheCall$1(this, null))), tableLogBuffer, "Repo", "deviceOnTheCall", false);
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        this.deviceOnTheCall = FlowKt.stateIn(logDiffsForTable2, coroutineScope, WhileSubscribed$default, bool);
        this.mobileIsDefault = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2 */
            public final class C32962 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$5$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32962.this.emit(null, this);
                    }
                }

                public C32962(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((DefaultConnectionModel) obj).mobile.isDefault);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32962(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Repo", "mobileIsDefault", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.hasCarrierMergedConnection = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2 */
            public final class C32972 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$6$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32972.this.emit(null, this);
                    }
                }

                public C32972(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((Integer) obj) != null);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32972(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Repo", "hasCarrierMergedConnection", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.defaultConnectionIsValidated = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2 */
            public final class C32982 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$map$7$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32982.this.emit(null, this);
                    }
                }

                public C32982(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((DefaultConnectionModel) obj).isValidated);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32982(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "defaultConnectionIsValidated", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        final SafeFlow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(stateIn2);
        this.activeSubChangedInGroupEvent = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2 */
            public final class C32992 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$special$$inlined$mapNotNull$1$2", m277f = "MobileConnectionsRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32992.this.emit(null, this);
                    }
                }

                public C32992(FlowCollector flowCollector, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileConnectionsRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                WithPrev withPrev = (WithPrev) obj;
                                Integer num = (Integer) withPrev.previousValue;
                                Integer num2 = (Integer) withPrev.newValue;
                                Unit unit = null;
                                if (num != null && num2 != null) {
                                    MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
                                    SubscriptionInfo activeSubscriptionInfo = mobileConnectionsRepositoryImpl.subscriptionManager.getActiveSubscriptionInfo(num.intValue());
                                    ParcelUuid groupUuid = activeSubscriptionInfo != null ? activeSubscriptionInfo.getGroupUuid() : null;
                                    SubscriptionInfo activeSubscriptionInfo2 = mobileConnectionsRepositoryImpl.subscriptionManager.getActiveSubscriptionInfo(num2.intValue());
                                    ParcelUuid groupUuid2 = activeSubscriptionInfo2 != null ? activeSubscriptionInfo2.getGroupUuid() : null;
                                    if (groupUuid != null && Intrinsics.areEqual(groupUuid, groupUuid2)) {
                                        unit = Unit.INSTANCE;
                                    }
                                }
                                if (unit != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32992(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final List access$fetchSubscriptionsList(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = mobileConnectionsRepositoryImpl.subscriptionManager.getCompleteActiveSubscriptionInfoList();
        boolean z = true;
        if (completeActiveSubscriptionInfoList.size() != 0 && (completeActiveSubscriptionInfoList.size() != 1 || completeActiveSubscriptionInfoList.get(0).getSimSlotIndex() != 1 || mobileConnectionsRepositoryImpl.simCardInfoUtil.isSimSettingOn(1))) {
            z = false;
        }
        if (z) {
            completeActiveSubscriptionInfoList.add(new SubscriptionInfo(Integer.MAX_VALUE, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, 0, "", null, 0, -1, "", 0, null, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "", false, null, null));
        }
        return completeActiveSubscriptionInfoList;
    }

    public static final SubscriptionModel access$toSubscriptionModel(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, SubscriptionInfo subscriptionInfo) {
        mobileConnectionsRepositoryImpl.getClass();
        return new SubscriptionModel(subscriptionInfo.getSubscriptionId(), subscriptionInfo.getSimSlotIndex(), subscriptionInfo.isOpportunistic(), subscriptionInfo.getGroupUuid(), subscriptionInfo.semGetProfileClass() == 1, subscriptionInfo.isEmbedded());
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean bootstrapProfile(int i) {
        for (SubscriptionModel subscriptionModel : (List) this.subscriptions.getValue()) {
            if (subscriptionModel.subscriptionId == i) {
                return subscriptionModel.bootstrap;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final ReadonlyStateFlow getActiveMobileDataRepository() {
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
    public final Flow getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final CoverScreenNetworkSignalModel getNoServiceInfo() {
        int i = 0;
        if (this.subIdRepositoryCache.size() == 1) {
            if (!((Boolean) ((FullMobileConnectionRepository) ((Map.Entry) CollectionsKt___CollectionsKt.first(((LinkedHashMap) this.subIdRepositoryCache).entrySet())).getValue()).isInService.getValue()).booleanValue()) {
                i = 1;
            }
        } else if (this.subIdRepositoryCache.size() == 2) {
            for (Map.Entry entry : ((LinkedHashMap) this.subIdRepositoryCache).entrySet()) {
                if (!((Boolean) ((FullMobileConnectionRepository) entry.getValue()).isInService.getValue()).booleanValue()) {
                    i = SubscriptionManager.getSlotIndex(((Number) entry.getKey()).intValue()) == 0 ? i | 16 : i | 256;
                }
            }
        }
        return new CoverScreenNetworkSignalModel(((Boolean) ((AirplaneModeRepositoryImpl) this.airplaneModeRepository).isAirplaneMode.getValue()).booleanValue(), i);
    }

    public final FullMobileConnectionRepository getOrCreateRepoForSubId(int i) {
        FullMobileConnectionRepository fullMobileConnectionRepository = (FullMobileConnectionRepository) ((LinkedHashMap) this.subIdRepositoryCache).get(Integer.valueOf(i));
        if (fullMobileConnectionRepository != null) {
            return fullMobileConnectionRepository;
        }
        Integer num = (Integer) this.carrierMergedSubId.getValue();
        boolean z = num != null && i == num.intValue();
        NetworkNameModel.Default r5 = this.defaultNetworkName;
        String str = this.networkNameSeparator;
        FullMobileConnectionRepository.Factory factory = this.fullMobileRepoFactory;
        factory.getClass();
        FullMobileConnectionRepository.Factory.Companion.getClass();
        String str2 = "MobileConnectionLog[" + i + "]";
        TableLogBufferFactory tableLogBufferFactory = factory.logFactory;
        Map map = tableLogBufferFactory.existingBuffers;
        Object obj = ((LinkedHashMap) map).get(str2);
        if (obj == null) {
            obj = tableLogBufferFactory.create(100, str2);
            map.put(str2, obj);
        }
        FullMobileConnectionRepository fullMobileConnectionRepository2 = new FullMobileConnectionRepository(i, z, (TableLogBuffer) obj, r5, str, factory.scope, factory.mobileRepoFactory, factory.carrierMergedRepoFactory, factory.dummyRepoFactory);
        this.subIdRepositoryCache.put(Integer.valueOf(i), fullMobileConnectionRepository2);
        return fullMobileConnectionRepository2;
    }

    public final Map<Integer, FullMobileConnectionRepository> getSubIdRepoCache() {
        return this.subIdRepositoryCache;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getSubscriptions() {
        return this.subscriptions;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FullMobileConnectionRepository getRepoForSubId(int i) {
        boolean z;
        List list = (List) this.subscriptions.getValue();
        Integer num = (Integer) this.activeMobileDataSubscriptionId.getValue();
        if (num == null || num.intValue() != i) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((SubscriptionModel) it.next()).subscriptionId == i) {
                }
            }
            z = false;
            if (!z) {
                Log.e("MobileConnectionsRepository", "subscriptionId " + i + " is not in the list of valid subscriptions");
            }
            return getOrCreateRepoForSubId(i);
        }
        z = true;
        if (!z) {
        }
        return getOrCreateRepoForSubId(i);
    }
}
