package android.support.v4.media.session;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioAttributesImplApi26;
import androidx.versionedparcelable.ParcelUtils;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class MediaControllerCompat {
    public final MediaControllerImplApi29 mImpl;
    public final Set mRegisteredCallbacks;

    public class MediaControllerImplApi21 {
        public final MediaController mControllerFwk;
        public final MediaSessionCompat.Token mSessionToken;
        public final Object mLock = new Object();
        public final List mPendingCallbacks = new ArrayList();
        public final HashMap mCallbackMap = new HashMap();

        class ExtraBinderRequestResultReceiver extends ResultReceiver {
            public final WeakReference mMediaControllerImpl;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21) {
                super(null);
                this.mMediaControllerImpl = new WeakReference(mediaControllerImplApi21);
            }

            @Override // android.os.ResultReceiver
            public final void onReceiveResult(int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerImplApi21) this.mMediaControllerImpl.get();
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return;
                }
                synchronized (mediaControllerImplApi21.mLock) {
                    MediaSessionCompat.Token token = mediaControllerImplApi21.mSessionToken;
                    IMediaSession iMediaSessionAsInterface = IMediaSession.Stub.asInterface(bundle.getBinder("android.support.v4.media.session.EXTRA_BINDER"));
                    synchronized (token.mLock) {
                        token.mExtraBinder = iMediaSessionAsInterface;
                    }
                    MediaSessionCompat.Token token2 = mediaControllerImplApi21.mSessionToken;
                    VersionedParcelable versionedParcelable = ParcelUtils.getVersionedParcelable(bundle);
                    synchronized (token2.mLock) {
                        token2.mSession2Token = versionedParcelable;
                    }
                    mediaControllerImplApi21.processPendingCallbacksLocked();
                }
            }
        }

        public class ExtraCallback extends Callback.StubCompat {
            public ExtraCallback(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onExtrasChanged() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onMetadataChanged() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onQueueChanged() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public final void onQueueTitleChanged() {
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

        public final void processPendingCallbacksLocked() {
            MediaSessionCompat.Token token = this.mSessionToken;
            if (token.getExtraBinder() == null) {
                return;
            }
            ArrayList arrayList = (ArrayList) this.mPendingCallbacks;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Callback callback = (Callback) obj;
                ExtraCallback extraCallback = new ExtraCallback(callback);
                this.mCallbackMap.put(callback, extraCallback);
                callback.mIControllerCallback = extraCallback;
                try {
                    token.getExtraBinder().registerCallbackListener(extraCallback);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                }
            }
            ((ArrayList) this.mPendingCallbacks).clear();
        }
    }

    public class MediaControllerImplApi29 extends MediaControllerImplApi21 {
        public MediaControllerImplApi29(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }
    }

    public MediaControllerCompat(Context context, MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mRegisteredCallbacks = Collections.synchronizedSet(new HashSet());
        this.mImpl = new MediaControllerImplApi29(context, token);
    }

    public MediaControllerCompat(Context context, MediaSessionCompat mediaSessionCompat) {
        this(context, mediaSessionCompat.mImpl.getSessionToken());
    }

    public final class PlaybackInfo {
        /* JADX WARN: Illegal instructions before constructor call */
        public PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            AudioAttributesImplApi26.Builder builder = new AudioAttributesCompat.Builder().mBuilderImpl;
            builder.mFwkBuilder.setLegacyStreamType(i2);
            this(i, new AudioAttributesCompat(new AudioAttributesImplApi26(builder.mFwkBuilder.build())), i3, i4, i5);
        }

        public PlaybackInfo(int i, AudioAttributesCompat audioAttributesCompat, int i2, int i3, int i4) {
        }
    }

    public abstract class Callback implements IBinder.DeathRecipient {
        public MediaControllerImplApi21.ExtraCallback mIControllerCallback;

        public class MediaControllerCallbackApi21 extends MediaController.Callback {
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
                if (((Callback) this.mCallback.get()) != null) {
                    MediaMetadataCompat.fromMediaMetadata(mediaMetadata);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                ArrayList arrayList;
                PlaybackStateCompat.CustomAction customAction;
                Callback callback = (Callback) this.mCallback.get();
                if (callback == null || callback.mIControllerCallback != null) {
                    return;
                }
                Parcelable.Creator<PlaybackStateCompat> creator = PlaybackStateCompat.CREATOR;
                if (playbackState != null) {
                    List<PlaybackState.CustomAction> customActions = PlaybackStateCompat.Api21Impl.getCustomActions(playbackState);
                    if (customActions != null) {
                        ArrayList arrayList2 = new ArrayList(customActions.size());
                        for (PlaybackState.CustomAction customAction2 : customActions) {
                            Parcelable.Creator<PlaybackStateCompat.CustomAction> creator2 = PlaybackStateCompat.CustomAction.CREATOR;
                            if (customAction2 != null) {
                                PlaybackState.CustomAction customAction3 = customAction2;
                                Bundle extras = PlaybackStateCompat.Api21Impl.getExtras(customAction3);
                                MediaSessionCompat.ensureClassLoader(extras);
                                customAction = new PlaybackStateCompat.CustomAction(PlaybackStateCompat.Api21Impl.getAction(customAction3), PlaybackStateCompat.Api21Impl.getName(customAction3), PlaybackStateCompat.Api21Impl.getIcon(customAction3), extras);
                            } else {
                                customAction = null;
                            }
                            arrayList2.add(customAction);
                        }
                        arrayList = arrayList2;
                    } else {
                        arrayList = null;
                    }
                    Bundle extras2 = PlaybackStateCompat.Api22Impl.getExtras(playbackState);
                    MediaSessionCompat.ensureClassLoader(extras2);
                    new PlaybackStateCompat(PlaybackStateCompat.Api21Impl.getState(playbackState), PlaybackStateCompat.Api21Impl.getPosition(playbackState), PlaybackStateCompat.Api21Impl.getBufferedPosition(playbackState), PlaybackStateCompat.Api21Impl.getPlaybackSpeed(playbackState), PlaybackStateCompat.Api21Impl.getActions(playbackState), 0, PlaybackStateCompat.Api21Impl.getErrorMessage(playbackState), PlaybackStateCompat.Api21Impl.getLastPositionUpdateTime(playbackState), arrayList, PlaybackStateCompat.Api21Impl.getActiveQueueItemId(playbackState), extras2);
                }
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
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionEvent(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
            }
        }

        public class StubCompat extends IMediaControllerCallback.Stub {
            public final WeakReference mCallback;

            public StubCompat(Callback callback) {
                this.mCallback = new WeakReference(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged() {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged() {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged() {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged() {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() {
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                if (((Callback) this.mCallback.get()) == null || parcelableVolumeInfo == null) {
                    return;
                }
                new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume);
            }
        }

        public Callback() {
            new MediaControllerCallbackApi21(this);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
        }
    }
}
