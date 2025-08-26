package com.android.settingslib;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EdmConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SecNotificationBlockManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final HashSet mConfigCSCSet = new HashSet();
    public static final HashMap mMetaDataMap = new HashMap();
    public static final HashSet mExceptableSystemAppSet = new HashSet();

    public static int checkConfigCSC(Context context, String str, NotificationChannel notificationChannel) {
        HashSet hashSet = mConfigCSCSet;
        boolean zIsEmpty = hashSet.isEmpty();
        boolean z = DEBUG;
        if (zIsEmpty) {
            Collections.addAll(hashSet, context.getResources().getStringArray(17236274));
            Collections.addAll(hashSet, context.getResources().getStringArray(17236324));
            String string = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigBlockNotiAppList");
            if (string != null && string.length() > 0) {
                for (String str2 : string.split(",")) {
                    if (str2 != null) {
                        if (z) {
                            Log.d("SecNotificationBlockManager", "initConfigCSCSet:CSC:".concat(str2));
                        }
                        mConfigCSCSet.add(str2);
                    }
                }
            }
        }
        HashSet hashSet2 = mConfigCSCSet;
        if (hashSet2.contains(str)) {
            if (z) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("checkConfigCSC:", str, "SecNotificationBlockManager");
            }
            return 2;
        }
        if (notificationChannel != null) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ":");
            sbM.append(notificationChannel.getId());
            if (!hashSet2.contains(sbM.toString())) {
                return 4;
            }
            if (z) {
                StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("checkConfigCSC with channel :", str, ":");
                sbM2.append(notificationChannel.getId());
                Log.d("SecNotificationBlockManager", sbM2.toString());
            }
            return 2;
        }
        Iterator it = hashSet2.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (!TextUtils.isEmpty(str3)) {
                if (str3.startsWith(str + ":")) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("checkConfigCSC :", str, "SecNotificationBlockManager");
                    return 2;
                }
            }
        }
        return 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        r6 = r0.getPackageInfo(r3, 64);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
    
        r3.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int checkSystemAppAndMetaData(Context context, String str) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
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
                    boolean zIsSystemPackage = Utils.isSystemPackage(context.getResources(), packageManager, packageInfo);
                    boolean z = DEBUG;
                    if (!zIsSystemPackage) {
                        if (!z) {
                            return 1;
                        }
                        Log.d("SecNotificationBlockManager", "checkSystemAppAndMetaData:" + str + ":nonSystemPackage");
                        return 1;
                    }
                    HashMap map = mMetaDataMap;
                    int i2 = 2;
                    if (!map.containsKey(str)) {
                        Bundle bundle = applicationInfo.metaData;
                        if (bundle != null) {
                            boolean z2 = bundle.getBoolean("com.samsung.android.notification.blockable", false);
                            map.put(str, Boolean.valueOf(z2));
                            if (z2) {
                                i2 = 4;
                            }
                        } else {
                            HashSet hashSet = mExceptableSystemAppSet;
                            if (hashSet.isEmpty()) {
                                hashSet.add(EdmConstants.NEW_EMAIL_PKG_NAME);
                            }
                            if (hashSet.contains(str)) {
                                i2 = 4;
                            }
                        }
                    } else if (((Boolean) map.get(str)).booleanValue()) {
                        i2 = 4;
                    }
                    if (z) {
                        Log.d("SecNotificationBlockManager", "checkSystemAppAndMetaData:" + str + ":" + i2);
                    }
                    return i2;
                }
            }
            return 4;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if (java.util.Arrays.stream(r0).noneMatch(new com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticLambda1()) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isBlockablePackage(Context context, String str) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
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
        PackageManager packageManager = context.getPackageManager();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int permissionFlags = packageManager.getPermissionFlags("android.permission.POST_NOTIFICATIONS", str, UserHandle.getUserHandleForUid(Binder.getCallingUid()));
            if ((permissionFlags & 16) == 0 && (permissionFlags & 4) == 0) {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                z = false;
            } else {
                Log.d("SecNotificationBlockManager", "FLAG_PERMISSION_SYSTEM_FIXED pkg :" + str);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                z = true;
            }
            if (z) {
                return false;
            }
            int iCheckSystemAppAndMetaData = checkSystemAppAndMetaData(context, str);
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(iCheckSystemAppAndMetaData, "isBlockablePackage pkg :", str, " , result = ", "SecNotificationBlockManager");
            return iCheckSystemAppAndMetaData != 2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }
}
