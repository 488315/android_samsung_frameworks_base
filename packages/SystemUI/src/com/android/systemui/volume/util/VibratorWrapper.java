package com.android.systemui.volume.util;

import android.content.Context;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class VibratorWrapper {
    public final Vibrator vibrator;

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

    public VibratorWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        Object systemService = context.getSystemService((Class<Object>) VibratorManager.class);
        systemService.getClass();
        this.vibrator = ((VibratorManager) systemService).getDefaultVibrator();
    }

    public final void startKeyHaptic() {
        Log.d("vol.VibratorWrapper", "Volume panel key VI vibrate");
        this.vibrator.vibrate(VibrationEffect.semCreateHaptic(50065, -1), new VibrationAttributes.Builder().setUsage(18).build());
    }

    public final void vibrate() {
        Log.d("vol.VibratorWrapper", "AOD volume panel show vibrate");
        this.vibrator.vibrate(VibrationEffect.semCreateHaptic(50025, -1), new VibrationAttributes.Builder().setUsage(18).build());
    }
}
