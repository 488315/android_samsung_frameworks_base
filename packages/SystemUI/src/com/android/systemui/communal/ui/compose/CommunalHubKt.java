package com.android.systemui.communal.ui.compose;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.util.SizeF;
import android.widget.RemoteViews;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridDslKt;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridScrollPosition;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.grid.LazyGridStateKt;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
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
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.ModalBottomSheetKt;
import androidx.compose.material3.SheetState;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
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
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorMatrix;
import androidx.compose.ui.graphics.ColorMatrixColorFilter;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.window.layout.WindowMetricsCalculator;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.Easings;
import com.android.compose.animation.Easings$fromInterpolator$1;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.R;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.compose.Dimensions;
import com.android.systemui.communal.ui.compose.extensions.LazyGridStateExtKt;
import com.android.systemui.communal.ui.compose.extensions.ModifierExtKt$allowGestures$1;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import com.android.systemui.communal.util.DensityUtils;
import com.android.systemui.communal.widgets.WidgetConfigurationController;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public abstract class CommunalHubKt {

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
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isFocusable(), Boolean.FALSE, composerImpl, 48);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion, 1.0f), 3);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue() && !baseCommunalViewModel.isEditMode()) {
                modifierWrapContentHeight$default = modifierWrapContentHeight$default.then(SemanticsModifierKt.semantics(FocusableKt.focusable$default(companion, ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue(), null, 2), false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, context.getString(R.string.accessibility_content_description_for_communal_hub));
                        String string = context.getString(R.string.accessibility_action_label_close_communal_hub);
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Arrays.asList(new CustomAccessibilityAction(string, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                BaseCommunalViewModel.changeScene$default(baseCommunalViewModel2, CommunalScenes.Blank, "closed by accessibility", null, 12);
                                return Boolean.TRUE;
                            }
                        }), new CustomAccessibilityAction(context.getString(R.string.accessibility_action_label_edit_widgets), new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel2;
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
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default);
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
            composableLambdaImpl.invoke(composerImpl, Integer.valueOf((i2 >> 3) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda3(baseCommunalViewModel, composableLambdaImpl, i);
        }
    }

    public static final void CommunalContent(final CommunalContentModel communalContentModel, BaseCommunalViewModel baseCommunalViewModel, final SizeF sizeF, final boolean z, Modifier modifier, WidgetConfigurator widgetConfigurator, final int i, final ContentListState contentListState, final RemoteViews.InteractionHandler interactionHandler, final CommunalAppWidgetSection communalAppWidgetSection, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, ContentScope contentScope, Composer composer, final int i2, final int i3) {
        int i4;
        SizeF sizeF2;
        int i5;
        WidgetConfigurator widgetConfigurator2;
        final BaseCommunalViewModel baseCommunalViewModel2;
        final ContentScope contentScope2;
        ComposerImpl composerImpl;
        final WidgetConfigurator widgetConfigurator3;
        final Modifier modifier2 = modifier;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(809886353);
        if ((i2 & 6) == 0) {
            i4 = ((i2 & 8) == 0 ? composerImpl2.changed(communalContentModel) : composerImpl2.changedInstance(communalContentModel) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerImpl2.changedInstance(baseCommunalViewModel) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            sizeF2 = sizeF;
            i4 |= composerImpl2.changedInstance(sizeF2) ? 256 : 128;
        } else {
            sizeF2 = sizeF;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerImpl2.changed(modifier2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        int i6 = i3 & 32;
        int i7 = 196608;
        if (i6 != 0) {
            i4 |= i7;
        } else if ((i2 & 196608) == 0) {
            i7 = (i2 & 262144) == 0 ? composerImpl2.changed(widgetConfigurator) : composerImpl2.changedInstance(widgetConfigurator) ? 131072 : 65536;
            i4 |= i7;
        }
        if ((i2 & 1572864) == 0) {
            i4 |= composerImpl2.changed(i) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((i2 & 12582912) == 0) {
            i4 |= composerImpl2.changedInstance(contentListState) ? 8388608 : 4194304;
        }
        if ((i2 & 100663296) == 0) {
            i4 |= composerImpl2.changedInstance(interactionHandler) ? 67108864 : 33554432;
        }
        if ((i2 & 805306368) == 0) {
            i4 |= composerImpl2.changedInstance(communalAppWidgetSection) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        int i8 = composerImpl2.changedInstance(resizeableItemFrameViewModel) ? 4 : 2;
        int i9 = i3 & 2048;
        if (i9 != 0) {
            i5 = i8 | 48;
        } else {
            i5 = i8 | (composerImpl2.changed(contentScope) ? 32 : 16);
        }
        if ((i4 & 306783379) == 306783378 && (i5 & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            baseCommunalViewModel2 = baseCommunalViewModel;
            widgetConfigurator3 = widgetConfigurator;
            composerImpl = composerImpl2;
            contentScope2 = contentScope;
        } else {
            WidgetConfigurator widgetConfigurator4 = i6 != 0 ? null : widgetConfigurator;
            ContentScope contentScope3 = i9 == 0 ? contentScope : null;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContent (CommunalHub.kt:1298)");
            }
            if (communalContentModel instanceof CommunalContentModel.WidgetContent.Widget) {
                composerImpl2.startReplaceGroup(-1444959308);
                int i10 = i4 >> 3;
                widgetConfigurator2 = widgetConfigurator4;
                baseCommunalViewModel2 = baseCommunalViewModel;
                WidgetContent(baseCommunalViewModel2, (CommunalContentModel.WidgetContent.Widget) communalContentModel, sizeF2, z, widgetConfigurator2, modifier2, i, contentListState, communalAppWidgetSection, resizeableItemFrameViewModel, composerImpl2, (i4 & 7168) | (i10 & 14) | (i4 & 896) | (57344 & i10) | ((i4 << 3) & 458752) | (3670016 & i4) | (i4 & 29360128) | (234881024 & i10) | ((i5 << 27) & 1879048192));
                modifier2 = modifier2;
                composerImpl2.end(false);
            } else {
                widgetConfigurator2 = widgetConfigurator4;
                baseCommunalViewModel2 = baseCommunalViewModel;
                if (communalContentModel instanceof CommunalContentModel.WidgetPlaceholder) {
                    composerImpl2.startReplaceGroup(-1444947573);
                    HighlightedItem(modifier2, 0.0f, composerImpl2, (i4 >> 12) & 14, 2);
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.WidgetContent.DisabledWidget) {
                    composerImpl2.startReplaceGroup(-1444944281);
                    DisabledWidgetPlaceholder((CommunalContentModel.WidgetContent.DisabledWidget) communalContentModel, baseCommunalViewModel2, modifier2, composerImpl2, ((i4 >> 6) & 896) | (i4 & 112));
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.WidgetContent.PendingWidget) {
                    composerImpl2.startReplaceGroup(-1444940165);
                    PendingWidgetPlaceholder((CommunalContentModel.WidgetContent.PendingWidget) communalContentModel, modifier2, composerImpl2, (i4 >> 9) & 112);
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.CtaTileInViewMode) {
                    composerImpl2.startReplaceGroup(-1444937121);
                    CtaTileInViewModeContent(baseCommunalViewModel2, modifier2, composerImpl2, ((i4 >> 3) & 14) | ((i4 >> 9) & 112));
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.Smartspace) {
                    composerImpl2.startReplaceGroup(-1444934168);
                    SmartspaceContent(interactionHandler, (CommunalContentModel.Smartspace) communalContentModel, modifier2, composerImpl2, ((i4 >> 6) & 896) | ((i4 >> 24) & 14));
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.Tutorial) {
                    composerImpl2.startReplaceGroup(-1444931029);
                    TutorialContent(modifier2, composerImpl2, (i4 >> 12) & 14);
                    composerImpl2.end(false);
                } else if (communalContentModel instanceof CommunalContentModel.Umo) {
                    composerImpl2.startReplaceGroup(-1444928936);
                    Umo(baseCommunalViewModel2, contentScope3, modifier2, composerImpl2, ((i4 >> 3) & 14) | (i5 & 112) | ((i4 >> 6) & 896));
                    composerImpl2.end(false);
                } else {
                    if (!(communalContentModel instanceof CommunalContentModel.Spacer)) {
                        composerImpl2.startReplaceGroup(-1444961131);
                        composerImpl2.end(false);
                        throw new NoWhenBranchMatchedException();
                    }
                    composerImpl2.startReplaceGroup(-1444926355);
                    BoxKt.Box(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), composerImpl2, 6);
                    composerImpl2.end(false);
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            contentScope2 = contentScope3;
            composerImpl = composerImpl2;
            widgetConfigurator3 = widgetConfigurator2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    ContentScope contentScope4 = contentScope2;
                    int i11 = i3;
                    CommunalHubKt.CommunalContent(communalContentModel, baseCommunalViewModel2, sizeF, z, modifier2, widgetConfigurator3, i, contentListState, interactionHandler, communalAppWidgetSection, resizeableItemFrameViewModel, contentScope4, (Composer) obj, iUpdateChangedFlags, i11);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:208:0x06ac  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x06f5  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0716  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CommunalHub(Modifier modifier, final BaseCommunalViewModel baseCommunalViewModel, final CommunalAppWidgetSection communalAppWidgetSection, RemoteViews.InteractionHandler interactionHandler, SystemUIDialogFactory systemUIDialogFactory, WidgetConfigurationController widgetConfigurationController, Function0 function0, Function0 function02, ContentScope contentScope, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        final RemoteViews.InteractionHandler interactionHandler2;
        int i4;
        final SystemUIDialogFactory systemUIDialogFactory2;
        int i5;
        final WidgetConfigurationController widgetConfigurationController2;
        int i6;
        int i7;
        Function0 function03;
        int i8;
        final ContentScope contentScope2;
        int i9;
        MutableState mutableState;
        boolean z;
        Boolean bool;
        MutableState mutableState2;
        State state;
        PaddingValuesImpl paddingValuesImplM124PaddingValuesa9UjIt4$default;
        boolean z2;
        final MutableState mutableState3;
        MutableState mutableState4;
        Boolean bool2;
        final int i10;
        Object obj;
        ContentListState contentListState;
        PaddingValuesImpl paddingValuesImpl;
        WidgetConfigurationController widgetConfigurationController3;
        ContentScope contentScope3;
        final LayoutDirection layoutDirection;
        Modifier modifierThen;
        final BaseCommunalViewModel baseCommunalViewModel2;
        Throwable th;
        final MutableState mutableState5;
        final MutableState mutableState6;
        final Function0 function04;
        final Function0 function05;
        int i11;
        boolean z3;
        SystemUIDialogFactory systemUIDialogFactory3;
        Object obj2;
        Boolean bool3;
        Function0 function06;
        final Function0 function07;
        final WidgetConfigurationController widgetConfigurationController4;
        Object obj3;
        boolean zChangedInstance;
        Object objRememberedValue;
        ComposerImpl composerImpl;
        boolean zChangedInstance2;
        Object objRememberedValue2;
        boolean zChangedInstance3;
        Object objRememberedValue3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-697702350);
        int i12 = i2 & 1;
        if (i12 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else {
            modifier2 = modifier;
            i3 = i | (composerImpl2.changed(modifier2) ? 4 : 2);
        }
        int i13 = i3 | (composerImpl2.changedInstance(baseCommunalViewModel) ? 32 : 16) | (composerImpl2.changedInstance(communalAppWidgetSection) ? 256 : 128);
        int i14 = i2 & 8;
        if (i14 != 0) {
            i4 = i13 | 3072;
            interactionHandler2 = interactionHandler;
        } else {
            interactionHandler2 = interactionHandler;
            i4 = i13 | (composerImpl2.changedInstance(interactionHandler2) ? 2048 : 1024);
        }
        int i15 = i2 & 16;
        if (i15 != 0) {
            i5 = i4 | 24576;
            systemUIDialogFactory2 = systemUIDialogFactory;
        } else {
            systemUIDialogFactory2 = systemUIDialogFactory;
            i5 = i4 | (composerImpl2.changedInstance(systemUIDialogFactory2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        }
        int i16 = i2 & 32;
        if (i16 != 0) {
            i6 = i5 | 196608;
            widgetConfigurationController2 = widgetConfigurationController;
        } else {
            widgetConfigurationController2 = widgetConfigurationController;
            i6 = i5 | (composerImpl2.changed(widgetConfigurationController2) ? 131072 : 65536);
        }
        int i17 = i2 & 64;
        if (i17 != 0) {
            i7 = i6 | 1572864;
        } else {
            i7 = i6 | (composerImpl2.changedInstance(function0) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        }
        int i18 = 128 & i2;
        if (i18 != 0) {
            i8 = i7 | 12582912;
            function03 = function02;
        } else {
            function03 = function02;
            i8 = i7 | (composerImpl2.changedInstance(function03) ? 8388608 : 4194304);
        }
        int i19 = 256 & i2;
        if (i19 != 0) {
            i9 = i8 | 100663296;
            contentScope2 = contentScope;
        } else {
            contentScope2 = contentScope;
            i9 = i8 | (composerImpl2.changed(contentScope2) ? 67108864 : 33554432);
        }
        int i20 = i9;
        if ((i20 & 38347923) == 38347922 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            function06 = function0;
            widgetConfigurationController4 = widgetConfigurationController2;
            function07 = function03;
        } else {
            Modifier modifier3 = i12 != 0 ? Modifier.Companion : modifier2;
            RemoteViews.InteractionHandler interactionHandler3 = i14 != 0 ? null : interactionHandler2;
            SystemUIDialogFactory systemUIDialogFactory4 = i15 != 0 ? null : systemUIDialogFactory2;
            if (i16 != 0) {
                widgetConfigurationController2 = null;
            }
            Function0 function08 = i17 != 0 ? null : function0;
            Function0 function09 = i18 != 0 ? null : function03;
            ContentScope contentScope4 = i19 != 0 ? null : contentScope2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHub (CommunalHub.kt:226)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.getCommunalContent(), EmptyList.INSTANCE, composerImpl2, 48);
            composerImpl2.startReplaceGroup(1463664943);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj4 = Composer.Companion.Empty;
            if (objRememberedValue4 == obj4) {
                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            final MutableState mutableState7 = (MutableState) objRememberedValue4;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1463667087);
            if (objM == obj4) {
                objM = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(objM);
            }
            final MutableState mutableState8 = (MutableState) objM;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1463669679);
            if (objM2 == obj4) {
                objM2 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(objM2);
            }
            MutableState mutableState9 = (MutableState) objM2;
            Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1463671830);
            if (objM3 == obj4) {
                Offset.Companion.getClass();
                objM3 = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl(0L));
                composerImpl2.updateRememberedValue(objM3);
            }
            MutableState mutableState10 = (MutableState) objM3;
            composerImpl2.end(false);
            CommunalInteractor communalInteractor = baseCommunalViewModel.communalInteractor;
            final LazyGridState lazyGridStateRememberLazyGridState = LazyGridStateKt.rememberLazyGridState(communalInteractor._firstVisibleItemIndex, communalInteractor._firstVisibleItemOffset, composerImpl2, 0);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(1463677817);
            boolean zChangedInstance4 = composerImpl2.changedInstance(baseCommunalViewModel);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (zChangedInstance4 || objRememberedValue5 == obj4) {
                objRememberedValue5 = new CommunalHubKt$CommunalHub$1$1(baseCommunalViewModel, null);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue5);
            List list = (List) mutableStateCollectAsStateWithLifecycle.getValue();
            int i21 = (i20 << 3) & 896;
            composerImpl2.startReplaceGroup(-1560096318);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.rememberContentListState (ContentListState.kt:36)");
            }
            composerImpl2.startReplaceGroup(1921316198);
            boolean zChanged = composerImpl2.changed(list);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue6 == obj4) {
                objRememberedValue6 = new ContentListState(list, new Function3() { // from class: com.android.systemui.communal.ui.compose.ContentListStateKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj5, Object obj6, Object obj7) {
                        Integer num = (Integer) obj7;
                        num.intValue();
                        baseCommunalViewModel.onAddWidget((ComponentName) obj5, (UserHandle) obj6, num, widgetConfigurationController2);
                        return Unit.INSTANCE;
                    }
                }, new ContentListStateKt$rememberContentListState$1$2(baseCommunalViewModel), new ContentListStateKt$rememberContentListState$1$3(baseCommunalViewModel), new ContentListStateKt$rememberContentListState$1$4(baseCommunalViewModel));
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            final ContentListState contentListState2 = (ContentListState) objRememberedValue6;
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.getReorderingWidgets(), composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.selectedKey, composerImpl2);
            composerImpl2.startReplaceGroup(1463690632);
            Object objRememberedValue7 = composerImpl2.rememberedValue();
            if (objRememberedValue7 == obj4) {
                mutableState = mutableState10;
                z = false;
                objRememberedValue7 = SnapshotStateKt.derivedStateOf(new CommunalHubKt$$ExternalSyntheticLambda0(0, mutableStateCollectAsStateWithLifecycle3, mutableStateCollectAsStateWithLifecycle2));
                composerImpl2.updateRememberedValue(objRememberedValue7);
            } else {
                mutableState = mutableState10;
                z = false;
            }
            State state2 = (State) objRememberedValue7;
            composerImpl2.end(z);
            Flow flowIsEmptyState = baseCommunalViewModel.isEmptyState();
            Boolean bool4 = Boolean.FALSE;
            final MutableState mutableStateCollectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(flowIsEmptyState, bool4, composerImpl2, 48);
            MutableState mutableStateCollectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isCommunalContentVisible(), Boolean.valueOf(!baseCommunalViewModel.isEditMode()), composerImpl2, 0);
            boolean zIsEditMode = baseCommunalViewModel.isEditMode();
            IntSize intSize = (IntSize) mutableState8.getValue();
            composerImpl2.startReplaceGroup(189392001);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.gridContentPadding (CommunalHub.kt:1874)");
            }
            composerImpl2.startReplaceGroup(-825535728);
            if (!zIsEditMode || intSize == null) {
                bool = bool4;
                mutableState2 = mutableStateCollectAsStateWithLifecycle5;
                state = state2;
                composerImpl2.startReplaceGroup(178610215);
                Dimensions.Companion.getClass();
                paddingValuesImplM124PaddingValuesa9UjIt4$default = PaddingKt.m124PaddingValuesa9UjIt4$default(Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), getHubDimensions(composerImpl2).m1083getGridTopSpacingD9Ej5fM(), Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), 0.0f, 8);
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, false)) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
            } else {
                composerImpl2.end(false);
                Context context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
                bool = bool4;
                Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                WindowMetricsCalculator.Companion.getClass();
                mutableState2 = mutableStateCollectAsStateWithLifecycle5;
                float fMo55toDpu2uoSUM = density.mo55toDpu2uoSUM(WindowMetricsCalculator.Companion.getOrCreate().computeCurrentWindowMetrics(context)._bounds.toRect().height());
                Dimensions.Companion companion = Dimensions.Companion;
                companion.getClass();
                DensityUtils.Companion companion2 = DensityUtils.Companion;
                companion2.getClass();
                state = state2;
                float fMo55toDpu2uoSUM2 = density.mo55toDpu2uoSUM((int) (intSize.packedValue & 4294967295L)) + DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(27);
                Dp.Companion companion3 = Dp.Companion;
                composerImpl2.startReplaceGroup(179417548);
                Dimensions hubDimensions = getHubDimensions(composerImpl2);
                companion.getClass();
                companion2.getClass();
                float fM1083getGridTopSpacingD9Ej5fM = getHubDimensions(composerImpl2).m1083getGridTopSpacingD9Ej5fM() + ((fMo55toDpu2uoSUM - fMo55toDpu2uoSUM2) - (hubDimensions.m1083getGridTopSpacingD9Ej5fM() + DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530)));
                float f = 2;
                Dp dpM837boximpl = Dp.m837boximpl(fM1083getGridTopSpacingD9Ej5fM / f);
                Dp dpM837boximpl2 = Dp.m837boximpl(Dimensions.Companion.m1084getItemSpacingD9Ej5fM() / f);
                if (dpM837boximpl.compareTo(dpM837boximpl2) < 0) {
                    dpM837boximpl = dpM837boximpl2;
                }
                float f2 = dpM837boximpl.value;
                paddingValuesImplM124PaddingValuesa9UjIt4$default = PaddingKt.m123PaddingValuesa9UjIt4(Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), fMo55toDpu2uoSUM2 + f2, Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), f2);
                composerImpl2.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
            }
            PaddingValuesImpl paddingValuesImpl2 = paddingValuesImplM124PaddingValuesa9UjIt4$default;
            ObserveScrollEffect(lazyGridStateRememberLazyGridState, baseCommunalViewModel, composerImpl2, i20 & 112);
            Context context2 = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
            WindowMetricsCalculator.Companion.getClass();
            final int iWidth = WindowMetricsCalculator.Companion.getOrCreate().computeCurrentWindowMetrics(context2)._bounds.toRect().width();
            final LayoutDirection layoutDirection2 = (LayoutDirection) composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection);
            if (baseCommunalViewModel.isEditMode()) {
                composerImpl2.startReplaceGroup(-1869476990);
                ObserveNewWidgetAddedEffect((List) mutableStateCollectAsStateWithLifecycle.getValue(), lazyGridStateRememberLazyGridState, baseCommunalViewModel, composerImpl2, i21);
                composerImpl2.end(false);
            } else {
                composerImpl2.startReplaceGroup(-1869389880);
                ScrollOnUpdatedLiveContentEffect((List) mutableStateCollectAsStateWithLifecycle.getValue(), lazyGridStateRememberLazyGridState, composerImpl2, 0);
                composerImpl2.end(false);
            }
            composerImpl2.startReplaceGroup(1463721563);
            Object objRememberedValue8 = composerImpl2.rememberedValue();
            if (objRememberedValue8 == obj4) {
                objRememberedValue8 = new NestedScrollConnection() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$nestedScrollConnection$1$1
                    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
                    /* renamed from: onPreScroll-OzD1aCk */
                    public final long mo176onPreScrollOzD1aCk(int i22, long j) {
                        StateFlowImpl stateFlowImpl = baseCommunalViewModel._isNestedScrolling;
                        if (!((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                            stateFlowImpl.updateState(null, Boolean.TRUE);
                        }
                        super.mo176onPreScrollOzD1aCk(i22, j);
                        return 0L;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue8);
            }
            CommunalHubKt$CommunalHub$nestedScrollConnection$1$1 communalHubKt$CommunalHub$nestedScrollConnection$1$1 = (CommunalHubKt$CommunalHub$nestedScrollConnection$1$1) objRememberedValue8;
            composerImpl2.end(false);
            String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_content_description_for_communal_hub, composerImpl2);
            composerImpl2.startReplaceGroup(1463737600);
            boolean zChanged2 = composerImpl2.changed(strStringResource);
            Object objRememberedValue9 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue9 == obj4) {
                z2 = false;
                objRememberedValue9 = new CommunalHubKt$$ExternalSyntheticLambda1(strStringResource, 0);
                composerImpl2.updateRememberedValue(objRememberedValue9);
            } else {
                z2 = false;
            }
            composerImpl2.end(z2);
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(TestTagKt.testTag(SemanticsModifierKt.semantics(modifier3, z2, (Function1) objRememberedValue9), "communal_hub"), 1.0f);
            if (baseCommunalViewModel.isEditMode()) {
                Modifier.Companion companion4 = Modifier.Companion;
                Object[] objArr = {layoutDirection2, lazyGridStateRememberLazyGridState, Offset.m395boximpl(access$CommunalHub$lambda$11(mutableState)), contentListState2};
                bool2 = bool;
                obj = obj4;
                paddingValuesImpl = paddingValuesImpl2;
                final MutableState mutableState11 = mutableState;
                widgetConfigurationController3 = widgetConfigurationController2;
                contentScope3 = contentScope4;
                PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        Object objObserveTaps$default = PointerInputScopeExtKt.observeTaps$default(pointerInputScope, new Function1(iWidth, lazyGridStateRememberLazyGridState, baseCommunalViewModel, mutableStateCollectAsStateWithLifecycle3, mutableState11, contentListState2) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$1.1
                            public final /* synthetic */ ContentListState $contentListState;
                            public final /* synthetic */ MutableState $contentOffset$delegate;
                            public final /* synthetic */ LazyGridState $gridState;
                            public final /* synthetic */ int $screenWidth;
                            public final /* synthetic */ BaseCommunalViewModel $viewModel;

                            {
                                this.$contentOffset$delegate = mutableState;
                                this.$contentListState = contentListState;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj5) {
                                long j = ((Offset) obj5).packedValue;
                                float fIntBitsToFloat = this.$layoutDirection == LayoutDirection.Rtl ? this.$screenWidth - Float.intBitsToFloat((int) (j >> 32)) : Float.intBitsToFloat((int) (j >> 32));
                                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
                                long jFloatToRawIntBits = Float.floatToRawIntBits(fIntBitsToFloat);
                                long jFloatToRawIntBits2 = Float.floatToRawIntBits(fIntBitsToFloat2);
                                Offset.Companion companion5 = Offset.Companion;
                                LazyGridItemInfo lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA = LazyGridStateExtKt.m1090firstItemAtOffsetUv8p0NA(((LazyGridMeasureResult) this.$gridState.getLayoutInfo()).visibleItemsInfo, Offset.m402minusMKHz9U((jFloatToRawIntBits << 32) | (jFloatToRawIntBits2 & 4294967295L), CommunalHubKt.access$CommunalHub$lambda$11(this.$contentOffset$delegate)));
                                Integer numValueOf = lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA != null ? Integer.valueOf(((LazyGridMeasuredItem) lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA).index) : null;
                                this.$viewModel.setSelectedKey(numValueOf != null ? CommunalHubKt.access$keyAtIndexIfEditable(numValueOf.intValue(), this.$contentListState.list) : null);
                                return Unit.INSTANCE;
                            }
                        }, continuation, 3);
                        return objObserveTaps$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objObserveTaps$default : Unit.INSTANCE;
                    }
                };
                mutableState4 = mutableStateCollectAsStateWithLifecycle3;
                mutableState3 = mutableState11;
                contentListState = contentListState2;
                lazyGridStateRememberLazyGridState = lazyGridStateRememberLazyGridState;
                layoutDirection = layoutDirection2;
                baseCommunalViewModel2 = baseCommunalViewModel;
                i10 = iWidth;
                modifierThen = modifierFillMaxSize.then(SuspendingPointerInputFilterKt.pointerInput((Modifier) companion4, objArr, pointerInputEventHandler));
            } else {
                mutableState3 = mutableState;
                mutableState4 = mutableStateCollectAsStateWithLifecycle3;
                bool2 = bool;
                i10 = iWidth;
                obj = obj4;
                contentListState = contentListState2;
                paddingValuesImpl = paddingValuesImpl2;
                widgetConfigurationController3 = widgetConfigurationController2;
                contentScope3 = contentScope4;
                layoutDirection = layoutDirection2;
                modifierThen = modifierFillMaxSize;
                baseCommunalViewModel2 = baseCommunalViewModel;
            }
            if (baseCommunalViewModel2.isEditMode()) {
                th = null;
            } else {
                th = null;
                modifierThen = modifierThen.then(SuspendingPointerInputFilterKt.pointerInput(NestedScrollModifierKt.nestedScroll(Modifier.Companion, communalHubKt$CommunalHub$nestedScrollConnection$1$1, null), baseCommunalViewModel2, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1

                    /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                        final /* synthetic */ BaseCommunalViewModel $viewModel;
                        private /* synthetic */ Object L$0;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(BaseCommunalViewModel baseCommunalViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.$viewModel = baseCommunalViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, continuation);
                            anonymousClass1.L$0 = obj;
                            return anonymousClass1;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:12:0x0035, code lost:
                        
                            if (r10 != r0) goto L14;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
                        
                            if (r10 == r0) goto L22;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:22:0x0065, code lost:
                        
                            return r0;
                         */
                        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0063 -> B:23:0x0066). Please report as a decompilation issue!!! */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invokeSuspend(Object obj) {
                            AwaitPointerEventScope awaitPointerEventScope;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                this.L$0 = awaitPointerEventScope;
                                this.label = 1;
                                obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                            } else if (i == 1) {
                                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                ResultKt.throwOnFailure(obj);
                                this.$viewModel.onResetTouchState();
                                if (((PointerInputChange) obj).isConsumed()) {
                                    StateFlowImpl stateFlowImpl = this.$viewModel._isTouchConsumed;
                                    if (!((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                        stateFlowImpl.updateState(null, Boolean.TRUE);
                                    }
                                }
                                this.L$0 = awaitPointerEventScope;
                                this.label = 2;
                                obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
                            } else {
                                if (i != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                ResultKt.throwOnFailure(obj);
                                PointerEvent pointerEvent = (PointerEvent) obj;
                                Iterator it = pointerEvent.changes.iterator();
                                while (it.hasNext()) {
                                    if (((PointerInputChange) it.next()).isConsumed()) {
                                        StateFlowImpl stateFlowImpl2 = this.$viewModel._isTouchConsumed;
                                        if (!((Boolean) stateFlowImpl2.getValue()).booleanValue()) {
                                            stateFlowImpl2.updateState(null, Boolean.TRUE);
                                        }
                                    }
                                }
                                List list = pointerEvent.changes;
                                int size = list.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    PointerInputChange pointerInputChange = (PointerInputChange) list.get(i2);
                                    if (!PointerEventKt.changedToUp(pointerInputChange) && !PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                                        break;
                                    }
                                }
                                this.$viewModel.onResetTouchState();
                                this.L$0 = awaitPointerEventScope;
                                this.label = 1;
                                obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                            }
                        }
                    }

                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        Object objAwaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new AnonymousClass1(baseCommunalViewModel2, null), continuation);
                        return objAwaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitPointerEventScope : Unit.INSTANCE;
                    }
                }));
            }
            if (baseCommunalViewModel2.isEditMode() || ((Boolean) mutableStateCollectAsStateWithLifecycle4.getValue()).booleanValue()) {
                mutableState5 = mutableStateCollectAsStateWithLifecycle;
                mutableState6 = mutableState9;
            } else {
                mutableState5 = mutableStateCollectAsStateWithLifecycle;
                mutableState6 = mutableState9;
                modifierThen = modifierThen.then(SuspendingPointerInputFilterKt.pointerInput((Modifier) Modifier.Companion, new Object[]{lazyGridStateRememberLazyGridState, Offset.m395boximpl(access$CommunalHub$lambda$11(mutableState3)), (List) mutableStateCollectAsStateWithLifecycle.getValue(), (LayoutCoordinates) mutableState9.getValue()}, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$5$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final int i22 = i10;
                        final MutableState mutableState12 = mutableState3;
                        final BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel2;
                        final MutableState mutableState13 = mutableState6;
                        final LayoutDirection layoutDirection3 = layoutDirection;
                        final LazyGridState lazyGridState = lazyGridStateRememberLazyGridState;
                        final State state3 = mutableState5;
                        Object objDetectLongPressGesture$default = PointerInputScopeExtKt.detectLongPressGesture$default(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$5$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj5) {
                                Offset offsetM395boximpl;
                                LazyGridItemInfo lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA;
                                long j = ((Offset) obj5).packedValue;
                                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) mutableState13.getValue();
                                if (layoutCoordinates != null) {
                                    Offset.Companion companion5 = Offset.Companion;
                                    offsetM395boximpl = Offset.m395boximpl(Offset.m402minusMKHz9U(Offset.m402minusMKHz9U((Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(layoutDirection3 == LayoutDirection.Rtl ? i22 - Float.intBitsToFloat((int) (j >> 32)) : Float.intBitsToFloat((int) (j >> 32))) << 32), LayoutCoordinatesKt.positionInWindow(layoutCoordinates)), CommunalHubKt.access$CommunalHub$lambda$11(mutableState12)));
                                } else {
                                    offsetM395boximpl = null;
                                }
                                Integer numValueOf = (offsetM395boximpl == null || (lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA = LazyGridStateExtKt.m1090firstItemAtOffsetUv8p0NA(((LazyGridMeasureResult) lazyGridState.getLayoutInfo()).visibleItemsInfo, offsetM395boximpl.packedValue)) == null) ? null : Integer.valueOf(((LazyGridMeasuredItem) lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA).index);
                                String strAccess$keyAtIndexIfEditable = numValueOf != null ? CommunalHubKt.access$keyAtIndexIfEditable(numValueOf.intValue(), (List) state3.getValue()) : null;
                                if (strAccess$keyAtIndexIfEditable != null) {
                                    BaseCommunalViewModel baseCommunalViewModel4 = baseCommunalViewModel3;
                                    baseCommunalViewModel4.onLongClick();
                                    baseCommunalViewModel4.setSelectedKey(strAccess$keyAtIndexIfEditable);
                                }
                                return Unit.INSTANCE;
                            }
                        }, continuation);
                        return objDetectLongPressGesture$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectLongPressGesture$default : Unit.INSTANCE;
                    }
                }));
            }
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierThen);
            ComposeUiNode.Companion.getClass();
            Function0 function010 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                Throwable th2 = th;
                ComposablesKt.invalidApplier();
                throw th2;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function010);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            final BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel;
            final ContentScope contentScope5 = contentScope3;
            Modifier modifier4 = modifier3;
            final RemoteViews.InteractionHandler interactionHandler4 = interactionHandler3;
            final WidgetConfigurationController widgetConfigurationController5 = widgetConfigurationController3;
            final ContentListState contentListState3 = contentListState;
            final State state3 = state;
            final MutableState mutableState12 = mutableState6;
            final MutableState mutableState13 = mutableState3;
            final MutableState mutableState14 = mutableState5;
            final MutableState mutableState15 = mutableState4;
            final PaddingValuesImpl paddingValuesImpl3 = paddingValuesImpl;
            final LazyGridState lazyGridState = lazyGridStateRememberLazyGridState;
            final int i22 = i10;
            final MutableState mutableState16 = mutableState2;
            Function2 function22 = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$1
                /* JADX WARN: Removed duplicated region for block: B:20:0x00b2  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00ed  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj5, Object obj6) {
                    Composer composer2 = (Composer) obj5;
                    if ((((Number) obj6).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHub.<anonymous>.<anonymous> (CommunalHub.kt:391)");
                            }
                            BaseCommunalViewModel baseCommunalViewModel4 = baseCommunalViewModel3;
                            if (baseCommunalViewModel4.isEditMode() || !((Boolean) mutableStateCollectAsStateWithLifecycle4.getValue()).booleanValue()) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-258067114);
                                Density density2 = (Density) composerImpl4.consume(CompositionLocalsKt.LocalDensity);
                                Dimensions.Companion.getClass();
                                final int iMo58toPx0680j_4 = (int) density2.mo58toPx0680j_4(Dimensions.SlideOffsetY);
                                boolean zBooleanValue = ((Boolean) mutableState16.getValue()).booleanValue();
                                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
                                EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(new TweenSpec(83, 83, easingKt$$ExternalSyntheticLambda0), 2);
                                Easings.INSTANCE.getClass();
                                Easings$fromInterpolator$1 easings$fromInterpolator$1 = Easings.Emphasized;
                                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2);
                                composerImpl4.startReplaceGroup(1792808160);
                                boolean zChanged3 = composerImpl4.changed(iMo58toPx0680j_4);
                                Object objRememberedValue10 = composerImpl4.rememberedValue();
                                Composer.Companion companion5 = Composer.Companion;
                                if (!zChanged3) {
                                    companion5.getClass();
                                    if (objRememberedValue10 == Composer.Companion.Empty) {
                                        final int i23 = 0;
                                        objRememberedValue10 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj7) {
                                                int i24 = i23;
                                                ((Integer) obj7).intValue();
                                                switch (i24) {
                                                }
                                                return Integer.valueOf(-iMo58toPx0680j_4);
                                            }
                                        };
                                        composerImpl4.updateRememberedValue(objRememberedValue10);
                                    }
                                    composerImpl4.end(false);
                                    EnterTransition enterTransitionPlus = enterTransitionFadeIn$default.plus(EnterExitTransitionKt.slideInVertically((Function1) objRememberedValue10, tweenSpecTween$default));
                                    ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda0, 2), 2);
                                    TweenSpec tweenSpecTween$default2 = AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2);
                                    composerImpl4.startReplaceGroup(1792821952);
                                    boolean zChanged4 = composerImpl4.changed(iMo58toPx0680j_4);
                                    Object objRememberedValue11 = composerImpl4.rememberedValue();
                                    if (!zChanged4) {
                                        companion5.getClass();
                                        if (objRememberedValue11 == Composer.Companion.Empty) {
                                            final int i24 = 1;
                                            objRememberedValue11 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$1$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj7) {
                                                    int i242 = i24;
                                                    ((Integer) obj7).intValue();
                                                    switch (i242) {
                                                    }
                                                    return Integer.valueOf(-iMo58toPx0680j_4);
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(objRememberedValue11);
                                        }
                                        composerImpl4.end(false);
                                        ExitTransition exitTransitionPlus = exitTransitionFadeOut$default.plus(EnterExitTransitionKt.slideOutVertically(tweenSpecTween$default2, (Function1) objRememberedValue11));
                                        Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                                        final RemoteViews.InteractionHandler interactionHandler5 = interactionHandler4;
                                        final MutableState mutableState17 = mutableState7;
                                        final State state4 = state3;
                                        final BaseCommunalViewModel baseCommunalViewModel5 = baseCommunalViewModel3;
                                        final PaddingValues paddingValues = paddingValuesImpl3;
                                        final State state5 = mutableState15;
                                        final int i25 = i22;
                                        final LazyGridState lazyGridState2 = lazyGridState;
                                        final ContentListState contentListState4 = contentListState3;
                                        final WidgetConfigurator widgetConfigurator = widgetConfigurationController5;
                                        final CommunalAppWidgetSection communalAppWidgetSection2 = communalAppWidgetSection;
                                        final ContentScope contentScope6 = contentScope5;
                                        final State state6 = mutableState14;
                                        final MutableState mutableState18 = mutableState13;
                                        final MutableState mutableState19 = mutableState12;
                                        AnimatedVisibilityKt.AnimatedVisibility(zBooleanValue, modifierFillMaxSize2, enterTransitionPlus, exitTransitionPlus, null, ComposableLambdaKt.rememberComposableLambda(1662366255, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$1.3
                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj7, Object obj8, Object obj9) {
                                                Composer composer3 = (Composer) obj8;
                                                ((Number) obj9).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHub.<anonymous>.<anonymous>.<anonymous> (CommunalHub.kt:417)");
                                                }
                                                RemoteViews.InteractionHandler interactionHandler6 = interactionHandler5;
                                                Modifier.Companion companion6 = Modifier.Companion;
                                                Alignment.Companion.getClass();
                                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl5.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer3, companion6);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function011 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl5.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl5.startReusableNode();
                                                if (composerImpl5.inserting) {
                                                    composerImpl5.createNode(function011);
                                                } else {
                                                    composerImpl5.useNode();
                                                }
                                                Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                                                Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl5, currentCompositeKeyHash2, function23);
                                                }
                                                Updater.m337setimpl(composer3, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                                                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                                                List list2 = (List) state6.getValue();
                                                final MutableState mutableState20 = mutableState18;
                                                long jAccess$CommunalHub$lambda$11 = CommunalHubKt.access$CommunalHub$lambda$11(mutableState20);
                                                composerImpl5.startReplaceGroup(2140383266);
                                                Object objRememberedValue12 = composerImpl5.rememberedValue();
                                                Composer.Companion.getClass();
                                                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                                final MutableState mutableState21 = mutableState19;
                                                if (objRememberedValue12 == composer$Companion$Empty$1) {
                                                    objRememberedValue12 = new CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1(mutableState21, 1);
                                                    composerImpl5.updateRememberedValue(objRememberedValue12);
                                                }
                                                Function1 function1 = (Function1) objRememberedValue12;
                                                Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl5, false, 2140385600);
                                                if (objM4 == composer$Companion$Empty$1) {
                                                    objM4 = new CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1(mutableState20, 2);
                                                    composerImpl5.updateRememberedValue(objM4);
                                                }
                                                Function1 function12 = (Function1) objM4;
                                                Object objM5 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl5, false, 2140389118);
                                                if (objM5 == composer$Companion$Empty$1) {
                                                    final MutableState mutableState22 = mutableState17;
                                                    final State state7 = state4;
                                                    objM5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$1$3$$ExternalSyntheticLambda2
                                                        /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object mo781invoke(Object obj10) {
                                                            boolean z4;
                                                            Rect rectBoundsInWindow;
                                                            IntRect intRect = (IntRect) obj10;
                                                            LayoutCoordinates layoutCoordinates = (LayoutCoordinates) mutableState21.getValue();
                                                            Offset offsetM395boximpl = null;
                                                            Offset offsetM395boximpl2 = layoutCoordinates != null ? Offset.m395boximpl(LayoutCoordinatesKt.positionInWindow(layoutCoordinates)) : null;
                                                            LayoutCoordinates layoutCoordinates2 = (LayoutCoordinates) mutableState22.getValue();
                                                            if (layoutCoordinates2 != null && (rectBoundsInWindow = LayoutCoordinatesKt.boundsInWindow(layoutCoordinates2)) != null) {
                                                                offsetM395boximpl = Offset.m395boximpl(rectBoundsInWindow.m409getCenterF1C5BW0());
                                                            }
                                                            if (!((Boolean) state7.getValue()).booleanValue() || offsetM395boximpl2 == null || offsetM395boximpl == null) {
                                                                z4 = false;
                                                            } else {
                                                                long jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(Offset.m403plusMKHz9U(offsetM395boximpl2.packedValue, CommunalHubKt.access$CommunalHub$lambda$11(mutableState20)));
                                                                intRect.getClass();
                                                                IntOffset.Companion companion7 = IntOffset.Companion;
                                                                int i26 = (int) (jM856roundk4lQ0M >> 32);
                                                                int i27 = (int) (jM856roundk4lQ0M & 4294967295L);
                                                                if (new IntRect(intRect.left + i26, intRect.top + i27, intRect.right + i26, intRect.bottom + i27).m857containsgyyYBs(IntOffsetKt.m856roundk4lQ0M(offsetM395boximpl.packedValue))) {
                                                                    z4 = true;
                                                                }
                                                            }
                                                            return Boolean.valueOf(z4);
                                                        }
                                                    };
                                                    composerImpl5.updateRememberedValue(objM5);
                                                }
                                                composerImpl5.end(false);
                                                CommunalHubKt.m1079CommunalHubLazyGridMGE6UKE(boxScopeInstance2, list2, baseCommunalViewModel5, paddingValues, state5, i25, jAccess$CommunalHub$lambda$11, lazyGridState2, contentListState4, function1, function12, (Function1) objM5, widgetConfigurator, interactionHandler6, communalAppWidgetSection2, contentScope6, composer3, 805306374);
                                                composerImpl5.end(true);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl4), composerImpl4, 196656, 16);
                                        composerImpl4.end(false);
                                    }
                                }
                            } else {
                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                composerImpl5.startReplaceGroup(-258272210);
                                CommunalHubKt.EmptyStateCta(paddingValuesImpl3, baseCommunalViewModel4, composerImpl5, 0);
                                composerImpl5.end(false);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            composerImpl2 = composerImpl2;
            AccessibilityContainer(baseCommunalViewModel3, ComposableLambdaKt.rememberComposableLambda(-1009457877, function22, composerImpl2), composerImpl2, ((i20 >> 3) & 14) | 48);
            composerImpl2.startReplaceGroup(-744258033);
            if (function08 == null || function09 == null) {
                function04 = function08;
                function05 = function09;
                i11 = 48;
                z3 = false;
            } else {
                boolean z4 = baseCommunalViewModel3.isEditMode() && ((Boolean) mutableState16.getValue()).booleanValue();
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
                EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 0, easingKt$$ExternalSyntheticLambda0, 2), 2);
                Easings.INSTANCE.getClass();
                Easings$fromInterpolator$1 easings$fromInterpolator$1 = Easings.Emphasized;
                EnterTransition enterTransitionPlus = enterTransitionFadeIn$default.plus(EnterExitTransitionKt.slideInVertically$default(AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2), null, 2));
                ExitTransition exitTransitionPlus = EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda0, 2), 2).plus(EnterExitTransitionKt.slideOutVertically(AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2), EnterExitTransitionKt.C06781.INSTANCE));
                function04 = function08;
                function05 = function09;
                Function3 function3 = new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$2
                    /* JADX WARN: Removed duplicated region for block: B:9:0x004c  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj5, Object obj6, Object obj7) {
                        Composer composer2 = (Composer) obj6;
                        ((Number) obj7).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHub.<anonymous>.<anonymous> (CommunalHub.kt:467)");
                        }
                        boolean zBooleanValue = ((Boolean) state3.getValue()).booleanValue();
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        composerImpl3.startReplaceGroup(1792924868);
                        State state4 = mutableState15;
                        boolean zChanged3 = composerImpl3.changed(state4);
                        ContentListState contentListState4 = contentListState3;
                        boolean zChangedInstance5 = zChanged3 | composerImpl3.changedInstance(contentListState4);
                        BaseCommunalViewModel baseCommunalViewModel4 = baseCommunalViewModel3;
                        boolean zChangedInstance6 = zChangedInstance5 | composerImpl3.changedInstance(baseCommunalViewModel4);
                        Object objRememberedValue10 = composerImpl3.rememberedValue();
                        Composer.Companion companion5 = Composer.Companion;
                        if (!zChangedInstance6) {
                            companion5.getClass();
                            if (objRememberedValue10 == Composer.Companion.Empty) {
                                objRememberedValue10 = new CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0(state4, contentListState4, 2, baseCommunalViewModel4);
                                composerImpl3.updateRememberedValue(objRememberedValue10);
                            }
                        }
                        Function0 function011 = (Function0) objRememberedValue10;
                        composerImpl3.end(false);
                        composerImpl3.startReplaceGroup(1792916448);
                        Object objRememberedValue11 = composerImpl3.rememberedValue();
                        companion5.getClass();
                        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue11 == composer$Companion$Empty$1) {
                            objRememberedValue11 = new CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1(mutableState8, 0);
                            composerImpl3.updateRememberedValue(objRememberedValue11);
                        }
                        Function1 function1 = (Function1) objRememberedValue11;
                        Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, 1792918732);
                        if (objM4 == composer$Companion$Empty$1) {
                            objM4 = new CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1(mutableState7, 3);
                            composerImpl3.updateRememberedValue(objM4);
                        }
                        composerImpl3.end(false);
                        CommunalHubKt.Toolbar(zBooleanValue, function011, function1, (Function1) objM4, function04, function05, composerImpl3, 3456);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                };
                baseCommunalViewModel3 = baseCommunalViewModel3;
                ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(83634137, function3, composerImpl2);
                z3 = false;
                i11 = 48;
                AnimatedVisibilityKt.AnimatedVisibility(z4, null, enterTransitionPlus, exitTransitionPlus, null, composableLambdaImplRememberComposableLambda, composerImpl2, 196608, 18);
                composerImpl2 = composerImpl2;
            }
            composerImpl2.end(z3);
            composerImpl2.startReplaceGroup(-744205738);
            if (!(baseCommunalViewModel3 instanceof CommunalViewModel) || systemUIDialogFactory4 == null) {
                systemUIDialogFactory3 = systemUIDialogFactory4;
                obj2 = obj;
                bool3 = bool2;
            } else {
                CommunalViewModel communalViewModel = (CommunalViewModel) baseCommunalViewModel3;
                bool3 = bool2;
                MutableState mutableStateCollectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isEnableWidgetDialogShowing, bool3, composerImpl2, i11);
                MutableState mutableStateCollectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isEnableWorkProfileDialogShowing, bool3, composerImpl2, i11);
                boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle6.getValue()).booleanValue();
                String strStringResource2 = StringResources_androidKt.stringResource(R.string.dialog_title_to_allow_any_widget, composerImpl2);
                String strStringResource3 = StringResources_androidKt.stringResource(R.string.button_text_to_open_settings, composerImpl2);
                composerImpl2.startReplaceGroup(-744184205);
                boolean zChangedInstance5 = composerImpl2.changedInstance(communalViewModel);
                Object objRememberedValue10 = composerImpl2.rememberedValue();
                if (zChangedInstance5) {
                    obj3 = obj;
                } else {
                    obj3 = obj;
                    if (objRememberedValue10 == obj3) {
                    }
                    composerImpl2.end(z3);
                    Function0 function011 = (Function0) ((KFunction) objRememberedValue10);
                    composerImpl2.startReplaceGroup(-744182062);
                    zChangedInstance = composerImpl2.changedInstance(communalViewModel);
                    objRememberedValue = composerImpl2.rememberedValue();
                    if (!zChangedInstance || objRememberedValue == obj3) {
                        objRememberedValue = new CommunalHubKt$CommunalHub$6$4$1(communalViewModel);
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl2.end(z3);
                    int i23 = (i20 >> 9) & 112;
                    composerImpl = composerImpl2;
                    obj2 = obj3;
                    SystemUIDialogFactory systemUIDialogFactory5 = systemUIDialogFactory4;
                    EnableWidgetDialogKt.EnableWidgetDialog(zBooleanValue, systemUIDialogFactory5, strStringResource2, strStringResource3, function011, (Function0) ((KFunction) objRememberedValue), composerImpl, i23);
                    boolean zBooleanValue2 = ((Boolean) mutableStateCollectAsStateWithLifecycle7.getValue()).booleanValue();
                    String strStringResource4 = StringResources_androidKt.stringResource(R.string.work_mode_off_title, composerImpl);
                    String strStringResource5 = StringResources_androidKt.stringResource(R.string.work_mode_turn_on, composerImpl);
                    composerImpl.startReplaceGroup(-744169192);
                    zChangedInstance2 = composerImpl.changedInstance(communalViewModel);
                    objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance2 || objRememberedValue2 == obj2) {
                        objRememberedValue2 = new CommunalHubKt$CommunalHub$6$5$1(communalViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(z3);
                    Function0 function012 = (Function0) ((KFunction) objRememberedValue2);
                    composerImpl.startReplaceGroup(-744166889);
                    zChangedInstance3 = composerImpl.changedInstance(communalViewModel);
                    objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance3 || objRememberedValue3 == obj2) {
                        objRememberedValue3 = new CommunalHubKt$CommunalHub$6$6$1(communalViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(z3);
                    EnableWidgetDialogKt.EnableWidgetDialog(zBooleanValue2, systemUIDialogFactory5, strStringResource4, strStringResource5, function012, (Function0) ((KFunction) objRememberedValue3), composerImpl, i23);
                    systemUIDialogFactory3 = systemUIDialogFactory5;
                    composerImpl2 = composerImpl;
                }
                objRememberedValue10 = new CommunalHubKt$CommunalHub$6$3$1(communalViewModel);
                composerImpl2.updateRememberedValue(objRememberedValue10);
                composerImpl2.end(z3);
                Function0 function0112 = (Function0) ((KFunction) objRememberedValue10);
                composerImpl2.startReplaceGroup(-744182062);
                zChangedInstance = composerImpl2.changedInstance(communalViewModel);
                objRememberedValue = composerImpl2.rememberedValue();
                if (!zChangedInstance) {
                    objRememberedValue = new CommunalHubKt$CommunalHub$6$4$1(communalViewModel);
                    composerImpl2.updateRememberedValue(objRememberedValue);
                    composerImpl2.end(z3);
                    int i232 = (i20 >> 9) & 112;
                    composerImpl = composerImpl2;
                    obj2 = obj3;
                    SystemUIDialogFactory systemUIDialogFactory52 = systemUIDialogFactory4;
                    EnableWidgetDialogKt.EnableWidgetDialog(zBooleanValue, systemUIDialogFactory52, strStringResource2, strStringResource3, function0112, (Function0) ((KFunction) objRememberedValue), composerImpl, i232);
                    boolean zBooleanValue22 = ((Boolean) mutableStateCollectAsStateWithLifecycle7.getValue()).booleanValue();
                    String strStringResource42 = StringResources_androidKt.stringResource(R.string.work_mode_off_title, composerImpl);
                    String strStringResource52 = StringResources_androidKt.stringResource(R.string.work_mode_turn_on, composerImpl);
                    composerImpl.startReplaceGroup(-744169192);
                    zChangedInstance2 = composerImpl.changedInstance(communalViewModel);
                    objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        objRememberedValue2 = new CommunalHubKt$CommunalHub$6$5$1(communalViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                        composerImpl.end(z3);
                        Function0 function0122 = (Function0) ((KFunction) objRememberedValue2);
                        composerImpl.startReplaceGroup(-744166889);
                        zChangedInstance3 = composerImpl.changedInstance(communalViewModel);
                        objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChangedInstance3) {
                            objRememberedValue3 = new CommunalHubKt$CommunalHub$6$6$1(communalViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                            composerImpl.end(z3);
                            EnableWidgetDialogKt.EnableWidgetDialog(zBooleanValue22, systemUIDialogFactory52, strStringResource42, strStringResource52, function0122, (Function0) ((KFunction) objRememberedValue3), composerImpl, i232);
                            systemUIDialogFactory3 = systemUIDialogFactory52;
                            composerImpl2 = composerImpl;
                        }
                    }
                }
            }
            composerImpl2.end(z3);
            composerImpl2.startReplaceGroup(-744163396);
            if (baseCommunalViewModel3 instanceof CommunalEditModeViewModel) {
                CommunalEditModeViewModel communalEditModeViewModel = (CommunalEditModeViewModel) baseCommunalViewModel3;
                if (((Boolean) FlowExtKt.collectAsStateWithLifecycle(communalEditModeViewModel.showDisclaimer, bool3, composerImpl2, 48).getValue()).booleanValue()) {
                    Object objRememberedValue11 = composerImpl2.rememberedValue();
                    if (objRememberedValue11 == obj2) {
                        objRememberedValue11 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                        composerImpl2.updateRememberedValue(objRememberedValue11);
                    }
                    final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue11;
                    final SheetState sheetStateRememberModalBottomSheetState = ModalBottomSheetKt.rememberModalBottomSheetState(composerImpl2);
                    MaterialTheme.INSTANCE.getClass();
                    ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl2);
                    composerImpl2.startReplaceGroup(-744150867);
                    boolean zChangedInstance6 = composerImpl2.changedInstance(communalEditModeViewModel);
                    Object objRememberedValue12 = composerImpl2.rememberedValue();
                    if (zChangedInstance6 || objRememberedValue12 == obj2) {
                        objRememberedValue12 = new CommunalHubKt$CommunalHub$6$7$1(communalEditModeViewModel);
                        composerImpl2.updateRememberedValue(objRememberedValue12);
                    }
                    composerImpl2.end(z3);
                    ComposerImpl composerImpl3 = composerImpl2;
                    ModalBottomSheetKt.m272ModalBottomSheetYbuCTN8((Function0) ((KFunction) objRememberedValue12), null, sheetStateRememberModalBottomSheetState, 0.0f, false, null, colorScheme.surfaceContainer, 0L, 0.0f, 0L, null, null, null, ComposableLambdaKt.rememberComposableLambda(-724131168, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$6$8
                        /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                            Composer composer2 = (Composer) obj6;
                            if ((((Number) obj7).intValue() & 17) == 16) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                if (composerImpl4.getSkipping()) {
                                    composerImpl4.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHub.<anonymous>.<anonymous> (CommunalHub.kt:527)");
                                    }
                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                    composerImpl5.startReplaceGroup(1793007504);
                                    CoroutineScope coroutineScope2 = coroutineScope;
                                    boolean zChangedInstance7 = composerImpl5.changedInstance(coroutineScope2);
                                    SheetState sheetState = sheetStateRememberModalBottomSheetState;
                                    boolean zChanged3 = zChangedInstance7 | composerImpl5.changed(sheetState);
                                    BaseCommunalViewModel baseCommunalViewModel4 = baseCommunalViewModel3;
                                    boolean zChangedInstance8 = zChanged3 | composerImpl5.changedInstance(baseCommunalViewModel4);
                                    Object objRememberedValue13 = composerImpl5.rememberedValue();
                                    if (!zChangedInstance8) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue13 == Composer.Companion.Empty) {
                                            objRememberedValue13 = new CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0(coroutineScope2, sheetState, 0, baseCommunalViewModel4);
                                            composerImpl5.updateRememberedValue(objRememberedValue13);
                                        }
                                        composerImpl5.end(false);
                                        CommunalHubKt.DisclaimerBottomSheetContent((Function0) objRememberedValue13, composerImpl5, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl3, 0, 3078, 7098);
                    composerImpl2 = composerImpl3;
                }
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, z3, true)) {
                ComposerKt.traceEventEnd();
            }
            function06 = function04;
            function07 = function05;
            interactionHandler2 = interactionHandler4;
            modifier2 = modifier4;
            widgetConfigurationController4 = widgetConfigurationController5;
            contentScope2 = contentScope5;
            systemUIDialogFactory2 = systemUIDialogFactory3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Modifier modifier5 = modifier2;
            final Function0 function013 = function06;
            recomposeScopeImplEndRestartGroup.block = new Function2(baseCommunalViewModel, communalAppWidgetSection, interactionHandler2, systemUIDialogFactory2, widgetConfigurationController4, function013, function07, contentScope2, i, i2) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda2
                public final /* synthetic */ BaseCommunalViewModel f$1;
                public final /* synthetic */ int f$10;
                public final /* synthetic */ CommunalAppWidgetSection f$2;
                public final /* synthetic */ RemoteViews.InteractionHandler f$3;
                public final /* synthetic */ SystemUIDialogFactory f$4;
                public final /* synthetic */ WidgetConfigurationController f$5;
                public final /* synthetic */ Function0 f$6;
                public final /* synthetic */ Function0 f$7;
                public final /* synthetic */ ContentScope f$8;

                {
                    this.f$10 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    ((Integer) obj6).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ContentScope contentScope6 = this.f$8;
                    int i24 = this.f$10;
                    CommunalHubKt.CommunalHub(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, contentScope6, (Composer) obj5, iUpdateChangedFlags, i24);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:152:0x040f  */
    /* JADX WARN: Type inference failed for: r7v15, types: [T, com.android.systemui.communal.ui.compose.GridDragDropState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v6, types: [T, androidx.compose.runtime.snapshots.SnapshotStateList] */
    /* renamed from: CommunalHubLazyGrid-MGE6UKE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1079CommunalHubLazyGridMGE6UKE(final BoxScopeInstance boxScopeInstance, final List list, final BaseCommunalViewModel baseCommunalViewModel, final PaddingValues paddingValues, final State state, final int i, final long j, final LazyGridState lazyGridState, final ContentListState contentListState, final Function1 function1, final Function1 function12, final Function1 function13, final WidgetConfigurator widgetConfigurator, final RemoteViews.InteractionHandler interactionHandler, final CommunalAppWidgetSection communalAppWidgetSection, final ContentScope contentScope, Composer composer, final int i2) {
        Modifier.Companion companion;
        boolean z;
        Ref$ObjectRef ref$ObjectRef;
        Ref$ObjectRef ref$ObjectRef2;
        Modifier modifierM131height3ABfNKs;
        ComposerImpl composerImpl;
        ContentListState contentListState2;
        CoroutineScope coroutineScope;
        LazyGridState lazyGridState2;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1276018913);
        int i3 = i2 | (composerImpl2.changedInstance(list) ? 32 : 16) | (composerImpl2.changedInstance(baseCommunalViewModel) ? 256 : 128) | (composerImpl2.changed(paddingValues) ? 2048 : 1024) | (composerImpl2.changed(state) ? 16384 : 8192) | (composerImpl2.changed(i) ? 131072 : 65536) | (composerImpl2.changed(j) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl2.changed(lazyGridState) ? 8388608 : 4194304) | (composerImpl2.changedInstance(contentListState) ? 67108864 : 33554432);
        int i4 = (composerImpl2.changed(widgetConfigurator) ? (char) 256 : (char) 128) | '6' | (composerImpl2.changedInstance(interactionHandler) ? (char) 2048 : (char) 1024) | (composerImpl2.changedInstance(communalAppWidgetSection) ? (char) 16384 : (char) 8192) | (composerImpl2.changed(contentScope) ? (char) 0 : (char) 0);
        if ((i3 & 306783379) == 306783378 && (74899 & i4) == 74898 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHubLazyGrid (CommunalHub.kt:859)");
            }
            Modifier.Companion companion2 = Modifier.Companion;
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            Modifier modifierAlign = boxScopeInstance.align(companion2, biasAlignment);
            composerImpl2.startReplaceGroup(-600948318);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            companion3.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                companion = companion2;
                objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda10(function1, 1);
                composerImpl2.updateRememberedValue(objRememberedValue);
            } else {
                companion = companion2;
            }
            composerImpl2.end(false);
            Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierAlign, (Function1) objRememberedValue);
            Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
            ref$ObjectRef3.element = list;
            Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
            Dimensions.Companion companion4 = Dimensions.Companion;
            companion4.getClass();
            float fM1084getItemSpacingD9Ej5fM = Dimensions.Companion.m1084getItemSpacingD9Ej5fM();
            if (!baseCommunalViewModel.isEditMode()) {
                z = false;
            } else if (baseCommunalViewModel instanceof CommunalEditModeViewModel) {
                composerImpl2.startReplaceGroup(-1449261328);
                ref$ObjectRef3.element = contentListState.list;
                int i5 = i3 >> 21;
                int i6 = i5 & 14;
                int i7 = (i5 & 126) | 384;
                composerImpl2.startReplaceGroup(590879362);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.rememberGridDragDropState (GridDragDropState.kt:69)");
                }
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                companion3.getClass();
                if (objRememberedValue2 == obj) {
                    objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue2;
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                Density density = (Density) composerImpl2.consume(staticProvidableCompositionLocal);
                float f = 60;
                Dp.Companion companion5 = Dp.Companion;
                float fMo58toPx0680j_4 = density.mo58toPx0680j_4(f);
                composerImpl2.startReplaceGroup(2008346738);
                boolean zChanged = composerImpl2.changed(contentListState) | ((((i7 & 14) ^ 6) > 4 && composerImpl2.changed(lazyGridState)) || (i7 & 6) == 4);
                Object objRememberedValue3 = composerImpl2.rememberedValue();
                if (zChanged || objRememberedValue3 == obj) {
                    contentListState2 = contentListState;
                    coroutineScope = coroutineScope2;
                    lazyGridState2 = lazyGridState;
                    GridDragDropState gridDragDropState = new GridDragDropState(lazyGridState2, contentListState2, coroutineScope, fMo58toPx0680j_4, function13);
                    composerImpl2.updateRememberedValue(gridDragDropState);
                    objRememberedValue3 = gridDragDropState;
                } else {
                    contentListState2 = contentListState;
                    coroutineScope = coroutineScope2;
                    lazyGridState2 = lazyGridState;
                }
                ?? r7 = (GridDragDropState) objRememberedValue3;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(2008360019);
                boolean zChanged2 = composerImpl2.changed((Object) r7) | composerImpl2.changedInstance(coroutineScope);
                Object objRememberedValue4 = composerImpl2.rememberedValue();
                if (zChanged2 || objRememberedValue4 == obj) {
                    objRememberedValue4 = new GridDragDropStateKt$rememberGridDragDropState$1$1(r7, coroutineScope, null);
                    composerImpl2.updateRememberedValue(objRememberedValue4);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, (Object) r7, (Function2) objRememberedValue4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                ref$ObjectRef4.element = r7;
                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifierOnGloballyPositioned, 1.0f);
                final GridDragDropState gridDragDropState2 = (GridDragDropState) ref$ObjectRef4.element;
                final LayoutDirection layoutDirection = (LayoutDirection) composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection);
                Offset offsetM395boximpl = Offset.m395boximpl(j);
                ref$ObjectRef = ref$ObjectRef3;
                ref$ObjectRef2 = ref$ObjectRef4;
                PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final GridDragDropState gridDragDropState3 = gridDragDropState2;
                        final int i8 = i;
                        final LayoutDirection layoutDirection2 = layoutDirection;
                        final long j2 = j;
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        final int i9 = 0;
                        final int i10 = 1;
                        Object objDetectDragGesturesAfterLongPress = DragGestureDetectorKt.detectDragGesturesAfterLongPress(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                ContentListState contentListState3;
                                Object objPrevious;
                                long j3 = ((Offset) obj2).packedValue;
                                GridDragDropState gridDragDropState4 = gridDragDropState3;
                                GridDragDropStateV1 gridDragDropStateV1 = gridDragDropState4.dragDropState;
                                gridDragDropStateV1.getClass();
                                float fIntBitsToFloat = layoutDirection2 == LayoutDirection.Ltr ? Float.intBitsToFloat((int) (j3 >> 32)) : i8 - Float.intBitsToFloat((int) (j3 >> 32));
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j3 & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
                                Offset.Companion companion6 = Offset.Companion;
                                List list2 = ((LazyGridMeasureResult) gridDragDropStateV1.state.getLayoutInfo()).visibleItemsInfo;
                                ArrayList arrayList = new ArrayList();
                                Iterator it = list2.iterator();
                                while (true) {
                                    boolean zHasNext = it.hasNext();
                                    contentListState3 = gridDragDropStateV1.contentListState;
                                    if (!zHasNext) {
                                        break;
                                    }
                                    Object next = it.next();
                                    if (contentListState3.isItemEditable(((LazyGridMeasuredItem) ((LazyGridItemInfo) next)).index)) {
                                        arrayList.add(next);
                                    }
                                }
                                LazyGridItemInfo lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA = LazyGridStateExtKt.m1090firstItemAtOffsetUv8p0NA(arrayList, Offset.m402minusMKHz9U(jFloatToRawIntBits, j2));
                                if (lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA != null) {
                                    LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) lazyGridItemInfoM1090firstItemAtOffsetUv8p0NA;
                                    ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemKey$delegate).setValue((String) lazyGridMeasuredItem.key);
                                    long j4 = lazyGridMeasuredItem.offset;
                                    IntOffset.Companion companion7 = IntOffset.Companion;
                                    ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemInitialOffset$delegate).setValue(Offset.m395boximpl((Float.floatToRawIntBits((int) (j4 & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j4 >> 32)) << 32)));
                                    SnapshotStateList snapshotStateList = contentListState3.list;
                                    ListIterator listIterator = snapshotStateList.listIterator(snapshotStateList.size());
                                    while (true) {
                                        if (!listIterator.hasPrevious()) {
                                            objPrevious = null;
                                            break;
                                        }
                                        objPrevious = listIterator.previous();
                                        CommunalContentModel communalContentModel = (CommunalContentModel) objPrevious;
                                        communalContentModel.getClass();
                                        if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
                                            break;
                                        }
                                    }
                                    CommunalContentModel communalContentModel2 = (CommunalContentModel) objPrevious;
                                    if (communalContentModel2 != null && gridDragDropStateV1.getDraggingItemLayoutInfo() != null) {
                                        int span = communalContentModel2.getSize().getSpan();
                                        LazyGridItemInfo draggingItemLayoutInfo = gridDragDropStateV1.getDraggingItemLayoutInfo();
                                        draggingItemLayoutInfo.getClass();
                                        if (span > ((LazyGridMeasuredItem) draggingItemLayoutInfo).span) {
                                            contentListState3.list.add(gridDragDropStateV1.spacer);
                                            gridDragDropStateV1.spacerIndex = Integer.valueOf(r0.size() - 1);
                                        }
                                    }
                                    String draggingItemKey = gridDragDropState4.dragDropState.getDraggingItemKey();
                                    draggingItemKey.getClass();
                                    baseCommunalViewModel2.onReorderWidgetStart(draggingItemKey);
                                }
                                return Unit.INSTANCE;
                            }
                        }, new Function0() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i9) {
                                    case 0:
                                        gridDragDropState3.onDragInterrupted();
                                        baseCommunalViewModel2.onReorderWidgetEnd();
                                        break;
                                    default:
                                        gridDragDropState3.onDragInterrupted();
                                        baseCommunalViewModel2.onReorderWidgetCancel();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        }, new Function0() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i10) {
                                    case 0:
                                        gridDragDropState3.onDragInterrupted();
                                        baseCommunalViewModel2.onReorderWidgetEnd();
                                        break;
                                    default:
                                        gridDragDropState3.onDragInterrupted();
                                        baseCommunalViewModel2.onReorderWidgetCancel();
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        }, new Function2() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1$$ExternalSyntheticLambda3
                            /* JADX WARN: Removed duplicated region for block: B:24:0x014a  */
                            /* JADX WARN: Removed duplicated region for block: B:41:0x0186  */
                            /* JADX WARN: Removed duplicated region for block: B:42:0x0196  */
                            /* JADX WARN: Removed duplicated region for block: B:44:0x01a4  */
                            /* JADX WARN: Removed duplicated region for block: B:49:0x01cb  */
                            /* JADX WARN: Removed duplicated region for block: B:58:0x01f4  */
                            /* JADX WARN: Removed duplicated region for block: B:61:0x020b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj2, Object obj3) {
                                long j3;
                                ContentListState contentListState3;
                                Object obj4;
                                float fIntBitsToFloat;
                                Integer num;
                                Integer numValueOf;
                                Object objPrevious;
                                ListIterator listIterator;
                                ((PointerInputChange) obj2).consume();
                                long j4 = ((Offset) obj3).packedValue;
                                GridDragDropStateV1 gridDragDropStateV1 = gridDragDropState3.dragDropState;
                                long jM1085getDraggingItemDraggedDeltaF1C5BW0 = gridDragDropStateV1.m1085getDraggingItemDraggedDeltaF1C5BW0();
                                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j4 >> 32));
                                if (LayoutDirection.Ltr != layoutDirection2) {
                                    fIntBitsToFloat2 = -fIntBitsToFloat2;
                                }
                                long j5 = 4294967295L;
                                ((SnapshotMutableStateImpl) gridDragDropStateV1.draggingItemDraggedDelta$delegate).setValue(Offset.m395boximpl(Offset.m403plusMKHz9U(jM1085getDraggingItemDraggedDeltaF1C5BW0, (Float.floatToRawIntBits(fIntBitsToFloat2) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j4 & 4294967295L))) & 4294967295L))));
                                LazyGridItemInfo draggingItemLayoutInfo = gridDragDropStateV1.getDraggingItemLayoutInfo();
                                if (draggingItemLayoutInfo != null) {
                                    LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) draggingItemLayoutInfo;
                                    long j6 = lazyGridMeasuredItem.offset;
                                    IntOffset.Companion companion6 = IntOffset.Companion;
                                    long jM403plusMKHz9U = Offset.m403plusMKHz9U((Float.floatToRawIntBits((int) (j6 >> 32)) << 32) | (Float.floatToRawIntBits((int) (j6 & 4294967295L)) & 4294967295L), gridDragDropStateV1.m1086getDraggingItemOffsetF1C5BW0());
                                    long j7 = lazyGridMeasuredItem.size;
                                    int i11 = (int) (jM403plusMKHz9U >> 32);
                                    float fIntBitsToFloat3 = Float.intBitsToFloat((int) (IntSizeKt.m866toSizeozmzZPI(j7) >> 32)) + Float.intBitsToFloat(i11);
                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (r10 & 4294967295L)) + Float.intBitsToFloat((int) (jM403plusMKHz9U & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat3) << 32);
                                    Offset.m403plusMKHz9U(jM403plusMKHz9U, Offset.m397divtuRUvjQ(2.0f, Offset.m402minusMKHz9U(jFloatToRawIntBits, jM403plusMKHz9U)));
                                    IntRect intRectM860IntRectVbeCjmY = IntRectKt.m860IntRectVbeCjmY(IntOffset.m853plusqkQi6aY(lazyGridMeasuredItem.offset, IntOffsetKt.m856roundk4lQ0M(gridDragDropStateV1.m1086getDraggingItemOffsetF1C5BW0())), j7);
                                    LazyGridState lazyGridState3 = gridDragDropStateV1.state;
                                    List list2 = ((LazyGridMeasureResult) lazyGridState3.getLayoutInfo()).visibleItemsInfo;
                                    ListIterator listIterator2 = list2.listIterator(list2.size());
                                    while (true) {
                                        boolean zHasPrevious = listIterator2.hasPrevious();
                                        ContentListState contentListState4 = gridDragDropStateV1.contentListState;
                                        if (!zHasPrevious) {
                                            j3 = j5;
                                            contentListState3 = contentListState4;
                                            obj4 = null;
                                            break;
                                        }
                                        objPrevious = listIterator2.previous();
                                        int i12 = ((LazyGridMeasuredItem) ((LazyGridItemInfo) CollectionsKt___CollectionsKt.last(((LazyGridMeasureResult) lazyGridState3.getLayoutInfo()).visibleItemsInfo))).index;
                                        LazyGridMeasuredItem lazyGridMeasuredItem2 = (LazyGridMeasuredItem) ((LazyGridItemInfo) objPrevious);
                                        j3 = j5;
                                        IntRect intRectM860IntRectVbeCjmY2 = IntRectKt.m860IntRectVbeCjmY(lazyGridMeasuredItem2.offset, lazyGridMeasuredItem2.size);
                                        if (!Intrinsics.areEqual(gridDragDropStateV1.getDraggingItemKey(), lazyGridMeasuredItem2.key)) {
                                            int i13 = lazyGridMeasuredItem2.index;
                                            contentListState3 = contentListState4;
                                            if (contentListState3.isItemEditable(i13)) {
                                                listIterator = listIterator2;
                                                if ((intRectM860IntRectVbeCjmY.m857containsgyyYBs(intRectM860IntRectVbeCjmY2.m858getCenternOccac()) || intRectM860IntRectVbeCjmY2.m857containsgyyYBs(intRectM860IntRectVbeCjmY.m858getCenternOccac())) && (i13 != i12 || lazyGridMeasuredItem2.span <= lazyGridMeasuredItem.span)) {
                                                    break;
                                                }
                                            } else {
                                                listIterator = listIterator2;
                                            }
                                        }
                                        listIterator2 = listIterator;
                                        j5 = j3;
                                    }
                                    obj4 = objPrevious;
                                    LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) obj4;
                                    if (lazyGridItemInfo != null) {
                                        LazyGridMeasuredItem lazyGridMeasuredItem3 = (LazyGridMeasuredItem) lazyGridItemInfo;
                                        Object obj5 = gridDragDropStateV1.previousTargetItemKey;
                                        Object obj6 = lazyGridMeasuredItem3.key;
                                        if (!Intrinsics.areEqual(obj6, obj5)) {
                                            LazyGridScrollPosition lazyGridScrollPosition = lazyGridState3.scrollPosition;
                                            int index = lazyGridScrollPosition.getIndex();
                                            int i14 = lazyGridMeasuredItem.index;
                                            int i15 = lazyGridMeasuredItem3.index;
                                            if (i15 == index) {
                                                numValueOf = Integer.valueOf(i14);
                                            } else if (i14 == lazyGridScrollPosition.getIndex()) {
                                                numValueOf = Integer.valueOf(i15);
                                            } else {
                                                num = null;
                                                gridDragDropStateV1.previousTargetItemKey = obj6;
                                                if (num == null) {
                                                    BuildersKt.launch$default(gridDragDropStateV1.scope, null, null, new GridDragDropStateV1$onDrag$1(gridDragDropStateV1, num, draggingItemLayoutInfo, lazyGridItemInfo, null), 3);
                                                } else {
                                                    SnapshotStateList snapshotStateList = contentListState3.list;
                                                    snapshotStateList.add(i15, snapshotStateList.remove(i14));
                                                }
                                                gridDragDropStateV1.setDraggingToRemove(false);
                                            }
                                            num = numValueOf;
                                            gridDragDropStateV1.previousTargetItemKey = obj6;
                                            if (num == null) {
                                            }
                                            gridDragDropStateV1.setDraggingToRemove(false);
                                        } else if (lazyGridItemInfo == null) {
                                            if (Float.intBitsToFloat((int) (gridDragDropStateV1.m1085getDraggingItemDraggedDeltaF1C5BW0() >> 32)) > 0.0f) {
                                                fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) - ((LazyGridMeasureResult) lazyGridState3.getLayoutInfo()).viewportEndOffset;
                                                if (fIntBitsToFloat < 0.0f) {
                                                    fIntBitsToFloat = 0.0f;
                                                }
                                                if (fIntBitsToFloat != 0.0f) {
                                                    gridDragDropStateV1.scrollChannel.mo3476trySendJP2dKIU(Float.valueOf(fIntBitsToFloat));
                                                }
                                                gridDragDropStateV1.setDraggingToRemove(Float.intBitsToFloat((int) (gridDragDropStateV1.m1085getDraggingItemDraggedDeltaF1C5BW0() & j3)) < 0.0f ? ((Boolean) gridDragDropStateV1.updateDragPositionForRemove.mo781invoke(intRectM860IntRectVbeCjmY)).booleanValue() : false);
                                                gridDragDropStateV1.previousTargetItemKey = null;
                                            } else if (Float.intBitsToFloat((int) (gridDragDropStateV1.m1085getDraggingItemDraggedDeltaF1C5BW0() >> 32)) < 0.0f) {
                                                fIntBitsToFloat = Float.intBitsToFloat(i11) - ((LazyGridMeasureResult) lazyGridState3.getLayoutInfo()).viewportStartOffset;
                                                if (fIntBitsToFloat > 0.0f) {
                                                }
                                                if (fIntBitsToFloat != 0.0f) {
                                                }
                                                gridDragDropStateV1.setDraggingToRemove(Float.intBitsToFloat((int) (gridDragDropStateV1.m1085getDraggingItemDraggedDeltaF1C5BW0() & j3)) < 0.0f ? ((Boolean) gridDragDropStateV1.updateDragPositionForRemove.mo781invoke(intRectM860IntRectVbeCjmY)).booleanValue() : false);
                                                gridDragDropStateV1.previousTargetItemKey = null;
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, continuation);
                        return objDetectDragGesturesAfterLongPress == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectDragGesturesAfterLongPress : Unit.INSTANCE;
                    }
                };
                PointerEvent pointerEvent = SuspendingPointerInputFilterKt.EmptyPointerEvent;
                modifierM131height3ABfNKs = modifierFillMaxSize.then(new SuspendPointerInputElement(gridDragDropState2, offsetM395boximpl, null, pointerInputEventHandler, 4, null));
                int i8 = i6 | ((i3 >> 15) & 112) | ((i3 >> 18) & 896);
                composerImpl2.startReplaceGroup(-1054567112);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.rememberDragAndDropTargetState (DragAndDropTargetState.kt:60)");
                }
                Object objRememberedValue5 = composerImpl2.rememberedValue();
                if (objRememberedValue5 == obj) {
                    objRememberedValue5 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                    composerImpl2.updateRememberedValue(objRememberedValue5);
                }
                CoroutineScope coroutineScope3 = (CoroutineScope) objRememberedValue5;
                float fMo58toPx0680j_42 = ((Density) composerImpl2.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(f);
                composerImpl2.startReplaceGroup(-1244924895);
                boolean zChanged3 = ((((i8 & 14) ^ 6) > 4 && composerImpl2.changed(lazyGridState2)) || (i8 & 6) == 4) | ((((i8 & 112) ^ 48) > 32 && composerImpl2.changed(j)) || (i8 & 48) == 32) | composerImpl2.changed(contentListState2) | composerImpl2.changed(fMo58toPx0680j_42) | composerImpl2.changed(coroutineScope3);
                Object objRememberedValue6 = composerImpl2.rememberedValue();
                if (zChanged3 || objRememberedValue6 == obj) {
                    objRememberedValue6 = new DragAndDropTargetState(lazyGridState, j, contentListState2, fMo58toPx0680j_42, coroutineScope3, null);
                    composerImpl2.updateRememberedValue(objRememberedValue6);
                }
                DragAndDropTargetState dragAndDropTargetState = (DragAndDropTargetState) objRememberedValue6;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-1244912584);
                boolean zChangedInstance = composerImpl2.changedInstance(dragAndDropTargetState) | composerImpl2.changedInstance(coroutineScope3);
                Object objRememberedValue7 = composerImpl2.rememberedValue();
                if (zChangedInstance || objRememberedValue7 == obj) {
                    objRememberedValue7 = new DragAndDropTargetStateKt$rememberDragAndDropTargetState$1$1(dragAndDropTargetState, coroutineScope3, null);
                    composerImpl2.updateRememberedValue(objRememberedValue7);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, dragAndDropTargetState, (Function2) objRememberedValue7);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(companion, 1.0f);
                composerImpl2.startReplaceGroup(-2092996508);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.dragAndDropTarget (DragAndDropTargetState.kt:87)");
                }
                SnapshotStateKt.rememberUpdatedState(dragAndDropTargetState, composerImpl2);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierFillMaxSize2);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function0);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                composerImpl2.end(true);
                composerImpl2.end(false);
                Ref$ObjectRef ref$ObjectRef5 = ref$ObjectRef2;
                composerImpl = composerImpl2;
                m1080HorizontalGridWrappertOXsyB8(paddingValues, lazyGridState, (GridDragDropState) ref$ObjectRef5.element, function12, fM1084getItemSpacingD9Ej5fM, fM1084getItemSpacingD9Ej5fM, modifierM131height3ABfNKs, new CommunalHubKt$$ExternalSyntheticLambda14(ref$ObjectRef, state, baseCommunalViewModel, ref$ObjectRef5, paddingValues, lazyGridState, contentListState, interactionHandler, communalAppWidgetSection, contentScope, widgetConfigurator), composerImpl, ((i3 >> 9) & 14) | ((i3 >> 18) & 112) | 3072);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                z = false;
            }
            ref$ObjectRef = ref$ObjectRef3;
            ref$ObjectRef2 = ref$ObjectRef4;
            composerImpl2.startReplaceGroup(-1447846674);
            Dimensions hubDimensions = getHubDimensions(composerImpl2);
            companion4.getClass();
            DensityUtils.Companion.getClass();
            float fM1083getGridTopSpacingD9Ej5fM = hubDimensions.m1083getGridTopSpacingD9Ej5fM() + DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530);
            Dp.Companion companion6 = Dp.Companion;
            modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(modifierOnGloballyPositioned, fM1083getGridTopSpacingD9Ej5fM);
            composerImpl2.end(z);
            Ref$ObjectRef ref$ObjectRef52 = ref$ObjectRef2;
            composerImpl = composerImpl2;
            m1080HorizontalGridWrappertOXsyB8(paddingValues, lazyGridState, (GridDragDropState) ref$ObjectRef52.element, function12, fM1084getItemSpacingD9Ej5fM, fM1084getItemSpacingD9Ej5fM, modifierM131height3ABfNKs, new CommunalHubKt$$ExternalSyntheticLambda14(ref$ObjectRef, state, baseCommunalViewModel, ref$ObjectRef52, paddingValues, lazyGridState, contentListState, interactionHandler, communalAppWidgetSection, contentScope, widgetConfigurator), composerImpl, ((i3 >> 9) & 14) | ((i3 >> 18) & 112) | 3072);
            if (ComposerKt.isTraceInProgress()) {
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(list, baseCommunalViewModel, paddingValues, state, i, j, lazyGridState, contentListState, function1, function12, function13, widgetConfigurator, interactionHandler, communalAppWidgetSection, contentScope, i2) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda15
                public final /* synthetic */ List f$1;
                public final /* synthetic */ Function1 f$10;
                public final /* synthetic */ Function1 f$11;
                public final /* synthetic */ WidgetConfigurator f$12;
                public final /* synthetic */ RemoteViews.InteractionHandler f$13;
                public final /* synthetic */ CommunalAppWidgetSection f$14;
                public final /* synthetic */ ContentScope f$15;
                public final /* synthetic */ BaseCommunalViewModel f$2;
                public final /* synthetic */ PaddingValues f$3;
                public final /* synthetic */ State f$4;
                public final /* synthetic */ int f$5;
                public final /* synthetic */ long f$6;
                public final /* synthetic */ LazyGridState f$7;
                public final /* synthetic */ ContentListState f$8;
                public final /* synthetic */ Function1 f$9;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(805306375);
                    CommunalAppWidgetSection communalAppWidgetSection2 = this.f$14;
                    ContentScope contentScope2 = this.f$15;
                    CommunalHubKt.m1079CommunalHubLazyGridMGE6UKE(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10, this.f$11, this.f$12, this.f$13, communalAppWidgetSection2, contentScope2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
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
            CardColors cardColorsM255cardColorsro_MJ88 = CardDefaults.m255cardColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
            DensityUtils.Companion.getClass();
            modifier2 = modifier;
            CardKt.Card(modifier2, RoundedCornerShapeKt.m188RoundedCornerShapea9UjIt4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(68), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(34), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(68), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(34)), cardColorsM255cardColorsro_MJ88, null, null, ComposableLambdaKt.rememberComposableLambda(-2139731156, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.CtaTileInViewModeContent.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
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
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CtaTileInViewModeContent.<anonymous> (CommunalHub.kt:1366)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                            DensityUtils.Companion.getClass();
                            Modifier modifierM126paddingVpY3zN4 = PaddingKt.m126paddingVpY3zN4(modifierFillMaxSize, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(50), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(32));
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                            Alignment.Companion.getClass();
                            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, horizontal, composer2, 54);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM126paddingVpY3zN4);
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
                            Updater.m337setimpl(composer2, columnMeasurePolicy, function2);
                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                            }
                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, function24);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                            ImageVector widgets = WidgetsKt.getWidgets();
                            String strStringResource = StringResources_androidKt.stringResource(R.string.cta_label_to_open_widget_picker, composer2);
                            Dimensions.Companion.getClass();
                            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, Dimensions.IconSize);
                            composerImpl3.startReplaceGroup(-1822745531);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda34(2);
                                composerImpl3.updateRememberedValue(objRememberedValue);
                            }
                            composerImpl3.end(false);
                            IconKt.m271Iconww6aTOc(widgets, strStringResource, SemanticsModifierKt.clearAndSetSemantics(modifierM140size3ABfNKs, (Function1) objRememberedValue), 0L, composer2, 0, 8);
                            SpacerKt.Spacer(composer2, SizeKt.m140size3ABfNKs(companion, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(6)));
                            String strStringResource2 = StringResources_androidKt.stringResource(R.string.cta_label_to_edit_widget, composer2);
                            MaterialTheme.INSTANCE.getClass();
                            TextStyle textStyle = MaterialTheme.getTypography(composer2).titleLarge;
                            Dp.Companion companion2 = Dp.Companion;
                            long jM1082access$nonScalableTextSize8Feqmps = CommunalHubKt.m1082access$nonScalableTextSize8Feqmps(22, composer2);
                            long jM1082access$nonScalableTextSize8Feqmps2 = CommunalHubKt.m1082access$nonScalableTextSize8Feqmps(28, composer2);
                            ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composer2);
                            TextKt.m317Text4IGK_g(strStringResource2, columnScopeInstance.weight(ScrollingContainerKt.scrollingContainer(companion, scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true)), 1.0f, true), 0L, jM1082access$nonScalableTextSize8Feqmps, null, null, null, 0L, null, null, jM1082access$nonScalableTextSize8Feqmps2, 0, false, 0, 0, null, textStyle, composer2, 0, 0, 64500);
                            SpacerKt.Spacer(composer2, SizeKt.m140size3ABfNKs(companion, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(16)));
                            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion, 1.0f), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(56));
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m93spacedByD5KLDUw(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(16), horizontal), Alignment.Companion.Top, composer2, 0);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierM131height3ABfNKs);
                            composerImpl3.startReusableNode();
                            if (composerImpl3.inserting) {
                                composerImpl3.createNode(function0);
                            } else {
                                composerImpl3.useNode();
                            }
                            Updater.m337setimpl(composer2, rowMeasurePolicy, function2);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier2, function24);
                            final RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getDensity(), RangesKt___RangesKt.coerceIn(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getFontScale(), 0.0f, 1.25f)));
                            final ColorScheme colorScheme2 = colorScheme;
                            final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                            CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(1286552390, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1$1$2$1
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0076  */
                                /* JADX WARN: Removed duplicated region for block: B:20:0x00d3  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
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
                                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CtaTileInViewModeContent.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CommunalHub.kt:1399)");
                                            }
                                            Modifier.Companion companion3 = Modifier.Companion;
                                            FillElement fillElement = SizeKt.FillWholeMaxHeight;
                                            companion3.then(fillElement);
                                            RowScope rowScope = rowScopeInstance;
                                            Modifier modifierWeight = rowScope.weight(fillElement, 1.0f, true);
                                            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                                            ColorScheme colorScheme3 = colorScheme2;
                                            long j2 = colorScheme3.onPrimary;
                                            buttonDefaults.getClass();
                                            ButtonColors buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(0L, j2, composer3, 13);
                                            Dp.Companion companion4 = Dp.Companion;
                                            BorderStroke borderStrokeM31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U((float) 1.0d, colorScheme3.primaryContainer);
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                            composerImpl5.startReplaceGroup(1492651036);
                                            BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel2;
                                            boolean zChangedInstance = composerImpl5.changedInstance(baseCommunalViewModel3);
                                            Object objRememberedValue2 = composerImpl5.rememberedValue();
                                            Composer.Companion companion5 = Composer.Companion;
                                            if (!zChangedInstance) {
                                                companion5.getClass();
                                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                                    objRememberedValue2 = new CommunalHubKt$CtaTileInViewModeContent$1$1$2$1$1$1(baseCommunalViewModel3);
                                                    composerImpl5.updateRememberedValue(objRememberedValue2);
                                                }
                                                composerImpl5.end(false);
                                                float f = 0;
                                                PaddingValuesImpl paddingValuesImplM123PaddingValuesa9UjIt4 = PaddingKt.m123PaddingValuesa9UjIt4(f, f, f, f);
                                                ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                                                ButtonKt.OutlinedButton((Function0) ((KFunction) objRememberedValue2), modifierWeight, false, null, buttonColorsM252buttonColorsro_MJ88, null, borderStrokeM31BorderStrokecXLIe8U, paddingValuesImplM123PaddingValuesa9UjIt4, null, ComposableSingletons$CommunalHubKt.f33lambda5, composerImpl5, 817889280, 300);
                                                Modifier modifierWeight2 = rowScope.weight(fillElement, 1.0f, true);
                                                ButtonColors buttonColorsM252buttonColorsro_MJ882 = ButtonDefaults.m252buttonColorsro_MJ88(colorScheme3.primaryContainer, colorScheme3.onPrimaryContainer, composerImpl5, 12);
                                                composerImpl5.startReplaceGroup(1492674974);
                                                boolean zChangedInstance2 = composerImpl5.changedInstance(baseCommunalViewModel3);
                                                Object objRememberedValue3 = composerImpl5.rememberedValue();
                                                if (!zChangedInstance2) {
                                                    companion5.getClass();
                                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                                        objRememberedValue3 = new CommunalHubKt$CtaTileInViewModeContent$1$1$2$1$2$1(baseCommunalViewModel3);
                                                        composerImpl5.updateRememberedValue(objRememberedValue3);
                                                    }
                                                    composerImpl5.end(false);
                                                    ButtonKt.Button((Function0) objRememberedValue3, modifierWeight2, false, null, buttonColorsM252buttonColorsro_MJ882, null, null, PaddingKt.m123PaddingValuesa9UjIt4(f, f, f, f), null, ComposableSingletons$CommunalHubKt.f34lambda6, composerImpl5, 817889280, 364);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 56);
                            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 14) | 196608, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda24(baseCommunalViewModel, modifier2, i, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DisabledWidgetPlaceholder(CommunalContentModel.WidgetContent.DisabledWidget disabledWidget, BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, Composer composer, int i) {
        int i2;
        Icon iconCreateWithResource;
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-959922153);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(disabledWidget) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(baseCommunalViewModel) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DisabledWidgetPlaceholder (CommunalHub.kt:1640)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            ActivityInfo activityInfo = disabledWidget.providerInfo.providerInfo;
            ApplicationInfo applicationInfo = activityInfo != null ? activityInfo.applicationInfo : null;
            if (applicationInfo == null || (i3 = applicationInfo.icon) == 0) {
                iconCreateWithResource = Icon.createWithResource(context, android.R.drawable.sym_def_app_icon);
                iconCreateWithResource.getClass();
            } else {
                iconCreateWithResource = Icon.createWithResource(applicationInfo.packageName, i3);
                iconCreateWithResource.getClass();
            }
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifier, MaterialTheme.getColorScheme(composerImpl).surfaceVariant, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(android.R.dimen.system_app_widget_background_radius, composerImpl)));
            boolean z = !baseCommunalViewModel.isEditMode();
            composerImpl.startReplaceGroup(-1387884865);
            boolean zChangedInstance = composerImpl.changedInstance(baseCommunalViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CommunalHubKt$DisabledWidgetPlaceholder$1$1(baseCommunalViewModel);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierM26backgroundbw27NRU, null, null, z, null, null, (Function0) ((KFunction) objRememberedValue), 24);
                Arrangement.INSTANCE.getClass();
                Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM34clickableO2vRcR0$default);
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
                Painter painterRememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(iconCreateWithResource.loadDrawable(context), composerImpl);
                String strStringResource = StringResources_androidKt.stringResource(R.string.icon_description_for_disabled_widget, composerImpl);
                Modifier.Companion companion = Modifier.Companion;
                Dimensions.Companion.getClass();
                Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, Dimensions.IconSize);
                ColorFilter.Companion companion2 = ColorFilter.Companion;
                Colors.INSTANCE.getClass();
                float[] fArr = ((ColorMatrix) Colors.DisabledColorFilter$delegate.getValue()).values;
                companion2.getClass();
                ImageKt.Image(painterRememberDrawablePainter, strStringResource, modifierM140size3ABfNKs, null, null, 0.0f, new ColorMatrixColorFilter(fArr, (DefaultConstructorMarker) null), composerImpl, 384, 56);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(disabledWidget, baseCommunalViewModel, modifier, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DisclaimerBottomSheetContent(Function0 function0, Composer composer, int i) {
        boolean z;
        Function0 function02 = function0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(31097356);
        int i2 = i | (composerImpl.changedInstance(function02) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.DisclaimerBottomSheetContent (CommunalHub.kt:546)");
            }
            MaterialTheme.INSTANCE.getClass();
            ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            float f = 32;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM126paddingVpY3zN4 = PaddingKt.m126paddingVpY3zN4(modifierFillMaxWidth, f, 24);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM126paddingVpY3zN4);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Icons.Outlined outlined = Icons.Outlined.INSTANCE;
            IconKt.m271Iconww6aTOc(WidgetsKt.getWidgets(), (String) null, SizeKt.m140size3ABfNKs(companion, f), colorScheme.primary, composerImpl, 432, 0);
            float f2 = 16;
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion, f2));
            TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.communal_widgets_disclaimer_title, composerImpl), null, colorScheme.onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).headlineMedium, composerImpl, 0, 0, 65530);
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion, f2));
            TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.communal_widgets_disclaimer_text, composerImpl), null, colorScheme.onSurfaceVariant, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composerImpl, 0, 0, 131066);
            composerImpl = composerImpl;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.m146widthInVpY3zN4$default(PaddingKt.m126paddingVpY3zN4(companion, 26, f2), 200, 0.0f, 2), 56, 0.0f, 2);
            composerImpl.startReplaceGroup(-1173248053);
            boolean z2 = (i2 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z2) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    z = false;
                    function02 = function0;
                    objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda8(function02, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                } else {
                    z = false;
                    function02 = function0;
                }
                composerImpl.end(z);
                ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                ButtonKt.Button((Function0) objRememberedValue, modifierM133heightInVpY3zN4$default, false, null, null, null, null, null, null, ComposableSingletons$CommunalHubKt.f29lambda1, composerImpl, 805306416, 508);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda9(i, function02);
        }
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
            float fM1083getGridTopSpacingD9Ej5fM = hubDimensions.m1083getGridTopSpacingD9Ej5fM() + DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530);
            Dp.Companion companion3 = Dp.Companion;
            Modifier modifierPadding = PaddingKt.padding(SizeKt.m131height3ABfNKs(companion, fM1083getGridTopSpacingD9Ej5fM), paddingValues);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long j = colorScheme.primary;
            cardDefaults.getClass();
            CardColors cardColorsM255cardColorsro_MJ88 = CardDefaults.m255cardColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
            companion2.getClass();
            CardKt.Card(modifierPadding, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(80)), cardColorsM255cardColorsro_MJ88, null, null, ComposableLambdaKt.rememberComposableLambda(-635955488, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.EmptyStateCta.1
                /* JADX WARN: Removed duplicated region for block: B:28:0x0133  */
                /* JADX WARN: Removed duplicated region for block: B:45:0x01fa  */
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
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.EmptyStateCta.<anonymous> (CommunalHub.kt:1068)");
                            }
                            Modifier.Companion companion4 = Modifier.Companion;
                            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion4, 1.0f);
                            DensityUtils.Companion.getClass();
                            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(modifierFillMaxSize, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(110), 0.0f, 2);
                            Arrangement arrangement = Arrangement.INSTANCE;
                            Dimensions.Companion.getClass();
                            float fM1084getItemSpacingD9Ej5fM = Dimensions.Companion.m1084getItemSpacingD9Ej5fM() / 2;
                            Dp.Companion companion5 = Dp.Companion;
                            Alignment.Companion.getClass();
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            arrangement.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.m94spacedByD5KLDUw(fM1084getItemSpacingD9Ej5fM, vertical), Alignment.Companion.CenterHorizontally, composer2, 48);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM127paddingVpY3zN4$default);
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
                            Updater.m337setimpl(composer2, columnMeasurePolicy, function2);
                            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                            }
                            Function2 function24 = ComposeUiNode.Companion.SetModifier;
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, function24);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            String strStringResource = StringResources_androidKt.stringResource(R.string.title_for_empty_state_cta, composer2);
                            MaterialTheme.INSTANCE.getClass();
                            TextStyle textStyle = MaterialTheme.getTypography(composer2).displaySmall;
                            ColorScheme colorScheme2 = colorScheme;
                            long j2 = colorScheme2.onPrimary;
                            TextAlign.Companion.getClass();
                            TextStyle textStyleM757mergedA7vx0o$default = TextStyle.m757mergedA7vx0o$default(textStyle, j2, 0L, null, null, null, 0L, null, TextAlign.Center, 0L, 16744446);
                            TextAutoSize textAutoSizeM201StepBasedvU0ePk$default = TextAutoSize.Companion.m201StepBasedvU0ePk$default(TextAutoSize.Companion, TextUnitKt.getSp(36), TextUnitKt.getSp(0.1d));
                            Modifier modifierFocusable$default = FocusableKt.focusable$default(companion4, false, null, 3);
                            composerImpl3.startReplaceGroup(768395855);
                            boolean zChanged = composerImpl3.changed(strStringResource);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion6 = Composer.Companion;
                            if (!zChanged) {
                                companion6.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda1(strStringResource, 1);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                BasicTextKt.m194BasicTextRWo7tUw(strStringResource, SemanticsModifierKt.semantics(modifierFocusable$default, true, (Function1) objRememberedValue), textStyleM757mergedA7vx0o$default, (Function1) null, 0, false, 0, 0, (ColorProducer) null, textAutoSizeM201StepBasedvU0ePk$default, composer2, 0, 504);
                                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion4, 1.0f);
                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Center, Alignment.Companion.Top, composer2, 6);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxWidth);
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function0);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composer2, rowMeasurePolicy, function2);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier2, function24);
                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion4, 56);
                                ButtonDefaults.INSTANCE.getClass();
                                ButtonColors buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(colorScheme2.primaryContainer, colorScheme2.onPrimaryContainer, composer2, 12);
                                composerImpl3.startReplaceGroup(1733001784);
                                Object obj4 = baseCommunalViewModel;
                                boolean zChangedInstance = composerImpl3.changedInstance(obj4);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance) {
                                    companion6.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new CommunalHubKt$$ExternalSyntheticLambda8(obj4, 1);
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                                    ButtonKt.Button((Function0) objRememberedValue2, modifierM131height3ABfNKs, false, null, buttonColorsM252buttonColorsro_MJ88, null, null, null, null, ComposableSingletons$CommunalHubKt.f30lambda2, composer2, 805306416, 492);
                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, true, true)) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 196608, 24);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda4(i, 1, paddingValues, baseCommunalViewModel);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void HighlightedItem(final Modifier modifier, final float f, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1602272507);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                f = 1.0f;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.HighlightedItem (CommunalHub.kt:1328)");
            }
            MaterialTheme.INSTANCE.getClass();
            final SolidColor solidColor = new SolidColor(MaterialTheme.getColorScheme(composerImpl).primary, null);
            composerImpl.startReplaceGroup(-1959915338);
            boolean zChanged = ((i3 & 112) == 32) | composerImpl.changed(solidColor);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda27
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            DrawScope drawScope = (DrawScope) obj;
                            DensityUtils.Companion.getClass();
                            float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(8));
                            float f2 = -fMo58toPx0680j_4;
                            Offset.Companion companion = Offset.Companion;
                            float f3 = fMo58toPx0680j_4 * 2;
                            float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) + f3;
                            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) + f3;
                            long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
                            Size.Companion companion2 = Size.Companion;
                            float fMo58toPx0680j_42 = drawScope.mo58toPx0680j_4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(37));
                            CornerRadius.Companion companion3 = CornerRadius.Companion;
                            DrawScope.m542drawRoundRectZuiqVtQ$default(drawScope, solidColor, (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L), jFloatToRawIntBits, (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (4294967295L & Float.floatToRawIntBits(fMo58toPx0680j_42)), f, new Stroke(drawScope.mo58toPx0680j_4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(3)), 0.0f, 0, 0, null, 30, null), 192);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                BoxKt.Box(DrawModifierKt.drawBehind(modifier, (Function1) objRememberedValue), composerImpl, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    float f2 = f;
                    int i5 = i2;
                    CommunalHubKt.HighlightedItem(modifier, f2, (Composer) obj, iUpdateChangedFlags, i5);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x0163  */
    /* renamed from: HorizontalGridWrapper-tOXsyB8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m1080HorizontalGridWrappertOXsyB8(final PaddingValues paddingValues, final LazyGridState lazyGridState, final GridDragDropState gridDragDropState, final Function1 function1, final float f, final float f2, final Modifier modifier, final CommunalHubKt$$ExternalSyntheticLambda14 communalHubKt$$ExternalSyntheticLambda14, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(875835493);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(paddingValues) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(lazyGridState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(gridDragDropState) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(f) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(f2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl.changedInstance(communalHubKt$$ExternalSyntheticLambda14) ? 8388608 : 4194304;
        }
        if ((4793491 & i2) == 4793490 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.HorizontalGridWrapper (CommunalHub.kt:795)");
            }
            boolean z = (gridDragDropState != null ? gridDragDropState.dragDropState.getDraggingItemKey() : null) != null;
            composerImpl.startReplaceGroup(1398365792);
            LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            float fCalculateStartPadding = PaddingKt.calculateStartPadding(paddingValues, layoutDirection);
            float fMo113calculateTopPaddingD9Ej5fM = paddingValues.mo113calculateTopPaddingD9Ej5fM();
            function1.mo781invoke(Offset.m395boximpl((Float.floatToRawIntBits(density.mo58toPx0680j_4(fCalculateStartPadding)) << 32) | (Float.floatToRawIntBits(density.mo58toPx0680j_4(fMo113calculateTopPaddingD9Ej5fM)) & 4294967295L)));
            boolean z2 = z;
            GridCells.Fixed fixed = new GridCells.Fixed(CommunalContentSize.FixedSize.FULL.getSpan());
            Arrangement arrangement = Arrangement.INSTANCE;
            Dimensions.Companion.getClass();
            float fM1084getItemSpacingD9Ej5fM = Dimensions.Companion.m1084getItemSpacingD9Ej5fM();
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(fM1084getItemSpacingD9Ej5fM);
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(Dimensions.Companion.m1084getItemSpacingD9Ej5fM());
            boolean z3 = !z2;
            composerImpl.startReplaceGroup(183688791);
            boolean z4 = (29360128 & i2) == 8388608;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z4) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda18(communalHubKt$$ExternalSyntheticLambda14, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                LazyGridDslKt.LazyHorizontalGrid(((i2 << 9) & 7168) | ((i2 >> 15) & 112) | ((i2 << 3) & 896), 0, 656, null, null, spacedAlignedM92spacedBy0680j_4, spacedAlignedM92spacedBy0680j_42, paddingValues, fixed, lazyGridState, composerImpl, modifier, (Function1) objRememberedValue, false, z3);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalHubKt$$ExternalSyntheticLambda14 communalHubKt$$ExternalSyntheticLambda142 = communalHubKt$$ExternalSyntheticLambda14;
                    CommunalHubKt.m1080HorizontalGridWrappertOXsyB8(paddingValues, lazyGridState, gridDragDropState, function1, f, f2, modifier, communalHubKt$$ExternalSyntheticLambda142, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            composerImpl.startReplaceGroup(-782717482);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == obj) {
                objRememberedValue2 = new ArrayList();
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            List list2 = (List) objRememberedValue2;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -782715213);
            if (objM == obj) {
                objM = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl.updateRememberedValue(objM);
            }
            MutableState mutableState = (MutableState) objM;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-782711585);
            boolean zChangedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(list) | composerImpl.changedInstance(list2) | composerImpl.changedInstance(baseCommunalViewModel) | composerImpl.changedInstance(coroutineScope);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == obj) {
                Object communalHubKt$ObserveNewWidgetAddedEffect$1$1 = new CommunalHubKt$ObserveNewWidgetAddedEffect$1$1(list, list2, baseCommunalViewModel, lazyGridState, coroutineScope, mutableState, null);
                composerImpl.updateRememberedValue(communalHubKt$ObserveNewWidgetAddedEffect$1$1);
                objRememberedValue3 = communalHubKt$ObserveNewWidgetAddedEffect$1$1;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, list, (Function2) objRememberedValue3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(list, lazyGridState, baseCommunalViewModel, i, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ObserveScrollEffect(LazyGridState lazyGridState, BaseCommunalViewModel baseCommunalViewModel, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1825347584);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(lazyGridState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(baseCommunalViewModel) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ObserveScrollEffect (CommunalHub.kt:590)");
            }
            composerImpl.startReplaceGroup(517147173);
            boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl.changedInstance(baseCommunalViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CommunalHubKt$ObserveScrollEffect$1$1(lazyGridState, baseCommunalViewModel, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, lazyGridState, (Function2) objRememberedValue);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda3(i, 0, lazyGridState, baseCommunalViewModel);
        }
    }

    public static final void PendingWidgetPlaceholder(CommunalContentModel.WidgetContent.PendingWidget pendingWidget, Modifier modifier, Composer composer, int i) {
        int i2;
        Icon iconCreateWithResource;
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
                iconCreateWithResource = Icon.createWithBitmap(bitmap);
                iconCreateWithResource.getClass();
            } else {
                iconCreateWithResource = Icon.createWithResource(context, android.R.drawable.sym_def_app_icon);
                iconCreateWithResource.getClass();
            }
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifier, MaterialTheme.getColorScheme(composerImpl).surfaceVariant, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(android.R.dimen.system_app_widget_background_radius, composerImpl)));
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU);
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
            Painter painterRememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(iconCreateWithResource.loadDrawable(context), composerImpl);
            String strStringResource = StringResources_androidKt.stringResource(R.string.icon_description_for_pending_widget, composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Dimensions.Companion.getClass();
            ImageKt.Image(painterRememberDrawablePainter, strStringResource, SizeKt.m140size3ABfNKs(companion, Dimensions.IconSize), null, null, 0.0f, null, composerImpl, 384, 120);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda3(i, 1, pendingWidget, modifier);
        }
    }

    /* renamed from: ResizableItemFrameWrapper-iBr3E7A, reason: not valid java name */
    public static final void m1081ResizableItemFrameWrapperiBr3E7A(final String str, final long j, final LazyGridState lazyGridState, final PaddingValues paddingValues, final Arrangement.SpacedAligned spacedAligned, final boolean z, final int i, final int i2, final Modifier modifier, final Function0 function0, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, final Function1 function1, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i3) {
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
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1877479336, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ResizableItemFrameWrapper$3
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
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ResizableItemFrameWrapper.<anonymous> (CommunalHub.kt:727)");
                            }
                            composableLambdaImpl.invoke(Modifier.Companion, composer2, 6);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            int i6 = (i4 & 65534) | ((i4 >> 9) & 458752) | ((i4 << 3) & 3670016);
            int i7 = i4 >> 15;
            int i8 = (i7 & 896) | (i7 & 112) | 12582912 | (i7 & 57344);
            int i9 = i5 << 15;
            ResizeableItemFrameKt.m1088ResizableItemFramegFm42b4(str, j, lazyGridState, paddingValues, spacedAligned, modifier, z, 0.0f, 0L, 0.0f, 0.0f, i, i2, span, function0, resizeableItemFrameViewModel, function1, composableLambdaImplRememberComposableLambda, composerImpl, i6, i8 | (i9 & 458752) | (i9 & 3670016));
            composerImpl = composerImpl;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, j, lazyGridState, paddingValues, spacedAligned, z, i, i2, modifier, function0, resizeableItemFrameViewModel, function1, composableLambdaImpl, i3) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda20
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
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Arrangement.SpacedAligned spacedAligned2 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$12;
                    CommunalHubKt.m1081ResizableItemFrameWrapperiBr3E7A(this.f$0, this.f$1, this.f$2, this.f$3, spacedAligned2, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10, this.f$11, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = new ArrayList();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            List list3 = (List) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -1992882713);
            if (objM == obj) {
                objM = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl.updateRememberedValue(objM);
            }
            MutableState mutableState = (MutableState) objM;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1992879523);
            boolean zChangedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(list) | composerImpl.changedInstance(list3);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == obj) {
                list2 = list;
                lazyGridState2 = lazyGridState;
                Object communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1 = new CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1$1(list2, list3, lazyGridState2, mutableState, null);
                composerImpl.updateRememberedValue(communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1);
                objRememberedValue2 = communalHubKt$ScrollOnUpdatedLiveContentEffect$1$1;
            } else {
                list2 = list;
                lazyGridState2 = lazyGridState;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, list2, (Function2) objRememberedValue2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda4(i, 0, list2, lazyGridState2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SmartspaceContent(RemoteViews.InteractionHandler interactionHandler, CommunalContentModel.Smartspace smartspace, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1317833478);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(interactionHandler) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(smartspace) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.SmartspaceContent (CommunalHub.kt:1711)");
            }
            composerImpl.startReplaceGroup(-123018300);
            composerImpl.startReplaceGroup(-123016811);
            boolean zChangedInstance = composerImpl.changedInstance(smartspace);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda18(smartspace, 1);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function1 = (Function1) objRememberedValue;
                composerImpl.end(false);
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-123028317);
                boolean zChangedInstance2 = composerImpl.changedInstance(interactionHandler) | composerImpl.changedInstance(smartspace);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new CommunalHubKt$$ExternalSyntheticLambda18(interactionHandler, smartspace);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function1 function12 = (Function1) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-123011129);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    companion.getClass();
                    if (objRememberedValue3 == Composer.Companion.Empty) {
                        objRememberedValue3 = new CommunalHubKt$$ExternalSyntheticLambda34(0);
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    AndroidView_androidKt.AndroidView(function12, modifier, (Function1) objRememberedValue3, null, function1, composerImpl, ((i2 >> 3) & 112) | 384, 8);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(interactionHandler, smartspace, modifier, i, 3);
        }
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
                function12.mo781invoke(null);
            }
            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.5f, null, "RemoveButtonAlphaAnimation", null, composerImpl, 3072, 22);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierFillMaxWidth, Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(27), Dimensions.Companion.m1084getItemSpacingD9Ej5fM(), 0.0f, 8);
            composerImpl.startReplaceGroup(1412439994);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                function13 = function1;
                objRememberedValue = new CommunalHubKt$$ExternalSyntheticLambda10(function13, 0);
                composerImpl.updateRememberedValue(objRememberedValue);
            } else {
                function13 = function1;
            }
            composerImpl.end(false);
            Modifier modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifierM129paddingqDBjuR0$default, (Function1) objRememberedValue);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierOnSizeChanged);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            final String strStringResource = StringResources_androidKt.stringResource(R.string.hub_mode_add_widget_button_text, composerImpl);
            composerImpl.startReplaceGroup(-571315015);
            boolean z2 = !z;
            ToolbarButton(z2, function02, boxScopeInstance.align(companion, Alignment.Companion.CenterStart), ComposableLambdaKt.rememberComposableLambda(1682874641, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
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
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Toolbar.<anonymous>.<anonymous> (CommunalHub.kt:1159)");
                            }
                            Icons.INSTANCE.getClass();
                            IconKt.m271Iconww6aTOc(AddKt.getAdd(), (String) null, (Modifier) null, 0L, composer2, 48, 12);
                            TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131070);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 9) & 112) | 3072);
            composerImpl.end(false);
            AnimatedVisibilityKt.AnimatedVisibility(z, boxScopeInstance.align(companion, Alignment.Companion.Center), EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(545396663, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2
                /* JADX WARN: Removed duplicated region for block: B:14:0x007a  */
                /* JADX WARN: Removed duplicated region for block: B:9:0x0047  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.Toolbar.<anonymous>.<anonymous> (CommunalHub.kt:1177)");
                    }
                    ButtonColors buttonColorsAccess$filledButtonColors = CommunalHubKt.access$filledButtonColors(composer2);
                    Dimensions.Companion.getClass();
                    PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                    Modifier.Companion companion2 = Modifier.Companion;
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    composerImpl2.startReplaceGroup(-1682431681);
                    State state = stateAnimateFloatAsState;
                    boolean zChanged = composerImpl2.changed(state);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    Composer.Companion companion3 = Composer.Companion;
                    if (!zChanged) {
                        companion3.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new CommunalHubKt$$ExternalSyntheticLambda18(state, 4);
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    composerImpl2.end(false);
                    Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(companion2, (Function1) objRememberedValue2);
                    composerImpl2.startReplaceGroup(-1682428939);
                    final boolean z3 = z;
                    boolean zChanged2 = composerImpl2.changed(z3);
                    final Function1 function14 = function12;
                    boolean zChanged3 = zChanged2 | composerImpl2.changed(function14);
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    if (!zChanged3) {
                        companion3.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$2$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    LayoutCoordinates layoutCoordinates = (LayoutCoordinates) obj4;
                                    if (z3) {
                                        function14.mo781invoke(layoutCoordinates);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue3);
                        }
                    }
                    composerImpl2.end(false);
                    Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierGraphicsLayer, (Function1) objRememberedValue3);
                    ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                    ButtonKt.Button(function0, modifierOnGloballyPositioned, false, null, buttonColorsAccess$filledButtonColors, null, null, paddingValuesImpl, null, ComposableSingletons$CommunalHubKt.f31lambda3, composerImpl2, 817889280, 364);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 200064 | (i2 & 14), 16);
            Modifier modifierAlign = boxScopeInstance.align(companion, Alignment.Companion.CenterEnd);
            ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
            ToolbarButton(z2, function03, modifierAlign, ComposableSingletons$CommunalHubKt.f32lambda4, composerImpl, ((i2 >> 12) & 112) | 3072);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function1 function14 = function13;
            recomposeScopeImplEndRestartGroup.block = new Function2(z, function0, function14, function12, function02, function03, i) { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda11
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Function1 f$3;
                public final /* synthetic */ Function0 f$4;
                public final /* synthetic */ Function0 f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3457);
                    Function0 function05 = this.f$4;
                    Function0 function06 = this.f$5;
                    CommunalHubKt.Toolbar(this.f$0, this.f$1, this.f$2, this.f$3, function05, function06, (Composer) obj, iUpdateChangedFlags);
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
            AnimatedVisibilityKt.AnimatedVisibility(z, modifier, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(2005554126, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.ToolbarButton.1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous> (CommunalHub.kt:1237)");
                    }
                    ButtonColors buttonColorsAccess$filledButtonColors = CommunalHubKt.access$filledButtonColors(composer2);
                    Dimensions.Companion.getClass();
                    PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                    final Function3 function3 = composableLambdaImpl;
                    ButtonKt.Button(function0, null, false, null, buttonColorsAccess$filledButtonColors, null, null, paddingValuesImpl, null, ComposableLambdaKt.rememberComposableLambda(167565790, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.ToolbarButton.1.1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            if ((((Number) obj6).intValue() & 17) == 16) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous>.<anonymous> (CommunalHub.kt:1242)");
                                    }
                                    Arrangement arrangement = Arrangement.INSTANCE;
                                    ButtonDefaults.INSTANCE.getClass();
                                    float f = ButtonDefaults.IconSpacing;
                                    Alignment.Companion.getClass();
                                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                    arrangement.getClass();
                                    Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw = Arrangement.m93spacedByD5KLDUw(f, horizontal);
                                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                    Modifier.Companion companion = Modifier.Companion;
                                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw, vertical, composer3, 48);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, companion);
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
                                    Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    function3.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                                    composerImpl3.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
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
            AnimatedVisibilityKt.AnimatedVisibility(!z, modifier, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(749082231, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.ToolbarButton.2
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
                    ButtonColors buttonColorsM253outlinedButtonColorsro_MJ88 = ButtonDefaults.m253outlinedButtonColorsro_MJ88(j, composer2);
                    Dp.Companion companion = Dp.Companion;
                    BorderStroke borderStrokeM31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U((float) 2.0d, colorScheme2.primary);
                    Dimensions.Companion.getClass();
                    PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                    final Function3 function3 = composableLambdaImpl;
                    ButtonKt.OutlinedButton(function0, null, false, null, buttonColorsM253outlinedButtonColorsro_MJ88, null, borderStrokeM31BorderStrokecXLIe8U, paddingValuesImpl, null, ComposableLambdaKt.rememberComposableLambda(-2074918971, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.ToolbarButton.2.1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            if ((((Number) obj6).intValue() & 17) == 16) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ToolbarButton.<anonymous>.<anonymous> (CommunalHub.kt:1264)");
                                    }
                                    Arrangement arrangement = Arrangement.INSTANCE;
                                    ButtonDefaults.INSTANCE.getClass();
                                    float f = ButtonDefaults.IconSpacing;
                                    Alignment.Companion.getClass();
                                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                    arrangement.getClass();
                                    Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw = Arrangement.m93spacedByD5KLDUw(f, horizontal);
                                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                    Modifier.Companion companion2 = Modifier.Companion;
                                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw, vertical, composer3, 48);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, companion2);
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
                                    Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    function3.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                                    composerImpl3.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda12(z, function0, modifier, composableLambdaImpl, i, 0);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    CommunalHubKt.TutorialContent(modifier2, (Composer) obj, iUpdateChangedFlags);
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
            final String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_action_label_umo_show_next, composerImpl);
            final String strStringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_umo_show_previous, composerImpl);
            Modifier modifierThen = !baseCommunalViewModel.isEditMode() ? modifier.then(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                    SemanticsPropertiesKt.setCustomActions((SemanticsPropertyReceiver) obj, Arrays.asList(new CustomAccessibilityAction(strStringResource, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1.1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            baseCommunalViewModel2.onShowNextMedia();
                            return Boolean.TRUE;
                        }
                    }), new CustomAccessibilityAction(strStringResource2, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            baseCommunalViewModel2.onShowPreviousMedia();
                            return Boolean.TRUE;
                        }
                    })));
                    return Unit.INSTANCE;
                }
            })) : modifier;
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
            composerImpl.startReplaceGroup(-1542599443);
            UmoLegacy(baseCommunalViewModel, modifier, composerImpl, ((i2 >> 3) & 112) | (i2 & 14));
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda5(baseCommunalViewModel, contentScope, modifier, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void UmoLegacy(final BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1911438308);
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
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.UmoLegacy (CommunalHub.kt:1778)");
            }
            Modifier modifierClip = ClipKt.clip(modifier, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(R.dimen.notification_corner_radius, composerImpl)));
            MaterialTheme.INSTANCE.getClass();
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierClip, MaterialTheme.getColorScheme(composerImpl).primary, RectangleShapeKt.RectangleShape);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(67512566);
            boolean zChangedInstance = composerImpl.changedInstance(baseCommunalViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$UmoLegacy$1$1
                        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                            Object objDetectHorizontalDragGestures$default = DragGestureDetectorKt.detectHorizontalDragGestures$default(pointerInputScope, null, null, new CommunalHubKt$$ExternalSyntheticLambda9(baseCommunalViewModel), continuation, 7);
                            return objDetectHorizontalDragGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectHorizontalDragGestures$default : Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierM26backgroundbw27NRU, unit, (PointerInputEventHandler) objRememberedValue);
                composerImpl.startReplaceGroup(67535778);
                boolean zChangedInstance2 = composerImpl.changedInstance(baseCommunalViewModel);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new CommunalHubKt$$ExternalSyntheticLambda18(baseCommunalViewModel, 3);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function1 function1 = (Function1) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(67547115);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    companion.getClass();
                    if (objRememberedValue3 == Composer.Companion.Empty) {
                        objRememberedValue3 = new CommunalHubKt$$ExternalSyntheticLambda34(1);
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    AndroidView_androidKt.AndroidView(function1, modifierPointerInput, (Function1) objRememberedValue3, null, null, composerImpl, 384, 24);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda24(baseCommunalViewModel, modifier, i, 1);
        }
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
            ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
            DensityUtils.Companion.getClass();
            AnimatedVisibilityKt.AnimatedVisibility(z, PaddingKt.m125padding3ABfNKs(modifier, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(16)), enterTransitionFadeIn$default, exitTransitionFadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(-635566206, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt.WidgetConfigureButton.1
                /* JADX WARN: Removed duplicated region for block: B:9:0x007a  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.WidgetConfigureButton.<anonymous> (CommunalHub.kt:1614)");
                    }
                    DensityUtils.Companion.getClass();
                    RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(16));
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(48));
                    ColorScheme colorScheme2 = colorScheme;
                    long j = colorScheme2.primary;
                    Color.Companion.getClass();
                    long j2 = Color.Transparent;
                    IconButtonColors iconButtonColors = new IconButtonColors(j, colorScheme2.onPrimary, j2, j2, null);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    composerImpl2.startReplaceGroup(-899264314);
                    CoroutineScope coroutineScope2 = coroutineScope;
                    boolean zChangedInstance = composerImpl2.changedInstance(coroutineScope2);
                    WidgetConfigurator widgetConfigurator2 = widgetConfigurator;
                    boolean zChangedInstance2 = zChangedInstance | composerImpl2.changedInstance(widgetConfigurator2);
                    CommunalContentModel.WidgetContent.Widget widget2 = widget;
                    boolean zChangedInstance3 = zChangedInstance2 | composerImpl2.changedInstance(widget2);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    if (!zChangedInstance3) {
                        Composer.Companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new CommunalHubKt$CommunalHub$6$8$$ExternalSyntheticLambda0(coroutineScope2, widgetConfigurator2, 1, widget2);
                            composerImpl2.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    composerImpl2.end(false);
                    ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                    IconButtonKt.FilledIconButton(1572864, 36, null, iconButtonColors, composerImpl2, modifierM140size3ABfNKs, roundedCornerShapeM187RoundedCornerShape0680j_4, (Function0) objRememberedValue2, ComposableSingletons$CommunalHubKt.f35lambda7, false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 200064, 16);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new CommunalHubKt$$ExternalSyntheticLambda12(z, widget, modifier, widgetConfigurator, i, 1);
        }
    }

    public static final void WidgetContent(final BaseCommunalViewModel baseCommunalViewModel, final CommunalContentModel.WidgetContent.Widget widget, final SizeF sizeF, final boolean z, final WidgetConfigurator widgetConfigurator, final Modifier modifier, final int i, final ContentListState contentListState, final CommunalAppWidgetSection communalAppWidgetSection, final ResizeableItemFrameViewModel resizeableItemFrameViewModel, Composer composer, final int i2) throws Throwable {
        int i3;
        int i4;
        ResizeableItemFrameViewModel resizeableItemFrameViewModel2;
        MutableState mutableState;
        Integer numValueOf;
        CoroutineScope coroutineScope;
        boolean z2;
        MutableInteractionSource mutableInteractionSource;
        boolean z3;
        Modifier modifierM182selectableO2vRcR0;
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
            Object objRememberedValue = composerImpl3.rememberedValue();
            Composer.Companion.getClass();
            Object obj2 = Composer.Companion.Empty;
            if (objRememberedValue == obj2) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                composerImpl3.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue;
            Context context = (Context) composerImpl3.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl3.startReplaceGroup(-1361363947);
            boolean zChanged = composerImpl3.changed(widget) | composerImpl3.changed(context);
            Object objRememberedValue2 = composerImpl3.rememberedValue();
            if (zChanged || objRememberedValue2 == obj2) {
                objRememberedValue2 = StringsKt__StringsKt.trim(widget.providerInfo.loadLabel(context.getPackageManager()).toString()).toString();
                composerImpl3.updateRememberedValue(objRememberedValue2);
            }
            final String str = (String) objRememberedValue2;
            composerImpl3.end(false);
            final String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_action_label_select_widget, composerImpl3);
            final String strStringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_remove_widget, composerImpl3);
            final String strStringResource3 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_place_widget, composerImpl3);
            final String strStringResource4 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_unselect_widget, composerImpl3);
            final String strStringResource5 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_shrink_widget, composerImpl3);
            final String strStringResource6 = StringResources_androidKt.stringResource(R.string.accessibility_action_label_expand_widget, composerImpl3);
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isFocusable(), Boolean.FALSE, composerImpl3, 48);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.selectedKey, composerImpl3);
            String str2 = (String) mutableStateCollectAsStateWithLifecycle2.getValue();
            if (str2 != null) {
                ListIterator listIterator = contentListState.list.listIterator();
                int i6 = 0;
                while (true) {
                    if (!listIterator.hasNext()) {
                        mutableState = mutableStateCollectAsStateWithLifecycle2;
                        i6 = -1;
                        break;
                    } else {
                        mutableState = mutableStateCollectAsStateWithLifecycle2;
                        if (Intrinsics.areEqual(((CommunalContentModel) listIterator.next()).getKey(), str2)) {
                            break;
                        }
                        i6++;
                        mutableStateCollectAsStateWithLifecycle2 = mutableState;
                    }
                }
                numValueOf = Integer.valueOf(i6);
            } else {
                mutableState = mutableStateCollectAsStateWithLifecycle2;
                numValueOf = null;
            }
            composerImpl3.startReplaceGroup(-1361331227);
            Object objRememberedValue3 = composerImpl3.rememberedValue();
            Object obj3 = Composer.Companion.Empty;
            if (objRememberedValue3 == obj3) {
                objRememberedValue3 = InteractionSourceKt.MutableInteractionSource();
                composerImpl3.updateRememberedValue(objRememberedValue3);
            }
            MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue3;
            final Integer num = numValueOf;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, -1361329157);
            if (objM == obj3) {
                objM = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl3);
            }
            FocusRequester focusRequester = (FocusRequester) objM;
            composerImpl3.end(false);
            composerImpl3.startReplaceGroup(-1361327894);
            if (baseCommunalViewModel.isEditMode() && z) {
                Unit unit = Unit.INSTANCE;
                composerImpl3.startReplaceGroup(-1361325761);
                Object objRememberedValue4 = composerImpl3.rememberedValue();
                if (objRememberedValue4 == obj3) {
                    coroutineScope = coroutineScope2;
                    objRememberedValue4 = new CommunalHubKt$WidgetContent$1$1(focusRequester, null);
                    composerImpl3.updateRememberedValue(objRememberedValue4);
                } else {
                    coroutineScope = coroutineScope2;
                }
                z2 = false;
                composerImpl3.end(false);
                EffectsKt.LaunchedEffect(composerImpl3, unit, (Function2) objRememberedValue4);
            } else {
                coroutineScope = coroutineScope2;
                z2 = false;
            }
            composerImpl3.end(z2);
            boolean zAreEqual = Intrinsics.areEqual((String) mutableState.getValue(), widget.key);
            composerImpl3.startReplaceGroup(-1361318500);
            if (baseCommunalViewModel.isEditMode()) {
                Modifier.Companion companion = Modifier.Companion;
                composerImpl3.startReplaceGroup(-1361314747);
                boolean zChangedInstance = composerImpl3.changedInstance(baseCommunalViewModel) | composerImpl3.changedInstance(widget);
                Object objRememberedValue5 = composerImpl3.rememberedValue();
                if (zChangedInstance || objRememberedValue5 == obj3) {
                    objRememberedValue5 = new CommunalHubKt$$ExternalSyntheticLambda0(1, baseCommunalViewModel, widget);
                    composerImpl3.updateRememberedValue(objRememberedValue5);
                }
                z3 = false;
                composerImpl3.end(false);
                modifierM182selectableO2vRcR0 = SelectableKt.m182selectableO2vRcR0(companion, zAreEqual, mutableInteractionSource2, null, true, null, (Function0) objRememberedValue5);
                mutableInteractionSource = mutableInteractionSource2;
            } else {
                mutableInteractionSource = mutableInteractionSource2;
                z3 = false;
                modifierM182selectableO2vRcR0 = Modifier.Companion;
            }
            composerImpl3.end(z3);
            Modifier modifierThen = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, focusRequester), z3, mutableInteractionSource, 1).then(modifierM182selectableO2vRcR0);
            boolean zIsEditMode = baseCommunalViewModel.isEditMode();
            boolean z6 = widget.inQuietMode;
            if ((zIsEditMode || z6) ? z3 : true) {
                modifierThen = modifierThen.then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, Unit.INSTANCE, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$2$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        final CommunalContentModel.WidgetContent.Widget widget2 = widget;
                        Object objObserveTaps$default = PointerInputScopeExtKt.observeTaps$default(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$2$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj4) {
                                long j = ((Offset) obj4).packedValue;
                                CommunalContentModel.WidgetContent.Widget widget3 = widget2;
                                baseCommunalViewModel2.onTapWidget(widget3.rank, widget3.componentName);
                                return Unit.INSTANCE;
                            }
                        }, continuation, 3);
                        return objObserveTaps$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objObserveTaps$default : Unit.INSTANCE;
                    }
                }));
            }
            if (!baseCommunalViewModel.isEditMode() && z6) {
                modifierThen = modifierThen.then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, Unit.INSTANCE, new PointerInputEventHandler() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$3$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        Object objObserveTaps$default = PointerInputScopeExtKt.observeTaps$default(pointerInputScope, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$3$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj4) {
                                long j = ((Offset) obj4).packedValue;
                                baseCommunalViewModel2.onOpenEnableWorkProfileDialog();
                                return Unit.INSTANCE;
                            }
                        }, continuation, 1);
                        return objObserveTaps$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objObserveTaps$default : Unit.INSTANCE;
                    }
                }));
            }
            if (baseCommunalViewModel.isEditMode()) {
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifier2 = modifierThen;
                obj = obj3;
                composerImpl = composerImpl3;
                final int i7 = i4;
                final ResizeableItemFrameViewModel resizeableItemFrameViewModel3 = resizeableItemFrameViewModel2;
                final CoroutineScope coroutineScope3 = coroutineScope;
                th = null;
                Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj4;
                        String str3 = strStringResource;
                        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, str3, null);
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                        final ContentListState contentListState2 = contentListState;
                        final int i8 = i7;
                        List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(new CustomAccessibilityAction(strStringResource2, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1$deleteAction$1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                int i9 = i8;
                                ContentListState contentListState3 = contentListState2;
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
                            ((ArrayList) listMutableListOf).add(new CustomAccessibilityAction(strStringResource5, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.1

                                /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1$1$1, reason: invalid class name and collision with other inner class name */
                                final class C01681 extends SuspendLambda implements Function2 {
                                    final /* synthetic */ ResizeableItemFrameViewModel $resizeableItemFrameViewModel;
                                    int label;

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    public C01681(ResizeableItemFrameViewModel resizeableItemFrameViewModel, Continuation continuation) {
                                        super(2, continuation);
                                        this.$resizeableItemFrameViewModel = resizeableItemFrameViewModel;
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Continuation create(Object obj, Continuation continuation) {
                                        return new C01681(this.$resizeableItemFrameViewModel, continuation);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        return ((C01681) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        Object objSnapTo;
                                        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i = this.label;
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.$resizeableItemFrameViewModel;
                                            this.label = 1;
                                            Integer nextAnchor = ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel.bottomDragState, true);
                                            AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel.topDragState;
                                            if (nextAnchor == null && ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, false) == null) {
                                                objSnapTo = Unit.INSTANCE;
                                            } else {
                                                Integer nextAnchor2 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, false);
                                                if (nextAnchor2 != null) {
                                                    objSnapTo = AnchoredDraggableKt.snapTo(anchoredDraggableState, nextAnchor2, this);
                                                    if (objSnapTo != obj2) {
                                                        objSnapTo = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    AnchoredDraggableState anchoredDraggableState2 = resizeableItemFrameViewModel.bottomDragState;
                                                    Integer nextAnchor3 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState2, true);
                                                    if (nextAnchor3 == null || (objSnapTo = AnchoredDraggableKt.snapTo(anchoredDraggableState2, new Integer(nextAnchor3.intValue()), this)) != obj2) {
                                                        objSnapTo = Unit.INSTANCE;
                                                    }
                                                }
                                            }
                                            if (objSnapTo == obj2) {
                                                return obj2;
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
                                    BuildersKt.launch$default(coroutineScope4, null, null, new C01681(resizeableItemFrameViewModel4, null), 3);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        if (ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel4.bottomDragState, false) != null || ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true) != null) {
                            ((ArrayList) listMutableListOf).add(new CustomAccessibilityAction(strStringResource6, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.2

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
                                        Object objSnapTo;
                                        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        int i = this.label;
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.$resizeableItemFrameViewModel;
                                            this.label = 1;
                                            Integer nextAnchor = ResizeableItemFrameViewModel.getNextAnchor(resizeableItemFrameViewModel.bottomDragState, false);
                                            AnchoredDraggableState anchoredDraggableState = resizeableItemFrameViewModel.topDragState;
                                            if (nextAnchor == null && ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true) == null) {
                                                objSnapTo = Unit.INSTANCE;
                                            } else {
                                                AnchoredDraggableState anchoredDraggableState2 = resizeableItemFrameViewModel.bottomDragState;
                                                Integer nextAnchor2 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState2, false);
                                                if (nextAnchor2 != null) {
                                                    objSnapTo = AnchoredDraggableKt.snapTo(anchoredDraggableState2, nextAnchor2, this);
                                                    if (objSnapTo != obj2) {
                                                        objSnapTo = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    Integer nextAnchor3 = ResizeableItemFrameViewModel.getNextAnchor(anchoredDraggableState, true);
                                                    if (nextAnchor3 == null || (objSnapTo = AnchoredDraggableKt.snapTo(anchoredDraggableState, new Integer(nextAnchor3.intValue()), this)) != obj2) {
                                                        objSnapTo = Unit.INSTANCE;
                                                    }
                                                }
                                            }
                                            if (objSnapTo == obj2) {
                                                return obj2;
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
                                    BuildersKt.launch$default(coroutineScope4, null, null, new AnonymousClass1(resizeableItemFrameViewModel4, null), 3);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        final Integer num2 = num;
                        final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                        if (num2 != null && num2.intValue() != i8) {
                            ((ArrayList) listMutableListOf).add(new CustomAccessibilityAction(strStringResource3, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.3
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    Integer num3 = num2;
                                    num3.getClass();
                                    int iIntValue = num3.intValue();
                                    ContentListState contentListState3 = contentListState2;
                                    SnapshotStateList snapshotStateList = contentListState3.list;
                                    snapshotStateList.add(i8, snapshotStateList.remove(iIntValue));
                                    ContentListState.onSaveList$default(contentListState3);
                                    baseCommunalViewModel2.setSelectedKey(null);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        if (z) {
                            ((ArrayList) listMutableListOf).add(new CustomAccessibilityAction(strStringResource4, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.5
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    baseCommunalViewModel2.setSelectedKey(null);
                                    return Boolean.TRUE;
                                }
                            }));
                        } else {
                            final CommunalContentModel.WidgetContent.Widget widget2 = widget;
                            ((ArrayList) listMutableListOf).add(new CustomAccessibilityAction(str3, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetContent$4$1.4
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    baseCommunalViewModel2.setSelectedKey(widget2.key);
                                    return Boolean.TRUE;
                                }
                            }));
                        }
                        SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, listMutableListOf);
                        return Unit.INSTANCE;
                    }
                };
                z4 = false;
                modifierThen = modifier2.then(SemanticsModifierKt.semantics(companion2, false, function1));
            } else {
                obj = obj3;
                composerImpl = composerImpl3;
                z4 = false;
                th = null;
            }
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, z4);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            ComposerImpl composerImpl4 = composerImpl;
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierThen);
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
            Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl4.startReplaceGroup(539478186);
            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue();
            composerImpl4.startReplaceGroup(-781249286);
            boolean zChangedInstance2 = composerImpl4.changedInstance(baseCommunalViewModel) | composerImpl4.changedInstance(widget);
            Object objRememberedValue6 = composerImpl4.rememberedValue();
            if (zChangedInstance2 || objRememberedValue6 == obj) {
                objRememberedValue6 = new CommunalHubKt$$ExternalSyntheticLambda0(2, baseCommunalViewModel, widget);
                composerImpl4.updateRememberedValue(objRememberedValue6);
            }
            Function0 function02 = (Function0) objRememberedValue6;
            composerImpl4.end(z4);
            Modifier.Companion companion3 = Modifier.Companion;
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion3, 1.0f);
            if (baseCommunalViewModel.isEditMode()) {
                modifierFillMaxSize = modifierFillMaxSize.then(SuspendingPointerInputFilterKt.pointerInput(modifierFillMaxSize, Unit.INSTANCE, ModifierExtKt$allowGestures$1.INSTANCE));
            }
            communalAppWidgetSection.Widget(zBooleanValue, function02, widget, sizeF, modifierFillMaxSize, composerImpl4, (i5 << 3) & 8064);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda31
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) throws Throwable {
                    ((Integer) obj5).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    CommunalAppWidgetSection communalAppWidgetSection2 = communalAppWidgetSection;
                    ResizeableItemFrameViewModel resizeableItemFrameViewModel4 = resizeableItemFrameViewModel;
                    CommunalHubKt.WidgetContent(baseCommunalViewModel, widget, sizeF, z, widgetConfigurator, modifier, i, contentListState, communalAppWidgetSection2, resizeableItemFrameViewModel4, (Composer) obj4, iUpdateChangedFlags);
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
        ButtonColors buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return buttonColorsM252buttonColorsro_MJ88;
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
    public static final long m1082access$nonScalableTextSize8Feqmps(float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-647122957);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.nonScalableTextSize (CommunalHub.kt:1866)");
        }
        long jMo60toSp0xMU5do = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo60toSp0xMU5do(f);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jMo60toSp0xMU5do;
    }

    public static final float dp(CommunalContentSize.FixedSize fixedSize) {
        int i = WhenMappings.$EnumSwitchMapping$0[fixedSize.ordinal()];
        if (i == 1) {
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            return DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530);
        }
        if (i == 2) {
            Dimensions.Companion.getClass();
            DensityUtils.Companion.getClass();
            float fM1091getAdjustedDpu2uoSUM = DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530) - Dimensions.Companion.m1084getItemSpacingD9Ej5fM();
            Dp.Companion companion = Dp.Companion;
            return fM1091getAdjustedDpu2uoSUM / 2;
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        Dimensions.Companion.getClass();
        DensityUtils.Companion.getClass();
        float fM1091getAdjustedDpu2uoSUM2 = DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530);
        float fM1084getItemSpacingD9Ej5fM = 2 * Dimensions.Companion.m1084getItemSpacingD9Ej5fM();
        Dp.Companion companion2 = Dp.Companion;
        return (fM1091getAdjustedDpu2uoSUM2 - fM1084getItemSpacingD9Ej5fM) / 3;
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
