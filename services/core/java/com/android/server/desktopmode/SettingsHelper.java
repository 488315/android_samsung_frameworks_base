package com.android.server.desktopmode;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class SettingsHelper {
    public static final Map DEFAULT_VALUES;
    public static final Map KEYS_TO_MIGRATE;
    public static final String TAG = "[DMS]" + SettingsHelper.class.getSimpleName();
    public final Context mContext;
    public final Injector mInjector;
    public final List mListeners = new CopyOnWriteArrayList();
    public final ContentResolver mResolver;
    public final StateManager.StateListener mStateListener;
    public final IStateManager mStateManager;

    static {
        ArrayMap arrayMap = new ArrayMap();
        KEYS_TO_MIGRATE = arrayMap;
        arrayMap.put("hdmi", "hdmi_auto_enter");
        arrayMap.put("keyboard", "keyboard_dex");
        arrayMap.put("keyboard_b", "keyboard_backup");
        arrayMap.put("timeout", "timeout_dex");
        arrayMap.put("timeout_b", "timeout_backup");
        arrayMap.put("developer", "launch_policy_developer_enabled");
        ArrayMap arrayMap2 = new ArrayMap();
        DEFAULT_VALUES = arrayMap2;
        arrayMap2.put("flow_pointer_is_on_dex", "false");
        arrayMap2.put("flow_pointer_from_where_dex", "right");
        arrayMap2.put("spen_mode", "mouse");
        arrayMap2.put("spen_input_change_support", "true");
        arrayMap2.put("touch_keyboard", "true");
    }

    public SettingsHelper(Context context, ServiceThread serviceThread, IStateManager iStateManager, Injector injector) {
        StateManager.StateListener stateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.SettingsHelper.1
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onUserChanged(State state) {
                SettingsHelper.this.setCurrentUserId(state.getCurrentUserId());
                SemDesktopModeState desktopModeState = state.getDesktopModeState();
                SettingsHelper settingsHelper = SettingsHelper.this;
                int i = desktopModeState.enabled;
                settingsHelper.backupOrRestoreSettings(i == 3 || i == 4, state, state.getCurrentUserId());
                if (DesktopModeFeature.SUPPORT_SFC) {
                    SettingsHelper.this.backupOrRestoreSuperFastCharging(state.getDockState().isDexPad(), state.getCurrentUserId());
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onStopLoadingScreen(boolean z) {
                if (z) {
                    SettingsHelper.this.setToDefaultIfNoSettings();
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onDualModeStopLoadingScreen(boolean z) {
                if (z) {
                    SettingsHelper.this.setToDefaultIfNoSettings();
                }
            }
        };
        this.mStateListener = stateListener;
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mResolver = contentResolver;
        this.mStateManager = iStateManager;
        iStateManager.registerListener(stateListener);
        this.mInjector = injector;
        contentResolver.registerContentObserver(DesktopModeSettings.getUri(), true, new SettingsObserver(new Handler(serviceThread.getLooper())), -1);
    }

    public void backupOrRestoreSettings(boolean z, State state) {
        backupOrRestoreSettings(z, state, state.getCurrentUserId());
    }

    public void backupOrRestoreSettings(boolean z, State state, int i) {
        boolean z2 = false;
        if (z == DesktopModeSettings.getSettingsAsUser(this.mResolver, "enabled", false, i)) {
            if (DesktopModeFeature.DEBUG) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("backupOrRestoreSettings(), enter=");
                sb.append(z);
                sb.append(", userId=");
                sb.append(i);
                sb.append(", Already ");
                sb.append(z ? "backed up!" : "restored!");
                Log.m7d(str, sb.toString());
                return;
            }
            return;
        }
        int displayType = state.getDesktopModeState().getDisplayType();
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "backupOrRestoreSettings(), enter=" + z + ", displayType=" + SemDesktopModeState.displayTypeToString(displayType) + ", userId=" + i);
        }
        if (!z && displayType == 0) {
            z2 = true;
        }
        if (z2 || displayType == 102) {
            setScreensaver(z, i);
            setVirtualKeyboard(z, state, i);
            setTouchKeyboard(z, state, i);
        }
        setPsmSpeedLimit(z, i);
        DesktopModeSettings.setSettingsAsUser(this.mResolver, "enabled", z, i);
    }

    public void clearSettingsByLauncherDataCleared(State state, int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "clearSettingsByLauncherDataCleared()");
        }
        DesktopModeSettings.clearSettingsAsUser(this.mResolver, i);
        SemDesktopModeState desktopModeState = state.getDesktopModeState();
        if (desktopModeState.enabled == 4) {
            if (desktopModeState.getDisplayType() == 101) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "set Dex default on virtual keyboard in standalone");
                }
                setNormalModeVirtualKeyboard(0, i);
            } else if (desktopModeState.getDisplayType() == 102) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "set the setting for touchkeyboard, isDexStationCoverAttached=" + state.isDexStationConnectedWithFlipCover());
                }
                DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard", !state.isDexStationConnectedWithFlipCover(), i);
                if (state.isDexStationConnectedWithFlipCover()) {
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard_backup", true, i);
                }
                setToDefaultIfNoSettings();
            }
        }
        Settings.System.putInt(this.mResolver, "display_chooser_do_not_show_again", 0);
    }

    public void setDefaultSettingsInRetailMode(State state, int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDefaultSettingsInRetailMode()");
        }
        if (state.getDesktopModeState().enabled == 4) {
            DesktopModeSettings.setSettingsAsUser(this.mResolver, "keyboard_dex", 1, i);
            Settings.System.putIntForUser(this.mResolver, "primary_mouse_button_option", 0, i);
        }
    }

    public void registerListener(OnSettingChangedListener onSettingChangedListener) {
        if (onSettingChangedListener == null || this.mListeners.contains(onSettingChangedListener)) {
            return;
        }
        this.mListeners.add(onSettingChangedListener);
    }

    public void unregisterListener(OnSettingChangedListener onSettingChangedListener) {
        if (onSettingChangedListener != null) {
            this.mListeners.remove(onSettingChangedListener);
        }
    }

    public void setCurrentUserId(int i) {
        if (i != 0) {
            DesktopModeSettings.applyGlobalSettings(this.mResolver, i);
        }
        DesktopModeSettings.setCurrentUserId(i);
        if (this.mContext.getPackageManager().isUpgrade() && i == 0) {
            startMigration();
        }
    }

    public final void startMigration() {
        for (Map.Entry entry : KEYS_TO_MIGRATE.entrySet()) {
            String str = "persist.service.dex." + ((String) entry.getKey());
            String str2 = SystemProperties.get(str, "");
            if (!"".equals(str2)) {
                DesktopModeSettings.setSettings(this.mResolver, (String) entry.getValue(), str2);
                SystemProperties.set(str, (String) null);
            }
        }
    }

    public final void setToDefaultIfNoSettings() {
        for (Map.Entry entry : DEFAULT_VALUES.entrySet()) {
            String str = (String) entry.getKey();
            if (!DesktopModeSettings.contains(this.mResolver, str)) {
                DesktopModeSettings.setSettings(this.mResolver, str, (String) entry.getValue());
            }
        }
    }

    public final void setVirtualKeyboard(boolean z, State state, int i) {
        if (z) {
            if (state.isDexOnPcConnected()) {
                int intForUser = Settings.Secure.getIntForUser(this.mResolver, "show_ime_with_hard_keyboard", 1, i);
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "setVirtualKeyboard(), current normalModeValue=" + intForUser);
                }
                if (intForUser == 0) {
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "keyboard_backup", intForUser, i);
                    setNormalModeVirtualKeyboard(1, i);
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(TAG, "setVirtualKeyboard(), Enabling to show onScreenKeyboard");
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        int settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "keyboard_backup", 1, i);
        if (settingsAsUser == 0) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "setVirtualKeyboard(), Restoring backed up value=" + settingsAsUser);
            }
            setNormalModeVirtualKeyboard(0, i);
            DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "keyboard_backup", i);
        }
    }

    public final void setNormalModeVirtualKeyboard(int i, int i2) {
        Settings.Secure.putIntForUser(this.mResolver, "show_ime_with_hard_keyboard", i, i2);
    }

    public final void setTouchKeyboard(boolean z, State state, int i) {
        if (z) {
            if (state.isDexStationConnectedWithFlipCover()) {
                boolean settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "touch_keyboard", true, i);
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "setTouchKeyboard(), currentValue=" + settingsAsUser);
                }
                if (settingsAsUser) {
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard_backup", settingsAsUser, i);
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard", false, i);
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(TAG, "setTouchKeyboard(), Enabling to show on dex display");
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        boolean settingsAsUser2 = DesktopModeSettings.getSettingsAsUser(this.mResolver, "touch_keyboard_backup", false, i);
        if (settingsAsUser2) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "setTouchKeyboard(), Restoring backed up value=" + settingsAsUser2);
            }
            DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard", true, i);
            DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "touch_keyboard_backup", i);
        }
    }

    public final void setPsmSpeedLimit(boolean z, int i) {
        if (isLowPowerMode()) {
            if (z) {
                int psmValue = getPsmValue("restricted_device_performance");
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "setPsmSpeedLimit(), current value=" + psmValue);
                }
                if (psmValue == 1) {
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "speed_limit_backup", psmValue, i);
                    setPsmValue("restricted_device_performance", 0);
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(TAG, "setPsmSpeedLimit(), Disabling speed limit");
                        return;
                    }
                    return;
                }
                return;
            }
            int settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "speed_limit_backup", 0, i);
            if (settingsAsUser == 1) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "setPsmSpeedLimit(), Restoring backed up value=" + settingsAsUser);
                }
                setPsmValue("restricted_device_performance", settingsAsUser);
                DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "speed_limit_backup", i);
            }
        }
    }

    public final int getPsmValue(String str) {
        int indexOf;
        String string = Settings.Global.getString(this.mResolver, str);
        if (string == null || (indexOf = string.indexOf(44)) <= 0) {
            return -1;
        }
        try {
            return Integer.parseInt(string.substring(0, indexOf));
        } catch (NumberFormatException e) {
            Log.m9e(TAG, "NumberFormatException in getPsmValue", e);
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setPsmValue(String str, int i) {
        int i2;
        int indexOf;
        String string = Settings.Global.getString(this.mResolver, str);
        if (string != null && (indexOf = string.indexOf(44)) > 0) {
            try {
                i2 = Integer.parseInt(string.substring(indexOf + 1).trim());
            } catch (NumberFormatException e) {
                Log.m9e(TAG, "NumberFormatException in setPsmValue", e);
            }
            Settings.Global.putString(this.mResolver, str, i + ", " + i2);
            if (DesktopModeFeature.DEBUG) {
                return;
            }
            Log.m7d(TAG, "setPsmValue = " + i + ", " + i2);
            return;
        }
        i2 = 0;
        Settings.Global.putString(this.mResolver, str, i + ", " + i2);
        if (DesktopModeFeature.DEBUG) {
        }
    }

    public final boolean isLowPowerMode() {
        return Settings.Global.getInt(this.mResolver, "low_power", 0) != 0;
    }

    public final void setScreensaver(boolean z, int i) {
        if (z) {
            int intForUser = Settings.Secure.getIntForUser(this.mResolver, "screensaver_enabled", 0, i);
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "setScreenSaver(), current value=" + intForUser);
            }
            if (intForUser == 1) {
                DesktopModeSettings.setSettingsAsUser(this.mResolver, "screensaver_backup", intForUser, i);
                setScreensaverValue(0, i);
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "setScreenSaver(), Disabling screen saver");
                    return;
                }
                return;
            }
            return;
        }
        int settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "screensaver_backup", 0, i);
        if (settingsAsUser == 1) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "setScreensaver(), Restoring backed up value=" + settingsAsUser);
            }
            setScreensaverValue(settingsAsUser, i);
            DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "screensaver_backup", i);
        }
    }

    public final void setScreensaverValue(int i, int i2) {
        Settings.Secure.putIntForUser(this.mResolver, "screensaver_enabled", i, i2);
    }

    public void backupOrRestoreSuperFastCharging(boolean z, int i) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        if (z) {
            int superFastChargingValue = getSuperFastChargingValue(i);
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "backupOrRestoreFastCharging(), current normalSfcValue=" + superFastChargingValue);
            }
            if (superFastChargingValue == 0) {
                DesktopModeSettings.setSettingsAsUser(this.mResolver, "super_fast_charging_backup", superFastChargingValue, i);
                setSuperFastChargingValue(1, i);
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "backupOrRestoreFastCharging(), Enabling super fast charging");
                }
            }
        } else {
            int settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "super_fast_charging_backup", 1, i);
            if (settingsAsUser == 0) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "backupOrRestoreFastCharging(), Restoring backed up value=" + settingsAsUser);
                }
                setSuperFastChargingValue(0, i);
                DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "super_fast_charging_backup", i);
            }
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }

    public final int getSuperFastChargingValue(int i) {
        return Settings.System.getIntForUser(this.mResolver, "super_fast_charging", 1, i);
    }

    public final void setSuperFastChargingValue(int i, int i2) {
        Settings.System.putIntForUser(this.mResolver, "super_fast_charging", i, i2);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, ContentResolver contentResolver, int i) {
        DesktopModeSettings.dump(indentingPrintWriter, contentResolver, i);
    }

    public class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            super.onChange(z, uri, i);
            String lastPathSegment = uri.getLastPathSegment();
            String settingsAsUser = DesktopModeSettings.getSettingsAsUser(SettingsHelper.this.mResolver, lastPathSegment, (String) null, i);
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(SettingsHelper.TAG, "Setting " + lastPathSegment + " for user " + i + " changed to=" + settingsAsUser);
            }
            if (i != 0 && DesktopModeSettings.isGlobalKey(lastPathSegment)) {
                DesktopModeSettings.setSettingsAsUser(SettingsHelper.this.mResolver, lastPathSegment, settingsAsUser, 0);
            }
            for (OnSettingChangedListener onSettingChangedListener : SettingsHelper.this.mListeners) {
                if (lastPathSegment.equals(onSettingChangedListener.mInterestedKey)) {
                    onSettingChangedListener.onSettingChanged(settingsAsUser, i);
                }
            }
        }
    }

    public abstract class OnSettingChangedListener {
        public final String mInterestedKey;

        public abstract void onSettingChanged(String str, int i);

        public OnSettingChangedListener(String str) {
            this.mInterestedKey = str;
        }
    }
}
