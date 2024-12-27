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

    public DisplayAiqeManager(Context context, IDisplayAiqeManager service) {
        this.mContext = context;
        this.mService = service;
        Slog.d(TAG, "construct complete.");
    }

    @SystemApi
    public boolean setByPassMode(boolean enable) {
        Slog.d(TAG, "setByPassMode : enable - " + (enable ? "true" : "false"));
        if (this.mService != null) {
            try {
                return this.mService.setByPassMode(enable);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setExtraDimMode(int level) {
        Slog.d(TAG, "setExtraDimMode : level - " + level);
        if (this.mService != null) {
            try {
                return this.mService.setExtraDimMode(level);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setHighDynamicRangeMode(boolean enable) {
        Slog.d(TAG, "setHighDynamicRangeMode : enable - " + (enable ? "true" : "false"));
        if (this.mService != null) {
            try {
                return this.mService.setHighDynamicRangeMode(enable);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setScreenMode(int mode) {
        Slog.d(TAG, "setScreenMode : mode - " + mode);
        if (this.mService != null) {
            try {
                return this.mService.setScreenMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setBlueLightFilterMode(boolean enable, int level) {
        Slog.d(
                TAG,
                "setBlueLightFilterMode : enable - "
                        + (enable ? "true" : "false")
                        + " level - "
                        + level);
        if (this.mService != null) {
            try {
                return this.mService.setBlueLightFilterMode(enable, level);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setContentMode(int mode) {
        Slog.d(TAG, "setContentMode : mode - " + mode);
        if (this.mService != null) {
            try {
                return this.mService.setContentMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public String getContentMode() {
        Slog.d(TAG, "getContentMode : start");
        if (this.mService == null) {
            return null;
        }
        try {
            String mode = this.mService.getContentMode();
            Slog.d(TAG, "getContentMode : mode - " + mode);
            return mode;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setVividnessMode(int index) {
        Slog.d(TAG, "setVividnessMode : index - " + index);
        if (this.mService != null) {
            try {
                return this.mService.setVividnessMode(index);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setWhiteBalanceMode(int m_r, int m_g, int m_b, int s_r, int s_g, int s_b) {
        Slog.d(
                TAG,
                "setWhiteBalanceMode : mode - "
                        + m_r
                        + ","
                        + m_g
                        + ","
                        + m_b
                        + ","
                        + s_r
                        + ","
                        + s_g
                        + ","
                        + s_b);
        if (this.mService != null) {
            try {
                return this.mService.setWhiteBalanceMode(m_r, m_g, m_b, s_r, s_g, s_b);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setEnvironmentAdaptiveDisplayMode(int mode) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayMode : mode - " + mode);
        if (this.mService != null) {
            try {
                return this.mService.setEnvironmentAdaptiveDisplayMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setEnvironmentAdaptiveDisplayLevel(int level) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayLevel : level - " + level);
        if (this.mService != null) {
            try {
                return this.mService.setEnvironmentAdaptiveDisplayLevel(level);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setHighBrightnessMode(int index) {
        Slog.d(TAG, "setHighBrightnessMode : index - " + index);
        if (this.mService != null) {
            try {
                return this.mService.setHighBrightnessMode(index);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean setNaturalMode(String mode) {
        Slog.d(TAG, "setNaturalMode : mode - " + mode);
        if (this.mService != null) {
            try {
                return this.mService.setNaturalMode(mode);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public boolean getDisplayService() {
        Slog.d(TAG, "getDisplayService");
        if (this.mService != null) {
            try {
                return this.mService.getDisplayService();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public int getCoprValue() {
        if (this.mService != null) {
            try {
                int ret = this.mService.getCoprValue();
                Slog.d(TAG, "getCoprValue : " + ret);
                return ret;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }
}
