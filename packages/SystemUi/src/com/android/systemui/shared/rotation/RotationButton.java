package com.android.systemui.shared.rotation;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.navigationbar.NavigationBarView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RotationButton {
    default boolean acceptRotationProposal() {
        return getCurrentView() != null;
    }

    View getCurrentView();

    Drawable getImageDrawable();

    boolean hide();

    boolean isVisible();

    void setDarkIntensity(float f);

    void setOnClickListener(View.OnClickListener onClickListener);

    void setOnHoverListener(RotationButtonController$$ExternalSyntheticLambda3 rotationButtonController$$ExternalSyntheticLambda3);

    void setRotationButtonController(RotationButtonController rotationButtonController);

    void setUpdatesCallback(NavigationBarView.C18592 c18592);

    boolean show();

    void updateIcon(int i, int i2);

    default void setCanShowRotationButton(boolean z) {
    }
}
