package androidx.compose.material3;

import androidx.compose.foundation.text.BasicTextKt;
import androidx.compose.material3.tokens.TypographyTokensKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.TextUnit;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class TextKt {
    public static final DynamicProvidableCompositionLocal LocalTextStyle = new DynamicProvidableCompositionLocal(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.TextKt$LocalTextStyle$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TypographyTokensKt.DefaultTextStyle;
        }
    });

    public static final void ProvideTextStyle(final TextStyle textStyle, final Function2 function2, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-460300127);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(textStyle) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.ProvideTextStyle (Text.kt:345)");
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = LocalTextStyle;
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal)).merge(textStyle)), function2, composerImpl, (i2 & 112) | 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextKt.ProvideTextStyle.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextKt.ProvideTextStyle(textStyle, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:267:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0131  */
    /* renamed from: Text--4IGK_g, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m317Text4IGK_g(final String str, Modifier modifier, long j, long j2, FontStyle fontStyle, FontWeight fontWeight, FontFamily fontFamily, long j3, TextDecoration textDecoration, TextAlign textAlign, long j4, int i, boolean z, int i2, int i3, Function1 function1, TextStyle textStyle, Composer composer, final int i4, final int i5, final int i6) {
        int i7;
        int i8;
        int i9;
        long j5;
        int i10;
        FontStyle fontStyle2;
        int i11;
        FontWeight fontWeight2;
        int i12;
        FontFamily fontFamily2;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        boolean z2;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        Modifier modifier2;
        long j6;
        long j7;
        TextDecoration textDecoration2;
        TextAlign textAlign2;
        long j8;
        int i27;
        int i28;
        int i29;
        Function1 function12;
        TextStyle textStyle2;
        long jM758getColor0d7_KjU;
        Modifier modifier3;
        int i30;
        Modifier modifier4;
        ComposerImpl composerImpl;
        final TextStyle textStyle3;
        final FontStyle fontStyle3;
        final FontFamily fontFamily3;
        final int i31;
        final long j9;
        final int i32;
        final boolean z3;
        final TextDecoration textDecoration3;
        final long j10;
        final long j11;
        final int i33;
        final long j12;
        final Function1 function13;
        final FontWeight fontWeight3;
        final TextAlign textAlign3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2055108902);
        if ((i6 & 1) != 0) {
            i7 = i4 | 6;
        } else if ((i4 & 6) == 0) {
            i7 = (composerImpl2.changed(str) ? 4 : 2) | i4;
        } else {
            i7 = i4;
        }
        int i34 = i6 & 2;
        if (i34 != 0) {
            i7 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                i7 |= composerImpl2.changed(modifier) ? 32 : 16;
            }
            i8 = i6 & 4;
            if (i8 == 0) {
                i7 |= 384;
            } else if ((i4 & 384) == 0) {
                i7 |= composerImpl2.changed(j) ? 256 : 128;
            }
            i9 = i6 & 8;
            if (i9 == 0) {
                i7 |= 3072;
                j5 = j2;
            } else {
                j5 = j2;
                if ((i4 & 3072) == 0) {
                    i7 |= composerImpl2.changed(j5) ? 2048 : 1024;
                }
            }
            i10 = i6 & 16;
            int i35 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i10 == 0) {
                i7 |= 24576;
            } else {
                if ((i4 & 24576) == 0) {
                    fontStyle2 = fontStyle;
                    i7 |= composerImpl2.changed(fontStyle2) ? 16384 : 8192;
                }
                i11 = i6 & 32;
                if (i11 != 0) {
                    i7 |= 196608;
                    fontWeight2 = fontWeight;
                } else {
                    fontWeight2 = fontWeight;
                    if ((i4 & 196608) == 0) {
                        i7 |= composerImpl2.changed(fontWeight2) ? 131072 : 65536;
                    }
                }
                i12 = i6 & 64;
                int i36 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i12 != 0) {
                    i7 |= 1572864;
                    fontFamily2 = fontFamily;
                } else {
                    fontFamily2 = fontFamily;
                    if ((i4 & 1572864) == 0) {
                        i7 |= composerImpl2.changed(fontFamily2) ? 1048576 : 524288;
                    }
                }
                i13 = i6 & 128;
                if (i13 != 0) {
                    i7 |= 12582912;
                } else if ((i4 & 12582912) == 0) {
                    i7 |= composerImpl2.changed(j3) ? 8388608 : 4194304;
                }
                i14 = i6 & 256;
                if (i14 != 0) {
                    i7 |= 100663296;
                } else if ((i4 & 100663296) == 0) {
                    i7 |= composerImpl2.changed(textDecoration) ? 67108864 : 33554432;
                }
                i15 = i6 & 512;
                if (i15 != 0) {
                    i7 |= 805306368;
                } else {
                    if ((i4 & 805306368) == 0) {
                        i16 = i15;
                        i7 |= composerImpl2.changed(textAlign) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    i17 = i6 & 1024;
                    if (i17 == 0) {
                        i18 = i5 | 6;
                    } else if ((i5 & 6) == 0) {
                        i18 = i5 | (composerImpl2.changed(j4) ? 4 : 2);
                    } else {
                        i18 = i5;
                    }
                    i19 = i6 & 2048;
                    if (i19 == 0) {
                        i18 |= 48;
                        i20 = i19;
                    } else if ((i5 & 48) == 0) {
                        i20 = i19;
                        i18 |= composerImpl2.changed(i) ? 32 : 16;
                    } else {
                        i20 = i19;
                    }
                    int i37 = i18;
                    i21 = i6 & 4096;
                    if (i21 == 0) {
                        i37 |= 384;
                    } else {
                        if ((i5 & 384) == 0) {
                            z2 = z;
                            i37 |= composerImpl2.changed(z2) ? 256 : 128;
                        }
                        i22 = i6 & 8192;
                        if (i22 != 0) {
                            i23 = i37 | 3072;
                        } else {
                            int i38 = i37;
                            if ((i5 & 3072) == 0) {
                                i23 = i38 | (composerImpl2.changed(i2) ? 2048 : 1024);
                            } else {
                                i23 = i38;
                            }
                        }
                        i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        if (i24 == 0) {
                            i25 = i23;
                            if ((i5 & 24576) == 0) {
                                if (!composerImpl2.changed(i3)) {
                                    i35 = 8192;
                                }
                                i25 |= i35;
                            }
                            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                            if (i26 == 0) {
                                i25 |= 196608;
                            } else if ((i5 & 196608) == 0) {
                                i25 |= composerImpl2.changedInstance(function1) ? 131072 : 65536;
                            }
                            if ((i5 & 1572864) == 0) {
                                if ((i6 & 65536) == 0 && composerImpl2.changed(textStyle)) {
                                    i36 = 1048576;
                                }
                                i25 |= i36;
                            }
                            if ((i7 & 306783379) != 306783378 && (i25 & 599187) == 599186 && composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                modifier4 = modifier;
                                textDecoration3 = textDecoration;
                                i33 = i;
                                i31 = i2;
                                i32 = i3;
                                function13 = function1;
                                textStyle3 = textStyle;
                                composerImpl = composerImpl2;
                                z3 = z2;
                                fontStyle3 = fontStyle2;
                                j10 = j5;
                                fontWeight3 = fontWeight2;
                                fontFamily3 = fontFamily2;
                                j9 = j;
                                j11 = j3;
                                textAlign3 = textAlign;
                                j12 = j4;
                            } else {
                                composerImpl2.startDefaults();
                                if ((i4 & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                    modifier2 = i34 == 0 ? Modifier.Companion : modifier;
                                    if (i8 == 0) {
                                        Color.Companion.getClass();
                                        j6 = Color.Unspecified;
                                    } else {
                                        j6 = j;
                                    }
                                    if (i9 != 0) {
                                        TextUnit.Companion.getClass();
                                        j5 = TextUnit.Unspecified;
                                    }
                                    if (i10 != 0) {
                                        fontStyle2 = null;
                                    }
                                    if (i11 != 0) {
                                        fontWeight2 = null;
                                    }
                                    if (i12 != 0) {
                                        fontFamily2 = null;
                                    }
                                    if (i13 == 0) {
                                        TextUnit.Companion.getClass();
                                        j7 = TextUnit.Unspecified;
                                    } else {
                                        j7 = j3;
                                    }
                                    textDecoration2 = i14 == 0 ? null : textDecoration;
                                    textAlign2 = i16 == 0 ? null : textAlign;
                                    if (i17 == 0) {
                                        TextUnit.Companion.getClass();
                                        j8 = TextUnit.Unspecified;
                                    } else {
                                        j8 = j4;
                                    }
                                    if (i20 == 0) {
                                        TextOverflow.Companion.getClass();
                                        i27 = TextOverflow.Clip;
                                    } else {
                                        i27 = i;
                                    }
                                    if (i21 != 0) {
                                        z2 = true;
                                    }
                                    i28 = i22 == 0 ? Integer.MAX_VALUE : i2;
                                    i29 = i24 == 0 ? i3 : 1;
                                    function12 = i26 == 0 ? function1 : null;
                                    if ((i6 & 65536) == 0) {
                                        textStyle2 = (TextStyle) composerImpl2.consume(LocalTextStyle);
                                        i25 &= -3670017;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.Text (Text.kt:109)");
                                    }
                                    composerImpl2.startReplaceGroup(-1827892941);
                                    if (j6 != 16) {
                                        modifier3 = modifier2;
                                        jM758getColor0d7_KjU = j6;
                                    } else {
                                        composerImpl2.startReplaceGroup(-1827892168);
                                        jM758getColor0d7_KjU = textStyle2.m758getColor0d7_KjU();
                                        if (jM758getColor0d7_KjU != 16) {
                                            modifier3 = modifier2;
                                        } else {
                                            modifier3 = modifier2;
                                            jM758getColor0d7_KjU = ((Color) composerImpl2.consume(ContentColorKt.LocalContentColor)).value;
                                        }
                                        composerImpl2.end(false);
                                    }
                                    composerImpl2.end(false);
                                    if (textAlign2 != null) {
                                        i30 = textAlign2.value;
                                    } else {
                                        TextAlign.Companion.getClass();
                                        i30 = TextAlign.Unspecified;
                                    }
                                    int i39 = (i7 & 126) | ((i25 >> 6) & 7168);
                                    int i40 = i25 << 9;
                                    Modifier modifier5 = modifier3;
                                    BasicTextKt.m195BasicTextVhcvRP8(str, modifier5, TextStyle.m757mergedA7vx0o$default(textStyle2, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i30, j8, 16609104), function12, i27, z2, i28, i29, null, composerImpl2, i39 | (i40 & 57344) | (i40 & 458752) | (i40 & 3670016) | (i40 & 29360128), 256);
                                    modifier4 = modifier5;
                                    composerImpl = composerImpl2;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    textStyle3 = textStyle2;
                                    fontStyle3 = fontStyle2;
                                    fontFamily3 = fontFamily2;
                                    i31 = i28;
                                    j9 = j6;
                                    i32 = i29;
                                    z3 = z2;
                                    TextAlign textAlign4 = textAlign2;
                                    textDecoration3 = textDecoration2;
                                    j10 = j5;
                                    j11 = j7;
                                    i33 = i27;
                                    j12 = j8;
                                    function13 = function12;
                                    fontWeight3 = fontWeight2;
                                    textAlign3 = textAlign4;
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i6 & 65536) != 0) {
                                        i25 &= -3670017;
                                    }
                                    modifier2 = modifier;
                                    j6 = j;
                                    j7 = j3;
                                    textDecoration2 = textDecoration;
                                    textAlign2 = textAlign;
                                    j8 = j4;
                                    i27 = i;
                                    i28 = i2;
                                    i29 = i3;
                                    function12 = function1;
                                }
                                textStyle2 = textStyle;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl2.startReplaceGroup(-1827892941);
                                if (j6 != 16) {
                                }
                                composerImpl2.end(false);
                                if (textAlign2 != null) {
                                }
                                int i392 = (i7 & 126) | ((i25 >> 6) & 7168);
                                int i402 = i25 << 9;
                                Modifier modifier52 = modifier3;
                                BasicTextKt.m195BasicTextVhcvRP8(str, modifier52, TextStyle.m757mergedA7vx0o$default(textStyle2, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i30, j8, 16609104), function12, i27, z2, i28, i29, null, composerImpl2, i392 | (i402 & 57344) | (i402 & 458752) | (i402 & 3670016) | (i402 & 29360128), 256);
                                modifier4 = modifier52;
                                composerImpl = composerImpl2;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                textStyle3 = textStyle2;
                                fontStyle3 = fontStyle2;
                                fontFamily3 = fontFamily2;
                                i31 = i28;
                                j9 = j6;
                                i32 = i29;
                                z3 = z2;
                                TextAlign textAlign42 = textAlign2;
                                textDecoration3 = textDecoration2;
                                j10 = j5;
                                j11 = j7;
                                i33 = i27;
                                j12 = j8;
                                function13 = function12;
                                fontWeight3 = fontWeight2;
                                textAlign3 = textAlign42;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final Modifier modifier6 = modifier4;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextKt$Text$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        TextKt.m317Text4IGK_g(str, modifier6, j9, j10, fontStyle3, fontWeight3, fontFamily3, j11, textDecoration3, textAlign3, j12, i33, z3, i31, i32, function13, textStyle3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i25 = i23 | 24576;
                        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i26 == 0) {
                        }
                        if ((i5 & 1572864) == 0) {
                        }
                        if ((i7 & 306783379) != 306783378) {
                            composerImpl2.startDefaults();
                            if ((i4 & 1) != 0) {
                                if (i34 == 0) {
                                }
                                if (i8 == 0) {
                                }
                                if (i9 != 0) {
                                }
                                if (i10 != 0) {
                                }
                                if (i11 != 0) {
                                }
                                if (i12 != 0) {
                                }
                                if (i13 == 0) {
                                }
                                if (i14 == 0) {
                                }
                                if (i16 == 0) {
                                }
                                if (i17 == 0) {
                                }
                                if (i20 == 0) {
                                }
                                if (i21 != 0) {
                                }
                                if (i22 == 0) {
                                }
                                if (i24 == 0) {
                                }
                                if (i26 == 0) {
                                }
                                if ((i6 & 65536) == 0) {
                                    textStyle2 = textStyle;
                                }
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl2.startReplaceGroup(-1827892941);
                                if (j6 != 16) {
                                }
                                composerImpl2.end(false);
                                if (textAlign2 != null) {
                                }
                                int i3922 = (i7 & 126) | ((i25 >> 6) & 7168);
                                int i4022 = i25 << 9;
                                Modifier modifier522 = modifier3;
                                BasicTextKt.m195BasicTextVhcvRP8(str, modifier522, TextStyle.m757mergedA7vx0o$default(textStyle2, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i30, j8, 16609104), function12, i27, z2, i28, i29, null, composerImpl2, i3922 | (i4022 & 57344) | (i4022 & 458752) | (i4022 & 3670016) | (i4022 & 29360128), 256);
                                modifier4 = modifier522;
                                composerImpl = composerImpl2;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                textStyle3 = textStyle2;
                                fontStyle3 = fontStyle2;
                                fontFamily3 = fontFamily2;
                                i31 = i28;
                                j9 = j6;
                                i32 = i29;
                                z3 = z2;
                                TextAlign textAlign422 = textAlign2;
                                textDecoration3 = textDecoration2;
                                j10 = j5;
                                j11 = j7;
                                i33 = i27;
                                j12 = j8;
                                function13 = function12;
                                fontWeight3 = fontWeight2;
                                textAlign3 = textAlign422;
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    z2 = z;
                    i22 = i6 & 8192;
                    if (i22 != 0) {
                    }
                    i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if (i24 == 0) {
                    }
                    i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i26 == 0) {
                    }
                    if ((i5 & 1572864) == 0) {
                    }
                    if ((i7 & 306783379) != 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                i16 = i15;
                i17 = i6 & 1024;
                if (i17 == 0) {
                }
                i19 = i6 & 2048;
                if (i19 == 0) {
                }
                int i372 = i18;
                i21 = i6 & 4096;
                if (i21 == 0) {
                }
                z2 = z;
                i22 = i6 & 8192;
                if (i22 != 0) {
                }
                i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i24 == 0) {
                }
                i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i26 == 0) {
                }
                if ((i5 & 1572864) == 0) {
                }
                if ((i7 & 306783379) != 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            fontStyle2 = fontStyle;
            i11 = i6 & 32;
            if (i11 != 0) {
            }
            i12 = i6 & 64;
            int i362 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i12 != 0) {
            }
            i13 = i6 & 128;
            if (i13 != 0) {
            }
            i14 = i6 & 256;
            if (i14 != 0) {
            }
            i15 = i6 & 512;
            if (i15 != 0) {
            }
            i16 = i15;
            i17 = i6 & 1024;
            if (i17 == 0) {
            }
            i19 = i6 & 2048;
            if (i19 == 0) {
            }
            int i3722 = i18;
            i21 = i6 & 4096;
            if (i21 == 0) {
            }
            z2 = z;
            i22 = i6 & 8192;
            if (i22 != 0) {
            }
            i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i24 == 0) {
            }
            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i26 == 0) {
            }
            if ((i5 & 1572864) == 0) {
            }
            if ((i7 & 306783379) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i8 = i6 & 4;
        if (i8 == 0) {
        }
        i9 = i6 & 8;
        if (i9 == 0) {
        }
        i10 = i6 & 16;
        int i352 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i10 == 0) {
        }
        fontStyle2 = fontStyle;
        i11 = i6 & 32;
        if (i11 != 0) {
        }
        i12 = i6 & 64;
        int i3622 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i12 != 0) {
        }
        i13 = i6 & 128;
        if (i13 != 0) {
        }
        i14 = i6 & 256;
        if (i14 != 0) {
        }
        i15 = i6 & 512;
        if (i15 != 0) {
        }
        i16 = i15;
        i17 = i6 & 1024;
        if (i17 == 0) {
        }
        i19 = i6 & 2048;
        if (i19 == 0) {
        }
        int i37222 = i18;
        i21 = i6 & 4096;
        if (i21 == 0) {
        }
        z2 = z;
        i22 = i6 & 8192;
        if (i22 != 0) {
        }
        i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i24 == 0) {
        }
        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i26 == 0) {
        }
        if ((i5 & 1572864) == 0) {
        }
        if ((i7 & 306783379) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0321  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0414  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:279:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0135  */
    /* renamed from: Text-IbK3jfQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m318TextIbK3jfQ(final AnnotatedString annotatedString, Modifier modifier, long j, long j2, FontStyle fontStyle, FontWeight fontWeight, FontFamily fontFamily, long j3, TextDecoration textDecoration, TextAlign textAlign, long j4, int i, boolean z, int i2, int i3, Map map, Function1 function1, TextStyle textStyle, Composer composer, final int i4, final int i5, final int i6) {
        int i7;
        int i8;
        int i9;
        long j5;
        int i10;
        FontStyle fontStyle2;
        int i11;
        FontWeight fontWeight2;
        int i12;
        FontFamily fontFamily2;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        boolean z2;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        Modifier modifier2;
        long j6;
        long j7;
        TextDecoration textDecoration2;
        TextAlign textAlign2;
        long j8;
        int i28;
        int i29;
        int i30;
        Map mapEmptyMap;
        Function1 function12;
        int i31;
        TextStyle textStyle2;
        long jM758getColor0d7_KjU;
        Map map2;
        boolean z3;
        int i32;
        Modifier modifier3;
        ComposerImpl composerImpl;
        final TextStyle textStyle3;
        final Map map3;
        final FontStyle fontStyle3;
        final FontFamily fontFamily3;
        final int i33;
        final Function1 function13;
        final boolean z4;
        final int i34;
        final TextDecoration textDecoration3;
        final long j9;
        final long j10;
        final long j11;
        final int i35;
        final FontWeight fontWeight3;
        final TextAlign textAlign3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(2027001676);
        if ((i6 & 1) != 0) {
            i7 = i4 | 6;
        } else if ((i4 & 6) == 0) {
            i7 = (composerImpl2.changed(annotatedString) ? 4 : 2) | i4;
        } else {
            i7 = i4;
        }
        int i36 = i6 & 2;
        if (i36 != 0) {
            i7 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                i7 |= composerImpl2.changed(modifier) ? 32 : 16;
            }
            i8 = i6 & 4;
            if (i8 == 0) {
                i7 |= 384;
            } else if ((i4 & 384) == 0) {
                i7 |= composerImpl2.changed(j) ? 256 : 128;
            }
            i9 = i6 & 8;
            if (i9 == 0) {
                i7 |= 3072;
                j5 = j2;
            } else {
                j5 = j2;
                if ((i4 & 3072) == 0) {
                    i7 |= composerImpl2.changed(j5) ? 2048 : 1024;
                }
            }
            i10 = i6 & 16;
            int i37 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i10 == 0) {
                i7 |= 24576;
            } else {
                if ((i4 & 24576) == 0) {
                    fontStyle2 = fontStyle;
                    i7 |= composerImpl2.changed(fontStyle2) ? 16384 : 8192;
                }
                i11 = i6 & 32;
                if (i11 != 0) {
                    i7 |= 196608;
                    fontWeight2 = fontWeight;
                } else {
                    fontWeight2 = fontWeight;
                    if ((i4 & 196608) == 0) {
                        i7 |= composerImpl2.changed(fontWeight2) ? 131072 : 65536;
                    }
                }
                i12 = i6 & 64;
                int i38 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i12 != 0) {
                    i7 |= 1572864;
                    fontFamily2 = fontFamily;
                } else {
                    fontFamily2 = fontFamily;
                    if ((i4 & 1572864) == 0) {
                        i7 |= composerImpl2.changed(fontFamily2) ? 1048576 : 524288;
                    }
                }
                i13 = i6 & 128;
                int i39 = 4194304;
                if (i13 != 0) {
                    i7 |= 12582912;
                } else if ((i4 & 12582912) == 0) {
                    i7 |= composerImpl2.changed(j3) ? 8388608 : 4194304;
                }
                i14 = i6 & 256;
                if (i14 != 0) {
                    i7 |= 100663296;
                } else if ((i4 & 100663296) == 0) {
                    i7 |= composerImpl2.changed(textDecoration) ? 67108864 : 33554432;
                }
                i15 = i6 & 512;
                if (i15 != 0) {
                    i7 |= 805306368;
                } else {
                    if ((i4 & 805306368) == 0) {
                        i16 = i15;
                        i7 |= composerImpl2.changed(textAlign) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    i17 = i6 & 1024;
                    if (i17 == 0) {
                        i18 = i5 | 6;
                    } else if ((i5 & 6) == 0) {
                        i18 = i5 | (composerImpl2.changed(j4) ? 4 : 2);
                    } else {
                        i18 = i5;
                    }
                    i19 = i6 & 2048;
                    if (i19 == 0) {
                        i18 |= 48;
                        i20 = i19;
                    } else if ((i5 & 48) == 0) {
                        i20 = i19;
                        i18 |= composerImpl2.changed(i) ? 32 : 16;
                    } else {
                        i20 = i19;
                    }
                    int i40 = i18;
                    i21 = i6 & 4096;
                    if (i21 == 0) {
                        i40 |= 384;
                    } else {
                        if ((i5 & 384) == 0) {
                            z2 = z;
                            i40 |= composerImpl2.changed(z2) ? 256 : 128;
                        }
                        i22 = i6 & 8192;
                        if (i22 != 0) {
                            i23 = i40 | 3072;
                        } else {
                            int i41 = i40;
                            if ((i5 & 3072) == 0) {
                                i23 = i41 | (composerImpl2.changed(i2) ? 2048 : 1024);
                            } else {
                                i23 = i41;
                            }
                        }
                        i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        if (i24 == 0) {
                            i25 = i23;
                            if ((i5 & 24576) == 0) {
                                if (!composerImpl2.changed(i3)) {
                                    i37 = 8192;
                                }
                                i25 |= i37;
                            }
                            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                            if (i26 == 0) {
                                i25 |= 196608;
                            } else if ((i5 & 196608) == 0) {
                                i25 |= composerImpl2.changedInstance(map) ? 131072 : 65536;
                            }
                            i27 = i6 & 65536;
                            if (i27 == 0) {
                                i25 |= 1572864;
                            } else if ((i5 & 1572864) == 0) {
                                if (composerImpl2.changedInstance(function1)) {
                                    i38 = 1048576;
                                }
                                i25 |= i38;
                            }
                            if ((i5 & 12582912) == 0) {
                                if ((i6 & 131072) == 0 && composerImpl2.changed(textStyle)) {
                                    i39 = 8388608;
                                }
                                i25 |= i39;
                            }
                            if ((i7 & 306783379) != 306783378 && (i25 & 4793491) == 4793490 && composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                j6 = j;
                                textDecoration3 = textDecoration;
                                i35 = i;
                                i33 = i2;
                                i34 = i3;
                                map3 = map;
                                function13 = function1;
                                textStyle3 = textStyle;
                                composerImpl = composerImpl2;
                                z4 = z2;
                                fontStyle3 = fontStyle2;
                                j9 = j5;
                                fontWeight3 = fontWeight2;
                                fontFamily3 = fontFamily2;
                                modifier3 = modifier;
                                j10 = j3;
                                textAlign3 = textAlign;
                                j11 = j4;
                            } else {
                                composerImpl2.startDefaults();
                                if ((i4 & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                    modifier2 = i36 == 0 ? Modifier.Companion : modifier;
                                    if (i8 == 0) {
                                        Color.Companion.getClass();
                                        j6 = Color.Unspecified;
                                    } else {
                                        j6 = j;
                                    }
                                    if (i9 != 0) {
                                        TextUnit.Companion.getClass();
                                        j5 = TextUnit.Unspecified;
                                    }
                                    if (i10 != 0) {
                                        fontStyle2 = null;
                                    }
                                    if (i11 != 0) {
                                        fontWeight2 = null;
                                    }
                                    if (i12 != 0) {
                                        fontFamily2 = null;
                                    }
                                    if (i13 == 0) {
                                        TextUnit.Companion.getClass();
                                        j7 = TextUnit.Unspecified;
                                    } else {
                                        j7 = j3;
                                    }
                                    textDecoration2 = i14 == 0 ? null : textDecoration;
                                    textAlign2 = i16 == 0 ? textAlign : null;
                                    if (i17 == 0) {
                                        TextUnit.Companion.getClass();
                                        j8 = TextUnit.Unspecified;
                                    } else {
                                        j8 = j4;
                                    }
                                    if (i20 == 0) {
                                        TextOverflow.Companion.getClass();
                                        i28 = TextOverflow.Clip;
                                    } else {
                                        i28 = i;
                                    }
                                    if (i21 != 0) {
                                        z2 = true;
                                    }
                                    i29 = i22 == 0 ? Integer.MAX_VALUE : i2;
                                    i30 = i24 == 0 ? i3 : 1;
                                    mapEmptyMap = i26 == 0 ? MapsKt__MapsKt.emptyMap() : map;
                                    function12 = i27 == 0 ? new Function1() { // from class: androidx.compose.material3.TextKt$Text$4
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                            return Unit.INSTANCE;
                                        }
                                    } : function1;
                                    Modifier modifier4 = modifier2;
                                    if ((i6 & 131072) == 0) {
                                        i31 = i25 & (-29360129);
                                        textStyle2 = (TextStyle) composerImpl2.consume(LocalTextStyle);
                                        modifier2 = modifier4;
                                    }
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.Text (Text.kt:254)");
                                    }
                                    Modifier modifier5 = modifier2;
                                    composerImpl2.startReplaceGroup(-1827697677);
                                    if (j6 != 16) {
                                        map2 = mapEmptyMap;
                                        z3 = z2;
                                        jM758getColor0d7_KjU = j6;
                                    } else {
                                        composerImpl2.startReplaceGroup(-1827696904);
                                        jM758getColor0d7_KjU = textStyle2.m758getColor0d7_KjU();
                                        if (jM758getColor0d7_KjU != 16) {
                                            map2 = mapEmptyMap;
                                            z3 = z2;
                                        } else {
                                            map2 = mapEmptyMap;
                                            z3 = z2;
                                            jM758getColor0d7_KjU = ((Color) composerImpl2.consume(ContentColorKt.LocalContentColor)).value;
                                        }
                                        composerImpl2.end(false);
                                    }
                                    composerImpl2.end(false);
                                    if (textAlign2 != null) {
                                        i32 = textAlign2.value;
                                    } else {
                                        TextAlign.Companion.getClass();
                                        i32 = TextAlign.Unspecified;
                                    }
                                    TextStyle textStyle4 = textStyle2;
                                    int i42 = (i7 & 126) | ((i31 >> 9) & 7168);
                                    int i43 = i31 << 9;
                                    boolean z5 = z3;
                                    Map map4 = map2;
                                    Function1 function14 = function12;
                                    BasicTextKt.m193BasicTextRWo7tUw(annotatedString, modifier5, TextStyle.m757mergedA7vx0o$default(textStyle4, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i32, j8, 16609104), function14, i28, z5, i29, i30, map4, (ColorProducer) null, composerImpl2, i42 | (i43 & 57344) | (i43 & 458752) | (i43 & 3670016) | (i43 & 29360128) | (i43 & 234881024), 512);
                                    modifier3 = modifier5;
                                    composerImpl = composerImpl2;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    textStyle3 = textStyle4;
                                    map3 = map4;
                                    fontStyle3 = fontStyle2;
                                    fontFamily3 = fontFamily2;
                                    i33 = i29;
                                    function13 = function12;
                                    z4 = z5;
                                    i34 = i30;
                                    textDecoration3 = textDecoration2;
                                    j9 = j5;
                                    j10 = j7;
                                    j11 = j8;
                                    i35 = i28;
                                    fontWeight3 = fontWeight2;
                                    textAlign3 = textAlign2;
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i6 & 131072) != 0) {
                                        i25 &= -29360129;
                                    }
                                    modifier2 = modifier;
                                    j6 = j;
                                    j7 = j3;
                                    textDecoration2 = textDecoration;
                                    textAlign2 = textAlign;
                                    j8 = j4;
                                    i28 = i;
                                    i29 = i2;
                                    i30 = i3;
                                    mapEmptyMap = map;
                                    function12 = function1;
                                }
                                i31 = i25;
                                textStyle2 = textStyle;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                Modifier modifier52 = modifier2;
                                composerImpl2.startReplaceGroup(-1827697677);
                                if (j6 != 16) {
                                }
                                composerImpl2.end(false);
                                if (textAlign2 != null) {
                                }
                                TextStyle textStyle42 = textStyle2;
                                int i422 = (i7 & 126) | ((i31 >> 9) & 7168);
                                int i432 = i31 << 9;
                                boolean z52 = z3;
                                Map map42 = map2;
                                Function1 function142 = function12;
                                BasicTextKt.m193BasicTextRWo7tUw(annotatedString, modifier52, TextStyle.m757mergedA7vx0o$default(textStyle42, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i32, j8, 16609104), function142, i28, z52, i29, i30, map42, (ColorProducer) null, composerImpl2, i422 | (i432 & 57344) | (i432 & 458752) | (i432 & 3670016) | (i432 & 29360128) | (i432 & 234881024), 512);
                                modifier3 = modifier52;
                                composerImpl = composerImpl2;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                textStyle3 = textStyle42;
                                map3 = map42;
                                fontStyle3 = fontStyle2;
                                fontFamily3 = fontFamily2;
                                i33 = i29;
                                function13 = function12;
                                z4 = z52;
                                i34 = i30;
                                textDecoration3 = textDecoration2;
                                j9 = j5;
                                j10 = j7;
                                j11 = j8;
                                i35 = i28;
                                fontWeight3 = fontWeight2;
                                textAlign3 = textAlign2;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final long j12 = j6;
                                final Modifier modifier6 = modifier3;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextKt$Text$5
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        TextKt.m318TextIbK3jfQ(annotatedString, modifier6, j12, j9, fontStyle3, fontWeight3, fontFamily3, j10, textDecoration3, textAlign3, j11, i35, z4, i33, i34, map3, function13, textStyle3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i25 = i23 | 24576;
                        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i26 == 0) {
                        }
                        i27 = i6 & 65536;
                        if (i27 == 0) {
                        }
                        if ((i5 & 12582912) == 0) {
                        }
                        if ((i7 & 306783379) != 306783378) {
                            composerImpl2.startDefaults();
                            if ((i4 & 1) != 0) {
                                if (i36 == 0) {
                                }
                                if (i8 == 0) {
                                }
                                if (i9 != 0) {
                                }
                                if (i10 != 0) {
                                }
                                if (i11 != 0) {
                                }
                                if (i12 != 0) {
                                }
                                if (i13 == 0) {
                                }
                                if (i14 == 0) {
                                }
                                if (i16 == 0) {
                                }
                                if (i17 == 0) {
                                }
                                if (i20 == 0) {
                                }
                                if (i21 != 0) {
                                }
                                if (i22 == 0) {
                                }
                                if (i24 == 0) {
                                }
                                if (i26 == 0) {
                                }
                                if (i27 == 0) {
                                }
                                Modifier modifier42 = modifier2;
                                if ((i6 & 131072) == 0) {
                                    i31 = i25;
                                    textStyle2 = textStyle;
                                }
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                Modifier modifier522 = modifier2;
                                composerImpl2.startReplaceGroup(-1827697677);
                                if (j6 != 16) {
                                }
                                composerImpl2.end(false);
                                if (textAlign2 != null) {
                                }
                                TextStyle textStyle422 = textStyle2;
                                int i4222 = (i7 & 126) | ((i31 >> 9) & 7168);
                                int i4322 = i31 << 9;
                                boolean z522 = z3;
                                Map map422 = map2;
                                Function1 function1422 = function12;
                                BasicTextKt.m193BasicTextRWo7tUw(annotatedString, modifier522, TextStyle.m757mergedA7vx0o$default(textStyle422, jM758getColor0d7_KjU, j5, fontWeight2, fontStyle2, fontFamily2, j7, textDecoration2, i32, j8, 16609104), function1422, i28, z522, i29, i30, map422, (ColorProducer) null, composerImpl2, i4222 | (i4322 & 57344) | (i4322 & 458752) | (i4322 & 3670016) | (i4322 & 29360128) | (i4322 & 234881024), 512);
                                modifier3 = modifier522;
                                composerImpl = composerImpl2;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                textStyle3 = textStyle422;
                                map3 = map422;
                                fontStyle3 = fontStyle2;
                                fontFamily3 = fontFamily2;
                                i33 = i29;
                                function13 = function12;
                                z4 = z522;
                                i34 = i30;
                                textDecoration3 = textDecoration2;
                                j9 = j5;
                                j10 = j7;
                                j11 = j8;
                                i35 = i28;
                                fontWeight3 = fontWeight2;
                                textAlign3 = textAlign2;
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    z2 = z;
                    i22 = i6 & 8192;
                    if (i22 != 0) {
                    }
                    i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if (i24 == 0) {
                    }
                    i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i26 == 0) {
                    }
                    i27 = i6 & 65536;
                    if (i27 == 0) {
                    }
                    if ((i5 & 12582912) == 0) {
                    }
                    if ((i7 & 306783379) != 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                i16 = i15;
                i17 = i6 & 1024;
                if (i17 == 0) {
                }
                i19 = i6 & 2048;
                if (i19 == 0) {
                }
                int i402 = i18;
                i21 = i6 & 4096;
                if (i21 == 0) {
                }
                z2 = z;
                i22 = i6 & 8192;
                if (i22 != 0) {
                }
                i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i24 == 0) {
                }
                i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i26 == 0) {
                }
                i27 = i6 & 65536;
                if (i27 == 0) {
                }
                if ((i5 & 12582912) == 0) {
                }
                if ((i7 & 306783379) != 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            fontStyle2 = fontStyle;
            i11 = i6 & 32;
            if (i11 != 0) {
            }
            i12 = i6 & 64;
            int i382 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i12 != 0) {
            }
            i13 = i6 & 128;
            int i392 = 4194304;
            if (i13 != 0) {
            }
            i14 = i6 & 256;
            if (i14 != 0) {
            }
            i15 = i6 & 512;
            if (i15 != 0) {
            }
            i16 = i15;
            i17 = i6 & 1024;
            if (i17 == 0) {
            }
            i19 = i6 & 2048;
            if (i19 == 0) {
            }
            int i4022 = i18;
            i21 = i6 & 4096;
            if (i21 == 0) {
            }
            z2 = z;
            i22 = i6 & 8192;
            if (i22 != 0) {
            }
            i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i24 == 0) {
            }
            i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i26 == 0) {
            }
            i27 = i6 & 65536;
            if (i27 == 0) {
            }
            if ((i5 & 12582912) == 0) {
            }
            if ((i7 & 306783379) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i8 = i6 & 4;
        if (i8 == 0) {
        }
        i9 = i6 & 8;
        if (i9 == 0) {
        }
        i10 = i6 & 16;
        int i372 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i10 == 0) {
        }
        fontStyle2 = fontStyle;
        i11 = i6 & 32;
        if (i11 != 0) {
        }
        i12 = i6 & 64;
        int i3822 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i12 != 0) {
        }
        i13 = i6 & 128;
        int i3922 = 4194304;
        if (i13 != 0) {
        }
        i14 = i6 & 256;
        if (i14 != 0) {
        }
        i15 = i6 & 512;
        if (i15 != 0) {
        }
        i16 = i15;
        i17 = i6 & 1024;
        if (i17 == 0) {
        }
        i19 = i6 & 2048;
        if (i19 == 0) {
        }
        int i40222 = i18;
        i21 = i6 & 4096;
        if (i21 == 0) {
        }
        z2 = z;
        i22 = i6 & 8192;
        if (i22 != 0) {
        }
        i24 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i24 == 0) {
        }
        i26 = i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i26 == 0) {
        }
        i27 = i6 & 65536;
        if (i27 == 0) {
        }
        if ((i5 & 12582912) == 0) {
        }
        if ((i7 & 306783379) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
