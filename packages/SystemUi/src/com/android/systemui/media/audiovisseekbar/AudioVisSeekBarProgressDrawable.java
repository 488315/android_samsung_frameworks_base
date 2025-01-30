package com.android.systemui.media.audiovisseekbar;

import android.animation.ArgbEvaluator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.SeekBar;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.config.RendererConfig;
import com.android.systemui.media.audiovisseekbar.renderer.TrackRendererFactory;
import com.android.systemui.media.audiovisseekbar.renderer.TrackRendererType;
import com.android.systemui.media.audiovisseekbar.renderer.track.RemainTrackLineRenderer;
import com.android.systemui.media.audiovisseekbar.renderer.track.auto.MultiWaveAreaTrackRenderer;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;
import com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator;
import com.android.systemui.media.audiovisseekbar.utils.easing.CustomPathInterpolator;
import com.android.systemui.media.audiovisseekbar.utils.easing.Interpolators;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AudioVisSeekBarProgressDrawable extends Drawable {
    public boolean active;
    public final AudioVisSeekBarConfig config;
    public final Lazy motionActivityAnimator$delegate;
    public final RectF remainTrackBounds;
    public final Lazy remainTrackRenderer$delegate;
    public final RectF trackBounds;
    public final MultiWaveAreaTrackRenderer trackRenderer;
    public final SeekBar view;

    public AudioVisSeekBarProgressDrawable(SeekBar seekBar) {
        this.view = seekBar;
        AudioVisSeekBarConfig audioVisSeekBarConfig = new AudioVisSeekBarConfig(0, 0, 0, 0, 0, 31, null);
        this.config = audioVisSeekBarConfig;
        TrackRendererType trackRendererType = TrackRendererType.WAVE_MULTI_AREA_AUTO;
        this.active = true;
        this.trackBounds = new RectF();
        this.remainTrackBounds = new RectF();
        this.remainTrackRenderer$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$remainTrackRenderer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = AudioVisSeekBarProgressDrawable.this;
                return new RemainTrackLineRenderer(audioVisSeekBarProgressDrawable.view, audioVisSeekBarProgressDrawable.config);
            }
        });
        TrackRendererFactory.INSTANCE.getClass();
        if (TrackRendererFactory.WhenMappings.$EnumSwitchMapping$0[trackRendererType.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        this.trackRenderer = new MultiWaveAreaTrackRenderer(seekBar, audioVisSeekBarConfig);
        this.motionActivityAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$motionActivityAnimator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Interpolators.INSTANCE.getClass();
                PathInterpolator pathInterpolator = Interpolators.MOTION_ACTIVITY_EASING;
                final AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = AudioVisSeekBarProgressDrawable.this;
                return new SingleStateValueAnimator(1.0f, 0L, pathInterpolator, new Function1() { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$motionActivityAnimator$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        float floatValue = ((Number) obj).floatValue();
                        MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = AudioVisSeekBarProgressDrawable.this.trackRenderer;
                        multiWaveAreaTrackRenderer.motionActivity = floatValue;
                        multiWaveAreaTrackRenderer.view.invalidate();
                        return Unit.INSTANCE;
                    }
                }, 2, null);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0248 A[LOOP:1: B:19:0x00ec->B:40:0x0248, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0256 A[EDGE_INSN: B:41:0x0256->B:42:0x0256 BREAK  A[LOOP:1: B:19:0x00ec->B:40:0x0248], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x023d  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        View view;
        int i;
        float f;
        float f2;
        float f3;
        float interpolation;
        double d;
        int i2;
        float f4;
        float m20m;
        RemainTrackLineRenderer remainTrackLineRenderer = (RemainTrackLineRenderer) this.remainTrackRenderer$delegate.getValue();
        RectF rectF = remainTrackLineRenderer.bounds;
        float f5 = rectF.left;
        float centerY = remainTrackLineRenderer.getCenterY();
        float f6 = rectF.right;
        RendererConfig rendererConfig = RendererConfig.INSTANCE;
        rendererConfig.getClass();
        canvas.drawLine(f5, centerY, f6 - RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.getCenterY(), remainTrackLineRenderer.trackPaint);
        canvas.drawRoundRect(rectF.left - RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.getCenterY() - RendererConfig.getRemainTrackBorderBound(), rectF.right, RendererConfig.getRemainTrackBorderBound() + remainTrackLineRenderer.getCenterY(), RendererConfig.getRemainTrackBorderBound(), RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.trackBorderPaint);
        MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = this.trackRenderer;
        float f7 = 0.0f;
        int i3 = 0;
        if (multiWaveAreaTrackRenderer.thumbX == 0.0f) {
            return;
        }
        View view2 = multiWaveAreaTrackRenderer.view;
        float width = view2.getWidth();
        rendererConfig.getClass();
        float dpToPx = width - (DimensionUtilsKt.dpToPx(8.0f) * 2.0f);
        float f8 = 2;
        float dpToPx2 = DimensionUtilsKt.dpToPx(8.0f) / f8;
        int i4 = 1;
        int i5 = 0;
        while (true) {
            int i6 = multiWaveAreaTrackRenderer.numWaves;
            if (i3 >= i6) {
                return;
            }
            Paint paint = multiWaveAreaTrackRenderer.pathPaint;
            ArgbEvaluator argbEvaluator = multiWaveAreaTrackRenderer.evaluator;
            float f9 = multiWaveAreaTrackRenderer.motionActivity;
            AudioVisSeekBarConfig audioVisSeekBarConfig = multiWaveAreaTrackRenderer.config;
            paint.setColor(((Integer) argbEvaluator.evaluate(f9, Integer.valueOf(audioVisSeekBarConfig.secondaryColor), Integer.valueOf(i3 == 0 ? audioVisSeekBarConfig.primaryColor : audioVisSeekBarConfig.secondaryColor))).intValue());
            paint.setAlpha(i3 == i4 ? 186 : 200);
            float height = ((((view2.getHeight() / 1.1f) - dpToPx2) / f8) - DimensionUtilsKt.dpToPx(3.0f)) * ((i6 - i3) / i6);
            Path path = multiWaveAreaTrackRenderer.path;
            path.reset();
            int roundToInt = MathKt__MathJVMKt.roundToInt(multiWaveAreaTrackRenderer.thumbX);
            int i7 = multiWaveAreaTrackRenderer.stepX;
            if (i7 <= 0) {
                throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Step must be positive, was: ", i7, "."));
            }
            int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i5, roundToInt, i7);
            RectF rectF2 = multiWaveAreaTrackRenderer.leftCornerBounds;
            if (progressionLastElement >= 0) {
                int i8 = 0;
                while (true) {
                    float f10 = i8;
                    float f11 = f7;
                    f2 = f8;
                    i = i3;
                    int i9 = progressionLastElement;
                    int i10 = i8;
                    double sin = Math.sin(((f10 / dpToPx) * 6.283185307179586d * multiWaveAreaTrackRenderer.cycleCount) + (multiWaveAreaTrackRenderer.phase * (i3 + 1)));
                    float width2 = f10 / multiWaveAreaTrackRenderer.bounds.width();
                    CustomPathInterpolator customPathInterpolator = multiWaveAreaTrackRenderer.scalePath;
                    f = dpToPx;
                    view = view2;
                    customPathInterpolator.pathMeasure.getPosTan(width2 * customPathInterpolator.pathLegth, customPathInterpolator.point, null);
                    double d2 = (((height * r1) * sin) - height) * customPathInterpolator.point[1] * multiWaveAreaTrackRenderer.motionActivity * multiWaveAreaTrackRenderer.widthScale.value * 1.1f;
                    float width3 = rectF2.width() * 0.5f;
                    if (f10 <= width3) {
                        d = d2 * 0.0f;
                        f3 = height;
                    } else {
                        if (f10 > width3) {
                            float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(roundToInt, width3, 0.5f, width3);
                            if (f10 <= m20m2) {
                                f3 = height;
                                interpolation = new PathInterpolator(0.22f, 0.25f, 0.63f, 1.0f).getInterpolation((f10 - width3) / m20m2);
                                d = d2 * interpolation;
                            }
                        }
                        f3 = height;
                        float f12 = f10 - width3;
                        float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(roundToInt, width3, 0.5f, width3);
                        interpolation = new PathInterpolator(0.25f, 0.25f, 0.63f, 1.0f).getInterpolation(1.0f - ((f12 - m20m3) / m20m3));
                        d = d2 * interpolation;
                    }
                    float centerY2 = (float) ((multiWaveAreaTrackRenderer.getCenterY() - dpToPx2) + d);
                    if (multiWaveAreaTrackRenderer.motionActivity < 1.0f) {
                        if (f10 <= rectF2.centerX() && rectF2.left <= f10) {
                            CustomPathInterpolator customPathInterpolator2 = multiWaveAreaTrackRenderer.leftTopCornerPath;
                            PointF[] pointFArr = customPathInterpolator2.samplingPoints;
                            int length = pointFArr.length - 1;
                            PointF pointF = pointFArr[0];
                            if (f10 > pointF.x) {
                                PointF pointF2 = pointFArr[length];
                                if (f10 < pointF2.x) {
                                    int i11 = 0;
                                    while (length - i11 > 1) {
                                        int i12 = (i11 + length) / 2;
                                        if (f10 < customPathInterpolator2.samplingPoints[i12].x) {
                                            length = i12;
                                        } else {
                                            i11 = i12;
                                        }
                                    }
                                    PointF[] pointFArr2 = customPathInterpolator2.samplingPoints;
                                    PointF pointF3 = pointFArr2[length];
                                    float f13 = pointF3.x;
                                    PointF pointF4 = pointFArr2[i11];
                                    float f14 = pointF4.x;
                                    float f15 = f13 - f14;
                                    if (f15 == 0.0f) {
                                        m20m = pointF4.y;
                                    } else {
                                        float f16 = pointF4.y;
                                        m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF3.y, f16, (f10 - f14) / f15, f16);
                                    }
                                    i2 = 1;
                                    centerY2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(centerY2, m20m, multiWaveAreaTrackRenderer.motionActivity, m20m);
                                    f4 = 0;
                                    if (f10 <= (rectF2.width() * 0.5f) + f4) {
                                        path.moveTo((rectF2.width() * 0.5f) + f4, centerY2);
                                        f7 = centerY2;
                                    } else {
                                        path.lineTo(f10, centerY2);
                                        f7 = f11;
                                    }
                                    if (i10 == i9) {
                                        break;
                                    }
                                    i8 = i10 + i7;
                                    progressionLastElement = i9;
                                    f8 = f2;
                                    dpToPx = f;
                                    i3 = i;
                                    view2 = view;
                                    height = f3;
                                } else {
                                    m20m = pointF2.y;
                                }
                            } else {
                                m20m = pointF.y;
                            }
                            i2 = 1;
                            centerY2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(centerY2, m20m, multiWaveAreaTrackRenderer.motionActivity, m20m);
                            f4 = 0;
                            if (f10 <= (rectF2.width() * 0.5f) + f4) {
                            }
                            if (i10 == i9) {
                            }
                        }
                    }
                    i2 = 1;
                    f4 = 0;
                    if (f10 <= (rectF2.width() * 0.5f) + f4) {
                    }
                    if (i10 == i9) {
                    }
                }
                i4 = i2;
            } else {
                view = view2;
                i = i3;
                f = dpToPx;
                f2 = f8;
                f7 = 0.0f;
            }
            i5 = 0;
            path.lineTo(roundToInt, multiWaveAreaTrackRenderer.getCenterY() + dpToPx2);
            path.lineTo(0 + dpToPx2, multiWaveAreaTrackRenderer.getCenterY() + dpToPx2);
            path.addArc(rectF2, 90.0f, 180.0f);
            path.lineTo((rectF2.width() * 0.5f) + rectF2.left, f7);
            path.close();
            canvas.drawPath(path, paint);
            multiWaveAreaTrackRenderer.phase += multiWaveAreaTrackRenderer.phaseShift;
            if ((multiWaveAreaTrackRenderer.motionActivity == 0.0f ? i4 : 0) == 0) {
                view.invalidate();
            }
            f8 = f2;
            dpToPx = f;
            view2 = view;
            i3 = i + 1;
            f7 = 0.0f;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return 255;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final float getThumbX() {
        return (getLevel() / 10000.0f) * getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        super.invalidateSelf();
        onBoundsChange(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.trackBounds.set(0.0f, 0.0f, getThumbX(), this.view.getHeight());
        this.remainTrackBounds.set(getThumbX(), 0.0f, rect.width(), this.view.getHeight());
        ((RemainTrackLineRenderer) this.remainTrackRenderer$delegate.getValue()).onLayout(this.remainTrackBounds);
        this.trackRenderer.onLayout(this.trackBounds);
        this.trackRenderer.onThumbLocationChanged(getThumbX());
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        ((RemainTrackLineRenderer) this.remainTrackRenderer$delegate.getValue()).onThumbLocationChanged(getThumbX());
        this.trackRenderer.onThumbLocationChanged(getThumbX());
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
    }
}
