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
import android.widget.flags.Flags;
import com.android.internal.widget.ConversationAvatarData;
import com.android.internal.widget.ConversationHeaderData;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.ConversationStyleSetAvatarAsync;
import java.util.Objects;

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
        AsyncHybridViewInflation.assertInLegacyMode();
        if (!(view instanceof ConversationLayout)) {
            super.bind(charSequence, charSequence2, view);
            return;
        }
        ConversationLayout conversationLayout = (ConversationLayout) view;
        AsyncHybridViewInflation.assertInLegacyMode();
        int i = ConversationStyleSetAvatarAsync.$r8$clinit;
        if (Flags.conversationStyleSetAvatarAsync()) {
            AsyncHybridViewInflation.assertInLegacyMode();
            ConversationHeaderData conversationHeaderData = conversationLayout.getConversationHeaderData();
            Objects.requireNonNull(conversationHeaderData, "conversationHeaderData should not be null");
            ConversationAvatarData.OneToOneConversationAvatarData conversationAvatar = conversationHeaderData.getConversationAvatar();
            Objects.requireNonNull(conversationAvatar, "conversationAvatar should not be null");
            if (conversationAvatar instanceof ConversationAvatarData.OneToOneConversationAvatarData) {
                this.mConversationFacePile.setVisibility(8);
                this.mConversationIconView.setVisibility(0);
                this.mConversationIconView.setImageDrawable(conversationAvatar.mDrawable);
                setSize(this.mConversationIconView, this.mSingleAvatarSize);
            } else {
                this.mConversationIconView.setVisibility(8);
                this.mConversationFacePile.setVisibility(0);
                View requireViewById = requireViewById(R.id.crossfade);
                this.mConversationFacePile = requireViewById;
                ImageView imageView = (ImageView) requireViewById.requireViewById(R.id.current_scene);
                ImageView imageView2 = (ImageView) this.mConversationFacePile.requireViewById(R.id.crosshair);
                ImageView imageView3 = (ImageView) this.mConversationFacePile.requireViewById(R.id.customPanel);
                conversationLayout.bindFacePileWithDrawable(imageView, imageView2, imageView3, (ConversationAvatarData.GroupConversationAvatarData) conversationAvatar);
                setSize(this.mConversationFacePile, this.mFacePileSize);
                setSize(imageView2, this.mFacePileAvatarSize);
                setSize(imageView3, this.mFacePileAvatarSize);
                setSize(imageView, (this.mFacePileProtectionWidth * 2) + this.mFacePileAvatarSize);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView3);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView2);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView);
            }
        } else {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            if (!(!Flags.conversationStyleSetAvatarAsync())) {
                throw new IllegalStateException("Legacy code path not supported when android.widget.flags.conversation_style_set_avatar_async is enabled.".toString());
            }
            AsyncHybridViewInflation.assertInLegacyMode();
            Icon conversationIcon = conversationLayout.getConversationIcon();
            if (conversationIcon != null) {
                this.mConversationFacePile.setVisibility(8);
                this.mConversationIconView.setVisibility(0);
                this.mConversationIconView.setImageIcon(conversationIcon);
                setSize(this.mConversationIconView, this.mSingleAvatarSize);
            } else {
                this.mConversationIconView.setVisibility(8);
                this.mConversationFacePile.setVisibility(0);
                View requireViewById2 = requireViewById(R.id.crossfade);
                this.mConversationFacePile = requireViewById2;
                ImageView imageView4 = (ImageView) requireViewById2.requireViewById(R.id.current_scene);
                ImageView imageView5 = (ImageView) this.mConversationFacePile.requireViewById(R.id.crosshair);
                ImageView imageView6 = (ImageView) this.mConversationFacePile.requireViewById(R.id.customPanel);
                conversationLayout.bindFacePile(imageView4, imageView5, imageView6);
                setSize(this.mConversationFacePile, this.mFacePileSize);
                setSize(imageView5, this.mFacePileAvatarSize);
                setSize(imageView6, this.mFacePileAvatarSize);
                setSize(imageView4, (this.mFacePileProtectionWidth * 2) + this.mFacePileAvatarSize);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView6);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView5);
                this.mTransformationHelper.addViewTransformingToSimilar(imageView4);
            }
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

    public TextView getConversationSenderNameView() {
        return this.mConversationSenderName;
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationIconView = (ImageView) requireViewById(R.id.dangerous);
        int i = AsyncHybridViewInflation.$r8$clinit;
        com.android.systemui.Flags.notificationAsyncHybridViewInflation();
        this.mConversationFacePile = requireViewById(R.id.crossfade);
        TextView textView = (TextView) requireViewById(com.android.systemui.R.id.conversation_notification_sender);
        this.mConversationSenderName = textView;
        int i2 = this.mSecondaryTextColor;
        if (i2 != 1) {
            textView.setTextColor(i2);
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
