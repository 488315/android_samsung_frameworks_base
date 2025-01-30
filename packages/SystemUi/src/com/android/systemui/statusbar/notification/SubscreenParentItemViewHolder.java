package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenParentItemViewHolder extends RecyclerView.ViewHolder {
    public Animator mClickAnimator;
    public final TextView mContent;
    public final Handler mHandler;
    public SubscreenNotificationInfo mInfo;
    public SubscreenNotificationListAdapter mListAdapter;
    public TextView mNotiGroupCount;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public final ImageView mSecureIcon;
    public final TextView mTitle;
    public final ImageView mTwoPhoneIcon;
    public final TextView mUnreadMessageCount;
    public final FrameLayout mUnreadMessageCountLayout;

    public SubscreenParentItemViewHolder(View view) {
        super(view);
        this.mHandler = new Handler();
        new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder.1
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenParentItemViewHolder.this.mListAdapter.getClass();
                SubscreenParentItemViewHolder subscreenParentItemViewHolder = SubscreenParentItemViewHolder.this;
                for (int i = 0; i < subscreenParentItemViewHolder.mNotificationInfoManager.mRecyclerViewItemHolderArray.size(); i++) {
                    ((SubscreenParentItemViewHolder) subscreenParentItemViewHolder.mNotificationInfoManager.mRecyclerViewItemHolderArray.get(i)).itemView.setTranslationX(0.0f);
                }
                subscreenParentItemViewHolder.mNotificationInfoManager.clearAllRecyclerViewItem();
                SubscreenParentItemViewHolder.this.mListAdapter.notifyDataSetChanged();
            }
        };
        this.mTwoPhoneIcon = (ImageView) view.findViewById(R.id.two_phone_icon);
        this.mSecureIcon = (ImageView) view.findViewById(R.id.secure_icon);
        this.mTitle = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
        this.mContent = (TextView) view.findViewById(R.id.subscreen_notification_content_text);
        this.mUnreadMessageCountLayout = (FrameLayout) view.findViewById(R.id.unread_message_count_layout);
        this.mUnreadMessageCount = (TextView) view.findViewById(R.id.unread_message_count);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0050, code lost:
    
        if (com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.isOnlyGroupSummary(r3) != false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void animateClickNotification(SubscreenSubRoomNotificaitonAnimatorManager subscreenSubRoomNotificaitonAnimatorManager, final SubscreenSubRoomNotification subscreenSubRoomNotification, final boolean z) {
        SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
        boolean z2 = subscreenNotificationInfo.mIsMessagingStyle;
        SystemUIAnalytics.sendEventCDLog(this.mNotificationInfoManager.mIsShownGroup ? "QPN101" : "QPN100", "QPNE0200", "type", z2 ? "conversation" : "normal", "type2", (z2 && subscreenNotificationInfo.mRemoteinput) ? "replyable" : "non-conversation", "app", subscreenNotificationInfo.mPkg);
        if (this.mInfo.mGroupSummary) {
            subscreenSubRoomNotification.getClass();
            SubscreenDeviceModelParent deviceModel = SubscreenSubRoomNotification.getDeviceModel();
            NotificationEntry notificationEntry = this.mInfo.mRow.mEntry;
            deviceModel.getClass();
        }
        subscreenSubRoomNotification.getClass();
        if (SubscreenSubRoomNotification.getDeviceModel().isLaunchApp(this.mInfo.mRow.mEntry)) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder = SubscreenParentItemViewHolder.this;
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenSubRoomNotification;
                    subscreenParentItemViewHolder.getClass();
                    subscreenSubRoomNotification2.getClass();
                    SubscreenSubRoomNotification.getDeviceModel().launchApp(subscreenParentItemViewHolder.mInfo.mRow.mEntry);
                }
            }, 300L);
            return;
        }
        if (this.mClickAnimator == null) {
            subscreenSubRoomNotification.mRecyclerViewItemSelectKey = this.mInfo.mKey;
            this.mClickAnimator = subscreenSubRoomNotificaitonAnimatorManager.alphaAnimatedMainView(subscreenSubRoomNotification.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder = SubscreenParentItemViewHolder.this;
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenSubRoomNotification;
                    boolean z3 = z;
                    subscreenParentItemViewHolder.getClass();
                    subscreenSubRoomNotification2.getClass();
                    boolean isKnoxSecurity = SubscreenSubRoomNotification.getDeviceModel().isKnoxSecurity(subscreenParentItemViewHolder.mInfo.mRow.mEntry);
                    SubscreenDeviceModelParent deviceModel2 = SubscreenSubRoomNotification.getDeviceModel();
                    NotificationEntry notificationEntry2 = subscreenParentItemViewHolder.mInfo.mRow.mEntry;
                    deviceModel2.getClass();
                    boolean isOnlyGroupSummary = SubscreenDeviceModelParent.isOnlyGroupSummary(notificationEntry2);
                    StringBuilder sb = new StringBuilder("setHeaderVIewLayout onClick mInfo.isGroupSummary() : ");
                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, subscreenParentItemViewHolder.mInfo.mGroupSummary, ", hideContent : ", z3, ", isKnoxSecurity : ");
                    sb.append(isKnoxSecurity);
                    sb.append(", isOnlyGroupSummary : ");
                    sb.append(isOnlyGroupSummary);
                    Log.e("SubscreenParentItemViewHolder", sb.toString());
                    if (!subscreenParentItemViewHolder.mInfo.mGroupSummary || z3 || isOnlyGroupSummary) {
                        SubscreenNotificationInfoManager subscreenNotificationInfoManager = subscreenParentItemViewHolder.mNotificationInfoManager;
                        subscreenNotificationInfoManager.mNotificationDetailAdapter.mItemPostionInGroup = subscreenNotificationInfoManager.mIsShownGroup ? subscreenParentItemViewHolder.getBindingAdapterPosition() : 0;
                        subscreenSubRoomNotification2.showDetailNotification(subscreenParentItemViewHolder.mInfo);
                    } else {
                        subscreenParentItemViewHolder.mNotificationInfoManager.clearAllRecyclerViewItem();
                        SubscreenNotificationInfo subscreenNotificationInfo2 = subscreenParentItemViewHolder.mInfo;
                        if (subscreenSubRoomNotification2.mNotificationRecyclerView != null && subscreenSubRoomNotification2.mNotificationGroupAdapter != null) {
                            Log.e("SubscreenSubRoomNotification", "showGroupNotification key" + subscreenNotificationInfo2.mKey);
                            subscreenSubRoomNotification2.mNotificationRecyclerView.setAdapter(subscreenSubRoomNotification2.mNotificationGroupAdapter);
                            SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter = subscreenSubRoomNotification2.mNotificationGroupAdapter;
                            if (subscreenNotificationGroupAdapter.mNotificationInfoManager.getGroupDataArraySize() > 0) {
                                subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.clear();
                            }
                            subscreenNotificationGroupAdapter.mSummaryInfo = subscreenNotificationInfo2;
                            SubscreenNotificationInfoManager subscreenNotificationInfoManager2 = subscreenNotificationGroupAdapter.mNotificationInfoManager;
                            subscreenNotificationInfoManager2.getClass();
                            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo2.mRow;
                            if (expandableNotificationRow.mIsSummaryWithChildren) {
                                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                                int min = Math.min(notificationChildrenContainer.getNotificationChildCount(), 8);
                                for (int i = 0; i < min; i++) {
                                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i);
                                    expandableNotificationRow2.mEntry.getClass();
                                    subscreenNotificationInfoManager2.mGroupDataArray.add(subscreenNotificationInfoManager2.createItemsData(expandableNotificationRow2));
                                }
                            }
                            subscreenSubRoomNotification2.mNotificationGroupAdapter.notifyDataSetChanged();
                            subscreenSubRoomNotification2.mIsShownGroup = true;
                            subscreenSubRoomNotification2.mNotificationInfoManager.setShownGroup(true);
                            SubscreenSubRoomNotification.getDeviceModel().updateMainHeaderViewVisibility(0);
                            SubscreenSubRoomNotification.getDeviceModel().initMainHeaderViewItems(SubscreenSubRoomNotification.mContext, subscreenNotificationInfo2, false);
                            SubscreenSubRoomNotification.getDeviceModel().setDimOnMainBackground(subscreenSubRoomNotification2.mSubscreenMainLayout);
                        }
                    }
                    subscreenParentItemViewHolder.mClickAnimator = null;
                }
            }, 300L);
        }
    }

    public final void initTranslationX() {
        View view = this.itemView;
        if (view != null) {
            view.setTranslationX(0.0f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setIconView(SubscreenParentAdapter subscreenParentAdapter, boolean z) {
        ImageView imageView;
        boolean subIconVisible = subscreenParentAdapter.mDeviceModel.getSubIconVisible(this.mInfo.mIsMessagingStyle, subscreenParentAdapter.mSubRoomNotification.mIsShownGroup);
        View view = this.itemView;
        ImageView imageView2 = (ImageView) view.findViewById(R.id.icon_conversation);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.icon);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.app_icon);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.icon_container);
        View findViewById = view.findViewById(R.id.spacer);
        if (frameLayout != null && findViewById != null) {
            subscreenParentAdapter.mDeviceModel.updateIconContainer(view, this.mInfo.mIsMessagingStyle);
        }
        boolean z2 = false;
        if (imageView2 != null && z) {
            SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
            if (subscreenNotificationInfo.mIsMessagingStyle && (subscreenNotificationInfo.mConversationIcon != null || subscreenNotificationInfo.mLargeIcon != null)) {
                imageView2.setVisibility(0);
                SubscreenNotificationInfo subscreenNotificationInfo2 = this.mInfo;
                Icon icon = subscreenNotificationInfo2.mConversationIcon;
                if (icon != null) {
                    imageView2.setImageIcon(icon);
                } else {
                    imageView2.setImageIcon(subscreenNotificationInfo2.mLargeIcon);
                }
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                if (imageView4 != null) {
                    imageView4.setVisibility(8);
                }
                imageView = (ImageView) view.findViewById(R.id.subscreen_notification_sub_icon);
                if (imageView == null) {
                    imageView.clearColorFilter();
                    imageView.setBackground(null);
                    imageView.setImageDrawable(null);
                    imageView.setPadding(0, 0, 0, 0);
                    if (subIconVisible) {
                        if (subscreenParentAdapter.mDeviceModel.isShowNotificationAppIcon()) {
                            imageView.setImageDrawable(this.mInfo.mAppIcon);
                        } else {
                            subscreenParentAdapter.mDeviceModel.updateSmallIconBg(imageView, false, false, true);
                            imageView.setImageDrawable(this.mInfo.mIcon);
                            subscreenParentAdapter.mDeviceModel.updateIconColor(imageView, this.mInfo.mRow.mEntry);
                        }
                        imageView.setVisibility(0);
                    } else {
                        imageView.setVisibility(8);
                    }
                    if (subIconVisible && this.mInfo.mRow.mEntry.getChannel().isImportantConversation()) {
                        z2 = true;
                    }
                    subscreenParentAdapter.mDeviceModel.updateImportBadgeIconRing(view, z2);
                    return;
                }
                return;
            }
        }
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
        if (imageView3 != null) {
            imageView3.clearColorFilter();
            imageView3.setBackground(null);
            imageView3.setImageDrawable(null);
            imageView3.setPadding(0, 0, 0, 0);
        }
        if (subscreenParentAdapter.mDeviceModel.isShowNotificationAppIcon()) {
            if (!this.mNotificationInfoManager.mIsShownGroup) {
                SubscreenNotificationInfo subscreenNotificationInfo3 = this.mInfo;
                if (subscreenNotificationInfo3.mAppIcon != null && !subscreenNotificationInfo3.useSmallIcon()) {
                    if (imageView4 != null) {
                        if (imageView3 != null) {
                            imageView3.setVisibility(8);
                        }
                        imageView4.setVisibility(0);
                        imageView4.setImageDrawable(this.mInfo.mAppIcon);
                    } else if (imageView3 != null) {
                        imageView3.setVisibility(0);
                        imageView3.setImageDrawable(this.mInfo.mAppIcon);
                    }
                }
            }
            if (imageView3 != null) {
                imageView3.setVisibility(0);
                imageView3.setImageDrawable(this.mInfo.mIcon);
            }
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
            subscreenParentAdapter.mDeviceModel.updateSmallIconSquircleBg(imageView3, false, false);
            subscreenParentAdapter.mDeviceModel.updateIconColor(imageView3, this.mInfo.mRow.mEntry);
        } else {
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
            if (imageView3 != null) {
                imageView3.setVisibility(0);
                subscreenParentAdapter.mDeviceModel.updateSmallIconBg(imageView3, false, false, false);
                imageView3.setImageDrawable(this.mInfo.mIcon);
                subscreenParentAdapter.mDeviceModel.updateIconColor(imageView3, this.mInfo.mRow.mEntry);
            }
        }
        subIconVisible = false;
        imageView = (ImageView) view.findViewById(R.id.subscreen_notification_sub_icon);
        if (imageView == null) {
        }
    }

    public final void setUnreadMessageCount(Context context) {
        int unreadCount;
        TextView textView = this.mUnreadMessageCount;
        if (textView != null) {
            FrameLayout frameLayout = this.mUnreadMessageCountLayout;
            if (frameLayout != null) {
                frameLayout.setVisibility(8);
            }
            textView.setVisibility(8);
            if (!this.mInfo.mIsMessagingStyle || (unreadCount = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).getUnreadCount(this.mInfo.mRow.mEntry)) <= 1) {
                return;
            }
            String string = context.getResources().getString(17043086, 99);
            if (unreadCount < 100) {
                string = String.format(Locale.getDefault(), "%d", Integer.valueOf(unreadCount));
            }
            textView.setText(string);
            textView.setVisibility(0);
            if (frameLayout != null) {
                frameLayout.setVisibility(0);
            }
        }
    }

    public final void updateTitleAndContent(Context context) {
        String title = this.mInfo.getTitle();
        SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
        String str = subscreenNotificationInfo.mContent;
        String str2 = subscreenNotificationInfo.mAppName;
        boolean z = true;
        boolean z2 = title == null || title.trim().isEmpty();
        if (str != null && !str.trim().isEmpty()) {
            z = false;
        }
        TextView textView = this.mContent;
        textView.setVisibility(0);
        TextView textView2 = this.mTitle;
        textView2.setText(title);
        textView.setText(str);
        if (z2) {
            textView.setVisibility(8);
            textView2.setText(str);
            if (z) {
                textView2.setText(str2);
            }
        } else {
            textView2.setText(title.replace("\n", " ").trim());
        }
        if (z) {
            textView.setVisibility(8);
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_one_line_item_vertical_margin_b5);
            LinearLayout linearLayout = (LinearLayout) this.itemView.findViewById(R.id.subscreen_notification_text_layout);
            if (linearLayout != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.topMargin = dimensionPixelSize;
                layoutParams.bottomMargin = dimensionPixelSize;
                linearLayout.setLayoutParams(layoutParams);
            }
        } else {
            textView.setText(str.replace("\n", " "));
        }
        if (z2 || z) {
            Log.e("SubscreenParentItemViewHolder", "Title : " + z2 + ", Content : " + z);
        }
    }
}
