package com.android.systemui.statusbar.phone;

import android.os.Debug;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KeyguardStatusBarViewExt {
    public float alpha = Float.MAX_VALUE;
    public int visibility = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final void printStatusLog() {
        int i = this.visibility;
        int i2 = StringCompanionObject.$r8$clinit;
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.alpha)}, 1));
        ExifInterface$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "v:", ", a:", format, ", "), Debug.getCallers(2, 5), "KeyguardStatusBarViewExt");
    }
}
