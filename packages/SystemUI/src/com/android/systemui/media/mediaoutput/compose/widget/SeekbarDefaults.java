package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SeekbarDefaults {
    public static final SeekbarDefaults INSTANCE = new SeekbarDefaults();
    public static final float thumbSize;

    static {
        Dp.Companion companion = Dp.Companion;
        thumbSize = 13;
    }

    private SeekbarDefaults() {
    }

    public final void SliderContainer(final MutableState mutableState, final Modifier modifier, final MutableState mutableState2, final Function1 function1, LabsViewModel labsViewModel, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        final MutableState mutableState3;
        int i3;
        Object failure;
        LabsViewModel labsViewModel2;
        int i4;
        boolean z;
        final LabsViewModel labsViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1421658324);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(mutableState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            mutableState3 = mutableState2;
            i2 |= composerImpl.changed(mutableState3) ? 256 : 128;
        } else {
            mutableState3 = mutableState2;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 131072 : 65536;
        }
        int i5 = i2;
        if ((74899 & i5) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            labsViewModel3 = labsViewModel;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i6 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    i3 = -57345;
                } catch (Throwable th) {
                    int i7 = Result.$r8$clinit;
                    i3 = -57345;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel;
                i4 = i5 & i3;
            } else {
                composerImpl.skipToGroupEnd();
                i4 = i5 & (-57345);
                labsViewModel2 = labsViewModel;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.SliderContainer (Controllers.kt:311)");
            }
            boolean z2 = composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl;
            Modifier.Companion companion = Modifier.Companion;
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composableLambdaImpl.invoke(function1, composerImpl, Integer.valueOf(((i4 >> 9) & 14) | ((i4 >> 12) & 112)));
            final boolean z3 = z2;
            MutableState collectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportVolumeInteraction, Boolean.TRUE, null, composerImpl, 48, 2);
            final SliderState sliderState = (SliderState) mutableState.getValue();
            if (sliderState == null || !((Boolean) collectAsState.getValue()).booleanValue()) {
                sliderState = null;
            }
            composerImpl.startReplaceGroup(1936250156);
            if (sliderState != null) {
                composerImpl.startReplaceGroup(1101423168);
                Object rememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (rememberedValue == composer$Companion$Empty$1) {
                    rememberedValue = SnapshotStateKt.mutableStateOf$default(Float.valueOf(1.0f));
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                final MutableState mutableState4 = (MutableState) rememberedValue;
                composerImpl.end(false);
                Modifier fillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
                composerImpl.startReplaceGroup(1101429232);
                boolean changedInstance = composerImpl.changedInstance(sliderState);
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
                            float f = (int) (((IntSize) obj).packedValue >> 32);
                            ClosedFloatingPointRange closedFloatingPointRange = SliderState.this.valueRange;
                            mutableState4.setValue(Float.valueOf(f / (((ClosedFloatRange) closedFloatingPointRange)._endInclusive - ((ClosedFloatRange) closedFloatingPointRange)._start)));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                composerImpl.end(false);
                Modifier onSizeChanged = OnRemeasuredModifierKt.onSizeChanged(fillMaxSize, (Function1) rememberedValue2);
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                Modifier focusable$default = FocusableKt.focusable$default(onSizeChanged.then(MinimumInteractiveModifier.INSTANCE), false, null, 2);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1101438313);
                boolean changedInstance2 = composerImpl.changedInstance(sliderState) | ((i4 & 896) == 256) | composerImpl.changed(z3) | ((i4 & 7168) == 2048);
                Object rememberedValue3 = composerImpl.rememberedValue();
                if (changedInstance2 || rememberedValue3 == composer$Companion$Empty$1) {
                    final SliderState sliderState2 = sliderState;
                    rememberedValue3 = new PointerInputEventHandler() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2$1
                        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                            ref$FloatRef.element = SliderState.this.getValue();
                            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
                            ref$FloatRef2.element = ref$FloatRef.element;
                            final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
                            final SliderState sliderState3 = SliderState.this;
                            final MutableState mutableState5 = mutableState3;
                            Function1 function12 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj) {
                                    Ref$FloatRef.this.element = Float.intBitsToFloat((int) (((Offset) obj).packedValue >> 32));
                                    ref$FloatRef.element = sliderState3.getValue();
                                    mutableState5.setValue(Boolean.TRUE);
                                    return Unit.INSTANCE;
                                }
                            };
                            final int i8 = 0;
                            Function0 function02 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2$1$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i8) {
                                        case 0:
                                            Function0 function03 = sliderState3.onValueChangeFinished;
                                            if (function03 != null) {
                                                function03.invoke();
                                            }
                                            mutableState5.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            Function0 function04 = sliderState3.onValueChangeFinished;
                                            if (function04 != null) {
                                                function04.invoke();
                                            }
                                            mutableState5.setValue(Boolean.FALSE);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            final int i9 = 1;
                            Function0 function03 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2$1$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i9) {
                                        case 0:
                                            Function0 function032 = sliderState3.onValueChangeFinished;
                                            if (function032 != null) {
                                                function032.invoke();
                                            }
                                            mutableState5.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            Function0 function04 = sliderState3.onValueChangeFinished;
                                            if (function04 != null) {
                                                function04.invoke();
                                            }
                                            mutableState5.setValue(Boolean.FALSE);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            final Function1 function13 = function1;
                            final MutableState mutableState6 = mutableState4;
                            final boolean z4 = z3;
                            Object detectHorizontalDragGestures = DragGestureDetectorKt.detectHorizontalDragGestures(pointerInputScope, function12, function02, function03, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2$1$$ExternalSyntheticLambda3
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    float intBitsToFloat;
                                    float f;
                                    PointerInputChange pointerInputChange = (PointerInputChange) obj;
                                    ((Float) obj2).getClass();
                                    pointerInputChange.consume();
                                    Ref$FloatRef ref$FloatRef4 = ref$FloatRef3;
                                    boolean z5 = z4;
                                    long j = pointerInputChange.position;
                                    if (z5) {
                                        intBitsToFloat = ref$FloatRef4.element;
                                        f = Float.intBitsToFloat((int) (j >> 32));
                                    } else {
                                        intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
                                        f = ref$FloatRef4.element;
                                    }
                                    float f2 = intBitsToFloat - f;
                                    float f3 = ref$FloatRef.element;
                                    SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
                                    float floatValue = ((Number) RangesKt___RangesKt.coerceIn(Float.valueOf((f2 / ((Number) mutableState6.getValue()).floatValue()) + f3), sliderState3.valueRange)).floatValue();
                                    Ref$FloatRef ref$FloatRef5 = ref$FloatRef2;
                                    if (ref$FloatRef5.element != floatValue) {
                                        ref$FloatRef5.element = floatValue;
                                        function13.mo779invoke(Float.valueOf(floatValue));
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, continuation);
                            return detectHorizontalDragGestures == CoroutineSingletons.COROUTINE_SUSPENDED ? detectHorizontalDragGestures : Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue3);
                }
                z = false;
                composerImpl.end(false);
                BoxKt.Box(SuspendingPointerInputFilterKt.pointerInput(focusable$default, unit, (PointerInputEventHandler) rememberedValue3), composerImpl, 0);
            } else {
                z = false;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, z, true)) {
                ComposerKt.traceEventEnd();
            }
            labsViewModel3 = labsViewModel2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeekbarDefaults.this.SliderContainer(mutableState, modifier, mutableState2, function1, labsViewModel3, composableLambdaImpl, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: Thumb-FJfuzF0, reason: not valid java name */
    public final void m2619ThumbFJfuzF0(State state, Modifier modifier, final SliderColors sliderColors, boolean z, float f, Composer composer, final int i) {
        final boolean z2;
        State state2;
        Modifier modifier2;
        float f2;
        final boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1328514551);
        if (((i | (composerImpl.changed(sliderColors) ? 256 : 128) | 3072) & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            state2 = state;
            modifier2 = modifier;
            z3 = z;
            f2 = f;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                z2 = true;
            } else {
                composerImpl.skipToGroupEnd();
                z2 = z;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.Thumb (Controllers.kt:234)");
            }
            composerImpl.startReplaceGroup(1752684879);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                state2 = state;
                composerImpl.updateRememberedValue(state2);
                rememberedValue = state2;
            } else {
                state2 = state;
            }
            State state3 = (State) rememberedValue;
            composerImpl.end(false);
            modifier2 = modifier;
            f2 = f;
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(modifier2, f2);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m139size3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            AnimatedVisibilityKt.AnimatedVisibility(!((Boolean) state3.getValue()).booleanValue(), null, EnterExitTransitionKt.fadeIn$default(null, 3).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(null, 0.0f, 7)), EnterExitTransitionKt.m6scaleOutL8ZKhE$default(null, 0.0f, 7).plus(EnterExitTransitionKt.fadeOut$default(null, 3)), null, ComposableLambdaKt.rememberComposableLambda(135604711, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$Thumb$1$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.Thumb.<anonymous>.<anonymous> (Controllers.kt:243)");
                    }
                    Modifier fillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                    float f3 = 2;
                    Dp.Companion companion = Dp.Companion;
                    SliderColors sliderColors2 = SliderColors.this;
                    long j = sliderColors2.activeTrackColor;
                    RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                    Modifier clip = ClipKt.clip(PaddingKt.m124padding3ABfNKs(BorderKt.m28borderxT4_qwU(fillMaxSize, f3, j, roundedCornerShape), f3), roundedCornerShape);
                    SeekbarDefaults.INSTANCE.getClass();
                    SpacerKt.Spacer(composer2, BackgroundKt.m26backgroundbw27NRU(clip, z2 ? sliderColors2.thumbColor : sliderColors2.disabledThumbColor, RectangleShapeKt.RectangleShape));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 200064, 18);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z3 = z2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final State state4 = state2;
            final Modifier modifier3 = modifier2;
            final float f3 = f2;
            endRestartGroup.block = new Function2(state4, modifier3, sliderColors, z3, f3, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$$ExternalSyntheticLambda1
                public final /* synthetic */ State f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ SliderColors f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ float f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(221239);
                    SeekbarDefaults.this.m2619ThumbFJfuzF0(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void Track(final SliderState sliderState, final State state, Modifier.Companion companion, final SliderColors sliderColors, Composer composer, final int i) {
        int i2;
        SeekbarDefaults seekbarDefaults;
        Modifier.Companion companion2;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1064132950);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(sliderState) : composerImpl.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(state) ? 32 : 16;
        }
        int i3 = i2 | 384;
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changed(sliderColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            seekbarDefaults = this;
            i3 |= composerImpl.changed(seekbarDefaults) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            seekbarDefaults = this;
        }
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                companion2 = Modifier.Companion;
            } else {
                composerImpl.skipToGroupEnd();
                companion2 = companion;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.Track (Controllers.kt:262)");
            }
            composerImpl.startReplaceGroup(2020247279);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Float.valueOf(1.5f));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            float floatValue = ((Number) mutableState.getValue()).floatValue();
            Dp.Companion companion3 = Dp.Companion;
            State m8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(floatValue, null, "trackRound", composerImpl, 384, 10);
            composerImpl.startReplaceGroup(2020252461);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = SnapshotStateKt.mutableStateOf$default(Float.valueOf(3.0f));
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            MutableState mutableState2 = (MutableState) rememberedValue2;
            composerImpl.end(false);
            State m8animateDpAsStateAjpBEmI2 = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(((Number) mutableState2.getValue()).floatValue(), null, "trackHeight", composerImpl, 384, 10);
            composerImpl.startReplaceGroup(2020257668);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                composerImpl.updateRememberedValue(state);
                rememberedValue3 = state;
            }
            State state2 = (State) rememberedValue3;
            composerImpl.end(false);
            Boolean bool = (Boolean) state2.getValue();
            bool.getClass();
            composerImpl.startReplaceGroup(2020259640);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = new SeekbarDefaults$Track$1$1(state2, mutableState2, mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, bool, (Function2) rememberedValue4);
            Modifier clip = ClipKt.clip(SizeKt.m130height3ABfNKs(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), ((Dp) m8animateDpAsStateAjpBEmI2.getValue()).value), RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(((Dp) m8animateDpAsStateAjpBEmI.getValue()).value));
            Arrangement.INSTANCE.getClass();
            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, clip);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            float f = ((ClosedFloatRange) sliderState.valueRange)._endInclusive;
            ClosedFloatRange closedFloatRange = (ClosedFloatRange) sliderState.valueRange;
            float f2 = f - closedFloatRange._start;
            float value = sliderState.getValue() - closedFloatRange._start;
            composerImpl.startReplaceGroup(995860695);
            if (value > 0.0f) {
                FillElement fillElement = SizeKt.FillWholeMaxHeight;
                companion2.getClass();
                SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(rowScopeInstance.weight(fillElement, value, true), sliderColors.activeTrackColor, RectangleShapeKt.RectangleShape));
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(995869641);
            float f3 = f2 - value;
            if (f3 > 0.0f) {
                z = true;
                SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(rowScopeInstance.weight(SizeKt.FillWholeMaxHeight, f3, true), sliderColors.inactiveTrackColor, RectangleShapeKt.RectangleShape));
            } else {
                z = true;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final SeekbarDefaults seekbarDefaults2 = seekbarDefaults;
            final Modifier.Companion companion4 = companion2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeekbarDefaults seekbarDefaults3 = SeekbarDefaults.INSTANCE;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeekbarDefaults.this.Track(sliderState, state, companion4, sliderColors, composer2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
