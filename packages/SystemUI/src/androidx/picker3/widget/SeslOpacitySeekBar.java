package androidx.picker3.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.core.graphics.ColorUtils;

/* loaded from: classes.dex */
class SeslOpacitySeekBar extends SeekBar {
    public final int[] mColors;
    public GradientDrawable mProgressDrawable;

    public SeslOpacitySeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[]{-1, -16777216};
    }

    public final void changeColorBase(int i, int i2) {
        if (this.mProgressDrawable != null) {
            int alphaComponent = ColorUtils.setAlphaComponent(i, 255);
            int[] iArr = this.mColors;
            iArr[1] = alphaComponent;
            this.mProgressDrawable.setColors(iArr);
            setProgressDrawable(this.mProgressDrawable);
            float[] fArr = new float[3];
            Color.colorToHSV(alphaComponent, fArr);
            this.mColors[0] = Color.HSVToColor(0, fArr);
            this.mColors[1] = Color.HSVToColor(255, fArr);
            setProgress(i2);
        }
    }

    public final void initColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        int iAlpha = Color.alpha(i);
        this.mColors[0] = Color.HSVToColor(0, fArr);
        this.mColors[1] = Color.HSVToColor(255, fArr);
        setProgress(iAlpha);
    }
}
