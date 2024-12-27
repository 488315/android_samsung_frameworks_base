package com.android.systemui.statusbar.notification;

import android.app.PendingIntent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.SystemUIAnalytics;

public class SubscreenParentDetailItemViewHolder extends RecyclerView.ViewHolder {
    public SubscreenNotificationDetailAdapter mAdapter;
    public final TextView mAppName;
    public final ImageView mBackButton;
    public final LinearLayout mBodyLayout;
    public final View mCallBackButton;
    public final View mClearButton;
    public final LinearLayout mContentLayout;
    public final LinearLayout mDetailButtonLayout;
    public final TextView mEditButton;
    public final Handler mHandler;
    public final LinearLayout mHeaderLayout;
    public final ImageView mIcon;
    public SubscreenNotificationInfo mInfo;
    public final AnonymousClass2 mInitFocusRunnable;
    public final View mOpenAppButton;
    public final TextView mReplyButton;
    public final LinearLayout mReplyContainer;
    public final ImageView mReplyEmojiButton;
    public final ImageView mReplyVoiceButton;
    public final LinearLayout mReplyVoiceEmojiLayout;
    public final LinearLayout mReplylayout;
    public final ImageView mSecureIcon;
    public final LinearLayout mSmartReplyLayout;
    public final TextView mTitle;
    public final ImageView mTwoPhoneIcon;

    public SubscreenParentDetailItemViewHolder(View view) {
        super(view);
        this.mHandler = new Handler();
        this.mInitFocusRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenParentDetailItemViewHolder.2
            @Override // java.lang.Runnable
            public final void run() {
                SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = SubscreenParentDetailItemViewHolder.this;
                subscreenParentDetailItemViewHolder.mHeaderLayout.setContentDescription(subscreenParentDetailItemViewHolder.mInfo.mAppName);
                SubscreenParentDetailItemViewHolder.this.mHeaderLayout.requestAccessibilityFocus();
            }
        };
        this.mAppName = (TextView) view.findViewById(R.id.subscreen_header_app_name);
        this.mIcon = (ImageView) view.findViewById(R.id.subscreen_header_icon);
        this.mContentLayout = (LinearLayout) view.findViewById(R.id.content_layout);
        this.mTitle = (TextView) view.findViewById(R.id.name_title);
        this.mEditButton = (TextView) view.findViewById(R.id.edit_button);
        View findViewById = view.findViewById(R.id.clear_button);
        this.mClearButton = findViewById;
        this.mOpenAppButton = view.findViewById(R.id.app_open_button);
        this.mBodyLayout = (LinearLayout) view.findViewById(R.id.body_layout);
        this.mTwoPhoneIcon = (ImageView) view.findViewById(R.id.two_phone_icon);
        this.mSecureIcon = (ImageView) view.findViewById(R.id.secure_icon);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.detail_header_layout);
        this.mHeaderLayout = linearLayout;
        this.mDetailButtonLayout = (LinearLayout) view.findViewById(R.id.detail_button_layout);
        this.mCallBackButton = view.findViewById(R.id.call_back_button);
        this.mReplyButton = (TextView) view.findViewById(R.id.reply_button);
        this.mReplylayout = (LinearLayout) view.findViewById(R.id.reply_layout);
        this.mSmartReplyLayout = (LinearLayout) view.findViewById(R.id.smart_reply_layout);
        this.mReplyContainer = (LinearLayout) view.findViewById(R.id.quick_reply_container);
        this.mReplyVoiceEmojiLayout = (LinearLayout) view.findViewById(R.id.reply_voice_emoji);
        this.mReplyVoiceButton = (ImageView) view.findViewById(R.id.reply_voice_button);
        this.mReplyEmojiButton = (ImageView) view.findViewById(R.id.reply_emoji_button);
        this.mBackButton = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.initDetailAdapterBackButton(view);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenParentDetailItemViewHolder.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = SubscreenParentDetailItemViewHolder.this;
                subscreenParentDetailItemViewHolder.mAdapter.mNotificationInfoManager.removeNotification(subscreenParentDetailItemViewHolder.mInfo.mRow.mEntry);
                ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(300, true);
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_CLEAR_NOTIFICATION, "from", "button");
            }
        });
        SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
        subscreenDeviceModelParent.getClass();
        linearLayout.setVisibility(subscreenDeviceModelParent instanceof SubscreenDeviceModelB5 ? 8 : 0);
    }

    public final void setIconDrawable() {
        ImageView imageView = this.mIcon;
        if (imageView == null) {
            return;
        }
        imageView.clearColorFilter();
        this.mIcon.setBackground(null);
        this.mIcon.setImageDrawable(null);
        this.mIcon.setPadding(0, 0, 0, 0);
        SubscreenNotificationInfo subscreenNotificationInfo = this.mInfo;
        if (subscreenNotificationInfo.mAppIcon != null && !subscreenNotificationInfo.useSmallIcon()) {
            this.mIcon.setImageDrawable(this.mInfo.mAppIcon);
            return;
        }
        this.mIcon.setImageDrawable(this.mInfo.mIcon);
        this.mAdapter.mDeviceModel.updateSmallIconSquircleBg(this.mIcon, true, false);
        this.mAdapter.mDeviceModel.updateIconColor(this.mIcon, this.mInfo.mRow.mEntry);
    }

    public final void startWaitState(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder) {
        PendingIntent pendingIntent;
        String str;
        subscreenNotificationDetailAdapter.mSelectHolder = subscreenParentDetailItemViewHolder;
        if (subscreenNotificationDetailAdapter.mReplyclicked) {
            pendingIntent = null;
            str = null;
        } else {
            pendingIntent = this.mInfo.mSbn.getNotification().contentIntent != null ? this.mInfo.mSbn.getNotification().contentIntent : this.mInfo.mSbn.getNotification().fullScreenIntent;
            str = subscreenParentDetailItemViewHolder.mInfo.mKey;
        }
        SubRoom.StateChangeListener stateChangeListener = subscreenNotificationDetailAdapter.mSubRoomNotification.mStateChangeListener;
        if (stateChangeListener != null) {
            stateChangeListener.requestCoverPopup(pendingIntent, str);
        }
    }

    public final void updateClearButtonVisibility() {
        boolean canViewBeDismissed$1 = this.mInfo.mRow.canViewBeDismissed$1();
        this.mClearButton.setVisibility(canViewBeDismissed$1 ? 0 : 8);
        Log.d("SubscreenParentDetailItemViewHolder", "updateClearButtonVisibility - " + canViewBeDismissed$1);
    }

    public final void updateShowInAppButtonVisibility() {
        Log.d("SubscreenParentDetailItemViewHolder", "updateShowInAppButtonVisibility - " + this.mInfo.mContentIntent);
        this.mOpenAppButton.setVisibility(this.mInfo.mContentIntent == null ? 8 : 0);
    }
}
