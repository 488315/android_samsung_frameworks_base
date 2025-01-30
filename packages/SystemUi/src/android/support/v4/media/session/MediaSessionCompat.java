package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.media.Rating;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import androidx.media.MediaSessionManager$RemoteUserInfo;
import androidx.media.session.MediaButtonReceiver;
import androidx.versionedparcelable.ParcelImpl;
import androidx.versionedparcelable.VersionedParcelable;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaSessionCompat {
    public static int sMaxBitmapSize;
    public final MediaSessionImpl mImpl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback {
        public CallbackHandler mCallbackHandler;
        public final Object mLock = new Object();
        public final MediaSessionCallbackApi21 mCallbackFwk = new MediaSessionCallbackApi21();
        public WeakReference mSessionImpl = new WeakReference(null);

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class CallbackHandler extends Handler {
            public CallbackHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                MediaSessionImpl mediaSessionImpl;
                Callback callback;
                CallbackHandler callbackHandler;
                if (message.what == 1) {
                    synchronized (Callback.this.mLock) {
                        mediaSessionImpl = (MediaSessionImpl) Callback.this.mSessionImpl.get();
                        callback = Callback.this;
                        callbackHandler = callback.mCallbackHandler;
                    }
                    if (mediaSessionImpl == null || callback != mediaSessionImpl.getCallback() || callbackHandler == null) {
                        return;
                    }
                    mediaSessionImpl.setCurrentControllerInfo((MediaSessionManager$RemoteUserInfo) message.obj);
                    Callback.this.getClass();
                    mediaSessionImpl.setCurrentControllerInfo(null);
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MediaSessionCallbackApi21 extends MediaSession.Callback {
            public MediaSessionCallbackApi21() {
            }

            public final MediaSessionImplApi21 getSessionImplIfCallbackIsSet() {
                MediaSessionImplApi21 mediaSessionImplApi21;
                synchronized (Callback.this.mLock) {
                    mediaSessionImplApi21 = (MediaSessionImplApi21) Callback.this.mSessionImpl.get();
                }
                if (mediaSessionImplApi21 == null || Callback.this != mediaSessionImplApi21.getCallback()) {
                    return null;
                }
                return mediaSessionImplApi21;
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
                VersionedParcelable versionedParcelable;
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                try {
                    if (str.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                        Bundle bundle2 = new Bundle();
                        Token token = sessionImplIfCallbackIsSet.mToken;
                        IMediaSession extraBinder = token.getExtraBinder();
                        bundle2.putBinder("android.support.v4.media.session.EXTRA_BINDER", extraBinder == null ? null : extraBinder.asBinder());
                        synchronized (token.mLock) {
                            versionedParcelable = token.mSession2Token;
                        }
                        if (versionedParcelable == null) {
                            bundle2.putParcelable("android.support.v4.media.session.SESSION_TOKEN2", null);
                        } else {
                            Bundle bundle3 = new Bundle();
                            bundle3.putParcelable("a", new ParcelImpl(versionedParcelable));
                            bundle2.putParcelable("android.support.v4.media.session.SESSION_TOKEN2", bundle3);
                        }
                        resultReceiver.send(0, bundle2);
                    } else if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM")) {
                        Callback callback = Callback.this;
                        callback.getClass();
                    } else if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT")) {
                        Callback callback2 = Callback.this;
                        bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX");
                        callback2.getClass();
                    } else if (str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                        Callback callback3 = Callback.this;
                        callback3.getClass();
                    } else if (!str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                        Callback.this.getClass();
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
                }
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onCustomAction(String str, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                try {
                    if (str.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
                        MediaSessionCompat.ensureClassLoader(bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.PREPARE")) {
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
                        bundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID");
                        MediaSessionCompat.ensureClassLoader(bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
                        bundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY");
                        MediaSessionCompat.ensureClassLoader(bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
                        MediaSessionCompat.ensureClassLoader(bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
                        bundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED");
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
                        bundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE");
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
                        bundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE");
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.SET_RATING")) {
                        MediaSessionCompat.ensureClassLoader(bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                        Callback.this.getClass();
                    } else if (str.equals("android.support.v4.media.session.action.SET_PLAYBACK_SPEED")) {
                        bundle.getFloat("android.support.v4.media.session.action.ARGUMENT_PLAYBACK_SPEED", 1.0f);
                        Callback.this.getClass();
                    } else {
                        Callback.this.getClass();
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaSessionCompat", "Could not unparcel the data.");
                }
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onFastForward() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final boolean onMediaButtonEvent(Intent intent) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return false;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
                return super.onMediaButtonEvent(intent);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPause() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPlay() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPlayFromMediaId(String str, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPlayFromSearch(String str, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPlayFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPrepare() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPrepareFromMediaId(String str, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPrepareFromSearch(String str, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onPrepareFromUri(Uri uri, Bundle bundle) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onRewind() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSeekTo(long j) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSetPlaybackSpeed(float f) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSetRating(Rating rating) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback callback = Callback.this;
                RatingCompat.fromRating(rating);
                callback.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSkipToNext() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSkipToPrevious() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onSkipToQueueItem(long j) {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }

            @Override // android.media.session.MediaSession.Callback
            public final void onStop() {
                MediaSessionImplApi21 sessionImplIfCallbackIsSet = getSessionImplIfCallbackIsSet();
                if (sessionImplIfCallbackIsSet == null) {
                    return;
                }
                Callback.this.getClass();
                sessionImplIfCallbackIsSet.setCurrentControllerInfo(null);
            }
        }

        public final void setSessionImpl(MediaSessionImpl mediaSessionImpl, Handler handler) {
            synchronized (this.mLock) {
                this.mSessionImpl = new WeakReference(mediaSessionImpl);
                CallbackHandler callbackHandler = this.mCallbackHandler;
                CallbackHandler callbackHandler2 = null;
                if (callbackHandler != null) {
                    callbackHandler.removeCallbacksAndMessages(null);
                }
                if (mediaSessionImpl != null && handler != null) {
                    callbackHandler2 = new CallbackHandler(handler.getLooper());
                }
                this.mCallbackHandler = callbackHandler2;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MediaSessionImpl {
        Callback getCallback();

        Token getSessionToken();

        void setCurrentControllerInfo(MediaSessionManager$RemoteUserInfo mediaSessionManager$RemoteUserInfo);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaSessionImplApi22 extends MediaSessionImplApi21 {
        public MediaSessionImplApi22(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        public MediaSessionImplApi22(Object obj) {
            super(obj);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaSessionImplApi28 extends MediaSessionImplApi22 {
        public MediaSessionImplApi28(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        public MediaSessionImplApi28(Object obj) {
            super(obj);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21, android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        public final void setCurrentControllerInfo(MediaSessionManager$RemoteUserInfo mediaSessionManager$RemoteUserInfo) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaSessionImplApi29 extends MediaSessionImplApi28 {
        public MediaSessionImplApi29(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImplApi21
        public final MediaSession createFwkMediaSession(Context context, String str, Bundle bundle) {
            return new MediaSession(context, str, bundle);
        }

        public MediaSessionImplApi29(Object obj) {
            super(obj);
            this.mSessionInfo = ((MediaSession) obj).getController().getSessionInfo();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.QueueItem.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new QueueItem[i];
            }
        };
        public final MediaDescriptionCompat mDescription;
        public final long mId;

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this(null, mediaDescriptionCompat, j);
        }

        public static void fromQueueItemList(List list) {
            QueueItem queueItem;
            if (list != null) {
                ArrayList arrayList = new ArrayList(list.size());
                for (Object obj : list) {
                    if (obj != null) {
                        MediaSession.QueueItem queueItem2 = (MediaSession.QueueItem) obj;
                        queueItem = new QueueItem(queueItem2, MediaDescriptionCompat.fromMediaDescription(queueItem2.getDescription()), queueItem2.getQueueId());
                    } else {
                        queueItem = null;
                    }
                    arrayList.add(queueItem);
                }
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            this.mDescription.writeToParcel(parcel, i);
            parcel.writeLong(this.mId);
        }

        private QueueItem(MediaSession.QueueItem queueItem, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null");
            }
            if (j == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescriptionCompat;
            this.mId = j;
        }

        public QueueItem(Parcel parcel) {
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.Token.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new Token(parcel.readParcelable(null));
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Token[i];
            }
        };
        public IMediaSession mExtraBinder;
        public final Object mInner;
        public final Object mLock;
        public VersionedParcelable mSession2Token;

        public Token(Object obj) {
            this(obj, null, null);
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.mInner;
            if (obj2 == null) {
                return token.mInner == null;
            }
            Object obj3 = token.mInner;
            if (obj3 == null) {
                return false;
            }
            return obj2.equals(obj3);
        }

        public final IMediaSession getExtraBinder() {
            IMediaSession iMediaSession;
            synchronized (this.mLock) {
                iMediaSession = this.mExtraBinder;
            }
            return iMediaSession;
        }

        public final int hashCode() {
            Object obj = this.mInner;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable((Parcelable) this.mInner, i);
        }

        public Token(Object obj, IMediaSession iMediaSession) {
            this(obj, iMediaSession, null);
        }

        public Token(Object obj, IMediaSession iMediaSession, VersionedParcelable versionedParcelable) {
            this.mLock = new Object();
            this.mInner = obj;
            this.mExtraBinder = iMediaSession;
            this.mSession2Token = versionedParcelable;
        }
    }

    public MediaSessionCompat(Context context, String str) {
        this(context, str, null, null);
    }

    public static void ensureClassLoader(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaSessionCompat.class.getClassLoader());
        }
    }

    public static Bundle unparcelWithClassLoader(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        ensureClassLoader(bundle);
        try {
            bundle.isEmpty();
            return bundle;
        } catch (BadParcelableException unused) {
            Log.e("MediaSessionCompat", "Could not unparcel the data.");
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        };
        public final ResultReceiver mResultReceiver;

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            this.mResultReceiver.writeToParcel(parcel, i);
        }

        public ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this(context, str, componentName, pendingIntent, null);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent, Bundle bundle) {
        this(context, str, componentName, pendingIntent, bundle, null);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent, Bundle bundle, VersionedParcelable versionedParcelable) {
        new ArrayList();
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                if (componentName == null) {
                    int i = MediaButtonReceiver.$r8$clinit;
                    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent.setPackage(context.getPackageName());
                    List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
                    if (queryBroadcastReceivers.size() == 1) {
                        ActivityInfo activityInfo = queryBroadcastReceivers.get(0).activityInfo;
                        componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
                    } else {
                        if (queryBroadcastReceivers.size() > 1) {
                            Log.w("MediaButtonReceiver", "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
                        }
                        componentName = null;
                    }
                    if (componentName == null) {
                        Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
                    }
                }
                if (componentName != null && pendingIntent == null) {
                    Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent2.setComponent(componentName);
                    pendingIntent = PendingIntent.getBroadcast(context, 0, intent2, QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING);
                }
                MediaSessionImplApi29 mediaSessionImplApi29 = new MediaSessionImplApi29(context, str, versionedParcelable, bundle);
                this.mImpl = mediaSessionImplApi29;
                Handler handler = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
                Callback callback = new Callback(this) { // from class: android.support.v4.media.session.MediaSessionCompat.1
                };
                synchronized (mediaSessionImplApi29.mLock) {
                    mediaSessionImplApi29.mCallback = callback;
                    mediaSessionImplApi29.mSessionFwk.setCallback(callback.mCallbackFwk, handler);
                    callback.setSessionImpl(mediaSessionImplApi29, handler);
                }
                mediaSessionImplApi29.setMediaButtonReceiver(pendingIntent);
                new MediaControllerCompat(context, this);
                if (sMaxBitmapSize == 0) {
                    sMaxBitmapSize = (int) (TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics()) + 0.5f);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("tag must not be null or empty");
        }
        throw new IllegalArgumentException("context must not be null");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaSessionImplApi21 implements MediaSessionImpl {
        public Callback mCallback;
        public final MediaSession mSessionFwk;
        public Bundle mSessionInfo;
        public final Token mToken;
        public final Object mLock = new Object();
        public final RemoteCallbackList mExtraControllerCallbacks = new RemoteCallbackList();

        public MediaSessionImplApi21(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            MediaSession createFwkMediaSession = createFwkMediaSession(context, str, bundle);
            this.mSessionFwk = createFwkMediaSession;
            this.mToken = new Token(createFwkMediaSession.getSessionToken(), new ExtraSession(), versionedParcelable);
            this.mSessionInfo = bundle;
            createFwkMediaSession.setFlags(3);
        }

        public MediaSession createFwkMediaSession(Context context, String str, Bundle bundle) {
            return new MediaSession(context, str);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        public final Callback getCallback() {
            Callback callback;
            synchronized (this.mLock) {
                callback = this.mCallback;
            }
            return callback;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        public final Token getSessionToken() {
            return this.mToken;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.MediaSessionImpl
        public void setCurrentControllerInfo(MediaSessionManager$RemoteUserInfo mediaSessionManager$RemoteUserInfo) {
            synchronized (this.mLock) {
            }
        }

        public final void setMediaButtonReceiver(PendingIntent pendingIntent) {
            this.mSessionFwk.setMediaButtonReceiver(pendingIntent);
        }

        public MediaSessionImplApi21(Object obj) {
            if (obj instanceof MediaSession) {
                MediaSession mediaSession = (MediaSession) obj;
                this.mSessionFwk = mediaSession;
                this.mToken = new Token(mediaSession.getSessionToken(), new ExtraSession());
                this.mSessionInfo = null;
                mediaSession.setFlags(3);
                return;
            }
            throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ExtraSession extends IMediaSession.Stub {
            public ExtraSession() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void adjustVolume(int i, int i2) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void fastForward() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final Bundle getExtras() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final long getFlags() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final String getPackageName() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final PlaybackStateCompat getPlaybackState() {
                MediaSessionImplApi21.this.getClass();
                MediaSessionImplApi21.this.getClass();
                return null;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void getRatingType() {
                MediaSessionImplApi21.this.getClass();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void getRepeatMode() {
                MediaSessionImplApi21.this.getClass();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final Bundle getSessionInfo() {
                if (MediaSessionImplApi21.this.mSessionInfo == null) {
                    return null;
                }
                return new Bundle(MediaSessionImplApi21.this.mSessionInfo);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void getShuffleMode() {
                MediaSessionImplApi21.this.getClass();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final String getTag() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void isCaptioningEnabled() {
                MediaSessionImplApi21.this.getClass();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final boolean isTransportControlEnabled() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void next() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void pause() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void play() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void playFromMediaId(Bundle bundle, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void playFromSearch(Bundle bundle, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void playFromUri(Uri uri, Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void prepare() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void prepareFromMediaId(Bundle bundle, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void prepareFromSearch(Bundle bundle, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void prepareFromUri(Uri uri, Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void previous() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void rate(RatingCompat ratingCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplApi21.this.getClass();
                MediaSessionImplApi21.this.mExtraControllerCallbacks.register(iMediaControllerCallback, new MediaSessionManager$RemoteUserInfo("android.media.session.MediaController", Binder.getCallingPid(), Binder.getCallingUid()));
                synchronized (MediaSessionImplApi21.this.mLock) {
                    MediaSessionImplApi21.this.getClass();
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void removeQueueItemAt(int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void rewind() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void seekTo(long j) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void sendCustomAction(Bundle bundle, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final boolean sendMediaButton(KeyEvent keyEvent) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setCaptioningEnabled(boolean z) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setPlaybackSpeed(float f) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setRepeatMode(int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setShuffleMode(int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setVolumeTo(int i, int i2) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void skipToQueueItem(long j) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void stop() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(iMediaControllerCallback);
                Binder.getCallingPid();
                Binder.getCallingUid();
                synchronized (MediaSessionImplApi21.this.mLock) {
                    MediaSessionImplApi21.this.getClass();
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void getQueue() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void isShuffleModeEnabledRemoved() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void setShuffleModeEnabledRemoved() {
            }
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        new ArrayList();
        this.mImpl = mediaSessionImpl;
        new MediaControllerCompat(context, this);
    }
}
