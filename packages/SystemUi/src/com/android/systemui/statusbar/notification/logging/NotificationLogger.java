package com.android.systemui.statusbar.notification.logging;

import android.os.Handler;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeFullExpansionListener;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.google.protobuf.nano.MessageNano;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationLogger implements StatusBarStateController.StateListener {
    public final ExpansionStateLogger mExpansionStateLogger;
    public long mLastVisibilityReportUptimeMs;
    public NotificationListContainer mListContainer;
    public final NotifLiveDataStore mNotifLiveDataStore;
    public final NotificationListener mNotificationListener;
    public final NotificationPanelLogger mNotificationPanelLogger;
    public final Executor mUiBgExecutor;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final ArraySet mCurrentlyVisibleNotifications = new ArraySet();
    public final Handler mHandler = new Handler();
    public final Object mDozingLock = new Object();
    public Boolean mDozing = null;
    public Boolean mLockscreen = null;
    public Boolean mPanelExpanded = null;
    public boolean mLogging = false;
    public Runnable mVisibilityReporter = new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger.1
        public final ArraySet mTmpNewlyVisibleNotifications = new ArraySet();
        public final ArraySet mTmpCurrentlyVisibleNotifications = new ArraySet();
        public final ArraySet mTmpNoLongerVisibleNotifications = new ArraySet();

        @Override // java.lang.Runnable
        public final void run() {
            NotificationLogger.this.mLastVisibilityReportUptimeMs = SystemClock.uptimeMillis();
            List list = (List) ((NotifLiveDataStoreImpl) NotificationLogger.this.mNotifLiveDataStore).activeNotifList.getValue();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NotificationEntry notificationEntry = (NotificationEntry) list.get(i);
                String key = notificationEntry.mSbn.getKey();
                NotificationStackScrollLayoutController.this.getClass();
                boolean isInVisibleLocation = NotificationStackScrollLayoutController.isInVisibleLocation(notificationEntry);
                NotificationVisibility obtain = NotificationVisibility.obtain(key, i, size, isInVisibleLocation, NotificationLogger.getNotificationLocation(notificationEntry));
                boolean contains = NotificationLogger.this.mCurrentlyVisibleNotifications.contains(obtain);
                if (isInVisibleLocation) {
                    this.mTmpCurrentlyVisibleNotifications.add(obtain);
                    if (!contains) {
                        this.mTmpNewlyVisibleNotifications.add(obtain);
                    }
                } else {
                    obtain.recycle();
                }
            }
            this.mTmpNoLongerVisibleNotifications.addAll(NotificationLogger.this.mCurrentlyVisibleNotifications);
            this.mTmpNoLongerVisibleNotifications.removeAll(this.mTmpCurrentlyVisibleNotifications);
            NotificationLogger.this.logNotificationVisibilityChanges(this.mTmpNewlyVisibleNotifications, this.mTmpNoLongerVisibleNotifications);
            NotificationLogger.recycleAllVisibilityObjects(NotificationLogger.this.mCurrentlyVisibleNotifications);
            NotificationLogger.this.mCurrentlyVisibleNotifications.addAll(this.mTmpCurrentlyVisibleNotifications);
            ExpansionStateLogger expansionStateLogger = NotificationLogger.this.mExpansionStateLogger;
            ArraySet arraySet = this.mTmpCurrentlyVisibleNotifications;
            expansionStateLogger.onVisibilityChanged(arraySet, arraySet);
            Trace.traceCounter(4096L, "Notifications [Active]", size);
            Trace.traceCounter(4096L, "Notifications [Visible]", NotificationLogger.this.mCurrentlyVisibleNotifications.size());
            NotificationLogger notificationLogger = NotificationLogger.this;
            ArraySet arraySet2 = this.mTmpNoLongerVisibleNotifications;
            notificationLogger.getClass();
            NotificationLogger.recycleAllVisibilityObjects(arraySet2);
            this.mTmpCurrentlyVisibleNotifications.clear();
            this.mTmpNewlyVisibleNotifications.clear();
            this.mTmpNoLongerVisibleNotifications.clear();
        }
    };
    public final IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExpansionStateLogger {
        public final Executor mUiBgExecutor;
        public final Map mExpansionStates = new ArrayMap();
        public final Map mLoggedExpansionState = new ArrayMap();
        IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class State {
            public Boolean mIsExpanded;
            public Boolean mIsUserAction;
            public Boolean mIsVisible;
            public NotificationVisibility.NotificationLocation mLocation;

            private State() {
            }

            public /* synthetic */ State(int i) {
                this();
            }

            public /* synthetic */ State(State state, int i) {
                this(state);
            }

            private State(State state) {
                this.mIsUserAction = state.mIsUserAction;
                this.mIsExpanded = state.mIsExpanded;
                this.mIsVisible = state.mIsVisible;
                this.mLocation = state.mLocation;
            }
        }

        public ExpansionStateLogger(Executor executor) {
            this.mUiBgExecutor = executor;
        }

        public final State getState(String str) {
            ArrayMap arrayMap = (ArrayMap) this.mExpansionStates;
            State state = (State) arrayMap.get(str);
            if (state != null) {
                return state;
            }
            State state2 = new State(0);
            arrayMap.put(str, state2);
            return state2;
        }

        public final void maybeNotifyOnNotificationExpansionChanged(String str, State state) {
            int i = 0;
            int i2 = 1;
            if (((state.mIsUserAction == null || state.mIsExpanded == null || state.mIsVisible == null || state.mLocation == null) ? false : true) && state.mIsVisible.booleanValue()) {
                ArrayMap arrayMap = (ArrayMap) this.mLoggedExpansionState;
                Boolean bool = (Boolean) arrayMap.get(str);
                if (bool != null || state.mIsExpanded.booleanValue()) {
                    if (bool == null || !Objects.equals(state.mIsExpanded, bool)) {
                        arrayMap.put(str, state.mIsExpanded);
                        this.mUiBgExecutor.execute(new NotificationLogger$$ExternalSyntheticLambda1(this, str, new State(state, i), i2));
                    }
                }
            }
        }

        public void onEntryRemoved(String str) {
            ((ArrayMap) this.mExpansionStates).remove(str);
            ((ArrayMap) this.mLoggedExpansionState).remove(str);
        }

        public void onEntryUpdated(String str) {
            ((ArrayMap) this.mLoggedExpansionState).remove(str);
        }

        public void onExpansionChanged(String str, boolean z, boolean z2, NotificationVisibility.NotificationLocation notificationLocation) {
            State state = getState(str);
            state.mIsUserAction = Boolean.valueOf(z);
            state.mIsExpanded = Boolean.valueOf(z2);
            state.mLocation = notificationLocation;
            maybeNotifyOnNotificationExpansionChanged(str, state);
        }

        public void onVisibilityChanged(Collection<NotificationVisibility> collection, Collection<NotificationVisibility> collection2) {
            NotificationVisibility[] cloneVisibilitiesAsArr = NotificationLogger.cloneVisibilitiesAsArr(collection);
            NotificationVisibility[] cloneVisibilitiesAsArr2 = NotificationLogger.cloneVisibilitiesAsArr(collection2);
            for (NotificationVisibility notificationVisibility : cloneVisibilitiesAsArr) {
                State state = getState(notificationVisibility.key);
                state.mIsVisible = Boolean.TRUE;
                state.mLocation = notificationVisibility.location;
                maybeNotifyOnNotificationExpansionChanged(notificationVisibility.key, state);
            }
            for (NotificationVisibility notificationVisibility2 : cloneVisibilitiesAsArr2) {
                getState(notificationVisibility2.key).mIsVisible = Boolean.FALSE;
            }
        }
    }

    public NotificationLogger(NotificationListener notificationListener, Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
        this.mNotificationListener = notificationListener;
        this.mUiBgExecutor = executor;
        this.mNotifLiveDataStore = notifLiveDataStore;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mExpansionStateLogger = expansionStateLogger;
        this.mNotificationPanelLogger = notificationPanelLogger;
        statusBarStateController.addCallback(this);
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda0
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                NotificationLogger.this.onShadeExpansionFullyChanged(Boolean.valueOf(z));
            }
        });
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger.2
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                NotificationLogger.this.mExpansionStateLogger.onEntryRemoved(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
                NotificationLogger.this.mExpansionStateLogger.onEntryUpdated(notificationEntry.mKey);
            }
        });
    }

    public static NotificationVisibility[] cloneVisibilitiesAsArr(Collection collection) {
        NotificationVisibility[] notificationVisibilityArr = new NotificationVisibility[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            NotificationVisibility notificationVisibility = (NotificationVisibility) it.next();
            if (notificationVisibility != null) {
                notificationVisibilityArr[i] = notificationVisibility.clone();
            }
            i++;
        }
        return notificationVisibilityArr;
    }

    public static NotificationVisibility.NotificationLocation getNotificationLocation(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableViewState expandableViewState;
        if (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null || (expandableViewState = expandableNotificationRow.mViewState) == null) {
            return NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN;
        }
        int i = expandableViewState.location;
        return i != 1 ? i != 2 ? i != 4 ? i != 8 ? i != 16 ? i != 64 ? NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN : NotificationVisibility.NotificationLocation.LOCATION_GONE : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_HIDDEN : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_PEEKING : NotificationVisibility.NotificationLocation.LOCATION_MAIN_AREA : NotificationVisibility.NotificationLocation.LOCATION_HIDDEN_TOP : NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP;
    }

    public static void recycleAllVisibilityObjects(ArraySet arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            ((NotificationVisibility) arraySet.valueAt(i)).recycle();
        }
        arraySet.clear();
    }

    public Runnable getVisibilityReporter() {
        return this.mVisibilityReporter;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.internal.statusbar.NotificationVisibility[], java.io.Serializable] */
    public final void logNotificationVisibilityChanges(Collection collection, Collection collection2) {
        if (collection.isEmpty() && collection2.isEmpty()) {
            return;
        }
        this.mUiBgExecutor.execute(new NotificationLogger$$ExternalSyntheticLambda1(this, cloneVisibilitiesAsArr(collection), cloneVisibilitiesAsArr(collection2), 0));
    }

    public final void maybeUpdateLoggingStatus() {
        if (this.mPanelExpanded == null || this.mDozing == null) {
            return;
        }
        Boolean bool = this.mLockscreen;
        boolean booleanValue = bool == null ? false : bool.booleanValue();
        if (!this.mPanelExpanded.booleanValue() || this.mDozing.booleanValue()) {
            stopNotificationLogging();
            return;
        }
        List list = (List) ((NotifLiveDataStoreImpl) this.mNotifLiveDataStore).activeNotifList.getValue();
        ((NotificationPanelLoggerImpl) this.mNotificationPanelLogger).getClass();
        Notifications$NotificationList notificationProto = NotificationPanelLogger.toNotificationProto(list);
        SysUiStatsLog.write((booleanValue ? NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN_LOCKSCREEN : NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN_STATUS_BAR).getId(), notificationProto.notifications.length, MessageNano.toByteArray(notificationProto));
        if (this.mLogging) {
            return;
        }
        this.mLogging = true;
        NotificationStackScrollLayoutController.this.mView.mListener = new NotificationLogger$$ExternalSyntheticLambda2(this);
        onChildLocationsChanged();
    }

    public void onChildLocationsChanged() {
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(this.mVisibilityReporter)) {
            return;
        }
        handler.postAtTime(this.mVisibilityReporter, this.mLastVisibilityReportUptimeMs + 500);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        synchronized (this.mDozingLock) {
            this.mDozing = Boolean.valueOf(z);
            maybeUpdateLoggingStatus();
        }
    }

    public void onShadeExpansionFullyChanged(Boolean bool) {
        Boolean bool2 = this.mPanelExpanded;
        if (bool2 == null || !bool2.equals(bool)) {
            this.mPanelExpanded = bool;
            synchronized (this.mDozingLock) {
                maybeUpdateLoggingStatus();
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        synchronized (this.mDozingLock) {
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            this.mLockscreen = Boolean.valueOf(z);
        }
    }

    public void setVisibilityReporter(Runnable runnable) {
        this.mVisibilityReporter = runnable;
    }

    public final void stopNotificationLogging() {
        if (this.mLogging) {
            this.mLogging = false;
            ArraySet arraySet = this.mCurrentlyVisibleNotifications;
            if (!arraySet.isEmpty()) {
                logNotificationVisibilityChanges(Collections.emptyList(), arraySet);
                recycleAllVisibilityObjects(arraySet);
            }
            this.mHandler.removeCallbacks(this.mVisibilityReporter);
            NotificationStackScrollLayoutController.this.mView.mListener = null;
        }
    }
}
