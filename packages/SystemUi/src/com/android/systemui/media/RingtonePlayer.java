package com.android.systemui.media;

import android.app.INotificationPlayerOnCompletionListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.IAudioService;
import android.media.IRingtonePlayer;
import android.media.Ringtone;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.sec.ims.presence.ServiceTuple;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RingtonePlayer implements CoreStartable {
    public final Context mContext;
    public final NotificationPlayer mAsyncPlayer = new NotificationPlayer("RingtonePlayer");
    public final HashMap mClients = new HashMap();
    public final C17801 mCallback = new IRingtonePlayer.Stub() { // from class: com.android.systemui.media.RingtonePlayer.1
        public final void fadeinRingtone(IBinder iBinder) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(iBinder);
            }
            if (client != null) {
                client.mRingtone.fadeinRingtone();
            }
        }

        public final void fadeoutRingtone(IBinder iBinder, int i, float f) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(iBinder);
            }
            if (client != null) {
                client.mRingtone.fadeoutRingtone(i, f);
            }
        }

        public final String getTitle(Uri uri) {
            return Ringtone.getTitle(RingtonePlayer.m1582$$Nest$mgetContextForUser(RingtonePlayer.this, Binder.getCallingUserHandle()), uri, false, false);
        }

        public final boolean isPlaying(IBinder iBinder) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(iBinder);
            }
            if (client != null) {
                return client.mRingtone.isPlaying();
            }
            return false;
        }

        public final ParcelFileDescriptor openRingtone(Uri uri) {
            ContentResolver contentResolver = RingtonePlayer.m1582$$Nest$mgetContextForUser(RingtonePlayer.this, Binder.getCallingUserHandle()).getContentResolver();
            if (uri.toString().startsWith(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString())) {
                Cursor query = contentResolver.query(uri, new String[]{"is_ringtone", "is_alarm", "is_notification"}, null, null, null);
                try {
                    if (query.moveToFirst() && (query.getInt(0) != 0 || query.getInt(1) != 0 || query.getInt(2) != 0)) {
                        try {
                            ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                            query.close();
                            return openFileDescriptor;
                        } catch (IOException e) {
                            throw new SecurityException(e);
                        }
                    }
                    query.close();
                } catch (Throwable th) {
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            throw new SecurityException("Uri is not ringtone, alarm, or notification: " + uri);
        }

        public final void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) {
            playWithVolumeShaping(iBinder, uri, audioAttributes, f, z, null);
        }

        public final void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Async playback only available from system UID.");
            }
            if (UserHandle.ALL.equals(userHandle)) {
                userHandle = UserHandle.SYSTEM;
            }
            RingtonePlayer ringtonePlayer = RingtonePlayer.this;
            ringtonePlayer.mAsyncPlayer.play(RingtonePlayer.m1582$$Nest$mgetContextForUser(ringtonePlayer, userHandle), uri, z, audioAttributes);
        }

        public final void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) {
            Client client;
            Client client2;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(iBinder);
            }
            if (client == null) {
                Ringtone ringtone = new Ringtone(RingtonePlayer.m1582$$Nest$mgetContextForUser(RingtonePlayer.this, Binder.getCallingUserHandle()), false);
                ringtone.setAudioAttributesField(audioAttributes);
                ringtone.setUri(uri, configuration);
                ringtone.createLocalMediaPlayer();
                synchronized (RingtonePlayer.this.mClients) {
                    client2 = (Client) RingtonePlayer.this.mClients.get(iBinder);
                    if (client2 == null) {
                        client2 = RingtonePlayer.this.new Client(iBinder, ringtone);
                        iBinder.linkToDeath(client2, 0);
                        RingtonePlayer.this.mClients.put(iBinder, client2);
                        ringtone = null;
                    }
                }
                if (ringtone != null) {
                    ringtone.stop();
                }
                client = client2;
            }
            client.mRingtone.setLooping(z);
            client.mRingtone.setVolume(f);
            client.mRingtone.play();
        }

        public final IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("setOnCompletionListener only available from system UID.");
            }
            NotificationPlayer notificationPlayer = RingtonePlayer.this.mAsyncPlayer;
            notificationPlayer.mOnCompletionListener.addElement(iNotificationPlayerOnCompletionListener);
            return notificationPlayer.mBinder;
        }

        public final void setPlaybackProperties(IBinder iBinder, float f, boolean z, boolean z2) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.get(iBinder);
            }
            if (client != null) {
                client.mRingtone.setVolume(f);
                client.mRingtone.setLooping(z);
                client.mRingtone.setHapticGeneratorEnabled(z2);
            }
        }

        public final void stop(IBinder iBinder) {
            Client client;
            synchronized (RingtonePlayer.this.mClients) {
                client = (Client) RingtonePlayer.this.mClients.remove(iBinder);
            }
            if (client != null) {
                client.mToken.unlinkToDeath(client, 0);
                client.mRingtone.stop();
            }
        }

        public final void stopAsync() {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Async playback only available from system UID.");
            }
            RingtonePlayer.this.mAsyncPlayer.stop();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Client implements IBinder.DeathRecipient {
        public final Ringtone mRingtone;
        public final IBinder mToken;

        public Client(IBinder iBinder, Ringtone ringtone) {
            this.mToken = iBinder;
            this.mRingtone = ringtone;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (RingtonePlayer.this.mClients) {
                RingtonePlayer.this.mClients.remove(this.mToken);
            }
            this.mRingtone.stop();
        }
    }

    /* renamed from: -$$Nest$mgetContextForUser, reason: not valid java name */
    public static Context m1582$$Nest$mgetContextForUser(RingtonePlayer ringtonePlayer, UserHandle userHandle) {
        ringtonePlayer.getClass();
        try {
            Context context = ringtonePlayer.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.media.RingtonePlayer$1] */
    public RingtonePlayer(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Clients:");
        printWriter.print("  mAsyncPlayer=");
        printWriter.println(this.mAsyncPlayer);
        NotificationPlayer notificationPlayer = this.mAsyncPlayer;
        if (notificationPlayer != null) {
            notificationPlayer.getClass();
            printWriter.println("com.android.systemui.media.NotificationPlayer:");
            printWriter.print("  mPlayer=");
            synchronized (notificationPlayer.mPlayerLock) {
                printWriter.println(notificationPlayer.mPlayer);
            }
        }
        synchronized (this.mClients) {
            for (Client client : this.mClients.values()) {
                printWriter.print("  mToken=");
                printWriter.print(client.mToken);
                printWriter.print(" mUri=");
                printWriter.println(client.mRingtone.getUri());
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        NotificationPlayer notificationPlayer = this.mAsyncPlayer;
        Context context = this.mContext;
        synchronized (notificationPlayer.mCmdQueue) {
            if (notificationPlayer.mWakeLock != null || notificationPlayer.mThread != null) {
                throw new RuntimeException("assertion failed mWakeLock=" + notificationPlayer.mWakeLock + " mThread=" + notificationPlayer.mThread);
            }
            notificationPlayer.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, notificationPlayer.mTag);
        }
        try {
            IAudioService.Stub.asInterface(ServiceManager.getService(ServiceTuple.MEDIA_CAP_AUDIO)).setRingtonePlayer(this.mCallback);
        } catch (RemoteException e) {
            Log.e("RingtonePlayer", "Problem registering RingtonePlayer: " + e);
        }
    }
}
