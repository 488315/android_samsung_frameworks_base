package com.android.server;

import android.os.FileUtils;
import android.os.SystemProperties;
import android.util.Slog;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class WatchdogSoftdog {
    public static WatchdogSoftdog sInstance;
    public int mSoftdogTimeout = 100;
    public boolean mSoftdogDisabled = true;

    private native void native_sdogClose();

    private native void native_sdogKick();

    private native int native_sdogOpen();

    private native void native_sdogSetTimeout(int i);

    public boolean isSoftDogDisabled() {
        return this.mSoftdogDisabled;
    }

    public static WatchdogSoftdog getInstance() {
        if (sInstance == null) {
            sInstance = new WatchdogSoftdog();
        }
        return sInstance;
    }

    public void softdogInitialize() {
        if ("off".equals(SystemProperties.get("persist.vendor.softdog"))) {
            Slog.i("Watchdog:WatchdogSoftdog", "!@persist.vendor.softdog is off, so do not turn on softdog");
        } else if (native_sdogOpen() >= 0) {
            this.mSoftdogDisabled = false;
            native_sdogSetTimeout(100);
        }
    }

    public void softdogKick(int i) {
        if (this.mSoftdogDisabled) {
            return;
        }
        if (i == 1000) {
            Slog.w("Watchdog:WatchdogSoftdog", "!@ softdog timeout is changed to " + i);
            this.mSoftdogTimeout = i;
            native_sdogSetTimeout(i);
            return;
        }
        if (Watchdog.DEBUG_LEVEL_LOW) {
            native_sdogKick();
            return;
        }
        try {
            String readTextFile = FileUtils.readTextFile(new File("/sys/class/power_supply/battery/temp"), 1024, null);
            int parseInt = readTextFile != null ? Integer.parseInt(readTextFile.trim()) : 0;
            if (parseInt > 600 && this.mSoftdogTimeout == 100) {
                native_sdogSetTimeout(1000);
                this.mSoftdogTimeout = 1000;
                Slog.w("Watchdog:WatchdogSoftdog", "!@ set softdog timeout to 1000 by high temperature");
            } else {
                if (parseInt < 550 && this.mSoftdogTimeout == 1000) {
                    native_sdogSetTimeout(100);
                    this.mSoftdogTimeout = 100;
                    Slog.w("Watchdog:WatchdogSoftdog", "!@ set softdog timeout to 100");
                    return;
                }
                native_sdogKick();
            }
        } catch (IOException e) {
            Slog.w("Watchdog:WatchdogSoftdog", "FileUtils", e);
            native_sdogKick();
        }
    }

    public void softdogTerminate() {
        if (this.mSoftdogDisabled) {
            return;
        }
        native_sdogClose();
        this.mSoftdogDisabled = true;
    }
}
