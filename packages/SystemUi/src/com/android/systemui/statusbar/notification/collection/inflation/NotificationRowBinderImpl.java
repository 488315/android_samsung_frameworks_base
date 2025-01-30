package com.android.systemui.statusbar.notification.collection.inflation;

import android.content.Context;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationClicker;
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
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Objects;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationRowBinderImpl {
    public BindRowCallback mBindRowCallback;
    public final Context mContext;
    public final ExpandableNotificationRowComponent.Builder mExpandableNotificationRowComponentBuilder;
    public final FeatureFlags mFeatureFlags;
    public final IconManager mIconManager;
    public NotificationListContainer mListContainer;
    public final NotificationMessagingUtil mMessagingUtil;
    public final NotifBindPipeline mNotifBindPipeline;
    public NotificationClicker mNotificationClicker;
    public final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public NotificationPresenter mPresenter;
    public final RowContentBindStage mRowContentBindStage;
    public final Provider mRowInflaterTaskProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BindRowCallback {
    }

    public NotificationRowBinderImpl(Context context, NotificationMessagingUtil notificationMessagingUtil, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage, Provider provider, ExpandableNotificationRowComponent.Builder builder, IconManager iconManager, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
        this.mMessagingUtil = notificationMessagingUtil;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mRowInflaterTaskProvider = provider;
        this.mExpandableNotificationRowComponentBuilder = builder;
        this.mIconManager = iconManager;
        this.mFeatureFlags = featureFlags;
    }

    public final void inflateContentViews(NotificationEntry notificationEntry, NotifInflater.Params params, ExpandableNotificationRow expandableNotificationRow, final NotificationRowContentBinder.InflationCallback inflationCallback) {
        boolean isImportantMessaging = this.mMessagingUtil.isImportantMessaging(notificationEntry.mSbn, notificationEntry.getImportance());
        boolean z = params.isLowPriority;
        expandableNotificationRow.getClass();
        RowContentBindStage rowContentBindStage = this.mRowContentBindStage;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.requireContentViews(1);
        rowContentBindParams.requireContentViews(2);
        if (rowContentBindParams.mUseIncreasedHeight != isImportantMessaging) {
            rowContentBindParams.mDirtyContentViews = 1 | rowContentBindParams.mDirtyContentViews;
        }
        rowContentBindParams.mUseIncreasedHeight = isImportantMessaging;
        if (rowContentBindParams.mUseLowPriority != z) {
            rowContentBindParams.mDirtyContentViews |= 3;
        }
        rowContentBindParams.mUseLowPriority = z;
        if (((NotificationLockscreenUserManagerImpl) this.mNotificationLockscreenUserManager).needsRedaction(notificationEntry)) {
            rowContentBindParams.requireContentViews(8);
        } else {
            rowContentBindParams.markContentViewsFreeable(8);
        }
        rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews;
        expandableNotificationRow.mUseIncreasedCollapsedHeight = isImportantMessaging;
        expandableNotificationRow.mIsLowPriority = z;
        expandableNotificationRow.mPrivateLayout.getClass();
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.mIsLowPriority = z;
            if (notificationChildrenContainer.mContainingNotification != null) {
                notificationChildrenContainer.recreateLowPriorityHeader(null);
                notificationChildrenContainer.updateHeaderVisibility(false, false);
            }
            boolean z2 = notificationChildrenContainer.mUserLocked;
            if (z2) {
                notificationChildrenContainer.setUserLocked(z2);
            }
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

    public final void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.C27911 c27911) {
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        boolean rowExists = notificationEntry.rowExists();
        Context context = this.mContext;
        IconManager iconManager = this.mIconManager;
        if (!rowExists) {
            iconManager.createIcons(notificationEntry);
            ((RowInflaterTask) this.mRowInflaterTaskProvider.get()).inflate(context, notificationStackScrollLayout, notificationEntry, new NotificationRowBinderImpl$$ExternalSyntheticLambda0(this, notificationEntry, params, c27911));
            return;
        }
        iconManager.updateIcons(notificationEntry);
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
        inflateContentViews(notificationEntry, params, expandableNotificationRow, c27911);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(context);
        }
    }

    public final void updateRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        int i = notificationEntry.targetSdk;
        boolean z = i >= 9 && i < 21;
        for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
            notificationContentView.mLegacy = z;
            notificationContentView.updateLegacy();
        }
        NotificationClicker notificationClicker = this.mNotificationClicker;
        Objects.requireNonNull(notificationClicker);
        expandableNotificationRow.setOnClickListener(notificationClicker);
        expandableNotificationRow.mOnDragSuccessListener = notificationClicker.mOnDragSuccessListener;
    }
}
