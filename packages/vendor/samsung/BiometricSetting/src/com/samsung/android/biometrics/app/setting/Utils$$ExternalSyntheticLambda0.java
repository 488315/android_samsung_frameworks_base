package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Display;
import android.widget.Toast;

import com.samsung.android.desktopmode.SemDesktopModeManager;

public final /* synthetic */ class Utils$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ Utils$$ExternalSyntheticLambda0(Context context, String str) {
        this.f$0 = context;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f$0;
        String str = this.f$1;
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService(SemDesktopModeManager.class);
        if (((semDesktopModeManager == null
                                || semDesktopModeManager.getDesktopModeState().getEnabled() != 4)
                        ? 0
                        : semDesktopModeManager.getDesktopModeState().getDisplayType())
                != 102) {
            return;
        }
        if (!TextUtils.isEmpty(str)) {
            Toast makeText = Toast.makeText(context, str, 0);
            makeText.semSetPreferredDisplayType(1);
            makeText.show();
        }
        Display display =
                ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0);
        if (display == null || display.getState() != 2) {
            try {
                PowerManager.WakeLock newWakeLock =
                        ((PowerManager) context.getSystemService(PowerManager.class))
                                .newWakeLock(268435466, "biometric:WakeLockForDeX");
                newWakeLock.setReferenceCounted(false);
                newWakeLock.acquire(1000L);
                newWakeLock.release();
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("handleWakeLockForDexIfNeeded: "), "BSS_Utils");
            }
        }
    }
}
