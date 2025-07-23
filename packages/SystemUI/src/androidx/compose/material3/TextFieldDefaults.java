package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldDefaults {
    public static final float FocusedIndicatorThickness;
    public static final TextFieldDefaults INSTANCE = new TextFieldDefaults();
    public static final float UnfocusedIndicatorThickness;

    static {
        Dp.Companion companion = Dp.Companion;
        UnfocusedIndicatorThickness = 1;
        FocusedIndicatorThickness = 2;
    }

    private TextFieldDefaults() {
    }

    public static TextFieldColors colors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.colors (TextFieldDefaults.kt:472)");
        }
        MaterialTheme.INSTANCE.getClass();
        TextFieldColors defaultTextFieldColors$material3_release = defaultTextFieldColors$material3_release(MaterialTheme.getColorScheme(composer), (TextSelectionColors) ((ComposerImpl) composer).consume(TextSelectionColorsKt.LocalTextSelectionColors));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultTextFieldColors$material3_release;
    }

    /* renamed from: contentPaddingWithoutLabel-a9UjIt4$default, reason: not valid java name */
    public static PaddingValuesImpl m312contentPaddingWithoutLabela9UjIt4$default(TextFieldDefaults textFieldDefaults) {
        float f = TextFieldImplKt.TextFieldPadding;
        textFieldDefaults.getClass();
        return PaddingKt.m122PaddingValuesa9UjIt4(f, f, f, f);
    }

    public static TextFieldColors defaultTextFieldColors$material3_release(ColorScheme colorScheme, TextSelectionColors textSelectionColors) {
        long Color;
        long Color2;
        long Color3;
        long Color4;
        long Color5;
        long Color6;
        long Color7;
        long Color8;
        long Color9;
        TextFieldColors m309copyejIjP34;
        TextFieldColors textFieldColors = colorScheme.defaultTextFieldColorsCached;
        if (textFieldColors != null) {
            if (Intrinsics.areEqual(textFieldColors.textSelectionColors, textSelectionColors)) {
                return textFieldColors;
            }
            m309copyejIjP34 = textFieldColors.m309copyejIjP34(textFieldColors.focusedTextColor, textFieldColors.unfocusedTextColor, textFieldColors.disabledTextColor, textFieldColors.errorTextColor, textFieldColors.focusedContainerColor, textFieldColors.unfocusedContainerColor, textFieldColors.disabledContainerColor, textFieldColors.errorContainerColor, textFieldColors.cursorColor, textFieldColors.errorCursorColor, (r95 & 1024) != 0 ? textFieldColors.textSelectionColors : textSelectionColors, (r95 & 2048) != 0 ? textFieldColors.focusedIndicatorColor : 0L, (r95 & 4096) != 0 ? textFieldColors.unfocusedIndicatorColor : 0L, textFieldColors.disabledIndicatorColor, (r95 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? textFieldColors.errorIndicatorColor : 0L, textFieldColors.focusedLeadingIconColor, textFieldColors.unfocusedLeadingIconColor, textFieldColors.disabledLeadingIconColor, textFieldColors.errorLeadingIconColor, textFieldColors.focusedTrailingIconColor, textFieldColors.unfocusedTrailingIconColor, textFieldColors.disabledTrailingIconColor, textFieldColors.errorTrailingIconColor, textFieldColors.focusedLabelColor, textFieldColors.unfocusedLabelColor, textFieldColors.disabledLabelColor, textFieldColors.errorLabelColor, textFieldColors.focusedPlaceholderColor, textFieldColors.unfocusedPlaceholderColor, textFieldColors.disabledPlaceholderColor, textFieldColors.errorPlaceholderColor, textFieldColors.focusedSupportingTextColor, textFieldColors.unfocusedSupportingTextColor, textFieldColors.disabledSupportingTextColor, textFieldColors.errorSupportingTextColor, textFieldColors.focusedPrefixColor, textFieldColors.unfocusedPrefixColor, textFieldColors.disabledPrefixColor, textFieldColors.errorPrefixColor, textFieldColors.focusedSuffixColor, textFieldColors.unfocusedSuffixColor, textFieldColors.disabledSuffixColor, textFieldColors.errorSuffixColor);
            colorScheme.defaultTextFieldColorsCached = m309copyejIjP34;
            return m309copyejIjP34;
        }
        FilledTextFieldTokens.INSTANCE.getClass();
        long fromToken = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusInputColor);
        long fromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.InputColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens = FilledTextFieldTokens.DisabledInputColor;
        long fromToken3 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        float f = FilledTextFieldTokens.DisabledInputOpacity;
        Color = ColorKt.Color(Color.m461getRedimpl(fromToken3), Color.m460getGreenimpl(fromToken3), Color.m458getBlueimpl(fromToken3), f, Color.m459getColorSpaceimpl(fromToken3));
        long fromToken4 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorInputColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = FilledTextFieldTokens.ContainerColor;
        long fromToken5 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken6 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken7 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken8 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken9 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.CaretColor);
        long fromToken10 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorFocusCaretColor);
        long fromToken11 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusActiveIndicatorColor);
        long fromToken12 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ActiveIndicatorColor);
        Color2 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), FilledTextFieldTokens.DisabledActiveIndicatorOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledActiveIndicatorColor)));
        long fromToken13 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorActiveIndicatorColor);
        long fromToken14 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusLeadingIconColor);
        long fromToken15 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.LeadingIconColor);
        Color3 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), FilledTextFieldTokens.DisabledLeadingIconOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledLeadingIconColor)));
        long fromToken16 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorLeadingIconColor);
        long fromToken17 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusTrailingIconColor);
        long fromToken18 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.TrailingIconColor);
        Color4 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), FilledTextFieldTokens.DisabledTrailingIconOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledTrailingIconColor)));
        long fromToken19 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorTrailingIconColor);
        long fromToken20 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusLabelColor);
        long fromToken21 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.LabelColor);
        Color5 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), FilledTextFieldTokens.DisabledLabelOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledLabelColor)));
        long fromToken22 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorLabelColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = FilledTextFieldTokens.InputPlaceholderColor;
        long fromToken23 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        long fromToken24 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        Color6 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens)));
        long fromToken25 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        long fromToken26 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusSupportingColor);
        long fromToken27 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.SupportingColor);
        Color7 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), FilledTextFieldTokens.DisabledSupportingOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledSupportingColor)));
        long fromToken28 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorSupportingColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = FilledTextFieldTokens.InputPrefixColor;
        long fromToken29 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        long fromToken30 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        Color8 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4)));
        long fromToken31 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = FilledTextFieldTokens.InputSuffixColor;
        long fromToken32 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5);
        long fromToken33 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5);
        Color9 = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5)));
        TextFieldColors textFieldColors2 = new TextFieldColors(fromToken, fromToken2, Color, fromToken4, fromToken5, fromToken6, fromToken7, fromToken8, fromToken9, fromToken10, textSelectionColors, fromToken11, fromToken12, Color2, fromToken13, fromToken14, fromToken15, Color3, fromToken16, fromToken17, fromToken18, Color4, fromToken19, fromToken20, fromToken21, Color5, fromToken22, fromToken23, fromToken24, Color6, fromToken25, fromToken26, fromToken27, Color7, fromToken28, fromToken29, fromToken30, Color8, fromToken31, fromToken32, fromToken33, Color9, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5), null);
        colorScheme.defaultTextFieldColorsCached = textFieldColors2;
        return textFieldColors2;
    }

    public static Shape getShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.<get-shape> (TextFieldDefaults.kt:68)");
        }
        FilledTextFieldTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(FilledTextFieldTokens.ContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    /* renamed from: supportingTextPadding-a9UjIt4$material3_release$default, reason: not valid java name */
    public static PaddingValuesImpl m313supportingTextPaddinga9UjIt4$material3_release$default(TextFieldDefaults textFieldDefaults) {
        float f = TextFieldImplKt.TextFieldPadding;
        Dp.Companion companion = Dp.Companion;
        textFieldDefaults.getClass();
        return PaddingKt.m122PaddingValuesa9UjIt4(f, TextFieldImplKt.SupportingTopPadding, f, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0185  */
    /* renamed from: Container-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m314Container4EFweAY(final boolean r21, final boolean r22, final androidx.compose.foundation.interaction.InteractionSource r23, androidx.compose.ui.Modifier r24, androidx.compose.material3.TextFieldColors r25, androidx.compose.ui.graphics.Shape r26, float r27, float r28, androidx.compose.runtime.Composer r29, final int r30, final int r31) {
        /*
            Method dump skipped, instructions count: 528
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextFieldDefaults.m314Container4EFweAY(boolean, boolean, androidx.compose.foundation.interaction.InteractionSource, androidx.compose.ui.Modifier, androidx.compose.material3.TextFieldColors, androidx.compose.ui.graphics.Shape, float, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x03be, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L282;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x03ff  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x040c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x029b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void DecorationBox(final java.lang.String r40, final kotlin.jvm.functions.Function2 r41, final boolean r42, final boolean r43, final androidx.compose.ui.text.input.VisualTransformation r44, final androidx.compose.foundation.interaction.InteractionSource r45, boolean r46, kotlin.jvm.functions.Function2 r47, kotlin.jvm.functions.Function2 r48, kotlin.jvm.functions.Function2 r49, kotlin.jvm.functions.Function2 r50, kotlin.jvm.functions.Function2 r51, kotlin.jvm.functions.Function2 r52, kotlin.jvm.functions.Function2 r53, androidx.compose.ui.graphics.Shape r54, androidx.compose.material3.TextFieldColors r55, androidx.compose.foundation.layout.PaddingValues r56, kotlin.jvm.functions.Function2 r57, androidx.compose.runtime.Composer r58, final int r59, final int r60, final int r61) {
        /*
            Method dump skipped, instructions count: 1223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextFieldDefaults.DecorationBox(java.lang.String, kotlin.jvm.functions.Function2, boolean, boolean, androidx.compose.ui.text.input.VisualTransformation, androidx.compose.foundation.interaction.InteractionSource, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.ui.graphics.Shape, androidx.compose.material3.TextFieldColors, androidx.compose.foundation.layout.PaddingValues, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int, int):void");
    }
}
