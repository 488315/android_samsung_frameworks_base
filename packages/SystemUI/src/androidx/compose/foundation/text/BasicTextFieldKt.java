package androidx.compose.foundation.text;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class BasicTextFieldKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        float f = 40;
        Dp.Companion companion = Dp.Companion;
        DpKt.m840DpSizeYgX7TsA(f, f);
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x03a0  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0422  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0425  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x044a  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x046e  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x04d7  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:301:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void BasicTextField(final String str, final Function1 function1, Modifier modifier, boolean z, boolean z2, TextStyle textStyle, KeyboardOptions keyboardOptions, KeyboardActions keyboardActions, boolean z3, int i, int i2, VisualTransformation visualTransformation, Function1 function12, MutableInteractionSource mutableInteractionSource, Brush brush, Function3 function3, Composer composer, final int i3, final int i4, final int i5) {
        int i6;
        Modifier modifier2;
        int i7;
        boolean z4;
        int i8;
        boolean z5;
        int i9;
        TextStyle textStyle2;
        int i10;
        KeyboardOptions keyboardOptions2;
        int i11;
        final KeyboardActions keyboardActions2;
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
        ComposerImpl composerImpl;
        final boolean z6;
        final Brush brush2;
        final Function3 function32;
        final KeyboardOptions keyboardOptions3;
        final boolean z7;
        final boolean z8;
        final TextStyle textStyle3;
        final Modifier modifier3;
        final int i26;
        final int i27;
        final VisualTransformation visualTransformation2;
        final Function1 function13;
        final MutableInteractionSource mutableInteractionSource2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        KeyboardOptions keyboardOptions4;
        KeyboardActions keyboardActions3;
        int i28;
        VisualTransformation visualTransformation3;
        KeyboardOptions keyboardOptions5;
        Brush solidColor;
        KeyboardOptions keyboardOptions6;
        int i29;
        int i30;
        boolean z9;
        VisualTransformation visualTransformation4;
        Function1 function14;
        Brush brush3;
        boolean z10;
        boolean z11;
        TextStyle textStyle4;
        MutableInteractionSource mutableInteractionSource3;
        int i31;
        KeyboardActions keyboardActions4;
        Modifier modifier4;
        Function3 function33;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        final MutableState mutableState;
        final TextFieldValue textFieldValue;
        boolean zChanged;
        Object objRememberedValue2;
        boolean z12;
        Object objRememberedValue3;
        final MutableState mutableState2;
        KeyboardCapitalization keyboardCapitalizationM776boximpl;
        KeyboardType keyboardTypeM778boximpl;
        LocaleList localeList;
        boolean zChanged2;
        Object objRememberedValue4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(945255183);
        if ((i5 & 1) != 0) {
            i6 = i3 | 6;
        } else if ((i3 & 6) == 0) {
            i6 = (composerImpl2.changed(str) ? 4 : 2) | i3;
        } else {
            i6 = i3;
        }
        if ((i5 & 2) != 0) {
            i6 |= 48;
        } else if ((i3 & 48) == 0) {
            i6 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        int i32 = i5 & 4;
        if (i32 != 0) {
            i6 |= 384;
        } else {
            if ((i3 & 384) == 0) {
                modifier2 = modifier;
                i6 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            i7 = i5 & 8;
            if (i7 == 0) {
                i6 |= 3072;
            } else {
                if ((i3 & 3072) == 0) {
                    z4 = z;
                    i6 |= composerImpl2.changed(z4) ? 2048 : 1024;
                }
                i8 = i5 & 16;
                int i33 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i8 != 0) {
                    i6 |= 24576;
                } else {
                    if ((i3 & 24576) == 0) {
                        z5 = z2;
                        i6 |= composerImpl2.changed(z5) ? 16384 : 8192;
                    }
                    i9 = i5 & 32;
                    if (i9 == 0) {
                        i6 |= 196608;
                        textStyle2 = textStyle;
                    } else {
                        textStyle2 = textStyle;
                        if ((i3 & 196608) == 0) {
                            i6 |= composerImpl2.changed(textStyle2) ? 131072 : 65536;
                        }
                    }
                    i10 = i5 & 64;
                    if (i10 == 0) {
                        i6 |= 1572864;
                        keyboardOptions2 = keyboardOptions;
                    } else {
                        keyboardOptions2 = keyboardOptions;
                        if ((i3 & 1572864) == 0) {
                            i6 |= composerImpl2.changed(keyboardOptions2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                    }
                    i11 = i5 & 128;
                    if (i11 == 0) {
                        i6 |= 12582912;
                        keyboardActions2 = keyboardActions;
                    } else {
                        keyboardActions2 = keyboardActions;
                        if ((i3 & 12582912) == 0) {
                            i6 |= composerImpl2.changed(keyboardActions2) ? 8388608 : 4194304;
                        }
                    }
                    i12 = i5 & 256;
                    if (i12 == 0) {
                        i6 |= 100663296;
                    } else {
                        if ((i3 & 100663296) == 0) {
                            i13 = i12;
                            i6 |= composerImpl2.changed(z3) ? 67108864 : 33554432;
                        }
                        if ((i3 & 805306368) == 0) {
                            i6 |= ((i5 & 512) == 0 && composerImpl2.changed(i)) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        }
                        i14 = i5 & 1024;
                        if (i14 != 0) {
                            i16 = i4 | 6;
                            i15 = i14;
                        } else if ((i4 & 6) == 0) {
                            i15 = i14;
                            i16 = i4 | (composerImpl2.changed(i2) ? 4 : 2);
                        } else {
                            i15 = i14;
                            i16 = i4;
                        }
                        i17 = i5 & 2048;
                        if (i17 != 0) {
                            i16 |= 48;
                            i18 = i17;
                        } else if ((i4 & 48) == 0) {
                            i18 = i17;
                            i16 |= composerImpl2.changed(visualTransformation) ? 32 : 16;
                        } else {
                            i18 = i17;
                        }
                        int i34 = i16;
                        i19 = i5 & 4096;
                        if (i19 != 0) {
                            i20 = i34 | 384;
                        } else if ((i4 & 384) == 0) {
                            i20 = i34 | (composerImpl2.changedInstance(function12) ? 256 : 128);
                        } else {
                            i20 = i34;
                        }
                        i21 = i5 & 8192;
                        if (i21 != 0) {
                            i22 = i20 | 3072;
                        } else {
                            int i35 = i20;
                            if ((i4 & 3072) == 0) {
                                i22 = i35 | (composerImpl2.changed(mutableInteractionSource) ? 2048 : 1024);
                            } else {
                                i22 = i35;
                            }
                        }
                        i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        if (i23 == 0) {
                            i24 = i22;
                            if ((i4 & 24576) == 0) {
                                if (!composerImpl2.changed(brush)) {
                                    i33 = 8192;
                                }
                                i24 |= i33;
                            }
                            i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                            if (i25 == 0) {
                                i24 |= 196608;
                            } else if ((i4 & 196608) == 0) {
                                i24 |= composerImpl2.changedInstance(function3) ? 131072 : 65536;
                            }
                            if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
                                composerImpl = composerImpl2;
                                composerImpl.skipToGroupEnd();
                                z6 = z3;
                                brush2 = brush;
                                function32 = function3;
                                keyboardOptions3 = keyboardOptions2;
                                z7 = z4;
                                z8 = z5;
                                textStyle3 = textStyle2;
                                modifier3 = modifier2;
                                i26 = i;
                                i27 = i2;
                                visualTransformation2 = visualTransformation;
                                function13 = function12;
                                mutableInteractionSource2 = mutableInteractionSource;
                            } else {
                                composerImpl2.startDefaults();
                                if ((i3 & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                                    if (i32 != 0) {
                                        modifier2 = Modifier.Companion;
                                    }
                                    if (i7 != 0) {
                                        z4 = true;
                                    }
                                    if (i8 != 0) {
                                        z5 = false;
                                    }
                                    if (i9 != 0) {
                                        TextStyle.Companion.getClass();
                                        textStyle2 = TextStyle.Default;
                                    }
                                    if (i10 != 0) {
                                        KeyboardOptions.Companion.getClass();
                                        keyboardOptions4 = KeyboardOptions.Default;
                                    } else {
                                        keyboardOptions4 = keyboardOptions2;
                                    }
                                    if (i11 != 0) {
                                        KeyboardActions.Companion.getClass();
                                        keyboardActions3 = KeyboardActions.Default;
                                    } else {
                                        keyboardActions3 = keyboardActions2;
                                    }
                                    boolean z13 = i13 != 0 ? false : z3;
                                    if ((i5 & 512) != 0) {
                                        i28 = z13 ? 1 : Integer.MAX_VALUE;
                                        i6 &= -1879048193;
                                    } else {
                                        i28 = i;
                                    }
                                    int i36 = i15 != 0 ? 1 : i2;
                                    if (i18 != 0) {
                                        VisualTransformation.Companion.getClass();
                                        visualTransformation3 = VisualTransformation.Companion.None;
                                    } else {
                                        visualTransformation3 = visualTransformation;
                                    }
                                    Function1 function15 = i19 != 0 ? new Function1() { // from class: androidx.compose.foundation.text.BasicTextFieldKt.BasicTextField.6
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                            return Unit.INSTANCE;
                                        }
                                    } : function12;
                                    MutableInteractionSource mutableInteractionSource4 = i21 != 0 ? null : mutableInteractionSource;
                                    if (i23 != 0) {
                                        Color.Companion.getClass();
                                        keyboardOptions5 = keyboardOptions4;
                                        solidColor = new SolidColor(Color.Black, null);
                                    } else {
                                        keyboardOptions5 = keyboardOptions4;
                                        solidColor = brush;
                                    }
                                    if (i25 != 0) {
                                        ComposableSingletons$BasicTextFieldKt.INSTANCE.getClass();
                                        i29 = i28;
                                        i30 = i36;
                                        z9 = z5;
                                        visualTransformation4 = visualTransformation3;
                                        function14 = function15;
                                        brush3 = solidColor;
                                        z10 = z13;
                                        z11 = z4;
                                        textStyle4 = textStyle2;
                                        mutableInteractionSource3 = mutableInteractionSource4;
                                        i31 = i24;
                                        keyboardActions4 = keyboardActions3;
                                        modifier4 = modifier2;
                                        function33 = ComposableSingletons$BasicTextFieldKt.f1lambda1;
                                        keyboardOptions6 = keyboardOptions5;
                                        composerImpl2.endDefaults();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.BasicTextField (BasicTextField.kt:702)");
                                        }
                                        objRememberedValue = composerImpl2.rememberedValue();
                                        Composer.Companion.getClass();
                                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                                        if (objRememberedValue == composer$Companion$Empty$1) {
                                            objRememberedValue = SnapshotStateKt.mutableStateOf$default(new TextFieldValue(str, 0L, (TextRange) null, 6, (DefaultConstructorMarker) null));
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        boolean z14 = z10;
                                        mutableState = (MutableState) objRememberedValue;
                                        int i37 = i29;
                                        TextFieldValue textFieldValue2 = (TextFieldValue) mutableState.getValue();
                                        Modifier modifier5 = modifier4;
                                        TextStyle textStyle5 = textStyle4;
                                        int i38 = i30;
                                        textFieldValue = new TextFieldValue(new AnnotatedString(str, null, 2, null), textFieldValue2.selection, textFieldValue2.composition, (DefaultConstructorMarker) null);
                                        zChanged = composerImpl2.changed(textFieldValue);
                                        objRememberedValue2 = composerImpl2.rememberedValue();
                                        if (!zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
                                            objRememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextFieldKt$BasicTextField$7$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    long j = textFieldValue.selection;
                                                    MutableState<TextFieldValue> mutableState3 = mutableState;
                                                    int i39 = BasicTextFieldKt.$r8$clinit;
                                                    if (!TextRange.m748equalsimpl0(j, ((TextFieldValue) mutableState3.getValue()).selection) || !Intrinsics.areEqual(textFieldValue.composition, ((TextFieldValue) mutableState.getValue()).composition)) {
                                                        mutableState.setValue(textFieldValue);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue2);
                                        }
                                        EffectsKt.SideEffect((Function0) objRememberedValue2, composerImpl2);
                                        z12 = (i6 & 14) != 4;
                                        objRememberedValue3 = composerImpl2.rememberedValue();
                                        if (!z12 || objRememberedValue3 == composer$Companion$Empty$1) {
                                            objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(str);
                                            composerImpl2.updateRememberedValue(objRememberedValue3);
                                        }
                                        mutableState2 = (MutableState) objRememberedValue3;
                                        keyboardOptions6.getClass();
                                        keyboardCapitalizationM776boximpl = KeyboardCapitalization.m776boximpl(keyboardOptions6.capitalization);
                                        KeyboardCapitalization.Companion.getClass();
                                        if (keyboardCapitalizationM776boximpl.value == KeyboardCapitalization.Unspecified) {
                                            keyboardCapitalizationM776boximpl = null;
                                        }
                                        int i39 = keyboardCapitalizationM776boximpl == null ? keyboardCapitalizationM776boximpl.value : 0;
                                        Boolean bool = keyboardOptions6.autoCorrectEnabled;
                                        boolean zBooleanValue = bool == null ? bool.booleanValue() : true;
                                        keyboardTypeM778boximpl = KeyboardType.m778boximpl(keyboardOptions6.keyboardType);
                                        KeyboardType.Companion.getClass();
                                        int i40 = i39;
                                        if (keyboardTypeM778boximpl.value == 0) {
                                            keyboardTypeM778boximpl = null;
                                        }
                                        int i41 = keyboardTypeM778boximpl == null ? keyboardTypeM778boximpl.value : KeyboardType.Text;
                                        ImeAction imeActionM774boximpl = ImeAction.m774boximpl(keyboardOptions6.imeAction);
                                        ImeAction.Companion.getClass();
                                        int i42 = i41;
                                        ImeAction imeAction = imeActionM774boximpl.value != ImeAction.Unspecified ? null : imeActionM774boximpl;
                                        int i43 = imeAction == null ? imeAction.value : ImeAction.Default;
                                        localeList = keyboardOptions6.hintLocales;
                                        if (localeList == null) {
                                            LocaleList.Companion.getClass();
                                            localeList = LocaleList.Empty;
                                        }
                                        ImeOptions imeOptions = new ImeOptions(z14, i40, zBooleanValue, i42, i43, keyboardOptions6.platformImeOptions, localeList, (DefaultConstructorMarker) null);
                                        int i44 = i31;
                                        boolean z15 = !z14;
                                        int i45 = !z14 ? 1 : i38;
                                        int i46 = !z14 ? 1 : i37;
                                        KeyboardOptions keyboardOptions7 = keyboardOptions6;
                                        zChanged2 = composerImpl2.changed(mutableState2) | ((i6 & 112) == 32);
                                        objRememberedValue4 = composerImpl2.rememberedValue();
                                        if (!zChanged2 || objRememberedValue4 == composer$Companion$Empty$1) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.compose.foundation.text.BasicTextFieldKt$BasicTextField$8$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    TextFieldValue textFieldValue3 = (TextFieldValue) obj;
                                                    MutableState<TextFieldValue> mutableState3 = mutableState;
                                                    int i47 = BasicTextFieldKt.$r8$clinit;
                                                    mutableState3.setValue(textFieldValue3);
                                                    boolean zAreEqual = Intrinsics.areEqual((String) mutableState2.getValue(), textFieldValue3.annotatedString.text);
                                                    MutableState<String> mutableState4 = mutableState2;
                                                    AnnotatedString annotatedString = textFieldValue3.annotatedString;
                                                    mutableState4.setValue(annotatedString.text);
                                                    if (!zAreEqual) {
                                                        function1.mo781invoke(annotatedString.text);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue4);
                                        }
                                        int i47 = i44 << 9;
                                        composerImpl = composerImpl2;
                                        CoreTextFieldKt.CoreTextField(textFieldValue, (Function1) objRememberedValue4, modifier5, textStyle5, visualTransformation4, function14, mutableInteractionSource3, brush3, z15, i46, i45, imeOptions, keyboardActions4, z11, z9, function33, null, composerImpl, (i6 & 896) | ((i6 >> 6) & 7168) | (57344 & i47) | (458752 & i47) | (3670016 & i47) | (i47 & 29360128), ((i6 >> 15) & 896) | (i6 & 7168) | (i6 & 57344) | (i44 & 458752), 65536);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        modifier3 = modifier5;
                                        textStyle3 = textStyle5;
                                        function13 = function14;
                                        mutableInteractionSource2 = mutableInteractionSource3;
                                        brush2 = brush3;
                                        keyboardActions2 = keyboardActions4;
                                        z7 = z11;
                                        z8 = z9;
                                        function32 = function33;
                                        keyboardOptions3 = keyboardOptions7;
                                        i26 = i37;
                                        i27 = i38;
                                        visualTransformation2 = visualTransformation4;
                                        z6 = z14;
                                    } else {
                                        keyboardOptions6 = keyboardOptions5;
                                        i29 = i28;
                                        i30 = i36;
                                        z9 = z5;
                                        visualTransformation4 = visualTransformation3;
                                        function14 = function15;
                                        brush3 = solidColor;
                                        z10 = z13;
                                        z11 = z4;
                                        textStyle4 = textStyle2;
                                        mutableInteractionSource3 = mutableInteractionSource4;
                                        i31 = i24;
                                        keyboardActions4 = keyboardActions3;
                                        modifier4 = modifier2;
                                    }
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i5 & 512) != 0) {
                                        i6 &= -1879048193;
                                    }
                                    int i48 = i24;
                                    keyboardActions4 = keyboardActions2;
                                    textStyle4 = textStyle2;
                                    i31 = i48;
                                    z10 = z3;
                                    i29 = i;
                                    i30 = i2;
                                    visualTransformation4 = visualTransformation;
                                    keyboardOptions6 = keyboardOptions2;
                                    z11 = z4;
                                    z9 = z5;
                                    modifier4 = modifier2;
                                    function14 = function12;
                                    mutableInteractionSource3 = mutableInteractionSource;
                                    brush3 = brush;
                                }
                                function33 = function3;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                objRememberedValue = composerImpl2.rememberedValue();
                                Composer.Companion.getClass();
                                composer$Companion$Empty$1 = Composer.Companion.Empty;
                                if (objRememberedValue == composer$Companion$Empty$1) {
                                }
                                boolean z142 = z10;
                                mutableState = (MutableState) objRememberedValue;
                                int i372 = i29;
                                TextFieldValue textFieldValue22 = (TextFieldValue) mutableState.getValue();
                                Modifier modifier52 = modifier4;
                                TextStyle textStyle52 = textStyle4;
                                int i382 = i30;
                                textFieldValue = new TextFieldValue(new AnnotatedString(str, null, 2, null), textFieldValue22.selection, textFieldValue22.composition, (DefaultConstructorMarker) null);
                                zChanged = composerImpl2.changed(textFieldValue);
                                objRememberedValue2 = composerImpl2.rememberedValue();
                                if (!zChanged) {
                                    objRememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextFieldKt$BasicTextField$7$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            long j = textFieldValue.selection;
                                            MutableState<TextFieldValue> mutableState3 = mutableState;
                                            int i392 = BasicTextFieldKt.$r8$clinit;
                                            if (!TextRange.m748equalsimpl0(j, ((TextFieldValue) mutableState3.getValue()).selection) || !Intrinsics.areEqual(textFieldValue.composition, ((TextFieldValue) mutableState.getValue()).composition)) {
                                                mutableState.setValue(textFieldValue);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(objRememberedValue2);
                                    EffectsKt.SideEffect((Function0) objRememberedValue2, composerImpl2);
                                    if ((i6 & 14) != 4) {
                                    }
                                    objRememberedValue3 = composerImpl2.rememberedValue();
                                    if (!z12) {
                                        objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(str);
                                        composerImpl2.updateRememberedValue(objRememberedValue3);
                                        mutableState2 = (MutableState) objRememberedValue3;
                                        keyboardOptions6.getClass();
                                        keyboardCapitalizationM776boximpl = KeyboardCapitalization.m776boximpl(keyboardOptions6.capitalization);
                                        KeyboardCapitalization.Companion.getClass();
                                        if (keyboardCapitalizationM776boximpl.value == KeyboardCapitalization.Unspecified) {
                                        }
                                        if (keyboardCapitalizationM776boximpl == null) {
                                        }
                                        Boolean bool2 = keyboardOptions6.autoCorrectEnabled;
                                        if (bool2 == null) {
                                        }
                                        keyboardTypeM778boximpl = KeyboardType.m778boximpl(keyboardOptions6.keyboardType);
                                        KeyboardType.Companion.getClass();
                                        int i402 = i39;
                                        if (keyboardTypeM778boximpl.value == 0) {
                                        }
                                        if (keyboardTypeM778boximpl == null) {
                                        }
                                        ImeAction imeActionM774boximpl2 = ImeAction.m774boximpl(keyboardOptions6.imeAction);
                                        ImeAction.Companion.getClass();
                                        int i422 = i41;
                                        if (imeActionM774boximpl2.value != ImeAction.Unspecified) {
                                        }
                                        if (imeAction == null) {
                                        }
                                        localeList = keyboardOptions6.hintLocales;
                                        if (localeList == null) {
                                        }
                                        ImeOptions imeOptions2 = new ImeOptions(z142, i402, zBooleanValue, i422, i43, keyboardOptions6.platformImeOptions, localeList, (DefaultConstructorMarker) null);
                                        int i442 = i31;
                                        boolean z152 = !z142;
                                        if (!z142) {
                                        }
                                        if (!z142) {
                                        }
                                        KeyboardOptions keyboardOptions72 = keyboardOptions6;
                                        zChanged2 = composerImpl2.changed(mutableState2) | ((i6 & 112) == 32);
                                        objRememberedValue4 = composerImpl2.rememberedValue();
                                        if (!zChanged2) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.compose.foundation.text.BasicTextFieldKt$BasicTextField$8$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    TextFieldValue textFieldValue3 = (TextFieldValue) obj;
                                                    MutableState<TextFieldValue> mutableState3 = mutableState;
                                                    int i472 = BasicTextFieldKt.$r8$clinit;
                                                    mutableState3.setValue(textFieldValue3);
                                                    boolean zAreEqual = Intrinsics.areEqual((String) mutableState2.getValue(), textFieldValue3.annotatedString.text);
                                                    MutableState<String> mutableState4 = mutableState2;
                                                    AnnotatedString annotatedString = textFieldValue3.annotatedString;
                                                    mutableState4.setValue(annotatedString.text);
                                                    if (!zAreEqual) {
                                                        function1.mo781invoke(annotatedString.text);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue4);
                                            int i472 = i442 << 9;
                                            composerImpl = composerImpl2;
                                            CoreTextFieldKt.CoreTextField(textFieldValue, (Function1) objRememberedValue4, modifier52, textStyle52, visualTransformation4, function14, mutableInteractionSource3, brush3, z152, i46, i45, imeOptions2, keyboardActions4, z11, z9, function33, null, composerImpl, (i6 & 896) | ((i6 >> 6) & 7168) | (57344 & i472) | (458752 & i472) | (3670016 & i472) | (i472 & 29360128), ((i6 >> 15) & 896) | (i6 & 7168) | (i6 & 57344) | (i442 & 458752), 65536);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            modifier3 = modifier52;
                                            textStyle3 = textStyle52;
                                            function13 = function14;
                                            mutableInteractionSource2 = mutableInteractionSource3;
                                            brush2 = brush3;
                                            keyboardActions2 = keyboardActions4;
                                            z7 = z11;
                                            z8 = z9;
                                            function32 = function33;
                                            keyboardOptions3 = keyboardOptions72;
                                            i26 = i372;
                                            i27 = i382;
                                            visualTransformation2 = visualTransformation4;
                                            z6 = z142;
                                        }
                                    }
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextFieldKt.BasicTextField.9
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        BasicTextFieldKt.BasicTextField(str, function1, modifier3, z7, z8, textStyle3, keyboardOptions3, keyboardActions2, z6, i26, i27, visualTransformation2, function13, mutableInteractionSource2, brush2, function32, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i3 | 1), RecomposeScopeImplKt.updateChangedFlags(i4), i5);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i24 = i22 | 24576;
                        i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i25 == 0) {
                        }
                        if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i13 = i12;
                    if ((i3 & 805306368) == 0) {
                    }
                    i14 = i5 & 1024;
                    if (i14 != 0) {
                    }
                    i17 = i5 & 2048;
                    if (i17 != 0) {
                    }
                    int i342 = i16;
                    i19 = i5 & 4096;
                    if (i19 != 0) {
                    }
                    i21 = i5 & 8192;
                    if (i21 != 0) {
                    }
                    i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if (i23 == 0) {
                    }
                    i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i25 == 0) {
                    }
                    if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                z5 = z2;
                i9 = i5 & 32;
                if (i9 == 0) {
                }
                i10 = i5 & 64;
                if (i10 == 0) {
                }
                i11 = i5 & 128;
                if (i11 == 0) {
                }
                i12 = i5 & 256;
                if (i12 == 0) {
                }
                i13 = i12;
                if ((i3 & 805306368) == 0) {
                }
                i14 = i5 & 1024;
                if (i14 != 0) {
                }
                i17 = i5 & 2048;
                if (i17 != 0) {
                }
                int i3422 = i16;
                i19 = i5 & 4096;
                if (i19 != 0) {
                }
                i21 = i5 & 8192;
                if (i21 != 0) {
                }
                i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i23 == 0) {
                }
                i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i25 == 0) {
                }
                if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z4 = z;
            i8 = i5 & 16;
            int i332 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i8 != 0) {
            }
            z5 = z2;
            i9 = i5 & 32;
            if (i9 == 0) {
            }
            i10 = i5 & 64;
            if (i10 == 0) {
            }
            i11 = i5 & 128;
            if (i11 == 0) {
            }
            i12 = i5 & 256;
            if (i12 == 0) {
            }
            i13 = i12;
            if ((i3 & 805306368) == 0) {
            }
            i14 = i5 & 1024;
            if (i14 != 0) {
            }
            i17 = i5 & 2048;
            if (i17 != 0) {
            }
            int i34222 = i16;
            i19 = i5 & 4096;
            if (i19 != 0) {
            }
            i21 = i5 & 8192;
            if (i21 != 0) {
            }
            i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i23 == 0) {
            }
            i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i25 == 0) {
            }
            if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i7 = i5 & 8;
        if (i7 == 0) {
        }
        z4 = z;
        i8 = i5 & 16;
        int i3322 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i8 != 0) {
        }
        z5 = z2;
        i9 = i5 & 32;
        if (i9 == 0) {
        }
        i10 = i5 & 64;
        if (i10 == 0) {
        }
        i11 = i5 & 128;
        if (i11 == 0) {
        }
        i12 = i5 & 256;
        if (i12 == 0) {
        }
        i13 = i12;
        if ((i3 & 805306368) == 0) {
        }
        i14 = i5 & 1024;
        if (i14 != 0) {
        }
        i17 = i5 & 2048;
        if (i17 != 0) {
        }
        int i342222 = i16;
        i19 = i5 & 4096;
        if (i19 != 0) {
        }
        i21 = i5 & 8192;
        if (i21 != 0) {
        }
        i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i23 == 0) {
        }
        i25 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i25 == 0) {
        }
        if (composerImpl2.shouldExecute(i6 & 1, (i6 & 306783379) == 306783378 || (i24 & 74899) != 74898)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
