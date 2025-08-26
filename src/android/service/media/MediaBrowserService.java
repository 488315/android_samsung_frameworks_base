package android.service.media;

import android.app.Service;
import android.content.Intent;
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
import android.service.media.IMediaBrowserService;
import android.service.media.MediaBrowserService;
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

/* loaded from: classes3.dex */
public abstract class MediaBrowserService extends Service {
    private static final boolean DBG = false;
    public static final String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_ERROR = -1;
    private static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    private static final int RESULT_OK = 0;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final String TAG = "MediaBrowserService";
    private final ServiceBinder mBinder;
    private ConnectionRecord mCurrentConnectionOnHandler;
    private final Handler mHandler = new Handler();
    private final AtomicReference<ServiceState> mServiceState;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ResultFlags {
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public abstract BrowserRoot onGetRoot(String str, int i, Bundle bundle);

    public abstract void onLoadChildren(String str, Result<List<MediaBrowser.MediaItem>> result);

    /* JADX INFO: Access modifiers changed from: private */
    static class ConnectionRecord implements IBinder.DeathRecipient {
        public final IMediaBrowserServiceCallbacks callbacks;
        public final int pid;
        public final String pkg;
        public final BrowserRoot root;
        public final Bundle rootHints;
        public final ServiceState serviceState;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        public final int uid;

        ConnectionRecord(ServiceState serviceState, String str, int i, int i2, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, BrowserRoot browserRoot) {
            this.serviceState = serviceState;
            this.pkg = str;
            this.pid = i;
            this.uid = i2;
            this.rootHints = bundle;
            this.callbacks = iMediaBrowserServiceCallbacks;
            this.root = browserRoot;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ConnectionRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$binderDied$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            this.serviceState.mConnections.remove(this.callbacks.asBinder());
        }
    }

    public class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        void onResultSent(T t, int i) {
        }

        Result(MediaBrowserService mediaBrowserService, Object obj) {
            this.mDebug = obj;
        }

        public void sendResult(T t) {
            if (this.mSendResultCalled) {
                throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(t, this.mFlags);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            }
            if (this.mSendResultCalled) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            }
            this.mDetachCalled = true;
        }

        boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled;
        }

        void setFlags(int i) {
            this.mFlags = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ServiceBinder extends IMediaBrowserService.Stub {
        private final AtomicReference<WeakReference<ServiceState>> mServiceState;

        @Override // android.service.media.IMediaBrowserService
        public void addSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

        private ServiceBinder(ServiceState serviceState) {
            this.mServiceState = new AtomicReference<>();
            setServiceState(serviceState);
        }

        public void setServiceState(ServiceState serviceState) {
            this.mServiceState.set(new WeakReference<>(serviceState));
        }

        @Override // android.service.media.IMediaBrowserService
        public void connect(final String str, final Bundle bundle, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            final int callingPid = Binder.getCallingPid();
            final int callingUid = Binder.getCallingUid();
            if (!serviceState.isValidPackage(str, callingUid)) {
                throw new IllegalArgumentException("Package/uid mismatch: uid=" + callingUid + " package=" + str);
            }
            serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    serviceState.connectOnHandler(str, callingPid, callingUid, bundle, iMediaBrowserServiceCallbacks);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void disconnect(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    serviceState.removeConnectionRecordOnHandler(iMediaBrowserServiceCallbacks);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscription(final String str, final IBinder iBinder, final Bundle bundle, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    serviceState.addSubscriptionOnHandler(str, iMediaBrowserServiceCallbacks, iBinder, bundle);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscription(final String str, final IBinder iBinder, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaBrowserService.ServiceBinder.lambda$removeSubscription$3(serviceState, str, iMediaBrowserServiceCallbacks, iBinder);
                }
            });
        }

        static /* synthetic */ void lambda$removeSubscription$3(ServiceState serviceState, String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, IBinder iBinder) {
            if (serviceState.removeSubscriptionOnHandler(str, iMediaBrowserServiceCallbacks, iBinder)) {
                return;
            }
            Log.w(MediaBrowserService.TAG, "removeSubscription for id with no subscription: " + str);
        }

        @Override // android.service.media.IMediaBrowserService
        public void getMediaItem(final String str, final ResultReceiver resultReceiver, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    serviceState.performLoadItemOnHandler(str, iMediaBrowserServiceCallbacks, resultReceiver);
                }
            });
        }
    }

    public MediaBrowserService() {
        AtomicReference<ServiceState> atomicReference = new AtomicReference<>(new ServiceState());
        this.mServiceState = atomicReference;
        this.mBinder = new ServiceBinder(atomicReference.get());
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

    public void onLoadChildren(String str, Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
        result.setFlags(1);
        onLoadChildren(str, result);
    }

    public void onLoadItem(String str, Result<MediaBrowser.MediaItem> result) {
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
                ServiceState serviceState2 = new ServiceState();
                this.mBinder.setServiceState(serviceState2);
                this.mServiceState.set(serviceState2);
                serviceState.release();
                return;
            }
            return;
        }
        if (serviceState.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        }
        serviceState.mSession = token;
        this.mHandler.post(new Runnable() { // from class: android.service.media.MediaBrowserService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                serviceState.notifySessionTokenInitializedOnHandler(token);
            }
        });
    }

    public MediaSession.Token getSessionToken() {
        return this.mServiceState.get().mSession;
    }

    public final Bundle getBrowserRootHints() {
        ConnectionRecord connectionRecord = this.mCurrentConnectionOnHandler;
        if (connectionRecord == null) {
            throw new IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
        }
        if (connectionRecord.rootHints == null) {
            return null;
        }
        return new Bundle(connectionRecord.rootHints);
    }

    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        ConnectionRecord connectionRecord = this.mCurrentConnectionOnHandler;
        if (connectionRecord == null) {
            throw new IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
        }
        return new MediaSessionManager.RemoteUserInfo(connectionRecord.pkg, connectionRecord.pid, connectionRecord.uid);
    }

    public void notifyChildrenChanged(String str) {
        notifyChildrenChanged(str, Bundle.EMPTY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyChildrenChanged$1(String str, Bundle bundle) {
        this.mServiceState.get().notifyChildrenChangeOnHandler(str, bundle);
    }

    public void notifyChildrenChanged(final String str, final Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(new Runnable() { // from class: android.service.media.MediaBrowserService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyChildrenChanged$1(str, bundle);
            }
        });
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(String str, Bundle bundle) {
            if (str == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = str;
            this.mExtras = bundle;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
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
            MediaBrowserService.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.service.media.MediaBrowserService$ServiceState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.clearConnectionsOnHandler();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearConnectionsOnHandler() {
            Iterator<ConnectionRecord> it = this.mConnections.values().iterator();
            while (it.hasNext()) {
                ConnectionRecord next = it.next();
                it.remove();
                try {
                    next.callbacks.onDisconnect();
                } catch (RemoteException e) {
                    Log.w(MediaBrowserService.TAG, TextUtils.formatSimple("onDisconnectRequest for %s failed", next.pkg), e);
                }
            }
        }

        public void removeConnectionRecordOnHandler(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            ConnectionRecord connectionRecordRemove = this.mConnections.remove(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecordRemove != null) {
                connectionRecordRemove.callbacks.asBinder().unlinkToDeath(connectionRecordRemove, 0);
            }
        }

        public void notifySessionTokenInitializedOnHandler(MediaSession.Token token) {
            Iterator<ConnectionRecord> it = this.mConnections.values().iterator();
            while (it.hasNext()) {
                ConnectionRecord next = it.next();
                try {
                    next.callbacks.onConnect(next.root.getRootId(), token, next.root.getExtras());
                } catch (RemoteException unused) {
                    Log.w(MediaBrowserService.TAG, "Connection for " + next.pkg + " is no longer valid.");
                    it.remove();
                }
            }
        }

        public void notifyChildrenChangeOnHandler(String str, Bundle bundle) {
            Iterator<IBinder> it = this.mConnections.keySet().iterator();
            while (it.hasNext()) {
                ConnectionRecord connectionRecord = this.mConnections.get(it.next());
                List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str);
                if (list != null) {
                    for (Pair<IBinder, Bundle> pair : list) {
                        if (MediaBrowserUtils.hasDuplicatedItems(bundle, pair.second)) {
                            performLoadChildrenOnHandler(str, connectionRecord, pair.second);
                        }
                    }
                }
            }
        }

        public void addSubscriptionOnHandler(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, IBinder iBinder, Bundle bundle) {
            ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                Log.w(MediaBrowserService.TAG, "addSubscription for callback that isn't registered id=" + str);
                return;
            }
            List<Pair<IBinder, Bundle>> arrayList = connectionRecord.subscriptions.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            for (Pair<IBinder, Bundle> pair : arrayList) {
                if (iBinder == pair.first && MediaBrowserUtils.areSameOptions(bundle, pair.second)) {
                    return;
                }
            }
            arrayList.add(new Pair<>(iBinder, bundle));
            connectionRecord.subscriptions.put(str, arrayList);
            performLoadChildrenOnHandler(str, connectionRecord, bundle);
        }

        public void connectOnHandler(String str, int i, int i2, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            IBinder iBinderAsBinder = iMediaBrowserServiceCallbacks.asBinder();
            this.mConnections.remove(iBinderAsBinder);
            MediaBrowserService.this.mCurrentConnectionOnHandler = new ConnectionRecord(this, str, i, i2, bundle, iMediaBrowserServiceCallbacks, null);
            BrowserRoot browserRootOnGetRoot = MediaBrowserService.this.onGetRoot(str, i2, bundle);
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (browserRootOnGetRoot == null) {
                Log.i(MediaBrowserService.TAG, "No root for client " + str + " from service " + getClass().getName());
                try {
                    iMediaBrowserServiceCallbacks.onConnectFailed();
                    return;
                } catch (RemoteException unused) {
                    Log.w(MediaBrowserService.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str);
                    return;
                }
            }
            try {
                ConnectionRecord connectionRecord = new ConnectionRecord(this, str, i, i2, bundle, iMediaBrowserServiceCallbacks, browserRootOnGetRoot);
                this.mConnections.put(iBinderAsBinder, connectionRecord);
                iBinderAsBinder.linkToDeath(connectionRecord, 0);
                if (this.mSession != null) {
                    iMediaBrowserServiceCallbacks.onConnect(connectionRecord.root.getRootId(), this.mSession, connectionRecord.root.getExtras());
                }
            } catch (RemoteException unused2) {
                Log.w(MediaBrowserService.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str);
                this.mConnections.remove(iBinderAsBinder);
            }
        }

        public boolean removeSubscriptionOnHandler(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, IBinder iBinder) {
            ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                Log.w(MediaBrowserService.TAG, "removeSubscription for callback that isn't registered id=" + str);
                return true;
            }
            boolean z = false;
            if (iBinder == null) {
                return connectionRecord.subscriptions.remove(str) != null;
            }
            List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str);
            if (list != null) {
                Iterator<Pair<IBinder, Bundle>> it = list.iterator();
                while (it.hasNext()) {
                    if (iBinder == it.next().first) {
                        it.remove();
                        z = true;
                    }
                }
                if (list.isEmpty()) {
                    connectionRecord.subscriptions.remove(str);
                }
            }
            return z;
        }

        public void performLoadChildrenOnHandler(final String str, final ConnectionRecord connectionRecord, final Bundle bundle) {
            Result<List<MediaBrowser.MediaItem>> result = new Result<List<MediaBrowser.MediaItem>>(str) { // from class: android.service.media.MediaBrowserService.ServiceState.1
                {
                    MediaBrowserService mediaBrowserService = MediaBrowserService.this;
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.service.media.MediaBrowserService.Result
                public void onResultSent(List<MediaBrowser.MediaItem> list, int i) {
                    ParceledListSlice parceledListSlice;
                    if (ServiceState.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                        return;
                    }
                    if ((i & 1) != 0) {
                        list = MediaBrowserUtils.applyPagingOptions(list, bundle);
                    }
                    if (list != null) {
                        parceledListSlice = new ParceledListSlice(list);
                        parceledListSlice.setInlineCountLimit(1);
                    } else {
                        parceledListSlice = null;
                    }
                    try {
                        connectionRecord.callbacks.onLoadChildren(str, parceledListSlice, bundle);
                    } catch (RemoteException unused) {
                        Log.w(MediaBrowserService.TAG, "Calling onLoadChildren() failed for id=" + str + " package=" + connectionRecord.pkg);
                    }
                }
            };
            MediaBrowserService.this.mCurrentConnectionOnHandler = connectionRecord;
            if (bundle == null) {
                MediaBrowserService.this.onLoadChildren(str, result);
            } else {
                MediaBrowserService.this.onLoadChildren(str, result, bundle);
            }
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (result.isDone()) {
                return;
            }
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connectionRecord.pkg + " id=" + str);
        }

        public void performLoadItemOnHandler(final String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final ResultReceiver resultReceiver) {
            final ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                Log.w(MediaBrowserService.TAG, "getMediaItem for callback that isn't registered id=" + str);
                return;
            }
            Result<MediaBrowser.MediaItem> result = new Result<MediaBrowser.MediaItem>(str) { // from class: android.service.media.MediaBrowserService.ServiceState.2
                {
                    MediaBrowserService mediaBrowserService = MediaBrowserService.this;
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.service.media.MediaBrowserService.Result
                public void onResultSent(MediaBrowser.MediaItem mediaItem, int i) {
                    if (ServiceState.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                        return;
                    }
                    if ((i & 2) != 0) {
                        resultReceiver.send(-1, null);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(MediaBrowserService.KEY_MEDIA_ITEM, mediaItem);
                    resultReceiver.send(0, bundle);
                }
            };
            MediaBrowserService.this.mCurrentConnectionOnHandler = connectionRecord;
            MediaBrowserService.this.onLoadItem(str, result);
            MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (result.isDone()) {
                return;
            }
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str);
        }

        public boolean isValidPackage(String str, int i) {
            if (str == null) {
                return false;
            }
            for (String str2 : MediaBrowserService.this.getPackageManager().getPackagesForUid(i)) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }
}
