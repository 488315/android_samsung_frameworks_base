package com.android.server.servicewatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.location.flags.Flags;
import com.android.internal.util.Preconditions;
import com.android.server.servicewatcher.ServiceWatcher;
import com.android.server.servicewatcher.ServiceWatcher.BoundServiceInfo;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes6.dex */
class ServiceWatcherImpl<TBoundServiceInfo extends ServiceWatcher.BoundServiceInfo> implements ServiceWatcher, ServiceWatcher.ServiceChangedListener {
    private static final int DISCONNECTED_COUNT_BEFORE_MARKED_AS_UNSTABLE = 10;
    private static final long RECONNECTION_TIMEOUT_THRESHOLD = 3600000;
    static final long RETRY_DELAY_MS = 15000;
    private static final long UNSTABLE_TIME_PERIOD_MS = 60000;
    private long mBindingDiedTime;
    private String mComponentName;
    private long mConnectedTime;
    private int mConnectionCount;
    final Context mContext;
    private int mDisconnectedCount;
    private String mDisconnectedService;
    private long mDisconnectedStartTime;
    private long mDisconnectedTime;
    private int mDisconnectionCount;
    final Handler mHandler;
    private boolean mIsLocationService;
    private String mPackageName;
    private String mPrevComponentName;
    private String mPrevPackageName;
    final ServiceWatcher.ServiceListener<? super TBoundServiceInfo> mServiceListener;
    final ServiceWatcher.ServiceSupplier<TBoundServiceInfo> mServiceSupplier;
    final String mTag;
    private boolean mUnstableFallbackEnabled;
    static final String TAG = "ServiceWatcher";
    static final boolean D = Log.isLoggable(TAG, 3);
    private final PackageMonitor mPackageMonitor = new PackageMonitor() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl.1
        @Override // com.android.internal.content.PackageMonitor
        public boolean onPackageChanged(String str, int i, String[] strArr) {
            return true;
        }

