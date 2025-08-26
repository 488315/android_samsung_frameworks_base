package com.android.systemui.haptics.slider;

/* loaded from: classes2.dex */
public interface HapticSlider {

    public final class SeekBar implements HapticSlider {
        public final android.widget.SeekBar seekBar;

        public SeekBar(android.widget.SeekBar seekBar) {
            this.seekBar = seekBar;
        }
    }
}
