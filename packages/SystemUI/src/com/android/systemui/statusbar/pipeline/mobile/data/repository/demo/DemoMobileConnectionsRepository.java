package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.content.Context;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import kotlinx.coroutines.Job;
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
    public final StateFlowImpl deviceServiceState;
    public final StateFlowImpl hasCarrierMergedConnection;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isAnySimSecure;
    public final TableLogBufferFactory logFactory;
    public final DemoModeMobileConnectionDataSource mobileDataSource;
    public final Job mobileDemoCommandJob;
    public final StateFlowImpl mobileIsDefault;
    public final ReadonlyStateFlow mobileMappingsReverseLookup;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow subscriptions;
    public final DemoModeWifiDataSource wifiDataSource;
    public final Job wifiDemoCommandJob;
    public Map connectionRepoCache = new LinkedHashMap();
    public final Map subscriptionInfoCache = new LinkedHashMap();

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

    public DemoMobileConnectionsRepository(DemoModeMobileConnectionDataSource demoModeMobileConnectionDataSource, DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope, Context context, TableLogBufferFactory tableLogBufferFactory) {
        this.scope = coroutineScope;
        this.logFactory = tableLogBufferFactory;
        SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._subscriptions = MutableStateFlow;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(MutableStateFlow, new DemoMobileConnectionsRepository$subscriptions$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow.getValue());
        this.subscriptions = stateIn;
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(stateIn, new DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1(null));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        SubscriptionModel subscriptionModel = (SubscriptionModel) CollectionsKt___CollectionsKt.firstOrNull((List) stateIn.$$delegate_0.getValue());
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(mapLatest, coroutineScope, WhileSubscribed$default, Integer.valueOf(subscriptionModel != null ? subscriptionModel.subscriptionId : -1));
        this.activeMobileDataSubscriptionId = stateIn2;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository r6 = r4.this$0
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository r5 = r6.getRepoForSubId(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), getRepoForSubId(((Number) stateIn2.$$delegate_0.getValue()).intValue()));
        this.activeSubChangedInGroupEvent = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new Unit[0]);
        this.defaultDataSubRatConfig = StateFlowKt.MutableStateFlow(MobileMappings.Config.readConfig(context));
        this.defaultMobileIconGroup = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TelephonyIcons.THREE_G);
        this.deviceServiceState = StateFlowKt.MutableStateFlow(null);
        this.defaultMobileIconMappingTable = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        Boolean bool = Boolean.FALSE;
        this.isAnySimSecure = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(TelephonyIcons.ICON_NAME_TO_ICON);
        this.defaultMobileIconMapping = MutableStateFlow2;
        FlowKt.stateIn(FlowKt.mapLatest(MutableStateFlow2, new DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), reverse((Map) MutableStateFlow2.getValue()));
        this.defaultDataSubId = StateFlowKt.MutableStateFlow(-1);
        Boolean bool2 = Boolean.TRUE;
        this.mobileIsDefault = StateFlowKt.MutableStateFlow(bool2);
        this.hasCarrierMergedConnection = StateFlowKt.MutableStateFlow(bool);
        this.defaultConnectionIsValidated = StateFlowKt.MutableStateFlow(bool2);
        this.deviceOnTheCall = StateFlowKt.MutableStateFlow(bool);
    }

    public static Map reverse(Map map) {
        Set<Map.Entry> entrySet = map.entrySet();
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Map.Entry entry : entrySet) {
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
    public final StateFlow getDeviceServiceState() {
        return this.deviceServiceState;
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
        String m = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "DemoMobileConnectionLog[", "]");
        TableLogBufferFactory tableLogBufferFactory = this.logFactory;
        Object obj = ((LinkedHashMap) tableLogBufferFactory.existingBuffers).get(m);
        if (obj == null) {
            obj = tableLogBufferFactory.create(100, m);
            tableLogBufferFactory.existingBuffers.put(m, obj);
        }
        CacheContainer cacheContainer2 = new CacheContainer(new DemoMobileConnectionRepository(i, (TableLogBuffer) obj, this.scope), null);
        this.connectionRepoCache.put(Integer.valueOf(i), cacheContainer2);
        return cacheContainer2.repo;
    }
}
