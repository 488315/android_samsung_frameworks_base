package com.android.server.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.printservice.recommendation.IRecommendationService;
import android.printservice.recommendation.IRecommendationServiceCallbacks;
import android.util.Log;

import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;

import java.util.List;

public final class RemotePrintServiceRecommendationService {
    public final Connection mConnection;
    public final Context mContext;
    public boolean mIsBound;
    public final Object mLock;
    public IRecommendationService mService;

    public final class Connection implements ServiceConnection {
        public final UserState mCallbacks;

        public Connection(UserState userState) {
            this.mCallbacks = userState;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (RemotePrintServiceRecommendationService.this.mLock) {
                RemotePrintServiceRecommendationService.this.mService =
                        IRecommendationService.Stub.asInterface(iBinder);
                try {
                    RemotePrintServiceRecommendationService.this.mService.registerCallbacks(
                            new IRecommendationServiceCallbacks
                                    .Stub() { // from class:
                                              // com.android.server.print.RemotePrintServiceRecommendationService.Connection.1
                                public final void onRecommendationsUpdated(List list) {
                                    synchronized (
                                            RemotePrintServiceRecommendationService.this.mLock) {
                                        try {
                                            RemotePrintServiceRecommendationService
                                                    remotePrintServiceRecommendationService =
                                                            RemotePrintServiceRecommendationService
                                                                    .this;
                                            if (remotePrintServiceRecommendationService.mIsBound
                                                    && remotePrintServiceRecommendationService
                                                                    .mService
                                                            != null) {
                                                if (list != null) {
                                                    Preconditions.checkCollectionElementsNotNull(
                                                            list, "recommendation");
                                                }
                                                UserState userState = Connection.this.mCallbacks;
                                                userState.getClass();
                                                Handler.getMain()
                                                        .sendMessage(
                                                                PooledLambda.obtainMessage(
                                                                        new UserState$$ExternalSyntheticLambda1(
                                                                                1),
                                                                        userState,
                                                                        list));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
                } catch (RemoteException e) {
                    Log.e("RemotePrintServiceRecS", "Could not register callbacks", e);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.w("RemotePrintServiceRecS", "Unexpected termination of connection");
            synchronized (RemotePrintServiceRecommendationService.this.mLock) {
                RemotePrintServiceRecommendationService.this.mService = null;
            }
        }
    }

    public RemotePrintServiceRecommendationService(
            Context context, UserHandle userHandle, UserState userState) {
        Object obj = new Object();
        this.mLock = obj;
        this.mContext = context;
        Connection connection = new Connection(userState);
        this.mConnection = connection;
        try {
            Intent serviceIntent = getServiceIntent(userHandle);
            synchronized (obj) {
                try {
                    boolean bindServiceAsUser =
                            context.bindServiceAsUser(
                                    serviceIntent, connection, 67108865, userHandle);
                    this.mIsBound = bindServiceAsUser;
                    if (!bindServiceAsUser) {
                        throw new Exception("Failed to bind to service " + serviceIntent);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            Log.e(
                    "RemotePrintServiceRecS",
                    "Could not connect to print service recommendation service",
                    e);
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            IRecommendationService iRecommendationService = this.mService;
            if (iRecommendationService != null) {
                try {
                    iRecommendationService.registerCallbacks(
                            (IRecommendationServiceCallbacks) null);
                } catch (RemoteException e) {
                    Log.e("RemotePrintServiceRecS", "Could not unregister callbacks", e);
                }
                this.mService = null;
            }
            if (this.mIsBound) {
                this.mContext.unbindService(this.mConnection);
                this.mIsBound = false;
            }
        }
    }

    public final void finalize() {
        if (this.mIsBound || this.mService != null) {
            Log.w("RemotePrintServiceRecS", "Service still connected on finalize()");
            close();
        }
        super.finalize();
    }

    public final Intent getServiceIntent(UserHandle userHandle) {
        List queryIntentServicesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentServicesAsUser(
                                new Intent(
                                        "android.printservice.recommendation.RecommendationService"),
                                268435588,
                                userHandle.getIdentifier());
        if (queryIntentServicesAsUser.size() != 1) {
            throw new Exception(
                    queryIntentServicesAsUser.size() + " instead of exactly one service found");
        }
        ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesAsUser.get(0);
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        ApplicationInfo applicationInfo =
                this.mContext
                        .getPackageManager()
                        .getApplicationInfo(resolveInfo.serviceInfo.packageName, 0);
        if (applicationInfo == null) {
            throw new Exception("Cannot read appInfo for service");
        }
        if ((applicationInfo.flags & 1) == 0) {
            throw new Exception("Service is not part of the system");
        }
        if ("android.permission.BIND_PRINT_RECOMMENDATION_SERVICE"
                .equals(resolveInfo.serviceInfo.permission)) {
            Intent intent = new Intent();
            intent.setComponent(componentName);
            return intent;
        }
        throw new Exception(
                "Service "
                        + componentName.flattenToShortString()
                        + " does not require permission"
                        + " android.permission.BIND_PRINT_RECOMMENDATION_SERVICE");
    }
}
