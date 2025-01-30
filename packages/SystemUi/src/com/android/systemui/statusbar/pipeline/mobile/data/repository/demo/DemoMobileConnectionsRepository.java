package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.content.Context;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
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
import kotlin.coroutines.jvm.internal.DebugMetadata;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final TableLogBufferFactory logFactory;
    public final DemoModeMobileConnectionDataSource mobileDataSource;
    public StandaloneCoroutine mobileDemoCommandJob;
    public final StateFlowImpl mobileIsDefault;
    public final ReadonlyStateFlow mobileMappingsReverseLookup;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow subscriptions;
    public final DemoModeWifiDataSource wifiDataSource;
    public StandaloneCoroutine wifiDemoCommandJob;
    public Map connectionRepoCache = new LinkedHashMap();
    public final Map subscriptionInfoCache = new LinkedHashMap();

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

    public DemoMobileConnectionsRepository(DemoModeMobileConnectionDataSource demoModeMobileConnectionDataSource, DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope, Context context, TableLogBufferFactory tableLogBufferFactory) {
        this.mobileDataSource = demoModeMobileConnectionDataSource;
        this.wifiDataSource = demoModeWifiDataSource;
        this.scope = coroutineScope;
        this.logFactory = tableLogBufferFactory;
        SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._subscriptions = MutableStateFlow;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(MutableStateFlow, new DemoMobileConnectionsRepository$subscriptions$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow.getValue());
        this.subscriptions = stateIn;
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(stateIn, new DemoMobileConnectionsRepository$activeMobileDataSubscriptionId$1(null));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        SubscriptionModel subscriptionModel = (SubscriptionModel) CollectionsKt___CollectionsKt.firstOrNull((List) stateIn.getValue());
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(mapLatest, coroutineScope, WhileSubscribed$default, Integer.valueOf(subscriptionModel != null ? subscriptionModel.subscriptionId : -1));
        this.activeMobileDataSubscriptionId = stateIn2;
        this.activeMobileDataRepository = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2 */
            public final class C32242 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoMobileConnectionsRepository this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository$special$$inlined$map$1$2", m277f = "DemoMobileConnectionsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32242.this.emit(null, this);
                    }
                }

                public C32242(FlowCollector flowCollector, DemoMobileConnectionsRepository demoMobileConnectionsRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoMobileConnectionsRepository;
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
                                DemoMobileConnectionRepository repoForSubId = this.this$0.getRepoForSubId(((Number) obj).intValue());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(repoForSubId, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C32242(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), getRepoForSubId(((Number) stateIn2.getValue()).intValue()));
        this.activeSubChangedInGroupEvent = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new Unit[0]);
        this.defaultDataSubRatConfig = StateFlowKt.MutableStateFlow(MobileMappings.Config.readConfig(context));
        this.defaultMobileIconGroup = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TelephonyIcons.THREE_G);
        this.defaultMobileIconMappingTable = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(TelephonyIcons.ICON_NAME_TO_ICON);
        this.defaultMobileIconMapping = MutableStateFlow2;
        this.mobileMappingsReverseLookup = FlowKt.stateIn(FlowKt.mapLatest(MutableStateFlow2, new DemoMobileConnectionsRepository$mobileMappingsReverseLookup$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), reverse((Map) MutableStateFlow2.getValue()));
        this.defaultDataSubId = StateFlowKt.MutableStateFlow(-1);
        Boolean bool = Boolean.TRUE;
        this.mobileIsDefault = StateFlowKt.MutableStateFlow(bool);
        Boolean bool2 = Boolean.FALSE;
        this.hasCarrierMergedConnection = StateFlowKt.MutableStateFlow(bool2);
        this.defaultConnectionIsValidated = StateFlowKt.MutableStateFlow(bool);
        this.deviceOnTheCall = StateFlowKt.MutableStateFlow(bool2);
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
        return new CoverScreenNetworkSignalModel(false, 0);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final DemoMobileConnectionRepository getRepoForSubId(int i) {
        CacheContainer cacheContainer = (CacheContainer) ((LinkedHashMap) this.connectionRepoCache).get(Integer.valueOf(i));
        DemoMobileConnectionRepository demoMobileConnectionRepository = cacheContainer != null ? cacheContainer.repo : null;
        if (demoMobileConnectionRepository != null) {
            return demoMobileConnectionRepository;
        }
        String m31m = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("DemoMobileConnectionLog[", i, "]");
        TableLogBufferFactory tableLogBufferFactory = this.logFactory;
        Map map = tableLogBufferFactory.existingBuffers;
        Object obj = ((LinkedHashMap) map).get(m31m);
        if (obj == null) {
            obj = tableLogBufferFactory.create(100, m31m);
            map.put(m31m, obj);
        }
        CacheContainer cacheContainer2 = new CacheContainer(new DemoMobileConnectionRepository(i, (TableLogBuffer) obj, this.scope), null);
        this.connectionRepoCache.put(Integer.valueOf(i), cacheContainer2);
        return cacheContainer2.repo;
    }
}
