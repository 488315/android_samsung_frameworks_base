package com.android.server.enterprise.license;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.analytics.activation.DevicePolicyListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DeviceProfileListener extends BroadcastReceiver {
    public final List mObservers = new ArrayList();

    public DeviceProfileListener(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        context.registerReceiver(this, intentFilter);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        char c;
        long clearCallingIdentity;
        final int i = 1;
        final int i2 = 0;
        Log.d(
                "[EnterpriseLicenseService] DeviceProfileListener",
                "onReceive(" + intent.getAction() + ")");
        String action = intent.getAction();
        action.getClass();
        switch (action.hashCode()) {
            case -2061058799:
                if (action.equals("android.intent.action.USER_REMOVED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 370775467:
                if (action.equals(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1121780209:
                if (action.equals("android.intent.action.USER_ADDED")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                Log.d("[EnterpriseLicenseService] DeviceProfileListener", "notifyUserRemoved()");
                Iterator it = ((ArrayList) this.mObservers).iterator();
                while (it.hasNext()) {
                    final EnterpriseLicenseService enterpriseLicenseService =
                            (EnterpriseLicenseService) ((IDeviceProfileObserver) it.next());
                    enterpriseLicenseService.getClass();
                    Log.d("EnterpriseLicenseService", "onUserRemoved " + intExtra);
                    enterpriseLicenseService.enforcePermission$1();
                    Log.d(
                            "EnterpriseLicenseService",
                            "revokeKnoxPermissionFromUninstalledPackages");
                    enterpriseLicenseService.enforcePermission$1();
                    Log.d("EnterpriseLicenseService", "getPackageNameFromAllActivations");
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 =
                            (ArrayList)
                                    EnterpriseLicenseService.mEdmStorageProvider.getValues(
                                            "LICENSE", new String[] {"pkgName"}, null);
                    if (!arrayList2.isEmpty()) {
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            String asString = ((ContentValues) it2.next()).getAsString("pkgName");
                            Log.d("EnterpriseLicenseService", "packageName found " + asString);
                            if (!TextUtils.isEmpty(asString)) {
                                arrayList.add(asString);
                            }
                        }
                    }
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        String str = (String) it3.next();
                        if (!enterpriseLicenseService.isPackageInstalled(str)) {
                            Log.d(
                                    "EnterpriseLicenseService",
                                    "revoking permissions from uninstalled package: " + str);
                            enterpriseLicenseService.resetLicenseByAdmin(str);
                        }
                    }
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        new Thread(
                                        new Runnable() { // from class:
                                                         // com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i3 = i2;
                                                EnterpriseLicenseService enterpriseLicenseService2 =
                                                        enterpriseLicenseService;
                                                switch (i3) {
                                                    case 0:
                                                        enterpriseLicenseService2.callLicenseAgent(
                                                                "ProfileOwnerRemoved", null, null);
                                                        break;
                                                    default:
                                                        enterpriseLicenseService2.callLicenseAgent(
                                                                "DeviceOwnerRemoved", null, null);
                                                        break;
                                                }
                                            }
                                        })
                                .start();
                    } finally {
                    }
                }
                return;
            case 1:
                String stringExtra =
                        intent.getStringExtra(DevicePolicyListener.EXTRA_DO_PO_PACKAGE_NAME);
                if (intent.getBooleanExtra(DevicePolicyListener.EXTRA_DO_CHANGED_STATUS, false)) {
                    Log.d(
                            "[EnterpriseLicenseService] DeviceProfileListener",
                            "notifyDeviceOwnerAdded()");
                    Iterator it4 = ((ArrayList) this.mObservers).iterator();
                    while (it4.hasNext()) {
                        ((EnterpriseLicenseService) ((IDeviceProfileObserver) it4.next()))
                                .getClass();
                        Log.d("EnterpriseLicenseService", "onDeviceOwnerAdded " + stringExtra);
                    }
                    return;
                }
                Log.d(
                        "[EnterpriseLicenseService] DeviceProfileListener",
                        "notifyDeviceOwnerRemoved()");
                Iterator it5 = ((ArrayList) this.mObservers).iterator();
                while (it5.hasNext()) {
                    final EnterpriseLicenseService enterpriseLicenseService2 =
                            (EnterpriseLicenseService) ((IDeviceProfileObserver) it5.next());
                    enterpriseLicenseService2.getClass();
                    Log.d("EnterpriseLicenseService", "onDeviceOwnerRemoved " + stringExtra);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        new Thread(
                                        new Runnable() { // from class:
                                                         // com.android.server.enterprise.license.EnterpriseLicenseService$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i3 = i;
                                                EnterpriseLicenseService
                                                        enterpriseLicenseService22 =
                                                                enterpriseLicenseService2;
                                                switch (i3) {
                                                    case 0:
                                                        enterpriseLicenseService22.callLicenseAgent(
                                                                "ProfileOwnerRemoved", null, null);
                                                        break;
                                                    default:
                                                        enterpriseLicenseService22.callLicenseAgent(
                                                                "DeviceOwnerRemoved", null, null);
                                                        break;
                                                }
                                            }
                                        })
                                .start();
                    } finally {
                    }
                }
                return;
            case 2:
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                Log.d("[EnterpriseLicenseService] DeviceProfileListener", "notifyUserAdded()");
                Iterator it6 = ((ArrayList) this.mObservers).iterator();
                while (it6.hasNext()) {
                    ((EnterpriseLicenseService) ((IDeviceProfileObserver) it6.next())).getClass();
                    Log.d("EnterpriseLicenseService", "onUserAdded " + intExtra2);
                }
                return;
            default:
                return;
        }
    }
}
