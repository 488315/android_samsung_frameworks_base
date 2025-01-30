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

/* loaded from: classes.dex */
public class HeapdumpWatcher {
    public static Context mContext;
    public ActivityManagerService mActivity;
    public static final double THRESHOLD_OF_HEAPSIZE = (Runtime.getRuntime().maxMemory() * 0.96d) / 1048576.0d;
    public static boolean mHeapDumped = false;
    public int mOverThresholdCnt = 0;
    public int mScreenOffCount = 0;
    public long mAllocatedMemory = 0;
    public final WatchdogSoftdog softdog = WatchdogSoftdog.getInstance();

    public void initInstance(Context context, ActivityManagerService activityManagerService) {
        mContext = context;
        this.mActivity = activityManagerService;
    }

    public void setAllocatedMemory(long j) {
        this.mAllocatedMemory = j;
    }

    public void checkHeap() {
        if (this.mAllocatedMemory < THRESHOLD_OF_HEAPSIZE) {
            this.mOverThresholdCnt = 0;
            this.mScreenOffCount = 0;
            return;
        }
        this.mOverThresholdCnt++;
        Slog.e("Watchdog:HeapdumpWatcher", "!@ The heap has been allocated excessively. OverThresholdCnt : " + this.mOverThresholdCnt);
        int i = this.mOverThresholdCnt;
        if (i < 20) {
            if (i == 2) {
                makeHeapDump();
            }
        } else if (checkConditionToThrowOOM()) {
            throw new OutOfMemoryError("HeapFull, " + this.mAllocatedMemory + "MB was used");
        }
    }

    public final boolean checkConditionToThrowOOM() {
        return checkScreenOff() && checkBackgroundAudio() && checkCall();
    }

    public boolean checkScreenOff() {
        PowerManager powerManager = (PowerManager) mContext.getSystemService("power");
        if (powerManager != null && powerManager.isInteractive()) {
            Slog.d("Watchdog:HeapdumpWatcher", "screen is on now");
            this.mScreenOffCount = 0;
        } else {
            this.mScreenOffCount++;
        }
        if (this.mScreenOffCount > 2) {
            return true;
        }
        Slog.w("Watchdog:HeapdumpWatcher", "!@ screen is on now (or off few seconds ago) cnt : " + this.mScreenOffCount);
        return false;
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

    public final void makeHeapDump() {
        if (mHeapDumped) {
            return;
        }
        ActivityManagerService activityManagerService = this.mActivity;
        if (activityManagerService == null || activityManagerService.isHeapDumpAllowed()) {
            mHeapDumped = true;
            new Thread("watchdogHeapDump") { // from class: com.android.server.HeapdumpWatcher.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
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
