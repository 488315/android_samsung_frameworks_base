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
        float fMax = Math.max(1.0f, Math.min(1.3f, getResources().getConfiguration().fontScale));
        float f = dimensionPixelSize;
        setTextSize(0, f * fMax);
        String string = getText().toString();
        TextPaint paint = getPaint();
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        int i = displayMetrics != null ? displayMetrics.densityDpi : 160;
        int i2 = displayMetrics != null ? displayMetrics.widthPixels : 1440;
        int i3 = 0;
        while (i3 < 10) {
            int iMeasureText = (int) paint.measureText(string);
            if (i2 <= 0 || i2 >= iMeasureText) {
                if (DataUsageLabelManager.DEBUG) {
                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i2, "dynamicallyReduceTextSize(", " done ! ) maxWidthPixels:", ", textWidth:");
                    ViewPager$$ExternalSyntheticOutline0.m(sbM, iMeasureText, ", densityDPI:", i, ", defaultTextSize:");
                    sbM.append(dimensionPixelSize);
                    sbM.append(", newScaleRatio:");
                    sbM.append(fMax);
                    Log.d("DataUsageLabelCommonView", sbM.toString());
                    return;
                }
                return;
            }
            float f2 = f;
            int i4 = i3 + 1;
            String str = string;
            float f3 = f2 - ((i / 160.0f) * i4);
            if (DataUsageLabelManager.DEBUG) {
                StringBuilder sb = new StringBuilder("dynamicallyReduceTextSize(");
                sb.append(i3);
                sb.append(") scaledNewFontSize:");
                sb.append(f3);
                sb.append(", maxWidthPixels:");
                ViewPager$$ExternalSyntheticOutline0.m(sb, i2, ", textWidth:", iMeasureText, ", densityDPI:");
                ViewPager$$ExternalSyntheticOutline0.m(sb, i, ", defaultTextSize:", dimensionPixelSize, ", newScaleRatio:");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(fMax, "DataUsageLabelCommonView", sb);
            }
            setTextSize(0, f3);
            f = f2;
            string = str;
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
