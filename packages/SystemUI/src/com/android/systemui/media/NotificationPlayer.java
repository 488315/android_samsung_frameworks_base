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
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import java.lang.Thread;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CmdThread extends Thread {
        public CmdThread() {
            super("NotificationPlayer-" + NotificationPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Command command;
            while (true) {
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    command = (Command) NotificationPlayer.this.mCmdQueue.removeFirst();
                }
                try {
                    int i = command.code;
                    if (i == 1) {
                        NotificationPlayer.m1971$$Nest$mstartSound(NotificationPlayer.this, command);
                    } else if (i == 2) {
                        NotificationPlayer.m1972$$Nest$mstopSound(NotificationPlayer.this, command);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Command {
        public AudioAttributes attributes;
        public int code;
        public Context context;
        public boolean looping;
        public long requestTime;
        public Uri uri;
        public float volume;

        private Command() {
        }

        public final String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attributes=" + this.attributes + " volume=" + this.volume + " uri=" + this.uri + " }";
        }

        public /* synthetic */ Command(int i) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CreationAndCompletionThread extends Thread {
        public final Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /* JADX WARN: Code restructure failed: missing block: B:106:0x003b, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:109:0x018c, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x0108, code lost:
        
            if (android.media.RingtoneManager.getActualDefaultRingtoneUri(r4.context, r5) != null) goto L57;
         */
        /* JADX WARN: Removed duplicated region for block: B:46:0x014a  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 397
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.CreationAndCompletionThread.run():void");
        }
    }

    /* renamed from: -$$Nest$misNotBatteryPowerSound, reason: not valid java name */
    public static boolean m1969$$Nest$misNotBatteryPowerSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        return (command.uri.toString().contains("ChargingStarted") || command.uri.toString().contains("LowBattery") || command.uri.toString().contains("Water_Protection") || command.uri.toString().contains("ChargingStarted_Fast")) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x00db A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mplayFallbackRingtone, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1970$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer r10, com.android.systemui.media.NotificationPlayer.Command r11) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.m1970$$Nest$mplayFallbackRingtone(com.android.systemui.media.NotificationPlayer, com.android.systemui.media.NotificationPlayer$Command):void");
    }

    /* renamed from: -$$Nest$mstartSound, reason: not valid java name */
    public static void m1971$$Nest$mstartSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        try {
            synchronized (notificationPlayer.mCompletionHandlingLock) {
                try {
                    Looper looper = notificationPlayer.mLooper;
                    if (looper != null && looper.getThread().getState() != Thread.State.TERMINATED) {
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
    public static void m1972$$Nest$mstopSound(NotificationPlayer notificationPlayer, Command command) {
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
        try {
            mediaPlayer.release();
        } catch (Exception e2) {
            Log.w(notificationPlayer.mTag, "Failed to release MediaPlayer", e2);
        }
        synchronized (notificationPlayer.mQueueAudioFocusLock) {
            AudioManager audioManager = notificationPlayer.mAudioManagerWithAudioFocus;
            if (audioManager != null) {
                try {
                    audioManager.abandonAudioFocus(null);
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
            AudioManager audioManager = this.mAudioManagerWithAudioFocus;
            if (audioManager != null) {
                audioManager.abandonAudioFocus(null);
                this.mAudioManagerWithAudioFocus = null;
            }
            try {
                Iterator it = this.mOnCompletionListener.iterator();
                while (it.hasNext()) {
                    ((INotificationPlayerOnCompletionListener) it.next()).onCompletion();
                }
            } catch (RemoteException unused) {
            }
        }
        synchronized (this.mCmdQueue) {
            synchronized (this.mCompletionHandlingLock) {
                try {
                    if (this.mCmdQueue.size() == 0) {
                        Looper looper = this.mLooper;
                        if (looper != null) {
                            looper.quit();
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
            mediaPlayer.release();
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.e(this.mTag, HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, i2, "error ", " (extra=", ") playing notification"));
        onCompletion(mediaPlayer);
        return true;
    }

    public final void play(Context context, Uri uri, boolean z, AudioAttributes audioAttributes, float f) {
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
