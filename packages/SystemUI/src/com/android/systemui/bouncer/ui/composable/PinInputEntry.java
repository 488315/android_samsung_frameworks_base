package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.animation.graphics.res.AnimatedVectorPainterResources_androidKt;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import androidx.compose.foundation.ImageKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.vector.VectorPainter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.ContentScale$Companion$Crop$1;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PinInputEntry {
    public final EntryToken.Digit digit;
    public final Animatable entryWidth;
    public final AnimatedImageVector shape;
    public final ShapeAnimations shapeAnimations;
    public final Animatable shapeSize;

    public PinInputEntry(EntryToken.Digit digit, ShapeAnimations shapeAnimations) {
        this.digit = digit;
        this.shapeAnimations = shapeAnimations;
        int i = digit.sequenceNumber;
        this.shape = shapeAnimations.dotToCircle;
        Dp m835boximpl = Dp.m835boximpl(shapeAnimations.shapeSize);
        Dp.Companion companion = Dp.Companion;
        TwoWayConverter twoWayConverter = VectorConvertersKt.DpToVector;
        this.entryWidth = new Animatable(m835boximpl, twoWayConverter, null, "Width of pin (" + digit + ")", 4, null);
        this.shapeSize = new Animatable(Dp.m835boximpl(shapeAnimations.shapeSize), twoWayConverter, null, "Size of pin (" + digit + ")", 4, null);
    }

    public final void Content(final PinBouncerViewModel pinBouncerViewModel, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(77119564);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pinBouncerViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.PinInputEntry.Content (PinInputDisplay.kt:510)");
            }
            final AnimationState animationState = this.shapeSize.internalState;
            final AnimationState animationState2 = this.entryWidth.internalState;
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.dotColor, composerImpl);
            final float f = this.shapeAnimations.shapeSize;
            composerImpl.startReplaceGroup(1876768384);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1876770414);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new PinInputEntry$Content$1$1(mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
            VectorPainter rememberAnimatedVectorPainter = AnimatedVectorPainterResources_androidKt.rememberAnimatedVectorPainter(this.shape, ((Boolean) mutableState.getValue()).booleanValue(), composerImpl);
            ContentScale.Companion.getClass();
            ContentScale$Companion$Crop$1 contentScale$Companion$Crop$1 = ContentScale.Companion.Crop;
            BlendModeColorFilter m463tintxETnrds$default = ColorFilter.Companion.m463tintxETnrds$default(ColorFilter.Companion, ((Color) collectAsStateWithLifecycle.getValue()).value);
            Modifier.Companion companion = Modifier.Companion;
            composerImpl.startReplaceGroup(1876780325);
            boolean changed = composerImpl.changed(animationState) | composerImpl.changed(animationState2) | composerImpl.changed(f);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function3() { // from class: com.android.systemui.bouncer.ui.composable.PinInputEntry$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        MeasureResult layout$1;
                        final MeasureScope measureScope = (MeasureScope) obj;
                        final State state = animationState;
                        int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(((Dp) state.getValue()).value);
                        Constraints.Companion.getClass();
                        final Placeable mo608measureBRTryo0 = ((Measurable) obj2).mo608measureBRTryo0(Constraints.Companion.m827fixedJhjzzOo(mo51roundToPx0680j_4, mo51roundToPx0680j_4));
                        final State state2 = animationState2;
                        int mo51roundToPx0680j_42 = measureScope.mo51roundToPx0680j_4(((Dp) state2.getValue()).value);
                        final float f2 = f;
                        layout$1 = measureScope.layout$1(mo51roundToPx0680j_42, measureScope.mo51roundToPx0680j_4(f2), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.bouncer.ui.composable.PinInputEntry$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                float f3 = ((Dp) state2.getValue()).value;
                                State state3 = state;
                                float f4 = (f3 - ((Dp) state3.getValue()).value) / 2.0f;
                                MeasureScope measureScope2 = measureScope;
                                ((Placeable.PlacementScope) obj4).place(Placeable.this, measureScope2.mo51roundToPx0680j_4(f4), measureScope2.mo51roundToPx0680j_4((f2 - ((Dp) state3.getValue()).value) / 2.0f), 0.0f);
                                return Unit.INSTANCE;
                            }
                        });
                        return layout$1;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            composerImpl.end(false);
            ImageKt.Image(rememberAnimatedVectorPainter, null, LayoutModifierKt.layout(companion, (Function3) rememberedValue3), null, contentScale$Companion$Crop$1, 0.0f, m463tintxETnrds$default, composerImpl, 24624, 40);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputEntry$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PinInputEntry.this.Content(pinBouncerViewModel, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final boolean isUnused() {
        Animatable animatable = this.entryWidth;
        return Dp.m836equalsimpl0(((Dp) ((SnapshotMutableStateImpl) animatable.targetValue$delegate).getValue()).value, (float) 0) && !animatable.isRunning();
    }
}
