package com.android.keyguard;

import android.os.SystemProperties;
import android.util.Log;
import android.view.DisplayInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;

public final class KeyguardPresentationDisabler implements Dumpable {
    public static final int[] KEYS = {24, 25};
    private final SettingsHelper mSettingsHelper;
    public final VibratorHelper mVibratorHelper;
    public final int[] mDownCount = {0, 0};
    public long mLastDownTime = 0;
    public boolean mKeyEnabled = false;

    public KeyguardPresentationDisabler(VibratorHelper vibratorHelper, DumpManager dumpManager, SettingsHelper settingsHelper) {
        this.mVibratorHelper = vibratorHelper;
        this.mSettingsHelper = settingsHelper;
        dumpManager.registerNormalDumpable("KeyguardPresentationDisabler", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  - mKeyEnabled: " + this.mKeyEnabled);
        if (!this.mKeyEnabled) {
            printWriter.print('\n');
            return;
        }
        printWriter.println(" / " + LogUtil.makeTimeStr(this.mLastDownTime));
    }

    public final boolean isEnabled(DisplayInfo displayInfo) {
        if (!DeviceType.isShipBuild() && SystemProperties.getBoolean("debug.keyguard.show_presentation", false)) {
            Log.i("KeyguardDisplayManager", "Show KeyguardPresentation for debugging");
            return true;
        }
        if (this.mKeyEnabled) {
            Log.i("KeyguardDisplayManager", "Do now show KeyguardPresentation: key enabled");
            return false;
        }
        if (this.mSettingsHelper.isSideSyncEnabled()) {
            Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation: sideSync is enabled");
            return false;
        }
        if ((displayInfo.flags & 536870912) == 0) {
            return true;
        }
        Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation: Display.FLAG_NO_LOCK_PRESENTATION");
        return false;
    }
}
