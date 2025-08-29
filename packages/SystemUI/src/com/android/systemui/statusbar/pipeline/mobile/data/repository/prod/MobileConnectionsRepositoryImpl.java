package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
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
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.IntIterator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes3.dex */
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
    public final FullMobileConnectionRepository.Factory fullMobileRepoFactory;
    public final ReadonlyStateFlow hasCarrierMergedConnection;
    public final Flow isAnySimSecure;
    public final ReadonlyStateFlow isDeviceEmergencyCallCapable;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MobileInputLogger logger;
    public final MobileDataIconResource mobileDataIconResource;
    public final Map mobileIconMappingTable;
    public final ReadonlyStateFlow mobileIsDefault;
    public final String networkNameSeparator;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 semCarrierChangedEvent;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 semOMCChangedEvent;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 serviceStateChangedEvent;
    private final SettingsHelper settingsHelper;
    public final ConcurrentHashMap subIdRepositoryCache = new ConcurrentHashMap();
    public final SubscriptionManager subscriptionManager;
    public final SubscriptionManagerProxy subscriptionManagerProxy;
    public final ReadonlyStateFlow subscriptions;
    public final TelephonyManager telephonyManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isAnySimSecure$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = MobileConnectionsRepositoryImpl.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = MobileConnectionsRepositoryImpl.this;
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isAnySimSecure$1$callback$1
                    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                    public final void onSimStateChanged(int i2, int i3, int i4) {
                        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = mobileConnectionsRepositoryImpl;
                        MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl2.logger;
                        mobileInputLogger.getClass();
                        LogBuffer.log$default(mobileInputLogger.buffer, "MobileInputLog", LogLevel.INFO, "onSimStateChanged");
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(mobileConnectionsRepositoryImpl2.keyguardUpdateMonitor.isSimPinSecure()));
                    }
                };
                MobileConnectionsRepositoryImpl.this.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
                ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.FALSE);
                MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0 mobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0 = new MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0(MobileConnectionsRepositoryImpl.this, keyguardUpdateMonitorCallback, 0);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, mobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isDeviceEmergencyCallCapable$1, reason: invalid class name and case insensitive filesystem */
    final class C11021 extends SuspendLambda implements Function2 {
        int label;

        public C11021(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MobileConnectionsRepositoryImpl.this.new C11021(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11021) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            boolean z = true;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!MobileConnectionsRepositoryImpl.this.context.getPackageManager().hasSystemFeature("android.hardware.telephony.radio.access")) {
                return Boolean.FALSE;
            }
            IntRange intRangeUntil = RangesKt___RangesKt.until(0, MobileConnectionsRepositoryImpl.this.telephonyManager.getActiveModemCount());
            MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = MobileConnectionsRepositoryImpl.this;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(intRangeUntil, 10));
            Iterator it = intRangeUntil.iterator();
            while (it.hasNext()) {
                arrayList.add(mobileConnectionsRepositoryImpl.telephonyManager.getServiceStateForSlot(((IntIterator) it).nextInt()));
            }
            if (arrayList.isEmpty()) {
                z = false;
            } else {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    ServiceState serviceState = (ServiceState) obj2;
                    if (serviceState != null && serviceState.isEmergencyOnly()) {
                        break;
                    }
                }
                z = false;
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$isInEcmMode$1, reason: invalid class name and case insensitive filesystem */
    final class C11031 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C11031(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MobileConnectionsRepositoryImpl.this.isInEcmMode(this);
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
        this.defaultNetworkName = new NetworkNameModel.Default(context.getString(R.string.permlab_accessLastKnownCellId));
        this.networkNameSeparator = context.getString(com.android.systemui.R.string.status_bar_network_name_separator);
        dumpManager.registerNormalDumpable("MobileConnectionsRepository", this);
        this.mobileIconMappingTable = new LinkedHashMap();
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(wifiRepository.getWifiNetwork(), connectivityRepositoryImpl.defaultConnections, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode, new MobileConnectionsRepositoryImpl$carrierMergedSubId$1(null))), tableLogBuffer, "carrierMergedSubId");
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.carrierMergedSubId = readonlyStateFlowStateIn;
        Flow flowFlowOn = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1(this, null)), coroutineDispatcher);
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$simSettingsChangeEvent$1(this, null));
        final int i = 0;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$serviceStateChangedEvent$2(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Intent intent = (Intent) obj;
                switch (i) {
                    case 0:
                        int i2 = MobileConnectionsRepositoryImpl.$r8$clinit;
                        intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                        return Unit.INSTANCE;
                    default:
                        int i3 = MobileConnectionsRepositoryImpl.$r8$clinit;
                        int intExtra = intent.getIntExtra("subscription", -1);
                        if (intExtra == -1) {
                            return null;
                        }
                        return Integer.valueOf(intExtra);
                }
            }
        }, 14));
        this.serviceStateChangedEvent = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        Flow flowLogDiffsForTable2 = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowKt.mapLatest(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new C11021(null)), coroutineDispatcher)), tableLogBuffer, "Repo", "deviceEmergencyOnly", false);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isDeviceEmergencyCallCapable = FlowKt.stateIn(flowLogDiffsForTable2, coroutineScope, startedEagerly, bool);
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.mapLatest(FlowKt.merge(flowFlowOn, readonlyStateFlowStateIn, flowConflatedCallbackFlow, BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"), null, 14)), new MobileConnectionsRepositoryImpl$subscriptions$1(this, null)), new MobileConnectionsRepositoryImpl$subscriptions$2(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        this.subscriptions = FlowKt.stateIn(DiffableKt.logDiffsForTable(flowDistinctUntilChanged, tableLogBuffer, "Repo", "subscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1(this, null)), coroutineDispatcher)), tableLogBuffer, "activeSubId"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activeMobileDataSubscriptionId = readonlyStateFlowStateIn2;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FullMobileConnectionRepository orCreateRepoForSubId;
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
                        Integer num = (Integer) obj;
                        if (num == null) {
                            orCreateRepoForSubId = null;
                        } else {
                            int iIntValue = num.intValue();
                            int i3 = MobileConnectionsRepositoryImpl.$r8$clinit;
                            orCreateRepoForSubId = this.this$0.getOrCreateRepoForSubId(iIntValue);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(orCreateRepoForSubId, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final int i2 = 1;
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubId$2(this, null), DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Intent intent = (Intent) obj;
                switch (i2) {
                    case 0:
                        int i22 = MobileConnectionsRepositoryImpl.$r8$clinit;
                        intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                        return Unit.INSTANCE;
                    default:
                        int i3 = MobileConnectionsRepositoryImpl.$r8$clinit;
                        int intExtra = intent.getIntExtra("subscription", -1);
                        if (intExtra == -1) {
                            return null;
                        }
                        return Integer.valueOf(intExtra);
                }
            }
        }, 14)), tableLogBuffer, "defaultSubId")), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.defaultDataSubId = readonlyStateFlowStateIn3;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, 14), new MobileConnectionsRepositoryImpl$carrierConfigChangedEvent$1(this, null));
        this.carrierConfigChangedEvent = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.samsung.carrier.action.CARRIER_CHANGED"), null, 14), new MobileConnectionsRepositoryImpl$semCarrierChangedEvent$1(this, null));
        this.semCarrierChangedEvent = flowKt__TransformKt$onEach$$inlined$unsafeTransform$12;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$13 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.samsung.intent.action.OMC_CHANGED"), null, 14), new MobileConnectionsRepositoryImpl$semOMCChangedEvent$1(this, null));
        this.semOMCChangedEvent = flowKt__TransformKt$onEach$$inlined$unsafeTransform$13;
        final ReadonlyStateFlow readonlyStateFlowStateIn4 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$1(null), FlowKt.merge(readonlyStateFlowStateIn3, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, readonlyStateFlowStateIn2)), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$2(this, null))), new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MobileMappings.Config.readConfig(context));
        this.defaultDataSubRatConfig = readonlyStateFlowStateIn4;
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(flowFlowOn, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, flowKt__TransformKt$onEach$$inlined$unsafeTransform$13, flowConflatedCallbackFlow);
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
                        List listAccess$fetchSubscriptionsList = MobileConnectionsRepositoryImpl.access$fetchSubscriptionsList(mobileConnectionsRepositoryImpl);
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAccess$fetchSubscriptionsList, 10));
                        Iterator it = listAccess$fetchSubscriptionsList.iterator();
                        while (it.hasNext()) {
                            arrayList.add(MobileConnectionsRepositoryImpl.access$toSubscriptionModel(mobileConnectionsRepositoryImpl, (SubscriptionInfo) it.next()));
                        }
                        int size = arrayList.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj3 = arrayList.get(i3);
                            i3++;
                            SubscriptionModel subscriptionModel = (SubscriptionModel) obj3;
                            int slotIndex = SubscriptionManager.getSlotIndex(subscriptionModel.subscriptionId) == -1 ? 0 : SubscriptionManager.getSlotIndex(subscriptionModel.subscriptionId);
                            mobileConnectionsRepositoryImpl.mobileIconMappingTable.put(new Integer(slotIndex), mobileConnectionsRepositoryImpl.mobileDataIconResource.mapIconSets(slotIndex));
                        }
                        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(mobileConnectionsRepositoryImpl.mobileIconMappingTable);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(concurrentHashMap, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Map mapMapIconSets = this.this$0.mobileDataIconResource.mapIconSets(SubscriptionManager.getSlotIndex(SubscriptionManager.getActiveDataSubscriptionId()));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mapMapIconSets, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlowStateIn4.collect(new AnonymousClass2(flowCollector, this, mobileMappingsProxy), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        SignalIcon$MobileIconGroup defaultIcons = ((MobileMappingsProxyImpl) this.$mobileMappingsProxy$inlined).getDefaultIcons((MobileMappings.Config) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(defaultIcons, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlowStateIn4.collect(new AnonymousClass2(flowCollector, mobileMappingsProxy), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new MobileConnectionsRepositoryImpl$defaultMobileIconGroup$2(this, null));
        this.isAnySimSecure = FlowKt.distinctUntilChanged(DiffableKt.logDiffsForTable(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null)), coroutineDispatcher2), tableLogBuffer, "Repo", "isAnySimSecure", false));
        this.deviceOnTheCall = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryImpl$deviceOnTheCall$1(this, null))), tableLogBuffer, "Repo", "deviceOnTheCall", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final ReadonlyStateFlow readonlyStateFlow = connectivityRepositoryImpl.defaultConnections;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Boolean boolValueOf = Boolean.valueOf(((DefaultConnectionModel) obj).mobile.isDefault);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Boolean boolValueOf = Boolean.valueOf(((Integer) obj) != null);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Boolean boolValueOf = Boolean.valueOf(((DefaultConnectionModel) obj).isValidated);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "defaultConnectionIsValidated", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(readonlyStateFlowStateIn2);
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        WithPrev withPrev = (WithPrev) obj;
                        Integer num = (Integer) withPrev.component1();
                        Integer num2 = (Integer) withPrev.component2();
                        Unit unit = null;
                        if (num != null && num2 != null) {
                            MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
                            SubscriptionInfo activeSubscriptionInfo = mobileConnectionsRepositoryImpl.subscriptionManager.getActiveSubscriptionInfo(num.intValue());
                            ParcelUuid groupUuid = activeSubscriptionInfo != null ? activeSubscriptionInfo.getGroupUuid() : null;
                            SubscriptionInfo activeSubscriptionInfo2 = mobileConnectionsRepositoryImpl.subscriptionManager.getActiveSubscriptionInfo(num2.intValue());
                            ParcelUuid groupUuid2 = activeSubscriptionInfo2 != null ? activeSubscriptionInfo2.getGroupUuid() : null;
                            if (groupUuid != null && groupUuid.equals(groupUuid2)) {
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final List access$fetchSubscriptionsList(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl) {
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = mobileConnectionsRepositoryImpl.subscriptionManager.getCompleteActiveSubscriptionInfoList();
        ArrayList arrayList = new ArrayList();
        for (Object obj : completeActiveSubscriptionInfoList) {
            if (SubscriptionManager.getSlotIndex(((SubscriptionInfo) obj).getSubscriptionId()) != -1) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final SubscriptionModel access$toSubscriptionModel(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, SubscriptionInfo subscriptionInfo) {
        mobileConnectionsRepositoryImpl.getClass();
        return new SubscriptionModel(subscriptionInfo.getSubscriptionId(), subscriptionInfo.isOpportunistic(), subscriptionInfo.isOnlyNonTerrestrialNetwork(), subscriptionInfo.getGroupUuid(), subscriptionInfo.getCarrierName().toString(), subscriptionInfo.getProfileClass(), subscriptionInfo.isEmbedded(), subscriptionInfo.semGetProfileClass() == 1, SubscriptionManager.getSlotIndex(subscriptionInfo.getSubscriptionId()), mobileConnectionsRepositoryImpl.settingsHelper.isSimSettingOn(subscriptionInfo.getSubscriptionId()));
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object next;
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
                        Iterator it = ((List) obj).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            if (((SubscriptionModel) next).subscriptionId == this.$subId$inlined) {
                                break;
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(next, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        FullMobileConnectionRepository.Factory factory = this.fullMobileRepoFactory;
        factory.getClass();
        FullMobileConnectionRepository.Factory.Companion.getClass();
        return new FullMobileConnectionRepository(i, z, factory.logFactory.getOrCreate(100, "MobileConnectionLog[" + i + "]"), flow, this.defaultNetworkName, this.networkNameSeparator, factory.scope, factory.mobileRepoFactory, factory.carrierMergedRepoFactory, factory.dummyRepoFactory);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
        indentingPrintWriter.println("Connection cache:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry entry : this.subIdRepositoryCache.entrySet()) {
            entry.getClass();
            indentingPrintWriter.println(((Integer) entry.getKey()) + ": " + ((WeakReference) entry.getValue()).get());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Connections (" + this.subIdRepositoryCache.size() + " total):");
        indentingPrintWriter.increaseIndent();
        Iterator it = this.subIdRepositoryCache.values().iterator();
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
            FullMobileConnectionRepository fullMobileConnectionRepository = (FullMobileConnectionRepository) ((WeakReference) ((Map.Entry) CollectionsKt___CollectionsKt.first(this.subIdRepositoryCache.entrySet())).getValue()).get();
            if (fullMobileConnectionRepository != null && !((Boolean) fullMobileConnectionRepository.isInService.$$delegate_0.getValue()).booleanValue()) {
                i = 1;
            }
        } else if (this.subIdRepositoryCache.size() == 2) {
            for (Map.Entry entry : this.subIdRepositoryCache.entrySet()) {
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
                FullMobileConnectionRepository fullMobileConnectionRepositoryCreateRepositoryForSubId = createRepositoryForSubId(i);
                this.subIdRepositoryCache.put(Integer.valueOf(i), new WeakReference(fullMobileConnectionRepositoryCreateRepositoryForSubId));
                return fullMobileConnectionRepositoryCreateRepositoryForSubId;
            }
        }
        FullMobileConnectionRepository fullMobileConnectionRepositoryCreateRepositoryForSubId2 = createRepositoryForSubId(i);
        this.subIdRepositoryCache.put(Integer.valueOf(i), new WeakReference(fullMobileConnectionRepositoryCreateRepositoryForSubId2));
        return fullMobileConnectionRepositoryCreateRepositoryForSubId2;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final MobileConnectionRepository getRepoForSubId(int i) {
        return getOrCreateRepoForSubId(i);
    }

    public final ConcurrentHashMap<Integer, WeakReference<FullMobileConnectionRepository>> getSubIdRepoCache() {
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

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow isDeviceEmergencyCallCapable() {
        return this.isDeviceEmergencyCallCapable;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0086 -> B:30:0x0089). Please report as a decompilation issue!!! */
    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isInEcmMode(Continuation continuation) {
        C11031 c11031;
        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl;
        Iterator it;
        if (continuation instanceof C11031) {
            c11031 = (C11031) continuation;
            int i = c11031.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11031.label = i - Integer.MIN_VALUE;
            } else {
                c11031 = new C11031(continuation);
            }
        }
        Object obj = c11031.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11031.label;
        boolean z = false;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.telephonyManager.getEmergencyCallbackMode()) {
                return Boolean.TRUE;
            }
            List list = (List) this.subscriptions.$$delegate_0.getValue();
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it2 = list.iterator();
                mobileConnectionsRepositoryImpl = this;
                it = it2;
                if (it.hasNext()) {
                }
            }
            return Boolean.valueOf(z);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        it = (Iterator) c11031.L$1;
        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl2 = (MobileConnectionsRepositoryImpl) c11031.L$0;
        ResultKt.throwOnFailure(obj);
        if (!((Boolean) obj).booleanValue()) {
            z = true;
            return Boolean.valueOf(z);
        }
        mobileConnectionsRepositoryImpl = mobileConnectionsRepositoryImpl2;
        if (it.hasNext()) {
            FullMobileConnectionRepository orCreateRepoForSubId = mobileConnectionsRepositoryImpl.getOrCreateRepoForSubId(((SubscriptionModel) it.next()).subscriptionId);
            c11031.L$0 = mobileConnectionsRepositoryImpl;
            c11031.L$1 = it;
            c11031.label = 1;
            Object objIsInEcmMode = orCreateRepoForSubId.isInEcmMode(c11031);
            if (objIsInEcmMode == coroutineSingletons) {
                return coroutineSingletons;
            }
            mobileConnectionsRepositoryImpl2 = mobileConnectionsRepositoryImpl;
            obj = objIsInEcmMode;
            if (!((Boolean) obj).booleanValue()) {
            }
        }
        return Boolean.valueOf(z);
    }
}
