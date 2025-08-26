package androidx.compose.foundation.lazy;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.ScrollableDefaults;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class LazyDslKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyColumn(Modifier modifier, LazyListState lazyListState, PaddingValues paddingValues, boolean z, Arrangement.Vertical vertical, Alignment.Horizontal horizontal, FlingBehavior flingBehavior, boolean z2, OverscrollEffect overscrollEffect, final Function1 function1, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        LazyListState lazyListStateRememberLazyListState;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        int i4;
        Arrangement.Vertical vertical2;
        int i5;
        Alignment.Horizontal horizontal2;
        boolean z3;
        final FlingBehavior flingBehavior2;
        int i6;
        boolean z4;
        int i7;
        ComposerImpl composerImpl;
        final boolean z5;
        final Alignment.Horizontal horizontal3;
        final Modifier modifier3;
        final LazyListState lazyListState2;
        final PaddingValues paddingValues2;
        final OverscrollEffect overscrollEffect2;
        final Arrangement.Vertical vertical3;
        final boolean z6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        OverscrollEffect overscrollEffectRememberOverscrollEffect;
        boolean z7;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(53695811);
        int i8 = i2 & 1;
        if (i8 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 6) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        if ((i & 48) == 0) {
            if ((i2 & 2) == 0) {
                lazyListStateRememberLazyListState = lazyListState;
                int i9 = composerImpl2.changed(lazyListStateRememberLazyListState) ? 32 : 16;
                i3 |= i9;
            } else {
                lazyListStateRememberLazyListState = lazyListState;
            }
            i3 |= i9;
        } else {
            lazyListStateRememberLazyListState = lazyListState;
        }
        int i10 = 4 & i2;
        if (i10 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                paddingValuesM120PaddingValues0680j_4 = paddingValues;
                i3 |= composerImpl2.changed(paddingValuesM120PaddingValues0680j_4) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else if ((i & 3072) == 0) {
                i3 |= composerImpl2.changed(z) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                if ((i2 & 16) == 0) {
                    vertical2 = vertical;
                    int i11 = composerImpl2.changed(vertical2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    i3 |= i11;
                } else {
                    vertical2 = vertical;
                }
                i3 |= i11;
            } else {
                vertical2 = vertical;
            }
            i5 = i2 & 32;
            if (i5 == 0) {
                i3 |= 196608;
            } else {
                if ((i & 196608) == 0) {
                    horizontal2 = horizontal;
                    i3 |= composerImpl2.changed(horizontal2) ? 131072 : 65536;
                }
                if ((i & 1572864) == 0) {
                    z3 = true;
                    flingBehavior2 = flingBehavior;
                    i3 |= ((i2 & 64) == 0 && composerImpl2.changed(flingBehavior2)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                } else {
                    z3 = true;
                    flingBehavior2 = flingBehavior;
                }
                i6 = 128 & i2;
                if (i6 != 0) {
                    i3 |= 12582912;
                    z4 = z2;
                } else {
                    z4 = z2;
                    if ((i & 12582912) == 0) {
                        i3 |= composerImpl2.changed(z4) ? 8388608 : 4194304;
                    }
                }
                if ((i & 100663296) == 0) {
                    i7 = i6;
                    i3 |= ((i2 & 256) == 0 && composerImpl2.changed(overscrollEffect)) ? 67108864 : 33554432;
                } else {
                    i7 = i6;
                }
                if ((i2 & 512) == 0) {
                    if ((i & 805306368) == 0) {
                        i3 |= composerImpl2.changedInstance(function1) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
                        composerImpl = composerImpl2;
                        composerImpl.skipToGroupEnd();
                        z5 = z4;
                        horizontal3 = horizontal2;
                        modifier3 = modifier2;
                        lazyListState2 = lazyListStateRememberLazyListState;
                        paddingValues2 = paddingValuesM120PaddingValues0680j_4;
                        overscrollEffect2 = overscrollEffect;
                        vertical3 = vertical2;
                        z6 = z;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i8 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i2 & 2) != 0) {
                                i3 &= -113;
                                lazyListStateRememberLazyListState = LazyListStateKt.rememberLazyListState(composerImpl2);
                            }
                            if (i10 != 0) {
                                Dp.Companion companion = Dp.Companion;
                                paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                            }
                            boolean z8 = i4 == 0 ? z : false;
                            if ((i2 & 16) != 0) {
                                Arrangement.INSTANCE.getClass();
                                i3 &= -57345;
                                vertical2 = !z8 ? Arrangement.Top : Arrangement.Bottom;
                            }
                            if (i5 != 0) {
                                Alignment.Companion.getClass();
                                horizontal2 = Alignment.Companion.Start;
                            }
                            if ((i2 & 64) != 0) {
                                ScrollableDefaults.INSTANCE.getClass();
                                i3 &= -3670017;
                                flingBehavior2 = ScrollableDefaults.flingBehavior(composerImpl2);
                            }
                            if (i7 != 0) {
                                z4 = z3;
                            }
                            if ((256 & i2) != 0) {
                                i3 &= -234881025;
                                overscrollEffectRememberOverscrollEffect = OverscrollKt.rememberOverscrollEffect(composerImpl2);
                            } else {
                                overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            }
                            z7 = z8;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 2) != 0) {
                                i3 &= -113;
                            }
                            if ((i2 & 16) != 0) {
                                i3 &= -57345;
                            }
                            if ((i2 & 64) != 0) {
                                i3 &= -3670017;
                            }
                            if ((256 & i2) != 0) {
                                i3 &= -234881025;
                            }
                            z7 = z;
                            overscrollEffectRememberOverscrollEffect = overscrollEffect;
                        }
                        boolean z9 = z4;
                        Arrangement.Vertical vertical4 = vertical2;
                        Alignment.Horizontal horizontal4 = horizontal2;
                        FlingBehavior flingBehavior3 = flingBehavior2;
                        PaddingValues paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                        LazyListState lazyListState3 = lazyListStateRememberLazyListState;
                        Modifier modifier4 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.LazyColumn (LazyDsl.kt:399)");
                        }
                        int i12 = i3 >> 3;
                        composerImpl = composerImpl2;
                        LazyListKt.LazyList(modifier4, lazyListState3, paddingValues3, z7, true, flingBehavior3, z9, overscrollEffectRememberOverscrollEffect, 0, horizontal4, vertical4, null, null, function1, composerImpl, (i3 & 14) | 24576 | (i3 & 112) | (i3 & 896) | (i3 & 7168) | (458752 & i12) | (3670016 & i12) | (i12 & 29360128) | ((i3 << 12) & 1879048192), ((i3 >> 12) & 14) | ((i3 >> 18) & 7168), 6400);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        modifier3 = modifier4;
                        lazyListState2 = lazyListState3;
                        paddingValues2 = paddingValues3;
                        z6 = z7;
                        flingBehavior2 = flingBehavior3;
                        z5 = z9;
                        overscrollEffect2 = overscrollEffectRememberOverscrollEffect;
                        horizontal3 = horizontal4;
                        vertical3 = vertical4;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.LazyDslKt.LazyColumn.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                LazyDslKt.LazyColumn(modifier3, lazyListState2, paddingValues2, z6, vertical3, horizontal3, flingBehavior2, z5, overscrollEffect2, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 805306368;
                if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            horizontal2 = horizontal;
            if ((i & 1572864) == 0) {
            }
            i6 = 128 & i2;
            if (i6 != 0) {
            }
            if ((i & 100663296) == 0) {
            }
            if ((i2 & 512) == 0) {
            }
            if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        paddingValuesM120PaddingValues0680j_4 = paddingValues;
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        if ((i & 24576) != 0) {
        }
        i5 = i2 & 32;
        if (i5 == 0) {
        }
        horizontal2 = horizontal;
        if ((i & 1572864) == 0) {
        }
        i6 = 128 & i2;
        if (i6 != 0) {
        }
        if ((i & 100663296) == 0) {
        }
        if ((i2 & 512) == 0) {
        }
        if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyRow(Modifier modifier, LazyListState lazyListState, PaddingValues paddingValues, boolean z, Arrangement.Horizontal horizontal, Alignment.Vertical vertical, FlingBehavior flingBehavior, boolean z2, OverscrollEffect overscrollEffect, final Function1 function1, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        LazyListState lazyListStateRememberLazyListState;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        int i4;
        Arrangement.Horizontal horizontal2;
        int i5;
        Alignment.Vertical vertical2;
        boolean z3;
        final FlingBehavior flingBehavior2;
        int i6;
        boolean z4;
        int i7;
        ComposerImpl composerImpl;
        final boolean z5;
        final Alignment.Vertical vertical3;
        final Modifier modifier3;
        final LazyListState lazyListState2;
        final PaddingValues paddingValues2;
        final OverscrollEffect overscrollEffect2;
        final Arrangement.Horizontal horizontal3;
        final boolean z6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        OverscrollEffect overscrollEffectRememberOverscrollEffect;
        boolean z7;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1884325601);
        int i8 = i2 & 1;
        if (i8 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 6) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        if ((i & 48) == 0) {
            if ((i2 & 2) == 0) {
                lazyListStateRememberLazyListState = lazyListState;
                int i9 = composerImpl2.changed(lazyListStateRememberLazyListState) ? 32 : 16;
                i3 |= i9;
            } else {
                lazyListStateRememberLazyListState = lazyListState;
            }
            i3 |= i9;
        } else {
            lazyListStateRememberLazyListState = lazyListState;
        }
        int i10 = 4 & i2;
        if (i10 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                paddingValuesM120PaddingValues0680j_4 = paddingValues;
                i3 |= composerImpl2.changed(paddingValuesM120PaddingValues0680j_4) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else if ((i & 3072) == 0) {
                i3 |= composerImpl2.changed(z) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                if ((i2 & 16) == 0) {
                    horizontal2 = horizontal;
                    int i11 = composerImpl2.changed(horizontal2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    i3 |= i11;
                } else {
                    horizontal2 = horizontal;
                }
                i3 |= i11;
            } else {
                horizontal2 = horizontal;
            }
            i5 = i2 & 32;
            if (i5 == 0) {
                i3 |= 196608;
            } else {
                if ((i & 196608) == 0) {
                    vertical2 = vertical;
                    i3 |= composerImpl2.changed(vertical2) ? 131072 : 65536;
                }
                if ((i & 1572864) == 0) {
                    z3 = true;
                    flingBehavior2 = flingBehavior;
                    i3 |= ((i2 & 64) == 0 && composerImpl2.changed(flingBehavior2)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                } else {
                    z3 = true;
                    flingBehavior2 = flingBehavior;
                }
                i6 = 128 & i2;
                if (i6 != 0) {
                    i3 |= 12582912;
                    z4 = z2;
                } else {
                    z4 = z2;
                    if ((i & 12582912) == 0) {
                        i3 |= composerImpl2.changed(z4) ? 8388608 : 4194304;
                    }
                }
                if ((i & 100663296) == 0) {
                    i7 = i6;
                    i3 |= ((i2 & 256) == 0 && composerImpl2.changed(overscrollEffect)) ? 67108864 : 33554432;
                } else {
                    i7 = i6;
                }
                if ((i2 & 512) == 0) {
                    if ((i & 805306368) == 0) {
                        i3 |= composerImpl2.changedInstance(function1) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
                        composerImpl = composerImpl2;
                        composerImpl.skipToGroupEnd();
                        z5 = z4;
                        vertical3 = vertical2;
                        modifier3 = modifier2;
                        lazyListState2 = lazyListStateRememberLazyListState;
                        paddingValues2 = paddingValuesM120PaddingValues0680j_4;
                        overscrollEffect2 = overscrollEffect;
                        horizontal3 = horizontal2;
                        z6 = z;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i8 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i2 & 2) != 0) {
                                i3 &= -113;
                                lazyListStateRememberLazyListState = LazyListStateKt.rememberLazyListState(composerImpl2);
                            }
                            if (i10 != 0) {
                                Dp.Companion companion = Dp.Companion;
                                paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                            }
                            boolean z8 = i4 == 0 ? z : false;
                            if ((i2 & 16) != 0) {
                                Arrangement.INSTANCE.getClass();
                                i3 &= -57345;
                                horizontal2 = !z8 ? Arrangement.Start : Arrangement.End;
                            }
                            if (i5 != 0) {
                                Alignment.Companion.getClass();
                                vertical2 = Alignment.Companion.Top;
                            }
                            if ((i2 & 64) != 0) {
                                ScrollableDefaults.INSTANCE.getClass();
                                i3 &= -3670017;
                                flingBehavior2 = ScrollableDefaults.flingBehavior(composerImpl2);
                            }
                            if (i7 != 0) {
                                z4 = z3;
                            }
                            if ((256 & i2) != 0) {
                                i3 &= -234881025;
                                overscrollEffectRememberOverscrollEffect = OverscrollKt.rememberOverscrollEffect(composerImpl2);
                            } else {
                                overscrollEffectRememberOverscrollEffect = overscrollEffect;
                            }
                            z7 = z8;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 2) != 0) {
                                i3 &= -113;
                            }
                            if ((i2 & 16) != 0) {
                                i3 &= -57345;
                            }
                            if ((i2 & 64) != 0) {
                                i3 &= -3670017;
                            }
                            if ((256 & i2) != 0) {
                                i3 &= -234881025;
                            }
                            z7 = z;
                            overscrollEffectRememberOverscrollEffect = overscrollEffect;
                        }
                        boolean z9 = z4;
                        Arrangement.Horizontal horizontal4 = horizontal2;
                        Alignment.Vertical vertical4 = vertical2;
                        FlingBehavior flingBehavior3 = flingBehavior2;
                        PaddingValues paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                        LazyListState lazyListState3 = lazyListStateRememberLazyListState;
                        Modifier modifier4 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.LazyRow (LazyDsl.kt:339)");
                        }
                        int i12 = i3 >> 3;
                        composerImpl = composerImpl2;
                        LazyListKt.LazyList(modifier4, lazyListState3, paddingValues3, z7, false, flingBehavior3, z9, overscrollEffectRememberOverscrollEffect, 0, null, null, vertical4, horizontal4, function1, composerImpl, (i3 & 14) | 24576 | (i3 & 112) | (i3 & 896) | (i3 & 7168) | (458752 & i12) | (3670016 & i12) | (i12 & 29360128), ((i3 >> 12) & 112) | ((i3 >> 6) & 896) | ((i3 >> 18) & 7168), 1792);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        modifier3 = modifier4;
                        lazyListState2 = lazyListState3;
                        paddingValues2 = paddingValues3;
                        z6 = z7;
                        flingBehavior2 = flingBehavior3;
                        z5 = z9;
                        overscrollEffect2 = overscrollEffectRememberOverscrollEffect;
                        vertical3 = vertical4;
                        horizontal3 = horizontal4;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.LazyDslKt.LazyRow.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                LazyDslKt.LazyRow(modifier3, lazyListState2, paddingValues2, z6, horizontal3, vertical3, flingBehavior2, z5, overscrollEffect2, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 805306368;
                if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            vertical2 = vertical;
            if ((i & 1572864) == 0) {
            }
            i6 = 128 & i2;
            if (i6 != 0) {
            }
            if ((i & 100663296) == 0) {
            }
            if ((i2 & 512) == 0) {
            }
            if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        paddingValuesM120PaddingValues0680j_4 = paddingValues;
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        if ((i & 24576) != 0) {
        }
        i5 = i2 & 32;
        if (i5 == 0) {
        }
        vertical2 = vertical;
        if ((i & 1572864) == 0) {
        }
        i6 = 128 & i2;
        if (i6 != 0) {
        }
        if ((i & 100663296) == 0) {
        }
        if ((i2 & 512) == 0) {
        }
        if (composerImpl2.shouldExecute(i3 & 1, (i3 & 306783379) == 306783378 ? z3 : false)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
