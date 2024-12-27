package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.admin.DeviceAdminService;
import android.app.admin.IDeviceAdminService;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import com.android.internal.os.BackgroundThread;
import com.android.server.am.PersistentConnection;
import com.android.server.appbinding.AppBindingUtils;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceAdminServiceController {
    public final DevicePolicyConstants mConstants;
    public final Context mContext;
    public final Handler mHandler;
    public final DevicePolicyManagerService.Injector mInjector;
    public final Object mLock = new Object();
    public final SparseArray mConnections = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DevicePolicyServiceConnection extends PersistentConnection {
        @Override // com.android.server.am.PersistentConnection
        public final Object asInterface(IBinder iBinder) {
            return IDeviceAdminService.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.am.PersistentConnection
        public final int getBindFlags() {
            return 67108864;
        }
    }

    public DeviceAdminServiceController(DevicePolicyManagerService devicePolicyManagerService, DevicePolicyConstants devicePolicyConstants) {
        DevicePolicyManagerService.Injector injector = devicePolicyManagerService.mInjector;
        this.mInjector = injector;
        this.mContext = injector.mContext;
        this.mHandler = new Handler(BackgroundThread.get().getLooper());
        this.mConstants = devicePolicyConstants;
    }

    public final void disconnectServiceOnUserLocked(int i) {
        if (this.mConnections.contains(i)) {
            Iterator it = ((Map) this.mConnections.get(i)).keySet().iterator();
            while (it.hasNext()) {
                ((DevicePolicyServiceConnection) ((Map) this.mConnections.get(i)).get((String) it.next())).unbind();
            }
            this.mConnections.remove(i);
        }
    }

    public final void disconnectServiceOnUserLocked(int i, String str) {
        DevicePolicyServiceConnection devicePolicyServiceConnection = this.mConnections.contains(i) ? (DevicePolicyServiceConnection) ((Map) this.mConnections.get(i)).get(str) : null;
        if (devicePolicyServiceConnection != null) {
            devicePolicyServiceConnection.unbind();
            ((Map) this.mConnections.get(i)).remove(str);
            if (((Map) this.mConnections.get(i)).isEmpty()) {
                this.mConnections.remove(i);
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                if (this.mConnections.size() == 0) {
                    return;
                }
                indentingPrintWriter.println("Admin Services:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mConnections.size(); i++) {
                    int keyAt = this.mConnections.keyAt(i);
                    indentingPrintWriter.print("User: ");
                    indentingPrintWriter.println(keyAt);
                    for (String str : ((Map) this.mConnections.get(keyAt)).keySet()) {
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print("Package: ");
                        indentingPrintWriter.println(str);
                        DevicePolicyServiceConnection devicePolicyServiceConnection = (DevicePolicyServiceConnection) ((Map) this.mConnections.valueAt(i)).get(str);
                        indentingPrintWriter.increaseIndent();
                        devicePolicyServiceConnection.dump("", indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startServiceForAdmin(int i, String str) {
        Object obj;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Object obj2 = this.mLock;
            try {
                synchronized (obj2) {
                    try {
                        this.mInjector.getClass();
                        ServiceInfo findService = AppBindingUtils.findService(str, i, "android.app.action.DEVICE_ADMIN_SERVICE", "android.permission.BIND_DEVICE_ADMIN", DeviceAdminService.class, AppGlobals.getPackageManager(), new StringBuilder());
                        if (findService != null) {
                            if ((this.mConnections.contains(i) ? (PersistentConnection) ((Map) this.mConnections.get(i)).get(str) : null) != null) {
                                disconnectServiceOnUserLocked(i, str);
                            }
                            ComponentName componentName = findService.getComponentName();
                            Context context = this.mContext;
                            Handler handler = this.mHandler;
                            DevicePolicyConstants devicePolicyConstants = this.mConstants;
                            obj = obj2;
                            DevicePolicyServiceConnection devicePolicyServiceConnection = new DevicePolicyServiceConnection("DevicePolicyManager", context, handler, i, componentName, devicePolicyConstants.DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC, devicePolicyConstants.DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE, devicePolicyConstants.DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC, devicePolicyConstants.DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC);
                            if (!this.mConnections.contains(i)) {
                                this.mConnections.put(i, new HashMap());
                            }
                            ((Map) this.mConnections.get(i)).put(str, devicePolicyServiceConnection);
                            synchronized (devicePolicyServiceConnection.mLock) {
                                devicePolicyServiceConnection.mShouldBeBound = true;
                                devicePolicyServiceConnection.bindInnerLocked(true);
                            }
                            return;
                        }
                        disconnectServiceOnUserLocked(i, str);
                    } catch (Throwable th) {
                        th = th;
                        obj = obj2;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void stopServiceForAdmin(int i, String str) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                disconnectServiceOnUserLocked(i, str);
            }
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void stopServicesForUser(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                disconnectServiceOnUserLocked(i);
            }
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
