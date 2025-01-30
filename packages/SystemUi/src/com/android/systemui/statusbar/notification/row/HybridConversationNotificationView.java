package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class HybridConversationNotificationView extends HybridNotificationView {
    public View mConversationFacePile;
    public ImageView mConversationIconView;
    public TextView mConversationSenderName;
    public int mFacePileAvatarSize;
    public int mFacePileProtectionWidth;
    public int mFacePileSize;
    public int mSingleAvatarSize;

    public HybridConversationNotificationView(Context context) {
        this(context, null);
    }

    public static void setSize(View view, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView
    public final void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        if (!(view instanceof ConversationLayout)) {
            super.bind(charSequence, charSequence2, view);
            return;
        }
        ConversationLayout conversationLayout = (ConversationLayout) view;
        Icon conversationIcon = conversationLayout.getConversationIcon();
        if (conversationIcon != null) {
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setVisibility(0);
            this.mConversationIconView.setImageIcon(conversationIcon);
            setSize(this.mConversationIconView, this.mSingleAvatarSize);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            View requireViewById = requireViewById(R.id.conversation_icon_badge_bg);
            this.mConversationFacePile = requireViewById;
            ImageView imageView = (ImageView) requireViewById.requireViewById(R.id.conversation_icon_container);
            ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_icon_badge_ring);
            ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(R.id.conversation_image_message_container);
            conversationLayout.bindFacePile(imageView, imageView2, imageView3);
            setSize(this.mConversationFacePile, this.mFacePileSize);
            setSize(imageView2, this.mFacePileAvatarSize);
            setSize(imageView3, this.mFacePileAvatarSize);
            setSize(imageView, (this.mFacePileProtectionWidth * 2) + this.mFacePileAvatarSize);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
            this.mTransformationHelper.addViewTransformingToSimilar(imageView);
        }
        CharSequence conversationTitle = conversationLayout.getConversationTitle();
        if (!TextUtils.isEmpty(conversationTitle)) {
            charSequence = conversationTitle;
        }
        if (conversationLayout.isOneToOne()) {
            this.mConversationSenderName.setVisibility(8);
        } else {
            this.mConversationSenderName.setVisibility(0);
            this.mConversationSenderName.setText(conversationLayout.getConversationSenderName());
        }
        CharSequence conversationText = conversationLayout.getConversationText();
        if (!TextUtils.isEmpty(conversationText)) {
            charSequence2 = conversationText;
        }
        super.bind(charSequence, charSequence2, conversationLayout);
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationIconView = (ImageView) requireViewById(R.id.costsMoney);
        this.mConversationFacePile = requireViewById(R.id.conversation_icon_badge_bg);
        TextView textView = (TextView) requireViewById(com.android.systemui.R.id.conversation_notification_sender);
        this.mConversationSenderName = textView;
        int i = this.mSecondaryTextColor;
        if (i != 1) {
            textView.setTextColor(i);
        }
        this.mFacePileSize = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.conversation_single_line_face_pile_size);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.conversation_single_line_face_pile_avatar_size);
        this.mSingleAvatarSize = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.conversation_single_line_avatar_size);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.conversation_single_line_face_pile_protection_width);
        this.mTransformationHelper.setCustomTransformation(new HybridNotificationView.FadeOutAndDownWithTitleTransformation(this.mConversationSenderName), this.mConversationSenderName.getId());
        this.mTransformationHelper.addTransformedView(this.mConversationSenderName);
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView
    public final void setNotificationFaded(boolean z) {
        NotificationFadeAware.setLayerTypeForFaded(this.mConversationFacePile, z);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HybridConversationNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
