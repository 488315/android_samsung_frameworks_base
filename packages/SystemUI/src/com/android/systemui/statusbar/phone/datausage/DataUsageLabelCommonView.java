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
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DataUsageLabelCommonView extends TextView {
    public Display mDisplay;
    public DisplayMetrics mDisplayMetrics;
    public final Context mViewContext;

    public DataUsageLabelCommonView(Context context) {
        super(context);
        this.mViewContext = context;
    }

    public final void dynamicallyReduceTextSize() {
        int dimensionPixelSize = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() ? this.mViewContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size_tablet) : this.mViewContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_text_size);
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
                    StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i2, "dynamicallyReduceTextSize(", " done ! ) maxWidthPixels:", ", textWidth:");
                    ViewPager$$ExternalSyntheticOutline0.m(m, measureText, ", densityDPI:", i, ", defaultTextSize:");
                    m.append(dimensionPixelSize);
                    m.append(", newScaleRatio:");
                    m.append(max);
                    Log.d("DataUsageLabelCommonView", m.toString());
                    return;
                }
                return;
            }
            float f2 = f;
            int i4 = i3 + 1;
            String str = charSequence;
            float f3 = f2 - ((i / 160.0f) * i4);
            if (DataUsageLabelManager.DEBUG) {
                StringBuilder sb = new StringBuilder("dynamicallyReduceTextSize(");
                sb.append(i3);
                sb.append(") scaledNewFontSize:");
                sb.append(f3);
                sb.append(", maxWidthPixels:");
                ViewPager$$ExternalSyntheticOutline0.m(sb, i2, ", textWidth:", measureText, ", densityDPI:");
                ViewPager$$ExternalSyntheticOutline0.m(sb, i, ", defaultTextSize:", dimensionPixelSize, ", newScaleRatio:");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(max, "DataUsageLabelCommonView", sb);
            }
            setTextSize(0, f3);
            f = f2;
            charSequence = str;
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
