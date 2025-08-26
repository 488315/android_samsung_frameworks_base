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
import android.view.animation.PathInterpolator;
import android.widget.SeekBar;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
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

/* loaded from: classes2.dex */
public final class AudioVisSeekBarProgressDrawable extends Drawable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean active;
    public final AudioVisSeekBarConfig config;
    public boolean listening;
    public final Lazy motionActivityAnimator$delegate;
    public final RectF remainTrackBounds;
    public final Lazy remainTrackRenderer$delegate;
    public final RectF trackBounds;
    public final MultiWaveAreaTrackRenderer trackRenderer;
    public final SeekBar view;

    public AudioVisSeekBarProgressDrawable(SeekBar seekBar) {
        this.view = seekBar;
        final int i = 0;
        AudioVisSeekBarConfig audioVisSeekBarConfig = new AudioVisSeekBarConfig(0, 0, 0, 0, 0, 31, null);
        this.config = audioVisSeekBarConfig;
        TrackRendererType trackRendererType = TrackRendererType.WAVE_MULTI_AREA_AUTO;
        this.active = true;
        this.trackBounds = new RectF();
        this.remainTrackBounds = new RectF();
        this.remainTrackRenderer$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioVisSeekBarProgressDrawable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = this.f$0;
                switch (i) {
                    case 0:
                        return new RemainTrackLineRenderer(audioVisSeekBarProgressDrawable.view, audioVisSeekBarProgressDrawable.config);
                    default:
                        int i2 = AudioVisSeekBarProgressDrawable.$r8$clinit;
                        Interpolators.INSTANCE.getClass();
                        return new SingleStateValueAnimator(1.0f, 0L, Interpolators.MOTION_ACTIVITY_EASING, new Function1() { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                float fFloatValue = ((Float) obj).floatValue();
                                MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = audioVisSeekBarProgressDrawable.trackRenderer;
                                multiWaveAreaTrackRenderer.motionActivity = fFloatValue;
                                multiWaveAreaTrackRenderer.view.invalidate();
                                return Unit.INSTANCE;
                            }
                        }, 2, null);
                }
            }
        });
        TrackRendererFactory.INSTANCE.getClass();
        if (TrackRendererFactory.WhenMappings.$EnumSwitchMapping$0[trackRendererType.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        this.trackRenderer = new MultiWaveAreaTrackRenderer(seekBar, audioVisSeekBarConfig);
        final int i2 = 1;
        this.motionActivityAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioVisSeekBarProgressDrawable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = this.f$0;
                switch (i2) {
                    case 0:
                        return new RemainTrackLineRenderer(audioVisSeekBarProgressDrawable.view, audioVisSeekBarProgressDrawable.config);
                    default:
                        int i22 = AudioVisSeekBarProgressDrawable.$r8$clinit;
                        Interpolators.INSTANCE.getClass();
                        return new SingleStateValueAnimator(1.0f, 0L, Interpolators.MOTION_ACTIVITY_EASING, new Function1() { // from class: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                float fFloatValue = ((Float) obj).floatValue();
                                MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = audioVisSeekBarProgressDrawable.trackRenderer;
                                multiWaveAreaTrackRenderer.motionActivity = fFloatValue;
                                multiWaveAreaTrackRenderer.view.invalidate();
                                return Unit.INSTANCE;
                            }
                        }, 2, null);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x021f  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        float f;
        float f2;
        char c;
        float f3;
        float interpolation;
        double d;
        int i;
        float fM$1;
        RemainTrackLineRenderer remainTrackLineRenderer = (RemainTrackLineRenderer) this.remainTrackRenderer$delegate.getValue();
        float f4 = remainTrackLineRenderer.bounds.left;
        float centerY = remainTrackLineRenderer.getCenterY();
        float f5 = remainTrackLineRenderer.bounds.right;
        RendererConfig rendererConfig = RendererConfig.INSTANCE;
        rendererConfig.getClass();
        canvas.drawLine(f4, centerY, f5 - RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.getCenterY(), remainTrackLineRenderer.trackPaint);
        canvas.drawRoundRect(remainTrackLineRenderer.bounds.left - RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.getCenterY() - RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.bounds.right, RendererConfig.getRemainTrackBorderBound() + remainTrackLineRenderer.getCenterY(), RendererConfig.getRemainTrackBorderBound(), RendererConfig.getRemainTrackBorderBound(), remainTrackLineRenderer.trackBorderPaint);
        MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = this.trackRenderer;
        float f6 = 0.0f;
        if (multiWaveAreaTrackRenderer.thumbX == 0.0f) {
            return;
        }
        float width = multiWaveAreaTrackRenderer.view.getWidth();
        rendererConfig.getClass();
        float fDpToPx = width - (DimensionUtilsKt.dpToPx(8.0f) * 2.0f);
        char c2 = 2;
        float f7 = 2;
        float fDpToPx2 = DimensionUtilsKt.dpToPx(8.0f) / f7;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = multiWaveAreaTrackRenderer.numWaves;
            if (i3 >= i4) {
                return;
            }
            Paint paint = multiWaveAreaTrackRenderer.pathPaint;
            ArgbEvaluator argbEvaluator = multiWaveAreaTrackRenderer.evaluator;
            float f8 = multiWaveAreaTrackRenderer.motionActivity;
            AudioVisSeekBarConfig audioVisSeekBarConfig = multiWaveAreaTrackRenderer.config;
            paint.setColor(((Integer) argbEvaluator.evaluate(f8, Integer.valueOf(audioVisSeekBarConfig.secondaryColor), Integer.valueOf(i3 == 0 ? audioVisSeekBarConfig.primaryColor : audioVisSeekBarConfig.secondaryColor))).intValue());
            int i5 = 1;
            paint.setAlpha(i3 == 1 ? 186 : 200);
            float height = ((((multiWaveAreaTrackRenderer.view.getHeight() / 1.1f) - fDpToPx2) / f7) - DimensionUtilsKt.dpToPx(3.0f)) * ((i4 - i3) / i4);
            multiWaveAreaTrackRenderer.path.reset();
            int iRoundToInt = MathKt__MathJVMKt.roundToInt(multiWaveAreaTrackRenderer.thumbX);
            int i6 = multiWaveAreaTrackRenderer.stepX;
            if (i6 <= 0) {
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i6, "Step must be positive, was: ", "."));
            }
            int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(i2, iRoundToInt, i6);
            if (progressionLastElement >= 0) {
                float f9 = f6;
                c = c2;
                int i7 = i2;
                while (true) {
                    float f10 = i7;
                    int i8 = i2;
                    int i9 = i5;
                    int i10 = i7;
                    f2 = fDpToPx2;
                    double dSin = Math.sin(((f10 / fDpToPx) * 6.283185307179586d * multiWaveAreaTrackRenderer.cycleCount) + (multiWaveAreaTrackRenderer.phase * (i3 + 1)));
                    float fWidth = f10 / multiWaveAreaTrackRenderer.bounds.width();
                    CustomPathInterpolator customPathInterpolator = multiWaveAreaTrackRenderer.scalePath;
                    customPathInterpolator.pathMeasure.getPosTan(fWidth * customPathInterpolator.pathLegth, customPathInterpolator.point, null);
                    double d2 = (((height * r6) * dSin) - height) * customPathInterpolator.point[i9] * multiWaveAreaTrackRenderer.motionActivity * multiWaveAreaTrackRenderer.widthScale.value * 1.1f;
                    float fWidth2 = multiWaveAreaTrackRenderer.leftCornerBounds.width() * 0.5f;
                    if (f10 <= fWidth2) {
                        d = d2 * 0.0f;
                        f = fDpToPx;
                    } else if (f10 > fWidth2) {
                        float fM$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(iRoundToInt, fWidth2, 0.5f, fWidth2);
                        if (f10 <= fM$12) {
                            f = fDpToPx;
                            interpolation = new PathInterpolator(0.22f, 0.25f, 0.63f, 1.0f).getInterpolation((f10 - fWidth2) / fM$12);
                        } else {
                            f = fDpToPx;
                            float fM$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(iRoundToInt, fWidth2, 0.5f, fWidth2);
                            interpolation = new PathInterpolator(0.25f, 0.25f, 0.63f, 1.0f).getInterpolation(1.0f - (((f10 - fWidth2) - fM$13) / fM$13));
                        }
                        d = d2 * interpolation;
                    }
                    float centerY2 = (float) ((multiWaveAreaTrackRenderer.getCenterY() - f2) + d);
                    if (multiWaveAreaTrackRenderer.motionActivity < 1.0f) {
                        RectF rectF = multiWaveAreaTrackRenderer.leftCornerBounds;
                        float f11 = rectF.left;
                        if (f10 > rectF.centerX() || f11 > f10) {
                            i = i9;
                        } else {
                            CustomPathInterpolator customPathInterpolator2 = multiWaveAreaTrackRenderer.leftTopCornerPath;
                            PointF[] pointFArr = customPathInterpolator2.samplingPoints;
                            int length = pointFArr.length - 1;
                            PointF pointF = pointFArr[i8];
                            if (f10 <= pointF.x) {
                                fM$1 = pointF.y;
                            } else {
                                PointF pointF2 = pointFArr[length];
                                if (f10 >= pointF2.x) {
                                    fM$1 = pointF2.y;
                                } else {
                                    int i11 = i8;
                                    while (true) {
                                        i = i9;
                                        if (length - i11 <= i) {
                                            break;
                                        }
                                        int i12 = (i11 + length) / 2;
                                        if (f10 < customPathInterpolator2.samplingPoints[i12].x) {
                                            length = i12;
                                        } else {
                                            i11 = i12;
                                        }
                                        i9 = i;
                                    }
                                    PointF[] pointFArr2 = customPathInterpolator2.samplingPoints;
                                    PointF pointF3 = pointFArr2[length];
                                    float f12 = pointF3.x;
                                    PointF pointF4 = pointFArr2[i11];
                                    float f13 = pointF4.x;
                                    float f14 = f12 - f13;
                                    if (f14 == 0.0f) {
                                        fM$1 = pointF4.y;
                                    } else {
                                        float f15 = pointF4.y;
                                        fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF3.y, f15, (f10 - f13) / f14, f15);
                                    }
                                    centerY2 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(centerY2, fM$1, multiWaveAreaTrackRenderer.motionActivity, fM$1);
                                }
                            }
                            i = i9;
                            centerY2 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(centerY2, fM$1, multiWaveAreaTrackRenderer.motionActivity, fM$1);
                        }
                        float f16 = i8;
                        if (f10 <= (multiWaveAreaTrackRenderer.leftCornerBounds.width() * 0.5f) + f16) {
                            multiWaveAreaTrackRenderer.path.moveTo((multiWaveAreaTrackRenderer.leftCornerBounds.width() * 0.5f) + f16, centerY2);
                            f9 = centerY2;
                        } else {
                            multiWaveAreaTrackRenderer.path.lineTo(f10, centerY2);
                        }
                        if (i10 == progressionLastElement) {
                            break;
                        }
                        i7 = i10 + i6;
                        i5 = i;
                        fDpToPx2 = f2;
                        fDpToPx = f;
                        i2 = 0;
                    }
                }
                f3 = f9;
            } else {
                f = fDpToPx;
                f2 = fDpToPx2;
                c = c2;
                f3 = 0.0f;
            }
            Path path = multiWaveAreaTrackRenderer.path;
            path.lineTo(iRoundToInt, multiWaveAreaTrackRenderer.getCenterY() + f2);
            path.lineTo(0 + f2, multiWaveAreaTrackRenderer.getCenterY() + f2);
            path.addArc(multiWaveAreaTrackRenderer.leftCornerBounds, 90.0f, 180.0f);
            RectF rectF2 = multiWaveAreaTrackRenderer.leftCornerBounds;
            path.lineTo((rectF2.width() * 0.5f) + rectF2.left, f3);
            path.close();
            canvas.drawPath(multiWaveAreaTrackRenderer.path, multiWaveAreaTrackRenderer.pathPaint);
            multiWaveAreaTrackRenderer.phase += multiWaveAreaTrackRenderer.phaseShift;
            if (multiWaveAreaTrackRenderer.motionActivity != 0.0f) {
                multiWaveAreaTrackRenderer.view.invalidate();
            }
            i3++;
            c2 = c;
            i2 = 0;
            f6 = 0.0f;
            fDpToPx2 = f2;
            fDpToPx = f;
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
