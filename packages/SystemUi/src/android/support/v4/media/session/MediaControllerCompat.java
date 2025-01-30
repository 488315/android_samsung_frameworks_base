package android.support.v4.media.session;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioAttributesImplApi26;
import androidx.versionedparcelable.ParcelImpl;
import androidx.versionedparcelable.ParcelUtils;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaControllerCompat {
    public final MediaControllerImplApi21 mImpl;
    public final ConcurrentHashMap mRegisteredCallbacks = new ConcurrentHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaControllerImplApi21 {
        public final MediaController mControllerFwk;
        public final MediaSessionCompat.Token mSessionToken;
        public final Object mLock = new Object();
        public final List mPendingCallbacks = new ArrayList();
        public final HashMap mCallbackMap = new HashMap();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        class ExtraBinderRequestResultReceiver extends ResultReceiver {
            public final WeakReference mMediaControllerImpl;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21) {
                super(null);
                this.mMediaControllerImpl = new WeakReference(mediaControllerImplApi21);
            }

            /* JADX WARN: Removed duplicated region for block: B:20:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // android.os.ResultReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceiveResult(int i, Bundle bundle) {
                VersionedParcelable versionedParcelable;
                Bundle bundle2;
                MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerImplApi21) this.mMediaControllerImpl.get();
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return;
                }
                synchronized (mediaControllerImplApi21.mLock) {
                    MediaSessionCompat.Token token = mediaControllerImplApi21.mSessionToken;
                    IMediaSession asInterface = IMediaSession.Stub.asInterface(bundle.getBinder("android.support.v4.media.session.EXTRA_BINDER"));
                    synchronized (token.mLock) {
                        token.mExtraBinder = asInterface;
                    }
                    MediaSessionCompat.Token token2 = mediaControllerImplApi21.mSessionToken;
                    try {
                        bundle2 = (Bundle) bundle.getParcelable("android.support.v4.media.session.SESSION_TOKEN2");
                    } catch (RuntimeException unused) {
                    }
                    if (bundle2 == null) {
                        versionedParcelable = null;
                        synchronized (token2.mLock) {
                            token2.mSession2Token = versionedParcelable;
                        }
                        mediaControllerImplApi21.processPendingCallbacksLocked();
                    } else {
                        bundle2.setClassLoader(ParcelUtils.class.getClassLoader());
                        Parcelable parcelable = bundle2.getParcelable("a");
                        if (!(parcelable instanceof ParcelImpl)) {
                            throw new IllegalArgumentException("Invalid parcel");
                        }
                        versionedParcelable = ((ParcelImpl) parcelable).mParcel;
                        synchronized (token2.mLock) {
                        }
                    }
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ExtraCallback extends Callback.StubCompat {
            public ExtraCallback(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onExtrasChanged(Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onQueueChanged(List list) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onQueueTitleChanged(CharSequence charSequence) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onSessionDestroyed() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                throw new AssertionError();
            }
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) {
            this.mSessionToken = token;
            MediaController mediaController = new MediaController(context, (MediaSession.Token) token.mInner);
            this.mControllerFwk = mediaController;
            if (token.getExtraBinder() == null) {
                mediaController.sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this));
            }
        }

        public final TransportControls getTransportControls() {
            return new TransportControlsApi29(this.mControllerFwk.getTransportControls());
        }

        public final void processPendingCallbacksLocked() {
            MediaSessionCompat.Token token = this.mSessionToken;
            if (token.getExtraBinder() == null) {
                return;
            }
            ArrayList arrayList = (ArrayList) this.mPendingCallbacks;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Callback callback = (Callback) it.next();
                ExtraCallback extraCallback = new ExtraCallback(callback);
                this.mCallbackMap.put(callback, extraCallback);
                callback.mIControllerCallback = extraCallback;
                try {
                    token.getExtraBinder().registerCallbackListener(extraCallback);
                    callback.postToHandler(13, null, null);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                }
            }
            arrayList.clear();
        }

        public final void unregisterCallback(Callback callback) {
            this.mControllerFwk.unregisterCallback(callback.mCallbackFwk);
            synchronized (this.mLock) {
                if (this.mSessionToken.getExtraBinder() != null) {
                    try {
                        ExtraCallback extraCallback = (ExtraCallback) this.mCallbackMap.remove(callback);
                        if (extraCallback != null) {
                            callback.mIControllerCallback = null;
                            this.mSessionToken.getExtraBinder().unregisterCallbackListener(extraCallback);
                        }
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
                    }
                } else {
                    ((ArrayList) this.mPendingCallbacks).remove(callback);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaControllerImplApi29 extends MediaControllerImplApi21 {
        public MediaControllerImplApi29(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class TransportControls {
        public abstract void pause();

        public abstract void play();

        public abstract void stop();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TransportControlsApi21 extends TransportControls {
        public final MediaController.TransportControls mControlsFwk;

        public TransportControlsApi21(MediaController.TransportControls transportControls) {
            this.mControlsFwk = transportControls;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public final void pause() {
            this.mControlsFwk.pause();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public final void play() {
            this.mControlsFwk.play();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public final void stop() {
            this.mControlsFwk.stop();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(MediaController.TransportControls transportControls) {
            super(transportControls);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TransportControlsApi24 extends TransportControlsApi23 {
        public TransportControlsApi24(MediaController.TransportControls transportControls) {
            super(transportControls);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransportControlsApi29 extends TransportControlsApi24 {
        public TransportControlsApi29(MediaController.TransportControls transportControls) {
            super(transportControls);
        }
    }

    public MediaControllerCompat(Context context, MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.mImpl = new MediaControllerImplApi29(context, mediaSessionCompat.mImpl.getSessionToken());
    }

    public final void unregisterCallback(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (this.mRegisteredCallbacks.remove(callback) == null) {
            Log.w("MediaControllerCompat", "the callback has never been registered");
            return;
        }
        try {
            this.mImpl.unregisterCallback(callback);
        } finally {
            callback.setHandler(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PlaybackInfo {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            this(i, new AudioAttributesCompat(r0.build()), i3, i4, i5);
            AudioAttributesImplApi26.Builder builder = new AudioAttributesCompat.Builder().mBuilderImpl;
            builder.mFwkBuilder.setLegacyStreamType(i2);
        }

        public PlaybackInfo(int i, AudioAttributesCompat audioAttributesCompat, int i2, int i3, int i4) {
        }
    }

    public MediaControllerCompat(Context context, MediaSessionCompat.Token token) {
        if (token != null) {
            this.mImpl = new MediaControllerImplApi21(context, token);
            return;
        }
        throw new IllegalArgumentException("sessionToken must not be null");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback implements IBinder.DeathRecipient {
        public final MediaControllerCallbackApi21 mCallbackFwk = new MediaControllerCallbackApi21(this);
        public MessageHandler mHandler;
        public MediaControllerImplApi21.ExtraCallback mIControllerCallback;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MediaControllerCallbackApi21 extends MediaController.Callback {
            public final WeakReference mCallback;

            public MediaControllerCallbackApi21(Callback callback) {
                this.mCallback = new WeakReference(callback);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
                if (((Callback) this.mCallback.get()) != null) {
                    new PlaybackInfo(playbackInfo.getPlaybackType(), new AudioAttributesCompat(new AudioAttributesImplApi26(playbackInfo.getAudioAttributes())), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onExtrasChanged(Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
            }

            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(mediaMetadata));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback == null || callback.mIControllerCallback != null) {
                    return;
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(playbackState));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onQueueChanged(List list) {
                if (((Callback) this.mCallback.get()) != null) {
                    MediaSessionCompat.QueueItem.fromQueueItemList(list);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onQueueTitleChanged(CharSequence charSequence) {
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionEvent(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MessageHandler extends Handler {
            public boolean mRegistered;

            public MessageHandler(Looper looper) {
                super(looper);
                this.mRegistered = false;
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (this.mRegistered) {
                    switch (message.what) {
                        case 1:
                            MediaSessionCompat.ensureClassLoader(message.getData());
                            Callback callback = Callback.this;
                            callback.getClass();
                            break;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            break;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            break;
                        case 4:
                            Callback callback2 = Callback.this;
                            callback2.getClass();
                            break;
                        case 5:
                            Callback callback3 = Callback.this;
                            callback3.getClass();
                            break;
                        case 6:
                            Callback callback4 = Callback.this;
                            callback4.getClass();
                            break;
                        case 7:
                            MediaSessionCompat.ensureClassLoader((Bundle) message.obj);
                            Callback.this.getClass();
                            break;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            break;
                        case 9:
                            Callback callback5 = Callback.this;
                            ((Integer) message.obj).intValue();
                            callback5.getClass();
                            break;
                        case 11:
                            Callback callback6 = Callback.this;
                            ((Boolean) message.obj).booleanValue();
                            callback6.getClass();
                            break;
                        case 12:
                            Callback callback7 = Callback.this;
                            ((Integer) message.obj).intValue();
                            callback7.getClass();
                            break;
                        case 13:
                            Callback.this.getClass();
                            break;
                    }
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public class StubCompat extends IMediaControllerCallback.Stub {
            public final WeakReference mCallback;

            public StubCompat(Callback callback) {
                this.mCallback = new WeakReference(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(7, bundle, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(3, mediaMetadataCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List list) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(5, list, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(6, charSequence, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(8, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(4, parcelableVolumeInfo != null ? new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null, null);
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            postToHandler(8, null, null);
        }

        public final void postToHandler(int i, Object obj, Bundle bundle) {
            MessageHandler messageHandler = this.mHandler;
            if (messageHandler != null) {
                Message obtainMessage = messageHandler.obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
        }

        public final void setHandler(Handler handler) {
            if (handler != null) {
                MessageHandler messageHandler = new MessageHandler(handler.getLooper());
                this.mHandler = messageHandler;
                messageHandler.mRegistered = true;
            } else {
                MessageHandler messageHandler2 = this.mHandler;
                if (messageHandler2 != null) {
                    messageHandler2.mRegistered = false;
                    messageHandler2.removeCallbacksAndMessages(null);
                    this.mHandler = null;
                }
            }
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onSessionDestroyed() {
        }
    }
}
