package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileIconsInteractorImpl implements MobileIconsInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeDataConnectionHasDataEnabled;
    public final ReadonlyStateFlow activeDataIconInteractor;
    public final StateFlow activeDataSubId;
    public final MobileIconsInteractorImpl$special$$inlined$map$2 addDefaultSubscriptionInfoIfabsent;
    public final ReadonlyStateFlow alwaysShowDataRatIcon;
    public final ReadonlyStateFlow alwaysUseCdmaLevel;
    public final Handler bgHandler;
    public final CarrierConfigTracker carrierConfigTracker;
    public final CarrierInfraMediator carrierInfraMediator;
    public final Context context;
    public final MobileDataIconResource dataIconResource;
    public final ReadonlyStateFlow defaultMobileIconGroup;
    public final ReadonlyStateFlow defaultMobileIconMapping;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 defaultMobileIconMappingTable;
    public final StateFlow deviceOnTheCall;
    public final MobileDisabledDataIconResource disabledDataIconResource;
    public final ReadonlyStateFlow filteredSubscriptions;
    public final ReadonlyStateFlow forcingCellularValidation;
    public final ReadonlyStateFlow icons;
    public final ReadonlyStateFlow isDefaultConnectionFailed;
    public final MobileIconsInteractorImpl$special$$inlined$map$5 isDeviceInEmergencyCallsOnlyMode;
    public final ReadonlyStateFlow isForceHidden;
    public final ReadonlyStateFlow isSingleCarrier;
    public final MobileConnectionsRepository mobileConnectionsRepo;
    public final ReadonlyStateFlow mobileIsDefault;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final Map reuseCache = new LinkedHashMap();
    public final MobileRoamingIconResource roamingIconResource;
    public final CoroutineScope scope;
    private final SettingsHelper settingsHelper;
    public final MobileSignalIconResource signalIconResource;
    public final StateFlow unfilteredSubscriptions;
    public final UserSetupRepository userSetupRepo;
    public final WifiRepository wifiRepo;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5] */
    public MobileIconsInteractorImpl(MobileConnectionsRepository mobileConnectionsRepository, CarrierConfigTracker carrierConfigTracker, TableLogBuffer tableLogBuffer, ConnectivityRepository connectivityRepository, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, CoroutineScope coroutineScope, Context context, FeatureFlagsClassic featureFlagsClassic, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, MobileMappingsProxy mobileMappingsProxy, Handler handler, CarrierInfraMediator carrierInfraMediator, SubscriptionsOrder subscriptionsOrder, SettingsHelper settingsHelper) {
        this.mobileConnectionsRepo = mobileConnectionsRepository;
        this.carrierConfigTracker = carrierConfigTracker;
        this.userSetupRepo = userSetupRepository;
        this.wifiRepo = wifiRepository;
        this.scope = coroutineScope;
        this.context = context;
        this.dataIconResource = mobileDataIconResource;
        this.signalIconResource = mobileSignalIconResource;
        this.roamingIconResource = mobileRoamingIconResource;
        this.disabledDataIconResource = mobileDisabledDataIconResource;
        this.mobileMappingsProxy = mobileMappingsProxy;
        this.bgHandler = handler;
        this.carrierInfraMediator = carrierInfraMediator;
        this.settingsHelper = settingsHelper;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileConnectionsRepository.getMobileIsDefault(), mobileConnectionsRepository.getHasCarrierMergedConnection(), new MobileIconsInteractorImpl$mobileIsDefault$1(null)), tableLogBuffer, "Intr", "mobileIsDefault", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        this.mobileIsDefault = stateIn;
        this.activeDataConnectionHasDataEnabled = FlowKt.stateIn(FlowKt.transformLatest(mobileConnectionsRepository.getActiveMobileDataRepository(), new MobileIconsInteractorImpl$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activeDataIconInteractor = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getActiveMobileDataSubscriptionId(), new MobileIconsInteractorImpl$activeDataIconInteractor$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final StateFlow subscriptions = mobileConnectionsRepository.getSubscriptions();
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconsInteractorImpl mobileIconsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsInteractorImpl;
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
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L65
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        int r7 = com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl.$r8$clinit
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl r7 = r5.this$0
                        r7.getClass()
                        r7.getClass()
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r6 = r6.iterator()
                    L49:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L5a
                        java.lang.Object r2 = r6.next()
                        r4 = r2
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r4 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r4
                        r7.add(r2)
                        goto L49
                    L5a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L65
                        return r1
                    L65:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconsInteractorImpl mobileIconsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsInteractorImpl;
                }

                /* JADX WARN: Multi-variable type inference failed */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L84
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        boolean r6 = r5.isEmpty()
                        r2 = 0
                        if (r6 != 0) goto L5b
                        int r6 = r5.size()
                        if (r6 != r3) goto L79
                        java.lang.Object r6 = r5.get(r2)
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r6 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel) r6
                        int r6 = r6.subscriptionId
                        int r6 = android.telephony.SubscriptionManager.getSlotIndex(r6)
                        if (r6 != r3) goto L79
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl r6 = r4.this$0
                        com.android.systemui.util.SettingsHelper r6 = r6.getSettingsHelper()
                        boolean r6 = r6.isSimSettingOn(r3)
                        if (r6 != 0) goto L79
                    L5b:
                        boolean r6 = r5.isEmpty()
                        if (r6 == 0) goto L68
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r5 = com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModelKt.DEFAULT_SUBSCRIPTION_MODEL
                        java.util.List r5 = java.util.Collections.singletonList(r5)
                        goto L79
                    L68:
                        r6 = 2
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel[] r6 = new com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel[r6]
                        java.lang.Object r5 = r5.get(r2)
                        r6[r2] = r5
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel r5 = com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModelKt.DEFAULT_SUBSCRIPTION_MODEL
                        r6[r3] = r5
                        java.util.List r5 = kotlin.collections.CollectionsKt__CollectionsKt.listOf(r6)
                    L79:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L84
                        return r1
                    L84:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, mobileConnectionsRepository.getActiveMobileDataSubscriptionId(), connectivityRepositoryImpl.vcnSubId, new MobileIconsInteractorImpl$filteredSubscriptions$1(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged2, tableLogBuffer, "Intr", "filteredSubscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        this.filteredSubscriptions = stateIn2;
        this.icons = FlowKt.stateIn(FlowKt.mapLatest(stateIn2, new MobileIconsInteractorImpl$icons$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        final Flow activeSubChangedInGroupEvent = mobileConnectionsRepository.getActiveSubChangedInGroupEvent();
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconsInteractorImpl mobileIconsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsInteractorImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl r6 = r4.this$0
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository r6 = r6.mobileConnectionsRepo
                        kotlinx.coroutines.flow.StateFlow r6 = r6.getDefaultConnectionIsValidated()
                        java.lang.Object r6 = r6.getValue()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L54
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MobileIconsInteractorImpl$forcingCellularValidation$2(null)), tableLogBuffer, "Intr", "forcingValidation", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.defaultMobileIconMappingTable = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(mobileConnectionsRepository.getDefaultMobileIconMappingTable(), new MobileIconsInteractorImpl$defaultMobileIconMappingTable$1(null));
        this.defaultMobileIconMapping = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconMapping(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MapsKt__MapsKt.emptyMap());
        this.alwaysShowDataRatIcon = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysShowDataRatIcon$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.alwaysUseCdmaLevel = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysUseCdmaLevel$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final StateFlow subscriptions2 = mobileConnectionsRepository.getSubscriptions();
        this.isSingleCarrier = FlowKt.stateIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        int r5 = r5.size()
                        if (r5 != r3) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "Intr", "isSingleCarrier", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.defaultMobileIconGroup = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconGroup(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), TelephonyIcons.G);
        this.isDefaultConnectionFailed = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.combine(stateIn, mobileConnectionsRepository.getDefaultConnectionIsValidated(), stateIn3, new MobileIconsInteractorImpl$isDefaultConnectionFailed$1(null)), tableLogBuffer, "Intr", "isDefaultConnectionFailed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final ReadonlyStateFlow readonlyStateFlow = connectivityRepositoryImpl.forceHiddenSlots;
        this.isForceHidden = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2$1
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
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot r6 = com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot.MOBILE
                        boolean r5 = r5.contains(r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final StateFlow deviceServiceState = mobileConnectionsRepository.getDeviceServiceState();
        this.isDeviceInEmergencyCallsOnlyMode = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5$2$1
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
                        com.android.systemui.statusbar.pipeline.mobile.data.model.ServiceStateModel r5 = (com.android.systemui.statusbar.pipeline.mobile.data.model.ServiceStateModel) r5
                        if (r5 == 0) goto L39
                        boolean r5 = r5.isEmergencyOnly
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.activeDataSubId = mobileConnectionsRepository.getActiveMobileDataSubscriptionId();
        this.deviceOnTheCall = mobileConnectionsRepository.getDeviceOnTheCall();
    }

    public final MobileIconInteractorImpl createMobileConnectionInteractorForSubId(int i) {
        MobileConnectionsRepository mobileConnectionsRepository = this.mobileConnectionsRepo;
        MobileIconInteractorImpl mobileIconInteractorImpl = new MobileIconInteractorImpl(this.scope, this.activeDataConnectionHasDataEnabled, this.alwaysShowDataRatIcon, this.alwaysUseCdmaLevel, this.isSingleCarrier, this.mobileIsDefault, this.activeDataSubId, this.defaultMobileIconMappingTable, this.defaultMobileIconMapping, this.defaultMobileIconGroup, this.isDefaultConnectionFailed, this.isForceHidden, mobileConnectionsRepository.getRepoForSubId(i), this.context, this.userSetupRepo, this.wifiRepo, this.dataIconResource, this.signalIconResource, this.roamingIconResource, this.disabledDataIconResource, this.carrierInfraMediator, this.mobileMappingsProxy, this.deviceOnTheCall, mobileConnectionsRepository.bootstrapProfile(i), this.bgHandler, null, QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING, null);
        this.reuseCache.put(Integer.valueOf(i), new WeakReference(mobileIconInteractorImpl));
        return mobileIconInteractorImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0020, code lost:
    
        if (((com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl) r0).slotId == android.telephony.SubscriptionManager.getSlotIndex(r3)) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor getMobileConnectionInteractorForSubId(int r3) {
        /*
            r2 = this;
            java.util.Map r0 = r2.reuseCache
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            java.util.LinkedHashMap r0 = (java.util.LinkedHashMap) r0
            java.lang.Object r0 = r0.get(r1)
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
            if (r0 == 0) goto L23
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor) r0
            if (r0 == 0) goto L23
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl) r0
            int r1 = android.telephony.SubscriptionManager.getSlotIndex(r3)
            int r0 = r0.slotId
            if (r0 != r1) goto L23
            goto L2d
        L23:
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r3 == r0) goto L2d
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r2 = r2.createMobileConnectionInteractorForSubId(r3)
            goto L4c
        L2d:
            java.util.Map r0 = r2.reuseCache
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            java.util.LinkedHashMap r0 = (java.util.LinkedHashMap) r0
            java.lang.Object r0 = r0.get(r1)
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
            if (r0 == 0) goto L48
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor) r0
            if (r0 != 0) goto L46
            goto L48
        L46:
            r2 = r0
            goto L4c
        L48:
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r2 = r2.createMobileConnectionInteractorForSubId(r3)
        L4c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl.getMobileConnectionInteractorForSubId(int):com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor");
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
