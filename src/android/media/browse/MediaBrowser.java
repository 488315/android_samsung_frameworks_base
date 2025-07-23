package android.media.browse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.BaseParceledListSlice;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.media.IMediaBrowserService;
import android.service.media.IMediaBrowserServiceCallbacks;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MediaBrowser {
    private static final int CONNECT_STATE_CONNECTED = 3;
    private static final int CONNECT_STATE_CONNECTING = 2;
    private static final int CONNECT_STATE_DISCONNECTED = 1;
    private static final int CONNECT_STATE_DISCONNECTING = 0;
    private static final int CONNECT_STATE_SUSPENDED = 4;
    private static final boolean DBG = false;
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    private static final String TAG = "MediaBrowser";
    private final ConnectionCallback mCallback;
    private final Context mContext;
    private volatile Bundle mExtras;
    private volatile MediaSession.Token mMediaSessionToken;
    private final Bundle mRootHints;
    private volatile String mRootId;
    private IMediaBrowserService mServiceBinder;
    private IMediaBrowserServiceCallbacks mServiceCallbacks;
    private final ComponentName mServiceComponent;
    private MediaServiceConnection mServiceConnection;
    private final Handler mHandler = new Handler();
    private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap<>();
    private volatile int mState = 1;

    public static abstract class ItemCallback {
        public void onError(String str) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }
    }

    public static abstract class SubscriptionCallback {
        Binder mToken = new Binder();

        public void onChildrenLoaded(String str, List<MediaItem> list) {
        }

        public void onChildrenLoaded(String str, List<MediaItem> list, Bundle bundle) {
        }

        public void onError(String str) {
        }

        public void onError(String str, Bundle bundle) {
        }
    }

    public MediaBrowser(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (componentName == null) {
            throw new IllegalArgumentException("service component must not be null");
        }
        if (connectionCallback == null) {
            throw new IllegalArgumentException("connection callback must not be null");
        }
        this.mContext = context;
        this.mServiceComponent = componentName;
        this.mCallback = connectionCallback;
        this.mRootHints = bundle == null ? null : new Bundle(bundle);
    }

    public void connect() {
        if (this.mState != 0 && this.mState != 1) {
            throw new IllegalStateException("connect() called while neither disconnecting nor disconnected (state=" + getStateLabel(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mState = 2;
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser.1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                if (MediaBrowser.this.mState == 0) {
                    return;
                }
                MediaBrowser.this.mState = 2;
                if (MediaBrowser.this.mServiceBinder != null) {
                    throw new RuntimeException("mServiceBinder should be null. Instead it is " + MediaBrowser.this.mServiceBinder);
                }
                if (MediaBrowser.this.mServiceCallbacks != null) {
                    throw new RuntimeException("mServiceCallbacks should be null. Instead it is " + MediaBrowser.this.mServiceCallbacks);
                }
                Intent intent = new Intent(MediaBrowserService.SERVICE_INTERFACE);
                intent.setComponent(MediaBrowser.this.mServiceComponent);
                MediaBrowser.this.mServiceConnection = new MediaServiceConnection();
                try {
                    z = MediaBrowser.this.mContext.bindService(intent, MediaBrowser.this.mServiceConnection, 4097);
                } catch (Exception unused) {
                    Log.e(MediaBrowser.TAG, "Failed binding to service " + MediaBrowser.this.mServiceComponent);
                    z = false;
                }
                if (z) {
                    return;
                }
                MediaBrowser.this.forceCloseConnection();
                MediaBrowser.this.mCallback.onConnectionFailed();
            }
        });
    }

    public void disconnect() {
        this.mState = 0;
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser.2
            @Override // java.lang.Runnable
            public void run() {
                if (MediaBrowser.this.mServiceCallbacks != null) {
                    try {
                        MediaBrowser.this.mServiceBinder.disconnect(MediaBrowser.this.mServiceCallbacks);
                    } catch (RemoteException unused) {
                        Log.w(MediaBrowser.TAG, "RemoteException during connect for " + MediaBrowser.this.mServiceComponent);
                    }
                }
                int i = MediaBrowser.this.mState;
                MediaBrowser.this.forceCloseConnection();
                if (i != 0) {
                    MediaBrowser.this.mState = i;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceCloseConnection() {
        MediaServiceConnection mediaServiceConnection = this.mServiceConnection;
        if (mediaServiceConnection != null) {
            try {
                this.mContext.unbindService(mediaServiceConnection);
            } catch (IllegalArgumentException unused) {
            }
        }
        this.mState = 1;
        this.mServiceConnection = null;
        this.mServiceBinder = null;
        this.mServiceCallbacks = null;
        this.mRootId = null;
        this.mMediaSessionToken = null;
    }

    public boolean isConnected() {
        return this.mState == 3;
    }

    public ComponentName getServiceComponent() {
        if (!isConnected()) {
            throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mServiceComponent;
    }

    public String getRoot() {
        if (!isConnected()) {
            throw new IllegalStateException("getRoot() called while not connected (state=" + getStateLabel(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mRootId;
    }

    public Bundle getExtras() {
        if (!isConnected()) {
            throw new IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mExtras;
    }

    public MediaSession.Token getSessionToken() {
        if (!isConnected()) {
            throw new IllegalStateException("getSessionToken() called while not connected (state=" + this.mState + NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mMediaSessionToken;
    }

    public void subscribe(String str, SubscriptionCallback subscriptionCallback) {
        subscribeInternal(str, null, subscriptionCallback);
    }

    public void subscribe(String str, Bundle bundle, SubscriptionCallback subscriptionCallback) {
        if (bundle == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        subscribeInternal(str, new Bundle(bundle), subscriptionCallback);
    }

    public void unsubscribe(String str) {
        unsubscribeInternal(str, null);
    }

    public void unsubscribe(String str, SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        unsubscribeInternal(str, subscriptionCallback);
    }

    public void getItem(final String str, final ItemCallback itemCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("mediaId cannot be empty.");
        }
        if (itemCallback == null) {
            throw new IllegalArgumentException("cb cannot be null.");
        }
        if (this.mState != 3) {
            Log.i(TAG, "Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new Runnable(this) { // from class: android.media.browse.MediaBrowser.3
                @Override // java.lang.Runnable
                public void run() {
                    itemCallback.onError(str);
                }
            });
            return;
        }
        try {
            this.mServiceBinder.getMediaItem(str, new ResultReceiver(this.mHandler) { // from class: android.media.browse.MediaBrowser.4
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int i, Bundle bundle) {
                    if (MediaBrowser.this.isConnected()) {
                        if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserService.KEY_MEDIA_ITEM)) {
                            itemCallback.onError(str);
                            return;
                        }
                        Parcelable parcelable = bundle.getParcelable(MediaBrowserService.KEY_MEDIA_ITEM);
                        if (parcelable != null && !(parcelable instanceof MediaItem)) {
                            itemCallback.onError(str);
                        } else {
                            itemCallback.onItemLoaded((MediaItem) parcelable);
                        }
                    }
                }
            }, this.mServiceCallbacks);
        } catch (RemoteException unused) {
            Log.i(TAG, "Remote error getting media item.");
            this.mHandler.post(new Runnable(this) { // from class: android.media.browse.MediaBrowser.5
                @Override // java.lang.Runnable
                public void run() {
                    itemCallback.onError(str);
                }
            });
        }
    }

    private void subscribeInternal(String str, Bundle bundle, SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId cannot be empty.");
        }
        if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Subscription subscription = this.mSubscriptions.get(str);
        if (subscription == null) {
            subscription = new Subscription();
            this.mSubscriptions.put(str, subscription);
        }
        subscription.putCallback(this.mContext, bundle, subscriptionCallback);
        if (isConnected()) {
            if (bundle == null) {
                try {
                    this.mServiceBinder.addSubscriptionDeprecated(str, this.mServiceCallbacks);
                } catch (RemoteException unused) {
                    Log.d(TAG, "addSubscription failed with RemoteException parentId=" + str);
                    return;
                }
            }
            this.mServiceBinder.addSubscription(str, subscriptionCallback.mToken, bundle, this.mServiceCallbacks);
        }
    }

    private void unsubscribeInternal(String str, SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId cannot be empty.");
        }
        Subscription subscription = this.mSubscriptions.get(str);
        if (subscription == null) {
            return;
        }
        try {
            if (subscriptionCallback == null) {
                if (isConnected()) {
                    this.mServiceBinder.removeSubscriptionDeprecated(str, this.mServiceCallbacks);
                    this.mServiceBinder.removeSubscription(str, null, this.mServiceCallbacks);
                }
            } else {
                List<SubscriptionCallback> callbacks = subscription.getCallbacks();
                List<Bundle> optionsList = subscription.getOptionsList();
                for (int size = callbacks.size() - 1; size >= 0; size--) {
                    if (callbacks.get(size) == subscriptionCallback) {
                        if (isConnected()) {
                            this.mServiceBinder.removeSubscription(str, subscriptionCallback.mToken, this.mServiceCallbacks);
                        }
                        callbacks.remove(size);
                        optionsList.remove(size);
                    }
                }
            }
        } catch (RemoteException unused) {
            Log.d(TAG, "removeSubscription failed with RemoteException parentId=" + str);
        }
        if (subscription.isEmpty() || subscriptionCallback == null) {
            this.mSubscriptions.remove(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getStateLabel(int i) {
        if (i == 0) {
            return "CONNECT_STATE_DISCONNECTING";
        }
        if (i == 1) {
            return "CONNECT_STATE_DISCONNECTED";
        }
        if (i == 2) {
            return "CONNECT_STATE_CONNECTING";
        }
        if (i == 3) {
            return "CONNECT_STATE_CONNECTED";
        }
        if (i == 4) {
            return "CONNECT_STATE_SUSPENDED";
        }
        return "UNKNOWN/" + i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceConnected(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final String str, final MediaSession.Token token, final Bundle bundle) {
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser.6
            @Override // java.lang.Runnable
            public void run() {
                if (MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnect")) {
                    if (MediaBrowser.this.mState != 2) {
                        Log.w(MediaBrowser.TAG, "onConnect from service while mState=" + MediaBrowser.getStateLabel(MediaBrowser.this.mState) + "... ignoring");
                        return;
                    }
                    MediaBrowser.this.mRootId = str;
                    MediaBrowser.this.mMediaSessionToken = token;
                    MediaBrowser.this.mExtras = bundle;
                    MediaBrowser.this.mState = 3;
                    MediaBrowser.this.mCallback.onConnected();
                    for (Map.Entry entry : MediaBrowser.this.mSubscriptions.entrySet()) {
                        String str2 = (String) entry.getKey();
                        Subscription subscription = (Subscription) entry.getValue();
                        List<SubscriptionCallback> callbacks = subscription.getCallbacks();
                        List<Bundle> optionsList = subscription.getOptionsList();
                        for (int i = 0; i < callbacks.size(); i++) {
                            try {
                                MediaBrowser.this.mServiceBinder.addSubscription(str2, callbacks.get(i).mToken, optionsList.get(i), MediaBrowser.this.mServiceCallbacks);
                            } catch (RemoteException unused) {
                                Log.d(MediaBrowser.TAG, "addSubscription failed with RemoteException parentId=" + str2);
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionFailed(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser.7
            @Override // java.lang.Runnable
            public void run() {
                Log.e(MediaBrowser.TAG, "onConnectFailed for " + MediaBrowser.this.mServiceComponent);
                if (MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnectFailed")) {
                    if (MediaBrowser.this.mState != 2) {
                        Log.w(MediaBrowser.TAG, "onConnect from service while mState=" + MediaBrowser.getStateLabel(MediaBrowser.this.mState) + "... ignoring");
                        return;
                    }
                    MediaBrowser.this.forceCloseConnection();
                    MediaBrowser.this.mCallback.onConnectionFailed();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadChildren(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final String str, final ParceledListSlice parceledListSlice, final Bundle bundle) {
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser.8
            @Override // java.lang.Runnable
            public void run() {
                Subscription subscription;
                SubscriptionCallback callback;
                if (!MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onLoadChildren") || (subscription = (Subscription) MediaBrowser.this.mSubscriptions.get(str)) == null || (callback = subscription.getCallback(MediaBrowser.this.mContext, bundle)) == null) {
                    return;
                }
                BaseParceledListSlice baseParceledListSlice = parceledListSlice;
                List list = baseParceledListSlice == null ? null : baseParceledListSlice.getList();
                Bundle bundle2 = bundle;
                if (bundle2 == null) {
                    if (list == null) {
                        callback.onError(str);
                        return;
                    } else {
                        callback.onChildrenLoaded(str, list);
                        return;
                    }
                }
                if (list == null) {
                    callback.onError(str, bundle2);
                } else {
                    callback.onChildrenLoaded(str, list, bundle2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisconnectRequested(final ServiceCallbacks serviceCallbacks) {
        this.mHandler.post(new Runnable() { // from class: android.media.browse.MediaBrowser$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaBrowser.this.lambda$onDisconnectRequested$0(serviceCallbacks);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisconnectRequested$0(ServiceCallbacks serviceCallbacks) {
        Log.i(TAG, "onDisconnectRequest for " + this.mServiceComponent);
        if (isCurrent(serviceCallbacks, "onDisconnectRequest")) {
            forceCloseConnection();
            this.mCallback.onDisconnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrent(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, String str) {
        if (this.mServiceCallbacks == iMediaBrowserServiceCallbacks && this.mState != 0 && this.mState != 1) {
            return true;
        }
        if (this.mState == 0 || this.mState == 1) {
            return false;
        }
        Log.i(TAG, str + " for " + this.mServiceComponent + " with mServiceConnection=" + this.mServiceCallbacks + " this=" + this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ServiceCallbacks getNewServiceCallbacks() {
        return new ServiceCallbacks(this);
    }

    void dump() {
        Log.d(TAG, "MediaBrowser...");
        Log.d(TAG, "  mServiceComponent=" + this.mServiceComponent);
        Log.d(TAG, "  mCallback=" + this.mCallback);
        Log.d(TAG, "  mRootHints=" + this.mRootHints);
        Log.d(TAG, "  mState=" + getStateLabel(this.mState));
        Log.d(TAG, "  mServiceConnection=" + this.mServiceConnection);
        Log.d(TAG, "  mServiceBinder=" + this.mServiceBinder);
        Log.d(TAG, "  mServiceCallbacks=" + this.mServiceCallbacks);
        Log.d(TAG, "  mRootId=" + this.mRootId);
        Log.d(TAG, "  mMediaSessionToken=" + this.mMediaSessionToken);
    }

    public static class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>() { // from class: android.media.browse.MediaBrowser.MediaItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MediaItem[] newArray(int i) {
                return new MediaItem[i];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescription mDescription;
        private final int mFlags;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public MediaItem(MediaDescription mediaDescription, int i) {
            if (mediaDescription == null) {
                throw new IllegalArgumentException("description cannot be null");
            }
            if (TextUtils.isEmpty(mediaDescription.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
            this.mFlags = i;
            this.mDescription = mediaDescription;
        }

        private MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = MediaDescription.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, i);
        }

        public String toString() {
            return "MediaItem{mFlags=" + this.mFlags + ", mDescription=" + this.mDescription + '}';
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean isBrowsable() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.mFlags & 2) != 0;
        }

        public MediaDescription getDescription() {
            return this.mDescription;
        }

        public String getMediaId() {
            return this.mDescription.getMediaId();
        }
    }

    public static class ConnectionCallback {
        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        public void onDisconnected() {
            onConnectionFailed();
        }
    }

    private class MediaServiceConnection implements ServiceConnection {
        private MediaServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
            postOrRun(new Runnable() { // from class: android.media.browse.MediaBrowser.MediaServiceConnection.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                        MediaBrowser.this.mServiceBinder = IMediaBrowserService.Stub.asInterface(iBinder);
                        MediaBrowser.this.mServiceCallbacks = MediaBrowser.this.getNewServiceCallbacks();
                        MediaBrowser.this.mState = 2;
                        try {
                            MediaBrowser.this.mServiceBinder.connect(MediaBrowser.this.mContext.getPackageName(), MediaBrowser.this.mRootHints, MediaBrowser.this.mServiceCallbacks);
                        } catch (RemoteException unused) {
                            Log.w(MediaBrowser.TAG, "RemoteException during connect for " + MediaBrowser.this.mServiceComponent);
                        }
                    }
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final ComponentName componentName) {
            postOrRun(new Runnable() { // from class: android.media.browse.MediaBrowser.MediaServiceConnection.2
                @Override // java.lang.Runnable
                public void run() {
                    if (MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                        MediaBrowser.this.mServiceBinder = null;
                        MediaBrowser.this.mServiceCallbacks = null;
                        MediaBrowser.this.mState = 4;
                        MediaBrowser.this.mCallback.onConnectionSuspended();
                    }
                }
            });
        }

        private void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == MediaBrowser.this.mHandler.getLooper().getThread()) {
                runnable.run();
            } else {
                MediaBrowser.this.mHandler.post(runnable);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isCurrent(String str) {
            if (MediaBrowser.this.mServiceConnection == this && MediaBrowser.this.mState != 0 && MediaBrowser.this.mState != 1) {
                return true;
            }
            if (MediaBrowser.this.mState == 0 || MediaBrowser.this.mState == 1) {
                return false;
            }
            Log.i(MediaBrowser.TAG, str + " for " + MediaBrowser.this.mServiceComponent + " with mServiceConnection=" + MediaBrowser.this.mServiceConnection + " this=" + this);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ServiceCallbacks extends IMediaBrowserServiceCallbacks.Stub {
        private WeakReference<MediaBrowser> mMediaBrowser;

        ServiceCallbacks(MediaBrowser mediaBrowser) {
            this.mMediaBrowser = new WeakReference<>(mediaBrowser);
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnect(String str, MediaSession.Token token, Bundle bundle) {
            MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onServiceConnected(this, str, token, bundle);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnectFailed() {
            MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onConnectionFailed(this);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildren(String str, ParceledListSlice parceledListSlice, Bundle bundle) {
            MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onLoadChildren(this, str, parceledListSlice, bundle);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onDisconnect() {
            MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onDisconnectRequested(this);
            }
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList();
        private final List<Bundle> mOptionsList = new ArrayList();

        Subscription() {
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public SubscriptionCallback getCallback(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) {
                    return this.mCallbacks.get(i);
                }
            }
            return null;
        }

        public void putCallback(Context context, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) {
                    this.mCallbacks.set(i, subscriptionCallback);
                    return;
                }
            }
            this.mCallbacks.add(subscriptionCallback);
            this.mOptionsList.add(bundle);
        }
    }
}
