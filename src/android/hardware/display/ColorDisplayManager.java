package android.hardware.display;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.display.IColorDisplayManager;
import android.metrics.LogMaker;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import com.android.internal.R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.server.display.feature.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalTime;

@SystemApi
/* loaded from: classes2.dex */
public final class ColorDisplayManager {

    @SystemApi
    public static final int AUTO_MODE_CUSTOM_TIME = 1;

    @SystemApi
    public static final int AUTO_MODE_DISABLED = 0;

    @SystemApi
    public static final int AUTO_MODE_TWILIGHT = 2;

    @SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_GLOBAL = 2;

    @SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_PER_APP = 4;

    @SystemApi
    public static final int CAPABILITY_NONE = 0;

    @SystemApi
    public static final int CAPABILITY_PROTECTED_CONTENT = 1;
    public static final int COLOR_MODE_AUTOMATIC = 3;
    public static final int COLOR_MODE_BOOSTED = 1;
    public static final int COLOR_MODE_NATURAL = 0;
    public static final int COLOR_MODE_SATURATED = 2;
    public static final int VENDOR_COLOR_MODE_RANGE_MAX = 511;
    public static final int VENDOR_COLOR_MODE_RANGE_MIN = 256;
    private final ColorDisplayManagerInternal mManager = ColorDisplayManagerInternal.getInstance();
    private MetricsLogger mMetricsLogger;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CapabilityType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    public static boolean isStandardColorMode(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3;
    }

    public boolean setNightDisplayActivated(boolean z) {
        return this.mManager.setNightDisplayActivated(z);
    }

    public boolean isNightDisplayActivated() {
        return this.mManager.isNightDisplayActivated();
    }

    public boolean setNightDisplayColorTemperature(int i) {
        return this.mManager.setNightDisplayColorTemperature(i);
    }

    public int getNightDisplayColorTemperature() {
        return this.mManager.getNightDisplayColorTemperature();
    }

    @SystemApi
    public int getNightDisplayAutoMode() {
        return this.mManager.getNightDisplayAutoMode();
    }

    public int getNightDisplayAutoModeRaw() {
        return this.mManager.getNightDisplayAutoModeRaw();
    }

