package android.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaCommunicationManager;
import android.media.MediaMetadata;
import android.media.MediaMetrics;
import android.media.Rating;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;

/* loaded from: classes3.dex */
public class MediaSessionLegacyHelper {
    private static MediaSessionLegacyHelper sInstance;
    private MediaCommunicationManager mCommunicationManager;
    private Context mContext;
    private MediaSessionManager mSessionManager;
    private static final String TAG = "MediaSessionHelper";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final Object sLock = new Object();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ArrayMap<PendingIntent, SessionHolder> mSessions = new ArrayMap<>();

    private MediaSessionLegacyHelper(Context context) {
        this.mContext = context;
        this.mSessionManager = (MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE);
        this.mCommunicationManager = (MediaCommunicationManager) context.getSystemService(MediaCommunicationManager.class);
    }

    public static MediaSessionLegacyHelper getHelper(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new MediaSessionLegacyHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    public static Bundle getOldMetadata(MediaMetadata mediaMetadata, int i, int i2) {
        boolean z = (i == -1 || i2 == -1) ? false : true;
        Bundle bundle = new Bundle();
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_ALBUM)) {
            bundle.putString(String.valueOf(1), mediaMetadata.getString(MediaMetadata.METADATA_KEY_ALBUM));
        }
        if (z && mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_ART)) {
            bundle.putParcelable(String.valueOf(100), scaleBitmapIfTooBig(mediaMetadata.getBitmap(MediaMetadata.METADATA_KEY_ART), i, i2));
        } else if (z && mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_ALBUM_ART)) {
            bundle.putParcelable(String.valueOf(100), scaleBitmapIfTooBig(mediaMetadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART), i, i2));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_ALBUM_ARTIST)) {
            bundle.putString(String.valueOf(13), mediaMetadata.getString(MediaMetadata.METADATA_KEY_ALBUM_ARTIST));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_ARTIST)) {
            bundle.putString(String.valueOf(2), mediaMetadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_AUTHOR)) {
            bundle.putString(String.valueOf(3), mediaMetadata.getString(MediaMetadata.METADATA_KEY_AUTHOR));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_COMPILATION)) {
            bundle.putString(String.valueOf(15), mediaMetadata.getString(MediaMetadata.METADATA_KEY_COMPILATION));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_COMPOSER)) {
            bundle.putString(String.valueOf(4), mediaMetadata.getString(MediaMetadata.METADATA_KEY_COMPOSER));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_DATE)) {
            bundle.putString(String.valueOf(5), mediaMetadata.getString(MediaMetadata.METADATA_KEY_DATE));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_DISC_NUMBER)) {
            bundle.putLong(String.valueOf(14), mediaMetadata.getLong(MediaMetadata.METADATA_KEY_DISC_NUMBER));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_DURATION)) {
            bundle.putLong(String.valueOf(9), mediaMetadata.getLong(MediaMetadata.METADATA_KEY_DURATION));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_GENRE)) {
            bundle.putString(String.valueOf(6), mediaMetadata.getString(MediaMetadata.METADATA_KEY_GENRE));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_NUM_TRACKS)) {
            bundle.putLong(String.valueOf(10), mediaMetadata.getLong(MediaMetadata.METADATA_KEY_NUM_TRACKS));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_RATING)) {
            bundle.putParcelable(String.valueOf(101), mediaMetadata.getRating(MediaMetadata.METADATA_KEY_RATING));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_USER_RATING)) {
            bundle.putParcelable(String.valueOf(268435457), mediaMetadata.getRating(MediaMetadata.METADATA_KEY_USER_RATING));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_TITLE)) {
            bundle.putString(String.valueOf(7), mediaMetadata.getString(MediaMetadata.METADATA_KEY_TITLE));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_TRACK_NUMBER)) {
            bundle.putLong(String.valueOf(0), mediaMetadata.getLong(MediaMetadata.METADATA_KEY_TRACK_NUMBER));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_WRITER)) {
            bundle.putString(String.valueOf(11), mediaMetadata.getString(MediaMetadata.METADATA_KEY_WRITER));
        }
        if (mediaMetadata.containsKey(MediaMetadata.METADATA_KEY_YEAR)) {
            bundle.putLong(String.valueOf(8), mediaMetadata.getLong(MediaMetadata.METADATA_KEY_YEAR));
        }
        return bundle;
    }

    public MediaSession getSession(PendingIntent pendingIntent) {
        SessionHolder sessionHolder = this.mSessions.get(pendingIntent);
        if (sessionHolder == null) {
            return null;
        }
        return sessionHolder.mSession;
    }

    public void sendMediaButtonEvent(KeyEvent keyEvent, boolean z) {
        if (keyEvent == null) {
            Log.w(TAG, "Tried to send a null key event. Ignoring.");
            return;
        }
        this.mCommunicationManager.dispatchMediaKeyEvent(keyEvent, z);
        if (DEBUG) {
            Log.d(TAG, "dispatched media key " + keyEvent);
        }
    }

    public void sendVolumeKeyEvent(KeyEvent keyEvent, int i, boolean z) {
        if (keyEvent == null) {
            Log.w(TAG, "Tried to send a null key event. Ignoring.");
        } else {
            this.mSessionManager.dispatchVolumeKeyEvent(keyEvent, i, z);
        }
    }

    public void sendAdjustVolumeBy(int i, int i2, int i3) {
        this.mSessionManager.dispatchAdjustVolume(i, i2, i3);
        if (DEBUG) {
            Log.d(TAG, "dispatched volume adjustment");
        }
    }

    public boolean isGlobalPriorityActive() {
        return this.mSessionManager.isGlobalPriorityActive();
    }

    public void addRccListener(PendingIntent pendingIntent, MediaSession.Callback callback) {
        if (pendingIntent == null) {
            Log.w(TAG, "Pending intent was null, can't add rcc listener.");
            return;
        }
        SessionHolder holder = getHolder(pendingIntent, true);
        if (holder == null) {
            return;
        }
        if (holder.mRccListener != null && holder.mRccListener == callback) {
            if (DEBUG) {
                Log.d(TAG, "addRccListener listener already added.");
                return;
            }
            return;
        }
        holder.mRccListener = callback;
        holder.mFlags |= 2;
        holder.mSession.setFlags(holder.mFlags);
        holder.update();
        if (DEBUG) {
            Log.d(TAG, "Added rcc listener for " + pendingIntent + MediaMetrics.SEPARATOR);
        }
    }

    public void removeRccListener(PendingIntent pendingIntent) {
        SessionHolder holder;
        if (pendingIntent == null || (holder = getHolder(pendingIntent, false)) == null || holder.mRccListener == null) {
            return;
        }
        holder.mRccListener = null;
        holder.mFlags &= -3;
        holder.mSession.setFlags(holder.mFlags);
        holder.update();
        if (DEBUG) {
            Log.d(TAG, "Removed rcc listener for " + pendingIntent + MediaMetrics.SEPARATOR);
        }
    }

    public void addMediaButtonListener(PendingIntent pendingIntent, ComponentName componentName, Context context) {
        if (pendingIntent == null) {
            Log.w(TAG, "Pending intent was null, can't addMediaButtonListener.");
            return;
        }
        SessionHolder holder = getHolder(pendingIntent, true);
        if (holder == null) {
            return;
        }
        if (holder.mMediaButtonListener != null && DEBUG) {
            Log.d(TAG, "addMediaButtonListener already added " + pendingIntent);
        }
        holder.mMediaButtonListener = new MediaButtonListener(pendingIntent, context);
        holder.mFlags |= 1;
        holder.mSession.setFlags(holder.mFlags);
        holder.mSession.setMediaButtonReceiver(pendingIntent);
        holder.update();
        if (DEBUG) {
            Log.d(TAG, "addMediaButtonListener added " + pendingIntent);
        }
    }

    public void removeMediaButtonListener(PendingIntent pendingIntent) {
        SessionHolder holder;
        if (pendingIntent == null || (holder = getHolder(pendingIntent, false)) == null || holder.mMediaButtonListener == null) {
            return;
        }
        holder.mFlags &= -2;
        holder.mSession.setFlags(holder.mFlags);
        holder.mMediaButtonListener = null;
        holder.update();
        if (DEBUG) {
            Log.d(TAG, "removeMediaButtonListener removed " + pendingIntent);
        }
    }

    private static Bitmap scaleBitmapIfTooBig(Bitmap bitmap, int i, int i2) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > i || height > i2) {
                float f = width;
                float f2 = height;
                float fMin = Math.min(i / f, i2 / f2);
                int iRound = Math.round(f * fMin);
                int iRound2 = Math.round(fMin * f2);
                Bitmap.Config config = bitmap.getConfig();
                if (config == null) {
                    config = Bitmap.Config.ARGB_8888;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iRound, iRound2, config);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, (Rect) null, new RectF(0.0f, 0.0f, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight()), paint);
                return bitmapCreateBitmap;
            }
        }
        return bitmap;
    }

    private SessionHolder getHolder(PendingIntent pendingIntent, boolean z) {
        SessionHolder sessionHolder = this.mSessions.get(pendingIntent);
        if (sessionHolder != null || !z) {
            return sessionHolder;
        }
        MediaSession mediaSession = new MediaSession(this.mContext, "MediaSessionHelper-" + pendingIntent.getCreatorPackage());
        mediaSession.setActive(true);
        SessionHolder sessionHolder2 = new SessionHolder(mediaSession, pendingIntent);
        this.mSessions.put(pendingIntent, sessionHolder2);
        return sessionHolder2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendKeyEvent(PendingIntent pendingIntent, Context context, Intent intent) {
        try {
            pendingIntent.send(context, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            Log.e(TAG, "Error sending media key down event:", e);
        }
    }

    private static final class MediaButtonListener extends MediaSession.Callback {
        private final Context mContext;
        private final PendingIntent mPendingIntent;

        public MediaButtonListener(PendingIntent pendingIntent, Context context) {
            this.mPendingIntent = pendingIntent;
            this.mContext = context;
        }

        @Override // android.media.session.MediaSession.Callback
        public boolean onMediaButtonEvent(Intent intent) {
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            return true;
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPlay() {
            sendKeyEvent(126);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPause() {
            sendKeyEvent(127);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToNext() {
            sendKeyEvent(87);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToPrevious() {
            sendKeyEvent(88);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onFastForward() {
            sendKeyEvent(90);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onRewind() {
            sendKeyEvent(89);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onStop() {
            sendKeyEvent(86);
        }

        private void sendKeyEvent(int i) {
            KeyEvent keyEvent = new KeyEvent(0, i);
            Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            intent.addFlags(268435456);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(1, i));
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            if (MediaSessionLegacyHelper.DEBUG) {
                Log.d(MediaSessionLegacyHelper.TAG, "Sent " + i + " to pending intent " + this.mPendingIntent);
            }
        }
    }

    private class SessionHolder {
        public SessionCallback mCb;
        public int mFlags;
        public MediaButtonListener mMediaButtonListener;
        public final PendingIntent mPi;
        public MediaSession.Callback mRccListener;
        public final MediaSession mSession;

        public SessionHolder(MediaSession mediaSession, PendingIntent pendingIntent) {
            this.mSession = mediaSession;
            this.mPi = pendingIntent;
        }

        public void update() {
            if (this.mMediaButtonListener == null && this.mRccListener == null) {
                this.mSession.setCallback(null);
                this.mSession.release();
                this.mCb = null;
                MediaSessionLegacyHelper.this.mSessions.remove(this.mPi);
                return;
            }
            if (this.mCb == null) {
                this.mCb = new SessionCallback();
                this.mSession.setCallback(this.mCb, new Handler(Looper.getMainLooper()));
            }
        }

        private class SessionCallback extends MediaSession.Callback {
            private SessionCallback() {
            }

            @Override // android.media.session.MediaSession.Callback
            public boolean onMediaButtonEvent(Intent intent) {
                if (SessionHolder.this.mMediaButtonListener == null) {
                    return true;
                }
                SessionHolder.this.mMediaButtonListener.onMediaButtonEvent(intent);
                return true;
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPlay() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onPlay();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPause() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onPause();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToNext() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onSkipToNext();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToPrevious() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onSkipToPrevious();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onFastForward() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onFastForward();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onRewind() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onRewind();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onStop() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onStop();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSeekTo(long j) {
                if (SessionHolder.this.mRccListener != null) {
                    SessionHolder.this.mRccListener.onSeekTo(j);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSetRating(Rating rating) {
                if (SessionHolder.this.mRccListener != null) {
                    SessionHolder.this.mRccListener.onSetRating(rating);
                }
            }
        }
    }
}
