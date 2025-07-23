package com.samsung.android.wallpaperbackup;

import android.app.WallpaperManager;
import android.app.slice.SliceItem;
import android.content.APKContents;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.AppSearchShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WallpaperExtraBundleHelper;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaperbackup.BnRFileHelper;
import com.samsung.android.wallpaperbackup.WallpaperUser;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class WallpaperBackupRestoreManager {
    private static boolean DEBUG = true;
    private static final String RESTORE_TYPE_COVER = "RESTORE_TYPE_COVER";
    private static final String RESTORE_TYPE_MAIN = "RESTORE_TYPE_MAIN";
    private static final String TAG = "WallpaperBackupRestoreManager";
    private int[] mModeFlagSet = {4, 16, 8, 32};

    private enum ResultCode {
        INVALID_VALUE(-1),
        RESULT_SUCCESS(0),
        RESULT_FAIL(1);

        private int code;

        ResultCode(int i) {
            this.code = i;
        }

        public int getCode() {
            return this.code;
        }
    }

    public void startBackupWallpaper(Context context, String str, String str2) {
        startBackupWallpaper(context, 1, str, str2, 0, "", "");
    }

    public void startBackupWallpaper(Context context, int i, String str, String str2, int i2, String str3, String str4) {
        startBackupWallpaper(context, "", i, str, str2, i2, str3, str4);
    }

    public void startBackupWallpaper(Context context, String str, int i, String str2, String str3, int i2, String str4, String str5) {
        String str6;
        String str7;
        String str8 = str2;
        String str9 = TAG;
        Slog.d(str9, "startBackupWallpaper which = " + Integer.toHexString(i) + " action= " + str + " basePath=" + str8 + " source=" + str3);
        if (TextUtils.isEmpty(str)) {
            if ((i & 1) != 0) {
                str7 = BnRConstants.RESPONSE_BACKUP_WALLPAPER;
            } else {
                str7 = BnRConstants.RESPONSE_BACKUP_LOCKSCREEN;
            }
            str6 = str7;
        } else {
            str6 = str;
        }
        if (!str8.endsWith(File.separator)) {
            str8 = str8 + File.separator;
        }
        String str10 = str8;
        BnRFileHelper.ErrorCode checkSaveAvailable = BnRFileHelper.checkSaveAvailable(str10);
        if (!checkSaveAvailable.equals(BnRFileHelper.ErrorCode.ERROR_NONE)) {
            HashMap hashMap = new HashMap();
            hashMap.put(Integer.valueOf(i), ResultCode.INVALID_VALUE);
            Slog.d(str9, "startBackupWallpaper is return because precondition fail");
            sendResponse(context, i, str6, ResultCode.RESULT_FAIL, checkSaveAvailable, BnRFileHelper.REQ_MINIMUM_SIZE, str3, str4, hashMap, null);
            return;
        }
        pushBackupFile(context, str6, i, str10, i2, str5, str4, str3);
    }

    private void pushBackupFile(Context context, String str, int i, String str2, int i2, String str3, String str4, String str5) {
        ArrayList arrayList = new ArrayList();
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        int i3 = 0;
        while (true) {
            int[] iArr = this.mModeFlagSet;
            if (i3 < iArr.length) {
                if (isSupportedScreen(iArr[i3], i)) {
                    if (BnRConstants.BNR_SOURCE_SCLOUD.equals(str5) && wallpaperManager.isSystemAndLockPaired(this.mModeFlagSet[i3]) && i == 2 && wallpaperManager.semGetWallpaperType(i) == 7) {
                        Log.i(TAG, "pushBackupFile() : home and lock layered wallpaper is paired for mode " + this.mModeFlagSet[i3] + ", backup home wallpaper");
                        WallpaperBNRHelper wallpaperBNRHelper = new WallpaperBNRHelper(context, wallpaperManager, str, 1 | this.mModeFlagSet[i3], str2, i2, str3, str4, str5);
                        wallpaperBNRHelper.setWhich(this.mModeFlagSet[i3] | i);
                        arrayList.add(wallpaperBNRHelper);
                    } else {
                        arrayList.add(new WallpaperBNRHelper(context, wallpaperManager, str, i | this.mModeFlagSet[i3], str2, i2, str3, str4, str5));
                    }
                }
                i3++;
            } else {
                new WallpaperBackupAsyncTask().execute(arrayList);
                return;
            }
        }
    }

    public void startRestoreWallpaper(Context context, String str, String str2) {
        startRestoreWallpaper(context, 1, str, str2, 0, "", null);
    }

    public void startRestoreWallpaper(Context context, int i, String str, String str2, int i2, String str3, String str4) {
        startRestoreWallpaper(context, "", i, str, str2, i2, str3, str4);
    }

    public void startRestoreWallpaper(Context context, String str, int i, String str2, String str3, int i2, String str4, String str5) {
        String str6;
        String str7;
        String str8 = str2;
        String str9 = TAG;
        Slog.d(str9, "startRestoreWallpaper: which = " + Integer.toHexString(i) + " action = " + str + " basePath = " + str8 + " source = " + str3 + " securityLevel = " + i2 + " restoreScreen = " + str5);
        if (TextUtils.isEmpty(str)) {
            if ((i & 1) != 0) {
                str7 = BnRConstants.RESPONSE_RESTORE_WALLPAPER;
            } else {
                str7 = BnRConstants.RESPONSE_RESTORE_LOCKSCREEN;
            }
            str6 = str7;
        } else {
            str6 = str;
        }
        if (!str8.endsWith(File.separator)) {
            str8 = str8 + File.separator;
        }
        String str10 = str8;
        BnRFileHelper.ErrorCode checkSaveAvailable = BnRFileHelper.checkSaveAvailable(str10);
        if (!checkSaveAvailable.equals(BnRFileHelper.ErrorCode.ERROR_NONE)) {
            ResultCode resultCode = ResultCode.INVALID_VALUE;
            HashMap hashMap = new HashMap();
            hashMap.put(Integer.valueOf(i), resultCode);
            Slog.d(str9, "startRestoreWallpaper is return because precondition fail");
            sendResponse(context, i, str6, ResultCode.RESULT_FAIL, checkSaveAvailable, BnRFileHelper.REQ_MINIMUM_SIZE, str3, null, hashMap, null);
            return;
        }
        pushRestoreFile(context, str6, i, str10, i2, str4, str3, str5);
    }

    private void pushRestoreFile(Context context, String str, int i, String str2, int i2, String str3, String str4, String str5) {
        ArrayList<WallpaperBNRHelper> arrayList = new ArrayList<>();
        Context context2 = context;
        WallpaperManager wallpaperManager = (WallpaperManager) context2.getSystemService("wallpaper");
        int i3 = 0;
        while (true) {
            int[] iArr = this.mModeFlagSet;
            if (i3 >= iArr.length) {
                break;
            }
            if (isSupportedScreen(iArr[i3], i)) {
                WallpaperBNRHelper wallpaperBNRHelper = new WallpaperBNRHelper(context2, wallpaperManager, str, i | this.mModeFlagSet[i3], str2, i2, str3, "", str4);
                if (handleDifferentTypeRestore(wallpaperBNRHelper, this.mModeFlagSet[i3], i, str5)) {
                    arrayList.add(wallpaperBNRHelper);
                }
            }
            i3++;
            context2 = context;
        }
        if (arrayList.size() == 0) {
            Log.d(TAG, "pushRestoreFile: Nothing to restore.");
            WallpaperBNRHelper wallpaperBNRHelper2 = new WallpaperBNRHelper(context, wallpaperManager, str, i, str2, i2, str3, "", str4);
            wallpaperBNRHelper2.setResultCode(ResultCode.RESULT_FAIL);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(wallpaperBNRHelper2);
            response(arrayList2);
            return;
        }
        if (!isRestorableDeviceType(arrayList)) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(arrayList.get(0));
            response(arrayList3);
            return;
        }
        new WallpaperRestoreAsyncTask().execute(arrayList);
    }

    private boolean handleDifferentTypeRestore(WallpaperBNRHelper wallpaperBNRHelper, int i, int i2, String str) {
        if (Rune.isFolder() && !"folder".equals(wallpaperBNRHelper.getDeviceType())) {
            if (i == 16) {
                Log.i(TAG, "skip restoring sub display of phone models");
                return false;
            }
            if (i == 4) {
                wallpaperBNRHelper.setWhich(i2 | 16);
                Log.i(TAG, "restoring main display of phone model to sub display of fold model");
                return true;
            }
        }
        if (!Rune.isFolder() && !Rune.isTablet()) {
            if ("folder".equals(wallpaperBNRHelper.getDeviceType())) {
                if (i == 16) {
                    if (!RESTORE_TYPE_COVER.equals(str)) {
                        return false;
                    }
                    wallpaperBNRHelper.setWhich(i2 | 4);
                    Log.i(TAG, "restoring sub display of fold model to phone");
                    return true;
                }
                if (i == 4) {
                    Log.i(TAG, "do not restore main display of fold model to phone");
                    return false;
                }
            } else if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                if (i == 16 && (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY ^ BnRConstants.COVER_TYPE_LARGE_SCREEN.equals(wallpaperBNRHelper.getCoverType()))) {
                    Log.i(TAG, "skip different size cover screen");
                    return false;
                }
            } else if (i == 16) {
                Log.i(TAG, "skip sub display for phones");
                return false;
            }
        }
        return true;
    }

    private boolean isSupportedScreen(int i, int i2) {
        if (i != 8) {
            if (i == 16) {
                if (Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE) {
                    return true;
                }
                if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
                    return false;
                }
                if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (i2 & 1) == 0) {
                    return false;
                }
            } else if (i == 32 && (!Rune.VIRTUAL_DISPLAY_WALLPAPER || (i2 & 1) == 0)) {
                return false;
            }
        } else if (!Rune.SUPPORT_DESKTOP_MODE) {
            return false;
        }
        return true;
    }

    private boolean isRestorableDeviceType(ArrayList<WallpaperBNRHelper> arrayList) {
        WallpaperBNRHelper wallpaperBNRHelper = arrayList.get(0);
        String deviceType = wallpaperBNRHelper.getDeviceType();
        if (TextUtils.isEmpty(deviceType)) {
            return true;
        }
        deviceType.hashCode();
        switch (deviceType) {
            case "folder":
                if (Rune.isFolder()) {
                    return true;
                }
                if (!Rune.isTablet() && Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE) {
                    return true;
                }
                break;
            case "tablet":
                if (Rune.isTablet()) {
                    return true;
                }
                break;
            case "phone":
                if (!Rune.isFolder() && !Rune.isTablet()) {
                    return true;
                }
                if (Rune.isFolder() && Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE) {
                    return true;
                }
                break;
            default:
                Log.d(TAG, "isRestorableDeviceType: deviceTypeBackup = " + deviceType);
                break;
        }
        Slog.d(TAG, "isRestorableDeviceType: Device type not matching");
        wallpaperBNRHelper.setResultCode(ResultCode.RESULT_FAIL);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void response(ArrayList<WallpaperBNRHelper> arrayList) {
        BnRFileHelper.ErrorCode errorCode = BnRFileHelper.ErrorCode.ERROR_NONE;
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        Iterator<WallpaperBNRHelper> it = arrayList.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            WallpaperBNRHelper next = it.next();
            hashMap.put(Integer.valueOf(next.getWhich()), next.getResultCode());
            if (next.getResultCode() == ResultCode.RESULT_SUCCESS) {
                i++;
            } else {
                i2++;
            }
            if (next.isBackupCase() && !TextUtils.isEmpty(next.getPackageName())) {
                arrayList2.add(next.getPackageName());
            }
        }
        if (i == 0) {
            errorCode = BnRFileHelper.ErrorCode.INVALID_DATA;
        } else if (i2 > 0) {
            errorCode = BnRFileHelper.ErrorCode.PARTIAL_SUCCESS;
        }
        BnRFileHelper.ErrorCode errorCode2 = errorCode;
        WallpaperBNRHelper wallpaperBNRHelper = arrayList.get(0);
        sendResponse(wallpaperBNRHelper.getContext(), wallpaperBNRHelper.getType(), wallpaperBNRHelper.getResponseAction(), i == 0 ? ResultCode.RESULT_FAIL : ResultCode.RESULT_SUCCESS, errorCode2, BnRFileHelper.REQ_MINIMUM_SIZE, wallpaperBNRHelper.getSource(), wallpaperBNRHelper.getSessionTime(), hashMap, arrayList2);
    }

    private static void sendResponse(Context context, int i, String str, ResultCode resultCode, BnRFileHelper.ErrorCode errorCode, int i2, String str2, String str3, HashMap hashMap, List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("sendResponse:\n\t\twhich \t\t\t\t= " + i).append("\n\t\taction \t\t\t\t= " + str).append("\n\t\tresultCode \t\t\t= " + resultCode).append("\n\t\terrorCode \t\t\t= " + errorCode).append("\n\t\trequiredSize \t\t= " + i2).append("\n\t\tsesstionTime \t\t= " + str3).append("\n\t\tsource \t\t\t\t= " + str2);
        if (hashMap != null) {
            for (Integer num : hashMap.keySet()) {
                stringBuffer.append("\n\t\textraResultCode \t= ");
                stringBuffer.append(String.format("%2d", num) + ": " + hashMap.get(num));
            }
        }
        if (list != null && list.size() > 0) {
            stringBuffer.append("\n\t\tpackages \t\t\t\t= " + Arrays.toString(list.toArray()));
        }
        String str4 = TAG;
        Slog.d(str4, stringBuffer.toString());
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra(BnRConstants.RESULT_KEY, resultCode.getCode());
        intent.putExtra(BnRConstants.ERROR_KEY, errorCode.getCode());
        intent.putExtra(BnRConstants.REQUIRED_SIZE_KEY, i2);
        intent.putExtra(BnRConstants.SOURCE_KEY, str2);
        if (!TextUtils.isEmpty(str3)) {
            intent.putExtra(BnRConstants.SESSION_TIME_KEY, str3);
        }
        if (errorCode == BnRFileHelper.ErrorCode.PARTIAL_SUCCESS) {
            intent.putExtra(BnRConstants.EXTRA_ERR_CODE, hashMap);
        }
        if (list != null && list.size() > 0) {
            intent.putStringArrayListExtra("EXTRA", (ArrayList) list);
        }
        context.sendBroadcast(intent, BnRConstants.PERMISSION_COM_WSSNPS);
        Slog.d(str4, "sendBroadcast. " + i);
    }

    static class WallpaperBNRHelper {
        private static final String TAG = "WallpaperBNRHelper";
        String mAction;
        String mBasePath;
        String mComponentName;
        Context mContext;
        String mCoverType;
        Rect mCropHint;
        ParcelFileDescriptor mDescriptor;
        String mDeviceType;
        ArrayList<String> mErrorDescriptions = new ArrayList<>();
        String mExternalParams;
        String mFilePath;
        boolean mIsBackupAllowed;
        boolean mIsBackupCase;
        boolean mIsCustomWallpaper;
        boolean mIsDownloadedThemeWallpaper;
        boolean mIsHomeAndLockPaired;
        int mMode;
        int mOrientation;
        String mPackageName;
        ResultCode mResultCode;
        int mRotation;
        String mSaveKey;
        int mSaveType;
        int mSecurityLevel;
        String mSessionTime;
        String mSettingsName;
        String mSource;
        String mSourceFilePath;
        String mTargetFilePath;
        int mTiltValue;
        int mType;
        Uri mUri;
        WallpaperManager mWallpaperManager;
        int mWallpaperType;
        WallpaperUser mWallpaperUser;
        int mWhich;
        String mXmlPath;

        WallpaperBNRHelper(Context context, WallpaperManager wallpaperManager, String str, int i, String str2, int i2, String str3, String str4, String str5) {
            this.mContext = context;
            if (!TextUtils.isEmpty(str) && str.contains("BACKUP")) {
                this.mIsBackupCase = true;
                Log.d(TAG, "WallpaperBNRHelper: Set backup case true.");
            }
            this.mWhich = i;
            this.mAction = str;
            this.mBasePath = str2;
            this.mSessionTime = str4;
            this.mSource = str5;
            this.mSecurityLevel = i2;
            this.mSaveKey = i2 != 1 ? "" : str3;
            if (!TextUtils.isEmpty(str2) && !this.mBasePath.endsWith(File.separator)) {
                this.mBasePath += File.separator;
            }
            this.mMode = i & 60;
            this.mType = i & 3;
            this.mWallpaperManager = wallpaperManager;
            setSettingsName();
            if (this.mIsBackupCase) {
                createBackupInfo();
            } else {
                createRestoreInfo();
            }
        }

        private String extractPackageName(Uri uri) {
            if (uri == null) {
                return null;
            }
            try {
                String path = uri.getPath();
                if (TextUtils.isEmpty(path)) {
                    return null;
                }
                String[] split = path.split("homewallpaper/");
                if (TextUtils.isEmpty(split[1])) {
                    return null;
                }
                String[] split2 = split[1].split("/");
                if (TextUtils.isEmpty(split2[0])) {
                    return null;
                }
                return split2[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "extractPackageName: uri = " + uri + ", error = " + e.getMessage());
                return null;
            }
        }

        private void createBackupInfoDownloadedTheme() {
            int i = this.mWallpaperType;
            if (i == 0) {
                int i2 = this.mWhich;
                this.mIsHomeAndLockPaired = this.mWallpaperManager.isSystemAndLockPaired(WhichChecker.getMode(i2));
                if (WhichChecker.isLock(this.mWhich) && this.mIsHomeAndLockPaired) {
                    Log.d(TAG, "createBackupInfoDownloadedTheme: Handle System&Lock wallpaper for downloaded theme wallpaper.");
                    i2 = WhichChecker.getMode(this.mWhich) | 1;
                }
                this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(i2);
                Uri semGetUri = this.mWallpaperManager.semGetUri(i2);
                this.mUri = semGetUri;
                this.mPackageName = extractPackageName(semGetUri);
                return;
            }
            if (i == 1) {
                this.mPackageName = this.mWallpaperManager.getMotionWallpaperPkgName(this.mWhich);
                return;
            }
            if (i == 3) {
                Uri semGetUri2 = this.mWallpaperManager.semGetUri(this.mWhich);
                this.mUri = semGetUri2;
                if (semGetUri2 != null) {
                    this.mPackageName = semGetUri2.getHost();
                    return;
                }
                return;
            }
            if (i == 4) {
                this.mPackageName = this.mWallpaperManager.getAnimatedPkgName(this.mWhich);
            } else {
                if (i != 8) {
                    return;
                }
                this.mPackageName = this.mWallpaperManager.getVideoPackage(this.mWhich);
            }
        }

        private void createBackupInfo() {
            if (!this.mWallpaperManager.isWallpaperDataExists(this.mWhich)) {
                Log.d(TAG, "createBackupInfo: WallpaperData for [" + this.mWhich + "] does not exist.");
                addErrorDescription("WallpaperData for [" + this.mWhich + "] does not exist.");
                return;
            }
            this.mWallpaperType = this.mWallpaperManager.semGetWallpaperType(this.mWhich);
            if ((Rune.VIRTUAL_DISPLAY_WALLPAPER && this.mMode == 32) || (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mMode == 16 && this.mWallpaperType == 8)) {
                this.mIsCustomWallpaper = true;
            } else {
                this.mIsCustomWallpaper = Settings.System.getIntForUser(this.mContext.getContentResolver(), this.mSettingsName, 1, -2) == 0;
            }
            this.mIsBackupAllowed = this.mWallpaperManager.isWallpaperBackupAllowed(this.mWhich);
            this.mIsDownloadedThemeWallpaper = Settings.System.getIntForUser(this.mContext.getContentResolver(), this.mSettingsName, 1, -2) == 3;
            if (Rune.isFolder()) {
                this.mDeviceType = "folder";
            } else if (Rune.isTablet()) {
                this.mDeviceType = BnRConstants.DEVICETYPE_TABLET;
            } else {
                this.mDeviceType = "phone";
            }
            if (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                this.mCoverType = BnRConstants.COVER_TYPE_LARGE_SCREEN;
            }
            if (!canBackup()) {
                Log.d(TAG, "createBackupInfo: Not allowed to backup wallpaper");
            }
            if (this.mIsDownloadedThemeWallpaper) {
                createBackupInfoDownloadedTheme();
                return;
            }
            this.mFilePath = getFilePath(null);
            this.mTargetFilePath = this.mBasePath + this.mFilePath;
            Bundle wallpaperExtras = this.mWallpaperManager.getWallpaperExtras(this.mWhich, this.mContext.getUserId());
            if (wallpaperExtras != null) {
                this.mExternalParams = WallpaperExtraBundleHelper.toJson(wallpaperExtras);
            }
            int i = this.mWallpaperType;
            if (i == 0) {
                if (this.mType == 2 && this.mWallpaperManager.isSystemAndLockPaired(this.mMode)) {
                    this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mMode | 1, false);
                } else {
                    this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mWhich, false);
                }
                this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(this.mWhich);
                this.mIsHomeAndLockPaired = this.mWallpaperManager.isSystemAndLockPaired(WhichChecker.getMode(this.mWhich));
                if (getMode() != 4 || TextUtils.isEmpty(this.mDeviceType)) {
                    return;
                }
                if (this.mDeviceType.equals("folder") || this.mDeviceType.equals(BnRConstants.DEVICETYPE_TABLET)) {
                    this.mOrientation = this.mWallpaperManager.getWallpaperOrientation(this.mWhich, this.mContext.getUserId());
                    return;
                }
                return;
            }
            if (i == 3) {
                Uri semGetUri = this.mWallpaperManager.semGetUri(this.mWhich);
                if (semGetUri != null) {
                    this.mUri = semGetUri;
                    this.mSourceFilePath = BnRConstants.CUSTOM_MULTIPACK_SOURCE_PATH + semGetUri.getHost() + semGetUri.getPath();
                    return;
                }
                return;
            }
            if (i == 5) {
                Uri semGetUri2 = this.mWallpaperManager.semGetUri(this.mWhich);
                if (semGetUri2 != null) {
                    this.mUri = semGetUri2;
                    this.mSourceFilePath = semGetUri2.getPath();
                    return;
                }
                return;
            }
            if (i == 7) {
                this.mIsHomeAndLockPaired = this.mWallpaperManager.isSystemAndLockPaired(WhichChecker.getMode(this.mWhich));
                ComponentName semGetWallpaperComponent = this.mWallpaperManager.semGetWallpaperComponent(this.mWhich, this.mContext.getUserId());
                if (semGetWallpaperComponent != null) {
                    this.mComponentName = semGetWallpaperComponent.flattenToString();
                    return;
                }
                return;
            }
            if (i == 8) {
                this.mSourceFilePath = this.mWallpaperManager.getVideoFilePath(this.mWhich);
                if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mMode == 16) {
                    this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mWhich, this.mContext.getUserId(), -1);
                    this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(this.mWhich);
                    return;
                }
                return;
            }
            Log.e(TAG, "createBackupInfo: Unhandled wallpaper type, mWallpaperType = " + this.mWallpaperType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void convertToImageWallpaperForSCloud() {
            addErrorDescription("convertImageWallpaperForSCloud: Backup with Samsung cloud, change layered type to image");
            this.mWallpaperType = 0;
            this.mFilePath = getFilePath(null);
            this.mTargetFilePath = this.mBasePath + this.mFilePath;
            int i = isHomeAndLockPaired() ? this.mMode | 1 : this.mWhich;
            try {
                String string = this.mWallpaperManager.getWallpaperExtras(i, this.mContext.getUserId()).getString(BnRConstants.KEY_REPRESENTATIVE_IMAGE_FILE);
                if (TextUtils.isEmpty(string)) {
                    Log.i(TAG, "representative file name is empty, try thumbnail file name and remove crop hints");
                    this.mExternalParams = null;
                    string = this.mWallpaperManager.getWallpaperExtras(i, this.mContext.getUserId()).getString(BnRConstants.KEY_THUNBNAIL_FILENAME);
                }
                this.mDescriptor = this.mWallpaperManager.getWallpaperAssetFile(i, this.mContext.getUserId(), string);
            } catch (Exception e) {
                addErrorDescription("Error occured getting representative file : " + e.toString() + ", try hard-coding way..");
                this.mDescriptor = this.mWallpaperManager.getWallpaperAssetFile(i, this.mContext.getUserId(), "thumbnail.jpg");
            }
        }

        private void createRestoreInfo() {
            Uri uri;
            XmlParser xmlParser = getXmlParser();
            if (xmlParser == null) {
                addErrorDescription("createRestoreInfo: xmlParser is null.");
                return;
            }
            WallpaperUser object = xmlParser.getObject();
            if (object == null) {
                addErrorDescription("createRestoreInfo: Cannot create WallpaperUser for restoring.");
                return;
            }
            this.mWallpaperUser = object;
            this.mWallpaperType = object.getWpType();
            this.mRotation = object.getRotationValue();
            this.mTiltValue = object.getTiltSettingValue();
            this.mUri = object.getUri();
            this.mExternalParams = object.getExternalParams();
            this.mIsHomeAndLockPaired = object.getIsHomeAndLockPaired();
            this.mCropHint = new Rect(object.getLeftValue(), object.getTopValue(), object.getRightValue(), object.getBottomValue());
            this.mDeviceType = object.getDeviceType();
            this.mCoverType = object.getCoverType();
            if (Rune.isFolder() && TextUtils.isEmpty(object.getDeviceType()) && getMode() == 4) {
                if (this.mCropHint.isEmpty()) {
                    addErrorDescription("createRestoreInfo: Cannot identify device type.");
                } else {
                    float min = Math.min(this.mCropHint.width(), this.mCropHint.height()) / Math.max(this.mCropHint.width(), this.mCropHint.height());
                    if (min > 0.74f) {
                        addErrorDescription("createRestoreInfo: Consider backup device is table. ratio = " + min);
                        this.mDeviceType = "folder";
                        object.setDeviceType("folder");
                    }
                }
            }
            if (this.mWallpaperType == 0 && getMode() == 4 && !TextUtils.isEmpty(this.mDeviceType) && (this.mDeviceType.equals("folder") || this.mDeviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                this.mOrientation = object.getOrientation();
            }
            boolean z = object.getTransparency() == 3;
            this.mIsDownloadedThemeWallpaper = z;
            if (z) {
                this.mPackageName = object.getComponent();
                if (this.mWallpaperType == 0 && (uri = object.getUri()) != null) {
                    this.mSourceFilePath = uri.getPath();
                    return;
                }
                return;
            }
            String sourceFilePath = getSourceFilePath(object);
            this.mSourceFilePath = sourceFilePath;
            if (this.mWallpaperType != 8) {
                return;
            }
            String fileNameFromPath = getFileNameFromPath(sourceFilePath);
            if (TextUtils.isEmpty(fileNameFromPath)) {
                fileNameFromPath = createVideoFileName();
            } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && getMode() == 4 && getType() == 2 && fileNameFromPath.endsWith("_6.mp4")) {
                fileNameFromPath = fileNameFromPath.replace("_6.mp4", "_2.mp4");
            }
            this.mTargetFilePath = BnRConstants.WALLPAPER_VIDEO_RESTORE_PATH + File.separator + fileNameFromPath;
        }

        private void setSettingsName() {
            this.mSettingsName = "";
            int i = this.mMode;
            if (i == 4) {
                if (this.mType == 2) {
                    this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY;
                    return;
                } else {
                    this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY;
                    return;
                }
            }
            if (i == 8) {
                if (this.mType == 2) {
                    this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_DEX;
                    return;
                } else {
                    this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_DEX;
                    return;
                }
            }
            if (i != 16) {
                return;
            }
            if (this.mType == 2) {
                this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_SUB_DISPLAY;
            } else {
                this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_SUB_DISPLAY;
            }
        }

        private boolean isLiveWallpaper() {
            return this.mWallpaperManager.semGetWallpaperType(this.mWhich) == 7;
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x008f, code lost:
        
            if (r0.startsWith(com.samsung.android.wallpaperbackup.BnRConstants.CUSTOM_PACK_PREFIX) != false) goto L45;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean canBackup() {
            /*
                r5 = this;
                int r0 = android.os.Build.VERSION.SEM_PLATFORM_INT
                r1 = 150000(0x249f0, float:2.10195E-40)
                r2 = 0
                r3 = 1
                if (r0 <= r1) goto L56
                int r0 = r5.mWallpaperType
                r1 = 7
                if (r0 != r1) goto L56
                boolean r0 = r5.isHomeAndLockPaired()     // Catch: java.lang.NullPointerException -> L51
                if (r0 == 0) goto L24
                android.app.WallpaperManager r0 = r5.mWallpaperManager     // Catch: java.lang.NullPointerException -> L51
                int r1 = r5.mMode     // Catch: java.lang.NullPointerException -> L51
                r1 = r1 | r3
                android.content.Context r4 = r5.mContext     // Catch: java.lang.NullPointerException -> L51
                int r4 = r4.getUserId()     // Catch: java.lang.NullPointerException -> L51
                android.os.Bundle r0 = r0.getWallpaperAssets(r1, r4)     // Catch: java.lang.NullPointerException -> L51
                goto L32
            L24:
                android.app.WallpaperManager r0 = r5.mWallpaperManager     // Catch: java.lang.NullPointerException -> L51
                int r1 = r5.mWhich     // Catch: java.lang.NullPointerException -> L51
                android.content.Context r4 = r5.mContext     // Catch: java.lang.NullPointerException -> L51
                int r4 = r4.getUserId()     // Catch: java.lang.NullPointerException -> L51
                android.os.Bundle r0 = r0.getWallpaperAssets(r1, r4)     // Catch: java.lang.NullPointerException -> L51
            L32:
                if (r0 == 0) goto L4b
                java.util.Set r0 = r0.keySet()     // Catch: java.lang.NullPointerException -> L51
                int r0 = r0.size()     // Catch: java.lang.NullPointerException -> L51
                if (r0 > 0) goto L3f
                goto L4b
            L3f:
                boolean r0 = r5.mIsBackupAllowed     // Catch: java.lang.NullPointerException -> L51
                if (r0 != 0) goto L62
                java.lang.String r0 = "Ignore mIsBackupAllowed = false in case custom live wallpaper."
                r5.addErrorDescription(r0)     // Catch: java.lang.NullPointerException -> L51
                r5.mIsBackupAllowed = r3     // Catch: java.lang.NullPointerException -> L51
                goto L62
            L4b:
                java.lang.String r0 = "Live wallpaper is applied with NO asset files."
                r5.addErrorDescription(r0)     // Catch: java.lang.NullPointerException -> L51
                return r2
            L51:
                r0 = move-exception
                r0.printStackTrace()
                goto L62
            L56:
                boolean r0 = r5.isLiveWallpaper()
                if (r0 == 0) goto L62
                java.lang.String r0 = "Live wallpaper is applied."
                r5.addErrorDescription(r0)
                return r2
            L62:
                boolean r0 = r5.mIsCustomWallpaper
                if (r0 == 0) goto L68
                boolean r1 = r5.mIsBackupAllowed
            L68:
                if (r0 != 0) goto L6e
                boolean r0 = r5.mIsDownloadedThemeWallpaper
                if (r0 == 0) goto L73
            L6e:
                boolean r0 = r5.mIsBackupAllowed
                if (r0 == 0) goto L73
                r2 = r3
            L73:
                int r0 = r5.mWallpaperType
                r1 = 3
                if (r0 != r1) goto L92
                if (r2 != 0) goto L92
                android.net.Uri r0 = r5.mUri
                if (r0 == 0) goto L92
                java.lang.String r0 = r0.toString()
                boolean r1 = android.text.TextUtils.isEmpty(r0)
                if (r1 != 0) goto L92
                java.lang.String r1 = "multipack://com.samsung.custompack"
                boolean r0 = r0.startsWith(r1)
                if (r0 == 0) goto L92
                goto L93
            L92:
                r3 = r2
            L93:
                java.lang.String r0 = com.samsung.android.wallpaperbackup.WallpaperBackupRestoreManager.WallpaperBNRHelper.TAG
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "canBackup: which = "
                r1.<init>(r2)
                int r5 = r5.mWhich
                r1.append(r5)
                java.lang.String r5 = " canBackup = "
                r1.append(r5)
                r1.append(r3)
                java.lang.String r5 = r1.toString()
                android.util.Slog.d(r0, r5)
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaperbackup.WallpaperBackupRestoreManager.WallpaperBNRHelper.canBackup():boolean");
        }

        public boolean isBackupCase() {
            return this.mIsBackupCase;
        }

        public String getDeviceType() {
            return this.mDeviceType;
        }

        public String getCoverType() {
            return this.mCoverType;
        }

        public boolean isDownloadedThemeWallpaper() {
            return this.mIsDownloadedThemeWallpaper;
        }

        public void setWhich(int i) {
            this.mWhich = i;
            this.mMode = i & 60;
            this.mType = i & 3;
        }

        public int getWhich() {
            return this.mWhich;
        }

        public int getMode() {
            return this.mMode;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getType() {
            return this.mType;
        }

        public int getWallpaperType() {
            return this.mWallpaperType;
        }

        public int getTiltValue() {
            return this.mTiltValue;
        }

        public Context getContext() {
            return this.mContext;
        }

        public Rect getCropHint() {
            return this.mCropHint;
        }

        public int getRotationValue() {
            return this.mRotation;
        }

        public String getResponseAction() {
            if (!TextUtils.isEmpty(this.mAction)) {
                return this.mAction;
            }
            if (this.mIsBackupCase) {
                if (this.mType == 2) {
                    return BnRConstants.RESPONSE_BACKUP_LOCKSCREEN;
                }
                return BnRConstants.RESPONSE_BACKUP_WALLPAPER;
            }
            if (this.mType == 2) {
                return BnRConstants.RESPONSE_RESTORE_LOCKSCREEN;
            }
            return BnRConstants.RESPONSE_RESTORE_WALLPAPER;
        }

        public ResultCode getResultCode() {
            return this.mResultCode;
        }

        public void setResultCode(ResultCode resultCode) {
            this.mResultCode = resultCode;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public String getExternalParams() {
            return this.mExternalParams;
        }

        public String getComponentName() {
            return this.mComponentName;
        }

        public boolean isHomeAndLockPaired() {
            return this.mIsHomeAndLockPaired;
        }

        public int getOrientation() {
            return this.mOrientation;
        }

        public WallpaperUser getWallpaperUser() {
            return this.mWallpaperUser;
        }

        public String getBasePath() {
            return this.mBasePath;
        }

        public int getFileSaveType() {
            return this.mSaveType;
        }

        public String getFileSaveKey() {
            return this.mSaveKey;
        }

        public String getSessionTime() {
            return this.mSessionTime;
        }

        public int getSecurityLevel() {
            return this.mSecurityLevel;
        }

        public String getSource() {
            return this.mSource;
        }

        public ParcelFileDescriptor getDescriptor() {
            return this.mDescriptor;
        }

        public ArrayList<String> getErrorDescriptions() {
            return this.mErrorDescriptions;
        }

        public void addErrorDescription(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mErrorDescriptions.add(str);
        }

        @Deprecated
        public String getSourceFilePath(WallpaperUser wallpaperUser) {
            int wpType;
            if (wallpaperUser == null || (wpType = wallpaperUser.getWpType()) == 0 || wpType == -1) {
                String str = getBasePath() + getOriginalFilePath();
                if (BnRFileHelper.isExist(str)) {
                    this.mSourceFilePath = str;
                    return str;
                }
            }
            return getBasePath() + getFilePath(wallpaperUser);
        }

        public String getSourceFilePath() {
            return this.mSourceFilePath;
        }

        public String getTargetFilePath() {
            return this.mTargetFilePath;
        }

        public String getFilePath() {
            return this.mFilePath;
        }

        public String getXmlPath() {
            return this.mXmlPath;
        }

        public String getSettingsName() {
            return this.mSettingsName;
        }

        public WallpaperManager getWallpaperManager() {
            return this.mWallpaperManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Deprecated
        public String getOriginalFilePath() {
            int i = this.mMode;
            String str = BnRConstants.LOCK_WALLPAPER_FILE_NAME;
            if (i == 4 || i == 8) {
                if (this.mType != 2) {
                    str = BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
                }
                return "wallpaper_original/".concat(str);
            }
            if (i != 16) {
                return null;
            }
            if (this.mType != 2) {
                str = BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
            }
            return "wallpaper_sub_display_original/".concat(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Deprecated
        public String getOriginalXmlFilePath() {
            int i = this.mMode;
            if (i == 4 || i == 8) {
                return this.mType == 2 ? BnRConstants.ORIGINAL_LOCK_XML_NAME : BnRConstants.ORIGINAL_XML_NAME;
            }
            if (i == 16) {
                return this.mType == 2 ? BnRConstants.SUB_DISPLAY_LOCK_XML_ORIGINAL_NAME : BnRConstants.SUB_DISPLAY_XML_ORIGINAL_NAME;
            }
            if (i != 32) {
                return null;
            }
            return this.mType == 2 ? "" : BnRConstants.VIRTUAL_DISPLAY_XML_NAME;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getXmlFilePath() {
            int i = this.mMode;
            if (i == 4) {
                return this.mType == 2 ? BnRConstants.DEFAULT_LOCK_XML_NAME : BnRConstants.DEFAULT_XML_NAME;
            }
            if (i != 8) {
                if (i == 16) {
                    return this.mType == 2 ? BnRConstants.SUB_DISPLAY_LOCK_XML_NAME : BnRConstants.SUB_DISPLAY_XML_NAME;
                }
                if (i != 32) {
                    return null;
                }
                return this.mType == 2 ? "" : BnRConstants.VIRTUAL_DISPLAY_XML_NAME;
            }
            String str = this.mType == 2 ? BnRConstants.DEX_LOCK_XML_NAME : BnRConstants.DEX_XML_NAME;
            if (!this.mIsBackupCase) {
                if (!new File(this.mBasePath + str).exists() && this.mType == 2) {
                    File file = new File(this.mBasePath + BnRConstants.DEX_XML_NAME);
                    if (file.exists() && file.canRead()) {
                        return BnRConstants.DEX_XML_NAME;
                    }
                }
            }
            return str;
        }

        private String getFilePath(WallpaperUser wallpaperUser) {
            if (wallpaperUser != null) {
                String path = wallpaperUser.getPath();
                if (!TextUtils.isEmpty(path)) {
                    return path;
                }
            }
            int i = this.mWallpaperType;
            if (i == -1 || i == 0) {
                return getImagePath();
            }
            if (i == 3) {
                return getMultipackPath();
            }
            if (i == 5) {
                return getGifPath();
            }
            if (i == 7) {
                return getLiveWallpaperPath();
            }
            if (i == 8) {
                return getVideoPath();
            }
            Log.e(TAG, "getFilePath: mWallpaperType = " + this.mWallpaperType);
            return null;
        }

        private String getGifPath() {
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                if (this.mMode == 16 && this.mType == 1) {
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_GIF_FILE_NAME;
                }
                Log.d(TAG, "getGifPath: Invalid which for gif wallpaper. mWhich = " + this.mWhich);
                return null;
            }
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER) {
                if (this.mMode == 32 && this.mType == 1) {
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_GIF_FILE_NAME;
                }
                Log.d(TAG, "getGifPath: Invalid which for gif wallpaper. mWhich = " + this.mWhich);
                return null;
            }
            Log.d(TAG, "getGifPath: NOT SUPPORTED YET!");
            return null;
        }

        private String getImagePath() {
            String str = this.mType == 2 ? BnRConstants.LOCK_WALLPAPER_FILE_NAME : BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
            int i = this.mMode;
            if (i == 4) {
                return "wallpaper" + File.separator + str;
            }
            if (i == 8) {
                return BnRConstants.DEX_FOLDER_NAME + File.separator + str;
            }
            if (i == 16) {
                return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + str;
            }
            if (i != 32) {
                return null;
            }
            return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + str;
        }

        private String getMultipackPath() {
            int i = this.mMode;
            if (i == 4) {
                return "wallpaper" + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
            }
            if (i == 8) {
                return BnRConstants.DEX_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
            }
            if (i == 16) {
                return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
            }
            if (i != 32) {
                return null;
            }
            return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
        }

        private String getVideoPath() {
            int i = this.mMode;
            if (i == 4) {
                return "wallpaper" + File.separator + getVideoFileName();
            }
            if (i == 8) {
                return BnRConstants.DEX_FOLDER_NAME + File.separator + getVideoFileName();
            }
            if (i == 16) {
                return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.VIDEO_DIR_PATH + getVideoFileName();
            }
            if (i != 32) {
                return null;
            }
            return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + getVideoFileName();
        }

        private String getLiveWallpaperPath() {
            int i = this.mMode;
            if (i == 4) {
                return "wallpaper" + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
            }
            if (i == 8) {
                return BnRConstants.DEX_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
            }
            if (i == 16) {
                return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
            }
            if (i != 32) {
                return null;
            }
            return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
        }

        private String getVideoFileName() {
            String str;
            try {
                str = getFileNameFromPath(this.mWallpaperManager.getVideoFilePath(this.mWhich));
            } catch (Exception e) {
                e.printStackTrace();
                str = "";
            }
            return TextUtils.isEmpty(str) ? createVideoFileName() : str;
        }

        private String createVideoFileName() {
            int i = this.mWhich;
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && getMode() == 4 && getType() == 2) {
                i = 2;
            }
            if (this.mContext != null) {
                return "video_wallpaper_" + this.mContext.getUserId() + Session.SESSION_SEPARATION_CHAR_CHILD + i + ".mp4";
            }
            Log.d(TAG, "createVideoFileName: context is null!");
            return "video_wallpaper_0_" + i + ".mp4";
        }

        private String getFileNameFromPath(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return str.substring(str.lastIndexOf("/") + 1);
        }

        private XmlParser getXmlParser() {
            try {
                if (new File(this.mBasePath, getOriginalXmlFilePath()).exists()) {
                    return new XmlParser(this.mBasePath + getOriginalXmlFilePath());
                }
                return new XmlParser(this.mBasePath + getXmlFilePath());
            } catch (Exception e) {
                Log.e(TAG, "getXmlParser: " + e.getMessage());
                addErrorDescription("getXmlParser: " + e.getMessage());
                return null;
            }
        }

        private String getStringWhich() {
            StringBuffer stringBuffer = new StringBuffer(NavigationBarInflaterView.SIZE_MOD_START);
            int i = this.mMode;
            if (i == 4) {
                stringBuffer.append("MAIN");
            } else if (i == 8) {
                stringBuffer.append("DEX");
            } else if (i == 16) {
                stringBuffer.append("SUB");
            } else if (i == 32) {
                stringBuffer.append("VIRTUAL");
            }
            int i2 = this.mType;
            if (i2 == 1) {
                stringBuffer.append(" | HOME");
            } else if (i2 == 2) {
                stringBuffer.append(" | LOCK");
            }
            stringBuffer.append(NavigationBarInflaterView.SIZE_MOD_END);
            return stringBuffer.toString();
        }

        public void close() {
            BnRFileHelper.closeSilently(this.mDescriptor);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("\n\tWallpaperBnRHelper:");
            StringBuffer append = stringBuffer.append("\n\t\tmWhich = " + this.mWhich + ": " + getStringWhich());
            StringBuilder sb = new StringBuilder("\n\t\tmWallpaperType = ");
            sb.append(this.mWallpaperType);
            append.append(sb.toString()).append("\n\t\tmIsBackupCase = " + this.mIsBackupCase);
            if (this.mIsBackupCase) {
                stringBuffer.append("\n\t\tmIsCustomWallpaper = " + this.mIsCustomWallpaper).append("\n\t\tmIsBackupAllowed = " + this.mIsBackupAllowed);
                stringBuffer.append("\n\t\tmIsDownloadedThemeWallpaper = " + this.mIsDownloadedThemeWallpaper);
            }
            if (!TextUtils.isEmpty(this.mPackageName)) {
                stringBuffer.append("\n\t\tmPackageName = " + this.mPackageName);
            }
            if (!TextUtils.isEmpty(this.mDeviceType)) {
                stringBuffer.append("\n\t\tmDeviceType = " + this.mDeviceType);
            }
            stringBuffer.append("\n\t\tmUri = " + this.mUri).append("\n\t\tmSaveType = " + this.mSaveType).append("\n\t\tmSecurityLevel = " + this.mSecurityLevel).append("\n\t\tmSaveKey = " + this.mSaveKey).append("\n\t\tmSessionTime = " + this.mSessionTime).append("\n\t\tmTiltValue = " + this.mTiltValue).append("\n\t\tmCropHint = " + this.mCropHint).append("\n\t\tmRotation = " + this.mRotation).append("\n\t\tmAction = " + this.mAction).append("\n\t\tmBasePath = " + this.mBasePath).append("\n\t\tmSource = " + this.mSource).append("\n\t\tmDescriptor = " + this.mDescriptor).append("\n\t\tmSourceFilePath = " + this.mSourceFilePath).append("\n\t\tmTargetFilePath = " + this.mTargetFilePath).append("\n\t\tmFilePath = " + this.mFilePath).append("\n\t\tmXmlPath = " + this.mXmlPath).append("\n\t\tmSettingsName = " + this.mSettingsName).append("\n\t\tmExternalParams = " + this.mExternalParams).append("\n\t\tmIsHomeAndLockPaired = " + this.mIsHomeAndLockPaired).append("\n\t\tmComponentName = " + this.mComponentName).append("\n\t\tmResultCode = " + this.mResultCode);
            if (this.mWallpaperUser != null) {
                stringBuffer.append("\n\t\tmWallpaperUser = " + this.mWallpaperUser.toString());
            }
            if (this.mErrorDescriptions.size() > 0) {
                stringBuffer.append("\n\t\tmErrorDescriptions = ");
                Iterator<String> it = this.mErrorDescriptions.iterator();
                while (it.hasNext()) {
                    stringBuffer.append("\n\t\t\t" + it.next());
                }
            }
            return stringBuffer.toString();
        }
    }

    static class WallpaperBackupAsyncTask extends AsyncTask<ArrayList, WallpaperBNRHelper, ArrayList<WallpaperBNRHelper>> {
        WallpaperBackupAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public ArrayList doInBackground(ArrayList... arrayListArr) {
            boolean z;
            ArrayList arrayList = arrayListArr[0];
            for (int i = 0; i < arrayList.size(); i++) {
                WallpaperBNRHelper wallpaperBNRHelper = (WallpaperBNRHelper) arrayList.get(i);
                cleanupFiles(wallpaperBNRHelper);
                if (wallpaperBNRHelper.canBackup()) {
                    z = wallpaperBNRHelper.isDownloadedThemeWallpaper() ? true : backupWallpaper(wallpaperBNRHelper);
                } else {
                    z = false;
                }
                if (z) {
                    backupXml(wallpaperBNRHelper);
                    wallpaperBNRHelper.setResultCode(ResultCode.RESULT_SUCCESS);
                } else {
                    wallpaperBNRHelper.setResultCode(ResultCode.RESULT_FAIL);
                }
                publishProgress(wallpaperBNRHelper);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(WallpaperBNRHelper... wallpaperBNRHelperArr) {
            try {
                WallpaperBNRHelper wallpaperBNRHelper = wallpaperBNRHelperArr[0];
                Slog.d(WallpaperBackupRestoreManager.TAG, "onProgressUpdate:" + wallpaperBNRHelper.toString());
                wallpaperBNRHelper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ArrayList<WallpaperBNRHelper> arrayList) {
            WallpaperBackupRestoreManager.response(arrayList);
        }

        private boolean backupWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            int wallpaperType = wallpaperBNRHelper.getWallpaperType();
            if (wallpaperType == -1 || wallpaperType == 0) {
                if (wallpaperBNRHelper.getDescriptor() == null) {
                    return false;
                }
                return BnRFileHelper.copyFile(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getDescriptor(), wallpaperBNRHelper.getFileSaveKey());
            }
            if (wallpaperType == 3) {
                return BnRFileHelper.copyDir(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getFileSaveKey());
            }
            if (wallpaperType == 5) {
                return BnRFileHelper.copyFile(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getFileSaveKey());
            }
            if (wallpaperType == 7) {
                if (BnRConstants.BNR_SOURCE_SCLOUD.equals(wallpaperBNRHelper.getSource())) {
                    wallpaperBNRHelper.convertToImageWallpaperForSCloud();
                    if (wallpaperBNRHelper.getDescriptor() == null) {
                        return false;
                    }
                    return BnRFileHelper.copyFile(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getDescriptor(), wallpaperBNRHelper.getFileSaveKey());
                }
                return BnRFileHelper.copyAssets(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getWallpaperManager().getWallpaperAssets(wallpaperBNRHelper.getWhich(), wallpaperBNRHelper.getContext().getUserId()), wallpaperBNRHelper.getFileSaveKey());
            }
            if (wallpaperType != 8) {
                return false;
            }
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && wallpaperBNRHelper.getMode() == 16) {
                BnRFileHelper.copyFile(wallpaperBNRHelper.getBasePath() + BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + "video/cover_video_thumb.jpg", wallpaperBNRHelper.getDescriptor(), wallpaperBNRHelper.getFileSaveKey());
            }
            return BnRFileHelper.copyFile(wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getFileSaveKey());
        }

        private void backupXml(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper == null) {
                Log.d(WallpaperBackupRestoreManager.TAG, "backupXml: Cannot create backup xml file.");
                return;
            }
            WallpaperUser wallpaperUser = new WallpaperUser();
            int wallpaperType = wallpaperBNRHelper.getWallpaperType();
            wallpaperUser.setWpType(wallpaperType);
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && wallpaperBNRHelper.getMode() == 16 && wallpaperType == 8) {
                wallpaperUser.setPath(BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.VIDEO_DIR_PATH);
            } else {
                wallpaperUser.setPath(wallpaperBNRHelper.getFilePath());
            }
            wallpaperUser.setTiltSettingValue(wallpaperBNRHelper.getTiltValue());
            if (wallpaperBNRHelper.isDownloadedThemeWallpaper()) {
                wallpaperUser.setTransparency(3);
            }
            wallpaperUser.setDeviceType(wallpaperBNRHelper.getDeviceType());
            wallpaperUser.setCoverType(wallpaperBNRHelper.getCoverType());
            if (wallpaperType == 0 && wallpaperBNRHelper.getMode() == 4) {
                String deviceType = wallpaperBNRHelper.getDeviceType();
                if (!TextUtils.isEmpty(deviceType) && (deviceType.equals("folder") || deviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                    wallpaperUser.setOrientation(wallpaperBNRHelper.getOrientation());
                }
            }
            if (!TextUtils.isEmpty(wallpaperBNRHelper.getPackageName())) {
                wallpaperUser.setComponent(wallpaperBNRHelper.getPackageName());
            }
            if (!TextUtils.isEmpty(wallpaperBNRHelper.getComponentName())) {
                wallpaperUser.setComponentName(wallpaperBNRHelper.getComponentName());
            }
            if (wallpaperBNRHelper.getUri() != null) {
                wallpaperUser.setUri(wallpaperBNRHelper.getUri());
            }
            if (!TextUtils.isEmpty(wallpaperBNRHelper.getExternalParams())) {
                wallpaperUser.setExternalParams(wallpaperBNRHelper.getExternalParams());
            }
            wallpaperUser.setIsHomeAndLockPaired(wallpaperBNRHelper.isHomeAndLockPaired());
            Rect cropHint = wallpaperBNRHelper.getCropHint();
            if (cropHint != null && !cropHint.isEmpty()) {
                WallpaperUser.WallpaperData wallpaperData = new WallpaperUser.WallpaperData();
                wallpaperData.left = cropHint.left;
                wallpaperData.right = cropHint.right;
                wallpaperData.top = cropHint.top;
                wallpaperData.bottom = cropHint.bottom;
                wallpaperUser.setWallpaperData(wallpaperData);
            }
            GenerateXML.generateXML(new File(wallpaperBNRHelper.getBasePath(), wallpaperBNRHelper.getXmlFilePath()), wallpaperBNRHelper.getWhich(), wallpaperUser);
        }

        private void cleanupFiles(WallpaperBNRHelper wallpaperBNRHelper) {
            String str = wallpaperBNRHelper.getBasePath() + wallpaperBNRHelper.getFilePath();
            String str2 = wallpaperBNRHelper.getBasePath() + wallpaperBNRHelper.getXmlFilePath();
            BnRFileHelper.deleteFile(str);
            BnRFileHelper.deleteFile(str2);
            String str3 = wallpaperBNRHelper.getBasePath() + wallpaperBNRHelper.getOriginalFilePath();
            String str4 = wallpaperBNRHelper.getBasePath() + wallpaperBNRHelper.getOriginalXmlFilePath();
            BnRFileHelper.deleteFile(str3);
            BnRFileHelper.deleteFile(str4);
        }
    }

    static class WallpaperRestoreAsyncTask extends AsyncTask<ArrayList, WallpaperBNRHelper, ArrayList<WallpaperBNRHelper>> {
        WallpaperRestoreAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public ArrayList doInBackground(ArrayList... arrayListArr) {
            ArrayList arrayList = arrayListArr[0];
            for (int i = 0; i < arrayList.size(); i++) {
                WallpaperBNRHelper wallpaperBNRHelper = (WallpaperBNRHelper) arrayList.get(i);
                int writeTransparencySettingValue = wallpaperBNRHelper.getMode() != 32 ? writeTransparencySettingValue(wallpaperBNRHelper) : -1;
                if (restoreWallpaper(wallpaperBNRHelper)) {
                    int wallpaperType = wallpaperBNRHelper.getWallpaperType();
                    if (wallpaperType != 3 && wallpaperType != 5 && (wallpaperType != 8 || !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || wallpaperBNRHelper.getType() != 1 || wallpaperBNRHelper.getMode() != 16)) {
                        requestResetEditInfo(wallpaperBNRHelper.getContext(), wallpaperBNRHelper.getWhich());
                    }
                    wallpaperBNRHelper.setResultCode(ResultCode.RESULT_SUCCESS);
                    if (wallpaperBNRHelper.getMode() != 32) {
                        writeSettingValue(wallpaperBNRHelper);
                    }
                    if (Build.VERSION.SEM_PLATFORM_INT >= 140100) {
                        WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).semClearBackupWallpapers(wallpaperBNRHelper.getWhich());
                    }
                } else {
                    if (writeTransparencySettingValue >= 0) {
                        writeTransparencySettingValue(wallpaperBNRHelper, writeTransparencySettingValue);
                    }
                    wallpaperBNRHelper.setResultCode(ResultCode.RESULT_FAIL);
                }
                publishProgress(wallpaperBNRHelper);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(WallpaperBNRHelper... wallpaperBNRHelperArr) {
            try {
                WallpaperBNRHelper wallpaperBNRHelper = wallpaperBNRHelperArr[0];
                Slog.d(WallpaperBackupRestoreManager.TAG, "onProgressUpdate:" + wallpaperBNRHelper.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ArrayList<WallpaperBNRHelper> arrayList) {
            WallpaperBackupRestoreManager.response(arrayList);
        }

        private int writeTransparencySettingValue(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper == null) {
                return -1;
            }
            return writeTransparencySettingValue(wallpaperBNRHelper, wallpaperBNRHelper.isDownloadedThemeWallpaper() ? 3 : 0);
        }

        private int writeTransparencySettingValue(WallpaperBNRHelper wallpaperBNRHelper, int i) {
            if (wallpaperBNRHelper == null) {
                return -1;
            }
            ContentResolver contentResolver = wallpaperBNRHelper.getContext().getContentResolver();
            String settingsName = wallpaperBNRHelper.getSettingsName();
            int intForUser = Settings.System.getIntForUser(contentResolver, settingsName, 1, -2);
            Settings.System.putIntForUser(contentResolver, settingsName, i, -2);
            return intForUser;
        }

        private void writeSettingValue(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper != null && wallpaperBNRHelper.getType() == 2) {
                int mode = wallpaperBNRHelper.getMode();
                if (mode == 4) {
                    Settings.System.putIntForUser(wallpaperBNRHelper.getContext().getContentResolver(), WallpaperManager.SETTINGS_LOCKSCREEN_WALLPAPER, 1, -2);
                } else {
                    if (mode != 16) {
                        return;
                    }
                    Settings.System.putIntForUser(wallpaperBNRHelper.getContext().getContentResolver(), WallpaperManager.SETTINGS_LOCKSCREEN_WALLPAPER_SUB, 1, -2);
                }
            }
        }

        private boolean restoreWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper == null) {
                return false;
            }
            if (wallpaperBNRHelper.isDownloadedThemeWallpaper()) {
                if (!isPackageInstalled(wallpaperBNRHelper)) {
                    wallpaperBNRHelper.addErrorDescription("restoreWallpaper: Package is not installed");
                    return false;
                }
            } else if (!isSourceFileValid(wallpaperBNRHelper)) {
                return false;
            }
            int wallpaperType = wallpaperBNRHelper.getWallpaperType();
            if (wallpaperType == -1 || wallpaperType == 0) {
                if (Build.VERSION.SEM_PLATFORM_INT >= 140100 && (WhichChecker.isWatchFaceDisplay(wallpaperBNRHelper.getWhich()) || WhichChecker.isVirtualDisplay(wallpaperBNRHelper.getWhich()))) {
                    return setImageWallpaperDroom(wallpaperBNRHelper);
                }
                if (wallpaperBNRHelper.getRotationValue() == 0 && wallpaperBNRHelper.getSecurityLevel() == 0) {
                    return setStream(wallpaperBNRHelper);
                }
                return setBitmap(wallpaperBNRHelper);
            }
            if (wallpaperType == 1) {
                return setMotionWallpaper(wallpaperBNRHelper);
            }
            if (wallpaperType == 3) {
                if (wallpaperBNRHelper.getMode() == 8) {
                    return false;
                }
                return setMultipackWallpaper(wallpaperBNRHelper);
            }
            if (wallpaperType == 4) {
                return setAnimatedWallpaper(wallpaperBNRHelper);
            }
            if (wallpaperType == 5) {
                return setGifWallpaper(wallpaperBNRHelper);
            }
            if (wallpaperType == 7) {
                return setLiveWallpaper(wallpaperBNRHelper);
            }
            if (wallpaperType == 8) {
                return setVideoWallpaper(wallpaperBNRHelper);
            }
            wallpaperBNRHelper.addErrorDescription("restoreWallpaper: Unhandled wallpaper type [" + wallpaperBNRHelper.getWallpaperType() + "].");
            return false;
        }

        private boolean setImageWallpaperDroom(WallpaperBNRHelper wallpaperBNRHelper) {
            Bundle bundle = new Bundle();
            bundle.putString("key", wallpaperBNRHelper.getFileSaveKey());
            bundle.putInt("which", wallpaperBNRHelper.getWhich());
            bundle.putInt("type", wallpaperBNRHelper.getWallpaperType());
            bundle.putInt(GenerateXML.ROTATION, wallpaperBNRHelper.getRotationValue());
            bundle.putString("source_path", wallpaperBNRHelper.getSourceFilePath());
            bundle.putParcelable("crop_rect", wallpaperBNRHelper.getCropHint());
            bundle.putParcelable("uri", wallpaperBNRHelper.getUri());
            String externalParams = wallpaperBNRHelper.getExternalParams();
            bundle.putParcelable(AppSearchShortcutInfo.KEY_EXTRAS, !TextUtils.isEmpty(externalParams) ? WallpaperExtraBundleHelper.fromJson(externalParams) : null);
            Bundle sendEventToDressRoom = sendEventToDressRoom(wallpaperBNRHelper.getContext(), "custom_pack", bundle);
            return sendEventToDressRoom != null && sendEventToDressRoom.getBoolean("result", false);
        }

        private boolean setBitmap(WallpaperBNRHelper wallpaperBNRHelper) {
            Bitmap createBitmap;
            Uri uri;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Bitmap bitmapFromPath = BnRFileHelper.getBitmapFromPath(wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getSecurityLevel(), wallpaperBNRHelper.getFileSaveKey());
            if (bitmapFromPath == null) {
                return false;
            }
            try {
                Matrix matrix = new Matrix();
                matrix.postRotate(wallpaperBNRHelper.getRotationValue());
                createBitmap = Bitmap.createBitmap(bitmapFromPath, 0, 0, bitmapFromPath.getWidth(), bitmapFromPath.getHeight(), matrix, true);
            } catch (Exception e) {
                e.printStackTrace();
                wallpaperBNRHelper.addErrorDescription("setBitmap: Exception <" + e.getMessage() + ">");
            }
            if (createBitmap == null) {
                wallpaperBNRHelper.addErrorDescription("setBitmap: rotatedBitmap is null.");
                return false;
            }
            Rect cropHint = wallpaperBNRHelper.getCropHint();
            Rect rect = cropHint.isEmpty() ? null : cropHint;
            String externalParams = wallpaperBNRHelper.getExternalParams();
            Bundle fromJson = TextUtils.isEmpty(externalParams) ? null : WallpaperExtraBundleHelper.fromJson(externalParams);
            if (wallpaperBNRHelper.getMode() == 4) {
                String deviceType = wallpaperBNRHelper.getDeviceType();
                if (!TextUtils.isEmpty(deviceType) && (deviceType.equals("folder") || deviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                    if (fromJson == null) {
                        fromJson = new Bundle();
                    }
                    fromJson.putInt("orientation", wallpaperBNRHelper.getOrientation());
                }
            }
            int bitmap = WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setBitmap(createBitmap, rect, true, wallpaperBNRHelper.getWhich(), fromJson);
            if (WallpaperBackupRestoreManager.DEBUG) {
                Log.d(WallpaperBackupRestoreManager.TAG, "setBitmap: Elapsed Time\t\t [" + (SystemClock.elapsedRealtime() - elapsedRealtime) + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (bitmap > 0) {
                if (wallpaperBNRHelper.isDownloadedThemeWallpaper() && (uri = wallpaperBNRHelper.getUri()) != null) {
                    WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).semSetUri(uri, true, wallpaperBNRHelper.getWhich());
                }
                return true;
            }
            return false;
        }

        private boolean setStream(WallpaperBNRHelper wallpaperBNRHelper) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            InputStream inputStreamFromPath = BnRFileHelper.getInputStreamFromPath(wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getSecurityLevel(), wallpaperBNRHelper.getFileSaveKey());
            if (inputStreamFromPath == null) {
                wallpaperBNRHelper.addErrorDescription("setStream: inputStream is null. path = <" + wallpaperBNRHelper.getSourceFilePath() + ">");
                return false;
            }
            try {
                Rect cropHint = wallpaperBNRHelper.getCropHint();
                Rect rect = (cropHint == null || !cropHint.isEmpty()) ? cropHint : null;
                String externalParams = wallpaperBNRHelper.getExternalParams();
                Bundle fromJson = TextUtils.isEmpty(externalParams) ? null : WallpaperExtraBundleHelper.fromJson(externalParams);
                if (wallpaperBNRHelper.isHomeAndLockPaired()) {
                    wallpaperBNRHelper.setWhich(wallpaperBNRHelper.getWhich() | 2);
                }
                int stream = WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setStream(inputStreamFromPath, rect, true, wallpaperBNRHelper.getWhich(), 0, false, fromJson);
                if (WallpaperBackupRestoreManager.DEBUG) {
                    Log.d(WallpaperBackupRestoreManager.TAG, "setStream: Elapsed Time\t\t [" + (SystemClock.elapsedRealtime() - elapsedRealtime) + NavigationBarInflaterView.SIZE_MOD_END);
                }
                if (stream <= 0) {
                    wallpaperBNRHelper.addErrorDescription("setStream: WallpaperManager.setStream returns " + stream);
                    return false;
                }
                BnRFileHelper.closeSilently(inputStreamFromPath);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                wallpaperBNRHelper.addErrorDescription("setStream: Exception <" + e.getMessage() + ">");
                return false;
            } finally {
                BnRFileHelper.closeSilently(inputStreamFromPath);
            }
        }

        private boolean setVideoWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper.isDownloadedThemeWallpaper()) {
                if (TextUtils.isEmpty(wallpaperBNRHelper.getPackageName())) {
                    return false;
                }
                WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setVideoLockscreenWallpaper((String) null, wallpaperBNRHelper.getPackageName(), (String) null, wallpaperBNRHelper.getWhich(), true);
                return true;
            }
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && wallpaperBNRHelper.getType() == 1 && wallpaperBNRHelper.getMode() == 16) {
                return setCoverVideoWallpaper(wallpaperBNRHelper);
            }
            if (!BnRFileHelper.copyEncryptFile(wallpaperBNRHelper.getSourceFilePath(), wallpaperBNRHelper.getTargetFilePath(), wallpaperBNRHelper.getFileSaveKey())) {
                return false;
            }
            WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setVideoLockscreenWallpaper(wallpaperBNRHelper.getTargetFilePath(), (String) null, (String) null, wallpaperBNRHelper.getWhich(), true);
            return true;
        }

        private boolean setCoverVideoWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("which", wallpaperBNRHelper.getWhich());
                bundle.putInt("type", wallpaperBNRHelper.getWallpaperType());
                bundle.putString("source_path", wallpaperBNRHelper.getSourceFilePath());
                bundle.putParcelable("crop_rect", wallpaperBNRHelper.getCropHint());
                bundle.putString("key", wallpaperBNRHelper.getFileSaveKey());
                Bundle sendEventToDressRoom = sendEventToDressRoom(wallpaperBNRHelper.getContext(), "custom_pack", bundle);
                if (sendEventToDressRoom != null) {
                    if (sendEventToDressRoom.getBoolean("result", false)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.e(WallpaperBackupRestoreManager.TAG, "restoreWallpaper: Exception " + e.getMessage());
            }
            return false;
        }

        private boolean setGifWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && wallpaperBNRHelper.getType() == 1 && wallpaperBNRHelper.getMode() == 16) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("which", wallpaperBNRHelper.getWhich());
                    bundle.putInt("type", wallpaperBNRHelper.getWallpaperType());
                    bundle.putString("source_path", wallpaperBNRHelper.getSourceFilePath());
                    bundle.putString("key", wallpaperBNRHelper.getFileSaveKey());
                    bundle.putBoolean("is_single_gif", true);
                    Bundle sendEventToDressRoom = sendEventToDressRoom(wallpaperBNRHelper.getContext(), "custom_pack", bundle);
                    if (sendEventToDressRoom != null) {
                        if (sendEventToDressRoom.getBoolean("result", false)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    Log.e(WallpaperBackupRestoreManager.TAG, "setGifWallpaper: Exception " + e.getMessage());
                }
            }
            return false;
        }

        private boolean setMultipackWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (wallpaperBNRHelper == null) {
                return false;
            }
            Uri uri = wallpaperBNRHelper.getUri();
            if (uri == null) {
                wallpaperBNRHelper.addErrorDescription("setMultipackWallpaper: uri is null.");
                return false;
            }
            if (!uri.getScheme().equals(WallpaperManager.SEM_SCHEME_MULTIPACK)) {
                wallpaperBNRHelper.addErrorDescription("setMultipackWallpaper: uri sheme is not multipack.");
                return false;
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
                wallpaperBNRHelper.addErrorDescription("setMultipackWallpaper: " + e.getMessage());
            }
            if (wallpaperBNRHelper.isDownloadedThemeWallpaper()) {
                WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).semSetUri(uri, true, wallpaperBNRHelper.getWhich(), 3);
                return true;
            }
            if (wallpaperBNRHelper.getUri() != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("uri", wallpaperBNRHelper.getUri());
                bundle.putInt("which", wallpaperBNRHelper.getWhich());
                bundle.putInt("type", wallpaperBNRHelper.getWallpaperType());
                bundle.putString("source_path", wallpaperBNRHelper.getSourceFilePath());
                bundle.putString("key", wallpaperBNRHelper.getFileSaveKey());
                if (sendEventToDressRoom(wallpaperBNRHelper.getContext(), "custom_pack", bundle).getBoolean("result", false)) {
                    return true;
                }
            }
            return false;
        }

        private boolean setMotionWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (!wallpaperBNRHelper.isDownloadedThemeWallpaper() || TextUtils.isEmpty(wallpaperBNRHelper.getPackageName())) {
                return false;
            }
            WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setMotionWallpaper(wallpaperBNRHelper.getPackageName(), wallpaperBNRHelper.getWhich(), true);
            return true;
        }

        private boolean setAnimatedWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            if (!wallpaperBNRHelper.isDownloadedThemeWallpaper() || TextUtils.isEmpty(wallpaperBNRHelper.getPackageName())) {
                return false;
            }
            try {
                WallpaperManager.getInstance(wallpaperBNRHelper.getContext()).setAnimatedLockscreenWallpaper(wallpaperBNRHelper.getPackageName(), wallpaperBNRHelper.getWhich(), true);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                wallpaperBNRHelper.addErrorDescription("setAnimatedWallpaper: " + e.getMessage());
                return false;
            }
        }

        private boolean setLiveWallpaper(WallpaperBNRHelper wallpaperBNRHelper) {
            try {
                Bundle bundle = new Bundle();
                Bundle fromJson = WallpaperExtraBundleHelper.fromJson(wallpaperBNRHelper.getExternalParams());
                if (fromJson != null) {
                    bundle.putString("contentType", fromJson.getString("contentType"));
                    bundle.putBundle(SliceItem.FORMAT_BUNDLE, fromJson);
                }
                bundle.putInt("which", wallpaperBNRHelper.getWhich());
                bundle.putInt("type", wallpaperBNRHelper.getWallpaperType());
                bundle.putString("source_path", wallpaperBNRHelper.getSourceFilePath());
                bundle.putBoolean("is_paired", wallpaperBNRHelper.isHomeAndLockPaired());
                bundle.putString("key", wallpaperBNRHelper.getFileSaveKey());
                Log.d(WallpaperBackupRestoreManager.TAG, "setLiveWallpaper: param = " + bundle);
                Bundle sendEventToDressRoom = sendEventToDressRoom(wallpaperBNRHelper.getContext(), "layered_image", bundle);
                if (sendEventToDressRoom != null) {
                    if (sendEventToDressRoom.getBoolean("result", false)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                wallpaperBNRHelper.addErrorDescription("setLiveWallpapr: " + e.getMessage());
            }
            return false;
        }

        private void requestResetEditInfo(Context context, int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("which", i);
            sendEventToDressRoom(context, "reset_edit_info", bundle);
        }

        private Bundle sendEventToDressRoom(Context context, String str, Bundle bundle) {
            return context.getContentResolver().call(Uri.parse("content://com.samsung.android.app.dressroom.provider"), str, (String) null, bundle);
        }

        private boolean isSourceFileValid(WallpaperBNRHelper wallpaperBNRHelper) {
            if (TextUtils.isEmpty(wallpaperBNRHelper.getSourceFilePath())) {
                wallpaperBNRHelper.addErrorDescription("isSourceFileValid: Source file path is empty.");
                return false;
            }
            if (BnRFileHelper.isExist(wallpaperBNRHelper.getSourceFilePath())) {
                return true;
            }
            wallpaperBNRHelper.addErrorDescription("isSourceFileValid: Source file not exist. path = <" + wallpaperBNRHelper.getSourceFilePath() + ">");
            return false;
        }

        private boolean isPackageInstalled(WallpaperBNRHelper wallpaperBNRHelper) {
            String packageName = wallpaperBNRHelper.getPackageName();
            if (Build.VERSION.SEM_PLATFORM_INT < 150000) {
                if (TextUtils.isEmpty(packageName)) {
                    return false;
                }
                try {
                    wallpaperBNRHelper.getContext().getPackageManager().getPackageInfo(packageName, 0);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (new File(APKContents.getMainThemePackagePath(packageName)).exists()) {
                return true;
            }
            return false;
        }
    }
}
