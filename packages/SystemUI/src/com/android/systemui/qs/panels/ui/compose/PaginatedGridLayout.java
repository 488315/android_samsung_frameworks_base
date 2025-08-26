package com.android.systemui.qs.panels.ui.compose;

import android.content.res.Resources;
import android.view.MotionEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.pager.PagerKt;
import androidx.compose.foundation.pager.PagerState;
import androidx.compose.foundation.pager.PagerStateKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import com.android.systemui.qs.panels.shared.model.TileRow;
import com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout;
import com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.PaginatedGridViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class PaginatedGridLayout implements GridLayout {
    public final PaginatableGridLayout delegateGridLayout;
    public final PaginatedGridViewModel.Factory viewModelFactory;

    public PaginatedGridLayout(PaginatedGridViewModel.Factory factory, PaginatableGridLayout paginatableGridLayout) {
        this.viewModelFactory = factory;
        this.delegateGridLayout = paginatableGridLayout;
    }

    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    public final void EditTileGrid(List list, Modifier.Companion companion, Function2 function2, Function1 function1, Function1 function12, Function0 function0, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1967745697);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout.EditTileGrid (PaginatedGridLayout.kt:0)");
        }
        ((InfiniteGridLayout) this.delegateGridLayout).EditTileGrid(list, companion, function2, function1, function12, function0, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02f3  */
    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void TileGrid(final ContentScope contentScope, List list, final Function0 function0, Composer composer, int i) throws Resources.NotFoundException {
        int i2;
        Object obj;
        final float f;
        float f2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1236405530);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout.TileGrid (PaginatedGridLayout.kt:71)");
        }
        composerImpl.startReplaceGroup(1176188162);
        boolean z = (((57344 & i) ^ 24576) > 16384 && composerImpl.changedInstance(this)) || (i & 24576) == 16384;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new PaginatedGridLayout$$ExternalSyntheticLambda0(this, 0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        final PaginatedGridViewModel paginatedGridViewModel = (PaginatedGridViewModel) SysUiViewModelKt.rememberViewModel("PaginatedGridLayout-TileGrid", null, (Function0) objRememberedValue, composerImpl, 6, 2);
        int columns = paginatedGridViewModel.columnsWithMediaViewModel.getColumns();
        int iIntegerResource = PrimitiveResources_androidKt.integerResource(R.integer.quick_settings_paginated_grid_num_rows, composerImpl);
        composerImpl.startReplaceGroup(1176195184);
        boolean zChanged = composerImpl.changed(list) | composerImpl.changed(columns) | composerImpl.changed(iIntegerResource);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                InfiniteGridLayout infiniteGridLayout = (InfiniteGridLayout) this.delegateGridLayout;
                infiniteGridLayout.getClass();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    TileViewModel tileViewModel = (TileViewModel) it.next();
                    TileSpec tileSpec = tileViewModel.spec;
                    IconTilesViewModel iconTilesViewModel = infiniteGridLayout.iconTilesViewModel;
                    arrayList.add(new SizedTileImpl(tileViewModel, iconTilesViewModel.isIconTile(tileSpec) ? 1 : ((Number) iconTilesViewModel.getLargeTilesSpan().getValue()).intValue()));
                }
                i2 = 1;
                PaginatableGridLayout.Companion.getClass();
                TileRow tileRow = new TileRow(columns);
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    SizedTile sizedTile = (SizedTile) obj2;
                    if (sizedTile.getWidth() > columns) {
                        throw new IllegalStateException("Check failed.");
                    }
                    if (!tileRow.maybeAddTile(sizedTile)) {
                        listBuilderCreateListBuilder.add(CollectionsKt___CollectionsKt.toList(tileRow._tiles));
                        ((ArrayList) tileRow._tiles).clear();
                        tileRow.availableColumns = tileRow.columns;
                        tileRow.maybeAddTile(sizedTile);
                    }
                }
                if (!CollectionsKt___CollectionsKt.toList(tileRow._tiles).isEmpty()) {
                    listBuilderCreateListBuilder.add(CollectionsKt___CollectionsKt.toList(tileRow._tiles));
                }
                List listChunked = CollectionsKt___CollectionsKt.chunked(listBuilderCreateListBuilder.build(), iIntegerResource);
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listChunked, 10));
                ArrayList arrayList3 = (ArrayList) listChunked;
                int size2 = arrayList3.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj3 = arrayList3.get(i4);
                    i4++;
                    List listFlatten = CollectionsKt__IterablesKt.flatten((List) obj3);
                    ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listFlatten, 10));
                    ArrayList arrayList5 = (ArrayList) listFlatten;
                    int size3 = arrayList5.size();
                    int i5 = 0;
                    while (i5 < size3) {
                        Object obj4 = arrayList5.get(i5);
                        i5++;
                        arrayList4.add((TileViewModel) ((SizedTile) obj4).getTile());
                    }
                    arrayList2.add(arrayList4);
                }
                composerImpl.updateRememberedValue(arrayList2);
                obj = arrayList2;
            } else {
                i2 = 1;
                obj = objRememberedValue2;
            }
        }
        final List list2 = (List) obj;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1176200951);
        boolean zChangedInstance = composerImpl.changedInstance(list2);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                objRememberedValue3 = new PaginatedGridLayout$$ExternalSyntheticLambda0(list2, i2);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
        }
        composerImpl.end(false);
        PagerState pagerStateRememberPagerState = PagerStateKt.rememberPagerState((Function0) objRememberedValue3, composerImpl);
        composerImpl.startReplaceGroup(1176203451);
        boolean zChanged2 = ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(function0)) || (i & 3072) == 2048) | composerImpl.changed(pagerStateRememberPagerState);
        Object objRememberedValue4 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion.getClass();
            if (objRememberedValue4 == Composer.Companion.Empty) {
                objRememberedValue4 = new PaginatedGridLayout$TileGrid$1$1(function0, pagerStateRememberPagerState, null);
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(function0, pagerStateRememberPagerState, (Function2) objRememberedValue4, composerImpl);
        composerImpl.startReplaceGroup(1176224180);
        boolean zChanged3 = composerImpl.changed(pagerStateRememberPagerState) | composerImpl.changedInstance(paginatedGridViewModel);
        Object objRememberedValue5 = composerImpl.rememberedValue();
        if (!zChanged3) {
            companion.getClass();
            if (objRememberedValue5 == Composer.Companion.Empty) {
                objRememberedValue5 = new PaginatedGridLayout$TileGrid$2$1(pagerStateRememberPagerState, paginatedGridViewModel, null);
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, pagerStateRememberPagerState, (Function2) objRememberedValue5);
        Modifier.Companion companion2 = Modifier.Companion;
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion2);
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
        Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
        }
        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        if (list2.size() > 1) {
            Dimensions.INSTANCE.getClass();
            f = Dimensions.InterPageSpacing;
        } else {
            f = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        PaddingValuesImpl paddingValuesImplM122PaddingValuesYgX7TsA$default = PaddingKt.m122PaddingValuesYgX7TsA$default(f, 2);
        Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(companion2, "qs_pager");
        composerImpl.startReplaceGroup(1788001644);
        boolean zChanged4 = composerImpl.changed(f);
        Object objRememberedValue6 = composerImpl.rememberedValue();
        if (!zChanged4) {
            companion.getClass();
            if (objRememberedValue6 == Composer.Companion.Empty) {
                objRememberedValue6 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj5) {
                        return Integer.valueOf(-((Density) obj5).mo52roundToPx0680j_4(f));
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
        }
        composerImpl.end(false);
        Modifier modifierPadding$default = com.android.compose.modifiers.PaddingKt.padding$default(modifierSysuiResTag, (Function1) objRememberedValue6, null, 2);
        composerImpl.startReplaceGroup(1788004544);
        boolean zChangedInstance2 = composerImpl.changedInstance(paginatedGridViewModel);
        Object objRememberedValue7 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            companion.getClass();
            if (objRememberedValue7 == Composer.Companion.Empty) {
                objRememberedValue7 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj5) {
                        if (((MotionEvent) obj5).getActionMasked() == 1) {
                            paginatedGridViewModel.falsingInteractor.manager.isFalseTouch(15);
                        }
                        return Boolean.FALSE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue7);
            }
        }
        composerImpl.end(false);
        Modifier modifierPointerInteropFilter$default = PointerInteropFilter_androidKt.pointerInteropFilter$default(modifierPadding$default, (Function1) objRememberedValue7);
        if (list2.size() > 1) {
            Dimensions.INSTANCE.getClass();
            f2 = Dimensions.InterPageSpacing;
        } else {
            Dp.Companion companion4 = Dp.Companion;
            f2 = 0;
        }
        PagerKt.m178HorizontalPager8jOkeI(pagerStateRememberPagerState, modifierPointerInteropFilter$default, paddingValuesImplM122PaddingValuesYgX7TsA$default, null, 1, f2, Alignment.Companion.Top, null, false, false, null, null, null, null, ComposableLambdaKt.rememberComposableLambda(1676058243, new Function4() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout$TileGrid$3$3
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj5, Object obj6, Object obj7, Object obj8) {
                int iIntValue = ((Number) obj6).intValue();
                Composer composer2 = (Composer) obj7;
                ((Number) obj8).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout.TileGrid.<anonymous>.<anonymous> (PaginatedGridLayout.kt:134)");
                }
                List list3 = (List) list2.get(iIntValue);
                PaginatableGridLayout paginatableGridLayout = this.delegateGridLayout;
                Modifier.Companion companion5 = Modifier.Companion;
                ((InfiniteGridLayout) paginatableGridLayout).TileGrid(contentScope, list3, function0, composer2, 384);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 1597440, 24576, 16264);
        PaginatedGridLayoutKt.FooterBar(paginatedGridViewModel.buildNumberViewModelFactory, pagerStateRememberPagerState, paginatedGridViewModel.editModeButtonViewModelFactory, composerImpl, 0);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
