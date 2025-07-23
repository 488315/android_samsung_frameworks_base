package com.android.keyguard;

import android.os.SystemProperties;
import android.util.Log;
import android.view.DisplayInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardPresentationDisabler implements Dumpable {
    private final SettingsHelper mSettingsHelper;

    public KeyguardPresentationDisabler(VibratorHelper vibratorHelper, DumpManager dumpManager, SettingsHelper settingsHelper) {
        this.mSettingsHelper = settingsHelper;
        dumpManager.registerNormalDumpable("KeyguardPresentationDisabler", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  - mKeyEnabled: false");
        printWriter.print('\n');
    }

    public final boolean isEnabled(DisplayInfo displayInfo) {
        if (!DeviceType.isShipBuild() && SystemProperties.getBoolean("debug.keyguard.show_presentation", false)) {
            Log.i("KeyguardDisplayManager", "Show KeyguardPresentation for debugging");
            return true;
        }
        if (this.mSettingsHelper.isSideSyncEnabled()) {
            Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation: sideSync is enabled");
            return false;
        }
        if ((displayInfo.flags & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) == 0) {
            return true;
        }
        Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation: Display.FLAG_NO_LOCK_PRESENTATION");
        return false;
    }
}
