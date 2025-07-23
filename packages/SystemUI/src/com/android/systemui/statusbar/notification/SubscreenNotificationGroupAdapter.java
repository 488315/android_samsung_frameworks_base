package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingType;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenNotificationGroupAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationGroupAdapter sInstance;
    public int mPositionControlCnt;
    public SubscreenNotificationInfo mSummaryInfo;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomViewHolder extends SubscreenParentItemViewHolder {
        public final FrameLayout mContentView;

        public CustomViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mContentView = (FrameLayout) view.findViewById(R.id.custom_remote_views);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.CustomViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (!CustomViewHolder.this.mInfo.mRow.mEntry.isOngoingActivity() || CustomViewHolder.this.mInfo.mRow.mIsSummaryWithChildren) {
                        if (SubscreenNotificationGroupAdapter.this.mDeviceModel.isKeyguardUsed()) {
                            CustomViewHolder customViewHolder = CustomViewHolder.this;
                            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                            subscreenNotificationGroupAdapter.mDeviceModel.clickAdapterItem(subscreenNotificationGroupAdapter.mContext, customViewHolder);
                        } else {
                            CustomViewHolder customViewHolder2 = CustomViewHolder.this;
                            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2 = SubscreenNotificationGroupAdapter.this;
                            customViewHolder2.animateClickNotification(subscreenNotificationGroupAdapter2.mNotificationAnimatorManager, subscreenNotificationGroupAdapter2.mSubRoomNotification, true);
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout mClearAllLayout;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter$FooterViewHolder$2, reason: invalid class name */
        public class AnonymousClass2 implements View.OnClickListener {
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
                        int i = 0;
                        if (subscreenNotificationInfo.mRow.isInsignificantSummary()) {
                            int i2 = subscreenNotificationInfoManager.mSubscreenNotificationController.mDeviceModel.mMoreNotificationCount;
                            while (i < i2) {
                                subscreenNotificationInfoManager.removeNotification(((ExpandableNotificationRow) ((ArrayList) subscreenNotificationInfo.mRow.mChildrenContainer.mAttachedChildren).get(i)).mEntry);
                                i++;
                            }
                            subscreenNotificationInfoManager.removeNotification(subscreenNotificationInfo.mRow.mEntry);
                        } else {
                            while (i < size) {
                                NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(i);
                                if (!notificationEntry.mRanking.getChannel().isImportantConversation() && subscreenNotificationInfo.mSbn.getGroupKey().equals(notificationEntry.mSbn.getGroupKey()) && SubscreenNotificationInfoManager.canViewBeCleared(notificationEntry.row)) {
                                    subscreenNotificationInfoManager.removeNotification(notificationEntry);
                                }
                                i++;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HideContenNotificationViewHolder extends SubscreenParentItemViewHolder {
        public final TextView mAppName;

        public HideContenNotificationViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mAppName = (TextView) view.findViewById(R.id.hide_content_app_name);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.HideContenNotificationViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (SubscreenNotificationGroupAdapter.this.mDeviceModel.isKeyguardUsed()) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NotificationGroupItemViewHolder extends SubscreenParentItemViewHolder {
        public NotificationGroupItemViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.NotificationGroupItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    if (SubscreenNotificationGroupAdapter.this.mDeviceModel.isKeyguardUsed()) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OngoingViewHolder extends SubscreenParentItemViewHolder {
        public final FrameLayout mContentView;
        public OngoingActivityData mOngoingActivityData;

        public OngoingViewHolder(View view) {
            super(view);
            this.mNotificationInfoManager = SubscreenNotificationGroupAdapter.this.mNotificationInfoManager;
            this.mContentView = (FrameLayout) view.findViewById(R.id.ongoing_remote_views);
            view.setOnClickListener(new View.OnClickListener(SubscreenNotificationGroupAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter.OngoingViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OngoingViewHolder ongoingViewHolder = OngoingViewHolder.this;
                    SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
                    subscreenNotificationGroupAdapter.mDeviceModel.clickLiveNotification(subscreenNotificationGroupAdapter.mContext, ongoingViewHolder, ongoingViewHolder.mOngoingActivityData);
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mNotificationInfoManager.mGroupDataArray.size() + (this.mDeviceModel.isMainHeader() ? 1 : 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        int i2 = !this.mDeviceModel.isMainHeader() ? 1 : 0;
        this.mPositionControlCnt = i2;
        int i3 = i - i2;
        int size = this.mNotificationInfoManager.mGroupDataArray.size();
        SubscreenNotificationInfo subscreenNotificationInfo = (i3 < 0 || i3 >= size) ? null : (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i3);
        if (i == size + this.mPositionControlCnt) {
            return 1;
        }
        if (i == 0 && !this.mDeviceModel.isMainHeader()) {
            return 2;
        }
        if (subscreenNotificationInfo.mRow.needsRedaction() && this.mDeviceModel.isNotShwonNotificationState(subscreenNotificationInfo.mRow.mEntry)) {
            return 4;
        }
        if (this.mDeviceModel.isSupportRemoteView(subscreenNotificationInfo.mRow.mEntry)) {
            return 5;
        }
        if (subscreenNotificationInfo.mRow.mEntry.isOngoingActivity()) {
            return this.mDeviceModel.isOneUI7_0() ? 6 : 5;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof NotificationGroupItemViewHolder) {
            NotificationGroupItemViewHolder notificationGroupItemViewHolder = (NotificationGroupItemViewHolder) viewHolder;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Group position Item: ", "SubscreenNotificationGroupAdapter");
            SubscreenNotificationInfo subscreenNotificationInfo = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            notificationGroupItemViewHolder.mInfo = subscreenNotificationInfo;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = SubscreenNotificationGroupAdapter.this;
            Context context = subscreenNotificationGroupAdapter.mContext;
            notificationGroupItemViewHolder.updateTitleAndContent(subscreenNotificationInfo);
            subscreenNotificationGroupAdapter.mDeviceModel.setClock(notificationGroupItemViewHolder.mInfo, notificationGroupItemViewHolder.itemView);
            subscreenNotificationGroupAdapter.mDeviceModel.setUnreadMessageCount(subscreenNotificationInfo, notificationGroupItemViewHolder.itemView);
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
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationGroupAdapter2.mDeviceModel;
            ImageView imageView2 = headerViewHolder.mTwoPhoneIcon;
            SubscreenNotificationInfo subscreenNotificationInfo3 = subscreenNotificationGroupAdapter2.mSummaryInfo;
            subscreenDeviceModelParent.getClass();
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo3);
            subscreenNotificationGroupAdapter2.mDeviceModel.updateKnoxIcon(headerViewHolder.mSecureIcon, subscreenNotificationGroupAdapter2.mSummaryInfo);
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
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter4 = SubscreenNotificationGroupAdapter.this;
            subscreenNotificationGroupAdapter4.mDeviceModel.setClock(hideContenNotificationViewHolder.mInfo, hideContenNotificationViewHolder.itemView);
            subscreenNotificationGroupAdapter4.mDeviceModel.setGroupAdapterIcon(subscreenNotificationGroupAdapter4.mContext, subscreenNotificationGroupAdapter4, hideContenNotificationViewHolder);
            subscreenNotificationGroupAdapter4.mDeviceModel.setListItemTextLayout(subscreenNotificationGroupAdapter4.mContext, hideContenNotificationViewHolder.itemView);
            hideContenNotificationViewHolder.mNotificationInfoManager.addRecyclerViewItemView(hideContenNotificationViewHolder);
            hideContenNotificationViewHolder.initTranslationX();
            return;
        }
        if (viewHolder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
            SubscreenNotificationInfo subscreenNotificationInfo5 = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            customViewHolder.mInfo = subscreenNotificationInfo5;
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter5 = SubscreenNotificationGroupAdapter.this;
            if (subscreenNotificationGroupAdapter5.mDeviceModel.isSupportRemoteView(subscreenNotificationInfo5.mRow.mEntry)) {
                if (customViewHolder.mContentView.getChildCount() > 0) {
                    customViewHolder.mInfo.setItemsData(subscreenNotificationInfo5.mRow);
                }
                customViewHolder.mContentView.removeAllViews();
                RemoteViews remoteViews = customViewHolder.mInfo.mContentView;
                if (remoteViews != null) {
                    customViewHolder.mContentView.addView(remoteViews.apply(subscreenNotificationGroupAdapter5.mContext, customViewHolder.mContentView, ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).remoteInputManager.mInteractionHandler));
                }
            }
            customViewHolder.mNotificationInfoManager.addRecyclerViewItemView(customViewHolder);
            return;
        }
        if (viewHolder instanceof OngoingViewHolder) {
            OngoingViewHolder ongoingViewHolder = (OngoingViewHolder) viewHolder;
            SubscreenNotificationInfo subscreenNotificationInfo6 = (SubscreenNotificationInfo) this.mNotificationInfoManager.mGroupDataArray.get(i - this.mPositionControlCnt);
            ongoingViewHolder.mInfo = subscreenNotificationInfo6;
            if (ongoingViewHolder.mContentView.getChildCount() > 0) {
                ongoingViewHolder.mInfo.setItemsData(subscreenNotificationInfo6.mRow);
            }
            ongoingViewHolder.mContentView.removeAllViews();
            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter6 = SubscreenNotificationGroupAdapter.this;
            if (subscreenNotificationGroupAdapter6.mDeviceModel.isOneUI7_0() && ongoingViewHolder.mInfo.mRow.mIsSummaryWithChildren) {
                ((SubscreenParentItemViewHolder) ongoingViewHolder).mPosition = ongoingViewHolder.getLayoutPosition();
            }
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            String str = ongoingViewHolder.mInfo.mKey;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
            ongoingViewHolder.mOngoingActivityData = ongoingActivityDataByKey;
            RemoteViews remoteViews2 = ongoingActivityDataByKey != null ? ongoingActivityDataByKey.mOngoingSubScreenExpandView : null;
            if (remoteViews2 != null) {
                View apply = remoteViews2.apply(subscreenNotificationGroupAdapter6.mContext, ongoingViewHolder.mContentView, ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).remoteInputManager.mInteractionHandler);
                if (ongoingViewHolder.mOngoingActivityData.mCustomExpandedCardView != null) {
                    apply = subscreenNotificationGroupAdapter6.mController.faceWidgetNotificationControllerWrapper.getViewFromNowBar(apply, BundleKt.bundleOf(new Pair("type", "SUB")));
                }
                ongoingViewHolder.mContentView.addView(apply);
                LinearLayout linearLayout = (LinearLayout) ongoingViewHolder.mContentView.findViewById(R.id.ongoing_activity_expanded_header_container);
                if (linearLayout != null) {
                    linearLayout.measure(0, View.MeasureSpec.makeMeasureSpec(41, Integer.MIN_VALUE));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = linearLayout.getMeasuredHeight();
                    linearLayout.setLayoutParams(layoutParams);
                }
                Context context2 = subscreenNotificationGroupAdapter6.mContext;
                OngoingActivityData ongoingActivityData = ongoingViewHolder.mOngoingActivityData;
                FrameLayout frameLayout = ongoingViewHolder.mContentView;
                frameLayout.post(new SubscreenParentItemViewHolder$$ExternalSyntheticLambda0(ongoingViewHolder, ongoingActivityData, context2, frameLayout));
                if (ongoingViewHolder.itemView.getMeasuredWidth() != 0) {
                    Context context3 = subscreenNotificationGroupAdapter6.mContext;
                    FrameLayout frameLayout2 = ongoingViewHolder.mContentView;
                    OngoingActivityData ongoingActivityData2 = ongoingViewHolder.mOngoingActivityData;
                    OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                    OngoingType ongoingType = OngoingType.SUB;
                    ongoingActivityLayoutUtil.getClass();
                    OngoingActivityLayoutUtil.updateNowbarSports(context3, frameLayout2, ongoingActivityData2, ongoingType);
                }
                FrameLayout frameLayout3 = ongoingViewHolder.mContentView;
                OngoingActivityData ongoingActivityData3 = ongoingViewHolder.mOngoingActivityData;
                OngoingActivityLayoutUtil.INSTANCE.getClass();
                OngoingActivityLayoutUtil.updateOngoingChronometer(frameLayout3, ongoingActivityData3, true);
            }
            ongoingViewHolder.mNotificationInfoManager.addRecyclerViewItemView(ongoingViewHolder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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
        if (i == 6) {
            return new OngoingViewHolder(groupAdapterLayout);
        }
        return null;
    }
}
