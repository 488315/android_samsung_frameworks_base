package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.Context;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.foundation.lazy.grid.LazyGridDslKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.layout.LookaheadScopeKt;
import com.android.compose.animation.ExpandableControllerKt;
import com.android.compose.animation.ExpandableKt;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModel;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TileKt {
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00fa, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LargeStaticTile(final com.android.systemui.qs.panels.ui.viewmodel.TileUiState r19, final com.android.systemui.qs.panels.ui.viewmodel.IconProvider r20, final androidx.compose.ui.Modifier r21, androidx.compose.runtime.Composer r22, final int r23) {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt.LargeStaticTile(com.android.systemui.qs.panels.ui.viewmodel.TileUiState, com.android.systemui.qs.panels.ui.viewmodel.IconProvider, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x010e, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0155, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01a4, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0206, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L89;
     */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02d1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Tile(final com.android.systemui.qs.panels.ui.viewmodel.TileViewModel r31, final boolean r32, final kotlin.jvm.functions.Function0 r33, final kotlinx.coroutines.CoroutineScope r34, final com.android.systemui.qs.panels.ui.compose.BounceableInfo r35, final com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider r36, androidx.compose.ui.Modifier r37, final kotlin.jvm.functions.Function0 r38, final com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel r39, androidx.compose.runtime.Composer r40, final int r41) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt.Tile(com.android.systemui.qs.panels.ui.viewmodel.TileViewModel, boolean, kotlin.jvm.functions.Function0, kotlinx.coroutines.CoroutineScope, com.android.systemui.qs.panels.ui.compose.BounceableInfo, com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c6, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TileContainer(final kotlin.jvm.functions.Function0 r13, final kotlin.jvm.functions.Function0 r14, final com.android.systemui.qs.panels.ui.viewmodel.AccessibilityUiState r15, final boolean r16, androidx.compose.runtime.internal.ComposableLambdaImpl r17, androidx.compose.runtime.Composer r18, final int r19) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt.TileContainer(kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, com.android.systemui.qs.panels.ui.viewmodel.AccessibilityUiState, boolean, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int):void");
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
            ExpandableKt.Expandable(ExpandableControllerKt.m908rememberExpandableControllerT042LqI(function03, roundedCornerShape, 0L, null, null, composerImpl, i2 & 126, 28), LookaheadScopeKt.approachLayout(ClipKt.clip(modifier, roundedCornerShape), new SquishTileKt$$ExternalSyntheticLambda0(function02), LookaheadScopeKt.defaultPlacementApproachInProgress, new SquishTileKt$$ExternalSyntheticLambda1(function02)), null, null, true, false, ComposableLambdaKt.rememberComposableLambda(1658196727, new Function3() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$TileExpandable$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Expandable expandable = (Expandable) obj;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileExpandable.<anonymous> (Tile.kt:274)");
                    }
                    TileHapticsViewModel tileHapticsViewModel2 = tileHapticsViewModel;
                    if (tileHapticsViewModel2 != null) {
                        expandable = tileHapticsViewModel2.createStateAwareExpandable(expandable);
                    }
                    Function3.this.invoke(expandable, composer2, 0);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    TileKt.TileExpandable(Function0.this, roundedCornerShape, function02, tileHapticsViewModel, modifier, composableLambdaImpl2, (Composer) obj, updateChangedFlags);
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
            LazyGridDslKt.LazyVerticalGrid((i2 & 14) | 1769472 | (i2 & 112) | (i2 & 896) | 3072, (i2 >> 12) & 14, 912, null, null, Arrangement.m91spacedBy0680j_4(f), Arrangement.m91spacedBy0680j_4(f), paddingValuesImpl, fixed, lazyGridState, composerImpl, modifier, function1, false, false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(modifier, lazyGridState, paddingValuesImpl, function1, i) { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ LazyGridState f$2;
                public final /* synthetic */ PaddingValuesImpl f$3;
                public final /* synthetic */ Function1 f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(3073);
                    GridCells.Fixed fixed2 = GridCells.Fixed.this;
                    PaddingValuesImpl paddingValuesImpl2 = this.f$3;
                    Function1 function12 = this.f$4;
                    TileKt.TileLazyGrid(fixed2, this.f$1, this.f$2, paddingValuesImpl2, function12, (Composer) obj, updateChangedFlags);
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
        return PaddingKt.m128paddingqDBjuR0$default(modifier, CommonTileDefaults.TileStartPadding, 0.0f, CommonTileDefaults.TileEndPadding, 0.0f, 10);
    }
}
