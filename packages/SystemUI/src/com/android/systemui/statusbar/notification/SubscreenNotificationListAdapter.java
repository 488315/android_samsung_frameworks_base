package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;

/* loaded from: classes3.dex */
public class SubscreenNotificationListAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationListAdapter sInstance;
    public Animator mFooterAnimator;
    public FooterViewHolder mFooterViewHolder;
    public final ArrayList mGroupViewHolderArray = new ArrayList();
    public Boolean mIsCustomNotificationUpdated = Boolean.FALSE;

    public class CustomViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;
        public final FrameLayout mContentView;
        public final ViewGroup mIconLayout;
        public final View mNormalView;

        public CustomViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
            this.mContentView = (FrameLayout) view.findViewById(R.id.custom_remote_views);
            this.mNormalView = view.findViewById(R.id.custom_normal_views);
            this.mIconLayout = (ViewGroup) view.findViewById(R.id.subscreen_noti_list_icon_layout);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.CustomViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (SubscreenNotificationListAdapter.this.mDeviceModel.isKeyguardUsed()) {
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

        public final void setNormalViewLayout() {
            this.mContentView.setVisibility(8);
            this.mNormalView.setVisibility(0);
            String title = this.mInfo.getTitle();
            this.mAppName.setText((title == null || title.trim().isEmpty()) ? this.mInfo.mAppName : this.mInfo.getTitle());
            SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
            subscreenNotificationListAdapter.mDeviceModel.setClock(this.mInfo, this.itemView);
            subscreenNotificationListAdapter.mDeviceModel.updateKnoxIcon(this.mSecureIcon, this.mInfo);
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationListAdapter.mDeviceModel;
            ImageView imageView = this.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
            subscreenDeviceModelParent.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView, subscreenNotificationInfo);
            subscreenNotificationListAdapter.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter.mContext, this.itemView);
        }
    }

    public class DnDTextViewHolder extends RecyclerView.ViewHolder {
        public DnDTextViewHolder(SubscreenNotificationListAdapter subscreenNotificationListAdapter, View view) {
            super(view);
            TextView textView = (TextView) view.findViewById(R.id.notification_dnd_status_text);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = subscreenNotificationListAdapter.mController.notificationStackScrollLayoutController;
            textView.setText(notificationStackScrollLayoutController.mView.getDndStatusText(notificationStackScrollLayoutController.mZenModeController));
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout mClearAllLayout;

        /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter$FooterViewHolder$2, reason: invalid class name */
        public class AnonymousClass2 implements View.OnClickListener {
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

        public FooterViewHolder(View view) throws Resources.NotFoundException {
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

    public class GroupViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;
        public final LinearLayout mContentLayout;
        public final FrameLayout mNotiGroupCountLayout;

        public GroupViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.group_app_name);
            this.mContentLayout = (LinearLayout) view.findViewById(R.id.group_child_layout);
            this.mNotiGroupCountLayout = (FrameLayout) view.findViewById(R.id.noti_group_count_layout);
            this.mNotiGroupCount = (TextView) view.findViewById(R.id.noti_group_count);
            this.mMainLayout = (LinearLayout) view.findViewById(R.id.subscreen_group_summary_main_layout);
            this.mDumyLayout = (FrameLayout) view.findViewById(R.id.subscreen_group_summary_dumy_layout);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.GroupViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GroupViewHolder groupViewHolder = GroupViewHolder.this;
                    SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                    groupViewHolder.animateClickNotification(subscreenNotificationListAdapter.mNotificationAnimatorManager, subscreenNotificationListAdapter.mSubRoomNotification, false);
                }
            });
            LinearLayout linearLayout = this.mMainLayout;
            if (linearLayout != null) {
                linearLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.GroupViewHolder.2
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        if (Math.abs(GroupViewHolder.this.mDumyLayout.getHeight() - GroupViewHolder.this.mMainLayout.getHeight()) > ((int) (SubscreenNotificationListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_default_stack_spacing) * 0.8d))) {
                            Log.d("SubscreenNotificationListAdapter", "GroupViewHolder - mMainLayout - onLayoutChange ");
                            GroupViewHolder groupViewHolder = GroupViewHolder.this;
                            SubscreenNotificationListAdapter.this.mGroupViewHolderArray.add(groupViewHolder);
                            SubscreenNotificationListAdapter.this.updateGroupViewHolderLayout();
                        }
                    }
                });
            }
        }
    }

    public class HideContentNotificationViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;

        public HideContentNotificationViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.hide_content_app_name);
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.HideContentNotificationViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (SubscreenNotificationListAdapter.this.mDeviceModel.isKeyguardUsed()) {
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

    public class NoNotificationViewHolder extends RecyclerView.ViewHolder {
        public NoNotificationViewHolder(SubscreenNotificationListAdapter subscreenNotificationListAdapter, View view) {
            super(view);
            subscreenNotificationListAdapter.mDeviceModel.setDimOnMainBackground(view);
        }
    }

    public class NotificationListItemViewHolder extends SubscreenParentItemViewHolder {
        public NotificationListItemViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            SubscreenNotificationListAdapter.this.mDeviceModel.setListAdpaterFirstChildTopMargin(this);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.NotificationListItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (SubscreenNotificationListAdapter.this.mDeviceModel.isKeyguardUsed()) {
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

    public class OngoingViewHolder extends SubscreenParentItemViewHolder {
        public final FrameLayout gradientBgView;
        public final TextView mAppName;
        public final FrameLayout mContentView;
        public final LinearLayout mHideContentLayout;
        public OngoingActivityData mOngoingActivityData;
        public boolean needHideContent;

        public OngoingViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
            this.mContentView = (FrameLayout) view.findViewById(R.id.ongoing_remote_views);
            this.mHideContentLayout = (LinearLayout) view.findViewById(R.id.ongoing_hide_content_layout);
            this.mAppName = (TextView) view.findViewById(R.id.hide_content_app_name);
            this.mMainLayout = (LinearLayout) view.findViewById(R.id.subscreen_main_layout);
            this.mDumyLayout = (FrameLayout) view.findViewById(R.id.subscreen_group_dumy_layout);
            this.gradientBgView = (FrameLayout) view.findViewById(R.id.subscreen_ongoing_layout);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.OngoingViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OngoingViewHolder ongoingViewHolder = OngoingViewHolder.this;
                    boolean z = ongoingViewHolder.mInfo.mRow.mIsSummaryWithChildren;
                    SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
                    if (!z) {
                        subscreenNotificationListAdapter.mDeviceModel.clickLiveNotification(subscreenNotificationListAdapter.mContext, ongoingViewHolder, ongoingViewHolder.mOngoingActivityData);
                        return;
                    }
                    if (subscreenNotificationListAdapter.mDeviceModel.isKeyguardUsed()) {
                        OngoingViewHolder ongoingViewHolder2 = OngoingViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter2 = SubscreenNotificationListAdapter.this;
                        subscreenNotificationListAdapter2.mDeviceModel.clickAdapterItem(subscreenNotificationListAdapter2.mContext, ongoingViewHolder2);
                    } else {
                        OngoingViewHolder ongoingViewHolder3 = OngoingViewHolder.this;
                        SubscreenNotificationListAdapter subscreenNotificationListAdapter3 = SubscreenNotificationListAdapter.this;
                        ongoingViewHolder3.animateClickNotification(subscreenNotificationListAdapter3.mNotificationAnimatorManager, subscreenNotificationListAdapter3.mSubRoomNotification, true);
                    }
                }
            });
            this.mMainLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener(SubscreenNotificationListAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.OngoingViewHolder.2
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    boolean z = Math.abs(OngoingViewHolder.this.mDumyLayout.getHeight() - OngoingViewHolder.this.mMainLayout.getHeight()) > ((int) (((double) SubscreenNotificationListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_default_stack_spacing)) * 0.8d));
                    OngoingViewHolder ongoingViewHolder = OngoingViewHolder.this;
                    if (ongoingViewHolder.mInfo.mRow.mIsSummaryWithChildren && !ongoingViewHolder.needHideContent && z) {
                        Log.d("SubscreenNotificationListAdapter", "OngoingViewHolder - mMainLayout - onLayoutChange ");
                        OngoingViewHolder ongoingViewHolder2 = OngoingViewHolder.this;
                        SubscreenNotificationListAdapter.this.mGroupViewHolderArray.add(ongoingViewHolder2);
                        SubscreenNotificationListAdapter.this.updateGroupViewHolderLayout();
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
        int listAdapterAddItemCnt = this.mDeviceModel.getListAdapterAddItemCnt();
        this.mNotificationInfoManager.getClass();
        return SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() + listAdapterAddItemCnt;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SubscreenNotificationInfo subscreenNotificationInfo;
        boolean zIsNotShwonNotificationState;
        boolean zIsOngoingActivity;
        this.mDeviceModel.setListAdpaterPosition(i);
        this.mNotificationInfoManager.getClass();
        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
        boolean zIsZenModeViewType = this.mDeviceModel.isZenModeViewType(i);
        int iConvertAdapterPositionToInfoIndex = this.mDeviceModel.convertAdapterPositionToInfoIndex(i);
        if (subscreenNotificationInfoListSize <= 0 || iConvertAdapterPositionToInfoIndex >= subscreenNotificationInfoListSize || zIsZenModeViewType) {
            subscreenNotificationInfo = null;
            zIsNotShwonNotificationState = false;
            zIsOngoingActivity = false;
        } else {
            this.mNotificationInfoManager.getClass();
            subscreenNotificationInfo = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(iConvertAdapterPositionToInfoIndex);
            zIsNotShwonNotificationState = this.mDeviceModel.isNotShwonNotificationState(subscreenNotificationInfo.mRow.mEntry);
            zIsOngoingActivity = subscreenNotificationInfo.mRow.mEntry.isOngoingActivity();
        }
        if (subscreenNotificationInfoListSize == 0) {
            return 3;
        }
        if (zIsZenModeViewType) {
            return 7;
        }
        if (iConvertAdapterPositionToInfoIndex == subscreenNotificationInfoListSize) {
            return 1;
        }
        if (subscreenNotificationInfo.mRow.needsRedaction() && zIsNotShwonNotificationState) {
            return (this.mDeviceModel.isOneUI7_0() && zIsOngoingActivity) ? 6 : 4;
        }
        if (zIsOngoingActivity) {
            return this.mDeviceModel.isOneUI7_0() ? 6 : 2;
        }
        if (subscreenNotificationInfo.mContentView != null) {
            Notification notification2 = subscreenNotificationInfo.mSbn.getNotification();
            Notification.Action[] actionArr = notification2.actions;
            if (actionArr == null) {
                return 2;
            }
            boolean z = false;
            for (Notification.Action action : actionArr) {
                if (action.getSemanticAction() == 10) {
                    z = true;
                }
            }
            boolean zEquals = "missed_call".equals(notification2.category);
            if (!z || !zEquals) {
                return 2;
            }
        }
        if (subscreenNotificationInfo.mGroupSummary) {
            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo.mRow;
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            if (notificationChildrenContainer == null || notificationChildrenContainer.getNotificationChildCount() != 1) {
                SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                subscreenDeviceModelParent.getClass();
                NotificationChildrenContainer notificationChildrenContainer2 = notificationEntry.row.mChildrenContainer;
                return (notificationEntry.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer2 == null || notificationChildrenContainer2.getNotificationChildCount() == 0)) ? 0 : 5;
            }
        } else if (subscreenNotificationInfo.mRow.isInsignificantSummary()) {
            return 5;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws Throwable {
        SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData;
        int i2;
        OngoingActivityData ongoingActivityData;
        this.mNotificationInfoManager.getClass();
        int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
        int iConvertAdapterPositionToInfoIndex = this.mDeviceModel.convertAdapterPositionToInfoIndex(i);
        boolean zIsZenModeViewType = this.mDeviceModel.isZenModeViewType(i);
        if (subscreenNotificationInfoListSize <= 0 || iConvertAdapterPositionToInfoIndex >= subscreenNotificationInfoListSize || zIsZenModeViewType) {
            subscreenNotificationInfoCreateItemsData = null;
        } else {
            this.mNotificationInfoManager.getClass();
            subscreenNotificationInfoCreateItemsData = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(iConvertAdapterPositionToInfoIndex);
        }
        if (viewHolder instanceof NotificationListItemViewHolder) {
            NotificationListItemViewHolder notificationListItemViewHolder = (NotificationListItemViewHolder) viewHolder;
            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfoCreateItemsData.mRow;
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter = SubscreenNotificationListAdapter.this;
            if (z) {
                subscreenNotificationListAdapter.getClass();
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                if (notificationChildrenContainer != null && notificationChildrenContainer.getNotificationChildCount() == 1) {
                    subscreenNotificationInfoCreateItemsData = subscreenNotificationListAdapter.mNotificationInfoManager.createItemsData((ExpandableNotificationRow) ((ArrayList) expandableNotificationRow.mChildrenContainer.mAttachedChildren).get(0));
                }
            }
            notificationListItemViewHolder.mInfo = subscreenNotificationInfoCreateItemsData;
            Context context = subscreenNotificationListAdapter.mContext;
            notificationListItemViewHolder.updateTitleAndContent(subscreenNotificationInfoCreateItemsData);
            subscreenNotificationListAdapter.mDeviceModel.setClock(notificationListItemViewHolder.mInfo, notificationListItemViewHolder.itemView);
            subscreenNotificationListAdapter.mDeviceModel.setUnreadMessageCount(subscreenNotificationInfoCreateItemsData, notificationListItemViewHolder.itemView);
            notificationListItemViewHolder.setIconView(notificationListItemViewHolder.mListAdapter, true);
            subscreenNotificationListAdapter.mDeviceModel.setRightIcon(subscreenNotificationListAdapter.mContext, notificationListItemViewHolder.mInfo, notificationListItemViewHolder.itemView);
            subscreenNotificationListAdapter.mDeviceModel.updateKnoxIcon(notificationListItemViewHolder.mSecureIcon, notificationListItemViewHolder.mInfo);
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationListAdapter.mDeviceModel;
            ImageView imageView = notificationListItemViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo = notificationListItemViewHolder.mInfo;
            subscreenDeviceModelParent.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView, subscreenNotificationInfo);
            subscreenNotificationListAdapter.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter.mContext, notificationListItemViewHolder.itemView);
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
            hideContentNotificationViewHolder.mInfo = subscreenNotificationInfoCreateItemsData;
            hideContentNotificationViewHolder.mAppName.setText(subscreenNotificationInfoCreateItemsData.mAppName);
            hideContentNotificationViewHolder.setIconView(hideContentNotificationViewHolder.mListAdapter, false);
            SubscreenNotificationListAdapter subscreenNotificationListAdapter3 = SubscreenNotificationListAdapter.this;
            subscreenNotificationListAdapter3.mDeviceModel.setClock(hideContentNotificationViewHolder.mInfo, hideContentNotificationViewHolder.itemView);
            subscreenNotificationListAdapter3.mDeviceModel.updateKnoxIcon(hideContentNotificationViewHolder.mSecureIcon, hideContentNotificationViewHolder.mInfo);
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationListAdapter3.mDeviceModel;
            ImageView imageView2 = hideContentNotificationViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo2 = hideContentNotificationViewHolder.mInfo;
            subscreenDeviceModelParent2.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo2);
            subscreenNotificationListAdapter3.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter3.mContext, hideContentNotificationViewHolder.itemView);
            hideContentNotificationViewHolder.mNotificationInfoManager.addRecyclerViewItemView(hideContentNotificationViewHolder);
            hideContentNotificationViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
            customViewHolder.mInfo = subscreenNotificationInfoCreateItemsData;
            customViewHolder.setIconView(customViewHolder.mListAdapter, false);
            SubscreenNotificationListAdapter subscreenNotificationListAdapter4 = SubscreenNotificationListAdapter.this;
            if (subscreenNotificationListAdapter4.mDeviceModel.isSupportRemoteView(customViewHolder.mInfo.mRow.mEntry)) {
                customViewHolder.mContentView.setVisibility(0);
                customViewHolder.mNormalView.setVisibility(8);
                if (customViewHolder.mContentView.getChildCount() > 0) {
                    customViewHolder.mInfo.setItemsData(subscreenNotificationInfoCreateItemsData.mRow);
                    if (!customViewHolder.mInfo.mRow.mIsSummaryWithChildren || subscreenNotificationListAdapter4.mIsCustomNotificationUpdated.booleanValue()) {
                        customViewHolder.mContentView.removeAllViews();
                    }
                }
                RemoteViews remoteViews = customViewHolder.mInfo.mContentView;
                if (remoteViews != null) {
                    customViewHolder.mContentView.addView(remoteViews.apply(subscreenNotificationListAdapter4.mContext, customViewHolder.mContentView, ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).remoteInputManager.mInteractionHandler));
                } else {
                    customViewHolder.setNormalViewLayout();
                    Log.d("SubscreenNotificationListAdapter", "CustomHolder onBind - dataViews is null");
                }
            } else {
                customViewHolder.setNormalViewLayout();
            }
            customViewHolder.mNotificationInfoManager.addRecyclerViewItemView(customViewHolder);
            customViewHolder.initTranslationX();
            if (subscreenNotificationListAdapter4.mIsCustomNotificationUpdated.booleanValue()) {
                subscreenNotificationListAdapter4.mIsCustomNotificationUpdated = Boolean.FALSE;
                return;
            }
            return;
        }
        if (viewHolder instanceof OngoingViewHolder) {
            OngoingViewHolder ongoingViewHolder = (OngoingViewHolder) viewHolder;
            ongoingViewHolder.mInfo = subscreenNotificationInfoCreateItemsData;
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            String str = subscreenNotificationInfoCreateItemsData.mKey;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
            ExpandableNotificationRow expandableNotificationRow2 = ongoingViewHolder.mInfo.mRow;
            if (expandableNotificationRow2.mIsSummaryWithChildren) {
                ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(((ExpandableNotificationRow) ((ArrayList) expandableNotificationRow2.mChildrenContainer.mAttachedChildren).get(0)).mEntry.mKey);
            }
            ongoingViewHolder.mOngoingActivityData = ongoingActivityDataByKey;
            ongoingViewHolder.mNotificationInfoManager.addRecyclerViewItemView(ongoingViewHolder);
            ongoingViewHolder.initTranslationX();
            int childCount = ongoingViewHolder.mContentView.getChildCount();
            SubscreenNotificationListAdapter subscreenNotificationListAdapter5 = SubscreenNotificationListAdapter.this;
            if (childCount > 0) {
                ongoingViewHolder.mInfo.setItemsData(subscreenNotificationInfoCreateItemsData.mRow);
                if (!ongoingViewHolder.mInfo.mRow.mIsSummaryWithChildren || subscreenNotificationListAdapter5.mIsCustomNotificationUpdated.booleanValue()) {
                    ongoingViewHolder.mContentView.removeAllViews();
                }
            }
            SubscreenNotificationInfo subscreenNotificationInfo3 = ongoingViewHolder.mInfo;
            subscreenNotificationInfo3.getClass();
            OngoingActivityData ongoingActivityDataByKey2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(subscreenNotificationInfo3.mKey);
            ExpandableNotificationRow expandableNotificationRow3 = subscreenNotificationInfo3.mRow;
            if (expandableNotificationRow3.mIsSummaryWithChildren) {
                ongoingActivityDataByKey2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(((ExpandableNotificationRow) ((ArrayList) expandableNotificationRow3.mChildrenContainer.mAttachedChildren).get(0)).mEntry.mKey);
            }
            if (ongoingActivityDataByKey2 == null) {
                subscreenNotificationInfo3.mOngoingView = null;
            } else {
                RemoteViews remoteViews2 = ongoingActivityDataByKey2.mOngoingSubScreenExpandView;
                if (remoteViews2 != null) {
                    View viewApply = remoteViews2.apply(subscreenNotificationInfo3.mContext, null, subscreenNotificationInfo3.controller.remoteInputManager.mInteractionHandler);
                    subscreenNotificationInfo3.mOngoingView = viewApply;
                    if (ongoingActivityDataByKey2.mCustomExpandedCardView != null) {
                        subscreenNotificationInfo3.mOngoingView = subscreenNotificationInfo3.controller.faceWidgetNotificationControllerWrapper.getViewFromNowBar(viewApply, BundleKt.bundleOf(new Pair("type", "SUB")));
                    }
                }
            }
            FrameLayout frameLayout = ongoingViewHolder.gradientBgView;
            if (frameLayout != null && (ongoingActivityData = ongoingViewHolder.mOngoingActivityData) != null) {
                frameLayout.post(new SubscreenParentItemViewHolder$$ExternalSyntheticLambda0(ongoingViewHolder, ongoingActivityData, subscreenNotificationListAdapter5.mContext, frameLayout));
            }
            ongoingViewHolder.needHideContent = subscreenNotificationInfoCreateItemsData.mRow.needsRedaction() && subscreenNotificationListAdapter5.mDeviceModel.isNotShwonNotificationState(subscreenNotificationInfoCreateItemsData.mRow.mEntry);
            StringBuilder sb = new StringBuilder("OngoingViewHolder onBind: ");
            sb.append(ongoingViewHolder.mInfo.mKey);
            sb.append(", needHideContent = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, ongoingViewHolder.needHideContent, "SubscreenNotificationListAdapter");
            if (ongoingViewHolder.needHideContent) {
                ongoingViewHolder.mHideContentLayout.setVisibility(0);
                ongoingViewHolder.mContentView.setVisibility(8);
                ongoingViewHolder.mAppName.setText(ongoingViewHolder.mInfo.mAppName);
                ongoingViewHolder.setIconView(ongoingViewHolder.mListAdapter, false);
                subscreenNotificationListAdapter5.mDeviceModel.setClock(ongoingViewHolder.mInfo, ongoingViewHolder.itemView);
                subscreenNotificationListAdapter5.mDeviceModel.updateKnoxIcon(ongoingViewHolder.mSecureIcon, ongoingViewHolder.mInfo);
                SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationListAdapter5.mDeviceModel;
                ImageView imageView3 = ongoingViewHolder.mTwoPhoneIcon;
                SubscreenNotificationInfo subscreenNotificationInfo4 = ongoingViewHolder.mInfo;
                subscreenDeviceModelParent3.getClass();
                SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView3, subscreenNotificationInfo4);
                subscreenNotificationListAdapter5.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter5.mContext, ongoingViewHolder.itemView);
                return;
            }
            ongoingViewHolder.mContentView.setVisibility(0);
            ongoingViewHolder.mHideContentLayout.setVisibility(8);
            if (ongoingViewHolder.mInfo.mRow.mIsSummaryWithChildren) {
                subscreenNotificationListAdapter5.mGroupViewHolderArray.add(ongoingViewHolder);
                ((SubscreenParentItemViewHolder) ongoingViewHolder).mPosition = ongoingViewHolder.getLayoutPosition();
                if (ongoingViewHolder.mContentView.getChildCount() > 0) {
                    return;
                }
            } else {
                ongoingViewHolder.mDumyLayout.setVisibility(8);
            }
            if (ongoingViewHolder.mOngoingActivityData != null) {
                FrameLayout frameLayout2 = ongoingViewHolder.mContentView;
                SubscreenNotificationInfo subscreenNotificationInfo5 = ongoingViewHolder.mInfo;
                if (subscreenNotificationInfo5.mOngoingView.getParent() != null) {
                    ((ViewGroup) subscreenNotificationInfo5.mOngoingView.getParent()).removeAllViews();
                }
                frameLayout2.addView(subscreenNotificationInfo5.mOngoingView);
                LinearLayout linearLayout = (LinearLayout) ongoingViewHolder.mContentView.findViewById(R.id.ongoing_activity_expanded_header_container);
                if (linearLayout != null) {
                    linearLayout.measure(0, View.MeasureSpec.makeMeasureSpec(41, Integer.MIN_VALUE));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = linearLayout.getMeasuredHeight();
                    linearLayout.setLayoutParams(layoutParams);
                }
                Context context2 = subscreenNotificationListAdapter5.mContext;
                FrameLayout frameLayout3 = ongoingViewHolder.mContentView;
                OngoingActivityData ongoingActivityData2 = ongoingViewHolder.mOngoingActivityData;
                if (ongoingViewHolder.itemView.getMeasuredWidth() != 0) {
                    OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                    OngoingType ongoingType = OngoingType.SUB;
                    ongoingActivityLayoutUtil.getClass();
                    OngoingActivityLayoutUtil.updateNowbarSports(context2, frameLayout3, ongoingActivityData2, ongoingType);
                }
                OngoingActivityLayoutUtil.INSTANCE.getClass();
                OngoingActivityLayoutUtil.updateOngoingChronometer(frameLayout3, ongoingActivityData2, true);
                OngoingActivityLayoutUtil.updateOngoingDescription(frameLayout3);
                return;
            }
            return;
        }
        if (viewHolder instanceof GroupViewHolder) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
            groupViewHolder.mInfo = subscreenNotificationInfoCreateItemsData;
            SubscreenNotificationListAdapter subscreenNotificationListAdapter6 = SubscreenNotificationListAdapter.this;
            SubscreenDeviceModelParent subscreenDeviceModelParent4 = subscreenNotificationListAdapter6.mDeviceModel;
            NotificationEntry notificationEntry = subscreenNotificationInfoCreateItemsData.mRow.mEntry;
            subscreenDeviceModelParent4.getClass();
            subscreenNotificationListAdapter6.mDeviceModel.setClock(groupViewHolder.mInfo, groupViewHolder.itemView);
            if (groupViewHolder.mNotiGroupCount != null) {
                FrameLayout frameLayout4 = groupViewHolder.mNotiGroupCountLayout;
                if (frameLayout4 != null) {
                    frameLayout4.setVisibility(0);
                }
                groupViewHolder.mNotiGroupCount.setVisibility(0);
                if (groupViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    i2 = subscreenNotificationListAdapter6.mDeviceModel.mMoreNotificationCount;
                } else {
                    SubscreenNotificationInfo subscreenNotificationInfo6 = groupViewHolder.mInfo;
                    ExpandableNotificationRow expandableNotificationRow4 = subscreenNotificationInfo6.mRow;
                    if (expandableNotificationRow4.mIsSummaryWithChildren) {
                        subscreenNotificationInfo6.mChildCount = expandableNotificationRow4.mChildrenContainer.mUntruncatedChildCount;
                    }
                    i2 = subscreenNotificationInfo6.mChildCount;
                }
                groupViewHolder.mNotiGroupCount.setText(Integer.toString(i2));
            }
            if (groupViewHolder.mAppName != null) {
                if (groupViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    int i3 = subscreenNotificationListAdapter6.mDeviceModel.mMoreNotificationCount;
                    groupViewHolder.mAppName.setText(subscreenNotificationListAdapter6.mContext.getResources().getQuantityString(R.plurals.notification_insignificant_title, i3, Integer.valueOf(i3)));
                    FrameLayout frameLayout5 = groupViewHolder.mNotiGroupCountLayout;
                    if (frameLayout5 != null) {
                        frameLayout5.setVisibility(8);
                    }
                    TextView textView = groupViewHolder.mNotiGroupCount;
                    if (textView != null) {
                        textView.setVisibility(8);
                    }
                } else {
                    groupViewHolder.mAppName.setText(groupViewHolder.mInfo.mAppName);
                }
            }
            boolean zIsOneUI7_0 = subscreenNotificationListAdapter6.mDeviceModel.isOneUI7_0();
            SubscreenNotificationListAdapter subscreenNotificationListAdapter7 = groupViewHolder.mListAdapter;
            if (zIsOneUI7_0) {
                subscreenNotificationListAdapter6.mGroupViewHolderArray.add(groupViewHolder);
                ((SubscreenParentItemViewHolder) groupViewHolder).mPosition = groupViewHolder.getLayoutPosition();
                SubscreenNotificationInfo subscreenNotificationInfo7 = groupViewHolder.mInfo;
                ExpandableNotificationRow expandableNotificationRow5 = subscreenNotificationInfo7.mRow;
                if (expandableNotificationRow5.mIsSummaryWithChildren) {
                    subscreenNotificationInfo7.mChildCount = expandableNotificationRow5.mChildrenContainer.mUntruncatedChildCount;
                }
                int i4 = subscreenNotificationInfo7.mChildCount;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i4, "setSummaryContentsForLargeScreen() - child Count : ", "SubscreenNotificationListAdapter");
                if (groupViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    groupViewHolder.setIconView(subscreenNotificationListAdapter7, false);
                    int i5 = subscreenNotificationListAdapter6.mDeviceModel.mMoreNotificationCount;
                    groupViewHolder.mTitle.setText(subscreenNotificationListAdapter6.mContext.getResources().getQuantityString(R.plurals.notification_insignificant_title, i5, Integer.valueOf(i5)));
                    groupViewHolder.mContent.setVisibility(8);
                    groupViewHolder.mNotiGroupCount.setVisibility(8);
                    groupViewHolder.mTwoPhoneIcon.setVisibility(8);
                    groupViewHolder.mSecureIcon.setVisibility(8);
                    groupViewHolder.itemView.findViewById(R.id.subscreen_right_icon).setVisibility(8);
                } else {
                    ExpandableNotificationRow expandableNotificationRow6 = groupViewHolder.mInfo.mRow;
                    if (expandableNotificationRow6.mIsSummaryWithChildren && i4 > 1) {
                        ExpandableNotificationRow expandableNotificationRow7 = (ExpandableNotificationRow) ((ArrayList) expandableNotificationRow6.mChildrenContainer.mAttachedChildren).get(0);
                        SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData2 = groupViewHolder.mNotificationInfoManager.createItemsData(expandableNotificationRow7);
                        boolean z2 = expandableNotificationRow7.needsRedaction() && subscreenNotificationListAdapter6.mDeviceModel.isNotShwonNotificationState(expandableNotificationRow7.mEntry);
                        groupViewHolder.setIconView(subscreenNotificationListAdapter7, subscreenNotificationInfoCreateItemsData2, !z2);
                        subscreenNotificationListAdapter6.mDeviceModel.updateKnoxIcon(groupViewHolder.mSecureIcon, subscreenNotificationInfoCreateItemsData2);
                        SubscreenDeviceModelParent subscreenDeviceModelParent5 = subscreenNotificationListAdapter6.mDeviceModel;
                        ImageView imageView4 = groupViewHolder.mTwoPhoneIcon;
                        subscreenDeviceModelParent5.getClass();
                        SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView4, subscreenNotificationInfoCreateItemsData2);
                        if (z2) {
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("setSummaryContentsForLargeScreen() - child needsRedaction : "), groupViewHolder.mInfo.mKey, "SubscreenNotificationListAdapter");
                            groupViewHolder.mTitle.setText(groupViewHolder.mInfo.mAppName);
                            groupViewHolder.mContent.setVisibility(8);
                        } else {
                            groupViewHolder.updateTitleAndContent(subscreenNotificationInfoCreateItemsData2);
                            subscreenNotificationListAdapter6.mDeviceModel.setUnreadMessageCount(subscreenNotificationInfoCreateItemsData2, groupViewHolder.itemView);
                            subscreenNotificationListAdapter6.mDeviceModel.setRightIcon(subscreenNotificationListAdapter6.mContext, subscreenNotificationInfoCreateItemsData2, groupViewHolder.itemView);
                            subscreenNotificationListAdapter6.mDeviceModel.setListItemTextLayout(subscreenNotificationListAdapter6.mContext, groupViewHolder.itemView);
                        }
                    }
                }
                if (groupViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    subscreenNotificationListAdapter6.mDeviceModel.updateMoreShadowIconColor(groupViewHolder.itemView, groupViewHolder.mInfo.mRow.mEntry);
                    FrameLayout frameLayout6 = groupViewHolder.mDumyLayout;
                    if (frameLayout6 != null) {
                        frameLayout6.setVisibility(8);
                    }
                }
            } else {
                groupViewHolder.setIconView(subscreenNotificationListAdapter7, false);
                subscreenNotificationListAdapter6.mDeviceModel.updateKnoxIcon(groupViewHolder.mSecureIcon, groupViewHolder.mInfo);
                SubscreenDeviceModelParent subscreenDeviceModelParent6 = subscreenNotificationListAdapter6.mDeviceModel;
                ImageView imageView5 = groupViewHolder.mTwoPhoneIcon;
                SubscreenNotificationInfo subscreenNotificationInfo8 = groupViewHolder.mInfo;
                subscreenDeviceModelParent6.getClass();
                SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView5, subscreenNotificationInfo8);
                if (!groupViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    if (groupViewHolder.mContentLayout.getChildCount() > 0) {
                        groupViewHolder.mContentLayout.removeAllViews();
                    }
                    SubscreenNotificationInfo subscreenNotificationInfo9 = groupViewHolder.mInfo;
                    ExpandableNotificationRow expandableNotificationRow8 = subscreenNotificationInfo9.mRow;
                    if (expandableNotificationRow8.mIsSummaryWithChildren) {
                        subscreenNotificationInfo9.mChildCount = expandableNotificationRow8.mChildrenContainer.mUntruncatedChildCount;
                    }
                    int i6 = subscreenNotificationInfo9.mChildCount;
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i6, "addGroupItems - child Count : ", "SubscreenNotificationListAdapter");
                    ExpandableNotificationRow expandableNotificationRow9 = groupViewHolder.mInfo.mRow;
                    if (expandableNotificationRow9.mIsSummaryWithChildren && i6 > 1) {
                        NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow9.mChildrenContainer;
                        for (int i7 = 0; i7 < 2; i7++) {
                            ExpandableNotificationRow expandableNotificationRow10 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer2.mAttachedChildren).get(i7);
                            SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData3 = subscreenNotificationListAdapter6.mNotificationInfoManager.createItemsData(expandableNotificationRow10);
                            View viewInflate = LayoutInflater.from(subscreenNotificationListAdapter6.mContext).inflate(subscreenNotificationListAdapter6.mDeviceModel.getListAdapterGroupItemResource(), (ViewGroup) groupViewHolder.mContentLayout, false);
                            TextView textView2 = (TextView) viewInflate.findViewById(R.id.group_item_title);
                            TextView textView3 = (TextView) viewInflate.findViewById(R.id.group_item_content);
                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                            if (expandableNotificationRow10.needsRedaction() && subscreenNotificationListAdapter6.mDeviceModel.isNotShwonNotificationState(expandableNotificationRow10.mEntry)) {
                                textView2.setLayoutParams(layoutParams2);
                                textView2.setText(subscreenNotificationInfoCreateItemsData3.getContentHiddenText());
                                textView3.setVisibility(8);
                            } else {
                                if (subscreenNotificationInfoCreateItemsData3.getTitle() == null) {
                                    textView2.setVisibility(8);
                                } else {
                                    textView2.setVisibility(0);
                                    textView2.setText(subscreenNotificationInfoCreateItemsData3.getTitle());
                                }
                                String str2 = subscreenNotificationInfoCreateItemsData3.mContent;
                                if (str2 == null) {
                                    textView2.setLayoutParams(layoutParams2);
                                    textView3.setVisibility(8);
                                } else {
                                    textView3.setText(str2);
                                    textView3.setVisibility(0);
                                }
                            }
                            groupViewHolder.mContentLayout.addView(viewInflate);
                        }
                    }
                }
            }
            if (groupViewHolder.mAppName != null) {
                Resources resources = subscreenNotificationListAdapter6.mContext.getResources();
                int i8 = groupViewHolder.mInfo.mChildCount;
                groupViewHolder.itemView.setContentDescription(groupViewHolder.mAppName.getText().toString() + resources.getQuantityString(R.plurals.plural_notification_count, i8, Integer.valueOf(i8)).toString());
            }
            groupViewHolder.mNotificationInfoManager.addRecyclerViewItemView(groupViewHolder);
            groupViewHolder.initTranslationX();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
        if (i == 6) {
            return new OngoingViewHolder(listAdapterLayout);
        }
        if (i == 4) {
            return new HideContentNotificationViewHolder(listAdapterLayout);
        }
        if (i == 5) {
            return new GroupViewHolder(listAdapterLayout);
        }
        if (i == 7) {
            return new DnDTextViewHolder(this, listAdapterLayout);
        }
        return null;
    }

    public final void updateGroupViewHolderLayout() {
        int size = this.mGroupViewHolderArray.size();
        if (size > 0) {
            double dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_default_stack_spacing) * 0.8d;
            for (int i = 0; i < size; i++) {
                final SubscreenParentItemViewHolder subscreenParentItemViewHolder = (SubscreenParentItemViewHolder) this.mGroupViewHolderArray.get(i);
                if (subscreenParentItemViewHolder.mInfo.mRow.isInsignificantSummary()) {
                    subscreenParentItemViewHolder.mDumyLayout.setVisibility(8);
                } else {
                    boolean z = Math.abs(subscreenParentItemViewHolder.mDumyLayout.getHeight() - subscreenParentItemViewHolder.mMainLayout.getHeight()) == ((int) dimensionPixelSize);
                    if (subscreenParentItemViewHolder.mDumyLayout.getVisibility() == 0 && subscreenParentItemViewHolder.mDumyLayout.getHeight() != 0 && z) {
                        Log.e("SubscreenNotificationListAdapter", "updateGroupViewHolderLayout : isNotUpdatedDumyViewLayout");
                    } else {
                        subscreenParentItemViewHolder.mDumyLayout.setVisibility(0);
                        subscreenParentItemViewHolder.mDumyLayout.setLayoutParams(new FrameLayout.LayoutParams(subscreenParentItemViewHolder.mMainLayout.getWidth(), (int) (subscreenParentItemViewHolder.mMainLayout.getHeight() + dimensionPixelSize)));
                        subscreenParentItemViewHolder.mDumyLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.1
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                                subscreenParentItemViewHolder.mDumyLayout.setClipBounds(new Rect(0, subscreenParentItemViewHolder.mMainLayout.getHeight(), subscreenParentItemViewHolder.mDumyLayout.getWidth(), subscreenParentItemViewHolder.mDumyLayout.getHeight()));
                            }
                        });
                        final int i2 = subscreenParentItemViewHolder.mPosition;
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SubscreenNotificationListAdapter.this.notifyItemChanged(i2);
                            }
                        });
                    }
                }
            }
            this.mGroupViewHolderArray.clear();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) throws Throwable {
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
