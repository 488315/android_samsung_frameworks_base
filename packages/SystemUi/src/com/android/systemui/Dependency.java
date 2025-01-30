package com.android.systemui;

import android.util.ArrayMap;
import com.android.internal.util.Preconditions;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.tuner.TunerActivity$$ExternalSyntheticLambda0;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Dependency {
    public static Dependency sDependency;
    public Lazy mAccessibilityButtonListController;
    public Lazy mAccessibilityButtonModeObserver;
    public Lazy mAccessibilityController;
    public Lazy mAccessibilityFloatingMenuController;
    public Lazy mAccessibilityManagerWrapper;
    public Lazy mActivityManagerWrapper;
    public Lazy mActivityStarter;
    public Lazy mAlarmManager;
    public Lazy mAmbientStateLazy;
    public Lazy mAnimationUtils;
    public Lazy mAppOpsController;
    public Lazy mAssistManager;
    public Lazy mAssistantFeedbackController;
    public Lazy mAsyncSensorManager;
    public Lazy mAutoHideController;
    public Lazy mBackgroundExecutor;
    public Lazy mBgHandler;
    public Lazy mBgLooper;
    public Lazy mBluetoothController;
    public Lazy mBroadcastDispatcher;
    public Lazy mBubblesManagerOptional;
    public Lazy mCastController;
    public Lazy mCentralSurfaces;
    public Lazy mChannelEditorDialogController;
    public Lazy mClockManager;
    public Lazy mCommandQueue;
    public Lazy mConfigurationController;
    public Lazy mContentInsetsProviderLazy;
    public Lazy mCoverScreenManager;
    public Lazy mCoverUtilWrapper;
    public Lazy mDarkIconDispatcher;
    public Lazy mDataSaverController;
    public Lazy mDesktopManager;
    public Lazy mDeviceConfigProxy;
    public Lazy mDevicePolicyManagerWrapper;
    public Lazy mDeviceProvisionedController;
    public Lazy mDialogLaunchAnimatorLazy;
    public Lazy mDisplayLifecycle;
    public Lazy mDisplayMetrics;
    public Lazy mDockManager;
    public Lazy mDozeParameters;
    public DumpManager mDumpManager;
    public Lazy mEdgeBackGestureHandlerFactoryLazy;
    public Lazy mEnhancedEstimates;
    public Lazy mExtensionController;
    public Lazy mExternalClockProvider;
    public Lazy mFaceWidgetController;
    public Lazy mFastUnlockController;
    public Lazy mFeatureFlagsLazy;
    public Lazy mFlashlightController;
    public Lazy mFoldController;
    public Lazy mForegroundServiceController;
    public Lazy mFragmentService;
    public Lazy mFullExpansionPanelNotiAlphaController;
    public Lazy mGarbageMonitor;
    public Lazy mGlobalActionsComponent;
    public Lazy mGroupExpansionManagerLazy;
    public Lazy mGroupMembershipManagerLazy;
    public Lazy mHeadsUpManager;
    public Lazy mHighPriorityProvider;
    public Lazy mHotspotController;
    public Lazy mINotificationManager;
    public Lazy mIStatusBarService;
    public Lazy mIWindowManager;
    public Lazy mInternetDialogFactory;
    public Lazy mKeyguardDismissUtil;
    public Lazy mKeyguardMonitor;
    public Lazy mKeyguardSecurityModel;
    public Lazy mKeyguardShortcutManager;
    public Lazy mKeyguardUpdateMonitor;
    public Lazy mKeyguardVisibilityMonitor;
    public Lazy mKnoxStateMonitor;
    public Lazy mLauncherApps;
    public Lazy mLeakDetector;
    public Lazy mLeakReportEmail;
    public Lazy mLeakReporter;
    public Lazy mLightBarController;
    public Lazy mLocalBluetoothManager;
    public Lazy mLocationController;
    public Lazy mLockscreenGestureLogger;
    public Lazy mLooperSlowLogController;
    public Lazy mMainExecutor;
    public Lazy mMainHandler;
    public Lazy mMainLooper;
    public Lazy mManagedProfileController;
    public Lazy mMediaOutputDialogFactory;
    public Lazy mMetricsLogger;
    public Lazy mMultiSIMControllerLazy;
    public Lazy mNavBarBgHandler;
    public Lazy mNavBarModeController;
    public Lazy mNavBarStore;
    public Lazy mNavigationBarController;
    public Lazy mNetworkController;
    public Lazy mNextAlarmController;
    public Lazy mNightDisplayListener;
    public Lazy mNotiCinemaLogger;
    public Lazy mNotifCollection;
    public Lazy mNotifLiveDataStore;
    public Lazy mNotificationBackupRestoreManager;
    public Lazy mNotificationColorPicker;
    public Lazy mNotificationGutsManager;
    public Lazy mNotificationIconTransitionManager;
    public Lazy mNotificationListener;
    public Lazy mNotificationLockscreenUserManager;
    public Lazy mNotificationLogger;
    public Lazy mNotificationMediaManager;
    public Lazy mNotificationRemoteInputManager;
    public Lazy mNotificationRemoteInputManagerCallback;
    public Lazy mNotificationSectionsManagerLazy;
    public Lazy mNotificationShadeWindowController;
    public Lazy mNotificationShelfManager;
    public Lazy mNotificationsController;
    public Lazy mOnUserInteractionCallback;
    public Lazy mOverviewProxyService;
    public Lazy mPackageManagerWrapper;
    public Lazy mPanelBlockExpandingHelper;
    public Lazy mPanelScreenShotBufferLogger;
    public Lazy mPeopleSpaceWidgetManager;
    public Lazy mPluginDependencyProvider;
    public Lazy mPluginFaceWidgetManager;
    public Lazy mPluginLockManager;
    public Lazy mPluginLockStarManager;
    public Lazy mPluginManager;
    public Lazy mPluginWallpaperManager;
    public Lazy mPrivacyDotViewControllerLazy;
    public Lazy mPrivacyItemController;
    public Lazy mProtoTracer;
    public Lazy mQSBackupRestoreManager;
    public Lazy mQSClockBellTower;
    public Lazy mRecordingController;
    public Lazy mReduceBrightColorsController;
    public Lazy mRemoteInputQuickSettingsDisabler;
    public Lazy mResetSettingsManager;
    public Lazy mRotationLockController;
    public Lazy mSBluetoothController;
    public Lazy mSPluginDependencyProvider;
    public Lazy mSPluginManager;
    public Lazy mSRotationLockController;
    public Lazy mScreenLifecycle;
    public Lazy mScreenOffAnimationController;
    public Lazy mSearcleManager;
    public Lazy mSecPanelExpansionStateNotifier;
    public Lazy mSecPanelLogger;
    public Lazy mSecPanelPolicy;
    public Lazy mSecQSPanelResourcePicker;
    public Lazy mSecRotationWatcher;
    public Lazy mSecurityController;
    public Lazy mSensorPrivacyController;
    public Lazy mSensorPrivacyManager;
    public Lazy mSettingsHelper;
    public Lazy mShadeController;
    public Lazy mShadeHeaderController;
    public Lazy mShelfToolTipManager;
    public Lazy mSimpleStatusBarIconController;
    public Lazy mSmartReplyConstants;
    public Lazy mSmartReplyController;
    public Lazy mStatusBarIconController;
    public Lazy mStatusBarStateController;
    public Lazy mSubScreenManager;
    public Lazy mSubscreenMusicWidgetController;
    public Lazy mSubscreenNotificationController;
    public Lazy mSubscreenQsPanelController;
    public Lazy mSubscreenUtil;
    public Lazy mSysUiStateFlagsContainer;
    public Lazy mSystemStatusAnimationSchedulerLazy;
    public Lazy mSystemUIDialogManagerLazy;
    public Lazy mSystemUIIndexMediator;
    public Lazy mSysuiColorExtractor;
    public Lazy mTaskbarDelegate;
    public Lazy mTelephonyListenerManager;
    public Lazy mTempStatusBarWindowController;
    public Lazy mTimeTickHandler;
    public Lazy mTunablePaddingService;
    public Lazy mTunerService;
    public Lazy mUiEventLogger;
    public Lazy mUiOffloadThread;
    public Lazy mUnlockedScreenOffAnimationHelper;
    public Lazy mUserContextProvider;
    public Lazy mUserInfoController;
    public Lazy mUserInteractorLazy;
    public Lazy mUserSwitcherController;
    public Lazy mUserTrackerLazy;
    public Lazy mVibratorHelper;
    public Lazy mVolumeDialogController;
    public Lazy mWakefulnessLifecycle;
    public Lazy mWallpaperChangeNotifier;
    public Lazy mWallpaperEventNotifier;
    public Lazy mWallpaperManager;
    public Lazy mWarningsUI;
    public Lazy mZenModeController;
    public static final DependencyKey BG_HANDLER = new DependencyKey("bg_handler");
    public static final DependencyKey BUBBLE_MANAGER = new DependencyKey("bubble_manager");
    public static final DependencyKey BG_LOOPER = new DependencyKey("background_looper");
    public static final DependencyKey MAIN_LOOPER = new DependencyKey("main_looper");
    public static final DependencyKey TIME_TICK_HANDLER = new DependencyKey("time_tick_handler");
    public static final DependencyKey MAIN_HANDLER = new DependencyKey("main_handler");
    public static final DependencyKey MAIN_EXECUTOR = new DependencyKey("main_executor");
    public static final DependencyKey BACKGROUND_EXECUTOR = new DependencyKey("background_executor");
    public static final DependencyKey LEAK_REPORT_EMAIL = new DependencyKey("leak_report_email");
    public static final DependencyKey NAVBAR_BG_HANDLER = new DependencyKey("navbar_background_handler");
    public final ArrayMap mDependencies = new ArrayMap();
    public final ArrayMap mProviders = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DependencyKey {
        public final String mDisplayName;

        public DependencyKey(String str) {
            this.mDisplayName = str;
        }

        public final String toString() {
            return this.mDisplayName;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface LazyDependencyCreator {
        Object createDependency();
    }

    public static void destroy(Class cls, TunerActivity$$ExternalSyntheticLambda0 tunerActivity$$ExternalSyntheticLambda0) {
        Dependency dependency = sDependency;
        Object remove = dependency.mDependencies.remove(cls);
        if (remove instanceof Dumpable) {
            dependency.mDumpManager.unregisterDumpable(remove.getClass().getName());
        }
        if (remove != null) {
            tunerActivity$$ExternalSyntheticLambda0.accept(remove);
        }
    }

    public static Object get(Class cls) {
        Object obj;
        Dependency dependency = sDependency;
        synchronized (dependency) {
            obj = dependency.mDependencies.get(cls);
            if (obj == null) {
                obj = dependency.createDependency(cls);
                dependency.mDependencies.put(cls, obj);
            }
        }
        return obj;
    }

    public static void setInstance(Dependency dependency) {
        sDependency = dependency;
    }

    public <T> T createDependency(Object obj) {
        Preconditions.checkArgument((obj instanceof DependencyKey) || (obj instanceof Class));
        ArrayMap arrayMap = this.mProviders;
        LazyDependencyCreator lazyDependencyCreator = (LazyDependencyCreator) arrayMap.get(obj);
        if (lazyDependencyCreator != null) {
            return (T) lazyDependencyCreator.createDependency();
        }
        throw new IllegalArgumentException("Unsupported dependency " + obj + ". " + arrayMap.size() + " providers known.");
    }

    public static Object get(DependencyKey dependencyKey) {
        Object obj;
        Dependency dependency = sDependency;
        synchronized (dependency) {
            obj = dependency.mDependencies.get(dependencyKey);
            if (obj == null) {
                obj = dependency.createDependency(dependencyKey);
                dependency.mDependencies.put(dependencyKey, obj);
            }
        }
        return obj;
    }
}
