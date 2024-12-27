package com.android.server.knox.zt.devicetrust.task;

import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.data.NetworkEventData;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import com.samsung.android.knox.zt.internal.IKnoxZtInternalService;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkEventMonitoring extends SchedulableMonitoringTask {
    public static final int SYSTEM_UID = 1000;
    public static final int TAG_NETWORK_EVENT_UNKNOWN = -1;
    public final String TAG;
    public final long mBootTimeNanos;
    public final int mEventType;
    public EndpointMonitorImpl.Injector mInjector;

    public NetworkEventMonitoring(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            EndpointMonitorImpl.Injector injector) {
        super(i, i2, i3, i4, i5, iEndpointMonitorListener, null, injector);
        this.mInjector = injector;
        this.mEventType = i;
        this.mBootTimeNanos = injector.mBootTimeNanos;
        this.TAG = getTag();
    }

    public final String getInterfaceNameFromIndex(int i) {
        try {
            return NetworkInterface.getByIndex(i).getName();
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "getInterfaceName error: ", this.TAG);
            return "";
        }
    }

    public final String getPackageNameFromUid(int i) {
        try {
            PackageManager packageManager = this.mInjector.mContext.getPackageManager();
            return packageManager != null ? packageManager.getNameForUid(i) : "";
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "getPackageNameForUid error: ", this.TAG);
            return "";
        }
    }

    public final int getTagFromEventType(int i) {
        if (i == 1) {
            return 1;
        }
        if (i != 2) {
            return i != 3 ? -1 : 3;
        }
        return 2;
    }

    public final boolean isPlatformSignedApp(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
                                .checkUidSignatures(i, 1000)
                        == 0) {
                    Log.d(this.TAG, "uid = " + i + " signature matched.");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            } catch (Exception e) {
                Log.e(this.TAG, "Exception checking platform signed app.. " + e);
            }
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "uid = ", " signature didn't match.", this.TAG);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyKnoxZtInternalService(NetworkEventData networkEventData, int i) {
        if (networkEventData == null) {
            Log.d(this.TAG, "notifyKnoxZtInternalService: null event data");
            return;
        }
        try {
            IKnoxZtInternalService ztInternalService = this.mInjector.getZtInternalService();
            if (ztInternalService == null) {
                Log.d(this.TAG, "notifyKnoxZtInternalService: KnoxZtService is null.");
            } else {
                Log.d(this.TAG, "notifyKnoxZtInternalService: sending data to framework..");
                ztInternalService.notifyFrameworkEvent(6, i, networkEventData.getBundle());
            }
        } catch (Throwable th) {
            Log.e(this.TAG, "notifyKnoxZtInternalService: Failed to bind zt internal service", th);
        }
    }

    public final void onEvent(NetworkEventData networkEventData) throws RemoteException {
        if (networkEventData == null) {
            Log.i(this.TAG, "onEvent(): data is null.");
            return;
        }
        int tagFromEventType = getTagFromEventType(networkEventData.eventType);
        if (tagFromEventType == -1) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onEvent: Invalid network event type: "),
                    networkEventData.eventType,
                    this.TAG);
            return;
        }
        String packageNameFromUid = getPackageNameFromUid(networkEventData.uid);
        if (tagFromEventType == 1 && isPlatformSignedApp(networkEventData.uid)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Excluding insecure network event for package ", packageNameFromUid, this.TAG);
            return;
        }
        networkEventData.packageName = packageNameFromUid;
        networkEventData.interfaceName = getInterfaceNameFromIndex(networkEventData.ifIndex);
        Log.d(this.TAG, "onEvent: data {" + networkEventData + "}");
        Log.d(this.TAG, "onEvent: passing data to notifyKnoxZtInternalService..");
        notifyKnoxZtInternalService(networkEventData, tagFromEventType);
    }

    @Override // com.android.server.knox.zt.devicetrust.task.MonitoringTask
    public final void onMonitored() {
        ArrayList readNetworkEventData =
                this.mInjector.mNative.readNetworkEventData(this.mEventType);
        if (readNetworkEventData != null) {
            try {
                Iterator it = readNetworkEventData.iterator();
                while (it.hasNext()) {
                    NetworkEventData networkEventData = (NetworkEventData) it.next();
                    Log.d(this.TAG, "onMonitored() sending data to onEvent");
                    networkEventData.adjustToActualTimeInMillis(this.mBootTimeNanos);
                    onEvent(networkEventData);
                }
            } catch (RemoteException e) {
                onTransactionFailure("Failed in transaction: ", e);
            }
        }
    }
}
