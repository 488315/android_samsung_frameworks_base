package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DragAppIcon extends ImageView {
    public float mCenterX;
    public float mCenterY;
    public SpringAnimation mScaleUpAnimX;
    public SpringAnimation mScaleUpAnimY;

    public DragAppIcon(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mCenterX = getResources().getDimensionPixelSize(R.dimen.drag_app_icon_width) / 2.0f;
        this.mCenterY = getResources().getDimensionPixelSize(R.dimen.drag_app_icon_height) / 2.0f;
        SpringAnimation springAnimation = new SpringAnimation(this, DynamicAnimation.SCALE_X);
        springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.7f);
        this.mScaleUpAnimX = springAnimation;
        SpringAnimation springAnimation2 = new SpringAnimation(this, DynamicAnimation.SCALE_Y);
        springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(220.0f, 0.7f);
        this.mScaleUpAnimY = springAnimation2;
    }

    public DragAppIcon(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragAppIcon(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DragAppIcon(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
