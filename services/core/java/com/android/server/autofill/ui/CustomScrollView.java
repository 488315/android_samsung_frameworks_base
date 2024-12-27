package com.android.server.autofill.ui;

import android.R;
import android.content.Context;
import android.graphics.Point;
import android.provider.DeviceConfig;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.autofill.Helper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class CustomScrollView extends ScrollView {
    public int mAttrBasedMaxHeightPercent;
    public int mHeight;
    public int mMaxLandscapeBodyHeightPercent;
    public int mMaxPortraitBodyHeightPercent;
    public int mWidth;

    public CustomScrollView(Context context) {
        super(context);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getChildCount() == 0) {
            Slog.e("CustomScrollView", "no children");
            return;
        }
        this.mWidth = View.MeasureSpec.getSize(i);
        if (this.mHeight == -1) {
            Point point = new Point();
            getContext().getDisplayNoVerify().getSize(point);
            int measuredHeight = getChildAt(0).getMeasuredHeight();
            int i3 = point.y;
            int i4 = getResources().getConfiguration().orientation == 2 ? (this.mMaxLandscapeBodyHeightPercent * i3) / 100 : (this.mMaxPortraitBodyHeightPercent * i3) / 100;
            this.mHeight = Math.min(measuredHeight, i4);
            if (Helper.sDebug) {
                StringBuilder sb = new StringBuilder("calculateDimensions(): mMaxPortraitBodyHeightPercent=");
                sb.append(this.mMaxPortraitBodyHeightPercent);
                sb.append(", mMaxLandscapeBodyHeightPercent=");
                sb.append(this.mMaxLandscapeBodyHeightPercent);
                sb.append(", mAttrBasedMaxHeightPercent=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(this.mAttrBasedMaxHeightPercent, i4, ", maxHeight=", ", contentHeight=", sb);
                sb.append(measuredHeight);
                sb.append(", w=");
                sb.append(this.mWidth);
                sb.append(", h=");
                DeviceIdleController$$ExternalSyntheticOutline0.m(sb, this.mHeight, "CustomScrollView");
            }
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public final void setMaxBodyHeightPercent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.^attr-private.autofillSaveCustomSubtitleMaxHeight, typedValue, true);
        int fraction = (int) typedValue.getFraction(100.0f, 100.0f);
        this.mAttrBasedMaxHeightPercent = fraction;
        this.mMaxPortraitBodyHeightPercent = DeviceConfig.getInt("autofill", "autofill_save_dialog_portrait_body_height_max_percent", fraction);
        this.mMaxLandscapeBodyHeightPercent = DeviceConfig.getInt("autofill", "autofill_save_dialog_landscape_body_height_max_percent", this.mAttrBasedMaxHeightPercent);
    }
}
