package androidx.compose.material3;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.foundation.text.BasicTextFieldKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.material3.internal.SystemBarsDefaultInsets_androidKt;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.ElevationTokens;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.material3.tokens.SearchBarTokens;
import androidx.compose.material3.tokens.SearchViewTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.focus.FocusState;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation$Companion$$ExternalSyntheticLambda0;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;

/* loaded from: classes.dex */
public final class SearchBarDefaults {
    public static final SearchBarDefaults INSTANCE = new SearchBarDefaults();
    public static final float InputFieldHeight;
    public static final float ShadowElevation;
    public static final float TonalElevation;

    static {
        ElevationTokens elevationTokens = ElevationTokens.INSTANCE;
        elevationTokens.getClass();
        float f = ElevationTokens.Level0;
        TonalElevation = f;
        elevationTokens.getClass();
        ShadowElevation = f;
        SearchBarTokens.INSTANCE.getClass();
        InputFieldHeight = SearchBarTokens.ContainerHeight;
    }

    private SearchBarDefaults() {
    }

    /* renamed from: colors-Klgx-Pg, reason: not valid java name */
    public static SearchBarColors m284colorsKlgxPg(long j, Composer composer, int i, int i2) {
        if ((i2 & 1) != 0) {
            SearchBarTokens.INSTANCE.getClass();
            j = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        }
        long j2 = j;
        SearchViewTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SearchViewTokens.DividerColor, composer);
        TextFieldColors textFieldColorsM285inputFieldColorsJVEmHcM = m285inputFieldColorsJVEmHcM(j2, j2, j2, composer, i & 7168, 1048575);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.colors (SearchBar.kt:1148)");
        }
        SearchBarColors searchBarColors = new SearchBarColors(j2, value, textFieldColorsM285inputFieldColorsJVEmHcM, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return searchBarColors;
    }

    public static Shape getInputFieldShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.<get-inputFieldShape> (SearchBar.kt:1050)");
        }
        SearchBarTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(SearchBarTokens.ContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static WindowInsets getWindowInsets(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.<get-windowInsets> (SearchBar.kt:1064)");
        }
        WindowInsets.Companion companion = WindowInsets.Companion;
        WindowInsets systemBarsForVisualComponents = SystemBarsDefaultInsets_androidKt.getSystemBarsForVisualComponents(composer);
        WindowInsetsSides.Companion.getClass();
        WindowInsets windowInsetsM149onlybOOhFvg = WindowInsetsKt.m149onlybOOhFvg(systemBarsForVisualComponents, WindowInsetsSides.Horizontal | WindowInsetsSides.Top);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return windowInsetsM149onlybOOhFvg;
    }

    /* renamed from: inputFieldColors-JVEmHcM, reason: not valid java name */
    public static TextFieldColors m285inputFieldColorsJVEmHcM(long j, long j2, long j3, Composer composer, int i, int i2) {
        long value;
        long value2;
        long value3;
        SearchBarTokens searchBarTokens = SearchBarTokens.INSTANCE;
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens = SearchBarTokens.InputTextColor;
        long value4 = ColorSchemeKt.getValue(colorSchemeKeyTokens, composer);
        searchBarTokens.getClass();
        long value5 = ColorSchemeKt.getValue(colorSchemeKeyTokens, composer);
        FilledTextFieldTokens filledTextFieldTokens = FilledTextFieldTokens.INSTANCE;
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = FilledTextFieldTokens.DisabledInputColor;
        long value6 = ColorSchemeKt.getValue(colorSchemeKeyTokens2, composer);
        float f = FilledTextFieldTokens.DisabledInputOpacity;
        long jColor = ColorKt.Color(Color.m463getRedimpl(value6), Color.m462getGreenimpl(value6), Color.m460getBlueimpl(value6), f, Color.m461getColorSpaceimpl(value6));
        filledTextFieldTokens.getClass();
        long value7 = ColorSchemeKt.getValue(FilledTextFieldTokens.CaretColor, composer);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = TextSelectionColorsKt.LocalTextSelectionColors;
        TextSelectionColors textSelectionColors = (TextSelectionColors) ((ComposerImpl) composer).consume(dynamicProvidableCompositionLocal);
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = SearchBarTokens.LeadingIconColor;
        long value8 = ColorSchemeKt.getValue(colorSchemeKeyTokens3, composer);
        searchBarTokens.getClass();
        long value9 = ColorSchemeKt.getValue(colorSchemeKeyTokens3, composer);
        filledTextFieldTokens.getClass();
        long value10 = ColorSchemeKt.getValue(FilledTextFieldTokens.DisabledLeadingIconColor, composer);
        long jColor2 = ColorKt.Color(Color.m463getRedimpl(value10), Color.m462getGreenimpl(value10), Color.m460getBlueimpl(value10), FilledTextFieldTokens.DisabledLeadingIconOpacity, Color.m461getColorSpaceimpl(value10));
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = SearchBarTokens.TrailingIconColor;
        long value11 = ColorSchemeKt.getValue(colorSchemeKeyTokens4, composer);
        searchBarTokens.getClass();
        long value12 = ColorSchemeKt.getValue(colorSchemeKeyTokens4, composer);
        filledTextFieldTokens.getClass();
        long value13 = ColorSchemeKt.getValue(FilledTextFieldTokens.DisabledTrailingIconColor, composer);
        long jColor3 = ColorKt.Color(Color.m463getRedimpl(value13), Color.m462getGreenimpl(value13), Color.m460getBlueimpl(value13), FilledTextFieldTokens.DisabledTrailingIconOpacity, Color.m461getColorSpaceimpl(value13));
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = SearchBarTokens.SupportingTextColor;
        long value14 = ColorSchemeKt.getValue(colorSchemeKeyTokens5, composer);
        searchBarTokens.getClass();
        long value15 = ColorSchemeKt.getValue(colorSchemeKeyTokens5, composer);
        filledTextFieldTokens.getClass();
        long value16 = ColorSchemeKt.getValue(colorSchemeKeyTokens2, composer);
        long jColor4 = ColorKt.Color(Color.m463getRedimpl(value16), Color.m462getGreenimpl(value16), Color.m460getBlueimpl(value16), f, Color.m461getColorSpaceimpl(value16));
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens6 = FilledTextFieldTokens.InputPrefixColor;
        long value17 = ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer);
        filledTextFieldTokens.getClass();
        long value18 = ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer);
        filledTextFieldTokens.getClass();
        long value19 = ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer);
        long jColor5 = ColorKt.Color(Color.m463getRedimpl(value19), Color.m462getGreenimpl(value19), Color.m460getBlueimpl(value19), f, Color.m461getColorSpaceimpl(value19));
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens7 = FilledTextFieldTokens.InputSuffixColor;
        long value20 = ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer);
        filledTextFieldTokens.getClass();
        long value21 = ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer);
        filledTextFieldTokens.getClass();
        long value22 = ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer);
        long jColor6 = ColorKt.Color(Color.m463getRedimpl(value22), Color.m462getGreenimpl(value22), Color.m460getBlueimpl(value22), f, Color.m461getColorSpaceimpl(value22));
        if ((i2 & 1048576) != 0) {
            searchBarTokens.getClass();
            value = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            value = j;
        }
        if ((i2 & 2097152) != 0) {
            searchBarTokens.getClass();
            value2 = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            value2 = j2;
        }
        if ((i2 & 4194304) != 0) {
            searchBarTokens.getClass();
            value3 = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            value3 = j3;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.inputFieldColors (SearchBar.kt:1231)");
        }
        TextFieldDefaults.INSTANCE.getClass();
        Color.Companion.getClass();
        long j4 = Color.Unspecified;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.colors (TextFieldDefaults.kt:574)");
        }
        MaterialTheme.INSTANCE.getClass();
        TextFieldColors textFieldColorsM310copyejIjP34 = TextFieldDefaults.defaultTextFieldColors$material3_release(MaterialTheme.getColorScheme(composer), (TextSelectionColors) ((ComposerImpl) composer).consume(dynamicProvidableCompositionLocal)).m310copyejIjP34(value4, value5, jColor, j4, value, value2, value3, j4, value7, j4, textSelectionColors, j4, j4, j4, j4, value8, value9, jColor2, j4, value11, value12, jColor3, j4, j4, j4, j4, j4, value14, value15, jColor4, j4, j4, j4, j4, j4, value17, value18, jColor5, j4, value20, value21, jColor6, j4);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return textFieldColorsM310copyejIjP34;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03cd  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0496  */
    /* JADX WARN: Removed duplicated region for block: B:236:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void InputField(final String str, final Function1 function1, final Function1 function12, final boolean z, final Function1 function13, Modifier modifier, boolean z2, Function2 function2, Function2 function22, Function2 function23, TextFieldColors textFieldColors, MutableInteractionSource mutableInteractionSource, Composer composer, final int i, final int i2, final int i3) throws Resources.NotFoundException {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Modifier modifier2;
        int i10;
        ComposerImpl composerImpl;
        final TextFieldColors textFieldColorsM285inputFieldColorsJVEmHcM;
        MutableInteractionSource mutableInteractionSource2;
        boolean z3;
        Function2 function24;
        Function2 function25;
        Function2 function26;
        Modifier modifier3;
        int i11;
        MutableInteractionSource mutableInteractionSource3;
        Object objRememberedValue;
        Object obj;
        FocusManager focusManager;
        final String strM322getString2EP1pXo;
        final String strM322getString2EP1pXo2;
        long jM758getColor0d7_KjU;
        boolean z4;
        Object objRememberedValue2;
        boolean zChanged;
        Object objRememberedValue3;
        boolean z5;
        Object objRememberedValue4;
        ComposerImpl composerImpl2;
        boolean z6;
        boolean zChanged2;
        Object objRememberedValue5;
        final boolean z7;
        final MutableInteractionSource mutableInteractionSource4;
        final TextFieldColors textFieldColors2;
        final Function2 function27;
        final Modifier modifier4;
        final Function2 function28;
        final Function2 function29;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1690291008);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl3.changed(str) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i3 & 2) != 0) {
            i4 |= 48;
            i5 = 4;
        } else {
            i5 = 4;
            if ((i & 48) == 0) {
                i4 |= composerImpl3.changedInstance(function1) ? 32 : 16;
            }
        }
        if ((i3 & 4) != 0) {
            i4 |= 384;
            i6 = 32;
        } else {
            i6 = 32;
            if ((i & 384) == 0) {
                i4 |= composerImpl3.changedInstance(function12) ? 256 : 128;
            }
        }
        if ((i3 & 8) != 0) {
            i4 |= 3072;
        } else if ((i & 3072) == 0) {
            i4 |= composerImpl3.changed(z) ? 2048 : 1024;
        }
        if ((i3 & 16) != 0) {
            i4 |= 24576;
        } else if ((i & 24576) == 0) {
            i4 |= composerImpl3.changedInstance(function13) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        int i12 = i3 & 32;
        if (i12 != 0) {
            i4 |= 196608;
        } else if ((i & 196608) == 0) {
            i4 |= composerImpl3.changed(modifier) ? 131072 : 65536;
        }
        int i13 = i3 & 64;
        if (i13 != 0) {
            i4 |= 1572864;
        } else if ((i & 1572864) == 0) {
            i4 |= composerImpl3.changed(z2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i14 = i3 & 128;
        if (i14 != 0) {
            i4 |= 12582912;
        } else if ((i & 12582912) == 0) {
            i4 |= composerImpl3.changedInstance(function2) ? 8388608 : 4194304;
        }
        int i15 = i3 & 256;
        if (i15 != 0) {
            i4 |= 100663296;
        } else if ((i & 100663296) == 0) {
            i4 |= composerImpl3.changedInstance(function22) ? 67108864 : 33554432;
        }
        int i16 = i3 & 512;
        if (i16 != 0) {
            i4 |= 805306368;
        } else {
            if ((i & 805306368) == 0) {
                i7 = i16;
                i4 |= composerImpl3.changedInstance(function23) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
            }
            if ((i2 & 6) != 0) {
                i8 = i2 | (((i3 & 1024) == 0 && composerImpl3.changed(textFieldColors)) ? i5 : 2);
            } else {
                i8 = i2;
            }
            i9 = i3 & 2048;
            if (i9 == 0) {
                i8 |= 48;
            } else {
                if ((i2 & 48) == 0) {
                    i8 |= composerImpl3.changed(mutableInteractionSource) ? i6 : 16;
                }
                if ((i3 & 4096) == 0) {
                    if ((i2 & 384) == 0) {
                        i8 |= composerImpl3.changed(this) ? 256 : 128;
                    }
                    if ((i4 & 306783379) != 306783378 && (i8 & 147) == 146 && composerImpl3.getSkipping()) {
                        composerImpl3.skipToGroupEnd();
                        z7 = z2;
                        function27 = function2;
                        function28 = function22;
                        function29 = function23;
                        textFieldColors2 = textFieldColors;
                        mutableInteractionSource4 = mutableInteractionSource;
                        composerImpl2 = composerImpl3;
                        modifier4 = modifier;
                    } else {
                        composerImpl3.startDefaults();
                        if ((i & 1) != 0 || composerImpl3.getDefaultsInvalid()) {
                            modifier2 = i12 == 0 ? Modifier.Companion : modifier;
                            boolean z8 = i13 == 0 ? true : z2;
                            Function2 function210 = i14 == 0 ? null : function2;
                            Function2 function211 = i15 == 0 ? null : function22;
                            Function2 function212 = i7 == 0 ? null : function23;
                            if ((1024 & i3) == 0) {
                                int i17 = (i8 << 3) & 7168;
                                i10 = i9;
                                textFieldColorsM285inputFieldColorsJVEmHcM = m285inputFieldColorsJVEmHcM(0L, 0L, 0L, composerImpl3, i17, 8388607);
                                composerImpl = composerImpl3;
                            } else {
                                i10 = i9;
                                composerImpl = composerImpl3;
                                textFieldColorsM285inputFieldColorsJVEmHcM = textFieldColors;
                            }
                            if (i10 == 0) {
                                z3 = z8;
                                function24 = function210;
                                function25 = function212;
                                function26 = function211;
                                mutableInteractionSource2 = null;
                            } else {
                                mutableInteractionSource2 = mutableInteractionSource;
                                z3 = z8;
                                function24 = function210;
                                function25 = function212;
                                function26 = function211;
                            }
                        } else {
                            composerImpl3.skipToGroupEnd();
                            modifier2 = modifier;
                            z3 = z2;
                            function24 = function2;
                            function26 = function22;
                            function25 = function23;
                            mutableInteractionSource2 = mutableInteractionSource;
                            composerImpl = composerImpl3;
                            textFieldColorsM285inputFieldColorsJVEmHcM = textFieldColors;
                        }
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField (SearchBar.kt:1661)");
                        }
                        Composer.Companion companion = Composer.Companion;
                        if (mutableInteractionSource2 != null) {
                            Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -1288405033, companion);
                            modifier3 = modifier2;
                            if (objM == Composer.Companion.Empty) {
                                objM = InteractionSourceKt.MutableInteractionSource();
                                composerImpl.updateRememberedValue(objM);
                            }
                            mutableInteractionSource3 = (MutableInteractionSource) objM;
                            i11 = 0;
                            composerImpl.end(false);
                        } else {
                            modifier3 = modifier2;
                            i11 = 0;
                            composerImpl.startReplaceGroup(-318656768);
                            composerImpl.end(false);
                            mutableInteractionSource3 = mutableInteractionSource2;
                        }
                        final boolean zBooleanValue = ((Boolean) FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource3, composerImpl, i11).getValue()).booleanValue();
                        objRememberedValue = composerImpl.rememberedValue();
                        companion.getClass();
                        final MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource3;
                        obj = Composer.Companion.Empty;
                        if (objRememberedValue == obj) {
                            objRememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
                        }
                        FocusRequester focusRequester = (FocusRequester) objRememberedValue;
                        final Function2 function213 = function24;
                        focusManager = (FocusManager) composerImpl.consume(CompositionLocalsKt.LocalFocusManager);
                        int i18 = Strings.$r8$clinit;
                        strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_search_bar_search, composerImpl);
                        final Function2 function214 = function26;
                        strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_suggestions_available, composerImpl);
                        final Function2 function215 = function25;
                        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = TextKt.LocalTextStyle;
                        jM758getColor0d7_KjU = ((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal)).m758getColor0d7_KjU();
                        if (jM758getColor0d7_KjU == 16) {
                            jM758getColor0d7_KjU = textFieldColorsM285inputFieldColorsJVEmHcM.m312textColorXeAY9LY$material3_release(z3, false, zBooleanValue);
                        }
                        long j = jM758getColor0d7_KjU;
                        Modifier modifier5 = modifier3;
                        Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.m143sizeInqDBjuR0$default(modifier3, SearchBarKt.SearchBarMinWidth, InputFieldHeight, SearchBarKt.SearchBarMaxWidth, 0.0f, 8), focusRequester);
                        z4 = (57344 & i4) != 16384;
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z4 || objRememberedValue2 == obj) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$20$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    if (((FocusStateImpl) ((FocusState) obj2)).isFocused()) {
                                        function13.mo781invoke(Boolean.TRUE);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierFocusRequester, (Function1) objRememberedValue2);
                        final boolean z9 = z3;
                        zChanged = ((i4 & 7168) != 2048) | composerImpl.changed(strM322getString2EP1pXo) | composerImpl.changed(strM322getString2EP1pXo2);
                        objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChanged || objRememberedValue3 == obj) {
                            objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$21$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, strM322getString2EP1pXo);
                                    if (z) {
                                        SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, strM322getString2EP1pXo2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierOnFocusChanged, false, (Function1) objRememberedValue3);
                        TextStyle textStyleMerge = ((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal)).merge(new TextStyle(j, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777214, (DefaultConstructorMarker) null));
                        SolidColor solidColor = new SolidColor(textFieldColorsM285inputFieldColorsJVEmHcM.cursorColor, null);
                        ImeAction.Companion.getClass();
                        KeyboardOptions keyboardOptions = new KeyboardOptions(0, (Boolean) null, 0, ImeAction.Search, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 119, (DefaultConstructorMarker) null);
                        int i19 = i4 & 14;
                        z5 = ((i4 & 896) != 256) | (i19 != i5);
                        objRememberedValue4 = composerImpl.rememberedValue();
                        if (!z5 || objRememberedValue4 == obj) {
                            objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$22$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    function12.mo781invoke(str);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue4);
                        }
                        KeyboardActions keyboardActions = new KeyboardActions(null, null, null, null, (Function1) objRememberedValue4, null, 47, null);
                        int i20 = i4;
                        Function3 function3 = new Function3() { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda;
                                Function2 function216 = (Function2) obj2;
                                Composer composer2 = (Composer) obj3;
                                int iIntValue = ((Number) obj4).intValue();
                                if ((iIntValue & 6) == 0) {
                                    iIntValue |= ((ComposerImpl) composer2).changedInstance(function216) ? 4 : 2;
                                }
                                if ((iIntValue & 19) == 18) {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    if (composerImpl4.getSkipping()) {
                                        composerImpl4.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous> (SearchBar.kt:1704)");
                                        }
                                        TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                                        String str2 = str;
                                        int i21 = iIntValue;
                                        boolean z10 = z9;
                                        VisualTransformation.Companion.getClass();
                                        VisualTransformation$Companion$$ExternalSyntheticLambda0 visualTransformation$Companion$$ExternalSyntheticLambda0 = VisualTransformation.Companion.None;
                                        MutableInteractionSource mutableInteractionSource6 = mutableInteractionSource5;
                                        Function2 function217 = function213;
                                        final Function2 function218 = function214;
                                        ComposableLambdaImpl composableLambdaImpl = null;
                                        if (function218 == null) {
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                            composerImpl5.startReplaceGroup(252441775);
                                            composerImpl5.end(false);
                                            composableLambdaImplRememberComposableLambda = null;
                                        } else {
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                            composerImpl6.startReplaceGroup(252441776);
                                            composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-396782848, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$23$1$1
                                                {
                                                    super(2);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj5, Object obj6) {
                                                    Composer composer3 = (Composer) obj5;
                                                    if ((((Number) obj6).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl7 = (ComposerImpl) composer3;
                                                        if (composerImpl7.getSkipping()) {
                                                            composerImpl7.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous>.<anonymous> (SearchBar.kt:1714)");
                                                            }
                                                            Modifier modifierM116offsetVpY3zN4$default = OffsetKt.m116offsetVpY3zN4$default(Modifier.Companion, SearchBarKt.SearchBarIconOffsetX, 0.0f, 2);
                                                            Function2 function219 = function218;
                                                            Alignment.Companion.getClass();
                                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                            ComposerImpl composerImpl8 = (ComposerImpl) composer3;
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl8.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierM116offsetVpY3zN4$default);
                                                            ComposeUiNode.Companion.getClass();
                                                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                            if (composerImpl8.applier == null) {
                                                                ComposablesKt.invalidApplier();
                                                                throw null;
                                                            }
                                                            composerImpl8.startReusableNode();
                                                            if (composerImpl8.inserting) {
                                                                composerImpl8.createNode(function0);
                                                            } else {
                                                                composerImpl8.useNode();
                                                            }
                                                            Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                            Function2 function220 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                            if (composerImpl8.inserting || !Intrinsics.areEqual(composerImpl8.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl8, currentCompositeKeyHash, function220);
                                                            }
                                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                            function219.invoke(composer3, 0);
                                                            composerImpl8.end(true);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl6);
                                            composerImpl6.end(false);
                                        }
                                        final Function2 function219 = function215;
                                        if (function219 == null) {
                                            ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                                            composerImpl7.startReplaceGroup(252666060);
                                            composerImpl7.end(false);
                                        } else {
                                            ComposerImpl composerImpl8 = (ComposerImpl) composer2;
                                            composerImpl8.startReplaceGroup(252666061);
                                            ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(1940619700, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$23$2$1
                                                {
                                                    super(2);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj5, Object obj6) {
                                                    Composer composer3 = (Composer) obj5;
                                                    if ((((Number) obj6).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl9 = (ComposerImpl) composer3;
                                                        if (composerImpl9.getSkipping()) {
                                                            composerImpl9.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous>.<anonymous> (SearchBar.kt:1718)");
                                                            }
                                                            Modifier.Companion companion2 = Modifier.Companion;
                                                            float f = -SearchBarKt.SearchBarIconOffsetX;
                                                            Dp.Companion companion3 = Dp.Companion;
                                                            Modifier modifierM116offsetVpY3zN4$default = OffsetKt.m116offsetVpY3zN4$default(companion2, f, 0.0f, 2);
                                                            Function2 function220 = function219;
                                                            Alignment.Companion.getClass();
                                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                            ComposerImpl composerImpl10 = (ComposerImpl) composer3;
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl10.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierM116offsetVpY3zN4$default);
                                                            ComposeUiNode.Companion.getClass();
                                                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                            if (composerImpl10.applier == null) {
                                                                ComposablesKt.invalidApplier();
                                                                throw null;
                                                            }
                                                            composerImpl10.startReusableNode();
                                                            if (composerImpl10.inserting) {
                                                                composerImpl10.createNode(function0);
                                                            } else {
                                                                composerImpl10.useNode();
                                                            }
                                                            Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                            Function2 function221 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                            if (composerImpl10.inserting || !Intrinsics.areEqual(composerImpl10.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl10, currentCompositeKeyHash, function221);
                                                            }
                                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                            function220.invoke(composer3, 0);
                                                            composerImpl10.end(true);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl8);
                                            composerImpl8.end(false);
                                            composableLambdaImpl = composableLambdaImplRememberComposableLambda2;
                                        }
                                        SearchBarDefaults.INSTANCE.getClass();
                                        Shape inputFieldShape = SearchBarDefaults.getInputFieldShape(composer2);
                                        TextFieldColors textFieldColors3 = textFieldColorsM285inputFieldColorsJVEmHcM;
                                        PaddingValuesImpl paddingValuesImplM313contentPaddingWithoutLabela9UjIt4$default = TextFieldDefaults.m313contentPaddingWithoutLabela9UjIt4$default(textFieldDefaults);
                                        final TextFieldColors textFieldColors4 = textFieldColorsM285inputFieldColorsJVEmHcM;
                                        final boolean z11 = z9;
                                        final boolean z12 = zBooleanValue;
                                        textFieldDefaults.DecorationBox(str2, function216, z10, true, visualTransformation$Companion$$ExternalSyntheticLambda0, mutableInteractionSource6, false, null, function217, composableLambdaImplRememberComposableLambda, composableLambdaImpl, null, null, null, inputFieldShape, textFieldColors3, paddingValuesImplM313contentPaddingWithoutLabela9UjIt4$default, ComposableLambdaKt.rememberComposableLambda(-1742391428, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23.3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj5, Object obj6) {
                                                Composer composer3 = (Composer) obj5;
                                                if ((((Number) obj6).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl9 = (ComposerImpl) composer3;
                                                    if (composerImpl9.getSkipping()) {
                                                        composerImpl9.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous> (SearchBar.kt:1724)");
                                                        }
                                                        State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColors4.m309containerColorXeAY9LY$material3_release(z11, false, z12), MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composer3), null, composer3, 0, 12);
                                                        Modifier.Companion companion2 = Modifier.Companion;
                                                        SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0 searchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0 = new SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac) { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23.3.1
                                                            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                            public final Object get() {
                                                                return ((State) this.receiver).getValue();
                                                            }
                                                        });
                                                        SearchBarDefaults.INSTANCE.getClass();
                                                        BoxKt.Box(TextFieldImplKt.textFieldBackground(companion2, searchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0, SearchBarDefaults.getInputFieldShape(composer3)), composer3, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, ((i21 << 3) & 112) | 27648, 113246208, 14528);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        TextFieldColors textFieldColors3 = textFieldColorsM285inputFieldColorsJVEmHcM;
                        int i21 = i19 | 102236160 | (i20 & 112) | ((i20 >> 9) & 7168);
                        ComposerImpl composerImpl4 = composerImpl;
                        BasicTextFieldKt.BasicTextField(str, function1, modifierSemantics, z9, false, textStyleMerge, keyboardOptions, keyboardActions, true, 0, 0, null, null, mutableInteractionSource5, solidColor, ComposableLambdaKt.rememberComposableLambda(653749962, function3, composerImpl), composerImpl4, i21, 196608, 7696);
                        composerImpl2 = composerImpl4;
                        z6 = z && zBooleanValue;
                        Boolean boolValueOf = Boolean.valueOf(z);
                        zChanged2 = composerImpl2.changed(z6) | composerImpl2.changedInstance(focusManager);
                        objRememberedValue5 = composerImpl2.rememberedValue();
                        if (!zChanged2 || objRememberedValue5 == obj) {
                            objRememberedValue5 = new SearchBarDefaults$InputField$24$1(z6, focusManager, null);
                            composerImpl2.updateRememberedValue(objRememberedValue5);
                        }
                        EffectsKt.LaunchedEffect(composerImpl2, boolValueOf, (Function2) objRememberedValue5);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        z7 = z9;
                        mutableInteractionSource4 = mutableInteractionSource2;
                        textFieldColors2 = textFieldColors3;
                        function27 = function213;
                        modifier4 = modifier5;
                        function28 = function214;
                        function29 = function215;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SearchBarDefaults.InputField.25
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) throws Resources.NotFoundException {
                                ((Number) obj3).intValue();
                                SearchBarDefaults.this.InputField(str, function1, function12, z, function13, modifier4, z7, function27, function28, function29, textFieldColors2, mutableInteractionSource4, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i8 |= 384;
                if ((i4 & 306783379) != 306783378) {
                    composerImpl3.startDefaults();
                    if ((i & 1) != 0) {
                        if (i12 == 0) {
                        }
                        if (i13 == 0) {
                        }
                        if (i14 == 0) {
                        }
                        if (i15 == 0) {
                        }
                        if (i7 == 0) {
                        }
                        if ((1024 & i3) == 0) {
                        }
                        if (i10 == 0) {
                        }
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        Composer.Companion companion2 = Composer.Companion;
                        if (mutableInteractionSource2 != null) {
                        }
                        final boolean zBooleanValue2 = ((Boolean) FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource3, composerImpl, i11).getValue()).booleanValue();
                        objRememberedValue = composerImpl.rememberedValue();
                        companion2.getClass();
                        final MutableInteractionSource mutableInteractionSource52 = mutableInteractionSource3;
                        obj = Composer.Companion.Empty;
                        if (objRememberedValue == obj) {
                        }
                        FocusRequester focusRequester2 = (FocusRequester) objRememberedValue;
                        final Function2 function2132 = function24;
                        focusManager = (FocusManager) composerImpl.consume(CompositionLocalsKt.LocalFocusManager);
                        int i182 = Strings.$r8$clinit;
                        strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_search_bar_search, composerImpl);
                        final Function2 function2142 = function26;
                        strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_suggestions_available, composerImpl);
                        final Function2 function2152 = function25;
                        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal2 = TextKt.LocalTextStyle;
                        jM758getColor0d7_KjU = ((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal2)).m758getColor0d7_KjU();
                        if (jM758getColor0d7_KjU == 16) {
                        }
                        long j2 = jM758getColor0d7_KjU;
                        Modifier modifier52 = modifier3;
                        Modifier modifierFocusRequester2 = FocusRequesterModifierKt.focusRequester(SizeKt.m143sizeInqDBjuR0$default(modifier3, SearchBarKt.SearchBarMinWidth, InputFieldHeight, SearchBarKt.SearchBarMaxWidth, 0.0f, 8), focusRequester2);
                        if ((57344 & i4) != 16384) {
                        }
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z4) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$20$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    if (((FocusStateImpl) ((FocusState) obj2)).isFocused()) {
                                        function13.mo781invoke(Boolean.TRUE);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                            Modifier modifierOnFocusChanged2 = FocusChangedModifierKt.onFocusChanged(modifierFocusRequester2, (Function1) objRememberedValue2);
                            final boolean z92 = z3;
                            zChanged = ((i4 & 7168) != 2048) | composerImpl.changed(strM322getString2EP1pXo) | composerImpl.changed(strM322getString2EP1pXo2);
                            objRememberedValue3 = composerImpl.rememberedValue();
                            if (!zChanged) {
                                objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$21$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, strM322getString2EP1pXo);
                                        if (z) {
                                            SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, strM322getString2EP1pXo2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue3);
                                Modifier modifierSemantics2 = SemanticsModifierKt.semantics(modifierOnFocusChanged2, false, (Function1) objRememberedValue3);
                                TextStyle textStyleMerge2 = ((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal2)).merge(new TextStyle(j2, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777214, (DefaultConstructorMarker) null));
                                SolidColor solidColor2 = new SolidColor(textFieldColorsM285inputFieldColorsJVEmHcM.cursorColor, null);
                                ImeAction.Companion.getClass();
                                KeyboardOptions keyboardOptions2 = new KeyboardOptions(0, (Boolean) null, 0, ImeAction.Search, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 119, (DefaultConstructorMarker) null);
                                int i192 = i4 & 14;
                                z5 = ((i4 & 896) != 256) | (i192 != i5);
                                objRememberedValue4 = composerImpl.rememberedValue();
                                if (!z5) {
                                    objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$22$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj2) {
                                            function12.mo781invoke(str);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                    KeyboardActions keyboardActions2 = new KeyboardActions(null, null, null, null, (Function1) objRememberedValue4, null, 47, null);
                                    int i202 = i4;
                                    Function3 function32 = new Function3() { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                            ComposableLambdaImpl composableLambdaImplRememberComposableLambda;
                                            Function2 function216 = (Function2) obj2;
                                            Composer composer2 = (Composer) obj3;
                                            int iIntValue = ((Number) obj4).intValue();
                                            if ((iIntValue & 6) == 0) {
                                                iIntValue |= ((ComposerImpl) composer2).changedInstance(function216) ? 4 : 2;
                                            }
                                            if ((iIntValue & 19) == 18) {
                                                ComposerImpl composerImpl42 = (ComposerImpl) composer2;
                                                if (composerImpl42.getSkipping()) {
                                                    composerImpl42.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous> (SearchBar.kt:1704)");
                                                    }
                                                    TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                                                    String str2 = str;
                                                    int i212 = iIntValue;
                                                    boolean z10 = z92;
                                                    VisualTransformation.Companion.getClass();
                                                    VisualTransformation$Companion$$ExternalSyntheticLambda0 visualTransformation$Companion$$ExternalSyntheticLambda0 = VisualTransformation.Companion.None;
                                                    MutableInteractionSource mutableInteractionSource6 = mutableInteractionSource52;
                                                    Function2 function217 = function2132;
                                                    final Function2 function218 = function2142;
                                                    ComposableLambdaImpl composableLambdaImpl = null;
                                                    if (function218 == null) {
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                        composerImpl5.startReplaceGroup(252441775);
                                                        composerImpl5.end(false);
                                                        composableLambdaImplRememberComposableLambda = null;
                                                    } else {
                                                        ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                                        composerImpl6.startReplaceGroup(252441776);
                                                        composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-396782848, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$23$1$1
                                                            {
                                                                super(2);
                                                            }

                                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                            @Override // kotlin.jvm.functions.Function2
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                            */
                                                            public final Object invoke(Object obj5, Object obj6) {
                                                                Composer composer3 = (Composer) obj5;
                                                                if ((((Number) obj6).intValue() & 3) == 2) {
                                                                    ComposerImpl composerImpl7 = (ComposerImpl) composer3;
                                                                    if (composerImpl7.getSkipping()) {
                                                                        composerImpl7.skipToGroupEnd();
                                                                    } else {
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous>.<anonymous> (SearchBar.kt:1714)");
                                                                        }
                                                                        Modifier modifierM116offsetVpY3zN4$default = OffsetKt.m116offsetVpY3zN4$default(Modifier.Companion, SearchBarKt.SearchBarIconOffsetX, 0.0f, 2);
                                                                        Function2 function219 = function218;
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                                        ComposerImpl composerImpl8 = (ComposerImpl) composer3;
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl8.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierM116offsetVpY3zN4$default);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl8.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl8.startReusableNode();
                                                                        if (composerImpl8.inserting) {
                                                                            composerImpl8.createNode(function0);
                                                                        } else {
                                                                            composerImpl8.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function220 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl8.inserting || !Intrinsics.areEqual(composerImpl8.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl8, currentCompositeKeyHash, function220);
                                                                        }
                                                                        Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        function219.invoke(composer3, 0);
                                                                        composerImpl8.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, composerImpl6);
                                                        composerImpl6.end(false);
                                                    }
                                                    final Function2 function219 = function2152;
                                                    if (function219 == null) {
                                                        ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                                                        composerImpl7.startReplaceGroup(252666060);
                                                        composerImpl7.end(false);
                                                    } else {
                                                        ComposerImpl composerImpl8 = (ComposerImpl) composer2;
                                                        composerImpl8.startReplaceGroup(252666061);
                                                        ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(1940619700, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults$InputField$23$2$1
                                                            {
                                                                super(2);
                                                            }

                                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                            @Override // kotlin.jvm.functions.Function2
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                            */
                                                            public final Object invoke(Object obj5, Object obj6) {
                                                                Composer composer3 = (Composer) obj5;
                                                                if ((((Number) obj6).intValue() & 3) == 2) {
                                                                    ComposerImpl composerImpl9 = (ComposerImpl) composer3;
                                                                    if (composerImpl9.getSkipping()) {
                                                                        composerImpl9.skipToGroupEnd();
                                                                    } else {
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous>.<anonymous> (SearchBar.kt:1718)");
                                                                        }
                                                                        Modifier.Companion companion22 = Modifier.Companion;
                                                                        float f = -SearchBarKt.SearchBarIconOffsetX;
                                                                        Dp.Companion companion3 = Dp.Companion;
                                                                        Modifier modifierM116offsetVpY3zN4$default = OffsetKt.m116offsetVpY3zN4$default(companion22, f, 0.0f, 2);
                                                                        Function2 function220 = function219;
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                                        ComposerImpl composerImpl10 = (ComposerImpl) composer3;
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl10.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierM116offsetVpY3zN4$default);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl10.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl10.startReusableNode();
                                                                        if (composerImpl10.inserting) {
                                                                            composerImpl10.createNode(function0);
                                                                        } else {
                                                                            composerImpl10.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function221 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl10.inserting || !Intrinsics.areEqual(composerImpl10.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl10, currentCompositeKeyHash, function221);
                                                                        }
                                                                        Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        function220.invoke(composer3, 0);
                                                                        composerImpl10.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }, composerImpl8);
                                                        composerImpl8.end(false);
                                                        composableLambdaImpl = composableLambdaImplRememberComposableLambda2;
                                                    }
                                                    SearchBarDefaults.INSTANCE.getClass();
                                                    Shape inputFieldShape = SearchBarDefaults.getInputFieldShape(composer2);
                                                    TextFieldColors textFieldColors32 = textFieldColorsM285inputFieldColorsJVEmHcM;
                                                    PaddingValuesImpl paddingValuesImplM313contentPaddingWithoutLabela9UjIt4$default = TextFieldDefaults.m313contentPaddingWithoutLabela9UjIt4$default(textFieldDefaults);
                                                    final TextFieldColors textFieldColors4 = textFieldColorsM285inputFieldColorsJVEmHcM;
                                                    final boolean z11 = z92;
                                                    final boolean z12 = zBooleanValue2;
                                                    textFieldDefaults.DecorationBox(str2, function216, z10, true, visualTransformation$Companion$$ExternalSyntheticLambda0, mutableInteractionSource6, false, null, function217, composableLambdaImplRememberComposableLambda, composableLambdaImpl, null, null, null, inputFieldShape, textFieldColors32, paddingValuesImplM313contentPaddingWithoutLabela9UjIt4$default, ComposableLambdaKt.rememberComposableLambda(-1742391428, new Function2() { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23.3
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj5, Object obj6) {
                                                            Composer composer3 = (Composer) obj5;
                                                            if ((((Number) obj6).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl9 = (ComposerImpl) composer3;
                                                                if (composerImpl9.getSkipping()) {
                                                                    composerImpl9.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.InputField.<anonymous>.<anonymous> (SearchBar.kt:1724)");
                                                                    }
                                                                    Object stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColors4.m309containerColorXeAY9LY$material3_release(z11, false, z12), MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composer3), null, composer3, 0, 12);
                                                                    Modifier.Companion companion22 = Modifier.Companion;
                                                                    SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0 searchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0 = new SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac) { // from class: androidx.compose.material3.SearchBarDefaults.InputField.23.3.1
                                                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                                        public final Object get() {
                                                                            return ((State) this.receiver).getValue();
                                                                        }
                                                                    });
                                                                    SearchBarDefaults.INSTANCE.getClass();
                                                                    BoxKt.Box(TextFieldImplKt.textFieldBackground(companion22, searchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0, SearchBarDefaults.getInputFieldShape(composer3)), composer3, 0);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer2), composer2, ((i212 << 3) & 112) | 27648, 113246208, 14528);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    TextFieldColors textFieldColors32 = textFieldColorsM285inputFieldColorsJVEmHcM;
                                    int i212 = i192 | 102236160 | (i202 & 112) | ((i202 >> 9) & 7168);
                                    ComposerImpl composerImpl42 = composerImpl;
                                    BasicTextFieldKt.BasicTextField(str, function1, modifierSemantics2, z92, false, textStyleMerge2, keyboardOptions2, keyboardActions2, true, 0, 0, null, null, mutableInteractionSource52, solidColor2, ComposableLambdaKt.rememberComposableLambda(653749962, function32, composerImpl), composerImpl42, i212, 196608, 7696);
                                    composerImpl2 = composerImpl42;
                                    if (z) {
                                        Boolean boolValueOf2 = Boolean.valueOf(z);
                                        zChanged2 = composerImpl2.changed(z6) | composerImpl2.changedInstance(focusManager);
                                        objRememberedValue5 = composerImpl2.rememberedValue();
                                        if (!zChanged2) {
                                            objRememberedValue5 = new SearchBarDefaults$InputField$24$1(z6, focusManager, null);
                                            composerImpl2.updateRememberedValue(objRememberedValue5);
                                            EffectsKt.LaunchedEffect(composerImpl2, boolValueOf2, (Function2) objRememberedValue5);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            z7 = z92;
                                            mutableInteractionSource4 = mutableInteractionSource2;
                                            textFieldColors2 = textFieldColors32;
                                            function27 = function2132;
                                            modifier4 = modifier52;
                                            function28 = function2142;
                                            function29 = function2152;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            if ((i3 & 4096) == 0) {
            }
            if ((i4 & 306783379) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i7 = i16;
        if ((i2 & 6) != 0) {
        }
        i9 = i3 & 2048;
        if (i9 == 0) {
        }
        if ((i3 & 4096) == 0) {
        }
        if ((i4 & 306783379) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
