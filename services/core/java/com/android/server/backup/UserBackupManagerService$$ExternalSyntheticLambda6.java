package com.android.server.backup;

import android.app.backup.ISelectBackupTransportCallback;
import android.content.ComponentName;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.backup.transport.TransportNotRegisteredException;

public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda6(
            UserBackupManagerService.AnonymousClass2 anonymousClass2, String str, String[] strArr) {
        this.f$0 = anonymousClass2;
        this.f$1 = str;
        this.f$2 = strArr;
    }

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda6(
            UserBackupManagerService userBackupManagerService,
            ComponentName componentName,
            ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        this.f$0 = userBackupManagerService;
        this.f$1 = componentName;
        this.f$2 = iSelectBackupTransportCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        switch (this.$r8$classId) {
            case 0:
                UserBackupManagerService userBackupManagerService =
                        (UserBackupManagerService) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                ISelectBackupTransportCallback iSelectBackupTransportCallback =
                        (ISelectBackupTransportCallback) this.f$2;
                TransportManager transportManager = userBackupManagerService.mTransportManager;
                synchronized (transportManager.mTransportLock) {
                    i = -1;
                    i2 = 0;
                    try {
                        transportManager.selectTransport(
                                transportManager.getTransportName(componentName));
                    } catch (TransportNotRegisteredException unused) {
                        int registerTransport = transportManager.registerTransport(componentName);
                        if (registerTransport != 0) {
                            i2 = registerTransport;
                        } else {
                            synchronized (transportManager.mTransportLock) {
                                try {
                                    try {
                                        transportManager.selectTransport(
                                                transportManager.getTransportName(componentName));
                                    } catch (TransportNotRegisteredException unused2) {
                                        Slog.wtf(
                                                "BackupTransportManager",
                                                TransportManager.addUserIdToLogMessage(
                                                        transportManager.mUserId,
                                                        "Transport got unregistered"));
                                        i2 = -1;
                                    }
                                } finally {
                                }
                            }
                        }
                    } finally {
                    }
                }
                String str = null;
                if (i2 == 0) {
                    try {
                        str =
                                userBackupManagerService.mTransportManager.getTransportName(
                                        componentName);
                        userBackupManagerService.updateStateForTransport(str);
                    } catch (TransportNotRegisteredException unused3) {
                        Slog.e(
                                "BackupManagerService",
                                UserBackupManagerService.addUserIdToLogMessage(
                                        userBackupManagerService.mUserId,
                                        "Transport got unregistered"));
                    }
                }
                i = i2;
                try {
                    if (str != null) {
                        iSelectBackupTransportCallback.onSuccess(str);
                    } else {
                        iSelectBackupTransportCallback.onFailure(i);
                    }
                    return;
                } catch (RemoteException unused4) {
                    Slog.e(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    userBackupManagerService.mUserId,
                                    "ISelectBackupTransportCallback listener not available"));
                    return;
                }
            default:
                UserBackupManagerService.AnonymousClass2 anonymousClass2 =
                        (UserBackupManagerService.AnonymousClass2) this.f$0;
                String str2 = (String) this.f$1;
                String[] strArr = (String[]) this.f$2;
                TransportManager transportManager2 =
                        UserBackupManagerService.this.mTransportManager;
                transportManager2.getClass();
                if (strArr.length != 1 || !strArr[0].equals(str2)) {
                    ArraySet arraySet = new ArraySet(strArr.length);
                    for (String str3 : strArr) {
                        arraySet.add(new ComponentName(str2, str3));
                    }
                    if (arraySet.isEmpty()) {
                        return;
                    }
                    synchronized (transportManager2.mTransportLock) {
                        ((ArrayMap) transportManager2.mRegisteredTransportsDescriptionMap)
                                .keySet()
                                .removeIf(
                                        new TransportManager$$ExternalSyntheticLambda2(
                                                0, arraySet));
                    }
                    transportManager2.registerTransportsFromPackage(
                            str2, new TransportManager$$ExternalSyntheticLambda2(0, arraySet));
                    return;
                }
                try {
                    int applicationEnabledSetting =
                            transportManager2.mPackageManager.getApplicationEnabledSetting(str2);
                    if (applicationEnabledSetting == 0) {
                        transportManager2.registerTransportsFromPackage(
                                str2, new TransportManager$$ExternalSyntheticLambda1());
                        return;
                    }
                    if (applicationEnabledSetting == 1) {
                        transportManager2.registerTransportsFromPackage(
                                str2, new TransportManager$$ExternalSyntheticLambda1());
                        return;
                    }
                    if (applicationEnabledSetting == 2) {
                        transportManager2.onPackageRemoved(str2);
                        return;
                    }
                    if (applicationEnabledSetting == 3) {
                        transportManager2.onPackageRemoved(str2);
                        return;
                    }
                    Slog.w(
                            "BackupTransportManager",
                            TransportManager.addUserIdToLogMessage(
                                    transportManager2.mUserId,
                                    "Package "
                                            + str2
                                            + " enabled setting: "
                                            + applicationEnabledSetting));
                    return;
                } catch (IllegalArgumentException unused5) {
                    return;
                }
        }
    }
}
