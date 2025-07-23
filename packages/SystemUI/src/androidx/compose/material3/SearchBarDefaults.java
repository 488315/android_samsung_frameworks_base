package androidx.compose.material3;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.internal.SystemBarsDefaultInsets_androidKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.ElevationTokens;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.material3.tokens.SearchBarTokens;
import androidx.compose.material3.tokens.SearchViewTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static SearchBarColors m283colorsKlgxPg(long j, Composer composer, int i, int i2) {
        if ((i2 & 1) != 0) {
            SearchBarTokens.INSTANCE.getClass();
            j = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        }
        long j2 = j;
        SearchViewTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SearchViewTokens.DividerColor, composer);
        TextFieldColors m284inputFieldColorsJVEmHcM = m284inputFieldColorsJVEmHcM(j2, j2, j2, composer, i & 7168, 1048575);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.colors (SearchBar.kt:1148)");
        }
        SearchBarColors searchBarColors = new SearchBarColors(j2, value, m284inputFieldColorsJVEmHcM, null);
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
        WindowInsets m148onlybOOhFvg = WindowInsetsKt.m148onlybOOhFvg(systemBarsForVisualComponents, WindowInsetsSides.Horizontal | WindowInsetsSides.Top);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m148onlybOOhFvg;
    }

    /* renamed from: inputFieldColors-JVEmHcM, reason: not valid java name */
    public static TextFieldColors m284inputFieldColorsJVEmHcM(long j, long j2, long j3, Composer composer, int i, int i2) {
        long Color;
        long Color2;
        long Color3;
        long Color4;
        long Color5;
        long Color6;
        long j4;
        long j5;
        long j6;
        SearchBarTokens searchBarTokens = SearchBarTokens.INSTANCE;
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens = SearchBarTokens.InputTextColor;
        long value = ColorSchemeKt.getValue(colorSchemeKeyTokens, composer);
        searchBarTokens.getClass();
        long value2 = ColorSchemeKt.getValue(colorSchemeKeyTokens, composer);
        FilledTextFieldTokens filledTextFieldTokens = FilledTextFieldTokens.INSTANCE;
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = FilledTextFieldTokens.DisabledInputColor;
        long value3 = ColorSchemeKt.getValue(colorSchemeKeyTokens2, composer);
        float f = FilledTextFieldTokens.DisabledInputOpacity;
        Color = ColorKt.Color(Color.m461getRedimpl(value3), Color.m460getGreenimpl(value3), Color.m458getBlueimpl(value3), f, Color.m459getColorSpaceimpl(value3));
        filledTextFieldTokens.getClass();
        long value4 = ColorSchemeKt.getValue(FilledTextFieldTokens.CaretColor, composer);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = TextSelectionColorsKt.LocalTextSelectionColors;
        TextSelectionColors textSelectionColors = (TextSelectionColors) ((ComposerImpl) composer).consume(dynamicProvidableCompositionLocal);
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = SearchBarTokens.LeadingIconColor;
        long value5 = ColorSchemeKt.getValue(colorSchemeKeyTokens3, composer);
        searchBarTokens.getClass();
        long value6 = ColorSchemeKt.getValue(colorSchemeKeyTokens3, composer);
        filledTextFieldTokens.getClass();
        Color2 = ColorKt.Color(Color.m461getRedimpl(r12), Color.m460getGreenimpl(r12), Color.m458getBlueimpl(r12), FilledTextFieldTokens.DisabledLeadingIconOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.getValue(FilledTextFieldTokens.DisabledLeadingIconColor, composer)));
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = SearchBarTokens.TrailingIconColor;
        long value7 = ColorSchemeKt.getValue(colorSchemeKeyTokens4, composer);
        searchBarTokens.getClass();
        long value8 = ColorSchemeKt.getValue(colorSchemeKeyTokens4, composer);
        filledTextFieldTokens.getClass();
        Color3 = ColorKt.Color(Color.m461getRedimpl(r12), Color.m460getGreenimpl(r12), Color.m458getBlueimpl(r12), FilledTextFieldTokens.DisabledTrailingIconOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.getValue(FilledTextFieldTokens.DisabledTrailingIconColor, composer)));
        searchBarTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = SearchBarTokens.SupportingTextColor;
        long value9 = ColorSchemeKt.getValue(colorSchemeKeyTokens5, composer);
        searchBarTokens.getClass();
        long value10 = ColorSchemeKt.getValue(colorSchemeKeyTokens5, composer);
        filledTextFieldTokens.getClass();
        Color4 = ColorKt.Color(Color.m461getRedimpl(r12), Color.m460getGreenimpl(r12), Color.m458getBlueimpl(r12), f, Color.m459getColorSpaceimpl(ColorSchemeKt.getValue(colorSchemeKeyTokens2, composer)));
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens6 = FilledTextFieldTokens.InputPrefixColor;
        long value11 = ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer);
        filledTextFieldTokens.getClass();
        long value12 = ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer);
        filledTextFieldTokens.getClass();
        Color5 = ColorKt.Color(Color.m461getRedimpl(r12), Color.m460getGreenimpl(r12), Color.m458getBlueimpl(r12), f, Color.m459getColorSpaceimpl(ColorSchemeKt.getValue(colorSchemeKeyTokens6, composer)));
        filledTextFieldTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens7 = FilledTextFieldTokens.InputSuffixColor;
        long value13 = ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer);
        filledTextFieldTokens.getClass();
        long value14 = ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer);
        filledTextFieldTokens.getClass();
        Color6 = ColorKt.Color(Color.m461getRedimpl(r2), Color.m460getGreenimpl(r2), Color.m458getBlueimpl(r2), f, Color.m459getColorSpaceimpl(ColorSchemeKt.getValue(colorSchemeKeyTokens7, composer)));
        if ((i2 & 1048576) != 0) {
            searchBarTokens.getClass();
            j4 = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            j4 = j;
        }
        if ((i2 & 2097152) != 0) {
            searchBarTokens.getClass();
            j5 = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            j5 = j2;
        }
        if ((i2 & 4194304) != 0) {
            searchBarTokens.getClass();
            j6 = ColorSchemeKt.getValue(SearchBarTokens.ContainerColor, composer);
        } else {
            j6 = j3;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SearchBarDefaults.inputFieldColors (SearchBar.kt:1231)");
        }
        TextFieldDefaults.INSTANCE.getClass();
        Color.Companion.getClass();
        long j7 = Color.Unspecified;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.colors (TextFieldDefaults.kt:574)");
        }
        MaterialTheme.INSTANCE.getClass();
        TextFieldColors m309copyejIjP34 = TextFieldDefaults.defaultTextFieldColors$material3_release(MaterialTheme.getColorScheme(composer), (TextSelectionColors) ((ComposerImpl) composer).consume(dynamicProvidableCompositionLocal)).m309copyejIjP34(value, value2, Color, j7, j4, j5, j6, j7, value4, j7, textSelectionColors, j7, j7, j7, j7, value5, value6, Color2, j7, value7, value8, Color3, j7, j7, j7, j7, j7, value9, value10, Color4, j7, j7, j7, j7, j7, value11, value12, Color5, j7, value13, value14, Color6, j7);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m309copyejIjP34;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x03cb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x044c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0496  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0309 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0335 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void InputField(final java.lang.String r66, final kotlin.jvm.functions.Function1 r67, final kotlin.jvm.functions.Function1 r68, final boolean r69, final kotlin.jvm.functions.Function1 r70, androidx.compose.ui.Modifier r71, boolean r72, kotlin.jvm.functions.Function2 r73, kotlin.jvm.functions.Function2 r74, kotlin.jvm.functions.Function2 r75, androidx.compose.material3.TextFieldColors r76, androidx.compose.foundation.interaction.MutableInteractionSource r77, androidx.compose.runtime.Composer r78, final int r79, final int r80, final int r81) {
        /*
            Method dump skipped, instructions count: 1205
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SearchBarDefaults.InputField(java.lang.String, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.material3.TextFieldColors, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.runtime.Composer, int, int, int):void");
    }
}
