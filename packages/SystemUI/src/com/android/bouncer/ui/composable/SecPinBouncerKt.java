package com.android.bouncer.ui.composable;

import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.Easings;
import com.android.compose.animation.Easings$fromInterpolator$1;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SecPinBouncerKt {
    public static final float pinButtonErrorShrinkFactor;
    public static final long pinButtonHoldTime;
    public static final long pinButtonPressedDuration;
    public static final Easings$fromInterpolator$1 pinButtonPressedEasing;
    public static final long pinButtonReleasedDuration;
    public static final Easings$fromInterpolator$1 pinButtonReleasedEasing;

    static {
        Dp.Companion companion = Dp.Companion;
        pinButtonErrorShrinkFactor = 67 / 60;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        pinButtonPressedDuration = DurationKt.toDuration(100, durationUnit);
        Easings.INSTANCE.getClass();
        pinButtonPressedEasing = Easings.Linear;
        pinButtonHoldTime = DurationKt.toDuration(33, durationUnit);
        pinButtonReleasedDuration = DurationKt.toDuration(VolteConstants.ErrorCode.BAD_EXTENSION, durationUnit);
        pinButtonReleasedEasing = Easings.Standard;
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x012b, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L84;
     */
    /* renamed from: ActionButton-mwpFuRA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m901ActionButtonmwpFuRA(final com.android.systemui.common.shared.model.Icon.Resource r24, final boolean r25, final kotlin.jvm.functions.Function0 r26, kotlin.jvm.functions.Function0 r27, final com.android.systemui.bouncer.ui.viewmodel.ActionButtonAppearance r28, final boolean r29, final long r30, final float r32, final kotlin.jvm.internal.PropertyReference0Impl r33, androidx.compose.runtime.Composer r34, final int r35, final int r36) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPinBouncerKt.m901ActionButtonmwpFuRA(com.android.systemui.common.shared.model.Icon$Resource, boolean, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, com.android.systemui.bouncer.ui.viewmodel.ActionButtonAppearance, boolean, long, float, kotlin.jvm.internal.PropertyReference0Impl, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00bc  */
    /* renamed from: PinPadButton--nWoaYo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m902PinPadButtonnWoaYo(final kotlin.jvm.functions.Function0 r27, final boolean r28, final long r29, final long r31, final androidx.compose.ui.Modifier r33, kotlin.jvm.functions.Function0 r34, final float r35, final androidx.compose.runtime.internal.ComposableLambdaImpl r36, androidx.compose.runtime.Composer r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPinBouncerKt.m902PinPadButtonnWoaYo(kotlin.jvm.functions.Function0, boolean, long, long, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function0, float, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00bc, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L58;
     */
    /* renamed from: SecDigitButton-wBJOh4Y, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m903SecDigitButtonwBJOh4Y(final int r18, final java.lang.String r19, final boolean r20, final kotlin.jvm.functions.Function1 r21, final long r22, final long r24, final float r26, androidx.compose.runtime.Composer r27, final int r28) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPinBouncerKt.m903SecDigitButtonwBJOh4Y(int, java.lang.String, boolean, kotlin.jvm.functions.Function1, long, long, float, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007c, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L37;
     */
    /* renamed from: SecPinPad-uFdPcIQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m904SecPinPaduFdPcIQ(final com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r20, float r21, final androidx.compose.ui.Modifier r22, androidx.compose.runtime.Composer r23, final int r24) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPinBouncerKt.m904SecPinPaduFdPcIQ(com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel, float, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
