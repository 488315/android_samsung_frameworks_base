package com.android.server.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.IRemoteDisplayCallback;
import android.media.IRemoteDisplayProvider;
import android.media.RemoteDisplayState;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.media.MediaRouterService;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteDisplayProviderProxy implements ServiceConnection {
    public Connection mActiveConnection;
    public boolean mBound;
    public final ComponentName mComponentName;
    public boolean mConnectionReady;
    public final Context mContext;
    public int mDiscoveryMode;
    public RemoteDisplayState mDisplayState;
    public Callback mDisplayStateCallback;
    public final AnonymousClass1 mDisplayStateChanged = new AnonymousClass1(0, this);
    public final Handler mHandler = new Handler();
    public boolean mRunning;
    public boolean mScheduledDisplayStateChangedCallback;
    public String mSelectedDisplayId;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.media.RemoteDisplayProviderProxy$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy = (RemoteDisplayProviderProxy) this.this$0;
                    int i = 0;
                    remoteDisplayProviderProxy.mScheduledDisplayStateChangedCallback = false;
                    Callback callback = remoteDisplayProviderProxy.mDisplayStateCallback;
                    if (callback != null) {
                        RemoteDisplayState remoteDisplayState = remoteDisplayProviderProxy.mDisplayState;
                        MediaRouterService.UserHandler userHandler = (MediaRouterService.UserHandler) callback;
                        int size = userHandler.mProviderRecords.size();
                        while (true) {
                            if (i >= size) {
                                i = -1;
                            } else if (((MediaRouterService.UserHandler.ProviderRecord) userHandler.mProviderRecords.get(i)).mProvider != remoteDisplayProviderProxy) {
                                i++;
                            }
                        }
                        if (i >= 0 && ((MediaRouterService.UserHandler.ProviderRecord) userHandler.mProviderRecords.get(i)).updateDescriptor(remoteDisplayState)) {
                            userHandler.checkSelectedRouteState();
                            userHandler.scheduleUpdateClientState();
                            break;
                        }
                    }
                    break;
                case 1:
                    Connection connection = (Connection) this.this$0;
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy2 = RemoteDisplayProviderProxy.this;
                    Connection connection2 = remoteDisplayProviderProxy2.mActiveConnection;
                    if (connection2 == connection) {
                        remoteDisplayProviderProxy2.mConnectionReady = true;
                        int i2 = remoteDisplayProviderProxy2.mDiscoveryMode;
                        if (i2 != 0) {
                            connection2.getClass();
                            try {
                                connection2.mProvider.setDiscoveryMode(i2);
                            } catch (RemoteException e) {
                                Slog.e("RemoteDisplayProvider", "Failed to deliver request to set discovery mode.", e);
                            }
                        }
                        String str = remoteDisplayProviderProxy2.mSelectedDisplayId;
                        if (str != null) {
                            Connection connection3 = remoteDisplayProviderProxy2.mActiveConnection;
                            connection3.getClass();
                            try {
                                connection3.mProvider.connect(str);
                                break;
                            } catch (RemoteException e2) {
                                Slog.e("RemoteDisplayProvider", "Failed to deliver request to connect to display.", e2);
                                return;
                            }
                        }
                    }
                    break;
                default:
                    Connection connection4 = (Connection) this.this$0;
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy3 = RemoteDisplayProviderProxy.this;
                    if (remoteDisplayProviderProxy3.mActiveConnection == connection4) {
                        Slog.d("RemoteDisplayProvider", remoteDisplayProviderProxy3 + ": Service connection died");
                        remoteDisplayProviderProxy3.disconnect();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Connection implements IBinder.DeathRecipient {
        public final ProviderCallback mCallback = new ProviderCallback(this);
        public final IRemoteDisplayProvider mProvider;

        public Connection(IRemoteDisplayProvider iRemoteDisplayProvider) {
            this.mProvider = iRemoteDisplayProvider;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            RemoteDisplayProviderProxy.this.mHandler.post(new AnonymousClass1(2, this));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderCallback extends IRemoteDisplayCallback.Stub {
        public final WeakReference mConnectionRef;

        public ProviderCallback(Connection connection) {
            this.mConnectionRef = new WeakReference(connection);
        }

        public final void onStateChanged(final RemoteDisplayState remoteDisplayState) {
            final Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                RemoteDisplayProviderProxy.this.mHandler.post(new Runnable() { // from class: com.android.server.media.RemoteDisplayProviderProxy.Connection.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Connection connection2 = Connection.this;
                        RemoteDisplayProviderProxy remoteDisplayProviderProxy = RemoteDisplayProviderProxy.this;
                        RemoteDisplayState remoteDisplayState2 = remoteDisplayState;
                        if (remoteDisplayProviderProxy.mActiveConnection == connection2) {
                            Slog.d("RemoteDisplayProvider", remoteDisplayProviderProxy + ": State changed, state=" + remoteDisplayState2);
                            if (Objects.equals(remoteDisplayProviderProxy.mDisplayState, remoteDisplayState2)) {
                                return;
                            }
                            remoteDisplayProviderProxy.mDisplayState = remoteDisplayState2;
                            if (remoteDisplayProviderProxy.mScheduledDisplayStateChangedCallback) {
                                return;
                            }
                            remoteDisplayProviderProxy.mScheduledDisplayStateChangedCallback = true;
                            remoteDisplayProviderProxy.mHandler.post(remoteDisplayProviderProxy.mDisplayStateChanged);
                        }
                    }
                });
            }
        }
    }

    public RemoteDisplayProviderProxy(Context context, ComponentName componentName, int i) {
        this.mContext = context;
        this.mComponentName = componentName;
        this.mUserId = i;
    }

    public final void bind() {
        if (this.mBound) {
            return;
        }
        Slog.d("RemoteDisplayProvider", this + ": Binding");
        Intent intent = new Intent("com.android.media.remotedisplay.RemoteDisplayProvider");
        intent.setComponent(this.mComponentName);
        try {
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(intent, this, 67108865, new UserHandle(this.mUserId));
            this.mBound = bindServiceAsUser;
            if (bindServiceAsUser) {
                return;
            }
            Slog.d("RemoteDisplayProvider", this + ": Bind failed");
        } catch (SecurityException e) {
            Slog.d("RemoteDisplayProvider", this + ": Bind failed", e);
        }
    }

    public final void disconnect() {
        Connection connection = this.mActiveConnection;
        if (connection != null) {
            String str = this.mSelectedDisplayId;
            if (str != null) {
                try {
                    connection.mProvider.disconnect(str);
                } catch (RemoteException e) {
                    Slog.e("RemoteDisplayProvider", "Failed to deliver request to disconnect from display.", e);
                }
            }
            this.mConnectionReady = false;
            Connection connection2 = this.mActiveConnection;
            connection2.mProvider.asBinder().unlinkToDeath(connection2, 0);
            connection2.mCallback.mConnectionRef.clear();
            this.mActiveConnection = null;
            if (Objects.equals(this.mDisplayState, null)) {
                return;
            }
            this.mDisplayState = null;
            if (this.mScheduledDisplayStateChangedCallback) {
                return;
            }
            this.mScheduledDisplayStateChangedCallback = true;
            this.mHandler.post(this.mDisplayStateChanged);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Slog.d("RemoteDisplayProvider", this + ": Connected");
        if (this.mBound) {
            disconnect();
            IRemoteDisplayProvider asInterface = IRemoteDisplayProvider.Stub.asInterface(iBinder);
            if (asInterface == null) {
                Slog.e("RemoteDisplayProvider", this + ": Service returned invalid remote display provider binder");
                return;
            }
            Connection connection = new Connection(asInterface);
            try {
                asInterface.asBinder().linkToDeath(connection, 0);
                asInterface.setCallback(connection.mCallback);
                this.mHandler.post(new AnonymousClass1(1, connection));
                this.mActiveConnection = connection;
            } catch (RemoteException unused) {
                connection.binderDied();
                Slog.d("RemoteDisplayProvider", this + ": Registration failed");
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Slog.d("RemoteDisplayProvider", this + ": Service disconnected");
        disconnect();
    }

    public final void setDiscoveryMode(int i) {
        if (this.mDiscoveryMode != i) {
            this.mDiscoveryMode = i;
            if (this.mConnectionReady) {
                Connection connection = this.mActiveConnection;
                connection.getClass();
                try {
                    connection.mProvider.setDiscoveryMode(i);
                } catch (RemoteException e) {
                    Slog.e("RemoteDisplayProvider", "Failed to deliver request to set discovery mode.", e);
                }
            }
            updateBinding();
        }
    }

    public final void setSelectedDisplay(String str) {
        String str2;
        if (Objects.equals(this.mSelectedDisplayId, str)) {
            return;
        }
        if (this.mConnectionReady && (str2 = this.mSelectedDisplayId) != null) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mProvider.disconnect(str2);
            } catch (RemoteException e) {
                Slog.e("RemoteDisplayProvider", "Failed to deliver request to disconnect from display.", e);
            }
        }
        this.mSelectedDisplayId = str;
        if (this.mConnectionReady && str != null) {
            Connection connection2 = this.mActiveConnection;
            connection2.getClass();
            try {
                connection2.mProvider.connect(str);
            } catch (RemoteException e2) {
                Slog.e("RemoteDisplayProvider", "Failed to deliver request to connect to display.", e2);
            }
        }
        updateBinding();
    }

    public final void start() {
        if (this.mRunning) {
            return;
        }
        Slog.d("RemoteDisplayProvider", this + ": Starting");
        this.mRunning = true;
        updateBinding();
    }

    public final void stop() {
        if (this.mRunning) {
            Slog.d("RemoteDisplayProvider", this + ": Stopping");
            this.mRunning = false;
            updateBinding();
        }
    }

    public final String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    public final void unbind() {
        if (this.mBound) {
            Slog.d("RemoteDisplayProvider", this + ": Unbinding");
            this.mBound = false;
            disconnect();
            this.mContext.unbindService(this);
        }
    }

    public final void updateBinding() {
        if (!this.mRunning || (this.mDiscoveryMode == 0 && this.mSelectedDisplayId == null)) {
            unbind();
        } else {
            bind();
        }
    }
}
