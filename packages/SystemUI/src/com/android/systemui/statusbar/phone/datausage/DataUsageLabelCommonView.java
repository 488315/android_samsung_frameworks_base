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
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DataUsageLabelCommonView extends TextView {
    public Display mDisplay;
    public DisplayMetrics mDisplayMetrics;
    public final Context mViewContext;

    public DataUsageLabelCommonView(Context context) {
        super(context);
        this.mViewContext = context;
    }

    public final void dynamicallyReduceTextSize() {
        int dimensionPixelSize = QpRune.QUICK_TABLET ? this.mViewContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size_tablet) : this.mViewContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size);
        float max = Math.max(1.0f, Math.min(1.3f, getResources().getConfiguration().fontScale));
        float f = dimensionPixelSize;
        setTextSize(0, f * max);
        String charSequence = getText().toString();
        TextPaint paint = getPaint();
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        int i = displayMetrics != null ? displayMetrics.densityDpi : 160;
        int i2 = displayMetrics != null ? displayMetrics.widthPixels : 1440;
        int i3 = 0;
        while (i3 < 10) {
            int measureText = (int) paint.measureText(charSequence);
            if (i2 <= 0 || i2 >= measureText) {
                if (DataUsageLabelManager.DEBUG) {
                    StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i3, i2, "dynamicallyReduceTextSize(", " done ! ) maxWidthPixels:", ", textWidth:");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, measureText, ", densityDPI:", i, ", defaultTextSize:");
                    m.append(dimensionPixelSize);
                    m.append(", newScaleRatio:");
                    m.append(max);
                    Log.d("DataUsageLabelCommonView", m.toString());
                    return;
                }
                return;
            }
            String str = charSequence;
            int i4 = i3 + 1;
            TextPaint textPaint = paint;
            float f2 = f - ((i / 160.0f) * i4);
            if (DataUsageLabelManager.DEBUG) {
                StringBuilder sb = new StringBuilder("dynamicallyReduceTextSize(");
                sb.append(i3);
                sb.append(") scaledNewFontSize:");
                sb.append(f2);
                sb.append(", maxWidthPixels:");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i2, ", textWidth:", measureText, ", densityDPI:");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i, ", defaultTextSize:", dimensionPixelSize, ", newScaleRatio:");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, max, "DataUsageLabelCommonView");
            }
            setTextSize(0, f2);
            charSequence = str;
            paint = textPaint;
            i3 = i4;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGravity(17);
        setVisibility(0);
        this.mDisplayMetrics = new DisplayMetrics();
        DisplayManager displayManager = (DisplayManager) this.mViewContext.getSystemService("display");
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
        this.mViewContext = context;
    }

    public DataUsageLabelCommonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mViewContext = context;
    }
}
