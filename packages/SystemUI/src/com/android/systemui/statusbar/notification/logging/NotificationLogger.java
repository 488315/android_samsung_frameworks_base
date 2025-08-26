package com.android.systemui.statusbar.notification.logging;

import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.UseElapsedRealtimeForCreationTime;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class NotificationLogger implements StatusBarStateController.StateListener, CoreStartable, NotificationRowStatsLogger {
    public final Object mDozingLock;
    public final Handler mHandler;
    public long mLastVisibilityReportUptimeMs;
    public Runnable mVisibilityReporter;

    public class ExpansionStateLogger {
        public final Executor mUiBgExecutor;
        public final Map mExpansionStates = new ArrayMap();
        public final Map mLoggedExpansionState = new ArrayMap();
        IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

        public class State {
            public Boolean mIsExpanded;
            public Boolean mIsUserAction;
            public Boolean mIsVisible;
            public NotificationVisibility.NotificationLocation mLocation;

            public /* synthetic */ State(int i) {
                this();
            }

            public /* synthetic */ State(State state, int i) {
                this(state);
            }

            private State() {
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
            State state = (State) ((ArrayMap) this.mExpansionStates).get(str);
            if (state != null) {
                return state;
            }
            State state2 = new State(0);
            ((ArrayMap) this.mExpansionStates).put(str, state2);
            return state2;
        }

        public final void maybeNotifyOnNotificationExpansionChanged(final String str, State state) {
            Boolean bool;
            if (state.mIsUserAction == null || state.mIsExpanded == null || (bool = state.mIsVisible) == null || state.mLocation == null || !bool.booleanValue()) {
                return;
            }
            Boolean bool2 = (Boolean) ((ArrayMap) this.mLoggedExpansionState).get(str);
            if (bool2 != null || state.mIsExpanded.booleanValue()) {
                if (bool2 == null || !Objects.equals(state.mIsExpanded, bool2)) {
                    ((ArrayMap) this.mLoggedExpansionState).put(str, state.mIsExpanded);
                    final State state2 = new State(state, 0);
                    this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger$ExpansionStateLogger$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationLogger.ExpansionStateLogger expansionStateLogger = this.f$0;
                            String str2 = str;
                            NotificationLogger.ExpansionStateLogger.State state3 = state2;
                            expansionStateLogger.getClass();
                            try {
                                expansionStateLogger.mBarService.onNotificationExpansionChanged(str2, state3.mIsUserAction.booleanValue(), state3.mIsExpanded.booleanValue(), state3.mLocation.ordinal());
                            } catch (RemoteException e) {
                                Log.e("NotificationLogger", "Failed to call onNotificationExpansionChanged: ", e);
                            }
                        }
                    });
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
            NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr = NotificationLogger.cloneVisibilitiesAsArr(collection);
            NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr2 = NotificationLogger.cloneVisibilitiesAsArr(collection2);
            for (NotificationVisibility notificationVisibility : notificationVisibilityArrCloneVisibilitiesAsArr) {
                State state = getState(notificationVisibility.key);
                state.mIsVisible = Boolean.TRUE;
                state.mLocation = notificationVisibility.location;
                maybeNotifyOnNotificationExpansionChanged(notificationVisibility.key, state);
            }
            for (NotificationVisibility notificationVisibility2 : notificationVisibilityArrCloneVisibilitiesAsArr2) {
                getState(notificationVisibility2.key).mIsVisible = Boolean.FALSE;
            }
        }
    }

    public NotificationLogger(NotificationListener notificationListener, Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, StatusBarStateController statusBarStateController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, JavaAdapter javaAdapter, ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
        new ArraySet();
        this.mHandler = new Handler();
        this.mDozingLock = new Object();
        this.mVisibilityReporter = new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger.1
            {
                new ArraySet();
                new ArraySet();
                new ArraySet();
            }

            @Override // java.lang.Runnable
            public final void run() {
                NotificationLogger notificationLogger = NotificationLogger.this;
                int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
                notificationLogger.mLastVisibilityReportUptimeMs = SystemClock.uptimeMillis();
                NotificationLogger.this.getClass();
                throw null;
            }
        };
        NotificationsLiveDataStoreRefactor.assertInLegacyMode();
        throw null;
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

    public Runnable getVisibilityReporter() {
        return this.mVisibilityReporter;
    }

    public void onChildLocationsChanged() {
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(this.mVisibilityReporter)) {
            return;
        }
        handler.postAtTime(this.mVisibilityReporter, this.mLastVisibilityReportUptimeMs + 500);
    }

    @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger
    public final void onNotificationExpansionChanged(String str, int i, boolean z, boolean z2) {
        throw null;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        synchronized (this.mDozingLock) {
            if (i != 1) {
            }
        }
    }

    public void setVisibilityReporter(Runnable runnable) {
        this.mVisibilityReporter = runnable;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        throw null;
    }
}
