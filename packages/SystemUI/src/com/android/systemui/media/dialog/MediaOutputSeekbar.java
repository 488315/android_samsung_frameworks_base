package com.android.systemui.media.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class MediaOutputSeekbar extends SeekBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    public MediaOutputSeekbar(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSeekBarChangeListener = null;
        setMin(0);
        super.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputSeekbar.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) throws Resources.NotFoundException {
                Resources resources = context.getResources();
                MediaOutputSeekbar mediaOutputSeekbar = MediaOutputSeekbar.this;
                MediaOutputSeekbar.this.setStateDescription(resources.getString(R.string.media_output_dialog_volume_percentage, Integer.valueOf((int) ((((mediaOutputSeekbar.getProgress() / 1000) * 1000) * 100.0d) / mediaOutputSeekbar.getMax()))));
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = MediaOutputSeekbar.this.mOnSeekBarChangeListener;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onProgressChanged(seekBar, i, z);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = MediaOutputSeekbar.this.mOnSeekBarChangeListener;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = MediaOutputSeekbar.this.mOnSeekBarChangeListener;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
    }

    @Override // android.widget.SeekBar
    public final void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }
}
