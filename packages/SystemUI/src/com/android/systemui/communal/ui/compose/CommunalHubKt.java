package com.android.systemui.communal.ui.compose;

import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.util.SizeF;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.outlined.WidgetsKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.R;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.compose.Dimensions;
import com.android.systemui.communal.ui.compose.extensions.ModifierExtKt$allowGestures$1;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import com.android.systemui.communal.util.DensityUtils;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CommunalHubKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CommunalContentSize.FixedSize.values().length];
            try {
                iArr[CommunalContentSize.FixedSize.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CommunalContentSize.FixedSize.HALF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CommunalContentSize.FixedSize.THIRD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void AccessibilityContainer(final BaseCommunalViewModel baseCommunalViewModel, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1963144233);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(baseCommunalViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.AccessibilityContainer (CommunalHub.kt:1820)");
            }
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isFocusable(), Boolean.FALSE, composerImpl, 48);
            Modifier.Companion companion = Modifier.Companion;
            Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion, 1.0f), 3);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue() && !baseCommunalViewModel.isEditMode()) {
                wrapContentHeight$default = wrapContentHeight$default.then(SemanticsModifierKt.semantics(FocusableKt.focusable$default(companion, ((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue(), null, 2), false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, context.getString(R.string.accessibility_content_description_for_communal_hub));
                        String string = context.getString(R.string.accessibility_action_label_close_communal_hub);
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Arrays.asList(new CustomAccessibilityAction(string, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                BaseCommunalViewModel.changeScene$default(BaseCommunalViewModel.this, CommunalScenes.Blank, "closed by accessibility", null, 12);
                                return Boolean.TRUE;
                            }
                        }), new CustomAccessibilityAction(context.getString(R.string.accessibility_action_label_edit_widgets), new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                BaseCommunalViewModel baseCommunalViewModel3 = BaseCommunalViewModel.this;
                                baseCommunalViewModel3.setSelectedKey(null);
                                baseCommunalViewModel3.onOpenWidgetEditor(false);
                                return Boolean.TRUE;
                            }
                        })));
                        return Unit.INSTANCE;
                    }
                }));
            }
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, wrapContentHeight$default);
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
            composableLambdaImpl.invoke(composerImpl, Integer.valueOf((i2 >> 3) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda3(baseCommunalViewModel, composableLambdaImpl, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0273  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CommunalContent(final com.android.systemui.communal.domain.model.CommunalContentModel r20, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r21, final android.util.SizeF r22, final boolean r23, androidx.compose.ui.Modifier r24, com.android.systemui.communal.widgets.WidgetConfigurator r25, final int r26, final com.android.systemui.communal.ui.compose.ContentListState r27, final android.widget.RemoteViews.InteractionHandler r28, final com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection r29, final com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel r30, com.android.compose.animation.scene.ContentScope r31, androidx.compose.runtime.Composer r32, final int r33, final int r34) {
        /*
            Method dump skipped, instructions count: 682
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.CommunalContent(com.android.systemui.communal.domain.model.CommunalContentModel, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, android.util.SizeF, boolean, androidx.compose.ui.Modifier, com.android.systemui.communal.widgets.WidgetConfigurator, int, com.android.systemui.communal.ui.compose.ContentListState, android.widget.RemoteViews$InteractionHandler, com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection, com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel, com.android.compose.animation.scene.ContentScope, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x0686, code lost:
    
        if (r14 == r11) goto L204;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CommunalHub(androidx.compose.ui.Modifier r38, final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r39, final com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection r40, android.widget.RemoteViews.InteractionHandler r41, com.android.systemui.statusbar.phone.SystemUIDialogFactory r42, com.android.systemui.communal.widgets.WidgetConfigurationController r43, kotlin.jvm.functions.Function0 r44, kotlin.jvm.functions.Function0 r45, com.android.compose.animation.scene.ContentScope r46, androidx.compose.runtime.Composer r47, final int r48, final int r49) {
        /*
            Method dump skipped, instructions count: 2041
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.CommunalHub(androidx.compose.ui.Modifier, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection, android.widget.RemoteViews$InteractionHandler, com.android.systemui.statusbar.phone.SystemUIDialogFactory, com.android.systemui.communal.widgets.WidgetConfigurationController, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, com.android.compose.animation.scene.ContentScope, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:125:0x040f  */
    /* JADX WARN: Type inference failed for: r7v15, types: [T, com.android.systemui.communal.ui.compose.GridDragDropState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v6, types: [T, androidx.compose.runtime.snapshots.SnapshotStateList] */
    /* renamed from: CommunalHubLazyGrid-MGE6UKE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1077CommunalHubLazyGridMGE6UKE(final androidx.compose.foundation.layout.BoxScopeInstance r35, final java.util.List r36, final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r37, final androidx.compose.foundation.layout.PaddingValues r38, final androidx.compose.runtime.State r39, final int r40, final long r41, final androidx.compose.foundation.lazy.grid.LazyGridState r43, final com.android.systemui.communal.ui.compose.ContentListState r44, final kotlin.jvm.functions.Function1 r45, final kotlin.jvm.functions.Function1 r46, final kotlin.jvm.functions.Function1 r47, final com.android.systemui.communal.widgets.WidgetConfigurator r48, final android.widget.RemoteViews.InteractionHandler r49, final com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection r50, final com.android.compose.animation.scene.ContentScope r51, androidx.compose.runtime.Composer r52, final int r53) {
        /*
            Method dump skipped, instructions count: 1095
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.m1077CommunalHubLazyGridMGE6UKE(androidx.compose.foundation.layout.BoxScopeInstance, java.util.List, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.State, int, long, androidx.compose.foundation.lazy.grid.LazyGridState, com.android.systemui.communal.ui.compose.ContentListState, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, com.android.systemui.communal.widgets.WidgetConfigurator, android.widget.RemoteViews$InteractionHandler, com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection, com.android.compose.animation.scene.ContentScope, androidx.compose.runtime.Composer, int):void");
    }

    public static final void CtaTileInViewModeContent(final BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, Composer composer, int i) {
        int i2;
        Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1975660102);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(baseCommunalViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CtaTileInViewModeContent (CommunalHub.kt:1355)");
            }
            MaterialTheme.INSTANCE.getClass();
            final ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long j = colorScheme.primary;
            cardDefaults.getClass();
            CardColors m254cardColorsro_MJ88 = CardDefaults.m254cardColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
            DensityUtils.Companion.getClass();
            modifier2 = modifier;
            CardKt.Card(modifier2, RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(68), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(34), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(68), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(34)), m254cardColorsro_MJ88, null, null, ComposableLambdaKt.rememberComposableLambda(-2139731156, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier then;
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CtaTileInViewModeContent.<anonymous> (CommunalHub.kt:1366)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    Modifier fillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                    DensityUtils.Companion.getClass();
                    Modifier m125paddingVpY3zN4 = PaddingKt.m125paddingVpY3zN4(fillMaxSize, DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(50), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(32));
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                    Alignment.Companion.getClass();
                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, horizontal, composer2, 54);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m125paddingVpY3zN4);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl3.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m336setimpl(composer2, columnMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m336setimpl(composer2, materializeModifier, function24);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                    ImageVector widgets = WidgetsKt.getWidgets();
                    String stringResource = StringResources_androidKt.stringResource(R.string.cta_label_to_open_widget_picker, composer2);
                    Dimensions.Companion.getClass();
                    Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(companion, Dimensions.IconSize);
                    composerImpl3.startReplaceGroup(-1822745531);
                    Object rememberedValue = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = new CommunalHubKt$$ExternalSyntheticLambda34(2);
                        composerImpl3.updateRememberedValue(rememberedValue);
                    }
                    composerImpl3.end(false);
                    IconKt.m270Iconww6aTOc(widgets, stringResource, SemanticsModifierKt.clearAndSetSemantics(m139size3ABfNKs, (Function1) rememberedValue), 0L, composer2, 0, 8);
                    SpacerKt.Spacer(composer2, SizeKt.m139size3ABfNKs(companion, DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(6)));
                    String stringResource2 = StringResources_androidKt.stringResource(R.string.cta_label_to_edit_widget, composer2);
                    MaterialTheme.INSTANCE.getClass();
                    TextStyle textStyle = MaterialTheme.getTypography(composer2).titleLarge;
                    Dp.Companion companion2 = Dp.Companion;
                    long m1080access$nonScalableTextSize8Feqmps = CommunalHubKt.m1080access$nonScalableTextSize8Feqmps(22, composer2);
                    long m1080access$nonScalableTextSize8Feqmps2 = CommunalHubKt.m1080access$nonScalableTextSize8Feqmps(28, composer2);
                    then = ScrollingContainerKt.scrollingContainer(companion, r6, Orientation.Vertical, (r14 & 2) != 0, false, null, r6.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(ScrollKt.rememberScrollState(composer2), false, true));
                    TextKt.m316Text4IGK_g(stringResource2, columnScopeInstance.weight(then, 1.0f, true), 0L, m1080access$nonScalableTextSize8Feqmps, null, null, null, 0L, null, null, m1080access$nonScalableTextSize8Feqmps2, 0, false, 0, 0, null, textStyle, composer2, 0, 0, 64500);
                    SpacerKt.Spacer(composer2, SizeKt.m139size3ABfNKs(companion, DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(16)));
                    Modifier m130height3ABfNKs = SizeKt.m130height3ABfNKs(SizeKt.fillMaxWidth(companion, 1.0f), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(56));
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m92spacedByD5KLDUw(DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(16), horizontal), Alignment.Companion.Top, composer2, 0);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, m130height3ABfNKs);
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composer2, rowMeasurePolicy, function2);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope2, function22);
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                    }
                    Updater.m336setimpl(composer2, materializeModifier2, function24);
                    final RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                    ProvidedValue defaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getDensity(), RangesKt___RangesKt.coerceIn(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getFontScale(), 0.0f, 1.25f)));
                    final ColorScheme colorScheme2 = ColorScheme.this;
                    final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                    CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(1286552390, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1$1$2$1
                        /* JADX WARN: Code restructure failed: missing block: B:15:0x0074, code lost:
                        
                            if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:19:0x00d1, code lost:
                        
                            if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r32, java.lang.Object r33) {
                            /*
                                Method dump skipped, instructions count: 267
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1$1$2$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), composer2, 56);
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 14) | 196608, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda24(baseCommunalViewModel, modifier2, i, 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c5, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DisabledWidgetPlaceholder(com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent.DisabledWidget r20, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r21, androidx.compose.ui.Modifier r22, androidx.compose.runtime.Composer r23, int r24) {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.DisabledWidgetPlaceholder(com.android.systemui.communal.domain.model.CommunalContentModel$WidgetContent$DisabledWidget, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x01ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DisclaimerBottomSheetContent(kotlin.jvm.functions.Function0 r34, androidx.compose.runtime.Composer r35, int r36) {
        /*
            Method dump skipped, instructions count: 482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.DisclaimerBottomSheetContent(kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }

    public static final void EmptyStateCta(PaddingValues paddingValues, final BaseCommunalViewModel baseCommunalViewModel, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1754062866);
        if ((((composerImpl.changed(paddingValues) ? 4 : 2) | i | (composerImpl.changedInstance(baseCommunalViewModel) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.EmptyStateCta (CommunalHub.kt:1057)");
            }
            MaterialTheme.INSTANCE.getClass();
            final ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Dimensions hubDimensions = getHubDimensions(composerImpl);
            Dimensions.Companion.getClass();
            DensityUtils.Companion companion2 = DensityUtils.Companion;
            companion2.getClass();
            float m1081getGridTopSpacingD9Ej5fM = hubDimensions.m1081getGridTopSpacingD9Ej5fM() + DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(530);
            Dp.Companion companion3 = Dp.Companion;
            Modifier padding = PaddingKt.padding(SizeKt.m130height3ABfNKs(companion, m1081getGridTopSpacingD9Ej5fM), paddingValues);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long j = colorScheme.primary;
            cardDefaults.getClass();
            CardColors m254cardColorsro_MJ88 = CardDefaults.m254cardColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
            companion2.getClass();
            CardKt.Card(padding, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(80)), m254cardColorsro_MJ88, null, null, ComposableLambdaKt.rememberComposableLambda(-635955488, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$1
                /* JADX WARN: Code restructure failed: missing block: B:38:0x01f8, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L45;
                 */
                /* JADX WARN: Removed duplicated region for block: B:28:0x0193  */
                /* JADX WARN: Removed duplicated region for block: B:37:0x01f3  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x022c  */
                /* JADX WARN: Removed duplicated region for block: B:45:0x019b  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r36, java.lang.Object r37, java.lang.Object r38) {
                    /*
                        Method dump skipped, instructions count: 566
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 196608, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda4(i, 1, paddingValues, baseCommunalViewModel);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0081, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void HighlightedItem(final androidx.compose.ui.Modifier r6, final float r7, androidx.compose.runtime.Composer r8, final int r9, final int r10) {
        /*
            androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
            r0 = -1602272507(0xffffffffa07f4305, float:-2.1621515E-19)
            r8.startRestartGroup(r0)
            r0 = r9 & 6
            if (r0 != 0) goto L17
            boolean r0 = r8.changed(r6)
            if (r0 == 0) goto L14
            r0 = 4
            goto L15
        L14:
            r0 = 2
        L15:
            r0 = r0 | r9
            goto L18
        L17:
            r0 = r9
        L18:
            r1 = r10 & 2
            r2 = 32
            if (r1 == 0) goto L21
            r0 = r0 | 48
            goto L30
        L21:
            r3 = r9 & 48
            if (r3 != 0) goto L30
            boolean r3 = r8.changed(r7)
            if (r3 == 0) goto L2d
            r3 = r2
            goto L2f
        L2d:
            r3 = 16
        L2f:
            r0 = r0 | r3
        L30:
            r3 = r0 & 19
            r4 = 18
            if (r3 != r4) goto L41
            boolean r3 = r8.getSkipping()
            if (r3 != 0) goto L3d
            goto L41
        L3d:
            r8.skipToGroupEnd()
            goto La0
        L41:
            if (r1 == 0) goto L45
            r7 = 1065353216(0x3f800000, float:1.0)
        L45:
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L50
            java.lang.String r1 = "com.android.systemui.communal.ui.compose.HighlightedItem (CommunalHub.kt:1328)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L50:
            androidx.compose.ui.graphics.SolidColor r1 = new androidx.compose.ui.graphics.SolidColor
            androidx.compose.material3.MaterialTheme r3 = androidx.compose.material3.MaterialTheme.INSTANCE
            r3.getClass()
            androidx.compose.material3.ColorScheme r3 = androidx.compose.material3.MaterialTheme.getColorScheme(r8)
            long r3 = r3.primary
            r5 = 0
            r1.<init>(r3, r5)
            r3 = -1959915338(0xffffffff8b2e10b6, float:-3.3523753E-32)
            r8.startReplaceGroup(r3)
            boolean r3 = r8.changed(r1)
            r0 = r0 & 112(0x70, float:1.57E-43)
            r4 = 0
            if (r0 != r2) goto L72
            r0 = 1
            goto L73
        L72:
            r0 = r4
        L73:
            r0 = r0 | r3
            java.lang.Object r2 = r8.rememberedValue()
            if (r0 != 0) goto L83
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r0) goto L8b
        L83:
            com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda27 r2 = new com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda27
            r2.<init>()
            r8.updateRememberedValue(r2)
        L8b:
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            r8.end(r4)
            androidx.compose.ui.Modifier r0 = androidx.compose.ui.draw.DrawModifierKt.drawBehind(r6, r2)
            androidx.compose.foundation.layout.BoxKt.Box(r0, r8, r4)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto La0
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        La0:
            androidx.compose.runtime.RecomposeScopeImpl r8 = r8.endRestartGroup()
            if (r8 == 0) goto Lad
            com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda28 r0 = new com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda28
            r0.<init>()
            r8.block = r0
        Lad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.HighlightedItem(androidx.compose.ui.Modifier, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0161, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L85;
     */
    /* renamed from: HorizontalGridWrapper-tOXsyB8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1078HorizontalGridWrappertOXsyB8(final androidx.compose.foundation.layout.PaddingValues r20, final androidx.compose.foundation.lazy.grid.LazyGridState r21, final com.android.systemui.communal.ui.compose.GridDragDropState r22, final kotlin.jvm.functions.Function1 r23, final float r24, final float r25, final androidx.compose.ui.Modifier r26, final com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda14 r27, androidx.compose.runtime.Composer r28, final int r29) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.m1078HorizontalGridWrappertOXsyB8(androidx.compose.foundation.layout.PaddingValues, androidx.compose.foundation.lazy.grid.LazyGridState, com.android.systemui.communal.ui.compose.GridDragDropState, kotlin.jvm.functions.Function1, float, float, androidx.compose.ui.Modifier, com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda14, androidx.compose.runtime.Composer, int):void");
    }

    public static final void ObserveNewWidgetAddedEffect(List list, LazyGridState lazyGridState, BaseCommunalViewModel baseCommunalViewModel, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1770238181);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(list) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(lazyGridState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(baseCommunalViewModel) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ObserveNewWidgetAddedEffect (CommunalHub.kt:648)");
            }
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            composerImpl.startReplaceGroup(-782717482);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == obj) {
                rememberedValue2 = new ArrayList();
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            List list2 = (List) rememberedValue2;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -782715213);
            if (m == obj) {
                m = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl.updateRememberedValue(m);
            }
            MutableState mutableState = (MutableState) m;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-782711585);
            boolean changedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(list) | composerImpl.changedInstance(list2) | composerImpl.changedInstance(baseCommunalViewModel) | composerImpl.changedInstance(coroutineScope);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue3 == obj) {
                Object communalHubKt$ObserveNewWidgetAddedEffect$1$1 = new CommunalHubKt$ObserveNewWidgetAddedEffect$1$1(list, list2, baseCommunalViewModel, lazyGridState, coroutineScope, mutableState, null);
                composerImpl.updateRememberedValue(communalHubKt$ObserveNewWidgetAddedEffect$1$1);
                rememberedValue3 = communalHubKt$ObserveNewWidgetAddedEffect$1$1;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, list, (Function2) rememberedValue3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(list, lazyGridState, baseCommunalViewModel, i, 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ObserveScrollEffect(androidx.compose.foundation.lazy.grid.LazyGridState r4, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r5, androidx.compose.runtime.Composer r6, int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r0 = -1825347584(0xffffffff93336800, float:-2.2644252E-27)
            r6.startRestartGroup(r0)
            r0 = r7 & 6
            r1 = 4
            if (r0 != 0) goto L18
            boolean r0 = r6.changed(r4)
            if (r0 == 0) goto L15
            r0 = r1
            goto L16
        L15:
            r0 = 2
        L16:
            r0 = r0 | r7
            goto L19
        L18:
            r0 = r7
        L19:
            r2 = r7 & 48
            if (r2 != 0) goto L29
            boolean r2 = r6.changedInstance(r5)
            if (r2 == 0) goto L26
            r2 = 32
            goto L28
        L26:
            r2 = 16
        L28:
            r0 = r0 | r2
        L29:
            r2 = r0 & 19
            r3 = 18
            if (r2 != r3) goto L3a
            boolean r2 = r6.getSkipping()
            if (r2 != 0) goto L36
            goto L3a
        L36:
            r6.skipToGroupEnd()
            goto L81
        L3a:
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L45
            java.lang.String r2 = "com.android.systemui.communal.ui.compose.ObserveScrollEffect (CommunalHub.kt:590)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r2)
        L45:
            r2 = 517147173(0x1ed30a25, float:2.234469E-20)
            r6.startReplaceGroup(r2)
            r0 = r0 & 14
            r2 = 0
            if (r0 != r1) goto L52
            r0 = 1
            goto L53
        L52:
            r0 = r2
        L53:
            boolean r1 = r6.changedInstance(r5)
            r0 = r0 | r1
            java.lang.Object r1 = r6.rememberedValue()
            if (r0 != 0) goto L67
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L70
        L67:
            com.android.systemui.communal.ui.compose.CommunalHubKt$ObserveScrollEffect$1$1 r1 = new com.android.systemui.communal.ui.compose.CommunalHubKt$ObserveScrollEffect$1$1
            r0 = 0
            r1.<init>(r4, r5, r0)
            r6.updateRememberedValue(r1)
        L70:
            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
            r6.end(r2)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r6, r4, r1)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L81
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L81:
            androidx.compose.runtime.RecomposeScopeImpl r6 = r6.endRestartGroup()
            if (r6 == 0) goto L8f
            com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda3 r0 = new com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda3
            r1 = 0
            r0.<init>(r7, r1, r4, r5)
            r6.block = r0
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.ObserveScrollEffect(androidx.compose.foundation.lazy.grid.LazyGridState, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, androidx.compose.runtime.Composer, int):void");
    }

    public static final void PendingWidgetPlaceholder(CommunalContentModel.WidgetContent.PendingWidget pendingWidget, Modifier modifier, Composer composer, int i) {
        int i2;
        Icon createWithResource;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(127270360);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pendingWidget) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.PendingWidgetPlaceholder (CommunalHub.kt:1680)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Bitmap bitmap = pendingWidget.icon;
            if (bitmap != null) {
                createWithResource = Icon.createWithBitmap(bitmap);
                createWithResource.getClass();
            } else {
                createWithResource = Icon.createWithResource(context, android.R.drawable.sym_def_app_icon);
                createWithResource.getClass();
            }
            MaterialTheme.INSTANCE.getClass();
            Modifier m26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifier, MaterialTheme.getColorScheme(composerImpl).surfaceVariant, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(android.R.dimen.system_app_widget_background_radius, composerImpl)));
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m26backgroundbw27NRU);
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
            Painter rememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(createWithResource.loadDrawable(context), composerImpl);
            String stringResource = StringResources_androidKt.stringResource(R.string.icon_description_for_pending_widget, composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Dimensions.Companion.getClass();
            ImageKt.Image(rememberDrawablePainter, stringResource, SizeKt.m139size3ABfNKs(companion, Dimensions.IconSize), null, null, 0.0f, null, composerImpl, 384, 120);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda3(i, 1, pendingWidget, modifier);
        }
    }

    /* renamed from: ResizableItemFrameWrapper-iBr3E7A, reason: not valid java name */
    public static final void m1079ResizableItemFrameWrapperiBr3E7A(final String str, final long j, final LazyGridState lazyGridState, final PaddingValues paddingValues, final Arrangement.SpacedAligned spacedAligned, final boolean z, final int i, final int i2, final Modifier modifier, final Function0 function0, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, final Function1 function1, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i3) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1573358422);
        int i4 = i3 | (composerImpl.changed(str) ? 4 : 2) | (composerImpl.changed(j) ? 32 : 16) | (composerImpl.changed(lazyGridState) ? 256 : 128) | (composerImpl.changed(paddingValues) ? 2048 : 1024) | (composerImpl.changed(spacedAligned) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changed(z) ? 131072 : 65536) | (composerImpl.changed(i) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl.changed(i2) ? 8388608 : 4194304) | (composerImpl.changed(modifier) ? 67108864 : 33554432) | (composerImpl.changedInstance(function0) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456);
        int i5 = 384 | (composerImpl.changedInstance(resizeableItemFrameViewModel) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16);
        if ((i4 & 306783379) == 306783378 && (i5 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ResizableItemFrameWrapper (CommunalHub.kt:703)");
            }
            composerImpl.startReplaceGroup(352386777);
            int span = CommunalContentSize.FixedSize.HALF.getSpan();
            ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1877479336, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ResizableItemFrameWrapper$3
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
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ResizableItemFrameWrapper.<anonymous> (CommunalHub.kt:727)");
                    }
                    Function3.this.invoke(Modifier.Companion, composer2, 6);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            int i6 = (i4 & 65534) | ((i4 >> 9) & 458752) | ((i4 << 3) & 3670016);
            int i7 = i4 >> 15;
            int i8 = (i7 & 896) | (i7 & 112) | 12582912 | (i7 & 57344);
            int i9 = i5 << 15;
            ResizeableItemFrameKt.m1086ResizableItemFramegFm42b4(str, j, lazyGridState, paddingValues, spacedAligned, modifier, z, 0.0f, 0L, 0.0f, 0.0f, i, i2, span, function0, resizeableItemFrameViewModel, function1, rememberComposableLambda, composerImpl, i6, i8 | (i9 & 458752) | (i9 & 3670016));
            composerImpl = composerImpl;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, j, lazyGridState, paddingValues, spacedAligned, z, i, i2, modifier, function0, resizeableItemFrameViewModel, function1, composableLambdaImpl, i3) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda20
                public final /* synthetic */ String f$0;
                public final /* synthetic */ long f$1;
                public final /* synthetic */ ResizeableItemFrameViewModel f$10;
                public final /* synthetic */ Function1 f$11;
                public final /* synthetic */ ComposableLambdaImpl f$12;
                public final /* synthetic */ LazyGridState f$2;
                public final /* synthetic */ PaddingValues f$3;
                public final /* synthetic */ Arrangement.SpacedAligned f$4;
                public final /* synthetic */ boolean f$5;
                public final /* synthetic */ int f$6;
                public final /* synthetic */ int f$7;
                public final /* synthetic */ Modifier f$8;
                public final /* synthetic */ Function0 f$9;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Arrangement.SpacedAligned spacedAligned2 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$12;
                    CommunalHubKt.m1079ResizableItemFrameWrapperiBr3E7A(this.f$0, this.f$1, this.f$2, this.f$3, spacedAligned2, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10, this.f$11, composableLambdaImpl2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ScrollOnUpdatedLiveContentEffect(List list, LazyGridState lazyGridState, Composer composer, int i) {
        List list2;
        LazyGridState lazyGridState2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(483517442);
        int i2 = (composerImpl.changedInstance(list) ? 4 : 2) | i | (composerImpl.changed(lazyGridState) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            list2 = list;
            lazyGridState2 = lazyGridState;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ScrollOnUpdatedLiveContentEffect (CommunalHub.kt:608)");
            }
            composerImpl.startReplaceGroup(-1992884982);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = new ArrayList();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            List list3 = (List) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -1992882713);
            if (m == obj) {
                m = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl.updateRememberedValue(m);
            }
            MutableState mutableState = (MutableState) m;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1992879523);
            boolean changedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(list) | composerImpl.changedInstance(list3);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
                list2 = list;
                lazyGridState2 = lazyGridState;
                Object communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1 = new CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1(list2, list3, lazyGridState2, mutableState, null);
                composerImpl.updateRememberedValue(communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1);
                rememberedValue2 = communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1;
            } else {
                list2 = list;
                lazyGridState2 = lazyGridState;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, list2, (Function2) rememberedValue2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda4(i, 0, list2, lazyGridState2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0076, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a5, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SmartspaceContent(android.widget.RemoteViews.InteractionHandler r13, com.android.systemui.communal.domain.model.CommunalContentModel.Smartspace r14, androidx.compose.ui.Modifier r15, androidx.compose.runtime.Composer r16, int r17) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.SmartspaceContent(android.widget.RemoteViews$InteractionHandler, com.android.systemui.communal.domain.model.CommunalContentModel$Smartspace, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public static final void Toolbar(final boolean z, final Function0 function0, Function1 function1, final Function1 function12, final Function0 function02, final Function0 function03, Composer composer, final int i) {
        Function1 function13;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1489933479);
        int i2 = i | (composerImpl.changed(z) ? 4 : 2) | (composerImpl.changedInstance(function0) ? 32 : 16) | (composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changedInstance(function03) ? 131072 : 65536);
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function13 = function1;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Toolbar (CommunalHub.kt:1130)");
            }
            if (!z) {
                function12.mo779invoke(null);
            }
            final State animateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.5f, null, "RemoveButtonAlphaAnimation", null, composerImpl, 3072, 22);
            Modifier.Companion companion = Modifier.Companion;
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(fillMaxWidth, Dimensions.Companion.m1082getItemSpacingD9Ej5fM(), DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(27), Dimensions.Companion.m1082getItemSpacingD9Ej5fM(), 0.0f, 8);
            composerImpl.startReplaceGroup(1412439994);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                function13 = function1;
                rememberedValue = new CommunalHubKt$$ExternalSyntheticLambda10(function13, 0);
                composerImpl.updateRememberedValue(rememberedValue);
            } else {
                function13 = function1;
            }
            composerImpl.end(false);
            Modifier onSizeChanged = OnRemeasuredModifierKt.onSizeChanged(m128paddingqDBjuR0$default, (Function1) rememberedValue);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, onSizeChanged);
            ComposeUiNode.Companion.getClass();
            Function0 function04 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function04);
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
            final String stringResource = StringResources_androidKt.stringResource(R.string.hub_mode_add_widget_button_text, composerImpl);
            composerImpl.startReplaceGroup(-571315015);
            boolean z2 = !z;
            ToolbarButton(z2, function02, boxScopeInstance.align(companion, Alignment.Companion.CenterStart), ComposableLambdaKt.rememberComposableLambda(1682874641, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Toolbar.<anonymous>.<anonymous> (CommunalHub.kt:1159)");
                    }
                    Icons.INSTANCE.getClass();
                    IconKt.m270Iconww6aTOc(AddKt.getAdd(), (String) null, (Modifier) null, 0L, composer2, 48, 12);
                    TextKt.m316Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 9) & 112) | 3072);
            composerImpl.end(false);
            AnimatedVisibilityKt.AnimatedVisibility(z, boxScopeInstance.align(companion, Alignment.Companion.Center), EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(545396663, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2
                /* JADX WARN: Code restructure failed: missing block: B:11:0x0078, code lost:
                
                    if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:7:0x0045, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r17, java.lang.Object r18, java.lang.Object r19) {
                    /*
                        r16 = this;
                        r0 = r16
                        r1 = r17
                        androidx.compose.animation.AnimatedVisibilityScope r1 = (androidx.compose.animation.AnimatedVisibilityScope) r1
                        r1 = r18
                        androidx.compose.runtime.Composer r1 = (androidx.compose.runtime.Composer) r1
                        r2 = r19
                        java.lang.Number r2 = (java.lang.Number) r2
                        r2.intValue()
                        boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r2 == 0) goto L1c
                        java.lang.String r2 = "com.android.systemui.communal.ui.compose.Toolbar.<anonymous>.<anonymous> (CommunalHub.kt:1177)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r2)
                    L1c:
                        androidx.compose.material3.ButtonColors r7 = com.android.systemui.communal.ui.compose.CommunalHubKt.access$filledButtonColors(r1)
                        com.android.systemui.communal.ui.compose.Dimensions$Companion r2 = com.android.systemui.communal.ui.compose.Dimensions.Companion
                        r2.getClass()
                        androidx.compose.foundation.layout.PaddingValuesImpl r10 = com.android.systemui.communal.ui.compose.Dimensions.ButtonPadding
                        androidx.compose.ui.Modifier$Companion r2 = androidx.compose.ui.Modifier.Companion
                        r13 = r1
                        androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
                        r1 = -1682431681(0xffffffff9bb8213f, float:-3.0461731E-22)
                        r13.startReplaceGroup(r1)
                        androidx.compose.runtime.State r1 = r1
                        boolean r3 = r13.changed(r1)
                        java.lang.Object r4 = r13.rememberedValue()
                        androidx.compose.runtime.Composer$Companion r5 = androidx.compose.runtime.Composer.Companion
                        if (r3 != 0) goto L47
                        r5.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r4 != r3) goto L50
                    L47:
                        com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda18 r4 = new com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda18
                        r3 = 4
                        r4.<init>(r1, r3)
                        r13.updateRememberedValue(r4)
                    L50:
                        kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
                        r1 = 0
                        r13.end(r1)
                        androidx.compose.ui.Modifier r2 = androidx.compose.ui.graphics.GraphicsLayerModifierKt.graphicsLayer(r2, r4)
                        r3 = -1682428939(0xffffffff9bb82bf5, float:-3.0468653E-22)
                        r13.startReplaceGroup(r3)
                        boolean r3 = r2
                        boolean r4 = r13.changed(r3)
                        kotlin.jvm.functions.Function1 r6 = r3
                        boolean r8 = r13.changed(r6)
                        r4 = r4 | r8
                        java.lang.Object r8 = r13.rememberedValue()
                        if (r4 != 0) goto L7a
                        r5.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r8 != r4) goto L82
                    L7a:
                        com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2$$ExternalSyntheticLambda1 r8 = new com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2$$ExternalSyntheticLambda1
                        r8.<init>()
                        r13.updateRememberedValue(r8)
                    L82:
                        kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
                        r13.end(r1)
                        androidx.compose.ui.Modifier r4 = androidx.compose.ui.layout.OnGloballyPositionedModifierKt.onGloballyPositioned(r2, r8)
                        com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt r1 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.INSTANCE
                        r1.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r12 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.f31lambda3
                        r14 = 817889280(0x30c00000, float:1.3969839E-9)
                        r15 = 364(0x16c, float:5.1E-43)
                        kotlin.jvm.functions.Function0 r3 = r4
                        r5 = 0
                        r6 = 0
                        r8 = 0
                        r9 = 0
                        r11 = 0
                        androidx.compose.material3.ButtonKt.Button(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
                        boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r0 == 0) goto La9
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    La9:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 200064 | (i2 & 14), 16);
            Modifier align = boxScopeInstance.align(companion, Alignment.Companion.CenterEnd);
            ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
            ToolbarButton(z2, function03, align, ComposableSingletons$CommunalHubKt.f32lambda4, composerImpl, ((i2 >> 12) & 112) | 3072);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function1 function14 = function13;
            endRestartGroup.block = new Function2(z, function0, function14, function12, function02, function03, i) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda11
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Function1 f$3;
                public final /* synthetic */ Function0 f$4;
                public final /* synthetic */ Function0 f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3457);
                    Function0 function05 = this.f$4;
                    Function0 function06 = this.f$5;
                    CommunalHubKt.Toolbar(this.f$0, this.f$1, this.f$2, this.f$3, function05, function06, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ToolbarButton(boolean z, final Function0 function0, Modifier modifier, final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(517616038);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton (CommunalHub.kt:1229)");
            }
            MaterialTheme.INSTANCE.getClass();
            final ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            int i3 = (i2 >> 3) & 112;
            AnimatedVisibilityKt.AnimatedVisibility(z, modifier, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(2005554126, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous> (CommunalHub.kt:1237)");
                    }
                    ButtonColors access$filledButtonColors = CommunalHubKt.access$filledButtonColors(composer2);
                    Dimensions.Companion.getClass();
                    PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                    final Function3 function3 = composableLambdaImpl;
                    ButtonKt.Button(Function0.this, null, false, null, access$filledButtonColors, null, null, paddingValuesImpl, null, ComposableLambdaKt.rememberComposableLambda(167565790, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$1.1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            if ((((Number) obj6).intValue() & 17) == 16) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous>.<anonymous> (CommunalHub.kt:1242)");
                            }
                            Arrangement arrangement = Arrangement.INSTANCE;
                            ButtonDefaults.INSTANCE.getClass();
                            float f = ButtonDefaults.IconSpacing;
                            Alignment.Companion.getClass();
                            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                            arrangement.getClass();
                            Arrangement.SpacedAligned m92spacedByD5KLDUw = Arrangement.m92spacedByD5KLDUw(f, horizontal);
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            Modifier.Companion companion = Modifier.Companion;
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m92spacedByD5KLDUw, vertical, composer3, 48);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, companion);
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
                            Updater.m336setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m336setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m336setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                            Function3.this.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 817889280, 366);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 200064 | i3, 16);
            AnimatedVisibilityKt.AnimatedVisibility(!z, modifier, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(749082231, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous> (CommunalHub.kt:1258)");
                    }
                    ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                    ColorScheme colorScheme2 = colorScheme;
                    long j = colorScheme2.onPrimaryContainer;
                    buttonDefaults.getClass();
                    ButtonColors m252outlinedButtonColorsro_MJ88 = ButtonDefaults.m252outlinedButtonColorsro_MJ88(j, composer2);
                    Dp.Companion companion = Dp.Companion;
                    BorderStroke m31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U((float) 2.0d, colorScheme2.primary);
                    Dimensions.Companion.getClass();
                    PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                    final Function3 function3 = composableLambdaImpl;
                    ButtonKt.OutlinedButton(Function0.this, null, false, null, m252outlinedButtonColorsro_MJ88, null, m31BorderStrokecXLIe8U, paddingValuesImpl, null, ComposableLambdaKt.rememberComposableLambda(-2074918971, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$2.1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            if ((((Number) obj6).intValue() & 17) == 16) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous>.<anonymous> (CommunalHub.kt:1264)");
                            }
                            Arrangement arrangement = Arrangement.INSTANCE;
                            ButtonDefaults.INSTANCE.getClass();
                            float f = ButtonDefaults.IconSpacing;
                            Alignment.Companion.getClass();
                            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                            arrangement.getClass();
                            Arrangement.SpacedAligned m92spacedByD5KLDUw = Arrangement.m92spacedByD5KLDUw(f, horizontal);
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            Modifier.Companion companion2 = Modifier.Companion;
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m92spacedByD5KLDUw, vertical, composer3, 48);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, companion2);
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
                            Updater.m336setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m336setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m336setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                            Function3.this.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 817889280, 302);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, i3 | 200064, 16);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda12(z, function0, modifier, composableLambdaImpl, i, 0);
        }
    }

    public static final void TutorialContent(Modifier modifier, Composer composer, final int i) {
        int i2;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(412326112);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.TutorialContent (CommunalHub.kt:1732)");
            }
            ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
            modifier2 = modifier;
            CardKt.Card(modifier2, null, null, null, null, ComposableSingletons$CommunalHubKt.f36lambda8, composerImpl, (i2 & 14) | 196608, 30);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalHubKt.TutorialContent(Modifier.this, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Umo(final BaseCommunalViewModel baseCommunalViewModel, ContentScope contentScope, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1921529810);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(baseCommunalViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(contentScope) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Umo (CommunalHub.kt:1741)");
            }
            final String stringResource = StringResources_androidKt.stringResource(R.string.accessibility_action_label_umo_show_next, composerImpl);
            final String stringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_umo_show_previous, composerImpl);
            Modifier then = !baseCommunalViewModel.isEditMode() ? modifier.then(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                    SemanticsPropertiesKt.setCustomActions((SemanticsPropertyReceiver) obj, Arrays.asList(new CustomAccessibilityAction(stringResource, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1.1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BaseCommunalViewModel.this.onShowNextMedia();
                            return Boolean.TRUE;
                        }
                    }), new CustomAccessibilityAction(stringResource2, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BaseCommunalViewModel.this.onShowPreviousMedia();
                            return Boolean.TRUE;
                        }
                    })));
                    return Unit.INSTANCE;
                }
            })) : modifier;
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then);
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
            composerImpl.startReplaceGroup(-1542599443);
            UmoLegacy(baseCommunalViewModel, modifier, composerImpl, ((i2 >> 3) & 112) | (i2 & 14));
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(baseCommunalViewModel, contentScope, modifier, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x007f, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a8, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void UmoLegacy(final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r8, androidx.compose.ui.Modifier r9, androidx.compose.runtime.Composer r10, int r11) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.UmoLegacy(com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public static final void WidgetConfigureButton(boolean z, final CommunalContentModel.WidgetContent.Widget widget, Modifier modifier, final WidgetConfigurator widgetConfigurator, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2015050074);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(widget) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(widgetConfigurator) : composerImpl.changedInstance(widgetConfigurator) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.WidgetConfigureButton (CommunalHub.kt:1604)");
            }
            MaterialTheme.INSTANCE.getClass();
            final ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            EnterTransition fadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
            ExitTransition fadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
            DensityUtils.Companion.getClass();
            AnimatedVisibilityKt.AnimatedVisibility(z, PaddingKt.m124padding3ABfNKs(modifier, DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(16)), fadeIn$default, fadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(-635566206, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1
                /* JADX WARN: Code restructure failed: missing block: B:7:0x0078, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r21, java.lang.Object r22, java.lang.Object r23) {
                    /*
                        r20 = this;
                        r0 = r20
                        r1 = r21
                        androidx.compose.animation.AnimatedVisibilityScope r1 = (androidx.compose.animation.AnimatedVisibilityScope) r1
                        r1 = r22
                        androidx.compose.runtime.Composer r1 = (androidx.compose.runtime.Composer) r1
                        r2 = r23
                        java.lang.Number r2 = (java.lang.Number) r2
                        r2.intValue()
                        boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r2 == 0) goto L1c
                        java.lang.String r2 = "com.android.systemui.communal.ui.compose.WidgetConfigureButton.<anonymous> (CommunalHub.kt:1614)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r2)
                    L1c:
                        com.android.systemui.communal.util.DensityUtils$Companion r2 = com.android.systemui.communal.util.DensityUtils.Companion
                        r2.getClass()
                        r2 = 16
                        float r2 = com.android.systemui.communal.util.DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(r2)
                        androidx.compose.foundation.shape.RoundedCornerShape r9 = androidx.compose.foundation.shape.RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(r2)
                        androidx.compose.ui.Modifier$Companion r2 = androidx.compose.ui.Modifier.Companion
                        r3 = 48
                        float r3 = com.android.systemui.communal.util.DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(r3)
                        androidx.compose.ui.Modifier r8 = androidx.compose.foundation.layout.SizeKt.m139size3ABfNKs(r2, r3)
                        androidx.compose.material3.IconButtonColors r6 = new androidx.compose.material3.IconButtonColors
                        androidx.compose.material3.ColorScheme r2 = androidx.compose.material3.ColorScheme.this
                        long r11 = r2.primary
                        androidx.compose.ui.graphics.Color$Companion r3 = androidx.compose.ui.graphics.Color.Companion
                        r3.getClass()
                        long r15 = androidx.compose.ui.graphics.Color.Transparent
                        r19 = 0
                        long r13 = r2.onPrimary
                        r17 = r15
                        r10 = r6
                        r10.<init>(r11, r13, r15, r17, r19)
                        r7 = r1
                        androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                        r1 = -899264314(0xffffffffca6650c6, float:-3773489.5)
                        r7.startReplaceGroup(r1)
                        kotlinx.coroutines.CoroutineScope r1 = r2
                        boolean r2 = r7.changedInstance(r1)
                        com.android.systemui.communal.widgets.WidgetConfigurator r3 = r3
                        boolean r4 = r7.changedInstance(r3)
                        r2 = r2 | r4
                        com.android.systemui.communal.domain.model.CommunalContentModel$WidgetContent$Widget r0 = r4
                        boolean r4 = r7.changedInstance(r0)
                        r2 = r2 | r4
                        java.lang.Object r4 = r7.rememberedValue()
                        if (r2 != 0) goto L7a
                        androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
                        r2.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r4 != r2) goto L83
                    L7a:
                        com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0 r4 = new com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0
                        r2 = 1
                        r4.<init>(r1, r3, r2, r0)
                        r7.updateRememberedValue(r4)
                    L83:
                        r10 = r4
                        kotlin.jvm.functions.Function0 r10 = (kotlin.jvm.functions.Function0) r10
                        r0 = 0
                        r7.end(r0)
                        com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt r0 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.INSTANCE
                        r0.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r11 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.f35lambda7
                        r3 = 1572864(0x180000, float:2.204052E-39)
                        r4 = 36
                        r12 = 0
                        r5 = 0
                        androidx.compose.material3.IconButtonKt.FilledIconButton(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
                        boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r0 == 0) goto La3
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    La3:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, (i2 & 14) | 200064, 16);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda12(z, widget, modifier, widgetConfigurator, i, 1);
        }
    }

    public static final void WidgetContent(final BaseCommunalViewModel baseCommunalViewModel, final CommunalContentModel.WidgetContent.Widget widget, final SizeF sizeF, final boolean z, final WidgetConfigurator widgetConfigurator, final Modifier modifier, final int i, final ContentListState contentListState, final CommunalAppWidgetSection communalAppWidgetSection, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, Composer composer, final int i2) {
        int i3;
        int i4;
        ResizeableItemFrameViewModel resizeableItemFrameViewModel2;
        MutableState mutableState;
        Integer num;
        CoroutineScope coroutineScope;
        boolean z2;
        MutableInteractionSource mutableInteractionSource;
        boolean z3;
        Modifier modifier2;
        Object obj;
        ComposerImpl composerImpl;
        boolean z4;
        Throwable th;
        ComposerImpl composerImpl2;
        boolean z5;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(1849612888);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl3.changedInstance(baseCommunalViewModel) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl3.changedInstance(widget) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl3.changedInstance(sizeF) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl3.changed(z) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= (32768 & i2) == 0 ? composerImpl3.changed(widgetConfigurator) : composerImpl3.changedInstance(widgetConfigurator) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            i3 |= composerImpl3.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i2) == 0) {
            i4 = i;
            i3 |= composerImpl3.changed(i4) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            i4 = i;
        }
        if ((12582912 & i2) == 0) {
            i3 |= composerImpl3.changedInstance(contentListState) ? 8388608 : 4194304;
        }
        if ((100663296 & i2) == 0) {
            i3 |= composerImpl3.changedInstance(communalAppWidgetSection) ? 67108864 : 33554432;
        }
        if ((805306368 & i2) == 0) {
            resizeableItemFrameViewModel2 = resizeableItemFrameViewModel;
            i3 |= composerImpl3.changedInstance(resizeableItemFrameViewModel2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        } else {
            resizeableItemFrameViewModel2 = resizeableItemFrameViewModel;
        }
        int i5 = i3;
        if ((i5 & 306783379) == 306783378 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            composerImpl2 = composerImpl3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.WidgetContent (CommunalHub.kt:1444)");
            }
            Object rememberedValue = composerImpl3.rememberedValue();
            Composer.Companion.getClass();
            Object obj2 = Composer.Companion.Empty;
            if (rememberedValue == obj2) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                composerImpl3.updateRememberedValue(rememberedValue);
            }
            CoroutineScope coroutineScope2 = (CoroutineScope) rememberedValue;
            Context context = (Context) composerImpl3.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl3.startReplaceGroup(-1361363947);
            boolean changed = composerImpl3.changed(widget) | composerImpl3.changed(context);
            Object rememberedValue2 = composerImpl3.rememberedValue();
            if (changed || rememberedValue2 == obj2) {
                rememberedValue2 = StringsKt__StringsKt.trim(widget.providerInfo.loadLabel(context.getPackageManager()).toString()).toString();
                composerImpl3.updateRememberedValue(rememberedValue2);
            }
            final String str = (String) rememberedValue2;
            composerImpl3.end(false);
            final String stringResource = StringResources_androidKt.stringResource(R.string.accessibility_action_label_select_widget, composerImpl3);
            final String stringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_remove_widget, composerImpl3);
            final String stringResource3 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_place_widget, composerImpl3);
            final String stringResource4 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_unselect_widget, composerImpl3);
            final String stringResource5 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_shrink_widget, composerImpl3);
            final String stringResource6 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_expand_widget, composerImpl3);
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isFocusable(), Boolean.FALSE, composerImpl3, 48);
            MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.selectedKey, composerImpl3);
            String str2 = (String) collectAsStateWithLifecycle2.getValue();
            if (str2 != null) {
                ListIterator listIterator = contentListState.list.listIterator();
                int i6 = 0;
                while (true) {
                    if (!listIterator.hasNext()) {
                        mutableState = collectAsStateWithLifecycle2;
                        i6 = -1;
                        break;
                    } else {
                        mutableState = collectAsStateWithLifecycle2;
                        if (Intrinsics.areEqual(((CommunalContentModel) listIterator.next()).getKey(), str2)) {
                            break;
                        }
                        i6++;
                        collectAsStateWithLifecycle2 = mutableState;
                    }
                }
                num = Integer.valueOf(i6);
            } else {
                mutableState = collectAsStateWithLifecycle2;
                num = null;
            }
            composerImpl3.startReplaceGroup(-1361331227);
            Object rememberedValue3 = composerImpl3.rememberedValue();
            Object obj3 = Composer.Companion.Empty;
            if (rememberedValue3 == obj3) {
                rememberedValue3 = InteractionSourceKt.MutableInteractionSource();
                composerImpl3.updateRememberedValue(rememberedValue3);
            }
            MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) rememberedValue3;
            final Integer num2 = num;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, -1361329157);
            if (m == obj3) {
                m = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl3);
            }
            FocusRequester focusRequester = (FocusRequester) m;
            composerImpl3.end(false);
            composerImpl3.startReplaceGroup(-1361327894);
            if (baseCommunalViewModel.isEditMode() && z) {
                Unit unit = Unit.INSTANCE;
                composerImpl3.startReplaceGroup(-1361325761);
                Object rememberedValue4 = composerImpl3.rememberedValue();
                if (rememberedValue4 == obj3) {
                    coroutineScope = coroutineScope2;
                    rememberedValue4 = new CommunalHubKt$WidgetContent$1$1(focusRequester, null);
                    composerImpl3.updateRememberedValue(rememberedValue4);
                } else {
                    coroutineScope = coroutineScope2;
                }
                z2 = false;
                composerImpl3.end(false);
                EffectsKt.LaunchedEffect(composerImpl3, unit, (Function2) rememberedValue4);
            } else {
                coroutineScope = coroutineScope2;
                z2 = false;
            }
            composerImpl3.end(z2);
            boolean areEqual = Intrinsics.areEqual((String) mutableState.getValue(), widget.key);
            composerImpl3.startReplaceGroup(-1361318500);
            if (baseCommunalViewModel.isEditMode()) {
                Modifier.Companion companion = Modifier.Companion;
                composerImpl3.startReplaceGroup(-1361314747);
                boolean changedInstance = composerImpl3.changedInstance(baseCommunalViewModel) | composerImpl3.changedInstance(widget);
                Object rememberedValue5 = composerImpl3.rememberedValue();
                if (changedInstance || rememberedValue5 == obj3) {
                    rememberedValue5 = new CommunalHubKt$$ExternalSyntheticLambda0(1, baseCommunalViewModel, widget);
                    composerImpl3.updateRememberedValue(rememberedValue5);
                }
                z3 = false;
                composerImpl3.end(false);
                modifier2 = SelectableKt.m181selectableO2vRcR0(companion, areEqual, mutableInteractionSource2, null, true, null, (Function0) rememberedValue5);
                mutableInteractionSource = mutableInteractionSource2;
            } else {
                mutableInteractionSource = mutableInteractionSource2;
                z3 = false;
                modifier2 = Modifier.Companion;
            }
            composerImpl3.end(z3);
            Modifier then = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester), z3, mutableInteractionSource, 1).then(modifier2);
            boolean isEditMode = baseCommunalViewModel.isEditMode();
            boolean z6 = widget.inQuietMode;
            if ((isEditMode || z6) ? z3 : true) {
                then = then.then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, Unit.INSTANCE, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$2$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final BaseCommunalViewModel baseCommunalViewModel2 = BaseCommunalViewModel.this;
                        final CommunalContentModel.WidgetContent.Widget widget2 = widget;
                        Object observeTaps$default = PointerInputScopeExtKt.observeTaps$default(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$2$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                long j = ((Offset) obj4).packedValue;
                                CommunalContentModel.WidgetContent.Widget widget3 = widget2;
                                BaseCommunalViewModel.this.onTapWidget(widget3.rank, widget3.componentName);
                                return Unit.INSTANCE;
                            }
                        }, continuation, 3);
                        return observeTaps$default == CoroutineSingletons.COROUTINE_SUSPENDED ? observeTaps$default : Unit.INSTANCE;
                    }
                }));
            }
            if (!baseCommunalViewModel.isEditMode() && z6) {
                then = then.then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, Unit.INSTANCE, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$3$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final BaseCommunalViewModel baseCommunalViewModel2 = BaseCommunalViewModel.this;
                        Object observeTaps$default = PointerInputScopeExtKt.observeTaps$default(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$3$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                long j = ((Offset) obj4).packedValue;
                                BaseCommunalViewModel.this.onOpenEnableWorkProfileDialog();
                                return Unit.INSTANCE;
                            }
                        }, continuation, 1);
                        return observeTaps$default == CoroutineSingletons.COROUTINE_SUSPENDED ? observeTaps$default : Unit.INSTANCE;
                    }
                }));
            }
            if (baseCommunalViewModel.isEditMode()) {
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifier3 = then;
                obj = obj3;
                composerImpl = composerImpl3;
                final int i7 = i4;
                final ResizeableItemFrameViewModel resizeableItemFrameViewModel3 = resizeableItemFrameViewModel2;
                final CoroutineScope coroutineScope3 = coroutineScope;
                th = null;
                Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj4) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj4;
                        String str3 = stringResource;
                        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, str3, null);
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                        final ContentListState contentListState2 = contentListState;
                        final int i8 = i7;
                        List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(new CustomAccessibilityAction(stringResource2, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1$deleteAction$1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i9 = i8;
                                ContentListState contentListState3 = ContentListState.this;
                                contentListState3.onRemove(i9);
                                ContentListState.onSaveList$default(contentListState3);
                                return Boolean.TRUE;
                            }
                        }));
                        final ResizeableItemFrameViewModel resizeableItemFrameViewModel4 = resizeableItemFrameViewModel3;
                        Integer nextAnchor = ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel4.bottomDragState, true);
                        AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel4.topDragState;
                        boolean z7 = (nextAnchor == null && ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, false) == null) ? false : true;
                        final CoroutineScope coroutineScope4 = coroutineScope3;
                        if (z7) {
                            ((ArrayList) mutableListOf).add(new CustomAccessibilityAction(stringResource5, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.1

                                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                                /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1$1$1, reason: invalid class name and collision with other inner class name */
                                final class C00881 extends SuspendLambda implements Function2 {
                                    final /* synthetic */ ResizeableItemFrameViewModel $resizeableItemFrameViewModel;
                                    int label;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C00881(ResizeableItemFrameViewModel resizeableItemFrameViewModel, Continuation continuation) {
                                        super(2, continuation);
                                        this.$resizeableItemFrameViewModel = resizeableItemFrameViewModel;
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Continuation create(Object obj, Continuation continuation) {
                                        return new C00881(this.$resizeableItemFrameViewModel, continuation);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        return ((C00881) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        Object obj2;
                                        Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i = this.label;
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.$resizeableItemFrameViewModel;
                                            this.label = 1;
                                            Integer nextAnchor = ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel.bottomDragState, true);
                                            AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel.topDragState;
                                            if (nextAnchor == null && ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, false) == null) {
                                                obj2 = Unit.INSTANCE;
                                            } else {
                                                Integer nextAnchor2 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, false);
                                                if (nextAnchor2 != null) {
                                                    obj2 = AnchoredDraggableKt.snapTo(anchoredDraggableState, nextAnchor2, this);
                                                    if (obj2 != obj3) {
                                                        obj2 = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    AnchoredDraggableState anchoredDraggableState2 = resizeableItemFrameViewModel.bottomDragState;
                                                    Integer nextAnchor3 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState2, true);
                                                    if (nextAnchor3 == null || (obj2 = AnchoredDraggableKt.snapTo(anchoredDraggableState2, new Integer(nextAnchor3.intValue()), this)) != obj3) {
                                                        obj2 = Unit.INSTANCE;
                                                    }
                                                }
                                            }
                                            if (obj2 == obj3) {
                                                return obj3;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    BuildersKt.launch$default(CoroutineScope.this, null, null, new C00881(resizeableItemFrameViewModel4, null), 3);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        if (ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel4.bottomDragState, false) != null || ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true) != null) {
                            ((ArrayList) mutableListOf).add(new CustomAccessibilityAction(stringResource6, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.2

                                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                                /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1$2$1, reason: invalid class name */
                                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                    final /* synthetic */ ResizeableItemFrameViewModel $resizeableItemFrameViewModel;
                                    int label;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public AnonymousClass1(ResizeableItemFrameViewModel resizeableItemFrameViewModel, Continuation continuation) {
                                        super(2, continuation);
                                        this.$resizeableItemFrameViewModel = resizeableItemFrameViewModel;
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Continuation create(Object obj, Continuation continuation) {
                                        return new AnonymousClass1(this.$resizeableItemFrameViewModel, continuation);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        Object obj2;
                                        Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i = this.label;
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.$resizeableItemFrameViewModel;
                                            this.label = 1;
                                            Integer nextAnchor = ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel.bottomDragState, false);
                                            AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel.topDragState;
                                            if (nextAnchor == null && ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true) == null) {
                                                obj2 = Unit.INSTANCE;
                                            } else {
                                                AnchoredDraggableState anchoredDraggableState2 = resizeableItemFrameViewModel.bottomDragState;
                                                Integer nextAnchor2 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState2, false);
                                                if (nextAnchor2 != null) {
                                                    obj2 = AnchoredDraggableKt.snapTo(anchoredDraggableState2, nextAnchor2, this);
                                                    if (obj2 != obj3) {
                                                        obj2 = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    Integer nextAnchor3 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true);
                                                    if (nextAnchor3 == null || (obj2 = AnchoredDraggableKt.snapTo(anchoredDraggableState, new Integer(nextAnchor3.intValue()), this)) != obj3) {
                                                        obj2 = Unit.INSTANCE;
                                                    }
                                                }
                                            }
                                            if (obj2 == obj3) {
                                                return obj3;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    BuildersKt.launch$default(CoroutineScope.this, null, null, new AnonymousClass1(resizeableItemFrameViewModel4, null), 3);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        final Integer num3 = num2;
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        if (num3 != null && num3.intValue() != i8) {
                            ((ArrayList) mutableListOf).add(new CustomAccessibilityAction(stringResource3, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.3
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    Integer num4 = num3;
                                    num4.getClass();
                                    int intValue = num4.intValue();
                                    ContentListState contentListState3 = ContentListState.this;
                                    SnapshotStateList snapshotStateList = contentListState3.list;
                                    snapshotStateList.add(i8, snapshotStateList.remove(intValue));
                                    ContentListState.onSaveList$default(contentListState3);
                                    baseCommunalViewModel2.setSelectedKey(null);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        if (z) {
                            ((ArrayList) mutableListOf).add(new CustomAccessibilityAction(stringResource4, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.5
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    BaseCommunalViewModel.this.setSelectedKey(null);
                                    return Boolean.TRUE;
                                }
                            }));
                        } else {
                            final CommunalContentModel.WidgetContent.Widget widget2 = widget;
                            ((ArrayList) mutableListOf).add(new CustomAccessibilityAction(str3, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.4
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    BaseCommunalViewModel.this.setSelectedKey(widget2.key);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, mutableListOf);
                        return Unit.INSTANCE;
                    }
                };
                z4 = false;
                then = modifier3.then(SemanticsModifierKt.semantics(companion2, false, function1));
            } else {
                obj = obj3;
                composerImpl = composerImpl3;
                z4 = false;
                th = null;
            }
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, z4);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            ComposerImpl composerImpl4 = composerImpl;
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, then);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl4.applier == null) {
                ComposablesKt.invalidApplier();
                throw th;
            }
            composerImpl4.startReusableNode();
            if (composerImpl4.inserting) {
                composerImpl4.createNode(function0);
            } else {
                composerImpl4.useNode();
            }
            Updater.m336setimpl(composerImpl4, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl4, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl4, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl4.startReplaceGroup(539478186);
            boolean booleanValue = ((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue();
            composerImpl4.startReplaceGroup(-781249286);
            boolean changedInstance2 = composerImpl4.changedInstance(baseCommunalViewModel) | composerImpl4.changedInstance(widget);
            Object rememberedValue6 = composerImpl4.rememberedValue();
            if (changedInstance2 || rememberedValue6 == obj) {
                rememberedValue6 = new CommunalHubKt$$ExternalSyntheticLambda0(2, baseCommunalViewModel, widget);
                composerImpl4.updateRememberedValue(rememberedValue6);
            }
            Function0 function02 = (Function0) rememberedValue6;
            composerImpl4.end(z4);
            Modifier.Companion companion3 = Modifier.Companion;
            Modifier fillMaxSize = SizeKt.fillMaxSize(companion3, 1.0f);
            if (baseCommunalViewModel.isEditMode()) {
                fillMaxSize = fillMaxSize.then(SuspendingPointerInputFilterKt.pointerInput(fillMaxSize, Unit.INSTANCE, ModifierExtKt$allowGestures$1.INSTANCE));
            }
            communalAppWidgetSection.Widget(booleanValue, function02, widget, sizeF, fillMaxSize, composerImpl4, (i5 << 3) & 8064);
            composerImpl2 = composerImpl4;
            Unit unit2 = Unit.INSTANCE;
            composerImpl2.end(z4);
            composerImpl2.startReplaceGroup(539492173);
            if (baseCommunalViewModel instanceof CommunalEditModeViewModel) {
                AppWidgetProviderInfo appWidgetProviderInfo = widget.providerInfo;
                z5 = true;
                if ((((appWidgetProviderInfo.widgetFeatures & 1) == 0 || appWidgetProviderInfo.configure == null) ? z4 : true) && widgetConfigurator != null) {
                    WidgetConfigureButton(z, widget, boxScopeInstance.align(companion3, Alignment.Companion.BottomEnd), widgetConfigurator, composerImpl2, ((i5 >> 9) & 14) | (i5 & 112) | ((i5 >> 3) & 7168));
                }
            } else {
                z5 = true;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, z4, z5)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda31
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Integer) obj5).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    CommunalAppWidgetSection communalAppWidgetSection2 = communalAppWidgetSection;
                    ResizeableItemFrameViewModel resizeableItemFrameViewModel4 = resizeableItemFrameViewModel;
                    CommunalHubKt.WidgetContent(BaseCommunalViewModel.this, widget, sizeF, z, widgetConfigurator, modifier, i, contentListState, communalAppWidgetSection2, resizeableItemFrameViewModel4, (Composer) obj4, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final long access$CommunalHub$lambda$11(MutableState mutableState) {
        return ((Offset) mutableState.getValue()).packedValue;
    }

    public static final ButtonColors access$filledButtonColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-878954106);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.filledButtonColors (CommunalHub.kt:1276)");
        }
        MaterialTheme.INSTANCE.getClass();
        ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
        ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
        long j = colorScheme.primary;
        buttonDefaults.getClass();
        ButtonColors m251buttonColorsro_MJ88 = ButtonDefaults.m251buttonColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m251buttonColorsro_MJ88;
    }

    public static final String access$keyAtIndexIfEditable(int i, List list) {
        if (i < 0 || i >= list.size()) {
            return null;
        }
        CommunalContentModel communalContentModel = (CommunalContentModel) list.get(i);
        communalContentModel.getClass();
        if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
            return ((CommunalContentModel) list.get(i)).getKey();
        }
        return null;
    }

    /* renamed from: access$nonScalableTextSize-8Feqmps, reason: not valid java name */
    public static final long m1080access$nonScalableTextSize8Feqmps(float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-647122957);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.nonScalableTextSize (CommunalHub.kt:1866)");
        }
        long mo59toSp0xMU5do = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo59toSp0xMU5do(f);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mo59toSp0xMU5do;
    }

    public static final float dp(CommunalContentSize.FixedSize fixedSize) {
        int i = WhenMappings.$EnumSwitchMapping$0[fixedSize.ordinal()];
        if (i == 1) {
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            return DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(530);
        }
        if (i == 2) {
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            float m1089getAdjustedDpu2uoSUM = DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(530) - Dimensions.Companion.m1082getItemSpacingD9Ej5fM();
            Dp.Companion companion = Dp.Companion;
            return m1089getAdjustedDpu2uoSUM / 2;
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        Dimensions.Companion.getClass();
        DensityUtils.Companion.getClass();
        float m1089getAdjustedDpu2uoSUM2 = DensityUtils.Companion.m1089getAdjustedDpu2uoSUM(530);
        float m1082getItemSpacingD9Ej5fM = 2 * Dimensions.Companion.m1082getItemSpacingD9Ej5fM();
        Dp.Companion companion2 = Dp.Companion;
        return (m1089getAdjustedDpu2uoSUM2 - m1082getItemSpacingD9Ej5fM) / 3;
    }

    public static final Dimensions getHubDimensions(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1423256976);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.<get-hubDimensions> (CommunalHub.kt:543)");
        }
        Dimensions dimensions = new Dimensions((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext), (Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return dimensions;
    }
}
