package androidx.compose.material3;

import androidx.activity.BackEventCompat;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.material3.internal.MutableWindowInsets;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import com.android.systemui.util.DeviceState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:103:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0251  */
    /* renamed from: SearchBar-WuY5d9Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m285SearchBarWuY5d9Q(final java.lang.String r36, final kotlin.jvm.functions.Function1 r37, final kotlin.jvm.functions.Function1 r38, final boolean r39, final kotlin.jvm.functions.Function1 r40, androidx.compose.ui.Modifier r41, boolean r42, kotlin.jvm.functions.Function2 r43, kotlin.jvm.functions.Function2 r44, kotlin.jvm.functions.Function2 r45, androidx.compose.ui.graphics.Shape r46, androidx.compose.material3.SearchBarColors r47, float r48, float r49, androidx.compose.foundation.layout.WindowInsets r50, androidx.compose.foundation.interaction.MutableInteractionSource r51, final kotlin.jvm.functions.Function3 r52, androidx.compose.runtime.Composer r53, final int r54, final int r55, final int r56) {
        /*
            Method dump skipped, instructions count: 953
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SearchBarKt.m285SearchBarWuY5d9Q(java.lang.String, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.ui.graphics.Shape, androidx.compose.material3.SearchBarColors, float, float, androidx.compose.foundation.layout.WindowInsets, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x026f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02c1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0264  */
    /* renamed from: SearchBar-Y92LkZI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m286SearchBarY92LkZI(final kotlin.jvm.functions.Function2 r29, boolean r30, final kotlin.jvm.functions.Function1 r31, androidx.compose.ui.Modifier r32, androidx.compose.ui.graphics.Shape r33, androidx.compose.material3.SearchBarColors r34, float r35, float r36, androidx.compose.foundation.layout.WindowInsets r37, final kotlin.jvm.functions.Function3 r38, androidx.compose.runtime.Composer r39, final int r40, final int r41) {
        /*
            Method dump skipped, instructions count: 850
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SearchBarKt.m286SearchBarY92LkZI(kotlin.jvm.functions.Function2, boolean, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.ui.graphics.Shape, androidx.compose.material3.SearchBarColors, float, float, androidx.compose.foundation.layout.WindowInsets, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x02c0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02ab  */
    /* renamed from: SearchBarImpl-j1jLAyQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m287SearchBarImplj1jLAyQ(final androidx.compose.animation.core.Animatable r25, final androidx.compose.runtime.MutableFloatState r26, final androidx.compose.runtime.MutableState r27, final androidx.compose.runtime.MutableState r28, androidx.compose.ui.Modifier r29, final kotlin.jvm.functions.Function2 r30, androidx.compose.ui.graphics.Shape r31, androidx.compose.material3.SearchBarColors r32, float r33, float r34, androidx.compose.foundation.layout.WindowInsets r35, final kotlin.jvm.functions.Function3 r36, androidx.compose.runtime.Composer r37, final int r38, final int r39, final int r40) {
        /*
            Method dump skipped, instructions count: 929
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SearchBarKt.m287SearchBarImplj1jLAyQ(androidx.compose.animation.core.Animatable, androidx.compose.runtime.MutableFloatState, androidx.compose.runtime.MutableState, androidx.compose.runtime.MutableState, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, androidx.compose.ui.graphics.Shape, androidx.compose.material3.SearchBarColors, float, float, androidx.compose.foundation.layout.WindowInsets, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int, int):void");
    }

    public static final void SearchBarLayout(final Animatable animatable, final MutableFloatState mutableFloatState, final MutableState mutableState, final MutableState mutableState2, final Modifier modifier, final WindowInsets windowInsets, final Function2 function2, final Function2 function22, final Function2 function23, Composer composer, final int i) {
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new MutableWindowInsets(null, 1, null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableWindowInsets mutableWindowInsets = (MutableWindowInsets) rememberedValue;
            Modifier zIndex = ZIndexModifierKt.zIndex(modifier, 1.0f);
            boolean z3 = (i3 & 458752) == 131072;
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (z3 || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function1() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        MutableWindowInsets mutableWindowInsets2 = MutableWindowInsets.this;
                        ((SnapshotMutableStateImpl) mutableWindowInsets2.insets$delegate).setValue(WindowInsetsKt.exclude(windowInsets, (WindowInsets) obj));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            Modifier consumeWindowInsets = WindowInsetsPaddingKt.consumeWindowInsets(WindowInsetsPaddingKt.onConsumedWindowInsetsChanged(zIndex, (Function1) rememberedValue2), windowInsets);
            boolean z4 = ((i3 & 7168) == 2048) | ((i3 & 14) == 4 || ((i3 & 8) != 0 && composerImpl.changedInstance(animatable))) | ((i3 & 112) == 32) | ((i3 & 896) == 256);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (z4 || rememberedValue3 == composer$Companion$Empty$1) {
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
                        final Placeable placeable2;
                        MeasureResult layout$1;
                        int m820getMaxHeightimpl;
                        long j2 = j;
                        final float floatValue = ((Number) animatable.internalState.getValue()).floatValue();
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
                                        final int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(f) + top;
                                        int mo51roundToPx0680j_42 = measureScope.mo51roundToPx0680j_4(f);
                                        int m832constrainWidthK40F9xA = ConstraintsKt.m832constrainWidthK40F9xA(measurable.maxIntrinsicWidth(Constraints.m820getMaxHeightimpl(j2)), j2);
                                        int m831constrainHeightK40F9xA = ConstraintsKt.m831constrainHeightK40F9xA(measurable.minIntrinsicHeight(Constraints.m821getMaxWidthimpl(j2)), j2);
                                        int roundToInt = MathKt__MathJVMKt.roundToInt(Constraints.m821getMaxWidthimpl(j2) * 0.9f);
                                        int roundToInt2 = MathKt__MathJVMKt.roundToInt(Constraints.m820getMaxHeightimpl(j2) * 0.9f);
                                        BackEventCompat backEventCompat = (BackEventCompat) mutableState5.getValue();
                                        float floatValue2 = ((SnapshotMutableFloatStateImpl) mutableFloatState3).getFloatValue();
                                        float f2 = 0.0f;
                                        if (backEventCompat != null) {
                                            if (Float.isNaN(floatValue2)) {
                                                f2 = 1.0f;
                                            } else if (floatValue2 > 0.0f) {
                                                f2 = floatValue / floatValue2;
                                            }
                                        }
                                        final float f3 = f2;
                                        int lerp = MathHelpersKt.lerp(f3, m832constrainWidthK40F9xA, roundToInt);
                                        int i7 = mo51roundToPx0680j_4 + m831constrainHeightK40F9xA;
                                        int lerp2 = MathHelpersKt.lerp(f3, i7, roundToInt2);
                                        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j2);
                                        int m820getMaxHeightimpl2 = Constraints.m820getMaxHeightimpl(j2);
                                        int lerp3 = MathHelpersKt.lerp(floatValue, lerp, m821getMaxWidthimpl);
                                        final int lerp4 = MathHelpersKt.lerp(floatValue, lerp2, m820getMaxHeightimpl2);
                                        final int lerp5 = MathHelpersKt.lerp(floatValue, mo51roundToPx0680j_4, 0);
                                        final int lerp6 = MathHelpersKt.lerp(floatValue, 0, mo51roundToPx0680j_42);
                                        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(ConstraintsKt.Constraints(lerp3, m821getMaxWidthimpl, m831constrainHeightK40F9xA, m831constrainHeightK40F9xA));
                                        int i8 = mo608measureBRTryo0.width;
                                        Constraints.Companion.getClass();
                                        Placeable mo608measureBRTryo02 = measurable2.mo608measureBRTryo0(Constraints.Companion.m827fixedJhjzzOo(i8, lerp4 - lerp5));
                                        if (measurable3 != null) {
                                            if (Constraints.m816getHasBoundedHeightimpl(j2)) {
                                                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j2) - (i7 + mo51roundToPx0680j_42);
                                                if (m820getMaxHeightimpl < 0) {
                                                    m820getMaxHeightimpl = 0;
                                                }
                                            } else {
                                                m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j2);
                                            }
                                            placeable = mo608measureBRTryo02;
                                            placeable2 = measurable3.mo608measureBRTryo0(ConstraintsKt.Constraints(i8, i8, 0, m820getMaxHeightimpl));
                                        } else {
                                            placeable = mo608measureBRTryo02;
                                            placeable2 = null;
                                        }
                                        final MutableState mutableState6 = mutableState5;
                                        final MutableState mutableState7 = mutableState3;
                                        final Placeable placeable3 = placeable;
                                        layout$1 = measureScope.layout$1(i8, lerp4, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$2$1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj2) {
                                                int i9;
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj2;
                                                int mo51roundToPx0680j_43 = MeasureScope.this.mo51roundToPx0680j_4(SearchBarKt.SearchBarPredictiveBackMinMargin);
                                                long j3 = j;
                                                BackEventCompat backEventCompat2 = (BackEventCompat) mutableState6.getValue();
                                                LayoutDirection layoutDirection = MeasureScope.this.getLayoutDirection();
                                                float f4 = floatValue;
                                                float f5 = f3;
                                                int i10 = 0;
                                                if (backEventCompat2 == null || f5 == 0.0f) {
                                                    i9 = 0;
                                                } else {
                                                    i9 = MathKt__MathJVMKt.roundToInt((1 - f4) * ((Constraints.m821getMaxWidthimpl(j3) * 0.05f) - mo51roundToPx0680j_43) * f5 * (backEventCompat2.swipeEdge == 0 ? 1 : -1) * (layoutDirection == LayoutDirection.Ltr ? 1 : -1));
                                                }
                                                long j4 = j;
                                                BackEventCompat backEventCompat3 = (BackEventCompat) mutableState6.getValue();
                                                BackEventCompat backEventCompat4 = (BackEventCompat) mutableState7.getValue();
                                                int i11 = lerp4;
                                                int mo51roundToPx0680j_44 = MeasureScope.this.mo51roundToPx0680j_4(SearchBarKt.SearchBarPredictiveBackMaxOffsetY);
                                                float f6 = f3;
                                                if (backEventCompat4 != null && backEventCompat3 != null && f6 != 0.0f) {
                                                    int min = Math.min(Math.max(0, ((Constraints.m820getMaxHeightimpl(j4) - i11) / 2) - mo51roundToPx0680j_43), mo51roundToPx0680j_44);
                                                    i10 = MathKt__MathJVMKt.roundToInt(MathHelpersKt.lerp(Math.abs(r4) / Constraints.m820getMaxHeightimpl(j4), 0, min) * f6 * Math.signum(backEventCompat3.touchY - backEventCompat4.touchY));
                                                }
                                                placementScope.placeRelative(placeable3, i9, lerp5 + i10, 0.0f);
                                                placementScope.placeRelative(mo608measureBRTryo0, i9, mo51roundToPx0680j_4 + i10, 0.0f);
                                                Placeable placeable4 = placeable2;
                                                if (placeable4 != null) {
                                                    placementScope.placeRelative(placeable4, i9, i10 + mo51roundToPx0680j_4 + mo608measureBRTryo0.height + lerp6, 0.0f);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        return layout$1;
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
                rememberedValue3 = measurePolicy;
            } else {
                z = true;
                th = null;
            }
            MeasurePolicy measurePolicy2 = (MeasurePolicy) rememberedValue3;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, consumeWindowInsets);
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
            Updater.m336setimpl(composerImpl, measurePolicy2, function24);
            Function2 function25 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function25);
            Function2 function26 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function26);
            }
            Function2 function27 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function27);
            Modifier.Companion companion = Modifier.Companion;
            Modifier layoutId = LayoutIdKt.layoutId(companion, "Surface");
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, z);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, layoutId);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function24);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function25);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function26);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function27);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function22.invoke(composerImpl, Integer.valueOf((i3 >> 21) & 14));
            composerImpl.end(true);
            Modifier layoutId2 = LayoutIdKt.layoutId(companion, "InputField");
            MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, layoutId2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function24);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function25);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function26);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function27);
            function2.invoke(composerImpl, Integer.valueOf((i3 >> 18) & 14));
            composerImpl.end(true);
            if (function23 == null) {
                composerImpl.startReplaceGroup(96147618);
                composerImpl.end(false);
                z2 = true;
            } else {
                composerImpl.startReplaceGroup(96147619);
                Modifier layoutId3 = LayoutIdKt.layoutId(companion, "Content");
                MeasurePolicy maybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, layoutId3);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy3, function24);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function25);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function26);
                }
                Updater.m336setimpl(composerImpl, materializeModifier4, function27);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarKt$SearchBarLayout$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SearchBarKt.SearchBarLayout(animatable, mutableFloatState, mutableState, mutableState2, modifier, windowInsets, function2, function22, function23, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
