package com.android.systemui.statusbar.phone;

import android.app.Flags;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.InitController;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.CommandQueue;
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
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationAlertsInteractor;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionCondition;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionRefactor;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionType;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.AnonymousClass18;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarNotificationPresenter implements NotificationPresenter, CommandQueue.Callbacks {
    public final AboveShelfObserver mAboveShelfObserver;
    public final ActivityStarter mActivityStarter;
    public final AnonymousClass4 mAlertsDisabledCondition;
    public final IStatusBarService mBarService;
    public final CommandQueue mCommandQueue;
    public final DozeScrimController mDozeScrimController;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManager mHeadsUpManager;
    public final AnonymousClass3 mInterruptSuppressor;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationMediaManager mMediaManager;
    public final AnonymousClass6 mNeedsRedactionFilter;
    public final NotificationListContainer mNotifListContainer;
    public final NotifShadeEventSource mNotifShadeEventSource;
    public final NotificationAlertsInteractor mNotificationAlertsInteractor;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNsslController;
    public final AnonymousClass2 mOnSettingsClickListener;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final AnonymousClass7 mPanelsDisabledCondition;
    public final PowerInteractor mPowerInteractor;
    public final QuickSettingsController mQsController;
    public final LockscreenShadeTransitionController mShadeTransitionController;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mVrMode;
    public final AnonymousClass5 mVrModeCondition;
    public final AnonymousClass1 mVrStateCallbacks;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void onSettingsClick(String str) {
            try {
                StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(str);
            } catch (RemoteException unused) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$3, reason: invalid class name */
    public final class AnonymousClass3 implements NotificationInterruptSuppressor {
        public AnonymousClass3() {
        }
    }

    public StatusBarNotificationPresenter(Context context, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, QuickSettingsController quickSettingsController, HeadsUpManager headsUpManager, NotificationShadeWindowView notificationShadeWindowView, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, NotificationAlertsInteractor notificationAlertsInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, PowerInteractor powerInteractor, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, InitController initController, final VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, final NotificationRemoteInputManager notificationRemoteInputManager, NotificationRemoteInputManager.Callback callback, NotificationListContainer notificationListContainer) {
        IVrStateCallbacks.Stub stub = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.1
            public final void onVrStateChanged(boolean z) {
                StatusBarNotificationPresenter.this.mVrMode = z;
            }
        };
        this.mOnSettingsClickListener = new AnonymousClass2();
        this.mInterruptSuppressor = new AnonymousClass3();
        VisualInterruptionType visualInterruptionType = VisualInterruptionType.PEEK;
        VisualInterruptionType visualInterruptionType2 = VisualInterruptionType.PULSE;
        VisualInterruptionType visualInterruptionType3 = VisualInterruptionType.BUBBLE;
        new VisualInterruptionCondition(this, Set.of(visualInterruptionType, visualInterruptionType2, visualInterruptionType3), "notification alerts disabled") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.4
        };
        new VisualInterruptionCondition(this, Set.of(visualInterruptionType, visualInterruptionType3), "device is in VR mode") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.5
        };
        new VisualInterruptionFilter(this, Set.of(visualInterruptionType), "needs redaction on public lockscreen") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.6
        };
        new VisualInterruptionCondition(this, Set.of(visualInterruptionType), "disabled panel") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.7
        };
        this.mActivityStarter = activityStarter;
        this.mKeyguardStateController = keyguardStateController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mQsController = quickSettingsController;
        this.mHeadsUpManager = headsUpManager;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mNotificationAlertsInteractor = notificationAlertsInteractor;
        this.mNsslController = notificationStackScrollLayoutController;
        this.mShadeTransitionController = lockscreenShadeTransitionController;
        this.mPowerInteractor = powerInteractor;
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
                asInterface.registerListener(stub);
            } catch (RemoteException e) {
                Slog.e("StatusBarNotificationPresenter", "Failed to register VR mode state listener: " + e);
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNsslController;
        notificationStackScrollLayoutController2.getClass();
        NotificationStackScrollLayoutController.AnonymousClass18 anonymousClass18 = notificationStackScrollLayoutController2.new AnonymousClass18();
        notificationRemoteInputManager.mCallback = callback;
        RemoteInputController remoteInputController = new RemoteInputController(anonymousClass18, notificationRemoteInputManager.mRemoteInputUriController, notificationRemoteInputManager.mRemoteInputControllerLogger);
        notificationRemoteInputManager.mRemoteInputController = remoteInputController;
        RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener;
        if (remoteInputCoordinator != null) {
            remoteInputCoordinator.setRemoteInputController(remoteInputController);
        }
        Iterator it = ((ArrayList) notificationRemoteInputManager.mControllerCallbacks).iterator();
        while (it.hasNext()) {
            RemoteInputController.Callback callback2 = (RemoteInputController.Callback) it.next();
            RemoteInputController remoteInputController2 = notificationRemoteInputManager.mRemoteInputController;
            remoteInputController2.getClass();
            Objects.requireNonNull(callback2);
            remoteInputController2.mCallbacks.add(callback2);
        }
        ((ArrayList) notificationRemoteInputManager.mControllerCallbacks).clear();
        RemoteInputController remoteInputController3 = notificationRemoteInputManager.mRemoteInputController;
        RemoteInputController.Callback anonymousClass2 = new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager.2
            public AnonymousClass2() {
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Callback
            public final void onRemoteInputSent(NotificationEntry notificationEntry) {
                NotificationRemoteInputManager notificationRemoteInputManager2 = NotificationRemoteInputManager.this;
                RemoteInputCoordinator remoteInputCoordinator2 = notificationRemoteInputManager2.mRemoteInputListener;
                if (remoteInputCoordinator2 != null) {
                    remoteInputCoordinator2.onRemoteInputSent(notificationEntry);
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
        remoteInputController3.getClass();
        remoteInputController3.mCallbacks.add(anonymousClass2);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final StatusBarNotificationPresenter statusBarNotificationPresenter = StatusBarNotificationPresenter.this;
                VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2 = visualInterruptionDecisionProvider;
                statusBarNotificationPresenter.getClass();
                final int i = 0;
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                        switch (i2) {
                            case 0:
                                if (!statusBarNotificationPresenter2.mPanelExpansionInteractor.isTracking() && !statusBarNotificationPresenter2.mQsController.getExpanded$1()) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (((StatusBarStateControllerImpl) sysuiStatusBarStateController2).mState == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !((BaseHeadsUpManager) statusBarNotificationPresenter2.mHeadsUpManager).hasNotifications()) {
                                    statusBarNotificationPresenter2.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                };
                NotifShadeEventSource notifShadeEventSource2 = statusBarNotificationPresenter.mNotifShadeEventSource;
                notifShadeEventSource2.setShadeEmptiedCallback(runnable2);
                final int i2 = 1;
                notifShadeEventSource2.setNotifRemovedByUserCallback(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                        switch (i22) {
                            case 0:
                                if (!statusBarNotificationPresenter2.mPanelExpansionInteractor.isTracking() && !statusBarNotificationPresenter2.mQsController.getExpanded$1()) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (((StatusBarStateControllerImpl) sysuiStatusBarStateController2).mState == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !((BaseHeadsUpManager) statusBarNotificationPresenter2.mHeadsUpManager).hasNotifications()) {
                                    statusBarNotificationPresenter2.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                });
                int i3 = VisualInterruptionRefactor.$r8$clinit;
                visualInterruptionDecisionProvider2.addLegacySuppressor(statusBarNotificationPresenter.mInterruptSuppressor);
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) statusBarNotificationPresenter.mLockscreenUserManager;
                notificationLockscreenUserManagerImpl.mPresenter = statusBarNotificationPresenter;
                if (!Flags.keyguardPrivateNotifications()) {
                    notificationLockscreenUserManagerImpl.init$11$1();
                }
                NotificationGutsManager notificationGutsManager2 = statusBarNotificationPresenter.mGutsManager;
                notificationGutsManager2.mPresenter = statusBarNotificationPresenter;
                notificationGutsManager2.mListContainer = statusBarNotificationPresenter.mNotifListContainer;
                notificationGutsManager2.mOnSettingsClickListener = statusBarNotificationPresenter.mOnSettingsClickListener;
                ((BaseHeadsUpManager) statusBarNotificationPresenter.mHeadsUpManager).mUser = notificationLockscreenUserManagerImpl.mCurrentUserId;
                statusBarNotificationPresenter.mCommandQueue.animateCollapsePanels();
                statusBarNotificationPresenter.mMediaManager.clearCurrentMediaNotification();
            }
        };
        if (initController.mTasksExecuted) {
            throw new IllegalStateException("post init tasks have already been executed!");
        }
        initController.mTasks.add(runnable);
    }

    public final boolean isCollapsing() {
        return this.mPanelExpansionInteractor.isCollapsing() || ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.launchingActivityFromNotification;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00aa, code lost:
    
        if (r7.isDynamicallyUnlocked() == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b3, code lost:
    
        r9.mLeaveOpenOnKeyguardHide = true;
        r2.dismissKeyguardThenExecute(new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3(), null, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b1, code lost:
    
        if (r7.isInLockedDownShade() != false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onExpandClicked(com.android.systemui.statusbar.notification.collection.NotificationEntry r8, boolean r9) {
        /*
            r7 = this;
            com.android.systemui.statusbar.policy.HeadsUpManager r0 = r7.mHeadsUpManager
            com.android.systemui.statusbar.policy.BaseHeadsUpManager r0 = (com.android.systemui.statusbar.policy.BaseHeadsUpManager) r0
            r0.getClass()
            java.lang.String r1 = r8.mKey
            com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry r0 = r0.getHeadsUpEntry(r1)
            if (r0 == 0) goto L18
            boolean r1 = r8.isRowPinned()
            if (r1 == 0) goto L18
            r0.setExpanded(r9)
        L18:
            r0 = 4
            com.android.systemui.power.domain.interactor.PowerInteractor r1 = r7.mPowerInteractor
            java.lang.String r2 = "NOTIFICATION_CLICK"
            r1.wakeUpIfDozing(r0, r2)
            if (r9 == 0) goto Ldf
            com.android.systemui.statusbar.SysuiStatusBarStateController r9 = r7.mStatusBarStateController
            com.android.systemui.statusbar.StatusBarStateControllerImpl r9 = (com.android.systemui.statusbar.StatusBarStateControllerImpl) r9
            int r0 = r9.mState
            r1 = 1
            com.android.systemui.plugins.ActivityStarter r2 = r7.mActivityStarter
            r3 = 0
            r4 = 0
            kotlinx.coroutines.flow.StateFlowImpl r5 = r8.mSensitive
            if (r0 != r1) goto L54
            java.lang.Object r0 = r5.getValue()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L49
            r9.mLeaveOpenOnKeyguardHide = r1
            com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3 r7 = new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3
            r7.<init>()
            r2.dismissKeyguardThenExecute(r7, r4, r3)
            goto Lbd
        L49:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r9 = r8.row
            com.android.systemui.statusbar.LockscreenShadeTransitionController r7 = r7.mShadeTransitionController
            r7.getClass()
            r7.goToLockedShade(r9, r1)
            goto Lbd
        L54:
            java.lang.Object r0 = r5.getValue()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            r6 = 2
            if (r0 == 0) goto L70
            int r0 = r9.mState
            if (r0 != r6) goto L70
            r9.mLeaveOpenOnKeyguardHide = r1
            com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3 r7 = new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3
            r7.<init>()
            r2.dismissKeyguardThenExecute(r7, r4, r3)
            goto Lbd
        L70:
            java.lang.Object r0 = r5.getValue()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto Lbd
            boolean r0 = com.android.systemui.NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE
            com.android.systemui.statusbar.notification.DynamicPrivacyController r7 = r7.mDynamicPrivacyController
            if (r0 == 0) goto Lad
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r7.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0
            boolean r5 = r0.mShowing
            if (r5 == 0) goto Lbd
            boolean r0 = r0.mSecure
            if (r0 != 0) goto L8f
            goto Lbd
        L8f:
            com.android.systemui.plugins.statusbar.StatusBarStateController r0 = r7.mStateController
            int r0 = r0.getState()
            if (r0 == 0) goto L9a
            if (r0 == r6) goto L9a
            goto Lbd
        L9a:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r7.mLockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            int r5 = r0.mCurrentUserId
            boolean r0 = r0.userAllowsNotificationsInPublic(r5)
            if (r0 == 0) goto Lbd
            boolean r7 = r7.isDynamicallyUnlocked()
            if (r7 == 0) goto Lb3
            goto Lbd
        Lad:
            boolean r7 = r7.isInLockedDownShade()
            if (r7 == 0) goto Lbd
        Lb3:
            r9.mLeaveOpenOnKeyguardHide = r1
            com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3 r7 = new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3
            r7.<init>()
            r2.dismissKeyguardThenExecute(r7, r4, r3)
        Lbd:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r8.row
            if (r7 == 0) goto Lc9
            boolean r7 = r7.mIsSummaryWithChildren
            if (r7 == 0) goto Lc9
            java.lang.String r7 = "grouped"
        Lc7:
            r3 = r7
            goto Lcd
        Lc9:
            java.lang.String r7 = "single"
            goto Lc7
        Lcd:
            android.service.notification.StatusBarNotification r7 = r8.mSbn
            java.lang.String r5 = r7.getPackageName()
            java.lang.String r2 = "type"
            java.lang.String r4 = "app"
            java.lang.String r0 = "QPN001"
            java.lang.String r1 = "QPNE0008"
            com.android.systemui.util.SystemUIAnalytics.sendEventCDLog(r0, r1, r2, r3, r4, r5)
        Ldf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.onExpandClicked(com.android.systemui.statusbar.notification.collection.NotificationEntry, boolean):void");
    }
}
