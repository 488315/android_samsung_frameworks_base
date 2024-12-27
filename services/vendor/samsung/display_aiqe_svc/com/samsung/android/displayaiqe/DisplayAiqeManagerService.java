package com.samsung.android.displayaiqe;

import android.content.Context;
import android.util.Slog;

import com.android.server.SystemService;

/* loaded from: classes.dex */
public class DisplayAiqeManagerService extends IDisplayAiqeManager.Stub {
    private static final String TAG = "DisplayAiqeManagerService";
    private final Context mContext;
    private DisplayAiqeHal mDisplayAiqeHal;

    public static class Lifecycle extends SystemService {
        private DisplayAiqeManagerService mDisplayAiqeManagerService;

        public Lifecycle(Context context) {
            super(context);
            this.mDisplayAiqeManagerService = new DisplayAiqeManagerService(context);
        }

        public void onStart() {
            Slog.d("DisplayAiqeManagerService$Lifecycle", "onStart");
            publishBinderService("display_aiqe", this.mDisplayAiqeManagerService);
        }
    }

    public DisplayAiqeManagerService(Context context) {
        this.mContext = context;
        this.mDisplayAiqeHal = DisplayAiqeHalInstance.getInstance(this, this.mContext);
    }

    public boolean setByPassMode(boolean enable) {
        Slog.d(TAG, "setByPassMode : enable - " + (enable ? "true" : "false"));
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setByPassMode(enable);
            } catch (Exception e) {
                Slog.e(TAG, "setByPassMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setExtraDimMode(int level) {
        Slog.d(TAG, "setExtraDimMode : level - " + level);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setExtraDimMode(level);
            } catch (Exception e) {
                Slog.e(TAG, "setExtraDimMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setHighDynamicRangeMode(boolean enable) {
        Slog.d(TAG, "setHighDynamicRangeMode : enable - " + (enable ? "true" : "false"));
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setHighDynamicRangeMode(enable);
            } catch (Exception e) {
                Slog.e(TAG, "setHighDynamicRangeMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setScreenMode(int mode) {
        Slog.d(TAG, "setScreenMode : mode - " + mode);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setScreenMode(mode);
            } catch (Exception e) {
                Slog.e(TAG, "setScreenMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setBlueLightFilterMode(boolean enable, int level) {
        Slog.d(
                TAG,
                "setBlueLightFilterMode : enable - "
                        + (enable ? "true" : "false")
                        + " level - "
                        + level);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setBlueLightFilterMode(enable, level);
            } catch (Exception e) {
                Slog.e(TAG, "setBlueLightFilterMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setContentMode(int mode) {
        Slog.d(TAG, "setContentMode : mode - " + mode);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setContentMode(mode);
            } catch (Exception e) {
                Slog.e(TAG, "setContentMode fail", e);
                return false;
            }
        }
        return false;
    }

    public String getContentMode() {
        String mode = null;
        Slog.d(TAG, "getContentMode : start");
        if (this.mDisplayAiqeHal == null) {
            return null;
        }
        try {
            mode = this.mDisplayAiqeHal.getContentMode();
            Slog.d(TAG, "getContentMode : mode - " + mode);
            return mode;
        } catch (Exception e) {
            Slog.e(TAG, "getContentMode fail", e);
            return mode;
        }
    }

    public boolean setVividnessMode(int index) {
        Slog.d(TAG, "setVividnessMode : index - " + index);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setVividnessMode(index);
            } catch (Exception e) {
                Slog.e(TAG, "setVividnessMode fail", e);
                return false;
            }
        }
        return false;
    }

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
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setWhiteBalanceMode(m_r, m_g, m_b, s_r, s_g, s_b);
            } catch (Exception e) {
                Slog.e(TAG, "setWhiteBalanceMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setEnvironmentAdaptiveDisplayMode(int mode) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayMode : mode - " + mode);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setEnvironmentAdaptiveDisplayMode(mode);
            } catch (Exception e) {
                Slog.e(TAG, "setEnvironmentAdaptiveDisplayMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setEnvironmentAdaptiveDisplayLevel(int level) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayLevel : level - " + level);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setEnvironmentAdaptiveDisplayLevel(level);
            } catch (Exception e) {
                Slog.e(TAG, "setEnvironmentAdaptiveDisplayLevel fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setHighBrightnessMode(int index) {
        Slog.d(TAG, "setHighBrightnessMode : index - " + index);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setHighBrightnessMode(index);
            } catch (Exception e) {
                Slog.e(TAG, "setHighBrightnessMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean setNaturalMode(String mode) {
        Slog.d(TAG, "setNaturalMode: mode - " + mode);
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.setNaturalMode(mode);
            } catch (Exception e) {
                Slog.e(TAG, "setNaturalMode fail", e);
                return false;
            }
        }
        return false;
    }

    public boolean getDisplayService() {
        Slog.d(TAG, "getDisplayService");
        if (this.mDisplayAiqeHal != null) {
            try {
                return this.mDisplayAiqeHal.getDisplayService();
            } catch (Exception e) {
                Slog.e(TAG, "setNaturalMode fail", e);
                return false;
            }
        }
        return false;
    }

    public int getCoprValue() {
        if (this.mDisplayAiqeHal != null) {
            try {
                int ret = this.mDisplayAiqeHal.getCoprValue();
                Slog.d(TAG, "getCoprValue: " + ret);
                return ret;
            } catch (Exception e) {
                Slog.e(TAG, "getCoprValue fail", e);
                return 0;
            }
        }
        return 0;
    }
}
