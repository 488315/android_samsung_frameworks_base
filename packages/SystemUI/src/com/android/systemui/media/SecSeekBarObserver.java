package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class SecSeekBarObserver implements Observer {
    public final SecPlayerViewHolder holder;
    public final String TAG = Reflection.getOrCreateKotlinClass(SecSeekBarObserver.class).getSimpleName();
    public final boolean DEBUG = true;

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
            SeekBar seekBar = secPlayerViewHolder.seekBar;
            if (seekBar == null) {
                seekBar = null;
            }
            seekBar.setEnabled(false);
            SeekBar seekBar2 = secPlayerViewHolder.seekBar;
            if (seekBar2 == null) {
                seekBar2 = null;
            }
            seekBar2.getThumb().setAlpha(0);
            SeekBar seekBar3 = secPlayerViewHolder.seekBar;
            if (seekBar3 == null) {
                seekBar3 = null;
            }
            seekBar3.setProgress(0);
            TextView textView = secPlayerViewHolder.elapsedTimeView;
            if (textView == null) {
                textView = null;
            }
            textView.setText("");
            TextView textView2 = secPlayerViewHolder.totalTimeView;
            (textView2 != null ? textView2 : null).setText("");
            return;
        }
        SeekBar seekBar4 = secPlayerViewHolder.seekBar;
        if (seekBar4 == null) {
            seekBar4 = null;
        }
        Drawable thumb = seekBar4.getThumb();
        boolean z3 = progress.seekAvailable;
        thumb.setAlpha(z3 ? 255 : 0);
        SeekBar seekBar5 = secPlayerViewHolder.seekBar;
        if (seekBar5 == null) {
            seekBar5 = null;
        }
        seekBar5.setEnabled(z3);
        SeekBar seekBar6 = secPlayerViewHolder.seekBar;
        if (seekBar6 == null) {
            seekBar6 = null;
        }
        int i = progress.duration;
        seekBar6.setMax(i);
        TextView textView3 = secPlayerViewHolder.totalTimeView;
        if (textView3 == null) {
            textView3 = null;
        }
        textView3.setText(DateUtils.formatElapsedTime(i / 1000));
        Integer num = progress.elapsedTime;
        if (num != null) {
            int iIntValue = num.intValue();
            SeekBar seekBar7 = secPlayerViewHolder.seekBar;
            if (seekBar7 == null) {
                seekBar7 = null;
            }
            seekBar7.setProgress(iIntValue);
            TextView textView4 = secPlayerViewHolder.elapsedTimeView;
            if (textView4 == null) {
                textView4 = null;
            }
            textView4.setText(DateUtils.formatElapsedTime(iIntValue / 1000));
        }
        LayerDrawable layerDrawable = secPlayerViewHolder.dummyProgressDrawable;
        String str = this.TAG;
        boolean z4 = this.DEBUG;
        if (layerDrawable != null && z3) {
            SeekBar seekBar8 = secPlayerViewHolder.seekBar;
            if (seekBar8 == null) {
                seekBar8 = null;
            }
            if (!(seekBar8.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable)) {
                if (z4) {
                    Log.d(str, "ColorUpdate progressDrawable !is AudioVisSeekBarProgressDrawable");
                }
                SeekBar seekBar9 = secPlayerViewHolder.seekBar;
                if (seekBar9 == null) {
                    seekBar9 = null;
                }
                SeekBar seekBar10 = secPlayerViewHolder.seekBar;
                if (seekBar10 == null) {
                    seekBar10 = null;
                }
                AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable = new AudioVisSeekBarProgressDrawable(seekBar10);
                AudioVisSeekBarConfig audioVisSeekBarConfig = audioVisSeekBarProgressDrawable.config;
                audioVisSeekBarConfig.primaryColor = secPlayerViewHolder.progressBarPrimaryColor;
                audioVisSeekBarConfig.secondaryColor = secPlayerViewHolder.progressBarSecondaryColor;
                seekBar9.setProgressDrawable(audioVisSeekBarProgressDrawable);
            }
        }
        SeekBar seekBar11 = secPlayerViewHolder.seekBar;
        if (seekBar11 == null) {
            seekBar11 = null;
        }
        if (seekBar11.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
            SeekBar seekBar12 = secPlayerViewHolder.seekBar;
            if (seekBar12 == null) {
                seekBar12 = null;
            }
            AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable2 = (AudioVisSeekBarProgressDrawable) seekBar12.getProgressDrawable();
            if (progress.playing && !progress.scrubbing && z3 && !secPlayerViewHolder.isRemoveAnimation()) {
                z2 = true;
            }
            if (audioVisSeekBarProgressDrawable2.active != z2) {
                audioVisSeekBarProgressDrawable2.active = z2;
                ((SingleStateValueAnimator) audioVisSeekBarProgressDrawable2.motionActivityAnimator$delegate.getValue()).animateTo(z2 ? 1.0f : 0.0f);
            }
            SeekBar seekBar13 = secPlayerViewHolder.seekBar;
            if (seekBar13 == null) {
                seekBar13 = null;
            }
            AudioVisSeekBarProgressDrawable audioVisSeekBarProgressDrawable3 = (AudioVisSeekBarProgressDrawable) seekBar13.getProgressDrawable();
            boolean z5 = audioVisSeekBarProgressDrawable3.listening;
            boolean z6 = progress.listening;
            if (z5 != z6) {
                audioVisSeekBarProgressDrawable3.listening = z6;
                SingleStateValueAnimator singleStateValueAnimator = (SingleStateValueAnimator) audioVisSeekBarProgressDrawable3.motionActivityAnimator$delegate.getValue();
                singleStateValueAnimator.listening = z6;
                if (z6) {
                    singleStateValueAnimator.animator.start();
                    singleStateValueAnimator.animator.end();
                } else {
                    singleStateValueAnimator.animator.cancel();
                }
            }
            if (z3) {
                return;
            }
            if (z4) {
                Log.d(str, "!data.seekAvailable");
            }
            SeekBar seekBar14 = secPlayerViewHolder.seekBar;
            (seekBar14 != null ? seekBar14 : null).setProgressDrawable(secPlayerViewHolder.dummyProgressDrawable);
        }
    }
}
