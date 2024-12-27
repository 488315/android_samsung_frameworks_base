package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DesktopManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileIconsViewModel {
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final ConfigurationController configuration;
    public final ConnectivityConstants constants;
    public final DesktopManager desktopManager;
    public final ReadonlyStateFlow firstMobileSubShowingNetworkTypeIcon;
    public final ReadonlyStateFlow firstMobileSubViewModel;
    public final FeatureFlagsClassic flags;
    public final MobileIconsInteractor interactor;
    public final MobileViewLogger logger;
    public final Map reuseCache = new LinkedHashMap();
    public final CoroutineScope scope;
    public final ReadonlyStateFlow subscriptionIdsFlow;
    public final VerboseMobileViewLogger verboseLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MobileIconsViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final MobileIconsViewModel mobileIconsViewModel = MobileIconsViewModel.this;
                ReadonlyStateFlow readonlyStateFlow = mobileIconsViewModel.subscriptionIdsFlow;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        CoroutineScope coroutineScope;
                        List list = (List) obj2;
                        MobileIconsViewModel mobileIconsViewModel2 = MobileIconsViewModel.this;
                        Set keySet = ((LinkedHashMap) mobileIconsViewModel2.reuseCache).keySet();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : keySet) {
                            if (!list.contains(Integer.valueOf(((Number) obj3).intValue()))) {
                                arrayList.add(obj3);
                            }
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            Pair pair = (Pair) mobileIconsViewModel2.reuseCache.remove(Integer.valueOf(((Number) it.next()).intValue()));
                            if (pair != null && (coroutineScope = (CoroutineScope) pair.getSecond()) != null) {
                                CoroutineScopeKt.cancel(coroutineScope, null);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public MobileIconsViewModel(MobileViewLogger mobileViewLogger, VerboseMobileViewLogger verboseMobileViewLogger, MobileIconsInteractor mobileIconsInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, FeatureFlagsClassic featureFlagsClassic, CoroutineScope coroutineScope, DesktopManager desktopManager, ConfigurationController configurationController) {
        this.logger = mobileViewLogger;
        this.verboseLogger = verboseMobileViewLogger;
        this.interactor = mobileIconsInteractor;
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.constants = connectivityConstants;
        this.flags = featureFlagsClassic;
        this.scope = coroutineScope;
        this.desktopManager = desktopManager;
        this.configuration = configurationController;
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(((MobileIconsInteractorImpl) mobileIconsInteractor).filteredSubscriptions, new MobileIconsViewModel$subscriptionIdsFlow$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(mapLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptyList.INSTANCE);
        this.subscriptionIdsFlow = stateIn;
        this.firstMobileSubShowingNetworkTypeIcon = FlowKt.stateIn(FlowKt.transformLatest(FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconsViewModel mobileIconsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        boolean r6 = r5.isEmpty()
                        if (r6 == 0) goto L3c
                        r5 = 0
                        goto L4e
                    L3c:
                        java.lang.Object r5 = kotlin.collections.CollectionsKt___CollectionsKt.last(r5)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel r6 = r4.this$0
                        java.lang.String r2 = ""
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon r5 = r6.commonViewModelForSub(r5, r2)
                    L4e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null), new MobileIconsViewModel$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    public final MobileIconViewModelCommon commonViewModelForSub(int i, String str) {
        Map map = this.reuseCache;
        Integer valueOf = Integer.valueOf(i);
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object obj = linkedHashMap.get(valueOf);
        if (obj == null) {
            CoroutineScope coroutineScope = this.scope;
            ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineScope.getCoroutineContext().plus(new JobImpl((Job) coroutineScope.getCoroutineContext().get(Job.Key))));
            Pair pair = new Pair(new MobileIconViewModel(i, ((MobileIconsInteractorImpl) this.interactor).getMobileConnectionInteractorForSubId(i), this.airplaneModeInteractor, this.constants, this.flags, CoroutineScope, this.desktopManager, str), CoroutineScope);
            linkedHashMap.put(valueOf, pair);
            obj = pair;
        }
        return (MobileIconViewModelCommon) ((Pair) obj).getFirst();
    }

    public final LocationBasedMobileViewModel viewModelForSub(int i, StatusBarLocation statusBarLocation, String str) {
        MobileIconViewModelCommon commonViewModelForSub = commonViewModelForSub(i, str);
        LocationBasedMobileViewModel.Companion companion = LocationBasedMobileViewModel.Companion;
        MobileIconInteractor mobileConnectionInteractorForSubId = ((MobileIconsInteractorImpl) this.interactor).getMobileConnectionInteractorForSubId(i);
        companion.getClass();
        switch (LocationBasedMobileViewModel.Companion.WhenMappings.$EnumSwitchMapping$0[statusBarLocation.ordinal()]) {
            case 1:
                return new HomeMobileIconViewModel(commonViewModelForSub, this.verboseLogger);
            case 2:
                return new KeyguardMobileIconViewModel(commonViewModelForSub);
            case 3:
                return new QsMobileIconViewModel(commonViewModelForSub);
            case 4:
                return new SubScreenQsMobileIconViewModel(commonViewModelForSub);
            case 5:
                return new ShadeCarrierGroupMobileIconViewModel(commonViewModelForSub, mobileConnectionInteractorForSubId, this.scope);
            case 6:
                throw new IllegalArgumentException("invalid location for MobileViewModel: " + statusBarLocation);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static /* synthetic */ void getReuseCache$annotations() {
    }
}
