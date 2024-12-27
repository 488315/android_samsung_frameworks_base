package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.widget.SystemUIImageView;
import com.android.wm.shell.animation.FlingAnimationUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class KeyguardAffordanceView extends SystemUIImageView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mCenterX;
    public int mCenterY;
    public final float mImageScale;
    public final int[] mTempPoint;

    public KeyguardAffordanceView(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        canvas.isHardwareAccelerated();
        canvas.save();
        float f = this.mImageScale;
        canvas.scale(f, f, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCenterX = getWidth() / 2;
        this.mCenterY = getHeight() / 2;
        getLocationInWindow(this.mTempPoint);
        float width = getRootView().getWidth();
        float f = this.mTempPoint[0] + this.mCenterX;
        Math.hypot(Math.max(width - f, f), this.mTempPoint[1] + this.mCenterY);
    }

    @Override // android.view.View
    public boolean performClick() {
        if (isClickable()) {
            return super.performClick();
        }
        return false;
    }

    public KeyguardAffordanceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardAffordanceView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public KeyguardAffordanceView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempPoint = new int[2];
        this.mImageScale = 1.0f;
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardAffordanceView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardAffordanceView keyguardAffordanceView = KeyguardAffordanceView.this;
                int i3 = KeyguardAffordanceView.$r8$clinit;
                keyguardAffordanceView.getClass();
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardAffordanceView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardAffordanceView keyguardAffordanceView = KeyguardAffordanceView.this;
                int i3 = KeyguardAffordanceView.$r8$clinit;
                keyguardAffordanceView.getClass();
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardAffordanceView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardAffordanceView keyguardAffordanceView = KeyguardAffordanceView.this;
                int i3 = KeyguardAffordanceView.$r8$clinit;
                keyguardAffordanceView.getClass();
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardAffordanceView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardAffordanceView keyguardAffordanceView = KeyguardAffordanceView.this;
                int i3 = KeyguardAffordanceView.$r8$clinit;
                keyguardAffordanceView.getClass();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ImageView);
        try {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(-1);
            obtainStyledAttributes.getColor(5, -1);
            ((ImageView) this).mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.keyguard_affordance_min_background_radius);
            new ArgbEvaluator();
            new FlingAnimationUtils(((ImageView) this).mContext.getResources().getDisplayMetrics(), 0.3f);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
