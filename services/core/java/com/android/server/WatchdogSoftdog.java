package com.android.server;

import android.os.FileUtils;
import android.util.Slog;

import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.io.IOException;

public class WatchdogSoftdog {
    public static WatchdogSoftdog sInstance;
    public boolean mSoftdogDisabled;
    public int mSoftdogTimeout;

    public native void native_sdogClose();

    public native void native_sdogKick();

    public native int native_sdogOpen();

    public native void native_sdogSetTimeout(int i);

    public final void softdogKick(int i) {
        if (this.mSoftdogDisabled) {
            return;
        }
        if (i == 1000) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "!@ softdog timeout is changed to ", "Watchdog:WatchdogSoftdog");
            this.mSoftdogTimeout = i;
            native_sdogSetTimeout(i);
            return;
        }
        if (CoreRune.IS_DEBUG_LEVEL_LOW) {
            native_sdogKick();
            return;
        }
        int i2 = 0;
        try {
            String readTextFile =
                    FileUtils.readTextFile(
                            new File("/sys/class/power_supply/battery/temp"), 1024, null);
            if (readTextFile != null) {
                i2 = Integer.parseInt(readTextFile.trim());
            }
        } catch (IOException e) {
            Slog.w("Watchdog:WatchdogSoftdog", "FileUtils", e);
        }
        if (i2 > 600 && this.mSoftdogTimeout == 100) {
            native_sdogSetTimeout(1000);
            this.mSoftdogTimeout = 1000;
            Slog.w(
                    "Watchdog:WatchdogSoftdog",
                    "!@ set softdog timeout to 1000 by high temperature");
        } else {
            if (i2 >= 550 || this.mSoftdogTimeout != 1000) {
                native_sdogKick();
                return;
            }
            native_sdogSetTimeout(100);
            this.mSoftdogTimeout = 100;
            Slog.w("Watchdog:WatchdogSoftdog", "!@ set softdog timeout to 100");
        }
    }
}
