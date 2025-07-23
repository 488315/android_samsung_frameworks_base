package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import com.android.keyguard.ClockEventController;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxy;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardClockViewModel {
    public AodBurnInLayer burnInLayer;
    public final ClockEventController clockEventController;
    public final ReadonlyStateFlow clockShouldBeCentered;
    public final ReadonlyStateFlow clockSize;
    public final Context context;
    public final ReadonlyStateFlow currentClock;
    public final ReadonlyStateFlow currentClockLayout;
    public final ReadonlyStateFlow hasAodIcons;
    public final ReadonlyStateFlow hasCustomPositionUpdatedAnimation;
    public final ReadonlyStateFlow hasCustomWeatherDataDisplay;
    public final ReadonlyStateFlow isLargeClockVisible;
    public final ChannelFlowTransformLatest largeClockTextSize;
    public final Resources resources;
    public final ShadeModeInteractor shadeModeInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 smallClockTopMargin;
    public final SystemBarUtilsProxy systemBarUtils;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ClockLayout {
        public static final /* synthetic */ ClockLayout[] $VALUES;
        public static final ClockLayout LARGE_CLOCK;
        public static final ClockLayout SMALL_CLOCK;
        public static final ClockLayout SPLIT_SHADE_LARGE_CLOCK;
        public static final ClockLayout SPLIT_SHADE_SMALL_CLOCK;
        public static final ClockLayout SPLIT_SHADE_WEATHER_LARGE_CLOCK;
        public static final ClockLayout WEATHER_LARGE_CLOCK;

        static {
            ClockLayout clockLayout = new ClockLayout("LARGE_CLOCK", 0);
            LARGE_CLOCK = clockLayout;
            ClockLayout clockLayout2 = new ClockLayout("SMALL_CLOCK", 1);
            SMALL_CLOCK = clockLayout2;
            ClockLayout clockLayout3 = new ClockLayout("SPLIT_SHADE_LARGE_CLOCK", 2);
            SPLIT_SHADE_LARGE_CLOCK = clockLayout3;
            ClockLayout clockLayout4 = new ClockLayout("SPLIT_SHADE_SMALL_CLOCK", 3);
            SPLIT_SHADE_SMALL_CLOCK = clockLayout4;
            ClockLayout clockLayout5 = new ClockLayout("WEATHER_LARGE_CLOCK", 4);
            WEATHER_LARGE_CLOCK = clockLayout5;
            ClockLayout clockLayout6 = new ClockLayout("SPLIT_SHADE_WEATHER_LARGE_CLOCK", 5);
            SPLIT_SHADE_WEATHER_LARGE_CLOCK = clockLayout6;
            ClockLayout[] clockLayoutArr = {clockLayout, clockLayout2, clockLayout3, clockLayout4, clockLayout5, clockLayout6};
            $VALUES = clockLayoutArr;
            EnumEntriesKt.enumEntries(clockLayoutArr);
        }

        private ClockLayout(String str, int i) {
        }

        public static ClockLayout valueOf(String str) {
            return (ClockLayout) Enum.valueOf(ClockLayout.class, str);
        }

        public static ClockLayout[] values() {
            return (ClockLayout[]) $VALUES.clone();
        }
    }

    public KeyguardClockViewModel(Context context, KeyguardClockInteractor keyguardClockInteractor, CoroutineScope coroutineScope, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel, ShadeModeInteractor shadeModeInteractor, SystemBarUtilsProxy systemBarUtilsProxy, ConfigurationInteractor configurationInteractor, Resources resources) {
        ClockFaceController largeClock;
        ClockFaceConfig config;
        this.context = context;
        this.shadeModeInteractor = shadeModeInteractor;
        this.systemBarUtils = systemBarUtilsProxy;
        this.resources = resources;
        final ReadonlyStateFlow readonlyStateFlow = keyguardClockInteractor.clockSize;
        this.clockSize = readonlyStateFlow;
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.ClockSize r5 = (com.android.systemui.keyguard.shared.model.ClockSize) r5
                        com.android.systemui.keyguard.shared.model.ClockSize r6 = com.android.systemui.keyguard.shared.model.ClockSize.LARGE
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.TRUE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.isLargeClockVisible = stateIn;
        this.clockEventController = keyguardClockInteractor.clockEventController;
        ReadonlyStateFlow readonlyStateFlow2 = keyguardClockInteractor.currentClock;
        this.currentClock = readonlyStateFlow2;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, readonlyStateFlow2, new KeyguardClockViewModel$hasCustomWeatherDataDisplay$1(null));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        ClockController clockController = (ClockController) readonlyStateFlow2.$$delegate_0.getValue();
        this.hasCustomWeatherDataDisplay = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, WhileSubscribed$default, Boolean.valueOf((clockController == null || (largeClock = clockController.getLargeClock()) == null || (config = largeClock.getConfig()) == null) ? false : config.getHasCustomWeatherDataDisplay()));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(keyguardClockInteractor.clockShouldBeCentered, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.clockShouldBeCentered = stateIn2;
        final Flow flow2 = notificationIconContainerAlwaysOnDisplayViewModel.icons;
        Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2$2$1
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
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData r5 = (com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData) r5
                        java.util.List r5 = r5.visibleIcons
                        java.util.Collection r5 = (java.util.Collection) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool2 = Boolean.FALSE;
        this.hasAodIcons = FlowKt.stateIn(flow3, coroutineScope, WhileSubscribed$default2, bool2);
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        this.currentClockLayout = FlowKt.stateIn(FlowKt.combine(stateIn, stateIn2, shadeModeInteractorImpl.isShadeLayoutWide, readonlyStateFlow2, new KeyguardClockViewModel$currentClockLayout$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ClockLayout.SMALL_CLOCK);
        this.hasCustomPositionUpdatedAnimation = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, stateIn, new KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool2);
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        this.smallClockTopMargin = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(configurationInteractorImpl.onAnyConfigurationChange, shadeModeInteractorImpl.isShadeLayoutWide, new KeyguardClockViewModel$smallClockTopMargin$1(this, null));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = configurationInteractorImpl.onAnyConfigurationChange;
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardClockViewModel this$0;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardClockViewModel keyguardClockViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardClockViewModel;
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
                        boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L62
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel r6 = r5.this$0
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxy r7 = r6.systemBarUtils
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl r7 = (com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl) r7
                        android.content.Context r7 = r7.context
                        int r7 = com.android.internal.policy.SystemBarUtils.getStatusBarHeight(r7)
                        android.content.res.Resources r2 = r6.resources
                        int r4 = com.android.systemui.customization.R$dimen.small_clock_padding_top
                        int r2 = r2.getDimensionPixelSize(r4)
                        int r2 = r2 + r7
                        android.content.res.Resources r6 = r6.resources
                        int r7 = com.android.systemui.customization.R$dimen.keyguard_smartspace_top_offset
                        int r6 = r6.getDimensionPixelSize(r7)
                        int r6 = r6 + r2
                        java.lang.Integer r7 = new java.lang.Integer
                        r7.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L62
                        return r1
                    L62:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.largeClockTextSize = configurationInteractorImpl.dimensionPixelSize(R$dimen.large_clock_text_size);
    }

    public final int getSmallClockTopMargin() {
        return new ClockPreviewConfig(this.context, ((Boolean) ((ShadeModeInteractorImpl) this.shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue(), false, null, null, 24, null).getSmallClockTopPadding(((SystemBarUtilsProxyImpl) this.systemBarUtils).getStatusBarHeaderHeightKeyguard());
    }
}
