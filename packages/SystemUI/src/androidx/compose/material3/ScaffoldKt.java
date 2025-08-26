package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.material3.internal.MutableWindowInsets;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeLayoutKt;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ScaffoldKt {
    public static final float FabSpacing;

    static {
        Dp.Companion companion = Dp.Companion;
        FabSpacing = 16;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:200:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x010e  */
    /* renamed from: Scaffold-TvnljyQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m282ScaffoldTvnljyQ(Modifier modifier, Function2 function2, Function2 function22, Function2 function23, Function2 function24, int i, long j, long j2, WindowInsets windowInsets, final Function3 function3, Composer composer, final int i2, final int i3) {
        int i4;
        Function2 function25;
        int i5;
        Function2 function26;
        int i6;
        Function2 function27;
        int i7;
        Function2 function28;
        int i8;
        int i9;
        int i10;
        Function2 function29;
        Function2 function210;
        Function2 function211;
        Function2 function212;
        int i11;
        long j3;
        long jM259contentColorForek8zF_U;
        Modifier modifier2;
        final WindowInsets contentWindowInsets;
        int i12;
        int i13;
        final Function2 function213;
        boolean z;
        final long j4;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final WindowInsets windowInsets2;
        final Function2 function214;
        final Function2 function215;
        final int i14;
        final long j5;
        final Function2 function216;
        final Function2 function217;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1219521777);
        int i15 = i3 & 1;
        if (i15 != 0) {
            i4 = i2 | 6;
        } else if ((i2 & 6) == 0) {
            i4 = (composerImpl2.changed(modifier) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        int i16 = i3 & 2;
        if (i16 != 0) {
            i4 |= 48;
        } else {
            if ((i2 & 48) == 0) {
                function25 = function2;
                i4 |= composerImpl2.changedInstance(function25) ? 32 : 16;
            }
            i5 = i3 & 4;
            if (i5 == 0) {
                i4 |= 384;
            } else {
                if ((i2 & 384) == 0) {
                    function26 = function22;
                    i4 |= composerImpl2.changedInstance(function26) ? 256 : 128;
                }
                i6 = i3 & 8;
                if (i6 != 0) {
                    i4 |= 3072;
                } else {
                    if ((i2 & 3072) == 0) {
                        function27 = function23;
                        i4 |= composerImpl2.changedInstance(function27) ? 2048 : 1024;
                    }
                    i7 = i3 & 16;
                    if (i7 == 0) {
                        i4 |= 24576;
                    } else {
                        if ((i2 & 24576) == 0) {
                            function28 = function24;
                            i4 |= composerImpl2.changedInstance(function28) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i8 = i3 & 32;
                        if (i8 != 0) {
                            i4 |= 196608;
                        } else {
                            if ((196608 & i2) == 0) {
                                i9 = i;
                                i4 |= composerImpl2.changed(i9) ? 131072 : 65536;
                            }
                            if ((i2 & 1572864) != 0) {
                                i10 = i15;
                                i4 |= ((i3 & 64) == 0 && composerImpl2.changed(j)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            } else {
                                i10 = i15;
                            }
                            if ((i2 & 12582912) == 0) {
                                i4 |= ((i3 & 128) == 0 && composerImpl2.changed(j2)) ? 8388608 : 4194304;
                            }
                            if ((i2 & 100663296) == 0) {
                                i4 |= ((i3 & 256) == 0 && composerImpl2.changed(windowInsets)) ? 67108864 : 33554432;
                            }
                            if ((i3 & 512) != 0) {
                                if ((i2 & 805306368) == 0) {
                                    i4 |= composerImpl2.changedInstance(function3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                }
                                if ((i4 & 306783379) == 306783378 && composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    modifier3 = modifier;
                                    function214 = function25;
                                    function215 = function26;
                                    function216 = function27;
                                    composerImpl = composerImpl2;
                                    function217 = function28;
                                    i14 = i9;
                                    j4 = j;
                                    j5 = j2;
                                    windowInsets2 = windowInsets;
                                } else {
                                    composerImpl2.startDefaults();
                                    if ((i2 & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                        Modifier modifier4 = i10 == 0 ? Modifier.Companion : modifier;
                                        if (i16 == 0) {
                                            ComposableSingletons$ScaffoldKt.INSTANCE.getClass();
                                            function29 = ComposableSingletons$ScaffoldKt.f10lambda1;
                                        } else {
                                            function29 = function25;
                                        }
                                        if (i5 == 0) {
                                            ComposableSingletons$ScaffoldKt.INSTANCE.getClass();
                                            function210 = ComposableSingletons$ScaffoldKt.f11lambda2;
                                        } else {
                                            function210 = function26;
                                        }
                                        if (i6 == 0) {
                                            ComposableSingletons$ScaffoldKt.INSTANCE.getClass();
                                            function211 = ComposableSingletons$ScaffoldKt.f12lambda3;
                                        } else {
                                            function211 = function27;
                                        }
                                        if (i7 == 0) {
                                            ComposableSingletons$ScaffoldKt.INSTANCE.getClass();
                                            function212 = ComposableSingletons$ScaffoldKt.f13lambda4;
                                        } else {
                                            function212 = function28;
                                        }
                                        if (i8 == 0) {
                                            FabPosition.Companion.getClass();
                                            i11 = FabPosition.End;
                                        } else {
                                            i11 = i9;
                                        }
                                        if ((i3 & 64) == 0) {
                                            MaterialTheme.INSTANCE.getClass();
                                            j3 = MaterialTheme.getColorScheme(composerImpl2).background;
                                            i4 &= -3670017;
                                        } else {
                                            j3 = j;
                                        }
                                        if ((i3 & 128) == 0) {
                                            jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j3, composerImpl2);
                                            i4 &= -29360129;
                                        } else {
                                            jM259contentColorForek8zF_U = j2;
                                        }
                                        if ((i3 & 256) == 0) {
                                            ScaffoldDefaults.INSTANCE.getClass();
                                            i4 &= -234881025;
                                            modifier2 = modifier4;
                                            contentWindowInsets = ScaffoldDefaults.getContentWindowInsets(composerImpl2);
                                        } else {
                                            modifier2 = modifier4;
                                            contentWindowInsets = windowInsets;
                                        }
                                    } else {
                                        composerImpl2.skipToGroupEnd();
                                        if ((i3 & 64) != 0) {
                                            i4 &= -3670017;
                                        }
                                        if ((i3 & 128) != 0) {
                                            i4 &= -29360129;
                                        }
                                        if ((i3 & 256) != 0) {
                                            i4 &= -234881025;
                                        }
                                        contentWindowInsets = windowInsets;
                                        function29 = function25;
                                        function210 = function26;
                                        function211 = function27;
                                        function212 = function28;
                                        i11 = i9;
                                        modifier2 = modifier;
                                        j3 = j;
                                        jM259contentColorForek8zF_U = j2;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.Scaffold (Scaffold.kt:94)");
                                    }
                                    i12 = (234881024 & i4) ^ 100663296;
                                    i13 = i4;
                                    if (i12 > 67108864 || !composerImpl2.changed(contentWindowInsets)) {
                                        function213 = function29;
                                        if ((i13 & 100663296) != 67108864) {
                                            z = false;
                                        }
                                        Object objRememberedValue = composerImpl2.rememberedValue();
                                        Composer.Companion companion = Composer.Companion;
                                        if (!z) {
                                            companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new MutableWindowInsets(contentWindowInsets);
                                                composerImpl2.updateRememberedValue(objRememberedValue);
                                            }
                                            final MutableWindowInsets mutableWindowInsets = (MutableWindowInsets) objRememberedValue;
                                            boolean zChanged = composerImpl2.changed(mutableWindowInsets) | ((i12 > 67108864 && composerImpl2.changed(contentWindowInsets)) || (i13 & 100663296) == 67108864);
                                            Object objRememberedValue2 = composerImpl2.rememberedValue();
                                            if (!zChanged) {
                                                companion.getClass();
                                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                                    objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.ScaffoldKt$Scaffold$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj) {
                                                            MutableWindowInsets mutableWindowInsets2 = mutableWindowInsets;
                                                            ((SnapshotMutableStateImpl) mutableWindowInsets2.insets$delegate).setValue(WindowInsetsKt.exclude(contentWindowInsets, (WindowInsets) obj));
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    composerImpl2.updateRememberedValue(objRememberedValue2);
                                                }
                                                Modifier modifierOnConsumedWindowInsetsChanged = WindowInsetsPaddingKt.onConsumedWindowInsetsChanged(modifier2, (Function1) objRememberedValue2);
                                                final Function2 function218 = function210;
                                                final Function2 function219 = function211;
                                                final Function2 function220 = function212;
                                                final int i17 = i11;
                                                Function2 function221 = function213;
                                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1979205334, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$Scaffold$2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(2);
                                                    }

                                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                                                    ComposerKt.traceEventStart("androidx.compose.material3.Scaffold.<anonymous> (Scaffold.kt:105)");
                                                                }
                                                                ScaffoldKt.m283access$ScaffoldLayoutFMILGgc(i17, function213, function3, function219, function220, mutableWindowInsets, function218, composer2, 0);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }, composerImpl2);
                                                int i18 = i13 >> 12;
                                                long j6 = jM259contentColorForek8zF_U;
                                                long j7 = j3;
                                                WindowInsets windowInsets3 = contentWindowInsets;
                                                SurfaceKt.m304SurfaceT9BRK9s(modifierOnConsumedWindowInsetsChanged, null, j7, j6, 0.0f, 0.0f, null, composableLambdaImplRememberComposableLambda, composerImpl2, (i18 & 7168) | (i18 & 896) | 12582912, 114);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                j4 = j7;
                                                composerImpl = composerImpl2;
                                                modifier3 = modifier2;
                                                windowInsets2 = windowInsets3;
                                                function214 = function221;
                                                function215 = function218;
                                                i14 = i17;
                                                j5 = j6;
                                                function216 = function219;
                                                function217 = function220;
                                            }
                                        }
                                    } else {
                                        function213 = function29;
                                    }
                                    z = true;
                                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                                    Composer.Companion companion2 = Composer.Companion;
                                    if (!z) {
                                    }
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ScaffoldKt$Scaffold$3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ((Number) obj2).intValue();
                                            ScaffoldKt.m282ScaffoldTvnljyQ(modifier3, function214, function215, function216, function217, i14, j4, j5, windowInsets2, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), i3);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i4 |= 805306368;
                            if ((i4 & 306783379) == 306783378) {
                                composerImpl2.startDefaults();
                                if ((i2 & 1) != 0) {
                                    if (i10 == 0) {
                                    }
                                    if (i16 == 0) {
                                    }
                                    if (i5 == 0) {
                                    }
                                    if (i6 == 0) {
                                    }
                                    if (i7 == 0) {
                                    }
                                    if (i8 == 0) {
                                    }
                                    if ((i3 & 64) == 0) {
                                    }
                                    if ((i3 & 128) == 0) {
                                    }
                                    if ((i3 & 256) == 0) {
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    i12 = (234881024 & i4) ^ 100663296;
                                    i13 = i4;
                                    if (i12 > 67108864) {
                                        function213 = function29;
                                        if ((i13 & 100663296) != 67108864) {
                                            z = true;
                                        }
                                        Object objRememberedValue32 = composerImpl2.rememberedValue();
                                        Composer.Companion companion22 = Composer.Companion;
                                        if (!z) {
                                        }
                                    }
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i9 = i;
                        if ((i2 & 1572864) != 0) {
                        }
                        if ((i2 & 12582912) == 0) {
                        }
                        if ((i2 & 100663296) == 0) {
                        }
                        if ((i3 & 512) != 0) {
                        }
                        if ((i4 & 306783379) == 306783378) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    function28 = function24;
                    i8 = i3 & 32;
                    if (i8 != 0) {
                    }
                    i9 = i;
                    if ((i2 & 1572864) != 0) {
                    }
                    if ((i2 & 12582912) == 0) {
                    }
                    if ((i2 & 100663296) == 0) {
                    }
                    if ((i3 & 512) != 0) {
                    }
                    if ((i4 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function27 = function23;
                i7 = i3 & 16;
                if (i7 == 0) {
                }
                function28 = function24;
                i8 = i3 & 32;
                if (i8 != 0) {
                }
                i9 = i;
                if ((i2 & 1572864) != 0) {
                }
                if ((i2 & 12582912) == 0) {
                }
                if ((i2 & 100663296) == 0) {
                }
                if ((i3 & 512) != 0) {
                }
                if ((i4 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function26 = function22;
            i6 = i3 & 8;
            if (i6 != 0) {
            }
            function27 = function23;
            i7 = i3 & 16;
            if (i7 == 0) {
            }
            function28 = function24;
            i8 = i3 & 32;
            if (i8 != 0) {
            }
            i9 = i;
            if ((i2 & 1572864) != 0) {
            }
            if ((i2 & 12582912) == 0) {
            }
            if ((i2 & 100663296) == 0) {
            }
            if ((i3 & 512) != 0) {
            }
            if ((i4 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function25 = function2;
        i5 = i3 & 4;
        if (i5 == 0) {
        }
        function26 = function22;
        i6 = i3 & 8;
        if (i6 != 0) {
        }
        function27 = function23;
        i7 = i3 & 16;
        if (i7 == 0) {
        }
        function28 = function24;
        i8 = i3 & 32;
        if (i8 != 0) {
        }
        i9 = i;
        if ((i2 & 1572864) != 0) {
        }
        if ((i2 & 12582912) == 0) {
        }
        if ((i2 & 100663296) == 0) {
        }
        if ((i3 & 512) != 0) {
        }
        if ((i4 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* renamed from: access$ScaffoldLayout-FMILGgc, reason: not valid java name */
    public static final void m283access$ScaffoldLayoutFMILGgc(final int i, final Function2 function2, final Function3 function3, final Function2 function22, final Function2 function23, final WindowInsets windowInsets, final Function2 function24, Composer composer, final int i2) {
        int i3;
        Function3 function32;
        WindowInsets windowInsets2;
        Function2 function25;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-975511942);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            function32 = function3;
            i3 |= composerImpl.changedInstance(function32) ? 256 : 128;
        } else {
            function32 = function3;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function22) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= composerImpl.changedInstance(function23) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i2) == 0) {
            windowInsets2 = windowInsets;
            i3 |= composerImpl.changed(windowInsets2) ? 131072 : 65536;
        } else {
            windowInsets2 = windowInsets;
        }
        if ((1572864 & i2) == 0) {
            function25 = function24;
            i3 |= composerImpl.changedInstance(function25) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            function25 = function24;
        }
        if ((i3 & 599187) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout (Scaffold.kt:138)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new ScaffoldKt$ScaffoldLayout$contentPadding$1$1();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$1 = (ScaffoldKt$ScaffoldLayout$contentPadding$1$1) objRememberedValue;
            boolean z = ((i3 & 896) == 256) | ((i3 & 112) == 32) | ((i3 & 458752) == 131072) | ((i3 & 7168) == 2048) | ((57344 & i3) == 16384) | ((i3 & 14) == 4) | ((3670016 & i3) == 1048576);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (z || objRememberedValue2 == composer$Companion$Empty$1) {
                final Function3 function33 = function32;
                final WindowInsets windowInsets3 = windowInsets2;
                final Function2 function26 = function25;
                Function2 function27 = new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:38:0x0141  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        int iMo52roundToPx0680j_4;
                        int iMo52roundToPx0680j_42;
                        final FabPlacement fabPlacement;
                        Integer numValueOf;
                        int i4;
                        int iIntValue;
                        final SubcomposeMeasureScope subcomposeMeasureScope = (SubcomposeMeasureScope) obj;
                        long j = ((Constraints) obj2).value;
                        final int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                        final int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
                        long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
                        int left = windowInsets3.getLeft(subcomposeMeasureScope, subcomposeMeasureScope.getLayoutDirection());
                        int right = windowInsets3.getRight(subcomposeMeasureScope, subcomposeMeasureScope.getLayoutDirection());
                        int bottom = windowInsets3.getBottom(subcomposeMeasureScope);
                        ScaffoldLayoutContent scaffoldLayoutContent = ScaffoldLayoutContent.TopBar;
                        final Function2 function28 = function2;
                        final Placeable placeableMo610measureBRTryo0 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent, new ComposableLambdaImpl(821838737, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$topBarPlaceable$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:171)");
                                        }
                                        Function2 function29 = function28;
                                        Modifier.Companion companion = Modifier.Companion;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                        Function2 function210 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function210);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function29.invoke(composer2, 0);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo610measureBRTryo0(jM816copyZbe2FdA$default);
                        ScaffoldLayoutContent scaffoldLayoutContent2 = ScaffoldLayoutContent.Snackbar;
                        final Function2 function29 = function22;
                        int i5 = (-left) - right;
                        int i6 = -bottom;
                        final Placeable placeableMo610measureBRTryo02 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent2, new ComposableLambdaImpl(83362666, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$snackbarPlaceable$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:176)");
                                        }
                                        Function2 function210 = function29;
                                        Modifier.Companion companion = Modifier.Companion;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                        Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function211);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function210.invoke(composer2, 0);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(i5, i6, jM816copyZbe2FdA$default));
                        ScaffoldLayoutContent scaffoldLayoutContent3 = ScaffoldLayoutContent.Fab;
                        final Function2 function210 = function23;
                        final Placeable placeableMo610measureBRTryo03 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent3, new ComposableLambdaImpl(1546204780, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$fabPlaceable$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:181)");
                                        }
                                        Function2 function211 = function210;
                                        Modifier.Companion companion = Modifier.Companion;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                        Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function212);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function211.invoke(composer2, 0);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(i5, i6, jM816copyZbe2FdA$default));
                        int i7 = placeableMo610measureBRTryo03.width;
                        if (i7 == 0 && placeableMo610measureBRTryo03.height == 0) {
                            fabPlacement = null;
                        } else {
                            int i8 = placeableMo610measureBRTryo03.height;
                            int i9 = i;
                            FabPosition.Companion.getClass();
                            if (i9 != 0) {
                                if (i9 != FabPosition.End && i9 != FabPosition.EndOverlay) {
                                    iMo52roundToPx0680j_4 = (iM823getMaxWidthimpl - i7) / 2;
                                } else if (subcomposeMeasureScope.getLayoutDirection() == LayoutDirection.Ltr) {
                                    iMo52roundToPx0680j_42 = subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                    iMo52roundToPx0680j_4 = (iM823getMaxWidthimpl - iMo52roundToPx0680j_42) - i7;
                                } else {
                                    iMo52roundToPx0680j_4 = subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                }
                                fabPlacement = new FabPlacement(iMo52roundToPx0680j_4, i7, i8);
                            } else if (subcomposeMeasureScope.getLayoutDirection() == LayoutDirection.Ltr) {
                                iMo52roundToPx0680j_4 = subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                fabPlacement = new FabPlacement(iMo52roundToPx0680j_4, i7, i8);
                            } else {
                                iMo52roundToPx0680j_42 = subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing);
                                iMo52roundToPx0680j_4 = (iM823getMaxWidthimpl - iMo52roundToPx0680j_42) - i7;
                                fabPlacement = new FabPlacement(iMo52roundToPx0680j_4, i7, i8);
                            }
                        }
                        ScaffoldLayoutContent scaffoldLayoutContent4 = ScaffoldLayoutContent.BottomBar;
                        final Function2 function211 = function26;
                        final Placeable placeableMo610measureBRTryo04 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent4, new ComposableLambdaImpl(1868541227, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$bottomBarPlaceable$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:217)");
                                        }
                                        Function2 function212 = function211;
                                        Modifier.Companion companion = Modifier.Companion;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                        Function2 function213 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function213);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function212.invoke(composer2, 0);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo610measureBRTryo0(jM816copyZbe2FdA$default);
                        boolean z2 = placeableMo610measureBRTryo04.width == 0 && placeableMo610measureBRTryo04.height == 0;
                        if (fabPlacement != null) {
                            int i10 = i;
                            WindowInsets windowInsets4 = windowInsets3;
                            int i11 = fabPlacement.height;
                            if (!z2) {
                                FabPosition.Companion.getClass();
                                int iMo52roundToPx0680j_43 = i10 == FabPosition.EndOverlay ? subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing) + i11 + windowInsets4.getBottom(subcomposeMeasureScope) : subcomposeMeasureScope.mo52roundToPx0680j_4(ScaffoldKt.FabSpacing) + placeableMo610measureBRTryo04.height + i11;
                                numValueOf = Integer.valueOf(iMo52roundToPx0680j_43);
                            }
                        } else {
                            numValueOf = null;
                        }
                        int i12 = placeableMo610measureBRTryo02.height;
                        if (i12 != 0) {
                            if (numValueOf != null) {
                                iIntValue = numValueOf.intValue();
                            } else {
                                Integer numValueOf2 = Integer.valueOf(placeableMo610measureBRTryo04.height);
                                if (z2) {
                                    numValueOf2 = null;
                                }
                                iIntValue = numValueOf2 != null ? numValueOf2.intValue() : windowInsets3.getBottom(subcomposeMeasureScope);
                            }
                            i4 = iIntValue + i12;
                        } else {
                            i4 = 0;
                        }
                        PaddingValues paddingValuesAsPaddingValues = WindowInsetsKt.asPaddingValues(windowInsets3, subcomposeMeasureScope);
                        ((SnapshotMutableStateImpl) scaffoldKt$ScaffoldLayout$contentPadding$1$1.paddingHolder$delegate).setValue(PaddingKt.m123PaddingValuesa9UjIt4(PaddingKt.calculateStartPadding(paddingValuesAsPaddingValues, subcomposeMeasureScope.getLayoutDirection()), (placeableMo610measureBRTryo0.width == 0 && placeableMo610measureBRTryo0.height == 0) ? paddingValuesAsPaddingValues.mo113calculateTopPaddingD9Ej5fM() : subcomposeMeasureScope.mo55toDpu2uoSUM(placeableMo610measureBRTryo0.height), PaddingKt.calculateEndPadding(paddingValuesAsPaddingValues, subcomposeMeasureScope.getLayoutDirection()), z2 ? paddingValuesAsPaddingValues.mo110calculateBottomPaddingD9Ej5fM() : subcomposeMeasureScope.mo55toDpu2uoSUM(placeableMo610measureBRTryo04.height)));
                        ScaffoldLayoutContent scaffoldLayoutContent5 = ScaffoldLayoutContent.MainContent;
                        final Function3 function34 = function33;
                        final ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$12 = scaffoldKt$ScaffoldLayout$contentPadding$1$1;
                        final Placeable placeableMo610measureBRTryo05 = ((Measurable) CollectionsKt___CollectionsKt.first(subcomposeMeasureScope.subcompose(scaffoldLayoutContent5, new ComposableLambdaImpl(906691836, true, new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1$bodyContentPlaceable$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (Scaffold.kt:268)");
                                        }
                                        Function3 function35 = function34;
                                        ScaffoldKt$ScaffoldLayout$contentPadding$1$1 scaffoldKt$ScaffoldLayout$contentPadding$1$13 = scaffoldKt$ScaffoldLayout$contentPadding$1$12;
                                        Modifier.Companion companion = Modifier.Companion;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
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
                                        Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function212);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function35.invoke(scaffoldKt$ScaffoldLayout$contentPadding$1$13, composer2, 6);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        })))).mo610measureBRTryo0(jM816copyZbe2FdA$default);
                        final WindowInsets windowInsets5 = windowInsets3;
                        final Integer num = numValueOf;
                        final int i13 = i4;
                        return subcomposeMeasureScope.layout$1(iM823getMaxWidthimpl, iM822getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$1$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj3) {
                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                placementScope.place(placeableMo610measureBRTryo05, 0, 0, 0.0f);
                                placementScope.place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                Placeable placeable = placeableMo610measureBRTryo02;
                                int i14 = (iM823getMaxWidthimpl - placeable.width) / 2;
                                WindowInsets windowInsets6 = windowInsets5;
                                SubcomposeMeasureScope subcomposeMeasureScope2 = subcomposeMeasureScope;
                                placementScope.place(placeable, windowInsets6.getLeft(subcomposeMeasureScope2, subcomposeMeasureScope2.getLayoutDirection()) + i14, iM822getMaxHeightimpl - i13, 0.0f);
                                Placeable placeable2 = placeableMo610measureBRTryo04;
                                placementScope.place(placeable2, 0, iM822getMaxHeightimpl - placeable2.height, 0.0f);
                                FabPlacement fabPlacement2 = fabPlacement;
                                if (fabPlacement2 != null) {
                                    Placeable placeable3 = placeableMo610measureBRTryo03;
                                    int i15 = iM822getMaxHeightimpl;
                                    Integer num2 = num;
                                    num2.getClass();
                                    placementScope.place(placeable3, fabPlacement2.left, i15 - num2.intValue(), 0.0f);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                };
                composerImpl.updateRememberedValue(function27);
                objRememberedValue2 = function27;
            }
            SubcomposeLayoutKt.SubcomposeLayout(null, (Function2) objRememberedValue2, composerImpl, 0, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ScaffoldKt$ScaffoldLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ScaffoldKt.m283access$ScaffoldLayoutFMILGgc(i, function2, function3, function22, function23, windowInsets, function24, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
