package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SplitShadeMediaSection extends KeyguardSection {
    public final Context context;
    public final KeyguardMediaController keyguardMediaController;
    public final int mediaContainerId = R.id.status_view_media_container;

    public SplitShadeMediaSection(Context context, KeyguardMediaController keyguardMediaController) {
        this.context = context;
        this.keyguardMediaController = keyguardMediaController;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        FrameLayout frameLayout = new FrameLayout(this.context, null);
        frameLayout.setId(this.mediaContainerId);
        int dimensionPixelSize = frameLayout.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_padding);
        int dimensionPixelSize2 = frameLayout.getContext().getResources().getDimensionPixelSize(R$dimen.status_view_margin_horizontal) + dimensionPixelSize;
        frameLayout.setPaddingRelative(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize);
        constraintLayout.addView(frameLayout);
        KeyguardMediaController keyguardMediaController = this.keyguardMediaController;
        keyguardMediaController.splitShadeContainer = frameLayout;
        keyguardMediaController.reattachHostView();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int i = this.mediaContainerId;
        constraintSet.constrainWidth(i, 0);
        constraintSet.constrainHeight(i, -2);
        constraintSet.connect(i, 3, R.id.smart_space_barrier_bottom, 4);
        constraintSet.connect(i, 6, 0, 6);
        constraintSet.connect(i, 7, R.id.split_shade_guideline, 7);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.mediaContainerId);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }
}
