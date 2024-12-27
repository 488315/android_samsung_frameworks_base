package com.android.systemui.media.controls.ui.binder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceStateLogger;
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.drawable.SquigglyProgress;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SeekBarObserver implements Observer {
    public static final int RESET_ANIMATION_DURATION_MS;
    public static final int RESET_ANIMATION_THRESHOLD_MS;
    public final MediaViewHolder holder;
    public final int seekBarDisabledHeight;
    public final int seekBarDisabledVerticalPadding;
    public final int seekBarEnabledMaxHeight;
    public final int seekBarEnabledVerticalPadding;
    public Animator seekBarResetAnimator;
    public final TraceStateLogger playingStateLogger = new TraceStateLogger("SeekBarObserver#playing", false, false, false, 14, null);
    public final TraceStateLogger listeningStateLogger = new TraceStateLogger("SeekBarObserver#listening", false, false, false, 14, null);
    public boolean animationEnabled = true;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        RESET_ANIMATION_DURATION_MS = 750;
        RESET_ANIMATION_THRESHOLD_MS = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
    }

    public SeekBarObserver(MediaViewHolder mediaViewHolder) {
        this.holder = mediaViewHolder;
        this.seekBarEnabledMaxHeight = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_enabled_seekbar_height);
        this.seekBarDisabledHeight = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_disabled_seekbar_height);
        this.seekBarEnabledVerticalPadding = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_session_enabled_seekbar_vertical_padding);
        this.seekBarDisabledVerticalPadding = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_session_disabled_seekbar_vertical_padding);
        float dimensionPixelSize = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_seekbar_progress_wavelength);
        float dimensionPixelSize2 = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_seekbar_progress_amplitude);
        float dimensionPixelSize3 = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_seekbar_progress_phase);
        float dimensionPixelSize4 = mediaViewHolder.seekBar.getContext().getResources().getDimensionPixelSize(R.dimen.qs_media_seekbar_progress_stroke_width);
        Drawable progressDrawable = mediaViewHolder.seekBar.getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (squigglyProgress != null) {
            squigglyProgress.waveLength = dimensionPixelSize;
            squigglyProgress.lineAmplitude = dimensionPixelSize2;
            squigglyProgress.phaseSpeed = dimensionPixelSize3;
            if (squigglyProgress.strokeWidth == dimensionPixelSize4) {
                return;
            }
            squigglyProgress.strokeWidth = dimensionPixelSize4;
            squigglyProgress.wavePaint.setStrokeWidth(dimensionPixelSize4);
            squigglyProgress.linePaint.setStrokeWidth(dimensionPixelSize4);
        }
    }

    public Animator buildResetAnimator(int i) {
        SeekBar seekBar = this.holder.seekBar;
        int progress = seekBar.getProgress();
        int i2 = RESET_ANIMATION_DURATION_MS;
        ObjectAnimator ofInt = ObjectAnimator.ofInt(seekBar, "progress", progress, i + i2);
        ofInt.setAutoCancel(true);
        ofInt.setDuration(i2);
        ofInt.setInterpolator(Interpolators.EMPHASIZED);
        return ofInt;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        SeekBarViewModel.Progress progress = (SeekBarViewModel.Progress) obj;
        MediaViewHolder mediaViewHolder = this.holder;
        Drawable progressDrawable = mediaViewHolder.seekBar.getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (!progress.enabled) {
            int maxHeight = mediaViewHolder.seekBar.getMaxHeight();
            int i = this.seekBarDisabledHeight;
            if (maxHeight != i) {
                mediaViewHolder.seekBar.setMaxHeight(i);
                mediaViewHolder.seekBar.setPadding(mediaViewHolder.seekBar.getPaddingLeft(), this.seekBarDisabledVerticalPadding, mediaViewHolder.seekBar.getPaddingRight(), mediaViewHolder.seekBar.getPaddingBottom());
            }
            mediaViewHolder.seekBar.setEnabled(false);
            if (squigglyProgress != null) {
                squigglyProgress.setAnimate(false);
            }
            mediaViewHolder.seekBar.getThumb().setAlpha(0);
            mediaViewHolder.seekBar.setProgress(0);
            mediaViewHolder.seekBar.setContentDescription("");
            mediaViewHolder.scrubbingElapsedTimeView.setText("");
            mediaViewHolder.scrubbingTotalTimeView.setText("");
            return;
        }
        boolean z = progress.playing;
        this.playingStateLogger.log(String.valueOf(z));
        boolean z2 = progress.listening;
        this.listeningStateLogger.log(String.valueOf(z2));
        Drawable thumb = mediaViewHolder.seekBar.getThumb();
        boolean z3 = progress.seekAvailable;
        thumb.setAlpha(z3 ? 255 : 0);
        mediaViewHolder.seekBar.setEnabled(z3);
        boolean z4 = progress.scrubbing;
        if (squigglyProgress != null) {
            squigglyProgress.setAnimate(z && !z4 && this.animationEnabled && z2);
        }
        if (squigglyProgress != null) {
            squigglyProgress.transitionEnabled = !z3;
            squigglyProgress.invalidateSelf();
        }
        int maxHeight2 = mediaViewHolder.seekBar.getMaxHeight();
        int i2 = this.seekBarEnabledMaxHeight;
        if (maxHeight2 != i2) {
            mediaViewHolder.seekBar.setMaxHeight(i2);
            mediaViewHolder.seekBar.setPadding(mediaViewHolder.seekBar.getPaddingLeft(), this.seekBarEnabledVerticalPadding, mediaViewHolder.seekBar.getPaddingRight(), mediaViewHolder.seekBar.getPaddingBottom());
        }
        SeekBar seekBar = mediaViewHolder.seekBar;
        int i3 = progress.duration;
        seekBar.setMax(i3);
        String formatElapsedTime = DateUtils.formatElapsedTime(i3 / 1000);
        if (z4) {
            mediaViewHolder.scrubbingTotalTimeView.setText(formatElapsedTime);
        }
        Integer num = progress.elapsedTime;
        if (num != null) {
            int intValue = num.intValue();
            if (!z4) {
                Animator animator = this.seekBarResetAnimator;
                if (!(animator != null ? animator.isRunning() : false)) {
                    int i4 = RESET_ANIMATION_THRESHOLD_MS;
                    if (intValue > i4 || mediaViewHolder.seekBar.getProgress() <= i4) {
                        mediaViewHolder.seekBar.setProgress(intValue);
                    } else {
                        Animator buildResetAnimator = buildResetAnimator(intValue);
                        buildResetAnimator.start();
                        this.seekBarResetAnimator = buildResetAnimator;
                    }
                }
            }
            String formatElapsedTime2 = DateUtils.formatElapsedTime(intValue / 1000);
            if (z4) {
                mediaViewHolder.scrubbingElapsedTimeView.setText(formatElapsedTime2);
            }
            SeekBar seekBar2 = mediaViewHolder.seekBar;
            seekBar2.setContentDescription(seekBar2.getContext().getString(R.string.controls_media_seekbar_description, formatElapsedTime2, formatElapsedTime));
        }
    }
}
