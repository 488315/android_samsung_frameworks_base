package com.android.systemui.qs.animator;

import android.content.Context;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.util.DeviceState;

/* loaded from: classes2.dex */
public class QsCoverAnimator {
    public final TouchAnimator mPanelViewAlphaAnimator;
    public final TouchAnimator mPanelViewTranslationAnimator;
    public final View mQSPanel;

    public QsCoverAnimator(Context context, SubscreenQsPanelController subscreenQsPanelController) {
        View view = subscreenQsPanelController.getSubRoomQuickPanel().mMainView;
        this.mQSPanel = view;
        if (view == null) {
            return;
        }
        ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getClass();
        int screenHeight = DeviceState.getScreenHeight(context);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(view, "translationY", -screenHeight, 0.0f);
        this.mPanelViewTranslationAnimator = builder.build();
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        builder2.addFloat(view, "alpha", 0.1f, 1.0f);
        builder2.mStartDelay = 0.35f;
        this.mPanelViewAlphaAnimator = builder2.build();
    }
}
