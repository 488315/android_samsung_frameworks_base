package com.samsung.android.wallpaperbackup;

import android.app.WallpaperManager;
import android.app.slice.SliceItem;
import android.content.APKContents;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.AppSearchShortcutInfo;
import android.content.p002pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.net.Uri;
import android.p009os.AsyncTask;
import android.p009os.Build;
import android.p009os.Bundle;
import android.p009os.ParcelFileDescriptor;
import android.p009os.SystemClock;
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
import java.util.Set;

/* loaded from: classes5.dex */
public class WallpaperBackupRestoreManager {
    private static final String RESTORE_TYPE_COVER = "RESTORE_TYPE_COVER";
    private static final String RESTORE_TYPE_MAIN = "RESTORE_TYPE_MAIN";
    private int[] mModeFlagSet = {4, 16, 8, 32};
    private static final String TAG = WallpaperBackupRestoreManager.class.getSimpleName();
    private static boolean DEBUG = true;

    private enum ResultCode {
        INVALID_VALUE(-1),
        RESULT_SUCCESS(0),
        RESULT_FAIL(1);

        private int code;

        ResultCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public void startBackupWallpaper(Context context, String basePath, String source) {
        startBackupWallpaper(context, 1, basePath, source, 0, "", "");
    }

    public void startBackupWallpaper(Context context, int which, String basePath, String source, int securityLevel, String sessionTime, String saveKey) {
        startBackupWallpaper(context, "", which, basePath, source, securityLevel, sessionTime, saveKey);
    }

    public void startBackupWallpaper(Context context, String action, int which, String basePath, String source, int securityLevel, String sessionTime, String saveKey) {
        String action2;
        String basePath2 = basePath;
        String str = TAG;
        Slog.m113d(str, "startBackupWallpaper which = " + Integer.toHexString(which) + " action= " + action + " basePath=" + basePath2 + " source=" + source);
        if (!TextUtils.isEmpty(action)) {
            action2 = action;
        } else if ((which & 1) != 0) {
            action2 = BnRConstants.RESPONSE_BACKUP_WALLPAPER;
        } else {
            action2 = BnRConstants.RESPONSE_BACKUP_LOCKSCREEN;
        }
        if (!basePath2.endsWith(File.separator)) {
            basePath2 = basePath2 + File.separator;
        }
        BnRFileHelper.ErrorCode errorCode = BnRFileHelper.checkSaveAvailable(basePath2);
        if (!errorCode.equals(BnRFileHelper.ErrorCode.ERROR_NONE)) {
            HashMap<Integer, ResultCode> extraResults = new HashMap<>();
            ResultCode err = ResultCode.INVALID_VALUE;
            extraResults.put(Integer.valueOf(which), err);
            Slog.m113d(str, "startBackupWallpaper is return because precondition fail");
            sendResponse(context, which, action2, ResultCode.RESULT_FAIL, errorCode, BnRFileHelper.REQ_MINIMUM_SIZE, source, sessionTime, extraResults, null);
            return;
        }
        pushBackupFile(context, action2, which, basePath2, securityLevel, saveKey, sessionTime, source);
    }

    private void pushBackupFile(Context context, String action, int which, String basePath, int securityLevel, String saveKey, String sessionTime, String source) {
        ArrayList<WallpaperBNRHelper> helpers = new ArrayList<>();
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        int i = 0;
        while (true) {
            int[] iArr = this.mModeFlagSet;
            if (i < iArr.length) {
                if (isSupportedScreen(iArr[i], which)) {
                    if (BnRConstants.BNR_SOURCE_SCLOUD.equals(source) && wallpaperManager.isSystemAndLockPaired(this.mModeFlagSet[i]) && which == 2 && wallpaperManager.semGetWallpaperType(which) == 7) {
                        Log.m98i(TAG, "pushBackupFile() : home and lock layered wallpaper is paired for mode " + this.mModeFlagSet[i] + ", backup home wallpaper");
                        int homeWhich = this.mModeFlagSet[i] | 1;
                        WallpaperBNRHelper helper = new WallpaperBNRHelper(context, wallpaperManager, action, homeWhich, basePath, securityLevel, saveKey, sessionTime, source);
                        helper.setWhich(this.mModeFlagSet[i] | which);
                        helpers.add(helper);
                    } else {
                        helpers.add(new WallpaperBNRHelper(context, wallpaperManager, action, which | this.mModeFlagSet[i], basePath, securityLevel, saveKey, sessionTime, source));
                    }
                }
                i++;
            } else {
                WallpaperBackupAsyncTask task = new WallpaperBackupAsyncTask();
                task.execute(helpers);
                return;
            }
        }
    }

    public void startRestoreWallpaper(Context context, String basePath, String source) {
        startRestoreWallpaper(context, 1, basePath, source, 0, "", null);
    }

    public void startRestoreWallpaper(Context context, int which, String basePath, String source, int securityLevel, String saveKey, String restoreScreen) {
        startRestoreWallpaper(context, "", which, basePath, source, securityLevel, saveKey, restoreScreen);
    }

    public void startRestoreWallpaper(Context context, String action, int which, String basePath, String source, int securityLevel, String saveKey, String restoreScreen) {
        String action2;
        String basePath2 = basePath;
        String str = TAG;
        Slog.m113d(str, "startRestoreWallpaper: which = " + Integer.toHexString(which) + " action = " + action + " basePath = " + basePath2 + " source = " + source + " securityLevel = " + securityLevel + " restoreScreen = " + restoreScreen);
        if (!TextUtils.isEmpty(action)) {
            action2 = action;
        } else if ((which & 1) != 0) {
            action2 = BnRConstants.RESPONSE_RESTORE_WALLPAPER;
        } else {
            action2 = BnRConstants.RESPONSE_RESTORE_LOCKSCREEN;
        }
        if (!basePath2.endsWith(File.separator)) {
            basePath2 = basePath2 + File.separator;
        }
        BnRFileHelper.ErrorCode errorCode = BnRFileHelper.checkSaveAvailable(basePath2);
        if (!errorCode.equals(BnRFileHelper.ErrorCode.ERROR_NONE)) {
            ResultCode err = ResultCode.INVALID_VALUE;
            HashMap<Integer, ResultCode> extraResults = new HashMap<>();
            extraResults.put(Integer.valueOf(which), err);
            Slog.m113d(str, "startRestoreWallpaper is return because precondition fail");
            sendResponse(context, which, action2, ResultCode.RESULT_FAIL, errorCode, BnRFileHelper.REQ_MINIMUM_SIZE, source, null, extraResults, null);
            return;
        }
        pushRestoreFile(context, action2, which, basePath2, securityLevel, saveKey, source, restoreScreen);
    }

    private void pushRestoreFile(Context context, String action, int which, String basePath, int securityLevel, String saveKey, String source, String restoreScreen) {
        ArrayList<WallpaperBNRHelper> helpers = new ArrayList<>();
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        int i = 0;
        while (true) {
            int[] iArr = this.mModeFlagSet;
            if (i >= iArr.length) {
                break;
            }
            if (isSupportedScreen(iArr[i], which)) {
                WallpaperBNRHelper helper = new WallpaperBNRHelper(context, wallpaperManager, action, which | this.mModeFlagSet[i], basePath, securityLevel, saveKey, "", source);
                if (handleDifferentTypeRestore(helper, this.mModeFlagSet[i], which, restoreScreen)) {
                    helpers.add(helper);
                }
            }
            i++;
        }
        int i2 = helpers.size();
        if (i2 == 0) {
            Log.m94d(TAG, "pushRestoreFile: Nothing to restore.");
            WallpaperBNRHelper helper2 = new WallpaperBNRHelper(context, wallpaperManager, action, which, basePath, securityLevel, saveKey, "", source);
            helper2.setResultCode(ResultCode.RESULT_FAIL);
            ArrayList<WallpaperBNRHelper> responseList = new ArrayList<>();
            responseList.add(helper2);
            response(responseList);
            return;
        }
        if (!isRestorableDeviceType(helpers)) {
            ArrayList<WallpaperBNRHelper> responseList2 = new ArrayList<>();
            responseList2.add(helpers.get(0));
            response(responseList2);
        } else {
            WallpaperRestoreAsyncTask task = new WallpaperRestoreAsyncTask();
            task.execute(helpers);
        }
    }

