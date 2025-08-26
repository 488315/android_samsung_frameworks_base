package androidx.picker3.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.android.systemui.R;

/* loaded from: classes.dex */
class SeslGradientColorSeekBar extends SeekBar {
    public final int[] mColors;
    public final GradientDrawable mProgressDrawable;

    public SeslGradientColorSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[]{-16777216, -1};
        context.getResources();
        this.mProgressDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.sesl_color_picker_gradient_seekbar_drawable);
    }

    public final void initColor(int i) {
        float[] fArr = {0.0f, 0.0f, 1.0f};
        Color.colorToHSV(i, fArr);
        float f = fArr[2];
        this.mColors[1] = Color.HSVToColor(fArr);
        setProgress(Math.round(f * getMax()));
    }
}
