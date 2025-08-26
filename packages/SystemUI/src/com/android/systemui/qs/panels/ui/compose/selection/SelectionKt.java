package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.ColorVectorConverterKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.SystemGestureExclusionKt;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.RemoveKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MaterialTheme;
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
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileDefaults;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes2.dex */
public abstract class SelectionKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TileState.values().length];
            try {
                iArr[TileState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TileState.GreyedOut.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TileState.Removable.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TileState.Selected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TileState.Placeable.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02e7 A[PHI: r4
      0x02e7: PHI (r4v15 com.android.systemui.qs.panels.ui.compose.selection.TileState) = 
      (r4v12 com.android.systemui.qs.panels.ui.compose.selection.TileState)
      (r4v16 com.android.systemui.qs.panels.ui.compose.selection.TileState)
     binds: [B:119:0x02e5, B:115:0x02de] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x047d  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x04b0  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0524  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x05da  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0601  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x063d  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0645  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0689  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x068e  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x06c8  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0708  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x070f  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0712  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0739  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x073e  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0741  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0749  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x077c  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0794  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x079b  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x07af  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x081d  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0924  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0136  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void InteractiveTileContainer(final TileState tileState, final ResizingState resizingState, final Modifier modifier, final Function0 function0, final String str, Function3 function3, Composer composer, final int i) {
        long j;
        Composer.Companion companion;
        long j2;
        long j3;
        final TileState tileState2;
        boolean z;
        boolean zChangedInstance;
        Object objRememberedValue;
        Size.Companion companion2;
        TwoWayConverter twoWayConverter;
        StaticProvidableCompositionLocal staticProvidableCompositionLocal;
        int i2;
        Size.Companion companion3;
        TwoWayConverter twoWayConverter2;
        long j4;
        Size sizeM415boximpl;
        int i3;
        long j5;
        Offset.Companion companion4;
        int i4;
        Transition transition;
        Offset.Companion companion5;
        long j6;
        Offset offsetM395boximpl;
        int i5;
        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation;
        ComposerImpl composerImpl;
        boolean z2;
        Modifier modifierLayout;
        boolean zChanged;
        Object objRememberedValue2;
        boolean z3;
        Function3 function32;
        boolean z4;
        long jFloatToRawIntBits;
        int iFloatToRawIntBits;
        long jFloatToRawIntBits2;
        int iFloatToRawIntBits2;
        long jFloatToRawIntBits3;
        int iFloatToRawIntBits3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1535956950);
        int i6 = i | (composerImpl2.changed(tileState) ? 4 : 2) | (composerImpl2.changed(resizingState) ? 32 : 16) | (composerImpl2.changed(modifier) ? 256 : 128) | (composerImpl2.changedInstance(function0) ? 2048 : 1024) | (composerImpl2.changed(str) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((74899 & i6) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            function32 = function3;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer (Selection.kt:110)");
            }
            int i7 = i6 & 14;
            Transition transitionUpdateTransition = TransitionKt.updateTransition(tileState, null, composerImpl2, i7, 2);
            composerImpl2.startReplaceGroup(-984222552);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateColor (Selection.kt:314)");
            }
            SelectionKt$animateColor$$inlined$animateColor$1 selectionKt$animateColor$$inlined$animateColor$1 = SelectionKt$animateColor$$inlined$animateColor$1.INSTANCE;
            SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) transitionUpdateTransition.targetState$delegate;
            TileState tileState3 = (TileState) snapshotMutableStateImpl.getValue();
            composerImpl2.startReplaceGroup(-853557497);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateColor.<anonymous> (Selection.kt:316)");
            }
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            int i8 = iArr[tileState3.ordinal()];
            if (i8 == 1 || i8 == 2) {
                composerImpl2.startReplaceGroup(-861975854);
                composerImpl2.end(false);
                Color.Companion.getClass();
                j = Color.Transparent;
            } else if (i8 == 3) {
                composerImpl2.startReplaceGroup(-861973833);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl2).primaryContainer;
                composerImpl2.end(false);
            } else {
                if (i8 != 4 && i8 != 5) {
                    composerImpl2.startReplaceGroup(-861977679);
                    composerImpl2.end(false);
                    throw new NoWhenBranchMatchedException();
                }
                composerImpl2.startReplaceGroup(-861970962);
                MaterialTheme.INSTANCE.getClass();
                j = MaterialTheme.getColorScheme(composerImpl2).primary;
                composerImpl2.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            ColorSpace colorSpaceM461getColorSpaceimpl = Color.m461getColorSpaceimpl(j);
            boolean zChanged2 = composerImpl2.changed(colorSpaceM461getColorSpaceimpl);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            Composer.Companion companion6 = Composer.Companion;
            if (!zChanged2) {
                companion6.getClass();
                if (objRememberedValue3 == Composer.Companion.Empty) {
                    objRememberedValue3 = (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo781invoke(colorSpaceM461getColorSpaceimpl);
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                }
                TwoWayConverter twoWayConverter3 = (TwoWayConverter) objRememberedValue3;
                TransitionState transitionState = transitionUpdateTransition.transitionState;
                TileState tileState4 = (TileState) transitionState.getCurrentState();
                composerImpl2.startReplaceGroup(-853557497);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateColor.<anonymous> (Selection.kt:316)");
                }
                int i9 = iArr[tileState4.ordinal()];
                if (i9 == 1 || i9 == 2) {
                    companion = companion6;
                    composerImpl2.startReplaceGroup(-861975854);
                    composerImpl2.end(false);
                    Color.Companion.getClass();
                    j2 = Color.Transparent;
                } else if (i9 == 3) {
                    companion = companion6;
                    composerImpl2.startReplaceGroup(-861973833);
                    MaterialTheme.INSTANCE.getClass();
                    j2 = MaterialTheme.getColorScheme(composerImpl2).primaryContainer;
                    composerImpl2.end(false);
                } else {
                    if (i9 != 4 && i9 != 5) {
                        composerImpl2.startReplaceGroup(-861977679);
                        composerImpl2.end(false);
                        throw new NoWhenBranchMatchedException();
                    }
                    composerImpl2.startReplaceGroup(-861970962);
                    MaterialTheme.INSTANCE.getClass();
                    companion = companion6;
                    j2 = MaterialTheme.getColorScheme(composerImpl2).primary;
                    composerImpl2.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                Color colorM456boximpl = Color.m456boximpl(j2);
                TileState tileState5 = (TileState) snapshotMutableStateImpl.getValue();
                composerImpl2.startReplaceGroup(-853557497);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateColor.<anonymous> (Selection.kt:316)");
                }
                int i10 = iArr[tileState5.ordinal()];
                if (i10 == 1 || i10 == 2) {
                    composerImpl2.startReplaceGroup(-861975854);
                    composerImpl2.end(false);
                    Color.Companion.getClass();
                    j3 = Color.Transparent;
                } else if (i10 == 3) {
                    composerImpl2.startReplaceGroup(-861973833);
                    MaterialTheme.INSTANCE.getClass();
                    j3 = MaterialTheme.getColorScheme(composerImpl2).primaryContainer;
                    composerImpl2.end(false);
                } else {
                    if (i10 != 4 && i10 != 5) {
                        composerImpl2.startReplaceGroup(-861977679);
                        composerImpl2.end(false);
                        throw new NoWhenBranchMatchedException();
                    }
                    composerImpl2.startReplaceGroup(-861970962);
                    MaterialTheme.INSTANCE.getClass();
                    j3 = MaterialTheme.getColorScheme(composerImpl2).primary;
                    composerImpl2.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                Color colorM456boximpl2 = Color.m456boximpl(j3);
                FiniteAnimationSpec finiteAnimationSpec = (FiniteAnimationSpec) selectionKt$animateColor$$inlined$animateColor$1.invoke(transitionUpdateTransition.getSegment(), composerImpl2, 0);
                Composer.Companion companion7 = companion;
                final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation2 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, colorM456boximpl, colorM456boximpl2, finiteAnimationSpec, twoWayConverter3, "ColorAnimation", composerImpl2, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(1568070824);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateAngle (Selection.kt:333)");
                }
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, 1040494291, companion7);
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objM == composer$Companion$Empty$1) {
                    objM = AnimatableKt.Animatable(0.0f, 0.01f);
                    composerImpl2.updateRememberedValue(objM);
                }
                Animatable animatable = (Animatable) objM;
                Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1040495802);
                if (objM2 == composer$Companion$Empty$1) {
                    objM2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                    composerImpl2.updateRememberedValue(objM2);
                }
                MutableState mutableState = (MutableState) objM2;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(1040498122);
                if ((i7 ^ 6) > 4) {
                    tileState2 = tileState;
                    if (composerImpl2.changed(tileState2)) {
                        z = true;
                        zChangedInstance = z | composerImpl2.changedInstance(animatable);
                        objRememberedValue = composerImpl2.rememberedValue();
                        if (!zChangedInstance || objRememberedValue == composer$Companion$Empty$1) {
                            objRememberedValue = new SelectionKt$animateAngle$1$1(tileState2, animatable, mutableState, null);
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl2.end(false);
                        EffectsKt.LaunchedEffect(composerImpl2, tileState2, (Function2) objRememberedValue);
                        AnimationState animationState = animatable.internalState;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl2.end(false);
                        composerImpl2.startReplaceGroup(1396876510);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateSize (Selection.kt:350)");
                        }
                        SelectionKt$animateSize$$inlined$animateSize$1 selectionKt$animateSize$$inlined$animateSize$1 = SelectionKt$animateSize$$inlined$animateSize$1.INSTANCE;
                        companion2 = Size.Companion;
                        twoWayConverter = VectorConvertersKt.SizeToVector;
                        TileState tileState6 = (TileState) transitionState.getCurrentState();
                        composerImpl2.startReplaceGroup(-73154045);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateSize.<anonymous> (Selection.kt:352)");
                        }
                        staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                        Density density = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                        i2 = iArr[tileState6.ordinal()];
                        long jFloatToRawIntBits4 = 0;
                        if (i2 != 1 || i2 == 2) {
                            companion3 = companion2;
                            twoWayConverter2 = twoWayConverter;
                            companion3.getClass();
                            j4 = 0;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl2.end(false);
                            sizeM415boximpl = Size.m415boximpl(j4);
                            TileState tileState7 = (TileState) snapshotMutableStateImpl.getValue();
                            composerImpl2.startReplaceGroup(-73154045);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateSize.<anonymous> (Selection.kt:352)");
                            }
                            Density density2 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                            i3 = iArr[tileState7.ordinal()];
                            if (i3 != 1 || i3 == 2) {
                                companion3.getClass();
                                j5 = 0;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl2.end(false);
                                final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation3 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, sizeM415boximpl, Size.m415boximpl(j5), (FiniteAnimationSpec) selectionKt$animateSize$$inlined$animateSize$1.invoke(transitionUpdateTransition.getSegment(), composerImpl2, 0), twoWayConverter2, "SizeAnimation", composerImpl2, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl2.end(false);
                                composerImpl2.startReplaceGroup(1837892620);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateOffset (Selection.kt:365)");
                                }
                                SelectionKt$animateOffset$$inlined$animateOffset$1 selectionKt$animateOffset$$inlined$animateOffset$1 = SelectionKt$animateOffset$$inlined$animateOffset$1.INSTANCE;
                                companion4 = Offset.Companion;
                                TwoWayConverter twoWayConverter4 = VectorConvertersKt.OffsetToVector;
                                TileState tileState8 = (TileState) transitionState.getCurrentState();
                                composerImpl2.startReplaceGroup(-66895951);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateOffset.<anonymous> (Selection.kt:367)");
                                }
                                Density density3 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                i4 = iArr[tileState8.ordinal()];
                                if (i4 != 1 || i4 == 2) {
                                    transition = transitionUpdateTransition;
                                    companion5 = companion4;
                                    companion5.getClass();
                                    j6 = 0;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl2.end(false);
                                    offsetM395boximpl = Offset.m395boximpl(j6);
                                    TileState tileState9 = (TileState) snapshotMutableStateImpl.getValue();
                                    composerImpl2.startReplaceGroup(-66895951);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.animateOffset.<anonymous> (Selection.kt:367)");
                                    }
                                    Density density4 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                    i5 = iArr[tileState9.ordinal()];
                                    if (i5 != 1 || i5 == 2) {
                                        companion5.getClass();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        Transition transition2 = transition;
                                        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation4 = TransitionKt.createTransitionAnimation(transition2, offsetM395boximpl, Offset.m395boximpl(jFloatToRawIntBits4), (FiniteAnimationSpec) selectionKt$animateOffset$$inlined$animateOffset$1.invoke(transition.getSegment(), composerImpl2, 0), twoWayConverter4, "OffsetAnimation", composerImpl2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1 selectionKt$InteractiveTileContainer$$inlined$animateFloat$1 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1.INSTANCE;
                                        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                        TwoWayConverter twoWayConverter5 = VectorConvertersKt.FloatToVector;
                                        TileState tileState10 = (TileState) transitionState.getCurrentState();
                                        composerImpl2.startReplaceGroup(224813371);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:117)");
                                        }
                                        TileState tileState11 = TileState.Removable;
                                        float f = (tileState10 == tileState11 || tileState10 == TileState.Selected) ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        Float fValueOf = Float.valueOf(f);
                                        TileState tileState12 = (TileState) snapshotMutableStateImpl.getValue();
                                        composerImpl2.startReplaceGroup(224813371);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:117)");
                                        }
                                        float f2 = (tileState12 != tileState11 || tileState12 == TileState.Selected) ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation5 = TransitionKt.createTransitionAnimation(transition2, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$1.invoke(transition2.getSegment(), composerImpl2, 0), twoWayConverter5, "FloatAnimation", composerImpl2, 0);
                                        SelectionKt$InteractiveTileContainer$$inlined$animateFloat$2 selectionKt$InteractiveTileContainer$$inlined$animateFloat$2 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$2.INSTANCE;
                                        TileState tileState13 = (TileState) transitionState.getCurrentState();
                                        composerImpl2.startReplaceGroup(686247157);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:118)");
                                        }
                                        float f3 = tileState13 != tileState11 ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        Float fValueOf2 = Float.valueOf(f3);
                                        TileState tileState14 = (TileState) snapshotMutableStateImpl.getValue();
                                        composerImpl2.startReplaceGroup(686247157);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:118)");
                                        }
                                        float f4 = tileState14 != tileState11 ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation6 = TransitionKt.createTransitionAnimation(transition2, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$2.invoke(transition2.getSegment(), composerImpl2, 0), twoWayConverter5, "FloatAnimation", composerImpl2, 0);
                                        SelectionKt$InteractiveTileContainer$$inlined$animateFloat$3 selectionKt$InteractiveTileContainer$$inlined$animateFloat$3 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$3.INSTANCE;
                                        TileState tileState15 = (TileState) transitionState.getCurrentState();
                                        composerImpl2.startReplaceGroup(467800529);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:120)");
                                        }
                                        TileState tileState16 = TileState.Selected;
                                        float f5 = tileState15 != tileState16 ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        Float fValueOf3 = Float.valueOf(f5);
                                        TileState tileState17 = (TileState) snapshotMutableStateImpl.getValue();
                                        composerImpl2.startReplaceGroup(467800529);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous> (Selection.kt:120)");
                                        }
                                        float f6 = tileState17 != tileState16 ? 1.0f : 0.0f;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition2, fValueOf3, Float.valueOf(f6), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$3.invoke(transition2.getSegment(), composerImpl2, 0), twoWayConverter5, "FloatAnimation", composerImpl2, 0);
                                        composerImpl = composerImpl2;
                                        z2 = tileState2 != tileState16;
                                        int i11 = ((i6 >> 6) & 14) | ((i6 << 3) & 896);
                                        composerImpl.startReplaceGroup(1504736047);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.resizable (Selection.kt:277)");
                                        }
                                        if (z2) {
                                            modifierLayout = ZIndexModifierKt.zIndex(modifier, 1.0f);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl.end(false);
                                        } else {
                                            Modifier modifierZIndex = ZIndexModifierKt.zIndex(modifier, 2.0f);
                                            composerImpl.startReplaceGroup(579261618);
                                            boolean z5 = (((i11 & 896) ^ 384) > 256 && composerImpl.changed(resizingState)) || (i11 & 384) == 256;
                                            Object objRememberedValue4 = composerImpl.rememberedValue();
                                            if (z5 || objRememberedValue4 == composer$Companion$Empty$1) {
                                                objRememberedValue4 = new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda10
                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                        MeasureScope measureScope = (MeasureScope) obj;
                                                        Measurable measurable = (Measurable) obj2;
                                                        Constraints constraints = (Constraints) obj3;
                                                        ResizingState resizingState2 = resizingState;
                                                        State stateDerivedStateOf = SnapshotStateKt.derivedStateOf(new SelectionKt$$ExternalSyntheticLambda4(resizingState2, 1));
                                                        Integer numValueOf = Integer.valueOf(MathKt__MathJVMKt.roundToInt(resizingState2.anchoredDraggableState.requireOffset()));
                                                        if (((Boolean) stateDerivedStateOf.getValue()).booleanValue()) {
                                                            numValueOf = null;
                                                        }
                                                        int iIntValue = numValueOf != null ? numValueOf.intValue() : Constraints.m823getMaxWidthimpl(constraints.value);
                                                        Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(constraints.value, iIntValue, iIntValue, 0, 0, 12));
                                                        return measureScope.layout$1(Constraints.m823getMaxWidthimpl(constraints.value), placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new SelectionKt$$ExternalSyntheticLambda14(placeableMo610measureBRTryo0, 0));
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue4);
                                            }
                                            composerImpl.end(false);
                                            modifierLayout = LayoutModifierKt.layout(modifierZIndex, (Function3) objRememberedValue4);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl.end(false);
                                        }
                                        MaterialTheme.INSTANCE.getClass();
                                        final long j7 = MaterialTheme.getColorScheme(composerImpl).primary;
                                        SelectionDefaults.INSTANCE.getClass();
                                        final float f7 = SelectionDefaults.SelectedBorderWidth;
                                        composerImpl.startReplaceGroup(983826115);
                                        zChanged = composerImpl.changed(transitionAnimationStateCreateTransitionAnimation);
                                        objRememberedValue2 = composerImpl.rememberedValue();
                                        if (!zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                                            z3 = false;
                                            final Object[] objArr = null == true ? 1 : 0;
                                            objRememberedValue2 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda3
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (objArr) {
                                                        case 0:
                                                            return Float.valueOf(((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue());
                                                        default:
                                                            return Offset.m395boximpl(((Offset) transitionAnimationStateCreateTransitionAnimation.getValue()).packedValue);
                                                    }
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue2);
                                        } else {
                                            z3 = false;
                                        }
                                        final Function0 function02 = (Function0) objRememberedValue2;
                                        composerImpl.end(z3);
                                        Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(modifierLayout, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda11
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj);
                                                layoutNodeDrawScope.drawContent();
                                                float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(f7);
                                                SolidColor solidColor = new SolidColor(j7, null);
                                                CommonTileDefaults.INSTANCE.getClass();
                                                float fMo58toPx0680j_42 = layoutNodeDrawScope.mo58toPx0680j_4(CommonTileDefaults.InactiveCornerRadius);
                                                long jFloatToRawIntBits5 = (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L);
                                                CornerRadius.Companion companion8 = CornerRadius.Companion;
                                                float f8 = fMo58toPx0680j_4 / 2;
                                                long jFloatToRawIntBits6 = (Float.floatToRawIntBits(f8) << 32) | (Float.floatToRawIntBits(f8) & 4294967295L);
                                                Offset.Companion companion9 = Offset.Companion;
                                                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4;
                                                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L)) - fMo58toPx0680j_4;
                                                Size.Companion companion10 = Size.Companion;
                                                DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope, solidColor, jFloatToRawIntBits6, (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L), jFloatToRawIntBits5, ((Number) function02.invoke()).floatValue(), new Stroke(fMo58toPx0680j_4, 0.0f, 0, 0, null, 30, null), 192);
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierDrawWithContent);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function03 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl.applier != null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl.startReusableNode();
                                        if (composerImpl.inserting) {
                                            composerImpl.createNode(function03);
                                        } else {
                                            composerImpl.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                                        }
                                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        function32 = function3;
                                        function32.invoke(BoxScopeInstance.INSTANCE, composerImpl, 54);
                                        composerImpl.startReplaceGroup(1324021449);
                                        boolean zChanged3 = composerImpl.changed(animationState);
                                        Object objRememberedValue5 = composerImpl.rememberedValue();
                                        if (zChanged3 || objRememberedValue5 == composer$Companion$Empty$1) {
                                            z4 = false;
                                            objRememberedValue5 = new SelectionKt$$ExternalSyntheticLambda4(animationState, 0);
                                            composerImpl.updateRememberedValue(objRememberedValue5);
                                        } else {
                                            z4 = false;
                                        }
                                        Function0 function04 = (Function0) objRememberedValue5;
                                        composerImpl.end(z4);
                                        composerImpl.startReplaceGroup(1324022794);
                                        boolean zChanged4 = composerImpl.changed(transitionAnimationStateCreateTransitionAnimation4);
                                        Object objRememberedValue6 = composerImpl.rememberedValue();
                                        if (zChanged4 || objRememberedValue6 == composer$Companion$Empty$1) {
                                            final int i12 = 1;
                                            objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda3
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i12) {
                                                        case 0:
                                                            return Float.valueOf(((Number) transitionAnimationStateCreateTransitionAnimation4.getValue()).floatValue());
                                                        default:
                                                            return Offset.m395boximpl(((Offset) transitionAnimationStateCreateTransitionAnimation4.getValue()).packedValue);
                                                    }
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue6);
                                        }
                                        composerImpl.end(false);
                                        MinimumInteractiveSizeComponent(function04, (Function0) objRememberedValue6, null, ComposableLambdaKt.rememberComposableLambda(-820809985, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$InteractiveTileContainer$3$3
                                            /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
                                            /* JADX WARN: Removed duplicated region for block: B:20:0x008b  */
                                            /* JADX WARN: Removed duplicated region for block: B:48:0x01dc  */
                                            /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
                                            @Override // kotlin.jvm.functions.Function3
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                Composer composer2 = (Composer) obj2;
                                                if ((((Number) obj3).intValue() & 17) == 16) {
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                    if (composerImpl3.getSkipping()) {
                                                        composerImpl3.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.InteractiveTileContainer.<anonymous>.<anonymous> (Selection.kt:136)");
                                                        }
                                                        Modifier.Companion companion8 = Modifier.Companion;
                                                        Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion8, 1.0f);
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                        composerImpl4.startReplaceGroup(-1206932092);
                                                        final State state = transitionAnimationStateCreateTransitionAnimation2;
                                                        boolean zChanged5 = composerImpl4.changed(state);
                                                        final State state2 = transitionAnimationStateCreateTransitionAnimation3;
                                                        boolean zChanged6 = zChanged5 | composerImpl4.changed(state2);
                                                        Object objRememberedValue7 = composerImpl4.rememberedValue();
                                                        Composer.Companion companion9 = Composer.Companion;
                                                        if (!zChanged6) {
                                                            companion9.getClass();
                                                            if (objRememberedValue7 == Composer.Companion.Empty) {
                                                                objRememberedValue7 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$InteractiveTileContainer$3$3$$ExternalSyntheticLambda0
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj4) {
                                                                        DrawScope drawScope = (DrawScope) obj4;
                                                                        long j8 = ((Color) state.getValue()).value;
                                                                        long jMo546getCenterF1C5BW0 = drawScope.mo546getCenterF1C5BW0();
                                                                        State state3 = state2;
                                                                        long jM402minusMKHz9U = Offset.m402minusMKHz9U(jMo546getCenterF1C5BW0, androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(((Size) state3.getValue()).packedValue));
                                                                        long j9 = ((Size) state3.getValue()).packedValue;
                                                                        float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) / 2;
                                                                        long jFloatToRawIntBits5 = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                                                                        CornerRadius.Companion companion10 = CornerRadius.Companion;
                                                                        DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j8, jM402minusMKHz9U, j9, jFloatToRawIntBits5, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                };
                                                                composerImpl4.updateRememberedValue(objRememberedValue7);
                                                            }
                                                            composerImpl4.end(false);
                                                            Modifier modifierDrawBehind = DrawModifierKt.drawBehind(modifierFillMaxSize, (Function1) objRememberedValue7);
                                                            composerImpl4.startReplaceGroup(-1206920523);
                                                            State state3 = transitionAnimationStateCreateTransitionAnimation5;
                                                            boolean zChanged7 = composerImpl4.changed(state3);
                                                            Object objRememberedValue8 = composerImpl4.rememberedValue();
                                                            if (!zChanged7) {
                                                                companion9.getClass();
                                                                if (objRememberedValue8 == Composer.Companion.Empty) {
                                                                    objRememberedValue8 = new SelectionKt$$ExternalSyntheticLambda14(state3, 1);
                                                                    composerImpl4.updateRememberedValue(objRememberedValue8);
                                                                }
                                                                composerImpl4.end(false);
                                                                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierDrawBehind, (Function1) objRememberedValue8);
                                                                TileState tileState18 = TileState.Selected;
                                                                TileState tileState19 = tileState2;
                                                                Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(AnchoredDraggableKt.anchoredDraggable$default(modifierGraphicsLayer, resizingState.anchoredDraggableState, Orientation.Horizontal, tileState19 == tileState18, 56), null, null, tileState19 != TileState.None, str, null, function0, 16);
                                                                Alignment.Companion.getClass();
                                                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                                                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl4, modifierM34clickableO2vRcR0$default);
                                                                ComposeUiNode.Companion.getClass();
                                                                Function0 function05 = ComposeUiNode.Companion.Constructor;
                                                                if (composerImpl4.applier == null) {
                                                                    ComposablesKt.invalidApplier();
                                                                    throw null;
                                                                }
                                                                composerImpl4.startReusableNode();
                                                                if (composerImpl4.inserting) {
                                                                    composerImpl4.createNode(function05);
                                                                } else {
                                                                    composerImpl4.useNode();
                                                                }
                                                                Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function22);
                                                                }
                                                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                                                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                Density density5 = (Density) composerImpl4.consume(CompositionLocalsKt.LocalDensity);
                                                                SelectionDefaults.INSTANCE.getClass();
                                                                float fMo53toDpGaN1DYA = density5.mo53toDpGaN1DYA(SelectionDefaults.BadgeIconSize);
                                                                Icons.INSTANCE.getClass();
                                                                ImageVector imageVectorBuild = RemoveKt._remove;
                                                                if (imageVectorBuild == null) {
                                                                    Dp.Companion companion10 = Dp.Companion;
                                                                    ImageVector.Builder builder = new ImageVector.Builder("Filled.Remove", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                                                                    EmptyList emptyList = VectorKt.EmptyPath;
                                                                    Color.Companion.getClass();
                                                                    SolidColor solidColor = new SolidColor(Color.Black, null);
                                                                    StrokeCap.Companion.getClass();
                                                                    StrokeJoin.Companion.getClass();
                                                                    int i13 = StrokeJoin.Bevel;
                                                                    PathBuilder pathBuilder = new PathBuilder();
                                                                    pathBuilder.moveTo(19.0f, 13.0f);
                                                                    pathBuilder.horizontalLineTo(5.0f);
                                                                    pathBuilder.verticalLineToRelative(-2.0f);
                                                                    pathBuilder.horizontalLineToRelative(14.0f);
                                                                    pathBuilder.verticalLineToRelative(2.0f);
                                                                    pathBuilder.close();
                                                                    builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i13, 1.0f, 0.0f, 1.0f, 0.0f);
                                                                    imageVectorBuild = builder.build();
                                                                    RemoveKt._remove = imageVectorBuild;
                                                                }
                                                                MaterialTheme.INSTANCE.getClass();
                                                                long j8 = MaterialTheme.getColorScheme(composerImpl4).onPrimaryContainer;
                                                                Modifier modifierAlign = boxScopeInstance.align(SizeKt.m140size3ABfNKs(companion8, fMo53toDpGaN1DYA), Alignment.Companion.Center);
                                                                composerImpl4.startReplaceGroup(63467239);
                                                                State state4 = transitionAnimationStateCreateTransitionAnimation6;
                                                                boolean zChanged8 = composerImpl4.changed(state4);
                                                                Object objRememberedValue9 = composerImpl4.rememberedValue();
                                                                if (!zChanged8) {
                                                                    companion9.getClass();
                                                                    if (objRememberedValue9 == Composer.Companion.Empty) {
                                                                        objRememberedValue9 = new SelectionKt$$ExternalSyntheticLambda14(state4, 2);
                                                                        composerImpl4.updateRememberedValue(objRememberedValue9);
                                                                    }
                                                                    composerImpl4.end(false);
                                                                    IconKt.m271Iconww6aTOc(imageVectorBuild, (String) null, GraphicsLayerModifierKt.graphicsLayer(modifierAlign, (Function1) objRememberedValue9), j8, composerImpl4, 48, 0);
                                                                    composerImpl4.end(true);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl), composerImpl, 3072);
                                        composerImpl.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    } else {
                                        if (i5 == 3) {
                                            SelectionDefaults.INSTANCE.getClass();
                                            jFloatToRawIntBits4 = (Float.floatToRawIntBits(density4.mo58toPx0680j_4(SelectionDefaults.BadgeXOffset)) << 32) | (Float.floatToRawIntBits(density4.mo58toPx0680j_4(SelectionDefaults.BadgeYOffset)) & 4294967295L);
                                        } else if (i5 != 4) {
                                            if (i5 != 5) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            companion5.getClass();
                                        } else {
                                            SelectionDefaults.INSTANCE.getClass();
                                            jFloatToRawIntBits4 = (Float.floatToRawIntBits(-density4.mo58toPx0680j_4(SelectionDefaults.SelectedBorderWidth)) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        composerImpl2.end(false);
                                        Transition transition22 = transition;
                                        final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation42 = TransitionKt.createTransitionAnimation(transition22, offsetM395boximpl, Offset.m395boximpl(jFloatToRawIntBits4), (FiniteAnimationSpec) selectionKt$animateOffset$$inlined$animateOffset$1.invoke(transition.getSegment(), composerImpl2, 0), twoWayConverter4, "OffsetAnimation", composerImpl2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        composerImpl2.end(false);
                                        SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1 selectionKt$InteractiveTileContainer$$inlined$animateFloat$12 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1.INSTANCE;
                                        FloatCompanionObject floatCompanionObject2 = FloatCompanionObject.INSTANCE;
                                        TwoWayConverter twoWayConverter52 = VectorConvertersKt.FloatToVector;
                                        TileState tileState102 = (TileState) transitionState.getCurrentState();
                                        composerImpl2.startReplaceGroup(224813371);
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        TileState tileState112 = TileState.Removable;
                                        if (tileState102 == tileState112) {
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl2.end(false);
                                            Float fValueOf4 = Float.valueOf(f);
                                            TileState tileState122 = (TileState) snapshotMutableStateImpl.getValue();
                                            composerImpl2.startReplaceGroup(224813371);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            if (tileState122 != tileState112) {
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                composerImpl2.end(false);
                                                final State<Float> transitionAnimationStateCreateTransitionAnimation52 = TransitionKt.createTransitionAnimation(transition22, fValueOf4, Float.valueOf(f2), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$12.invoke(transition22.getSegment(), composerImpl2, 0), twoWayConverter52, "FloatAnimation", composerImpl2, 0);
                                                SelectionKt$InteractiveTileContainer$$inlined$animateFloat$2 selectionKt$InteractiveTileContainer$$inlined$animateFloat$22 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$2.INSTANCE;
                                                TileState tileState132 = (TileState) transitionState.getCurrentState();
                                                composerImpl2.startReplaceGroup(686247157);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                if (tileState132 != tileState112) {
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                composerImpl2.end(false);
                                                Float fValueOf22 = Float.valueOf(f3);
                                                TileState tileState142 = (TileState) snapshotMutableStateImpl.getValue();
                                                composerImpl2.startReplaceGroup(686247157);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                if (tileState142 != tileState112) {
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                composerImpl2.end(false);
                                                final State<Float> transitionAnimationStateCreateTransitionAnimation62 = TransitionKt.createTransitionAnimation(transition22, fValueOf22, Float.valueOf(f4), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$22.invoke(transition22.getSegment(), composerImpl2, 0), twoWayConverter52, "FloatAnimation", composerImpl2, 0);
                                                SelectionKt$InteractiveTileContainer$$inlined$animateFloat$3 selectionKt$InteractiveTileContainer$$inlined$animateFloat$32 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$3.INSTANCE;
                                                TileState tileState152 = (TileState) transitionState.getCurrentState();
                                                composerImpl2.startReplaceGroup(467800529);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                TileState tileState162 = TileState.Selected;
                                                if (tileState152 != tileState162) {
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                composerImpl2.end(false);
                                                Float fValueOf32 = Float.valueOf(f5);
                                                TileState tileState172 = (TileState) snapshotMutableStateImpl.getValue();
                                                composerImpl2.startReplaceGroup(467800529);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                if (tileState172 != tileState162) {
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                composerImpl2.end(false);
                                                transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition22, fValueOf32, Float.valueOf(f6), (FiniteAnimationSpec) selectionKt$InteractiveTileContainer$$inlined$animateFloat$32.invoke(transition22.getSegment(), composerImpl2, 0), twoWayConverter52, "FloatAnimation", composerImpl2, 0);
                                                composerImpl = composerImpl2;
                                                if (tileState2 != tileState162) {
                                                }
                                                int i112 = ((i6 >> 6) & 14) | ((i6 << 3) & 896);
                                                composerImpl.startReplaceGroup(1504736047);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                if (z2) {
                                                }
                                                MaterialTheme.INSTANCE.getClass();
                                                final long j72 = MaterialTheme.getColorScheme(composerImpl).primary;
                                                SelectionDefaults.INSTANCE.getClass();
                                                final float f72 = SelectionDefaults.SelectedBorderWidth;
                                                composerImpl.startReplaceGroup(983826115);
                                                zChanged = composerImpl.changed(transitionAnimationStateCreateTransitionAnimation);
                                                objRememberedValue2 = composerImpl.rememberedValue();
                                                if (zChanged) {
                                                    z3 = false;
                                                    final int objArr2 = null == true ? 1 : 0;
                                                    objRememberedValue2 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda3
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            switch (objArr2) {
                                                                case 0:
                                                                    return Float.valueOf(((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue());
                                                                default:
                                                                    return Offset.m395boximpl(((Offset) transitionAnimationStateCreateTransitionAnimation.getValue()).packedValue);
                                                            }
                                                        }
                                                    };
                                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                                    final Function0 function022 = (Function0) objRememberedValue2;
                                                    composerImpl.end(z3);
                                                    Modifier modifierDrawWithContent2 = DrawModifierKt.drawWithContent(modifierLayout, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda11
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj) {
                                                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj);
                                                            layoutNodeDrawScope.drawContent();
                                                            float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(f72);
                                                            SolidColor solidColor = new SolidColor(j72, null);
                                                            CommonTileDefaults.INSTANCE.getClass();
                                                            float fMo58toPx0680j_42 = layoutNodeDrawScope.mo58toPx0680j_4(CommonTileDefaults.InactiveCornerRadius);
                                                            long jFloatToRawIntBits5 = (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L);
                                                            CornerRadius.Companion companion8 = CornerRadius.Companion;
                                                            float f8 = fMo58toPx0680j_4 / 2;
                                                            long jFloatToRawIntBits6 = (Float.floatToRawIntBits(f8) << 32) | (Float.floatToRawIntBits(f8) & 4294967295L);
                                                            Offset.Companion companion9 = Offset.Companion;
                                                            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                            float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - fMo58toPx0680j_4;
                                                            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() & 4294967295L)) - fMo58toPx0680j_4;
                                                            Size.Companion companion10 = Size.Companion;
                                                            DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope, solidColor, jFloatToRawIntBits6, (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L), jFloatToRawIntBits5, ((Number) function022.invoke()).floatValue(), new Stroke(fMo58toPx0680j_4, 0.0f, 0, 0, null, 30, null), 192);
                                                            return Unit.INSTANCE;
                                                        }
                                                    });
                                                    Alignment.Companion.getClass();
                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierDrawWithContent2);
                                                    ComposeUiNode.Companion.getClass();
                                                    Function0 function032 = ComposeUiNode.Companion.Constructor;
                                                    if (composerImpl.applier != null) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (i4 == 3) {
                                        transition = transitionUpdateTransition;
                                        companion5 = companion4;
                                        SelectionDefaults.INSTANCE.getClass();
                                        float fMo58toPx0680j_4 = density3.mo58toPx0680j_4(SelectionDefaults.BadgeXOffset);
                                        float fMo58toPx0680j_42 = density3.mo58toPx0680j_4(SelectionDefaults.BadgeYOffset);
                                        jFloatToRawIntBits = Float.floatToRawIntBits(fMo58toPx0680j_4);
                                        iFloatToRawIntBits = Float.floatToRawIntBits(fMo58toPx0680j_42);
                                    } else if (i4 != 4) {
                                        if (i4 != 5) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        transition = transitionUpdateTransition;
                                        companion5 = companion4;
                                        companion5.getClass();
                                        j6 = 0;
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        composerImpl2.end(false);
                                        offsetM395boximpl = Offset.m395boximpl(j6);
                                        TileState tileState92 = (TileState) snapshotMutableStateImpl.getValue();
                                        composerImpl2.startReplaceGroup(-66895951);
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        Density density42 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                        i5 = iArr[tileState92.ordinal()];
                                        if (i5 != 1) {
                                            companion5.getClass();
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl2.end(false);
                                            Transition transition222 = transition;
                                            final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation422 = TransitionKt.createTransitionAnimation(transition222, offsetM395boximpl, Offset.m395boximpl(jFloatToRawIntBits4), (FiniteAnimationSpec) selectionKt$animateOffset$$inlined$animateOffset$1.invoke(transition.getSegment(), composerImpl2, 0), twoWayConverter4, "OffsetAnimation", composerImpl2, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl2.end(false);
                                            SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1 selectionKt$InteractiveTileContainer$$inlined$animateFloat$122 = SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1.INSTANCE;
                                            FloatCompanionObject floatCompanionObject22 = FloatCompanionObject.INSTANCE;
                                            TwoWayConverter twoWayConverter522 = VectorConvertersKt.FloatToVector;
                                            TileState tileState1022 = (TileState) transitionState.getCurrentState();
                                            composerImpl2.startReplaceGroup(224813371);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            TileState tileState1122 = TileState.Removable;
                                            if (tileState1022 == tileState1122) {
                                            }
                                        }
                                    } else {
                                        SelectionDefaults.INSTANCE.getClass();
                                        transition = transitionUpdateTransition;
                                        companion5 = companion4;
                                        jFloatToRawIntBits = Float.floatToRawIntBits(-density3.mo58toPx0680j_4(SelectionDefaults.SelectedBorderWidth));
                                        iFloatToRawIntBits = Float.floatToRawIntBits(0.0f);
                                    }
                                    j6 = (jFloatToRawIntBits << 32) | (iFloatToRawIntBits & 4294967295L);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl2.end(false);
                                    offsetM395boximpl = Offset.m395boximpl(j6);
                                    TileState tileState922 = (TileState) snapshotMutableStateImpl.getValue();
                                    composerImpl2.startReplaceGroup(-66895951);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    Density density422 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                    i5 = iArr[tileState922.ordinal()];
                                    if (i5 != 1) {
                                    }
                                }
                            } else {
                                if (i3 == 3) {
                                    SelectionDefaults.INSTANCE.getClass();
                                    float fMo58toPx0680j_43 = density2.mo58toPx0680j_4(SelectionDefaults.BadgeSize);
                                    jFloatToRawIntBits2 = Float.floatToRawIntBits(fMo58toPx0680j_43);
                                    iFloatToRawIntBits2 = Float.floatToRawIntBits(fMo58toPx0680j_43);
                                } else if (i3 != 4) {
                                    if (i3 != 5) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    companion3.getClass();
                                    j5 = 0;
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl2.end(false);
                                    final State<Size> transitionAnimationStateCreateTransitionAnimation32 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, sizeM415boximpl, Size.m415boximpl(j5), (FiniteAnimationSpec) selectionKt$animateSize$$inlined$animateSize$1.invoke(transitionUpdateTransition.getSegment(), composerImpl2, 0), twoWayConverter2, "SizeAnimation", composerImpl2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl2.end(false);
                                    composerImpl2.startReplaceGroup(1837892620);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    SelectionKt$animateOffset$$inlined$animateOffset$1 selectionKt$animateOffset$$inlined$animateOffset$12 = SelectionKt$animateOffset$$inlined$animateOffset$1.INSTANCE;
                                    companion4 = Offset.Companion;
                                    TwoWayConverter twoWayConverter42 = VectorConvertersKt.OffsetToVector;
                                    TileState tileState82 = (TileState) transitionState.getCurrentState();
                                    composerImpl2.startReplaceGroup(-66895951);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    Density density32 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                    i4 = iArr[tileState82.ordinal()];
                                    if (i4 != 1) {
                                        transition = transitionUpdateTransition;
                                        companion5 = companion4;
                                        companion5.getClass();
                                        j6 = 0;
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        composerImpl2.end(false);
                                        offsetM395boximpl = Offset.m395boximpl(j6);
                                        TileState tileState9222 = (TileState) snapshotMutableStateImpl.getValue();
                                        composerImpl2.startReplaceGroup(-66895951);
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        Density density4222 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                        i5 = iArr[tileState9222.ordinal()];
                                        if (i5 != 1) {
                                        }
                                    }
                                } else {
                                    SelectionDefaults.INSTANCE.getClass();
                                    float fMo58toPx0680j_44 = density2.mo58toPx0680j_4(SelectionDefaults.ResizingPillWidth);
                                    float fMo58toPx0680j_45 = density2.mo58toPx0680j_4(SelectionDefaults.ResizingPillHeight);
                                    jFloatToRawIntBits2 = Float.floatToRawIntBits(fMo58toPx0680j_44);
                                    iFloatToRawIntBits2 = Float.floatToRawIntBits(fMo58toPx0680j_45);
                                }
                                j5 = (jFloatToRawIntBits2 << 32) | (iFloatToRawIntBits2 & 4294967295L);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl2.end(false);
                                final State<Size> transitionAnimationStateCreateTransitionAnimation322 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, sizeM415boximpl, Size.m415boximpl(j5), (FiniteAnimationSpec) selectionKt$animateSize$$inlined$animateSize$1.invoke(transitionUpdateTransition.getSegment(), composerImpl2, 0), twoWayConverter2, "SizeAnimation", composerImpl2, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl2.end(false);
                                composerImpl2.startReplaceGroup(1837892620);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                SelectionKt$animateOffset$$inlined$animateOffset$1 selectionKt$animateOffset$$inlined$animateOffset$122 = SelectionKt$animateOffset$$inlined$animateOffset$1.INSTANCE;
                                companion4 = Offset.Companion;
                                TwoWayConverter twoWayConverter422 = VectorConvertersKt.OffsetToVector;
                                TileState tileState822 = (TileState) transitionState.getCurrentState();
                                composerImpl2.startReplaceGroup(-66895951);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                Density density322 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                i4 = iArr[tileState822.ordinal()];
                                if (i4 != 1) {
                                }
                            }
                        } else {
                            if (i2 == 3) {
                                companion3 = companion2;
                                twoWayConverter2 = twoWayConverter;
                                SelectionDefaults.INSTANCE.getClass();
                                float fMo58toPx0680j_46 = density.mo58toPx0680j_4(SelectionDefaults.BadgeSize);
                                jFloatToRawIntBits3 = Float.floatToRawIntBits(fMo58toPx0680j_46);
                                iFloatToRawIntBits3 = Float.floatToRawIntBits(fMo58toPx0680j_46);
                            } else if (i2 != 4) {
                                if (i2 != 5) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                companion3 = companion2;
                                twoWayConverter2 = twoWayConverter;
                                companion3.getClass();
                                j4 = 0;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl2.end(false);
                                sizeM415boximpl = Size.m415boximpl(j4);
                                TileState tileState72 = (TileState) snapshotMutableStateImpl.getValue();
                                composerImpl2.startReplaceGroup(-73154045);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                Density density22 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                i3 = iArr[tileState72.ordinal()];
                                if (i3 != 1) {
                                    companion3.getClass();
                                    j5 = 0;
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl2.end(false);
                                    final State<Size> transitionAnimationStateCreateTransitionAnimation3222 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, sizeM415boximpl, Size.m415boximpl(j5), (FiniteAnimationSpec) selectionKt$animateSize$$inlined$animateSize$1.invoke(transitionUpdateTransition.getSegment(), composerImpl2, 0), twoWayConverter2, "SizeAnimation", composerImpl2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl2.end(false);
                                    composerImpl2.startReplaceGroup(1837892620);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    SelectionKt$animateOffset$$inlined$animateOffset$1 selectionKt$animateOffset$$inlined$animateOffset$1222 = SelectionKt$animateOffset$$inlined$animateOffset$1.INSTANCE;
                                    companion4 = Offset.Companion;
                                    TwoWayConverter twoWayConverter4222 = VectorConvertersKt.OffsetToVector;
                                    TileState tileState8222 = (TileState) transitionState.getCurrentState();
                                    composerImpl2.startReplaceGroup(-66895951);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    Density density3222 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                                    i4 = iArr[tileState8222.ordinal()];
                                    if (i4 != 1) {
                                    }
                                }
                            } else {
                                SelectionDefaults.INSTANCE.getClass();
                                float fMo58toPx0680j_47 = density.mo58toPx0680j_4(SelectionDefaults.ResizingPillWidth);
                                float fMo58toPx0680j_48 = density.mo58toPx0680j_4(SelectionDefaults.ResizingPillHeight);
                                companion3 = companion2;
                                twoWayConverter2 = twoWayConverter;
                                jFloatToRawIntBits3 = Float.floatToRawIntBits(fMo58toPx0680j_47);
                                iFloatToRawIntBits3 = Float.floatToRawIntBits(fMo58toPx0680j_48);
                            }
                            j4 = (jFloatToRawIntBits3 << 32) | (iFloatToRawIntBits3 & 4294967295L);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl2.end(false);
                            sizeM415boximpl = Size.m415boximpl(j4);
                            TileState tileState722 = (TileState) snapshotMutableStateImpl.getValue();
                            composerImpl2.startReplaceGroup(-73154045);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            Density density222 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                            i3 = iArr[tileState722.ordinal()];
                            if (i3 != 1) {
                            }
                        }
                    }
                } else {
                    tileState2 = tileState;
                }
                if ((i6 & 6) != 4) {
                    z = false;
                }
                zChangedInstance = z | composerImpl2.changedInstance(animatable);
                objRememberedValue = composerImpl2.rememberedValue();
                if (!zChangedInstance) {
                    objRememberedValue = new SelectionKt$animateAngle$1$1(tileState2, animatable, mutableState, null);
                    composerImpl2.updateRememberedValue(objRememberedValue);
                    composerImpl2.end(false);
                    EffectsKt.LaunchedEffect(composerImpl2, tileState2, (Function2) objRememberedValue);
                    AnimationState animationState2 = animatable.internalState;
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(1396876510);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    SelectionKt$animateSize$$inlined$animateSize$1 selectionKt$animateSize$$inlined$animateSize$12 = SelectionKt$animateSize$$inlined$animateSize$1.INSTANCE;
                    companion2 = Size.Companion;
                    twoWayConverter = VectorConvertersKt.SizeToVector;
                    TileState tileState62 = (TileState) transitionState.getCurrentState();
                    composerImpl2.startReplaceGroup(-73154045);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                    Density density5 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                    i2 = iArr[tileState62.ordinal()];
                    long jFloatToRawIntBits42 = 0;
                    if (i2 != 1) {
                        companion3 = companion2;
                        twoWayConverter2 = twoWayConverter;
                        companion3.getClass();
                        j4 = 0;
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        composerImpl2.end(false);
                        sizeM415boximpl = Size.m415boximpl(j4);
                        TileState tileState7222 = (TileState) snapshotMutableStateImpl.getValue();
                        composerImpl2.startReplaceGroup(-73154045);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        Density density2222 = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                        i3 = iArr[tileState7222.ordinal()];
                        if (i3 != 1) {
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function3 function33 = function32;
            recomposeScopeImplEndRestartGroup.block = new Function2(resizingState, modifier, function0, str, function33, i) { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda6
                public final /* synthetic */ ResizingState f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ Function0 f$3;
                public final /* synthetic */ String f$4;
                public final /* synthetic */ Function3 f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196609);
                    Function3 function34 = this.f$5;
                    SelectionKt.InteractiveTileContainer(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, function34, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void MinimumInteractiveSizeComponent(final Function0 function0, final Function0 function02, Modifier modifier, Function3 function3, Composer composer, final int i) {
        int i2;
        Function3 function32;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1659916325);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        } else {
            i2 = i;
        }
        int i3 = i2 | (composerImpl.changedInstance(function02) ? 32 : 16) | 384;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
            function32 = function3;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.MinimumInteractiveSizeComponent (Selection.kt:249)");
            }
            final float f = ((Dp) composerImpl.consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value;
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.Center;
            Modifier modifierZIndex = ZIndexModifierKt.zIndex(companion, 2.0f);
            composerImpl.startReplaceGroup(-774422871);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new SelectionKt$$ExternalSyntheticLambda7();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierSystemGestureExclusion = SystemGestureExclusionKt.systemGestureExclusion(modifierZIndex, (Function1) objRememberedValue);
            composerImpl.startReplaceGroup(-774420111);
            boolean zChanged = ((i3 & 112) == 32) | ((i3 & 14) == 4) | composerImpl.changed(f);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        MeasureScope measureScope = (MeasureScope) obj;
                        final Constraints constraints = (Constraints) obj3;
                        int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(f);
                        Constraints.Companion.getClass();
                        final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj2).mo610measureBRTryo0(Constraints.Companion.m829fixedJhjzzOo(iMo52roundToPx0680j_4, iMo52roundToPx0680j_4));
                        int i4 = placeableMo610measureBRTryo0.width;
                        int i5 = placeableMo610measureBRTryo0.height;
                        final Function0 function03 = function0;
                        final Function0 function04 = function02;
                        return measureScope.layout$1(i4, i5, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj4) {
                                Constraints constraints2 = constraints;
                                float fM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(constraints2.value) / 2.0f;
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(Constraints.m823getMaxWidthimpl(constraints2.value) - fM822getMaxHeightimpl) << 32) | (Float.floatToRawIntBits(fM822getMaxHeightimpl) & 4294967295L);
                                Offset.Companion companion2 = Offset.Companion;
                                double dFloatValue = ((Number) function03.invoke()).floatValue();
                                float fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) + (((float) Math.cos(dFloatValue)) * fM822getMaxHeightimpl);
                                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L)) + (fM822getMaxHeightimpl * ((float) Math.sin(dFloatValue)));
                                long jM403plusMKHz9U = Offset.m403plusMKHz9U((Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32), ((Offset) function04.invoke()).packedValue);
                                int iRoundToInt = MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jM403plusMKHz9U >> 32)));
                                Placeable placeable = placeableMo610measureBRTryo0;
                                ((Placeable.PlacementScope) obj4).place(placeable, iRoundToInt - (placeable.width / 2), MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jM403plusMKHz9U & 4294967295L))) - (placeable.height / 2), 0.0f);
                                return Unit.INSTANCE;
                            }
                        });
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            Modifier modifierLayout = LayoutModifierKt.layout(modifierSystemGestureExclusion, (Function3) objRememberedValue2);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierLayout);
            ComposeUiNode.Companion.getClass();
            Function0 function03 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function03);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            function32 = function3;
            function32.invoke(BoxScopeInstance.INSTANCE, composerImpl, 54);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function3 function33 = function32;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function3 function34 = function33;
                    SelectionKt.MinimumInteractiveSizeComponent(function0, function02, modifier2, function34, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StaticTileBadge(final ImageVector imageVector, final String str, final boolean z, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(282664700);
        if (((i | (composerImpl.changed(imageVector) ? 4 : 2) | (composerImpl.changed(str) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changedInstance(function0) ? 2048 : 1024)) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.StaticTileBadge (Selection.kt:212)");
            }
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            SelectionDefaults.INSTANCE.getClass();
            final long jFloatToRawIntBits = (Float.floatToRawIntBits(density.mo58toPx0680j_4(SelectionDefaults.BadgeXOffset)) << 32) | (Float.floatToRawIntBits(density.mo58toPx0680j_4(SelectionDefaults.BadgeYOffset)) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, null, null, null, composerImpl, 0, 30);
            composerImpl.startReplaceGroup(-566674700);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new SelectionKt$$ExternalSyntheticLambda0();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            Function0 function02 = (Function0) objRememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-566673749);
            boolean zChanged = composerImpl.changed(jFloatToRawIntBits);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Offset.m395boximpl(jFloatToRawIntBits);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            MinimumInteractiveSizeComponent(function02, (Function0) objRememberedValue2, null, ComposableLambdaKt.rememberComposableLambda(-1542411233, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt.StaticTileBadge.3
                /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
                /* JADX WARN: Removed duplicated region for block: B:34:0x0115  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.StaticTileBadge.<anonymous> (Selection.kt:216)");
                            }
                            Modifier.Companion companion2 = Modifier.Companion;
                            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion2, 1.0f);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(573641430);
                            State state = stateAnimateFloatAsState;
                            boolean zChanged2 = composerImpl3.changed(state);
                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                            Composer.Companion companion3 = Composer.Companion;
                            if (!zChanged2) {
                                companion3.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    objRememberedValue3 = new SelectionKt$$ExternalSyntheticLambda14(state, 3);
                                    composerImpl3.updateRememberedValue(objRememberedValue3);
                                }
                                composerImpl3.end(false);
                                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxSize, (Function1) objRememberedValue3);
                                if (z) {
                                    modifierGraphicsLayer = modifierGraphicsLayer.then(ClickableKt.m34clickableO2vRcR0$default(companion2, null, null, false, str, null, function0, 20));
                                }
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierGraphicsLayer);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function03);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                Density density2 = (Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity);
                                SelectionDefaults.INSTANCE.getClass();
                                float fMo53toDpGaN1DYA = density2.mo53toDpGaN1DYA(SelectionDefaults.BadgeIconSize);
                                MaterialTheme.INSTANCE.getClass();
                                final long j = MaterialTheme.getColorScheme(composerImpl3).primary;
                                long j2 = MaterialTheme.getColorScheme(composerImpl3).onPrimary;
                                Modifier modifierAlign = boxScopeInstance.align(SizeKt.m140size3ABfNKs(companion2, fMo53toDpGaN1DYA), Alignment.Companion.Center);
                                composerImpl3.startReplaceGroup(-608341466);
                                boolean zChanged3 = composerImpl3.changed(j);
                                Object objRememberedValue4 = composerImpl3.rememberedValue();
                                if (!zChanged3) {
                                    companion3.getClass();
                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                        objRememberedValue4 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$StaticTileBadge$3$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                DrawScope drawScope = (DrawScope) obj4;
                                                SelectionDefaults.INSTANCE.getClass();
                                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j, drawScope.mo58toPx0680j_4(SelectionDefaults.BadgeSize) / 2, 0L, 0.0f, null, 0, 124);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue4);
                                    }
                                    composerImpl3.end(false);
                                    IconKt.m271Iconww6aTOc(imageVector, str, DrawModifierKt.drawBehind(modifierAlign, (Function1) objRememberedValue4), j2, composerImpl3, 0, 0);
                                    composerImpl3.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 3078);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, z, function0, i) { // from class: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$$ExternalSyntheticLambda2
                public final /* synthetic */ String f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Function0 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ImageVector imageVector2 = this.f$0;
                    boolean z2 = this.f$2;
                    Function0 function03 = this.f$3;
                    SelectionKt.StaticTileBadge(imageVector2, this.f$1, z2, function03, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
