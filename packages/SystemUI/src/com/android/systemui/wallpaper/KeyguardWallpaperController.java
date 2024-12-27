package com.android.systemui.wallpaper;

import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.inject.Provider;

public final class KeyguardWallpaperController extends IWallpaperManagerCallback.Stub implements KeyguardWallpaper {
    public static KeyguardWallpaperController sController;
    public final Context mContext;
    public final Map mEventListeners = new HashMap(2);
    public final ExecutorService mExecutor;
    public final AnonymousClass2 mKnoxStateCallback;
    public final Handler mMainHandler;
    public Consumer mNoSensorConsumer;
    public int mOldTransparentType;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final IWallpaperManager mService;
    private final SettingsHelper mSettingsHelper;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final WallpaperAnalytics mWallpaperAnalytics;
    public final WallpaperChangeNotifier mWallpaperChangeNotifier;
    public final WallpaperEventNotifier mWallpaperEventNotifier;
    public final WallpaperLogger mWallpaperLogger;
    public final WallpaperManager mWallpaperManager;
    public final AnonymousClass3 mWorkHandler;

    public static void $r8$lambda$EG0gQOZLZG2rZ3JVMqdpjjIo4Yo(KeyguardWallpaperController keyguardWallpaperController) {
        Consumer consumer;
        keyguardWallpaperController.getClass();
        boolean z = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z2 = WallpaperUtils.mIsEmergencyMode;
        boolean isLiveWallpaper = WallpaperUtils.isLiveWallpaper();
        boolean isLockScreenRotationAllowed = keyguardWallpaperController.mSettingsHelper.isLockScreenRotationAllowed();
        boolean isVideoWallpaper = WallpaperUtils.isVideoWallpaper(keyguardWallpaperController.mContext);
        boolean z3 = false;
        if (!isLockScreenRotationAllowed) {
            isVideoWallpaper = true;
        } else if (z || z2 || isLiveWallpaper) {
            isVideoWallpaper = false;
        }
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("disableRotateIfNeeded: video = ", " , rotate support = ", isVideoWallpaper);
        boolean z4 = LsRune.WALLPAPER_ROTATABLE_WALLPAPER;
        m.append(z4);
        m.append(" , sub = ");
        m.append(isSubDisplay());
        m.append(" , enabled = ");
        m.append(keyguardWallpaperController.mSettingsHelper.isLockScreenRotationAllowed());
        m.append(" , isUpsMode = ");
        m.append(z);
        m.append(" , isEmergencyMode = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z2, " , isLiveWallpaperSettingEnabled = ", isLiveWallpaper, " , isLockScreenRotationAllowed = ");
        m.append(isLockScreenRotationAllowed);
        keyguardWallpaperController.printLognAddHistory(m.toString());
        if (!LsRune.WALLPAPER_VIDEO_WALLPAPER || (consumer = keyguardWallpaperController.mNoSensorConsumer) == null) {
            return;
        }
        if (isVideoWallpaper && (!z4 || isSubDisplay())) {
            z3 = true;
        }
        consumer.accept(Boolean.valueOf(z3));
    }

    /* renamed from: -$$Nest$mhandleWallpaperMessage, reason: not valid java name */
    public static void m2368$$Nest$mhandleWallpaperMessage(final KeyguardWallpaperController keyguardWallpaperController, Message message) {
        keyguardWallpaperController.getClass();
        int i = message.what;
        if (i == 607) {
            final SemWallpaperColors wallpaperColors = keyguardWallpaperController.getWallpaperColors(false);
            final int i2 = WallpaperUtils.sCurrentWhich;
            keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                    SemWallpaperColors semWallpaperColors = wallpaperColors;
                    int i3 = i2;
                    keyguardWallpaperController2.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i3), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i3), semWallpaperColors);
                }
            });
        }
        if (i == 608) {
            Bundle data = message.getData();
            if (data == null) {
                keyguardWallpaperController.printLognAddHistory("handleWallpaperColorChanged: Error - extra is null!");
                return;
            }
            final SemWallpaperColors parcelable = data.getParcelable("wallpaper_colors");
            final int i3 = data.getInt("which", 2);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                int i4 = WallpaperUtils.sCurrentWhich;
                if ((i4 & 16) != 0) {
                    if ((i3 & 16) == 0 && (!LsRune.WALLPAPER_SUB_WATCHFACE || (i3 & 2) != 2)) {
                        keyguardWallpaperController.printLognAddHistory(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i4, i3, "handleWallpaperColorChanged: currentWhich = ", ", which = ", ". Return."));
                        return;
                    }
                } else if (!LsRune.WALLPAPER_SUB_WATCHFACE && (i3 & 16) != 0) {
                    keyguardWallpaperController.printLognAddHistory(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i4, i3, "handleWallpaperColorChanged: currentWhich = ", ", which = ", ". Return."));
                    return;
                }
            }
            if (parcelable != null) {
                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                        SemWallpaperColors semWallpaperColors = parcelable;
                        int i32 = i3;
                        keyguardWallpaperController2.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
                    }
                });
                return;
            } else {
                keyguardWallpaperController.printLognAddHistory("handleWallpaperColorChanged: Error - colors is null!");
                return;
            }
        }
        if (i == 721) {
            int i5 = message.arg1;
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "onUserSwitchComplete userId = ", " , selectedUserId = ");
            m.append(keyguardWallpaperController.mSelectedUserInteractor.getSelectedUserId(false));
            Log.d("KeyguardWallpaperController", m.toString());
            WallpaperUtils.loadDeviceState(i5, keyguardWallpaperController.mContext);
            WallpaperEventNotifier wallpaperEventNotifier = keyguardWallpaperController.mWallpaperEventNotifier;
            WallpaperManager wallpaperManager = wallpaperEventNotifier.mWallpaperManager;
            wallpaperEventNotifier.setCurStatusFlag(false, wallpaperManager != null ? wallpaperManager.semGetWallpaperColors(2) : null);
            keyguardWallpaperController.sendUpdateWallpaperMessage(607, null);
            keyguardWallpaperController.printLognAddHistory("onUserSwitchComplete: userId = " + i5 + ", wallpaper type = " + keyguardWallpaperController.getLockWallpaperType(true));
            return;
        }
        if (i == 722) {
            Context context = keyguardWallpaperController.mContext;
            WallpaperUtils.loadDeviceState(context.getUserId(), context);
            keyguardWallpaperController.sendUpdateWallpaperMessage(607, null);
            keyguardWallpaperController.printLognAddHistory("onBootCompleted: wallpaeprType = " + WallpaperUtils.sWallpaperType);
            return;
        }
        if (i == 724) {
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            Bundle bundle = new Bundle();
            bundle.putBoolean("visible", booleanValue);
            keyguardWallpaperController.broadcastEvent(724, bundle);
            return;
        }
        if (i == 1003) {
            WallpaperAnalytics wallpaperAnalytics = keyguardWallpaperController.mWallpaperAnalytics;
            if (wallpaperAnalytics != null) {
                wallpaperAnalytics.updateWallpaperStatus(message.arg1);
                return;
            }
            return;
        }
        switch (i) {
            case 601:
                Bundle data2 = message.getData();
                Log.d("KeyguardWallpaperController", "handleWallpaperChanged");
                int lockWallpaperType = keyguardWallpaperController.getLockWallpaperType(data2 != null ? data2.getBoolean("include_dls", true) : true);
                int i6 = isSubDisplay() ? 16 : 4;
                WallpaperUtils.setWallpaperType(lockWallpaperType, keyguardWallpaperController.mContext);
                keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda1(keyguardWallpaperController, 2));
                keyguardWallpaperController.mWallpaperAnalytics.updateWallpaperStatus(i6 | 2);
                break;
            case 602:
            case VolteConstants.ErrorCode.DECLINE /* 603 */:
                Log.d("KeyguardWallpaperController", "colorUpdateForModeChange");
                final SemWallpaperColors wallpaperColors2 = keyguardWallpaperController.getWallpaperColors(false);
                final int i7 = WallpaperUtils.sCurrentWhich;
                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                        SemWallpaperColors semWallpaperColors = wallpaperColors2;
                        int i32 = i7;
                        keyguardWallpaperController2.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
                    }
                });
                break;
            default:
                switch (i) {
                    case 728:
                        keyguardWallpaperController.broadcastEvent(728, null);
                        break;
                    case 729:
                        keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = true;
                        break;
                    case 730:
                        keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = false;
                        final SemWallpaperColors wallpaperColors3 = keyguardWallpaperController.getWallpaperColors(false);
                        final int i8 = WallpaperUtils.sCurrentWhich;
                        keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                                SemWallpaperColors semWallpaperColors = wallpaperColors3;
                                int i32 = i8;
                                keyguardWallpaperController2.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
                            }
                        });
                        break;
                    case 731:
                        keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = false;
                        final SemWallpaperColors wallpaperColors4 = keyguardWallpaperController.getWallpaperColors(false);
                        final int i9 = WallpaperUtils.sCurrentWhich;
                        keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                                SemWallpaperColors semWallpaperColors = wallpaperColors4;
                                int i32 = i9;
                                keyguardWallpaperController2.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
                            }
                        });
                        break;
                    case 732:
                        keyguardWallpaperController.notifyEvent(732);
                        break;
                    case 733:
                        keyguardWallpaperController.handleColorThemeStateChanged(false);
                        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                            keyguardWallpaperController.handleColorThemeStateChanged(true);
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                            case 903:
                                keyguardWallpaperController.handleAdaptiveColorModeChanged(false);
                                break;
                            case 904:
                                keyguardWallpaperController.mWallpaperAnalytics.updateWallpaperStatus((((Boolean) message.obj).booleanValue() ? 16 : 4) | 2);
                                int lockscreenWallpaperTransparent = keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperTransparent();
                                TooltipPopup$$ExternalSyntheticOutline0.m(keyguardWallpaperController.mOldTransparentType, "KeyguardWallpaperController", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(lockscreenWallpaperTransparent, "forceBroadcastWhiteKeyguardWallpaper: cur = ", " , old = "));
                                if (keyguardWallpaperController.mOldTransparentType == 2 && lockscreenWallpaperTransparent != 2) {
                                    keyguardWallpaperController.mSettingsHelper.forceBroadcastWhiteKeyguardWallpaper();
                                }
                                keyguardWallpaperController.mOldTransparentType = lockscreenWallpaperTransparent;
                                WallpaperEventNotifier wallpaperEventNotifier2 = keyguardWallpaperController.mWallpaperEventNotifier;
                                WallpaperManager wallpaperManager2 = wallpaperEventNotifier2.mWallpaperManager;
                                wallpaperEventNotifier2.setCurStatusFlag(false, wallpaperManager2 != null ? wallpaperManager2.semGetWallpaperColors(2) : null);
                                break;
                            case 905:
                                keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda1(keyguardWallpaperController, 2));
                                break;
                            case 906:
                                Bundle data3 = message.getData();
                                if (data3 != null) {
                                    if (keyguardWallpaperController.mPluginWallpaperManager != null) {
                                        int i10 = data3.getInt("which", 2);
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(i10, "handleColorAreasChanged : which = ", "KeyguardWallpaperController");
                                        keyguardWallpaperController.mPluginWallpaperManager.onColorAreasChanged(i10);
                                        break;
                                    } else {
                                        Log.e("KeyguardWallpaperController", "handleColorAreasChanged : plugin wallpaper manager is null");
                                        break;
                                    }
                                } else {
                                    Log.e("KeyguardWallpaperController", "handleColorAreasChanged : extra is null");
                                    break;
                                }
                            case 907:
                                keyguardWallpaperController.handleAdaptiveColorModeChanged(true);
                                break;
                            default:
                                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage: unsupported command ("), message.what, ")", "KeyguardWallpaperController");
                                break;
                        }
                }
        }
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$3] */
    public KeyguardWallpaperController(Context context, WallpaperManager wallpaperManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle, WallpaperLogger wallpaperLogger, WallpaperEventNotifier wallpaperEventNotifier, SystemWallpaperColors systemWallpaperColors, ConfigurationController configurationController, KeyguardFoldController keyguardFoldController, KeyguardWallpaperEventHandler keyguardWallpaperEventHandler, SelectedUserInteractor selectedUserInteractor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        boolean z;
        new Provider() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.1
            @Override // javax.inject.Provider
            public final Object get() {
                return KeyguardWallpaperController.this.getHints();
            }
        };
        this.mExecutor = Executors.newFixedThreadPool(2);
        HandlerThread handlerThread = new HandlerThread("KeyguardWallpaperThread");
        this.mMainHandler = new Handler(Looper.getMainLooper());
        KnoxStateMonitorCallback knoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.2
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onEnableMDMWallpaper() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                keyguardWallpaperController2.printLognAddHistory("onEnableMDMWallpaper");
                keyguardWallpaperController2.broadcastEvent(605, null);
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onMDMWallpaperChanged() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                keyguardWallpaperController2.printLognAddHistory("onMDMWallpaperChanged");
                keyguardWallpaperController2.broadcastEvent(VolteConstants.ErrorCode.NOT_ACCEPTABLE2, null);
            }
        };
        this.mContext = context;
        this.mWallpaperManager = wallpaperManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mWallpaperLogger = wallpaperLogger;
        this.mSelectedUserInteractor = selectedUserInteractor;
        Log.d("KeyguardWallpaperController", "KeyguardWallpaperController() selectedUserId = " + selectedUserInteractor.getSelectedUserId(false));
        handlerThread.start();
        handlerThread.setPriority(10);
        final ?? r4 = new Handler(handlerThread.getLooper()) { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                KeyguardWallpaperController.m2368$$Nest$mhandleWallpaperMessage(KeyguardWallpaperController.this, message);
            }
        };
        this.mWorkHandler = r4;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
        WallpaperUtils.setSettingsHelper(settingsHelper);
        WallpaperUtils.loadDeviceState(context.getUserId(), context);
        this.mWallpaperEventNotifier = wallpaperEventNotifier;
        this.mSystemWallpaperColors = systemWallpaperColors;
        IWallpaperManager asInterface = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        this.mService = asInterface;
        this.mWallpaperChangeNotifier = wallpaperChangeNotifier;
        WallpaperAnalytics wallpaperAnalytics = new WallpaperAnalytics(context, pluginWallpaperManager, settingsHelper);
        this.mWallpaperAnalytics = wallpaperAnalytics;
        SharedPreferences sharedPreferences = wallpaperAnalytics.mContext.getSharedPreferences(SystemUIAnalytics.WALLPAPER_PREF_NAME, 0);
        int i = sharedPreferences.getInt("version", -1);
        if (i < 1) {
            Log.i("WallpaperAnalytics", "migrateIfNeeds: perform migration. from=" + i + ", to=1");
            wallpaperAnalytics.updateWallpaperStatus(6);
            wallpaperAnalytics.updateWallpaperStatus(5);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                wallpaperAnalytics.updateWallpaperStatus(18);
                wallpaperAnalytics.updateWallpaperStatus(17);
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("version", 1);
            edit.apply();
        }
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("which", 0);
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(intExtra, "onReceive: system wallpaper has been changed. which = ", "KeyguardWallpaperController");
                if (intExtra > 0) {
                    Message obtainMessage = obtainMessage(1003);
                    obtainMessage.arg1 = intExtra;
                    sendMessage(obtainMessage);
                }
            }
        }, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
        this.mOldTransparentType = settingsHelper.getLockscreenWallpaperTransparent();
        if (asInterface == null) {
            Log.e("KeyguardWallpaperController", "WallpaperManagerService is not ready yet! Just return here!");
            return;
        }
        keyguardWallpaperEventHandler.setEventReceiver(new Consumer() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                r4.sendMessage((Message) obj);
            }
        });
        new Thread(new KeyguardWallpaperController$$ExternalSyntheticLambda1(this, 0), "LockWallpaperCB").start();
        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(knoxStateMonitorCallback);
        sendUpdateWallpaperMessage(607, null);
        sController = this;
        boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z2) {
            z = false;
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda2
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z3) {
                    KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                    keyguardWallpaperController.getClass();
                    keyguardWallpaperController.printLognAddHistory("onFolderStateChanged: isOpened = " + z3);
                    boolean z4 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                    int i2 = z4 ? !z3 ? 18 : 6 : 2;
                    if (WallpaperUtils.sCurrentWhich != i2) {
                        TooltipPopup$$ExternalSyntheticOutline0.m(WallpaperUtils.sCurrentWhich, "KeyguardWallpaperController", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onFolderStateChanged: which = ", ", previous which = "));
                        WallpaperUtils.sCurrentWhich = i2;
                    }
                    if (!z4 || LsRune.WALLPAPER_SUB_WATCHFACE) {
                        return;
                    }
                    keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda1(keyguardWallpaperController, 2));
                }
            }, 1001, false);
        } else {
            z = false;
        }
        try {
            boolean z3 = asInterface.semGetWallpaperType(6) == 3 ? true : z;
            if (!z3 && z2 && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                z3 = asInterface.semGetWallpaperType(18) == 3 ? true : z;
            }
            if (!z3 || MultiPackDispatcher.enableDlsIfDisabled(context)) {
                return;
            }
            Log.e("KeyguardWallpaperController", "Failed to enable DLS.");
        } catch (RemoteException e) {
            Log.e("KeyguardWallpaperController", "System dead?" + e);
        }
    }

    public static boolean isSubDisplay() {
        return (WallpaperUtils.sCurrentWhich & 16) != 0;
    }

    public final ArrayList broadcastEvent(int i, Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        for (ImageWallpaper.IntegratedEngine.AnonymousClass1 anonymousClass1 : ((HashMap) this.mEventListeners).values()) {
            if (anonymousClass1 != null) {
                arrayList.add(anonymousClass1.onEventReceived(i, bundle));
            }
        }
        return arrayList;
    }

    public final void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardWallpaperController: ");
        WallpaperEventNotifier wallpaperEventNotifier = this.mWallpaperEventNotifier;
        if (wallpaperEventNotifier != null) {
            printWriter.println("WallpaperEventNotifier:");
            synchronized (wallpaperEventNotifier) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
                wallpaperEventNotifier.mLogs.forEach(new Consumer() { // from class: com.android.systemui.wallpaper.WallpaperEventNotifier$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PrintWriter printWriter2 = printWriter;
                        DateFormat dateFormat = simpleDateFormat;
                        WallpaperEventNotifier.DebugLog debugLog = (WallpaperEventNotifier.DebugLog) obj;
                        Date date = new Date(debugLog.time);
                        StringBuilder sb = new StringBuilder("    ");
                        sb.append(dateFormat.format(date));
                        sb.append(": ");
                        CarrierTextController$$ExternalSyntheticOutline0.m(sb, debugLog.text, printWriter2);
                    }
                });
            }
            KeyguardWallpaperColors keyguardWallpaperColors = wallpaperEventNotifier.mKeyguardWallpaperColors;
            keyguardWallpaperColors.getClass();
            printWriter.println("KeyguardWallpaperColors:");
            try {
                printWriter.println("\tLast wallpaper color = " + keyguardWallpaperColors.getSemWallpaperColors(keyguardWallpaperColors.mSelectedUserId, false).toSimpleString() + "\n");
            } catch (Exception e) {
                printWriter.println("\nDump error: " + e.getMessage() + "\n");
            }
        }
        WallpaperUtils.dump(this.mContext, printWriter);
    }

    public final SemWallpaperColors.Item getHint(long j, boolean z) {
        SemWallpaperColors.Item item;
        SemWallpaperColors.Item item2;
        SemWallpaperColors blankWallpaperColors = (WallpaperUtils.mIsUltraPowerSavingMode || WallpaperUtils.mIsEmergencyMode) ? SemWallpaperColors.getBlankWallpaperColors() : this.mWallpaperEventNotifier.getSemWallpaperColors(z);
        if (blankWallpaperColors != null && (item2 = blankWallpaperColors.get(j)) != null) {
            return item2;
        }
        SemWallpaperColors wallpaperColors = getWallpaperColors(z);
        if (wallpaperColors != null && (item = wallpaperColors.get(j)) != null) {
            return item;
        }
        Log.d("KeyguardWallpaperController", "getDummyHintItem()");
        return new SemWallpaperColors.Item(0, 1.0f, 0.5f);
    }

    public final SemWallpaperColors getHints() {
        boolean z = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z2 = WallpaperUtils.mIsEmergencyMode;
        if (z || z2) {
            return SemWallpaperColors.getBlankWallpaperColors();
        }
        SemWallpaperColors semWallpaperColors = this.mWallpaperEventNotifier.getSemWallpaperColors(false);
        if (semWallpaperColors != null) {
            return semWallpaperColors;
        }
        SemWallpaperColors wallpaperColors = getWallpaperColors(false);
        if (wallpaperColors != null) {
            return wallpaperColors;
        }
        Log.d("KeyguardWallpaperController", "getHints: getBlankWallpaperColors!");
        return SemWallpaperColors.getBlankWallpaperColors();
    }

    public final int getLockWallpaperType(boolean z) {
        int i = WallpaperUtils.sCurrentWhich;
        int i2 = 8;
        if (WhichChecker.isFlagEnabled(i, 8)) {
            return 0;
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            i = 6;
        }
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("\n - isIncludePluginLock: ", "\n - mService: ", z);
        m.append(this.mService);
        IWallpaperManager iWallpaperManager = this.mService;
        int i3 = -1;
        if (iWallpaperManager != null) {
            try {
                if (z) {
                    boolean isDynamicWallpaperEnabled = this.mPluginWallpaperManager.isDynamicWallpaperEnabled();
                    m.append("\n - isDlsWallpaperEnabled: ");
                    m.append(isDynamicWallpaperEnabled);
                    if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled()) {
                        i3 = this.mPluginWallpaperManager.getWallpaperType();
                        m.append("\n - type[DLS]: ");
                        m.append(i3);
                    } else {
                        i3 = this.mService.semGetWallpaperType(i);
                        try {
                            if (this.mSelectedUserInteractor.getSelectedUserId() == 0 && !this.mUpdateMonitor.mUserIsUnlocked.get(0)) {
                                if (this.mSelectedUserInteractor.getSelectedUserId() == 0 && LsRune.KEYGUARD_FBE && this.mSelectedUserInteractor.getSelectedUserId() == 0 && !this.mUpdateMonitor.mUserIsUnlocked.get(0)) {
                                    int screenId = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                                    if (this.mPluginWallpaperManager.isFbeWallpaperAvailable(screenId)) {
                                        if (!this.mPluginWallpaperManager.isFbeWallpaperVideo(screenId)) {
                                            i2 = 0;
                                        }
                                        m.append("\n - type[from FBE]: ");
                                        m.append(i2);
                                    }
                                }
                                i2 = i3;
                                m.append("\n - type[from FBE]: ");
                                m.append(i2);
                            } else if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled(0) || i3 != 3) {
                                m.append("\n - type[from WMS]: ");
                                m.append(i3);
                            } else {
                                if (this.mSelectedUserInteractor.getSelectedUserId() == 0) {
                                    int screenId2 = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                                    if (this.mPluginWallpaperManager.isFbeWallpaperAvailable(screenId2)) {
                                        if (!this.mPluginWallpaperManager.isFbeWallpaperVideo(screenId2)) {
                                            i2 = 0;
                                        }
                                        m.append("\n - type[force FBE]: ");
                                        m.append(i2);
                                    }
                                }
                                i2 = i3;
                                m.append("\n - type[force FBE]: ");
                                m.append(i2);
                            }
                            i3 = i2;
                        } catch (RemoteException e) {
                            e = e;
                            i3 = i2;
                            Log.e("KeyguardWallpaperController", "System dead?" + e);
                            m.append("\n - WallpaperUtils.getCurrentWhich(): ");
                            m.append(WallpaperUtils.sCurrentWhich);
                            Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + m.toString());
                            return i3;
                        }
                    }
                } else {
                    i3 = iWallpaperManager.semGetWallpaperType(i);
                    m.append("\n - type[from WMS]: ");
                    m.append(i3);
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        }
        m.append("\n - WallpaperUtils.getCurrentWhich(): ");
        m.append(WallpaperUtils.sCurrentWhich);
        Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + m.toString());
        return i3;
    }

    public final SemWallpaperColors getWallpaperColors(boolean z) {
        int i = 6;
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            if (z || isSubDisplay()) {
                i = 17;
            }
        } else if (isSubDisplay()) {
            i = 18;
        }
        return this.mWallpaperManager.semGetWallpaperColors(i);
    }

    public final void handleAdaptiveColorModeChanged(boolean z) {
        boolean z2 = z ? WallpaperUtils.mIsAdaptiveColorModeSub : WallpaperUtils.mIsAdaptiveColorMode;
        boolean isAdaptiveColorMode = this.mSettingsHelper.isAdaptiveColorMode(z);
        if (z2 != isAdaptiveColorMode) {
            if (!z) {
                Context context = this.mContext;
                if (this.mSettingsHelper.isAdaptiveColorMode()) {
                    context.getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit().putString(SystemUIAnalytics.STID_LOCK_CLOCK_STYLE_COLOR, "Adaptive color (Default)").apply();
                }
            }
            WallpaperEventNotifier wallpaperEventNotifier = this.mWallpaperEventNotifier;
            wallpaperEventNotifier.update(z, 2L, wallpaperEventNotifier.mKeyguardWallpaperColors.getSemWallpaperColors(wallpaperEventNotifier.mSelectedUserInteractor.getSelectedUserId(false), z));
        }
        if (z) {
            WallpaperUtils.mIsAdaptiveColorModeSub = isAdaptiveColorMode;
        } else {
            WallpaperUtils.mIsAdaptiveColorMode = isAdaptiveColorMode;
        }
    }

    public final void handleColorThemeStateChanged(boolean z) {
        boolean isColorThemeEnabled = this.mSettingsHelper.isColorThemeEnabled();
        int adaptiveColorMode = this.mSettingsHelper.getAdaptiveColorMode(z);
        if (isColorThemeEnabled) {
            if ((adaptiveColorMode & 2) == 0) {
                this.mSettingsHelper.setAdaptiveColorMode(z, adaptiveColorMode | 2);
            }
        } else if ((adaptiveColorMode & 2) != 0) {
            this.mSettingsHelper.setAdaptiveColorMode(z, adaptiveColorMode & (-3));
        }
        this.mWallpaperEventNotifier.update(z, 1024L, getWallpaperColors(z));
    }

    public final Bundle notifyEvent(int i) {
        Bundle bundle = null;
        if (((ImageWallpaper.IntegratedEngine.AnonymousClass1) ((HashMap) this.mEventListeners).get(Integer.valueOf(WhichChecker.isFlagEnabled(WallpaperUtils.sCurrentWhich, 16) ? 1 : 0))) != null) {
            bundle = ((ImageWallpaper.IntegratedEngine.AnonymousClass1) ((HashMap) this.mEventListeners).get(Integer.valueOf(WhichChecker.isFlagEnabled(WallpaperUtils.sCurrentWhich, 16) ? 1 : 0))).onEventReceived(i, null);
        }
        Log.d("KeyguardWallpaperController", "notifyEvent: event = " + i + ", result = " + bundle);
        return bundle;
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("include_dls", false);
        bundle2.putInt("which", i2);
        printLognAddHistory("onSemWallpaperChanged: type = " + i + ", which = " + i2);
        final WallpaperChangeNotifier wallpaperChangeNotifier = this.mWallpaperChangeNotifier;
        wallpaperChangeNotifier.getClass();
        Log.d("WallpaperChangeNotifier", "notify: which = " + i2);
        synchronized (wallpaperChangeNotifier.mListeners) {
            for (int i3 = 0; i3 < wallpaperChangeNotifier.mListeners.size(); i3++) {
                try {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(wallpaperChangeNotifier.mListeners.get(i3));
                    wallpaperChangeNotifier.mHandler.post(new WallpaperChangeNotifier$$ExternalSyntheticLambda0());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        wallpaperChangeNotifier.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperChangeNotifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Settings.System.getInt(WallpaperChangeNotifier.this.mContext.getContentResolver(), "dls_state", 0);
            }
        }, 500L);
        if (WhichChecker.isFlagEnabled(i2, 2)) {
            if (!WhichChecker.isFlagEnabled(i2, 8) || (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER && WallpaperUtils.isDexStandAloneMode())) {
                if (i != 3) {
                    this.mWallpaperManager.semSetDLSWallpaperColors(null, i2);
                    if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && this.mWallpaperEventNotifier.mIsThemeApplying && WallpaperUtils.isSubDisplay(i2)) {
                        this.mMainHandler.postDelayed(new KeyguardWallpaperController$$ExternalSyntheticLambda1(this, 1), 1000L);
                        bundle2.putLong("delay", 1000L);
                    } else if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled(WallpaperUtils.isSubDisplay(i2) ? 1 : 0)) {
                        this.mPluginWallpaperManager.onLockWallpaperChanged(WallpaperUtils.isSubDisplay(i2) ? 1 : 0);
                    }
                }
                if (i == -1) {
                    bundle2.putLong("delay", 500L);
                }
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                    int i4 = WallpaperUtils.sCurrentWhich;
                    if ((i4 & 60) == 0) {
                        i4 |= 4;
                    }
                    if (i4 != ((i2 & 60) == 0 ? i2 | 4 : i2)) {
                        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i2, "Ignore wallpaper change for not current which : ", "KeyguardWallpaperController");
                        return;
                    }
                }
                sendUpdateWallpaperMessage(601, bundle2);
            }
        }
    }

    public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
        removeMessages(906);
        Message obtainMessage = obtainMessage(906);
        Bundle bundle = new Bundle();
        bundle.putInt("which", i);
        bundle.putInt("userid", i2);
        obtainMessage.setData(bundle);
        sendMessage(obtainMessage);
    }

    public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
        boolean z;
        if (semWallpaperColors == null) {
            printLognAddHistory("onSemWallpaperColorsChanged: SemWallpaperColors == null");
            return;
        }
        int i3 = i & 2;
        if (i3 == 0) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "set system color which = ", ", opacity = ");
            m.append(semWallpaperColors.getDarkModeDimOpacity());
            Log.i("KeyguardWallpaperController", m.toString());
            SystemWallpaperColors systemWallpaperColors = this.mSystemWallpaperColors;
            systemWallpaperColors.getClass();
            Log.d("SystemWallpaperColors", "setColor: which = " + i);
            if ((i & 1) != 0) {
                int i4 = (i & 60) == 0 ? i | 4 : i;
                Log.i("SystemWallpaperColors", "setColor : put color for which " + i4 + ", color = " + semWallpaperColors);
                systemWallpaperColors.mSystemWallpaperColors.put(i4, semWallpaperColors);
            }
        }
        if (WhichChecker.isFlagEnabled(i, 8) && !WallpaperUtils.isDexStandAloneMode()) {
            printLognAddHistory("onSemWallpaperColorsChanged: DEX.");
            return;
        }
        boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean z3 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z3 && !(z = LsRune.WALLPAPER_SUB_WATCHFACE) && z3 && !z && (i & 3) == 2) {
            WallpaperUtils.sCachedWallpaperColors.put(WallpaperUtils.isSubDisplay(i) ? 16 : 4, semWallpaperColors);
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            if (i3 == 0 && (i & 16) == 0) {
                printLognAddHistory("onSemWallpaperColorsChanged: Not for lockscreen. which = " + i);
                return;
            } else if (i3 != 0 && (i & 16) != 0) {
                printLognAddHistory("onSemWallpaperColorsChanged: Not avaiable on this model. which = " + i);
                return;
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            if (i3 == 0 && (i & 32) == 0) {
                printLognAddHistory("onSemWallpaperColorsChanged: Not for lockscreen. which = " + i);
                return;
            } else if (i3 != 0 && (i & 32) != 0) {
                printLognAddHistory("onSemWallpaperColorsChanged: Not avaiable on this model. which = " + i);
                return;
            }
        } else if (i3 == 0) {
            printLognAddHistory("onSemWallpaperColorsChanged: Not for lockscreen. which = " + i);
            return;
        }
        if (this.mWallpaperEventNotifier.mIsThemeApplying) {
            printLognAddHistory("onSemWallpaperColorsChanged: Theme is currently applying. Send message later.");
            return;
        }
        boolean z4 = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z5 = WallpaperUtils.mIsEmergencyMode;
        if (z4 || z5) {
            printLognAddHistory("onSemWallpaperColorsChanged: We are in UPSM or EM. We don't need this event for now.");
            return;
        }
        StringBuilder m2 = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "onSemWallpaperColorsChanged: which = ", ", userId = ", ", colors = ");
        m2.append(semWallpaperColors.toSimpleString());
        printLognAddHistory(m2.toString());
        removeMessages(608);
        Message obtainMessage = obtainMessage(608);
        Bundle bundle = new Bundle();
        bundle.putParcelable("wallpaper_colors", semWallpaperColors);
        bundle.putInt("which", i);
        bundle.putInt("userid", i2);
        obtainMessage.setData(bundle);
        sendMessage(obtainMessage);
    }

    public final void printLognAddHistory(String str) {
        ((WallpaperLoggerImpl) this.mWallpaperLogger).log("KeyguardWallpaperController", str);
    }

    public final void sendUpdateWallpaperMessage(int i, Bundle bundle) {
        AnonymousClass3 anonymousClass3 = this.mWorkHandler;
        if (anonymousClass3 != null) {
            Message obtainMessage = anonymousClass3.obtainMessage(i);
            if (i != 609 && hasMessages(i)) {
                printLognAddHistory("sendUpdateWallpaperMessage: remove message what = " + i);
                removeMessages(i);
            }
            if (bundle == null) {
                sendEmptyMessage(i);
                return;
            }
            obtainMessage.setData(bundle);
            long j = bundle.getLong("delay", 0L);
            if (j == 0) {
                sendMessage(obtainMessage);
            } else {
                sendMessageDelayed(obtainMessage, j);
            }
        }
    }

    public final void onWallpaperChanged() {
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }
}
