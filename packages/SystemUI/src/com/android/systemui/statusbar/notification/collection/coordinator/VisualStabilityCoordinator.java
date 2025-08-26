package com.android.systemui.statusbar.notification.collection.coordinator;

import android.net.Uri;
import android.provider.Settings;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.AvalancheController;
import com.android.systemui.statusbar.notification.headsup.AvalancheController$logDroppedHunsInBackground$1;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalism;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class VisualStabilityCoordinator implements Coordinator, Dumpable {
    protected static final long ALLOW_SECTION_CHANGE_TIMEOUT = 500;
    private final CommunalSceneInteractor mCommunalSceneInteractor;
    private final DelayableExecutor mDelayableExecutor;
    private boolean mFullyDozed;
    private final HeadsUpRepository mHeadsUpRepository;
    private final JavaAdapter mJavaAdapter;
    final KeyguardStateController.Callback mKeyguardFadeAwayAnimationCallback;
    private final KeyguardStateController mKeyguardStateController;
    private final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    private final VisualStabilityCoordinatorLogger mLogger;
    private final DelayableExecutor mMainExecutor;
    private boolean mNotifPanelCollapsing;
    private boolean mNotifPanelLaunchingActivity;
    private final NotifStabilityManager mNotifStabilityManager;
    private final OnBeforeRenderListListener mOnBeforeRenderListListener;
    private boolean mPanelExpanded;
    private boolean mPipelineRunAllowed;
    private boolean mPulsing;
    private boolean mReorderingAllowed;
    private final SeenNotificationsInteractor mSeenNotificationsInteractor;
    private SettingsHelper.OnChangedCallback mSettingsCallback;
    private final Uri[] mSettingsValueList;
    private final ShadeAnimationInteractor mShadeAnimationInteractor;
    private final ShadeInteractor mShadeInteractor;
    private final StatusBarStateController mStatusBarStateController;
    final StatusBarStateController.StateListener mStatusBarStateControllerListener;
    private final VisibilityLocationProvider mVisibilityLocationProvider;
    private final VisualStabilityProvider mVisualStabilityProvider;
    private final WakefulnessLifecycle mWakefulnessLifecycle;
    final WakefulnessLifecycle.Observer mWakefulnessObserver;
    private boolean mSleepy = true;
    private boolean mCommunalShowing = false;
    private boolean mLockscreenShowing = false;
    private boolean mTrackingHeadsUp = false;
    private boolean mLockscreenInGoneTransition = false;
    private Set<String> mHeadsUpGroupKeys = new HashSet();
    private boolean mIsSuppressingPipelineRun = false;
    private boolean mIsSuppressingGroupChange = false;
    private final Set<String> mEntriesWithSuppressedSectionChange = new HashSet();
    private boolean mIsSuppressingEntryReorder = false;
    private Map<String, Runnable> mEntriesThatCanChangeSection = new HashMap();
    private final boolean mCheckLockScreenTransitionEnabled = true;

    public VisualStabilityCoordinator(DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, DumpManager dumpManager, HeadsUpRepository headsUpRepository, ShadeAnimationInteractor shadeAnimationInteractor, JavaAdapter javaAdapter, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider, WakefulnessLifecycle wakefulnessLifecycle, CommunalSceneInteractor communalSceneInteractor, ShadeInteractor shadeInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardStateController keyguardStateController, VisualStabilityCoordinatorLogger visualStabilityCoordinatorLogger) {
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_SORT_TIME)};
        this.mSettingsValueList = uriArr;
        this.mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public void onChanged(Uri uri) {
                if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_SORT_TIME))) {
                    VisualStabilityCoordinator.this.mNotifStabilityManager.invalidateList("TIME_SORT_CHANGED");
                }
            }
        };
        this.mKeyguardFadeAwayAnimationCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public void onKeyguardFadingAwayChanged() {
                VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
                visualStabilityCoordinator.onLockscreenInGoneTransitionChanged(((KeyguardStateControllerImpl) visualStabilityCoordinator.mKeyguardStateController).mKeyguardFadingAway);
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onFaceEnrolledChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardDismissAmountChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardGoingAwayChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onKeyguardShowingChanged() {
            }

            public /* bridge */ /* synthetic */ void onLaunchTransitionFadingAwayChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onPrimaryBouncerShowingChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public /* bridge */ /* synthetic */ void onUnlockedChanged() {
            }
        };
        this.mNotifStabilityManager = new NotifStabilityManager("VisualStabilityCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.3
            private boolean canMoveForHeadsUp(NotificationEntry notificationEntry) {
                if (notificationEntry == null) {
                    return false;
                }
                int i = NotificationMinimalism.$r8$clinit;
                if (((HeadsUpManagerImpl) VisualStabilityCoordinator.this.mHeadsUpRepository).isHeadsUpEntry(notificationEntry.mKey)) {
                    return !VisualStabilityCoordinator.this.mVisibilityLocationProvider.isInVisibleLocation(notificationEntry);
                }
                return false;
            }

            private boolean canReorderGroupEntry(GroupEntry groupEntry) {
                return (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode() || groupEntry == null || !VisualStabilityCoordinator.this.mReorderingAllowed || isHeadsUpGroup(groupEntry)) ? false : true;
            }

            private boolean canReorderNotificationEntry(NotificationEntry notificationEntry) {
                return (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode() || notificationEntry == null || !VisualStabilityCoordinator.this.mReorderingAllowed || isParentHeadsUpGroup(notificationEntry)) ? false : true;
            }

            private boolean isHeadsUpGroup(PipelineEntry pipelineEntry) {
                NotificationEntry notificationEntry;
                if (!(pipelineEntry instanceof GroupEntry)) {
                    return false;
                }
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                if (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode() || (notificationEntry = groupEntry.mSummary) == null) {
                    return false;
                }
                return ((HeadsUpManagerImpl) VisualStabilityCoordinator.this.mHeadsUpRepository).isHeadsUpEntry(notificationEntry.mKey);
            }

            private boolean isParentHeadsUpGroup(NotificationEntry notificationEntry) {
                PipelineEntry pipelineEntry;
                if (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode() || notificationEntry == null || (pipelineEntry = notificationEntry.mAttachState.parent) == null) {
                    return false;
                }
                return isHeadsUpGroup(pipelineEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isEntryReorderingAllowed(PipelineEntry pipelineEntry) {
                if (!StabilizeHeadsUpGroup.isEnabled()) {
                    return VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(pipelineEntry.getRepresentativeEntry());
                }
                if (isEveryChangeAllowed()) {
                    return true;
                }
                NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
                return canReorderNotificationEntry(representativeEntry) || canMoveForHeadsUp(representativeEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isEveryChangeAllowed() {
                return StabilizeHeadsUpGroup.isEnabled() ? VisualStabilityCoordinator.this.mReorderingAllowed && VisualStabilityCoordinator.this.mHeadsUpGroupKeys.isEmpty() : VisualStabilityCoordinator.this.mReorderingAllowed;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
                VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
                visualStabilityCoordinator.mIsSuppressingGroupChange = visualStabilityCoordinator.mIsSuppressingGroupChange;
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isGroupPruneAllowed(GroupEntry groupEntry) {
                boolean z = StabilizeHeadsUpGroup.isEnabled() ? isEveryChangeAllowed() || canReorderGroupEntry(groupEntry) : VisualStabilityCoordinator.this.mReorderingAllowed;
                VisualStabilityCoordinator.this.mIsSuppressingGroupChange |= !z;
                return z;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isPipelineRunAllowed() {
                VisualStabilityCoordinator.this.mIsSuppressingPipelineRun |= !VisualStabilityCoordinator.this.mPipelineRunAllowed;
                return VisualStabilityCoordinator.this.mPipelineRunAllowed;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isSectionChangeAllowed(NotificationEntry notificationEntry) {
                boolean z = true;
                if (!StabilizeHeadsUpGroup.isEnabled() ? !(VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(notificationEntry) || VisualStabilityCoordinator.this.mEntriesThatCanChangeSection.containsKey(notificationEntry.mKey)) : !(isEveryChangeAllowed() || canReorderNotificationEntry(notificationEntry) || canMoveForHeadsUp(notificationEntry) || VisualStabilityCoordinator.this.mEntriesThatCanChangeSection.containsKey(notificationEntry.mKey))) {
                    z = false;
                }
                if (!z) {
                    VisualStabilityCoordinator.this.mEntriesWithSuppressedSectionChange.add(notificationEntry.mKey);
                }
                return z;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public void onBeginRun() {
                VisualStabilityCoordinator.this.mIsSuppressingPipelineRun = false;
                VisualStabilityCoordinator.this.mIsSuppressingGroupChange = false;
                VisualStabilityCoordinator.this.mEntriesWithSuppressedSectionChange.clear();
                VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = false;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public void onEntryReorderSuppressed() {
                VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = true;
            }
        };
        this.mOnBeforeRenderListListener = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.4
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public void onBeforeRenderList(List<PipelineEntry> list) {
                NotificationEntry notificationEntry;
                if (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode()) {
                    return;
                }
                HashSet hashSet = new HashSet();
                for (int i = 0; i < list.size(); i++) {
                    PipelineEntry pipelineEntry = list.get(i);
                    if ((pipelineEntry instanceof GroupEntry) && (notificationEntry = ((GroupEntry) pipelineEntry).mSummary) != null) {
                        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) VisualStabilityCoordinator.this.mHeadsUpRepository;
                        String str = notificationEntry.mKey;
                        if (headsUpManagerImpl.isHeadsUpEntry(str)) {
                            hashSet.add(str);
                        }
                    }
                }
                VisualStabilityCoordinator.this.setHeadsUpGroupKeys(hashSet);
            }
        };
        this.mStatusBarStateControllerListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.5
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozeAmountChanged(float f, float f2) {
                boolean z = f == 1.0f;
                VisualStabilityCoordinator.this.mFullyDozed = z;
                VisualStabilityCoordinator.this.updateAllowedStates("fullyDozed", z);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onExpandedChanged(boolean z) {
                VisualStabilityCoordinator.this.mPanelExpanded = z;
                VisualStabilityCoordinator.this.updateAllowedStates("panelExpanded", z);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onPulsingChanged(boolean z) {
                VisualStabilityCoordinator.this.mPulsing = z;
                VisualStabilityCoordinator.this.updateAllowedStates("pulsing", z);
            }
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.6
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedGoingToSleep() {
                VisualStabilityCoordinator.this.mSleepy = true;
                VisualStabilityCoordinator.this.updateAllowedStates("sleepy", true);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedWakingUp() {
                VisualStabilityCoordinator.this.mSleepy = false;
                VisualStabilityCoordinator.this.updateAllowedStates("sleepy", false);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public /* bridge */ /* synthetic */ void onFinishedWakingUp() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public /* bridge */ /* synthetic */ void onPostFinishedWakingUp() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public /* bridge */ /* synthetic */ void onStartedGoingToSleep() {
            }
        };
        this.mHeadsUpRepository = headsUpRepository;
        this.mShadeAnimationInteractor = shadeAnimationInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mSeenNotificationsInteractor = seenNotificationsInteractor;
        this.mVisibilityLocationProvider = visibilityLocationProvider;
        this.mVisualStabilityProvider = visualStabilityProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = statusBarStateController;
        this.mDelayableExecutor = delayableExecutor;
        this.mMainExecutor = delayableExecutor2;
        this.mCommunalSceneInteractor = communalSceneInteractor;
        this.mShadeInteractor = shadeInteractor;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mKeyguardStateController = keyguardStateController;
        this.mLogger = visualStabilityCoordinatorLogger;
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsCallback, uriArr);
        dumpManager.registerDumpable(this);
    }

    private boolean isPanelCollapsingOrLaunchingActivity() {
        return this.mNotifPanelCollapsing || this.mNotifPanelLaunchingActivity;
    }

    private boolean isReorderingAllowed() {
        if (NotiRune.NOTI_SUBSCREEN_ALL && ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.isSubScreen()) {
            return true;
        }
        return ((this.mFullyDozed && this.mSleepy) || !isStackShowing() || this.mCommunalShowing) && !this.mPulsing;
    }

    private boolean isStackShowing() {
        int i = SceneContainerFlag.$r8$clinit;
        return this.mPanelExpanded;
    }

    private boolean isSuppressingSectionChange() {
        return !this.mEntriesWithSuppressedSectionChange.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$temporarilyAllowSectionChanges$0(String str) {
        this.mEntriesThatCanChangeSection.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$temporarilyAllowSectionChanges$1(String str) {
        this.mEntriesThatCanChangeSection.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeInvalidateList() {
        if (this.mPipelineRunAllowed && this.mIsSuppressingPipelineRun) {
            this.mNotifStabilityManager.invalidateList("pipeline run suppression ended");
            return;
        }
        if (this.mReorderingAllowed) {
            if (this.mIsSuppressingGroupChange || isSuppressingSectionChange() || this.mIsSuppressingEntryReorder) {
                this.mNotifStabilityManager.invalidateList("reorder suppression ended for group=" + this.mIsSuppressingGroupChange + " section=" + isSuppressingSectionChange() + " sort=" + this.mIsSuppressingEntryReorder);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCommunalShowingChanged(boolean z) {
        this.mCommunalShowing = z;
        updateAllowedStates("communalShowing", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLaunchingActivityChanged(boolean z) {
        this.mNotifPanelLaunchingActivity = z;
        updateAllowedStates("notifPanelLaunchingActivity", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLockscreenInGoneTransitionChanged(boolean z) {
        if (this.mCheckLockScreenTransitionEnabled && z != this.mLockscreenInGoneTransition) {
            this.mLockscreenInGoneTransition = z;
            updateAllowedStates("lockscreenInGoneTransition", z);
        }
    }

    private void onLockscreenKeyguardStateTransitionValueChanged(float f) {
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShadeOrQsClosingChanged(boolean z) {
        this.mNotifPanelCollapsing = z;
        updateAllowedStates("notifPanelCollapsing", z);
    }

    private void onTrackingHeadsUpModeChanged(boolean z) {
        this.mTrackingHeadsUp = z;
        updateAllowedStates("trackingHeadsUp", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAllowedStates(String str, boolean z) {
        updateAllowedStates(str, z, false);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        final int i = 0;
        final int i2 = 1;
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mSleepy = this.mWakefulnessLifecycle.mWakefulness == 0;
        this.mFullyDozed = this.mStatusBarStateController.getDozeAmount() == 1.0f;
        this.mStatusBarStateController.addCallback(this.mStatusBarStateControllerListener);
        this.mPulsing = this.mStatusBarStateController.isPulsing();
        this.mJavaAdapter.alwaysCollectFlow(this.mShadeAnimationInteractor.isAnyCloseAnimationRunning(), new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda2
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = i;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                switch (i3) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(zBooleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(zBooleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(zBooleanValue);
                        break;
                }
            }
        });
        this.mJavaAdapter.alwaysCollectFlow(this.mShadeAnimationInteractor.isLaunchingActivity, new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda2
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = i2;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                switch (i3) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(zBooleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(zBooleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(zBooleanValue);
                        break;
                }
            }
        });
        JavaAdapter javaAdapter = this.mJavaAdapter;
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        final int i3 = 2;
        javaAdapter.alwaysCollectFlow(booleanFlowOperators.allOf(this.mCommunalSceneInteractor.isIdleOnCommunal, booleanFlowOperators.not(((ShadeInteractorImpl) this.mShadeInteractor).isAnyFullyExpanded)), new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda2
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i3;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                switch (i32) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(zBooleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(zBooleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(zBooleanValue);
                        break;
                }
            }
        });
        if (StabilizeHeadsUpGroup.isEnabled()) {
            notifPipeline.addOnBeforeRenderListListener(this.mOnBeforeRenderListListener);
        }
        int i4 = SceneContainerFlag.$r8$clinit;
        if (this.mCheckLockScreenTransitionEnabled) {
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardFadeAwayAnimationCallback);
        }
        NotifStabilityManager notifStabilityManager = this.mNotifStabilityManager;
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        if (shadeListBuilder.mNotifStabilityManager == null) {
            shadeListBuilder.mNotifStabilityManager = notifStabilityManager;
            notifStabilityManager.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 3));
        } else {
            throw new IllegalStateException("Attempting to set the NotifStabilityManager more than once. There should only be one visual stability manager. Manager is being set by " + shadeListBuilder.mNotifStabilityManager.getName() + " and " + notifStabilityManager.getName());
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("pipelineRunAllowed: "), this.mPipelineRunAllowed, printWriter, "  notifPanelCollapsing: "), this.mNotifPanelCollapsing, printWriter, "  launchingNotifActivity: "), this.mNotifPanelLaunchingActivity, printWriter);
        if (this.mCheckLockScreenTransitionEnabled) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  lockscreenInGoneTransition: "), this.mLockscreenInGoneTransition, printWriter);
        }
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("reorderingAllowed: "), this.mReorderingAllowed, printWriter, "  sleepy: "), this.mSleepy, printWriter, "  fullyDozed: "), this.mFullyDozed, printWriter, "  panelExpanded: "), this.mPanelExpanded, printWriter, "  pulsing: "), this.mPulsing, printWriter, "  communalShowing: "), this.mCommunalShowing, printWriter, "isSuppressingPipelineRun: "), this.mIsSuppressingPipelineRun, printWriter, "isSuppressingGroupChange: "), this.mIsSuppressingGroupChange, printWriter, "isSuppressingEntryReorder: ");
        sbM.append(this.mIsSuppressingEntryReorder);
        printWriter.println(sbM.toString());
        if (StabilizeHeadsUpGroup.isEnabled()) {
            printWriter.println("headsUpGroupKeys: " + this.mHeadsUpGroupKeys.size());
        }
        printWriter.println("entriesWithSuppressedSectionChange: " + this.mEntriesWithSuppressedSectionChange.size());
        Iterator<String> it = this.mEntriesWithSuppressedSectionChange.iterator();
        while (it.hasNext()) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "  ", it.next());
        }
        printWriter.println("entriesThatCanChangeSection: " + this.mEntriesThatCanChangeSection.size());
        Iterator<String> it2 = this.mEntriesThatCanChangeSection.keySet().iterator();
        while (it2.hasNext()) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "  ", it2.next());
        }
    }

    public void setHeadsUpGroupKeys(Set<String> set) {
        boolean z;
        if (StabilizeHeadsUpGroup.isUnexpectedlyInLegacyMode()) {
            return;
        }
        if (set == null) {
            set = new HashSet<>();
        }
        Iterator<String> it = this.mHeadsUpGroupKeys.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (!set.contains(it.next())) {
                z = true;
                break;
            }
        }
        this.mHeadsUpGroupKeys = set;
        if (z) {
            updateAllowedStates("headsUpGroupEntryChange", set.isEmpty(), true);
        }
    }

    public void temporarilyAllowSectionChanges(NotificationEntry notificationEntry, long j) {
        String str = notificationEntry.mKey;
        boolean zIsSectionChangeAllowed = this.mNotifStabilityManager.isSectionChangeAllowed(notificationEntry);
        if (this.mEntriesThatCanChangeSection.containsKey(str)) {
            this.mEntriesThatCanChangeSection.get(str).run();
        }
        this.mEntriesThatCanChangeSection.put(str, this.mDelayableExecutor.executeAtTime(new VisualStabilityCoordinator$$ExternalSyntheticLambda0(this, str, 1), j + ALLOW_SECTION_CHANGE_TIMEOUT));
        if (zIsSectionChangeAllowed) {
            return;
        }
        this.mNotifStabilityManager.invalidateList("temporarilyAllowSectionChanges");
    }

    private void updateAllowedStates(String str, boolean z, boolean z2) {
        boolean z3;
        boolean z4 = this.mPipelineRunAllowed;
        boolean z5 = this.mReorderingAllowed;
        this.mPipelineRunAllowed = (isPanelCollapsingOrLaunchingActivity() || (this.mCheckLockScreenTransitionEnabled && this.mLockscreenInGoneTransition)) ? false : true;
        boolean zIsReorderingAllowed = isReorderingAllowed();
        this.mReorderingAllowed = zIsReorderingAllowed;
        boolean z6 = this.mPipelineRunAllowed;
        if (z4 == z6 && z5 == zIsReorderingAllowed) {
            z3 = z2;
        } else {
            z3 = z2;
            this.mLogger.logAllowancesChanged(z4, z6, z5, zIsReorderingAllowed, str, z, z3);
        }
        if (z3) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.maybeInvalidateList();
                }
            });
        } else {
            maybeInvalidateList();
        }
        VisualStabilityProvider visualStabilityProvider = this.mVisualStabilityProvider;
        boolean z7 = this.mReorderingAllowed;
        if (visualStabilityProvider.isReorderingAllowed != z7) {
            visualStabilityProvider.isReorderingAllowed = z7;
            if (z7) {
                ListenerSet<OnReorderingAllowedListener> listenerSet = visualStabilityProvider.allListeners;
                for (OnReorderingAllowedListener onReorderingAllowedListener : listenerSet) {
                    if (visualStabilityProvider.temporaryListeners.remove(onReorderingAllowedListener)) {
                        listenerSet.remove(onReorderingAllowedListener);
                    }
                    onReorderingAllowedListener.onReorderingAllowed();
                }
                return;
            }
            Iterator<E> it = visualStabilityProvider.banListeners.iterator();
            while (it.hasNext()) {
                AvalancheController avalancheController = ((HeadsUpManagerImpl$$ExternalSyntheticLambda4) it.next()).f$0.mAvalancheController;
                if (avalancheController != null) {
                    avalancheController.bgHandler.post(new AvalancheController$logDroppedHunsInBackground$1(((ArrayList) avalancheController.getWaitingKeys()).size(), avalancheController));
                    ((ArrayList) avalancheController.nextList).clear();
                    ((HashMap) avalancheController.nextMap).clear();
                    avalancheController.headsUpEntryShowing = null;
                    if (avalancheController.enableAtRuntime) {
                        avalancheController.enableAtRuntime = false;
                    }
                }
            }
        }
    }

    public void temporarilyAllowSectionChanges(Collection<NotificationEntry> collection, long j) {
        for (NotificationEntry notificationEntry : collection) {
            String str = notificationEntry.mKey;
            this.mNotifStabilityManager.isSectionChangeAllowed(notificationEntry);
            if (this.mEntriesThatCanChangeSection.containsKey(str)) {
                this.mEntriesThatCanChangeSection.get(str).run();
            }
            this.mEntriesThatCanChangeSection.put(str, this.mDelayableExecutor.executeAtTime(new VisualStabilityCoordinator$$ExternalSyntheticLambda0(this, str, 0), ALLOW_SECTION_CHANGE_TIMEOUT + j));
        }
        this.mNotifStabilityManager.invalidateList("temporarilyAllowSectionChanges");
    }
}
