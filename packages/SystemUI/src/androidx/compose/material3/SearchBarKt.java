package androidx.compose.material3;

import android.content.res.Resources;
import androidx.activity.BackEventCompat;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.foundation.shape.GenericShape;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.material3.internal.BackHandler_androidKt;
import androidx.compose.material3.internal.MutableWindowInsets;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.material3.tokens.SearchViewTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import com.android.systemui.util.DeviceState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class SearchBarKt {
    public static final TweenSpec AnimationEnterFloatSpec;
    public static final TweenSpec AnimationEnterSizeSpec = null;
    public static final TweenSpec AnimationExitFloatSpec;
    public static final TweenSpec AnimationExitSizeSpec = null;
    public static final TweenSpec AnimationPredictiveBackExitFloatSpec;
    public static final float SearchBarCornerRadius;
    public static final float SearchBarIconOffsetX;
    public static final float SearchBarMaxWidth;
    public static final float SearchBarMinWidth;
    public static final float SearchBarPredictiveBackMaxOffsetY;
    public static final float SearchBarPredictiveBackMinMargin;
    public static final float SearchBarVerticalPadding;
    public static final TextFieldColors UnspecifiedTextFieldColors;

    static {
        Color.Companion.getClass();
        long j = Color.Unspecified;
        UnspecifiedTextFieldColors = new TextFieldColors(j, j, j, j, j, j, j, j, j, j, new TextSelectionColors(j, j, null), j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, j, null);
        float f = 8;
        Dp.Companion companion = Dp.Companion;
        SearchBarDefaults.INSTANCE.getClass();
        SearchBarCornerRadius = SearchBarDefaults.InputFieldHeight / 2;
        SearchBarMinWidth = 360;
        SearchBarMaxWidth = DeviceState.CAPTURED_BLUR_THRESHOLD_WIDTH;
        SearchBarVerticalPadding = f;
        SearchBarIconOffsetX = 4;
        SearchBarPredictiveBackMinMargin = f;
        SearchBarPredictiveBackMaxOffsetY = 24;
        MotionTokens.INSTANCE.getClass();
        CubicBezierEasing cubicBezierEasing = MotionTokens.EasingEmphasizedDecelerateCubicBezier;
        CubicBezierEasing cubicBezierEasing2 = new CubicBezierEasing(0.0f, 1.0f, 0.0f, 1.0f);
        TweenSpec tweenSpec = new TweenSpec(VolteConstants.ErrorCode.BUSY_EVERYWHERE, 100, cubicBezierEasing);
        AnimationEnterFloatSpec = tweenSpec;
        TweenSpec tweenSpec2 = new TweenSpec(350, 100, cubicBezierEasing2);
        AnimationExitFloatSpec = tweenSpec2;
        AnimationPredictiveBackExitFloatSpec = AnimationSpecKt.tween$default(350, 0, cubicBezierEasing2, 2);
        TweenSpec tweenSpec3 = new TweenSpec(VolteConstants.ErrorCode.BUSY_EVERYWHERE, 100, cubicBezierEasing);
        TweenSpec tweenSpec4 = new TweenSpec(350, 100, cubicBezierEasing2);
        EnterExitTransitionKt.fadeIn$default(tweenSpec, 2).plus(EnterExitTransitionKt.expandVertically$default(tweenSpec3, null, null, 14));
        EnterExitTransitionKt.fadeOut$default(tweenSpec2, 2).plus(EnterExitTransitionKt.shrinkVertically$default(tweenSpec4, null, null, 14));
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:252:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0128  */
    /* renamed from: SearchBar-WuY5d9Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m286SearchBarWuY5d9Q(final String str, final Function1 function1, final Function1 function12, final boolean z, final Function1 function13, Modifier modifier, boolean z2, Function2 function2, Function2 function22, Function2 function23, Shape shape, SearchBarColors searchBarColors, float f, float f2, WindowInsets windowInsets, MutableInteractionSource mutableInteractionSource, final Function3 function3, Composer composer, final int i, final int i2, final int i3) throws Throwable {
        int i4;
        Function1 function14;
        int i5;
        Modifier modifier2;
        int i6;
        boolean z3;
        int i7;
        Function2 function24;
        int i8;
        Function2 function25;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        Shape inputFieldShape;
        Modifier modifier3;
        int i17;
        SearchBarColors searchBarColorsM284colorsKlgxPg;
        float f3;
        float f4;
        WindowInsets windowInsets2;
        final MutableInteractionSource mutableInteractionSource2;
        final boolean z4;
        float f5;
        final Function2 function26;
        Shape shape2;
        final Function2 function27;
        final Function2 function28;
        final SearchBarColors searchBarColors2;
        float f6;
        WindowInsets windowInsets3;
        final Modifier modifier4;
        final float f7;
        ComposerImpl composerImpl;
        final SearchBarColors searchBarColors3;
        final Function2 function29;
        final Function2 function210;
        final MutableInteractionSource mutableInteractionSource3;
        final float f8;
        final WindowInsets windowInsets4;
        final Function2 function211;
        final Shape shape3;
        final boolean z5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(385158066);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i3 & 2) != 0) {
            i4 |= 48;
        } else {
            if ((i & 48) == 0) {
                i4 |= composerImpl2.changedInstance(function1) ? 32 : 16;
            }
            if ((i3 & 4) == 0) {
                i4 |= 384;
            } else {
                if ((i & 384) == 0) {
                    i4 |= composerImpl2.changedInstance(function12) ? 256 : 128;
                }
                if ((i3 & 8) != 0) {
                    i4 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        i4 |= composerImpl2.changed(z) ? 2048 : 1024;
                    }
                    int i18 = 8192;
                    if ((i3 & 16) == 0) {
                        i4 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            function14 = function13;
                            i4 |= composerImpl2.changedInstance(function14) ? 16384 : 8192;
                        }
                        i5 = i3 & 32;
                        if (i5 != 0) {
                            i4 |= 196608;
                            modifier2 = modifier;
                        } else {
                            modifier2 = modifier;
                            if ((i & 196608) == 0) {
                                i4 |= composerImpl2.changed(modifier2) ? 131072 : 65536;
                            }
                        }
                        i6 = i3 & 64;
                        int i19 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        if (i6 != 0) {
                            i4 |= 1572864;
                            z3 = z2;
                        } else {
                            z3 = z2;
                            if ((i & 1572864) == 0) {
                                i4 |= composerImpl2.changed(z3) ? 1048576 : 524288;
                            }
                        }
                        i7 = i3 & 128;
                        if (i7 != 0) {
                            i4 |= 12582912;
                            function24 = function2;
                        } else {
                            function24 = function2;
                            if ((i & 12582912) == 0) {
                                i4 |= composerImpl2.changedInstance(function24) ? 8388608 : 4194304;
                            }
                        }
                        i8 = i3 & 256;
                        if (i8 != 0) {
                            i4 |= 100663296;
                            function25 = function22;
                        } else {
                            function25 = function22;
                            if ((i & 100663296) == 0) {
                                i4 |= composerImpl2.changedInstance(function25) ? 67108864 : 33554432;
                            }
                        }
                        i9 = i3 & 512;
                        if (i9 != 0) {
                            i4 |= 805306368;
                        } else {
                            if ((i & 805306368) == 0) {
                                i10 = i9;
                                i4 |= composerImpl2.changedInstance(function23) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            if ((i2 & 6) != 0) {
                                i11 = i2 | (((i3 & 1024) == 0 && composerImpl2.changed(shape)) ? 4 : 2);
                            } else {
                                i11 = i2;
                            }
                            if ((i2 & 48) == 0) {
                                i11 |= ((i3 & 2048) == 0 && composerImpl2.changed(searchBarColors)) ? 32 : 16;
                            }
                            int i20 = i11;
                            i12 = i3 & 4096;
                            if (i12 == 0) {
                                i13 = i20 | 384;
                            } else {
                                int i21 = i20;
                                if ((i2 & 384) == 0) {
                                    i21 |= composerImpl2.changed(f) ? 256 : 128;
                                }
                                i13 = i21;
                            }
                            i14 = i3 & 8192;
                            if (i14 != 0) {
                                i15 = i13;
                                if ((i2 & 3072) == 0) {
                                    i15 |= composerImpl2.changed(f2) ? 2048 : 1024;
                                }
                                if ((i2 & 24576) == 0) {
                                    if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0 && composerImpl2.changed(windowInsets)) {
                                        i18 = 16384;
                                    }
                                    i15 |= i18;
                                }
                                i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                                if (i16 != 0) {
                                    i15 |= 196608;
                                } else if ((i2 & 196608) == 0) {
                                    i15 |= composerImpl2.changed(mutableInteractionSource) ? 131072 : 65536;
                                }
                                if ((i3 & 65536) != 0) {
                                    i15 |= 1572864;
                                } else if ((i2 & 1572864) == 0) {
                                    if (composerImpl2.changedInstance(function3)) {
                                        i19 = 1048576;
                                    }
                                    i15 |= i19;
                                }
                                if ((i4 & 306783379) == 306783378 && (i15 & 599187) == 599186 && composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    function210 = function23;
                                    searchBarColors3 = searchBarColors;
                                    f7 = f2;
                                    mutableInteractionSource3 = mutableInteractionSource;
                                    modifier4 = modifier2;
                                    z5 = z3;
                                    function29 = function24;
                                    composerImpl = composerImpl2;
                                    function211 = function25;
                                    shape3 = shape;
                                    f8 = f;
                                    windowInsets4 = windowInsets;
                                } else {
                                    composerImpl2.startDefaults();
                                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                        Modifier modifier5 = i5 == 0 ? Modifier.Companion : modifier2;
                                        boolean z6 = i6 == 0 ? true : z3;
                                        if (i7 != 0) {
                                            function24 = null;
                                        }
                                        if (i8 != 0) {
                                            function25 = null;
                                        }
                                        Function2 function212 = i10 == 0 ? null : function23;
                                        if ((i3 & 1024) == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            inputFieldShape = SearchBarDefaults.getInputFieldShape(composerImpl2);
                                            i15 &= -15;
                                        } else {
                                            inputFieldShape = shape;
                                        }
                                        if ((i3 & 2048) == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            i17 = i4;
                                            modifier3 = modifier5;
                                            searchBarColorsM284colorsKlgxPg = SearchBarDefaults.m284colorsKlgxPg(0L, composerImpl2, 3072, 7);
                                            i15 &= -113;
                                        } else {
                                            modifier3 = modifier5;
                                            i17 = i4;
                                            searchBarColorsM284colorsKlgxPg = searchBarColors;
                                        }
                                        if (i12 == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            f3 = SearchBarDefaults.TonalElevation;
                                        } else {
                                            f3 = f;
                                        }
                                        if (i14 == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            f4 = SearchBarDefaults.ShadowElevation;
                                        } else {
                                            f4 = f2;
                                        }
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            windowInsets2 = SearchBarDefaults.getWindowInsets(composerImpl2);
                                            i15 &= -57345;
                                        } else {
                                            windowInsets2 = windowInsets;
                                        }
                                        if (i16 == 0) {
                                            z4 = z6;
                                            f5 = f4;
                                            function26 = function212;
                                            shape2 = inputFieldShape;
                                            function27 = function24;
                                            function28 = function25;
                                            mutableInteractionSource2 = null;
                                        } else {
                                            mutableInteractionSource2 = mutableInteractionSource;
                                            z4 = z6;
                                            f5 = f4;
                                            function26 = function212;
                                            shape2 = inputFieldShape;
                                            function27 = function24;
                                            function28 = function25;
                                        }
                                        searchBarColors2 = searchBarColorsM284colorsKlgxPg;
                                        f6 = f3;
                                        windowInsets3 = windowInsets2;
                                        modifier4 = modifier3;
                                    } else {
                                        composerImpl2.skipToGroupEnd();
                                        if ((i3 & 1024) != 0) {
                                            i15 &= -15;
                                        }
                                        if ((i3 & 2048) != 0) {
                                            i15 &= -113;
                                        }
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                            i15 &= -57345;
                                        }
                                        function26 = function23;
                                        shape2 = shape;
                                        f6 = f;
                                        f5 = f2;
                                        mutableInteractionSource2 = mutableInteractionSource;
                                        i17 = i4;
                                        z4 = z3;
                                        function27 = function24;
                                        function28 = function25;
                                        searchBarColors2 = searchBarColors;
                                        windowInsets3 = windowInsets;
                                        modifier4 = modifier2;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBar (SearchBar.kt:1973)");
                                    }
                                    final Function1 function15 = function14;
                                    int i22 = i17 >> 6;
                                    int i23 = i15 << 12;
                                    SearchBarColors searchBarColors4 = searchBarColors2;
                                    m287SearchBarY92LkZI(ComposableLambdaKt.rememberComposableLambda(555674834, new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBar$6
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBar.<anonymous> (SearchBar.kt:1975)");
                                                    }
                                                    SearchBarDefaults.INSTANCE.InputField(str, function1, function12, z, function15, SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), z4, function27, function28, function26, searchBarColors2.inputFieldColors, mutableInteractionSource2, composer2, 196608, 384, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), z, function13, modifier4, shape2, searchBarColors4, f6, f5, windowInsets3, function3, composerImpl2, (i22 & 7168) | (i22 & 112) | 6 | (i22 & 896) | (57344 & i23) | (458752 & i23) | (3670016 & i23) | (29360128 & i23) | (i23 & 234881024) | ((i15 << 9) & 1879048192), 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    f7 = f5;
                                    composerImpl = composerImpl2;
                                    searchBarColors3 = searchBarColors4;
                                    function29 = function27;
                                    function210 = function26;
                                    mutableInteractionSource3 = mutableInteractionSource2;
                                    f8 = f6;
                                    windowInsets4 = windowInsets3;
                                    function211 = function28;
                                    shape3 = shape2;
                                    z5 = z4;
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBar$7
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) throws Throwable {
                                            ((Number) obj2).intValue();
                                            SearchBarKt.m286SearchBarWuY5d9Q(str, function1, function12, z, function13, modifier4, z5, function29, function211, function210, shape3, searchBarColors3, f8, f7, windowInsets4, mutableInteractionSource3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i15 = i13 | 3072;
                            if ((i2 & 24576) == 0) {
                            }
                            i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                            if (i16 != 0) {
                            }
                            if ((i3 & 65536) != 0) {
                            }
                            if ((i4 & 306783379) == 306783378) {
                                composerImpl2.startDefaults();
                                if ((i & 1) != 0) {
                                    if (i5 == 0) {
                                    }
                                    if (i6 == 0) {
                                    }
                                    if (i7 != 0) {
                                    }
                                    if (i8 != 0) {
                                    }
                                    if (i10 == 0) {
                                    }
                                    if ((i3 & 1024) == 0) {
                                    }
                                    if ((i3 & 2048) == 0) {
                                    }
                                    if (i12 == 0) {
                                    }
                                    if (i14 == 0) {
                                    }
                                    if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                    }
                                    if (i16 == 0) {
                                    }
                                    searchBarColors2 = searchBarColorsM284colorsKlgxPg;
                                    f6 = f3;
                                    windowInsets3 = windowInsets2;
                                    modifier4 = modifier3;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    final Function1 function152 = function14;
                                    int i222 = i17 >> 6;
                                    int i232 = i15 << 12;
                                    SearchBarColors searchBarColors42 = searchBarColors2;
                                    m287SearchBarY92LkZI(ComposableLambdaKt.rememberComposableLambda(555674834, new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBar$6
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBar.<anonymous> (SearchBar.kt:1975)");
                                                    }
                                                    SearchBarDefaults.INSTANCE.InputField(str, function1, function12, z, function152, SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), z4, function27, function28, function26, searchBarColors2.inputFieldColors, mutableInteractionSource2, composer2, 196608, 384, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), z, function13, modifier4, shape2, searchBarColors42, f6, f5, windowInsets3, function3, composerImpl2, (i222 & 7168) | (i222 & 112) | 6 | (i222 & 896) | (57344 & i232) | (458752 & i232) | (3670016 & i232) | (29360128 & i232) | (i232 & 234881024) | ((i15 << 9) & 1879048192), 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    f7 = f5;
                                    composerImpl = composerImpl2;
                                    searchBarColors3 = searchBarColors42;
                                    function29 = function27;
                                    function210 = function26;
                                    mutableInteractionSource3 = mutableInteractionSource2;
                                    f8 = f6;
                                    windowInsets4 = windowInsets3;
                                    function211 = function28;
                                    shape3 = shape2;
                                    z5 = z4;
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i10 = i9;
                        if ((i2 & 6) != 0) {
                        }
                        if ((i2 & 48) == 0) {
                        }
                        int i202 = i11;
                        i12 = i3 & 4096;
                        if (i12 == 0) {
                        }
                        i14 = i3 & 8192;
                        if (i14 != 0) {
                        }
                        if ((i2 & 24576) == 0) {
                        }
                        i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i16 != 0) {
                        }
                        if ((i3 & 65536) != 0) {
                        }
                        if ((i4 & 306783379) == 306783378) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    function14 = function13;
                    i5 = i3 & 32;
                    if (i5 != 0) {
                    }
                    i6 = i3 & 64;
                    int i192 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    if (i6 != 0) {
                    }
                    i7 = i3 & 128;
                    if (i7 != 0) {
                    }
                    i8 = i3 & 256;
                    if (i8 != 0) {
                    }
                    i9 = i3 & 512;
                    if (i9 != 0) {
                    }
                    i10 = i9;
                    if ((i2 & 6) != 0) {
                    }
                    if ((i2 & 48) == 0) {
                    }
                    int i2022 = i11;
                    i12 = i3 & 4096;
                    if (i12 == 0) {
                    }
                    i14 = i3 & 8192;
                    if (i14 != 0) {
                    }
                    if ((i2 & 24576) == 0) {
                    }
                    i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i16 != 0) {
                    }
                    if ((i3 & 65536) != 0) {
                    }
                    if ((i4 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                int i182 = 8192;
                if ((i3 & 16) == 0) {
                }
                function14 = function13;
                i5 = i3 & 32;
                if (i5 != 0) {
                }
                i6 = i3 & 64;
                int i1922 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i6 != 0) {
                }
                i7 = i3 & 128;
                if (i7 != 0) {
                }
                i8 = i3 & 256;
                if (i8 != 0) {
                }
                i9 = i3 & 512;
                if (i9 != 0) {
                }
                i10 = i9;
                if ((i2 & 6) != 0) {
                }
                if ((i2 & 48) == 0) {
                }
                int i20222 = i11;
                i12 = i3 & 4096;
                if (i12 == 0) {
                }
                i14 = i3 & 8192;
                if (i14 != 0) {
                }
                if ((i2 & 24576) == 0) {
                }
                i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i16 != 0) {
                }
                if ((i3 & 65536) != 0) {
                }
                if ((i4 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            if ((i3 & 8) != 0) {
            }
            int i1822 = 8192;
            if ((i3 & 16) == 0) {
            }
            function14 = function13;
            i5 = i3 & 32;
            if (i5 != 0) {
            }
            i6 = i3 & 64;
            int i19222 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i6 != 0) {
            }
            i7 = i3 & 128;
            if (i7 != 0) {
            }
            i8 = i3 & 256;
            if (i8 != 0) {
            }
            i9 = i3 & 512;
            if (i9 != 0) {
            }
            i10 = i9;
            if ((i2 & 6) != 0) {
            }
            if ((i2 & 48) == 0) {
            }
            int i202222 = i11;
            i12 = i3 & 4096;
            if (i12 == 0) {
            }
            i14 = i3 & 8192;
            if (i14 != 0) {
            }
            if ((i2 & 24576) == 0) {
            }
            i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i16 != 0) {
            }
            if ((i3 & 65536) != 0) {
            }
            if ((i4 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        if ((i3 & 4) == 0) {
        }
        if ((i3 & 8) != 0) {
        }
        int i18222 = 8192;
        if ((i3 & 16) == 0) {
        }
        function14 = function13;
        i5 = i3 & 32;
        if (i5 != 0) {
        }
        i6 = i3 & 64;
        int i192222 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i6 != 0) {
        }
        i7 = i3 & 128;
        if (i7 != 0) {
        }
        i8 = i3 & 256;
        if (i8 != 0) {
        }
        i9 = i3 & 512;
        if (i9 != 0) {
        }
        i10 = i9;
        if ((i2 & 6) != 0) {
        }
        if ((i2 & 48) == 0) {
        }
        int i2022222 = i11;
        i12 = i3 & 4096;
        if (i12 == 0) {
        }
        i14 = i3 & 8192;
        if (i14 != 0) {
        }
        if ((i2 & 24576) == 0) {
        }
        i16 = i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i16 != 0) {
        }
        if ((i3 & 65536) != 0) {
        }
        if ((i4 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x026f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:199:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0104  */
    /* renamed from: SearchBar-Y92LkZI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m287SearchBarY92LkZI(final Function2 function2, boolean z, final Function1 function1, Modifier modifier, Shape shape, SearchBarColors searchBarColors, float f, float f2, WindowInsets windowInsets, final Function3 function3, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        Function1 function12;
        final Modifier modifier2;
        final Shape inputFieldShape;
        final SearchBarColors searchBarColorsM284colorsKlgxPg;
        int i4;
        float f3;
        int i5;
        float f4;
        WindowInsets windowInsets2;
        int i6;
        int i7;
        WindowInsets windowInsets3;
        int i8;
        Modifier modifier3;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        CoroutineScope coroutineScope;
        Object objRememberedValue2;
        Animatable animatable;
        Object objRememberedValue3;
        MutableFloatState mutableFloatState;
        Object objRememberedValue4;
        MutableState mutableState;
        Object objRememberedValue5;
        MutableState mutableState2;
        Boolean boolValueOf;
        boolean zChangedInstance;
        Object objRememberedValue6;
        Boolean bool;
        Modifier modifier4;
        int i9;
        Object objRememberedValue7;
        MutatorMutex mutatorMutex;
        int i10;
        Object objRememberedValue8;
        Animatable animatable2;
        int i11;
        ComposerImpl composerImpl;
        final float f5;
        final float f6;
        final WindowInsets windowInsets4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final boolean z2 = z;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1256554518);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(z2) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
            function12 = function1;
        } else {
            function12 = function1;
            if ((i & 384) == 0) {
                i3 |= composerImpl2.changedInstance(function12) ? 256 : 128;
            }
        }
        int i12 = i2 & 8;
        if (i12 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                if ((i2 & 16) == 0) {
                    inputFieldShape = shape;
                    int i13 = composerImpl2.changed(inputFieldShape) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    i3 |= i13;
                } else {
                    inputFieldShape = shape;
                }
                i3 |= i13;
            } else {
                inputFieldShape = shape;
            }
            if ((196608 & i) != 0) {
                if ((i2 & 32) == 0) {
                    searchBarColorsM284colorsKlgxPg = searchBarColors;
                    int i14 = composerImpl2.changed(searchBarColorsM284colorsKlgxPg) ? 131072 : 65536;
                    i3 |= i14;
                } else {
                    searchBarColorsM284colorsKlgxPg = searchBarColors;
                }
                i3 |= i14;
            } else {
                searchBarColorsM284colorsKlgxPg = searchBarColors;
            }
            i4 = i2 & 64;
            if (i4 == 0) {
                i3 |= 1572864;
            } else {
                if ((1572864 & i) == 0) {
                    f3 = f;
                    i3 |= composerImpl2.changed(f3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                i5 = i2 & 128;
                if (i5 != 0) {
                    i3 |= 12582912;
                    f4 = f2;
                } else {
                    f4 = f2;
                    if ((i & 12582912) == 0) {
                        i3 |= composerImpl2.changed(f4) ? 8388608 : 4194304;
                    }
                }
                if ((i & 100663296) == 0) {
                    if ((i2 & 256) == 0) {
                        windowInsets2 = windowInsets;
                        int i15 = composerImpl2.changed(windowInsets2) ? 67108864 : 33554432;
                        i3 |= i15;
                    } else {
                        windowInsets2 = windowInsets;
                    }
                    i3 |= i15;
                } else {
                    windowInsets2 = windowInsets;
                }
                i6 = i3;
                if ((i2 & 512) == 0) {
                    if ((i & 805306368) == 0) {
                        i6 |= composerImpl2.changedInstance(function3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((i6 & 306783379) == 306783378 || !composerImpl2.getSkipping()) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i12 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i2 & 16) != 0) {
                                SearchBarDefaults.INSTANCE.getClass();
                                i6 &= -57345;
                                inputFieldShape = SearchBarDefaults.getInputFieldShape(composerImpl2);
                            }
                            if ((i2 & 32) == 0) {
                                SearchBarDefaults.INSTANCE.getClass();
                                i7 = -234881025;
                                searchBarColorsM284colorsKlgxPg = SearchBarDefaults.m284colorsKlgxPg(0L, composerImpl2, 3072, 7);
                                i6 &= -458753;
                            } else {
                                i7 = -234881025;
                            }
                            if (i4 != 0) {
                                SearchBarDefaults.INSTANCE.getClass();
                                f3 = SearchBarDefaults.TonalElevation;
                            }
                            if (i5 != 0) {
                                SearchBarDefaults.INSTANCE.getClass();
                                f4 = SearchBarDefaults.ShadowElevation;
                            }
                            if ((i2 & 256) == 0) {
                                SearchBarDefaults.INSTANCE.getClass();
                                i6 &= i7;
                                windowInsets3 = SearchBarDefaults.getWindowInsets(composerImpl2);
                            }
                            Shape shape2 = inputFieldShape;
                            SearchBarColors searchBarColors2 = searchBarColorsM284colorsKlgxPg;
                            float f7 = f4;
                            float f8 = f3;
                            i8 = i6;
                            modifier3 = modifier2;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.SearchBar (SearchBar.kt:552)");
                            }
                            objRememberedValue = composerImpl2.rememberedValue();
                            Composer.Companion.getClass();
                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue == composer$Companion$Empty$1) {
                                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2));
                                composerImpl2.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                                objRememberedValue = compositionScopedCoroutineScopeCanceller;
                            }
                            coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
                            objRememberedValue2 = composerImpl2.rememberedValue();
                            if (objRememberedValue2 == composer$Companion$Empty$1) {
                                objRememberedValue2 = AnimatableKt.Animatable(z ? 1.0f : 0.0f, 0.01f);
                                composerImpl2.updateRememberedValue(objRememberedValue2);
                            }
                            animatable = (Animatable) objRememberedValue2;
                            objRememberedValue3 = composerImpl2.rememberedValue();
                            if (objRememberedValue3 == composer$Companion$Empty$1) {
                                objRememberedValue3 = PrimitiveSnapshotStateKt.mutableFloatStateOf(Float.NaN);
                                composerImpl2.updateRememberedValue(objRememberedValue3);
                            }
                            mutableFloatState = (MutableFloatState) objRememberedValue3;
                            objRememberedValue4 = composerImpl2.rememberedValue();
                            if (objRememberedValue4 == composer$Companion$Empty$1) {
                                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(null);
                                composerImpl2.updateRememberedValue(objRememberedValue4);
                            }
                            mutableState = (MutableState) objRememberedValue4;
                            objRememberedValue5 = composerImpl2.rememberedValue();
                            if (objRememberedValue5 == composer$Companion$Empty$1) {
                                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(null);
                                composerImpl2.updateRememberedValue(objRememberedValue5);
                            }
                            mutableState2 = (MutableState) objRememberedValue5;
                            boolValueOf = Boolean.valueOf(z);
                            zChangedInstance = composerImpl2.changedInstance(animatable) | ((i8 & 112) == 32);
                            objRememberedValue6 = composerImpl2.rememberedValue();
                            if (zChangedInstance || objRememberedValue6 == composer$Companion$Empty$1) {
                                bool = boolValueOf;
                                modifier4 = modifier3;
                                z2 = z;
                                i9 = 0;
                                SearchBarKt$SearchBar$3$1 searchBarKt$SearchBar$3$1 = new SearchBarKt$SearchBar$3$1(animatable, z2, mutableFloatState, mutableState, mutableState2, null);
                                composerImpl2.updateRememberedValue(searchBarKt$SearchBar$3$1);
                                objRememberedValue6 = searchBarKt$SearchBar$3$1;
                            } else {
                                z2 = z;
                                bool = boolValueOf;
                                modifier4 = modifier3;
                                i9 = 0;
                            }
                            int i16 = (i8 >> 3) & 14;
                            EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue6);
                            objRememberedValue7 = composerImpl2.rememberedValue();
                            if (objRememberedValue7 == composer$Companion$Empty$1) {
                                objRememberedValue7 = new MutatorMutex();
                                composerImpl2.updateRememberedValue(objRememberedValue7);
                            }
                            mutatorMutex = (MutatorMutex) objRememberedValue7;
                            i10 = (composerImpl2.changedInstance(animatable) ? 1 : 0) | ((i8 & 896) != 256 ? i9 : 1) | (composerImpl2.changedInstance(coroutineScope) ? 1 : 0);
                            objRememberedValue8 = composerImpl2.rememberedValue();
                            if (i10 == 0 || objRememberedValue8 == composer$Companion$Empty$1) {
                                animatable2 = animatable;
                                i11 = i8;
                                SearchBarKt$SearchBar$4$1 searchBarKt$SearchBar$4$1 = new SearchBarKt$SearchBar$4$1(mutatorMutex, mutableFloatState, animatable2, function12, coroutineScope, mutableState, mutableState2, null);
                                composerImpl2.updateRememberedValue(searchBarKt$SearchBar$4$1);
                                objRememberedValue8 = searchBarKt$SearchBar$4$1;
                            } else {
                                animatable2 = animatable;
                                i11 = i8;
                            }
                            BackHandler_androidKt.PredictiveBackHandler(z2, (Function2) objRememberedValue8, composerImpl2, i16, i9);
                            int i17 = i11 << 6;
                            Modifier modifier5 = modifier4;
                            WindowInsets windowInsets5 = windowInsets3;
                            composerImpl = composerImpl2;
                            m288SearchBarImplj1jLAyQ(animatable2, mutableFloatState, mutableState, mutableState2, modifier5, function2, shape2, searchBarColors2, f8, f7, windowInsets5, function3, composerImpl, (57344 & (i11 << 3)) | 3512 | ((i11 << 15) & 458752) | (3670016 & i17) | (29360128 & i17) | (234881024 & i17) | (i17 & 1879048192), (i11 >> 24) & 126, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier2 = modifier5;
                            inputFieldShape = shape2;
                            searchBarColorsM284colorsKlgxPg = searchBarColors2;
                            f5 = f8;
                            f6 = f7;
                            windowInsets4 = windowInsets5;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 16) != 0) {
                                i6 &= -57345;
                            }
                            if ((i2 & 32) != 0) {
                                i6 &= -458753;
                            }
                            if ((i2 & 256) != 0) {
                                i6 &= -234881025;
                            }
                        }
                        windowInsets3 = windowInsets2;
                        Shape shape22 = inputFieldShape;
                        SearchBarColors searchBarColors22 = searchBarColorsM284colorsKlgxPg;
                        float f72 = f4;
                        float f82 = f3;
                        i8 = i6;
                        modifier3 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        objRememberedValue = composerImpl2.rememberedValue();
                        Composer.Companion.getClass();
                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                        }
                        coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
                        objRememberedValue2 = composerImpl2.rememberedValue();
                        if (objRememberedValue2 == composer$Companion$Empty$1) {
                        }
                        animatable = (Animatable) objRememberedValue2;
                        objRememberedValue3 = composerImpl2.rememberedValue();
                        if (objRememberedValue3 == composer$Companion$Empty$1) {
                        }
                        mutableFloatState = (MutableFloatState) objRememberedValue3;
                        objRememberedValue4 = composerImpl2.rememberedValue();
                        if (objRememberedValue4 == composer$Companion$Empty$1) {
                        }
                        mutableState = (MutableState) objRememberedValue4;
                        objRememberedValue5 = composerImpl2.rememberedValue();
                        if (objRememberedValue5 == composer$Companion$Empty$1) {
                        }
                        mutableState2 = (MutableState) objRememberedValue5;
                        boolValueOf = Boolean.valueOf(z);
                        zChangedInstance = composerImpl2.changedInstance(animatable) | ((i8 & 112) == 32);
                        objRememberedValue6 = composerImpl2.rememberedValue();
                        if (zChangedInstance) {
                            bool = boolValueOf;
                            modifier4 = modifier3;
                            z2 = z;
                            i9 = 0;
                            SearchBarKt$SearchBar$3$1 searchBarKt$SearchBar$3$12 = new SearchBarKt$SearchBar$3$1(animatable, z2, mutableFloatState, mutableState, mutableState2, null);
                            composerImpl2.updateRememberedValue(searchBarKt$SearchBar$3$12);
                            objRememberedValue6 = searchBarKt$SearchBar$3$12;
                            int i162 = (i8 >> 3) & 14;
                            EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue6);
                            objRememberedValue7 = composerImpl2.rememberedValue();
                            if (objRememberedValue7 == composer$Companion$Empty$1) {
                            }
                            mutatorMutex = (MutatorMutex) objRememberedValue7;
                            i10 = (composerImpl2.changedInstance(animatable) ? 1 : 0) | ((i8 & 896) != 256 ? i9 : 1) | (composerImpl2.changedInstance(coroutineScope) ? 1 : 0);
                            objRememberedValue8 = composerImpl2.rememberedValue();
                            if (i10 == 0) {
                                animatable2 = animatable;
                                i11 = i8;
                                SearchBarKt$SearchBar$4$1 searchBarKt$SearchBar$4$12 = new SearchBarKt$SearchBar$4$1(mutatorMutex, mutableFloatState, animatable2, function12, coroutineScope, mutableState, mutableState2, null);
                                composerImpl2.updateRememberedValue(searchBarKt$SearchBar$4$12);
                                objRememberedValue8 = searchBarKt$SearchBar$4$12;
                                BackHandler_androidKt.PredictiveBackHandler(z2, (Function2) objRememberedValue8, composerImpl2, i162, i9);
                                int i172 = i11 << 6;
                                Modifier modifier52 = modifier4;
                                WindowInsets windowInsets52 = windowInsets3;
                                composerImpl = composerImpl2;
                                m288SearchBarImplj1jLAyQ(animatable2, mutableFloatState, mutableState, mutableState2, modifier52, function2, shape22, searchBarColors22, f82, f72, windowInsets52, function3, composerImpl, (57344 & (i11 << 3)) | 3512 | ((i11 << 15) & 458752) | (3670016 & i172) | (29360128 & i172) | (234881024 & i172) | (i172 & 1879048192), (i11 >> 24) & 126, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                modifier2 = modifier52;
                                inputFieldShape = shape22;
                                searchBarColorsM284colorsKlgxPg = searchBarColors22;
                                f5 = f82;
                                f6 = f72;
                                windowInsets4 = windowInsets52;
                            }
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                        composerImpl = composerImpl2;
                        f6 = f4;
                        f5 = f3;
                        windowInsets4 = windowInsets2;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBar$5
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) throws Throwable {
                                ((Number) obj2).intValue();
                                SearchBarKt.m287SearchBarY92LkZI(function2, z2, function1, modifier2, inputFieldShape, searchBarColorsM284colorsKlgxPg, f5, f6, windowInsets4, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i6 |= 805306368;
                if ((i6 & 306783379) == 306783378) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i12 != 0) {
                        }
                        if ((i2 & 16) != 0) {
                        }
                        if ((i2 & 32) == 0) {
                        }
                        if (i4 != 0) {
                        }
                        if (i5 != 0) {
                        }
                        if ((i2 & 256) == 0) {
                            windowInsets3 = windowInsets2;
                        }
                        Shape shape222 = inputFieldShape;
                        SearchBarColors searchBarColors222 = searchBarColorsM284colorsKlgxPg;
                        float f722 = f4;
                        float f822 = f3;
                        i8 = i6;
                        modifier3 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        objRememberedValue = composerImpl2.rememberedValue();
                        Composer.Companion.getClass();
                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                        }
                        coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
                        objRememberedValue2 = composerImpl2.rememberedValue();
                        if (objRememberedValue2 == composer$Companion$Empty$1) {
                        }
                        animatable = (Animatable) objRememberedValue2;
                        objRememberedValue3 = composerImpl2.rememberedValue();
                        if (objRememberedValue3 == composer$Companion$Empty$1) {
                        }
                        mutableFloatState = (MutableFloatState) objRememberedValue3;
                        objRememberedValue4 = composerImpl2.rememberedValue();
                        if (objRememberedValue4 == composer$Companion$Empty$1) {
                        }
                        mutableState = (MutableState) objRememberedValue4;
                        objRememberedValue5 = composerImpl2.rememberedValue();
                        if (objRememberedValue5 == composer$Companion$Empty$1) {
                        }
                        mutableState2 = (MutableState) objRememberedValue5;
                        boolValueOf = Boolean.valueOf(z);
                        zChangedInstance = composerImpl2.changedInstance(animatable) | ((i8 & 112) == 32);
                        objRememberedValue6 = composerImpl2.rememberedValue();
                        if (zChangedInstance) {
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            f3 = f;
            i5 = i2 & 128;
            if (i5 != 0) {
            }
            if ((i & 100663296) == 0) {
            }
            i6 = i3;
            if ((i2 & 512) == 0) {
            }
            if ((i6 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        if ((i & 24576) != 0) {
        }
        if ((196608 & i) != 0) {
        }
        i4 = i2 & 64;
        if (i4 == 0) {
        }
        f3 = f;
        i5 = i2 & 128;
        if (i5 != 0) {
        }
        if ((i & 100663296) == 0) {
        }
        i6 = i3;
        if ((i2 & 512) == 0) {
        }
        if ((i6 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x02c0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:226:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0101  */
    /* renamed from: SearchBarImpl-j1jLAyQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m288SearchBarImplj1jLAyQ(final Animatable animatable, final MutableFloatState mutableFloatState, final MutableState mutableState, final MutableState mutableState2, Modifier modifier, final Function2 function2, Shape shape, SearchBarColors searchBarColors, float f, float f2, WindowInsets windowInsets, final Function3 function3, Composer composer, final int i, final int i2, final int i3) throws Throwable {
        int i4;
        int i5;
        Modifier modifier2;
        Function2 function22;
        Shape inputFieldShape;
        SearchBarColors searchBarColors2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        Modifier modifier3;
        final SearchBarColors searchBarColorsM284colorsKlgxPg;
        float f3;
        float f4;
        WindowInsets windowInsets2;
        Shape shape2;
        final float f5;
        final float f6;
        int i11;
        final Density density;
        Shape inputFieldShape2;
        Shape value;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        WindowInsets windowInsets3;
        State state;
        boolean zChanged;
        Object objRememberedValue2;
        Object objRememberedValue3;
        ComposableLambdaImpl composableLambdaImplRememberComposableLambda;
        ComposerImpl composerImpl;
        final Shape shape3;
        final SearchBarColors searchBarColors3;
        final float f7;
        final float f8;
        final WindowInsets windowInsets4;
        final Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-153434528);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = ((i & 8) == 0 ? composerImpl2.changed(animatable) : composerImpl2.changedInstance(animatable) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i3 & 2) != 0) {
            i4 |= 48;
        } else {
            if ((i & 48) == 0) {
                i4 |= composerImpl2.changed(mutableFloatState) ? 32 : 16;
            }
            if ((i3 & 4) == 0) {
                i4 |= 384;
            } else {
                if ((i & 384) == 0) {
                    i4 |= composerImpl2.changed(mutableState) ? 256 : 128;
                }
                if ((i3 & 8) != 0) {
                    i4 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        i4 |= composerImpl2.changed(mutableState2) ? 2048 : 1024;
                    }
                    i5 = i3 & 16;
                    if (i5 == 0) {
                        i4 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            modifier2 = modifier;
                            i4 |= composerImpl2.changed(modifier2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if ((i3 & 32) != 0) {
                            i4 |= 196608;
                        } else {
                            if ((i & 196608) == 0) {
                                function22 = function2;
                                i4 |= composerImpl2.changedInstance(function22) ? 131072 : 65536;
                            }
                            if ((i & 1572864) != 0) {
                                inputFieldShape = shape;
                                i4 |= ((i3 & 64) == 0 && composerImpl2.changed(inputFieldShape)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            } else {
                                inputFieldShape = shape;
                            }
                            if ((i & 12582912) != 0) {
                                if ((i3 & 128) == 0) {
                                    searchBarColors2 = searchBarColors;
                                    int i12 = composerImpl2.changed(searchBarColors2) ? 8388608 : 4194304;
                                    i4 |= i12;
                                } else {
                                    searchBarColors2 = searchBarColors;
                                }
                                i4 |= i12;
                            } else {
                                searchBarColors2 = searchBarColors;
                            }
                            i6 = i3 & 256;
                            if (i6 == 0) {
                                i4 |= 100663296;
                            } else if ((i & 100663296) == 0) {
                                i4 |= composerImpl2.changed(f) ? 67108864 : 33554432;
                            }
                            i7 = i4;
                            i8 = i3 & 512;
                            if (i8 != 0) {
                                if ((i & 805306368) == 0) {
                                    i9 = i8;
                                    i7 |= composerImpl2.changed(f2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                }
                                if ((i2 & 6) == 0) {
                                    i10 = i2 | (((i3 & 1024) == 0 && composerImpl2.changed(windowInsets)) ? 4 : 2);
                                } else {
                                    i10 = i2;
                                }
                                if ((i3 & 2048) != 0) {
                                    i10 |= 48;
                                } else if ((i2 & 48) == 0) {
                                    i10 |= composerImpl2.changedInstance(function3) ? 32 : 16;
                                }
                                if ((i7 & 306783379) == 306783378 && (i10 & 19) == 18 && composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    f8 = f2;
                                    shape3 = inputFieldShape;
                                    searchBarColors3 = searchBarColors2;
                                    composerImpl = composerImpl2;
                                    modifier4 = modifier2;
                                    f7 = f;
                                    windowInsets4 = windowInsets;
                                } else {
                                    composerImpl2.startDefaults();
                                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                        modifier3 = i5 == 0 ? Modifier.Companion : modifier2;
                                        if ((i3 & 64) != 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            inputFieldShape = SearchBarDefaults.getInputFieldShape(composerImpl2);
                                            i7 &= -3670017;
                                        }
                                        if ((i3 & 128) == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            searchBarColorsM284colorsKlgxPg = SearchBarDefaults.m284colorsKlgxPg(0L, composerImpl2, 3072, 7);
                                            i7 &= -29360129;
                                        } else {
                                            searchBarColorsM284colorsKlgxPg = searchBarColors2;
                                        }
                                        if (i6 == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            f3 = SearchBarDefaults.TonalElevation;
                                        } else {
                                            f3 = f;
                                        }
                                        if (i9 == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            f4 = SearchBarDefaults.ShadowElevation;
                                        } else {
                                            f4 = f2;
                                        }
                                        if ((i3 & 1024) == 0) {
                                            SearchBarDefaults.INSTANCE.getClass();
                                            windowInsets2 = SearchBarDefaults.getWindowInsets(composerImpl2);
                                            i10 &= -15;
                                        } else {
                                            windowInsets2 = windowInsets;
                                        }
                                        shape2 = inputFieldShape;
                                        f5 = f3;
                                        f6 = f4;
                                        i11 = i7;
                                    } else {
                                        composerImpl2.skipToGroupEnd();
                                        if ((i3 & 64) != 0) {
                                            i7 &= -3670017;
                                        }
                                        if ((i3 & 128) != 0) {
                                            i7 &= -29360129;
                                        }
                                        if ((i3 & 1024) != 0) {
                                            i10 &= -15;
                                        }
                                        windowInsets2 = windowInsets;
                                        searchBarColorsM284colorsKlgxPg = searchBarColors2;
                                        modifier3 = modifier2;
                                        i11 = i7;
                                        f6 = f2;
                                        shape2 = inputFieldShape;
                                        f5 = f;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBarImpl (SearchBar.kt:2099)");
                                    }
                                    Modifier modifier5 = modifier3;
                                    density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                                    SearchBarDefaults.INSTANCE.getClass();
                                    inputFieldShape2 = SearchBarDefaults.getInputFieldShape(composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.<get-fullScreenShape> (SearchBar.kt:1054)");
                                    }
                                    SearchViewTokens.INSTANCE.getClass();
                                    value = ShapesKt.getValue(SearchViewTokens.FullScreenContainerShape, composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    objRememberedValue = composerImpl2.rememberedValue();
                                    Composer.Companion.getClass();
                                    composer$Companion$Empty$1 = Composer.Companion.Empty;
                                    if (objRememberedValue != composer$Companion$Empty$1) {
                                        windowInsets3 = windowInsets2;
                                        objRememberedValue = SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$useFullScreenShape$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return Boolean.valueOf(((Number) animatable.internalState.getValue()).floatValue() == 1.0f);
                                            }
                                        });
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    } else {
                                        windowInsets3 = windowInsets2;
                                    }
                                    state = (State) objRememberedValue;
                                    zChanged = composerImpl2.changed(((Boolean) state.getValue()).booleanValue()) | ((((i11 & 3670016) ^ 1572864) <= 1048576 && composerImpl2.changed(shape2)) || (i11 & 1572864) == 1048576);
                                    objRememberedValue2 = composerImpl2.rememberedValue();
                                    if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                                        Shape genericShape = !Intrinsics.areEqual(shape2, inputFieldShape2) ? new GenericShape(new Function3() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$animatedShape$1$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                long j = ((Size) obj2).packedValue;
                                                Density density2 = density;
                                                float fFloatValue = (1 - ((Number) animatable.internalState.getValue()).floatValue()) * SearchBarKt.SearchBarCornerRadius;
                                                Dp.Companion companion = Dp.Companion;
                                                float fMo58toPx0680j_4 = density2.mo58toPx0680j_4(fFloatValue);
                                                Rect rectM423toRectuvyYCjk = androidx.compose.ui.geometry.SizeKt.m423toRectuvyYCjk(j);
                                                long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_4) & 4294967295L);
                                                CornerRadius.Companion companion2 = CornerRadius.Companion;
                                                float fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32));
                                                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
                                                long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
                                                Path.addRoundRect$default((Path) obj, new RoundRect(rectM423toRectuvyYCjk.left, rectM423toRectuvyYCjk.top, rectM423toRectuvyYCjk.right, rectM423toRectuvyYCjk.bottom, jFloatToRawIntBits2, jFloatToRawIntBits2, jFloatToRawIntBits2, jFloatToRawIntBits2, null));
                                                return Unit.INSTANCE;
                                            }
                                        }) : ((Boolean) state.getValue()).booleanValue() ? value : shape2;
                                        composerImpl2.updateRememberedValue(genericShape);
                                        objRememberedValue2 = genericShape;
                                    }
                                    final Shape shape4 = (Shape) objRememberedValue2;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(1161490571, new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$surface$1
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
                                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBarImpl.<anonymous> (SearchBar.kt:2125)");
                                                    }
                                                    Shape shape5 = shape4;
                                                    long j = searchBarColorsM284colorsKlgxPg.containerColor;
                                                    long jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j, composer2);
                                                    float f9 = f5;
                                                    float f10 = f6;
                                                    ComposableSingletons$SearchBarKt.INSTANCE.getClass();
                                                    SurfaceKt.m304SurfaceT9BRK9s(null, shape5, j, jM259contentColorForek8zF_U, f9, f10, null, ComposableSingletons$SearchBarKt.f14lambda1, composer2, 12582912, 65);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2);
                                    objRememberedValue3 = composerImpl2.rememberedValue();
                                    if (objRememberedValue3 == composer$Companion$Empty$1) {
                                        objRememberedValue3 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$showContent$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return Boolean.valueOf(((Number) animatable.internalState.getValue()).floatValue() > 0.0f);
                                            }
                                        });
                                        composerImpl2.updateRememberedValue(objRememberedValue3);
                                    }
                                    if (((Boolean) ((State) objRememberedValue3).getValue()).booleanValue()) {
                                        composerImpl2.startReplaceGroup(-1279224875);
                                        composerImpl2.end(false);
                                        composableLambdaImplRememberComposableLambda = null;
                                    } else {
                                        composerImpl2.startReplaceGroup(-1279460629);
                                        composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1287216371, new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$wrappedContent$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
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
                                                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarImpl.<anonymous> (SearchBar.kt:2141)");
                                                        }
                                                        Modifier.Companion companion = Modifier.Companion;
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                        boolean zChangedInstance = composerImpl4.changedInstance(animatable);
                                                        final Animatable<Float, AnimationVector1D> animatable2 = animatable;
                                                        Object objRememberedValue4 = composerImpl4.rememberedValue();
                                                        if (!zChangedInstance) {
                                                            Composer.Companion.getClass();
                                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                                objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$wrappedContent$1$1$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj3) {
                                                                        ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj3)).setAlpha(((Number) animatable2.internalState.getValue()).floatValue());
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                };
                                                                composerImpl4.updateRememberedValue(objRememberedValue4);
                                                            }
                                                            Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(companion, (Function1) objRememberedValue4);
                                                            SearchBarColors searchBarColors4 = searchBarColorsM284colorsKlgxPg;
                                                            Function3 function32 = function3;
                                                            Arrangement.INSTANCE.getClass();
                                                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                                            Alignment.Companion.getClass();
                                                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl4, 0);
                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierGraphicsLayer);
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
                                                            Updater.m337setimpl(composerImpl4, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                            Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function23);
                                                            }
                                                            Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                                            DividerKt.m263HorizontalDivider9IZ8Weo(null, 0.0f, searchBarColors4.dividerColor, composerImpl4, 0, 3);
                                                            function32.invoke(columnScopeInstance, composerImpl4, 6);
                                                            composerImpl4.end(true);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl2);
                                        composerImpl2.end(false);
                                    }
                                    int i13 = (i11 & 14) | 12582920 | (i11 & 112) | (i11 & 896) | (i11 & 7168) | (57344 & i11) | ((i10 << 15) & 458752) | ((i11 << 3) & 3670016);
                                    float f9 = f5;
                                    float f10 = f6;
                                    Function2 function23 = function22;
                                    WindowInsets windowInsets5 = windowInsets3;
                                    SearchBarColors searchBarColors4 = searchBarColorsM284colorsKlgxPg;
                                    SearchBarLayout(animatable, mutableFloatState, mutableState, mutableState2, modifier5, windowInsets5, function23, composableLambdaImplRememberComposableLambda2, composableLambdaImplRememberComposableLambda, composerImpl2, i13);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl = composerImpl2;
                                    shape3 = shape2;
                                    searchBarColors3 = searchBarColors4;
                                    f7 = f9;
                                    f8 = f10;
                                    windowInsets4 = windowInsets5;
                                    modifier4 = modifier5;
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) throws Throwable {
                                            ((Number) obj2).intValue();
                                            SearchBarKt.m288SearchBarImplj1jLAyQ(animatable, mutableFloatState, mutableState, mutableState2, modifier4, function2, shape3, searchBarColors3, f7, f8, windowInsets4, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i7 |= 805306368;
                            i9 = i8;
                            if ((i2 & 6) == 0) {
                            }
                            if ((i3 & 2048) != 0) {
                            }
                            if ((i7 & 306783379) == 306783378) {
                                composerImpl2.startDefaults();
                                if ((i & 1) != 0) {
                                    if (i5 == 0) {
                                    }
                                    if ((i3 & 64) != 0) {
                                    }
                                    if ((i3 & 128) == 0) {
                                    }
                                    if (i6 == 0) {
                                    }
                                    if (i9 == 0) {
                                    }
                                    if ((i3 & 1024) == 0) {
                                    }
                                    shape2 = inputFieldShape;
                                    f5 = f3;
                                    f6 = f4;
                                    i11 = i7;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    Modifier modifier52 = modifier3;
                                    density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                                    SearchBarDefaults.INSTANCE.getClass();
                                    inputFieldShape2 = SearchBarDefaults.getInputFieldShape(composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    SearchViewTokens.INSTANCE.getClass();
                                    value = ShapesKt.getValue(SearchViewTokens.FullScreenContainerShape, composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    objRememberedValue = composerImpl2.rememberedValue();
                                    Composer.Companion.getClass();
                                    composer$Companion$Empty$1 = Composer.Companion.Empty;
                                    if (objRememberedValue != composer$Companion$Empty$1) {
                                    }
                                    state = (State) objRememberedValue;
                                    if (((i11 & 3670016) ^ 1572864) <= 1048576) {
                                        zChanged = composerImpl2.changed(((Boolean) state.getValue()).booleanValue()) | ((((i11 & 3670016) ^ 1572864) <= 1048576 && composerImpl2.changed(shape2)) || (i11 & 1572864) == 1048576);
                                        objRememberedValue2 = composerImpl2.rememberedValue();
                                        if (zChanged) {
                                            if (!Intrinsics.areEqual(shape2, inputFieldShape2)) {
                                            }
                                            composerImpl2.updateRememberedValue(genericShape);
                                            objRememberedValue2 = genericShape;
                                            final Shape shape42 = (Shape) objRememberedValue2;
                                            ComposableLambdaImpl composableLambdaImplRememberComposableLambda22 = ComposableLambdaKt.rememberComposableLambda(1161490571, new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBarImpl$surface$1
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
                                                                ComposerKt.traceEventStart("androidx.compose.material3.SearchBarImpl.<anonymous> (SearchBar.kt:2125)");
                                                            }
                                                            Shape shape5 = shape42;
                                                            long j = searchBarColorsM284colorsKlgxPg.containerColor;
                                                            long jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j, composer2);
                                                            float f92 = f5;
                                                            float f102 = f6;
                                                            ComposableSingletons$SearchBarKt.INSTANCE.getClass();
                                                            SurfaceKt.m304SurfaceT9BRK9s(null, shape5, j, jM259contentColorForek8zF_U, f92, f102, null, ComposableSingletons$SearchBarKt.f14lambda1, composer2, 12582912, 65);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl2);
                                            objRememberedValue3 = composerImpl2.rememberedValue();
                                            if (objRememberedValue3 == composer$Companion$Empty$1) {
                                            }
                                            if (((Boolean) ((State) objRememberedValue3).getValue()).booleanValue()) {
                                            }
                                            int i132 = (i11 & 14) | 12582920 | (i11 & 112) | (i11 & 896) | (i11 & 7168) | (57344 & i11) | ((i10 << 15) & 458752) | ((i11 << 3) & 3670016);
                                            float f92 = f5;
                                            float f102 = f6;
                                            Function2 function232 = function22;
                                            WindowInsets windowInsets52 = windowInsets3;
                                            SearchBarColors searchBarColors42 = searchBarColorsM284colorsKlgxPg;
                                            SearchBarLayout(animatable, mutableFloatState, mutableState, mutableState2, modifier52, windowInsets52, function232, composableLambdaImplRememberComposableLambda22, composableLambdaImplRememberComposableLambda, composerImpl2, i132);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl = composerImpl2;
                                            shape3 = shape2;
                                            searchBarColors3 = searchBarColors42;
                                            f7 = f92;
                                            f8 = f102;
                                            windowInsets4 = windowInsets52;
                                            modifier4 = modifier52;
                                        }
                                    } else {
                                        zChanged = composerImpl2.changed(((Boolean) state.getValue()).booleanValue()) | ((((i11 & 3670016) ^ 1572864) <= 1048576 && composerImpl2.changed(shape2)) || (i11 & 1572864) == 1048576);
                                        objRememberedValue2 = composerImpl2.rememberedValue();
                                        if (zChanged) {
                                        }
                                    }
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        function22 = function2;
                        if ((i & 1572864) != 0) {
                        }
                        if ((i & 12582912) != 0) {
                        }
                        i6 = i3 & 256;
                        if (i6 == 0) {
                        }
                        i7 = i4;
                        i8 = i3 & 512;
                        if (i8 != 0) {
                        }
                        i9 = i8;
                        if ((i2 & 6) == 0) {
                        }
                        if ((i3 & 2048) != 0) {
                        }
                        if ((i7 & 306783379) == 306783378) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    modifier2 = modifier;
                    if ((i3 & 32) != 0) {
                    }
                    function22 = function2;
                    if ((i & 1572864) != 0) {
                    }
                    if ((i & 12582912) != 0) {
                    }
                    i6 = i3 & 256;
                    if (i6 == 0) {
                    }
                    i7 = i4;
                    i8 = i3 & 512;
                    if (i8 != 0) {
                    }
                    i9 = i8;
                    if ((i2 & 6) == 0) {
                    }
                    if ((i3 & 2048) != 0) {
                    }
                    if ((i7 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                i5 = i3 & 16;
                if (i5 == 0) {
                }
                modifier2 = modifier;
                if ((i3 & 32) != 0) {
                }
                function22 = function2;
                if ((i & 1572864) != 0) {
                }
                if ((i & 12582912) != 0) {
                }
                i6 = i3 & 256;
                if (i6 == 0) {
                }
                i7 = i4;
                i8 = i3 & 512;
                if (i8 != 0) {
                }
                i9 = i8;
                if ((i2 & 6) == 0) {
                }
                if ((i3 & 2048) != 0) {
                }
                if ((i7 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            if ((i3 & 8) != 0) {
            }
            i5 = i3 & 16;
            if (i5 == 0) {
            }
            modifier2 = modifier;
            if ((i3 & 32) != 0) {
            }
            function22 = function2;
            if ((i & 1572864) != 0) {
            }
            if ((i & 12582912) != 0) {
            }
            i6 = i3 & 256;
            if (i6 == 0) {
            }
            i7 = i4;
            i8 = i3 & 512;
            if (i8 != 0) {
            }
            i9 = i8;
            if ((i2 & 6) == 0) {
            }
            if ((i3 & 2048) != 0) {
            }
            if ((i7 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        if ((i3 & 4) == 0) {
        }
        if ((i3 & 8) != 0) {
        }
        i5 = i3 & 16;
        if (i5 == 0) {
        }
        modifier2 = modifier;
        if ((i3 & 32) != 0) {
        }
        function22 = function2;
        if ((i & 1572864) != 0) {
        }
        if ((i & 12582912) != 0) {
        }
        i6 = i3 & 256;
        if (i6 == 0) {
        }
        i7 = i4;
        i8 = i3 & 512;
        if (i8 != 0) {
        }
        i9 = i8;
        if ((i2 & 6) == 0) {
        }
        if ((i3 & 2048) != 0) {
        }
        if ((i7 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    public static final void SearchBarLayout(final Animatable animatable, final MutableFloatState mutableFloatState, final MutableState mutableState, final MutableState mutableState2, final Modifier modifier, final WindowInsets windowInsets, final Function2 function2, final Function2 function22, final Function2 function23, Composer composer, final int i) throws Throwable {
        int i2;
        MutableFloatState mutableFloatState2;
        final MutableState mutableState3;
        MutableState mutableState4;
        boolean z;
        Throwable th;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(510810397);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(animatable) : composerImpl.changedInstance(animatable) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            mutableFloatState2 = mutableFloatState;
            i2 |= composerImpl.changed(mutableFloatState2) ? 32 : 16;
        } else {
            mutableFloatState2 = mutableFloatState;
        }
        if ((i & 384) == 0) {
            mutableState3 = mutableState;
            i2 |= composerImpl.changed(mutableState3) ? 256 : 128;
        } else {
            mutableState3 = mutableState;
        }
        if ((i & 3072) == 0) {
            mutableState4 = mutableState2;
            i2 |= composerImpl.changed(mutableState4) ? 2048 : 1024;
        } else {
            mutableState4 = mutableState2;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(windowInsets) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 8388608 : 4194304;
        }
        if ((100663296 & i) == 0) {
            i2 |= composerImpl.changedInstance(function23) ? 67108864 : 33554432;
        }
        int i3 = i2;
        if ((38347923 & i3) == 38347922 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SearchBarLayout (SearchBar.kt:2173)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new MutableWindowInsets(null, 1, null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableWindowInsets mutableWindowInsets = (MutableWindowInsets) objRememberedValue;
            Modifier modifierZIndex = ZIndexModifierKt.zIndex(modifier, 1.0f);
            boolean z3 = (i3 & 458752) == 131072;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (z3 || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MutableWindowInsets mutableWindowInsets2 = mutableWindowInsets;
                        ((SnapshotMutableStateImpl) mutableWindowInsets2.insets$delegate).setValue(WindowInsetsKt.exclude(windowInsets, (WindowInsets) obj));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            Modifier modifierConsumeWindowInsets = WindowInsetsPaddingKt.consumeWindowInsets(WindowInsetsPaddingKt.onConsumedWindowInsetsChanged(modifierZIndex, (Function1) objRememberedValue2), windowInsets);
            boolean z4 = ((i3 & 7168) == 2048) | ((i3 & 14) == 4 || ((i3 & 8) != 0 && composerImpl.changedInstance(animatable))) | ((i3 & 112) == 32) | ((i3 & 896) == 256);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (z4 || objRememberedValue3 == composer$Companion$Empty$1) {
                final MutableFloatState mutableFloatState3 = mutableFloatState2;
                final MutableState mutableState5 = mutableState4;
                z = true;
                th = null;
                MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$2$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, final long j) {
                        Object obj;
                        Placeable placeable;
                        final Placeable placeableMo610measureBRTryo0;
                        int iM822getMaxHeightimpl;
                        long j2 = j;
                        final float fFloatValue = ((Number) animatable.internalState.getValue()).floatValue();
                        int size = list.size();
                        int i4 = 0;
                        while (i4 < size) {
                            Measurable measurable = (Measurable) list.get(i4);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable), "InputField")) {
                                int size2 = list.size();
                                int i5 = 0;
                                while (i5 < size2) {
                                    Measurable measurable2 = (Measurable) list.get(i5);
                                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable2), "Surface")) {
                                        int size3 = list.size();
                                        int i6 = 0;
                                        while (true) {
                                            if (i6 >= size3) {
                                                obj = null;
                                                break;
                                            }
                                            obj = list.get(i6);
                                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj), "Content")) {
                                                break;
                                            }
                                            i6++;
                                        }
                                        Measurable measurable3 = (Measurable) obj;
                                        int top = mutableWindowInsets.getTop(measureScope);
                                        float f = SearchBarKt.SearchBarVerticalPadding;
                                        final int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(f) + top;
                                        int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(f);
                                        int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(measurable.maxIntrinsicWidth(Constraints.m822getMaxHeightimpl(j2)), j2);
                                        int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(measurable.minIntrinsicHeight(Constraints.m823getMaxWidthimpl(j2)), j2);
                                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(Constraints.m823getMaxWidthimpl(j2) * 0.9f);
                                        int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(Constraints.m822getMaxHeightimpl(j2) * 0.9f);
                                        BackEventCompat backEventCompat = (BackEventCompat) mutableState5.getValue();
                                        float floatValue = ((SnapshotMutableFloatStateImpl) mutableFloatState3).getFloatValue();
                                        float f2 = 0.0f;
                                        if (backEventCompat != null) {
                                            if (Float.isNaN(floatValue)) {
                                                f2 = 1.0f;
                                            } else if (floatValue > 0.0f) {
                                                f2 = fFloatValue / floatValue;
                                            }
                                        }
                                        final float f3 = f2;
                                        int iLerp = MathHelpersKt.lerp(f3, iM834constrainWidthK40F9xA, iRoundToInt);
                                        int i7 = iMo52roundToPx0680j_4 + iM833constrainHeightK40F9xA;
                                        int iLerp2 = MathHelpersKt.lerp(f3, i7, iRoundToInt2);
                                        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j2);
                                        int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(j2);
                                        int iLerp3 = MathHelpersKt.lerp(fFloatValue, iLerp, iM823getMaxWidthimpl);
                                        final int iLerp4 = MathHelpersKt.lerp(fFloatValue, iLerp2, iM822getMaxHeightimpl2);
                                        final int iLerp5 = MathHelpersKt.lerp(fFloatValue, iMo52roundToPx0680j_4, 0);
                                        final int iLerp6 = MathHelpersKt.lerp(fFloatValue, 0, iMo52roundToPx0680j_42);
                                        final Placeable placeableMo610measureBRTryo02 = measurable.mo610measureBRTryo0(ConstraintsKt.Constraints(iLerp3, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA, iM833constrainHeightK40F9xA));
                                        int i8 = placeableMo610measureBRTryo02.width;
                                        Constraints.Companion.getClass();
                                        Placeable placeableMo610measureBRTryo03 = measurable2.mo610measureBRTryo0(Constraints.Companion.m829fixedJhjzzOo(i8, iLerp4 - iLerp5));
                                        if (measurable3 != null) {
                                            if (Constraints.m818getHasBoundedHeightimpl(j2)) {
                                                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j2) - (i7 + iMo52roundToPx0680j_42);
                                                if (iM822getMaxHeightimpl < 0) {
                                                    iM822getMaxHeightimpl = 0;
                                                }
                                            } else {
                                                iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j2);
                                            }
                                            placeable = placeableMo610measureBRTryo03;
                                            placeableMo610measureBRTryo0 = measurable3.mo610measureBRTryo0(ConstraintsKt.Constraints(i8, i8, 0, iM822getMaxHeightimpl));
                                        } else {
                                            placeable = placeableMo610measureBRTryo03;
                                            placeableMo610measureBRTryo0 = null;
                                        }
                                        final MutableState mutableState6 = mutableState5;
                                        final MutableState mutableState7 = mutableState3;
                                        final Placeable placeable2 = placeable;
                                        return measureScope.layout$1(i8, iLerp4, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$2$1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj2) {
                                                int iRoundToInt3;
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj2;
                                                int iMo52roundToPx0680j_43 = measureScope.mo52roundToPx0680j_4(SearchBarKt.SearchBarPredictiveBackMinMargin);
                                                long j3 = j;
                                                BackEventCompat backEventCompat2 = (BackEventCompat) mutableState6.getValue();
                                                LayoutDirection layoutDirection = measureScope.getLayoutDirection();
                                                float f4 = fFloatValue;
                                                float f5 = f3;
                                                int iRoundToInt4 = 0;
                                                if (backEventCompat2 == null || f5 == 0.0f) {
                                                    iRoundToInt3 = 0;
                                                } else {
                                                    iRoundToInt3 = MathKt__MathJVMKt.roundToInt((1 - f4) * ((Constraints.m823getMaxWidthimpl(j3) * 0.05f) - iMo52roundToPx0680j_43) * f5 * (backEventCompat2.swipeEdge == 0 ? 1 : -1) * (layoutDirection == LayoutDirection.Ltr ? 1 : -1));
                                                }
                                                long j4 = j;
                                                BackEventCompat backEventCompat3 = (BackEventCompat) mutableState6.getValue();
                                                BackEventCompat backEventCompat4 = (BackEventCompat) mutableState7.getValue();
                                                int i9 = iLerp4;
                                                int iMo52roundToPx0680j_44 = measureScope.mo52roundToPx0680j_4(SearchBarKt.SearchBarPredictiveBackMaxOffsetY);
                                                float f6 = f3;
                                                if (backEventCompat4 != null && backEventCompat3 != null && f6 != 0.0f) {
                                                    int iMin = Math.min(Math.max(0, ((Constraints.m822getMaxHeightimpl(j4) - i9) / 2) - iMo52roundToPx0680j_43), iMo52roundToPx0680j_44);
                                                    iRoundToInt4 = MathKt__MathJVMKt.roundToInt(MathHelpersKt.lerp(Math.abs(r4) / Constraints.m822getMaxHeightimpl(j4), 0, iMin) * f6 * Math.signum(backEventCompat3.touchY - backEventCompat4.touchY));
                                                }
                                                placementScope.placeRelative(placeable2, iRoundToInt3, iLerp5 + iRoundToInt4, 0.0f);
                                                placementScope.placeRelative(placeableMo610measureBRTryo02, iRoundToInt3, iMo52roundToPx0680j_4 + iRoundToInt4, 0.0f);
                                                Placeable placeable3 = placeableMo610measureBRTryo0;
                                                if (placeable3 != null) {
                                                    placementScope.placeRelative(placeable3, iRoundToInt3, iRoundToInt4 + iMo52roundToPx0680j_4 + placeableMo610measureBRTryo02.height + iLerp6, 0.0f);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        });
                                    }
                                    i5++;
                                    j2 = j;
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                            i4++;
                            j2 = j;
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerImpl.updateRememberedValue(measurePolicy);
                objRememberedValue3 = measurePolicy;
            } else {
                z = true;
                th = null;
            }
            MeasurePolicy measurePolicy2 = (MeasurePolicy) objRememberedValue3;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierConsumeWindowInsets);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw th;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Function2 function24 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, measurePolicy2, function24);
            Function2 function25 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function25);
            Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function26);
            }
            Function2 function27 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function27);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierLayoutId = LayoutIdKt.layoutId(companion, "Surface");
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function24);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function25);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function26);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function27);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function22.invoke(composerImpl, Integer.valueOf((i3 >> 21) & 14));
            composerImpl.end(true);
            Modifier modifierLayoutId2 = LayoutIdKt.layoutId(companion, "InputField");
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function24);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function25);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function26);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function27);
            function2.invoke(composerImpl, Integer.valueOf((i3 >> 18) & 14));
            composerImpl.end(true);
            if (function23 == null) {
                composerImpl.startReplaceGroup(96147618);
                composerImpl.end(false);
                z2 = true;
            } else {
                composerImpl.startReplaceGroup(96147619);
                Modifier modifierLayoutId3 = LayoutIdKt.layoutId(companion, "Content");
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId3);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function24);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function25);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function26);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function27);
                function23.invoke(composerImpl, 0);
                z2 = true;
                composerImpl.end(true);
                Unit unit = Unit.INSTANCE;
                composerImpl.end(false);
            }
            composerImpl.end(z2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarKt.SearchBarLayout.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    SearchBarKt.SearchBarLayout(animatable, mutableFloatState, mutableState, mutableState2, modifier, windowInsets, function2, function22, function23, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
