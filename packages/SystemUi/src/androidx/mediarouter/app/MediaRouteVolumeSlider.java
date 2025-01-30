package androidx.mediarouter.app;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatSeekBar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class MediaRouteVolumeSlider extends AppCompatSeekBar {
    public int mBackgroundColor;
    public final float mDisabledAlpha;
    public boolean mHideThumb;
    public int mProgressAndThumbColor;
    public Drawable mThumb;

    public MediaRouteVolumeSlider(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int i = isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f);
        this.mThumb.setColorFilter(this.mProgressAndThumbColor, PorterDuff.Mode.SRC_IN);
        this.mThumb.setAlpha(i);
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
            Drawable findDrawableByLayerId = layerDrawable.findDrawableByLayerId(R.id.progress);
            layerDrawable.findDrawableByLayerId(R.id.background).setColorFilter(this.mBackgroundColor, PorterDuff.Mode.SRC_IN);
            progressDrawable = findDrawableByLayerId;
        }
        progressDrawable.setColorFilter(this.mProgressAndThumbColor, PorterDuff.Mode.SRC_IN);
        progressDrawable.setAlpha(i);
    }

    public final void setColor(int i, int i2) {
        if (this.mProgressAndThumbColor != i) {
            if (Color.alpha(i) != 255) {
                Log.e("MediaRouteVolumeSlider", "Volume slider progress and thumb color cannot be translucent: #" + Integer.toHexString(i));
            }
            this.mProgressAndThumbColor = i;
        }
        if (this.mBackgroundColor != i2) {
            if (Color.alpha(i2) != 255) {
                Log.e("MediaRouteVolumeSlider", "Volume slider background color cannot be translucent: #" + Integer.toHexString(i2));
            }
            this.mBackgroundColor = i2;
        }
    }

    public final void setHideThumb(boolean z) {
        if (this.mHideThumb == z) {
            return;
        }
        this.mHideThumb = z;
        super.setThumb(z ? null : this.mThumb);
    }

    @Override // android.widget.AbsSeekBar
    public final void setThumb(Drawable drawable) {
        this.mThumb = drawable;
        if (this.mHideThumb) {
            drawable = null;
        }
        super.setThumb(drawable);
    }

    public MediaRouteVolumeSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.seekBarStyle);
    }

    public MediaRouteVolumeSlider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(context);
    }
}
