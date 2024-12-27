package com.android.systemui.pluginlock;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.component.PluginLockShortcutDnd;
import com.android.systemui.pluginlock.component.PluginLockShortcutFlashLight;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginLockManagerImpl implements PluginLockManager, KeyguardListener.SPlugin, KeyguardListener.UserSwitch, DesktopManager.Callback, Dumpable {
    private static final String DO_NOT_DISTURB_TASK = "Dnd";
    private static final String FLASH_LIGHT_TASK = "Flashlight";
    private static final String KEY_ACTION = "action";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_NOTIFICATION_TYPE = "notification_type";
    private static final String KEY_NOTIFICATION_VISIBILITY = "notification_visibility";
    private static final String KEY_SHORTCUT_VISIBILITY = "shortcut_visibility";
    private static final String LOCKSTAR_FACEWIDGET_AREA = "lockstar_facewidget_area";
    private static final String NOTIFICATION_ENABLED = "lock_screen_show_notifications";
    private static final String NOTIFICATION_TYPE = "lockscreen_minimizing_notification";
    private static final String PLUGIN_LOCK_SETTINGS = "lockstar_enabled";
    private static final String PLUGIN_LOCK_SETTINGS_SUB = "plugin_lock_sub_enabled";
    private static final String SETTINGS_EMERGENCY_MODE = "emergency_mode";
    private static final String SETTINGS_LONG_TOUCH_AND_HOLD_TO_EDIT = "lock_editor_support_touch_hold";
    private static final String SETTINGS_SEM_MINIMAL_BATTERY_USE = "minimal_battery_use";
    private static final String SETTINGS_SEM_ULTRA_POWERSAVING_MODE = "ultra_powersaving_mode";
    private static final String SHORTCUT_ENABLED = "lockscreen_show_shortcut";
    private static final String TAG = "PluginLockManagerImpl";
    private static final String UPDATE_LOCKSTAR_DATA = "update_lockstar_data";
    private static final String UPDATE_LOCKSTAR_DATA_ITEM = "update_lockstar_data_item";
    private final Context mContext;
    private CoverState mCoverState;
    private final ContentResolver mCr;
    private HashMap<Integer, Integer> mCurrentPluginValueMap;
    private final PluginLockDelegateApp mDelegateApp;
    private final PluginLockDelegateSysUi mDelegateSysUi;
    private PluginLockInstanceState mInstanceState;
    private boolean mIsDynamicEnabled;
    private boolean mIsMigrating;
    private boolean mIsSwitchingToSub;
    private final HashMap<String, PluginLockInstanceState> mLockPluginMap;
    private final PluginLockMediator mMediator;
    private PluginLock mPluginLock;
    private final PluginWallpaperManager mPluginWallpaperManager;
    private final PluginLockInstancePolicy mPolicy;
    private String mRemovedPackageName;
    private final int[] mScreenList;
    private int mScreenType;
    private final SettingsHelper.OnChangedCallback mSettingsCallback;
    private final SettingsHelper mSettingsHelper;
    private PluginLockShortcutDnd mTaskDnd;
    private PluginLockShortcutFlashLight mTaskFlashLight;
    private final Uri[] mUris;
    private int mUserId;
    private final PluginLockUtils mUtils;

    public PluginLockManagerImpl(PluginLockMediator pluginLockMediator, PluginLockInstancePolicy pluginLockInstancePolicy, PluginLockDelegateApp pluginLockDelegateApp, PluginLockDelegateSysUi pluginLockDelegateSysUi, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, PluginWallpaperManager pluginWallpaperManager, KeyguardFoldController keyguardFoldController, DesktopManager desktopManager, Context context) {
        Uri[] uriArr = {Settings.System.getUriFor("lockstar_enabled"), Settings.System.getUriFor("plugin_lock_sub_enabled"), Settings.System.getUriFor("emergency_mode"), Settings.System.getUriFor("ultra_powersaving_mode"), Settings.System.getUriFor("minimal_battery_use"), Settings.Secure.getUriFor("lock_screen_show_notifications"), Settings.System.getUriFor("lockscreen_minimizing_notification"), Settings.System.getUriFor("lockscreen_show_shortcut")};
        this.mUris = uriArr;
        this.mLockPluginMap = new HashMap<>();
        this.mRemovedPackageName = null;
        this.mIsMigrating = false;
        this.mCurrentPluginValueMap = new HashMap<>();
        this.mScreenType = 0;
        this.mIsSwitchingToSub = false;
        this.mUserId = 0;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.pluginlock.PluginLockManagerImpl$$ExternalSyntheticLambda3
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PluginLockManagerImpl.this.lambda$new$0(uri);
            }
        };
        this.mSettingsCallback = onChangedCallback;
        this.mMediator = pluginLockMediator;
        this.mPolicy = pluginLockInstancePolicy;
        this.mDelegateSysUi = pluginLockDelegateSysUi;
        this.mDelegateApp = pluginLockDelegateApp;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mUtils = pluginLockUtils;
        pluginLockUtils.addDump(TAG, "## PluginLockManager ##, " + this);
        pluginLockMediator.setKeyguardSPluginListener(this);
        pluginLockMediator.setKeyguardUserSwitchListener(this);
        pluginLockMediator.setKeyguardBasicListener(pluginLockDelegateApp);
        if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER) {
            desktopManager.registerCallback(this);
        }
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            this.mScreenList = new int[]{0, 1};
        } else {
            this.mScreenList = new int[]{0};
        }
        boolean isOwnerProcess = isOwnerProcess();
        if (settingsHelper != null && isOwnerProcess) {
            settingsHelper.registerCallback(onChangedCallback, uriArr);
        }
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE) {
            try {
                if (WallpaperManager.getInstance(context).getLidState() == 0) {
                    Log.d(TAG, "PluginLockManager: mScreenType = PluginLock.SCREEN_SUB");
                    this.mScreenType = 1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            ScoverManager scoverManager = new ScoverManager(context);
            if (scoverManager.getCoverState() != null && !scoverManager.getCoverState().switchState) {
                Log.d(TAG, "PluginLockManager, virtual display: mScreenType = PluginLock.SCREEN_SUB");
                this.mScreenType = 1;
            }
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.pluginlock.PluginLockManagerImpl$$ExternalSyntheticLambda4
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    PluginLockManagerImpl.this.onFolderStateChanged(z);
                }
            }, 1000, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableByMode() {
        for (int i : this.mScreenList) {
            if (isEnabledFromSettingHelper(i)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "disableByMode, screen: ", TAG);
                this.mSettingsHelper.setPluginLockValue(i, PluginLockInstancePolicy.DISABLED_BY_MODE);
                updatePluginLockMode(i, false, false);
            }
        }
    }

    private void disableByUser() {
        for (int i : this.mScreenList) {
            if (isEnabledFromSettingHelper(i)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "disableByUser, screen: ", TAG);
                this.mSettingsHelper.setPluginLockValue(i, PluginLockInstancePolicy.DISABLED_BY_SUB_USER);
                updatePluginLockMode(i, false, false);
            }
        }
    }

    private boolean disableForcedIfNeed(int i) {
        PluginLockInstanceState pluginLockInstanceState;
        int pluginLockValue = this.mSettingsHelper.getPluginLockValue(i);
        int currentPluginValue = getCurrentPluginValue(i);
        if (!this.mPolicy.isEnable(pluginLockValue) || !this.mPolicy.isEnable(currentPluginValue) || this.mPolicy.isSameInstance(currentPluginValue, pluginLockValue) || (pluginLockInstanceState = this.mInstanceState) == null || !pluginLockInstanceState.isEnabled(i)) {
            return false;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(currentPluginValue, "disableForcedIfNeed() disabled ", TAG);
        updatePluginLockMode(i, false, true);
        return true;
    }

    private int getCurrentPluginValue(int i) {
        Integer num = this.mCurrentPluginValueMap.get(Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    private void handleEmergencyModeChanged() {
        if (this.mSettingsHelper.isEmergencyMode()) {
            Log.d(TAG, "handleEmergencyModeChanged, enabled");
            disableByMode();
        }
    }

    private void handleEnableStateChanged(int i) {
        int pluginLockValue = this.mSettingsHelper.getPluginLockValue(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, pluginLockValue, "handleEnableStateChanged screen:", ", value:", TAG);
        if (pluginLockValue == 30000 || pluginLockValue == 20000) {
            Log.d(TAG, "handleEnableStateChanged: user switched or mode changed, ignore!");
            return;
        }
        if (!LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) {
            Log.d(TAG, "handleEnableStateChanged: not supported, skip!");
            return;
        }
        if (this.mIsMigrating && i == 1) {
            Log.d(TAG, "handleEnableStateChanged: migrating, skip!");
            this.mIsMigrating = false;
        } else if (disableForcedIfNeed(i)) {
            new Handler(Looper.getMainLooper()).post(new PluginLockManagerImpl$$ExternalSyntheticLambda0(this, i, 0));
        } else {
            updatePluginLockMode(i, isEnabledFromSettingHelper(i), false);
        }
    }

    private void handleLongTouchModeChanged() {
        if (this.mSettingsHelper.isSupportTouchAndHoldToEdit()) {
            Log.d(TAG, "handleLongTouchModeChanged");
            this.mMediator.updateWindowSecureState(false);
        }
    }

    private void handleMinimalBatteryModeChanged() {
        if (!this.mSettingsHelper.isUltraPowerSavingMode()) {
            setLatestPluginInstance(false);
        } else {
            Log.d(TAG, "handleMinimalBatteryModeChanged, enabled");
            disableByMode();
        }
    }

    private void handleStandaloneDexMode(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("handleStandaloneDexMode, dexEnabled:", TAG, z);
        if (z) {
            new Handler(Looper.getMainLooper()).post(new PluginLockManagerImpl$$ExternalSyntheticLambda1(this, 2));
        } else {
            setLatestPluginInstance(false);
        }
    }

    private boolean isDynamicLockEnabled() {
        return (!this.mIsDynamicEnabled || this.mInstanceState == null || this.mPluginLock == null || this.mUtils.isGoingToRescueParty()) ? false : true;
    }

    private boolean isEnabledFromSettingHelper(int i) {
        int pluginLockValue = this.mSettingsHelper.getPluginLockValue(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, pluginLockValue, "isEnabledFromSettingHelper, screen:", ", value:", TAG);
        return this.mPolicy.isEnable(pluginLockValue);
    }

    private boolean isOwnerProcess() {
        return UserHandle.semGetMyUserId() == 0;
    }

    private boolean isPluginLockPackage(String str) {
        return str.startsWith("com.samsung.android.dynamiclock") || str.startsWith("com.samsung.android.mateagent");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEnableStateChanged$1(int i) {
        updatePluginLockMode(i, isEnabledFromSettingHelper(i), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        Log.d(TAG, "SettingHelper changed uri: " + uri);
        if (uri == null) {
            Log.d(TAG, "uri null");
            return;
        }
        if (uri.equals(Settings.System.getUriFor("lockstar_enabled"))) {
            handleEnableStateChanged(0);
            return;
        }
        if (uri.equals(Settings.System.getUriFor("plugin_lock_sub_enabled"))) {
            handleEnableStateChanged(1);
            return;
        }
        if (uri.equals(Settings.System.getUriFor("emergency_mode"))) {
            handleEmergencyModeChanged();
        } else if (uri.equals(Settings.System.getUriFor("minimal_battery_use"))) {
            handleMinimalBatteryModeChanged();
        } else if (uri.equals(Settings.System.getUriFor("lock_editor_support_touch_hold"))) {
            handleLongTouchModeChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitchComplete$2() {
        Log.d(TAG, "onUserSwitchComplete for owner");
        setLatestPluginInstance(false);
        this.mIsSwitchingToSub = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitchComplete$3(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete for ", TAG);
        this.mIsSwitchingToSub = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPluginInstanceState$4() {
        this.mPluginWallpaperManager.onLockWallpaperChanged(0);
    }

    private void migration(PluginLockInstanceState pluginLockInstanceState) {
        int dataVersion = pluginLockInstanceState.getDataVersion();
        Log.d(TAG, "[migration] for [" + pluginLockInstanceState.getPackageName() + "]");
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("[migration] - savedVersion: "), dataVersion, ", currVersion:3", TAG);
        if (dataVersion < 3) {
            int pluginLockValue = this.mSettingsHelper.getPluginLockValue(0);
            int pluginLockValue2 = this.mSettingsHelper.getPluginLockValue(1);
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(pluginLockValue, pluginLockValue2, "[migration] - mainValue: ", ", subValue:", TAG);
            if (pluginLockValue == -1 || pluginLockValue2 != -1) {
                return;
            }
            if (!this.mPolicy.isSameInstance(pluginLockInstanceState.getAllowedNumber(), pluginLockValue)) {
                Log.d(TAG, "[migration] - not activated plugin");
            } else if (this.mPolicy.isEnable(pluginLockValue)) {
                Log.d(TAG, "[migration] - start!");
                pluginLockInstanceState.setStateData(1, this.mPolicy.isEnable(pluginLockValue));
                this.mIsMigrating = true;
                this.mSettingsHelper.setPluginLockValue(1, pluginLockValue);
            }
        }
    }

    private void notifyPluginLockModeChanged(PluginLock pluginLock, int i, boolean z) {
        if (pluginLock == null || pluginLock.getBasicManager() == null) {
            return;
        }
        try {
            if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                pluginLock.getBasicManager().onPluginLockModeChanged(i, z);
            } else {
                pluginLock.getBasicManager().onPluginLockModeChanged(z);
            }
        } catch (AbstractMethodError e) {
            Log.w(TAG, "notifyPluginLockMode, " + e.toString());
            pluginLock.getBasicManager().onPluginLockModeChanged(z);
        }
    }

    private void putPluginInstanceToMap(String str, PluginLockInstanceState pluginLockInstanceState) {
        if (this.mLockPluginMap.containsKey(str)) {
            this.mLockPluginMap.remove(str);
        }
        this.mLockPluginMap.put(str, pluginLockInstanceState);
        this.mPolicy.setCategory(pluginLockInstanceState);
        Log.d(TAG, "putPluginInstanceToMap, size " + this.mLockPluginMap.size() + ", packageName " + str);
    }

    private void resetConfigs() {
        this.mMediator.resetConfigs();
    }

    private void setCurrentPluginValue(int i, int i2) {
        this.mCurrentPluginValueMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    private void setLatestPluginInstance(int i, boolean z) {
        boolean isCurrentOwner = this.mUtils.isCurrentOwner();
        Log.d(TAG, "setLatestPluginInstance map size: " + this.mLockPluginMap.size() + ", isCurrentOwner:" + isCurrentOwner);
        if (isCurrentOwner) {
            long j = 0;
            Map.Entry<String, PluginLockInstanceState> entry = null;
            for (Map.Entry<String, PluginLockInstanceState> entry2 : this.mLockPluginMap.entrySet()) {
                PluginLockInstanceData.Data data = entry2.getValue().getData();
                if (data != null) {
                    Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data.getTimeStamps(i) : data.getTimeStamp();
                    if (timeStamps != null && j < timeStamps.longValue()) {
                        j = timeStamps.longValue();
                        entry = entry2;
                    }
                }
            }
            if (entry != null) {
                int allowedNumber = entry.getValue().getAllowedNumber() + (this.mSettingsHelper.getPluginLockValue(i) % 10 == 1 ? 2 : 1);
                RecyclerView$$ExternalSyntheticOutline0.m(allowedNumber, TAG, new StringBuilder("setLatestPluginInstance() set value:"));
                this.mSettingsHelper.setPluginLockValue(i, allowedNumber);
            } else {
                if (!z) {
                    PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
                    if (pluginLockDelegateApp != null) {
                        pluginLockDelegateApp.setBasicManager(null);
                        return;
                    }
                    return;
                }
                this.mUtils.addDump(TAG, "setLatestPluginInstance, screen:" + i + ", DISABLED_ALL");
                setPluginInstance(i, null);
                this.mSettingsHelper.setPluginLockValue(i, 0);
            }
        }
    }

    private void setPluginInstance(int i, PluginLockInstanceState pluginLockInstanceState) {
        setPluginInstance(i, pluginLockInstanceState, false);
    }

    private void updateEnabledState(int i, boolean z) {
        int currentPluginValue = z ? getCurrentPluginValue(i) : this.mSettingsHelper.getPluginLockValue(i);
        ListPopupWindow$$ExternalSyntheticOutline0.m(currentPluginValue, "updateEnabledState getPluginLockValue = ", TAG);
        boolean z2 = false;
        boolean z3 = currentPluginValue >= 10000;
        boolean z4 = !z && isEnabledFromSettingHelper(i);
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateEnabledState() isDynamicMode = ", ", isEnabledFromSetting = ", TAG, z3, z4);
        if (z3 && z4) {
            z2 = true;
        }
        this.mIsDynamicEnabled = z2;
    }

    private void updatePluginLockMode(int i, boolean z, boolean z2) {
        PluginLockInstanceState pluginLockInstanceState;
        String next;
        PluginLockInstanceState pluginLockInstanceState2;
        PluginLockInstanceState pluginLockInstanceState3;
        this.mUtils.addDump(TAG, "[PluginLock Switching] start");
        int currentPluginValue = z2 ? getCurrentPluginValue(i) : this.mSettingsHelper.getPluginLockValue(i);
        boolean z3 = this.mIsDynamicEnabled;
        if (!this.mUtils.isCurrentOwner() && currentPluginValue != 30000) {
            Log.w(TAG, "[PluginLock Switching] ignore, screen: " + i + ", pluginValue:" + currentPluginValue);
            return;
        }
        updateEnabledState(i, z2);
        PluginLockUtils pluginLockUtils = this.mUtils;
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("[PluginLock Switching]\n screen:", i, "\n enable:", z, "\n pluginValue(final):");
        m.append(currentPluginValue);
        m.append("\n pluginValue(current):");
        m.append(getCurrentPluginValue(i));
        m.append("\n pluginValue(setting):");
        m.append(this.mSettingsHelper.getPluginLockValue(i));
        m.append("\n wasEnabled:");
        m.append(z3);
        m.append("\n isEnabled:");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, this.mIsDynamicEnabled, "\n isForcedDisable:", z2, "\n isOwnerProcess:");
        m.append(isOwnerProcess());
        m.append("\n isCurrentOwner:");
        m.append(this.mUtils.isCurrentOwner());
        pluginLockUtils.addDump(TAG, m.toString());
        if (z && currentPluginValue != 0 && this.mUtils.isGoingToRescueParty()) {
            this.mUtils.addDump(TAG, "[PluginLock Switching] getting disabled by the rescue party");
            this.mSettingsHelper.setPluginLockValue(i, 0);
        }
        boolean z4 = true;
        if (z) {
            this.mMediator.setLockscreenEnabled(true);
            this.mMediator.registerUpdateMonitor();
            Set<String> keySet = this.mLockPluginMap.keySet();
            Iterator<String> it = keySet.iterator();
            while (it.hasNext()) {
                PluginLockInstanceState pluginLockInstanceState4 = this.mLockPluginMap.get(it.next());
                if (pluginLockInstanceState4 == null) {
                    break;
                }
                if (!this.mPolicy.isSameInstance(currentPluginValue, pluginLockInstanceState4.getAllowedNumber()) && !this.mPolicy.isDefaultInstance(pluginLockInstanceState4.getAllowedNumber()) && (pluginLockInstanceState3 = this.mInstanceState) != null && pluginLockInstanceState3.isEnabled(i)) {
                    this.mUtils.addDump(TAG, "[PluginLock Switching] enable, set timestamp 0 for " + pluginLockInstanceState4.getAllowedNumber());
                    if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                        pluginLockInstanceState4.setStateData(i, false);
                    } else {
                        pluginLockInstanceState4.setTimeStamp(false);
                    }
                }
            }
            Iterator<String> it2 = keySet.iterator();
            while (true) {
                if (!it2.hasNext() || (pluginLockInstanceState2 = this.mLockPluginMap.get((next = it2.next()))) == null) {
                    break;
                }
                if (this.mPolicy.isSameInstance(currentPluginValue, pluginLockInstanceState2.getAllowedNumber())) {
                    PluginLockUtils pluginLockUtils2 = this.mUtils;
                    StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "[PluginLock Switching] enable, screen: ", ", mScreenType: ");
                    m2.append(this.mScreenType);
                    m2.append(", key:");
                    m2.append(next);
                    m2.append(", number:");
                    m2.append(currentPluginValue);
                    pluginLockUtils2.addDump(TAG, m2.toString());
                    PluginLock pluginLockInstance = pluginLockInstanceState2.getPluginLockInstance();
                    if (!LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION || i == this.mScreenType) {
                        setPluginInstance(i, pluginLockInstanceState2);
                        if (this.mIsDynamicEnabled || z3) {
                            resetDynamicLock();
                        }
                        PluginLockWallpaper.setDualDisplayPlugin(this.mPolicy.isDualDisplayInstance(pluginLockInstanceState2.getAllowedNumber()));
                    } else {
                        pluginLockInstanceState2.setStateData(i, true);
                        PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
                        if (pluginLockDelegateApp != null && pluginLockInstance != null) {
                            pluginLockDelegateApp.setPanelView(pluginLockInstance.getBasicManager());
                        }
                        if (pluginLockInstance != null && pluginLockInstance.getBasicManager() != null) {
                            pluginLockInstance.getBasicManager().setCallback(this.mDelegateSysUi);
                        }
                    }
                    notifyPluginLockModeChanged(pluginLockInstance, i, true);
                }
            }
        } else {
            if (currentPluginValue != 30000 && (!LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION || (pluginLockInstanceState = this.mInstanceState) == null || !pluginLockInstanceState.isEnabledOtherScreen(i))) {
                this.mMediator.unregisterUpdateMonitor();
            }
            resetConfigs();
            if (z3 && !this.mIsDynamicEnabled) {
                if (!this.mSettingsHelper.isEmergencyMode() && !this.mSettingsHelper.isUltraPowerSavingMode() && !this.mIsSwitchingToSub) {
                    z4 = false;
                }
                this.mMediator.resetDynamicLockData(z4);
            }
            resetDynamicLock();
            PluginLockWallpaper.setDualDisplayPlugin(false);
            PluginLockDelegateApp pluginLockDelegateApp2 = this.mDelegateApp;
            if (pluginLockDelegateApp2 != null) {
                pluginLockDelegateApp2.setBasicManager(null);
            }
            PluginLockDelegateSysUi pluginLockDelegateSysUi = this.mDelegateSysUi;
            if (pluginLockDelegateSysUi != null) {
                pluginLockDelegateSysUi.setPluginLockInstanceState(i, null);
            }
            this.mMediator.setPluginLock(null);
            this.mMediator.setInstanceState(i, null);
            this.mPluginLock = null;
            this.mInstanceState = null;
            for (String str : this.mLockPluginMap.keySet()) {
                PluginLockInstanceState pluginLockInstanceState5 = this.mLockPluginMap.get(str);
                if (pluginLockInstanceState5 == null) {
                    return;
                }
                if (currentPluginValue == 0 || currentPluginValue == 20000 || currentPluginValue == 30000 || this.mPolicy.isSameInstance(currentPluginValue, pluginLockInstanceState5.getAllowedNumber())) {
                    PluginLockUtils pluginLockUtils3 = this.mUtils;
                    StringBuilder m3 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(currentPluginValue, "[PluginLock Switching] disable, pluginValue:", "key:", str, ", number:");
                    m3.append(pluginLockInstanceState5.getAllowedNumber());
                    pluginLockUtils3.addDump(TAG, m3.toString());
                    notifyPluginLockModeChanged(pluginLockInstanceState5.getPluginLockInstance(), i, false);
                    if (currentPluginValue == 0) {
                        if (pluginLockInstanceState5.getPluginLockTimeStamp() > 0) {
                            this.mUtils.addDump(TAG, "[PluginLock Switching] disable all, set timestamp 0 for " + pluginLockInstanceState5.getAllowedNumber());
                        }
                        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                            pluginLockInstanceState5.setStateData(i, false);
                        } else {
                            pluginLockInstanceState5.setTimeStamp(false);
                        }
                    } else if (!this.mPolicy.isSameInstance(currentPluginValue, pluginLockInstanceState5.getAllowedNumber())) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(currentPluginValue, "[PluginLock Switching] disable,  won't update timestamp, ", TAG);
                    } else if (!z2) {
                        this.mUtils.addDump(TAG, "[PluginLock Switching] disable, set timestamp 0 for " + pluginLockInstanceState5.getAllowedNumber());
                        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                            pluginLockInstanceState5.setStateData(i, false);
                        } else {
                            pluginLockInstanceState5.setTimeStamp(false);
                        }
                        setLatestPluginInstance(i, false);
                    }
                }
            }
        }
        this.mMediator.setEnabled(isDynamicLockEnabled());
        setCurrentPluginValue(i, currentPluginValue);
        this.mUtils.addDump(TAG, "[PluginLock Switching] done, " + this.mPluginLock);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        String dumpLegacy = this.mUtils.getDumpLegacy();
        String dump = this.mUtils.getDump();
        Log.i(TAG, "\n\nPluginLockManager event:\n");
        Log.i(TAG, "------ Legacy --------------------------------------------------------------\n");
        Log.i(TAG, dumpLegacy + "\n");
        Log.i(TAG, "------ New -----------------------------------------------------------------\n");
        Log.i(TAG, dump + "\n");
        Log.i(TAG, "----------------------------------------------------------------------------\n");
        printWriter.println("\n\nPluginLockManager event:\n");
        printWriter.println("------ Legacy --------------------------------------------------------------\n");
        printWriter.println(dumpLegacy);
        printWriter.println("------ New -----------------------------------------------------------------\n");
        printWriter.println(dump);
        printWriter.println("----------------------------------------------------------------------------\n");
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public String getDynamicLockData() {
        return this.mMediator.getDynamicLockData();
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public String getLockStarItemLocationInfo(String str) {
        return this.mMediator.getLockStarItemLocationInfo(str);
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public PluginLock getPluginLock() {
        return this.mPluginLock;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public boolean getShortcutTaskState(String str) {
        boolean z;
        if (str.equals("Dnd")) {
            if (this.mTaskDnd == null) {
                this.mTaskDnd = new PluginLockShortcutDnd(this.mContext, this.mMediator);
            }
            z = this.mTaskDnd.isEnabled();
        } else if (str.equals("Flashlight")) {
            if (this.mTaskFlashLight == null) {
                this.mTaskFlashLight = new PluginLockShortcutFlashLight(this.mContext, this.mMediator);
            }
            z = this.mTaskFlashLight.isEnabled();
        } else {
            z = false;
        }
        LogUtil.d(TAG, "getShortcutTaskState [taskName] " + str + ",[isEnable] " + z, new Object[0]);
        return z;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void onCoverStateChanged(CoverState coverState) {
        Log.d(TAG, "onCoverStateChanged " + coverState);
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY && DeviceState.isCoverUiWithWallpaper(coverState.getType()) && this.mCoverState != coverState) {
            this.mCoverState = coverState;
            onFolderStateChanged(coverState.switchState);
        }
    }

    @Override // com.android.systemui.util.DesktopManager.Callback
    public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        Log.d(TAG, "onDesktopModeStateChanged: " + semDesktopModeState);
        if (semDesktopModeState.getDisplayType() != 101) {
            return;
        }
        if (semDesktopModeState.getEnabled() == 4 && semDesktopModeState.getState() == 50) {
            handleStandaloneDexMode(true);
        } else if (semDesktopModeState.getEnabled() == 2 && semDesktopModeState.getState() == 50) {
            handleStandaloneDexMode(false);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void onFolderStateChanged(boolean z) {
        PluginLockInstanceState pluginLockInstanceState;
        this.mScreenType = (!z && (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY)) ? 1 : 0;
        TooltipPopup$$ExternalSyntheticOutline0.m(this.mScreenType, TAG, RowView$$ExternalSyntheticOutline0.m("PluginLock:onFolderStateChanged, opened: ", ", mScreenType: ", z));
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            Iterator<String> it = this.mLockPluginMap.keySet().iterator();
            while (it.hasNext()) {
                pluginLockInstanceState = this.mLockPluginMap.get(it.next());
                if (pluginLockInstanceState != null && pluginLockInstanceState.isEnabled(this.mScreenType) && pluginLockInstanceState != this.mInstanceState && pluginLockInstanceState.isRecentInstance(this.mScreenType)) {
                    Log.i(TAG, "PluginLock:onFolderStateChanged, newState found: null");
                    break;
                }
            }
        }
        pluginLockInstanceState = null;
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && isDynamicLockEnabled()) {
            try {
                if (this.mInstanceState != null) {
                    Log.i(TAG, "PluginLock:onFolderStateChanged, old: " + this.mInstanceState.getPackageName());
                    this.mMediator.onFolderStateChanged(z, false);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        this.mMediator.setScreenTypeChanged(this.mScreenType);
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            Log.i(TAG, "PluginLock:onFolderStateChanged, will be switched, newState: " + pluginLockInstanceState);
            if (pluginLockInstanceState != null) {
                Log.i(TAG, "PluginLock:onFolderStateChanged, changed to new state");
                setPluginInstance(this.mScreenType, pluginLockInstanceState, true);
            } else {
                PluginLockInstanceState pluginLockInstanceState2 = this.mInstanceState;
                if (pluginLockInstanceState2 == null || pluginLockInstanceState2.isRecentInstance(this.mScreenType)) {
                    Log.i(TAG, "PluginLock:onFolderStateChanged, instance maintained");
                } else {
                    Log.i(TAG, "PluginLock:onFolderStateChanged, instance reset");
                    setPluginInstance(this.mScreenType, null);
                }
            }
            updateEnabledState(this.mScreenType, false);
        }
        boolean isDynamicLockEnabled = isDynamicLockEnabled();
        this.mMediator.setEnabled(isDynamicLockEnabled);
        if (isDynamicLockEnabled) {
            try {
                if (this.mInstanceState != null) {
                    Log.i(TAG, "onFolderStateChanged, new: " + this.mInstanceState.getPackageName());
                    this.mMediator.onFolderStateChanged(z, true);
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.SPlugin
    public void onPluginConnected(PluginLock pluginLock, Context context) {
        boolean isOwnerProcess = isOwnerProcess();
        Log.d(TAG, "onPluginConnected : " + context.getPackageName() + ", isOwnerProcess: " + isOwnerProcess);
        if (isOwnerProcess) {
            PluginLockInstanceState pluginLockInstanceState = new PluginLockInstanceState(pluginLock, context, this.mUtils);
            if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                migration(pluginLockInstanceState);
            }
            try {
                setPluginInstanceState(pluginLockInstanceState);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.SPlugin
    public void onPluginDisconnected(PluginLock pluginLock, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onPluginDisconnected ", TAG);
        if (pluginLock != null) {
            removeInstance(i, pluginLock);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.SPlugin
    public void onRootViewAttached(ViewGroup viewGroup) {
        Log.d(TAG, "onRootViewAttached : " + viewGroup);
        this.mDelegateApp.onRootViewAttached(viewGroup);
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.UserSwitch
    public void onUserSwitchComplete(int i) {
        this.mUtils.addDump(TAG, "onUserSwitchComplete, from: " + this.mUserId + ", to: " + i);
        Handler handler = new Handler(Looper.getMainLooper());
        if (i == 0) {
            handler.postDelayed(new PluginLockManagerImpl$$ExternalSyntheticLambda1(this, 0), 3000L);
        } else {
            handler.postDelayed(new PluginLockManagerImpl$$ExternalSyntheticLambda0(this, i, 1), 1000L);
        }
        this.mUserId = i;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.UserSwitch
    public void onUserSwitching(int i) {
        this.mUtils.addDump(TAG, "onUserSwitching, userId: " + i);
        if (i != 0) {
            this.mIsSwitchingToSub = true;
            disableByUser();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void registerSystemUIViewCallback(PluginLockListener.State state) {
        this.mMediator.registerStateCallback(state);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void removeInstance(int r14, com.samsung.systemui.splugins.pluginlock.PluginLock r15) {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockManagerImpl.removeInstance(int, com.samsung.systemui.splugins.pluginlock.PluginLock):void");
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void removeShortcutTaskListener() {
        LogUtil.d(TAG, "removeShortcutTaskListener", new Object[0]);
        PluginLockShortcutDnd pluginLockShortcutDnd = this.mTaskDnd;
        if (pluginLockShortcutDnd != null) {
            pluginLockShortcutDnd.removeListener();
            this.mTaskDnd = null;
        }
        PluginLockShortcutFlashLight pluginLockShortcutFlashLight = this.mTaskFlashLight;
        if (pluginLockShortcutFlashLight != null) {
            pluginLockShortcutFlashLight.removeListener();
            this.mTaskFlashLight = null;
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void removeSystemUIViewCallback(PluginLockListener.State state) {
        this.mMediator.removeStateCallback(state);
    }

    public void resetDynamicLock() {
        Log.d(TAG, "resetDynamicLock()");
        this.mMediator.resetDynamicLock();
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPluginInstanceState(com.android.systemui.pluginlock.PluginLockInstanceState r14) {
        /*
            Method dump skipped, instructions count: 463
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockManagerImpl.setPluginInstanceState(com.android.systemui.pluginlock.PluginLockInstanceState):void");
    }

    public void updateLockStarData(Bundle bundle) {
        Log.d(TAG, "updateLockStarData: " + bundle);
        this.mMediator.onEventReceived(bundle);
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void updateShortcutTaskState(String str) {
        PluginLockShortcutFlashLight pluginLockShortcutFlashLight;
        if (str.equals("Dnd")) {
            PluginLockShortcutDnd pluginLockShortcutDnd = this.mTaskDnd;
            if (pluginLockShortcutDnd != null) {
                pluginLockShortcutDnd.excute();
                return;
            }
            return;
        }
        if (!str.equals("Flashlight") || (pluginLockShortcutFlashLight = this.mTaskFlashLight) == null) {
            return;
        }
        pluginLockShortcutFlashLight.excute();
    }

    private void setPluginInstance(int i, PluginLockInstanceState pluginLockInstanceState, boolean z) {
        PluginLock pluginLock;
        PluginLockInstanceState pluginLockInstanceState2;
        this.mUtils.addDump(TAG, "setPluginInstance() screen:" + i + ", state: " + pluginLockInstanceState);
        if (pluginLockInstanceState == null) {
            this.mPluginLock = null;
            PluginLockInstanceState pluginLockInstanceState3 = this.mInstanceState;
            if (pluginLockInstanceState3 != null) {
                boolean z2 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
                if (z2) {
                    pluginLockInstanceState3.setStateData(i, false);
                } else {
                    pluginLockInstanceState3.setTimeStamp(false);
                }
                if (!z2 || !this.mInstanceState.isEnabledOtherScreen(i)) {
                    this.mInstanceState.destroy();
                }
            }
            this.mMediator.updateWindowSecureState(false);
            if (this.mDelegateApp != null && LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && ((pluginLockInstanceState2 = this.mInstanceState) == null || !pluginLockInstanceState2.isEnabledOtherScreen(i))) {
                this.mDelegateApp.setBasicManager(null);
            }
            this.mInstanceState = null;
        } else {
            this.mInstanceState = pluginLockInstanceState;
            this.mPluginLock = pluginLockInstanceState.getPluginLockInstance();
            Log.d(TAG, "setPluginInstance() mInstanceState: " + this.mInstanceState);
            if (!z) {
                if (this.mPolicy.isDefaultInstance(this.mInstanceState.getAllowedNumber()) && !this.mInstanceState.hasEnabledPlugin(0) && !this.mInstanceState.hasEnabledPlugin(1)) {
                    for (int i2 : this.mScreenList) {
                        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                            this.mInstanceState.setStateData(i2, true);
                        } else {
                            this.mInstanceState.setTimeStamp(true);
                        }
                    }
                } else if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                    this.mInstanceState.setStateData(i, true);
                } else {
                    this.mInstanceState.setTimeStamp(true);
                }
                this.mUtils.addDump(TAG, "setPluginInstance() set timestamp true for " + pluginLockInstanceState.getPackageName());
            }
            PluginLockDelegateSysUi pluginLockDelegateSysUi = this.mDelegateSysUi;
            if (pluginLockDelegateSysUi != null) {
                pluginLockDelegateSysUi.setPluginLockInstanceState(i, this.mInstanceState);
            }
            PluginLock pluginLock2 = this.mPluginLock;
            if (pluginLock2 != null && pluginLock2.getBasicManager() != null) {
                this.mPluginLock.getBasicManager().setCallback(this.mDelegateSysUi);
            }
            this.mMediator.setPluginLock(this.mPluginLock);
            PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
            if (pluginLockDelegateApp != null && (pluginLock = this.mPluginLock) != null) {
                pluginLockDelegateApp.setBasicManager(pluginLock.getBasicManager());
            }
            this.mMediator.onBarStateChanged();
        }
        this.mMediator.setInstanceState(i, pluginLockInstanceState);
    }

    private void setLatestPluginInstance(boolean z) {
        for (int i : this.mScreenList) {
            setLatestPluginInstance(i, z);
        }
    }
}
