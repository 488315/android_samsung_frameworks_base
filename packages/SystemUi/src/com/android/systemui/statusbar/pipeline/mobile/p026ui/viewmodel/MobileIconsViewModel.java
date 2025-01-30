package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileIconsViewModel {
    public final AirplaneModeInteractor airplaneModeInteractor;
    public final ConfigurationController configuration;
    public final ConnectivityConstants constants;
    public final DesktopManager desktopManager;
    public final ReadonlyStateFlow firstMobileSubShowingNetworkTypeIcon;
    public final ReadonlyStateFlow firstMobileSubViewModel;
    public final MobileIconsInteractor interactor;
    public final MobileViewLogger logger;
    public final Map mobileIconSubIdCache = new LinkedHashMap();
    public final CoroutineScope scope;
    public final StatusBarPipelineFlags statusBarPipelineFlags;
    public final ReadonlyStateFlow subscriptionIdsFlow;
    public final VerboseMobileViewLogger verboseLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$1", m277f = "MobileIconsViewModel.kt", m278l = {104}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$1 */
    public static final class C33371 extends SuspendLambda implements Function2 {
        int label;

        public C33371(Continuation<? super C33371> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MobileIconsViewModel.this.new C33371(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33371) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    /* JADX WARN: Code restructure failed: missing block: B:9:0x004e, code lost:
                    
                        if (((com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModel) r4).slotId != android.telephony.SubscriptionManager.getSlotIndex(r3)) goto L13;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:12:0x0056 A[SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:16:0x0016 A[SYNTHETIC] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean z;
                        List list = (List) obj2;
                        Map map = MobileIconsViewModel.this.mobileIconSubIdCache;
                        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                        Set keySet = linkedHashMap.keySet();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : keySet) {
                            int intValue = ((Number) obj3).intValue();
                            if (list.contains(Integer.valueOf(intValue))) {
                                if (linkedHashMap.get(Integer.valueOf(intValue)) != null) {
                                    Object obj4 = linkedHashMap.get(Integer.valueOf(intValue));
                                    Intrinsics.checkNotNull(obj4);
                                }
                                z = false;
                                if (!z) {
                                    arrayList.add(obj3);
                                }
                            }
                            z = true;
                            if (!z) {
                            }
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            map.remove(Integer.valueOf(((Number) it.next()).intValue()));
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

    public MobileIconsViewModel(MobileViewLogger mobileViewLogger, VerboseMobileViewLogger verboseMobileViewLogger, MobileIconsInteractor mobileIconsInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, CoroutineScope coroutineScope, StatusBarPipelineFlags statusBarPipelineFlags, MobileSignalIconResource mobileSignalIconResource, DesktopManager desktopManager, ConfigurationController configurationController) {
        this.logger = mobileViewLogger;
        this.verboseLogger = verboseMobileViewLogger;
        this.interactor = mobileIconsInteractor;
        this.airplaneModeInteractor = airplaneModeInteractor;
        this.constants = connectivityConstants;
        this.scope = coroutineScope;
        this.statusBarPipelineFlags = statusBarPipelineFlags;
        this.desktopManager = desktopManager;
        this.configuration = configurationController;
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(((MobileIconsInteractorImpl) mobileIconsInteractor).filteredSubscriptions, new MobileIconsViewModel$subscriptionIdsFlow$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(mapLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), EmptyList.INSTANCE);
        this.subscriptionIdsFlow = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2 */
            public final class C33362 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconsViewModel this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$special$$inlined$map$1$2", m277f = "MobileIconsViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33362.this.emit(null, this);
                    }
                }

                public C33362(FlowCollector flowCollector, MobileIconsViewModel mobileIconsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconsViewModel;
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
                    MobileIconViewModel commonViewModelForSub;
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
                                List list = (List) obj;
                                if (list.isEmpty()) {
                                    commonViewModelForSub = null;
                                } else {
                                    commonViewModelForSub = this.this$0.commonViewModelForSub(((Number) CollectionsKt___CollectionsKt.last(list)).intValue(), "");
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(commonViewModelForSub, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C33362(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.firstMobileSubViewModel = stateIn2;
        this.firstMobileSubShowingNetworkTypeIcon = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileIconsViewModel$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.FALSE);
        BuildersKt.launch$default(coroutineScope, null, null, new C33371(null), 3);
    }

    public final MobileIconViewModel commonViewModelForSub(int i, String str) {
        Map map = this.mobileIconSubIdCache;
        MobileIconViewModel mobileIconViewModel = (MobileIconViewModel) ((LinkedHashMap) map).get(Integer.valueOf(i));
        if (mobileIconViewModel != null) {
            return mobileIconViewModel;
        }
        MobileIconViewModel mobileIconViewModel2 = new MobileIconViewModel(i, ((MobileIconsInteractorImpl) this.interactor).getMobileConnectionInteractorForSubId(i), this.airplaneModeInteractor, this.constants, this.scope, this.desktopManager, str);
        map.put(Integer.valueOf(i), mobileIconViewModel2);
        return mobileIconViewModel2;
    }

    public final LocationBasedMobileViewModel viewModelForSub(int i, StatusBarLocation statusBarLocation, String str) {
        MobileIconViewModel commonViewModelForSub = commonViewModelForSub(i, str);
        LocationBasedMobileViewModel.Companion.getClass();
        int i2 = LocationBasedMobileViewModel.Companion.WhenMappings.$EnumSwitchMapping$0[statusBarLocation.ordinal()];
        StatusBarPipelineFlags statusBarPipelineFlags = this.statusBarPipelineFlags;
        if (i2 == 1) {
            return new HomeMobileIconViewModel(commonViewModelForSub, statusBarPipelineFlags, this.verboseLogger);
        }
        if (i2 == 2) {
            return new KeyguardMobileIconViewModel(commonViewModelForSub, statusBarPipelineFlags);
        }
        if (i2 == 3) {
            return new QsMobileIconViewModel(commonViewModelForSub, statusBarPipelineFlags);
        }
        if (i2 == 4) {
            return new SubScreenQsMobileIconViewModel(commonViewModelForSub, statusBarPipelineFlags);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ void getMobileIconSubIdCache$annotations() {
    }
}
