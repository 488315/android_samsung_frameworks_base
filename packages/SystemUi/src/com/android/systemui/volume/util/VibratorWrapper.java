package com.android.systemui.volume.util;

import android.content.Context;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VibratorWrapper {
    public final Vibrator vibrator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public VibratorWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        this.vibrator = ((VibratorManager) context.getSystemService(VibratorManager.class)).getDefaultVibrator();
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
