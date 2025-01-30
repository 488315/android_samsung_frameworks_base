package com.android.systemui.pluginlock;

import android.app.SemWallpaperColors;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginLockManagerWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockFaceWidget;
import com.android.systemui.pluginlock.component.PluginLockHelpText;
import com.android.systemui.pluginlock.component.PluginLockLockIcon;
import com.android.systemui.pluginlock.component.PluginLockMusic;
import com.android.systemui.pluginlock.component.PluginLockNotification;
import com.android.systemui.pluginlock.component.PluginLockSecure;
import com.android.systemui.pluginlock.component.PluginLockShortcut;
import com.android.systemui.pluginlock.component.PluginLockStatusBar;
import com.android.systemui.pluginlock.component.PluginLockSwipe;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener$SPlugin;
import com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.pluginlock.model.IndicationData;
import com.android.systemui.shade.C2456x44c40078;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.google.gson.Gson;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockMediatorImpl implements PluginLockMediator, SPluginListener {
    public static int sScreenType;
    public int mBarState;
    public PluginLockDelegateApp mBasicListener;
    public PluginLockFaceWidget mClock;
    public final ExternalClockProvider mClockProvider;
    public final Context mContext;
    public final DozeParameters mDozeParameters;
    public String mDynamicLockData;
    public final Handler mHandler;
    public PluginLockHelpText mHelpText;
    public final PluginHomeWallpaper mHomeWallpaper;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public PluginLockLockIcon mLockIcon;
    public final LockPatternUtils mLockPatternUtils;
    public final PluginLockWallpaper mLockWallpaper;
    public PluginLockMusic mMusic;
    public PluginLockNotification mNotification;
    public Context mPluginContext;
    public KeyguardListener$SPlugin mSPluginListener;
    public final SPluginManager mSPluginManager;
    public PluginLockSecure mSecure;
    public final SettingsHelper mSettingsHelper;
    public final KeyguardShortcutManager mShortcurManager;
    public PluginLockShortcut mShortcut;
    public PluginLockStatusBar mStatusBar;
    public KeyguardStatusBarViewController.C30636 mStatusBarCallback;
    public PluginLockSwipe mSwipe;
    public final PluginLockUtils mUtils;
    public int mViewMode;
    public C2456x44c40078 mWindowListener;
    public final List mUserSwitchListenerList = new ArrayList();
    public final List mStateListenerList = new ArrayList();
    public DynamicLockData mCurrentDynamicLockData = null;
    public boolean mIsEnabled = false;
    public boolean mIsDynamicLockData = true;
    public boolean mIsRotateMenuHide = false;
    public boolean mIsLockScreenEnabled = true;
    public boolean mIsSecureWindow = false;
    public boolean mIsCoverAttached = false;
    public final KeyguardUpdateMonitorCallback mMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            PluginLockMediatorImpl pluginLockMediatorImpl = PluginLockMediatorImpl.this;
            if (pluginLockMediatorImpl.mViewMode == 0 || z) {
                return;
            }
            pluginLockMediatorImpl.onViewModeChanged(0);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLocaleChanged() {
            PluginLockBasicManager pluginLockBasicManager;
            PluginLockDelegateApp pluginLockDelegateApp = PluginLockMediatorImpl.this.mBasicListener;
            if (pluginLockDelegateApp == null || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
                return;
            }
            pluginLockBasicManager.onLocaleChanged();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            LogUtil.m225i("PluginLockMediatorImpl", AbstractC0000x2c234b15.m0m("onStartedGoingToSleep why :", i), new Object[0]);
            PluginLockMediatorImpl pluginLockMediatorImpl = PluginLockMediatorImpl.this;
            if (pluginLockMediatorImpl.mIsSecureWindow) {
                pluginLockMediatorImpl.updateWindowSecureState(false);
            }
            if (i == 4) {
                Bundle bundle = new Bundle();
                bundle.putString("action", PluginLock.ACTION_LID_SWITCH);
                pluginLockMediatorImpl.onEventReceived(bundle);
            }
            boolean z = !pluginLockMediatorImpl.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
            if (pluginLockMediatorImpl.mIsLockScreenEnabled != z) {
                pluginLockMediatorImpl.mIsLockScreenEnabled = z;
                Bundle bundle2 = new Bundle();
                bundle2.putString("action", PluginLock.ACTION_LOCK_STYLE_CHANGED);
                bundle2.putBoolean("value", pluginLockMediatorImpl.mIsLockScreenEnabled);
                pluginLockMediatorImpl.onEventReceived(bundle2);
            }
            PluginLockDelegateApp pluginLockDelegateApp = pluginLockMediatorImpl.mBasicListener;
            if (pluginLockDelegateApp != null) {
                boolean z2 = pluginLockMediatorImpl.mDozeParameters.mControlScreenOffAnimation;
                StringBuilder sb = new StringBuilder("onStartedGoingToSleep enabled: true aodClockTransition : ");
                pluginLockDelegateApp.mUtils.getClass();
                sb.append(z2);
                Log.d("PluginLockDelegateApp", sb.toString());
                PluginLockBasicManager pluginLockBasicManager = pluginLockDelegateApp.mBasicManager;
                if (pluginLockBasicManager != null) {
                    pluginLockBasicManager.onStartedGoingToSleep(z2);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedWakingUp() {
            PluginLockBasicManager pluginLockBasicManager;
            PluginLockDelegateApp pluginLockDelegateApp = PluginLockMediatorImpl.this.mBasicListener;
            if (pluginLockDelegateApp == null || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
                return;
            }
            pluginLockBasicManager.onStartedWakingUp();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUpdateCoverState(CoverState coverState) {
            boolean z = coverState.attached;
            PluginLockMediatorImpl pluginLockMediatorImpl = PluginLockMediatorImpl.this;
            pluginLockMediatorImpl.mIsCoverAttached = z;
            boolean z2 = !coverState.switchState;
            Log.d("PluginLockMediatorImpl", "onUpdateCoverState, mViewMode: " + pluginLockMediatorImpl.mViewMode + ", state: " + coverState);
            if (z2 && pluginLockMediatorImpl.mViewMode == 1) {
                Bundle bundle = new Bundle();
                bundle.putString("action", PluginLock.ACTION_COVER_CLOSED);
                pluginLockMediatorImpl.onEventReceived(bundle);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            KeyguardListener$UserSwitch keyguardListener$UserSwitch;
            synchronized (PluginLockMediatorImpl.this.mUserSwitchListenerList) {
                for (int i2 = 0; i2 < ((ArrayList) PluginLockMediatorImpl.this.mUserSwitchListenerList).size(); i2++) {
                    WeakReference weakReference = (WeakReference) ((ArrayList) PluginLockMediatorImpl.this.mUserSwitchListenerList).get(i2);
                    if (weakReference != null && (keyguardListener$UserSwitch = (KeyguardListener$UserSwitch) weakReference.get()) != null) {
                        keyguardListener$UserSwitch.onUserSwitchComplete(i);
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            KeyguardListener$UserSwitch keyguardListener$UserSwitch;
            synchronized (PluginLockMediatorImpl.this.mUserSwitchListenerList) {
                for (int i2 = 0; i2 < ((ArrayList) PluginLockMediatorImpl.this.mUserSwitchListenerList).size(); i2++) {
                    WeakReference weakReference = (WeakReference) ((ArrayList) PluginLockMediatorImpl.this.mUserSwitchListenerList).get(i2);
                    if (weakReference != null && (keyguardListener$UserSwitch = (KeyguardListener$UserSwitch) weakReference.get()) != null) {
                        keyguardListener$UserSwitch.onUserSwitching(i);
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            PluginLockMediatorImpl.this.mUtils.checkSafeMode();
        }
    };

    public PluginLockMediatorImpl(Context context, SPluginManager sPluginManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, ExternalClockProvider externalClockProvider, KeyguardShortcutManager keyguardShortcutManager, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils) {
        this.mSPluginManager = sPluginManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mClockProvider = externalClockProvider;
        this.mShortcurManager = keyguardShortcutManager;
        this.mSettingsHelper = settingsHelper;
        this.mContext = context;
        this.mUtils = pluginLockUtils;
        pluginLockUtils.addDump("PluginLockMediatorImpl", "## PluginLockMediatorImpl ##, " + this);
        this.mDozeParameters = dozeParameters;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mHomeWallpaper = new PluginHomeWallpaper(context);
        this.mLockWallpaper = new PluginLockWallpaper(context, null, settingsHelper);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static String getItemLocation(int i) {
        int i2 = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getRealSize().y;
        int i3 = i2 / 3;
        if (i3 > i) {
            return "top";
        }
        if (i3 * 2 > i) {
            return "background";
        }
        if (i2 >= i) {
            return "bottom";
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0054, code lost:
    
        if (r8.equals("face_widget") == false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getLockStarItemLocationInfo(String str) {
        int intValue;
        int intValue2;
        if (this.mCurrentDynamicLockData == null || str == null || str.isEmpty()) {
            Log.d("PluginLockMediatorImpl", "getLockStarItemLocationInfo Data: " + this.mCurrentDynamicLockData + ", group: " + str);
            return "none";
        }
        char c = 0;
        boolean z = this.mContext.getResources().getConfiguration().orientation == 2;
        int hashCode = str.hashCode();
        if (hashCode != -1481109914) {
            if (hashCode != 339679582) {
                if (hashCode == 1824376660 && str.equals("indication_text_view")) {
                    c = 2;
                }
                c = 65535;
            } else {
                if (str.equals("notification_icon_only")) {
                    c = 1;
                }
                c = 65535;
            }
        }
        if (c != 0) {
            if (c != 1) {
                if (c != 2) {
                    return "none";
                }
                if (this.mCurrentDynamicLockData.getIndicationData() == null) {
                    return "bottom";
                }
                int i = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getRealSize().y;
                IndicationData.HelpTextData helpTextData = this.mCurrentDynamicLockData.getIndicationData().getHelpTextData();
                if (z) {
                    intValue = i - helpTextData.getHeight().intValue();
                    intValue2 = helpTextData.getPaddingBottomLand().intValue();
                } else {
                    intValue = i - helpTextData.getHeight().intValue();
                    intValue2 = helpTextData.getPaddingBottom().intValue();
                }
                return getItemLocation(intValue - intValue2);
            }
            if (this.mCurrentDynamicLockData.getNotificationData() != null && this.mCurrentDynamicLockData.getNotificationData().getNotiType().intValue() == 2) {
                return z ? getItemLocation(this.mCurrentDynamicLockData.getNotificationData().getIconOnlyData().getTopYLand().intValue()) : getItemLocation(this.mCurrentDynamicLockData.getNotificationData().getIconOnlyData().getTopY().intValue());
            }
        } else if (this.mCurrentDynamicLockData.getServiceBoxData() != null) {
            return z ? getItemLocation(this.mCurrentDynamicLockData.getServiceBoxData().getTopYLand().intValue()) : getItemLocation(this.mCurrentDynamicLockData.getServiceBoxData().getTopY().intValue());
        }
        return "top";
    }

    public final boolean isDynamicLockEnabled() {
        if (this.mIsEnabled) {
            this.mUtils.getClass();
            if (!PluginLockUtils.isGoingToRescueParty()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRotateMenuHide() {
        StringBuilder sb = new StringBuilder("isRotateMenuHide mIsEnabled: ");
        sb.append(this.mIsEnabled);
        sb.append(", mIsRotateMenuHide: ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mIsRotateMenuHide, "PluginLockMediatorImpl");
        return this.mIsEnabled && this.mIsRotateMenuHide;
    }

    public final void onDataCleared() {
        this.mLockWallpaper.mWallpaperUpdateCallback.onDataCleared();
        PluginHomeWallpaper pluginHomeWallpaper = this.mHomeWallpaper;
        pluginHomeWallpaper.mWallpaperUpdateCallback.onDataCleared();
        Map map = pluginHomeWallpaper.mWallpaperDataList;
        try {
            PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) map).get(0);
            wallpaperData.mType = -2;
            wallpaperData.mPath = null;
            wallpaperData.mUri = null;
            wallpaperData.mHints = null;
            wallpaperData.mRect = null;
            if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                PluginHomeWallpaper.WallpaperData wallpaperData2 = (PluginHomeWallpaper.WallpaperData) ((HashMap) map).get(1);
                wallpaperData2.mType = -2;
                wallpaperData2.mPath = null;
                wallpaperData2.mUri = null;
                wallpaperData2.mHints = null;
                wallpaperData2.mRect = null;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public final void onEventReceived(Bundle bundle) {
        PluginLockBasicManager pluginLockBasicManager;
        PluginLockDelegateApp pluginLockDelegateApp = this.mBasicListener;
        if (pluginLockDelegateApp == null || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
            return;
        }
        pluginLockBasicManager.onEventReceived(bundle);
    }

    public final void onFolderStateChanged(boolean z, boolean z2) {
        PluginLockBasicManager pluginLockBasicManager;
        PluginLockDelegateApp pluginLockDelegateApp = this.mBasicListener;
        if (pluginLockDelegateApp == null || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
            return;
        }
        try {
            pluginLockBasicManager.onFolderStateChanged(z, z2);
        } catch (AbstractMethodError e) {
            Log.w("PluginLockDelegateApp", "onFolderStateChanged, " + e.getMessage());
            pluginLockDelegateApp.mBasicManager.onFolderStateChanged(z);
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginConnected(SPlugin sPlugin, Context context) {
        PluginLock pluginLock = (PluginLock) sPlugin;
        Log.d("PluginLockMediatorImpl", "onPluginConnected");
        KeyguardListener$SPlugin keyguardListener$SPlugin = this.mSPluginListener;
        if (keyguardListener$SPlugin != null) {
            PluginLockManagerImpl pluginLockManagerImpl = (PluginLockManagerImpl) keyguardListener$SPlugin;
            boolean z = UserHandle.semGetMyUserId() == 0;
            Log.d("PluginLockManagerImpl", "onPluginConnected : " + context.getPackageName() + ", isOwnerProcess: " + z);
            if (z) {
                PluginLockInstanceState pluginLockInstanceState = new PluginLockInstanceState(pluginLock, context, pluginLockManagerImpl.mUtils);
                if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                    int intValue = ((PluginLockInstanceData) pluginLockInstanceState.mGson.fromJson(PluginLockInstanceData.class, pluginLockInstanceState.getDbData())).getVersion().intValue();
                    Log.d("PluginLockInstanceState", "getDataVersion() " + intValue);
                    Log.d("PluginLockManagerImpl", "[migration] for [" + pluginLockInstanceState.mPackageName + "]");
                    StringBuilder sb = new StringBuilder("[migration] - savedVersion: ");
                    sb.append(intValue);
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, ", currVersion:3", "PluginLockManagerImpl");
                    if (intValue < 3) {
                        SettingsHelper settingsHelper = pluginLockManagerImpl.mSettingsHelper;
                        int pluginLockValue = settingsHelper.getPluginLockValue(0);
                        int pluginLockValue2 = settingsHelper.getPluginLockValue(1);
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("[migration] - mainValue: ", pluginLockValue, ", subValue:", pluginLockValue2, "PluginLockManagerImpl");
                        if (pluginLockValue != -1 && pluginLockValue2 == -1) {
                            int i = pluginLockInstanceState.mAllowedNumber;
                            pluginLockManagerImpl.mPolicy.getClass();
                            if (!PluginLockInstancePolicy.isSameInstance(i, pluginLockValue)) {
                                Log.d("PluginLockManagerImpl", "[migration] - not activated plugin");
                            } else if (PluginLockInstancePolicy.isEnable(pluginLockValue)) {
                                Log.d("PluginLockManagerImpl", "[migration] - start!");
                                pluginLockInstanceState.setStateData(1, PluginLockInstancePolicy.isEnable(pluginLockValue));
                                pluginLockManagerImpl.mIsMigrating = true;
                                settingsHelper.setPluginLockValue(1, pluginLockValue);
                            }
                        }
                    }
                }
                try {
                    pluginLockManagerImpl.setPluginInstanceState(pluginLockInstanceState);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008b  */
    @Override // com.samsung.systemui.splugins.SPluginListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPluginDisconnected(SPlugin sPlugin, int i) {
        int[] iArr;
        PluginLockMediator pluginLockMediator;
        int i2;
        PluginLock pluginLock = (PluginLock) sPlugin;
        Log.d("PluginLockMediatorImpl", "onPluginDisconnected");
        KeyguardListener$SPlugin keyguardListener$SPlugin = this.mSPluginListener;
        if (keyguardListener$SPlugin != null) {
            PluginLockManagerImpl pluginLockManagerImpl = (PluginLockManagerImpl) keyguardListener$SPlugin;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onPluginDisconnected ", i, "PluginLockManagerImpl");
            if (pluginLock != null) {
                Log.d("PluginLockManagerImpl", "removeInstance() reason " + i);
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = pluginLockManagerImpl.mLockPluginMap;
                Iterator it = hashMap.entrySet().iterator();
                int i3 = 0;
                boolean z = false;
                while (true) {
                    boolean hasNext = it.hasNext();
                    iArr = pluginLockManagerImpl.mScreenList;
                    pluginLockMediator = pluginLockManagerImpl.mMediator;
                    if (!hasNext) {
                        break;
                    }
                    PluginLockInstanceState pluginLockInstanceState = (PluginLockInstanceState) ((Map.Entry) it.next()).getValue();
                    if (pluginLockInstanceState.mInstance == pluginLock) {
                        StringBuilder m1m = AbstractC0000x2c234b15.m1m("disconnected, reason: ", i, ", package:");
                        m1m.append(pluginLockInstanceState.mPackageName);
                        String sb = m1m.toString();
                        PluginLockUtils pluginLockUtils = pluginLockManagerImpl.mUtils;
                        pluginLockUtils.addDump("PluginLockManagerImpl", sb);
                        int length = iArr.length;
                        boolean z2 = z;
                        boolean z3 = i3;
                        while (i3 < length) {
                            int i4 = iArr[i3];
                            boolean z4 = LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION;
                            if (z4) {
                                if (!pluginLockInstanceState.isRecentInstance(i4)) {
                                    i3++;
                                    z3 = 0;
                                }
                                if (i == 0) {
                                    if (z4) {
                                        pluginLockInstanceState.setStateData(i4, z3);
                                    } else {
                                        pluginLockInstanceState.setTimeStamp(z3);
                                    }
                                    String str = pluginLockInstanceState.mPackageName;
                                    if (str.startsWith("com.samsung.android.dynamiclock") || str.startsWith("com.samsung.android.mateagent")) {
                                        pluginLockUtils.addDump("PluginLockManagerImpl", "plugin Package removed" + pluginLockInstanceState.mPackageName);
                                        pluginLockManagerImpl.mRemovedPackageName = pluginLockInstanceState.mPackageName;
                                    }
                                    z3 = 0;
                                    z2 = true;
                                }
                                PluginLockManagerImpl.notifyPluginLockModeChanged(pluginLock, i4, z3);
                                PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
                                pluginLockMediatorImpl.resetConfigs();
                                pluginLockMediatorImpl.resetDynamicLockData(z3);
                                pluginLockMediatorImpl.resetDynamicLock();
                                i3++;
                                z3 = 0;
                            } else {
                                if (!pluginLockInstanceState.isRecentInstance()) {
                                    i3++;
                                    z3 = 0;
                                }
                                if (i == 0) {
                                }
                                PluginLockManagerImpl.notifyPluginLockModeChanged(pluginLock, i4, z3);
                                PluginLockMediatorImpl pluginLockMediatorImpl2 = (PluginLockMediatorImpl) pluginLockMediator;
                                pluginLockMediatorImpl2.resetConfigs();
                                pluginLockMediatorImpl2.resetDynamicLockData(z3);
                                pluginLockMediatorImpl2.resetDynamicLock();
                                i3++;
                                z3 = 0;
                            }
                        }
                        arrayList.add(pluginLockInstanceState.mPackageName);
                        z = z2;
                    }
                    i3 = 0;
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    PluginLockInstanceState pluginLockInstanceState2 = (PluginLockInstanceState) hashMap.get(str2);
                    Log.d("PluginLockManagerImpl", "removeInstance() pkgName:" + str2 + ", state: " + pluginLockInstanceState2 + ", map size: " + hashMap.size());
                    if (pluginLockInstanceState2 != null) {
                        int length2 = iArr.length;
                        while (i2 < length2) {
                            int i5 = iArr[i2];
                            if (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION) {
                                i2 = pluginLockInstanceState2.isRecentInstance(i5) ? 0 : i2 + 1;
                                pluginLockManagerImpl.mPluginLock = null;
                                pluginLockManagerImpl.mInstanceState = null;
                                ((PluginLockMediatorImpl) pluginLockMediator).updateWindowSecureState(false);
                                pluginLockManagerImpl.mDelegateApp.setBasicManager(null);
                                pluginLockManagerImpl.mDelegateSysUi.setPluginLockInstanceState(i5, null);
                            } else {
                                if (!pluginLockInstanceState2.isRecentInstance()) {
                                }
                                pluginLockManagerImpl.mPluginLock = null;
                                pluginLockManagerImpl.mInstanceState = null;
                                ((PluginLockMediatorImpl) pluginLockMediator).updateWindowSecureState(false);
                                pluginLockManagerImpl.mDelegateApp.setBasicManager(null);
                                pluginLockManagerImpl.mDelegateSysUi.setPluginLockInstanceState(i5, null);
                            }
                        }
                        pluginLockInstanceState2.destroy();
                    }
                    hashMap.remove(str2);
                }
                arrayList.clear();
                if (z) {
                    pluginLockManagerImpl.setLatestPluginInstance(true);
                }
            }
        }
    }

    public final void onViewModeChanged(int i) {
        PluginLockListener$State pluginLockListener$State;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("onViewModeChanged mode: ", i, ", mStateListenerList.size(): ");
        ArrayList arrayList = (ArrayList) this.mStateListenerList;
        m1m.append(arrayList.size());
        m1m.append(", mViewMode:");
        m1m.append(this.mViewMode);
        int i2 = 0;
        LogUtil.m223d("PluginLockMediatorImpl", m1m.toString(), new Object[0]);
        if (this.mViewMode == i) {
            return;
        }
        this.mViewMode = i;
        if (1 != i && this.mIsSecureWindow) {
            updateWindowSecureState(false);
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            WeakReference weakReference = (WeakReference) arrayList.get(i3);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onViewModeChanged(i);
            }
        }
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onViewModeChanged(i);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(this, i, i2));
            }
        }
    }

    public final void publishLockStarState() {
        String lockStarItemLocationInfo;
        int i = 0;
        LogUtil.m223d("PluginLockMediatorImpl", "publishLockStarState mIsDynamicLockData: " + this.mIsDynamicLockData, new Object[0]);
        int i2 = 0;
        while (true) {
            List list = this.mStateListenerList;
            boolean z = true;
            if (i2 >= ((ArrayList) list).size()) {
                break;
            }
            if (((WeakReference) ((ArrayList) list).get(i2)).get() != null) {
                Log.d("PluginLockMediatorImpl", "publishLockStarState : " + ((WeakReference) ((ArrayList) list).get(i2)).get());
                try {
                    PluginLockListener$State pluginLockListener$State = (PluginLockListener$State) ((WeakReference) ((ArrayList) list).get(i2)).get();
                    if (this.mIsDynamicLockData) {
                        z = false;
                    }
                    pluginLockListener$State.onLockStarEnabled(z);
                } catch (Exception e) {
                    EmergencyButton$$ExternalSyntheticOutline0.m58m("publishLockStarState Exception: ", e, "PluginLockMediatorImpl");
                }
            }
            i2++;
        }
        Context context = this.mContext;
        if (context == null || context.getContentResolver() == null) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (!this.mIsDynamicLockData && (lockStarItemLocationInfo = getLockStarItemLocationInfo("face_widget")) != null) {
            lockStarItemLocationInfo.hashCode();
            switch (lockStarItemLocationInfo) {
                case "bottom":
                    i = 3;
                    break;
                case "background":
                    i = 2;
                    break;
                case "top":
                    i = 1;
                    break;
            }
        }
        Settings.Secure.putInt(contentResolver, "lockstar_facewidget_area", i);
    }

    public final void registerStateCallback(PluginLockListener$State pluginLockListener$State) {
        LogUtil.m223d("PluginLockMediatorImpl", "registerStateCallback: " + pluginLockListener$State, new Object[0]);
        synchronized (this.mStateListenerList) {
            for (int i = 0; i < ((ArrayList) this.mStateListenerList).size(); i++) {
                if (((WeakReference) ((ArrayList) this.mStateListenerList).get(i)).get() == pluginLockListener$State) {
                    return;
                }
            }
            ((ArrayList) this.mStateListenerList).add(new WeakReference(pluginLockListener$State));
            if (pluginLockListener$State instanceof FaceWidgetPluginLockManagerWrapper.FaceWidgetLockStarStateCallbackWrapper) {
                StringBuilder sb = new StringBuilder();
                sb.append("registerStateCallback isLockStar: ");
                sb.append(!this.mIsDynamicLockData);
                LogUtil.m225i("PluginLockMediatorImpl", sb.toString(), new Object[0]);
                pluginLockListener$State.onLockStarEnabled(this.mIsDynamicLockData ? false : true);
                DynamicLockData dynamicLockData = this.mCurrentDynamicLockData;
                if (dynamicLockData != null) {
                    PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
                    if (pluginLockFaceWidget != null) {
                        pluginLockFaceWidget.loadClockData(null, dynamicLockData);
                    }
                    PluginLockMusic pluginLockMusic = this.mMusic;
                    if (pluginLockMusic != null) {
                        pluginLockMusic.loadMusicData();
                    }
                }
            }
        }
    }

    public final void registerUpdateMonitor() {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mMonitorCallback;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        keyguardUpdateMonitor.removeCallback(keyguardUpdateMonitorCallback);
        keyguardUpdateMonitor.registerCallback(this.mMonitorCallback);
        ScoverManager scoverManager = new ScoverManager(this.mContext);
        this.mIsCoverAttached = scoverManager.getCoverState() != null ? scoverManager.getCoverState().attached : false;
    }

    public final void resetConfigs() {
        PluginLockListener$State pluginLockListener$State;
        Log.e("PluginLockMediatorImpl", "resetConfig mIsDynamicLockData: " + this.mIsDynamicLockData);
        Point point = DeviceState.sDisplaySize;
        if (DeviceType.isTablet() || !WallpaperUtils.isVideoWallpaper()) {
            setScreenOrientation(false, false);
        } else {
            this.mIsRotateMenuHide = false;
        }
        C2456x44c40078 c2456x44c40078 = this.mWindowListener;
        if (c2456x44c40078 != null) {
            c2456x44c40078.updateOverlayUserTimeout(false);
        }
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.mStateListenerList;
            if (i >= arrayList.size()) {
                this.mIsDynamicLockData = true;
                return;
            }
            WeakReference weakReference = (WeakReference) arrayList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onViewModeChanged(0);
                if (!this.mIsDynamicLockData) {
                    pluginLockListener$State.onLockStarEnabled(false);
                }
            }
            i++;
        }
    }

    public final void resetDynamicLock() {
        PluginLockListener$State pluginLockListener$State;
        Log.e("PluginLockMediatorImpl", "resetDynamicLock");
        int i = 0;
        while (true) {
            List list = this.mStateListenerList;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            WeakReference weakReference = (WeakReference) ((ArrayList) list).get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.resetDynamicLock();
            }
            i++;
        }
    }

    public final void resetDynamicLockData(boolean z) {
        PluginLockListener$State pluginLockListener$State;
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceData.Data.RecoverData recoverData2;
        PluginLockInstanceData.Data.RecoverData recoverData3;
        this.mCurrentDynamicLockData = null;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("resetDynamicLockData() reconnectReq: ", z, "PluginLockMediatorImpl");
        PluginLockNotification pluginLockNotification = this.mNotification;
        if (pluginLockNotification != null) {
            Log.d("PluginLockNotification", "reset()");
            Log.d("PluginLockNotification", "unregisterCallback()");
            pluginLockNotification.mVisibility = -1;
            pluginLockNotification.mCallbackValue = -1;
            pluginLockNotification.mCallbackRegisterTime = 0L;
            pluginLockNotification.mSettingsHelper.unregisterCallback(pluginLockNotification.mCallBack);
            if (!z) {
                int notificationState = pluginLockNotification.getNotificationState();
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("reset() state: ", notificationState, "PluginLockNotification");
                if (notificationState != -1 && notificationState != -2) {
                    PluginLockInstanceState pluginLockInstanceState = pluginLockNotification.mInstanceState;
                    int intValue = (pluginLockInstanceState == null || (recoverData3 = pluginLockInstanceState.getRecoverData()) == null) ? -1 : recoverData3.getNotificationBackupVisibility().intValue();
                    PluginLockInstanceState pluginLockInstanceState2 = pluginLockNotification.mInstanceState;
                    int intValue2 = (pluginLockInstanceState2 == null || (recoverData2 = pluginLockInstanceState2.getRecoverData()) == null) ? -1 : recoverData2.getNotificationBackupType().intValue();
                    pluginLockNotification.setNotificationVisibility(intValue);
                    pluginLockNotification.setNotificationType(intValue2);
                }
                pluginLockNotification.setNotificationBackup(-1, -1);
            }
        }
        if (this.mSecure != null) {
            Log.d("PluginLockSecure", "reset()");
        }
        PluginLockShortcut pluginLockShortcut = this.mShortcut;
        if (pluginLockShortcut != null) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("reset() reconnectReq: ", z, "PluginLockShortcut");
            pluginLockShortcut.mShortcutVisibility = -1;
            Log.d("PluginLockShortcut", "unregisterCallback() ");
            pluginLockShortcut.mCallbackValue = -1;
            pluginLockShortcut.mCallbackRegisterTime = 0L;
            pluginLockShortcut.mSettingsHelper.unregisterCallback(pluginLockShortcut.mCallback);
            if (!z) {
                int shortcutState = pluginLockShortcut.getShortcutState();
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("reset() state: ", shortcutState, "PluginLockShortcut");
                if (shortcutState != -1 && shortcutState != -2) {
                    PluginLockInstanceState pluginLockInstanceState3 = pluginLockShortcut.mInstanceState;
                    int intValue3 = (pluginLockInstanceState3 == null || (recoverData = pluginLockInstanceState3.getRecoverData()) == null) ? -1 : recoverData.getShortcutBackupValue().intValue();
                    Log.d("PluginLockShortcut", "reset() original: " + intValue3);
                    pluginLockShortcut.setShortcutVisibility(intValue3);
                }
                pluginLockShortcut.setShortcutBackup(-1);
            }
            this.mShortcurManager.updateShortcuts();
        }
        PluginLockStatusBar pluginLockStatusBar = this.mStatusBar;
        int i = 0;
        if (pluginLockStatusBar != null) {
            Log.d("PluginLockStatusBar", "reset()");
            KeyguardStatusBarViewController.C30636 c30636 = pluginLockStatusBar.mCallback;
            if (c30636 != null) {
                c30636.onVisibilityUpdated(0, 0);
            }
        }
        PluginLockSwipe pluginLockSwipe = this.mSwipe;
        if (pluginLockSwipe != null) {
            Log.d("PluginLockSwipe", "reset()");
            pluginLockSwipe.mNonSwipeMode = 0;
            pluginLockSwipe.mNonSwipeModeAngle = 45;
        }
        PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
        if (pluginLockWallpaper != null) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !LsRune.PLUGIN_LOCK_LSM) {
                pluginLockWallpaper.reset(z);
            } else if (z) {
                pluginLockWallpaper.reset(true);
            } else {
                pluginLockWallpaper.resetAll();
            }
        }
        PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
        if (pluginLockFaceWidget != null) {
            pluginLockFaceWidget.reset(z);
        }
        PluginLockMusic pluginLockMusic = this.mMusic;
        if (pluginLockMusic != null) {
            pluginLockMusic.mMusicPaddingTop = -1;
            pluginLockMusic.mMusicPaddingStart = -1;
            pluginLockMusic.mMusicPaddingEnd = -1;
            pluginLockMusic.mMusicVisibility = 0;
            pluginLockMusic.mMusicPaddingTopLand = -1;
            pluginLockMusic.mMusicPaddingStartLand = -1;
            pluginLockMusic.mMusicPaddingEndLand = -1;
            pluginLockMusic.mMusicVisibilityLand = 0;
            pluginLockMusic.mMusicGravityLand = 17;
            pluginLockMusic.loadMusicData();
        }
        if (this.mLockIcon != null) {
            Log.d("PluginLockLockIcon", "reset()");
        }
        while (true) {
            ArrayList arrayList = (ArrayList) this.mStateListenerList;
            if (i >= arrayList.size()) {
                return;
            }
            WeakReference weakReference = (WeakReference) arrayList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.onPluginLockReset();
            }
            i++;
        }
    }

    public final void setEnabled(boolean z) {
        LogUtil.m223d("PluginLockMediatorImpl", "setEnabled: " + z + ", " + this, new Object[0]);
        this.mIsEnabled = z;
    }

    public final void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        Log.d("PluginLockMediatorImpl", "setInstanceState, screen: " + i + ", state: " + pluginLockInstanceState);
        PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
        List list = this.mStateListenerList;
        if (pluginLockFaceWidget == null) {
            PluginLockFaceWidget pluginLockFaceWidget2 = new PluginLockFaceWidget(this.mContext, pluginLockInstanceState, this.mClockProvider, this.mSettingsHelper, this);
            this.mClock = pluginLockFaceWidget2;
            pluginLockFaceWidget2.mStateListenerList = list;
        }
        this.mClock.mInstanceState = pluginLockInstanceState;
        PluginLockMusic pluginLockMusic = this.mMusic;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        Context context = this.mContext;
        if (pluginLockMusic == null) {
            PluginLockMusic pluginLockMusic2 = new PluginLockMusic(context, pluginLockInstanceState, settingsHelper);
            this.mMusic = pluginLockMusic2;
            pluginLockMusic2.mStateListenerList = list;
        }
        this.mMusic.mInstanceState = pluginLockInstanceState;
        if (this.mHelpText == null) {
            this.mHelpText = new PluginLockHelpText(context, pluginLockInstanceState, settingsHelper);
        }
        this.mHelpText.mInstanceState = pluginLockInstanceState;
        if (this.mLockIcon == null) {
            this.mLockIcon = new PluginLockLockIcon(context, pluginLockInstanceState, settingsHelper);
        }
        this.mLockIcon.mInstanceState = pluginLockInstanceState;
        if (this.mNotification == null) {
            this.mNotification = new PluginLockNotification(context, pluginLockInstanceState, settingsHelper, this);
        }
        this.mNotification.mInstanceState = pluginLockInstanceState;
        if (this.mSecure == null) {
            this.mSecure = new PluginLockSecure(context, pluginLockInstanceState, settingsHelper);
        }
        this.mSecure.mInstanceState = pluginLockInstanceState;
        if (this.mShortcut == null) {
            this.mShortcut = new PluginLockShortcut(context, pluginLockInstanceState, settingsHelper, this);
        }
        this.mShortcut.mInstanceState = pluginLockInstanceState;
        if (this.mStatusBar == null) {
            PluginLockStatusBar pluginLockStatusBar = new PluginLockStatusBar(context, pluginLockInstanceState, settingsHelper);
            this.mStatusBar = pluginLockStatusBar;
            pluginLockStatusBar.mCallback = this.mStatusBarCallback;
        }
        this.mStatusBar.mInstanceState = pluginLockInstanceState;
        if (this.mSwipe == null) {
            this.mSwipe = new PluginLockSwipe(context, pluginLockInstanceState, settingsHelper);
        }
        this.mSwipe.mInstanceState = pluginLockInstanceState;
        PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
        pluginLockWallpaper.mInstanceState = pluginLockInstanceState;
        Log.d("PluginLockWallpaper", "setInstanceState, screen: " + i + ", instanceState: " + pluginLockInstanceState);
        if (pluginLockInstanceState == null && (((!LsRune.WALLPAPER_SUB_WATCHFACE && !LsRune.WALLPAPER_VIRTUAL_DISPLAY) || i != 1) && pluginLockWallpaper.isDynamicWallpaper())) {
            pluginLockWallpaper.reset(false);
        }
        if (pluginLockInstanceState != null) {
            this.mPluginContext = pluginLockInstanceState.mContext;
        } else {
            this.mPluginContext = null;
        }
    }

    public final void setKeyguardUserSwitchListener(KeyguardListener$UserSwitch keyguardListener$UserSwitch) {
        LogUtil.m223d("PluginLockMediatorImpl", "setKeyguardUserSwitchListener: " + keyguardListener$UserSwitch, new Object[0]);
        synchronized (this.mUserSwitchListenerList) {
            for (int i = 0; i < ((ArrayList) this.mUserSwitchListenerList).size(); i++) {
                if (((WeakReference) ((ArrayList) this.mUserSwitchListenerList).get(i)).get() == keyguardListener$UserSwitch) {
                    return;
                }
            }
            ((ArrayList) this.mUserSwitchListenerList).add(new WeakReference(keyguardListener$UserSwitch));
        }
    }

    public final void setPluginLock(PluginLock pluginLock) {
        PluginLockListener$State pluginLockListener$State;
        StringBuilder sb = new StringBuilder("setPluginLock() ");
        List list = this.mStateListenerList;
        sb.append(((ArrayList) list).size());
        Log.d("PluginLockMediatorImpl", sb.toString());
        for (int i = 0; i < ((ArrayList) list).size(); i++) {
            WeakReference weakReference = (WeakReference) ((ArrayList) list).get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.setPluginLock(pluginLock);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0137, code lost:
    
        if (r10.mBitmap == null) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01f9, code lost:
    
        if (((r6.isCustomPack(r6.getScreenType()) || r6.isServiceWallpaper(r6.getScreenType())) ? false : true) != false) goto L122;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0190  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setPluginWallpaper(int i, int i2, int i3, String str, String str2) {
        boolean z;
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceData.Data.RecoverData recoverData2;
        boolean z2;
        int parseInt;
        PluginLockInstanceData.Data.RecoverData recoverData3;
        PluginLockInstanceData.Data.RecoverData recoverData4;
        if ((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && i == 1 && (i2 == -2 || i2 >= 10)) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("setPluginWallpaper() Home, type: ", i2, ", screen: ", i, "PluginLockMediatorImpl");
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY && !this.mIsCoverAttached) {
                Log.w("PluginLockMediatorImpl", "setPluginWallpaper() cover is not attached");
                return;
            }
            int i4 = i2 >= 20 ? 1 : 0;
            PluginHomeWallpaper pluginHomeWallpaper = this.mHomeWallpaper;
            if (pluginHomeWallpaper != null) {
                pluginHomeWallpaper.setWallpaper(i4, i2, i3, str, str2);
                return;
            }
            return;
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("setPluginWallpaper() Lock, type: ", i2, ", screen: ", i, "PluginLockMediatorImpl");
        boolean z3 = LsRune.LOCKUI_SUB_DISPLAY_LOCK;
        PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
        if (z3 && i2 == -3) {
            pluginLockWallpaper.resetAll();
            return;
        }
        if (i2 == -2) {
            pluginLockWallpaper.reset(false);
            return;
        }
        Context context = this.mPluginContext;
        pluginLockWallpaper.getClass();
        Log.d("PluginLockWallpaper", "update() wallpaperType:" + i2 + ", sourceType:" + i3 + ", source:" + str + ", screenType:" + PluginLockWallpaper.sScreenType + ", iCrops = " + str2);
        pluginLockWallpaper.mRecoverRequestedScreen = -1;
        PluginLockWallpaper.PluginLockWallpaperData pluginLockWallpaperData = (PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(pluginLockWallpaper.getScreenType());
        pluginLockWallpaper.mHasData = (pluginLockWallpaperData.mType == -2 || (pluginLockWallpaperData.mBitmap == null && pluginLockWallpaperData.mPath == null && pluginLockWallpaperData.mUri == null)) ? false : true;
        pluginLockWallpaperData.mType = i2;
        pluginLockWallpaper.setPluginWallpaperType(i2);
        pluginLockWallpaperData.mIntelligentCrop = str2;
        if (i3 == 0) {
            String str3 = pluginLockWallpaperData.mPath;
            z = str3 == null || !str3.equals(str);
            pluginLockWallpaperData.mPath = str;
            pluginLockWallpaperData.mBitmap = null;
            pluginLockWallpaperData.mUri = null;
        } else if (i3 == 1) {
            try {
                pluginLockWallpaperData.mPath = null;
                pluginLockWallpaperData.mBitmap = null;
                pluginLockWallpaperData.mUri = null;
                parseInt = Integer.parseInt(str);
                if (pluginLockWallpaperData.mResourceId == parseInt) {
                }
            } catch (Exception e) {
                e = e;
                z2 = false;
            }
            try {
                Bitmap bitmap = PluginLockWallpaper.getBitmap(parseInt, context.getResources());
                pluginLockWallpaperData.mPath = null;
                pluginLockWallpaperData.mBitmap = bitmap;
                pluginLockWallpaperData.mUri = null;
                if (bitmap == null) {
                    pluginLockWallpaperData.mResourceId = 0;
                }
                pluginLockWallpaperData.mResourceId = parseInt;
                z = true;
            } catch (Exception e2) {
                e = e2;
                z2 = true;
                AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("couldn't load bitmap:"), "PluginLockWallpaper");
                z = z2;
                if (PluginLockWallpaper.isCloneDisplayRequired()) {
                }
                if (pluginLockWallpaperData.mBitmap == null) {
                }
                if (pluginLockWallpaper.isServiceWallpaper(pluginLockWallpaper.getScreenType())) {
                }
                if (pluginLockWallpaper.mWallpaperUpdateCallback != null) {
                    Log.d("PluginLockWallpaper", "update() onWallpaperUpdate will be called");
                    pluginLockWallpaper.mWallpaperUpdateCallback.onWallpaperUpdate(false);
                }
                if (PluginLockWallpaper.sScreenTypeChanged) {
                }
            }
        } else if (i3 != 2) {
            pluginLockWallpaperData.resetAll();
            if (PluginLockWallpaper.isCloneDisplayRequired()) {
                PluginLockInstanceState pluginLockInstanceState = pluginLockWallpaper.mInstanceState;
                if (pluginLockInstanceState != null && (recoverData4 = pluginLockInstanceState.getRecoverData()) != null) {
                    recoverData4.setWallpaperSource();
                    recoverData4.setWallpaperType();
                    pluginLockWallpaper.mInstanceState.updateDb();
                }
            } else {
                int i5 = PluginLockWallpaper.sScreenType;
                PluginLockInstanceState pluginLockInstanceState2 = pluginLockWallpaper.mInstanceState;
                if (pluginLockInstanceState2 != null && (recoverData3 = pluginLockInstanceState2.getRecoverData()) != null) {
                    recoverData3.setWallpaperSource(i5, -1);
                    recoverData3.setWallpaperType(i5, -1);
                    pluginLockWallpaper.mInstanceState.updateDb();
                }
            }
            z = false;
        } else {
            Uri uri = pluginLockWallpaperData.mUri;
            Uri parse = Uri.parse(str);
            z = uri == null || !uri.equals(parse);
            pluginLockWallpaperData.mPath = null;
            pluginLockWallpaperData.mBitmap = null;
            pluginLockWallpaperData.mUri = parse;
        }
        if (PluginLockWallpaper.isCloneDisplayRequired()) {
            int i6 = PluginLockWallpaper.sScreenType;
            PluginLockInstanceState pluginLockInstanceState3 = pluginLockWallpaper.mInstanceState;
            if (pluginLockInstanceState3 != null && (recoverData = pluginLockInstanceState3.getRecoverData()) != null) {
                recoverData.setWallpaperDynamic(i6, i2);
                pluginLockWallpaper.mInstanceState.updateDb();
            }
        } else {
            PluginLockInstanceState pluginLockInstanceState4 = pluginLockWallpaper.mInstanceState;
            if (pluginLockInstanceState4 != null && (recoverData2 = pluginLockInstanceState4.getRecoverData()) != null) {
                recoverData2.setWallpaperDynamic(i2);
                pluginLockWallpaper.mInstanceState.updateDb();
            }
        }
        if (pluginLockWallpaperData.mBitmap == null || pluginLockWallpaperData.mPath != null || pluginLockWallpaperData.mUri != null) {
            if (pluginLockWallpaper.isServiceWallpaper(pluginLockWallpaper.getScreenType())) {
                if (!pluginLockWallpaper.isCustomPack(pluginLockWallpaper.getScreenType())) {
                }
                if (PluginLockWallpaper.isCloneDisplayRequired()) {
                    pluginLockWallpaper.setMultiPackWallpaperSource(0);
                    pluginLockWallpaper.setMultiPackWallpaperSource(1);
                } else {
                    pluginLockWallpaper.setMultiPackWallpaperSource(PluginLockWallpaper.sScreenType);
                }
            } else if (PluginLockWallpaper.isCloneDisplayRequired()) {
                pluginLockWallpaper.backupWallpaperType(0);
                pluginLockWallpaper.backupWallpaperType(1);
                pluginLockWallpaper.backupWallpaperSource(0);
                pluginLockWallpaper.backupWallpaperSource(1);
            } else {
                pluginLockWallpaper.backupWallpaperType(PluginLockWallpaper.sScreenType);
                pluginLockWallpaper.backupWallpaperSource(PluginLockWallpaper.sScreenType);
            }
        }
        if (pluginLockWallpaper.mWallpaperUpdateCallback != null && (!PluginLockWallpaper.sScreenTypeChanged || !pluginLockWallpaper.mHasData || z)) {
            Log.d("PluginLockWallpaper", "update() onWallpaperUpdate will be called");
            pluginLockWallpaper.mWallpaperUpdateCallback.onWallpaperUpdate(false);
        }
        if (PluginLockWallpaper.sScreenTypeChanged) {
            pluginLockWallpaper.mHintUpdatedSkip = false;
        } else {
            PluginLockWallpaper.sScreenTypeChanged = false;
            pluginLockWallpaper.mHintUpdatedSkip = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setPluginWallpaperHint(String str) {
        final SemWallpaperColors semWallpaperColors;
        PluginHomeWallpaper pluginHomeWallpaper;
        if (str != null) {
            if (str.equals("white")) {
                SemWallpaperColors.Builder builder = new SemWallpaperColors.Builder();
                builder.setColorType(0);
                semWallpaperColors = builder.build();
            } else if (str.equals("black")) {
                SemWallpaperColors.Builder builder2 = new SemWallpaperColors.Builder();
                builder2.setColorType(1);
                semWallpaperColors = builder2.build();
            } else if (!str.equals("")) {
                semWallpaperColors = SemWallpaperColors.fromXml(str);
            }
            if (!LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                pluginHomeWallpaper = this.mHomeWallpaper;
                if (pluginHomeWallpaper.getWallpaperType(1) > 10) {
                    pluginHomeWallpaper.getClass();
                    if (PluginHomeWallpaper.sScreenType == 1) {
                        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY && !this.mIsCoverAttached) {
                            Log.w("PluginLockMediatorImpl", "setPluginWallpaperHint() cover is not attached");
                            return;
                        }
                        Map map = pluginHomeWallpaper.mWallpaperDataList;
                        PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) map).get(Integer.valueOf(PluginHomeWallpaper.getKey(PluginHomeWallpaper.sScreenType)));
                        if (wallpaperData != null) {
                            wallpaperData.mHints = semWallpaperColors;
                        }
                        if (pluginHomeWallpaper.mWallpaperUpdateCallback != null) {
                            int key = PluginHomeWallpaper.getKey(PluginHomeWallpaper.sScreenType);
                            Log.d("PluginHomeWallpaper", "updateHint() onWallpaperHintUpdate will be called: " + key);
                            PluginHomeWallpaper.WallpaperData wallpaperData2 = (PluginHomeWallpaper.WallpaperData) ((HashMap) map).get(Integer.valueOf(key));
                            if (wallpaperData2 != null) {
                                pluginHomeWallpaper.mWallpaperUpdateCallback.onWallpaperHintUpdate(wallpaperData2.mHints);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
            }
            PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
            ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(pluginLockWallpaper.getScreenType())).mHints = semWallpaperColors;
            pluginLockWallpaper.updateHint();
            if (this.mViewMode == 1 || this.mWindowListener == null) {
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onViewModePageChanged(semWallpaperColors);
                return;
            } else {
                this.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PluginLockMediatorImpl pluginLockMediatorImpl = PluginLockMediatorImpl.this;
                        pluginLockMediatorImpl.mWindowListener.onViewModePageChanged(semWallpaperColors);
                    }
                });
                return;
            }
        }
        semWallpaperColors = null;
        if (!LsRune.WALLPAPER_SUB_WATCHFACE) {
        }
        pluginHomeWallpaper = this.mHomeWallpaper;
        if (pluginHomeWallpaper.getWallpaperType(1) > 10) {
        }
        PluginLockWallpaper pluginLockWallpaper2 = this.mLockWallpaper;
        ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper2.mWallpaperDataList).get(pluginLockWallpaper2.getScreenType())).mHints = semWallpaperColors;
        pluginLockWallpaper2.updateHint();
        if (this.mViewMode == 1) {
        }
    }

    public final void setScreenOrientation(boolean z, boolean z2) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("setScreenOrientation noSensor: ", z, ", hideMenu : ", z2, "PluginLockMediatorImpl");
        if (!z && WallpaperUtils.isVideoWallpaper()) {
            Log.d("PluginLockMediatorImpl", "setScreenOrientation ignore, video wallpaper");
            return;
        }
        if (z) {
            Point point = DeviceState.sDisplaySize;
            if (DeviceType.isTablet()) {
                Log.d("PluginLockMediatorImpl", "setScreenOrientation ignore, tablet");
                return;
            }
        }
        if (this.mWindowListener != null) {
            if (z) {
                this.mIsRotateMenuHide = z2;
            } else {
                this.mIsRotateMenuHide = false;
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(this.mWindowListener.this$0, z);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda0(this, z, 3));
            }
        }
    }

    public final void updateDynamicLockData(String str) {
        DynamicLockData dynamicLockData;
        PluginLockListener$State pluginLockListener$State;
        PluginLockUtils pluginLockUtils = this.mUtils;
        int i = 0;
        if ((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && sScreenType == 1) {
            LogUtil.m226w("PluginLockMediatorImpl", "updateDynamicLockData skip", new Object[0]);
            return;
        }
        LogUtil.m223d("PluginLockMediatorImpl", KeyAttributes$$ExternalSyntheticOutline0.m21m("updateDynamicLockData dynamicLockData: ", str), new Object[0]);
        try {
            dynamicLockData = (DynamicLockData) new Gson().fromJson(DynamicLockData.class, str);
        } catch (Throwable th) {
            pluginLockUtils.addDump("PluginLockMediatorImpl", "[parse, update] " + th.toString());
            th.printStackTrace();
            dynamicLockData = null;
        }
        Log.d("PluginLockMediatorImpl", "updateDynamicLockData() currData:" + this.mCurrentDynamicLockData + ", newData:" + dynamicLockData);
        if (dynamicLockData != null) {
            try {
                PluginLockSwipe pluginLockSwipe = this.mSwipe;
                DynamicLockData dynamicLockData2 = this.mCurrentDynamicLockData;
                pluginLockSwipe.getClass();
                Log.d("PluginLockSwipe", "update()");
                pluginLockSwipe.apply(dynamicLockData2, dynamicLockData);
                this.mSecure.getClass();
                Log.d("PluginLockSecure", "update()");
                Log.d("PluginLockSecure", "apply()");
                dynamicLockData.getCaptureData().getType().intValue();
                PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
                DynamicLockData dynamicLockData3 = this.mCurrentDynamicLockData;
                pluginLockWallpaper.getClass();
                Log.d("PluginLockWallpaper", "update()");
                if (dynamicLockData3 == null || !dynamicLockData.getWallpaperData().equals(dynamicLockData3.getWallpaperData())) {
                    dynamicLockData.getWallpaperData().getUpdateStyle().intValue();
                    dynamicLockData.getWallpaperData().getRecoverType().intValue();
                }
                this.mHelpText.getClass();
                Log.d("PluginLockHelpText", "update()");
                Log.d("PluginLockHelpText", "apply()");
                this.mLockIcon.getClass();
                Log.d("PluginLockLockIcon", "update()");
            } catch (Throwable th2) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[basic, update] " + th2.toString());
                th2.printStackTrace();
            }
            try {
                this.mClock.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th3) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[clock, update] " + th3.toString());
                th3.printStackTrace();
            }
            try {
                this.mMusic.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th4) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[music, update] " + th4.toString());
                th4.printStackTrace();
            }
            try {
                this.mNotification.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th5) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[notification, update] " + th5.toString());
                th5.printStackTrace();
            }
            try {
                PluginLockStatusBar pluginLockStatusBar = this.mStatusBar;
                pluginLockStatusBar.getClass();
                Log.d("PluginLockStatusBar", "update()");
                boolean isStatusBarIconVisible = dynamicLockData.isStatusBarIconVisible();
                boolean isStatusBarNetworkVisible = dynamicLockData.isStatusBarNetworkVisible();
                KeyguardStatusBarViewController.C30636 c30636 = pluginLockStatusBar.mCallback;
                if (c30636 != null) {
                    c30636.onVisibilityUpdated(isStatusBarIconVisible ? 0 : 4, isStatusBarNetworkVisible ? 0 : 4);
                }
            } catch (Throwable th6) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[statusbar, update] " + th6.toString());
                th6.printStackTrace();
            }
            try {
                this.mShortcut.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th7) {
                pluginLockUtils.addDump("PluginLockMediatorImpl", "[shortcut, update] " + th7.toString());
                th7.printStackTrace();
            }
            this.mCurrentDynamicLockData = dynamicLockData;
            this.mIsDynamicLockData = dynamicLockData.isDlsData();
            publishLockStarState();
        }
        while (true) {
            ArrayList arrayList = (ArrayList) this.mStateListenerList;
            if (i >= arrayList.size()) {
                return;
            }
            WeakReference weakReference = (WeakReference) arrayList.get(i);
            if (weakReference != null && (pluginLockListener$State = (PluginLockListener$State) weakReference.get()) != null) {
                pluginLockListener$State.updateDynamicLockData(str);
            }
            i++;
        }
    }

    public final void updateWindowSecureState(boolean z) {
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("updateWindowSecureState() : ", z, "PluginLockMediatorImpl");
        this.mIsSecureWindow = z;
        if (this.mWindowListener != null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda0(this, z, 0));
                return;
            }
            C2456x44c40078 c2456x44c40078 = this.mWindowListener;
            c2456x44c40078.getClass();
            String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = c2456x44c40078.this$0;
            NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
            currentState.securedWindow = z;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
    }
}
