package android.media;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.session.MediaController;
import android.media.session.MediaSessionLegacyHelper;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

@Deprecated
/* loaded from: classes2.dex */
public final class RemoteController {
    private static final boolean DEBUG = false;
    private static final int MAX_BITMAP_DIMENSION = 512;
    private static final int MSG_CLIENT_CHANGE = 0;
    private static final int MSG_NEW_MEDIA_METADATA = 2;
    private static final int MSG_NEW_PLAYBACK_STATE = 1;
    public static final int POSITION_SYNCHRONIZATION_CHECK = 1;
    public static final int POSITION_SYNCHRONIZATION_NONE = 0;
    private static final int SENDMSG_NOOP = 1;
    private static final int SENDMSG_QUEUE = 2;
    private static final int SENDMSG_REPLACE = 0;
    private static final String TAG = "RemoteController";
    private static final Object mInfoLock = new Object();
    private int mArtworkHeight;
    private int mArtworkWidth;
    private final Context mContext;
    private MediaController mCurrentSession;
    private boolean mEnabled;
    private final EventHandler mEventHandler;
    private boolean mIsRegistered;
    private PlaybackInfo mLastPlaybackInfo;
    private final int mMaxBitmapDimension;
    private MetadataEditor mMetadataEditor;
    private OnClientUpdateListener mOnClientUpdateListener;
    private MediaController.Callback mSessionCb;
    private MediaSessionManager.OnActiveSessionsChangedListener mSessionListener;
    private MediaSessionManager mSessionManager;

    public interface OnClientUpdateListener {
        void onClientChange(boolean z);

        void onClientMetadataUpdate(MetadataEditor metadataEditor);

        void onClientPlaybackStateUpdate(int i);

        void onClientPlaybackStateUpdate(int i, long j, long j2, float f);

        void onClientTransportControlUpdate(int i);
    }

    public RemoteController(Context context, OnClientUpdateListener onClientUpdateListener) throws IllegalArgumentException {
        this(context, onClientUpdateListener, null);
    }

    public RemoteController(Context context, OnClientUpdateListener onClientUpdateListener, Looper looper) throws IllegalArgumentException {
        this.mSessionCb = new MediaControllerCallback();
        this.mIsRegistered = false;
        this.mArtworkWidth = -1;
        this.mArtworkHeight = -1;
        this.mEnabled = true;
        if (context == null) {
            throw new IllegalArgumentException("Invalid null Context");
        }
        if (onClientUpdateListener == null) {
            throw new IllegalArgumentException("Invalid null OnClientUpdateListener");
        }
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            Looper myLooper = Looper.myLooper();
            if (myLooper != null) {
                this.mEventHandler = new EventHandler(this, myLooper);
            } else {
                throw new IllegalArgumentException("Calling thread not associated with a looper");
            }
        }
        this.mOnClientUpdateListener = onClientUpdateListener;
        this.mContext = context;
        this.mSessionManager = (MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE);
        this.mSessionListener = new TopTransportSessionListener();
        if (ActivityManager.isLowRamDeviceStatic()) {
            this.mMaxBitmapDimension = 512;
        } else {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            this.mMaxBitmapDimension = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }

