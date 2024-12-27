package com.android.systemui.media.audiovisseekbar.renderer.track;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.config.RendererConfig;
import com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RemainTrackLineRenderer extends BaseRenderer {
    public final Paint trackBorderPaint;
    public final Paint trackPaint;

    public RemainTrackLineRenderer(View view, AudioVisSeekBarConfig audioVisSeekBarConfig) {
        super(view, audioVisSeekBarConfig);
        Paint paint = new Paint();
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        paint.setColor(audioVisSeekBarConfig.remainTrackColor);
        RendererConfig rendererConfig = RendererConfig.INSTANCE;
        rendererConfig.getClass();
        paint.setStrokeWidth(DimensionUtilsKt.dpToPx(8.0f));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(50.0f));
        paint.setAntiAlias(true);
        this.trackPaint = paint;
        Paint paint2 = new Paint();
        paint2.setStyle(style);
        paint2.setColor(audioVisSeekBarConfig.remainTrackBorderColor);
        rendererConfig.getClass();
        paint2.setStrokeWidth(DimensionUtilsKt.dpToPx(1.0f));
        paint2.setAntiAlias(true);
        this.trackBorderPaint = paint2;
    }

    @Override // com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer
    public final void onThumbLocationChanged(float f) {
        RectF rectF = this.bounds;
        RendererConfig.INSTANCE.getClass();
        rectF.left = RangesKt___RangesKt.coerceAtMost(DimensionUtilsKt.dpToPx(8.0f) + f, this.bounds.right - RendererConfig.getRemainTrackBorderBound());
    }
}
