package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.AlphaOptimizedFrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FakeShadowView extends AlphaOptimizedFrameLayout {
    public final View mFakeShadow;
    public float mOutlineAlpha;
    public final int mShadowMinHeight;

    public FakeShadowView(Context context) {
        this(context, null);
    }

    public FakeShadowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FakeShadowView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FakeShadowView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        View view = new View(context);
        this.mFakeShadow = view;
        view.setVisibility(4);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (getResources().getDisplayMetrics().density * 48.0f)));
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.FakeShadowView.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view2, Outline outline) {
                outline.setRect(0, 0, FakeShadowView.this.getWidth(), FakeShadowView.this.mFakeShadow.getHeight());
                outline.setAlpha(FakeShadowView.this.mOutlineAlpha);
            }
        });
        addView(view);
        this.mShadowMinHeight = Math.max(1, context.getResources().getDimensionPixelSize(R.dimen.notification_divider_height));
    }
}