    public long getEstimatedMediaPosition() {
        PlaybackState playbackState;
        synchronized (mInfoLock) {
            MediaController mediaController = this.mCurrentSession;
            if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
                return -1L;
            }
            return playbackState.getPosition();
        }
    }

    public boolean sendMediaKeyEvent(KeyEvent keyEvent) throws IllegalArgumentException {
        if (!KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            throw new IllegalArgumentException("not a media key event");
        }
        synchronized (mInfoLock) {
            MediaController mediaController = this.mCurrentSession;
            if (mediaController == null) {
                return false;
            }
            return mediaController.dispatchMediaButtonEvent(keyEvent);
        }
    }

    public boolean seekTo(long j) throws IllegalArgumentException {
        if (!this.mEnabled) {
            Log.e(TAG, "Cannot use seekTo() from a disabled RemoteController");
            return false;
        }
        if (j < 0) {
            throw new IllegalArgumentException("illegal negative time value");
        }
        synchronized (mInfoLock) {
            MediaController mediaController = this.mCurrentSession;
            if (mediaController != null) {
                mediaController.getTransportControls().seekTo(j);
            }
        }
        return true;
    }

    public boolean setArtworkConfiguration(boolean z, int i, int i2) throws IllegalArgumentException {
        synchronized (mInfoLock) {
            if (!z) {
                this.mArtworkWidth = -1;
                this.mArtworkHeight = -1;
            } else if (i > 0 && i2 > 0) {
                int i3 = this.mMaxBitmapDimension;
                if (i > i3) {
                    i = i3;
                }
                if (i2 > i3) {
                    i2 = i3;
                }
                this.mArtworkWidth = i;
                this.mArtworkHeight = i2;
            } else {
                throw new IllegalArgumentException("Invalid dimensions");
            }
        }
        return true;
    }

    public boolean setArtworkConfiguration(int i, int i2) throws IllegalArgumentException {
        return setArtworkConfiguration(true, i, i2);
    }

    public boolean clearArtworkConfiguration() {
        return setArtworkConfiguration(false, -1, -1);
    }

    public boolean setSynchronizationMode(int i) throws IllegalArgumentException {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Unknown synchronization mode " + i);
        }
        if (this.mIsRegistered) {
            return true;
        }
        Log.e(TAG, "Cannot set synchronization mode on an unregistered RemoteController");
        return false;
    }

    public MetadataEditor editMetadata() {
        MetadataEditor metadataEditor = new MetadataEditor();
        metadataEditor.mEditorMetadata = new Bundle();
        metadataEditor.mEditorArtwork = null;
        metadataEditor.mMetadataChanged = true;
        metadataEditor.mArtworkChanged = true;
        metadataEditor.mEditableKeys = 0L;
        return metadataEditor;
    }

    public class MetadataEditor extends MediaMetadataEditor {
        protected MetadataEditor() {
        }

        protected MetadataEditor(Bundle bundle, long j) {
            this.mEditorMetadata = bundle;
            this.mEditableKeys = j;
            this.mEditorArtwork = (Bitmap) bundle.getParcelable(String.valueOf(100), Bitmap.class);
            if (this.mEditorArtwork != null) {
                cleanupBitmapFromBundle(100);
            }
            this.mMetadataChanged = true;
            this.mArtworkChanged = true;
            this.mApplied = false;
        }

        private void cleanupBitmapFromBundle(int i) {
            if (METADATA_KEYS_TYPE.get(i, -1) == 2) {
                this.mEditorMetadata.remove(String.valueOf(i));
            }
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void apply() {
            Rating rating;
            if (this.mMetadataChanged) {
                synchronized (RemoteController.mInfoLock) {
                    if (RemoteController.this.mCurrentSession != null && this.mEditorMetadata.containsKey(String.valueOf(268435457)) && (rating = (Rating) getObject(268435457, null)) != null) {
                        RemoteController.this.mCurrentSession.getTransportControls().setRating(rating);
                    }
                }
                this.mApplied = false;
            }
        }
    }

    private class MediaControllerCallback extends MediaController.Callback {
        private MediaControllerCallback() {
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            RemoteController.this.onNewPlaybackState(playbackState);
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            RemoteController.this.onNewMediaMetadata(mediaMetadata);
        }
    }

    private class TopTransportSessionListener implements MediaSessionManager.OnActiveSessionsChangedListener {
        private TopTransportSessionListener() {
        }

        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(List<MediaController> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                MediaController mediaController = list.get(i);
                if ((mediaController.getFlags() & 2) != 0) {
                    RemoteController.this.updateController(mediaController);
                    return;
                }
            }
            RemoteController.this.updateController(null);
        }
    }

    private class EventHandler extends Handler {
        public EventHandler(RemoteController remoteController, Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                RemoteController.this.onClientChange(message.arg2 == 1);
                return;
            }
            if (i == 1) {
                RemoteController.this.onNewPlaybackState((PlaybackState) message.obj);
            } else {
                if (i == 2) {
                    RemoteController.this.onNewMediaMetadata((MediaMetadata) message.obj);
                    return;
                }
                Log.e(RemoteController.TAG, "unknown event " + message.what);
            }
        }
    }

    void startListeningToSessions() {
        ComponentName componentName = new ComponentName(this.mContext, this.mOnClientUpdateListener.getClass());
        this.mSessionManager.addOnActiveSessionsChangedListener(this.mSessionListener, componentName, Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : null);
        this.mSessionListener.onActiveSessionsChanged(this.mSessionManager.getActiveSessions(componentName));
    }

    void stopListeningToSessions() {
        this.mSessionManager.removeOnActiveSessionsChangedListener(this.mSessionListener);
    }

    private static void sendMsg(Handler handler, int i, int i2, int i3, int i4, Object obj, int i5) {
        if (handler == null) {
            Log.e(TAG, "null event handler, will not deliver message " + i);
        } else {
            if (i2 == 0) {
                handler.removeMessages(i);
            } else if (i2 == 1 && handler.hasMessages(i)) {
                return;
            }
            handler.sendMessageDelayed(handler.obtainMessage(i, i3, i4, obj), i5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClientChange(boolean z) {
        OnClientUpdateListener onClientUpdateListener;
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
            this.mMetadataEditor = null;
        }
        if (onClientUpdateListener != null) {
            onClientUpdateListener.onClientChange(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateController(MediaController mediaController) {
        synchronized (mInfoLock) {
            if (mediaController == null) {
                MediaController mediaController2 = this.mCurrentSession;
                if (mediaController2 != null) {
                    mediaController2.unregisterCallback(this.mSessionCb);
                    this.mCurrentSession = null;
                    sendMsg(this.mEventHandler, 0, 0, 0, 1, null, 0);
                }
            } else if (this.mCurrentSession == null || !mediaController.getSessionToken().equals(this.mCurrentSession.getSessionToken())) {
                MediaController mediaController3 = this.mCurrentSession;
                if (mediaController3 != null) {
                    mediaController3.unregisterCallback(this.mSessionCb);
                }
                sendMsg(this.mEventHandler, 0, 0, 0, 0, null, 0);
                this.mCurrentSession = mediaController;
                mediaController.registerCallback(this.mSessionCb, this.mEventHandler);
                sendMsg(this.mEventHandler, 1, 0, 0, 0, mediaController.getPlaybackState(), 0);
                sendMsg(this.mEventHandler, 2, 0, 0, 0, mediaController.getMetadata(), 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNewPlaybackState(PlaybackState playbackState) {
        OnClientUpdateListener onClientUpdateListener;
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
        }
        if (onClientUpdateListener != null) {
            int rccStateFromState = playbackState == null ? 0 : RemoteControlClient.getRccStateFromState(playbackState.getState());
            if (playbackState == null || playbackState.getPosition() == -1) {
                onClientUpdateListener.onClientPlaybackStateUpdate(rccStateFromState);
            } else {
                onClientUpdateListener.onClientPlaybackStateUpdate(rccStateFromState, playbackState.getLastPositionUpdateTime(), playbackState.getPosition(), playbackState.getPlaybackSpeed());
            }
            if (playbackState != null) {
                onClientUpdateListener.onClientTransportControlUpdate(RemoteControlClient.getRccControlFlagsFromActions(playbackState.getActions()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNewMediaMetadata(MediaMetadata mediaMetadata) {
        OnClientUpdateListener onClientUpdateListener;
        MetadataEditor metadataEditor;
        if (mediaMetadata == null) {
            return;
        }
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
            MediaController mediaController = this.mCurrentSession;
            metadataEditor = new MetadataEditor(MediaSessionLegacyHelper.getOldMetadata(mediaMetadata, this.mArtworkWidth, this.mArtworkHeight), (mediaController == null || mediaController.getRatingType() == 0) ? 0L : 268435457L);
            this.mMetadataEditor = metadataEditor;
        }
        if (onClientUpdateListener != null) {
            onClientUpdateListener.onClientMetadataUpdate(metadataEditor);
        }
    }

    private static class PlaybackInfo {
        long mCurrentPosMs;
        float mSpeed;
        int mState;
        long mStateChangeTimeMs;

        PlaybackInfo(int i, long j, long j2, float f) {
            this.mState = i;
            this.mStateChangeTimeMs = j;
            this.mCurrentPosMs = j2;
            this.mSpeed = f;
        }
    }

    OnClientUpdateListener getUpdateListener() {
        return this.mOnClientUpdateListener;
    }
}
