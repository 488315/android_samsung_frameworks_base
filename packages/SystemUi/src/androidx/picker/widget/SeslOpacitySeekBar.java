package androidx.picker.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class SeslOpacitySeekBar extends SeekBar {
    public final int[] mColors;
    public GradientDrawable mProgressDrawable;

    public SeslOpacitySeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new int[]{-1, EmergencyPhoneWidget.BG_COLOR};
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
