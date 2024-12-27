package com.android.systemui.media.audiovisseekbar;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.PathInterpolator;
import android.widget.SeekBar;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.renderer.TrackRendererFactory;
import com.android.systemui.media.audiovisseekbar.renderer.TrackRendererType;
import com.android.systemui.media.audiovisseekbar.renderer.track.RemainTrackLineRenderer;
import com.android.systemui.media.audiovisseekbar.renderer.track.auto.MultiWaveAreaTrackRenderer;
import com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator;
import com.android.systemui.media.audiovisseekbar.utils.easing.Interpolators;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public final class AudioVisSeekBarProgressDrawable extends Drawable {
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

    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r24) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable.draw(android.graphics.Canvas):void");
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
