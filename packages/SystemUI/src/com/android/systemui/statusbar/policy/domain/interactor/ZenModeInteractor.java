package com.android.systemui.statusbar.policy.domain.interactor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioSystem;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.ListenableFutureKt;
import com.android.settingslib.notification.data.repository.ZenModeRepository;
import com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl;
import com.android.settingslib.notification.modes.ZenIcon;
import com.android.settingslib.notification.modes.ZenIconKeys;
import com.android.settingslib.notification.modes.ZenIconLoader;
import com.android.settingslib.notification.modes.ZenMode;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.modes.shared.ModesUi;
import com.android.systemui.shared.notifications.data.repository.NotificationSettingsRepository;
import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepository;
import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import com.google.common.base.Platform;
import com.google.common.util.concurrent.AbstractTransformFuture;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.ForwardingFluentFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class ZenModeInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow activeModes;
    public final Flow areNotificationsHiddenInShade;
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public final StateFlowImpl dndMode;
    public final ZenIconLoader iconLoader;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isZenAvailable;
    public final StateFlowImpl modes;
    public final NotificationSettingsRepository notificationSettingsRepository;
    public final Map zenModeByStreamPredicates;
    public final ZenModeRepository zenModeRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            ZenModeInteractor zenModeInteractor = ZenModeInteractor.this;
            int i = ZenModeInteractor.$r8$clinit;
            return zenModeInteractor.buildActiveZenModes(null, this);
        }
    }

    /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1, reason: invalid class name and case insensitive filesystem */
    final class C11111 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C11111(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ZenModeInteractor.this.getModeIcon(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public ZenModeInteractor(Context context, ZenModeRepository zenModeRepository, NotificationSettingsRepository notificationSettingsRepository, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ZenIconLoader zenIconLoader, DeviceProvisioningRepository deviceProvisioningRepository, UserSetupRepository userSetupRepository) {
        this.context = context;
        this.zenModeRepository = zenModeRepository;
        this.notificationSettingsRepository = notificationSettingsRepository;
        this.bgDispatcher = coroutineDispatcher;
        this.iconLoader = zenIconLoader;
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        this.zenModeByStreamPredicates = MapsKt__MapsKt.mapOf(new Pair(3, new Function1() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i) {
                    case 0:
                        int i4 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryMedia() == 2);
                    case 1:
                        int i5 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryAlarms() == 2);
                    default:
                        int i6 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategorySystem() == 2);
                }
            }
        }), new Pair(4, new Function1() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i2) {
                    case 0:
                        int i4 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryMedia() == 2);
                    case 1:
                        int i5 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryAlarms() == 2);
                    default:
                        int i6 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategorySystem() == 2);
                }
            }
        }), new Pair(1, new Function1() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i3) {
                    case 0:
                        int i4 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryMedia() == 2);
                    case 1:
                        int i5 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategoryAlarms() == 2);
                    default:
                        int i6 = ZenModeInteractor.$r8$clinit;
                        return Boolean.valueOf(zenMode.getPolicy().getPriorityCategorySystem() == 2);
                }
            }
        }));
        this.isZenAvailable = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((DeviceProvisioningRepositoryImpl) deviceProvisioningRepository).isDeviceProvisioned, ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp, new ZenModeInteractor$isZenAvailable$1(null));
        ZenModeRepositoryImpl zenModeRepositoryImpl = (ZenModeRepositoryImpl) zenModeRepository;
        final StateFlow stateFlow = (StateFlow) zenModeRepositoryImpl.globalZenMode$delegate.getValue();
        this.areNotificationsHiddenInShade = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Integer num = (Integer) obj;
                        boolean z = false;
                        int iIntValue = num != null ? num.intValue() : 0;
                        if (iIntValue != 0 && (iIntValue == 1 || iIntValue == 2 || iIntValue == 3)) {
                            z = true;
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
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
                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), (StateFlow) zenModeRepositoryImpl.consolidatedNotificationPolicy$delegate.getValue(), new ZenModeInteractor$areNotificationsHiddenInShade$1(null)));
        final StateFlowImpl stateFlowImpl = zenModeRepositoryImpl.modes;
        this.modes = stateFlowImpl;
        this.dndMode = StateFlowKt.MutableStateFlow(null);
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ZenModeInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ZenModeInteractor zenModeInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = zenModeInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = ZenModeInteractor.$r8$clinit;
                        Object objBuildActiveZenModes = this.this$0.buildActiveZenModes((List) obj, anonymousClass1);
                        if (objBuildActiveZenModes != coroutineSingletons) {
                            obj2 = objBuildActiveZenModes;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        this.activeModes = flowDistinctUntilChanged;
        FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4

            /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        ZenModeInfo zenModeInfo = ((ActiveZenModes) obj).mainMode;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(zenModeInfo, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        LazyKt__LazyJVMKt.lazy(new ZenModeInteractor$$ExternalSyntheticLambda3());
    }

    public static void getDndMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = ModesUi.$r8$clinit;
        throw new IllegalStateException("New code path not supported when android.app.modes_ui is disabled.");
    }

    /* renamed from: activeModesBlockingStream-tLTdkI8, reason: not valid java name */
    public final Flow m3108activeModesBlockingStreamtLTdkI8(int i) {
        final Function1 function1 = (Function1) this.zenModeByStreamPredicates.get(Integer.valueOf(i));
        if (function1 == null) {
            AudioStream.Companion companion = AudioStream.Companion;
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AudioSystem.streamToString(i), " is unsupported. Use canBeBlockedByZenMode to check if the stream can be affected by the Zen Mode.").toString());
        }
        final StateFlowImpl stateFlowImpl = this.modes;
        final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $isBlockingStream$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$isBlockingStream$inlined = function1;
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
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (List) obj) {
                            if (((Boolean) this.$isBlockingStream$inlined.mo781invoke((ZenMode) obj3)).booleanValue()) {
                                arrayList.add(obj3);
                            }
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
                Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ZenModeInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ZenModeInteractor zenModeInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = zenModeInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005c, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = ZenModeInteractor.$r8$clinit;
                        Object objBuildActiveZenModes = this.this$0.buildActiveZenModes((List) obj, anonymousClass1);
                        if (objBuildActiveZenModes != coroutineSingletons) {
                            obj2 = objBuildActiveZenModes;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, this.bgDispatcher));
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object buildActiveZenModes(List list, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        List listSortedWith;
        ZenModeInfo zenModeInfo;
        String str;
        Iterator it;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object modeIcon = anonymousClass1.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(modeIcon);
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                if (((ZenMode) obj2).isActive()) {
                    arrayList.add(obj2);
                }
            }
            listSortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList, ZenMode.PRIORITIZING_COMPARATOR);
            ZenMode zenMode = (ZenMode) CollectionsKt___CollectionsKt.firstOrNull(listSortedWith);
            if (zenMode == null) {
                zenModeInfo = null;
                List list2 = listSortedWith;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                it = list2.iterator();
                while (it.hasNext()) {
                    String name = ((ZenMode) it.next()).mRule.getName();
                    int i3 = Platform.$r8$clinit;
                    if (name == null) {
                        name = "";
                    }
                    arrayList2.add(name);
                }
                return new ActiveZenModes(arrayList2, zenModeInfo);
            }
            String name2 = zenMode.mRule.getName();
            int i4 = Platform.$r8$clinit;
            if (name2 == null) {
                name2 = "";
            }
            anonymousClass1.L$0 = listSortedWith;
            anonymousClass1.L$1 = name2;
            anonymousClass1.label = 1;
            modeIcon = getModeIcon(zenMode, anonymousClass1);
            if (modeIcon == obj) {
                return obj;
            }
            str = name2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) anonymousClass1.L$1;
            listSortedWith = (List) anonymousClass1.L$0;
            ResultKt.throwOnFailure(modeIcon);
        }
        zenModeInfo = new ZenModeInfo(str, (ZenIcon) modeIcon);
        List list22 = listSortedWith;
        ArrayList arrayList22 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list22, 10));
        it = list22.iterator();
        while (it.hasNext()) {
        }
        return new ActiveZenModes(arrayList22, zenModeInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getModeIcon(final ZenMode zenMode, ContinuationImpl continuationImpl) {
        C11111 c11111;
        final ZenIcon.Key keyForSystemResource;
        if (continuationImpl instanceof C11111) {
            c11111 = (C11111) continuationImpl;
            int i = c11111.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11111.label = i - Integer.MIN_VALUE;
            } else {
                c11111 = new C11111(continuationImpl);
            }
        }
        Object obj = c11111.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11111.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        final Context context = this.context;
        final ZenIconLoader zenIconLoader = this.iconLoader;
        zenIconLoader.getClass();
        if (zenMode.isManualDnd()) {
            keyForSystemResource = ZenIconKeys.MANUAL_DND;
        } else if (zenMode.mRule.getIconResId() != 0) {
            keyForSystemResource = "android".equals(zenMode.mRule.getPackageName()) ? ZenIcon.Key.forSystemResource(zenMode.mRule.getIconResId()) : new ZenIcon.Key(zenMode.mRule.getPackageName(), zenMode.mRule.getIconResId());
        } else if (zenMode.mKind == ZenMode.Kind.IMPLICIT) {
            keyForSystemResource = ZenIconKeys.IMPLICIT_MODE_DEFAULT;
        } else {
            Object obj2 = ZenIconKeys.TYPE_DEFAULTS.get(Integer.valueOf(zenMode.mRule.getType()));
            if (obj2 == null) {
                obj2 = ZenIconKeys.FOR_UNEXPECTED_TYPE;
            }
            keyForSystemResource = (ZenIcon.Key) obj2;
        }
        ListenableFuture listenableFutureLoadIcon = zenIconLoader.loadIcon(context, keyForSystemResource, true);
        int i3 = FluentFuture.$r8$clinit;
        FluentFuture forwardingFluentFuture = listenableFutureLoadIcon instanceof FluentFuture ? (FluentFuture) listenableFutureLoadIcon : new ForwardingFluentFuture(listenableFutureLoadIcon);
        AsyncFunction asyncFunction = new AsyncFunction() { // from class: com.android.settingslib.notification.modes.ZenIconLoader$$ExternalSyntheticLambda0
            @Override // com.google.common.util.concurrent.AsyncFunction
            public final ListenableFuture apply(Object obj3) {
                Context context2 = context;
                Drawable drawable = (Drawable) obj3;
                Drawable drawable2 = ZenIconLoader.MISSING;
                ZenIconLoader zenIconLoader2 = zenIconLoader;
                zenIconLoader2.getClass();
                if (drawable != null) {
                    return Futures.immediateFuture(new ZenIcon(keyForSystemResource, drawable));
                }
                ZenMode zenMode2 = zenMode;
                Object obj4 = ZenIconKeys.TYPE_DEFAULTS.get(Integer.valueOf(zenMode2.mRule.getType()));
                if (obj4 == null) {
                    obj4 = ZenIconKeys.FOR_UNEXPECTED_TYPE;
                }
                ZenIcon.Key key = (ZenIcon.Key) obj4;
                ListenableFuture listenableFutureLoadIcon2 = zenIconLoader2.loadIcon(context2, key, false);
                int i4 = FluentFuture.$r8$clinit;
                FluentFuture forwardingFluentFuture2 = listenableFutureLoadIcon2 instanceof FluentFuture ? (FluentFuture) listenableFutureLoadIcon2 : new ForwardingFluentFuture(listenableFutureLoadIcon2);
                ZenIconLoader$$ExternalSyntheticLambda1 zenIconLoader$$ExternalSyntheticLambda1 = new ZenIconLoader$$ExternalSyntheticLambda1(zenMode2, key);
                Executor executorDirectExecutor = MoreExecutors.directExecutor();
                forwardingFluentFuture2.getClass();
                return Futures.transform(forwardingFluentFuture2, zenIconLoader$$ExternalSyntheticLambda1, executorDirectExecutor);
            }
        };
        forwardingFluentFuture.getClass();
        int i4 = AbstractTransformFuture.$r8$clinit;
        ListeningExecutorService listeningExecutorService = zenIconLoader.mBackgroundExecutor;
        listeningExecutorService.getClass();
        AbstractTransformFuture.AsyncTransformFuture asyncTransformFuture = new AbstractTransformFuture.AsyncTransformFuture(forwardingFluentFuture, asyncFunction);
        forwardingFluentFuture.addListener(asyncTransformFuture, MoreExecutors.rejectionPropagatingExecutor(listeningExecutorService, asyncTransformFuture));
        c11111.label = 1;
        Object objAwait = ListenableFutureKt.await(asyncTransformFuture, c11111);
        return objAwait == coroutineSingletons ? coroutineSingletons : objAwait;
    }

    public final int getZenDuration() {
        return ((Number) this.notificationSettingsRepository.zenDuration.$$delegate_0.getValue()).intValue();
    }
}
