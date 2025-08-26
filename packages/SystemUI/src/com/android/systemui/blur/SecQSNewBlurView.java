package com.android.systemui.blur;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SemBlurInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.systemui.R;

/* loaded from: classes.dex */
public final class SecQSNewBlurView extends FrameLayout {
    public ImageView imageView;
    public boolean isBlurBlocked;
    public final int[] newPos;
    public final QSColorCurve qsColorCurve;

    public SecQSNewBlurView(Context context) {
        super(context);
        this.newPos = new int[2];
        this.qsColorCurve = new QSColorCurve(getContext());
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ImageView imageView = this.imageView;
        if (imageView != null) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.qs_blur_gradient_drawable));
        }
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
        builder.setBackgroundCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_corner_radius));
        builder.setRadius(0);
        semSetBlurInfo(builder.build());
        setAlpha(getAlpha());
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setBackground(getContext().getDrawable(R.drawable.qs_blur_drawable));
        this.imageView = (ImageView) findViewById(R.id.qs_blur_gradient);
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
        builder.setBackgroundCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_corner_radius));
        builder.setRadius(0);
        semSetBlurInfo(builder.build());
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (this.isBlurBlocked) {
            f = 0.0f;
        }
        super.setAlpha(f);
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha((int) (255.0f * f));
        }
        ImageView imageView = this.imageView;
        if (imageView != null) {
            imageView.setAlpha(f);
        }
        BackgroundBlurDrawable background2 = getBackground();
        BackgroundBlurDrawable backgroundBlurDrawable = background2 instanceof BackgroundBlurDrawable ? background2 : null;
        if (backgroundBlurDrawable != null) {
            this.qsColorCurve.setFraction(f);
            QSColorCurve qSColorCurve = this.qsColorCurve;
            backgroundBlurDrawable.setBlurColorCurve(new SemBlurInfo.ColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY));
            backgroundBlurDrawable.setBlurRadius((int) this.qsColorCurve.radius);
        }
    }

    public SecQSNewBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.newPos = new int[2];
        this.qsColorCurve = new QSColorCurve(getContext());
    }
}
