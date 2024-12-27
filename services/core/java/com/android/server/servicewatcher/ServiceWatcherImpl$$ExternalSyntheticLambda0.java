package com.android.server.servicewatcher;

import android.os.DeadObjectException;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.util.Preconditions;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ServiceWatcherImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ServiceWatcherImpl.MyServiceConnection f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ServiceWatcherImpl$$ExternalSyntheticLambda0(
            ServiceWatcherImpl.MyServiceConnection myServiceConnection, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = myServiceConnection;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ServiceWatcherImpl.MyServiceConnection myServiceConnection = this.f$0;
                ServiceWatcherImpl.MyServiceConnection myServiceConnection2 =
                        (ServiceWatcherImpl.MyServiceConnection) this.f$1;
                myServiceConnection.getClass();
                Preconditions.checkState(
                        Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
                if (myServiceConnection.mBoundServiceInfo != null) {
                    if (ServiceWatcherImpl.D) {
                        Log.d(
                                "ServiceWatcher",
                                "["
                                        + ServiceWatcherImpl.this.mTag
                                        + "] unbinding from "
                                        + myServiceConnection.mBoundServiceInfo);
                    }
                    ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0
                            serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0 =
                                    myServiceConnection.mRebinder;
                    if (serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0 != null) {
                        ServiceWatcherImpl.this.mHandler.removeCallbacks(
                                serviceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0);
                        myServiceConnection.mRebinder = null;
                    } else {
                        ServiceWatcherImpl.this.mContext.unbindService(myServiceConnection);
                    }
                    myServiceConnection.onServiceDisconnected(
                            myServiceConnection.mBoundServiceInfo.mComponentName);
                }
                myServiceConnection2.bind();
                break;
            default:
                ServiceWatcherImpl.MyServiceConnection myServiceConnection3 = this.f$0;
                ServiceWatcher$BinderOperation serviceWatcher$BinderOperation =
                        (ServiceWatcher$BinderOperation) this.f$1;
                myServiceConnection3.getClass();
                Preconditions.checkState(
                        Looper.myLooper() == ServiceWatcherImpl.this.mHandler.getLooper());
                if (myServiceConnection3.mBinder == null) {
                    serviceWatcher$BinderOperation.onError(new DeadObjectException());
                    break;
                } else {
                    try {
                        serviceWatcher$BinderOperation.run(myServiceConnection3.mBinder);
                        break;
                    } catch (RemoteException | RuntimeException e) {
                        Log.e(
                                "ServiceWatcher",
                                "["
                                        + ServiceWatcherImpl.this.mTag
                                        + "] error running operation on "
                                        + myServiceConnection3.mBoundServiceInfo,
                                e);
                        serviceWatcher$BinderOperation.onError(e);
                    }
                }
        }
    }
}
