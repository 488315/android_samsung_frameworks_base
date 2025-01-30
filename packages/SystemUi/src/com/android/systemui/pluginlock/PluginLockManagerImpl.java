package com.android.systemui.pluginlock;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockShortcutDnd;
import com.android.systemui.pluginlock.component.PluginLockShortcutFlashLight;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener$SPlugin;
import com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.utils.DumpUtils;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockManagerImpl implements PluginLockManager, KeyguardListener$SPlugin, KeyguardListener$UserSwitch, DesktopManager.Callback, Dumpable {
    public final Context mContext;
    public CoverState mCoverState;
    public final ContentResolver mCr;
    public final HashMap mCurrentPluginValueMap;
    public final PluginLockDelegateApp mDelegateApp;
    public final PluginLockDelegateSysUi mDelegateSysUi;
    public PluginLockInstanceState mInstanceState;
    public boolean mIsDynamicEnabled;
    public boolean mIsMigrating;
    public boolean mIsSwitchingToSub;
    public final HashMap mLockPluginMap;
    public final PluginLockMediator mMediator;
    public PluginLock mPluginLock;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final PluginLockInstancePolicy mPolicy;
    public String mRemovedPackageName;
    public final int[] mScreenList;
    public int mScreenType;
    public final PluginLockManagerImpl$$ExternalSyntheticLambda1 mSettingsCallback;
    public final SettingsHelper mSettingsHelper;
    public PluginLockShortcutDnd mTaskDnd;
    public PluginLockShortcutFlashLight mTaskFlashLight;
    public int mUserId;
    public final PluginLockUtils mUtils;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.systemui.pluginlock.PluginLockManagerImpl$$ExternalSyntheticLambda1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public PluginLockManagerImpl(PluginLockMediator pluginLockMediator, PluginLockInstancePolicy pluginLockInstancePolicy, PluginLockDelegateApp pluginLockDelegateApp, PluginLockDelegateSysUi pluginLockDelegateSysUi, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, PluginWallpaperManager pluginWallpaperManager, KeyguardFoldController keyguardFoldController, DesktopManager desktopManager, Context context) {
        Uri[] uriArr = {Settings.System.getUriFor("lockstar_enabled"), Settings.System.getUriFor("plugin_lock_sub_enabled"), Settings.System.getUriFor("emergency_mode"), Settings.System.getUriFor("ultra_powersaving_mode"), Settings.System.getUriFor("minimal_battery_use"), Settings.Secure.getUriFor("lock_screen_show_notifications"), Settings.System.getUriFor("lockscreen_minimizing_notification"), Settings.System.getUriFor("lockscreen_show_shortcut")};
        this.mLockPluginMap = new HashMap();
        this.mRemovedPackageName = null;
        this.mIsMigrating = false;
        this.mCurrentPluginValueMap = new HashMap();
        this.mScreenType = 0;
        this.mIsSwitchingToSub = false;
        this.mUserId = 0;
        ?? r8 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.pluginlock.PluginLockManagerImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PluginLockManagerImpl pluginLockManagerImpl = PluginLockManagerImpl.this;
                pluginLockManagerImpl.getClass();
                Log.d("PluginLockManagerImpl", "SettingHelper changed uri: " + uri);
                if (uri == null) {
                    Log.d("PluginLockManagerImpl", "uri null");
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lockstar_enabled"))) {
                    pluginLockManagerImpl.handleEnableStateChanged(0);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("plugin_lock_sub_enabled"))) {
                    pluginLockManagerImpl.handleEnableStateChanged(1);
                    return;
                }
                boolean equals = uri.equals(Settings.System.getUriFor("emergency_mode"));
                SettingsHelper settingsHelper2 = pluginLockManagerImpl.mSettingsHelper;
                if (equals) {
                    if (settingsHelper2.isEmergencyMode()) {
                        Log.d("PluginLockManagerImpl", "handleEmergencyModeChanged, enabled");
                        pluginLockManagerImpl.disableByMode();
                        return;
                    }
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("minimal_battery_use"))) {
                    if (!settingsHelper2.isUltraPowerSavingMode()) {
                        pluginLockManagerImpl.setLatestPluginInstance(false);
                        return;
                    } else {
                        Log.d("PluginLockManagerImpl", "handleMinimalBatteryModeChanged, enabled");
                        pluginLockManagerImpl.disableByMode();
                        return;
                    }
                }
                if (uri.equals(Settings.System.getUriFor("lock_editor_support_touch_hold")) && settingsHelper2.isSupportTouchAndHoldToEdit()) {
                    Log.d("PluginLockManagerImpl", "handleLongTouchModeChanged");
                    ((PluginLockMediatorImpl) pluginLockManagerImpl.mMediator).updateWindowSecureState(false);
                }
            }
        };
        this.mSettingsCallback = r8;
        this.mMediator = pluginLockMediator;
        this.mPolicy = pluginLockInstancePolicy;
        this.mDelegateSysUi = pluginLockDelegateSysUi;
        this.mDelegateApp = pluginLockDelegateApp;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mUtils = pluginLockUtils;
        pluginLockUtils.addDump("PluginLockManagerImpl", "## PluginLockManager ##, " + this);
        PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
        pluginLockMediatorImpl.mSPluginListener = this;
        pluginLockMediatorImpl.setKeyguardUserSwitchListener(this);
        pluginLockMediatorImpl.mBasicListener = pluginLockDelegateApp;
        if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER) {
            ((DesktopManagerImpl) desktopManager).registerCallback(this);
        }
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            this.mScreenList = new int[]{0, 1};
        } else {
            this.mScreenList = new int[]{0};
        }
        boolean z = UserHandle.semGetMyUserId() == 0;
        if (settingsHelper != 0 && z) {
            settingsHelper.registerCallback(r8, uriArr);
        }
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE) {
            try {
                if (WallpaperManager.getInstance(context).getLidState() == 0) {
                    Log.d("PluginLockManagerImpl", "PluginLockManager: mScreenType = PluginLock.SCREEN_SUB");
                    this.mScreenType = 1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            ScoverManager scoverManager = new ScoverManager(context);
            if (scoverManager.getCoverState() != null && !scoverManager.getCoverState().switchState) {
                Log.d("PluginLockManagerImpl", "PluginLockManager, virtual display: mScreenType = PluginLock.SCREEN_SUB");
                this.mScreenType = 1;
            }
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.pluginlock.PluginLockManagerImpl$$ExternalSyntheticLambda2
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z2) {
                    PluginLockManagerImpl.this.onFolderStateChanged(z2);
                }
            }, 1000);
        }
    }

    public static void notifyPluginLockModeChanged(PluginLock pluginLock, int i, boolean z) {
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
            Log.w("PluginLockManagerImpl", "notifyPluginLockMode, " + e.toString());
            pluginLock.getBasicManager().onPluginLockModeChanged(z);
        }
    }

    public final void disableByMode() {
        for (int i : this.mScreenList) {
            if (isEnabledFromSettingHelper(i)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("disableByMode, screen: ", i, "PluginLockManagerImpl");
                this.mSettingsHelper.setPluginLockValue(i, 20000);
                updatePluginLockMode(i, false, false);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PluginLockUtils pluginLockUtils = this.mUtils;
        ContentResolver contentResolver = pluginLockUtils.mDumpUtils.mContext.getContentResolver();
        String string = Settings.Secure.getString(contentResolver, "plugin_lock_event_dump");
        if (string != null) {
            Settings.Secure.putString(contentResolver, "plugin_lock_event_dump", null);
        }
        pluginLockUtils.mDumpUtils.getClass();
        String dump = DumpUtils.getDump();
        Log.i("PluginLockManagerImpl", "\n\nPluginLockManager event:\n");
        Log.i("PluginLockManagerImpl", "------ Legacy --------------------------------------------------------------\n");
        Log.i("PluginLockManagerImpl", string + "\n");
        Log.i("PluginLockManagerImpl", "------ New -----------------------------------------------------------------\n");
        Log.i("PluginLockManagerImpl", dump + "\n");
        Log.i("PluginLockManagerImpl", "----------------------------------------------------------------------------\n");
        printWriter.println("\n\nPluginLockManager event:\n");
        printWriter.println("------ Legacy --------------------------------------------------------------\n");
        printWriter.println(string);
        printWriter.println("------ New -----------------------------------------------------------------\n");
        printWriter.println(dump);
        printWriter.println("----------------------------------------------------------------------------\n");
    }

    public final int getCurrentPluginValue(int i) {
        Integer num = (Integer) this.mCurrentPluginValueMap.get(Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public final String getLockStarItemLocationInfo(String str) {
        return ((PluginLockMediatorImpl) this.mMediator).getLockStarItemLocationInfo(str);
    }

    public final void handleEnableStateChanged(int i) {
        boolean z;
        PluginLockInstanceState pluginLockInstanceState;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        int pluginLockValue = settingsHelper.getPluginLockValue(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("handleEnableStateChanged screen:", i, ", value:", pluginLockValue, "PluginLockManagerImpl");
        if (pluginLockValue == 30000 || pluginLockValue == 20000) {
            Log.d("PluginLockManagerImpl", "handleEnableStateChanged: user switched or mode changed, ignore!");
            return;
        }
        if (!LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) {
            Log.d("PluginLockManagerImpl", "handleEnableStateChanged: not supported, skip!");
            return;
        }
        if (this.mIsMigrating && i == 1) {
            Log.d("PluginLockManagerImpl", "handleEnableStateChanged: migrating, skip!");
            this.mIsMigrating = false;
            return;
        }
        int pluginLockValue2 = settingsHelper.getPluginLockValue(i);
        int currentPluginValue = getCurrentPluginValue(i);
        this.mPolicy.getClass();
        if (PluginLockInstancePolicy.isEnable(pluginLockValue2) && PluginLockInstancePolicy.isEnable(currentPluginValue) && !PluginLockInstancePolicy.isSameInstance(currentPluginValue, pluginLockValue2) && (pluginLockInstanceState = this.mInstanceState) != null && pluginLockInstanceState.mData.isEnabled(i)) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("disableForcedIfNeed() disabled ", currentPluginValue, "PluginLockManagerImpl");
            updatePluginLockMode(i, false, true);
            z = true;
        } else {
            z = false;
        }
        if (z) {
            new Handler(Looper.getMainLooper()).post(new PluginLockManagerImpl$$ExternalSyntheticLambda3(this, i, 1));
        } else {
            updatePluginLockMode(i, isEnabledFromSettingHelper(i), false);
        }
    }

    public final void handleStandaloneDexMode(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleStandaloneDexMode, dexEnabled:", z, "PluginLockManagerImpl");
        if (z) {
            new Handler(Looper.getMainLooper()).post(new PluginLockManagerImpl$$ExternalSyntheticLambda0(this, 1));
        } else {
            setLatestPluginInstance(false);
        }
    }

    public final boolean isDynamicLockEnabled() {
        if (this.mIsDynamicEnabled && this.mInstanceState != null && this.mPluginLock != null) {
            this.mUtils.getClass();
            if (!PluginLockUtils.isGoingToRescueParty()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isEnabledFromSettingHelper(int i) {
        int pluginLockValue = this.mSettingsHelper.getPluginLockValue(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("isEnabledFromSettingHelper, screen:", i, ", value:", pluginLockValue, "PluginLockManagerImpl");
        this.mPolicy.getClass();
        return PluginLockInstancePolicy.isEnable(pluginLockValue);
    }

    @Override // com.android.systemui.util.DesktopManager.Callback
    public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        Log.d("PluginLockManagerImpl", "onDesktopModeStateChanged: " + semDesktopModeState);
        if (semDesktopModeState.getDisplayType() != 101) {
            return;
        }
        if (semDesktopModeState.getEnabled() == 4 && semDesktopModeState.getState() == 50) {
            handleStandaloneDexMode(true);
        } else if (semDesktopModeState.getEnabled() == 2 && semDesktopModeState.getState() == 50) {
            handleStandaloneDexMode(false);
        }
    }

    public final void onFolderStateChanged(boolean z) {
        PluginLockInstanceState pluginLockInstanceState;
        this.mScreenType = (!z && (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY)) ? 1 : 0;
        TooltipPopup$$ExternalSyntheticOutline0.m13m(RowView$$ExternalSyntheticOutline0.m49m("PluginLock:onFolderStateChanged, opened: ", z, ", mScreenType: "), this.mScreenType, "PluginLockManagerImpl");
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            HashMap hashMap = this.mLockPluginMap;
            Iterator it = hashMap.keySet().iterator();
            while (it.hasNext()) {
                pluginLockInstanceState = (PluginLockInstanceState) hashMap.get((String) it.next());
                if (pluginLockInstanceState != null) {
                    if (pluginLockInstanceState.mData.isEnabled(this.mScreenType) && pluginLockInstanceState != this.mInstanceState && pluginLockInstanceState.isRecentInstance(this.mScreenType)) {
                        Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, newState found: null");
                        break;
                    }
                }
            }
        }
        pluginLockInstanceState = null;
        boolean z2 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (z2 && isDynamicLockEnabled()) {
            try {
                if (this.mInstanceState != null) {
                    Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, old: " + this.mInstanceState.mPackageName);
                    ((PluginLockMediatorImpl) pluginLockMediator).onFolderStateChanged(z, false);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        int i = this.mScreenType;
        PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
        pluginLockMediatorImpl.getClass();
        Log.i("PluginLockMediatorImpl", "setScreenTypeChanged() type: " + i);
        boolean z3 = LsRune.LOCKUI_SUB_DISPLAY_LOCK;
        PluginLockMediatorImpl.sScreenType = (z3 || LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) ? i : 0;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("setScreenTypeChanged() type: ", i, "PluginLockWallpaper");
        PluginLockWallpaper.sScreenType = z3 ? i : 0;
        PluginLockWallpaper.sScreenTypeChanged = z3;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("setScreenTypeChanged() type: ", i, "PluginHomeWallpaper");
        PluginHomeWallpaper.sScreenType = i;
        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
            Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, will be switched, newState: " + pluginLockInstanceState);
            if (pluginLockInstanceState != null) {
                Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, changed to new state");
                setPluginInstance(this.mScreenType, pluginLockInstanceState, true);
            } else {
                PluginLockInstanceState pluginLockInstanceState2 = this.mInstanceState;
                if (pluginLockInstanceState2 == null || pluginLockInstanceState2.isRecentInstance(this.mScreenType)) {
                    Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, instance maintained");
                } else {
                    Log.i("PluginLockManagerImpl", "PluginLock:onFolderStateChanged, instance reset");
                    setPluginInstance(this.mScreenType, null, false);
                }
            }
            updateEnabledState(this.mScreenType, false);
        }
        boolean isDynamicLockEnabled = isDynamicLockEnabled();
        pluginLockMediatorImpl.setEnabled(isDynamicLockEnabled);
        if (isDynamicLockEnabled) {
            try {
                if (this.mInstanceState != null) {
                    Log.i("PluginLockManagerImpl", "onFolderStateChanged, new: " + this.mInstanceState.mPackageName);
                    ((PluginLockMediatorImpl) pluginLockMediator).onFolderStateChanged(z, true);
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch
    public final void onUserSwitchComplete(int i) {
        this.mUtils.addDump("PluginLockManagerImpl", "onUserSwitchComplete, from: " + this.mUserId + ", to: " + i);
        Handler handler = new Handler(Looper.getMainLooper());
        if (i == 0) {
            handler.postDelayed(new PluginLockManagerImpl$$ExternalSyntheticLambda0(this, 2), 3000L);
        } else {
            handler.postDelayed(new PluginLockManagerImpl$$ExternalSyntheticLambda3(this, i, 0), 1000L);
        }
        this.mUserId = i;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch
    public final void onUserSwitching(int i) {
        this.mUtils.addDump("PluginLockManagerImpl", AbstractC0000x2c234b15.m0m("onUserSwitching, userId: ", i));
        if (i != 0) {
            this.mIsSwitchingToSub = true;
            for (int i2 : this.mScreenList) {
                if (isEnabledFromSettingHelper(i2)) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("disableByUser, screen: ", i2, "PluginLockManagerImpl");
                    this.mSettingsHelper.setPluginLockValue(i2, 30000);
                    updatePluginLockMode(i2, false, false);
                }
            }
        }
    }

    public final void registerSystemUIViewCallback(PluginLockListener$State pluginLockListener$State) {
        ((PluginLockMediatorImpl) this.mMediator).registerStateCallback(pluginLockListener$State);
    }

    public final void removeSystemUIViewCallback(PluginLockListener$State pluginLockListener$State) {
        PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) this.mMediator;
        synchronized (pluginLockMediatorImpl.mStateListenerList) {
            for (int i = 0; i < ((ArrayList) pluginLockMediatorImpl.mStateListenerList).size(); i++) {
                if (((WeakReference) ((ArrayList) pluginLockMediatorImpl.mStateListenerList).get(i)).get() == pluginLockListener$State) {
                    ((ArrayList) pluginLockMediatorImpl.mStateListenerList).remove(i);
                    return;
                }
            }
        }
    }

    public final void setLatestPluginInstance(int i, boolean z) {
        PluginLockUtils pluginLockUtils = this.mUtils;
        pluginLockUtils.getClass();
        boolean isCurrentOwner = PluginLockUtils.isCurrentOwner();
        StringBuilder sb = new StringBuilder("setLatestPluginInstance map size: ");
        HashMap hashMap = this.mLockPluginMap;
        sb.append(hashMap.size());
        sb.append(", isCurrentOwner:");
        sb.append(isCurrentOwner);
        Log.d("PluginLockManagerImpl", sb.toString());
        if (isCurrentOwner) {
            long j = 0;
            Map.Entry entry = null;
            for (Map.Entry entry2 : hashMap.entrySet()) {
                PluginLockInstanceData.Data data = ((PluginLockInstanceState) entry2.getValue()).mData;
                if (data != null) {
                    Long timeStamps = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION ? data.getTimeStamps(i) : data.getTimeStamp();
                    if (timeStamps != null && j < timeStamps.longValue()) {
                        j = timeStamps.longValue();
                        entry = entry2;
                    }
                }
            }
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (entry != null) {
                int i2 = ((PluginLockInstanceState) entry.getValue()).mAllowedNumber;
                int i3 = settingsHelper.getPluginLockValue(i) % 10 == 1 ? 2 : 1;
                StringBuilder sb2 = new StringBuilder("setLatestPluginInstance() set value:");
                int i4 = i2 + i3;
                sb2.append(i4);
                Log.d("PluginLockManagerImpl", sb2.toString());
                settingsHelper.setPluginLockValue(i, i4);
                return;
            }
            if (!z) {
                PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
                if (pluginLockDelegateApp != null) {
                    pluginLockDelegateApp.setBasicManager(null);
                    return;
                }
                return;
            }
            pluginLockUtils.addDump("PluginLockManagerImpl", "setLatestPluginInstance, screen:" + i + ", DISABLED_ALL");
            setPluginInstance(i, null, false);
            settingsHelper.setPluginLockValue(i, 0);
        }
    }

    public final void setPluginInstance(int i, PluginLockInstanceState pluginLockInstanceState, boolean z) {
        PluginLock pluginLock;
        PluginLockInstanceState pluginLockInstanceState2;
        PluginLockUtils pluginLockUtils = this.mUtils;
        pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstance() screen:" + i + ", state: " + pluginLockInstanceState);
        PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
        PluginLockMediator pluginLockMediator = this.mMediator;
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
            ((PluginLockMediatorImpl) pluginLockMediator).updateWindowSecureState(false);
            if (pluginLockDelegateApp != null && LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && ((pluginLockInstanceState2 = this.mInstanceState) == null || !pluginLockInstanceState2.isEnabledOtherScreen(i))) {
                pluginLockDelegateApp.setBasicManager(null);
            }
            this.mInstanceState = null;
        } else {
            this.mInstanceState = pluginLockInstanceState;
            this.mPluginLock = pluginLockInstanceState.mInstance;
            Log.d("PluginLockManagerImpl", "setPluginInstance() mInstanceState: " + this.mInstanceState);
            if (!z) {
                if (this.mPolicy.isDefaultInstance(this.mInstanceState.mAllowedNumber) && !this.mInstanceState.hasEnabledPlugin(0) && !this.mInstanceState.hasEnabledPlugin(1)) {
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
                pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstance() set timestamp true for " + pluginLockInstanceState.mPackageName);
            }
            PluginLockDelegateSysUi pluginLockDelegateSysUi = this.mDelegateSysUi;
            if (pluginLockDelegateSysUi != null) {
                pluginLockDelegateSysUi.setPluginLockInstanceState(i, this.mInstanceState);
            }
            PluginLock pluginLock2 = this.mPluginLock;
            if (pluginLock2 != null && pluginLock2.getBasicManager() != null) {
                this.mPluginLock.getBasicManager().setCallback(pluginLockDelegateSysUi);
            }
            ((PluginLockMediatorImpl) pluginLockMediator).setPluginLock(this.mPluginLock);
            if (pluginLockDelegateApp != null && (pluginLock = this.mPluginLock) != null) {
                pluginLockDelegateApp.setBasicManager(pluginLock.getBasicManager());
            }
            final PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
            if (pluginLockMediatorImpl.mBasicListener != null) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    pluginLockMediatorImpl.mBasicListener.onBarStateChanged(pluginLockMediatorImpl.mBarState);
                } else {
                    pluginLockMediatorImpl.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            PluginLockMediatorImpl pluginLockMediatorImpl2 = PluginLockMediatorImpl.this;
                            pluginLockMediatorImpl2.mBasicListener.onBarStateChanged(pluginLockMediatorImpl2.mBarState);
                        }
                    });
                }
            }
        }
        ((PluginLockMediatorImpl) pluginLockMediator).setInstanceState(i, pluginLockInstanceState);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0253  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setPluginInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        PluginLockInstancePolicy pluginLockInstancePolicy;
        SettingsHelper settingsHelper;
        PluginLockUtils pluginLockUtils;
        int i;
        int pluginLockValue;
        int i2;
        int i3;
        Map map;
        int[] iArr = this.mScreenList;
        int length = iArr.length;
        int i4 = 0;
        Integer num = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (true) {
            pluginLockInstancePolicy = this.mPolicy;
            settingsHelper = this.mSettingsHelper;
            pluginLockUtils = this.mUtils;
            if (i4 >= length) {
                break;
            }
            int i5 = iArr[i4];
            int pluginLockValue2 = settingsHelper.getPluginLockValue(i5);
            if (!z) {
                z = pluginLockValue2 == 20000 ? true : z2;
            }
            if (!z3) {
                z3 = pluginLockValue2 == 30000 ? true : z2;
            }
            Log.d("PluginLockManagerImpl", "setPluginInstanceState() settingValue:" + pluginLockValue2 + ", " + pluginLockInstanceState);
            if (!pluginLockInstanceState.hasEnabledPlugin(i5) && pluginLockValue2 % 10 > 0) {
                pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstanceState(): abnormal case detected: " + pluginLockValue2);
                int i6 = pluginLockInstanceState.mAllowedNumber;
                pluginLockInstancePolicy.getClass();
                if (PluginLockInstancePolicy.isSameInstance(pluginLockValue2, i6)) {
                    pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstanceState(): " + i6 + " will have a timestamp");
                    if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                        pluginLockInstanceState.setStateData(i5, true);
                    } else {
                        pluginLockInstanceState.setTimeStamp(true);
                    }
                }
            }
            i4++;
            z2 = false;
        }
        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("setPluginInstanceState() getPackageName "), pluginLockInstanceState.mPackageName, "PluginLockManagerImpl");
        String str = pluginLockInstanceState.mPackageName;
        HashMap hashMap = this.mLockPluginMap;
        if (hashMap.containsKey(str)) {
            hashMap.remove(str);
        }
        hashMap.put(str, pluginLockInstanceState);
        pluginLockInstancePolicy.getClass();
        boolean equals = PluginLockInstancePolicy.DEFAULT_PACKAGES[0].equals(pluginLockInstanceState.mPackageName);
        Map map2 = pluginLockInstancePolicy.mCategoryMap;
        if (equals) {
            HashMap hashMap2 = (HashMap) map2;
            Integer num2 = (Integer) hashMap2.get(Integer.valueOf(pluginLockInstanceState.mAllowedNumber));
            if (num2 == null) {
                num2 = num;
            }
            hashMap2.put(Integer.valueOf(pluginLockInstanceState.mAllowedNumber), Integer.valueOf(num2.intValue() | 1));
        }
        String[] strArr = (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? PluginLockInstancePolicy.DUAL_DISPLAY_PACKAGES : PluginLockInstancePolicy.DUAL_DISPLAY_PACKAGES_FOLDER;
        int length2 = strArr.length;
        int i7 = 0;
        while (i7 < length2) {
            Integer num3 = num;
            String[] strArr2 = strArr;
            if (strArr[i7].equals(pluginLockInstanceState.mPackageName)) {
                HashMap hashMap3 = (HashMap) map2;
                Integer num4 = (Integer) hashMap3.get(Integer.valueOf(pluginLockInstanceState.mAllowedNumber));
                map = map2;
                if (num4 == null) {
                    num4 = num3;
                }
                hashMap3.put(Integer.valueOf(pluginLockInstanceState.mAllowedNumber), Integer.valueOf(num4.intValue() | 2));
            } else {
                map = map2;
            }
            i7++;
            num = num3;
            strArr = strArr2;
            map2 = map;
        }
        Log.d("PluginLockManagerImpl", "putPluginInstanceToMap, size " + hashMap.size() + ", packageName " + str);
        boolean isDefaultInstance = pluginLockInstancePolicy.isDefaultInstance(pluginLockInstanceState.mAllowedNumber);
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (isDefaultInstance) {
            ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper.mWallpaperUpdateCallback.onReady();
            ContentResolver contentResolver = this.mCr;
            i = 0;
            if (Settings.System.getInt(contentResolver, "tss_activated", 0) == 1) {
                pluginLockUtils.addDump("PluginLockManagerImpl", "TSS Activated");
                Settings.System.putInt(contentResolver, "tss_activated", 0);
                new Handler(Looper.getMainLooper()).postDelayed(new PluginLockManagerImpl$$ExternalSyntheticLambda0(this, i), 500L);
            }
        } else {
            i = 0;
        }
        pluginLockUtils.addDump("PluginLockManagerImpl", "connected: " + pluginLockInstanceState.mPackageName);
        if (z && (settingsHelper.isEmergencyMode() || settingsHelper.isPowerSavingMode())) {
            pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstanceState() skip, disabled by mode");
            return;
        }
        if (z3 && !PluginLockUtils.isCurrentOwner()) {
            pluginLockUtils.addDump("PluginLockManagerImpl", "setPluginInstanceState() skip, disabled by user");
            ((PluginLockMediatorImpl) pluginLockMediator).registerUpdateMonitor();
            return;
        }
        if (this.mRemovedPackageName != null) {
            String str2 = pluginLockInstanceState.mPackageName;
            if (((str2.startsWith("com.samsung.android.dynamiclock") || str2.startsWith("com.samsung.android.mateagent")) ? 1 : i) != 0 && this.mRemovedPackageName.equals(pluginLockInstanceState.mPackageName)) {
                pluginLockUtils.addDump("PluginLockManagerImpl", "Re install after deleting package " + this.mRemovedPackageName);
                ((PluginLockMediatorImpl) pluginLockMediator).onDataCleared();
                this.mRemovedPackageName = null;
            }
        }
        int length3 = iArr.length;
        while (i < length3) {
            int i8 = iArr[i];
            boolean z4 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
            if (z4) {
                if (!pluginLockInstanceState.isRecentInstance(i8)) {
                    i++;
                }
                if (z4 || this.mScreenType == i8) {
                    this.mInstanceState = pluginLockInstanceState;
                    this.mPluginLock = pluginLockInstanceState.mInstance;
                }
                pluginLockValue = settingsHelper.getPluginLockValue(i8);
                i2 = pluginLockInstanceState.mAllowedNumber;
                if (PluginLockInstancePolicy.isSameInstance(pluginLockValue, i2)) {
                    i3 = i2 + 1;
                } else {
                    i3 = i2 + (pluginLockValue % 10 == 1 ? 2 : 1);
                }
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setPluginInstanceState screen:", i8, ", now:", pluginLockValue, ", new:");
                m45m.append(i3);
                pluginLockUtils.addDump("PluginLockManagerImpl", m45m.toString());
                settingsHelper.setPluginLockValue(i8, i3);
                i++;
            } else {
                if (!pluginLockInstanceState.isRecentInstance()) {
                    i++;
                }
                if (z4) {
                }
                this.mInstanceState = pluginLockInstanceState;
                this.mPluginLock = pluginLockInstanceState.mInstance;
                pluginLockValue = settingsHelper.getPluginLockValue(i8);
                i2 = pluginLockInstanceState.mAllowedNumber;
                if (PluginLockInstancePolicy.isSameInstance(pluginLockValue, i2)) {
                }
                StringBuilder m45m2 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setPluginInstanceState screen:", i8, ", now:", pluginLockValue, ", new:");
                m45m2.append(i3);
                pluginLockUtils.addDump("PluginLockManagerImpl", m45m2.toString());
                settingsHelper.setPluginLockValue(i8, i3);
                i++;
            }
        }
    }

    public final void updateEnabledState(int i, boolean z) {
        int currentPluginValue = z ? getCurrentPluginValue(i) : this.mSettingsHelper.getPluginLockValue(i);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("updateEnabledState getPluginLockValue = ", currentPluginValue, "PluginLockManagerImpl");
        boolean z2 = currentPluginValue >= 10000;
        boolean z3 = !z && isEnabledFromSettingHelper(i);
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("updateEnabledState() isDynamicMode = ", z2, ", isEnabledFromSetting = ", z3, "PluginLockManagerImpl");
        this.mIsDynamicEnabled = z2 && z3;
    }

    public final void updatePluginLockMode(int i, boolean z, boolean z2) {
        PluginLockMediator pluginLockMediator;
        boolean z3;
        PluginLockInstanceState pluginLockInstanceState;
        PluginLockMediator pluginLockMediator2;
        PluginLockInstanceState pluginLockInstanceState2;
        PluginLockUtils pluginLockUtils = this.mUtils;
        pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] start");
        SettingsHelper settingsHelper = this.mSettingsHelper;
        int currentPluginValue = z2 ? getCurrentPluginValue(i) : settingsHelper.getPluginLockValue(i);
        boolean z4 = this.mIsDynamicEnabled;
        if (!PluginLockUtils.isCurrentOwner() && currentPluginValue != 30000) {
            Log.w("PluginLockManagerImpl", "[PluginLock Switching] ignore, screen: " + i + ", pluginValue:" + currentPluginValue);
            return;
        }
        updateEnabledState(i, z2);
        StringBuilder sb = new StringBuilder("[PluginLock Switching]\n screen:");
        sb.append(i);
        sb.append("\n enable:");
        sb.append(z);
        sb.append("\n pluginValue(final):");
        sb.append(currentPluginValue);
        sb.append("\n pluginValue(current):");
        sb.append(getCurrentPluginValue(i));
        sb.append("\n pluginValue(setting):");
        sb.append(settingsHelper.getPluginLockValue(i));
        sb.append("\n wasEnabled:");
        sb.append(z4);
        sb.append("\n isEnabled:");
        sb.append(this.mIsDynamicEnabled);
        sb.append("\n isForcedDisable:");
        sb.append(z2);
        sb.append("\n isOwnerProcess:");
        sb.append(UserHandle.semGetMyUserId() == 0);
        sb.append("\n isCurrentOwner:");
        sb.append(PluginLockUtils.isCurrentOwner());
        pluginLockUtils.addDump("PluginLockManagerImpl", sb.toString());
        if (z && currentPluginValue != 0 && PluginLockUtils.isGoingToRescueParty()) {
            pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] getting disabled by the rescue party");
            settingsHelper.setPluginLockValue(i, 0);
        }
        HashMap hashMap = this.mLockPluginMap;
        String str = "PluginLockWallpaper";
        PluginLockMediator pluginLockMediator3 = this.mMediator;
        PluginLockDelegateSysUi pluginLockDelegateSysUi = this.mDelegateSysUi;
        PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
        PluginLockInstancePolicy pluginLockInstancePolicy = this.mPolicy;
        if (z) {
            PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator3;
            pluginLockMediatorImpl.mIsLockScreenEnabled = true;
            pluginLockMediatorImpl.registerUpdateMonitor();
            Set keySet = hashMap.keySet();
            Iterator it = keySet.iterator();
            while (it.hasNext()) {
                Iterator it2 = it;
                PluginLockInstanceState pluginLockInstanceState3 = (PluginLockInstanceState) hashMap.get((String) it.next());
                if (pluginLockInstanceState3 == null) {
                    break;
                }
                String str2 = str;
                int i2 = pluginLockInstanceState3.mAllowedNumber;
                pluginLockInstancePolicy.getClass();
                if (PluginLockInstancePolicy.isSameInstance(currentPluginValue, i2) || pluginLockInstancePolicy.isDefaultInstance(pluginLockInstanceState3.mAllowedNumber) || (pluginLockInstanceState2 = this.mInstanceState) == null || !pluginLockInstanceState2.mData.isEnabled(i)) {
                    pluginLockMediator2 = pluginLockMediator3;
                } else {
                    pluginLockMediator2 = pluginLockMediator3;
                    pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] enable, set timestamp 0 for " + pluginLockInstanceState3.mAllowedNumber);
                    if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                        pluginLockInstanceState3.setStateData(i, false);
                    } else {
                        pluginLockInstanceState3.setTimeStamp(false);
                    }
                }
                it = it2;
                str = str2;
                pluginLockMediator3 = pluginLockMediator2;
            }
            String str3 = str;
            pluginLockMediator = pluginLockMediator3;
            Iterator it3 = keySet.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                String str4 = (String) it3.next();
                PluginLockInstanceState pluginLockInstanceState4 = (PluginLockInstanceState) hashMap.get(str4);
                if (pluginLockInstanceState4 == null) {
                    break;
                }
                int i3 = pluginLockInstanceState4.mAllowedNumber;
                pluginLockInstancePolicy.getClass();
                if (PluginLockInstancePolicy.isSameInstance(currentPluginValue, i3)) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("[PluginLock Switching] enable, screen: ", i, ", mScreenType: ");
                    m1m.append(this.mScreenType);
                    m1m.append(", key:");
                    m1m.append(str4);
                    m1m.append(", number:");
                    m1m.append(currentPluginValue);
                    pluginLockUtils.addDump("PluginLockManagerImpl", m1m.toString());
                    PluginLock pluginLock = pluginLockInstanceState4.mInstance;
                    boolean z5 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
                    if (!z5 || i == this.mScreenType) {
                        setPluginInstance(i, pluginLockInstanceState4, false);
                        if (this.mIsDynamicEnabled || z4) {
                            Log.d("PluginLockManagerImpl", "resetDynamicLock()");
                            ((PluginLockMediatorImpl) pluginLockMediator).resetDynamicLock();
                        }
                        int i4 = pluginLockInstanceState4.mAllowedNumber;
                        Integer num = (Integer) ((HashMap) pluginLockInstancePolicy.mCategoryMap).get(Integer.valueOf((i4 / 10) * 10));
                        boolean z6 = num != null && (num.intValue() & 2) == 2;
                        AbstractC0731x5bb8a836.m72m("isDualDisplayInstance() allowedNumber:", i4, ", ret:", z6, "PluginLockInstancePolicy");
                        boolean z7 = z6 || z5;
                        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("setDualDisplayPlugin() : ", z7, str3);
                        PluginLockWallpaper.sDualDisplayPlugin = z7;
                    } else {
                        pluginLockInstanceState4.setStateData(i, true);
                        if (pluginLockDelegateApp != null && pluginLock != null) {
                            pluginLockDelegateApp.setPanelView(pluginLock.getBasicManager());
                        }
                        if (pluginLock != null && pluginLock.getBasicManager() != null) {
                            pluginLock.getBasicManager().setCallback(pluginLockDelegateSysUi);
                        }
                    }
                    notifyPluginLockModeChanged(pluginLock, i, true);
                }
            }
        } else {
            pluginLockMediator = pluginLockMediator3;
            if (currentPluginValue != 30000 && (!LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION || (pluginLockInstanceState = this.mInstanceState) == null || !pluginLockInstanceState.isEnabledOtherScreen(i))) {
                PluginLockMediatorImpl pluginLockMediatorImpl2 = (PluginLockMediatorImpl) pluginLockMediator;
                pluginLockMediatorImpl2.mKeyguardUpdateMonitor.removeCallback(pluginLockMediatorImpl2.mMonitorCallback);
            }
            PluginLockMediatorImpl pluginLockMediatorImpl3 = (PluginLockMediatorImpl) pluginLockMediator;
            pluginLockMediatorImpl3.resetConfigs();
            if (z4 && !this.mIsDynamicEnabled) {
                pluginLockMediatorImpl3.resetDynamicLockData(settingsHelper.isEmergencyMode() || settingsHelper.isUltraPowerSavingMode() || this.mIsSwitchingToSub);
            }
            Log.d("PluginLockManagerImpl", "resetDynamicLock()");
            ((PluginLockMediatorImpl) pluginLockMediator).resetDynamicLock();
            Log.i("PluginLockWallpaper", "setDualDisplayPlugin() : false");
            PluginLockWallpaper.sDualDisplayPlugin = false;
            if (pluginLockDelegateApp != null) {
                pluginLockDelegateApp.setBasicManager(null);
            }
            if (pluginLockDelegateSysUi != null) {
                pluginLockDelegateSysUi.setPluginLockInstanceState(i, null);
            }
            pluginLockMediatorImpl3.setPluginLock(null);
            pluginLockMediatorImpl3.setInstanceState(i, null);
            this.mPluginLock = null;
            this.mInstanceState = null;
            for (String str5 : hashMap.keySet()) {
                PluginLockInstanceState pluginLockInstanceState5 = (PluginLockInstanceState) hashMap.get(str5);
                if (pluginLockInstanceState5 == null) {
                    return;
                }
                if (currentPluginValue != 0 && currentPluginValue != 20000 && currentPluginValue != 30000) {
                    int i5 = pluginLockInstanceState5.mAllowedNumber;
                    pluginLockInstancePolicy.getClass();
                    if (PluginLockInstancePolicy.isSameInstance(currentPluginValue, i5)) {
                    }
                }
                StringBuilder m61m = AbstractC0662xaf167275.m61m("[PluginLock Switching] disable, pluginValue:", currentPluginValue, "key:", str5, ", number:");
                m61m.append(pluginLockInstanceState5.mAllowedNumber);
                pluginLockUtils.addDump("PluginLockManagerImpl", m61m.toString());
                notifyPluginLockModeChanged(pluginLockInstanceState5.mInstance, i, false);
                if (currentPluginValue == 0) {
                    if (pluginLockInstanceState5.mTimeStamp > 0) {
                        pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] disable all, set timestamp 0 for " + pluginLockInstanceState5.mAllowedNumber);
                    }
                    if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                        pluginLockInstanceState5.setStateData(i, false);
                    } else {
                        pluginLockInstanceState5.setTimeStamp(false);
                    }
                } else {
                    int i6 = pluginLockInstanceState5.mAllowedNumber;
                    pluginLockInstancePolicy.getClass();
                    if (!PluginLockInstancePolicy.isSameInstance(currentPluginValue, i6)) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("[PluginLock Switching] disable,  won't update timestamp, ", currentPluginValue, "PluginLockManagerImpl");
                    } else if (!z2) {
                        pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] disable, set timestamp 0 for " + pluginLockInstanceState5.mAllowedNumber);
                        if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                            z3 = false;
                            pluginLockInstanceState5.setStateData(i, false);
                        } else {
                            z3 = false;
                            pluginLockInstanceState5.setTimeStamp(false);
                        }
                        setLatestPluginInstance(i, z3);
                    }
                }
            }
        }
        ((PluginLockMediatorImpl) pluginLockMediator).setEnabled(isDynamicLockEnabled());
        this.mCurrentPluginValueMap.put(Integer.valueOf(i), Integer.valueOf(currentPluginValue));
        pluginLockUtils.addDump("PluginLockManagerImpl", "[PluginLock Switching] done, " + this.mPluginLock);
    }

    public final void setLatestPluginInstance(boolean z) {
        for (int i : this.mScreenList) {
            setLatestPluginInstance(i, z);
        }
    }
}
