package androidx.picker.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class SeslOpacitySeekBar extends SeekBar {
    public final int[] mColors;
    public GradientDrawable mProgressDrawable;

    public SeslOpacitySeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[]{-1, -16777216};
    }

    public final void initColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        int alpha = Color.alpha(i);
        this.mColors[0] = Color.HSVToColor(0, fArr);
        this.mColors[1] = Color.HSVToColor(255, fArr);
        setProgress(alpha);
    }
}
