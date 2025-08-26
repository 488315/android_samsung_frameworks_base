package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.ScrollableDefaults;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class LazyGridDslKt {
    /* JADX WARN: Removed duplicated region for block: B:102:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:220:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyHorizontalGrid(final int i, final int i2, final int i3, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior, Arrangement.Horizontal horizontal, Arrangement.Vertical vertical, PaddingValues paddingValues, final GridCells gridCells, LazyGridState lazyGridState, Composer composer, Modifier modifier, final Function1 function1, boolean z, boolean z2) {
        int i4;
        Modifier modifier2;
        int i5;
        boolean z3;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        int i6;
        int i7;
        boolean z4;
        int i8;
        Arrangement.Horizontal horizontal2;
        int i9;
        final Arrangement.Vertical vertical2;
        int i10;
        FlingBehavior flingBehavior2;
        int i11;
        boolean z5;
        int i12;
        ComposerImpl composerImpl;
        final LazyGridState lazyGridState2;
        final Arrangement.Vertical vertical3;
        final boolean z6;
        final boolean z7;
        final PaddingValues paddingValues2;
        final FlingBehavior flingBehavior3;
        final Arrangement.Horizontal horizontal3;
        final OverscrollEffect overscrollEffect2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        LazyGridState lazyGridStateRememberLazyGridState;
        OverscrollEffect overscrollEffectRememberOverscrollEffect;
        FlingBehavior flingBehavior4;
        LazyGridState lazyGridState3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(635941664);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl2.changed(gridCells) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        int i13 = i3 & 2;
        if (i13 != 0) {
            i4 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i4 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            if ((i & 384) == 0) {
                i4 |= ((i3 & 4) == 0 && composerImpl2.changed(lazyGridState)) ? 256 : 128;
            }
            i5 = i3 & 8;
            if (i5 == 0) {
                i4 |= 3072;
                z3 = true;
            } else {
                z3 = true;
                if ((i & 3072) == 0) {
                    paddingValuesM120PaddingValues0680j_4 = paddingValues;
                    i4 |= composerImpl2.changed(paddingValuesM120PaddingValues0680j_4) ? 2048 : 1024;
                }
                i6 = i3 & 16;
                if (i6 != 0) {
                    i4 |= 24576;
                    z4 = z;
                    i7 = 32;
                } else {
                    i7 = 32;
                    if ((i & 24576) == 0) {
                        z4 = z;
                        i4 |= composerImpl2.changed(z4) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    } else {
                        z4 = z;
                    }
                }
                if ((i & 196608) == 0) {
                    if ((i3 & 32) == 0) {
                        i8 = 4;
                        horizontal2 = horizontal;
                        int i14 = composerImpl2.changed(horizontal2) ? 131072 : 65536;
                        i4 |= i14;
                    } else {
                        i8 = 4;
                        horizontal2 = horizontal;
                    }
                    i4 |= i14;
                } else {
                    i8 = 4;
                    horizontal2 = horizontal;
                }
                i9 = i3 & 64;
                if (i9 == 0) {
                    vertical2 = vertical;
                    if ((i & 1572864) == 0) {
                        i10 = 3;
                        i4 |= composerImpl2.changed(vertical2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                    if ((i & 12582912) != 0) {
                        if ((i3 & 128) == 0) {
                            flingBehavior2 = flingBehavior;
                            int i15 = composerImpl2.changed(flingBehavior2) ? 8388608 : 4194304;
                            i4 |= i15;
                        } else {
                            flingBehavior2 = flingBehavior;
                        }
                        i4 |= i15;
                    } else {
                        flingBehavior2 = flingBehavior;
                    }
                    i11 = 256 & i3;
                    if (i11 == 0) {
                        i4 |= 100663296;
                        z5 = z2;
                    } else {
                        z5 = z2;
                        if ((i & 100663296) == 0) {
                            i4 |= composerImpl2.changed(z5) ? 67108864 : 33554432;
                        }
                    }
                    if ((i & 805306368) == 0) {
                        i4 |= ((i3 & 512) == 0 && composerImpl2.changed(overscrollEffect)) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((1024 & i3) == 0) {
                        i12 = i2 | 6;
                    } else if ((i2 & 6) == 0) {
                        i12 = i2 | (composerImpl2.changedInstance(function1) ? i8 : 2);
                    } else {
                        i12 = i2;
                    }
                    if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
                        composerImpl = composerImpl2;
                        composerImpl.skipToGroupEnd();
                        lazyGridState2 = lazyGridState;
                        vertical3 = vertical2;
                        z6 = z5;
                        z7 = z4;
                        paddingValues2 = paddingValuesM120PaddingValues0680j_4;
                        flingBehavior3 = flingBehavior2;
                        horizontal3 = horizontal2;
                        overscrollEffect2 = overscrollEffect;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i13 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i3 & 4) != 0) {
                                lazyGridStateRememberLazyGridState = LazyGridStateKt.rememberLazyGridState(0, 0, composerImpl2, i10);
                                i4 &= -897;
                            } else {
                                lazyGridStateRememberLazyGridState = lazyGridState;
                            }
                            if (i5 != 0) {
                                Dp.Companion companion = Dp.Companion;
                                paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                            }
                            if (i6 != 0) {
                                z4 = false;
                            }
                            if ((i3 & 32) != 0) {
                                Arrangement.INSTANCE.getClass();
                                i4 &= -458753;
                                horizontal2 = !z4 ? Arrangement.Start : Arrangement.End;
                            }
                            if (i9 != 0) {
                                Arrangement.INSTANCE.getClass();
                                vertical2 = Arrangement.Top;
                            }
                            if ((128 & i3) != 0) {
                                ScrollableDefaults.INSTANCE.getClass();
                                i4 &= -29360129;
                                flingBehavior2 = ScrollableDefaults.flingBehavior(composerImpl2);
                            }
                            if (i11 != 0) {
                                z5 = z3;
                            }
                            if ((i3 & 512) != 0) {
                                i4 &= -1879048193;
                                overscrollEffectRememberOverscrollEffect = OverscrollKt.rememberOverscrollEffect(composerImpl2);
                            } else {
                                overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            }
                            flingBehavior4 = flingBehavior2;
                            lazyGridState3 = lazyGridStateRememberLazyGridState;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i3 & 4) != 0) {
                                i4 &= -897;
                            }
                            if ((i3 & 32) != 0) {
                                i4 &= -458753;
                            }
                            if ((128 & i3) != 0) {
                                i4 &= -29360129;
                            }
                            if ((i3 & 512) != 0) {
                                i4 &= -1879048193;
                            }
                            overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            lazyGridState3 = lazyGridState;
                            flingBehavior4 = flingBehavior2;
                        }
                        int i16 = i12;
                        boolean z8 = z4;
                        Arrangement.Horizontal horizontal4 = horizontal2;
                        int i17 = i7;
                        PaddingValues paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.LazyHorizontalGrid (LazyGridDsl.kt:168)");
                        }
                        int i18 = i4 >> 15;
                        int i19 = (i4 & 14) | (i18 & 112);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberRowHeightSums (LazyGridDsl.kt:248)");
                        }
                        int i20 = i8;
                        boolean z9 = (((((i19 & 14) ^ 6) <= i20 || !composerImpl2.changed(gridCells)) && (i19 & 6) != i20) ? false : z3) | (((((i19 & 112) ^ 48) <= i17 || !composerImpl2.changed(vertical2)) && (i19 & 48) != i17) ? false : z3);
                        Object objRememberedValue = composerImpl2.rememberedValue();
                        if (!z9) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new GridSlotCache(new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridDslKt$rememberRowHeightSums$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        Density density = (Density) obj;
                                        long j = ((Constraints) obj2).value;
                                        if (Constraints.m822getMaxHeightimpl(j) == Integer.MAX_VALUE) {
                                            InlineClassHelperKt.throwIllegalArgumentException("LazyHorizontalGrid's height should be bound by parent.");
                                        }
                                        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                                        GridCells gridCells2 = gridCells;
                                        Arrangement.Vertical vertical4 = vertical2;
                                        int[] intArray = CollectionsKt___CollectionsKt.toIntArray(((GridCells.Fixed) gridCells2).calculateCrossAxisCellSizes(iM822getMaxHeightimpl, density.mo52roundToPx0680j_4(vertical4.mo95getSpacingD9Ej5fM())));
                                        int[] iArr = new int[intArray.length];
                                        vertical4.arrange(density, iM822getMaxHeightimpl, intArray, iArr);
                                        return new LazyGridSlots(intArray, iArr);
                                    }
                                });
                                composerImpl2.updateRememberedValue(objRememberedValue);
                            }
                            LazyGridSlotsProvider lazyGridSlotsProvider = (LazyGridSlotsProvider) objRememberedValue;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            int i21 = i4 >> 3;
                            Arrangement.Vertical vertical4 = vertical2;
                            boolean z10 = z5;
                            composerImpl = composerImpl2;
                            LazyGridKt.LazyGrid(modifier2, lazyGridState3, lazyGridSlotsProvider, paddingValues3, z8, false, flingBehavior4, z10, overscrollEffectRememberOverscrollEffect, vertical4, horizontal4, function1, composerImpl, 196608 | (i21 & 14) | (i21 & 112) | (i4 & 7168) | (57344 & i4) | (3670016 & i21) | (29360128 & i21) | (i21 & 234881024) | ((i4 << 9) & 1879048192), (i18 & 14) | ((i16 << 3) & 112), 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            lazyGridState2 = lazyGridState3;
                            paddingValues2 = paddingValues3;
                            z7 = z8;
                            flingBehavior3 = flingBehavior4;
                            z6 = z10;
                            overscrollEffect2 = overscrollEffectRememberOverscrollEffect;
                            vertical3 = vertical4;
                            horizontal3 = horizontal4;
                        }
                    }
                    final Modifier modifier3 = modifier2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridDslKt.LazyHorizontalGrid.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                GridCells gridCells2 = gridCells;
                                Modifier modifier4 = modifier3;
                                LazyGridState lazyGridState4 = lazyGridState2;
                                PaddingValues paddingValues4 = paddingValues2;
                                boolean z11 = z7;
                                Arrangement.Horizontal horizontal5 = horizontal3;
                                Arrangement.Vertical vertical5 = vertical3;
                                FlingBehavior flingBehavior5 = flingBehavior3;
                                boolean z12 = z6;
                                OverscrollEffect overscrollEffect3 = overscrollEffect2;
                                Function1 function12 = function1;
                                LazyGridDslKt.LazyHorizontalGrid(RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3, overscrollEffect3, flingBehavior5, horizontal5, vertical5, paddingValues4, gridCells2, lazyGridState4, (Composer) obj, modifier4, function12, z11, z12);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i4 |= 1572864;
                vertical2 = vertical;
                i10 = 3;
                if ((i & 12582912) != 0) {
                }
                i11 = 256 & i3;
                if (i11 == 0) {
                }
                if ((i & 805306368) == 0) {
                }
                if ((1024 & i3) == 0) {
                }
                if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
                }
                final Modifier modifier32 = modifier2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            paddingValuesM120PaddingValues0680j_4 = paddingValues;
            i6 = i3 & 16;
            if (i6 != 0) {
            }
            if ((i & 196608) == 0) {
            }
            i9 = i3 & 64;
            if (i9 == 0) {
            }
            i10 = 3;
            if ((i & 12582912) != 0) {
            }
            i11 = 256 & i3;
            if (i11 == 0) {
            }
            if ((i & 805306368) == 0) {
            }
            if ((1024 & i3) == 0) {
            }
            if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
            }
            final Modifier modifier322 = modifier2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        if ((i & 384) == 0) {
        }
        i5 = i3 & 8;
        if (i5 == 0) {
        }
        paddingValuesM120PaddingValues0680j_4 = paddingValues;
        i6 = i3 & 16;
        if (i6 != 0) {
        }
        if ((i & 196608) == 0) {
        }
        i9 = i3 & 64;
        if (i9 == 0) {
        }
        i10 = 3;
        if ((i & 12582912) != 0) {
        }
        i11 = 256 & i3;
        if (i11 == 0) {
        }
        if ((i & 805306368) == 0) {
        }
        if ((1024 & i3) == 0) {
        }
        if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
        }
        final Modifier modifier3222 = modifier2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:219:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyVerticalGrid(final int i, final int i2, final int i3, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior, Arrangement.Horizontal horizontal, Arrangement.Vertical vertical, PaddingValues paddingValues, final GridCells gridCells, LazyGridState lazyGridState, Composer composer, Modifier modifier, final Function1 function1, boolean z, boolean z2) {
        int i4;
        Modifier modifier2;
        int i5;
        boolean z3;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        int i6;
        int i7;
        boolean z4;
        int i8;
        Arrangement.Vertical vertical2;
        int i9;
        final Arrangement.Horizontal horizontal2;
        int i10;
        FlingBehavior flingBehavior2;
        int i11;
        boolean z5;
        int i12;
        ComposerImpl composerImpl;
        final LazyGridState lazyGridState2;
        final Arrangement.Horizontal horizontal3;
        final boolean z6;
        final boolean z7;
        final PaddingValues paddingValues2;
        final FlingBehavior flingBehavior3;
        final Arrangement.Vertical vertical3;
        final OverscrollEffect overscrollEffect2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        LazyGridState lazyGridStateRememberLazyGridState;
        OverscrollEffect overscrollEffectRememberOverscrollEffect;
        FlingBehavior flingBehavior4;
        Arrangement.Vertical vertical4;
        LazyGridState lazyGridState3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2072102870);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl2.changed(gridCells) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        int i13 = i3 & 2;
        if (i13 != 0) {
            i4 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i4 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            if ((i & 384) == 0) {
                i4 |= ((i3 & 4) == 0 && composerImpl2.changed(lazyGridState)) ? 256 : 128;
            }
            i5 = i3 & 8;
            if (i5 == 0) {
                i4 |= 3072;
                z3 = true;
            } else {
                z3 = true;
                if ((i & 3072) == 0) {
                    paddingValuesM120PaddingValues0680j_4 = paddingValues;
                    i4 |= composerImpl2.changed(paddingValuesM120PaddingValues0680j_4) ? 2048 : 1024;
                }
                i6 = i3 & 16;
                if (i6 != 0) {
                    i4 |= 24576;
                    z4 = z;
                    i7 = 32;
                } else {
                    i7 = 32;
                    if ((i & 24576) == 0) {
                        z4 = z;
                        i4 |= composerImpl2.changed(z4) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    } else {
                        z4 = z;
                    }
                }
                if ((i & 196608) == 0) {
                    if ((i3 & 32) == 0) {
                        i8 = 4;
                        vertical2 = vertical;
                        int i14 = composerImpl2.changed(vertical2) ? 131072 : 65536;
                        i4 |= i14;
                    } else {
                        i8 = 4;
                        vertical2 = vertical;
                    }
                    i4 |= i14;
                } else {
                    i8 = 4;
                    vertical2 = vertical;
                }
                i9 = i3 & 64;
                if (i9 == 0) {
                    horizontal2 = horizontal;
                    if ((i & 1572864) == 0) {
                        i10 = 3;
                        i4 |= composerImpl2.changed(horizontal2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                    if ((i & 12582912) != 0) {
                        if ((i3 & 128) == 0) {
                            flingBehavior2 = flingBehavior;
                            int i15 = composerImpl2.changed(flingBehavior2) ? 8388608 : 4194304;
                            i4 |= i15;
                        } else {
                            flingBehavior2 = flingBehavior;
                        }
                        i4 |= i15;
                    } else {
                        flingBehavior2 = flingBehavior;
                    }
                    i11 = 256 & i3;
                    if (i11 == 0) {
                        i4 |= 100663296;
                        z5 = z2;
                    } else {
                        z5 = z2;
                        if ((i & 100663296) == 0) {
                            i4 |= composerImpl2.changed(z5) ? 67108864 : 33554432;
                        }
                    }
                    if ((i & 805306368) == 0) {
                        i4 |= ((i3 & 512) == 0 && composerImpl2.changed(overscrollEffect)) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((1024 & i3) == 0) {
                        i12 = i2 | 6;
                    } else if ((i2 & 6) == 0) {
                        i12 = i2 | (composerImpl2.changedInstance(function1) ? i8 : 2);
                    } else {
                        i12 = i2;
                    }
                    if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
                        composerImpl = composerImpl2;
                        composerImpl.skipToGroupEnd();
                        lazyGridState2 = lazyGridState;
                        horizontal3 = horizontal2;
                        z6 = z5;
                        z7 = z4;
                        paddingValues2 = paddingValuesM120PaddingValues0680j_4;
                        flingBehavior3 = flingBehavior2;
                        vertical3 = vertical2;
                        overscrollEffect2 = overscrollEffect;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i13 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i3 & 4) != 0) {
                                lazyGridStateRememberLazyGridState = LazyGridStateKt.rememberLazyGridState(0, 0, composerImpl2, i10);
                                i4 &= -897;
                            } else {
                                lazyGridStateRememberLazyGridState = lazyGridState;
                            }
                            if (i5 != 0) {
                                Dp.Companion companion = Dp.Companion;
                                paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                            }
                            if (i6 != 0) {
                                z4 = false;
                            }
                            if ((i3 & 32) != 0) {
                                Arrangement.INSTANCE.getClass();
                                i4 &= -458753;
                                vertical2 = !z4 ? Arrangement.Top : Arrangement.Bottom;
                            }
                            if (i9 != 0) {
                                Arrangement.INSTANCE.getClass();
                                horizontal2 = Arrangement.Start;
                            }
                            if ((128 & i3) != 0) {
                                ScrollableDefaults.INSTANCE.getClass();
                                i4 &= -29360129;
                                flingBehavior2 = ScrollableDefaults.flingBehavior(composerImpl2);
                            }
                            if (i11 != 0) {
                                z5 = z3;
                            }
                            if ((i3 & 512) != 0) {
                                i4 &= -1879048193;
                                overscrollEffectRememberOverscrollEffect = OverscrollKt.rememberOverscrollEffect(composerImpl2);
                            } else {
                                overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            }
                            flingBehavior4 = flingBehavior2;
                            vertical4 = vertical2;
                            lazyGridState3 = lazyGridStateRememberLazyGridState;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i3 & 4) != 0) {
                                i4 &= -897;
                            }
                            if ((i3 & 32) != 0) {
                                i4 &= -458753;
                            }
                            if ((128 & i3) != 0) {
                                i4 &= -29360129;
                            }
                            if ((i3 & 512) != 0) {
                                i4 &= -1879048193;
                            }
                            overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            lazyGridState3 = lazyGridState;
                            flingBehavior4 = flingBehavior2;
                            vertical4 = vertical2;
                        }
                        int i16 = i7;
                        boolean z8 = z4;
                        PaddingValues paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.LazyVerticalGrid (LazyGridDsl.kt:79)");
                        }
                        int i17 = (i4 & 14) | ((i4 >> 15) & 112);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberColumnWidthSums (LazyGridDsl.kt:221)");
                        }
                        int i18 = i8;
                        boolean z9 = (((((i17 & 14) ^ 6) <= i18 || !composerImpl2.changed(gridCells)) && (i17 & 6) != i18) ? false : z3) | (((((i17 & 112) ^ 48) > i16 && composerImpl2.changed(horizontal2)) || (i17 & 48) == i16) ? z3 : false);
                        Object objRememberedValue = composerImpl2.rememberedValue();
                        if (!z9) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new GridSlotCache(new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridDslKt$rememberColumnWidthSums$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        Density density = (Density) obj;
                                        long j = ((Constraints) obj2).value;
                                        if (Constraints.m823getMaxWidthimpl(j) == Integer.MAX_VALUE) {
                                            InlineClassHelperKt.throwIllegalArgumentException("LazyVerticalGrid's width should be bound by parent.");
                                        }
                                        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                                        GridCells gridCells2 = gridCells;
                                        Arrangement.Horizontal horizontal4 = horizontal2;
                                        int[] intArray = CollectionsKt___CollectionsKt.toIntArray(((GridCells.Fixed) gridCells2).calculateCrossAxisCellSizes(iM823getMaxWidthimpl, density.mo52roundToPx0680j_4(horizontal4.mo95getSpacingD9Ej5fM())));
                                        int[] iArr = new int[intArray.length];
                                        horizontal4.arrange(density, iM823getMaxWidthimpl, intArray, LayoutDirection.Ltr, iArr);
                                        return new LazyGridSlots(intArray, iArr);
                                    }
                                });
                                composerImpl2.updateRememberedValue(objRememberedValue);
                            }
                            LazyGridSlotsProvider lazyGridSlotsProvider = (LazyGridSlotsProvider) objRememberedValue;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            int i19 = i4 >> 3;
                            int i20 = ((i4 >> 18) & 14) | ((i12 << 3) & 112);
                            Arrangement.Horizontal horizontal4 = horizontal2;
                            boolean z10 = z5;
                            composerImpl = composerImpl2;
                            LazyGridKt.LazyGrid(modifier2, lazyGridState3, lazyGridSlotsProvider, paddingValues3, z8, true, flingBehavior4, z10, overscrollEffectRememberOverscrollEffect, vertical4, horizontal4, function1, composerImpl, 196608 | (i19 & 14) | (i19 & 112) | (i4 & 7168) | (57344 & i4) | (3670016 & i19) | (29360128 & i19) | (i19 & 234881024) | ((i4 << 12) & 1879048192), i20, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            lazyGridState2 = lazyGridState3;
                            paddingValues2 = paddingValues3;
                            z7 = z8;
                            flingBehavior3 = flingBehavior4;
                            z6 = z10;
                            overscrollEffect2 = overscrollEffectRememberOverscrollEffect;
                            vertical3 = vertical4;
                            horizontal3 = horizontal4;
                        }
                    }
                    final Modifier modifier3 = modifier2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridDslKt.LazyVerticalGrid.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                GridCells gridCells2 = gridCells;
                                Modifier modifier4 = modifier3;
                                LazyGridState lazyGridState4 = lazyGridState2;
                                PaddingValues paddingValues4 = paddingValues2;
                                boolean z11 = z7;
                                Arrangement.Vertical vertical5 = vertical3;
                                Arrangement.Horizontal horizontal5 = horizontal3;
                                FlingBehavior flingBehavior5 = flingBehavior3;
                                boolean z12 = z6;
                                OverscrollEffect overscrollEffect3 = overscrollEffect2;
                                Function1 function12 = function1;
                                LazyGridDslKt.LazyVerticalGrid(RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3, overscrollEffect3, flingBehavior5, horizontal5, vertical5, paddingValues4, gridCells2, lazyGridState4, (Composer) obj, modifier4, function12, z11, z12);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i4 |= 1572864;
                horizontal2 = horizontal;
                i10 = 3;
                if ((i & 12582912) != 0) {
                }
                i11 = 256 & i3;
                if (i11 == 0) {
                }
                if ((i & 805306368) == 0) {
                }
                if ((1024 & i3) == 0) {
                }
                if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
                }
                final Modifier modifier32 = modifier2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            paddingValuesM120PaddingValues0680j_4 = paddingValues;
            i6 = i3 & 16;
            if (i6 != 0) {
            }
            if ((i & 196608) == 0) {
            }
            i9 = i3 & 64;
            if (i9 == 0) {
            }
            i10 = 3;
            if ((i & 12582912) != 0) {
            }
            i11 = 256 & i3;
            if (i11 == 0) {
            }
            if ((i & 805306368) == 0) {
            }
            if ((1024 & i3) == 0) {
            }
            if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
            }
            final Modifier modifier322 = modifier2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        if ((i & 384) == 0) {
        }
        i5 = i3 & 8;
        if (i5 == 0) {
        }
        paddingValuesM120PaddingValues0680j_4 = paddingValues;
        i6 = i3 & 16;
        if (i6 != 0) {
        }
        if ((i & 196608) == 0) {
        }
        i9 = i3 & 64;
        if (i9 == 0) {
        }
        i10 = 3;
        if ((i & 12582912) != 0) {
        }
        i11 = 256 & i3;
        if (i11 == 0) {
        }
        if ((i & 805306368) == 0) {
        }
        if ((1024 & i3) == 0) {
        }
        if (composerImpl2.shouldExecute(i4 & 1, ((i4 & 306783379) == 306783378 || (i12 & 3) != 2) ? z3 : false)) {
        }
        final Modifier modifier3222 = modifier2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
