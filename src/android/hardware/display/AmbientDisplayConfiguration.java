package android.hardware.display;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.internal.util.ArrayUtils;
import java.util.Map;

/* loaded from: classes2.dex */
public class AmbientDisplayConfiguration {
    private static final String[] DOZE_SETTINGS = {Settings.Secure.DOZE_ENABLED, Settings.Secure.DOZE_ALWAYS_ON, Settings.Secure.DOZE_PICK_UP_GESTURE, Settings.Secure.DOZE_PULSE_ON_LONG_PRESS, Settings.Secure.DOZE_DOUBLE_TAP_GESTURE, Settings.Secure.DOZE_WAKE_LOCK_SCREEN_GESTURE, Settings.Secure.DOZE_WAKE_DISPLAY_GESTURE, Settings.Secure.DOZE_TAP_SCREEN_GESTURE};
    private static final String[] NON_USER_CONFIGURABLE_DOZE_SETTINGS = {Settings.Secure.DOZE_QUICK_PICKUP_GESTURE};
    private static final String TAG = "AmbientDisplayConfig";
    private final boolean mAlwaysOnByDefault;
    private final Context mContext;
    private final boolean mPickupGestureEnabledByDefault;
    private final boolean mScreenOffUdfpsAvailable;
    final SparseArray<Map<String, String>> mUsersInitialValues = new SparseArray<>();

    public boolean alwaysOnAvailable() {
        return false;
    }

    public AmbientDisplayConfiguration(Context context) {
        this.mContext = context;
        this.mAlwaysOnByDefault = context.getResources().getBoolean(R.bool.config_dozeAlwaysOnEnabled);
        this.mPickupGestureEnabledByDefault = context.getResources().getBoolean(R.bool.config_dozePickupGestureEnabled);
        this.mScreenOffUdfpsAvailable = context.getResources().getBoolean(R.bool.config_screen_off_udfps_enabled);
    }

    public boolean enabled(int i) {
        return pulseOnNotificationEnabled(i) || pulseOnLongPressEnabled(i) || alwaysOnEnabled(i) || wakeLockScreenGestureEnabled(i) || wakeDisplayGestureEnabled(i) || pickupGestureEnabled(i) || tapGestureEnabled(i) || doubleTapGestureEnabled(i) || quickPickupSensorEnabled(i) || screenOffUdfpsEnabled(i);
    }

