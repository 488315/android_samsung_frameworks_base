package com.android.server.enterprise.mam;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.localservice.MobileApplicationManagementInternal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MobileApplicationManagementService extends MobileApplicationManagementInternal {
    public static AnonymousClass1 mamReceiver;
    public static MobileApplicationManagementService mamService;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final IPackageManager mPMS;
    public static final Object lock = new Object();
    public static final Object mAppPermissionLock = new Object();
    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v7, types: [android.content.BroadcastReceiver, com.android.server.enterprise.mam.MobileApplicationManagementService$1] */
    public MobileApplicationManagementService(Context context) {
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mPMS = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        try {
            if (mamReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter.addDataScheme("package");
                List stringList = edmStorageProvider.getStringList(null, "ENT_APP_MGMT_RT", "pkgName");
                KnoxsdkFileLog.d("MobileApplicationManagementService", "registerMamReceiver: getEamPackages = [" + stringList.toString() + "]");
                Iterator it = ((ArrayList) stringList).iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    KnoxsdkFileLog.d("MobileApplicationManagementService", "registerMamReceiver: addDataSchemeSpecificPart = [" + str + "]");
                    intentFilter.addDataSchemeSpecificPart(str, 0);
                }
                ?? r8 = new BroadcastReceiver() { // from class: com.android.server.enterprise.mam.MobileApplicationManagementService.1
                    /* JADX WARN: Removed duplicated region for block: B:14:0x00f0  */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(android.content.Context r9, android.content.Intent r10) {
                        /*
                            Method dump skipped, instructions count: 369
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.mam.MobileApplicationManagementService.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
                    }
                };
                mamReceiver = r8;
                this.mContext.registerReceiver(r8, intentFilter, null, null);
            }
        } catch (IllegalArgumentException e) {
            KnoxsdkFileLog.e("MobileApplicationManagementService", "Cannot register registerMamReceiver - " + e.getMessage());
        }
    }

    public static Object deserializeObject(byte[] bArr) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
            try {
                Object readObject = objectInputStream.readObject();
                objectInputStream.close();
                return readObject;
            } catch (Throwable th) {
                try {
                    objectInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | ClassNotFoundException unused) {
            return null;
        }
    }

    public static final String fingerprint(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            char[] cArr = HEX_DIGITS;
            sb.append(cArr[(b >> 4) & 15]);
            sb.append(cArr[b & 15]);
            i++;
            if (i != bArr.length) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    public final List getPermission(String str) {
        List arrayList;
        String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("get permissions for other app ", str);
        int callingUid = Binder.getCallingUid();
        if (!UserHandle.isSameApp(callingUid, 1000) && callingUid != 0) {
            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Only system may: ", m));
        }
        synchronized (mAppPermissionLock) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("pkgName", str);
            byte[] blob = this.mEdmStorageProvider.getBlob(contentValues, "ENT_APP_MGMT_RT", "permission");
            arrayList = blob != null ? (List) deserializeObject(blob) : new ArrayList();
        }
        return arrayList;
    }
}
