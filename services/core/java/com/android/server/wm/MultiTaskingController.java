package com.android.server.wm;

import android.R;
import android.app.PendingIntent;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import android.view.InsetsSource;
import android.view.InsetsState;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.DexController;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.WindowContainer;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SmartPopupViewUtil;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.remoteappmode.RemoteAppModeManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiTaskingController implements IController {
    public final ActivityEmbeddedPackageRepository mActivityEmbeddedPackageRepository;
    public final ActivityTaskManagerService mAtm;
    public boolean mDeferFocusChanging;
    public final AnonymousClass1 mDemoResetStartedReceiver;
    public H mH;
    public boolean mIsFullToSplitEnabled;
    public boolean mIsGestureTypeSideAndBottom;
    public boolean mIsMinimalBatteryUse;
    public boolean mIsNavigationModeGesture;
    public int mLastRotation;
    public final AnonymousClass1 mMinimizeAllReceiver;
    public MultiTaskingController$$ExternalSyntheticLambda4 mMoveToBackTaskWithIme;
    public SettingsObserver mSettingsObserver;
    public int mSwipeGestureThreshold;
    public ActivityRecord mTmpPipCandidate;
    public WindowManagerService mWm;
    public int mSystemUIUid = -1;
    public final Configuration mLastConfig = new Configuration();
    public int mSplitFeasibleMode = 2;
    public boolean mIsDisplaySizeOverride = false;
    public final RemoteCallbackList mRemoteAppTransitionListeners = new RemoteCallbackList();
    public final MultiTaskingController$$ExternalSyntheticLambda5 mNotifyStartRecentsAnimation = new MultiTaskingController$$ExternalSyntheticLambda5(0);
    public final MultiTaskingController$$ExternalSyntheticLambda5 mNotifyFinishRecentsAnimation = new MultiTaskingController$$ExternalSyntheticLambda5(1);
    public final MultiTaskingController$$ExternalSyntheticLambda5 mNotifyStartHomeAnimation = new MultiTaskingController$$ExternalSyntheticLambda5(2);
    public final MultiTaskingController$$ExternalSyntheticLambda5 mNotifyWallpaperVisibilityChanged = new MultiTaskingController$$ExternalSyntheticLambda5(3);
    public boolean mDeferEnsureConfig = false;
    public final Rect mTmpRect = new Rect();
    public Task mAffordanceTargetTask = null;
    public final Queue mFocusableTaskIds = new LinkedList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                int i2 = message.arg1;
                int i3 = message.arg2;
                Rect rect = (Rect) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock = MultiTaskingController.this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        MultiTaskingController.this.mAtm.mFreeformController.setFreeformWindowingModeByCornerGestureLocked(i2, rect, i3);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                if (CoreRune.MW_FREEFORM_CORNER_GESTURE_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("2004", "From Gesture");
                    return;
                }
                return;
            }
            if (i == 1) {
                RemoteAppModeManagerInternal remoteAppModeManagerInternal = (RemoteAppModeManagerInternal) LocalServices.getService(RemoteAppModeManagerInternal.class);
                if (remoteAppModeManagerInternal == null) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage: can't find localService, what="), message.what, "MultiTaskingController");
                    return;
                }
                int i4 = message.arg1;
                Object obj = message.obj;
                String str = obj != null ? (String) obj : "";
                if (message.arg2 == 1) {
                    remoteAppModeManagerInternal.onSecuredAppLaunched(i4, str);
                    return;
                } else {
                    remoteAppModeManagerInternal.onSecuredAppRemoved(i4, str);
                    return;
                }
            }
            if (i == 3) {
                MultiTaskingController multiTaskingController = MultiTaskingController.this;
                MultiTaskingController.m1065$$Nest$mforAllRemoteAppTransitionListeners(multiTaskingController, multiTaskingController.mNotifyStartRecentsAnimation, message);
                return;
            }
            if (i == 4) {
                MultiTaskingController multiTaskingController2 = MultiTaskingController.this;
                MultiTaskingController.m1065$$Nest$mforAllRemoteAppTransitionListeners(multiTaskingController2, multiTaskingController2.mNotifyFinishRecentsAnimation, message);
            } else if (i == 5) {
                MultiTaskingController multiTaskingController3 = MultiTaskingController.this;
                MultiTaskingController.m1065$$Nest$mforAllRemoteAppTransitionListeners(multiTaskingController3, multiTaskingController3.mNotifyStartHomeAnimation, message);
            } else {
                if (i != 6) {
                    return;
                }
                MultiTaskingController multiTaskingController4 = MultiTaskingController.this;
                MultiTaskingController.m1065$$Nest$mforAllRemoteAppTransitionListeners(multiTaskingController4, multiTaskingController4.mNotifyWallpaperVisibilityChanged, message);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mDexFontScaleUri;
        public final Uri mDexForceImmersiveModeEnabledUri;
        public final Uri mDexForceImmersiveModeSettingUri;
        public final Uri mDexStandaloneRotationUri;
        public final Uri mDexStarShowingDelayTimeUri;
        public final Uri mDexTouchPadUsingUri;
        public final Uri mEdgeUri;
        public final Uri mFreeformCaptionTypeUri;
        public final Uri mMinimalBatteryUseUri;
        public final Uri mNavigationBarGestureWhileHiddenUri;
        public final Uri mNavigationBarGesturesDetailTypeUri;
        public final Uri mNotificationBubbleUri;
        public final Uri mSmartPopupViewPackageListUri;
        public final Uri mSplitGestureUri;
        public final Uri mSwipeForPopUpViewCornerAreaUri;
        public final ArrayList mUriList;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mUriList = new ArrayList();
            this.mNavigationBarGestureWhileHiddenUri = Settings.Global.getUriFor("navigation_bar_gesture_while_hidden");
            this.mNavigationBarGesturesDetailTypeUri = Settings.Global.getUriFor("navigation_bar_gesture_detail_type");
            Uri uri = DesktopModeSettings.CONTENT_URI;
            this.mDexStandaloneRotationUri = Uri.withAppendedPath(uri, "standalone_mode_rotate_app");
            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                this.mSmartPopupViewPackageListUri = Settings.Secure.getUriFor("floating_noti_package_list");
                this.mNotificationBubbleUri = Settings.Secure.getUriFor("notification_bubbles");
            }
            this.mSwipeForPopUpViewCornerAreaUri = Settings.Global.getUriFor("freeform_corner_gesture_level");
            this.mMinimalBatteryUseUri = Settings.System.getUriFor("minimal_battery_use");
            if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
                this.mFreeformCaptionTypeUri = Settings.Global.getUriFor("freeform_caption_type");
            }
            this.mSplitGestureUri = Settings.Global.getUriFor("open_in_split_screen_view");
            this.mEdgeUri = Settings.Secure.getUriFor("edge_enable");
            this.mDexForceImmersiveModeSettingUri = Uri.withAppendedPath(uri, "taskbar_hide_bar");
            this.mDexForceImmersiveModeEnabledUri = Uri.withAppendedPath(uri, "taskbar_hide_bar_enabled");
            this.mDexTouchPadUsingUri = Uri.withAppendedPath(uri, "touchpad_enabled");
            this.mDexFontScaleUri = Uri.withAppendedPath(uri, "font_scale");
            this.mDexStarShowingDelayTimeUri = Uri.withAppendedPath(uri, "mouse_immersive_time_control");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                Slog.w("MultiTaskingController", "SettingsObserver_onChange: uri is null");
            } else {
                readSettings(false, uri);
            }
        }

        public final void readSettings(boolean z, Uri uri) {
            ContentResolver contentResolver = MultiTaskingController.this.mAtm.mContext.getContentResolver();
            if (z || this.mNavigationBarGestureWhileHiddenUri.equals(uri)) {
                MultiTaskingController.this.mIsNavigationModeGesture = Settings.Global.getInt(contentResolver, "navigation_bar_gesture_while_hidden", 0) == 1;
            }
            if (z || this.mNavigationBarGesturesDetailTypeUri.equals(uri)) {
                MultiTaskingController.this.mIsGestureTypeSideAndBottom = Settings.Global.getInt(contentResolver, "navigation_bar_gesture_detail_type", 1) == 1;
            }
            if (z || this.mDexStandaloneRotationUri.equals(uri)) {
                final boolean settingsAsUser = DesktopModeSettings.getSettingsAsUser(contentResolver, "standalone_mode_rotate_app", false, DesktopModeSettings.sCurrentUserId);
                final DexController dexController = MultiTaskingController.this.mAtm.mDexController;
                dexController.mAtm.mH.post(new Runnable() { // from class: com.android.server.wm.DexController$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        DexController dexController2 = DexController.this;
                        boolean z2 = settingsAsUser;
                        WindowManagerGlobalLock windowManagerGlobalLock = dexController2.mAtm.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                dexController2.mDexStandaloneRotationEnabled = z2;
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
            }
            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW && MultiTaskingController.this.mAtm.mAmInternal.isBooted() && (z || this.mSmartPopupViewPackageListUri.equals(uri) || this.mNotificationBubbleUri.equals(uri))) {
                FreeformController freeformController = MultiTaskingController.this.mAtm.mFreeformController;
                Message obtainMessage = freeformController.mH.obtainMessage(104);
                obtainMessage.obj = "setting_changed";
                freeformController.mH.sendMessage(obtainMessage);
                FreeformController freeformController2 = MultiTaskingController.this.mAtm.mFreeformController;
                Message obtainMessage2 = freeformController2.mH.obtainMessage(103);
                obtainMessage2.obj = "setting_changed";
                freeformController2.mH.sendMessage(obtainMessage2);
            }
            if (z || this.mSwipeForPopUpViewCornerAreaUri.equals(uri)) {
                MultiTaskingController multiTaskingController = MultiTaskingController.this;
                int cornerGestureCustomValue = MultiWindowEdgeDetector.getCornerGestureCustomValue(Settings.Global.getInt(contentResolver, "freeform_corner_gesture_level", 2));
                WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingController.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        Iterator it = multiTaskingController.mAtm.mExt.getStartedUserIdsLocked().iterator();
                        while (it.hasNext()) {
                            multiTaskingController.mAtm.mExt.mCoreStateController.setVolatileState("corner_gesture_custom_value", Integer.valueOf(cornerGestureCustomValue), ((Integer) it.next()).intValue(), true, true, null);
                            MultiWindowEdgeDetector.updateCustomBoundsIfNeeded();
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            if (z || this.mMinimalBatteryUseUri.equals(uri)) {
                int i = Settings.System.getInt(contentResolver, "minimal_battery_use", 0);
                WindowManagerGlobalLock windowManagerGlobalLock2 = MultiTaskingController.this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        MultiTaskingController.this.mIsMinimalBatteryUse = i == 1;
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && (z || this.mFreeformCaptionTypeUri.equals(uri))) {
                MultiTaskingController.this.mAtm.mFreeformController.updateFreeformCaptionType(Settings.Global.getInt(contentResolver, "freeform_caption_type", 0));
            }
            if (z || this.mSplitGestureUri.equals(uri)) {
                MultiTaskingController.this.mIsFullToSplitEnabled = Settings.Global.getInt(contentResolver, "open_in_split_screen_view", 0) == 1;
            }
            if (z || this.mEdgeUri.equals(uri)) {
                boolean z2 = Settings.Secure.getIntForUser(contentResolver, "edge_enable", 0, -2) == 1;
                WindowManagerGlobalLock windowManagerGlobalLock3 = MultiTaskingController.this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        DisplayContent displayContent = MultiTaskingController.this.mAtm.mRootWindowContainer.mDefaultDisplay;
                        if (displayContent != null) {
                            displayContent.mDisplayPolicy.mEdgeEnabled = z2;
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            if (z || this.mDexForceImmersiveModeSettingUri.equals(uri)) {
                ActivityTaskManagerService activityTaskManagerService = MultiTaskingController.this.mAtm;
                activityTaskManagerService.mDexController.updateForceImmersiveModeSetting(DesktopModeSettings.getSettingsAsUser(activityTaskManagerService.mContext.getContentResolver(), "taskbar_hide_bar", false, DesktopModeSettings.sCurrentUserId));
            }
            if (z || this.mDexForceImmersiveModeEnabledUri.equals(uri)) {
                ActivityTaskManagerService activityTaskManagerService2 = MultiTaskingController.this.mAtm;
                activityTaskManagerService2.mDexController.updateForceImmersiveModeState(DesktopModeSettings.getSettingsAsUser(activityTaskManagerService2.mContext.getContentResolver(), "taskbar_hide_bar_enabled", false, DesktopModeSettings.sCurrentUserId));
            }
            if (z || this.mDexTouchPadUsingUri.equals(uri)) {
                boolean settingsAsUser2 = DesktopModeSettings.getSettingsAsUser(contentResolver, "touchpad_enabled", false, DesktopModeSettings.sCurrentUserId);
                WindowManagerGlobalLock windowManagerGlobalLock4 = MultiTaskingController.this.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock4) {
                    try {
                        DexController dexController2 = MultiTaskingController.this.mAtm.mDexController;
                        if (dexController2.mDexTouchPadEnabled != settingsAsUser2) {
                            dexController2.mDexTouchPadEnabled = settingsAsUser2;
                            Slog.i("DexController", "setDexTouchPadEnabledLocked: enabled=" + dexController2.mDexTouchPadEnabled);
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            if (z || this.mDexFontScaleUri.equals(uri)) {
                float parseFloat = Float.parseFloat(DesktopModeSettings.getSettingsAsUser(contentResolver, "font_scale", Float.toString(1.0f), DesktopModeSettings.sCurrentUserId));
                MultiTaskingController.this.mAtm.mDexController.updateDexFontScaleIfNeeded(parseFloat);
                Slog.d("MultiTaskingController", "SettingsObserver_readSettings: dex_font_scale=" + parseFloat);
            }
            if (z || this.mDexStarShowingDelayTimeUri.equals(uri)) {
                ActivityTaskManagerService activityTaskManagerService3 = MultiTaskingController.this.mAtm;
                activityTaskManagerService3.mDexController.updateDexStarShowingDelayTime(DesktopModeSettings.getSettingsAsUser(activityTaskManagerService3.mContext.getContentResolver(), "mouse_immersive_time_control", -1, DesktopModeSettings.sCurrentUserId));
            }
        }

        public final String toString() {
            return "SettingsObserver{mUriList=" + this.mUriList + "}";
        }
    }

    /* renamed from: -$$Nest$mforAllRemoteAppTransitionListeners, reason: not valid java name */
    public static void m1065$$Nest$mforAllRemoteAppTransitionListeners(MultiTaskingController multiTaskingController, MultiTaskingController$$ExternalSyntheticLambda5 multiTaskingController$$ExternalSyntheticLambda5, Message message) {
        synchronized (multiTaskingController.mRemoteAppTransitionListeners) {
            for (int beginBroadcast = multiTaskingController.mRemoteAppTransitionListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    multiTaskingController$$ExternalSyntheticLambda5.accept((IRemoteAppTransitionListener) multiTaskingController.mRemoteAppTransitionListeners.getBroadcastItem(beginBroadcast), message);
                } catch (RemoteException unused) {
                }
            }
            multiTaskingController.mRemoteAppTransitionListeners.finishBroadcast();
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.wm.MultiTaskingController$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.wm.MultiTaskingController$1] */
    public MultiTaskingController(ActivityTaskManagerService activityTaskManagerService) {
        final int i = 0;
        this.mMinimizeAllReceiver = new BroadcastReceiver(this) { // from class: com.android.server.wm.MultiTaskingController.1
            public final /* synthetic */ MultiTaskingController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        Bundle extras = intent.getExtras();
                        int i2 = extras != null ? extras.getInt("displayId", 0) : 0;
                        if ("com.samsung.android.multiwindow.MINIMIZE_ALL".equals(action) || "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM".equals(action)) {
                            WindowManagerGlobalLock windowManagerGlobalLock = this.this$0.mAtm.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    this.this$0.minimizeAllTasksLocked(i2, true);
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                    default:
                        String action2 = intent.getAction();
                        if (!"com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action2)) {
                            if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action2)) {
                                Slog.d("MultiTaskingController", "settings reset");
                                SmartPopupViewUtil.resetPackageListStr(this.this$0.mAtm.mContext);
                                return;
                            }
                            return;
                        }
                        if (SmartPopupViewUtil.isShopDemo(this.this$0.mAtm.mContext)) {
                            Slog.d("MultiTaskingController", "demo reset");
                            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                                SmartPopupViewUtil.resetPackageListStr(this.this$0.mAtm.mContext);
                            }
                            MultiWindowManager.getInstance().setCornerGestureEnabledWithSettings(false);
                            return;
                        }
                        return;
                }
            }
        };
        final int i2 = 1;
        this.mDemoResetStartedReceiver = new BroadcastReceiver(this) { // from class: com.android.server.wm.MultiTaskingController.1
            public final /* synthetic */ MultiTaskingController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                switch (i2) {
                    case 0:
                        String action = intent.getAction();
                        Bundle extras = intent.getExtras();
                        int i22 = extras != null ? extras.getInt("displayId", 0) : 0;
                        if ("com.samsung.android.multiwindow.MINIMIZE_ALL".equals(action) || "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM".equals(action)) {
                            WindowManagerGlobalLock windowManagerGlobalLock = this.this$0.mAtm.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    this.this$0.minimizeAllTasksLocked(i22, true);
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                    default:
                        String action2 = intent.getAction();
                        if (!"com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action2)) {
                            if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action2)) {
                                Slog.d("MultiTaskingController", "settings reset");
                                SmartPopupViewUtil.resetPackageListStr(this.this$0.mAtm.mContext);
                                return;
                            }
                            return;
                        }
                        if (SmartPopupViewUtil.isShopDemo(this.this$0.mAtm.mContext)) {
                            Slog.d("MultiTaskingController", "demo reset");
                            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                                SmartPopupViewUtil.resetPackageListStr(this.this$0.mAtm.mContext);
                            }
                            MultiWindowManager.getInstance().setCornerGestureEnabledWithSettings(false);
                            return;
                        }
                        return;
                }
            }
        };
        this.mAtm = activityTaskManagerService;
        this.mActivityEmbeddedPackageRepository = new ActivityEmbeddedPackageRepository(activityTaskManagerService);
    }

    public static boolean isInImmersiveSplitScreenMode() {
        return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED || MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
    }

    public static boolean isInSystemBarArea(int i, int i2, int i3, DisplayContent displayContent) {
        InsetsState insetsState = displayContent.mInsetsStateController.mState;
        int sourceSize = insetsState.sourceSize();
        for (int i4 = 0; i4 < sourceSize; i4++) {
            InsetsSource sourceAt = insetsState.sourceAt(i4);
            if (sourceAt.getType() == i && !sourceAt.getFrame().isEmpty()) {
                return sourceAt.getFrame().contains(i2, i3);
            }
        }
        return false;
    }

    public static void printAllTasksLocked(PrintWriter printWriter, String str, WindowContainer windowContainer, int i) {
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            return;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(asTask.isRootTask() ? "Root #" : "Leaf #");
        m.append(i);
        m.append(" ");
        m.append(asTask);
        printWriter.println(m.toString());
        for (int size = asTask.mChildren.size() - 1; size >= 0; size--) {
            printAllTasksLocked(printWriter, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  "), (WindowContainer) asTask.mChildren.get(size), size);
        }
    }

    public static void printTaskDisplayAreaLocked(PrintWriter printWriter, String str, WindowContainer windowContainer) {
        for (int childCount = windowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "TaskDisplayArea: ");
            m.append(windowContainer.getName());
            printWriter.print(m.toString());
            WindowContainer childAt = windowContainer.getChildAt(childCount);
            if (childAt.asTaskDisplayArea() != null) {
                printTaskDisplayAreaLocked(printWriter, str + "  ", windowContainer);
            } else {
                printAllTasksLocked(printWriter, str + "  ", childAt, childCount);
            }
        }
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[MultiTaskingController]");
        if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && this.mAffordanceTargetTask != null) {
            printWriter.println("  mAffordanceTargetTask=" + this.mAffordanceTargetTask);
        }
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mSplitFeasibleMode="), this.mSplitFeasibleMode, printWriter, "  mIsDisplaySizeOverride="), this.mIsDisplaySizeOverride, printWriter);
        }
        printWriter.println("  (CONFIGURATION CONTAINERS)");
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.mRootWindowContainer.dumpConfigurationLocked(printWriter, "  ", 0);
        printWriter.println();
        printWriter.println("  (TASK ORDER INFO)");
        int childCount = activityTaskManagerService.mRootWindowContainer.getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                break;
            }
            DisplayContent displayContent = (DisplayContent) activityTaskManagerService.mRootWindowContainer.getChildAt(childCount);
            printWriter.println("    DisplayContent #" + displayContent.mDisplayId);
            displayContent.forAllTaskDisplayAreas(new MultiTaskingController$$ExternalSyntheticLambda10(this, printWriter));
        }
        printWriter.println();
        printWriter.println("  (FOCUS INFO)");
        RootWindowContainer rootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        printWriter.println("    TopDisplayFocusedTask=" + rootWindowContainer.getTopDisplayFocusedRootTask());
        printWriter.println("    TopResumedActivity=" + rootWindowContainer.getTopResumedActivity());
        printWriter.println();
        for (int childCount2 = rootWindowContainer.getChildCount() + (-1); childCount2 >= 0; childCount2 += -1) {
            DisplayContent displayContent2 = (DisplayContent) rootWindowContainer.getChildAt(childCount2);
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("    DisplayContent #"), displayContent2.mDisplayId, printWriter, "      FocusedTask=");
            m.append(displayContent2.getFocusedRootTask());
            printWriter.println(m.toString());
            printWriter.println("      Preferred TopFocusableTask=" + displayContent2.getDefaultTaskDisplayArea().mPreferredTopFocusableRootTask);
            printWriter.println("      mFocusedApp=" + displayContent2.mFocusedApp);
            printWriter.println("      mCurrentFocus=" + displayContent2.mCurrentFocus);
            printWriter.println("      mInputMethodWindow=" + displayContent2.mInputMethodWindow);
            printWriter.println("      mImeLayeringTarget=" + displayContent2.getImeTarget(0));
            printWriter.println("      mImeInputTarget=" + displayContent2.mImeInputTarget);
            printWriter.println("      mImeControlTarget=" + displayContent2.getImeTarget(2));
            printWriter.println("      " + displayContent2.mInsetsStateController.getImeSourceProvider().mSource);
        }
        if (!rootWindowContainer.mTopFocusedAppByProcess.isEmpty()) {
            printWriter.println();
            printWriter.println("    mTopFocusedAppByProcess");
            for (Map.Entry entry : rootWindowContainer.mTopFocusedAppByProcess.entrySet()) {
                printWriter.println("      [" + entry.getKey() + "] r=" + entry.getValue());
            }
        }
        printWriter.println();
        TransitionController transitionController = activityTaskManagerService.mWindowOrganizerController.mTransitionController;
        if (transitionController != null) {
            printWriter.println("  (TRANSITION INFO)");
            if (transitionController.mCollectingTransition != null) {
                printWriter.println("    mCollectingTransition=" + transitionController.mCollectingTransition);
            }
            if (!transitionController.mWaitingTransitions.isEmpty()) {
                printWriter.println("    mWaitingTransitions=" + transitionController.mWaitingTransitions);
            }
            if (transitionController.mFinishingTransition != null) {
                printWriter.println("    mFinishingTransition=" + transitionController.mFinishingTransition);
            }
        }
        printWriter.println();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean exitMultiWindow(IBinder iBinder) {
        boolean z;
        Task task;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord activityRecord = null;
                if (iBinder instanceof ActivityRecord.Token) {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    z = false;
                    activityRecord = isInRootTaskLocked;
                    task = isInRootTaskLocked != null ? isInRootTaskLocked.task : null;
                } else if ((iBinder instanceof WindowContainer.RemoteToken) && (((WindowContainer) ((WindowContainer.RemoteToken) iBinder).mWeakRef.get()) instanceof Task)) {
                    task = ((WindowContainer) ((WindowContainer.RemoteToken) iBinder).mWeakRef.get()).asTask();
                    activityRecord = task != null ? task.topRunningActivity(false) : null;
                    z = true;
                } else {
                    z = false;
                    task = null;
                }
                if (activityRecord != null && task != null && task.getDisplayArea() != null) {
                    int windowingMode = task.getWindowingMode();
                    IBinder iBinder2 = iBinder;
                    if (z) {
                        iBinder2 = activityRecord.token;
                    }
                    if (WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration()) && task.getDisplayArea().isSplitScreenModeActivated()) {
                        TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                        taskOrganizerInfo.setExitSplitScreenTopTaskId(task.mTaskId);
                        taskOrganizerInfo.setExitSplitScreenStageType(task.getWindowConfiguration().getStageType());
                        this.mAtm.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
                    } else if (5 == windowingMode) {
                        this.mAtm.mActivityClientController.toggleFreeformWindowingMode(iBinder2);
                    } else if (1 != windowingMode) {
                        Slog.w("MultiTaskingController", "exitMultiWindow: invalid windowing mode to request mode=" + activityRecord.getWindowingMode() + ", r=" + activityRecord);
                    } else if (task.isDexMode()) {
                        this.mAtm.mActivityClientController.toggleFreeformWindowingMode(iBinder2);
                    }
                    boolean z2 = activityRecord.getWindowingMode() == 1;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return z2;
                }
                Slog.w("MultiTaskingController", "exitMultiWindow: cannot find task, token=" + iBinder);
                WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean getEmbedActivityPackageEnabled(String str, int i) {
        return this.mAtm.mExt.mActivityEmbeddedController.getEnabled(i, str) != 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a0, code lost:
    
        if (r8 == 32) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c9, code lost:
    
        if (r3 > r13) goto L52;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getMultiSplitFlags() {
        /*
            Method dump skipped, instructions count: 217
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.MultiTaskingController.getMultiSplitFlags():int");
    }

    public final ParceledListSlice getTaskInfoFromPackageName(String str) {
        ParceledListSlice parceledListSlice;
        if (str == null) {
            return null;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int childCount = this.mAtm.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                    ((DisplayContent) this.mAtm.mRootWindowContainer.getChildAt(childCount)).forAllActivities(new MultiTaskingController$$ExternalSyntheticLambda10(str, arrayList2, arrayList));
                }
                parceledListSlice = new ParceledListSlice(arrayList);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return parceledListSlice;
    }

    public final boolean handleAltTabKeyIfNeededLocked() {
        Task topMostTask;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        TaskDisplayArea defaultTaskDisplayArea = activityTaskManagerService.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
        ActivityRecord focusedActivity = defaultTaskDisplayArea.getFocusedActivity();
        if (focusedActivity == null || !focusedActivity.inSplitScreenWindowingMode() || !defaultTaskDisplayArea.isSplitScreenModeActivated()) {
            ((LinkedList) this.mFocusableTaskIds).clear();
            return false;
        }
        if (this.mFocusableTaskIds.isEmpty()) {
            int stageType = focusedActivity.getStageType();
            Task rootTask = defaultTaskDisplayArea.getRootTask(new TaskDisplayArea$$ExternalSyntheticLambda7(2));
            if (rootTask != null) {
                for (int childCount = rootTask.getChildCount() - 1; childCount >= 0; childCount--) {
                    WindowContainer childAt = rootTask.getChildAt(childCount);
                    if (childAt.asTask() != null && childAt.inSplitScreenWindowingMode()) {
                        Task asTask = childAt.asTask();
                        if (asTask.getStageType() != stageType && asTask.isVisible() && (topMostTask = asTask.getTopMostTask()) != null) {
                            ((LinkedList) this.mFocusableTaskIds).add(Integer.valueOf(topMostTask.mTaskId));
                        }
                    }
                }
            }
            if (this.mFocusableTaskIds.isEmpty()) {
                return false;
            }
            ((LinkedList) this.mFocusableTaskIds).add(-1);
            Slog.d("MultiTaskingController", "collectFocusableSplitScreenTasksLocked:" + this.mFocusableTaskIds);
        }
        int intValue = ((Integer) ((LinkedList) this.mFocusableTaskIds).poll()).intValue();
        if (intValue == -1) {
            return false;
        }
        activityTaskManagerService.setFocusedTask(intValue);
        return true;
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
        this.mSettingsObserver = new SettingsObserver(this.mH);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean interceptStartActivityFromRecentsLocked(com.android.server.wm.Task r22, android.app.ActivityOptions r23) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.MultiTaskingController.interceptStartActivityFromRecentsLocked(com.android.server.wm.Task, android.app.ActivityOptions):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0140 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean interceptStartActivityLocked(com.android.server.wm.Task r18, com.android.server.wm.ActivityRecord r19, com.android.server.wm.ActivityRecord r20, int r21, int r22, android.app.ActivityOptions r23, com.android.server.uri.NeededUriGrants r24, com.android.server.wm.Task r25, com.android.server.wm.ActivityStarter.Request r26) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.MultiTaskingController.interceptStartActivityLocked(com.android.server.wm.Task, com.android.server.wm.ActivityRecord, com.android.server.wm.ActivityRecord, int, int, android.app.ActivityOptions, com.android.server.uri.NeededUriGrants, com.android.server.wm.Task, com.android.server.wm.ActivityStarter$Request):boolean");
    }

    public final boolean isVisibleTaskByTaskIdInDexDisplay(int i) {
        Task anyTaskForId;
        int i2;
        String str;
        if (i != -1) {
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            if (activityTaskManagerService.mDexController.getDexModeLocked() == 2 && (anyTaskForId = activityTaskManagerService.mRootWindowContainer.anyTaskForId(i, 1, null, false)) != null && anyTaskForId.getDisplayId() == 2) {
                ActivityRecord rootActivity = anyTaskForId.getRootActivity(true, false);
                if (rootActivity == null) {
                    WindowProcessController windowProcessController = anyTaskForId.mRootProcess;
                    if (windowProcessController == null) {
                        return false;
                    }
                    String str2 = windowProcessController.mName;
                    i2 = windowProcessController.mUid;
                    str = str2;
                } else {
                    if (anyTaskForId.isVisible()) {
                        return true;
                    }
                    str = rootActivity.processName;
                    i2 = rootActivity.getUid();
                }
                if (!activityTaskManagerService.mDexController.getTaskLocked(i2, 0, str, true).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) {
        ActivityInfo activityInfo;
        if (pendingIntent != null) {
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            if (activityTaskManagerService.mDexController.getDexModeLocked() == 2) {
                List queryIntentComponents = pendingIntent.queryIntentComponents(0);
                if (!queryIntentComponents.isEmpty() && (activityInfo = ((ResolveInfo) queryIntentComponents.get(0)).activityInfo) != null) {
                    ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                    if (!activityTaskManagerService.mDexController.getTaskLocked(applicationInfo.uid, 0, applicationInfo.processName, true).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void minimizeAllTasksLocked(int i, boolean z) {
        ActivityRecord activityRecord;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        DisplayContent displayContent = activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
            return;
        }
        if (displayContent.isDexMode()) {
            DexController dexController = activityTaskManagerService.mDexController;
            if (!((ArrayList) dexController.mMinimizedToggleTasks).isEmpty()) {
                dexController.restoreToggleTasksToFrontLocked(displayContent.mDisplayId);
                return;
            }
        }
        if (MultiWindowCoreState.MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED && !displayContent.isDexMode()) {
            Slog.i("MultiTaskingController", "Blocked minimized freeform by multistar");
            return;
        }
        boolean z2 = CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY;
        boolean z3 = i == 0 && !displayContent.isDexMode();
        try {
            activityTaskManagerService.deferWindowLayout();
            if (z3) {
                activityTaskManagerService.mFreeformController.mDeferMinimizeCallback = true;
            }
            ArrayList arrayList = new ArrayList();
            displayContent.getDefaultTaskDisplayArea().forAllRootTasks((Consumer) new MultiTaskingController$$ExternalSyntheticLambda2(this, arrayList, 1), true);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Task task = (Task) it.next();
                if (minimizeTaskLocked(-1, -1, task, z)) {
                    if (displayContent.isDexMode()) {
                        DexController dexController2 = activityTaskManagerService.mDexController;
                        if (!((ArrayList) dexController2.mMinimizedToggleTasks).contains(task)) {
                            ((ArrayList) dexController2.mMinimizedToggleTasks).add(task);
                        }
                    }
                    boolean z4 = CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY;
                }
            }
            if (CoreRune.MT_NEW_DEX_PIP && (activityRecord = this.mTmpPipCandidate) != null) {
                this.mTmpPipCandidate = null;
                if (activityTaskManagerService.mActivityClientController.requestPictureInPictureMode(activityRecord)) {
                    Slog.d("MultiTaskingController", "minimizeAllTasksLocked: entered pip, r=" + activityRecord);
                } else {
                    activityRecord.supportsEnterPipOnTaskSwitch = false;
                    Slog.d("MultiTaskingController", "minimizeAllTasksLocked: failed to enter pip, r=" + activityRecord);
                }
            }
            if (z3) {
                activityTaskManagerService.mFreeformController.continueMinimizeStateChangedCallbacks();
            }
            activityTaskManagerService.continueWindowLayout();
        } catch (Throwable th) {
            if (z3) {
                activityTaskManagerService.mFreeformController.continueMinimizeStateChangedCallbacks();
            }
            activityTaskManagerService.continueWindowLayout();
            throw th;
        }
    }

    public final boolean minimizeTaskLocked(int i, int i2, final Task task, final boolean z) {
        WindowState windowState;
        InputTarget inputTarget;
        if (task == null) {
            Slog.w("MultiTaskingController", "minimizeTaskLocked: fail, task is null");
            return false;
        }
        if (!task.canMinimize()) {
            Slog.w("MultiTaskingController", "minimizeTaskLocked: fail, task can't minimize. t=" + task);
            if (task.isAnimating() && task.getWindowingMode() == 5) {
                Slog.w("MultiTaskingController", "minimizeTaskLocked: cancelAnimation, t=" + task);
                task.cancelAnimation();
            }
            return false;
        }
        DisplayContent displayContent = task.getDisplayContent();
        if (displayContent == null || displayContent.isDesktopModeEnabled() || (windowState = displayContent.mInputMethodWindow) == null || !windowState.isVisible() || (inputTarget = displayContent.mImeInputTarget) == null || inputTarget.getWindowState() == null || displayContent.mImeInputTarget.getWindowState().getTask() != task || !displayContent.mInsetsStateController.getImeSourceProvider().mImeShowing || !displayContent.mInsetsStateController.getImeSourceProvider().mImeShowing) {
            return task.moveTaskToBack(task, null, true, z, i, i2);
        }
        InputMethodManagerInternal.get().hideAllInputMethods(61);
        this.mH.postDelayed(new Runnable() { // from class: com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingController multiTaskingController = MultiTaskingController.this;
                Task task2 = task;
                boolean z2 = z;
                WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingController.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        task2.moveTaskToBack(task2, null, true, z2, -1, -1);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }, 400L);
        return true;
    }

    public final void removeEmbedActivityProcessIfNeeded(int i, String str) {
        try {
            PackageInfo packageInfoAsUser = this.mAtm.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
            if (packageInfoAsUser != null) {
                ActivityTaskManagerService activityTaskManagerService = this.mAtm;
                ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
                final WindowProcessController processController = activityTaskManagerService.getProcessController(applicationInfo.uid, applicationInfo.processName);
                if (processController == null || processController.mPid == ActivityManagerService.MY_PID) {
                    return;
                }
                this.mH.post(new Runnable() { // from class: com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        MultiTaskingController multiTaskingController = MultiTaskingController.this;
                        WindowProcessController windowProcessController = processController;
                        multiTaskingController.mAtm.mAmInternal.killProcess(windowProcessController.mName, windowProcessController.mUid, "embedded-package-changed");
                    }
                });
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final boolean removeFocusedTask(int i) {
        int i2 = 0;
        if (i == -1) {
            return false;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean[] zArr = {false};
                displayContent.forAllTaskDisplayAreas(new MultiTaskingController$$ExternalSyntheticLambda2(this, zArr, i2));
                boolean z = zArr[0];
                WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setEmbedActivityPackageEnabled(final String str, boolean z, final int i) {
        int i2 = z ? 1 : 2;
        if (this.mAtm.mExt.mActivityEmbeddedController.getEnabled(i, str) == i2) {
            return;
        }
        ActivityEmbeddedController activityEmbeddedController = this.mAtm.mExt.mActivityEmbeddedController;
        activityEmbeddedController.mUserChange.putValue(str, activityEmbeddedController.findTargetUserId(i), Integer.valueOf(i2));
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                final ArrayList arrayList = new ArrayList();
                this.mAtm.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ComponentName componentName;
                        MultiTaskingController multiTaskingController = MultiTaskingController.this;
                        int i3 = i;
                        String str2 = str;
                        ArrayList arrayList2 = arrayList;
                        Task task = (Task) obj;
                        if (multiTaskingController.mAtm.mExt.mActivityEmbeddedController.findTargetUserId(task.mUserId) == i3 && (componentName = task.realActivity) != null && componentName.getPackageName().equals(str2)) {
                            arrayList2.add(task);
                        }
                    }
                }, true);
                if (arrayList.isEmpty()) {
                    removeEmbedActivityProcessIfNeeded(i, str);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Task task = (Task) it.next();
                    if (task.isVisible()) {
                        task.mIsWaitingRemoveEmbedActivityTask = true;
                        task.getRootTask().moveTaskToBack(task, null);
                    } else {
                        WindowProcessController windowProcessController = task.mRootProcess;
                        if (windowProcessController != null && windowProcessController.mPid != ActivityManagerService.MY_PID) {
                            this.mH.post(new MultiTaskingController$$ExternalSyntheticLambda14(this, task, windowProcessController));
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    public final boolean shouldDeferEnterSplit(List list, List list2) {
        String str;
        int uid;
        ActivityInfo activityInfo;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.mDexController.getDexModeLocked() != 2) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List queryIntentComponents = ((PendingIntent) it.next()).queryIntentComponents(0);
            if (!queryIntentComponents.isEmpty() && (activityInfo = ((ResolveInfo) queryIntentComponents.get(0)).activityInfo) != null) {
                ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                ArrayList taskLocked = activityTaskManagerService.mDexController.getTaskLocked(applicationInfo.uid, 0, applicationInfo.processName, true);
                if (!taskLocked.isEmpty()) {
                    Iterator it2 = taskLocked.iterator();
                    while (it2.hasNext()) {
                        arrayList.add(((DexController.FindTaskResult) it2.next()).mTask);
                    }
                }
            }
        }
        Iterator it3 = list2.iterator();
        while (it3.hasNext()) {
            Task anyTaskForId = activityTaskManagerService.mRootWindowContainer.anyTaskForId(((Integer) it3.next()).intValue(), 1, null, false);
            if (anyTaskForId != null && anyTaskForId.getDisplayId() == 2) {
                if (!anyTaskForId.isVisible() || anyTaskForId.topRunningActivity(false) == null) {
                    ActivityRecord rootActivity = anyTaskForId.getRootActivity(true, false);
                    if (rootActivity != null) {
                        str = rootActivity.processName;
                        uid = rootActivity.getUid();
                    } else {
                        WindowProcessController windowProcessController = anyTaskForId.mRootProcess;
                        if (windowProcessController != null) {
                            String str2 = windowProcessController.mName;
                            uid = windowProcessController.mUid;
                            str = str2;
                        }
                    }
                    ArrayList taskLocked2 = activityTaskManagerService.mDexController.getTaskLocked(uid, 0, str, true);
                    if (!taskLocked2.isEmpty()) {
                        Iterator it4 = taskLocked2.iterator();
                        while (it4.hasNext()) {
                            arrayList.add(((DexController.FindTaskResult) it4.next()).mTask);
                        }
                    }
                } else {
                    arrayList.add(anyTaskForId);
                }
            }
        }
        Iterator it5 = arrayList.iterator();
        while (it5.hasNext()) {
            minimizeTaskLocked(-1, -1, (Task) it5.next(), true);
        }
        return !arrayList.isEmpty();
    }

    public final void startAssistantActivityToSplitLocked(Intent intent, float f) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        ResolveInfo resolveActivity = activityTaskManagerService.mContext.getPackageManager().resolveActivity(intent, 1024);
        boolean z = true;
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            Slog.d("MultiTaskingController", "confirmSplitScreenMode : ri is null, li=" + intent);
        } else {
            if (activityTaskManagerService.mDexController.getDexModeLocked() == 2) {
                try {
                    activityTaskManagerService.mContext.getPackageManager().getApplicationInfo(resolveActivity.activityInfo.packageName, 128);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (MultiWindowUtils.isAiKeyTrampolineActivity(resolveActivity.activityInfo.packageName)) {
                intent.setAiKeyAppLaunch(true);
            }
        }
        Task task = activityTaskManagerService.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().getTask(new MultiTaskingController$$ExternalSyntheticLambda0(0));
        DisplayContent displayContent = activityTaskManagerService.mRootWindowContainer.getDisplayContent(0);
        DisplayRotation displayRotation = displayContent != null ? displayContent.mDisplayRotation : null;
        if (displayRotation != null) {
            int i = displayRotation.mLastSensorRotation;
            int i2 = displayRotation.mUserRotationMode;
            ActivityRecord activityRecord = task != null ? task.topRunningActivity(false) : null;
            if (i == 1 && i2 == 0 && activityRecord != null && ActivityInfo.isFixedOrientationPortrait(activityRecord.info.screenOrientation) && !CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
                Slog.d("MultiTaskingController", "deferSplitRotationIfNeeded: trigger");
                TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                taskOrganizerInfo.setAssistantActivityToSplit(intent, f, z);
                activityTaskManagerService.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
            }
        }
        z = false;
        TaskOrganizerInfo taskOrganizerInfo2 = new TaskOrganizerInfo();
        taskOrganizerInfo2.setAssistantActivityToSplit(intent, f, z);
        activityTaskManagerService.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo2.toBundle());
    }

    public final void updateEmbedActivityPackageEnabled(int i, int i2, String str, boolean z) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.mExt.mActivityEmbeddedController.getEnabled(i2, str) == 0 || z) {
            ActivityEmbeddedController activityEmbeddedController = activityTaskManagerService.mExt.mActivityEmbeddedController;
            activityEmbeddedController.mUserChange.putValue(str, activityEmbeddedController.findTargetUserId(i2), Integer.valueOf(i));
        }
    }

    public final void updateMultiSplitAppMinimumSizeLocked() {
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            int i = this.mSplitFeasibleMode;
            this.mSplitFeasibleMode = 2;
            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
            DisplayContent displayContent = activityTaskManagerService.mRootWindowContainer.mDefaultDisplay;
            if (displayContent == null) {
                return;
            }
            DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
            Resources currentUserResources = displayPolicy.getCurrentUserResources();
            int dimensionPixelSize = currentUserResources.getDimensionPixelSize(R.dimen.harmful_app_padding_top) - (currentUserResources.getDimensionPixelSize(R.dimen.harmful_app_name_padding_top) * 2);
            int dimensionPixelSize2 = currentUserResources.getConfiguration().densityDpi >= ((MultiWindowUtils.isTablet() || (CoreRune.MW_MULTI_SPLIT_FOR_COVER_DISPLAY && (currentUserResources.getConfiguration().semDisplayDeviceType == 5))) ? FrameworkStatsLog.VBMETA_DIGEST_REPORTED : FrameworkStatsLog.SYSTEM_SERVER_PRE_WATCHDOG_OCCURRED) ? currentUserResources.getDimensionPixelSize(R.dimen.floating_toolbar_vertical_margin) : currentUserResources.getDimensionPixelSize(R.dimen.progress_bar_size_small);
            int i2 = 0;
            while (true) {
                if (i2 >= 4) {
                    break;
                }
                if (i2 != 2 || displayContent.mDisplayRotation.mAllowAllRotations == 1) {
                    boolean z = i2 == 1 || i2 == 3;
                    DisplayPolicy.DecorInsets.Info decorInsetsInfo = displayPolicy.getDecorInsetsInfo(i2, z ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth, z ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight);
                    int width = decorInsetsInfo.mConfigFrame.width();
                    int height = decorInsetsInfo.mConfigFrame.height();
                    int i3 = (dimensionPixelSize2 * 2) + dimensionPixelSize;
                    boolean z2 = width < i3;
                    boolean z3 = height < i3;
                    if (z2 && z3) {
                        this.mSplitFeasibleMode = 0;
                        break;
                    } else if (z2 != z3) {
                        this.mSplitFeasibleMode = 1;
                        break;
                    }
                }
                i2++;
            }
            boolean z4 = displayContent.mIsSizeForced || displayContent.mIsDensityForced;
            if (z4 || this.mIsDisplaySizeOverride) {
                TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                if (z4) {
                    taskOrganizerInfo.setSplitFeasibleMode(this.mSplitFeasibleMode);
                } else {
                    taskOrganizerInfo.setSplitFeasibleMode(2);
                }
                activityTaskManagerService.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
                Slog.d("MultiTaskingController", "split feasible change, prev=" + i + ", cur=" + this.mSplitFeasibleMode + ", override=" + z4);
            }
            this.mIsDisplaySizeOverride = z4;
        }
    }
}
