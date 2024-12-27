package com.android.server.enterprise.lso;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.lockscreen.ILockscreenOverlay;
import com.samsung.android.knox.lockscreen.LSOAttributeSet;
import com.samsung.android.knox.lockscreen.LSOConstants;
import com.samsung.android.knox.lockscreen.LSOItemContainer;
import com.samsung.android.knox.lockscreen.LSOItemData;
import com.samsung.android.knox.lockscreen.LSOItemImage;
import com.samsung.android.knox.lockscreen.LSOUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LSOService extends ILockscreenOverlay.Stub implements EnterpriseServiceCallback {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public LSOAttributeSet lsoPref;
    public final Context mContext;
    public final LSOItemData[] mItemData;
    public int mOverlayAdminUid;
    public int mWallpaperAdminUid;
    public final Point screenDimesions;
    public final LSOStorageProvider storageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.android.server.enterprise.lso.LSOService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                            .equals(intent.getAction())) {
                        int intExtra =
                                intent.getIntExtra(
                                        "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                        0);
                        LSOService lSOService = LSOService.this;
                        lSOService.setLockscreenInvisibleOverlaySystemUI(
                                intExtra, lSOService.isConfigured(null, 1));
                        lSOService.setLockscreenWallpaperSystemUI(
                                intExtra, lSOService.isConfigured(null, 2));
                    }
                }
            };

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a5, code lost:

       if (r8 != null) goto L24;
    */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0105 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0105 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.enterprise.lso.LSOService$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LSOService(android.content.Context r12) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.lso.LSOService.<init>(android.content.Context):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String copyFileFromParcel(
            android.os.ParcelFileDescriptor r8, java.lang.String r9) {
        /*
            java.lang.String r0 = "failed to close outputstream"
            java.lang.String r1 = "failed to close inputstream"
            java.lang.String r2 = "LSOService"
            java.lang.String r3 = "/data/system/enterprise"
            boolean r3 = com.samsung.android.knox.lockscreen.LSOUtils.mkDir(r3)
            r4 = 0
            if (r3 != 0) goto L12
            return r4
        L12:
            java.lang.String r3 = "/data/system/enterprise/temp"
            boolean r3 = com.samsung.android.knox.lockscreen.LSOUtils.mkDir(r3)
            if (r3 != 0) goto L1b
            return r4
        L1b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r5 = "/data/system/enterprise/temp/"
            r3.<init>(r5)
            java.io.File r5 = new java.io.File
            r5.<init>(r9)
            java.lang.String r9 = r5.getName()
            r3.append(r9)
            java.lang.String r9 = r3.toString()
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6f
            java.io.FileDescriptor r8 = r8.getFileDescriptor()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6f
            r5.<init>(r8)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6f
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
        L44:
            int r6 = r5.read(r3)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L52
            if (r6 <= 0) goto L54
            r7 = 0
            r8.write(r3, r7, r6)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L52
            goto L44
        L4f:
            r9 = move-exception
        L50:
            r4 = r5
            goto L8b
        L52:
            r9 = move-exception
            goto L72
        L54:
            r8.flush()     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L52
            r5.close()     // Catch: java.io.IOException -> L5b
            goto L5e
        L5b:
            android.util.Log.e(r2, r1)
        L5e:
            r8.close()     // Catch: java.io.IOException -> L62
            goto L65
        L62:
            android.util.Log.e(r2, r0)
        L65:
            return r9
        L66:
            r9 = move-exception
            r8 = r4
            goto L50
        L69:
            r9 = move-exception
            r8 = r4
            goto L72
        L6c:
            r9 = move-exception
            r8 = r4
            goto L8b
        L6f:
            r9 = move-exception
            r8 = r4
            r5 = r8
        L72:
            java.lang.String r3 = "copyFileFromParcel() : failed to copy image from parcel "
            android.util.Log.e(r2, r3, r9)     // Catch: java.lang.Throwable -> L4f
            if (r5 == 0) goto L81
            r5.close()     // Catch: java.io.IOException -> L7e
            goto L81
        L7e:
            android.util.Log.e(r2, r1)
        L81:
            if (r8 == 0) goto L8a
            r8.close()     // Catch: java.io.IOException -> L87
            goto L8a
        L87:
            android.util.Log.e(r2, r0)
        L8a:
            return r4
        L8b:
            if (r4 == 0) goto L94
            r4.close()     // Catch: java.io.IOException -> L91
            goto L94
        L91:
            android.util.Log.e(r2, r1)
        L94:
            if (r8 == 0) goto L9d
            r8.close()     // Catch: java.io.IOException -> L9a
            goto L9d
        L9a:
            android.util.Log.e(r2, r0)
        L9d:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.lso.LSOService.copyFileFromParcel(android.os.ParcelFileDescriptor,"
                    + " java.lang.String):java.lang.String");
    }

    public static void deleteWallpaperFiles() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                LSOUtils.deleteFile("/data/system/enterprise/lso/lockscreen_wallpaper.jpg");
                LSOUtils.deleteFile("/data/system/enterprise/lso/lockscreen_wallpaper_ripple.jpg");
            } catch (Exception e) {
                Log.e("LSOService", "deleteWallpaperFiles() : failed but ignore this error");
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|(2:2|3)|(2:26|(5:29|(3:18|19|(1:22))|(1:13)|14|15))|7|(0)|18|19|(0)|22|(1:13)(1:13)|14|15|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x002b, code lost:

       r6 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x002f, code lost:

       com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r6, "canConfigure() Unhandled exception.", "LSOService");
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0037 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean canConfigure(com.samsung.android.knox.ContextInfo r7, int r8) {
        /*
            r6 = this;
            java.lang.String r0 = "LSOService"
            r1 = 2
            r2 = 1
            r3 = -1
            r4 = 0
            com.samsung.android.knox.ContextInfo r7 = r6.enforceOwnerOnlyPermission(r7)     // Catch: java.lang.Exception -> L2d
            int r7 = r7.mCallerUid     // Catch: java.lang.Exception -> L2d
            if (r3 == r8) goto L12
            if (r8 == 0) goto L12
            if (r2 != r8) goto L19
        L12:
            int r5 = r6.mOverlayAdminUid     // Catch: java.lang.Exception -> L2d
            if (r5 == r3) goto L1b
            if (r5 != r7) goto L19
            goto L1b
        L19:
            r5 = r4
            goto L1c
        L1b:
            r5 = r2
        L1c:
            if (r3 == r8) goto L22
            if (r8 == 0) goto L22
            if (r1 != r8) goto L35
        L22:
            int r6 = r6.mWallpaperAdminUid     // Catch: java.lang.Exception -> L2b
            if (r6 == r3) goto L28
            if (r6 != r7) goto L35
        L28:
            int r5 = r5 + 1
            goto L35
        L2b:
            r6 = move-exception
            goto L2f
        L2d:
            r6 = move-exception
            r5 = r4
        L2f:
            java.lang.String r7 = "canConfigure() Unhandled exception."
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r6, r7, r0)
        L35:
            if (r3 != r8) goto L3c
            if (r5 < r1) goto L3a
            goto L3e
        L3a:
            r2 = r4
            goto L3e
        L3c:
            if (r5 <= 0) goto L3a
        L3e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "canConfigure("
            r6.<init>(r7)
            java.lang.String r7 = com.samsung.android.knox.lockscreen.LSOConstants.getLSOFeatureName(r8)
            r6.append(r7)
            java.lang.String r7 = ") - "
            r6.append(r7)
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r0, r6)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.lso.LSOService.canConfigure(com.samsung.android.knox.ContextInfo,"
                    + " int):boolean");
    }

    public final synchronized String copyFile(int i, String str, String str2) {
        return LSOUtils.copyFile(
                str,
                str2 + "_" + String.valueOf(i) + String.valueOf(0) + "_" + new File(str).getName());
    }

    public final synchronized boolean copyFiles(int i, LSOItemData lSOItemData, int i2) {
        String str = "/data/system/enterprise/lso/" + i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                deleteFiles(i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            Log.e("LSOService", "copyFiles1() error occurs. ", e);
        }
        if (!LSOUtils.mkDir("/data/system/enterprise")) {
            return false;
        }
        if (!LSOUtils.mkDir("/data/system/enterprise/lso")) {
            return false;
        }
        if (!LSOUtils.mkDir(str)) {
            return false;
        }
        copyFiles(lSOItemData, str + "/" + i, 0);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = true;
        if (!z) {
            deleteFiles(i2);
        }
        Log.d("LSOService", "copyFiles1() " + LSOConstants.getLayerName(i2) + "result = " + z);
        return z;
    }

    public final synchronized boolean copyFiles(LSOItemData lSOItemData, String str, int i) {
        LSOAttributeSet attrs;
        boolean z = true;
        if (lSOItemData == null) {
            return true;
        }
        boolean z2 = false;
        try {
            byte type = lSOItemData.getType();
            if (type == 3) {
                LSOItemImage lSOItemImage = (LSOItemImage) lSOItemData;
                if (lSOItemImage.getImagePath() != null) {
                    String copyFileFromParcel =
                            copyFileFromParcel(
                                    lSOItemImage.getFileDescriptor(), lSOItemImage.getImagePath());
                    if (copyFileFromParcel == null) {
                        Log.e("LSOService", "copyFiles2 - LSO_ITEM_TYPE_IMAGE : file no created");
                        return false;
                    }
                    String copyFile = copyFile(i, copyFileFromParcel, str);
                    LSOUtils.deleteFile(copyFileFromParcel);
                    if (copyFile == null) {
                        z = false;
                    } else {
                        lSOItemImage.setImagePath(copyFile);
                    }
                }
            } else if (type == 4) {
                LSOItemContainer lSOItemContainer = (LSOItemContainer) lSOItemData;
                if (lSOItemContainer.getBGImagePath() != null) {
                    String copyFileFromParcel2 =
                            copyFileFromParcel(
                                    lSOItemContainer.getFileDescriptor(),
                                    lSOItemContainer.getBGImagePath());
                    if (copyFileFromParcel2 == null) {
                        Log.e(
                                "LSOService",
                                "copyFiles2 - LSO_ITEM_TYPE_CONTAINER : file no created");
                        return false;
                    }
                    String copyFile2 = copyFile(i, copyFileFromParcel2, str);
                    LSOUtils.deleteFile(copyFileFromParcel2);
                    if (copyFile2 != null) {
                        lSOItemContainer.setBGImage(copyFile2);
                    }
                    z = false;
                }
                for (int i2 = 0; i2 < lSOItemContainer.getNumItems() && z; i2++) {
                    z = copyFiles(lSOItemContainer.getItem(i2), str, i + 1);
                }
            }
        } catch (Exception e) {
            Log.e("LSOService", "copyFiles2() - failed. ", e);
        }
        if (z && (attrs = lSOItemData.getAttrs()) != null && attrs.containsKey("android:src")) {
            String copyFileFromParcel3 =
                    copyFileFromParcel(
                            lSOItemData.getFileDescriptor(), attrs.getAsString("android:src"));
            if (copyFileFromParcel3 == null) {
                Log.e("LSOService", "copyFiles2 - attrSet ATTR_IMAGE_SRC : file no created");
                return false;
            }
            String copyFile3 = copyFile(i, copyFileFromParcel3, str);
            LSOUtils.deleteFile(copyFileFromParcel3);
            if (copyFile3 == null) {
                Log.d("LSOService", "copyFiles2() - " + str + ", ret =" + z2);
                return z2;
            }
            lSOItemData.setAttribute("android:src", copyFile3);
        }
        z2 = z;
        Log.d("LSOService", "copyFiles2() - " + str + ", ret =" + z2);
        return z2;
    }

    public final synchronized void deleteFiles(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (i == 0) {
                    for (int i2 = 1; i2 <= 3; i2++) {
                        LSOUtils.deleteRecursive(new File("/data/system/enterprise/lso/" + i2));
                    }
                } else {
                    LSOUtils.deleteRecursive(new File("/data/system/enterprise/lso/" + i));
                }
            } catch (Exception e) {
                Log.e(
                        "LSOService",
                        "deleteFiles() : failed layer - " + LSOConstants.getLayerName(i),
                        e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump LSOService");
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean isConfigured = isConfigured(null, 2);
        sb.append("LOCKSCREEN_WALLPAPER : " + isConfigured);
        sb.append(System.lineSeparator());
        if (isConfigured) {
            sb.append("  AdminUid: " + this.mWallpaperAdminUid);
            sb.append(System.lineSeparator());
        }
        boolean isConfigured2 = isConfigured(null, 1);
        sb.append("LOCKSCREEN_OVERLAY : " + isConfigured2);
        sb.append(System.lineSeparator());
        if (isConfigured2) {
            sb.append("  AdminUid: " + this.mOverlayAdminUid);
            for (int i = 1; i <= 3; i++) {
                LSOItemData data = getData(null, i);
                if (data != null) {
                    sb.append(System.lineSeparator());
                    sb.append(
                            "    Layer " + LSOConstants.getLayerName(i) + " : " + data.toString());
                }
            }
            sb.append(System.lineSeparator());
        }
        printWriter.write(sb.toString());
    }

    public final ContextInfo enforceOwnerOnlyPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(
                        Arrays.asList("com.samsung.android.knox.permission.KNOX_LOCKSCREEN")));
    }

    public final synchronized LSOItemData getData(ContextInfo contextInfo, int i) {
        try {
            Log.d("LSOService", "getData() " + LSOConstants.getLayerName(i));
            if (i < 1 || i > 3) {
                throw new InvalidParameterException("Invalid layer. Layer must be 1...3");
            }
            if (DEBUG) {
                Log.d("LSOService", "getData() - " + this.mItemData[i]);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mItemData[i];
    }

    public final LSOAttributeSet getPreferences(ContextInfo contextInfo) {
        if (DEBUG) {
            Log.d("LSOService", "getPreferences() - lsoPref = " + this.lsoPref);
        }
        return this.lsoPref;
    }

    public final boolean isConfigured(ContextInfo contextInfo, int i) {
        boolean z = true;
        int i2 = ((-1 == i || i == 0 || 1 == i) && this.mOverlayAdminUid != -1) ? 1 : 0;
        if ((-1 == i || i == 0 || 2 == i) && this.mWallpaperAdminUid != -1) {
            i2++;
        }
        if (-1 != i ? i2 <= 0 : i2 < 2) {
            z = false;
        }
        Log.d("LSOService", "isConfigured(" + LSOConstants.getLSOFeatureName(i) + ") - " + z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            setLockscreenInvisibleOverlaySystemUI(callingOrCurrentUserId, isConfigured(null, 1));
            setLockscreenWallpaperSystemUI(callingOrCurrentUserId, isConfigured(null, 2));
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        if (i == this.mOverlayAdminUid) {
            deleteFiles(0);
            this.storageProvider.resetOverlayData(0);
            this.mOverlayAdminUid = -1;
            this.lsoPref = null;
            for (int i2 = 1; i2 <= 3; i2++) {
                this.mItemData[i2] = null;
            }
        }
        if (i == this.mWallpaperAdminUid) {
            deleteWallpaperFiles();
            this.storageProvider.resetWallpaperData();
            this.mWallpaperAdminUid = -1;
        }
    }

    public final void resetData(ContextInfo contextInfo, int i) {
        Log.d("LSOService", "resetData() - " + LSOConstants.getLayerName(i));
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        if (i < 0 || i > 3) {
            throw new InvalidParameterException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Invalid layer. Layer must be 0...3 but requseted "));
        }
        if (!canConfigure(enforceOwnerOnlyPermission, 1)) {
            Log.e("LSOService", "resetData() : Not allowed to reset Overlay");
            return;
        }
        if (enforceOwnerOnlyPermission.mCallerUid != this.mOverlayAdminUid) {
            Log.e(
                    "LSOService",
                    "resetData() : requested uid is diffren with present admin = "
                            + this.mOverlayAdminUid
                            + " but "
                            + enforceOwnerOnlyPermission.mCallerUid);
            return;
        }
        deleteFiles(i);
        this.storageProvider.resetOverlayData(i);
        int adminUid = this.storageProvider.getAdminUid("LOCKSCREEN_OVERLAY");
        this.mOverlayAdminUid = adminUid;
        if (adminUid == -1) {
            this.lsoPref = null;
        }
        if (i == 0) {
            for (int i2 = 1; i2 <= 3; i2++) {
                this.mItemData[i2] = null;
            }
        } else {
            this.mItemData[i] = null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "LSOService",
                        String.format(
                                "Admin %d has reset banner settings.",
                                Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)),
                        callingOrCurrentUserId);
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
            sendConfigChangeNotification(callingOrCurrentUserId);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void resetWallpaper(ContextInfo contextInfo) {
        Log.d("LSOService", "resetWallpaper()");
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        if (!canConfigure(enforceOwnerOnlyPermission, 2)) {
            Log.e("LSOService", "Not allowed to reset Wallpaper");
            return;
        }
        if (enforceOwnerOnlyPermission.mCallerUid == this.mWallpaperAdminUid) {
            this.storageProvider.resetWallpaperData();
            deleteWallpaperFiles();
            this.mWallpaperAdminUid = -1;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "LSOService",
                        String.format(
                                "Admin %d has reset banner wallpaper.",
                                Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)),
                        callingOrCurrentUserId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                sendConfigChangeNotification(callingOrCurrentUserId);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void sendConfigChangeNotification(int i) {
        boolean isConfigured = isConfigured(null, 2);
        boolean isConfigured2 = isConfigured(null, 1);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.LSO_CONFIG_CHANGED_INTERNAL");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.KNOX_WALLPAPER_ENABLED_INTERNAL",
                        isConfigured2 ? 1 : 0);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null);
                Log.d("LSOService", "sendConfigChangeNotification() - done");
            } catch (Exception e) {
                Log.e("LSOService", "sendConfigChangeNotification() : failed to send intent.", e);
            }
            try {
                setLockscreenInvisibleOverlaySystemUI(i, isConfigured2);
                setLockscreenWallpaperSystemUI(i, isConfigured);
            } catch (Exception e2) {
                Log.e(
                        "LSOService",
                        "sendConfigChangeNotification() : failed to update system ui.",
                        e2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) {
        Log.d("LSOService", "setData() " + LSOConstants.getLayerName(i));
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        int i2 = enforceOwnerOnlyPermission.mCallerUid;
        try {
            if (i < 1 || i > 3) {
                throw new InvalidParameterException("Invalid layer. Layer must be 1...3");
            }
            if (!canConfigure(enforceOwnerOnlyPermission, 1)) {
                Log.i(
                        "LSOService",
                        "Lockscreen is configured by another admin. Overwrite not allowed.");
                return -1;
            }
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService("phone");
            if (i == 3 && (telephonyManager == null || !telephonyManager.isVoiceCapable())) {
                Log.i("LSOService", "setData() failed because telephony is not supported.");
                return -3;
            }
            if (!copyFiles(i2, lSOItemData, i)) {
                return -4;
            }
            if (!this.storageProvider.setOverlayData(i2, lSOItemData, i, this.lsoPref)) {
                int adminUid = this.storageProvider.getAdminUid("LOCKSCREEN_OVERLAY");
                this.mOverlayAdminUid = adminUid;
                this.mItemData[i] = null;
                if (adminUid == -1) {
                    this.lsoPref = null;
                }
                deleteFiles(i);
                return -4;
            }
            this.mOverlayAdminUid = i2;
            this.mItemData[i] = lSOItemData;
            lSOItemData.closeFileDescriptor();
            sendConfigChangeNotification(callingOrCurrentUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "LSOService",
                        String.format(
                                "Admin %d has changed banner settings.",
                                Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)),
                        callingOrCurrentUserId);
                return 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            lSOItemData.closeFileDescriptor();
        }
    }

    public final void setLockscreenInvisibleOverlaySystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext)
                        .setLockscreenInvisibleOverlayAsUser(i, z);
            } catch (Exception e) {
                Log.e("LSOService", "setLockscreenInvisibleOverlaySystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setLockscreenWallpaperSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setLockscreenWallpaperAsUser(i, z);
            } catch (Exception e) {
                Log.e("LSOService", "setLockscreenWallpaperSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) {
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int i = this.mOverlayAdminUid;
        if (i == -1) {
            Log.i("LSOService", "setPreferences() : There is no configured data from admin. ");
            return -6;
        }
        if (i != enforceOwnerOnlyPermission.mCallerUid) {
            Log.i(
                    "LSOService",
                    "setPreferences() : Lockscreen is configured by another admin. Overwrite not"
                        + " allowed.");
            return -1;
        }
        LSOItemData[] lSOItemDataArr = this.mItemData;
        if (lSOItemDataArr[1] == null && lSOItemDataArr[2] == null) {
            Log.i("LSOService", "setPreferences() : layer doesn't configure so cannot set pref.");
            return -6;
        }
        LSOStorageProvider lSOStorageProvider = this.storageProvider;
        lSOStorageProvider.getClass();
        ContentValues contentValues = new ContentValues();
        if (lSOAttributeSet != null) {
            contentValues.put("accountObject", lSOAttributeSet.toByteArray());
        } else {
            contentValues.put("accountObject", (byte[]) null);
        }
        Log.d("LSOStorageProvider", "Insert/Update record: ADMIN_REF");
        SQLiteDatabase writableDatabase = lSOStorageProvider.mEdmDbHelper.getWritableDatabase();
        int update =
                writableDatabase.update(
                        "ADMIN_REF",
                        contentValues,
                        "policyName=?",
                        new String[] {"LOCKSCREEN_OVERLAY"});
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                update, "Number of rows updated: ", "LSOStorageProvider");
        if (update <= 0) {
            Log.e(
                    "LSOStorageProvider",
                    "ADMIN_REF: Failed to insert record - " + contentValues.toString());
            writableDatabase.close();
        }
        boolean z = update > 0;
        sendConfigChangeNotification(Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission));
        if (z) {
            this.lsoPref = lSOAttributeSet;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "LSOService",
                        String.format(
                                "Admin %d has changed banner settings.",
                                Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)),
                        UserHandle.getUserId(enforceOwnerOnlyPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z ? 0 : -4;
    }

    public final int setWallpaper(
            ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) {
        boolean z;
        DualAppManagerService$$ExternalSyntheticOutline0.m("setWallpaper(", str, ")", "LSOService");
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        int i = enforceOwnerOnlyPermission.mCallerUid;
        if (!canConfigure(enforceOwnerOnlyPermission, 2)) {
            Log.d(
                    "LSOService",
                    "setWallpaper() : Lockscreen is configured by another admin. Overwrite not"
                        + " allowed.");
            return -1;
        }
        if (str == null || parcelFileDescriptor == null) {
            Log.d(
                    "LSOService",
                    "setWallpaper() : imageFilePath or image is null, please check path");
            return -4;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String copyFileFromParcel = copyFileFromParcel(parcelFileDescriptor, str);
        try {
            if (copyFileFromParcel == null) {
                Log.e("LSOService", "file not created");
                return -4;
            }
            try {
            } catch (Exception e) {
                Log.d("LSOService", "setWallpaper() : error occurs", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            if (!new File("/data/data/com.sec.android.gallery3d").exists()) {
                Log.e("LSOService", "/data/data/com.sec.android.gallery3d does not exists");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -3;
            }
            if (!LSOUtils.mkDir("/data/system/enterprise")) {
                return -4;
            }
            if (!LSOUtils.mkDir("/data/system/enterprise/lso")) {
                return -4;
            }
            this.storageProvider.resetWallpaperData();
            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
            z =
                    LSOUtils.convertImageFormat(
                            copyFileFromParcel,
                            compressFormat,
                            "/data/system/enterprise/lso/lockscreen_wallpaper.jpg",
                            this.screenDimesions);
            if (z) {
                Log.d("LSOService", "setWallpaper() : Create Ripple image");
                z =
                        LSOUtils.createRippleImage(
                                "/data/system/enterprise/lso/lockscreen_wallpaper.jpg",
                                compressFormat,
                                "/data/system/enterprise/lso/lockscreen_wallpaper_ripple.jpg");
            }
            if (!z) {
                Log.e("LSOService", "setWallpaper() : Error in copying file");
                LSOUtils.deleteFile(copyFileFromParcel);
                return -4;
            }
            LSOStorageProvider lSOStorageProvider = this.storageProvider;
            long j = i;
            lSOStorageProvider.getClass();
            if (j == -1) {
                throw new InvalidParameterException("Parameter cannot be null");
            }
            lSOStorageProvider.resetWallpaperData();
            ContentValues contentValues = new ContentValues();
            contentValues.put("policyName", "LOCKSCREEN_WALLPAPER");
            contentValues.put("adminUid", Long.valueOf(j));
            boolean z2 = -1 != lSOStorageProvider.insertRecord("ADMIN_REF", contentValues);
            if (!z2) {
                lSOStorageProvider.resetWallpaperData();
            }
            if (!z2) {
                Log.e("LSOService", "setWallpaper() : Failed to set wallpaper data. ");
                this.mWallpaperAdminUid = -1;
                LSOUtils.deleteFile(copyFileFromParcel);
                deleteWallpaperFiles();
                return -4;
            }
            this.mWallpaperAdminUid = i;
            sendConfigChangeNotification(callingOrCurrentUserId);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(
                        5,
                        1,
                        true,
                        Process.myPid(),
                        "LSOService",
                        String.format(
                                "Admin %d has changed banner wallpaper to file %s",
                                Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid), str),
                        callingOrCurrentUserId);
            } catch (Exception unused) {
            } catch (Throwable th) {
                throw th;
            }
            LSOUtils.deleteFile(copyFileFromParcel);
            return 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
