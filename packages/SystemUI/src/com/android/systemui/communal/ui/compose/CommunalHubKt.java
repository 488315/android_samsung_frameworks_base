package com.android.systemui.communal.ui.compose;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterExitTransitionKt$slideInVertically$1;
import androidx.compose.animation.EnterExitTransitionKt$slideOutVertically$1;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.EnterTransitionImpl;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.ExitTransitionImpl;
import androidx.compose.animation.Fade;
import androidx.compose.animation.TransitionData;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.animation.core.TwoWayConverterImpl;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.Arrangement$Start$1;
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
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.grid.LazyGridStateKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.WidgetsKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.StateListIterator;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorMatrix;
import androidx.compose.ui.graphics.ColorMatrixColorFilter;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOriginKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesAndroid;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsProperties_androidKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import androidx.compose.ui.window.AndroidPopup_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.window.layout.WindowMetricsCalculator;
import androidx.window.layout.WindowMetricsCalculatorCompat;
import com.android.compose.animation.Easings;
import com.android.compose.animation.Easings$fromInterpolator$1;
import com.android.compose.animation.ExpandableKt$$ExternalSyntheticOutline0;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.compose.ui.graphics.painter.DrawablePainterKt;
import com.android.systemui.R;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.PopupType;
import com.android.systemui.communal.widgets.SmartspaceAppWidgetHostView;
import com.android.systemui.communal.widgets.WidgetConfigurationController;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class CommunalHubKt {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CommunalContentSize.values().length];
            try {
                iArr[CommunalContentSize.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CommunalContentSize.HALF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CommunalContentSize.THIRD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void AccessibilityContainer(final BaseCommunalViewModel baseCommunalViewModel, final Function2 function2, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-325917450);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isFocusable(), Boolean.FALSE, composerImpl, 56);
        Modifier.Companion companion = Modifier.Companion;
        Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion, 1.0f), false, 3);
        if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue() && !baseCommunalViewModel.isEditMode()) {
            wrapContentHeight$default = wrapContentHeight$default.then(SemanticsModifierKt.semantics(FocusableKt.focusable$default(companion, ((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue(), 2), false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, context.getString(R.string.accessibility_content_description_for_communal_hub));
                    String string = context.getString(R.string.accessibility_action_label_close_communal_hub);
                    final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                    CustomAccessibilityAction customAccessibilityAction = new CustomAccessibilityAction(string, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BaseCommunalViewModel baseCommunalViewModel3 = BaseCommunalViewModel.this;
                            ((CommunalSceneRepositoryImpl) baseCommunalViewModel3.communalSceneInteractor.communalSceneRepository).changeScene(CommunalScenes.Blank, null);
                            return Boolean.TRUE;
                        }
                    });
                    String string2 = context.getString(R.string.accessibility_action_label_edit_widgets);
                    final BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel;
                    SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, CollectionsKt__CollectionsKt.listOf(customAccessibilityAction, new CustomAccessibilityAction(string2, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$1$1.2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BaseCommunalViewModel.onOpenWidgetEditor$default(BaseCommunalViewModel.this, null, false, 3);
                            return Boolean.TRUE;
                        }
                    })));
                    return Unit.INSTANCE;
                }
            }));
        }
        Alignment.Companion.getClass();
        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, wrapContentHeight$default);
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
        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function22);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
        function2.invoke(composerImpl, Integer.valueOf((i >> 3) & 14));
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$AccessibilityContainer$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.AccessibilityContainer(BaseCommunalViewModel.this, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$4, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$2, kotlin.jvm.internal.Lambda] */
    public static final void CommunalHub(Modifier modifier, final BaseCommunalViewModel baseCommunalViewModel, RemoteViews.InteractionHandler interactionHandler, SystemUIDialogFactory systemUIDialogFactory, WidgetConfigurator widgetConfigurator, Function0 function0, Function0 function02, Composer composer, final int i, final int i2) {
        PaddingValuesImpl m101PaddingValuesa9UjIt4$default;
        LazyGridState lazyGridState;
        ?? r13;
        EnterTransitionImpl slideInVertically;
        ExitTransitionImpl slideOutVertically;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1300785933);
        Modifier modifier2 = (i2 & 1) != 0 ? Modifier.Companion : modifier;
        RemoteViews.InteractionHandler interactionHandler2 = (i2 & 4) != 0 ? null : interactionHandler;
        SystemUIDialogFactory systemUIDialogFactory2 = (i2 & 8) != 0 ? null : systemUIDialogFactory;
        final WidgetConfigurator widgetConfigurator2 = (i2 & 16) != 0 ? null : widgetConfigurator;
        Function0 function03 = (i2 & 32) != 0 ? null : function0;
        Function0 function04 = (i2 & 64) != 0 ? null : function02;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.getCommunalContent(), EmptyList.INSTANCE, composerImpl, 56);
        MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.getCurrentPopup(), null, composerImpl, 56);
        composerImpl.startReplaceGroup(1017042712);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Object obj = Composer.Companion.Empty;
        if (rememberedValue == obj) {
            rememberedValue = SnapshotStateKt.mutableStateOf(null, StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        Object m = ExpandableKt$$ExternalSyntheticOutline0.m(composerImpl, false, 1017042779);
        if (m == obj) {
            m = SnapshotStateKt.mutableStateOf(null, StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(m);
        }
        final MutableState mutableState2 = (MutableState) m;
        Object m2 = ExpandableKt$$ExternalSyntheticOutline0.m(composerImpl, false, 1017042860);
        if (m2 == obj) {
            m2 = SnapshotStateKt.mutableStateOf(null, StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(m2);
        }
        final MutableState mutableState3 = (MutableState) m2;
        composerImpl.end(false);
        LazyGridState rememberLazyGridState = LazyGridStateKt.rememberLazyGridState(composerImpl);
        List list = (List) collectAsStateWithLifecycle.getValue();
        composerImpl.startReplaceGroup(-1560096318);
        composerImpl.startReplaceGroup(1447450953);
        boolean changed = composerImpl.changed(list);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == obj) {
            rememberedValue2 = new ContentListState(list, new Function3() { // from class: com.android.systemui.communal.ui.compose.ContentListStateKt$rememberContentListState$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    int intValue = ((Number) obj4).intValue();
                    BaseCommunalViewModel baseCommunalViewModel2 = BaseCommunalViewModel.this;
                    WidgetConfigurator widgetConfigurator3 = widgetConfigurator2;
                    ((CommunalWidgetRepositoryImpl) baseCommunalViewModel2.communalInteractor.widgetRepository).addWidget((ComponentName) obj2, (UserHandle) obj3, intValue, widgetConfigurator3);
                    return Unit.INSTANCE;
                }
            }, new ContentListStateKt$rememberContentListState$1$2(baseCommunalViewModel), new ContentListStateKt$rememberContentListState$1$3(baseCommunalViewModel));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        final ContentListState contentListState = (ContentListState) rememberedValue2;
        composerImpl.end(false);
        composerImpl.end(false);
        final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.getReorderingWidgets(), composerImpl);
        final MutableState collectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel._selectedKey, composerImpl);
        composerImpl.startReplaceGroup(1017043230);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == obj) {
            rememberedValue3 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$removeButtonEnabled$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(State.this.getValue() != null || ((Boolean) collectAsStateWithLifecycle3.getValue()).booleanValue());
                }
            });
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        final State state = (State) rememberedValue3;
        composerImpl.end(false);
        Flow isEmptyState = baseCommunalViewModel.isEmptyState();
        Boolean bool = Boolean.FALSE;
        final MutableState collectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(isEmptyState, bool, composerImpl, 56);
        final MutableState collectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(baseCommunalViewModel.isCommunalContentVisible(), Boolean.valueOf(!baseCommunalViewModel.isEditMode()), composerImpl, 8);
        boolean isEditMode = baseCommunalViewModel.isEditMode();
        IntSize intSize = (IntSize) mutableState2.getValue();
        composerImpl.startReplaceGroup(189392001);
        if (!isEditMode || intSize == null) {
            Dimensions.INSTANCE.getClass();
            float f = Dimensions.ItemSpacing;
            m101PaddingValuesa9UjIt4$default = PaddingKt.m101PaddingValuesa9UjIt4$default(f, Dimensions.GridTopSpacing, f, 0.0f, 8);
            composerImpl.end(false);
        } else {
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            WindowMetricsCalculator.Companion.getClass();
            float mo58toDpu2uoSUM = density.mo58toDpu2uoSUM(((WindowMetricsCalculatorCompat) WindowMetricsCalculator.Companion.getOrCreate()).computeCurrentWindowMetrics(context)._bounds.toRect().height());
            Dimensions.INSTANCE.getClass();
            float mo58toDpu2uoSUM2 = density.mo58toDpu2uoSUM((int) (intSize.packedValue & 4294967295L)) + Dimensions.ToolbarPaddingTop;
            Dp.Companion companion = Dp.Companion;
            float f2 = ((Dp) RangesKt___RangesKt.coerceAtLeast(Dp.m738boximpl((((mo58toDpu2uoSUM - mo58toDpu2uoSUM2) - Dimensions.GridHeight) + Dimensions.GridTopSpacing) / 2), Dp.m738boximpl(Dimensions.Spacing))).value;
            float f3 = Dimensions.ToolbarPaddingHorizontal;
            m101PaddingValuesa9UjIt4$default = PaddingKt.m100PaddingValuesa9UjIt4(f3, mo58toDpu2uoSUM2 + f2, f3, f2);
            composerImpl = composerImpl;
            composerImpl.end(false);
        }
        final PaddingValuesImpl paddingValuesImpl = m101PaddingValuesa9UjIt4$default;
        composerImpl.startReplaceGroup(1906284573);
        Density density2 = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        ContentPaddingInPx contentPaddingInPx = new ContentPaddingInPx(density2.mo61toPx0680j_4(paddingValuesImpl.mo90calculateLeftPaddingu2uoSUM(LayoutDirection.Ltr)), density2.mo61toPx0680j_4(paddingValuesImpl.top));
        composerImpl.end(false);
        final long Offset = OffsetKt.Offset(contentPaddingInPx.start, contentPaddingInPx.top);
        composerImpl.startReplaceGroup(1017043742);
        if (!baseCommunalViewModel.isEditMode()) {
            ScrollOnUpdatedLiveContentEffect((List) collectAsStateWithLifecycle.getValue(), rememberLazyGridState, composerImpl, 8);
        }
        composerImpl.end(false);
        Modifier testTag = TestTagKt.testTag(SemanticsModifierKt.semantics(modifier2, false, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                KProperty[] kPropertyArr = SemanticsProperties_androidKt.$$delegatedProperties;
                SemanticsPropertiesAndroid.INSTANCE.getClass();
                SemanticsPropertyKey semanticsPropertyKey = SemanticsPropertiesAndroid.TestTagsAsResourceId;
                KProperty kProperty = SemanticsProperties_androidKt.$$delegatedProperties[0];
                semanticsPropertyKey.setValue((SemanticsPropertyReceiver) obj2, Boolean.TRUE);
                return Unit.INSTANCE;
            }
        }), "communal_hub");
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        Modifier then = testTag.then(fillElement);
        Object[] objArr = {rememberLazyGridState, Offset.m320boximpl(Offset), contentListState};
        CommunalHubKt$CommunalHub$2 communalHubKt$CommunalHub$2 = new CommunalHubKt$CommunalHub$2(baseCommunalViewModel, Offset, rememberLazyGridState, contentListState, null);
        PointerEvent pointerEvent = SuspendingPointerInputFilterKt.EmptyPointerEvent;
        Modifier then2 = then.then(new SuspendPointerInputElement(null, null, objArr, communalHubKt$CommunalHub$2, 3, null));
        if (baseCommunalViewModel.isEditMode() || ((Boolean) collectAsStateWithLifecycle5.getValue()).booleanValue()) {
            lazyGridState = rememberLazyGridState;
        } else {
            Modifier.Companion companion2 = Modifier.Companion;
            lazyGridState = rememberLazyGridState;
            SuspendPointerInputElement suspendPointerInputElement = new SuspendPointerInputElement(null, null, new Object[]{rememberLazyGridState, Offset.m320boximpl(Offset), (List) collectAsStateWithLifecycle.getValue(), (LayoutCoordinates) mutableState3.getValue()}, new CommunalHubKt$CommunalHub$3$1(baseCommunalViewModel, mutableState3, Offset, rememberLazyGridState, collectAsStateWithLifecycle, null), 3, null);
            companion2.getClass();
            then2 = then2.then(PointerInteropFilter_androidKt.motionEventSpy(KeyInputModifierKt.onPreviewKeyEvent(suspendPointerInputElement, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj2).nativeKeyEvent;
                    BaseCommunalViewModel.this.communalInteractor._userActivity.tryEmit(Unit.INSTANCE);
                    return Boolean.FALSE;
                }
            }), new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    SharedFlowImpl sharedFlowImpl = BaseCommunalViewModel.this.communalInteractor._userActivity;
                    Unit unit = Unit.INSTANCE;
                    sharedFlowImpl.tryEmit(unit);
                    return unit;
                }
            }));
        }
        Alignment.Companion.getClass();
        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then2);
        ComposeUiNode.Companion.getClass();
        Function0 function05 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function05);
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
        final LazyGridState lazyGridState2 = lazyGridState;
        final WidgetConfigurator widgetConfigurator3 = widgetConfigurator2;
        final RemoteViews.InteractionHandler interactionHandler3 = interactionHandler2;
        final Modifier modifier3 = modifier2;
        ComposerImpl composerImpl2 = composerImpl;
        AccessibilityContainer(baseCommunalViewModel, ComposableLambdaKt.rememberComposableLambda(273884332, composerImpl2, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Code restructure failed: missing block: B:17:0x00aa, code lost:
            
                if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
             */
            /* JADX WARN: Code restructure failed: missing block: B:21:0x00e4, code lost:
            
                if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
             */
            /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1$3, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r26, java.lang.Object r27) {
                /*
                    Method dump skipped, instructions count: 338
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }), composerImpl2, 56);
        composerImpl2.startReplaceGroup(-162563752);
        if (function03 == null || function04 == null) {
            r13 = 0;
        } else {
            boolean z = baseCommunalViewModel.isEditMode() && ((Boolean) collectAsStateWithLifecycle6.getValue()).booleanValue();
            EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
            r13 = 0;
            EnterTransitionImpl fadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 0, easingKt$$ExternalSyntheticLambda0, 2), 2);
            Easings.INSTANCE.getClass();
            Easings$fromInterpolator$1 easings$fromInterpolator$1 = Easings.Emphasized;
            slideInVertically = EnterExitTransitionKt.slideInVertically(EnterExitTransitionKt$slideInVertically$1.INSTANCE, AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2));
            EnterTransitionImpl plus = fadeIn$default.plus(slideInVertically);
            ExitTransitionImpl fadeOut$default = EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda0, 2), 2);
            slideOutVertically = EnterExitTransitionKt.slideOutVertically(EnterExitTransitionKt$slideOutVertically$1.INSTANCE, AnimationSpecKt.tween$default(1000, 0, easings$fromInterpolator$1, 2));
            ExitTransitionImpl plus2 = fadeOut$default.plus(slideOutVertically);
            final Function0 function06 = function03;
            final Function0 function07 = function04;
            AnimatedVisibilityKt.AnimatedVisibility(z, (Modifier) null, plus, plus2, (String) null, ComposableLambdaKt.rememberComposableLambda(-1595557414, composerImpl2, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    ((Number) obj4).intValue();
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    boolean booleanValue = ((Boolean) state.getValue()).booleanValue();
                    final State state2 = collectAsStateWithLifecycle4;
                    final ContentListState contentListState2 = contentListState;
                    final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                    Function0 function08 = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Integer num;
                            String str = (String) State.this.getValue();
                            if (str != null) {
                                ListIterator listIterator = contentListState2.list.listIterator();
                                int i4 = 0;
                                while (true) {
                                    StateListIterator stateListIterator = (StateListIterator) listIterator;
                                    if (!stateListIterator.hasNext()) {
                                        i4 = -1;
                                        break;
                                    }
                                    if (Intrinsics.areEqual(((CommunalContentModel) stateListIterator.next()).getKey(), str)) {
                                        break;
                                    }
                                    i4++;
                                }
                                num = Integer.valueOf(i4);
                            } else {
                                num = null;
                            }
                            if (num != null) {
                                ContentListState contentListState3 = contentListState2;
                                BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel2;
                                contentListState3.onRemove(num.intValue());
                                contentListState3.onSaveList(null, null, null);
                                baseCommunalViewModel3.setSelectedKey(null);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj3);
                    composerImpl3.startReplaceGroup(2136037927);
                    final MutableState mutableState4 = mutableState2;
                    Object rememberedValue4 = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (rememberedValue4 == composer$Companion$Empty$1) {
                        rememberedValue4 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$2$2$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj5) {
                                MutableState.this.setValue(IntSize.m757boximpl(((IntSize) obj5).packedValue));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl3.updateRememberedValue(rememberedValue4);
                    }
                    Function1 function1 = (Function1) rememberedValue4;
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(2136037998);
                    final MutableState mutableState5 = mutableState;
                    Object rememberedValue5 = composerImpl3.rememberedValue();
                    if (rememberedValue5 == composer$Companion$Empty$1) {
                        rememberedValue5 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$2$3$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj5) {
                                MutableState.this.setValue((LayoutCoordinates) obj5);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl3.updateRememberedValue(rememberedValue5);
                    }
                    composerImpl3.end(false);
                    CommunalHubKt.access$Toolbar(booleanValue, function08, function1, (Function1) rememberedValue5, Function0.this, function07, composerImpl3, 3456);
                    return Unit.INSTANCE;
                }
            }), composerImpl2, 196608, 18);
        }
        composerImpl2.end(r13);
        composerImpl2.startReplaceGroup(-162562109);
        if (Intrinsics.areEqual((PopupType) collectAsStateWithLifecycle2.getValue(), PopupType.CtaTile.INSTANCE)) {
            PopupOnDismissCtaTile(new CommunalHubKt$CommunalHub$4$3(baseCommunalViewModel), composerImpl2, r13);
        }
        composerImpl2.end(r13);
        boolean areEqual = Intrinsics.areEqual((PopupType) collectAsStateWithLifecycle2.getValue(), PopupType.CustomizeWidgetButton.INSTANCE);
        Modifier.Companion companion3 = Modifier.Companion;
        companion3.getClass();
        AnimatedVisibilityKt.AnimatedVisibility(areEqual, fillElement, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(-1648433579, composerImpl2, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                ((Number) obj4).intValue();
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                final BaseCommunalViewModel baseCommunalViewModel2 = BaseCommunalViewModel.this;
                final State state2 = collectAsStateWithLifecycle4;
                Function0 function08 = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$4.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BaseCommunalViewModel.this.onHidePopup();
                        BaseCommunalViewModel.onOpenWidgetEditor$default(BaseCommunalViewModel.this, (String) state2.getValue(), false, 2);
                        return Unit.INSTANCE;
                    }
                };
                final BaseCommunalViewModel baseCommunalViewModel3 = BaseCommunalViewModel.this;
                CommunalHubKt.access$ButtonToEditWidgets((AnimatedVisibilityScope) obj2, function08, new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$4$4.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BaseCommunalViewModel.this.onHidePopup();
                        return Unit.INSTANCE;
                    }
                }, (Composer) obj3, 8);
                return Unit.INSTANCE;
            }
        }), composerImpl2, 196656, 28);
        composerImpl2.startReplaceGroup(-162561564);
        if ((baseCommunalViewModel instanceof CommunalViewModel) && systemUIDialogFactory2 != null) {
            CommunalViewModel communalViewModel = (CommunalViewModel) baseCommunalViewModel;
            MutableState collectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isEnableWidgetDialogShowing, bool, composerImpl2, 56);
            MutableState collectAsStateWithLifecycle8 = FlowExtKt.collectAsStateWithLifecycle(communalViewModel.isEnableWorkProfileDialogShowing, bool, composerImpl2, 56);
            EnableWidgetDialogKt.EnableWidgetDialog(((Boolean) collectAsStateWithLifecycle7.getValue()).booleanValue(), systemUIDialogFactory2, StringResources_androidKt.stringResource(R.string.dialog_title_to_allow_any_widget, composerImpl2), StringResources_androidKt.stringResource(R.string.button_text_to_open_settings, composerImpl2), new CommunalHubKt$CommunalHub$4$5(baseCommunalViewModel), new CommunalHubKt$CommunalHub$4$6(baseCommunalViewModel), composerImpl2, 64);
            EnableWidgetDialogKt.EnableWidgetDialog(((Boolean) collectAsStateWithLifecycle8.getValue()).booleanValue(), systemUIDialogFactory2, StringResources_androidKt.stringResource(R.string.work_mode_off_title, composerImpl2), StringResources_androidKt.stringResource(R.string.work_mode_turn_on, composerImpl2), new CommunalHubKt$CommunalHub$4$7(baseCommunalViewModel), new CommunalHubKt$CommunalHub$4$8(baseCommunalViewModel), composerImpl2, 64);
        }
        composerImpl2.end(r13);
        Dimensions.INSTANCE.getClass();
        SpacerKt.Spacer(composerImpl2, SuspendingPointerInputFilterKt.pointerInput(SizeKt.m119width3ABfNKs(boxScopeInstance.align(SizeKt.m108height3ABfNKs(companion3, Dimensions.GridHeight), Alignment.Companion.CenterStart), Dimensions.Spacing), Unit.INSTANCE, new CommunalHubKt$CommunalHub$4$9(null)));
        composerImpl2.end(true);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
        if (endRestartGroup != null) {
            final RemoteViews.InteractionHandler interactionHandler4 = interactionHandler2;
            final SystemUIDialogFactory systemUIDialogFactory3 = systemUIDialogFactory2;
            final Function0 function08 = function03;
            final Function0 function09 = function04;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    CommunalHubKt.CommunalHub(Modifier.this, baseCommunalViewModel, interactionHandler4, systemUIDialogFactory3, widgetConfigurator3, function08, function09, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1, kotlin.jvm.internal.Lambda] */
    public static final void CtaTileInViewModeContent(final BaseCommunalViewModel baseCommunalViewModel, final Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1975660102);
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
        CardDefaults cardDefaults = CardDefaults.INSTANCE;
        long j = androidColorScheme.primary;
        cardDefaults.getClass();
        CardColors m211cardColorsro_MJ88 = CardDefaults.m211cardColorsro_MJ88(j, androidColorScheme.onPrimary, composerImpl, 0, 12);
        float f = 68;
        Dp.Companion companion = Dp.Companion;
        float f2 = 34;
        CardKt.Card(modifier, RoundedCornerShapeKt.m152RoundedCornerShapea9UjIt4(f, f2, f, f2), m211cardColorsro_MJ88, null, null, ComposableLambdaKt.rememberComposableLambda(-2139731156, composerImpl, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Composer composer2 = (Composer) obj2;
                if ((((Number) obj3).intValue() & 81) == 16) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Modifier.Companion companion2 = Modifier.Companion;
                FillElement fillElement = SizeKt.FillWholeMaxSize;
                companion2.then(fillElement);
                Dp.Companion companion3 = Dp.Companion;
                Modifier m103paddingVpY3zN4 = PaddingKt.m103paddingVpY3zN4(fillElement, 70, 38);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                AndroidColorScheme androidColorScheme2 = AndroidColorScheme.this;
                BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                Arrangement.INSTANCE.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composer2, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m103paddingVpY3zN4);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                boolean z = composerImpl3.applier instanceof Applier;
                if (!z) {
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
                Updater.m276setimpl(composer2, columnMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m276setimpl(composer2, currentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m276setimpl(composer2, materializeModifier, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                ImageVector widgets = WidgetsKt.getWidgets();
                String stringResource = StringResources_androidKt.stringResource(R.string.cta_label_to_open_widget_picker, composer2);
                Dimensions.INSTANCE.getClass();
                IconKt.m226Iconww6aTOc(widgets, stringResource, SizeKt.m115size3ABfNKs(companion2, Dimensions.IconSize), 0L, composer2, 384, 8);
                SpacerKt.Spacer(composer2, SizeKt.m115size3ABfNKs(companion2, 6));
                String stringResource2 = StringResources_androidKt.stringResource(R.string.cta_label_to_edit_widget, composer2);
                MaterialTheme.INSTANCE.getClass();
                TextStyle textStyle = MaterialTheme.getTypography(composer2).titleMedium;
                TextAlign.Companion.getClass();
                TextKt.m257Text4IGK_g(stringResource2, null, 0L, 0L, null, null, null, 0L, null, TextAlign.m705boximpl(TextAlign.Center), 0L, 0, false, 0, 0, null, textStyle, composer2, 0, 0, 65022);
                SpacerKt.Spacer(composer2, SizeKt.m115size3ABfNKs(companion2, 20));
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Center, Alignment.Companion.Top, composer2, 6);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, fillMaxWidth);
                if (!z) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function0);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy, function2);
                Updater.m276setimpl(composer2, currentCompositionLocalScope2, function22);
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                }
                Updater.m276setimpl(composer2, materializeModifier2, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                long j2 = androidColorScheme2.onPrimary;
                buttonDefaults.getClass();
                ButtonColors m209buttonColorsro_MJ88 = ButtonDefaults.m209buttonColorsro_MJ88(0L, j2, composer2, 13);
                BorderStroke m27BorderStrokecXLIe8U = BorderStrokeKt.m27BorderStrokecXLIe8U((float) 1.0d, androidColorScheme2.primaryContainer);
                PaddingValuesImpl paddingValuesImpl = Dimensions.ButtonPadding;
                CommunalHubKt$CtaTileInViewModeContent$1$1$1$1 communalHubKt$CtaTileInViewModeContent$1$1$1$1 = new CommunalHubKt$CtaTileInViewModeContent$1$1$1$1(baseCommunalViewModel2);
                ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                ButtonKt.OutlinedButton(communalHubKt$CtaTileInViewModeContent$1$1$1$1, null, false, null, m209buttonColorsro_MJ88, null, m27BorderStrokecXLIe8U, paddingValuesImpl, null, ComposableSingletons$CommunalHubKt.f32lambda7, composer2, 817889280, 302);
                SpacerKt.Spacer(composer2, SizeKt.m115size3ABfNKs(companion2, 14));
                ButtonKt.Button(new CommunalHubKt$CtaTileInViewModeContent$1$1$1$2(baseCommunalViewModel2), null, false, null, ButtonDefaults.m209buttonColorsro_MJ88(androidColorScheme2.primaryContainer, androidColorScheme2.onPrimaryContainer, composer2, 12), null, null, paddingValuesImpl, null, ComposableSingletons$CommunalHubKt.f33lambda8, composer2, 817889280, 366);
                composerImpl3.end(true);
                composerImpl3.end(true);
                return Unit.INSTANCE;
            }
        }), composerImpl, ((i >> 3) & 14) | 196608, 24);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CtaTileInViewModeContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.CtaTileInViewModeContent(BaseCommunalViewModel.this, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void DisabledWidgetPlaceholder(final CommunalContentModel.WidgetContent.DisabledWidget disabledWidget, final BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, Composer composer, final int i, final int i2) {
        Icon createWithResource;
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-959922153);
        Modifier modifier2 = (i2 & 4) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        ActivityInfo activityInfo = disabledWidget.providerInfo.providerInfo;
        ApplicationInfo applicationInfo = activityInfo != null ? activityInfo.applicationInfo : null;
        if (applicationInfo == null || (i3 = applicationInfo.icon) == 0) {
            createWithResource = Icon.createWithResource(context, android.R.drawable.sym_def_app_icon);
            Intrinsics.checkNotNull(createWithResource);
        } else {
            createWithResource = Icon.createWithResource(applicationInfo.packageName, i3);
            Intrinsics.checkNotNull(createWithResource);
        }
        MaterialTheme.INSTANCE.getClass();
        Modifier m30clickableO2vRcR0$default = ClickableKt.m30clickableO2vRcR0$default(BackgroundKt.m24backgroundbw27NRU(modifier2, MaterialTheme.getColorScheme(composerImpl).surfaceVariant, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(android.R.dimen.system_app_widget_background_radius, composerImpl))), null, null, !baseCommunalViewModel.isEditMode(), null, new CommunalHubKt$DisabledWidgetPlaceholder$1(baseCommunalViewModel), 24);
        Arrangement.INSTANCE.getClass();
        Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m30clickableO2vRcR0$default);
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
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        Painter rememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(createWithResource.loadDrawable(context), composerImpl);
        String stringResource = StringResources_androidKt.stringResource(R.string.icon_description_for_disabled_widget, composerImpl);
        Modifier.Companion companion = Modifier.Companion;
        Dimensions.INSTANCE.getClass();
        Modifier m115size3ABfNKs = SizeKt.m115size3ABfNKs(companion, Dimensions.IconSize);
        ColorFilter.Companion companion2 = ColorFilter.Companion;
        Colors.INSTANCE.getClass();
        float[] fArr = ((ColorMatrix) Colors.DisabledColorFilter$delegate.getValue()).values;
        companion2.getClass();
        ImageKt.Image(rememberDrawablePainter, stringResource, m115size3ABfNKs, null, null, 0.0f, new ColorMatrixColorFilter(fArr, (DefaultConstructorMarker) null), composerImpl, 392, 56);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$DisabledWidgetPlaceholder$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.DisabledWidgetPlaceholder(CommunalContentModel.WidgetContent.DisabledWidget.this, baseCommunalViewModel, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void HighlightedItem(final androidx.compose.ui.Modifier r9, androidx.compose.runtime.Composer r10, final int r11, final int r12) {
        /*
            androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
            r0 = 1964937909(0x751e92b5, float:2.0101525E32)
            r10.startRestartGroup(r0)
            r0 = r12 & 1
            r1 = 2
            if (r0 == 0) goto L11
            r2 = r11 | 6
        Lf:
            r7 = r2
            goto L21
        L11:
            r2 = r11 & 14
            if (r2 != 0) goto L20
            boolean r2 = r10.changed(r9)
            if (r2 == 0) goto L1d
            r2 = 4
            goto L1e
        L1d:
            r2 = r1
        L1e:
            r2 = r2 | r11
            goto Lf
        L20:
            r7 = r11
        L21:
            r2 = r7 & 11
            if (r2 != r1) goto L30
            boolean r1 = r10.getSkipping()
            if (r1 != 0) goto L2c
            goto L30
        L2c:
            r10.skipToGroupEnd()
            goto L81
        L30:
            if (r0 == 0) goto L34
            androidx.compose.ui.Modifier$Companion r9 = androidx.compose.ui.Modifier.Companion
        L34:
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            androidx.compose.material3.CardDefaults r0 = androidx.compose.material3.CardDefaults.INSTANCE
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Transparent
            r0.getClass()
            r3 = 0
            r5 = 6
            r6 = 14
            r0 = r1
            r2 = r3
            r4 = r10
            androidx.compose.material3.CardColors r2 = androidx.compose.material3.CardDefaults.m211cardColorsro_MJ88(r0, r2, r4, r5, r6)
            com.android.systemui.communal.ui.compose.Dimensions r0 = com.android.systemui.communal.ui.compose.Dimensions.INSTANCE
            r0.getClass()
            float r0 = com.android.systemui.communal.ui.compose.Dimensions.CardOutlineWidth
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.compose.theme.AndroidColorSchemeKt.LocalAndroidColorScheme
            java.lang.Object r1 = r10.consume(r1)
            com.android.compose.theme.AndroidColorScheme r1 = (com.android.compose.theme.AndroidColorScheme) r1
            long r3 = r1.tertiaryFixed
            androidx.compose.foundation.BorderStroke r4 = androidx.compose.foundation.BorderStrokeKt.m27BorderStrokecXLIe8U(r0, r3)
            r0 = 16
            float r0 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            androidx.compose.foundation.shape.RoundedCornerShape r1 = androidx.compose.foundation.shape.RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(r0)
            com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt r0 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.INSTANCE
            r0.getClass()
            androidx.compose.runtime.internal.ComposableLambdaImpl r5 = com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.f31lambda6
            r0 = 196608(0x30000, float:2.75506E-40)
            r3 = r7 & 14
            r7 = r3 | r0
            r8 = 8
            r3 = 0
            r0 = r9
            r6 = r10
            androidx.compose.material3.CardKt.Card(r0, r1, r2, r3, r4, r5, r6, r7, r8)
        L81:
            androidx.compose.runtime.RecomposeScopeImpl r10 = r10.endRestartGroup()
            if (r10 == 0) goto L8e
            com.android.systemui.communal.ui.compose.CommunalHubKt$HighlightedItem$1 r0 = new com.android.systemui.communal.ui.compose.CommunalHubKt$HighlightedItem$1
            r0.<init>()
            r10.block = r0
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.HighlightedItem(androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void PendingWidgetPlaceholder(final CommunalContentModel.WidgetContent.PendingWidget pendingWidget, final Modifier modifier, Composer composer, final int i, final int i2) {
        Icon createWithResource;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(127270360);
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Bitmap bitmap = pendingWidget.icon;
        if (bitmap != null) {
            createWithResource = Icon.createWithBitmap(bitmap);
            Intrinsics.checkNotNull(createWithResource);
        } else {
            createWithResource = Icon.createWithResource(context, android.R.drawable.sym_def_app_icon);
            Intrinsics.checkNotNull(createWithResource);
        }
        MaterialTheme.INSTANCE.getClass();
        Modifier m24backgroundbw27NRU = BackgroundKt.m24backgroundbw27NRU(modifier, MaterialTheme.getColorScheme(composerImpl).surfaceVariant, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(PrimitiveResources_androidKt.dimensionResource(android.R.dimen.system_app_widget_background_radius, composerImpl)));
        Arrangement.INSTANCE.getClass();
        Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m24backgroundbw27NRU);
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
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        Painter rememberDrawablePainter = DrawablePainterKt.rememberDrawablePainter(createWithResource.loadDrawable(context), composerImpl);
        String stringResource = StringResources_androidKt.stringResource(R.string.icon_description_for_pending_widget, composerImpl);
        Modifier.Companion companion = Modifier.Companion;
        Dimensions.INSTANCE.getClass();
        ImageKt.Image(rememberDrawablePainter, stringResource, SizeKt.m115size3ABfNKs(companion, Dimensions.IconSize), null, null, 0.0f, null, composerImpl, 392, 120);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$PendingWidgetPlaceholder$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.PendingWidgetPlaceholder(CommunalContentModel.WidgetContent.PendingWidget.this, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PopupOnDismissCtaTile(final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1384972529);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopCenter;
            long IntOffset = IntOffsetKt.IntOffset(0, 40);
            ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
            AndroidPopup_androidKt.m780PopupK5zGePQ(biasAlignment, IntOffset, function0, null, ComposableSingletons$CommunalHubKt.f30lambda5, composerImpl, ((i2 << 6) & 896) | 24630, 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$PopupOnDismissCtaTile$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.PopupOnDismissCtaTile(Function0.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ScrollOnUpdatedLiveContentEffect(final List list, final LazyGridState lazyGridState, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(483517442);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl), composerImpl);
        }
        CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        composerImpl.startReplaceGroup(-1449769961);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new ArrayList();
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, list, new CommunalHubKt$ScrollOnUpdatedLiveContentEffect$1((List) rememberedValue2, list, lazyGridState, coroutineScope, null));
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ScrollOnUpdatedLiveContentEffect$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.ScrollOnUpdatedLiveContentEffect(list, lazyGridState, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SmartspaceContent(final RemoteViews.InteractionHandler interactionHandler, final CommunalContentModel.Smartspace smartspace, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1317833478);
        if ((i2 & 4) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier modifier2 = modifier;
        AndroidView_androidKt.AndroidView(new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$SmartspaceContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SmartspaceAppWidgetHostView smartspaceAppWidgetHostView = new SmartspaceAppWidgetHostView((Context) obj);
                RemoteViews.InteractionHandler interactionHandler2 = interactionHandler;
                CommunalContentModel.Smartspace smartspace2 = smartspace;
                if (interactionHandler2 != null) {
                    smartspaceAppWidgetHostView.setInteractionHandler(interactionHandler2);
                }
                smartspaceAppWidgetHostView.updateAppWidget(smartspace2.remoteViews);
                return smartspaceAppWidgetHostView;
            }
        }, modifier2, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$SmartspaceContent$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        }, null, null, composerImpl, ((i >> 3) & 112) | 384, 24);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$SmartspaceContent$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.SmartspaceContent(interactionHandler, smartspace, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006a  */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r7v8, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$ToolbarButton$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ToolbarButton(boolean r21, final kotlin.jvm.functions.Function0 r22, androidx.compose.ui.Modifier r23, final kotlin.jvm.functions.Function3 r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.ToolbarButton(boolean, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void TutorialContent(final Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(412326112);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i3 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
            CardKt.Card(modifier, null, null, null, null, ComposableSingletons$CommunalHubKt.f26lambda10, composerImpl, (i3 & 14) | 196608, 30);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$TutorialContent$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.TutorialContent(Modifier.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void Umo(final BaseCommunalViewModel baseCommunalViewModel, final Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1155214189);
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier modifier2 = modifier;
        AndroidView_androidKt.AndroidView(new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                UniqueObjectHostView uniqueObjectHostView = BaseCommunalViewModel.this.mediaHost.hostView;
                if (uniqueObjectHostView == null) {
                    uniqueObjectHostView = null;
                }
                uniqueObjectHostView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                UniqueObjectHostView uniqueObjectHostView2 = BaseCommunalViewModel.this.mediaHost.hostView;
                if (uniqueObjectHostView2 != null) {
                    return uniqueObjectHostView2;
                }
                return null;
            }
        }, modifier2, new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        }, null, null, composerImpl, (i & 112) | 384, 24);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$Umo$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.Umo(BaseCommunalViewModel.this, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1, kotlin.jvm.internal.Lambda] */
    public static final void WidgetConfigureButton(final boolean z, final CommunalContentModel.WidgetContent.Widget widget, Modifier modifier, final WidgetConfigurator widgetConfigurator, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2015050074);
        if ((i2 & 4) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl), composerImpl);
        }
        final CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        EnterTransitionImpl fadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
        ExitTransitionImpl fadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
        Dp.Companion companion = Dp.Companion;
        AnimatedVisibilityKt.AnimatedVisibility(z, PaddingKt.m102padding3ABfNKs(modifier, 16), fadeIn$default, fadeOut$default, (String) null, ComposableLambdaKt.rememberComposableLambda(-635566206, composerImpl, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Dp.Companion companion2 = Dp.Companion;
                RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(16);
                Modifier m115size3ABfNKs = SizeKt.m115size3ABfNKs(Modifier.Companion, 48);
                AndroidColorScheme androidColorScheme2 = AndroidColorScheme.this;
                long j = androidColorScheme2.primary;
                Color.Companion.getClass();
                long j2 = Color.Transparent;
                IconButtonColors iconButtonColors = new IconButtonColors(j, androidColorScheme2.onPrimary, j2, j2, null);
                final CoroutineScope coroutineScope2 = coroutineScope;
                final WidgetConfigurator widgetConfigurator2 = widgetConfigurator;
                final CommunalContentModel.WidgetContent.Widget widget2 = widget;
                Function0 function0 = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1.1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C00751 extends SuspendLambda implements Function2 {
                        final /* synthetic */ CommunalContentModel.WidgetContent.Widget $model;
                        final /* synthetic */ WidgetConfigurator $widgetConfigurator;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C00751(WidgetConfigurator widgetConfigurator, CommunalContentModel.WidgetContent.Widget widget, Continuation continuation) {
                            super(2, continuation);
                            this.$widgetConfigurator = widgetConfigurator;
                            this.$model = widget;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C00751(this.$widgetConfigurator, this.$model, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C00751) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                WidgetConfigurator widgetConfigurator = this.$widgetConfigurator;
                                int i2 = this.$model.appWidgetId;
                                this.label = 1;
                                if (((WidgetConfigurationController) widgetConfigurator).configureWidget(i2, this) == coroutineSingletons) {
                                    return coroutineSingletons;
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

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BuildersKt.launch$default(CoroutineScope.this, null, null, new C00751(widgetConfigurator2, widget2, null), 3);
                        return Unit.INSTANCE;
                    }
                };
                ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                IconButtonKt.FilledIconButton(function0, m115size3ABfNKs, false, m151RoundedCornerShape0680j_4, iconButtonColors, null, ComposableSingletons$CommunalHubKt.f34lambda9, (Composer) obj2, 1572912, 36);
                return Unit.INSTANCE;
            }
        }), composerImpl, (i & 14) | 200064, 16);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier2 = modifier;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$WidgetConfigureButton$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.WidgetConfigureButton(z, widget, modifier2, widgetConfigurator, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0197, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x004b, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void WidgetContent(final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r19, final com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent.Widget r20, final android.util.SizeF r21, final boolean r22, final com.android.systemui.communal.widgets.WidgetConfigurator r23, androidx.compose.ui.Modifier r24, final int r25, final com.android.systemui.communal.ui.compose.ContentListState r26, androidx.compose.runtime.Composer r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.WidgetContent(com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, com.android.systemui.communal.domain.model.CommunalContentModel$WidgetContent$Widget, android.util.SizeF, boolean, com.android.systemui.communal.widgets.WidgetConfigurator, androidx.compose.ui.Modifier, int, com.android.systemui.communal.ui.compose.ContentListState, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$1, kotlin.jvm.internal.Lambda] */
    public static final void access$ButtonToEditWidgets(final AnimatedVisibilityScope animatedVisibilityScope, final Function0 function0, final Function0 function02, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1807824709);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Alignment.Companion.getClass();
        AndroidPopup_androidKt.m780PopupK5zGePQ(Alignment.Companion.TopCenter, IntOffsetKt.IntOffset(0, 40), function02, null, ComposableLambdaKt.rememberComposableLambda(-1540089086, composerImpl, new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$1$2, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                final AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl3.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
                AnimatedVisibilityScope animatedVisibilityScope2 = AnimatedVisibilityScope.this;
                Dp.Companion companion = Dp.Companion;
                Modifier graphicsLayer = GraphicsLayerModifierKt.graphicsLayer(SizeKt.m108height3ABfNKs(Modifier.Companion, 56), new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj3) {
                        ((ReusableGraphicsLayerScope) obj3).m426setTransformOrigin__ExYCQ(TransformOriginKt.TransformOrigin(0.0f, 0.0f));
                        return Unit.INSTANCE;
                    }
                });
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
                TweenSpec tween$default = AnimationSpecKt.tween$default(83, 0, easingKt$$ExternalSyntheticLambda0, 2);
                TwoWayConverterImpl twoWayConverterImpl = EnterExitTransitionKt.TransformOriginVectorConverter;
                Modifier m24backgroundbw27NRU = BackgroundKt.m24backgroundbw27NRU(animatedVisibilityScope2.animateEnterExit(graphicsLayer, new EnterTransitionImpl(new TransitionData(new Fade(0.0f, tween$default), null, null, null, false, null, 62, null)), EnterExitTransitionKt.fadeOut$default(new TweenSpec(83, 167, easingKt$$ExternalSyntheticLambda0), 2), "animateEnterExit"), androidColorScheme.secondary, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(50));
                Function0 function03 = function0;
                final AnimatedVisibilityScope animatedVisibilityScope3 = AnimatedVisibilityScope.this;
                ButtonKt.Button(function03, m24backgroundbw27NRU, false, null, null, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(2078090514, composerImpl3, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        Composer composer3 = (Composer) obj4;
                        if ((((Number) obj5).intValue() & 81) == 16) {
                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                            if (composerImpl4.getSkipping()) {
                                composerImpl4.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        AnimatedVisibilityScope animatedVisibilityScope4 = AnimatedVisibilityScope.this;
                        Modifier.Companion companion2 = Modifier.Companion;
                        EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = EasingKt.LinearEasing;
                        Modifier animateEnterExit = animatedVisibilityScope4.animateEnterExit(companion2, EnterExitTransitionKt.fadeIn$default(new TweenSpec(167, 83, easingKt$$ExternalSyntheticLambda02), 2), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(167, 0, easingKt$$ExternalSyntheticLambda02, 2), 2), "animateEnterExit");
                        AndroidColorScheme androidColorScheme2 = androidColorScheme;
                        Arrangement.INSTANCE.getClass();
                        Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                        Alignment.Companion.getClass();
                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composer3, 0);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, animateEnterExit);
                        ComposeUiNode.Companion.getClass();
                        Function0 function04 = ComposeUiNode.Companion.Constructor;
                        if (!(composerImpl5.applier instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl5.startReusableNode();
                        if (composerImpl5.inserting) {
                            composerImpl5.createNode(function04);
                        } else {
                            composerImpl5.useNode();
                        }
                        Updater.m276setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m276setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                        }
                        Updater.m276setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                        ImageVector widgets = WidgetsKt.getWidgets();
                        String stringResource = StringResources_androidKt.stringResource(R.string.button_to_configure_widgets_text, composer3);
                        long j = androidColorScheme2.onSecondary;
                        Dp.Companion companion3 = Dp.Companion;
                        IconKt.m226Iconww6aTOc(widgets, stringResource, SizeKt.m115size3ABfNKs(companion2, 20), j, composer3, 384, 0);
                        SpacerKt.Spacer(composer3, SizeKt.m115size3ABfNKs(companion2, 8));
                        String stringResource2 = StringResources_androidKt.stringResource(R.string.button_to_configure_widgets_text, composer3);
                        MaterialTheme.INSTANCE.getClass();
                        TextKt.m257Text4IGK_g(stringResource2, null, androidColorScheme2.onSecondary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer3).titleSmall, composer3, 0, 0, 65530);
                        composerImpl5.end(true);
                        return Unit.INSTANCE;
                    }
                }), composerImpl3, 805306368, 508);
                return Unit.INSTANCE;
            }
        }), composerImpl, (i & 896) | 24630, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$ButtonToEditWidgets$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.access$ButtonToEditWidgets(AnimatedVisibilityScope.this, function0, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$CommunalContent(final com.android.systemui.communal.domain.model.CommunalContentModel r18, final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r19, final android.util.SizeF r20, final boolean r21, androidx.compose.ui.Modifier r22, com.android.systemui.communal.widgets.WidgetConfigurator r23, final int r24, final com.android.systemui.communal.ui.compose.ContentListState r25, final android.widget.RemoteViews.InteractionHandler r26, androidx.compose.runtime.Composer r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.access$CommunalContent(com.android.systemui.communal.domain.model.CommunalContentModel, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, android.util.SizeF, boolean, androidx.compose.ui.Modifier, com.android.systemui.communal.widgets.WidgetConfigurator, int, com.android.systemui.communal.ui.compose.ContentListState, android.widget.RemoteViews$InteractionHandler, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x004e, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v8, types: [T, com.android.systemui.communal.ui.compose.GridDragDropState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v22, types: [com.android.systemui.communal.ui.compose.DragAndDropTargetStateKt$dragAndDropTarget$2] */
    /* JADX WARN: Type inference failed for: r9v4, types: [T, androidx.compose.runtime.snapshots.SnapshotStateList] */
    /* renamed from: access$CommunalHubLazyGrid-pnlYLlI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m941access$CommunalHubLazyGridpnlYLlI(final androidx.compose.foundation.layout.BoxScope r25, final java.util.List r26, final com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r27, final androidx.compose.foundation.layout.PaddingValues r28, final androidx.compose.runtime.State r29, final long r30, final androidx.compose.foundation.lazy.grid.LazyGridState r32, final com.android.systemui.communal.ui.compose.ContentListState r33, final kotlin.jvm.functions.Function1 r34, final kotlin.jvm.functions.Function1 r35, final com.android.systemui.communal.widgets.WidgetConfigurator r36, final android.widget.RemoteViews.InteractionHandler r37, androidx.compose.runtime.Composer r38, final int r39, final int r40) {
        /*
            Method dump skipped, instructions count: 798
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.m941access$CommunalHubLazyGridpnlYLlI(androidx.compose.foundation.layout.BoxScope, java.util.List, com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.State, long, androidx.compose.foundation.lazy.grid.LazyGridState, com.android.systemui.communal.ui.compose.ContentListState, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, com.android.systemui.communal.widgets.WidgetConfigurator, android.widget.RemoteViews$InteractionHandler, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$1, kotlin.jvm.internal.Lambda] */
    public static final void access$EmptyStateCta(final PaddingValues paddingValues, final BaseCommunalViewModel baseCommunalViewModel, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1754062866);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
        Modifier.Companion companion = Modifier.Companion;
        Dimensions.INSTANCE.getClass();
        Modifier padding = PaddingKt.padding(SizeKt.m108height3ABfNKs(companion, Dimensions.GridHeight), paddingValues);
        CardDefaults cardDefaults = CardDefaults.INSTANCE;
        Color.Companion.getClass();
        long j = Color.Transparent;
        cardDefaults.getClass();
        CardColors m211cardColorsro_MJ88 = CardDefaults.m211cardColorsro_MJ88(j, 0L, composerImpl, 6, 14);
        Dp.Companion companion2 = Dp.Companion;
        CardKt.Card(padding, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(80), m211cardColorsro_MJ88, null, BorderStrokeKt.m27BorderStrokecXLIe8U(3, androidColorScheme.secondary), ComposableLambdaKt.rememberComposableLambda(-635955488, composerImpl, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Composer composer2 = (Composer) obj2;
                if ((((Number) obj3).intValue() & 81) == 16) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Modifier.Companion companion3 = Modifier.Companion;
                FillElement fillElement = SizeKt.FillWholeMaxSize;
                companion3.then(fillElement);
                Dp.Companion companion4 = Dp.Companion;
                Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(fillElement, 110, 0.0f, 2);
                Arrangement arrangement = Arrangement.INSTANCE;
                Dimensions.INSTANCE.getClass();
                float f = Dimensions.Spacing;
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                arrangement.getClass();
                Arrangement.SpacedAligned m77spacedByD5KLDUw = Arrangement.m77spacedByD5KLDUw(f, vertical);
                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                AndroidColorScheme androidColorScheme2 = AndroidColorScheme.this;
                final BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m77spacedByD5KLDUw, horizontal, composer2, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m104paddingVpY3zN4$default);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                boolean z = composerImpl3.applier instanceof Applier;
                if (!z) {
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
                Updater.m276setimpl(composer2, columnMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m276setimpl(composer2, currentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m276setimpl(composer2, materializeModifier, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                String stringResource = StringResources_androidKt.stringResource(R.string.title_for_empty_state_cta, composer2);
                MaterialTheme.INSTANCE.getClass();
                TextStyle textStyle = MaterialTheme.getTypography(composer2).displaySmall;
                TextAlign.Companion.getClass();
                TextKt.m257Text4IGK_g(stringResource, null, androidColorScheme2.secondary, 0L, null, null, null, 0L, null, TextAlign.m705boximpl(TextAlign.Center), 0L, 0, false, 0, 0, null, textStyle, composer2, 0, 0, 65018);
                Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Center, Alignment.Companion.Top, composer2, 6);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer2, fillMaxWidth);
                if (!z) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function0);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m276setimpl(composer2, rowMeasurePolicy, function2);
                Updater.m276setimpl(composer2, currentCompositionLocalScope2, function22);
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                }
                Updater.m276setimpl(composer2, materializeModifier2, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier m108height3ABfNKs = SizeKt.m108height3ABfNKs(companion3, 56);
                ButtonDefaults.INSTANCE.getClass();
                ButtonColors m209buttonColorsro_MJ88 = ButtonDefaults.m209buttonColorsro_MJ88(androidColorScheme2.primary, androidColorScheme2.onPrimary, composer2, 12);
                Function0 function02 = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$1$1$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BaseCommunalViewModel.onOpenWidgetEditor$default(BaseCommunalViewModel.this, null, true, 1);
                        return Unit.INSTANCE;
                    }
                };
                ComposableSingletons$CommunalHubKt.INSTANCE.getClass();
                ButtonKt.Button(function02, m108height3ABfNKs, false, null, m209buttonColorsro_MJ88, null, null, null, null, ComposableSingletons$CommunalHubKt.f25lambda1, composer2, 805306416, 492);
                composerImpl3.end(true);
                composerImpl3.end(true);
                return Unit.INSTANCE;
            }
        }), composerImpl, 196608, 8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$EmptyStateCta$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CommunalHubKt.access$EmptyStateCta(PaddingValues.this, baseCommunalViewModel, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00e3, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L63;
     */
    /* JADX WARN: Type inference failed for: r0v27, types: [com.android.systemui.communal.ui.compose.CommunalHubKt$Toolbar$2$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$Toolbar(final boolean r25, final kotlin.jvm.functions.Function0 r26, final kotlin.jvm.functions.Function1 r27, final kotlin.jvm.functions.Function1 r28, final kotlin.jvm.functions.Function0 r29, final kotlin.jvm.functions.Function0 r30, androidx.compose.runtime.Composer r31, final int r32) {
        /*
            Method dump skipped, instructions count: 490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt.access$Toolbar(boolean, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }

    public static final ButtonColors access$filledButtonColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-878954106);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AndroidColorScheme androidColorScheme = (AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme);
        ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
        long j = androidColorScheme.primary;
        buttonDefaults.getClass();
        ButtonColors m209buttonColorsro_MJ88 = ButtonDefaults.m209buttonColorsro_MJ88(j, androidColorScheme.onPrimary, composerImpl, 12);
        composerImpl.end(false);
        return m209buttonColorsro_MJ88;
    }

    public static final String access$keyAtIndexIfEditable(int i, List list) {
        if (i >= 0 && i < list.size()) {
            CommunalContentModel communalContentModel = (CommunalContentModel) list.get(i);
            communalContentModel.getClass();
            if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
                return ((CommunalContentModel) list.get(i)).getKey();
            }
        }
        return null;
    }

    /* renamed from: isPointerWithinEnabledRemoveButton-_UaWb3I, reason: not valid java name */
    public static final boolean m942isPointerWithinEnabledRemoveButton_UaWb3I(boolean z, Offset offset, LayoutCoordinates layoutCoordinates) {
        if (!z || offset == null || layoutCoordinates == null) {
            return false;
        }
        return LayoutCoordinatesKt.boundsInWindow(layoutCoordinates).m336containsk4lQ0M(offset.packedValue);
    }
}
