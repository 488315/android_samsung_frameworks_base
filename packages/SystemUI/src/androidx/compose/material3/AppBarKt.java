package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.gestures.DraggableKt;
import androidx.compose.foundation.gestures.DraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.ProvideContentColorTextStyleKt;
import androidx.compose.material3.tokens.AppBarSmallTokens;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.ResultKt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class AppBarKt {
    public static final float TopAppBarHorizontalPadding;
    public static final float TopAppBarTitleInset;

    static {
        Dp.Companion companion = Dp.Companion;
        new CubicBezierEasing(0.8f, 0.0f, 0.8f, 0.15f);
        float f = 4;
        TopAppBarHorizontalPadding = f;
        TopAppBarTitleInset = 16 - f;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:145:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0114  */
    /* renamed from: CenterAlignedTopAppBar-GHTll3U, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m246CenterAlignedTopAppBarGHTll3U(final Function2 function2, Modifier modifier, Function2 function22, Function3 function3, float f, WindowInsets windowInsets, TopAppBarColors topAppBarColors, TopAppBarScrollBehavior topAppBarScrollBehavior, Composer composer, final int i, final int i2) {
        Function2 function23;
        int i3;
        Modifier modifier2;
        int i4;
        Function2 function24;
        int i5;
        Function3 function32;
        int i6;
        float f2;
        WindowInsets windowInsets2;
        TopAppBarColors topAppBarColors2;
        int i7;
        int i8;
        Modifier modifier3;
        float f3;
        TopAppBarScrollBehavior topAppBarScrollBehavior2;
        Function2 function25;
        Function3 function33;
        WindowInsets windowInsets3;
        TopAppBarColors topAppBarColors3;
        float f4;
        ComposerImpl composerImpl;
        final float f5;
        final Modifier modifier4;
        final Function2 function26;
        final Function3 function34;
        final WindowInsets windowInsets4;
        final TopAppBarColors topAppBarColors4;
        final TopAppBarScrollBehavior topAppBarScrollBehavior3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i9;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1952988048);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            function23 = function2;
        } else {
            function23 = function2;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(function23) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i10 = i2 & 2;
        if (i10 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function24 = function22;
                    i3 |= composerImpl2.changedInstance(function24) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        function32 = function3;
                        i3 |= composerImpl2.changedInstance(function32) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 != 0) {
                        if ((i & 24576) == 0) {
                            f2 = f;
                            i3 |= composerImpl2.changed(f2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((196608 & i) == 0) {
                            if ((i2 & 32) == 0) {
                                windowInsets2 = windowInsets;
                                int i11 = composerImpl2.changed(windowInsets2) ? 131072 : 65536;
                                i3 |= i11;
                            } else {
                                windowInsets2 = windowInsets;
                            }
                            i3 |= i11;
                        } else {
                            windowInsets2 = windowInsets;
                        }
                        if ((1572864 & i) == 0) {
                            if ((i2 & 64) == 0) {
                                topAppBarColors2 = topAppBarColors;
                                if (composerImpl2.changed(topAppBarColors2)) {
                                    i9 = 1048576;
                                }
                                i3 |= i9;
                            } else {
                                topAppBarColors2 = topAppBarColors;
                            }
                            i9 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            i3 |= i9;
                        } else {
                            topAppBarColors2 = topAppBarColors;
                        }
                        i7 = i2 & 128;
                        if (i7 != 0) {
                            i8 = i3 | 12582912;
                        } else {
                            int i12 = i3;
                            if ((i & 12582912) == 0) {
                                i8 = i12 | (composerImpl2.changed(topAppBarScrollBehavior) ? 8388608 : 4194304);
                            } else {
                                i8 = i12;
                            }
                        }
                        if ((i8 & 4793491) == 4793490 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            topAppBarScrollBehavior3 = topAppBarScrollBehavior;
                            composerImpl = composerImpl2;
                            modifier4 = modifier2;
                            function26 = function24;
                            function34 = function32;
                            f5 = f2;
                            windowInsets4 = windowInsets2;
                            topAppBarColors4 = topAppBarColors2;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                Modifier modifier5 = i10 == 0 ? Modifier.Companion : modifier2;
                                if (i4 != 0) {
                                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                                    function24 = ComposableSingletons$AppBarKt.f5lambda7;
                                }
                                if (i5 != 0) {
                                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                                    function32 = ComposableSingletons$AppBarKt.f6lambda8;
                                }
                                if (i6 != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    f2 = TopAppBarDefaults.TopAppBarExpandedHeight;
                                }
                                if ((i2 & 32) != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    i8 &= -458753;
                                    windowInsets2 = TopAppBarDefaults.getWindowInsets(composerImpl2);
                                }
                                if ((i2 & 64) != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    i8 &= -3670017;
                                    topAppBarColors2 = TopAppBarDefaults.topAppBarColors(composerImpl2);
                                }
                                if (i7 == 0) {
                                    float f6 = f2;
                                    modifier3 = modifier5;
                                    f3 = f6;
                                    topAppBarScrollBehavior2 = null;
                                } else {
                                    float f7 = f2;
                                    modifier3 = modifier5;
                                    f3 = f7;
                                    topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                }
                                function25 = function24;
                                function33 = function32;
                                windowInsets3 = windowInsets2;
                                topAppBarColors3 = topAppBarColors2;
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 32) != 0) {
                                    i8 &= -458753;
                                }
                                if ((i2 & 64) != 0) {
                                    i8 &= -3670017;
                                }
                                topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                function25 = function24;
                                function33 = function32;
                                f3 = f2;
                                windowInsets3 = windowInsets2;
                                topAppBarColors3 = topAppBarColors2;
                                modifier3 = modifier2;
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.CenterAlignedTopAppBar (AppBar.kt:347)");
                            }
                            AppBarSmallTokens.INSTANCE.getClass();
                            TextStyle value = TypographyKt.getValue(AppBarSmallTokens.TitleFont, composerImpl2);
                            TextStyle.Companion.getClass();
                            TextStyle textStyle = TextStyle.Default;
                            Alignment.Companion.getClass();
                            int i13 = i8;
                            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                            Dp.Companion.getClass();
                            if (!Dp.m838equalsimpl0(f3, Dp.Unspecified) || Dp.m838equalsimpl0(f3, Dp.Infinity)) {
                                TopAppBarDefaults.INSTANCE.getClass();
                                f4 = TopAppBarDefaults.TopAppBarExpandedHeight;
                            } else {
                                f4 = f3;
                            }
                            int i14 = i13 << 12;
                            composerImpl = composerImpl2;
                            m247SingleRowTopAppBarwn8IZOc(modifier3, function23, value, null, textStyle, horizontal, function25, function33, f4, windowInsets3, topAppBarColors3, topAppBarScrollBehavior2, composerImpl, ((i13 >> 3) & 14) | 224256 | ((i13 << 3) & 112) | (3670016 & i14) | (29360128 & i14) | (i14 & 1879048192), (i13 >> 18) & 126, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            f5 = f3;
                            modifier4 = modifier3;
                            function26 = function25;
                            function34 = function33;
                            windowInsets4 = windowInsets3;
                            topAppBarColors4 = topAppBarColors3;
                            topAppBarScrollBehavior3 = topAppBarScrollBehavior2;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.AppBarKt$CenterAlignedTopAppBar$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AppBarKt.m246CenterAlignedTopAppBarGHTll3U(function2, modifier4, function26, function34, f5, windowInsets4, topAppBarColors4, topAppBarScrollBehavior3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 24576;
                    f2 = f;
                    if ((196608 & i) == 0) {
                    }
                    if ((1572864 & i) == 0) {
                    }
                    i7 = i2 & 128;
                    if (i7 != 0) {
                    }
                    if ((i8 & 4793491) == 4793490) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i10 == 0) {
                            }
                            if (i4 != 0) {
                            }
                            if (i5 != 0) {
                            }
                            if (i6 != 0) {
                            }
                            if ((i2 & 32) != 0) {
                            }
                            if ((i2 & 64) != 0) {
                            }
                            if (i7 == 0) {
                            }
                            function25 = function24;
                            function33 = function32;
                            windowInsets3 = windowInsets2;
                            topAppBarColors3 = topAppBarColors2;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            AppBarSmallTokens.INSTANCE.getClass();
                            TextStyle value2 = TypographyKt.getValue(AppBarSmallTokens.TitleFont, composerImpl2);
                            TextStyle.Companion.getClass();
                            TextStyle textStyle2 = TextStyle.Default;
                            Alignment.Companion.getClass();
                            int i132 = i8;
                            BiasAlignment.Horizontal horizontal2 = Alignment.Companion.CenterHorizontally;
                            Dp.Companion.getClass();
                            if (Dp.m838equalsimpl0(f3, Dp.Unspecified)) {
                                TopAppBarDefaults.INSTANCE.getClass();
                                f4 = TopAppBarDefaults.TopAppBarExpandedHeight;
                                int i142 = i132 << 12;
                                composerImpl = composerImpl2;
                                m247SingleRowTopAppBarwn8IZOc(modifier3, function23, value2, null, textStyle2, horizontal2, function25, function33, f4, windowInsets3, topAppBarColors3, topAppBarScrollBehavior2, composerImpl, ((i132 >> 3) & 14) | 224256 | ((i132 << 3) & 112) | (3670016 & i142) | (29360128 & i142) | (i142 & 1879048192), (i132 >> 18) & 126, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                f5 = f3;
                                modifier4 = modifier3;
                                function26 = function25;
                                function34 = function33;
                                windowInsets4 = windowInsets3;
                                topAppBarColors4 = topAppBarColors3;
                                topAppBarScrollBehavior3 = topAppBarScrollBehavior2;
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function32 = function3;
                i6 = i2 & 16;
                if (i6 != 0) {
                }
                f2 = f;
                if ((196608 & i) == 0) {
                }
                if ((1572864 & i) == 0) {
                }
                i7 = i2 & 128;
                if (i7 != 0) {
                }
                if ((i8 & 4793491) == 4793490) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function24 = function22;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            function32 = function3;
            i6 = i2 & 16;
            if (i6 != 0) {
            }
            f2 = f;
            if ((196608 & i) == 0) {
            }
            if ((1572864 & i) == 0) {
            }
            i7 = i2 & 128;
            if (i7 != 0) {
            }
            if ((i8 & 4793491) == 4793490) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function24 = function22;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        function32 = function3;
        i6 = i2 & 16;
        if (i6 != 0) {
        }
        f2 = f;
        if ((196608 & i) == 0) {
        }
        if ((1572864 & i) == 0) {
        }
        i7 = i2 & 128;
        if (i7 != 0) {
        }
        if ((i8 & 4793491) == 4793490) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:231:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0118  */
    /* renamed from: SingleRowTopAppBar-wn8IZOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m247SingleRowTopAppBarwn8IZOc(Modifier modifier, final Function2 function2, final TextStyle textStyle, final Function2 function22, final TextStyle textStyle2, final Alignment.Horizontal horizontal, final Function2 function23, final Function3 function3, final float f, final WindowInsets windowInsets, final TopAppBarColors topAppBarColors, final TopAppBarScrollBehavior topAppBarScrollBehavior, Composer composer, final int i, final int i2, final int i3) {
        Modifier modifier2;
        int i4;
        int i5;
        int i6;
        int i7;
        TextStyle textStyle3;
        int i8;
        int i9;
        int i10;
        Modifier modifierDraggable$default;
        Modifier modifier3;
        ComposerImpl composerImpl;
        final TopAppBarState topAppBarState;
        Modifier modifierOnSizeChanged;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1206124591);
        int i11 = i3 & 1;
        if (i11 != 0) {
            i4 = i | 6;
            modifier2 = modifier;
        } else {
            modifier2 = modifier;
            if ((i & 6) == 0) {
                i4 = i | (composerImpl2.changed(modifier2) ? 4 : 2);
            } else {
                i4 = i;
            }
        }
        if ((i3 & 2) != 0) {
            i6 = i4 | 48;
            i7 = 4;
            i5 = 32;
        } else {
            i5 = 32;
            if ((i & 48) == 0) {
                i4 |= composerImpl2.changedInstance(function2) ? 32 : 16;
            }
            i6 = i4;
            i7 = 4;
        }
        if ((i3 & 4) != 0) {
            i6 |= 384;
        } else {
            if ((i & 384) == 0) {
                i6 |= composerImpl2.changed(textStyle) ? 256 : 128;
            }
            if ((i3 & 8) == 0) {
                i6 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    i6 |= composerImpl2.changedInstance(function22) ? 2048 : 1024;
                }
                if ((i3 & 16) != 0) {
                    i6 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        textStyle3 = textStyle2;
                        i6 |= composerImpl2.changed(textStyle3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if ((i3 & 32) == 0) {
                        i6 |= 196608;
                    } else if ((i & 196608) == 0) {
                        i6 |= composerImpl2.changed(horizontal) ? 131072 : 65536;
                    }
                    if ((i3 & 64) == 0) {
                        i6 |= 1572864;
                    } else if ((i & 1572864) == 0) {
                        i6 |= composerImpl2.changedInstance(function23) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                    if ((128 & i3) == 0) {
                        i6 |= 12582912;
                    } else if ((i & 12582912) == 0) {
                        i6 |= composerImpl2.changedInstance(function3) ? 8388608 : 4194304;
                    }
                    if ((256 & i3) == 0) {
                        i6 |= 100663296;
                    } else if ((i & 100663296) == 0) {
                        i6 |= composerImpl2.changed(f) ? 67108864 : 33554432;
                    }
                    if ((i3 & 512) == 0) {
                        i6 |= 805306368;
                    } else if ((i & 805306368) == 0) {
                        i6 |= composerImpl2.changed(windowInsets) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((1024 & i3) == 0) {
                        i9 = i2 | 6;
                    } else {
                        if ((i2 & 6) != 0) {
                            i8 = i2;
                            if ((2048 & i3) != 0) {
                                i10 = i8 | 48;
                            } else if ((i2 & 48) == 0) {
                                i10 = i8 | (composerImpl2.changed(topAppBarScrollBehavior) ? i5 : 16);
                            } else {
                                i10 = i8;
                            }
                            int i12 = i10;
                            if ((i6 & 306783379) == 306783378 && (i12 & 19) == 18 && composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                modifier3 = modifier2;
                                composerImpl = composerImpl2;
                            } else {
                                Modifier modifier4 = i11 == 0 ? Modifier.Companion : modifier2;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.SingleRowTopAppBar (AppBar.kt:2564)");
                                }
                                if (!Float.isNaN(f) || f == Float.POSITIVE_INFINITY) {
                                    throw new IllegalArgumentException("The expandedHeight is expected to be specified and finite");
                                }
                                int i13 = i12 & 112;
                                boolean z = ((i12 & 14) == i7) | (i13 == i5);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                Composer.Companion companion = Composer.Companion;
                                if (!z) {
                                    companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$targetColor$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
                                            @Override // kotlin.jvm.functions.Function0
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke() {
                                                float fCoerceIn;
                                                TopAppBarState topAppBarState2;
                                                TopAppBarScrollBehavior topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                                if (topAppBarScrollBehavior2 == null || (topAppBarState2 = ((PinnedScrollBehavior) topAppBarScrollBehavior2).state) == null) {
                                                    fCoerceIn = 0.0f;
                                                } else {
                                                    float f2 = topAppBarState2.heightOffsetLimit;
                                                    if (f2 != 0.0f) {
                                                        fCoerceIn = 1 - (RangesKt___RangesKt.coerceIn(f2 - ((SnapshotMutableFloatStateImpl) topAppBarState2.contentOffset$delegate).getFloatValue(), topAppBarState2.heightOffsetLimit, 0.0f) / topAppBarState2.heightOffsetLimit);
                                                    }
                                                }
                                                TopAppBarColors topAppBarColors2 = topAppBarColors;
                                                float f3 = fCoerceIn > 0.01f ? 1.0f : 0.0f;
                                                topAppBarColors2.getClass();
                                                return Color.m456boximpl(ColorKt.m467lerpjxsXWHM(topAppBarColors2.containerColor, topAppBarColors2.scrolledContainerColor, EasingKt.FastOutLinearInEasing.transform(f3)));
                                            }
                                        });
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(((Color) ((State) objRememberedValue).getValue()).value, MotionSchemeKt.value(MotionSchemeKeyTokens.DefaultEffects, composerImpl2), null, composerImpl2, 0, 12);
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-994623000, new Function2() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$actionsRow$1
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
                                                        ComposerKt.traceEventStart("androidx.compose.material3.SingleRowTopAppBar.<anonymous> (AppBar.kt:2591)");
                                                    }
                                                    Arrangement.INSTANCE.getClass();
                                                    Arrangement$End$1 arrangement$End$1 = Arrangement.End;
                                                    Alignment.Companion.getClass();
                                                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                    Function3 function32 = function3;
                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$End$1, vertical, composer2, 54);
                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion2);
                                                    ComposeUiNode.Companion.getClass();
                                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                    if (composerImpl4.applier == null) {
                                                        ComposablesKt.invalidApplier();
                                                        throw null;
                                                    }
                                                    composerImpl4.startReusableNode();
                                                    if (composerImpl4.inserting) {
                                                        composerImpl4.createNode(function0);
                                                    } else {
                                                        composerImpl4.useNode();
                                                    }
                                                    Updater.m337setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                    Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                    if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function24);
                                                    }
                                                    Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                    function32.invoke(RowScopeInstance.INSTANCE, composer2, 6);
                                                    composerImpl4.end(true);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2);
                                    if (topAppBarScrollBehavior == null || ((PinnedScrollBehavior) topAppBarScrollBehavior).isPinned) {
                                        composerImpl2.startReplaceGroup(1689243978);
                                        composerImpl2.end(false);
                                        modifierDraggable$default = Modifier.Companion;
                                    } else {
                                        composerImpl2.startReplaceGroup(1688709445);
                                        Modifier.Companion companion2 = Modifier.Companion;
                                        Orientation orientation = Orientation.Vertical;
                                        boolean z2 = i13 == 32;
                                        Object objRememberedValue2 = composerImpl2.rememberedValue();
                                        if (!z2) {
                                            companion.getClass();
                                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                                objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$appBarDragModifier$1$1
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj) {
                                                        float fFloatValue = ((Number) obj).floatValue();
                                                        TopAppBarState topAppBarState2 = ((PinnedScrollBehavior) topAppBarScrollBehavior).state;
                                                        float heightOffset = topAppBarState2.getHeightOffset() + fFloatValue;
                                                        ((SnapshotMutableFloatStateImpl) topAppBarState2._heightOffset).setFloatValue(RangesKt___RangesKt.coerceIn(heightOffset, topAppBarState2.heightOffsetLimit, 0.0f));
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl2.updateRememberedValue(objRememberedValue2);
                                            }
                                            DraggableState draggableStateRememberDraggableState = DraggableKt.rememberDraggableState(composerImpl2, (Function1) objRememberedValue2);
                                            boolean z3 = i13 == 32;
                                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                                            if (!z3) {
                                                companion.getClass();
                                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                                    objRememberedValue3 = new AppBarKt$SingleRowTopAppBar$appBarDragModifier$2$1(topAppBarScrollBehavior, null);
                                                    composerImpl2.updateRememberedValue(objRememberedValue3);
                                                }
                                                modifierDraggable$default = DraggableKt.draggable$default(companion2, draggableStateRememberDraggableState, orientation, false, null, false, null, (Function3) objRememberedValue3, false, 188);
                                                composerImpl2.end(false);
                                            }
                                        }
                                    }
                                    Modifier modifierThen = modifier4.then(modifierDraggable$default);
                                    boolean zChanged = composerImpl2.changed(stateM7animateColorAsStateeuL9pac);
                                    modifier3 = modifier4;
                                    Object objRememberedValue4 = composerImpl2.rememberedValue();
                                    if (!zChanged) {
                                        companion.getClass();
                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$2$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    DrawScope drawScope = (DrawScope) obj;
                                                    long j = ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value;
                                                    Color.Companion.getClass();
                                                    if (!ULong.m3446equalsimpl0(j, Color.Unspecified)) {
                                                        DrawScope.m541drawRectnJ9OG0$default(drawScope, j, 0L, 0L, 0.0f, null, null, 0, 126);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue4);
                                        }
                                        Modifier modifierThen2 = SemanticsModifierKt.semantics(DrawModifierKt.drawBehind(modifierThen, (Function1) objRememberedValue4), false, new Function1() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$3
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                SemanticsPropertiesKt.setTraversalGroup((SemanticsPropertyReceiver) obj);
                                                return Unit.INSTANCE;
                                            }
                                        }).then(new SuspendPointerInputElement(Unit.INSTANCE, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new AppBarKt$SingleRowTopAppBar$4(null)), 6, null));
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierThen2);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl2.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl2.startReusableNode();
                                        if (composerImpl2.inserting) {
                                            composerImpl2.createNode(function0);
                                        } else {
                                            composerImpl2.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function24);
                                        }
                                        Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        Modifier modifierClipToBounds = ClipKt.clipToBounds(WindowInsetsPaddingKt.windowInsetsPadding(Modifier.Companion, windowInsets));
                                        if (topAppBarScrollBehavior != null && (topAppBarState = ((PinnedScrollBehavior) topAppBarScrollBehavior).state) != null && (modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifierClipToBounds, new Function1() { // from class: androidx.compose.material3.AppBarKt$adjustHeightOffsetLimit$1$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                long j = ((IntSize) obj).packedValue;
                                                IntSize.Companion companion3 = IntSize.Companion;
                                                topAppBarState.heightOffsetLimit = -(((int) (j & 4294967295L)) - topAppBarState.getHeightOffset());
                                                return Unit.INSTANCE;
                                            }
                                        })) != null) {
                                            modifierClipToBounds = modifierOnSizeChanged;
                                        }
                                        boolean z4 = i13 == 32;
                                        Object objRememberedValue5 = composerImpl2.rememberedValue();
                                        if (!z4) {
                                            companion.getClass();
                                            if (objRememberedValue5 == Composer.Companion.Empty) {
                                                objRememberedValue5 = new FloatProducer() { // from class: androidx.compose.material3.AppBarKt$$ExternalSyntheticLambda0
                                                    @Override // androidx.compose.material3.internal.FloatProducer
                                                    public final float invoke() {
                                                        TopAppBarState topAppBarState2;
                                                        float f2 = AppBarKt.TopAppBarHorizontalPadding;
                                                        TopAppBarScrollBehavior topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                                        if (topAppBarScrollBehavior2 == null || (topAppBarState2 = ((PinnedScrollBehavior) topAppBarScrollBehavior2).state) == null) {
                                                            return 0.0f;
                                                        }
                                                        return topAppBarState2.getHeightOffset();
                                                    }
                                                };
                                                composerImpl2.updateRememberedValue(objRememberedValue5);
                                            }
                                            int i14 = i6;
                                            long j = topAppBarColors.navigationIconContentColor;
                                            Arrangement.INSTANCE.getClass();
                                            int i15 = (i14 << 15) & 2146959360;
                                            int i16 = ((i14 >> 9) & 896) | 1600566;
                                            int i17 = i14 >> 3;
                                            m249TopAppBarLayoutlyUyIHI(modifierClipToBounds, (FloatProducer) objRememberedValue5, j, topAppBarColors.titleContentColor, topAppBarColors.subtitleContentColor, topAppBarColors.actionIconContentColor, function2, textStyle, function22, textStyle3, new Function0() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$5$2
                                                @Override // kotlin.jvm.functions.Function0
                                                public final /* bridge */ /* synthetic */ Object invoke() {
                                                    return Float.valueOf(1.0f);
                                                }
                                            }, Arrangement.Center, horizontal, 0, false, function23, composableLambdaImplRememberComposableLambda, f, composerImpl2, i15, i16 | (458752 & i17) | (i17 & 29360128));
                                            composerImpl = composerImpl2;
                                            composerImpl.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                                final Modifier modifier5 = modifier3;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.AppBarKt$SingleRowTopAppBar$6
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        AppBarKt.m247SingleRowTopAppBarwn8IZOc(modifier5, function2, textStyle, function22, textStyle2, horizontal, function23, function3, f, windowInsets, topAppBarColors, topAppBarScrollBehavior, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i9 = i2 | (composerImpl2.changed(topAppBarColors) ? i7 : 2);
                    }
                    i8 = i9;
                    if ((2048 & i3) != 0) {
                    }
                    int i122 = i10;
                    if ((i6 & 306783379) == 306783378) {
                        if (i11 == 0) {
                        }
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        if (Float.isNaN(f)) {
                        }
                        throw new IllegalArgumentException("The expandedHeight is expected to be specified and finite");
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                textStyle3 = textStyle2;
                if ((i3 & 32) == 0) {
                }
                if ((i3 & 64) == 0) {
                }
                if ((128 & i3) == 0) {
                }
                if ((256 & i3) == 0) {
                }
                if ((i3 & 512) == 0) {
                }
                if ((1024 & i3) == 0) {
                }
                i8 = i9;
                if ((2048 & i3) != 0) {
                }
                int i1222 = i10;
                if ((i6 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            if ((i3 & 16) != 0) {
            }
            textStyle3 = textStyle2;
            if ((i3 & 32) == 0) {
            }
            if ((i3 & 64) == 0) {
            }
            if ((128 & i3) == 0) {
            }
            if ((256 & i3) == 0) {
            }
            if ((i3 & 512) == 0) {
            }
            if ((1024 & i3) == 0) {
            }
            i8 = i9;
            if ((2048 & i3) != 0) {
            }
            int i12222 = i10;
            if ((i6 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        if ((i3 & 8) == 0) {
        }
        if ((i3 & 16) != 0) {
        }
        textStyle3 = textStyle2;
        if ((i3 & 32) == 0) {
        }
        if ((i3 & 64) == 0) {
        }
        if ((128 & i3) == 0) {
        }
        if ((256 & i3) == 0) {
        }
        if ((i3 & 512) == 0) {
        }
        if ((1024 & i3) == 0) {
        }
        i8 = i9;
        if ((2048 & i3) != 0) {
        }
        int i122222 = i10;
        if ((i6 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:145:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0114  */
    /* renamed from: TopAppBar-GHTll3U, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m248TopAppBarGHTll3U(final Function2 function2, Modifier modifier, Function2 function22, Function3 function3, float f, WindowInsets windowInsets, TopAppBarColors topAppBarColors, TopAppBarScrollBehavior topAppBarScrollBehavior, Composer composer, final int i, final int i2) {
        Function2 function23;
        int i3;
        Modifier modifier2;
        int i4;
        Function2 function24;
        int i5;
        Function3 function32;
        int i6;
        float f2;
        WindowInsets windowInsets2;
        TopAppBarColors topAppBarColors2;
        int i7;
        int i8;
        Modifier modifier3;
        float f3;
        TopAppBarScrollBehavior topAppBarScrollBehavior2;
        Function2 function25;
        Function3 function33;
        WindowInsets windowInsets3;
        TopAppBarColors topAppBarColors3;
        float f4;
        ComposerImpl composerImpl;
        final float f5;
        final Modifier modifier4;
        final Function2 function26;
        final Function3 function34;
        final WindowInsets windowInsets4;
        final TopAppBarColors topAppBarColors4;
        final TopAppBarScrollBehavior topAppBarScrollBehavior3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i9;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(226148675);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            function23 = function2;
        } else {
            function23 = function2;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(function23) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i10 = i2 & 2;
        if (i10 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function24 = function22;
                    i3 |= composerImpl2.changedInstance(function24) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        function32 = function3;
                        i3 |= composerImpl2.changedInstance(function32) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 != 0) {
                        if ((i & 24576) == 0) {
                            f2 = f;
                            i3 |= composerImpl2.changed(f2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((196608 & i) == 0) {
                            if ((i2 & 32) == 0) {
                                windowInsets2 = windowInsets;
                                int i11 = composerImpl2.changed(windowInsets2) ? 131072 : 65536;
                                i3 |= i11;
                            } else {
                                windowInsets2 = windowInsets;
                            }
                            i3 |= i11;
                        } else {
                            windowInsets2 = windowInsets;
                        }
                        if ((1572864 & i) == 0) {
                            if ((i2 & 64) == 0) {
                                topAppBarColors2 = topAppBarColors;
                                if (composerImpl2.changed(topAppBarColors2)) {
                                    i9 = 1048576;
                                }
                                i3 |= i9;
                            } else {
                                topAppBarColors2 = topAppBarColors;
                            }
                            i9 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            i3 |= i9;
                        } else {
                            topAppBarColors2 = topAppBarColors;
                        }
                        i7 = i2 & 128;
                        if (i7 != 0) {
                            i8 = i3 | 12582912;
                        } else {
                            int i12 = i3;
                            if ((i & 12582912) == 0) {
                                i8 = i12 | (composerImpl2.changed(topAppBarScrollBehavior) ? 8388608 : 4194304);
                            } else {
                                i8 = i12;
                            }
                        }
                        if ((i8 & 4793491) == 4793490 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            topAppBarScrollBehavior3 = topAppBarScrollBehavior;
                            composerImpl = composerImpl2;
                            modifier4 = modifier2;
                            function26 = function24;
                            function34 = function32;
                            f5 = f2;
                            windowInsets4 = windowInsets2;
                            topAppBarColors4 = topAppBarColors2;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                Modifier modifier5 = i10 == 0 ? Modifier.Companion : modifier2;
                                if (i4 != 0) {
                                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                                    function24 = ComposableSingletons$AppBarKt.f3lambda3;
                                }
                                if (i5 != 0) {
                                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                                    function32 = ComposableSingletons$AppBarKt.f4lambda4;
                                }
                                if (i6 != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    f2 = TopAppBarDefaults.TopAppBarExpandedHeight;
                                }
                                if ((i2 & 32) != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    i8 &= -458753;
                                    windowInsets2 = TopAppBarDefaults.getWindowInsets(composerImpl2);
                                }
                                if ((i2 & 64) != 0) {
                                    TopAppBarDefaults.INSTANCE.getClass();
                                    i8 &= -3670017;
                                    topAppBarColors2 = TopAppBarDefaults.topAppBarColors(composerImpl2);
                                }
                                if (i7 == 0) {
                                    float f6 = f2;
                                    modifier3 = modifier5;
                                    f3 = f6;
                                    topAppBarScrollBehavior2 = null;
                                } else {
                                    float f7 = f2;
                                    modifier3 = modifier5;
                                    f3 = f7;
                                    topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                }
                                function25 = function24;
                                function33 = function32;
                                windowInsets3 = windowInsets2;
                                topAppBarColors3 = topAppBarColors2;
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 32) != 0) {
                                    i8 &= -458753;
                                }
                                if ((i2 & 64) != 0) {
                                    i8 &= -3670017;
                                }
                                topAppBarScrollBehavior2 = topAppBarScrollBehavior;
                                function25 = function24;
                                function33 = function32;
                                f3 = f2;
                                windowInsets3 = windowInsets2;
                                topAppBarColors3 = topAppBarColors2;
                                modifier3 = modifier2;
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.TopAppBar (AppBar.kt:222)");
                            }
                            AppBarSmallTokens.INSTANCE.getClass();
                            TextStyle value = TypographyKt.getValue(AppBarSmallTokens.TitleFont, composerImpl2);
                            TextStyle.Companion.getClass();
                            TextStyle textStyle = TextStyle.Default;
                            Alignment.Companion.getClass();
                            int i13 = i8;
                            BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
                            Dp.Companion.getClass();
                            if (!Dp.m838equalsimpl0(f3, Dp.Unspecified) || Dp.m838equalsimpl0(f3, Dp.Infinity)) {
                                TopAppBarDefaults.INSTANCE.getClass();
                                f4 = TopAppBarDefaults.TopAppBarExpandedHeight;
                            } else {
                                f4 = f3;
                            }
                            int i14 = i13 << 12;
                            composerImpl = composerImpl2;
                            m247SingleRowTopAppBarwn8IZOc(modifier3, function23, value, null, textStyle, horizontal, function25, function33, f4, windowInsets3, topAppBarColors3, topAppBarScrollBehavior2, composerImpl, ((i13 >> 3) & 14) | 224256 | ((i13 << 3) & 112) | (3670016 & i14) | (29360128 & i14) | (i14 & 1879048192), (i13 >> 18) & 126, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            f5 = f3;
                            modifier4 = modifier3;
                            function26 = function25;
                            function34 = function33;
                            windowInsets4 = windowInsets3;
                            topAppBarColors4 = topAppBarColors3;
                            topAppBarScrollBehavior3 = topAppBarScrollBehavior2;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.AppBarKt$TopAppBar$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AppBarKt.m248TopAppBarGHTll3U(function2, modifier4, function26, function34, f5, windowInsets4, topAppBarColors4, topAppBarScrollBehavior3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 24576;
                    f2 = f;
                    if ((196608 & i) == 0) {
                    }
                    if ((1572864 & i) == 0) {
                    }
                    i7 = i2 & 128;
                    if (i7 != 0) {
                    }
                    if ((i8 & 4793491) == 4793490) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i10 == 0) {
                            }
                            if (i4 != 0) {
                            }
                            if (i5 != 0) {
                            }
                            if (i6 != 0) {
                            }
                            if ((i2 & 32) != 0) {
                            }
                            if ((i2 & 64) != 0) {
                            }
                            if (i7 == 0) {
                            }
                            function25 = function24;
                            function33 = function32;
                            windowInsets3 = windowInsets2;
                            topAppBarColors3 = topAppBarColors2;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            AppBarSmallTokens.INSTANCE.getClass();
                            TextStyle value2 = TypographyKt.getValue(AppBarSmallTokens.TitleFont, composerImpl2);
                            TextStyle.Companion.getClass();
                            TextStyle textStyle2 = TextStyle.Default;
                            Alignment.Companion.getClass();
                            int i132 = i8;
                            BiasAlignment.Horizontal horizontal2 = Alignment.Companion.Start;
                            Dp.Companion.getClass();
                            if (Dp.m838equalsimpl0(f3, Dp.Unspecified)) {
                                TopAppBarDefaults.INSTANCE.getClass();
                                f4 = TopAppBarDefaults.TopAppBarExpandedHeight;
                                int i142 = i132 << 12;
                                composerImpl = composerImpl2;
                                m247SingleRowTopAppBarwn8IZOc(modifier3, function23, value2, null, textStyle2, horizontal2, function25, function33, f4, windowInsets3, topAppBarColors3, topAppBarScrollBehavior2, composerImpl, ((i132 >> 3) & 14) | 224256 | ((i132 << 3) & 112) | (3670016 & i142) | (29360128 & i142) | (i142 & 1879048192), (i132 >> 18) & 126, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                f5 = f3;
                                modifier4 = modifier3;
                                function26 = function25;
                                function34 = function33;
                                windowInsets4 = windowInsets3;
                                topAppBarColors4 = topAppBarColors3;
                                topAppBarScrollBehavior3 = topAppBarScrollBehavior2;
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function32 = function3;
                i6 = i2 & 16;
                if (i6 != 0) {
                }
                f2 = f;
                if ((196608 & i) == 0) {
                }
                if ((1572864 & i) == 0) {
                }
                i7 = i2 & 128;
                if (i7 != 0) {
                }
                if ((i8 & 4793491) == 4793490) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function24 = function22;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            function32 = function3;
            i6 = i2 & 16;
            if (i6 != 0) {
            }
            f2 = f;
            if ((196608 & i) == 0) {
            }
            if ((1572864 & i) == 0) {
            }
            i7 = i2 & 128;
            if (i7 != 0) {
            }
            if ((i8 & 4793491) == 4793490) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function24 = function22;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        function32 = function3;
        i6 = i2 & 16;
        if (i6 != 0) {
        }
        f2 = f;
        if ((196608 & i) == 0) {
        }
        if ((1572864 & i) == 0) {
        }
        i7 = i2 & 128;
        if (i7 != 0) {
        }
        if ((i8 & 4793491) == 4793490) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:174:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x03fe  */
    /* renamed from: TopAppBarLayout-lyUyIHI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m249TopAppBarLayoutlyUyIHI(final Modifier modifier, final FloatProducer floatProducer, final long j, final long j2, final long j3, final long j4, final Function2 function2, final TextStyle textStyle, final Function2 function22, final TextStyle textStyle2, final Function0 function0, final Arrangement.Vertical vertical, Alignment.Horizontal horizontal, final int i, final boolean z, final Function2 function23, Function2 function24, final float f, Composer composer, final int i2, final int i3) {
        int i4;
        int i5;
        Object topAppBarMeasurePolicy;
        Function2 function25;
        int i6;
        ComposerImpl composerImpl;
        Object obj;
        int i7;
        Alignment.Horizontal horizontal2;
        Function2 function26;
        Function0 function02;
        ComposerImpl composerImpl2;
        int i8;
        Function2 function27;
        Function2 function28;
        BiasAlignment biasAlignment;
        float f2;
        boolean z2;
        Function2 function29 = function24;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1808290480);
        if ((i2 & 6) == 0) {
            i4 = (composerImpl3.changed(modifier) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= (i2 & 64) == 0 ? composerImpl3.changed(floatProducer) : composerImpl3.changedInstance(floatProducer) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl3.changed(j) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl3.changed(j2) ? 2048 : 1024;
        }
        int i9 = i2 & 24576;
        int i10 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i9 == 0) {
            i4 |= composerImpl3.changed(j3) ? 16384 : 8192;
        }
        if ((i2 & 196608) == 0) {
            i4 |= composerImpl3.changed(j4) ? 131072 : 65536;
        }
        int i11 = i2 & 1572864;
        int i12 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i11 == 0) {
            i4 |= composerImpl3.changedInstance(function2) ? 1048576 : 524288;
        }
        if ((i2 & 12582912) == 0) {
            i4 |= composerImpl3.changed(textStyle) ? 8388608 : 4194304;
        }
        if ((100663296 & i2) == 0) {
            i4 |= composerImpl3.changedInstance(function22) ? 67108864 : 33554432;
        }
        if ((805306368 & i2) == 0) {
            i4 |= composerImpl3.changed(textStyle2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        int i13 = i4;
        if ((i3 & 6) == 0) {
            i5 = (composerImpl3.changedInstance(function0) ? 4 : 2) | i3;
        } else {
            i5 = i3;
        }
        if ((i3 & 48) == 0) {
            i5 |= composerImpl3.changed(vertical) ? 32 : 16;
        }
        if ((i3 & 384) == 0) {
            i5 |= composerImpl3.changed(horizontal) ? 256 : 128;
        }
        if ((i3 & 3072) == 0) {
            i5 |= composerImpl3.changed(i) ? 2048 : 1024;
        }
        if ((i3 & 24576) == 0) {
            if (!composerImpl3.changed(z)) {
                i10 = 8192;
            }
            i5 |= i10;
        }
        if ((i3 & 196608) == 0) {
            i5 |= composerImpl3.changedInstance(function23) ? 131072 : 65536;
        }
        if ((i3 & 1572864) == 0) {
            if (composerImpl3.changedInstance(function29)) {
                i12 = 1048576;
            }
            i5 |= i12;
        }
        if ((i3 & 12582912) == 0) {
            i5 |= composerImpl3.changed(f) ? 8388608 : 4194304;
        }
        if ((i13 & 306783379) == 306783378 && (4793491 & i5) == 4793490 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            composerImpl2 = composerImpl3;
            horizontal2 = horizontal;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.TopAppBarLayout (AppBar.kt:2867)");
            }
            int i14 = i5 & 896;
            boolean z3 = ((i13 & 112) == 32 || ((i13 & 64) != 0 && composerImpl3.changed(floatProducer))) | ((i5 & 112) == 32) | (i14 == 256) | ((i5 & 7168) == 2048) | ((29360128 & i5) == 8388608);
            Object objRememberedValue = composerImpl3.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!z3) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    function25 = function23;
                    i6 = i14;
                    composerImpl = composerImpl3;
                    obj = UniversalCredentialUtil.AGENT_TITLE;
                    i7 = i5;
                    topAppBarMeasurePolicy = new TopAppBarMeasurePolicy(floatProducer, vertical, horizontal, i, f, null);
                    horizontal2 = horizontal;
                    composerImpl.updateRememberedValue(topAppBarMeasurePolicy);
                } else {
                    function25 = function23;
                    i6 = i14;
                    composerImpl = composerImpl3;
                    topAppBarMeasurePolicy = objRememberedValue;
                    obj = UniversalCredentialUtil.AGENT_TITLE;
                    horizontal2 = horizontal;
                    i7 = i5;
                }
                TopAppBarMeasurePolicy topAppBarMeasurePolicy2 = (TopAppBarMeasurePolicy) topAppBarMeasurePolicy;
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
                Function2 function210 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl, topAppBarMeasurePolicy2, function210);
                Function2 function211 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function211);
                Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function212);
                }
                Function2 function213 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function213);
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifierLayoutId = LayoutIdKt.layoutId(companion2, "navigationIcon");
                float f3 = TopAppBarHorizontalPadding;
                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierLayoutId, f3, 0.0f, 0.0f, 0.0f, 14);
                Alignment.Companion.getClass();
                BiasAlignment biasAlignment2 = Alignment.Companion.TopStart;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment2, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function03);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function210);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function211);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function212);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function213);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
                CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), function25, composerImpl, ((i7 >> 12) & 112) | 8);
                composerImpl.end(true);
                if (function22 != null) {
                    composerImpl.startReplaceGroup(46540531);
                    Modifier modifierThen = PaddingKt.m127paddingVpY3zN4$default(LayoutIdKt.layoutId(companion2, obj), f3, 0.0f, 2).then(z ? SemanticsModifierKt.clearAndSetSemantics(companion2, new Function1() { // from class: androidx.compose.material3.AppBarKt$TopAppBarLayout$1$2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                            return Unit.INSTANCE;
                        }
                    }) : companion2);
                    boolean z4 = (i7 & 14) == 4;
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!z4) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.AppBarKt$TopAppBarLayout$1$3$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj2)).setAlpha(((Number) function0.invoke()).floatValue());
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierThen, (Function1) objRememberedValue2);
                        Arrangement.INSTANCE.getClass();
                        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal2, composerImpl, (i6 >> 3) & 112);
                        int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        i8 = 8;
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierGraphicsLayer);
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function03);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, columnMeasurePolicy, function210);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function211);
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function212);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function213);
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        int i15 = i13 >> 18;
                        int i16 = i13 >> 12;
                        ComposerImpl composerImpl4 = composerImpl;
                        ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j2, textStyle, function2, composerImpl4, ((i13 >> 9) & 14) | (i15 & 112) | (i16 & 896));
                        int i17 = (i16 & 14) | ((i13 >> 24) & 112) | (i15 & 896);
                        function26 = function211;
                        function27 = function212;
                        f2 = f3;
                        function28 = function210;
                        function02 = function03;
                        composerImpl2 = composerImpl4;
                        ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j3, textStyle2, function22, composerImpl2, i17);
                        composerImpl2.end(true);
                        composerImpl2.end(false);
                        biasAlignment = biasAlignment2;
                        z2 = false;
                    }
                } else {
                    function26 = function211;
                    function02 = function03;
                    composerImpl2 = composerImpl;
                    i8 = 8;
                    function27 = function212;
                    function28 = function210;
                    composerImpl2.startReplaceGroup(47556742);
                    Modifier modifierThen2 = PaddingKt.m127paddingVpY3zN4$default(LayoutIdKt.layoutId(companion2, obj), f3, 0.0f, 2).then(z ? SemanticsModifierKt.clearAndSetSemantics(companion2, new Function1() { // from class: androidx.compose.material3.AppBarKt$TopAppBarLayout$1$5
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                            return Unit.INSTANCE;
                        }
                    }) : companion2);
                    boolean z5 = (i7 & 14) == 4;
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    if (!z5) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.AppBarKt$TopAppBarLayout$1$6$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj2)).setAlpha(((Number) function0.invoke()).floatValue());
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue3);
                        }
                        Modifier modifierGraphicsLayer2 = GraphicsLayerModifierKt.graphicsLayer(modifierThen2, (Function1) objRememberedValue3);
                        biasAlignment = biasAlignment2;
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                        int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl2.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl2, modifierGraphicsLayer2);
                        composerImpl2.startReusableNode();
                        f2 = f3;
                        if (composerImpl2.inserting) {
                            composerImpl2.createNode(function02);
                        } else {
                            composerImpl2.useNode();
                        }
                        Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy2, function28);
                        Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope4, function26);
                        if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl2, currentCompositeKeyHash4, function27);
                        }
                        Updater.m337setimpl(composerImpl2, modifierMaterializeModifier4, function213);
                        ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j2, textStyle, function2, composerImpl2, ((i13 >> 9) & 14) | ((i13 >> 18) & 112) | ((i13 >> 12) & 896));
                        composerImpl2.end(true);
                        z2 = false;
                        composerImpl2.end(false);
                    }
                }
                Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(LayoutIdKt.layoutId(companion2, "actionIcons"), 0.0f, 0.0f, f2, 0.0f, 11);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z2);
                int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM129paddingqDBjuR0$default2);
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function02);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy3, function28);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope5, function26);
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl2, currentCompositeKeyHash5, function27);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier5, function213);
                function29 = function24;
                CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j4)), function29, composerImpl2, i8 | ((i7 >> 15) & 112));
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, true, true)) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Alignment.Horizontal horizontal3 = horizontal2;
            final Function2 function214 = function29;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.AppBarKt$TopAppBarLayout$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    AppBarKt.m249TopAppBarLayoutlyUyIHI(modifier, floatProducer, j, j2, j3, j4, function2, textStyle, function22, textStyle2, function0, vertical, horizontal3, i, z, function23, function214, f, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), RecomposeScopeImplKt.updateChangedFlags(i3));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$settleAppBar(final TopAppBarState topAppBarState, float f, ContinuationImpl continuationImpl) {
        AppBarKt$settleAppBar$1 appBarKt$settleAppBar$1;
        Ref$FloatRef ref$FloatRef;
        AnimationSpec animationSpec;
        Ref$FloatRef ref$FloatRef2;
        if (continuationImpl instanceof AppBarKt$settleAppBar$1) {
            appBarKt$settleAppBar$1 = (AppBarKt$settleAppBar$1) continuationImpl;
            int i = appBarKt$settleAppBar$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                appBarKt$settleAppBar$1.label = i - Integer.MIN_VALUE;
            } else {
                appBarKt$settleAppBar$1 = new AppBarKt$settleAppBar$1(continuationImpl);
            }
        }
        AppBarKt$settleAppBar$1 appBarKt$settleAppBar$12 = appBarKt$settleAppBar$1;
        Object obj = appBarKt$settleAppBar$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = appBarKt$settleAppBar$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (topAppBarState.getCollapsedFraction() < 0.01f || topAppBarState.getCollapsedFraction() == 1.0f) {
                Velocity.Companion.getClass();
                return Velocity.m878boximpl(0L);
            }
            ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef.element = f;
            animationSpec = null;
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$FloatRef2 = (Ref$FloatRef) appBarKt$settleAppBar$12.L$0;
                ResultKt.throwOnFailure(obj);
                ref$FloatRef = ref$FloatRef2;
                return Velocity.m878boximpl(VelocityKt.Velocity(0.0f, ref$FloatRef.element));
            }
            Ref$FloatRef ref$FloatRef3 = (Ref$FloatRef) appBarKt$settleAppBar$12.L$2;
            animationSpec = (AnimationSpec) appBarKt$settleAppBar$12.L$1;
            TopAppBarState topAppBarState2 = (TopAppBarState) appBarKt$settleAppBar$12.L$0;
            ResultKt.throwOnFailure(obj);
            ref$FloatRef = ref$FloatRef3;
            topAppBarState = topAppBarState2;
        }
        if (animationSpec != null && topAppBarState.getHeightOffset() < 0.0f && topAppBarState.getHeightOffset() > topAppBarState.heightOffsetLimit) {
            AnimationState animationStateAnimationState$default = AnimationStateKt.AnimationState$default(topAppBarState.getHeightOffset(), 0.0f, 30);
            Float f2 = new Float(topAppBarState.getCollapsedFraction() < 0.5f ? 0.0f : topAppBarState.heightOffsetLimit);
            Function1 function1 = new Function1() { // from class: androidx.compose.material3.AppBarKt$settleAppBar$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    TopAppBarState topAppBarState3 = topAppBarState;
                    float fFloatValue = ((Number) ((SnapshotMutableStateImpl) ((AnimationScope) obj2).value$delegate).getValue()).floatValue();
                    ((SnapshotMutableFloatStateImpl) topAppBarState3._heightOffset).setFloatValue(RangesKt___RangesKt.coerceIn(fFloatValue, topAppBarState3.heightOffsetLimit, 0.0f));
                    return Unit.INSTANCE;
                }
            };
            appBarKt$settleAppBar$12.L$0 = ref$FloatRef;
            appBarKt$settleAppBar$12.L$1 = null;
            appBarKt$settleAppBar$12.L$2 = null;
            appBarKt$settleAppBar$12.label = 2;
            if (SuspendAnimationKt.animateTo$default(animationStateAnimationState$default, f2, animationSpec, false, function1, appBarKt$settleAppBar$12, 4) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$FloatRef2 = ref$FloatRef;
            ref$FloatRef = ref$FloatRef2;
        }
        return Velocity.m878boximpl(VelocityKt.Velocity(0.0f, ref$FloatRef.element));
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final TopAppBarState rememberTopAppBarState(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.rememberTopAppBarState (AppBar.kt:1921)");
        }
        Object[] objArr = new Object[0];
        TopAppBarState.Companion.getClass();
        SaverKt$Saver$1 saverKt$Saver$1 = TopAppBarState.Saver;
        final float f = -3.4028235E38f;
        final float f2 = 0.0f;
        boolean zChanged = ((ComposerImpl) composer).changed(-3.4028235E38f) | ((ComposerImpl) composer).changed(0.0f) | ((ComposerImpl) composer).changed(0.0f);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function0() { // from class: androidx.compose.material3.AppBarKt$rememberTopAppBarState$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new TopAppBarState(f, f2, f2);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        TopAppBarState topAppBarState = (TopAppBarState) RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1, null, (Function0) objRememberedValue, composerImpl, 0, 4);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return topAppBarState;
    }
}
