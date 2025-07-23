package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
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
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda5;
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
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationAlertsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
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
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarNotificationPresenter implements NotificationPresenter, CommandQueue.Callbacks {
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
    public final KeyguardManager mKeyguardManager;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void onSettingsClick(String str) {
            try {
                StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(str);
            } catch (RemoteException unused) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$3, reason: invalid class name */
    public class AnonymousClass3 implements NotificationInterruptSuppressor {
        public AnonymousClass3() {
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$4] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$5] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$6] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$7] */
    public StatusBarNotificationPresenter(Context context, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, QuickSettingsController quickSettingsController, HeadsUpManager headsUpManager, NotificationShadeWindowView notificationShadeWindowView, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, NotificationAlertsInteractor notificationAlertsInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, PowerInteractor powerInteractor, CommandQueue commandQueue, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, InitController initController, final VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, final NotificationRemoteInputManager notificationRemoteInputManager, NotificationRemoteInputManager.Callback callback, NotificationListContainer notificationListContainer, DeviceUnlockedInteractor deviceUnlockedInteractor) {
        IVrStateCallbacks iVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.1
            public final void onVrStateChanged(boolean z) {
                StatusBarNotificationPresenter.this.mVrMode = z;
            }
        };
        this.mVrStateCallbacks = iVrStateCallbacks;
        this.mOnSettingsClickListener = new AnonymousClass2();
        this.mInterruptSuppressor = new AnonymousClass3();
        VisualInterruptionType visualInterruptionType = VisualInterruptionType.PEEK;
        VisualInterruptionType visualInterruptionType2 = VisualInterruptionType.PULSE;
        VisualInterruptionType visualInterruptionType3 = VisualInterruptionType.BUBBLE;
        this.mAlertsDisabledCondition = new VisualInterruptionCondition(this, Set.of(visualInterruptionType, visualInterruptionType2, visualInterruptionType3), "notification alerts disabled") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.4
        };
        this.mVrModeCondition = new VisualInterruptionCondition(this, Set.of(visualInterruptionType, visualInterruptionType3), "device is in VR mode") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.5
        };
        this.mNeedsRedactionFilter = new VisualInterruptionFilter(this, Set.of(visualInterruptionType), "needs redaction on public lockscreen") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.6
        };
        this.mPanelsDisabledCondition = new VisualInterruptionCondition(this, Set.of(visualInterruptionType), "disabled panel") { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.7
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
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
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
        ArrayList arrayList = (ArrayList) notificationRemoteInputManager.mControllerCallbacks;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            RemoteInputController.Callback callback2 = (RemoteInputController.Callback) obj;
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
                final int i2 = 0;
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3 = i2;
                        StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                        switch (i3) {
                            case 0:
                                if (!statusBarNotificationPresenter2.mPanelExpansionInteractor.isTracking() && !statusBarNotificationPresenter2.mQsController.getExpanded()) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (sysuiStatusBarStateController2.getState() == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !((HeadsUpManagerImpl) statusBarNotificationPresenter2.mHeadsUpManager).hasNotifications()) {
                                    statusBarNotificationPresenter2.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                };
                NotifShadeEventSource notifShadeEventSource2 = statusBarNotificationPresenter.mNotifShadeEventSource;
                notifShadeEventSource2.setShadeEmptiedCallback(runnable2);
                final int i3 = 1;
                notifShadeEventSource2.setNotifRemovedByUserCallback(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i32 = i3;
                        StatusBarNotificationPresenter statusBarNotificationPresenter2 = statusBarNotificationPresenter;
                        switch (i32) {
                            case 0:
                                if (!statusBarNotificationPresenter2.mPanelExpansionInteractor.isTracking() && !statusBarNotificationPresenter2.mQsController.getExpanded()) {
                                    SysuiStatusBarStateController sysuiStatusBarStateController2 = statusBarNotificationPresenter2.mStatusBarStateController;
                                    if (sysuiStatusBarStateController2.getState() == 2 && !statusBarNotificationPresenter2.isCollapsing()) {
                                        sysuiStatusBarStateController2.setState(1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                if (NotificationStackScrollLayoutController.this.mView.mPulsing && !((HeadsUpManagerImpl) statusBarNotificationPresenter2.mHeadsUpManager).hasNotifications()) {
                                    statusBarNotificationPresenter2.mDozeScrimController.mPulseOut.run();
                                    break;
                                }
                                break;
                        }
                    }
                });
                int i4 = VisualInterruptionRefactor.$r8$clinit;
                visualInterruptionDecisionProvider2.addLegacySuppressor(statusBarNotificationPresenter.mInterruptSuppressor);
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) statusBarNotificationPresenter.mLockscreenUserManager;
                notificationLockscreenUserManagerImpl.mPresenter = statusBarNotificationPresenter;
                NotificationGutsManager notificationGutsManager2 = statusBarNotificationPresenter.mGutsManager;
                notificationGutsManager2.mPresenter = statusBarNotificationPresenter;
                notificationGutsManager2.mListContainer = statusBarNotificationPresenter.mNotifListContainer;
                notificationGutsManager2.mOnSettingsClickListener = statusBarNotificationPresenter.mOnSettingsClickListener;
                ((HeadsUpManagerImpl) statusBarNotificationPresenter.mHeadsUpManager).mUser = notificationLockscreenUserManagerImpl.mCurrentUserId;
                statusBarNotificationPresenter.mCommandQueue.animateCollapsePanels();
                NotificationMediaManager notificationMediaManager2 = statusBarNotificationPresenter.mMediaManager;
                notificationMediaManager2.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda5(notificationMediaManager2));
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

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00f0, code lost:
    
        if (r7.isDynamicallyUnlocked() == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f9, code lost:
    
        ((com.android.systemui.statusbar.StatusBarStateControllerImpl) r9).setLeaveOpenOnKeyguardHide(true);
        r4.dismissKeyguardThenExecute(new com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3(), null, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f7, code lost:
    
        if (r7.isInLockedDownShade() != false) goto L49;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onExpandClicked(com.android.systemui.statusbar.notification.collection.NotificationEntry r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.onExpandClicked(com.android.systemui.statusbar.notification.collection.NotificationEntry, boolean):void");
    }
}
