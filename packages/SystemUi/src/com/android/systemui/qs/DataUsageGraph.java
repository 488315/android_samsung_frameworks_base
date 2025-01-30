package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.settingslib.Utils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DataUsageGraph extends View {
    public final int mMarkerWidth;
    public final Paint mTmpPaint;
    public final RectF mTmpRect;
    public final int mTrackColor;
    public final int mUsageColor;
    public final int mWarningColor;

    public DataUsageGraph(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpRect = new RectF();
        this.mTmpPaint = new Paint();
        Resources resources = context.getResources();
        this.mTrackColor = Utils.getColorStateListDefaultColor(R.color.data_usage_graph_track, context);
        this.mWarningColor = Utils.getColorStateListDefaultColor(R.color.data_usage_graph_warning, context);
        this.mUsageColor = Utils.getColorAttrDefaultColor(android.R.attr.colorAccent, context, 0);
        Utils.getColorAttrDefaultColor(android.R.attr.colorError, context, 0);
        this.mMarkerWidth = resources.getDimensionPixelSize(R.dimen.data_usage_graph_marker_width);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.mTmpRect;
        Paint paint = this.mTmpPaint;
        int width = getWidth();
        int height = getHeight();
        float f = width;
        float f2 = 0L;
        float f3 = (f2 / f2) * f;
        rectF.set(0.0f, 0.0f, f, height);
        paint.setColor(this.mTrackColor);
        canvas.drawRect(rectF, paint);
        float f4 = height;
        rectF.set(0.0f, 0.0f, f3, f4);
        paint.setColor(this.mUsageColor);
        canvas.drawRect(rectF, paint);
        float min = Math.min(Math.max(f3 - (this.mMarkerWidth / 2), 0.0f), width - this.mMarkerWidth);
        rectF.set(min, 0.0f, this.mMarkerWidth + min, f4);
        paint.setColor(this.mWarningColor);
        canvas.drawRect(rectF, paint);
    }
}
