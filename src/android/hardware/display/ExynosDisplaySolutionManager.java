package android.hardware.display;

import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes2.dex */
public final class ExynosDisplaySolutionManager {
    public static final String HDR_TUNE_PATTERN_CHANGED = "com.android.server.display.HDR_TUNE_PATTERN_CHANGED";
    public static final String HDR_TUNE_PATTERN_COLOR = "com.android.server.display.hdr_tune_color";
    public static final String HDR_TUNE_PATTERN_FORMAT = "com.android.server.display.hdr_tune_format";
    public static final String HDR_TUNE_PATTERN_TYPE = "com.android.server.display.hdr_tune_type";
    private static float RETURN_ERROR = -1.0f;
    private static int RETURN_ERROR_INT = -1;
    private static final String TAG = "ExynosDisplaySolutionManager";
    final IExynosDisplaySolutionManager mService;

    public ExynosDisplaySolutionManager(IExynosDisplaySolutionManager iExynosDisplaySolutionManager) {
        this.mService = iExynosDisplaySolutionManager;
    }

    private void onError(Exception exc) {
        Log.e(TAG, "Error ExynosDisplaySolutionManager", exc);
    }

    public void setDisplayFeature(String str, int i, int i2, String str2) {
        try {
            this.mService.setDisplayFeature(str, i, i2, str2);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public String getColorEnhancementMode() {
        IExynosDisplaySolutionManager iExynosDisplaySolutionManager = this.mService;
        if (iExynosDisplaySolutionManager == null) {
            return null;
        }
        try {
            return iExynosDisplaySolutionManager.getColorEnhancementMode();
        } catch (RemoteException e) {
            this.onError(e);
            return null;
        }
    }

    public void setColorEnhancementSettingValue(int i) {
        try {
            this.mService.setColorEnhancementSettingValue(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setColorTempSettingValue(int i, int i2) {
        try {
            this.mService.setColorTempSettingValue(i, i2);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setColorTempSettingOn(int i) {
        try {
            this.mService.setColorTempSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeTempSettingValue(int i) {
        try {
            this.mService.setEyeTempSettingValue(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeTempSettingOn(int i) {
        try {
            this.mService.setEyeTempSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbGainSettingValue(int i, int i2, int i3) {
        try {
            this.mService.setRgbGainSettingValue(i, i2, i3);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbGainSettingOn(int i) {
        try {
            this.mService.setRgbGainSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbWeightSettingValue(float f, float f2, float f3) {
        try {
            this.mService.setRgbWeightSettingValue(f, f2, f3);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRgbWeightSettingOn(int i) {
        try {
            this.mService.setRgbWeightSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setSkinColorSettingOn(int i) {
        try {
            this.mService.setSkinColorSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHsvGainSettingValue(int i, int i2, int i3) {
        try {
            this.mService.setHsvGainSettingValue(i, i2, i3);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHsvGainSettingOn(int i) {
        try {
            this.mService.setHsvGainSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setWhitePointColorSettingOn(int i) {
        try {
            this.mService.setWhitePointColorSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEdgeSharpnessSettingValue(int i) {
        try {
            this.mService.setEdgeSharpnessSettingValue(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEdgeSharpnessSettingOn(int i) {
        try {
            this.mService.setEdgeSharpnessSettingOn(i);
        } catch (RemoteException e) {
            onError(e);
        }
    }
}
