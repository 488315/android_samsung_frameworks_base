package androidx.compose.material3;

import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.OutlinedTextFieldTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OutlinedTextFieldDefaults {
    public static final float FocusedBorderThickness;
    public static final OutlinedTextFieldDefaults INSTANCE = new OutlinedTextFieldDefaults();
    public static final float MinHeight;
    public static final float MinWidth;
    public static final float UnfocusedBorderThickness;

    static {
        Dp.Companion companion = Dp.Companion;
        MinHeight = 56;
        MinWidth = IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView;
        UnfocusedBorderThickness = 1;
        FocusedBorderThickness = 2;
    }

    private OutlinedTextFieldDefaults() {
    }

    public static TextFieldColors colors(int i, Composer composer) {
        long Color;
        long Color2;
        long Color3;
        long Color4;
        long Color5;
        long Color6;
        long Color7;
        long Color8;
        long Color9;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.colors (TextFieldDefaults.kt:1214)");
        }
        MaterialTheme.INSTANCE.getClass();
        ColorScheme colorScheme = MaterialTheme.getColorScheme(composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.<get-defaultOutlinedTextFieldColors> (TextFieldDefaults.kt:1364)");
        }
        TextFieldColors textFieldColors = colorScheme.defaultOutlinedTextFieldColorsCached;
        if (textFieldColors == null) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(523393076);
            composerImpl.end(false);
            textFieldColors = null;
        } else {
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            composerImpl2.startReplaceGroup(523393077);
            TextSelectionColors textSelectionColors = (TextSelectionColors) composerImpl2.consume(TextSelectionColorsKt.LocalTextSelectionColors);
            if (!Intrinsics.areEqual(textFieldColors.textSelectionColors, textSelectionColors)) {
                textFieldColors = textFieldColors.m309copyejIjP34(textFieldColors.focusedTextColor, textFieldColors.unfocusedTextColor, textFieldColors.disabledTextColor, textFieldColors.errorTextColor, textFieldColors.focusedContainerColor, textFieldColors.unfocusedContainerColor, textFieldColors.disabledContainerColor, textFieldColors.errorContainerColor, textFieldColors.cursorColor, textFieldColors.errorCursorColor, (r95 & 1024) != 0 ? textFieldColors.textSelectionColors : textSelectionColors, (r95 & 2048) != 0 ? textFieldColors.focusedIndicatorColor : 0L, (r95 & 4096) != 0 ? textFieldColors.unfocusedIndicatorColor : 0L, textFieldColors.disabledIndicatorColor, (r95 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? textFieldColors.errorIndicatorColor : 0L, textFieldColors.focusedLeadingIconColor, textFieldColors.unfocusedLeadingIconColor, textFieldColors.disabledLeadingIconColor, textFieldColors.errorLeadingIconColor, textFieldColors.focusedTrailingIconColor, textFieldColors.unfocusedTrailingIconColor, textFieldColors.disabledTrailingIconColor, textFieldColors.errorTrailingIconColor, textFieldColors.focusedLabelColor, textFieldColors.unfocusedLabelColor, textFieldColors.disabledLabelColor, textFieldColors.errorLabelColor, textFieldColors.focusedPlaceholderColor, textFieldColors.unfocusedPlaceholderColor, textFieldColors.disabledPlaceholderColor, textFieldColors.errorPlaceholderColor, textFieldColors.focusedSupportingTextColor, textFieldColors.unfocusedSupportingTextColor, textFieldColors.disabledSupportingTextColor, textFieldColors.errorSupportingTextColor, textFieldColors.focusedPrefixColor, textFieldColors.unfocusedPrefixColor, textFieldColors.disabledPrefixColor, textFieldColors.errorPrefixColor, textFieldColors.focusedSuffixColor, textFieldColors.unfocusedSuffixColor, textFieldColors.disabledSuffixColor, textFieldColors.errorSuffixColor);
                colorScheme.defaultOutlinedTextFieldColorsCached = textFieldColors;
            }
            composerImpl2.end(false);
        }
        if (textFieldColors == null) {
            ComposerImpl composerImpl3 = (ComposerImpl) composer;
            composerImpl3.startReplaceGroup(1541103159);
            OutlinedTextFieldTokens.INSTANCE.getClass();
            long fromToken = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusInputColor);
            long fromToken2 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.InputColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens = OutlinedTextFieldTokens.DisabledInputColor;
            Color = ColorKt.Color(Color.m461getRedimpl(r9), Color.m460getGreenimpl(r9), Color.m458getBlueimpl(r9), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens)));
            long fromToken3 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorInputColor);
            Color.Companion.getClass();
            long j = Color.Transparent;
            long fromToken4 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.CaretColor);
            long fromToken5 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorFocusCaretColor);
            TextSelectionColors textSelectionColors2 = (TextSelectionColors) composerImpl3.consume(TextSelectionColorsKt.LocalTextSelectionColors);
            long fromToken6 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusOutlineColor);
            long fromToken7 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.OutlineColor);
            Color2 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.12f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledOutlineColor)));
            long fromToken8 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorOutlineColor);
            long fromToken9 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusLeadingIconColor);
            long fromToken10 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.LeadingIconColor);
            Color3 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledLeadingIconColor)));
            long fromToken11 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorLeadingIconColor);
            long fromToken12 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusTrailingIconColor);
            long fromToken13 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.TrailingIconColor);
            Color4 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledTrailingIconColor)));
            long fromToken14 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorTrailingIconColor);
            long fromToken15 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusLabelColor);
            long fromToken16 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.LabelColor);
            Color5 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledLabelColor)));
            long fromToken17 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorLabelColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens2 = OutlinedTextFieldTokens.InputPlaceholderColor;
            long fromToken18 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            long fromToken19 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            Color6 = ColorKt.Color(Color.m461getRedimpl(r2), Color.m460getGreenimpl(r2), Color.m458getBlueimpl(r2), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens)));
            long fromToken20 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            long fromToken21 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusSupportingColor);
            long fromToken22 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.SupportingColor);
            Color7 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledSupportingColor)));
            long fromToken23 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorSupportingColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens3 = OutlinedTextFieldTokens.InputPrefixColor;
            long fromToken24 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            long fromToken25 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            Color8 = ColorKt.Color(Color.m461getRedimpl(r2), Color.m460getGreenimpl(r2), Color.m458getBlueimpl(r2), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3)));
            long fromToken26 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            ColorSchemeKeyTokens colorSchemeKeyTokens4 = OutlinedTextFieldTokens.InputSuffixColor;
            long fromToken27 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
            long fromToken28 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
            Color9 = ColorKt.Color(Color.m461getRedimpl(r2), Color.m460getGreenimpl(r2), Color.m458getBlueimpl(r2), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4)));
            TextFieldColors textFieldColors2 = new TextFieldColors(fromToken, fromToken2, Color, fromToken3, j, j, j, j, fromToken4, fromToken5, textSelectionColors2, fromToken6, fromToken7, Color2, fromToken8, fromToken9, fromToken10, Color3, fromToken11, fromToken12, fromToken13, Color4, fromToken14, fromToken15, fromToken16, Color5, fromToken17, fromToken18, fromToken19, Color6, fromToken20, fromToken21, fromToken22, Color7, fromToken23, fromToken24, fromToken25, Color8, fromToken26, fromToken27, fromToken28, Color9, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4), null);
            colorScheme.defaultOutlinedTextFieldColorsCached = textFieldColors2;
            composerImpl3.end(false);
            textFieldColors = textFieldColors2;
        } else {
            ComposerImpl composerImpl4 = (ComposerImpl) composer;
            composerImpl4.startReplaceGroup(1540908944);
            composerImpl4.end(false);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return textFieldColors;
    }

    public static Shape getShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.<get-shape> (TextFieldDefaults.kt:892)");
        }
        OutlinedTextFieldTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(OutlinedTextFieldTokens.ContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02b9  */
    /* renamed from: Container-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m275Container4EFweAY(final boolean r23, final boolean r24, final androidx.compose.foundation.interaction.InteractionSource r25, androidx.compose.ui.Modifier r26, androidx.compose.material3.TextFieldColors r27, androidx.compose.ui.graphics.Shape r28, float r29, float r30, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instructions count: 723
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.OutlinedTextFieldDefaults.m275Container4EFweAY(boolean, boolean, androidx.compose.foundation.interaction.InteractionSource, androidx.compose.ui.Modifier, androidx.compose.material3.TextFieldColors, androidx.compose.ui.graphics.Shape, float, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x0377, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L260;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0357  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void DecorationBox(final java.lang.String r39, final kotlin.jvm.functions.Function2 r40, final boolean r41, final boolean r42, final androidx.compose.ui.text.input.VisualTransformation r43, final androidx.compose.foundation.interaction.InteractionSource r44, boolean r45, kotlin.jvm.functions.Function2 r46, kotlin.jvm.functions.Function2 r47, kotlin.jvm.functions.Function2 r48, kotlin.jvm.functions.Function2 r49, kotlin.jvm.functions.Function2 r50, kotlin.jvm.functions.Function2 r51, kotlin.jvm.functions.Function2 r52, androidx.compose.material3.TextFieldColors r53, androidx.compose.foundation.layout.PaddingValues r54, kotlin.jvm.functions.Function2 r55, androidx.compose.runtime.Composer r56, final int r57, final int r58, final int r59) {
        /*
            Method dump skipped, instructions count: 1165
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox(java.lang.String, kotlin.jvm.functions.Function2, boolean, boolean, androidx.compose.ui.text.input.VisualTransformation, androidx.compose.foundation.interaction.InteractionSource, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.material3.TextFieldColors, androidx.compose.foundation.layout.PaddingValues, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int, int):void");
    }
}
