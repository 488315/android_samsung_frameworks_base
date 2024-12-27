package com.android.systemui.statusbar.notification.collection.coordinator;

import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.NotiRune;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalismPrototype$V2;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class VisualStabilityCoordinator implements Coordinator, Dumpable {
    protected static final long ALLOW_SECTION_CHANGE_TIMEOUT = 500;
    public static final boolean DEBUG = false;
    public static final String TAG = "VisualStability";
    private final CommunalInteractor mCommunalInteractor;
    private final DelayableExecutor mDelayableExecutor;
    private boolean mFullyDozed;
    private final HeadsUpManager mHeadsUpManager;
    private final JavaAdapter mJavaAdapter;
    private boolean mNotifPanelCollapsing;
    private boolean mNotifPanelLaunchingActivity;
    private final NotifStabilityManager mNotifStabilityManager;
    private boolean mPanelExpanded;
    private boolean mPipelineRunAllowed;
    private boolean mPulsing;
    private boolean mReorderingAllowed;
    private final SeenNotificationsInteractor mSeenNotificationsInteractor;
    private SettingsHelper.OnChangedCallback mSettingsCallback;
    private final Uri[] mSettingsValueList;
    private final ShadeAnimationInteractor mShadeAnimationInteractor;
    private final StatusBarStateController mStatusBarStateController;
    final StatusBarStateController.StateListener mStatusBarStateControllerListener;
    private final VisibilityLocationProvider mVisibilityLocationProvider;
    private final VisualStabilityProvider mVisualStabilityProvider;
    private final WakefulnessLifecycle mWakefulnessLifecycle;
    final WakefulnessLifecycle.Observer mWakefulnessObserver;
    private boolean mSleepy = true;
    private boolean mCommunalShowing = false;
    private boolean mIsSuppressingPipelineRun = false;
    private boolean mIsSuppressingGroupChange = false;
    private final Set<String> mEntriesWithSuppressedSectionChange = new HashSet();
    private boolean mIsSuppressingEntryReorder = false;
    private Map<String, Runnable> mEntriesThatCanChangeSection = new HashMap();

    public VisualStabilityCoordinator(DelayableExecutor delayableExecutor, DumpManager dumpManager, HeadsUpManager headsUpManager, ShadeAnimationInteractor shadeAnimationInteractor, JavaAdapter javaAdapter, SeenNotificationsInteractor seenNotificationsInteractor, StatusBarStateController statusBarStateController, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider, WakefulnessLifecycle wakefulnessLifecycle, CommunalInteractor communalInteractor) {
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
        this.mNotifStabilityManager = new NotifStabilityManager("VisualStabilityCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.2
            private boolean canMoveForHeadsUp(NotificationEntry notificationEntry) {
                if (notificationEntry == null) {
                    return false;
                }
                int i = NotificationMinimalismPrototype$V2.$r8$clinit;
                Flags.notificationMinimalismPrototype();
                if (((BaseHeadsUpManager) VisualStabilityCoordinator.this.mHeadsUpManager).isHeadsUpEntry(notificationEntry.mKey)) {
                    return !VisualStabilityCoordinator.this.mVisibilityLocationProvider.isInVisibleLocation(notificationEntry);
                }
                return false;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isEntryReorderingAllowed(ListEntry listEntry) {
                return VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(listEntry.getRepresentativeEntry());
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isEveryChangeAllowed() {
                return VisualStabilityCoordinator.this.mReorderingAllowed;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
                VisualStabilityCoordinator visualStabilityCoordinator = VisualStabilityCoordinator.this;
                visualStabilityCoordinator.mIsSuppressingGroupChange = visualStabilityCoordinator.mIsSuppressingGroupChange;
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
            public boolean isGroupPruneAllowed(GroupEntry groupEntry) {
                boolean z = VisualStabilityCoordinator.this.mReorderingAllowed;
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
                boolean z = VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(notificationEntry) || VisualStabilityCoordinator.this.mEntriesThatCanChangeSection.containsKey(notificationEntry.mKey);
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
        this.mStatusBarStateControllerListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.3
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
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.4
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
        this.mHeadsUpManager = headsUpManager;
        this.mShadeAnimationInteractor = shadeAnimationInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mSeenNotificationsInteractor = seenNotificationsInteractor;
        this.mVisibilityLocationProvider = visibilityLocationProvider;
        this.mVisualStabilityProvider = visualStabilityProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = statusBarStateController;
        this.mDelayableExecutor = delayableExecutor;
        this.mCommunalInteractor = communalInteractor;
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
        return ((this.mFullyDozed && this.mSleepy) || !this.mPanelExpanded || this.mCommunalShowing) && !this.mPulsing;
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
    public void onShadeOrQsClosingChanged(boolean z) {
        this.mNotifPanelCollapsing = z;
        updateAllowedStates("notifPanelCollapsing", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAllowedStates(String str, boolean z) {
        boolean z2 = this.mPipelineRunAllowed;
        boolean z3 = this.mReorderingAllowed;
        this.mPipelineRunAllowed = !isPanelCollapsingOrLaunchingActivity();
        boolean isReorderingAllowed = isReorderingAllowed();
        this.mReorderingAllowed = isReorderingAllowed;
        if (DEBUG && (z2 != this.mPipelineRunAllowed || z3 != isReorderingAllowed)) {
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("Stability allowances changed:  pipelineRunAllowed ", "->", z2);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, this.mPipelineRunAllowed, "  reorderingAllowed ", z3, "->");
            m.append(this.mReorderingAllowed);
            m.append("  when setting ");
            m.append(str);
            m.append("=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, z, TAG);
        }
        if (this.mPipelineRunAllowed && this.mIsSuppressingPipelineRun) {
            this.mNotifStabilityManager.invalidateList("pipeline run suppression ended");
        } else if (this.mReorderingAllowed && (this.mIsSuppressingGroupChange || isSuppressingSectionChange() || this.mIsSuppressingEntryReorder)) {
            this.mNotifStabilityManager.invalidateList("reorder suppression ended for group=" + this.mIsSuppressingGroupChange + " section=" + isSuppressingSectionChange() + " sort=" + this.mIsSuppressingEntryReorder);
        }
        VisualStabilityProvider visualStabilityProvider = this.mVisualStabilityProvider;
        boolean z4 = this.mReorderingAllowed;
        if (visualStabilityProvider.isReorderingAllowed != z4) {
            visualStabilityProvider.isReorderingAllowed = z4;
            if (z4) {
                ListenerSet<HeadsUpManagerPhone$$ExternalSyntheticLambda0> listenerSet = visualStabilityProvider.allListeners;
                for (HeadsUpManagerPhone$$ExternalSyntheticLambda0 headsUpManagerPhone$$ExternalSyntheticLambda0 : listenerSet) {
                    if (visualStabilityProvider.temporaryListeners.remove(headsUpManagerPhone$$ExternalSyntheticLambda0)) {
                        listenerSet.remove(headsUpManagerPhone$$ExternalSyntheticLambda0);
                    }
                    HeadsUpManagerPhone headsUpManagerPhone = headsUpManagerPhone$$ExternalSyntheticLambda0.f$0;
                    headsUpManagerPhone.mAnimationStateHandler.f$0.mHeadsUpGoingAwayAnimationsAllowed = false;
                    ArraySet arraySet = headsUpManagerPhone.mEntriesToRemoveWhenReorderingAllowed;
                    arraySet.getClass();
                    ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                    while (elementIterator.hasNext()) {
                        NotificationEntry notificationEntry = (NotificationEntry) elementIterator.next();
                        if (headsUpManagerPhone.isHeadsUpEntry(notificationEntry.mKey)) {
                            headsUpManagerPhone.removeEntry(notificationEntry.mKey, "mOnReorderingAllowedListener");
                        }
                    }
                    arraySet.clear();
                    headsUpManagerPhone.mAnimationStateHandler.f$0.mHeadsUpGoingAwayAnimationsAllowed = true;
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mSleepy = this.mWakefulnessLifecycle.mWakefulness == 0;
        this.mFullyDozed = this.mStatusBarStateController.getDozeAmount() == 1.0f;
        this.mStatusBarStateController.addCallback(this.mStatusBarStateControllerListener);
        this.mPulsing = this.mStatusBarStateController.isPulsing();
        final int i = 0;
        this.mJavaAdapter.alwaysCollectFlow(this.mShadeAnimationInteractor.isAnyCloseAnimationRunning(), new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda1
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                switch (i2) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(booleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(booleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(booleanValue);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mJavaAdapter.alwaysCollectFlow(this.mShadeAnimationInteractor.isLaunchingActivity, new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda1
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                switch (i22) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(booleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(booleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(booleanValue);
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mJavaAdapter.alwaysCollectFlow(this.mCommunalInteractor.isIdleOnCommunal, new Consumer(this) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator$$ExternalSyntheticLambda1
            public final /* synthetic */ VisualStabilityCoordinator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                VisualStabilityCoordinator visualStabilityCoordinator = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                switch (i22) {
                    case 0:
                        visualStabilityCoordinator.onShadeOrQsClosingChanged(booleanValue);
                        break;
                    case 1:
                        visualStabilityCoordinator.onLaunchingActivityChanged(booleanValue);
                        break;
                    default:
                        visualStabilityCoordinator.onCommunalShowingChanged(booleanValue);
                        break;
                }
            }
        });
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
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("pipelineRunAllowed: "), this.mPipelineRunAllowed, printWriter, "  notifPanelCollapsing: "), this.mNotifPanelCollapsing, printWriter, "  launchingNotifActivity: "), this.mNotifPanelLaunchingActivity, printWriter, "reorderingAllowed: "), this.mReorderingAllowed, printWriter, "  sleepy: "), this.mSleepy, printWriter, "  fullyDozed: "), this.mFullyDozed, printWriter, "  panelExpanded: "), this.mPanelExpanded, printWriter, "  pulsing: "), this.mPulsing, printWriter, "  communalShowing: "), this.mCommunalShowing, printWriter, "isSuppressingPipelineRun: "), this.mIsSuppressingPipelineRun, printWriter, "isSuppressingGroupChange: "), this.mIsSuppressingGroupChange, printWriter, "isSuppressingEntryReorder: "), this.mIsSuppressingEntryReorder, printWriter, "entriesWithSuppressedSectionChange: ");
        m.append(this.mEntriesWithSuppressedSectionChange.size());
        printWriter.println(m.toString());
        Iterator<String> it = this.mEntriesWithSuppressedSectionChange.iterator();
        while (it.hasNext()) {
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "  ", it.next());
        }
        printWriter.println("entriesThatCanChangeSection: " + this.mEntriesThatCanChangeSection.size());
        Iterator<String> it2 = this.mEntriesThatCanChangeSection.keySet().iterator();
        while (it2.hasNext()) {
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "  ", it2.next());
        }
    }

    public void temporarilyAllowSectionChanges(NotificationEntry notificationEntry, long j) {
        String str = notificationEntry.mKey;
        boolean isSectionChangeAllowed = this.mNotifStabilityManager.isSectionChangeAllowed(notificationEntry);
        if (this.mEntriesThatCanChangeSection.containsKey(str)) {
            this.mEntriesThatCanChangeSection.get(str).run();
        }
        this.mEntriesThatCanChangeSection.put(str, this.mDelayableExecutor.executeAtTime(new VisualStabilityCoordinator$$ExternalSyntheticLambda0(this, str, 1), j + ALLOW_SECTION_CHANGE_TIMEOUT));
        if (isSectionChangeAllowed) {
            return;
        }
        this.mNotifStabilityManager.invalidateList("temporarilyAllowSectionChanges");
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
