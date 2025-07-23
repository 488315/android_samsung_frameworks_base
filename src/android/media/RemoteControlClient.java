package android.media;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.MediaSessionLegacyHelper;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class RemoteControlClient {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_PLAYBACK_VOLUME = 15;
    public static final int DEFAULT_PLAYBACK_VOLUME_HANDLING = 1;
    public static final int FLAGS_KEY_MEDIA_NONE = 0;
    public static final int FLAG_INFORMATION_REQUEST_ALBUM_ART = 8;
    public static final int FLAG_INFORMATION_REQUEST_KEY_MEDIA = 2;
    public static final int FLAG_INFORMATION_REQUEST_METADATA = 1;
    public static final int FLAG_INFORMATION_REQUEST_PLAYSTATE = 4;
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_POSITION_UPDATE = 256;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_RATING = 512;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static int MEDIA_POSITION_READABLE = 1;
    public static int MEDIA_POSITION_WRITABLE = 2;
    public static final int PLAYBACKINFO_INVALID_VALUE = Integer.MIN_VALUE;
    public static final int PLAYBACKINFO_PLAYBACK_TYPE = 1;
    public static final int PLAYBACKINFO_USES_STREAM = 5;
    public static final int PLAYBACKINFO_VOLUME = 2;
    public static final int PLAYBACKINFO_VOLUME_HANDLING = 4;
    public static final int PLAYBACKINFO_VOLUME_MAX = 3;
    public static final long PLAYBACK_POSITION_ALWAYS_UNKNOWN = -9216204211029966080L;
    public static final long PLAYBACK_POSITION_INVALID = -1;
    public static final float PLAYBACK_SPEED_1X = 1.0f;
    public static final int PLAYBACK_TYPE_LOCAL = 0;
    private static final int PLAYBACK_TYPE_MAX = 1;
    private static final int PLAYBACK_TYPE_MIN = 0;
    public static final int PLAYBACK_TYPE_REMOTE = 1;
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final int PLAYSTATE_BUFFERING = 8;
    public static final int PLAYSTATE_ERROR = 9;
    public static final int PLAYSTATE_FAST_FORWARDING = 4;
    public static final int PLAYSTATE_NONE = 0;
    public static final int PLAYSTATE_PAUSED = 2;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_REWINDING = 5;
    public static final int PLAYSTATE_SKIPPING_BACKWARDS = 7;
    public static final int PLAYSTATE_SKIPPING_FORWARDS = 6;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final long POSITION_DRIFT_MAX_MS = 500;
    private static final long POSITION_REFRESH_PERIOD_MIN_MS = 2000;
    private static final long POSITION_REFRESH_PERIOD_PLAYING_MS = 15000;
    public static final int RCSE_ID_UNREGISTERED = -1;
    private static final String TAG = "RemoteControlClient";
    private MediaMetadata mMediaMetadata;
    private OnMetadataUpdateListener mMetadataUpdateListener;
    private Bitmap mOriginalArtwork;
    private OnGetPlaybackPositionListener mPositionProvider;
    private OnPlaybackPositionUpdateListener mPositionUpdateListener;
    private final PendingIntent mRcMediaIntent;
    private MediaSession mSession;
    private final Object mCacheLock = new Object();
    private int mPlaybackState = 0;
    private long mPlaybackStateChangeTimeMs = 0;
    private long mPlaybackPositionMs = -1;
    private float mPlaybackSpeed = 1.0f;
    private int mTransportControlFlags = 0;
    private Bundle mMetadata = new Bundle();
    private int mCurrentClientGenId = -1;
    private boolean mNeedsPositionSync = false;
    private PlaybackState mSessionPlaybackState = null;
    private MediaSession.Callback mTransportListener = new MediaSession.Callback() { // from class: android.media.RemoteControlClient.1
        @Override // android.media.session.MediaSession.Callback
        public void onSeekTo(long j) {
            RemoteControlClient remoteControlClient = RemoteControlClient.this;
            remoteControlClient.onSeekTo(remoteControlClient.mCurrentClientGenId, j);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSetRating(Rating rating) {
            if ((RemoteControlClient.this.mTransportControlFlags & 512) != 0) {
                RemoteControlClient remoteControlClient = RemoteControlClient.this;
                remoteControlClient.onUpdateMetadata(remoteControlClient.mCurrentClientGenId, 268435457, rating);
            }
        }
    };

    public interface OnGetPlaybackPositionListener {
        long onGetPlaybackPosition();
    }

    public interface OnMetadataUpdateListener {
        void onMetadataUpdate(int i, Object obj);
    }

    public interface OnPlaybackPositionUpdateListener {
        void onPlaybackPositionUpdate(long j);
    }

    private static long getActionForRccFlag(int i) {
        if (i == 1) {
            return 16L;
        }
        if (i == 2) {
            return 8L;
        }
        if (i == 4) {
            return 4L;
        }
        if (i == 8) {
            return 512L;
        }
        if (i == 16) {
            return 2L;
        }
        if (i == 32) {
            return 1L;
        }
        if (i == 64) {
            return 64L;
        }
        if (i == 128) {
            return 32L;
        }
        if (i != 256) {
            return i != 512 ? 0L : 128L;
        }
        return 256L;
    }

    private static int getRccFlagForAction(long j) {
        int i = j < 2147483647L ? (int) j : 0;
        int i2 = 1;
        if (i == 1) {
            return 32;
        }
        if (i == 2) {
            return 16;
        }
        if (i == 4) {
            return 4;
        }
        if (i == 8) {
            return 2;
        }
        if (i != 16) {
            i2 = 128;
            if (i != 32) {
                int i3 = 64;
                if (i != 64) {
                    i3 = 512;
                    if (i != 128) {
                        if (i != 256) {
                            return i != 512 ? 0 : 8;
                        }
                        return 256;
                    }
                }
                return i3;
            }
        }
        return i2;
    }

    static int getRccStateFromState(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
            default:
                return -1;
            case 9:
                return 7;
            case 10:
                return 6;
        }
    }

    private static int getStateFromRccState(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 10;
            case 7:
                return 9;
            case 8:
                return 6;
            case 9:
                return 7;
            default:
                return -1;
        }
    }

    static boolean playbackPositionShouldMove(int i) {
        if (i == 1 || i == 2) {
            return false;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
                return false;
            default:
                return true;
        }
    }

    public RemoteControlClient(PendingIntent pendingIntent) {
        this.mRcMediaIntent = pendingIntent;
    }

    public RemoteControlClient(PendingIntent pendingIntent, Looper looper) {
        this.mRcMediaIntent = pendingIntent;
    }

    public void registerWithSession(MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.addRccListener(this.mRcMediaIntent, this.mTransportListener);
        this.mSession = mediaSessionLegacyHelper.getSession(this.mRcMediaIntent);
        setTransportControlFlags(this.mTransportControlFlags);
    }

    public void unregisterWithSession(MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.removeRccListener(this.mRcMediaIntent);
        this.mSession = null;
    }

    public MediaSession getMediaSession() {
        return this.mSession;
    }

    @Deprecated
    public class MetadataEditor extends MediaMetadataEditor {
        public static final int BITMAP_KEY_ARTWORK = 100;
        public static final int METADATA_KEY_ARTWORK = 100;

        private MetadataEditor() {
        }

        public Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized MetadataEditor putString(int i, String str) throws IllegalArgumentException {
            String keyFromMetadataEditorKey;
            super.putString(i, str);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putText(keyFromMetadataEditorKey, str);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized MetadataEditor putLong(int i, long j) throws IllegalArgumentException {
            String keyFromMetadataEditorKey;
            super.putLong(i, j);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putLong(keyFromMetadataEditorKey, j);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized MetadataEditor putBitmap(int i, Bitmap bitmap) throws IllegalArgumentException {
            String keyFromMetadataEditorKey;
            super.putBitmap(i, bitmap);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putBitmap(keyFromMetadataEditorKey, bitmap);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized MetadataEditor putObject(int i, Object obj) throws IllegalArgumentException {
            String keyFromMetadataEditorKey;
            super.putObject(i, obj);
            if (this.mMetadataBuilder != null && ((i == 268435457 || i == 101) && (keyFromMetadataEditorKey = MediaMetadata.getKeyFromMetadataEditorKey(i)) != null)) {
                this.mMetadataBuilder.putRating(keyFromMetadataEditorKey, (Rating) obj);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void clear() {
            super.clear();
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void apply() {
            if (this.mApplied) {
                Log.e(RemoteControlClient.TAG, "Can't apply a previously applied MetadataEditor");
                return;
            }
            synchronized (RemoteControlClient.this.mCacheLock) {
                RemoteControlClient.this.mMetadata = new Bundle(this.mEditorMetadata);
                RemoteControlClient.this.mMetadata.putLong(String.valueOf(MediaMetadataEditor.KEY_EDITABLE_MASK), this.mEditableKeys);
                if (RemoteControlClient.this.mOriginalArtwork != null && !RemoteControlClient.this.mOriginalArtwork.equals(this.mEditorArtwork)) {
                    RemoteControlClient.this.mOriginalArtwork.recycle();
                }
                RemoteControlClient.this.mOriginalArtwork = this.mEditorArtwork;
                this.mEditorArtwork = null;
                if (RemoteControlClient.this.mSession != null && this.mMetadataBuilder != null) {
                    RemoteControlClient.this.mMediaMetadata = this.mMetadataBuilder.build();
                    RemoteControlClient.this.mSession.setMetadata(RemoteControlClient.this.mMediaMetadata);
                }
                this.mApplied = true;
            }
        }
    }

    public MetadataEditor editMetadata(boolean z) {
        MetadataEditor metadataEditor = new MetadataEditor();
        if (z) {
            metadataEditor.mEditorMetadata = new Bundle();
            metadataEditor.mEditorArtwork = null;
            metadataEditor.mMetadataChanged = true;
            metadataEditor.mArtworkChanged = true;
            metadataEditor.mEditableKeys = 0L;
        } else {
            metadataEditor.mEditorMetadata = new Bundle(this.mMetadata);
            metadataEditor.mEditorArtwork = this.mOriginalArtwork;
            metadataEditor.mMetadataChanged = false;
            metadataEditor.mArtworkChanged = false;
        }
        if (z || this.mMediaMetadata == null) {
            metadataEditor.mMetadataBuilder = new MediaMetadata.Builder();
            return metadataEditor;
        }
        metadataEditor.mMetadataBuilder = new MediaMetadata.Builder(this.mMediaMetadata);
        return metadataEditor;
    }

    public void setPlaybackState(int i) {
        setPlaybackStateInt(i, PLAYBACK_POSITION_ALWAYS_UNKNOWN, 1.0f, false);
    }

    public void setPlaybackState(int i, long j, float f) {
        setPlaybackStateInt(i, j, f, true);
    }

    private void setPlaybackStateInt(int i, long j, float f, boolean z) {
        synchronized (this.mCacheLock) {
            if (this.mPlaybackState != i || this.mPlaybackPositionMs != j || this.mPlaybackSpeed != f) {
                this.mPlaybackState = i;
                if (!z) {
                    this.mPlaybackPositionMs = PLAYBACK_POSITION_ALWAYS_UNKNOWN;
                } else if (j < 0) {
                    this.mPlaybackPositionMs = -1L;
                } else {
                    this.mPlaybackPositionMs = j;
                }
                this.mPlaybackSpeed = f;
                this.mPlaybackStateChangeTimeMs = SystemClock.elapsedRealtime();
                if (this.mSession != null) {
                    int stateFromRccState = getStateFromRccState(i);
                    long j2 = z ? this.mPlaybackPositionMs : -1L;
                    PlaybackState.Builder builder = new PlaybackState.Builder(this.mSessionPlaybackState);
                    builder.setState(stateFromRccState, j2, f, SystemClock.elapsedRealtime());
                    builder.setErrorMessage(null);
                    PlaybackState build = builder.build();
                    this.mSessionPlaybackState = build;
                    this.mSession.setPlaybackState(build);
                }
            }
        }
    }

    public void setTransportControlFlags(int i) {
        synchronized (this.mCacheLock) {
            this.mTransportControlFlags = i;
            if (this.mSession != null) {
                PlaybackState.Builder builder = new PlaybackState.Builder(this.mSessionPlaybackState);
                builder.setActions(getActionsFromRccControlFlags(i));
                PlaybackState build = builder.build();
                this.mSessionPlaybackState = build;
                this.mSession.setPlaybackState(build);
            }
        }
    }

    public void setMetadataUpdateListener(OnMetadataUpdateListener onMetadataUpdateListener) {
        synchronized (this.mCacheLock) {
            this.mMetadataUpdateListener = onMetadataUpdateListener;
        }
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        synchronized (this.mCacheLock) {
            this.mPositionUpdateListener = onPlaybackPositionUpdateListener;
        }
    }

    public void setOnGetPlaybackPositionListener(OnGetPlaybackPositionListener onGetPlaybackPositionListener) {
        synchronized (this.mCacheLock) {
            this.mPositionProvider = onGetPlaybackPositionListener;
        }
    }

    public PendingIntent getRcMediaIntent() {
        return this.mRcMediaIntent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeekTo(int i, long j) {
        OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener;
        synchronized (this.mCacheLock) {
            if (this.mCurrentClientGenId == i && (onPlaybackPositionUpdateListener = this.mPositionUpdateListener) != null) {
                onPlaybackPositionUpdateListener.onPlaybackPositionUpdate(j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateMetadata(int i, int i2, Object obj) {
        OnMetadataUpdateListener onMetadataUpdateListener;
        synchronized (this.mCacheLock) {
            if (this.mCurrentClientGenId == i && (onMetadataUpdateListener = this.mMetadataUpdateListener) != null) {
                onMetadataUpdateListener.onMetadataUpdate(i2, obj);
            }
        }
    }

    private static long getCheckPeriodFromSpeed(float f) {
        return Math.abs(f) <= 1.0f ? POSITION_REFRESH_PERIOD_PLAYING_MS : Math.max((long) (15000.0f / Math.abs(f)), POSITION_REFRESH_PERIOD_MIN_MS);
    }

    private static long getActionsFromRccControlFlags(int i) {
        long j = 1;
        long j2 = 0;
        while (true) {
            long j3 = i;
            if (j > j3) {
                return j2;
            }
            if ((j3 & j) != 0) {
                j2 |= getActionForRccFlag((int) j);
            }
            j <<= 1;
        }
    }

    static int getRccControlFlagsFromActions(long j) {
        int i = 0;
        for (long j2 = 1; j2 <= j && j2 < 2147483647L; j2 <<= 1) {
            if ((j2 & j) != 0) {
                i |= getRccFlagForAction(j2);
            }
        }
        return i;
    }
}
