package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridDslKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.LookaheadScopeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.BounceableKt;
import com.android.compose.animation.ExpandableControllerKt;
import com.android.compose.animation.ExpandableKt;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModel;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.ui.compose.BounceableInfo;
import com.android.systemui.qs.panels.ui.viewmodel.AccessibilityUiState;
import com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiState;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiStateKt;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public abstract class TileKt {
    /* JADX WARN: Removed duplicated region for block: B:42:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LargeStaticTile(final TileUiState tileUiState, final IconProvider iconProvider, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1897674537);
        int i2 = (composerImpl.changed(tileUiState) ? 4 : 2) | i | (composerImpl.changed(iconProvider) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.LargeStaticTile (Tile.kt:307)");
            }
            TileDefaults.INSTANCE.getClass();
            TileColors colorForState = TileDefaults.getColorForState(tileUiState, false, composerImpl, (i2 & 14) | 432);
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(ClipKt.clip(modifier, (Shape) TileDefaults.animateTileShapeAsState(tileUiState.state, composerImpl).getValue()), colorForState.background, RectangleShapeKt.RectangleShape);
            CommonTileDefaults.INSTANCE.getClass();
            Modifier modifierLargeTilePadding = largeTilePadding(SizeKt.m131height3ABfNKs(modifierM26backgroundbw27NRU, CommonTileDefaults.TileHeight));
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierLargeTilePadding);
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
            composerImpl.startReplaceGroup(757493578);
            boolean z = (i2 & 112) == 32;
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!z) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new TileKt$$ExternalSyntheticLambda3(iconProvider, 1);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function1 = (Function1) objRememberedValue;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(757497580);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new AnimatedBackgroundKt$$ExternalSyntheticLambda0();
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                CommonTileKt.LargeTileContent(tileUiState.label, "", function1, null, colorForState, (Function0) objRememberedValue2, null, null, null, null, null, composerImpl, 199728, 1984);
                composerImpl = composerImpl;
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(iconProvider, modifier, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda5
                public final /* synthetic */ IconProvider f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    IconProvider iconProvider2 = this.f$1;
                    Modifier modifier2 = this.f$2;
                    TileKt.LargeStaticTile(this.f$0, iconProvider2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0110 A[Catch: all -> 0x02f5, TryCatch #0 {all -> 0x02f5, blocks: (B:47:0x00b7, B:49:0x00c1, B:50:0x00c6, B:52:0x00d9, B:53:0x00dc, B:57:0x00f7, B:60:0x0109, B:63:0x0119, B:67:0x014a, B:69:0x0150, B:72:0x0160, B:80:0x0197, B:82:0x019f, B:85:0x01ae, B:87:0x0201, B:90:0x0210, B:92:0x027a, B:97:0x028d, B:96:0x0284, B:89:0x0208, B:84:0x01a6, B:71:0x0157, B:62:0x0110), top: B:109:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0157 A[Catch: all -> 0x02f5, TryCatch #0 {all -> 0x02f5, blocks: (B:47:0x00b7, B:49:0x00c1, B:50:0x00c6, B:52:0x00d9, B:53:0x00dc, B:57:0x00f7, B:60:0x0109, B:63:0x0119, B:67:0x014a, B:69:0x0150, B:72:0x0160, B:80:0x0197, B:82:0x019f, B:85:0x01ae, B:87:0x0201, B:90:0x0210, B:92:0x027a, B:97:0x028d, B:96:0x0284, B:89:0x0208, B:84:0x01a6, B:71:0x0157, B:62:0x0110), top: B:109:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01a6 A[Catch: all -> 0x02f5, TryCatch #0 {all -> 0x02f5, blocks: (B:47:0x00b7, B:49:0x00c1, B:50:0x00c6, B:52:0x00d9, B:53:0x00dc, B:57:0x00f7, B:60:0x0109, B:63:0x0119, B:67:0x014a, B:69:0x0150, B:72:0x0160, B:80:0x0197, B:82:0x019f, B:85:0x01ae, B:87:0x0201, B:90:0x0210, B:92:0x027a, B:97:0x028d, B:96:0x0284, B:89:0x0208, B:84:0x01a6, B:71:0x0157, B:62:0x0110), top: B:109:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0208 A[Catch: all -> 0x02f5, TryCatch #0 {all -> 0x02f5, blocks: (B:47:0x00b7, B:49:0x00c1, B:50:0x00c6, B:52:0x00d9, B:53:0x00dc, B:57:0x00f7, B:60:0x0109, B:63:0x0119, B:67:0x014a, B:69:0x0150, B:72:0x0160, B:80:0x0197, B:82:0x019f, B:85:0x01ae, B:87:0x0201, B:90:0x0210, B:92:0x027a, B:97:0x028d, B:96:0x0284, B:89:0x0208, B:84:0x01a6, B:71:0x0157, B:62:0x0110), top: B:109:0x00b7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Tile(final TileViewModel tileViewModel, final boolean z, final Function0 function0, final CoroutineScope coroutineScope, final BounceableInfo bounceableInfo, final TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider, Modifier modifier, final Function0 function02, final DetailsViewModel detailsViewModel, Composer composer, final int i) {
        boolean z2;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-40903192);
        int i2 = i | (composerImpl.changed(tileViewModel) ? 4 : 2) | (composerImpl.changed(z) ? 32 : 16) | (composerImpl.changedInstance(function0) ? 256 : 128) | (composerImpl.changedInstance(coroutineScope) ? 2048 : 1024) | (composerImpl.changed(bounceableInfo) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changed(tileHapticsViewModelFactoryProvider) ? 131072 : 65536) | 1572864 | (composerImpl.changedInstance(function02) ? 8388608 : 4194304);
        if ((i & 100663296) == 0) {
            i2 |= composerImpl.changed(detailsViewModel) ? 67108864 : 33554432;
        }
        if ((38347923 & i2) == 38347922 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile (Tile.kt:143)");
            }
            TileSpec tileSpec = tileViewModel.spec;
            QSTile qSTile = tileViewModel.tile;
            Trace.beginSection(StringsKt___StringsKt.takeLast(127, tileSpec.toString()));
            try {
                final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(bounceableInfo, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.resources (Tile.kt:522)");
                }
                composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration);
                Resources resources = (Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                TileUiState uiState = TileUiStateKt.toUiState(qSTile.getState(), resources);
                composerImpl.startReplaceGroup(-225142765);
                int i3 = i2 & 14;
                boolean z3 = true;
                int i4 = i2;
                boolean zChangedInstance = (i3 == 4) | composerImpl.changedInstance(resources);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion2 = Composer.Companion;
                if (!zChangedInstance) {
                    companion2.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new TileKt$Tile$2$uiState$2$1(tileViewModel, resources, null);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    int i5 = (i4 << 3) & 112;
                    final MutableState mutableStateProduceState = SnapshotStateKt.produceState(uiState, tileViewModel, resources, (Function2) objRememberedValue, composerImpl, i5);
                    IconProvider iconProvider = TileUiStateKt.toIconProvider(qSTile.getState());
                    composerImpl.startReplaceGroup(-225137169);
                    boolean z4 = i3 == 4;
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!z4) {
                        companion2.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new TileKt$Tile$2$icon$2$1(tileViewModel, null);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl.end(false);
                        final MutableState mutableStateProduceState2 = SnapshotStateKt.produceState(iconProvider, tileViewModel, (Function2) objRememberedValue2, composerImpl, i5);
                        TileDefaults.INSTANCE.getClass();
                        final TileColors colorForState = TileDefaults.getColorForState((TileUiState) mutableStateProduceState.getValue(), z, composerImpl, (i4 & 112) | 384);
                        composerImpl.startReplaceGroup(-225128373);
                        boolean z5 = (458752 & i4) == 131072;
                        if (i3 != 4) {
                            z3 = false;
                        }
                        boolean z6 = z5 | z3;
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!z6) {
                            companion2.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new Function0(tileViewModel) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda6
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        this.f$0.getClass();
                                        return null;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl.end(false);
                            final TileHapticsViewModel tileHapticsViewModel = (TileHapticsViewModel) SysUiViewModelKt.rememberViewModel("TileHapticsViewModel", null, (Function0) objRememberedValue3, composerImpl, 6, 2);
                            MutableState mutableStateAnimateTileShapeAsState = TileDefaults.animateTileShapeAsState(((TileUiState) mutableStateProduceState.getValue()).state, composerImpl);
                            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(colorForState.background, null, "QSTileBackgroundColor", composerImpl, 384, 10);
                            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(colorForState.alpha, null, "QSTileAlpha", null, composerImpl, 3072, 22);
                            composerImpl.startReplaceGroup(-225112882);
                            boolean zChanged = composerImpl.changed(stateM7animateColorAsStateeuL9pac);
                            Object objRememberedValue4 = composerImpl.rememberedValue();
                            if (!zChanged) {
                                companion2.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    objRememberedValue4 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$1$1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return Color.m456boximpl(((Color) stateM7animateColorAsStateeuL9pac.getValue()).value);
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                }
                                Function0 function03 = (Function0) objRememberedValue4;
                                composerImpl.end(false);
                                RoundedCornerShape roundedCornerShape = (RoundedCornerShape) mutableStateAnimateTileShapeAsState.getValue();
                                MaterialTheme.INSTANCE.getClass();
                                Modifier modifierBounceable = BounceableKt.bounceable(SizeKt.fillMaxWidth(BorderOnFocusKt.m2935borderOnFocusPOIbLQ4$default(companion, MaterialTheme.getColorScheme(composerImpl).secondary, ((RoundedCornerShape) mutableStateAnimateTileShapeAsState.getValue()).topEnd), 1.0f), ((BounceableInfo) mutableStateRememberUpdatedState.getValue()).bounceable, ((BounceableInfo) mutableStateRememberUpdatedState.getValue()).previousTile, ((BounceableInfo) mutableStateRememberUpdatedState.getValue()).nextTile, Orientation.Horizontal, ((BounceableInfo) mutableStateRememberUpdatedState.getValue()).bounceEnd);
                                composerImpl.startReplaceGroup(-225088362);
                                boolean zChanged2 = composerImpl.changed(stateAnimateFloatAsState);
                                Object objRememberedValue5 = composerImpl.rememberedValue();
                                if (zChanged2) {
                                    z2 = false;
                                    objRememberedValue5 = new TileKt$$ExternalSyntheticLambda7(stateAnimateFloatAsState, 0);
                                    composerImpl.updateRememberedValue(objRememberedValue5);
                                    composerImpl.end(z2);
                                    TileExpandable(function03, roundedCornerShape, function0, tileHapticsViewModel, GraphicsLayerModifierKt.graphicsLayer(modifierBounceable, (Function1) objRememberedValue5), ComposableLambdaKt.rememberComposableLambda(-616577919, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x00a0  */
                                        /* JADX WARN: Removed duplicated region for block: B:9:0x0046  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                            final Expandable expandable = (Expandable) obj;
                                            Composer composer2 = (Composer) obj2;
                                            ((Number) obj3).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous> (Tile.kt:192)");
                                            }
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            composerImpl2.startReplaceGroup(-1364223496);
                                            final TileHapticsViewModel tileHapticsViewModel2 = tileHapticsViewModel;
                                            boolean zChangedInstance2 = composerImpl2.changedInstance(tileHapticsViewModel2);
                                            final TileViewModel tileViewModel2 = tileViewModel;
                                            boolean zChanged3 = zChangedInstance2 | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable);
                                            Object objRememberedValue6 = composerImpl2.rememberedValue();
                                            Composer.Companion companion3 = Composer.Companion;
                                            if (!zChanged3) {
                                                companion3.getClass();
                                                if (objRememberedValue6 == Composer.Companion.Empty) {
                                                    objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda0
                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel2;
                                                            if (tileHapticsViewModel3 != null) {
                                                                tileHapticsViewModel3.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.LONG_CLICKED);
                                                            }
                                                            tileViewModel2.tile.longClick(expandable);
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(objRememberedValue6);
                                                }
                                            }
                                            Function0 function04 = (Function0) objRememberedValue6;
                                            composerImpl2.end(false);
                                            State state = mutableStateProduceState;
                                            if (!((TileUiState) state.getValue()).handlesLongClick) {
                                                function04 = null;
                                            }
                                            final Function0 function05 = function04;
                                            composerImpl2.startReplaceGroup(-1364211210);
                                            boolean zChangedInstance3 = composerImpl2.changedInstance(tileHapticsViewModel2) | composerImpl2.changed(detailsViewModel) | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable) | composerImpl2.changed(state) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changed(mutableStateRememberUpdatedState);
                                            Object objRememberedValue7 = composerImpl2.rememberedValue();
                                            if (!zChangedInstance3) {
                                                companion3.getClass();
                                                if (objRememberedValue7 == Composer.Companion.Empty) {
                                                    final TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel;
                                                    final CoroutineScope coroutineScope2 = coroutineScope;
                                                    final DetailsViewModel detailsViewModel2 = detailsViewModel;
                                                    final TileViewModel tileViewModel3 = tileViewModel;
                                                    final State state2 = mutableStateProduceState;
                                                    final State state3 = mutableStateRememberUpdatedState;
                                                    Function0 function06 = new Function0(detailsViewModel2, tileViewModel3, expandable, tileHapticsViewModel3, coroutineScope2, state2, state3) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda1
                                                        public final /* synthetic */ TileViewModel f$1;
                                                        public final /* synthetic */ Expandable f$2;
                                                        public final /* synthetic */ TileHapticsViewModel f$3;
                                                        public final /* synthetic */ CoroutineScope f$4;
                                                        public final /* synthetic */ State f$5;
                                                        public final /* synthetic */ State f$6;

                                                        {
                                                            this.f$1 = tileViewModel3;
                                                            this.f$2 = expandable;
                                                            this.f$3 = tileHapticsViewModel3;
                                                            this.f$4 = coroutineScope2;
                                                            this.f$5 = state2;
                                                            this.f$6 = state3;
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            this.f$1.tile.click(this.f$2);
                                                            TileHapticsViewModel tileHapticsViewModel4 = this.f$3;
                                                            if (tileHapticsViewModel4 != null) {
                                                                tileHapticsViewModel4.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                            }
                                                            if (((TileUiState) this.f$5.getValue()).accessibilityUiState.toggleableState != null) {
                                                                CoroutineTracingKt.launchTraced$default(this.f$4, null, null, new TileKt$Tile$2$3$1$1$1(this.f$6, null), 7);
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(function06);
                                                    objRememberedValue7 = function06;
                                                }
                                            }
                                            Function0 function07 = (Function0) objRememberedValue7;
                                            composerImpl2.end(false);
                                            AccessibilityUiState accessibilityUiState = ((TileUiState) state.getValue()).accessibilityUiState;
                                            final Function0 function08 = function02;
                                            final State state4 = mutableStateProduceState;
                                            final State state5 = mutableStateProduceState2;
                                            final boolean z7 = z;
                                            final TileColors tileColors = colorForState;
                                            final TileHapticsViewModel tileHapticsViewModel4 = tileHapticsViewModel;
                                            final TileViewModel tileViewModel4 = tileViewModel;
                                            final Function0 function09 = function0;
                                            TileKt.TileContainer(function07, function05, accessibilityUiState, z7, ComposableLambdaKt.rememberComposableLambda(-1762671212, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3.2
                                                /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
                                                /* JADX WARN: Removed duplicated region for block: B:36:0x00f5  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                    BoxScope boxScope = (BoxScope) obj4;
                                                    Composer composer3 = (Composer) obj5;
                                                    int iIntValue = ((Number) obj6).intValue();
                                                    if ((iIntValue & 6) == 0) {
                                                        iIntValue |= ((ComposerImpl) composer3).changed(boxScope) ? 4 : 2;
                                                    }
                                                    if ((iIntValue & 19) == 18) {
                                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                        if (composerImpl3.getSkipping()) {
                                                            composerImpl3.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous>.<anonymous> (Tile.kt:224)");
                                                            }
                                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                            composerImpl4.startReplaceGroup(-1047679689);
                                                            State state6 = state5;
                                                            boolean zChanged4 = composerImpl4.changed(state6);
                                                            Object objRememberedValue8 = composerImpl4.rememberedValue();
                                                            Composer.Companion companion4 = Composer.Companion;
                                                            if (!zChanged4) {
                                                                companion4.getClass();
                                                                if (objRememberedValue8 == Composer.Companion.Empty) {
                                                                    objRememberedValue8 = new TileKt$$ExternalSyntheticLambda7(state6, 1);
                                                                    composerImpl4.updateRememberedValue(objRememberedValue8);
                                                                }
                                                                Function1 function1 = (Function1) objRememberedValue8;
                                                                composerImpl4.end(false);
                                                                if (z7) {
                                                                    composerImpl4.startReplaceGroup(1881733297);
                                                                    long j = tileColors.icon;
                                                                    Modifier.Companion companion5 = Modifier.Companion;
                                                                    Alignment.Companion.getClass();
                                                                    CommonTileKt.m2904SmallTileContent8V94_ZQ(function1, j, boxScope.align(companion5, Alignment.Companion.Center), null, false, composerImpl4, 0, 24);
                                                                    composerImpl4.end(false);
                                                                } else {
                                                                    composerImpl4.startReplaceGroup(1882012638);
                                                                    TileDefaults tileDefaults = TileDefaults.INSTANCE;
                                                                    State state7 = state4;
                                                                    int i6 = ((TileUiState) state7.getValue()).state;
                                                                    tileDefaults.getClass();
                                                                    composerImpl4.startReplaceGroup(-1688665276);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.animateIconShapeAsState (Tile.kt:470)");
                                                                    }
                                                                    MutableState mutableStateM2905animateShapeAsStaterAjV9yQ = TileDefaults.m2905animateShapeAsStaterAjV9yQ(i6, TileDefaults.ActiveIconCornerRadius, "QSTileCornerRadius", composerImpl4, 3456);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                    composerImpl4.end(false);
                                                                    composerImpl4.startReplaceGroup(-1047664011);
                                                                    final TileHapticsViewModel tileHapticsViewModel5 = tileHapticsViewModel4;
                                                                    boolean zChangedInstance4 = composerImpl4.changedInstance(tileHapticsViewModel5);
                                                                    final TileViewModel tileViewModel5 = tileViewModel4;
                                                                    boolean zChanged5 = zChangedInstance4 | composerImpl4.changed(tileViewModel5);
                                                                    Object objRememberedValue9 = composerImpl4.rememberedValue();
                                                                    if (!zChanged5) {
                                                                        companion4.getClass();
                                                                        if (objRememberedValue9 == Composer.Companion.Empty) {
                                                                            objRememberedValue9 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$2$$ExternalSyntheticLambda1
                                                                                @Override // kotlin.jvm.functions.Function0
                                                                                public final Object invoke() {
                                                                                    TileHapticsViewModel tileHapticsViewModel6 = tileHapticsViewModel5;
                                                                                    if (tileHapticsViewModel6 != null) {
                                                                                        tileHapticsViewModel6.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                                                    }
                                                                                    tileViewModel5.tile.secondaryClick(null);
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            };
                                                                            composerImpl4.updateRememberedValue(objRememberedValue9);
                                                                        }
                                                                        Function0 function010 = (Function0) objRememberedValue9;
                                                                        composerImpl4.end(false);
                                                                        if (!((TileUiState) state7.getValue()).handlesSecondaryClick) {
                                                                            function010 = null;
                                                                        }
                                                                        Function0 function011 = function010;
                                                                        String str = ((TileUiState) state7.getValue()).label;
                                                                        String str2 = ((TileUiState) state7.getValue()).secondaryLabel;
                                                                        Drawable drawable = ((TileUiState) state7.getValue()).sideDrawable;
                                                                        RoundedCornerShape roundedCornerShape2 = (RoundedCornerShape) mutableStateM2905animateShapeAsStaterAjV9yQ.getValue();
                                                                        CommonTileKt.LargeTileContent(str, str2, function1, drawable, tileColors, function09, function08, ((TileUiState) state7.getValue()).accessibilityUiState, roundedCornerShape2, function011, function05, composerImpl4, 0, 0);
                                                                        composerImpl4.end(false);
                                                                    }
                                                                }
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl2), composerImpl2, 24576);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl), composerImpl, (i4 & 896) | 196608);
                                    composerImpl = composerImpl;
                                    Unit unit = Unit.INSTANCE;
                                    Trace.endSection();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    modifier2 = companion;
                                } else {
                                    companion2.getClass();
                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                        z2 = false;
                                        objRememberedValue5 = new TileKt$$ExternalSyntheticLambda7(stateAnimateFloatAsState, 0);
                                        composerImpl.updateRememberedValue(objRememberedValue5);
                                        composerImpl.end(z2);
                                        TileExpandable(function03, roundedCornerShape, function0, tileHapticsViewModel, GraphicsLayerModifierKt.graphicsLayer(modifierBounceable, (Function1) objRememberedValue5), ComposableLambdaKt.rememberComposableLambda(-616577919, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3
                                            /* JADX WARN: Removed duplicated region for block: B:18:0x00a0  */
                                            /* JADX WARN: Removed duplicated region for block: B:9:0x0046  */
                                            @Override // kotlin.jvm.functions.Function3
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                final Expandable expandable = (Expandable) obj;
                                                Composer composer2 = (Composer) obj2;
                                                ((Number) obj3).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous> (Tile.kt:192)");
                                                }
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                composerImpl2.startReplaceGroup(-1364223496);
                                                final TileHapticsViewModel tileHapticsViewModel2 = tileHapticsViewModel;
                                                boolean zChangedInstance2 = composerImpl2.changedInstance(tileHapticsViewModel2);
                                                final TileViewModel tileViewModel2 = tileViewModel;
                                                boolean zChanged3 = zChangedInstance2 | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable);
                                                Object objRememberedValue6 = composerImpl2.rememberedValue();
                                                Composer.Companion companion3 = Composer.Companion;
                                                if (!zChanged3) {
                                                    companion3.getClass();
                                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                                        objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel2;
                                                                if (tileHapticsViewModel3 != null) {
                                                                    tileHapticsViewModel3.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.LONG_CLICKED);
                                                                }
                                                                tileViewModel2.tile.longClick(expandable);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(objRememberedValue6);
                                                    }
                                                }
                                                Function0 function04 = (Function0) objRememberedValue6;
                                                composerImpl2.end(false);
                                                State state = mutableStateProduceState;
                                                if (!((TileUiState) state.getValue()).handlesLongClick) {
                                                    function04 = null;
                                                }
                                                final Function0 function05 = function04;
                                                composerImpl2.startReplaceGroup(-1364211210);
                                                boolean zChangedInstance3 = composerImpl2.changedInstance(tileHapticsViewModel2) | composerImpl2.changed(detailsViewModel) | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable) | composerImpl2.changed(state) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changed(mutableStateRememberUpdatedState);
                                                Object objRememberedValue7 = composerImpl2.rememberedValue();
                                                if (!zChangedInstance3) {
                                                    companion3.getClass();
                                                    if (objRememberedValue7 == Composer.Companion.Empty) {
                                                        final TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel;
                                                        final CoroutineScope coroutineScope2 = coroutineScope;
                                                        final DetailsViewModel detailsViewModel2 = detailsViewModel;
                                                        final TileViewModel tileViewModel3 = tileViewModel;
                                                        final State state2 = mutableStateProduceState;
                                                        final State state3 = mutableStateRememberUpdatedState;
                                                        Function0 function06 = new Function0(detailsViewModel2, tileViewModel3, expandable, tileHapticsViewModel3, coroutineScope2, state2, state3) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda1
                                                            public final /* synthetic */ TileViewModel f$1;
                                                            public final /* synthetic */ Expandable f$2;
                                                            public final /* synthetic */ TileHapticsViewModel f$3;
                                                            public final /* synthetic */ CoroutineScope f$4;
                                                            public final /* synthetic */ State f$5;
                                                            public final /* synthetic */ State f$6;

                                                            {
                                                                this.f$1 = tileViewModel3;
                                                                this.f$2 = expandable;
                                                                this.f$3 = tileHapticsViewModel3;
                                                                this.f$4 = coroutineScope2;
                                                                this.f$5 = state2;
                                                                this.f$6 = state3;
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                this.f$1.tile.click(this.f$2);
                                                                TileHapticsViewModel tileHapticsViewModel4 = this.f$3;
                                                                if (tileHapticsViewModel4 != null) {
                                                                    tileHapticsViewModel4.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                                }
                                                                if (((TileUiState) this.f$5.getValue()).accessibilityUiState.toggleableState != null) {
                                                                    CoroutineTracingKt.launchTraced$default(this.f$4, null, null, new TileKt$Tile$2$3$1$1$1(this.f$6, null), 7);
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function06);
                                                        objRememberedValue7 = function06;
                                                    }
                                                }
                                                Function0 function07 = (Function0) objRememberedValue7;
                                                composerImpl2.end(false);
                                                AccessibilityUiState accessibilityUiState = ((TileUiState) state.getValue()).accessibilityUiState;
                                                final Function0 function08 = function02;
                                                final State<TileUiState> state4 = mutableStateProduceState;
                                                final State<? extends IconProvider> state5 = mutableStateProduceState2;
                                                final boolean z7 = z;
                                                final TileColors tileColors = colorForState;
                                                final TileHapticsViewModel tileHapticsViewModel4 = tileHapticsViewModel;
                                                final TileViewModel tileViewModel4 = tileViewModel;
                                                final Function0 function09 = function0;
                                                TileKt.TileContainer(function07, function05, accessibilityUiState, z7, ComposableLambdaKt.rememberComposableLambda(-1762671212, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3.2
                                                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
                                                    /* JADX WARN: Removed duplicated region for block: B:36:0x00f5  */
                                                    @Override // kotlin.jvm.functions.Function3
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                        BoxScope boxScope = (BoxScope) obj4;
                                                        Composer composer3 = (Composer) obj5;
                                                        int iIntValue = ((Number) obj6).intValue();
                                                        if ((iIntValue & 6) == 0) {
                                                            iIntValue |= ((ComposerImpl) composer3).changed(boxScope) ? 4 : 2;
                                                        }
                                                        if ((iIntValue & 19) == 18) {
                                                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                            if (composerImpl3.getSkipping()) {
                                                                composerImpl3.skipToGroupEnd();
                                                            } else {
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous>.<anonymous> (Tile.kt:224)");
                                                                }
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                composerImpl4.startReplaceGroup(-1047679689);
                                                                State state6 = state5;
                                                                boolean zChanged4 = composerImpl4.changed(state6);
                                                                Object objRememberedValue8 = composerImpl4.rememberedValue();
                                                                Composer.Companion companion4 = Composer.Companion;
                                                                if (!zChanged4) {
                                                                    companion4.getClass();
                                                                    if (objRememberedValue8 == Composer.Companion.Empty) {
                                                                        objRememberedValue8 = new TileKt$$ExternalSyntheticLambda7(state6, 1);
                                                                        composerImpl4.updateRememberedValue(objRememberedValue8);
                                                                    }
                                                                    Function1 function1 = (Function1) objRememberedValue8;
                                                                    composerImpl4.end(false);
                                                                    if (z7) {
                                                                        composerImpl4.startReplaceGroup(1881733297);
                                                                        long j = tileColors.icon;
                                                                        Modifier.Companion companion5 = Modifier.Companion;
                                                                        Alignment.Companion.getClass();
                                                                        CommonTileKt.m2904SmallTileContent8V94_ZQ(function1, j, boxScope.align(companion5, Alignment.Companion.Center), null, false, composerImpl4, 0, 24);
                                                                        composerImpl4.end(false);
                                                                    } else {
                                                                        composerImpl4.startReplaceGroup(1882012638);
                                                                        TileDefaults tileDefaults = TileDefaults.INSTANCE;
                                                                        State state7 = state4;
                                                                        int i6 = ((TileUiState) state7.getValue()).state;
                                                                        tileDefaults.getClass();
                                                                        composerImpl4.startReplaceGroup(-1688665276);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.animateIconShapeAsState (Tile.kt:470)");
                                                                        }
                                                                        MutableState mutableStateM2905animateShapeAsStaterAjV9yQ = TileDefaults.m2905animateShapeAsStaterAjV9yQ(i6, TileDefaults.ActiveIconCornerRadius, "QSTileCornerRadius", composerImpl4, 3456);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                        composerImpl4.end(false);
                                                                        composerImpl4.startReplaceGroup(-1047664011);
                                                                        final TileHapticsViewModel tileHapticsViewModel5 = tileHapticsViewModel4;
                                                                        boolean zChangedInstance4 = composerImpl4.changedInstance(tileHapticsViewModel5);
                                                                        final TileViewModel tileViewModel5 = tileViewModel4;
                                                                        boolean zChanged5 = zChangedInstance4 | composerImpl4.changed(tileViewModel5);
                                                                        Object objRememberedValue9 = composerImpl4.rememberedValue();
                                                                        if (!zChanged5) {
                                                                            companion4.getClass();
                                                                            if (objRememberedValue9 == Composer.Companion.Empty) {
                                                                                objRememberedValue9 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$2$$ExternalSyntheticLambda1
                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                    public final Object invoke() {
                                                                                        TileHapticsViewModel tileHapticsViewModel6 = tileHapticsViewModel5;
                                                                                        if (tileHapticsViewModel6 != null) {
                                                                                            tileHapticsViewModel6.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                                                        }
                                                                                        tileViewModel5.tile.secondaryClick(null);
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                };
                                                                                composerImpl4.updateRememberedValue(objRememberedValue9);
                                                                            }
                                                                            Function0 function010 = (Function0) objRememberedValue9;
                                                                            composerImpl4.end(false);
                                                                            if (!((TileUiState) state7.getValue()).handlesSecondaryClick) {
                                                                                function010 = null;
                                                                            }
                                                                            Function0 function011 = function010;
                                                                            String str = ((TileUiState) state7.getValue()).label;
                                                                            String str2 = ((TileUiState) state7.getValue()).secondaryLabel;
                                                                            Drawable drawable = ((TileUiState) state7.getValue()).sideDrawable;
                                                                            RoundedCornerShape roundedCornerShape2 = (RoundedCornerShape) mutableStateM2905animateShapeAsStaterAjV9yQ.getValue();
                                                                            CommonTileKt.LargeTileContent(str, str2, function1, drawable, tileColors, function09, function08, ((TileUiState) state7.getValue()).accessibilityUiState, roundedCornerShape2, function011, function05, composerImpl4, 0, 0);
                                                                            composerImpl4.end(false);
                                                                        }
                                                                    }
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }, composerImpl2), composerImpl2, 24576);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl), composerImpl, (i4 & 896) | 196608);
                                        composerImpl = composerImpl;
                                        Unit unit2 = Unit.INSTANCE;
                                        Trace.endSection();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        modifier2 = companion;
                                    } else {
                                        z2 = false;
                                        composerImpl.end(z2);
                                        TileExpandable(function03, roundedCornerShape, function0, tileHapticsViewModel, GraphicsLayerModifierKt.graphicsLayer(modifierBounceable, (Function1) objRememberedValue5), ComposableLambdaKt.rememberComposableLambda(-616577919, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3
                                            /* JADX WARN: Removed duplicated region for block: B:18:0x00a0  */
                                            /* JADX WARN: Removed duplicated region for block: B:9:0x0046  */
                                            @Override // kotlin.jvm.functions.Function3
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                final Expandable expandable = (Expandable) obj;
                                                Composer composer2 = (Composer) obj2;
                                                ((Number) obj3).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous> (Tile.kt:192)");
                                                }
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                composerImpl2.startReplaceGroup(-1364223496);
                                                final TileHapticsViewModel tileHapticsViewModel2 = tileHapticsViewModel;
                                                boolean zChangedInstance2 = composerImpl2.changedInstance(tileHapticsViewModel2);
                                                final TileViewModel tileViewModel2 = tileViewModel;
                                                boolean zChanged3 = zChangedInstance2 | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable);
                                                Object objRememberedValue6 = composerImpl2.rememberedValue();
                                                Composer.Companion companion3 = Composer.Companion;
                                                if (!zChanged3) {
                                                    companion3.getClass();
                                                    if (objRememberedValue6 == Composer.Companion.Empty) {
                                                        objRememberedValue6 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel2;
                                                                if (tileHapticsViewModel3 != null) {
                                                                    tileHapticsViewModel3.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.LONG_CLICKED);
                                                                }
                                                                tileViewModel2.tile.longClick(expandable);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(objRememberedValue6);
                                                    }
                                                }
                                                Function0 function04 = (Function0) objRememberedValue6;
                                                composerImpl2.end(false);
                                                State state = mutableStateProduceState;
                                                if (!((TileUiState) state.getValue()).handlesLongClick) {
                                                    function04 = null;
                                                }
                                                final Function0 function05 = function04;
                                                composerImpl2.startReplaceGroup(-1364211210);
                                                boolean zChangedInstance3 = composerImpl2.changedInstance(tileHapticsViewModel2) | composerImpl2.changed(detailsViewModel) | composerImpl2.changed(tileViewModel2) | composerImpl2.changedInstance(expandable) | composerImpl2.changed(state) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changed(mutableStateRememberUpdatedState);
                                                Object objRememberedValue7 = composerImpl2.rememberedValue();
                                                if (!zChangedInstance3) {
                                                    companion3.getClass();
                                                    if (objRememberedValue7 == Composer.Companion.Empty) {
                                                        final TileHapticsViewModel tileHapticsViewModel3 = tileHapticsViewModel;
                                                        final CoroutineScope coroutineScope2 = coroutineScope;
                                                        final DetailsViewModel detailsViewModel2 = detailsViewModel;
                                                        final TileViewModel tileViewModel3 = tileViewModel;
                                                        final State state2 = mutableStateProduceState;
                                                        final State state3 = mutableStateRememberUpdatedState;
                                                        Function0 function06 = new Function0(detailsViewModel2, tileViewModel3, expandable, tileHapticsViewModel3, coroutineScope2, state2, state3) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$$ExternalSyntheticLambda1
                                                            public final /* synthetic */ TileViewModel f$1;
                                                            public final /* synthetic */ Expandable f$2;
                                                            public final /* synthetic */ TileHapticsViewModel f$3;
                                                            public final /* synthetic */ CoroutineScope f$4;
                                                            public final /* synthetic */ State f$5;
                                                            public final /* synthetic */ State f$6;

                                                            {
                                                                this.f$1 = tileViewModel3;
                                                                this.f$2 = expandable;
                                                                this.f$3 = tileHapticsViewModel3;
                                                                this.f$4 = coroutineScope2;
                                                                this.f$5 = state2;
                                                                this.f$6 = state3;
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                this.f$1.tile.click(this.f$2);
                                                                TileHapticsViewModel tileHapticsViewModel4 = this.f$3;
                                                                if (tileHapticsViewModel4 != null) {
                                                                    tileHapticsViewModel4.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                                }
                                                                if (((TileUiState) this.f$5.getValue()).accessibilityUiState.toggleableState != null) {
                                                                    CoroutineTracingKt.launchTraced$default(this.f$4, null, null, new TileKt$Tile$2$3$1$1$1(this.f$6, null), 7);
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function06);
                                                        objRememberedValue7 = function06;
                                                    }
                                                }
                                                Function0 function07 = (Function0) objRememberedValue7;
                                                composerImpl2.end(false);
                                                AccessibilityUiState accessibilityUiState = ((TileUiState) state.getValue()).accessibilityUiState;
                                                final Function0 function08 = function02;
                                                final State<TileUiState> state4 = mutableStateProduceState;
                                                final State<? extends IconProvider> state5 = mutableStateProduceState2;
                                                final boolean z7 = z;
                                                final TileColors tileColors = colorForState;
                                                final TileHapticsViewModel tileHapticsViewModel4 = tileHapticsViewModel;
                                                final TileViewModel tileViewModel4 = tileViewModel;
                                                final Function0 function09 = function0;
                                                TileKt.TileContainer(function07, function05, accessibilityUiState, z7, ComposableLambdaKt.rememberComposableLambda(-1762671212, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3.2
                                                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
                                                    /* JADX WARN: Removed duplicated region for block: B:36:0x00f5  */
                                                    @Override // kotlin.jvm.functions.Function3
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                        BoxScope boxScope = (BoxScope) obj4;
                                                        Composer composer3 = (Composer) obj5;
                                                        int iIntValue = ((Number) obj6).intValue();
                                                        if ((iIntValue & 6) == 0) {
                                                            iIntValue |= ((ComposerImpl) composer3).changed(boxScope) ? 4 : 2;
                                                        }
                                                        if ((iIntValue & 19) == 18) {
                                                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                            if (composerImpl3.getSkipping()) {
                                                                composerImpl3.skipToGroupEnd();
                                                            } else {
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.Tile.<anonymous>.<anonymous>.<anonymous> (Tile.kt:224)");
                                                                }
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                composerImpl4.startReplaceGroup(-1047679689);
                                                                State state6 = state5;
                                                                boolean zChanged4 = composerImpl4.changed(state6);
                                                                Object objRememberedValue8 = composerImpl4.rememberedValue();
                                                                Composer.Companion companion4 = Composer.Companion;
                                                                if (!zChanged4) {
                                                                    companion4.getClass();
                                                                    if (objRememberedValue8 == Composer.Companion.Empty) {
                                                                        objRememberedValue8 = new TileKt$$ExternalSyntheticLambda7(state6, 1);
                                                                        composerImpl4.updateRememberedValue(objRememberedValue8);
                                                                    }
                                                                    Function1 function1 = (Function1) objRememberedValue8;
                                                                    composerImpl4.end(false);
                                                                    if (z7) {
                                                                        composerImpl4.startReplaceGroup(1881733297);
                                                                        long j = tileColors.icon;
                                                                        Modifier.Companion companion5 = Modifier.Companion;
                                                                        Alignment.Companion.getClass();
                                                                        CommonTileKt.m2904SmallTileContent8V94_ZQ(function1, j, boxScope.align(companion5, Alignment.Companion.Center), null, false, composerImpl4, 0, 24);
                                                                        composerImpl4.end(false);
                                                                    } else {
                                                                        composerImpl4.startReplaceGroup(1882012638);
                                                                        TileDefaults tileDefaults = TileDefaults.INSTANCE;
                                                                        State state7 = state4;
                                                                        int i6 = ((TileUiState) state7.getValue()).state;
                                                                        tileDefaults.getClass();
                                                                        composerImpl4.startReplaceGroup(-1688665276);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.animateIconShapeAsState (Tile.kt:470)");
                                                                        }
                                                                        MutableState mutableStateM2905animateShapeAsStaterAjV9yQ = TileDefaults.m2905animateShapeAsStaterAjV9yQ(i6, TileDefaults.ActiveIconCornerRadius, "QSTileCornerRadius", composerImpl4, 3456);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                        composerImpl4.end(false);
                                                                        composerImpl4.startReplaceGroup(-1047664011);
                                                                        final TileHapticsViewModel tileHapticsViewModel5 = tileHapticsViewModel4;
                                                                        boolean zChangedInstance4 = composerImpl4.changedInstance(tileHapticsViewModel5);
                                                                        final TileViewModel tileViewModel5 = tileViewModel4;
                                                                        boolean zChanged5 = zChangedInstance4 | composerImpl4.changed(tileViewModel5);
                                                                        Object objRememberedValue9 = composerImpl4.rememberedValue();
                                                                        if (!zChanged5) {
                                                                            companion4.getClass();
                                                                            if (objRememberedValue9 == Composer.Companion.Empty) {
                                                                                objRememberedValue9 = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$3$2$$ExternalSyntheticLambda1
                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                    public final Object invoke() {
                                                                                        TileHapticsViewModel tileHapticsViewModel6 = tileHapticsViewModel5;
                                                                                        if (tileHapticsViewModel6 != null) {
                                                                                            tileHapticsViewModel6.tileInteractionState.setValue(TileHapticsViewModel.TileInteractionState.CLICKED);
                                                                                        }
                                                                                        tileViewModel5.tile.secondaryClick(null);
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                };
                                                                                composerImpl4.updateRememberedValue(objRememberedValue9);
                                                                            }
                                                                            Function0 function010 = (Function0) objRememberedValue9;
                                                                            composerImpl4.end(false);
                                                                            if (!((TileUiState) state7.getValue()).handlesSecondaryClick) {
                                                                                function010 = null;
                                                                            }
                                                                            Function0 function011 = function010;
                                                                            String str = ((TileUiState) state7.getValue()).label;
                                                                            String str2 = ((TileUiState) state7.getValue()).secondaryLabel;
                                                                            Drawable drawable = ((TileUiState) state7.getValue()).sideDrawable;
                                                                            RoundedCornerShape roundedCornerShape2 = (RoundedCornerShape) mutableStateM2905animateShapeAsStaterAjV9yQ.getValue();
                                                                            CommonTileKt.LargeTileContent(str, str2, function1, drawable, tileColors, function09, function08, ((TileUiState) state7.getValue()).accessibilityUiState, roundedCornerShape2, function011, function05, composerImpl4, 0, 0);
                                                                            composerImpl4.end(false);
                                                                        }
                                                                    }
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }, composerImpl2), composerImpl2, 24576);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl), composerImpl, (i4 & 896) | 196608);
                                        composerImpl = composerImpl;
                                        Unit unit22 = Unit.INSTANCE;
                                        Trace.endSection();
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        modifier2 = companion;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    BounceableInfo bounceableInfo2 = bounceableInfo;
                    Function0 function04 = function02;
                    DetailsViewModel detailsViewModel2 = detailsViewModel;
                    TileKt.Tile(tileViewModel, z, function0, coroutineScope, bounceableInfo2, tileHapticsViewModelFactoryProvider, modifier2, function04, detailsViewModel2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TileContainer(final Function0 function0, final Function0 function02, final AccessibilityUiState accessibilityUiState, final boolean z, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        final ComposableLambdaImpl composableLambdaImpl2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(547472752);
        int i2 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | (composerImpl.changedInstance(function02) ? 32 : 16) | (composerImpl.changed(accessibilityUiState) ? 256 : 128) | (composerImpl.changed(z) ? 2048 : 1024);
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            composableLambdaImpl2 = composableLambdaImpl;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileContainer (Tile.kt:285)");
            }
            Modifier.Companion companion = Modifier.Companion;
            CommonTileDefaults.INSTANCE.getClass();
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m131height3ABfNKs(companion, CommonTileDefaults.TileHeight), 1.0f);
            int i3 = i2 << 3;
            int i4 = (i3 & 57344) | (i3 & 112) | 6 | (i3 & 896) | (i3 & 7168);
            composerImpl.startReplaceGroup(-1554254556);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.tileCombinedClickable (Tile.kt:352)");
            }
            Modifier modifierM39combinedClickablef5TDLPQ$default = ClickableKt.m39combinedClickablef5TDLPQ$default(modifierFillMaxWidth, accessibilityUiState.clickLabel, CommonTileDefaults.longPressLabel(composerImpl), function02, function0, 37);
            composerImpl.startReplaceGroup(1379730995);
            boolean z2 = (((i4 & 7168) ^ 3072) > 2048 && composerImpl.changed(accessibilityUiState)) || (i4 & 3072) == 2048;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z2) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new TileKt$$ExternalSyntheticLambda3(accessibilityUiState, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM39combinedClickablef5TDLPQ$default, false, (Function1) objRememberedValue);
                if (z) {
                    modifierSemantics = modifierSemantics.then(SemanticsModifierKt.semantics(companion, false, new Function1() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$tileCombinedClickable$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, accessibilityUiState.contentDescription);
                            return Unit.INSTANCE;
                        }
                    }));
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(modifierSemantics, z ? "qs_tile_small" : "qs_tile_large");
                if (!z) {
                    modifierSysuiResTag = modifierSysuiResTag.then(largeTilePadding(companion));
                }
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSysuiResTag);
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
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                composableLambdaImpl2 = composableLambdaImpl;
                composableLambdaImpl2.invoke((Object) BoxScopeInstance.INSTANCE, (Object) composerImpl, (Object) 54);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function02, accessibilityUiState, z, composableLambdaImpl2, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda1
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ AccessibilityUiState f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ ComposableLambdaImpl f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(24577);
                    ComposableLambdaImpl composableLambdaImpl3 = this.f$4;
                    TileKt.TileContainer(this.f$0, this.f$1, this.f$2, this.f$3, composableLambdaImpl3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TileExpandable(final Function0 function0, final RoundedCornerShape roundedCornerShape, final Function0 function02, final TileHapticsViewModel tileHapticsViewModel, final Modifier modifier, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        Function0 function03;
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(222516676);
        if ((i & 6) == 0) {
            function03 = function0;
            i2 = (composerImpl.changedInstance(function03) ? 4 : 2) | i;
        } else {
            function03 = function0;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(roundedCornerShape) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(tileHapticsViewModel) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(composableLambdaImpl) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileExpandable (Tile.kt:268)");
            }
            ExpandableKt.Expandable(ExpandableControllerKt.m910rememberExpandableControllerT042LqI(function03, roundedCornerShape, 0L, null, null, composerImpl, i2 & 126, 28), LookaheadScopeKt.approachLayout(ClipKt.clip(modifier, roundedCornerShape), new SquishTileKt$$ExternalSyntheticLambda0(function02), LookaheadScopeKt.defaultPlacementApproachInProgress, new SquishTileKt$$ExternalSyntheticLambda1(function02)), null, null, true, false, ComposableLambdaKt.rememberComposableLambda(1658196727, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt.TileExpandable.1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Expandable expandableCreateStateAwareExpandable = (Expandable) obj;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileExpandable.<anonymous> (Tile.kt:274)");
                    }
                    TileHapticsViewModel tileHapticsViewModel2 = tileHapticsViewModel;
                    if (tileHapticsViewModel2 != null) {
                        expandableCreateStateAwareExpandable = tileHapticsViewModel2.createStateAwareExpandable(expandableCreateStateAwareExpandable);
                    }
                    composableLambdaImpl.invoke(expandableCreateStateAwareExpandable, composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1597440, 44);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    TileKt.TileExpandable(function0, roundedCornerShape, function02, tileHapticsViewModel, modifier, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TileLazyGrid(final GridCells.Fixed fixed, final Modifier modifier, final LazyGridState lazyGridState, final PaddingValuesImpl paddingValuesImpl, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1231368701);
        int i2 = i | (composerImpl.changed(fixed) ? 4 : 2) | (composerImpl.changed(modifier) ? 32 : 16) | (composerImpl.changed(lazyGridState) ? 256 : 128) | (composerImpl.changedInstance(function1) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileLazyGrid (Tile.kt:117)");
            }
            Arrangement arrangement = Arrangement.INSTANCE;
            CommonTileDefaults.INSTANCE.getClass();
            float f = CommonTileDefaults.TileArrangementPadding;
            arrangement.getClass();
            LazyGridDslKt.LazyVerticalGrid((i2 & 14) | 1769472 | (i2 & 112) | (i2 & 896) | 3072, (i2 >> 12) & 14, 912, null, null, Arrangement.m92spacedBy0680j_4(f), Arrangement.m92spacedBy0680j_4(f), paddingValuesImpl, fixed, lazyGridState, composerImpl, modifier, function1, false, false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier, lazyGridState, paddingValuesImpl, function1, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ LazyGridState f$2;
                public final /* synthetic */ PaddingValuesImpl f$3;
                public final /* synthetic */ Function1 f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3073);
                    GridCells.Fixed fixed2 = this.f$0;
                    PaddingValuesImpl paddingValuesImpl2 = this.f$3;
                    Function1 function12 = this.f$4;
                    TileKt.TileLazyGrid(fixed2, this.f$1, this.f$2, paddingValuesImpl2, function12, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Icon getTileIcon(Context context, IconProvider iconProvider) {
        QSTile.Icon icon = iconProvider.getIcon();
        return icon != null ? icon instanceof QSTileImpl.ResourceIcon ? new Icon.Resource(((QSTileImpl.ResourceIcon) icon).mResId, null) : new Icon.Loaded(icon.getDrawable(context), null, null, 4, null) : new Icon.Resource(R.drawable.ic_error_outline, null);
    }

    public static final Modifier largeTilePadding(Modifier modifier) {
        CommonTileDefaults.INSTANCE.getClass();
        return PaddingKt.m129paddingqDBjuR0$default(modifier, CommonTileDefaults.TileStartPadding, 0.0f, CommonTileDefaults.TileEndPadding, 0.0f, 10);
    }
}
