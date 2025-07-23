package com.samsung.android.displayaiqe;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes6.dex */
public class DisplayAiqeManager {
    private static final String TAG = "DisplayAiqeManager";
    private final Context mContext;
    private final IDisplayAiqeManager mService;

    public DisplayAiqeManager(Context context, IDisplayAiqeManager iDisplayAiqeManager) {
        this.mContext = context;
        this.mService = iDisplayAiqeManager;
        Slog.d(TAG, "construct complete.");
    }

    @SystemApi
    public boolean setByPassMode(boolean z) {
        Slog.d(TAG, "setByPassMode : enable - ".concat(z ? "true" : "false"));
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setByPassMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setExtraDimMode(int i) {
        Slog.d(TAG, "setExtraDimMode : level - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setExtraDimMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setHighDynamicRangeMode(boolean z) {
        Slog.d(TAG, "setHighDynamicRangeMode : enable - ".concat(z ? "true" : "false"));
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setHighDynamicRangeMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setScreenMode(int i) {
        Slog.d(TAG, "setScreenMode : mode - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setScreenMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setBlueLightFilterMode(boolean z, int i) {
        StringBuilder sb = new StringBuilder("setBlueLightFilterMode : enable - ");
        sb.append(z ? "true" : "false");
        sb.append(" level - ");
        sb.append(i);
        Slog.d(TAG, sb.toString());
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setBlueLightFilterMode(z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setContentMode(int i) {
        Slog.d(TAG, "setContentMode : mode - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setContentMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getContentMode() {
        Slog.d(TAG, "getContentMode : start");
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return null;
        }
        try {
            String contentMode = iDisplayAiqeManager.getContentMode();
            Slog.d(TAG, "getContentMode : mode - " + contentMode);
            return contentMode;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setVividnessMode(int i) {
        Slog.d(TAG, "setVividnessMode : index - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setVividnessMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6) {
        Slog.d(TAG, "setWhiteBalanceMode : mode - " + i + "," + i2 + "," + i3 + "," + i4 + "," + i5 + "," + i6);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setWhiteBalanceMode(i, i2, i3, i4, i5, i6);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setEnvironmentAdaptiveDisplayMode(int i) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayMode : mode - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setEnvironmentAdaptiveDisplayMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setEnvironmentAdaptiveDisplayLevel(int i) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayLevel : level - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setEnvironmentAdaptiveDisplayLevel(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setHighBrightnessMode(int i) {
        Slog.d(TAG, "setHighBrightnessMode : index - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setHighBrightnessMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setNaturalMode(String str) {
        Slog.d(TAG, "setNaturalMode : mode - " + str);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setNaturalMode(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setLinearSkinMode(String str) {
        Slog.d(TAG, "setLinearSkinMode : mode - " + str);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setLinearSkinMode(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setInternalDimmingFrame(int i) {
        Slog.d(TAG, "setInternalDimmingFrame : count - " + i);
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.setInternalDimmingFrame(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean getDisplayService() {
        Slog.d(TAG, "getDisplayService");
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            return iDisplayAiqeManager.getDisplayService();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getCoprValue() {
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return 0;
        }
        try {
            int coprValue = iDisplayAiqeManager.getCoprValue();
            Slog.d(TAG, "getCoprValue : " + coprValue);
            return coprValue;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setABCMode(int i, String str) {
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            boolean aBCMode = iDisplayAiqeManager.setABCMode(i, str);
            Slog.d(TAG, "setABCMode : " + aBCMode);
            return aBCMode;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setABCState(int i, int i2) {
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            boolean aBCState = iDisplayAiqeManager.setABCState(i, i2);
            Slog.d(TAG, "setABCState : " + aBCState);
            return aBCState;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setABCReconfig(int i) {
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return false;
        }
        try {
            boolean aBCReconfig = iDisplayAiqeManager.setABCReconfig(i);
            Slog.d(TAG, "setABCReconfig : " + aBCReconfig);
            return aBCReconfig;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getPanelName(int i) {
        IDisplayAiqeManager iDisplayAiqeManager = this.mService;
        if (iDisplayAiqeManager == null) {
            return null;
        }
        try {
            String panelName = iDisplayAiqeManager.getPanelName(i);
            Slog.d(TAG, "getPanelName : " + panelName);
            return panelName;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
