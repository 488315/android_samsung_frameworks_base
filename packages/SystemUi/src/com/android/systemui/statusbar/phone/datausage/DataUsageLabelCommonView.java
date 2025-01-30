package com.android.systemui.statusbar.phone.datausage;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.hardware.display.DisplayManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DataUsageLabelCommonView extends TextView {
    public final Context mContext;
    public Display mDisplay;
    public DisplayMetrics mDisplayMetrics;

    public DataUsageLabelCommonView(Context context) {
        super(context);
        this.mContext = context;
    }

    public final void dynamicallyReduceTextSize() {
        int dimensionPixelSize = QpRune.QUICK_TABLET ? this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size_tablet) : this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size);
        float max = Math.max(1.0f, Math.min(1.3f, getResources().getConfiguration().fontScale));
        float f = dimensionPixelSize;
        int i = 0;
        setTextSize(0, f * max);
        String charSequence = getText().toString();
        TextPaint paint = getPaint();
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        int i2 = displayMetrics != null ? displayMetrics.densityDpi : 160;
        int i3 = displayMetrics != null ? displayMetrics.widthPixels : 1440;
        while (i < 10) {
            int measureText = (int) paint.measureText(charSequence);
            if (i3 <= 0 || i3 >= measureText) {
                if (DataUsageLabelManager.DEBUG) {
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("dynamicallyReduceTextSize(", i, " done ! ) maxWidthPixels:", i3, ", textWidth:");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, measureText, ", densityDPI:", i2, ", defaultTextSize:");
                    m45m.append(dimensionPixelSize);
                    m45m.append(", newScaleRatio:");
                    m45m.append(max);
                    Log.d("DataUsageLabelCommonView", m45m.toString());
                    return;
                }
                return;
            }
            String str = charSequence;
            int i4 = i + 1;
            TextPaint textPaint = paint;
            float f2 = f - ((i2 / 160.0f) * i4);
            if (DataUsageLabelManager.DEBUG) {
                StringBuilder sb = new StringBuilder("dynamicallyReduceTextSize(");
                sb.append(i);
                sb.append(") scaledNewFontSize:");
                sb.append(f2);
                sb.append(", maxWidthPixels:");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i3, ", textWidth:", measureText, ", densityDPI:");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i2, ", defaultTextSize:", dimensionPixelSize, ", newScaleRatio:");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, max, "DataUsageLabelCommonView");
            }
            setTextSize(0, f2);
            charSequence = str;
            paint = textPaint;
            i = i4;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGravity(17);
        setVisibility(0);
        this.mDisplayMetrics = new DisplayMetrics();
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (displayManager != null) {
            this.mDisplay = displayManager.getDisplay(0);
        }
        Display display = this.mDisplay;
        if (display != null) {
            display.getRealMetrics(this.mDisplayMetrics);
        }
        dynamicallyReduceTextSize();
        setTypeface(Typeface.create("sec-400", 0));
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        dynamicallyReduceTextSize();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDisplayMetrics = null;
        this.mDisplay = null;
    }

    public DataUsageLabelCommonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public DataUsageLabelCommonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }
}