    @SystemApi
    public boolean setNightDisplayAutoMode(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Invalid autoMode: " + i);
        }
        if (this.mManager.getNightDisplayAutoMode() != i) {
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CHANGED).setType(4).setSubtype(i));
        }
        return this.mManager.setNightDisplayAutoMode(i);
    }

    public LocalTime getNightDisplayCustomStartTime() {
        return this.mManager.getNightDisplayCustomStartTime().getLocalTime();
    }

    @SystemApi
    public boolean setNightDisplayCustomStartTime(LocalTime localTime) {
        if (localTime == null) {
            throw new IllegalArgumentException("startTime cannot be null");
        }
        getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CUSTOM_TIME_CHANGED).setType(4).setSubtype(0));
        return this.mManager.setNightDisplayCustomStartTime(new Time(localTime));
    }

    public LocalTime getNightDisplayCustomEndTime() {
        return this.mManager.getNightDisplayCustomEndTime().getLocalTime();
    }

    @SystemApi
    public boolean setNightDisplayCustomEndTime(LocalTime localTime) {
        if (localTime == null) {
            throw new IllegalArgumentException("endTime cannot be null");
        }
        getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CUSTOM_TIME_CHANGED).setType(4).setSubtype(1));
        return this.mManager.setNightDisplayCustomEndTime(new Time(localTime));
    }

    public void setColorMode(int i) {
        this.mManager.setColorMode(i);
    }

    public int getColorMode() {
        return this.mManager.getColorMode();
    }

    public boolean isDeviceColorManaged() {
        return this.mManager.isDeviceColorManaged();
    }

    @SystemApi
    public boolean setSaturationLevel(int i) {
        return this.mManager.setSaturationLevel(i);
    }

    public boolean isSaturationActivated() {
        return this.mManager.isSaturationActivated();
    }

    @SystemApi
    public boolean setAppSaturationLevel(String str, int i) {
        return this.mManager.setAppSaturationLevel(str, i);
    }

    public boolean setDisplayWhiteBalanceEnabled(boolean z) {
        return this.mManager.setDisplayWhiteBalanceEnabled(z);
    }

    public boolean isDisplayWhiteBalanceEnabled() {
        return this.mManager.isDisplayWhiteBalanceEnabled();
    }

    public boolean setReduceBrightColorsActivated(boolean z) {
        return this.mManager.setReduceBrightColorsActivated(z);
    }

    public boolean isReduceBrightColorsActivated() {
        return this.mManager.isReduceBrightColorsActivated();
    }

    public boolean setReduceBrightColorsStrength(int i) {
        return this.mManager.setReduceBrightColorsStrength(i);
    }

    public int getReduceBrightColorsStrength() {
        return this.mManager.getReduceBrightColorsStrength();
    }

    public float getReduceBrightColorsOffsetFactor() {
        return this.mManager.getReduceBrightColorsOffsetFactor();
    }

    public static boolean isNightDisplayAvailable(Context context) {
        return context.getResources().getBoolean(R.bool.config_nightDisplayAvailable);
    }

    public static int getMinimumColorTemperature(Context context) {
        return context.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMin);
    }

    public static int getMaximumColorTemperature(Context context) {
        return context.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMax);
    }

    public static boolean isDisplayWhiteBalanceAvailable(Context context) {
        return context.getResources().getBoolean(R.bool.config_displayWhiteBalanceAvailable);
    }

    public static boolean isReduceBrightColorsAvailable(Context context) {
        if (context.getResources().getBoolean(R.bool.config_reduceBrightColorsAvailable)) {
            return (Flags.evenDimmer() && context.getResources().getBoolean(R.bool.config_evenDimmerEnabled)) ? false : true;
        }
        return false;
    }

    public static int getMinimumReduceBrightColorsStrength(Context context) {
        return context.getResources().getInteger(R.integer.config_reduceBrightColorsStrengthMin);
    }

    public static int getMaximumReduceBrightColorsStrength(Context context) {
        return context.getResources().getInteger(R.integer.config_reduceBrightColorsStrengthMax);
    }

    public static boolean isColorTransformAccelerated(Context context) {
        return context.getResources().getBoolean(R.bool.config_setColorTransformAccelerated);
    }

    @SystemApi
    public int getTransformCapabilities() {
        return this.mManager.getTransformCapabilities();
    }

    public static boolean areAccessibilityTransformsEnabled(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, 0) == 1 || Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, 0) == 1;
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    private static class ColorDisplayManagerInternal {
        private static ColorDisplayManagerInternal sInstance;
        private final IColorDisplayManager mCdm;

        private ColorDisplayManagerInternal(IColorDisplayManager iColorDisplayManager) {
            this.mCdm = iColorDisplayManager;
        }

        public static ColorDisplayManagerInternal getInstance() {
            ColorDisplayManagerInternal colorDisplayManagerInternal;
            synchronized (ColorDisplayManagerInternal.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new ColorDisplayManagerInternal(IColorDisplayManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.COLOR_DISPLAY_SERVICE)));
                    } catch (ServiceManager.ServiceNotFoundException e) {
                        throw new IllegalStateException(e);
                    }
                }
                colorDisplayManagerInternal = sInstance;
            }
            return colorDisplayManagerInternal;
        }

        boolean isNightDisplayActivated() {
            try {
                return this.mCdm.isNightDisplayActivated();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayActivated(boolean z) {
            try {
                return this.mCdm.setNightDisplayActivated(z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayColorTemperature() {
            try {
                return this.mCdm.getNightDisplayColorTemperature();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayColorTemperature(int i) {
            try {
                return this.mCdm.setNightDisplayColorTemperature(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoMode() {
            try {
                return this.mCdm.getNightDisplayAutoMode();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoModeRaw() {
            try {
                return this.mCdm.getNightDisplayAutoModeRaw();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayAutoMode(int i) {
            try {
                return this.mCdm.setNightDisplayAutoMode(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        Time getNightDisplayCustomStartTime() {
            try {
                return this.mCdm.getNightDisplayCustomStartTime();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomStartTime(Time time) {
            try {
                return this.mCdm.setNightDisplayCustomStartTime(time);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        Time getNightDisplayCustomEndTime() {
            try {
                return this.mCdm.getNightDisplayCustomEndTime();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomEndTime(Time time) {
            try {
                return this.mCdm.setNightDisplayCustomEndTime(time);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isDeviceColorManaged() {
            try {
                return this.mCdm.isDeviceColorManaged();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setSaturationLevel(int i) {
            try {
                return this.mCdm.setSaturationLevel(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isSaturationActivated() {
            try {
                return this.mCdm.isSaturationActivated();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setAppSaturationLevel(String str, int i) {
            try {
                return this.mCdm.setAppSaturationLevel(str, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isDisplayWhiteBalanceEnabled() {
            try {
                return this.mCdm.isDisplayWhiteBalanceEnabled();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setDisplayWhiteBalanceEnabled(boolean z) {
            try {
                return this.mCdm.setDisplayWhiteBalanceEnabled(z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isReduceBrightColorsActivated() {
            try {
                return this.mCdm.isReduceBrightColorsActivated();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setReduceBrightColorsActivated(boolean z) {
            try {
                return this.mCdm.setReduceBrightColorsActivated(z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getReduceBrightColorsStrength() {
            try {
                return this.mCdm.getReduceBrightColorsStrength();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setReduceBrightColorsStrength(int i) {
            try {
                return this.mCdm.setReduceBrightColorsStrength(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        float getReduceBrightColorsOffsetFactor() {
            try {
                return this.mCdm.getReduceBrightColorsOffsetFactor();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getColorMode() {
            try {
                return this.mCdm.getColorMode();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setColorMode(int i) {
            try {
                this.mCdm.setColorMode(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getTransformCapabilities() {
            try {
                return this.mCdm.getTransformCapabilities();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
