package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayAddress;
import android.view.Surface;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.wm.DeviceStateController;
import com.android.server.wm.DisplayRotation;
import com.android.server.wm.RemoteDisplayChangeController;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DisplayRotation {
    public final boolean isDefaultDisplay;
    public int mAllowAllRotations;
    public final boolean mAllowRotationResolver;
    public boolean mAllowSeamlessRotationDespiteNavBarMoving;
    public int mCameraRotationMode;
    public final int mCarDockRotation;
    public final DisplayRotationImmersiveAppCompatPolicy mCompatPolicyForImmersiveApps;
    public final Context mContext;
    public int mCurrentAppOrientation;
    final Runnable mDefaultDisplayRotationChangedCallback;
    public boolean mDefaultFixedToUserRotation;
    public int mDeferredRotationPauseCount;
    public int mDemoHdmiRotation;
    public boolean mDemoHdmiRotationLock;
    public int mDemoRotation;
    public boolean mDemoRotationLock;
    public final int mDeskDockRotation;
    public final DeviceStateController mDeviceStateController;
    public final DisplayContent mDisplayContent;
    public final DisplayPolicy mDisplayPolicy;
    public final DisplayRotationCoordinator mDisplayRotationCoordinator;
    public final DisplayWindowSettings mDisplayWindowSettings;
    public int mFixedToUserRotation;
    public final FoldController mFoldController;
    public boolean mLandscapePreferred;
    int mLandscapeRotation;
    public int mLastOrientation;
    public int mLastSensorRotation;
    public final int mLidOpenRotation;
    public final Object mLock;
    public OrientationListener mOrientationListener;
    int mPortraitRotation;
    public boolean mRotatingSeamlessly;
    public int mRotation;
    public int mRotationChoiceShownToUserForConfirmation;
    public final RotationHistory mRotationHistory;
    public int mSeamlessRotationCount;
    int mSeascapeRotation;
    public final WindowManagerService mService;
    public SettingsObserver mSettingsObserver;
    public int mShowRotationSuggestions;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public final boolean mSupportAutoRotation;
    public int mTableMode;
    public final RotationAnimationPair mTmpRotationAnim;
    public final int mUndockedHdmiRotation;
    int mUpsideDownRotation;
    public int mUserRotation;
    public int mUserRotationMode;
    public boolean mUserRotationUpdateCompleted;

    public class RotationAnimationPair {
        public int mEnter;
        public int mExit;

        public RotationAnimationPair() {
        }
    }

    public DisplayRotation(WindowManagerService windowManagerService, DisplayContent displayContent, DisplayAddress displayAddress, DeviceStateController deviceStateController, DisplayRotationCoordinator displayRotationCoordinator) {
        this(windowManagerService, displayContent, displayAddress, displayContent.getDisplayPolicy(), windowManagerService.mDisplayWindowSettings, windowManagerService.mContext, windowManagerService.getWindowManagerLock(), deviceStateController, displayRotationCoordinator);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DisplayRotation(WindowManagerService windowManagerService, DisplayContent displayContent, DisplayAddress displayAddress, DisplayPolicy displayPolicy, DisplayWindowSettings displayWindowSettings, Context context, Object obj, DeviceStateController deviceStateController, DisplayRotationCoordinator displayRotationCoordinator) {
        this.mTmpRotationAnim = new RotationAnimationPair();
        this.mRotationHistory = new RotationHistory();
        this.mCurrentAppOrientation = -1;
        this.mLastOrientation = -1;
        this.mLastSensorRotation = -1;
        this.mRotationChoiceShownToUserForConfirmation = -1;
        this.mAllowAllRotations = -1;
        this.mUserRotationMode = 0;
        this.mUserRotation = 0;
        this.mCameraRotationMode = 0;
        this.mFixedToUserRotation = 0;
        this.mUserRotationUpdateCompleted = true;
        this.mTableMode = 0;
        this.mLandscapePreferred = false;
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayPolicy = displayPolicy;
        this.mDisplayWindowSettings = displayWindowSettings;
        this.mContext = context;
        this.mLock = obj;
        this.mDeviceStateController = deviceStateController;
        boolean z = displayContent.isDefaultDisplay;
        this.isDefaultDisplay = z;
        this.mCompatPolicyForImmersiveApps = initImmersiveAppCompatPolicy(windowManagerService, displayContent);
        boolean z2 = context.getResources().getBoolean(R.bool.config_settingsHelpLinksEnabled);
        this.mSupportAutoRotation = z2;
        this.mAllowRotationResolver = context.getResources().getBoolean(R.bool.config_allowRotationResolver);
        this.mLidOpenRotation = readRotation(R.integer.config_maxResolverActivityColumns);
        this.mCarDockRotation = readRotation(R.integer.config_defaultNightDisplayCustomEndTime);
        this.mDeskDockRotation = readRotation(R.integer.config_doublelineClockDefault);
        this.mUndockedHdmiRotation = readRotation(R.integer.config_vibrationWaveformRampDownDuration);
        int readDefaultDisplayRotation = readDefaultDisplayRotation(displayAddress);
        this.mRotation = readDefaultDisplayRotation;
        this.mDisplayRotationCoordinator = displayRotationCoordinator;
        if (z) {
            displayRotationCoordinator.setDefaultDisplayDefaultRotation(readDefaultDisplayRotation);
        }
        Runnable runnable = new Runnable() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DisplayRotation.this.updateRotationAndSendNewConfigIfChanged();
            }
        };
        this.mDefaultDisplayRotationChangedCallback = runnable;
        if (DisplayRotationCoordinator.isSecondaryInternalDisplay(displayContent) && deviceStateController.shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay()) {
            displayRotationCoordinator.setDefaultDisplayRotationChangedCallback(runnable);
        }
        if (z) {
            Handler handler = UiThread.getHandler();
            OrientationListener orientationListener = new OrientationListener(context, handler, readDefaultDisplayRotation);
            this.mOrientationListener = orientationListener;
            orientationListener.setCurrentRotation(this.mRotation);
            SettingsObserver settingsObserver = new SettingsObserver(handler);
            this.mSettingsObserver = settingsObserver;
            settingsObserver.observe();
            if (z2 && context.getResources().getBoolean(R.bool.config_tvExternalInputLoggingDisplayNameFilterEnabled)) {
                this.mFoldController = new FoldController();
                return;
            } else {
                this.mFoldController = null;
                return;
            }
        }
        this.mFoldController = null;
    }

    public DisplayRotationImmersiveAppCompatPolicy initImmersiveAppCompatPolicy(WindowManagerService windowManagerService, DisplayContent displayContent) {
        return DisplayRotationImmersiveAppCompatPolicy.createIfNeeded(windowManagerService.mLetterboxConfiguration, this, displayContent);
    }

    public final int readDefaultDisplayRotation(DisplayAddress displayAddress) {
        if (!(displayAddress instanceof DisplayAddress.Physical)) {
            return 0;
        }
        if (this.isDefaultDisplay) {
            return 1;
        }
        String str = SystemProperties.get("ro.bootanim.set_orientation_" + ((DisplayAddress.Physical) displayAddress).getPhysicalDisplayId(), "ORIENTATION_0");
        if (str.equals("ORIENTATION_90")) {
            return 1;
        }
        if (str.equals("ORIENTATION_180")) {
            return 2;
        }
        return str.equals("ORIENTATION_270") ? 3 : 0;
    }

    public final int readRotation(int i) {
        try {
            int integer = this.mContext.getResources().getInteger(i);
            if (integer == 0) {
                return 0;
            }
            if (integer == 90) {
                return 1;
            }
            if (integer != 180) {
                return integer != 270 ? -1 : 3;
            }
            return 2;
        } catch (Resources.NotFoundException unused) {
            return -1;
        }
    }

    public boolean useDefaultSettingsProvider() {
        return this.isDefaultDisplay;
    }

    public void updateUserDependentConfiguration(Resources resources) {
        this.mAllowSeamlessRotationDespiteNavBarMoving = resources.getBoolean(R.bool.config_allowSeamlessRotationDespiteNavBarMoving);
    }

    public void configure(int i, int i2) {
        boolean z;
        Resources resources = this.mContext.getResources();
        if (i > i2) {
            this.mLandscapeRotation = 0;
            this.mSeascapeRotation = 2;
            if (resources.getBoolean(R.bool.config_notificationHeaderClickableForExpand)) {
                this.mPortraitRotation = 1;
                this.mUpsideDownRotation = 3;
            } else {
                this.mPortraitRotation = 3;
                this.mUpsideDownRotation = 1;
            }
        } else {
            this.mPortraitRotation = 0;
            this.mUpsideDownRotation = 2;
            if (resources.getBoolean(R.bool.config_notificationHeaderClickableForExpand)) {
                this.mLandscapeRotation = 3;
                this.mSeascapeRotation = 1;
            } else {
                this.mLandscapeRotation = 1;
                this.mSeascapeRotation = 3;
            }
        }
        if ("portrait".equals(SystemProperties.get("persist.demo.hdmirotation"))) {
            this.mDemoHdmiRotation = this.mPortraitRotation;
        } else {
            this.mDemoHdmiRotation = this.mLandscapeRotation;
        }
        this.mDemoHdmiRotationLock = SystemProperties.getBoolean("persist.demo.hdmirotationlock", false);
        if ("portrait".equals(SystemProperties.get("persist.demo.remoterotation"))) {
            this.mDemoRotation = this.mPortraitRotation;
        } else {
            this.mDemoRotation = this.mLandscapeRotation;
        }
        this.mDemoRotationLock = SystemProperties.getBoolean("persist.demo.rotationlock", false);
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
        boolean hasSystemFeature2 = this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        boolean z2 = this.mDisplayContent.getDisplayId() == 2;
        if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
            DisplayContent displayContent = this.mDisplayContent;
            if (!displayContent.isDefaultDisplay && displayContent.isNewDexMode()) {
                z = true;
                this.mDefaultFixedToUserRotation = (hasSystemFeature || hasSystemFeature2 || this.mService.mIsPc || this.mDisplayContent.forceDesktopMode() || z2 || z) && !"true".equals(SystemProperties.get("config.override_forced_orient"));
            }
        }
        z = false;
        if (hasSystemFeature) {
            this.mDefaultFixedToUserRotation = (hasSystemFeature || hasSystemFeature2 || this.mService.mIsPc || this.mDisplayContent.forceDesktopMode() || z2 || z) && !"true".equals(SystemProperties.get("config.override_forced_orient"));
        }
        this.mDefaultFixedToUserRotation = (hasSystemFeature || hasSystemFeature2 || this.mService.mIsPc || this.mDisplayContent.forceDesktopMode() || z2 || z) && !"true".equals(SystemProperties.get("config.override_forced_orient"));
    }

    public void applyCurrentRotation(int i) {
        this.mRotationHistory.addRecord(this, i);
        OrientationListener orientationListener = this.mOrientationListener;
        if (orientationListener != null) {
            orientationListener.setCurrentRotation(i);
        }
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public int getLastOrientation() {
        return this.mLastOrientation;
    }

    public boolean updateOrientation(int i, boolean z) {
        if (i == this.mLastOrientation && !z) {
            return false;
        }
        this.mLastOrientation = i;
        if (i != this.mCurrentAppOrientation) {
            this.mCurrentAppOrientation = i;
            if (this.isDefaultDisplay) {
                updateOrientationListenerLw();
            }
        }
        return updateRotationUnchecked(z);
    }

    public boolean updateRotationAndSendNewConfigIfChanged() {
        boolean updateRotationUnchecked = updateRotationUnchecked(false);
        if (updateRotationUnchecked) {
            this.mDisplayContent.sendNewConfiguration();
        }
        return updateRotationUnchecked;
    }

    public boolean updateRotationUnchecked(boolean z) {
        TransitionRequestInfo.DisplayChange displayChange;
        int displayId = this.mDisplayContent.getDisplayId();
        if (!z) {
            if (this.mDeferredRotationPauseCount > 0) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1497304204, 0, (String) null, (Object[]) null);
                }
                return false;
            }
            if (this.mDisplayContent.inTransition() && this.mDisplayContent.getDisplayPolicy().isScreenOnFully() && !this.mDisplayContent.mTransitionController.useShellTransitionsRotation()) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 292904800, 0, (String) null, (Object[]) null);
                }
                return false;
            }
            if (this.mService.mDisplayFrozen) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1947239194, 0, (String) null, (Object[]) null);
                }
                return false;
            }
            if (this.mDisplayContent.mFixedRotationTransitionListener.shouldDeferRotation()) {
                this.mLastOrientation = -2;
                return false;
            }
        }
        if (!this.mService.mDisplayEnabled) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1117599386, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        int i = this.mRotation;
        int i2 = this.mLastOrientation;
        int rotationForOrientation = rotationForOrientation(i2, i);
        FoldController foldController = this.mFoldController;
        if (foldController != null && foldController.shouldRevertOverriddenRotation()) {
            int revertOverriddenRotation = this.mFoldController.revertOverriddenRotation();
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1043981272, 0, (String) null, new Object[]{String.valueOf(Surface.rotationToString(revertOverriddenRotation)), String.valueOf(Surface.rotationToString(i)), String.valueOf(Surface.rotationToString(rotationForOrientation))});
            }
            rotationForOrientation = revertOverriddenRotation;
        }
        if (DisplayRotationCoordinator.isSecondaryInternalDisplay(this.mDisplayContent) && this.mDeviceStateController.shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay()) {
            rotationForOrientation = RotationUtils.reverseRotationDirectionAroundZAxis(this.mDisplayRotationCoordinator.getDefaultDisplayCurrentRotation());
        }
        if (ProtoLogCache.WM_FORCE_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_ORIENTATION, -1717651004, 4372, "Computed rotation=%s (%d) for display id=%d based on lastOrientation=%s (%d) and oldRotation=%s (%d), caller=%s", new Object[]{String.valueOf(Surface.rotationToString(rotationForOrientation)), Long.valueOf(rotationForOrientation), Long.valueOf(displayId), String.valueOf(ActivityInfo.screenOrientationToString(i2)), Long.valueOf(i2), String.valueOf(Surface.rotationToString(i)), Long.valueOf(i), String.valueOf(Debug.getCallers(6))});
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -766059044, 273, (String) null, new Object[]{Long.valueOf(displayId), String.valueOf(ActivityInfo.screenOrientationToString(i2)), Long.valueOf(i2), String.valueOf(Surface.rotationToString(rotationForOrientation)), Long.valueOf(rotationForOrientation)});
        }
        if (i == rotationForOrientation) {
            return false;
        }
        if (this.isDefaultDisplay) {
            this.mDisplayRotationCoordinator.onDefaultDisplayRotationChanged(rotationForOrientation);
        }
        RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
        if (recentsAnimationController != null) {
            recentsAnimationController.cancelAnimationForDisplayChange();
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            displayChange = null;
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1730156332, 85, (String) null, new Object[]{Long.valueOf(displayId), Long.valueOf(rotationForOrientation), Long.valueOf(i), Long.valueOf(i2)});
        } else {
            displayChange = null;
        }
        this.mRotation = rotationForOrientation;
        if (this.isDefaultDisplay) {
            this.mService.mExt.mPolicyExt.setRotation(rotationForOrientation);
        }
        if (CoreRune.FW_TSP_STATE_CONTROLLER && this.isDefaultDisplay) {
            this.mService.mExt.mTspStateController.setOrientation(isAnyPortrait(rotationForOrientation));
        }
        if (CoreRune.FW_VRR_PERFORMANCE && this.isDefaultDisplay) {
            this.mService.mPowerManagerInternal.setPowerBoost(0, 0);
        }
        this.mDisplayContent.setLayoutNeeded();
        this.mDisplayContent.mWaitingForConfig = true;
        this.mService.mExt.postRotationInfoForAudioManager();
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            boolean isCollecting = this.mDisplayContent.mTransitionController.isCollecting();
            this.mDisplayContent.requestChangeTransitionIfNeeded(536870912, isCollecting ? displayChange : new TransitionRequestInfo.DisplayChange(this.mDisplayContent.getDisplayId(), i, this.mRotation));
            if (isCollecting) {
                startRemoteRotation(i, this.mRotation);
            }
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mDisplayAreaPolicy != null && displayContent.getDefaultTaskDisplayArea().isSplitScreenStarting()) {
                this.mDisplayContent.setFadeInOutAnimationNeeded(true, "split_screen_starting");
            }
            return true;
        }
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.mWindowsFreezingScreen = 1;
        windowManagerService.f1749mH.sendNewMessageDelayed(11, this.mDisplayContent, 2000L);
        if (shouldRotateSeamlessly(i, rotationForOrientation, z)) {
            prepareSeamlessRotation();
        } else {
            prepareNormalRotationAnimation();
        }
        startRemoteRotation(i, this.mRotation);
        return true;
    }

    public final void startRemoteRotation(int i, final int i2) {
        this.mDisplayContent.mRemoteDisplayChangeController.performRemoteDisplayChange(i, i2, null, new RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
            public final void onContinueRemoteDisplayChange(WindowContainerTransaction windowContainerTransaction) {
                DisplayRotation.this.lambda$startRemoteRotation$0(i2, windowContainerTransaction);
            }
        });
    }

    /* renamed from: continueRotation, reason: merged with bridge method [inline-methods] */
    public final void lambda$startRemoteRotation$0(int i, WindowContainerTransaction windowContainerTransaction) {
        if (i != this.mRotation) {
            return;
        }
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            if (!this.mDisplayContent.mTransitionController.isCollecting()) {
                Slog.e(StartingSurfaceController.TAG, "Trying to continue rotation outside a transition");
            }
            DisplayContent displayContent = this.mDisplayContent;
            displayContent.mTransitionController.collect(displayContent);
        }
        this.mService.mAtmService.deferWindowLayout();
        try {
            this.mDisplayContent.sendNewConfiguration();
            if (windowContainerTransaction != null) {
                this.mService.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
            }
        } finally {
            this.mService.mAtmService.continueWindowLayout();
        }
    }

    public void prepareNormalRotationAnimation() {
        cancelSeamlessRotation();
        RotationAnimationPair selectRotationAnimation = selectRotationAnimation();
        this.mService.startFreezingDisplay(selectRotationAnimation.mExit, selectRotationAnimation.mEnter, this.mDisplayContent);
    }

    public void cancelSeamlessRotation() {
        if (this.mRotatingSeamlessly) {
            this.mDisplayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DisplayRotation.lambda$cancelSeamlessRotation$1((WindowState) obj);
                }
            }, true);
            this.mSeamlessRotationCount = 0;
            this.mRotatingSeamlessly = false;
            this.mDisplayContent.finishAsyncRotationIfPossible();
        }
    }

    public static /* synthetic */ void lambda$cancelSeamlessRotation$1(WindowState windowState) {
        if (windowState.mSeamlesslyRotated) {
            windowState.cancelSeamlessRotation();
            windowState.mSeamlesslyRotated = false;
        }
    }

    public final void prepareSeamlessRotation() {
        this.mSeamlessRotationCount = 0;
        this.mRotatingSeamlessly = true;
    }

    public boolean isRotatingSeamlessly() {
        return this.mRotatingSeamlessly;
    }

    public boolean hasSeamlessRotatingWindow() {
        return this.mSeamlessRotationCount > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean shouldRotateSeamlessly(int i, int i2, boolean z) {
        boolean z2;
        WindowState topFullscreenOpaqueWindow;
        if (this.isDefaultDisplay) {
            TaskDisplayArea defaultTaskDisplayArea = this.mDisplayContent.getDefaultTaskDisplayArea();
            boolean isSplitScreenVisible = defaultTaskDisplayArea.isSplitScreenVisible();
            boolean isSplitScreenStarting = defaultTaskDisplayArea.isSplitScreenStarting();
            if (isSplitScreenVisible || isSplitScreenStarting || this.mDisplayContent.hasVisibleFreeformTask()) {
                if (isSplitScreenStarting) {
                    this.mDisplayContent.setFadeInOutAnimationNeeded(true, "reject_seamless_rot(starting)");
                }
                z2 = true;
                if (!this.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
                    if (!z2) {
                        return true;
                    }
                    this.mDisplayContent.setFadeInOutAnimationNeeded(true, "reject_seamless_rot");
                    return false;
                }
                if (z2 || (topFullscreenOpaqueWindow = this.mDisplayPolicy.getTopFullscreenOpaqueWindow()) == null || topFullscreenOpaqueWindow != this.mDisplayContent.mCurrentFocus || ((topFullscreenOpaqueWindow.getAttrs().rotationAnimation != 3 && !topFullscreenOpaqueWindow.isAllowedSeamlessRotation()) || topFullscreenOpaqueWindow.inMultiWindowMode() || topFullscreenOpaqueWindow.isAnimatingLw() || (!canRotateSeamlessly(i, i2) && !topFullscreenOpaqueWindow.isAllowedSeamlessRotation()))) {
                    return false;
                }
                ActivityRecord activityRecord = topFullscreenOpaqueWindow.mActivityRecord;
                if ((activityRecord == null || activityRecord.matchParentBounds()) && !this.mDisplayContent.getDefaultTaskDisplayArea().hasPinnedTask() && !this.mDisplayContent.hasAlertWindowSurfaces()) {
                    return z || this.mDisplayContent.getWindow(new Predicate() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean z3;
                            z3 = ((WindowState) obj).mSeamlesslyRotated;
                            return z3;
                        }
                    }) == null;
                }
                return false;
            }
        }
        z2 = false;
        if (!this.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
        }
    }

    public boolean canRotateSeamlessly(int i, int i2) {
        if (this.mAllowSeamlessRotationDespiteNavBarMoving || this.mDisplayPolicy.navigationBarCanMove()) {
            return true;
        }
        return (i == 2 || i2 == 2) ? false : true;
    }

    public void markForSeamlessRotation(WindowState windowState, boolean z) {
        if (z == windowState.mSeamlesslyRotated || windowState.mForceSeamlesslyRotate) {
            return;
        }
        Slog.d(StartingSurfaceController.TAG, "markForSeamlessRotation, seamlesslyRotated=" + z + ", w=" + windowState + ", caller=" + Debug.getCallers(3));
        windowState.mSeamlesslyRotated = z;
        if (z) {
            this.mSeamlessRotationCount++;
        } else {
            this.mSeamlessRotationCount--;
        }
        if (this.mSeamlessRotationCount == 0) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, -576070986, 0, (String) null, (Object[]) null);
            }
            this.mRotatingSeamlessly = false;
            this.mDisplayContent.finishAsyncRotationIfPossible();
            updateRotationAndSendNewConfigIfChanged();
        }
    }

    public final RotationAnimationPair selectRotationAnimation() {
        boolean z = (this.mDisplayPolicy.isScreenOnFully() && this.mService.mPolicy.okToAnimate(false)) ? false : true;
        WindowState topFullscreenOpaqueWindow = this.mDisplayPolicy.getTopFullscreenOpaqueWindow();
        if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ANIM, 2019765997, 52, (String) null, new Object[]{String.valueOf(topFullscreenOpaqueWindow), Long.valueOf(topFullscreenOpaqueWindow == null ? 0L : topFullscreenOpaqueWindow.getAttrs().rotationAnimation), Boolean.valueOf(z)});
        }
        if (z) {
            RotationAnimationPair rotationAnimationPair = this.mTmpRotationAnim;
            rotationAnimationPair.mExit = R.anim.task_fragment_clear_top_close_exit;
            rotationAnimationPair.mEnter = R.anim.task_fragment_clear_top_close_enter;
            return rotationAnimationPair;
        }
        if (topFullscreenOpaqueWindow != null) {
            int rotationAnimationHint = topFullscreenOpaqueWindow.getRotationAnimationHint();
            if (rotationAnimationHint < 0 && this.mDisplayPolicy.isTopLayoutFullscreen()) {
                rotationAnimationHint = topFullscreenOpaqueWindow.getAttrs().rotationAnimation;
            }
            if (rotationAnimationHint != 1) {
                if (rotationAnimationHint == 2) {
                    RotationAnimationPair rotationAnimationPair2 = this.mTmpRotationAnim;
                    rotationAnimationPair2.mExit = R.anim.task_fragment_clear_top_close_exit;
                    rotationAnimationPair2.mEnter = R.anim.task_fragment_clear_top_close_enter;
                } else if (rotationAnimationHint != 3) {
                    RotationAnimationPair rotationAnimationPair3 = this.mTmpRotationAnim;
                    rotationAnimationPair3.mEnter = 0;
                    rotationAnimationPair3.mExit = 0;
                }
            }
            RotationAnimationPair rotationAnimationPair4 = this.mTmpRotationAnim;
            rotationAnimationPair4.mExit = R.anim.task_fragment_clear_top_open_enter;
            rotationAnimationPair4.mEnter = R.anim.task_fragment_clear_top_close_enter;
        } else {
            RotationAnimationPair rotationAnimationPair5 = this.mTmpRotationAnim;
            rotationAnimationPair5.mEnter = 0;
            rotationAnimationPair5.mExit = 0;
        }
        return this.mTmpRotationAnim;
    }

    public boolean validateRotationAnimation(int i, int i2, boolean z) {
        switch (i) {
            case R.anim.task_fragment_clear_top_close_exit:
            case R.anim.task_fragment_clear_top_open_enter:
                if (z) {
                    return false;
                }
                RotationAnimationPair selectRotationAnimation = selectRotationAnimation();
                return i == selectRotationAnimation.mExit && i2 == selectRotationAnimation.mEnter;
            default:
                return true;
        }
    }

    public void restoreSettings(int i, int i2, int i3) {
        this.mFixedToUserRotation = i3;
        if (this.isDefaultDisplay) {
            return;
        }
        if (i != 0 && i != 1) {
            Slog.w(StartingSurfaceController.TAG, "Trying to restore an invalid user rotation mode " + i + " for " + this.mDisplayContent);
            i = 0;
        }
        if (i2 < 0 || i2 > 3) {
            Slog.w(StartingSurfaceController.TAG, "Trying to restore an invalid user rotation " + i2 + " for " + this.mDisplayContent);
            i2 = 0;
        }
        this.mUserRotationMode = i;
        this.mUserRotation = i2;
    }

    public void setFixedToUserRotation(int i) {
        if (this.mFixedToUserRotation == i) {
            return;
        }
        this.mFixedToUserRotation = i;
        this.mDisplayWindowSettings.setFixedToUserRotation(this.mDisplayContent, i);
        DisplayContent displayContent = this.mDisplayContent;
        ActivityRecord activityRecord = displayContent.mFocusedApp;
        if (activityRecord != null) {
            displayContent.onLastFocusedTaskDisplayAreaChanged(activityRecord.getDisplayArea());
        }
        this.mDisplayContent.updateOrientation();
    }

    public void setUserRotation(int i, int i2) {
        boolean z;
        this.mRotationChoiceShownToUserForConfirmation = -1;
        if (useDefaultSettingsProvider()) {
            if (this.mUserRotation != i2) {
                this.mUserRotationUpdateCompleted = false;
            }
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Settings.System.putIntForUser(contentResolver, "accelerometer_rotation", i != 1 ? 1 : 0, -2);
            Settings.System.putIntForUser(contentResolver, "user_rotation", i2, -2);
            return;
        }
        if (this.mUserRotationMode != i) {
            this.mUserRotationMode = i;
            z = true;
        } else {
            z = false;
        }
        if (this.mUserRotation != i2) {
            this.mUserRotation = i2;
            z = true;
        }
        this.mDisplayWindowSettings.setUserRotation(this.mDisplayContent, i, i2);
        if (z) {
            this.mService.updateRotation(true, false);
        }
    }

    public void freezeRotation(int i) {
        if (this.mDeviceStateController.shouldReverseRotationDirectionAroundZAxis(this.mDisplayContent)) {
            i = RotationUtils.reverseRotationDirectionAroundZAxis(i);
        }
        if (i == -1) {
            i = this.mRotation;
        }
        setUserRotation(1, i);
    }

    public void thawRotation() {
        setUserRotation(0, this.mUserRotation);
    }

    public boolean isRotationFrozen() {
        return !this.isDefaultDisplay ? this.mUserRotationMode == 1 : Settings.System.getIntForUser(this.mContext.getContentResolver(), "accelerometer_rotation", 0, -2) == 0;
    }

    public boolean isFixedToUserRotation() {
        int i = this.mFixedToUserRotation;
        if (i == 1) {
            return false;
        }
        if (i != 2) {
            return this.mDefaultFixedToUserRotation;
        }
        return true;
    }

    public int getFixedToUserRotationMode() {
        return this.mFixedToUserRotation;
    }

    public int getCurrentAppOrientation() {
        return this.mCurrentAppOrientation;
    }

    public DisplayPolicy getDisplayPolicy() {
        return this.mDisplayPolicy;
    }

    public WindowOrientationListener getOrientationListener() {
        return this.mOrientationListener;
    }

    public int getUserRotation() {
        return this.mUserRotation;
    }

    public int getUserRotationMode() {
        return this.mUserRotationMode;
    }

    public void updateOrientationListener() {
        synchronized (this.mLock) {
            updateOrientationListenerLw();
        }
    }

    public void pause() {
        this.mDeferredRotationPauseCount++;
    }

    public void resume() {
        int i = this.mDeferredRotationPauseCount;
        if (i <= 0) {
            return;
        }
        int i2 = i - 1;
        this.mDeferredRotationPauseCount = i2;
        if (i2 == 0) {
            updateRotationAndSendNewConfigIfChanged();
        }
    }

    public final void updateOrientationListenerLw() {
        boolean z;
        OrientationListener orientationListener = this.mOrientationListener;
        if (orientationListener == null || !orientationListener.canDetectOrientation()) {
            return;
        }
        boolean isScreenOnEarly = this.mDisplayPolicy.isScreenOnEarly();
        boolean isAwake = this.mDisplayPolicy.isAwake();
        boolean isKeyguardDrawComplete = this.mDisplayPolicy.isKeyguardDrawComplete();
        boolean isWindowManagerDrawComplete = this.mDisplayPolicy.isWindowManagerDrawComplete();
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1868124841, 4063, (String) null, new Object[]{Boolean.valueOf(isScreenOnEarly), Boolean.valueOf(isAwake), Long.valueOf(this.mCurrentAppOrientation), Boolean.valueOf(this.mOrientationListener.mEnabled), Boolean.valueOf(isKeyguardDrawComplete), Boolean.valueOf(isWindowManagerDrawComplete)});
        }
        if (isScreenOnEarly && ((isAwake || this.mOrientationListener.shouldStayEnabledWhileDreaming()) && isKeyguardDrawComplete && isWindowManagerDrawComplete && needSensorRunning())) {
            OrientationListener orientationListener2 = this.mOrientationListener;
            if (!orientationListener2.mEnabled) {
                orientationListener2.enable();
            }
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mOrientationListener.disable();
        }
    }

    public final boolean needSensorRunning() {
        int i;
        if (isFixedToUserRotation() || shouldDisableRotationSensor()) {
            return false;
        }
        FoldController foldController = this.mFoldController;
        if (foldController != null && foldController.shouldDisableRotationSensor()) {
            return false;
        }
        if (this.mSupportAutoRotation && ((i = this.mCurrentAppOrientation) == 4 || i == 10 || i == 7 || i == 6)) {
            return true;
        }
        int dockMode = this.mDisplayPolicy.getDockMode();
        if ((this.mDisplayPolicy.isCarDockEnablesAccelerometer() && dockMode == 2) || (this.mDisplayPolicy.isDeskDockEnablesAccelerometer() && (dockMode == 1 || dockMode == 3 || dockMode == 4))) {
            return true;
        }
        if (this.mUserRotationMode == 1) {
            return this.mSupportAutoRotation && this.mShowRotationSuggestions == 1;
        }
        return this.mSupportAutoRotation;
    }

    public boolean needsUpdate() {
        int i = this.mRotation;
        return i != rotationForOrientation(this.mLastOrientation, i);
    }

    public void resetAllowAllRotations() {
        this.mAllowAllRotations = -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:136:0x017f, code lost:
    
        if (r14 != 13) goto L112;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int rotationForOrientation(int i, int i2) {
        int i3;
        int i4;
        if (ProtoLogCache.WM_FORCE_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_ORIENTATION, 773949334, 1092, "rotationForOrientation(orient=%s (%d), last=%s (%d)); user=%s (%d) %s", new Object[]{String.valueOf(ActivityInfo.screenOrientationToString(i)), Long.valueOf(i), String.valueOf(Surface.rotationToString(i2)), Long.valueOf(i2), String.valueOf(Surface.rotationToString(this.mUserRotation)), Long.valueOf(this.mUserRotation), this.mUserRotationMode == 1 ? "USER_ROTATION_LOCKED" : ""});
        }
        Slog.d(StartingSurfaceController.TAG, "rotationForOrientation, orientationSource=" + this.mDisplayContent.getLastOrientationSource());
        if (CoreRune.FW_ORIENTATION_CONTROL) {
            this.mService.mAtmService.mExt.mOrientationController.logInRotationForOrientation(this.mDisplayContent);
        }
        if (isFixedToUserRotation()) {
            return this.mUserRotation;
        }
        OrientationListener orientationListener = this.mOrientationListener;
        int i5 = -1;
        int proposedRotation = orientationListener != null ? orientationListener.getProposedRotation() : -1;
        FoldController foldController = this.mFoldController;
        if (foldController != null && foldController.shouldIgnoreSensorRotation()) {
            proposedRotation = -1;
        }
        if (this.mDeviceStateController.shouldReverseRotationDirectionAroundZAxis(this.mDisplayContent)) {
            proposedRotation = RotationUtils.reverseRotationDirectionAroundZAxis(proposedRotation);
        }
        this.mLastSensorRotation = proposedRotation;
        if (proposedRotation < 0) {
            proposedRotation = i2;
        }
        int lidState = this.mDisplayPolicy.getLidState();
        int dockMode = this.mDisplayPolicy.getDockMode();
        boolean isHdmiPlugged = this.mDisplayPolicy.isHdmiPlugged();
        boolean isCarDockEnablesAccelerometer = this.mDisplayPolicy.isCarDockEnablesAccelerometer();
        boolean isDeskDockEnablesAccelerometer = this.mDisplayPolicy.isDeskDockEnablesAccelerometer();
        if (!this.isDefaultDisplay) {
            i5 = this.mUserRotation;
        } else if (lidState != 1 || (i4 = this.mLidOpenRotation) < 0) {
            if (dockMode != 2 || (!isCarDockEnablesAccelerometer && this.mCarDockRotation < 0)) {
                if ((dockMode == 1 || dockMode == 3 || dockMode == 4) && !((!isDeskDockEnablesAccelerometer && this.mDeskDockRotation < 0) || i == 14 || i == 5)) {
                    if (!isDeskDockEnablesAccelerometer) {
                        proposedRotation = this.mDeskDockRotation;
                    }
                } else if (isHdmiPlugged && this.mDemoHdmiRotationLock) {
                    i5 = this.mDemoHdmiRotation;
                } else if (isHdmiPlugged && dockMode == 0 && (i3 = this.mUndockedHdmiRotation) >= 0) {
                    i5 = i3;
                } else if (this.mDemoRotationLock) {
                    i5 = this.mDemoRotation;
                } else if (this.mDisplayPolicy.isPersistentVrModeEnabled()) {
                    i5 = this.mPortraitRotation;
                } else {
                    if (i != 14) {
                        if (this.mSupportAutoRotation) {
                            if (((this.mUserRotationMode == 0 || isTabletopAutoRotateOverrideEnabled()) && (i == 2 || i == -1 || i == 11 || i == 12 || i == 13)) || i == 4 || i == 10 || i == 6 || i == 7) {
                                if (proposedRotation == 2) {
                                    if (getAllowAllRotations() != 1) {
                                        if (i != 10) {
                                        }
                                    }
                                }
                            } else if (this.mUserRotationMode == 1 && i != 5 && i != 0 && i != 1 && i != 8 && i != 9) {
                                i5 = this.mUserRotation;
                            }
                        }
                    }
                    i5 = i2;
                }
            } else if (!isCarDockEnablesAccelerometer) {
                proposedRotation = this.mCarDockRotation;
            }
            i5 = proposedRotation;
        } else {
            i5 = i4;
        }
        if (i == 0) {
            return isLandscapeOrSeascape(i5) ? i5 : this.mLandscapeRotation;
        }
        if (i == 1) {
            return isAnyPortrait(i5) ? i5 : this.mPortraitRotation;
        }
        if (i != 11) {
            if (i != 12) {
                switch (i) {
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        return isLandscapeOrSeascape(i5) ? i5 : this.mSeascapeRotation;
                    case 9:
                        return isAnyPortrait(i5) ? i5 : this.mUpsideDownRotation;
                    default:
                        if (i5 >= 0) {
                            return i5;
                        }
                        return 0;
                }
            }
            return isAnyPortrait(i5) ? i5 : isAnyPortrait(i2) ? i2 : this.mPortraitRotation;
        }
        return isLandscapeOrSeascape(i5) ? i5 : isLandscapeOrSeascape(i2) ? i2 : this.mLandscapeRotation;
    }

    public final int getAllowAllRotations() {
        if (this.mAllowAllRotations == -1) {
            this.mAllowAllRotations = CoreRune.FW_ALLOW_ALL_ROTATION ? 1 : 0;
        }
        return this.mAllowAllRotations;
    }

    public boolean isLandscapeOrSeascape(int i) {
        return i == this.mLandscapeRotation || i == this.mSeascapeRotation;
    }

    public boolean isAnyPortrait(int i) {
        return i == this.mPortraitRotation || i == this.mUpsideDownRotation;
    }

    public final boolean isValidRotationChoice(int i) {
        int i2 = this.mCurrentAppOrientation;
        if (i2 == -1 || i2 == 2) {
            return getAllowAllRotations() == 1 ? i >= 0 : i >= 0 && i != 2;
        }
        switch (i2) {
            case 11:
                return isLandscapeOrSeascape(i);
            case 12:
                return i == this.mPortraitRotation;
            case 13:
                return i >= 0;
            default:
                return false;
        }
    }

    public final boolean isTabletopAutoRotateOverrideEnabled() {
        FoldController foldController = this.mFoldController;
        return foldController != null && foldController.overrideFrozenRotation();
    }

    public final boolean isRotationChoiceAllowed(int i) {
        int dockMode;
        DisplayRotationImmersiveAppCompatPolicy displayRotationImmersiveAppCompatPolicy = this.mCompatPolicyForImmersiveApps;
        if (!(displayRotationImmersiveAppCompatPolicy != null && displayRotationImmersiveAppCompatPolicy.isRotationLockEnforced(i)) && this.mUserRotationMode != 1) {
            return false;
        }
        FoldController foldController = this.mFoldController;
        if ((foldController != null && foldController.shouldIgnoreSensorRotation()) || isTabletopAutoRotateOverrideEnabled() || isFixedToUserRotation()) {
            return false;
        }
        if ((this.mDisplayPolicy.getLidState() == 1 && this.mLidOpenRotation >= 0) || (dockMode = this.mDisplayPolicy.getDockMode()) == 2) {
            return false;
        }
        boolean isDeskDockEnablesAccelerometer = this.mDisplayPolicy.isDeskDockEnablesAccelerometer();
        if ((dockMode == 1 || dockMode == 3 || dockMode == 4) && !isDeskDockEnablesAccelerometer) {
            return false;
        }
        boolean isHdmiPlugged = this.mDisplayPolicy.isHdmiPlugged();
        if (isHdmiPlugged && this.mDemoHdmiRotationLock) {
            return false;
        }
        if ((isHdmiPlugged && dockMode == 0 && this.mUndockedHdmiRotation >= 0) || this.mDemoRotationLock || this.mDisplayPolicy.isPersistentVrModeEnabled() || !this.mSupportAutoRotation) {
            return false;
        }
        int i2 = this.mCurrentAppOrientation;
        if (i2 != -1 && i2 != 2) {
            switch (i2) {
            }
            return false;
        }
        return true;
    }

    public final void sendProposedRotationChangeToStatusBarInternal(int i, boolean z) {
        if (this.mStatusBarManagerInternal == null) {
            this.mStatusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
        }
        StatusBarManagerInternal statusBarManagerInternal = this.mStatusBarManagerInternal;
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.onProposedRotationChanged(i, z);
        }
    }

    public void dispatchProposedRotation(int i) {
        if (this.mService.mRotationWatcherController.hasProposedRotationListeners()) {
            synchronized (this.mLock) {
                this.mService.mRotationWatcherController.dispatchProposedRotation(this.mDisplayContent, i);
            }
        }
    }

    public static String allowAllRotationsToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? Integer.toString(i) : "true" : "false" : "unknown";
    }

    public void onUserSwitch() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.onChange(false);
        }
    }

    public void onDisplayRemoved() {
        removeDefaultDisplayRotationChangedCallback();
        FoldController foldController = this.mFoldController;
        if (foldController != null) {
            foldController.onDisplayRemoved();
        }
    }

    public final boolean updateSettings() {
        boolean z;
        boolean z2;
        boolean z3;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        synchronized (this.mLock) {
            z = true;
            int intForUser = ActivityManager.isLowRamDeviceStatic() ? 0 : Settings.Secure.getIntForUser(contentResolver, "show_rotation_suggestions", 1, -2);
            if (this.mShowRotationSuggestions != intForUser) {
                this.mShowRotationSuggestions = intForUser;
                z2 = true;
            } else {
                z2 = false;
            }
            int intForUser2 = Settings.System.getIntForUser(contentResolver, "user_rotation", 0, -2);
            if (this.mUserRotation != intForUser2) {
                this.mUserRotation = intForUser2;
                z3 = true;
            } else {
                z3 = false;
            }
            int i = Settings.System.getIntForUser(contentResolver, "accelerometer_rotation", 0, -2) != 0 ? 0 : 1;
            if (this.mUserRotationMode != i) {
                this.mUserRotationMode = i;
                z2 = true;
                z3 = true;
            }
            if (z2) {
                updateOrientationListenerLw();
            }
            int intForUser3 = Settings.Secure.getIntForUser(contentResolver, "camera_autorotate", 0, -2);
            if (this.mCameraRotationMode != intForUser3) {
                this.mCameraRotationMode = intForUser3;
            } else {
                z = z3;
            }
        }
        return z;
    }

    public void removeDefaultDisplayRotationChangedCallback() {
        if (DisplayRotationCoordinator.isSecondaryInternalDisplay(this.mDisplayContent)) {
            this.mDisplayRotationCoordinator.removeDefaultDisplayRotationChangedCallback();
        }
    }

    public void onSetRequestedOrientation() {
        int i;
        if (this.mCompatPolicyForImmersiveApps == null || (i = this.mRotationChoiceShownToUserForConfirmation) == -1) {
            return;
        }
        this.mOrientationListener.onProposedRotationChanged(i);
    }

    public int getRotationLockOrientation() {
        int i = this.mUserRotation;
        if (!this.mUserRotationUpdateCompleted) {
            i = Settings.System.getIntForUser(this.mContext.getContentResolver(), "user_rotation", 0, -2);
        }
        return isAnyPortrait(i) ? 1 : 2;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + "DisplayRotation");
        printWriter.println(str + "  mCurrentAppOrientation=" + ActivityInfo.screenOrientationToString(this.mCurrentAppOrientation));
        printWriter.println(str + "  mLastOrientation=" + this.mLastOrientation);
        printWriter.print(str + "  mRotation=" + this.mRotation);
        StringBuilder sb = new StringBuilder();
        sb.append(" mDeferredRotationPauseCount=");
        sb.append(this.mDeferredRotationPauseCount);
        printWriter.println(sb.toString());
        printWriter.print(str + "  mLandscapeRotation=" + Surface.rotationToString(this.mLandscapeRotation));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" mSeascapeRotation=");
        sb2.append(Surface.rotationToString(this.mSeascapeRotation));
        printWriter.println(sb2.toString());
        printWriter.print(str + "  mPortraitRotation=" + Surface.rotationToString(this.mPortraitRotation));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(" mUpsideDownRotation=");
        sb3.append(Surface.rotationToString(this.mUpsideDownRotation));
        printWriter.println(sb3.toString());
        printWriter.println(str + "  mSupportAutoRotation=" + this.mSupportAutoRotation);
        OrientationListener orientationListener = this.mOrientationListener;
        if (orientationListener != null) {
            orientationListener.dump(printWriter, str + "  ");
        }
        printWriter.println();
        printWriter.print(str + "  mCarDockRotation=" + Surface.rotationToString(this.mCarDockRotation));
        StringBuilder sb4 = new StringBuilder();
        sb4.append(" mDeskDockRotation=");
        sb4.append(Surface.rotationToString(this.mDeskDockRotation));
        printWriter.println(sb4.toString());
        printWriter.print(str + "  mUserRotationMode=" + WindowManagerPolicy.userRotationModeToString(this.mUserRotationMode));
        StringBuilder sb5 = new StringBuilder();
        sb5.append(" mUserRotation=");
        sb5.append(Surface.rotationToString(this.mUserRotation));
        printWriter.print(sb5.toString());
        printWriter.print(" mCameraRotationMode=" + this.mCameraRotationMode);
        printWriter.println(" mAllowAllRotations=" + allowAllRotationsToString(this.mAllowAllRotations));
        printWriter.print(str + "  mDemoHdmiRotation=" + Surface.rotationToString(this.mDemoHdmiRotation));
        StringBuilder sb6 = new StringBuilder();
        sb6.append(" mDemoHdmiRotationLock=");
        sb6.append(this.mDemoHdmiRotationLock);
        printWriter.print(sb6.toString());
        printWriter.println(" mUndockedHdmiRotation=" + Surface.rotationToString(this.mUndockedHdmiRotation));
        printWriter.println(str + "  mLidOpenRotation=" + Surface.rotationToString(this.mLidOpenRotation));
        printWriter.println(str + "  mFixedToUserRotation=" + isFixedToUserRotation());
        if (this.mFoldController != null) {
            printWriter.println(str + "FoldController");
            printWriter.println(str + "  mPauseAutorotationDuringUnfolding=" + this.mFoldController.mPauseAutorotationDuringUnfolding);
            printWriter.println(str + "  mShouldDisableRotationSensor=" + this.mFoldController.mShouldDisableRotationSensor);
            printWriter.println(str + "  mShouldIgnoreSensorRotation=" + this.mFoldController.mShouldIgnoreSensorRotation);
            printWriter.println(str + "  mLastDisplaySwitchTime=" + this.mFoldController.mLastDisplaySwitchTime);
            printWriter.println(str + "  mLastHingeAngleEventTime=" + this.mFoldController.mLastHingeAngleEventTime);
            printWriter.println(str + "  mDeviceState=" + this.mFoldController.mDeviceState);
        }
        if (this.mRotatingSeamlessly || this.mSeamlessRotationCount > 0) {
            printWriter.print(str + "  mRotatingSeamlessly=" + this.mRotatingSeamlessly);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(" mSeamlessRotationCount=");
            sb7.append(this.mSeamlessRotationCount);
            printWriter.println(sb7.toString());
        }
        if (this.mRotationHistory.mRecords.isEmpty()) {
            return;
        }
        printWriter.println();
        printWriter.println(str + "  RotationHistory");
        String str2 = "    " + str;
        Iterator it = this.mRotationHistory.mRecords.iterator();
        while (it.hasNext()) {
            ((RotationHistory.Record) it.next()).dump(str2, printWriter);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, getRotation());
        protoOutputStream.write(1133871366146L, isRotationFrozen());
        protoOutputStream.write(1120986464259L, getUserRotation());
        protoOutputStream.write(1120986464260L, this.mFixedToUserRotation);
        protoOutputStream.write(1120986464261L, this.mLastOrientation);
        protoOutputStream.write(1133871366150L, isFixedToUserRotation());
        protoOutputStream.end(start);
    }

    public boolean isDeviceInPosture(DeviceStateController.DeviceState deviceState, boolean z) {
        FoldController foldController = this.mFoldController;
        if (foldController == null) {
            return false;
        }
        return foldController.isDeviceInPosture(deviceState, z);
    }

    public boolean isDisplaySeparatingHinge() {
        FoldController foldController = this.mFoldController;
        return foldController != null && foldController.isSeparatingHinge();
    }

    public void foldStateChanged(DeviceStateController.DeviceState deviceState) {
        if (this.mFoldController != null) {
            synchronized (this.mLock) {
                this.mFoldController.foldStateChanged(deviceState);
            }
        }
    }

    public void physicalDisplayChanged() {
        FoldController foldController = this.mFoldController;
        if (foldController != null) {
            foldController.onPhysicalDisplayChanged();
        }
    }

    public long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public class FoldController {
        public final Runnable mActivityBoundsUpdateCallback;
        public int mDisplaySwitchRotationBlockTimeMs;
        public int mHingeAngleRotationBlockTimeMs;
        public SensorEventListener mHingeAngleSensorEventListener;
        public final boolean mIsDisplayAlwaysSeparatingHinge;
        public int mMaxHingeAngle;
        public final boolean mPauseAutorotationDuringUnfolding;
        public SensorManager mSensorManager;
        public boolean mShouldDisableRotationSensor;
        public boolean mShouldIgnoreSensorRotation;
        public int mHalfFoldSavedRotation = -1;
        public DeviceStateController.DeviceState mDeviceState = DeviceStateController.DeviceState.UNKNOWN;
        public long mLastHingeAngleEventTime = 0;
        public long mLastDisplaySwitchTime = 0;
        public boolean mInHalfFoldTransition = false;
        public final Set mTabletopRotations = new ArraySet();

        public FoldController() {
            int[] intArray = DisplayRotation.this.mContext.getResources().getIntArray(R.array.config_supported_cellular_usage_settings);
            if (intArray != null) {
                for (int i : intArray) {
                    if (i == 0) {
                        this.mTabletopRotations.add(0);
                    } else if (i == 90) {
                        this.mTabletopRotations.add(1);
                    } else if (i == 180) {
                        this.mTabletopRotations.add(2);
                    } else if (i == 270) {
                        this.mTabletopRotations.add(3);
                    } else if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                        ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_ORIENTATION, -637815408, 1, (String) null, new Object[]{Long.valueOf(i)});
                    }
                }
            } else if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_DEBUG_ORIENTATION, 939638078, 0, (String) null, (Object[]) null);
            }
            this.mIsDisplayAlwaysSeparatingHinge = DisplayRotation.this.mContext.getResources().getBoolean(R.bool.config_flipToScreenOffEnabled);
            this.mActivityBoundsUpdateCallback = new RunnableC29111(DisplayRotation.this);
            boolean z = DisplayRotation.this.mContext.getResources().getBoolean(R.bool.config_ui_enableFadingMarquee);
            this.mPauseAutorotationDuringUnfolding = z;
            if (z) {
                this.mDisplaySwitchRotationBlockTimeMs = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_reduceBrightColorsStrengthDefault);
                this.mHingeAngleRotationBlockTimeMs = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_reduceBrightColorsStrengthMax);
                this.mMaxHingeAngle = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_reduceBrightColorsStrengthMin);
                registerSensorManager();
            }
        }

        /* renamed from: com.android.server.wm.DisplayRotation$FoldController$1 */
        public class RunnableC29111 implements Runnable {
            public final /* synthetic */ DisplayRotation val$this$0;

            public RunnableC29111(DisplayRotation displayRotation) {
                this.val$this$0 = displayRotation;
            }

            @Override // java.lang.Runnable
            public void run() {
                ActivityRecord activityRecord;
                if (FoldController.this.mDeviceState == DeviceStateController.DeviceState.OPEN || FoldController.this.mDeviceState == DeviceStateController.DeviceState.HALF_FOLDED) {
                    synchronized (DisplayRotation.this.mLock) {
                        Task task = DisplayRotation.this.mDisplayContent.getTask(new Predicate() { // from class: com.android.server.wm.DisplayRotation$FoldController$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$run$0;
                                lambda$run$0 = DisplayRotation.FoldController.RunnableC29111.lambda$run$0((Task) obj);
                                return lambda$run$0;
                            }
                        });
                        if (task != null && (activityRecord = task.topRunningActivity()) != null) {
                            activityRecord.recomputeConfiguration();
                        }
                    }
                }
            }

            public static /* synthetic */ boolean lambda$run$0(Task task) {
                return task.getWindowingMode() == 1;
            }
        }

        public final void registerSensorManager() {
            Sensor defaultSensor;
            SensorManager sensorManager = (SensorManager) DisplayRotation.this.mContext.getSystemService(SensorManager.class);
            this.mSensorManager = sensorManager;
            if (sensorManager == null || (defaultSensor = sensorManager.getDefaultSensor(36)) == null) {
                return;
            }
            SensorEventListener sensorEventListener = new SensorEventListener() { // from class: com.android.server.wm.DisplayRotation.FoldController.2
                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(Sensor sensor, int i) {
                }

                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    FoldController.this.onHingeAngleChanged(sensorEvent.values[0]);
                }
            };
            this.mHingeAngleSensorEventListener = sensorEventListener;
            this.mSensorManager.registerListener(sensorEventListener, defaultSensor, 0, DisplayRotation.this.getHandler());
        }

        public void onDisplayRemoved() {
            SensorEventListener sensorEventListener;
            SensorManager sensorManager = this.mSensorManager;
            if (sensorManager == null || (sensorEventListener = this.mHingeAngleSensorEventListener) == null) {
                return;
            }
            sensorManager.unregisterListener(sensorEventListener);
        }

        public boolean isDeviceInPosture(DeviceStateController.DeviceState deviceState, boolean z) {
            DeviceStateController.DeviceState deviceState2 = this.mDeviceState;
            if (deviceState != deviceState2) {
                return false;
            }
            return deviceState2 != DeviceStateController.DeviceState.HALF_FOLDED || z == this.mTabletopRotations.contains(Integer.valueOf(DisplayRotation.this.mRotation));
        }

        public boolean isSeparatingHinge() {
            DeviceStateController.DeviceState deviceState = this.mDeviceState;
            return deviceState == DeviceStateController.DeviceState.HALF_FOLDED || (deviceState == DeviceStateController.DeviceState.OPEN && this.mIsDisplayAlwaysSeparatingHinge);
        }

        public boolean overrideFrozenRotation() {
            return this.mDeviceState == DeviceStateController.DeviceState.HALF_FOLDED;
        }

        public boolean shouldRevertOverriddenRotation() {
            return this.mDeviceState == DeviceStateController.DeviceState.OPEN && !this.mShouldIgnoreSensorRotation && this.mInHalfFoldTransition && DisplayRotation.this.mDisplayContent.getRotationReversionController().isOverrideActive(2) && DisplayRotation.this.mUserRotationMode == 1;
        }

        public int revertOverriddenRotation() {
            int i = this.mHalfFoldSavedRotation;
            this.mHalfFoldSavedRotation = -1;
            DisplayRotation.this.mDisplayContent.getRotationReversionController().revertOverride(2);
            this.mInHalfFoldTransition = false;
            return i;
        }

        public void foldStateChanged(DeviceStateController.DeviceState deviceState) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                long displayId = DisplayRotation.this.mDisplayContent.getDisplayId();
                String valueOf = String.valueOf(deviceState.name());
                long j = this.mHalfFoldSavedRotation;
                long j2 = DisplayRotation.this.mUserRotation;
                DisplayRotation displayRotation = DisplayRotation.this;
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 2066210760, 5457, (String) null, new Object[]{Long.valueOf(displayId), valueOf, Long.valueOf(j), Long.valueOf(j2), Long.valueOf(displayRotation.mLastSensorRotation), Long.valueOf(displayRotation.mLastOrientation), Long.valueOf(DisplayRotation.this.mRotation)});
            }
            DeviceStateController.DeviceState deviceState2 = this.mDeviceState;
            if (deviceState2 == DeviceStateController.DeviceState.UNKNOWN) {
                this.mDeviceState = deviceState;
                return;
            }
            DeviceStateController.DeviceState deviceState3 = DeviceStateController.DeviceState.HALF_FOLDED;
            if (deviceState == deviceState3 && deviceState2 != deviceState3) {
                DisplayRotation.this.mDisplayContent.getRotationReversionController().beforeOverrideApplied(2);
                this.mHalfFoldSavedRotation = DisplayRotation.this.mRotation;
                this.mDeviceState = deviceState;
                DisplayRotation.this.mService.updateRotation(false, false);
            } else {
                this.mInHalfFoldTransition = true;
                this.mDeviceState = deviceState;
                DisplayRotation.this.mService.updateRotation(false, false);
            }
            UiThread.getHandler().removeCallbacks(this.mActivityBoundsUpdateCallback);
            UiThread.getHandler().postDelayed(this.mActivityBoundsUpdateCallback, 800L);
        }

        public boolean shouldIgnoreSensorRotation() {
            return this.mShouldIgnoreSensorRotation;
        }

        public boolean shouldDisableRotationSensor() {
            return this.mShouldDisableRotationSensor;
        }

        public final void updateSensorRotationBlockIfNeeded() {
            long uptimeMillis = DisplayRotation.this.uptimeMillis();
            boolean z = uptimeMillis - this.mLastDisplaySwitchTime < ((long) this.mDisplaySwitchRotationBlockTimeMs) || uptimeMillis - this.mLastHingeAngleEventTime < ((long) this.mHingeAngleRotationBlockTimeMs);
            if (z != this.mShouldIgnoreSensorRotation) {
                this.mShouldIgnoreSensorRotation = z;
                if (z) {
                    return;
                }
                if (this.mShouldDisableRotationSensor) {
                    this.mShouldDisableRotationSensor = false;
                    DisplayRotation.this.updateOrientationListenerLw();
                } else {
                    DisplayRotation.this.updateRotationAndSendNewConfigIfChanged();
                }
            }
        }

        public void onPhysicalDisplayChanged() {
            if (this.mPauseAutorotationDuringUnfolding) {
                this.mLastDisplaySwitchTime = DisplayRotation.this.uptimeMillis();
                DeviceStateController.DeviceState deviceState = this.mDeviceState;
                if (deviceState == DeviceStateController.DeviceState.OPEN || deviceState == DeviceStateController.DeviceState.HALF_FOLDED) {
                    this.mShouldDisableRotationSensor = true;
                    DisplayRotation.this.updateOrientationListenerLw();
                }
                updateSensorRotationBlockIfNeeded();
                DisplayRotation.this.getHandler().postDelayed(new Runnable() { // from class: com.android.server.wm.DisplayRotation$FoldController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayRotation.FoldController.this.lambda$onPhysicalDisplayChanged$0();
                    }
                }, this.mDisplaySwitchRotationBlockTimeMs);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhysicalDisplayChanged$0() {
            synchronized (DisplayRotation.this.mLock) {
                updateSensorRotationBlockIfNeeded();
            }
        }

        public void onHingeAngleChanged(float f) {
            if (f < this.mMaxHingeAngle) {
                this.mLastHingeAngleEventTime = DisplayRotation.this.uptimeMillis();
                updateSensorRotationBlockIfNeeded();
                DisplayRotation.this.getHandler().postDelayed(new Runnable() { // from class: com.android.server.wm.DisplayRotation$FoldController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayRotation.FoldController.this.lambda$onHingeAngleChanged$1();
                    }
                }, this.mHingeAngleRotationBlockTimeMs);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHingeAngleChanged$1() {
            synchronized (DisplayRotation.this.mLock) {
                updateSensorRotationBlockIfNeeded();
            }
        }
    }

    public Handler getHandler() {
        return this.mService.f1749mH;
    }

    public class OrientationListener extends WindowOrientationListener implements Runnable {
        public transient boolean mEnabled;

        @Override // com.android.server.wm.WindowOrientationListener
        public void onTableModeChanged(int i) {
        }

        public OrientationListener(Context context, Handler handler, int i) {
            super(context, handler, i);
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public boolean isKeyguardShowingAndNotOccluded() {
            return DisplayRotation.this.mService.isKeyguardShowingAndNotOccluded();
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public boolean isRotationResolverEnabled() {
            return DisplayRotation.this.mAllowRotationResolver && DisplayRotation.this.mUserRotationMode == 0 && DisplayRotation.this.mCameraRotationMode == 1 && !DisplayRotation.this.mService.mPowerManager.isPowerSaveMode();
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void onProposedRotationChanged(int i) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 2128917433, 1, (String) null, new Object[]{Long.valueOf(i)});
            }
            if (!CoreRune.FW_VRR_PERFORMANCE) {
                DisplayRotation.this.mService.mPowerManagerInternal.setPowerBoost(0, 0);
            }
            DisplayRotation.this.dispatchProposedRotation(i);
            if (DisplayRotation.this.isRotationChoiceAllowed(i)) {
                DisplayRotation.this.mRotationChoiceShownToUserForConfirmation = i;
                DisplayRotation.this.sendProposedRotationChangeToStatusBarInternal(i, DisplayRotation.this.isValidRotationChoice(i));
            } else {
                DisplayRotation.this.mRotationChoiceShownToUserForConfirmation = -1;
                DisplayRotation.this.mService.updateRotation(false, false);
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void enable() {
            this.mEnabled = true;
            getHandler().post(this);
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1568331821, 0, (String) null, (Object[]) null);
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void disable() {
            this.mEnabled = false;
            getHandler().post(this);
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -439951996, 0, (String) null, (Object[]) null);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mEnabled) {
                super.enable();
            } else {
                super.disable();
            }
        }
    }

    public class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        public void observe() {
            ContentResolver contentResolver = DisplayRotation.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("show_rotation_suggestions"), false, this, -1);
            contentResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, this, -1);
            contentResolver.registerContentObserver(Settings.System.getUriFor("user_rotation"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("camera_autorotate"), false, this, -1);
            DisplayRotation.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (DisplayRotation.this.updateSettings()) {
                DisplayRotation.this.mService.updateRotation(true, false);
            }
        }
    }

    public class RotationHistory {
        public final ArrayDeque mRecords;

        public RotationHistory() {
            this.mRecords = new ArrayDeque(8);
        }

        public class Record {
            public final DeviceStateController.DeviceState mDeviceState;
            public final String mDisplayRotationCompatPolicySummary;
            public final int mFromRotation;
            public final int mHalfFoldSavedRotation;
            public final boolean mIgnoreOrientationRequest;
            public final boolean mInHalfFoldTransition;
            public final String mLastOrientationSource;
            public final String mNonDefaultRequestingTaskDisplayArea;
            public final boolean[] mRotationReversionSlots;
            public final int mSensorRotation;
            public final int mSourceOrientation;
            public final long mTimestamp = System.currentTimeMillis();
            public final int mToRotation;
            public final int mUserRotation;
            public final int mUserRotationMode;

            public Record(DisplayRotation displayRotation, int i, int i2) {
                String displayArea;
                int overrideOrientation;
                this.mFromRotation = i;
                this.mToRotation = i2;
                this.mUserRotation = displayRotation.mUserRotation;
                this.mUserRotationMode = displayRotation.mUserRotationMode;
                OrientationListener orientationListener = displayRotation.mOrientationListener;
                this.mSensorRotation = (orientationListener == null || !orientationListener.mEnabled) ? -2 : displayRotation.mLastSensorRotation;
                DisplayContent displayContent = displayRotation.mDisplayContent;
                this.mIgnoreOrientationRequest = displayContent.getIgnoreOrientationRequest();
                TaskDisplayArea orientationRequestingTaskDisplayArea = displayContent.getOrientationRequestingTaskDisplayArea();
                if (orientationRequestingTaskDisplayArea == null) {
                    displayArea = "none";
                } else {
                    displayArea = orientationRequestingTaskDisplayArea != displayContent.getDefaultTaskDisplayArea() ? orientationRequestingTaskDisplayArea.toString() : null;
                }
                this.mNonDefaultRequestingTaskDisplayArea = displayArea;
                WindowContainer lastOrientationSource = displayContent.getLastOrientationSource();
                if (lastOrientationSource != null) {
                    this.mLastOrientationSource = lastOrientationSource.toString();
                    WindowState asWindowState = lastOrientationSource.asWindowState();
                    if (asWindowState != null) {
                        overrideOrientation = asWindowState.mAttrs.screenOrientation;
                    } else {
                        overrideOrientation = lastOrientationSource.getOverrideOrientation();
                    }
                    this.mSourceOrientation = overrideOrientation;
                } else {
                    this.mLastOrientationSource = null;
                    this.mSourceOrientation = -2;
                }
                FoldController foldController = displayRotation.mFoldController;
                if (foldController != null) {
                    this.mHalfFoldSavedRotation = foldController.mHalfFoldSavedRotation;
                    this.mInHalfFoldTransition = displayRotation.mFoldController.mInHalfFoldTransition;
                    this.mDeviceState = displayRotation.mFoldController.mDeviceState;
                } else {
                    this.mHalfFoldSavedRotation = -2;
                    this.mInHalfFoldTransition = false;
                    this.mDeviceState = DeviceStateController.DeviceState.UNKNOWN;
                }
                DisplayRotationCompatPolicy displayRotationCompatPolicy = displayContent.mDisplayRotationCompatPolicy;
                this.mDisplayRotationCompatPolicySummary = displayRotationCompatPolicy != null ? displayRotationCompatPolicy.getSummaryForDisplayRotationHistoryRecord() : null;
                this.mRotationReversionSlots = displayRotation.mDisplayContent.getRotationReversionController().getSlotsCopy();
            }

            public void dump(String str, PrintWriter printWriter) {
                printWriter.println(str + TimeUtils.logTimeOfDay(this.mTimestamp) + " " + Surface.rotationToString(this.mFromRotation) + " to " + Surface.rotationToString(this.mToRotation));
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("  source=");
                sb.append(this.mLastOrientationSource);
                sb.append(" ");
                sb.append(ActivityInfo.screenOrientationToString(this.mSourceOrientation));
                printWriter.println(sb.toString());
                printWriter.println(str + "  mode=" + WindowManagerPolicy.userRotationModeToString(this.mUserRotationMode) + " user=" + Surface.rotationToString(this.mUserRotation) + " sensor=" + Surface.rotationToString(this.mSensorRotation));
                if (this.mIgnoreOrientationRequest) {
                    printWriter.println(str + "  ignoreRequest=true");
                }
                if (this.mNonDefaultRequestingTaskDisplayArea != null) {
                    printWriter.println(str + "  requestingTda=" + this.mNonDefaultRequestingTaskDisplayArea);
                }
                if (this.mHalfFoldSavedRotation != -2) {
                    printWriter.println(str + " halfFoldSavedRotation=" + this.mHalfFoldSavedRotation + " mInHalfFoldTransition=" + this.mInHalfFoldTransition + " mFoldState=" + this.mDeviceState);
                }
                if (this.mDisplayRotationCompatPolicySummary != null) {
                    printWriter.println(str + this.mDisplayRotationCompatPolicySummary);
                }
                if (this.mRotationReversionSlots != null) {
                    printWriter.println(str + " reversionSlots= NOSENSOR " + this.mRotationReversionSlots[0] + ", CAMERA " + this.mRotationReversionSlots[1] + " HALF_FOLD " + this.mRotationReversionSlots[2]);
                }
            }
        }

        public void addRecord(DisplayRotation displayRotation, int i) {
            if (this.mRecords.size() >= 8) {
                this.mRecords.removeFirst();
            }
            this.mRecords.addLast(new Record(displayRotation, displayRotation.mDisplayContent.getWindowConfiguration().getRotation(), i));
        }
    }

    public int getCompatRotationForOrientation(int i, int i2) {
        if (i == 0) {
            return isLandscapeOrSeascape(i2) ? i2 : this.mLandscapeRotation;
        }
        if (i == 1) {
            return isAnyPortrait(i2) ? i2 : this.mPortraitRotation;
        }
        if (i != 11) {
            if (i != 12) {
                switch (i) {
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        if (!isLandscapeOrSeascape(i2)) {
                            break;
                        }
                        break;
                    case 9:
                        if (!isAnyPortrait(i2)) {
                            break;
                        }
                        break;
                    default:
                        if (i2 >= 0) {
                        }
                        break;
                }
                return i2;
            }
            return (isAnyPortrait(i2) || isAnyPortrait(i2)) ? i2 : this.mPortraitRotation;
        }
        return (isLandscapeOrSeascape(i2) || isLandscapeOrSeascape(i2)) ? i2 : this.mLandscapeRotation;
    }

    public final boolean shouldDisableRotationSensor() {
        CoverPolicy coverPolicy;
        if (WmCoverState.isEnabled() && (coverPolicy = this.mDisplayPolicy.mExt.mCoverPolicy) != null && coverPolicy.isLastCoverAppOpened() && WmCoverState.getInstance().isViewCoverClosed()) {
            Slog.d(StartingSurfaceController.TAG, "shouldDisableRotationSensor, rotation sensor is disabled due to cover policy");
            return true;
        }
        if (!this.mService.mPowerManagerInternal.isProximityPositive()) {
            return false;
        }
        Slog.d(StartingSurfaceController.TAG, "shouldDisableRotationSensor, rotation sensor is disabled due to proximity sensor");
        return true;
    }

    public boolean isAllowAllRotations() {
        return this.mAllowAllRotations == 1;
    }
}