    private boolean handleDifferentTypeRestore(WallpaperBNRHelper helper, int mode, int which, String restoreScreen) {
        if (Rune.isFolder() && !"folder".equals(helper.getDeviceType())) {
            if (mode == 16) {
                Log.m98i(TAG, "skip restoring sub display of phone models");
                return false;
            }
            if (mode == 4) {
                helper.setWhich(which | 16);
                Log.m98i(TAG, "restoring main display of phone model to sub display of fold model");
                return true;
            }
        }
        if (!Rune.isFolder() && !Rune.isTablet()) {
            if ("folder".equals(helper.getDeviceType())) {
                if (mode == 16) {
                    if (!RESTORE_TYPE_COVER.equals(restoreScreen)) {
                        return false;
                    }
                    helper.setWhich(which | 4);
                    Log.m98i(TAG, "restoring sub display of fold model to phone");
                    return true;
                }
                if (mode == 4) {
                    Log.m98i(TAG, "do not restore main display of fold model to phone");
                    return false;
                }
            } else if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                if (mode == 16 && (Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY ^ BnRConstants.COVER_TYPE_LARGE_SCREEN.equals(helper.getCoverType()))) {
                    Log.m98i(TAG, "skip different size cover screen");
                    return false;
                }
            } else if (mode == 16) {
                Log.m98i(TAG, "skip sub display for phones");
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:23:0x002a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean isSupportedScreen(int mode, int type) {
        switch (mode) {
            case 8:
            default:
                return true;
            case 16:
                if (Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE && !Rune.isTablet()) {
                    return true;
                }
                if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
                    return false;
                }
                if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (type & 1) == 0) {
                    return false;
                }
                break;
            case 32:
                if (!Rune.VIRTUAL_DISPLAY_WALLPAPER || (type & 1) == 0) {
                    return false;
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean isRestorableDeviceType(ArrayList<WallpaperBNRHelper> helpers) {
        char c;
        WallpaperBNRHelper helper = helpers.get(0);
        String deviceTypeBackup = helper.getDeviceType();
        if (TextUtils.isEmpty(deviceTypeBackup)) {
            return true;
        }
        switch (deviceTypeBackup.hashCode()) {
            case -1268966290:
                if (deviceTypeBackup.equals("folder")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -881377690:
                if (deviceTypeBackup.equals(BnRConstants.DEVICETYPE_TABLET)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 106642798:
                if (deviceTypeBackup.equals("phone")) {
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
                if (Rune.isFolder()) {
                    return true;
                }
                if (!Rune.isTablet() && Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE) {
                    return true;
                }
                break;
            case 1:
                if (Rune.isTablet()) {
                    return true;
                }
                break;
            case 2:
                if (!Rune.isFolder() && !Rune.isTablet()) {
                    return true;
                }
                if (Rune.isFolder() && Rune.BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE) {
                    return true;
                }
                break;
            default:
                Log.m94d(TAG, "isRestorableDeviceType: deviceTypeBackup = " + deviceTypeBackup);
                break;
        }
        Slog.m113d(TAG, "isRestorableDeviceType: Device type not matching");
        helper.setResultCode(ResultCode.RESULT_FAIL);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void response(ArrayList<WallpaperBNRHelper> helpers) {
        BnRFileHelper.ErrorCode errorCode = BnRFileHelper.ErrorCode.ERROR_NONE;
        int successCount = 0;
        int failCount = 0;
        HashMap<Integer, ResultCode> extraResults = new HashMap<>();
        List<String> packageList = new ArrayList<>();
        Iterator<WallpaperBNRHelper> it = helpers.iterator();
        while (it.hasNext()) {
            WallpaperBNRHelper helper = it.next();
            extraResults.put(Integer.valueOf(helper.getWhich()), helper.getResultCode());
            if (helper.getResultCode() == ResultCode.RESULT_SUCCESS) {
                successCount++;
            } else {
                failCount++;
            }
            if (helper.isBackupCase() && !TextUtils.isEmpty(helper.getPackageName())) {
                packageList.add(helper.getPackageName());
            }
        }
        if (successCount == 0) {
            errorCode = BnRFileHelper.ErrorCode.INVALID_DATA;
        } else if (failCount > 0) {
            errorCode = BnRFileHelper.ErrorCode.PARTIAL_SUCCESS;
        }
        WallpaperBNRHelper helper2 = helpers.get(0);
        sendResponse(helper2.getContext(), helper2.getType(), helper2.getResponseAction(), successCount == 0 ? ResultCode.RESULT_FAIL : ResultCode.RESULT_SUCCESS, errorCode, BnRFileHelper.REQ_MINIMUM_SIZE, helper2.getSource(), helper2.getSessionTime(), extraResults, packageList);
    }

    private static void sendResponse(Context context, int which, String action, ResultCode resultCode, BnRFileHelper.ErrorCode errorCode, int requiredSize, String source, String sessionTime, HashMap extraResultCodes, List<String> packageList) {
        StringBuffer logBuffer = new StringBuffer();
        logBuffer.append("sendResponse:\n\t\twhich \t\t\t\t= " + which).append("\n\t\taction \t\t\t\t= " + action).append("\n\t\tresultCode \t\t\t= " + resultCode).append("\n\t\terrorCode \t\t\t= " + errorCode).append("\n\t\trequiredSize \t\t= " + requiredSize).append("\n\t\tsesstionTime \t\t= " + sessionTime).append("\n\t\tsource \t\t\t\t= " + source);
        if (extraResultCodes != null) {
            Set<Integer> keys = extraResultCodes.keySet();
            for (Integer key : keys) {
                logBuffer.append("\n\t\textraResultCode \t= ");
                logBuffer.append(String.format("%2d", key) + ": " + extraResultCodes.get(key));
            }
        }
        if (packageList != null && packageList.size() > 0) {
            logBuffer.append("\n\t\tpackages \t\t\t\t= " + Arrays.toString(packageList.toArray()));
        }
        String str = TAG;
        Slog.m113d(str, logBuffer.toString());
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(BnRConstants.RESULT_KEY, resultCode.getCode());
        intent.putExtra(BnRConstants.ERROR_KEY, errorCode.getCode());
        intent.putExtra(BnRConstants.REQUIRED_SIZE_KEY, requiredSize);
        intent.putExtra(BnRConstants.SOURCE_KEY, source);
        if (!TextUtils.isEmpty(sessionTime)) {
            intent.putExtra(BnRConstants.SESSION_TIME_KEY, sessionTime);
        }
        if (errorCode == BnRFileHelper.ErrorCode.PARTIAL_SUCCESS) {
            intent.putExtra(BnRConstants.EXTRA_ERR_CODE, extraResultCodes);
        }
        if (packageList != null && packageList.size() > 0) {
            intent.putStringArrayListExtra("EXTRA", (ArrayList) packageList);
        }
        context.sendBroadcast(intent, BnRConstants.PERMISSION_COM_WSSNPS);
        Slog.m113d(str, "sendBroadcast. " + which);
    }

    static class WallpaperBNRHelper {
        private static final String TAG = WallpaperBNRHelper.class.getSimpleName();
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

        WallpaperBNRHelper(Context context, WallpaperManager wallpaperManager, String action, int which, String basePath, int securityLevel, String saveKey, String sessionTime, String source) {
            this.mContext = context;
            if (!TextUtils.isEmpty(action) && action.contains("BACKUP")) {
                this.mIsBackupCase = true;
                Log.m94d(TAG, "WallpaperBNRHelper: Set backup case true.");
            }
            this.mWhich = which;
            this.mAction = action;
            this.mBasePath = basePath;
            this.mSessionTime = sessionTime;
            this.mSource = source;
            this.mSecurityLevel = securityLevel;
            this.mSaveKey = securityLevel == 1 ? saveKey : "";
            if (!TextUtils.isEmpty(basePath) && !this.mBasePath.endsWith(File.separator)) {
                this.mBasePath += File.separator;
            }
            this.mMode = which & 60;
            this.mType = which & 3;
            this.mWallpaperManager = wallpaperManager;
            setSettingsName();
            if (this.mIsBackupCase) {
                createBackupInfo();
            } else {
                createRestoreInfo();
            }
        }

        private String extractPackageName(Uri uri) {
            if (uri != null) {
                try {
                    String path = uri.getPath();
                    if (!TextUtils.isEmpty(path)) {
                        String[] splitStr = path.split("homewallpaper/");
                        if (!TextUtils.isEmpty(splitStr[1])) {
                            String[] finals = splitStr[1].split("/");
                            if (!TextUtils.isEmpty(finals[0])) {
                                return finals[0];
                            }
                            return null;
                        }
                        return null;
                    }
                    return null;
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.m96e(TAG, "extractPackageName: uri = " + uri + ", error = " + e.getMessage());
                    return null;
                }
            }
            return null;
        }

        private void createBackupInfoDownloadedTheme() {
            switch (this.mWallpaperType) {
                case 0:
                    this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(this.mWhich);
                    Uri semGetUri = this.mWallpaperManager.semGetUri(this.mWhich);
                    this.mUri = semGetUri;
                    this.mPackageName = extractPackageName(semGetUri);
                    break;
                case 1:
                    this.mPackageName = this.mWallpaperManager.getMotionWallpaperPkgName(this.mWhich);
                    break;
                case 3:
                    Uri semGetUri2 = this.mWallpaperManager.semGetUri(this.mWhich);
                    this.mUri = semGetUri2;
                    if (semGetUri2 != null) {
                        this.mPackageName = semGetUri2.getHost();
                        break;
                    }
                    break;
                case 4:
                    this.mPackageName = this.mWallpaperManager.getAnimatedPkgName(this.mWhich);
                    break;
                case 8:
                    this.mPackageName = this.mWallpaperManager.getVideoPackage(this.mWhich);
                    break;
            }
        }

        private void createBackupInfo() {
            SensorManager sensorManager;
            if (!this.mWallpaperManager.isWallpaperDataExists(this.mWhich)) {
                Log.m94d(TAG, "createBackupInfo: WallpaperData for [" + this.mWhich + "] does not exist.");
                addErrorDescription("WallpaperData for [" + this.mWhich + "] does not exist.");
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
                Log.m94d(TAG, "createBackupInfo: Not allowed to backup wallpaper");
            }
            if (this.mIsDownloadedThemeWallpaper) {
                createBackupInfoDownloadedTheme();
                return;
            }
            this.mFilePath = getFilePath(null);
            this.mTargetFilePath = this.mBasePath + this.mFilePath;
            if (this.mType == 1 && this.mMode != 8 && (sensorManager = (SensorManager) this.mContext.getSystemService(Context.SENSOR_SERVICE)) != null) {
                Sensor gyroSensor = sensorManager.getDefaultSensor(65579);
                if (gyroSensor != null) {
                    this.mTiltValue = Settings.System.getInt(this.mContext.getContentResolver(), BnRConstants.SETTINGS_WALLPAPER_TILT_STATUS, 0);
                }
            }
            Bundle extrasBundle = this.mWallpaperManager.getWallpaperExtras(this.mWhich, this.mContext.getUserId());
            if (extrasBundle != null) {
                this.mExternalParams = WallpaperExtraBundleHelper.toJson(extrasBundle);
            }
            switch (this.mWallpaperType) {
                case 0:
                    if (this.mType == 2 && this.mWallpaperManager.isSystemAndLockPaired(this.mMode)) {
                        this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mMode | 1, false);
                    } else {
                        this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mWhich, false);
                    }
                    this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(this.mWhich);
                    if (getMode() == 4 && !TextUtils.isEmpty(this.mDeviceType)) {
                        if (this.mDeviceType.equals("folder") || this.mDeviceType.equals(BnRConstants.DEVICETYPE_TABLET)) {
                            this.mOrientation = this.mWallpaperManager.getWallpaperOrientation(this.mWhich, this.mContext.getUserId());
                            break;
                        }
                    }
                    break;
                case 1:
                case 2:
                case 4:
                case 6:
                default:
                    Log.m96e(TAG, "createBackupInfo: Unhandled wallpaper type, mWallpaperType = " + this.mWallpaperType);
                    break;
                case 3:
                    Uri customPackUri = this.mWallpaperManager.semGetUri(this.mWhich);
                    if (customPackUri != null) {
                        this.mUri = customPackUri;
                        this.mSourceFilePath = BnRConstants.CUSTOM_MULTIPACK_SOURCE_PATH + customPackUri.getHost() + customPackUri.getPath();
                        break;
                    }
                    break;
                case 5:
                    Uri gifUri = this.mWallpaperManager.semGetUri(this.mWhich);
                    if (gifUri != null) {
                        this.mUri = gifUri;
                        this.mSourceFilePath = gifUri.getPath();
                        break;
                    }
                    break;
                case 7:
                    this.mIsHomeAndLockPaired = this.mWallpaperManager.isSystemAndLockPaired(WhichChecker.getMode(this.mWhich));
                    ComponentName cn = this.mWallpaperManager.semGetWallpaperComponent(this.mWhich, this.mContext.getUserId());
                    if (cn != null) {
                        this.mComponentName = cn.flattenToString();
                        break;
                    }
                    break;
                case 8:
                    this.mSourceFilePath = this.mWallpaperManager.getVideoFilePath(this.mWhich);
                    if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mMode == 16) {
                        this.mDescriptor = this.mWallpaperManager.getWallpaperFile(this.mWhich, this.mContext.getUserId(), -1);
                        this.mCropHint = this.mWallpaperManager.semGetWallpaperCropHint(this.mWhich);
                        break;
                    }
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void convertToImageWallpaperForSCloud() {
            addErrorDescription("convertImageWallpaperForSCloud: Backup with Samsung cloud, change layered type to image");
            this.mWallpaperType = 0;
            this.mFilePath = getFilePath(null);
            this.mTargetFilePath = this.mBasePath + this.mFilePath;
            int whichForAssets = isHomeAndLockPaired() ? this.mMode | 1 : this.mWhich;
            try {
                String fileName = this.mWallpaperManager.getWallpaperExtras(whichForAssets, this.mContext.getUserId()).getString(BnRConstants.KEY_REPRESENTATIVE_IMAGE_FILE);
                if (TextUtils.isEmpty(fileName)) {
                    Log.m98i(TAG, "representative file name is empty, try thumbnail file name and remove crop hints");
                    this.mExternalParams = null;
                    fileName = this.mWallpaperManager.getWallpaperExtras(whichForAssets, this.mContext.getUserId()).getString(BnRConstants.KEY_THUNBNAIL_FILENAME);
                }
                this.mDescriptor = this.mWallpaperManager.getWallpaperAssetFile(whichForAssets, this.mContext.getUserId(), fileName);
            } catch (Exception e) {
                addErrorDescription("Error occured getting representative file : " + e.toString() + ", try hard-coding way..");
                this.mDescriptor = this.mWallpaperManager.getWallpaperAssetFile(whichForAssets, this.mContext.getUserId(), "thumbnail.jpg");
            }
        }

        private void createRestoreInfo() {
            XmlParser xmlParser = getXmlParser();
            if (xmlParser == null) {
                addErrorDescription("createRestoreInfo: xmlParser is null.");
            }
            WallpaperUser wallpaperUser = xmlParser.getObject();
            if (wallpaperUser == null) {
                addErrorDescription("createRestoreInfo: Cannot create WallpaperUser for restoring.");
                return;
            }
            this.mWallpaperUser = wallpaperUser;
            this.mWallpaperType = wallpaperUser.getWpType();
            this.mRotation = wallpaperUser.getRotationValue();
            this.mTiltValue = wallpaperUser.getTiltSettingValue();
            this.mUri = wallpaperUser.getUri();
            this.mExternalParams = wallpaperUser.getExternalParams();
            this.mIsHomeAndLockPaired = wallpaperUser.getIsHomeAndLockPaired();
            this.mCropHint = new Rect(wallpaperUser.getLeftValue(), wallpaperUser.getTopValue(), wallpaperUser.getRightValue(), wallpaperUser.getBottomValue());
            this.mDeviceType = wallpaperUser.getDeviceType();
            this.mCoverType = wallpaperUser.getCoverType();
            if (Rune.isFolder() && TextUtils.isEmpty(wallpaperUser.getDeviceType()) && getMode() == 4) {
                if (this.mCropHint.isEmpty()) {
                    addErrorDescription("createRestoreInfo: Cannot identify device type.");
                } else {
                    int min = Math.min(this.mCropHint.width(), this.mCropHint.height());
                    int max = Math.max(this.mCropHint.width(), this.mCropHint.height());
                    float ratio = min / max;
                    if (ratio > 0.74f) {
                        addErrorDescription("createRestoreInfo: Consider backup device is table. ratio = " + ratio);
                        this.mDeviceType = "folder";
                        wallpaperUser.setDeviceType("folder");
                    }
                }
            }
            int min2 = this.mWallpaperType;
            if (min2 == 0 && getMode() == 4 && !TextUtils.isEmpty(this.mDeviceType) && (this.mDeviceType.equals("folder") || this.mDeviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                this.mOrientation = wallpaperUser.getOrientation();
            }
            boolean z = wallpaperUser.getTransparency() == 3;
            this.mIsDownloadedThemeWallpaper = z;
            if (z) {
                this.mPackageName = wallpaperUser.getComponent();
                switch (this.mWallpaperType) {
                    case 0:
                        Uri uri = wallpaperUser.getUri();
                        if (uri != null) {
                            this.mSourceFilePath = uri.getPath();
                            break;
                        }
                        break;
                }
            }
            String sourceFilePath = getSourceFilePath(wallpaperUser);
            this.mSourceFilePath = sourceFilePath;
            switch (this.mWallpaperType) {
                case 8:
                    String fileName = getFileNameFromPath(sourceFilePath);
                    if (TextUtils.isEmpty(fileName)) {
                        fileName = createVideoFileName();
                    } else if (Rune.SUPPORT_SUB_DISPLAY_MODE && getMode() == 4 && getType() == 2 && fileName.endsWith("_6.mp4")) {
                        fileName = fileName.replace("_6.mp4", "_2.mp4");
                    }
                    this.mTargetFilePath = BnRConstants.WALLPAPER_VIDEO_RESTORE_PATH + File.separator + fileName;
                    break;
            }
        }

        private void setSettingsName() {
            this.mSettingsName = "";
            switch (this.mMode) {
                case 4:
                    if (this.mType == 2) {
                        this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY;
                        break;
                    } else {
                        this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY;
                        break;
                    }
                case 8:
                    if (this.mType == 2) {
                        this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_DEX;
                        break;
                    } else {
                        this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_DEX;
                        break;
                    }
                case 16:
                    if (this.mType == 2) {
                        this.mSettingsName = BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_SUB_DISPLAY;
                        break;
                    } else {
                        this.mSettingsName = BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_SUB_DISPLAY;
                        break;
                    }
            }
        }

        private boolean isLiveWallpaper() {
            if (this.mType == 1) {
                return this.mWallpaperManager.isLiveWallpaper(this.mWhich);
            }
            return this.mWallpaperManager.isExternalLiveWallpaper(this.mWhich);
        }

        public boolean canBackup() {
            Uri uri;
            Bundle assetFiles;
            if (Build.VERSION.SEM_PLATFORM_INT > 150000 && this.mWallpaperType == 7) {
                try {
                    if (isHomeAndLockPaired()) {
                        assetFiles = this.mWallpaperManager.getWallpaperAssets(this.mMode | 1, this.mContext.getUserId());
                    } else {
                        assetFiles = this.mWallpaperManager.getWallpaperAssets(this.mWhich, this.mContext.getUserId());
                    }
                    if (assetFiles != null && assetFiles.keySet().size() > 0) {
                        if (!this.mIsBackupAllowed) {
                            addErrorDescription("Ignore mIsBackupAllowed = false in case custom live wallpaper.");
                            this.mIsBackupAllowed = true;
                        }
                    }
                    addErrorDescription("Live wallpaper is applied with NO asset files.");
                    return false;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (isLiveWallpaper()) {
                addErrorDescription("Live wallpaper is applied.");
                return false;
            }
            boolean z = this.mIsCustomWallpaper;
            if (!z || !this.mIsBackupAllowed) {
            }
            boolean canBackup = (z || this.mIsDownloadedThemeWallpaper) && this.mIsBackupAllowed;
            if (this.mWallpaperType == 3 && !canBackup && (uri = this.mUri) != null) {
                String stringUri = uri.toString();
                if (!TextUtils.isEmpty(stringUri) && stringUri.startsWith(BnRConstants.CUSTOM_PACK_PREFIX)) {
                    canBackup = true;
                }
            }
            Slog.m113d(TAG, "canBackup: which = " + this.mWhich + " canBackup = " + canBackup);
            return canBackup;
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

        public void setWhich(int which) {
            this.mWhich = which;
            this.mMode = which & 60;
            this.mType = which & 3;
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

        public void setResultCode(ResultCode code) {
            this.mResultCode = code;
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

        public void addErrorDescription(String error) {
            if (!TextUtils.isEmpty(error)) {
                this.mErrorDescriptions.add(error);
            }
        }

        @Deprecated
        public String getSourceFilePath(WallpaperUser user) {
            boolean needTocheckOriginalFile = false;
            if (user == null) {
                needTocheckOriginalFile = true;
            } else {
                int type = user.getWpType();
                if (type == 0 || type == -1) {
                    needTocheckOriginalFile = true;
                }
            }
            if (needTocheckOriginalFile) {
                String originalFilePath = getBasePath() + getOriginalFilePath();
                if (BnRFileHelper.isExist(originalFilePath)) {
                    this.mSourceFilePath = originalFilePath;
                    return originalFilePath;
                }
            }
            return getBasePath() + getFilePath(user);
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
            switch (i) {
                case 4:
                case 8:
                    StringBuilder append = new StringBuilder().append("wallpaper_original/");
                    if (this.mType != 2) {
                        str = BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
                    }
                    return append.append(str).toString();
                case 16:
                    StringBuilder append2 = new StringBuilder().append("wallpaper_sub_display_original/");
                    if (this.mType != 2) {
                        str = BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
                    }
                    return append2.append(str).toString();
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Deprecated
        public String getOriginalXmlFilePath() {
            switch (this.mMode) {
                case 4:
                case 8:
                    return this.mType == 2 ? BnRConstants.ORIGINAL_LOCK_XML_NAME : BnRConstants.ORIGINAL_XML_NAME;
                case 16:
                    return this.mType == 2 ? BnRConstants.SUB_DISPLAY_LOCK_XML_ORIGINAL_NAME : BnRConstants.SUB_DISPLAY_XML_ORIGINAL_NAME;
                case 32:
                    return this.mType == 2 ? "" : BnRConstants.VIRTUAL_DISPLAY_XML_NAME;
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getXmlFilePath() {
            switch (this.mMode) {
                case 4:
                    return this.mType == 2 ? BnRConstants.DEFAULT_LOCK_XML_NAME : BnRConstants.DEFAULT_XML_NAME;
                case 8:
                    String path = this.mType == 2 ? BnRConstants.DEX_LOCK_XML_NAME : BnRConstants.DEX_XML_NAME;
                    if (!this.mIsBackupCase && !new File(this.mBasePath + path).exists() && this.mType == 2) {
                        File checkFile = new File(this.mBasePath + BnRConstants.DEX_XML_NAME);
                        if (checkFile.exists() && checkFile.canRead()) {
                            return BnRConstants.DEX_XML_NAME;
                        }
                        return path;
                    }
                    return path;
                case 16:
                    return this.mType == 2 ? BnRConstants.SUB_DISPLAY_LOCK_XML_NAME : BnRConstants.SUB_DISPLAY_XML_NAME;
                case 32:
                    return this.mType == 2 ? "" : BnRConstants.VIRTUAL_DISPLAY_XML_NAME;
                default:
                    return null;
            }
        }

        private String getFilePath(WallpaperUser wallpaperUser) {
            if (wallpaperUser != null) {
                String path = wallpaperUser.getPath();
                if (!TextUtils.isEmpty(path)) {
                    return path;
                }
            }
            switch (this.mWallpaperType) {
                case -1:
                case 0:
                    return getImagePath();
                case 1:
                case 2:
                case 4:
                case 6:
                default:
                    Log.m96e(TAG, "getFilePath: mWallpaperType = " + this.mWallpaperType);
                    return null;
                case 3:
                    return getMultipackPath();
                case 5:
                    return getGifPath();
                case 7:
                    return getLiveWallpaperPath();
                case 8:
                    return getVideoPath();
            }
        }

        private String getGifPath() {
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                if (this.mMode == 16 && this.mType == 1) {
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_GIF_FILE_NAME;
                }
                Log.m94d(TAG, "getGifPath: Invalid which for gif wallpaper. mWhich = " + this.mWhich);
                return null;
            }
            if (Rune.VIRTUAL_DISPLAY_WALLPAPER) {
                if (this.mMode == 32 && this.mType == 1) {
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_GIF_FILE_NAME;
                }
                Log.m94d(TAG, "getGifPath: Invalid which for gif wallpaper. mWhich = " + this.mWhich);
                return null;
            }
            Log.m94d(TAG, "getGifPath: NOT SUPPORTED YET!");
            return null;
        }

        private String getImagePath() {
            String imageFileName = this.mType == 2 ? BnRConstants.LOCK_WALLPAPER_FILE_NAME : BnRConstants.WALLPAPER_IMAGE_FILE_NAME;
            switch (this.mMode) {
                case 4:
                    return "wallpaper" + File.separator + imageFileName;
                case 8:
                    return BnRConstants.DEX_FOLDER_NAME + File.separator + imageFileName;
                case 16:
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + imageFileName;
                case 32:
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + imageFileName;
                default:
                    return null;
            }
        }

        private String getMultipackPath() {
            switch (this.mMode) {
                case 4:
                    return "wallpaper" + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
                case 8:
                    return BnRConstants.DEX_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
                case 16:
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
                case 32:
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.CUSTOM_MULTIPACK_PATH;
                default:
                    return null;
            }
        }

        private String getVideoPath() {
            switch (this.mMode) {
                case 4:
                    return "wallpaper" + File.separator + getVideoFileName();
                case 8:
                    return BnRConstants.DEX_FOLDER_NAME + File.separator + getVideoFileName();
                case 16:
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.VIDEO_DIR_PATH + getVideoFileName();
                case 32:
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + getVideoFileName();
                default:
                    return null;
            }
        }

        private String getLiveWallpaperPath() {
            switch (this.mMode) {
                case 4:
                    return "wallpaper" + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
                case 8:
                    return BnRConstants.DEX_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
                case 16:
                    return BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
                case 32:
                    return BnRConstants.VIRTUAL_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.LIVE_WALLPAPER_ASSETS_PATH;
                default:
                    return null;
            }
        }

        private String getVideoFileName() {
            String returnFileName = "";
            try {
                String strPath = this.mWallpaperManager.getVideoFilePath(this.mWhich);
                returnFileName = getFileNameFromPath(strPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(returnFileName)) {
                String returnFileName2 = createVideoFileName();
                return returnFileName2;
            }
            return returnFileName;
        }

        private String createVideoFileName() {
            int which = this.mWhich;
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && getMode() == 4 && getType() == 2) {
                which = 2;
            }
            if (this.mContext != null) {
                return "video_wallpaper_" + this.mContext.getUserId() + Session.SESSION_SEPARATION_CHAR_CHILD + which + MediaMetrics.SEPARATOR + BnRConstants.VIDEO_FILE_EXTENSION;
            }
            Log.m94d(TAG, "createVideoFileName: context is null!");
            return "video_wallpaper_0_" + which + MediaMetrics.SEPARATOR + BnRConstants.VIDEO_FILE_EXTENSION;
        }

        private String getFileNameFromPath(String fullPath) {
            if (TextUtils.isEmpty(fullPath)) {
                return null;
            }
            return fullPath.substring(fullPath.lastIndexOf("/") + 1);
        }

        private XmlParser getXmlParser() {
            try {
                File xmlFile = new File(this.mBasePath, getOriginalXmlFilePath());
                if (xmlFile.exists()) {
                    return new XmlParser(this.mBasePath + getOriginalXmlFilePath());
                }
                return new XmlParser(this.mBasePath + getXmlFilePath());
            } catch (Exception e) {
                Log.m96e(TAG, "getXmlParser: " + e.getMessage());
                addErrorDescription("getXmlParser: " + e.getMessage());
                return null;
            }
        }

        private String getStringWhich() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(NavigationBarInflaterView.SIZE_MOD_START);
            switch (this.mMode) {
                case 4:
                    buffer.append("MAIN");
                    break;
                case 8:
                    buffer.append("DEX");
                    break;
                case 16:
                    buffer.append("SUB");
                    break;
                case 32:
                    buffer.append("VIRTUAL");
                    break;
            }
            switch (this.mType) {
                case 1:
                    buffer.append(" | HOME");
                    break;
                case 2:
                    buffer.append(" | LOCK");
                    break;
            }
            buffer.append(NavigationBarInflaterView.SIZE_MOD_END);
            return buffer.toString();
        }

        public void close() {
            BnRFileHelper.closeSilently(this.mDescriptor);
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("\n\tWallpaperBnRHelper:").append("\n\t\tmWhich = " + this.mWhich + ": " + getStringWhich()).append("\n\t\tmWallpaperType = " + this.mWallpaperType).append("\n\t\tmIsBackupCase = " + this.mIsBackupCase);
            if (this.mIsBackupCase) {
                buffer.append("\n\t\tmIsCustomWallpaper = " + this.mIsCustomWallpaper).append("\n\t\tmIsBackupAllowed = " + this.mIsBackupAllowed);
                buffer.append("\n\t\tmIsDownloadedThemeWallpaper = " + this.mIsDownloadedThemeWallpaper);
            }
            if (!TextUtils.isEmpty(this.mPackageName)) {
                buffer.append("\n\t\tmPackageName = " + this.mPackageName);
            }
            if (!TextUtils.isEmpty(this.mDeviceType)) {
                buffer.append("\n\t\tmDeviceType = " + this.mDeviceType);
            }
            buffer.append("\n\t\tmUri = " + this.mUri).append("\n\t\tmSaveType = " + this.mSaveType).append("\n\t\tmSecurityLevel = " + this.mSecurityLevel).append("\n\t\tmSaveKey = " + this.mSaveKey).append("\n\t\tmSessionTime = " + this.mSessionTime).append("\n\t\tmTiltValue = " + this.mTiltValue).append("\n\t\tmCropHint = " + this.mCropHint).append("\n\t\tmRotation = " + this.mRotation).append("\n\t\tmAction = " + this.mAction).append("\n\t\tmBasePath = " + this.mBasePath).append("\n\t\tmSource = " + this.mSource).append("\n\t\tmDescriptor = " + this.mDescriptor).append("\n\t\tmSourceFilePath = " + this.mSourceFilePath).append("\n\t\tmTargetFilePath = " + this.mTargetFilePath).append("\n\t\tmFilePath = " + this.mFilePath).append("\n\t\tmXmlPath = " + this.mXmlPath).append("\n\t\tmSettingsName = " + this.mSettingsName).append("\n\t\tmExternalParams = " + this.mExternalParams).append("\n\t\tmIsHomeAndLockPaired = " + this.mIsHomeAndLockPaired).append("\n\t\tmComponentName = " + this.mComponentName).append("\n\t\tmResultCode = " + this.mResultCode);
            if (this.mWallpaperUser != null) {
                buffer.append("\n\t\tmWallpaperUser = " + this.mWallpaperUser.toString());
            }
            if (this.mErrorDescriptions.size() > 0) {
                buffer.append("\n\t\tmErrorDescriptions = ");
                Iterator<String> it = this.mErrorDescriptions.iterator();
                while (it.hasNext()) {
                    String error = it.next();
                    buffer.append("\n\t\t\t" + error);
                }
            }
            return buffer.toString();
        }
    }

    static class WallpaperBackupAsyncTask extends AsyncTask<ArrayList, WallpaperBNRHelper, ArrayList<WallpaperBNRHelper>> {
        WallpaperBackupAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public ArrayList doInBackground(ArrayList... helpers) {
            ArrayList arrayList = helpers[0];
            for (int i = 0; i < arrayList.size(); i++) {
                WallpaperBNRHelper helper = (WallpaperBNRHelper) arrayList.get(i);
                cleanupFiles(helper);
                boolean backupSuccess = false;
                if (helper.canBackup()) {
                    if (helper.isDownloadedThemeWallpaper()) {
                        backupSuccess = true;
                    } else {
                        backupSuccess = backupWallpaper(helper);
                    }
                }
                if (backupSuccess) {
                    backupXml(helper);
                    helper.setResultCode(ResultCode.RESULT_SUCCESS);
                } else {
                    helper.setResultCode(ResultCode.RESULT_FAIL);
                }
                publishProgress(helper);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public void onProgressUpdate(WallpaperBNRHelper... helpers) {
            try {
                WallpaperBNRHelper helper = helpers[0];
                Slog.m113d(WallpaperBackupRestoreManager.TAG, "onProgressUpdate:" + helper.toString());
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public void onPostExecute(ArrayList<WallpaperBNRHelper> helpers) {
            WallpaperBackupRestoreManager.response(helpers);
        }

        private boolean backupWallpaper(WallpaperBNRHelper helper) {
            switch (helper.getWallpaperType()) {
                case -1:
                case 0:
                    if (helper.getDescriptor() == null) {
                        return false;
                    }
                    return BnRFileHelper.copyFile(helper.getTargetFilePath(), helper.getDescriptor(), helper.getFileSaveKey());
                case 1:
                case 2:
                case 4:
                case 6:
                default:
                    return false;
                case 3:
                    return BnRFileHelper.copyDir(helper.getTargetFilePath(), helper.getSourceFilePath(), helper.getFileSaveKey());
                case 5:
                    return BnRFileHelper.copyFile(helper.getTargetFilePath(), helper.getSourceFilePath(), helper.getFileSaveKey());
                case 7:
                    if (BnRConstants.BNR_SOURCE_SCLOUD.equals(helper.getSource())) {
                        helper.convertToImageWallpaperForSCloud();
                        if (helper.getDescriptor() == null) {
                            return false;
                        }
                        return BnRFileHelper.copyFile(helper.getTargetFilePath(), helper.getDescriptor(), helper.getFileSaveKey());
                    }
                    return BnRFileHelper.copyAssets(helper.getTargetFilePath(), helper.getWallpaperManager().getWallpaperAssets(helper.getWhich(), helper.getContext().getUserId()), helper.getFileSaveKey());
                case 8:
                    if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && helper.getMode() == 16) {
                        BnRFileHelper.copyFile(helper.getBasePath() + BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.VIDEO_DIR_PATH + BnRConstants.VIDEO_THUMBNAIL_FILE_NAME, helper.getDescriptor(), helper.getFileSaveKey());
                    }
                    return BnRFileHelper.copyFile(helper.getTargetFilePath(), helper.getSourceFilePath(), helper.getFileSaveKey());
            }
        }

        private void backupXml(WallpaperBNRHelper helper) {
            if (helper == null) {
                Log.m94d(WallpaperBackupRestoreManager.TAG, "backupXml: Cannot create backup xml file.");
                return;
            }
            WallpaperUser wallpaperUser = new WallpaperUser();
            int wallpaperType = helper.getWallpaperType();
            wallpaperUser.setWpType(wallpaperType);
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && helper.getMode() == 16 && wallpaperType == 8) {
                wallpaperUser.setPath(BnRConstants.SUB_DISPLAY_FOLDER_NAME + File.separator + BnRConstants.VIDEO_DIR_PATH);
            } else {
                wallpaperUser.setPath(helper.getFilePath());
            }
            wallpaperUser.setTiltSettingValue(helper.getTiltValue());
            if (helper.isDownloadedThemeWallpaper()) {
                wallpaperUser.setTransparency(3);
            }
            wallpaperUser.setDeviceType(helper.getDeviceType());
            wallpaperUser.setCoverType(helper.getCoverType());
            if (wallpaperType == 0 && helper.getMode() == 4) {
                String deviceType = helper.getDeviceType();
                if (!TextUtils.isEmpty(deviceType) && (deviceType.equals("folder") || deviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                    wallpaperUser.setOrientation(helper.getOrientation());
                }
            }
            if (!TextUtils.isEmpty(helper.getPackageName())) {
                wallpaperUser.setComponent(helper.getPackageName());
            }
            if (!TextUtils.isEmpty(helper.getComponentName())) {
                wallpaperUser.setComponentName(helper.getComponentName());
            }
            if (helper.getUri() != null) {
                wallpaperUser.setUri(helper.getUri());
            }
            if (!TextUtils.isEmpty(helper.getExternalParams())) {
                wallpaperUser.setExternalParams(helper.getExternalParams());
            }
            wallpaperUser.setIsHomeAndLockPaired(helper.isHomeAndLockPaired());
            Rect cropHint = helper.getCropHint();
            if (cropHint != null && !cropHint.isEmpty()) {
                WallpaperUser.WallpaperData wallpaperData = new WallpaperUser.WallpaperData();
                wallpaperData.left = cropHint.left;
                wallpaperData.right = cropHint.right;
                wallpaperData.top = cropHint.top;
                wallpaperData.bottom = cropHint.bottom;
                wallpaperUser.setWallpaperData(wallpaperData);
            }
            File xmlFile = new File(helper.getBasePath(), helper.getXmlFilePath());
            GenerateXML.generateXML(xmlFile, helper.getWhich(), wallpaperUser);
        }

        private void cleanupFiles(WallpaperBNRHelper helper) {
            String filePath = helper.getBasePath() + helper.getFilePath();
            String xmlPath = helper.getBasePath() + helper.getXmlFilePath();
            BnRFileHelper.deleteFile(filePath);
            BnRFileHelper.deleteFile(xmlPath);
            String originalFilePath = helper.getBasePath() + helper.getOriginalFilePath();
            String originalXmlPath = helper.getBasePath() + helper.getOriginalXmlFilePath();
            BnRFileHelper.deleteFile(originalFilePath);
            BnRFileHelper.deleteFile(originalXmlPath);
        }
    }

    static class WallpaperRestoreAsyncTask extends AsyncTask<ArrayList, WallpaperBNRHelper, ArrayList<WallpaperBNRHelper>> {
        WallpaperRestoreAsyncTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public ArrayList doInBackground(ArrayList... helpers) {
            ArrayList arrayList = helpers[0];
            for (int i = 0; i < arrayList.size(); i++) {
                WallpaperBNRHelper helper = (WallpaperBNRHelper) arrayList.get(i);
                int oldTransparencyValue = -1;
                if (helper.getMode() != 32) {
                    oldTransparencyValue = writeTransparencySettingValue(helper);
                }
                boolean retoreSuccess = restoreWallpaper(helper);
                if (retoreSuccess) {
                    int wallpaperType = helper.getWallpaperType();
                    if (wallpaperType != 3 && wallpaperType != 5 && (wallpaperType != 8 || !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || helper.getType() != 1 || helper.getMode() != 16)) {
                        requestResetEditInfo(helper.getContext(), helper.getWhich());
                    }
                    helper.setResultCode(ResultCode.RESULT_SUCCESS);
                    if (helper.getMode() != 32) {
                        writeSettingValue(helper);
                    }
                    if (Build.VERSION.SEM_PLATFORM_INT >= 140100) {
                        WallpaperManager.getInstance(helper.getContext()).semClearBackupWallpapers(helper.getWhich());
                    }
                } else {
                    if (oldTransparencyValue >= 0) {
                        writeTransparencySettingValue(helper, oldTransparencyValue);
                    }
                    helper.setResultCode(ResultCode.RESULT_FAIL);
                }
                publishProgress(helper);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public void onProgressUpdate(WallpaperBNRHelper... helpers) {
            try {
                WallpaperBNRHelper helper = helpers[0];
                Slog.m113d(WallpaperBackupRestoreManager.TAG, "onProgressUpdate:" + helper.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public void onPostExecute(ArrayList<WallpaperBNRHelper> helpers) {
            WallpaperBackupRestoreManager.response(helpers);
        }

        private int writeTransparencySettingValue(WallpaperBNRHelper helper) {
            if (helper == null) {
                return -1;
            }
            int transparency = 0;
            if (helper.isDownloadedThemeWallpaper()) {
                transparency = 3;
            }
            return writeTransparencySettingValue(helper, transparency);
        }

        private int writeTransparencySettingValue(WallpaperBNRHelper helper, int transparency) {
            if (helper == null) {
                return -1;
            }
            ContentResolver resolver = helper.getContext().getContentResolver();
            String settingsName = helper.getSettingsName();
            int oldValue = Settings.System.getIntForUser(resolver, settingsName, 1, -2);
            Settings.System.putIntForUser(resolver, settingsName, transparency, -2);
            return oldValue;
        }

        private void writeSettingValue(WallpaperBNRHelper helper) {
            SensorManager sensorManager;
            if (helper == null) {
                return;
            }
            if (helper.getType() == 2) {
                switch (helper.getMode()) {
                    case 4:
                        Settings.System.putIntForUser(helper.getContext().getContentResolver(), WallpaperManager.SETTINGS_LOCKSCREEN_WALLPAPER, 1, -2);
                        break;
                    case 16:
                        Settings.System.putIntForUser(helper.getContext().getContentResolver(), WallpaperManager.SETTINGS_LOCKSCREEN_WALLPAPER_SUB, 1, -2);
                        break;
                }
                return;
            }
            if (helper.getMode() != 8 && (sensorManager = (SensorManager) helper.getContext().getSystemService(Context.SENSOR_SERVICE)) != null) {
                Sensor gyroSensor = sensorManager.getDefaultSensor(65579);
                if (gyroSensor != null) {
                    Settings.System.putInt(helper.getContext().getContentResolver(), BnRConstants.SETTINGS_WALLPAPER_TILT_STATUS, helper.getTiltValue());
                }
            }
        }

        private boolean restoreWallpaper(WallpaperBNRHelper helper) {
            if (helper == null) {
                return false;
            }
            if (helper.isDownloadedThemeWallpaper()) {
                if (!isPackageInstalled(helper)) {
                    helper.addErrorDescription("restoreWallpaper: Package is not installed");
                    return false;
                }
            } else if (!isSourceFileValid(helper)) {
                return false;
            }
            switch (helper.getWallpaperType()) {
                case -1:
                case 0:
                    if (Build.VERSION.SEM_PLATFORM_INT >= 140100 && (WhichChecker.isWatchFaceDisplay(helper.getWhich()) || WhichChecker.isVirtualDisplay(helper.getWhich()))) {
                        break;
                    } else if (helper.getRotationValue() != 0 || helper.getSecurityLevel() != 0) {
                        break;
                    } else {
                        break;
                    }
                    break;
                case 1:
                    break;
                case 2:
                case 6:
                default:
                    helper.addErrorDescription("restoreWallpaper: Unhandled wallpaper type [" + helper.getWallpaperType() + "].");
                    break;
                case 3:
                    if (helper.getMode() != 8) {
                        break;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 7:
                    break;
                case 8:
                    break;
            }
            return false;
        }

        private boolean setImageWallpaperDroom(WallpaperBNRHelper helper) {
            Bundle param = new Bundle();
            param.putString("key", helper.getFileSaveKey());
            param.putInt("which", helper.getWhich());
            param.putInt("type", helper.getWallpaperType());
            param.putInt(GenerateXML.ROTATION, helper.getRotationValue());
            param.putString("source_path", helper.getSourceFilePath());
            param.putParcelable("crop_rect", helper.getCropHint());
            param.putParcelable("uri", helper.getUri());
            Bundle extrasBundle = null;
            String extrasStr = helper.getExternalParams();
            if (!TextUtils.isEmpty(extrasStr)) {
                extrasBundle = WallpaperExtraBundleHelper.fromJson(extrasStr);
            }
            param.putParcelable(AppSearchShortcutInfo.KEY_EXTRAS, extrasBundle);
            Bundle resultBundle = sendEventToDressRoom(helper.getContext(), "custom_pack", param);
            if (resultBundle == null || !resultBundle.getBoolean("result", false)) {
                return false;
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x00b0 A[Catch: Exception -> 0x00f6, TryCatch #0 {Exception -> 0x00f6, blocks: (B:6:0x001b, B:8:0x003c, B:10:0x0043, B:13:0x004e, B:15:0x005a, B:16:0x005f, B:18:0x0066, B:20:0x0070, B:22:0x0078, B:25:0x0083, B:26:0x0089, B:27:0x0096, B:29:0x00b0, B:31:0x00d8, B:33:0x00df, B:35:0x00e5), top: B:5:0x001b }] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00d8 A[Catch: Exception -> 0x00f6, TryCatch #0 {Exception -> 0x00f6, blocks: (B:6:0x001b, B:8:0x003c, B:10:0x0043, B:13:0x004e, B:15:0x005a, B:16:0x005f, B:18:0x0066, B:20:0x0070, B:22:0x0078, B:25:0x0083, B:26:0x0089, B:27:0x0096, B:29:0x00b0, B:31:0x00d8, B:33:0x00df, B:35:0x00e5), top: B:5:0x001b }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean setBitmap(WallpaperBNRHelper helper) {
            Bitmap rotatedBitmap;
            Bundle extrasBundle;
            int result;
            Uri uri;
            long startTime = SystemClock.elapsedRealtime();
            Bitmap bitmap = BnRFileHelper.getBitmapFromPath(helper.getSourceFilePath(), helper.getSecurityLevel(), helper.getFileSaveKey());
            if (bitmap == null) {
                return false;
            }
            try {
                Matrix matrix = new Matrix();
                matrix.postRotate(helper.getRotationValue());
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (Exception e) {
                e.printStackTrace();
                helper.addErrorDescription("setBitmap: Exception <" + e.getMessage() + ">");
            }
            if (rotatedBitmap == null) {
                helper.addErrorDescription("setBitmap: rotatedBitmap is null.");
                return false;
            }
            Rect visibleRect = helper.getCropHint();
            if (visibleRect.isEmpty()) {
                visibleRect = null;
            }
            Bundle extrasBundle2 = null;
            String extrasStr = helper.getExternalParams();
            if (!TextUtils.isEmpty(extrasStr)) {
                extrasBundle2 = WallpaperExtraBundleHelper.fromJson(extrasStr);
            }
            if (helper.getMode() == 4) {
                String deviceType = helper.getDeviceType();
                if (!TextUtils.isEmpty(deviceType) && (deviceType.equals("folder") || deviceType.equals(BnRConstants.DEVICETYPE_TABLET))) {
                    if (extrasBundle2 == null) {
                        extrasBundle2 = new Bundle();
                    }
                    extrasBundle2.putInt("orientation", helper.getOrientation());
                    extrasBundle = extrasBundle2;
                    result = WallpaperManager.getInstance(helper.getContext()).setBitmap(rotatedBitmap, visibleRect, true, helper.getWhich(), extrasBundle);
                    if (WallpaperBackupRestoreManager.DEBUG) {
                        Log.m94d(WallpaperBackupRestoreManager.TAG, "setBitmap: Elapsed Time\t\t [" + (SystemClock.elapsedRealtime() - startTime) + NavigationBarInflaterView.SIZE_MOD_END);
                    }
                    if (result > 0) {
                        if (helper.isDownloadedThemeWallpaper() && (uri = helper.getUri()) != null) {
                            WallpaperManager.getInstance(helper.getContext()).semSetUri(uri, true, helper.getWhich());
                        }
                        return true;
                    }
                    return false;
                }
            }
            extrasBundle = extrasBundle2;
            result = WallpaperManager.getInstance(helper.getContext()).setBitmap(rotatedBitmap, visibleRect, true, helper.getWhich(), extrasBundle);
            if (WallpaperBackupRestoreManager.DEBUG) {
            }
            if (result > 0) {
            }
            return false;
        }

        private boolean setStream(WallpaperBNRHelper helper) {
            Bundle extrasBundle;
            long startTime = SystemClock.elapsedRealtime();
            InputStream inputStream = BnRFileHelper.getInputStreamFromPath(helper.getSourceFilePath(), helper.getSecurityLevel(), helper.getFileSaveKey());
            try {
                if (inputStream == null) {
                    helper.addErrorDescription("setStream: inputStream is null. path = <" + helper.getSourceFilePath() + ">");
                    return false;
                }
                Rect visibleRect = helper.getCropHint();
                if (visibleRect != null && visibleRect.isEmpty()) {
                    visibleRect = null;
                }
                String extrasStr = helper.getExternalParams();
                if (TextUtils.isEmpty(extrasStr)) {
                    extrasBundle = null;
                } else {
                    Bundle extrasBundle2 = WallpaperExtraBundleHelper.fromJson(extrasStr);
                    extrasBundle = extrasBundle2;
                }
                int result = WallpaperManager.getInstance(helper.getContext()).setStream(inputStream, visibleRect, true, helper.getWhich(), 0, false, extrasBundle);
                if (WallpaperBackupRestoreManager.DEBUG) {
                    Log.m94d(WallpaperBackupRestoreManager.TAG, "setStream: Elapsed Time\t\t [" + (SystemClock.elapsedRealtime() - startTime) + NavigationBarInflaterView.SIZE_MOD_END);
                }
                if (result > 0) {
                    BnRFileHelper.closeSilently(inputStream);
                    return true;
                }
                helper.addErrorDescription("setStream: WallpaperManager.setStream returns " + result);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                helper.addErrorDescription("setStream: Exception <" + e.getMessage() + ">");
                return false;
            } finally {
                BnRFileHelper.closeSilently(inputStream);
            }
        }

        private boolean setVideoWallpaper(WallpaperBNRHelper helper) {
            if (helper.isDownloadedThemeWallpaper()) {
                if (TextUtils.isEmpty(helper.getPackageName())) {
                    return false;
                }
                WallpaperManager.getInstance(helper.getContext()).setVideoLockscreenWallpaper((String) null, helper.getPackageName(), (String) null, helper.getWhich(), true);
                return true;
            }
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && helper.getType() == 1 && helper.getMode() == 16) {
                return setCoverVideoWallpaper(helper);
            }
            boolean result = BnRFileHelper.copyEncryptFile(helper.getSourceFilePath(), helper.getTargetFilePath(), helper.getFileSaveKey());
            if (!result) {
                return false;
            }
            WallpaperManager.getInstance(helper.getContext()).setVideoLockscreenWallpaper(helper.getTargetFilePath(), (String) null, (String) null, helper.getWhich(), true);
            return true;
        }

        private boolean setCoverVideoWallpaper(WallpaperBNRHelper helper) {
            try {
                Bundle param = new Bundle();
                param.putInt("which", helper.getWhich());
                param.putInt("type", helper.getWallpaperType());
                param.putString("source_path", helper.getSourceFilePath());
                param.putParcelable("crop_rect", helper.getCropHint());
                param.putString("key", helper.getFileSaveKey());
                Bundle resultBundle = sendEventToDressRoom(helper.getContext(), "custom_pack", param);
                if (resultBundle != null) {
                    if (resultBundle.getBoolean("result", false)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.m96e(WallpaperBackupRestoreManager.TAG, "restoreWallpaper: Exception " + e.getMessage());
            }
            return false;
        }

        private boolean setGifWallpaper(WallpaperBNRHelper helper) {
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && helper.getType() == 1 && helper.getMode() == 16) {
                try {
                    Bundle param = new Bundle();
                    param.putInt("which", helper.getWhich());
                    param.putInt("type", helper.getWallpaperType());
                    param.putString("source_path", helper.getSourceFilePath());
                    param.putString("key", helper.getFileSaveKey());
                    param.putBoolean("is_single_gif", true);
                    Bundle resultBundle = sendEventToDressRoom(helper.getContext(), "custom_pack", param);
                    if (resultBundle != null) {
                        if (resultBundle.getBoolean("result", false)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    Log.m96e(WallpaperBackupRestoreManager.TAG, "setGifWallpaper: Exception " + e.getMessage());
                }
            }
            return false;
        }

        private boolean setMultipackWallpaper(WallpaperBNRHelper helper) {
            if (helper == null) {
                return false;
            }
            Uri uri = helper.getUri();
            if (uri == null) {
                helper.addErrorDescription("setMultipackWallpaper: uri is null.");
                return false;
            }
            if (!uri.getScheme().equals(WallpaperManager.SEM_SCHEME_MULTIPACK)) {
                helper.addErrorDescription("setMultipackWallpaper: uri sheme is not multipack.");
                return false;
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
                helper.addErrorDescription("setMultipackWallpaper: " + e.getMessage());
            }
            if (helper.isDownloadedThemeWallpaper()) {
                WallpaperManager.getInstance(helper.getContext()).semSetUri(uri, true, helper.getWhich(), 3);
                return true;
            }
            if (helper.getUri() != null) {
                Bundle param = new Bundle();
                param.putParcelable("uri", helper.getUri());
                param.putInt("which", helper.getWhich());
                param.putInt("type", helper.getWallpaperType());
                param.putString("source_path", helper.getSourceFilePath());
                param.putString("key", helper.getFileSaveKey());
                Bundle resultBundle = sendEventToDressRoom(helper.getContext(), "custom_pack", param);
                if (resultBundle.getBoolean("result", false)) {
                    return true;
                }
            }
            return false;
        }

        private boolean setMotionWallpaper(WallpaperBNRHelper helper) {
            if (!helper.isDownloadedThemeWallpaper()) {
                return false;
            }
            String packageName = helper.getPackageName();
            if (TextUtils.isEmpty(packageName)) {
                return false;
            }
            WallpaperManager.getInstance(helper.getContext()).setMotionWallpaper(helper.getPackageName(), helper.getWhich(), true);
            return true;
        }

        private boolean setAnimatedWallpaper(WallpaperBNRHelper helper) {
            if (helper.isDownloadedThemeWallpaper()) {
                String packageName = helper.getPackageName();
                if (TextUtils.isEmpty(packageName)) {
                    return false;
                }
                try {
                    WallpaperManager.getInstance(helper.getContext()).setAnimatedLockscreenWallpaper(helper.getPackageName(), helper.getWhich(), true);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    helper.addErrorDescription("setAnimatedWallpaper: " + e.getMessage());
                }
            }
            return false;
        }

        private boolean setLiveWallpaper(WallpaperBNRHelper helper) {
            try {
                Bundle param = new Bundle();
                Bundle extrasBundle = WallpaperExtraBundleHelper.fromJson(helper.getExternalParams());
                if (extrasBundle != null) {
                    param.putString("contentType", extrasBundle.getString("contentType"));
                    param.putBundle(SliceItem.FORMAT_BUNDLE, extrasBundle);
                }
                param.putInt("which", helper.getWhich());
                param.putInt("type", helper.getWallpaperType());
                param.putString("source_path", helper.getSourceFilePath());
                param.putBoolean("is_paired", helper.isHomeAndLockPaired());
                param.putString("key", helper.getFileSaveKey());
                Log.m94d(WallpaperBackupRestoreManager.TAG, "setLiveWallpaper: param = " + param);
                Bundle resultBundle = sendEventToDressRoom(helper.getContext(), "layered_image", param);
                if (resultBundle != null) {
                    if (resultBundle.getBoolean("result", false)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                helper.addErrorDescription("setLiveWallpapr: " + e.getMessage());
            }
            return false;
        }

        private void requestResetEditInfo(Context context, int which) {
            Bundle param = new Bundle();
            param.putInt("which", which);
            sendEventToDressRoom(context, "reset_edit_info", param);
        }

        private Bundle sendEventToDressRoom(Context context, String method, Bundle param) {
            return context.getContentResolver().call(Uri.parse("content://com.samsung.android.app.dressroom.provider"), method, (String) null, param);
        }

        private boolean isSourceFileValid(WallpaperBNRHelper helper) {
            if (TextUtils.isEmpty(helper.getSourceFilePath())) {
                helper.addErrorDescription("isSourceFileValid: Source file path is empty.");
                return false;
            }
            if (!BnRFileHelper.isExist(helper.getSourceFilePath())) {
                helper.addErrorDescription("isSourceFileValid: Source file not exist. path = <" + helper.getSourceFilePath() + ">");
                return false;
            }
            return true;
        }

        private boolean isPackageInstalled(WallpaperBNRHelper helper) {
            String packageName = helper.getPackageName();
            if (Build.VERSION.SEM_PLATFORM_INT < 150000) {
                if (TextUtils.isEmpty(packageName)) {
                    return false;
                }
                try {
                    PackageManager packageManager = helper.getContext().getPackageManager();
                    packageManager.getPackageInfo(packageName, 0);
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
