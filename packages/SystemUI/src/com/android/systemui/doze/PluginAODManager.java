package com.android.systemui.doze;

import android.app.Notification;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.SecCoverBlurController;
import com.android.systemui.cover.CoverScreenManager;
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
import com.android.systemui.keyguard.SecLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;
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
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.SubscreenSubRoomQuickSettings;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
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
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda0;
import com.android.systemui.subscreen.SubScreenQSEventHandler;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
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
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginAODManager implements Dumpable, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public AODMachine mAODMachine;
    public AODOverlayContainer mAODOverlayContainer;
    public PluginAOD mAODPlugin;
    public final AODTouchModeManager mAODTouchModeManager;
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
    public final DozeServiceHost mDozeServiceHost;
    public boolean mDozing;
    public EngineeringModeManagerWrapper mEmm;
    public FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final KeyguardFoldController mFoldController;
    public final AnonymousClass9 mHandler;
    public IDisplayManager mIDisplayManager;
    public boolean mIsDifferentOrientation;
    public KeyguardFastBioUnlockController mKeyguardFastBioUnlockController;
    public final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewController mKeyguardViewController;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
    public final LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public IRefreshRateToken mMaxRefreshRateToken;
    public final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationsController mNotificationsController;
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
                        Optional findFirst = wakefulnessLifecycle.mMsgForLifecycle.stream().filter(new Predicate() { // from class: com.android.systemui.keyguard.SecLifecycle$$ExternalSyntheticLambda1
                            public final /* synthetic */ int f$0 = 3;
                            public final /* synthetic */ int f$1 = 13;

                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                SecLifecycle.Msg msg = (SecLifecycle.Msg) obj;
                                return msg.msg == this.f$0 && msg.reason == this.f$1;
                            }
                        }).findFirst();
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
                NotificationVisibility obtain = NotificationVisibility.obtain(str, entry.mRanking.getRank(), pluginAODManager.mNotificationsController.getActiveNotificationsCount(), true);
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
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        StatusBarNotification statusBarNotification = (StatusBarNotification) it2.next();
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
        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4, types: [int] */
        /* JADX WARN: Type inference failed for: r14v5 */
        /* JADX WARN: Type inference failed for: r14v6 */
        /* JADX WARN: Type inference failed for: r14v7 */
        /* JADX WARN: Type inference failed for: r5v14, types: [java.lang.StringBuilder] */
        @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
        public final void onNotificationInfoUpdated(ArrayList arrayList) {
            NotificationLockscreenUserManager notificationLockscreenUserManager;
            StatusBarIconView statusBarIconView;
            Log.d("PluginAODManager", "onNotificationInfoUpdated() " + arrayList.size());
            PluginAODManager pluginAODManager = PluginAODManager.this;
            pluginAODManager.mNotiIconMap.clear();
            StringBuilder sb = new StringBuilder();
            NotificationLockscreenUserManager notificationLockscreenUserManager2 = pluginAODManager.mNotificationLockscreenUserManager;
            boolean z = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager2).mShowLockscreenNotifications;
            PluginNotificationController pluginNotificationController = ((FaceWidgetNotificationControllerWrapper) pluginAODManager.mFaceWidgetNotiController).mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.isMusicFaceWidgetOn();
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                LockscreenNotificationInfo lockscreenNotificationInfo = (LockscreenNotificationInfo) it.next();
                if (lockscreenNotificationInfo != null) {
                    StatusBarNotification statusBarNotification = lockscreenNotificationInfo.mSbn;
                    FaceWidgetNotificationController faceWidgetNotificationController = pluginAODManager.mFaceWidgetNotiController;
                    statusBarNotification.getKey();
                    faceWidgetNotificationController.getClass();
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) pluginAODManager.mCommonNotifCollectionLazy.get())).mNotifCollection.getEntry(lockscreenNotificationInfo.mKey);
                    if (entry == null) {
                        Log.i("PluginAODManager", "onNotificationInfoUpdated : can not find " + lockscreenNotificationInfo.mKey);
                    } else {
                        ?? r14 = z ? 2 : z;
                        boolean z2 = z && ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager2).mShowLockscreenNotifications;
                        if (z2) {
                            r14 = 3;
                        }
                        boolean z3 = z2 && !((KeyguardNotificationVisibilityProviderImpl) pluginAODManager.mKeyguardNotificationVisibilityProvider).shouldHideNotification(entry);
                        if (z3) {
                            r14 = 4;
                        }
                        if (!z || z3) {
                            notificationLockscreenUserManager = notificationLockscreenUserManager2;
                        } else {
                            notificationLockscreenUserManager = notificationLockscreenUserManager2;
                            sb.append("[" + statusBarNotification.getKey() + "$" + r14 + "]");
                        }
                        if (z3) {
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
                                        Iterator it2 = attachedChildren.iterator();
                                        while (it2.hasNext()) {
                                            arrayList3.add(((ExpandableNotificationRow) it2.next()).mEntry.mSbn);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        notificationLockscreenUserManager2 = notificationLockscreenUserManager;
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
            int size = arrayList2.size();
            PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
            if (pluginAOD != null) {
                pluginAOD.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
            } else {
                PluginCover pluginCover = pluginAODManager.mCoverPlugin;
                if (pluginCover != null) {
                    pluginCover.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
                } else {
                    PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                    if (pluginSubScreen != null) {
                        pluginSubScreen.getNotificationManager().updateVisibleNotifications(arrayList2, arrayList3, size);
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb2.append("[updateVisibleNotifications] totalCount : [" + size + "]");
            sb2.append(" showingKeys ");
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                StatusBarNotification statusBarNotification2 = (StatusBarNotification) it3.next();
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
            if (pluginSubScreen != null) {
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
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("getLockStarData() ", "PluginAODManager", z);
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
            Lazy lazy = PluginAODManager.this.mAODAmbientWallpaperHelper.keyguardFoldControllerLazy;
            Log.d("AODAmbientWallpaperHelper", "isWonderLandAmbientWallpaper: isFolded=" + (!((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened()) + ", isMainWonderLandWallpaper=false, isSubWonderLandWallpaper=false");
            if (!LsRune.AOD_SUB_DISPLAY_LOCK) {
                return false;
            }
            ((KeyguardFoldControllerImpl) ((KeyguardFoldController) lazy.get())).isFoldOpened();
            return false;
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "registerAODDoubleTouchListener() ");
            DozeServiceHost dozeServiceHost = PluginAODManager.this.mDozeServiceHost;
            if (dozeServiceHost == null || (shadeViewController = dozeServiceHost.mNotificationPanelViewController) == null) {
                return;
            }
            shadeViewController.registerAODDoubleTouchListener(onTouchListener);
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void setBottomArea(View view) {
            Log.d("PluginAODManager", "setBottomArea() ");
            KeyguardBottomAreaView view2 = ((NotificationPanelViewController) PluginAODManager.this.mPanelViewControllerLazy.get()).mKeyguardBottomAreaViewController.getView();
            if (view2 instanceof KeyguardSecBottomAreaView) {
                KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) view2;
                ((FrameLayout) keyguardSecBottomAreaView.bottomDozeArea$delegate.getValue()).removeAllViews();
                ((FrameLayout) keyguardSecBottomAreaView.bottomDozeArea$delegate.getValue()).addView(view);
            }
        }

        @Override // com.android.systemui.plugins.aod.PluginAOD.UICallback
        public final void unregisterAODDoubleTouchListener() {
            ShadeViewController shadeViewController;
            Log.d("PluginAODManager", "unregisterAODDoubleTouchListener() ");
            DozeServiceHost dozeServiceHost = PluginAODManager.this.mDozeServiceHost;
            if (dozeServiceHost == null || (shadeViewController = dozeServiceHost.mNotificationPanelViewController) == null) {
                return;
            }
            shadeViewController.unregisterAODDoubleTouchListener();
        }
    };
    public final AnonymousClass7 mSubUICallback = new PluginSubScreen.Callback() { // from class: com.android.systemui.doze.PluginAODManager.7
        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getBouncerMessage();
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final Bundle getIncorrectBouncerMessage() {
            return PluginAODManager.this.mKeyguardViewController.getIncorrectBouncerMessage();
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
            ExifInterface$$ExternalSyntheticOutline0.m(sb, i != 301 ? LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "INVALID TYPE [", "]") : "SUB_ROOM_NOTIFICATION", "CoverScreenManager");
            return (SubRoom) coverScreenManager.mSubRoomMap.get(Integer.valueOf(i));
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final List getVisibleNotificationList() {
            SubScreenManager subScreenManager = PluginAODManager.this.mSubScreenManager;
            subScreenManager.getClass();
            final ArrayList arrayList = new ArrayList();
            subScreenManager.mNotifPipeline.getAllNotifs().stream().forEach(new Consumer() { // from class: com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((NotificationEntry) obj).mSbn);
                }
            });
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
            return pluginAODManager.mKeyguardUpdateMonitor.isDualDarInnerAuthRequired(pluginAODManager.mSelectedUserInteractor.getSelectedUserId(false));
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
            return PluginAODManager.this.mKeyguardUpdateMonitor.isUserUnlocked();
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
            ShadeViewController shadeViewController = pluginAODManager.mDozeServiceHost.mNotificationPanelViewController;
            if (shadeViewController != null) {
                shadeViewController.getKeyguardBottomAreaViewController().launchApp(componentName);
            }
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final void onSubScreenBouncerStateChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onSubScreenBouncerStateChanged() ", "PluginAODManager", z);
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
        public final void setDisplayStateOverrideWithDisplayId(int i, int i2) {
            PluginAODManager pluginAODManager = PluginAODManager.this;
            if (pluginAODManager.mDisplayManager == null) {
                pluginAODManager.mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
            try {
                Log.i("PluginAODManager", "updateDisplayStateLimit: displayState=" + i);
                IDisplayManager iDisplayManager = pluginAODManager.mDisplayManager;
                if (iDisplayManager == null) {
                    Log.e("PluginAODManager", "updateDisplayStateLimit : mDisplayManager is null!! ERROR case");
                    return;
                }
                iDisplayManager.setDisplayStateOverrideWithDisplayId(pluginAODManager.mDisplayStateLock, i, i2);
                if (i == 2) {
                    pluginAODManager.mPassiveModeToken = pluginAODManager.mDisplayManager.acquirePassiveModeToken(pluginAODManager.mToken, "PluginAODManager");
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
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setEnableDLS() ", "PluginAODManager", z);
            PluginAODManager.this.mPluginLockMediator.setPluginWallpaperState(1, z ? 1 : 0);
        }

        @Override // com.android.systemui.plugins.subscreen.PluginSubScreen.Callback
        public final boolean shouldControlScreenOff() {
            boolean z = PluginAODManager.this.mDozeParameters.mControlScreenOffAnimation;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("shouldControlScreenOff() : ", "PluginAODManager", z);
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
                if (!subScreenManager.mKeyguardUpdateMonitor.isUserUnlocked()) {
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

        /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
        
            r4 = r2.mAODPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
        
            if (r4 == null) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
        
            r4.getNotificationManager().removeNotification(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0047, code lost:
        
            r2 = r2.mCoverPlugin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0049, code lost:
        
            if (r2 == null) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x004b, code lost:
        
            r2.getNotificationManager().removeNotification(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0052, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
        
            return;
         */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onEntryRemoved(com.android.systemui.statusbar.notification.collection.NotificationEntry r3, int r4) {
            /*
                r2 = this;
                java.lang.String r4 = "PluginAODManager"
                java.lang.String r0 = "onEntryRemoved: "
                android.util.Log.d(r4, r0)
                com.android.systemui.doze.PluginAODManager r2 = com.android.systemui.doze.PluginAODManager.this
                java.lang.String r3 = r3.mKey
                java.util.ArrayList r4 = r2.mSmartAlerts
                java.util.Iterator r4 = r4.iterator()
            L11:
                boolean r0 = r4.hasNext()
                if (r0 == 0) goto L3b
                java.lang.Object r0 = r4.next()
                android.service.notification.StatusBarNotification r0 = (android.service.notification.StatusBarNotification) r0
                if (r0 == 0) goto L11
                java.lang.String r1 = r0.getKey()
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L11
                java.util.ArrayList r1 = r2.mSmartAlerts
                monitor-enter(r1)
                java.util.ArrayList r4 = r2.mSmartAlerts     // Catch: java.lang.Throwable -> L38
                r4.remove(r0)     // Catch: java.lang.Throwable -> L38
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L38
                java.lang.String r4 = "removeNotification"
                r2.logSmartAlert(r4)
                goto L3b
            L38:
                r2 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L38
                throw r2
            L3b:
                com.android.systemui.plugins.aod.PluginAOD r4 = r2.mAODPlugin
                if (r4 == 0) goto L47
                com.android.systemui.plugins.aod.PluginAODNotificationManager r2 = r4.getNotificationManager()
                r2.removeNotification(r3)
                goto L52
            L47:
                com.android.systemui.plugins.cover.PluginCover r2 = r2.mCoverPlugin
                if (r2 == 0) goto L52
                com.android.systemui.plugins.aod.PluginAODNotificationManager r2 = r2.getNotificationManager()
                r2.removeNotification(r3)
            L52:
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
                Iterator it = pluginAODManager.mSmartAlerts.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) it.next();
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
    public int mCurrentPhoneState = 0;
    public final IBinder mRefreshRateToken = new Binder();

    static {
        boolean z = DozeService.DEBUG;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.doze.PluginAODManager$9] */
    /* JADX WARN: Type inference failed for: r6v10, types: [com.android.systemui.doze.PluginAODManager$5] */
    /* JADX WARN: Type inference failed for: r6v11, types: [com.android.systemui.doze.PluginAODManager$6] */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.android.systemui.doze.PluginAODManager$7] */
    /* JADX WARN: Type inference failed for: r6v13, types: [com.android.systemui.doze.PluginAODManager$8] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.doze.PluginAODManager$2] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.doze.PluginAODManager$3] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.doze.PluginAODManager$4] */
    public PluginAODManager(Context context, SelectedUserInteractor selectedUserInteractor, LockscreenNotificationManager lockscreenNotificationManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, PluginFaceWidgetManager pluginFaceWidgetManager, SettingsHelper settingsHelper, DozeParameters dozeParameters, PluginLockMediator pluginLockMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardWallpaper keyguardWallpaper, DozeServiceHost dozeServiceHost, SubScreenManager subScreenManager, Lazy lazy, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, NotificationLockscreenUserManager notificationLockscreenUserManager, SamsungServiceLogger samsungServiceLogger, KeyguardFoldController keyguardFoldController, DumpManager dumpManager, PluginLockStarManager pluginLockStarManager, NotificationsController notificationsController, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, final SubScreenQuickPanelWindowController subScreenQuickPanelWindowController, KeyguardViewMediatorHelper keyguardViewMediatorHelper, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, ConfigurationController configurationController, AODTouchModeManager aODTouchModeManager, KeyguardWallpaperController keyguardWallpaperController) {
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
        this.mDozeServiceHost = dozeServiceHost;
        this.mWallpaperUtilsWrapper = faceWidgetWallpaperUtilsWrapper;
        this.mDisplayLifeCycle = displayLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mCommonNotifCollectionLazy = lazy2;
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
                    subScreenQuickPanelWindowController.mCommandQueue.addCallback((CommandQueue.Callbacks) subScreenQuickPanelWindowController);
                    subScreenQuickPanelWindowController.mDisplayLifecycle.addObserver(subScreenQuickPanelWindowController.mFoldStateChangedListener);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2415, -2138832832, -3);
                    subScreenQuickPanelWindowController.mLp = layoutParams;
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
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView = (SubScreenQuickPanelWindowView) LayoutInflater.from(subScreenQuickPanelWindowController.mContext).inflate(R.layout.subscreen_quickpanel_window_view, (ViewGroup) null);
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCorners(15, subScreenQuickPanelWindowController.mContext.getResources().getDimensionPixelSize(17105820));
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.semSetRoundedCornerColor(15, subScreenQuickPanelWindowController.mContext.getColor(android.R.color.black));
                        SubscreenQsPanelController subscreenQsPanelController = subScreenQuickPanelWindowController.mSubscreenQsPanelController;
                        if (subscreenQsPanelController.mSubRoomQuickSettings == null) {
                            subscreenQsPanelController.mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(subscreenQsPanelController.mContext, subscreenQsPanelController.mInjectionInflater);
                        }
                        View view = subscreenQsPanelController.mSubRoomQuickSettings.mMainView;
                        subScreenQuickPanelWindowController.mQSPanel = view;
                        subScreenQuickPanelWindowController.mSubScreenQsWindowView.addView(view);
                        SecCoverBlurController secCoverBlurController = new SecCoverBlurController(subScreenQuickPanelWindowController.mContext, subScreenQuickPanelWindowController.mQSPanel);
                        if (secCoverBlurController.mRootView == null) {
                            Log.w("SecCoverBlurController", "applySemWindowBlur: rootView is null");
                        } else {
                            QSColorCurve qSColorCurve = secCoverBlurController.mColorCurve;
                            qSColorCurve.setFraction(1.0f);
                            float f = 40;
                            secCoverBlurController.mRootView.semSetBlurInfo(new SemBlurInfo.Builder(0).setRadius((int) qSColorCurve.radius).setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY).setBackgroundCornerRadius(f, f, f, f).build());
                        }
                    }
                    subScreenQuickPanelWindowController.mWindowManager.addView(subScreenQuickPanelWindowController.mSubScreenQsWindowView, subScreenQuickPanelWindowController.mLp);
                    Context context2 = subScreenQuickPanelWindowController.mContext;
                    subScreenQuickPanelWindowController.mPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
                    subScreenQuickPanelWindowController.mMaxExpandedHeight = DeviceState.getScreenHeight(context2);
                    subScreenQSEventHandler.init();
                    SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = subScreenQuickPanelWindowController.mSubScreenQsWindowView;
                    subScreenQuickPanelWindowView.mSubScreenQSTouchHandler = subScreenQSEventHandler;
                    subScreenQuickPanelWindowView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.subscreen.SubScreenQuickPanelWindowController.2
                        public AnonymousClass2() {
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
                    subScreenQuickPanelWindowController.mSubScreenComponent.create(subScreenQuickPanelWindowController.mSubScreenQsWindowView).getSubScreenQuickPanelHeaderController().init();
                    subScreenManager.setSubRoom(300, new SubScreenQuickPanelWindowController.PanelExpandedFractionProvider(subScreenQuickPanelWindowController, i));
                }
            } else if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
                if (this.mSubscreenQsPanelController == null) {
                    this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
                }
                this.mSubscreenQsPanelController.init();
                SubscreenQsPanelController subscreenQsPanelController2 = this.mSubscreenQsPanelController;
                if (subscreenQsPanelController2.mSubRoomQuickSettings == null) {
                    subscreenQsPanelController2.mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(subscreenQsPanelController2.mContext, subscreenQsPanelController2.mInjectionInflater);
                }
                subScreenManager.setSubRoom(300, subscreenQsPanelController2.mSubRoomQuickSettings);
            }
        }
        this.mCoverScreenManagerLazy = lazy;
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
                DozeServiceHost dozeServiceHost2 = pluginAODManager.mDozeServiceHost;
                if (dozeServiceHost2.mCentralSurfaces == null) {
                    Log.i("DozeServiceHost", "animateExpandNotificationsPanel() called before initialize(), return");
                } else {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("animateExpandNotificationsPanel sbn="), statusBarNotification == null, "DozeServiceHost");
                    ((CentralSurfacesImpl) dozeServiceHost2.mCentralSurfaces).animateExpandNotificationsPanel(statusBarNotification, true);
                }
            }
        };
        this.mPluginLockStarManager = pluginLockStarManager;
        pluginLockStarManager.registerCallback("AOD", new PluginLockStarManager.LockStarCallback(this) { // from class: com.android.systemui.doze.PluginAODManager.10
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
            }
        });
        this.mNotificationsController = notificationsController;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "PluginAODManager", this);
        this.mFoldController = keyguardFoldController;
        if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.doze.PluginAODManager$$ExternalSyntheticLambda0
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    PluginAODManager pluginAODManager = PluginAODManager.this;
                    pluginAODManager.getClass();
                    Log.i("PluginAODManager", "onFolderStateChanged isOpened : " + z);
                    pluginAODManager.mIsFolderOpened = z;
                    if (LsRune.AOD_SUB_DISPLAY_COVER) {
                        pluginAODManager.updateAnimateScreenOff();
                    }
                    if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT && !z) {
                        pluginAODManager.setStartedByFolderClosed(true);
                    }
                    PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                    if (pluginAOD != null) {
                        pluginAOD.onFolderStateChanged(z);
                    }
                }
            }, 4, true);
        }
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mAODTouchModeManager = aODTouchModeManager;
        this.mKeyguardWallpaperController = keyguardWallpaperController;
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
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("        supportAODLightReveal : "), LsRune.AOD_LIGHT_REVEAL, printWriter);
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
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
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
        Iterator it = this.mSmartAlerts.iterator();
        while (it.hasNext()) {
            StatusBarNotification statusBarNotification = (StatusBarNotification) it.next();
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
        if ((keyguardUpdateMonitor.getCoverState() != null && !keyguardUpdateMonitor.getCoverState().switchState) || keyguardUpdateMonitor.isBouncerFullyShown() || keyguardUpdateMonitor.isScreenOffMemoRunning() || this.mStartedByFolderClosed || this.mCurrentPhoneState != 0) {
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
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onTransitionEnded mClockTransitionStarted:"), this.mClockTransitionStarted, " shouldControlScreenOff : ", z, "PluginAODManager");
        if (this.mClockTransitionStarted) {
            this.mAODMachine.requestState(DozeMachine.State.DOZE_TRANSITION_ENDED);
            this.mAODTouchModeManager.setTouchMode(0);
            this.mClockTransitionStarted = false;
            if (z) {
                this.mPluginLockMediator.onAodTransitionEnd();
                this.mKeyguardWallpaper.getClass();
            }
        }
    }

    public final void registerUpdateMonitor$1() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mWakefulness = wakefulnessLifecycle.mWakefulness;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mWakefulness, "PluginAODManager", new StringBuilder("registerUpdateMonitor mWakefulness="));
    }

    public final void removeUpdateMonitor() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        wakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
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
            ((StatusBarStateControllerImpl) sysuiStatusBarStateController).removeCallback((StatusBarStateController.StateListener) anonymousClass5);
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
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        statusBarStateControllerImpl.addCallback((StatusBarStateController.StateListener) anonymousClass5);
        PluginAOD pluginAOD2 = this.mAODPlugin;
        float f = statusBarStateControllerImpl.mDozeAmount;
        pluginAOD2.onDozeAmountChanged(f, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(f));
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
                ((StatusBarStateControllerImpl) sysuiStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this.mStateListener);
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
            this.mCoverPlugin.onDozeAmountChanged(((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
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
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("setStartedByFolderClosed: ", "PluginAODManager", z);
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
                this.mSubScreenPlugin.onDozeAmountChanged(((StatusBarStateControllerImpl) this.mStatusBarStateController).mDozeAmount);
            }
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
            m.append(((BatteryControllerImpl) this.mDozeServiceHost.mBatteryController).mAodPowerSave);
            m.append(" mIsFolderOpened=");
            m.append(this.mIsFolderOpened);
            m.append(" needControlScreenOff=");
            m.append(needControlScreenOff());
            m.append(" called=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(4, "PluginAODManager", m);
        }
        dozeParameters.setControlScreenOffAnimation(z2);
    }

    public final void updateRefreshRate(int i) {
        if (LsRune.AOD_FULLSCREEN) {
            boolean z = false;
            if (!LsRune.AOD_DOZE_AP_SLEEP ? i == 4 : i == 3) {
                z = true;
            }
            Log.i("PluginAODManager", "updateRefreshRate: displayState=" + i + " dozeSuspend=" + z);
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
