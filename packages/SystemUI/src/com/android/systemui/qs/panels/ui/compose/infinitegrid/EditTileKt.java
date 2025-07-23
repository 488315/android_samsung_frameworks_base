package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.draganddrop.DragAndDropTargetKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridIntervalContent;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.grid.LazyGridStateKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ClearKt;
import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
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
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRectKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.gesture.effect.OffsetOverscrollEffectFactory;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileList$target$1$1;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState;
import com.android.systemui.qs.panels.ui.compose.selection.PlacementEvent;
import com.android.systemui.qs.panels.ui.model.AvailableTileGridCell;
import com.android.systemui.qs.panels.ui.model.GridCell;
import com.android.systemui.qs.panels.ui.model.TileGridCell;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class EditTileKt {
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0068, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00cf, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0103, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L49;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AutoScrollGrid(final com.android.systemui.qs.panels.ui.compose.EditTileListState r9, final androidx.compose.foundation.ScrollState r10, final androidx.compose.foundation.layout.PaddingValues r11, androidx.compose.runtime.Composer r12, final int r13) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.AutoScrollGrid(com.android.systemui.qs.panels.ui.compose.EditTileListState, androidx.compose.foundation.ScrollState, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c0, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x021b, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L67;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AvailableTileGrid(final androidx.compose.runtime.snapshots.SnapshotStateList r27, final com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r28, final int r29, final kotlin.jvm.functions.Function1 r30, final com.android.systemui.qs.panels.ui.compose.DragAndDropState r31, androidx.compose.runtime.Composer r32, final int r33) {
        /*
            Method dump skipped, instructions count: 1020
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.AvailableTileGrid(androidx.compose.runtime.snapshots.SnapshotStateList, com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState, int, kotlin.jvm.functions.Function1, com.android.systemui.qs.panels.ui.compose.DragAndDropState, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00fb, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0125, code lost:
    
        if (r15 == androidx.compose.runtime.Composer.Companion.Empty) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0184, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7.rememberedValue(), java.lang.Integer.valueOf(r5)) == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0217, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0270, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L97;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0447  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0344  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AvailableTileGridCell(com.android.systemui.qs.panels.ui.model.AvailableTileGridCell r57, final com.android.systemui.qs.panels.ui.compose.DragAndDropState r58, final com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r59, kotlin.jvm.functions.Function1 r60, final androidx.compose.ui.Modifier r61, androidx.compose.runtime.Composer r62, final int r63) {
        /*
            Method dump skipped, instructions count: 1126
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.AvailableTileGridCell(com.android.systemui.qs.panels.ui.model.AvailableTileGridCell, com.android.systemui.qs.panels.ui.compose.DragAndDropState, com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public static final void CategoryHeader(TileCategory tileCategory, Modifier modifier, Composer composer, int i) {
        String stringResource;
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
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m91spacedBy0680j_4(8), vertical, composerImpl, 54);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Painter painterResource = PainterResources_androidKt.painterResource(tileCategory.getIconId(), composerImpl, 0);
            MaterialTheme.INSTANCE.getClass();
            IconKt.m269Iconww6aTOc(painterResource, (String) null, (Modifier) null, MaterialTheme.getColorScheme(composerImpl).onSurface, composerImpl, 48, 4);
            Text label = tileCategory.getLabel();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.load (TextExt.kt:30)");
            }
            if (label instanceof Text.Loaded) {
                stringResource = ((Text.Loaded) label).text;
            } else {
                if (!(label instanceof Text.Resource)) {
                    throw new NoWhenBranchMatchedException();
                }
                stringResource = StringResources_androidKt.stringResource(((Text.Resource) label).res, composerImpl);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (stringResource == null) {
                stringResource = "";
            }
            TextKt.m316Text4IGK_g(stringResource, null, MaterialTheme.getColorScheme(composerImpl).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleMediumEmphasized, composerImpl, 0, 0, 65530);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 1, tileCategory, modifier);
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
            final MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(editTileListState, composerImpl2);
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
            State m8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI((f3 * f6) + f5, null, "QSEditCurrentTilesGridHeight", composerImpl2, 384, 10);
            final LazyGridState rememberLazyGridState = LazyGridStateKt.rememberLazyGridState(0, 0, composerImpl2, 3);
            composerImpl2.startReplaceGroup(247537997);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                i4 = i5;
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl((Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L)));
                composerImpl2.updateRememberedValue(rememberedValue);
            } else {
                i4 = i5;
            }
            MutableState mutableState = (MutableState) rememberedValue;
            composerImpl2.end(false);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            CoroutineScope coroutineScope2 = (CoroutineScope) rememberedValue2;
            final PersistentList persistentList = editTileListState._tiles.getReadable$runtime_release().list;
            MaterialTheme.INSTANCE.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl2).primary;
            GridCells.Fixed fixed = new GridCells.Fixed(i);
            PaddingValuesImpl m119PaddingValues0680j_4 = PaddingKt.m119PaddingValues0680j_4(f3);
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            composerImpl2.startReplaceGroup(247551325);
            boolean changed = composerImpl2.changed(m8animateDpAsStateAjpBEmI);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                coroutineScope = coroutineScope2;
                z = false;
                rememberedValue3 = new EditTileKt$$ExternalSyntheticLambda3(m8animateDpAsStateAjpBEmI, 0);
                composerImpl2.updateRememberedValue(rememberedValue3);
            } else {
                coroutineScope = coroutineScope2;
                z = false;
            }
            composerImpl2.end(z);
            Modifier m28borderxT4_qwU = BorderKt.m28borderxT4_qwU(com.android.compose.modifiers.SizeKt.height(fillMaxWidth, (Function1) rememberedValue3), f6, j, RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(EditModeTileDefaults.GridBackgroundCornerRadius));
            composerImpl2.startReplaceGroup(247559991);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (rememberedValue4 == composer$Companion$Empty$1) {
                z2 = false;
                rememberedValue4 = new EditTileKt$$ExternalSyntheticLambda4(mutableState, 0);
                composerImpl2.updateRememberedValue(rememberedValue4);
            } else {
                z2 = false;
            }
            final Function0 function0 = (Function0) rememberedValue4;
            composerImpl2.end(z2);
            composerImpl2.startReplaceGroup(247561194);
            int i7 = i4 & 112;
            boolean changed2 = ((i4 & 3670016) == 1048576) | composerImpl2.changed(rememberUpdatedState) | (i7 == 32);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue5 == composer$Companion$Empty$1) {
                rememberedValue5 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Function1.this.mo779invoke(((EditTileListState) rememberUpdatedState.getValue()).tileSpecs());
                        mutableSelectionState.setSelection((TileSpec) obj);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue5);
            }
            final Function1 function13 = (Function1) rememberedValue5;
            composerImpl2.end(false);
            int i8 = ((i4 << 9) & 7168) | 384;
            composerImpl2.startReplaceGroup(168293444);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.dragAndDropTileList (DragAndDropState.kt:125)");
            }
            composerImpl2.startReplaceGroup(1748206450);
            boolean z5 = (((i8 & 7168) ^ 3072) > 2048 && composerImpl2.changed(editTileListState)) || (i8 & 3072) == 2048;
            Object rememberedValue6 = composerImpl2.rememberedValue();
            if (z5 || rememberedValue6 == composer$Companion$Empty$1) {
                rememberedValue6 = new DragAndDropTarget() { // from class: com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileList$target$1$1
                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final boolean onDrop(DragAndDropEvent dragAndDropEvent) {
                        EditTileListState editTileListState2 = (EditTileListState) DragAndDropState.this;
                        SizedTile draggedCell = editTileListState2.getDraggedCell();
                        if (draggedCell == null) {
                            return false;
                        }
                        function13.mo779invoke(((EditTileViewModel) draggedCell.getTile()).tileSpec);
                        editTileListState2.onDrop();
                        return true;
                    }

                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final void onEnded(DragAndDropEvent dragAndDropEvent) {
                        ((EditTileListState) DragAndDropState.this).onDrop();
                    }

                    @Override // androidx.compose.ui.draganddrop.DragAndDropTarget
                    public final void onMoved(DragAndDropEvent dragAndDropEvent) {
                        Object obj;
                        int indexOf;
                        int i9;
                        long access$toOffset = DragAndDropStateKt.access$toOffset(dragAndDropEvent);
                        EditTileListState editTileListState2 = (EditTileListState) DragAndDropState.this;
                        editTileListState2.m2888setDraggedPositionk4lQ0M(access$toOffset);
                        long m400minusMKHz9U = Offset.m400minusMKHz9U(access$toOffset, ((Offset) function0.invoke()).packedValue);
                        Iterator it = ((LazyGridMeasureResult) rememberLazyGridState.getLayoutInfo()).visibleItemsInfo.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                obj = null;
                                break;
                            }
                            obj = it.next();
                            LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) ((LazyGridItemInfo) obj);
                            if (IntRectKt.toRect(IntRectKt.m858IntRectVbeCjmY(lazyGridMeasuredItem.offset, lazyGridMeasuredItem.size)).m405containsk4lQ0M(m400minusMKHz9U)) {
                                break;
                            }
                        }
                        LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) obj;
                        if (lazyGridItemInfo != null) {
                            LazyGridMeasuredItem lazyGridMeasuredItem2 = (LazyGridMeasuredItem) lazyGridItemInfo;
                            long j2 = lazyGridMeasuredItem2.offset;
                            IntOffset.Companion companion2 = IntOffset.Companion;
                            boolean z6 = lazyGridMeasuredItem2.span != 1 && ((double) Float.intBitsToFloat((int) (m400minusMKHz9U >> 32))) > (((double) ((int) (lazyGridMeasuredItem2.size >> 32))) * 0.75d) + ((double) ((int) (j2 >> 32)));
                            SizedTile draggedCell = editTileListState2.getDraggedCell();
                            if (draggedCell == null || (indexOf = editTileListState2.indexOf(((EditTileViewModel) draggedCell.getTile()).tileSpec)) == (i9 = lazyGridMeasuredItem2.index)) {
                                return;
                            }
                            if (z6) {
                                i9++;
                            }
                            SnapshotStateList snapshotStateList = editTileListState2._tiles;
                            if (indexOf != -1) {
                                GridCell gridCell2 = (GridCell) snapshotStateList.remove(indexOf);
                                editTileListState2.regenerateGrid();
                                snapshotStateList.add(RangesKt___RangesKt.coerceIn(i9, 0, snapshotStateList.size()), gridCell2);
                            } else {
                                snapshotStateList.add(RangesKt___RangesKt.coerceIn(i9, 0, snapshotStateList.size()), new TileGridCell(draggedCell, 0, 0));
                            }
                            editTileListState2.regenerateGrid();
                        }
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue6);
            }
            DragAndDropStateKt$dragAndDropTileList$target$1$1 dragAndDropStateKt$dragAndDropTileList$target$1$1 = (DragAndDropStateKt$dragAndDropTileList$target$1$1) rememberedValue6;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1748250807);
            if (m == composer$Companion$Empty$1) {
                z3 = true;
                m = new DragAndDropStateKt$$ExternalSyntheticLambda0(1);
                composerImpl2.updateRememberedValue(m);
            } else {
                z3 = true;
            }
            composerImpl2.end(false);
            Modifier dragAndDropTarget = DragAndDropTargetKt.dragAndDropTarget(m28borderxT4_qwU, (Function1) m, dragAndDropStateKt$dragAndDropTileList$target$1$1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 247566761);
            if (m2 == composer$Companion$Empty$1) {
                m2 = new EditTileKt$$ExternalSyntheticLambda6(mutableState, 0);
                composerImpl2.updateRememberedValue(m2);
            }
            composerImpl2.end(false);
            Modifier onGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(dragAndDropTarget, (Function1) m2);
            composerImpl2.startReplaceGroup(247571122);
            boolean changed3 = composerImpl2.changed(j);
            Object rememberedValue7 = composerImpl2.rememberedValue();
            if (changed3 || rememberedValue7 == composer$Companion$Empty$1) {
                z4 = false;
                rememberedValue7 = new EditTileKt$$ExternalSyntheticLambda7(j, 0);
                composerImpl2.updateRememberedValue(rememberedValue7);
            } else {
                z4 = false;
            }
            composerImpl2.end(z4);
            Modifier testTag = TestTagKt.testTag(DrawModifierKt.drawBehind(onGloballyPositioned, (Function1) rememberedValue7), "CurrentTilesGrid");
            composerImpl2.startReplaceGroup(247581275);
            final CoroutineScope coroutineScope3 = coroutineScope;
            int i9 = i4;
            boolean changedInstance = (i6 == 4 ? z3 : false) | composerImpl2.changedInstance(persistentList) | (i7 == 32 ? z3 : false) | composerImpl2.changedInstance(coroutineScope3) | ((i9 & 7168) == 2048 ? z3 : false) | ((458752 & i9) == 131072 ? z3 : false) | composerImpl2.changed(rememberUpdatedState);
            if ((57344 & i9) != 16384) {
                z3 = false;
            }
            boolean z6 = changedInstance | z3;
            Object rememberedValue8 = composerImpl2.rememberedValue();
            if (z6 || rememberedValue8 == composer$Companion$Empty$1) {
                composerImpl = composerImpl2;
                lazyGridState = rememberLazyGridState;
                Function1 function14 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        final EditTileKt$$ExternalSyntheticLambda13 editTileKt$$ExternalSyntheticLambda13 = new EditTileKt$$ExternalSyntheticLambda13(function2, rememberUpdatedState, 0);
                        final PersistentList persistentList2 = PersistentList.this;
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
                            /* JADX WARN: Code restructure failed: missing block: B:46:0x012d, code lost:
                            
                                if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L46;
                             */
                            @Override // kotlin.jvm.functions.Function4
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object invoke(java.lang.Object r15, java.lang.Object r16, java.lang.Object r17, java.lang.Object r18) {
                                /*
                                    Method dump skipped, instructions count: 355
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditTiles$4.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                            }
                        }));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(function14);
                rememberedValue8 = function14;
            } else {
                composerImpl = composerImpl2;
                lazyGridState = rememberLazyGridState;
            }
            composerImpl.end(false);
            composerImpl2 = composerImpl;
            TileKt.TileLazyGrid(fixed, testTag, lazyGridState, m119PaddingValues0680j_4, (Function1) rememberedValue8, composerImpl2, 3072);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(mutableSelectionState, i, i2, function2, function1, function12, i3) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda9
                public final /* synthetic */ MutableSelectionState f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ int f$3;
                public final /* synthetic */ Function2 f$4;
                public final /* synthetic */ Function1 f$5;
                public final /* synthetic */ Function1 f$6;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function1 function15 = this.f$5;
                    Function1 function16 = this.f$6;
                    EditTileKt.CurrentTilesGrid(EditTileListState.this, this.f$1, this.f$2, this.f$3, this.f$4, function15, function16, (Composer) obj, updateChangedFlags);
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(EditModeHeaderState.Idle);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            boolean z = true;
            Boolean valueOf = Boolean.valueOf(editTileListState.getDraggedCell() != null);
            Boolean valueOf2 = Boolean.valueOf(mutableSelectionState.getSelection() != null);
            Boolean valueOf3 = Boolean.valueOf(mutableSelectionState.getPlacementEnabled());
            composerImpl.startReplaceGroup(-1990359929);
            boolean z2 = (((i2 & 14) ^ 6) > 4 && composerImpl.changed(editTileListState)) || (i2 & 6) == 4;
            if ((((i2 & 112) ^ 48) <= 32 || !composerImpl.changed(mutableSelectionState)) && (i2 & 48) != 32) {
                z = false;
            }
            boolean z3 = z2 | z;
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (z3 || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new EditTileKt$rememberEditModeState$1$1(editTileListState, mutableSelectionState, mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(valueOf, valueOf2, valueOf3, (Function2) rememberedValue2, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            EditModeHeaderState editModeHeaderState = (EditModeHeaderState) mutableState.getValue();
            Alignment.Companion.getClass();
            AnimatedContentKt.AnimatedContent(editModeHeaderState, modifier, null, Alignment.Companion.Center, "QSEditHeader", null, ComposableLambdaKt.rememberComposableLambda(-1716391981, new Function4() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$CurrentTilesGridHeader$1
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    final EditModeHeaderState editModeHeaderState2 = (EditModeHeaderState) obj2;
                    Composer composer2 = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGridHeader.<anonymous> (EditTile.kt:458)");
                    }
                    final MutableSelectionState mutableSelectionState2 = MutableSelectionState.this;
                    final Function1 function12 = function1;
                    EditTileKt.EditGridHeader(48, composer2, ComposableLambdaKt.rememberComposableLambda(582084309, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$CurrentTilesGridHeader$1.1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a4, code lost:
                        
                            if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                         */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r3, java.lang.Object r4, java.lang.Object r5) {
                            /*
                                r2 = this;
                                androidx.compose.foundation.layout.BoxScope r3 = (androidx.compose.foundation.layout.BoxScope) r3
                                androidx.compose.runtime.Composer r4 = (androidx.compose.runtime.Composer) r4
                                java.lang.Number r5 = (java.lang.Number) r5
                                int r3 = r5.intValue()
                                r3 = r3 & 17
                                r5 = 16
                                if (r3 != r5) goto L1f
                                r3 = r4
                                androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                                boolean r5 = r3.getSkipping()
                                if (r5 != 0) goto L1a
                                goto L1f
                            L1a:
                                r3.skipToGroupEnd()
                                goto Lc3
                            L1f:
                                boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r3 == 0) goto L2a
                                java.lang.String r3 = "com.android.systemui.qs.panels.ui.compose.infinitegrid.CurrentTilesGridHeader.<anonymous>.<anonymous> (EditTile.kt:459)"
                                androidx.compose.runtime.ComposerKt.traceEventStart(r3)
                            L2a:
                                int[] r3 = com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$CurrentTilesGridHeader$1.AnonymousClass1.WhenMappings.$EnumSwitchMapping$0
                                com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState r5 = com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeHeaderState.this
                                int r5 = r5.ordinal()
                                r3 = r3[r5]
                                r5 = 1
                                r0 = 0
                                if (r3 == r5) goto L7c
                                r2 = 2
                                r5 = 0
                                if (r3 == r2) goto L66
                                r2 = 3
                                if (r3 != r2) goto L55
                                androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                                r2 = -91172339(0xfffffffffa90d20d, float:-3.7597554E35)
                                r4.startReplaceGroup(r2)
                                r2 = 2131953157(0x7f130605, float:1.9542777E38)
                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r2, r4)
                                com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditGridCenteredText(r2, r5, r4, r0)
                                r4.end(r0)
                                goto Lba
                            L55:
                                androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                                r2 = 274137143(0x10570037, float:4.240144E-29)
                                r4.startReplaceGroup(r2)
                                r4.end(r0)
                                kotlin.NoWhenBranchMatchedException r2 = new kotlin.NoWhenBranchMatchedException
                                r2.<init>()
                                throw r2
                            L66:
                                androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                                r2 = -91336546(0xfffffffffa8e509e, float:-3.6947063E35)
                                r4.startReplaceGroup(r2)
                                r2 = 2131957063(0x7f131547, float:1.95507E38)
                                java.lang.String r2 = androidx.compose.ui.res.StringResources_androidKt.stringResource(r2, r4)
                                com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditGridCenteredText(r2, r5, r4, r0)
                                r4.end(r0)
                                goto Lba
                            L7c:
                                androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                                r3 = -91636688(0xfffffffffa89bc30, float:-3.575808E35)
                                r4.startReplaceGroup(r3)
                                r3 = 274139833(0x10570ab9, float:4.2409534E-29)
                                r4.startReplaceGroup(r3)
                                com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r3 = r2
                                boolean r5 = r4.changed(r3)
                                kotlin.jvm.functions.Function1 r2 = r3
                                boolean r1 = r4.changed(r2)
                                r5 = r5 | r1
                                java.lang.Object r1 = r4.rememberedValue()
                                if (r5 != 0) goto La6
                                androidx.compose.runtime.Composer$Companion r5 = androidx.compose.runtime.Composer.Companion
                                r5.getClass()
                                androidx.compose.runtime.Composer$Companion$Empty$1 r5 = androidx.compose.runtime.Composer.Companion.Empty
                                if (r1 != r5) goto Laf
                            La6:
                                com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda39 r1 = new com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda39
                                r5 = 1
                                r1.<init>(r5, r3, r2)
                                r4.updateRememberedValue(r1)
                            Laf:
                                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                                r4.end(r0)
                                com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.RemoveTileTarget(r1, r4, r0)
                                r4.end(r0)
                            Lba:
                                boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                                if (r2 == 0) goto Lc3
                                androidx.compose.runtime.ComposerKt.traceEventEnd()
                            Lc3:
                                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                                return r2
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$CurrentTilesGridHeader$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda2(editTileListState, mutableSelectionState, function1, modifier, i);
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
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new MutableSelectionState();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final MutableSelectionState mutableSelectionState = (MutableSelectionState) rememberedValue;
            int i5 = i4;
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1889393423);
            if (function02 != null) {
                composerImpl2.startReplaceGroup(-1889392382);
                boolean changed = composerImpl2.changed(mutableSelectionState) | ((c & 14) == 4);
                Object rememberedValue2 = composerImpl2.rememberedValue();
                if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = new EditTileKt$$ExternalSyntheticLambda39(0, mutableSelectionState, function02);
                    composerImpl2.updateRememberedValue(rememberedValue2);
                }
                function03 = (Function0) rememberedValue2;
                z = false;
                composerImpl2.end(false);
            } else {
                z = false;
                function03 = null;
            }
            composerImpl2.end(z);
            PlacementEvent placementEvent = (PlacementEvent) ((SnapshotMutableStateImpl) mutableSelectionState.placementEvent$delegate).getValue();
            composerImpl2.startReplaceGroup(-1889386490);
            boolean changed2 = composerImpl2.changed(mutableSelectionState) | ((i5 & 14) == 4) | ((i5 & 458752) == 131072);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new EditTileKt$DefaultEditTileGrid$1$1(mutableSelectionState, editTileListState, function2, null);
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, placementEvent, (Function2) rememberedValue3);
            Color.Companion.getClass();
            long j = Color.Transparent;
            ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-50093261, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$DefaultEditTileGrid$2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.DefaultEditTileGrid.<anonymous> (EditTile.kt:264)");
                    }
                    EditTileKt.EditModeTopBar(Function0.this, function03, composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2);
            final Function1 function15 = function13;
            final Function1 function16 = function14;
            composerImpl = composerImpl2;
            ScaffoldKt.m281ScaffoldTvnljyQ(null, rememberComposableLambda, null, null, null, 0, j, 0L, null, ComposableLambdaKt.rememberComposableLambda(1878196104, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$DefaultEditTileGrid$3
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    final PaddingValues paddingValues = (PaddingValues) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
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
                    Object rememberedValue4 = composerImpl4.rememberedValue();
                    Composer.Companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                    if (rememberedValue4 == composer$Companion$Empty$12) {
                        rememberedValue4 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl4);
                        composerImpl4.updateRememberedValue(rememberedValue4);
                    }
                    CoroutineScope coroutineScope = (CoroutineScope) rememberedValue4;
                    composerImpl4.startReplaceGroup(1104260892);
                    boolean changed3 = composerImpl4.changed(coroutineScope) | composerImpl4.changed(springSpec);
                    Object rememberedValue5 = composerImpl4.rememberedValue();
                    if (changed3 || rememberedValue5 == composer$Companion$Empty$12) {
                        rememberedValue5 = new OffsetOverscrollEffectFactory(coroutineScope, springSpec);
                        composerImpl4.updateRememberedValue(rememberedValue5);
                    }
                    OffsetOverscrollEffectFactory offsetOverscrollEffectFactory = (OffsetOverscrollEffectFactory) rememberedValue5;
                    composerImpl4.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl4.end(false);
                    ProvidedValue defaultProvidedValue$runtime_release = computedProvidableCompositionLocal.defaultProvidedValue$runtime_release(offsetOverscrollEffectFactory);
                    final List list2 = list;
                    final int i6 = i;
                    final Function2 function23 = function2;
                    final EditTileListState editTileListState2 = EditTileListState.this;
                    final Modifier modifier = companion;
                    final Function1 function17 = function15;
                    final Function1 function18 = function16;
                    final MutableSelectionState mutableSelectionState2 = mutableSelectionState;
                    final int i7 = i2;
                    final Function2 function24 = function22;
                    CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-503511992, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$DefaultEditTileGrid$3.1
                        /* JADX WARN: Code restructure failed: missing block: B:15:0x0063, code lost:
                        
                            if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:19:0x00d3, code lost:
                        
                            if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:26:0x0108, code lost:
                        
                            if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r26, java.lang.Object r27) {
                            /*
                                Method dump skipped, instructions count: 654
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$DefaultEditTileGrid$3.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }, composer2), composer2, 56);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, 806879280, 445);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda40
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i3 | 1);
                    Function0 function04 = function0;
                    Function0 function05 = function02;
                    EditTileKt.DefaultEditTileGrid(EditTileListState.this, list, i, i2, companion, function2, function1, function12, function22, function04, function05, (Composer) obj, updateChangedFlags);
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
            TextKt.m316Text4IGK_g(str, companion2, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).titleSmall, composerImpl, i2 & 126, 0, 65532);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(str, companion2, i);
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
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(MaterialTheme.getColorScheme(composerImpl).onSurface)), ComposableLambdaKt.rememberComposableLambda(-1127019224, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditGridHeader$1
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
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditGridHeader.<anonymous> (EditTile.kt:487)");
                    }
                    Alignment.Companion.getClass();
                    BiasAlignment biasAlignment = Alignment.Companion.Center;
                    Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.this, 1.0f);
                    MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, fillMaxWidth);
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
                    Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    composableLambdaImpl.invoke(BoxScopeInstance.INSTANCE, composer2, 6);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 0, companion, composableLambdaImpl);
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
            TopAppBarColors m318copytNS2XkQ = TopAppBarDefaults.getDefaultTopAppBarColors$material3_release(MaterialTheme.getColorScheme(composerImpl)).m318copytNS2XkQ(j2, j4, j4, j3, j4, j4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(Modifier.Companion, 0.0f, 8, 1);
            ComposableSingletons$EditTileKt.INSTANCE.getClass();
            AppBarKt.m247TopAppBarGHTll3U(ComposableSingletons$EditTileKt.f96lambda1, m126paddingVpY3zN4$default, ComposableLambdaKt.rememberComposableLambda(450710209, new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditModeTopBar$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0044, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r11, java.lang.Object r12) {
                    /*
                        r10 = this;
                        androidx.compose.runtime.Composer r11 = (androidx.compose.runtime.Composer) r11
                        java.lang.Number r12 = (java.lang.Number) r12
                        int r12 = r12.intValue()
                        r12 = r12 & 3
                        r0 = 2
                        if (r12 != r0) goto L1b
                        r12 = r11
                        androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
                        boolean r0 = r12.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1b
                    L17:
                        r12.skipToGroupEnd()
                        goto L76
                    L1b:
                        boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r12 == 0) goto L26
                        java.lang.String r12 = "com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTopBar.<anonymous> (EditTile.kt:195)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r12)
                    L26:
                        androidx.compose.ui.Modifier$Companion r12 = androidx.compose.ui.Modifier.Companion
                        r4 = r11
                        androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                        r11 = -787032097(0xffffffffd116d7df, float:-4.0491676E10)
                        r4.startReplaceGroup(r11)
                        long r0 = r2
                        boolean r11 = r4.changed(r0)
                        java.lang.Object r2 = r4.rememberedValue()
                        if (r11 != 0) goto L46
                        androidx.compose.runtime.Composer$Companion r11 = androidx.compose.runtime.Composer.Companion
                        r11.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r11 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r2 != r11) goto L4f
                    L46:
                        com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda7 r2 = new com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$$ExternalSyntheticLambda7
                        r11 = 2
                        r2.<init>(r0, r11)
                        r4.updateRememberedValue(r2)
                    L4f:
                        kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
                        r11 = 0
                        r4.end(r11)
                        androidx.compose.ui.Modifier r5 = androidx.compose.ui.draw.DrawModifierKt.drawBehind(r12, r2)
                        com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt r11 = com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt.INSTANCE
                        r11.getClass()
                        androidx.compose.runtime.internal.ComposableLambdaImpl r8 = com.android.systemui.qs.panels.ui.compose.infinitegrid.ComposableSingletons$EditTileKt.f97lambda2
                        r0 = 1572864(0x180000, float:2.204052E-39)
                        r1 = 60
                        kotlin.jvm.functions.Function0 r7 = kotlin.jvm.functions.Function0.this
                        r9 = 0
                        r3 = 0
                        r2 = 0
                        r6 = 0
                        androidx.compose.material3.IconButtonKt.IconButton(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
                        boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r10 == 0) goto L76
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L76:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditModeTopBar$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(763420408, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt$EditModeTopBar$2
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
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTopBar.<anonymous> (EditTile.kt:208)");
                    }
                    Function0 function03 = Function0.this;
                    if (function03 != null) {
                        ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                        MaterialTheme.INSTANCE.getClass();
                        long j5 = MaterialTheme.getColorScheme(composer2).primary;
                        long j6 = MaterialTheme.getColorScheme(composer2).onPrimary;
                        buttonDefaults.getClass();
                        ButtonColors m253textButtonColorsro_MJ88 = ButtonDefaults.m253textButtonColorsro_MJ88(j5, j6, composer2, 12);
                        ComposableSingletons$EditTileKt.INSTANCE.getClass();
                        ButtonKt.TextButton(function03, null, false, null, m253textButtonColorsro_MJ88, null, null, null, null, ComposableSingletons$EditTileKt.f98lambda3, composer2, 805306368, 494);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), 0.0f, null, m318copytNS2XkQ, null, composerImpl, 3510, 176);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda10(i, 3, function0, function02);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00fa, code lost:
    
        if (r15 == androidx.compose.runtime.Composer.Companion.Empty) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0126, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01fb, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0225, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x027c, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L108;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void EditTile(final com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel r23, final com.android.systemui.qs.panels.ui.compose.selection.TileState r24, final com.android.systemui.qs.panels.ui.compose.selection.ResizingState r25, final kotlin.jvm.functions.Function0 r26, com.android.systemui.qs.panels.ui.compose.infinitegrid.TileColors r27, androidx.compose.runtime.Composer r28, final int r29) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.EditTile(com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel, com.android.systemui.qs.panels.ui.compose.selection.TileState, com.android.systemui.qs.panels.ui.compose.selection.ResizingState, kotlin.jvm.functions.Function0, com.android.systemui.qs.panels.ui.compose.infinitegrid.TileColors, androidx.compose.runtime.Composer, int):void");
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
            Arrangement.SpacedAligned m92spacedByD5KLDUw = Arrangement.m92spacedByD5KLDUw(f, horizontal);
            Dp.Companion companion2 = Dp.Companion;
            Modifier m124padding3ABfNKs = PaddingKt.m124padding3ABfNKs(BorderKt.m28borderxT4_qwU(ClickableKt.m35clickableXHw0xAI$default(SizeKt.wrapContentSize$default(Modifier.Companion, null, 3), false, null, function0, 7), 1, ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value, RoundedCornerShapeKt.CircleShape), 10);
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m92spacedByD5KLDUw, vertical, composerImpl, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m124padding3ABfNKs);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Icons.INSTANCE.getClass();
            ImageVector imageVector = ClearKt._clear;
            if (imageVector == null) {
                ImageVector.Builder builder = new ImageVector.Builder("Filled.Clear", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                EmptyList emptyList = VectorKt.EmptyPath;
                Color.Companion.getClass();
                SolidColor solidColor = new SolidColor(Color.Black, null);
                StrokeCap.Companion.getClass();
                StrokeJoin.Companion.getClass();
                int i2 = StrokeJoin.Bevel;
                PathBuilder m = ExpandMoreKt$$ExternalSyntheticOutline0.m(19.0f, 6.41f, 17.59f, 5.0f);
                m.lineTo(12.0f, 10.59f);
                m.lineTo(6.41f, 5.0f);
                m.lineTo(5.0f, 6.41f);
                m.lineTo(10.59f, 12.0f);
                m.lineTo(5.0f, 17.59f);
                m.lineTo(6.41f, 19.0f);
                m.lineTo(12.0f, 13.41f);
                m.lineTo(17.59f, 19.0f);
                ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(m, 19.0f, 17.59f, 13.41f, 12.0f);
                builder.m565addPathoIyEayM("", m._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i2, 1.0f, 0.0f, 1.0f, 0.0f);
                imageVector = builder.build();
                ClearKt._clear = imageVector;
            }
            IconKt.m270Iconww6aTOc(imageVector, (String) null, (Modifier) null, 0L, composerImpl, 48, 12);
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.qs_customize_remove, composerImpl), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composerImpl, 0, 0, 131070);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda16(function0, i, 1);
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
            BoxKt.Box(SizeKt.fillMaxWidth(SizeKt.m130height3ABfNKs(modifier, CommonTileDefaults.TileHeight), 1.0f), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new EditTileKt$$ExternalSyntheticLambda16(modifier, i, 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0385  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TileGridCell(final com.android.systemui.qs.panels.ui.model.TileGridCell r30, final int r31, final com.android.systemui.qs.panels.ui.compose.DragAndDropState r32, final com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState r33, final kotlin.jvm.functions.Function1 r34, final kotlin.jvm.functions.Function1 r35, final kotlinx.coroutines.CoroutineScope r36, final int r37, final androidx.compose.ui.Modifier r38, androidx.compose.runtime.Composer r39, final int r40) {
        /*
            Method dump skipped, instructions count: 1118
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.EditTileKt.TileGridCell(com.android.systemui.qs.panels.ui.model.TileGridCell, int, com.android.systemui.qs.panels.ui.compose.DragAndDropState, com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionState, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlinx.coroutines.CoroutineScope, int, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
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
