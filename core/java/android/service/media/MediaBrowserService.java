package android.service.media;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowserUtils;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import com.android.media.flags.Flags;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class MediaBrowserService extends Service {
    private static final boolean DBG = false;
    public static final String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_ERROR = -1;
    private static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    private static final int RESULT_OK = 0;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final String TAG = "MediaBrowserService";
    private ConnectionRecord mCurrentConnectionOnHandler;
    private final Handler mHandler = new Handler();
    private final AtomicReference<ServiceState> mServiceState =
            new AtomicReference<>(new ServiceState());
    private final ServiceBinder mBinder = new ServiceBinder(this.mServiceState.get());

    @Retention(RetentionPolicy.SOURCE)
    private @interface ResultFlags {}

    public abstract BrowserRoot onGetRoot(String str, int i, Bundle bundle);

    public abstract void onLoadChildren(String str, Result<List<MediaBrowser.MediaItem>> result);

    static class ConnectionRecord implements IBinder.DeathRecipient {
        public final IMediaBrowserServiceCallbacks callbacks;
        public final int pid;
        public final String pkg;
        public final BrowserRoot root;
        public final Bundle rootHints;
        public final ServiceState serviceState;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        public final int uid;

        ConnectionRecord(
                ServiceState serviceState,
                String pkg,
                int pid,
                int uid,
                Bundle rootHints,
                IMediaBrowserServiceCallbacks callbacks,
                BrowserRoot root) {
            this.serviceState = serviceState;
            this.pkg = pkg;
            this.pid = pid;
            this.uid = uid;
            this.rootHints = rootHints;
            this.callbacks = callbacks;
            this.root = root;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ConnectionRecord$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ConnectionRecord.this.lambda$binderDied$0();
                        }
                    });
        }

        public /* synthetic */ void lambda$binderDied$0() {
            this.serviceState.mConnections.remove(this.callbacks.asBinder());
        }
    }

    public class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        Result(Object debug) {
            this.mDebug = debug;
        }

        public void sendResult(T result) {
            if (this.mSendResultCalled) {
                throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(result, this.mFlags);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException(
                        "detach() called when detach() had already been called for: "
                                + this.mDebug);
            }
            if (this.mSendResultCalled) {
                throw new IllegalStateException(
                        "detach() called when sendResult() had already been called for: "
                                + this.mDebug);
            }
            this.mDetachCalled = true;
        }

        boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled;
        }

        void setFlags(int flags) {
            this.mFlags = flags;
        }

        void onResultSent(T result, int flags) {}
    }

    static class ServiceBinder extends IMediaBrowserService.Stub {
        private final AtomicReference<WeakReference<ServiceState>> mServiceState;

        private ServiceBinder(ServiceState serviceState) {
            this.mServiceState = new AtomicReference<>();
            setServiceState(serviceState);
        }

        public void setServiceState(ServiceState serviceState) {
            this.mServiceState.set(new WeakReference<>(serviceState));
        }

        @Override // android.service.media.IMediaBrowserService
        public void connect(
                final String pkg,
                final Bundle rootHints,
                final IMediaBrowserServiceCallbacks callbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            final int pid = Binder.getCallingPid();
            final int uid = Binder.getCallingUid();
            if (!serviceState.isValidPackage(pkg, uid)) {
                throw new IllegalArgumentException(
                        "Package/uid mismatch: uid=" + uid + " package=" + pkg);
            }
            serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceState.this.connectOnHandler(
                                    pkg, pid, uid, rootHints, callbacks);
                        }
                    });
        }

        @Override // android.service.media.IMediaBrowserService
        public void disconnect(final IMediaBrowserServiceCallbacks callbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceState.this.removeConnectionRecordOnHandler(
                                    callbacks);
                        }
                    });
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscriptionDeprecated(String id, IMediaBrowserServiceCallbacks callbacks) {}

        @Override // android.service.media.IMediaBrowserService
        public void addSubscription(
                final String id,
                final IBinder token,
                final Bundle options,
                final IMediaBrowserServiceCallbacks callbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceState.this.addSubscriptionOnHandler(
                                    id, callbacks, token, options);
                        }
                    });
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscriptionDeprecated(
                String id, IMediaBrowserServiceCallbacks callbacks) {}

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscription(
                final String id,
                final IBinder token,
                final IMediaBrowserServiceCallbacks callbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceBinder.lambda$removeSubscription$3(
                                    MediaBrowserService.ServiceState.this, id, callbacks, token);
                        }
                    });
        }

        static /* synthetic */ void lambda$removeSubscription$3(
                ServiceState serviceState,
                String id,
                IMediaBrowserServiceCallbacks callbacks,
                IBinder token) {
            if (!serviceState.removeSubscriptionOnHandler(id, callbacks, token)) {
                Log.w(
                        MediaBrowserService.TAG,
                        "removeSubscription for id with no subscription: " + id);
            }
        }

        @Override // android.service.media.IMediaBrowserService
        public void getMediaItem(
                final String mediaId,
                final ResultReceiver receiver,
                final IMediaBrowserServiceCallbacks callbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceState.this.performLoadItemOnHandler(
                                    mediaId, callbacks, receiver);
                        }
                    });
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {}

    public void onLoadChildren(
            String parentId, Result<List<MediaBrowser.MediaItem>> result, Bundle options) {
        result.setFlags(1);
        onLoadChildren(parentId, result);
    }

    public void onLoadItem(String itemId, Result<MediaBrowser.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void setSessionToken(final MediaSession.Token token) {
        final ServiceState serviceState = this.mServiceState.get();
        if (token == null) {
            if (!Flags.enableNullSessionInMediaBrowserService()) {
                throw new IllegalArgumentException("Session token may not be null.");
            }
            if (serviceState.mSession != null) {
                ServiceState newServiceState = new ServiceState();
                this.mBinder.setServiceState(newServiceState);
                this.mServiceState.set(newServiceState);
                serviceState.release();
                return;
            }
            return;
        }
        if (serviceState.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        }
        serviceState.mSession = token;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.service.media.MediaBrowserService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaBrowserService.ServiceState.this
                                .notifySessionTokenInitializedOnHandler(token);
                    }
                });
    }

    public MediaSession.Token getSessionToken() {
        return this.mServiceState.get().mSession;
    }

    public final Bundle getBrowserRootHints() {
        ConnectionRecord currentConnection = this.mCurrentConnectionOnHandler;
        if (currentConnection == null) {
            throw new IllegalStateException(
                    "This should be called inside of onGetRoot or onLoadChildren or onLoadItem"
                        + " methods");
        }
        if (currentConnection.rootHints == null) {
            return null;
        }
        return new Bundle(currentConnection.rootHints);
    }

    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        ConnectionRecord currentConnection = this.mCurrentConnectionOnHandler;
        if (currentConnection == null) {
            throw new IllegalStateException(
                    "This should be called inside of onGetRoot or onLoadChildren or onLoadItem"
                        + " methods");
        }
        return new MediaSessionManager.RemoteUserInfo(
                currentConnection.pkg, currentConnection.pid, currentConnection.uid);
    }

    public void notifyChildrenChanged(String parentId) {
        notifyChildrenChanged(parentId, Bundle.EMPTY);
    }

    public void notifyChildrenChanged(final String parentId, final Bundle options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.service.media.MediaBrowserService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaBrowserService.this.lambda$notifyChildrenChanged$1(parentId, options);
                    }
                });
    }

    public /* synthetic */ void lambda$notifyChildrenChanged$1(String parentId, Bundle options) {
        this.mServiceState.get().notifyChildrenChangeOnHandler(parentId, options);
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(String rootId, Bundle extras) {
            if (rootId == null) {
                throw new IllegalArgumentException(
                        "The root id in BrowserRoot cannot be null. Use null for BrowserRoot"
                            + " instead.");
            }
            this.mRootId = rootId;
            this.mExtras = extras;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    class ServiceState {
        private final ArrayMap<IBinder, ConnectionRecord> mConnections;
        private MediaSession.Token mSession;

        private ServiceState() {
            this.mConnections = new ArrayMap<>();
        }

        public ServiceBinder getBinder() {
            return MediaBrowserService.this.mBinder;
        }

        public void postOnHandler(Runnable runnable) {
            MediaBrowserService.this.mHandler.post(runnable);
        }

        public void release() {
            MediaBrowserService.this.mHandler.postAtFrontOfQueue(
                    new Runnable() { // from class:
                                     // android.service.media.MediaBrowserService$ServiceState$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaBrowserService.ServiceState.this.clearConnectionsOnHandler();
                        }
                    });
        }

        public void clearConnectionsOnHandler() {
            Iterator<ConnectionRecord> iterator = this.mConnections.values().iterator();
            while (iterator.hasNext()) {
                ConnectionRecord record = iterator.next();
                iterator.remove();
                try {
                    record.callbacks.onDisconnect();
                } catch (RemoteException exception) {
                    Log.w(
                            MediaBrowserService.TAG,
                            TextUtils.formatSimple("onDisconnectRequest for %s failed", record.pkg),
                            exception);
                }
            }
        }

        public void removeConnectionRecordOnHandler(IMediaBrowserServiceCallbacks callbacks) {
            IBinder b = callbacks.asBinder();
            ConnectionRecord old = this.mConnections.remove(b);
            if (old != null) {
                old.callbacks.asBinder().unlinkToDeath(old, 0);
            }
        }

        public void notifySessionTokenInitializedOnHandler(MediaSession.Token token) {
            Iterator<ConnectionRecord> iter = this.mConnections.values().iterator();
            while (iter.hasNext()) {
                ConnectionRecord connection = iter.next();
                try {
                    connection.callbacks.onConnect(
                            connection.root.getRootId(), token, connection.root.getExtras());
                } catch (RemoteException e) {
                    Log.w(
                            MediaBrowserService.TAG,
                            "Connection for " + connection.pkg + " is no longer valid.");
                    iter.remove();
                }
            }
        }

        public void notifyChildrenChangeOnHandler(String parentId, Bundle options) {
            for (IBinder binder : this.mConnections.keySet()) {
                ConnectionRecord connection = this.mConnections.get(binder);
                List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(parentId);
                if (callbackList != null) {
                    for (Pair<IBinder, Bundle> callback : callbackList) {
                        if (MediaBrowserUtils.hasDuplicatedItems(options, callback.second)) {
                            performLoadChildrenOnHandler(parentId, connection, callback.second);
                        }
                    }
                }
            }
        }

        public void addSubscriptionOnHandler(
                String id, IMediaBrowserServiceCallbacks callbacks, IBinder token, Bundle options) {
            IBinder b = callbacks.asBinder();
            ConnectionRecord connection = this.mConnections.get(b);
            if (connection == null) {
                Log.w(
                        MediaBrowserService.TAG,
                        "addSubscription for callback that isn't registered id=" + id);
                return;
            }
            List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
            if (callbackList == null) {
                callbackList = new ArrayList();
            }
            for (Pair<IBinder, Bundle> callback : callbackList) {
                if (token == callback.first
                        && MediaBrowserUtils.areSameOptions(options, callback.second)) {
                    return;
                }
            }
            callbackList.add(new Pair<>(token, options));
            connection.subscriptions.put(id, callbackList);
            performLoadChildrenOnHandler(id, connection, options);
        }

        public void connectOnHandler(
                String pkg,
                int pid,
                int uid,
                Bundle rootHints,
                IMediaBrowserServiceCallbacks callbacks) {
            IBinder b = callbacks.asBinder();
            this.mConnections.remove(b);
            MediaBrowserService.this.mCurrentConnectionOnHandler =
                    new ConnectionRecord(this, pkg, pid, uid, rootHints, callbacks, null);
            BrowserRoot root = MediaBrowserService.this.onGetRoot(pkg, uid, rootHints);
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (root == null) {
                Log.i(
                        MediaBrowserService.TAG,
                        "No root for client " + pkg + " from service " + getClass().getName());
                try {
                    callbacks.onConnectFailed();
                } catch (RemoteException e) {
                    Log.w(
                            MediaBrowserService.TAG,
                            "Calling onConnectFailed() failed. Ignoring. pkg=" + pkg);
                }
                return;
            }
            try {
                ConnectionRecord connection =
                        new ConnectionRecord(this, pkg, pid, uid, rootHints, callbacks, root);
                this.mConnections.put(b, connection);
                b.linkToDeath(connection, 0);
                if (this.mSession != null) {
                    try {
                        callbacks.onConnect(
                                connection.root.getRootId(),
                                this.mSession,
                                connection.root.getExtras());
                    } catch (RemoteException e2) {
                        Log.w(
                                MediaBrowserService.TAG,
                                "Calling onConnect() failed. Dropping client. pkg=" + pkg);
                        this.mConnections.remove(b);
                    }
                }
            } catch (RemoteException e3) {
            }
        }

        public boolean removeSubscriptionOnHandler(
                String id, IMediaBrowserServiceCallbacks callbacks, IBinder token) {
            IBinder b = callbacks.asBinder();
            ConnectionRecord connection = this.mConnections.get(b);
            if (connection == null) {
                Log.w(
                        MediaBrowserService.TAG,
                        "removeSubscription for callback that isn't registered id=" + id);
                return true;
            }
            if (token == null) {
                return connection.subscriptions.remove(id) != null;
            }
            boolean removed = false;
            List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
            if (callbackList != null) {
                Iterator<Pair<IBinder, Bundle>> iter = callbackList.iterator();
                while (iter.hasNext()) {
                    if (token == iter.next().first) {
                        removed = true;
                        iter.remove();
                    }
                }
                if (callbackList.isEmpty()) {
                    connection.subscriptions.remove(id);
                }
            }
            return removed;
        }

        public void performLoadChildrenOnHandler(
                final String parentId, final ConnectionRecord connection, final Bundle options) {
            Result<List<MediaBrowser.MediaItem>> result =
                    new Result<List<MediaBrowser.MediaItem>>(
                            parentId) { // from class:
                                        // android.service.media.MediaBrowserService.ServiceState.1
                        {
                            MediaBrowserService mediaBrowserService = MediaBrowserService.this;
                        }

                        @Override // android.service.media.MediaBrowserService.Result
                        public void onResultSent(List<MediaBrowser.MediaItem> list, int flag) {
                            List<MediaBrowser.MediaItem> filteredList;
                            if (ServiceState.this.mConnections.get(connection.callbacks.asBinder())
                                    != connection) {
                                return;
                            }
                            if ((flag & 1) != 0) {
                                filteredList = MediaBrowserUtils.applyPagingOptions(list, options);
                            } else {
                                filteredList = list;
                            }
                            ParceledListSlice<MediaBrowser.MediaItem> pls = null;
                            if (filteredList != null) {
                                pls = new ParceledListSlice<>(filteredList);
                                pls.setInlineCountLimit(1);
                            }
                            try {
                                connection.callbacks.onLoadChildren(parentId, pls, options);
                            } catch (RemoteException e) {
                                Log.w(
                                        MediaBrowserService.TAG,
                                        "Calling onLoadChildren() failed for id="
                                                + parentId
                                                + " package="
                                                + connection.pkg);
                            }
                        }
                    };
            MediaBrowserService.this.mCurrentConnectionOnHandler = connection;
            if (options == null) {
                MediaBrowserService.this.onLoadChildren(parentId, result);
            } else {
                MediaBrowserService.this.onLoadChildren(parentId, result, options);
            }
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (!result.isDone()) {
                throw new IllegalStateException(
                        "onLoadChildren must call detach() or sendResult() before returning for"
                            + " package="
                                + connection.pkg
                                + " id="
                                + parentId);
            }
        }

        public void performLoadItemOnHandler(
                final String itemId,
                IMediaBrowserServiceCallbacks callbacks,
                final ResultReceiver receiver) {
            IBinder b = callbacks.asBinder();
            final ConnectionRecord connection = this.mConnections.get(b);
            if (connection == null) {
                Log.w(
                        MediaBrowserService.TAG,
                        "getMediaItem for callback that isn't registered id=" + itemId);
                return;
            }
            Result<MediaBrowser.MediaItem> result =
                    new Result<MediaBrowser.MediaItem>(
                            itemId) { // from class:
                                      // android.service.media.MediaBrowserService.ServiceState.2
                        {
                            MediaBrowserService mediaBrowserService = MediaBrowserService.this;
                        }

                        @Override // android.service.media.MediaBrowserService.Result
                        public void onResultSent(MediaBrowser.MediaItem item, int flag) {
                            if (ServiceState.this.mConnections.get(connection.callbacks.asBinder())
                                    != connection) {
                                return;
                            }
                            if ((flag & 2) != 0) {
                                receiver.send(-1, null);
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MediaBrowserService.KEY_MEDIA_ITEM, item);
                            receiver.send(0, bundle);
                        }
                    };
            MediaBrowserService.this.mCurrentConnectionOnHandler = connection;
            MediaBrowserService.this.onLoadItem(itemId, result);
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (!result.isDone()) {
                throw new IllegalStateException(
                        "onLoadItem must call detach() or sendResult() before returning for id="
                                + itemId);
            }
        }

        public boolean isValidPackage(String providedPackage, int uid) {
            if (providedPackage == null) {
                return false;
            }
            PackageManager pm = MediaBrowserService.this.getPackageManager();
            for (String packageForUid : pm.getPackagesForUid(uid)) {
                if (packageForUid.equals(providedPackage)) {
                    return true;
                }
            }
            return false;
        }
    }
}
