package android.media.session;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.ISessionCallback;
import android.media.session.ISessionController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaSession {

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int FLAG_EXCLUSIVE_GLOBAL_PRIORITY = 65536;

    @Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;

    @Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    public static final int FLAG_USE_A2DP = 536870912;
    public static final int INVALID_PID = -1;
    public static final int INVALID_UID = -1;

    @Deprecated(forRemoval = true, since = "13.0")
    public static final int SEM_FLAG_HANDLES_MEDIA_BUTTONS = 268435456;
    static final String TAG = "MediaSession";
    private boolean mActive;
    private final ISession mBinder;
    private CallbackMessageHandler mCallback;
    private final CallbackStub mCbStub;
    private Context mContext;
    private final MediaController mController;
    private final Object mLock;
    private final int mMaxBitmapSize;
    private PlaybackState mPlaybackState;
    private final Token mSessionToken;
    private VolumeProvider mVolumeProvider;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public MediaSession(Context context, String str) {
        this(context, str, null);
    }

    public MediaSession(Context context, String str, Bundle bundle) {
        this.mLock = new Object();
        this.mActive = false;
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("tag cannot be null or empty");
        }
        if (hasCustomParcelable(bundle)) {
            throw new IllegalArgumentException("sessionInfo shouldn't contain any custom parcelables");
        }
        this.mContext = context;
        this.mMaxBitmapSize = context.getResources().getDimensionPixelSize(R.dimen.config_mediaMetadataBitmapMaxSize);
        CallbackStub callbackStub = new CallbackStub(this);
        this.mCbStub = callbackStub;
        try {
            ISession createSession = ((MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE)).createSession(callbackStub, str, bundle);
            this.mBinder = createSession;
            Token token = new Token(Process.myUid(), createSession.getController());
            this.mSessionToken = token;
            this.mController = new MediaController(context, token);
        } catch (RemoteException e) {
            throw new RuntimeException("Remote error creating session.", e);
        }
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        synchronized (this.mLock) {
            CallbackMessageHandler callbackMessageHandler = this.mCallback;
            if (callbackMessageHandler != null) {
                callbackMessageHandler.mCallback.mSession = null;
                this.mCallback.removeCallbacksAndMessages(null);
            }
            if (callback == null) {
                this.mCallback = null;
                return;
            }
            Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
            callback.mSession = this;
            this.mCallback = new CallbackMessageHandler(looper, callback);
        }
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        try {
            this.mBinder.setLaunchPendingIntent(pendingIntent);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Failure in setLaunchPendingIntent.", e);
        }
    }

    @Deprecated
    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        try {
            this.mBinder.setMediaButtonReceiver(pendingIntent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setMediaButtonBroadcastReceiver(ComponentName componentName) {
        if (componentName != null) {
            try {
                if (!TextUtils.equals(componentName.getPackageName(), this.mContext.getPackageName())) {
                    throw new IllegalArgumentException("broadcastReceiver should belong to the same package as the context given when creating MediaSession.");
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mBinder.setMediaButtonBroadcastReceiver(componentName);
    }

    public void setFlags(int i) {
        try {
            this.mBinder.setFlags(i);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Failure in setFlags.", e);
        }
    }

    public void setPlaybackToLocal(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Attributes cannot be null for local playback.");
        }
        try {
            this.mBinder.setPlaybackToLocal(audioAttributes);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Failure in setPlaybackToLocal.", e);
        }
    }

    public void setPlaybackToRemote(VolumeProvider volumeProvider) {
        if (volumeProvider == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        synchronized (this.mLock) {
            this.mVolumeProvider = volumeProvider;
        }
        volumeProvider.setCallback(new VolumeProvider.Callback() { // from class: android.media.session.MediaSession.1
            @Override // android.media.VolumeProvider.Callback
            public void onVolumeChanged(VolumeProvider volumeProvider2) {
                MediaSession.this.notifyRemoteVolumeChanged(volumeProvider2);
            }
        });
        try {
            this.mBinder.setPlaybackToRemote(volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getVolumeControlId());
            this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
        } catch (RemoteException e) {
            Log.wtf(TAG, "Failure in setPlaybackToRemote.", e);
        }
    }

    public void setActive(boolean z) {
        if (this.mActive == z) {
            return;
        }
        try {
            this.mBinder.setActive(z);
            this.mActive = z;
        } catch (RemoteException e) {
            Log.wtf(TAG, "Failure in setActive.", e);
        }
    }

    public boolean isActive() {
        return this.mActive;
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        try {
            this.mBinder.sendEvent(str, bundle);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Error sending event", e);
        }
    }

    public void release() {
        setCallback(null);
        try {
            this.mBinder.destroySession();
        } catch (RemoteException e) {
            Log.wtf(TAG, "Error releasing session: ", e);
        }
    }

    public Token getSessionToken() {
        return this.mSessionToken;
    }

    public MediaController getController() {
        return this.mController;
    }

    public void setPlaybackState(PlaybackState playbackState) {
        this.mPlaybackState = playbackState;
        try {
            this.mBinder.setPlaybackState(playbackState);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Dead object in setPlaybackState.", e);
        }
    }

    public void setMetadata(MediaMetadata mediaMetadata) {
        int i;
        MediaDescription mediaDescription;
        if (mediaMetadata != null) {
            mediaMetadata = new MediaMetadata.Builder(mediaMetadata).setBitmapDimensionLimit(this.mMaxBitmapSize).build();
            r0 = mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_DURATION) ? mediaMetadata.getLong(MediaMetadata.METADATA_KEY_DURATION) : -1L;
            i = mediaMetadata.size();
            mediaDescription = mediaMetadata.getDescription();
        } else {
            i = 0;
            mediaDescription = null;
        }
        try {
            this.mBinder.setMetadata(mediaMetadata, r0, "size=" + i + ", description=" + mediaDescription);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Dead object in setPlaybackState.", e);
        }
    }

    public void setQueue(List<QueueItem> list) {
        try {
            if (list == null) {
                this.mBinder.resetQueue();
            } else {
                ParcelableListBinder.send(this.mBinder.getBinderForSetQueue(), list);
            }
        } catch (RemoteException e) {
            Log.wtf("Dead object in setQueue.", e);
        }
    }

    public void setQueueTitle(CharSequence charSequence) {
        try {
            this.mBinder.setQueueTitle(charSequence);
        } catch (RemoteException e) {
            Log.wtf("Dead object in setQueueTitle.", e);
        }
    }

    public void setRatingType(int i) {
        try {
            this.mBinder.setRatingType(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error in setRatingType.", e);
        }
    }

    public void setExtras(Bundle bundle) {
        try {
            this.mBinder.setExtras(bundle);
        } catch (RemoteException e) {
            Log.wtf("Dead object in setExtras.", e);
        }
    }

    public final MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
        CallbackMessageHandler callbackMessageHandler = this.mCallback;
        if (callbackMessageHandler == null || callbackMessageHandler.mCurrentControllerInfo == null) {
            throw new IllegalStateException("This should be called inside of MediaSession.Callback methods");
        }
        return this.mCallback.mCurrentControllerInfo;
    }

    public void notifyRemoteVolumeChanged(VolumeProvider volumeProvider) {
        synchronized (this.mLock) {
            if (volumeProvider != null) {
                if (volumeProvider == this.mVolumeProvider) {
                    try {
                        this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
                        return;
                    } catch (RemoteException e) {
                        Log.e(TAG, "Error in notifyVolumeChanged", e);
                        return;
                    }
                }
            }
            Log.w(TAG, "Received update from stale volume provider");
        }
    }

    public String getCallingPackage() {
        CallbackMessageHandler callbackMessageHandler = this.mCallback;
        if (callbackMessageHandler == null || callbackMessageHandler.mCurrentControllerInfo == null) {
            return null;
        }
        return this.mCallback.mCurrentControllerInfo.getPackageName();
    }

    static boolean hasCustomParcelable(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        Parcel parcel = null;
        try {
            try {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeBundle(bundle);
                    obtain.setDataPosition(0);
                    Bundle readBundle = obtain.readBundle(null);
                    Iterator<String> it = readBundle.keySet().iterator();
                    while (it.hasNext()) {
                        readBundle.get(it.next());
                    }
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    return false;
                } catch (BadParcelableException e) {
                    e = e;
                    parcel = obtain;
                    Log.d(TAG, "Custom parcelable in bundle.", e);
                    if (parcel == null) {
                        return true;
                    }
                    parcel.recycle();
                    return true;
                } catch (Throwable th) {
                    th = th;
                    parcel = obtain;
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    throw th;
                }
            } catch (BadParcelableException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    void dispatchPrepare(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 3, null, null);
    }

    void dispatchPrepareFromMediaId(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
        postToCallback(remoteUserInfo, 4, str, bundle);
    }

    void dispatchPrepareFromSearch(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
        postToCallback(remoteUserInfo, 5, str, bundle);
    }

    void dispatchPrepareFromUri(MediaSessionManager.RemoteUserInfo remoteUserInfo, Uri uri, Bundle bundle) {
        postToCallback(remoteUserInfo, 6, uri, bundle);
    }

    void dispatchPlay(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 7, null, null);
    }

    void dispatchPlayFromMediaId(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
        postToCallback(remoteUserInfo, 8, str, bundle);
    }

    void dispatchPlayFromSearch(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
        postToCallback(remoteUserInfo, 9, str, bundle);
    }

    void dispatchPlayFromUri(MediaSessionManager.RemoteUserInfo remoteUserInfo, Uri uri, Bundle bundle) {
        postToCallback(remoteUserInfo, 10, uri, bundle);
    }

    void dispatchSkipToItem(MediaSessionManager.RemoteUserInfo remoteUserInfo, long j) {
        postToCallback(remoteUserInfo, 11, Long.valueOf(j), null);
    }

    void dispatchPause(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 12, null, null);
    }

    void dispatchStop(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 13, null, null);
    }

    void dispatchNext(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 14, null, null);
    }

    void dispatchPrevious(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 15, null, null);
    }

    void dispatchFastForward(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 16, null, null);
    }

    void dispatchRewind(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 17, null, null);
    }

    void dispatchSeekTo(MediaSessionManager.RemoteUserInfo remoteUserInfo, long j) {
        postToCallback(remoteUserInfo, 18, Long.valueOf(j), null);
    }

    void dispatchRate(MediaSessionManager.RemoteUserInfo remoteUserInfo, Rating rating) {
        postToCallback(remoteUserInfo, 19, rating, null);
    }

    void dispatchSetPlaybackSpeed(MediaSessionManager.RemoteUserInfo remoteUserInfo, float f) {
        postToCallback(remoteUserInfo, 20, Float.valueOf(f), null);
    }

    void dispatchCustomAction(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
        postToCallback(remoteUserInfo, 21, str, bundle);
    }

    void dispatchMediaButton(MediaSessionManager.RemoteUserInfo remoteUserInfo, Intent intent) {
        postToCallback(remoteUserInfo, 2, intent, null);
    }

    void dispatchMediaButtonDelayed(MediaSessionManager.RemoteUserInfo remoteUserInfo, Intent intent, long j) {
        postToCallbackDelayed(remoteUserInfo, 24, intent, null, j);
    }

    void dispatchAdjustVolume(MediaSessionManager.RemoteUserInfo remoteUserInfo, int i) {
        postToCallback(remoteUserInfo, 22, Integer.valueOf(i), null);
    }

    void dispatchSetVolumeTo(MediaSessionManager.RemoteUserInfo remoteUserInfo, int i) {
        postToCallback(remoteUserInfo, 23, Integer.valueOf(i), null);
    }

    void dispatchCommand(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle, ResultReceiver resultReceiver) {
        postToCallback(remoteUserInfo, 1, new Command(str, bundle, resultReceiver), null);
    }

    void postToCallback(MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, Object obj, Bundle bundle) {
        postToCallbackDelayed(remoteUserInfo, i, obj, bundle, 0L);
    }

    void postToCallbackDelayed(MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, Object obj, Bundle bundle, long j) {
        synchronized (this.mLock) {
            CallbackMessageHandler callbackMessageHandler = this.mCallback;
            if (callbackMessageHandler != null) {
                callbackMessageHandler.post(remoteUserInfo, i, obj, bundle, j);
            }
        }
    }

    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() { // from class: android.media.session.MediaSession.Token.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Token createFromParcel(Parcel parcel) {
                return new Token(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Token[] newArray(int i) {
                return new Token[i];
            }
        };
        private final ISessionController mBinder;
        private final int mUid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Token(int i, ISessionController iSessionController) {
            this.mUid = i;
            this.mBinder = iSessionController;
        }

        Token(Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mBinder = ISessionController.Stub.asInterface(parcel.readStrongBinder());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mUid);
            parcel.writeStrongBinder(this.mBinder.asBinder());
        }

        public int hashCode() {
            int i = this.mUid * 31;
            ISessionController iSessionController = this.mBinder;
            return i + (iSessionController == null ? 0 : iSessionController.asBinder().hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Token token = (Token) obj;
            if (this.mUid != token.mUid) {
                return false;
            }
            ISessionController iSessionController = this.mBinder;
            if (iSessionController == null || token.mBinder == null) {
                return iSessionController == token.mBinder;
            }
            return Objects.equals(iSessionController.asBinder(), token.mBinder.asBinder());
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public int getUid() {
            return this.mUid;
        }

        public ISessionController getBinder() {
            return this.mBinder;
        }
    }

    public static abstract class Callback {
        private CallbackMessageHandler mHandler;
        private boolean mMediaPlayPauseKeyPending;
        private MediaSession mSession;

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetPlaybackSpeed(float f) {
        }

        public void onSetRating(Rating rating) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public boolean onMediaButtonEvent(Intent intent) {
            KeyEvent keyEvent;
            if (this.mSession != null && this.mHandler != null && Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction()) && (keyEvent = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent.class)) != null && keyEvent.getAction() == 0) {
                PlaybackState playbackState = this.mSession.mPlaybackState;
                long actions = playbackState == null ? 0L : playbackState.getActions();
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 79 || keyCode == 85) {
                    if (keyEvent.getRepeatCount() > 0) {
                        handleMediaPlayPauseKeySingleTapIfPending();
                    } else if (this.mMediaPlayPauseKeyPending) {
                        this.mHandler.removeMessages(24);
                        this.mMediaPlayPauseKeyPending = false;
                        if ((actions & 32) != 0) {
                            onSkipToNext();
                        }
                    } else {
                        this.mMediaPlayPauseKeyPending = true;
                        MediaSession mediaSession = this.mSession;
                        mediaSession.dispatchMediaButtonDelayed(mediaSession.getCurrentControllerInfo(), intent, ViewConfiguration.getDoubleTapTimeout());
                    }
                    return true;
                }
                handleMediaPlayPauseKeySingleTapIfPending();
                int keyCode2 = keyEvent.getKeyCode();
                if (keyCode2 != 126) {
                    if (keyCode2 != 127) {
                        switch (keyCode2) {
                            case 86:
                                if ((actions & 1) != 0) {
                                    onStop();
                                    return true;
                                }
                                break;
                            case 87:
                                if ((actions & 32) != 0) {
                                    onSkipToNext();
                                    return true;
                                }
                                break;
                            case 88:
                                if ((actions & 16) != 0) {
                                    onSkipToPrevious();
                                    return true;
                                }
                                break;
                            case 89:
                                if ((actions & 8) != 0) {
                                    onRewind();
                                    return true;
                                }
                                break;
                            case 90:
                                if ((actions & 64) != 0) {
                                    onFastForward();
                                    return true;
                                }
                                break;
                        }
                    } else if ((actions & 2) != 0) {
                        onPause();
                        return true;
                    }
                } else if ((actions & 4) != 0) {
                    onPlay();
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleMediaPlayPauseKeySingleTapIfPending() {
            if (this.mMediaPlayPauseKeyPending) {
                this.mMediaPlayPauseKeyPending = false;
                this.mHandler.removeMessages(24);
                PlaybackState playbackState = this.mSession.mPlaybackState;
                long actions = playbackState == null ? 0L : playbackState.getActions();
                boolean z = playbackState != null && playbackState.getState() == 3;
                boolean z2 = (516 & actions) != 0;
                boolean z3 = (actions & 514) != 0;
                if (z && z3) {
                    onPause();
                } else {
                    if (z || !z2) {
                        return;
                    }
                    onPlay();
                }
            }
        }
    }

    public static class CallbackStub extends ISessionCallback.Stub {
        private WeakReference<MediaSession> mMediaSession;

        public CallbackStub(MediaSession mediaSession) {
            this.mMediaSession = new WeakReference<>(mediaSession);
        }

        private static MediaSessionManager.RemoteUserInfo createRemoteUserInfo(String str, int i, int i2) {
            return new MediaSessionManager.RemoteUserInfo(str, i, i2);
        }

        @Override // android.media.session.ISessionCallback
        public void onCommand(String str, int i, int i2, String str2, Bundle bundle, ResultReceiver resultReceiver) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchCommand(createRemoteUserInfo(str, i, i2), str2, bundle, resultReceiver);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButton(String str, int i, int i2, Intent intent, int i3, ResultReceiver resultReceiver) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                try {
                    mediaSession.dispatchMediaButton(createRemoteUserInfo(str, i, i2), intent);
                } finally {
                    if (resultReceiver != null) {
                        resultReceiver.send(i3, null);
                    }
                }
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButtonFromController(String str, int i, int i2, Intent intent) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchMediaButton(createRemoteUserInfo(str, i, i2), intent);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepare(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepare(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromMediaId(String str, int i, int i2, String str2, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromMediaId(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromSearch(String str, int i, int i2, String str2, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromSearch(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromUri(String str, int i, int i2, Uri uri, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromUri(createRemoteUserInfo(str, i, i2), uri, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlay(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlay(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromMediaId(String str, int i, int i2, String str2, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromMediaId(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromSearch(String str, int i, int i2, String str2, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromSearch(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromUri(String str, int i, int i2, Uri uri, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromUri(createRemoteUserInfo(str, i, i2), uri, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSkipToTrack(String str, int i, int i2, long j) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSkipToItem(createRemoteUserInfo(str, i, i2), j);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPause(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPause(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onStop(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchStop(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onNext(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchNext(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrevious(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrevious(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onFastForward(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchFastForward(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onRewind(String str, int i, int i2) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchRewind(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSeekTo(String str, int i, int i2, long j) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSeekTo(createRemoteUserInfo(str, i, i2), j);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onRate(String str, int i, int i2, Rating rating) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchRate(createRemoteUserInfo(str, i, i2), rating);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSetPlaybackSpeed(String str, int i, int i2, float f) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSetPlaybackSpeed(createRemoteUserInfo(str, i, i2), f);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onCustomAction(String str, int i, int i2, String str2, Bundle bundle) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchCustomAction(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onAdjustVolume(String str, int i, int i2, int i3) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchAdjustVolume(createRemoteUserInfo(str, i, i2), i3);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSetVolumeTo(String str, int i, int i2, int i3) {
            MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSetVolumeTo(createRemoteUserInfo(str, i, i2), i3);
            }
        }
    }

    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>() { // from class: android.media.session.MediaSession.QueueItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public QueueItem[] newArray(int i) {
                return new QueueItem[i];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescription mDescription;
        private final long mId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public QueueItem(MediaDescription mediaDescription, long j) {
            if (mediaDescription == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            }
            if (j == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescription;
            this.mId = j;
        }

        private QueueItem(Parcel parcel) {
            this.mDescription = MediaDescription.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public MediaDescription getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mDescription.writeToParcel(parcel, i);
            parcel.writeLong(this.mId);
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof QueueItem)) {
                return false;
            }
            QueueItem queueItem = (QueueItem) obj;
            return this.mId == queueItem.mId && Objects.equals(this.mDescription, queueItem.mDescription);
        }
    }

    private static final class Command {
        public final String command;
        public final Bundle extras;
        public final ResultReceiver stub;

        Command(String str, Bundle bundle, ResultReceiver resultReceiver) {
            this.command = str;
            this.extras = bundle;
            this.stub = resultReceiver;
        }
    }

    private class CallbackMessageHandler extends Handler {
        private static final int MSG_ADJUST_VOLUME = 22;
        private static final int MSG_COMMAND = 1;
        private static final int MSG_CUSTOM_ACTION = 21;
        private static final int MSG_FAST_FORWARD = 16;
        private static final int MSG_MEDIA_BUTTON = 2;
        private static final int MSG_NEXT = 14;
        private static final int MSG_PAUSE = 12;
        private static final int MSG_PLAY = 7;
        private static final int MSG_PLAY_MEDIA_ID = 8;
        private static final int MSG_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 24;
        private static final int MSG_PLAY_SEARCH = 9;
        private static final int MSG_PLAY_URI = 10;
        private static final int MSG_PREPARE = 3;
        private static final int MSG_PREPARE_MEDIA_ID = 4;
        private static final int MSG_PREPARE_SEARCH = 5;
        private static final int MSG_PREPARE_URI = 6;
        private static final int MSG_PREVIOUS = 15;
        private static final int MSG_RATE = 19;
        private static final int MSG_REWIND = 17;
        private static final int MSG_SEEK_TO = 18;
        private static final int MSG_SET_PLAYBACK_SPEED = 20;
        private static final int MSG_SET_VOLUME = 23;
        private static final int MSG_SKIP_TO_ITEM = 11;
        private static final int MSG_STOP = 13;
        private Callback mCallback;
        private MediaSessionManager.RemoteUserInfo mCurrentControllerInfo;

        CallbackMessageHandler(Looper looper, Callback callback) {
            super(looper);
            this.mCallback = callback;
            callback.mHandler = this;
        }

        void post(MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, Object obj, Bundle bundle, long j) {
            Message obtainMessage = obtainMessage(i, Pair.create(remoteUserInfo, obj));
            obtainMessage.setAsynchronous(true);
            obtainMessage.setData(bundle);
            if (j > 0) {
                sendMessageDelayed(obtainMessage, j);
            } else {
                sendMessage(obtainMessage);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            VolumeProvider volumeProvider;
            VolumeProvider volumeProvider2;
            this.mCurrentControllerInfo = (MediaSessionManager.RemoteUserInfo) ((Pair) message.obj).first;
            S s = ((Pair) message.obj).second;
            switch (message.what) {
                case 1:
                    Command command = (Command) s;
                    this.mCallback.onCommand(command.command, command.extras, command.stub);
                    break;
                case 2:
                    this.mCallback.onMediaButtonEvent((Intent) s);
                    break;
                case 3:
                    this.mCallback.onPrepare();
                    break;
                case 4:
                    this.mCallback.onPrepareFromMediaId((String) s, message.getData());
                    break;
                case 5:
                    this.mCallback.onPrepareFromSearch((String) s, message.getData());
                    break;
                case 6:
                    this.mCallback.onPrepareFromUri((Uri) s, message.getData());
                    break;
                case 7:
                    this.mCallback.onPlay();
                    break;
                case 8:
                    this.mCallback.onPlayFromMediaId((String) s, message.getData());
                    break;
                case 9:
                    this.mCallback.onPlayFromSearch((String) s, message.getData());
                    break;
                case 10:
                    this.mCallback.onPlayFromUri((Uri) s, message.getData());
                    break;
                case 11:
                    this.mCallback.onSkipToQueueItem(((Long) s).longValue());
                    break;
                case 12:
                    this.mCallback.onPause();
                    break;
                case 13:
                    this.mCallback.onStop();
                    break;
                case 14:
                    this.mCallback.onSkipToNext();
                    break;
                case 15:
                    this.mCallback.onSkipToPrevious();
                    break;
                case 16:
                    this.mCallback.onFastForward();
                    break;
                case 17:
                    this.mCallback.onRewind();
                    break;
                case 18:
                    this.mCallback.onSeekTo(((Long) s).longValue());
                    break;
                case 19:
                    this.mCallback.onSetRating((Rating) s);
                    break;
                case 20:
                    this.mCallback.onSetPlaybackSpeed(((Float) s).floatValue());
                    break;
                case 21:
                    this.mCallback.onCustomAction((String) s, message.getData());
                    break;
                case 22:
                    synchronized (MediaSession.this.mLock) {
                        volumeProvider = MediaSession.this.mVolumeProvider;
                    }
                    if (volumeProvider != null) {
                        volumeProvider.onAdjustVolume(((Integer) s).intValue());
                        break;
                    }
                    break;
                case 23:
                    synchronized (MediaSession.this.mLock) {
                        volumeProvider2 = MediaSession.this.mVolumeProvider;
                    }
                    if (volumeProvider2 != null) {
                        volumeProvider2.onSetVolumeTo(((Integer) s).intValue());
                        break;
                    }
                    break;
                case 24:
                    this.mCallback.handleMediaPlayPauseKeySingleTapIfPending();
                    break;
            }
            this.mCurrentControllerInfo = null;
        }
    }
}
