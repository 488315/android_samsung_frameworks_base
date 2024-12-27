package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class BaseHeadsUpManager implements HeadsUpManager {
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public final int mAutoDismissTime;
    public final AvalancheController mAvalancheController;
    public final Context mContext;
    public final DelayableExecutor mExecutor;
    public boolean mHasPinnedNotification;
    public final HeadsUpManagerLogger mLogger;
    public final int mMinimumDisplayTime;
    public int mSnoozeLengthMs;
    public final ArrayMap mSnoozedPackages;
    public final int mStickyForSomeTimeAutoDismissTime;
    public final SystemClock mSystemClock;
    public final int mTouchAcceptanceDelay;
    public final UiEventLogger mUiEventLogger;
    public int mUser;
    public final ListenerSet mListeners = new ListenerSet();
    public final List mCallbacks = new ArrayList();
    public final ArrayMap mHeadsUpEntryMap = new ArrayMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class HeadsUpEntry implements Comparable {
        public Runnable mCancelRemoveRunnable;
        public long mEarliestRemovalTime;
        public NotificationEntry mEntry;
        public boolean mExpanded;
        public long mPostTime;
        public boolean mRemoteInputActive;
        public Runnable mRemoveRunnable;
        public boolean mUserActionMayIndirectlyRemove;
        public boolean mWasUnpinned;

        public HeadsUpEntry() {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
        }

        public long calculateFinishTime() {
            int i;
            if (this.mEntry.isStickyAndNotDemoted()) {
                i = BaseHeadsUpManager.this.mStickyForSomeTimeAutoDismissTime;
            } else {
                BaseHeadsUpManager baseHeadsUpManager = BaseHeadsUpManager.this;
                AvalancheController avalancheController = baseHeadsUpManager.mAvalancheController;
                i = baseHeadsUpManager.mAutoDismissTime;
                avalancheController.getClass();
                Flags.notificationAvalancheThrottleHun();
            }
            return this.mPostTime + BaseHeadsUpManager.this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(i, 7);
        }

        public final void cancelAutoRemovalCallbacks(String str) {
            HeadsUpManagerLogger headsUpManagerLogger = BaseHeadsUpManager.this.mLogger;
            NotificationEntry notificationEntry = this.mEntry;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logAutoRemoveCancelRequest$2 headsUpManagerLogger$logAutoRemoveCancelRequest$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logAutoRemoveCancelRequest$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("request: cancel auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logAutoRemoveCancelRequest$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5 baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5 = new BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5(this, str, 0);
            if (!BaseHeadsUpManager.this.isHeadsUpEntry(this.mEntry.mKey)) {
                baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5.run();
            } else {
                BaseHeadsUpManager.this.mAvalancheController.getClass();
                AvalancheController.update(baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5);
            }
        }

        public Runnable createRemoveRunnable(NotificationEntry notificationEntry) {
            return new BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5(this, notificationEntry, 1);
        }

        public final boolean equals(Object obj) {
            NotificationEntry notificationEntry;
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof HeadsUpEntry)) {
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

        public boolean isSticky() {
            if ((!this.mEntry.isRowPinned() || !this.mExpanded) && !this.mRemoteInputActive) {
                BaseHeadsUpManager baseHeadsUpManager = BaseHeadsUpManager.this;
                NotificationEntry notificationEntry = this.mEntry;
                baseHeadsUpManager.getClass();
                if (!BaseHeadsUpManager.hasFullScreenIntent(notificationEntry)) {
                    return false;
                }
            }
            return true;
        }

        public void reset() {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            cancelAutoRemovalCallbacks("reset()");
            this.mEntry = null;
            this.mRemoveRunnable = null;
            this.mExpanded = false;
            this.mRemoteInputActive = false;
        }

        public final void scheduleAutoRemovalCallback(BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2 baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2, String str) {
            HeadsUpManagerLogger headsUpManagerLogger = BaseHeadsUpManager.this.mLogger;
            NotificationEntry notificationEntry = this.mEntry;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logAutoRemoveRequest$2 headsUpManagerLogger$logAutoRemoveRequest$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logAutoRemoveRequest$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("request: reschedule auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logAutoRemoveRequest$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            BaseHeadsUpManager$$ExternalSyntheticLambda1 baseHeadsUpManager$$ExternalSyntheticLambda1 = new BaseHeadsUpManager$$ExternalSyntheticLambda1(this, baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2, str);
            AvalancheController avalancheController = BaseHeadsUpManager.this.mAvalancheController;
            str.concat(" scheduleAutoRemovalCallback");
            avalancheController.getClass();
            AvalancheController.update(baseHeadsUpManager$$ExternalSyntheticLambda1);
        }

        public void setExpanded(boolean z) {
            this.mExpanded = z;
        }

        public void setRowPinned(boolean z) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) {
                return;
            }
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            boolean isAboveShelf = expandableNotificationRow.isAboveShelf();
            expandableNotificationRow.mIsPinned = z;
            if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight()) {
                expandableNotificationRow.notifyHeightChanged(false);
            }
            if (z) {
                expandableNotificationRow.setAnimationRunning(true);
                expandableNotificationRow.mExpandedWhenPinned = false;
            } else if (expandableNotificationRow.mExpandedWhenPinned) {
                expandableNotificationRow.setUserExpanded(true, false);
            }
            expandableNotificationRow.setChronometerRunning(expandableNotificationRow.mLastChronometerRunning);
            if (expandableNotificationRow.isAboveShelf() != isAboveShelf) {
                expandableNotificationRow.mAboveShelfChangedListener.onAboveShelfStateChanged(true ^ isAboveShelf);
            }
            boolean z2 = expandableNotificationRow.mIsPinned;
            NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
            notificationBackgroundView.mIsPinned = z2;
            notificationBackgroundView.invalidate();
        }

        public void updateEntry(String str, boolean z) {
            updateEntry$1(str, z);
        }

        public final void updateEntry$1(final String str, final boolean z) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda1
                public final /* synthetic */ boolean f$3 = true;

                @Override // java.lang.Runnable
                public final void run() {
                    BaseHeadsUpManager.HeadsUpEntry headsUpEntry = BaseHeadsUpManager.HeadsUpEntry.this;
                    boolean z2 = z;
                    String str2 = str;
                    boolean z3 = this.f$3;
                    HeadsUpManagerLogger headsUpManagerLogger = BaseHeadsUpManager.this.mLogger;
                    NotificationEntry notificationEntry = headsUpEntry.mEntry;
                    headsUpManagerLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    HeadsUpManagerLogger$logUpdateEntry$2 headsUpManagerLogger$logUpdateEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdateEntry$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String str1 = logMessage.getStr1();
                            boolean bool1 = logMessage.getBool1();
                            String str22 = logMessage.getStr2();
                            StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("update entry ", str1, " updatePostTime: ", " reason: ", bool1);
                            m.append(str22);
                            return m.toString();
                        }
                    };
                    LogBuffer logBuffer = headsUpManagerLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUpdateEntry$2, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    logMessageImpl.bool1 = z2;
                    if (str2 == null) {
                        str2 = "unknown";
                    }
                    logMessageImpl.str2 = str2;
                    logBuffer.commit(obtain);
                    long elapsedRealtime = BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
                    if (z3) {
                        headsUpEntry.mEarliestRemovalTime = BaseHeadsUpManager.this.mMinimumDisplayTime + elapsedRealtime;
                    }
                    if (z2) {
                        headsUpEntry.mPostTime = Math.max(headsUpEntry.mPostTime, elapsedRealtime);
                    }
                }
            };
            BaseHeadsUpManager.this.mAvalancheController.getClass();
            AvalancheController.update(runnable);
            if (isSticky()) {
                cancelAutoRemovalCallbacks("updateEntry (sticky)");
            } else {
                scheduleAutoRemovalCallback(new BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2(this, 0), "updateEntry (not sticky)");
                BaseHeadsUpManager.this.onEntryUpdated$1();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0063 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0064  */
        @Override // java.lang.Comparable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int compareTo(com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry r7) {
            /*
                r6 = this;
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r6.mEntry
                boolean r0 = r0.isRowPinned()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r7.mEntry
                boolean r1 = r1.isRowPinned()
                r2 = -1
                if (r0 == 0) goto L12
                if (r1 != 0) goto L12
                return r2
            L12:
                r3 = 1
                if (r0 != 0) goto L18
                if (r1 == 0) goto L18
                return r3
            L18:
                com.android.systemui.statusbar.policy.BaseHeadsUpManager r0 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.this
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r6.mEntry
                r0.getClass()
                boolean r0 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.hasFullScreenIntent(r1)
                com.android.systemui.statusbar.policy.BaseHeadsUpManager r1 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.this
                com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r7.mEntry
                r1.getClass()
                boolean r1 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.hasFullScreenIntent(r4)
                if (r0 == 0) goto L34
                if (r1 != 0) goto L34
            L32:
                r0 = r2
                goto L61
            L34:
                if (r0 != 0) goto L3a
                if (r1 == 0) goto L3a
            L38:
                r0 = r3
                goto L61
            L3a:
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r6.mEntry
                boolean r0 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.m2237$$Nest$smisCriticalCallNotif(r0)
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r7.mEntry
                boolean r1 = com.android.systemui.statusbar.policy.BaseHeadsUpManager.m2237$$Nest$smisCriticalCallNotif(r1)
                if (r0 == 0) goto L4b
                if (r1 != 0) goto L4b
                goto L32
            L4b:
                if (r0 != 0) goto L50
                if (r1 == 0) goto L50
                goto L38
            L50:
                boolean r0 = r6.mRemoteInputActive
                if (r0 == 0) goto L59
                boolean r1 = r7.mRemoteInputActive
                if (r1 != 0) goto L59
                goto L32
            L59:
                if (r0 != 0) goto L60
                boolean r0 = r7.mRemoteInputActive
                if (r0 == 0) goto L60
                goto L38
            L60:
                r0 = 0
            L61:
                if (r0 == 0) goto L64
                return r0
            L64:
                long r0 = r6.mPostTime
                long r4 = r7.mPostTime
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 <= 0) goto L6d
                return r2
            L6d:
                if (r0 != 0) goto L7c
                com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r6.mEntry
                java.lang.String r6 = r6.mKey
                com.android.systemui.statusbar.notification.collection.NotificationEntry r7 = r7.mEntry
                java.lang.String r7 = r7.mKey
                int r6 = r6.compareTo(r7)
                return r6
            L7c:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry.compareTo(com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry):int");
        }

        public HeadsUpEntry(NotificationEntry notificationEntry) {
            Runnable createRemoveRunnable = createRemoveRunnable(notificationEntry);
            this.mEntry = notificationEntry;
            this.mRemoveRunnable = createRemoveRunnable;
            this.mPostTime = BaseHeadsUpManager.this.mSystemClock.elapsedRealtime() + BaseHeadsUpManager.this.mTouchAcceptanceDelay;
            updateEntry("setEntry", true);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public static boolean m2237$$Nest$smisCriticalCallNotif(NotificationEntry notificationEntry) {
        Notification notification2 = notificationEntry.mSbn.getNotification();
        if (notification2.isStyle(Notification.CallStyle.class) && notification2.extras.getInt("android.callType") == 1) {
            return true;
        }
        return notificationEntry.mSbn.isOngoing() && "call".equals(notification2.category);
    }

    public BaseHeadsUpManager(Context context, HeadsUpManagerLogger headsUpManagerLogger, Handler handler, final GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, AccessibilityManagerWrapper accessibilityManagerWrapper, UiEventLogger uiEventLogger, AvalancheController avalancheController) {
        this.mLogger = headsUpManagerLogger;
        this.mExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        this.mContext = context;
        this.mAccessibilityMgr = accessibilityManagerWrapper;
        this.mUiEventLogger = uiEventLogger;
        this.mAvalancheController = avalancheController;
        Resources resources = context.getResources();
        int i = NotificationThrottleHun.$r8$clinit;
        Flags.notificationAvalancheThrottleHun();
        this.mMinimumDisplayTime = resources.getInteger(R.integer.heads_up_notification_minimum_time);
        this.mStickyForSomeTimeAutoDismissTime = resources.getInteger(R.integer.sticky_heads_up_notification_time);
        this.mAutoDismissTime = resources.getInteger(R.integer.heads_up_notification_decay);
        this.mTouchAcceptanceDelay = resources.getInteger(R.integer.touch_acceptance_delay);
        this.mSnoozedPackages = new ArrayMap();
        this.mSnoozeLengthMs = globalSettings.getInt("heads_up_snooze_length_ms", resources.getInteger(R.integer.heads_up_default_snooze_length_ms));
        globalSettings.registerContentObserverSync(globalSettings.getUriFor("heads_up_snooze_length_ms"), false, new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                int i2 = globalSettings.getInt("heads_up_snooze_length_ms", -1);
                if (i2 > -1) {
                    BaseHeadsUpManager baseHeadsUpManager = BaseHeadsUpManager.this;
                    if (i2 != baseHeadsUpManager.mSnoozeLengthMs) {
                        baseHeadsUpManager.mSnoozeLengthMs = i2;
                        HeadsUpManagerLogger headsUpManagerLogger2 = baseHeadsUpManager.mLogger;
                        headsUpManagerLogger2.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        HeadsUpManagerLogger$logSnoozeLengthChange$2 headsUpManagerLogger$logSnoozeLengthChange$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logSnoozeLengthChange$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "snooze length changed: ", "ms");
                            }
                        };
                        LogBuffer logBuffer = headsUpManagerLogger2.buffer;
                        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logSnoozeLengthChange$2, null);
                        ((LogMessageImpl) obtain).int1 = i2;
                        logBuffer.commit(obtain);
                    }
                }
            }
        });
    }

    public static boolean hasFullScreenIntent(NotificationEntry notificationEntry) {
        return notificationEntry.mSbn.getNotification().fullScreenIntent != null;
    }

    public final void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
        this.mListeners.addIfAbsent(onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean canRemoveImmediately(String str) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(str);
        return (headsUpEntry != null && headsUpEntry.mUserActionMayIndirectlyRemove) || headsUpEntry == null || headsUpEntry.mEarliestRemovalTime < BaseHeadsUpManager.this.mSystemClock.elapsedRealtime() || headsUpEntry.mEntry.isRowDismissed();
    }

    public HeadsUpEntry createHeadsUpEntry(NotificationEntry notificationEntry) {
        return new HeadsUpEntry(notificationEntry);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("HeadsUpManager state:");
        dumpInternal(printWriter, strArr);
    }

    public void dumpInternal(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mTouchAcceptanceDelay=");
        printWriter.println(this.mTouchAcceptanceDelay);
        printWriter.print("  mSnoozeLengthMs=");
        printWriter.println(this.mSnoozeLengthMs);
        printWriter.print("  now=");
        printWriter.println(this.mSystemClock.elapsedRealtime());
        printWriter.print("  mUser=");
        printWriter.println(this.mUser);
        for (HeadsUpEntry headsUpEntry : this.mHeadsUpEntryMap.values()) {
            printWriter.print("  HeadsUpEntry=");
            printWriter.println(headsUpEntry.mEntry);
        }
        int size = this.mSnoozedPackages.size();
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  snoozed packages: ", size, printWriter);
        for (int i = 0; i < size; i++) {
            printWriter.print("    ");
            printWriter.print(this.mSnoozedPackages.valueAt(i));
            printWriter.print(", ");
            printWriter.println((String) this.mSnoozedPackages.keyAt(i));
        }
    }

    public final HeadsUpEntry getHeadsUpEntry(String str) {
        if (this.mHeadsUpEntryMap.containsKey(str)) {
            return (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        }
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        return null;
    }

    public final List getHeadsUpEntryList() {
        ArrayList arrayList = new ArrayList(this.mHeadsUpEntryMap.values());
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        arrayList.addAll(new ArrayList());
        return arrayList;
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
        if (this.mHeadsUpEntryMap.isEmpty()) {
            this.mAvalancheController.getClass();
            Flags.notificationAvalancheThrottleHun();
            if (new ArrayList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final boolean isHeadsUpEntry(String str) {
        if (this.mHeadsUpEntryMap.containsKey(str)) {
            return true;
        }
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        return false;
    }

    public final boolean isSnoozed(String str) {
        String str2 = this.mUser + "," + str;
        Long l = (Long) this.mSnoozedPackages.get(str2);
        if (l == null) {
            return false;
        }
        long longValue = l.longValue();
        long elapsedRealtime = this.mSystemClock.elapsedRealtime();
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        if (longValue > elapsedRealtime) {
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logIsSnoozedReturned$2 headsUpManagerLogger$logIsSnoozedReturned$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logIsSnoozedReturned$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package snoozed when queried ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logIsSnoozedReturned$2, null);
            ((LogMessageImpl) obtain).str1 = str2;
            logBuffer.commit(obtain);
            return true;
        }
        headsUpManagerLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        HeadsUpManagerLogger$logPackageUnsnoozed$2 headsUpManagerLogger$logPackageUnsnoozed$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logPackageUnsnoozed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package unsnoozed ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer2 = headsUpManagerLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$logPackageUnsnoozed$2, null);
        ((LogMessageImpl) obtain2).str1 = str2;
        logBuffer2.commit(obtain2);
        this.mSnoozedPackages.remove(str2);
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isTrackingHeadsUp() {
        return false;
    }

    public void onEntryAdded(HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        notificationEntry.setHeadsUp(true);
        setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(notificationEntry), "onEntryAdded");
        EventLog.writeEvent(36001, notificationEntry.mKey, 1);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, true);
        }
    }

    public void onEntryRemoved(HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        notificationEntry.setHeadsUp(false);
        setEntryPinned(headsUpEntry, false, "onEntryRemoved");
        EventLog.writeEvent(36001, notificationEntry.mKey, 0);
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logNotificationActuallyRemoved$2 headsUpManagerLogger$logNotificationActuallyRemoved$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logNotificationActuallyRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("notification removed ", ((LogMessage) obj).getStr1(), " ");
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logNotificationActuallyRemoved$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, false);
        }
        if (notificationEntry.mIsHeadsUpByBriefExpanding) {
            notificationEntry.mIsHeadsUpByBriefExpanding = false;
        }
    }

    public final void releaseAllImmediately() {
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logReleaseAllImmediately$2 headsUpManagerLogger$logReleaseAllImmediately$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logReleaseAllImmediately$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "release all immediately";
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        logBuffer.commit(logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logReleaseAllImmediately$2, null));
        ArraySet arraySet = new ArraySet(this.mHeadsUpEntryMap.keySet());
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        ArrayList arrayList = new ArrayList();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            removeEntry((String) it.next(), "releaseAllImmediately (keysToRemove)");
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            removeEntry((String) it2.next(), "releaseAllImmediately (waitingKeysToRemove)");
        }
    }

    public final void removeEntry(final String str, final String str2) {
        final HeadsUpEntry headsUpEntry;
        final boolean z;
        HeadsUpEntry headsUpEntry2 = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        AvalancheController avalancheController = this.mAvalancheController;
        if (headsUpEntry2 == null) {
            avalancheController.getClass();
            Flags.notificationAvalancheThrottleHun();
            z = true;
            headsUpEntry = null;
        } else {
            headsUpEntry = headsUpEntry2;
            z = false;
        }
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logRemoveEntryRequest$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str22 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                boolean z2 = z;
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("request: ", str22, " => remove entry ", str1, " isWaiting: ");
                m.append(z2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.str2 = str2;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BaseHeadsUpManager baseHeadsUpManager = BaseHeadsUpManager.this;
                String str3 = str;
                String str4 = str2;
                final boolean z2 = z;
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry3 = headsUpEntry;
                HeadsUpManagerLogger headsUpManagerLogger2 = baseHeadsUpManager.mLogger;
                headsUpManagerLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                Function1 function12 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logRemoveEntry$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return logMessage.getStr2() + " => remove entry " + logMessage.getStr1() + " isWaiting: " + z2;
                    }
                };
                LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, function12, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = NotificationUtils.logKey(str3);
                logMessageImpl2.str2 = str4;
                logMessageImpl2.bool1 = z2;
                logBuffer2.commit(obtain2);
                if (headsUpEntry3 == null) {
                    return;
                }
                NotificationEntry notificationEntry = headsUpEntry3.mEntry;
                if (notificationEntry == null || !notificationEntry.mExpandAnimationRunning) {
                    notificationEntry.mIsDemoted = true;
                    baseHeadsUpManager.mHeadsUpEntryMap.remove(str3);
                    baseHeadsUpManager.onEntryRemoved(headsUpEntry3);
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.sendAccessibilityEvent(2048);
                    }
                    NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                    Flags.notificationsHeadsUpRefactor();
                    headsUpEntry3.reset();
                }
            }
        };
        avalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        runnable.run();
    }

    public final boolean removeNotification$1(String str, boolean z) {
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logRemoveNotification$2 headsUpManagerLogger$logRemoveNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logRemoveNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("remove notification ", str1, " releaseImmediately: ", " isWaiting: ", bool1);
                m.append(bool2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logRemoveNotification$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = false;
        logBuffer.commit(obtain);
        Flags.notificationAvalancheThrottleHun();
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry == null) {
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
        if (headsUpEntry.mRemoveRunnable != null) {
            headsUpEntry.scheduleAutoRemovalCallback(new BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2(headsUpEntry, 1), "removeAsSoonAsPossible");
        }
        return false;
    }

    public final void setEntryPinned(HeadsUpEntry headsUpEntry, boolean z, String str) {
        StatusBarNotification statusBarNotification;
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        HeadsUpManagerLogger$logSetEntryPinned$2 headsUpManagerLogger$logSetEntryPinned$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logSetEntryPinned$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr2() + " => set entry pinned " + logMessage.getStr1() + " pinned: " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logSetEntryPinned$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        if (!z) {
            headsUpEntry.mWasUnpinned = true;
        }
        boolean z2 = false;
        if ((notificationEntry2 != null && notificationEntry2.isRowPinned()) != z) {
            headsUpEntry.setRowPinned(z);
            Iterator it = this.mHeadsUpEntryMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (getHeadsUpEntry((String) it.next()).mEntry.isRowPinned()) {
                    z2 = true;
                    break;
                }
            }
            boolean z3 = this.mHasPinnedNotification;
            ListenerSet listenerSet = this.mListeners;
            if (z2 != z3) {
                LogMessage obtain2 = logBuffer.obtain("HeadsUpManager", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdatePinnedMode$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("has pinned notification changed to ", ((LogMessage) obj).getBool1());
                    }
                }, null);
                ((LogMessageImpl) obtain2).bool1 = z2;
                logBuffer.commit(obtain2);
                this.mHasPinnedNotification = z2;
                if (z2) {
                    MetricsLogger.count(this.mContext, "note_peek", 1);
                }
                Iterator it2 = listenerSet.iterator();
                while (it2.hasNext()) {
                    ((OnHeadsUpChangedListener) it2.next()).onHeadsUpPinnedModeChanged(z2);
                }
            }
            if (z && (statusBarNotification = notificationEntry2.mSbn) != null) {
                this.mUiEventLogger.logWithInstanceId(NotificationPeekEvent.NOTIFICATION_PEEK, statusBarNotification.getUid(), notificationEntry2.mSbn.getPackageName(), notificationEntry2.mSbn.getInstanceId());
            }
            Iterator it3 = listenerSet.iterator();
            while (it3.hasNext()) {
                OnHeadsUpChangedListener onHeadsUpChangedListener = (OnHeadsUpChangedListener) it3.next();
                if (z) {
                    onHeadsUpChangedListener.onHeadsUpPinned(notificationEntry2);
                } else {
                    onHeadsUpChangedListener.onHeadsUpUnPinned(notificationEntry2);
                }
            }
        }
    }

    public boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
        return headsUpEntry == null ? hasFullScreenIntent(notificationEntry) : hasFullScreenIntent(notificationEntry) && !headsUpEntry.mWasUnpinned;
    }

    public final void showNotification(NotificationEntry notificationEntry) {
        HeadsUpEntry createHeadsUpEntry = createHeadsUpEntry(notificationEntry);
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logShowNotificationRequest$2 headsUpManagerLogger$logShowNotificationRequest$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logShowNotificationRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("request: show notification ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logShowNotificationRequest$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        BaseHeadsUpManager$$ExternalSyntheticLambda1 baseHeadsUpManager$$ExternalSyntheticLambda1 = new BaseHeadsUpManager$$ExternalSyntheticLambda1(this, notificationEntry, createHeadsUpEntry);
        this.mAvalancheController.getClass();
        AvalancheController.update(baseHeadsUpManager$$ExternalSyntheticLambda1);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void snooze() {
        ArrayList arrayList = new ArrayList(this.mHeadsUpEntryMap.keySet());
        this.mAvalancheController.getClass();
        Flags.notificationAvalancheThrottleHun();
        arrayList.addAll(new ArrayList());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String packageName = getHeadsUpEntry((String) it.next()).mEntry.mSbn.getPackageName();
            String str = this.mUser + "," + packageName;
            HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logPackageSnoozed$2 headsUpManagerLogger$logPackageSnoozed$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logPackageSnoozed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package snoozed ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logPackageSnoozed$2, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
            this.mSnoozedPackages.put(str, Long.valueOf(this.mSystemClock.elapsedRealtime() + this.mSnoozeLengthMs));
        }
    }

    public final void unpinAll() {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        for (String str : this.mHeadsUpEntryMap.keySet()) {
            HeadsUpEntry headsUpEntry = getHeadsUpEntry(str);
            HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
            headsUpManagerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logUnpinEntryRequest$2 headsUpManagerLogger$logUnpinEntryRequest$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUnpinEntryRequest$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("request: unpin entry ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUnpinEntryRequest$2, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(str);
            logBuffer.commit(obtain);
            this.mAvalancheController.getClass();
            Flags.notificationAvalancheThrottleHun();
            headsUpManagerLogger.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            HeadsUpManagerLogger$logUnpinEntry$2 headsUpManagerLogger$logUnpinEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUnpinEntry$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("unpin entry ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer2 = headsUpManagerLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$logUnpinEntry$2, null);
            ((LogMessageImpl) obtain2).str1 = NotificationUtils.logKey(str);
            logBuffer2.commit(obtain2);
            setEntryPinned(headsUpEntry, false, "unpinAll");
            headsUpEntry.updateEntry("unpinAll", false);
            NotificationEntry notificationEntry = headsUpEntry.mEntry;
            if (notificationEntry != null && (expandableNotificationRow = notificationEntry.row) != null && expandableNotificationRow.mustStayOnScreen() && (expandableNotificationRow2 = headsUpEntry.mEntry.row) != null) {
                expandableNotificationRow2.mMustStayOnScreen = false;
            }
        }
    }

    public final void updateNotification(final String str, final boolean z) {
        boolean z2 = ((HeadsUpEntry) this.mHeadsUpEntryMap.get(str)) != null;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logUpdateNotificationRequest$2 headsUpManagerLogger$logUpdateNotificationRequest$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdateNotificationRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("request: update notification ", str1, " alert: ", " hasEntry: ", bool1);
                m.append(bool2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUpdateNotificationRequest$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BaseHeadsUpManager.this.updateNotificationInternal(str, z);
            }
        };
        this.mAvalancheController.getClass();
        AvalancheController.update(runnable);
    }

    public final void updateNotificationInternal(String str, boolean z) {
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        boolean z2 = headsUpEntry != null;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        headsUpManagerLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logUpdateNotification$2 headsUpManagerLogger$logUpdateNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logUpdateNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("update notification ", str1, " alert: ", " hasEntry: ", bool1);
                m.append(bool2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logUpdateNotification$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
        if (headsUpEntry == null) {
            return;
        }
        ExpandableNotificationRow expandableNotificationRow = headsUpEntry.mEntry.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.sendAccessibilityEvent(2048);
        }
        if (z) {
            headsUpEntry.updateEntry("updateNotification", true);
            setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(headsUpEntry.mEntry), "updateNotificationInternal");
        }
    }

    public void onEntryUpdated$1() {
    }
}
