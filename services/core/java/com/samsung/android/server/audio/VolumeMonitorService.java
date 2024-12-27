package com.samsung.android.server.audio;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.audio.PlaybackActivityMonitor;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.audiofx.SemVolumeMonitor;
import com.samsung.android.server.audio.utils.PlaybackUtils;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VolumeMonitorService {
    public static final Uri authority =
            Uri.parse("content://com.sec.android.app.volumemonitorprovider.VolumeMonitorProvider");
    public static VolumeMonitorService sInstance;
    public final Context mContext;
    public SemVolumeMonitor mSemVolumeMonitor;
    public final Object mStateLock = new Object();
    public VolumeMonitorThread mVolumeMonitorThread = null;
    public Handler mVolumeHandler = null;
    public int mBluetoothVolumeIndex = 0;
    public PlaybackActivityMonitor mPlaybackActivityMonitor = null;
    public boolean mEnabled = false;
    public boolean mAvrcpAbsVolSupported = false;
    public int mState = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VolumeMonitorThread extends Thread {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class VolumeMonitorHandler extends Handler {
            public VolumeMonitorHandler() {}

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    synchronized (VolumeMonitorService.this.mStateLock) {
                        try {
                            VolumeMonitorService volumeMonitorService = VolumeMonitorService.this;
                            if (volumeMonitorService.mState == 1) {
                                return;
                            }
                            volumeMonitorService.mState = 1;
                            Handler handler = volumeMonitorService.mVolumeHandler;
                            handler.sendMessageDelayed(handler.obtainMessage(1), 60000L);
                            return;
                        } finally {
                        }
                    }
                }
                if (i != 1) {
                    Log.e("AS.VolumeMonitor", "Not supported message");
                    return;
                }
                AudioExecutor.execute(new VolumeMonitorService$$ExternalSyntheticLambda0(1, this));
                synchronized (VolumeMonitorService.this.mStateLock) {
                    try {
                        VolumeMonitorService volumeMonitorService2 = VolumeMonitorService.this;
                        if (!volumeMonitorService2.mEnabled) {
                            volumeMonitorService2.mState = 0;
                            return;
                        }
                        if (!volumeMonitorService2.isMusicPlaying()) {
                            Log.d("AS.VolumeMonitor", "Music is stopped");
                            VolumeMonitorService.this.mState = 0;
                        } else {
                            Log.d("AS.VolumeMonitor", "Music is playing");
                            Handler handler2 = VolumeMonitorService.this.mVolumeHandler;
                            handler2.sendMessageDelayed(handler2.obtainMessage(1), 60000L);
                        }
                    } finally {
                    }
                }
            }
        }

        public VolumeMonitorThread() {
            super("VolumeMonitor");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (VolumeMonitorService.this) {
                VolumeMonitorService.this.mVolumeHandler = new VolumeMonitorHandler();
                VolumeMonitorService.this.notify();
            }
            Looper.loop();
        }
    }

    public VolumeMonitorService(Context context) {
        this.mContext = context;
    }

    public static synchronized VolumeMonitorService getInstance(Context context) {
        VolumeMonitorService volumeMonitorService;
        synchronized (VolumeMonitorService.class) {
            try {
                if (sInstance == null) {
                    sInstance = new VolumeMonitorService(context);
                }
                volumeMonitorService = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return volumeMonitorService;
    }

    public final Context getUserContext() {
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(
                    context.getPackageName(), 0, new UserHandle(ActivityManager.getCurrentUser()));
        } catch (PackageManager.NameNotFoundException unused) {
            return this.mContext;
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
        synchronized (this) {
            while (this.mVolumeHandler == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e("AS.VolumeMonitor", "Interrupted while waiting on volume handler.");
                }
            }
        }
        return true;
    }

    public final boolean isMusicPlaying() {
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackActivityMonitor;
        if (playbackActivityMonitor != null) {
            synchronized (playbackActivityMonitor.mPlayerLock) {
                try {
                    Iterator it = playbackActivityMonitor.mPlayers.values().iterator();
                    while (it.hasNext()) {
                        if (PlaybackUtils.isMusicActive((AudioPlaybackConfiguration) it.next())) {
                            return true;
                        }
                    }
                } finally {
                }
            }
        }
        return false;
    }

    public final void resetByDataClear() {
        synchronized (this) {
            try {
                SemVolumeMonitor semVolumeMonitor = this.mSemVolumeMonitor;
                if (semVolumeMonitor == null) {
                    return;
                }
                semVolumeMonitor.resetData();
                AudioExecutor.execute(new VolumeMonitorService$$ExternalSyntheticLambda0(0, this));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setVolumeMonitorOnOff(boolean z) {
        if (Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY && Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            z = true;
        }
        synchronized (this) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void triggerMonitoring() {
        synchronized (this) {
            try {
                if (this.mEnabled) {
                    if (this.mVolumeHandler.hasMessages(0)) {
                        return;
                    }
                    Handler handler = this.mVolumeHandler;
                    handler.sendMessage(handler.obtainMessage(0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
