package com.android.settingslib;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecNotificationBlockManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final HashSet mConfigCSCSet = new HashSet();
    public static final HashMap mMetaDataMap = new HashMap();
    public static final HashSet mExceptableSystemAppSet = new HashSet();

    public static int checkConfigCSC(Context context, String str, NotificationChannel notificationChannel) {
        HashSet hashSet = mConfigCSCSet;
        boolean isEmpty = hashSet.isEmpty();
        boolean z = DEBUG;
        if (isEmpty) {
            Collections.addAll(hashSet, context.getResources().getStringArray(17236273));
            Collections.addAll(hashSet, context.getResources().getStringArray(17236323));
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ":");
            m.append(notificationChannel.getId());
            if (!hashSet2.contains(m.toString())) {
                return 4;
            }
            if (z) {
                StringBuilder m2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("checkConfigCSC with channel :", str, ":");
                m2.append(notificationChannel.getId());
                Log.d("SecNotificationBlockManager", m2.toString());
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0029, code lost:
    
        r6 = r0.getPackageInfo(r3, 64);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
    
        r3.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a1, code lost:
    
        if (r9.contains(r10) != false) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a6 A[Catch: NameNotFoundException -> 0x0062, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0062, blocks: (B:3:0x0007, B:5:0x000d, B:9:0x001c, B:11:0x001f, B:20:0x002f, B:13:0x0033, B:23:0x0038, B:27:0x004a, B:31:0x0066, B:33:0x006f, B:38:0x00a6, B:40:0x007c, B:42:0x0080, B:45:0x0090, B:47:0x0098, B:48:0x009d, B:17:0x0029), top: B:2:0x0007, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int checkSystemAppAndMetaData(android.content.Context r9, java.lang.String r10) {
        /*
            android.content.pm.PackageManager r0 = r9.getPackageManager()
            r1 = 128(0x80, float:1.8E-43)
            r2 = 4
            android.content.pm.ApplicationInfo r1 = r0.getApplicationInfo(r10, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r1 == 0) goto Lc1
            java.lang.String r3 = r1.packageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            int r4 = r1.uid     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String[] r4 = r0.getPackagesForUid(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r5 = 0
            r6 = 0
            if (r4 == 0) goto L36
            if (r3 == 0) goto L36
            r7 = r5
        L1c:
            int r8 = r4.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r7 >= r8) goto L36
            r8 = r4[r7]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            boolean r8 = r3.equals(r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r8 == 0) goto L33
            r4 = 64
            android.content.pm.PackageInfo r6 = r0.getPackageInfo(r3, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2e
            goto L36
        L2e:
            r3 = move-exception
            r3.printStackTrace()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            goto L36
        L33:
            int r7 = r7 + 1
            goto L1c
        L36:
            if (r6 == 0) goto Lc1
            android.content.res.Resources r9 = r9.getResources()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            boolean r9 = com.android.settingslib.Utils.isSystemPackage(r9, r0, r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r0 = "checkSystemAppAndMetaData:"
            java.lang.String r3 = "SecNotificationBlockManager"
            boolean r4 = com.android.settingslib.SecNotificationBlockManager.DEBUG
            if (r9 != 0) goto L66
            if (r4 == 0) goto L64
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.<init>()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.append(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.append(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r10 = ":nonSystemPackage"
            r9.append(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r9 = r9.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            android.util.Log.d(r3, r9)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            goto L64
        L62:
            r9 = move-exception
            goto Lc2
        L64:
            r9 = 1
            return r9
        L66:
            java.util.HashMap r9 = com.android.settingslib.SecNotificationBlockManager.mMetaDataMap     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            boolean r6 = r9.containsKey(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r7 = 2
            if (r6 == 0) goto L7c
            java.lang.Object r9 = r9.get(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            boolean r9 = r9.booleanValue()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r9 == 0) goto La4
            goto La3
        L7c:
            android.os.Bundle r1 = r1.metaData     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r1 == 0) goto L90
            java.lang.String r6 = "com.samsung.android.notification.blockable"
            boolean r1 = r1.getBoolean(r6, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.put(r10, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r1 == 0) goto La4
            goto La3
        L90:
            java.util.HashSet r9 = com.android.settingslib.SecNotificationBlockManager.mExceptableSystemAppSet     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            boolean r1 = r9.isEmpty()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r1 == 0) goto L9d
            java.lang.String r1 = "com.samsung.android.email.provider"
            r9.add(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
        L9d:
            boolean r9 = r9.contains(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            if (r9 == 0) goto La4
        La3:
            r7 = r2
        La4:
            if (r4 == 0) goto Lc0
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.<init>()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.append(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.append(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r10 = ":"
            r9.append(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r9.append(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r9 = r9.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            android.util.Log.d(r3, r9)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
        Lc0:
            return r7
        Lc1:
            return r2
        Lc2:
            r9.printStackTrace()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.SecNotificationBlockManager.checkSystemAppAndMetaData(android.content.Context, java.lang.String):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if (java.util.Arrays.stream(r0).noneMatch(new com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticLambda1()) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isBlockablePackage(android.content.Context r9, java.lang.String r10) {
        /*
            android.content.pm.PackageManager r0 = r9.getPackageManager()
            r1 = 4160(0x1040, float:5.83E-42)
            r2 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r10, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            if (r0 == 0) goto L2f
            android.content.pm.ApplicationInfo r1 = r0.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            int r1 = r1.targetSdkVersion     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            r3 = 32
            if (r1 <= r3) goto L2f
            java.lang.String[] r0 = r0.requestedPermissions     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            if (r0 == 0) goto L2b
            java.util.stream.Stream r0 = java.util.Arrays.stream(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticLambda1 r1 = new com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticLambda1     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            r1.<init>()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            boolean r0 = r0.noneMatch(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            if (r0 == 0) goto L2f
            goto L2b
        L29:
            r0 = move-exception
            goto L2c
        L2b:
            return r2
        L2c:
            r0.printStackTrace()
        L2f:
            r0 = 0
            int r0 = checkConfigCSC(r9, r10, r0)
            r1 = 2
            if (r0 != r1) goto L38
            return r2
        L38:
            java.lang.String r0 = "FLAG_PERMISSION_SYSTEM_FIXED pkg :"
            android.content.pm.PackageManager r3 = r9.getPackageManager()
            long r4 = android.os.Binder.clearCallingIdentity()
            int r6 = android.os.Binder.getCallingUid()
            android.os.UserHandle r6 = android.os.UserHandle.getUserHandleForUid(r6)
            java.lang.String r7 = "android.permission.POST_NOTIFICATIONS"
            int r3 = r3.getPermissionFlags(r7, r10, r6)     // Catch: java.lang.Throwable -> L87
            r6 = r3 & 16
            r7 = 1
            java.lang.String r8 = "SecNotificationBlockManager"
            if (r6 != 0) goto L61
            r3 = r3 & 4
            if (r3 == 0) goto L5c
            goto L61
        L5c:
            android.os.Binder.restoreCallingIdentity(r4)
            r0 = r2
            goto L74
        L61:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L87
            r3.append(r10)     // Catch: java.lang.Throwable -> L87
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L87
            android.util.Log.d(r8, r0)     // Catch: java.lang.Throwable -> L87
            android.os.Binder.restoreCallingIdentity(r4)
            r0 = r7
        L74:
            if (r0 == 0) goto L77
            return r2
        L77:
            int r9 = checkSystemAppAndMetaData(r9, r10)
            java.lang.String r0 = "isBlockablePackage pkg :"
            java.lang.String r3 = " , result = "
            com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0.m(r9, r0, r10, r3, r8)
            if (r9 != r1) goto L85
            goto L86
        L85:
            r2 = r7
        L86:
            return r2
        L87:
            r9 = move-exception
            android.os.Binder.restoreCallingIdentity(r4)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.SecNotificationBlockManager.isBlockablePackage(android.content.Context, java.lang.String):boolean");
    }
}
