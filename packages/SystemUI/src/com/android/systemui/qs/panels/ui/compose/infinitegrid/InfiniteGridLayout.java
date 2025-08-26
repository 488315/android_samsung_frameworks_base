package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.R;
import com.android.systemui.grid.ui.compose.SpannedGridsKt;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import com.android.systemui.qs.panels.ui.compose.BounceableInfo;
import com.android.systemui.qs.panels.ui.compose.BounceableInfoKt;
import com.android.systemui.qs.panels.ui.compose.EditTileListState;
import com.android.systemui.qs.panels.ui.compose.PaginatableGridLayout;
import com.android.systemui.qs.panels.ui.compose.TileListenerKt;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.EditTileViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.InfiniteGridViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.ui.ElementKeys;
import com.android.systemui.qs.shared.ui.TileIdentity;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function7;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class InfiniteGridLayout implements PaginatableGridLayout {
    public final DetailsViewModel detailsViewModel;
    public final IconTilesViewModel iconTilesViewModel;
    public final TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider;
    public final InfiniteGridViewModel.Factory viewModelFactory;

    public InfiniteGridLayout(DetailsViewModel detailsViewModel, IconTilesViewModel iconTilesViewModel, InfiniteGridViewModel.Factory factory, TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider) {
        this.detailsViewModel = detailsViewModel;
        this.iconTilesViewModel = iconTilesViewModel;
        this.viewModelFactory = factory;
        this.tileHapticsViewModelFactoryProvider = tileHapticsViewModelFactoryProvider;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0030  */
    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void EditTileGrid(List list, Modifier.Companion companion, Function2 function2, Function1 function1, Function1 function12, Function0 function0, Composer composer) {
        Object obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1759944283);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.EditTileGrid (InfiniteGridLayout.kt:136)");
        }
        composerImpl.startReplaceGroup(601475729);
        boolean zChangedInstance = composerImpl.changedInstance(this);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion2 = Composer.Companion;
        if (!zChangedInstance) {
            companion2.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new InfiniteGridLayout$$ExternalSyntheticLambda0(this, 0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        InfiniteGridViewModel infiniteGridViewModel = (InfiniteGridViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLayout.EditTileGrid", null, (Function0) objRememberedValue, composerImpl, 6, 2);
        composerImpl.startReplaceGroup(601481131);
        boolean zChangedInstance2 = composerImpl.changedInstance(infiniteGridViewModel);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            companion2.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new InfiniteGridLayout$$ExternalSyntheticLambda1(infiniteGridViewModel, 0);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        DynamicIconTilesViewModel dynamicIconTilesViewModel = (DynamicIconTilesViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLayout.EditTileGrid", null, (Function0) objRememberedValue2, composerImpl, 6, 2);
        composerImpl.startReplaceGroup(601487295);
        boolean zChangedInstance3 = composerImpl.changedInstance(infiniteGridViewModel);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChangedInstance3) {
            companion2.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                objRememberedValue3 = new InfiniteGridLayout$$ExternalSyntheticLambda1(infiniteGridViewModel, 1);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
        }
        composerImpl.end(false);
        int columns = ((QSColumnsViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLayout.EditTileGrid", null, (Function0) objRememberedValue3, composerImpl, 6, 2)).getColumns();
        State state = dynamicIconTilesViewModel.largeTilesSpanState;
        MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(dynamicIconTilesViewModel.$$delegate_0.getLargeTiles(), composerImpl);
        Object obj2 = (Set) mutableStateCollectAsStateWithLifecycle.getValue();
        int iIntValue = ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue();
        composerImpl.startReplaceGroup(601500789);
        boolean zChanged = composerImpl.changed(obj2) | composerImpl.changed(list) | composerImpl.changed(iIntValue);
        Object objRememberedValue4 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion2.getClass();
            obj = objRememberedValue4;
            if (objRememberedValue4 == Composer.Companion.Empty) {
                List<EditTileViewModel> list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (EditTileViewModel editTileViewModel : list2) {
                    arrayList.add(new SizedTileImpl(editTileViewModel, (editTileViewModel.isCurrent && ((Set) mutableStateCollectAsStateWithLifecycle.getValue()).contains(editTileViewModel.tileSpec)) ? ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue() : 1));
                }
                composerImpl.updateRememberedValue(arrayList);
                obj = arrayList;
            }
        }
        composerImpl.end(false);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : (List) obj) {
            if (((EditTileViewModel) ((SizedTileImpl) obj3).tile).isCurrent) {
                arrayList2.add(obj3);
            } else {
                arrayList3.add(obj3);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list3 = (List) pair.component1();
        List list4 = (List) pair.component2();
        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) state;
        int iIntValue2 = ((Number) snapshotMutableStateImpl.getValue()).intValue();
        composerImpl.startReplaceGroup(-1696966392);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.rememberEditListState (EditTileListState.kt:44)");
        }
        composerImpl.startReplaceGroup(533409414);
        boolean zChanged2 = composerImpl.changed(list3) | composerImpl.changed(columns);
        Object objRememberedValue5 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion2.getClass();
            if (objRememberedValue5 == Composer.Companion.Empty) {
                objRememberedValue5 = new EditTileListState(list3, columns, iIntValue2);
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
        }
        EditTileListState editTileListState = (EditTileListState) objRememberedValue5;
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        composerImpl.startReplaceGroup(601526194);
        boolean zChangedInstance4 = composerImpl.changedInstance(dynamicIconTilesViewModel);
        Object objRememberedValue6 = composerImpl.rememberedValue();
        if (!zChangedInstance4) {
            companion2.getClass();
            if (objRememberedValue6 == Composer.Companion.Empty) {
                objRememberedValue6 = new InfiniteGridLayout$EditTileGrid$2$1(dynamicIconTilesViewModel);
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
        }
        KFunction kFunction = (KFunction) objRememberedValue6;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(601529170);
        boolean zChangedInstance5 = composerImpl.changedInstance(infiniteGridViewModel);
        Object objRememberedValue7 = composerImpl.rememberedValue();
        if (!zChangedInstance5) {
            companion2.getClass();
            if (objRememberedValue7 == Composer.Companion.Empty) {
                objRememberedValue7 = new InfiniteGridLayout$EditTileGrid$3$1(infiniteGridViewModel);
                composerImpl.updateRememberedValue(objRememberedValue7);
            }
        }
        composerImpl.end(false);
        EditTileKt.DefaultEditTileGrid(editTileListState, list4, columns, ((Number) snapshotMutableStateImpl.getValue()).intValue(), companion, function2, function1, function12, (Function2) kFunction, function0, (Function0) ((KFunction) objRememberedValue7), composerImpl, 24576);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0170  */
    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void TileGrid(final ContentScope contentScope, List list, final Function0 function0, Composer composer, int i) {
        Object obj;
        Object obj2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1101960128);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.TileGrid (InfiniteGridLayout.kt:64)");
        }
        composerImpl.startReplaceGroup(-746259417);
        boolean z = (((57344 & i) ^ 24576) > 16384 && composerImpl.changedInstance(this)) || (i & 24576) == 16384;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new InfiniteGridLayout$$ExternalSyntheticLambda0(this, 1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        InfiniteGridViewModel infiniteGridViewModel = (InfiniteGridViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLayout.TileGrid", null, (Function0) objRememberedValue, composerImpl, 6, 2);
        composerImpl.startReplaceGroup(-746254143);
        boolean zChangedInstance = composerImpl.changedInstance(infiniteGridViewModel);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new InfiniteGridLayout$$ExternalSyntheticLambda1(infiniteGridViewModel, 2);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        final DynamicIconTilesViewModel dynamicIconTilesViewModel = (DynamicIconTilesViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLayout.TileGrid", null, (Function0) objRememberedValue2, composerImpl, 6, 2);
        composerImpl.startReplaceGroup(-746247828);
        boolean zChangedInstance2 = composerImpl.changedInstance(infiniteGridViewModel);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            companion.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                objRememberedValue3 = new InfiniteGridLayout$$ExternalSyntheticLambda1(infiniteGridViewModel, 3);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
        }
        composerImpl.end(false);
        final int columns = ((QSColumnsViewModel) SysUiViewModelKt.rememberViewModel("InfiniteGridLAyout.TileGrid", null, (Function0) objRememberedValue3, composerImpl, 6, 2)).getColumns();
        State state = dynamicIconTilesViewModel.largeTilesState;
        State state2 = dynamicIconTilesViewModel.largeTilesSpanState;
        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) state;
        Object obj3 = (Set) snapshotMutableStateImpl.getValue();
        int iIntValue = ((Number) ((SnapshotMutableStateImpl) state2).getValue()).intValue();
        composerImpl.startReplaceGroup(-746234474);
        boolean zChanged = composerImpl.changed(obj3) | composerImpl.changed(list) | composerImpl.changed(iIntValue);
        Object objRememberedValue4 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            obj = objRememberedValue4;
            if (objRememberedValue4 == Composer.Companion.Empty) {
                List<TileViewModel> list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (TileViewModel tileViewModel : list2) {
                    arrayList.add(new SizedTileImpl(tileViewModel, ((Set) snapshotMutableStateImpl.getValue()).contains(tileViewModel.spec) ? ((Number) ((SnapshotMutableStateImpl) state2).getValue()).intValue() : 1));
                }
                composerImpl.updateRememberedValue(arrayList);
                obj = arrayList;
            }
        }
        final List list3 = (List) obj;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-746226950);
        boolean zChanged2 = composerImpl.changed(list3);
        Object objRememberedValue5 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion.getClass();
            obj2 = objRememberedValue5;
            if (objRememberedValue5 == Composer.Companion.Empty) {
                int size = list3.size();
                ArrayList arrayList2 = new ArrayList(size);
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList2.add(new BounceableTileViewModel());
                }
                composerImpl.updateRememberedValue(arrayList2);
                obj2 = arrayList2;
            }
        }
        final List list4 = (List) obj2;
        composerImpl.end(false);
        final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(infiniteGridViewModel.squishinessViewModel.squishiness, composerImpl);
        Object objRememberedValue6 = composerImpl.rememberedValue();
        companion.getClass();
        Object obj4 = Composer.Companion.Empty;
        if (objRememberedValue6 == obj4) {
            objRememberedValue6 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
            composerImpl.updateRememberedValue(objRememberedValue6);
        }
        final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue6;
        composerImpl.startReplaceGroup(-746219175);
        boolean zChanged3 = composerImpl.changed(list3);
        Object objRememberedValue7 = composerImpl.rememberedValue();
        if (zChanged3 || objRememberedValue7 == obj4) {
            objRememberedValue7 = SnapshotStateKt.derivedStateOf(new InfiniteGridLayout$$ExternalSyntheticLambda6(list3, 0));
            composerImpl.updateRememberedValue(objRememberedValue7);
        }
        composerImpl.end(false);
        float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_horizontal, composerImpl);
        float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl);
        List list5 = (List) ((State) objRememberedValue7).getValue();
        composerImpl.startReplaceGroup(-746208278);
        boolean zChangedInstance3 = composerImpl.changedInstance(list3);
        Object objRememberedValue8 = composerImpl.rememberedValue();
        if (zChangedInstance3 || objRememberedValue8 == obj4) {
            objRememberedValue8 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj5) {
                    return ((TileViewModel) ((SizedTileImpl) list3.get(((Integer) obj5).intValue())).tile).spec;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue8);
        }
        composerImpl.end(false);
        SpannedGridsKt.m2584VerticalSpannedGridKhTvWYU(columns, fDimensionResource, fDimensionResource2, list5, null, (Function1) objRememberedValue8, ComposableLambdaKt.rememberComposableLambda(-1998267123, new Function7() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.TileGrid.2
            /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
            @Override // kotlin.jvm.functions.Function7
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11) {
                int i3;
                final int iIntValue2 = ((Number) obj6).intValue();
                final int iIntValue3 = ((Number) obj7).intValue();
                final boolean zBooleanValue = ((Boolean) obj8).booleanValue();
                final boolean zBooleanValue2 = ((Boolean) obj9).booleanValue();
                Composer composer2 = (Composer) obj10;
                int iIntValue4 = ((Number) obj11).intValue();
                if ((iIntValue4 & 48) == 0) {
                    i3 = (((ComposerImpl) composer2).changed(iIntValue2) ? 32 : 16) | iIntValue4;
                } else {
                    i3 = iIntValue4;
                }
                if ((iIntValue4 & 384) == 0) {
                    i3 |= ((ComposerImpl) composer2).changed(iIntValue3) ? 256 : 128;
                }
                if ((iIntValue4 & 3072) == 0) {
                    i3 |= ((ComposerImpl) composer2).changed(zBooleanValue) ? 2048 : 1024;
                }
                if ((iIntValue4 & 24576) == 0) {
                    i3 |= ((ComposerImpl) composer2).changed(zBooleanValue2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((74897 & i3) == 74896) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.TileGrid.<anonymous> (InfiniteGridLayout.kt:101)");
                        }
                        final SizedTileImpl sizedTileImpl = (SizedTileImpl) list3.get(iIntValue2);
                        ElementKeys elementKeys = ElementKeys.INSTANCE;
                        TileSpec tileSpec = ((TileViewModel) sizedTileImpl.tile).spec;
                        elementKeys.getClass();
                        ElementKey elementKey = new ElementKey(tileSpec.getSpec(), new TileIdentity(tileSpec, iIntValue2), null, false, 12, null);
                        Modifier.Companion companion2 = Modifier.Companion;
                        final List list6 = list4;
                        final CoroutineScope coroutineScope2 = coroutineScope;
                        final Function0 function02 = function0;
                        final DynamicIconTilesViewModel dynamicIconTilesViewModel2 = dynamicIconTilesViewModel;
                        final InfiniteGridLayout infiniteGridLayout = this;
                        final int i4 = columns;
                        final State state3 = mutableStateCollectAsStateWithLifecycle;
                        contentScope.Element(elementKey, companion2, ComposableLambdaKt.rememberComposableLambda(-63831307, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.TileGrid.2.1
                            /* JADX WARN: Removed duplicated region for block: B:15:0x0076  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj12, Object obj13, Object obj14) {
                                Composer composer3 = (Composer) obj13;
                                if ((((Number) obj14).intValue() & 17) == 16) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout.TileGrid.<anonymous>.<anonymous> (InfiniteGridLayout.kt:104)");
                                        }
                                        TileViewModel tileViewModel2 = (TileViewModel) sizedTileImpl.tile;
                                        boolean zIsIconTile = dynamicIconTilesViewModel2.$$delegate_0.isIconTile(tileViewModel2.spec);
                                        InfiniteGridLayout infiniteGridLayout2 = infiniteGridLayout;
                                        TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider = infiniteGridLayout2.tileHapticsViewModelFactoryProvider;
                                        BounceableInfo bounceableInfo = BounceableInfoKt.bounceableInfo(list6, sizedTileImpl, iIntValue2, iIntValue3, i4, zBooleanValue, zBooleanValue2);
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        composerImpl4.startReplaceGroup(-998046053);
                                        State state4 = state3;
                                        boolean zChanged4 = composerImpl4.changed(state4);
                                        Object objRememberedValue9 = composerImpl4.rememberedValue();
                                        if (!zChanged4) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue9 == Composer.Companion.Empty) {
                                                objRememberedValue9 = new InfiniteGridLayout$$ExternalSyntheticLambda6(state4, 1);
                                                composerImpl4.updateRememberedValue(objRememberedValue9);
                                            }
                                            composerImpl4.end(false);
                                            TileKt.Tile(tileViewModel2, zIsIconTile, (Function0) objRememberedValue9, coroutineScope2, bounceableInfo, tileHapticsViewModelFactoryProvider, null, function02, infiniteGridLayout2.detailsViewModel, composerImpl4, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
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
        }, composerImpl), composerImpl, 1572864, 16);
        TileListenerKt.TileListener(list, function0, composerImpl, ((i >> 3) & 14) | ((i >> 6) & 112));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
