package com.android.systemui.doze;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.LayoutInflater;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.window.RemoteTransition;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.blur.SecCoverBlurController;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginControllerImpl;
import com.android.systemui.facewidget.plugin.FaceWidgetWallpaperUtilsWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.AODDumpLog;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.SecLifecycle$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda15;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.media.SubscreenMusicWidgetController;
import com.android.systemui.media.SubscreenMusicWidgetSubroom;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.aod.PluginAODSystemUIConfiguration;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.keyguardstatusview.PluginAODStateProvider;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView;
import com.android.systemui.qp.SubscreenParentLayout;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeaderController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda0;
import com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda1;
import com.android.systemui.subscreen.SubScreenQSEventHandler;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
import com.android.systemui.subscreen.SubScreenSurfaceControlImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PluginAODManager implements Dumpable, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public AODMachine mAODMachine;
    public AODOverlayContainer mAODOverlayContainer;
    public PluginAOD mAODPlugin;
    public final AODTouchModeManager mAODTouchModeManager;
    public final ActiveNotificationsInteractor mActiveNotificationsInteractor;
    public BatteryMeterViewController mBatteryMeterViewController;
    public BatteryMeterViewController.Factory mBatteryMeterViewControllerFactory;
    public PluginClockPack mClockPackPlugin;
    public boolean mClockTransitionStarted;
    public final Lazy mCommonNotifCollectionLazy;
    public final Context mContext;
    public boolean mControlScreenOff;
    public PluginCover mCoverPlugin;
    public Context mCoverPluginContext;
    public final Lazy mCoverScreenManagerLazy;
    public final DisplayLifecycle mDisplayLifeCycle;
    public IDisplayManager mDisplayManager;
    public final DozeParameters mDozeParameters;
    public final Lazy mDozeServiceHostLazy;
    public boolean mDozing;
    public EngineeringModeManagerWrapper mEmm;
    public FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final KeyguardFoldController mFoldController;
    public final AnonymousClass9 mHandler;
    public IDisplayManager mIDisplayManager;
    public boolean mIsDifferentOrientation;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public KeyguardFastBioUnlockController mKeyguardFastBioUnlockController;
    public final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewController mKeyguardViewController;
    public final KeyguardViewMediatorHelper mKeyguardViewMediatorHelper;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
    public final LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public IRefreshRateToken mMaxRefreshRateToken;
    public final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public Lazy mPanelViewControllerLazy;
    public IRefreshRateToken mPassiveModeToken;
    public PluginAODStateProvider mPluginAODStateProvider;
    public final PluginLockMediator mPluginLockMediator;
    public final PluginLockStarManager mPluginLockStarManager;
    public ScrimController mScrimController;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private final SettingsHelper mSettingsHelper;
    public boolean mStartedByFolderClosed;
    public StatusBarManager mStatusBarManager;
    public final SubScreenManager mSubScreenManager;
    public PluginSubScreen mSubScreenPlugin;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public int mWakefulness;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final FaceWidgetWallpaperUtilsWrapper mWallpaperUtilsWrapper;
    public final PluginAODSystemUIConfiguration mSysUIConfig = new PluginAODSystemUIConfiguration();
    public final FaceWidgetNotificationController mFaceWidgetNotiController = ((FaceWidgetPluginControllerImpl) Dependency.sDependency.getDependencyInner(FaceWidgetPluginControllerImpl.class)).mNotificationManager;
    public final ArrayList mSmartAlerts = new ArrayList();
    public final ConcurrentHashMap mNotiIconMap = new ConcurrentHashMap();
    public Point mZigzagPosition = new Point();
    public List mConnectionRunnableList = null;
    public final IBinder mDisplayStateLock = new Binder();
    public final IBinder mToken = new Binder();
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.doze.PluginAODManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPhoneStateChanged(int i) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mSysUIConfig.get(2, 0) != i) {
                pluginAODManager.mSysUIConfig.set(2, i);
                PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                if (pluginAOD != null) {
                    pluginAOD.onSystemUIConfigurationChanged(pluginAODManager.mSysUIConfig);
                    return;
                }
                PluginClockPack pluginClockPack = pluginAODManager.mClockPackPlugin;
                if (pluginClockPack != null) {
                    pluginClockPack.onSystemUIConfigurationChanged(pluginAODManager.mSysUIConfig);
                }
            }
        }
    };
    public final AnonymousClass2 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.doze.PluginAODManager.2
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            PluginAOD pluginAOD = PluginAODManager.this.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.onFinishedGoingToSleep();
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                PluginAODManager pluginAODManager = PluginAODManager.this;
                if (pluginAODManager.mKeyguardUpdateMonitor.isCoverClosed()) {
                    ((CoverScreenManager) pluginAODManager.mCoverScreenManagerLazy.get()).prepareCoverHomeActivity();
                }
            }
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            boolean z;
            Log.d("PluginAODManager", "onStartedWakingUp");
            int i = PluginAODManager.this.mWakefulnessLifecycle.mLastWakeReason;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onStartedWakingUp why=", "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.getClass();
            if (i != 0 && i != 2 && i != 110) {
                Log.d("PluginAODManager", "clearNotiMapIfNeeded() clear");
                pluginAODManager.mSysUIConfig.setNotiMap(null);
            }
            if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
                WakefulnessLifecycle wakefulnessLifecycle = PluginAODManager.this.mWakefulnessLifecycle;
                synchronized (wakefulnessLifecycle.mMsgForLifecycle) {
                    try {
                        Optional findFirst = wakefulnessLifecycle.mMsgForLifecycle.stream().filter(new SecLifecycle$$ExternalSyntheticLambda0(1)).findFirst();
                        z = findFirst != null && findFirst.isPresent();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    return;
                }
                PluginAODManager.this.setStartedByFolderClosed(false);
            }
        }
    };
    public final AnonymousClass3 mNotificationCallback = new PluginAODNotificationManager.Callback() { // from class: com.android.systemui.doze.PluginAODManager.3
        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void animateExpandLockedShadePanel(StatusBarNotification statusBarNotification) {
            Log.d("PluginAODManager", "animateExpandLockedShadePanel() sbn=" + statusBarNotification);
            PluginAODManager pluginAODManager = PluginAODManager.this;
            boolean hasMessages = pluginAODManager.mHandler.hasMessages(1000);
            AnonymousClass9 anonymousClass9 = pluginAODManager.mHandler;
            if (hasMessages) {
                anonymousClass9.removeMessages(1000);
            }
            Message obtainMessage = anonymousClass9.obtainMessage(1000);
            obtainMessage.obj = statusBarNotification == null ? null : statusBarNotification.clone();
            anonymousClass9.sendMessageDelayed(obtainMessage, 300L);
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void clickNotification(String str) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("clickNotification() ", str, "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) pluginAODManager.mCommonNotifCollectionLazy.get())).mNotifCollection.getEntry(str);
            if (entry != null) {
                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                int rank = entry.mRanking.getRank();
                int i = NotificationsLiveDataStoreRefactor.$r8$clinit;
                NotificationVisibility obtain = NotificationVisibility.obtain(str, rank, pluginAODManager.mActiveNotificationsInteractor.getAllNotificationsCountValue(), true);
                try {
                    if (asInterface != null) {
                        asInterface.onNotificationClick(str, obtain);
                    } else {
                        Log.e("PluginAODManager", "can't get STATUS_BAR_SERVICE");
                    }
                } catch (RemoteException unused) {
                }
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final Icon getNotificationIcon(String str) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            ConcurrentHashMap concurrentHashMap = pluginAODManager.mNotiIconMap;
            if (concurrentHashMap == null || concurrentHashMap.isEmpty()) {
                return null;
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getNotificationIcon() ", str, "PluginAODManager");
            return (Icon) pluginAODManager.mNotiIconMap.get(str);
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void requestActiveNotifications() {
            Collection allNotifs = ((NotifPipeline) ((CommonNotifCollection) PluginAODManager.this.mCommonNotifCollectionLazy.get())).getAllNotifs();
            Log.d("PluginAODManager", "requestActiveNotifications() size = " + allNotifs.size());
            ArrayList arrayList = new ArrayList();
            Iterator it = allNotifs.iterator();
            while (it.hasNext()) {
                arrayList.add(((NotificationEntry) it.next()).mSbn);
            }
            PluginAODManager pluginAODManager = PluginAODManager.this;
            synchronized (pluginAODManager.mSmartAlerts) {
                try {
                    pluginAODManager.mSmartAlerts.clear();
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        StatusBarNotification statusBarNotification = (StatusBarNotification) obj;
                        if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                            pluginAODManager.mSmartAlerts.add(statusBarNotification);
                        }
                    }
                    if (pluginAODManager.mSmartAlerts.size() > 0) {
                        pluginAODManager.logSmartAlert("updateActiveNotifications");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateActiveNotifications(arrayList);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().updateActiveNotifications(arrayList);
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void requestVisibleNotifications() {
            Log.d("PluginAODManager", "requestVisibleNotifications()");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            FaceWidgetNotificationController faceWidgetNotificationController = pluginAODManager.mLockscreenNotificationIconsOnlyController.mFaceWidgetNotificationController;
            if (faceWidgetNotificationController != null) {
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController;
                if (faceWidgetNotificationControllerWrapper.getNotificationManager() != null) {
                    faceWidgetNotificationControllerWrapper.getNotificationManager().setTagId(R.id.tag_fresh_drawable, R.id.tag_shows_conversation);
                }
            }
            LockscreenNotificationManager lockscreenNotificationManager = pluginAODManager.mLockscreenNotificationManager;
            lockscreenNotificationManager.getClass();
            Log.i("LockscreenNotificationManager", "refreshLockScreenNotifications: AOD_REQUEST_NOTIFICATIONS");
            LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = lockscreenNotificationManager.mLockScreenNotificationStateListener;
            if (lockScreenNotiIconCoordinator != null) {
                lockScreenNotiIconCoordinator.onLockScreenNotiStateChanged();
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAODNotificationManager.Callback
        public final void showSubScreenNotification(String str) {
        }
    };
    public final AnonymousClass4 mLockscreenNotiCallback = new LockscreenNotificationManager.Callback() { // from class: com.android.systemui.doze.PluginAODManager.4
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v14 */
        /* JADX WARN: Type inference failed for: r11v15 */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v4 */
        /* JADX WARN: Type inference failed for: r11v5 */
        /* JADX WARN: Type inference failed for: r11v6 */
        /* JADX WARN: Type inference failed for: r11v7, types: [int] */
        /* JADX WARN: Type inference failed for: r6v14, types: [java.lang.StringBuilder] */
        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationInfoUpdated(ArrayList arrayList) {
            boolean z;
            StatusBarIconView statusBarIconView;
            boolean z2 = true;
            Log.d("PluginAODManager", "onNotificationInfoUpdated() " + arrayList.size());
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.mNotiIconMap.clear();
            StringBuilder sb = new StringBuilder();
            NotificationLockscreenUserManager notificationLockscreenUserManager = pluginAODManager.mNotificationLockscreenUserManager;
            boolean z3 = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mShowLockscreenNotifications;
            PluginNotificationController pluginNotificationController = ((FaceWidgetNotificationControllerWrapper) pluginAODManager.mFaceWidgetNotiController).mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.isMusicFaceWidgetOn();
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                LockscreenNotificationInfo lockscreenNotificationInfo = (LockscreenNotificationInfo) arrayList.get(i);
                if (lockscreenNotificationInfo == null) {
                    i = i2;
                } else {
                    StatusBarNotification statusBarNotification = lockscreenNotificationInfo.mSbn;
                    boolean z4 = z2;
                    FaceWidgetNotificationController faceWidgetNotificationController = pluginAODManager.mFaceWidgetNotiController;
                    statusBarNotification.getKey();
                    faceWidgetNotificationController.getClass();
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) pluginAODManager.mCommonNotifCollectionLazy.get())).mNotifCollection.getEntry(lockscreenNotificationInfo.mKey);
                    if (entry == null) {
                        Log.i("PluginAODManager", "onNotificationInfoUpdated : can not find " + lockscreenNotificationInfo.mKey);
                        i = i2;
                        z2 = z4;
                    } else {
                        ?? r11 = z3 ? 2 : z3;
                        NotificationLockscreenUserManager notificationLockscreenUserManager2 = notificationLockscreenUserManager;
                        boolean z5 = (z3 && ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager2).mShowLockscreenNotifications) ? z4 : false;
                        if (z5) {
                            r11 = 3;
                        }
                        boolean z6 = (!z5 || ((KeyguardNotificationVisibilityProviderImpl) pluginAODManager.mKeyguardNotificationVisibilityProvider).shouldHideNotification(entry)) ? false : z4;
                        if (z6) {
                            r11 = 4;
                        }
                        if (!z3 || z6) {
                            z = z6;
                        } else {
                            z = z6;
                            sb.append("[" + statusBarNotification.getKey() + "$" + r11 + "]");
                        }
                        if (z) {
                            Log.d("PluginAODManager", "onNotificationInfoUpdated() " + lockscreenNotificationInfo.mKey);
                            arrayList2.add(statusBarNotification);
                            String str = lockscreenNotificationInfo.mKey;
                            ConcurrentHashMap concurrentHashMap = pluginAODManager.mNotiIconMap;
                            if (concurrentHashMap != null && (statusBarIconView = lockscreenNotificationInfo.mStatusBarIcon) != null) {
                                concurrentHashMap.putIfAbsent(str, statusBarIconView.mIcon.icon);
                            }
                            arrayList3.add(statusBarNotification);
                            if (statusBarNotification.getNotification().isGroupSummary()) {
                                try {
                                    List attachedChildren = entry.row.getAttachedChildren();
                                    if (attachedChildren != null) {
                                        ArrayList arrayList4 = (ArrayList) attachedChildren;
                                        int size2 = arrayList4.size();
                                        int i3 = 0;
                                        while (i3 < size2) {
                                            Object obj = arrayList4.get(i3);
                                            i3++;
                                            arrayList3.add(((ExpandableNotificationRow) obj).mEntry.mSbn);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        i = i2;
                        z2 = z4;
                        notificationLockscreenUserManager = notificationLockscreenUserManager2;
                    }
                }
            }
            if (sb.length() > 0) {
                String str2 = "onNotificationInfoUpdated$ don't show - " + sb.toString();
                SamsungServiceLogger samsungServiceLogger = AODDumpLog.logger;
                LogLevel logLevel = LogLevel.DEBUG;
                SamsungServiceLogger samsungServiceLogger2 = AODDumpLog.logger;
                if (samsungServiceLogger2 != null) {
                    ((SamsungServiceLoggerImpl) samsungServiceLogger2).logWithThreadId("", logLevel, str2);
                }
            }
            int size3 = arrayList2.size();
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size3);
            } else {
                PluginCover pluginCover = pluginAODManager.mCoverPlugin;
                if (pluginCover != null) {
                    pluginCover.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size3);
                } else {
                    PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                    if (pluginSubScreen != null) {
                        pluginSubScreen.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size3);
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb2.append("[updateVisibleNotifications] totalCount : [" + size3 + "]");
            sb2.append(" showingKeys ");
            int size4 = arrayList2.size();
            int i4 = 0;
            while (i4 < size4) {
                Object obj2 = arrayList2.get(i4);
                i4++;
                StatusBarNotification statusBarNotification2 = (StatusBarNotification) obj2;
                if (statusBarNotification2 != null) {
                    sb3.append("[" + statusBarNotification2.getKey() + "]");
                }
            }
            sb2.append(sb3.toString());
            String sb4 = sb2.toString();
            SamsungServiceLogger samsungServiceLogger3 = AODDumpLog.logger;
            LogLevel logLevel2 = LogLevel.DEBUG;
            SamsungServiceLogger samsungServiceLogger4 = AODDumpLog.logger;
            if (samsungServiceLogger4 != null) {
                ((SamsungServiceLoggerImpl) samsungServiceLogger4).logWithThreadId("", logLevel2, sb4);
            }
        }

        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationTypeChanged(int i) {
        }
    };
    public final SysuiStatusBarStateController mStatusBarStateController = (SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
    public final AnonymousClass5 mStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.doze.PluginAODManager.5
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            PluginCover pluginCover;
            PluginAODManager pluginAODManager = PluginAODManager.this;
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.onDozeAmountChanged(f, f2);
            }
            PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
            if (pluginSubScreen != null && (!pluginAODManager.mIsFolderOpened || f2 == 0.0f || f2 == 1.0f)) {
                pluginSubScreen.onDozeAmountChanged(f2);
            }
            if (!LsRune.COVER_VIRTUAL_DISPLAY || (pluginCover = pluginAODManager.mCoverPlugin) == null) {
                return;
            }
            pluginCover.onDozeAmountChanged(f2);
        }
    };
    public final AnonymousClass6 mAODUICallback = new PluginAOD.UICallback() { // from class: com.android.systemui.doze.PluginAODManager.6
        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final int getKeyguardOrientation() {
            NotificationShadeWindowController notificationShadeWindowController = PluginAODManager.this.mNotificationShadeWindowController;
            if (notificationShadeWindowController == null) {
                return -1;
            }
            SecNotificationShadeWindowControllerHelperImpl.Provider provider = ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.provider;
            if (provider == null) {
                provider = null;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) provider.lpSupplier.get();
            if (layoutParams == null) {
                return -1;
            }
            return layoutParams.screenOrientation;
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final Bundle getLockStarData(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("getLockStarData() ", "PluginAODManager", z);
            PluginLockStar pluginLockStar = PluginAODManager.this.mPluginLockStarManager.mPluginLockStar;
            if (pluginLockStar == null) {
                Log.w("LStar|PluginLockStarManager", "getAODLockStarData: no plugin");
                return new Bundle();
            }
            try {
                return pluginLockStar.getAODData(z);
            } catch (Error unused) {
                Log.w("LStar|PluginLockStarManager", "getAODLockStarData: no method");
                return new Bundle();
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final boolean isCaptureEnabled() {
            Log.d("PluginAODManager", "isCaptureEnabled()");
            return PluginAODManager.this.mEmm.isCaptureEnabled;
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final boolean isWonderLandAmbientWallpaperEnabled() {
            AODAmbientWallpaperHelper aODAmbientWallpaperHelper = PluginAODManager.this.mAODAmbientWallpaperHelper;
            Lazy lazy = aODAmbientWallpaperHelper.keyguardFoldControllerLazy;
            ActionBarContextView$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("isWonderLandAmbientWallpaper: isFolded=", ", isMainWonderLandWallpaper=", ", isSubWonderLandWallpaper=", !((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened(), aODAmbientWallpaperHelper.isMainWonderLandWallpaper), aODAmbientWallpaperHelper.isSubWonderLandWallpaper, "AODAmbientWallpaperHelper");
            return LsRune.AOD_SUB_DISPLAY_LOCK ? !((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened() ? aODAmbientWallpaperHelper.isSubWonderLandWallpaper : aODAmbientWallpaperHelper.isMainWonderLandWallpaper : aODAmbientWallpaperHelper.isMainWonderLandWallpaper;
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "registerAODDoubleTouchListener() ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mDozeServiceHostLazy.get() == null || (shadeViewController = ((DozeServiceHost) pluginAODManager.mDozeServiceHostLazy.get()).mNotificationPanelViewController) == null) {
                return;
            }
            shadeViewController.registerAODDoubleTouchListener(onTouchListener);
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void setBottomArea(View view) {
            Log.d("PluginAODManager", "setBottomArea() ");
            KeyguardSecBottomAreaView view2 = ((NotificationPanelViewController) PluginAODManager.this.mPanelViewControllerLazy.get()).mKeyguardSecBottomAreaViewController.getView();
            ((FrameLayout) view2.bottomDozeArea$delegate.getValue()).removeAllViews();
            ((FrameLayout) view2.bottomDozeArea$delegate.getValue()).addView(view);
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void unregisterAODDoubleTouchListener() {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "unregisterAODDoubleTouchListener() ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mDozeServiceHostLazy.get() == null || (shadeViewController = ((DozeServiceHost) pluginAODManager.mDozeServiceHostLazy.get()).mNotificationPanelViewController) == null) {
                return;
            }
            shadeViewController.unregisterAODDoubleTouchListener();
        }
    };
    public final AnonymousClass7 mSubUICallback = new PluginSubScreen.Callback() { // from class: com.android.systemui.doze.PluginAODManager.7
        public final AnonymousClass1 mActivityStartRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.doze.PluginAODManager.7.1
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                RemoteAnimationTarget remoteAnimationTarget;
                if (PluginAODManager.this.mSubScreenPlugin == null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                    return;
                }
                if (remoteAnimationTargetArr != null && remoteAnimationTargetArr.length != 0 && (remoteAnimationTarget = remoteAnimationTargetArr[0]) != null) {
                    PluginAODManager.this.mSubScreenPlugin.onEnterRemoteTransition(new SubScreenSurfaceControlImpl(remoteAnimationTarget.leash, iRemoteAnimationFinishedCallback), remoteAnimationTargetArr[0].taskInfo.realActivity);
                } else {
                    Log.d("PluginAODManager", "No apps provided skipping remote animation.");
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            }

            public final void onAnimationCancelled() {
            }
        };

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final View createBatteryView() {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            Context context = pluginAODManager.mContext;
            BatteryMeterViewController batteryMeterViewController = pluginAODManager.mBatteryMeterViewController;
            if (batteryMeterViewController != null) {
                batteryMeterViewController.destroy();
            }
            BatteryMeterView batteryMeterView = new BatteryMeterView(context, null);
            BatteryMeterViewController create = pluginAODManager.mBatteryMeterViewControllerFactory.create(batteryMeterView, StatusBarLocation.AOD);
            pluginAODManager.mBatteryMeterViewController = create;
            create.init();
            return batteryMeterView;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getBouncerMessage();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getIncorrectBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getIncorrectBouncerMessage();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final ActivityOptions getRemoteTransitionOption() {
            if (!LsRune.SUBSCREEN_REMOTE_TRANSITION) {
                return ActivityOptions.makeBasic();
            }
            AnonymousClass1 anonymousClass1 = this.mActivityStartRunner;
            RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(anonymousClass1, 500L, 500L, true);
            boolean z = RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED;
            return ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter, new RemoteTransition(new RemoteAnimationRunnerCompat.AnonymousClass1(anonymousClass1), remoteAnimationAdapter.getCallingApplication(), "SysUILaunch"));
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final SubRoom getSubRoom(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getSubRoom() ", "PluginAODManager");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            SubScreenManager subScreenManager = pluginAODManager.mSubScreenManager;
            if (subScreenManager != null) {
                Log.d("SubScreenManager", "getSubRoom() " + SubScreenManager.getRoomName$1(i));
                return (SubRoom) subScreenManager.mSubRoomMap.get(Integer.valueOf(i));
            }
            if (!LsRune.COVER_VIRTUAL_DISPLAY) {
                return null;
            }
            CoverScreenManager coverScreenManager = (CoverScreenManager) pluginAODManager.mCoverScreenManagerLazy.get();
            StringBuilder sb = new StringBuilder("getSubRoom() ");
            coverScreenManager.getClass();
            ExifInterface$$ExternalSyntheticOutline0.m(sb, i != 301 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "INVALID TYPE [", "]") : "SUB_ROOM_NOTIFICATION", "CoverScreenManager");
            return (SubRoom) coverScreenManager.mSubRoomMap.get(Integer.valueOf(i));
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final List getVisibleNotificationList() {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            ArrayList arrayList = new ArrayList();
            subScreenManager.mNotifPipeline.getAllNotifs().stream().forEach(new SubScreenManager$$ExternalSyntheticLambda1(arrayList, 1));
            return arrayList;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final PluginSystemUIWallpaperUtils getWallpaperUtils() {
            return PluginAODManager.this.mWallpaperUtilsWrapper;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isCaptureEnabled() {
            return PluginAODManager.this.mEmm.isCaptureEnabled;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isDualDarInnerAuthRequired() {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            return pluginAODManager.mKeyguardUpdateMonitor.isDualDarInnerAuthRequired(pluginAODManager.mSelectedUserInteractor.getSelectedUserId());
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isFullscreenBouncer() {
            return SecurityUtils.checkFullscreenBouncer(PluginAODManager.this.mKeyguardUpdateMonitor.getCurrentSecurityMode());
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isKeyguardShowing() {
            return PluginAODManager.this.mKeyguardUpdateMonitor.mKeyguardShowing;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isSecure() {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            return pluginAODManager.mKeyguardUpdateMonitor.isSecure() && !pluginAODManager.mKeyguardUpdateMonitor.getUserCanSkipBouncer(pluginAODManager.mSelectedUserInteractor.getSelectedUserId());
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isSimPinSecure() {
            return PluginAODManager.this.mKeyguardUpdateMonitor.isSimPinSecure();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean isUserUnlocked() {
            Log.d("PluginAODManager", "isUserUnlocked() ");
            return PluginAODManager.this.mKeyguardUpdateMonitor.isUserUnlocked$1();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void launchApp(ComponentName componentName) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("launchApp() ", componentName, "PluginAODManager");
            int i = PluginAODManager.$r8$clinit;
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.getClass();
            if (componentName == null) {
                return;
            }
            if (TextUtils.equals(PluginLockShortcutTask.FLASH_LIGHT_TASK, componentName.getClassName())) {
                SubscreenFlashLightController.getInstance(pluginAODManager.mContext).startFlashActivity();
                return;
            }
            DozeServiceHost dozeServiceHost = (DozeServiceHost) pluginAODManager.mDozeServiceHostLazy.get();
            dozeServiceHost.getClass();
            ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).getClass();
            if (KeyguardShortcutManager.isSamsungCameraPackage(componentName)) {
                ((CameraLauncher) dozeServiceHost.mCentralSurfaces.mCameraLauncherLazy.get()).launchCamera(3, true);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void onSubScreenBouncerStateChanged(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("onSubScreenBouncerStateChanged() ", "PluginAODManager", z);
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchSubScreenBouncerStateChanged(z);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void requestDualState(boolean z) {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            if (subScreenManager.mDeviceState == 4) {
                subScreenManager.requestDualState(z);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setAODVisibleState(int i) {
            KeyguardWallpaperController keyguardWallpaperController = PluginAODManager.this.mKeyguardWallpaperController;
            keyguardWallpaperController.getClass();
            Log.d("KeyguardWallpaperController", "setAODVisibleState: state=" + i);
            try {
                keyguardWallpaperController.mService.notifyAodVisibilityState(i);
            } catch (RemoteException e) {
                Log.d("KeyguardWallpaperController", "setAODVisibleState: System dead?" + e);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setBatteryMeterViewColor(int i, int i2, float f) {
            PluginAODManager.this.mBatteryMeterViewController.setAodBatteryColorAlpha(f, i, i2);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setDisplayStateOverrideWithDisplayId(int i, int i2) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mDisplayManager == null) {
                pluginAODManager.mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
            try {
                Log.i("PluginAODManager", "updateDisplayStateOverrideWithDisplayId: displayState=" + i);
                IDisplayManager iDisplayManager = pluginAODManager.mDisplayManager;
                if (iDisplayManager == null) {
                    Log.e("PluginAODManager", "updateDisplayStateOverrideWithDisplayId : mDisplayManager is null!! ERROR case");
                    return;
                }
                iDisplayManager.setDisplayStateOverrideWithDisplayId(pluginAODManager.mDisplayStateLock, i, i2, 10000);
                if (i == 2) {
                    if (pluginAODManager.mPassiveModeToken != null) {
                        Log.i("PluginAODManager", "updateDisplayStateOverrideWithDisplayId: previous token release");
                        pluginAODManager.mPassiveModeToken.release();
                    }
                    pluginAODManager.mPassiveModeToken = pluginAODManager.mDisplayManager.acquirePassiveModeToken(pluginAODManager.mToken, "PluginAODManager");
                    Log.i("PluginAODManager", "updateDisplayStateOverrideWithDisplayId: acquirePassiveModeToken");
                    return;
                }
                IRefreshRateToken iRefreshRateToken = pluginAODManager.mPassiveModeToken;
                if (iRefreshRateToken != null) {
                    iRefreshRateToken.release();
                    pluginAODManager.mPassiveModeToken = null;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void setEnableDLS(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setEnableDLS() ", "PluginAODManager", z);
            PluginAODManager.this.mPluginLockMediator.setPluginWallpaperState(1, z ? 1 : 0);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean shouldControlScreenOff() {
            boolean z = PluginAODManager.this.mDozeParameters.mControlScreenOffAnimation;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldControlScreenOff() : ", "PluginAODManager", z);
            return z;
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStartSubscreenBiometric(null);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startFingerprintState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStartSubscreenFingerprint();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startSubHomeActivity() {
            Log.d("PluginAODManager", "startSubHomeActivity() ");
            PluginAODManager.this.mSubScreenManager.startSubHomeActivity();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void startSubHomeActivityIfNeeded() {
            Log.d("PluginAODManager", "startSubHomeActivityIfNeeded() ");
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                subScreenManager.mDismissCallbackRegistry.notifyDismissCancelled();
                subScreenManager.mRequestBouncerForLauncherTask = false;
                if (((ArrayList) subScreenManager.mOccludedApps).isEmpty()) {
                    subScreenManager.startSubHomeActivity();
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void stopBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.dispatchStopSubscreenBiometric();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void updateBiometricState() {
            PluginAODManager.this.mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_SUB_SCREEN);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void updateSubScreenFallback(boolean z) {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateFallback()  , ", " , ", z);
                m.append(subScreenManager.mFallback);
                Log.d("SubScreenManager", m.toString());
                if (z) {
                    subScreenManager.startSubScreenFallback(subScreenManager.mSubDisplay);
                    return;
                }
                if (!subScreenManager.mKeyguardUpdateMonitor.isUserUnlocked$1()) {
                    Log.d("SubScreenManager", "updateFallback. Do not unlocked. So not finish ");
                } else if (subScreenManager.mFallback != null) {
                    subScreenManager.startSubHomeActivity();
                    subScreenManager.mHandler.sendEmptyMessageDelayed(3000, 500L);
                }
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void verifyCredential(String str) {
            PluginAODManager.this.mKeyguardViewController.requestUnlock(str);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void onClockPageClicked() {
        }
    };
    public final AnonymousClass8 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.doze.PluginAODManager.8
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            Log.d("PluginAODManager", "onEntryAdded: ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            pluginAODManager.getClass();
            if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                synchronized (pluginAODManager.mSmartAlerts) {
                    pluginAODManager.mSmartAlerts.add(statusBarNotification);
                }
                pluginAODManager.logSmartAlert("addNotification");
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().addNotification(statusBarNotification);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().addNotification(statusBarNotification);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x003a, code lost:
        
            r6 = r4.mAODPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x003c, code lost:
        
            if (r6 == null) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
        
            r6.getNotificationManager().removeNotification(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:
        
            r4 = r4.mCoverPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
        
            if (r4 == null) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x004a, code lost:
        
            r4.getNotificationManager().removeNotification(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
        
            return;
         */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onEntryRemoved(com.android.systemui.statusbar.notification.collection.NotificationEntry r5, int r6) {
            /*
                r4 = this;
                java.lang.String r6 = "PluginAODManager"
                java.lang.String r0 = "onEntryRemoved: "
                android.util.Log.d(r6, r0)
                com.android.systemui.doze.PluginAODManager r4 = com.android.systemui.doze.PluginAODManager.this
                java.lang.String r5 = r5.mKey
                java.util.ArrayList r6 = r4.mSmartAlerts
                int r0 = r6.size()
                r1 = 0
            L12:
                if (r1 >= r0) goto L3a
                java.lang.Object r2 = r6.get(r1)
                int r1 = r1 + 1
                android.service.notification.StatusBarNotification r2 = (android.service.notification.StatusBarNotification) r2
                if (r2 == 0) goto L12
                java.lang.String r3 = r2.getKey()
                boolean r3 = r3.equals(r5)
                if (r3 == 0) goto L12
                java.util.ArrayList r3 = r4.mSmartAlerts
                monitor-enter(r3)
                java.util.ArrayList r6 = r4.mSmartAlerts     // Catch: java.lang.Throwable -> L37
                r6.remove(r2)     // Catch: java.lang.Throwable -> L37
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L37
                java.lang.String r6 = "removeNotification"
                r4.logSmartAlert(r6)
                goto L3a
            L37:
                r4 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L37
                throw r4
            L3a:
                com.android.systemui.plugins.aod.PluginAOD r6 = r4.mAODPlugin
                if (r6 == 0) goto L46
                com.android.systemui.plugins.aod.PluginAODNotificationManager r4 = r6.getNotificationManager()
                r4.removeNotification(r5)
                return
            L46:
                com.android.systemui.plugins.cover.PluginCover r4 = r4.mCoverPlugin
                if (r4 == 0) goto L51
                com.android.systemui.plugins.aod.PluginAODNotificationManager r4 = r4.getNotificationManager()
                r4.removeNotification(r5)
            L51:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.PluginAODManager.AnonymousClass8.onEntryRemoved(com.android.systemui.statusbar.notification.collection.NotificationEntry, int):void");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            Log.d("PluginAODManager", "onEntryUpdated: ");
            PluginAODManager pluginAODManager = PluginAODManager.this;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            pluginAODManager.getClass();
            if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                ArrayList arrayList = pluginAODManager.mSmartAlerts;
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i);
                    i++;
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) obj;
                    if (statusBarNotification2 != null && statusBarNotification != null && statusBarNotification2.getKey().equals(statusBarNotification.getKey())) {
                        synchronized (pluginAODManager.mSmartAlerts) {
                            try {
                                pluginAODManager.mSmartAlerts.remove(statusBarNotification2);
                                if (PluginAODManager.isSmartAlertNoti(statusBarNotification)) {
                                    pluginAODManager.mSmartAlerts.add(statusBarNotification);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        pluginAODManager.logSmartAlert("updateNotification");
                    }
                }
            }
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateNotification(statusBarNotification);
                return;
            }
            PluginCover pluginCover = pluginAODManager.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.getNotificationManager().updateNotification(statusBarNotification);
            }
        }
    };
    public boolean mIsFolderOpened = true;
    public boolean mIsInitFoldState = true;
    public int mCurrentPhoneState = 0;
    public final IBinder mRefreshRateToken = new Binder();

    static {
        boolean z = DozeService.DEBUG;
    }

    /* JADX WARN: Type inference failed for: r14v20, types: [com.android.systemui.doze.PluginAODManager$9] */
    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.systemui.doze.PluginAODManager$5] */
    /* JADX WARN: Type inference failed for: r5v11, types: [com.android.systemui.doze.PluginAODManager$6] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.systemui.doze.PluginAODManager$7] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.systemui.doze.PluginAODManager$8] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.doze.PluginAODManager$2] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.doze.PluginAODManager$3] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.doze.PluginAODManager$4] */
    public PluginAODManager(Context context, SelectedUserInteractor selectedUserInteractor, LockscreenNotificationManager lockscreenNotificationManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, PluginFaceWidgetManager pluginFaceWidgetManager, SettingsHelper settingsHelper, DozeParameters dozeParameters, PluginLockMediator pluginLockMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardWallpaper keyguardWallpaper, Lazy lazy, SubScreenManager subScreenManager, Lazy lazy2, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy3, NotificationLockscreenUserManager notificationLockscreenUserManager, SamsungServiceLogger samsungServiceLogger, KeyguardFoldController keyguardFoldController, DumpManager dumpManager, PluginLockStarManager pluginLockStarManager, NotificationsController notificationsController, ActiveNotificationsInteractor activeNotificationsInteractor, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, final SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, KeyguardViewMediatorHelper keyguardViewMediatorHelper, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, ConfigurationController configurationController, AODTouchModeManager aODTouchModeManager, KeyguardWallpaperController keyguardWallpaperController, KeyguardDisplayManager keyguardDisplayManager) {
        int i = 0;
        this.mContext = context;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewController = keyguardViewController;
        this.mSettingsHelper = settingsHelper;
        this.mDozeParameters = dozeParameters;
        this.mPluginLockMediator = pluginLockMediator;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mDozeServiceHostLazy = lazy;
        this.mWallpaperUtilsWrapper = faceWidgetWallpaperUtilsWrapper;
        this.mDisplayLifeCycle = displayLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mCommonNotifCollectionLazy = lazy3;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mFaceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenManager = subScreenManager;
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                if (subScreenManager != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
                    subScreenManager.setSubRoom(301, subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null);
                } else {
                    subscreenNotificationController.getClass();
                }
            }
            SubscreenMusicWidgetController subscreenMusicWidgetController = (SubscreenMusicWidgetController) Dependency.sDependency.getDependencyInner(SubscreenMusicWidgetController.class);
            subScreenManager.setSubRoom(304, new SubscreenMusicWidgetSubroom(subscreenMusicWidgetController.mContext, subscreenMusicWidgetController.mMediaHost));
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                if (QpRune.QUICK_SUBSCREEN_PANEL_WINDOW) {
                    subScreenQuickPanelWindowController.getClass();
                    if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
                        subScreenQuickPanelWindowController.mCommandQueue.addCallback((CommandQueue.Callbacks) subScreenQuickPanelWindowController);
                        subScreenQuickPanelWindowController.mDisplayLifecycle.addObserver(subScreenQuickPanelWindowController.mFoldStateChangedListener);
                        subScreenQuickPanelWindowController.mDisplayManager.registerDisplayListener(subScreenQuickPanelWindowController.mDisplayListener, null);
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2415, -2138832832, -3);
                        subScreenQuickPanelWindowController.mLp = layoutParams;
                        layoutParams.flags &= -9;
                        layoutParams.privateFlags |= 16;
                        layoutParams.layoutInDisplayCutoutMode = 3;
                        layoutParams.gravity = 48;
                        layoutParams.screenOrientation = 1;
                        layoutParams.setTitle("SubScreenQuickPanel");
                        WindowManager.LayoutParams layoutParams2 = subScreenQuickPanelWindowController.mLp;
                        SubScreenQSEventHandler subScreenQSEventHandler = subScreenQuickPanelWindowController.mSubScreenQSEventHandler;
                        layoutParams2.semSetScreenTimeout(subScreenQSEventHandler.getScreenTimeOut());
                        WindowManager.LayoutParams layoutParams3 = subScreenQuickPanelWindowController.mLp;
                        layoutParams3.setFitInsetsTypes(layoutParams3.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()) & (~WindowInsets.Type.statusBars()));
                        subScreenQuickPanelWindowController.mWindowManager = (WindowManager) subScreenQuickPanelWindowController.mContext.getSystemService("window");
                        if (subScreenQuickPanelWindowController.mSubScreenQsWindowView == null) {
                            SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = (SubScreenQuickPanelWindowView) LayoutInflater.from(subScreenQuickPanelWindowController.mContext).inflate(R.layout.subscreen_quickpanel_window_view, (ViewGroup) null);
                            subScreenQuickPanelWindowController.mSubScreenQsWindowView = subScreenQuickPanelWindowView;
                            if (QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL) {
                                subScreenQuickPanelWindowView.semSetRoundedCorners(0);
                            } else {
                                subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCorners(15, subScreenQuickPanelWindowController.mContext.getResources().getDimensionPixelSize(17105923));
                                subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCornerColor(15, subScreenQuickPanelWindowController.mContext.getColor(android.R.color.black));
                            }
                            SubscreenQsPanelController subscreenQsPanelController = subScreenQuickPanelWindowController.mSubscreenQsPanelController;
                            View view = subscreenQsPanelController.getSubRoomQuickPanel().mMainView;
                            subScreenQuickPanelWindowController.mQSPanel = view;
                            if (view instanceof SubscreenParentLayout) {
                                SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 = new SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15(subScreenQuickPanelWindowController);
                                ((SubscreenParentLayout) view).getClass();
                                SecMediaHost secMediaHost = (SecMediaHost) Dependency.sDependency.getDependencyInner(SecMediaHost.class);
                                secMediaHost.mCoverQSClickConsumer = subScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
                                SecMediaHost.iteratePlayers((SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(MediaType.COVER_QS), new SecMediaHost$$ExternalSyntheticLambda15(subScreenQuickPanelWindowController$$ExternalSyntheticLambda15, 2));
                                SubscreenParentLayout subscreenParentLayout = (SubscreenParentLayout) subScreenQuickPanelWindowController.mQSPanel;
                                QSHost qSHost = subscreenQsPanelController.mHost;
                                subscreenParentLayout.qsHost = qSHost;
                                if (qSHost != null) {
                                    qSHost.addCallback(subscreenParentLayout.mQSHostCallback);
                                }
                                subScreenQuickPanelWindowController.mSubroomQuickSettingsQSPanelBaseView = (SubroomQuickSettingsQSPanelBaseView) ((SubscreenParentLayout) subScreenQuickPanelWindowController.mQSPanel).findViewById(R.id.subscreen_quick_settings_baseview);
                            }
                            subScreenQuickPanelWindowController.mSubScreenQsWindowView.addView(subScreenQuickPanelWindowController.mQSPanel);
                            new SecCoverBlurController(subScreenQuickPanelWindowController.mContext, subScreenQuickPanelWindowController.mQSPanel).applyBlur();
                        }
                        subScreenQuickPanelWindowController.mWindowManager.addView(subScreenQuickPanelWindowController.mSubScreenQsWindowView, subScreenQuickPanelWindowController.mLp);
                        Context context2 = subScreenQuickPanelWindowController.mContext;
                        subScreenQuickPanelWindowController.mPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
                        subScreenQuickPanelWindowController.mMaxExpandedHeight = DeviceState.getScreenHeight(context2);
                        subScreenQSEventHandler.init();
                        SubScreenQuickPanelWindowView subScreenQuickPanelWindowView2 = subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                        subScreenQuickPanelWindowView2.mSubScreenQSTouchHandler = subScreenQSEventHandler;
                        subScreenQuickPanelWindowView2.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.3
                            public AnonymousClass3() {
                            }

                            @Override // android.view.View.OnApplyWindowInsetsListener
                            public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                                int safeInsetTop = displayCutout != null ? displayCutout.getSafeInsetTop() : 0;
                                View view3 = SubScreenQuickPanelWindowController.this.mQSPanel;
                                if (view3 != null) {
                                    view3.setPadding(view3.getPaddingLeft(), safeInsetTop, SubScreenQuickPanelWindowController.this.mQSPanel.getPaddingRight(), SubScreenQuickPanelWindowController.this.mQSPanel.getPaddingBottom());
                                }
                                return windowInsets;
                            }
                        });
                        SubScreenQuickPanelHeaderController subScreenQuickPanelHeaderController = ((DaggerReferenceGlobalRootComponent.SubScreenQuickPanelComponentImpl) subScreenQuickPanelWindowController.mSubScreenComponent.create(subScreenQuickPanelWindowController.mSubScreenQsWindowView)).getSubScreenQuickPanelHeaderController();
                        subScreenQuickPanelWindowController.mSubScreenQuickPanelHeaderController = subScreenQuickPanelHeaderController;
                        subScreenQuickPanelHeaderController.init();
                        subScreenManager.setSubRoom(300, new SubScreenQuickPanelWindowController.PanelExpandedFractionProvider(subScreenQuickPanelWindowController, i));
                    } else {
                        Log.e("SubScreenQuickPanelWindowController", "SubScreenQuickPanelWindowController not initialized for non-primary user, just return");
                    }
                }
            } else if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
                if (this.mSubscreenQsPanelController == null) {
                    this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
                }
                this.mSubscreenQsPanelController.init();
                subScreenManager.setSubRoom(300, this.mSubscreenQsPanelController.getSubRoomQuickPanel());
            }
        }
        this.mCoverScreenManagerLazy = lazy2;
        AODDumpLog.logger = samsungServiceLogger;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.doze.PluginAODManager.9
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1000) {
                    return;
                }
                StringBuilder sb = new StringBuilder("MSG_EXPAND_NOTI_PANEL: mDozing=");
                PluginAODManager pluginAODManager = PluginAODManager.this;
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, pluginAODManager.mDozing, "PluginAODManager");
                if (pluginAODManager.mDozing) {
                    return;
                }
                StatusBarNotification statusBarNotification = (StatusBarNotification) message.obj;
                if (((DozeServiceHost) pluginAODManager.mDozeServiceHostLazy.get()).mCentralSurfaces == null) {
                    Log.i("DozeServiceHost", "animateExpandNotificationsPanel() called before initialize(), return");
                } else {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("animateExpandNotificationsPanel sbn="), statusBarNotification == null, "DozeServiceHost");
                }
            }
        };
        this.mPluginLockStarManager = pluginLockStarManager;
        pluginLockStarManager.registerCallback("AOD", new PluginLockStarManager.LockStarCallback(this) { // from class: com.android.systemui.doze.PluginAODManager.10
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
            }
        });
        this.mActiveNotificationsInteractor = activeNotificationsInteractor;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "PluginAODManager", this);
        this.mFoldController = keyguardFoldController;
        if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.doze.PluginAODManager$$ExternalSyntheticLambda0
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    PluginKeyguardStatusView pluginKeyguardStatusView;
                    PluginAODManager pluginAODManager = PluginAODManager.this;
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onFolderStateChanged isOpened : ", "PluginAODManager", z);
                    pluginAODManager.mIsFolderOpened = z;
                    if (LsRune.AOD_SUB_DISPLAY_COVER) {
                        pluginAODManager.updateAnimateScreenOff();
                    }
                    if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
                        if (!z && !pluginAODManager.mIsInitFoldState) {
                            pluginAODManager.setStartedByFolderClosed(true);
                        }
                        pluginAODManager.mIsInitFoldState = false;
                    }
                    PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                    if (pluginAOD != null) {
                        pluginAOD.onFolderStateChanged(z);
                    }
                    if (!z) {
                        PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                        if (pluginSubScreen != null) {
                            pluginSubScreen.onDozeAmountChanged(pluginAODManager.mStatusBarStateController.getDozeAmount());
                            return;
                        }
                        return;
                    }
                    FaceWidgetContainerWrapper faceWidgetContainerWrapper = pluginAODManager.mFaceWidgetContainerWrapper;
                    if (faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) {
                        return;
                    }
                    pluginKeyguardStatusView.setDarkAmount(0.0f);
                }
            }, 4, false);
        }
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mKeyguardViewMediatorHelper = keyguardViewMediatorHelper;
        this.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mAODTouchModeManager = aODTouchModeManager;
        this.mKeyguardWallpaperController = keyguardWallpaperController;
        this.mKeyguardDisplayManager = keyguardDisplayManager;
    }

    public static boolean isSmartAlertNoti(StatusBarNotification statusBarNotification) {
        Notification notification2;
        Bundle bundle;
        return (statusBarNotification == null || (notification2 = statusBarNotification.getNotification()) == null || (bundle = notification2.extras) == null || TextUtils.isEmpty(bundle.getString("smart_alert_title", ""))) ? false : true;
    }

    public final void addConnectionRunnable(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (this.mConnectionRunnableList == null) {
            this.mConnectionRunnableList = new ArrayList();
        }
        if (this.mConnectionRunnableList.contains(runnable)) {
            return;
        }
        this.mConnectionRunnableList.add(runnable);
    }

    public final void chargingAnimStarted(boolean z) {
        PluginAOD pluginAOD = this.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.onChargingAnimStarted(z);
            return;
        }
        PluginClockPack pluginClockPack = this.mClockPackPlugin;
        if (pluginClockPack != null) {
            pluginClockPack.onChargingAnimStarted(z);
        }
    }

    public final void disableStatusBar(int i) {
        Context context;
        if (this.mStatusBarManager == null && (context = this.mCoverPluginContext) != null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            Log.w("PluginAODManager", "disableStatusBar() : Could not get status bar manager");
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "disableStatusBar() ", "PluginAODManager");
            this.mStatusBarManager.disable(i);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("");
        printWriter.println("      Plugins");
        printWriter.println("        mAODPlugin : " + this.mAODPlugin);
        printWriter.println("        mClockPackPlugin : " + this.mClockPackPlugin);
        printWriter.println("        mCoverPlugin : " + this.mCoverPlugin);
        printWriter.println("        mSubScreenPlugin : " + this.mSubScreenPlugin);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("        supportAODLightReveal : "), LsRune.AOD_LIGHT_REVEAL, printWriter);
    }

    public final void enableTouch(boolean z) {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("enableTouch : ", " FaceWidgetContainerWrapper = ", z);
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mFaceWidgetContainerWrapper;
        m.append(faceWidgetContainerWrapper);
        Log.d("PluginAODManager", m.toString());
        if (faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) {
            return;
        }
        pluginKeyguardStatusView.setTouchEnabled(z);
    }

    public final void initAODOverlayContainer() {
        AODOverlayContainer aODOverlayContainer;
        if (this.mAODOverlayContainer != null) {
            return;
        }
        Log.d("PluginAODManager", "initAODOverlayContainer()");
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeServiceHostLazy.get();
        if (dozeServiceHost.getNotificationPanelView() == null) {
            aODOverlayContainer = null;
        } else {
            if (dozeServiceHost.mAODOverlayContainer == null) {
                dozeServiceHost.mAODOverlayContainer = (AODOverlayContainer) ((ViewStub) ((ViewGroup) dozeServiceHost.getNotificationPanelView().findViewWithTag("aod_overlay_container_stub_parent")).findViewById(R.id.aod_overlay_container_stub)).inflate();
            }
            aODOverlayContainer = dozeServiceHost.mAODOverlayContainer;
        }
        this.mAODOverlayContainer = aODOverlayContainer;
    }

    public final void logSmartAlert(String str) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb.append("[onUpdateSmartAlert:" + str + "] ");
        sb.append(this.mSmartAlerts.size() + 81);
        sb.append(" showingKeys ");
        ArrayList arrayList = this.mSmartAlerts;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            StatusBarNotification statusBarNotification = (StatusBarNotification) obj;
            if (statusBarNotification != null) {
                sb2.append("[" + statusBarNotification.getKey() + ']');
            }
        }
        sb.append(sb2.toString());
        String sb3 = sb.toString();
        SamsungServiceLogger samsungServiceLogger = AODDumpLog.logger;
        LogLevel logLevel = LogLevel.DEBUG;
        SamsungServiceLogger samsungServiceLogger2 = AODDumpLog.logger;
        if (samsungServiceLogger2 != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger2).logWithThreadId("", logLevel, sb3);
        }
    }

    public final boolean needControlScreenOff() {
        PluginAODStateProvider pluginAODStateProvider;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if ((keyguardUpdateMonitor.getCoverState() != null && !keyguardUpdateMonitor.getCoverState().switchState) || keyguardUpdateMonitor.isBouncerFullyShown() || keyguardUpdateMonitor.isScreenOffMemoRunning() || this.mStartedByFolderClosed || this.mKeyguardDisplayManager.isDesktopMode() || this.mCurrentPhoneState != 0) {
            return false;
        }
        boolean z = this.mDozeParameters.mAODParameters.mDozeAlwaysOn;
        if (z && (pluginAODStateProvider = this.mPluginAODStateProvider) != null && pluginAODStateProvider.getNeedScreenOff()) {
            return false;
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        updateAnimateScreenOff();
    }

    public final void onTransitionEnded() {
        if (this.mAODMachine == null) {
            return;
        }
        boolean z = this.mDozeParameters.mControlScreenOffAnimation;
        CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("onTransitionEnded mClockTransitionStarted:"), this.mClockTransitionStarted, " shouldControlScreenOff : ", z, "PluginAODManager");
        if (this.mClockTransitionStarted) {
            this.mAODMachine.requestState(DozeMachine.State.DOZE_TRANSITION_ENDED);
            this.mAODTouchModeManager.setTouchMode(AODTouchModeManager.TouchMode.DOUBLE);
            this.mClockTransitionStarted = false;
            if (z) {
                this.mPluginLockMediator.onAodTransitionEnd();
                this.mKeyguardWallpaper.getClass();
            }
        }
    }

    public final void registerUpdateMonitor$1() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        AnonymousClass2 anonymousClass2 = this.mWakefulnessObserver;
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.addObserver(anonymousClass2);
        this.mWakefulness = wakefulnessLifecycle.mWakefulness;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mWakefulness, "PluginAODManager", new StringBuilder("registerUpdateMonitor mWakefulness="));
    }

    public final void removeUpdateMonitor() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        AnonymousClass2 anonymousClass2 = this.mWakefulnessObserver;
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.removeObserver(anonymousClass2);
        this.mWakefulness = wakefulnessLifecycle.mWakefulness;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mWakefulness, "PluginAODManager", new StringBuilder("removeUpdateMonitor mWakefulness="));
    }

    public final void setAODPlugin(PluginAOD pluginAOD) {
        AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
        AnonymousClass5 anonymousClass5 = this.mStateListener;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        if (pluginAOD == null && this.mCoverPlugin == null && this.mSubScreenPlugin == null) {
            removeUpdateMonitor();
            sysuiStatusBarStateController.removeCallback(anonymousClass5);
            if (lockscreenNotificationManager != null) {
                lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
            }
        }
        this.mAODPlugin = pluginAOD;
        AnonymousClass8 anonymousClass8 = this.mNotifCollectionListener;
        Lazy lazy = this.mCommonNotifCollectionLazy;
        if (pluginAOD == null) {
            this.mDozeParameters.mAODParameters.updateDozeAlwaysOn();
            updateAnimateScreenOff();
            Log.d("PluginAODManager", "removeNotiCollectionListener: ");
            NotifCollection notifCollection = ((NotifPipeline) ((CommonNotifCollection) lazy.get())).mNotifCollection;
            notifCollection.getClass();
            Assert.isMainThread();
            notifCollection.mNotifCollectionListeners.remove(anonymousClass8);
            return;
        }
        if (this.mCoverPlugin == null && this.mSubScreenPlugin == null) {
            registerUpdateMonitor$1();
        }
        sysuiStatusBarStateController.addCallback(anonymousClass5);
        PluginAOD pluginAOD2 = this.mAODPlugin;
        float dozeAmount = sysuiStatusBarStateController.getDozeAmount();
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        pluginAOD2.onDozeAmountChanged(dozeAmount, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(statusBarStateControllerImpl.mDozeAmount));
        this.mAODPlugin.setIsDozing(this.mDozing, false);
        if (lockscreenNotificationManager != null) {
            this.mAODPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
            lockscreenNotificationManager.addCallback(anonymousClass4);
            Log.d("PluginAODManager", "addNotiCollectionListener: ");
            ((NotifPipeline) ((CommonNotifCollection) lazy.get())).addCollectionListener(anonymousClass8);
        }
        this.mAODPlugin.setAODUICallback(this.mAODUICallback);
    }

    public final void setCoverPlugin(Context context, PluginCover pluginCover) {
        CoverScreenManager coverScreenManager;
        this.mCoverPluginContext = context;
        AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        if (pluginCover == null && this.mAODPlugin == null && this.mSubScreenPlugin == null) {
            if (lockscreenNotificationManager != null) {
                lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY) {
                sysuiStatusBarStateController.removeCallback(this.mStateListener);
            }
            removeUpdateMonitor();
        }
        this.mCoverPlugin = pluginCover;
        if (pluginCover == null) {
            disableStatusBar(0);
            return;
        }
        if (this.mAODPlugin == null && this.mSubScreenPlugin == null) {
            registerUpdateMonitor$1();
        }
        if (lockscreenNotificationManager != null) {
            this.mCoverPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
            lockscreenNotificationManager.addCallback(anonymousClass4);
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            if (NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
                if (subscreenDeviceModelParent != null) {
                    subscreenDeviceModelParent.onDisplayReady();
                }
                Lazy lazy = this.mCoverScreenManagerLazy;
                if (lazy != null && (coverScreenManager = (CoverScreenManager) lazy.get()) != null) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
                    SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent2 != null ? subscreenDeviceModelParent2.getSubRoomNotification() : null;
                    Log.d("CoverScreenManager", "setSubRoom() SUB_ROOM_NOTIFICATION, " + subRoomNotification);
                    coverScreenManager.mSubRoomMap.put(301, subRoomNotification);
                }
            }
            this.mCoverPlugin.setPluginCallback(this.mSubUICallback);
            this.mCoverPlugin.onDozingChanged(this.mDozing);
            this.mCoverPlugin.onDozeAmountChanged(sysuiStatusBarStateController.getDozeAmount());
        }
    }

    public final void setIsDozing(boolean z, boolean z2) {
        AODOverlayContainer aODOverlayContainer;
        if (this.mDozing == z) {
            return;
        }
        if (!z && this.mKeyguardFastBioUnlockController.isFastWakeAndUnlockMode() && this.mSettingsHelper.isEnabledBiometricUnlockVI() && !this.mSettingsHelper.isAODShown()) {
            this.mScrimController.mSecLsScrimControlHelper.setFrontScrimToBlack(true);
        }
        initAODOverlayContainer();
        if (!z && (aODOverlayContainer = this.mAODOverlayContainer) != null) {
            aODOverlayContainer.setVisibility(8);
        }
        this.mDozing = z;
        PluginAOD pluginAOD = this.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.setIsDozing(z, z2);
        }
        if (LsRune.SUBSCREEN_UI) {
            PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
            if (pluginSubScreen != null) {
                pluginSubScreen.onDozingChanged(z);
            }
            if (LsRune.SUBSCREEN_WATCHFACE && !this.mDisplayLifeCycle.mIsFolderOpened) {
                boolean z3 = this.mDozing;
                SubScreenManager subScreenManager = this.mSubScreenManager;
                PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration = this.mSysUIConfig;
                if (z3) {
                    if (this.mWakefulnessLifecycle.mLastSleepReason == 13) {
                        subScreenManager.getClass();
                        subScreenManager.mBackgroundExecutor.executeDelayed(new SubScreenManager$$ExternalSyntheticLambda0(subScreenManager, 2), 100L);
                    } else if (pluginAODSystemUIConfiguration.get(2, 0) == 0) {
                        subScreenManager.adjustSubHomeActivityOrder(true);
                    }
                } else if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && pluginAODSystemUIConfiguration.get(2, 0) == 0) {
                    subScreenManager.adjustSubHomeActivityOrder(false);
                }
            }
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            PluginCover pluginCover = this.mCoverPlugin;
            if (pluginCover != null) {
                pluginCover.onDozingChanged(z);
            }
            if (this.mKeyguardUpdateMonitor.isCoverClosed() && this.mDozing) {
                ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).prepareCoverHomeActivity();
            }
        }
    }

    public final void setStartedByFolderClosed(boolean z) {
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("setStartedByFolderClosed: ", "PluginAODManager", z);
        this.mStartedByFolderClosed = z;
        updateAnimateScreenOff();
    }

    public final void setSubScreenPlugin(PluginSubScreen pluginSubScreen) {
        if (LsRune.SUBSCREEN_UI) {
            PluginAOD pluginAOD = this.mAODPlugin;
            AnonymousClass4 anonymousClass4 = this.mLockscreenNotiCallback;
            LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
            if (pluginAOD == null && this.mCoverPlugin == null && pluginSubScreen == null) {
                lockscreenNotificationManager.mCallbacks.remove(anonymousClass4);
                removeUpdateMonitor();
            }
            this.mSubScreenPlugin = pluginSubScreen;
            if (pluginSubScreen != null) {
                if (this.mAODPlugin == null && this.mCoverPlugin == null) {
                    registerUpdateMonitor$1();
                }
                this.mSubScreenPlugin.getNotificationManager().setCallback(this.mNotificationCallback);
                lockscreenNotificationManager.addCallback(anonymousClass4);
                this.mSubScreenPlugin.setPluginCallback(this.mSubUICallback);
                this.mSubScreenPlugin.onDozingChanged(this.mDozing);
                this.mSubScreenPlugin.onFolderStateChanged(((KeyguardFoldControllerImpl) this.mFoldController).isFoldOpened());
                this.mSubScreenPlugin.onDozeAmountChanged(this.mStatusBarStateController.getDozeAmount());
            }
        }
    }

    public final void showCoverToast(PendingIntent pendingIntent, Intent intent) {
        Log.d("PluginAODManager", "showCoverToast() with FIntent");
        if (this.mCoverPlugin != null) {
            this.mCoverPlugin.showCoverToast(pendingIntent, intent.getBooleanExtra("ignoreUnlock", false));
        }
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen != null) {
            pluginSubScreen.requestOpenAppPopup(pendingIntent, intent);
        }
    }

    public final void updateAnimateScreenOff() {
        boolean z = LsRune.AOD_DISABLE_CLOCK_TRANSITION;
        boolean z2 = false;
        DozeParameters dozeParameters = this.mDozeParameters;
        if (z) {
            dozeParameters.setControlScreenOffAnimation(false);
            return;
        }
        if (dozeParameters.getDisplayNeedsBlanking()) {
            return;
        }
        boolean alwaysOn = dozeParameters.getAlwaysOn();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (alwaysOn && ((keyguardUpdateMonitor.isKeyguardVisible() || ((LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && !this.mIsFolderOpened) || (LsRune.AOD_FULLSCREEN && dozeParameters.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation()))) && needControlScreenOff())) {
            z2 = true;
        }
        if (this.mControlScreenOff != z2) {
            this.mControlScreenOff = z2;
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateAnimateScreenOff : controlScreenOff=", " AlwaysOn=", z2);
            m.append(dozeParameters.getAlwaysOn());
            m.append(" keyguardShowing=");
            m.append(keyguardUpdateMonitor.isKeyguardVisible());
            m.append(" powerSaveActive=");
            m.append(((BatteryControllerImpl) ((DozeServiceHost) this.mDozeServiceHostLazy.get()).mBatteryController).mAodPowerSave);
            m.append(" mIsFolderOpened=");
            m.append(this.mIsFolderOpened);
            m.append(" needControlScreenOff=");
            m.append(needControlScreenOff());
            m.append(" called=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(4, "PluginAODManager", m);
        }
        dozeParameters.setControlScreenOffAnimation(z2);
    }

    public final void updateRefreshRate(boolean z) {
        if (LsRune.AOD_FULLSCREEN) {
            if (((KeyguardViewMediatorHelperImpl) this.mKeyguardViewMediatorHelper).isScreenOn()) {
                Log.d("PluginAODManager", "updateRefreshRate: release token in Screen ON");
                z = false;
            }
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateRefreshRate: acquire=", " called=", z);
            m.append(Debug.getCallers(2));
            Log.i("PluginAODManager", m.toString());
            if (!z) {
                IRefreshRateToken iRefreshRateToken = this.mMaxRefreshRateToken;
                if (iRefreshRateToken != null) {
                    try {
                        iRefreshRateToken.release();
                        Log.d("PluginAODManager", "updateRefreshRate disabled");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    this.mMaxRefreshRateToken = null;
                    return;
                }
                return;
            }
            if (this.mMaxRefreshRateToken == null) {
                if (this.mIDisplayManager == null) {
                    this.mIDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                }
                IDisplayManager iDisplayManager = this.mIDisplayManager;
                if (iDisplayManager != null) {
                    try {
                        this.mMaxRefreshRateToken = iDisplayManager.acquireRefreshRateMaxLimitToken(this.mRefreshRateToken, 30, "PluginAODManager");
                        Log.d("PluginAODManager", "updateRefreshRate enabled 30hz");
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            if (this.mMaxRefreshRateToken == null) {
                Log.w("PluginAODManager", "updateRefreshRate failed");
            }
        }
    }
}
