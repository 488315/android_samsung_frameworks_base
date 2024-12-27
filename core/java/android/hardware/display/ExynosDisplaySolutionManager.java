package android.hardware.display;

import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes2.dex */
public final class ExynosDisplaySolutionManager {
    public static final String HDR_TUNE_PATTERN_CHANGED =
            "com.android.server.display.HDR_TUNE_PATTERN_CHANGED";
    public static final String HDR_TUNE_PATTERN_COLOR = "com.android.server.display.hdr_tune_color";
    public static final String HDR_TUNE_PATTERN_FORMAT =
            "com.android.server.display.hdr_tune_format";
    public static final String HDR_TUNE_PATTERN_TYPE = "com.android.server.display.hdr_tune_type";
    private static float RETURN_ERROR = -1.0f;
    private static int RETURN_ERROR_INT = -1;
    private static final String TAG = "ExynosDisplaySolutionManager";
    final IExynosDisplaySolutionManager mService;

    public ExynosDisplaySolutionManager(IExynosDisplaySolutionManager service) {
        this.mService = service;
    }

    private void onError(Exception e) {
        Log.e(TAG, "Error ExynosDisplaySolutionManager", e);
    }

    public void setDisplayFeature(String arg0, int arg1, int arg2, String arg3) {
        try {
            this.mService.setDisplayFeature(arg0, arg1, arg2, arg3);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public String getColorEnhancementMode() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getColorEnhancementMode();
        } catch (RemoteException e) {
            onError(e);
            return null;
        }
    }

    public void setColorEnhancementSettingValue(int value) {
        try {
            this.mService.setColorEnhancementSettingValue(value);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setColorTempSettingValue(int valueFrom, int valueTo) {
        try {
            this.mService.setColorTempSettingValue(valueFrom, valueTo);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setColorTempSettingOn(int onoff) {
        try {
            this.mService.setColorTempSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeTempSettingValue(int value) {
        try {
            this.mService.setEyeTempSettingValue(value);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeTempSettingOn(int onoff) {
        try {
            this.mService.setEyeTempSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbGainSettingValue(int r, int g, int b) {
        try {
            this.mService.setRgbGainSettingValue(r, g, b);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbGainSettingOn(int onoff) {
        try {
            this.mService.setRgbGainSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbWeightSettingValue(float r, float g, float b) {
        try {
            this.mService.setRgbWeightSettingValue(r, g, b);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbWeightSettingOn(int onoff) {
        try {
            this.mService.setRgbWeightSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setSkinColorSettingOn(int onoff) {
        try {
            this.mService.setSkinColorSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHsvGainSettingValue(int h, int s, int v) {
        try {
            this.mService.setHsvGainSettingValue(h, s, v);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHsvGainSettingOn(int onoff) {
        try {
            this.mService.setHsvGainSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setWhitePointColorSettingOn(int onoff) {
        try {
            this.mService.setWhitePointColorSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEdgeSharpnessSettingValue(int value) {
        try {
            this.mService.setEdgeSharpnessSettingValue(value);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEdgeSharpnessSettingOn(int onoff) {
        try {
            this.mService.setEdgeSharpnessSettingOn(onoff);
        } catch (RemoteException e) {
            onError(e);
        }
    }
}