        @Override // com.android.internal.content.PackageMonitor
        public void onSomePackagesChanged() {
            ServiceWatcherImpl.this.onServiceChanged(false);
        }
    };
    private boolean mRegistered = false;
    private ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection mServiceConnection = new MyServiceConnection(null);
    private final Handler mNsHandler = new Handler() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 205) {
                if (ServiceWatcherImpl.D) {
                    Log.d(ServiceWatcherImpl.this.mTag, "location proxy service reconnection timeout");
                }
                ServiceWatcherImpl.this.sendPorxyConnectionInfo(205);
            }
        }
    };

    private boolean isLocationService(String str) {
        return "fused".equals(str) || "network".equals(str) || "GeocoderProxy".equals(str);
    }

    ServiceWatcherImpl(Context context, Handler handler, String str, ServiceWatcher.ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceWatcher.ServiceListener<? super TBoundServiceInfo> serviceListener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mTag = str;
        this.mServiceSupplier = serviceSupplier;
        this.mServiceListener = serviceListener;
        this.mIsLocationService = isLocationService(str);
    }

    ServiceWatcherImpl(Context context, Handler handler, String str, boolean z, ServiceWatcher.ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceWatcher.ServiceListener<? super TBoundServiceInfo> serviceListener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mTag = str;
        if (Flags.serviceWatcherUnstableFallback()) {
            this.mUnstableFallbackEnabled = z;
        }
        this.mServiceSupplier = serviceSupplier;
        this.mServiceListener = serviceListener;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public boolean checkServiceResolves() {
        return this.mServiceSupplier.hasMatchingService();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void register() {
        Preconditions.checkState(!this.mRegistered);
        this.mRegistered = true;
        this.mPackageMonitor.register(this.mContext, UserHandle.ALL, this.mHandler);
        this.mServiceSupplier.register(this);
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void unregister() {
        Preconditions.checkState(this.mRegistered);
        this.mServiceSupplier.unregister();
        this.mPackageMonitor.unregister();
        this.mRegistered = false;
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener
    public synchronized void onServiceChanged() {
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void runOnBinder(final ServiceWatcher.BinderOperation binderOperation) {
        final ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection = this.mServiceConnection;
        this.mHandler.post(new Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                myServiceConnection.runOnBinder(binderOperation);
            }
        });
    }

    synchronized void onServiceChanged(boolean z) {
        ServiceWatcher.BoundServiceInfo serviceInfo = this.mRegistered ? this.mServiceSupplier.getServiceInfo() : null;
        if ((z | (!this.mServiceConnection.isConnected())) || !Objects.equals(this.mServiceConnection.getBoundServiceInfo(), serviceInfo)) {
            Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + this.mTag + "] chose new implementation " + serviceInfo);
            final ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection = this.mServiceConnection;
            final ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection2 = new MyServiceConnection(serviceInfo);
            this.mServiceConnection = myServiceConnection2;
            this.mHandler.post(new Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceWatcherImpl.lambda$onServiceChanged$1(myServiceConnection, myServiceConnection2);
                }
            });
        }
    }

    static /* synthetic */ void lambda$onServiceChanged$1(MyServiceConnection myServiceConnection, MyServiceConnection myServiceConnection2) {
        myServiceConnection.unbind();
        myServiceConnection2.bind();
    }

    public String toString() {
        ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        return myServiceConnection.getBoundServiceInfo().toString();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public void dump(PrintWriter printWriter) {
        ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        printWriter.println("target service=" + myServiceConnection.getBoundServiceInfo());
        printWriter.println("connected=" + myServiceConnection.isConnected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MyServiceConnection implements ServiceConnection {
        private volatile IBinder mBinder;
        private final TBoundServiceInfo mBoundServiceInfo;
        private boolean mForcingRebind;
        private Runnable mRebinder;

        MyServiceConnection(TBoundServiceInfo tboundserviceinfo) {
            this.mBoundServiceInfo = tboundserviceinfo;
        }

        TBoundServiceInfo getBoundServiceInfo() {
            return this.mBoundServiceInfo;
        }

        boolean isConnected() {
            return this.mBinder != null;
        }

        void bind() {
            final MyServiceConnection myServiceConnection;
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBoundServiceInfo == null) {
                return;
            }
            if (ServiceWatcherImpl.D) {
                Log.d(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] binding to " + this.mBoundServiceInfo);
            }
            this.mRebinder = null;
            try {
                myServiceConnection = this;
                try {
                    if (ServiceWatcherImpl.this.mContext.bindServiceAsUser(new Intent(this.mBoundServiceInfo.getAction()).setComponent(this.mBoundServiceInfo.getComponentName()), myServiceConnection, this.mBoundServiceInfo.getFlags(), ServiceWatcherImpl.this.mHandler, UserHandle.of(this.mBoundServiceInfo.getUserId()))) {
                        return;
                    }
                    Log.e(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] unexpected bind failure - retrying later");
                    myServiceConnection.mRebinder = new Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.bind();
                        }
                    };
                    ServiceWatcherImpl.this.mHandler.postDelayed(myServiceConnection.mRebinder, ServiceWatcherImpl.RETRY_DELAY_MS);
                } catch (SecurityException e) {
                    e = e;
                    Log.e(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] " + myServiceConnection.mBoundServiceInfo + " bind failed", e);
                }
            } catch (SecurityException e2) {
                e = e2;
                myServiceConnection = this;
            }
        }

        void unbind() {
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBoundServiceInfo == null) {
                return;
            }
            if (ServiceWatcherImpl.D) {
                Log.d(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] unbinding from " + this.mBoundServiceInfo);
            }
            if (this.mRebinder != null) {
                ServiceWatcherImpl.this.mHandler.removeCallbacks(this.mRebinder);
                this.mRebinder = null;
            } else {
                ServiceWatcherImpl.this.mContext.unbindService(this);
            }
            onServiceDisconnected(this.mBoundServiceInfo.getComponentName());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void runOnBinder(ServiceWatcher.BinderOperation binderOperation) {
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBinder == null) {
                binderOperation.onError(new DeadObjectException());
                return;
            }
            try {
                binderOperation.run(this.mBinder);
            } catch (RemoteException | RuntimeException e) {
                Log.e(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] error running operation on " + this.mBoundServiceInfo, e);
                binderOperation.onError(e);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            Preconditions.checkState(this.mBinder == null);
            Log.i(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] connected to " + componentName.toShortString());
            this.mBinder = iBinder;
            this.mForcingRebind = false;
            if (ServiceWatcherImpl.this.mServiceListener != null) {
                try {
                    ServiceWatcherImpl.this.mServiceListener.onBind(iBinder, this.mBoundServiceInfo);
                } catch (RemoteException | RuntimeException e) {
                    Log.e(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] error running operation on " + this.mBoundServiceInfo, e);
                }
            }
            if (ServiceWatcherImpl.this.mIsLocationService) {
                ServiceWatcherImpl.this.mNsHandler.removeMessages(205);
                ServiceWatcherImpl serviceWatcherImpl = ServiceWatcherImpl.this;
                serviceWatcherImpl.mPrevComponentName = serviceWatcherImpl.mComponentName;
                ServiceWatcherImpl.this.mComponentName = componentName.toString();
                ServiceWatcherImpl serviceWatcherImpl2 = ServiceWatcherImpl.this;
                serviceWatcherImpl2.mPrevPackageName = serviceWatcherImpl2.mPackageName;
                ServiceWatcherImpl.this.mPackageName = componentName.getPackageName();
                ServiceWatcherImpl.this.mConnectedTime = SystemClock.elapsedRealtime();
                ServiceWatcherImpl.this.mConnectionCount++;
                ServiceWatcherImpl.this.sendPorxyConnectionInfo(203);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            TBoundServiceInfo tboundserviceinfo;
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBinder == null) {
                return;
            }
            Log.i(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] disconnected from " + this.mBoundServiceInfo);
            this.mBinder = null;
            if (ServiceWatcherImpl.this.mServiceListener != null) {
                ServiceWatcherImpl.this.mServiceListener.onUnbind();
            }
            if (!ServiceWatcherImpl.this.mUnstableFallbackEnabled || (tboundserviceinfo = this.mBoundServiceInfo) == null || tboundserviceinfo.toString() == null) {
                return;
            }
            String string = this.mBoundServiceInfo.toString();
            if (Objects.equals(ServiceWatcherImpl.this.mDisconnectedService, string) && ServiceWatcherImpl.this.mDisconnectedStartTime > 0 && SystemClock.elapsedRealtime() - ServiceWatcherImpl.this.mDisconnectedStartTime <= 60000) {
                ServiceWatcherImpl.this.mDisconnectedCount++;
            } else {
                ServiceWatcherImpl.this.mDisconnectedService = string;
                ServiceWatcherImpl.this.mDisconnectedStartTime = SystemClock.elapsedRealtime();
                ServiceWatcherImpl.this.mDisconnectedCount = 1;
            }
            Log.d(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] Service disconnected : " + string + " Count = " + ServiceWatcherImpl.this.mDisconnectedCount);
            if (ServiceWatcherImpl.this.mDisconnectedCount >= 10) {
                Log.i(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] Service disconnected too many times, set as unstable : " + ServiceWatcherImpl.this.mDisconnectedService);
                ServiceWatcherImpl.this.mServiceSupplier.alertUnstableService(ServiceWatcherImpl.this.mDisconnectedService);
                ServiceWatcherImpl.this.mDisconnectedService = null;
                ServiceWatcherImpl.this.mDisconnectedStartTime = 0L;
                ServiceWatcherImpl.this.mDisconnectedCount = 0;
                if (!this.mForcingRebind) {
                    this.mForcingRebind = true;
                    ServiceWatcherImpl.this.onServiceChanged(true);
                }
            }
            if (ServiceWatcherImpl.this.mIsLocationService) {
                ServiceWatcherImpl.this.mDisconnectedTime = SystemClock.elapsedRealtime();
                ServiceWatcherImpl.this.mDisconnectionCount++;
                ServiceWatcherImpl.this.sendPorxyConnectionInfo(204);
                ServiceWatcherImpl.this.mNsHandler.sendEmptyMessageDelayed(205, 3600000L);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Preconditions.checkState(Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
            Log.w(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " died");
            if (ServiceWatcherImpl.this.mIsLocationService) {
                ServiceWatcherImpl.this.mBindingDiedTime = SystemClock.elapsedRealtime();
            }
            if (this.mForcingRebind) {
                return;
            }
            this.mForcingRebind = true;
            ServiceWatcherImpl.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBindingDied$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindingDied$0() {
            ServiceWatcherImpl.this.onServiceChanged(true);
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Log.e(ServiceWatcherImpl.TAG, NavigationBarInflaterView.SIZE_MOD_START + ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " has null binding");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPorxyConnectionInfo(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", this.mTag);
        bundle.putString("componentName", this.mComponentName);
        bundle.putString("prevComponentName", this.mPrevComponentName);
        bundle.putString("packageName", this.mPackageName);
        bundle.putString("prevPackageName", this.mPrevPackageName);
        bundle.putLong("connectedTime", this.mConnectedTime);
        bundle.putInt("connectionCount", this.mConnectionCount);
        bundle.putLong("disconnectedTime", this.mDisconnectedTime);
        bundle.putInt("disconnectionCount", this.mDisconnectionCount);
        bundle.putLong("bindingDiedTime", this.mBindingDiedTime);
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        messageObtain.obj = bundle;
        try {
            ((LocationManager) this.mContext.getSystemService("location")).notifyNSFLP(messageObtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
