package com.android.systemui.communal.ui.compose;

import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.BackgroundElement;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.draw.BlurKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.LinearGradient;
import androidx.compose.ui.graphics.RadialGradient;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.TileMode;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda1;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl$updateContents$1;
import com.android.compose.animation.scene.SceneTransitionLayoutKt;
import com.android.compose.animation.scene.SceneTransitionLayoutStateKt;
import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalBackgroundType;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.ui.compose.extensions.ModifierExtKt$allowGestures$1;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.util.CommunalColors;
import com.android.systemui.communal.util.CommunalColorsImpl;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import com.android.systemui.scene.ui.composable.SceneTransitionLayoutDataSource;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public abstract class CommunalContainerKt {
    public static final float ANIMATION_OFFSCREEN_OFFSET;
    public static final SceneTransitions sceneTransitions;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CommunalBackgroundType.values().length];
            try {
                iArr[CommunalBackgroundType.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CommunalBackgroundType.STATIC_GRADIENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CommunalBackgroundType.ANIMATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CommunalBackgroundType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CommunalBackgroundType.BLUR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[CommunalBackgroundType.SCRIM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        TransitionDslKt.transitions(new CommunalContainerKt$$ExternalSyntheticLambda0(0));
        sceneTransitions = TransitionDslKt.transitions(new CommunalContainerKt$$ExternalSyntheticLambda0(11));
        Dp.Companion companion = Dp.Companion;
        ANIMATION_OFFSCREEN_OFFSET = 128;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ee  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AnimatedLinearGradient(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        ?? r2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1850432005);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.AnimatedLinearGradient (CommunalContainer.kt:373)");
            }
            MaterialTheme.INSTANCE.getClass();
            ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(boxScopeInstance.matchParentSize(Modifier.Companion), colorScheme.primary, RectangleShapeKt.RectangleShape);
            long j = colorScheme.primaryContainer;
            final long jColor = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.6f, Color.m461getColorSpaceimpl(j));
            composerImpl.startReplaceGroup(-780450518);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.animatedRadialGradientBackground (CommunalContainer.kt:405)");
            }
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            final InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat = InfiniteTransitionKt.animateFloat(InfiniteTransitionKt.rememberInfiniteTransition("radial gradient transition", composerImpl, 0), 0.0f, 1.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(4, AnimationSpecKt.tween$default(10000, 0, new CubicBezierEasing(0.33f, 0.0f, 0.67f, 1.0f), 2), RepeatMode.Reverse, 0L), "radial gradient center fraction", composerImpl, 29112, 0);
            composerImpl.startReplaceGroup(1748080544);
            boolean zChanged = composerImpl.changed(density);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = Float.valueOf(density.mo58toPx0680j_4(ANIMATION_OFFSCREEN_OFFSET));
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                final float fFloatValue = ((Number) objRememberedValue).floatValue();
                composerImpl.end(false);
                composerImpl.startReplaceGroup(1748084690);
                boolean zChanged2 = composerImpl.changed(fFloatValue) | composerImpl.changed(transitionAnimationStateAnimateFloat) | composerImpl.changed(jColor);
                final long j2 = colorScheme.primary;
                boolean zChanged3 = zChanged2 | composerImpl.changed(j2);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged3) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        r2 = 0;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda41
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                float f = 2;
                                float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) / f;
                                float f2 = fFloatValue;
                                float f3 = fIntBitsToFloat + f2;
                                float fIntBitsToFloat2 = (f * f2) + Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L));
                                InfiniteTransition.TransitionAnimationState transitionAnimationState = transitionAnimationStateAnimateFloat;
                                float fFloatValue2 = (((Number) transitionAnimationState.getValue()).floatValue() * fIntBitsToFloat2) - f2;
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(-f2) << 32) | (Float.floatToRawIntBits(fFloatValue2) & 4294967295L);
                                Offset.Companion companion2 = Offset.Companion;
                                float fIntBitsToFloat3 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) + f2;
                                float fFloatValue3 = ((1.0f - ((Number) transitionAnimationState.getValue()).floatValue()) * fIntBitsToFloat2) - f2;
                                long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fIntBitsToFloat3) << 32) | (Float.floatToRawIntBits(fFloatValue3) & 4294967295L);
                                Brush.Companion companion3 = Brush.Companion;
                                long j3 = jColor;
                                Color colorM456boximpl = Color.m456boximpl(j3);
                                long j4 = j2;
                                RadialGradient radialGradientM452radialGradientP_VxKs$default = Brush.Companion.m452radialGradientP_VxKs$default(companion3, Arrays.asList(colorM456boximpl, Color.m456boximpl(j4)), jFloatToRawIntBits2, f3);
                                BlendMode.Companion.getClass();
                                int i2 = BlendMode.SrcAtop;
                                DrawScope.m533drawCircleV9BoPsw$default(drawScope, radialGradientM452radialGradientP_VxKs$default, f3, jFloatToRawIntBits2, 0.0f, i2, 56);
                                DrawScope.m533drawCircleV9BoPsw$default(drawScope, Brush.Companion.m452radialGradientP_VxKs$default(companion3, Arrays.asList(Color.m456boximpl(j3), Color.m456boximpl(j4)), jFloatToRawIntBits, f3), f3, jFloatToRawIntBits, 0.0f, i2, 56);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(function1);
                        objRememberedValue2 = function1;
                    } else {
                        r2 = 0;
                    }
                    composerImpl.end(r2);
                    Modifier modifierDrawBehind = DrawModifierKt.drawBehind(modifierM26backgroundbw27NRU, (Function1) objRememberedValue2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(r2);
                    BoxKt.Box(modifierDrawBehind, composerImpl, r2);
                    BackgroundTopScrim(boxScopeInstance, composerImpl, 6);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 2);
        }
    }

    public static final void Background(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2031071227);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Background (CommunalContainer.kt:395)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 4);
        }
    }

    public static final void BackgroundTopScrim(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        long j;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1955833836);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.BackgroundTopScrim (CommunalContainer.kt:388)");
            }
            if (DarkThemeKt.isSystemInDarkTheme(composerImpl)) {
                Color.Companion.getClass();
                j = Color.Black;
            } else {
                Color.Companion.getClass();
                j = Color.White;
            }
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(AlphaKt.alpha(boxScopeInstance.matchParentSize(Modifier.Companion), 0.34f), j, RectangleShapeKt.RectangleShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 1);
        }
    }

    public static final void CommunalContainer(Modifier.Companion companion, final CommunalViewModel communalViewModel, final SceneDataSourceDelegator sceneDataSourceDelegator, final CommunalColors communalColors, final CommunalContent communalContent, Composer composer, final int i) {
        Object obj;
        Modifier.Companion companion2;
        boolean z;
        final Modifier.Companion companion3;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(561132849);
        int i2 = i | 6 | (composerImpl2.changedInstance(communalViewModel) ? 32 : 16) | (composerImpl2.changedInstance(sceneDataSourceDelegator) ? 256 : 128) | (composerImpl2.changed(communalColors) ? 2048 : 1024) | (composerImpl2.changedInstance(communalContent) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i2 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            companion3 = companion;
            composerImpl = composerImpl2;
        } else {
            Modifier.Companion companion4 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContainer (CommunalContainer.kt:196)");
            }
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.currentScene, composerImpl2);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.touchesAllowed, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.communalBackground, CommunalBackgroundType.ANIMATED, composerImpl2, 48);
            Flow flow = (Flow) communalViewModel.swipeToHubEnabled$delegate.getValue();
            Boolean bool = Boolean.FALSE;
            final MutableState mutableStateCollectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(flow, bool, composerImpl2, 48);
            SceneKey sceneKey = (SceneKey) mutableStateCollectAsStateWithLifecycle.getValue();
            communalViewModel.communalSettingsInteractor.isV2FlagEnabled();
            composerImpl2.startReplaceGroup(1842532861);
            boolean zChangedInstance = composerImpl2.changedInstance(communalViewModel);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new CommunalContainerKt$$ExternalSyntheticLambda24(communalViewModel, 0);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            composerImpl2.end(false);
            final MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState = SceneTransitionLayoutStateKt.rememberMutableSceneTransitionLayoutState(sceneKey, sceneTransitions, (Function1) objRememberedValue2, null, null, null, null, composerImpl2, SceneTransitions.$stable << 3, EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_SUCCESSFUL);
            MutableState mutableStateCollectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isUiBlurred, composerImpl2);
            composerImpl2.startReplaceGroup(1842540606);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new CommunalSwipeDetector(null, 1, null);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            CommunalSwipeDetector communalSwipeDetector = (CommunalSwipeDetector) objRememberedValue3;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1842542881);
            boolean zChanged = composerImpl2.changed(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(sceneDataSourceDelegator);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue4 == composer$Companion$Empty$1) {
                objRememberedValue4 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda25
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        SceneTransitionLayoutDataSource sceneTransitionLayoutDataSource = new SceneTransitionLayoutDataSource(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, coroutineScope);
                        final SceneDataSourceDelegator sceneDataSourceDelegator2 = sceneDataSourceDelegator;
                        sceneDataSourceDelegator2.setDelegate(sceneTransitionLayoutDataSource);
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$lambda$34$lambda$33$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                sceneDataSourceDelegator2.setDelegate(null);
                            }
                        };
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, (Function1) objRememberedValue4, composerImpl2);
            composerImpl2.startReplaceGroup(1842556290);
            boolean zChangedInstance2 = composerImpl2.changedInstance(communalViewModel) | composerImpl2.changed(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (zChangedInstance2 || objRememberedValue5 == composer$Companion$Empty$1) {
                objRememberedValue5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda26
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, 5)));
                        final CommunalViewModel communalViewModel2 = communalViewModel;
                        ((CommunalSceneRepositoryImpl) communalViewModel2.communalSceneInteractor.repository)._transitionState.setValue(flowDistinctUntilChanged);
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$lambda$37$lambda$36$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                ((CommunalSceneRepositoryImpl) communalViewModel2.communalSceneInteractor.repository)._transitionState.setValue(null);
                            }
                        };
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(communalViewModel, mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, (Function1) objRememberedValue5, composerImpl2);
            float fMo54toDpu2uoSUM = ((Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity)).mo54toDpu2uoSUM(communalViewModel.blurRadiusPx);
            final MutableState mutableStateCollectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.swipeFromHubInLandscape, bool, composerImpl2, 48);
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion4, 1.0f);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle5.getValue()).booleanValue()) {
                modifierFillMaxSize = modifierFillMaxSize.then(BlurKt.m359blurF8QBwvs$default(companion4, fMo54toDpu2uoSUM));
            }
            Modifier modifier = modifierFillMaxSize;
            composerImpl2.startReplaceGroup(1842575304);
            boolean zChanged2 = composerImpl2.changed(mutableStateCollectAsStateWithLifecycle4) | composerImpl2.changed(mutableStateCollectAsStateWithLifecycle6) | composerImpl2.changed(mutableStateCollectAsStateWithLifecycle3) | ((i2 & 7168) == 2048) | composerImpl2.changedInstance(communalContent) | composerImpl2.changedInstance(communalViewModel);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue6 == composer$Companion$Empty$1) {
                companion2 = companion4;
                z = false;
                obj = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda27
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Map mapEmptyMap;
                        TransitionKey transitionKey;
                        SceneTransitionLayoutImpl$updateContents$1 sceneTransitionLayoutImpl$updateContents$1 = (SceneTransitionLayoutImpl$updateContents$1) obj2;
                        SceneKey sceneKey2 = CommunalScenes.Blank;
                        if (((Boolean) mutableStateCollectAsStateWithLifecycle4.getValue()).booleanValue()) {
                            Swipe.Companion companion5 = Swipe.Companion;
                            Edge edge = Edge.End;
                            companion5.getClass();
                            Swipe swipe = new Swipe(SwipeDirection.Start, 1, null, edge, null);
                            UserActionResult.Companion companion6 = UserActionResult.Companion;
                            SceneKey sceneKey3 = CommunalScenes.Communal;
                            CommunalTransitionKeys.INSTANCE.getClass();
                            Pair pair = new Pair(swipe, UserActionResult.Companion.invoke$default(companion6, sceneKey3, CommunalTransitionKeys.Swipe, 4));
                            mapEmptyMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
                        } else {
                            mapEmptyMap = MapsKt__MapsKt.emptyMap();
                        }
                        Map map = mapEmptyMap;
                        ComposableSingletons$CommunalContainerKt.INSTANCE.getClass();
                        SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, sceneKey2, map, null, ComposableSingletons$CommunalContainerKt.f28lambda1, 12);
                        SceneKey sceneKey4 = CommunalScenes.Communal;
                        Swipe.Companion.getClass();
                        Swipe swipe2 = Swipe.End;
                        UserActionResult.Companion companion7 = UserActionResult.Companion;
                        if (((Boolean) mutableStateCollectAsStateWithLifecycle6.getValue()).booleanValue()) {
                            CommunalTransitionKeys.INSTANCE.getClass();
                            transitionKey = CommunalTransitionKeys.SwipeInLandscape;
                        } else {
                            CommunalTransitionKeys.INSTANCE.getClass();
                            transitionKey = CommunalTransitionKeys.Swipe;
                        }
                        Pair pair2 = new Pair(swipe2, UserActionResult.Companion.invoke$default(companion7, sceneKey2, transitionKey, 4));
                        Map mapSingletonMap = Collections.singletonMap(pair2.getFirst(), pair2.getSecond());
                        final CommunalViewModel communalViewModel2 = communalViewModel;
                        final MutableState mutableState = mutableStateCollectAsStateWithLifecycle3;
                        final CommunalColors communalColors2 = communalColors;
                        final CommunalContent communalContent2 = communalContent;
                        SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, sceneKey4, mapSingletonMap, null, new ComposableLambdaImpl(-1409937629, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$4$1$1
                            /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                ContentScope contentScope = (ContentScope) obj3;
                                Composer composer2 = (Composer) obj4;
                                int iIntValue = ((Number) obj5).intValue();
                                if ((iIntValue & 6) == 0) {
                                    iIntValue |= ((ComposerImpl) composer2).changed(contentScope) ? 4 : 2;
                                }
                                if ((iIntValue & 19) == 18) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContainer.<anonymous>.<anonymous>.<anonymous> (CommunalContainer.kt:271)");
                                        }
                                        SceneTransitions sceneTransitions2 = CommunalContainerKt.sceneTransitions;
                                        CommunalContainerKt.CommunalScene(contentScope, (CommunalBackgroundType) mutableState.getValue(), communalColors2, communalContent2, communalViewModel2, null, composer2, iIntValue & 14);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }), 12);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(obj);
            } else {
                obj = objRememberedValue6;
                z = false;
                companion2 = companion4;
            }
            composerImpl2.end(z);
            boolean z2 = z;
            companion3 = companion2;
            SceneTransitionLayoutKt.SceneTransitionLayout(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, modifier, communalSwipeDetector, communalSwipeDetector, 0.0f, (Function1) obj, composerImpl2, 3456, 48);
            composerImpl = composerImpl2;
            Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(companion3, 1.0f);
            if (!((Boolean) mutableStateCollectAsStateWithLifecycle2.getValue()).booleanValue()) {
                modifierFillMaxSize2 = modifierFillMaxSize2.then(SuspendingPointerInputFilterKt.pointerInput(modifierFillMaxSize2, Unit.INSTANCE, ModifierExtKt$allowGestures$1.INSTANCE));
            }
            BoxKt.Box(modifierFillMaxSize2, composerImpl, z2 ? 1 : 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(communalViewModel, sceneDataSourceDelegator, communalColors, communalContent, i) { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda28
                public final /* synthetic */ CommunalViewModel f$1;
                public final /* synthetic */ SceneDataSourceDelegator f$2;
                public final /* synthetic */ CommunalColors f$3;
                public final /* synthetic */ CommunalContent f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    CommunalColors communalColors2 = this.f$3;
                    CommunalContent communalContent2 = this.f$4;
                    CommunalContainerKt.CommunalContainer(this.f$0, this.f$1, this.f$2, communalColors2, communalContent2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0216  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CommunalScene(final ContentScope contentScope, CommunalBackgroundType communalBackgroundType, final CommunalColors communalColors, final CommunalContent communalContent, final CommunalViewModel communalViewModel, Modifier.Companion companion, Composer composer, final int i) {
        int i2;
        CommunalBackgroundType communalBackgroundType2;
        Modifier modifierClearAndSetSemantics;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(989683389);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            communalBackgroundType2 = communalBackgroundType;
            i2 |= composerImpl.changed(communalBackgroundType2) ? 32 : 16;
        } else {
            communalBackgroundType2 = communalBackgroundType;
        }
        if ((i & 384) == 0) {
            i2 |= (i & 512) == 0 ? composerImpl.changed(communalColors) : composerImpl.changedInstance(communalColors) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(communalContent) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(communalViewModel) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        int i3 = i2 | 196608;
        if ((74899 & i3) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalScene (CommunalContainer.kt:307)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isFocusable, Boolean.FALSE, composerImpl, 48);
            ObserveOrientationChange(communalViewModel, composerImpl, (i3 >> 12) & 14);
            Communal$Elements.INSTANCE.getClass();
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(contentScope.element(companion3, Communal$Elements.Scrim), 1.0f);
            composerImpl.startReplaceGroup(-1858065431);
            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
            Composer.Companion companion4 = Composer.Companion;
            if (zBooleanValue) {
                modifierClearAndSetSemantics = FocusableKt.focusable$default(companion3, false, null, 3);
            } else {
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -1858061261, companion4);
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objM == composer$Companion$Empty$1) {
                    objM = new CommunalContainerKt$$ExternalSyntheticLambda0(16);
                    composerImpl.updateRememberedValue(objM);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(companion3, false, (Function1) objM);
                composerImpl.startReplaceGroup(-1858060121);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (objRememberedValue == composer$Companion$Empty$1) {
                    objRememberedValue = new CommunalContainerKt$$ExternalSyntheticLambda0(18);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                modifierClearAndSetSemantics = SemanticsModifierKt.clearAndSetSemantics(modifierSemantics, (Function1) objRememberedValue);
            }
            composerImpl.end(false);
            Modifier modifierThen = modifierFillMaxSize.then(modifierClearAndSetSemantics);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            switch (WhenMappings.$EnumSwitchMapping$0[communalBackgroundType2.ordinal()]) {
                case 1:
                    composerImpl.startReplaceGroup(2064057158);
                    DefaultBackground(boxScopeInstance, communalColors, composerImpl, ((i3 >> 3) & 112) | 6);
                    composerImpl.end(false);
                    break;
                case 2:
                    composerImpl.startReplaceGroup(2064059994);
                    StaticLinearGradient(boxScopeInstance, composerImpl, 6);
                    composerImpl.end(false);
                    break;
                case 3:
                    composerImpl.startReplaceGroup(2064062236);
                    AnimatedLinearGradient(boxScopeInstance, composerImpl, 6);
                    composerImpl.end(false);
                    break;
                case 4:
                    composerImpl.startReplaceGroup(2064064408);
                    BackgroundTopScrim(boxScopeInstance, composerImpl, 6);
                    composerImpl.end(false);
                    break;
                case 5:
                    composerImpl.startReplaceGroup(2064066448);
                    Background(boxScopeInstance, composerImpl, 6);
                    composerImpl.end(false);
                    break;
                case 6:
                    composerImpl.startReplaceGroup(2064068270);
                    Scrimmed(boxScopeInstance, composerImpl, 6);
                    composerImpl.end(false);
                    break;
                default:
                    composerImpl.startReplaceGroup(2064055353);
                    composerImpl.end(false);
                    throw new NoWhenBranchMatchedException();
            }
            composerImpl.startReplaceGroup(2064069502);
            Modifier modifierFocusable$default = FocusableKt.focusable$default(companion3, ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue(), null, 2);
            composerImpl.startReplaceGroup(1498623655);
            boolean zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChanged) {
                companion4.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new CommunalContainerKt$$ExternalSyntheticLambda24(mutableStateCollectAsStateWithLifecycle, 2);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                communalContent.Content(contentScope, SemanticsModifierKt.semantics(modifierFocusable$default, false, (Function1) objRememberedValue2), composerImpl, i3 & 14);
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                    ComposerKt.traceEventEnd();
                }
                companion2 = companion3;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final CommunalBackgroundType communalBackgroundType3 = communalBackgroundType2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda32
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalViewModel communalViewModel2 = communalViewModel;
                    Modifier.Companion companion5 = companion2;
                    CommunalContainerKt.CommunalScene(contentScope, communalBackgroundType3, communalColors, communalContent, communalViewModel2, companion5, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void DefaultBackground(final BoxScopeInstance boxScopeInstance, final CommunalColors communalColors, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2110767570);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(boxScopeInstance) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(communalColors) : composerImpl.changedInstance(communalColors) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DefaultBackground (CommunalContainer.kt:348)");
            }
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(boxScopeInstance.matchParentSize(Modifier.Companion), ColorKt.Color(((android.graphics.Color) FlowExtKt.collectAsStateWithLifecycle(((CommunalColorsImpl) communalColors).backgroundColor, composerImpl).getValue()).toArgb()), RectangleShapeKt.RectangleShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda36
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalContainerKt.DefaultBackground(boxScopeInstance, communalColors, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ObserveOrientationChange(final CommunalViewModel communalViewModel, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(463124224);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(communalViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ObserveOrientationChange (CommunalContainer.kt:287)");
            }
            Configuration configuration = (Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration);
            Integer numValueOf = Integer.valueOf(configuration.orientation);
            composerImpl.startReplaceGroup(1539945347);
            boolean zChangedInstance = composerImpl.changedInstance(communalViewModel) | composerImpl.changedInstance(configuration);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CommunalContainerKt$ObserveOrientationChange$1$1(communalViewModel, configuration, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, numValueOf, (Function2) objRememberedValue);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1539948603);
                boolean zChangedInstance2 = composerImpl.changedInstance(communalViewModel);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new CommunalContainerKt$$ExternalSyntheticLambda24(communalViewModel, 1);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue2, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalContainerKt.ObserveOrientationChange(communalViewModel, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Scrimmed(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1578118355);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Scrimmed (CommunalContainer.kt:354)");
            }
            Modifier modifierAlpha = AlphaKt.alpha(boxScopeInstance.matchParentSize(Modifier.Companion), 0.34f);
            Color.Companion.getClass();
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(modifierAlpha, Color.Black, RectangleShapeKt.RectangleShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 0);
        }
    }

    public static final void StaticLinearGradient(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1743878288);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.StaticLinearGradient (CommunalContainer.kt:360)");
            }
            MaterialTheme.INSTANCE.getClass();
            ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Modifier modifierMatchParentSize = boxScopeInstance.matchParentSize(Modifier.Companion);
            Brush.Companion companion = Brush.Companion;
            List listAsList = Arrays.asList(Color.m456boximpl(colorScheme.primary), Color.m456boximpl(colorScheme.primaryContainer));
            Offset.Companion.getClass();
            long j = Offset.Infinite;
            TileMode.Companion.getClass();
            companion.getClass();
            BoxKt.Box(modifierMatchParentSize.then(new BackgroundElement(0L, new LinearGradient(listAsList, null, 0L, j, 0, null), 1.0f, RectangleShapeKt.RectangleShape, InspectableValueKt.NoInspectorInfo, 1, null)), composerImpl, 0);
            BackgroundTopScrim(boxScopeInstance, composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 3);
        }
    }
}
