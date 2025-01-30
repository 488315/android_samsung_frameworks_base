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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        float dpToPx = DimensionUtilsKt.dpToPx(8.0f) / 2.0f;
        float centerY = getCenterY() - dpToPx;
        float centerY2 = getCenterY() + dpToPx;
        RectF rectF2 = this.leftCornerBounds;
        float f = rectF.left;
        rectF2.set(f, centerY, (dpToPx * 2) + f, centerY2);
        CustomPathInterpolator customPathInterpolator = this.leftTopCornerPath;
        customPathInterpolator.reset();
        customPathInterpolator.addArc(rectF2, 180.0f, 90.0f);
        customPathInterpolator.updatePath();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    @Override // com.android.systemui.media.audiovisseekbar.renderer.BaseRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onThumbLocationChanged(float f) {
        boolean z;
        super.onThumbLocationChanged(f);
        RectF rectF = this.bounds;
        int width = (int) rectF.width();
        if (width >= 0) {
            RendererConfig.INSTANCE.getClass();
            if (width < ((int) DimensionUtilsKt.dpToPx(8.0f))) {
                z = true;
                SingleStateValueAnimator singleStateValueAnimator = this.widthScale;
                if (z) {
                    RendererConfig.INSTANCE.getClass();
                    int dpToPx = (int) DimensionUtilsKt.dpToPx(8.0f);
                    View view = this.view;
                    int width2 = view.getWidth();
                    int i = this.cycleCount * 2;
                    if (width < width2 / i && dpToPx <= width) {
                        singleStateValueAnimator.animateTo(0.3f);
                    } else {
                        if (width <= view.getWidth() && view.getWidth() / i <= width) {
                            singleStateValueAnimator.animateTo(1.0f);
                        }
                    }
                } else {
                    singleStateValueAnimator.animateTo(0.1f);
                }
                CustomPathInterpolator customPathInterpolator = this.scalePath;
                customPathInterpolator.reset();
                RendererConfig.INSTANCE.getClass();
                customPathInterpolator.moveTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 0.0f);
                customPathInterpolator.quadTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 1.0f, rectF.width() / 2, 1.0f);
                customPathInterpolator.quadTo(rectF.width(), 1.0f, rectF.width(), 0.0f);
                customPathInterpolator.updatePath();
            }
        }
        z = false;
        SingleStateValueAnimator singleStateValueAnimator2 = this.widthScale;
        if (z) {
        }
        CustomPathInterpolator customPathInterpolator2 = this.scalePath;
        customPathInterpolator2.reset();
        RendererConfig.INSTANCE.getClass();
        customPathInterpolator2.moveTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 0.0f);
        customPathInterpolator2.quadTo(DimensionUtilsKt.dpToPx(8.0f) / 2.0f, 1.0f, rectF.width() / 2, 1.0f);
        customPathInterpolator2.quadTo(rectF.width(), 1.0f, rectF.width(), 0.0f);
        customPathInterpolator2.updatePath();
    }
}
