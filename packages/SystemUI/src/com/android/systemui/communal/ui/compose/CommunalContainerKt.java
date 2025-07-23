package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.BackgroundElement;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.draw.BlurKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.LinearGradient;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.TileMode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
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
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CommunalContainerKt {
    public static final float ANIMATION_OFFSCREEN_OFFSET;
    public static final SceneTransitions sceneTransitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ab, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x011f  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedLinearGradient(androidx.compose.foundation.layout.BoxScopeInstance r19, androidx.compose.runtime.Composer r20, int r21) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalContainerKt.AnimatedLinearGradient(androidx.compose.foundation.layout.BoxScopeInstance, androidx.compose.runtime.Composer, int):void");
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 4);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 1);
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
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.currentScene, composerImpl2);
            MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.touchesAllowed, composerImpl2);
            final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.communalBackground, CommunalBackgroundType.ANIMATED, composerImpl2, 48);
            Flow flow = (Flow) communalViewModel.swipeToHubEnabled$delegate.getValue();
            Boolean bool = Boolean.FALSE;
            final MutableState collectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(flow, bool, composerImpl2, 48);
            SceneKey sceneKey = (SceneKey) collectAsStateWithLifecycle.getValue();
            communalViewModel.communalSettingsInteractor.isV2FlagEnabled();
            composerImpl2.startReplaceGroup(1842532861);
            boolean changedInstance = composerImpl2.changedInstance(communalViewModel);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new CommunalContainerKt$$ExternalSyntheticLambda24(communalViewModel, 0);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            final MutableSceneTransitionLayoutStateImpl rememberMutableSceneTransitionLayoutState = SceneTransitionLayoutStateKt.rememberMutableSceneTransitionLayoutState(sceneKey, sceneTransitions, (Function1) rememberedValue2, null, null, null, null, composerImpl2, SceneTransitions.$stable << 3, EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_SUCCESSFUL);
            MutableState collectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isUiBlurred, composerImpl2);
            composerImpl2.startReplaceGroup(1842540606);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new CommunalSwipeDetector(null, 1, null);
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            CommunalSwipeDetector communalSwipeDetector = (CommunalSwipeDetector) rememberedValue3;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1842542881);
            boolean changed = composerImpl2.changed(rememberMutableSceneTransitionLayoutState) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(sceneDataSourceDelegator);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (changed || rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda25
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        SceneTransitionLayoutDataSource sceneTransitionLayoutDataSource = new SceneTransitionLayoutDataSource(MutableSceneTransitionLayoutStateImpl.this, coroutineScope);
                        final SceneDataSourceDelegator sceneDataSourceDelegator2 = sceneDataSourceDelegator;
                        sceneDataSourceDelegator2.setDelegate(sceneTransitionLayoutDataSource);
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$lambda$34$lambda$33$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                SceneDataSourceDelegator.this.setDelegate(null);
                            }
                        };
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue4);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(rememberMutableSceneTransitionLayoutState, (Function1) rememberedValue4, composerImpl2);
            composerImpl2.startReplaceGroup(1842556290);
            boolean changedInstance2 = composerImpl2.changedInstance(communalViewModel) | composerImpl2.changed(rememberMutableSceneTransitionLayoutState);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (changedInstance2 || rememberedValue5 == composer$Companion$Empty$1) {
                rememberedValue5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda26
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(rememberMutableSceneTransitionLayoutState, 5)));
                        final CommunalViewModel communalViewModel2 = CommunalViewModel.this;
                        ((CommunalSceneRepositoryImpl) communalViewModel2.communalSceneInteractor.repository)._transitionState.setValue(distinctUntilChanged);
                        return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$lambda$37$lambda$36$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                ((CommunalSceneRepositoryImpl) CommunalViewModel.this.communalSceneInteractor.repository)._transitionState.setValue(null);
                            }
                        };
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(communalViewModel, rememberMutableSceneTransitionLayoutState, (Function1) rememberedValue5, composerImpl2);
            float mo53toDpu2uoSUM = ((Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity)).mo53toDpu2uoSUM(communalViewModel.blurRadiusPx);
            final MutableState collectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.swipeFromHubInLandscape, bool, composerImpl2, 48);
            Modifier fillMaxSize = SizeKt.fillMaxSize(companion4, 1.0f);
            if (((Boolean) collectAsStateWithLifecycle5.getValue()).booleanValue()) {
                fillMaxSize = fillMaxSize.then(BlurKt.m358blurF8QBwvs$default(companion4, mo53toDpu2uoSUM));
            }
            Modifier modifier = fillMaxSize;
            composerImpl2.startReplaceGroup(1842575304);
            boolean changed2 = composerImpl2.changed(collectAsStateWithLifecycle4) | composerImpl2.changed(collectAsStateWithLifecycle6) | composerImpl2.changed(collectAsStateWithLifecycle3) | ((i2 & 7168) == 2048) | composerImpl2.changedInstance(communalContent) | composerImpl2.changedInstance(communalViewModel);
            Object rememberedValue6 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue6 == composer$Companion$Empty$1) {
                companion2 = companion4;
                z = false;
                obj = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda27
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        Map emptyMap;
                        TransitionKey transitionKey;
                        SceneTransitionLayoutImpl$updateContents$1 sceneTransitionLayoutImpl$updateContents$1 = (SceneTransitionLayoutImpl$updateContents$1) obj2;
                        SceneKey sceneKey2 = CommunalScenes.Blank;
                        if (((Boolean) MutableState.this.getValue()).booleanValue()) {
                            Swipe.Companion companion5 = Swipe.Companion;
                            Edge edge = Edge.End;
                            companion5.getClass();
                            Swipe swipe = new Swipe(SwipeDirection.Start, 1, null, edge, null);
                            UserActionResult.Companion companion6 = UserActionResult.Companion;
                            SceneKey sceneKey3 = CommunalScenes.Communal;
                            CommunalTransitionKeys.INSTANCE.getClass();
                            Pair pair = new Pair(swipe, UserActionResult.Companion.invoke$default(companion6, sceneKey3, CommunalTransitionKeys.Swipe, 4));
                            emptyMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
                        } else {
                            emptyMap = MapsKt__MapsKt.emptyMap();
                        }
                        Map map = emptyMap;
                        ComposableSingletons$CommunalContainerKt.INSTANCE.getClass();
                        SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, sceneKey2, map, null, ComposableSingletons$CommunalContainerKt.f28lambda1, 12);
                        SceneKey sceneKey4 = CommunalScenes.Communal;
                        Swipe.Companion.getClass();
                        Swipe swipe2 = Swipe.End;
                        UserActionResult.Companion companion7 = UserActionResult.Companion;
                        if (((Boolean) collectAsStateWithLifecycle6.getValue()).booleanValue()) {
                            CommunalTransitionKeys.INSTANCE.getClass();
                            transitionKey = CommunalTransitionKeys.SwipeInLandscape;
                        } else {
                            CommunalTransitionKeys.INSTANCE.getClass();
                            transitionKey = CommunalTransitionKeys.Swipe;
                        }
                        Pair pair2 = new Pair(swipe2, UserActionResult.Companion.invoke$default(companion7, sceneKey2, transitionKey, 4));
                        Map singletonMap = Collections.singletonMap(pair2.getFirst(), pair2.getSecond());
                        final CommunalViewModel communalViewModel2 = communalViewModel;
                        final MutableState mutableState = collectAsStateWithLifecycle3;
                        final CommunalColors communalColors2 = communalColors;
                        final CommunalContent communalContent2 = communalContent;
                        SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, sceneKey4, singletonMap, null, new ComposableLambdaImpl(-1409937629, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$4$1$1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                ContentScope contentScope = (ContentScope) obj3;
                                Composer composer2 = (Composer) obj4;
                                int intValue = ((Number) obj5).intValue();
                                if ((intValue & 6) == 0) {
                                    intValue |= ((ComposerImpl) composer2).changed(contentScope) ? 4 : 2;
                                }
                                if ((intValue & 19) == 18) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContainer.<anonymous>.<anonymous>.<anonymous> (CommunalContainer.kt:271)");
                                }
                                SceneTransitions sceneTransitions2 = CommunalContainerKt.sceneTransitions;
                                CommunalContainerKt.CommunalScene(contentScope, (CommunalBackgroundType) mutableState.getValue(), CommunalColors.this, communalContent2, communalViewModel2, null, composer2, intValue & 14);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }), 12);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(obj);
            } else {
                obj = rememberedValue6;
                z = false;
                companion2 = companion4;
            }
            composerImpl2.end(z);
            boolean z2 = z;
            companion3 = companion2;
            SceneTransitionLayoutKt.SceneTransitionLayout(rememberMutableSceneTransitionLayoutState, modifier, communalSwipeDetector, communalSwipeDetector, 0.0f, (Function1) obj, composerImpl2, 3456, 48);
            composerImpl = composerImpl2;
            Modifier fillMaxSize2 = SizeKt.fillMaxSize(companion3, 1.0f);
            if (!((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue()) {
                fillMaxSize2 = fillMaxSize2.then(SuspendingPointerInputFilterKt.pointerInput(fillMaxSize2, Unit.INSTANCE, ModifierExtKt$allowGestures$1.INSTANCE));
            }
            BoxKt.Box(fillMaxSize2, composerImpl, z2 ? 1 : 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(communalViewModel, sceneDataSourceDelegator, communalColors, communalContent, i) { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda28
                public final /* synthetic */ CommunalViewModel f$1;
                public final /* synthetic */ SceneDataSourceDelegator f$2;
                public final /* synthetic */ CommunalColors f$3;
                public final /* synthetic */ CommunalContent f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    CommunalColors communalColors2 = this.f$3;
                    CommunalContent communalContent2 = this.f$4;
                    CommunalContainerKt.CommunalContainer(Modifier.Companion.this, this.f$1, this.f$2, communalColors2, communalContent2, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0214, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L86;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CommunalScene(final com.android.compose.animation.scene.ContentScope r18, com.android.systemui.communal.shared.model.CommunalBackgroundType r19, final com.android.systemui.communal.util.CommunalColors r20, final com.android.systemui.communal.ui.compose.CommunalContent r21, final com.android.systemui.communal.ui.viewmodel.CommunalViewModel r22, androidx.compose.ui.Modifier.Companion r23, androidx.compose.runtime.Composer r24, final int r25) {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalContainerKt.CommunalScene(com.android.compose.animation.scene.ContentScope, com.android.systemui.communal.shared.model.CommunalBackgroundType, com.android.systemui.communal.util.CommunalColors, com.android.systemui.communal.ui.compose.CommunalContent, com.android.systemui.communal.ui.viewmodel.CommunalViewModel, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.Composer, int):void");
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda36
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalContainerKt.DefaultBackground(BoxScopeInstance.this, communalColors, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005e, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0089, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ObserveOrientationChange(final com.android.systemui.communal.ui.viewmodel.CommunalViewModel r5, androidx.compose.runtime.Composer r6, final int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r0 = 463124224(0x1b9ab700, float:2.5595424E-22)
            r6.startRestartGroup(r0)
            r0 = r7 & 6
            r1 = 2
            if (r0 != 0) goto L18
            boolean r0 = r6.changedInstance(r5)
            if (r0 == 0) goto L15
            r0 = 4
            goto L16
        L15:
            r0 = r1
        L16:
            r0 = r0 | r7
            goto L19
        L18:
            r0 = r7
        L19:
            r0 = r0 & 3
            if (r0 != r1) goto L29
            boolean r0 = r6.getSkipping()
            if (r0 != 0) goto L24
            goto L29
        L24:
            r6.skipToGroupEnd()
            goto La5
        L29:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L34
            java.lang.String r0 = "com.android.systemui.communal.ui.compose.ObserveOrientationChange (CommunalContainer.kt:287)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L34:
            androidx.compose.runtime.DynamicProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalConfiguration
            java.lang.Object r0 = r6.consume(r0)
            android.content.res.Configuration r0 = (android.content.res.Configuration) r0
            int r1 = r0.orientation
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r2 = 1539945347(0x5bc9b383, float:1.1354769E17)
            r6.startReplaceGroup(r2)
            boolean r2 = r6.changedInstance(r5)
            boolean r3 = r6.changedInstance(r0)
            r2 = r2 | r3
            java.lang.Object r3 = r6.rememberedValue()
            androidx.compose.runtime.Composer$Companion r4 = androidx.compose.runtime.Composer.Companion
            if (r2 != 0) goto L60
            r4.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L69
        L60:
            com.android.systemui.communal.ui.compose.CommunalContainerKt$ObserveOrientationChange$1$1 r3 = new com.android.systemui.communal.ui.compose.CommunalContainerKt$ObserveOrientationChange$1$1
            r2 = 0
            r3.<init>(r5, r0, r2)
            r6.updateRememberedValue(r3)
        L69:
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            r0 = 0
            r6.end(r0)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r6, r1, r3)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            r2 = 1539948603(0x5bc9c03b, float:1.1357566E17)
            r6.startReplaceGroup(r2)
            boolean r2 = r6.changedInstance(r5)
            java.lang.Object r3 = r6.rememberedValue()
            if (r2 != 0) goto L8b
            r4.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L94
        L8b:
            com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda24 r3 = new com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda24
            r2 = 1
            r3.<init>(r5, r2)
            r6.updateRememberedValue(r3)
        L94:
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r6.end(r0)
            androidx.compose.runtime.EffectsKt.DisposableEffect(r1, r3, r6)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto La5
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        La5:
            androidx.compose.runtime.RecomposeScopeImpl r6 = r6.endRestartGroup()
            if (r6 == 0) goto Lb2
            com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda39 r0 = new com.android.systemui.communal.ui.compose.CommunalContainerKt$$ExternalSyntheticLambda39
            r0.<init>()
            r6.block = r0
        Lb2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalContainerKt.ObserveOrientationChange(com.android.systemui.communal.ui.viewmodel.CommunalViewModel, androidx.compose.runtime.Composer, int):void");
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
            Modifier alpha = AlphaKt.alpha(boxScopeInstance.matchParentSize(Modifier.Companion), 0.34f);
            Color.Companion.getClass();
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(alpha, Color.Black, RectangleShapeKt.RectangleShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 0);
        }
    }

    public static final void StaticLinearGradient(BoxScopeInstance boxScopeInstance, Composer composer, int i) {
        Modifier then;
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
            Modifier matchParentSize = boxScopeInstance.matchParentSize(Modifier.Companion);
            Brush.Companion companion = Brush.Companion;
            List asList = Arrays.asList(Color.m454boximpl(colorScheme.primary), Color.m454boximpl(colorScheme.primaryContainer));
            Offset.Companion.getClass();
            long j = Offset.Infinite;
            TileMode.Companion.getClass();
            companion.getClass();
            then = matchParentSize.then(new BackgroundElement(0L, new LinearGradient(asList, null, 0L, j, 0, null), 1.0f, RectangleShapeKt.RectangleShape, InspectableValueKt.NoInspectorInfo, 1, null));
            BoxKt.Box(then, composerImpl, 0);
            BackgroundTopScrim(boxScopeInstance, composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalContainerKt$$ExternalSyntheticLambda33(boxScopeInstance, i, 3);
        }
    }
}
