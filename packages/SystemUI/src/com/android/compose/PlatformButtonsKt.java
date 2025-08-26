package com.android.compose;

import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.QuantumSecurityInfo;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class PlatformButtonsKt {
    public static final PaddingValuesImpl ButtonPaddings;

    static {
        Dp.Companion companion = Dp.Companion;
        ButtonPaddings = PaddingKt.m121PaddingValuesYgX7TsA(16, 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void PlatformButton(final Function0 function0, Modifier modifier, boolean z, ButtonColors buttonColors, PaddingValuesImpl paddingValuesImpl, Shape shape, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        ButtonColors buttonColors2;
        int i3;
        ButtonColors buttonColorsM252buttonColorsro_MJ88;
        PaddingValuesImpl paddingValuesImpl2;
        Shape shape2;
        int i4;
        boolean z2;
        Modifier modifier3;
        final ComposableLambdaImpl composableLambdaImpl2;
        final ButtonColors buttonColors3;
        final boolean z3;
        final Shape shape3;
        final PaddingValuesImpl paddingValuesImpl3;
        final Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1497181280);
        int i5 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        int i6 = i2 & 2;
        if (i6 != 0) {
            i5 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i5 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            int i7 = i5 | 384;
            if ((i2 & 8) != 0) {
                buttonColors2 = buttonColors;
                int i8 = composerImpl.changed(buttonColors2) ? 2048 : 1024;
                i3 = i7 | i8 | 90112;
                if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    z3 = z;
                    paddingValuesImpl3 = paddingValuesImpl;
                    shape3 = shape;
                    composableLambdaImpl2 = composableLambdaImpl;
                    modifier4 = modifier2;
                    buttonColors3 = buttonColors2;
                } else {
                    composerImpl.startDefaults();
                    if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                        Modifier modifier5 = i6 == 0 ? Modifier.Companion : modifier2;
                        if ((i2 & 8) == 0) {
                            composerImpl.startReplaceGroup(-611437802);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.filledButtonColors (PlatformButtons.kt:125)");
                            }
                            MaterialTheme.INSTANCE.getClass();
                            ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
                            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                            long j = colorScheme.primary;
                            buttonDefaults.getClass();
                            buttonColorsM252buttonColorsro_MJ88 = ButtonDefaults.m252buttonColorsro_MJ88(j, colorScheme.onPrimary, composerImpl, 12);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            i3 &= -7169;
                        } else {
                            buttonColorsM252buttonColorsro_MJ88 = buttonColors2;
                        }
                        ButtonDefaults.INSTANCE.getClass();
                        Shape shape4 = ButtonDefaults.getShape(composerImpl);
                        int i9 = i3 & (-458753);
                        paddingValuesImpl2 = ButtonPaddings;
                        shape2 = shape4;
                        i4 = i9;
                        z2 = true;
                        modifier3 = modifier5;
                    } else {
                        composerImpl.skipToGroupEnd();
                        if ((i2 & 8) != 0) {
                            i3 &= -7169;
                        }
                        int i10 = i3 & (-458753);
                        paddingValuesImpl2 = paddingValuesImpl;
                        i4 = i10;
                        modifier3 = modifier2;
                        buttonColorsM252buttonColorsro_MJ88 = buttonColors2;
                        z2 = z;
                        shape2 = shape;
                    }
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.PlatformButton (PlatformButtons.kt:46)");
                    }
                    Dp.Companion companion = Dp.Companion;
                    composableLambdaImpl2 = composableLambdaImpl;
                    ButtonColors buttonColors4 = buttonColorsM252buttonColorsro_MJ88;
                    ButtonKt.Button(function0, SizeKt.m133heightInVpY3zN4$default(modifier3, 36, 0.0f, 2), z2, shape2, buttonColors4, null, null, paddingValuesImpl2, null, ComposableLambdaKt.rememberComposableLambda(-221024176, new Function3() { // from class: com.android.compose.PlatformButtonsKt.PlatformButton.1
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            RowScope rowScope = (RowScope) obj;
                            Composer composer2 = (Composer) obj2;
                            int iIntValue = ((Number) obj3).intValue();
                            if ((iIntValue & 6) == 0) {
                                iIntValue |= ((ComposerImpl) composer2).changed(rowScope) ? 4 : 2;
                            }
                            if ((iIntValue & 19) == 18) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.PlatformButton.<anonymous> (PlatformButtons.kt:55)");
                                    }
                                    composableLambdaImpl2.invoke(rowScope, composer2, Integer.valueOf(iIntValue & 14));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, ((i4 << 3) & 57344) | (i4 & 14) | 805306752 | 12582912, QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION);
                    composerImpl = composerImpl;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    buttonColors3 = buttonColors4;
                    z3 = z2;
                    shape3 = shape2;
                    paddingValuesImpl3 = paddingValuesImpl2;
                    modifier4 = modifier3;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl2;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.PlatformButtonsKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl3;
                            PlatformButtonsKt.PlatformButton(function0, modifier4, z3, buttonColors3, paddingValuesImpl3, shape3, composableLambdaImpl4, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            buttonColors2 = buttonColors;
            i3 = i7 | i8 | 90112;
            if ((599187 & i3) == 599186) {
                composerImpl.startDefaults();
                if ((i & 1) != 0) {
                    if (i6 == 0) {
                    }
                    if ((i2 & 8) == 0) {
                    }
                    ButtonDefaults.INSTANCE.getClass();
                    Shape shape42 = ButtonDefaults.getShape(composerImpl);
                    int i92 = i3 & (-458753);
                    paddingValuesImpl2 = ButtonPaddings;
                    shape2 = shape42;
                    i4 = i92;
                    z2 = true;
                    modifier3 = modifier5;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    Dp.Companion companion2 = Dp.Companion;
                    composableLambdaImpl2 = composableLambdaImpl;
                    ButtonColors buttonColors42 = buttonColorsM252buttonColorsro_MJ88;
                    ButtonKt.Button(function0, SizeKt.m133heightInVpY3zN4$default(modifier3, 36, 0.0f, 2), z2, shape2, buttonColors42, null, null, paddingValuesImpl2, null, ComposableLambdaKt.rememberComposableLambda(-221024176, new Function3() { // from class: com.android.compose.PlatformButtonsKt.PlatformButton.1
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            RowScope rowScope = (RowScope) obj;
                            Composer composer2 = (Composer) obj2;
                            int iIntValue = ((Number) obj3).intValue();
                            if ((iIntValue & 6) == 0) {
                                iIntValue |= ((ComposerImpl) composer2).changed(rowScope) ? 4 : 2;
                            }
                            if ((iIntValue & 19) == 18) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.PlatformButton.<anonymous> (PlatformButtons.kt:55)");
                                    }
                                    composableLambdaImpl2.invoke(rowScope, composer2, Integer.valueOf(iIntValue & 14));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, ((i4 << 3) & 57344) | (i4 & 14) | 805306752 | 12582912, QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION);
                    composerImpl = composerImpl;
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    buttonColors3 = buttonColors42;
                    z3 = z2;
                    shape3 = shape2;
                    paddingValuesImpl3 = paddingValuesImpl2;
                    modifier4 = modifier3;
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        int i72 = i5 | 384;
        if ((i2 & 8) != 0) {
        }
        i3 = i72 | i8 | 90112;
        if ((599187 & i3) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    public static final void PlatformOutlinedButton(final Function0 function0, Modifier.Companion companion, boolean z, ButtonColors buttonColors, BorderStroke borderStroke, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        final BorderStroke borderStrokeM31BorderStrokecXLIe8U;
        boolean z2;
        Modifier.Companion companion2;
        int i2;
        ButtonColors buttonColors2;
        final ComposableLambdaImpl composableLambdaImpl2;
        final Modifier.Companion companion3;
        final ButtonColors buttonColors3;
        final boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1105589102);
        int i3 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | 9648;
        if ((74899 & i3) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion3 = companion;
            z3 = z;
            buttonColors3 = buttonColors;
            borderStrokeM31BorderStrokecXLIe8U = borderStroke;
            composableLambdaImpl2 = composableLambdaImpl;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                Modifier.Companion companion4 = Modifier.Companion;
                composerImpl.startReplaceGroup(-1616274790);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.outlineButtonColors (PlatformButtons.kt:134)");
                }
                ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                materialTheme.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
                buttonDefaults.getClass();
                ButtonColors buttonColorsM253outlinedButtonColorsro_MJ88 = ButtonDefaults.m253outlinedButtonColorsro_MJ88(j, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                composerImpl.startReplaceGroup(1849525912);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.outlineButtonBorder (PlatformButtons.kt:146)");
                }
                Dp.Companion companion5 = Dp.Companion;
                materialTheme.getClass();
                borderStrokeM31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).primary);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                int i4 = i3 & (-64513);
                z2 = true;
                companion2 = companion4;
                i2 = i4;
                buttonColors2 = buttonColorsM253outlinedButtonColorsro_MJ88;
            } else {
                composerImpl.skipToGroupEnd();
                int i5 = i3 & (-64513);
                z2 = z;
                buttonColors2 = buttonColors;
                borderStrokeM31BorderStrokecXLIe8U = borderStroke;
                i2 = i5;
                companion2 = companion;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.PlatformOutlinedButton (PlatformButtons.kt:67)");
            }
            Dp.Companion companion6 = Dp.Companion;
            composableLambdaImpl2 = composableLambdaImpl;
            boolean z4 = z2;
            ButtonKt.OutlinedButton(function0, SizeKt.m133heightInVpY3zN4$default(companion2, 36, 0.0f, 2), z4, null, buttonColors2, null, borderStrokeM31BorderStrokecXLIe8U, ButtonPaddings, null, ComposableLambdaKt.rememberComposableLambda(-670085408, new Function3() { // from class: com.android.compose.PlatformButtonsKt.PlatformOutlinedButton.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    RowScope rowScope = (RowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(rowScope) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.PlatformOutlinedButton.<anonymous> (PlatformButtons.kt:76)");
                            }
                            composableLambdaImpl2.invoke(rowScope, composer2, Integer.valueOf(iIntValue & 14));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, (i2 & 14) | 817889664, IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion3 = companion2;
            buttonColors3 = buttonColors2;
            z3 = z4;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl2;
            recomposeScopeImplEndRestartGroup.block = new Function2(companion3, z3, buttonColors3, borderStrokeM31BorderStrokecXLIe8U, composableLambdaImpl3, i) { // from class: com.android.compose.PlatformButtonsKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Modifier.Companion f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ ButtonColors f$3;
                public final /* synthetic */ BorderStroke f$4;
                public final /* synthetic */ ComposableLambdaImpl f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196609);
                    BorderStroke borderStroke2 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl4 = this.f$5;
                    PlatformButtonsKt.PlatformOutlinedButton(this.f$0, this.f$1, this.f$2, this.f$3, borderStroke2, composableLambdaImpl4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void PlatformTextButton(final Function0 function0, Modifier.Companion companion, boolean z, ButtonColors buttonColors, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        boolean z2;
        ButtonColors buttonColors2;
        int i2;
        Modifier.Companion companion2;
        final Modifier.Companion companion3;
        final ButtonColors buttonColors3;
        final boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1853320753);
        int i3 = i | (composerImpl.changedInstance(function0) ? 4 : 2) | 1456;
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion3 = companion;
            z3 = z;
            buttonColors3 = buttonColors;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                Modifier.Companion companion4 = Modifier.Companion;
                composerImpl.startReplaceGroup(1942961313);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.textButtonColors (PlatformButtons.kt:151)");
                }
                ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).primary;
                buttonDefaults.getClass();
                ButtonColors buttonColorsM254textButtonColorsro_MJ88 = ButtonDefaults.m254textButtonColorsro_MJ88(0L, j, composerImpl, 13);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                z2 = true;
                buttonColors2 = buttonColorsM254textButtonColorsro_MJ88;
                i2 = i3 & (-7169);
                companion2 = companion4;
            } else {
                composerImpl.skipToGroupEnd();
                i2 = i3 & (-7169);
                companion2 = companion;
                z2 = z;
                buttonColors2 = buttonColors;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.PlatformTextButton (PlatformButtons.kt:87)");
            }
            ButtonKt.TextButton(function0, companion2, z2, null, buttonColors2, null, null, null, null, composableLambdaImpl, composerImpl, (i2 & 1022) | 805306368, VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion3 = companion2;
            buttonColors3 = buttonColors2;
            z3 = z2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(companion3, z3, buttonColors3, composableLambdaImpl, i) { // from class: com.android.compose.PlatformButtonsKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ ButtonColors f$3;
                public final /* synthetic */ ComposableLambdaImpl f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(24577);
                    ButtonColors buttonColors4 = this.f$3;
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$4;
                    PlatformButtonsKt.PlatformTextButton(this.f$0, this.f$1, this.f$2, buttonColors4, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
