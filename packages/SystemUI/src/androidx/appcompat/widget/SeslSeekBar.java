package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslSeekBar extends SeslAbsSeekBar {
    public OnSeekBarChangeListener mOnSeekBarChangeListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z);

        void onStartTrackingTouch();

        void onStopTrackingTouch(SeslSeekBar seslSeekBar);
    }

    public SeslSeekBar(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar, androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar, androidx.appcompat.widget.SeslProgressBar, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        synchronized (this) {
            z = this.mIndeterminate;
        }
        if (z || !isEnabled()) {
            return;
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar, androidx.appcompat.widget.SeslProgressBar
    public final void onProgressRefresh(float f, boolean z, int i) {
        super.onProgressRefresh(f, z, i);
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, i, z);
        }
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void onStartTrackingTouch() {
        super.onStartTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch();
        }
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void onStopTrackingTouch() {
        this.mIsDragging = false;
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    public SeslSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarStyle);
    }

    public SeslSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
