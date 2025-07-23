package com.android.systemui.media.audiovisseekbar;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            public final Object mo779invoke(Object obj) {
                                float floatValue = ((Float) obj).floatValue();
                                MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = AudioVisSeekBarProgressDrawable.this.trackRenderer;
                                multiWaveAreaTrackRenderer.motionActivity = floatValue;
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
                            public final Object mo779invoke(Object obj) {
                                float floatValue = ((Float) obj).floatValue();
                                MultiWaveAreaTrackRenderer multiWaveAreaTrackRenderer = AudioVisSeekBarProgressDrawable.this.trackRenderer;
                                multiWaveAreaTrackRenderer.motionActivity = floatValue;
                                multiWaveAreaTrackRenderer.view.invalidate();
                                return Unit.INSTANCE;
                            }
                        }, 2, null);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x024d A[LOOP:1: B:17:0x00f0->B:37:0x024d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x025b A[EDGE_INSN: B:38:0x025b->B:39:0x025b BREAK  A[LOOP:1: B:17:0x00f0->B:37:0x024d], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0245  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r25) {
        /*
            Method dump skipped, instructions count: 724
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
