package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Region;
import android.os.Handler;
import android.util.Pools;
import android.view.View;
import androidx.collection.ArraySet;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda13;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.AvalancheController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpManagerPhone extends BaseHeadsUpManager implements HeadsUpRepository, OnHeadsUpChangedListener {
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda13 mAnimationStateHandler;
    public final KeyguardBypassController mBypassController;
    public final HashSet mEntriesToRemoveAfterExpand;
    public final ArraySet mEntriesToRemoveWhenReorderingAllowed;
    public final AnonymousClass1 mEntryPool;
    public final int mExtensionTime;
    public final GroupMembershipManager mGroupMembershipManager;
    public final StateFlowImpl mHeadsUpAnimatingAway;
    public int mHeadsUpInset;
    public final StateFlowImpl mHeadsUpNotificationRows;
    public final List mHeadsUpPhoneListeners;
    public boolean mIsExpanded;
    public final HeadsUpManagerPhone$$ExternalSyntheticLambda0 mOnReorderingAllowedListener;
    public boolean mReleaseOnExpandFinish;
    public int mStatusBarState;
    public final AnonymousClass3 mStatusBarStateListener;
    public final HashSet mSwipedOutKeys;
    public final StateFlowImpl mTopHeadsUpRow;
    public final Region mTouchableRegion;
    public boolean mTrackingHeadsUp;
    public final VisualStabilityProvider mVisualStabilityProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.HeadsUpManagerPhone$1, reason: invalid class name */
    public final class AnonymousClass1 implements Pools.Pool {
        public final Stack mPoolObjects = new Stack();

        public AnonymousClass1() {
        }

        public final Object acquire() {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            return !this.mPoolObjects.isEmpty() ? (HeadsUpEntryPhone) this.mPoolObjects.pop() : HeadsUpManagerPhone.this.new HeadsUpEntryPhone();
        }

        public final boolean release(Object obj) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            this.mPoolObjects.push((HeadsUpEntryPhone) obj);
            return true;
        }
    }

    public HeadsUpManagerPhone(Context context, HeadsUpManagerLogger headsUpManagerLogger, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, AccessibilityManagerWrapper accessibilityManagerWrapper, UiEventLogger uiEventLogger, JavaAdapter javaAdapter, ShadeInteractor shadeInteractor, AvalancheController avalancheController) {
        super(context, headsUpManagerLogger, handler, globalSettings, systemClock, delayableExecutor, accessibilityManagerWrapper, uiEventLogger, avalancheController);
        this.mHeadsUpPhoneListeners = new ArrayList();
        this.mTopHeadsUpRow = StateFlowKt.MutableStateFlow(null);
        this.mHeadsUpNotificationRows = StateFlowKt.MutableStateFlow(new HashSet());
        this.mHeadsUpAnimatingAway = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.mSwipedOutKeys = new HashSet();
        this.mEntriesToRemoveAfterExpand = new HashSet();
        this.mEntriesToRemoveWhenReorderingAllowed = new ArraySet();
        this.mTouchableRegion = new Region();
        this.mEntryPool = new AnonymousClass1();
        this.mOnReorderingAllowedListener = new HeadsUpManagerPhone$$ExternalSyntheticLambda0(this);
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.3
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                if (z) {
                    return;
                }
                Iterator it = ((ArrayList) HeadsUpManagerPhone.this.getHeadsUpEntryList()).iterator();
                while (it.hasNext()) {
                    ((BaseHeadsUpManager.HeadsUpEntry) it.next()).updateEntry("onDozingChanged(false)", true);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                HeadsUpManagerPhone headsUpManagerPhone = HeadsUpManagerPhone.this;
                boolean z = headsUpManagerPhone.mStatusBarState == 1;
                boolean z2 = i == 1;
                headsUpManagerPhone.mStatusBarState = i;
                if (z && !z2 && headsUpManagerPhone.mBypassController.getBypassEnabled()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((ArrayList) headsUpManagerPhone.getHeadsUpEntryList()).iterator();
                    while (it.hasNext()) {
                        BaseHeadsUpManager.HeadsUpEntry headsUpEntry = (BaseHeadsUpManager.HeadsUpEntry) it.next();
                        NotificationEntry notificationEntry = headsUpEntry.mEntry;
                        if (notificationEntry != null && notificationEntry.isBubble() && !headsUpEntry.isSticky()) {
                            arrayList.add(headsUpEntry.mEntry.mKey);
                        }
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        headsUpManagerPhone.removeEntry((String) it2.next(), "mStatusBarStateListener");
                    }
                }
            }
        };
        this.mExtensionTime = this.mContext.getResources().getInteger(R.integer.ambient_notification_extension_time);
        statusBarStateController.addCallback(stateListener);
        this.mBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mVisualStabilityProvider = visualStabilityProvider;
        updateResources$2$1();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                HeadsUpManagerPhone.this.updateResources$2$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                HeadsUpManagerPhone.this.updateResources$2$1();
            }
        });
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                HeadsUpManagerPhone headsUpManagerPhone = HeadsUpManagerPhone.this;
                Boolean bool = (Boolean) obj;
                headsUpManagerPhone.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                if (bool.booleanValue() != headsUpManagerPhone.mIsExpanded) {
                    headsUpManagerPhone.mIsExpanded = bool.booleanValue();
                    if (bool.booleanValue()) {
                        headsUpManagerPhone.mHeadsUpAnimatingAway.updateState(null, Boolean.FALSE);
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager, com.android.systemui.statusbar.policy.HeadsUpManager
    public final boolean canRemoveImmediately(String str) {
        if (this.mSwipedOutKeys.contains(str)) {
            this.mSwipedOutKeys.remove(str);
            return true;
        }
        HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) this.mHeadsUpEntryMap.get(str);
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        return headsUpEntryPhone == null || headsUpEntryPhone != ((HeadsUpEntryPhone) getTopHeadsUpEntry()) || super.canRemoveImmediately(str);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final BaseHeadsUpManager.HeadsUpEntry createHeadsUpEntry(NotificationEntry notificationEntry) {
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) this.mEntryPool.acquire();
        headsUpEntryPhone.getClass();
        NotificationsHeadsUpRefactor.assertInLegacyMode();
        HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0 headsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0 = new HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0(headsUpEntryPhone, notificationEntry);
        headsUpEntryPhone.mEntry = notificationEntry;
        headsUpEntryPhone.mRemoveRunnable = headsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0;
        headsUpEntryPhone.mPostTime = BaseHeadsUpManager.this.mSystemClock.elapsedRealtime() + BaseHeadsUpManager.this.mTouchAcceptanceDelay;
        headsUpEntryPhone.updateEntry("setEntry", true);
        return headsUpEntryPhone;
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("HeadsUpManagerPhone state:");
        dumpInternal(printWriter, strArr);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final void dumpInternal(PrintWriter printWriter, String[] strArr) {
        super.dumpInternal(printWriter, strArr);
        printWriter.print("  mBarState=");
        printWriter.println(this.mStatusBarState);
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager, com.android.systemui.statusbar.policy.HeadsUpManager
    public final boolean isTrackingHeadsUp() {
        return this.mTrackingHeadsUp;
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final void onEntryAdded(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        super.onEntryAdded(headsUpEntry);
        updateTopHeadsUpFlow();
        this.mHeadsUpNotificationRows.updateState(null, new HashSet(this.mHeadsUpEntryMap.values()));
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final void onEntryRemoved(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        super.onEntryRemoved(headsUpEntry);
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        this.mEntryPool.release((HeadsUpEntryPhone) headsUpEntry);
        updateTopHeadsUpFlow();
        this.mHeadsUpNotificationRows.updateState(null, new HashSet(this.mHeadsUpEntryMap.values()));
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final void onEntryUpdated$1() {
        updateTopHeadsUpFlow();
    }

    public final void setGutsShown(NotificationEntry notificationEntry, boolean z) {
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry instanceof HeadsUpEntryPhone) {
            HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) headsUpEntry;
            if ((notificationEntry.isRowPinned() || !z) && headsUpEntryPhone.mGutsShownPinned != z) {
                headsUpEntryPhone.mGutsShownPinned = z;
                if (z) {
                    headsUpEntryPhone.cancelAutoRemovalCallbacks("setGutsShownPinned(true)");
                } else {
                    headsUpEntryPhone.updateEntry("setGutsShownPinned(false)", false);
                }
            }
        }
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        StateFlowImpl stateFlowImpl = this.mHeadsUpAnimatingAway;
        if (z != ((Boolean) stateFlowImpl.getValue()).booleanValue()) {
            Iterator it = ((ArrayList) this.mHeadsUpPhoneListeners).iterator();
            while (it.hasNext()) {
                final StatusBarTouchableRegionManager statusBarTouchableRegionManager = ((StatusBarTouchableRegionManager$$ExternalSyntheticLambda0) it.next()).f$0;
                if (z) {
                    statusBarTouchableRegionManager.updateTouchableRegion();
                } else {
                    View view = statusBarTouchableRegionManager.mNotificationPanelView;
                    if (view != null) {
                        statusBarTouchableRegionManager.mForceCollapsedUntilLayout = true;
                        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager.3
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                if (StatusBarTouchableRegionManager.this.mNotificationPanelView.isVisibleToUser()) {
                                    return;
                                }
                                StatusBarTouchableRegionManager.this.mNotificationPanelView.removeOnLayoutChangeListener(this);
                                StatusBarTouchableRegionManager statusBarTouchableRegionManager2 = StatusBarTouchableRegionManager.this;
                                statusBarTouchableRegionManager2.mForceCollapsedUntilLayout = false;
                                statusBarTouchableRegionManager2.updateTouchableRegion();
                            }
                        });
                    }
                }
            }
            stateFlowImpl.updateState(null, Boolean.valueOf(z));
        }
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    public final boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        boolean z = this.mStatusBarState == 0 && !this.mIsExpanded;
        if (this.mBypassController.getBypassEnabled()) {
            z |= this.mStatusBarState == 1;
        }
        return z || super.shouldHeadsUpBecomePinned(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager, com.android.systemui.statusbar.policy.HeadsUpManager
    public final void snooze() {
        super.snooze();
        this.mReleaseOnExpandFinish = true;
    }

    public final void updateResources$2$1() {
        Resources resources = this.mContext.getResources();
        this.mHeadsUpInset = resources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(this.mContext);
    }

    public final void updateTopHeadsUpFlow() {
        this.mTopHeadsUpRow.setValue((HeadsUpRowRepository) getTopHeadsUpEntry());
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HeadsUpEntryPhone extends BaseHeadsUpManager.HeadsUpEntry implements HeadsUpRowRepository {
        public boolean extended;
        public boolean mGutsShownPinned;
        public final StateFlowImpl mIsPinned;

        public HeadsUpEntryPhone() {
            super();
            this.mIsPinned = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final long calculateFinishTime() {
            return super.calculateFinishTime() + (this.extended ? HeadsUpManagerPhone.this.mExtensionTime : 0);
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final Runnable createRemoveRunnable(NotificationEntry notificationEntry) {
            return new HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0(this, notificationEntry);
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final boolean isSticky() {
            return super.isSticky() || this.mGutsShownPinned;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final void reset() {
            super.reset();
            this.mGutsShownPinned = false;
            this.extended = false;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final void setExpanded(boolean z) {
            if (this.mExpanded == z) {
                return;
            }
            this.mExpanded = z;
            if (z) {
                cancelAutoRemovalCallbacks("setExpanded(true)");
            } else {
                updateEntry("setExpanded(false)", false);
            }
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final void setRowPinned(boolean z) {
            super.setRowPinned(z);
            this.mIsPinned.updateState(null, Boolean.valueOf(z));
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public final void updateEntry(String str, boolean z) {
            updateEntry$1(str, z);
            if (HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.remove(this.mEntry);
            }
            if (HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.remove(this.mEntry);
            }
        }

        public HeadsUpEntryPhone(NotificationEntry notificationEntry) {
            super(notificationEntry);
            this.mIsPinned = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        }
    }
}
