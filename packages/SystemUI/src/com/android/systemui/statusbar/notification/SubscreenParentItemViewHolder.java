package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;

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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0053, code lost:
    
        if (com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.isOnlyGroupSummary(r3) != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void animateClickNotification(com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager r12, final com.android.systemui.statusbar.notification.SubscreenSubRoomNotification r13, final boolean r14) {
        /*
            r11 = this;
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r0 = r11.mInfo
            boolean r1 = r0.mIsMessagingStyle
            if (r1 == 0) goto La
            java.lang.String r2 = "conversation"
        L8:
            r6 = r2
            goto Ld
        La:
            java.lang.String r2 = "normal"
            goto L8
        Ld:
            if (r1 == 0) goto L18
            boolean r1 = r0.mRemoteinput
            if (r1 == 0) goto L18
            java.lang.String r1 = "replyable"
        L16:
            r8 = r1
            goto L1b
        L18:
            java.lang.String r1 = "non-conversation"
            goto L16
        L1b:
            com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager r1 = r11.mNotificationInfoManager
            boolean r1 = r1.mIsShownGroup
            if (r1 == 0) goto L25
            java.lang.String r1 = "QPN101"
        L23:
            r3 = r1
            goto L28
        L25:
            java.lang.String r1 = "QPN100"
            goto L23
        L28:
            java.lang.String r10 = r0.mPkg
            java.lang.String r7 = "type2"
            java.lang.String r9 = "app"
            java.lang.String r4 = "QPNE0200"
            java.lang.String r5 = "type"
            com.android.systemui.util.SystemUIAnalytics.sendEventCDLog(r3, r4, r5, r6, r7, r8, r9, r10)
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r0 = r11.mInfo
            boolean r0 = r0.mGroupSummary
            r1 = 300(0x12c, double:1.48E-321)
            if (r0 == 0) goto L55
            r13.getClass()
            com.android.systemui.statusbar.notification.SubscreenDeviceModelParent r0 = com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.getDeviceModel()
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r3 = r11.mInfo
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r3.mRow
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.mEntry
            r0.getClass()
            boolean r0 = com.android.systemui.statusbar.notification.SubscreenDeviceModelParent.isOnlyGroupSummary(r3)
            if (r0 == 0) goto L73
        L55:
            r13.getClass()
            com.android.systemui.statusbar.notification.SubscreenDeviceModelParent r0 = com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.getDeviceModel()
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r3 = r11.mInfo
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r3.mRow
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.mEntry
            boolean r0 = r0.isLaunchApp(r3)
            if (r0 == 0) goto L73
            android.os.Handler r12 = r11.mHandler
            com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda0 r14 = new com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda0
            r14.<init>()
            r12.postDelayed(r14, r1)
            return
        L73:
            android.animation.Animator r0 = r11.mClickAnimator
            if (r0 != 0) goto L8a
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r0 = r11.mInfo
            java.lang.String r0 = r0.mKey
            r13.mRecyclerViewItemSelectKey = r0
            android.widget.LinearLayout r0 = r13.mSubscreenMainLayout
            com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda1 r3 = new com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder$$ExternalSyntheticLambda1
            r3.<init>()
            android.animation.Animator r12 = r12.alphaAnimatedMainView(r1, r0, r3)
            r11.mClickAnimator = r12
        L8a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder.animateClickNotification(com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager, com.android.systemui.statusbar.notification.SubscreenSubRoomNotification, boolean):void");
    }

    public final void initTranslationX() {
        View view = this.itemView;
        if (view != null) {
            view.setTranslationX(0.0f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setIconView(com.android.systemui.statusbar.notification.SubscreenParentAdapter r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenParentItemViewHolder.setIconView(com.android.systemui.statusbar.notification.SubscreenParentAdapter, boolean):void");
    }

    public final void setUnreadMessageCount(Context context) {
        if (this.mUnreadMessageCount != null) {
            FrameLayout frameLayout = this.mUnreadMessageCountLayout;
            if (frameLayout != null) {
                frameLayout.setVisibility(8);
            }
            this.mUnreadMessageCount.setVisibility(8);
            if (this.mInfo.mIsMessagingStyle) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                NotificationEntry notificationEntry = this.mInfo.mRow.mEntry;
                Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(subscreenNotificationController.context, notificationEntry.mSbn.getNotification());
                ConversationNotificationManager conversationNotificationManager = subscreenNotificationController.conversationNotificationManager;
                Object compute = conversationNotificationManager.states.compute(notificationEntry.mKey, new ConversationNotificationManager$getUnreadCount$1(notificationEntry, conversationNotificationManager, recoverBuilder));
                Intrinsics.checkNotNull(compute);
                int i = ((ConversationNotificationManager.ConversationState) compute).unreadCount;
                if (i > 1) {
                    String string = context.getResources().getString(17043306, 99);
                    if (i < 100) {
                        string = String.format(Locale.getDefault(), "%d", Integer.valueOf(i));
                    }
                    this.mUnreadMessageCount.setText(string);
                    this.mUnreadMessageCount.setVisibility(0);
                    FrameLayout frameLayout2 = this.mUnreadMessageCountLayout;
                    if (frameLayout2 != null) {
                        frameLayout2.setVisibility(0);
                    }
                }
            }
            this.mUnreadMessageCount.setVisibility(8);
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
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_one_line_item_vertical_margin_b5);
            LinearLayout linearLayout = (LinearLayout) this.itemView.findViewById(R.id.subscreen_notification_text_layout);
            if (linearLayout != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.topMargin = dimensionPixelSize;
                layoutParams.bottomMargin = dimensionPixelSize;
                linearLayout.setLayoutParams(layoutParams);
            }
        } else {
            this.mContent.setText(str.replace("\n", " "));
        }
        if (z2 || z) {
            Log.e("SubscreenParentItemViewHolder", "Title : " + z2 + ", Content : " + z);
        }
    }
}
