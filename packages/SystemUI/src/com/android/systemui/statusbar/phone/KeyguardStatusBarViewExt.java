package com.android.systemui.statusbar.phone;

import android.os.Debug;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

public final class KeyguardStatusBarViewExt {
    public float alpha = Float.MAX_VALUE;
    public int visibility = -1;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
