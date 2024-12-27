package com.android.systemui.statusbar.notification;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenNotificationGroupAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationGroupAdapter sInstance;
    public int mPositionControlCnt;
    public SubscreenNotificationInfo mSummaryInfo;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CustomViewHolder extends SubscreenParentItemViewHolder {
        public final FrameLayout mContentView;

        public CustomViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mContentView = (FrameLayout) view.findViewById(R.id.custom_remote_views);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.CustomViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationGroupAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        CustomViewHolder customViewHolder = CustomViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        subscreenNotificationGroupAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationGroupAdapter.mContext, customViewHolder);
                    } else {
                        CustomViewHolder customViewHolder2 = CustomViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
                        customViewHolder2.animateClickNotification(subscreenNotificationGroupAdapter2.mNotificationAnimatorManager, subscreenNotificationGroupAdapter2.mSubRoomNotification, true);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FooterViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout mClearAllLayout;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter$FooterViewHolder$2, reason: invalid class name */
        public final class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2(SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter) {
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FooterViewHolder.this.mClearAllLayout.setEnabled(false);
                FooterViewHolder.this.mClearAllLayout.setAlpha(0.5f);
                SubscreenNotificationGroupAdapter.this.mNotificationAnimatorManager.performDismissAllAnimations(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter$FooterViewHolder$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        SubscreenNotificationInfoManager subscreenNotificationInfoManager = subscreenNotificationGroupAdapter.mNotificationInfoManager;
                        SubscreenNotificationInfo subscreenNotificationInfo = subscreenNotificationGroupAdapter.mSummaryInfo;
                        subscreenNotificationInfoManager.getClass();
                        NotifCollection notifCollection = subscreenNotificationInfoManager.mNotifCollection;
                        notifCollection.getClass();
                        Assert.isMainThread();
                        ArrayList arrayList = new ArrayList(notifCollection.mReadOnlyNotificationSet);
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(i);
                            if (!notificationEntry.mRanking.getChannel().isImportantConversation() && subscreenNotificationInfo.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && SubscreenNotificationInfoManager.canViewBeCleared(notificationEntry.row)) {
                                subscreenNotificationInfoManager.removeNotification(notificationEntry);
                            }
                        }
                        subscreenNotificationInfoManager.clearAllRecyclerViewItem();
                        subscreenNotificationInfoManager.mGroupDataArray.clear();
                        if (subscreenNotificationInfoManager.mIsShownGroup) {
                            subscreenNotificationInfoManager.mNotificationGroupAdapter.mDeviceModel.hideGroupNotification();
                        }
                    }
                });
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_GROUP, SystemUIAnalytics.EID_QPNE_COVER_CLEAR_BUTTON, "from", "group");
            }
        }

        public FooterViewHolder(View view) {
            super(view);
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.subcreen_item_clear_all_layout);
            this.mClearAllLayout = frameLayout;
            ((TextView) view.findViewById(R.id.subcreen_item_clear_all)).semSetButtonShapeEnabled(true);
            frameLayout.getBackground().setAlpha(((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowButtonBackground() ? 0 : 255);
            view.setContentDescription(SubscreenNotificationGroupAdapter.this.mContext.getResources().getString(R.string.clear_all_notifications_text) + SubscreenNotificationGroupAdapter.this.mContext.getResources().getString(R.string.accessibility_button));
            frameLayout.setOnFocusChangeListener(new View.OnFocusChangeListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.FooterViewHolder.1
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    if (z) {
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        SubscreenRecyclerView subscreenRecyclerView = subscreenNotificationGroupAdapter.mSubRoomNotification.mNotificationRecyclerView;
                        subscreenNotificationGroupAdapter.mNotificationInfoManager.getClass();
                        subscreenRecyclerView.scrollToPosition(SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize());
                    }
                }
            });
            frameLayout.setOnClickListener(new AnonymousClass2(SubscreenNotificationGroupAdapter.this));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView mAppName;
        public ImageView mBackButton;
        public ImageView mIcon;
        public ImageView mSecureIcon;
        public ImageView mTwoPhoneIcon;

        public HeaderViewHolder(View view) {
            super(view);
            SubscreenNotificationGroupAdapter.this.mDeviceModel.initGroupAdapterHeaderViewHolder(SubscreenNotificationGroupAdapter.this.mContext, view, SubscreenNotificationGroupAdapter.this, this);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HideContenNotificationViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;

        public HideContenNotificationViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.hide_content_app_name);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.HideContenNotificationViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationGroupAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        HideContenNotificationViewHolder hideContenNotificationViewHolder = HideContenNotificationViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        subscreenNotificationGroupAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationGroupAdapter.mContext, hideContenNotificationViewHolder);
                    } else {
                        HideContenNotificationViewHolder hideContenNotificationViewHolder2 = HideContenNotificationViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
                        hideContenNotificationViewHolder2.animateClickNotification(subscreenNotificationGroupAdapter2.mNotificationAnimatorManager, subscreenNotificationGroupAdapter2.mSubRoomNotification, true);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotificationGroupItemViewHolder extends SubscreenParentItemViewHolder {
        public NotificationGroupItemViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.NotificationGroupItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationGroupAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        NotificationGroupItemViewHolder notificationGroupItemViewHolder = NotificationGroupItemViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        subscreenNotificationGroupAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationGroupAdapter.mContext, notificationGroupItemViewHolder);
                    } else {
                        NotificationGroupItemViewHolder notificationGroupItemViewHolder2 = NotificationGroupItemViewHolder.this;
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
                        notificationGroupItemViewHolder2.animateClickNotification(subscreenNotificationGroupAdapter2.mNotificationAnimatorManager, subscreenNotificationGroupAdapter2.mSubRoomNotification, false);
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        subscreenDeviceModelParent.getClass();
        return this.mNotificationInfoManager.mGroupDataArray.size() + (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5 ? 1 : 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        subscreenDeviceModelParent.getClass();
        int i2 = !(subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) ? 1 : 0;
        this.mPositionControlCnt = i2;
        int i3 = i - i2;
        int size = this.mNotificationInfoManager.mGroupDataArray.size();
        SubscreenNotificationInfo subscreenNotificationInfo = (i3 < 0 || i3 >= size) ? null : (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i3);
        if (i == size + this.mPositionControlCnt) {
            return 1;
        }
        if (i == 0) {
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
            subscreenDeviceModelParent2.getClass();
            if (!(subscreenDeviceModelParent2 instanceof SubscreenDeviceModelB5)) {
                return 2;
            }
        }
        if (subscreenNotificationInfo.mRow.needsRedaction() && this.mDeviceModel.isNotShwonNotificationState(subscreenNotificationInfo.mRow.mEntry)) {
            return 4;
        }
        return (subscreenNotificationInfo.mContentView == null || !this.mDeviceModel.isShowingRemoteView(subscreenNotificationInfo.mPkg)) ? 0 : 5;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof NotificationGroupItemViewHolder) {
            NotificationGroupItemViewHolder notificationGroupItemViewHolder = (NotificationGroupItemViewHolder) viewHolder;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Group position Item: ", "SubscreenNotificationGroupAdapter");
            notificationGroupItemViewHolder.mInfo = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
            notificationGroupItemViewHolder.updateTitleAndContent(subscreenNotificationGroupAdapter.mContext);
            subscreenNotificationGroupAdapter.mDeviceModel.setClock(notificationGroupItemViewHolder.mInfo, notificationGroupItemViewHolder.itemView);
            notificationGroupItemViewHolder.setUnreadMessageCount(subscreenNotificationGroupAdapter.mContext);
            subscreenNotificationGroupAdapter.mDeviceModel.setGroupAdapterIcon(subscreenNotificationGroupAdapter.mContext, subscreenNotificationGroupAdapter, notificationGroupItemViewHolder);
            subscreenNotificationGroupAdapter.mDeviceModel.setListItemTextLayout(subscreenNotificationGroupAdapter.mContext, notificationGroupItemViewHolder.itemView);
            notificationGroupItemViewHolder.mNotificationInfoManager.addRecyclerViewItemView(notificationGroupItemViewHolder);
            notificationGroupItemViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Group position header: ", "SubscreenNotificationGroupAdapter");
            ImageView imageView = headerViewHolder.mIcon;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
            if (imageView != null) {
                imageView.clearColorFilter();
                headerViewHolder.mIcon.setBackground(null);
                headerViewHolder.mIcon.setImageDrawable(null);
                headerViewHolder.mIcon.setPadding(0, 0, 0, 0);
                SubscreenNotificationInfo subscreenNotificationInfo = subscreenNotificationGroupAdapter2.mSummaryInfo;
                if (subscreenNotificationInfo.mAppIcon == null || subscreenNotificationInfo.useSmallIcon()) {
                    headerViewHolder.mIcon.setImageDrawable(subscreenNotificationGroupAdapter2.mSummaryInfo.mIcon);
                    subscreenNotificationGroupAdapter2.mDeviceModel.updateSmallIconSquircleBg(headerViewHolder.mIcon, true, false);
                    subscreenNotificationGroupAdapter2.mDeviceModel.updateIconColor(headerViewHolder.mIcon, subscreenNotificationGroupAdapter2.mSummaryInfo.mRow.mEntry);
                } else {
                    headerViewHolder.mIcon.setImageDrawable(subscreenNotificationGroupAdapter2.mSummaryInfo.mAppIcon);
                }
            }
            headerViewHolder.mAppName.setText(subscreenNotificationGroupAdapter2.mSummaryInfo.mAppName);
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationGroupAdapter2.mDeviceModel;
            ImageView imageView2 = headerViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo2 = subscreenNotificationGroupAdapter2.mSummaryInfo;
            subscreenDeviceModelParent.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo2);
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationGroupAdapter2.mDeviceModel;
            ImageView imageView3 = headerViewHolder.mSecureIcon;
            SubscreenNotificationInfo subscreenNotificationInfo3 = subscreenNotificationGroupAdapter2.mSummaryInfo;
            subscreenDeviceModelParent2.getClass();
            SubscreenDeviceModelParent.updateKnoxIcon(imageView3, subscreenNotificationInfo3);
            return;
        }
        if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Group position Footer: ", "SubscreenNotificationGroupAdapter");
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter3 = SubscreenNotificationGroupAdapter.this;
            SubscreenNotificationInfoManager subscreenNotificationInfoManager = subscreenNotificationGroupAdapter3.mNotificationInfoManager;
            int size = subscreenNotificationInfoManager.mGroupDataArray.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    footerViewHolder.mClearAllLayout.setVisibility(8);
                    break;
                } else {
                    if (SubscreenNotificationInfoManager.canViewBeCleared(((SubscreenNotificationInfo) subscreenNotificationInfoManager.mGroupDataArray.get(i2)).mRow)) {
                        footerViewHolder.mClearAllLayout.setEnabled(true);
                        footerViewHolder.mClearAllLayout.setAlpha(1.0f);
                        footerViewHolder.mClearAllLayout.setVisibility(0);
                        break;
                    }
                    i2++;
                }
            }
            subscreenNotificationGroupAdapter3.mDeviceModel.setGroupAdapterFooterMargin(subscreenNotificationGroupAdapter3.mContext, footerViewHolder);
            return;
        }
        if (viewHolder instanceof HideContenNotificationViewHolder) {
            HideContenNotificationViewHolder hideContenNotificationViewHolder = (HideContenNotificationViewHolder) viewHolder;
            SubscreenNotificationInfo subscreenNotificationInfo4 = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            hideContenNotificationViewHolder.mInfo = subscreenNotificationInfo4;
            hideContenNotificationViewHolder.mAppName.setText(subscreenNotificationInfo4.getContentHiddenText());
            hideContenNotificationViewHolder.mNotificationInfoManager.addRecyclerViewItemView(hideContenNotificationViewHolder);
            hideContenNotificationViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
            SubscreenNotificationInfo subscreenNotificationInfo5 = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter4 = SubscreenNotificationGroupAdapter.this;
            customViewHolder.mInfo = subscreenNotificationGroupAdapter4.mNotificationInfoManager.createItemsData(subscreenNotificationInfo5.mRow);
            customViewHolder.mContentView.removeAllViews();
            customViewHolder.mContentView.addView(customViewHolder.mInfo.mContentView.apply(subscreenNotificationGroupAdapter4.mContext, customViewHolder.mContentView, ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).remoteInputManager.mInteractionHandler));
            customViewHolder.mNotificationInfoManager.addRecyclerViewItemView(customViewHolder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        View groupAdapterLayout = this.mDeviceModel.getGroupAdapterLayout(viewGroup, i, this.mContext);
        if (i == 0) {
            return new NotificationGroupItemViewHolder(groupAdapterLayout);
        }
        if (i == 1) {
            return new FooterViewHolder(groupAdapterLayout);
        }
        if (i == 2) {
            return new HeaderViewHolder(groupAdapterLayout);
        }
        if (i == 4) {
            return new HideContenNotificationViewHolder(groupAdapterLayout);
        }
        if (i == 5) {
            return new CustomViewHolder(groupAdapterLayout);
        }
        return null;
    }
}
