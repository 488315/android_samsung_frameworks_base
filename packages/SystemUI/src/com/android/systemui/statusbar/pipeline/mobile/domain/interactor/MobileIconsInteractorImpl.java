package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModelKt;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class MobileIconsInteractorImpl implements MobileIconsInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeDataConnectionHasDataEnabled;
    public final ReadonlyStateFlow activeDataIconInteractor;
    public final StateFlow activeDataSubId;
    public final StateFlow activeMobileDataSubscriptionId;
    public final MobileIconsInteractorImpl$special$$inlined$map$2 addDefaultSubscriptionInfoIfabsent;
    public final ReadonlyStateFlow alwaysShowDataRatIcon;
    public final ReadonlyStateFlow alwaysUseCdmaLevel;
    public final Handler bgHandler;
    public final CarrierConfigTracker carrierConfigTracker;
    public final CarrierInfraMediator carrierInfraMediator;
    public final Context context;
    public final MobileDataIconResource dataIconResource;
    public final StateFlow defaultDataSubId;
    public final ReadonlyStateFlow defaultMobileIconGroup;
    public final ReadonlyStateFlow defaultMobileIconMapping;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 defaultMobileIconMappingTable;
    public final StateFlow deviceOnTheCall;
    public final MobileDisabledDataIconResource disabledDataIconResource;
    public final ReadonlyStateFlow filteredSubscriptions;
    public final ReadonlyStateFlow forcingCellularValidation;
    public final ReadonlyStateFlow icons;
    public final ReadonlyStateFlow isDefaultConnectionFailed;
    public final StateFlow isDeviceInEmergencyCallsOnlyMode;
    public final ReadonlyStateFlow isForceHidden;
    public final ReadonlyStateFlow isSingleCarrier;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isStackable;
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

    /* JADX WARN: Type inference failed for: r13v5, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
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
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileConnectionsRepository.getMobileIsDefault(), mobileConnectionsRepository.getHasCarrierMergedConnection(), new MobileIconsInteractorImpl$mobileIsDefault$1(null)), tableLogBuffer, "Intr", "mobileIsDefault", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.mobileIsDefault = readonlyStateFlowStateIn;
        this.activeMobileDataSubscriptionId = mobileConnectionsRepository.getActiveMobileDataSubscriptionId();
        this.activeDataConnectionHasDataEnabled = FlowKt.stateIn(FlowKt.transformLatest(mobileConnectionsRepository.getActiveMobileDataRepository(), new MobileIconsInteractorImpl$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activeDataIconInteractor = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getActiveMobileDataSubscriptionId(), new MobileIconsInteractorImpl$activeDataIconInteractor$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final StateFlow subscriptions = mobileConnectionsRepository.getSubscriptions();
        this.unfilteredSubscriptions = subscriptions;
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

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
                        int i3 = MobileIconsInteractorImpl.$r8$clinit;
                        MobileIconsInteractorImpl mobileIconsInteractorImpl = this.this$0;
                        mobileIconsInteractorImpl.getClass();
                        mobileIconsInteractorImpl.getClass();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (List) obj) {
                            arrayList.add(obj3);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = subscriptions.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        ?? r13 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

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
                        List listSingletonList = (List) obj;
                        if (listSingletonList.isEmpty() || (listSingletonList.size() == 1 && SubscriptionManager.getSlotIndex(((SubscriptionModel) listSingletonList.get(0)).subscriptionId) == 1 && !this.this$0.getSettingsHelper().isSimSettingOn(1))) {
                            listSingletonList = listSingletonList.isEmpty() ? Collections.singletonList(SubscriptionModelKt.DEFAULT_SUBSCRIPTION_MODEL) : Arrays.asList(listSingletonList.get(0), SubscriptionModelKt.DEFAULT_SUBSCRIPTION_MODEL);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(listSingletonList, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.addDefaultSubscriptionInfoIfabsent = r13;
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(r13, mobileConnectionsRepository.getActiveMobileDataSubscriptionId(), connectivityRepositoryImpl.vcnSubId, new MobileIconsInteractorImpl$filteredSubscriptions$1(this, null)));
        EmptyList emptyList = EmptyList.INSTANCE;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(flowDistinctUntilChanged2, tableLogBuffer, "Intr", "filteredSubscriptions", emptyList), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        this.filteredSubscriptions = readonlyStateFlowStateIn2;
        this.defaultDataSubId = mobileConnectionsRepository.getDefaultDataSubId();
        this.icons = FlowKt.stateIn(FlowKt.mapLatest(readonlyStateFlowStateIn2, new MobileIconsInteractorImpl$icons$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        this.isStackable = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        final Flow activeSubChangedInGroupEvent = mobileConnectionsRepository.getActiveSubChangedInGroupEvent();
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsInteractorImpl this$0;

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
                        if (((Boolean) this.this$0.mobileConnectionsRepo.getDefaultConnectionIsValidated().getValue()).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = activeSubChangedInGroupEvent.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new MobileIconsInteractorImpl$forcingCellularValidation$2(null)), tableLogBuffer, "Intr", "forcingValidation", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.forcingCellularValidation = readonlyStateFlowStateIn3;
        this.defaultMobileIconMappingTable = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(mobileConnectionsRepository.getDefaultMobileIconMappingTable(), new MobileIconsInteractorImpl$defaultMobileIconMappingTable$1(null));
        this.defaultMobileIconMapping = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconMapping(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MapsKt__MapsKt.emptyMap());
        this.alwaysShowDataRatIcon = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysShowDataRatIcon$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.alwaysUseCdmaLevel = FlowKt.stateIn(FlowKt.mapLatest(mobileConnectionsRepository.getDefaultDataSubRatConfig(), new MobileIconsInteractorImpl$alwaysUseCdmaLevel$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final StateFlow subscriptions2 = mobileConnectionsRepository.getSubscriptions();
        this.isSingleCarrier = FlowKt.stateIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        Boolean boolValueOf = Boolean.valueOf(((List) obj).size() == 1);
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
                Object objCollect = subscriptions2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "Intr", "isSingleCarrier", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.defaultMobileIconGroup = FlowKt.stateIn(mobileConnectionsRepository.getDefaultMobileIconGroup(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), TelephonyIcons.G);
        this.isDefaultConnectionFailed = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.combine(readonlyStateFlowStateIn, mobileConnectionsRepository.getDefaultConnectionIsValidated(), readonlyStateFlowStateIn3, new MobileIconsInteractorImpl$isDefaultConnectionFailed$1(null)), tableLogBuffer, "Intr", "isDefaultConnectionFailed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        userSetupRepository.getClass();
        final ReadonlyStateFlow readonlyStateFlow = connectivityRepositoryImpl.forceHiddenSlots;
        this.isForceHidden = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        Boolean boolValueOf = Boolean.valueOf(((Set) obj).contains(ConnectivitySlot.MOBILE));
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
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isDeviceInEmergencyCallsOnlyMode = mobileConnectionsRepository.isDeviceEmergencyCallCapable();
        this.activeDataSubId = mobileConnectionsRepository.getActiveMobileDataSubscriptionId();
        this.deviceOnTheCall = mobileConnectionsRepository.getDeviceOnTheCall();
    }

    public final MobileIconInteractorImpl createMobileConnectionInteractorForSubId(int i) {
        MobileConnectionsRepository mobileConnectionsRepository = this.mobileConnectionsRepo;
        MobileIconInteractorImpl mobileIconInteractorImpl = new MobileIconInteractorImpl(this.scope, this.activeDataConnectionHasDataEnabled, this.alwaysShowDataRatIcon, this.alwaysUseCdmaLevel, this.isSingleCarrier, this.mobileIsDefault, this.activeDataSubId, this.defaultMobileIconMappingTable, this.defaultMobileIconMapping, this.defaultMobileIconGroup, this.isDefaultConnectionFailed, this.isForceHidden, mobileConnectionsRepository.getRepoForSubId(i), this.context, this.userSetupRepo, this.wifiRepo, this.dataIconResource, this.signalIconResource, this.roamingIconResource, this.disabledDataIconResource, this.carrierInfraMediator, this.mobileMappingsProxy, this.deviceOnTheCall, mobileConnectionsRepository.bootstrapProfile(i), this.bgHandler, null, 33554432, null);
        this.reuseCache.put(Integer.valueOf(i), new WeakReference(mobileIconInteractorImpl));
        return mobileIconInteractorImpl;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final ReadonlyStateFlow getActiveDataIconInteractor() {
        return this.activeDataIconInteractor;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getActiveMobileDataSubscriptionId() {
        return this.activeMobileDataSubscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getDefaultDataSubId$1() {
        return this.defaultDataSubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final Flow getFilteredSubscriptions() {
        return this.filteredSubscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getIcons() {
        return this.icons;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final MobileIconInteractor getMobileConnectionInteractorForSubId(int i) {
        MobileIconInteractor mobileIconInteractor;
        MobileIconInteractor mobileIconInteractor2;
        WeakReference weakReference = (WeakReference) ((LinkedHashMap) this.reuseCache).get(Integer.valueOf(i));
        if ((weakReference == null || (mobileIconInteractor2 = (MobileIconInteractor) weakReference.get()) == null || mobileIconInteractor2.getSlotId() != SubscriptionManager.getSlotIndex(i)) && i != Integer.MAX_VALUE) {
            return createMobileConnectionInteractorForSubId(i);
        }
        WeakReference weakReference2 = (WeakReference) ((LinkedHashMap) this.reuseCache).get(Integer.valueOf(i));
        return (weakReference2 == null || (mobileIconInteractor = (MobileIconInteractor) weakReference2.get()) == null) ? createMobileConnectionInteractorForSubId(i) : mobileIconInteractor;
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow isDeviceInEmergencyCallsOnlyMode() {
        return this.isDeviceInEmergencyCallsOnlyMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow isSingleCarrier() {
        return this.isSingleCarrier;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final Flow isStackable() {
        return this.isStackable;
    }
}
