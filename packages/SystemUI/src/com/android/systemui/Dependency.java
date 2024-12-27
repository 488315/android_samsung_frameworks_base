package com.android.systemui;

import android.util.ArrayMap;
import com.android.internal.util.Preconditions;
import com.android.systemui.dump.DumpManager;
import dagger.Lazy;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Dependency {
    public static Dependency sDependency;
    public Lazy mActivityStarter;
    public Lazy mAiBriefManagerLazy;
    public Lazy mAmbientStateLazy;
    public Lazy mAppLockNotificationController;
    public Lazy mAssistManager;
    public Lazy mAssistantFeedbackController;
    public Lazy mBgHandler;
    public Lazy mBgLooper;
    public Lazy mBluetoothController;
    public Lazy mBroadcastDispatcher;
    public Lazy mBubblesManagerOptional;
    public Lazy mCentralSurfaces;
    public Lazy mChannelEditorDialogController;
    public Lazy mColoredBGHelper;
    public Lazy mCommandQueue;
    public Lazy mConfigurationController;
    public Lazy mContentInsetsProviderLazy;
    public Lazy mCoverScreenManager;
    public Lazy mCoverUtilWrapper;
    public Lazy mDarkIconDispatcher;
    public Lazy mDesktopManager;
    public Lazy mDeviceProvisionedController;
    public Lazy mDialogTransitionAnimatorLazy;
    public Lazy mDisplayLifecycle;
    public DumpManager mDumpManager;
    public Lazy mExternalClockProvider;
    public Lazy mFaceWidgetController;
    public Lazy mFalsingManager;
    public Lazy mFastUnlockController;
    public Lazy mFeatureFlagsLazy;
    public Lazy mFlashlightController;
    public Lazy mFoldController;
    public Lazy mFragmentService;
    public Lazy mFullExpansionPanelNotiAlphaController;
    public Lazy mGlobalActionsComponent;
    public Lazy mGroupExpansionManagerLazy;
    public Lazy mGroupMembershipManagerLazy;
    public Lazy mHeadsUpManager;
    public Lazy mHighPriorityProvider;
    public Lazy mINotificationManager;
    public Lazy mKeyguardMonitor;
    public Lazy mKeyguardShortcutManager;
    public Lazy mKeyguardUpdateMonitor;
    public Lazy mKeyguardVisibilityMonitor;
    public Lazy mKnoxStateMonitor;
    public Lazy mLauncherApps;
    public Lazy mLightBarController;
    public Lazy mLooperSlowLogController;
    public Lazy mMainHandler;
    public Lazy mMetricsLogger;
    public Lazy mMultiSIMControllerLazy;
    public Lazy mNavBarBgHandler;
    public Lazy mNavBarModeController;
    public Lazy mNavBarStore;
    public Lazy mNavigationBarController;
    public Lazy mNetworkController;
    public Lazy mNotiCinemaLogger;
    public Lazy mNotifCollection;
    public Lazy mNotifLiveDataStore;
    public Lazy mNotificationBackupRestoreManager;
    public Lazy mNotificationColorPicker;
    public Lazy mNotificationGutsManager;
    public Lazy mNotificationLockscreenUserManager;
    public Lazy mNotificationMediaManager;
    public Lazy mNotificationSectionsManagerLazy;
    public Lazy mNotificationShelfManager;
    public Lazy mOnUserInteractionCallback;
    public Lazy mOverviewProxyService;
    public Lazy mPanelScreenShotBufferLogger;
    public Lazy mPanelSplitHelper;
    public Lazy mPeopleSpaceWidgetManager;
    public Lazy mPluginFaceWidgetManager;
    public Lazy mPluginLockManager;
    public Lazy mPluginLockStarManager;
    public Lazy mPluginManager;
    public Lazy mPluginWallController;
    public Lazy mPluginWallpaperManager;
    public Lazy mPrivacyDialogController;
    public Lazy mQSBackupRestoreManager;
    public Lazy mQSClockBellTower;
    public Lazy mQSScrimViewSwitch;
    public Lazy mQuickPanelExternalLogger;
    public Lazy mQuickSettingsController;
    public Lazy mResetSettingsManager;
    public Lazy mRotationLockController;
    public Lazy mSPluginDependencyProvider;
    public Lazy mSPluginManager;
    public Lazy mScreenOffAnimationController;
    public Lazy mSearcleManager;
    public Lazy mSecHideNotificationShadeInMirrorInteractor;
    public Lazy mSecNotificationShadeWindowStateInteractor;
    public Lazy mSecPanelExpansionStateInteractor;
    public Lazy mSecPanelSAStatusLogInteractor;
    public Lazy mSecPanelTouchBlockHelper;
    public Lazy mSecPanelTouchProximityInteractor;
    public Lazy mSecQSCoverResourcePicker;
    public Lazy mSecQSDetailController;
    public Lazy mSecQSExpansionStateInteractor;
    public Lazy mSecQSPanelResourcePicker;
    public Lazy mSecQpBlurController;
    public Lazy mSecQuickSettingsAffordanceInteractor;
    public Lazy mSecRotationWatcher;
    public Lazy mSecSTQuickControlRequestReceiver;
    public Lazy mSecurityController;
    public Lazy mSelectedUserInteractor;
    public Lazy mSensitiveNotificationProtectionController;
    public Lazy mSensorPrivacyManager;
    public Lazy mSettingsHelper;
    public Lazy mShadeController;
    public Lazy mShadeHeaderController;
    public Lazy mShadeRepository;
    public Lazy mShelfToolTipManager;
    public Lazy mStatusBarIconControllerLazy;
    public Lazy mStatusBarStateController;
    public Lazy mStatusBarWindowControllerLazy;
    public Lazy mSubScreenManager;
    public Lazy mSubscreenMusicWidgetController;
    public Lazy mSubscreenNotificationController;
    public Lazy mSubscreenQsPanelController;
    public Lazy mSubscreenUtil;
    public Lazy mSysUiStateFlagsContainer;
    public Lazy mSystemUIDialogManagerLazy;
    public Lazy mSystemUIIndexMediator;
    public Lazy mTaskbarDelegate;
    public Lazy mTimeTickHandler;
    public Lazy mTunablePaddingService;
    public Lazy mTunerService;
    public Lazy mUiEventLogger;
    public Lazy mUiOffloadThread;
    public Lazy mUnlockedScreenOffAnimationHelper;
    public Lazy mUserContextProvider;
    public Lazy mUserInfoController;
    public Lazy mUserSwitcherController;
    public Lazy mUserSwitcherInteractor;
    public Lazy mUserTrackerLazy;
    public Lazy mVibratorHelper;
    public Lazy mVolumeDialogController;
    public Lazy mVolumeManager;
    public Lazy mWakefulnessLifecycle;
    public Lazy mWallpaperChangeNotifier;
    public Lazy mWallpaperEventNotifier;
    public Lazy mWallpaperManager;
    public static final DependencyKey BG_HANDLER = new DependencyKey("bg_handler");
    public static final DependencyKey BUBBLE_MANAGER = new DependencyKey("bubble_manager");
    public static final DependencyKey BG_LOOPER = new DependencyKey("background_looper");
    public static final DependencyKey TIME_TICK_HANDLER = new DependencyKey("time_tick_handler");
    public static final DependencyKey MAIN_HANDLER = new DependencyKey("main_handler");
    public static final DependencyKey NAVBAR_BG_HANDLER = new DependencyKey("navbar_background_handler");
    public final ArrayMap mDependencies = new ArrayMap();
    public final ArrayMap mProviders = new ArrayMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DependencyKey {
        public final String mDisplayName;

        public DependencyKey(String str) {
            this.mDisplayName = str;
        }

        public final String toString() {
            return this.mDisplayName;
        }
    }

    public static void destroy(Class cls, Consumer consumer) {
        Dependency dependency = sDependency;
        Object remove = dependency.mDependencies.remove(cls);
        if (remove instanceof Dumpable) {
            dependency.mDumpManager.unregisterDumpable(remove.getClass().getName());
        }
        if (remove != null) {
            consumer.accept(remove);
        }
    }

    public static void setInstance(Dependency dependency) {
        sDependency = dependency;
    }

    public <T> T createDependency(Object obj) {
        Preconditions.checkArgument((obj instanceof DependencyKey) || (obj instanceof Class));
        Dependency$$ExternalSyntheticLambda0 dependency$$ExternalSyntheticLambda0 = (Dependency$$ExternalSyntheticLambda0) this.mProviders.get(obj);
        if (dependency$$ExternalSyntheticLambda0 != null) {
            return (T) dependency$$ExternalSyntheticLambda0.f$0.get();
        }
        throw new IllegalArgumentException("Unsupported dependency " + obj + ". " + this.mProviders.size() + " providers known.");
    }

    public final synchronized Object getDependencyInner(Object obj) {
        Object obj2;
        obj2 = this.mDependencies.get(obj);
        if (obj2 == null) {
            obj2 = createDependency(obj);
            this.mDependencies.put(obj, obj2);
        }
        return obj2;
    }
}
