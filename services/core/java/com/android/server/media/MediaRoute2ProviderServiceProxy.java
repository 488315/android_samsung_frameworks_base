package com.android.server.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.IMediaRoute2ProviderService;
import android.media.IMediaRoute2ProviderServiceCallback;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.media.flags.Flags;
import com.android.server.media.MediaRoute2Provider;
import com.android.server.media.MediaRoute2ProviderServiceProxy;
import com.android.server.media.MediaRouter2ServiceImpl;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaRoute2ProviderServiceProxy extends MediaRoute2Provider {
    public static final boolean DEBUG = Log.isLoggable("MR2ProviderSvcProxy", 3);
    public Connection mActiveConnection;
    public boolean mBound;
    public boolean mConnectionReady;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsManagerScanning;
    public final boolean mIsSelfScanOnlyProvider;
    public RouteDiscoveryPreference mLastDiscoveryPreference;
    public boolean mLastDiscoveryPreferenceIncludesThisPackage;
    public final List mReleasingSessions;
    public final LongSparseArray mRequestIdToSessionCreationRequest;
    public boolean mRunning;
    public final ServiceConnectionImpl mServiceConnection;
    public final Map mSessionOriginalIdToTransferRequest;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Connection implements IBinder.DeathRecipient {
        public final ServiceCallbackStub mCallbackStub = new ServiceCallbackStub(this);
        public final IMediaRoute2ProviderService mService;

        public Connection(IMediaRoute2ProviderService iMediaRoute2ProviderService) {
            this.mService = iMediaRoute2ProviderService;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1(0, this));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceCallbackStub extends IMediaRoute2ProviderServiceCallback.Stub {
        public final WeakReference mConnectionRef;

        public ServiceCallbackStub(Connection connection) {
            this.mConnectionRef = new WeakReference(connection);
        }

        public final void notifyProviderUpdated(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            Objects.requireNonNull(mediaRoute2ProviderInfo, "providerInfo must not be null");
            for (MediaRoute2Info mediaRoute2Info : mediaRoute2ProviderInfo.getRoutes()) {
                if (mediaRoute2Info.isSystemRoute()) {
                    throw new SecurityException("Only the system is allowed to publish system routes. Disallowed route: " + mediaRoute2Info);
                }
                if (mediaRoute2Info.getSuitabilityStatus() == 2) {
                    throw new SecurityException("Only the system is allowed to set not suitable for transfer status. Disallowed route: " + mediaRoute2Info);
                }
                if (mediaRoute2Info.isSystemRouteType()) {
                    throw new SecurityException("Only the system is allowed to publish routes with system route types. Disallowed route: " + mediaRoute2Info);
                }
            }
            Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(0, connection, mediaRoute2ProviderInfo));
            }
        }

        public final void notifyRequestFailed(final long j, final int i) {
            final Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaRoute2ProviderServiceProxy.Connection connection2 = MediaRoute2ProviderServiceProxy.Connection.this;
                        long j2 = j;
                        int i2 = i;
                        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy = MediaRoute2ProviderServiceProxy.this;
                        mediaRoute2ProviderServiceProxy.getClass();
                        if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                            synchronized (mediaRoute2ProviderServiceProxy.mLock) {
                                mediaRoute2ProviderServiceProxy.mRequestIdToSessionCreationRequest.remove(j2);
                            }
                        }
                        if (mediaRoute2ProviderServiceProxy.mActiveConnection != connection2) {
                            return;
                        }
                        if (j2 == 0) {
                            Slog.w("MR2ProviderSvcProxy", "onRequestFailed: Ignoring requestId REQUEST_ID_NONE");
                        } else {
                            ((MediaRouter2ServiceImpl.UserHandler) mediaRoute2ProviderServiceProxy.mCallback).onRequestFailed(mediaRoute2ProviderServiceProxy, j2, i2);
                        }
                    }
                });
            }
        }

        public final void notifySessionCreated(final long j, final RoutingSessionInfo routingSessionInfo) {
            final Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaRoute2ProviderServiceProxy.Connection connection2 = MediaRoute2ProviderServiceProxy.Connection.this;
                        long j2 = j;
                        RoutingSessionInfo routingSessionInfo2 = routingSessionInfo;
                        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy = MediaRoute2ProviderServiceProxy.this;
                        if (mediaRoute2ProviderServiceProxy.mActiveConnection != connection2) {
                            return;
                        }
                        if (routingSessionInfo2 == null) {
                            Slog.w("MR2ProviderSvcProxy", "onSessionCreated: Ignoring null session sent from " + mediaRoute2ProviderServiceProxy.mComponentName);
                            return;
                        }
                        RoutingSessionInfo assignProviderIdForSession = mediaRoute2ProviderServiceProxy.assignProviderIdForSession(routingSessionInfo2);
                        final String id = assignProviderIdForSession.getId();
                        synchronized (mediaRoute2ProviderServiceProxy.mLock) {
                            try {
                                if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                                    assignProviderIdForSession = mediaRoute2ProviderServiceProxy.createSessionWithPopulatedTransferInitiationDataLocked(j2, null, assignProviderIdForSession);
                                }
                                final int i = 0;
                                if (!mediaRoute2ProviderServiceProxy.mSessionInfos.stream().anyMatch(new Predicate() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i2 = i;
                                        String str = id;
                                        RoutingSessionInfo routingSessionInfo3 = (RoutingSessionInfo) obj;
                                        switch (i2) {
                                        }
                                        return TextUtils.equals(routingSessionInfo3.getId(), str);
                                    }
                                })) {
                                    final int i2 = 1;
                                    if (!mediaRoute2ProviderServiceProxy.mReleasingSessions.stream().anyMatch(new Predicate() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            int i22 = i2;
                                            String str = id;
                                            RoutingSessionInfo routingSessionInfo3 = (RoutingSessionInfo) obj;
                                            switch (i22) {
                                            }
                                            return TextUtils.equals(routingSessionInfo3.getId(), str);
                                        }
                                    })) {
                                        ((ArrayList) mediaRoute2ProviderServiceProxy.mSessionInfos).add(assignProviderIdForSession);
                                        ((MediaRouter2ServiceImpl.UserHandler) mediaRoute2ProviderServiceProxy.mCallback).onSessionCreated(mediaRoute2ProviderServiceProxy, j2, assignProviderIdForSession);
                                        return;
                                    }
                                }
                                Slog.w("MR2ProviderSvcProxy", "onSessionCreated: Duplicate session already exists. Ignoring.");
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            }
        }

        public final void notifySessionReleased(RoutingSessionInfo routingSessionInfo) {
            Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(connection, routingSessionInfo));
            }
        }

        public final void notifySessionsUpdated(List list) {
            Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(2, connection, list));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceConnectionImpl implements ServiceConnection {
        public ServiceConnectionImpl() {
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            if (Flags.enableMr2ServiceNonMainBgThread()) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(4, this, componentName));
            } else {
                MediaRoute2ProviderServiceProxy.m649$$Nest$monBindingDiedInternal(MediaRoute2ProviderServiceProxy.this, componentName);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Flags.enableMr2ServiceNonMainBgThread()) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(3, this, iBinder));
            } else {
                MediaRoute2ProviderServiceProxy.m650$$Nest$monServiceConnectedInternal(MediaRoute2ProviderServiceProxy.this, iBinder);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (Flags.enableMr2ServiceNonMainBgThread()) {
                MediaRoute2ProviderServiceProxy.this.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1(2, this));
            } else {
                MediaRoute2ProviderServiceProxy.m651$$Nest$monServiceDisconnectedInternal(MediaRoute2ProviderServiceProxy.this);
            }
        }
    }

    /* renamed from: -$$Nest$monBindingDiedInternal, reason: not valid java name */
    public static void m649$$Nest$monBindingDiedInternal(MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy, ComponentName componentName) {
        mediaRoute2ProviderServiceProxy.unbind();
        if (Flags.enablePreventionOfKeepAliveRouteProviders()) {
            Slog.w("MR2ProviderSvcProxy", TextUtils.formatSimple("Route provider service (%s) binding died, but we did not rebind.", new Object[]{componentName.toString()}));
        } else if (mediaRoute2ProviderServiceProxy.shouldBind()) {
            Slog.w("MR2ProviderSvcProxy", TextUtils.formatSimple("Rebound to provider service (%s) after binding died.", new Object[]{componentName.toString()}));
            mediaRoute2ProviderServiceProxy.bind();
        }
    }

    /* renamed from: -$$Nest$monServiceConnectedInternal, reason: not valid java name */
    public static void m650$$Nest$monServiceConnectedInternal(MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy, IBinder iBinder) {
        boolean z = DEBUG;
        if (z) {
            mediaRoute2ProviderServiceProxy.getClass();
            Slog.d("MR2ProviderSvcProxy", mediaRoute2ProviderServiceProxy + ": Connected");
        }
        if (mediaRoute2ProviderServiceProxy.mBound) {
            mediaRoute2ProviderServiceProxy.disconnect();
            IMediaRoute2ProviderService asInterface = IMediaRoute2ProviderService.Stub.asInterface(iBinder);
            if (asInterface == null) {
                Slog.e("MR2ProviderSvcProxy", mediaRoute2ProviderServiceProxy + ": Service returned invalid binder");
                return;
            }
            Connection connection = mediaRoute2ProviderServiceProxy.new Connection(asInterface);
            try {
                asInterface.asBinder().linkToDeath(connection, 0);
                asInterface.setCallback(connection.mCallbackStub);
                mediaRoute2ProviderServiceProxy.mHandler.post(new MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1(1, connection));
                mediaRoute2ProviderServiceProxy.mActiveConnection = connection;
            } catch (RemoteException unused) {
                connection.binderDied();
                if (z) {
                    Slog.d("MR2ProviderSvcProxy", mediaRoute2ProviderServiceProxy + ": Registration failed");
                }
            }
        }
    }

    /* renamed from: -$$Nest$monServiceDisconnectedInternal, reason: not valid java name */
    public static void m651$$Nest$monServiceDisconnectedInternal(MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy) {
        if (DEBUG) {
            mediaRoute2ProviderServiceProxy.getClass();
            Slog.d("MR2ProviderSvcProxy", mediaRoute2ProviderServiceProxy + ": Service disconnected");
        }
        mediaRoute2ProviderServiceProxy.disconnect();
    }

    public MediaRoute2ProviderServiceProxy(Context context, Looper looper, ComponentName componentName, boolean z, int i) {
        super(componentName);
        this.mServiceConnection = new ServiceConnectionImpl();
        this.mLastDiscoveryPreference = null;
        this.mLastDiscoveryPreferenceIncludesThisPackage = false;
        this.mReleasingSessions = new ArrayList();
        Objects.requireNonNull(context, "Context must not be null.");
        this.mContext = context;
        this.mRequestIdToSessionCreationRequest = new LongSparseArray();
        this.mSessionOriginalIdToTransferRequest = new HashMap();
        this.mIsSelfScanOnlyProvider = z;
        this.mUserId = i;
        this.mHandler = new Handler(looper);
    }

    public final RoutingSessionInfo assignProviderIdForSession(RoutingSessionInfo routingSessionInfo) {
        return new RoutingSessionInfo.Builder(routingSessionInfo).setOwnerPackageName(this.mComponentName.getPackageName()).setProviderId(this.mUniqueId).build();
    }

    public final void bind() {
        if (this.mBound) {
            return;
        }
        boolean z = DEBUG;
        if (z) {
            Slog.d("MR2ProviderSvcProxy", this + ": Binding");
        }
        Intent intent = new Intent("android.media.MediaRoute2ProviderService");
        intent.setComponent(this.mComponentName);
        try {
            boolean bindServiceAsUser = this.mContext.bindServiceAsUser(intent, this.mServiceConnection, 67108865, new UserHandle(this.mUserId));
            this.mBound = bindServiceAsUser;
            if (bindServiceAsUser || !z) {
                return;
            }
            Slog.d("MR2ProviderSvcProxy", this + ": Bind failed");
        } catch (SecurityException e) {
            if (z) {
                Slog.d("MR2ProviderSvcProxy", this + ": Bind failed", e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0062, code lost:
    
        if (r2.anyMatch(new com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda1(r3)) != false) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.media.RoutingSessionInfo createSessionWithPopulatedTransferInitiationDataLocked(long r6, android.media.RoutingSessionInfo r8, android.media.RoutingSessionInfo r9) {
        /*
            r5 = this;
            if (r8 == 0) goto L11
            java.util.Map r6 = r5.mSessionOriginalIdToTransferRequest
            java.lang.String r7 = r9.getOriginalId()
            java.util.HashMap r6 = (java.util.HashMap) r6
            java.lang.Object r6 = r6.get(r7)
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest r6 = (com.android.server.media.MediaRoute2Provider.SessionCreationOrTransferRequest) r6
            goto L19
        L11:
            android.util.LongSparseArray r0 = r5.mRequestIdToSessionCreationRequest
            java.lang.Object r6 = r0.get(r6)
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest r6 = (com.android.server.media.MediaRoute2Provider.SessionCreationOrTransferRequest) r6
        L19:
            r7 = 1
            r0 = 0
            if (r6 == 0) goto L40
            java.util.List r1 = r9.getSelectedRoutes()
            java.util.stream.Stream r1 = r1.stream()
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda0 r2 = new com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda0
            r2.<init>()
            java.util.stream.Stream r1 = r1.map(r2)
            java.lang.String r2 = r6.mTargetOriginalRouteId
            java.util.Objects.requireNonNull(r2)
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda1 r3 = new com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda1
            r3.<init>(r2)
            boolean r1 = r1.anyMatch(r3)
            if (r1 == 0) goto L40
            r1 = r7
            goto L41
        L40:
            r1 = r0
        L41:
            if (r6 == 0) goto L65
            java.util.List r2 = r9.getTransferableRoutes()
            java.util.stream.Stream r2 = r2.stream()
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda0 r3 = new com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda0
            r3.<init>()
            java.util.stream.Stream r2 = r2.map(r3)
            java.lang.String r3 = r6.mTargetOriginalRouteId
            java.util.Objects.requireNonNull(r3)
            com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda1 r4 = new com.android.server.media.MediaRoute2Provider$SessionCreationOrTransferRequest$$ExternalSyntheticLambda1
            r4.<init>(r3)
            boolean r2 = r2.anyMatch(r4)
            if (r2 == 0) goto L65
            goto L66
        L65:
            r7 = r0
        L66:
            if (r1 == 0) goto L6f
            int r0 = r6.mTransferReason
            android.os.UserHandle r2 = r6.mTransferInitiatorUserHandle
            java.lang.String r3 = r6.mTransferInitiatorPackageName
            goto L88
        L6f:
            if (r8 == 0) goto L7e
            int r0 = r8.getTransferReason()
            android.os.UserHandle r2 = r8.getTransferInitiatorUserHandle()
            java.lang.String r3 = r8.getTransferInitiatorPackageName()
            goto L88
        L7e:
            int r2 = r5.mUserId
            android.os.UserHandle r2 = android.os.UserHandle.of(r2)
            java.lang.String r3 = r9.getClientPackageName()
        L88:
            if (r1 != 0) goto L8c
            if (r7 != 0) goto La3
        L8c:
            if (r8 == 0) goto L9a
            java.util.Map r5 = r5.mSessionOriginalIdToTransferRequest
            java.lang.String r6 = r9.getId()
            java.util.HashMap r5 = (java.util.HashMap) r5
            r5.remove(r6)
            goto La3
        L9a:
            if (r6 == 0) goto La3
            android.util.LongSparseArray r5 = r5.mRequestIdToSessionCreationRequest
            long r6 = r6.mRequestId
            r5.remove(r6)
        La3:
            android.media.RoutingSessionInfo$Builder r5 = new android.media.RoutingSessionInfo$Builder
            r5.<init>(r9)
            android.media.RoutingSessionInfo$Builder r5 = r5.setTransferInitiator(r2, r3)
            android.media.RoutingSessionInfo$Builder r5 = r5.setTransferReason(r0)
            android.media.RoutingSessionInfo r5 = r5.build()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.MediaRoute2ProviderServiceProxy.createSessionWithPopulatedTransferInitiationDataLocked(long, android.media.RoutingSessionInfo, android.media.RoutingSessionInfo):android.media.RoutingSessionInfo");
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void deselectRoute(String str, long j, String str2) {
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.deselectRoute(j, str, str2);
            } catch (RemoteException e) {
                Slog.e("MR2ProviderSvcProxy", "deselectRoute: Failed to deliver request.", e);
            }
        }
    }

    public final void disconnect() {
        Connection connection = this.mActiveConnection;
        if (connection != null) {
            this.mConnectionReady = false;
            connection.mService.asBinder().unlinkToDeath(connection, 0);
            connection.mCallbackStub.mConnectionRef.clear();
            this.mActiveConnection = null;
            setProviderState(null);
            notifyProviderState();
            synchronized (this.mLock) {
                try {
                    Iterator it = ((ArrayList) this.mSessionInfos).iterator();
                    while (it.hasNext()) {
                        RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) it.next();
                        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) this.mCallback;
                        userHandler.getClass();
                        userHandler.sendMessage(PooledLambda.obtainMessage(new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(4), userHandler, this, routingSessionInfo));
                    }
                    ((ArrayList) this.mSessionInfos).clear();
                    ((ArrayList) this.mReleasingSessions).clear();
                    this.mRequestIdToSessionCreationRequest.clear();
                    ((HashMap) this.mSessionOriginalIdToTransferRequest).clear();
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final String getDebugString() {
        int size;
        int size2;
        synchronized (this.mLock) {
            size = this.mRequestIdToSessionCreationRequest.size();
            size2 = ((HashMap) this.mSessionOriginalIdToTransferRequest).size();
        }
        return TextUtils.formatSimple("ProviderServiceProxy - package: %s, bound: %b, connection (active:%b, ready:%b), pending (session creations: %d, transfers: %d)", new Object[]{this.mComponentName.getPackageName(), Boolean.valueOf(this.mBound), Boolean.valueOf(this.mActiveConnection != null), Boolean.valueOf(this.mConnectionReady), Integer.valueOf(size), Integer.valueOf(size2)});
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void prepareReleaseSession(String str) {
        synchronized (this.mLock) {
            try {
                Iterator it = ((ArrayList) this.mSessionInfos).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) it.next();
                    if (TextUtils.equals(routingSessionInfo.getId(), str)) {
                        ((ArrayList) this.mSessionInfos).remove(routingSessionInfo);
                        ((ArrayList) this.mReleasingSessions).add(routingSessionInfo);
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void releaseSession(long j, String str) {
        if (this.mConnectionReady) {
            if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                synchronized (this.mLock) {
                    ((HashMap) this.mSessionOriginalIdToTransferRequest).remove(str);
                }
            }
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.releaseSession(j, str);
            } catch (RemoteException unused) {
                Slog.e("MR2ProviderSvcProxy", "releaseSession: Failed to deliver request.");
            }
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void requestCreateSession(long j, String str, String str2, Bundle bundle, int i, UserHandle userHandle, String str3) {
        if (this.mConnectionReady) {
            if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                synchronized (this.mLock) {
                    this.mRequestIdToSessionCreationRequest.put(j, new MediaRoute2Provider.SessionCreationOrTransferRequest(j, str2, i, userHandle, str3));
                }
            }
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.requestCreateSession(j, str, str2, bundle);
            } catch (RemoteException unused) {
                Slog.e("MR2ProviderSvcProxy", "requestCreateSession: Failed to deliver request.");
            }
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void selectRoute(String str, long j, String str2) {
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.selectRoute(j, str, str2);
            } catch (RemoteException e) {
                Slog.e("MR2ProviderSvcProxy", "selectRoute: Failed to deliver request.", e);
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void setRouteVolume(int i, String str, long j) {
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.setRouteVolume(j, str, i);
            } catch (RemoteException e) {
                Slog.e("MR2ProviderSvcProxy", "setRouteVolume: Failed to deliver request.", e);
            }
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void setSessionVolume(int i, String str, long j) {
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.setSessionVolume(j, str, i);
            } catch (RemoteException e) {
                Slog.e("MR2ProviderSvcProxy", "setSessionVolume: Failed to deliver request.", e);
            }
            updateBinding();
        }
    }

    public final boolean shouldBind() {
        if (!this.mRunning) {
            return false;
        }
        boolean z = this.mIsManagerScanning && !Flags.enablePreventionOfManagerScansWhenNoAppsScan();
        if (!((ArrayList) getSessionInfos()).isEmpty() || z) {
            return true;
        }
        RouteDiscoveryPreference routeDiscoveryPreference = this.mLastDiscoveryPreference;
        if (routeDiscoveryPreference == null || routeDiscoveryPreference.getPreferredFeatures().isEmpty()) {
            return false;
        }
        return this.mLastDiscoveryPreferenceIncludesThisPackage || !this.mIsSelfScanOnlyProvider;
    }

    public final void start(boolean z) {
        if (!this.mRunning) {
            if (DEBUG) {
                Slog.d("MR2ProviderSvcProxy", this + ": Starting");
            }
            this.mRunning = true;
            if (!Flags.enablePreventionOfKeepAliveRouteProviders()) {
                updateBinding();
            }
        }
        if (z && this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    public final void stop() {
        if (this.mRunning) {
            if (DEBUG) {
                Slog.d("MR2ProviderSvcProxy", this + ": Stopping");
            }
            this.mRunning = false;
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void transferToRoute(long j, UserHandle userHandle, String str, String str2, String str3, int i) {
        if (this.mConnectionReady) {
            if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                synchronized (this.mLock) {
                    ((HashMap) this.mSessionOriginalIdToTransferRequest).put(str2, new MediaRoute2Provider.SessionCreationOrTransferRequest(j, str3, i, userHandle, str));
                }
            }
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.transferToRoute(j, str2, str3);
            } catch (RemoteException e) {
                Slog.e("MR2ProviderSvcProxy", "transferToRoute: Failed to deliver request.", e);
            }
        }
    }

    public final void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                Slog.d("MR2ProviderSvcProxy", this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            this.mContext.unbindService(this.mServiceConnection);
        }
    }

    public final void updateBinding() {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void updateDiscoveryPreference(Set set, RouteDiscoveryPreference routeDiscoveryPreference) {
        this.mLastDiscoveryPreference = routeDiscoveryPreference;
        this.mLastDiscoveryPreferenceIncludesThisPackage = set.contains(this.mComponentName.getPackageName());
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            connection.getClass();
            try {
                connection.mService.updateDiscoveryPreference(routeDiscoveryPreference);
            } catch (RemoteException unused) {
                Slog.e("MR2ProviderSvcProxy", "updateDiscoveryPreference: Failed to deliver request.");
            }
        }
        updateBinding();
    }
}
