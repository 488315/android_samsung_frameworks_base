package com.android.systemui.media.audiovisseekbar.renderer.track.auto;

import android.animation.ArgbEvaluator;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.config.RendererConfig;
import com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;
import com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator;
import com.android.systemui.media.audiovisseekbar.utils.easing.CustomPathInterpolator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MultiWaveAreaTrackRenderer extends BaseRenderer {
    public final int cycleCount;
    public final ArgbEvaluator evaluator;
    public final RectF leftCornerBounds;
    public final CustomPathInterpolator leftTopCornerPath;
    public final int numWaves;
    public final Path path;
    public final Paint pathPaint;
    public float phase;
    public final float phaseShift;
    public final CustomPathInterpolator scalePath;
    public final int stepX;
    public final SingleStateValueAnimator widthScale;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MultiWaveAreaTrackRenderer(View view, AudioVisSeekBarConfig audioVisSeekBarConfig) {
        super(view, audioVisSeekBarConfig);
        this.numWaves = 2;
        this.cycleCount = 3;
        this.phaseShift = -0.025f;
        this.stepX = 2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        this.pathPaint = paint;
        this.path = new Path();
        this.evaluator = new ArgbEvaluator();
        this.scalePath = new CustomPathInterpolator();
        this.widthScale = new SingleStateValueAnimator(0.0f, 200L, null, null, 13, null);
        this.leftTopCornerPath = new CustomPathInterpolator();
        this.leftCornerBounds = new RectF();
    }

    @Override // com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer
    public final void onLayout(RectF rectF) {
        super.onLayout(rectF);
        RendererConfig.INSTANCE.getClass();
        float fDpToPx = DimensionUtilsKt.dpToPx(8.0f) / 2.0f;
        float centerY = getCenterY() - fDpToPx;
        float centerY2 = getCenterY() + fDpToPx;
        RectF rectF2 = this.leftCornerBounds;
        float f = rectF.left;
        rectF2.set(f, centerY, (fDpToPx * 2) + f, centerY2);
        CustomPathInterpolator customPathInterpolator = this.leftTopCornerPath;
        customPathInterpolator.reset();
        customPathInterpolator.addArc(this.leftCornerBounds, 180.0f, 90.0f);
        customPathInterpolator.updatePath();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0026  */
    @Override // com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onThumbLocationChanged(float f) {
        super.onThumbLocationChanged(f);
        int iWidth = (int) this.bounds.width();
        SingleStateValueAnimator singleStateValueAnimator = this.widthScale;
        if (iWidth >= 0) {
            RendererConfig.INSTANCE.getClass();
            if (iWidth < ((int) DimensionUtilsKt.dpToPx(8.0f))) {
                singleStateValueAnimator.animateTo(0.1f);
            } else {
                RendererConfig.INSTANCE.getClass();
                int iDpToPx = (int) DimensionUtilsKt.dpToPx(8.0f);
                int width = this.view.getWidth();
                int i = this.cycleCount * 2;
                if (iWidth >= width / i || iDpToPx > iWidth) {
                    int width2 = this.view.getWidth() / i;
                    if (iWidth <= this.view.getWidth() && width2 <= iWidth) {
                        singleStateValueAnimator.animateTo(1.0f);
                    }
                } else {
                    singleStateValueAnimator.animateTo(0.3f);
                }
            }
        }
        CustomPathInterpolator customPathInterpolator = this.scalePath;
        customPathInterpolator.reset();
        RendererConfig.INSTANCE.getClass();
        customPathInterpolator.moveTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 0.0f);
        customPathInterpolator.quadTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 1.0f, this.bounds.width() / 2, 1.0f);
        customPathInterpolator.quadTo(this.bounds.width(), 1.0f, this.bounds.width(), 0.0f);
        customPathInterpolator.updatePath();
    }
}
