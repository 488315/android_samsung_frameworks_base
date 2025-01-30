package com.samsung.android.server.audio;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.audio.PlaybackActivityMonitor;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.audiofx.SemVolumeMonitor;
import com.samsung.android.server.audio.VolumeMonitorService;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public class VolumeMonitorService {
    public static final Uri authority = Uri.parse("content://com.sec.android.app.volumemonitorprovider.VolumeMonitorProvider");
    public static VolumeMonitorService sInstance;
    public Context mContext;
    public SemVolumeMonitor mSemVolumeMonitor;
    public final Object mStateLock = new Object();
    public VolumeMonitorThread mVolumeMonitorThread = null;
    public Handler mVolumeHandler = null;
    public int mBluetoothVolumeIndex = 0;
    public PlaybackActivityMonitor mPlaybackActivityMonitor = null;
    public boolean mEnabled = false;
    public boolean mAvrcpAbsVolSupported = false;
    public int mState = 0;

    public VolumeMonitorService(Context context) {
        this.mContext = context;
    }

    public static synchronized VolumeMonitorService getInstance(Context context) {
        VolumeMonitorService volumeMonitorService;
        synchronized (VolumeMonitorService.class) {
            if (sInstance == null) {
                sInstance = new VolumeMonitorService(context);
            }
            volumeMonitorService = sInstance;
        }
        return volumeMonitorService;
    }

    public void setPlaybackActivityMonitor(PlaybackActivityMonitor playbackActivityMonitor) {
        this.mPlaybackActivityMonitor = playbackActivityMonitor;
    }

    public void triggerMonitoring() {
        synchronized (this) {
            if (this.mEnabled) {
                if (this.mVolumeHandler.hasMessages(0)) {
                    return;
                }
                Handler handler = this.mVolumeHandler;
                handler.sendMessage(handler.obtainMessage(0));
            }
        }
    }

    public final boolean initLocked(boolean z) {
        if (this.mSemVolumeMonitor == null) {
            try {
                this.mSemVolumeMonitor = new SemVolumeMonitor(0, -3);
            } catch (RuntimeException e) {
                Log.e("AS.VolumeMonitor", "Failed to init" + e);
                this.mSemVolumeMonitor = null;
                return false;
            }
        }
        if (!z || this.mVolumeMonitorThread != null) {
            return true;
        }
        VolumeMonitorThread volumeMonitorThread = new VolumeMonitorThread();
        this.mVolumeMonitorThread = volumeMonitorThread;
        volumeMonitorThread.start();
        waitForVolumeHandlerCreation();
        return true;
    }

    public void setVolumeMonitorOnOff(boolean z) {
        if (Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY && Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            z = true;
        }
        synchronized (this) {
            if (this.mEnabled == z) {
                return;
            }
            if (initLocked(z)) {
                this.mEnabled = z;
                this.mSemVolumeMonitor.onOff(z);
                if (z) {
                    AudioSystem.setParameters("l_volume_monitor_onoff=true");
                } else {
                    AudioSystem.setParameters("l_volume_monitor_onoff=false");
                }
                if (z) {
                    this.mSemVolumeMonitor.setBluetoothVolume(this.mBluetoothVolumeIndex);
                    this.mSemVolumeMonitor.setAbsoluteVolumeState(this.mAvrcpAbsVolSupported);
                    if (isMusicPlaying()) {
                        triggerMonitoring();
                    }
                }
            }
        }
    }

    public void audioServerDied() {
        boolean z;
        synchronized (this) {
            SemVolumeMonitor semVolumeMonitor = this.mSemVolumeMonitor;
            if (semVolumeMonitor != null) {
                semVolumeMonitor.release();
                this.mSemVolumeMonitor = null;
            }
            z = this.mEnabled;
            this.mEnabled = false;
        }
        setVolumeMonitorOnOff(z);
    }

    public void resetByDataClear() {
        synchronized (this) {
            SemVolumeMonitor semVolumeMonitor = this.mSemVolumeMonitor;
            if (semVolumeMonitor == null) {
                return;
            }
            semVolumeMonitor.resetData();
            AudioExecutor.execute(new Runnable() { // from class: com.samsung.android.server.audio.VolumeMonitorService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VolumeMonitorService.this.lambda$resetByDataClear$0();
                }
            });
        }
    }

    public void setDeviceVolumeForBluetooth(int i, boolean z) {
        boolean z2;
        this.mBluetoothVolumeIndex = i;
        if (this.mAvrcpAbsVolSupported != z) {
            this.mAvrcpAbsVolSupported = z;
            z2 = true;
        } else {
            z2 = false;
        }
        synchronized (this) {
            if (this.mEnabled) {
                this.mSemVolumeMonitor.setBluetoothVolume(i);
                if (z2) {
                    this.mSemVolumeMonitor.setAbsoluteVolumeState(z);
                }
            }
        }
    }

    public final void waitForVolumeHandlerCreation() {
        synchronized (this) {
            while (this.mVolumeHandler == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e("AS.VolumeMonitor", "Interrupted while waiting on volume handler.");
                }
            }
        }
    }

    public final boolean isMusicPlaying() {
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackActivityMonitor;
        return playbackActivityMonitor != null && playbackActivityMonitor.isMusicPlaying();
    }

    public class VolumeMonitorThread extends Thread {
        public VolumeMonitorThread() {
            super("VolumeMonitor");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (VolumeMonitorService.this) {
                VolumeMonitorService.this.mVolumeHandler = new VolumeMonitorHandler();
                VolumeMonitorService.this.notify();
            }
            Looper.loop();
        }

        public class VolumeMonitorHandler extends Handler {
            public VolumeMonitorHandler() {
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    synchronized (VolumeMonitorService.this.mStateLock) {
                        if (VolumeMonitorService.this.mState == 1) {
                            return;
                        }
                        VolumeMonitorService.this.mState = 1;
                        VolumeMonitorService.this.mVolumeHandler.sendMessageDelayed(VolumeMonitorService.this.mVolumeHandler.obtainMessage(1), 60000L);
                        return;
                    }
                }
                if (i == 1) {
                    AudioExecutor.execute(new Runnable() { // from class: com.samsung.android.server.audio.VolumeMonitorService$VolumeMonitorThread$VolumeMonitorHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VolumeMonitorService.VolumeMonitorThread.VolumeMonitorHandler.this.lambda$handleMessage$0();
                        }
                    });
                    synchronized (VolumeMonitorService.this.mStateLock) {
                        if (!VolumeMonitorService.this.mEnabled) {
                            VolumeMonitorService.this.mState = 0;
                            return;
                        } else if (VolumeMonitorService.this.isMusicPlaying()) {
                            Log.d("AS.VolumeMonitor", "Music is playing");
                            VolumeMonitorService.this.mVolumeHandler.sendMessageDelayed(VolumeMonitorService.this.mVolumeHandler.obtainMessage(1), 60000L);
                            return;
                        } else {
                            Log.d("AS.VolumeMonitor", "Music is stopped");
                            VolumeMonitorService.this.mState = 0;
                            return;
                        }
                    }
                }
                Log.e("AS.VolumeMonitor", "Not supported message");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleMessage$0() {
                VolumeMonitorService.this.sendVolumeData();
            }
        }
    }

    public final void sendVolumeData() {
        SemVolumeMonitor semVolumeMonitor = this.mSemVolumeMonitor;
        if (semVolumeMonitor == null) {
            Log.e("AS.VolumeMonitor", "SemVolumeMonitor is null");
            return;
        }
        try {
            getUserContext().getContentResolver().call(authority, "setVolumeData()", new String(semVolumeMonitor.getOneMinScoreStatus(FrameworkStatsLog.NOTIFICATION_REPORTED, 60), StandardCharsets.UTF_8), (Bundle) null);
        } catch (Exception e) {
            Log.e("AS.VolumeMonitor", "sendVolumeData() " + e.getMessage());
        }
    }

    /* renamed from: resetData, reason: merged with bridge method [inline-methods] */
    public final void lambda$resetByDataClear$0() {
        try {
            getUserContext().getContentResolver().call(authority, "resetData()", "", (Bundle) null);
        } catch (Exception e) {
            Log.e("AS.VolumeMonitor", "resetData() " + e.getMessage());
        }
    }

    public final Context getUserContext() {
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, new UserHandle(ActivityManager.getCurrentUser()));
        } catch (PackageManager.NameNotFoundException unused) {
            return this.mContext;
        }
    }
}
