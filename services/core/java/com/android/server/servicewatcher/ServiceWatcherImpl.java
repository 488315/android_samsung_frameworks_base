package com.android.server.servicewatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationConstants;
import android.location.flags.Flags;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.clipboard.ClipboardService;
import com.android.server.location.LocationManagerService;
import com.android.server.location.injector.Injector;
import com.android.server.location.nsflp.NSConnectionRecord;

import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ServiceWatcherImpl implements ServiceWatcher$ServiceChangedListener {
    public static final boolean D = Log.isLoggable("ServiceWatcher", 3);
    public final NSConnectionRecord mConnectionRecord;
    public final Context mContext;
    public final Handler mHandler;
    public final Injector mInjector;
    public final ServiceWatcher$ServiceListener mServiceListener;
    public final ServiceWatcher$ServiceSupplier mServiceSupplier;
    public final String mTag;
    public final AnonymousClass1 mPackageMonitor =
            new PackageMonitor() { // from class:
                                   // com.android.server.servicewatcher.ServiceWatcherImpl.1
                public final boolean onPackageChanged(String str, int i, String[] strArr) {
                    return true;
                }

                public final void onSomePackagesChanged() {
                    ServiceWatcherImpl.this.onServiceChanged(false);
                }
            };
    public boolean mRegistered = false;
    public MyServiceConnection mServiceConnection = new MyServiceConnection(null);
    public final AnonymousClass2 mNsHandler =
            new Handler() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    LocationConstants.STATE_TYPE state_type =
                            LocationConstants.STATE_TYPE.PROXY_SERVICE_RECONNECTION_TIMEOUT;
                    if (i == state_type.ordinal()) {
                        boolean z = ServiceWatcherImpl.D;
                        ServiceWatcherImpl serviceWatcherImpl = ServiceWatcherImpl.this;
                        if (z) {
                            Log.d(
                                    serviceWatcherImpl.mTag,
                                    "location proxy service reconnection timeout");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("record", serviceWatcherImpl.mConnectionRecord);
                        Injector injector = serviceWatcherImpl.mInjector;
                        if (injector != null) {
                            ((LocationManagerService.SystemInjector) injector)
                                    .mNSConnectionHelper.onStateUpdated(state_type, bundle);
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyServiceConnection implements ServiceConnection {
        public volatile IBinder mBinder;
        public final CurrentUserServiceSupplier.BoundServiceInfo mBoundServiceInfo;
        public ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0 mRebinder;

        public MyServiceConnection(CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
            this.mBoundServiceInfo = boundServiceInfo;
        }

        public final void bind() {
            Preconditions.checkState(
                    Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBoundServiceInfo == null) {
                return;
            }
            if (ServiceWatcherImpl.D) {
                Log.d(
                        "ServiceWatcher",
                        "["
                                + ServiceWatcherImpl.this.mTag
                                + "] binding to "
                                + this.mBoundServiceInfo);
            }
            this.mRebinder = null;
            Intent component =
                    new Intent(this.mBoundServiceInfo.mAction)
                            .setComponent(this.mBoundServiceInfo.mComponentName);
            try {
                ServiceWatcherImpl serviceWatcherImpl = ServiceWatcherImpl.this;
                if (serviceWatcherImpl.mContext.bindServiceAsUser(
                        component,
                        this,
                        1073741829,
                        serviceWatcherImpl.mHandler,
                        UserHandle.of(UserHandle.getUserId(this.mBoundServiceInfo.mUid)))) {
                    return;
                }
                Log.e(
                        "ServiceWatcher",
                        "["
                                + ServiceWatcherImpl.this.mTag
                                + "] unexpected bind failure - retrying later");
                ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0
                        serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0 =
                                new ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0(
                                        this, 1);
                this.mRebinder = serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0;
                ServiceWatcherImpl.this.mHandler.postDelayed(
                        serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0, 15000L);
            } catch (SecurityException e) {
                Log.e(
                        "ServiceWatcher",
                        "["
                                + ServiceWatcherImpl.this.mTag
                                + "] "
                                + this.mBoundServiceInfo
                                + " bind failed",
                        e);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Preconditions.checkState(
                    Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            Log.w(
                    "ServiceWatcher",
                    "[" + ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " died");
            ServiceWatcherImpl.this.mConnectionRecord.serviceBindingDiedTime =
                    SystemClock.elapsedRealtime();
            ServiceWatcherImpl.this.mHandler.postDelayed(
                    new ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0(this, 0),
                    500L);
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Log.e(
                    "ServiceWatcher",
                    "["
                            + ServiceWatcherImpl.this.mTag
                            + "] "
                            + this.mBoundServiceInfo
                            + " has null binding");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Preconditions.checkState(
                    Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            Preconditions.checkState(this.mBinder == null);
            Log.i(
                    "ServiceWatcher",
                    "["
                            + ServiceWatcherImpl.this.mTag
                            + "] connected to "
                            + componentName.toShortString());
            this.mBinder = iBinder;
            ServiceWatcher$ServiceListener serviceWatcher$ServiceListener =
                    ServiceWatcherImpl.this.mServiceListener;
            if (serviceWatcher$ServiceListener != null) {
                try {
                    serviceWatcher$ServiceListener.onBind(iBinder, this.mBoundServiceInfo);
                } catch (RemoteException | RuntimeException e) {
                    Log.e(
                            "ServiceWatcher",
                            "["
                                    + ServiceWatcherImpl.this.mTag
                                    + "] error running operation on "
                                    + this.mBoundServiceInfo,
                            e);
                }
            }
            removeMessages(
                    LocationConstants.STATE_TYPE.PROXY_SERVICE_RECONNECTION_TIMEOUT.ordinal());
            NSConnectionRecord nSConnectionRecord = ServiceWatcherImpl.this.mConnectionRecord;
            String componentName2 = componentName.toString();
            String str = nSConnectionRecord.componentName;
            if (str != null) {
                nSConnectionRecord.prevComponentName = str;
            }
            nSConnectionRecord.componentName = componentName2;
            NSConnectionRecord nSConnectionRecord2 = ServiceWatcherImpl.this.mConnectionRecord;
            String packageName = componentName.getPackageName();
            String str2 = nSConnectionRecord2.packageName;
            if (str2 != null) {
                nSConnectionRecord2.prevPackageName = str2;
            }
            nSConnectionRecord2.packageName = packageName;
            ServiceWatcherImpl.this.mConnectionRecord.connectedTime = SystemClock.elapsedRealtime();
            ServiceWatcherImpl serviceWatcherImpl = ServiceWatcherImpl.this;
            serviceWatcherImpl.mConnectionRecord.connectionCount++;
            if (serviceWatcherImpl.mInjector != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("record", ServiceWatcherImpl.this.mConnectionRecord);
                ((LocationManagerService.SystemInjector) ServiceWatcherImpl.this.mInjector)
                        .mNSConnectionHelper.onStateUpdated(
                                LocationConstants.STATE_TYPE.PROXY_SERVICE_CONNECTED, bundle);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Preconditions.checkState(
                    Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBinder == null) {
                return;
            }
            Log.i(
                    "ServiceWatcher",
                    "["
                            + ServiceWatcherImpl.this.mTag
                            + "] disconnected from "
                            + this.mBoundServiceInfo);
            this.mBinder = null;
            ServiceWatcher$ServiceListener serviceWatcher$ServiceListener =
                    ServiceWatcherImpl.this.mServiceListener;
            if (serviceWatcher$ServiceListener != null) {
                serviceWatcher$ServiceListener.onUnbind();
            }
            ServiceWatcherImpl.this.mConnectionRecord.disconnectedTime =
                    SystemClock.elapsedRealtime();
            ServiceWatcherImpl serviceWatcherImpl = ServiceWatcherImpl.this;
            serviceWatcherImpl.mConnectionRecord.disconnectionCount++;
            if (serviceWatcherImpl.mInjector != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("record", ServiceWatcherImpl.this.mConnectionRecord);
                ((LocationManagerService.SystemInjector) ServiceWatcherImpl.this.mInjector)
                        .mNSConnectionHelper.onStateUpdated(
                                LocationConstants.STATE_TYPE.PROXY_SERVICE_DISCONNECTED, bundle);
            }
            sendEmptyMessageDelayed(
                    LocationConstants.STATE_TYPE.PROXY_SERVICE_RECONNECTION_TIMEOUT.ordinal(),
                    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.servicewatcher.ServiceWatcherImpl$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.servicewatcher.ServiceWatcherImpl$2] */
    public ServiceWatcherImpl(
            Context context,
            Handler handler,
            String str,
            CurrentUserServiceSupplier currentUserServiceSupplier,
            ServiceWatcher$ServiceListener serviceWatcher$ServiceListener,
            Injector injector) {
        this.mContext = context;
        this.mHandler = handler;
        this.mTag = str;
        this.mServiceSupplier = currentUserServiceSupplier;
        this.mServiceListener = serviceWatcher$ServiceListener;
        this.mInjector = injector;
        this.mConnectionRecord = new NSConnectionRecord(str);
    }

    public final boolean checkServiceResolves() {
        CurrentUserServiceSupplier currentUserServiceSupplier =
                (CurrentUserServiceSupplier) this.mServiceSupplier;
        currentUserServiceSupplier.getClass();
        if (Flags.fixServiceWatcher()
                && "".equals(currentUserServiceSupplier.mIntent.getPackage())) {
            return false;
        }
        return !currentUserServiceSupplier
                .mContext
                .getPackageManager()
                .queryIntentServicesAsUser(
                        currentUserServiceSupplier.mIntent,
                        currentUserServiceSupplier.mMatchSystemAppsOnly ? 1835008 : 786432,
                        0)
                .isEmpty();
    }

    public final void dump(PrintWriter printWriter) {
        MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        printWriter.println("target service=" + myServiceConnection.mBoundServiceInfo);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("connected="), myServiceConnection.mBinder != null, printWriter);
    }

    public final synchronized void onServiceChanged(boolean z) {
        try {
            CurrentUserServiceSupplier.BoundServiceInfo serviceInfo =
                    this.mRegistered
                            ? ((CurrentUserServiceSupplier) this.mServiceSupplier).getServiceInfo()
                            : null;
            if ((z | (!(this.mServiceConnection.mBinder != null)))
                    || !Objects.equals(this.mServiceConnection.mBoundServiceInfo, serviceInfo)) {
                Log.i(
                        "ServiceWatcher",
                        "[" + this.mTag + "] chose new implementation " + serviceInfo);
                MyServiceConnection myServiceConnection = this.mServiceConnection;
                MyServiceConnection myServiceConnection2 = new MyServiceConnection(serviceInfo);
                this.mServiceConnection = myServiceConnection2;
                this.mHandler.post(
                        new ServiceWatcherImpl$$ExternalSyntheticLambda0(
                                myServiceConnection, myServiceConnection2, 0));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void register() {
        boolean z = true;
        Preconditions.checkState(!this.mRegistered);
        this.mRegistered = true;
        AnonymousClass1 anonymousClass1 = this.mPackageMonitor;
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        anonymousClass1.register(context, userHandle, this.mHandler);
        CurrentUserServiceSupplier currentUserServiceSupplier =
                (CurrentUserServiceSupplier) this.mServiceSupplier;
        if (currentUserServiceSupplier.mListener != null) {
            z = false;
        }
        Preconditions.checkState(z);
        currentUserServiceSupplier.mListener = this;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.setPriority(1000);
        currentUserServiceSupplier.mContext.registerReceiverAsUser(
                currentUserServiceSupplier, userHandle, intentFilter, null, FgThread.getHandler());
        onServiceChanged(false);
    }

    public final synchronized void runOnBinder(
            ServiceWatcher$BinderOperation serviceWatcher$BinderOperation) {
        this.mHandler.post(
                new ServiceWatcherImpl$$ExternalSyntheticLambda0(
                        this.mServiceConnection, serviceWatcher$BinderOperation, 1));
    }

    public final String toString() {
        MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        return myServiceConnection.mBoundServiceInfo.toString();
    }

    public final synchronized void unregister() {
        Preconditions.checkState(this.mRegistered);
        CurrentUserServiceSupplier currentUserServiceSupplier =
                (CurrentUserServiceSupplier) this.mServiceSupplier;
        Preconditions.checkArgument(currentUserServiceSupplier.mListener != null);
        currentUserServiceSupplier.mListener = null;
        currentUserServiceSupplier.mContext.unregisterReceiver(currentUserServiceSupplier);
        unregister();
        this.mRegistered = false;
        onServiceChanged(false);
    }
}
