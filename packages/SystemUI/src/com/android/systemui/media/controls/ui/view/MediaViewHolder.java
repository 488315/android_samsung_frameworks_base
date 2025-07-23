package com.android.systemui.media.controls.ui.view;

import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.R;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set backgroundIds;
    public static final Set controlsIds;
    public static final Set detailIds;
    public static final Set expandedBottomActionIds;
    public static final Set genericButtonIds;
    public final ImageButton action0;
    public final ImageButton action1;
    public final ImageButton action2;
    public final ImageButton action3;
    public final ImageButton action4;
    public final ImageButton actionNext;
    public final ImageButton actionPlayPause;
    public final ImageButton actionPrev;
    public final ImageView albumView;
    public final ImageView appIcon;
    public final TextView artistText;
    public final CachingIconView explicitIndicator;
    public final GutsViewHolder gutsViewHolder;
    public final LoadingEffectView loadingEffectView;
    public final MultiRippleView multiRippleView;
    public final TransitionLayout player;
    public final TextView scrubbingElapsedTimeView;
    public final TextView scrubbingTotalTimeView;
    public final ViewGroup seamless;
    public final View seamlessButton;
    public final ImageView seamlessIcon;
    public final TextView seamlessText;
    public final SeekBar seekBar;
    public final TextView titleText;
    public final TurbulenceNoiseView turbulenceNoiseView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Integer valueOf = Integer.valueOf(R.id.icon);
        Integer valueOf2 = Integer.valueOf(R.id.app_name);
        Integer valueOf3 = Integer.valueOf(R.id.header_title);
        Integer valueOf4 = Integer.valueOf(R.id.header_artist);
        Integer valueOf5 = Integer.valueOf(R.id.media_explicit_indicator);
        Integer valueOf6 = Integer.valueOf(R.id.media_seamless);
        Integer valueOf7 = Integer.valueOf(R.id.media_progress_bar);
        Integer valueOf8 = Integer.valueOf(R.id.actionPlayPause);
        Integer valueOf9 = Integer.valueOf(R.id.actionNext);
        Integer valueOf10 = Integer.valueOf(R.id.actionPrev);
        Integer valueOf11 = Integer.valueOf(R.id.action0);
        Integer valueOf12 = Integer.valueOf(R.id.action1);
        Integer valueOf13 = Integer.valueOf(R.id.action2);
        Integer valueOf14 = Integer.valueOf(R.id.action3);
        Integer valueOf15 = Integer.valueOf(R.id.action4);
        Integer valueOf16 = Integer.valueOf(R.id.media_scrubbing_elapsed_time);
        Integer valueOf17 = Integer.valueOf(R.id.media_scrubbing_total_time);
        controlsIds = ArraysKt___ArraysKt.toSet(new Integer[]{valueOf, valueOf2, valueOf3, valueOf4, valueOf5, valueOf6, valueOf7, valueOf8, valueOf9, valueOf10, valueOf11, valueOf12, valueOf13, valueOf14, valueOf15, valueOf, valueOf16, valueOf17});
        genericButtonIds = ArraysKt___ArraysKt.toSet(new Integer[]{valueOf11, valueOf12, valueOf13, valueOf14, valueOf15});
        expandedBottomActionIds = ArraysKt___ArraysKt.toSet(new Integer[]{valueOf7, valueOf10, valueOf9, valueOf11, valueOf12, valueOf13, valueOf14, valueOf15, valueOf16, valueOf17});
        detailIds = ArraysKt___ArraysKt.toSet(new Integer[]{valueOf3, valueOf4, valueOf5, valueOf8});
        backgroundIds = ArraysKt___ArraysKt.toSet(new Integer[]{Integer.valueOf(R.id.album_art), Integer.valueOf(R.id.turbulence_noise_view), Integer.valueOf(R.id.loading_effect_view), Integer.valueOf(R.id.touch_ripple_view)});
        Typeface.create("variable-headline-small", 0);
        Typeface.create("variable-title-medium", 0);
        Typeface.create("variable-label-medium", 0);
        Typeface.create("variable-label-large", 0);
    }

    public MediaViewHolder(View view) {
        this.player = (TransitionLayout) view;
        this.albumView = (ImageView) view.requireViewById(R.id.album_art);
        this.multiRippleView = (MultiRippleView) view.requireViewById(R.id.touch_ripple_view);
        this.turbulenceNoiseView = (TurbulenceNoiseView) view.requireViewById(R.id.turbulence_noise_view);
        this.loadingEffectView = (LoadingEffectView) view.requireViewById(R.id.loading_effect_view);
        this.appIcon = (ImageView) view.requireViewById(R.id.icon);
        this.titleText = (TextView) view.requireViewById(R.id.header_title);
        this.artistText = (TextView) view.requireViewById(R.id.header_artist);
        this.explicitIndicator = view.requireViewById(R.id.media_explicit_indicator);
        this.seamless = (ViewGroup) view.requireViewById(R.id.media_seamless);
        this.seamlessIcon = (ImageView) view.requireViewById(R.id.media_seamless_image);
        this.seamlessText = (TextView) view.requireViewById(R.id.media_seamless_text);
        this.seamlessButton = view.requireViewById(R.id.media_seamless_button);
        this.seekBar = (SeekBar) view.requireViewById(R.id.media_progress_bar);
        this.scrubbingElapsedTimeView = (TextView) view.requireViewById(R.id.media_scrubbing_elapsed_time);
        this.scrubbingTotalTimeView = (TextView) view.requireViewById(R.id.media_scrubbing_total_time);
        this.gutsViewHolder = new GutsViewHolder(view);
        this.actionPlayPause = (ImageButton) view.requireViewById(R.id.actionPlayPause);
        this.actionNext = (ImageButton) view.requireViewById(R.id.actionNext);
        this.actionPrev = (ImageButton) view.requireViewById(R.id.actionPrev);
        this.action0 = (ImageButton) view.requireViewById(R.id.action0);
        this.action1 = (ImageButton) view.requireViewById(R.id.action1);
        this.action2 = (ImageButton) view.requireViewById(R.id.action2);
        this.action3 = (ImageButton) view.requireViewById(R.id.action3);
        this.action4 = (ImageButton) view.requireViewById(R.id.action4);
    }

    public final ImageButton getAction(int i) {
        if (i == R.id.actionPlayPause) {
            return this.actionPlayPause;
        }
        if (i == R.id.actionNext) {
            return this.actionNext;
        }
        if (i == R.id.actionPrev) {
            return this.actionPrev;
        }
        if (i == R.id.action0) {
            return this.action0;
        }
        if (i == R.id.action1) {
            return this.action1;
        }
        if (i == R.id.action2) {
            return this.action2;
        }
        if (i == R.id.action3) {
            return this.action3;
        }
        if (i == R.id.action4) {
            return this.action4;
        }
        throw new IllegalArgumentException();
    }

    public final void marquee(long j, final boolean z) {
        final GutsViewHolder gutsViewHolder = this.gutsViewHolder;
        Handler handler = gutsViewHolder.gutsText.getHandler();
        if (handler == null) {
            Log.d("MediaViewHolder", "marquee while longPressText.getHandler() is null", new Exception());
        } else {
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.GutsViewHolder$marquee$1
                @Override // java.lang.Runnable
                public final void run() {
                    GutsViewHolder.this.gutsText.setSelected(z);
                }
            }, j);
        }
    }
}
