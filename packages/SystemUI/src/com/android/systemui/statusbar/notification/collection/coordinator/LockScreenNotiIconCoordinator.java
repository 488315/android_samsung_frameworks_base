package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.res.Configuration;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@CoordinatorScope
public class LockScreenNotiIconCoordinator implements Coordinator, ConfigurationController.ConfigurationListener {
    private static final String TAG = "LockScreenNotiIconCoordinator";
    private AmbientState mAmbientState;
    private GroupMembershipManager mGroupMembershipManager;
    private boolean mHasVisibleNotificationOnKeyguard;
    private final KeyguardStateController mKeyguardStateController;
    private final LockscreenNotificationManager mLockscreenNotificationManager;
    private LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private ArrayList<LockscreenNotificationInfo> mNotificationInfoArray;
    private final PluginLockMediator mPluginLockMediator;
    private int mPluginLockMode;
    private SysuiStatusBarStateController mStatusBarStateController;
    private final NotifFilter mNotifFilter = new NotifFilter(TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.3
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return ((StatusBarStateControllerImpl) LockScreenNotiIconCoordinator.this.mStatusBarStateController).mUpcomingState == 1 && notificationEntry.isOngoingAcitivty() && notificationEntry.isPromotedState() && !LockScreenNotiIconCoordinator.this.needLockScreenExpandPanel();
        }
    };
    private final PluginLockListener.State mPluginLockListener = new PluginLockListener.State() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.4
        @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
        public void onViewModeChanged(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onViewModeChanged, mode: ", LockScreenNotiIconCoordinator.TAG);
            LockScreenNotiIconCoordinator.this.mPluginLockMode = i;
            if (LockScreenNotiIconCoordinator.this.mAmbientState != null) {
                LockScreenNotiIconCoordinator.this.mAmbientState.mPluginLockMode = LockScreenNotiIconCoordinator.this.mPluginLockMode;
                if (!LockScreenNotiIconCoordinator.this.needLockScreenExpandPanel() && LockScreenNotiIconCoordinator.this.mAmbientState.mDragDownOnKeyguard && LockScreenNotiIconCoordinator.this.mPluginLockMode != 1) {
                    LockScreenNotiIconCoordinator.this.mAmbientState.setDragDownOnKeyguard(false);
                }
            }
            LockScreenNotiIconCoordinator.this.onLockScreenNotiStateChanged();
        }
    };

    public LockScreenNotiIconCoordinator(LockscreenNotificationManager lockscreenNotificationManager, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, GroupMembershipManager groupMembershipManager, PluginLockMediator pluginLockMediator, StatusBarStateController statusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, AmbientState ambientState, ConfigurationController configurationController) {
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mGroupMembershipManager = groupMembershipManager;
        lockscreenNotificationManager.getClass();
        this.mNotificationInfoArray = new ArrayList<>();
        this.mStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mPluginLockMediator = pluginLockMediator;
        registerPluginLockCallback();
        this.mAmbientState = ambientState;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.1
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public void onExpansionFinished() {
                Log.d(LockScreenNotiIconCoordinator.TAG, "onExpansionFinished");
                if (LockScreenNotiIconCoordinator.this.mAmbientState.mDragDownOnKeyguard) {
                    LockScreenNotiIconCoordinator.this.mAmbientState.setDragDownOnKeyguard(false);
                    LockScreenNotiIconCoordinator.this.onLockScreenNotiStateChanged();
                }
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public void onExpansionReset() {
                Log.d(LockScreenNotiIconCoordinator.TAG, "onExpansionReset");
                if (LockScreenNotiIconCoordinator.this.mAmbientState.mDragDownOnKeyguard) {
                    LockScreenNotiIconCoordinator.this.mAmbientState.setDragDownOnKeyguard(false);
                    LockScreenNotiIconCoordinator.this.onLockScreenNotiStateChanged();
                }
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public void onExpansionStarted() {
                if (LockScreenNotiIconCoordinator.this.needLockScreenExpandPanel()) {
                    Log.d(LockScreenNotiIconCoordinator.TAG, "onExpansionStarted");
                    LockScreenNotiIconCoordinator.this.mAmbientState.setDragDownOnKeyguard(true);
                    LockScreenNotiIconCoordinator.this.onLockScreenNotiStateChanged();
                }
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public /* bridge */ /* synthetic */ void setTransitionToFullShadeAmount(float f) {
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public /* bridge */ /* synthetic */ void setTransitionToFullShadeAmount(float f, boolean z, long j) {
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public /* bridge */ /* synthetic */ void onPulseExpansionFinished() {
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public void onPrimaryBouncerShowingChanged() {
                LockScreenNotiIconCoordinator.this.onLockScreenNotiStateChanged();
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onFaceEnrolledChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardDismissAmountChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardFadingAwayChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardGoingAwayChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardShowingChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onLaunchTransitionFadingAwayChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onUnlockedChanged() {
            }
        });
    }

    private void applyNotificationInfoArray(List<ListEntry> list) {
        this.mLockscreenNotificationManager.getClass();
        boolean z = this.mLockscreenNotificationManager.mIsFolded;
        boolean z2 = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState == 1;
        boolean z3 = z2 || z;
        for (ListEntry listEntry : list) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            ExpandableNotificationRow expandableNotificationRow = representativeEntry.row;
            if (!((this.mPluginLockMode == 1 && z2) || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mPrimaryBouncerShowing) || this.mAmbientState.mDragDownOnKeyguard) {
                if (z3) {
                    expandableNotificationRow.getClass();
                    if (((ArrayList) representativeEntry.mDismissInterceptors).size() <= 0 && (!representativeEntry.isOngoingAcitivty() || !representativeEntry.isPromotedState())) {
                        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
                        ArrayList<LockscreenNotificationInfo> arrayList = this.mNotificationInfoArray;
                        lockscreenNotificationManager.getClass();
                        LockscreenNotificationInfo lockscreenNotificationInfo = new LockscreenNotificationInfo();
                        lockscreenNotificationInfo.mStatusBarIcon = representativeEntry.mIcons.mStatusBarIcon;
                        lockscreenNotificationInfo.mSbn = representativeEntry.mSbn;
                        lockscreenNotificationInfo.mKey = representativeEntry.mKey;
                        lockscreenNotificationInfo.mRow = representativeEntry.row;
                        arrayList.add(lockscreenNotificationInfo);
                    }
                }
                if (z2) {
                    this.mLockscreenNotificationManager.getClass();
                    if ((LockscreenNotificationManager.isNotificationIconsOnlyShowing() && !this.mAmbientState.mDragDownOnKeyguard) || ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone()) {
                        expandableNotificationRow.setVisibility(8);
                    }
                }
                lambda$restoreVisibility$0(listEntry);
            } else {
                expandableNotificationRow.setVisibility(8);
            }
        }
        if (this.mHasVisibleNotificationOnKeyguard) {
            return;
        }
        this.mHasVisibleNotificationOnKeyguard = !list.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean needLockScreenExpandPanel() {
        return this.mAmbientState.isNeedsToExpandLocksNoti();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLockScreenNotiInfoArrayUpdated(List<ListEntry> list) {
        applyNotificationInfoArray(list);
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        ArrayList<LockscreenNotificationInfo> arrayList = this.mNotificationInfoArray;
        synchronized (lockscreenNotificationManager.mLock) {
            lockscreenNotificationManager.mHandler.removeMessages(101);
            lockscreenNotificationManager.mHandler.obtainMessage(101, 0, 0, arrayList).sendToTarget();
        }
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState == 1) {
            LockscreenNotificationManager lockscreenNotificationManager2 = this.mLockscreenNotificationManager;
            this.mNotificationInfoArray.size();
            lockscreenNotificationManager2.getClass();
            this.mHasVisibleNotificationOnKeyguard = false;
        }
    }

    private void registerPluginLockCallback() {
        this.mPluginLockMediator.registerStateCallback(this.mPluginLockListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetNotificationInfoArray(List<ListEntry> list) {
        this.mLockscreenNotificationManager.getClass();
        this.mNotificationInfoArray = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: restoreVisibility, reason: merged with bridge method [inline-methods] */
    public void lambda$restoreVisibility$0(ListEntry listEntry) {
        ExpandableNotificationRow expandableNotificationRow = listEntry.getRepresentativeEntry().row;
        if (expandableNotificationRow.getVisibility() == 8) {
            expandableNotificationRow.setVisibility(0);
        }
        if (listEntry instanceof GroupEntry) {
            ((GroupEntry) listEntry).mUnmodifiableChildren.stream().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LockScreenNotiIconCoordinator.this.lambda$restoreVisibility$0((NotificationEntry) obj);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                LockScreenNotiIconCoordinator.this.resetNotificationInfoArray(list);
            }
        });
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                LockScreenNotiIconCoordinator.this.notifyLockScreenNotiInfoArrayUpdated(list);
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
        this.mLockscreenNotificationManager.mLockScreenNotificationStateListener = this;
    }

    public void onLockScreenNotiStateChanged() {
        this.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOrientationChanged(int i) {
        if (needLockScreenExpandPanel()) {
            return;
        }
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mDragDownOnKeyguard && i == 1 && this.mPluginLockMode != 1) {
            ambientState.setDragDownOnKeyguard(false);
            onLockScreenNotiStateChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onDensityOrFontScaleChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onDisplayDeviceTypeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLocaleListChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onMaxBoundsChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onSmallestScreenWidthChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onThemeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onUiModeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onConfigChanged(Configuration configuration) {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLayoutDirectionChanged(boolean z) {
    }
}
