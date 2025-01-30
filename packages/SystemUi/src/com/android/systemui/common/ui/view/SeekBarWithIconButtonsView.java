package com.android.systemui.common.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.android.systemui.R;
import com.android.systemui.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeekBarWithIconButtonsView extends LinearLayout {
    public final ImageView mIconEnd;
    public final ViewGroup mIconEndFrame;
    public final ImageView mIconStart;
    public final ViewGroup mIconStartFrame;
    public final SeekBarChangeListener mSeekBarListener;
    public final SeekBar mSeekbar;
    public String[] mStateLabels;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

        public /* synthetic */ SeekBarChangeListener(SeekBarWithIconButtonsView seekBarWithIconButtonsView, int i) {
            this();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            SeekBarWithIconButtonsView seekBarWithIconButtonsView = SeekBarWithIconButtonsView.this;
            if (seekBarWithIconButtonsView.mStateLabels != null) {
                SeekBar seekBar2 = seekBarWithIconButtonsView.mSeekbar;
                int progress = seekBar2.getProgress();
                String[] strArr = seekBarWithIconButtonsView.mStateLabels;
                seekBar2.setStateDescription(progress < strArr.length ? strArr[seekBarWithIconButtonsView.mSeekbar.getProgress()] : "");
            }
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onProgressChanged(seekBar, i, z);
            }
            SeekBarWithIconButtonsView seekBarWithIconButtonsView2 = SeekBarWithIconButtonsView.this;
            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView2.mIconStart, i > 0);
            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView2.mIconEnd, i < seekBarWithIconButtonsView2.mSeekbar.getMax());
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStartTrackingTouch(seekBar);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStopTrackingTouch(seekBar);
            }
        }

        private SeekBarChangeListener() {
            this.mOnSeekBarChangeListener = null;
        }
    }

    public SeekBarWithIconButtonsView(Context context) {
        this(context, null);
    }

    public static void setIconViewAndFrameEnabled(View view, boolean z) {
        view.setEnabled(z);
        ((ViewGroup) view.getParent()).setEnabled(z);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 0;
        SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener(this, i3);
        this.mSeekBarListener = seekBarChangeListener;
        this.mStateLabels = null;
        final int i4 = 1;
        LayoutInflater.from(context).inflate(R.layout.seekbar_with_icon_buttons, (ViewGroup) this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.icon_start_frame);
        this.mIconStartFrame = viewGroup;
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.icon_end_frame);
        this.mIconEndFrame = viewGroup2;
        ImageView imageView = (ImageView) findViewById(R.id.icon_start);
        this.mIconStart = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.icon_end);
        this.mIconEnd = imageView2;
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        this.mSeekbar = seekBar;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarWithIconButtonsView_Layout, i, i2);
            int i5 = obtainStyledAttributes.getInt(2, 6);
            int i6 = obtainStyledAttributes.getInt(3, 0);
            seekBar.setMax(i5);
            seekBar.setProgress(i6);
            setIconViewAndFrameEnabled(imageView, i6 > 0);
            setIconViewAndFrameEnabled(imageView2, i6 < seekBar.getMax());
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                viewGroup.setContentDescription(context.getString(resourceId));
            }
            if (resourceId2 != 0) {
                viewGroup2.setContentDescription(context.getString(resourceId2));
            }
        } else {
            seekBar.setMax(6);
            seekBar.setProgress(0);
            setIconViewAndFrameEnabled(imageView, false);
            setIconViewAndFrameEnabled(imageView2, seekBar.getMax() > 0);
        }
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.common.ui.view.SeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        SeekBarWithIconButtonsView seekBarWithIconButtonsView = this.f$0;
                        int progress = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress > 0) {
                            seekBarWithIconButtonsView.mSeekbar.setProgress(progress - 1);
                            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView.mIconStart, seekBarWithIconButtonsView.mSeekbar.getProgress() > 0);
                            break;
                        }
                        break;
                    default:
                        SeekBarWithIconButtonsView seekBarWithIconButtonsView2 = this.f$0;
                        int progress2 = seekBarWithIconButtonsView2.mSeekbar.getProgress();
                        if (progress2 < seekBarWithIconButtonsView2.mSeekbar.getMax()) {
                            seekBarWithIconButtonsView2.mSeekbar.setProgress(progress2 + 1);
                            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView2.mIconEnd, seekBarWithIconButtonsView2.mSeekbar.getProgress() < seekBarWithIconButtonsView2.mSeekbar.getMax());
                            break;
                        }
                        break;
                }
            }
        });
        viewGroup2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.common.ui.view.SeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                    case 0:
                        SeekBarWithIconButtonsView seekBarWithIconButtonsView = this.f$0;
                        int progress = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress > 0) {
                            seekBarWithIconButtonsView.mSeekbar.setProgress(progress - 1);
                            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView.mIconStart, seekBarWithIconButtonsView.mSeekbar.getProgress() > 0);
                            break;
                        }
                        break;
                    default:
                        SeekBarWithIconButtonsView seekBarWithIconButtonsView2 = this.f$0;
                        int progress2 = seekBarWithIconButtonsView2.mSeekbar.getProgress();
                        if (progress2 < seekBarWithIconButtonsView2.mSeekbar.getMax()) {
                            seekBarWithIconButtonsView2.mSeekbar.setProgress(progress2 + 1);
                            SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView2.mIconEnd, seekBarWithIconButtonsView2.mSeekbar.getProgress() < seekBarWithIconButtonsView2.mSeekbar.getMax());
                            break;
                        }
                        break;
                }
            }
        });
    }
}
