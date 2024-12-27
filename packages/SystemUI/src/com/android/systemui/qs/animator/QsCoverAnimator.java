package com.android.systemui.qs.animator;

import android.content.Context;
import android.view.View;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.SubscreenSubRoomQuickSettings;
import com.android.systemui.qs.TouchAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QsCoverAnimator {
    public final TouchAnimator mPanelViewAlphaAnimator;
    public final TouchAnimator mPanelViewTranslationAnimator;
    public final View mQSPanel;

    public QsCoverAnimator(Context context, SubscreenQsPanelController subscreenQsPanelController) {
        if (subscreenQsPanelController.mSubRoomQuickSettings == null) {
            subscreenQsPanelController.mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(subscreenQsPanelController.mContext, subscreenQsPanelController.mInjectionInflater);
        }
        View view = subscreenQsPanelController.mSubRoomQuickSettings.mMainView;
        this.mQSPanel = view;
        if (view == null) {
            return;
        }
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(view, "translationY", -720.0f, 0.0f);
        this.mPanelViewTranslationAnimator = builder.build();
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        builder2.addFloat(view, "alpha", 0.1f, 1.0f);
        builder2.mStartDelay = 0.35f;
        this.mPanelViewAlphaAnimator = builder2.build();
    }
}
