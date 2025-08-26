package com.samsung.sesl.compose.component;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.material3.ScaffoldDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeLayoutKt;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

/* loaded from: classes4.dex */
public abstract class ScaffoldKt {
    public static final float FabSpacing;
    public static final StaticProvidableCompositionLocal LocalSeslFabPlacement = new StaticProvidableCompositionLocal(new ScaffoldKt$$ExternalSyntheticLambda1());

    static {
        Dp.Companion companion = Dp.Companion;
        FabSpacing = 16;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* renamed from: SeslScaffold-5k0As8s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3341SeslScaffold5k0As8s(Modifier modifier, Function2 function2, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, Function4 function4, int i, long j, WindowInsets windowInsets, ComposableLambdaImpl composableLambdaImpl4, Composer composer, int i2, int i3) {
        int i4;
        Function2 function22;
        ComposableLambdaImpl composableLambdaImpl5;
        long j2;
        int i5;
        int i6;
        ComposableLambdaImpl composableLambdaImpl6;
        int i7;
        WindowInsets windowInsets2;
        ComposableLambdaImpl composableLambdaImpl7;
        ComposerImpl composerImpl;
        ComposableLambdaImpl composableLambdaImpl8;
        ComposableLambdaImpl composableLambdaImpl9;
        ComposableLambdaImpl composableLambdaImpl10;
        int i8;
        long j3;
        WindowInsets windowInsets3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1084627303);
        if ((i2 & 6) == 0) {
            i4 = (composerImpl2.changed(modifier) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            function22 = function2;
            i4 |= composerImpl2.changedInstance(function22) ? 32 : 16;
        } else {
            function22 = function2;
        }
        int i9 = i4 | 384;
        int i10 = i3 & 8;
        if (i10 == 0) {
            if ((i2 & 3072) == 0) {
                composableLambdaImpl5 = composableLambdaImpl2;
                i9 |= composerImpl2.changedInstance(composableLambdaImpl5) ? 2048 : 1024;
            }
            int i11 = i9 | 24576;
            if ((196608 & i2) == 0) {
                i11 |= composerImpl2.changedInstance(function4) ? 131072 : 65536;
            }
            int i12 = i11 | 1572864;
            if ((12582912 & i2) != 0) {
                j2 = j;
                i12 |= ((i3 & 128) == 0 && composerImpl2.changed(j2)) ? 8388608 : 4194304;
            } else {
                j2 = j;
            }
            if ((100663296 & i2) == 0) {
                i12 |= 33554432;
            }
            i5 = i12 | 805306368;
            if ((306783379 & i5) == 306783378 || !composerImpl2.getSkipping()) {
                composerImpl2.startDefaults();
                if ((i2 & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                    ComposableSingletons$ScaffoldKt composableSingletons$ScaffoldKt = ComposableSingletons$ScaffoldKt.INSTANCE;
                    composableSingletons$ScaffoldKt.getClass();
                    ComposableLambdaImpl composableLambdaImpl11 = ComposableSingletons$ScaffoldKt.f122lambda2;
                    if (i10 != 0) {
                        composableSingletons$ScaffoldKt.getClass();
                        composableLambdaImpl5 = ComposableSingletons$ScaffoldKt.f123lambda3;
                    }
                    composableSingletons$ScaffoldKt.getClass();
                    ComposableLambdaImpl composableLambdaImpl12 = ComposableSingletons$ScaffoldKt.f124lambda4;
                    SeslFabPosition.Companion.getClass();
                    int i13 = SeslFabPosition.End;
                    if ((i3 & 128) != 0) {
                        SeslTheme.INSTANCE.getClass();
                        j2 = SeslTheme.getColorScheme(composerImpl2).roundedCorner;
                        i5 &= -29360129;
                    }
                    SeslScaffoldDefaults.INSTANCE.getClass();
                    composerImpl2.startReplaceGroup(-99216221);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldDefaults.<get-contentWindowInsets> (Scaffold.kt:296)");
                    }
                    ScaffoldDefaults.INSTANCE.getClass();
                    WindowInsets contentWindowInsets = ScaffoldDefaults.getContentWindowInsets(composerImpl2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl2.end(false);
                    i6 = i5 & (-234881025);
                    composableLambdaImpl6 = composableLambdaImpl12;
                    i7 = i13;
                    windowInsets2 = contentWindowInsets;
                    composableLambdaImpl7 = composableLambdaImpl11;
                } else {
                    composerImpl2.skipToGroupEnd();
                    if ((i3 & 128) != 0) {
                        i5 &= -29360129;
                    }
                    i6 = i5 & (-234881025);
                    composableLambdaImpl7 = composableLambdaImpl;
                    composableLambdaImpl6 = composableLambdaImpl3;
                    i7 = i;
                    windowInsets2 = windowInsets;
                }
                ComposableLambdaImpl composableLambdaImpl13 = composableLambdaImpl5;
                long j4 = j2;
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffold (Scaffold.kt:67)");
                }
                composerImpl = composerImpl2;
                m3342SeslScaffoldImpl5k0As8s(modifier, function22, composableLambdaImpl7, composableLambdaImpl13, composableLambdaImpl6, function4, i7, j4, windowInsets2, composableLambdaImpl4, composerImpl, i6 & 2147483646, 6);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composableLambdaImpl8 = composableLambdaImpl7;
                composableLambdaImpl9 = composableLambdaImpl13;
                composableLambdaImpl10 = composableLambdaImpl6;
                i8 = i7;
                j3 = j4;
                windowInsets3 = windowInsets2;
            } else {
                composerImpl2.skipToGroupEnd();
                composableLambdaImpl8 = composableLambdaImpl;
                composableLambdaImpl10 = composableLambdaImpl3;
                windowInsets3 = windowInsets;
                composerImpl = composerImpl2;
                composableLambdaImpl9 = composableLambdaImpl5;
                j3 = j2;
                i8 = i;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new ScaffoldKt$$ExternalSyntheticLambda0(modifier, function2, composableLambdaImpl8, composableLambdaImpl9, composableLambdaImpl10, function4, i8, j3, windowInsets3, composableLambdaImpl4, i2, i3, 0);
                return;
            }
            return;
        }
        i9 = i4 | 3456;
        composableLambdaImpl5 = composableLambdaImpl2;
        int i112 = i9 | 24576;
        if ((196608 & i2) == 0) {
        }
        int i122 = i112 | 1572864;
        if ((12582912 & i2) != 0) {
        }
        if ((100663296 & i2) == 0) {
        }
        i5 = i122 | 805306368;
        if ((306783379 & i5) == 306783378) {
            composerImpl2.startDefaults();
            if ((i2 & 1) != 0) {
                ComposableSingletons$ScaffoldKt composableSingletons$ScaffoldKt2 = ComposableSingletons$ScaffoldKt.INSTANCE;
                composableSingletons$ScaffoldKt2.getClass();
                ComposableLambdaImpl composableLambdaImpl112 = ComposableSingletons$ScaffoldKt.f122lambda2;
                if (i10 != 0) {
                }
                composableSingletons$ScaffoldKt2.getClass();
                ComposableLambdaImpl composableLambdaImpl122 = ComposableSingletons$ScaffoldKt.f124lambda4;
                SeslFabPosition.Companion.getClass();
                int i132 = SeslFabPosition.End;
                if ((i3 & 128) != 0) {
                }
                SeslScaffoldDefaults.INSTANCE.getClass();
                composerImpl2.startReplaceGroup(-99216221);
                if (ComposerKt.isTraceInProgress()) {
                }
                ScaffoldDefaults.INSTANCE.getClass();
                WindowInsets contentWindowInsets2 = ScaffoldDefaults.getContentWindowInsets(composerImpl2);
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl2.end(false);
                i6 = i5 & (-234881025);
                composableLambdaImpl6 = composableLambdaImpl122;
                i7 = i132;
                windowInsets2 = contentWindowInsets2;
                composableLambdaImpl7 = composableLambdaImpl112;
                ComposableLambdaImpl composableLambdaImpl132 = composableLambdaImpl5;
                long j42 = j2;
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl = composerImpl2;
                m3342SeslScaffoldImpl5k0As8s(modifier, function22, composableLambdaImpl7, composableLambdaImpl132, composableLambdaImpl6, function4, i7, j42, windowInsets2, composableLambdaImpl4, composerImpl, i6 & 2147483646, 6);
                if (ComposerKt.isTraceInProgress()) {
                }
                composableLambdaImpl8 = composableLambdaImpl7;
                composableLambdaImpl9 = composableLambdaImpl132;
                composableLambdaImpl10 = composableLambdaImpl6;
                i8 = i7;
                j3 = j42;
                windowInsets3 = windowInsets2;
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* renamed from: SeslScaffoldImpl-5k0As8s, reason: not valid java name */
    public static final void m3342SeslScaffoldImpl5k0As8s(Modifier modifier, Function2 function2, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, Function4 function4, int i, long j, WindowInsets windowInsets, ComposableLambdaImpl composableLambdaImpl4, Composer composer, int i2, int i3) {
        int i4;
        int i5;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1990694873);
        if ((i2 & 48) == 0) {
            i4 = (composerImpl.changedInstance(function2) ? 32 : 16) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl2) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerImpl.changedInstance(composableLambdaImpl3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            i4 |= composerImpl.changedInstance(function4) ? 131072 : 65536;
        }
        if ((1572864 & i2) == 0) {
            i4 |= composerImpl.changed(i) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((100663296 & i2) == 0) {
            i4 |= composerImpl.changed(windowInsets) ? 67108864 : 33554432;
        }
        if ((805306368 & i2) == 0) {
            i4 |= (1073741824 & i2) == 0 ? composerImpl.changed((Object) null) : composerImpl.changedInstance(null) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((i3 & 6) == 0) {
            i5 = i3 | (composerImpl.changedInstance(composableLambdaImpl4) ? 4 : 2);
        } else {
            i5 = i3;
        }
        if ((302589073 & i4) == 302589072 && (i5 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i2 & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldImpl (Scaffold.kt:98)");
            }
            m3343SeslScaffoldLayoutvZ1zQFI(i, function2, composableLambdaImpl4, composableLambdaImpl2, composableLambdaImpl3, windowInsets, composableLambdaImpl, function4, composerImpl, ((i5 << 6) & 896) | ((i4 >> 18) & 14) | (i4 & 112) | (i4 & 7168) | (57344 & i4) | ((i4 >> 9) & 458752) | ((i4 << 12) & 3670016) | ((i4 << 6) & 29360128) | ((i4 >> 3) & 234881024));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ScaffoldKt$$ExternalSyntheticLambda0(modifier, function2, composableLambdaImpl, composableLambdaImpl2, composableLambdaImpl3, function4, i, j, windowInsets, composableLambdaImpl4, i2, i3, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x016b  */
    /* renamed from: SeslScaffoldLayout-vZ1zQFI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3343SeslScaffoldLayoutvZ1zQFI(final int i, final Function2 function2, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, final ComposableLambdaImpl composableLambdaImpl3, final WindowInsets windowInsets, final ComposableLambdaImpl composableLambdaImpl4, final Function4 function4, Composer composer, final int i2) {
        int i3;
        ComposableLambdaImpl composableLambdaImpl5;
        WindowInsets windowInsets2;
        ComposableLambdaImpl composableLambdaImpl6;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1807799313);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            composableLambdaImpl5 = composableLambdaImpl;
            i3 |= composerImpl.changedInstance(composableLambdaImpl5) ? 256 : 128;
        } else {
            composableLambdaImpl5 = composableLambdaImpl;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl2) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= composerImpl.changedInstance(composableLambdaImpl3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            windowInsets2 = windowInsets;
            i3 |= composerImpl.changed(windowInsets2) ? 131072 : 65536;
        } else {
            windowInsets2 = windowInsets;
        }
        if ((1572864 & i2) == 0) {
            composableLambdaImpl6 = composableLambdaImpl4;
            i3 |= composerImpl.changedInstance(composableLambdaImpl6) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            composableLambdaImpl6 = composableLambdaImpl4;
        }
        if ((i2 & 12582912) == 0) {
            i3 |= composerImpl.changedInstance(function4) ? 8388608 : 4194304;
        }
        if ((i2 & 100663296) == 0) {
            i3 |= (i2 & 134217728) == 0 ? composerImpl.changed((Object) null) : composerImpl.changedInstance(null) ? 67108864 : 33554432;
        }
        if ((i3 & 38347923) == 38347922 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldLayout (Scaffold.kt:123)");
            }
            composerImpl.startReplaceGroup(1643971324);
            boolean z = ((i3 & 29360128) == 8388608) | ((i3 & 112) == 32) | ((234881024 & i3) == 67108864 || ((i3 & 134217728) != 0 && composerImpl.changedInstance(null))) | ((i3 & 7168) == 2048) | ((458752 & i3) == 131072) | ((57344 & i3) == 16384) | ((i3 & 14) == 4) | ((3670016 & i3) == 1048576) | ((i3 & 896) == 256);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    i4 = 1;
                    final ComposableLambdaImpl composableLambdaImpl7 = composableLambdaImpl5;
                    final ComposableLambdaImpl composableLambdaImpl8 = composableLambdaImpl6;
                    final WindowInsets windowInsets3 = windowInsets2;
                    Function2 function22 = new Function2() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            final SubcomposeMeasureScope subcomposeMeasureScope = (SubcomposeMeasureScope) obj;
                            Constraints constraints = (Constraints) obj2;
                            final int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(constraints.value);
                            final int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(constraints.value);
                            final long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(constraints.value, 0, 0, 0, 0, 10);
                            final WindowInsets windowInsets4 = windowInsets3;
                            final ComposableLambdaImpl composableLambdaImpl9 = composableLambdaImpl7;
                            final Function4 function42 = function4;
                            final Function2 function23 = function2;
                            final ComposableLambdaImpl composableLambdaImpl10 = composableLambdaImpl2;
                            final ComposableLambdaImpl composableLambdaImpl11 = composableLambdaImpl3;
                            final int i5 = i;
                            final ComposableLambdaImpl composableLambdaImpl12 = composableLambdaImpl8;
                            return subcomposeMeasureScope.layout$1(iM823getMaxWidthimpl, iM822getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$$ExternalSyntheticLambda5
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj3) {
                                    long j;
                                    Object next;
                                    WindowInsets windowInsets5;
                                    Object next2;
                                    Object next3;
                                    int i6;
                                    final SeslFabPlacement seslFabPlacement;
                                    Object next4;
                                    Integer numValueOf;
                                    int iIntValue;
                                    int iIntValue2;
                                    int iMo52roundToPx0680j_4;
                                    Object next5;
                                    Object next6;
                                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                    SeslScaffoldLayoutContent seslScaffoldLayoutContent = SeslScaffoldLayoutContent.TopBar;
                                    Function2 function24 = function23;
                                    final SubcomposeMeasureScope subcomposeMeasureScope2 = subcomposeMeasureScope;
                                    List listSubcompose = subcomposeMeasureScope2.subcompose(seslScaffoldLayoutContent, function24);
                                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSubcompose, 10));
                                    Iterator it = listSubcompose.iterator();
                                    while (true) {
                                        boolean zHasNext = it.hasNext();
                                        j = jM816copyZbe2FdA$default;
                                        if (!zHasNext) {
                                            break;
                                        }
                                        arrayList.add(((Measurable) it.next()).mo610measureBRTryo0(j));
                                    }
                                    Iterator it2 = arrayList.iterator();
                                    if (it2.hasNext()) {
                                        next = it2.next();
                                        if (it2.hasNext()) {
                                            int i7 = ((Placeable) next).height;
                                            do {
                                                Object next7 = it2.next();
                                                int i8 = ((Placeable) next7).height;
                                                if (i7 < i8) {
                                                    next = next7;
                                                    i7 = i8;
                                                }
                                            } while (it2.hasNext());
                                        }
                                    } else {
                                        next = null;
                                    }
                                    Placeable placeable = (Placeable) next;
                                    int i9 = placeable != null ? placeable.height : 0;
                                    List listSubcompose2 = subcomposeMeasureScope2.subcompose(SeslScaffoldLayoutContent.Snackbar, composableLambdaImpl10);
                                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSubcompose2, 10));
                                    Iterator it3 = listSubcompose2.iterator();
                                    while (true) {
                                        boolean zHasNext2 = it3.hasNext();
                                        windowInsets5 = windowInsets4;
                                        if (!zHasNext2) {
                                            break;
                                        }
                                        arrayList2.add(((Measurable) it3.next()).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU((-windowInsets5.getLeft(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection())) - windowInsets5.getRight(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection()), -windowInsets5.getBottom(subcomposeMeasureScope2), j)));
                                    }
                                    Iterator it4 = arrayList2.iterator();
                                    if (it4.hasNext()) {
                                        next2 = it4.next();
                                        if (it4.hasNext()) {
                                            int i10 = ((Placeable) next2).height;
                                            do {
                                                Object next8 = it4.next();
                                                int i11 = ((Placeable) next8).height;
                                                if (i10 < i11) {
                                                    next2 = next8;
                                                    i10 = i11;
                                                }
                                            } while (it4.hasNext());
                                        }
                                    } else {
                                        next2 = null;
                                    }
                                    Placeable placeable2 = (Placeable) next2;
                                    int i12 = placeable2 != null ? placeable2.height : 0;
                                    Iterator it5 = arrayList2.iterator();
                                    if (it5.hasNext()) {
                                        next3 = it5.next();
                                        if (it5.hasNext()) {
                                            int i13 = ((Placeable) next3).width;
                                            do {
                                                Object next9 = it5.next();
                                                int i14 = ((Placeable) next9).width;
                                                if (i13 < i14) {
                                                    i13 = i14;
                                                    next3 = next9;
                                                }
                                            } while (it5.hasNext());
                                        }
                                    } else {
                                        next3 = null;
                                    }
                                    Placeable placeable3 = (Placeable) next3;
                                    int i15 = placeable3 != null ? placeable3.width : 0;
                                    List listSubcompose3 = subcomposeMeasureScope2.subcompose(SeslScaffoldLayoutContent.Fab, composableLambdaImpl11);
                                    ArrayList arrayList3 = new ArrayList();
                                    Iterator it6 = listSubcompose3.iterator();
                                    while (it6.hasNext()) {
                                        int i16 = i12;
                                        Placeable placeableMo610measureBRTryo0 = ((Measurable) it6.next()).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU((-windowInsets5.getLeft(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection())) - windowInsets5.getRight(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection()), -windowInsets5.getBottom(subcomposeMeasureScope2), j));
                                        if (placeableMo610measureBRTryo0.height == 0 || placeableMo610measureBRTryo0.width == 0) {
                                            placeableMo610measureBRTryo0 = null;
                                        }
                                        if (placeableMo610measureBRTryo0 != null) {
                                            arrayList3.add(placeableMo610measureBRTryo0);
                                        }
                                        i12 = i16;
                                    }
                                    int i17 = i12;
                                    boolean zIsEmpty = arrayList3.isEmpty();
                                    float f = ScaffoldKt.FabSpacing;
                                    int i18 = iM823getMaxWidthimpl;
                                    if (zIsEmpty) {
                                        i6 = i18;
                                        seslFabPlacement = null;
                                    } else {
                                        Iterator it7 = arrayList3.iterator();
                                        if (it7.hasNext()) {
                                            next5 = it7.next();
                                            if (it7.hasNext()) {
                                                int i19 = ((Placeable) next5).width;
                                                while (true) {
                                                    Object next10 = it7.next();
                                                    Iterator it8 = it7;
                                                    int i20 = ((Placeable) next10).width;
                                                    if (i19 < i20) {
                                                        i19 = i20;
                                                        next5 = next10;
                                                    }
                                                    if (!it8.hasNext()) {
                                                        break;
                                                    }
                                                    it7 = it8;
                                                }
                                            }
                                        } else {
                                            next5 = null;
                                        }
                                        next5.getClass();
                                        int i21 = ((Placeable) next5).width;
                                        Iterator it9 = arrayList3.iterator();
                                        if (it9.hasNext()) {
                                            next6 = it9.next();
                                            if (it9.hasNext()) {
                                                int i22 = ((Placeable) next6).height;
                                                while (true) {
                                                    Object next11 = it9.next();
                                                    i6 = i18;
                                                    int i23 = ((Placeable) next11).height;
                                                    if (i22 < i23) {
                                                        i22 = i23;
                                                        next6 = next11;
                                                    }
                                                    if (!it9.hasNext()) {
                                                        break;
                                                    }
                                                    i18 = i6;
                                                }
                                            } else {
                                                i6 = i18;
                                            }
                                        } else {
                                            i6 = i18;
                                            next6 = null;
                                        }
                                        next6.getClass();
                                        int i24 = ((Placeable) next6).height;
                                        SeslFabPosition.Companion.getClass();
                                        seslFabPlacement = new SeslFabPlacement(i5 == SeslFabPosition.End ? subcomposeMeasureScope2.getLayoutDirection() == LayoutDirection.Ltr ? (i6 - subcomposeMeasureScope2.mo52roundToPx0680j_4(f)) - i21 : subcomposeMeasureScope2.mo52roundToPx0680j_4(f) : (i6 - i21) / 2, i21, i24);
                                    }
                                    SeslScaffoldLayoutContent seslScaffoldLayoutContent2 = SeslScaffoldLayoutContent.BottomBar;
                                    final ComposableLambdaImpl composableLambdaImpl13 = composableLambdaImpl12;
                                    int i25 = i15;
                                    ArrayList arrayList4 = arrayList3;
                                    List listSubcompose4 = subcomposeMeasureScope2.subcompose(seslScaffoldLayoutContent2, new ComposableLambdaImpl(866376125, true, new Function2() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$SeslScaffoldLayout$1$1$1$bottomBarPlaceables$1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj4, Object obj5) {
                                            Composer composer2 = (Composer) obj4;
                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldLayout.<anonymous>.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:201)");
                                                    }
                                                    CompositionLocalKt.CompositionLocalProvider(ScaffoldKt.LocalSeslFabPlacement.defaultProvidedValue$runtime_release(seslFabPlacement), composableLambdaImpl13, composer2, 8);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }));
                                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSubcompose4, 10));
                                    Iterator it10 = listSubcompose4.iterator();
                                    while (it10.hasNext()) {
                                        arrayList5.add(((Measurable) it10.next()).mo610measureBRTryo0(j));
                                    }
                                    Iterator it11 = arrayList5.iterator();
                                    if (it11.hasNext()) {
                                        next4 = it11.next();
                                        if (it11.hasNext()) {
                                            int i26 = ((Placeable) next4).height;
                                            do {
                                                Object next12 = it11.next();
                                                Object obj4 = next4;
                                                int i27 = ((Placeable) next12).height;
                                                if (i26 < i27) {
                                                    i26 = i27;
                                                    next4 = next12;
                                                } else {
                                                    next4 = obj4;
                                                }
                                            } while (it11.hasNext());
                                        }
                                    } else {
                                        next4 = null;
                                    }
                                    Placeable placeable4 = (Placeable) next4;
                                    Integer numValueOf2 = placeable4 != null ? Integer.valueOf(placeable4.height) : null;
                                    if (seslFabPlacement != null) {
                                        int i28 = seslFabPlacement.height;
                                        if (numValueOf2 == null) {
                                            iIntValue2 = subcomposeMeasureScope2.mo52roundToPx0680j_4(f) + i28;
                                            iMo52roundToPx0680j_4 = windowInsets5.getBottom(subcomposeMeasureScope2);
                                        } else {
                                            iIntValue2 = numValueOf2.intValue() + i28;
                                            iMo52roundToPx0680j_4 = subcomposeMeasureScope2.mo52roundToPx0680j_4(f);
                                        }
                                        numValueOf = Integer.valueOf(iMo52roundToPx0680j_4 + iIntValue2);
                                    } else {
                                        numValueOf = null;
                                    }
                                    if (i17 != 0) {
                                        iIntValue = i17 + (numValueOf != null ? numValueOf.intValue() : numValueOf2 != null ? numValueOf2.intValue() : windowInsets5.getBottom(subcomposeMeasureScope2));
                                    } else {
                                        iIntValue = 0;
                                    }
                                    PaddingValues paddingValuesAsPaddingValues = WindowInsetsKt.asPaddingValues(windowInsets5, subcomposeMeasureScope2);
                                    Integer num = numValueOf2;
                                    float fMo113calculateTopPaddingD9Ej5fM = arrayList.isEmpty() ? paddingValuesAsPaddingValues.mo113calculateTopPaddingD9Ej5fM() : subcomposeMeasureScope2.mo54toDpu2uoSUM(i9 + 0.0f);
                                    Dp.Companion companion = Dp.Companion;
                                    int i29 = iIntValue;
                                    final PaddingValuesImpl paddingValuesImplM123PaddingValuesa9UjIt4 = PaddingKt.m123PaddingValuesa9UjIt4(PaddingKt.calculateStartPadding(paddingValuesAsPaddingValues, subcomposeMeasureScope2.getLayoutDirection()), Math.max(fMo113calculateTopPaddingD9Ej5fM, 0), PaddingKt.calculateEndPadding(paddingValuesAsPaddingValues, subcomposeMeasureScope2.getLayoutDirection()), (arrayList5.isEmpty() || num == null) ? paddingValuesAsPaddingValues.mo110calculateBottomPaddingD9Ej5fM() : subcomposeMeasureScope2.mo55toDpu2uoSUM(num.intValue()));
                                    SeslScaffoldLayoutContent seslScaffoldLayoutContent3 = SeslScaffoldLayoutContent.MainContent;
                                    final ComposableLambdaImpl composableLambdaImpl14 = composableLambdaImpl9;
                                    Integer num2 = numValueOf;
                                    List listSubcompose5 = subcomposeMeasureScope2.subcompose(seslScaffoldLayoutContent3, new ComposableLambdaImpl(13021582, true, new Function2() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$SeslScaffoldLayout$1$1$1$bodyContentPlaceables$1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6) {
                                            Composer composer2 = (Composer) obj5;
                                            if ((((Number) obj6).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldLayout.<anonymous>.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:244)");
                                                    }
                                                    composableLambdaImpl14.invoke(paddingValuesImplM123PaddingValuesa9UjIt4, composer2, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }));
                                    ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSubcompose5, 10));
                                    Iterator it12 = listSubcompose5.iterator();
                                    while (it12.hasNext()) {
                                        arrayList6.add(((Measurable) it12.next()).mo610measureBRTryo0(j));
                                    }
                                    Iterator it13 = arrayList6.iterator();
                                    if (!it13.hasNext()) {
                                        throw new NoSuchElementException();
                                    }
                                    Object next13 = it13.next();
                                    if (it13.hasNext()) {
                                        int i30 = ((Placeable) next13).height;
                                        while (true) {
                                            Object next14 = it13.next();
                                            Iterator it14 = it13;
                                            int i31 = ((Placeable) next14).height;
                                            if (i30 < i31) {
                                                i30 = i31;
                                                next13 = next14;
                                            }
                                            if (!it14.hasNext()) {
                                                break;
                                            }
                                            it13 = it14;
                                        }
                                    }
                                    int i32 = ((Placeable) next13).height;
                                    int i33 = iM822getMaxHeightimpl;
                                    final float f2 = i33 - i32;
                                    if (f2 < 0.0f) {
                                        f2 = 0.0f;
                                    }
                                    SeslScaffoldLayoutContent seslScaffoldLayoutContent4 = SeslScaffoldLayoutContent.Background;
                                    final Function4 function43 = function42;
                                    List listSubcompose6 = subcomposeMeasureScope2.subcompose(seslScaffoldLayoutContent4, new ComposableLambdaImpl(2018368447, true, new Function2() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$SeslScaffoldLayout$1$1$1$1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6) {
                                            Composer composer2 = (Composer) obj5;
                                            if ((((Number) obj6).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslScaffoldLayout.<anonymous>.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:251)");
                                                    }
                                                    SeslScaffoldTemplate$BackgroundScope.Companion.getClass();
                                                    SeslScaffoldTemplate$BackgroundScope seslScaffoldTemplate$BackgroundScope = SeslScaffoldTemplate$BackgroundScope.instance;
                                                    PaddingValues paddingValues = paddingValuesImplM123PaddingValuesa9UjIt4;
                                                    float fMo113calculateTopPaddingD9Ej5fM2 = paddingValues.mo113calculateTopPaddingD9Ej5fM();
                                                    SubcomposeMeasureScope subcomposeMeasureScope3 = subcomposeMeasureScope2;
                                                    float fCalculateStartPadding = PaddingKt.calculateStartPadding(paddingValues, subcomposeMeasureScope3.getLayoutDirection());
                                                    float fCalculateEndPadding = PaddingKt.calculateEndPadding(paddingValues, subcomposeMeasureScope3.getLayoutDirection());
                                                    float fMo54toDpu2uoSUM = subcomposeMeasureScope3.mo54toDpu2uoSUM(f2) + paddingValues.mo110calculateBottomPaddingD9Ej5fM();
                                                    Dp.Companion companion2 = Dp.Companion;
                                                    function43.invoke(seslScaffoldTemplate$BackgroundScope, PaddingKt.m123PaddingValuesa9UjIt4(fCalculateStartPadding, fMo113calculateTopPaddingD9Ej5fM2, fCalculateEndPadding, fMo54toDpu2uoSUM), composer2, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }));
                                    ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSubcompose6, 10));
                                    Iterator it15 = listSubcompose6.iterator();
                                    while (it15.hasNext()) {
                                        arrayList7.add(((Measurable) it15.next()).mo610measureBRTryo0(j));
                                    }
                                    int size = arrayList7.size();
                                    int i34 = 0;
                                    while (i34 < size) {
                                        Object obj5 = arrayList7.get(i34);
                                        i34++;
                                        placementScope.place((Placeable) obj5, 0, 0, 0.0f);
                                    }
                                    int size2 = arrayList6.size();
                                    int i35 = 0;
                                    while (i35 < size2) {
                                        Object obj6 = arrayList6.get(i35);
                                        i35++;
                                        placementScope.place((Placeable) obj6, 0, 0, 0.0f);
                                    }
                                    int size3 = arrayList.size();
                                    int i36 = 0;
                                    while (i36 < size3) {
                                        Object obj7 = arrayList.get(i36);
                                        i36++;
                                        placementScope.place((Placeable) obj7, 0, (int) 0.0f, 0.0f);
                                    }
                                    int size4 = arrayList2.size();
                                    int i37 = 0;
                                    while (i37 < size4) {
                                        Object obj8 = arrayList2.get(i37);
                                        i37++;
                                        placementScope.place((Placeable) obj8, windowInsets5.getLeft(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection()) + ((i6 - i25) / 2), i33 - i29, 0.0f);
                                    }
                                    int size5 = arrayList5.size();
                                    int i38 = 0;
                                    while (i38 < size5) {
                                        Object obj9 = arrayList5.get(i38);
                                        i38++;
                                        Placeable placeable5 = (Placeable) obj9;
                                        if (num != null) {
                                            placementScope.place(placeable5, 0, i33 - num.intValue(), 0.0f);
                                        }
                                    }
                                    if (seslFabPlacement != null) {
                                        int size6 = arrayList4.size();
                                        int i39 = 0;
                                        while (i39 < size6) {
                                            ArrayList arrayList8 = arrayList4;
                                            Object obj10 = arrayList8.get(i39);
                                            i39++;
                                            num2.getClass();
                                            placementScope.place((Placeable) obj10, seslFabPlacement.left, i33 - num2.intValue(), 0.0f);
                                            arrayList4 = arrayList8;
                                        }
                                        Unit unit = Unit.INSTANCE;
                                    }
                                    return Unit.INSTANCE;
                                }
                            });
                        }
                    };
                    composerImpl.updateRememberedValue(function22);
                    objRememberedValue = function22;
                } else {
                    i4 = 1;
                }
                composerImpl.end(false);
                SubcomposeLayoutKt.SubcomposeLayout(null, (Function2) objRememberedValue, composerImpl, 0, i4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.ScaffoldKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    ComposableLambdaImpl composableLambdaImpl9 = composableLambdaImpl;
                    WindowInsets windowInsets4 = windowInsets;
                    ComposableLambdaImpl composableLambdaImpl10 = composableLambdaImpl4;
                    Function4 function42 = function4;
                    ScaffoldKt.m3343SeslScaffoldLayoutvZ1zQFI(i, function2, composableLambdaImpl9, composableLambdaImpl2, composableLambdaImpl3, windowInsets4, composableLambdaImpl10, function42, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
