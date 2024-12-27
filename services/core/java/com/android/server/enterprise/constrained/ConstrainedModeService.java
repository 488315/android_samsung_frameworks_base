package com.android.server.enterprise.constrained;

import android.R;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.ServiceManager;
import android.util.Log;

import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.EnrollData;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.localservice.ConstrainedModeInternal;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ConstrainedModeService extends ConstrainedModeInternal {
    public static final byte[] CONSTRAINED_DELIMITER = {8, 14, 25, -1};
    public static final Object lock = new Object();
    public static ConstrainedModeService sConstrainedService = null;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final PackageManagerAdapter mPackageManagerAdapter;
    public final AnonymousClass1 mReceiver;
    public EnterpriseDeviceManager mEDM = null;
    public final HashMap mCachedConstrainedData = new HashMap();

    public ConstrainedModeService(Context context) {
        this.mEdmStorageProvider = null;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.enterprise.constrained.ConstrainedModeService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ConstrainedModeService constrainedModeService;
                        List constrainedStateAll;
                        if (!intent.getAction().equals("android.intent.action.LOCALE_CHANGED")
                                || (constrainedStateAll =
                                                (constrainedModeService =
                                                                ConstrainedModeService.this)
                                                        .getConstrainedStateAll())
                                        == null) {
                            return;
                        }
                        Iterator it = ((ArrayList) constrainedStateAll).iterator();
                        while (it.hasNext()) {
                            EnrollData enrollData = (EnrollData) it.next();
                            constrainedModeService.showConstrainedStateNotification(
                                    enrollData.getPackageName(), null, null, null, false);
                            if (enrollData.getConstrainedState() == 0) {
                                constrainedModeService.showConstrainedStateNotification(
                                        enrollData.getPackageName(),
                                        enrollData.getComment(),
                                        enrollData.getDownloadUrl(),
                                        enrollData.getTargetPkgName(),
                                        true);
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        context.registerReceiver(broadcastReceiver, intentFilter);
        this.mPackageManagerAdapter = PackageManagerAdapter.getInstance(context);
        updateConstrainedStateData(false);
        new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.enterprise.constrained.ConstrainedModeService.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                boolean z = false;
                                while (!z) {
                                    if (INotificationManager.Stub.asInterface(
                                                    ServiceManager.getService("notification"))
                                            != null) {
                                        ConstrainedModeService.this.updateConstrainedStateData(
                                                true);
                                        z = true;
                                    } else {
                                        try {
                                            Thread.sleep(1000L);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        })
                .start();
    }

    public static List split(byte[] bArr, byte[] bArr2) {
        LinkedList linkedList = new LinkedList();
        int i = 0;
        int i2 = 0;
        while (i < bArr2.length) {
            int i3 = 0;
            while (true) {
                if (i3 >= bArr.length) {
                    linkedList.add(Arrays.copyOfRange(bArr2, i2, i));
                    i += bArr.length;
                    i2 = i;
                    break;
                }
                int i4 = i + i3;
                if (i4 < bArr2.length && bArr[i3] == bArr2[i4]) {
                    i3++;
                }
            }
            i++;
        }
        linkedList.add(Arrays.copyOfRange(bArr2, i2, bArr2.length));
        return linkedList;
    }

    public final boolean checkConstrainedState() {
        List constrainedStateAll = getConstrainedStateAll();
        if (constrainedStateAll != null) {
            Iterator it = ((ArrayList) constrainedStateAll).iterator();
            while (it.hasNext()) {
                if (((EnrollData) it.next()).getConstrainedState() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void cleanUpConstrainedState(int i) {
        if (i != Binder.getCallingUid()) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BIND_DEVICE_ADMIN",
                    "Only system or itself can remove an EDM admin");
        }
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                1, contentValues, Constants.JSON_CLIENT_DATA_STATUS, i, "adminUid");
        if (this.mEdmStorageProvider.getValue(contentValues, "ConstrainedStateTable", "adminUid")
                == null) {
            Log.d("ConstrainedMode", "constrained state will go on");
            return;
        }
        String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
        if (packageNameForUid != null) {
            disableConstrainedStateInternal(i, packageNameForUid);
        }
    }

    public final boolean disableConstrainedState(int i) {
        return disableConstrainedStateInternal(i, this.mEdmStorageProvider.getPackageNameForUid(i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003e, code lost:

       android.util.Log.i("ConstrainedMode", "remove! " + ((java.lang.String) r1.getKey()));
       new java.io.File("/efs/constrained", (java.lang.String) r1.getKey()).delete();
       r20.mEdmStorageProvider.putInt(r21, 0, 1, "ConstrainedStateTable", com.samsung.android.knoxguard.service.utils.Constants.JSON_CLIENT_DATA_STATUS);
       showConstrainedStateNotification(r22, null, null, null, false);
       updateConstrainedStateData(true);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean disableConstrainedStateInternal(int r21, java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.constrained.ConstrainedModeService.disableConstrainedStateInternal(int,"
                    + " java.lang.String):boolean");
    }

    public final void dump(PrintWriter printWriter) {
        List constrainedStateAll = getConstrainedStateAll();
        if (constrainedStateAll != null) {
            Iterator it = ((ArrayList) constrainedStateAll).iterator();
            while (it.hasNext()) {
                EnrollData enrollData = (EnrollData) it.next();
                if (enrollData.getConstrainedState() == 0) {
                    int policyBitMask = enrollData.getPolicyBitMask();
                    StringBuilder sb = new StringBuilder("  Restrict Camera : ");
                    sb.append((policyBitMask & 1) > 0 ? "true" : "false");
                    sb.append(System.lineSeparator());
                    printWriter.write(sb.toString());
                    StringBuilder sb2 = new StringBuilder("  Restrict MTP : ");
                    sb2.append((policyBitMask & 4) > 0 ? "true" : "false");
                    sb2.append(System.lineSeparator());
                    printWriter.write(sb2.toString());
                    StringBuilder sb3 = new StringBuilder("  Restrict Bluetooth : ");
                    sb3.append((policyBitMask & 8) > 0 ? "true" : "false");
                    sb3.append(System.lineSeparator());
                    printWriter.write(sb3.toString());
                    StringBuilder sb4 = new StringBuilder("  Restrict SDCard : ");
                    sb4.append((policyBitMask & 2) > 0 ? "true" : "false");
                    sb4.append(System.lineSeparator());
                    printWriter.write(sb4.toString());
                    StringBuilder sb5 = new StringBuilder("  Restrict Tethering : ");
                    sb5.append((policyBitMask & 16) > 0 ? "true" : "false");
                    sb5.append(System.lineSeparator());
                    printWriter.write(sb5.toString());
                    StringBuilder sb6 = new StringBuilder("  Restrict USB Debugging : ");
                    sb6.append((policyBitMask & 32) > 0 ? "true" : "false");
                    sb6.append(System.lineSeparator());
                    printWriter.write(sb6.toString());
                    StringBuilder sb7 = new StringBuilder("  Restrict Screen Capture : ");
                    sb7.append((policyBitMask & 64) > 0 ? "true" : "false");
                    sb7.append(System.lineSeparator());
                    printWriter.write(sb7.toString());
                }
            }
            printWriter.write(System.lineSeparator());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x016d A[Catch: all -> 0x00d6, TryCatch #2 {all -> 0x00d6, blocks: (B:89:0x00c5, B:91:0x00cb, B:35:0x00fa, B:37:0x0109, B:39:0x010f, B:40:0x0120, B:42:0x0128, B:44:0x012e, B:46:0x013a, B:49:0x0142, B:50:0x0160, B:52:0x016d, B:54:0x0173, B:58:0x0177, B:60:0x0181, B:56:0x017c, B:63:0x0194, B:86:0x0117, B:31:0x00dd, B:33:0x00e3, B:87:0x00ee), top: B:88:0x00c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0181 A[Catch: all -> 0x00d6, TryCatch #2 {all -> 0x00d6, blocks: (B:89:0x00c5, B:91:0x00cb, B:35:0x00fa, B:37:0x0109, B:39:0x010f, B:40:0x0120, B:42:0x0128, B:44:0x012e, B:46:0x013a, B:49:0x0142, B:50:0x0160, B:52:0x016d, B:54:0x0173, B:58:0x0177, B:60:0x0181, B:56:0x017c, B:63:0x0194, B:86:0x0117, B:31:0x00dd, B:33:0x00e3, B:87:0x00ee), top: B:88:0x00c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01ad A[Catch: all -> 0x00a3, TRY_LEAVE, TryCatch #0 {all -> 0x00a3, blocks: (B:23:0x006c, B:24:0x0092, B:26:0x0098, B:28:0x00a7, B:64:0x019a, B:66:0x01ad, B:69:0x01b4), top: B:22:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01b4 A[Catch: all -> 0x00a3, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x00a3, blocks: (B:23:0x006c, B:24:0x0092, B:26:0x0098, B:28:0x00a7, B:64:0x019a, B:66:0x01ad, B:69:0x01b4), top: B:22:0x006c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean enableConstrainedState(
            int r17,
            java.lang.String r18,
            java.lang.String r19,
            java.lang.String r20,
            java.lang.String r21,
            int r22) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.constrained.ConstrainedModeService.enableConstrainedState(int,"
                    + " java.lang.String, java.lang.String, java.lang.String, java.lang.String,"
                    + " int):boolean");
    }

    public final int getConstrainedState() {
        List constrainedStateAll = getConstrainedStateAll();
        if (constrainedStateAll == null) {
            return 0;
        }
        Iterator it = ((ArrayList) constrainedStateAll).iterator();
        while (it.hasNext()) {
            if (((EnrollData) it.next()).getConstrainedState() == 0) {
                return 2;
            }
        }
        return 1;
    }

    public final List getConstrainedStateAll() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (this.mCachedConstrainedData != null) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = this.mCachedConstrainedData.entrySet().iterator();
                    while (it.hasNext()) {
                        arrayList.add((EnrollData) ((Map.Entry) it.next()).getValue());
                    }
                    if (!arrayList.isEmpty()) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return arrayList;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final boolean isRestrictedByConstrainedState(int i) {
        List constrainedStateAll = getConstrainedStateAll();
        if (constrainedStateAll == null) {
            return false;
        }
        Iterator it = ((ArrayList) constrainedStateAll).iterator();
        while (it.hasNext()) {
            EnrollData enrollData = (EnrollData) it.next();
            if (enrollData.getConstrainedState() == 0 && (enrollData.getPolicyBitMask() & i) > 0) {
                return true;
            }
        }
        return false;
    }

    public final void showConstrainedStateNotification(
            String str, String str2, String str3, String str4, boolean z) {
        String m$1 =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "_ConstrainedNoti");
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            Log.d("ConstrainedMode", "Failed to get NotificationManager");
            return;
        }
        notificationManager.createNotificationChannel(
                new NotificationChannel("CONSTRAINED_MODE", str, 4));
        if (!z) {
            notificationManager.cancel(4558);
            return;
        }
        Notification.Builder builder = new Notification.Builder(this.mContext, "CONSTRAINED_MODE");
        builder.setWhen(0L);
        builder.setSmallIcon(R.drawable.pointer_grabbing_icon);
        builder.setContentTitle(this.mContext.getString(R.string.find));
        if (str2.equals("DEFAULT")) {
            builder.setContentText(
                    this.mContext.getString(R.string.grant_credentials_permission_message_footer));
            builder.setStyle(
                    new Notification.BigTextStyle()
                            .bigText(
                                    this.mContext.getString(
                                            R.string.grant_credentials_permission_message_footer)));
        } else {
            builder.setContentText(str2);
            builder.setStyle(new Notification.BigTextStyle().bigText(str2));
        }
        builder.setPriority(2);
        builder.setOngoing(true);
        if (str3 != null && str3.length() > 0) {
            Intent intent =
                    new Intent(
                            "com.samsung.android.knox.intent.action.CHECK_REENROLLMENT_INTERNAL");
            intent.putExtra("pkg", str);
            intent.putExtra("url", str3);
            if (str4 != null && str4.length() > 0) {
                intent.putExtra("targetPkgName", str4);
            }
            builder.setContentIntent(
                    PendingIntent.getBroadcast(this.mContext, m$1.hashCode(), intent, 67108864));
        }
        notificationManager.notify(4558, builder.build());
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x02a7 A[Catch: all -> 0x0263, Exception -> 0x0267, TryCatch #17 {Exception -> 0x0267, all -> 0x0263, blocks: (B:22:0x02bb, B:101:0x025f, B:102:0x0282, B:103:0x026b, B:124:0x0292, B:130:0x02a7), top: B:100:0x025f }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0313 A[Catch: all -> 0x02f8, TRY_LEAVE, TryCatch #14 {all -> 0x02f8, blocks: (B:29:0x030c, B:31:0x0313, B:173:0x02e7, B:175:0x02ed, B:178:0x02fc), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x031c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateConstrainedStateData(boolean r34) {
        /*
            Method dump skipped, instructions count: 800
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.constrained.ConstrainedModeService.updateConstrainedStateData(boolean):void");
    }
}
