package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.PendingIntent;
import android.content.Context;
import android.view.View;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Iterator;
import java.util.Objects;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationRowBinderImpl {
    public final Context mContext;
    public final ExpandableNotificationRowComponent.Builder mExpandableNotificationRowComponentBuilder;
    public final FeatureFlags mFeatureFlags;
    public final IconManager mIconManager;
    public NotificationListContainer mListContainer;
    public final NotificationRowBinderLogger mLogger;
    public final NotifBindPipeline mNotifBindPipeline;
    public NotificationClicker mNotificationClicker;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public NotificationPresenter mPresenter;
    public final RowContentBindStage mRowContentBindStage;
    public final Provider mRowInflaterTaskProvider;

    public NotificationRowBinderImpl(Context context, NotificationMessagingUtil notificationMessagingUtil, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage, Provider provider, ExpandableNotificationRowComponent.Builder builder, IconManager iconManager, NotificationRowBinderLogger notificationRowBinderLogger, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mRowInflaterTaskProvider = provider;
        this.mExpandableNotificationRowComponentBuilder = builder;
        this.mIconManager = iconManager;
        this.mLogger = notificationRowBinderLogger;
        this.mFeatureFlags = featureFlags;
    }

    public final void inflateContentViews(NotificationEntry notificationEntry, NotifInflater.Params params, final ExpandableNotificationRow expandableNotificationRow, final NotifInflaterImpl.AnonymousClass1 anonymousClass1) {
        expandableNotificationRow.getClass();
        RowContentBindStage rowContentBindStage = this.mRowContentBindStage;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.requireContentViews(1);
        rowContentBindParams.requireContentViews(2);
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = notificationEntry.mKey;
        ongoingActivityDataHelper.getClass();
        if (OngoingActivityDataHelper.getPendingOngoingActivityData(str) != null) {
            rowContentBindParams.requireContentViews(256);
        }
        boolean z = rowContentBindParams.mUseMinimized;
        boolean z2 = params.isMinimized;
        if (z != z2) {
            rowContentBindParams.mDirtyContentViews |= 3;
        }
        rowContentBindParams.mUseMinimized = z2;
        final int i = params.redactionType;
        rowContentBindParams.mRedactionType = i;
        if (i != 0) {
            rowContentBindParams.requireContentViews(8);
        } else {
            rowContentBindParams.markContentViewsFreeable(8);
        }
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        boolean z3 = params.isChildInGroup;
        if (z3) {
            rowContentBindParams.requireContentViews(16);
        } else {
            rowContentBindParams.markContentViewsFreeable(16);
        }
        int i3 = LockscreenOtpRedaction.$r8$clinit;
        if (!z3 || i == 0) {
            rowContentBindParams.markContentViewsFreeable(128);
        } else {
            rowContentBindParams.requireContentViews(128);
        }
        int i4 = AsyncGroupHeaderViewInflation.$r8$clinit;
        rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews;
        NotificationRowBinderLogger notificationRowBinderLogger = this.mLogger;
        notificationRowBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = notificationRowBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        String str2 = notificationEntry.mKey;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = params.reason;
        logBuffer.commit(obtain);
        LogMessage obtain2 = logBuffer.obtain("NotificationRowBinder", logLevel, new NotificationRowBinderLogger$$ExternalSyntheticLambda0(7), null);
        ((LogMessageImpl) obtain2).str1 = str2;
        logBuffer.commit(obtain2);
        boolean z4 = expandableNotificationRow.isInsignificantSummary() ? true : z2;
        expandableNotificationRow.mIsMinimized = z4;
        expandableNotificationRow.mPrivateLayout.getClass();
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.mIsMinimized = z4;
            if (notificationChildrenContainer.mContainingNotification != null) {
                notificationChildrenContainer.recreateLowPriorityHeader(null);
                notificationChildrenContainer.updateHeaderVisibility(false, false);
            }
            boolean z5 = notificationChildrenContainer.mUserLocked;
            if (z5) {
                notificationChildrenContainer.setUserLocked(z5);
            }
        }
        rowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                ExpandableNotificationRow.this.mRedactionType = i;
                anonymousClass1.onAsyncInflationFinished();
            }
        });
    }

    public final void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.AnonymousClass1 anonymousClass1) {
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        boolean rowExists = notificationEntry.rowExists();
        String str = params.reason;
        IconManager iconManager = this.mIconManager;
        NotificationRowBinderLogger notificationRowBinderLogger = this.mLogger;
        if (!rowExists) {
            notificationRowBinderLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = notificationRowBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
            logMessageImpl.str2 = str;
            logBuffer.commit(obtain);
            iconManager.createIcons(notificationEntry);
            LogMessage obtain2 = logBuffer.obtain("NotificationRowBinder", logLevel, new NotificationRowBinderLogger$$ExternalSyntheticLambda0(2), null);
            ((LogMessageImpl) obtain2).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer.commit(obtain2);
            ((RowInflaterTask) this.mRowInflaterTaskProvider.get()).inflate(this.mContext, notificationStackScrollLayout, notificationEntry, null, new NotificationRowBinderImpl$$ExternalSyntheticLambda0(this, notificationEntry, params, anonymousClass1));
            return;
        }
        notificationRowBinderLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda02 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer2 = notificationRowBinderLogger.buffer;
        LogMessage obtain3 = logBuffer2.obtain("NotificationRowBinder", logLevel2, notificationRowBinderLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
        logMessageImpl2.str1 = NotificationUtils.logKey(notificationEntry);
        logMessageImpl2.str2 = str;
        logBuffer2.commit(obtain3);
        boolean z = false;
        iconManager.updateIcons(notificationEntry, false);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        expandableNotificationRow.mShowingPublicInitialized = false;
        expandableNotificationRow.mDismissed = false;
        ExpandableNotificationRowController expandableNotificationRowController = expandableNotificationRow.mEntry.mRowController;
        if (expandableNotificationRowController != null && expandableNotificationRowController.mAllowLongPress) {
            ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController.mView;
            ExpandableNotificationRowDragController expandableNotificationRowDragController = expandableNotificationRowController.mDragController;
            if (expandableNotificationRowDragController != null) {
                expandableNotificationRow2.mDragController = expandableNotificationRowDragController;
            }
            ExpandableNotificationRowController$$ExternalSyntheticLambda1 expandableNotificationRowController$$ExternalSyntheticLambda1 = expandableNotificationRowController.mLongPressListener;
            if (expandableNotificationRowController$$ExternalSyntheticLambda1 != null) {
                expandableNotificationRow2.mLongPressListener = expandableNotificationRowController$$ExternalSyntheticLambda1;
            }
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
        if (notificationMenuRowPlugin == null || !notificationMenuRowPlugin.isMenuVisible()) {
            expandableNotificationRow.resetTranslation();
        }
        NotificationStackScrollLayout.AnonymousClass7 anonymousClass7 = expandableNotificationRow.mOnHeightChangedListener;
        if (anonymousClass7 != null) {
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
            if ((notificationStackScrollLayout2.mAnimationsEnabled || notificationStackScrollLayout2.mPulsing) && (notificationStackScrollLayout2.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow))) {
                z = true;
            }
            expandableNotificationRow.setAnimationRunning(z);
            expandableNotificationRow.setChronometerRunning(notificationStackScrollLayout2.mIsExpanded);
            if (notificationStackScrollLayout2.mTopHeadsUpRow == expandableNotificationRow) {
                Iterator it = notificationStackScrollLayout2.mHeadsUpHeightChangedListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        }
        expandableNotificationRow.requestLayout();
        expandableNotificationRow.mTargetPoint = null;
        updateRow(notificationEntry, expandableNotificationRow);
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        Context context = this.mContext;
        ongoingActivityDataHelper.getClass();
        OngoingActivityDataHelper.recreateOngoingActivity(context, notificationEntry);
        inflateContentViews(notificationEntry, params, expandableNotificationRow, anonymousClass1);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(this.mContext);
        }
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda0] */
    public final void updateRow(NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow) {
        int i = notificationEntry.targetSdk;
        boolean z = i >= 9 && i < 21;
        for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
            notificationContentView.mLegacy = z;
            notificationContentView.updateLegacy();
        }
        final NotificationClicker notificationClicker = this.mNotificationClicker;
        Objects.requireNonNull(notificationClicker);
        expandableNotificationRow.mBubbleClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationClicker notificationClicker2 = NotificationClicker.this;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                NotificationActivityStarter notificationActivityStarter = notificationClicker2.mNotificationActivityStarter;
                NotificationEntry notificationEntry2 = expandableNotificationRow2.mEntry;
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter;
                statusBarNotificationActivityStarter.getClass();
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_BUBBLE_FROM_NOTI, "type", notificationEntry2.isBubble() ? SystemUIAnalytics.QPNE_VID_NOT_BUBBLE : SystemUIAnalytics.QPNE_VID_BUBBLE);
                final StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4 statusBarNotificationActivityStarter$$ExternalSyntheticLambda4 = new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4(statusBarNotificationActivityStarter, notificationEntry2, 0);
                if (notificationEntry2.isBubble()) {
                    statusBarNotificationActivityStarter$$ExternalSyntheticLambda4.run();
                } else {
                    statusBarNotificationActivityStarter.performActionAfterKeyguardDismissed(notificationEntry2, new StatusBarNotificationActivityStarter.OnKeyguardDismissedAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda5
                        @Override // com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.OnKeyguardDismissedAction
                        public final void onDismiss(PendingIntent pendingIntent, boolean z2, boolean z3, boolean z4) {
                            StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4.this.run();
                        }
                    });
                }
            }
        };
        NotificationContentView notificationContentView2 = expandableNotificationRow.mPrivateLayout;
        notificationContentView2.applyBubbleAction(notificationContentView2.mExpandedChild, expandableNotificationRow.mEntry);
        NotificationContentView notificationContentView3 = expandableNotificationRow.mPublicLayout;
        notificationContentView3.applyBubbleAction(notificationContentView3.mExpandedChild, expandableNotificationRow.mEntry);
        expandableNotificationRow.setOnClickListener(notificationClicker);
        expandableNotificationRow.mOnDragSuccessListener = notificationClicker.mOnDragSuccessListener;
    }
}
