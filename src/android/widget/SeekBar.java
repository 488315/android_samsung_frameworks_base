package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;

/* loaded from: classes5.dex */
public class SeekBar extends AbsSeekBar {
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private SemOnSeekBarHoverListener mOnSeekBarHoverListener;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }

    public interface SemOnSeekBarHoverListener {
        void onHoverChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingHover(SeekBar seekBar, int i);

        void onStopTrackingHover(SeekBar seekBar);
    }

    public SeekBar(Context context) {
        this(context, null);
    }

    public SeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842875);
    }

    public SeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    void onProgressRefresh(float f, boolean z, int i) throws Throwable {
        super.onProgressRefresh(f, z, i);
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, i, z);
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    @Override // android.widget.AbsSeekBar
    void onStartTrackingTouch() {
        super.onStartTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    @Override // android.widget.AbsSeekBar
    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (canUserSetProgress()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
    }

    public void semSetOnSeekBarHoverListener(SemOnSeekBarHoverListener semOnSeekBarHoverListener) {
        this.mOnSeekBarHoverListener = semOnSeekBarHoverListener;
    }

    @Override // android.widget.AbsSeekBar
    void onStartTrackingHover(int i, int i2, int i3) {
        SemOnSeekBarHoverListener semOnSeekBarHoverListener = this.mOnSeekBarHoverListener;
        if (semOnSeekBarHoverListener != null) {
            semOnSeekBarHoverListener.onStartTrackingHover(this, i);
        }
        super.onStartTrackingHover(i, i2, i3);
    }

    @Override // android.widget.AbsSeekBar
    void onStopTrackingHover() {
        SemOnSeekBarHoverListener semOnSeekBarHoverListener = this.mOnSeekBarHoverListener;
        if (semOnSeekBarHoverListener != null) {
            semOnSeekBarHoverListener.onStopTrackingHover(this);
        }
        super.onStopTrackingHover();
    }

    @Override // android.widget.AbsSeekBar
    void onHoverChanged(int i, int i2, int i3) {
        SemOnSeekBarHoverListener semOnSeekBarHoverListener = this.mOnSeekBarHoverListener;
        if (semOnSeekBarHoverListener != null) {
            semOnSeekBarHoverListener.onHoverChanged(this, i, true);
        }
        super.onHoverChanged(i, i2, i3);
    }
}
