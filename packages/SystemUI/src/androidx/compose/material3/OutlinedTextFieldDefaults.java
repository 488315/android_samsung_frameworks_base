package androidx.compose.material3;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.material3.internal.TextFieldType;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.material3.tokens.OutlinedTextFieldTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;

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
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.colors (TextFieldDefaults.kt:1214)");
        }
        MaterialTheme.INSTANCE.getClass();
        ColorScheme colorScheme = MaterialTheme.getColorScheme(composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.<get-defaultOutlinedTextFieldColors> (TextFieldDefaults.kt:1364)");
        }
        TextFieldColors textFieldColorsM310copyejIjP34 = colorScheme.defaultOutlinedTextFieldColorsCached;
        if (textFieldColorsM310copyejIjP34 == null) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(523393076);
            composerImpl.end(false);
            textFieldColorsM310copyejIjP34 = null;
        } else {
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            composerImpl2.startReplaceGroup(523393077);
            TextSelectionColors textSelectionColors = (TextSelectionColors) composerImpl2.consume(TextSelectionColorsKt.LocalTextSelectionColors);
            if (!Intrinsics.areEqual(textFieldColorsM310copyejIjP34.textSelectionColors, textSelectionColors)) {
                textFieldColorsM310copyejIjP34 = textFieldColorsM310copyejIjP34.m310copyejIjP34(textFieldColorsM310copyejIjP34.focusedTextColor, textFieldColorsM310copyejIjP34.unfocusedTextColor, textFieldColorsM310copyejIjP34.disabledTextColor, textFieldColorsM310copyejIjP34.errorTextColor, textFieldColorsM310copyejIjP34.focusedContainerColor, textFieldColorsM310copyejIjP34.unfocusedContainerColor, textFieldColorsM310copyejIjP34.disabledContainerColor, textFieldColorsM310copyejIjP34.errorContainerColor, textFieldColorsM310copyejIjP34.cursorColor, textFieldColorsM310copyejIjP34.errorCursorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 1024) != 0 ? textFieldColorsM310copyejIjP34.textSelectionColors : textSelectionColors, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 2048) != 0 ? textFieldColorsM310copyejIjP34.focusedIndicatorColor : 0L, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & 4096) != 0 ? textFieldColorsM310copyejIjP34.unfocusedIndicatorColor : 0L, textFieldColorsM310copyejIjP34.disabledIndicatorColor, (KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? textFieldColorsM310copyejIjP34.errorIndicatorColor : 0L, textFieldColorsM310copyejIjP34.focusedLeadingIconColor, textFieldColorsM310copyejIjP34.unfocusedLeadingIconColor, textFieldColorsM310copyejIjP34.disabledLeadingIconColor, textFieldColorsM310copyejIjP34.errorLeadingIconColor, textFieldColorsM310copyejIjP34.focusedTrailingIconColor, textFieldColorsM310copyejIjP34.unfocusedTrailingIconColor, textFieldColorsM310copyejIjP34.disabledTrailingIconColor, textFieldColorsM310copyejIjP34.errorTrailingIconColor, textFieldColorsM310copyejIjP34.focusedLabelColor, textFieldColorsM310copyejIjP34.unfocusedLabelColor, textFieldColorsM310copyejIjP34.disabledLabelColor, textFieldColorsM310copyejIjP34.errorLabelColor, textFieldColorsM310copyejIjP34.focusedPlaceholderColor, textFieldColorsM310copyejIjP34.unfocusedPlaceholderColor, textFieldColorsM310copyejIjP34.disabledPlaceholderColor, textFieldColorsM310copyejIjP34.errorPlaceholderColor, textFieldColorsM310copyejIjP34.focusedSupportingTextColor, textFieldColorsM310copyejIjP34.unfocusedSupportingTextColor, textFieldColorsM310copyejIjP34.disabledSupportingTextColor, textFieldColorsM310copyejIjP34.errorSupportingTextColor, textFieldColorsM310copyejIjP34.focusedPrefixColor, textFieldColorsM310copyejIjP34.unfocusedPrefixColor, textFieldColorsM310copyejIjP34.disabledPrefixColor, textFieldColorsM310copyejIjP34.errorPrefixColor, textFieldColorsM310copyejIjP34.focusedSuffixColor, textFieldColorsM310copyejIjP34.unfocusedSuffixColor, textFieldColorsM310copyejIjP34.disabledSuffixColor, textFieldColorsM310copyejIjP34.errorSuffixColor);
                colorScheme.defaultOutlinedTextFieldColorsCached = textFieldColorsM310copyejIjP34;
            }
            composerImpl2.end(false);
        }
        if (textFieldColorsM310copyejIjP34 == null) {
            ComposerImpl composerImpl3 = (ComposerImpl) composer;
            composerImpl3.startReplaceGroup(1541103159);
            OutlinedTextFieldTokens.INSTANCE.getClass();
            long jFromToken = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusInputColor);
            long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.InputColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens = OutlinedTextFieldTokens.DisabledInputColor;
            long jFromToken3 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
            long jColor = ColorKt.Color(Color.m463getRedimpl(jFromToken3), Color.m462getGreenimpl(jFromToken3), Color.m460getBlueimpl(jFromToken3), 0.38f, Color.m461getColorSpaceimpl(jFromToken3));
            long jFromToken4 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorInputColor);
            Color.Companion.getClass();
            long j = Color.Transparent;
            long jFromToken5 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.CaretColor);
            long jFromToken6 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorFocusCaretColor);
            TextSelectionColors textSelectionColors2 = (TextSelectionColors) composerImpl3.consume(TextSelectionColorsKt.LocalTextSelectionColors);
            long jFromToken7 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusOutlineColor);
            long jFromToken8 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.OutlineColor);
            long jFromToken9 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledOutlineColor);
            long jColor2 = ColorKt.Color(Color.m463getRedimpl(jFromToken9), Color.m462getGreenimpl(jFromToken9), Color.m460getBlueimpl(jFromToken9), 0.12f, Color.m461getColorSpaceimpl(jFromToken9));
            long jFromToken10 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorOutlineColor);
            long jFromToken11 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusLeadingIconColor);
            long jFromToken12 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.LeadingIconColor);
            long jFromToken13 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledLeadingIconColor);
            long jColor3 = ColorKt.Color(Color.m463getRedimpl(jFromToken13), Color.m462getGreenimpl(jFromToken13), Color.m460getBlueimpl(jFromToken13), 0.38f, Color.m461getColorSpaceimpl(jFromToken13));
            long jFromToken14 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorLeadingIconColor);
            long jFromToken15 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusTrailingIconColor);
            long jFromToken16 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.TrailingIconColor);
            long jFromToken17 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledTrailingIconColor);
            long jColor4 = ColorKt.Color(Color.m463getRedimpl(jFromToken17), Color.m462getGreenimpl(jFromToken17), Color.m460getBlueimpl(jFromToken17), 0.38f, Color.m461getColorSpaceimpl(jFromToken17));
            long jFromToken18 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorTrailingIconColor);
            long jFromToken19 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusLabelColor);
            long jFromToken20 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.LabelColor);
            long jFromToken21 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledLabelColor);
            long jColor5 = ColorKt.Color(Color.m463getRedimpl(jFromToken21), Color.m462getGreenimpl(jFromToken21), Color.m460getBlueimpl(jFromToken21), 0.38f, Color.m461getColorSpaceimpl(jFromToken21));
            long jFromToken22 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorLabelColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens2 = OutlinedTextFieldTokens.InputPlaceholderColor;
            long jFromToken23 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            long jFromToken24 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            long jFromToken25 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
            long jColor6 = ColorKt.Color(Color.m463getRedimpl(jFromToken25), Color.m462getGreenimpl(jFromToken25), Color.m460getBlueimpl(jFromToken25), 0.38f, Color.m461getColorSpaceimpl(jFromToken25));
            long jFromToken26 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
            long jFromToken27 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.FocusSupportingColor);
            long jFromToken28 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.SupportingColor);
            long jFromToken29 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.DisabledSupportingColor);
            long jColor7 = ColorKt.Color(Color.m463getRedimpl(jFromToken29), Color.m462getGreenimpl(jFromToken29), Color.m460getBlueimpl(jFromToken29), 0.38f, Color.m461getColorSpaceimpl(jFromToken29));
            long jFromToken30 = ColorSchemeKt.fromToken(colorScheme, OutlinedTextFieldTokens.ErrorSupportingColor);
            ColorSchemeKeyTokens colorSchemeKeyTokens3 = OutlinedTextFieldTokens.InputPrefixColor;
            long jFromToken31 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            long jFromToken32 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            long jFromToken33 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            long jColor8 = ColorKt.Color(Color.m463getRedimpl(jFromToken33), Color.m462getGreenimpl(jFromToken33), Color.m460getBlueimpl(jFromToken33), 0.38f, Color.m461getColorSpaceimpl(jFromToken33));
            long jFromToken34 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
            ColorSchemeKeyTokens colorSchemeKeyTokens4 = OutlinedTextFieldTokens.InputSuffixColor;
            long jFromToken35 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
            long jFromToken36 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
            long jFromToken37 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
            TextFieldColors textFieldColors = new TextFieldColors(jFromToken, jFromToken2, jColor, jFromToken4, j, j, j, j, jFromToken5, jFromToken6, textSelectionColors2, jFromToken7, jFromToken8, jColor2, jFromToken10, jFromToken11, jFromToken12, jColor3, jFromToken14, jFromToken15, jFromToken16, jColor4, jFromToken18, jFromToken19, jFromToken20, jColor5, jFromToken22, jFromToken23, jFromToken24, jColor6, jFromToken26, jFromToken27, jFromToken28, jColor7, jFromToken30, jFromToken31, jFromToken32, jColor8, jFromToken34, jFromToken35, jFromToken36, ColorKt.Color(Color.m463getRedimpl(jFromToken37), Color.m462getGreenimpl(jFromToken37), Color.m460getBlueimpl(jFromToken37), 0.38f, Color.m461getColorSpaceimpl(jFromToken37)), ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4), null);
            colorScheme.defaultOutlinedTextFieldColorsCached = textFieldColors;
            composerImpl3.end(false);
            textFieldColorsM310copyejIjP34 = textFieldColors;
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
        return textFieldColorsM310copyejIjP34;
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:170:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00f9  */
    /* renamed from: Container-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m276Container4EFweAY(final boolean z, final boolean z2, final InteractionSource interactionSource, Modifier modifier, TextFieldColors textFieldColors, Shape shape, float f, float f2, Composer composer, final int i, final int i2) {
        int i3;
        final Modifier modifier2;
        TextFieldColors textFieldColors2;
        Shape shape2;
        float f3;
        float f4;
        float f5;
        float f6;
        TextFieldColors textFieldColors3;
        float f7;
        Shape shape3;
        MotionSchemeKeyTokens motionSchemeKeyTokens;
        boolean z3;
        ComposerImpl composerImpl;
        boolean z4;
        State stateRememberUpdatedState;
        ComposerImpl composerImpl2;
        State stateRememberUpdatedState2;
        ComposerImpl composerImpl3;
        final float f8;
        final float f9;
        final Shape shape4;
        final TextFieldColors textFieldColors4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i4;
        ComposerImpl composerImpl4 = (ComposerImpl) composer;
        composerImpl4.startRestartGroup(1035477640);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl4.changed(z) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((2 & i2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl4.changed(z2) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl4.changed(interactionSource) ? 256 : 128;
        }
        int i5 = i2 & 8;
        if (i5 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl4.changed(modifier2) ? 2048 : 1024;
            }
            if ((i & 24576) != 0) {
                if ((i2 & 16) == 0) {
                    textFieldColors2 = textFieldColors;
                    int i6 = composerImpl4.changed(textFieldColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
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
                    int i7 = composerImpl4.changed(shape2) ? 131072 : 65536;
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
                    if (composerImpl4.changed(f3)) {
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
            if ((i & 12582912) != 0) {
                if ((i2 & 128) == 0) {
                    f4 = f2;
                    int i8 = composerImpl4.changed(f4) ? 8388608 : 4194304;
                    i3 |= i8;
                } else {
                    f4 = f2;
                }
                i3 |= i8;
            } else {
                f4 = f2;
            }
            if ((256 & i2) != 0) {
                if ((i & 100663296) == 0) {
                    i3 |= composerImpl4.changed(this) ? 67108864 : 33554432;
                }
                if ((i3 & 38347923) == 38347922 && composerImpl4.getSkipping()) {
                    composerImpl4.skipToGroupEnd();
                    textFieldColors4 = textFieldColors2;
                    f9 = f3;
                    composerImpl3 = composerImpl4;
                    f8 = f4;
                    shape4 = shape2;
                } else {
                    composerImpl4.startDefaults();
                    if ((i & 1) != 0 || composerImpl4.getDefaultsInvalid()) {
                        if (i5 != 0) {
                            modifier2 = Modifier.Companion;
                        }
                        if ((i2 & 16) != 0) {
                            TextFieldColors textFieldColorsColors = colors((i3 >> 24) & 14, composerImpl4);
                            i3 &= -57345;
                            textFieldColors2 = textFieldColorsColors;
                        }
                        if ((i2 & 32) != 0) {
                            INSTANCE.getClass();
                            i3 &= -458753;
                            shape2 = getShape(composerImpl4);
                        }
                        if ((i2 & 64) == 0) {
                            f5 = FocusedBorderThickness;
                            i3 &= -3670017;
                        } else {
                            f5 = f3;
                        }
                        if ((128 & i2) == 0) {
                            i3 &= -29360129;
                            f6 = UnfocusedBorderThickness;
                            f7 = f5;
                            textFieldColors3 = textFieldColors2;
                        } else {
                            f6 = f4;
                            textFieldColors3 = textFieldColors2;
                            f7 = f5;
                        }
                        shape3 = shape2;
                    } else {
                        composerImpl4.skipToGroupEnd();
                        if ((i2 & 16) != 0) {
                            i3 &= -57345;
                        }
                        if ((i2 & 32) != 0) {
                            i3 &= -458753;
                        }
                        if ((i2 & 64) != 0) {
                            i3 &= -3670017;
                        }
                        if ((128 & i2) != 0) {
                            i3 &= -29360129;
                        }
                        f6 = f4;
                        textFieldColors3 = textFieldColors2;
                        shape3 = shape2;
                        f7 = f3;
                    }
                    composerImpl4.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.Container (TextFieldDefaults.kt:1056)");
                    }
                    boolean zBooleanValue = ((Boolean) FocusInteractionKt.collectIsFocusedAsState(interactionSource, composerImpl4, (i3 >> 6) & 14).getValue()).booleanValue();
                    float f10 = TextFieldImplKt.TextFieldPadding;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.internal.animateBorderStrokeAsState (TextFieldImpl.kt:475)");
                    }
                    long jM311indicatorColorXeAY9LY$material3_release = textFieldColors3.m311indicatorColorXeAY9LY$material3_release(z, z2, zBooleanValue);
                    MotionSchemeKeyTokens motionSchemeKeyTokens2 = MotionSchemeKeyTokens.FastEffects;
                    SpringSpec springSpecValue = MotionSchemeKt.value(motionSchemeKeyTokens2, composerImpl4);
                    if (z) {
                        motionSchemeKeyTokens = motionSchemeKeyTokens2;
                        z3 = false;
                        composerImpl = composerImpl4;
                        z4 = zBooleanValue;
                        composerImpl.startReplaceGroup(1024978881);
                        stateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Color.m456boximpl(jM311indicatorColorXeAY9LY$material3_release), composerImpl);
                        composerImpl.end(false);
                    } else {
                        composerImpl4.startReplaceGroup(1024898126);
                        z4 = zBooleanValue;
                        motionSchemeKeyTokens = motionSchemeKeyTokens2;
                        z3 = false;
                        stateRememberUpdatedState = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(jM311indicatorColorXeAY9LY$material3_release, springSpecValue, null, composerImpl4, 0, 12);
                        composerImpl = composerImpl4;
                        composerImpl.end(false);
                    }
                    SpringSpec springSpecValue2 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl);
                    if (z) {
                        composerImpl2 = composerImpl;
                        composerImpl2.startReplaceGroup(1025342356);
                        stateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(Dp.m837boximpl(f6), composerImpl2);
                        composerImpl2.end(z3);
                    } else {
                        composerImpl.startReplaceGroup(1025160293);
                        ComposerImpl composerImpl5 = composerImpl;
                        stateRememberUpdatedState2 = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(z4 ? f7 : f6, springSpecValue2, null, composerImpl5, 0, 12);
                        composerImpl2 = composerImpl5;
                        composerImpl2.end(z3);
                    }
                    Modifier modifier3 = modifier2;
                    MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(BorderStrokeKt.m31BorderStrokecXLIe8U(((Dp) stateRememberUpdatedState2.getValue()).value, ((Color) stateRememberUpdatedState.getValue()).value), composerImpl2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    ComposerImpl composerImpl6 = composerImpl2;
                    final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColors3.m309containerColorXeAY9LY$material3_release(z, z2, z4), MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl2), null, composerImpl6, 0, 12);
                    composerImpl3 = composerImpl6;
                    BorderStroke borderStroke = (BorderStroke) mutableStateRememberUpdatedState.getValue();
                    BoxKt.Box(TextFieldImplKt.textFieldBackground(BorderKt.m29borderziNgDLE(modifier3, borderStroke.width, borderStroke.brush, shape3), new TextFieldDefaults$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac) { // from class: androidx.compose.material3.OutlinedTextFieldDefaults$Container$1
                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                        public final Object get() {
                            return ((State) this.receiver).getValue();
                        }
                    }), shape3), composerImpl3, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    modifier2 = modifier3;
                    f8 = f6;
                    f9 = f7;
                    shape4 = shape3;
                    textFieldColors4 = textFieldColors3;
                }
                recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldDefaults$Container$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            this.$tmp0_rcvr.m276Container4EFweAY(z, z2, interactionSource, modifier2, textFieldColors4, shape4, f9, f8, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 100663296;
            if ((i3 & 38347923) == 38347922) {
                composerImpl4.startDefaults();
                if ((i & 1) != 0) {
                    if (i5 != 0) {
                    }
                    if ((i2 & 16) != 0) {
                    }
                    if ((i2 & 32) != 0) {
                    }
                    if ((i2 & 64) == 0) {
                    }
                    if ((128 & i2) == 0) {
                    }
                    shape3 = shape2;
                    composerImpl4.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    boolean zBooleanValue2 = ((Boolean) FocusInteractionKt.collectIsFocusedAsState(interactionSource, composerImpl4, (i3 >> 6) & 14).getValue()).booleanValue();
                    float f102 = TextFieldImplKt.TextFieldPadding;
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    long jM311indicatorColorXeAY9LY$material3_release2 = textFieldColors3.m311indicatorColorXeAY9LY$material3_release(z, z2, zBooleanValue2);
                    MotionSchemeKeyTokens motionSchemeKeyTokens22 = MotionSchemeKeyTokens.FastEffects;
                    SpringSpec springSpecValue3 = MotionSchemeKt.value(motionSchemeKeyTokens22, composerImpl4);
                    if (z) {
                    }
                    SpringSpec springSpecValue22 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl);
                    if (z) {
                    }
                    Modifier modifier32 = modifier2;
                    MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(BorderStrokeKt.m31BorderStrokecXLIe8U(((Dp) stateRememberUpdatedState2.getValue()).value, ((Color) stateRememberUpdatedState.getValue()).value), composerImpl2);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    ComposerImpl composerImpl62 = composerImpl2;
                    final Object stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(textFieldColors3.m309containerColorXeAY9LY$material3_release(z, z2, z4), MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl2), null, composerImpl62, 0, 12);
                    composerImpl3 = composerImpl62;
                    BorderStroke borderStroke2 = (BorderStroke) mutableStateRememberUpdatedState2.getValue();
                    BoxKt.Box(TextFieldImplKt.textFieldBackground(BorderKt.m29borderziNgDLE(modifier32, borderStroke2.width, borderStroke2.brush, shape3), new TextFieldDefaults$sam$androidx_compose_ui_graphics_ColorProducer$0(new PropertyReference0Impl(stateM7animateColorAsStateeuL9pac2) { // from class: androidx.compose.material3.OutlinedTextFieldDefaults$Container$1
                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                        public final Object get() {
                            return ((State) this.receiver).getValue();
                        }
                    }), shape3), composerImpl3, 0);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    modifier2 = modifier32;
                    f8 = f6;
                    f9 = f7;
                    shape4 = shape3;
                    textFieldColors4 = textFieldColors3;
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
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
        if ((i & 12582912) != 0) {
        }
        if ((256 & i2) != 0) {
        }
        if ((i3 & 38347923) == 38347922) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:273:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void DecorationBox(final String str, final Function2 function2, final boolean z, final boolean z2, final VisualTransformation visualTransformation, final InteractionSource interactionSource, boolean z3, Function2 function22, Function2 function23, Function2 function24, Function2 function25, Function2 function26, Function2 function27, Function2 function28, TextFieldColors textFieldColors, PaddingValues paddingValues, Function2 function29, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        boolean z4;
        int i5;
        final boolean z5;
        int i6;
        Function2 function210;
        int i7;
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
        Function2 function211;
        final TextFieldColors textFieldColorsColors;
        PaddingValues paddingValuesM123PaddingValuesa9UjIt4;
        PaddingValues paddingValues2;
        final Function2 function212;
        Function2 function213;
        Function2 function214;
        Function2 function215;
        final TextFieldColors textFieldColors2;
        Function2 function216;
        int i20;
        Function2 function217;
        Function2 function218;
        boolean z6;
        Function2 function219;
        final Function2 function220;
        final boolean z7;
        final Function2 function221;
        final Function2 function222;
        final Function2 function223;
        ComposerImpl composerImpl;
        final Function2 function224;
        final Function2 function225;
        final PaddingValues paddingValues3;
        final Function2 function226;
        final Function2 function227;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-350442135);
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
                i4 |= composerImpl2.changedInstance(function2) ? 32 : 16;
            }
            if ((i3 & 4) == 0) {
                i4 |= 384;
            } else if ((i & 384) == 0) {
                i4 |= composerImpl2.changed(z) ? 256 : 128;
            }
            if ((i3 & 8) == 0) {
                i4 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    z4 = z2;
                    i4 |= composerImpl2.changed(z4) ? 2048 : 1024;
                }
                int i21 = 8192;
                if ((i3 & 16) != 0) {
                    i4 |= 24576;
                } else if ((i & 24576) == 0) {
                    i4 |= composerImpl2.changed(visualTransformation) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i3 & 32) != 0) {
                    i4 |= 196608;
                } else if ((i & 196608) == 0) {
                    i4 |= composerImpl2.changed(interactionSource) ? 131072 : 65536;
                }
                i5 = i3 & 64;
                int i22 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i5 != 0) {
                    i4 |= 1572864;
                    z5 = z3;
                } else {
                    z5 = z3;
                    if ((i & 1572864) == 0) {
                        i4 |= composerImpl2.changed(z5) ? 1048576 : 524288;
                    }
                }
                i6 = i3 & 128;
                if (i6 != 0) {
                    i4 |= 12582912;
                    function210 = function22;
                } else {
                    function210 = function22;
                    if ((i & 12582912) == 0) {
                        i4 |= composerImpl2.changedInstance(function210) ? 8388608 : 4194304;
                    }
                }
                i7 = i3 & 256;
                if (i7 != 0) {
                    i4 |= 100663296;
                } else if ((i & 100663296) == 0) {
                    i4 |= composerImpl2.changedInstance(function23) ? 67108864 : 33554432;
                }
                i8 = i3 & 512;
                if (i8 == 0) {
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
                    int i23 = i12;
                    i15 = i3 & 4096;
                    if (i15 == 0) {
                        i16 = i23 | 384;
                    } else if ((i2 & 384) == 0) {
                        i16 = i23 | (composerImpl2.changedInstance(function27) ? 256 : 128);
                    } else {
                        i16 = i23;
                    }
                    i17 = i3 & 8192;
                    if (i17 == 0) {
                        i18 = i16 | 3072;
                    } else {
                        int i24 = i16;
                        if ((i2 & 3072) == 0) {
                            i18 = i24 | (composerImpl2.changedInstance(function28) ? 2048 : 1024);
                        } else {
                            i18 = i24;
                        }
                    }
                    if ((i2 & 24576) == 0) {
                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0 && composerImpl2.changed(textFieldColors)) {
                            i21 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        }
                        i18 |= i21;
                    }
                    if ((i2 & 196608) == 0) {
                        i18 |= ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0 && composerImpl2.changed(paddingValues)) ? 131072 : 65536;
                    }
                    i19 = i3 & 65536;
                    if (i19 == 0) {
                        i18 |= 1572864;
                    } else if ((i2 & 1572864) == 0) {
                        if (composerImpl2.changedInstance(function29)) {
                            i22 = 1048576;
                        }
                        i18 |= i22;
                    }
                    if ((i3 & 131072) == 0) {
                        i18 |= 12582912;
                    } else if ((i2 & 12582912) == 0) {
                        i18 |= composerImpl2.changed(this) ? 8388608 : 4194304;
                    }
                    if ((i4 & 306783379) != 306783378 && (i18 & 4793491) == 4793490 && composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        function227 = function23;
                        function222 = function25;
                        function220 = function26;
                        function223 = function27;
                        function226 = function28;
                        textFieldColors2 = textFieldColors;
                        paddingValues3 = paddingValues;
                        function225 = function29;
                        composerImpl = composerImpl2;
                        function224 = function210;
                        z7 = z5;
                        function221 = function24;
                    } else {
                        composerImpl2.startDefaults();
                        ComposableLambdaImpl composableLambdaImplRememberComposableLambda = null;
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i5 != 0) {
                                z5 = false;
                            }
                            if (i6 != 0) {
                                function210 = null;
                            }
                            Function2 function228 = i7 == 0 ? null : function23;
                            Function2 function229 = i9 == 0 ? null : function24;
                            Function2 function230 = i11 == 0 ? null : function25;
                            function211 = i14 == 0 ? null : function26;
                            Function2 function231 = i15 == 0 ? null : function27;
                            Function2 function232 = i17 == 0 ? null : function28;
                            if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                textFieldColorsColors = colors((i18 >> 21) & 14, composerImpl2);
                                i18 &= -57345;
                            } else {
                                textFieldColorsColors = textFieldColors;
                            }
                            Function2 function233 = function228;
                            if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
                                float f = TextFieldImplKt.TextFieldPadding;
                                paddingValuesM123PaddingValuesa9UjIt4 = PaddingKt.m123PaddingValuesa9UjIt4(f, f, f, f);
                                i18 &= -458753;
                            } else {
                                paddingValuesM123PaddingValuesa9UjIt4 = paddingValues;
                            }
                            PaddingValues paddingValues4 = paddingValuesM123PaddingValuesa9UjIt4;
                            if (i19 == 0) {
                                TextFieldColors textFieldColors3 = textFieldColorsColors;
                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-1448570018, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox.1
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
                                                    ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox.<anonymous> (TextFieldDefaults.kt:1156)");
                                                }
                                                OutlinedTextFieldDefaults outlinedTextFieldDefaults = OutlinedTextFieldDefaults.INSTANCE;
                                                boolean z8 = z;
                                                boolean z9 = z5;
                                                InteractionSource interactionSource2 = interactionSource;
                                                Modifier.Companion companion = Modifier.Companion;
                                                TextFieldColors textFieldColors4 = textFieldColorsColors;
                                                outlinedTextFieldDefaults.getClass();
                                                outlinedTextFieldDefaults.m276Container4EFweAY(z8, z9, interactionSource2, companion, textFieldColors4, OutlinedTextFieldDefaults.getShape(composer2), OutlinedTextFieldDefaults.FocusedBorderThickness, OutlinedTextFieldDefaults.UnfocusedBorderThickness, composer2, 114822144, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl2);
                                paddingValues2 = paddingValues4;
                                function215 = function231;
                                textFieldColors2 = textFieldColors3;
                                i20 = i18;
                                function218 = composableLambdaImplRememberComposableLambda2;
                                function212 = function210;
                                function213 = function229;
                                function214 = function230;
                                function216 = function232;
                                function217 = function233;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox (TextFieldDefaults.kt:1167)");
                                }
                                PaddingValues paddingValues5 = paddingValues2;
                                z6 = ((i4 & 14) != 4) | ((57344 & i4) == 16384);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                if (z6) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = visualTransformation.filter(new AnnotatedString(str, null, null, 6, null));
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    String str2 = ((TransformedText) objRememberedValue).text.text;
                                    TextFieldType textFieldType = TextFieldType.Outlined;
                                    TextFieldLabelPosition.Attached attached = new TextFieldLabelPosition.Attached(false, null, null, 7, null);
                                    if (function212 == null) {
                                        composerImpl2.startReplaceGroup(-1341044029);
                                        composerImpl2.end(false);
                                        function219 = function212;
                                    } else {
                                        composerImpl2.startReplaceGroup(-1341044028);
                                        function219 = function212;
                                        composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(790581763, new Function3() { // from class: androidx.compose.material3.OutlinedTextFieldDefaults$DecorationBox$2$1
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
                                                            ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox.<anonymous>.<anonymous> (TextFieldDefaults.kt:1181)");
                                                        }
                                                        function212.invoke(composer2, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl2);
                                        composerImpl2.end(false);
                                    }
                                    int i25 = i4 >> 9;
                                    int i26 = i20 << 21;
                                    int i27 = ((i4 << 3) & 896) | 6 | (i25 & 458752) | (i25 & 3670016) | (i26 & 29360128) | (i26 & 234881024) | (i26 & 1879048192);
                                    int i28 = ((i20 >> 9) & 14) | ((i4 >> 6) & 112) | (i4 & 896) | (i25 & 7168) | ((i4 >> 3) & 57344) | (i20 & 458752) | ((i20 << 6) & 3670016) | ((i20 << 3) & 29360128);
                                    boolean z8 = z4;
                                    boolean z9 = z5;
                                    Function2 function234 = function219;
                                    Function2 function235 = function211;
                                    TextFieldImplKt.CommonDecorationBox(textFieldType, str2, function2, attached, composableLambdaImplRememberComposableLambda, function217, function213, function214, function235, function215, function216, z8, z, z9, interactionSource, paddingValues5, textFieldColors2, function218, composerImpl2, i27, i28);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    function220 = function235;
                                    z7 = z9;
                                    function221 = function213;
                                    function222 = function214;
                                    function223 = function215;
                                    composerImpl = composerImpl2;
                                    function224 = function234;
                                    function225 = function218;
                                    paddingValues3 = paddingValues5;
                                    function226 = function216;
                                    function227 = function217;
                                }
                            } else {
                                TextFieldColors textFieldColors4 = textFieldColorsColors;
                                paddingValues2 = paddingValues4;
                                function212 = function210;
                                function213 = function229;
                                function214 = function230;
                                function215 = function231;
                                textFieldColors2 = textFieldColors4;
                                function216 = function232;
                                i20 = i18;
                                function217 = function233;
                            }
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                i18 &= -57345;
                            }
                            if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                                i18 &= -458753;
                            }
                            function217 = function23;
                            function214 = function25;
                            function211 = function26;
                            function215 = function27;
                            function216 = function28;
                            textFieldColors2 = textFieldColors;
                            paddingValues2 = paddingValues;
                            function212 = function210;
                            i20 = i18;
                            function213 = function24;
                        }
                        function218 = function29;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        PaddingValues paddingValues52 = paddingValues2;
                        z6 = ((i4 & 14) != 4) | ((57344 & i4) == 16384);
                        Object objRememberedValue2 = composerImpl2.rememberedValue();
                        if (z6) {
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                OutlinedTextFieldDefaults.this.DecorationBox(str, function2, z, z2, visualTransformation, interactionSource, z7, function224, function227, function221, function222, function220, function223, function226, textFieldColors2, paddingValues3, function225, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i4 |= 805306368;
                i9 = i8;
                i10 = i3 & 1024;
                if (i10 == 0) {
                }
                i13 = i3 & 2048;
                if (i13 == 0) {
                }
                int i232 = i12;
                i15 = i3 & 4096;
                if (i15 == 0) {
                }
                i17 = i3 & 8192;
                if (i17 == 0) {
                }
                if ((i2 & 24576) == 0) {
                }
                if ((i2 & 196608) == 0) {
                }
                i19 = i3 & 65536;
                if (i19 == 0) {
                }
                if ((i3 & 131072) == 0) {
                }
                if ((i4 & 306783379) != 306783378) {
                    composerImpl2.startDefaults();
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = null;
                    if ((i & 1) != 0) {
                        if (i5 != 0) {
                        }
                        if (i6 != 0) {
                        }
                        if (i7 == 0) {
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
                        Function2 function2332 = function228;
                        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
                        }
                        PaddingValues paddingValues42 = paddingValuesM123PaddingValuesa9UjIt4;
                        if (i19 == 0) {
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z4 = z2;
            int i212 = 8192;
            if ((i3 & 16) != 0) {
            }
            if ((i3 & 32) != 0) {
            }
            i5 = i3 & 64;
            int i222 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i5 != 0) {
            }
            i6 = i3 & 128;
            if (i6 != 0) {
            }
            i7 = i3 & 256;
            if (i7 != 0) {
            }
            i8 = i3 & 512;
            if (i8 == 0) {
            }
            i9 = i8;
            i10 = i3 & 1024;
            if (i10 == 0) {
            }
            i13 = i3 & 2048;
            if (i13 == 0) {
            }
            int i2322 = i12;
            i15 = i3 & 4096;
            if (i15 == 0) {
            }
            i17 = i3 & 8192;
            if (i17 == 0) {
            }
            if ((i2 & 24576) == 0) {
            }
            if ((i2 & 196608) == 0) {
            }
            i19 = i3 & 65536;
            if (i19 == 0) {
            }
            if ((i3 & 131072) == 0) {
            }
            if ((i4 & 306783379) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        if ((i3 & 4) == 0) {
        }
        if ((i3 & 8) == 0) {
        }
        z4 = z2;
        int i2122 = 8192;
        if ((i3 & 16) != 0) {
        }
        if ((i3 & 32) != 0) {
        }
        i5 = i3 & 64;
        int i2222 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i5 != 0) {
        }
        i6 = i3 & 128;
        if (i6 != 0) {
        }
        i7 = i3 & 256;
        if (i7 != 0) {
        }
        i8 = i3 & 512;
        if (i8 == 0) {
        }
        i9 = i8;
        i10 = i3 & 1024;
        if (i10 == 0) {
        }
        i13 = i3 & 2048;
        if (i13 == 0) {
        }
        int i23222 = i12;
        i15 = i3 & 4096;
        if (i15 == 0) {
        }
        i17 = i3 & 8192;
        if (i17 == 0) {
        }
        if ((i2 & 24576) == 0) {
        }
        if ((i2 & 196608) == 0) {
        }
        i19 = i3 & 65536;
        if (i19 == 0) {
        }
        if ((i3 & 131072) == 0) {
        }
        if ((i4 & 306783379) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
