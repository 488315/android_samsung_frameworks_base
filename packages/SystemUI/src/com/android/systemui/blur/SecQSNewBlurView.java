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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecQSNewBlurView extends FrameLayout {
    public ImageView imageView;
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
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setBackground(getContext().getDrawable(R.drawable.qs_blur_drawable));
        this.imageView = (ImageView) findViewById(R.id.qs_blur_gradient);
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f);
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha((int) (255.0f * f));
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
