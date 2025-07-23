package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class GestureTutorialScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:61:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void GestureTutorialScreen(final com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig r18, final kotlinx.coroutines.flow.SafeFlow r19, final kotlin.jvm.functions.Function1 r20, final kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r21, final kotlin.jvm.functions.Function0 r22, final kotlin.jvm.functions.Function0 r23, final kotlin.jvm.functions.Function0 r24, kotlin.jvm.functions.Function1 r25, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt.GestureTutorialScreen(com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig, kotlinx.coroutines.flow.SafeFlow, kotlin.jvm.functions.Function1, kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void TouchpadGesturesHandlingBox(final Function1 function1, final TutorialActionState tutorialActionState, final boolean z, final Function0 function0, Modifier.Companion companion, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1617451430);
        if ((i & 384) == 0) {
            i2 = (composerImpl.changed(z) ? 256 : 128) | i;
        } else {
            i2 = i;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 2048 : 1024;
        }
        int i3 = i2 | 24576;
        if ((196608 & i) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl) ? 131072 : 65536;
        }
        if ((74881 & i3) == 74880 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.TouchpadGesturesHandlingBox (GestureTutorialScreen.kt:79)");
            }
            composerImpl.startReplaceGroup(-681068345);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = AnimatableKt.Animatable(0.0f, 0.01f);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final Animatable animatable = (Animatable) rememberedValue;
            composerImpl.end(false);
            Boolean valueOf = Boolean.valueOf(z);
            composerImpl.startReplaceGroup(-681065918);
            boolean changedInstance = ((i3 & 896) == 256) | composerImpl.changedInstance(animatable) | ((i3 & 7168) == 2048);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1(z, animatable, function0, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, valueOf, (Function2) rememberedValue2);
            Modifier fillMaxSize = SizeKt.fillMaxSize(companion2, 1.0f);
            composerImpl.startReplaceGroup(-681032813);
            boolean changedInstance2 = composerImpl.changedInstance(animatable);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function1() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setRotationZ(((Number) Animatable.this.internalState.getValue()).floatValue());
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            composerImpl.end(false);
            Modifier graphicsLayer = GraphicsLayerModifierKt.graphicsLayer(fillMaxSize, (Function1) rememberedValue3);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, graphicsLayer);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function02);
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
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i3 >> 12) & 112) | 6));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    GestureTutorialScreenKt.TouchpadGesturesHandlingBox(Function1.this, tutorialActionState, z, function0, companion2, composableLambdaImpl2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
