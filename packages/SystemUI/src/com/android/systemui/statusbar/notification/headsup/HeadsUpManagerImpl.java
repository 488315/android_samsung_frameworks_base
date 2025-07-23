package com.android.systemui.statusbar.notification.headsup;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Region;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pools;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ExpandHeadsUpOnInlineReply;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HeadsUpManagerImpl implements HeadsUpManager, HeadsUpRepository, OnHeadsUpChangedListener {
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 mAnimationStateHandler;
    public final int mAutoDismissTime;
    public final AvalancheController mAvalancheController;
    public final KeyguardBypassController mBypassController;
    public final List mCallbacks;
    public final Context mContext;
    public final HashSet mEntriesToRemoveAfterExpand;
    final ArraySet<NotificationEntry> mEntriesToRemoveWhenReorderingAllowed;
    public final AnonymousClass1 mEntryPool;
    public final DelayableExecutor mExecutor;
    public final int mExtensionTime;
    public final GroupMembershipManager mGroupMembershipManager;
    public boolean mHasPinnedNotification;
    public final StateFlowImpl mHeadsUpAnimatingAway;
    final ArrayMap<String, HeadsUpEntry> mHeadsUpEntryMap;
    public int mHeadsUpInset;
    public final StateFlowImpl mHeadsUpNotificationRows;
    public final List mHeadsUpPhoneListeners;
    public boolean mIsShadeOrQsExpanded;
    public final ListenerSet mListeners = new ListenerSet();
    public final HeadsUpManagerLogger mLogger;
    public final int mMinimumDisplayTimeDefault;
    final OnReorderingAllowedListener mOnReorderingAllowedListener;
    public boolean mReleaseOnExpandFinish;
    public int mSnoozeLengthMs;
    public final ArrayMap mSnoozedPackages;
    public int mStatusBarState;
    public final AnonymousClass4 mStatusBarStateListener;
    public final int mStickyForSomeTimeAutoDismissTime;
    public final HashSet mSwipedOutKeys;
    public final SystemClock mSystemClock;
    public final StateFlowImpl mTopHeadsUpRow;
    public final int mTouchAcceptanceDelay;
    public final Region mTouchableRegion;
    public final StateFlowImpl mTrackingHeadsUp;
    public final UiEventLogger mUiEventLogger;
    public int mUser;
    public final VisualStabilityProvider mVisualStabilityProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HeadsUpEntry implements Comparable, HeadsUpRowRepository {
        public boolean extended;
        public Runnable mCancelRemoveRunnable;
        public long mEarliestRemovalTime;
        public final NotificationEntry mEntry;
        public boolean mExpanded;
        public boolean mGutsShownPinned;
        public final StateFlowImpl mPinnedStatus = StateFlowKt.MutableStateFlow(PinnedStatus.NotPinned);
        public long mPostTime;
        public boolean mRemoteInputActivatedAtLeastOnce;
        public boolean mRemoteInputActive;
        public final HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 mRemoveRunnable;
        public boolean mUserActionMayIndirectlyRemove;
        boolean mWasUnpinned;

        public HeadsUpEntry() {
            NotificationThrottleHun.assertInLegacyMode();
            throw null;
        }

        public final void cancelAutoRemovalCallbacks(String str) {
            HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 = new HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1(this, str, 1);
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null || !HeadsUpManagerImpl.this.isHeadsUpEntry(notificationEntry.mKey)) {
                headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1.run();
                return;
            }
            HeadsUpManagerLogger headsUpManagerLogger = HeadsUpManagerImpl.this.mLogger;
            NotificationEntry notificationEntry2 = this.mEntry;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            HeadsUpManagerImpl.this.mAvalancheController.update(this, headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1, str.concat(" cancelAutoRemovalCallbacks"));
        }

        public final int compareNonTimeFields(HeadsUpEntry headsUpEntry) {
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null && headsUpEntry.mEntry == null) {
                return 0;
            }
            if (headsUpEntry.mEntry == null) {
                return -1;
            }
            if (notificationEntry == null) {
                return 1;
            }
            HeadsUpManagerImpl.this.getClass();
            boolean hasFullScreenIntent = HeadsUpManagerImpl.hasFullScreenIntent(notificationEntry);
            HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
            NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
            headsUpManagerImpl.getClass();
            boolean hasFullScreenIntent2 = HeadsUpManagerImpl.hasFullScreenIntent(notificationEntry2);
            if (hasFullScreenIntent && !hasFullScreenIntent2) {
                return -1;
            }
            if (!hasFullScreenIntent && hasFullScreenIntent2) {
                return 1;
            }
            boolean m3064$$Nest$smisCriticalCallNotif = HeadsUpManagerImpl.m3064$$Nest$smisCriticalCallNotif(this.mEntry);
            boolean m3064$$Nest$smisCriticalCallNotif2 = HeadsUpManagerImpl.m3064$$Nest$smisCriticalCallNotif(headsUpEntry.mEntry);
            if (m3064$$Nest$smisCriticalCallNotif && !m3064$$Nest$smisCriticalCallNotif2) {
                return -1;
            }
            if (!m3064$$Nest$smisCriticalCallNotif && m3064$$Nest$smisCriticalCallNotif2) {
                return 1;
            }
            boolean z = this.mRemoteInputActive;
            if (!z || headsUpEntry.mRemoteInputActive) {
                return (z || !headsUpEntry.mRemoteInputActive) ? 0 : 1;
            }
            return -1;
        }

        public final boolean equals(Object obj) {
            NotificationEntry notificationEntry;
            if (this == obj) {
                return true;
            }
            if (obj instanceof HeadsUpEntry) {
                HeadsUpEntry headsUpEntry = (HeadsUpEntry) obj;
                NotificationEntry notificationEntry2 = this.mEntry;
                if (notificationEntry2 != null && (notificationEntry = headsUpEntry.mEntry) != null) {
                    return notificationEntry2.mKey.equals(notificationEntry.mKey);
                }
            }
            return false;
        }

        public final int hashCode() {
            NotificationEntry notificationEntry = this.mEntry;
            return notificationEntry == null ? super.hashCode() : notificationEntry.mKey.hashCode() * 31;
        }

        public final boolean isSticky() {
            if (this.mGutsShownPinned) {
                return true;
            }
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null) {
                return false;
            }
            int i = ExpandHeadsUpOnInlineReply.$r8$clinit;
            if (!this.mRemoteInputActive && this.mRemoteInputActivatedAtLeastOnce) {
                return false;
            }
            if (notificationEntry.isRowPinned() && this.mExpanded) {
                Notification notification2 = this.mEntry.mSbn.getNotification();
                PromotedNotificationContentModel.Companion.getClass();
                if (!notification2.isPromotedOngoing()) {
                    return true;
                }
            }
            if (this.mRemoteInputActive) {
                return true;
            }
            HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
            NotificationEntry notificationEntry2 = this.mEntry;
            headsUpManagerImpl.getClass();
            return HeadsUpManagerImpl.hasFullScreenIntent(notificationEntry2);
        }

        public final void scheduleAutoRemovalCallback(HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0 headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0, String str) {
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null) {
                Log.wtf("BaseHeadsUpManager", "#scheduleAutoRemovalCallback with null mEntry; returning early");
                return;
            }
            HeadsUpManagerLogger headsUpManagerLogger = HeadsUpManagerImpl.this.mLogger;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            HeadsUpManagerImpl.this.mAvalancheController.update(this, new HeadsUpManagerImpl$$ExternalSyntheticLambda0(this, headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0, str), str.concat(" scheduleAutoRemovalCallback"));
        }

        public void setRowPinnedStatus(PinnedStatus pinnedStatus) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry != null && (expandableNotificationRow = notificationEntry.row) != null) {
                int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
                boolean isAboveShelf = expandableNotificationRow.isAboveShelf();
                expandableNotificationRow.mPinnedStatus = pinnedStatus;
                if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight()) {
                    expandableNotificationRow.notifyHeightChanged(false);
                }
                if (pinnedStatus.isPinned()) {
                    expandableNotificationRow.setAnimationRunning(true);
                    expandableNotificationRow.mExpandedWhenPinned = false;
                    ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getClass();
                    expandableNotificationRow.applyHeadsUpBackground(NotificationColorPicker.isCustom(expandableNotificationRow));
                } else if (expandableNotificationRow.mExpandedWhenPinned) {
                    expandableNotificationRow.setUserExpanded(true, false);
                }
                expandableNotificationRow.setChronometerRunning(expandableNotificationRow.mLastChronometerRunning);
                if (expandableNotificationRow.isAboveShelf() != isAboveShelf) {
                    expandableNotificationRow.mAboveShelfChangedListener.onAboveShelfStateChanged(!isAboveShelf);
                }
                boolean isPinned = expandableNotificationRow.mPinnedStatus.isPinned();
                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
                notificationBackgroundView.mIsPinned = isPinned;
                notificationBackgroundView.invalidate();
            }
            this.mPinnedStatus.setValue(pinnedStatus);
        }

        public final void updateEntry(final String str, final boolean z, final boolean z2) {
            HeadsUpManagerImpl.this.mAvalancheController.update(this, new Runnable() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = HeadsUpManagerImpl.HeadsUpEntry.this;
                    boolean z3 = z;
                    String str2 = str;
                    boolean z4 = z2;
                    NotificationEntry notificationEntry = headsUpEntry.mEntry;
                    if (notificationEntry == null) {
                        Log.wtf("BaseHeadsUpManager", "#updateEntry called with null mEntry; returning early");
                        return;
                    }
                    HeadsUpManagerLogger headsUpManagerLogger = HeadsUpManagerImpl.this.mLogger;
                    headsUpManagerLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(17);
                    LogBuffer logBuffer = headsUpManagerLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                    logMessageImpl.bool1 = z3;
                    logMessageImpl.str2 = str2;
                    logBuffer.commit(obtain);
                    long elapsedRealtime = HeadsUpManagerImpl.this.mSystemClock.elapsedRealtime();
                    if (z4) {
                        int i = StatusBarNotifChips.$r8$clinit;
                        headsUpEntry.mEarliestRemovalTime = HeadsUpManagerImpl.this.mMinimumDisplayTimeDefault + elapsedRealtime;
                    }
                    if (z3) {
                        headsUpEntry.mPostTime = Math.max(headsUpEntry.mPostTime, elapsedRealtime);
                    }
                }
            }, FakeFeatures$$ExternalSyntheticOutline0.m("updateEntry reason:", str, " updatePostTime:", z));
            if (isSticky()) {
                cancelAutoRemovalCallbacks("updateEntry (sticky)");
                return;
            }
            scheduleAutoRemovalCallback(new HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0(this, 1), "updateEntry (not sticky)");
            HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
            headsUpManagerImpl.mTopHeadsUpRow.setValue(headsUpManagerImpl.getTopHeadsUpEntry());
            HeadsUpManagerImpl.this.mEntriesToRemoveAfterExpand.remove(this.mEntry);
            NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
        }

        @Override // java.lang.Comparable
        public final int compareTo(HeadsUpEntry headsUpEntry) {
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null && headsUpEntry.mEntry == null) {
                return 0;
            }
            if (headsUpEntry.mEntry == null) {
                return -1;
            }
            if (notificationEntry == null) {
                return 1;
            }
            boolean isRowPinned = notificationEntry.isRowPinned();
            boolean isRowPinned2 = headsUpEntry.mEntry.isRowPinned();
            if (isRowPinned && !isRowPinned2) {
                return -1;
            }
            if (!isRowPinned && isRowPinned2) {
                return 1;
            }
            int compareNonTimeFields = compareNonTimeFields(headsUpEntry);
            if (compareNonTimeFields != 0) {
                return compareNonTimeFields;
            }
            long j = this.mPostTime;
            long j2 = headsUpEntry.mPostTime;
            if (j > j2) {
                return -1;
            }
            if (j == j2) {
                return this.mEntry.mKey.compareTo(headsUpEntry.mEntry.mKey);
            }
            return 1;
        }

        public HeadsUpEntry(NotificationEntry notificationEntry) {
            HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 = new HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1(this, notificationEntry, 0);
            this.mEntry = notificationEntry;
            this.mRemoveRunnable = headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1;
            this.mPostTime = HeadsUpManagerImpl.this.mSystemClock.elapsedRealtime() + HeadsUpManagerImpl.this.mTouchAcceptanceDelay;
            updateEntry("setEntry", true, true);
            NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
            HeadsUpManagerImpl.this.mEntriesToRemoveWhenReorderingAllowed.add(notificationEntry);
            if (HeadsUpManagerImpl.this.mVisualStabilityProvider.isReorderingAllowed) {
                return;
            }
            notificationEntry.getClass();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum NotificationPeekEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_PEEK(801);

        private final int mId;

        NotificationPeekEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* renamed from: -$$Nest$smisCriticalCallNotif, reason: not valid java name */
    public static boolean m3064$$Nest$smisCriticalCallNotif(NotificationEntry notificationEntry) {
        Notification notification2 = notificationEntry.mSbn.getNotification();
        return (notification2.isStyle(Notification.CallStyle.class) && notification2.extras.getInt("android.callType") == 1) || (notificationEntry.mSbn.isOngoing() && "call".equals(notification2.category));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener, com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$4] */
    public HeadsUpManagerImpl(Context context, HeadsUpManagerLogger headsUpManagerLogger, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, final GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, AccessibilityManagerWrapper accessibilityManagerWrapper, UiEventLogger uiEventLogger, JavaAdapter javaAdapter, ShadeInteractor shadeInteractor, AvalancheController avalancheController) {
        PinnedStatus pinnedStatus = PinnedStatus.NotPinned;
        this.mHeadsUpPhoneListeners = new ArrayList();
        this.mHeadsUpEntryMap = new ArrayMap<>();
        this.mTopHeadsUpRow = StateFlowKt.MutableStateFlow(null);
        this.mHeadsUpNotificationRows = StateFlowKt.MutableStateFlow(new HashSet());
        Boolean bool = Boolean.FALSE;
        this.mHeadsUpAnimatingAway = StateFlowKt.MutableStateFlow(bool);
        this.mTrackingHeadsUp = StateFlowKt.MutableStateFlow(bool);
        this.mSwipedOutKeys = new HashSet();
        this.mEntriesToRemoveAfterExpand = new HashSet();
        this.mEntriesToRemoveWhenReorderingAllowed = new ArraySet<>();
        this.mTouchableRegion = new Region();
        this.mCallbacks = new ArrayList();
        this.mEntryPool = new Pools.Pool(this) { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.1
            {
                new Stack();
            }

            public final Object acquire() {
                NotificationThrottleHun.assertInLegacyMode();
                throw null;
            }

            public final boolean release(Object obj) {
                NotificationThrottleHun.assertInLegacyMode();
                throw null;
            }
        };
        OnReorderingAllowedListener onReorderingAllowedListener = new OnReorderingAllowedListener() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener
            public final void onReorderingAllowed() {
                HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
                NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
                AvalancheController avalancheController2 = headsUpManagerImpl.mAvalancheController;
                if (!avalancheController2.enableAtRuntime) {
                    avalancheController2.enableAtRuntime = true;
                }
                if (headsUpManagerImpl.mEntriesToRemoveWhenReorderingAllowed.isEmpty()) {
                    return;
                }
                ((NotificationStackScrollLayout) headsUpManagerImpl.mAnimationStateHandler.f$0).mHeadsUpGoingAwayAnimationsAllowed = false;
                Iterator<NotificationEntry> it = headsUpManagerImpl.mEntriesToRemoveWhenReorderingAllowed.iterator();
                while (it.hasNext()) {
                    NotificationEntry next = it.next();
                    if (next != null) {
                        String str = next.mKey;
                        if (headsUpManagerImpl.isHeadsUpEntry(str)) {
                            headsUpManagerImpl.removeEntry(str, "mOnReorderingAllowedListener");
                        }
                    }
                }
                headsUpManagerImpl.mEntriesToRemoveWhenReorderingAllowed.clear();
                ((NotificationStackScrollLayout) headsUpManagerImpl.mAnimationStateHandler.f$0).mHeadsUpGoingAwayAnimationsAllowed = true;
            }
        };
        this.mOnReorderingAllowedListener = onReorderingAllowedListener;
        HeadsUpManagerImpl$$ExternalSyntheticLambda4 headsUpManagerImpl$$ExternalSyntheticLambda4 = new HeadsUpManagerImpl$$ExternalSyntheticLambda4(this);
        ?? r3 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.4
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                if (z) {
                    return;
                }
                ArrayList arrayList = (ArrayList) HeadsUpManagerImpl.this.getHeadsUpEntryList();
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((HeadsUpEntry) obj).updateEntry("onDozingChanged(false)", true, true);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
                int i2 = 0;
                boolean z = headsUpManagerImpl.mStatusBarState == 1;
                boolean z2 = i == 1;
                headsUpManagerImpl.mStatusBarState = i;
                if (z && !z2 && headsUpManagerImpl.mBypassController.getBypassEnabled()) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = (ArrayList) headsUpManagerImpl.getHeadsUpEntryList();
                    int size = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList2.get(i3);
                        i3++;
                        HeadsUpEntry headsUpEntry = (HeadsUpEntry) obj;
                        NotificationEntry notificationEntry = headsUpEntry.mEntry;
                        if (notificationEntry != null && notificationEntry.isBubble() && !headsUpEntry.isSticky()) {
                            arrayList.add(headsUpEntry.mEntry.mKey);
                        }
                    }
                    int size2 = arrayList.size();
                    while (i2 < size2) {
                        Object obj2 = arrayList.get(i2);
                        i2++;
                        headsUpManagerImpl.removeEntry((String) obj2, "mStatusBarStateListener");
                    }
                }
            }
        };
        this.mStatusBarStateListener = r3;
        this.mLogger = headsUpManagerLogger;
        this.mExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        this.mContext = context;
        this.mAccessibilityMgr = accessibilityManagerWrapper;
        this.mUiEventLogger = uiEventLogger;
        this.mAvalancheController = avalancheController;
        avalancheController.baseEntryMapStr = new Function0() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
                if (headsUpManagerImpl.mHeadsUpEntryMap.isEmpty()) {
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (HeadsUpManagerImpl.HeadsUpEntry headsUpEntry : headsUpManagerImpl.mHeadsUpEntryMap.values()) {
                    sb.append("\n ");
                    NotificationEntry notificationEntry = headsUpEntry.mEntry;
                    sb.append(notificationEntry == null ? "null" : notificationEntry.mKey);
                }
                return sb.toString();
            }
        };
        this.mBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mVisualStabilityProvider = visualStabilityProvider;
        Resources resources = context.getResources();
        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
        this.mMinimumDisplayTimeDefault = resources.getInteger(R.integer.heads_up_notification_minimum_time_with_throttling);
        resources.getInteger(R.integer.heads_up_notification_minimum_time_for_user_initiated);
        this.mStickyForSomeTimeAutoDismissTime = resources.getInteger(R.integer.sticky_heads_up_notification_time);
        this.mAutoDismissTime = resources.getInteger(R.integer.heads_up_notification_decay);
        this.mExtensionTime = resources.getInteger(R.integer.ambient_notification_extension_time);
        this.mTouchAcceptanceDelay = resources.getInteger(R.integer.touch_acceptance_delay);
        this.mSnoozedPackages = new ArrayMap();
        this.mSnoozeLengthMs = globalSettings.getInt("heads_up_snooze_length_ms", resources.getInteger(R.integer.heads_up_default_snooze_length_ms));
        globalSettings.registerContentObserverSync(globalSettings.getUriFor("heads_up_snooze_length_ms"), false, new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                int i = globalSettings.getInt("heads_up_snooze_length_ms", -1);
                if (i > -1) {
                    HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
                    if (i != headsUpManagerImpl.mSnoozeLengthMs) {
                        headsUpManagerImpl.mSnoozeLengthMs = i;
                        HeadsUpManagerLogger headsUpManagerLogger2 = headsUpManagerImpl.mLogger;
                        headsUpManagerLogger2.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(11);
                        LogBuffer logBuffer = headsUpManagerLogger2.buffer;
                        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) obtain).int1 = i;
                        logBuffer.commit(obtain);
                    }
                }
            }
        });
        statusBarStateController.addCallback(r3);
        updateResources$2();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                HeadsUpManagerImpl.this.updateResources$2();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                HeadsUpManagerImpl.this.updateResources$2();
            }
        });
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                HeadsUpManagerImpl headsUpManagerImpl = HeadsUpManagerImpl.this;
                Boolean bool2 = (Boolean) obj;
                if (bool2.booleanValue() != headsUpManagerImpl.mIsShadeOrQsExpanded) {
                    headsUpManagerImpl.mIsShadeOrQsExpanded = bool2.booleanValue();
                    int i = SceneContainerFlag.$r8$clinit;
                    if (bool2.booleanValue()) {
                        headsUpManagerImpl.mHeadsUpAnimatingAway.updateState(null, Boolean.FALSE);
                    }
                }
            }
        });
        int i = SceneContainerFlag.$r8$clinit;
        visualStabilityProvider.banListeners.addIfAbsent(headsUpManagerImpl$$ExternalSyntheticLambda4);
        visualStabilityProvider.temporaryListeners.remove(onReorderingAllowedListener);
        visualStabilityProvider.allListeners.addIfAbsent(onReorderingAllowedListener);
    }

    public static boolean hasFullScreenIntent(NotificationEntry notificationEntry) {
        return (notificationEntry.mSbn.getNotification() == null || notificationEntry.mSbn.getNotification().fullScreenIntent == null) ? false : true;
    }

    public final void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
        this.mListeners.addIfAbsent(onHeadsUpChangedListener);
    }

    public final boolean canRemoveImmediately(String str) {
        NotificationEntry notificationEntry;
        if (this.mSwipedOutKeys.contains(str)) {
            this.mSwipedOutKeys.remove(str);
            return true;
        }
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        int i = SceneContainerFlag.$r8$clinit;
        return headsUpEntry == null || headsUpEntry != getTopHeadsUpEntry() || headsUpEntry.mUserActionMayIndirectlyRemove || headsUpEntry.mEarliestRemovalTime < HeadsUpManagerImpl.this.mSystemClock.elapsedRealtime() || ((notificationEntry = headsUpEntry.mEntry) != null && notificationEntry.isRowDismissed());
    }

    public HeadsUpEntry createHeadsUpEntry(NotificationEntry notificationEntry) {
        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
        return new HeadsUpEntry(notificationEntry);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("HeadsUpManager state:");
        printWriter.print("  mTouchAcceptanceDelay=");
        printWriter.println(this.mTouchAcceptanceDelay);
        printWriter.print("  mSnoozeLengthMs=");
        printWriter.println(this.mSnoozeLengthMs);
        printWriter.print("  now=");
        printWriter.println(this.mSystemClock.elapsedRealtime());
        printWriter.print("  mUser=");
        printWriter.println(this.mUser);
        Iterator<HeadsUpEntry> it = this.mHeadsUpEntryMap.values().iterator();
        while (it.hasNext()) {
            Object obj = it.next().mEntry;
            if (obj == null) {
                obj = "null";
            }
            printWriter.println(obj);
        }
        int size = this.mSnoozedPackages.size();
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  snoozed packages: ", size, printWriter);
        for (int i = 0; i < size; i++) {
            printWriter.print("    ");
            printWriter.print(this.mSnoozedPackages.valueAt(i));
            printWriter.print(", ");
            printWriter.println((String) this.mSnoozedPackages.keyAt(i));
        }
        printWriter.print("  mBarState=");
        printWriter.println(this.mStatusBarState);
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    public final Stream getAllEntries() {
        return getHeadsUpEntryList().stream().map(new HeadsUpManagerImpl$$ExternalSyntheticLambda7());
    }

    public final long getEarliestRemovalTime(String str) {
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry != null) {
            return Math.max(0L, headsUpEntry.mEarliestRemovalTime - this.mSystemClock.elapsedRealtime());
        }
        return 0L;
    }

    public HeadsUpEntry getHeadsUpEntry(String str) {
        return this.mHeadsUpEntryMap.containsKey(str) ? this.mHeadsUpEntryMap.get(str) : this.mAvalancheController.getWaitingEntry(str);
    }

    public final List getHeadsUpEntryList() {
        ArrayList arrayList = new ArrayList(this.mHeadsUpEntryMap.values());
        AvalancheController avalancheController = this.mAvalancheController;
        arrayList.addAll(!avalancheController.isEnabled() ? new ArrayList() : CollectionsKt___CollectionsKt.toList(((HashMap) avalancheController.nextMap).keySet()));
        return arrayList;
    }

    public final PinnedStatus getNewPinnedStatusForEntry(HeadsUpEntry headsUpEntry, PinnedStatus pinnedStatus) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        if (notificationEntry == null) {
            return PinnedStatus.NotPinned;
        }
        if (!shouldHeadsUpBecomePinned(notificationEntry)) {
            return PinnedStatus.NotPinned;
        }
        int i = StatusBarNotifChips.$r8$clinit;
        if (pinnedStatus != PinnedStatus.PinnedByUser) {
            return pinnedStatus;
        }
        Log.wtf("BaseHeadsUpManager", "PinnedStatus.PinnedByUser not allowed if StatusBarNotifChips flag off");
        return PinnedStatus.NotPinned;
    }

    public final HeadsUpEntry getTopHeadsUpEntry() {
        HeadsUpEntry headsUpEntry = null;
        if (this.mHeadsUpEntryMap.isEmpty()) {
            return null;
        }
        for (HeadsUpEntry headsUpEntry2 : this.mHeadsUpEntryMap.values()) {
            if (headsUpEntry == null || headsUpEntry2.compareTo(headsUpEntry) < 0) {
                headsUpEntry = headsUpEntry2;
            }
        }
        return headsUpEntry;
    }

    public final boolean hasNotifications() {
        if (!this.mHeadsUpEntryMap.isEmpty()) {
            return true;
        }
        AvalancheController avalancheController = this.mAvalancheController;
        return !(!avalancheController.isEnabled() ? new ArrayList() : CollectionsKt___CollectionsKt.toList(((HashMap) avalancheController.nextMap).keySet())).isEmpty();
    }

    public final boolean isHeadsUpEntry(String str) {
        return this.mHeadsUpEntryMap.containsKey(str) || this.mAvalancheController.isWaiting(str);
    }

    public void onEntryAdded(HeadsUpEntry headsUpEntry, PinnedStatus pinnedStatus) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        Objects.requireNonNull(notificationEntry);
        notificationEntry.setHeadsUp(true);
        setEntryPinned(headsUpEntry, getNewPinnedStatusForEntry(headsUpEntry, pinnedStatus), "onEntryAdded");
        EventLog.writeEvent(36001, notificationEntry.mKey, 1);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, true);
        }
        this.mTopHeadsUpRow.setValue(getTopHeadsUpEntry());
        this.mHeadsUpNotificationRows.updateState(null, new HashSet(this.mHeadsUpEntryMap.values()));
    }

    public void onEntryRemoved(HeadsUpEntry headsUpEntry, String str) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        Objects.requireNonNull(notificationEntry);
        notificationEntry.setHeadsUp(false);
        setEntryPinned(headsUpEntry, PinnedStatus.NotPinned, "onEntryRemoved");
        EventLog.writeEvent(36001, notificationEntry.mKey, 0);
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(notificationEntry);
        logBuffer.commit(obtain);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, false);
        }
        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
        this.mTopHeadsUpRow.setValue(getTopHeadsUpEntry());
        this.mHeadsUpNotificationRows.updateState(null, new HashSet(this.mHeadsUpEntryMap.values()));
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        if (notificationEntry2 == null) {
            return;
        }
        if (!str.equals("mOnReorderingAllowedListener")) {
            this.mEntriesToRemoveWhenReorderingAllowed.remove(notificationEntry2);
        }
        if (notificationEntry.mIsHeadsUpByBriefExpanding) {
            notificationEntry.mIsHeadsUpByBriefExpanding = false;
        }
    }

    public final void releaseAllImmediately() {
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        logBuffer.commit(logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null));
        ArraySet arraySet = new ArraySet(this.mHeadsUpEntryMap.keySet());
        List waitingKeys = this.mAvalancheController.getWaitingKeys();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            removeEntry((String) it.next(), "releaseAllImmediately (keysToRemove)");
        }
        ArrayList arrayList = (ArrayList) waitingKeys;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            removeEntry((String) obj, "releaseAllImmediately (waitingKeysToRemove)");
        }
    }

    public final void removeEntry(String str, String str2) {
        boolean z;
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        AvalancheController avalancheController = this.mAvalancheController;
        if (headsUpEntry == null) {
            headsUpEntry = avalancheController.getWaitingEntry(str);
            z = true;
        } else {
            z = false;
        }
        HeadsUpEntry headsUpEntry2 = headsUpEntry;
        boolean z2 = z;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda11 headsUpManagerLogger$$ExternalSyntheticLambda11 = new HeadsUpManagerLogger$$ExternalSyntheticLambda11(z2, 0);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda11, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.str2 = str2;
        logMessageImpl.bool1 = z2;
        logBuffer.commit(obtain);
        avalancheController.delete(headsUpEntry2, new HeadsUpManagerImpl$$ExternalSyntheticLambda1(this, str, str2, z2, headsUpEntry2), "removeEntry");
    }

    public final void removeListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
        this.mListeners.remove(onHeadsUpChangedListener);
    }

    public final boolean removeNotification(String str, String str2, boolean z) {
        AvalancheController avalancheController = this.mAvalancheController;
        boolean isWaiting = avalancheController.isWaiting(str);
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(20);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = isWaiting;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
        if (avalancheController.isWaiting(str)) {
            removeEntry(str, "removeNotification (isWaiting)");
            return true;
        }
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry == null) {
            LogMessage obtain2 = logBuffer.obtain("HeadsUpManager", logLevel, new HeadsUpManagerLogger$$ExternalSyntheticLambda0(21), null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
            logMessageImpl2.str1 = NotificationUtils.logKey(str);
            logMessageImpl2.str2 = str2;
            logBuffer.commit(obtain2);
            return true;
        }
        if (z) {
            removeEntry(str, "removeNotification (releaseImmediately)");
            return true;
        }
        if (canRemoveImmediately(str)) {
            removeEntry(str, "removeNotification (canRemoveImmediately)");
            return true;
        }
        if (headsUpEntry.mRemoveRunnable == null) {
            return false;
        }
        headsUpEntry.scheduleAutoRemovalCallback(new HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0(headsUpEntry, 0), "removeAsSoonAsPossible");
        return false;
    }

    public final void setEntryPinned(HeadsUpEntry headsUpEntry, PinnedStatus pinnedStatus, String str) {
        boolean z;
        PinnedStatus pinnedStatus2;
        NotificationEntry notificationEntry;
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        Objects.requireNonNull(notificationEntry2);
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(10);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry2);
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = pinnedStatus.name();
        logBuffer.commit(obtain);
        boolean isPinned = pinnedStatus.isPinned();
        if (!isPinned) {
            headsUpEntry.mWasUnpinned = true;
        }
        if (headsUpEntry.mPinnedStatus.getValue() != pinnedStatus) {
            headsUpEntry.setRowPinnedStatus(pinnedStatus);
            Iterator<String> it = this.mHeadsUpEntryMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                HeadsUpEntry headsUpEntry2 = getHeadsUpEntry(it.next());
                if (headsUpEntry2 != null && (notificationEntry = headsUpEntry2.mEntry) != null && notificationEntry.isRowPinned()) {
                    z = true;
                    break;
                }
            }
            Iterator<String> it2 = this.mHeadsUpEntryMap.keySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    pinnedStatus2 = PinnedStatus.NotPinned;
                    break;
                }
                HeadsUpEntry headsUpEntry3 = getHeadsUpEntry(it2.next());
                NotificationEntry notificationEntry3 = headsUpEntry3.mEntry;
                if (notificationEntry3 != null && notificationEntry3.isRowPinned()) {
                    ExpandableNotificationRow expandableNotificationRow = headsUpEntry3.mEntry.row;
                    pinnedStatus2 = expandableNotificationRow != null ? expandableNotificationRow.mPinnedStatus : PinnedStatus.NotPinned;
                }
            }
            boolean z2 = this.mHasPinnedNotification;
            ListenerSet listenerSet = this.mListeners;
            if (z != z2) {
                LogMessage obtain2 = logBuffer.obtain("HeadsUpManager", LogLevel.INFO, new HeadsUpManagerLogger$$ExternalSyntheticLambda0(5), null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.bool1 = z;
                logMessageImpl2.str1 = pinnedStatus2.name();
                logBuffer.commit(obtain2);
                this.mHasPinnedNotification = z;
                if (z) {
                    MetricsLogger.count(this.mContext, "note_peek", 1);
                }
                Iterator it3 = listenerSet.iterator();
                while (it3.hasNext()) {
                    ((OnHeadsUpChangedListener) it3.next()).onHeadsUpPinnedModeChanged(z);
                }
            }
            if (isPinned) {
                this.mUiEventLogger.logWithInstanceId(NotificationPeekEvent.NOTIFICATION_PEEK, notificationEntry2.mSbn.getUid(), notificationEntry2.mSbn.getPackageName(), notificationEntry2.mSbn.getInstanceId());
            }
            Iterator it4 = listenerSet.iterator();
            while (it4.hasNext()) {
                OnHeadsUpChangedListener onHeadsUpChangedListener = (OnHeadsUpChangedListener) it4.next();
                if (isPinned) {
                    onHeadsUpChangedListener.onHeadsUpPinned(notificationEntry2);
                } else {
                    onHeadsUpChangedListener.onHeadsUpUnPinned(notificationEntry2);
                }
            }
        }
    }

    public final void setGutsShown(NotificationEntry notificationEntry, boolean z) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry == null) {
            return;
        }
        if ((notificationEntry.isRowPinned() || !z) && headsUpEntry.mGutsShownPinned != z) {
            headsUpEntry.mGutsShownPinned = z;
            if (z) {
                headsUpEntry.cancelAutoRemovalCallbacks("setGutsShownPinned(true)");
            } else {
                headsUpEntry.updateEntry("setGutsShownPinned(false)", false, true);
            }
        }
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        StateFlowImpl stateFlowImpl = this.mHeadsUpAnimatingAway;
        if (z != ((Boolean) stateFlowImpl.getValue()).booleanValue()) {
            ArrayList arrayList = (ArrayList) this.mHeadsUpPhoneListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                final ShadeTouchableRegionManager shadeTouchableRegionManager = ((ShadeTouchableRegionManager$$ExternalSyntheticLambda0) obj).f$0;
                if (z) {
                    shadeTouchableRegionManager.updateTouchableRegion();
                } else {
                    View view = shadeTouchableRegionManager.mNotificationPanelView;
                    if (view != null) {
                        shadeTouchableRegionManager.mForceCollapsedUntilLayout = true;
                        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager.3
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                                if (ShadeTouchableRegionManager.this.mNotificationPanelView.isVisibleToUser()) {
                                    return;
                                }
                                ShadeTouchableRegionManager.this.mNotificationPanelView.removeOnLayoutChangeListener(this);
                                ShadeTouchableRegionManager shadeTouchableRegionManager2 = ShadeTouchableRegionManager.this;
                                shadeTouchableRegionManager2.mForceCollapsedUntilLayout = false;
                                shadeTouchableRegionManager2.updateTouchableRegion();
                            }
                        });
                    }
                }
            }
            stateFlowImpl.updateState(null, Boolean.valueOf(z));
        }
    }

    public final void setRemoteInputActive(NotificationEntry notificationEntry, boolean z) {
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(notificationEntry.mKey);
        if (headsUpEntry == null || headsUpEntry.mRemoteInputActive == z) {
            return;
        }
        headsUpEntry.mRemoteInputActive = z;
        int i = ExpandHeadsUpOnInlineReply.$r8$clinit;
        if (z) {
            headsUpEntry.mRemoteInputActivatedAtLeastOnce = true;
        }
        if (z) {
            headsUpEntry.cancelAutoRemovalCallbacks("setRemoteInputActive(true)");
        } else {
            headsUpEntry.updateEntry("setRemoteInputActive(false)", false, true);
        }
        this.mTopHeadsUpRow.setValue(getTopHeadsUpEntry());
    }

    public boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        if (notificationEntry != null) {
            boolean z = this.mStatusBarState == 0 && !this.mIsShadeOrQsExpanded;
            int i = SceneContainerFlag.$r8$clinit;
            if (this.mBypassController.getBypassEnabled()) {
                z |= this.mStatusBarState == 1;
            }
            if (!z) {
                HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
                if (headsUpEntry == null) {
                    return hasFullScreenIntent(notificationEntry);
                }
                if (!hasFullScreenIntent(notificationEntry) || headsUpEntry.mWasUnpinned) {
                }
            }
            return true;
        }
        return false;
    }

    public final void snooze() {
        NotificationEntry notificationEntry;
        ArrayList arrayList = new ArrayList(this.mHeadsUpEntryMap.keySet());
        arrayList.addAll(this.mAvalancheController.getWaitingKeys());
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            HeadsUpEntry headsUpEntry = getHeadsUpEntry((String) obj);
            if (headsUpEntry != null && (notificationEntry = headsUpEntry.mEntry) != null) {
                String packageName = notificationEntry.mSbn.getPackageName();
                String str = this.mUser + "," + packageName;
                HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
                headsUpManagerLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = str;
                logBuffer.commit(obtain);
                this.mSnoozedPackages.put(str, Long.valueOf(this.mSystemClock.elapsedRealtime() + this.mSnoozeLengthMs));
            }
        }
        this.mReleaseOnExpandFinish = true;
    }

    public final void unpinAll() {
        for (String str : this.mHeadsUpEntryMap.keySet()) {
            HeadsUpEntry headsUpEntry = getHeadsUpEntry(str);
            if (headsUpEntry == null) {
                Log.wtf("BaseHeadsUpManager", "Couldn't find entry " + str + " in unpinAll");
            } else {
                HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
                headsUpManagerLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(27);
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(str);
                logBuffer.commit(obtain);
                this.mAvalancheController.delete(headsUpEntry, new HeadsUpManagerImpl$$ExternalSyntheticLambda0(this, str, headsUpEntry), "unpinAll");
            }
        }
    }

    public final void updateNotification(String str, PinnedStatus pinnedStatus) {
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        boolean z = headsUpEntry != null;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(26);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = pinnedStatus.name();
        logBuffer.commit(obtain);
        this.mAvalancheController.update(headsUpEntry, new HeadsUpManagerImpl$$ExternalSyntheticLambda0(this, str, pinnedStatus), "updateNotification");
    }

    public final void updateNotificationInternal(String str, PinnedStatus pinnedStatus) {
        ExpandableNotificationRow expandableNotificationRow;
        HeadsUpEntry headsUpEntry = this.mHeadsUpEntryMap.get(str);
        boolean z = headsUpEntry != null;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(22);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = pinnedStatus.name();
        logBuffer.commit(obtain);
        if (headsUpEntry == null) {
            return;
        }
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        if (notificationEntry != null && (expandableNotificationRow = notificationEntry.row) != null) {
            expandableNotificationRow.sendAccessibilityEvent(2048);
        }
        if (pinnedStatus.isPinned()) {
            headsUpEntry.updateEntry("updateNotification", true, true);
            setEntryPinned(headsUpEntry, getNewPinnedStatusForEntry(headsUpEntry, pinnedStatus), "updateNotificationInternal");
        }
    }

    public final void updateResources$2() {
        Resources resources = this.mContext.getResources();
        this.mHeadsUpInset = resources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(this.mContext);
    }
}
