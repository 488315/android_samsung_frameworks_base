package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.NotiRune;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper$mOnHideRawValueChangedListener$1;
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
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class SensitiveContentCoordinatorImpl extends Invalidator implements SensitiveContentCoordinator, DynamicPrivacyController.Listener, OnBeforeRenderListListener {
    public static final int $stable = 8;
    private final AppLockNotificationController appLockNotificationController;
    private boolean canSwipeToEnter;
    private final DeviceEntryInteractor deviceEntryInteractor;
    private final DynamicPrivacyController dynamicPrivacyController;
    private boolean inTransitionFromLockedToGone;
    private final KeyguardStateController keyguardStateController;
    private final KeyguardUpdateMonitor keyguardUpdateMonitor;
    private SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private boolean needUpdateNext;
    private final Runnable onSensitiveStateChanged;
    private final SceneInteractor sceneInteractor;
    private final CoroutineScope scope;
    private final SensitiveContentCoordinatorImpl$screenshareSecretFilter$1 screenshareSecretFilter;
    private final SelectedUserInteractor selectedUserInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;
    private final SettingsHelper settingsHelper;
    private final StatusBarStateController statusBarStateController;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1] */
    public SensitiveContentCoordinatorImpl(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, DeviceEntryInteractor deviceEntryInteractor, SceneInteractor sceneInteractor, CoroutineScope coroutineScope, SettingsHelper settingsHelper, AppLockNotificationController appLockNotificationController) {
        super("SensitiveContentInvalidator");
        this.dynamicPrivacyController = dynamicPrivacyController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.statusBarStateController = statusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneInteractor = sceneInteractor;
        this.scope = coroutineScope;
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
                NotificationChannel channel = notificationEntry.mRanking.getChannel();
                if (channel != null && channel.getLockscreenVisibility() == -1) {
                    return true;
                }
                Notification notification2 = notificationEntry.mSbn.getNotification();
                return notification2 != null && notification2.visibility == -1;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                SensitiveNotificationProtectionController sensitiveNotificationProtectionController2;
                sensitiveNotificationProtectionController2 = SensitiveContentCoordinatorImpl.this.sensitiveNotificationProtectionController;
                return ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController2).isSensitiveStateActive() && isSecret(notificationEntry);
            }
        };
    }

    private final boolean isKeyguardGoingAway() {
        return ((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.dynamicPrivacyController.mListeners.add(this);
        SensitiveNotificationProtectionController sensitiveNotificationProtectionController = this.sensitiveNotificationProtectionController;
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(this.onSensitiveStateChanged);
        notifPipeline.addOnBeforeRenderListListener(this);
        notifPipeline.addPreRenderInvalidator(this);
        notifPipeline.addFinalizeFilter(this.screenshareSecretFilter);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
    public void onBeforeRenderList(List<? extends PipelineEntry> list) {
        Sequence extractAllRepresentativeEntries;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE;
        boolean z6 = false;
        if (z5) {
            this.keyguardUpdateMonitor.setHasRedactedNotifications(false);
        }
        if (!isKeyguardGoingAway() || this.needUpdateNext) {
            boolean z7 = true;
            if (this.statusBarStateController.getState() == 1 && this.keyguardUpdateMonitor.getUserUnlockedWithBiometricAndIsBypassing(this.selectedUserInteractor.getSelectedUserId())) {
                return;
            }
            this.needUpdateNext = false;
            boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).isSensitiveStateActive();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
            boolean isLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(i);
            boolean z8 = (isLockscreenPublicMode && !((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).userAllowsPrivateNotificationsInPublic(i)) || isSensitiveStateActive;
            boolean isDynamicallyUnlocked = this.dynamicPrivacyController.isDynamicallyUnlocked();
            boolean isAllowPrivateNotificationsWhenUnsecure = z5 ? this.settingsHelper.isAllowPrivateNotificationsWhenUnsecure(i) : false;
            extractAllRepresentativeEntries = SensitiveContentCoordinatorKt.extractAllRepresentativeEntries((List<? extends PipelineEntry>) list);
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(extractAllRepresentativeEntries, new SensitiveContentCoordinatorImpl$$ExternalSyntheticLambda0()));
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
                boolean shouldProtectNotification = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).shouldProtectNotification(notificationEntry);
                boolean z10 = ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).getRedactionType(notificationEntry) != 0 ? z7 : z6;
                boolean z11 = (z9 && z10) ? z7 : z6;
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow == null || expandableNotificationRow.isInsignificantSummary() != z7) {
                    if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
                        notificationEntry.mUserPublic = z9;
                    }
                    boolean z12 = NotiRune.NOTI_STYLE_APP_LOCK;
                    if (z12) {
                        z = ((AppLockNotificationControllerImpl) this.appLockNotificationController).shouldHideNotiForAppLock(notificationEntry);
                        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                        if (expandableNotificationRow2 != null && expandableNotificationRow2.mShowPublicExpander != (!z)) {
                            expandableNotificationRow2.mShowPublicExpander = z4;
                            expandableNotificationRow2.mPublicLayout.updateExpandButtonsDuringLayout(z4, false);
                        }
                    } else {
                        z = false;
                    }
                    notificationEntry.setSensitive(z11 || shouldProtectNotification || (z12 && z), z8);
                    boolean z13 = z10 || shouldProtectNotification || (z12 && z);
                    if (notificationEntry.mRawValueHide != z13) {
                        notificationEntry.mRawValueHide = z13;
                        Iterator it = notificationEntry.mOnHideRawValueChangedListeners.iterator();
                        while (it.hasNext()) {
                            ((OngoingActivityDataHelper$mOnHideRawValueChangedListener$1) ((NotificationEntry.OnHideRawValueChangedListener) it.next())).getClass();
                            String str = OngoingActivityDataHelper.TAG;
                            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("hide change : ", " | ", notificationEntry.mRawValueHide);
                            String str2 = notificationEntry.mKey;
                            m.append(str2);
                            Log.i(str, m.toString());
                            OngoingActivityDataHelper.INSTANCE.getClass();
                            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str2);
                            if (ongoingActivityDataByKey != null) {
                                OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityDataByKey));
                            } else {
                                Log.i(str, "updateNowbarItemWhenSensitivityChanged() : ongoingActivityData is null");
                            }
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow3 = notificationEntry.row;
                    if (expandableNotificationRow3 == null || expandableNotificationRow3.mShowPublicExpander == (!shouldProtectNotification)) {
                        z2 = false;
                    } else {
                        expandableNotificationRow3.mShowPublicExpander = z3;
                        z2 = false;
                        expandableNotificationRow3.mPublicLayout.updateExpandButtonsDuringLayout(z3, false);
                    }
                    if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && !this.keyguardUpdateMonitor.hasRedactedNotifications() && z10) {
                        this.keyguardUpdateMonitor.setHasRedactedNotifications(z10);
                    }
                    z6 = z2;
                    z7 = true;
                } else {
                    notificationEntry.setSensitive(z6, z8);
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
