package androidx.mediarouter.app;

import android.R;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatSeekBar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class MediaRouteVolumeSlider extends AppCompatSeekBar {
    public final float mDisabledAlpha;
    public Drawable mThumb;

    public MediaRouteVolumeSlider(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int i = isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f);
        Drawable drawable = this.mThumb;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        drawable.setColorFilter(0, mode);
        this.mThumb.setAlpha(i);
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
            progressDrawable = layerDrawable.findDrawableByLayerId(R.id.progress);
            layerDrawable.findDrawableByLayerId(R.id.background).setColorFilter(0, mode);
        }
        progressDrawable.setColorFilter(0, mode);
        progressDrawable.setAlpha(i);
    }

    @Override // android.widget.AbsSeekBar
    public final void setThumb(Drawable drawable) {
        this.mThumb = drawable;
        super.setThumb(drawable);
    }

    public MediaRouteVolumeSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.seekBarStyle);
    }

    public MediaRouteVolumeSlider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedValue typedValue = new TypedValue();
        this.mDisabledAlpha = context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true) ? typedValue.getFloat() : 0.5f;
    }
}
