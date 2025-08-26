package androidx.compose.material3;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.material3.internal.TextFieldType;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.FilledTextFieldTokens;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;

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
        TextFieldColors textFieldColorsDefaultTextFieldColors$material3_release = defaultTextFieldColors$material3_release(MaterialTheme.getColorScheme(composer), (TextSelectionColors) ((ComposerImpl) composer).consume(TextSelectionColorsKt.LocalTextSelectionColors));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return textFieldColorsDefaultTextFieldColors$material3_release;
    }

    /* renamed from: contentPaddingWithoutLabel-a9UjIt4$default, reason: not valid java name */
    public static PaddingValuesImpl m313contentPaddingWithoutLabela9UjIt4$default(TextFieldDefaults textFieldDefaults) {
        float f = TextFieldImplKt.TextFieldPadding;
        textFieldDefaults.getClass();
        return PaddingKt.m123PaddingValuesa9UjIt4(f, f, f, f);
    }

    public static TextFieldColors defaultTextFieldColors$material3_release(ColorScheme colorScheme, TextSelectionColors textSelectionColors) {
        TextFieldColors textFieldColors = colorScheme.defaultTextFieldColorsCached;
        if (textFieldColors != null) {
            if (Intrinsics.areEqual(textFieldColors.textSelectionColors, textSelectionColors)) {
                return textFieldColors;
            }
            TextFieldColors textFieldColorsM310copyejIjP34 = textFieldColors.m310copyejIjP34(textFieldColors.focusedTextColor, textFieldColors.unfocusedTextColor, textFieldColors.disabledTextColor, textFieldColors.errorTextColor, textFieldColors.focusedContainerColor, textFieldColors.unfocusedContainerColor, textFieldColors.disabledContainerColor, textFieldColors.errorContainerColor, textFieldColors.cursorColor, textFieldColors.errorCursorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 1024) != 0 ? textFieldColors.textSelectionColors : textSelectionColors, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 2048) != 0 ? textFieldColors.focusedIndicatorColor : 0L, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 4096) != 0 ? textFieldColors.unfocusedIndicatorColor : 0L, textFieldColors.disabledIndicatorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? textFieldColors.errorIndicatorColor : 0L, textFieldColors.focusedLeadingIconColor, textFieldColors.unfocusedLeadingIconColor, textFieldColors.disabledLeadingIconColor, textFieldColors.errorLeadingIconColor, textFieldColors.focusedTrailingIconColor, textFieldColors.unfocusedTrailingIconColor, textFieldColors.disabledTrailingIconColor, textFieldColors.errorTrailingIconColor, textFieldColors.focusedLabelColor, textFieldColors.unfocusedLabelColor, textFieldColors.disabledLabelColor, textFieldColors.errorLabelColor, textFieldColors.focusedPlaceholderColor, textFieldColors.unfocusedPlaceholderColor, textFieldColors.disabledPlaceholderColor, textFieldColors.errorPlaceholderColor, textFieldColors.focusedSupportingTextColor, textFieldColors.unfocusedSupportingTextColor, textFieldColors.disabledSupportingTextColor, textFieldColors.errorSupportingTextColor, textFieldColors.focusedPrefixColor, textFieldColors.unfocusedPrefixColor, textFieldColors.disabledPrefixColor, textFieldColors.errorPrefixColor, textFieldColors.focusedSuffixColor, textFieldColors.unfocusedSuffixColor, textFieldColors.disabledSuffixColor, textFieldColors.errorSuffixColor);
            colorScheme.defaultTextFieldColorsCached = textFieldColorsM310copyejIjP34;
            return textFieldColorsM310copyejIjP34;
        }
        FilledTextFieldTokens.INSTANCE.getClass();
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusInputColor);
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.InputColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens = FilledTextFieldTokens.DisabledInputColor;
        long jFromToken3 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        float f = FilledTextFieldTokens.DisabledInputOpacity;
        long jColor = ColorKt.Color(Color.m463getRedimpl(jFromToken3), Color.m462getGreenimpl(jFromToken3), Color.m460getBlueimpl(jFromToken3), f, Color.m461getColorSpaceimpl(jFromToken3));
        long jFromToken4 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorInputColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = FilledTextFieldTokens.ContainerColor;
        long jFromToken5 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken6 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken7 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken8 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken9 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.CaretColor);
        long jFromToken10 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorFocusCaretColor);
        long jFromToken11 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusActiveIndicatorColor);
        long jFromToken12 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ActiveIndicatorColor);
        long jFromToken13 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledActiveIndicatorColor);
        long jColor2 = ColorKt.Color(Color.m463getRedimpl(jFromToken13), Color.m462getGreenimpl(jFromToken13), Color.m460getBlueimpl(jFromToken13), FilledTextFieldTokens.DisabledActiveIndicatorOpacity, Color.m461getColorSpaceimpl(jFromToken13));
        long jFromToken14 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorActiveIndicatorColor);
        long jFromToken15 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusLeadingIconColor);
        long jFromToken16 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.LeadingIconColor);
        long jFromToken17 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledLeadingIconColor);
        long jColor3 = ColorKt.Color(Color.m463getRedimpl(jFromToken17), Color.m462getGreenimpl(jFromToken17), Color.m460getBlueimpl(jFromToken17), FilledTextFieldTokens.DisabledLeadingIconOpacity, Color.m461getColorSpaceimpl(jFromToken17));
        long jFromToken18 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorLeadingIconColor);
        long jFromToken19 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusTrailingIconColor);
        long jFromToken20 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.TrailingIconColor);
        long jFromToken21 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledTrailingIconColor);
        long jColor4 = ColorKt.Color(Color.m463getRedimpl(jFromToken21), Color.m462getGreenimpl(jFromToken21), Color.m460getBlueimpl(jFromToken21), FilledTextFieldTokens.DisabledTrailingIconOpacity, Color.m461getColorSpaceimpl(jFromToken21));
        long jFromToken22 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorTrailingIconColor);
        long jFromToken23 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusLabelColor);
        long jFromToken24 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.LabelColor);
        long jFromToken25 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledLabelColor);
        long jColor5 = ColorKt.Color(Color.m463getRedimpl(jFromToken25), Color.m462getGreenimpl(jFromToken25), Color.m460getBlueimpl(jFromToken25), FilledTextFieldTokens.DisabledLabelOpacity, Color.m461getColorSpaceimpl(jFromToken25));
        long jFromToken26 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorLabelColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = FilledTextFieldTokens.InputPlaceholderColor;
        long jFromToken27 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        long jFromToken28 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        long jFromToken29 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        long jColor6 = ColorKt.Color(Color.m463getRedimpl(jFromToken29), Color.m462getGreenimpl(jFromToken29), Color.m460getBlueimpl(jFromToken29), f, Color.m461getColorSpaceimpl(jFromToken29));
        long jFromToken30 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        long jFromToken31 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.FocusSupportingColor);
        long jFromToken32 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.SupportingColor);
        long jFromToken33 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.DisabledSupportingColor);
        long jColor7 = ColorKt.Color(Color.m463getRedimpl(jFromToken33), Color.m462getGreenimpl(jFromToken33), Color.m460getBlueimpl(jFromToken33), FilledTextFieldTokens.DisabledSupportingOpacity, Color.m461getColorSpaceimpl(jFromToken33));
        long jFromToken34 = ColorSchemeKt.fromToken(colorScheme, FilledTextFieldTokens.ErrorSupportingColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = FilledTextFieldTokens.InputPrefixColor;
        long jFromToken35 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        long jFromToken36 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        long jFromToken37 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        long jColor8 = ColorKt.Color(Color.m463getRedimpl(jFromToken37), Color.m462getGreenimpl(jFromToken37), Color.m460getBlueimpl(jFromToken37), f, Color.m461getColorSpaceimpl(jFromToken37));
        long jFromToken38 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = FilledTextFieldTokens.InputSuffixColor;
        long jFromToken39 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5);
        long jFromToken40 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5);
        long jFromToken41 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5);
        TextFieldColors textFieldColors2 = new TextFieldColors(jFromToken, jFromToken2, jColor, jFromToken4, jFromToken5, jFromToken6, jFromToken7, jFromToken8, jFromToken9, jFromToken10, textSelectionColors, jFromToken11, jFromToken12, jColor2, jFromToken14, jFromToken15, jFromToken16, jColor3, jFromToken18, jFromToken19, jFromToken20, jColor4, jFromToken22, jFromToken23, jFromToken24, jColor5, jFromToken26, jFromToken27, jFromToken28, jColor6, jFromToken30, jFromToken31, jFromToken32, jColor7, jFromToken34, jFromToken35, jFromToken36, jColor8, jFromToken38, jFromToken39, jFromToken40, ColorKt.Color(Color.m463getRedimpl(jFromToken41), Color.m462getGreenimpl(jFromToken41), Color.m460getBlueimpl(jFromToken41), f, Color.m461getColorSpaceimpl(jFromToken41)), ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens5), null);
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
    public static PaddingValuesImpl m314supportingTextPaddinga9UjIt4$material3_release$default(TextFieldDefaults textFieldDefaults) {
        float f = TextFieldImplKt.TextFieldPadding;
        Dp.Companion companion = Dp.Companion;
        textFieldDefaults.getClass();
        return PaddingKt.m123PaddingValuesa9UjIt4(f, TextFieldImplKt.SupportingTopPadding, f, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:154:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00ed  */
    /* renamed from: Container-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m315Container4EFweAY(final boolean z, final boolean z2, final InteractionSource interactionSource, Modifier modifier, TextFieldColors textFieldColors, Shape shape, float f, float f2, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        TextFieldColors textFieldColors2;
        Shape shape2;
        float f3;
        float f4;
        TextFieldColors textFieldColorsColors;
        Shape shape3;
        float f5;
        Modifier modifier3;
        Shape shape4;
        float f6;
        ComposerImpl composerImpl;
        final Shape shape5;
        final Modifier modifier4;
        final float f7;
        final float f8;
        final TextFieldColors textFieldColors3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-818661242);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(z) ? 4 : 2) | i;
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
        } else if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(interactionSource) ? 256 : 128;
        }
        int i5 = i2 & 8;
        if (i5 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                if ((i2 & 16) == 0) {
                    textFieldColors2 = textFieldColors;
                    int i6 = composerImpl2.changed(textFieldColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    i3 |= i6;
                } else {
                    textFieldColors2 = textFieldColors;
                }
                i3 |= i6;
            } else {
                textFieldColors2 = textFieldColors;
            }
            if ((196608 & i) != 0) {
                if ((i2 & 32) == 0) {
                    shape2 = shape;
                    int i7 = composerImpl2.changed(shape2) ? 131072 : 65536;
                    i3 |= i7;
                } else {
                    shape2 = shape;
                }
                i3 |= i7;
            } else {
                shape2 = shape;
            }
            if ((1572864 & i) != 0) {
                if ((i2 & 64) == 0) {
                    f3 = f;
                    if (composerImpl2.changed(f3)) {
                        i4 = 1048576;
                    }
                    i3 |= i4;
                } else {
                    f3 = f;
                }
                i4 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                i3 |= i4;
            } else {
                f3 = f;
            }
            if ((12582912 & i) != 0) {
                if ((i2 & 128) == 0) {
                    f4 = f2;
                    int i8 = composerImpl2.changed(f4) ? 8388608 : 4194304;
                    i3 |= i8;
                } else {
                    f4 = f2;
                }
                i3 |= i8;
            } else {
                f4 = f2;
            }
            if ((i2 & 256) != 0) {
                if ((i & 100663296) == 0) {
                    i3 |= composerImpl2.changed(this) ? 67108864 : 33554432;
                }
                if ((38347923 & i3) == 38347922 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    modifier4 = modifier2;
                    textFieldColors3 = textFieldColors2;
                    shape5 = shape2;
                    f8 = f3;
                    f7 = f4;
                    composerImpl = composerImpl2;
                } else {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                        Modifier modifier5 = i5 == 0 ? Modifier.Companion : modifier2;
                        if ((i2 & 16) == 0) {
                            textFieldColorsColors = colors(composerImpl2);
                            i3 &= -57345;
                        } else {
                            textFieldColorsColors = textFieldColors2;
                        }
                        if ((i2 & 32) == 0) {
                            INSTANCE.getClass();
                            shape3 = getShape(composerImpl2);
                            i3 &= -458753;
                        } else {
                            shape3 = shape2;
                        }
                        if ((i2 & 64) == 0) {
                            i3 &= -3670017;
                            f5 = FocusedIndicatorThickness;
                        } else {
                            f5 = f3;
                        }
                        if ((i2 & 128) == 0) {
                            i3 &= -29360129;
                            modifier3 = modifier5;
                            shape4 = shape3;
                            f6 = UnfocusedIndicatorThickness;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.Container (TextFieldDefaults.kt:238)");
                            }
                            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColorsColors.m309containerColorXeAY9LY$material3_release(z, z2, ((Boolean) FocusInteractionKt.collectIsFocusedAsState(interactionSource, composerImpl2, (i3 >> 6) & 14).getValue()).booleanValue()), MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl2), null, composerImpl2, 0, 12);
                            composerImpl = composerImpl2;
                            TextFieldColors textFieldColors4 = textFieldColorsColors;
                            float f9 = f5;
                            BoxKt.Box(TextFieldImplKt.textFieldBackground(modifier3, new TextFieldDefaults$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac) { // from class: androidx.compose.material3.TextFieldDefaults$Container$1
                                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                public final Object get() {
                                    return ((State) this.receiver).getValue();
                                }
                            }), shape4).then(new IndicatorLineElement(z, z2, interactionSource, textFieldColors4, shape4, f9, f6, null)), composerImpl, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            float f10 = f6;
                            shape5 = shape4;
                            modifier4 = modifier3;
                            f7 = f10;
                            f8 = f9;
                            textFieldColors3 = textFieldColors4;
                        } else {
                            modifier3 = modifier5;
                            shape4 = shape3;
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                        if ((i2 & 16) != 0) {
                            i3 &= -57345;
                        }
                        if ((i2 & 32) != 0) {
                            i3 &= -458753;
                        }
                        if ((i2 & 64) != 0) {
                            i3 &= -3670017;
                        }
                        if ((i2 & 128) != 0) {
                            i3 &= -29360129;
                        }
                        shape4 = shape2;
                        f5 = f3;
                        modifier3 = modifier2;
                        textFieldColorsColors = textFieldColors2;
                    }
                    f6 = f4;
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    final Object stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColorsColors.m309containerColorXeAY9LY$material3_release(z, z2, ((Boolean) FocusInteractionKt.collectIsFocusedAsState(interactionSource, composerImpl2, (i3 >> 6) & 14).getValue()).booleanValue()), MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl2), null, composerImpl2, 0, 12);
                    composerImpl = composerImpl2;
                    TextFieldColors textFieldColors42 = textFieldColorsColors;
                    float f92 = f5;
                    BoxKt.Box(TextFieldImplKt.textFieldBackground(modifier3, new TextFieldDefaults$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac2) { // from class: androidx.compose.material3.TextFieldDefaults$Container$1
                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                        public final Object get() {
                            return ((State) this.receiver).getValue();
                        }
                    }), shape4).then(new IndicatorLineElement(z, z2, interactionSource, textFieldColors42, shape4, f92, f6, null)), composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    float f102 = f6;
                    shape5 = shape4;
                    modifier4 = modifier3;
                    f7 = f102;
                    f8 = f92;
                    textFieldColors3 = textFieldColors42;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextFieldDefaults$Container$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            this.$tmp0_rcvr.m315Container4EFweAY(z, z2, interactionSource, modifier4, textFieldColors3, shape5, f8, f7, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 100663296;
            if ((38347923 & i3) == 38347922) {
                composerImpl2.startDefaults();
                if ((i & 1) != 0) {
                    if (i5 == 0) {
                    }
                    if ((i2 & 16) == 0) {
                    }
                    if ((i2 & 32) == 0) {
                    }
                    if ((i2 & 64) == 0) {
                    }
                    if ((i2 & 128) == 0) {
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        if ((i & 24576) != 0) {
        }
        if ((196608 & i) != 0) {
        }
        if ((1572864 & i) != 0) {
        }
        if ((12582912 & i) != 0) {
        }
        if ((i2 & 256) != 0) {
        }
        if ((38347923 & i3) == 38347922) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:296:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void DecorationBox(final String str, final Function2 function2, final boolean z, final boolean z2, final VisualTransformation visualTransformation, final InteractionSource interactionSource, boolean z3, Function2 function22, Function2 function23, Function2 function24, Function2 function25, Function2 function26, Function2 function27, Function2 function28, Shape shape, TextFieldColors textFieldColors, PaddingValues paddingValues, Function2 function29, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        Function2 function210;
        boolean z4;
        InteractionSource interactionSource2;
        int i5;
        boolean z5;
        int i6;
        Function2 function211;
        int i7;
        Function2 function212;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        Shape shape2;
        TextFieldColors textFieldColorsColors;
        Function2 function213;
        final Shape shape3;
        PaddingValues paddingValuesM123PaddingValuesa9UjIt4;
        Shape shape4;
        Function2 function2RememberComposableLambda;
        PaddingValues paddingValues2;
        Function2 function214;
        Function2 function215;
        final Function2 function216;
        TextFieldColors textFieldColors2;
        Function2 function217;
        Function2 function218;
        Function2 function219;
        Function2 function220;
        boolean z6;
        boolean z7;
        boolean z8;
        ComposerImpl composerImpl;
        final Function2 function221;
        final Function2 function222;
        final Function2 function223;
        final Function2 function224;
        final Function2 function225;
        final Function2 function226;
        final Function2 function227;
        final boolean z9;
        final PaddingValues paddingValues3;
        final TextFieldColors textFieldColors3;
        final Function2 function228;
        final Shape shape5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(289640444);
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
                function210 = function2;
                i4 |= composerImpl2.changedInstance(function210) ? 32 : 16;
            }
            if ((i3 & 4) == 0) {
                i4 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z4 = z;
                    i4 |= composerImpl2.changed(z4) ? 256 : 128;
                }
                if ((i3 & 8) != 0) {
                    i4 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        i4 |= composerImpl2.changed(z2) ? 2048 : 1024;
                    }
                    int i20 = 8192;
                    if ((i3 & 16) == 0) {
                        i4 |= 24576;
                    } else if ((i & 24576) == 0) {
                        i4 |= composerImpl2.changed(visualTransformation) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if ((i3 & 32) == 0) {
                        i4 |= 196608;
                    } else {
                        if ((i & 196608) == 0) {
                            interactionSource2 = interactionSource;
                            i4 |= composerImpl2.changed(interactionSource2) ? 131072 : 65536;
                        }
                        i5 = i3 & 64;
                        if (i5 != 0) {
                            i4 |= 1572864;
                            z5 = z3;
                        } else {
                            z5 = z3;
                            if ((i & 1572864) == 0) {
                                i4 |= composerImpl2.changed(z5) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                        }
                        i6 = i3 & 128;
                        if (i6 != 0) {
                            i4 |= 12582912;
                            function211 = function22;
                        } else {
                            function211 = function22;
                            if ((i & 12582912) == 0) {
                                i4 |= composerImpl2.changedInstance(function211) ? 8388608 : 4194304;
                            }
                        }
                        i7 = i3 & 256;
                        if (i7 != 0) {
                            i4 |= 100663296;
                            function212 = function23;
                        } else {
                            function212 = function23;
                            if ((i & 100663296) == 0) {
                                i4 |= composerImpl2.changedInstance(function212) ? 67108864 : 33554432;
                            }
                        }
                        i8 = i3 & 512;
                        if (i8 != 0) {
                            i4 |= 805306368;
                        } else {
                            if ((i & 805306368) == 0) {
                                i9 = i8;
                                i4 |= composerImpl2.changedInstance(function24) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            i10 = i3 & 1024;
                            if (i10 == 0) {
                                i12 = i2 | 6;
                                i11 = i10;
                            } else if ((i2 & 6) == 0) {
                                i11 = i10;
                                i12 = i2 | (composerImpl2.changedInstance(function25) ? 4 : 2);
                            } else {
                                i11 = i10;
                                i12 = i2;
                            }
                            i13 = i3 & 2048;
                            if (i13 == 0) {
                                i12 |= 48;
                                i14 = i13;
                            } else if ((i2 & 48) == 0) {
                                i14 = i13;
                                i12 |= composerImpl2.changedInstance(function26) ? 32 : 16;
                            } else {
                                i14 = i13;
                            }
                            int i21 = i12;
                            i15 = i3 & 4096;
                            if (i15 == 0) {
                                i16 = i21 | 384;
                            } else {
                                int i22 = i21;
                                if ((i2 & 384) == 0) {
                                    i22 |= composerImpl2.changedInstance(function27) ? 256 : 128;
                                }
                                i16 = i22;
                            }
                            i17 = i3 & 8192;
                            if (i17 != 0) {
                                i18 = i16;
                                if ((i2 & 3072) == 0) {
                                    i18 |= composerImpl2.changedInstance(function28) ? 2048 : 1024;
                                }
                                if ((i2 & 24576) == 0) {
                                    if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0 && composerImpl2.changed(shape)) {
                                        i20 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                                    }
                                    i18 |= i20;
                                }
                                if ((i2 & 196608) == 0) {
                                    i18 |= ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0 && composerImpl2.changed(textFieldColors)) ? 131072 : 65536;
                                }
                                if ((i2 & 1572864) == 0) {
                                    i18 |= ((i3 & 65536) == 0 && composerImpl2.changed(paddingValues)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                                }
                                i19 = i3 & 131072;
                                if (i19 != 0) {
                                    i18 |= 12582912;
                                } else if ((i2 & 12582912) == 0) {
                                    i18 |= composerImpl2.changedInstance(function29) ? 8388608 : 4194304;
                                }
                                if ((i3 & 262144) != 0) {
                                    i18 |= 100663296;
                                } else if ((i2 & 100663296) == 0) {
                                    i18 |= composerImpl2.changed(this) ? 67108864 : 33554432;
                                }
                                if ((i4 & 306783379) == 306783378 && (i18 & 38347923) == 38347922 && composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    function224 = function25;
                                    function225 = function26;
                                    function227 = function28;
                                    shape5 = shape;
                                    textFieldColors3 = textFieldColors;
                                    paddingValues3 = paddingValues;
                                    function228 = function29;
                                    composerImpl = composerImpl2;
                                    function222 = function212;
                                    z9 = z5;
                                    function221 = function211;
                                    function223 = function24;
                                    function226 = function27;
                                } else {
                                    composerImpl2.startDefaults();
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = null;
                                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                        if (i5 != 0) {
                                            z5 = false;
                                        }
                                        if (i6 != 0) {
                                            function211 = null;
                                        }
                                        if (i7 != 0) {
                                            function212 = null;
                                        }
                                        Function2 function229 = i9 == 0 ? null : function24;
                                        Function2 function230 = i11 == 0 ? null : function25;
                                        Function2 function231 = i14 == 0 ? null : function26;
                                        Function2 function232 = i15 == 0 ? null : function27;
                                        Function2 function233 = i17 == 0 ? null : function28;
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                            INSTANCE.getClass();
                                            shape2 = getShape(composerImpl2);
                                            i18 &= -57345;
                                        } else {
                                            shape2 = shape;
                                        }
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
                                            textFieldColorsColors = colors(composerImpl2);
                                            i18 &= -458753;
                                        } else {
                                            textFieldColorsColors = textFieldColors;
                                        }
                                        if ((i3 & 65536) == 0) {
                                            if (function211 == null) {
                                                function213 = function229;
                                                paddingValuesM123PaddingValuesa9UjIt4 = m313contentPaddingWithoutLabela9UjIt4$default(this);
                                                shape3 = shape2;
                                            } else {
                                                function213 = function229;
                                                float f = TextFieldImplKt.TextFieldPadding;
                                                shape3 = shape2;
                                                float f2 = TextFieldKt.TextFieldWithLabelVerticalPadding;
                                                paddingValuesM123PaddingValuesa9UjIt4 = PaddingKt.m123PaddingValuesa9UjIt4(f, f2, f, f2);
                                            }
                                            i18 &= -3670017;
                                        } else {
                                            function213 = function229;
                                            shape3 = shape2;
                                            paddingValuesM123PaddingValuesa9UjIt4 = paddingValues;
                                        }
                                        if (i19 == 0) {
                                            final InteractionSource interactionSource3 = interactionSource2;
                                            final boolean z10 = z5;
                                            final boolean z11 = z4;
                                            final TextFieldColors textFieldColors4 = textFieldColorsColors;
                                            shape4 = shape3;
                                            paddingValues2 = paddingValuesM123PaddingValuesa9UjIt4;
                                            function214 = function212;
                                            function2RememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-435523791, new Function2() { // from class: androidx.compose.material3.TextFieldDefaults.DecorationBox.1
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
                                                                ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.DecorationBox.<anonymous> (TextFieldDefaults.kt:384)");
                                                            }
                                                            TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
                                                            boolean z12 = z11;
                                                            boolean z13 = z10;
                                                            InteractionSource interactionSource4 = interactionSource3;
                                                            Modifier.Companion companion = Modifier.Companion;
                                                            TextFieldColors textFieldColors5 = textFieldColors4;
                                                            Shape shape6 = shape3;
                                                            textFieldDefaults.getClass();
                                                            textFieldDefaults.m315Container4EFweAY(z12, z13, interactionSource4, companion, textFieldColors5, shape6, TextFieldDefaults.FocusedIndicatorThickness, TextFieldDefaults.UnfocusedIndicatorThickness, composer2, 114822144, 0);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl2);
                                        } else {
                                            shape4 = shape3;
                                            function2RememberComposableLambda = function29;
                                            paddingValues2 = paddingValuesM123PaddingValuesa9UjIt4;
                                            function214 = function212;
                                        }
                                        function215 = function231;
                                        function216 = function211;
                                        textFieldColors2 = textFieldColorsColors;
                                        function217 = function232;
                                        function218 = function213;
                                        function219 = function233;
                                        function220 = function230;
                                        z6 = z5;
                                    } else {
                                        composerImpl2.skipToGroupEnd();
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                            i18 &= -57345;
                                        }
                                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                                            i18 &= -458753;
                                        }
                                        if ((i3 & 65536) != 0) {
                                            i18 &= -3670017;
                                        }
                                        function218 = function24;
                                        function215 = function26;
                                        function217 = function27;
                                        function219 = function28;
                                        shape4 = shape;
                                        textFieldColors2 = textFieldColors;
                                        paddingValues2 = paddingValues;
                                        function2RememberComposableLambda = function29;
                                        function214 = function212;
                                        z6 = z5;
                                        function216 = function211;
                                        function220 = function25;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.DecorationBox (TextFieldDefaults.kt:395)");
                                    }
                                    z7 = ((i4 & 14) != 4) | ((57344 & i4) == 16384);
                                    Object objRememberedValue = composerImpl2.rememberedValue();
                                    if (z7) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = visualTransformation.filter(new AnnotatedString(str, null, null, 6, null));
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        String str2 = ((TransformedText) objRememberedValue).text.text;
                                        TextFieldType textFieldType = TextFieldType.Filled;
                                        TextFieldLabelPosition.Attached attached = new TextFieldLabelPosition.Attached(false, null, null, 7, null);
                                        if (function216 == null) {
                                            composerImpl2.startReplaceGroup(-1626079295);
                                            z8 = false;
                                        } else {
                                            composerImpl2.startReplaceGroup(-1626079294);
                                            composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-419785962, new Function3() { // from class: androidx.compose.material3.TextFieldDefaults$DecorationBox$2$1
                                                {
                                                    super(3);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                    Composer composer2 = (Composer) obj2;
                                                    if ((((Number) obj3).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                        if (composerImpl3.getSkipping()) {
                                                            composerImpl3.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.TextFieldDefaults.DecorationBox.<anonymous>.<anonymous> (TextFieldDefaults.kt:409)");
                                                            }
                                                            function216.invoke(composer2, 0);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl2);
                                            z8 = false;
                                        }
                                        composerImpl2.end(z8);
                                        Function2 function234 = function216;
                                        int i23 = i4 >> 9;
                                        int i24 = i18 << 21;
                                        Shape shape6 = shape4;
                                        composerImpl = composerImpl2;
                                        TextFieldImplKt.CommonDecorationBox(textFieldType, str2, function210, attached, composableLambdaImplRememberComposableLambda, function214, function218, function220, function215, function217, function219, z2, z, z6, interactionSource, paddingValues2, textFieldColors2, function2RememberComposableLambda, composerImpl, ((i4 << 3) & 896) | 6 | (i23 & 458752) | (i23 & 3670016) | (i24 & 29360128) | (i24 & 234881024) | (i24 & 1879048192), (i23 & 7168) | ((i18 >> 9) & 14) | ((i4 >> 6) & 112) | (i4 & 896) | (57344 & (i4 >> 3)) | ((i18 >> 3) & 458752) | ((i18 << 3) & 3670016) | (i18 & 29360128));
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        function221 = function234;
                                        function222 = function214;
                                        function223 = function218;
                                        function224 = function220;
                                        function225 = function215;
                                        function226 = function217;
                                        function227 = function219;
                                        z9 = z6;
                                        paddingValues3 = paddingValues2;
                                        textFieldColors3 = textFieldColors2;
                                        function228 = function2RememberComposableLambda;
                                        shape5 = shape6;
                                    }
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextFieldDefaults.DecorationBox.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ((Number) obj2).intValue();
                                            TextFieldDefaults.this.DecorationBox(str, function2, z, z2, visualTransformation, interactionSource, z9, function221, function222, function223, function224, function225, function226, function227, shape5, textFieldColors3, paddingValues3, function228, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i18 = i16 | 3072;
                            if ((i2 & 24576) == 0) {
                            }
                            if ((i2 & 196608) == 0) {
                            }
                            if ((i2 & 1572864) == 0) {
                            }
                            i19 = i3 & 131072;
                            if (i19 != 0) {
                            }
                            if ((i3 & 262144) != 0) {
                            }
                            if ((i4 & 306783379) == 306783378) {
                                composerImpl2.startDefaults();
                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = null;
                                if ((i & 1) != 0) {
                                    if (i5 != 0) {
                                    }
                                    if (i6 != 0) {
                                    }
                                    if (i7 != 0) {
                                    }
                                    if (i9 == 0) {
                                    }
                                    if (i11 == 0) {
                                    }
                                    if (i14 == 0) {
                                    }
                                    if (i15 == 0) {
                                    }
                                    if (i17 == 0) {
                                    }
                                    if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                    }
                                    if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
                                    }
                                    if ((i3 & 65536) == 0) {
                                    }
                                    if (i19 == 0) {
                                    }
                                    function215 = function231;
                                    function216 = function211;
                                    textFieldColors2 = textFieldColorsColors;
                                    function217 = function232;
                                    function218 = function213;
                                    function219 = function233;
                                    function220 = function230;
                                    z6 = z5;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    z7 = ((i4 & 14) != 4) | ((57344 & i4) == 16384);
                                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                                    if (z7) {
                                    }
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i9 = i8;
                        i10 = i3 & 1024;
                        if (i10 == 0) {
                        }
                        i13 = i3 & 2048;
                        if (i13 == 0) {
                        }
                        int i212 = i12;
                        i15 = i3 & 4096;
                        if (i15 == 0) {
                        }
                        i17 = i3 & 8192;
                        if (i17 != 0) {
                        }
                        if ((i2 & 24576) == 0) {
                        }
                        if ((i2 & 196608) == 0) {
                        }
                        if ((i2 & 1572864) == 0) {
                        }
                        i19 = i3 & 131072;
                        if (i19 != 0) {
                        }
                        if ((i3 & 262144) != 0) {
                        }
                        if ((i4 & 306783379) == 306783378) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    interactionSource2 = interactionSource;
                    i5 = i3 & 64;
                    if (i5 != 0) {
                    }
                    i6 = i3 & 128;
                    if (i6 != 0) {
                    }
                    i7 = i3 & 256;
                    if (i7 != 0) {
                    }
                    i8 = i3 & 512;
                    if (i8 != 0) {
                    }
                    i9 = i8;
                    i10 = i3 & 1024;
                    if (i10 == 0) {
                    }
                    i13 = i3 & 2048;
                    if (i13 == 0) {
                    }
                    int i2122 = i12;
                    i15 = i3 & 4096;
                    if (i15 == 0) {
                    }
                    i17 = i3 & 8192;
                    if (i17 != 0) {
                    }
                    if ((i2 & 24576) == 0) {
                    }
                    if ((i2 & 196608) == 0) {
                    }
                    if ((i2 & 1572864) == 0) {
                    }
                    i19 = i3 & 131072;
                    if (i19 != 0) {
                    }
                    if ((i3 & 262144) != 0) {
                    }
                    if ((i4 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                int i202 = 8192;
                if ((i3 & 16) == 0) {
                }
                if ((i3 & 32) == 0) {
                }
                interactionSource2 = interactionSource;
                i5 = i3 & 64;
                if (i5 != 0) {
                }
                i6 = i3 & 128;
                if (i6 != 0) {
                }
                i7 = i3 & 256;
                if (i7 != 0) {
                }
                i8 = i3 & 512;
                if (i8 != 0) {
                }
                i9 = i8;
                i10 = i3 & 1024;
                if (i10 == 0) {
                }
                i13 = i3 & 2048;
                if (i13 == 0) {
                }
                int i21222 = i12;
                i15 = i3 & 4096;
                if (i15 == 0) {
                }
                i17 = i3 & 8192;
                if (i17 != 0) {
                }
                if ((i2 & 24576) == 0) {
                }
                if ((i2 & 196608) == 0) {
                }
                if ((i2 & 1572864) == 0) {
                }
                i19 = i3 & 131072;
                if (i19 != 0) {
                }
                if ((i3 & 262144) != 0) {
                }
                if ((i4 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z4 = z;
            if ((i3 & 8) != 0) {
            }
            int i2022 = 8192;
            if ((i3 & 16) == 0) {
            }
            if ((i3 & 32) == 0) {
            }
            interactionSource2 = interactionSource;
            i5 = i3 & 64;
            if (i5 != 0) {
            }
            i6 = i3 & 128;
            if (i6 != 0) {
            }
            i7 = i3 & 256;
            if (i7 != 0) {
            }
            i8 = i3 & 512;
            if (i8 != 0) {
            }
            i9 = i8;
            i10 = i3 & 1024;
            if (i10 == 0) {
            }
            i13 = i3 & 2048;
            if (i13 == 0) {
            }
            int i212222 = i12;
            i15 = i3 & 4096;
            if (i15 == 0) {
            }
            i17 = i3 & 8192;
            if (i17 != 0) {
            }
            if ((i2 & 24576) == 0) {
            }
            if ((i2 & 196608) == 0) {
            }
            if ((i2 & 1572864) == 0) {
            }
            i19 = i3 & 131072;
            if (i19 != 0) {
            }
            if ((i3 & 262144) != 0) {
            }
            if ((i4 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function210 = function2;
        if ((i3 & 4) == 0) {
        }
        z4 = z;
        if ((i3 & 8) != 0) {
        }
        int i20222 = 8192;
        if ((i3 & 16) == 0) {
        }
        if ((i3 & 32) == 0) {
        }
        interactionSource2 = interactionSource;
        i5 = i3 & 64;
        if (i5 != 0) {
        }
        i6 = i3 & 128;
        if (i6 != 0) {
        }
        i7 = i3 & 256;
        if (i7 != 0) {
        }
        i8 = i3 & 512;
        if (i8 != 0) {
        }
        i9 = i8;
        i10 = i3 & 1024;
        if (i10 == 0) {
        }
        i13 = i3 & 2048;
        if (i13 == 0) {
        }
        int i2122222 = i12;
        i15 = i3 & 4096;
        if (i15 == 0) {
        }
        i17 = i3 & 8192;
        if (i17 != 0) {
        }
        if ((i2 & 24576) == 0) {
        }
        if ((i2 & 196608) == 0) {
        }
        if ((i2 & 1572864) == 0) {
        }
        i19 = i3 & 131072;
        if (i19 != 0) {
        }
        if ((i3 & 262144) != 0) {
        }
        if ((i4 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
