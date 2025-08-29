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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$2;
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

/* loaded from: classes3.dex */
public class KeyguardWallpaperController extends IWallpaperManagerCallback.Stub implements KeyguardWallpaper {
    public static KeyguardWallpaperController sController;
    public final Context mContext;
    public final Map mEventListeners = new HashMap(2);
    public final ExecutorService mExecutor;
    public final AnonymousClass2 mKnoxStateCallback;
    public final Handler mMainHandler;
    public SecNotificationShadeWindowControllerHelperImpl$attach$2 mNoSensorConsumer;
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

    /* renamed from: -$$Nest$mhandleWallpaperMessage, reason: not valid java name */
    public static void m3221$$Nest$mhandleWallpaperMessage(final KeyguardWallpaperController keyguardWallpaperController, Message message) {
        keyguardWallpaperController.getClass();
        int i = message.what;
        if (i == 607) {
            final SemWallpaperColors wallpaperColors = keyguardWallpaperController.getWallpaperColors(false);
            final int i2 = WallpaperUtils.sCurrentWhich;
            keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                    SemWallpaperColors semWallpaperColors = wallpaperColors;
                    int i3 = i2;
                    keyguardWallpaperController2.mWallpaperEventNotifier.update(WhichChecker.isWatchFace(i3) || WhichChecker.isVirtualDisplay(i3), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i3), semWallpaperColors);
                }
            });
            return;
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
                        keyguardWallpaperController.printLognAddHistory(MutableVectorKt$$ExternalSyntheticOutline0.m(i4, i3, "handleWallpaperColorChanged: currentWhich = ", ", which = ", ". Return."));
                        return;
                    }
                } else if (!LsRune.WALLPAPER_SUB_WATCHFACE && (i3 & 16) != 0) {
                    keyguardWallpaperController.printLognAddHistory(MutableVectorKt$$ExternalSyntheticOutline0.m(i4, i3, "handleWallpaperColorChanged: currentWhich = ", ", which = ", ". Return."));
                    return;
                }
            }
            if (parcelable != null) {
                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                        SemWallpaperColors semWallpaperColors = parcelable;
                        int i32 = i3;
                        keyguardWallpaperController2.mWallpaperEventNotifier.update(WhichChecker.isWatchFace(i32) || WhichChecker.isVirtualDisplay(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "onUserSwitchComplete userId = ", " , selectedUserId = ");
            sbM.append(keyguardWallpaperController.mSelectedUserInteractor.getSelectedUserId());
            Log.d("KeyguardWallpaperController", sbM.toString());
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
            boolean zBooleanValue = ((Boolean) message.obj).booleanValue();
            Bundle bundle = new Bundle();
            bundle.putBoolean("visible", zBooleanValue);
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
                int i6 = (WallpaperUtils.sCurrentWhich & 16) == 0 ? 4 : 16;
                int userId = keyguardWallpaperController.mContext.getUserId();
                if (userId >= 150 && userId <= 160) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(userId, "Do not support wallpaper change : userId = ", "KeyguardWallpaperController");
                    break;
                } else {
                    WallpaperUtils.setWallpaperType(lockWallpaperType, keyguardWallpaperController.mContext);
                    keyguardWallpaperController.disableRotateIfNeeded();
                    keyguardWallpaperController.mWallpaperAnalytics.updateWallpaperStatus(i6 | 2);
                    break;
                }
                break;
            case 602:
            case VolteConstants.ErrorCode.DECLINE /* 603 */:
                keyguardWallpaperController.disableRotateIfNeeded();
                Log.d("KeyguardWallpaperController", "colorUpdateForModeChange");
                final SemWallpaperColors wallpaperColors2 = keyguardWallpaperController.getWallpaperColors(false);
                final int i7 = WallpaperUtils.sCurrentWhich;
                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                        SemWallpaperColors semWallpaperColors = wallpaperColors2;
                        int i32 = i7;
                        keyguardWallpaperController2.mWallpaperEventNotifier.update(WhichChecker.isWatchFace(i32) || WhichChecker.isVirtualDisplay(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
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
                                KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                                SemWallpaperColors semWallpaperColors = wallpaperColors3;
                                int i32 = i8;
                                keyguardWallpaperController2.mWallpaperEventNotifier.update(WhichChecker.isWatchFace(i32) || WhichChecker.isVirtualDisplay(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
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
                                KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                                SemWallpaperColors semWallpaperColors = wallpaperColors4;
                                int i32 = i9;
                                keyguardWallpaperController2.mWallpaperEventNotifier.update(WhichChecker.isWatchFace(i32) || WhichChecker.isVirtualDisplay(i32), keyguardWallpaperController2.mWallpaperEventNotifier.mKeyguardWallpaperColors.setSemWallpaperColors(semWallpaperColors, i32), semWallpaperColors);
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
                                keyguardWallpaperController.disableRotateIfNeeded();
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
                                EmergencyButtonController$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage: unsupported command ("), message.what, ")", "KeyguardWallpaperController");
                                break;
                        }
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.knox.KnoxStateMonitorCallback, com.android.systemui.wallpaper.KeyguardWallpaperController$2] */
    /* JADX WARN: Type inference failed for: r6v8, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$3] */
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
        ?? r5 = new KnoxStateMonitorCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.2
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
        this.mKnoxStateCallback = r5;
        this.mContext = context;
        this.mWallpaperManager = wallpaperManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mWallpaperLogger = wallpaperLogger;
        this.mSelectedUserInteractor = selectedUserInteractor;
        Log.d("KeyguardWallpaperController", "KeyguardWallpaperController() selectedUserId = " + selectedUserInteractor.getSelectedUserId());
        handlerThread.start();
        handlerThread.setPriority(10);
        final ?? r6 = new Handler(handlerThread.getLooper()) { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                KeyguardWallpaperController.m3221$$Nest$mhandleWallpaperMessage(KeyguardWallpaperController.this, message);
            }
        };
        this.mWorkHandler = r6;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
        WallpaperUtils.setSettingsHelper(settingsHelper);
        WallpaperUtils.loadDeviceState(context.getUserId(), context);
        this.mWallpaperEventNotifier = wallpaperEventNotifier;
        this.mSystemWallpaperColors = systemWallpaperColors;
        IWallpaperManager iWallpaperManagerAsInterface = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        this.mService = iWallpaperManagerAsInterface;
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
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putInt("version", 1);
            editorEdit.apply();
        }
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("which", 0);
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(intExtra, "onReceive: system wallpaper has been changed. which = ", "KeyguardWallpaperController");
                if (intExtra > 0) {
                    Message messageObtainMessage = obtainMessage(1003);
                    messageObtainMessage.arg1 = intExtra;
                    sendMessage(messageObtainMessage);
                }
            }
        }, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
        this.mOldTransparentType = settingsHelper.getLockscreenWallpaperTransparent();
        if (iWallpaperManagerAsInterface == null) {
            Log.e("KeyguardWallpaperController", "WallpaperManagerService is not ready yet! Just return here!");
            return;
        }
        keyguardWallpaperEventHandler.setEventReceiver(new Consumer() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sendMessage((Message) obj);
            }
        });
        new Thread(new KeyguardWallpaperController$$ExternalSyntheticLambda1(this, 0), "LockWallpaperCB").start();
        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(r5);
        sendUpdateWallpaperMessage(607, null);
        sController = this;
        boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z2) {
            z = false;
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda2
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z3) {
                    KeyguardWallpaperController keyguardWallpaperController = this.f$0;
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
                    keyguardWallpaperController.disableRotateIfNeeded();
                }
            }, 1001, false);
        } else {
            z = false;
        }
        try {
            boolean z3 = iWallpaperManagerAsInterface.semGetWallpaperType(6) == 3 ? true : z;
            if (!z3 && z2 && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                z3 = iWallpaperManagerAsInterface.semGetWallpaperType(18) == 3 ? true : z;
            }
            if (!z3 || MultiPackDispatcher.enableDlsIfDisabled(context)) {
                return;
            }
            Log.e("KeyguardWallpaperController", "Failed to enable DLS.");
        } catch (RemoteException e) {
            Log.e("KeyguardWallpaperController", "System dead?" + e);
        }
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

    public final void disableRotateIfNeeded() {
        this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda1(this, 2));
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
                        boolean z = WallpaperEventNotifier.DEBUG;
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

    /* JADX WARN: Removed duplicated region for block: B:44:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getLockWallpaperType(boolean z) {
        int i = WallpaperUtils.sCurrentWhich;
        int i2 = 8;
        if (WhichChecker.isFlagEnabled(i, 8)) {
            return 0;
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            i = 6;
        }
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("\n - isIncludePluginLock: ", "\n - mService: ", z);
        sbM.append(this.mService);
        IWallpaperManager iWallpaperManager = this.mService;
        int iSemGetWallpaperType = -1;
        if (iWallpaperManager != null) {
            try {
                if (z) {
                    boolean zIsDynamicWallpaperEnabled = this.mPluginWallpaperManager.isDynamicWallpaperEnabled();
                    sbM.append("\n - isDlsWallpaperEnabled: ");
                    sbM.append(zIsDynamicWallpaperEnabled);
                    if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled()) {
                        iSemGetWallpaperType = this.mPluginWallpaperManager.getWallpaperType();
                        sbM.append("\n - type[DLS]: ");
                        sbM.append(iSemGetWallpaperType);
                    } else {
                        iSemGetWallpaperType = this.mService.semGetWallpaperType(i);
                        boolean z2 = true;
                        try {
                            if (this.mSelectedUserInteractor.getSelectedUserId() == 0 && !this.mUpdateMonitor.mUserManager.isUserUnlocked(0)) {
                                if ((this.mSelectedUserInteractor.getSelectedUserId() == 0) && LsRune.KEYGUARD_FBE) {
                                    if (this.mSelectedUserInteractor.getSelectedUserId() != 0 || this.mUpdateMonitor.mUserManager.isUserUnlocked(0)) {
                                        z2 = false;
                                    }
                                    if (z2) {
                                        int screenId = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                                        if (this.mPluginWallpaperManager.isFbeWallpaperAvailable(screenId)) {
                                            if (!this.mPluginWallpaperManager.isFbeWallpaperVideo(screenId)) {
                                                i2 = 0;
                                            }
                                        }
                                        sbM.append("\n - type[from FBE]: ");
                                        sbM.append(i2);
                                    }
                                } else {
                                    i2 = iSemGetWallpaperType;
                                    sbM.append("\n - type[from FBE]: ");
                                    sbM.append(i2);
                                }
                            } else if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled(0) || iSemGetWallpaperType != 3) {
                                sbM.append("\n - type[from WMS]: ");
                                sbM.append(iSemGetWallpaperType);
                            } else {
                                if (this.mSelectedUserInteractor.getSelectedUserId() != 0) {
                                    z2 = false;
                                }
                                if (z2) {
                                    int screenId2 = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                                    if (!this.mPluginWallpaperManager.isFbeWallpaperAvailable(screenId2)) {
                                        i2 = iSemGetWallpaperType;
                                    } else if (!this.mPluginWallpaperManager.isFbeWallpaperVideo(screenId2)) {
                                        i2 = 0;
                                    }
                                    sbM.append("\n - type[force FBE]: ");
                                    sbM.append(i2);
                                }
                            }
                            iSemGetWallpaperType = i2;
                        } catch (RemoteException e) {
                            e = e;
                            iSemGetWallpaperType = i2;
                            Log.e("KeyguardWallpaperController", "System dead?" + e);
                            sbM.append("\n - WallpaperUtils.getCurrentWhich(): ");
                            sbM.append(WallpaperUtils.sCurrentWhich);
                            Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + sbM.toString());
                            return iSemGetWallpaperType;
                        }
                    }
                } else {
                    iSemGetWallpaperType = iWallpaperManager.semGetWallpaperType(i);
                    sbM.append("\n - type[from WMS]: ");
                    sbM.append(iSemGetWallpaperType);
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        }
        sbM.append("\n - WallpaperUtils.getCurrentWhich(): ");
        sbM.append(WallpaperUtils.sCurrentWhich);
        Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + sbM.toString());
        return iSemGetWallpaperType;
    }

    public final SemWallpaperColors getWallpaperColors(boolean z) {
        int i = 6;
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            if (z || (WallpaperUtils.sCurrentWhich & 16) != 0) {
                i = 17;
            }
        } else if ((WallpaperUtils.sCurrentWhich & 16) != 0) {
            i = 18;
        }
        return this.mWallpaperManager.semGetWallpaperColors(i);
    }

    public final void handleAdaptiveColorModeChanged(boolean z) {
        boolean z2 = z ? WallpaperUtils.mIsAdaptiveColorModeSub : WallpaperUtils.mIsAdaptiveColorMode;
        boolean zIsAdaptiveColorMode = this.mSettingsHelper.isAdaptiveColorMode(z);
        if (z2 != zIsAdaptiveColorMode) {
            if (!z) {
                Context context = this.mContext;
                if (this.mSettingsHelper.isAdaptiveColorMode()) {
                    context.getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit().putString(SystemUIAnalytics.STID_LOCK_CLOCK_STYLE_COLOR, "Adaptive color (Default)").apply();
                }
            }
            WallpaperEventNotifier wallpaperEventNotifier = this.mWallpaperEventNotifier;
            wallpaperEventNotifier.update(z, 2L, wallpaperEventNotifier.mKeyguardWallpaperColors.getSemWallpaperColors(wallpaperEventNotifier.mSelectedUserInteractor.getSelectedUserId(), z));
        }
        if (z) {
            WallpaperUtils.mIsAdaptiveColorModeSub = zIsAdaptiveColorMode;
        } else {
            WallpaperUtils.mIsAdaptiveColorMode = zIsAdaptiveColorMode;
        }
    }

    public final void handleColorThemeStateChanged(boolean z) {
        boolean zIsColorThemeEnabled = this.mSettingsHelper.isColorThemeEnabled();
        int adaptiveColorMode = this.mSettingsHelper.getAdaptiveColorMode(z);
        if (zIsColorThemeEnabled) {
            if ((adaptiveColorMode & 2) == 0) {
                this.mSettingsHelper.setAdaptiveColorMode(z, adaptiveColorMode | 2);
            }
        } else if ((adaptiveColorMode & 2) != 0) {
            this.mSettingsHelper.setAdaptiveColorMode(z, adaptiveColorMode & (-3));
        }
        this.mWallpaperEventNotifier.update(z, 1024L, getWallpaperColors(z));
    }

    public final Bundle notifyEvent(int i) {
        Bundle bundleOnEventReceived = null;
        if (((ImageWallpaper.IntegratedEngine.AnonymousClass1) ((HashMap) this.mEventListeners).get(Integer.valueOf(WhichChecker.isFlagEnabled(WallpaperUtils.sCurrentWhich, 16) ? 1 : 0))) != null) {
            bundleOnEventReceived = ((ImageWallpaper.IntegratedEngine.AnonymousClass1) ((HashMap) this.mEventListeners).get(Integer.valueOf(WhichChecker.isFlagEnabled(WallpaperUtils.sCurrentWhich, 16) ? 1 : 0))).onEventReceived(i, null);
        }
        Log.d("KeyguardWallpaperController", "notifyEvent: event = " + i + ", result = " + bundleOnEventReceived);
        return bundleOnEventReceived;
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        int i3 = 1;
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("include_dls", false);
        bundle2.putInt("which", i2);
        printLognAddHistory("onSemWallpaperChanged: type = " + i + ", which = " + i2);
        final WallpaperChangeNotifier wallpaperChangeNotifier = this.mWallpaperChangeNotifier;
        wallpaperChangeNotifier.getClass();
        Log.d("WallpaperChangeNotifier", "notify: which = " + i2);
        synchronized (wallpaperChangeNotifier.mListeners) {
            for (int i4 = 0; i4 < wallpaperChangeNotifier.mListeners.size(); i4++) {
                try {
                    if (wallpaperChangeNotifier.mListeners.get(i4) != null) {
                        throw new ClassCastException();
                    }
                    wallpaperChangeNotifier.mHandler.post(new WallpaperChangeNotifier$$ExternalSyntheticLambda0());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        wallpaperChangeNotifier.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperChangeNotifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Settings.System.getInt(wallpaperChangeNotifier.mContext.getContentResolver(), "dls_state", 0);
            }
        }, 500L);
        if (WhichChecker.isFlagEnabled(i2, 2)) {
            if (WhichChecker.isFlagEnabled(i2, 8)) {
                if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER) {
                    boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                    return;
                }
                return;
            }
            if (i != 3) {
                this.mWallpaperManager.semSetDLSWallpaperColors(null, i2);
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && this.mWallpaperEventNotifier.mIsThemeApplying && WallpaperUtils.isSubDisplay(i2)) {
                    this.mMainHandler.postDelayed(new KeyguardWallpaperController$$ExternalSyntheticLambda1(this, i3), 1000L);
                    bundle2.putLong("delay", 1000L);
                } else if (this.mPluginWallpaperManager.isDynamicWallpaperEnabled(WallpaperUtils.isSubDisplay(i2) ? 1 : 0)) {
                    this.mPluginWallpaperManager.onLockWallpaperChanged(WallpaperUtils.isSubDisplay(i2) ? 1 : 0);
                }
            }
            if (i == -1) {
                bundle2.putLong("delay", 500L);
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                int i5 = WallpaperUtils.sCurrentWhich;
                if ((i5 & 60) == 0) {
                    i5 |= 4;
                }
                if (i5 != ((i2 & 60) == 0 ? i2 | 4 : i2)) {
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Ignore wallpaper change for not current which : ", "KeyguardWallpaperController");
                    return;
                }
            }
            sendUpdateWallpaperMessage(601, bundle2);
        }
    }

    public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
        removeMessages(906);
        Message messageObtainMessage = obtainMessage(906);
        Bundle bundle = new Bundle();
        bundle.putInt("which", i);
        bundle.putInt("userid", i2);
        messageObtainMessage.setData(bundle);
        sendMessage(messageObtainMessage);
    }

    public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
        boolean z;
        if (semWallpaperColors == null) {
            printLognAddHistory("onSemWallpaperColorsChanged: SemWallpaperColors == null");
            return;
        }
        int i3 = i & 2;
        if (i3 == 0) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "set system color which = ", ", opacity = ");
            sbM.append(semWallpaperColors.getDarkModeDimOpacity());
            Log.i("KeyguardWallpaperController", sbM.toString());
            SystemWallpaperColors systemWallpaperColors = this.mSystemWallpaperColors;
            systemWallpaperColors.getClass();
            Log.d("SystemWallpaperColors", "setColor: which = " + i);
            if ((i & 1) != 0) {
                int i4 = (i & 60) == 0 ? i | 4 : i;
                Log.i("SystemWallpaperColors", "setColor : put color for which " + i4 + ", color = " + semWallpaperColors);
                systemWallpaperColors.mSystemWallpaperColors.put(i4, semWallpaperColors);
            }
        }
        if (WhichChecker.isFlagEnabled(i, 8)) {
            boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
            printLognAddHistory("onSemWallpaperColorsChanged: DEX.");
            return;
        }
        boolean z3 = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean z4 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z4 && !(z = LsRune.WALLPAPER_SUB_WATCHFACE) && z4 && !z && (i & 3) == 2) {
            WallpaperUtils.sCachedWallpaperColors.put(WallpaperUtils.isSubDisplay(i) ? 16 : 4, semWallpaperColors);
        }
        boolean z5 = LsRune.WALLPAPER_SUB_WATCHFACE;
        if (z5) {
            if (i3 == 0 && (i & 16) == 0) {
                printLognAddHistory("onSemWallpaperColorsChanged: Not for lockscreen. which = " + i);
                return;
            } else if (i3 != 0 && (16 & i) != 0) {
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
        boolean z6 = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z7 = WallpaperUtils.mIsEmergencyMode;
        if (z6 || z7) {
            printLognAddHistory("onSemWallpaperColorsChanged: We are in UPSM or EM. We don't need this event for now.");
            return;
        }
        StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "onSemWallpaperColorsChanged: which = ", ", userId = ", ", colors = ");
        sbM2.append(semWallpaperColors.toSimpleString());
        printLognAddHistory(sbM2.toString());
        removeMessages(608);
        Message messageObtainMessage = obtainMessage(608);
        Bundle bundle = new Bundle();
        bundle.putParcelable("wallpaper_colors", semWallpaperColors);
        bundle.putInt("which", i);
        bundle.putInt("userid", i2);
        messageObtainMessage.setData(bundle);
        if (!z4 || z5) {
            sendMessage(messageObtainMessage);
        } else {
            sendMessageDelayed(messageObtainMessage, 10L);
        }
    }

    public final void printLognAddHistory(String str) {
        ((WallpaperLoggerImpl) this.mWallpaperLogger).log("KeyguardWallpaperController", str);
    }

    public final void sendUpdateWallpaperMessage(int i, Bundle bundle) {
        AnonymousClass3 anonymousClass3 = this.mWorkHandler;
        if (anonymousClass3 != null) {
            Message messageObtainMessage = anonymousClass3.obtainMessage(i);
            if (i != 609 && hasMessages(i)) {
                printLognAddHistory("sendUpdateWallpaperMessage: remove message what = " + i);
                removeMessages(i);
            }
            if (bundle == null) {
                sendEmptyMessage(i);
                return;
            }
            messageObtainMessage.setData(bundle);
            long j = bundle.getLong("delay", 0L);
            if (j == 0) {
                sendMessage(messageObtainMessage);
            } else {
                sendMessageDelayed(messageObtainMessage, j);
            }
        }
    }

    public final void onWallpaperChanged() {
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }
}
