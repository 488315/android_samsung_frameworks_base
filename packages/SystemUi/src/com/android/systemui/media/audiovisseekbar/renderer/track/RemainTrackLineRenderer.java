package com.android.systemui.media.audiovisseekbar.renderer.track;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.config.RendererConfig;
import com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RemainTrackLineRenderer extends BaseRenderer {
    public final Paint trackBorderPaint;
    public final Paint trackPaint;

    public RemainTrackLineRenderer(View view, AudioVisSeekBarConfig audioVisSeekBarConfig) {
        super(view, audioVisSeekBarConfig);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(audioVisSeekBarConfig.remainTrackColor);
        RendererConfig.INSTANCE.getClass();
        paint.setStrokeWidth(DimensionUtilsKt.dpToPx(8.0f));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(50.0f));
        paint.setAntiAlias(true);
        this.trackPaint = paint;
        Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(audioVisSeekBarConfig.remainTrackBorderColor);
        paint2.setStrokeWidth(DimensionUtilsKt.dpToPx(1.0f));
        paint2.setAntiAlias(true);
        this.trackBorderPaint = paint2;
    }

    @Override // com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer
    public final void onThumbLocationChanged(float f) {
        RectF rectF = this.bounds;
        RendererConfig.INSTANCE.getClass();
        float dpToPx = DimensionUtilsKt.dpToPx(8.0f) + f;
        float remainTrackBorderBound = rectF.right - RendererConfig.getRemainTrackBorderBound();
        if (dpToPx > remainTrackBorderBound) {
            dpToPx = remainTrackBorderBound;
        }
        rectF.left = dpToPx;
    }
}
