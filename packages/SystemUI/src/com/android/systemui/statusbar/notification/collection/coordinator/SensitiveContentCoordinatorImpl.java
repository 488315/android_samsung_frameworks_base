package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.server.notification.Flags;
import com.android.systemui.NotiRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.flow.StateFlowImpl;

@CoordinatorScope
public final class SensitiveContentCoordinatorImpl extends Invalidator implements SensitiveContentCoordinator, DynamicPrivacyController.Listener, OnBeforeRenderListListener {
    public static final int $stable = 8;
    private final AppLockNotificationController appLockNotificationController;
    private final DynamicPrivacyController dynamicPrivacyController;
    private final KeyguardStateController keyguardStateController;
    private final KeyguardUpdateMonitor keyguardUpdateMonitor;
    private SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private boolean needUpdateNext;
    private final Runnable onSensitiveStateChanged;
    private final SensitiveContentCoordinatorImpl$screenshareSecretFilter$1 screenshareSecretFilter;
    private final SelectedUserInteractor selectedUserInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;
    private final SettingsHelper settingsHelper;
    private final StatusBarStateController statusBarStateController;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1] */
    public SensitiveContentCoordinatorImpl(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, SettingsHelper settingsHelper, AppLockNotificationController appLockNotificationController) {
        super("SensitiveContentInvalidator");
        this.dynamicPrivacyController = dynamicPrivacyController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.statusBarStateController = statusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        this.settingsHelper = settingsHelper;
        this.appLockNotificationController = appLockNotificationController;
        this.onSensitiveStateChanged = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$onSensitiveStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                SensitiveContentCoordinatorImpl.this.invalidateList("onSensitiveStateChanged");
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean z) {
                KeyguardStateController keyguardStateController2;
                if (z) {
                    return;
                }
                keyguardStateController2 = SensitiveContentCoordinatorImpl.this.keyguardStateController;
                if (((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardGoingAway) {
                    SensitiveContentCoordinatorImpl.this.needUpdateNext = true;
                    SensitiveContentCoordinatorImpl.this.invalidateList("onKeyguardVisibilityChanged");
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLocaleChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockModeChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerDown() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerUp() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUnlocking() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
            }
        };
        this.screenshareSecretFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1
            {
                super("ScreenshareSecretFilter");
            }

            public final boolean isSecret(NotificationEntry notificationEntry) {
                Notification notification2;
                NotificationChannel channel = notificationEntry.mRanking.getChannel();
                return (channel != null && channel.getLockscreenVisibility() == -1) || ((notification2 = notificationEntry.mSbn.getNotification()) != null && notification2.visibility == -1);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                SensitiveNotificationProtectionController sensitiveNotificationProtectionController2;
                Flags.screenshareNotificationHiding();
                sensitiveNotificationProtectionController2 = SensitiveContentCoordinatorImpl.this.sensitiveNotificationProtectionController;
                return ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController2).isSensitiveStateActive() && isSecret(notificationEntry);
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.dynamicPrivacyController.mListeners.add(this);
        Flags.screenshareNotificationHiding();
        SensitiveNotificationProtectionController sensitiveNotificationProtectionController = this.sensitiveNotificationProtectionController;
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(this.onSensitiveStateChanged);
        notifPipeline.addOnBeforeRenderListListener(this);
        notifPipeline.addPreRenderInvalidator(this);
        Flags.screenshareNotificationHiding();
        notifPipeline.addFinalizeFilter(this.screenshareSecretFilter);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
    public void onBeforeRenderList(List<? extends ListEntry> list) {
        Sequence extractAllRepresentativeEntries;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        ExpandableNotificationRow expandableNotificationRow;
        boolean z5 = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE;
        boolean z6 = false;
        if (z5) {
            this.keyguardUpdateMonitor.setHasRedactedNotifications(false);
        }
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway || this.needUpdateNext) {
            boolean z7 = true;
            if (this.statusBarStateController.getState() == 1 && this.keyguardUpdateMonitor.getUserUnlockedWithBiometricAndIsBypassing(this.selectedUserInteractor.getSelectedUserId(false))) {
                return;
            }
            this.needUpdateNext = false;
            Flags.screenshareNotificationHiding();
            boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).isSensitiveStateActive();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
            boolean isLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(i);
            boolean z8 = (isLockscreenPublicMode && !((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).userAllowsPrivateNotificationsInPublic(i)) || isSensitiveStateActive;
            boolean isDynamicallyUnlocked = this.dynamicPrivacyController.isDynamicallyUnlocked();
            boolean isAllowPrivateNotificationsWhenUnsecure = z5 ? this.settingsHelper.isAllowPrivateNotificationsWhenUnsecure(i) : false;
            extractAllRepresentativeEntries = SensitiveContentCoordinatorKt.extractAllRepresentativeEntries((List<? extends ListEntry>) list);
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(extractAllRepresentativeEntries, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$onBeforeRenderList$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(NotificationEntry notificationEntry) {
                    return Boolean.valueOf(notificationEntry.rowExists());
                }
            }));
            while (filteringSequence$iterator$1.hasNext()) {
                NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
                int identifier = notificationEntry.mSbn.getUser().getIdentifier();
                boolean z9 = (isLockscreenPublicMode || ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isLockscreenPublicMode(identifier)) ? z7 : z6;
                if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && isAllowPrivateNotificationsWhenUnsecure) {
                    if (z9) {
                        if (!isDynamicallyUnlocked) {
                            z9 = z7;
                        } else if (identifier != i && identifier != -1) {
                            z9 = ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).mUsersWithSeparateWorkChallenge.get(identifier, z6);
                        }
                    }
                    z9 = z6;
                }
                Flags.screenshareNotificationHiding();
                boolean shouldProtectNotification = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).shouldProtectNotification(notificationEntry);
                boolean needsRedaction = ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).needsRedaction(notificationEntry);
                boolean z10 = (z9 && needsRedaction) ? z7 : z6;
                if (!NotiRune.NOTI_INSIGNIFICANT || (expandableNotificationRow = notificationEntry.row) == null || expandableNotificationRow.isInsignificantSummary() != z7) {
                    if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
                        notificationEntry.mUserPublic = z9;
                    }
                    boolean z11 = NotiRune.NOTI_STYLE_APP_LOCK;
                    if (z11) {
                        z = ((AppLockNotificationControllerImpl) this.appLockNotificationController).shouldHideNotiForAppLock(notificationEntry);
                        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                        if (expandableNotificationRow2 != null && expandableNotificationRow2.mShowPublicExpander != (!z)) {
                            expandableNotificationRow2.mShowPublicExpander = z4;
                            expandableNotificationRow2.mPublicLayout.updateExpandButtonsDuringLayout(z4, false);
                        }
                    } else {
                        z = false;
                    }
                    boolean z12 = z10 || shouldProtectNotification || (z11 && z);
                    ExpandableNotificationRow expandableNotificationRow3 = notificationEntry.row;
                    int intrinsicHeight = expandableNotificationRow3.getIntrinsicHeight();
                    expandableNotificationRow3.mSensitive = z12;
                    expandableNotificationRow3.mSensitiveHiddenInGeneral = z8;
                    if (intrinsicHeight != expandableNotificationRow3.getIntrinsicHeight()) {
                        z7 = true;
                        expandableNotificationRow3.notifyHeightChanged(true);
                    } else {
                        z7 = true;
                    }
                    expandableNotificationRow3.updateBackgroundForGroupState();
                    StateFlowImpl stateFlowImpl = notificationEntry.mSensitive;
                    if (z12 != ((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                        stateFlowImpl.updateState(null, Boolean.valueOf(z12));
                        Iterator it = notificationEntry.mOnSensitivityChangedListeners.iterator();
                        while (it.hasNext()) {
                            ((NotificationEntry.OnSensitivityChangedListener) it.next()).onSensitivityChanged(notificationEntry);
                        }
                    }
                    Flags.screenshareNotificationHiding();
                    ExpandableNotificationRow expandableNotificationRow4 = notificationEntry.row;
                    if (expandableNotificationRow4 == null || expandableNotificationRow4.mShowPublicExpander == (!shouldProtectNotification)) {
                        z2 = false;
                    } else {
                        expandableNotificationRow4.mShowPublicExpander = z3;
                        z2 = false;
                        expandableNotificationRow4.mPublicLayout.updateExpandButtonsDuringLayout(z3, false);
                    }
                    if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && !this.keyguardUpdateMonitor.hasRedactedNotifications() && needsRedaction) {
                        this.keyguardUpdateMonitor.setHasRedactedNotifications(needsRedaction);
                    }
                    z6 = z2;
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
    public void onDynamicPrivacyChanged() {
        invalidateList("onDynamicPrivacyChanged");
    }

    private static /* synthetic */ void getKeyguardUpdateMonitorCallback$annotations() {
    }

    private static /* synthetic */ void getNeedUpdateNext$annotations() {
    }
}
