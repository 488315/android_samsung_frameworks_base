package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.R;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.row.HybridConversationNotificationView;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationData;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.FacePile;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleIcon;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleLineViewBinder {
    static {
        new SingleLineViewBinder();
    }

    private SingleLineViewBinder() {
    }

    public static final void bind(SingleLineViewModel singleLineViewModel, HybridNotificationView hybridNotificationView) {
        ConversationData conversationData;
        ConversationData conversationData2;
        ConversationData conversationData3;
        ConversationAvatar conversationAvatar;
        if (!(hybridNotificationView instanceof HybridConversationNotificationView)) {
            if (hybridNotificationView != null) {
                hybridNotificationView.bind(singleLineViewModel != null ? singleLineViewModel.titleText : null, singleLineViewModel != null ? singleLineViewModel.contentText : null);
                return;
            }
            return;
        }
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = AsyncHybridViewInflation.$r8$clinit;
        if (singleLineViewModel != null && (conversationData3 = singleLineViewModel.conversationData) != null && (conversationAvatar = conversationData3.avatar) != null) {
            HybridConversationNotificationView hybridConversationNotificationView = (HybridConversationNotificationView) hybridNotificationView;
            hybridConversationNotificationView.getClass();
            if (conversationAvatar instanceof SingleIcon) {
                SingleIcon singleIcon = (SingleIcon) conversationAvatar;
                View view = hybridConversationNotificationView.mConversationFacePile;
                if (view != null) {
                    view.setVisibility(8);
                }
                hybridConversationNotificationView.mConversationIconView.setVisibility(0);
                hybridConversationNotificationView.mConversationIconView.setImageDrawable(singleIcon.iconDrawable);
                HybridConversationNotificationView.setSize(hybridConversationNotificationView.mConversationIconView, hybridConversationNotificationView.mSingleAvatarSize);
            } else {
                FacePile facePile = (FacePile) conversationAvatar;
                hybridConversationNotificationView.mConversationIconView.setVisibility(8);
                if (hybridConversationNotificationView.mConversationFacePile == null) {
                    hybridConversationNotificationView.mConversationFacePile = hybridConversationNotificationView.mConversationFacePileStub.inflate();
                }
                hybridConversationNotificationView.mConversationFacePile.setVisibility(0);
                ImageView imageView = (ImageView) hybridConversationNotificationView.mConversationFacePile.requireViewById(R.id.dpad);
                ImageView imageView2 = (ImageView) hybridConversationNotificationView.mConversationFacePile.requireViewById(R.id.divider);
                ImageView imageView3 = (ImageView) hybridConversationNotificationView.mConversationFacePile.requireViewById(R.id.drag);
                imageView.setImageTintList(ColorStateList.valueOf(facePile.bottomBackgroundColor));
                imageView2.setImageDrawable(facePile.bottomIconDrawable);
                imageView3.setImageDrawable(facePile.topIconDrawable);
                HybridConversationNotificationView.setSize(hybridConversationNotificationView.mConversationFacePile, hybridConversationNotificationView.mFacePileSize);
                HybridConversationNotificationView.setSize(imageView2, hybridConversationNotificationView.mFacePileAvatarSize);
                HybridConversationNotificationView.setSize(imageView3, hybridConversationNotificationView.mFacePileAvatarSize);
                HybridConversationNotificationView.setSize(imageView, (hybridConversationNotificationView.mFacePileProtectionWidth * 2) + hybridConversationNotificationView.mFacePileAvatarSize);
                hybridConversationNotificationView.mTransformationHelper.addViewTransformingToSimilar(imageView3);
                hybridConversationNotificationView.mTransformationHelper.addViewTransformingToSimilar(imageView2);
                hybridConversationNotificationView.mTransformationHelper.addViewTransformingToSimilar(imageView);
            }
        }
        HybridConversationNotificationView hybridConversationNotificationView2 = (HybridConversationNotificationView) hybridNotificationView;
        CharSequence charSequence = singleLineViewModel != null ? singleLineViewModel.titleText : null;
        CharSequence charSequence2 = singleLineViewModel != null ? singleLineViewModel.contentText : null;
        CharSequence charSequence3 = (singleLineViewModel == null || (conversationData2 = singleLineViewModel.conversationData) == null) ? null : conversationData2.conversationSenderName;
        if (singleLineViewModel != null && (conversationData = singleLineViewModel.conversationData) != null) {
            r1 = conversationData.summarization;
        }
        hybridConversationNotificationView2.getClass();
        if (TextUtils.isEmpty(r1)) {
            hybridConversationNotificationView2.mTextView.setSingleLine(true);
            if (charSequence3 == null) {
                hybridConversationNotificationView2.mConversationSenderName.setVisibility(8);
            } else {
                hybridConversationNotificationView2.mConversationSenderName.setVisibility(0);
                hybridConversationNotificationView2.mConversationSenderName.setText(charSequence3);
            }
        } else {
            hybridConversationNotificationView2.mConversationSenderName.setVisibility(8);
            hybridConversationNotificationView2.mTextView.setSingleLine(false);
            hybridConversationNotificationView2.mTextView.setMaxLines(1);
            charSequence2 = r1;
        }
        hybridConversationNotificationView2.bind(charSequence, charSequence2, TextUtils.isEmpty(r1));
    }
}
