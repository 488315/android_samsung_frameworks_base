package com.android.settingslib;

import android.app.AppGlobals;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.IPermissionManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EdmConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecNotificationBlockManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final HashSet mConfigCSCSet = new HashSet();
    public static final HashMap mMetaDataMap = new HashMap();
    public static final HashSet mExceptableSystemAppSet = new HashSet();

    public static int checkConfigCSC(Context context, String str, NotificationChannel notificationChannel) {
        HashSet hashSet = mConfigCSCSet;
        boolean isEmpty = hashSet.isEmpty();
        boolean z = DEBUG;
        if (isEmpty) {
            Collections.addAll(hashSet, context.getResources().getStringArray(17236256));
            Collections.addAll(hashSet, context.getResources().getStringArray(17236300));
            String string = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigBlockNotiAppList");
            if (string != null && string.length() > 0) {
                for (String str2 : string.split(",")) {
                    if (str2 != null) {
                        if (z) {
                            Log.d("SecNotificationBlockManager", "initConfigCSCSet:CSC:".concat(str2));
                        }
                        hashSet.add(str2);
                    }
                }
            }
        }
        if (hashSet.contains(str)) {
            if (z) {
                AbstractC0000x2c234b15.m3m("checkConfigCSC:", str, "SecNotificationBlockManager");
            }
            return 2;
        }
        if (notificationChannel != null) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ":");
            m2m.append(notificationChannel.getId());
            if (!hashSet.contains(m2m.toString())) {
                return 4;
            }
            if (z) {
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("checkConfigCSC with channel :", str, ":");
                m4m.append(notificationChannel.getId());
                Log.d("SecNotificationBlockManager", m4m.toString());
            }
            return 2;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (!TextUtils.isEmpty(str3)) {
                if (str3.startsWith(str + ":")) {
                    AbstractC0000x2c234b15.m3m("checkConfigCSC :", str, "SecNotificationBlockManager");
                    return 2;
                }
            }
        }
        return 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0029, code lost:
    
        r6 = r0.getPackageInfo(r3, 64);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
    
        r3.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (((java.lang.Boolean) r9.get(r10)).booleanValue() != false) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4 A[Catch: NameNotFoundException -> 0x00c0, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x00c0, blocks: (B:3:0x0007, B:5:0x000d, B:9:0x001c, B:11:0x001f, B:20:0x002f, B:13:0x0033, B:23:0x0038, B:27:0x004a, B:31:0x0063, B:33:0x006c, B:37:0x00a4, B:39:0x007a, B:41:0x007e, B:44:0x008e, B:46:0x0096, B:47:0x009b, B:17:0x0029), top: B:2:0x0007, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int checkSystemAppAndMetaData(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
            if (applicationInfo != null) {
                String str2 = applicationInfo.packageName;
                String[] packagesForUid = packageManager.getPackagesForUid(applicationInfo.uid);
                PackageInfo packageInfo = null;
                if (packagesForUid != null && str2 != null) {
                    int i = 0;
                    while (true) {
                        if (i >= packagesForUid.length) {
                            break;
                        }
                        if (str2.equals(packagesForUid[i])) {
                            break;
                        }
                        i++;
                    }
                }
                if (packageInfo != null) {
                    boolean isSystemPackage = Utils.isSystemPackage(context.getResources(), packageManager, packageInfo);
                    boolean z = DEBUG;
                    if (!isSystemPackage) {
                        if (!z) {
                            return 1;
                        }
                        Log.d("SecNotificationBlockManager", "checkSystemAppAndMetaData:" + str + ":nonSystemPackage");
                        return 1;
                    }
                    HashMap hashMap = mMetaDataMap;
                    int i2 = 2;
                    if (!hashMap.containsKey(str)) {
                        Bundle bundle = applicationInfo.metaData;
                        if (bundle == null) {
                            HashSet hashSet = mExceptableSystemAppSet;
                            if (hashSet.isEmpty()) {
                                hashSet.add(EdmConstants.NEW_EMAIL_PKG_NAME);
                            }
                            if (hashSet.contains(str)) {
                                i2 = 4;
                            }
                            if (z) {
                            }
                            return i2;
                        }
                        boolean z2 = bundle.getBoolean("com.samsung.android.notification.blockable", false);
                        hashMap.put(str, Boolean.valueOf(z2));
                        if (z2) {
                            i2 = 4;
                        }
                        if (z) {
                            Log.d("SecNotificationBlockManager", "checkSystemAppAndMetaData:" + str + ":" + i2);
                        }
                        return i2;
                    }
                }
            }
            return 4;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if (java.util.Arrays.stream(r0).noneMatch(new com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticLambda0()) != false) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0090 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isBlockablePackage(Context context, String str) {
        int permissionFlags;
        boolean z;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 4160);
            if (packageInfo != null && packageInfo.applicationInfo.targetSdkVersion > 32) {
                String[] strArr = packageInfo.requestedPermissions;
                if (strArr != null) {
                }
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (checkConfigCSC(context, str, null) == 2) {
            return false;
        }
        IPermissionManager permissionManager = AppGlobals.getPermissionManager();
        if (permissionManager == null) {
            Log.e("SecNotificationBlockManager", "AppGlobals.getPermissionManager() is null");
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    permissionFlags = permissionManager.getPermissionFlags(str, "android.permission.POST_NOTIFICATIONS", UserHandle.getUserId(Binder.getCallingUid()));
                } catch (RemoteException e2) {
                    Log.d("SecNotificationBlockManager", "Could not reach system server :" + e2);
                }
                if ((permissionFlags & 16) != 0 || (permissionFlags & 4) != 0) {
                    Log.d("SecNotificationBlockManager", "FLAG_PERMISSION_SYSTEM_FIXED pkg :" + str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = true;
                    if (!z) {
                        return false;
                    }
                    int checkSystemAppAndMetaData = checkSystemAppAndMetaData(context, str);
                    Log.d("SecNotificationBlockManager", "isBlockablePackage pkg :" + str + " , result = " + checkSystemAppAndMetaData);
                    return checkSystemAppAndMetaData != 2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        z = false;
        if (!z) {
        }
    }
}
