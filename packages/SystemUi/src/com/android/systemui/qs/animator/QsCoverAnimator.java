package com.android.systemui.qs.animator;

import android.content.Context;
import android.view.View;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.TouchAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsCoverAnimator {
    public TouchAnimator mPanelViewAlphaAnimator;
    public TouchAnimator mPanelViewTranslationAnimator;
    public final View mQSPanel;

    public QsCoverAnimator(Context context, SubscreenQsPanelController subscreenQsPanelController) {
        View view = subscreenQsPanelController.getSubRoomQuickPanel().mMainView;
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
