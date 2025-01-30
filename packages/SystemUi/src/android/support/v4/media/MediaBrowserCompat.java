package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaBrowserCompat {
    public static final boolean DEBUG = Log.isLoggable("MediaBrowserCompat", 3);
    public final MediaBrowserImplApi26 mImpl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CallbackHandler extends Handler {
        public final WeakReference mCallbackImplRef;
        public WeakReference mCallbacksMessengerRef;

        public CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            this.mCallbackImplRef = new WeakReference(mediaBrowserServiceCallbackImpl);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WeakReference weakReference = this.mCallbacksMessengerRef;
            if (weakReference == null || weakReference.get() == null || this.mCallbackImplRef.get() == null) {
                return;
            }
            Bundle data = message.getData();
            MediaSessionCompat.ensureClassLoader(data);
            MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl = (MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get();
            Messenger messenger = (Messenger) this.mCallbacksMessengerRef.get();
            try {
                int i = message.what;
                if (i == 1) {
                    MediaSessionCompat.ensureClassLoader(data.getBundle("data_root_hints"));
                    mediaBrowserServiceCallbackImpl.onServiceConnected(messenger, data.getString("data_media_item_id"), (MediaSessionCompat.Token) data.getParcelable("data_media_session_token"));
                } else if (i == 2) {
                    mediaBrowserServiceCallbackImpl.onConnectionFailed(messenger);
                } else if (i != 3) {
                    Log.w("MediaBrowserCompat", "Unhandled message: " + message + "\n  Client version: 1\n  Service version: " + message.arg1);
                } else {
                    Bundle bundle = data.getBundle("data_options");
                    MediaSessionCompat.ensureClassLoader(bundle);
                    MediaSessionCompat.ensureClassLoader(data.getBundle("data_notify_children_changed_options"));
                    String string = data.getString("data_media_item_id");
                    data.getParcelableArrayList("data_media_item_list");
                    mediaBrowserServiceCallbackImpl.onLoadChildren(messenger, string, bundle);
                }
            } catch (BadParcelableException unused) {
                Log.e("MediaBrowserCompat", "Could not unparcel the data.");
                if (message.what == 1) {
                    mediaBrowserServiceCallbackImpl.onConnectionFailed(messenger);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CustomActionCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class CustomActionResultReceiver extends ResultReceiver {
        public final CustomActionCallback mCallback;
        public final Bundle mExtras;

        public CustomActionResultReceiver(String str, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler);
            this.mExtras = bundle;
            this.mCallback = customActionCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            if (this.mCallback == null) {
                return;
            }
            MediaSessionCompat.ensureClassLoader(bundle);
            if (i == -1) {
                this.mCallback.getClass();
                return;
            }
            if (i == 0) {
                this.mCallback.getClass();
                return;
            }
            if (i == 1) {
                this.mCallback.getClass();
                return;
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("Unknown result code: ", i, " (extras=");
            m1m.append(this.mExtras);
            m1m.append(", resultData=");
            m1m.append(bundle);
            m1m.append(")");
            Log.w("MediaBrowserCompat", m1m.toString());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ItemCallback {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ItemCallbackApi23 extends MediaBrowser.ItemCallback {
            public ItemCallbackApi23() {
            }

            @Override // android.media.browse.MediaBrowser.ItemCallback
            public final void onError(String str) {
                ItemCallback.this.getClass();
            }

            @Override // android.media.browse.MediaBrowser.ItemCallback
            public final void onItemLoaded(MediaBrowser.MediaItem mediaItem) {
                ItemCallback itemCallback = ItemCallback.this;
                Parcelable.Creator<MediaItem> creator = MediaItem.CREATOR;
                if (mediaItem != null) {
                    new MediaItem(MediaDescriptionCompat.fromMediaDescription(mediaItem.getDescription()), mediaItem.getFlags());
                }
                itemCallback.getClass();
            }
        }

        public ItemCallback() {
            new ItemCallbackApi23();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class ItemReceiver extends ResultReceiver {
        public final ItemCallback mCallback;

        public ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            super(handler);
            this.mCallback = itemCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle = MediaSessionCompat.unparcelWithClassLoader(bundle);
            }
            if (i != 0 || bundle == null || !bundle.containsKey("media_item")) {
                this.mCallback.getClass();
                return;
            }
            Parcelable parcelable = bundle.getParcelable("media_item");
            if (parcelable != null && !(parcelable instanceof MediaItem)) {
                this.mCallback.getClass();
            } else {
                this.mCallback.getClass();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        public MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaBrowserImplApi26 extends MediaBrowserImplApi23 {
        public MediaBrowserImplApi26(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MediaBrowserServiceCallbackImpl {
        void onConnectionFailed(Messenger messenger);

        void onLoadChildren(Messenger messenger, String str, Bundle bundle);

        void onServiceConnected(Messenger messenger, String str, MediaSessionCompat.Token token);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SearchCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class SearchResultReceiver extends ResultReceiver {
        public final SearchCallback mCallback;

        public SearchResultReceiver(String str, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler);
            this.mCallback = searchCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle = MediaSessionCompat.unparcelWithClassLoader(bundle);
            }
            if (i != 0 || bundle == null || !bundle.containsKey("search_results")) {
                this.mCallback.getClass();
                return;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray("search_results");
            if (parcelableArray == null) {
                this.mCallback.getClass();
                return;
            }
            ArrayList arrayList = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                arrayList.add((MediaItem) parcelable);
            }
            this.mCallback.getClass();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ServiceBinderWrapper {
        public final Messenger mMessenger;
        public final Bundle mRootHints;

        public ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
            this.mMessenger = new Messenger(iBinder);
            this.mRootHints = bundle;
        }

        public final void sendRequest(int i, Bundle bundle, Messenger messenger) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 1;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            this.mMessenger.send(obtain);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Subscription {
        public final List mCallbacks = new ArrayList();
        public final List mOptionsList = new ArrayList();

        public final void getCallback(Bundle bundle) {
            int i = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.mOptionsList;
                if (i >= arrayList.size()) {
                    return;
                }
                Bundle bundle2 = (Bundle) arrayList.get(i);
                if (bundle2 == bundle || (bundle2 != null ? !(bundle != null ? !(bundle2.getInt("android.media.browse.extra.PAGE", -1) == bundle.getInt("android.media.browse.extra.PAGE", -1) && bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) == bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1)) : !(bundle2.getInt("android.media.browse.extra.PAGE", -1) == -1 && bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1)) : bundle.getInt("android.media.browse.extra.PAGE", -1) == -1 && bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1)) {
                    return;
                }
                i++;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SubscriptionCallback {
        public final IBinder mToken = new Binder();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public class SubscriptionCallbackApi21 extends MediaBrowser.SubscriptionCallback {
            public SubscriptionCallbackApi21() {
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public final void onChildrenLoaded(String str, List list) {
                SubscriptionCallback.this.getClass();
                SubscriptionCallback subscriptionCallback = SubscriptionCallback.this;
                MediaItem.fromMediaItemList(list);
                subscriptionCallback.getClass();
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public final void onError(String str) {
                SubscriptionCallback.this.getClass();
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SubscriptionCallbackApi26 extends SubscriptionCallbackApi21 {
            public SubscriptionCallbackApi26() {
                super();
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public final void onChildrenLoaded(String str, List list, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                SubscriptionCallback subscriptionCallback = SubscriptionCallback.this;
                MediaItem.fromMediaItemList(list);
                subscriptionCallback.getClass();
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public final void onError(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                SubscriptionCallback.this.getClass();
            }
        }

        public SubscriptionCallback() {
            new SubscriptionCallbackApi26();
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        this.mImpl = new MediaBrowserImplApi26(context, componentName, connectionCallback, bundle);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaBrowserCompat.MediaItem.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new MediaItem[i];
            }
        };
        public final MediaDescriptionCompat mDescription;
        public final int mFlags;

        public MediaItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            }
            if (TextUtils.isEmpty(mediaDescriptionCompat.mMediaId)) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
            this.mFlags = i;
            this.mDescription = mediaDescriptionCompat;
        }

        public static void fromMediaItemList(List list) {
            MediaItem mediaItem;
            if (list != null) {
                ArrayList arrayList = new ArrayList(list.size());
                for (Object obj : list) {
                    if (obj != null) {
                        MediaBrowser.MediaItem mediaItem2 = (MediaBrowser.MediaItem) obj;
                        mediaItem = new MediaItem(MediaDescriptionCompat.fromMediaDescription(mediaItem2.getDescription()), mediaItem2.getFlags());
                    } else {
                        mediaItem = null;
                    }
                    arrayList.add(mediaItem);
                }
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "MediaItem{mFlags=" + this.mFlags + ", mDescription=" + this.mDescription + '}';
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, i);
        }

        public MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MediaBrowserImplApi21 implements MediaBrowserServiceCallbackImpl {
        public final MediaBrowser mBrowserFwk;
        public Messenger mCallbacksMessenger;
        public final Context mContext;
        public MediaSessionCompat.Token mMediaSessionToken;
        public final Bundle mRootHints;
        public ServiceBinderWrapper mServiceBinderWrapper;
        public final CallbackHandler mHandler = new CallbackHandler(this);
        public final ArrayMap mSubscriptions = new ArrayMap();

        public MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.mContext = context;
            Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
            this.mRootHints = bundle2;
            bundle2.putInt("extra_client_version", 1);
            bundle2.putInt("extra_calling_pid", Process.myPid());
            connectionCallback.mConnectionCallbackInternal = this;
            this.mBrowserFwk = new MediaBrowser(context, componentName, connectionCallback.mConnectionCallbackFwk, bundle2);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        public final void onLoadChildren(Messenger messenger, String str, Bundle bundle) {
            if (this.mCallbacksMessenger != messenger) {
                return;
            }
            Subscription subscription = (Subscription) this.mSubscriptions.get(str);
            if (subscription != null) {
                subscription.getCallback(bundle);
            } else if (MediaBrowserCompat.DEBUG) {
                AbstractC0000x2c234b15.m3m("onLoadChildren for id that isn't subscribed id=", str, "MediaBrowserCompat");
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        public final void onConnectionFailed(Messenger messenger) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        public final void onServiceConnected(Messenger messenger, String str, MediaSessionCompat.Token token) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ConnectionCallback {
        public final ConnectionCallbackApi21 mConnectionCallbackFwk = new ConnectionCallbackApi21();
        public MediaBrowserImplApi21 mConnectionCallbackInternal;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ConnectionCallbackApi21 extends MediaBrowser.ConnectionCallback {
            public ConnectionCallbackApi21() {
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public final void onConnected() {
                MediaBrowserImplApi21 mediaBrowserImplApi21 = ConnectionCallback.this.mConnectionCallbackInternal;
                if (mediaBrowserImplApi21 != null) {
                    MediaBrowser mediaBrowser = mediaBrowserImplApi21.mBrowserFwk;
                    try {
                        Bundle extras = mediaBrowser.getExtras();
                        if (extras != null) {
                            extras.getInt("extra_service_version", 0);
                            IBinder binder = extras.getBinder("extra_messenger");
                            if (binder != null) {
                                mediaBrowserImplApi21.mServiceBinderWrapper = new ServiceBinderWrapper(binder, mediaBrowserImplApi21.mRootHints);
                                CallbackHandler callbackHandler = mediaBrowserImplApi21.mHandler;
                                Messenger messenger = new Messenger(callbackHandler);
                                mediaBrowserImplApi21.mCallbacksMessenger = messenger;
                                callbackHandler.getClass();
                                callbackHandler.mCallbacksMessengerRef = new WeakReference(messenger);
                                try {
                                    ServiceBinderWrapper serviceBinderWrapper = mediaBrowserImplApi21.mServiceBinderWrapper;
                                    Context context = mediaBrowserImplApi21.mContext;
                                    Messenger messenger2 = mediaBrowserImplApi21.mCallbacksMessenger;
                                    serviceBinderWrapper.getClass();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("data_package_name", context.getPackageName());
                                    bundle.putInt("data_calling_pid", Process.myPid());
                                    bundle.putBundle("data_root_hints", serviceBinderWrapper.mRootHints);
                                    serviceBinderWrapper.sendRequest(6, bundle, messenger2);
                                } catch (RemoteException unused) {
                                    Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
                                }
                            }
                            IMediaSession asInterface = IMediaSession.Stub.asInterface(extras.getBinder("extra_session_binder"));
                            if (asInterface != null) {
                                MediaSession.Token sessionToken = mediaBrowser.getSessionToken();
                                Parcelable.Creator<MediaSessionCompat.Token> creator = MediaSessionCompat.Token.CREATOR;
                                mediaBrowserImplApi21.mMediaSessionToken = sessionToken != null ? new MediaSessionCompat.Token(sessionToken, asInterface) : null;
                            }
                        }
                    } catch (IllegalStateException e) {
                        Log.e("MediaBrowserCompat", "Unexpected IllegalStateException", e);
                    }
                }
                ConnectionCallback.this.onConnected();
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public final void onConnectionFailed() {
                MediaBrowserImplApi21 mediaBrowserImplApi21 = ConnectionCallback.this.mConnectionCallbackInternal;
                if (mediaBrowserImplApi21 != null) {
                    mediaBrowserImplApi21.getClass();
                }
                ConnectionCallback.this.onConnectionFailed();
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public final void onConnectionSuspended() {
                MediaBrowserImplApi21 mediaBrowserImplApi21 = ConnectionCallback.this.mConnectionCallbackInternal;
                if (mediaBrowserImplApi21 != null) {
                    mediaBrowserImplApi21.mServiceBinderWrapper = null;
                    mediaBrowserImplApi21.mCallbacksMessenger = null;
                    mediaBrowserImplApi21.mMediaSessionToken = null;
                    CallbackHandler callbackHandler = mediaBrowserImplApi21.mHandler;
                    callbackHandler.getClass();
                    callbackHandler.mCallbacksMessengerRef = new WeakReference(null);
                }
                ConnectionCallback.this.onConnectionSuspended();
            }
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }
    }
}
