package com.android.systemui.qs.composefragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.LifecycleKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionLayoutKt;
import com.android.compose.animation.scene.SceneTransitionLayoutStateKt;
import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.compose.modifiers.PaddingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyboard.shortcut.ui.composable.InteractionsConfig;
import com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.qs.footer.ui.compose.FooterActionsKt;
import com.android.systemui.qs.panels.ui.compose.EditModeKt;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.shared.ui.ElementKeys;
import com.android.systemui.qs.ui.composable.QuickSettingsThemeKt;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LifecycleFragment;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = new QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            Object obj2 = (QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1344086626);
            if (m == obj) {
                m = new LinkedHashMap();
                composerImpl2.updateRememberedValue(m);
            }
            Object obj3 = (Map) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1344092124);
            if (m2 == obj) {
                SceneKeys sceneKeys = SceneKeys.INSTANCE;
                QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
                if (qSFragmentComposeViewModel == null) {
                    qSFragmentComposeViewModel = null;
                }
                QSFragmentComposeViewModel.QSExpansionState qSExpansionState = (QSFragmentComposeViewModel.QSExpansionState) qSFragmentComposeViewModel.expansionState$delegate.getValue();
                sceneKeys.getClass();
                m2 = qSExpansionState.progress < 0.5f ? SceneKeys.QuickQuickSettings : SceneKeys.QuickSettings;
                composerImpl2.updateRememberedValue(m2);
            }
            SceneKey sceneKey = (SceneKey) m2;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1344096259);
            boolean changedInstance = composerImpl2.changedInstance(this);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
                rememberedValue2 = new QSFragmentCompose$$ExternalSyntheticLambda4(this, 0);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            SceneTransitions transitions = TransitionDslKt.transitions((Function1) rememberedValue2);
            composerImpl2.startReplaceGroup(1344110601);
            boolean changedInstance2 = composerImpl2.changedInstance(obj2) | composerImpl2.changedInstance(obj3);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changedInstance2 || rememberedValue3 == obj) {
                rememberedValue3 = new QSFragmentCompose$$ExternalSyntheticLambda5(0, obj2, obj3);
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            Function1 function1 = (Function1) rememberedValue3;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(1344122012);
            boolean changedInstance3 = composerImpl2.changedInstance(obj3);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (changedInstance3 || rememberedValue4 == obj) {
                rememberedValue4 = new QSFragmentCompose$$ExternalSyntheticLambda6(obj3, 0);
                composerImpl2.updateRememberedValue(rememberedValue4);
            }
            composerImpl2.end(false);
            MutableSceneTransitionLayoutStateImpl rememberMutableSceneTransitionLayoutState = SceneTransitionLayoutStateKt.rememberMutableSceneTransitionLayoutState(sceneKey, transitions, null, null, null, function1, (Function1) rememberedValue4, composerImpl2, (SceneTransitions.$stable << 3) | 6, 636);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(1344131348);
            boolean changed = composerImpl2.changed(rememberMutableSceneTransitionLayoutState) | composerImpl2.changedInstance(this);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (changed || rememberedValue5 == obj) {
                rememberedValue5 = new QSFragmentCompose$CollapsableQuickSettingsSTL$1$1(rememberMutableSceneTransitionLayoutState, this, null);
                composerImpl2.updateRememberedValue(rememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) rememberedValue5);
            Modifier fillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(1344142293);
            boolean changedInstance4 = composerImpl2.changedInstance(this);
            Object rememberedValue6 = composerImpl2.rememberedValue();
            if (changedInstance4 || rememberedValue6 == obj) {
                rememberedValue6 = new QSFragmentCompose$$ExternalSyntheticLambda4(this, 4);
                composerImpl2.updateRememberedValue(rememberedValue6);
            }
            composerImpl2.end(false);
            SceneTransitionLayoutKt.SceneTransitionLayout(rememberMutableSceneTransitionLayoutState, fillMaxSize, null, null, 0.0f, (Function1) rememberedValue6, composerImpl2, 48, 60);
            composerImpl = composerImpl2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda3(this, i, 1);
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
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
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
                    final QSFragmentCompose qSFragmentCompose = QSFragmentCompose.this;
                    SurfacesKt.ProvideShortcutHelperIndication(interactionsConfig, ComposableLambdaKt.rememberComposableLambda(201376207, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$Content$1.1
                        /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
                        
                            if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r8, java.lang.Object r9) {
                            /*
                                Method dump skipped, instructions count: 261
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$Content$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), composer2, 48);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda3(this, i, 0);
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
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            composerImpl.startReplaceGroup(1160342269);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new QSFragmentCompose$$ExternalSyntheticLambda9(0);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            EditModeKt.EditMode(editModeViewModel, PaddingKt.padding$default(fillMaxWidth, (Function1) rememberedValue, null, 2), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(companion, i) { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda10
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int i2 = QSFragmentCompose.$r8$clinit;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    QSFragmentCompose.this.EditModeElement(this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0087, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0134, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0161, code lost:
    
        if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0187, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01b2, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01d7, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x027b, code lost:
    
        if (r11 == null) goto L94;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void QuickQuickSettingsElement(final com.android.compose.animation.scene.ContentScope r19, androidx.compose.ui.Modifier r20, androidx.compose.runtime.Composer r21, int r22) {
        /*
            Method dump skipped, instructions count: 789
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose.QuickQuickSettingsElement(com.android.compose.animation.scene.ContentScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public final void QuickSettingsElement(final ContentScope contentScope, Modifier.Companion companion, Composer composer, int i) {
        Modifier modifier;
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
            final int intValue = ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsHeaderHeight$delegate).getValue()).intValue();
            final float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_panel_padding_top, composerImpl);
            String stringResource = StringResources_androidKt.stringResource(R.string.accessibility_quick_settings_collapse, composerImpl);
            QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.viewModel;
            if (qSFragmentComposeViewModel2 == null) {
                qSFragmentComposeViewModel2 = null;
            }
            Runnable runnable = qSFragmentComposeViewModel2.collapseExpandAccessibilityAction;
            if (runnable == null || (modifier = SemanticsModifierKt.semantics(companion3, false, new QSFragmentCompose$$ExternalSyntheticLambda5(2, stringResource, runnable))) == null) {
                modifier = companion3;
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(576080992);
            QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.viewModel;
            if (((Boolean) ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null).isQsEnabled$delegate).getValue()).booleanValue()) {
                ElementKeys.INSTANCE.getClass();
                contentScope.Element(ElementKeys.QuickSettingsContent, columnScopeInstance.weight(companion3, 1.0f, true), ComposableLambdaKt.rememberComposableLambda(1300083842, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1
                    /* JADX WARN: Code restructure failed: missing block: B:15:0x0050, code lost:
                    
                        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:19:0x0081, code lost:
                    
                        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:23:0x00aa, code lost:
                    
                        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:27:0x00d3, code lost:
                    
                        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:43:0x017d, code lost:
                    
                        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L49;
                     */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r20, java.lang.Object r21, java.lang.Object r22) {
                        /*
                            Method dump skipped, instructions count: 580
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }, composerImpl), composerImpl, ((i2 << 9) & 7168) | 384);
                QuickSettingsThemeKt.QuickSettingsTheme(ComposableLambdaKt.rememberComposableLambda(-2040864656, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous> (QSFragmentCompose.kt:829)");
                        }
                        ElementKeys.INSTANCE.getClass();
                        ElementKey elementKey = ElementKeys.FooterActions;
                        Modifier sysuiResTag = SysuiTestTagKt.sysuiResTag(Modifier.Companion, "qs_footer_actions");
                        final QSFragmentCompose qSFragmentCompose = this;
                        ContentScope.this.Element(elementKey, sysuiResTag, ComposableLambdaKt.rememberComposableLambda(2039865224, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$QuickSettingsElement$1$2.1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                Composer composer3 = (Composer) obj4;
                                if ((((Number) obj5).intValue() & 17) == 16) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.QuickSettingsElement.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:833)");
                                }
                                QSFragmentCompose qSFragmentCompose2 = QSFragmentCompose.this;
                                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentCompose2.viewModel;
                                if (qSFragmentComposeViewModel4 == null) {
                                    qSFragmentComposeViewModel4 = null;
                                }
                                FooterActionsKt.FooterActions(qSFragmentComposeViewModel4.footerActionsViewModel, qSFragmentCompose2, null, composer3, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer2), composer2, 432);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new QSFragmentCompose$$ExternalSyntheticLambda16(this, contentScope, companion2, i, 1);
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
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        QSFragmentCompose$notificationScrimClippingParams$1 qSFragmentCompose$notificationScrimClippingParams$1 = this.notificationScrimClippingParams;
        qSFragmentCompose$notificationScrimClippingParams$1.getClass();
        asIndenting.append("NotificationScrimClippingParams").println(":");
        asIndenting.increaseIndent();
        try {
            Boolean bool = (Boolean) ((SnapshotMutableStateImpl) qSFragmentCompose$notificationScrimClippingParams$1.isEnabled$delegate).getValue();
            bool.booleanValue();
            DumpUtilsKt.println(asIndenting, "isEnabled", bool);
            DumpUtilsKt.println(asIndenting, "params", (NotificationScrimClipParams) ((SnapshotMutableStateImpl) qSFragmentCompose$notificationScrimClippingParams$1.params$delegate).getValue());
            asIndenting.decreaseIndent();
            asIndenting.append("QQS positioning").println(":");
            asIndenting.increaseIndent();
            try {
                DumpUtilsKt.println(asIndenting, "qqsHeight", getHeaderHeight() + "px");
                DumpUtilsKt.println(asIndenting, "qqsTop", this.qqsPositionOnRoot.top + "px");
                DumpUtilsKt.println(asIndenting, "qqsBottom", this.qqsPositionOnRoot.bottom + "px");
                DumpUtilsKt.println(asIndenting, "qqsLeft", this.qqsPositionOnRoot.left + "px");
                DumpUtilsKt.println(asIndenting, "qqsPositionOnRoot", this.qqsPositionOnRoot);
                Rect rect = new Rect();
                getHeaderBoundsOnScreen(rect);
                DumpUtilsKt.println(asIndenting, "qqsPositionOnScreen", rect);
                asIndenting.decreaseIndent();
                DumpUtilsKt.println(asIndenting, "QQS visible", this.qqsVisible.getValue());
                DumpUtilsKt.println(asIndenting, "Always composed", Boolean.FALSE);
                if (this.viewModel != null) {
                    asIndenting.append("View Model").println(":");
                    asIndenting.increaseIndent();
                    try {
                        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
                        if (qSFragmentComposeViewModel == null) {
                            qSFragmentComposeViewModel = null;
                        }
                        qSFragmentComposeViewModel.dump(asIndenting, strArr);
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
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new QSFragmentCompose$onCreate$1(this, null), 3);
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = layoutInflater.getContext();
        context.getClass();
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setId(R.id.quick_settings_container);
        RepeatWhenAttachedKt.repeatWhenAttached(composeView, EmptyCoroutineContext.INSTANCE, new QSFragmentCompose$onCreateView$composeView$1$1(composeView, this, null));
        QSFragmentCompose$$ExternalSyntheticLambda0 qSFragmentCompose$$ExternalSyntheticLambda0 = new QSFragmentCompose$$ExternalSyntheticLambda0(this, 0);
        SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new QSFragmentCompose$$ExternalSyntheticLambda0(this, 1));
        QSFragmentCompose$$ExternalSyntheticLambda0 qSFragmentCompose$$ExternalSyntheticLambda02 = new QSFragmentCompose$$ExternalSyntheticLambda0(this, 2);
        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.viewModel;
        FrameLayoutTouchPassthrough frameLayoutTouchPassthrough = new FrameLayoutTouchPassthrough(context, qSFragmentCompose$$ExternalSyntheticLambda0, snapshotFlow, qSFragmentCompose$$ExternalSyntheticLambda02, new QSFragmentCompose$onCreateView$frame$4(qSFragmentComposeViewModel != null ? qSFragmentComposeViewModel : null));
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
