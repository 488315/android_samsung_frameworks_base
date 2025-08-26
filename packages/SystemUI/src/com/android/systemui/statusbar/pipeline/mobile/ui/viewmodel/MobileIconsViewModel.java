package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes3.dex */
public final class MobileIconsViewModel {
    public final StateFlow activeMobileDataSubscriptionId;
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final ConfigurationController configuration;
    public final ConnectivityConstants constants;
    public final ReadonlyStateFlow firstMobileSubShowingNetworkTypeIcon;
    public final ReadonlyStateFlow firstMobileSubViewModel;
    public final ChannelFlowTransformLatest iconsAreAllVisible;
    public final MobileIconsInteractor interactor;
    public final ReadonlyStateFlow isStackable;
    public final MobileViewLogger logger;
    public final ReadonlyStateFlow mobileSubViewModels;
    public final ConcurrentHashMap reuseCache = new ConcurrentHashMap();
    public final CoroutineScope scope;
    public final ReadonlyStateFlow subscriptionIdsFlow;
    public final TaskbarIndicatorController taskbarIndicatorController;
    public final VerboseMobileViewLogger verboseLogger;

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
                        MobileIconsViewModel mobileIconsViewModel2 = mobileIconsViewModel;
                        Set setKeySet = mobileIconsViewModel2.reuseCache.keySet();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : setKeySet) {
                            if (!list.contains((Integer) obj3)) {
                                arrayList.add(obj3);
                            }
                        }
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj4 = arrayList.get(i2);
                            i2++;
                            Pair pair = (Pair) mobileIconsViewModel2.reuseCache.remove((Integer) obj4);
                            if (pair != null && (coroutineScope = (CoroutineScope) pair.getSecond()) != null) {
                                CoroutineScopeKt.cancel(coroutineScope, null);
                            }
                        }
                        Log.d("MobileIconsViewModel", "invalidateCaches: cancel vmScope excepts subIds[" + list + "]");
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

    public MobileIconsViewModel(MobileViewLogger mobileViewLogger, VerboseMobileViewLogger verboseMobileViewLogger, MobileIconsInteractor mobileIconsInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, CoroutineScope coroutineScope, TaskbarIndicatorController taskbarIndicatorController, ConfigurationController configurationController, TableLogBuffer tableLogBuffer, CoroutineDispatcher coroutineDispatcher) {
        this.logger = mobileViewLogger;
        this.verboseLogger = verboseMobileViewLogger;
        this.interactor = mobileIconsInteractor;
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.constants = connectivityConstants;
        this.scope = coroutineScope;
        this.taskbarIndicatorController = taskbarIndicatorController;
        this.configuration = configurationController;
        this.activeMobileDataSubscriptionId = mobileIconsInteractor.getActiveMobileDataSubscriptionId();
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(mobileIconsInteractor.getFilteredSubscriptions(), new MobileIconsViewModel$subscriptionIdsFlow$1(null));
        EmptyList emptyList = EmptyList.INSTANCE;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(channelFlowTransformLatestMapLatest, tableLogBuffer, "vm", "subscriptionIdsFlow", emptyList);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        this.subscriptionIdsFlow = readonlyStateFlowStateIn;
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsViewModel this$0;

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
                        List list = (List) obj;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(this.this$0.commonViewModelForSub(((Number) it.next()).intValue(), ""));
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        this.mobileSubViewModels = readonlyStateFlowStateIn2;
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    MobileIconViewModelCommon mobileIconViewModelCommonCommonViewModelForSub;
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
                        List list = (List) obj;
                        if (list.isEmpty()) {
                            mobileIconViewModelCommonCommonViewModelForSub = null;
                        } else {
                            mobileIconViewModelCommonCommonViewModelForSub = this.this$0.commonViewModelForSub(((MobileIconViewModelCommon) CollectionsKt___CollectionsKt.last(list)).getSubscriptionId(), "");
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mobileIconViewModelCommonCommonViewModelForSub, anonymousClass1) == coroutineSingletons) {
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
        this.firstMobileSubViewModel = readonlyStateFlowStateIn3;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(readonlyStateFlowStateIn3, new MobileIconsViewModel$special$$inlined$flatMapLatest$1(null));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.firstMobileSubShowingNetworkTypeIcon = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(readonlyStateFlowStateIn2, new MobileIconsViewModel$special$$inlined$flatMapLatest$2(null));
        this.iconsAreAllVisible = channelFlowTransformLatestTransformLatest2;
        this.isStackable = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestTransformLatest2, mobileIconsInteractor.isStackable(), new MobileIconsViewModel$isStackable$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        CoroutineTracingKt.launchTraced$default(coroutineScope, coroutineDispatcher, null, new AnonymousClass1(null), 5);
    }

    public final MobileIconViewModelCommon commonViewModelForSub(int i, String str) {
        ConcurrentHashMap concurrentHashMap = this.reuseCache;
        Integer numValueOf = Integer.valueOf(i);
        Object obj = concurrentHashMap.get(numValueOf);
        if (obj == null) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "createViewModel - subId: ", "MobileIconsViewModel");
            TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            CoroutineScope coroutineScope = this.scope;
            ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineScope.getCoroutineContext().plus(new JobImpl((Job) coroutineScope.getCoroutineContext().get(Job.Key))).plus(emptyCoroutineContext));
            Pair pair = new Pair(new MobileIconViewModel(i, this.interactor.getMobileConnectionInteractorForSubId(i), this.airplaneModeInteractor, this.constants, contextScopeCoroutineScope, this.taskbarIndicatorController, str), contextScopeCoroutineScope);
            Object objPutIfAbsent = concurrentHashMap.putIfAbsent(numValueOf, pair);
            obj = objPutIfAbsent == null ? pair : objPutIfAbsent;
        }
        return (MobileIconViewModelCommon) ((Pair) obj).getFirst();
    }

    public final LocationBasedMobileViewModel viewModelForSub(int i, StatusBarLocation statusBarLocation, String str) {
        MobileIconViewModelCommon mobileIconViewModelCommonCommonViewModelForSub = commonViewModelForSub(i, str);
        LocationBasedMobileViewModel.Companion companion = LocationBasedMobileViewModel.Companion;
        MobileIconInteractor mobileConnectionInteractorForSubId = this.interactor.getMobileConnectionInteractorForSubId(i);
        companion.getClass();
        switch (LocationBasedMobileViewModel.Companion.WhenMappings.$EnumSwitchMapping$0[statusBarLocation.ordinal()]) {
            case 1:
                return new HomeMobileIconViewModel(mobileIconViewModelCommonCommonViewModelForSub, this.verboseLogger);
            case 2:
                return new KeyguardMobileIconViewModel(mobileIconViewModelCommonCommonViewModelForSub);
            case 3:
                return new QsMobileIconViewModel(mobileIconViewModelCommonCommonViewModelForSub);
            case 4:
                return new SubScreenQsMobileIconViewModel(mobileIconViewModelCommonCommonViewModelForSub);
            case 5:
                return new ShadeCarrierGroupMobileIconViewModel(mobileIconViewModelCommonCommonViewModelForSub, mobileConnectionInteractorForSubId, this.scope);
            case 6:
                throw new IllegalArgumentException("invalid location for MobileViewModel: " + statusBarLocation);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static /* synthetic */ void getReuseCache$annotations() {
    }
}
