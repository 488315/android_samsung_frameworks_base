package com.android.server.bgslotmanager;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.am.BGProtectManager;
import com.android.server.am.DynamicHiddenApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
public class CameraKillModeManager extends CameraManager.SemCameraDeviceStateCallback {
    public static int CAMERA_DHA_VER = BgAppPropManager.getSlmkPropertyInt("cam_dha_ver", "0");
    public LinkedHashMap dha_camera_map;
    public HashMap dha_cameraclientexcept_map;
    public boolean isOriginBG;
    public boolean isUsingCamera;
    public BGSlotManager mBGSlotManager;
    public int mCamKillStartTime;
    public CameraKillModeOperator mCameraKillModeOperator;
    public CameraManager mCameraManager;
    public DynamicHiddenApp mDynamicHiddenApp;
    public MemInfoGetter mInfo;
    public float mMinFreeMax;
    public float mSzDHAThresholdRate;

    public abstract class CameraKillModeManagerLazyHolder {
        public static final CameraKillModeManager INSTANCE = new CameraKillModeManager();
        public static boolean isinitClass = false;
    }

    public CameraKillModeManager() {
        this.isUsingCamera = false;
        this.isOriginBG = true;
        this.mCamKillStartTime = BgAppPropManager.getSlmkPropertyInt("cam_kill_start_tm", "120000");
        this.mSzDHAThresholdRate = BgAppPropManager.getSlmkPropertyFloat("dha_th_rate", "2.0");
        this.dha_camera_map = new LinkedHashMap();
        this.dha_cameraclientexcept_map = new HashMap();
        this.mCameraManager = null;
        this.mCameraKillModeOperator = new CameraKillModeOperator();
    }

    public static CameraKillModeManager getInstance() {
        return CameraKillModeManagerLazyHolder.INSTANCE;
    }

    public void initCameraKillModeManager(Context context, Handler handler, DynamicHiddenApp dynamicHiddenApp) {
        if (CameraKillModeManagerLazyHolder.isinitClass) {
            return;
        }
        this.mDynamicHiddenApp = dynamicHiddenApp;
        this.mInfo = dynamicHiddenApp.getMemInfoGetterInstance();
        this.mBGSlotManager = dynamicHiddenApp.getBGSlotManagerInstance();
        if (CAMERA_DHA_VER > 0) {
            this.mMinFreeMax = dynamicHiddenApp.getMemLevel(999);
        }
        if (CAMERA_DHA_VER > 1) {
            int i = 0;
            dynamicHiddenApp.setLmkdCameraKillBoost(99, 0, 0);
            while (true) {
                String[] strArr = BGProtectManager.LMKD_CAM_CLIENT_EXCEPT_ARRAY;
                if (i >= strArr.length) {
                    break;
                }
                this.dha_cameraclientexcept_map.put(strArr[i], 1);
                i++;
            }
            this.dha_camera_map.put(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="), -1);
        }
        CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
        this.mCameraManager = cameraManager;
        if (cameraManager != null) {
            cameraManager.unregisterSemCameraDeviceStateCallback(this);
            this.mCameraManager.registerSemCameraDeviceStateCallback(this, handler);
        }
        CameraKillModeManagerLazyHolder.isinitClass = true;
    }

    public void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
        if (CAMERA_DHA_VER == 0 && (str == null || str2 == null)) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        if (DynamicHiddenApp.DEBUG) {
            Slog.i("DynamicHiddenApp_CameraKillModeManager", "Camera Callback on DHA [id] " + str + " [faceing] " + i + " [newState] " + i2 + " [clientName] " + str2);
        }
        if (uptimeMillis >= 600000 && (CAMERA_DHA_VER & 1) != 0) {
            doReduceBackgroundCached(i2, str2);
        }
        if (uptimeMillis < this.mCamKillStartTime || (CAMERA_DHA_VER & 2) == 0) {
            return;
        }
        doLmkdCameraKillMode(i2, str2);
    }

    public void doReduceBackgroundCached(int i, String str) {
        if ("com.sec.android.app.camera".equals(str)) {
            this.mCameraKillModeOperator.setState(i, false);
            this.mCameraKillModeOperator.runReduceBackgroundCached();
        }
    }

    public void doLmkdCameraKillMode(int i, String str) {
        int intValue;
        if (this.dha_cameraclientexcept_map.containsKey(str) || str.contains("vendor.") || str.contains("client.")) {
            return;
        }
        if (!this.dha_camera_map.containsKey(str)) {
            if (this.dha_camera_map.size() > 100) {
                return;
            }
            intValue = -1;
            this.dha_camera_map.put(str, -1);
        } else {
            intValue = ((Integer) this.dha_camera_map.get(str)).intValue();
        }
        int indexOf = new ArrayList(this.dha_camera_map.keySet()).indexOf(str);
        this.mCameraKillModeOperator.setState(i, true);
        this.mCameraKillModeOperator.runLmkdCameraKillMode(intValue, indexOf);
    }

    public void addCamListIfIsCameraProcess(String str, int i) {
        if (CAMERA_DHA_VER <= 1 || !this.dha_camera_map.containsKey(str)) {
            return;
        }
        this.dha_camera_map.put(str, Integer.valueOf(i));
    }

    public class CameraKillModeOperator {
        public int cameraState;
        public int prevCameraState;

        public CameraKillModeOperator() {
        }

        public void runReduceBackgroundCached() {
            if (this.cameraState == 3) {
                if (CameraKillModeManager.this.isOriginBG) {
                    return;
                }
                CameraKillModeManager.this.isOriginBG = true;
                CameraKillModeManager.this.mBGSlotManager.restoreFromCameraBGSlot();
                return;
            }
            if (CameraKillModeManager.this.isOriginBG) {
                long availableMemLegacy = CameraKillModeManager.this.mInfo.getAvailableMemLegacy();
                Slog.i("DynamicHiddenApp_CameraKillModeManager", "Available Mem: " + availableMemLegacy + " CAM TH " + ((long) (CameraKillModeManager.this.mMinFreeMax * CameraKillModeManager.this.mSzDHAThresholdRate)));
                if (availableMemLegacy < ((long) (CameraKillModeManager.this.mMinFreeMax * CameraKillModeManager.this.mSzDHAThresholdRate))) {
                    CameraKillModeManager.this.mBGSlotManager.setCameraBGSlot();
                    CameraKillModeManager.this.isOriginBG = false;
                }
            }
        }

        public void runLmkdCameraKillMode(int i, int i2) {
            if (CameraKillModeManager.this.mDynamicHiddenApp != null) {
                CameraKillModeManager.this.mDynamicHiddenApp.setLmkdCameraKillBoost(this.cameraState, i, i2);
            }
        }

        public void setState(int i, boolean z) {
            if (i == 0) {
                CameraKillModeManager.this.isUsingCamera = true;
                this.cameraState = 0;
            } else {
                int i2 = this.prevCameraState;
                if (i2 == 2 && i == 1) {
                    this.cameraState = 1;
                } else if (i == 1) {
                    this.cameraState = 11;
                } else if (i2 == 1 && i == 2) {
                    this.cameraState = 2;
                } else if (i == 2) {
                    this.cameraState = 12;
                } else if (i == 3) {
                    this.cameraState = 3;
                    CameraKillModeManager.this.isUsingCamera = false;
                }
            }
            if (z) {
                this.prevCameraState = i;
            }
        }
    }
}
