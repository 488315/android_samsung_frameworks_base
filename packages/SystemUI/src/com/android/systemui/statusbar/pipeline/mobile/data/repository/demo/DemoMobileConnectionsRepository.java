package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.content.Context;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class DemoMobileConnectionsRepository implements MobileConnectionsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _subscriptions;
    public final ReadonlyStateFlow activeMobileDataRepository;
    public final ReadonlyStateFlow activeMobileDataSubscriptionId;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 activeSubChangedInGroupEvent;
    public final StateFlowImpl defaultConnectionIsValidated;
    public final StateFlowImpl defaultDataSubId;
    public final StateFlowImpl defaultDataSubRatConfig;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 defaultMobileIconGroup;
    public final StateFlowImpl defaultMobileIconMapping;
    public final StateFlowImpl defaultMobileIconMappingTable;
    public final StateFlowImpl deviceOnTheCall;
    public final StateFlowImpl hasCarrierMergedConnection;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isAnySimSecure;
    public final StateFlowImpl isDeviceEmergencyCallCapable;
    public final TableLogBufferFactory logFactory;
    public final DemoModeMobileConnectionDataSource mobileDataSource;
    public final StandaloneCoroutine mobileDemoCommandJob;
    public final StateFlowImpl mobileIsDefault;
    public final ReadonlyStateFlow mobileMappingsReverseLookup;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow subscriptions;
    public final DemoModeWifiDataSource wifiDataSource;
    public final StandaloneCoroutine wifiDemoCommandJob;
    public Map connectionRepoCache = new LinkedHashMap();
    public final Map subscriptionInfoCache = new LinkedHashMap();

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

    public DemoMobileConnectionsRepository(DemoModeMobileConnectionDataSource demoModeMobileConnectionDataSource, DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope, Context context, TableLogBufferFactory tableLogBufferFactory) {
        this.mobileDataSource = demoModeMobileConnectionDataSource;
        this.wifiDataSource = demoModeWifiDataSource;
        this.scope = coroutineScope;
        this.logFactory = tableLogBufferFactory;
        SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._subscriptions = stateFlowImplMutableStateFlow;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow, new DemoMobileConnectionsRepository$subscriptions$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow.getValue());
        this.subscriptions = readonlyStateFlowStateIn;
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(readonlyStateFlowStateIn, new DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1(null));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        SubscriptionModel subscriptionModel = (SubscriptionModel) CollectionsKt___CollectionsKt.firstOrNull((List) readonlyStateFlowStateIn.$$delegate_0.getValue());
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(channelFlowTransformLatestMapLatest, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Integer.valueOf(subscriptionModel != null ? subscriptionModel.subscriptionId : -1));
        this.activeMobileDataSubscriptionId = readonlyStateFlowStateIn2;
        this.activeMobileDataRepository = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoMobileConnectionsRepository this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DemoMobileConnectionsRepository demoMobileConnectionsRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoMobileConnectionsRepository;
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
                        DemoMobileConnectionRepository repoForSubId = this.this$0.getRepoForSubId(((Number) obj).intValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(repoForSubId, anonymousClass1) == coroutineSingletons) {
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
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), getRepoForSubId(((Number) readonlyStateFlowStateIn2.$$delegate_0.getValue()).intValue()));
        this.activeSubChangedInGroupEvent = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new Unit[0]);
        this.defaultDataSubRatConfig = StateFlowKt.MutableStateFlow(MobileMappings.Config.readConfig(context));
        this.defaultMobileIconGroup = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TelephonyIcons.THREE_G);
        Boolean bool = Boolean.FALSE;
        this.isDeviceEmergencyCallCapable = StateFlowKt.MutableStateFlow(bool);
        this.defaultMobileIconMappingTable = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this.isAnySimSecure = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(TelephonyIcons.ICON_NAME_TO_ICON);
        this.defaultMobileIconMapping = stateFlowImplMutableStateFlow2;
        this.mobileMappingsReverseLookup = FlowKt.stateIn(FlowKt.mapLatest(stateFlowImplMutableStateFlow2, new DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), reverse$1((Map) stateFlowImplMutableStateFlow2.getValue()));
        this.defaultDataSubId = StateFlowKt.MutableStateFlow(null);
        Boolean bool2 = Boolean.TRUE;
        this.mobileIsDefault = StateFlowKt.MutableStateFlow(bool2);
        this.hasCarrierMergedConnection = StateFlowKt.MutableStateFlow(bool);
        this.defaultConnectionIsValidated = StateFlowKt.MutableStateFlow(bool2);
        this.deviceOnTheCall = StateFlowKt.MutableStateFlow(bool);
    }

    public static Map reverse$1(Map map) {
        Set<Map.Entry> setEntrySet = map.entrySet();
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        for (Map.Entry entry : setEntrySet) {
            linkedHashMap.put(entry.getValue(), entry.getKey());
        }
        return linkedHashMap;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean bootstrapProfile(int i) {
        return false;
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
        return false;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final CoverScreenNetworkSignalModel getNoServiceInfo() {
        return new CoverScreenNetworkSignalModel(false, 0);
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

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Object isInEcmMode(Continuation continuation) {
        return Boolean.FALSE;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final DemoMobileConnectionRepository getRepoForSubId(int i) {
        CacheContainer cacheContainer = (CacheContainer) ((LinkedHashMap) this.connectionRepoCache).get(Integer.valueOf(i));
        DemoMobileConnectionRepository demoMobileConnectionRepository = cacheContainer != null ? cacheContainer.repo : null;
        if (demoMobileConnectionRepository != null) {
            return demoMobileConnectionRepository;
        }
        CacheContainer cacheContainer2 = new CacheContainer(new DemoMobileConnectionRepository(i, this.logFactory.getOrCreate(100, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "DemoMobileConnectionLog[", "]")), this.scope), null);
        this.connectionRepoCache.put(Integer.valueOf(i), cacheContainer2);
        return cacheContainer2.repo;
    }
}