    public boolean pulseOnNotificationEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_ENABLED, i) && pulseOnNotificationAvailable();
    }

    public boolean pulseOnNotificationAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_pulseOnNotificationsAvailable) && ambientDisplayAvailable();
    }

    public boolean pickupGestureEnabled(int i) {
        return boolSetting(Settings.Secure.DOZE_PICK_UP_GESTURE, i, this.mPickupGestureEnabledByDefault ? 1 : 0) && dozePickupSensorAvailable();
    }

    public boolean dozePickupSensorAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_dozePulsePickup);
    }

    public boolean tapGestureEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_TAP_SCREEN_GESTURE, i) && tapSensorAvailable();
    }

    public boolean tapSensorAvailable() {
        for (String str : tapSensorTypeMapping()) {
            if (!TextUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean doubleTapGestureEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_DOUBLE_TAP_GESTURE, i) && doubleTapSensorAvailable();
    }

    public boolean doubleTapSensorAvailable() {
        return !TextUtils.isEmpty(doubleTapSensorType());
    }

    public boolean quickPickupSensorEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_QUICK_PICKUP_GESTURE, i) && !TextUtils.isEmpty(quickPickupSensorType()) && pickupGestureEnabled(i) && !alwaysOnEnabled(i);
    }

    public boolean screenOffUdfpsEnabled(int i) {
        if (TextUtils.isEmpty(udfpsLongPressSensorType())) {
            return false;
        }
        return (this.mScreenOffUdfpsAvailable && Flags.screenOffUnlockUdfps() && this.mContext.getResources().getBoolean(R.bool.config_screen_off_udfps_default_on)) ? boolSettingDefaultOn(Settings.Secure.SCREEN_OFF_UNLOCK_UDFPS_ENABLED, i) : boolSettingDefaultOff(Settings.Secure.SCREEN_OFF_UNLOCK_UDFPS_ENABLED, i);
    }

    public boolean wakeScreenGestureAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_dozeWakeLockScreenSensorAvailable);
    }

    public boolean wakeLockScreenGestureEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_WAKE_LOCK_SCREEN_GESTURE, i) && wakeScreenGestureAvailable();
    }

    public boolean wakeDisplayGestureEnabled(int i) {
        return boolSettingDefaultOn(Settings.Secure.DOZE_WAKE_DISPLAY_GESTURE, i) && wakeScreenGestureAvailable();
    }

    public long getWakeLockScreenDebounce() {
        return this.mContext.getResources().getInteger(R.integer.config_dozeWakeLockScreenDebounce);
    }

    public String doubleTapSensorType() {
        return this.mContext.getResources().getString(R.string.config_dozeDoubleTapSensorType);
    }

    public String[] tapSensorTypeMapping() {
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.config_dozeTapSensorPostureMapping);
        return ArrayUtils.isEmpty(stringArray) ? new String[]{this.mContext.getResources().getString(R.string.config_dozeTapSensorType)} : stringArray;
    }

    public String longPressSensorType() {
        return this.mContext.getResources().getString(R.string.config_dozeLongPressSensorType);
    }

    public String udfpsLongPressSensorType() {
        return this.mContext.getResources().getString(R.string.config_dozeUdfpsLongPressSensorType);
    }

    public String quickPickupSensorType() {
        return this.mContext.getResources().getString(R.string.config_quickPickupSensorType);
    }

    public boolean pulseOnLongPressEnabled(int i) {
        return pulseOnLongPressAvailable() && boolSettingDefaultOff(Settings.Secure.DOZE_PULSE_ON_LONG_PRESS, i);
    }

    private boolean pulseOnLongPressAvailable() {
        return !TextUtils.isEmpty(longPressSensorType());
    }

    public boolean alwaysOnEnabled(int i) {
        return boolSetting(Settings.Secure.DOZE_ALWAYS_ON, i, this.mAlwaysOnByDefault ? 1 : 0) && alwaysOnAvailable() && !accessibilityInversionEnabled(i);
    }

    public boolean alwaysOnAvailableForUser(int i) {
        return alwaysOnAvailable() && !accessibilityInversionEnabled(i);
    }

    public String ambientDisplayComponent() {
        return this.mContext.getResources().getString(R.string.config_dozeComponent);
    }

    public boolean accessibilityInversionEnabled(int i) {
        return boolSettingDefaultOff(Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, i);
    }

    public boolean ambientDisplayAvailable() {
        return !TextUtils.isEmpty(ambientDisplayComponent());
    }

    public boolean dozeSuppressed(int i) {
        return boolSettingDefaultOff(Settings.Secure.SUPPRESS_DOZE, i);
    }

    private boolean alwaysOnDisplayAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable);
    }

    private boolean alwaysOnDisplayDebuggingEnabled() {
        return SystemProperties.getBoolean("debug.doze.aod", false) && Build.IS_DEBUGGABLE;
    }

    private boolean boolSettingDefaultOn(String str, int i) {
        return boolSetting(str, i, 1);
    }

    private boolean boolSettingDefaultOff(String str, int i) {
        return boolSetting(str, i, 0);
    }

    private boolean boolSetting(String str, int i, int i2) {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, i2, i) != 0;
    }

    public void disableDozeSettings(int i) {
        disableDozeSettings(false, i);
    }

    public void disableDozeSettings(boolean z, int i) {
        Map<String, String> map = this.mUsersInitialValues.get(i);
        if (map != null && !map.isEmpty()) {
            throw new IllegalStateException("Don't call #disableDozeSettings more than once,without first calling #restoreDozeSettings");
        }
        ArrayMap arrayMap = new ArrayMap();
        for (String str : DOZE_SETTINGS) {
            arrayMap.put(str, getDozeSetting(str, i));
            putDozeSetting(str, "0", i);
        }
        if (z) {
            for (String str2 : NON_USER_CONFIGURABLE_DOZE_SETTINGS) {
                arrayMap.put(str2, getDozeSetting(str2, i));
                putDozeSetting(str2, "0", i);
            }
        }
        this.mUsersInitialValues.put(i, arrayMap);
    }

    public void restoreDozeSettings(int i) {
        Map<String, String> map = this.mUsersInitialValues.get(i);
        if (map == null || map.isEmpty()) {
            return;
        }
        for (String str : DOZE_SETTINGS) {
            putDozeSetting(str, map.get(str), i);
        }
        this.mUsersInitialValues.remove(i);
    }

    private String getDozeSetting(String str, int i) {
        return Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
    }

    private void putDozeSetting(String str, String str2, int i) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i);
    }
}
