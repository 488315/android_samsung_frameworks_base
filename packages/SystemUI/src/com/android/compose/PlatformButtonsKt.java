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
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlatformButtonsKt {
    public static final PaddingValuesImpl ButtonPaddings;

    static {
        Dp.Companion companion = Dp.Companion;
        ButtonPaddings = PaddingKt.m120PaddingValuesYgX7TsA(16, 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void PlatformButton(final kotlin.jvm.functions.Function0 r19, androidx.compose.ui.Modifier r20, boolean r21, androidx.compose.material3.ButtonColors r22, androidx.compose.foundation.layout.PaddingValuesImpl r23, androidx.compose.ui.graphics.Shape r24, androidx.compose.runtime.internal.ComposableLambdaImpl r25, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.PlatformButtonsKt.PlatformButton(kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.ButtonColors, androidx.compose.foundation.layout.PaddingValuesImpl, androidx.compose.ui.graphics.Shape, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void PlatformOutlinedButton(final Function0 function0, Modifier.Companion companion, boolean z, ButtonColors buttonColors, BorderStroke borderStroke, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        final BorderStroke m31BorderStrokecXLIe8U;
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
            m31BorderStrokecXLIe8U = borderStroke;
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
                ButtonColors m252outlinedButtonColorsro_MJ88 = ButtonDefaults.m252outlinedButtonColorsro_MJ88(j, composerImpl);
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
                m31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U(1, MaterialTheme.getColorScheme(composerImpl).primary);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                int i4 = i3 & (-64513);
                z2 = true;
                companion2 = companion4;
                i2 = i4;
                buttonColors2 = m252outlinedButtonColorsro_MJ88;
            } else {
                composerImpl.skipToGroupEnd();
                int i5 = i3 & (-64513);
                z2 = z;
                buttonColors2 = buttonColors;
                m31BorderStrokecXLIe8U = borderStroke;
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
            ButtonKt.OutlinedButton(function0, SizeKt.m132heightInVpY3zN4$default(companion2, 36, 0.0f, 2), z4, null, buttonColors2, null, m31BorderStrokecXLIe8U, ButtonPaddings, null, ComposableLambdaKt.rememberComposableLambda(-670085408, new Function3() { // from class: com.android.compose.PlatformButtonsKt$PlatformOutlinedButton$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    RowScope rowScope = (RowScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(rowScope) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.PlatformOutlinedButton.<anonymous> (PlatformButtons.kt:76)");
                    }
                    Function3.this.invoke(rowScope, composer2, Integer.valueOf(intValue & 14));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl2;
            endRestartGroup.block = new Function2(companion3, z3, buttonColors3, m31BorderStrokecXLIe8U, composableLambdaImpl3, i) { // from class: com.android.compose.PlatformButtonsKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Modifier.Companion f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ ButtonColors f$3;
                public final /* synthetic */ BorderStroke f$4;
                public final /* synthetic */ ComposableLambdaImpl f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196609);
                    BorderStroke borderStroke2 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl4 = this.f$5;
                    PlatformButtonsKt.PlatformOutlinedButton(Function0.this, this.f$1, this.f$2, this.f$3, borderStroke2, composableLambdaImpl4, (Composer) obj, updateChangedFlags);
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
                ButtonColors m253textButtonColorsro_MJ88 = ButtonDefaults.m253textButtonColorsro_MJ88(0L, j, composerImpl, 13);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                z2 = true;
                buttonColors2 = m253textButtonColorsro_MJ88;
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(companion3, z3, buttonColors3, composableLambdaImpl, i) { // from class: com.android.compose.PlatformButtonsKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier.Companion f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ ButtonColors f$3;
                public final /* synthetic */ ComposableLambdaImpl f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(24577);
                    ButtonColors buttonColors4 = this.f$3;
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$4;
                    PlatformButtonsKt.PlatformTextButton(Function0.this, this.f$1, this.f$2, buttonColors4, composableLambdaImpl2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
