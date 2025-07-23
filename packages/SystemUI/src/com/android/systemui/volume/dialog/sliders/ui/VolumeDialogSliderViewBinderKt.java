package com.android.systemui.volume.dialog.sliders.ui;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.sliders.ui.compose.SliderIconsState;
import com.android.systemui.volume.dialog.sliders.ui.compose.TrackMeasurePolicy;
import com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderStateModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel;
import com.android.systemui.volume.ui.compose.slider.AccessibilityParams;
import com.android.systemui.volume.ui.compose.slider.Haptics;
import com.android.systemui.volume.ui.compose.slider.SliderIconKt;
import com.android.systemui.volume.ui.compose.slider.SliderKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatRange;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VolumeDialogSliderViewBinderKt {
    public static final void VolumeDialogSlider(final VolumeDialogSliderViewModel volumeDialogSliderViewModel, final VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel, final SliderHapticsViewModel.Factory factory, Modifier modifier, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(510520555);
        if (((i | (composerImpl.changedInstance(volumeDialogSliderViewModel) ? 4 : 2) | (composerImpl.changedInstance(volumeDialogOverscrollViewModel) ? 32 : 16) | (composerImpl.changed(factory) ? 256 : 128) | 3072) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            final Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider (VolumeDialogSliderViewBinder.kt:89)");
            }
            SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            materialTheme.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j2 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j3 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j4 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            sliderDefaults.getClass();
            Color.Companion.getClass();
            long j5 = Color.Unspecified;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.colors (Slider.kt:1122)");
            }
            materialTheme.getClass();
            final SliderColors m290copyK518z4 = SliderDefaults.getDefaultSliderColors$material3_release(MaterialTheme.getColorScheme(composerImpl)).m290copyK518z4(j5, j5, j, j2, j5, j5, j5, j3, j4, j5);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            final VolumeDialogSliderStateModel volumeDialogSliderStateModel = (VolumeDialogSliderStateModel) FlowExtKt.collectAsStateWithLifecycle(volumeDialogSliderViewModel.state, null, composerImpl, 48).getValue();
            if (volumeDialogSliderStateModel == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i2 = 0;
                    endRestartGroup.block = new Function2(volumeDialogSliderViewModel, volumeDialogOverscrollViewModel, factory, companion, i, i2) { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda0
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ VolumeDialogSliderViewModel f$0;
                        public final /* synthetic */ VolumeDialogOverscrollViewModel f$1;
                        public final /* synthetic */ SliderHapticsViewModel.Factory f$2;
                        public final /* synthetic */ Modifier f$3;

                        {
                            this.$r8$classId = i2;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (this.$r8$classId) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                                    SliderHapticsViewModel.Factory factory2 = this.f$2;
                                    Modifier modifier3 = this.f$3;
                                    VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory2, modifier3, (Composer) obj, updateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                                    SliderHapticsViewModel.Factory factory3 = this.f$2;
                                    Modifier modifier4 = this.f$3;
                                    VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory3, modifier4, (Composer) obj, updateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.startReplaceGroup(1007827447);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1007830270);
            boolean changedInstance = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1(mutableInteractionSource, volumeDialogSliderViewModel, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) rememberedValue2);
            boolean z = !volumeDialogSliderStateModel.isDisabled;
            Haptics enabled = factory != null ? new Haptics.Enabled(factory, new SliderHapticFeedbackFilter(false, false, 3, null), Orientation.Vertical) : Haptics.Disabled.INSTANCE;
            AccessibilityParams accessibilityParams = new AccessibilityParams(volumeDialogSliderStateModel.label, null, 2, null);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1007920052);
            boolean changedInstance2 = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new PointerInputEventHandler() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ PointerInputScope $this_pointerInput;
                        final /* synthetic */ VolumeDialogSliderViewModel $viewModel;
                        int label;

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1$1$1, reason: invalid class name and collision with other inner class name */
                        final class C04211 extends RestrictedSuspendLambda implements Function2 {
                            final /* synthetic */ CoroutineContext $currentContext;
                            final /* synthetic */ VolumeDialogSliderViewModel $viewModel;
                            private /* synthetic */ Object L$0;
                            Object L$1;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public C04211(CoroutineContext coroutineContext, VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
                                super(2, continuation);
                                this.$currentContext = coroutineContext;
                                this.$viewModel = volumeDialogSliderViewModel;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                C04211 c04211 = new C04211(this.$currentContext, this.$viewModel, continuation);
                                c04211.L$0 = obj;
                                return c04211;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((C04211) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
                            /* JADX WARN: Removed duplicated region for block: B:14:0x00d6  */
                            /* JADX WARN: Removed duplicated region for block: B:16:0x007a  */
                            /* JADX WARN: Removed duplicated region for block: B:7:0x0060  */
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0039 -> B:5:0x003c). Please report as a decompilation issue!!! */
                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object invokeSuspend(java.lang.Object r13) {
                                /*
                                    r12 = this;
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r1 = r12.label
                                    r2 = 1
                                    if (r1 == 0) goto L1d
                                    if (r1 != r2) goto L15
                                    java.lang.Object r1 = r12.L$1
                                    com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel r1 = (com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel) r1
                                    java.lang.Object r3 = r12.L$0
                                    androidx.compose.ui.input.pointer.AwaitPointerEventScope r3 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r3
                                    kotlin.ResultKt.throwOnFailure(r13)
                                    goto L3c
                                L15:
                                    java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                                    java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                                    r12.<init>(r13)
                                    throw r12
                                L1d:
                                    kotlin.ResultKt.throwOnFailure(r13)
                                    java.lang.Object r13 = r12.L$0
                                    androidx.compose.ui.input.pointer.AwaitPointerEventScope r13 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r13
                                    r3 = r13
                                L25:
                                    kotlin.coroutines.CoroutineContext r13 = r12.$currentContext
                                    boolean r13 = kotlinx.coroutines.JobKt.isActive(r13)
                                    if (r13 == 0) goto Ld6
                                    com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel r1 = r12.$viewModel
                                    r12.L$0 = r3
                                    r12.L$1 = r1
                                    r12.label = r2
                                    java.lang.Object r13 = androidx.compose.ui.input.pointer.AwaitPointerEventScope.awaitPointerEvent$default(r3, r12)
                                    if (r13 != r0) goto L3c
                                    return r0
                                L3c:
                                    androidx.compose.ui.input.pointer.PointerEvent r13 = (androidx.compose.ui.input.pointer.PointerEvent) r13
                                    r1.getClass()
                                    java.util.List r4 = r13.changes
                                    java.lang.Object r4 = kotlin.collections.CollectionsKt___CollectionsKt.first(r4)
                                    androidx.compose.ui.input.pointer.PointerInputChange r4 = (androidx.compose.ui.input.pointer.PointerInputChange) r4
                                    long r4 = r4.position
                                    int r13 = r13.type
                                    androidx.compose.ui.input.pointer.PointerEventType$Companion r6 = androidx.compose.ui.input.pointer.PointerEventType.Companion
                                    r6.getClass()
                                    int r6 = androidx.compose.ui.input.pointer.PointerEventType.Press
                                    r7 = 0
                                    r8 = 4294967295(0xffffffff, double:2.1219957905E-314)
                                    r10 = 32
                                    com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInputEventsInteractor r1 = r1.inputEventsInteractor
                                    if (r13 != r6) goto L7a
                                    com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Start r13 = new com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Start
                                    long r10 = r4 >> r10
                                    int r6 = (int) r10
                                    float r6 = java.lang.Float.intBitsToFloat(r6)
                                    long r4 = r4 & r8
                                    int r4 = (int) r4
                                    float r4 = java.lang.Float.intBitsToFloat(r4)
                                    r13.<init>(r6, r4)
                                    com.android.systemui.volume.dialog.sliders.data.repository.VolumeDialogSliderTouchEventsRepository r1 = r1.repository
                                    kotlinx.coroutines.flow.StateFlowImpl r1 = r1.mutableSliderTouchEvents
                                    r1.updateState(r7, r13)
                                    goto L25
                                L7a:
                                    int r6 = androidx.compose.ui.input.pointer.PointerEventType.Move
                                    if (r13 != r6) goto L98
                                    com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Move r13 = new com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Move
                                    long r10 = r4 >> r10
                                    int r6 = (int) r10
                                    float r6 = java.lang.Float.intBitsToFloat(r6)
                                    long r4 = r4 & r8
                                    int r4 = (int) r4
                                    float r4 = java.lang.Float.intBitsToFloat(r4)
                                    r13.<init>(r6, r4)
                                    com.android.systemui.volume.dialog.sliders.data.repository.VolumeDialogSliderTouchEventsRepository r1 = r1.repository
                                    kotlinx.coroutines.flow.StateFlowImpl r1 = r1.mutableSliderTouchEvents
                                    r1.updateState(r7, r13)
                                    goto L25
                                L98:
                                    int r6 = androidx.compose.ui.input.pointer.PointerEventType.Scroll
                                    if (r13 != r6) goto Lb7
                                    com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Move r13 = new com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$Move
                                    long r10 = r4 >> r10
                                    int r6 = (int) r10
                                    float r6 = java.lang.Float.intBitsToFloat(r6)
                                    long r4 = r4 & r8
                                    int r4 = (int) r4
                                    float r4 = java.lang.Float.intBitsToFloat(r4)
                                    r13.<init>(r6, r4)
                                    com.android.systemui.volume.dialog.sliders.data.repository.VolumeDialogSliderTouchEventsRepository r1 = r1.repository
                                    kotlinx.coroutines.flow.StateFlowImpl r1 = r1.mutableSliderTouchEvents
                                    r1.updateState(r7, r13)
                                    goto L25
                                Lb7:
                                    int r6 = androidx.compose.ui.input.pointer.PointerEventType.Release
                                    if (r13 != r6) goto L25
                                    com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$End r13 = new com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent$Touch$End
                                    long r10 = r4 >> r10
                                    int r6 = (int) r10
                                    float r6 = java.lang.Float.intBitsToFloat(r6)
                                    long r4 = r4 & r8
                                    int r4 = (int) r4
                                    float r4 = java.lang.Float.intBitsToFloat(r4)
                                    r13.<init>(r6, r4)
                                    com.android.systemui.volume.dialog.sliders.data.repository.VolumeDialogSliderTouchEventsRepository r1 = r1.repository
                                    kotlinx.coroutines.flow.StateFlowImpl r1 = r1.mutableSliderTouchEvents
                                    r1.updateState(r7, r13)
                                    goto L25
                                Ld6:
                                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                                    return r12
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1.AnonymousClass1.C04211.invokeSuspend(java.lang.Object):java.lang.Object");
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(PointerInputScope pointerInputScope, VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.$this_pointerInput = pointerInputScope;
                            this.$viewModel = volumeDialogSliderViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.$this_pointerInput, this.$viewModel, continuation);
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
                                CoroutineContext context = getContext();
                                PointerInputScope pointerInputScope = this.$this_pointerInput;
                                C04211 c04211 = new C04211(context, this.$viewModel, null);
                                this.label = 1;
                                if (pointerInputScope.awaitPointerEventScope(c04211, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        Object coroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(pointerInputScope, VolumeDialogSliderViewModel.this, null), continuation);
                        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            composerImpl.end(false);
            Modifier pointerInput = SuspendingPointerInputFilterKt.pointerInput(companion, unit, (PointerInputEventHandler) rememberedValue3);
            composerImpl.startReplaceGroup(1007845032);
            boolean changedInstance3 = composerImpl.changedInstance(volumeDialogOverscrollViewModel) | composerImpl.changed(volumeDialogSliderStateModel) | composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (changedInstance3 || rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        float floatValue = ((Float) obj).floatValue();
                        ClosedFloatRange closedFloatRange = (ClosedFloatRange) volumeDialogSliderStateModel.valueRange;
                        VolumeDialogOverscrollViewModel.this.sliderValue.updateState(null, new VolumeDialogOverscrollViewModel.Slider(floatValue, closedFloatRange._start, closedFloatRange._endInclusive));
                        VolumeDialogSliderViewModel volumeDialogSliderViewModel2 = volumeDialogSliderViewModel;
                        volumeDialogSliderViewModel2.visibilityInteractor.resetDismissTimeout();
                        volumeDialogSliderViewModel2.userVolumeUpdates.updateState(null, new VolumeDialogSliderViewModel.VolumeUpdate(MathKt__MathJVMKt.roundToInt(floatValue), volumeDialogSliderViewModel2.systemClock.uptimeMillis()));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            Function1 function1 = (Function1) rememberedValue4;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1007854840);
            boolean changedInstance4 = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object rememberedValue5 = composerImpl.rememberedValue();
            if (changedInstance4 || rememberedValue5 == composer$Companion$Empty$1) {
                rememberedValue5 = new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        float floatValue = ((Float) obj).floatValue();
                        VolumeDialogSliderViewModel volumeDialogSliderViewModel2 = VolumeDialogSliderViewModel.this;
                        volumeDialogSliderViewModel2.getClass();
                        int roundToInt = MathKt__MathJVMKt.roundToInt(floatValue);
                        int audioStream = volumeDialogSliderViewModel2.sliderType.getAudioStream();
                        VolumeDialogLogger volumeDialogLogger = volumeDialogSliderViewModel2.logger;
                        volumeDialogLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = volumeDialogLogger.logBuffer;
                        LogMessage obtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.int1 = roundToInt;
                        logMessageImpl.int2 = audioStream;
                        logBuffer.commit(obtain);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue5);
            }
            composerImpl.end(false);
            SliderKt.Slider(volumeDialogSliderStateModel.value, volumeDialogSliderStateModel.valueRange, function1, (Function1) rememberedValue5, z, accessibilityParams, pointerInput, 1.0f, m290copyK518z4, mutableInteractionSource, enabled, true, true, ComposableLambdaKt.rememberComposableLambda(-559232083, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$6
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SliderState sliderState = (SliderState) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous> (VolumeDialogSliderViewBinder.kt:138)");
                    }
                    final VolumeDialogSliderStateModel volumeDialogSliderStateModel2 = VolumeDialogSliderStateModel.this;
                    VolumeDialogSliderTrackKt.m3202SliderTrackq58E_xs(sliderState, !volumeDialogSliderStateModel2.isDisabled, null, m290copyK518z4, 0.0f, 0.0f, 0.0f, 0.0f, true, ComposableLambdaKt.rememberComposableLambda(1218107908, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$6.1
                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                            SliderIconsState sliderIconsState = (SliderIconsState) obj5;
                            Composer composer3 = (Composer) obj6;
                            int intValue2 = ((Number) obj7).intValue();
                            if ((intValue2 & 48) == 0) {
                                intValue2 |= (intValue2 & 64) == 0 ? ((ComposerImpl) composer3).changed(sliderIconsState) : ((ComposerImpl) composer3).changedInstance(sliderIconsState) ? 32 : 16;
                            }
                            if ((intValue2 & 145) == 144) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:144)");
                            }
                            final VolumeDialogSliderStateModel volumeDialogSliderStateModel3 = VolumeDialogSliderStateModel.this;
                            SliderIconKt.SliderIcon(ComposableLambdaKt.rememberComposableLambda(-987571870, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.1.1
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                    Composer composer4 = (Composer) obj9;
                                    if ((((Number) obj10).intValue() & 17) == 16) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:146)");
                                    }
                                    Icon.Loaded loaded = VolumeDialogSliderStateModel.this.icon;
                                    Dp.Companion companion2 = Dp.Companion;
                                    IconKt.m1072IconFNF3uiM(loaded, SizeKt.m139size3ABfNKs(Modifier.Companion, 20), 0L, composer4, 48, 4);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer3), ((TrackMeasurePolicy) sliderIconsState).isActiveTrackStartIconVisible(), null, composer3, 6);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), ComposableLambdaKt.rememberComposableLambda(-212353485, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$6.2
                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                            SliderIconsState sliderIconsState = (SliderIconsState) obj5;
                            Composer composer3 = (Composer) obj6;
                            int intValue2 = ((Number) obj7).intValue();
                            if ((intValue2 & 48) == 0) {
                                intValue2 |= (intValue2 & 64) == 0 ? ((ComposerImpl) composer3).changed(sliderIconsState) : ((ComposerImpl) composer3).changedInstance(sliderIconsState) ? 32 : 16;
                            }
                            if ((intValue2 & 145) == 144) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:152)");
                            }
                            final VolumeDialogSliderStateModel volumeDialogSliderStateModel3 = VolumeDialogSliderStateModel.this;
                            SliderIconKt.SliderIcon(ComposableLambdaKt.rememberComposableLambda(131050133, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.2.1
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                    Composer composer4 = (Composer) obj9;
                                    if ((((Number) obj10).intValue() & 17) == 16) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:154)");
                                    }
                                    Icon.Loaded loaded = VolumeDialogSliderStateModel.this.icon;
                                    Dp.Companion companion2 = Dp.Companion;
                                    IconKt.m1072IconFNF3uiM(loaded, SizeKt.m139size3ABfNKs(Modifier.Companion, 20), 0L, composer4, 48, 4);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer3), !((TrackMeasurePolicy) sliderIconsState).isActiveTrackStartIconVisible(), null, composer3, 6);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 905969672 | (intValue & 14), 48, 5364);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(313421367, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$7
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    int i3;
                    SliderState sliderState = (SliderState) obj;
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) obj2;
                    Composer composer2 = (Composer) obj3;
                    int intValue = ((Number) obj4).intValue();
                    if ((intValue & 6) == 0) {
                        i3 = ((intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2) | intValue;
                    } else {
                        i3 = intValue;
                    }
                    if ((intValue & 48) == 0) {
                        i3 |= ((ComposerImpl) composer2).changed(mutableInteractionSource2) ? 32 : 16;
                    }
                    if ((i3 & 147) == 146) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous> (VolumeDialogSliderViewBinder.kt:162)");
                    }
                    SliderDefaults sliderDefaults2 = SliderDefaults.INSTANCE;
                    boolean z2 = !VolumeDialogSliderStateModel.this.isDisabled;
                    Dp.Companion companion2 = Dp.Companion;
                    sliderDefaults2.m296ThumbHwbPF3A(mutableInteractionSource2, sliderState, null, m290copyK518z4, z2, DpKt.m838DpSizeYgX7TsA(52, 4), composer2, ((i3 >> 3) & 14) | 1769536 | ((i3 << 3) & 112), 4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 817889280);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i3 = 1;
            endRestartGroup2.block = new Function2(volumeDialogSliderViewModel, volumeDialogOverscrollViewModel, factory, modifier2, i, i3) { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda0
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ VolumeDialogSliderViewModel f$0;
                public final /* synthetic */ VolumeDialogOverscrollViewModel f$1;
                public final /* synthetic */ SliderHapticsViewModel.Factory f$2;
                public final /* synthetic */ Modifier f$3;

                {
                    this.$r8$classId = i3;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (this.$r8$classId) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                            SliderHapticsViewModel.Factory factory2 = this.f$2;
                            Modifier modifier3 = this.f$3;
                            VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory2, modifier3, (Composer) obj, updateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                            SliderHapticsViewModel.Factory factory3 = this.f$2;
                            Modifier modifier4 = this.f$3;
                            VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory3, modifier4, (Composer) obj, updateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
