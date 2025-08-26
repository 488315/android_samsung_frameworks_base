package com.android.systemui.media;

import android.app.INotificationPlayerOnCompletionListener;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.lang.Thread;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/* loaded from: classes2.dex */
public class NotificationPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    public AudioManager mAudioManagerWithAudioFocus;
    public CreationAndCompletionThread mCompletionThread;
    public Looper mLooper;
    public MediaPlayer mPlayer;
    public final String mTag;
    public CmdThread mThread;
    public PowerManager.WakeLock mWakeLock;
    public final LinkedList mCmdQueue = new LinkedList();
    public final Object mCompletionHandlingLock = new Object();
    public final Object mPlayerLock = new Object();
    public final Object mQueueAudioFocusLock = new Object();
    public int mNotificationRampTimeMs = 0;
    public int mState = 2;
    public final IBinder mBinder = new Binder();
    public final Vector mOnCompletionListener = new Vector();

    public final class CmdThread extends Thread {
        public CmdThread() {
            super("NotificationPlayer-" + NotificationPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Command command;
            while (true) {
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    Log.d(NotificationPlayer.this.mTag, "RemoveFirst");
                    command = (Command) NotificationPlayer.this.mCmdQueue.removeFirst();
                }
                try {
                    int i = command.code;
                    if (i == 1) {
                        Log.d(NotificationPlayer.this.mTag, "PLAY");
                        NotificationPlayer.m2625$$Nest$mstartSound(NotificationPlayer.this, command);
                    } else if (i == 2) {
                        Log.d(NotificationPlayer.this.mTag, "STOP");
                        NotificationPlayer.m2626$$Nest$mstopSound(NotificationPlayer.this, command);
                    }
                    synchronized (NotificationPlayer.this.mCmdQueue) {
                        try {
                            if (NotificationPlayer.this.mCmdQueue.size() == 0) {
                                break;
                            }
                        } finally {
                        }
                    }
                } catch (Throwable th) {
                    synchronized (NotificationPlayer.this.mCmdQueue) {
                        try {
                            if (NotificationPlayer.this.mCmdQueue.size() != 0) {
                                throw th;
                            }
                            NotificationPlayer notificationPlayer = NotificationPlayer.this;
                            notificationPlayer.mThread = null;
                            PowerManager.WakeLock wakeLock = notificationPlayer.mWakeLock;
                            if (wakeLock != null) {
                                wakeLock.release();
                            }
                            return;
                        } finally {
                        }
                    }
                }
            }
            NotificationPlayer notificationPlayer2 = NotificationPlayer.this;
            notificationPlayer2.mThread = null;
            PowerManager.WakeLock wakeLock2 = notificationPlayer2.mWakeLock;
            if (wakeLock2 != null) {
                wakeLock2.release();
            }
        }
    }

    public final class Command {
        public AudioAttributes attributes;
        public int code;
        public Context context;
        public boolean looping;
        public long requestTime;
        public Uri uri;
        public float volume;

        public /* synthetic */ Command(int i) {
            this();
        }

        public final String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attributes=" + this.attributes + " volume=" + this.volume + " uri=" + this.uri + " }";
        }

        private Command() {
        }
    }

    public final class CreationAndCompletionThread extends Thread {
        public final Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x005a, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:93:0x0202, code lost:
        
            throw r0;
         */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0177 A[Catch: all -> 0x005a, TryCatch #6 {all -> 0x005a, all -> 0x01c5, blocks: (B:4:0x0029, B:6:0x0039, B:7:0x003e, B:9:0x0044, B:14:0x0060, B:16:0x0091, B:18:0x0099, B:20:0x00a3, B:22:0x00b1, B:24:0x00b7, B:25:0x00bb, B:39:0x00f4, B:40:0x00f5, B:41:0x0118, B:45:0x012b, B:63:0x019d, B:64:0x01a1, B:82:0x01d0, B:84:0x01f1, B:85:0x01f4, B:86:0x01f7, B:87:0x01fa, B:91:0x0200, B:44:0x0122, B:50:0x014a, B:51:0x014e, B:53:0x0154, B:61:0x016f, B:56:0x0162, B:62:0x0177, B:66:0x01a4, B:68:0x01aa, B:80:0x01cd, B:71:0x01b8, B:79:0x01c7), top: B:106:0x0029 }] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x01a2  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x01c7 A[Catch: all -> 0x01c5, Merged into TryCatch #6 {all -> 0x005a, all -> 0x01c5, blocks: (B:4:0x0029, B:6:0x0039, B:7:0x003e, B:9:0x0044, B:14:0x0060, B:16:0x0091, B:18:0x0099, B:20:0x00a3, B:22:0x00b1, B:24:0x00b7, B:25:0x00bb, B:39:0x00f4, B:40:0x00f5, B:41:0x0118, B:45:0x012b, B:63:0x019d, B:64:0x01a1, B:82:0x01d0, B:84:0x01f1, B:85:0x01f4, B:86:0x01f7, B:87:0x01fa, B:91:0x0200, B:44:0x0122, B:50:0x014a, B:51:0x014e, B:53:0x0154, B:61:0x016f, B:56:0x0162, B:62:0x0177, B:66:0x01a4, B:68:0x01aa, B:80:0x01cd, B:71:0x01b8, B:79:0x01c7), top: B:106:0x0029 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            MediaPlayer mediaPlayer;
            Looper.prepare();
            NotificationPlayer.this.mLooper = Looper.myLooper();
            Log.d(NotificationPlayer.this.mTag, "in run: new looper " + NotificationPlayer.this.mLooper);
            synchronized (this) {
                try {
                    AudioManager audioManager = (AudioManager) this.mCmd.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                    boolean z = true;
                    MediaPlayer mediaPlayer2 = null;
                    try {
                        mediaPlayer = new MediaPlayer();
                        try {
                            Command command = this.mCmd;
                            if (command.attributes == null) {
                                command.attributes = new AudioAttributes.Builder().setUsage(5).setContentType(4).build();
                            }
                            mediaPlayer.setAudioAttributes(this.mCmd.attributes);
                            Command command2 = this.mCmd;
                            mediaPlayer.setDataSource(command2.context, command2.uri);
                            mediaPlayer.setLooping(this.mCmd.looping);
                            mediaPlayer.setVolume(this.mCmd.volume);
                            mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                            mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                            mediaPlayer.prepare();
                            Command command3 = this.mCmd;
                            if (command3.uri != null && NotificationPlayer.m2623$$Nest$misNotBatteryPowerSound(NotificationPlayer.this, command3) && this.mCmd.uri.getEncodedPath() != null && this.mCmd.uri.getEncodedPath().length() > 0 && !audioManager.isMusicActiveRemotely()) {
                                synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                                    try {
                                        NotificationPlayer notificationPlayer = NotificationPlayer.this;
                                        if (notificationPlayer.mAudioManagerWithAudioFocus == null) {
                                            Log.d(notificationPlayer.mTag, "requesting AudioFocus");
                                            Command command4 = this.mCmd;
                                            int i = command4.looping ? 2 : 3;
                                            NotificationPlayer.this.mNotificationRampTimeMs = audioManager.getFocusRampTimeMs(i, command4.attributes);
                                            audioManager.requestAudioFocus(null, this.mCmd.attributes, i, 0);
                                            NotificationPlayer.this.mAudioManagerWithAudioFocus = audioManager;
                                        } else {
                                            Log.d(notificationPlayer.mTag, "AudioFocus was previously requested");
                                        }
                                    } finally {
                                    }
                                }
                            }
                            mediaPlayer.setWakeMode(this.mCmd.context, 1);
                            Log.d(NotificationPlayer.this.mTag, "notification will be delayed by " + NotificationPlayer.this.mNotificationRampTimeMs + "ms");
                            try {
                                Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                            } catch (InterruptedException e) {
                                Log.e(NotificationPlayer.this.mTag, "Exception while sleeping to sync notification playback with ducking", e);
                            }
                            mediaPlayer.start();
                            Log.d(NotificationPlayer.this.mTag, "player.start piid:" + mediaPlayer.getPlayerIId());
                        } catch (Exception e2) {
                            e = e2;
                            if (mediaPlayer != null) {
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                            Command command5 = this.mCmd;
                            if (command5.uri != null) {
                                NotificationPlayer.this.getClass();
                                int defaultType = RingtoneManager.getDefaultType(command5.uri);
                                if (defaultType == -1 || RingtoneManager.getActualDefaultRingtoneUri(command5.context, defaultType) != null) {
                                    NotificationPlayer.m2624$$Nest$mplayFallbackRingtone(NotificationPlayer.this, this.mCmd);
                                } else {
                                    Log.w(NotificationPlayer.this.mTag, "error loading sound for " + this.mCmd.uri, e);
                                    NotificationPlayer.this.abandonAudioFocusAfterError();
                                    NotificationPlayer.this.notifyError();
                                }
                            }
                            synchronized (NotificationPlayer.this.mPlayerLock) {
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        mediaPlayer = null;
                    }
                    synchronized (NotificationPlayer.this.mPlayerLock) {
                        if (mediaPlayer == null) {
                            Command command6 = this.mCmd;
                            if (command6.uri != null) {
                                NotificationPlayer.this.getClass();
                                int defaultType2 = RingtoneManager.getDefaultType(command6.uri);
                                if (defaultType2 != -1 && RingtoneManager.getActualDefaultRingtoneUri(command6.context, defaultType2) == null) {
                                    z = false;
                                }
                                if (!z) {
                                    NotificationPlayer notificationPlayer2 = NotificationPlayer.this;
                                    mediaPlayer2 = notificationPlayer2.mPlayer;
                                    notificationPlayer2.mPlayer = mediaPlayer;
                                }
                            }
                        }
                    }
                    if (mediaPlayer2 != null) {
                        Log.d(NotificationPlayer.this.mTag, "mp.pause+release piid:" + mediaPlayer2.getPlayerIId());
                        mediaPlayer2.pause();
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException unused) {
                        }
                        mediaPlayer2.release();
                    }
                    notify();
                } finally {
                }
            }
            Looper.loop();
        }
    }

    /* renamed from: -$$Nest$misNotBatteryPowerSound, reason: not valid java name */
    public static boolean m2623$$Nest$misNotBatteryPowerSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        return (command.uri.toString().contains("ChargingStarted") || command.uri.toString().contains("LowBattery") || command.uri.toString().contains("Water_Protection") || command.uri.toString().contains("ChargingStarted_Fast")) ? false : true;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:12:0x0028
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.lang.String] */
    /* renamed from: -$$Nest$mplayFallbackRingtone, reason: not valid java name */
    public static void m2624$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer r11, com.android.systemui.media.NotificationPlayer.Command r12) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.m2624$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer, com.android.systemui.media.NotificationPlayer$Command):void");
    }

    /* renamed from: -$$Nest$mstartSound, reason: not valid java name */
    public static void m2625$$Nest$mstartSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        try {
            Log.d(notificationPlayer.mTag, "startSound()");
            synchronized (notificationPlayer.mCompletionHandlingLock) {
                try {
                    Looper looper = notificationPlayer.mLooper;
                    if (looper != null && looper.getThread().getState() != Thread.State.TERMINATED) {
                        Log.d(notificationPlayer.mTag, "in startSound quitting looper " + notificationPlayer.mLooper);
                        notificationPlayer.mLooper.quit();
                    }
                    CreationAndCompletionThread creationAndCompletionThread = notificationPlayer.new CreationAndCompletionThread(command);
                    notificationPlayer.mCompletionThread = creationAndCompletionThread;
                    synchronized (creationAndCompletionThread) {
                        notificationPlayer.mCompletionThread.start();
                        notificationPlayer.mCompletionThread.wait();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            long jUptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
            if (jUptimeMillis > 1000) {
                Log.w(notificationPlayer.mTag, "Notification sound delayed by " + jUptimeMillis + "msecs");
            }
        } catch (Exception e) {
            Log.w(notificationPlayer.mTag, "error loading sound for " + command.uri, e);
            notificationPlayer.notifyError();
        }
    }

    /* renamed from: -$$Nest$mstopSound, reason: not valid java name */
    public static void m2626$$Nest$mstopSound(NotificationPlayer notificationPlayer, Command command) throws IllegalStateException {
        MediaPlayer mediaPlayer;
        synchronized (notificationPlayer.mPlayerLock) {
            mediaPlayer = notificationPlayer.mPlayer;
            notificationPlayer.mPlayer = null;
        }
        if (mediaPlayer == null) {
            Log.w(notificationPlayer.mTag, "STOP command without a player");
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
        if (jUptimeMillis > 1000) {
            Log.w(notificationPlayer.mTag, "Notification stop delayed by " + jUptimeMillis + "msecs");
        }
        try {
            mediaPlayer.stop();
        } catch (Exception e) {
            Log.w(notificationPlayer.mTag, "Failed to stop MediaPlayer", e);
        }
        Log.i(notificationPlayer.mTag, "About to release MediaPlayer piid:" + mediaPlayer.getPlayerIId() + " due to notif cancelled");
        try {
            mediaPlayer.release();
        } catch (Exception e2) {
            Log.w(notificationPlayer.mTag, "Failed to release MediaPlayer", e2);
        }
        synchronized (notificationPlayer.mQueueAudioFocusLock) {
            if (notificationPlayer.mAudioManagerWithAudioFocus != null) {
                Log.d(notificationPlayer.mTag, "in STOP: abandonning AudioFocus");
                try {
                    notificationPlayer.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                } catch (Exception e3) {
                    Log.w(notificationPlayer.mTag, "Failed to abandon audio focus", e3);
                }
                notificationPlayer.mAudioManagerWithAudioFocus = null;
            }
        }
        synchronized (notificationPlayer.mCompletionHandlingLock) {
            try {
                Looper looper = notificationPlayer.mLooper;
                if (looper != null && looper.getThread().getState() != Thread.State.TERMINATED) {
                    Log.d(notificationPlayer.mTag, "in STOP: quitting looper " + notificationPlayer.mLooper);
                    notificationPlayer.mLooper.quit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public NotificationPlayer(String str) {
        if (str != null) {
            this.mTag = str;
        } else {
            this.mTag = "NotificationPlayer";
        }
    }

    public final void abandonAudioFocusAfterError() {
        synchronized (this.mQueueAudioFocusLock) {
            try {
                if (this.mAudioManagerWithAudioFocus != null) {
                    Log.d(this.mTag, "abandoning focus after playback error");
                    this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                    this.mAudioManagerWithAudioFocus = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyError() {
        try {
            Iterator it = this.mOnCompletionListener.iterator();
            while (it.hasNext()) {
                INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener = (INotificationPlayerOnCompletionListener) it.next();
                Log.d(this.mTag, "error while playSound : unRegister EasyMute");
                iNotificationPlayerOnCompletionListener.onCompletion();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this.mQueueAudioFocusLock) {
            try {
                if (this.mAudioManagerWithAudioFocus != null) {
                    Log.d(this.mTag, "onCompletion() abandoning AudioFocus");
                    this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                    this.mAudioManagerWithAudioFocus = null;
                } else {
                    Log.d(this.mTag, "onCompletion() no need to abandon AudioFocus");
                }
                try {
                    Iterator it = this.mOnCompletionListener.iterator();
                    while (it.hasNext()) {
                        ((INotificationPlayerOnCompletionListener) it.next()).onCompletion();
                    }
                } catch (RemoteException unused) {
                }
            } finally {
            }
        }
        synchronized (this.mCmdQueue) {
            synchronized (this.mCompletionHandlingLock) {
                try {
                    Log.d(this.mTag, "onCompletion queue size=" + this.mCmdQueue.size());
                    if (this.mCmdQueue.size() == 0) {
                        if (this.mLooper != null) {
                            Log.d(this.mTag, "in onCompletion quitting looper " + this.mLooper);
                            this.mLooper.quit();
                        }
                        this.mCompletionThread = null;
                    }
                } finally {
                }
            }
        }
        synchronized (this.mPlayerLock) {
            try {
                if (mediaPlayer == this.mPlayer) {
                    this.mPlayer = null;
                }
            } finally {
            }
        }
        if (mediaPlayer != null) {
            Log.i("NotificationPlayer", "About to release MediaPlayer piid:" + mediaPlayer.getPlayerIId() + " due to onCompletion");
            mediaPlayer.release();
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.e(this.mTag, MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "error ", " (extra=", ") playing notification"));
        onCompletion(mediaPlayer);
        return true;
    }

    public final void play(Context context, Uri uri, boolean z, AudioAttributes audioAttributes, float f) {
        Log.d(this.mTag, "play uri=" + uri.toString());
        Command command = new Command(0);
        command.requestTime = SystemClock.uptimeMillis();
        command.code = 1;
        command.context = context;
        command.uri = uri;
        command.looping = z;
        command.attributes = audioAttributes;
        command.volume = f;
        synchronized (this.mCmdQueue) {
            this.mCmdQueue.add(command);
            if (this.mThread == null) {
                PowerManager.WakeLock wakeLock = this.mWakeLock;
                if (wakeLock != null) {
                    wakeLock.acquire();
                }
                CmdThread cmdThread = new CmdThread();
                this.mThread = cmdThread;
                cmdThread.start();
            }
            this.mState = 1;
        }
    }

    public final void stop() {
        Log.d(this.mTag, "stop");
        synchronized (this.mCmdQueue) {
            try {
                if (this.mState != 2) {
                    Command command = new Command(0);
                    command.requestTime = SystemClock.uptimeMillis();
                    command.code = 2;
                    this.mCmdQueue.add(command);
                    if (this.mThread == null) {
                        PowerManager.WakeLock wakeLock = this.mWakeLock;
                        if (wakeLock != null) {
                            wakeLock.acquire();
                        }
                        CmdThread cmdThread = new CmdThread();
                        this.mThread = cmdThread;
                        cmdThread.start();
                    }
                    this.mState = 2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
