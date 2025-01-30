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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.DlsRestoreDispatcher;
import com.android.systemui.wallpaper.MultiPackDispatcher;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.colors.ColorData;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpaper.view.KeyguardAnimatedWallpaper;
import com.android.systemui.wallpaper.view.KeyguardBlurredWallpaper;
import com.android.systemui.wallpaper.view.KeyguardImageWallpaper;
import com.android.systemui.wallpaper.view.KeyguardLiveWallpaper;
import com.android.systemui.wallpaper.view.KeyguardMotionWallpaper;
import com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper;
import com.android.systemui.wallpaper.view.KeyguardVideoWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardWallpaperController extends IWallpaperManagerCallback.Stub implements KeyguardWallpaper, PluginWallpaperCallback {
    public static KeyguardWallpaperController sController;
    public final SemGfxImageFilter mBlurFilter;
    public KeyguardBlurredWallpaper mBlurredView;
    public int mBottom;
    public final Context mContext;
    public DlsRestoreDispatcher mDlsRestoreDispatcher;
    public final DozeParameters mDozeParameters;
    public boolean mIsLockscreenDisabled;
    public final C36623 mKnoxStateCallback;
    public final LockPatternUtils mLockPatternUtils;
    public final Handler mMainHandler;
    public MultiPackDispatcher mMultiPackDispatcher;
    public Consumer mNoSensorConsumer;
    public final WallpaperChangeObserver mObserver;
    public int mOldLockScreenWallpaperSettingsValue;
    public int mOldLockScreenWallpaperSubSettingsValue;
    public int mOldTransparentType;
    public SystemUIWallpaperBase mOldWallpaperView;
    public final PluginLockUtils mPluginLockUtils;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public ViewGroup mRootView;
    public KeyguardWallpaperController$$ExternalSyntheticLambda9 mRunnableCleanUp;
    public KeyguardWallpaperController$$ExternalSyntheticLambda7 mRunnableSetBackground;
    public KeyguardWallpaperController$$ExternalSyntheticLambda2 mRunnableUpdate;
    public boolean mScreenOn;
    public final SemDisplaySolutionManager mSemDisplaySolutionManager;
    public final IWallpaperManager mService;
    public final SettingsHelper mSettingsHelper;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final C36634 mTransitionAnimationListener;
    public final C36645 mTransitionListener;
    public SystemUIWallpaperBase mTransitionView;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final WallpaperAnalytics mWallpaperAnalytics;
    public final WallpaperChangeNotifier mWallpaperChangeNotifier;
    public final WallpaperEventNotifier mWallpaperEventNotifier;
    public final WallpaperLogger mWallpaperLogger;
    public final WallpaperManager mWallpaperManager;
    public final C36612 mWallpaperResultCallback;
    public SystemUIWallpaperBase mWallpaperView;
    public Consumer mWcgConsumer;
    public final HandlerC36656 mWorkHandler;
    public boolean mIsKeyguardShowing = false;
    public boolean mOccluded = false;
    public boolean mIsGoingToSleep = false;
    public boolean mIsFingerPrintTouchDown = false;
    public int mVisibility = 4;
    public boolean mIsPendingTypeChange = false;
    public boolean mIsPendingTypeChangeForTransition = false;
    public boolean mPendingRotationForTransitionView = false;
    public boolean mDismissCancelled = false;
    public int mCurrentUserId = 0;
    public boolean mIsBlurredViewAdded = false;
    public boolean mBouncer = false;
    public boolean mWallpaperChanged = false;
    public int mDlsViewMode = 0;
    public final C36601 mColorProvider = new Provider() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.1
        @Override // javax.inject.Provider
        public final Object get() {
            KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
            keyguardWallpaperController.getClass();
            boolean z = WallpaperUtils.mIsUltraPowerSavingMode;
            boolean z2 = WallpaperUtils.mIsEmergencyMode;
            if (z || z2) {
                return SemWallpaperColors.getBlankWallpaperColors();
            }
            SemWallpaperColors semWallpaperColors = keyguardWallpaperController.mWallpaperEventNotifier.getSemWallpaperColors(false);
            if (semWallpaperColors != null) {
                return semWallpaperColors;
            }
            SemWallpaperColors wallpaperColors = keyguardWallpaperController.getWallpaperColors(false);
            if (wallpaperColors != null) {
                return wallpaperColors;
            }
            Log.d("KeyguardWallpaperController", "getHints: getBlankWallpaperColors!");
            return SemWallpaperColors.getBlankWallpaperColors();
        }
    };
    public boolean mIsPluginLockReady = false;
    public final ExecutorService mExecutor = Executors.newFixedThreadPool(2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.KeyguardWallpaperController$4 */
    public final class C36634 {
        public C36634() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.KeyguardWallpaperController$5 */
    public final class C36645 {
        public C36645() {
        }

        public final void onDrawCompleted() {
            KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
            StringBuilder sb = new StringBuilder("onDrawCompleted: mIsPendingTypeChangeForTransition = ");
            KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, keyguardWallpaperController2.mIsPendingTypeChangeForTransition, "KeyguardWallpaperController");
            if (keyguardWallpaperController2.mIsPendingTypeChangeForTransition) {
                keyguardWallpaperController2.mMainHandler.postAtFrontOfQueue(keyguardWallpaperController2.mRunnableSetBackground);
                keyguardWallpaperController2.mIsPendingTypeChangeForTransition = false;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.KeyguardWallpaperController$8 */
    public final class C36678 {
        public C36678() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.KeyguardWallpaperController$9 */
    public final class C36689 {
        public C36689() {
        }

        public final void onMultipackApplied(int i, int i2) {
            KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
            if (i == 0) {
                Settings.System.putInt(keyguardWallpaperController.mContext.getContentResolver(), i2 == 0 ? "lockscreen_wallpaper" : "lockscreen_wallpaper_sub", 1);
                StringBuilder sb = new StringBuilder("put settings ");
                sb.append(keyguardWallpaperController.mSettingsHelper.isLiveWallpaperEnabled(i2 == 0));
                keyguardWallpaperController.printLognAddHistory(sb.toString());
            }
            String concat = "onMultipackApplied: reason = ".concat(i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "APPLY_MULTIPACK_RESULT_FAIL_DLS_INTERNAL_ERROR" : "APPLY_MULTIPACK_RESULT_FAIL_LIVE_WALLPAPER" : "APPLY_MULTIPACK_RESULT_FAIL_RETRY_COUNT_OVER" : "APPLY_MULTIPACK_RESULT_SUCCESS");
            KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.sController;
            keyguardWallpaperController.printLognAddHistory(concat);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$1] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$2] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.knox.KnoxStateMonitorCallback, com.android.systemui.wallpaper.KeyguardWallpaperController$3] */
    /* JADX WARN: Type inference failed for: r9v7, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$6] */
    public KeyguardWallpaperController(Context context, WallpaperManager wallpaperManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle, WallpaperLogger wallpaperLogger, WallpaperEventNotifier wallpaperEventNotifier, SystemWallpaperColors systemWallpaperColors, DozeParameters dozeParameters, ConfigurationController configurationController, KeyguardFoldController keyguardFoldController, WallpaperChangeObserver wallpaperChangeObserver, KeyguardWallpaperEventHandler keyguardWallpaperEventHandler, WallpaperChangeNotifier wallpaperChangeNotifier) {
        boolean z;
        this.mOldLockScreenWallpaperSettingsValue = -1;
        this.mOldLockScreenWallpaperSubSettingsValue = -1;
        HandlerThread handlerThread = new HandlerThread("KeyguardWallpaperThread");
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mWallpaperResultCallback = new WallpaperResultCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.2
            @Override // com.android.systemui.wallpaper.WallpaperResultCallback
            public final void onDrawFinished() {
                KeyguardBlurredWallpaper keyguardBlurredWallpaper;
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                StringBuilder sb = new StringBuilder("onDrawFinished: chaged = ");
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                NotificationListener$$ExternalSyntheticOutline0.m123m(sb, keyguardWallpaperController2.mWallpaperChanged, "KeyguardWallpaperController");
                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed() && (keyguardBlurredWallpaper = keyguardWallpaperController2.mBlurredView) != null && keyguardWallpaperController2.mWallpaperChanged) {
                    keyguardBlurredWallpaper.update();
                    keyguardWallpaperController2.mWallpaperChanged = false;
                }
            }

            @Override // com.android.systemui.wallpaper.WallpaperResultCallback
            public final void onPreviewReady() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                keyguardWallpaperController.mObserver.updateState(2);
                boolean z2 = false;
                if (LsRune.KEYGUARD_FBE && keyguardWallpaperController.isPluginLockFbeCondition()) {
                    if (((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).isFbeWallpaperAvailable(PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich))) {
                        if (!(KeyguardUpdateMonitor.getCurrentUser() != 0)) {
                            Log.d("KeyguardWallpaperController", "isFbeWallpaperInDisplay: true");
                            z2 = true;
                        }
                    }
                }
                if (z2) {
                    int screenId = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                    SemWallpaperColors fbeSemWallpaperColors = ((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).getFbeSemWallpaperColors(screenId);
                    KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.sController;
                    Log.i("KeyguardWallpaperController", "onPreviewReady" + fbeSemWallpaperColors + " , screenId = " + screenId);
                    keyguardWallpaperController.onWallpaperHintUpdate(fbeSemWallpaperColors);
                }
            }

            @Override // com.android.systemui.wallpaper.WallpaperResultCallback
            public final void onDelegateBitmapReady(Bitmap bitmap) {
            }
        };
        ?? r7 = new KnoxStateMonitorCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.3
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onEnableMDMWallpaper() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                keyguardWallpaperController2.printLognAddHistory("onEnableMDMWallpaper");
                keyguardWallpaperController2.sendUpdateWallpaperMessage(605);
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onMDMWallpaperChanged() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                keyguardWallpaperController2.printLognAddHistory("onMDMWallpaperChanged");
                keyguardWallpaperController2.sendUpdateWallpaperMessage(VolteConstants.ErrorCode.NOT_ACCEPTABLE2);
            }
        };
        this.mKnoxStateCallback = r7;
        this.mTransitionAnimationListener = new C36634();
        this.mTransitionListener = new C36645();
        Log.d("KeyguardWallpaperController", "KeyguardWallpaperController()");
        this.mContext = context;
        this.mWallpaperManager = wallpaperManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mWallpaperLogger = wallpaperLogger;
        this.mDozeParameters = dozeParameters;
        this.mObserver = wallpaperChangeObserver;
        handlerThread.start();
        handlerThread.setPriority(10);
        final ?? r9 = new Handler(handlerThread.getLooper()) { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.6
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:351:0x02f0  */
            /* JADX WARN: Removed duplicated region for block: B:354:0x02fb  */
            /* JADX WARN: Removed duplicated region for block: B:356:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:357:0x02f3  */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void handleMessage(Message message) {
                MultiPackDispatcher multiPackDispatcher;
                WallpaperChangeNotifier wallpaperChangeNotifier2;
                int i;
                int i2;
                Uri semGetUri;
                SystemUIWallpaperBase systemUIWallpaperBase;
                SystemUIWallpaperBase systemUIWallpaperBase2;
                final KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.sController;
                keyguardWallpaperController.getClass();
                int i3 = message.what;
                int i4 = 8;
                int i5 = 0;
                switch (i3) {
                    case 601:
                        Bundle data = message.getData();
                        Log.d("KeyguardWallpaperController", "handleWallpaperChanged");
                        int lockWallpaperType = keyguardWallpaperController.getLockWallpaperType(data != null ? data.getBoolean("include_dls", true) : true);
                        WallpaperUtils.clearCachedWallpaper(6);
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                            WallpaperUtils.clearCachedWallpaper(18);
                        }
                        if (!keyguardWallpaperController.isStartMultipackCondition() && (multiPackDispatcher = keyguardWallpaperController.mMultiPackDispatcher) != null) {
                            if (multiPackDispatcher.mOnApplyMultipackListener != null) {
                                multiPackDispatcher.mOnApplyMultipackListener = null;
                            }
                            MultiPackDispatcher.MyHandler myHandler = multiPackDispatcher.mHandler;
                            if (myHandler != null && myHandler.hasMessages(0)) {
                                multiPackDispatcher.mHandler.removeMessages(0);
                            }
                            ((WallpaperLoggerImpl) multiPackDispatcher.mLoggerWrapper).log("MultiPackDispatcher", "clear");
                            Log.d("MultiPackDispatcher", "clear");
                            keyguardWallpaperController.mMultiPackDispatcher = null;
                        }
                        if (KeyguardWallpaperController.isMatching(lockWallpaperType, keyguardWallpaperController.mWallpaperView) && !WallpaperUtils.isLiveWallpaperEnabled(KeyguardWallpaperController.isSubDisplay())) {
                            keyguardWallpaperController.handleWallpaperResourceUpdated();
                            return;
                        } else {
                            keyguardWallpaperController.printLognAddHistory("handleWallpaperChanged: Type mismatching. Creating new wallpaper view.");
                            keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType);
                            return;
                        }
                    case 602:
                    case VolteConstants.ErrorCode.DECLINE /* 603 */:
                        Log.d("KeyguardWallpaperController", "colorUpdateForModeChange");
                        if (keyguardWallpaperController.mRootView != null) {
                            final SemWallpaperColors wallpaperColors = keyguardWallpaperController.getWallpaperColors(false);
                            final int i6 = WallpaperUtils.sCurrentWhich;
                            keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda10
                                /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
                                /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
                                /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
                                /* JADX WARN: Removed duplicated region for block: B:45:0x0045  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    SettingsHelper settingsHelper2;
                                    long j;
                                    SemWallpaperColors.Item item;
                                    SemWallpaperColors.Item item2;
                                    SemWallpaperColors.Item item3;
                                    KeyguardWallpaperController keyguardWallpaperController3 = KeyguardWallpaperController.this;
                                    SemWallpaperColors semWallpaperColors = wallpaperColors;
                                    int i7 = i6;
                                    KeyguardWallpaperColors keyguardWallpaperColors = keyguardWallpaperController3.mWallpaperEventNotifier.mKeyguardWallpaperColors;
                                    keyguardWallpaperColors.getClass();
                                    int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                                    boolean z2 = LsRune.WALLPAPER_SUB_WATCHFACE;
                                    long j2 = 0;
                                    if (z2 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                                        if ((i7 & 2) == 0 && (i7 & 16) == 0 && (i7 & 32) == 0) {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i7, "KeyguardWallpaperColors");
                                        }
                                        SparseArray sparseArray = !WallpaperUtils.isCoverScreen(i7) ? keyguardWallpaperColors.mSemWallpaperColorsCover : keyguardWallpaperColors.mSemWallpaperColors;
                                        settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                        if (semWallpaperColors == null) {
                                            StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSemWallpaperColors: userId = ", currentUser, ", colors = ");
                                            m1m.append(semWallpaperColors.toSimpleString());
                                            Log.d("KeyguardWallpaperColors", m1m.toString());
                                            ColorData colorData = new ColorData(semWallpaperColors, settingsHelper2.isOpenThemeLook(), settingsHelper2.isOpenThemeLockWallpaper(), false);
                                            j = keyguardWallpaperColors.checkUpdates((ColorData) sparseArray.get(currentUser), colorData);
                                            sparseArray.put(currentUser, colorData);
                                        } else {
                                            j = 0;
                                        }
                                        if ((z2 || !WallpaperUtils.isCoverScreen(i7)) && j != 0) {
                                            Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                            if (j != 0 && semWallpaperColors != null) {
                                                if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                                                    int fontColor = item3.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor;
                                                }
                                                if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                                                    int fontColor2 = item2.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor2, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor2;
                                                }
                                                if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                                                    int fontColor3 = item.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor3, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor3;
                                                }
                                            }
                                        }
                                        j2 = j;
                                    } else {
                                        if ((i7 & 2) != 2) {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i7, "KeyguardWallpaperColors");
                                        }
                                        if (!WallpaperUtils.isCoverScreen(i7)) {
                                        }
                                        settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                        if (semWallpaperColors == null) {
                                        }
                                        if (z2) {
                                        }
                                        Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                        if (j != 0) {
                                            if ((j & 512) != 0) {
                                                int fontColor4 = item3.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor4, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor4;
                                            }
                                            if ((j & 16) != 0) {
                                                int fontColor22 = item2.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor22, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor22;
                                            }
                                            if ((j & 256) != 0) {
                                                int fontColor32 = item.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor32, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor32;
                                            }
                                        }
                                        j2 = j;
                                    }
                                    keyguardWallpaperController3.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i7), j2, semWallpaperColors);
                                    SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController3.mWallpaperView;
                                    if (systemUIWallpaperBase3 != null) {
                                        systemUIWallpaperBase3.updateThumbnail();
                                    }
                                }
                            });
                        }
                        keyguardWallpaperController.handleWallpaperTypeChanged(WallpaperUtils.getWallpaperType());
                        return;
                    case VolteConstants.ErrorCode.DOES_NOT_EXIST_ANYWHERE /* 604 */:
                        int lockWallpaperType2 = keyguardWallpaperController.getLockWallpaperType(true);
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("handleDlsWallpaperChanged: wallpaperType = ", lockWallpaperType2, "KeyguardWallpaperController");
                        if (LsRune.WALLPAPER_SUPPORT_DLS_SNAPSHOT && LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && keyguardWallpaperController.getLockWallpaperType(false) == 1000 && (semGetUri = keyguardWallpaperController.mWallpaperManager.semGetUri(WallpaperUtils.sCurrentWhich)) != null) {
                            String substring = semGetUri.toString().substring(semGetUri.toString().lastIndexOf("/") + 1);
                            Log.d("KeyguardWallpaperController", "handleDlsWallpaperChanged: subType = " + substring);
                            if (!TextUtils.isEmpty(substring) && substring.contains("sgg") && (keyguardWallpaperController.mWallpaperView == null || !keyguardWallpaperController.mIsKeyguardShowing)) {
                                Log.d("KeyguardWallpaperController", "handleDlsWallpaperChanged: Skip event in SGG.");
                                wallpaperChangeNotifier2 = keyguardWallpaperController.mWallpaperChangeNotifier;
                                wallpaperChangeNotifier2.getClass();
                                int i7 = WallpaperUtils.sCurrentWhich;
                                i = Settings.System.getInt(wallpaperChangeNotifier2.mContext.getContentResolver(), "dls_state", 0);
                                Log.d("WallpaperChangeNotifier", "checkUpdateAndNotify: mDlsState = " + wallpaperChangeNotifier2.mDlsState + ", curDlsState = " + i);
                                i2 = !WhichChecker.isSubDisplay(i7) ? 30720 : CustomDeviceManager.SETTINGS_ALL_PREVIOUS;
                                if ((wallpaperChangeNotifier2.mDlsState & i2) == (i & i2)) {
                                    wallpaperChangeNotifier2.notify(i7);
                                    return;
                                }
                                return;
                            }
                        }
                        if (KeyguardWallpaperController.isMatching(lockWallpaperType2, keyguardWallpaperController.mWallpaperView)) {
                            if (WallpaperUtils.isAODShowLockWallpaperEnabled()) {
                                keyguardWallpaperController.mIsPendingTypeChangeForTransition = false;
                            }
                            keyguardWallpaperController.handleWallpaperResourceUpdated();
                        } else {
                            keyguardWallpaperController.printLognAddHistory("handleDlsWallpaperChanged: Type mismatching. Creating new wallpaper view.");
                            if ((WallpaperUtils.isAODShowLockWallpaperEnabled() && lockWallpaperType2 == 8 && keyguardWallpaperController.mTransitionView != null) != false) {
                                keyguardWallpaperController.printLognAddHistory("handleDlsWallpaperChanged: Pending rotation.");
                                keyguardWallpaperController.mPendingRotationForTransitionView = true;
                            }
                            keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType2);
                        }
                        wallpaperChangeNotifier2 = keyguardWallpaperController.mWallpaperChangeNotifier;
                        wallpaperChangeNotifier2.getClass();
                        int i72 = WallpaperUtils.sCurrentWhich;
                        i = Settings.System.getInt(wallpaperChangeNotifier2.mContext.getContentResolver(), "dls_state", 0);
                        Log.d("WallpaperChangeNotifier", "checkUpdateAndNotify: mDlsState = " + wallpaperChangeNotifier2.mDlsState + ", curDlsState = " + i);
                        if (!WhichChecker.isSubDisplay(i72)) {
                        }
                        if ((wallpaperChangeNotifier2.mDlsState & i2) == (i & i2)) {
                        }
                        break;
                    case 605:
                    case VolteConstants.ErrorCode.NOT_ACCEPTABLE2 /* 606 */:
                        break;
                    case 607:
                        final SemWallpaperColors wallpaperColors2 = keyguardWallpaperController.getWallpaperColors(false);
                        final int i8 = WallpaperUtils.sCurrentWhich;
                        keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda10
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
                            /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
                            /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
                            /* JADX WARN: Removed duplicated region for block: B:45:0x0045  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                SettingsHelper settingsHelper2;
                                long j;
                                SemWallpaperColors.Item item;
                                SemWallpaperColors.Item item2;
                                SemWallpaperColors.Item item3;
                                KeyguardWallpaperController keyguardWallpaperController3 = KeyguardWallpaperController.this;
                                SemWallpaperColors semWallpaperColors = wallpaperColors2;
                                int i73 = i8;
                                KeyguardWallpaperColors keyguardWallpaperColors = keyguardWallpaperController3.mWallpaperEventNotifier.mKeyguardWallpaperColors;
                                keyguardWallpaperColors.getClass();
                                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                                boolean z2 = LsRune.WALLPAPER_SUB_WATCHFACE;
                                long j2 = 0;
                                if (z2 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                                    if ((i73 & 2) == 0 && (i73 & 16) == 0 && (i73 & 32) == 0) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                    }
                                    SparseArray sparseArray = !WallpaperUtils.isCoverScreen(i73) ? keyguardWallpaperColors.mSemWallpaperColorsCover : keyguardWallpaperColors.mSemWallpaperColors;
                                    settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                    if (semWallpaperColors == null) {
                                        StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSemWallpaperColors: userId = ", currentUser, ", colors = ");
                                        m1m.append(semWallpaperColors.toSimpleString());
                                        Log.d("KeyguardWallpaperColors", m1m.toString());
                                        ColorData colorData = new ColorData(semWallpaperColors, settingsHelper2.isOpenThemeLook(), settingsHelper2.isOpenThemeLockWallpaper(), false);
                                        j = keyguardWallpaperColors.checkUpdates((ColorData) sparseArray.get(currentUser), colorData);
                                        sparseArray.put(currentUser, colorData);
                                    } else {
                                        j = 0;
                                    }
                                    if ((z2 || !WallpaperUtils.isCoverScreen(i73)) && j != 0) {
                                        Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                        if (j != 0 && semWallpaperColors != null) {
                                            if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                                                int fontColor4 = item3.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor4, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor4;
                                            }
                                            if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                                                int fontColor22 = item2.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor22, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor22;
                                            }
                                            if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                                                int fontColor32 = item.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor32, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor32;
                                            }
                                        }
                                    }
                                    j2 = j;
                                } else {
                                    if ((i73 & 2) != 2) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                    }
                                    if (!WallpaperUtils.isCoverScreen(i73)) {
                                    }
                                    settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                    if (semWallpaperColors == null) {
                                    }
                                    if (z2) {
                                    }
                                    Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                    if (j != 0) {
                                        if ((j & 512) != 0) {
                                            int fontColor42 = item3.getFontColor();
                                            Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor42, -2);
                                            settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor42;
                                        }
                                        if ((j & 16) != 0) {
                                            int fontColor222 = item2.getFontColor();
                                            Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor222, -2);
                                            settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor222;
                                        }
                                        if ((j & 256) != 0) {
                                            int fontColor322 = item.getFontColor();
                                            Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor322, -2);
                                            settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor322;
                                        }
                                    }
                                    j2 = j;
                                }
                                keyguardWallpaperController3.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i73), j2, semWallpaperColors);
                                SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController3.mWallpaperView;
                                if (systemUIWallpaperBase3 != null) {
                                    systemUIWallpaperBase3.updateThumbnail();
                                }
                            }
                        });
                        return;
                    case 608:
                        Bundle data2 = message.getData();
                        KeyguardUpdateMonitor.getCurrentUser();
                        if (data2 == null) {
                            keyguardWallpaperController.printLognAddHistory("handleWallpaperColorChanged: Error - extra is null!");
                            return;
                        }
                        final SemWallpaperColors parcelable = data2.getParcelable("wallpaper_colors");
                        final int i9 = data2.getInt("which", 2);
                        data2.getInt("userid", KeyguardUpdateMonitor.getCurrentUser());
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                            int i10 = WallpaperUtils.sCurrentWhich;
                            if ((i10 & 16) != 0) {
                                if ((i9 & 16) == 0 && (!LsRune.WALLPAPER_SUB_WATCHFACE || (i9 & 2) != 2)) {
                                    keyguardWallpaperController.printLognAddHistory(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("handleWallpaperColorChanged: currentWhich = ", i10, ", which = ", i9, ". Return."));
                                    return;
                                }
                            } else if (!LsRune.WALLPAPER_SUB_WATCHFACE && (i9 & 16) != 0) {
                                keyguardWallpaperController.printLognAddHistory(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("handleWallpaperColorChanged: currentWhich = ", i10, ", which = ", i9, ". Return."));
                                return;
                            }
                        }
                        if (parcelable != null) {
                            keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda10
                                /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
                                /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
                                /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
                                /* JADX WARN: Removed duplicated region for block: B:45:0x0045  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    SettingsHelper settingsHelper2;
                                    long j;
                                    SemWallpaperColors.Item item;
                                    SemWallpaperColors.Item item2;
                                    SemWallpaperColors.Item item3;
                                    KeyguardWallpaperController keyguardWallpaperController3 = KeyguardWallpaperController.this;
                                    SemWallpaperColors semWallpaperColors = parcelable;
                                    int i73 = i9;
                                    KeyguardWallpaperColors keyguardWallpaperColors = keyguardWallpaperController3.mWallpaperEventNotifier.mKeyguardWallpaperColors;
                                    keyguardWallpaperColors.getClass();
                                    int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                                    boolean z2 = LsRune.WALLPAPER_SUB_WATCHFACE;
                                    long j2 = 0;
                                    if (z2 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                                        if ((i73 & 2) == 0 && (i73 & 16) == 0 && (i73 & 32) == 0) {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                        }
                                        SparseArray sparseArray = !WallpaperUtils.isCoverScreen(i73) ? keyguardWallpaperColors.mSemWallpaperColorsCover : keyguardWallpaperColors.mSemWallpaperColors;
                                        settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                        if (semWallpaperColors == null) {
                                            StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSemWallpaperColors: userId = ", currentUser, ", colors = ");
                                            m1m.append(semWallpaperColors.toSimpleString());
                                            Log.d("KeyguardWallpaperColors", m1m.toString());
                                            ColorData colorData = new ColorData(semWallpaperColors, settingsHelper2.isOpenThemeLook(), settingsHelper2.isOpenThemeLockWallpaper(), false);
                                            j = keyguardWallpaperColors.checkUpdates((ColorData) sparseArray.get(currentUser), colorData);
                                            sparseArray.put(currentUser, colorData);
                                        } else {
                                            j = 0;
                                        }
                                        if ((z2 || !WallpaperUtils.isCoverScreen(i73)) && j != 0) {
                                            Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                            if (j != 0 && semWallpaperColors != null) {
                                                if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                                                    int fontColor42 = item3.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor42, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor42;
                                                }
                                                if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                                                    int fontColor222 = item2.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor222;
                                                }
                                                if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                                                    int fontColor322 = item.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor322, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor322;
                                                }
                                            }
                                        }
                                        j2 = j;
                                    } else {
                                        if ((i73 & 2) != 2) {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                        }
                                        if (!WallpaperUtils.isCoverScreen(i73)) {
                                        }
                                        settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                        if (semWallpaperColors == null) {
                                        }
                                        if (z2) {
                                        }
                                        Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                        if (j != 0) {
                                            if ((j & 512) != 0) {
                                                int fontColor422 = item3.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor422, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor422;
                                            }
                                            if ((j & 16) != 0) {
                                                int fontColor2222 = item2.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor2222, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor2222;
                                            }
                                            if ((j & 256) != 0) {
                                                int fontColor3222 = item.getFontColor();
                                                Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor3222, -2);
                                                settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor3222;
                                            }
                                        }
                                        j2 = j;
                                    }
                                    keyguardWallpaperController3.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i73), j2, semWallpaperColors);
                                    SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController3.mWallpaperView;
                                    if (systemUIWallpaperBase3 != null) {
                                        systemUIWallpaperBase3.updateThumbnail();
                                    }
                                }
                            });
                            return;
                        } else {
                            keyguardWallpaperController.printLognAddHistory("handleWallpaperColorChanged: Error - colors is null!");
                            return;
                        }
                    case 609:
                        Bundle data3 = message.getData();
                        if (data3 == null) {
                            keyguardWallpaperController.printLognAddHistory("notifyBackupStateChanged: bundle is null.");
                            return;
                        }
                        final int i11 = data3.getInt("which", 2);
                        final int i12 = data3.getInt(IMSParameter.CALL.STATUS, 0);
                        final int i13 = data3.getInt("key", 0);
                        final PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager;
                        PluginLockUtils pluginLockUtils2 = pluginWallpaperManagerImpl.mUtils;
                        if (pluginLockUtils2.mHandlerExecutor == null) {
                            pluginLockUtils2.mHandlerExecutor = new PluginLockUtils.HandlerExecutor();
                        }
                        if (pluginLockUtils2.mHandlerExecutor.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginWallpaperManagerImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PluginLockBasicManager pluginLockBasicManager;
                                PluginWallpaperManagerImpl pluginWallpaperManagerImpl2 = PluginWallpaperManagerImpl.this;
                                int i14 = i11;
                                int i15 = i12;
                                int i16 = i13;
                                pluginWallpaperManagerImpl2.getClass();
                                Log.d("PluginWallpaperManagerImpl", "onSemBackupStatusChanged which=" + i14 + ", status=" + i15 + ", key=" + i16);
                                try {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("which", i14);
                                    bundle.putInt(IMSParameter.CALL.STATUS, i15);
                                    bundle.putInt("key", i16);
                                    PluginLockDelegateApp pluginLockDelegateApp = pluginWallpaperManagerImpl2.mDelegateApp;
                                    if (pluginLockDelegateApp == null || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
                                        return;
                                    }
                                    pluginLockBasicManager.onSemBackupStatusChanged(bundle);
                                } catch (Throwable th) {
                                    th.printStackTrace();
                                }
                            }
                        })) {
                            return;
                        }
                        Log.w("PluginLockUtils", "HandlerExecutor execute failed");
                        return;
                    case 610:
                        if (keyguardWallpaperController.isStartMultipackCondition()) {
                            keyguardWallpaperController.startMultipack(WallpaperUtils.sCurrentWhich);
                            return;
                        }
                        return;
                    case 611:
                        keyguardWallpaperController.mWallpaperManager.semSetDLSWallpaperColors(null, 6);
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                            keyguardWallpaperController.mWallpaperManager.semSetDLSWallpaperColors(null, 18);
                        }
                        if (keyguardWallpaperController.isStartMultipackCondition()) {
                            keyguardWallpaperController.startMultipack(WallpaperUtils.sCurrentWhich);
                            return;
                        }
                        return;
                    case 612:
                        int lockWallpaperType3 = keyguardWallpaperController.getLockWallpaperType(true);
                        boolean z2 = !WallpaperUtils.isSubDisplay();
                        boolean userCanSkipBouncer = keyguardWallpaperController.mUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser());
                        boolean isSecure = keyguardWallpaperController.mUpdateMonitor.isSecure();
                        boolean z3 = LsRune.WALLPAPER_SUB_WATCHFACE;
                        if (z3) {
                            keyguardWallpaperController.mIsPendingTypeChange = false;
                        } else {
                            keyguardWallpaperController.mIsPendingTypeChange = z2 && !((isSecure && !userCanSkipBouncer && (keyguardWallpaperController.mIsKeyguardShowing || keyguardWallpaperController.mOccluded)) || keyguardWallpaperController.mDismissCancelled || keyguardWallpaperController.mOccluded);
                        }
                        WallpaperLogger wallpaperLogger2 = keyguardWallpaperController.mWallpaperLogger;
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("handleDisplayChanged: open = ", z2, " , secure = ", isSecure, " , can skip bouncer = ");
                        m69m.append(userCanSkipBouncer);
                        m69m.append(" , dismissCancelled = ");
                        m69m.append(keyguardWallpaperController.mDismissCancelled);
                        m69m.append(" , type = ");
                        m69m.append(lockWallpaperType3);
                        m69m.append(" , showing = ");
                        m69m.append(keyguardWallpaperController.mIsKeyguardShowing);
                        m69m.append(" , occuded = ");
                        m69m.append(keyguardWallpaperController.mOccluded);
                        m69m.append(" , mIsPendingTypeChange = ");
                        m69m.append(keyguardWallpaperController.mIsPendingTypeChange);
                        ((WallpaperLoggerImpl) wallpaperLogger2).log("KeyguardWallpaperController", m69m.toString());
                        if (z3) {
                            if (z2) {
                                if (WallpaperUtils.isLiveWallpaperEnabled(false) || !KeyguardWallpaperController.isMatching(lockWallpaperType3, keyguardWallpaperController.mWallpaperView)) {
                                    keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType3, true);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (keyguardWallpaperController.mIsPendingTypeChange) {
                            keyguardWallpaperController.hideLockOnlyLiveWallpaperImmediately();
                            keyguardWallpaperController.cleanUp(false);
                        } else {
                            if (keyguardWallpaperController.mRunnableCleanUp != null) {
                                Log.i("KeyguardWallpaperController", "handleDisplayChanged, remove cleanup runnable");
                                keyguardWallpaperController.mMainHandler.removeCallbacks(keyguardWallpaperController.mRunnableCleanUp);
                            }
                            if (WallpaperUtils.isLiveWallpaperEnabled(true) && !WallpaperUtils.isLiveWallpaperEnabled(false)) {
                                keyguardWallpaperController.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 12));
                            }
                            keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType3, true);
                        }
                        if (keyguardWallpaperController.mDismissCancelled) {
                            keyguardWallpaperController.mDismissCancelled = false;
                        }
                        keyguardWallpaperController.forceBroadcastWhiteKeyguardWallpaper(keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperTransparent());
                        return;
                    case 613:
                        keyguardWallpaperController.startMultipack(message.getData().getInt("which", WallpaperUtils.sCurrentWhich));
                        return;
                    default:
                        switch (i3) {
                            case 719:
                                SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController.mWallpaperView;
                                if (systemUIWallpaperBase3 != null) {
                                    systemUIWallpaperBase3.dispatchWallpaperCommand("android.wallpaper.keyguardgoingaway");
                                    return;
                                }
                                return;
                            case 720:
                                int i14 = message.arg1;
                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onUserSwitching: userId = ", i14, "KeyguardWallpaperController");
                                keyguardWallpaperController.mCurrentUserId = i14;
                                keyguardWallpaperController.mIsLockscreenDisabled = keyguardWallpaperController.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
                                return;
                            case 721:
                                int i15 = message.arg1;
                                WallpaperUtils.loadDeviceState(i15, keyguardWallpaperController.mContext);
                                int lockWallpaperType4 = keyguardWallpaperController.getLockWallpaperType(true);
                                keyguardWallpaperController.mCurrentUserId = i15;
                                keyguardWallpaperController.printLognAddHistory("onUserSwitchComplete: userId = " + i15 + ", wallpaper type = " + lockWallpaperType4);
                                keyguardWallpaperController.mOldLockScreenWallpaperSettingsValue = keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperType(0);
                                keyguardWallpaperController.mOldLockScreenWallpaperSubSettingsValue = keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperType(16);
                                WallpaperEventNotifier wallpaperEventNotifier2 = keyguardWallpaperController.mWallpaperEventNotifier;
                                WallpaperManager wallpaperManager2 = wallpaperEventNotifier2.mWallpaperManager;
                                wallpaperEventNotifier2.setCurStatusFlag(false, wallpaperManager2 != null ? wallpaperManager2.semGetWallpaperColors(2) : null);
                                keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType4);
                                keyguardWallpaperController.sendUpdateWallpaperMessage(607);
                                return;
                            case 722:
                                Context context2 = keyguardWallpaperController.mContext;
                                WallpaperUtils.loadDeviceState(context2.getUserId(), context2);
                                int wallpaperType = WallpaperUtils.getWallpaperType();
                                if (WallpaperUtils.isExternalLiveWallpaper()) {
                                    wallpaperType = -2;
                                }
                                keyguardWallpaperController.printLognAddHistory("onBootCompleted: wallpaeprType = " + wallpaperType);
                                if (KeyguardWallpaperController.isMatching(wallpaperType, keyguardWallpaperController.mWallpaperView)) {
                                    keyguardWallpaperController.handleWallpaperResourceUpdated();
                                } else {
                                    keyguardWallpaperController.handleWallpaperTypeChanged(wallpaperType);
                                }
                                keyguardWallpaperController.sendUpdateWallpaperMessage(607);
                                return;
                            case 723:
                                keyguardWallpaperController.setKeyguardShowing(((Boolean) message.obj).booleanValue());
                                return;
                            case 724:
                                keyguardWallpaperController.mBouncer = ((Boolean) message.obj).booleanValue();
                                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                                    SystemUIWallpaperBase systemUIWallpaperBase4 = keyguardWallpaperController.mWallpaperView;
                                    if (systemUIWallpaperBase4 != null) {
                                        systemUIWallpaperBase4.updateBlurState(keyguardWallpaperController.mBouncer);
                                    }
                                    keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, i4));
                                }
                                SystemUIWallpaperBase systemUIWallpaperBase5 = keyguardWallpaperController.mWallpaperView;
                                if (systemUIWallpaperBase5 != null) {
                                    systemUIWallpaperBase5.onKeyguardBouncerFullyShowingChanged(keyguardWallpaperController.mBouncer);
                                    return;
                                }
                                return;
                            case 725:
                                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                                keyguardWallpaperController.mOccluded = booleanValue;
                                SystemUIWallpaperBase systemUIWallpaperBase6 = keyguardWallpaperController.mWallpaperView;
                                if (systemUIWallpaperBase6 != null) {
                                    systemUIWallpaperBase6.onOccluded(booleanValue);
                                }
                                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                                    if (!(keyguardWallpaperController.mWallpaperView instanceof KeyguardVideoWallpaper) && !WallpaperUtils.isExternalLiveWallpaper() && !keyguardWallpaperController.mOccluded && keyguardWallpaperController.mIsBlurredViewAdded) {
                                        keyguardWallpaperController.cleanUpBlurredView();
                                    }
                                    KeyguardBlurredWallpaper keyguardBlurredWallpaper = keyguardWallpaperController.mBlurredView;
                                    if (keyguardBlurredWallpaper != null) {
                                        keyguardBlurredWallpaper.onOccluded(keyguardWallpaperController.mOccluded);
                                    }
                                    if (!keyguardWallpaperController.mOccluded && !keyguardWallpaperController.mBouncer && keyguardWallpaperController.mDlsViewMode != 1) {
                                        keyguardWallpaperController.applyBlurFilter(0);
                                    }
                                }
                                keyguardWallpaperController.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 11));
                                return;
                            case 726:
                                keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 9));
                                return;
                            case 727:
                                if (!keyguardWallpaperController.mScreenOn) {
                                    Log.d("KeyguardWallpaperController", "onBiometricAuthenticated(): wakeup and unlocked");
                                }
                                keyguardWallpaperController.mIsFingerPrintTouchDown = false;
                                SystemUIWallpaperBase systemUIWallpaperBase7 = keyguardWallpaperController.mWallpaperView;
                                if (systemUIWallpaperBase7 != null) {
                                    systemUIWallpaperBase7.onFingerprintAuthSuccess(keyguardWallpaperController.mScreenOn);
                                    return;
                                }
                                return;
                            case 728:
                                SystemUIWallpaperBase systemUIWallpaperBase8 = keyguardWallpaperController.mWallpaperView;
                                if (systemUIWallpaperBase8 != null) {
                                    systemUIWallpaperBase8.onFaceAuthError();
                                    return;
                                }
                                return;
                            case 729:
                                keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = true;
                                return;
                            case 730:
                                keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = false;
                                final SemWallpaperColors wallpaperColors3 = keyguardWallpaperController.getWallpaperColors(false);
                                final int i16 = WallpaperUtils.sCurrentWhich;
                                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda10
                                    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
                                    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
                                    /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
                                    /* JADX WARN: Removed duplicated region for block: B:45:0x0045  */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final void run() {
                                        SettingsHelper settingsHelper2;
                                        long j;
                                        SemWallpaperColors.Item item;
                                        SemWallpaperColors.Item item2;
                                        SemWallpaperColors.Item item3;
                                        KeyguardWallpaperController keyguardWallpaperController3 = KeyguardWallpaperController.this;
                                        SemWallpaperColors semWallpaperColors = wallpaperColors3;
                                        int i73 = i16;
                                        KeyguardWallpaperColors keyguardWallpaperColors = keyguardWallpaperController3.mWallpaperEventNotifier.mKeyguardWallpaperColors;
                                        keyguardWallpaperColors.getClass();
                                        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                                        boolean z22 = LsRune.WALLPAPER_SUB_WATCHFACE;
                                        long j2 = 0;
                                        if (z22 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                                            if ((i73 & 2) == 0 && (i73 & 16) == 0 && (i73 & 32) == 0) {
                                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                            }
                                            SparseArray sparseArray = !WallpaperUtils.isCoverScreen(i73) ? keyguardWallpaperColors.mSemWallpaperColorsCover : keyguardWallpaperColors.mSemWallpaperColors;
                                            settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                            if (semWallpaperColors == null) {
                                                StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSemWallpaperColors: userId = ", currentUser, ", colors = ");
                                                m1m.append(semWallpaperColors.toSimpleString());
                                                Log.d("KeyguardWallpaperColors", m1m.toString());
                                                ColorData colorData = new ColorData(semWallpaperColors, settingsHelper2.isOpenThemeLook(), settingsHelper2.isOpenThemeLockWallpaper(), false);
                                                j = keyguardWallpaperColors.checkUpdates((ColorData) sparseArray.get(currentUser), colorData);
                                                sparseArray.put(currentUser, colorData);
                                            } else {
                                                j = 0;
                                            }
                                            if ((z22 || !WallpaperUtils.isCoverScreen(i73)) && j != 0) {
                                                Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                                if (j != 0 && semWallpaperColors != null) {
                                                    if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                                                        int fontColor422 = item3.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor422, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor422;
                                                    }
                                                    if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                                                        int fontColor2222 = item2.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor2222, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor2222;
                                                    }
                                                    if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                                                        int fontColor3222 = item.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor3222, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor3222;
                                                    }
                                                }
                                            }
                                            j2 = j;
                                        } else {
                                            if ((i73 & 2) != 2) {
                                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                            }
                                            if (!WallpaperUtils.isCoverScreen(i73)) {
                                            }
                                            settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                            if (semWallpaperColors == null) {
                                            }
                                            if (z22) {
                                            }
                                            Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                            if (j != 0) {
                                                if ((j & 512) != 0) {
                                                    int fontColor4222 = item3.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor4222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor4222;
                                                }
                                                if ((j & 16) != 0) {
                                                    int fontColor22222 = item2.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor22222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor22222;
                                                }
                                                if ((j & 256) != 0) {
                                                    int fontColor32222 = item.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor32222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor32222;
                                                }
                                            }
                                            j2 = j;
                                        }
                                        keyguardWallpaperController3.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i73), j2, semWallpaperColors);
                                        SystemUIWallpaperBase systemUIWallpaperBase32 = keyguardWallpaperController3.mWallpaperView;
                                        if (systemUIWallpaperBase32 != null) {
                                            systemUIWallpaperBase32.updateThumbnail();
                                        }
                                    }
                                });
                                return;
                            case 731:
                                keyguardWallpaperController.mWallpaperEventNotifier.mIsThemeApplying = false;
                                final SemWallpaperColors wallpaperColors4 = keyguardWallpaperController.getWallpaperColors(false);
                                final int i17 = WallpaperUtils.sCurrentWhich;
                                keyguardWallpaperController.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda10
                                    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
                                    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
                                    /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
                                    /* JADX WARN: Removed duplicated region for block: B:45:0x0045  */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final void run() {
                                        SettingsHelper settingsHelper2;
                                        long j;
                                        SemWallpaperColors.Item item;
                                        SemWallpaperColors.Item item2;
                                        SemWallpaperColors.Item item3;
                                        KeyguardWallpaperController keyguardWallpaperController3 = KeyguardWallpaperController.this;
                                        SemWallpaperColors semWallpaperColors = wallpaperColors4;
                                        int i73 = i17;
                                        KeyguardWallpaperColors keyguardWallpaperColors = keyguardWallpaperController3.mWallpaperEventNotifier.mKeyguardWallpaperColors;
                                        keyguardWallpaperColors.getClass();
                                        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                                        boolean z22 = LsRune.WALLPAPER_SUB_WATCHFACE;
                                        long j2 = 0;
                                        if (z22 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                                            if ((i73 & 2) == 0 && (i73 & 16) == 0 && (i73 & 32) == 0) {
                                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                            }
                                            SparseArray sparseArray = !WallpaperUtils.isCoverScreen(i73) ? keyguardWallpaperColors.mSemWallpaperColorsCover : keyguardWallpaperColors.mSemWallpaperColors;
                                            settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                            if (semWallpaperColors == null) {
                                                StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSemWallpaperColors: userId = ", currentUser, ", colors = ");
                                                m1m.append(semWallpaperColors.toSimpleString());
                                                Log.d("KeyguardWallpaperColors", m1m.toString());
                                                ColorData colorData = new ColorData(semWallpaperColors, settingsHelper2.isOpenThemeLook(), settingsHelper2.isOpenThemeLockWallpaper(), false);
                                                j = keyguardWallpaperColors.checkUpdates((ColorData) sparseArray.get(currentUser), colorData);
                                                sparseArray.put(currentUser, colorData);
                                            } else {
                                                j = 0;
                                            }
                                            if ((z22 || !WallpaperUtils.isCoverScreen(i73)) && j != 0) {
                                                Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                                if (j != 0 && semWallpaperColors != null) {
                                                    if ((j & 512) != 0 && (item3 = semWallpaperColors.get(512L)) != null) {
                                                        int fontColor4222 = item3.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor4222, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor4222;
                                                    }
                                                    if ((j & 16) != 0 && (item2 = semWallpaperColors.get(16L)) != null) {
                                                        int fontColor22222 = item2.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor22222, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor22222;
                                                    }
                                                    if ((j & 256) != 0 && (item = semWallpaperColors.get(256L)) != null) {
                                                        int fontColor32222 = item.getFontColor();
                                                        Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor32222, -2);
                                                        settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor32222;
                                                    }
                                                }
                                            }
                                            j2 = j;
                                        } else {
                                            if ((i73 & 2) != 2) {
                                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setSemWallpaperColors: Nothing to do for which = ", i73, "KeyguardWallpaperColors");
                                            }
                                            if (!WallpaperUtils.isCoverScreen(i73)) {
                                            }
                                            settingsHelper2 = keyguardWallpaperColors.mSettingsHelper;
                                            if (semWallpaperColors == null) {
                                            }
                                            if (z22) {
                                            }
                                            Log.d("KeyguardWallpaperColors", "writeSettingsWallpaperColors() flags = " + j);
                                            if (j != 0) {
                                                if ((j & 512) != 0) {
                                                    int fontColor42222 = item3.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_wallpaper", fontColor42222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_wallpaper").mIntValue = fontColor42222;
                                                }
                                                if ((j & 16) != 0) {
                                                    int fontColor222222 = item2.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_statusbar", fontColor222222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_statusbar").mIntValue = fontColor222222;
                                                }
                                                if ((j & 256) != 0) {
                                                    int fontColor322222 = item.getFontColor();
                                                    Settings.System.putIntForUser(settingsHelper2.mContext.getContentResolver(), "white_lockscreen_navigationbar", fontColor322222, -2);
                                                    settingsHelper2.mItemLists.get("white_lockscreen_navigationbar").mIntValue = fontColor322222;
                                                }
                                            }
                                            j2 = j;
                                        }
                                        keyguardWallpaperController3.mWallpaperEventNotifier.update(WallpaperUtils.isCoverScreen(i73), j2, semWallpaperColors);
                                        SystemUIWallpaperBase systemUIWallpaperBase32 = keyguardWallpaperController3.mWallpaperView;
                                        if (systemUIWallpaperBase32 != null) {
                                            systemUIWallpaperBase32.updateThumbnail();
                                        }
                                    }
                                });
                                return;
                            case 732:
                                keyguardWallpaperController.handleDlsViewMode(message.arg1, false);
                                return;
                            case 733:
                                keyguardWallpaperController.handleColorThemeStateChanged(false);
                                if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                                    keyguardWallpaperController.handleColorThemeStateChanged(true);
                                    return;
                                }
                                return;
                            default:
                                switch (i3) {
                                    case 833:
                                        SystemUIWallpaperBase systemUIWallpaperBase9 = keyguardWallpaperController.mWallpaperView;
                                        if (systemUIWallpaperBase9 != null) {
                                            systemUIWallpaperBase9.dispatchWallpaperCommand("android.wallpaper.goingtosleep");
                                        }
                                        keyguardWallpaperController.mIsGoingToSleep = true;
                                        WallpaperUtils.sDrawState = false;
                                        if (!keyguardWallpaperController.mIsLockscreenDisabled && (systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView) != null) {
                                            systemUIWallpaperBase.updateDrawState(false);
                                        }
                                        keyguardWallpaperController.onTransitionAod(true);
                                        return;
                                    case 834:
                                        SystemUIWallpaperBase systemUIWallpaperBase10 = keyguardWallpaperController.mWallpaperView;
                                        if (systemUIWallpaperBase10 != null) {
                                            systemUIWallpaperBase10.dispatchWallpaperCommand("android.wallpaper.wakingup");
                                        }
                                        keyguardWallpaperController.mIsGoingToSleep = false;
                                        keyguardWallpaperController.mScreenOn = true;
                                        if (keyguardWallpaperController.mRootView == null) {
                                            return;
                                        }
                                        WallpaperUtils.sDrawState = true;
                                        if (!keyguardWallpaperController.mIsLockscreenDisabled && (systemUIWallpaperBase2 = keyguardWallpaperController.mWallpaperView) != null) {
                                            systemUIWallpaperBase2.updateDrawState(true);
                                        }
                                        int visibility = keyguardWallpaperController.mRootView.getVisibility();
                                        if (visibility == 0) {
                                            keyguardWallpaperController.onResume();
                                        }
                                        StringBuilder sb = new StringBuilder("onStartedWakingUp() mWallpaperView:");
                                        sb.append(keyguardWallpaperController.mWallpaperView);
                                        sb.append(", visibility:");
                                        sb.append(visibility);
                                        sb.append(", mIsKeyguardShowing:");
                                        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, keyguardWallpaperController.mIsKeyguardShowing, "KeyguardWallpaperController");
                                        if (keyguardWallpaperController.mWallpaperView == null && !keyguardWallpaperController.mIsLockscreenDisabled && !WallpaperUtils.isLiveWallpaperEnabled() && (!LsRune.WALLPAPER_SUB_WATCHFACE || !KeyguardWallpaperController.isSubDisplay())) {
                                            keyguardWallpaperController.handleWallpaperTypeChanged(keyguardWallpaperController.getLockWallpaperType(true));
                                        }
                                        keyguardWallpaperController.onTransitionAod(false);
                                        return;
                                    case 835:
                                        keyguardWallpaperController.mIsGoingToSleep = false;
                                        keyguardWallpaperController.mScreenOn = false;
                                        keyguardWallpaperController.onPause();
                                        return;
                                    case 836:
                                        keyguardWallpaperController.mScreenOn = true;
                                        return;
                                    case 837:
                                        if (keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperTransparent() == 1) {
                                            return;
                                        }
                                        Log.d("KeyguardWallpaperController", "MSG_FINGERPRINT_TOUCH_DOWN");
                                        if (keyguardWallpaperController.mScreenOn) {
                                            keyguardWallpaperController.mIsFingerPrintTouchDown = true;
                                            keyguardWallpaperController.setThumbnailVisibility(0);
                                            return;
                                        }
                                        return;
                                    case 838:
                                        if (keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperTransparent() == 1) {
                                            return;
                                        }
                                        Log.d("KeyguardWallpaperController", "MSG_FINGERPRINT_TOUCH_UP");
                                        keyguardWallpaperController.mIsFingerPrintTouchDown = false;
                                        keyguardWallpaperController.setThumbnailVisibility(4);
                                        return;
                                    default:
                                        switch (i3) {
                                            case 901:
                                                break;
                                            case VpnErrorValues.ERROR_USB_TETHERING_FAILED /* 902 */:
                                                boolean booleanValue2 = ((Boolean) message.obj).booleanValue();
                                                Log.d("KeyguardWallpaperController", "onLiveWallpaperChanged");
                                                WallpaperUtils.loadLiveWallpaperSettings(keyguardWallpaperController.mCurrentUserId, keyguardWallpaperController.mContext);
                                                boolean isSubDisplay = KeyguardWallpaperController.isSubDisplay();
                                                if (((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).isDynamicWallpaperEnabled() && WallpaperUtils.isLiveWallpaperEnabled(booleanValue2)) {
                                                    ((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).onLockWallpaperChanged(booleanValue2 ? 1 : 0);
                                                }
                                                if (!booleanValue2) {
                                                    int lockscreenWallpaperType = keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperType(0);
                                                    RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("new = ", lockscreenWallpaperType, " , old = "), keyguardWallpaperController.mOldLockScreenWallpaperSettingsValue, "KeyguardWallpaperController");
                                                    if (keyguardWallpaperController.mOldLockScreenWallpaperSettingsValue != lockscreenWallpaperType) {
                                                        keyguardWallpaperController.mOldLockScreenWallpaperSettingsValue = lockscreenWallpaperType;
                                                        if (!isSubDisplay) {
                                                            keyguardWallpaperController.sendUpdateWallpaperMessage(901);
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    int lockscreenWallpaperType2 = keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperType(16);
                                                    RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("new = ", lockscreenWallpaperType2, " , old = "), keyguardWallpaperController.mOldLockScreenWallpaperSubSettingsValue, "KeyguardWallpaperController");
                                                    if (keyguardWallpaperController.mOldLockScreenWallpaperSubSettingsValue != lockscreenWallpaperType2) {
                                                        keyguardWallpaperController.mOldLockScreenWallpaperSubSettingsValue = lockscreenWallpaperType2;
                                                        if (isSubDisplay) {
                                                            keyguardWallpaperController.sendUpdateWallpaperMessage(901);
                                                            break;
                                                        }
                                                    }
                                                }
                                                break;
                                            case 903:
                                                keyguardWallpaperController.handleAdaptiveColorModeChanged(false);
                                                break;
                                            case 904:
                                                boolean booleanValue3 = ((Boolean) message.obj).booleanValue();
                                                int i18 = booleanValue3 ? 16 : 4;
                                                if (!booleanValue3 || !DeviceType.isTablet()) {
                                                    keyguardWallpaperController.mWallpaperAnalytics.updateWallpaperStatus(i18 | 2);
                                                    keyguardWallpaperController.forceBroadcastWhiteKeyguardWallpaper(keyguardWallpaperController.mSettingsHelper.getLockscreenWallpaperTransparent());
                                                    WallpaperEventNotifier wallpaperEventNotifier3 = keyguardWallpaperController.mWallpaperEventNotifier;
                                                    WallpaperManager wallpaperManager3 = wallpaperEventNotifier3.mWallpaperManager;
                                                    wallpaperEventNotifier3.setCurStatusFlag(false, wallpaperManager3 != null ? wallpaperManager3.semGetWallpaperColors(2) : null);
                                                    break;
                                                }
                                                break;
                                            case 905:
                                                keyguardWallpaperController.disableRotateIfNeeded();
                                                break;
                                            case 906:
                                                Bundle data4 = message.getData();
                                                if (data4 != null) {
                                                    if (keyguardWallpaperController.mPluginWallpaperManager != null) {
                                                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("handleColorAreasChanged : which = ", data4.getInt("which", 2), "KeyguardWallpaperController");
                                                        keyguardWallpaperController.mPluginWallpaperManager.getClass();
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
                                                switch (i3) {
                                                    case 1000:
                                                        boolean booleanValue4 = ((Boolean) message.obj).booleanValue();
                                                        int i19 = WallpaperUtils.sCurrentWhich;
                                                        int i20 = booleanValue4 ? (i19 | 8) & (-5) : (i19 | 4) & (-9);
                                                        WallpaperUtils.sCurrentWhich = i20;
                                                        int lockWallpaperType5 = keyguardWallpaperController.getLockWallpaperType(true);
                                                        RecyclerView$$ExternalSyntheticOutline0.m46m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("handleDesktopModeChanged : ", booleanValue4, ", which = ", i20, " , type = "), lockWallpaperType5, "KeyguardWallpaperController");
                                                        keyguardWallpaperController.mWallpaperManager.semRequestWallpaperColorsAnalysis(i20);
                                                        keyguardWallpaperController.handleWallpaperTypeChanged(lockWallpaperType5);
                                                        break;
                                                    case 1001:
                                                        Bundle data5 = message.getData();
                                                        if (keyguardWallpaperController.mDlsRestoreDispatcher == null) {
                                                            keyguardWallpaperController.mDlsRestoreDispatcher = new DlsRestoreDispatcher(keyguardWallpaperController.mContext, keyguardWallpaperController.mWallpaperLogger, keyguardWallpaperController.mPluginLockUtils);
                                                        }
                                                        keyguardWallpaperController.mDlsRestoreDispatcher.mOnRestoreDlsListener = keyguardWallpaperController.new C36678();
                                                        DlsRestoreDispatcher dlsRestoreDispatcher = keyguardWallpaperController.mDlsRestoreDispatcher;
                                                        dlsRestoreDispatcher.getClass();
                                                        Message message2 = new Message();
                                                        message2.what = 0;
                                                        message2.setData(data5);
                                                        if (dlsRestoreDispatcher.mHandler == null) {
                                                            dlsRestoreDispatcher.mHandler = new DlsRestoreDispatcher.DlsRestoreHandler(dlsRestoreDispatcher, i5);
                                                        }
                                                        dlsRestoreDispatcher.mHandler.sendMessage(message2);
                                                        break;
                                                    case 1002:
                                                        break;
                                                    case 1003:
                                                        WallpaperAnalytics wallpaperAnalytics = keyguardWallpaperController.mWallpaperAnalytics;
                                                        if (wallpaperAnalytics != null) {
                                                            wallpaperAnalytics.updateWallpaperStatus(message.arg1);
                                                            break;
                                                        }
                                                        break;
                                                    default:
                                                        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("handleMessage: unsupported command ("), message.what, ")", "KeyguardWallpaperController");
                                                        break;
                                                }
                                        }
                                        return;
                                }
                        }
                }
                int wallpaperViewType = keyguardWallpaperController.getWallpaperViewType();
                if (wallpaperViewType == -1 && !WallpaperUtils.isLiveWallpaperEnabled()) {
                    Log.w("KeyguardWallpaperController", "MSG_WALLPAPER_CHANGED_BY_LIVE_WALLPAPERS canceled.");
                } else if (KeyguardWallpaperController.isMatching(wallpaperViewType, keyguardWallpaperController.mWallpaperView)) {
                    keyguardWallpaperController.handleWallpaperResourceUpdated();
                } else {
                    keyguardWallpaperController.handleWallpaperTypeChanged(wallpaperViewType);
                }
            }
        };
        this.mWorkHandler = r9;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mPluginLockUtils = pluginLockUtils;
        this.mSettingsHelper = settingsHelper;
        WallpaperUtils.mSettingsHelper = settingsHelper;
        WallpaperUtils.loadDeviceState(context.getUserId(), context);
        this.mWallpaperEventNotifier = wallpaperEventNotifier;
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mLockPatternUtils = new LockPatternUtils(context);
        IWallpaperManager asInterface = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        this.mService = asInterface;
        this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) context.getSystemService("DisplaySolution");
        this.mWallpaperChangeNotifier = wallpaperChangeNotifier;
        WallpaperAnalytics wallpaperAnalytics = new WallpaperAnalytics(context, pluginWallpaperManager, settingsHelper);
        this.mWallpaperAnalytics = wallpaperAnalytics;
        SharedPreferences sharedPreferences = wallpaperAnalytics.mContext.getSharedPreferences("wallpaper_pref", 0);
        int i = sharedPreferences.getInt("version", -1);
        if (i >= 1) {
            z = true;
        } else {
            Log.i("WallpaperAnalytics", "migrateIfNeeds: perform migration. from=" + i + ", to=1");
            wallpaperAnalytics.updateWallpaperStatus(6);
            wallpaperAnalytics.updateWallpaperStatus(5);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                wallpaperAnalytics.updateWallpaperStatus(18);
                wallpaperAnalytics.updateWallpaperStatus(17);
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            z = true;
            edit.putInt("version", 1);
            edit.apply();
        }
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController.7
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("which", 0);
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("onReceive: system wallpaper has been changed. which = ", intExtra, "KeyguardWallpaperController");
                if (intExtra > 0) {
                    Message obtainMessage = obtainMessage(1003);
                    obtainMessage.arg1 = intExtra;
                    sendMessage(obtainMessage);
                }
            }
        }, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
        this.mOldTransparentType = settingsHelper.getLockscreenWallpaperTransparent();
        this.mBlurFilter = new SemGfxImageFilter();
        this.mOldLockScreenWallpaperSettingsValue = settingsHelper.getLockscreenWallpaperType(0);
        this.mOldLockScreenWallpaperSubSettingsValue = settingsHelper.getLockscreenWallpaperType(16);
        if (asInterface == null) {
            Log.e("KeyguardWallpaperController", "WallpaperManagerService is not ready yet! Just return here!");
            return;
        }
        keyguardWallpaperEventHandler.mEventConsumer = new Consumer() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                r9.sendMessage((Message) obj);
            }
        };
        new Thread(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 0), "LockWallpaperCB").start();
        ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).registerCallback(r7);
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mLockWallpaper;
        if (pluginLockWallpaper != null) {
            pluginLockWallpaper.mWallpaperUpdateCallback = this;
        }
        sendUpdateWallpaperMessage(607);
        sController = this;
        boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z2) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda3
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z3) {
                    KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                    keyguardWallpaperController.getClass();
                    keyguardWallpaperController.printLognAddHistory("onFolderStateChanged: isOpened = " + z3);
                    int i2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE ? !z3 ? 18 : 6 : 2;
                    if (WallpaperUtils.sCurrentWhich != i2) {
                        TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("onFolderStateChanged: which = ", i2, ", previous which = "), WallpaperUtils.sCurrentWhich, "KeyguardWallpaperController");
                        WallpaperUtils.sCurrentWhich = i2;
                        keyguardWallpaperController.mIsPendingTypeChange = false;
                        if (keyguardWallpaperController.mRunnableCleanUp != null) {
                            Log.d("KeyguardWallpaperController", "onFolderStateChanged, remove clean-up runnable");
                            keyguardWallpaperController.mMainHandler.removeCallbacks(keyguardWallpaperController.mRunnableCleanUp);
                        }
                        if (keyguardWallpaperController.mRunnableSetBackground != null) {
                            Log.d("KeyguardWallpaperController", "onFolderStateChanged, remove set background runnable");
                            keyguardWallpaperController.mMainHandler.removeCallbacks(keyguardWallpaperController.mRunnableSetBackground);
                        }
                        if (keyguardWallpaperController.mWorkHandler.hasMessages(612)) {
                            Log.d("KeyguardWallpaperController", "onFolderStateChanged, remove MSG_WALLPAPER_DISPLAY_CHANGED");
                            keyguardWallpaperController.mWorkHandler.removeMessages(612);
                        }
                        keyguardWallpaperController.sendUpdateWallpaperMessage(612, true, null);
                    }
                }
            }, 1001);
        }
        try {
            boolean z3 = asInterface.semGetWallpaperType(6) == 3 ? z : false;
            if (!z3 && z2 && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                z3 = asInterface.semGetWallpaperType(18) == 3 ? z : false;
            }
            if (!z3 || MultiPackDispatcher.enableDlsIfDisabled(context)) {
                return;
            }
            Log.e("KeyguardWallpaperController", "Failed to enable DLS.");
        } catch (RemoteException e) {
            Log.e("KeyguardWallpaperController", "System dead?" + e);
        }
    }

    public static boolean isMatching(int i, SystemUIWallpaperBase systemUIWallpaperBase) {
        if (systemUIWallpaperBase == null) {
            return false;
        }
        if (i != -1) {
            if (i != 0) {
                if (i == 1 || i == 2) {
                    if (systemUIWallpaperBase instanceof KeyguardMotionWallpaper) {
                        return true;
                    }
                } else if (i != 4) {
                    if (i != 1000) {
                        if (i != 7) {
                            if (i != 8) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("isMatching: Invalid type. type = ", i, "KeyguardWallpaperController");
                            } else if (systemUIWallpaperBase instanceof KeyguardVideoWallpaper) {
                                return true;
                            }
                        } else if (systemUIWallpaperBase instanceof KeyguardLiveWallpaper) {
                            return true;
                        }
                    }
                } else if (systemUIWallpaperBase instanceof KeyguardAnimatedWallpaper) {
                    return true;
                }
            }
        } else if (!LsRune.WALLPAPER_SUB_WATCHFACE) {
            return false;
        }
        return systemUIWallpaperBase instanceof KeyguardImageWallpaper;
    }

    public static boolean isSubDisplay() {
        return (WallpaperUtils.sCurrentWhich & 16) != 0;
    }

    public final void applyBlur(int i) {
        int i2 = 1;
        if (this.mDlsViewMode == 1) {
            Log.w("KeyguardWallpaperController", "applyBlur: ignored by DLS");
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showBlurredViewIfNeededOnUiThread();
        } else {
            this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, i2));
        }
        int i3 = 0;
        if (this.mSettingsHelper.isReduceTransparencyEnabled()) {
            i = 0;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            applyBlurInternalOnUiThread(i);
        } else {
            this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda4(this, i, i3));
        }
    }

    public final void applyBlurFilter(int i) {
        Object obj = this.mWallpaperView;
        if (obj != null) {
            KeyguardWallpaperController$$ExternalSyntheticLambda4 keyguardWallpaperController$$ExternalSyntheticLambda4 = new KeyguardWallpaperController$$ExternalSyntheticLambda4((View) obj, i);
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                keyguardWallpaperController$$ExternalSyntheticLambda4.run();
            } else {
                this.mMainHandler.postAtFrontOfQueue(keyguardWallpaperController$$ExternalSyntheticLambda4);
            }
        }
    }

    public final void applyBlurInternalOnUiThread(int i) {
        SystemUIWallpaperBase systemUIWallpaperBase;
        Assert.isMainThread();
        Log.d("KeyguardWallpaperController", "applyBlurInternal: amount = " + i);
        SystemUIWallpaperBase systemUIWallpaperBase2 = this.mWallpaperView;
        if (systemUIWallpaperBase2 != null) {
            systemUIWallpaperBase2.updateBlurState(((float) i) != 0.0f);
        }
        if (!LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) {
            if (!LsRune.WALLPAPER_BLUR || (systemUIWallpaperBase = this.mWallpaperView) == null || (systemUIWallpaperBase instanceof KeyguardVideoWallpaper)) {
                return;
            }
            applyBlurFilter(i);
            return;
        }
        KeyguardBlurredWallpaper keyguardBlurredWallpaper = this.mBlurredView;
        if (keyguardBlurredWallpaper != null && this.mIsBlurredViewAdded) {
            keyguardBlurredWallpaper.applyBlur(i);
        } else if (this.mWallpaperView != null) {
            applyBlurFilter(i);
        }
        WallpaperUtils.sLastAmount = i;
    }

    public final void cleanUp(boolean z) {
        if (WallpaperUtils.isAODShowLockWallpaperAndLockDisabled(KeyguardUpdateMonitor.getCurrentUser(), this.mContext)) {
            Log.d("KeyguardWallpaperController", "cleanUp: DO NOT clean up keyguard wallpaper");
        } else if (this.mWallpaperView != null) {
            if (this.mRunnableCleanUp != null) {
                Log.i("KeyguardWallpaperController", "cleanUpOnUiThread, remove runnable");
                this.mMainHandler.removeCallbacks(this.mRunnableCleanUp);
            }
            KeyguardWallpaperController$$ExternalSyntheticLambda9 keyguardWallpaperController$$ExternalSyntheticLambda9 = new KeyguardWallpaperController$$ExternalSyntheticLambda9(this, z, 0);
            this.mRunnableCleanUp = keyguardWallpaperController$$ExternalSyntheticLambda9;
            this.mMainHandler.postAtFrontOfQueue(keyguardWallpaperController$$ExternalSyntheticLambda9);
        } else {
            Log.d("KeyguardWallpaperController", "cleanUp() is cancelled because view is already null");
        }
        if (this.mIsLockscreenDisabled || !z) {
            return;
        }
        this.mIsLockscreenDisabled = true;
    }

    public final void cleanUpBlurredView() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            cleanUpBlurredViewOnUiThread();
        } else {
            this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 4));
        }
    }

    public final void cleanUpBlurredViewOnUiThread() {
        Assert.isMainThread();
        Log.i("KeyguardWallpaperController", "cleanUpBlurredView: mBlurredView = " + this.mBlurredView);
        KeyguardBlurredWallpaper keyguardBlurredWallpaper = this.mBlurredView;
        if (keyguardBlurredWallpaper != null && this.mIsBlurredViewAdded) {
            keyguardBlurredWallpaper.cleanUp();
            this.mRootView.removeView(this.mBlurredView);
            this.mBlurredView = null;
        }
        this.mIsBlurredViewAdded = false;
    }

    public final void disableRotateIfNeeded() {
        this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 10));
    }

    public final void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        SemWallpaperColors semWallpaperColors;
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
                        KeyboardUI$$ExternalSyntheticOutline0.m134m(sb, debugLog.text, printWriter2);
                    }
                });
            }
            KeyguardWallpaperColors keyguardWallpaperColors = wallpaperEventNotifier.mKeyguardWallpaperColors;
            keyguardWallpaperColors.getClass();
            printWriter.println("KeyguardWallpaperColors:");
            try {
                StringBuilder sb = new StringBuilder("\tLast wallpaper color = ");
                try {
                    semWallpaperColors = ((ColorData) keyguardWallpaperColors.mSemWallpaperColors.get(KeyguardUpdateMonitor.getCurrentUser())).colors;
                } catch (NullPointerException unused) {
                    semWallpaperColors = null;
                }
                sb.append(semWallpaperColors.toSimpleString());
                sb.append("\n");
                printWriter.println(sb.toString());
            } catch (Exception e) {
                printWriter.println("\nDump error: " + e.getMessage() + "\n");
            }
        }
        MultiPackDispatcher multiPackDispatcher = this.mMultiPackDispatcher;
        if (multiPackDispatcher != null) {
            StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "MultiPackDispatcher", "mLastUri = ");
            m75m.append(multiPackDispatcher.mLastUri);
            printWriter.println(m75m.toString());
        }
        boolean z = WallpaperUtils.mIsEmergencyMode;
        printWriter.println("Dump of WallpaperUtils: ");
        printWriter.print("  isAdaptiveColorMode: ");
        printWriter.println(WallpaperUtils.mSettingsHelper.isAdaptiveColorMode());
        printWriter.print("  isExternalLiveWallpaper: ");
        printWriter.println(WallpaperUtils.isExternalLiveWallpaper());
        printWriter.print("  Emergency mode: ");
        printWriter.println(WallpaperUtils.mIsEmergencyMode);
        printWriter.print("  UltraPowerSavingMode: ");
        printWriter.println(WallpaperUtils.mIsUltraPowerSavingMode);
        printWriter.print("  DeXMode: ");
        printWriter.println(false);
        printWriter.print("  Type: ");
        printWriter.println(WallpaperUtils.sWallpaperType);
        printWriter.print("  isVideoWallpaper: ");
        printWriter.println(WallpaperUtils.isVideoWallpaper());
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            printWriter.print("  sCachedWallpaperColors(FLAG_DISPLAY_PHONE): ");
            SparseArray sparseArray = WallpaperUtils.sCachedWallpaperColors;
            printWriter.println(sparseArray.get(4));
            printWriter.print("  sCachedWallpaperColors(FLAG_DISPLAY_SUB): ");
            printWriter.println(sparseArray.get(16));
        }
        printWriter.println();
    }

    public final void forceBroadcastWhiteKeyguardWallpaper(int i) {
        TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("forceBroadcastWhiteKeyguardWallpaper: cur = ", i, " , old = "), this.mOldTransparentType, "KeyguardWallpaperController");
        if (this.mOldTransparentType == 2 && i != 2) {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            settingsHelper.getClass();
            settingsHelper.broadcastChange(Settings.System.getUriFor("white_lockscreen_wallpaper"));
        }
        this.mOldTransparentType = i;
    }

    public final int getFbeWallpaperType(int i) {
        if (!(KeyguardUpdateMonitor.getCurrentUser() == 0) || !LsRune.KEYGUARD_FBE || !isPluginLockFbeCondition()) {
            return i;
        }
        int screenId = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
        return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperAvailable(screenId) ? ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperVideo(screenId) ? 8 : 0 : i;
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0060, code lost:
    
        if (((com.android.systemui.pluginlock.component.PluginLockWallpaper.PluginLockWallpaperData) ((java.util.ArrayList) r8.mWallpaperDataList).get(r8.getScreenType())).mType != 2) goto L19;
     */
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
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("\n - isIncludePluginLock: ", z, "\n - mService: ");
        m49m.append(this.mService);
        IWallpaperManager iWallpaperManager = this.mService;
        int i3 = -1;
        if (iWallpaperManager != null) {
            try {
                if (z) {
                    boolean isDynamicWallpaperEnabled = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled();
                    m49m.append("\n - isDlsWallpaperEnabled: ");
                    m49m.append(isDynamicWallpaperEnabled);
                    try {
                        if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                            PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).mMediator).mLockWallpaper;
                            if (pluginLockWallpaper != null) {
                            }
                            i2 = 0;
                            m49m.append("\n - type[DLS]: ");
                            m49m.append(i2);
                        } else {
                            i3 = this.mService.semGetWallpaperType(i);
                            if (isPluginLockFbeCondition()) {
                                i2 = getFbeWallpaperType(i3);
                                m49m.append("\n - type[from FBE]: ");
                                m49m.append(i2);
                            } else if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled(0) || i3 != 3) {
                                m49m.append("\n - type[from WMS]: ");
                                m49m.append(i3);
                            } else {
                                if (KeyguardUpdateMonitor.getCurrentUser() == 0) {
                                    int screenId = PluginWallpaperManager.getScreenId(WallpaperUtils.sCurrentWhich);
                                    if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperAvailable(screenId)) {
                                        if (!((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperVideo(screenId)) {
                                            i2 = 0;
                                        }
                                        m49m.append("\n - type[force FBE]: ");
                                        m49m.append(i2);
                                    }
                                }
                                i2 = i3;
                                m49m.append("\n - type[force FBE]: ");
                                m49m.append(i2);
                            }
                        }
                        i3 = i2;
                    } catch (RemoteException e) {
                        e = e;
                        i3 = i2;
                        Log.e("KeyguardWallpaperController", "System dead?" + e);
                        m49m.append("\n - WallpaperUtils.getCurrentWhich(): ");
                        m49m.append(WallpaperUtils.sCurrentWhich);
                        Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + m49m.toString());
                        return i3;
                    }
                } else {
                    i3 = iWallpaperManager.semGetWallpaperType(i);
                    m49m.append("\n - type[from WMS]: ");
                    m49m.append(i3);
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        }
        m49m.append("\n - WallpaperUtils.getCurrentWhich(): ");
        m49m.append(WallpaperUtils.sCurrentWhich);
        Log.d("KeyguardWallpaperController", "getLockWallpaperType: " + m49m.toString());
        return i3;
    }

    public final Bitmap getWallpaperBitmap() {
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            return systemUIWallpaperBase.getWallpaperBitmap();
        }
        return null;
    }

    public final SemWallpaperColors getWallpaperColors(boolean z) {
        int i;
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            if (z || isSubDisplay()) {
                i = 17;
            }
            i = 6;
        } else {
            if (isSubDisplay()) {
                i = 18;
            }
            i = 6;
        }
        return this.mWallpaperManager.semGetWallpaperColors(i);
    }

    public final int getWallpaperViewType() {
        int i;
        if (this.mWallpaperView == null || WallpaperUtils.isLiveWallpaperEnabled()) {
            i = -1;
        } else {
            SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
            i = systemUIWallpaperBase instanceof KeyguardMotionWallpaper ? 1 : systemUIWallpaperBase instanceof KeyguardAnimatedWallpaper ? 4 : systemUIWallpaperBase instanceof KeyguardLiveWallpaper ? 7 : systemUIWallpaperBase instanceof KeyguardVideoWallpaper ? 8 : 0;
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("getWallpaperViewType: type = ", i, "KeyguardWallpaperController");
        return i;
    }

    public final void handleAdaptiveColorModeChanged(boolean z) {
        SemWallpaperColors semWallpaperColors;
        boolean z2 = z ? WallpaperUtils.mIsAdaptiveColorModeSub : WallpaperUtils.mIsAdaptiveColorMode;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        boolean isAdaptiveColorMode = z ? settingsHelper.mItemLists.get("lock_adaptive_color_sub").getIntValue() != 0 : settingsHelper.isAdaptiveColorMode();
        if (z2 != isAdaptiveColorMode) {
            if (!z) {
                Context context = this.mContext;
                boolean isAdaptiveColorMode2 = this.mSettingsHelper.isAdaptiveColorMode();
                context.getSharedPreferences("lockscreen_pref", 0).edit().putInt("9010", isAdaptiveColorMode2 ? 1 : 0).apply();
                if (isAdaptiveColorMode2) {
                    context.getSharedPreferences("lockscreen_pref", 0).edit().putString("9009", "Adaptive color (Default)").apply();
                }
            }
            WallpaperEventNotifier wallpaperEventNotifier = this.mWallpaperEventNotifier;
            KeyguardWallpaperColors keyguardWallpaperColors = wallpaperEventNotifier.mKeyguardWallpaperColors;
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            keyguardWallpaperColors.getClass();
            try {
                semWallpaperColors = z ? ((ColorData) keyguardWallpaperColors.mSemWallpaperColorsCover.get(currentUser)).colors : ((ColorData) keyguardWallpaperColors.mSemWallpaperColors.get(currentUser)).colors;
            } catch (NullPointerException unused) {
                semWallpaperColors = null;
            }
            wallpaperEventNotifier.update(z, 2L, semWallpaperColors);
        }
        if (z) {
            WallpaperUtils.mIsAdaptiveColorModeSub = isAdaptiveColorMode;
        } else {
            WallpaperUtils.mIsAdaptiveColorMode = isAdaptiveColorMode;
        }
    }

    public final void handleColorThemeStateChanged(boolean z) {
        boolean isColorThemeEnabled$1 = this.mSettingsHelper.isColorThemeEnabled$1();
        SettingsHelper.ItemMap itemMap = this.mSettingsHelper.mItemLists;
        int intValue = z ? itemMap.get("lock_adaptive_color_sub").getIntValue() : itemMap.get("lock_adaptive_color").getIntValue();
        if (isColorThemeEnabled$1) {
            if ((intValue & 2) == 0) {
                this.mSettingsHelper.setAdaptiveColorMode(intValue | 2, z);
            }
        } else if ((intValue & 2) != 0) {
            this.mSettingsHelper.setAdaptiveColorMode(intValue & (-3), z);
        }
        this.mWallpaperEventNotifier.update(z, 1024L, getWallpaperColors(z));
    }

    public final void handleDlsViewMode(int i, boolean z) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("handleDlsViewMode: mode = ", i, "KeyguardWallpaperController");
        if (z || this.mDlsViewMode != i) {
            this.mDlsViewMode = i;
            int i2 = 0;
            int i3 = (i != 1 || this.mSettingsHelper.isReduceTransparencyEnabled()) ? 0 : 100;
            if (Looper.myLooper() == Looper.getMainLooper()) {
                applyBlurInternalOnUiThread(i3);
            } else {
                this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda4(this, i3, i2));
            }
        }
    }

    public final void handleDlsViewModeDelayed(int i) {
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("handleWallpaperTypeChanged mDlsViewMode: "), this.mDlsViewMode, "KeyguardWallpaperController");
        if (this.mDlsViewMode == 1) {
            postDelayed(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 5), i);
        }
    }

    public final void handleWallpaperResourceUpdated() {
        Log.d("KeyguardWallpaperController", "handleWallpaperResourceUpdated");
        boolean z = true;
        if (WallpaperUtils.isAODShowLockWallpaperAndLockDisabled(KeyguardUpdateMonitor.getCurrentUser(), this.mContext) || !(this.mIsLockscreenDisabled || WallpaperUtils.isLiveWallpaperEnabled() || this.mRootView == null || this.mWallpaperView == null)) {
            if (WallpaperUtils.isExternalLiveWallpaper()) {
                setBackground(null, null, false, true, true);
            } else {
                if (this.mRunnableUpdate != null) {
                    Log.i("KeyguardWallpaperController", "handleWallpaperResourceUpdated, remove update runnable");
                    this.mMainHandler.removeCallbacks(this.mRunnableUpdate);
                }
                this.mObserver.updateState(1);
                KeyguardWallpaperController$$ExternalSyntheticLambda2 keyguardWallpaperController$$ExternalSyntheticLambda2 = new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 13);
                this.mRunnableUpdate = keyguardWallpaperController$$ExternalSyntheticLambda2;
                this.mMainHandler.post(keyguardWallpaperController$$ExternalSyntheticLambda2);
            }
            if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                this.mWallpaperChanged = true;
            }
            this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : 4) | 2);
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("handleWallpaperResourceUpdated: mIsLockscreenDisabled = " + this.mIsLockscreenDisabled);
            sb.append(", isLiveWallpaperEnabled = " + WallpaperUtils.isLiveWallpaperEnabled());
            StringBuilder sb2 = new StringBuilder(", mRootView == null ? ");
            sb2.append(this.mRootView == null);
            sb.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder(", mWallpaperView == null ? ");
            if (this.mWallpaperView != null) {
                z = false;
            }
            sb3.append(z);
            sb.append(sb3.toString());
            printLognAddHistory(sb.toString());
        } catch (NullPointerException unused) {
            Log.d("KeyguardWallpaperController", "handleWallpaperResourceUpdated: Exception while printing log.");
        }
        this.mObserver.updateState(3);
        if (WallpaperUtils.isExternalLiveWallpaper()) {
            setBackground(null, null, false, true, true);
        }
    }

    public final void handleWallpaperTypeChanged(int i) {
        if (this.mRootView == null) {
            printLognAddHistory("handleWallpaperTypeChanged: mRootView is null.");
        } else {
            handleWallpaperTypeChanged(i, false);
        }
    }

    public final void hideLockOnlyLiveWallpaperImmediately() {
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase == null || !(systemUIWallpaperBase instanceof KeyguardLiveWallpaper)) {
            return;
        }
        Log.d("KeyguardWallpaperController", "hideLockOnlyLiveWallpaperImmediately");
        KeyguardLiveWallpaper.WallpaperConnection wallpaperConnection = ((KeyguardLiveWallpaper) this.mWallpaperView).mWallpaperConnection;
        if (wallpaperConnection != null) {
            wallpaperConnection.setSurfaceAlpha(0.0f);
        }
    }

    public final boolean isPluginLockFbeCondition() {
        return (KeyguardUpdateMonitor.getCurrentUser() == 0) && !this.mUpdateMonitor.isUserUnlocked(0);
    }

    public final boolean isStartMultipackCondition() {
        int i;
        int i2 = 18;
        int i3 = LsRune.WALLPAPER_SUB_DISPLAY_MODE ? isSubDisplay() ? 18 : 6 : 2;
        if (isWallpaperUpdateFromDls()) {
            Log.d("KeyguardWallpaperController", "isStartMultipackCondition: PluginLock manages lockscreen wallpaper.");
            return false;
        }
        try {
            i = this.mService.semGetWallpaperType(i3);
        } catch (RemoteException e) {
            Log.e("KeyguardWallpaperController", "System dead?" + e);
            i = -1;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("isStartMultipackCondition: type = ", i, "KeyguardWallpaperController");
        if (i == 3) {
            return true;
        }
        if (i == -1) {
            if (WallpaperUtils.isLiveWallpaperEnabled()) {
                Log.d("KeyguardWallpaperController", "isStartMultipackCondition: Live wallpaper is enabled.");
                return false;
            }
            if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                i2 = 2;
            } else if (!isSubDisplay()) {
                i2 = 6;
            }
            if (WallpaperManager.isDefaultOperatorWallpaper(this.mContext, i2)) {
                Log.d("KeyguardWallpaperController", "isStartMultipackCondition: Operator wallpaper.");
                return false;
            }
            if (WallpaperManager.getInstance(this.mContext).getDefaultWallpaperType(WallpaperUtils.sCurrentWhich) == 3) {
                return true;
            }
        }
        return false;
    }

    public final boolean isWallpaperUpdateFromDls() {
        if (KeyguardUpdateMonitor.getCurrentUser() != 0) {
            return false;
        }
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE ? isSubDisplay() ? Prefs.getBoolean(this.mContext, "WPaperChangedByDlsSub", false) : Prefs.getBoolean(this.mContext, "WPaperChangedByDls", false) : Prefs.getBoolean(this.mContext, "WPaperChangedByDls", false);
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onDataCleared() {
        printLognAddHistory("onDataCleared()");
        setWallpaperUpdateFromDls(2, false);
        sendUpdateWallpaperMessage(610);
    }

    public final void onPause() {
        if (this.mWallpaperView != null) {
            boolean z = this.mDozeParameters.mControlScreenOffAnimation;
            Log.d("KeyguardWallpaperController", "mWallpaperView.onPause() visibility = " + this.mVisibility + " shouldControlScreenOff = " + z);
            WallpaperUtils.sDrawState = false;
            this.mWallpaperView.updateDrawState(false);
            if (z) {
                return;
            }
            this.mWallpaperView.onPause();
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onReady() {
        printLognAddHistory("onReady()");
        if (this.mIsPluginLockReady) {
            return;
        }
        this.mIsPluginLockReady = true;
        sendUpdateWallpaperMessage(611);
    }

    public final void onResume() {
        Log.d("KeyguardWallpaperController", "mWallpaperView.onResume()");
        this.mExecutor.execute(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 6));
        WallpaperUtils.sDrawState = true;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 7));
            return;
        }
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            systemUIWallpaperBase.updateDrawState(true);
            this.mWallpaperView.onResume();
        }
    }

    public final void onSemBackupStatusChanged(int i, int i2, int i3) {
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSemBackupStatusChanged: which = ", i, ", status = ", i2, " , key = ");
        m45m.append(i3);
        printLognAddHistory(m45m.toString());
        if (this.mPluginWallpaperManager == null) {
            printLognAddHistory("onSemBackupStatusChanged: mPluginWallpaperManager is null.");
            return;
        }
        if ((i & 2) != 0 || i == -1 || (LsRune.WALLPAPER_SUB_WATCHFACE && WhichChecker.isWatchFace(i))) {
            Bundle bundle = new Bundle();
            bundle.putInt("which", i);
            bundle.putInt(IMSParameter.CALL.STATUS, i2);
            bundle.putInt("key", i3);
            sendUpdateWallpaperMessage(609, false, bundle);
        }
    }

    public final void onSemMultipackApplied(int i) {
        this.mObserver.updateState(1);
        printLognAddHistory("onSemMultipackApplied: which = " + i);
        Bundle bundle = new Bundle();
        bundle.putInt("which", i);
        sendUpdateWallpaperMessage(613, false, bundle);
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("include_dls", false);
        bundle2.putInt("which", i2);
        printLognAddHistory("onSemWallpaperChanged: type = " + i + ", which = " + i2);
        this.mWallpaperChangeNotifier.notify(i2);
        int i3 = 2;
        if (WhichChecker.isFlagEnabled(i2, 2)) {
            if (!WhichChecker.isFlagEnabled(i2, 8) || (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER && WallpaperUtils.isDexStandAloneMode())) {
                this.mObserver.updateState(1);
                if (i == 1000) {
                    String string = bundle.getString("trigger");
                    if (string != null) {
                        if (string.equals("dls")) {
                            bundle2.putInt(PluginLock.KEY_SCREEN, WhichChecker.isSubDisplay(i2) ? 1 : 0);
                            sendUpdateWallpaperMessage(1002, false, bundle2);
                            return;
                        } else {
                            if (string.equals("snapshot")) {
                                bundle2.putBoolean("flag", true);
                                Uri semGetUri = this.mWallpaperManager.semGetUri(i2);
                                if (semGetUri != null) {
                                    bundle2.putString("type", semGetUri.toString().substring(semGetUri.toString().lastIndexOf("/") + 1));
                                }
                                bundle2.putInt(PluginLock.KEY_SCREEN, WhichChecker.isSubDisplay(i2) ? 1 : 0);
                                sendUpdateWallpaperMessage(1001, false, bundle2);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (i != 3) {
                    this.mWallpaperManager.semSetDLSWallpaperColors(null, i2);
                    if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled(WhichChecker.isSubDisplay(i2) ? 1 : 0)) {
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && this.mWallpaperEventNotifier.mIsThemeApplying && WallpaperUtils.isSubDisplay(i2)) {
                            this.mMainHandler.postDelayed(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, i3), 1000L);
                            bundle2.putLong("delay", 1000L);
                        } else {
                            ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).onLockWallpaperChanged(WallpaperUtils.isSubDisplay(i2) ? 1 : 0);
                        }
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
                        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Ignore wallpaper change for not current which : ", i2, "KeyguardWallpaperController");
                        return;
                    }
                }
                sendUpdateWallpaperMessage(601, false, bundle2);
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
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("set system color which = ", i, ", opacity = ");
            m1m.append(semWallpaperColors.getDarkModeDimOpacity());
            Log.i("KeyguardWallpaperController", m1m.toString());
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
        boolean z2 = WallpaperUtils.mIsEmergencyMode;
        boolean z3 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z3 && !(z = LsRune.WALLPAPER_SUB_WATCHFACE) && z3 && !z) {
            if ((i & 3) == 2) {
                WallpaperUtils.sCachedWallpaperColors.put(WallpaperUtils.isSubDisplay(i) ? 16 : 4, semWallpaperColors);
            }
        }
        boolean z4 = LsRune.WALLPAPER_SUB_WATCHFACE;
        if (z4) {
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
        boolean z5 = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z6 = WallpaperUtils.mIsEmergencyMode;
        if (z5 || z6) {
            printLognAddHistory("onSemWallpaperColorsChanged: We are in UPSM or EM. We don't need this event for now.");
            return;
        }
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSemWallpaperColorsChanged: which = ", i, ", userId = ", i2, ", colors = ");
        m45m.append(semWallpaperColors.toSimpleString());
        printLognAddHistory(m45m.toString());
        removeMessages(608);
        Message obtainMessage = obtainMessage(608);
        Bundle bundle = new Bundle();
        bundle.putParcelable("wallpaper_colors", semWallpaperColors);
        bundle.putInt("which", i);
        bundle.putInt("userid", i2);
        obtainMessage.setData(bundle);
        if (!z3 || z4) {
            sendMessage(obtainMessage);
        } else {
            sendMessageDelayed(obtainMessage, 50L);
        }
    }

    public final void onTransitionAod(boolean z) {
        Log.d("KeyguardWallpaperController", "onTransitionAod: mDozeParameters.shouldControlScreenOff() = " + this.mDozeParameters.mControlScreenOffAnimation);
        if (WallpaperUtils.isAODShowLockWallpaperEnabled()) {
            this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda9(this, z, 1));
        }
        if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled() && WallpaperUtils.isAODShowLockWallpaperEnabled() && (!z || this.mDozeParameters.mControlScreenOffAnimation)) {
            return;
        }
        this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 14));
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors) {
        int i = WallpaperUtils.sCurrentWhich;
        if (semWallpaperColors == null) {
            this.mWallpaperManager.semSetDLSWallpaperColors(semWallpaperColors, i);
            return;
        }
        int which = semWallpaperColors.getWhich();
        if ((which & 2) == 0) {
            printLognAddHistory("onWallpaperHintUpdate: invalid which. which = " + which);
            return;
        }
        if (which != i) {
            Log.w("KeyguardWallpaperController", "onWallpaperHintUpdate: which mismatched. curWhich = " + i + ", colorWhich=" + which);
        }
        this.mWallpaperManager.semSetDLSWallpaperColors(semWallpaperColors, which);
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperUpdate(boolean z) {
        printLognAddHistory("onWallpaperUpdate, cacheClear:" + z);
        this.mObserver.updateState(1);
        if (z && LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            WallpaperUtils.clearCachedWallpaper(2);
            WallpaperUtils.clearCachedWallpaper(18);
        }
        if (KeyguardUpdateMonitor.getCurrentUser() != 0) {
            printLognAddHistory("onWallpaperUpdate: Error. onWallpaperUpdate SHOULD NOT be called on multi-user.");
            return;
        }
        boolean z2 = WallpaperUtils.mIsUltraPowerSavingMode;
        boolean z3 = WallpaperUtils.mIsEmergencyMode;
        if (z2 || z3) {
            printLognAddHistory("onWallpaperUpdate: We are handling wallpaper update by settings changed event for UPSM or EM.");
            return;
        }
        if (!isWallpaperUpdateFromDls()) {
            setWallpaperUpdateFromDls(isSubDisplay() ? 1 : 0, true);
        }
        if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled() && WallpaperUtils.isLiveWallpaperEnabled(isSubDisplay())) {
            WallpaperUtils.loadLiveWallpaperSettings(this.mCurrentUserId, this.mContext);
        }
        sendUpdateWallpaperMessage(VolteConstants.ErrorCode.DOES_NOT_EXIST_ANYWHERE);
    }

    public final void printLognAddHistory(String str) {
        ((WallpaperLoggerImpl) this.mWallpaperLogger).log("KeyguardWallpaperController", str);
    }

    public final void removeAllChildViews(ViewGroup viewGroup, boolean z) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        Log.d("KeyguardWallpaperController", "removeAllChildViews: childCount = " + childCount);
        while (true) {
            childCount--;
            if (childCount < 0) {
                Log.d("KeyguardWallpaperController", "removeAllChildViews: childCount after remove = " + viewGroup.getChildCount());
                return;
            }
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt != null) {
                if (!z && WallpaperUtils.isAODShowLockWallpaperEnabled() && ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled() && (childAt instanceof KeyguardTransitionWallpaper)) {
                    Log.d("KeyguardWallpaperController", "removeAllChildViews: skip transition view.");
                } else {
                    try {
                        viewGroup.removeView(childAt);
                    } catch (Throwable th) {
                        Log.e("KeyguardWallpaperController", "removeAllChildViews : e = " + th, th);
                    }
                }
            }
        }
    }

    public final void sendUpdateWallpaperMessage(int i) {
        sendUpdateWallpaperMessage(i, false, null);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda7] */
    public final void setBackground(final SystemUIWallpaper systemUIWallpaper, final SystemUIWallpaper systemUIWallpaper2, final boolean z, final boolean z2, final boolean z3) {
        if (this.mRunnableSetBackground != null) {
            Log.d("KeyguardWallpaperController", "setBackground, remove runnable");
            this.mMainHandler.removeCallbacks(this.mRunnableSetBackground);
            this.mOldWallpaperView = null;
        }
        this.mRunnableSetBackground = new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public final void run() {
                SystemUIWallpaperBase systemUIWallpaperBase;
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                SystemUIWallpaperBase systemUIWallpaperBase2 = systemUIWallpaper;
                boolean z4 = z3;
                SystemUIWallpaperBase systemUIWallpaperBase3 = systemUIWallpaper2;
                boolean z5 = z;
                boolean z6 = z2;
                keyguardWallpaperController.mRunnableSetBackground = null;
                keyguardWallpaperController.mOldWallpaperView = keyguardWallpaperController.mWallpaperView;
                Log.d("KeyguardWallpaperController", "setBackground [old] : " + keyguardWallpaperController.mOldWallpaperView + " , [new] : " + systemUIWallpaperBase2);
                ViewGroup viewGroup = keyguardWallpaperController.mRootView;
                if (viewGroup != null) {
                    keyguardWallpaperController.removeAllChildViews(viewGroup, false);
                    if (systemUIWallpaperBase2 != 0) {
                        SystemUIWallpaper systemUIWallpaper3 = (SystemUIWallpaper) systemUIWallpaperBase2;
                        if (keyguardWallpaperController.mIsKeyguardShowing != systemUIWallpaper3.mIsKeyguardShowing) {
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateKeyguardState, showing state : "), keyguardWallpaperController.mIsKeyguardShowing, "KeyguardWallpaperController");
                            systemUIWallpaperBase2.onKeyguardShowing(keyguardWallpaperController.mIsKeyguardShowing);
                        }
                        if (keyguardWallpaperController.mOccluded != systemUIWallpaper3.mOccluded) {
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateKeyguardState, occluded state : "), keyguardWallpaperController.mOccluded, "KeyguardWallpaperController");
                            systemUIWallpaperBase2.onOccluded(keyguardWallpaperController.mOccluded);
                        }
                        if (keyguardWallpaperController.mBouncer != systemUIWallpaper3.mBouncer) {
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateKeyguardState, bouncer state : "), keyguardWallpaperController.mBouncer, "KeyguardWallpaperController");
                            systemUIWallpaperBase2.onKeyguardBouncerFullyShowingChanged(keyguardWallpaperController.mBouncer);
                        }
                        if (WallpaperUtils.isAODShowLockWallpaperEnabled() && ((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).isDynamicWallpaperEnabled() && keyguardWallpaperController.mTransitionView != null) {
                            ViewGroup viewGroup2 = keyguardWallpaperController.mRootView;
                            int i = -1;
                            if (viewGroup2 != null) {
                                int childCount = viewGroup2.getChildCount() - 1;
                                while (true) {
                                    if (childCount < 0) {
                                        break;
                                    }
                                    View childAt = keyguardWallpaperController.mRootView.getChildAt(childCount);
                                    if (childAt != null && WallpaperUtils.isAODShowLockWallpaperEnabled() && ((PluginWallpaperManagerImpl) keyguardWallpaperController.mPluginWallpaperManager).isDynamicWallpaperEnabled() && (childAt instanceof KeyguardTransitionWallpaper)) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("getTransitionViewIndex: index = ", childCount, "KeyguardWallpaperController");
                                        i = childCount;
                                        break;
                                    }
                                    childCount--;
                                }
                            }
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setBackground: index = ", i, "KeyguardWallpaperController");
                            keyguardWallpaperController.mRootView.addView((View) systemUIWallpaperBase2, 0);
                            if (keyguardWallpaperController.mRootView.getChildCount() == 1) {
                                keyguardWallpaperController.mRootView.addView((View) keyguardWallpaperController.mTransitionView);
                            }
                        } else {
                            keyguardWallpaperController.mRootView.addView((View) systemUIWallpaperBase2);
                        }
                    }
                    keyguardWallpaperController.mUpdateMonitor.setHasLockscreenWallpaper(systemUIWallpaperBase2 != 0);
                }
                keyguardWallpaperController.mWallpaperView = systemUIWallpaperBase2;
                if (WallpaperUtils.isAODShowLockWallpaperEnabled() && (systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView) != null) {
                    if (!(((SystemUIWallpaper) systemUIWallpaperBase).mTransitionAnimationListener != null)) {
                        systemUIWallpaperBase2.setTransitionAnimationListener(keyguardWallpaperController.mTransitionAnimationListener);
                    }
                }
                SystemUIWallpaperBase systemUIWallpaperBase4 = keyguardWallpaperController.mOldWallpaperView;
                if (systemUIWallpaperBase4 != null) {
                    systemUIWallpaperBase4.cleanUp();
                }
                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                    if (keyguardWallpaperController.mIsBlurredViewAdded) {
                        keyguardWallpaperController.cleanUpBlurredView();
                    }
                    if (z4) {
                        keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda6(keyguardWallpaperController, systemUIWallpaperBase3));
                    }
                }
                keyguardWallpaperController.mUpdateMonitor.setBackDropViewShowing(z5, z6);
            }
        };
        if (WallpaperUtils.isAODShowLockWallpaperEnabled() && ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled() && this.mTransitionView != null && this.mIsPendingTypeChangeForTransition) {
            Log.d("KeyguardWallpaperController", "setBackground: Postpone setBackground()");
        } else {
            this.mIsPendingTypeChangeForTransition = false;
            this.mMainHandler.post(this.mRunnableSetBackground);
        }
    }

    public final void setKeyguardShowing(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("setKeyguardShowing() showing:", z, ", mIsPendingTypeChange:");
        m49m.append(this.mIsPendingTypeChange);
        m49m.append(", mOccluded:");
        NotificationListener$$ExternalSyntheticOutline0.m123m(m49m, this.mOccluded, "KeyguardWallpaperController");
        this.mIsKeyguardShowing = z;
        if (z && this.mIsPendingTypeChange) {
            if (this.mRunnableCleanUp != null) {
                Log.i("KeyguardWallpaperController", "setKeyguardShowing, remove cleanUp runnable");
                this.mMainHandler.removeCallbacks(this.mRunnableCleanUp);
            }
            handleWallpaperTypeChanged(getLockWallpaperType(true), true);
        }
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            systemUIWallpaperBase.onKeyguardShowing(this.mIsKeyguardShowing);
        }
        this.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda2(this, 11));
        if (this.mIsLockscreenDisabled && this.mIsKeyguardShowing) {
            this.mIsLockscreenDisabled = false;
            if (LsRune.WALLPAPER_SUB_WATCHFACE && isSubDisplay()) {
                return;
            }
            if (!WallpaperUtils.isAODShowLockWallpaperAndLockDisabled(KeyguardUpdateMonitor.getCurrentUser(), this.mContext) || this.mWallpaperView == null) {
                sendEmptyMessage(601);
            } else {
                onResume();
            }
        }
    }

    public final void setRootView(ViewGroup viewGroup) {
        Log.d("KeyguardWallpaperController", "setRootView");
        this.mRootView = viewGroup;
        if (viewGroup != null) {
            removeAllChildViews(viewGroup, true);
        }
        this.mIsLockscreenDisabled = this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
        ViewGroup viewGroup2 = this.mRootView;
        if (viewGroup2 != null) {
            viewGroup2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                    if (keyguardWallpaperController.mBottom != i4) {
                        StringBuilder sb = new StringBuilder("onLayoutChange() v: ");
                        sb.append(view);
                        sb.append(", bottom : ");
                        sb.append(i4);
                        sb.append(", oldBottom : ");
                        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i8, "KeyguardWallpaperController");
                        keyguardWallpaperController.mBottom = i4;
                        keyguardWallpaperController.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 11));
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            keyguardWallpaperController.showBlurredViewIfNeededOnUiThread();
                        } else {
                            keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 1));
                        }
                        keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda2(keyguardWallpaperController, 3));
                    }
                }
            });
        } else {
            Log.d("KeyguardWallpaperController", "setRootView: mRootView is null!");
        }
        handleWallpaperTypeChanged(getLockWallpaperType(true));
    }

    public final void setThumbnailVisibility(int i) {
        WallpaperUtils.isSubDisplay();
        int i2 = 1;
        if (!(WallpaperUtils.sWallpaperType[WallpaperUtils.isSubDisplay() ? 1 : 0] == 7) || WallpaperUtils.isLiveWallpaperEnabled()) {
            return;
        }
        if (this.mIsFingerPrintTouchDown && i != 0) {
            Log.w("KeyguardWallpaperController", "Thumbnail should be shown when unlocking using fingerprint.");
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.postAtFrontOfQueue(new KeyguardWallpaperController$$ExternalSyntheticLambda4(this, i, i2));
            return;
        }
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            systemUIWallpaperBase.setThumbnailVisibility(i);
        }
    }

    public final void setWallpaperUpdateFromDls(int i, boolean z) {
        if (KeyguardUpdateMonitor.getCurrentUser() != 0) {
            printLognAddHistory("setWallpaperUpdateFromDls: User (" + KeyguardUpdateMonitor.getCurrentUser() + ") changed wallpaper. Don't update WPAPER_CHANGED_BY_DLS.");
            return;
        }
        if (i == 2) {
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && Prefs.getBoolean(this.mContext, "WPaperChangedByDlsSub", false) != z) {
                Prefs.putBoolean(this.mContext, "WPaperChangedByDlsSub", z);
            }
            if (Prefs.getBoolean(this.mContext, "WPaperChangedByDls", false) != z) {
                Prefs.putBoolean(this.mContext, "WPaperChangedByDls", z);
                return;
            }
            return;
        }
        if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            if (Prefs.getBoolean(this.mContext, "WPaperChangedByDls", false) != z) {
                Prefs.putBoolean(this.mContext, "WPaperChangedByDls", z);
            }
        } else if (i == 1) {
            if (Prefs.getBoolean(this.mContext, "WPaperChangedByDlsSub", false) != z) {
                Prefs.putBoolean(this.mContext, "WPaperChangedByDlsSub", z);
            }
        } else if (Prefs.getBoolean(this.mContext, "WPaperChangedByDls", false) != z) {
            Prefs.putBoolean(this.mContext, "WPaperChangedByDls", z);
        }
    }

    public final void showBlurredViewIfNeededOnUiThread() {
        ViewGroup viewGroup;
        Assert.isMainThread();
        if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed() && this.mOccluded && this.mBouncer && (viewGroup = this.mRootView) != null) {
            viewGroup.setVisibility(0);
            Object obj = this.mWallpaperView;
            if (obj != null) {
                ((View) obj).setVisibility(4);
            }
            if (this.mIsBlurredViewAdded) {
                return;
            }
            this.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda6(this, this.mWallpaperView));
        }
    }

    public final void startMultipack(int i) {
        printLognAddHistory("startMultipack: which = " + i);
        if (!this.mIsPluginLockReady) {
            Log.d("KeyguardWallpaperController", "startMultipack: mIsPluginLockReady is false");
            return;
        }
        if (this.mMultiPackDispatcher == null) {
            MultiPackDispatcher multiPackDispatcher = new MultiPackDispatcher(this.mContext, this.mWallpaperLogger, this.mPluginLockUtils);
            this.mMultiPackDispatcher = multiPackDispatcher;
            multiPackDispatcher.mOnApplyMultipackListener = new C36689();
        }
        MultiPackDispatcher multiPackDispatcher2 = this.mMultiPackDispatcher;
        if (multiPackDispatcher2 != null) {
            multiPackDispatcher2.startMultipack(i);
        }
    }

    public final void sendUpdateWallpaperMessage(int i, boolean z, Bundle bundle) {
        HandlerC36656 handlerC36656 = this.mWorkHandler;
        if (handlerC36656 != null) {
            Message obtainMessage = handlerC36656.obtainMessage(i);
            if (i != 612 && i != 613 && i != 609 && hasMessages(i)) {
                printLognAddHistory("sendUpdateWallpaperMessage: remove message what = " + i);
                removeMessages(i);
            }
            if (bundle == null) {
                if (z) {
                    sendMessageAtFrontOfQueue(obtainMessage);
                    return;
                } else {
                    sendEmptyMessage(i);
                    return;
                }
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x038c  */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleWallpaperTypeChanged(int i, boolean z) {
        boolean z2;
        int i2;
        int i3;
        SystemUIWallpaper keyguardImageWallpaper;
        SystemUIWallpaper systemUIWallpaper;
        final boolean z3;
        String str;
        Uri uri;
        String str2;
        String str3;
        Uri uri2;
        Uri uri3;
        String str4;
        String str5;
        ?? r6;
        Uri uri4;
        String str6;
        if (!WallpaperUtils.isAODShowLockWallpaperAndLockDisabled(KeyguardUpdateMonitor.getCurrentUser(), this.mContext) && this.mIsLockscreenDisabled) {
            printLognAddHistory("handleWallpaperTypeChanged: mIsLockscreenDisabled");
            this.mObserver.updateState(3);
            return;
        }
        StringBuilder sb = new StringBuilder("handleWallpaperTypeChanged: type = ");
        final boolean z4 = WallpaperUtils.mIsUltraPowerSavingMode;
        final boolean z5 = WallpaperUtils.mIsEmergencyMode;
        boolean isLiveWallpaperEnabled = WallpaperUtils.isLiveWallpaperEnabled(isSubDisplay());
        final boolean isSubDisplay = isSubDisplay();
        final int fbeWallpaperType = getFbeWallpaperType(i);
        int i4 = WallpaperUtils.sCurrentWhich;
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            WallpaperUtils.sWallpaperType[0] = fbeWallpaperType;
            i4 = 6;
        } else {
            WallpaperUtils.sWallpaperType[isSubDisplay ? 1 : 0] = fbeWallpaperType;
        }
        sb.append(fbeWallpaperType);
        sb.append(", which = ");
        sb.append(WallpaperUtils.sCurrentWhich);
        sb.append(", userId = ");
        sb.append(this.mCurrentUserId);
        sb.append(", upsm = ");
        sb.append(z4);
        sb.append(", em = ");
        sb.append(z5);
        sb.append(", isLiveWallpaperSettingEnabled = ");
        sb.append(isLiveWallpaperEnabled);
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                boolean z6 = z4;
                boolean z7 = z5;
                ViewGroup viewGroup = keyguardWallpaperController.mRootView;
                if (viewGroup != null) {
                    viewGroup.setBackgroundColor((z6 || z7) ? EmergencyPhoneWidget.BG_COLOR : 0);
                }
            }
        });
        final boolean z6 = true;
        if ((WallpaperUtils.isAODShowLockWallpaperEnabled() && fbeWallpaperType == 8 && this.mTransitionView != null) && this.mPendingRotationForTransitionView) {
            Log.d("KeyguardWallpaperController", "handleWallpaperTypeChanged: Pending no sensor operation");
        } else {
            disableRotateIfNeeded();
        }
        if (!z4 && !z5) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            if (edmMonitor != null && edmMonitor.mIsLockscreenWallpaperConfigured) {
                sb.append(", MDM mode");
                printLognAddHistory(sb.toString());
                setBackground(new KeyguardImageWallpaper(this.mContext, this.mUpdateMonitor, this.mWallpaperResultCallback, this.mExecutor, i4, z && !this.mIsPendingTypeChange, this.mPluginWallpaperManager, this.mSettingsHelper, this.mWallpaperLogger, this.mWcgConsumer, this.mColorProvider), null, true, false, false);
                return;
            }
            if (isLiveWallpaperEnabled) {
                setBackground(null, null, false, true, true);
                sb.append(", Live wallpaper");
                printLognAddHistory(sb.toString());
                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                    handleDlsViewModeDelayed(150);
                }
                this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay ? 16 : 4) | 2);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    this.mUpdateMonitor.dispatchWallpaperTypeChanged(fbeWallpaperType, true, isSubDisplay);
                    return;
                } else {
                    this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                            keyguardWallpaperController.mUpdateMonitor.dispatchWallpaperTypeChanged(fbeWallpaperType, z6, isSubDisplay);
                        }
                    });
                    return;
                }
            }
            if (this.mWallpaperView == null) {
                this.mObserver.updateState(1);
            }
            if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed() && this.mIsBlurredViewAdded) {
                cleanUpBlurredView();
            }
            if (fbeWallpaperType == 0) {
                z2 = isSubDisplay ? 1 : 0;
                i2 = 4;
                i3 = 150;
                keyguardImageWallpaper = new KeyguardImageWallpaper(this.mContext, this.mUpdateMonitor, this.mWallpaperResultCallback, this.mExecutor, i4, z && !this.mIsPendingTypeChange, this.mPluginWallpaperManager, this.mSettingsHelper, this.mWallpaperLogger, this.mWcgConsumer, this.mColorProvider);
            } else if (fbeWallpaperType != 1) {
                Uri uri5 = null;
                if (fbeWallpaperType == 4) {
                    z2 = isSubDisplay ? 1 : 0;
                    i2 = 4;
                    i3 = 150;
                    try {
                        str = this.mService.getAnimatedPkgName(i4);
                    } catch (RemoteException e) {
                        Log.e("KeyguardWallpaperController", "System dead?" + e);
                        str = null;
                    }
                    keyguardImageWallpaper = new KeyguardAnimatedWallpaper(this.mContext, str, this.mUpdateMonitor, this.mExecutor, this.mWallpaperResultCallback, this.mWcgConsumer, i4);
                } else if (fbeWallpaperType == 7) {
                    z2 = isSubDisplay ? 1 : 0;
                    i2 = 4;
                    i3 = 150;
                    keyguardImageWallpaper = new KeyguardLiveWallpaper(this.mContext, this.mUpdateMonitor, this.mWallpaperResultCallback, this.mExecutor, this.mWallpaperLogger, this.mWcgConsumer, this.mOccluded, this.mIsKeyguardShowing, this.mCurrentUserId, false, this.mSettingsHelper);
                } else if (fbeWallpaperType != 8) {
                    printLognAddHistory("handleWallpaperTypeChanged: default wallpaper or DLS is displayed.");
                    Context context = this.mContext;
                    KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
                    C36612 c36612 = this.mWallpaperResultCallback;
                    ExecutorService executorService = this.mExecutor;
                    boolean z7 = z && !this.mIsPendingTypeChange;
                    PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
                    SettingsHelper settingsHelper = this.mSettingsHelper;
                    WallpaperLogger wallpaperLogger = this.mWallpaperLogger;
                    Consumer consumer = this.mWcgConsumer;
                    z2 = isSubDisplay ? 1 : 0;
                    i2 = 4;
                    i3 = 150;
                    keyguardImageWallpaper = new KeyguardImageWallpaper(context, keyguardUpdateMonitor, c36612, executorService, i4, z7, pluginWallpaperManager, settingsHelper, wallpaperLogger, consumer, this.mColorProvider);
                } else {
                    z2 = isSubDisplay ? 1 : 0;
                    i2 = 4;
                    i3 = 150;
                    try {
                        str2 = this.mService.getVideoFilePath(i4);
                        try {
                            r6 = this.mService.getVideoPackage(i4);
                            try {
                                str3 = this.mService.getVideoFileName(i4);
                            } catch (RemoteException e2) {
                                e = e2;
                                uri = null;
                                str3 = null;
                                uri4 = r6;
                                uri5 = uri4;
                                Log.e("KeyguardWallpaperController", "System dead?" + e);
                                uri2 = uri5;
                                uri5 = uri;
                                uri3 = uri5;
                                str4 = str2;
                                str5 = uri2;
                                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("handleWallpaperTypeChanged: VIDEO wallpaper path = ", str4, ", pkg = ", str5, " , filename = ");
                                m87m.append(str3);
                                m87m.append(" , mVisibility = ");
                                m87m.append(this.mVisibility);
                                m87m.append(", isFolderStateChanged =");
                                m87m.append(z);
                                printLognAddHistory(m87m.toString());
                                systemUIWallpaper = new KeyguardVideoWallpaper(this.mContext, str4, str5, uri3, str3, this.mUpdateMonitor, this.mDozeParameters, this.mWallpaperResultCallback, this.mExecutor, this.mPluginWallpaperManager, this.mWallpaperLogger, this.mWcgConsumer, this.mOccluded, this.mIsKeyguardShowing, this.mCurrentUserId, this.mBouncer);
                                printLognAddHistory(sb.toString());
                                setBackground(systemUIWallpaper, systemUIWallpaper, true, true, systemUIWallpaper instanceof KeyguardVideoWallpaper);
                                if (Looper.myLooper() == Looper.getMainLooper()) {
                                }
                                this.mIsPendingTypeChange = z3;
                                handleDlsViewModeDelayed((LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) ? 100 : i3);
                                this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : i2) | 2);
                                return;
                            }
                        } catch (RemoteException e3) {
                            e = e3;
                            r6 = 0;
                        }
                        try {
                            str6 = "";
                            r6 = r6;
                            if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                                str2 = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getWallpaperPath();
                                uri5 = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getWallpaperUri();
                                r6 = "";
                            }
                        } catch (RemoteException e4) {
                            e = e4;
                            uri = uri5;
                            uri4 = r6;
                            uri5 = uri4;
                            Log.e("KeyguardWallpaperController", "System dead?" + e);
                            uri2 = uri5;
                            uri5 = uri;
                            uri3 = uri5;
                            str4 = str2;
                            str5 = uri2;
                            StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("handleWallpaperTypeChanged: VIDEO wallpaper path = ", str4, ", pkg = ", str5, " , filename = ");
                            m87m2.append(str3);
                            m87m2.append(" , mVisibility = ");
                            m87m2.append(this.mVisibility);
                            m87m2.append(", isFolderStateChanged =");
                            m87m2.append(z);
                            printLognAddHistory(m87m2.toString());
                            systemUIWallpaper = new KeyguardVideoWallpaper(this.mContext, str4, str5, uri3, str3, this.mUpdateMonitor, this.mDozeParameters, this.mWallpaperResultCallback, this.mExecutor, this.mPluginWallpaperManager, this.mWallpaperLogger, this.mWcgConsumer, this.mOccluded, this.mIsKeyguardShowing, this.mCurrentUserId, this.mBouncer);
                            printLognAddHistory(sb.toString());
                            setBackground(systemUIWallpaper, systemUIWallpaper, true, true, systemUIWallpaper instanceof KeyguardVideoWallpaper);
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                            }
                            this.mIsPendingTypeChange = z3;
                            handleDlsViewModeDelayed((LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) ? 100 : i3);
                            this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : i2) | 2);
                            return;
                        }
                    } catch (RemoteException e5) {
                        e = e5;
                        uri = null;
                        str2 = null;
                        str3 = null;
                    }
                    if (LsRune.KEYGUARD_FBE && isPluginLockFbeCondition()) {
                        int screenId = PluginWallpaperManager.getScreenId(i4);
                        if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperAvailable(screenId)) {
                            str4 = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaperPath(screenId);
                            uri3 = uri5;
                            str5 = str6;
                            StringBuilder m87m22 = AbstractC0866xb1ce8deb.m87m("handleWallpaperTypeChanged: VIDEO wallpaper path = ", str4, ", pkg = ", str5, " , filename = ");
                            m87m22.append(str3);
                            m87m22.append(" , mVisibility = ");
                            m87m22.append(this.mVisibility);
                            m87m22.append(", isFolderStateChanged =");
                            m87m22.append(z);
                            printLognAddHistory(m87m22.toString());
                            systemUIWallpaper = new KeyguardVideoWallpaper(this.mContext, str4, str5, uri3, str3, this.mUpdateMonitor, this.mDozeParameters, this.mWallpaperResultCallback, this.mExecutor, this.mPluginWallpaperManager, this.mWallpaperLogger, this.mWcgConsumer, this.mOccluded, this.mIsKeyguardShowing, this.mCurrentUserId, this.mBouncer);
                            printLognAddHistory(sb.toString());
                            setBackground(systemUIWallpaper, systemUIWallpaper, true, true, systemUIWallpaper instanceof KeyguardVideoWallpaper);
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                z3 = false;
                                this.mUpdateMonitor.dispatchWallpaperTypeChanged(fbeWallpaperType, false, z2);
                            } else {
                                final boolean z8 = z2;
                                z3 = false;
                                this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda8
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.this;
                                        keyguardWallpaperController.mUpdateMonitor.dispatchWallpaperTypeChanged(fbeWallpaperType, z3, z8);
                                    }
                                });
                            }
                            this.mIsPendingTypeChange = z3;
                            handleDlsViewModeDelayed((LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) ? 100 : i3);
                            this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : i2) | 2);
                            return;
                        }
                    }
                    uri2 = r6;
                    uri3 = uri5;
                    str4 = str2;
                    str5 = uri2;
                    StringBuilder m87m222 = AbstractC0866xb1ce8deb.m87m("handleWallpaperTypeChanged: VIDEO wallpaper path = ", str4, ", pkg = ", str5, " , filename = ");
                    m87m222.append(str3);
                    m87m222.append(" , mVisibility = ");
                    m87m222.append(this.mVisibility);
                    m87m222.append(", isFolderStateChanged =");
                    m87m222.append(z);
                    printLognAddHistory(m87m222.toString());
                    systemUIWallpaper = new KeyguardVideoWallpaper(this.mContext, str4, str5, uri3, str3, this.mUpdateMonitor, this.mDozeParameters, this.mWallpaperResultCallback, this.mExecutor, this.mPluginWallpaperManager, this.mWallpaperLogger, this.mWcgConsumer, this.mOccluded, this.mIsKeyguardShowing, this.mCurrentUserId, this.mBouncer);
                    printLognAddHistory(sb.toString());
                    setBackground(systemUIWallpaper, systemUIWallpaper, true, true, systemUIWallpaper instanceof KeyguardVideoWallpaper);
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                    }
                    this.mIsPendingTypeChange = z3;
                    handleDlsViewModeDelayed((LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) ? 100 : i3);
                    this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : i2) | 2);
                    return;
                }
            } else {
                z2 = isSubDisplay ? 1 : 0;
                i2 = 4;
                i3 = 150;
                keyguardImageWallpaper = new KeyguardMotionWallpaper(this.mContext, this.mUpdateMonitor, this.mWallpaperResultCallback, this.mExecutor, this.mWcgConsumer, i4);
            }
            systemUIWallpaper = keyguardImageWallpaper;
            printLognAddHistory(sb.toString());
            setBackground(systemUIWallpaper, systemUIWallpaper, true, true, systemUIWallpaper instanceof KeyguardVideoWallpaper);
            if (Looper.myLooper() == Looper.getMainLooper()) {
            }
            this.mIsPendingTypeChange = z3;
            handleDlsViewModeDelayed((LsRune.WALLPAPER_CAPTURED_BLUR || !DeviceState.isCapturedBlurAllowed()) ? 100 : i3);
            this.mWallpaperAnalytics.updateWallpaperStatus((isSubDisplay() ? 16 : i2) | 2);
            return;
        }
        printLognAddHistory(sb.toString());
        setBackground(null, null, true, false, false);
    }

    public final void onWallpaperChanged() {
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }
}
