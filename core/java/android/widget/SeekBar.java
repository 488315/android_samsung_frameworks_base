package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;

/* loaded from: classes4.dex */
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

    public SeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 16842875);
    }

    public SeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    void onProgressRefresh(float scale, boolean fromUser, int progress) {
        super.onProgressRefresh(scale, fromUser, progress);
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, progress, fromUser);
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        this.mOnSeekBarChangeListener = l;
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
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (canUserSetProgress()) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
    }

    public void semSetOnSeekBarHoverListener(SemOnSeekBarHoverListener l) {
        this.mOnSeekBarHoverListener = l;
    }

    @Override // android.widget.AbsSeekBar
    void onStartTrackingHover(int hoverLevel, int posX, int posY) {
        SemOnSeekBarHoverListener semOnSeekBarHoverListener = this.mOnSeekBarHoverListener;
        if (semOnSeekBarHoverListener != null) {
            semOnSeekBarHoverListener.onStartTrackingHover(this, hoverLevel);
        }
        super.onStartTrackingHover(hoverLevel, posX, posY);
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
    void onHoverChanged(int hoverLevel, int posX, int posY) {
        SemOnSeekBarHoverListener semOnSeekBarHoverListener = this.mOnSeekBarHoverListener;
        if (semOnSeekBarHoverListener != null) {
            semOnSeekBarHoverListener.onHoverChanged(this, hoverLevel, true);
        }
        super.onHoverChanged(hoverLevel, posX, posY);
    }
}
