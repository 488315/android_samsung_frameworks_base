package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.Notification;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenNotificationListAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationListAdapter sInstance;
    public Animator mFooterAnimator;
    public FooterViewHolder mFooterViewHolder;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CustomViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;
        public final FrameLayout mContentView;
        public final View mNormalView;

        public CustomViewHolder(View view) {
            super(view);
            this.mListAdapter = SubscreenNotificationListAdapter.this;
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
            this.mContentView = (FrameLayout) view.findViewById(R.id.custom_remote_views);
            this.mNormalView = view.findViewById(R.id.custom_normal_views);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.CustomViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationListAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        CustomViewHolder customViewHolder = CustomViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                        subscreenNotificationListAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationListAdapter.mContext, customViewHolder);
                    } else {
                        CustomViewHolder customViewHolder2 = CustomViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter2 = SubscreenNotificationListAdapter.this;
                        customViewHolder2.animateClickNotification(subscreenNotificationListAdapter2.mNotificationAnimatorManager, subscreenNotificationListAdapter2.mSubRoomNotification, true);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FooterViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout mClearAllLayout;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$FooterViewHolder$2, reason: invalid class name */
        public final class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2(SubscreenNotificationListAdapter subscreenNotificationListAdapter) {
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("SubscreenNotificationListAdapter", "FooterViewHolder - mClearAllLayout- clear all");
                FooterViewHolder.this.mClearAllLayout.setEnabled(false);
                FooterViewHolder.this.mClearAllLayout.setAlpha(0.5f);
                SubscreenNotificationListAdapter.this.mNotificationAnimatorManager.performDismissAllAnimations(new SubscreenNotificationListAdapter$$ExternalSyntheticLambda0(this, 2));
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_LIST, SystemUIAnalytics.EID_QPNE_COVER_CLEAR_BUTTON, "from", SystemUIAnalytics.QPNE_VID_COVER_ALL);
            }
        }

        public FooterViewHolder(View view) {
            super(view);
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.subcreen_item_clear_all_layout);
            this.mClearAllLayout = frameLayout;
            ((TextView) view.findViewById(R.id.subcreen_item_clear_all)).semSetButtonShapeEnabled(true);
            frameLayout.getBackground().setAlpha(((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowButtonBackground() ? 0 : 255);
            SubscreenNotificationListAdapter.this.mFooterViewHolder = this;
            frameLayout.setContentDescription(SubscreenNotificationListAdapter.this.mContext.getResources().getString(R.string.clear_all_notifications_text) + SubscreenNotificationListAdapter.this.mContext.getResources().getString(R.string.accessibility_button));
            frameLayout.setOnFocusChangeListener(new View.OnFocusChangeListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.FooterViewHolder.1
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    if (z) {
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                        SubscreenRecyclerView subscreenRecyclerView = subscreenNotificationListAdapter.mSubRoomNotification.mNotificationRecyclerView;
                        subscreenNotificationListAdapter.mNotificationInfoManager.getClass();
                        subscreenRecyclerView.scrollToPosition(SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize());
                    }
                }
            });
            frameLayout.setOnClickListener(new AnonymousClass2(SubscreenNotificationListAdapter.this));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class GroupViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;
        public final LinearLayout mContentLayout;

        public GroupViewHolder(View view) {
            super(view);
            this.mListAdapter = SubscreenNotificationListAdapter.this;
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.group_app_name);
            this.mContentLayout = (LinearLayout) view.findViewById(R.id.group_child_layout);
            this.mNotiGroupCount = (TextView) view.findViewById(R.id.noti_group_count);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.GroupViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GroupViewHolder groupViewHolder = GroupViewHolder.this;
                    SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                    groupViewHolder.animateClickNotification(subscreenNotificationListAdapter.mNotificationAnimatorManager, subscreenNotificationListAdapter.mSubRoomNotification, false);
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HideContentNotificationViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;

        public HideContentNotificationViewHolder(View view) {
            super(view);
            this.mListAdapter = SubscreenNotificationListAdapter.this;
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.hide_content_app_name);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.HideContentNotificationViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationListAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        HideContentNotificationViewHolder hideContentNotificationViewHolder = HideContentNotificationViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                        subscreenNotificationListAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationListAdapter.mContext, hideContentNotificationViewHolder);
                    } else {
                        HideContentNotificationViewHolder hideContentNotificationViewHolder2 = HideContentNotificationViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter2 = SubscreenNotificationListAdapter.this;
                        hideContentNotificationViewHolder2.animateClickNotification(subscreenNotificationListAdapter2.mNotificationAnimatorManager, subscreenNotificationListAdapter2.mSubRoomNotification, true);
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoNotificationViewHolder extends RecyclerView.ViewHolder {
        public NoNotificationViewHolder(SubscreenNotificationListAdapter subscreenNotificationListAdapter, View view) {
            super(view);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotificationListItemViewHolder extends SubscreenParentItemViewHolder {
        public NotificationListItemViewHolder(View view) {
            super(view);
            this.mListAdapter = SubscreenNotificationListAdapter.this;
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.NotificationListItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = SubscreenNotificationListAdapter.this.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    if (subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) {
                        NotificationListItemViewHolder notificationListItemViewHolder = NotificationListItemViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                        subscreenNotificationListAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationListAdapter.mContext, notificationListItemViewHolder);
                    } else {
                        NotificationListItemViewHolder notificationListItemViewHolder2 = NotificationListItemViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter2 = SubscreenNotificationListAdapter.this;
                        notificationListItemViewHolder2.animateClickNotification(subscreenNotificationListAdapter2.mNotificationAnimatorManager, subscreenNotificationListAdapter2.mSubRoomNotification, false);
                    }
                }
            });
        }
    }

    private SubscreenNotificationListAdapter() {
    }

    public static SubscreenNotificationListAdapter getInstance() {
        if (sInstance == null) {
            sInstance = new SubscreenNotificationListAdapter();
        }
        return sInstance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        this.mNotificationInfoManager.getClass();
        return SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SubscreenNotificationInfo subscreenNotificationInfo;
        boolean z;
        this.mDeviceModel.setListAdpaterPosition(i);
        this.mNotificationInfoManager.getClass();
        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
        if (subscreenNotificationInfoListSize <= 0 || i == subscreenNotificationInfoListSize) {
            subscreenNotificationInfo = null;
            z = false;
        } else {
            this.mNotificationInfoManager.getClass();
            subscreenNotificationInfo = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i);
            z = this.mDeviceModel.isNotShwonNotificationState(subscreenNotificationInfo.mRow.mEntry);
        }
        if (subscreenNotificationInfoListSize == 0) {
            return 3;
        }
        if (i == subscreenNotificationInfoListSize) {
            return 1;
        }
        if (subscreenNotificationInfo.mRow.needsRedaction() && z) {
            return 4;
        }
        if (subscreenNotificationInfo.mContentView != null) {
            Notification notification2 = subscreenNotificationInfo.mSbn.getNotification();
            Notification.Action[] actionArr = notification2.actions;
            if (actionArr == null) {
                return 2;
            }
            boolean z2 = false;
            for (Notification.Action action : actionArr) {
                if (action.getSemanticAction() == 10) {
                    z2 = true;
                }
            }
            boolean equals = "missed_call".equals(notification2.category);
            if (!z2 || !equals) {
                return 2;
            }
        }
        if (!subscreenNotificationInfo.mGroupSummary) {
            return 0;
        }
        ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo.mRow;
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer == null || notificationChildrenContainer.getNotificationChildCount() != 1) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            subscreenDeviceModelParent.getClass();
            if (!SubscreenDeviceModelParent.isOnlyGroupSummary(notificationEntry)) {
                return 5;
            }
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SubscreenNotificationInfo subscreenNotificationInfo;
        this.mNotificationInfoManager.getClass();
        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
        if (subscreenNotificationInfoListSize <= 0 || i == subscreenNotificationInfoListSize) {
            subscreenNotificationInfo = null;
        } else {
            this.mNotificationInfoManager.getClass();
            subscreenNotificationInfo = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i);
        }
        if (viewHolder instanceof NotificationListItemViewHolder) {
            NotificationListItemViewHolder notificationListItemViewHolder = (NotificationListItemViewHolder) viewHolder;
            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo.mRow;
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
            if (z) {
                subscreenNotificationListAdapter.getClass();
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                if (notificationChildrenContainer != null && notificationChildrenContainer.getNotificationChildCount() == 1) {
                    subscreenNotificationInfo = subscreenNotificationListAdapter.mNotificationInfoManager.createItemsData((ExpandableNotificationRow) ((ArrayList) expandableNotificationRow.mChildrenContainer.mAttachedChildren).get(0));
                }
            }
            notificationListItemViewHolder.mInfo = subscreenNotificationInfo;
            notificationListItemViewHolder.updateTitleAndContent(subscreenNotificationListAdapter.mContext);
            subscreenNotificationListAdapter.mDeviceModel.setClock(notificationListItemViewHolder.mInfo, notificationListItemViewHolder.itemView);
            notificationListItemViewHolder.setUnreadMessageCount(subscreenNotificationListAdapter.mContext);
            notificationListItemViewHolder.setIconView(notificationListItemViewHolder.mListAdapter, true);
            subscreenNotificationListAdapter.mDeviceModel.setRightIcon(subscreenNotificationListAdapter.mContext, notificationListItemViewHolder.mInfo, notificationListItemViewHolder.itemView);
            subscreenNotificationListAdapter.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter.mContext, notificationListItemViewHolder.itemView);
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationListAdapter.mDeviceModel;
            ImageView imageView = notificationListItemViewHolder.mSecureIcon;
            SubscreenNotificationInfo subscreenNotificationInfo2 = notificationListItemViewHolder.mInfo;
            subscreenDeviceModelParent.getClass();
            SubscreenDeviceModelParent.updateKnoxIcon(imageView, subscreenNotificationInfo2);
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationListAdapter.mDeviceModel;
            ImageView imageView2 = notificationListItemViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo3 = notificationListItemViewHolder.mInfo;
            subscreenDeviceModelParent2.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo3);
            notificationListItemViewHolder.mNotificationInfoManager.addRecyclerViewItemView(notificationListItemViewHolder);
            notificationListItemViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter2 = SubscreenNotificationListAdapter.this;
            subscreenNotificationListAdapter2.mNotificationInfoManager.getClass();
            if (SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() != 0) {
                subscreenNotificationListAdapter2.mNotificationInfoManager.getClass();
                if (SubscreenNotificationInfoManager.checkRemoveNotification()) {
                    footerViewHolder.mClearAllLayout.setEnabled(true);
                    footerViewHolder.mClearAllLayout.setAlpha(1.0f);
                    footerViewHolder.mClearAllLayout.setVisibility(0);
                    return;
                }
            }
            footerViewHolder.mClearAllLayout.setVisibility(8);
            return;
        }
        if (viewHolder instanceof HideContentNotificationViewHolder) {
            HideContentNotificationViewHolder hideContentNotificationViewHolder = (HideContentNotificationViewHolder) viewHolder;
            hideContentNotificationViewHolder.mInfo = subscreenNotificationInfo;
            hideContentNotificationViewHolder.mAppName.setText(subscreenNotificationInfo.mAppName);
            hideContentNotificationViewHolder.setIconView(hideContentNotificationViewHolder.mListAdapter, false);
            SubscreenNotificationListAdapter subscreenNotificationListAdapter3 = SubscreenNotificationListAdapter.this;
            subscreenNotificationListAdapter3.mDeviceModel.setClock(hideContentNotificationViewHolder.mInfo, hideContentNotificationViewHolder.itemView);
            SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationListAdapter3.mDeviceModel;
            ImageView imageView3 = hideContentNotificationViewHolder.mSecureIcon;
            SubscreenNotificationInfo subscreenNotificationInfo4 = hideContentNotificationViewHolder.mInfo;
            subscreenDeviceModelParent3.getClass();
            SubscreenDeviceModelParent.updateKnoxIcon(imageView3, subscreenNotificationInfo4);
            SubscreenDeviceModelParent subscreenDeviceModelParent4 = subscreenNotificationListAdapter3.mDeviceModel;
            ImageView imageView4 = hideContentNotificationViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo5 = hideContentNotificationViewHolder.mInfo;
            subscreenDeviceModelParent4.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView4, subscreenNotificationInfo5);
            hideContentNotificationViewHolder.mNotificationInfoManager.addRecyclerViewItemView(hideContentNotificationViewHolder);
            hideContentNotificationViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
            customViewHolder.mInfo = subscreenNotificationInfo;
            customViewHolder.setIconView(customViewHolder.mListAdapter, false);
            SubscreenNotificationListAdapter subscreenNotificationListAdapter4 = SubscreenNotificationListAdapter.this;
            if (subscreenNotificationListAdapter4.mDeviceModel.isShowingRemoteView(customViewHolder.mInfo.mPkg)) {
                customViewHolder.mContentView.setVisibility(0);
                customViewHolder.mNormalView.setVisibility(8);
                if (customViewHolder.mContentView.getChildCount() > 0) {
                    customViewHolder.mInfo.setItemsData(subscreenNotificationInfo.mRow);
                }
                customViewHolder.mContentView.removeAllViews();
                customViewHolder.mContentView.addView(customViewHolder.mInfo.mContentView.apply(subscreenNotificationListAdapter4.mContext, customViewHolder.mContentView, ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).remoteInputManager.mInteractionHandler));
            } else {
                customViewHolder.mContentView.setVisibility(8);
                customViewHolder.mNormalView.setVisibility(0);
                String title = customViewHolder.mInfo.getTitle();
                customViewHolder.mAppName.setText((title == null || title.trim().isEmpty()) ? customViewHolder.mInfo.mAppName : customViewHolder.mInfo.getTitle());
                subscreenNotificationListAdapter4.mDeviceModel.setClock(customViewHolder.mInfo, customViewHolder.itemView);
                SubscreenDeviceModelParent subscreenDeviceModelParent5 = subscreenNotificationListAdapter4.mDeviceModel;
                ImageView imageView5 = customViewHolder.mSecureIcon;
                SubscreenNotificationInfo subscreenNotificationInfo6 = customViewHolder.mInfo;
                subscreenDeviceModelParent5.getClass();
                SubscreenDeviceModelParent.updateKnoxIcon(imageView5, subscreenNotificationInfo6);
                SubscreenDeviceModelParent subscreenDeviceModelParent6 = subscreenNotificationListAdapter4.mDeviceModel;
                ImageView imageView6 = customViewHolder.mTwoPhoneIcon;
                SubscreenNotificationInfo subscreenNotificationInfo7 = customViewHolder.mInfo;
                subscreenDeviceModelParent6.getClass();
                SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView6, subscreenNotificationInfo7);
                subscreenNotificationListAdapter4.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter4.mContext, customViewHolder.itemView);
            }
            customViewHolder.mNotificationInfoManager.addRecyclerViewItemView(customViewHolder);
            customViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof GroupViewHolder) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
            groupViewHolder.mInfo = subscreenNotificationInfo;
            groupViewHolder.setIconView(groupViewHolder.mListAdapter, false);
            SubscreenNotificationListAdapter subscreenNotificationListAdapter5 = SubscreenNotificationListAdapter.this;
            subscreenNotificationListAdapter5.mDeviceModel.updateShadowIconColor(groupViewHolder.itemView, groupViewHolder.mInfo.mRow.mEntry);
            subscreenNotificationListAdapter5.mDeviceModel.setClock(groupViewHolder.mInfo, groupViewHolder.itemView);
            TextView textView = groupViewHolder.mAppName;
            if (textView != null) {
                textView.setText(groupViewHolder.mInfo.mAppName);
            }
            TextView textView2 = groupViewHolder.mNotiGroupCount;
            SubscreenNotificationInfo subscreenNotificationInfo8 = groupViewHolder.mInfo;
            ExpandableNotificationRow expandableNotificationRow2 = subscreenNotificationInfo8.mRow;
            if (expandableNotificationRow2.mIsSummaryWithChildren) {
                subscreenNotificationInfo8.mChildCount = expandableNotificationRow2.mChildrenContainer.mUntruncatedChildCount;
            }
            textView2.setText(Integer.toString(subscreenNotificationInfo8.mChildCount));
            if (groupViewHolder.mContentLayout.getChildCount() > 0) {
                groupViewHolder.mContentLayout.removeAllViews();
            }
            SubscreenNotificationInfo subscreenNotificationInfo9 = groupViewHolder.mInfo;
            ExpandableNotificationRow expandableNotificationRow3 = subscreenNotificationInfo9.mRow;
            if (expandableNotificationRow3.mIsSummaryWithChildren) {
                subscreenNotificationInfo9.mChildCount = expandableNotificationRow3.mChildrenContainer.mUntruncatedChildCount;
            }
            int i2 = subscreenNotificationInfo9.mChildCount;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "addGroupItems - child Count : ", "SubscreenNotificationListAdapter");
            ExpandableNotificationRow expandableNotificationRow4 = groupViewHolder.mInfo.mRow;
            if (expandableNotificationRow4.mIsSummaryWithChildren && i2 > 1) {
                NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow4.mChildrenContainer;
                for (int i3 = 0; i3 < 2; i3++) {
                    ExpandableNotificationRow expandableNotificationRow5 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer2.mAttachedChildren).get(i3);
                    SubscreenNotificationInfo createItemsData = subscreenNotificationListAdapter5.mNotificationInfoManager.createItemsData(expandableNotificationRow5);
                    View inflate = LayoutInflater.from(subscreenNotificationListAdapter5.mContext).inflate(subscreenNotificationListAdapter5.mDeviceModel.getListAdapterGroupItemResource(), (ViewGroup) groupViewHolder.mContentLayout, false);
                    TextView textView3 = (TextView) inflate.findViewById(R.id.group_item_title);
                    TextView textView4 = (TextView) inflate.findViewById(R.id.group_item_content);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    if (expandableNotificationRow5.needsRedaction() && subscreenNotificationListAdapter5.mDeviceModel.isNotShwonNotificationState(expandableNotificationRow5.mEntry)) {
                        textView3.setLayoutParams(layoutParams);
                        textView3.setText(createItemsData.getContentHiddenText());
                        textView4.setVisibility(8);
                    } else {
                        if (createItemsData.getTitle() == null) {
                            textView3.setVisibility(8);
                        } else {
                            textView3.setVisibility(0);
                            textView3.setText(createItemsData.getTitle());
                        }
                        String str = createItemsData.mContent;
                        if (str == null) {
                            textView3.setLayoutParams(layoutParams);
                            textView4.setVisibility(8);
                        } else {
                            textView4.setText(str);
                            textView4.setVisibility(0);
                        }
                    }
                    groupViewHolder.mContentLayout.addView(inflate);
                }
            }
            if (groupViewHolder.mAppName != null) {
                Resources resources = subscreenNotificationListAdapter5.mContext.getResources();
                int i4 = groupViewHolder.mInfo.mChildCount;
                groupViewHolder.itemView.setContentDescription(groupViewHolder.mAppName.getText().toString() + resources.getQuantityString(R.plurals.plural_notification_count, i4, Integer.valueOf(i4)).toString());
            }
            SubscreenDeviceModelParent subscreenDeviceModelParent7 = subscreenNotificationListAdapter5.mDeviceModel;
            ImageView imageView7 = groupViewHolder.mSecureIcon;
            SubscreenNotificationInfo subscreenNotificationInfo10 = groupViewHolder.mInfo;
            subscreenDeviceModelParent7.getClass();
            SubscreenDeviceModelParent.updateKnoxIcon(imageView7, subscreenNotificationInfo10);
            SubscreenDeviceModelParent subscreenDeviceModelParent8 = subscreenNotificationListAdapter5.mDeviceModel;
            ImageView imageView8 = groupViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo11 = groupViewHolder.mInfo;
            subscreenDeviceModelParent8.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView8, subscreenNotificationInfo11);
            groupViewHolder.mNotificationInfoManager.addRecyclerViewItemView(groupViewHolder);
            groupViewHolder.initTranslationX();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        View listAdapterLayout = this.mDeviceModel.getListAdapterLayout(viewGroup, i, this.mContext);
        if (i == 0) {
            return new NotificationListItemViewHolder(listAdapterLayout);
        }
        if (i == 1) {
            return new FooterViewHolder(listAdapterLayout);
        }
        if (i == 3) {
            return new NoNotificationViewHolder(this, listAdapterLayout);
        }
        if (i == 2) {
            return new CustomViewHolder(listAdapterLayout);
        }
        if (i == 4) {
            return new HideContentNotificationViewHolder(listAdapterLayout);
        }
        if (i == 5) {
            return new GroupViewHolder(listAdapterLayout);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        if (list.isEmpty()) {
            onBindViewHolder(viewHolder, i);
            return;
        }
        for (Object obj : list) {
            if ((obj instanceof String) && ((String) obj).equals("click")) {
                boolean z = viewHolder instanceof HideContentNotificationViewHolder;
            }
        }
    }
}
