package com.samsung.android.camera.mic;

import android.media.IAudioService;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes6.dex */
public class SemMultiMicManager {
    private static final String AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY = "l_multi_mic_key";
    private static final String AUDIO_PARAMETER_STREAM_CAM_BACK = "cam_back";
    private static final String AUDIO_PARAMETER_STREAM_CAM_COORDINATE = "cam_coordinate";
    private static final String AUDIO_PARAMETER_STREAM_CAM_ORIENTATION = "cam_orientation";
    private static final String AUDIO_PARAMETER_STREAM_ENABLE = "cam_enable";
    private static final String AUDIO_PARAMETER_STREAM_ZOOM_MAX = "cam_zoom_max";
    private static final String AUDIO_PARAMETER_STREAM_ZOOM_MIN = "cam_zoom_min";
    private static final String AUDIO_PARAMETER_STREAM_ZOOM_STEP = "cam_zoom";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE = "audio_focus_enable";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION = "camera_direction";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE = "focus_coordinate";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_MODE = "mode";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_PHONE_ORIENTATION = "phone_orientation";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_SENSITIVITY_LEVEL = "sensitivity_level";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL = "zoom_level";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MAX = "zoom_max";
    private static final String AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MIN = "zoom_min";
    public static final int CAMERA_FACING_BACK = 1;
    public static final int CAMERA_FACING_FRONT = 0;
    private static final int DEFAULT_COORDINATE = -88888;
    private static final int DEFAULT_SENSITIVITY_LEVEL = -88888;
    private static final float DEFAULT_ZOOM = 0.0f;
    public static final int MODE_ADJUSTING_SENSITIVITY = 1;
    public static final int MODE_ADJUSTING_SENSITIVITY_BY_BLUETOOTH_AND_BUILTIN_MIC = 2;
    public static final int MODE_ADJUSTING_SENSITIVITY_BY_BLUETOOTH_MIC = 2;
    public static final int MODE_ADJUSTING_ZOOM_LEVEL = 0;
    public static final int SURFACE_ROTATION_0 = 0;
    public static final int SURFACE_ROTATION_180 = 180;
    public static final int SURFACE_ROTATION_270 = 270;
    public static final int SURFACE_ROTATION_90 = 90;
    private static final String TAG = "SemMultiMicManager";
    private static SemMultiMicManager sInstance;
    private static IAudioService sService;
    private float mMaxZoom = -1.0f;
    private float mMinZoom = 0.0f;
    private float mCameraZoomLevel = 1.0f;
    private int mSoundLocation = 0;
    private int mOrientation = 0;
    private int mCoordinate = 0;
    private boolean mEnable = false;
    private int mSensitivityLevel = -88888;
    private int mMode = 0;

    private static boolean isValidMode(int i) {
        return i >= 0 && i <= 2;
    }

    private SemMultiMicManager() {
    }

    public static SemMultiMicManager getInstance() {
        if (!isSupported()) {
            return null;
        }
        if (sInstance == null) {
            sInstance = new SemMultiMicManager();
        }
        return sInstance;
    }

    public void initialize(int i, int i2, float f, float f2) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, i).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_PHONE_ORIENTATION, i2).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MAX, f).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MIN, f2).build().toString());
        this.mSoundLocation = i;
        this.mOrientation = i2;
        this.mMaxZoom = f;
        this.mMinZoom = f2;
    }

    public void release() {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_PHONE_ORIENTATION, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MAX, -1.0f).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MIN, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_SENSITIVITY_LEVEL, -88888).build().toString());
        this.mSoundLocation = 0;
        this.mOrientation = 0;
        this.mMaxZoom = -1.0f;
        this.mMinZoom = 0.0f;
        setMode(0);
    }

    public void setEnabled(boolean z) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE, z ? 1 : 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE, -88888).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL, 0.0f).build().toString());
        this.mEnable = z;
    }

    public void setAudioFocusCoordinate(int i) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE, 1).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE, i).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL, 0.0f).build().toString());
        this.mCoordinate = i;
    }

    public void setAudioZoomLevel(float f) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE, 1).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE, -88888).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL, f).build().toString());
        this.mCameraZoomLevel = f;
    }

    public static boolean isSupported() {
        int i;
        try {
            i = Integer.parseInt("08020");
        } catch (NumberFormatException unused) {
            i = 0;
        }
        return i >= 8001 || (i >= 7010 && i < 8000);
    }

    private void writeToBundle(Bundle bundle) {
        bundle.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_MAX, this.mMaxZoom);
        bundle.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_MIN, this.mMinZoom);
        bundle.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_STEP, this.mCameraZoomLevel);
        bundle.putInt(AUDIO_PARAMETER_STREAM_CAM_BACK, this.mSoundLocation);
        bundle.putInt(AUDIO_PARAMETER_STREAM_CAM_ORIENTATION, this.mOrientation);
        bundle.putInt(AUDIO_PARAMETER_STREAM_CAM_COORDINATE, this.mCoordinate);
        bundle.putBoolean(AUDIO_PARAMETER_STREAM_ENABLE, this.mEnable);
    }

    public boolean setMicSensitivity(int i, int i2) {
        int i3 = this.mMode;
        if (i3 != 1 && i3 != 2) {
            Log.e(TAG, "Current mode is not MODE_ADJUSTING_SENSITIVITY");
            return false;
        }
        if (i2 < -12 || i2 > 12) {
            Log.e(TAG, "Invalid level " + i2 + " in setMicSensitivity");
            return false;
        }
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_SENSITIVITY_LEVEL, i2).build().toString());
        this.mSensitivityLevel = i2;
        return true;
    }

    public int getMicSensitivity(int i) {
        return this.mSensitivityLevel;
    }

    public boolean setMode(int i) {
        if (!isValidMode(i)) {
            Log.e(TAG, "Invalid mode " + i + " in setMode");
            return false;
        }
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam("mode", i).setParam(AudioParameter.SUBKEY_AUDIO_PARAM).build().toString());
        this.mMode = i;
        return true;
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean setSoundLocation(int i) {
        if (i < 0 || i > 1) {
            Log.e(TAG, "Invalid location " + i + " in setSoundLocation");
            return false;
        }
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, i).build().toString());
        this.mSoundLocation = i;
        return true;
    }

    public int getSoundLocation() {
        return this.mSoundLocation;
    }

    public static boolean isSupported(int i) {
        int i2;
        if (!isValidMode(i)) {
            Log.e(TAG, "Invalid mode " + i + " in isSupported");
            return false;
        }
        try {
            i2 = Integer.parseInt("08020");
        } catch (NumberFormatException unused) {
            i2 = 0;
        }
        return i == 0 ? i2 >= 8001 : i == 1 ? i2 >= 8010 || (i2 >= 7010 && i2 < 8000) : i == 2 ? i2 >= 8020 || (i2 >= 7020 && i2 < 8000) : i2 >= 8001;
    }

    private static void setAudioServiceConfig(String str) {
        try {
            getService().setAudioServiceConfig(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAudioServiceConfig", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Not allowed to audio routing", e2);
        }
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService asInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = asInterface;
        return asInterface;
    }
}
