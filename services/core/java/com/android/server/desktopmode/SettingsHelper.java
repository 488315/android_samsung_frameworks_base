package com.android.server.desktopmode;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SettingsHelper {
    public static final Map DEFAULT_VALUES;
    public static final Map KEYS_TO_MIGRATE;
    public final Context mContext;
    public final Injector mInjector;
    public final List mListeners = new CopyOnWriteArrayList();
    public final ContentResolver mResolver;
    public final AnonymousClass1 mStateListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class OnSettingChangedListener {
        public final String mInterestedKey;

        public OnSettingChangedListener(String str) {
            this.mInterestedKey = str;
        }

        public abstract void onSettingChanged(String str);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            super.onChange(z, uri, i);
            String lastPathSegment = uri.getLastPathSegment();
            String settingsAsUser = DesktopModeSettings.getSettingsAsUser(SettingsHelper.this.mResolver, lastPathSegment, (String) null, i);
            if (DesktopModeFeature.DEBUG) {
                Map map = SettingsHelper.KEYS_TO_MIGRATE;
                StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "Setting ", lastPathSegment, " for user ", " changed to=");
                m.append(settingsAsUser);
                Log.d("[DMS]SettingsHelper", m.toString());
            }
            if (i != 0 && ((ArraySet) DesktopModeSettings.SETTINGS_GLOBAL_KEYS).contains(lastPathSegment)) {
                DesktopModeSettings.setSettingsAsUser(SettingsHelper.this.mResolver, lastPathSegment, settingsAsUser, 0);
            }
            Iterator it = ((CopyOnWriteArrayList) SettingsHelper.this.mListeners).iterator();
            while (it.hasNext()) {
                OnSettingChangedListener onSettingChangedListener = (OnSettingChangedListener) it.next();
                if (lastPathSegment.equals(onSettingChangedListener.mInterestedKey)) {
                    onSettingChangedListener.onSettingChanged(settingsAsUser);
                }
            }
        }
    }

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
            public final void onDualModeStopLoadingScreen(boolean z) {
                if (z) {
                    SettingsHelper.this.setToDefaultIfNoSettings();
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public final void onStopLoadingScreen(boolean z) {
                if (z) {
                    SettingsHelper.this.setToDefaultIfNoSettings();
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public final void onUserChanged(StateManager.InternalState internalState) {
                int i = internalState.mCurrentUserId;
                SettingsHelper settingsHelper = SettingsHelper.this;
                if (i != 0) {
                    ContentResolver contentResolver = settingsHelper.mResolver;
                    Iterator it = ((ArraySet) DesktopModeSettings.SETTINGS_GLOBAL_KEYS).iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        String settingsAsUser = DesktopModeSettings.getSettingsAsUser(contentResolver, str, (String) null, 0);
                        if (settingsAsUser != null) {
                            DesktopModeSettings.setSettingsAsUser(contentResolver, str, settingsAsUser, i);
                        }
                    }
                } else {
                    settingsHelper.getClass();
                }
                DesktopModeSettings.sCurrentUserId = i;
                if (settingsHelper.mContext.getPackageManager().isUpgrade() && i == 0) {
                    for (Map.Entry entry : ((ArrayMap) SettingsHelper.KEYS_TO_MIGRATE).entrySet()) {
                        String str2 = "persist.service.dex." + ((String) entry.getKey());
                        String str3 = SystemProperties.get(str2, "");
                        if (!"".equals(str3)) {
                            DesktopModeSettings.setSettings(settingsHelper.mResolver, (String) entry.getValue(), str3);
                            SystemProperties.set(str2, (String) null);
                        }
                    }
                }
                int i2 = internalState.mDesktopModeState.enabled;
                settingsHelper.backupOrRestoreSettings(i2 == 3 || i2 == 4, internalState, internalState.mCurrentUserId);
                if (DesktopModeFeature.SUPPORT_SFC) {
                    settingsHelper.backupOrRestoreSuperFastCharging(internalState.mCurrentUserId, internalState.mDockState.isDexPad());
                }
            }
        };
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mResolver = contentResolver;
        ((StateManager) iStateManager).registerListener(stateListener);
        this.mInjector = injector;
        contentResolver.registerContentObserver(DesktopModeSettings.CONTENT_URI, true, new SettingsObserver(new Handler(serviceThread.getLooper())), -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void backupOrRestoreSettings(boolean r10, com.android.server.desktopmode.StateManager.InternalState r11, int r12) {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.SettingsHelper.backupOrRestoreSettings(boolean, com.android.server.desktopmode.StateManager$InternalState, int):void");
    }

    public final void backupOrRestoreSuperFastCharging(int i, boolean z) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z) {
            int intForUser = Settings.System.getIntForUser(this.mResolver, "super_fast_charging", 1, i);
            boolean z2 = DesktopModeFeature.DEBUG;
            if (z2) {
                DesktopModeService$$ExternalSyntheticOutline0.m(intForUser, "backupOrRestoreFastCharging(), current normalSfcValue=", "[DMS]SettingsHelper");
            }
            if (intForUser == 0) {
                DesktopModeSettings.setSettingsAsUser(this.mResolver, "super_fast_charging_backup", intForUser, i);
                Settings.System.putIntForUser(this.mResolver, "super_fast_charging", 1, i);
                if (z2) {
                    Log.d("[DMS]SettingsHelper", "backupOrRestoreFastCharging(), Enabling super fast charging");
                }
            }
        } else {
            int settingsAsUser = DesktopModeSettings.getSettingsAsUser(this.mResolver, "super_fast_charging_backup", 1, i);
            if (settingsAsUser == 0) {
                if (DesktopModeFeature.DEBUG) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(settingsAsUser, "backupOrRestoreFastCharging(), Restoring backed up value=", "[DMS]SettingsHelper");
                }
                Settings.System.putIntForUser(this.mResolver, "super_fast_charging", 0, i);
                DesktopModeSettings.deleteSettingsAsUser(this.mResolver, "super_fast_charging_backup", i);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void clearSettingsByLauncherDataCleared(StateManager.InternalState internalState, int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]SettingsHelper", "clearSettingsByLauncherDataCleared()");
        }
        try {
            this.mResolver.call(DesktopModeSettings.getUriAsUser(i), "clearSettings", (String) null, (Bundle) null);
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to clear Dex settings", e);
        }
        SemDesktopModeState semDesktopModeState = internalState.mDesktopModeState;
        if (semDesktopModeState.enabled == 4) {
            if (semDesktopModeState.getDisplayType() == 101) {
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]SettingsHelper", "set Dex default on virtual keyboard in standalone");
                }
                Settings.Secure.putIntForUser(this.mResolver, "show_ime_with_hard_keyboard", 0, i);
            } else if (semDesktopModeState.getDisplayType() == 102) {
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]SettingsHelper", "set the setting for touchkeyboard, isDexStationCoverAttached=" + internalState.isDexStationConnectedWithFlipCover());
                }
                DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard", !internalState.isDexStationConnectedWithFlipCover(), i);
                if (internalState.isDexStationConnectedWithFlipCover()) {
                    DesktopModeSettings.setSettingsAsUser(this.mResolver, "touch_keyboard_backup", true, i);
                }
                setToDefaultIfNoSettings();
            }
        }
        Settings.System.putInt(this.mResolver, "display_chooser_do_not_show_again", 0);
    }

    public final void registerListener(OnSettingChangedListener onSettingChangedListener) {
        if (onSettingChangedListener == null || ((CopyOnWriteArrayList) this.mListeners).contains(onSettingChangedListener)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mListeners).add(onSettingChangedListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPsmValue(int r6) {
        /*
            r5 = this;
            android.content.ContentResolver r0 = r5.mResolver
            java.lang.String r1 = "restricted_device_performance"
            java.lang.String r0 = android.provider.Settings.Global.getString(r0, r1)
            java.lang.String r2 = "[DMS]SettingsHelper"
            if (r0 == 0) goto L2a
            r3 = 44
            int r3 = r0.indexOf(r3)
            if (r3 <= 0) goto L2a
            int r3 = r3 + 1
            java.lang.String r0 = r0.substring(r3)     // Catch: java.lang.NumberFormatException -> L24
            java.lang.String r0 = r0.trim()     // Catch: java.lang.NumberFormatException -> L24
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L24
            goto L2b
        L24:
            r0 = move-exception
            java.lang.String r3 = "NumberFormatException in setPsmValue"
            com.android.server.desktopmode.Log.e(r2, r3, r0)
        L2a:
            r0 = 0
        L2b:
            android.content.ContentResolver r5 = r5.mResolver
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            java.lang.String r4 = ", "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            android.provider.Settings.Global.putString(r5, r1, r3)
            boolean r5 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
            if (r5 == 0) goto L60
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r1 = "setPsmValue = "
            r5.<init>(r1)
            r5.append(r6)
            r5.append(r4)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.android.server.desktopmode.Log.d(r2, r5)
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.SettingsHelper.setPsmValue(int):void");
    }

    public final void setToDefaultIfNoSettings() {
        for (Map.Entry entry : ((ArrayMap) DEFAULT_VALUES).entrySet()) {
            String str = (String) entry.getKey();
            if (DesktopModeSettings.getSettingsAsUser(this.mResolver, str, (String) null, DesktopModeSettings.sCurrentUserId) == null) {
                DesktopModeSettings.setSettings(this.mResolver, str, (String) entry.getValue());
            }
        }
    }

    public final void unregisterListener(OnSettingChangedListener onSettingChangedListener) {
        if (onSettingChangedListener != null) {
            ((CopyOnWriteArrayList) this.mListeners).remove(onSettingChangedListener);
        }
    }
}
