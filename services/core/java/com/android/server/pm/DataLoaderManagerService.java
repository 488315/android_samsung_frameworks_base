package com.android.server.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.DataLoaderParamsParcel;
import android.content.pm.IDataLoader;
import android.content.pm.IDataLoaderManager;
import android.content.pm.IDataLoaderStatusListener;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.SystemService;

import java.util.List;

public final class DataLoaderManagerService extends SystemService {
    public final DataLoaderManagerBinderService mBinderService;
    public final Context mContext;
    public final Handler mHandler;
    public final SparseArray mServiceConnections;

    public final class DataLoaderManagerBinderService extends IDataLoaderManager.Stub {
        public DataLoaderManagerBinderService() {}

        public final boolean bindToDataLoader(
                final int i,
                DataLoaderParamsParcel dataLoaderParamsParcel,
                long j,
                IDataLoaderStatusListener iDataLoaderStatusListener) {
            synchronized (DataLoaderManagerService.this.mServiceConnections) {
                try {
                    if (DataLoaderManagerService.this.mServiceConnections.get(i) != null) {
                        return true;
                    }
                    ComponentName componentName =
                            new ComponentName(
                                    dataLoaderParamsParcel.packageName,
                                    dataLoaderParamsParcel.className);
                    PackageManager packageManager =
                            DataLoaderManagerService.this.mContext.getPackageManager();
                    ComponentName componentName2 = null;
                    if (packageManager == null) {
                        Slog.e("DataLoaderManager", "PackageManager is not available.");
                    } else {
                        Intent intent = new Intent("android.intent.action.LOAD_DATA");
                        intent.setComponent(componentName);
                        List queryIntentServicesAsUser =
                                packageManager.queryIntentServicesAsUser(
                                        intent, 0, UserHandle.getCallingUserId());
                        if (queryIntentServicesAsUser == null
                                || queryIntentServicesAsUser.isEmpty()) {
                            Slog.e(
                                    "DataLoaderManager",
                                    "Failed to find data loader service provider in "
                                            + componentName);
                        } else if (queryIntentServicesAsUser.size() > 0) {
                            ServiceInfo serviceInfo =
                                    ((ResolveInfo) queryIntentServicesAsUser.get(0)).serviceInfo;
                            componentName2 =
                                    new ComponentName(serviceInfo.packageName, serviceInfo.name);
                        } else {
                            Slog.e(
                                    "DataLoaderManager",
                                    "Didn't find any matching data loader service provider.");
                        }
                    }
                    final ComponentName componentName3 = componentName2;
                    if (componentName3 != null) {
                        final DataLoaderServiceConnection dataLoaderServiceConnection =
                                DataLoaderManagerService.this
                                .new DataLoaderServiceConnection(i, iDataLoaderStatusListener);
                        final Intent intent2 = new Intent();
                        intent2.setComponent(componentName3);
                        return DataLoaderManagerService.this.mHandler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.pm.DataLoaderManagerService$DataLoaderManagerBinderService$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DataLoaderManagerService.DataLoaderManagerBinderService
                                                dataLoaderManagerBinderService =
                                                        DataLoaderManagerService
                                                                .DataLoaderManagerBinderService
                                                                .this;
                                        Intent intent3 = intent2;
                                        DataLoaderManagerService.DataLoaderServiceConnection
                                                dataLoaderServiceConnection2 =
                                                        dataLoaderServiceConnection;
                                        ComponentName componentName4 = componentName3;
                                        int i2 = i;
                                        DataLoaderManagerService dataLoaderManagerService =
                                                DataLoaderManagerService.this;
                                        if (dataLoaderManagerService.mContext.bindServiceAsUser(
                                                intent3,
                                                dataLoaderServiceConnection2,
                                                1,
                                                dataLoaderManagerService.mHandler,
                                                UserHandle.of(UserHandle.getCallingUserId()))) {
                                            return;
                                        }
                                        Slog.e(
                                                "DataLoaderManager",
                                                "Failed to bind to: "
                                                        + componentName4
                                                        + " for ID="
                                                        + i2);
                                        DataLoaderManagerService.this.mContext.unbindService(
                                                dataLoaderServiceConnection2);
                                    }
                                },
                                j);
                    }
                    Slog.e(
                            "DataLoaderManager",
                            "Invalid component: " + componentName + " for ID=" + i);
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final IDataLoader getDataLoader(int i) {
            synchronized (DataLoaderManagerService.this.mServiceConnections) {
                try {
                    DataLoaderServiceConnection dataLoaderServiceConnection =
                            (DataLoaderServiceConnection)
                                    DataLoaderManagerService.this.mServiceConnections.get(i, null);
                    if (dataLoaderServiceConnection == null) {
                        return null;
                    }
                    return dataLoaderServiceConnection.mDataLoader;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unbindFromDataLoader(int i) {
            synchronized (DataLoaderManagerService.this.mServiceConnections) {
                try {
                    DataLoaderServiceConnection dataLoaderServiceConnection =
                            (DataLoaderServiceConnection)
                                    DataLoaderManagerService.this.mServiceConnections.get(i, null);
                    if (dataLoaderServiceConnection == null) {
                        return;
                    }
                    IDataLoader iDataLoader = dataLoaderServiceConnection.mDataLoader;
                    if (iDataLoader != null) {
                        try {
                            iDataLoader.destroy(dataLoaderServiceConnection.mId);
                        } catch (RemoteException unused) {
                        }
                        dataLoaderServiceConnection.mDataLoader = null;
                    }
                    dataLoaderServiceConnection.unbind();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class DataLoaderServiceConnection
            implements ServiceConnection, IBinder.DeathRecipient {
        public IDataLoader mDataLoader = null;
        public final int mId;
        public final IDataLoaderStatusListener mListener;

        public DataLoaderServiceConnection(
                int i, IDataLoaderStatusListener iDataLoaderStatusListener) {
            this.mId = i;
            this.mListener = iDataLoaderStatusListener;
            if (iDataLoaderStatusListener != null) {
                try {
                    iDataLoaderStatusListener.onStatusChanged(i, 1);
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            IDataLoaderStatusListener iDataLoaderStatusListener;
            Slog.i("DataLoaderManager", "DataLoader " + this.mId + " died");
            if (!unbind() || (iDataLoaderStatusListener = this.mListener) == null) {
                return;
            }
            try {
                iDataLoaderStatusListener.onStatusChanged(this.mId, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            IDataLoaderStatusListener iDataLoaderStatusListener;
            Slog.i("DataLoaderManager", "DataLoader " + this.mId + " died");
            if (!unbind() || (iDataLoaderStatusListener = this.mListener) == null) {
                return;
            }
            try {
                iDataLoaderStatusListener.onStatusChanged(this.mId, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            IDataLoaderStatusListener iDataLoaderStatusListener;
            Slog.i("DataLoaderManager", "DataLoader " + this.mId + " failed to start");
            if (!unbind() || (iDataLoaderStatusListener = this.mListener) == null) {
                return;
            }
            try {
                iDataLoaderStatusListener.onStatusChanged(this.mId, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IDataLoader asInterface = IDataLoader.Stub.asInterface(iBinder);
            this.mDataLoader = asInterface;
            if (asInterface == null) {
                onNullBinding(componentName);
                return;
            }
            synchronized (DataLoaderManagerService.this.mServiceConnections) {
                try {
                    DataLoaderServiceConnection dataLoaderServiceConnection =
                            (DataLoaderServiceConnection)
                                    DataLoaderManagerService.this.mServiceConnections.get(this.mId);
                    if (dataLoaderServiceConnection != this) {
                        if (dataLoaderServiceConnection != null) {
                            DataLoaderManagerService.this.mContext.unbindService(this);
                            return;
                        }
                        DataLoaderManagerService.this.mServiceConnections.append(this.mId, this);
                    }
                    try {
                        iBinder.linkToDeath(this, 0);
                        IDataLoaderStatusListener iDataLoaderStatusListener = this.mListener;
                        if (iDataLoaderStatusListener != null) {
                            try {
                                iDataLoaderStatusListener.onStatusChanged(this.mId, 2);
                            } catch (RemoteException unused) {
                            }
                        }
                    } catch (RemoteException e) {
                        Slog.e(
                                "DataLoaderManager",
                                "Failed to link to DataLoader's death: " + this.mId,
                                e);
                        onBindingDied(componentName);
                    }
                } finally {
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            IDataLoaderStatusListener iDataLoaderStatusListener;
            Slog.i(
                    "DataLoaderManager",
                    "DataLoader " + this.mId + " disconnected, but will try to recover");
            if (!unbind() || (iDataLoaderStatusListener = this.mListener) == null) {
                return;
            }
            try {
                iDataLoaderStatusListener.onStatusChanged(this.mId, 0);
            } catch (RemoteException unused) {
            }
        }

        public final boolean unbind() {
            try {
                DataLoaderManagerService.this.mContext.unbindService(this);
            } catch (Exception unused) {
            }
            synchronized (DataLoaderManagerService.this.mServiceConnections) {
                try {
                    if (DataLoaderManagerService.this.mServiceConnections.get(this.mId) != this) {
                        return false;
                    }
                    DataLoaderManagerService.this.mServiceConnections.remove(this.mId);
                    return true;
                } finally {
                }
            }
        }
    }

    public DataLoaderManagerService(Context context) {
        super(context);
        this.mServiceConnections = new SparseArray();
        this.mContext = context;
        this.mHandler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("DataLoaderManager")
                                .getLooper());
        this.mBinderService = new DataLoaderManagerBinderService();
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("dataloader_manager", this.mBinderService);
    }
}
