package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.ClockEventController;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxy;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.ResultKt;
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
                        Boolean boolValueOf = Boolean.valueOf(((ClockSize) obj) == ClockSize.LARGE);
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
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.TRUE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.isLargeClockVisible = readonlyStateFlowStateIn;
        this.clockEventController = keyguardClockInteractor.clockEventController;
        ReadonlyStateFlow readonlyStateFlow2 = keyguardClockInteractor.currentClock;
        this.currentClock = readonlyStateFlow2;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, readonlyStateFlow2, new KeyguardClockViewModel$hasCustomWeatherDataDisplay$1(null));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        ClockController clockController = (ClockController) readonlyStateFlow2.$$delegate_0.getValue();
        this.hasCustomWeatherDataDisplay = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Boolean.valueOf((clockController == null || (largeClock = clockController.getLargeClock()) == null || (config = largeClock.getConfig()) == null) ? false : config.getHasCustomWeatherDataDisplay()));
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(keyguardClockInteractor.clockShouldBeCentered, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.clockShouldBeCentered = readonlyStateFlowStateIn2;
        final Flow flow2 = notificationIconContainerAlwaysOnDisplayViewModel.icons;
        Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$2

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
                        Boolean boolValueOf = Boolean.valueOf(!((NotificationIconsViewData) obj).visibleIcons.isEmpty());
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool2 = Boolean.FALSE;
        this.hasAodIcons = FlowKt.stateIn(flow3, coroutineScope, startedWhileSubscribedWhileSubscribed$default2, bool2);
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        this.currentClockLayout = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn, readonlyStateFlowStateIn2, shadeModeInteractorImpl.isShadeLayoutWide, readonlyStateFlow2, new KeyguardClockViewModel$currentClockLayout$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ClockLayout.SMALL_CLOCK);
        this.hasCustomPositionUpdatedAnimation = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, readonlyStateFlowStateIn, new KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool2);
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        this.smallClockTopMargin = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(configurationInteractorImpl.onAnyConfigurationChange, shadeModeInteractorImpl.isShadeLayoutWide, new KeyguardClockViewModel$smallClockTopMargin$1(this, null));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = configurationInteractorImpl.onAnyConfigurationChange;
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$special$$inlined$map$3

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
                        KeyguardClockViewModel keyguardClockViewModel = this.this$0;
                        Integer num = new Integer(keyguardClockViewModel.resources.getDimensionPixelSize(R$dimen.keyguard_smartspace_top_offset) + keyguardClockViewModel.resources.getDimensionPixelSize(R$dimen.small_clock_padding_top) + SystemBarUtils.getStatusBarHeight(((SystemBarUtilsProxyImpl) keyguardClockViewModel.systemBarUtils).context));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.largeClockTextSize = configurationInteractorImpl.dimensionPixelSize(R$dimen.large_clock_text_size);
    }

    public final int getSmallClockTopMargin() {
        return new ClockPreviewConfig(this.context, ((Boolean) ((ShadeModeInteractorImpl) this.shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue(), false, null, null, 24, null).getSmallClockTopPadding(((SystemBarUtilsProxyImpl) this.systemBarUtils).getStatusBarHeaderHeightKeyguard());
    }
}
