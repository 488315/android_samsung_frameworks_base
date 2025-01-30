package com.android.systemui.media;

import android.R;
import android.app.INotificationPlayerOnCompletionListener;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
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
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.sec.ims.presence.ServiceTuple;
import java.io.IOException;
import java.lang.Thread;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CmdThread extends Thread {
        public CmdThread() {
            super("NotificationPlayer-" + NotificationPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Command command;
            NotificationPlayer notificationPlayer;
            MediaPlayer mediaPlayer;
            while (true) {
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    command = (Command) NotificationPlayer.this.mCmdQueue.removeFirst();
                }
                int i = command.code;
                if (i == 1) {
                    NotificationPlayer.m1581$$Nest$mstartSound(NotificationPlayer.this, command);
                } else if (i == 2) {
                    synchronized (NotificationPlayer.this.mPlayerLock) {
                        notificationPlayer = NotificationPlayer.this;
                        mediaPlayer = notificationPlayer.mPlayer;
                        notificationPlayer.mPlayer = null;
                    }
                    if (mediaPlayer != null) {
                        long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
                        if (uptimeMillis > 1000) {
                            Log.w(NotificationPlayer.this.mTag, "Notification stop delayed by " + uptimeMillis + "msecs");
                        }
                        try {
                            mediaPlayer.stop();
                        } catch (Exception unused) {
                        }
                        mediaPlayer.release();
                        synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                            AudioManager audioManager = NotificationPlayer.this.mAudioManagerWithAudioFocus;
                            if (audioManager != null) {
                                audioManager.abandonAudioFocus(null);
                                NotificationPlayer.this.mAudioManagerWithAudioFocus = null;
                            }
                        }
                        synchronized (NotificationPlayer.this.mCompletionHandlingLock) {
                            Looper looper = NotificationPlayer.this.mLooper;
                            if (looper != null && looper.getThread().getState() != Thread.State.TERMINATED) {
                                NotificationPlayer.this.mLooper.quit();
                            }
                        }
                    } else {
                        Log.w(notificationPlayer.mTag, "STOP command without a player");
                    }
                }
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    if (NotificationPlayer.this.mCmdQueue.size() == 0) {
                        break;
                    }
                }
            }
            NotificationPlayer notificationPlayer2 = NotificationPlayer.this;
            notificationPlayer2.mThread = null;
            PowerManager.WakeLock wakeLock = notificationPlayer2.mWakeLock;
            if (wakeLock != null) {
                wakeLock.release();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Command {
        public AudioAttributes attributes;
        public int code;
        public Context context;
        public boolean looping;
        public long requestTime;
        public Uri uri;

        private Command() {
        }

        public /* synthetic */ Command(int i) {
            this();
        }

        public final String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attributes=" + this.attributes + " uri=" + this.uri + " }";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CreationAndCompletionThread extends Thread {
        public final Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(15:3|4|5|(2:6|7)|(4:9|10|(1:12)|13)|(2:90|(3:98|164|109))(11:23|(3:25|(2:30|(1:32))|80)(11:81|(2:86|(12:88|34|(1:36)|37|38|39|40|41|238|(5:47|48|49|50|51)|54|55))|89|34|(0)|37|38|39|40|41|238)|79|34|(0)|37|38|39|40|41|238)|33|34|(0)|37|38|39|40|41|238) */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x01c3, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x01c4, code lost:
        
            android.util.Log.e(r10.this$0.mTag, "Exception while sleeping to sync notification playback with ducking", r0);
         */
        /* JADX WARN: Removed duplicated region for block: B:124:0x01fc A[Catch: all -> 0x027f, TryCatch #7 {, blocks: (B:4:0x000c, B:7:0x001c, B:10:0x0021, B:12:0x0027, B:13:0x003c, B:15:0x0049, B:17:0x0055, B:19:0x0065, B:21:0x0075, B:23:0x0085, B:25:0x0089, B:27:0x00a2, B:30:0x00b3, B:32:0x00c3, B:34:0x018e, B:36:0x01b0, B:37:0x01b3, B:39:0x01ba, B:40:0x01cd, B:41:0x0234, B:42:0x0238, B:47:0x0267, B:50:0x026c, B:51:0x026f, B:54:0x0272, B:55:0x0275, B:63:0x027b, B:78:0x01c4, B:80:0x00dc, B:81:0x00f5, B:83:0x010e, B:86:0x011f, B:88:0x012f, B:89:0x0139, B:90:0x0140, B:92:0x0146, B:94:0x014c, B:96:0x015a, B:98:0x0160, B:99:0x0164, B:113:0x018b, B:116:0x01d7, B:117:0x01db, B:119:0x01e1, B:124:0x01fc, B:125:0x01ef, B:128:0x0204, B:129:0x0224, B:135:0x022f, B:139:0x027e, B:131:0x0225, B:133:0x0229, B:134:0x022e, B:65:0x023b, B:67:0x0241, B:45:0x0264, B:72:0x024f, B:44:0x025e), top: B:3:0x000c, inners: #3, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x01b0 A[Catch: Exception -> 0x01d1, all -> 0x027f, TryCatch #6 {Exception -> 0x01d1, blocks: (B:10:0x0021, B:12:0x0027, B:13:0x003c, B:15:0x0049, B:17:0x0055, B:19:0x0065, B:21:0x0075, B:23:0x0085, B:25:0x0089, B:27:0x00a2, B:30:0x00b3, B:32:0x00c3, B:34:0x018e, B:36:0x01b0, B:37:0x01b3, B:39:0x01ba, B:40:0x01cd, B:78:0x01c4, B:80:0x00dc, B:81:0x00f5, B:83:0x010e, B:86:0x011f, B:88:0x012f, B:89:0x0139, B:90:0x0140, B:92:0x0146, B:94:0x014c, B:96:0x015a, B:98:0x0160, B:99:0x0164, B:113:0x018b), top: B:9:0x0021 }] */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0239  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            MediaPlayer mediaPlayer;
            boolean z;
            Uri uri;
            float f;
            Looper.prepare();
            NotificationPlayer.this.mLooper = Looper.myLooper();
            synchronized (this) {
                AudioManager audioManager = (AudioManager) this.mCmd.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                MediaPlayer mediaPlayer2 = null;
                boolean z2 = true;
                try {
                    mediaPlayer = new MediaPlayer();
                } catch (Exception e) {
                    e = e;
                    mediaPlayer = null;
                }
                try {
                    Command command = this.mCmd;
                    if (command.attributes == null) {
                        command.attributes = new AudioAttributes.Builder().setUsage(5).setContentType(4).build();
                    }
                    mediaPlayer.setAudioAttributes(this.mCmd.attributes);
                    uri = this.mCmd.uri;
                } catch (Exception e2) {
                    e = e2;
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    Command command2 = this.mCmd;
                    if (command2.uri != null) {
                        NotificationPlayer.this.getClass();
                        int defaultType = RingtoneManager.getDefaultType(command2.uri);
                        if (defaultType != -1 && RingtoneManager.getActualDefaultRingtoneUri(command2.context, defaultType) == null) {
                            z = false;
                            if (z) {
                                NotificationPlayer.m1580$$Nest$mplayFallbackRingtone(NotificationPlayer.this, this.mCmd);
                                synchronized (NotificationPlayer.this.mPlayerLock) {
                                }
                            }
                        }
                        z = true;
                        if (z) {
                        }
                    }
                    Log.w(NotificationPlayer.this.mTag, "error loading sound for " + this.mCmd.uri, e);
                    NotificationPlayer notificationPlayer = NotificationPlayer.this;
                    synchronized (notificationPlayer.mQueueAudioFocusLock) {
                        AudioManager audioManager2 = notificationPlayer.mAudioManagerWithAudioFocus;
                        if (audioManager2 != null) {
                            audioManager2.abandonAudioFocus(null);
                            notificationPlayer.mAudioManagerWithAudioFocus = null;
                        }
                    }
                    NotificationPlayer.this.notifyError();
                    synchronized (NotificationPlayer.this.mPlayerLock) {
                    }
                }
                if (uri == null || !(uri.toString().contains("ChargingStarted") || this.mCmd.uri.toString().contains("LowBattery") || this.mCmd.uri.toString().contains("Water_Protection") || this.mCmd.uri.toString().contains("ChargingStarted_Fast"))) {
                    Uri uri2 = this.mCmd.uri;
                    if (uri2 != null && uri2.getEncodedPath() != null && this.mCmd.uri.getEncodedPath().length() > 0 && !audioManager.isMusicActiveRemotely()) {
                        synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                            NotificationPlayer notificationPlayer2 = NotificationPlayer.this;
                            if (notificationPlayer2.mAudioManagerWithAudioFocus == null) {
                                Command command3 = this.mCmd;
                                int i = command3.looping ? 2 : 3;
                                notificationPlayer2.mNotificationRampTimeMs = audioManager.getFocusRampTimeMs(i, command3.attributes);
                                audioManager.requestAudioFocus(null, this.mCmd.attributes, i, 0);
                                NotificationPlayer.this.mAudioManagerWithAudioFocus = audioManager;
                            }
                        }
                    }
                } else {
                    if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
                        Log.i(NotificationPlayer.this.mTag, "Situation Feature on");
                        if (!this.mCmd.uri.toString().contains("ChargingStarted") && !this.mCmd.uri.toString().contains("ChargingStarted_Fast")) {
                            if (this.mCmd.uri.toString().contains("LowBattery")) {
                                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder(this.mCmd.attributes).semAddAudioTag("stv_low_battery").build());
                            }
                        }
                        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder(this.mCmd.attributes).semAddAudioTag("stv_charger_connection").build());
                    } else {
                        Log.i(NotificationPlayer.this.mTag, "Situation Feature off");
                        if (!this.mCmd.uri.toString().contains("ChargingStarted") && !this.mCmd.uri.toString().contains("ChargingStarted_Fast")) {
                            if (this.mCmd.uri.toString().contains("LowBattery")) {
                                f = audioManager.semGetSituationVolume(11, 0);
                                Command command4 = this.mCmd;
                                mediaPlayer.setDataSource(command4.context, command4.uri);
                                mediaPlayer.setLooping(this.mCmd.looping);
                                mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                                mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                                mediaPlayer.prepare();
                                if (f >= 0.0f) {
                                    mediaPlayer.setVolume(f, f);
                                }
                                mediaPlayer.setWakeMode(this.mCmd.context, 1);
                                Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                                mediaPlayer.start();
                                synchronized (NotificationPlayer.this.mPlayerLock) {
                                    if (mediaPlayer == null) {
                                        try {
                                            Command command5 = this.mCmd;
                                            if (command5.uri != null) {
                                                NotificationPlayer.this.getClass();
                                                int defaultType2 = RingtoneManager.getDefaultType(command5.uri);
                                                if (defaultType2 != -1) {
                                                    z2 = RingtoneManager.getActualDefaultRingtoneUri(command5.context, defaultType2) != null;
                                                }
                                                if (z2) {
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    NotificationPlayer notificationPlayer3 = NotificationPlayer.this;
                                    mediaPlayer2 = notificationPlayer3.mPlayer;
                                    notificationPlayer3.mPlayer = mediaPlayer;
                                }
                                if (mediaPlayer2 != null) {
                                    mediaPlayer2.pause();
                                    try {
                                        Thread.sleep(100L);
                                    } catch (InterruptedException unused) {
                                    }
                                    mediaPlayer2.release();
                                }
                                notify();
                            }
                        }
                        f = audioManager.semGetSituationVolume(16, 0);
                        Command command42 = this.mCmd;
                        mediaPlayer.setDataSource(command42.context, command42.uri);
                        mediaPlayer.setLooping(this.mCmd.looping);
                        mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                        mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                        mediaPlayer.prepare();
                        if (f >= 0.0f) {
                        }
                        mediaPlayer.setWakeMode(this.mCmd.context, 1);
                        Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                        mediaPlayer.start();
                        synchronized (NotificationPlayer.this.mPlayerLock) {
                        }
                    }
                    f = 1.0f;
                    Command command422 = this.mCmd;
                    mediaPlayer.setDataSource(command422.context, command422.uri);
                    mediaPlayer.setLooping(this.mCmd.looping);
                    mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                    mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                    mediaPlayer.prepare();
                    if (f >= 0.0f) {
                    }
                    mediaPlayer.setWakeMode(this.mCmd.context, 1);
                    Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                    mediaPlayer.start();
                    synchronized (NotificationPlayer.this.mPlayerLock) {
                    }
                }
                f = -1.0f;
                Command command4222 = this.mCmd;
                mediaPlayer.setDataSource(command4222.context, command4222.uri);
                mediaPlayer.setLooping(this.mCmd.looping);
                mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                mediaPlayer.prepare();
                if (f >= 0.0f) {
                }
                mediaPlayer.setWakeMode(this.mCmd.context, 1);
                Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                mediaPlayer.start();
                synchronized (NotificationPlayer.this.mPlayerLock) {
                }
            }
            Looper.loop();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x00da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mplayFallbackRingtone, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1580$$Nest$mplayFallbackRingtone(NotificationPlayer notificationPlayer, Command command) {
        AssetFileDescriptor assetFileDescriptor;
        Exception e;
        MediaPlayer mediaPlayer;
        notificationPlayer.getClass();
        MediaPlayer mediaPlayer2 = null;
        try {
            try {
                assetFileDescriptor = command.context.getResources().openRawResourceFd(R.raw.fallback_categories);
                if (assetFileDescriptor == null) {
                    Log.d(notificationPlayer.mTag, "played fallback ringtone");
                    synchronized (notificationPlayer.mPlayerLock) {
                        notificationPlayer.mPlayer = null;
                    }
                    if (assetFileDescriptor == null) {
                        return;
                    }
                } else {
                    try {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setAudioAttributes(command.attributes);
                            if (assetFileDescriptor.getDeclaredLength() < 0) {
                                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor());
                            } else {
                                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
                            }
                            mediaPlayer.setLooping(command.looping);
                            mediaPlayer.setOnCompletionListener(notificationPlayer);
                            mediaPlayer.setOnErrorListener(notificationPlayer);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            Log.d(notificationPlayer.mTag, "played fallback ringtone");
                            synchronized (notificationPlayer.mPlayerLock) {
                                notificationPlayer.mPlayer = mediaPlayer;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            Log.w(notificationPlayer.mTag, "error loading sound for " + command.uri, e);
                            synchronized (notificationPlayer.mQueueAudioFocusLock) {
                                AudioManager audioManager = notificationPlayer.mAudioManagerWithAudioFocus;
                                if (audioManager != null) {
                                    audioManager.abandonAudioFocus(null);
                                    notificationPlayer.mAudioManagerWithAudioFocus = null;
                                }
                            }
                            notificationPlayer.notifyError();
                            Log.d(notificationPlayer.mTag, "played fallback ringtone");
                            synchronized (notificationPlayer.mPlayerLock) {
                                notificationPlayer.mPlayer = mediaPlayer;
                            }
                            if (assetFileDescriptor == null) {
                                return;
                            }
                            assetFileDescriptor.close();
                        }
                    } catch (Exception e3) {
                        e = e3;
                        mediaPlayer = null;
                    } catch (Throwable th) {
                        th = th;
                        Log.d(notificationPlayer.mTag, "played fallback ringtone");
                        synchronized (notificationPlayer.mPlayerLock) {
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                mediaPlayer2 = mediaPlayer;
                Log.d(notificationPlayer.mTag, "played fallback ringtone");
                synchronized (notificationPlayer.mPlayerLock) {
                    notificationPlayer.mPlayer = mediaPlayer2;
                }
                if (assetFileDescriptor != null) {
                    try {
                        assetFileDescriptor.close();
                    } catch (IOException unused) {
                        Log.w(notificationPlayer.mTag, "AssetFileDescriptor Close error");
                    }
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            assetFileDescriptor = null;
            mediaPlayer = null;
        } catch (Throwable th3) {
            th = th3;
            assetFileDescriptor = null;
        }
        try {
            assetFileDescriptor.close();
        } catch (IOException unused2) {
            Log.w(notificationPlayer.mTag, "AssetFileDescriptor Close error");
        }
    }

    /* renamed from: -$$Nest$mstartSound, reason: not valid java name */
    public static void m1581$$Nest$mstartSound(NotificationPlayer notificationPlayer, Command command) {
        notificationPlayer.getClass();
        try {
            synchronized (notificationPlayer.mCompletionHandlingLock) {
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
                if (this.mCmdQueue.size() == 0) {
                    Looper looper = this.mLooper;
                    if (looper != null) {
                        looper.quit();
                    }
                    this.mCompletionThread = null;
                }
            }
        }
        synchronized (this.mPlayerLock) {
            if (mediaPlayer == this.mPlayer) {
                this.mPlayer = null;
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.e(this.mTag, SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("error ", i, " (extra=", i2, ") playing notification"));
        onCompletion(mediaPlayer);
        return true;
    }

    public final void play(Context context, Uri uri, boolean z, AudioAttributes audioAttributes) {
        Command command = new Command(0);
        command.requestTime = SystemClock.uptimeMillis();
        command.code = 1;
        command.context = context;
        command.uri = uri;
        command.looping = z;
        command.attributes = audioAttributes;
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
        }
    }
}
