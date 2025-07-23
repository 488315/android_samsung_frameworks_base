package com.android.systemui.keyguard.ui.viewmodel;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.BurnInInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryIconViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ChannelFlowTransformLatest accessibilityDelegateHint;
    public final AccessibilityInteractor accessibilityInteractor;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 alphaMultiplierFromShadeExpansion;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 animatedBurnInOffsets;
    public final Flow burnInOffsets;
    public final DeviceEntryIconViewModel$special$$inlined$map$4 deviceDidNotEnterFromDeviceEntryIcon;
    public final DeviceEntrySourceInteractor deviceEntrySourceInteractor;
    public final ReadonlyStateFlow deviceEntryViewAlpha;
    public final MutableSharedFlow dozeAmount;
    public final FloatEvaluator floatEvaluator;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconType;
    public final IntEvaluator intEvaluator;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isInteractive;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isLongPressEnabled;
    public final ReadonlyStateFlow isUdfpsSupported;
    public final ChannelFlowTransformLatest isUnlocked;
    public final Flow isVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final Lazy keyguardViewController;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 nonAnimatedBurnInOffsets;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 qsProgress;
    public final CoroutineScope scope;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 shadeExpansion;
    public final DeviceEntryIconViewModel$special$$inlined$map$1 showingAlternateBouncer;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 transitionAlpha;
    public final ReadonlyStateFlow udfpsLocation;
    public final ReadonlyStateFlow useBackgroundProtection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v16, types: [com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1] */
    public DeviceEntryIconViewModel(Set<DeviceEntryIconTransition> set, BurnInInteractor burnInInteractor, ShadeInteractor shadeInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, Lazy lazy, DeviceEntryInteractor deviceEntryInteractor, DeviceEntrySourceInteractor deviceEntrySourceInteractor, AccessibilityInteractor accessibilityInteractor, CoroutineScope coroutineScope) {
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardViewController = lazy;
        this.deviceEntrySourceInteractor = deviceEntrySourceInteractor;
        this.accessibilityInteractor = accessibilityInteractor;
        this.scope = coroutineScope;
        this.isUdfpsSupported = deviceEntryUdfpsInteractor.isUdfpsSupported;
        SharingStarted.Companion.getClass();
        this.udfpsLocation = FlowKt.stateIn(deviceEntryUdfpsInteractor.udfpsLocation, coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.intEvaluator = new IntEvaluator();
        this.floatEvaluator = new FloatEvaluator();
        final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.startedKeyguardTransitionStep;
        this.showingAlternateBouncer = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1$2$1
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r5.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.ALTERNATE_BOUNCER
                        if (r5 != r6) goto L3c
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.qsProgress = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryIconViewModel$qsProgress$1(null), shadeInteractorImpl.baseShadeInteractor.getQsExpansion());
        this.shadeExpansion = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryIconViewModel$shadeExpansion$1(null), shadeInteractorImpl.baseShadeInteractor.getShadeExpansion());
        Set<DeviceEntryIconTransition> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(((DeviceEntryIconTransition) it.next()).getDeviceEntryParentViewAlpha());
        }
        ChannelLimitedFlowMerge merge = FlowKt.merge(arrayList);
        CoroutineScope coroutineScope2 = this.scope;
        SharingStarted.Companion companion = SharingStarted.Companion;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryIconViewModel$transitionAlpha$2(this, keyguardTransitionInteractor, null), FlowKt.shareIn(merge, coroutineScope2, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0));
        this.transitionAlpha = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$2(null), FlowKt.combine(this.showingAlternateBouncer, this.shadeExpansion, this.qsProgress, new DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1(null)));
        this.alphaMultiplierFromShadeExpansion = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(burnInInteractor.deviceEntryIconXOffset, burnInInteractor.deviceEntryIconYOffset, burnInInteractor.udfpsProgress, new DeviceEntryIconViewModel$nonAnimatedBurnInOffsets$1(null));
        this.nonAnimatedBurnInOffsets = combine;
        MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.AOD);
        this.dozeAmount = transitionValueFlow;
        this.animatedBurnInOffsets = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(combine, transitionValueFlow, new DeviceEntryIconViewModel$animatedBurnInOffsets$1(this, null));
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, new DeviceEntryIconViewModel$deviceEntryViewAlpha$1(null)), this.scope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Float.valueOf(0.0f));
        this.deviceEntryViewAlpha = stateIn;
        this.useBackgroundProtection = this.isUdfpsSupported;
        this.burnInOffsets = FlowKt.distinctUntilChanged(FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new DeviceEntryIconViewModel$special$$inlined$flatMapLatest$1(null, keyguardTransitionInteractor, shadeInteractor, this)));
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(this.keyguardInteractor.isKeyguardDismissible, new DeviceEntryIconViewModel$special$$inlined$flatMapLatest$2(null));
        this.isUnlocked = transformLatest;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(deviceEntryUdfpsInteractor.isListeningForUdfps, transformLatest, new DeviceEntryIconViewModel$iconType$1(null));
        this.iconType = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.isVisible = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 <= 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this.isUdfpsSupported, new DeviceEntryIconViewModel$isInteractive$1(null));
        this.isInteractive = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        this.accessibilityDelegateHint = FlowKt.transformLatest(this.accessibilityInteractor.isEnabled, new DeviceEntryIconViewModel$special$$inlined$flatMapLatest$3(null, this));
        this.isLongPressEnabled = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        final SharedFlowImpl sharedFlowImpl = this.deviceEntrySourceInteractor.attemptEnterDeviceFromDeviceEntryIcon;
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntryIconViewModel this$0;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntryIconViewModel deviceEntryIconViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntryIconViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3$2$1
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
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel r5 = r4.this$0
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r5 = r5.keyguardInteractor
                        kotlinx.coroutines.flow.StateFlowImpl r5 = r5.isKeyguardDismissible
                        java.lang.Object r5 = r5.getValue()
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 != 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.deviceDidNotEnterFromDeviceEntryIcon = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
