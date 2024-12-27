package com.android.server.bgslotmanager;

import android.hardware.camera2.CameraManager;
import android.os.SystemClock;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.DynamicHiddenApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public final class CameraKillModeManager extends CameraManager.SemCameraDeviceStateCallback {
    public static int CAMERA_DHA_VER = BgAppPropManager.getSlmkPropertyInt("cam_dha_ver", "0");
    public LinkedHashMap dha_camera_map;
    public HashMap dha_cameraclientexcept_map;
    public boolean isOriginBG;
    public BGSlotManager mBGSlotManager;
    public int mCamKillStartTime;
    public CameraKillModeOperator mCameraKillModeOperator;
    public CameraManager mCameraManager;
    public DynamicHiddenApp mDynamicHiddenApp;
    public MemInfoGetter mInfo;
    public float mMinFreeMax;
    public float mSzDHAThresholdRate;

    public abstract class CameraKillModeManagerLazyHolder {
        public static final CameraKillModeManager INSTANCE;
        public static boolean isinitClass;

        static {
            CameraKillModeManager cameraKillModeManager = new CameraKillModeManager();
            cameraKillModeManager.isOriginBG = true;
            cameraKillModeManager.mCamKillStartTime =
                    BgAppPropManager.getSlmkPropertyInt("cam_kill_start_tm", "120000");
            cameraKillModeManager.mSzDHAThresholdRate =
                    Float.parseFloat(BgAppPropManager.getSlmkPropertyString("dha_th_rate", "2.0"));
            cameraKillModeManager.dha_camera_map = new LinkedHashMap();
            cameraKillModeManager.dha_cameraclientexcept_map = new HashMap();
            cameraKillModeManager.mCameraManager = null;
            cameraKillModeManager.mCameraKillModeOperator =
                    cameraKillModeManager.new CameraKillModeOperator();
            INSTANCE = cameraKillModeManager;
            isinitClass = false;
        }
    }

    public final class CameraKillModeOperator {
        public int cameraState;
        public int prevCameraState;

        public CameraKillModeOperator() {}

        public final void setState(int i, boolean z) {
            CameraKillModeManager cameraKillModeManager = CameraKillModeManager.this;
            if (i == 0) {
                cameraKillModeManager.getClass();
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
                    cameraKillModeManager.getClass();
                }
            }
            if (z) {
                this.prevCameraState = i;
            }
        }
    }

    public final void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
        int intValue;
        if (CAMERA_DHA_VER == 0 && (str == null || str2 == null)) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        if (DynamicHiddenApp.DEBUG) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Camera Callback on DHA [id] ", str, " [faceing] ", " [newState] ");
            m.append(i2);
            m.append(" [clientName] ");
            m.append(str2);
            Slog.i("DynamicHiddenApp_CameraKillModeManager", m.toString());
        }
        if (uptimeMillis >= 600000
                && (CAMERA_DHA_VER & 1) != 0
                && "com.sec.android.app.camera".equals(str2)) {
            this.mCameraKillModeOperator.setState(i2, false);
            CameraKillModeOperator cameraKillModeOperator = this.mCameraKillModeOperator;
            int i3 = cameraKillModeOperator.cameraState;
            CameraKillModeManager cameraKillModeManager = CameraKillModeManager.this;
            if (i3 == 3) {
                if (!cameraKillModeManager.isOriginBG) {
                    cameraKillModeManager.isOriginBG = true;
                    BGSlotManager bGSlotManager = cameraKillModeManager.mBGSlotManager;
                    bGSlotManager.BGSlotState &= -2;
                    bGSlotManager.changeBGSlot();
                    Slog.i("DynamicHiddenApp_BGSlotManager", "CameraBGSlot Recovered");
                }
            } else if (cameraKillModeManager.isOriginBG) {
                MemInfoGetter memInfoGetter = cameraKillModeManager.mInfo;
                memInfoGetter.mInfoInner.readLightMemInfo();
                long freeSize =
                        (memInfoGetter.mInfoInner.getFreeSize()
                                        + memInfoGetter.mInfoInner.getCachedSizeLegacy())
                                - (memInfoGetter.mInfoInner.getRbinTotalSize()
                                        - memInfoGetter.mInfoInner.getRbinAllocedSize());
                StringBuilder m2 =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "Available Mem: ", freeSize, " CAM TH ");
                m2.append(
                        (long)
                                (cameraKillModeManager.mMinFreeMax
                                        * cameraKillModeManager.mSzDHAThresholdRate));
                Slog.i("DynamicHiddenApp_CameraKillModeManager", m2.toString());
                if (freeSize
                        < ((long)
                                (cameraKillModeManager.mMinFreeMax
                                        * cameraKillModeManager.mSzDHAThresholdRate))) {
                    BGSlotManager bGSlotManager2 = cameraKillModeManager.mBGSlotManager;
                    bGSlotManager2.BGSlotState |= 1;
                    bGSlotManager2.changeBGSlot();
                    cameraKillModeManager.isOriginBG = false;
                }
            }
        }
        if (uptimeMillis < this.mCamKillStartTime
                || (CAMERA_DHA_VER & 2) == 0
                || this.dha_cameraclientexcept_map.containsKey(str2)
                || str2.contains("vendor.")
                || str2.contains("client.")) {
            return;
        }
        if (this.dha_camera_map.containsKey(str2)) {
            intValue = ((Integer) this.dha_camera_map.get(str2)).intValue();
        } else {
            if (this.dha_camera_map.size() > 100) {
                return;
            }
            intValue = -1;
            this.dha_camera_map.put(str2, -1);
        }
        int indexOf = new ArrayList(this.dha_camera_map.keySet()).indexOf(str2);
        this.mCameraKillModeOperator.setState(i2, true);
        CameraKillModeOperator cameraKillModeOperator2 = this.mCameraKillModeOperator;
        if (CameraKillModeManager.this.mDynamicHiddenApp != null) {
            DynamicHiddenApp.setLmkdCameraKillBoost(
                    cameraKillModeOperator2.cameraState, intValue, indexOf);
        }
    }
}
