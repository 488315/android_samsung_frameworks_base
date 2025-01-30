package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecSeekBarObserver implements Observer {
    public final SecPlayerViewHolder holder;

    public SecSeekBarObserver(SecPlayerViewHolder secPlayerViewHolder) {
        this.holder = secPlayerViewHolder;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        SecSeekBarViewModel.Progress progress = (SecSeekBarViewModel.Progress) obj;
        boolean z = progress.enabled;
        boolean z2 = false;
        SecPlayerViewHolder secPlayerViewHolder = this.holder;
        if (!z) {
            secPlayerViewHolder.getSeekBar().setEnabled(false);
            secPlayerViewHolder.getSeekBar().getThumb().setAlpha(0);
            secPlayerViewHolder.getSeekBar().setProgress(0);
            TextView textView = secPlayerViewHolder.elapsedTimeView;
            if (textView == null) {
                textView = null;
            }
            textView.setText("");
            TextView textView2 = secPlayerViewHolder.totalTimeView;
            (textView2 != null ? textView2 : null).setText("");
            return;
        }
        Drawable thumb = secPlayerViewHolder.getSeekBar().getThumb();
        boolean z3 = progress.seekAvailable;
        thumb.setAlpha(z3 ? 255 : 0);
        secPlayerViewHolder.getSeekBar().setEnabled(z3);
        SeekBar seekBar = secPlayerViewHolder.getSeekBar();
        int i = progress.duration;
        seekBar.setMax(i);
        TextView textView3 = secPlayerViewHolder.totalTimeView;
        if (textView3 == null) {
            textView3 = null;
        }
        textView3.setText(DateUtils.formatElapsedTime(i / 1000));
        Integer num = progress.elapsedTime;
        if (num != null) {
            int intValue = num.intValue();
            secPlayerViewHolder.getSeekBar().setProgress(intValue);
            TextView textView4 = secPlayerViewHolder.elapsedTimeView;
            (textView4 != null ? textView4 : null).setText(DateUtils.formatElapsedTime(intValue / 1000));
        }
        if ((secPlayerViewHolder.dummyProgressDrawable != null) && z3 && !(secPlayerViewHolder.getSeekBar().getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable)) {
            SeekBar seekBar2 = secPlayerViewHolder.getSeekBar();
            AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = new AudioVisSeekBarProgressDrawable(secPlayerViewHolder.getSeekBar());
            AudioVisSeekBarConfig audioVisSeekBarConfig = audioVisSeekBarProgressDrawable.config;
            audioVisSeekBarConfig.primaryColor = secPlayerViewHolder.progressBarPrimaryColor;
            audioVisSeekBarConfig.secondaryColor = secPlayerViewHolder.progressBarSecondaryColor;
            seekBar2.setProgressDrawable(audioVisSeekBarProgressDrawable);
        }
        if (secPlayerViewHolder.getSeekBar().getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
            AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable2 = (AudioVisSeekBarProgressDrawable) secPlayerViewHolder.getSeekBar().getProgressDrawable();
            if (progress.playing && !progress.scrubbing && z3) {
                z2 = true;
            }
            if (audioVisSeekBarProgressDrawable2.active != z2) {
                audioVisSeekBarProgressDrawable2.active = z2;
                ((SingleStateValueAnimator) audioVisSeekBarProgressDrawable2.motionActivityAnimator$delegate.getValue()).animateTo(z2 ? 1.0f : 0.0f);
            }
            if (z3) {
                return;
            }
            secPlayerViewHolder.getSeekBar().setProgressDrawable(secPlayerViewHolder.dummyProgressDrawable);
        }
    }
}
