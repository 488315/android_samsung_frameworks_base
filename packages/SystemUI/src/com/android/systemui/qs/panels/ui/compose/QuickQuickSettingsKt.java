package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.R;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.grid.ui.compose.SpannedGridsKt;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider;
import com.android.systemui.qs.composefragment.ui.GridAnchorKt;
import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.ui.ElementKeys;
import com.android.systemui.qs.shared.ui.TileIdentity;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public abstract class QuickQuickSettingsKt {
    /* JADX WARN: Removed duplicated region for block: B:30:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void QuickQuickSettings(final ContentScope contentScope, final QuickQuickSettingsViewModel quickQuickSettingsViewModel, Modifier modifier, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl;
        final Modifier modifier2;
        final Function0 function02 = function0;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-362513511);
        int i2 = i | (composerImpl2.changed(contentScope) ? 4 : 2) | (composerImpl2.changedInstance(quickQuickSettingsViewModel) ? 32 : 16) | 384 | (composerImpl2.changedInstance(function02) ? 2048 : 1024);
        if ((i2 & 1171) == 1170 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            modifier2 = modifier;
            composerImpl = composerImpl2;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.QuickQuickSettings (QuickQuickSettings.kt:43)");
            }
            final List list = (List) quickQuickSettingsViewModel.tileViewModels$delegate.getValue();
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add((TileViewModel) ((SizedTile) list.get(i3)).getTile());
            }
            composerImpl2.startReplaceGroup(1907115987);
            boolean zChanged = composerImpl2.changed(list);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                Object obj = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    int size2 = list.size();
                    ArrayList arrayList2 = new ArrayList(size2);
                    for (int i4 = 0; i4 < size2; i4++) {
                        arrayList2.add(new BounceableTileViewModel());
                    }
                    composerImpl2.updateRememberedValue(arrayList2);
                    obj = arrayList2;
                }
                final List list2 = (List) obj;
                composerImpl2.end(false);
                final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(quickQuickSettingsViewModel.squishinessViewModel.squishiness, composerImpl2);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                companion2.getClass();
                Object obj2 = Composer.Companion.Empty;
                if (objRememberedValue2 == obj2) {
                    objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue2;
                composerImpl2.startReplaceGroup(1907123410);
                boolean zChanged2 = composerImpl2.changed(list);
                Object objRememberedValue3 = composerImpl2.rememberedValue();
                if (zChanged2 || objRememberedValue3 == obj2) {
                    objRememberedValue3 = SnapshotStateKt.derivedStateOf(new QuickQuickSettingsKt$$ExternalSyntheticLambda0(list, 0));
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                }
                State state = (State) objRememberedValue3;
                composerImpl2.end(false);
                final int columns = quickQuickSettingsViewModel.qsColumnsViewModel.getColumns();
                int i5 = i2 >> 6;
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, companion);
                ComposeUiNode.Companion.getClass();
                Function0 function03 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function03);
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
                GridAnchorKt.GridAnchor(contentScope, null, composerImpl2, i2 & 14);
                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_horizontal, composerImpl2);
                float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl2);
                List list3 = (List) state.getValue();
                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(Modifier.Companion, "qqs_tile_layout");
                composerImpl2.startReplaceGroup(2048549346);
                boolean zChangedInstance = composerImpl2.changedInstance(list);
                Object objRememberedValue4 = composerImpl2.rememberedValue();
                if (zChangedInstance || objRememberedValue4 == obj2) {
                    objRememberedValue4 = new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.QuickQuickSettingsKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj3) {
                            return ((TileViewModel) ((SizedTile) list.get(((Integer) obj3).intValue())).getTile()).spec;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue4);
                }
                composerImpl2.end(false);
                function02 = function0;
                SpannedGridsKt.m2582VerticalSpannedGridKhTvWYU(columns, fDimensionResource, fDimensionResource2, list3, modifierSysuiResTag, (Function1) objRememberedValue4, ComposableLambdaKt.rememberComposableLambda(-1384616666, new Function7() { // from class: com.android.systemui.qs.panels.ui.compose.QuickQuickSettingsKt$QuickQuickSettings$1$2
                    /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
                    @Override // kotlin.jvm.functions.Function7
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9) {
                        int i6;
                        final int iIntValue = ((Number) obj4).intValue();
                        final int iIntValue2 = ((Number) obj5).intValue();
                        final boolean zBooleanValue = ((Boolean) obj6).booleanValue();
                        final boolean zBooleanValue2 = ((Boolean) obj7).booleanValue();
                        Composer composer2 = (Composer) obj8;
                        int iIntValue3 = ((Number) obj9).intValue();
                        if ((iIntValue3 & 48) == 0) {
                            i6 = (((ComposerImpl) composer2).changed(iIntValue) ? 32 : 16) | iIntValue3;
                        } else {
                            i6 = iIntValue3;
                        }
                        if ((iIntValue3 & 384) == 0) {
                            i6 |= ((ComposerImpl) composer2).changed(iIntValue2) ? 256 : 128;
                        }
                        if ((iIntValue3 & 3072) == 0) {
                            i6 |= ((ComposerImpl) composer2).changed(zBooleanValue) ? 2048 : 1024;
                        }
                        if ((iIntValue3 & 24576) == 0) {
                            i6 |= ((ComposerImpl) composer2).changed(zBooleanValue2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((74897 & i6) == 74896) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.QuickQuickSettings.<anonymous>.<anonymous> (QuickQuickSettings.kt:64)");
                                }
                                final SizedTile sizedTile = (SizedTile) list.get(iIntValue);
                                ElementKeys elementKeys = ElementKeys.INSTANCE;
                                TileSpec tileSpec = ((TileViewModel) sizedTile.getTile()).spec;
                                elementKeys.getClass();
                                ElementKey elementKey = new ElementKey(tileSpec.getSpec(), new TileIdentity(tileSpec, iIntValue), null, false, 12, null);
                                Modifier.Companion companion3 = Modifier.Companion;
                                final List list4 = list2;
                                final CoroutineScope coroutineScope2 = coroutineScope;
                                final Function0 function04 = function0;
                                final int i7 = columns;
                                final QuickQuickSettingsViewModel quickQuickSettingsViewModel2 = quickQuickSettingsViewModel;
                                final State state2 = mutableStateCollectAsStateWithLifecycle;
                                contentScope.Element(elementKey, companion3, ComposableLambdaKt.rememberComposableLambda(-382661570, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.QuickQuickSettingsKt$QuickQuickSettings$1$2.1
                                    /* JADX WARN: Removed duplicated region for block: B:15:0x006a  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj10, Object obj11, Object obj12) {
                                        Composer composer3 = (Composer) obj11;
                                        if ((((Number) obj12).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.QuickQuickSettings.<anonymous>.<anonymous>.<anonymous> (QuickQuickSettings.kt:66)");
                                                }
                                                SizedTile sizedTile2 = sizedTile;
                                                TileViewModel tileViewModel = (TileViewModel) sizedTile2.getTile();
                                                boolean zIsIcon = sizedTile2.isIcon();
                                                BounceableInfo bounceableInfo = BounceableInfoKt.bounceableInfo(list4, sizedTile, iIntValue, iIntValue2, i7, zBooleanValue, zBooleanValue2);
                                                TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider = quickQuickSettingsViewModel2.tileHapticsViewModelFactoryProvider;
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                composerImpl5.startReplaceGroup(-893847501);
                                                State state3 = state2;
                                                boolean zChanged3 = composerImpl5.changed(state3);
                                                Object objRememberedValue5 = composerImpl5.rememberedValue();
                                                if (!zChanged3) {
                                                    Composer.Companion.getClass();
                                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                                        objRememberedValue5 = new QuickQuickSettingsKt$$ExternalSyntheticLambda0(state3, 1);
                                                        composerImpl5.updateRememberedValue(objRememberedValue5);
                                                    }
                                                    composerImpl5.end(false);
                                                    TileKt.Tile(tileViewModel, zIsIcon, (Function0) objRememberedValue5, coroutineScope2, bounceableInfo, tileHapticsViewModelFactoryProvider, null, function04, null, composerImpl5, 100663296);
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
                }, composerImpl2), composerImpl2, 1597440, 0);
                composerImpl = composerImpl2;
                composerImpl.end(true);
                TileListenerKt.TileListener(arrayList, function02, composerImpl, i5 & 112);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = companion;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(quickQuickSettingsViewModel, modifier2, function02, i) { // from class: com.android.systemui.qs.panels.ui.compose.QuickQuickSettingsKt$$ExternalSyntheticLambda2
                public final /* synthetic */ QuickQuickSettingsViewModel f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ Function0 f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Integer) obj4).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Modifier modifier3 = this.f$2;
                    Function0 function04 = this.f$3;
                    QuickQuickSettingsKt.QuickQuickSettings(this.f$0, this.f$1, modifier3, function04, (Composer) obj3, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
