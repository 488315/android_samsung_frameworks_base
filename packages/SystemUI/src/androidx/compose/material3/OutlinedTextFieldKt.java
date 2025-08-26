package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.BasicTextFieldKt;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
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
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public abstract class OutlinedTextFieldKt {
    public static final float OutlinedTextFieldInnerPadding;

    static {
        Dp.Companion companion = Dp.Companion;
        OutlinedTextFieldInnerPadding = 4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x03f3  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:352:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x012e  */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v42 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void OutlinedTextField(final String str, final Function1 function1, Modifier modifier, boolean z, boolean z2, TextStyle textStyle, Function2 function2, Function2 function22, Function2 function23, Function2 function24, Function2 function25, Function2 function26, Function2 function27, boolean z3, VisualTransformation visualTransformation, KeyboardOptions keyboardOptions, KeyboardActions keyboardActions, boolean z4, int i, int i2, MutableInteractionSource mutableInteractionSource, Shape shape, TextFieldColors textFieldColors, Composer composer, final int i3, final int i4, final int i5, final int i6) {
        final String str2;
        int i7;
        final Function1 function12;
        Modifier modifier2;
        int i8;
        boolean z5;
        int i9;
        boolean z6;
        TextStyle textStyle2;
        int i10;
        final Function2 function28;
        int i11;
        Function2 function29;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        int i32;
        int i33;
        VisualTransformation visualTransformation2;
        KeyboardOptions keyboardOptions2;
        KeyboardActions keyboardActions2;
        MutableInteractionSource mutableInteractionSource2;
        Shape shape2;
        final Function2 function210;
        final Function2 function211;
        final Function2 function212;
        final Function2 function213;
        final boolean z7;
        final Function2 function214;
        final Function2 function215;
        final KeyboardOptions keyboardOptions3;
        final KeyboardActions keyboardActions3;
        final boolean z8;
        final int i34;
        final Shape shape3;
        final TextFieldColors textFieldColorsColors;
        final int i35;
        final VisualTransformation visualTransformation3;
        final boolean z9;
        TextStyle textStyle3;
        final Modifier modifier3;
        ?? r5;
        MutableInteractionSource mutableInteractionSource3;
        long jM758getColor0d7_KjU;
        final TextStyle textStyle4;
        ComposerImpl composerImpl;
        final Function2 function216;
        final boolean z10;
        final boolean z11;
        final boolean z12;
        final KeyboardOptions keyboardOptions4;
        final KeyboardActions keyboardActions4;
        final boolean z13;
        final int i36;
        final int i37;
        final VisualTransformation visualTransformation4;
        final Function2 function217;
        final Function2 function218;
        final Function2 function219;
        final Function2 function220;
        final Shape shape4;
        final MutableInteractionSource mutableInteractionSource4;
        final TextFieldColors textFieldColors2;
        final Function2 function221;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1922450045);
        if ((i6 & 1) != 0) {
            i7 = i3 | 6;
            str2 = str;
        } else {
            str2 = str;
            if ((i3 & 6) == 0) {
                i7 = (composerImpl2.changed(str2) ? 4 : 2) | i3;
            } else {
                i7 = i3;
            }
        }
        if ((i6 & 2) != 0) {
            i7 |= 48;
            function12 = function1;
        } else {
            function12 = function1;
            if ((i3 & 48) == 0) {
                i7 |= composerImpl2.changedInstance(function12) ? 32 : 16;
            }
        }
        int i38 = i6 & 4;
        if (i38 != 0) {
            i7 |= 384;
        } else {
            if ((i3 & 384) == 0) {
                modifier2 = modifier;
                i7 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            i8 = i6 & 8;
            if (i8 == 0) {
                i7 |= 3072;
            } else {
                if ((i3 & 3072) == 0) {
                    z5 = z;
                    i7 |= composerImpl2.changed(z5) ? 2048 : 1024;
                }
                i9 = i6 & 16;
                int i39 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i9 != 0) {
                    i7 |= 24576;
                } else {
                    if ((i3 & 24576) == 0) {
                        z6 = z2;
                        i7 |= composerImpl2.changed(z6) ? 16384 : 8192;
                    }
                    if ((i3 & 196608) != 0) {
                        textStyle2 = textStyle;
                        i7 |= ((i6 & 32) == 0 && composerImpl2.changed(textStyle2)) ? 131072 : 65536;
                    } else {
                        textStyle2 = textStyle;
                    }
                    i10 = i6 & 64;
                    if (i10 == 0) {
                        i7 |= 1572864;
                        function28 = function2;
                    } else {
                        function28 = function2;
                        if ((i3 & 1572864) == 0) {
                            i7 |= composerImpl2.changedInstance(function28) ? 1048576 : 524288;
                        }
                    }
                    i11 = i6 & 128;
                    if (i11 == 0) {
                        i7 |= 12582912;
                        function29 = function22;
                    } else {
                        function29 = function22;
                        if ((i3 & 12582912) == 0) {
                            i7 |= composerImpl2.changedInstance(function29) ? 8388608 : 4194304;
                        }
                    }
                    i12 = i6 & 256;
                    if (i12 == 0) {
                        i7 |= 100663296;
                    } else {
                        if ((i3 & 100663296) == 0) {
                            i13 = i12;
                            i7 |= composerImpl2.changedInstance(function23) ? 67108864 : 33554432;
                        }
                        i14 = i6 & 512;
                        if (i14 != 0) {
                            i7 |= 805306368;
                        } else {
                            if ((i3 & 805306368) == 0) {
                                i15 = i14;
                                i7 |= composerImpl2.changedInstance(function24) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            i16 = i6 & 1024;
                            if (i16 == 0) {
                                i18 = i4 | 6;
                                i17 = i16;
                            } else if ((i4 & 6) == 0) {
                                i17 = i16;
                                i18 = i4 | (composerImpl2.changedInstance(function25) ? 4 : 2);
                            } else {
                                i17 = i16;
                                i18 = i4;
                            }
                            i19 = i6 & 2048;
                            if (i19 == 0) {
                                i18 |= 48;
                                i20 = i19;
                            } else if ((i4 & 48) == 0) {
                                i20 = i19;
                                i18 |= composerImpl2.changedInstance(function26) ? 32 : 16;
                            } else {
                                i20 = i19;
                            }
                            int i40 = i18;
                            i21 = i7;
                            i22 = i6 & 4096;
                            if (i22 == 0) {
                                i23 = i40 | 384;
                            } else {
                                int i41 = i40;
                                if ((i4 & 384) == 0) {
                                    i41 |= composerImpl2.changedInstance(function27) ? 256 : 128;
                                }
                                i23 = i41;
                            }
                            i24 = i6 & 8192;
                            if (i24 == 0) {
                                i25 = i23 | 3072;
                            } else {
                                int i42 = i23;
                                if ((i4 & 3072) == 0) {
                                    i25 = i42 | (composerImpl2.changed(z3) ? 2048 : 1024);
                                } else {
                                    i25 = i42;
                                }
                            }
                            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                            if (i26 != 0) {
                                i27 = i25;
                                if ((i4 & 24576) == 0) {
                                    if (!composerImpl2.changed(visualTransformation)) {
                                        i39 = 8192;
                                    }
                                    i27 |= i39;
                                }
                                i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                                if (i28 != 0) {
                                    i27 |= 196608;
                                } else if ((i4 & 196608) == 0) {
                                    i27 |= composerImpl2.changed(keyboardOptions) ? 131072 : 65536;
                                }
                                i29 = i6 & 65536;
                                if (i29 != 0) {
                                    i27 |= 1572864;
                                } else if ((i4 & 1572864) == 0) {
                                    i27 |= composerImpl2.changed(keyboardActions) ? 1048576 : 524288;
                                }
                                i30 = i6 & 131072;
                                if (i30 != 0) {
                                    i27 |= 12582912;
                                } else if ((i4 & 12582912) == 0) {
                                    i27 |= composerImpl2.changed(z4) ? 8388608 : 4194304;
                                }
                                if ((i4 & 100663296) == 0) {
                                    i27 |= ((i6 & 262144) == 0 && composerImpl2.changed(i)) ? 67108864 : 33554432;
                                }
                                i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                                if (i31 != 0) {
                                    i27 |= 805306368;
                                } else if ((i4 & 805306368) == 0) {
                                    i27 |= composerImpl2.changed(i2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                }
                                i32 = i6 & 1048576;
                                if (i32 != 0) {
                                    i33 = i5 | 6;
                                } else if ((i5 & 6) == 0) {
                                    i33 = i5 | (composerImpl2.changed(mutableInteractionSource) ? 4 : 2);
                                } else {
                                    i33 = i5;
                                }
                                if ((i5 & 48) == 0) {
                                    i33 |= ((i6 & 2097152) == 0 && composerImpl2.changed(shape)) ? 32 : 16;
                                }
                                if ((i5 & 384) == 0) {
                                    i33 |= ((i6 & 4194304) == 0 && composerImpl2.changed(textFieldColors)) ? 256 : 128;
                                }
                                int i43 = i33;
                                if ((i21 & 306783379) == 306783378 && (i27 & 306783379) == 306783378 && (i43 & 147) == 146 && composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                    function210 = function23;
                                    function219 = function25;
                                    function220 = function27;
                                    z10 = z3;
                                    keyboardOptions4 = keyboardOptions;
                                    keyboardActions4 = keyboardActions;
                                    z13 = z4;
                                    i36 = i;
                                    i37 = i2;
                                    mutableInteractionSource4 = mutableInteractionSource;
                                    shape4 = shape;
                                    textFieldColors2 = textFieldColors;
                                    composerImpl = composerImpl2;
                                    z11 = z5;
                                    z12 = z6;
                                    textStyle4 = textStyle2;
                                    function216 = function28;
                                    function217 = function29;
                                    modifier3 = modifier2;
                                    function218 = function24;
                                    function221 = function26;
                                    visualTransformation4 = visualTransformation;
                                } else {
                                    composerImpl2.startDefaults();
                                    if ((i3 & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                        if (i38 != 0) {
                                            modifier2 = Modifier.Companion;
                                        }
                                        if (i8 != 0) {
                                            z5 = true;
                                        }
                                        if (i9 != 0) {
                                            z6 = false;
                                        }
                                        if ((i6 & 32) != 0) {
                                            textStyle2 = (TextStyle) composerImpl2.consume(TextKt.LocalTextStyle);
                                        }
                                        if (i10 != 0) {
                                            function28 = null;
                                        }
                                        if (i11 != 0) {
                                            function29 = null;
                                        }
                                        Function2 function222 = i13 == 0 ? null : function23;
                                        Function2 function223 = i15 == 0 ? null : function24;
                                        Function2 function224 = i17 == 0 ? null : function25;
                                        Function2 function225 = i20 == 0 ? null : function26;
                                        Function2 function226 = i22 == 0 ? null : function27;
                                        boolean z14 = i24 == 0 ? false : z3;
                                        if (i26 == 0) {
                                            VisualTransformation.Companion.getClass();
                                            visualTransformation2 = VisualTransformation.Companion.None;
                                        } else {
                                            visualTransformation2 = visualTransformation;
                                        }
                                        if (i28 == 0) {
                                            KeyboardOptions.Companion.getClass();
                                            keyboardOptions2 = KeyboardOptions.Default;
                                        } else {
                                            keyboardOptions2 = keyboardOptions;
                                        }
                                        if (i29 == 0) {
                                            KeyboardActions.Companion.getClass();
                                            keyboardActions2 = KeyboardActions.Default;
                                        } else {
                                            keyboardActions2 = keyboardActions;
                                        }
                                        boolean z15 = i30 == 0 ? false : z4;
                                        int i44 = (i6 & 262144) == 0 ? z15 ? 1 : Integer.MAX_VALUE : i;
                                        int i45 = i31 == 0 ? i2 : 1;
                                        mutableInteractionSource2 = i32 == 0 ? mutableInteractionSource : null;
                                        if ((i6 & 2097152) == 0) {
                                            OutlinedTextFieldDefaults.INSTANCE.getClass();
                                            shape2 = OutlinedTextFieldDefaults.getShape(composerImpl2);
                                        } else {
                                            shape2 = shape;
                                        }
                                        if ((i6 & 4194304) == 0) {
                                            OutlinedTextFieldDefaults.INSTANCE.getClass();
                                            function210 = function222;
                                            function211 = function223;
                                            function212 = function29;
                                            function213 = function224;
                                            z7 = z14;
                                            function214 = function225;
                                            function215 = function226;
                                            keyboardOptions3 = keyboardOptions2;
                                            keyboardActions3 = keyboardActions2;
                                            z8 = z15;
                                            i34 = i44;
                                            shape3 = shape2;
                                            i35 = i45;
                                            textFieldColorsColors = OutlinedTextFieldDefaults.colors(6, composerImpl2);
                                        } else {
                                            function210 = function222;
                                            function211 = function223;
                                            function212 = function29;
                                            function213 = function224;
                                            z7 = z14;
                                            function214 = function225;
                                            function215 = function226;
                                            keyboardOptions3 = keyboardOptions2;
                                            keyboardActions3 = keyboardActions2;
                                            z8 = z15;
                                            i34 = i44;
                                            shape3 = shape2;
                                            textFieldColorsColors = textFieldColors;
                                            i35 = i45;
                                        }
                                        visualTransformation3 = visualTransformation2;
                                        z9 = z6;
                                        textStyle3 = textStyle2;
                                        modifier3 = modifier2;
                                    } else {
                                        composerImpl2.skipToGroupEnd();
                                        function210 = function23;
                                        function211 = function24;
                                        function213 = function25;
                                        function214 = function26;
                                        function215 = function27;
                                        z7 = z3;
                                        visualTransformation3 = visualTransformation;
                                        keyboardOptions3 = keyboardOptions;
                                        keyboardActions3 = keyboardActions;
                                        z8 = z4;
                                        i34 = i;
                                        i35 = i2;
                                        mutableInteractionSource2 = mutableInteractionSource;
                                        shape3 = shape;
                                        z9 = z6;
                                        textStyle3 = textStyle2;
                                        function212 = function29;
                                        modifier3 = modifier2;
                                        textFieldColorsColors = textFieldColors;
                                    }
                                    final boolean z16 = z5;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField (OutlinedTextField.kt:395)");
                                    }
                                    if (mutableInteractionSource2 != null) {
                                        composerImpl2.startReplaceGroup(943817043);
                                        Object objRememberedValue = composerImpl2.rememberedValue();
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        mutableInteractionSource3 = (MutableInteractionSource) objRememberedValue;
                                        r5 = 0;
                                        composerImpl2.end(false);
                                    } else {
                                        r5 = 0;
                                        composerImpl2.startReplaceGroup(30445060);
                                        composerImpl2.end(false);
                                        mutableInteractionSource3 = mutableInteractionSource2;
                                    }
                                    composerImpl2.startReplaceGroup(30451170);
                                    jM758getColor0d7_KjU = textStyle3.m758getColor0d7_KjU();
                                    if (jM758getColor0d7_KjU == 16) {
                                        jM758getColor0d7_KjU = textFieldColorsColors.m312textColorXeAY9LY$material3_release(z16, z7, ((Boolean) FocusInteractionKt.collectIsFocusedAsState(mutableInteractionSource3, composerImpl2, r5).getValue()).booleanValue());
                                    }
                                    long j = jM758getColor0d7_KjU;
                                    composerImpl2.end(r5);
                                    final TextStyle textStyleMerge = textStyle3.merge(new TextStyle(j, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777214, (DefaultConstructorMarker) null));
                                    final MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource3;
                                    CompositionLocalKt.CompositionLocalProvider(TextSelectionColorsKt.LocalTextSelectionColors.defaultProvidedValue$runtime_release(textFieldColorsColors.textSelectionColors), ComposableLambdaKt.rememberComposableLambda(-1886965181, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) {
                                            Modifier modifierM129paddingqDBjuR0$default;
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous> (OutlinedTextField.kt:407)");
                                                    }
                                                    Modifier modifier4 = modifier3;
                                                    if (function28 != null) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                        composerImpl4.startReplaceGroup(611116980);
                                                        modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SemanticsModifierKt.semantics(Modifier.Companion, true, new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj3) {
                                                                return Unit.INSTANCE;
                                                            }
                                                        }), 0.0f, TextFieldImplKt.minimizedLabelHalfHeight(composerImpl4), 0.0f, 0.0f, 13);
                                                        composerImpl4.end(false);
                                                    } else {
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                        composerImpl5.startReplaceGroup(611500667);
                                                        composerImpl5.end(false);
                                                        modifierM129paddingqDBjuR0$default = Modifier.Companion;
                                                    }
                                                    Modifier modifierThen = modifier4.then(modifierM129paddingqDBjuR0$default);
                                                    boolean z17 = z7;
                                                    int i46 = Strings.$r8$clinit;
                                                    Modifier modifierDefaultErrorSemantics = TextFieldImplKt.defaultErrorSemantics(modifierThen, z17, Strings_androidKt.m322getString2EP1pXo(R.string.default_error_message, composer2));
                                                    OutlinedTextFieldDefaults.INSTANCE.getClass();
                                                    Modifier modifierM130defaultMinSizeVpY3zN4 = SizeKt.m130defaultMinSizeVpY3zN4(modifierDefaultErrorSemantics, OutlinedTextFieldDefaults.MinWidth, OutlinedTextFieldDefaults.MinHeight);
                                                    TextFieldColors textFieldColors3 = textFieldColorsColors;
                                                    SolidColor solidColor = new SolidColor(z7 ? textFieldColors3.errorCursorColor : textFieldColors3.cursorColor, null);
                                                    final String str3 = str2;
                                                    Function1 function13 = function12;
                                                    final boolean z18 = z16;
                                                    boolean z19 = z9;
                                                    TextStyle textStyle5 = textStyleMerge;
                                                    KeyboardOptions keyboardOptions5 = keyboardOptions3;
                                                    KeyboardActions keyboardActions5 = keyboardActions3;
                                                    final boolean z20 = z8;
                                                    int i47 = i34;
                                                    int i48 = i35;
                                                    final VisualTransformation visualTransformation5 = visualTransformation3;
                                                    final MutableInteractionSource mutableInteractionSource6 = mutableInteractionSource5;
                                                    final boolean z21 = z7;
                                                    final Function2 function227 = function28;
                                                    final Function2 function228 = function212;
                                                    final Function2 function229 = function210;
                                                    final Function2 function230 = function211;
                                                    final Function2 function231 = function213;
                                                    final Function2 function232 = function214;
                                                    final Function2 function233 = function215;
                                                    final TextFieldColors textFieldColors4 = textFieldColorsColors;
                                                    final Shape shape5 = shape3;
                                                    BasicTextFieldKt.BasicTextField(str3, function13, modifierM130defaultMinSizeVpY3zN4, z18, z19, textStyle5, keyboardOptions5, keyboardActions5, z20, i47, i48, visualTransformation5, null, mutableInteractionSource6, solidColor, ComposableLambdaKt.rememberComposableLambda(1474611661, new Function3() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.2
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                            Function2 function234 = (Function2) obj3;
                                                            Composer composer3 = (Composer) obj4;
                                                            int iIntValue = ((Number) obj5).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= ((ComposerImpl) composer3).changedInstance(function234) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                                if (composerImpl6.getSkipping()) {
                                                                    composerImpl6.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous>.<anonymous> (OutlinedTextField.kt:441)");
                                                                    }
                                                                    OutlinedTextFieldDefaults outlinedTextFieldDefaults = OutlinedTextFieldDefaults.INSTANCE;
                                                                    String str4 = str3;
                                                                    final boolean z22 = z18;
                                                                    boolean z23 = z20;
                                                                    VisualTransformation visualTransformation6 = visualTransformation5;
                                                                    final MutableInteractionSource mutableInteractionSource7 = mutableInteractionSource6;
                                                                    final boolean z24 = z21;
                                                                    Function2 function235 = function227;
                                                                    Function2 function236 = function228;
                                                                    Function2 function237 = function229;
                                                                    Function2 function238 = function230;
                                                                    Function2 function239 = function231;
                                                                    Function2 function240 = function232;
                                                                    Function2 function241 = function233;
                                                                    final TextFieldColors textFieldColors5 = textFieldColors4;
                                                                    final Shape shape6 = shape5;
                                                                    outlinedTextFieldDefaults.DecorationBox(str4, function234, z22, z23, visualTransformation6, mutableInteractionSource7, z24, function235, function236, function237, function238, function239, function240, function241, textFieldColors5, null, ComposableLambdaKt.rememberComposableLambda(2108828640, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.2.1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(2);
                                                                        }

                                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                        @Override // kotlin.jvm.functions.Function2
                                                                        /*
                                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                                        */
                                                                        public final Object invoke(Object obj6, Object obj7) {
                                                                            Composer composer4 = (Composer) obj6;
                                                                            if ((((Number) obj7).intValue() & 3) == 2) {
                                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer4;
                                                                                if (composerImpl7.getSkipping()) {
                                                                                    composerImpl7.skipToGroupEnd();
                                                                                } else {
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous>.<anonymous>.<anonymous> (OutlinedTextField.kt:458)");
                                                                                    }
                                                                                    OutlinedTextFieldDefaults.INSTANCE.m276Container4EFweAY(z22, z24, mutableInteractionSource7, null, textFieldColors5, shape6, 0.0f, 0.0f, composer4, 100663296, 200);
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventEnd();
                                                                                    }
                                                                                }
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    }, composer3), composer3, (iIntValue << 3) & 112, 14155776, NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer2), composer2, 0, 196608, 4096);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), composerImpl2, 56);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    textStyle4 = textStyle3;
                                    composerImpl = composerImpl2;
                                    function216 = function28;
                                    z10 = z7;
                                    z11 = z16;
                                    z12 = z9;
                                    keyboardOptions4 = keyboardOptions3;
                                    keyboardActions4 = keyboardActions3;
                                    z13 = z8;
                                    i36 = i34;
                                    i37 = i35;
                                    visualTransformation4 = visualTransformation3;
                                    function217 = function212;
                                    function218 = function211;
                                    function219 = function213;
                                    function220 = function215;
                                    shape4 = shape3;
                                    mutableInteractionSource4 = mutableInteractionSource2;
                                    textFieldColors2 = textFieldColorsColors;
                                    function221 = function214;
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    final Modifier modifier4 = modifier3;
                                    final Function2 function227 = function210;
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.4
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ((Number) obj2).intValue();
                                            OutlinedTextFieldKt.OutlinedTextField(str, function1, modifier4, z11, z12, textStyle4, function216, function217, function227, function218, function219, function221, function220, z10, visualTransformation4, keyboardOptions4, keyboardActions4, z13, i36, i37, mutableInteractionSource4, shape4, textFieldColors2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i3 | 1), RecomposeScopeImplKt.updateChangedFlags(i4), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i27 = i25 | 24576;
                            i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                            if (i28 != 0) {
                            }
                            i29 = i6 & 65536;
                            if (i29 != 0) {
                            }
                            i30 = i6 & 131072;
                            if (i30 != 0) {
                            }
                            if ((i4 & 100663296) == 0) {
                            }
                            i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            if (i31 != 0) {
                            }
                            i32 = i6 & 1048576;
                            if (i32 != 0) {
                            }
                            if ((i5 & 48) == 0) {
                            }
                            if ((i5 & 384) == 0) {
                            }
                            int i432 = i33;
                            if ((i21 & 306783379) == 306783378) {
                                composerImpl2.startDefaults();
                                if ((i3 & 1) != 0) {
                                    if (i38 != 0) {
                                    }
                                    if (i8 != 0) {
                                    }
                                    if (i9 != 0) {
                                    }
                                    if ((i6 & 32) != 0) {
                                    }
                                    if (i10 != 0) {
                                    }
                                    if (i11 != 0) {
                                    }
                                    if (i13 == 0) {
                                    }
                                    if (i15 == 0) {
                                    }
                                    if (i17 == 0) {
                                    }
                                    if (i20 == 0) {
                                    }
                                    if (i22 == 0) {
                                    }
                                    if (i24 == 0) {
                                    }
                                    if (i26 == 0) {
                                    }
                                    if (i28 == 0) {
                                    }
                                    if (i29 == 0) {
                                    }
                                    if (i30 == 0) {
                                    }
                                    if ((i6 & 262144) == 0) {
                                    }
                                    if (i31 == 0) {
                                    }
                                    if (i32 == 0) {
                                    }
                                    if ((i6 & 2097152) == 0) {
                                    }
                                    if ((i6 & 4194304) == 0) {
                                    }
                                    visualTransformation3 = visualTransformation2;
                                    z9 = z6;
                                    textStyle3 = textStyle2;
                                    modifier3 = modifier2;
                                    final boolean z162 = z5;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    if (mutableInteractionSource2 != null) {
                                    }
                                    composerImpl2.startReplaceGroup(30451170);
                                    jM758getColor0d7_KjU = textStyle3.m758getColor0d7_KjU();
                                    if (jM758getColor0d7_KjU == 16) {
                                    }
                                    long j2 = jM758getColor0d7_KjU;
                                    composerImpl2.end(r5);
                                    final TextStyle textStyleMerge2 = textStyle3.merge(new TextStyle(j2, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777214, (DefaultConstructorMarker) null));
                                    final MutableInteractionSource mutableInteractionSource52 = mutableInteractionSource3;
                                    CompositionLocalKt.CompositionLocalProvider(TextSelectionColorsKt.LocalTextSelectionColors.defaultProvidedValue$runtime_release(textFieldColorsColors.textSelectionColors), ComposableLambdaKt.rememberComposableLambda(-1886965181, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj, Object obj2) {
                                            Modifier modifierM129paddingqDBjuR0$default;
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous> (OutlinedTextField.kt:407)");
                                                    }
                                                    Modifier modifier42 = modifier3;
                                                    if (function28 != null) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                        composerImpl4.startReplaceGroup(611116980);
                                                        modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SemanticsModifierKt.semantics(Modifier.Companion, true, new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj3) {
                                                                return Unit.INSTANCE;
                                                            }
                                                        }), 0.0f, TextFieldImplKt.minimizedLabelHalfHeight(composerImpl4), 0.0f, 0.0f, 13);
                                                        composerImpl4.end(false);
                                                    } else {
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                        composerImpl5.startReplaceGroup(611500667);
                                                        composerImpl5.end(false);
                                                        modifierM129paddingqDBjuR0$default = Modifier.Companion;
                                                    }
                                                    Modifier modifierThen = modifier42.then(modifierM129paddingqDBjuR0$default);
                                                    boolean z17 = z7;
                                                    int i46 = Strings.$r8$clinit;
                                                    Modifier modifierDefaultErrorSemantics = TextFieldImplKt.defaultErrorSemantics(modifierThen, z17, Strings_androidKt.m322getString2EP1pXo(R.string.default_error_message, composer2));
                                                    OutlinedTextFieldDefaults.INSTANCE.getClass();
                                                    Modifier modifierM130defaultMinSizeVpY3zN4 = SizeKt.m130defaultMinSizeVpY3zN4(modifierDefaultErrorSemantics, OutlinedTextFieldDefaults.MinWidth, OutlinedTextFieldDefaults.MinHeight);
                                                    TextFieldColors textFieldColors3 = textFieldColorsColors;
                                                    SolidColor solidColor = new SolidColor(z7 ? textFieldColors3.errorCursorColor : textFieldColors3.cursorColor, null);
                                                    final String str3 = str2;
                                                    Function1 function13 = function12;
                                                    final boolean z18 = z162;
                                                    boolean z19 = z9;
                                                    TextStyle textStyle5 = textStyleMerge2;
                                                    KeyboardOptions keyboardOptions5 = keyboardOptions3;
                                                    KeyboardActions keyboardActions5 = keyboardActions3;
                                                    final boolean z20 = z8;
                                                    int i47 = i34;
                                                    int i48 = i35;
                                                    final VisualTransformation visualTransformation5 = visualTransformation3;
                                                    final MutableInteractionSource mutableInteractionSource6 = mutableInteractionSource52;
                                                    final boolean z21 = z7;
                                                    final Function2 function2272 = function28;
                                                    final Function2 function228 = function212;
                                                    final Function2 function229 = function210;
                                                    final Function2 function230 = function211;
                                                    final Function2 function231 = function213;
                                                    final Function2 function232 = function214;
                                                    final Function2 function233 = function215;
                                                    final TextFieldColors textFieldColors4 = textFieldColorsColors;
                                                    final Shape shape5 = shape3;
                                                    BasicTextFieldKt.BasicTextField(str3, function13, modifierM130defaultMinSizeVpY3zN4, z18, z19, textStyle5, keyboardOptions5, keyboardActions5, z20, i47, i48, visualTransformation5, null, mutableInteractionSource6, solidColor, ComposableLambdaKt.rememberComposableLambda(1474611661, new Function3() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.2
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                            Function2 function234 = (Function2) obj3;
                                                            Composer composer3 = (Composer) obj4;
                                                            int iIntValue = ((Number) obj5).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= ((ComposerImpl) composer3).changedInstance(function234) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                                if (composerImpl6.getSkipping()) {
                                                                    composerImpl6.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous>.<anonymous> (OutlinedTextField.kt:441)");
                                                                    }
                                                                    OutlinedTextFieldDefaults outlinedTextFieldDefaults = OutlinedTextFieldDefaults.INSTANCE;
                                                                    String str4 = str3;
                                                                    final boolean z22 = z18;
                                                                    boolean z23 = z20;
                                                                    VisualTransformation visualTransformation6 = visualTransformation5;
                                                                    final MutableInteractionSource mutableInteractionSource7 = mutableInteractionSource6;
                                                                    final boolean z24 = z21;
                                                                    Function2 function235 = function2272;
                                                                    Function2 function236 = function228;
                                                                    Function2 function237 = function229;
                                                                    Function2 function238 = function230;
                                                                    Function2 function239 = function231;
                                                                    Function2 function240 = function232;
                                                                    Function2 function241 = function233;
                                                                    final TextFieldColors textFieldColors5 = textFieldColors4;
                                                                    final Shape shape6 = shape5;
                                                                    outlinedTextFieldDefaults.DecorationBox(str4, function234, z22, z23, visualTransformation6, mutableInteractionSource7, z24, function235, function236, function237, function238, function239, function240, function241, textFieldColors5, null, ComposableLambdaKt.rememberComposableLambda(2108828640, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField.3.2.1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(2);
                                                                        }

                                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                        @Override // kotlin.jvm.functions.Function2
                                                                        /*
                                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                                        */
                                                                        public final Object invoke(Object obj6, Object obj7) {
                                                                            Composer composer4 = (Composer) obj6;
                                                                            if ((((Number) obj7).intValue() & 3) == 2) {
                                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer4;
                                                                                if (composerImpl7.getSkipping()) {
                                                                                    composerImpl7.skipToGroupEnd();
                                                                                } else {
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextField.<anonymous>.<anonymous>.<anonymous> (OutlinedTextField.kt:458)");
                                                                                    }
                                                                                    OutlinedTextFieldDefaults.INSTANCE.m276Container4EFweAY(z22, z24, mutableInteractionSource7, null, textFieldColors5, shape6, 0.0f, 0.0f, composer4, 100663296, 200);
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventEnd();
                                                                                    }
                                                                                }
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    }, composer3), composer3, (iIntValue << 3) & 112, 14155776, NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer2), composer2, 0, 196608, 4096);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), composerImpl2, 56);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    textStyle4 = textStyle3;
                                    composerImpl = composerImpl2;
                                    function216 = function28;
                                    z10 = z7;
                                    z11 = z162;
                                    z12 = z9;
                                    keyboardOptions4 = keyboardOptions3;
                                    keyboardActions4 = keyboardActions3;
                                    z13 = z8;
                                    i36 = i34;
                                    i37 = i35;
                                    visualTransformation4 = visualTransformation3;
                                    function217 = function212;
                                    function218 = function211;
                                    function219 = function213;
                                    function220 = function215;
                                    shape4 = shape3;
                                    mutableInteractionSource4 = mutableInteractionSource2;
                                    textFieldColors2 = textFieldColorsColors;
                                    function221 = function214;
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i15 = i14;
                        i16 = i6 & 1024;
                        if (i16 == 0) {
                        }
                        i19 = i6 & 2048;
                        if (i19 == 0) {
                        }
                        int i402 = i18;
                        i21 = i7;
                        i22 = i6 & 4096;
                        if (i22 == 0) {
                        }
                        i24 = i6 & 8192;
                        if (i24 == 0) {
                        }
                        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        if (i26 != 0) {
                        }
                        i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i28 != 0) {
                        }
                        i29 = i6 & 65536;
                        if (i29 != 0) {
                        }
                        i30 = i6 & 131072;
                        if (i30 != 0) {
                        }
                        if ((i4 & 100663296) == 0) {
                        }
                        i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        if (i31 != 0) {
                        }
                        i32 = i6 & 1048576;
                        if (i32 != 0) {
                        }
                        if ((i5 & 48) == 0) {
                        }
                        if ((i5 & 384) == 0) {
                        }
                        int i4322 = i33;
                        if ((i21 & 306783379) == 306783378) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    i13 = i12;
                    i14 = i6 & 512;
                    if (i14 != 0) {
                    }
                    i15 = i14;
                    i16 = i6 & 1024;
                    if (i16 == 0) {
                    }
                    i19 = i6 & 2048;
                    if (i19 == 0) {
                    }
                    int i4022 = i18;
                    i21 = i7;
                    i22 = i6 & 4096;
                    if (i22 == 0) {
                    }
                    i24 = i6 & 8192;
                    if (i24 == 0) {
                    }
                    i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if (i26 != 0) {
                    }
                    i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i28 != 0) {
                    }
                    i29 = i6 & 65536;
                    if (i29 != 0) {
                    }
                    i30 = i6 & 131072;
                    if (i30 != 0) {
                    }
                    if ((i4 & 100663296) == 0) {
                    }
                    i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    if (i31 != 0) {
                    }
                    i32 = i6 & 1048576;
                    if (i32 != 0) {
                    }
                    if ((i5 & 48) == 0) {
                    }
                    if ((i5 & 384) == 0) {
                    }
                    int i43222 = i33;
                    if ((i21 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                z6 = z2;
                if ((i3 & 196608) != 0) {
                }
                i10 = i6 & 64;
                if (i10 == 0) {
                }
                i11 = i6 & 128;
                if (i11 == 0) {
                }
                i12 = i6 & 256;
                if (i12 == 0) {
                }
                i13 = i12;
                i14 = i6 & 512;
                if (i14 != 0) {
                }
                i15 = i14;
                i16 = i6 & 1024;
                if (i16 == 0) {
                }
                i19 = i6 & 2048;
                if (i19 == 0) {
                }
                int i40222 = i18;
                i21 = i7;
                i22 = i6 & 4096;
                if (i22 == 0) {
                }
                i24 = i6 & 8192;
                if (i24 == 0) {
                }
                i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i26 != 0) {
                }
                i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i28 != 0) {
                }
                i29 = i6 & 65536;
                if (i29 != 0) {
                }
                i30 = i6 & 131072;
                if (i30 != 0) {
                }
                if ((i4 & 100663296) == 0) {
                }
                i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i31 != 0) {
                }
                i32 = i6 & 1048576;
                if (i32 != 0) {
                }
                if ((i5 & 48) == 0) {
                }
                if ((i5 & 384) == 0) {
                }
                int i432222 = i33;
                if ((i21 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z5 = z;
            i9 = i6 & 16;
            int i392 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i9 != 0) {
            }
            z6 = z2;
            if ((i3 & 196608) != 0) {
            }
            i10 = i6 & 64;
            if (i10 == 0) {
            }
            i11 = i6 & 128;
            if (i11 == 0) {
            }
            i12 = i6 & 256;
            if (i12 == 0) {
            }
            i13 = i12;
            i14 = i6 & 512;
            if (i14 != 0) {
            }
            i15 = i14;
            i16 = i6 & 1024;
            if (i16 == 0) {
            }
            i19 = i6 & 2048;
            if (i19 == 0) {
            }
            int i402222 = i18;
            i21 = i7;
            i22 = i6 & 4096;
            if (i22 == 0) {
            }
            i24 = i6 & 8192;
            if (i24 == 0) {
            }
            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i26 != 0) {
            }
            i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i28 != 0) {
            }
            i29 = i6 & 65536;
            if (i29 != 0) {
            }
            i30 = i6 & 131072;
            if (i30 != 0) {
            }
            if ((i4 & 100663296) == 0) {
            }
            i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i31 != 0) {
            }
            i32 = i6 & 1048576;
            if (i32 != 0) {
            }
            if ((i5 & 48) == 0) {
            }
            if ((i5 & 384) == 0) {
            }
            int i4322222 = i33;
            if ((i21 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i8 = i6 & 8;
        if (i8 == 0) {
        }
        z5 = z;
        i9 = i6 & 16;
        int i3922 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i9 != 0) {
        }
        z6 = z2;
        if ((i3 & 196608) != 0) {
        }
        i10 = i6 & 64;
        if (i10 == 0) {
        }
        i11 = i6 & 128;
        if (i11 == 0) {
        }
        i12 = i6 & 256;
        if (i12 == 0) {
        }
        i13 = i12;
        i14 = i6 & 512;
        if (i14 != 0) {
        }
        i15 = i14;
        i16 = i6 & 1024;
        if (i16 == 0) {
        }
        i19 = i6 & 2048;
        if (i19 == 0) {
        }
        int i4022222 = i18;
        i21 = i7;
        i22 = i6 & 4096;
        if (i22 == 0) {
        }
        i24 = i6 & 8192;
        if (i24 == 0) {
        }
        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i26 != 0) {
        }
        i28 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i28 != 0) {
        }
        i29 = i6 & 65536;
        if (i29 != 0) {
        }
        i30 = i6 & 131072;
        if (i30 != 0) {
        }
        if ((i4 & 100663296) == 0) {
        }
        i31 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i31 != 0) {
        }
        i32 = i6 & 1048576;
        if (i32 != 0) {
        }
        if ((i5 & 48) == 0) {
        }
        if ((i5 & 384) == 0) {
        }
        int i43222222 = i33;
        if ((i21 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:151:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0591  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void OutlinedTextFieldLayout(final Modifier modifier, final Function2 function2, final Function3 function3, Function2 function22, final Function2 function23, final Function2 function24, final Function2 function25, Function2 function26, final boolean z, TextFieldLabelPosition textFieldLabelPosition, final FloatProducer floatProducer, final Function1 function1, final Function2 function27, Function2 function28, PaddingValues paddingValues, Composer composer, final int i, final int i2) {
        int i3;
        boolean z2;
        int i4;
        ComposerImpl composerImpl;
        float f;
        Object outlinedTextFieldMeasurePolicy;
        int i5;
        PaddingValues paddingValues2;
        int i6;
        Function2 function29;
        int i7;
        float f2;
        float f3;
        TextFieldLabelPosition textFieldLabelPosition2;
        Modifier modifier2;
        Function2 function210;
        Function2 function211;
        boolean z3;
        final FloatProducer floatProducer2;
        boolean z4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-564923171);
        if ((i & 6) == 0) {
            i3 = i | (composerImpl2.changed(modifier) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changedInstance(function2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changedInstance(function3) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changedInstance(function22) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl2.changedInstance(function23) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i & 196608) == 0) {
            i3 |= composerImpl2.changedInstance(function24) ? 131072 : 65536;
        }
        if ((i & 1572864) == 0) {
            i3 |= composerImpl2.changedInstance(function25) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((i & 12582912) == 0) {
            i3 |= composerImpl2.changedInstance(function26) ? 8388608 : 4194304;
        }
        if ((i & 100663296) == 0) {
            z2 = z;
            i3 |= composerImpl2.changed(z2) ? 67108864 : 33554432;
        } else {
            z2 = z;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerImpl2.changed(textFieldLabelPosition) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((i2 & 6) == 0) {
            i4 = i2 | ((i2 & 8) == 0 ? composerImpl2.changed(floatProducer) : composerImpl2.changedInstance(floatProducer) ? 4 : 2);
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl2.changedInstance(function27) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl2.changedInstance(function28) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerImpl2.changed(paddingValues) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        int i8 = i4;
        if ((i3 & 306783379) == 306783378 && (i8 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            function210 = function22;
            textFieldLabelPosition2 = textFieldLabelPosition;
            function211 = function28;
            paddingValues2 = paddingValues;
            function29 = function26;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.OutlinedTextFieldLayout (OutlinedTextField.kt:662)");
            }
            float fTextFieldHorizontalIconPadding = TextFieldImplKt.textFieldHorizontalIconPadding(composerImpl2);
            int i9 = i8 & 14;
            boolean zChanged = ((i8 & 112) == 32) | ((i3 & 234881024) == 67108864) | ((i3 & 1879048192) == 536870912) | (i9 == 4 || ((i8 & 8) != 0 && composerImpl2.changed(floatProducer))) | ((i8 & 57344) == 16384) | composerImpl2.changed(fTextFieldHorizontalIconPadding);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    composerImpl = composerImpl2;
                    f = fTextFieldHorizontalIconPadding;
                    boolean z5 = z2;
                    i5 = i9;
                    paddingValues2 = paddingValues;
                    outlinedTextFieldMeasurePolicy = new OutlinedTextFieldMeasurePolicy(function1, z5, textFieldLabelPosition, floatProducer, paddingValues2, f, null);
                    composerImpl.updateRememberedValue(outlinedTextFieldMeasurePolicy);
                } else {
                    paddingValues2 = paddingValues;
                    composerImpl = composerImpl2;
                    i5 = i9;
                    f = fTextFieldHorizontalIconPadding;
                    outlinedTextFieldMeasurePolicy = objRememberedValue;
                }
                OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy2 = (OutlinedTextFieldMeasurePolicy) outlinedTextFieldMeasurePolicy;
                LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                float f4 = f;
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                Applier applier = composerImpl.applier;
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Function2 function212 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl, outlinedTextFieldMeasurePolicy2, function212);
                Function2 function213 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function213);
                Function2 function214 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function214);
                }
                Function2 function215 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function215);
                function27.invoke(composerImpl, Integer.valueOf((i8 >> 6) & 14));
                if (function23 != null) {
                    composerImpl.startReplaceGroup(-816491150);
                    Modifier modifierThen = LayoutIdKt.layoutId(Modifier.Companion, "Leading").then(MinimumInteractiveModifier.INSTANCE);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function215);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 12) & 14, function23, composerImpl, true, false);
                } else {
                    composerImpl.startReplaceGroup(-816246095);
                    composerImpl.end(false);
                }
                if (function24 != null) {
                    composerImpl.startReplaceGroup(-816203408);
                    Modifier modifierThen2 = LayoutIdKt.layoutId(Modifier.Companion, "Trailing").then(MinimumInteractiveModifier.INSTANCE);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen2);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function215);
                    BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                    i6 = 0;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 15) & 14, function24, composerImpl, true, false);
                } else {
                    i6 = 0;
                    composerImpl.startReplaceGroup(-815956431);
                    composerImpl.end(false);
                }
                float fCalculateStartPadding = PaddingKt.calculateStartPadding(paddingValues2, layoutDirection);
                float fCalculateEndPadding = PaddingKt.calculateEndPadding(paddingValues2, layoutDirection);
                if (function23 != null) {
                    fCalculateStartPadding -= f4;
                    float f5 = i6;
                    if (fCalculateStartPadding < f5) {
                        fCalculateStartPadding = f5;
                    }
                }
                float f6 = fCalculateStartPadding;
                if (function24 != null) {
                    fCalculateEndPadding -= f4;
                    float f7 = i6;
                    if (fCalculateEndPadding < f7) {
                        fCalculateEndPadding = f7;
                    }
                }
                float f8 = fCalculateEndPadding;
                if (function25 != null) {
                    composerImpl.startReplaceGroup(-815252452);
                    Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(Modifier.Companion, "Prefix"), TextFieldImplKt.MinTextLineHeight, 0.0f, 2), 3), f6, 0.0f, TextFieldImplKt.PrefixSuffixTextPadding, 0.0f, 10);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function215);
                    BoxScopeInstance boxScopeInstance3 = BoxScopeInstance.INSTANCE;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 18) & 14, function25, composerImpl, true, false);
                } else {
                    composerImpl.startReplaceGroup(-814924751);
                    composerImpl.end(false);
                }
                if (function26 != null) {
                    composerImpl.startReplaceGroup(-814881506);
                    Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(Modifier.Companion, "Suffix"), TextFieldImplKt.MinTextLineHeight, 0.0f, 2), 3), TextFieldImplKt.PrefixSuffixTextPadding, 0.0f, f8, 0.0f, 10);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy4 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default2);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy4, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope5, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier5, function215);
                    BoxScopeInstance boxScopeInstance4 = BoxScopeInstance.INSTANCE;
                    function29 = function26;
                    i7 = 0;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 21) & 14, function29, composerImpl, true, false);
                } else {
                    function29 = function26;
                    i7 = 0;
                    composerImpl.startReplaceGroup(-814555727);
                    composerImpl.end(false);
                }
                Modifier modifierM129paddingqDBjuR0$default3 = Modifier.Companion;
                Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(modifierM129paddingqDBjuR0$default3, TextFieldImplKt.MinTextLineHeight, 0.0f, 2), 3);
                if (function25 == null) {
                    f2 = f6;
                } else {
                    Dp.Companion companion2 = Dp.Companion;
                    f2 = i7;
                }
                if (function29 == null) {
                    f3 = f8;
                } else {
                    Dp.Companion companion3 = Dp.Companion;
                    f3 = i7;
                }
                Modifier modifierM129paddingqDBjuR0$default4 = PaddingKt.m129paddingqDBjuR0$default(modifierWrapContentHeight$default, f2, 0.0f, f3, 0.0f, 10);
                if (function3 != null) {
                    composerImpl.startReplaceGroup(-814185742);
                    function3.invoke(LayoutIdKt.layoutId(modifierM129paddingqDBjuR0$default3, "Hint").then(modifierM129paddingqDBjuR0$default4), composerImpl, Integer.valueOf((i3 >> 3) & 112));
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(-814094447);
                    composerImpl.end(false);
                }
                Modifier modifierThen3 = LayoutIdKt.layoutId(modifierM129paddingqDBjuR0$default3, "TextField").then(modifierM129paddingqDBjuR0$default4);
                Alignment.Companion.getClass();
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy5 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
                int currentCompositeKeyHash6 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope6 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier6 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen3);
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy5, function212);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope6, function213);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash6))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash6, composerImpl, currentCompositeKeyHash6, function214);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier6, function215);
                BoxScopeInstance boxScopeInstance5 = BoxScopeInstance.INSTANCE;
                function2.invoke(composerImpl, Integer.valueOf((i3 >> 3) & 14));
                composerImpl.end(true);
                textFieldLabelPosition2 = textFieldLabelPosition;
                if (textFieldLabelPosition2 instanceof TextFieldLabelPosition.Above) {
                    float f9 = TextFieldImplKt.AboveLabelHorizontalPadding;
                    modifierM129paddingqDBjuR0$default3 = PaddingKt.m129paddingqDBjuR0$default(modifierM129paddingqDBjuR0$default3, f9, 0.0f, f9, TextFieldImplKt.AboveLabelBottomPadding, 2);
                    modifier2 = modifierM129paddingqDBjuR0$default3;
                } else {
                    modifier2 = modifierM129paddingqDBjuR0$default3;
                }
                if (function22 != null) {
                    composerImpl.startReplaceGroup(-813442858);
                    if (i5 != 4) {
                        if ((i8 & 8) != 0) {
                            floatProducer2 = floatProducer;
                            if (composerImpl.changedInstance(floatProducer2)) {
                            }
                            Object objRememberedValue2 = composerImpl.rememberedValue();
                            if (!z4) {
                                companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new Function0() { // from class: androidx.compose.material3.OutlinedTextFieldKt$OutlinedTextFieldLayout$1$6$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return Dp.m837boximpl(MathHelpersKt.lerp(TextFieldImplKt.MinTextLineHeight, TextFieldImplKt.MinFocusedLabelLineHeight, floatProducer2.invoke()));
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                }
                                Modifier modifierThen4 = LayoutIdKt.layoutId(SizeKt.wrapContentHeight$default(TextFieldImplKt.textFieldLabelMinHeight(modifier2, (Function0) objRememberedValue2), 3), "Label").then(modifierM129paddingqDBjuR0$default3);
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy6 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                int currentCompositeKeyHash7 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope7 = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier7 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen4);
                                if (applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function0);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy6, function212);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope7, function213);
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash7))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash7, composerImpl, currentCompositeKeyHash7, function214);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier7, function215);
                                function210 = function22;
                                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 9) & 14, function210, composerImpl, true, false);
                            }
                        } else {
                            floatProducer2 = floatProducer;
                        }
                        z4 = false;
                        Object objRememberedValue22 = composerImpl.rememberedValue();
                        if (!z4) {
                        }
                    } else {
                        floatProducer2 = floatProducer;
                    }
                    z4 = true;
                    Object objRememberedValue222 = composerImpl.rememberedValue();
                    if (!z4) {
                    }
                } else {
                    function210 = function22;
                    composerImpl.startReplaceGroup(-813047887);
                    composerImpl.end(false);
                }
                if (function28 != null) {
                    composerImpl.startReplaceGroup(-812999372);
                    Modifier modifierPadding = PaddingKt.padding(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(modifier2, "Supporting"), TextFieldImplKt.MinSupportingTextLineHeight, 0.0f, 2), 3), TextFieldDefaults.m314supportingTextPaddinga9UjIt4$material3_release$default(TextFieldDefaults.INSTANCE));
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy7 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash8 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope8 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier8 = ComposedModifierKt.materializeModifier(composerImpl, modifierPadding);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy7, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope8, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash8))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash8, composerImpl, currentCompositeKeyHash8, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier8, function215);
                    function211 = function28;
                    z3 = true;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i8 >> 9) & 14, function211, composerImpl, true, false);
                } else {
                    function211 = function28;
                    z3 = true;
                    composerImpl.startReplaceGroup(-812663983);
                    composerImpl.end(false);
                }
                composerImpl.end(z3);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function2 function216 = function210;
            final Function2 function217 = function29;
            final TextFieldLabelPosition textFieldLabelPosition3 = textFieldLabelPosition2;
            final PaddingValues paddingValues3 = paddingValues2;
            final Function2 function218 = function211;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextFieldLayout.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    OutlinedTextFieldKt.OutlinedTextFieldLayout(modifier, function2, function3, function216, function23, function24, function25, function217, z, textFieldLabelPosition3, floatProducer, function1, function27, function218, paddingValues3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier outlineCutout(Modifier modifier, final Function0 function0, final Alignment.Horizontal horizontal, final PaddingValues paddingValues) {
        return DrawModifierKt.drawWithContent(modifier, new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldKt.outlineCutout.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ContentDrawScope contentDrawScope = (ContentDrawScope) obj;
                long j = ((Size) function0.invoke()).packedValue;
                float fM419getWidthimpl = Size.m419getWidthimpl(j);
                if (fM419getWidthimpl > 0.0f) {
                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
                    float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(OutlinedTextFieldKt.OutlinedTextFieldInnerPadding);
                    float fMo58toPx0680j_42 = layoutNodeDrawScope.mo58toPx0680j_4(paddingValues.mo111calculateLeftPaddingu2uoSUM(layoutNodeDrawScope.getLayoutDirection()));
                    float fMo58toPx0680j_43 = layoutNodeDrawScope.mo58toPx0680j_4(paddingValues.mo112calculateRightPaddingu2uoSUM(layoutNodeDrawScope.getLayoutDirection()));
                    Alignment.Horizontal horizontal2 = horizontal;
                    int iRoundToInt = MathKt__MathJVMKt.roundToInt(fM419getWidthimpl);
                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                    float fAlign = horizontal2.align(iRoundToInt, MathKt__MathJVMKt.roundToInt((Size.m419getWidthimpl(canvasDrawScope.mo547getSizeNHjbRc()) - fMo58toPx0680j_42) - fMo58toPx0680j_43), layoutNodeDrawScope.getLayoutDirection()) + fMo58toPx0680j_42;
                    float f = 2;
                    float f2 = fM419getWidthimpl / f;
                    float f3 = fAlign + f2;
                    float f4 = (f3 - f2) - fMo58toPx0680j_4;
                    float f5 = f4 < 0.0f ? 0.0f : f4;
                    float f6 = f3 + f2 + fMo58toPx0680j_4;
                    float fM419getWidthimpl2 = Size.m419getWidthimpl(canvasDrawScope.mo547getSizeNHjbRc());
                    float f7 = f6 > fM419getWidthimpl2 ? fM419getWidthimpl2 : f6;
                    float fM417getHeightimpl = Size.m417getHeightimpl(j);
                    float f8 = (-fM417getHeightimpl) / f;
                    float f9 = fM417getHeightimpl / f;
                    ClipOp.Companion.getClass();
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                    canvasDrawScope$drawContext$1.getCanvas().save();
                    try {
                        canvasDrawScope$drawContext$1.transform.m530clipRectN_I0leg(f5, f8, f7, f9, 0);
                        layoutNodeDrawScope.drawContent();
                    } finally {
                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, jM528getSizeNHjbRc);
                    }
                } else {
                    ((LayoutNodeDrawScope) contentDrawScope).drawContent();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
