package com.android.systemui.statusbar.policy.domain.interactor;

import android.content.Context;
import android.media.AudioSystem;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settingslib.notification.data.repository.ZenModeRepository;
import com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl;
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
import java.util.Map;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Integer r5 = (java.lang.Integer) r5
                        r6 = 0
                        if (r5 == 0) goto L3c
                        int r5 = r5.intValue()
                        goto L3d
                    L3c:
                        r5 = r6
                    L3d:
                        if (r5 == 0) goto L49
                        if (r5 == r3) goto L48
                        r2 = 2
                        if (r5 == r2) goto L48
                        r2 = 3
                        if (r5 == r2) goto L48
                        goto L49
                    L48:
                        r6 = r3
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), (StateFlow) zenModeRepositoryImpl.consolidatedNotificationPolicy$delegate.getValue(), new ZenModeInteractor$areNotificationsHiddenInShade$1(null)));
        final StateFlowImpl stateFlowImpl = zenModeRepositoryImpl.modes;
        this.modes = stateFlowImpl;
        this.dndMode = StateFlowKt.MutableStateFlow(null);
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5f
                    L2a:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L32:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L53
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        r0.L$0 = r8
                        r0.label = r4
                        int r2 = com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor.$r8$clinit
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor r6 = r6.this$0
                        java.lang.Object r6 = r6.buildActiveZenModes(r7, r0)
                        if (r6 != r1) goto L50
                        goto L5e
                    L50:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L53:
                        r7 = 0
                        r0.L$0 = r7
                        r0.label = r3
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L5f
                    L5e:
                        return r1
                    L5f:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        this.activeModes = distinctUntilChanged;
        FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.policy.domain.model.ActiveZenModes r5 = (com.android.systemui.statusbar.policy.domain.model.ActiveZenModes) r5
                        com.android.systemui.statusbar.policy.domain.model.ZenModeInfo r5 = r5.mainMode
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
    public final Flow m3091activeModesBlockingStreamtLTdkI8(int i) {
        final Function1 function1 = (Function1) this.zenModeByStreamPredicates.get(Integer.valueOf(i));
        if (function1 == null) {
            AudioStream.Companion companion = AudioStream.Companion;
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AudioSystem.streamToString(i), " is unsupported. Use canBeBlockedByZenMode to check if the stream can be affected by the Zen Mode.").toString());
        }
        final StateFlowImpl stateFlowImpl = this.modes;
        final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L69
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        java.util.ArrayList r8 = new java.util.ArrayList
                        r8.<init>()
                        java.util.Iterator r7 = r7.iterator()
                    L3f:
                        boolean r2 = r7.hasNext()
                        if (r2 == 0) goto L5e
                        java.lang.Object r2 = r7.next()
                        r4 = r2
                        com.android.settingslib.notification.modes.ZenMode r4 = (com.android.settingslib.notification.modes.ZenMode) r4
                        kotlin.jvm.functions.Function1 r5 = r6.$isBlockingStream$inlined
                        java.lang.Object r4 = r5.mo779invoke(r4)
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        if (r4 == 0) goto L3f
                        r8.add(r2)
                        goto L3f
                    L5e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L69
                        return r1
                    L69:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStream-tLTdkI8$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5f
                    L2a:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L32:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L53
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        r0.L$0 = r8
                        r0.label = r4
                        int r2 = com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor.$r8$clinit
                        com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor r6 = r6.this$0
                        java.lang.Object r6 = r6.buildActiveZenModes(r7, r0)
                        if (r6 != r1) goto L50
                        goto L5e
                    L50:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L53:
                        r7 = 0
                        r0.L$0 = r7
                        r0.label = r3
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L5f
                    L5e:
                        return r1
                    L5f:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$activeModesBlockingStreamtLTdkI8$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.bgDispatcher));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object buildActiveZenModes(java.util.List r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$buildActiveZenModes$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            java.lang.String r3 = ""
            r4 = 1
            if (r2 == 0) goto L39
            if (r2 != r4) goto L31
            java.lang.Object r6 = r0.L$1
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$0
            java.util.List r7 = (java.util.List) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L85
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.Iterator r7 = r7.iterator()
        L47:
            boolean r2 = r7.hasNext()
            if (r2 == 0) goto L5e
            java.lang.Object r2 = r7.next()
            r5 = r2
            com.android.settingslib.notification.modes.ZenMode r5 = (com.android.settingslib.notification.modes.ZenMode) r5
            boolean r5 = r5.isActive()
            if (r5 == 0) goto L47
            r8.add(r2)
            goto L47
        L5e:
            java.util.Comparator r7 = com.android.settingslib.notification.modes.ZenMode.PRIORITIZING_COMPARATOR
            java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(r8, r7)
            java.lang.Object r8 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r7)
            com.android.settingslib.notification.modes.ZenMode r8 = (com.android.settingslib.notification.modes.ZenMode) r8
            if (r8 == 0) goto L8d
            android.app.AutomaticZenRule r2 = r8.mRule
            java.lang.String r2 = r2.getName()
            int r5 = com.google.common.base.Platform.$r8$clinit
            if (r2 != 0) goto L77
            r2 = r3
        L77:
            r0.L$0 = r7
            r0.L$1 = r2
            r0.label = r4
            java.lang.Object r8 = r6.getModeIcon(r8, r0)
            if (r8 != r1) goto L84
            return r1
        L84:
            r6 = r2
        L85:
            com.android.settingslib.notification.modes.ZenIcon r8 = (com.android.settingslib.notification.modes.ZenIcon) r8
            com.android.systemui.statusbar.policy.domain.model.ZenModeInfo r0 = new com.android.systemui.statusbar.policy.domain.model.ZenModeInfo
            r0.<init>(r6, r8)
            goto L8e
        L8d:
            r0 = 0
        L8e:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r6 = new java.util.ArrayList
            r8 = 10
            int r8 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r7, r8)
            r6.<init>(r8)
            java.util.Iterator r7 = r7.iterator()
        L9f:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto Lba
            java.lang.Object r8 = r7.next()
            com.android.settingslib.notification.modes.ZenMode r8 = (com.android.settingslib.notification.modes.ZenMode) r8
            android.app.AutomaticZenRule r8 = r8.mRule
            java.lang.String r8 = r8.getName()
            int r1 = com.google.common.base.Platform.$r8$clinit
            if (r8 != 0) goto Lb6
            r8 = r3
        Lb6:
            r6.add(r8)
            goto L9f
        Lba:
            com.android.systemui.statusbar.policy.domain.model.ActiveZenModes r7 = new com.android.systemui.statusbar.policy.domain.model.ActiveZenModes
            r7.<init>(r6, r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor.buildActiveZenModes(java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getModeIcon(final com.android.settingslib.notification.modes.ZenMode r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1 r0 = (com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1 r0 = new com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor$getModeIcon$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            return r8
        L27:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            android.content.Context r8 = r6.context
            com.android.settingslib.notification.modes.ZenIconLoader r6 = r6.iconLoader
            r6.getClass()
            boolean r2 = r7.isManualDnd()
            if (r2 == 0) goto L42
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = com.android.settingslib.notification.modes.ZenIconKeys.MANUAL_DND
            goto L95
        L42:
            android.app.AutomaticZenRule r2 = r7.mRule
            int r2 = r2.getIconResId()
            if (r2 == 0) goto L75
            android.app.AutomaticZenRule r2 = r7.mRule
            java.lang.String r2 = r2.getPackageName()
            java.lang.String r4 = "android"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L63
            android.app.AutomaticZenRule r2 = r7.mRule
            int r2 = r2.getIconResId()
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = com.android.settingslib.notification.modes.ZenIcon.Key.forSystemResource(r2)
            goto L95
        L63:
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = new com.android.settingslib.notification.modes.ZenIcon$Key
            android.app.AutomaticZenRule r4 = r7.mRule
            java.lang.String r4 = r4.getPackageName()
            android.app.AutomaticZenRule r5 = r7.mRule
            int r5 = r5.getIconResId()
            r2.<init>(r4, r5)
            goto L95
        L75:
            com.android.settingslib.notification.modes.ZenMode$Kind r2 = r7.mKind
            com.android.settingslib.notification.modes.ZenMode$Kind r4 = com.android.settingslib.notification.modes.ZenMode.Kind.IMPLICIT
            if (r2 != r4) goto L7e
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = com.android.settingslib.notification.modes.ZenIconKeys.IMPLICIT_MODE_DEFAULT
            goto L95
        L7e:
            android.app.AutomaticZenRule r2 = r7.mRule
            int r2 = r2.getType()
            com.google.common.collect.ImmutableMap r4 = com.android.settingslib.notification.modes.ZenIconKeys.TYPE_DEFAULTS
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.Object r2 = r4.get(r2)
            if (r2 == 0) goto L91
            goto L93
        L91:
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = com.android.settingslib.notification.modes.ZenIconKeys.FOR_UNEXPECTED_TYPE
        L93:
            com.android.settingslib.notification.modes.ZenIcon$Key r2 = (com.android.settingslib.notification.modes.ZenIcon.Key) r2
        L95:
            com.google.common.util.concurrent.ListenableFuture r4 = r6.loadIcon(r8, r2, r3)
            int r5 = com.google.common.util.concurrent.FluentFuture.$r8$clinit
            boolean r5 = r4 instanceof com.google.common.util.concurrent.FluentFuture
            if (r5 == 0) goto La2
            com.google.common.util.concurrent.FluentFuture r4 = (com.google.common.util.concurrent.FluentFuture) r4
            goto La8
        La2:
            com.google.common.util.concurrent.ForwardingFluentFuture r5 = new com.google.common.util.concurrent.ForwardingFluentFuture
            r5.<init>(r4)
            r4 = r5
        La8:
            com.android.settingslib.notification.modes.ZenIconLoader$$ExternalSyntheticLambda0 r5 = new com.android.settingslib.notification.modes.ZenIconLoader$$ExternalSyntheticLambda0
            r5.<init>()
            r4.getClass()
            int r7 = com.google.common.util.concurrent.AbstractTransformFuture.$r8$clinit
            com.google.common.util.concurrent.ListeningExecutorService r6 = r6.mBackgroundExecutor
            r6.getClass()
            com.google.common.util.concurrent.AbstractTransformFuture$AsyncTransformFuture r7 = new com.google.common.util.concurrent.AbstractTransformFuture$AsyncTransformFuture
            r7.<init>(r4, r5)
            java.util.concurrent.Executor r6 = com.google.common.util.concurrent.MoreExecutors.rejectionPropagatingExecutor(r6, r7)
            r4.addListener(r7, r6)
            r0.label = r3
            java.lang.Object r6 = androidx.concurrent.futures.ListenableFutureKt.await(r7, r0)
            if (r6 != r1) goto Lcc
            return r1
        Lcc:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor.getModeIcon(com.android.settingslib.notification.modes.ZenMode, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final int getZenDuration() {
        return ((Number) this.notificationSettingsRepository.zenDuration.$$delegate_0.getValue()).intValue();
    }
}
