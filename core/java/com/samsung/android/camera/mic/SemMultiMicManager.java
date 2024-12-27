package com.samsung.android.camera.mic;

import android.media.IAudioService;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes5.dex */
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
    private static SemMultiMicManager sInstance = null;
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

    public void initialize(int soundLocation, int deviceOrientation, float maxZoom, float minZoom) {
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, soundLocation).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_PHONE_ORIENTATION, deviceOrientation).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MAX, maxZoom).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MIN, minZoom).build().toString();
        setAudioServiceConfig(parameters);
        this.mSoundLocation = soundLocation;
        this.mOrientation = deviceOrientation;
        this.mMaxZoom = maxZoom;
        this.mMinZoom = minZoom;
    }

    public void release() {
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_PHONE_ORIENTATION, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MAX, -1.0f).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_MIN, 0).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_SENSITIVITY_LEVEL, -88888).build().toString();
        setAudioServiceConfig(parameters);
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

    public void setAudioFocusCoordinate(int coordinate) {
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE, 1).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE, coordinate).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL, 0.0f).build().toString();
        setAudioServiceConfig(parameters);
        this.mCoordinate = coordinate;
    }

    public void setAudioZoomLevel(float cameraZoomLevel) {
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_AUDIO_FOCUS_ENABLE, 1).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_FOCUS_COORDINATE, -88888).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_ZOOM_LEVEL, cameraZoomLevel).build().toString();
        setAudioServiceConfig(parameters);
        this.mCameraZoomLevel = cameraZoomLevel;
    }

    public static boolean isSupported() {
        int version = 0;
        try {
            version = Integer.parseInt("08020");
        } catch (NumberFormatException e) {
        }
        return version >= 8001 || (version >= 7010 && version < 8000);
    }

    private void writeToBundle(Bundle dest) {
        dest.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_MAX, this.mMaxZoom);
        dest.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_MIN, this.mMinZoom);
        dest.putFloat(AUDIO_PARAMETER_STREAM_ZOOM_STEP, this.mCameraZoomLevel);
        dest.putInt(AUDIO_PARAMETER_STREAM_CAM_BACK, this.mSoundLocation);
        dest.putInt(AUDIO_PARAMETER_STREAM_CAM_ORIENTATION, this.mOrientation);
        dest.putInt(AUDIO_PARAMETER_STREAM_CAM_COORDINATE, this.mCoordinate);
        dest.putBoolean(AUDIO_PARAMETER_STREAM_ENABLE, this.mEnable);
    }

    public boolean setMicSensitivity(int channel, int level) {
        if (this.mMode != 1 && this.mMode != 2) {
            Log.e(TAG, "Current mode is not MODE_ADJUSTING_SENSITIVITY");
            return false;
        }
        if (level < -12 || level > 12) {
            Log.e(TAG, "Invalid level " + level + " in setMicSensitivity");
            return false;
        }
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_SENSITIVITY_LEVEL, level).build().toString();
        setAudioServiceConfig(parameters);
        this.mSensitivityLevel = level;
        return true;
    }

    public int getMicSensitivity(int channel) {
        return this.mSensitivityLevel;
    }

    public boolean setMode(int mode) {
        if (!isValidMode(mode)) {
            Log.e(TAG, "Invalid mode " + mode + " in setMode");
            return false;
        }
        AudioParameter.Builder builder = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam("mode", mode).setParam(AudioParameter.SUBKEY_AUDIO_PARAM);
        setAudioServiceConfig(builder.build().toString());
        this.mMode = mode;
        return true;
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean setSoundLocation(int soundLocation) {
        if (soundLocation < 0 || soundLocation > 1) {
            Log.e(TAG, "Invalid location " + soundLocation + " in setSoundLocation");
            return false;
        }
        String parameters = new AudioParameter.Builder().setParam(AUDIO_PARAMETER_SEC_LOCAL_MULTI_MIC_KEY).setParam(AUDIO_PARAMETER_SUBKEY_MULTI_MIC_CAMERA_DIRECTION, soundLocation).build().toString();
        setAudioServiceConfig(parameters);
        this.mSoundLocation = soundLocation;
        return true;
    }

    public int getSoundLocation() {
        return this.mSoundLocation;
    }

    public static boolean isSupported(int mode) {
        if (!isValidMode(mode)) {
            Log.e(TAG, "Invalid mode " + mode + " in isSupported");
            return false;
        }
        int version = 0;
        try {
            version = Integer.parseInt("08020");
        } catch (NumberFormatException e) {
        }
        return mode == 0 ? version >= 8001 : mode == 1 ? version >= 8010 || (version >= 7010 && version < 8000) : mode == 2 ? version >= 8020 || (version >= 7020 && version < 8000) : version >= 8001;
    }

    private static boolean isValidMode(int mode) {
        if (mode < 0 || mode > 2) {
            return false;
        }
        return true;
    }

    private static void setAudioServiceConfig(String keyValuePairs) {
        IAudioService service = getService();
        try {
            service.setAudioServiceConfig(keyValuePairs);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAudioServiceConfig", e);
        } catch (SecurityException e2) {
            Log.e(TAG, "Not allowed to audio routing", e2);
        }
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }
}
