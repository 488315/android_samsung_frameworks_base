package com.android.systemui.media;

import android.app.INotificationPlayerOnCompletionListener;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import java.lang.Thread;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        NotificationPlayer.m2608$$Nest$mstartSound(NotificationPlayer.this, command);
                    } else if (i == 2) {
                        Log.d(NotificationPlayer.this.mTag, "STOP");
                        NotificationPlayer.m2609$$Nest$mstopSound(NotificationPlayer.this, command);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CreationAndCompletionThread extends Thread {
        public final Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /* JADX WARN: Code restructure failed: missing block: B:101:0x005a, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:104:0x0202, code lost:
        
            throw r0;
         */
        /* JADX WARN: Removed duplicated region for block: B:47:0x01a2  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x016f A[Catch: all -> 0x005a, TryCatch #6 {all -> 0x005a, all -> 0x01c5, blocks: (B:4:0x0029, B:7:0x0039, B:9:0x003e, B:11:0x0044, B:12:0x0060, B:14:0x0091, B:16:0x0099, B:18:0x00a3, B:20:0x00b1, B:22:0x00b7, B:23:0x00bb, B:39:0x00f4, B:41:0x00f5, B:43:0x0118, B:44:0x012b, B:45:0x019d, B:46:0x01a1, B:51:0x01d0, B:54:0x01f1, B:55:0x01f4, B:58:0x01f7, B:59:0x01fa, B:66:0x0200, B:81:0x0122, B:85:0x014a, B:86:0x014e, B:88:0x0154, B:93:0x016f, B:94:0x0162, B:97:0x0177, B:68:0x01a4, B:70:0x01aa, B:49:0x01cd, B:75:0x01b8, B:48:0x01c7), top: B:3:0x0029 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 515
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.CreationAndCompletionThread.run():void");
        }
    }

    /* renamed from: -$$Nest$misNotBatteryPowerSound, reason: not valid java name */
    public static boolean m2606$$Nest$misNotBatteryPowerSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        return (command.uri.toString().contains("ChargingStarted") || command.uri.toString().contains("LowBattery") || command.uri.toString().contains("Water_Protection") || command.uri.toString().contains("ChargingStarted_Fast")) ? false : true;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:55:0x0028
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.lang.String] */
    /* renamed from: -$$Nest$mplayFallbackRingtone, reason: not valid java name */
    public static void m2607$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer r11, com.android.systemui.media.NotificationPlayer.Command r12) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.m2607$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer, com.android.systemui.media.NotificationPlayer$Command):void");
    }

    /* renamed from: -$$Nest$mstartSound, reason: not valid java name */
    public static void m2608$$Nest$mstartSound(NotificationPlayer notificationPlayer, Command command) {
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
            long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
            if (uptimeMillis > 1000) {
                Log.w(notificationPlayer.mTag, "Notification sound delayed by " + uptimeMillis + "msecs");
            }
        } catch (Exception e) {
            Log.w(notificationPlayer.mTag, "error loading sound for " + command.uri, e);
            notificationPlayer.notifyError();
        }
    }

    /* renamed from: -$$Nest$mstopSound, reason: not valid java name */
    public static void m2609$$Nest$mstopSound(NotificationPlayer notificationPlayer, Command command) {
        MediaPlayer mediaPlayer;
        synchronized (notificationPlayer.mPlayerLock) {
            mediaPlayer = notificationPlayer.mPlayer;
            notificationPlayer.mPlayer = null;
        }
        if (mediaPlayer == null) {
            Log.w(notificationPlayer.mTag, "STOP command without a player");
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
        if (uptimeMillis > 1000) {
            Log.w(notificationPlayer.mTag, "Notification stop delayed by " + uptimeMillis + "msecs");
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
