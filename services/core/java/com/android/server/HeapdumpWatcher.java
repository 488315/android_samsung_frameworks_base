package com.android.server;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Debug;
import android.os.PowerManager;
import android.telecom.TelecomManager;
import android.util.Slog;
import com.android.server.am.ActivityManagerService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HeapdumpWatcher {
    public static Context mContext;
    public ActivityManagerService mActivity;
    public final WatchdogSoftdog softdog;
    public static final double THRESHOLD_OF_HEAPSIZE = (Runtime.getRuntime().maxMemory() * 0.96d) / 1048576.0d;
    public static boolean mHeapDumped = false;
    public int mOverThresholdCnt = 0;
    public int mScreenOffCount = 0;
    public long mAllocatedMemory = 0;

    public HeapdumpWatcher() {
        if (WatchdogSoftdog.sInstance == null) {
            WatchdogSoftdog watchdogSoftdog = new WatchdogSoftdog();
            watchdogSoftdog.mSoftdogTimeout = 100;
            watchdogSoftdog.mSoftdogDisabled = true;
            WatchdogSoftdog.sInstance = watchdogSoftdog;
        }
        this.softdog = WatchdogSoftdog.sInstance;
    }

    public boolean checkBackgroundAudio() {
        AudioManager audioManager = (AudioManager) mContext.getSystemService("audio");
        if (audioManager == null) {
            Slog.e("Watchdog:HeapdumpWatcher", "Failed to get AudioManager");
            return false;
        }
        for (AudioPlaybackConfiguration audioPlaybackConfiguration : audioManager.getActivePlaybackConfigurations()) {
            int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
            if (usage != 2 && usage != 6 && audioPlaybackConfiguration.isActive()) {
                Slog.w("Watchdog:HeapdumpWatcher", "!@ audio is active by uid : " + audioPlaybackConfiguration.getClientUid());
                return false;
            }
        }
        return true;
    }

    public boolean checkCall() {
        TelecomManager telecomManager = (TelecomManager) mContext.getSystemService("telecom");
        if (telecomManager == null || !telecomManager.isInCall()) {
            return true;
        }
        Slog.w("Watchdog:HeapdumpWatcher", "!@ In call");
        return false;
    }

    public boolean checkScreenOff() {
        PowerManager powerManager = (PowerManager) mContext.getSystemService("power");
        if (powerManager == null || !powerManager.isInteractive()) {
            this.mScreenOffCount++;
        } else {
            Slog.d("Watchdog:HeapdumpWatcher", "screen is on now");
            this.mScreenOffCount = 0;
        }
        if (this.mScreenOffCount > 2) {
            return true;
        }
        HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("!@ screen is on now (or off few seconds ago) cnt : "), this.mScreenOffCount, "Watchdog:HeapdumpWatcher");
        return false;
    }

    public final void makeHeapDump() {
        if (mHeapDumped) {
            return;
        }
        ActivityManagerService activityManagerService = this.mActivity;
        if (activityManagerService == null || activityManagerService.isHeapDumpAllowed()) {
            mHeapDumped = true;
            new Thread() { // from class: com.android.server.HeapdumpWatcher.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        if (!Files.exists(Paths.get("/data/log/core", new String[0]), new LinkOption[0])) {
                            Slog.w("Watchdog:HeapdumpWatcher", " create folder /data/log/core");
                            Files.createDirectory(Paths.get("/data/log/core", new String[0]), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
                        }
                        Slog.i("Watchdog:HeapdumpWatcher", "Start dumping for java heapdump");
                        HeapdumpWatcher.this.softdog.softdogKick(1000);
                        Debug.dumpHprofData("/data/log/core/system_server.hprof");
                    } catch (IOException unused) {
                        Slog.w("Watchdog:HeapdumpWatcher", "IOException: Cannot dump for java heapdump");
                    } catch (RuntimeException unused2) {
                        Slog.w("Watchdog:HeapdumpWatcher", "RuntimeException: Cannot dump for java heapdump");
                    }
                }
            }.start();
        }
    }
}
