package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimationModifierKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ClipScrollableContainerKt;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.draganddrop.DragAndDropTargetKt;
import androidx.compose.foundation.gestures.AnchoredDraggableKt;
import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.foundation.gestures.DraggableAnchorsConfig;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.IntrinsicKt;
import androidx.compose.foundation.layout.IntrinsicSize;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridIntervalContent;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridItemScope;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.grid.LazyGridStateKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.ClearKt;
import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MotionScheme$Companion$standard$1;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarColors;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draganddrop.DragAndDropEvent;
import androidx.compose.ui.draganddrop.DragAndDropTarget;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRectKt;
import androidx.compose.ui.unit.IntSize;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.gesture.effect.OffsetOverscrollEffectFactory;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.compose.gestures.EagerTapKt;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import com.android.systemui.qs.panels.ui.compose.DragAndDropState;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropRemoveZone$target$1$1;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileList$target$1$1;
import com.android.systemui.qs.panels.ui.compose.DragType;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.PlacementEvent;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import com.android.systemui.qs.panels.ui.compose.selection.SelectionKt;
import com.android.systemui.qs.panels.ui.compose.selection.TileState;
import com.android.systemui.qs.panels.ui.model.AvailableTileGridCell;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.SpacerGridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.viewmodel.AvailableEditActions;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.CategoryAndName;
import com.android.systemui.qs.shared.model.TileCategory;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public abstract class EditTileKt {
    /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AutoScrollGrid(final EditTileListState editTileListState, final ScrollState scrollState, final PaddingValues paddingValues, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1082946662);
        int i2 = (composerImpl.changed(editTileListState) ? 4 : 2) | i | (composerImpl.changed(scrollState) ? 32 : 16) | (composerImpl.changed(paddingValues) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.AutoScrollGrid (EditTile.kt:369)");
            }
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            composerImpl.startReplaceGroup(-547157769);
            boolean zChanged = composerImpl.changed(density);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Pair(Integer.valueOf(density.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM())), Integer.valueOf(density.mo52roundToPx0680j_4(paddingValues.mo110calculateBottomPaddingD9Ej5fM())));
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Pair pair = (Pair) objRememberedValue;
                composerImpl.end(false);
                final int iIntValue = ((Number) pair.component1()).intValue();
                final int iIntValue2 = ((Number) pair.component2()).intValue();
                composerImpl.startReplaceGroup(-547149845);
                boolean z = (i2 & 14) == 4;
                int i3 = i2 & 112;
                boolean zChanged2 = z | (i3 == 32) | composerImpl.changed(iIntValue) | composerImpl.changed(iIntValue2);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                long j = ((Offset) ((SnapshotMutableStateImpl) editTileListState.draggedPosition$delegate).getValue()).packedValue;
                                if ((9223372034707292159L & j) == 9205357640488583168L) {
                                    return null;
                                }
                                int iRoundToInt = MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (j & 4294967295L)));
                                if (iRoundToInt < iIntValue + 100) {
                                    return 0;
                                }
                                ScrollState scrollState2 = scrollState;
                                if (iRoundToInt > (((SnapshotMutableIntStateImpl) scrollState2.viewportSize$delegate).getIntValue() - iIntValue2) - 100) {
                                    return Integer.valueOf(scrollState2.getMaxValue());
                                }
                                return null;
                            }
                        });
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    State state = (State) objRememberedValue2;
                    composerImpl.end(false);
                    Integer num = (Integer) state.getValue();
                    composerImpl.startReplaceGroup(-547124736);
                    boolean zChanged3 = composerImpl.changed(state) | (i3 == 32);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChanged3) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new EditTileKt$AutoScrollGrid$2$1(state, scrollState, null);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        EffectsKt.LaunchedEffect(composerImpl, num, (Function2) objRememberedValue3);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(scrollState, paddingValues, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda1
                public final /* synthetic */ ScrollState f$1;
                public final /* synthetic */ PaddingValues f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ScrollState scrollState2 = this.f$1;
                    PaddingValues paddingValues2 = this.f$2;
                    EditTileKt.AutoScrollGrid(this.f$0, scrollState2, paddingValues2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x021d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AvailableTileGrid(final SnapshotStateList snapshotStateList, final MutableSelectionState mutableSelectionState, final int i, final Function1 function1, final DragAndDropState dragAndDropState, Composer composer, final int i2) throws Throwable {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-543581225);
        MutableSelectionState mutableSelectionState2 = mutableSelectionState;
        Function1 function12 = function1;
        DragAndDropState dragAndDropState2 = dragAndDropState;
        int i3 = i2 | (composerImpl.changed(mutableSelectionState2) ? 32 : 16) | (composerImpl.changed(i) ? 256 : 128) | (composerImpl.changedInstance(function12) ? 2048 : 1024) | (composerImpl.changed(dragAndDropState2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.AvailableTileGrid (EditTile.kt:592)");
            }
            ArrayList arrayList = new ArrayList(snapshotStateList.size());
            int size = snapshotStateList.size();
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.add(((AvailableTileGridCell) snapshotStateList.get(i4)).tile.category);
            }
            ArrayList arrayList2 = new ArrayList(snapshotStateList.size());
            int size2 = snapshotStateList.size();
            for (int i5 = 0; i5 < size2; i5++) {
                arrayList2.add(((AvailableTileGridCell) snapshotStateList.get(i5)).tile.label);
            }
            composerImpl.startReplaceGroup(-1045605498);
            boolean zChanged = composerImpl.changed(arrayList) | composerImpl.changed(arrayList2);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                Object obj = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Object obj2 : snapshotStateList) {
                        TileCategory category = ((CategoryAndName) obj2).getCategory();
                        Object obj3 = linkedHashMap.get(category);
                        if (obj3 == null) {
                            ArrayList arrayList3 = new ArrayList();
                            linkedHashMap.put(category, arrayList3);
                            obj3 = arrayList3;
                        }
                        ((List) obj3).add(obj2);
                    }
                    TreeMap treeMap = new TreeMap(linkedHashMap);
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(treeMap.size()));
                    for (Map.Entry entry : treeMap.entrySet()) {
                        linkedHashMap2.put(entry.getKey(), CollectionsKt___CollectionsKt.sortedWith((Iterable) entry.getValue(), new Comparator() { // from class: com.android.systemui.qs.shared.model.TileCategoryKt$groupAndSort$lambda$2$$inlined$sortedBy$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj4, Object obj5) {
                                return ComparisonsKt__ComparisonsKt.compareValues(((CategoryAndName) obj4).getName(), ((CategoryAndName) obj5).getName());
                            }
                        }));
                    }
                    composerImpl.updateRememberedValue(linkedHashMap2);
                    obj = linkedHashMap2;
                }
                Map map = (Map) obj;
                composerImpl.end(false);
                Arrangement arrangement = Arrangement.INSTANCE;
                CommonTileDefaults.INSTANCE.getClass();
                float f = CommonTileDefaults.TileArrangementPadding;
                arrangement.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
                Modifier modifierTestTag = TestTagKt.testTag(SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 3), "AvailableTilesGrid");
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, horizontal, composerImpl, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierTestTag);
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
                composerImpl.startReplaceGroup(1895261453);
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry2 = (Map.Entry) it.next();
                    TileCategory tileCategory = (TileCategory) entry2.getKey();
                    List list = (List) entry2.getValue();
                    composerImpl.startMovableGroup(1429240375, tileCategory);
                    MaterialTheme.INSTANCE.getClass();
                    int i6 = i3;
                    long j = MaterialTheme.getColorScheme(composerImpl).surface;
                    Arrangement arrangement2 = Arrangement.INSTANCE;
                    float f2 = 16;
                    Dp.Companion companion2 = Dp.Companion;
                    arrangement2.getClass();
                    Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(f2);
                    Modifier.Companion companion3 = Modifier.Companion;
                    Iterator it2 = it;
                    composerImpl.startReplaceGroup(1429246499);
                    boolean zChanged2 = composerImpl.changed(j);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new EditTileKt$$ExternalSyntheticLambda7(j, 1);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                    }
                    composerImpl.end(false);
                    Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(DrawModifierKt.drawBehind(companion3, (Function1) objRememberedValue2), f2);
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_42, Alignment.Companion.Start, composerImpl, 6);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM125padding3ABfNKs);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function02);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function22);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                    CategoryHeader(tileCategory, PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxWidth(companion3, 1.0f), 0.0f, 0.0f, 0.0f, f2, 7), composerImpl, 48);
                    composerImpl.startReplaceGroup(2007477325);
                    ArrayList arrayList4 = (ArrayList) CollectionsKt___CollectionsKt.chunked(list, i);
                    int size3 = arrayList4.size();
                    int i7 = 0;
                    while (i7 < size3) {
                        Object obj4 = arrayList4.get(i7);
                        i7++;
                        List<AvailableTileGridCell> list2 = (List) obj4;
                        Arrangement arrangement3 = Arrangement.INSTANCE;
                        CommonTileDefaults.INSTANCE.getClass();
                        float f3 = CommonTileDefaults.TileArrangementPadding;
                        arrangement3.getClass();
                        Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_43 = Arrangement.m92spacedBy0680j_4(f3);
                        Modifier modifierHeight = IntrinsicKt.height(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), IntrinsicSize.Max);
                        Alignment.Companion.getClass();
                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_43, Alignment.Companion.Top, composerImpl, 6);
                        int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierHeight);
                        ComposeUiNode.Companion.getClass();
                        Function0 function03 = ComposeUiNode.Companion.Constructor;
                        composerImpl.startReusableNode();
                        ArrayList arrayList5 = arrayList4;
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function03);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        composerImpl.startReplaceGroup(-1522499549);
                        for (AvailableTileGridCell availableTileGridCell : list2) {
                            composerImpl.startMovableGroup(-443812214, availableTileGridCell.key);
                            AvailableTileGridCell(availableTileGridCell, dragAndDropState2, mutableSelectionState2, function12, rowScopeInstance.weight(Modifier.Companion, 1.0f, true).then(SizeKt.FillWholeMaxHeight), composerImpl, ((i6 >> 9) & 112) | ((i6 << 3) & 896) | (i6 & 7168));
                            composerImpl.end(false);
                            mutableSelectionState2 = mutableSelectionState;
                            function12 = function1;
                            dragAndDropState2 = dragAndDropState;
                        }
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(-1522477908);
                        int size4 = i - list2.size();
                        for (int i8 = 0; i8 < size4; i8++) {
                            SpacerKt.Spacer(composerImpl, rowScopeInstance.weight(Modifier.Companion, 1.0f, true));
                        }
                        composerImpl.end(false);
                        composerImpl.end(true);
                        mutableSelectionState2 = mutableSelectionState;
                        function12 = function1;
                        dragAndDropState2 = dragAndDropState;
                        arrayList4 = arrayList5;
                    }
                    composerImpl.end(false);
                    composerImpl.end(true);
                    composerImpl.end(false);
                    mutableSelectionState2 = mutableSelectionState;
                    function12 = function1;
                    dragAndDropState2 = dragAndDropState;
                    i3 = i6;
                    it = it2;
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(mutableSelectionState, i, function1, dragAndDropState, i2) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda12
                public final /* synthetic */ MutableSelectionState f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ Function1 f$3;
                public final /* synthetic */ DragAndDropState f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) throws Throwable {
                    ((Integer) obj6).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    Function1 function13 = this.f$3;
                    DragAndDropState dragAndDropState3 = this.f$4;
                    EditTileKt.AvailableTileGrid(this.f$0, this.f$1, this.f$2, function13, dragAndDropState3, (Composer) obj5, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0272  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AvailableTileGridCell(AvailableTileGridCell availableTileGridCell, final DragAndDropState dragAndDropState, final MutableSelectionState mutableSelectionState, Function1 function1, final Modifier modifier, Composer composer, final int i) throws Throwable {
        Throwable th;
        String strStringResource;
        boolean z;
        int currentCompositeKeyHash;
        Function2 function2;
        int i2;
        Function2 function22;
        EditTileViewModel editTileViewModel;
        final TileColors tileColors;
        boolean z2;
        Function2 function23;
        Modifier.Companion companion;
        Modifier modifierDragAndDropTileSource;
        boolean zChanged;
        AvailableTileGridCell availableTileGridCell2;
        Function1 function12;
        boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-233206104);
        int i3 = (composerImpl.changed(availableTileGridCell) ? 4 : 2) | i;
        if ((i & 48) == 0) {
            i3 |= (i & 64) == 0 ? composerImpl.changed(dragAndDropState) : composerImpl.changedInstance(dragAndDropState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changed(mutableSelectionState) ? 256 : 128;
        }
        int i4 = i3 | (composerImpl.changedInstance(function1) ? 2048 : 1024) | (composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i4 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            availableTileGridCell2 = availableTileGridCell;
            function12 = function1;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.AvailableTileGridCell (EditTile.kt:888)");
            }
            composerImpl.startReplaceGroup(329165560);
            boolean z4 = availableTileGridCell.isAvailable;
            if (z4) {
                strStringResource = null;
                th = null;
            } else {
                th = null;
                strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_tile_already_added, composerImpl);
            }
            composerImpl.end(false);
            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z4 ? 1.0f : 0.38f, null, null, null, composerImpl, 0, 30);
            EditModeTileDefaults.INSTANCE.getClass();
            TileColors tileColorsEditTileColors = EditModeTileDefaults.editTileColors(composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement arrangement = Arrangement.INSTANCE;
            CommonTileDefaults commonTileDefaults = CommonTileDefaults.INSTANCE;
            commonTileDefaults.getClass();
            float f = CommonTileDefaults.TileStartPadding;
            BiasAlignment.Vertical vertical = Alignment.Companion.Top;
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM94spacedByD5KLDUw = Arrangement.m94spacedByD5KLDUw(f, vertical);
            composerImpl.startReplaceGroup(329182693);
            boolean zChanged2 = composerImpl.changed(stateAnimateFloatAsState);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged2) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new EditTileKt$$ExternalSyntheticLambda3(stateAnimateFloatAsState, 1);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifier, (Function1) objRememberedValue);
                composerImpl.startReplaceGroup(329185163);
                boolean zChanged3 = composerImpl.changed(strStringResource);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged3) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new EditTileKt$$ExternalSyntheticLambda6(strStringResource, 2);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierGraphicsLayer, true, (Function1) objRememberedValue2);
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM94spacedByD5KLDUw, horizontal, composerImpl, 54);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw th;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl, columnMeasurePolicy, function24);
                    Function2 function25 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function25);
                    Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting) {
                        z = z4;
                    } else {
                        z = z4;
                        if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        }
                        Function2 function27 = ComposeUiNode.Companion.SetModifier;
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function27);
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        Modifier.Companion companion3 = Modifier.Companion;
                        Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion3, 1.0f), CommonTileDefaults.TileHeight);
                        BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                        currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs);
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.useNode();
                        } else {
                            composerImpl.createNode(function0);
                        }
                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function24);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function25);
                        if (!composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function26);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function27);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        composerImpl.startReplaceGroup(-1010363474);
                        EditTileViewModel editTileViewModel2 = availableTileGridCell.tile;
                        if (z) {
                            function2 = function25;
                            i2 = i4;
                            function22 = function26;
                            editTileViewModel = editTileViewModel2;
                            composerImpl = composerImpl;
                            tileColors = tileColorsEditTileColors;
                            z2 = false;
                            function23 = function27;
                            companion = companion3;
                            modifierDragAndDropTileSource = companion;
                        } else {
                            SizedTileImpl sizedTileImpl = new SizedTileImpl(editTileViewModel2, availableTileGridCell.width);
                            DragType dragType = DragType.Add;
                            composerImpl.startReplaceGroup(-1010356106);
                            boolean z5 = (i4 & 896) == 256;
                            Object objRememberedValue3 = composerImpl.rememberedValue();
                            if (!z5) {
                                companion2.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    objRememberedValue3 = new EditTileKt$$ExternalSyntheticLambda4(mutableSelectionState, 1);
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                composerImpl.end(false);
                                i2 = i4;
                                function2 = function25;
                                function22 = function26;
                                editTileViewModel = editTileViewModel2;
                                composerImpl = composerImpl;
                                function23 = function27;
                                tileColors = tileColorsEditTileColors;
                                z2 = false;
                                companion = companion3;
                                modifierDragAndDropTileSource = DragAndDropStateKt.dragAndDropTileSource(companion, sizedTileImpl, dragAndDropState, dragType, (Function0) objRememberedValue3, composerImpl, ((i4 << 3) & 896) | 3078);
                            }
                        }
                        composerImpl.end(z2);
                        Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifierDragAndDropTileSource, 1.0f);
                        composerImpl.startReplaceGroup(-1010349470);
                        zChanged = composerImpl.changed(tileColors);
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        if (zChanged) {
                            companion2.getClass();
                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                objRememberedValue4 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$AvailableTileGridCell$3$1$1$1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return Color.m456boximpl(tileColors.background);
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue4);
                            }
                            composerImpl.end(false);
                            commonTileDefaults.getClass();
                            Modifier modifierDrawBehind = DrawModifierKt.drawBehind(ClipKt.clip(modifierFillMaxSize, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(CommonTileDefaults.InactiveCornerRadius)), new EditTileKt$$ExternalSyntheticLambda26(0, (Function0) objRememberedValue4));
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierDrawBehind);
                            composerImpl.startReusableNode();
                            if (composerImpl.inserting) {
                                composerImpl.createNode(function0);
                            } else {
                                composerImpl.useNode();
                            }
                            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function24);
                            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function2);
                            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function22);
                            }
                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function23);
                            Modifier modifierAlign = boxScopeInstance.align(companion, Alignment.Companion.Center);
                            composerImpl.startReplaceGroup(929476510);
                            int i5 = i2 & 14;
                            boolean z6 = i5 == 4;
                            Object objRememberedValue5 = composerImpl.rememberedValue();
                            if (!z6) {
                                companion2.getClass();
                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                    availableTileGridCell2 = availableTileGridCell;
                                    objRememberedValue5 = new EditTileKt$$ExternalSyntheticLambda6(availableTileGridCell2, 3);
                                    composerImpl.updateRememberedValue(objRememberedValue5);
                                } else {
                                    availableTileGridCell2 = availableTileGridCell;
                                }
                                composerImpl.end(false);
                                CommonTileKt.m2906SmallTileContent8V94_ZQ((Function1) objRememberedValue5, tileColors.icon, modifierAlign, null, true, composerImpl, 24576, 8);
                                composerImpl.end(true);
                                Icons.INSTANCE.getClass();
                                ImageVector add = AddKt.getAdd();
                                String strStringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_tile_add_action, composerImpl);
                                composerImpl.startReplaceGroup(-1010331036);
                                int i6 = i2;
                                boolean z7 = (i5 == 4) | ((i6 & 7168) == 2048) | ((i6 & 896) == 256);
                                Object objRememberedValue6 = composerImpl.rememberedValue();
                                if (!z7) {
                                    companion2.getClass();
                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                        function12 = function1;
                                        z3 = false;
                                        objRememberedValue6 = new EditTileKt$$ExternalSyntheticLambda22(function12, availableTileGridCell2, 0, mutableSelectionState);
                                        composerImpl.updateRememberedValue(objRememberedValue6);
                                    } else {
                                        function12 = function1;
                                        z3 = false;
                                    }
                                    composerImpl.end(z3);
                                    SelectionKt.StaticTileBadge(add, strStringResource2, availableTileGridCell2.isAvailable, (Function0) objRememberedValue6, composerImpl, 0);
                                    composerImpl.end(true);
                                    Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(companion, 1.0f);
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z3);
                                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxSize2);
                                    composerImpl.startReusableNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(function0);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function24);
                                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function2);
                                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function22);
                                    }
                                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function23);
                                    String str = editTileViewModel.label.text;
                                    TextOverflow.Companion.getClass();
                                    int i7 = TextOverflow.Ellipsis;
                                    TextAlign.Companion.getClass();
                                    int i8 = TextAlign.Center;
                                    MaterialTheme.INSTANCE.getClass();
                                    TextStyle textStyle = MaterialTheme.getTypography(composerImpl).labelMedium;
                                    Hyphens.Companion.getClass();
                                    TextKt.m317Text4IGK_g(str, boxScopeInstance.align(companion, Alignment.Companion.TopCenter), tileColors.label, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i8), 0L, i7, false, 2, 0, null, TextStyle.m756copyp1EtxEg$default(textStyle, 0L, 0L, null, null, 0L, 0, 0L, null, null, Hyphens.Auto, 12582911), composerImpl, 0, 3120, 54776);
                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, true)) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function26);
                    Function2 function272 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function272);
                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                    Modifier.Companion companion32 = Modifier.Companion;
                    Modifier modifierM131height3ABfNKs2 = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion32, 1.0f), CommonTileDefaults.TileHeight);
                    BiasAlignment biasAlignment2 = Alignment.Companion.TopStart;
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy4 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment2, false);
                    currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs2);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy4, function24);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope22, function25);
                    if (!composerImpl.inserting) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function26);
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier22, function272);
                        BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                        composerImpl.startReplaceGroup(-1010363474);
                        EditTileViewModel editTileViewModel22 = availableTileGridCell.tile;
                        if (z) {
                        }
                        composerImpl.end(z2);
                        Modifier modifierFillMaxSize3 = SizeKt.fillMaxSize(modifierDragAndDropTileSource, 1.0f);
                        composerImpl.startReplaceGroup(-1010349470);
                        zChanged = composerImpl.changed(tileColors);
                        Object objRememberedValue42 = composerImpl.rememberedValue();
                        if (zChanged) {
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final AvailableTileGridCell availableTileGridCell3 = availableTileGridCell2;
            final Function1 function13 = function12;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda23
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    AvailableTileGridCell availableTileGridCell4 = availableTileGridCell3;
                    Function1 function14 = function13;
                    Modifier modifier2 = modifier;
                    EditTileKt.AvailableTileGridCell(availableTileGridCell4, dragAndDropState, mutableSelectionState, function14, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CategoryHeader(TileCategory tileCategory, Modifier modifier, Composer composer, int i) {
        String strStringResource;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-476934284);
        if ((((composerImpl.changed(tileCategory) ? 4 : 2) | i) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CategoryHeader (EditTile.kt:862)");
            }
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(8), vertical, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Painter painterPainterResource = PainterResources_androidKt.painterResource(tileCategory.getIconId(), composerImpl, 0);
            MaterialTheme.INSTANCE.getClass();
            IconKt.m270Iconww6aTOc(painterPainterResource, (String) null, (Modifier) null, MaterialTheme.getColorScheme(composerImpl).onSurface, composerImpl, 48, 4);
            Text label = tileCategory.getLabel();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.load (TextExt.kt:30)");
            }
            if (label instanceof Text.Loaded) {
                strStringResource = ((Text.Loaded) label).text;
            } else {
                if (!(label instanceof Text.Resource)) {
                    throw new NoWhenBranchMatchedException();
                }
                strStringResource = StringResources_androidKt.stringResource(((Text.Resource) label).res, composerImpl);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (strStringResource == null) {
                strStringResource = "";
            }
            TextKt.m317Text4IGK_g(strStringResource, null, MaterialTheme.getColorScheme(composerImpl).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleMediumEmphasized, composerImpl, 0, 0, 65530);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 1, tileCategory, modifier);
        }
    }

    public static final void CurrentTilesGrid(final EditTileListState editTileListState, final MutableSelectionState mutableSelectionState, final int i, final int i2, final Function2 function2, final Function1 function1, final Function1 function12, Composer composer, final int i3) {
        int i4;
        CoroutineScope coroutineScope;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        ComposerImpl composerImpl;
        LazyGridState lazyGridState;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(493279580);
        int i5 = i3 | (composerImpl2.changed(editTileListState) ? 4 : 2) | (composerImpl2.changed(mutableSelectionState) ? 32 : 16) | (composerImpl2.changed(i) ? 256 : 128) | (composerImpl2.changed(i2) ? 2048 : 1024) | (composerImpl2.changedInstance(function2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl2.changedInstance(function1) ? 131072 : 65536) | (composerImpl2.changedInstance(function12) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        if ((i5 & 599187) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGrid (EditTile.kt:521)");
            }
            int i6 = i5 & 14;
            final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(editTileListState, composerImpl2);
            GridCell gridCell = (GridCell) CollectionsKt___CollectionsKt.lastOrNull(editTileListState._tiles.getReadable$runtime_release().list);
            int row = gridCell != null ? gridCell.getRow() : 0;
            CommonTileDefaults.INSTANCE.getClass();
            float f = CommonTileDefaults.TileHeight;
            float f2 = CommonTileDefaults.TileArrangementPadding;
            EditModeTileDefaults.INSTANCE.getClass();
            float f3 = EditModeTileDefaults.CurrentTilesGridPadding;
            float f4 = f + f2;
            Dp.Companion companion = Dp.Companion;
            float f5 = f4 * (row + 1);
            float f6 = 2;
            State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI((f3 * f6) + f5, null, "QSEditCurrentTilesGridHeight", composerImpl2, 384, 10);
            final LazyGridState lazyGridStateRememberLazyGridState = LazyGridStateKt.rememberLazyGridState(0, 0, composerImpl2, 3);
            composerImpl2.startReplaceGroup(247537997);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                i4 = i5;
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl((Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L)));
                composerImpl2.updateRememberedValue(objRememberedValue);
            } else {
                i4 = i5;
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl2.end(false);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue2;
            final PersistentList persistentList = editTileListState._tiles.getReadable$runtime_release().list;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).primary;
            GridCells.Fixed fixed = new GridCells.Fixed(i);
            PaddingValuesImpl paddingValuesImplM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(f3);
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(247551325);
            boolean zChanged = composerImpl2.changed(stateM8animateDpAsStateAjpBEmI);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                coroutineScope = coroutineScope2;
                z = false;
                objRememberedValue3 = new EditTileKt$$ExternalSyntheticLambda3(stateM8animateDpAsStateAjpBEmI, 0);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            } else {
                coroutineScope = coroutineScope2;
                z = false;
            }
            composerImpl2.end(z);
            Modifier modifierM28borderxT4_qwU = BorderKt.m28borderxT4_qwU(com.android.compose.modifiers.SizeKt.height(modifierFillMaxWidth, (Function1) objRememberedValue3), f6, j, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(EditModeTileDefaults.GridBackgroundCornerRadius));
            composerImpl2.startReplaceGroup(247559991);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (objRememberedValue4 == composer$Companion$Empty$1) {
                z2 = false;
                objRememberedValue4 = new EditTileKt$$ExternalSyntheticLambda4(mutableState, 0);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            } else {
                z2 = false;
            }
            final Function0 function0 = (Function0) objRememberedValue4;
            composerImpl2.end(z2);
            composerImpl2.startReplaceGroup(247561194);
            int i7 = i4 & 112;
            boolean zChanged2 = ((i4 & 3670016) == 1048576) | composerImpl2.changed(mutableStateRememberUpdatedState) | (i7 == 32);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue5 == composer$Companion$Empty$1) {
                objRememberedValue5 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        function12.mo781invoke(((EditTileListState) mutableStateRememberUpdatedState.getValue()).tileSpecs());
                        mutableSelectionState.setSelection((TileSpec) obj);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            final Function1 function13 = (Function1) objRememberedValue5;
            composerImpl2.end(false);
            int i8 = ((i4 << 9) & 7168) | 384;
            composerImpl2.startReplaceGroup(168293444);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.dragAndDropTileList (DragAndDropState.kt:125)");
            }
            composerImpl2.startReplaceGroup(1748206450);
            boolean z5 = (((i8 & 7168) ^ 3072) > 2048 && composerImpl2.changed(editTileListState)) || (i8 & 3072) == 2048;
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (z5 || objRememberedValue6 == composer$Companion$Empty$1) {
                objRememberedValue6 = new DragAndDropTarget() { // from class: com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileList$target$1$1
                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final boolean onDrop(DragAndDropEvent dragAndDropEvent) {
                        EditTileListState editTileListState2 = (EditTileListState) editTileListState;
                        SizedTile draggedCell = editTileListState2.getDraggedCell();
                        if (draggedCell == null) {
                            return false;
                        }
                        function13.mo781invoke(((EditTileViewModel) draggedCell.getTile()).tileSpec);
                        editTileListState2.onDrop();
                        return true;
                    }

                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final void onEnded(DragAndDropEvent dragAndDropEvent) {
                        ((EditTileListState) editTileListState).onDrop();
                    }

                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final void onMoved(DragAndDropEvent dragAndDropEvent) {
                        Object next;
                        int iIndexOf;
                        int i9;
                        long jAccess$toOffset = DragAndDropStateKt.access$toOffset(dragAndDropEvent);
                        EditTileListState editTileListState2 = (EditTileListState) editTileListState;
                        editTileListState2.m2905setDraggedPositionk4lQ0M(jAccess$toOffset);
                        long jM402minusMKHz9U = Offset.m402minusMKHz9U(jAccess$toOffset, ((Offset) function0.invoke()).packedValue);
                        Iterator it = ((LazyGridMeasureResult) lazyGridStateRememberLazyGridState.getLayoutInfo()).visibleItemsInfo.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) ((LazyGridItemInfo) next);
                            if (IntRectKt.toRect(IntRectKt.m860IntRectVbeCjmY(lazyGridMeasuredItem.offset, lazyGridMeasuredItem.size)).m407containsk4lQ0M(jM402minusMKHz9U)) {
                                break;
                            }
                        }
                        LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) next;
                        if (lazyGridItemInfo != null) {
                            LazyGridMeasuredItem lazyGridMeasuredItem2 = (LazyGridMeasuredItem) lazyGridItemInfo;
                            long j2 = lazyGridMeasuredItem2.offset;
                            IntOffset.Companion companion2 = IntOffset.Companion;
                            boolean z6 = lazyGridMeasuredItem2.span != 1 && ((double) Float.intBitsToFloat((int) (jM402minusMKHz9U >> 32))) > (((double) ((int) (lazyGridMeasuredItem2.size >> 32))) * 0.75d) + ((double) ((int) (j2 >> 32)));
                            SizedTile draggedCell = editTileListState2.getDraggedCell();
                            if (draggedCell == null || (iIndexOf = editTileListState2.indexOf(((EditTileViewModel) draggedCell.getTile()).tileSpec)) == (i9 = lazyGridMeasuredItem2.index)) {
                                return;
                            }
                            if (z6) {
                                i9++;
                            }
                            SnapshotStateList snapshotStateList = editTileListState2._tiles;
                            if (iIndexOf != -1) {
                                GridCell gridCell2 = (GridCell) snapshotStateList.remove(iIndexOf);
                                editTileListState2.regenerateGrid();
                                snapshotStateList.add(RangesKt___RangesKt.coerceIn(i9, 0, snapshotStateList.size()), gridCell2);
                            } else {
                                snapshotStateList.add(RangesKt___RangesKt.coerceIn(i9, 0, snapshotStateList.size()), new TileGridCell(draggedCell, 0, 0));
                            }
                            editTileListState2.regenerateGrid();
                        }
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            DragAndDropStateKt$dragAndDropTileList$target$1$1 dragAndDropStateKt$dragAndDropTileList$target$1$1 = (DragAndDropStateKt$dragAndDropTileList$target$1$1) objRememberedValue6;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1748250807);
            if (objM == composer$Companion$Empty$1) {
                z3 = true;
                objM = new DragAndDropStateKt$$ExternalSyntheticLambda0(1);
                composerImpl2.updateRememberedValue(objM);
            } else {
                z3 = true;
            }
            composerImpl2.end(false);
            Modifier modifierDragAndDropTarget = DragAndDropTargetKt.dragAndDropTarget(modifierM28borderxT4_qwU, (Function1) objM, dragAndDropStateKt$dragAndDropTileList$target$1$1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 247566761);
            if (objM2 == composer$Companion$Empty$1) {
                objM2 = new EditTileKt$$ExternalSyntheticLambda6(mutableState, 0);
                composerImpl2.updateRememberedValue(objM2);
            }
            composerImpl2.end(false);
            Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierDragAndDropTarget, (Function1) objM2);
            composerImpl2.startReplaceGroup(247571122);
            boolean zChanged3 = composerImpl2.changed(j);
            Object objRememberedValue7 = composerImpl2.rememberedValue();
            if (zChanged3 || objRememberedValue7 == composer$Companion$Empty$1) {
                z4 = false;
                objRememberedValue7 = new EditTileKt$$ExternalSyntheticLambda7(j, 0);
                composerImpl2.updateRememberedValue(objRememberedValue7);
            } else {
                z4 = false;
            }
            composerImpl2.end(z4);
            Modifier modifierTestTag = TestTagKt.testTag(DrawModifierKt.drawBehind(modifierOnGloballyPositioned, (Function1) objRememberedValue7), "CurrentTilesGrid");
            composerImpl2.startReplaceGroup(247581275);
            final CoroutineScope coroutineScope3 = coroutineScope;
            int i9 = i4;
            boolean zChangedInstance = (i6 == 4 ? z3 : false) | composerImpl2.changedInstance(persistentList) | (i7 == 32 ? z3 : false) | composerImpl2.changedInstance(coroutineScope3) | ((i9 & 7168) == 2048 ? z3 : false) | ((458752 & i9) == 131072 ? z3 : false) | composerImpl2.changed(mutableStateRememberUpdatedState);
            if ((57344 & i9) != 16384) {
                z3 = false;
            }
            boolean z6 = zChangedInstance | z3;
            Object objRememberedValue8 = composerImpl2.rememberedValue();
            if (z6 || objRememberedValue8 == composer$Companion$Empty$1) {
                composerImpl = composerImpl2;
                lazyGridState = lazyGridStateRememberLazyGridState;
                Function1 function14 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final EditTileKt$$ExternalSyntheticLambda13 editTileKt$$ExternalSyntheticLambda13 = new EditTileKt$$ExternalSyntheticLambda13(function2, mutableStateRememberUpdatedState, 0);
                        final PersistentList persistentList2 = persistentList;
                        int size = persistentList2.size();
                        EditTileKt$$ExternalSyntheticLambda6 editTileKt$$ExternalSyntheticLambda6 = new EditTileKt$$ExternalSyntheticLambda6(persistentList2, 1);
                        EditTileKt$$ExternalSyntheticLambda16 editTileKt$$ExternalSyntheticLambda16 = new EditTileKt$$ExternalSyntheticLambda16(persistentList2);
                        EditTileKt$$ExternalSyntheticLambda17 editTileKt$$ExternalSyntheticLambda17 = new EditTileKt$$ExternalSyntheticLambda17();
                        final CoroutineScope coroutineScope4 = coroutineScope3;
                        final int i10 = i2;
                        final EditTileListState editTileListState2 = editTileListState;
                        final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                        final Function1 function15 = function1;
                        ((LazyGridIntervalContent) ((LazyGridScope) obj)).items(size, editTileKt$$ExternalSyntheticLambda6, editTileKt$$ExternalSyntheticLambda16, editTileKt$$ExternalSyntheticLambda17, new ComposableLambdaImpl(1159464279, true, new Function4() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditTiles$4
                            /* JADX WARN: Removed duplicated region for block: B:23:0x0053  */
                            /* JADX WARN: Removed duplicated region for block: B:46:0x012f  */
                            @Override // kotlin.jvm.functions.Function4
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) throws Resources.NotFoundException {
                                int i11;
                                LazyGridItemScope lazyGridItemScope = (LazyGridItemScope) obj2;
                                final int iIntValue = ((Number) obj3).intValue();
                                Composer composer2 = (Composer) obj4;
                                int iIntValue2 = ((Number) obj5).intValue();
                                if ((iIntValue2 & 6) == 0) {
                                    i11 = (((ComposerImpl) composer2).changed(lazyGridItemScope) ? 4 : 2) | iIntValue2;
                                } else {
                                    i11 = iIntValue2;
                                }
                                if ((iIntValue2 & 48) == 0) {
                                    i11 |= ((ComposerImpl) composer2).changed(iIntValue) ? 32 : 16;
                                }
                                if ((i11 & 147) == 146) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTiles.<anonymous> (EditTile.kt:686)");
                                        }
                                        GridCell gridCell2 = (GridCell) persistentList2.get(iIntValue);
                                        if (gridCell2 instanceof TileGridCell) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                            composerImpl4.startReplaceGroup(-705601355);
                                            TileGridCell tileGridCell = (TileGridCell) gridCell2;
                                            TileSpec tileSpec = tileGridCell.tile.tileSpec;
                                            SizedTile draggedCell = ((EditTileListState) editTileListState2).getDraggedCell();
                                            if (draggedCell != null ? Intrinsics.areEqual(((EditTileViewModel) draggedCell.getTile()).tileSpec, tileSpec) : false) {
                                                composerImpl4.startReplaceGroup(-398787202);
                                                Modifier.Companion companion2 = Modifier.Companion;
                                                MaterialTheme.INSTANCE.getClass();
                                                long j2 = MaterialTheme.getColorScheme(composerImpl4).secondary;
                                                long jColor = ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.3f, Color.m461getColorSpaceimpl(j2));
                                                CommonTileDefaults.INSTANCE.getClass();
                                                EditTileKt.SpacerGridCell(BackgroundKt.m26backgroundbw27NRU(companion2, jColor, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(CommonTileDefaults.InactiveCornerRadius)), composerImpl4, 0);
                                                composerImpl4.end(false);
                                            } else {
                                                composerImpl4.startReplaceGroup(-398233728);
                                                Modifier.Companion companion3 = Modifier.Companion;
                                                IntOffset.Companion companion4 = IntOffset.Companion;
                                                EditTileKt.TileGridCell(tileGridCell, iIntValue, editTileListState2, mutableSelectionState2, editTileKt$$ExternalSyntheticLambda13, function15, coroutineScope4, i10, LazyGridItemScope.animateItem$default(lazyGridItemScope, companion3, new SpringSpec(0.75f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold())), 5), composerImpl4, i11 & 112);
                                                composerImpl4.end(false);
                                            }
                                            composerImpl4.end(false);
                                        } else {
                                            if (!(gridCell2 instanceof SpacerGridCell)) {
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                composerImpl5.startReplaceGroup(-705603587);
                                                composerImpl5.end(false);
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                            composerImpl6.startReplaceGroup(-705550618);
                                            Modifier.Companion companion5 = Modifier.Companion;
                                            Unit unit = Unit.INSTANCE;
                                            composerImpl6.startReplaceGroup(-705548652);
                                            final MutableSelectionState mutableSelectionState3 = mutableSelectionState2;
                                            boolean zChanged4 = ((i11 & 112) == 32) | composerImpl6.changed(mutableSelectionState3);
                                            Object objRememberedValue9 = composerImpl6.rememberedValue();
                                            if (!zChanged4) {
                                                Composer.Companion.getClass();
                                                if (objRememberedValue9 == Composer.Companion.Empty) {
                                                    objRememberedValue9 = new PointerInputEventHandler() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditTiles$4$1$1
                                                        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                            final MutableSelectionState mutableSelectionState4 = mutableSelectionState3;
                                                            final int i12 = iIntValue;
                                                            Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditTiles$4$1$1$$ExternalSyntheticLambda0
                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj6) {
                                                                    MutableSelectionState mutableSelectionState5 = mutableSelectionState4;
                                                                    if (mutableSelectionState5.getPlacementEnabled()) {
                                                                        TileSpec selection = mutableSelectionState5.getSelection();
                                                                        if (selection != null) {
                                                                            ((SnapshotMutableStateImpl) mutableSelectionState5.placementEvent$delegate).setValue(new PlacementEvent.PlaceToIndex(selection, i12));
                                                                        }
                                                                        mutableSelectionState5.setPlacementEnabled(false);
                                                                    } else if (mutableSelectionState5.getSelection() != null) {
                                                                        mutableSelectionState5.unSelect();
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }, continuation, 7);
                                                            return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl6.updateRememberedValue(objRememberedValue9);
                                                }
                                                composerImpl6.end(false);
                                                EditTileKt.SpacerGridCell(SuspendingPointerInputFilterKt.pointerInput(companion5, unit, (PointerInputEventHandler) objRememberedValue9), composerImpl6, 0);
                                                composerImpl6.end(false);
                                            }
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(function14);
                objRememberedValue8 = function14;
            } else {
                composerImpl = composerImpl2;
                lazyGridState = lazyGridStateRememberLazyGridState;
            }
            composerImpl.end(false);
            composerImpl2 = composerImpl;
            TileKt.TileLazyGrid(fixed, modifierTestTag, lazyGridState, paddingValuesImplM120PaddingValues0680j_4, (Function1) objRememberedValue8, composerImpl2, 3072);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(mutableSelectionState, i, i2, function2, function1, function12, i3) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda9
                public final /* synthetic */ MutableSelectionState f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ int f$3;
                public final /* synthetic */ Function2 f$4;
                public final /* synthetic */ Function1 f$5;
                public final /* synthetic */ Function1 f$6;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function15 = this.f$5;
                    Function1 function16 = this.f$6;
                    EditTileKt.CurrentTilesGrid(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, function15, function16, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void CurrentTilesGridHeader(EditTileListState editTileListState, final MutableSelectionState mutableSelectionState, final Function1 function1, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1162167909);
        int i2 = i | (composerImpl.changed(editTileListState) ? 4 : 2) | (composerImpl.changed(mutableSelectionState) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128);
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGridHeader (EditTile.kt:449)");
            }
            composerImpl.startReplaceGroup(-413675054);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.rememberEditModeState (EditTile.kt:421)");
            }
            composerImpl.startReplaceGroup(-1990366308);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(EditModeHeaderState.Idle);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            boolean z = true;
            Boolean boolValueOf = Boolean.valueOf(editTileListState.getDraggedCell() != null);
            Boolean boolValueOf2 = Boolean.valueOf(mutableSelectionState.getSelection() != null);
            Boolean boolValueOf3 = Boolean.valueOf(mutableSelectionState.getPlacementEnabled());
            composerImpl.startReplaceGroup(-1990359929);
            boolean z2 = (((i2 & 14) ^ 6) > 4 && composerImpl.changed(editTileListState)) || (i2 & 6) == 4;
            if ((((i2 & 112) ^ 48) <= 32 || !composerImpl.changed(mutableSelectionState)) && (i2 & 48) != 32) {
                z = false;
            }
            boolean z3 = z2 | z;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (z3 || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new EditTileKt$rememberEditModeState$1$1(editTileListState, mutableSelectionState, mutableState, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(boolValueOf, boolValueOf2, boolValueOf3, (Function2) objRememberedValue2, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            EditModeHeaderState editModeHeaderState = (EditModeHeaderState) mutableState.getValue();
            Alignment.Companion.getClass();
            AnimatedContentKt.AnimatedContent(editModeHeaderState, modifier, null, Alignment.Companion.Center, "QSEditHeader", null, ComposableLambdaKt.rememberComposableLambda(-1716391981, new Function4() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.CurrentTilesGridHeader.1
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    final EditModeHeaderState editModeHeaderState2 = (EditModeHeaderState) obj2;
                    Composer composer2 = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGridHeader.<anonymous> (EditTile.kt:458)");
                    }
                    final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                    final Function1 function12 = function1;
                    EditTileKt.EditGridHeader(48, composer2, ComposableLambdaKt.rememberComposableLambda(582084309, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.CurrentTilesGridHeader.1.1

                        /* renamed from: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$CurrentTilesGridHeader$1$1$WhenMappings */
                        public abstract /* synthetic */ class WhenMappings {
                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                            static {
                                int[] iArr = new int[EditModeHeaderState.values().length];
                                try {
                                    iArr[EditModeHeaderState.Remove.ordinal()] = 1;
                                } catch (NoSuchFieldError unused) {
                                }
                                try {
                                    iArr[EditModeHeaderState.Place.ordinal()] = 2;
                                } catch (NoSuchFieldError unused2) {
                                }
                                try {
                                    iArr[EditModeHeaderState.Idle.ordinal()] = 3;
                                } catch (NoSuchFieldError unused3) {
                                }
                                $EnumSwitchMapping$0 = iArr;
                            }
                        }

                        /* JADX WARN: Removed duplicated region for block: B:25:0x00a6  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                            Composer composer3 = (Composer) obj6;
                            if ((((Number) obj7).intValue() & 17) == 16) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGridHeader.<anonymous>.<anonymous> (EditTile.kt:459)");
                                    }
                                    int i3 = WhenMappings.$EnumSwitchMapping$0[editModeHeaderState2.ordinal()];
                                    if (i3 == 1) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        composerImpl3.startReplaceGroup(-91636688);
                                        composerImpl3.startReplaceGroup(274139833);
                                        MutableSelectionState mutableSelectionState3 = mutableSelectionState2;
                                        boolean zChanged = composerImpl3.changed(mutableSelectionState3);
                                        Function1 function13 = function12;
                                        boolean zChanged2 = zChanged | composerImpl3.changed(function13);
                                        Object objRememberedValue3 = composerImpl3.rememberedValue();
                                        if (!zChanged2) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                                objRememberedValue3 = new EditTileKt$$ExternalSyntheticLambda39(1, mutableSelectionState3, function13);
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            composerImpl3.end(false);
                                            EditTileKt.RemoveTileTarget((Function0) objRememberedValue3, composerImpl3, 0);
                                            composerImpl3.end(false);
                                        }
                                    } else if (i3 == 2) {
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        composerImpl4.startReplaceGroup(-91336546);
                                        EditTileKt.EditGridCenteredText(StringResources_androidKt.stringResource(R.string.tap_to_position_tile, composerImpl4), null, composerImpl4, 0);
                                        composerImpl4.end(false);
                                    } else {
                                        if (i3 != 3) {
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                            composerImpl5.startReplaceGroup(274137143);
                                            composerImpl5.end(false);
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                        composerImpl6.startReplaceGroup(-91172339);
                                        EditTileKt.EditGridCenteredText(StringResources_androidKt.stringResource(R.string.drag_to_rearrange_tiles, composerImpl6), null, composerImpl6, 0);
                                        composerImpl6.end(false);
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), null);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1600560, 36);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda2(editTileListState, mutableSelectionState, function1, modifier, i);
        }
    }

    public static final void DefaultEditTileGrid(final EditTileListState editTileListState, final List list, final int i, final int i2, final Modifier.Companion companion, final Function2 function2, final Function1 function1, final Function1 function12, final Function2 function22, final Function0 function0, final Function0 function02, Composer composer, final int i3) {
        int i4;
        Function1 function13;
        Function1 function14;
        boolean z;
        final Function0 function03;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1244939657);
        if ((i3 & 6) == 0) {
            i4 = (composerImpl2.changed(editTileListState) ? 4 : 2) | i3;
        } else {
            i4 = i3;
        }
        if ((i3 & 48) == 0) {
            i4 |= composerImpl2.changedInstance(list) ? 32 : 16;
        }
        if ((i3 & 384) == 0) {
            i4 |= composerImpl2.changed(i) ? 256 : 128;
        }
        if ((i3 & 3072) == 0) {
            i4 |= composerImpl2.changed(i2) ? 2048 : 1024;
        }
        if ((i3 & 24576) == 0) {
            i4 |= composerImpl2.changed(companion) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i3) == 0) {
            i4 |= composerImpl2.changedInstance(function2) ? 131072 : 65536;
        }
        if ((1572864 & i3) == 0) {
            function13 = function1;
            i4 |= composerImpl2.changedInstance(function13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            function13 = function1;
        }
        if ((12582912 & i3) == 0) {
            function14 = function12;
            i4 |= composerImpl2.changedInstance(function14) ? 8388608 : 4194304;
        } else {
            function14 = function12;
        }
        if ((i3 & 100663296) == 0) {
            i4 |= composerImpl2.changedInstance(function22) ? 67108864 : 33554432;
        }
        if ((i3 & 805306368) == 0) {
            i4 |= composerImpl2.changedInstance(function0) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        char c = composerImpl2.changedInstance(function02) ? (char) 4 : (char) 2;
        if ((i4 & 306783379) == 306783378 && (c & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid (EditTile.kt:241)");
            }
            composerImpl2.startReplaceGroup(-1397861040);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.rememberSelectionState (MutableSelectionState.kt:32)");
            }
            composerImpl2.startReplaceGroup(-1015162660);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new MutableSelectionState();
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            final MutableSelectionState mutableSelectionState = (MutableSelectionState) objRememberedValue;
            int i5 = i4;
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1889393423);
            if (function02 != null) {
                composerImpl2.startReplaceGroup(-1889392382);
                boolean zChanged = composerImpl2.changed(mutableSelectionState) | ((c & 14) == 4);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new EditTileKt$$ExternalSyntheticLambda39(0, mutableSelectionState, function02);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                function03 = (Function0) objRememberedValue2;
                z = false;
                composerImpl2.end(false);
            } else {
                z = false;
                function03 = null;
            }
            composerImpl2.end(z);
            PlacementEvent placementEvent = (PlacementEvent) ((SnapshotMutableStateImpl) mutableSelectionState.placementEvent$delegate).getValue();
            composerImpl2.startReplaceGroup(-1889386490);
            boolean zChanged2 = composerImpl2.changed(mutableSelectionState) | ((i5 & 14) == 4) | ((i5 & 458752) == 131072);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new EditTileKt$DefaultEditTileGrid$1$1(mutableSelectionState, editTileListState, function2, null);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, placementEvent, (Function2) objRememberedValue3);
            Color.Companion.getClass();
            long j = Color.Transparent;
            ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-50093261, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.DefaultEditTileGrid.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid.<anonymous> (EditTile.kt:264)");
                            }
                            EditTileKt.EditModeTopBar(function0, function03, composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2);
            final Function1 function15 = function13;
            final Function1 function16 = function14;
            composerImpl = composerImpl2;
            ScaffoldKt.m282ScaffoldTvnljyQ(null, composableLambdaImplRememberComposableLambda, null, null, null, 0, j, 0L, null, ComposableLambdaKt.rememberComposableLambda(1878196104, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.DefaultEditTileGrid.3
                /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    final PaddingValues paddingValues = (PaddingValues) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid.<anonymous> (EditTile.kt:266)");
                            }
                            ComputedProvidableCompositionLocal computedProvidableCompositionLocal = OverscrollKt.LocalOverscrollFactory;
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(-930025661);
                            MaterialTheme.INSTANCE.getClass();
                            SpringSpec springSpec = ((MotionScheme$Companion$standard$1) MaterialTheme.getMotionScheme(composerImpl4)).slowSpatialSpec;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.gesture.effect.rememberOffsetOverscrollEffectFactory (OffsetOverscrollEffect.kt:54)");
                            }
                            Object objRememberedValue4 = composerImpl4.rememberedValue();
                            Composer.Companion.getClass();
                            Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                            if (objRememberedValue4 == composer$Companion$Empty$12) {
                                objRememberedValue4 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl4);
                                composerImpl4.updateRememberedValue(objRememberedValue4);
                            }
                            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue4;
                            composerImpl4.startReplaceGroup(1104260892);
                            boolean zChanged3 = composerImpl4.changed(coroutineScope) | composerImpl4.changed(springSpec);
                            Object objRememberedValue5 = composerImpl4.rememberedValue();
                            if (zChanged3 || objRememberedValue5 == composer$Companion$Empty$12) {
                                objRememberedValue5 = new OffsetOverscrollEffectFactory(coroutineScope, springSpec);
                                composerImpl4.updateRememberedValue(objRememberedValue5);
                            }
                            OffsetOverscrollEffectFactory offsetOverscrollEffectFactory = (OffsetOverscrollEffectFactory) objRememberedValue5;
                            composerImpl4.end(false);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl4.end(false);
                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = computedProvidableCompositionLocal.defaultProvidedValue$runtime_release(offsetOverscrollEffectFactory);
                            final List list2 = list;
                            final int i6 = i;
                            final Function2 function23 = function2;
                            final EditTileListState editTileListState2 = editTileListState;
                            final Modifier modifier = companion;
                            final Function1 function17 = function15;
                            final Function1 function18 = function16;
                            final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                            final int i7 = i2;
                            final Function2 function24 = function22;
                            CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-503511992, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.DefaultEditTileGrid.3.1
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0065  */
                                /* JADX WARN: Removed duplicated region for block: B:20:0x00d5  */
                                /* JADX WARN: Removed duplicated region for block: B:28:0x010a  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj4, Object obj5) {
                                    Composer composer3 = (Composer) obj4;
                                    if ((((Number) obj5).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                        if (composerImpl5.getSkipping()) {
                                            composerImpl5.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid.<anonymous>.<anonymous> (EditTile.kt:269)");
                                            }
                                            ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composer3);
                                            final EditTileListState editTileListState3 = editTileListState2;
                                            PaddingValues paddingValues2 = paddingValues;
                                            EditTileKt.AutoScrollGrid(editTileListState3, scrollStateRememberScrollState, paddingValues2, composer3, 0);
                                            DragType dragType = (DragType) ((SnapshotMutableStateImpl) editTileListState3.dragType$delegate).getValue();
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                            composerImpl6.startReplaceGroup(-1236518501);
                                            boolean zChanged4 = composerImpl6.changed(editTileListState3) | composerImpl6.changed(scrollStateRememberScrollState);
                                            Object objRememberedValue6 = composerImpl6.rememberedValue();
                                            Composer.Companion companion2 = Composer.Companion;
                                            if (!zChanged4) {
                                                companion2.getClass();
                                                if (objRememberedValue6 == Composer.Companion.Empty) {
                                                    objRememberedValue6 = new EditTileKt$DefaultEditTileGrid$3$1$1$1(editTileListState3, scrollStateRememberScrollState, null);
                                                    composerImpl6.updateRememberedValue(objRememberedValue6);
                                                }
                                                composerImpl6.end(false);
                                                EffectsKt.LaunchedEffect(composerImpl6, dragType, (Function2) objRememberedValue6);
                                                Arrangement arrangement = Arrangement.INSTANCE;
                                                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_label_container_margin, composerImpl6);
                                                arrangement.getClass();
                                                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(fDimensionResource);
                                                Modifier modifierThen = ScrollingContainerKt.scrollingContainer(ClipScrollableContainerKt.clipScrollableContainer(PaddingKt.m129paddingqDBjuR0$default(SizeKt.fillMaxSize(modifier, 1.0f), 0.0f, paddingValues2.mo113calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13), Orientation.Vertical), scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true));
                                                composerImpl6.startReplaceGroup(-1236489158);
                                                Function1 function19 = function17;
                                                boolean zChanged5 = composerImpl6.changed(function19);
                                                Function1 function110 = function18;
                                                boolean zChanged6 = zChanged5 | composerImpl6.changed(function110) | composerImpl6.changed(editTileListState3);
                                                MutableSelectionState mutableSelectionState3 = mutableSelectionState2;
                                                boolean zChanged7 = zChanged6 | composerImpl6.changed(mutableSelectionState3);
                                                Object objRememberedValue7 = composerImpl6.rememberedValue();
                                                if (!zChanged7) {
                                                    companion2.getClass();
                                                    if (objRememberedValue7 == Composer.Companion.Empty) {
                                                        objRememberedValue7 = new EditTileKt$$ExternalSyntheticLambda2(function19, function110, editTileListState3, mutableSelectionState3);
                                                        composerImpl6.updateRememberedValue(objRememberedValue7);
                                                    }
                                                    final Function2 function25 = (Function2) objRememberedValue7;
                                                    composerImpl6.end(false);
                                                    composerImpl6.startReplaceGroup(-1773225128);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.dragAndDropRemoveZone (DragAndDropState.kt:79)");
                                                    }
                                                    composerImpl6.startReplaceGroup(-1988281787);
                                                    boolean zChanged8 = composerImpl6.changed(editTileListState3);
                                                    Object objRememberedValue8 = composerImpl6.rememberedValue();
                                                    if (!zChanged8) {
                                                        companion2.getClass();
                                                        if (objRememberedValue8 == Composer.Companion.Empty) {
                                                            objRememberedValue8 = new DragAndDropTarget() { // from class: com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropRemoveZone$target$1$1
                                                                @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                                                                public final boolean onDrop(DragAndDropEvent dragAndDropEvent) {
                                                                    EditTileListState editTileListState4 = (EditTileListState) editTileListState3;
                                                                    SizedTile draggedCell = editTileListState4.getDraggedCell();
                                                                    if (draggedCell == null) {
                                                                        return false;
                                                                    }
                                                                    function25.invoke(((EditTileViewModel) draggedCell.getTile()).tileSpec, Boolean.valueOf(editTileListState4.isDraggedCellRemovable()));
                                                                    editTileListState4.onDrop();
                                                                    return true;
                                                                }

                                                                @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                                                                public final void onEntered(DragAndDropEvent dragAndDropEvent) {
                                                                    final SizedTile draggedCell;
                                                                    EditTileListState editTileListState4 = (EditTileListState) editTileListState3;
                                                                    if (editTileListState4.isDraggedCellRemovable() && (draggedCell = editTileListState4.getDraggedCell()) != null) {
                                                                        final Function1 function111 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.EditTileListState$$ExternalSyntheticLambda0
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj6) {
                                                                                GridCell gridCell = (GridCell) obj6;
                                                                                int i8 = EditTileListState.$r8$clinit;
                                                                                return Boolean.valueOf((gridCell instanceof TileGridCell) && Intrinsics.areEqual(((TileGridCell) gridCell).tile.tileSpec, ((EditTileViewModel) draggedCell.getTile()).tileSpec));
                                                                            }
                                                                        };
                                                                        editTileListState4._tiles.removeIf(new Predicate() { // from class: com.android.systemui.qs.panels.ui.compose.EditTileListStateKt$sam$java_util_function_Predicate$0
                                                                            @Override // java.util.function.Predicate
                                                                            public final /* synthetic */ boolean test(Object obj6) {
                                                                                return ((Boolean) function111.mo781invoke(obj6)).booleanValue();
                                                                            }
                                                                        });
                                                                        Offset.Companion.getClass();
                                                                        editTileListState4.m2905setDraggedPositionk4lQ0M(Offset.Unspecified);
                                                                        editTileListState4.regenerateGrid();
                                                                    }
                                                                }

                                                                @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                                                                public final void onMoved(DragAndDropEvent dragAndDropEvent) {
                                                                    ((EditTileListState) editTileListState3).m2905setDraggedPositionk4lQ0M(DragAndDropStateKt.access$toOffset(dragAndDropEvent));
                                                                }
                                                            };
                                                            composerImpl6.updateRememberedValue(objRememberedValue8);
                                                        }
                                                        DragAndDropStateKt$dragAndDropRemoveZone$target$1$1 dragAndDropStateKt$dragAndDropRemoveZone$target$1$1 = (DragAndDropStateKt$dragAndDropRemoveZone$target$1$1) objRememberedValue8;
                                                        composerImpl6.end(false);
                                                        composerImpl6.startReplaceGroup(-1988254629);
                                                        Object objRememberedValue9 = composerImpl6.rememberedValue();
                                                        companion2.getClass();
                                                        if (objRememberedValue9 == Composer.Companion.Empty) {
                                                            objRememberedValue9 = new DragAndDropStateKt$$ExternalSyntheticLambda0(0);
                                                            composerImpl6.updateRememberedValue(objRememberedValue9);
                                                        }
                                                        composerImpl6.end(false);
                                                        Modifier modifierDragAndDropTarget = DragAndDropTargetKt.dragAndDropTarget(modifierThen, (Function1) objRememberedValue9, dragAndDropStateKt$dragAndDropRemoveZone$target$1$1);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                        composerImpl6.end(false);
                                                        final List list3 = list2;
                                                        Alignment.Companion.getClass();
                                                        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Start, composerImpl6, 0);
                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl6);
                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl6.currentCompositionLocalScope();
                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl6, modifierDragAndDropTarget);
                                                        ComposeUiNode.Companion.getClass();
                                                        Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                        if (composerImpl6.applier == null) {
                                                            ComposablesKt.invalidApplier();
                                                            throw null;
                                                        }
                                                        composerImpl6.startReusableNode();
                                                        if (composerImpl6.inserting) {
                                                            composerImpl6.createNode(function04);
                                                        } else {
                                                            composerImpl6.useNode();
                                                        }
                                                        Function2 function26 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                        Updater.m337setimpl(composerImpl6, columnMeasurePolicy, function26);
                                                        Function2 function27 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                        Updater.m337setimpl(composerImpl6, persistentCompositionLocalMapCurrentCompositionLocalScope, function27);
                                                        Function2 function28 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                        if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl6, currentCompositeKeyHash, function28);
                                                        }
                                                        Function2 function29 = ComposeUiNode.Companion.SetModifier;
                                                        Updater.m337setimpl(composerImpl6, modifierMaterializeModifier, function29);
                                                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                                        Modifier.Companion companion3 = Modifier.Companion;
                                                        Dp.Companion companion4 = Dp.Companion;
                                                        Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion3, 1.0f), 48, 0.0f, 2);
                                                        final EditTileListState editTileListState4 = editTileListState2;
                                                        final MutableSelectionState mutableSelectionState4 = mutableSelectionState2;
                                                        Function1 function111 = function17;
                                                        EditTileKt.CurrentTilesGridHeader(editTileListState4, mutableSelectionState4, function111, modifierM133heightInVpY3zN4$default, composerImpl6, 3072);
                                                        int i8 = i7;
                                                        Function2 function210 = function24;
                                                        final int i9 = i6;
                                                        EditTileKt.CurrentTilesGrid(editTileListState4, mutableSelectionState4, i9, i8, function210, function111, function18, composerImpl6, 0);
                                                        Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
                                                        EditModeTileDefaults.INSTANCE.getClass();
                                                        Modifier modifierAnimateContentSize$default = AnimationModifierKt.animateContentSize$default(SizeKt.m135requiredHeightInVpY3zN4$default(modifierFillMaxWidth, EditModeTileDefaults.AvailableTilesGridMinHeight, 0.0f, 2));
                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl6);
                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl6.currentCompositionLocalScope();
                                                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl6, modifierAnimateContentSize$default);
                                                        composerImpl6.startReusableNode();
                                                        if (composerImpl6.inserting) {
                                                            composerImpl6.createNode(function04);
                                                        } else {
                                                            composerImpl6.useNode();
                                                        }
                                                        Updater.m337setimpl(composerImpl6, measurePolicyMaybeCachedBoxMeasurePolicy, function26);
                                                        Updater.m337setimpl(composerImpl6, persistentCompositionLocalMapCurrentCompositionLocalScope2, function27);
                                                        if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl6, currentCompositeKeyHash2, function28);
                                                        }
                                                        Updater.m337setimpl(composerImpl6, modifierMaterializeModifier2, function29);
                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                        boolean z2 = editTileListState4.getDraggedCell() == null && !mutableSelectionState4.getPlacementEnabled();
                                                        EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(null, 3);
                                                        ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(null, 3);
                                                        final Modifier modifier2 = modifier;
                                                        final Function2 function211 = function23;
                                                        AnimatedVisibilityKt.AnimatedVisibility(z2, null, enterTransitionFadeIn$default, exitTransitionFadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(765075612, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$DefaultEditTileGrid$3$1$3$1$1
                                                            @Override // kotlin.jvm.functions.Function3
                                                            public final Object invoke(Object obj6, Object obj7, Object obj8) throws Throwable {
                                                                Composer composer4 = (Composer) obj7;
                                                                ((Number) obj8).intValue();
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (EditTile.kt:333)");
                                                                }
                                                                Arrangement arrangement2 = Arrangement.INSTANCE;
                                                                float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_label_container_margin, composer4);
                                                                arrangement2.getClass();
                                                                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(fDimensionResource2);
                                                                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier2, 1.0f);
                                                                List list4 = list3;
                                                                Alignment.Companion.getClass();
                                                                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_42, Alignment.Companion.Start, composer4, 0);
                                                                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composer4);
                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer4;
                                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl7.currentCompositionLocalScope();
                                                                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer4, modifierFillMaxSize);
                                                                ComposeUiNode.Companion.getClass();
                                                                Function0 function05 = ComposeUiNode.Companion.Constructor;
                                                                if (composerImpl7.applier == null) {
                                                                    ComposablesKt.invalidApplier();
                                                                    throw null;
                                                                }
                                                                composerImpl7.startReusableNode();
                                                                if (composerImpl7.inserting) {
                                                                    composerImpl7.createNode(function05);
                                                                } else {
                                                                    composerImpl7.useNode();
                                                                }
                                                                Updater.m337setimpl(composer4, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                Updater.m337setimpl(composer4, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                if (composerImpl7.inserting || !Intrinsics.areEqual(composerImpl7.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl7, currentCompositeKeyHash3, function212);
                                                                }
                                                                Updater.m337setimpl(composer4, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                                                                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                                                composerImpl7.startReplaceGroup(1224779745);
                                                                Object objRememberedValue10 = composerImpl7.rememberedValue();
                                                                Composer.Companion.getClass();
                                                                Composer$Companion$Empty$1 composer$Companion$Empty$13 = Composer.Companion.Empty;
                                                                EditTileListState editTileListState5 = editTileListState4;
                                                                Object obj9 = objRememberedValue10;
                                                                if (objRememberedValue10 == composer$Companion$Empty$13) {
                                                                    SnapshotStateList snapshotStateList = new SnapshotStateList();
                                                                    snapshotStateList.addAll(EditTileKt.access$toAvailableTiles(editTileListState5._tiles.getReadable$runtime_release().list, list4));
                                                                    composerImpl7.updateRememberedValue(snapshotStateList);
                                                                    obj9 = snapshotStateList;
                                                                }
                                                                SnapshotStateList snapshotStateList2 = (SnapshotStateList) obj9;
                                                                composerImpl7.end(false);
                                                                PersistentList persistentList = editTileListState5._tiles.getReadable$runtime_release().list;
                                                                composerImpl7.startReplaceGroup(1224790023);
                                                                boolean zChanged9 = composerImpl7.changed(editTileListState5) | composerImpl7.changedInstance(list4);
                                                                Object objRememberedValue11 = composerImpl7.rememberedValue();
                                                                if (zChanged9 || objRememberedValue11 == composer$Companion$Empty$13) {
                                                                    objRememberedValue11 = new EditTileKt$DefaultEditTileGrid$3$1$3$1$1$1$1$1(snapshotStateList2, editTileListState5, list4, null);
                                                                    composerImpl7.updateRememberedValue(objRememberedValue11);
                                                                }
                                                                composerImpl7.end(false);
                                                                EffectsKt.LaunchedEffect(persistentList, list4, (Function2) objRememberedValue11, composer4);
                                                                composerImpl7.startReplaceGroup(1224804886);
                                                                Function2 function213 = function211;
                                                                boolean zChanged10 = composerImpl7.changed(function213) | composerImpl7.changed(editTileListState5);
                                                                Object objRememberedValue12 = composerImpl7.rememberedValue();
                                                                if (zChanged10 || objRememberedValue12 == composer$Companion$Empty$13) {
                                                                    objRememberedValue12 = new EditTileKt$$ExternalSyntheticLambda13(function213, editTileListState5, 1);
                                                                    composerImpl7.updateRememberedValue(objRememberedValue12);
                                                                }
                                                                composerImpl7.end(false);
                                                                EditTileKt.AvailableTileGrid(snapshotStateList2, mutableSelectionState4, i9, (Function1) objRememberedValue12, editTileListState5, composer4, 6);
                                                                composerImpl7.end(true);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, composerImpl6), composerImpl6, 200064, 18);
                                                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl6, true, true)) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 56);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, 806879280, 445);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda40
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i3 | 1);
                    Function0 function04 = function0;
                    Function0 function05 = function02;
                    EditTileKt.DefaultEditTileGrid(editTileListState, list, i, i2, companion, function2, function1, function12, function22, function04, function05, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void EditGridCenteredText(String str, Modifier.Companion companion, Composer composer, int i) {
        ComposerImpl composerImpl;
        Modifier.Companion companion2;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-756775533);
        int i2 = i | (composerImpl2.changed(str) ? 4 : 2) | 48;
        if ((i2 & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
            companion2 = companion;
        } else {
            composerImpl = composerImpl2;
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditGridCenteredText (EditTile.kt:492)");
            }
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(str, companion2, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, i2 & 126, 0, 65532);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(str, companion2, i);
        }
    }

    public static final void EditGridHeader(int i, Composer composer, final ComposableLambdaImpl composableLambdaImpl, final Modifier.Companion companion) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2007180824);
        if (((i | 6) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditGridHeader (EditTile.kt:485)");
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            MaterialTheme.INSTANCE.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(MaterialTheme.getColorScheme(composerImpl).onSurface)), ComposableLambdaKt.rememberComposableLambda(-1127019224, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditGridHeader.1
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditGridHeader.<anonymous> (EditTile.kt:487)");
                            }
                            Alignment.Companion.getClass();
                            BiasAlignment biasAlignment = Alignment.Companion.Center;
                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxWidth);
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
                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composer2, 6);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 0, companion, composableLambdaImpl);
        }
    }

    public static final void EditModeTopBar(final Function0 function0, final Function0 function02, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(290323783);
        if ((((composerImpl.changedInstance(function0) ? 4 : 2) | i | (composerImpl.changedInstance(function02) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTopBar (EditTile.kt:179)");
            }
            MaterialTheme.INSTANCE.getClass();
            final long j = MaterialTheme.getColorScheme(composerImpl).primaryContainer;
            TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
            Color.Companion.getClass();
            long j2 = Color.Transparent;
            long j3 = MaterialTheme.getColorScheme(composerImpl).onSurface;
            topAppBarDefaults.getClass();
            long j4 = Color.Unspecified;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarDefaults.topAppBarColors (AppBar.kt:1530)");
            }
            TopAppBarColors topAppBarColorsM319copytNS2XkQ = TopAppBarDefaults.getDefaultTopAppBarColors$material3_release(MaterialTheme.getColorScheme(composerImpl)).m319copytNS2XkQ(j2, j4, j4, j3, j4, j4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 0.0f, 8, 1);
            ComposableSingletons$EditTileKt.INSTANCE.getClass();
            AppBarKt.m248TopAppBarGHTll3U(ComposableSingletons$EditTileKt.f96lambda1, modifierM127paddingVpY3zN4$default, ComposableLambdaKt.rememberComposableLambda(450710209, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditModeTopBar.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTopBar.<anonymous> (EditTile.kt:195)");
                            }
                            Modifier.Companion companion2 = Modifier.Companion;
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-787032097);
                            long j5 = j;
                            boolean zChanged = composerImpl3.changed(j5);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChanged) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new EditTileKt$$ExternalSyntheticLambda7(j5, 2);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                Modifier modifierDrawBehind = DrawModifierKt.drawBehind(companion2, (Function1) objRememberedValue);
                                ComposableSingletons$EditTileKt.INSTANCE.getClass();
                                IconButtonKt.IconButton(1572864, 60, null, null, composerImpl3, modifierDrawBehind, null, function0, ComposableSingletons$EditTileKt.f97lambda2, false);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(763420408, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditModeTopBar.2
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTopBar.<anonymous> (EditTile.kt:208)");
                            }
                            Function0 function03 = function02;
                            if (function03 != null) {
                                ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                                MaterialTheme.INSTANCE.getClass();
                                long j5 = MaterialTheme.getColorScheme(composer2).primary;
                                long j6 = MaterialTheme.getColorScheme(composer2).onPrimary;
                                buttonDefaults.getClass();
                                ButtonColors buttonColorsM254textButtonColorsro_MJ88 = ButtonDefaults.m254textButtonColorsro_MJ88(j5, j6, composer2, 12);
                                ComposableSingletons$EditTileKt.INSTANCE.getClass();
                                ButtonKt.TextButton(function03, null, false, null, buttonColorsM254textButtonColorsro_MJ88, null, null, null, null, ComposableSingletons$EditTileKt.f98lambda3, composer2, 805306368, 494);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), 0.0f, null, topAppBarColorsM319copytNS2XkQ, null, composerImpl, 3510, 176);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 3, function0, function02);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0227  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void EditTile(final EditTileViewModel editTileViewModel, final TileState tileState, final ResizingState resizingState, final Function0 function0, TileColors tileColors, Composer composer, final int i) {
        int i2;
        TileColors tileColorsEditTileColors;
        ComposerImpl composerImpl;
        final TileColors tileColors2;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1742957773);
        int i3 = i | (composerImpl2.changed(editTileViewModel) ? 4 : 2) | (composerImpl2.changed(tileState) ? 32 : 16) | (composerImpl2.changed(resizingState) ? 256 : 128) | (composerImpl2.changedInstance(function0) ? 2048 : 1024) | 8192;
        if ((i3 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            tileColors2 = tileColors;
            composerImpl = composerImpl2;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                EditModeTileDefaults.INSTANCE.getClass();
                i2 = i3 & (-57345);
                tileColorsEditTileColors = EditModeTileDefaults.editTileColors(composerImpl2);
            } else {
                composerImpl2.skipToGroupEnd();
                i2 = i3 & (-57345);
                tileColorsEditTileColors = tileColors;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTile (EditTile.kt:967)");
            }
            CommonTileDefaults.INSTANCE.getClass();
            final float f = CommonTileDefaults.IconSize - CommonTileDefaults.LargeTileIconSize;
            Dp.Companion companion = Dp.Companion;
            TileColors tileColors3 = tileColorsEditTileColors;
            int i4 = i2;
            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(tileState == TileState.GreyedOut ? 0.4f : 1.0f, null, null, null, composerImpl2, 0, 30);
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(6);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Modifier.Companion companion2 = Modifier.Companion;
            composerImpl2.startReplaceGroup(1849238316);
            int i5 = i4 & 7168;
            boolean z = ((i4 & 896) == 256) | (i5 == 2048);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            if (!z) {
                companion3.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda32
                        /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            int iIntValue;
                            final float fMo52roundToPx0680j_4;
                            MeasureScope measureScope = (MeasureScope) obj;
                            Measurable measurable = (Measurable) obj2;
                            Constraints constraints = (Constraints) obj3;
                            Pair pair = (Pair) resizingState.bounds$delegate.getValue();
                            Float f2 = (Float) pair.component1();
                            Float f3 = (Float) pair.component2();
                            float fFloatValue = ((Number) function0.invoke()).floatValue();
                            if (f3 != null) {
                                int iRoundToInt = MathKt__MathJVMKt.roundToInt(f3.floatValue());
                                Integer numValueOf = Integer.valueOf(iRoundToInt);
                                if (iRoundToInt <= Constraints.m823getMaxWidthimpl(constraints.value)) {
                                    numValueOf = null;
                                }
                                iIntValue = numValueOf != null ? numValueOf.intValue() : Constraints.m823getMaxWidthimpl(constraints.value);
                            }
                            int i6 = iIntValue;
                            final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(constraints.value, i6, i6, 0, 0, 12));
                            float fMo52roundToPx0680j_42 = 0.0f;
                            long j = constraints.value;
                            if (fFloatValue == 0.0f) {
                                int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                                CommonTileDefaults.INSTANCE.getClass();
                                fMo52roundToPx0680j_4 = ((iM823getMaxWidthimpl - measureScope.mo52roundToPx0680j_4(CommonTileDefaults.ToggleTargetSize)) / 2.0f) - measureScope.mo58toPx0680j_4(CommonTileDefaults.TileStartPadding);
                            } else {
                                if (f2 != null) {
                                    int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(f2.floatValue());
                                    CommonTileDefaults.INSTANCE.getClass();
                                    fMo52roundToPx0680j_42 = ((iRoundToInt2 - measureScope.mo52roundToPx0680j_4(CommonTileDefaults.ToggleTargetSize)) / 2.0f) - measureScope.mo58toPx0680j_4(CommonTileDefaults.TileStartPadding);
                                }
                                fMo52roundToPx0680j_4 = (1.0f - fFloatValue) * fMo52roundToPx0680j_42;
                            }
                            return measureScope.layout$1(Constraints.m823getMaxWidthimpl(j), Constraints.m822getMaxHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda38
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj4) {
                                    ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, MathKt__MathJVMKt.roundToInt(fMo52roundToPx0680j_4), 0, 0.0f);
                                    return Unit.INSTANCE;
                                }
                            });
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                Modifier modifierLargeTilePadding = TileKt.largeTilePadding(LayoutModifierKt.layout(companion2, (Function3) objRememberedValue));
                composerImpl2.startReplaceGroup(1849289246);
                boolean zChanged = composerImpl2.changed(stateAnimateFloatAsState);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChanged) {
                    companion3.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new EditTileKt$$ExternalSyntheticLambda3(stateAnimateFloatAsState, 2);
                        composerImpl2.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl2.end(false);
                    Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierLargeTilePadding, (Function1) objRememberedValue2);
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composerImpl2, 54);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierGraphicsLayer);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function02);
                    } else {
                        composerImpl2.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion2, CommonTileDefaults.ToggleTargetSize);
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM140size3ABfNKs);
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function02);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    long j = tileColors3.icon;
                    Modifier modifierAlign = boxScopeInstance.align(companion2, Alignment.Companion.Center);
                    composerImpl2.startReplaceGroup(-1640111341);
                    boolean z2 = (i4 & 14) == 4;
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    if (!z2) {
                        companion3.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new EditTileKt$$ExternalSyntheticLambda6(editTileViewModel, 4);
                            composerImpl2.updateRememberedValue(objRememberedValue3);
                        }
                        Function1 function1 = (Function1) objRememberedValue3;
                        composerImpl2.end(false);
                        composerImpl2.startReplaceGroup(-1640107711);
                        boolean z3 = i5 == 2048;
                        Object objRememberedValue4 = composerImpl2.rememberedValue();
                        if (!z3) {
                            companion3.getClass();
                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                objRememberedValue4 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda35
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        CommonTileDefaults.INSTANCE.getClass();
                                        return Dp.m837boximpl(CommonTileDefaults.IconSize - (((Number) function0.invoke()).floatValue() * f));
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue4);
                            }
                            composerImpl2.end(false);
                            CommonTileKt.m2906SmallTileContent8V94_ZQ(function1, j, modifierAlign, (Function0) objRememberedValue4, true, composerImpl2, 24576, 0);
                            composerImpl2.end(true);
                            String str = editTileViewModel.label.text;
                            AnnotatedString annotatedString = editTileViewModel.appName;
                            String str2 = annotatedString != null ? annotatedString.text : null;
                            Modifier modifierWeight = rowScopeInstance.weight(companion2, 1.0f, true);
                            composerImpl2.startReplaceGroup(-2019675582);
                            boolean z4 = i5 == 2048;
                            Object objRememberedValue5 = composerImpl2.rememberedValue();
                            if (!z4) {
                                companion3.getClass();
                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                    objRememberedValue5 = new EditTileKt$$ExternalSyntheticLambda26(1, function0);
                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                }
                                composerImpl2.end(false);
                                CommonTileKt.LargeTileLabels(str, str2, tileColors3, GraphicsLayerModifierKt.graphicsLayer(modifierWeight, (Function1) objRememberedValue5), null, null, composerImpl2, 0, 48);
                                composerImpl = composerImpl2;
                                composerImpl.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                tileColors2 = tileColors3;
                            }
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(tileState, resizingState, function0, tileColors2, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda37
                public final /* synthetic */ TileState f$1;
                public final /* synthetic */ ResizingState f$2;
                public final /* synthetic */ Function0 f$3;
                public final /* synthetic */ TileColors f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function0 function03 = this.f$3;
                    TileColors tileColors4 = this.f$4;
                    EditTileKt.EditTile(this.f$0, this.f$1, this.f$2, function03, tileColors4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void RemoveTileTarget(Function0 function0, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1658681413);
        if ((((composerImpl.changedInstance(function0) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.RemoveTileTarget (EditTile.kt:497)");
            }
            Alignment.Companion companion = Alignment.Companion;
            companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement arrangement = Arrangement.INSTANCE;
            CommonTileDefaults.INSTANCE.getClass();
            float f = CommonTileDefaults.TileArrangementPadding;
            companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw = Arrangement.m93spacedByD5KLDUw(f, horizontal);
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(BorderKt.m28borderxT4_qwU(ClickableKt.m35clickableXHw0xAI$default(SizeKt.wrapContentSize$default(Modifier.Companion, null, 3), false, null, function0, 7), 1, ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value, RoundedCornerShapeKt.CircleShape), 10);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM125padding3ABfNKs);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Icons.INSTANCE.getClass();
            ImageVector imageVectorBuild = ClearKt._clear;
            if (imageVectorBuild == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Clear", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i2 = StrokeJoin.Bevel;
                PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(19.0f, 6.41f, 17.59f, 5.0f);
                pathBuilderM.lineTo(12.0f, 10.59f);
                pathBuilderM.lineTo(6.41f, 5.0f);
                pathBuilderM.lineTo(5.0f, 6.41f);
                pathBuilderM.lineTo(10.59f, 12.0f);
                pathBuilderM.lineTo(5.0f, 17.59f);
                pathBuilderM.lineTo(6.41f, 19.0f);
                pathBuilderM.lineTo(12.0f, 13.41f);
                pathBuilderM.lineTo(17.59f, 19.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 19.0f, 17.59f, 13.41f, 12.0f);
                builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i2, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVectorBuild = builder.build();
                ClearKt._clear = imageVectorBuild;
            }
            IconKt.m271Iconww6aTOc(imageVectorBuild, (String) null, (Modifier) null, 0L, composerImpl, 48, 12);
            TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_customize_remove, composerImpl), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composerImpl, 0, 0, 131070);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda16(function0, i, 1);
        }
    }

    public static final void SpacerGridCell(Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(607813919);
        if ((((composerImpl.changed(modifier) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.SpacerGridCell (EditTile.kt:955)");
            }
            CommonTileDefaults.INSTANCE.getClass();
            BoxKt.Box(SizeKt.fillMaxWidth(SizeKt.m131height3ABfNKs(modifier, CommonTileDefaults.TileHeight), 1.0f), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda16(modifier, i, 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x043b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TileGridCell(final TileGridCell tileGridCell, final int i, final DragAndDropState dragAndDropState, final MutableSelectionState mutableSelectionState, final Function1 function1, final Function1 function12, final CoroutineScope coroutineScope, final int i2, final Modifier modifier, Composer composer, final int i3) throws Resources.NotFoundException {
        int i4;
        Boolean bool;
        MutableState mutableState;
        Boolean bool2;
        TileSpec tileSpec;
        boolean z;
        Function0 function0;
        boolean z2;
        String strStringResource;
        boolean zChanged;
        Object objRememberedValue;
        boolean zChanged2;
        Object objRememberedValue2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1904258365);
        if ((i3 & 6) == 0) {
            i4 = (composerImpl2.changed(tileGridCell) ? 4 : 2) | i3;
        } else {
            i4 = i3;
        }
        if ((i3 & 48) == 0) {
            i4 |= composerImpl2.changed(i) ? 32 : 16;
        }
        if ((i3 & 384) == 0) {
            i4 |= (i3 & 512) == 0 ? composerImpl2.changed(dragAndDropState) : composerImpl2.changedInstance(dragAndDropState) ? 256 : 128;
        }
        if ((i3 & 3072) == 0) {
            i4 |= composerImpl2.changed(mutableSelectionState) ? 2048 : 1024;
        }
        if ((i3 & 24576) == 0) {
            i4 |= composerImpl2.changedInstance(function1) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i3) == 0) {
            i4 |= composerImpl2.changedInstance(function12) ? 131072 : 65536;
        }
        if ((i3 & 1572864) == 0) {
            i4 |= composerImpl2.changedInstance(coroutineScope) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((i3 & 12582912) == 0) {
            i4 |= composerImpl2.changed(i2) ? 8388608 : 4194304;
        }
        if ((i3 & 100663296) == 0) {
            i4 |= composerImpl2.changed(modifier) ? 67108864 : 33554432;
        }
        int i5 = i4;
        if ((i5 & 38347923) == 38347922 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileGridCell (EditTile.kt:757)");
            }
            final String strStringResource2 = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_position, new Object[]{Integer.valueOf(i + 1)}, composerImpl2);
            EditTileViewModel editTileViewModel = tileGridCell.tile;
            int i6 = i5 >> 6;
            int i7 = i6 & 112;
            composerImpl2.startReplaceGroup(-441557950);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.rememberTileState (EditTile.kt:734)");
            }
            composerImpl2.startReplaceGroup(685949617);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue3 == obj) {
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(TileState.None);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            MutableState mutableState2 = (MutableState) objRememberedValue3;
            composerImpl2.end(false);
            boolean zContains = editTileViewModel.availableEditActions.contains(AvailableEditActions.REMOVE);
            TileSpec selection = mutableSelectionState.getSelection();
            Boolean boolValueOf = Boolean.valueOf(mutableSelectionState.getPlacementEnabled());
            Boolean boolValueOf2 = Boolean.valueOf(zContains);
            composerImpl2.startReplaceGroup(685955812);
            boolean zChanged3 = (((i7 ^ 48) > 32 && composerImpl2.changed(mutableSelectionState)) || (i6 & 48) == 32) | composerImpl2.changed(editTileViewModel) | composerImpl2.changed(zContains);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (zChanged3 || objRememberedValue4 == obj) {
                bool = boolValueOf2;
                mutableState = mutableState2;
                bool2 = boolValueOf;
                tileSpec = selection;
                z = false;
                Object editTileKt$rememberTileState$1$1 = new EditTileKt$rememberTileState$1$1(mutableState, mutableSelectionState, editTileViewModel, zContains, null);
                composerImpl2.updateRememberedValue(editTileKt$rememberTileState$1$1);
                objRememberedValue4 = editTileKt$rememberTileState$1$1;
            } else {
                bool = boolValueOf2;
                mutableState = mutableState2;
                bool2 = boolValueOf;
                tileSpec = selection;
                z = false;
            }
            composerImpl2.end(z);
            EffectsKt.LaunchedEffect(tileSpec, bool2, bool, (Function2) objRememberedValue4, composerImpl2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(z);
            TileSpec tileSpec2 = tileGridCell.tile.tileSpec;
            boolean zIsIcon = tileGridCell.isIcon();
            composerImpl2.startReplaceGroup(-1708041680);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.rememberResizingState (ResizingState.kt:30)");
            }
            composerImpl2.startReplaceGroup(-560107159);
            boolean zChanged4 = composerImpl2.changed(tileSpec2);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (zChanged4 || objRememberedValue5 == obj) {
                objRememberedValue5 = new ResizingState(tileSpec2, zIsIcon);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            final ResizingState resizingState = (ResizingState) objRememberedValue5;
            composerImpl2.end(z);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(z);
            composerImpl2.startReplaceGroup(1554498756);
            int i8 = i5 & 14;
            boolean zChanged5 = composerImpl2.changed(mutableState) | composerImpl2.changed(resizingState) | (i8 == 4 ? true : z);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChanged5 || objRememberedValue6 == obj) {
                objRememberedValue6 = new EditTileKt$$ExternalSyntheticLambda22(resizingState, tileGridCell, 1, mutableState);
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            Function0 function02 = (Function0) objRememberedValue6;
            composerImpl2.end(z);
            TileState tileState = (TileState) mutableState.getValue();
            TileState tileState2 = TileState.Selected;
            if (tileState != tileState2) {
                composerImpl2.startReplaceGroup(945021349);
                Boolean boolValueOf3 = Boolean.valueOf(tileGridCell.isIcon());
                composerImpl2.startReplaceGroup(1554509175);
                function0 = function02;
                boolean zChanged6 = (i8 == 4) | composerImpl2.changed(resizingState);
                Object objRememberedValue7 = composerImpl2.rememberedValue();
                if (zChanged6 || objRememberedValue7 == obj) {
                    objRememberedValue7 = new EditTileKt$TileGridCell$1$1(resizingState, tileGridCell, null);
                    composerImpl2.updateRememberedValue(objRememberedValue7);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, boolValueOf3, (Function2) objRememberedValue7);
                composerImpl2.end(false);
            } else {
                function0 = function02;
                composerImpl2.startReplaceGroup(945215502);
                ResizingState.ResizeOperation.TemporaryResizeOperation temporaryResizeOperation = (ResizingState.ResizeOperation.TemporaryResizeOperation) resizingState.temporaryResizeOperation$delegate.getValue();
                composerImpl2.startReplaceGroup(1554517294);
                int i9 = i5 & 57344;
                boolean zChanged7 = (i9 == 16384) | composerImpl2.changed(resizingState);
                Object objRememberedValue8 = composerImpl2.rememberedValue();
                if (zChanged7 || objRememberedValue8 == obj) {
                    objRememberedValue8 = new EditTileKt$TileGridCell$2$1(function1, resizingState, null);
                    composerImpl2.updateRememberedValue(objRememberedValue8);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, temporaryResizeOperation, (Function2) objRememberedValue8);
                ResizingState.ResizeOperation.FinalResizeOperation finalResizeOperation = (ResizingState.ResizeOperation.FinalResizeOperation) resizingState.finalResizeOperation$delegate.getValue();
                composerImpl2.startReplaceGroup(1554521514);
                boolean zChanged8 = (i9 == 16384) | composerImpl2.changed(resizingState);
                Object objRememberedValue9 = composerImpl2.rememberedValue();
                if (zChanged8 || objRememberedValue9 == obj) {
                    objRememberedValue9 = new EditTileKt$TileGridCell$3$1(function1, resizingState, null);
                    composerImpl2.updateRememberedValue(objRememberedValue9);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, finalResizeOperation, (Function2) objRememberedValue9);
                composerImpl2.end(false);
            }
            Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
            CommonTileDefaults.INSTANCE.getClass();
            final int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(CommonTileDefaults.TileArrangementPadding) * (i2 - 1);
            EditModeTileDefaults.INSTANCE.getClass();
            final TileColors tileColorsEditTileColors = EditModeTileDefaults.editTileColors(composerImpl2);
            final String strStringResource3 = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_toggle_tile_size_action, composerImpl2);
            final String strStringResource4 = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_toggle_placement_mode, composerImpl2);
            TileState tileState3 = (TileState) mutableState.getValue();
            composerImpl2.startReplaceGroup(1554537715);
            if (tileState3 == TileState.Removable) {
                strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_qs_edit_remove_tile_action, composerImpl2);
            } else if (tileState3 == tileState2) {
                strStringResource = strStringResource3;
            } else {
                if (tileState3 != TileState.None && tileState3 != TileState.Placeable && tileState3 != TileState.GreyedOut) {
                    throw new NoWhenBranchMatchedException();
                }
                z2 = false;
                strStringResource = null;
                composerImpl2.end(z2);
                TileState tileState4 = (TileState) mutableState.getValue();
                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m131height3ABfNKs(modifier, CommonTileDefaults.TileHeight), 1.0f);
                composerImpl2.startReplaceGroup(1554553449);
                zChanged = (i8 != 4) | composerImpl2.changed(iMo52roundToPx0680j_4) | ((i5 & 29360128) != 8388608) | composerImpl2.changed(resizingState);
                objRememberedValue = composerImpl2.rememberedValue();
                if (!zChanged || objRememberedValue == obj) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda29
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            IntSize intSize = (IntSize) obj2;
                            TileGridCell tileGridCell2 = tileGridCell;
                            boolean zIsIcon2 = tileGridCell2.isIcon();
                            int i10 = iMo52roundToPx0680j_4;
                            int i11 = i2;
                            int i12 = zIsIcon2 ? (int) (intSize.packedValue >> 32) : (((int) (intSize.packedValue >> 32)) - i10) / i11;
                            int i13 = tileGridCell2.isIcon() ? (((int) (intSize.packedValue >> 32)) * i11) + i10 : (int) (intSize.packedValue >> 32);
                            final float f = i12;
                            final float f2 = i13;
                            ResizingState resizingState2 = resizingState;
                            resizingState2.getClass();
                            AnchoredDraggableState.updateAnchors$default(resizingState2.anchoredDraggableState, AnchoredDraggableKt.DraggableAnchors(new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.ResizingState$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj3) {
                                    DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj3;
                                    draggableAnchorsConfig.at(QSDragAnchor.Icon, f);
                                    draggableAnchorsConfig.at(QSDragAnchor.Large, f2);
                                    return Unit.INSTANCE;
                                }
                            }));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                Modifier modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifierFillMaxWidth, (Function1) objRememberedValue);
                composerImpl2.startReplaceGroup(1554565355);
                zChanged2 = composerImpl2.changed(mutableState) | ((i5 & 458752) != 131072) | (i8 != 4) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changed(resizingState);
                objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChanged2 || objRememberedValue2 == obj) {
                    final MutableState mutableState3 = mutableState;
                    Object obj2 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda30
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            MutableState mutableState4 = mutableState3;
                            if (((TileState) mutableState4.getValue()) == TileState.Removable) {
                                function12.mo781invoke(tileGridCell.tile.tileSpec);
                            } else if (((TileState) mutableState4.getValue()) == TileState.Selected) {
                                BuildersKt.launch$default(coroutineScope, null, null, new EditTileKt$TileGridCell$5$1$1(resizingState, null), 3);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    mutableState = mutableState3;
                    composerImpl2.updateRememberedValue(obj2);
                    objRememberedValue2 = obj2;
                }
                composerImpl2.end(false);
                final Function0 function03 = function0;
                final MutableState mutableState4 = mutableState;
                ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-417905065, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.TileGridCell.6
                    /* JADX WARN: Removed duplicated region for block: B:19:0x00a9  */
                    /* JADX WARN: Removed duplicated region for block: B:27:0x00ff  */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x013e  */
                    /* JADX WARN: Removed duplicated region for block: B:40:0x016f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        Composer composer2 = (Composer) obj4;
                        if ((((Number) obj5).intValue() & 17) == 16) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileGridCell.<anonymous> (EditTile.kt:817)");
                                }
                                MaterialTheme.INSTANCE.getClass();
                                long j = MaterialTheme.getColorScheme(composer2).primary;
                                long jColor = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.4f, Color.m461getColorSpaceimpl(j));
                                State state = mutableState4;
                                if (((TileState) state.getValue()) != TileState.Placeable) {
                                    jColor = tileColorsEditTileColors.background;
                                }
                                final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(jColor, null, null, composer2, 0, 14);
                                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-1338365570);
                                boolean zChanged9 = composerImpl4.changed(strStringResource2);
                                TileGridCell tileGridCell2 = tileGridCell;
                                boolean zChanged10 = zChanged9 | composerImpl4.changed(tileGridCell2) | composerImpl4.changed(strStringResource3) | composerImpl4.changed(function1) | composerImpl4.changed(strStringResource4);
                                final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                                boolean zChanged11 = zChanged10 | composerImpl4.changed(mutableSelectionState2);
                                Object objRememberedValue10 = composerImpl4.rememberedValue();
                                Composer.Companion companion = Composer.Companion;
                                if (!zChanged11) {
                                    companion.getClass();
                                    if (objRememberedValue10 == Composer.Companion.Empty) {
                                        final String str = strStringResource3;
                                        final String str2 = strStringResource4;
                                        final String str3 = strStringResource2;
                                        final TileGridCell tileGridCell3 = tileGridCell;
                                        final Function1 function13 = function1;
                                        final MutableSelectionState mutableSelectionState3 = mutableSelectionState;
                                        Function1 function14 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$TileGridCell$6$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj6) {
                                                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj6;
                                                SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, str3);
                                                TileGridCell tileGridCell4 = tileGridCell3;
                                                SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, tileGridCell4.tile.label.text);
                                                SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Arrays.asList(new CustomAccessibilityAction(str, new EditTileKt$$ExternalSyntheticLambda39(2, function13, tileGridCell4)), new CustomAccessibilityAction(str2, new EditTileKt$$ExternalSyntheticLambda39(3, mutableSelectionState3, tileGridCell4))));
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl4.updateRememberedValue(function14);
                                        objRememberedValue10 = function14;
                                    }
                                    composerImpl4.end(false);
                                    Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierFillMaxSize, true, (Function1) objRememberedValue10);
                                    final TileSpec tileSpec3 = tileGridCell2.tile.tileSpec;
                                    composerImpl4.startReplaceGroup(859640867);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.selectableTile (MutableSelectionState.kt:169)");
                                    }
                                    Unit unit = Unit.INSTANCE;
                                    composerImpl4.startReplaceGroup(-1809625748);
                                    boolean zChanged12 = composerImpl4.changed(mutableSelectionState2) | composerImpl4.changed(tileSpec3);
                                    Object objRememberedValue11 = composerImpl4.rememberedValue();
                                    if (!zChanged12) {
                                        companion.getClass();
                                        if (objRememberedValue11 == Composer.Companion.Empty) {
                                            objRememberedValue11 = new PointerInputEventHandler() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1
                                                /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0] */
                                                /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1] */
                                                /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2] */
                                                @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                    final MutableSelectionState mutableSelectionState4 = mutableSelectionState2;
                                                    ?? r0 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            return Boolean.valueOf(!mutableSelectionState4.getPlacementEnabled());
                                                        }
                                                    };
                                                    final TileSpec tileSpec4 = tileSpec3;
                                                    Object objDetectEagerTapGestures = EagerTapKt.detectEagerTapGestures(pointerInputScope, r0, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj6) {
                                                            MutableSelectionState mutableSelectionState5 = mutableSelectionState4;
                                                            mutableSelectionState5.setSelection(tileSpec4);
                                                            mutableSelectionState5.setPlacementEnabled(true);
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            MutableSelectionState mutableSelectionState5 = mutableSelectionState4;
                                                            boolean placementEnabled = mutableSelectionState5.getPlacementEnabled();
                                                            TileSpec tileSpec5 = tileSpec4;
                                                            if (placementEnabled && Intrinsics.areEqual(mutableSelectionState5.getSelection(), tileSpec5)) {
                                                                mutableSelectionState5.setPlacementEnabled(false);
                                                            } else if (mutableSelectionState5.getPlacementEnabled()) {
                                                                TileSpec selection2 = mutableSelectionState5.getSelection();
                                                                if (selection2 != null) {
                                                                    ((SnapshotMutableStateImpl) mutableSelectionState5.placementEvent$delegate).setValue(new PlacementEvent.PlaceToTileSpec(selection2, tileSpec5));
                                                                }
                                                                mutableSelectionState5.setPlacementEnabled(false);
                                                            } else if (Intrinsics.areEqual(mutableSelectionState5.getSelection(), tileSpec5)) {
                                                                mutableSelectionState5.unSelect();
                                                            } else {
                                                                mutableSelectionState5.setSelection(tileSpec5);
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, continuation);
                                                    return objDetectEagerTapGestures == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectEagerTapGestures : Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(objRememberedValue11);
                                        }
                                        composerImpl4.end(false);
                                        Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierSemantics, unit, (PointerInputEventHandler) objRememberedValue11);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl4.end(false);
                                        SizedTileImpl sizedTileImpl = new SizedTileImpl(tileGridCell2.tile, tileGridCell2.width);
                                        DragType dragType = DragType.Move;
                                        composerImpl4.startReplaceGroup(-1338332643);
                                        boolean zChanged13 = composerImpl4.changed(mutableSelectionState2);
                                        Object objRememberedValue12 = composerImpl4.rememberedValue();
                                        if (!zChanged13) {
                                            companion.getClass();
                                            if (objRememberedValue12 == Composer.Companion.Empty) {
                                                objRememberedValue12 = new EditTileKt$TileGridCell$6$2$1(mutableSelectionState2);
                                                composerImpl4.updateRememberedValue(objRememberedValue12);
                                            }
                                            composerImpl4.end(false);
                                            Modifier modifierDragAndDropTileSource = DragAndDropStateKt.dragAndDropTileSource(modifierPointerInput, sizedTileImpl, dragAndDropState, dragType, (Function0) ((KFunction) objRememberedValue12), composerImpl4, 3072);
                                            composerImpl4.startReplaceGroup(-1338330216);
                                            boolean zChanged14 = composerImpl4.changed(stateM7animateColorAsStateeuL9pac);
                                            Object objRememberedValue13 = composerImpl4.rememberedValue();
                                            if (!zChanged14) {
                                                companion.getClass();
                                                if (objRememberedValue13 == Composer.Companion.Empty) {
                                                    objRememberedValue13 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$TileGridCell$6$3$1
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            return Color.m456boximpl(((Color) stateM7animateColorAsStateeuL9pac.getValue()).value);
                                                        }
                                                    };
                                                    composerImpl4.updateRememberedValue(objRememberedValue13);
                                                }
                                                composerImpl4.end(false);
                                                CommonTileDefaults.INSTANCE.getClass();
                                                Modifier modifierDrawBehind = DrawModifierKt.drawBehind(ClipKt.clip(modifierDragAndDropTileSource, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(CommonTileDefaults.InactiveCornerRadius)), new EditTileKt$$ExternalSyntheticLambda26(0, (Function0) objRememberedValue13));
                                                Alignment.Companion.getClass();
                                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierDrawBehind);
                                                ComposeUiNode.Companion.getClass();
                                                Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                if (composerImpl4.applier == null) {
                                                    ComposablesKt.invalidApplier();
                                                    throw null;
                                                }
                                                composerImpl4.startReusableNode();
                                                if (composerImpl4.inserting) {
                                                    composerImpl4.createNode(function04);
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
                                                EditTileKt.EditTile(tileGridCell2.tile, (TileState) state.getValue(), resizingState, function03, null, composerImpl4, 0);
                                                composerImpl4.end(true);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2);
                composerImpl = composerImpl2;
                SelectionKt.InteractiveTileContainer(tileState4, resizingState, modifierOnSizeChanged, (Function0) objRememberedValue2, strStringResource, composableLambdaImplRememberComposableLambda, composerImpl, 196608);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
            z2 = false;
            composerImpl2.end(z2);
            TileState tileState42 = (TileState) mutableState.getValue();
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(SizeKt.m131height3ABfNKs(modifier, CommonTileDefaults.TileHeight), 1.0f);
            composerImpl2.startReplaceGroup(1554553449);
            zChanged = (i8 != 4) | composerImpl2.changed(iMo52roundToPx0680j_4) | ((i5 & 29360128) != 8388608) | composerImpl2.changed(resizingState);
            objRememberedValue = composerImpl2.rememberedValue();
            if (!zChanged) {
                objRememberedValue = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda29
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj22) {
                        IntSize intSize = (IntSize) obj22;
                        TileGridCell tileGridCell2 = tileGridCell;
                        boolean zIsIcon2 = tileGridCell2.isIcon();
                        int i10 = iMo52roundToPx0680j_4;
                        int i11 = i2;
                        int i12 = zIsIcon2 ? (int) (intSize.packedValue >> 32) : (((int) (intSize.packedValue >> 32)) - i10) / i11;
                        int i13 = tileGridCell2.isIcon() ? (((int) (intSize.packedValue >> 32)) * i11) + i10 : (int) (intSize.packedValue >> 32);
                        final float f = i12;
                        final float f2 = i13;
                        ResizingState resizingState2 = resizingState;
                        resizingState2.getClass();
                        AnchoredDraggableState.updateAnchors$default(resizingState2.anchoredDraggableState, AnchoredDraggableKt.DraggableAnchors(new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.ResizingState$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj3) {
                                DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj3;
                                draggableAnchorsConfig.at(QSDragAnchor.Icon, f);
                                draggableAnchorsConfig.at(QSDragAnchor.Large, f2);
                                return Unit.INSTANCE;
                            }
                        }));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue);
                composerImpl2.end(false);
                Modifier modifierOnSizeChanged2 = OnRemeasuredModifierKt.onSizeChanged(modifierFillMaxWidth2, (Function1) objRememberedValue);
                composerImpl2.startReplaceGroup(1554565355);
                zChanged2 = composerImpl2.changed(mutableState) | ((i5 & 458752) != 131072) | (i8 != 4) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changed(resizingState);
                objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChanged2) {
                    final MutableState mutableState32 = mutableState;
                    Object obj22 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda30
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            MutableState mutableState42 = mutableState32;
                            if (((TileState) mutableState42.getValue()) == TileState.Removable) {
                                function12.mo781invoke(tileGridCell.tile.tileSpec);
                            } else if (((TileState) mutableState42.getValue()) == TileState.Selected) {
                                BuildersKt.launch$default(coroutineScope, null, null, new EditTileKt$TileGridCell$5$1$1(resizingState, null), 3);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    mutableState = mutableState32;
                    composerImpl2.updateRememberedValue(obj22);
                    objRememberedValue2 = obj22;
                    composerImpl2.end(false);
                    final Function0 function032 = function0;
                    final State<? extends TileState> mutableState42 = mutableState;
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-417905065, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.TileGridCell.6
                        /* JADX WARN: Removed duplicated region for block: B:19:0x00a9  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x00ff  */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x013e  */
                        /* JADX WARN: Removed duplicated region for block: B:40:0x016f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                            Composer composer2 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 17) == 16) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileGridCell.<anonymous> (EditTile.kt:817)");
                                    }
                                    MaterialTheme.INSTANCE.getClass();
                                    long j = MaterialTheme.getColorScheme(composer2).primary;
                                    long jColor = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.4f, Color.m461getColorSpaceimpl(j));
                                    State state = mutableState42;
                                    if (((TileState) state.getValue()) != TileState.Placeable) {
                                        jColor = tileColorsEditTileColors.background;
                                    }
                                    final State<Color> stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(jColor, null, null, composer2, 0, 14);
                                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-1338365570);
                                    boolean zChanged9 = composerImpl4.changed(strStringResource2);
                                    TileGridCell tileGridCell2 = tileGridCell;
                                    boolean zChanged10 = zChanged9 | composerImpl4.changed(tileGridCell2) | composerImpl4.changed(strStringResource3) | composerImpl4.changed(function1) | composerImpl4.changed(strStringResource4);
                                    final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                                    boolean zChanged11 = zChanged10 | composerImpl4.changed(mutableSelectionState2);
                                    Object objRememberedValue10 = composerImpl4.rememberedValue();
                                    Composer.Companion companion = Composer.Companion;
                                    if (!zChanged11) {
                                        companion.getClass();
                                        if (objRememberedValue10 == Composer.Companion.Empty) {
                                            final String str = strStringResource3;
                                            final String str2 = strStringResource4;
                                            final String str3 = strStringResource2;
                                            final TileGridCell tileGridCell3 = tileGridCell;
                                            final Function1 function13 = function1;
                                            final MutableSelectionState mutableSelectionState3 = mutableSelectionState;
                                            Function1 function14 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$TileGridCell$6$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj6) {
                                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj6;
                                                    SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, str3);
                                                    TileGridCell tileGridCell4 = tileGridCell3;
                                                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, tileGridCell4.tile.label.text);
                                                    SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Arrays.asList(new CustomAccessibilityAction(str, new EditTileKt$$ExternalSyntheticLambda39(2, function13, tileGridCell4)), new CustomAccessibilityAction(str2, new EditTileKt$$ExternalSyntheticLambda39(3, mutableSelectionState3, tileGridCell4))));
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(function14);
                                            objRememberedValue10 = function14;
                                        }
                                        composerImpl4.end(false);
                                        Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierFillMaxSize, true, (Function1) objRememberedValue10);
                                        final TileSpec tileSpec3 = tileGridCell2.tile.tileSpec;
                                        composerImpl4.startReplaceGroup(859640867);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.selection.selectableTile (MutableSelectionState.kt:169)");
                                        }
                                        Unit unit = Unit.INSTANCE;
                                        composerImpl4.startReplaceGroup(-1809625748);
                                        boolean zChanged12 = composerImpl4.changed(mutableSelectionState2) | composerImpl4.changed(tileSpec3);
                                        Object objRememberedValue11 = composerImpl4.rememberedValue();
                                        if (!zChanged12) {
                                            companion.getClass();
                                            if (objRememberedValue11 == Composer.Companion.Empty) {
                                                objRememberedValue11 = new PointerInputEventHandler() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1
                                                    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0] */
                                                    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1] */
                                                    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2] */
                                                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                        final MutableSelectionState mutableSelectionState4 = mutableSelectionState2;
                                                        ?? r0 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                return Boolean.valueOf(!mutableSelectionState4.getPlacementEnabled());
                                                            }
                                                        };
                                                        final TileSpec tileSpec4 = tileSpec3;
                                                        Object objDetectEagerTapGestures = EagerTapKt.detectEagerTapGestures(pointerInputScope, r0, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj6) {
                                                                MutableSelectionState mutableSelectionState5 = mutableSelectionState4;
                                                                mutableSelectionState5.setSelection(tileSpec4);
                                                                mutableSelectionState5.setPlacementEnabled(true);
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                MutableSelectionState mutableSelectionState5 = mutableSelectionState4;
                                                                boolean placementEnabled = mutableSelectionState5.getPlacementEnabled();
                                                                TileSpec tileSpec5 = tileSpec4;
                                                                if (placementEnabled && Intrinsics.areEqual(mutableSelectionState5.getSelection(), tileSpec5)) {
                                                                    mutableSelectionState5.setPlacementEnabled(false);
                                                                } else if (mutableSelectionState5.getPlacementEnabled()) {
                                                                    TileSpec selection2 = mutableSelectionState5.getSelection();
                                                                    if (selection2 != null) {
                                                                        ((SnapshotMutableStateImpl) mutableSelectionState5.placementEvent$delegate).setValue(new PlacementEvent.PlaceToTileSpec(selection2, tileSpec5));
                                                                    }
                                                                    mutableSelectionState5.setPlacementEnabled(false);
                                                                } else if (Intrinsics.areEqual(mutableSelectionState5.getSelection(), tileSpec5)) {
                                                                    mutableSelectionState5.unSelect();
                                                                } else {
                                                                    mutableSelectionState5.setSelection(tileSpec5);
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, continuation);
                                                        return objDetectEagerTapGestures == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectEagerTapGestures : Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl4.updateRememberedValue(objRememberedValue11);
                                            }
                                            composerImpl4.end(false);
                                            Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierSemantics, unit, (PointerInputEventHandler) objRememberedValue11);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl4.end(false);
                                            SizedTileImpl sizedTileImpl = new SizedTileImpl(tileGridCell2.tile, tileGridCell2.width);
                                            DragType dragType = DragType.Move;
                                            composerImpl4.startReplaceGroup(-1338332643);
                                            boolean zChanged13 = composerImpl4.changed(mutableSelectionState2);
                                            Object objRememberedValue12 = composerImpl4.rememberedValue();
                                            if (!zChanged13) {
                                                companion.getClass();
                                                if (objRememberedValue12 == Composer.Companion.Empty) {
                                                    objRememberedValue12 = new EditTileKt$TileGridCell$6$2$1(mutableSelectionState2);
                                                    composerImpl4.updateRememberedValue(objRememberedValue12);
                                                }
                                                composerImpl4.end(false);
                                                Modifier modifierDragAndDropTileSource = DragAndDropStateKt.dragAndDropTileSource(modifierPointerInput, sizedTileImpl, dragAndDropState, dragType, (Function0) ((KFunction) objRememberedValue12), composerImpl4, 3072);
                                                composerImpl4.startReplaceGroup(-1338330216);
                                                boolean zChanged14 = composerImpl4.changed(stateM7animateColorAsStateeuL9pac);
                                                Object objRememberedValue13 = composerImpl4.rememberedValue();
                                                if (!zChanged14) {
                                                    companion.getClass();
                                                    if (objRememberedValue13 == Composer.Companion.Empty) {
                                                        objRememberedValue13 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$TileGridCell$6$3$1
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                return Color.m456boximpl(((Color) stateM7animateColorAsStateeuL9pac.getValue()).value);
                                                            }
                                                        };
                                                        composerImpl4.updateRememberedValue(objRememberedValue13);
                                                    }
                                                    composerImpl4.end(false);
                                                    CommonTileDefaults.INSTANCE.getClass();
                                                    Modifier modifierDrawBehind = DrawModifierKt.drawBehind(ClipKt.clip(modifierDragAndDropTileSource, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(CommonTileDefaults.InactiveCornerRadius)), new EditTileKt$$ExternalSyntheticLambda26(0, (Function0) objRememberedValue13));
                                                    Alignment.Companion.getClass();
                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierDrawBehind);
                                                    ComposeUiNode.Companion.getClass();
                                                    Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                    if (composerImpl4.applier == null) {
                                                        ComposablesKt.invalidApplier();
                                                        throw null;
                                                    }
                                                    composerImpl4.startReusableNode();
                                                    if (composerImpl4.inserting) {
                                                        composerImpl4.createNode(function04);
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
                                                    EditTileKt.EditTile(tileGridCell2.tile, (TileState) state.getValue(), resizingState, function032, null, composerImpl4, 0);
                                                    composerImpl4.end(true);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2);
                    composerImpl = composerImpl2;
                    SelectionKt.InteractiveTileContainer(tileState42, resizingState, modifierOnSizeChanged2, (Function0) objRememberedValue2, strStringResource, composableLambdaImplRememberComposableLambda2, composerImpl, 196608);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda31
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) throws Resources.NotFoundException {
                    ((Integer) obj4).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i3 | 1);
                    int i10 = i2;
                    Modifier modifier2 = modifier;
                    EditTileKt.TileGridCell(tileGridCell, i, dragAndDropState, mutableSelectionState, function1, function12, coroutineScope, i10, modifier2, (Composer) obj3, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final List access$toAvailableTiles(PersistentList persistentList, List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : persistentList) {
            if (obj instanceof TileGridCell) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add(new AvailableTileGridCell(((TileGridCell) arrayList.get(i)).tile, 0, false, null, 10, null));
        }
        ArrayList arrayList3 = new ArrayList(list.size());
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            arrayList3.add(new AvailableTileGridCell((EditTileViewModel) ((SizedTile) list.get(i2)).getTile(), 0, false, null, 14, null));
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) arrayList3, (Collection) arrayList2);
    }
}
