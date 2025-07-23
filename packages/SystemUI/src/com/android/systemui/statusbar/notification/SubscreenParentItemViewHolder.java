package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.graphics.ColorFilter;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenParentItemViewHolder extends RecyclerView.ViewHolder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Animator mClickAnimator;
    public final TextView mContent;
    public FrameLayout mDumyLayout;
    public SubscreenNotificationInfo mInfo;
    public final SubscreenNotificationListAdapter mListAdapter;
    public LinearLayout mMainLayout;
    public TextView mNotiGroupCount;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public int mPosition;
    public final ImageView mSecureIcon;
    public final TextView mTitle;
    public final ImageView mTwoPhoneIcon;

    public SubscreenParentItemViewHolder(View view) {
        super(view);
        new Handler();
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
        this.mListAdapter = SubscreenNotificationListAdapter.getInstance();
        this.mTwoPhoneIcon = (ImageView) view.findViewById(R.id.two_phone_icon);
        this.mSecureIcon = (ImageView) view.findViewById(R.id.secure_icon);
        this.mTitle = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
        this.mContent = (TextView) view.findViewById(R.id.subscreen_notification_content_text);
    }

    public final void animateClickNotification(SubscreenSubRoomNotificaitonAnimatorManager subscreenSubRoomNotificaitonAnimatorManager, final SubscreenSubRoomNotification subscreenSubRoomNotification, final boolean z) {
        SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
        boolean z2 = subscreenNotificationInfo.mIsMessagingStyle;
        SystemUIAnalytics.sendEventCDLog(this.mNotificationInfoManager.mIsShownGroup ? SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_GROUP : SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_LIST, SystemUIAnalytics.EID_QPNE_COVER_VIEW_DETAILS, "type", z2 ? SystemUIAnalytics.QPNE_VID_CONVERSATION : SystemUIAnalytics.QPNE_VID_NORMAL, "type2", (z2 && subscreenNotificationInfo.mRemoteinput) ? "replyable" : "non-conversation", SystemUIAnalytics.QPNE_KEY_APP, subscreenNotificationInfo.mPkg);
        if (this.mClickAnimator == null) {
            subscreenSubRoomNotification.mRecyclerViewItemSelectKey = this.mInfo.mKey;
            this.mClickAnimator = subscreenSubRoomNotificaitonAnimatorManager.alphaAnimatedMainView(300L, subscreenSubRoomNotification.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder = SubscreenParentItemViewHolder.this;
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenSubRoomNotification;
                    boolean z3 = z;
                    int i = SubscreenParentItemViewHolder.$r8$clinit;
                    subscreenParentItemViewHolder.getClass();
                    subscreenSubRoomNotification2.getClass();
                    boolean isKnoxSecurity = SubscreenSubRoomNotification.getDeviceModel().isKnoxSecurity(subscreenParentItemViewHolder.mInfo.mRow.mEntry);
                    SubscreenDeviceModelParent deviceModel = SubscreenSubRoomNotification.getDeviceModel();
                    NotificationEntry notificationEntry = subscreenParentItemViewHolder.mInfo.mRow.mEntry;
                    deviceModel.getClass();
                    NotificationChildrenContainer notificationChildrenContainer = notificationEntry.row.mChildrenContainer;
                    boolean z4 = notificationEntry.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer == null || notificationChildrenContainer.getNotificationChildCount() == 0);
                    boolean isInsignificantSummary = subscreenParentItemViewHolder.mInfo.mRow.isInsignificantSummary();
                    StringBuilder sb = new StringBuilder("setHeaderVIewLayout onClick mInfo.isGroupSummary() : ");
                    KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, subscreenParentItemViewHolder.mInfo.mGroupSummary, ", hideContent : ", z3, ", isKnoxSecurity : ");
                    KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, isKnoxSecurity, ", isOnlyGroupSummary : ", z4, ", isInsignificantSummary : ");
                    sb.append(isInsignificantSummary);
                    Log.e("SubscreenParentItemViewHolder", sb.toString());
                    if (!(subscreenParentItemViewHolder.mInfo.mGroupSummary || isInsignificantSummary) || z3 || z4) {
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
                            if (subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.size() > 0) {
                                subscreenNotificationGroupAdapter.mNotificationInfoManager.mGroupDataArray.clear();
                            }
                            subscreenNotificationGroupAdapter.mSummaryInfo = subscreenNotificationInfo2;
                            SubscreenNotificationInfoManager subscreenNotificationInfoManager2 = subscreenNotificationGroupAdapter.mNotificationInfoManager;
                            subscreenNotificationInfoManager2.getClass();
                            ExpandableNotificationRow expandableNotificationRow = subscreenNotificationInfo2.mRow;
                            if (expandableNotificationRow.mIsSummaryWithChildren) {
                                NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
                                int min = Math.min(notificationChildrenContainer2.getNotificationChildCount(), 8);
                                if (subscreenNotificationInfo2.mRow.isInsignificantSummary()) {
                                    min = ((ArrayList) notificationChildrenContainer2.mAttachedChildren).size() > 50 ? 50 : ((ArrayList) notificationChildrenContainer2.mAttachedChildren).size();
                                    SubscreenNotificationController subscreenNotificationController = subscreenNotificationInfoManager2.mSubscreenNotificationController;
                                    if (min <= subscreenNotificationController.mDeviceModel.mMoreNotificationCount) {
                                        RecyclerView$$ExternalSyntheticOutline0.m(subscreenNotificationController.mDeviceModel.mMoreNotificationCount, "SubscreenNotificationInfoManager", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(min, "addGroupItems - childCount : ", ", MoreNotificationCount : "));
                                    }
                                }
                                for (int i2 = 0; i2 < min; i2++) {
                                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer2.mAttachedChildren).get(i2);
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
            });
        }
    }

    public final void initTranslationX() {
        View view = this.itemView;
        if (view != null) {
            view.setTranslationX(0.0f);
        }
    }

    public final void setIconView(SubscreenParentAdapter subscreenParentAdapter, boolean z) {
        setIconView(subscreenParentAdapter, this.mInfo, z);
    }

    public final void updateTitleAndContent(SubscreenNotificationInfo subscreenNotificationInfo) {
        String title = subscreenNotificationInfo.getTitle();
        String str = subscreenNotificationInfo.mContent;
        String str2 = subscreenNotificationInfo.mAppName;
        boolean z = true;
        boolean z2 = title == null || title.trim().isEmpty();
        if (str != null && !str.trim().isEmpty()) {
            z = false;
        }
        this.mContent.setVisibility(0);
        this.mTitle.setText(title);
        this.mContent.setText(str);
        if (z2) {
            this.mContent.setVisibility(8);
            this.mTitle.setText(str);
            if (z) {
                this.mTitle.setText(str2);
            }
        } else {
            this.mTitle.setText(title.replace("\n", " ").trim());
        }
        if (z) {
            this.mContent.setVisibility(8);
        } else {
            this.mContent.setText(str.replace("\n", " "));
        }
        if (z2 || z) {
            Log.e("SubscreenParentItemViewHolder", "Title : " + z2 + ", Content : " + z);
        }
    }

    public final void setIconView(SubscreenParentAdapter subscreenParentAdapter, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) {
        boolean subIconVisible = subscreenParentAdapter.mDeviceModel.getSubIconVisible(subscreenNotificationInfo.mIsMessagingStyle, this.mNotificationInfoManager.mIsShownGroup && !(this.mNotificationInfoManager.mIsShownGroup && subscreenNotificationInfo.mRow.isInsignificant()));
        ImageView imageView = (ImageView) this.itemView.findViewById(R.id.icon_conversation);
        ImageView imageView2 = (ImageView) this.itemView.findViewById(R.id.icon);
        ImageView imageView3 = (ImageView) this.itemView.findViewById(R.id.app_icon);
        ImageView imageView4 = (ImageView) this.itemView.findViewById(R.id.more_icon);
        if (imageView4 != null) {
            imageView4.setColorFilter((ColorFilter) null);
            imageView4.setImageDrawable(null);
            imageView4.setBackground(null);
            imageView4.setVisibility(8);
        }
        if (imageView == null || !z || !subscreenNotificationInfo.mIsMessagingStyle || (subscreenNotificationInfo.mConversationIcon == null && subscreenNotificationInfo.mLargeIcon == null)) {
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            if (imageView2 != null) {
                imageView2.clearColorFilter();
                imageView2.setBackground(null);
                imageView2.setImageDrawable(null);
                imageView2.setPadding(0, 0, 0, 0);
            }
            if (!subscreenParentAdapter.mDeviceModel.isShowNotificationAppIcon()) {
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                if (imageView2 != null) {
                    if (!subscreenNotificationInfo.mRow.isInsignificantSummary() || imageView4 == null) {
                        imageView2.setVisibility(0);
                        imageView2.setImageDrawable(subscreenNotificationInfo.mIcon);
                    } else {
                        imageView2.setVisibility(8);
                        imageView4.setVisibility(0);
                        imageView4.setImageDrawable(subscreenNotificationInfo.mIcon);
                        imageView2 = imageView4;
                    }
                    subscreenParentAdapter.mDeviceModel.updateSmallIconBg(imageView2, false, false, false);
                    subscreenParentAdapter.mDeviceModel.updateIconColor(imageView2, subscreenNotificationInfo.mRow.mEntry);
                }
            } else if (subscreenNotificationInfo.mAppIcon == null || subscreenNotificationInfo.useSmallIcon() || subscreenNotificationInfo.isSportsOngoing()) {
                if (imageView2 != null) {
                    if (!subscreenNotificationInfo.mRow.isInsignificantSummary() || imageView4 == null) {
                        imageView2.setVisibility(0);
                        imageView2.setImageDrawable(subscreenNotificationInfo.mIcon);
                    } else {
                        imageView2.setVisibility(8);
                        imageView4.setVisibility(0);
                        imageView4.setImageDrawable(subscreenNotificationInfo.mIcon);
                        imageView2 = imageView4;
                    }
                }
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                subscreenParentAdapter.mDeviceModel.updateSmallIconSquircleBg(imageView2, false, false);
                subscreenParentAdapter.mDeviceModel.updateIconColor(imageView2, subscreenNotificationInfo.mRow.mEntry);
            } else if (subscreenNotificationInfo.mRow.isInsignificantSummary() && imageView4 != null) {
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                }
                imageView4.setVisibility(0);
                imageView4.setImageDrawable(subscreenNotificationInfo.mAppIcon);
            } else if (imageView3 != null) {
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                }
                imageView3.setVisibility(0);
                imageView3.setImageDrawable(subscreenNotificationInfo.mAppIcon);
            } else if (imageView2 != null) {
                imageView2.setVisibility(0);
                imageView2.setImageDrawable(subscreenNotificationInfo.mAppIcon);
            }
            subIconVisible = false;
        } else {
            imageView.setVisibility(0);
            Icon icon = subscreenNotificationInfo.mConversationIcon;
            if (icon != null) {
                imageView.setImageIcon(icon);
            } else {
                imageView.setImageIcon(subscreenNotificationInfo.mLargeIcon);
            }
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (imageView3 != null) {
                imageView3.setVisibility(8);
            }
        }
        ImageView imageView5 = (ImageView) this.itemView.findViewById(R.id.subscreen_notification_sub_icon);
        if (imageView5 != null) {
            imageView5.clearColorFilter();
            imageView5.setBackground(null);
            imageView5.setImageDrawable(null);
            imageView5.setPadding(0, 0, 0, 0);
            if (subIconVisible) {
                if (subscreenParentAdapter.mDeviceModel.isShowNotificationAppIcon()) {
                    imageView5.setImageDrawable(subscreenNotificationInfo.mAppIcon);
                } else {
                    subscreenParentAdapter.mDeviceModel.updateSmallIconBg(imageView5, false, false, true);
                    imageView5.setImageDrawable(subscreenNotificationInfo.mIcon);
                    subscreenParentAdapter.mDeviceModel.updateIconColor(imageView5, subscreenNotificationInfo.mRow.mEntry);
                }
                imageView5.setVisibility(0);
            } else {
                imageView5.setVisibility(8);
            }
            subscreenParentAdapter.mDeviceModel.updateImportBadgeIconRing(this.itemView, subIconVisible && subscreenNotificationInfo.mRow.mEntry.mRanking.getChannel().isImportantConversation());
        }
    }
}
