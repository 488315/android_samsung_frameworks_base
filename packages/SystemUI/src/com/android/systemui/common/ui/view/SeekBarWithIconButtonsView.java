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
import com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$onCreate$1;
import com.android.systemui.res.R$styleable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SeekBarWithIconButtonsView extends LinearLayout {
    public final ImageView mIconEnd;
    public final ViewGroup mIconEndFrame;
    public final ImageView mIconStart;
    public final ViewGroup mIconStartFrame;
    public final int mSeekBarChangeMagnitude;
    public final SeekBarChangeListener mSeekBarListener;
    public final SeekBar mSeekbar;
    public boolean mSetProgressFromButtonFlag;
    public String[] mStateLabels;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnSeekBarWithIconButtonsChangeListener extends SeekBar.OnSeekBarChangeListener {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public OnSeekBarWithIconButtonsChangeListener mOnSeekBarChangeListener;

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
            OnSeekBarWithIconButtonsChangeListener onSeekBarWithIconButtonsChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarWithIconButtonsChangeListener != null) {
                SeekBarWithIconButtonsView seekBarWithIconButtonsView2 = SeekBarWithIconButtonsView.this;
                if (seekBarWithIconButtonsView2.mSetProgressFromButtonFlag) {
                    seekBarWithIconButtonsView2.mSetProgressFromButtonFlag = false;
                    ((FontScalingDialogDelegate$onCreate$1) onSeekBarWithIconButtonsChangeListener).onProgressChanged(seekBar, i, true);
                    ((FontScalingDialogDelegate$onCreate$1) this.mOnSeekBarChangeListener).onUserInteractionFinalized(seekBar, 1);
                } else {
                    ((FontScalingDialogDelegate$onCreate$1) onSeekBarWithIconButtonsChangeListener).onProgressChanged(seekBar, i, z);
                }
            }
            SeekBarWithIconButtonsView.this.updateIconViewIfNeeded(i);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            OnSeekBarWithIconButtonsChangeListener onSeekBarWithIconButtonsChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarWithIconButtonsChangeListener != null) {
                ((FontScalingDialogDelegate$onCreate$1) onSeekBarWithIconButtonsChangeListener).onStartTrackingTouch(seekBar);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            OnSeekBarWithIconButtonsChangeListener onSeekBarWithIconButtonsChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarWithIconButtonsChangeListener != null) {
                onSeekBarWithIconButtonsChangeListener.getClass();
                ((FontScalingDialogDelegate$onCreate$1) this.mOnSeekBarChangeListener).onUserInteractionFinalized(seekBar, 0);
            }
        }

        private SeekBarChangeListener() {
            this.mOnSeekBarChangeListener = null;
        }
    }

    public SeekBarWithIconButtonsView(Context context) {
        this(context, null);
    }

    public OnSeekBarWithIconButtonsChangeListener getOnSeekBarWithIconButtonsChangeListener() {
        return this.mSeekBarListener.mOnSeekBarChangeListener;
    }

    public int getProgress() {
        return this.mSeekbar.getProgress();
    }

    public SeekBar getSeekbar() {
        return this.mSeekbar;
    }

    public final void updateIconViewIfNeeded(int i) {
        ImageView imageView = this.mIconStart;
        boolean z = i > 0;
        imageView.setEnabled(z);
        ((ViewGroup) imageView.getParent()).setEnabled(z);
        ImageView imageView2 = this.mIconEnd;
        boolean z2 = i < this.mSeekbar.getMax();
        imageView2.setEnabled(z2);
        ((ViewGroup) imageView2.getParent()).setEnabled(z2);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSeekBarChangeMagnitude = 1;
        this.mSetProgressFromButtonFlag = false;
        SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener(this, 0);
        this.mSeekBarListener = seekBarChangeListener;
        this.mStateLabels = null;
        LayoutInflater.from(context).inflate(R.layout.seekbar_with_icon_buttons, (ViewGroup) this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.icon_start_frame);
        this.mIconStartFrame = viewGroup;
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.icon_end_frame);
        this.mIconEndFrame = viewGroup2;
        this.mIconStart = (ImageView) findViewById(R.id.icon_start);
        this.mIconEnd = (ImageView) findViewById(R.id.icon_end);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        this.mSeekbar = seekBar;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarWithIconButtonsView_Layout, i, i2);
            int i3 = obtainStyledAttributes.getInt(2, 6);
            int i4 = obtainStyledAttributes.getInt(3, 0);
            seekBar.setMax(i3);
            this.mSeekbar.setProgress(i4);
            updateIconViewIfNeeded(this.mSeekbar.getProgress());
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                viewGroup.setContentDescription(context.getString(resourceId));
            }
            if (resourceId2 != 0) {
                viewGroup2.setContentDescription(context.getString(resourceId2));
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(5, 0);
            if (resourceId3 != 0) {
                seekBar.setTickMark(getResources().getDrawable(resourceId3));
            }
            this.mSeekBarChangeMagnitude = obtainStyledAttributes.getInt(4, 1);
        } else {
            seekBar.setMax(6);
            this.mSeekbar.setProgress(0);
            updateIconViewIfNeeded(this.mSeekbar.getProgress());
        }
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        final int i5 = 0;
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.common.ui.view.SeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i6 = i5;
                SeekBarWithIconButtonsView seekBarWithIconButtonsView = this.f$0;
                switch (i6) {
                    case 0:
                        int progress = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress > 0) {
                            int i7 = progress - seekBarWithIconButtonsView.mSeekBarChangeMagnitude;
                            seekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
                            seekBarWithIconButtonsView.mSeekbar.setProgress(i7);
                            seekBarWithIconButtonsView.updateIconViewIfNeeded(seekBarWithIconButtonsView.mSeekbar.getProgress());
                            break;
                        }
                        break;
                    default:
                        int progress2 = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress2 < seekBarWithIconButtonsView.mSeekbar.getMax()) {
                            int i8 = progress2 + seekBarWithIconButtonsView.mSeekBarChangeMagnitude;
                            seekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
                            seekBarWithIconButtonsView.mSeekbar.setProgress(i8);
                            seekBarWithIconButtonsView.updateIconViewIfNeeded(seekBarWithIconButtonsView.mSeekbar.getProgress());
                            break;
                        }
                        break;
                }
            }
        });
        final int i6 = 1;
        viewGroup2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.common.ui.view.SeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i62 = i6;
                SeekBarWithIconButtonsView seekBarWithIconButtonsView = this.f$0;
                switch (i62) {
                    case 0:
                        int progress = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress > 0) {
                            int i7 = progress - seekBarWithIconButtonsView.mSeekBarChangeMagnitude;
                            seekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
                            seekBarWithIconButtonsView.mSeekbar.setProgress(i7);
                            seekBarWithIconButtonsView.updateIconViewIfNeeded(seekBarWithIconButtonsView.mSeekbar.getProgress());
                            break;
                        }
                        break;
                    default:
                        int progress2 = seekBarWithIconButtonsView.mSeekbar.getProgress();
                        if (progress2 < seekBarWithIconButtonsView.mSeekbar.getMax()) {
                            int i8 = progress2 + seekBarWithIconButtonsView.mSeekBarChangeMagnitude;
                            seekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
                            seekBarWithIconButtonsView.mSeekbar.setProgress(i8);
                            seekBarWithIconButtonsView.updateIconViewIfNeeded(seekBarWithIconButtonsView.mSeekbar.getProgress());
                            break;
                        }
                        break;
                }
            }
        });
    }
}
