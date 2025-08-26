package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.activity.compose.BackHandlerKt;
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
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.MapSaverKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.inputdevice.tutorial.ui.composable.ActionTutorialContentKt;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState$Companion$$ExternalSyntheticLambda1;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public abstract class GestureTutorialScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void GestureTutorialScreen(final TutorialScreenConfig tutorialScreenConfig, final SafeFlow safeFlow, final Function1 function1, final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, final Function0 function0, final Function0 function02, final Function0 function03, Function1 function12, Composer composer, final int i, final int i2) {
        int i3;
        Function0 function04;
        Function1 function13;
        Object objRememberedValue;
        ComposerImpl composerImpl;
        final Function1 function14;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-437585763);
        if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl2.changed(tutorialScreenConfig) : composerImpl2.changedInstance(tutorialScreenConfig) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changedInstance(safeFlow) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changedInstance(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            function04 = function0;
            i3 |= composerImpl2.changedInstance(function04) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            function04 = function0;
        }
        if ((i & 196608) == 0) {
            i3 |= composerImpl2.changedInstance(function02) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function03) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i4 = i2 & 128;
        if (i4 == 0) {
            if ((12582912 & i) == 0) {
                function13 = function12;
                i3 |= composerImpl2.changedInstance(function13) ? 8388608 : 4194304;
            }
            if ((4793491 & i3) == 4793490 || !composerImpl2.getSkipping()) {
                final Function1 function15 = i4 == 0 ? null : function13;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreen (GestureTutorialScreen.kt:52)");
                }
                BackHandlerKt.BackHandler(false, function03, composerImpl2, (i3 >> 15) & 112, 1);
                Object[] objArr = new Object[0];
                TutorialActionState.Companion.getClass();
                SaverKt$Saver$1 saverKt$Saver$1MapSaver = MapSaverKt.mapSaver(new TutorialActionState$Companion$$ExternalSyntheticLambda0(), new TutorialActionState$Companion$$ExternalSyntheticLambda1());
                composerImpl2.startReplaceGroup(-15183031);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new GestureTutorialScreenKt$$ExternalSyntheticLambda0();
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                MutableState mutableStateRememberSaveable = RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1MapSaver, (Function0) objRememberedValue, composerImpl2);
                MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, Boolean.FALSE, composerImpl2, ((i3 >> 9) & 14) | 48);
                int i5 = i3 >> 3;
                int i6 = i3;
                final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(safeFlow, (TutorialActionState) mutableStateRememberSaveable.getValue(), composerImpl2, i5 & 14);
                mutableStateRememberSaveable.setValue((TutorialActionState) mutableStateCollectAsStateWithLifecycle2.getValue());
                TutorialActionState tutorialActionState = (TutorialActionState) mutableStateCollectAsStateWithLifecycle2.getValue();
                boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
                ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(1018742334, new Function3() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt.GestureTutorialScreen.1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreen.<anonymous> (GestureTutorialScreen.kt:67)");
                                }
                                ActionTutorialContentKt.ActionTutorialContent((TutorialActionState) mutableStateCollectAsStateWithLifecycle2.getValue(), function02, tutorialScreenConfig, function15, composer2, 512, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2);
                int i7 = ((i6 >> 6) & 14) | 196608 | (i5 & 7168);
                composerImpl = composerImpl2;
                Function1 function16 = function15;
                TouchpadGesturesHandlingBox(function1, tutorialActionState, zBooleanValue, function04, null, composableLambdaImplRememberComposableLambda, composerImpl, i7);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                function14 = function16;
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
                function14 = function13;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        TutorialScreenConfig tutorialScreenConfig2 = tutorialScreenConfig;
                        Function1 function17 = function14;
                        GestureTutorialScreenKt.GestureTutorialScreen(tutorialScreenConfig2, safeFlow, function1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, function0, function02, function03, function17, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 12582912;
        function13 = function12;
        if ((4793491 & i3) == 4793490) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            BackHandlerKt.BackHandler(false, function03, composerImpl2, (i3 >> 15) & 112, 1);
            Object[] objArr2 = new Object[0];
            TutorialActionState.Companion.getClass();
            SaverKt$Saver$1 saverKt$Saver$1MapSaver2 = MapSaverKt.mapSaver(new TutorialActionState$Companion$$ExternalSyntheticLambda0(), new TutorialActionState$Companion$$ExternalSyntheticLambda1());
            composerImpl2.startReplaceGroup(-15183031);
            objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
            }
            composerImpl2.end(false);
            MutableState mutableStateRememberSaveable2 = RememberSaveableKt.rememberSaveable(objArr2, saverKt$Saver$1MapSaver2, (Function0) objRememberedValue, composerImpl2);
            MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, Boolean.FALSE, composerImpl2, ((i3 >> 9) & 14) | 48);
            int i52 = i3 >> 3;
            int i62 = i3;
            final State<? extends TutorialActionState> mutableStateCollectAsStateWithLifecycle22 = FlowExtKt.collectAsStateWithLifecycle(safeFlow, (TutorialActionState) mutableStateRememberSaveable2.getValue(), composerImpl2, i52 & 14);
            mutableStateRememberSaveable2.setValue((TutorialActionState) mutableStateCollectAsStateWithLifecycle22.getValue());
            TutorialActionState tutorialActionState2 = (TutorialActionState) mutableStateCollectAsStateWithLifecycle22.getValue();
            boolean zBooleanValue2 = ((Boolean) mutableStateCollectAsStateWithLifecycle3.getValue()).booleanValue();
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(1018742334, new Function3() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt.GestureTutorialScreen.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
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
                                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreen.<anonymous> (GestureTutorialScreen.kt:67)");
                            }
                            ActionTutorialContentKt.ActionTutorialContent((TutorialActionState) mutableStateCollectAsStateWithLifecycle22.getValue(), function02, tutorialScreenConfig, function15, composer2, 512, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2);
            int i72 = ((i62 >> 6) & 14) | 196608 | (i52 & 7168);
            composerImpl = composerImpl2;
            Function1 function162 = function15;
            TouchpadGesturesHandlingBox(function1, tutorialActionState2, zBooleanValue2, function04, null, composableLambdaImplRememberComposableLambda2, composerImpl, i72);
            if (ComposerKt.isTraceInProgress()) {
            }
            function14 = function162;
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = AnimatableKt.Animatable(0.0f, 0.01f);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final Animatable animatable = (Animatable) objRememberedValue;
            composerImpl.end(false);
            Boolean boolValueOf = Boolean.valueOf(z);
            composerImpl.startReplaceGroup(-681065918);
            boolean zChangedInstance = ((i3 & 896) == 256) | composerImpl.changedInstance(animatable) | ((i3 & 7168) == 2048);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1(z, animatable, function0, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, boolValueOf, (Function2) objRememberedValue2);
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion2, 1.0f);
            composerImpl.startReplaceGroup(-681032813);
            boolean zChangedInstance2 = composerImpl.changedInstance(animatable);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new Function1() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setRotationZ(((Number) animatable.internalState.getValue()).floatValue());
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxSize, (Function1) objRememberedValue3);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierGraphicsLayer);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composerImpl, Integer.valueOf(((i3 >> 12) & 112) | 6));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    GestureTutorialScreenKt.TouchpadGesturesHandlingBox(function1, tutorialActionState, z, function0, companion2, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
