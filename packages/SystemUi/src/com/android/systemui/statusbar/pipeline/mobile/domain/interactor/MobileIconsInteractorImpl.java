package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileIconsInteractorImpl implements MobileIconsInteractor {
    public final ReadonlyStateFlow activeDataConnectionHasDataEnabled;
    public final StateFlow activeDataSubId;
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
    public final ReadonlyStateFlow isDefaultConnectionFailed;
    public final ReadonlyStateFlow isForceHidden;
    public final MobileConnectionsRepository mobileConnectionsRepo;
    public final ReadonlyStateFlow mobileIsDefault;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final Map reuseCache = new LinkedHashMap();
    public final MobileRoamingIconResource roamingIconResource;
    public final CoroutineScope scope;
    public final SettingsHelper settingsHelper;
    public final MobileSignalIconResource signalIconResource;
    public final StateFlow unfilteredSubscriptions;
    public final UserSetupRepository userSetupRepo;
    public final WifiRepository wifiRepo;

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

    public MobileIconsInteractorImpl(MobileConnectionsRepository mobileConnectionsRepository, CarrierConfigTracker carrierConfigTracker, TableLogBuffer tableLogBuffer, ConnectivityRepository connectivityRepository, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, CoroutineScope coroutineScope, Context context, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, MobileMappingsProxy mobileMappingsProxy, SettingsHelper settingsHelper, Handler handler, CarrierInfraMediator carrierInfraMediator, SubscriptionsOrder subscriptionsOrder) {
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
        this.settingsHelper = settingsHelper;
        this.bgHandler = handler;
        this.carrierInfraMediator = carrierInfraMediator;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileConnectionsRepository.getMobileIsDefault(), mobileConnectionsRepository.getHasCarrierMergedConnection(), new MobileIconsInteractorImpl$mobileIsDefault$1(null)), tableLogBuffer, "Intr", "mobileIsDefault", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        this.mobileIsDefault = stateIn;
        this.activeDataConnectionHasDataEnabled = FlowKt.stateIn(FlowKt.transformLatest(mobileConnectionsRepository.getActiveMobileDataRepository(), new MobileIconsInteractorImpl$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        StateFlow subscriptions = mobileConnectionsRepository.getSubscriptions();
        this.unfilteredSubscriptions = subscriptions;
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(subscriptions, mobileConnectionsRepository.getActiveMobileDataSubscriptionId(), mobileConnectionsRepository.getDefaultDataSubId(), connectivityRepositoryImpl.vcnSubId, new MobileIconsInteractorImpl$filteredSubscriptions$1(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged, tableLogBuffer, "Intr", "filteredSubscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), emptyList);
        this.filteredSubscriptions = stateIn2;
        FlowKt.stateIn(FlowKt.mapLatest(stateIn2, new MobileIconsInteractorImpl$icons$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), emptyList);
        final Flow activeSubChangedInGroupEvent = mobileConnectionsRepository.getActiveSubChangedInGroupEvent();
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2 */
            public final class C33222 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2", m277f = "MobileIconsInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33222.this.emit(null, this);
                    }
                }

                public C33222(FlowCollector flowCollector, MobileIconsInteractorImpl mobileIconsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsInteractorImpl;
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
                                if (((Boolean) this.this$0.mobileConnectionsRepo.getDefaultConnectionIsValidated().getValue()).booleanValue()) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C33222(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MobileIconsInteractorImpl$forcingCellularValidation$2(null)), tableLogBuffer, "Intr", "forcingValidation", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.forcingCellularValidation = stateIn3;
        this.defaultMobileIconMappingTable = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(mobileConnectionsRepository.getDefaultMobileIconMappingTable(), new MobileIconsInteractorImpl$defaultMobileIconMappingTable$1(null));
        this.defaultMobileIconMapping = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconMapping(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MapsKt__MapsKt.emptyMap());
        this.alwaysShowDataRatIcon = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysShowDataRatIcon$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.alwaysUseCdmaLevel = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysUseCdmaLevel$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.defaultMobileIconGroup = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconGroup(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), TelephonyIcons.f221G);
        this.isDefaultConnectionFailed = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.combine(stateIn, mobileConnectionsRepository.getDefaultConnectionIsValidated(), stateIn3, new MobileIconsInteractorImpl$isDefaultConnectionFailed$1(null)), tableLogBuffer, "Intr", "isDefaultConnectionFailed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        final ReadonlyStateFlow readonlyStateFlow = connectivityRepositoryImpl.forceHiddenSlots;
        this.isForceHidden = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2 */
            public final class C33232 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2", m277f = "MobileIconsInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33232.this.emit(null, this);
                    }
                }

                public C33232(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((Set) obj).contains(ConnectivitySlot.MOBILE));
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
                Object collect = Flow.this.collect(new C33232(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.activeDataSubId = mobileConnectionsRepository.getActiveMobileDataSubscriptionId();
        this.deviceOnTheCall = mobileConnectionsRepository.getDeviceOnTheCall();
    }

    public final MobileIconInteractorImpl getMobileConnectionInteractorForSubId(int i) {
        CoroutineScope coroutineScope = this.scope;
        ReadonlyStateFlow readonlyStateFlow = this.activeDataConnectionHasDataEnabled;
        ReadonlyStateFlow readonlyStateFlow2 = this.alwaysShowDataRatIcon;
        ReadonlyStateFlow readonlyStateFlow3 = this.alwaysUseCdmaLevel;
        ReadonlyStateFlow readonlyStateFlow4 = this.mobileIsDefault;
        StateFlow stateFlow = this.activeDataSubId;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = this.defaultMobileIconMappingTable;
        ReadonlyStateFlow readonlyStateFlow5 = this.defaultMobileIconMapping;
        ReadonlyStateFlow readonlyStateFlow6 = this.defaultMobileIconGroup;
        ReadonlyStateFlow readonlyStateFlow7 = this.isDefaultConnectionFailed;
        ReadonlyStateFlow readonlyStateFlow8 = this.isForceHidden;
        MobileConnectionsRepository mobileConnectionsRepository = this.mobileConnectionsRepo;
        MobileIconInteractorImpl mobileIconInteractorImpl = new MobileIconInteractorImpl(coroutineScope, readonlyStateFlow, readonlyStateFlow2, readonlyStateFlow3, readonlyStateFlow4, stateFlow, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, readonlyStateFlow5, readonlyStateFlow6, readonlyStateFlow7, readonlyStateFlow8, mobileConnectionsRepository.getRepoForSubId(i), this.context, this.userSetupRepo, this.wifiRepo, this.dataIconResource, this.signalIconResource, this.roamingIconResource, this.disabledDataIconResource, this.carrierInfraMediator, this.mobileMappingsProxy, this.settingsHelper, this.deviceOnTheCall, mobileConnectionsRepository.bootstrapProfile(i), this.bgHandler, null, QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING, null);
        this.reuseCache.put(Integer.valueOf(i), new WeakReference(mobileIconInteractorImpl));
        return mobileIconInteractorImpl;
    }
}
