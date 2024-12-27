package com.android.systemui.communal.ui.compose;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.BackgroundElement;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.LinearGradient;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.TileMode;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.animation.ExpandableKt$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.BaseTransitionBuilder;
import com.android.compose.animation.scene.BaseTransitionBuilderImpl;
import com.android.compose.animation.scene.CommunalSwipeDetector;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.FixedSizeEdgeDetector;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.ObservableTransitionStateKt;
import com.android.compose.animation.scene.PassthroughSwipeDetector;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneScope;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl$updateScenes$1;
import com.android.compose.animation.scene.SceneTransitionLayoutKt;
import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.SceneTransitionsBuilderImpl;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDetectorKt;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.TransitionBuilderImpl;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.compose.animation.scene.transformation.Translate;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalBackgroundType;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.ui.compose.extensions.ModifierExtKt;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.util.CommunalColors;
import com.android.systemui.communal.util.CommunalColorsImpl;
import com.android.systemui.scene.shared.model.SceneDataSourceDelegator;
import com.android.systemui.scene.ui.composable.SceneTransitionLayoutDataSource;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class CommunalContainerKt {
    public static final float ANIMATION_OFFSCREEN_OFFSET;
    public static final SceneTransitions sceneTransitions = TransitionDslKt.transitions(new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = (SceneTransitionsBuilderImpl) obj;
            SceneKey sceneKey = CommunalScenes.Communal;
            CommunalTransitionKeys.INSTANCE.getClass();
            sceneTransitionsBuilderImpl.transition(null, sceneKey, CommunalTransitionKeys.SimpleFade, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj2;
                    transitionBuilderImpl.spec = AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 0, null, 6);
                    transitionBuilderImpl.fade(AllElements.INSTANCE);
                    return Unit.INSTANCE;
                }
            });
            sceneTransitionsBuilderImpl.transition(null, sceneKey, null, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1.2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj2;
                    transitionBuilderImpl.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                    Communal$Elements.INSTANCE.getClass();
                    BaseTransitionBuilder.translate$default(transitionBuilderImpl, Communal$Elements.Grid, Edge.Right);
                    transitionBuilderImpl.timestampRange(167, 334, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.2.1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3)).fade(AllElements.INSTANCE);
                            return Unit.INSTANCE;
                        }
                    });
                    return Unit.INSTANCE;
                }
            });
            SceneKey sceneKey2 = CommunalScenes.Blank;
            sceneTransitionsBuilderImpl.transition(null, sceneKey2, null, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1.3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj2;
                    transitionBuilderImpl.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                    Communal$Elements.INSTANCE.getClass();
                    BaseTransitionBuilder.translate$default(transitionBuilderImpl, Communal$Elements.Grid, Edge.Right);
                    transitionBuilderImpl.timestampRange(null, 167, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.3.1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Communal$Elements.INSTANCE.getClass();
                            BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3);
                            baseTransitionBuilderImpl.fade(Communal$Elements.Grid);
                            baseTransitionBuilderImpl.fade(Communal$Elements.IndicationArea);
                            baseTransitionBuilderImpl.fade(Communal$Elements.LockIcon);
                            baseTransitionBuilderImpl.fade(Communal$Elements.StatusBar);
                            return Unit.INSTANCE;
                        }
                    });
                    transitionBuilderImpl.timestampRange(167, 334, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.3.2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Communal$Elements.INSTANCE.getClass();
                            ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3)).fade(Communal$Elements.Scrim);
                            return Unit.INSTANCE;
                        }
                    });
                    return Unit.INSTANCE;
                }
            });
            sceneTransitionsBuilderImpl.transition(null, sceneKey2, CommunalTransitionKeys.ToEditMode, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1.4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj2;
                    transitionBuilderImpl.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                    transitionBuilderImpl.timestampRange(null, Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend), new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.4.1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Communal$Elements.INSTANCE.getClass();
                            BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3);
                            baseTransitionBuilderImpl.fade(Communal$Elements.Grid);
                            baseTransitionBuilderImpl.fade(Communal$Elements.IndicationArea);
                            baseTransitionBuilderImpl.fade(Communal$Elements.LockIcon);
                            return Unit.INSTANCE;
                        }
                    });
                    Communal$Elements.INSTANCE.getClass();
                    transitionBuilderImpl.fade(Communal$Elements.Scrim);
                    return Unit.INSTANCE;
                }
            });
            sceneTransitionsBuilderImpl.transition(null, sceneKey, CommunalTransitionKeys.FromEditMode, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$sceneTransitions$1.5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj2;
                    transitionBuilderImpl.spec = AnimationSpecKt.tween$default(1000, 0, null, 6);
                    Communal$Elements.INSTANCE.getClass();
                    ElementKey elementKey = Communal$Elements.Grid;
                    Dimensions.INSTANCE.getClass();
                    float f = Dimensions.SlideOffsetY;
                    Dp.Companion companion = Dp.Companion;
                    TransitionBuilderImpl transitionBuilderImpl2 = transitionBuilderImpl;
                    transitionBuilderImpl2.getClass();
                    transitionBuilderImpl2.transformation(new Translate(elementKey, 0, f, null));
                    transitionBuilderImpl.timestampRange(null, 167, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.5.1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Communal$Elements.INSTANCE.getClass();
                            BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3);
                            baseTransitionBuilderImpl.fade(Communal$Elements.IndicationArea);
                            baseTransitionBuilderImpl.fade(Communal$Elements.LockIcon);
                            baseTransitionBuilderImpl.fade(Communal$Elements.Scrim);
                            return Unit.INSTANCE;
                        }
                    });
                    transitionBuilderImpl.timestampRange(167, 334, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt.sceneTransitions.1.5.2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Communal$Elements.INSTANCE.getClass();
                            ((BaseTransitionBuilderImpl) ((BaseTransitionBuilder) obj3)).fade(Communal$Elements.Grid);
                            return Unit.INSTANCE;
                        }
                    });
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    });

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CommunalBackgroundType.values().length];
            try {
                iArr[CommunalBackgroundType.DEFAULT.ordinal()] = 1;
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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
        ANIMATION_OFFSCREEN_OFFSET = 128;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b0, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ef, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedLinearGradient(final androidx.compose.foundation.layout.BoxScope r21, androidx.compose.runtime.Composer r22, final int r23) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalContainerKt.AnimatedLinearGradient(androidx.compose.foundation.layout.BoxScope, androidx.compose.runtime.Composer, int):void");
    }

    public static final void BackgroundTopScrim(final BoxScope boxScope, Composer composer, final int i) {
        int i2;
        long j;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1955833836);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (DarkThemeKt.isSystemInDarkTheme(composerImpl)) {
                Color.Companion.getClass();
                j = Color.Black;
            } else {
                Color.Companion.getClass();
                j = Color.White;
            }
            BoxKt.Box(BackgroundKt.m24backgroundbw27NRU(AlphaKt.alpha(boxScope.matchParentSize(Modifier.Companion), 0.34f), j, RectangleShapeKt.RectangleShape), composerImpl, 0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$BackgroundTopScrim$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContainerKt.BackgroundTopScrim(BoxScope.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CommunalContainer(Modifier modifier, final CommunalViewModel communalViewModel, final SceneDataSourceDelegator sceneDataSourceDelegator, final CommunalColors communalColors, final CommunalContent communalContent, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(561132849);
        Modifier modifier2 = (i2 & 1) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl), composerImpl);
        }
        final CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.currentScene, CommunalScenes.Blank, composerImpl, 56);
        Boolean bool = Boolean.FALSE;
        MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.touchesAllowed, bool, composerImpl, 56);
        final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.showGestureIndicator, bool, composerImpl, 56);
        final MutableState collectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.communalBackground, CommunalBackgroundType.DEFAULT, composerImpl, 56);
        composerImpl.startReplaceGroup(-2018776070);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new MutableSceneTransitionLayoutStateImpl((SceneKey) collectAsStateWithLifecycle.getValue(), sceneTransitions, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$state$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(!((Boolean) ((ShadeInteractorImpl) CommunalViewModel.this.shadeInteractor).isAnyFullyExpanded.$$delegate_0.getValue()).booleanValue());
                }
            }, EmptyList.INSTANCE, false);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        final MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState = (MutableSceneTransitionLayoutState) rememberedValue2;
        Object m = ExpandableKt$$ExternalSyntheticOutline0.m(composerImpl, false, -2018775785);
        if (m == composer$Companion$Empty$1) {
            m = new CommunalSwipeDetector(null, 1, null);
            composerImpl.updateRememberedValue(m);
        }
        composerImpl.end(false);
        EffectsKt.DisposableEffect(mutableSceneTransitionLayoutState, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                sceneDataSourceDelegator.setDelegate(new SceneTransitionLayoutDataSource(MutableSceneTransitionLayoutState.this, coroutineScope));
                final SceneDataSourceDelegator sceneDataSourceDelegator2 = sceneDataSourceDelegator;
                return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$1$invoke$$inlined$onDispose$1
                    @Override // androidx.compose.runtime.DisposableEffectResult
                    public final void dispose() {
                        SceneDataSourceDelegator.this.setDelegate(null);
                    }
                };
            }
        }, composerImpl);
        EffectsKt.DisposableEffect(communalViewModel, mutableSceneTransitionLayoutState, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CommunalViewModel communalViewModel2 = CommunalViewModel.this;
                ((CommunalSceneRepositoryImpl) communalViewModel2.communalSceneInteractor.communalSceneRepository)._transitionState.setValue(ObservableTransitionStateKt.observableTransitionState(mutableSceneTransitionLayoutState));
                final CommunalViewModel communalViewModel3 = CommunalViewModel.this;
                return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$2$invoke$$inlined$onDispose$1
                    @Override // androidx.compose.runtime.DisposableEffectResult
                    public final void dispose() {
                        ((CommunalSceneRepositoryImpl) CommunalViewModel.this.communalSceneInteractor.communalSceneRepository)._transitionState.setValue(null);
                    }
                };
            }
        }, composerImpl);
        composerImpl.startReplaceGroup(-2018775122);
        Flags.glanceableHubFullscreenSwipe();
        FixedSizeEdgeDetector fixedSizeEdgeDetector = new FixedSizeEdgeDetector(PrimitiveResources_androidKt.dimensionResource(R.dimen.communal_gesture_initiation_width, composerImpl), null);
        composerImpl.end(false);
        Flags.glanceableHubFullscreenSwipe();
        PassthroughSwipeDetector passthroughSwipeDetector = SwipeDetectorKt.DefaultSwipeDetector;
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        SceneTransitionLayoutKt.SceneTransitionLayout(mutableSceneTransitionLayoutState, modifier2.then(fillElement), fixedSizeEdgeDetector, passthroughSwipeDetector, 0.0f, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SceneTransitionLayoutImpl$updateScenes$1 sceneTransitionLayoutImpl$updateScenes$1 = (SceneTransitionLayoutImpl$updateScenes$1) obj;
                SceneKey sceneKey = CommunalScenes.Blank;
                Swipe swipe = new Swipe(SwipeDirection.Left, 0, Edge.Right, 2, null);
                SceneKey sceneKey2 = CommunalScenes.Communal;
                Map mapOf = MapsKt__MapsJVMKt.mapOf(swipe.to(sceneKey2));
                final State state = State.this;
                sceneTransitionLayoutImpl$updateScenes$1.scene(sceneKey, mapOf, new ComposableLambdaImpl(1926827780, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$3.1
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 81) == 16) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        Modifier.Companion companion = Modifier.Companion;
                        FillElement fillElement2 = SizeKt.FillWholeMaxSize;
                        companion.then(fillElement2);
                        Arrangement.INSTANCE.getClass();
                        Arrangement$End$1 arrangement$End$1 = Arrangement.End;
                        State state2 = State.this;
                        Alignment.Companion.getClass();
                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$End$1, Alignment.Companion.Top, composer2, 6);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, fillElement2);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (!(composerImpl3.applier instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function0);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m276setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m276setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                        }
                        Updater.m276setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        composerImpl3.startReplaceGroup(747404414);
                        SceneTransitions sceneTransitions2 = CommunalContainerKt.sceneTransitions;
                        if (((Boolean) state2.getValue()).booleanValue()) {
                            Flags.FEATURE_FLAGS.getClass();
                        }
                        composerImpl3.end(false);
                        composerImpl3.end(true);
                        return Unit.INSTANCE;
                    }
                }));
                Map mapOf2 = MapsKt__MapsJVMKt.mapOf(new Swipe(SwipeDirection.Right, 0, Edge.Left, 2, null).to(sceneKey));
                final CommunalColors communalColors2 = communalColors;
                final CommunalContent communalContent2 = communalContent;
                final State state2 = collectAsStateWithLifecycle4;
                sceneTransitionLayoutImpl$updateScenes$1.scene(sceneKey2, mapOf2, new ComposableLambdaImpl(-570310163, true, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$3.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        SceneScope sceneScope = (SceneScope) obj2;
                        Composer composer2 = (Composer) obj3;
                        int intValue = ((Number) obj4).intValue();
                        if ((intValue & 14) == 0) {
                            intValue |= ((ComposerImpl) composer2).changed(sceneScope) ? 4 : 2;
                        }
                        if ((intValue & 91) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        State state3 = state2;
                        SceneTransitions sceneTransitions2 = CommunalContainerKt.sceneTransitions;
                        CommunalContainerKt.access$CommunalScene(sceneScope, (CommunalBackgroundType) state3.getValue(), CommunalColors.this, communalContent2, null, composer2, (intValue & 14) | 4096, 8);
                        return Unit.INSTANCE;
                    }
                }));
                return Unit.INSTANCE;
            }
        }, composerImpl, 518, 16);
        Modifier.Companion.getClass();
        BoxKt.Box(ModifierExtKt.allowGestures(fillElement, ((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue()), composerImpl, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalContainer$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContainerKt.CommunalContainer(Modifier.this, communalViewModel, sceneDataSourceDelegator, communalColors, communalContent, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void DefaultBackground(final BoxScope boxScope, final CommunalColors communalColors, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2110767570);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(communalColors) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            BoxKt.Box(BackgroundKt.m24backgroundbw27NRU(boxScope.matchParentSize(Modifier.Companion), ColorKt.Color(((android.graphics.Color) FlowExtKt.collectAsStateWithLifecycle(((CommunalColorsImpl) communalColors).backgroundColor, composerImpl).getValue()).toArgb()), RectangleShapeKt.RectangleShape), composerImpl, 0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$DefaultBackground$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContainerKt.DefaultBackground(BoxScope.this, communalColors, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void StaticLinearGradient(final BoxScope boxScope, Composer composer, final int i) {
        int i2;
        Modifier then;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1743878288);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(boxScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
            Modifier matchParentSize = boxScope.matchParentSize(Modifier.Companion);
            Brush.Companion companion = Brush.Companion;
            List listOf = CollectionsKt__CollectionsKt.listOf(Color.m383boximpl(androidColorScheme.primary), Color.m383boximpl(androidColorScheme.primaryContainer));
            Offset.Companion.getClass();
            long j = Offset.Infinite;
            TileMode.Companion.getClass();
            companion.getClass();
            then = matchParentSize.then(new BackgroundElement(0L, new LinearGradient(listOf, null, 0L, j, 0, null), 1.0f, RectangleShapeKt.RectangleShape, InspectableValueKt.NoInspectorInfo, 1, null));
            BoxKt.Box(then, composerImpl, 0);
            BackgroundTopScrim(boxScope, composerImpl, i2 & 14);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$StaticLinearGradient$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContainerKt.StaticLinearGradient(BoxScope.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$CommunalScene(final SceneScope sceneScope, final CommunalBackgroundType communalBackgroundType, final CommunalColors communalColors, final CommunalContent communalContent, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1724885992);
        Modifier modifier2 = (i2 & 8) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion;
        Communal$Elements.INSTANCE.getClass();
        Modifier then = sceneScope.element(companion, Communal$Elements.Scrim).then(SizeKt.FillWholeMaxSize);
        Alignment.Companion.getClass();
        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
        int i4 = WhenMappings.$EnumSwitchMapping$0[communalBackgroundType.ordinal()];
        if (i4 == 1) {
            composerImpl.startReplaceGroup(620768203);
            DefaultBackground(boxScopeInstance, communalColors, composerImpl, ((i >> 3) & 112) | 6);
            composerImpl.end(false);
        } else if (i4 == 2) {
            composerImpl.startReplaceGroup(620768292);
            StaticLinearGradient(boxScopeInstance, composerImpl, 6);
            composerImpl.end(false);
        } else if (i4 == 3) {
            composerImpl.startReplaceGroup(620768362);
            AnimatedLinearGradient(boxScopeInstance, composerImpl, 6);
            composerImpl.end(false);
        } else if (i4 != 4) {
            composerImpl.startReplaceGroup(620768460);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(620768430);
            BackgroundTopScrim(boxScopeInstance, composerImpl, 6);
            composerImpl.end(false);
        }
        composerImpl.end(true);
        communalContent.Content(sceneScope, modifier2, composerImpl, (i & 14) | 512 | ((i >> 9) & 112), 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$CommunalScene$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalContainerKt.access$CommunalScene(SceneScope.this, communalBackgroundType, communalColors, communalContent, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
