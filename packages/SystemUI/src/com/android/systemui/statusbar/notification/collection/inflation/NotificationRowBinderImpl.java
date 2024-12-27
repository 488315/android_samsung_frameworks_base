package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.PendingIntent;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.server.notification.Flags;
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
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutCreatorImpl;
import java.util.Objects;
import javax.inject.Provider;
import kotlin.jvm.functions.Function1;

public final class NotificationRowBinderImpl {
    public final Context mContext;
    public final ExpandableNotificationRowComponent.Builder mExpandableNotificationRowComponentBuilder;
    public final FeatureFlags mFeatureFlags;
    public final IconManager mIconManager;
    public NotificationListContainer mListContainer;
    public final NotificationRowBinderLogger mLogger;
    public final NotificationMessagingUtil mMessagingUtil;
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
        this.mMessagingUtil = notificationMessagingUtil;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mRowInflaterTaskProvider = provider;
        this.mExpandableNotificationRowComponentBuilder = builder;
        this.mIconManager = iconManager;
        this.mLogger = notificationRowBinderLogger;
        this.mFeatureFlags = featureFlags;
    }

    public final void inflateContentViews(NotificationEntry notificationEntry, NotifInflater.Params params, ExpandableNotificationRow expandableNotificationRow, final NotificationRowContentBinder.InflationCallback inflationCallback) {
        boolean z = this.mMessagingUtil.isImportantMessaging(notificationEntry.mSbn, notificationEntry.mRanking.getImportance()) && !(NotiRune.NOTI_INSIGNIFICANT && notificationEntry.isInsignificant());
        boolean z2 = params.isMinimized;
        expandableNotificationRow.getClass();
        RowContentBindStage rowContentBindStage = this.mRowContentBindStage;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.requireContentViews(1);
        rowContentBindParams.requireContentViews(2);
        if (rowContentBindParams.mUseIncreasedHeight != z) {
            rowContentBindParams.mDirtyContentViews |= 1;
        }
        rowContentBindParams.mUseIncreasedHeight = z;
        if (rowContentBindParams.mUseMinimized != z2) {
            rowContentBindParams.mDirtyContentViews |= 3;
        }
        rowContentBindParams.mUseMinimized = z2;
        Flags.screenshareNotificationHiding();
        if (params.needsRedaction) {
            rowContentBindParams.requireContentViews(8);
        } else {
            rowContentBindParams.markContentViewsFreeable(8);
        }
        int i = AsyncHybridViewInflation.$r8$clinit;
        com.android.systemui.Flags.notificationAsyncHybridViewInflation();
        int i2 = AsyncGroupHeaderViewInflation.$r8$clinit;
        com.android.systemui.Flags.notificationAsyncGroupHeaderInflation();
        rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews;
        NotificationRowBinderLogger notificationRowBinderLogger = this.mLogger;
        notificationRowBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowBinderLogger$logRequestingRebind$2 notificationRowBinderLogger$logRequestingRebind$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logRequestingRebind$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("requesting rebind for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = notificationRowBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$logRequestingRebind$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        String str = notificationEntry.mKey;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = params.reason;
        logBuffer.commit(obtain);
        LogMessage obtain2 = logBuffer.obtain("NotificationRowBinder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logRebindComplete$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("rebind complete for ", ((LogMessage) obj).getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain2).str1 = str;
        logBuffer.commit(obtain2);
        expandableNotificationRow.mUseIncreasedCollapsedHeight = z;
        boolean z3 = (NotiRune.NOTI_INSIGNIFICANT && expandableNotificationRow.isInsignificant()) ? true : z2;
        expandableNotificationRow.mIsMinimized = z3;
        expandableNotificationRow.mPrivateLayout.getClass();
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setIsMinimized(z3);
        }
        rowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                NotificationRowContentBinder.InflationCallback inflationCallback2 = NotificationRowContentBinder.InflationCallback.this;
                if (inflationCallback2 != null) {
                    inflationCallback2.onAsyncInflationFinished(notificationEntry2);
                }
            }
        });
    }

    public final void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.AnonymousClass1 anonymousClass1) {
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        boolean rowExists = notificationEntry.rowExists();
        IconManager iconManager = this.mIconManager;
        NotificationRowBinderLogger notificationRowBinderLogger = this.mLogger;
        if (!rowExists) {
            notificationRowBinderLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationRowBinderLogger$logCreatingRow$2 notificationRowBinderLogger$logCreatingRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logCreatingRow$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("creating row for ", logMessage.getStr1(), ": ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = notificationRowBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$logCreatingRow$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.str2 = params.reason;
            logBuffer.commit(obtain);
            iconManager.createIcons(notificationEntry);
            LogMessage obtain2 = logBuffer.obtain("NotificationRowBinder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logInflatingRow$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflating row for ", ((LogMessage) obj).getStr1());
                }
            }, null);
            ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain2);
            ((RowInflaterTask) this.mRowInflaterTaskProvider.get()).inflate(this.mContext, notificationStackScrollLayout, notificationEntry, null, new NotificationRowBinderImpl$$ExternalSyntheticLambda0(this, notificationEntry, params, anonymousClass1));
            return;
        }
        notificationRowBinderLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotificationRowBinderLogger$logUpdatingRow$2 notificationRowBinderLogger$logUpdatingRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logUpdatingRow$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("updating row for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer2 = notificationRowBinderLogger.buffer;
        LogMessage obtain3 = logBuffer2.obtain("NotificationRowBinder", logLevel2, notificationRowBinderLogger$logUpdatingRow$2, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
        logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl2.str2 = params.reason;
        logBuffer2.commit(obtain3);
        iconManager.updateIcons(notificationEntry, false);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        expandableNotificationRow.mShowingPublicInitialized = false;
        expandableNotificationRow.mDismissed = false;
        NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
        if (notificationMenuRowPlugin == null || !notificationMenuRowPlugin.isMenuVisible()) {
            expandableNotificationRow.resetTranslation();
        }
        ExpandableView.OnHeightChangedListener onHeightChangedListener = expandableNotificationRow.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onReset(expandableNotificationRow);
        }
        expandableNotificationRow.requestLayout();
        expandableNotificationRow.mTargetPoint = null;
        updateRow(notificationEntry, expandableNotificationRow);
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        Context context = this.mContext;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(notificationEntry.mKey);
        if (ongoingActivityDataByKey != null) {
            OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl = new OngoingActivityLayoutCreatorImpl(context);
            RemoteViews createExpandView = ongoingActivityLayoutCreatorImpl.createExpandView(ongoingActivityDataByKey);
            createExpandView.addFlags(1);
            ongoingActivityDataByKey.mOngoingExpandView = createExpandView;
            RemoteViews createCollapsedView = ongoingActivityLayoutCreatorImpl.createCollapsedView(ongoingActivityDataByKey);
            createCollapsedView.addFlags(1);
            ongoingActivityDataByKey.mOngoingCollapsedView = createCollapsedView;
        }
        inflateContentViews(notificationEntry, params, expandableNotificationRow, anonymousClass1);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(this.mContext);
        }
    }

    public final void releaseViews(NotificationEntry notificationEntry) {
        boolean rowExists = notificationEntry.rowExists();
        NotificationRowBinderLogger notificationRowBinderLogger = this.mLogger;
        if (!rowExists) {
            notificationRowBinderLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationRowBinderLogger$logNotReleasingViewsRowDoesntExist$2 notificationRowBinderLogger$logNotReleasingViewsRowDoesntExist$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logNotReleasingViewsRowDoesntExist$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("not releasing views for ", ((LogMessage) obj).getStr1(), ": row doesn't exist");
                }
            };
            LogBuffer logBuffer = notificationRowBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$logNotReleasingViewsRowDoesntExist$2, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain);
            return;
        }
        notificationRowBinderLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotificationRowBinderLogger$logReleasingViews$2 notificationRowBinderLogger$logReleasingViews$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$logReleasingViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("releasing views for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer2 = notificationRowBinderLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotificationRowBinder", logLevel2, notificationRowBinderLogger$logReleasingViews$2, null);
        ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer2.commit(obtain2);
        RowContentBindStage rowContentBindStage = this.mRowContentBindStage;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.markContentViewsFreeable(1);
        rowContentBindParams.markContentViewsFreeable(2);
        rowContentBindParams.markContentViewsFreeable(8);
        int i = AsyncHybridViewInflation.$r8$clinit;
        com.android.systemui.Flags.notificationAsyncHybridViewInflation();
        rowContentBindStage.requestRebind(notificationEntry, null);
    }

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
                final StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4 statusBarNotificationActivityStarter$$ExternalSyntheticLambda4 = new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4(statusBarNotificationActivityStarter, notificationEntry2, 0);
                if (notificationEntry2.isBubble()) {
                    statusBarNotificationActivityStarter$$ExternalSyntheticLambda4.run();
                } else {
                    statusBarNotificationActivityStarter.performActionAfterKeyguardDismissed(notificationEntry2, new StatusBarNotificationActivityStarter.OnKeyguardDismissedAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda5
                        @Override // com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.OnKeyguardDismissedAction
                        public final void onDismiss(PendingIntent pendingIntent, boolean z2, boolean z3, boolean z4) {
                            statusBarNotificationActivityStarter$$ExternalSyntheticLambda4.run();
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
