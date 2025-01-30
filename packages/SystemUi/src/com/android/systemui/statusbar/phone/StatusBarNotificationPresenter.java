package com.android.systemui.statusbar.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.session.MediaController;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.InitController;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$setRemoteInputController$1;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C295815;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarNotificationPresenter implements NotificationPresenter, NotificationRowBinderImpl.BindRowCallback, CommandQueue.Callbacks {
    public final AboveShelfObserver mAboveShelfObserver;
    public final ActivityStarter mActivityStarter;
    public final IStatusBarService mBarService;
    public final CentralSurfaces mCentralSurfaces;
    public final CommandQueue mCommandQueue;
    public final DozeScrimController mDozeScrimController;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public final C31584 mInterruptSuppressor;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationMediaManager mMediaManager;
    public final NotificationListContainer mNotifListContainer;
    public final NotifShadeEventSource mNotifShadeEventSource;
    public final ShadeViewController mNotificationPanel;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final C31573 mOnSettingsClickListener;
    public final QuickSettingsController mQsController;
    public final LockscreenShadeTransitionController mShadeTransitionController;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mVrMode;
    public final C31551 mVrStateCallbacks;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$3 */
    public final class C31573 {
        public C31573() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$4 */
    public final class C31584 {
        public C31584() {
        }
    }

    public StatusBarNotificationPresenter(Context context, ShadeViewController shadeViewController, QuickSettingsController quickSettingsController, HeadsUpManagerPhone headsUpManagerPhone, NotificationShadeWindowView notificationShadeWindowView, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, KeyguardIndicationController keyguardIndicationController, CentralSurfaces centralSurfaces, LockscreenShadeTransitionController lockscreenShadeTransitionController, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, LockscreenGestureLogger lockscreenGestureLogger, InitController initController, final NotificationInterruptStateProvider notificationInterruptStateProvider, final NotificationRemoteInputManager notificationRemoteInputManager, NotifPipelineFlags notifPipelineFlags, NotificationRemoteInputManager.Callback callback, NotificationListContainer notificationListContainer) {
        IVrStateCallbacks iVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.1
            public final void onVrStateChanged(boolean z) {
                StatusBarNotificationPresenter.this.mVrMode = z;
            }
        };
        this.mVrStateCallbacks = iVrStateCallbacks;
        new Object(this) { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.2
        };
        this.mOnSettingsClickListener = new C31573();
        this.mInterruptSuppressor = new C31584();
        this.mActivityStarter = activityStarter;
        this.mKeyguardStateController = keyguardStateController;
        this.mNotificationPanel = shadeViewController;
        this.mQsController = quickSettingsController;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mCentralSurfaces = centralSurfaces;
        this.mShadeTransitionController = lockscreenShadeTransitionController;
        this.mCommandQueue = commandQueue;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mNotifShadeEventSource = notifShadeEventSource;
        this.mMediaManager = notificationMediaManager;
        this.mGutsManager = notificationGutsManager;
        AboveShelfObserver aboveShelfObserver = new AboveShelfObserver(notificationStackScrollLayoutController.mView);
        this.mAboveShelfObserver = aboveShelfObserver;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        aboveShelfObserver.mListener = (AboveShelfObserver.HasViewAboveShelfChangedListener) notificationShadeWindowView.findViewById(R.id.notification_container_parent);
        this.mDozeScrimController = dozeScrimController;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mNotifListContainer = notificationListContainer;
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(iVrStateCallbacks);
            } catch (RemoteException e) {
                Slog.e("StatusBarNotificationPresenter", "Failed to register VR mode state listener: " + e);
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController2.getClass();
        NotificationStackScrollLayoutController.C295815 c295815 = notificationStackScrollLayoutController2.new C295815();
        notificationRemoteInputManager.mCallback = callback;
        notificationRemoteInputManager.mRemoteInputController = new RemoteInputController(c295815, notificationRemoteInputManager.mRemoteInputUriController, notificationRemoteInputManager.mRemoteInputControllerLogger);
        RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener;
        if (remoteInputCoordinator != null) {
            remoteInputCoordinator.mSmartReplyController.mCallback = new RemoteInputCoordinator$setRemoteInputController$1(remoteInputCoordinator);
        }
        ArrayList arrayList = (ArrayList) notificationRemoteInputManager.mControllerCallbacks;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            RemoteInputController.Callback callback2 = (RemoteInputController.Callback) it.next();
            RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
            remoteInputController.getClass();
            Objects.requireNonNull(callback2);
            remoteInputController.mCallbacks.add(callback2);
        }
        arrayList.clear();
        RemoteInputController remoteInputController2 = notificationRemoteInputManager.mRemoteInputController;
        RemoteInputController.Callback c25912 = new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager.2
            public C25912() {
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Callback
            public final void onRemoteInputSent(NotificationEntry notificationEntry) {
                boolean booleanValue;
                NotificationRemoteInputManager notificationRemoteInputManager2 = NotificationRemoteInputManager.this;
                RemoteInputCoordinator remoteInputCoordinator2 = notificationRemoteInputManager2.mRemoteInputListener;
                if (remoteInputCoordinator2 != null) {
                    remoteInputCoordinator2.getClass();
                    booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                    String str = notificationEntry.mKey;
                    if (booleanValue) {
                        AbstractC0689x6838b71d.m68m("onRemoteInputSent(entry=", str, ")", "RemoteInputCoordinator");
                    }
                    remoteInputCoordinator2.mRemoteInputHistoryExtender.endLifetimeExtension(str);
                    remoteInputCoordinator2.mSmartReplyHistoryExtender.endLifetimeExtension(str);
                    remoteInputCoordinator2.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 500L);
                }
                try {
                    notificationRemoteInputManager2.mBarService.onNotificationDirectReplied(notificationEntry.mSbn.getKey());
                    NotificationEntry.EditedSuggestionInfo editedSuggestionInfo = notificationEntry.editedSuggestionInfo;
                    if (editedSuggestionInfo != null) {
                        boolean z = !TextUtils.equals(notificationEntry.remoteInputText, editedSuggestionInfo.originalText);
                        IStatusBarService iStatusBarService = notificationRemoteInputManager2.mBarService;
                        String key = notificationEntry.mSbn.getKey();
                        NotificationEntry.EditedSuggestionInfo editedSuggestionInfo2 = notificationEntry.editedSuggestionInfo;
                        iStatusBarService.onNotificationSmartReplySent(key, editedSuggestionInfo2.index, editedSuggestionInfo2.originalText, NotificationLogger.getNotificationLocation(notificationEntry).toMetricsEventEnum(), z);
                    }
                } catch (RemoteException unused) {
                }
            }
        };
        remoteInputController2.getClass();
        remoteInputController2.mCallbacks.add(c25912);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final StatusBarNotificationPresenter statusBarNotificationPresenter = StatusBarNotificationPresenter.this;
                NotificationInterruptStateProvider notificationInterruptStateProvider2 = notificationInterruptStateProvider;
                statusBarNotificationPresenter.mKeyguardIndicationController.init();
                final int i = 0;
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                                if (!((NotificationPanelViewController) statusBarNotificationPresenter2.mNotificationPanel).mTracking && !statusBarNotificationPresenter2.mQsController.mExpanded) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (((StatusBarStateControllerImpl) sysuiStatusBarStateController2).mState == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState$1(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                StatusBarNotificationPresenter statusBarNotificationPresenter3 = statusBarNotificationPresenter;
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !(!statusBarNotificationPresenter3.mHeadsUpManager.mAlertEntries.isEmpty())) {
                                    statusBarNotificationPresenter3.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                };
                ShadeEventCoordinator shadeEventCoordinator = (ShadeEventCoordinator) statusBarNotificationPresenter.mNotifShadeEventSource;
                final int i2 = 1;
                if (!(shadeEventCoordinator.mShadeEmptiedCallback == null)) {
                    throw new IllegalStateException("mShadeEmptiedCallback already set".toString());
                }
                shadeEventCoordinator.mShadeEmptiedCallback = runnable2;
                Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                                if (!((NotificationPanelViewController) statusBarNotificationPresenter2.mNotificationPanel).mTracking && !statusBarNotificationPresenter2.mQsController.mExpanded) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (((StatusBarStateControllerImpl) sysuiStatusBarStateController2).mState == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState$1(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                StatusBarNotificationPresenter statusBarNotificationPresenter3 = statusBarNotificationPresenter;
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !(!statusBarNotificationPresenter3.mHeadsUpManager.mAlertEntries.isEmpty())) {
                                    statusBarNotificationPresenter3.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                };
                if (!(shadeEventCoordinator.mNotifRemovedByUserCallback == null)) {
                    throw new IllegalStateException("mNotifRemovedByUserCallback already set".toString());
                }
                shadeEventCoordinator.mNotifRemovedByUserCallback = runnable3;
                ((ArrayList) ((NotificationInterruptStateProviderImpl) notificationInterruptStateProvider2).mSuppressors).add(statusBarNotificationPresenter.mInterruptSuppressor);
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) statusBarNotificationPresenter.mLockscreenUserManager;
                notificationLockscreenUserManagerImpl.mPresenter = statusBarNotificationPresenter;
                Handler handler = notificationLockscreenUserManagerImpl.mMainHandler;
                notificationLockscreenUserManagerImpl.mLockscreenSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.4
                    public C25844(Handler handler2) {
                        super(handler2);
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        NotificationLockscreenUserManagerImpl.this.mUsersAllowingPrivateNotifications.clear();
                        NotificationLockscreenUserManagerImpl.this.mUsersAllowingNotifications.clear();
                        NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                        Iterator it2 = NotificationLockscreenUserManagerImpl.this.mNotifStateChangedListeners.iterator();
                        while (it2.hasNext()) {
                            ((NotificationLockscreenUserManager.NotificationStateChangedListener) it2.next()).onNotificationStateChanged();
                        }
                    }
                };
                notificationLockscreenUserManagerImpl.mSettingsObserver = new ContentObserver(handler2) { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.5
                    public C25855(Handler handler2) {
                        super(handler2);
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                        ((DeviceProvisionedControllerImpl) NotificationLockscreenUserManagerImpl.this.mDeviceProvisionedController).isDeviceProvisioned();
                    }
                };
                Context context2 = notificationLockscreenUserManagerImpl.mContext;
                ContentResolver contentResolver = context2.getContentResolver();
                ((SecureSettingsImpl) notificationLockscreenUserManagerImpl.mSecureSettings).getClass();
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_notifications"), false, notificationLockscreenUserManagerImpl.mLockscreenSettingsObserver, -1);
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_allow_private_notifications"), true, notificationLockscreenUserManagerImpl.mLockscreenSettingsObserver, -1);
                context2.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, notificationLockscreenUserManagerImpl.mSettingsObserver);
                IntentFilter intentFilter = new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
                UserHandle userHandle = UserHandle.ALL;
                BroadcastDispatcher broadcastDispatcher = notificationLockscreenUserManagerImpl.mBroadcastDispatcher;
                broadcastDispatcher.registerReceiver(notificationLockscreenUserManagerImpl.mAllUsersReceiver, intentFilter, null, userHandle);
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.USER_ADDED");
                intentFilter2.addAction("android.intent.action.USER_REMOVED");
                intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
                NotificationLockscreenUserManagerImpl.C25822 c25822 = notificationLockscreenUserManagerImpl.mBaseBroadcastReceiver;
                broadcastDispatcher.registerReceiver(c25822, intentFilter2, null, UserHandle.ALL);
                IntentFilter intentFilter3 = new IntentFilter();
                intentFilter3.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
                notificationLockscreenUserManagerImpl.mContext.registerReceiver(c25822, intentFilter3, "com.android.systemui.permission.SELF", null, 2);
                Executor handlerExecutor = new HandlerExecutor(handler2);
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) notificationLockscreenUserManagerImpl.mUserTracker;
                userTrackerImpl.addCallback(notificationLockscreenUserManagerImpl.mUserChangedCallback, handlerExecutor);
                notificationLockscreenUserManagerImpl.mCurrentUserId = userTrackerImpl.getUserId();
                notificationLockscreenUserManagerImpl.updateCurrentProfilesCache();
                notificationLockscreenUserManagerImpl.mSettingsObserver.onChange(false);
                notificationLockscreenUserManagerImpl.mLockscreenSettingsObserver.onChange(false);
                NotificationGutsManager notificationGutsManager2 = statusBarNotificationPresenter.mGutsManager;
                notificationGutsManager2.mPresenter = statusBarNotificationPresenter;
                notificationGutsManager2.mListContainer = statusBarNotificationPresenter.mNotifListContainer;
                notificationGutsManager2.mOnSettingsClickListener = statusBarNotificationPresenter.mOnSettingsClickListener;
                statusBarNotificationPresenter.onUserSwitched(notificationLockscreenUserManagerImpl.mCurrentUserId);
            }
        };
        if (initController.mTasksExecuted) {
            throw new IllegalStateException("post init tasks have already been executed!");
        }
        initController.mTasks.add(runnable);
    }

    public final boolean isCollapsing() {
        return ((NotificationPanelViewController) this.mNotificationPanel).isCollapsing() || ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.launchingActivityFromNotification;
    }

    public final boolean isPresenterFullyCollapsed() {
        return ((NotificationPanelViewController) this.mNotificationPanel).isFullyCollapsed();
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0093, code lost:
    
        if (r13 == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009c, code lost:
    
        r15.mLeaveOpenOnKeyguardHide = true;
        r4.dismissKeyguardThenExecute(new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda2(r6), null, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009a, code lost:
    
        if (r13.isInLockedDownShade() != false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onExpandClicked(NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        int state;
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        headsUpManagerPhone.getClass();
        HeadsUpManager.HeadsUpEntry headsUpEntry = headsUpManagerPhone.getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry != null && notificationEntry.isRowPinned()) {
            headsUpEntry.setExpanded(z);
        }
        ((CentralSurfacesImpl) this.mCentralSurfaces).wakeUpIfDozing(SystemClock.uptimeMillis(), "NOTIFICATION_CLICK", 4);
        if (z) {
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
            int i = statusBarStateControllerImpl.mState;
            final int i2 = 0;
            ActivityStarter activityStarter = this.mActivityStarter;
            if (i != 1) {
                boolean z3 = notificationEntry.mSensitive;
                final int i3 = 2;
                if (z3 && i == 2) {
                    statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = true;
                    activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction(r1) { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda2
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            return false;
                        }
                    }, null, false);
                } else if (z3) {
                    boolean z4 = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE;
                    DynamicPrivacyController dynamicPrivacyController = this.mDynamicPrivacyController;
                    if (z4) {
                        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) dynamicPrivacyController.mKeyguardStateController;
                        if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mSecure && ((state = dynamicPrivacyController.mStateController.getState()) == 0 || state == 2)) {
                            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) dynamicPrivacyController.mLockscreenUserManager;
                            if (notificationLockscreenUserManagerImpl.userAllowsNotificationsInPublic(notificationLockscreenUserManagerImpl.mCurrentUserId) && !dynamicPrivacyController.isDynamicallyUnlocked()) {
                                z2 = true;
                            }
                        }
                        z2 = false;
                    }
                }
            } else if (notificationEntry.mSensitive) {
                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = true;
                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction(i2) { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda2
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        return false;
                    }
                }, null, false);
            } else {
                this.mShadeTransitionController.goToLockedShade(notificationEntry.row, true);
            }
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0008", "type", ((expandableNotificationRow == null || !expandableNotificationRow.mIsSummaryWithChildren) ? 0 : 1) != 0 ? "grouped" : "single", "app", notificationEntry.mSbn.getPackageName());
        }
    }

    public final void onUserSwitched(int i) {
        this.mHeadsUpManager.mUser = i;
        this.mCommandQueue.animateCollapsePanels();
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        notificationMediaManager.mMediaNotificationKey = null;
        notificationMediaManager.mMediaArtworkProcessor.getClass();
        notificationMediaManager.mMediaMetadata = null;
        MediaController mediaController = notificationMediaManager.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
        }
        notificationMediaManager.mMediaController = null;
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        if (centralSurfacesImpl.mLockscreenWallpaper != null && !centralSurfacesImpl.mWallpaperManager.isLockscreenLiveWallpaperEnabled()) {
            LockscreenWallpaper lockscreenWallpaper = centralSurfacesImpl.mLockscreenWallpaper;
            lockscreenWallpaper.assertLockscreenLiveWallpaperNotEnabled();
            if (i != lockscreenWallpaper.mCurrentUserId) {
                lockscreenWallpaper.mCached = false;
                lockscreenWallpaper.mCurrentUserId = i;
            }
        }
        centralSurfacesImpl.mScrimController.getClass();
        if (centralSurfacesImpl.mWallpaperSupported) {
            centralSurfacesImpl.mWallpaperChangedReceiver.onReceive(centralSurfacesImpl.mContext, null);
        }
        notificationMediaManager.updateMediaMetaData(true, false);
    }
}
