package com.android.systemui.statusbar.notification;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.p053im.ImIntent;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationGroupAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationGroupAdapter sInstance;
    public int mPositionControlCnt;
    public SubscreenNotificationInfo mSummaryInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomViewHolder extends SubscreenParentItemViewHolder {
        public final NotificationRemoteInputManager.C25901 RemoteViewInteractionHandler;
        public final FrameLayout mContentView;

        public CustomViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mContentView = (FrameLayout) view.findViewById(R.id.custom_remote_views);
            this.RemoteViewInteractionHandler = ((NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class)).mInteractionHandler;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FooterViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout mClearAllLayout;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter$FooterViewHolder$2 */
        public final class ViewOnClickListenerC27512 implements View.OnClickListener {
            public ViewOnClickListenerC27512(SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter) {
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
                            if (!notificationEntry.getChannel().isImportantConversation() && subscreenNotificationInfo.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && SubscreenNotificationInfoManager.canViewBeCleared(notificationEntry.row)) {
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
                SystemUIAnalytics.sendEventCDLog("QPN101", "QPNE0201", ImIntent.Extras.EXTRA_FROM, "group");
            }
        }

        public FooterViewHolder(View view) {
            super(view);
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.subcreen_item_clear_all_layout);
            this.mClearAllLayout = frameLayout;
            ((TextView) view.findViewById(R.id.subcreen_item_clear_all)).semSetButtonShapeEnabled(true);
            frameLayout.getBackground().setAlpha(((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowButtonBackground() ? 0 : 255);
            view.setContentDescription(SubscreenNotificationGroupAdapter.this.mContext.getResources().getString(R.string.clear_all_notifications_text) + SubscreenNotificationGroupAdapter.this.mContext.getResources().getString(R.string.accessibility_button));
            frameLayout.setOnFocusChangeListener(new View.OnFocusChangeListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.FooterViewHolder.1
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    if (z) {
                        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                        SubscreenRecyclerView subscreenRecyclerView = subscreenNotificationGroupAdapter.mSubRoomNotification.mNotificationRecyclerView;
                        subscreenNotificationGroupAdapter.mNotificationInfoManager.getClass();
                        subscreenRecyclerView.scrollToPosition(SubscreenNotificationInfoManager.getNotificationInfoArraySize());
                    }
                }
            });
            frameLayout.setOnClickListener(new ViewOnClickListenerC27512(SubscreenNotificationGroupAdapter.this));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        return this.mNotificationInfoManager.getGroupDataArraySize() + (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5 ? 1 : 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        subscreenDeviceModelParent.getClass();
        this.mPositionControlCnt = !(subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) ? 1 : 0;
        if (i == this.mNotificationInfoManager.getGroupDataArraySize() + this.mPositionControlCnt) {
            return 1;
        }
        if (i == 0) {
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = this.mDeviceModel;
            subscreenDeviceModelParent2.getClass();
            if (!(subscreenDeviceModelParent2 instanceof SubscreenDeviceModelB5)) {
                return 2;
            }
        }
        if (((SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt)).mRow.needsRedaction() && this.mDeviceModel.isNotShwonNotificationState(((SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt)).mRow.mEntry)) {
            return 4;
        }
        return (((SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt)).mRow.mEntry.mSbn.getNotification().contentView == null || !this.mDeviceModel.isShowingRemoteView(((SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt)).mRow.mEntry.mSbn.getPackageName())) ? 0 : 5;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        if (viewHolder instanceof NotificationGroupItemViewHolder) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Group postion Item: ", i, "SubscreenNotificationGroupAdapter");
            NotificationGroupItemViewHolder notificationGroupItemViewHolder = (NotificationGroupItemViewHolder) viewHolder;
            notificationGroupItemViewHolder.mInfo = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
            notificationGroupItemViewHolder.updateTitleAndContent(subscreenNotificationGroupAdapter.mContext);
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationGroupAdapter.mDeviceModel;
            SubscreenNotificationInfo subscreenNotificationInfo = notificationGroupItemViewHolder.mInfo;
            View view = notificationGroupItemViewHolder.itemView;
            subscreenDeviceModelParent.setClock(subscreenNotificationInfo, view);
            notificationGroupItemViewHolder.setUnreadMessageCount(subscreenNotificationGroupAdapter.mContext);
            subscreenNotificationGroupAdapter.mDeviceModel.setGroupAdapterIcon(subscreenNotificationGroupAdapter.mContext, subscreenNotificationGroupAdapter, notificationGroupItemViewHolder);
            subscreenNotificationGroupAdapter.mDeviceModel.setListItemTextLayout(subscreenNotificationGroupAdapter.mContext, view);
            notificationGroupItemViewHolder.mNotificationInfoManager.addRecyclerViewItemView(notificationGroupItemViewHolder);
            notificationGroupItemViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof HeaderViewHolder) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Group postion header: ", i, "SubscreenNotificationGroupAdapter");
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            ImageView imageView = headerViewHolder.mIcon;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
            if (imageView != null) {
                imageView.clearColorFilter();
                headerViewHolder.mIcon.setBackground(null);
                headerViewHolder.mIcon.setImageDrawable(null);
                headerViewHolder.mIcon.setPadding(0, 0, 0, 0);
                SubscreenNotificationInfo subscreenNotificationInfo2 = subscreenNotificationGroupAdapter2.mSummaryInfo;
                if (subscreenNotificationInfo2.mAppIcon == null || subscreenNotificationInfo2.useSmallIcon()) {
                    headerViewHolder.mIcon.setImageDrawable(subscreenNotificationGroupAdapter2.mSummaryInfo.mIcon);
                    subscreenNotificationGroupAdapter2.mDeviceModel.updateSmallIconSquircleBg(headerViewHolder.mIcon, true, false);
                    subscreenNotificationGroupAdapter2.mDeviceModel.updateIconColor(headerViewHolder.mIcon, subscreenNotificationGroupAdapter2.mSummaryInfo.mRow.mEntry);
                } else {
                    headerViewHolder.mIcon.setImageDrawable(subscreenNotificationGroupAdapter2.mSummaryInfo.mAppIcon);
                }
            }
            headerViewHolder.mAppName.setText(subscreenNotificationGroupAdapter2.mSummaryInfo.mAppName);
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationGroupAdapter2.mDeviceModel;
            ImageView imageView2 = headerViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo3 = subscreenNotificationGroupAdapter2.mSummaryInfo;
            subscreenDeviceModelParent2.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo3);
            SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationGroupAdapter2.mDeviceModel;
            ImageView imageView3 = headerViewHolder.mSecureIcon;
            SubscreenNotificationInfo subscreenNotificationInfo4 = subscreenNotificationGroupAdapter2.mSummaryInfo;
            subscreenDeviceModelParent3.getClass();
            SubscreenDeviceModelParent.updateKnoxIcon(imageView3, subscreenNotificationInfo4);
            return;
        }
        if (!(viewHolder instanceof FooterViewHolder)) {
            if (viewHolder instanceof HideContenNotificationViewHolder) {
                HideContenNotificationViewHolder hideContenNotificationViewHolder = (HideContenNotificationViewHolder) viewHolder;
                SubscreenNotificationInfo createItemsData = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager.createItemsData(((SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt)).mRow);
                hideContenNotificationViewHolder.mInfo = createItemsData;
                hideContenNotificationViewHolder.mAppName.setText(createItemsData.getContentHiddenText(createItemsData));
                hideContenNotificationViewHolder.mNotificationInfoManager.addRecyclerViewItemView(hideContenNotificationViewHolder);
                hideContenNotificationViewHolder.initTranslationX();
                return;
            }
            if (viewHolder instanceof CustomViewHolder) {
                CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
                SubscreenNotificationInfo subscreenNotificationInfo5 = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
                SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter3 = SubscreenNotificationGroupAdapter.this;
                customViewHolder.mInfo = subscreenNotificationGroupAdapter3.mNotificationInfoManager.createItemsData(subscreenNotificationInfo5.mRow);
                FrameLayout frameLayout = customViewHolder.mContentView;
                frameLayout.removeAllViews();
                frameLayout.addView(customViewHolder.mInfo.mContentView.apply(subscreenNotificationGroupAdapter3.mContext, frameLayout, customViewHolder.RemoteViewInteractionHandler));
                customViewHolder.mNotificationInfoManager.addRecyclerViewItemView(customViewHolder);
                return;
            }
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Group postion Footer: ", i, "SubscreenNotificationGroupAdapter");
        FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter4 = SubscreenNotificationGroupAdapter.this;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager = subscreenNotificationGroupAdapter4.mNotificationInfoManager;
        int groupDataArraySize = subscreenNotificationInfoManager.getGroupDataArraySize();
        int i2 = 0;
        while (true) {
            if (i2 >= groupDataArraySize) {
                z = false;
                break;
            } else {
                if (SubscreenNotificationInfoManager.canViewBeCleared(((SubscreenNotificationInfo) subscreenNotificationInfoManager.mGroupDataArray.get(i2)).mRow)) {
                    z = true;
                    break;
                }
                i2++;
            }
        }
        FrameLayout frameLayout2 = footerViewHolder.mClearAllLayout;
        if (z) {
            frameLayout2.setEnabled(true);
            frameLayout2.setAlpha(1.0f);
            frameLayout2.setVisibility(0);
        } else {
            frameLayout2.setVisibility(8);
        }
        subscreenNotificationGroupAdapter4.mDeviceModel.setGroupAdapterFooterMargin(subscreenNotificationGroupAdapter4.mContext, footerViewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        View groupAdapterLayout = this.mDeviceModel.getGroupAdapterLayout(recyclerView, i, this.mContext);
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
