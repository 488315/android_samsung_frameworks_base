package androidx.navigation.compose;

import androidx.activity.compose.PredictiveBackHandlerKt;
import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.ContentTransform;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.SizeTransform;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.compose.runtime.saveable.SaveableStateHolderKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController$$ExternalSyntheticLambda0;
import androidx.navigation.NavControllerViewModel;
import androidx.navigation.NavControllerViewModel$Companion$FACTORY$1;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigator;
import androidx.navigation.compose.ComposeNavGraphNavigator;
import androidx.navigation.compose.ComposeNavigator;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class NavHostKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:193:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void NavHost(final NavHostController navHostController, final String str, Modifier modifier, Alignment alignment, String str2, Function1 function1, Function1 function12, Function1 function13, Function1 function14, Function1 function15, final Function1 function16, Composer composer, final int i, final int i2, final int i3) throws Throwable {
        int i4;
        Modifier modifier2;
        int i5;
        Alignment alignment2;
        int i6;
        String str3;
        int i7;
        Function1 function17;
        int i8;
        Function1 function18;
        int i9;
        int i10;
        int i11;
        int i12;
        Function1 function19;
        Function1 function110;
        Function1 function111;
        Function1 function112;
        Function1 function113;
        Modifier modifier3;
        Function1 function114;
        String str4;
        Alignment alignment3;
        int i13;
        Function1 function115;
        boolean z;
        final Function1 function116;
        ComposerImpl composerImpl;
        final Function1 function117;
        final Function1 function118;
        final Function1 function119;
        final Function1 function120;
        final String str5;
        final Alignment alignment4;
        final Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1840250294);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl2.changedInstance(navHostController) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i3 & 2) != 0) {
            i4 |= 48;
        } else if ((i & 48) == 0) {
            i4 |= composerImpl2.changed(str) ? 32 : 16;
        }
        int i14 = i3 & 4;
        if (i14 != 0) {
            i4 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i4 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            i5 = i3 & 8;
            if (i5 == 0) {
                i4 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    alignment2 = alignment;
                    i4 |= composerImpl2.changed(alignment2) ? 2048 : 1024;
                }
                i6 = i3 & 16;
                if (i6 != 0) {
                    i4 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        str3 = str2;
                        i4 |= composerImpl2.changed(str3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i7 = i3 & 32;
                    if (i7 == 0) {
                        i4 |= 196608;
                        function17 = function1;
                    } else {
                        function17 = function1;
                        if ((i & 196608) == 0) {
                            i4 |= composerImpl2.changedInstance(function17) ? 131072 : 65536;
                        }
                    }
                    i8 = i3 & 64;
                    if (i8 == 0) {
                        i4 |= 1572864;
                        function18 = function12;
                    } else {
                        function18 = function12;
                        if ((i & 1572864) == 0) {
                            i4 |= composerImpl2.changedInstance(function18) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                    }
                    if ((i & 12582912) == 0) {
                        i4 |= ((i3 & 128) == 0 && composerImpl2.changedInstance(function13)) ? 8388608 : 4194304;
                    }
                    if ((i & 100663296) != 0) {
                        i9 = i4 | (((i3 & 256) == 0 && composerImpl2.changedInstance(function14)) ? 67108864 : 33554432);
                    } else {
                        i9 = i4;
                    }
                    i10 = i3 & 512;
                    if (i10 != 0) {
                        if ((i & 805306368) == 0) {
                            i11 = i10;
                            i9 |= composerImpl2.changedInstance(function15) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        }
                        if ((i3 & 1024) != 0) {
                            i12 = i2 | 6;
                        } else if ((i2 & 6) == 0) {
                            i12 = i2 | (composerImpl2.changedInstance(function16) ? 4 : 2);
                        } else {
                            i12 = i2;
                        }
                        int i15 = i12;
                        if ((i9 & 306783379) == 306783378 && (i15 & 3) == 2 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            Function1 function121 = function17;
                            str5 = str3;
                            modifier4 = modifier2;
                            function119 = function121;
                            function116 = function15;
                            alignment4 = alignment2;
                            composerImpl = composerImpl2;
                            function120 = function18;
                            function117 = function13;
                            function118 = function14;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                if (i14 != 0) {
                                    modifier2 = Modifier.Companion;
                                }
                                if (i5 != 0) {
                                    Alignment.Companion.getClass();
                                    alignment2 = Alignment.Companion.TopStart;
                                }
                                if (i6 != 0) {
                                    str3 = null;
                                }
                                function19 = i7 == 0 ? new Function1() { // from class: androidx.navigation.compose.NavHostKt.NavHost.7
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        return EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 0, null, 6), 2);
                                    }
                                } : function17;
                                function110 = i8 == 0 ? new Function1() { // from class: androidx.navigation.compose.NavHostKt.NavHost.8
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        return EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 0, null, 6), 2);
                                    }
                                } : function18;
                                if ((i3 & 128) == 0) {
                                    i9 &= -29360129;
                                    function111 = function19;
                                } else {
                                    function111 = function13;
                                }
                                if ((i3 & 256) == 0) {
                                    i9 &= -234881025;
                                    function112 = function110;
                                } else {
                                    function112 = function14;
                                }
                                if (i11 == 0) {
                                    Alignment alignment5 = alignment2;
                                    function115 = null;
                                    str4 = str3;
                                    alignment3 = alignment5;
                                    Function1 function122 = function111;
                                    function113 = function112;
                                    modifier3 = modifier2;
                                    function114 = function122;
                                    i13 = i9;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.navigation.compose.NavHost (NavHost.kt:208)");
                                    }
                                    Modifier modifier5 = modifier3;
                                    z = ((i13 & 57344) != 16384) | ((i13 & 112) != 32) | ((i15 & 14) == 4);
                                    Object objRememberedValue = composerImpl2.rememberedValue();
                                    if (z) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            NavGraphBuilder navGraphBuilder = new NavGraphBuilder(navHostController._navigatorProvider, str, str4);
                                            function16.mo781invoke(navGraphBuilder);
                                            objRememberedValue = navGraphBuilder.build();
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        int i16 = i13 & 8078;
                                        int i17 = i13 >> 3;
                                        String str6 = str4;
                                        NavHost(navHostController, (NavGraph) objRememberedValue, modifier5, alignment3, function19, function110, function114, function113, function115, composerImpl2, (i17 & 234881024) | i16 | (i17 & 57344) | (i17 & 458752) | (i17 & 3670016) | (i17 & 29360128), 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        function116 = function115;
                                        composerImpl = composerImpl2;
                                        function117 = function114;
                                        function118 = function113;
                                        function119 = function19;
                                        function120 = function110;
                                        str5 = str6;
                                        alignment4 = alignment3;
                                        modifier4 = modifier5;
                                    }
                                } else {
                                    Function1 function123 = function111;
                                    function113 = function112;
                                    modifier3 = modifier2;
                                    function114 = function123;
                                    str4 = str3;
                                    alignment3 = alignment2;
                                    i13 = i9;
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i3 & 128) != 0) {
                                    i9 &= -29360129;
                                }
                                if ((i3 & 256) != 0) {
                                    i9 &= -234881025;
                                }
                                function113 = function14;
                                str4 = str3;
                                function19 = function17;
                                modifier3 = modifier2;
                                alignment3 = alignment2;
                                function110 = function18;
                                i13 = i9;
                                function114 = function13;
                            }
                            function115 = function15;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            Modifier modifier52 = modifier3;
                            z = ((i13 & 57344) != 16384) | ((i13 & 112) != 32) | ((i15 & 14) == 4);
                            Object objRememberedValue2 = composerImpl2.rememberedValue();
                            if (z) {
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.navigation.compose.NavHostKt.NavHost.10
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) throws Throwable {
                                    ((Number) obj2).intValue();
                                    NavHostKt.NavHost(navHostController, str, modifier4, alignment4, str5, function119, function120, function117, function118, function116, function16, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i9 |= 805306368;
                    i11 = i10;
                    if ((i3 & 1024) != 0) {
                    }
                    int i152 = i12;
                    if ((i9 & 306783379) == 306783378) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i14 != 0) {
                            }
                            if (i5 != 0) {
                            }
                            if (i6 != 0) {
                            }
                            if (i7 == 0) {
                            }
                            if (i8 == 0) {
                            }
                            if ((i3 & 128) == 0) {
                            }
                            if ((i3 & 256) == 0) {
                            }
                            if (i11 == 0) {
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                str3 = str2;
                i7 = i3 & 32;
                if (i7 == 0) {
                }
                i8 = i3 & 64;
                if (i8 == 0) {
                }
                if ((i & 12582912) == 0) {
                }
                if ((i & 100663296) != 0) {
                }
                i10 = i3 & 512;
                if (i10 != 0) {
                }
                i11 = i10;
                if ((i3 & 1024) != 0) {
                }
                int i1522 = i12;
                if ((i9 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            alignment2 = alignment;
            i6 = i3 & 16;
            if (i6 != 0) {
            }
            str3 = str2;
            i7 = i3 & 32;
            if (i7 == 0) {
            }
            i8 = i3 & 64;
            if (i8 == 0) {
            }
            if ((i & 12582912) == 0) {
            }
            if ((i & 100663296) != 0) {
            }
            i10 = i3 & 512;
            if (i10 != 0) {
            }
            i11 = i10;
            if ((i3 & 1024) != 0) {
            }
            int i15222 = i12;
            if ((i9 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i5 = i3 & 8;
        if (i5 == 0) {
        }
        alignment2 = alignment;
        i6 = i3 & 16;
        if (i6 != 0) {
        }
        str3 = str2;
        i7 = i3 & 32;
        if (i7 == 0) {
        }
        i8 = i3 & 64;
        if (i8 == 0) {
        }
        if ((i & 12582912) == 0) {
        }
        if ((i & 100663296) != 0) {
        }
        i10 = i3 & 512;
        if (i10 != 0) {
        }
        i11 = i10;
        if ((i3 & 1024) != 0) {
        }
        int i152222 = i12;
        if ((i9 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x059a  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x05b2  */
    /* JADX WARN: Removed duplicated region for block: B:287:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00f8  */
    /* JADX WARN: Type inference failed for: r13v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v25 */
    /* JADX WARN: Type inference failed for: r13v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void NavHost(final NavHostController navHostController, final NavGraph navGraph, Modifier modifier, Alignment alignment, Function1 function1, Function1 function12, Function1 function13, Function1 function14, Function1 function15, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        int i4;
        Modifier modifier2;
        int i5;
        Alignment alignment2;
        int i6;
        Function1 function16;
        int i7;
        final Function1 function17;
        Function1 function18;
        Function1 function19;
        int i8;
        int i9;
        Modifier modifier3;
        final Alignment alignment3;
        final Function1 function110;
        final Function1 function111;
        int i10;
        final Function1 function112;
        final Function1 function113;
        ViewModelStoreOwner current;
        MutableState mutableState;
        final Modifier modifier4;
        Function1 function114;
        Function1 function115;
        Function1 function116;
        Function1 function117;
        Function1 function118;
        final Alignment alignment4;
        ?? r13;
        DialogNavigator dialogNavigator;
        final Function1 function119;
        final Function1 function120;
        final Function1 function121;
        final MutableState mutableState2;
        final State state;
        DialogNavigator dialogNavigator2;
        boolean z;
        Map map;
        ComposeNavigator composeNavigator;
        final State state2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1964664536);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(navHostController) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                i3 |= composerImpl.changedInstance(navGraph) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    modifier2 = modifier;
                    i3 |= composerImpl.changed(modifier2) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        alignment2 = alignment;
                        i3 |= composerImpl.changed(alignment2) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            function16 = function1;
                            i3 |= composerImpl.changedInstance(function16) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i7 = i2 & 32;
                        if (i7 == 0) {
                            if ((196608 & i) == 0) {
                                function17 = function12;
                                i3 |= composerImpl.changedInstance(function17) ? 131072 : 65536;
                            }
                            if ((i & 1572864) != 0) {
                                function18 = function13;
                                i3 |= ((i2 & 64) == 0 && composerImpl.changedInstance(function18)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            } else {
                                function18 = function13;
                            }
                            if ((i & 12582912) != 0) {
                                if ((i2 & 128) == 0) {
                                    function19 = function14;
                                    int i11 = composerImpl.changedInstance(function19) ? 8388608 : 4194304;
                                    i3 |= i11;
                                } else {
                                    function19 = function14;
                                }
                                i3 |= i11;
                            } else {
                                function19 = function14;
                            }
                            i8 = i3;
                            i9 = i2 & 256;
                            if (i9 == 0) {
                                i8 |= 100663296;
                            } else if ((i & 100663296) == 0) {
                                i8 |= composerImpl.changedInstance(function15) ? 67108864 : 33554432;
                            }
                            if ((i8 & 38347923) != 38347922 && composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                                modifier4 = modifier2;
                                alignment4 = alignment2;
                                function119 = function18;
                                function121 = function16;
                                function120 = function15;
                            } else {
                                composerImpl.startDefaults();
                                if ((i & 1) == 0 && !composerImpl.getDefaultsInvalid()) {
                                    composerImpl.skipToGroupEnd();
                                    if ((i2 & 64) != 0) {
                                        i8 &= -3670017;
                                    }
                                    if ((i2 & 128) != 0) {
                                        i8 &= -29360129;
                                    }
                                } else {
                                    if (i4 != 0) {
                                        modifier2 = Modifier.Companion;
                                    }
                                    if (i5 != 0) {
                                        Alignment.Companion.getClass();
                                        alignment2 = Alignment.Companion.TopStart;
                                    }
                                    if (i6 != 0) {
                                        function16 = new Function1() { // from class: androidx.navigation.compose.NavHostKt.NavHost.23
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                return EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 0, null, 6), 2);
                                            }
                                        };
                                    }
                                    if (i7 != 0) {
                                        function17 = new Function1() { // from class: androidx.navigation.compose.NavHostKt.NavHost.24
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                return EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 0, null, 6), 2);
                                            }
                                        };
                                    }
                                    if ((i2 & 64) != 0) {
                                        i8 &= -3670017;
                                        function18 = function16;
                                    }
                                    if ((i2 & 128) != 0) {
                                        i8 &= -29360129;
                                        function19 = function17;
                                    }
                                    if (i9 == 0) {
                                        modifier3 = modifier2;
                                        alignment3 = alignment2;
                                        function110 = function18;
                                        function111 = function16;
                                        i10 = i8;
                                        function112 = null;
                                        function113 = function19;
                                    }
                                    composerImpl.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.navigation.compose.NavHost (NavHost.kt:488)");
                                    }
                                    final LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
                                    LocalViewModelStoreOwner.INSTANCE.getClass();
                                    current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                                    if (current != null) {
                                        ViewModelStore viewModelStore = current.getViewModelStore();
                                        NavControllerViewModel navControllerViewModel = navHostController.viewModel;
                                        NavControllerViewModel.Companion.getClass();
                                        NavControllerViewModel$Companion$FACTORY$1 navControllerViewModel$Companion$FACTORY$1 = NavControllerViewModel.FACTORY;
                                        final Modifier modifier5 = modifier3;
                                        if (!Intrinsics.areEqual(navControllerViewModel, (NavControllerViewModel) new ViewModelProvider(viewModelStore, navControllerViewModel$Companion$FACTORY$1, null, 4, null).get(NavControllerViewModel.class))) {
                                            if (navHostController.backQueue.isEmpty()) {
                                                navHostController.viewModel = (NavControllerViewModel) new ViewModelProvider(viewModelStore, navControllerViewModel$Companion$FACTORY$1, null, 4, null).get(NavControllerViewModel.class);
                                            } else {
                                                throw new IllegalStateException("ViewModelStore should be set before setGraph call");
                                            }
                                        }
                                        navHostController.setGraph(navGraph);
                                        Navigator navigator = navHostController._navigatorProvider.getNavigator("composable");
                                        final ComposeNavigator composeNavigator2 = navigator instanceof ComposeNavigator ? (ComposeNavigator) navigator : null;
                                        if (composeNavigator2 == null) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                                            if (recomposeScopeImplEndRestartGroup2 != null) {
                                                final Function1 function122 = function17;
                                                recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: androidx.navigation.compose.NavHostKt$NavHost$composeNavigator$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(Object obj, Object obj2) throws Throwable {
                                                        ((Number) obj2).intValue();
                                                        NavHostKt.NavHost(navHostController, navGraph, modifier5, alignment3, function111, function122, function110, function113, function112, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                return;
                                            }
                                            return;
                                        }
                                        final Function1 function123 = function111;
                                        final Function1 function124 = function110;
                                        final Function1 function125 = function113;
                                        final Function1 function126 = function112;
                                        MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(composeNavigator2.getState().backStack, composerImpl);
                                        Object objRememberedValue = composerImpl.rememberedValue();
                                        Composer.Companion.getClass();
                                        Object obj = Composer.Companion.Empty;
                                        if (objRememberedValue == obj) {
                                            objRememberedValue = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                        }
                                        MutableFloatState mutableFloatState = (MutableFloatState) objRememberedValue;
                                        Object objRememberedValue2 = composerImpl.rememberedValue();
                                        if (objRememberedValue2 == obj) {
                                            objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                                            composerImpl.updateRememberedValue(objRememberedValue2);
                                        }
                                        MutableState mutableState3 = (MutableState) objRememberedValue2;
                                        Alignment alignment5 = alignment3;
                                        boolean z2 = ((List) mutableStateCollectAsState.getValue()).size() > 1;
                                        boolean zChanged = composerImpl.changed(mutableStateCollectAsState) | composerImpl.changed(composeNavigator2);
                                        Object objRememberedValue3 = composerImpl.rememberedValue();
                                        if (zChanged || objRememberedValue3 == obj) {
                                            objRememberedValue3 = new NavHostKt$NavHost$25$1(composeNavigator2, mutableFloatState, mutableStateCollectAsState, mutableState3, null);
                                            mutableState = mutableState3;
                                            composerImpl.updateRememberedValue(objRememberedValue3);
                                        } else {
                                            mutableState = mutableState3;
                                        }
                                        PredictiveBackHandlerKt.PredictiveBackHandler(z2, (Function2) objRememberedValue3, composerImpl, 0, 0);
                                        boolean zChangedInstance = composerImpl.changedInstance(navHostController) | composerImpl.changedInstance(lifecycleOwner);
                                        Object objRememberedValue4 = composerImpl.rememberedValue();
                                        if (zChangedInstance || objRememberedValue4 == obj) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$26$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    Lifecycle lifecycle;
                                                    NavHostController navHostController2 = navHostController;
                                                    LifecycleOwner lifecycleOwner2 = lifecycleOwner;
                                                    if (!Intrinsics.areEqual(lifecycleOwner2, navHostController2.lifecycleOwner)) {
                                                        LifecycleOwner lifecycleOwner3 = navHostController2.lifecycleOwner;
                                                        NavController$$ExternalSyntheticLambda0 navController$$ExternalSyntheticLambda0 = navHostController2.lifecycleObserver;
                                                        if (lifecycleOwner3 != null && (lifecycle = lifecycleOwner3.getLifecycle()) != null) {
                                                            lifecycle.removeObserver(navController$$ExternalSyntheticLambda0);
                                                        }
                                                        navHostController2.lifecycleOwner = lifecycleOwner2;
                                                        lifecycleOwner2.getLifecycle().addObserver(navController$$ExternalSyntheticLambda0);
                                                    }
                                                    return new DisposableEffectResult() { // from class: androidx.navigation.compose.NavHostKt$NavHost$26$1$invoke$$inlined$onDispose$1
                                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                                        public final void dispose() {
                                                        }
                                                    };
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue4);
                                        }
                                        EffectsKt.DisposableEffect(lifecycleOwner, (Function1) objRememberedValue4, composerImpl);
                                        final SaveableStateHolder saveableStateHolderRememberSaveableStateHolder = SaveableStateHolderKt.rememberSaveableStateHolder(composerImpl);
                                        final MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(navHostController.visibleEntries, composerImpl);
                                        Object objRememberedValue5 = composerImpl.rememberedValue();
                                        if (objRememberedValue5 == obj) {
                                            objRememberedValue5 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.navigation.compose.NavHostKt$NavHost$visibleEntries$2$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                /* JADX WARN: Multi-variable type inference failed */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    List list = (List) mutableStateCollectAsState2.getValue();
                                                    ArrayList arrayList = new ArrayList();
                                                    for (Object obj2 : list) {
                                                        if (Intrinsics.areEqual(((NavBackStackEntry) obj2).destination.navigatorName, "composable")) {
                                                            arrayList.add(obj2);
                                                        }
                                                    }
                                                    return arrayList;
                                                }
                                            });
                                            composerImpl.updateRememberedValue(objRememberedValue5);
                                        }
                                        final State state3 = (State) objRememberedValue5;
                                        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) CollectionsKt___CollectionsKt.lastOrNull((List) state3.getValue());
                                        Object objRememberedValue6 = composerImpl.rememberedValue();
                                        if (objRememberedValue6 == obj) {
                                            objRememberedValue6 = new LinkedHashMap();
                                            composerImpl.updateRememberedValue(objRememberedValue6);
                                        }
                                        final Map map2 = (Map) objRememberedValue6;
                                        composerImpl.startReplaceGroup(653349945);
                                        if (navBackStackEntry != null) {
                                            boolean zChanged2 = composerImpl.changed(composeNavigator2) | ((((i10 & 3670016) ^ 1572864) > 1048576 && composerImpl.changed(function124)) || (i10 & 1572864) == 1048576) | ((57344 & i10) == 16384);
                                            Object objRememberedValue7 = composerImpl.rememberedValue();
                                            if (zChanged2 || objRememberedValue7 == obj) {
                                                mutableState2 = mutableState;
                                                objRememberedValue7 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$finalEnter$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl = (AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj2);
                                                        ComposeNavigator.Destination destination = (ComposeNavigator.Destination) ((NavBackStackEntry) animatedContentTransitionScopeImpl.getTargetState()).destination;
                                                        if (((Boolean) ((SnapshotMutableStateImpl) composeNavigator2.isPop).getValue()).booleanValue() || ((Boolean) mutableState2.getValue()).booleanValue()) {
                                                            NavDestination.Companion.getClass();
                                                            for (NavDestination navDestination : NavDestination.Companion.getHierarchy(destination)) {
                                                                if (navDestination instanceof ComposeNavigator.Destination) {
                                                                    ((ComposeNavigator.Destination) navDestination).getClass();
                                                                } else if (navDestination instanceof ComposeNavGraphNavigator.ComposeNavGraph) {
                                                                    ((ComposeNavGraphNavigator.ComposeNavGraph) navDestination).getClass();
                                                                }
                                                            }
                                                            return (EnterTransition) function124.mo781invoke(animatedContentTransitionScopeImpl);
                                                        }
                                                        NavDestination.Companion.getClass();
                                                        for (NavDestination navDestination2 : NavDestination.Companion.getHierarchy(destination)) {
                                                            if (navDestination2 instanceof ComposeNavigator.Destination) {
                                                                ((ComposeNavigator.Destination) navDestination2).getClass();
                                                            } else if (navDestination2 instanceof ComposeNavGraphNavigator.ComposeNavGraph) {
                                                                ((ComposeNavGraphNavigator.ComposeNavGraph) navDestination2).getClass();
                                                            }
                                                        }
                                                        return (EnterTransition) function123.mo781invoke(animatedContentTransitionScopeImpl);
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue7);
                                            } else {
                                                mutableState2 = mutableState;
                                            }
                                            final Function1 function127 = (Function1) objRememberedValue7;
                                            function114 = function123;
                                            function115 = function124;
                                            boolean zChanged3 = composerImpl.changed(composeNavigator2) | ((((i10 & 29360128) ^ 12582912) > 8388608 && composerImpl.changed(function125)) || (i10 & 12582912) == 8388608) | ((458752 & i10) == 131072);
                                            Object objRememberedValue8 = composerImpl.rememberedValue();
                                            if (zChanged3 || objRememberedValue8 == obj) {
                                                objRememberedValue8 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$finalExit$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl = (AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj2);
                                                        ComposeNavigator.Destination destination = (ComposeNavigator.Destination) ((NavBackStackEntry) animatedContentTransitionScopeImpl.getInitialState()).destination;
                                                        if (((Boolean) ((SnapshotMutableStateImpl) composeNavigator2.isPop).getValue()).booleanValue() || ((Boolean) mutableState2.getValue()).booleanValue()) {
                                                            NavDestination.Companion.getClass();
                                                            for (NavDestination navDestination : NavDestination.Companion.getHierarchy(destination)) {
                                                                if (navDestination instanceof ComposeNavigator.Destination) {
                                                                    ((ComposeNavigator.Destination) navDestination).getClass();
                                                                } else if (navDestination instanceof ComposeNavGraphNavigator.ComposeNavGraph) {
                                                                    ((ComposeNavGraphNavigator.ComposeNavGraph) navDestination).getClass();
                                                                }
                                                            }
                                                            return (ExitTransition) function125.mo781invoke(animatedContentTransitionScopeImpl);
                                                        }
                                                        NavDestination.Companion.getClass();
                                                        for (NavDestination navDestination2 : NavDestination.Companion.getHierarchy(destination)) {
                                                            if (navDestination2 instanceof ComposeNavigator.Destination) {
                                                                ((ComposeNavigator.Destination) navDestination2).getClass();
                                                            } else if (navDestination2 instanceof ComposeNavGraphNavigator.ComposeNavGraph) {
                                                                ((ComposeNavGraphNavigator.ComposeNavGraph) navDestination2).getClass();
                                                            }
                                                        }
                                                        return (ExitTransition) function17.mo781invoke(animatedContentTransitionScopeImpl);
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue8);
                                            }
                                            final Function1 function128 = (Function1) objRememberedValue8;
                                            function117 = function125;
                                            boolean z3 = (234881024 & i10) == 67108864;
                                            Object objRememberedValue9 = composerImpl.rememberedValue();
                                            if (z3 || objRememberedValue9 == obj) {
                                                objRememberedValue9 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$finalSizeTransform$1$1
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl = (AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj2);
                                                        ComposeNavigator.Destination destination = (ComposeNavigator.Destination) ((NavBackStackEntry) animatedContentTransitionScopeImpl.getTargetState()).destination;
                                                        NavDestination.Companion.getClass();
                                                        for (NavDestination navDestination : NavDestination.Companion.getHierarchy(destination)) {
                                                            if (navDestination instanceof ComposeNavigator.Destination) {
                                                                ((ComposeNavigator.Destination) navDestination).getClass();
                                                            } else if (navDestination instanceof ComposeNavGraphNavigator.ComposeNavGraph) {
                                                                ((ComposeNavGraphNavigator.ComposeNavGraph) navDestination).getClass();
                                                            }
                                                        }
                                                        Function1 function129 = function126;
                                                        if (function129 != null) {
                                                            return (SizeTransform) function129.mo781invoke(animatedContentTransitionScopeImpl);
                                                        }
                                                        return null;
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue9);
                                            }
                                            final Function1 function129 = (Function1) objRememberedValue9;
                                            Boolean bool = Boolean.TRUE;
                                            boolean zChanged4 = composerImpl.changed(composeNavigator2);
                                            function116 = function126;
                                            Object objRememberedValue10 = composerImpl.rememberedValue();
                                            if (zChanged4 || objRememberedValue10 == obj) {
                                                objRememberedValue10 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$27$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        final State<List<NavBackStackEntry>> state4 = state3;
                                                        final ComposeNavigator composeNavigator3 = composeNavigator2;
                                                        return new DisposableEffectResult() { // from class: androidx.navigation.compose.NavHostKt$NavHost$27$1$invoke$$inlined$onDispose$1
                                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                                            public final void dispose() {
                                                                Iterator it = ((List) state4.getValue()).iterator();
                                                                while (it.hasNext()) {
                                                                    composeNavigator3.getState().markTransitionComplete((NavBackStackEntry) it.next());
                                                                }
                                                            }
                                                        };
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue10);
                                            }
                                            EffectsKt.DisposableEffect(bool, (Function1) objRememberedValue10, composerImpl);
                                            Object objRememberedValue11 = composerImpl.rememberedValue();
                                            if (objRememberedValue11 == obj) {
                                                objRememberedValue11 = new SeekableTransitionState(navBackStackEntry);
                                                composerImpl.updateRememberedValue(objRememberedValue11);
                                            }
                                            SeekableTransitionState seekableTransitionState = (SeekableTransitionState) objRememberedValue11;
                                            if (((Boolean) mutableState2.getValue()).booleanValue()) {
                                                composerImpl.startReplaceGroup(-1218750696);
                                                SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
                                                Float fValueOf = Float.valueOf(snapshotMutableFloatStateImpl.getFloatValue());
                                                boolean zChanged5 = composerImpl.changed(mutableStateCollectAsState) | composerImpl.changedInstance(seekableTransitionState);
                                                state = state3;
                                                Object objRememberedValue12 = composerImpl.rememberedValue();
                                                if (zChanged5 || objRememberedValue12 == obj) {
                                                    function118 = function17;
                                                    objRememberedValue12 = new NavHostKt$NavHost$28$1(seekableTransitionState, mutableStateCollectAsState, snapshotMutableFloatStateImpl, null);
                                                    composerImpl.updateRememberedValue(objRememberedValue12);
                                                } else {
                                                    function118 = function17;
                                                }
                                                EffectsKt.LaunchedEffect(composerImpl, fValueOf, (Function2) objRememberedValue12);
                                                composerImpl.end(false);
                                                z = false;
                                                dialogNavigator2 = null;
                                            } else {
                                                state = state3;
                                                function118 = function17;
                                                composerImpl.startReplaceGroup(-1218533572);
                                                boolean zChangedInstance2 = composerImpl.changedInstance(seekableTransitionState) | composerImpl.changedInstance(navBackStackEntry);
                                                Object objRememberedValue13 = composerImpl.rememberedValue();
                                                if (zChangedInstance2 || objRememberedValue13 == obj) {
                                                    dialogNavigator2 = null;
                                                    objRememberedValue13 = new NavHostKt$NavHost$29$1(seekableTransitionState, navBackStackEntry, null);
                                                    composerImpl.updateRememberedValue(objRememberedValue13);
                                                } else {
                                                    dialogNavigator2 = null;
                                                }
                                                EffectsKt.LaunchedEffect(composerImpl, navBackStackEntry, (Function2) objRememberedValue13);
                                                z = false;
                                                composerImpl.end(false);
                                            }
                                            Transition transitionRememberTransition = TransitionKt.rememberTransition(seekableTransitionState, "entry", composerImpl, SeekableTransitionState.$stable | 48);
                                            boolean zChangedInstance3 = composerImpl.changedInstance(map2) | composerImpl.changed(composeNavigator2) | composerImpl.changed(function127) | composerImpl.changed(function128) | composerImpl.changed(function129);
                                            Object objRememberedValue14 = composerImpl.rememberedValue();
                                            if (zChangedInstance3 || objRememberedValue14 == obj) {
                                                final ComposeNavigator composeNavigator3 = composeNavigator2;
                                                objRememberedValue14 = new Function1() { // from class: androidx.navigation.compose.NavHostKt$NavHost$30$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        float fFloatValue;
                                                        AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl = (AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj2);
                                                        if (!((List) state.getValue()).contains(animatedContentTransitionScopeImpl.getInitialState())) {
                                                            EnterTransition.Companion.getClass();
                                                            EnterTransition enterTransition = EnterTransition.None;
                                                            ExitTransition.Companion.getClass();
                                                            return AnimatedContentKt.togetherWith(enterTransition, ExitTransition.None);
                                                        }
                                                        Float f = map2.get(((NavBackStackEntry) animatedContentTransitionScopeImpl.getInitialState()).id);
                                                        if (f != null) {
                                                            fFloatValue = f.floatValue();
                                                        } else {
                                                            map2.put(((NavBackStackEntry) animatedContentTransitionScopeImpl.getInitialState()).id, Float.valueOf(0.0f));
                                                            fFloatValue = 0.0f;
                                                        }
                                                        if (!Intrinsics.areEqual(((NavBackStackEntry) animatedContentTransitionScopeImpl.getTargetState()).id, ((NavBackStackEntry) animatedContentTransitionScopeImpl.getInitialState()).id)) {
                                                            fFloatValue = ((Boolean) ((SnapshotMutableStateImpl) composeNavigator3.isPop).getValue()).booleanValue() ? fFloatValue - 1.0f : fFloatValue + 1.0f;
                                                        }
                                                        map2.put(((NavBackStackEntry) animatedContentTransitionScopeImpl.getTargetState()).id, Float.valueOf(fFloatValue));
                                                        return new ContentTransform((EnterTransition) function127.mo781invoke(animatedContentTransitionScopeImpl), (ExitTransition) function128.mo781invoke(animatedContentTransitionScopeImpl), fFloatValue, (SizeTransform) function129.mo781invoke(animatedContentTransitionScopeImpl));
                                                    }
                                                };
                                                map = map2;
                                                composeNavigator = composeNavigator3;
                                                state2 = state;
                                                composerImpl.updateRememberedValue(objRememberedValue14);
                                            } else {
                                                composeNavigator = composeNavigator2;
                                                map = map2;
                                                state2 = state;
                                            }
                                            r13 = z;
                                            dialogNavigator = dialogNavigator2;
                                            AnimatedContentKt.AnimatedContent(transitionRememberTransition, modifier5, (Function1) objRememberedValue14, alignment5, new Function1() { // from class: androidx.navigation.compose.NavHostKt.NavHost.31
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    return ((NavBackStackEntry) obj2).id;
                                                }
                                            }, ComposableLambdaKt.rememberComposableLambda(820763100, new Function4() { // from class: androidx.navigation.compose.NavHostKt.NavHost.32
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                /* JADX WARN: Multi-variable type inference failed */
                                                {
                                                    super(4);
                                                }

                                                /* JADX WARN: Multi-variable type inference failed */
                                                /* JADX WARN: Type inference failed for: r0v2 */
                                                /* JADX WARN: Type inference failed for: r0v3 */
                                                /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object] */
                                                @Override // kotlin.jvm.functions.Function4
                                                public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                                                    NavBackStackEntry navBackStackEntryPrevious;
                                                    final AnimatedContentScope animatedContentScope = (AnimatedContentScope) obj2;
                                                    final NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) obj3;
                                                    Composer composer2 = (Composer) obj4;
                                                    ((Number) obj5).intValue();
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.navigation.compose.NavHost.<anonymous> (NavHost.kt:656)");
                                                    }
                                                    if (!((Boolean) mutableState2.getValue()).booleanValue()) {
                                                        List list = (List) state2.getValue();
                                                        ListIterator listIterator = list.listIterator(list.size());
                                                        while (true) {
                                                            if (!listIterator.hasPrevious()) {
                                                                navBackStackEntryPrevious = 0;
                                                                break;
                                                            }
                                                            navBackStackEntryPrevious = listIterator.previous();
                                                            if (Intrinsics.areEqual(navBackStackEntry2, (NavBackStackEntry) navBackStackEntryPrevious)) {
                                                                break;
                                                            }
                                                        }
                                                        navBackStackEntry2 = navBackStackEntryPrevious;
                                                    }
                                                    if (navBackStackEntry2 != null) {
                                                        NavBackStackEntryProviderKt.LocalOwnersProvider(navBackStackEntry2, saveableStateHolderRememberSaveableStateHolder, ComposableLambdaKt.rememberComposableLambda(-1263531443, new Function2() { // from class: androidx.navigation.compose.NavHostKt.NavHost.32.1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(2);
                                                            }

                                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                            @Override // kotlin.jvm.functions.Function2
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                            */
                                                            public final Object invoke(Object obj6, Object obj7) {
                                                                Composer composer3 = (Composer) obj6;
                                                                if ((((Number) obj7).intValue() & 3) == 2) {
                                                                    ComposerImpl composerImpl2 = (ComposerImpl) composer3;
                                                                    if (composerImpl2.getSkipping()) {
                                                                        composerImpl2.skipToGroupEnd();
                                                                    } else {
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("androidx.navigation.compose.NavHost.<anonymous>.<anonymous> (NavHost.kt:669)");
                                                                        }
                                                                        NavBackStackEntry navBackStackEntry3 = navBackStackEntry2;
                                                                        ((ComposeNavigator.Destination) navBackStackEntry3.destination).content.invoke(animatedContentScope, navBackStackEntry3, composer3, 0);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, composer2), composer2, 384);
                                                    }
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl), composerImpl, (i10 & 7168) | ((i10 >> 3) & 112) | 221184, 0);
                                            alignment4 = alignment5;
                                            modifier4 = modifier5;
                                            Object currentState = transitionRememberTransition.transitionState.getCurrentState();
                                            Object value = ((SnapshotMutableStateImpl) transitionRememberTransition.targetState$delegate).getValue();
                                            boolean zChanged6 = composerImpl.changed(transitionRememberTransition) | composerImpl.changed(composeNavigator) | composerImpl.changedInstance(map);
                                            Object objRememberedValue15 = composerImpl.rememberedValue();
                                            if (zChanged6 || objRememberedValue15 == obj) {
                                                objRememberedValue15 = new NavHostKt$NavHost$33$1(transitionRememberTransition, map, state2, composeNavigator, null);
                                                composerImpl.updateRememberedValue(objRememberedValue15);
                                            }
                                            EffectsKt.LaunchedEffect(currentState, value, (Function2) objRememberedValue15, composerImpl);
                                        } else {
                                            modifier4 = modifier5;
                                            function114 = function123;
                                            function115 = function124;
                                            function116 = function126;
                                            function117 = function125;
                                            function118 = function17;
                                            alignment4 = alignment5;
                                            r13 = 0;
                                            dialogNavigator = null;
                                        }
                                        composerImpl.end(r13);
                                        Navigator navigator2 = navHostController._navigatorProvider.getNavigator("dialog");
                                        DialogNavigator dialogNavigator3 = navigator2 instanceof DialogNavigator ? (DialogNavigator) navigator2 : dialogNavigator;
                                        if (dialogNavigator3 == null) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            RecomposeScopeImpl recomposeScopeImplEndRestartGroup3 = composerImpl.endRestartGroup();
                                            if (recomposeScopeImplEndRestartGroup3 != null) {
                                                final Function1 function130 = function117;
                                                final Function1 function131 = function115;
                                                final Function1 function132 = function118;
                                                final Function1 function133 = function116;
                                                final Function1 function134 = function114;
                                                recomposeScopeImplEndRestartGroup3.block = new Function2() { // from class: androidx.navigation.compose.NavHostKt$NavHost$dialogNavigator$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(Object obj2, Object obj3) throws Throwable {
                                                        ((Number) obj3).intValue();
                                                        NavHostKt.NavHost(navHostController, navGraph, modifier4, alignment4, function134, function132, function131, function130, function133, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                return;
                                            }
                                            return;
                                        }
                                        function19 = function117;
                                        function119 = function115;
                                        function17 = function118;
                                        function120 = function116;
                                        function121 = function114;
                                        DialogHostKt.DialogHost(dialogNavigator3, composerImpl, r13);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    } else {
                                        throw new IllegalStateException("NavHost requires a ViewModelStoreOwner to be provided via LocalViewModelStoreOwner");
                                    }
                                }
                                modifier3 = modifier2;
                                alignment3 = alignment2;
                                function110 = function18;
                                function111 = function16;
                                function113 = function19;
                                i10 = i8;
                                function112 = function15;
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                final LifecycleOwner lifecycleOwner2 = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
                                LocalViewModelStoreOwner.INSTANCE.getClass();
                                current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                                if (current != null) {
                                }
                            }
                            final Function1 function135 = function19;
                            final Function1 function136 = function17;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.navigation.compose.NavHostKt.NavHost.34
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj2, Object obj3) throws Throwable {
                                        ((Number) obj3).intValue();
                                        NavHostKt.NavHost(navHostController, navGraph, modifier4, alignment4, function121, function136, function119, function135, function120, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 196608;
                        function17 = function12;
                        if ((i & 1572864) != 0) {
                        }
                        if ((i & 12582912) != 0) {
                        }
                        i8 = i3;
                        i9 = i2 & 256;
                        if (i9 == 0) {
                        }
                        if ((i8 & 38347923) != 38347922) {
                            composerImpl.startDefaults();
                            if ((i & 1) == 0) {
                                if (i4 != 0) {
                                }
                                if (i5 != 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i7 != 0) {
                                }
                                if ((i2 & 64) != 0) {
                                }
                                if ((i2 & 128) != 0) {
                                }
                                if (i9 == 0) {
                                    modifier3 = modifier2;
                                    alignment3 = alignment2;
                                    function110 = function18;
                                    function111 = function16;
                                    function113 = function19;
                                    i10 = i8;
                                    function112 = function15;
                                }
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                final LifecycleOwner lifecycleOwner22 = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
                                LocalViewModelStoreOwner.INSTANCE.getClass();
                                current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                                if (current != null) {
                                }
                            }
                        }
                        final Function1 function1352 = function19;
                        final Function1 function1362 = function17;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    function16 = function1;
                    i7 = i2 & 32;
                    if (i7 == 0) {
                    }
                    function17 = function12;
                    if ((i & 1572864) != 0) {
                    }
                    if ((i & 12582912) != 0) {
                    }
                    i8 = i3;
                    i9 = i2 & 256;
                    if (i9 == 0) {
                    }
                    if ((i8 & 38347923) != 38347922) {
                    }
                    final Function1 function13522 = function19;
                    final Function1 function13622 = function17;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                alignment2 = alignment;
                i6 = i2 & 16;
                if (i6 == 0) {
                }
                function16 = function1;
                i7 = i2 & 32;
                if (i7 == 0) {
                }
                function17 = function12;
                if ((i & 1572864) != 0) {
                }
                if ((i & 12582912) != 0) {
                }
                i8 = i3;
                i9 = i2 & 256;
                if (i9 == 0) {
                }
                if ((i8 & 38347923) != 38347922) {
                }
                final Function1 function135222 = function19;
                final Function1 function136222 = function17;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            modifier2 = modifier;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            alignment2 = alignment;
            i6 = i2 & 16;
            if (i6 == 0) {
            }
            function16 = function1;
            i7 = i2 & 32;
            if (i7 == 0) {
            }
            function17 = function12;
            if ((i & 1572864) != 0) {
            }
            if ((i & 12582912) != 0) {
            }
            i8 = i3;
            i9 = i2 & 256;
            if (i9 == 0) {
            }
            if ((i8 & 38347923) != 38347922) {
            }
            final Function1 function1352222 = function19;
            final Function1 function1362222 = function17;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        modifier2 = modifier;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        alignment2 = alignment;
        i6 = i2 & 16;
        if (i6 == 0) {
        }
        function16 = function1;
        i7 = i2 & 32;
        if (i7 == 0) {
        }
        function17 = function12;
        if ((i & 1572864) != 0) {
        }
        if ((i & 12582912) != 0) {
        }
        i8 = i3;
        i9 = i2 & 256;
        if (i9 == 0) {
        }
        if ((i8 & 38347923) != 38347922) {
        }
        final Function1 function13522222 = function19;
        final Function1 function13622222 = function17;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
