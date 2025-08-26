package com.android.systemui.qs.composefragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.SystemGestureExclusionKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LookaheadScopeKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnPlacedModifierKt;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.ColorResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionLayoutKt;
import com.android.compose.animation.scene.SceneTransitionLayoutStateKt;
import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.modifiers.PaddingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda1;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.brightness.ui.compose.BrightnessSliderKt;
import com.android.systemui.brightness.ui.compose.ContainerColors;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyboard.shortcut.ui.composable.InteractionsConfig;
import com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.composefragment.ui.GridAnchorKt;
import com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.qs.footer.ui.compose.FooterActionsKt;
import com.android.systemui.qs.panels.ui.compose.EditModeKt;
import com.android.systemui.qs.panels.ui.compose.QuickQuickSettingsKt;
import com.android.systemui.qs.panels.ui.compose.TileGridKt;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.shared.ui.ElementKeys;
import com.android.systemui.qs.ui.composable.QuickSettingsShade;
import com.android.systemui.qs.ui.composable.QuickSettingsThemeKt;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LifecycleFragment;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class QSFragmentCompose extends LifecycleFragment implements QS, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DumpManager dumpManager;
    public final QSFragmentComposeViewModel.Factory qsFragmentComposeViewModelFactory;
    public QSFragmentComposeViewModel viewModel;
    public final StateFlowImpl scrollListener = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl collapsedMediaVisibilityChangedListener = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl heightListener = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl qsContainerController = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl qqsVisible = StateFlowKt.MutableStateFlow(Boolean.FALSE);
    public final Rect qqsPositionOnRoot = new Rect();
    public final Rect composeViewPositionOnScreen = new Rect();
    public final ScrollState scrollState = new ScrollState(0);
    public final int[] locationTemp = new int[2];
    public final QSFragmentCompose$notificationScrimClippingParams$1 notificationScrimClippingParams = new QSFragmentCompose$notificationScrimClippingParams$1();

    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$onCreate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSFragmentCompose.this.new AnonymousClass1(continuation);
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
                QSFragmentComposeViewModel qSFragmentComposeViewModel = QSFragmentCompose.this.viewModel;
                if (qSFragmentComposeViewModel == null) {
                    qSFragmentComposeViewModel = null;
                }
                this.label = 1;
                if (qSFragmentComposeViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public QSFragmentCompose(QSFragmentComposeViewModel.Factory factory, DumpManager dumpManager) {
        this.qsFragmentComposeViewModelFactory = factory;
        this.dumpManager = dumpManager;
    }

    public final void CollapsableQuickSettingsSTL(int i, Composer composer) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-778098946);
        if ((((composerImpl2.changedInstance(this) ? 4 : 2) | i) & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL (QSFragmentCompose.kt:306)");
            }
            composerImpl2.startReplaceGroup(1344082843);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = new QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1();
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            Object obj2 = (QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1344086626);
            if (objM == obj) {
                objM = new LinkedHashMap();
                composerImpl2.updateRememberedValue(objM);
            }
            Object obj3 = (Map) objM;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1344092124);
            if (objM2 == obj) {
                SceneKeys sceneKeys = SceneKeys.INSTANCE;
                QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
                if (qSFragmentComposeViewModel == null) {
                    qSFragmentComposeViewModel = null;
                }
                QSFragmentComposeViewModel.QSExpansionState qSExpansionState = (QSFragmentComposeViewModel.QSExpansionState) qSFragmentComposeViewModel.expansionState$delegate.getValue();
                sceneKeys.getClass();
                objM2 = qSExpansionState.progress < 0.5f ? SceneKeys.QuickQuickSettings : SceneKeys.QuickSettings;
                composerImpl2.updateRememberedValue(objM2);
            }
            SceneKey sceneKey = (SceneKey) objM2;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1344096259);
            boolean zChangedInstance = composerImpl2.changedInstance(this);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == obj) {
                objRememberedValue2 = new QSFragmentCompose$$ExternalSyntheticLambda4(this, 0);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            composerImpl2.end(false);
            SceneTransitions sceneTransitionsTransitions = TransitionDslKt.transitions((Function1) objRememberedValue2);
            composerImpl2.startReplaceGroup(1344110601);
            boolean zChangedInstance2 = composerImpl2.changedInstance(obj2) | composerImpl2.changedInstance(obj3);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChangedInstance2 || objRememberedValue3 == obj) {
                objRememberedValue3 = new QSFragmentCompose$$ExternalSyntheticLambda5(0, obj2, obj3);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            Function1 function1 = (Function1) objRememberedValue3;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1344122012);
            boolean zChangedInstance3 = composerImpl2.changedInstance(obj3);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (zChangedInstance3 || objRememberedValue4 == obj) {
                objRememberedValue4 = new QSFragmentCompose$$ExternalSyntheticLambda6(obj3, 0);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            composerImpl2.end(false);
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState = SceneTransitionLayoutStateKt.rememberMutableSceneTransitionLayoutState(sceneKey, sceneTransitionsTransitions, null, null, null, function1, (Function1) objRememberedValue4, composerImpl2, (SceneTransitions.$stable << 3) | 6, 636);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(1344131348);
            boolean zChanged = composerImpl2.changed(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState) | composerImpl2.changedInstance(this);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue5 == obj) {
                objRememberedValue5 = new QSFragmentCompose$CollapsableQuickSettingsSTL$1$1(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, this, null);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue5);
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(1344142293);
            boolean zChangedInstance4 = composerImpl2.changedInstance(this);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChangedInstance4 || objRememberedValue6 == obj) {
                objRememberedValue6 = new QSFragmentCompose$$ExternalSyntheticLambda4(this, 4);
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            composerImpl2.end(false);
            SceneTransitionLayoutKt.SceneTransitionLayout(mutableSceneTransitionLayoutStateImplRememberMutableSceneTransitionLayoutState, modifierFillMaxSize, null, null, 0.0f, (Function1) objRememberedValue6, composerImpl2, 48, 60);
            composerImpl = composerImpl2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda3(this, i, 1);
        }
    }

    public final void Content$1(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1022911894);
        if ((((composerImpl.changedInstance(this) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.Content (QSFragmentCompose.kt:259)");
            }
            composerImpl.startReplaceGroup(926664142);
            composerImpl.end(false);
            PlatformThemeKt.PlatformTheme(true, ComposableLambdaKt.rememberComposableLambda(-1141656652, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$Content$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.Content.<anonymous> (QSFragmentCompose.kt:261)");
                            }
                            QSFragmentComposeKt$instanceProvider$1 qSFragmentComposeKt$instanceProvider$1 = QSFragmentComposeKt.instanceProvider;
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(1617757491);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.interactionsConfig (QSFragmentCompose.kt:1299)");
                            }
                            MaterialTheme.INSTANCE.getClass();
                            Dp.Companion companion = Dp.Companion;
                            InteractionsConfig interactionsConfig = new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl3).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl3).onSurface, 0.15f, 0L, 0.0f, 0.0f, 28, 0.0f, 0.0f, 0.0f, 1904, null);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl3.end(false);
                            final QSFragmentCompose qSFragmentCompose = this.this$0;
                            SurfacesKt.ProvideShortcutHelperIndication(interactionsConfig, ComposableLambdaKt.rememberComposableLambda(201376207, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$Content$1.1
                                /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        if (composerImpl4.getSkipping()) {
                                            composerImpl4.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.Content.<anonymous>.<anonymous> (QSFragmentCompose.kt:264)");
                                            }
                                            final QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                            QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose2.viewModel;
                                            if (qSFragmentComposeViewModel == null) {
                                                qSFragmentComposeViewModel = null;
                                            }
                                            if (qSFragmentComposeViewModel.isQsVisibleAndAnyShadeExpanded()) {
                                                Modifier.Companion companion2 = Modifier.Companion;
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(-1411579153);
                                                boolean zChangedInstance = composerImpl5.changedInstance(qSFragmentCompose2);
                                                Object objRememberedValue = composerImpl5.rememberedValue();
                                                if (!zChangedInstance) {
                                                    Composer.Companion.getClass();
                                                    if (objRememberedValue == Composer.Companion.Empty) {
                                                        objRememberedValue = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose2, 5);
                                                        composerImpl5.updateRememberedValue(objRememberedValue);
                                                    }
                                                    composerImpl5.end(false);
                                                    Modifier modifierThen = GraphicsLayerModifierKt.graphicsLayer(companion2, (Function1) objRememberedValue).then(OffsetKt.offset(companion2, new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$Content$1$1$3$1
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj5) {
                                                            QSFragmentComposeViewModel qSFragmentComposeViewModel2 = qSFragmentCompose2.viewModel;
                                                            if (qSFragmentComposeViewModel2 == null) {
                                                                qSFragmentComposeViewModel2 = null;
                                                            }
                                                            return IntOffset.m849boximpl((Math.round(((Number) qSFragmentComposeViewModel2.viewTranslationY$delegate.getValue()).floatValue()) & 4294967295L) | (0 << 32));
                                                        }
                                                    }));
                                                    QSFragmentComposeViewModel qSFragmentComposeViewModel2 = qSFragmentCompose2.viewModel;
                                                    if (qSFragmentComposeViewModel2 == null) {
                                                        qSFragmentComposeViewModel2 = null;
                                                    }
                                                    boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel2.containerViewModel.brightnessSliderViewModel.showMirror$delegate).getValue()).booleanValue();
                                                    QSFragmentComposeKt$instanceProvider$1 qSFragmentComposeKt$instanceProvider$12 = QSFragmentComposeKt.instanceProvider;
                                                    if (zBooleanValue) {
                                                        modifierThen = SuspendingPointerInputFilterKt.pointerInput(modifierThen, Unit.INSTANCE, new PointerInputEventHandler() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$gesturesDisabled$1

                                                            /* renamed from: com.android.systemui.qs.composefragment.QSFragmentComposeKt$gesturesDisabled$1$1, reason: invalid class name */
                                                            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                                                                private /* synthetic */ Object L$0;
                                                                int label;

                                                                public AnonymousClass1(Continuation continuation) {
                                                                    super(2, continuation);
                                                                }

                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                public final Continuation create(Object obj, Continuation continuation) {
                                                                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
                                                                    anonymousClass1.L$0 = obj;
                                                                    return anonymousClass1;
                                                                }

                                                                @Override // kotlin.jvm.functions.Function2
                                                                public final Object invoke(Object obj, Object obj2) {
                                                                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                }

                                                                /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                                                                    jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
                                                                    	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                                                                    	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                                                                    	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                                                                    */
                                                                /* JADX WARN: Removed duplicated region for block: B:11:0x002d A[RETURN] */
                                                                /* JADX WARN: Removed duplicated region for block: B:15:0x003e A[LOOP:0: B:13:0x0038->B:15:0x003e, LOOP_END] */
                                                                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x002b -> B:12:0x002e). Please report as a decompilation issue!!! */
                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final java.lang.Object invokeSuspend(java.lang.Object r5) {
                                                                    /*
                                                                        r4 = this;
                                                                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                                        int r1 = r4.label
                                                                        r2 = 1
                                                                        if (r1 == 0) goto L19
                                                                        if (r1 != r2) goto L11
                                                                        java.lang.Object r1 = r4.L$0
                                                                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                                                                        kotlin.ResultKt.throwOnFailure(r5)
                                                                        goto L2e
                                                                    L11:
                                                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                                        r4.<init>(r5)
                                                                        throw r4
                                                                    L19:
                                                                        kotlin.ResultKt.throwOnFailure(r5)
                                                                        java.lang.Object r5 = r4.L$0
                                                                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r5 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r5
                                                                        r1 = r5
                                                                    L21:
                                                                        androidx.compose.ui.input.pointer.PointerEventPass r5 = androidx.compose.ui.input.pointer.PointerEventPass.Initial
                                                                        r4.L$0 = r1
                                                                        r4.label = r2
                                                                        java.lang.Object r5 = r1.awaitPointerEvent(r5, r4)
                                                                        if (r5 != r0) goto L2e
                                                                        return r0
                                                                    L2e:
                                                                        androidx.compose.ui.input.pointer.PointerEvent r5 = (androidx.compose.ui.input.pointer.PointerEvent) r5
                                                                        java.util.List r5 = r5.changes
                                                                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                                                                        java.util.Iterator r5 = r5.iterator()
                                                                    L38:
                                                                        boolean r3 = r5.hasNext()
                                                                        if (r3 == 0) goto L21
                                                                        java.lang.Object r3 = r5.next()
                                                                        androidx.compose.ui.input.pointer.PointerInputChange r3 = (androidx.compose.ui.input.pointer.PointerInputChange) r3
                                                                        r3.consume()
                                                                        goto L38
                                                                    */
                                                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentComposeKt$gesturesDisabled$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                                                                }
                                                            }

                                                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                                Object objAwaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new AnonymousClass1(null), continuation);
                                                                return objAwaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitPointerEventScope : Unit.INSTANCE;
                                                            }
                                                        });
                                                    }
                                                    Alignment.Companion.getClass();
                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl5, modifierThen);
                                                    ComposeUiNode.Companion.getClass();
                                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                    if (composerImpl5.applier == null) {
                                                        ComposablesKt.invalidApplier();
                                                        throw null;
                                                    }
                                                    composerImpl5.startReusableNode();
                                                    if (composerImpl5.inserting) {
                                                        composerImpl5.createNode(function0);
                                                    } else {
                                                        composerImpl5.useNode();
                                                    }
                                                    Updater.m337setimpl(composerImpl5, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                    Updater.m337setimpl(composerImpl5, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                    if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                                                    }
                                                    Updater.m337setimpl(composerImpl5, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                    qSFragmentCompose2.CollapsableQuickSettingsSTL(0, composerImpl5);
                                                    composerImpl5.end(true);
                                                }
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 48);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda3(this, i, 0);
        }
    }

    public final void EditModeElement(final Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(168832265);
        if (((i | 6 | (composerImpl.changedInstance(this) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.EditModeElement (QSFragmentCompose.kt:844)");
            }
            QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
            if (qSFragmentComposeViewModel == null) {
                qSFragmentComposeViewModel = null;
            }
            EditModeViewModel editModeViewModel = qSFragmentComposeViewModel.containerViewModel.editModeViewModel;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            composerImpl.startReplaceGroup(1160342269);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new QSFragmentCompose$$ExternalSyntheticLambda9(0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            EditModeKt.EditMode(editModeViewModel, PaddingKt.padding$default(modifierFillMaxWidth, (Function1) objRememberedValue, null, 2), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(companion, i) { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda10
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int i2 = QSFragmentCompose.$r8$clinit;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    this.f$0.EditModeElement(this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void QuickQuickSettingsElement(final ContentScope contentScope, Modifier modifier, Composer composer, int i) {
        Composer.Companion companion;
        boolean zChanged;
        boolean z;
        Modifier modifier2;
        int i2;
        Modifier modifierSemantics;
        int currentCompositeKeyHash;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(883424252);
        if (((i | (composerImpl.changed(contentScope) ? 4 : 2) | 48 | (composerImpl.changedInstance(this) ? 256 : 128)) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            Modifier.Companion companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickQuickSettingsElement (QSFragmentCompose.kt:604)");
            }
            QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
            if (qSFragmentComposeViewModel == null) {
                qSFragmentComposeViewModel = null;
            }
            final int iIntValue = ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsHeaderHeight$delegate).getValue()).intValue();
            QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
            if (qSFragmentComposeViewModel2 == null) {
                qSFragmentComposeViewModel2 = null;
            }
            final int iIntValue2 = ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel2.qqsBottomPadding$delegate).getValue()).intValue();
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(-267354283);
            boolean zChangedInstance = composerImpl.changedInstance(this);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            if (!zChangedInstance) {
                companion3.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new QSFragmentCompose$$ExternalSyntheticLambda4(this, 3);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue, composerImpl);
                QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.viewModel;
                if (qSFragmentComposeViewModel3 == null) {
                    qSFragmentComposeViewModel3 = null;
                }
                MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(qSFragmentComposeViewModel3.quickQuickSettingsViewModel.squishinessViewModel.squishiness, composerImpl);
                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(companion2, "quick_qs_panel");
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSysuiResTag);
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
                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
                composerImpl.startReplaceGroup(1191907609);
                boolean zChangedInstance2 = composerImpl.changedInstance(this) | composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (zChangedInstance2) {
                    companion = companion3;
                } else {
                    companion3.getClass();
                    companion = companion3;
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                    }
                    composerImpl.end(false);
                    Modifier modifierOnPlaced = OnPlacedModifierKt.onPlaced(modifierFillMaxWidth, (Function1) objRememberedValue2);
                    composerImpl.startReplaceGroup(1191936709);
                    zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (zChanged) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new QSFragmentCompose$$ExternalSyntheticLambda6(mutableStateCollectAsStateWithLifecycle, 1);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        Function1 function1 = (Function1) objRememberedValue3;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(1191937746);
                        boolean zChangedInstance3 = composerImpl.changedInstance(this);
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        if (!zChangedInstance3) {
                            companion.getClass();
                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                objRememberedValue4 = new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda14
                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                        ApproachMeasureScope approachMeasureScope = (ApproachMeasureScope) obj;
                                        Measurable measurable = (Measurable) obj2;
                                        Constraints constraints = (Constraints) obj3;
                                        QSFragmentComposeViewModel qSFragmentComposeViewModel4 = this.f$0.viewModel;
                                        if (qSFragmentComposeViewModel4 == null) {
                                            qSFragmentComposeViewModel4 = null;
                                        }
                                        int iMo604getLookaheadSizeYbymL2g = (int) (approachMeasureScope.mo604getLookaheadSizeYbymL2g() & 4294967295L);
                                        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel4.qqsHeight$delegate).setValue(Integer.valueOf(iMo604getLookaheadSizeYbymL2g));
                                        Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(constraints.value);
                                        return approachMeasureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new QSFragmentCompose$$ExternalSyntheticLambda6(placeableMo610measureBRTryo0, 2));
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue4);
                            }
                            composerImpl.end(false);
                            Modifier modifierApproachLayout = LookaheadScopeKt.approachLayout(modifierOnPlaced, function1, LookaheadScopeKt.defaultPlacementApproachInProgress, (Function3) objRememberedValue4);
                            composerImpl.startReplaceGroup(1191949983);
                            boolean zChanged2 = composerImpl.changed(iIntValue);
                            Object objRememberedValue5 = composerImpl.rememberedValue();
                            if (!zChanged2) {
                                companion.getClass();
                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                    objRememberedValue5 = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda15
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            int i3 = QSFragmentCompose.$r8$clinit;
                                            return Integer.valueOf(iIntValue);
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue5);
                                }
                                Function1 function12 = (Function1) objRememberedValue5;
                                composerImpl.end(false);
                                composerImpl.startReplaceGroup(1191950786);
                                boolean zChanged3 = composerImpl.changed(iIntValue2);
                                Object objRememberedValue6 = composerImpl.rememberedValue();
                                if (!zChanged3) {
                                    companion.getClass();
                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                        objRememberedValue6 = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda15
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                int i3 = QSFragmentCompose.$r8$clinit;
                                                return Integer.valueOf(iIntValue2);
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue6);
                                    }
                                    composerImpl.end(false);
                                    Modifier modifierPadding$default = PaddingKt.padding$default(modifierApproachLayout, function12, null, (Function1) objRememberedValue6, 5);
                                    BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierPadding$default);
                                    composerImpl.startReusableNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(function0);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                                    }
                                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-473596109, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickQuickSettingsElement$2$6$Tiles$1
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickQuickSettingsElement.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:646)");
                                                    }
                                                    QSFragmentCompose qSFragmentCompose = this;
                                                    QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentCompose.viewModel;
                                                    if (qSFragmentComposeViewModel4 == null) {
                                                        qSFragmentComposeViewModel4 = null;
                                                    }
                                                    QuickQuickSettingsViewModel quickQuickSettingsViewModel = qSFragmentComposeViewModel4.quickQuickSettingsViewModel;
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                    composerImpl3.startReplaceGroup(-22169742);
                                                    boolean zChangedInstance4 = composerImpl3.changedInstance(qSFragmentCompose);
                                                    Object objRememberedValue7 = composerImpl3.rememberedValue();
                                                    if (!zChangedInstance4) {
                                                        Composer.Companion.getClass();
                                                        if (objRememberedValue7 == Composer.Companion.Empty) {
                                                            objRememberedValue7 = new DrawInContainerNode$$ExternalSyntheticLambda1();
                                                            composerImpl3.updateRememberedValue(objRememberedValue7);
                                                        }
                                                        composerImpl3.end(false);
                                                        QuickQuickSettingsKt.QuickQuickSettings(contentScope, quickQuickSettingsViewModel, null, (Function0) objRememberedValue7, composerImpl3, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl);
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(867798964, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickQuickSettingsElement$2$6$Media$1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickQuickSettingsElement.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:664)");
                                                    }
                                                    QSFragmentCompose qSFragmentCompose = this.this$0;
                                                    QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentCompose.viewModel;
                                                    if (qSFragmentComposeViewModel4 == null) {
                                                        qSFragmentComposeViewModel4 = null;
                                                    }
                                                    if (qSFragmentComposeViewModel4.getQqsMediaVisible()) {
                                                        Modifier.Companion companion4 = Modifier.Companion;
                                                        Dp.Companion.getClass();
                                                        Modifier modifierM135requiredHeightInVpY3zN4$default = SizeKt.m135requiredHeightInVpY3zN4$default(companion4, 0.0f, Dp.Infinity, 1);
                                                        QSFragmentComposeViewModel qSFragmentComposeViewModel5 = qSFragmentCompose.viewModel;
                                                        QSFragmentComposeKt.MediaObject((qSFragmentComposeViewModel5 != null ? qSFragmentComposeViewModel5 : null).qqsMediaHost, modifierM135requiredHeightInVpY3zN4$default, null, composer2, 48, 4);
                                                    }
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl);
                                    composerImpl.startReplaceGroup(-1883674095);
                                    QSFragmentComposeViewModel qSFragmentComposeViewModel4 = this.viewModel;
                                    if (qSFragmentComposeViewModel4 == null) {
                                        qSFragmentComposeViewModel4 = null;
                                    }
                                    if (((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel4.isQsEnabled$delegate).getValue()).booleanValue()) {
                                        String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_quick_settings_expand, composerImpl);
                                        QSFragmentComposeViewModel qSFragmentComposeViewModel5 = this.viewModel;
                                        if (qSFragmentComposeViewModel5 == null) {
                                            qSFragmentComposeViewModel5 = null;
                                        }
                                        Runnable runnable = qSFragmentComposeViewModel5.collapseExpandAccessibilityAction;
                                        if (runnable != null) {
                                            i2 = 2;
                                            modifierSemantics = SemanticsModifierKt.semantics(companion2, false, new QSFragmentCompose$$ExternalSyntheticLambda5(i2, strStringResource, runnable));
                                            if (modifierSemantics == null) {
                                            }
                                            Modifier modifierM127paddingVpY3zN4$default = androidx.compose.foundation.layout.PaddingKt.m127paddingVpY3zN4$default(modifierSemantics, QSFragmentComposeKt.access$qsHorizontalMargin(composerImpl), 0.0f, i2);
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                            currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
                                            composerImpl.startReusableNode();
                                            if (composerImpl.inserting) {
                                                composerImpl.useNode();
                                            } else {
                                                composerImpl.createNode(function0);
                                            }
                                            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                                            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                                            if (!composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                                            }
                                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
                                            QSFragmentComposeViewModel qSFragmentComposeViewModel6 = this.viewModel;
                                            QSFragmentComposeKt.QuickQuickSettingsLayout(composableLambdaImplRememberComposableLambda, composableLambdaImplRememberComposableLambda2, (qSFragmentComposeViewModel6 != null ? null : qSFragmentComposeViewModel6).qqsMediaInRowViewModel.getShouldMediaShowInRow(), composerImpl, 54);
                                            z = true;
                                            composerImpl.end(true);
                                        } else {
                                            i2 = 2;
                                        }
                                        modifierSemantics = companion2;
                                        Modifier modifierM127paddingVpY3zN4$default2 = androidx.compose.foundation.layout.PaddingKt.m127paddingVpY3zN4$default(modifierSemantics, QSFragmentComposeKt.access$qsHorizontalMargin(composerImpl), 0.0f, i2);
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy22 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                        currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope32 = composerImpl.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier32 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default2);
                                        composerImpl.startReusableNode();
                                        if (composerImpl.inserting) {
                                        }
                                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy22, function2);
                                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope32, function22);
                                        if (!composerImpl.inserting) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier32, function24);
                                            QSFragmentComposeViewModel qSFragmentComposeViewModel62 = this.viewModel;
                                            QSFragmentComposeKt.QuickQuickSettingsLayout(composableLambdaImplRememberComposableLambda, composableLambdaImplRememberComposableLambda2, (qSFragmentComposeViewModel62 != null ? null : qSFragmentComposeViewModel62).qqsMediaInRowViewModel.getShouldMediaShowInRow(), composerImpl, 54);
                                            z = true;
                                            composerImpl.end(true);
                                        }
                                    } else {
                                        z = true;
                                    }
                                    composerImpl.end(false);
                                    composerImpl.end(z);
                                    SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, z));
                                    composerImpl.end(z);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    modifier2 = companion2;
                                }
                            }
                        }
                    }
                }
                objRememberedValue2 = new QSFragmentCompose$$ExternalSyntheticLambda5(1, this, mutableStateCollectAsStateWithLifecycle);
                composerImpl.updateRememberedValue(objRememberedValue2);
                composerImpl.end(false);
                Modifier modifierOnPlaced2 = OnPlacedModifierKt.onPlaced(modifierFillMaxWidth, (Function1) objRememberedValue2);
                composerImpl.startReplaceGroup(1191936709);
                zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle);
                Object objRememberedValue32 = composerImpl.rememberedValue();
                if (zChanged) {
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda16(this, contentScope, modifier2, i, 0);
        }
    }

    public final void QuickSettingsElement(final ContentScope contentScope, Modifier.Companion companion, Composer composer, int i) {
        Modifier modifierSemantics;
        Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(71497625);
        int i2 = i | (composerImpl.changed(contentScope) ? 4 : 2) | 48 | (composerImpl.changedInstance(this) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement (QSFragmentCompose.kt:699)");
            }
            QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
            if (qSFragmentComposeViewModel == null) {
                qSFragmentComposeViewModel = null;
            }
            final int iIntValue = ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsHeaderHeight$delegate).getValue()).intValue();
            final float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_panel_padding_top, composerImpl);
            String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_quick_settings_collapse, composerImpl);
            QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
            if (qSFragmentComposeViewModel2 == null) {
                qSFragmentComposeViewModel2 = null;
            }
            Runnable runnable = qSFragmentComposeViewModel2.collapseExpandAccessibilityAction;
            if (runnable == null || (modifierSemantics = SemanticsModifierKt.semantics(companion3, false, new QSFragmentCompose$$ExternalSyntheticLambda5(2, strStringResource, runnable))) == null) {
                modifierSemantics = companion3;
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(576080992);
            QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.viewModel;
            if (((Boolean) ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null).isQsEnabled$delegate).getValue()).booleanValue()) {
                ElementKeys.INSTANCE.getClass();
                contentScope.Element(ElementKeys.QuickSettingsContent, columnScopeInstance.weight(companion3, 1.0f, true), ComposableLambdaKt.rememberComposableLambda(1300083842, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0052  */
                    /* JADX WARN: Removed duplicated region for block: B:20:0x0083  */
                    /* JADX WARN: Removed duplicated region for block: B:25:0x00ac  */
                    /* JADX WARN: Removed duplicated region for block: B:30:0x00d5  */
                    /* JADX WARN: Removed duplicated region for block: B:49:0x017f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous> (QSFragmentCompose.kt:710)");
                                }
                                Unit unit = Unit.INSTANCE;
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-780252246);
                                final QSFragmentCompose qSFragmentCompose = this.this$0;
                                boolean zChangedInstance = composerImpl3.changedInstance(qSFragmentCompose);
                                Object objRememberedValue = composerImpl3.rememberedValue();
                                Composer.Companion companion4 = Composer.Companion;
                                if (!zChangedInstance) {
                                    companion4.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, 2);
                                        composerImpl3.updateRememberedValue(objRememberedValue);
                                    }
                                    composerImpl3.end(false);
                                    EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue, composerImpl3);
                                    Modifier.Companion companion5 = Modifier.Companion;
                                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion5, 1.0f);
                                    composerImpl3.startReplaceGroup(-780240701);
                                    boolean zChangedInstance2 = composerImpl3.changedInstance(qSFragmentCompose);
                                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                                    if (!zChangedInstance2) {
                                        companion4.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, 6);
                                            composerImpl3.updateRememberedValue(objRememberedValue2);
                                        }
                                        composerImpl3.end(false);
                                        Modifier modifierOnPlaced = OnPlacedModifierKt.onPlaced(modifierFillMaxSize, (Function1) objRememberedValue2);
                                        composerImpl3.startReplaceGroup(-780215755);
                                        boolean zChangedInstance3 = composerImpl3.changedInstance(qSFragmentCompose);
                                        Object objRememberedValue3 = composerImpl3.rememberedValue();
                                        if (!zChangedInstance3) {
                                            companion4.getClass();
                                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                                objRememberedValue3 = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, 7);
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            composerImpl3.end(false);
                                            Modifier modifierOffset = OffsetKt.offset(modifierOnPlaced, (Function1) objRememberedValue3);
                                            composerImpl3.startReplaceGroup(-780206119);
                                            boolean zChangedInstance4 = composerImpl3.changedInstance(qSFragmentCompose);
                                            Object objRememberedValue4 = composerImpl3.rememberedValue();
                                            if (!zChangedInstance4) {
                                                companion4.getClass();
                                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                                    objRememberedValue4 = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, 8);
                                                    composerImpl3.updateRememberedValue(objRememberedValue4);
                                                }
                                                composerImpl3.end(false);
                                                Modifier modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifierOffset, (Function1) objRememberedValue4);
                                                ScrollState scrollState = qSFragmentCompose.scrollState;
                                                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(ScrollingContainerKt.scrollingContainer(modifierOnSizeChanged, scrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollState, false, true)), "expanded_qs_scroll_view");
                                                Arrangement.INSTANCE.getClass();
                                                Arrangement$Top$1 arrangement$Top$12 = Arrangement.Top;
                                                Alignment.Companion.getClass();
                                                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$12, Alignment.Companion.Start, composerImpl3, 0);
                                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierSysuiResTag);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function02 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl3.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl3.startReusableNode();
                                                if (composerImpl3.inserting) {
                                                    composerImpl3.createNode(function02);
                                                } else {
                                                    composerImpl3.useNode();
                                                }
                                                Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                Updater.m337setimpl(composerImpl3, columnMeasurePolicy2, function22);
                                                Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
                                                Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function24);
                                                }
                                                Function2 function25 = ComposeUiNode.Companion.SetModifier;
                                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, function25);
                                                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentCompose.viewModel;
                                                if (qSFragmentComposeViewModel4 == null) {
                                                    qSFragmentComposeViewModel4 = null;
                                                }
                                                final QuickSettingsContainerViewModel quickSettingsContainerViewModel = qSFragmentComposeViewModel4.containerViewModel;
                                                composerImpl3.startReplaceGroup(-1420710693);
                                                final int i3 = iIntValue;
                                                boolean zChanged = composerImpl3.changed(i3);
                                                final float f = fDimensionResource;
                                                boolean zChanged2 = zChanged | composerImpl3.changed(f);
                                                Object objRememberedValue5 = composerImpl3.rememberedValue();
                                                if (!zChanged2) {
                                                    companion4.getClass();
                                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                                        objRememberedValue5 = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$$ExternalSyntheticLambda4
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj4) {
                                                                return Integer.valueOf(((Density) obj4).mo52roundToPx0680j_4(f) + i3);
                                                            }
                                                        };
                                                        composerImpl3.updateRememberedValue(objRememberedValue5);
                                                    }
                                                    composerImpl3.end(false);
                                                    SpacerKt.Spacer(composerImpl3, com.android.compose.modifiers.SizeKt.height(companion5, (Function1) objRememberedValue5));
                                                    final ContentScope contentScope2 = contentScope;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-349346012, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$5$BrightnessSlider$1
                                                        /* JADX WARN: Multi-variable type inference failed */
                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0050  */
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
                                                        /* JADX WARN: Type inference failed for: r8v21, types: [androidx.compose.ui.Modifier] */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5) {
                                                            Composer composer3 = (Composer) obj4;
                                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:747)");
                                                                    }
                                                                    QuickSettingsShade quickSettingsShade = QuickSettingsShade.INSTANCE;
                                                                    Modifier.Companion companion6 = Modifier.Companion;
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                    composerImpl5.startReplaceGroup(811897433);
                                                                    final ContentScope contentScope3 = contentScope2;
                                                                    boolean zChanged3 = composerImpl5.changed(contentScope3);
                                                                    final QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                                                    boolean zChangedInstance5 = zChanged3 | composerImpl5.changedInstance(qSFragmentCompose2);
                                                                    Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                    if (!zChangedInstance5) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                            objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$5$BrightnessSlider$1$$ExternalSyntheticLambda0
                                                                                /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
                                                                                @Override // kotlin.jvm.functions.Function0
                                                                                /*
                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                */
                                                                                public final Object invoke() {
                                                                                    boolean z;
                                                                                    if (((MutableSceneTransitionLayoutStateImpl) contentScope3.getLayoutState()).getTransitionState() instanceof TransitionState.Idle) {
                                                                                        QSFragmentComposeViewModel qSFragmentComposeViewModel5 = qSFragmentCompose2.viewModel;
                                                                                        if (qSFragmentComposeViewModel5 == null) {
                                                                                            qSFragmentComposeViewModel5 = null;
                                                                                        }
                                                                                        if (((Boolean) qSFragmentComposeViewModel5.isNotTransitioning$delegate.getValue()).booleanValue()) {
                                                                                            z = true;
                                                                                        }
                                                                                    } else {
                                                                                        z = false;
                                                                                    }
                                                                                    return Boolean.valueOf(z);
                                                                                }
                                                                            };
                                                                            composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                        }
                                                                        Function0 function03 = (Function0) objRememberedValue6;
                                                                        composerImpl5.end(false);
                                                                        quickSettingsShade.getClass();
                                                                        composerImpl5.startReplaceGroup(-139706516);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("com.android.systemui.qs.ui.composable.QuickSettingsShade.systemGestureExclusionInShade (QuickSettingsShadeOverlay.kt:305)");
                                                                        }
                                                                        final Density density = (Density) composerImpl5.consume(CompositionLocalsKt.LocalDensity);
                                                                        if (((Boolean) function03.invoke()).booleanValue()) {
                                                                            ?? SystemGestureExclusion = SystemGestureExclusionKt.systemGestureExclusion(Modifier.Companion, new Function1() { // from class: com.android.systemui.qs.ui.composable.QuickSettingsShade$systemGestureExclusionInShade$1$1
                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj6) {
                                                                                    QuickSettingsShade.Dimensions.INSTANCE.getClass();
                                                                                    float fMo58toPx0680j_4 = density.mo58toPx0680j_4(QuickSettingsShade.Dimensions.Padding);
                                                                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(-fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                                                                                    Offset.Companion companion7 = Offset.Companion;
                                                                                    float fMo612getSizeYbymL2g = (2 * fMo58toPx0680j_4) + ((int) (r8.mo612getSizeYbymL2g() >> 32));
                                                                                    float fMo612getSizeYbymL2g2 = (int) (((LayoutCoordinates) obj6).mo612getSizeYbymL2g() & 4294967295L);
                                                                                    long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo612getSizeYbymL2g2) & 4294967295L) | (Float.floatToRawIntBits(fMo612getSizeYbymL2g) << 32);
                                                                                    Size.Companion companion8 = Size.Companion;
                                                                                    return RectKt.m413Recttz77jQw(jFloatToRawIntBits, jFloatToRawIntBits2);
                                                                                }
                                                                            });
                                                                            companion6.getClass();
                                                                            companion6 = SystemGestureExclusion;
                                                                        }
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                        composerImpl5.end(false);
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl5.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl5, companion6);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl5.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl5.startReusableNode();
                                                                        if (composerImpl5.inserting) {
                                                                            composerImpl5.createNode(function04);
                                                                        } else {
                                                                            composerImpl5.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composerImpl5, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl5, currentCompositeKeyHash3, function26);
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        final QuickSettingsContainerViewModel quickSettingsContainerViewModel2 = quickSettingsContainerViewModel;
                                                                        QSFragmentComposeKt.AlwaysDarkMode(ComposableLambdaKt.rememberComposableLambda(-1558659383, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$5$BrightnessSlider$1$2$1
                                                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                                            @Override // kotlin.jvm.functions.Function2
                                                                            /*
                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                            */
                                                                            public final Object invoke(Object obj6, Object obj7) {
                                                                                Composer composer4 = (Composer) obj6;
                                                                                if ((((Number) obj7).intValue() & 3) == 2) {
                                                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer4;
                                                                                    if (composerImpl6.getSkipping()) {
                                                                                        composerImpl6.skipToGroupEnd();
                                                                                    } else {
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:764)");
                                                                                        }
                                                                                        BrightnessSliderViewModel brightnessSliderViewModel = quickSettingsContainerViewModel2.brightnessSliderViewModel;
                                                                                        Color.Companion.getClass();
                                                                                        long j = Color.Transparent;
                                                                                        ContainerColors.Companion.getClass();
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventStart("com.android.systemui.brightness.ui.compose.ContainerColors.Companion.<get-defaultContainerColor> (BrightnessSlider.kt:417)");
                                                                                        }
                                                                                        long jColorResource = ColorResources_androidKt.colorResource(R.color.shade_panel_fallback, composer4);
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventEnd();
                                                                                        }
                                                                                        BrightnessSliderKt.BrightnessSliderContainer(brightnessSliderViewModel, SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), new ContainerColors(j, jColorResource, null), composer4, 48);
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventEnd();
                                                                                        }
                                                                                    }
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        }, composerImpl5), composerImpl5, 6);
                                                                        composerImpl5.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl3);
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(168355110, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$5$TileGrid$1
                                                        /* JADX WARN: Removed duplicated region for block: B:26:0x00b2  */
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5) {
                                                            Composer composer3 = (Composer) obj4;
                                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:779)");
                                                                    }
                                                                    Modifier.Companion companion6 = Modifier.Companion;
                                                                    Alignment.Companion.getClass();
                                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl5.currentCompositionLocalScope();
                                                                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer3, companion6);
                                                                    ComposeUiNode.Companion.getClass();
                                                                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                                    if (composerImpl5.applier == null) {
                                                                        ComposablesKt.invalidApplier();
                                                                        throw null;
                                                                    }
                                                                    composerImpl5.startReusableNode();
                                                                    if (composerImpl5.inserting) {
                                                                        composerImpl5.createNode(function03);
                                                                    } else {
                                                                        composerImpl5.useNode();
                                                                    }
                                                                    Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                    Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                    Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                    if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl5, currentCompositeKeyHash3, function26);
                                                                    }
                                                                    Updater.m337setimpl(composer3, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                                                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                    ContentScope contentScope3 = contentScope2;
                                                                    GridAnchorKt.GridAnchor(contentScope3, null, composer3, 0);
                                                                    TileGridViewModel tileGridViewModel = quickSettingsContainerViewModel.tileGridViewModel;
                                                                    Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion6, 1.0f);
                                                                    composerImpl5.startReplaceGroup(-290563341);
                                                                    boolean zChangedInstance5 = composerImpl5.changedInstance(qSFragmentCompose);
                                                                    Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                    if (!zChangedInstance5) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                            objRememberedValue6 = new DrawInContainerNode$$ExternalSyntheticLambda1();
                                                                            composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                        }
                                                                        composerImpl5.end(false);
                                                                        TileGridKt.TileGrid(contentScope3, tileGridViewModel, modifierFillMaxWidth, (Function0) objRememberedValue6, composer3, 384, 0);
                                                                        composerImpl5.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl3);
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = ComposableLambdaKt.rememberComposableLambda(1520433860, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1$5$Media$1
                                                        /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5) {
                                                            Composer composer3 = (Composer) obj4;
                                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:802)");
                                                                    }
                                                                    QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                                                    QSFragmentComposeViewModel qSFragmentComposeViewModel5 = qSFragmentCompose2.viewModel;
                                                                    if (qSFragmentComposeViewModel5 == null) {
                                                                        qSFragmentComposeViewModel5 = null;
                                                                    }
                                                                    if (((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel5.qsMediaVisible$delegate).getValue()).booleanValue()) {
                                                                        QSFragmentComposeViewModel qSFragmentComposeViewModel6 = qSFragmentCompose2.viewModel;
                                                                        MediaHost mediaHost = (qSFragmentComposeViewModel6 != null ? qSFragmentComposeViewModel6 : null).qsMediaHost;
                                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                        composerImpl5.startReplaceGroup(812010751);
                                                                        boolean zChangedInstance5 = composerImpl5.changedInstance(qSFragmentCompose2);
                                                                        Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                        if (!zChangedInstance5) {
                                                                            Composer.Companion.getClass();
                                                                            if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                                objRememberedValue6 = new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose2, 9);
                                                                                composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                            }
                                                                            composerImpl5.end(false);
                                                                            QSFragmentComposeKt.MediaObject(mediaHost, null, (Function1) objRememberedValue6, composerImpl5, 0, 2);
                                                                        }
                                                                    }
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl3);
                                                    Modifier modifierSysuiResTag2 = SysuiTestTagKt.sysuiResTag(SizeKt.fillMaxWidth(companion5, 1.0f), "quick_settings_panel");
                                                    QuickSettingsShade.Dimensions.INSTANCE.getClass();
                                                    Modifier modifierM129paddingqDBjuR0$default = androidx.compose.foundation.layout.PaddingKt.m129paddingqDBjuR0$default(modifierSysuiResTag2, QSFragmentComposeKt.access$qsHorizontalMargin(composerImpl3), QuickSettingsShade.Dimensions.Padding, QSFragmentComposeKt.access$qsHorizontalMargin(composerImpl3), 0.0f, 8);
                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl3.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl3, modifierM129paddingqDBjuR0$default);
                                                    composerImpl3.startReusableNode();
                                                    if (composerImpl3.inserting) {
                                                        composerImpl3.createNode(function02);
                                                    } else {
                                                        composerImpl3.useNode();
                                                    }
                                                    Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
                                                    Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope3, function23);
                                                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl3, currentCompositeKeyHash3, function24);
                                                    }
                                                    Updater.m337setimpl(composerImpl3, modifierMaterializeModifier3, function25);
                                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                    QSFragmentComposeViewModel qSFragmentComposeViewModel5 = qSFragmentCompose.viewModel;
                                                    QSFragmentComposeKt.QuickSettingsLayout(composableLambdaImplRememberComposableLambda, composableLambdaImplRememberComposableLambda2, composableLambdaImplRememberComposableLambda3, (qSFragmentComposeViewModel5 == null ? null : qSFragmentComposeViewModel5).qsMediaInRowViewModel.getShouldMediaShowInRow(), composerImpl3, 438);
                                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, ((i2 << 9) & 7168) | 384);
                QuickSettingsThemeKt.QuickSettingsTheme(ComposableLambdaKt.rememberComposableLambda(-2040864656, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous> (QSFragmentCompose.kt:829)");
                                }
                                ElementKeys.INSTANCE.getClass();
                                ElementKey elementKey = ElementKeys.FooterActions;
                                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(Modifier.Companion, "qs_footer_actions");
                                final QSFragmentCompose qSFragmentCompose = this;
                                contentScope.Element(elementKey, modifierSysuiResTag, ComposableLambdaKt.rememberComposableLambda(2039865224, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$2.1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        Composer composer3 = (Composer) obj4;
                                        if ((((Number) obj5).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                            if (composerImpl3.getSkipping()) {
                                                composerImpl3.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:833)");
                                                }
                                                QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentCompose2.viewModel;
                                                if (qSFragmentComposeViewModel4 == null) {
                                                    qSFragmentComposeViewModel4 = null;
                                                }
                                                FooterActionsKt.FooterActions(qSFragmentComposeViewModel4.footerActionsViewModel, qSFragmentCompose2, null, composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer2), composer2, 432);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 6);
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda16(this, contentScope, companion2, i, 1);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeCustomizer() {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.containerViewModel.editModeViewModel.stopEditing();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeDetail() {
        closeCustomizer();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        QSFragmentCompose$notificationScrimClippingParams$1 qSFragmentCompose$notificationScrimClippingParams$1 = this.notificationScrimClippingParams;
        qSFragmentCompose$notificationScrimClippingParams$1.getClass();
        printWriterAsIndenting.append("NotificationScrimClippingParams").println(":");
        printWriterAsIndenting.increaseIndent();
        try {
            Boolean bool = (Boolean) ((SnapshotMutableStateImpl) qSFragmentCompose$notificationScrimClippingParams$1.isEnabled$delegate).getValue();
            bool.booleanValue();
            DumpUtilsKt.println(printWriterAsIndenting, "isEnabled", bool);
            DumpUtilsKt.println(printWriterAsIndenting, "params", (NotificationScrimClipParams) ((SnapshotMutableStateImpl) qSFragmentCompose$notificationScrimClippingParams$1.params$delegate).getValue());
            printWriterAsIndenting.decreaseIndent();
            printWriterAsIndenting.append("QQS positioning").println(":");
            printWriterAsIndenting.increaseIndent();
            try {
                DumpUtilsKt.println(printWriterAsIndenting, "qqsHeight", getHeaderHeight() + "px");
                DumpUtilsKt.println(printWriterAsIndenting, "qqsTop", this.qqsPositionOnRoot.top + "px");
                DumpUtilsKt.println(printWriterAsIndenting, "qqsBottom", this.qqsPositionOnRoot.bottom + "px");
                DumpUtilsKt.println(printWriterAsIndenting, "qqsLeft", this.qqsPositionOnRoot.left + "px");
                DumpUtilsKt.println(printWriterAsIndenting, "qqsPositionOnRoot", this.qqsPositionOnRoot);
                Rect rect = new Rect();
                getHeaderBoundsOnScreen(rect);
                DumpUtilsKt.println(printWriterAsIndenting, "qqsPositionOnScreen", rect);
                printWriterAsIndenting.decreaseIndent();
                DumpUtilsKt.println(printWriterAsIndenting, "QQS visible", this.qqsVisible.getValue());
                DumpUtilsKt.println(printWriterAsIndenting, "Always composed", Boolean.FALSE);
                if (this.viewModel != null) {
                    printWriterAsIndenting.append("View Model").println(":");
                    printWriterAsIndenting.increaseIndent();
                    try {
                        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
                        if (qSFragmentComposeViewModel == null) {
                            qSFragmentComposeViewModel = null;
                        }
                        qSFragmentComposeViewModel.dump(printWriterAsIndenting, strArr);
                    } finally {
                    }
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getDesiredHeight() {
        View view = getView();
        if (view != null) {
            return view.getHeight();
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final View getHeader() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = QSComposeFragment.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.qs_ui_refactor_compose_fragment to be enabled.");
        return null;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeaderBottom() {
        return this.qqsPositionOnRoot.bottom;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void getHeaderBoundsOnScreen(Rect rect) {
        rect.set(this.qqsPositionOnRoot);
        View view = getView();
        if (view != null) {
            view.getBoundsOnScreen(this.composeViewPositionOnScreen);
        } else {
            this.composeViewPositionOnScreen.setEmpty();
        }
        Rect rect2 = this.composeViewPositionOnScreen;
        rect.offset(rect2.left, rect2.top);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeaderLeft() {
        return this.qqsPositionOnRoot.left;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeaderTop() {
        return this.qqsPositionOnRoot.top;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeightDiff() {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        return ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsBottomPadding$delegate).getValue()).intValue() + (((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qsScrollHeight$delegate).getValue()).intValue() - ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsHeight$delegate).getValue()).intValue());
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getQsMinExpansionHeight() {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        if (!qSFragmentComposeViewModel.isInSplitShade()) {
            QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
            return ((Number) ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel2 != null ? qSFragmentComposeViewModel2 : null).qqsHeight$delegate).getValue()).intValue();
        }
        View view = getView();
        if (view != null) {
            view.getLocationOnScreen(this.locationTemp);
        }
        float f = this.locationTemp[1];
        View view2 = getView();
        int translationY = (int) (f - (view2 != null ? view2.getTranslationY() : 0.0f));
        View view3 = getView();
        return translationY + (view3 != null ? view3.getHeight() : 0);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isCustomizing() {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        return ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.isEditing$delegate).getValue()).booleanValue();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isFullyCollapsed() {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        return ((Boolean) qSFragmentComposeViewModel.isQsFullyCollapsed$delegate.getValue()).booleanValue();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isHeaderShown() {
        return ((Boolean) this.qqsVisible.getValue()).booleanValue();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isShowingDetail() {
        return isCustomizing();
    }

    @Override // com.android.systemui.util.LifecycleFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = QSComposeFragment.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.qs_ui_refactor_compose_fragment to be enabled.");
        this.viewModel = this.qsFragmentComposeViewModelFactory.create(LifecycleKt.getCoroutineScope(getLifecycle()));
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new QSFragmentCompose$setListenerCollections$1(this, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new AnonymousClass1(null), 3);
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = layoutInflater.getContext();
        context.getClass();
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setId(R.id.quick_settings_container);
        RepeatWhenAttachedKt.repeatWhenAttached(composeView, EmptyCoroutineContext.INSTANCE, new QSFragmentCompose$onCreateView$composeView$1$1(composeView, this, null));
        QSFragmentCompose$$ExternalSyntheticLambda0 qSFragmentCompose$$ExternalSyntheticLambda0 = new QSFragmentCompose$$ExternalSyntheticLambda0(this, 0);
        SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new QSFragmentCompose$$ExternalSyntheticLambda0(this, 1));
        QSFragmentCompose$$ExternalSyntheticLambda0 qSFragmentCompose$$ExternalSyntheticLambda02 = new QSFragmentCompose$$ExternalSyntheticLambda0(this, 2);
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        FrameLayoutTouchPassthrough frameLayoutTouchPassthrough = new FrameLayoutTouchPassthrough(context, qSFragmentCompose$$ExternalSyntheticLambda0, safeFlowSnapshotFlow, qSFragmentCompose$$ExternalSyntheticLambda02, new QSFragmentCompose$onCreateView$frame$4(qSFragmentComposeViewModel != null ? qSFragmentComposeViewModel : null));
        frameLayoutTouchPassthrough.addView(composeView, -1, -1);
        return frameLayoutTouchPassthrough;
    }

    @Override // com.android.systemui.util.LifecycleFragment, android.app.Fragment
    public final void onStart() {
        super.onStart();
        QSFragmentComposeKt$instanceProvider$1 qSFragmentComposeKt$instanceProvider$1 = QSFragmentComposeKt.instanceProvider;
        int i = qSFragmentComposeKt$instanceProvider$1.currentId;
        qSFragmentComposeKt$instanceProvider$1.currentId = i + 1;
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new QSFragmentCompose$registerDumpable$1(this, i + "-QSFragmentCompose", null), 3);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapseExpandAction(Runnable runnable) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.collapseExpandAccessibilityAction = runnable;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapsedMediaVisibilityChangedListener(Consumer consumer) {
        this.collapsedMediaVisibilityChangedListener.setValue(consumer);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setContainerController(QSContainerController qSContainerController) {
        this.qsContainerController.setValue(qSContainerController);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setExpanded(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.isQsExpanded$delegate.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        int i6;
        int i7;
        int i8;
        int i9;
        ((SnapshotMutableStateImpl) this.notificationScrimClippingParams.isEnabled$delegate).setValue(Boolean.valueOf(z));
        QSFragmentCompose$notificationScrimClippingParams$1 qSFragmentCompose$notificationScrimClippingParams$1 = this.notificationScrimClippingParams;
        int i10 = i;
        if (z2) {
            i10 = 0;
        }
        if (z2) {
            i7 = i4;
            i8 = i10;
            i9 = i5;
            i6 = 0;
        } else {
            i6 = i3;
            i7 = i4;
            i8 = i10;
            i9 = i5;
        }
        ((SnapshotMutableStateImpl) qSFragmentCompose$notificationScrimClippingParams$1.params$delegate).setValue(new NotificationScrimClipParams(i2, i7, i8, i6, i9));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeightOverride(int i) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.heightOverride$delegate).setValue(Integer.valueOf(i));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setInSplitShade(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.isInSplitShade$delegate.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setIsNotificationPanelFullWidth(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.isSmallScreen$delegate.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverScrollAmount(int i) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.overScrollAmount$delegate.setValue(Integer.valueOf(i));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverscrolling(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.isStackScrollerOverscrolling$delegate.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setPanelView(QS.HeightListener heightListener) {
        this.heightListener.setValue(heightListener);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsExpansion(float f, float f2, float f3, float f4) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        if (f < 0.0f) {
            ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qsExpansion$delegate).setValue(Float.valueOf(-1.0f));
        } else {
            qSFragmentComposeViewModel.getClass();
            ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qsExpansion$delegate).setValue(Float.valueOf(RangesKt___RangesKt.coerceIn(f, 0.0f, 1.0f)));
        }
        QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
        if (qSFragmentComposeViewModel2 == null) {
            qSFragmentComposeViewModel2 = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel2.panelExpansionFraction$delegate).setValue(Float.valueOf(f2));
        QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.viewModel;
        if (qSFragmentComposeViewModel3 == null) {
            qSFragmentComposeViewModel3 = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel3.squishinessFraction$delegate).setValue(Float.valueOf(f4));
        QSFragmentComposeViewModel qSFragmentComposeViewModel4 = this.viewModel;
        ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel4 != null ? qSFragmentComposeViewModel4 : null).proposedTranslation$delegate).setValue(Float.valueOf(f3));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsVisible(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        qSFragmentComposeViewModel.isQsVisible$delegate.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setScrollListener(QS.ScrollListener scrollListener) {
        this.scrollListener.setValue(scrollListener);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setShouldUpdateSquishinessOnMedia(boolean z) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.shouldUpdateSquishinessOnMedia$delegate).setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        if (qSFragmentComposeViewModel == null) {
            qSFragmentComposeViewModel = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.isTransitioningToFullShade$delegate).setValue(Boolean.valueOf(z));
        QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
        if (qSFragmentComposeViewModel2 == null) {
            qSFragmentComposeViewModel2 = null;
        }
        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel2.lockscreenToShadeProgress$delegate).setValue(Float.valueOf(f));
        if (z) {
            QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.viewModel;
            ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null).squishinessFraction$delegate).setValue(Float.valueOf(f2));
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderClickable(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderListening(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setListening(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void animateHeaderSlidingOut() {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void hideImmediately() {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void notifyCustomizeChanged() {
    }
}
