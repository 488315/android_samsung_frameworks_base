package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;

/* loaded from: classes3.dex */
public class HybridConversationNotificationView extends HybridNotificationView {
    public View mConversationFacePile;
    public ViewStub mConversationFacePileStub;
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
    public final void bind(CharSequence charSequence, CharSequence charSequence2) {
        int i = AsyncHybridViewInflation.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.notification_async_hybrid_view_inflation is enabled.");
    }

    public TextView getConversationSenderNameView() {
        return this.mConversationSenderName;
    }

    @Override // com.android.systemui.statusbar.notification.row.HybridNotificationView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationIconView = (ImageView) requireViewById(R.id.dvorak);
        int i = AsyncHybridViewInflation.$r8$clinit;
        this.mConversationFacePileStub = (ViewStub) requireViewById(R.id.discouraged);
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
