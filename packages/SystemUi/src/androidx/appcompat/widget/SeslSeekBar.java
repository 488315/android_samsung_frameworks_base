package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslSeekBar extends SeslAbsSeekBar {
    public int mOldValue;
    public OnSeekBarChangeListener mOnSeekBarChangeListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z);

        void onStartTrackingTouch(SeslSeekBar seslSeekBar);

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
        if (!z && isEnabled()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar, androidx.appcompat.widget.SeslProgressBar
    public final void onProgressRefresh(float f, int i, boolean z) {
        super.onProgressRefresh(f, i, z);
        if (!this.mIsSeamless) {
            OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onProgressChanged(this, i, z);
                return;
            }
            return;
        }
        int round = Math.round(i / 1000.0f);
        if (this.mOldValue != round) {
            this.mOldValue = round;
            OnSeekBarChangeListener onSeekBarChangeListener2 = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener2 != null) {
                onSeekBarChangeListener2.onProgressChanged(this, round, z);
            }
        }
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void onStartTrackingTouch() {
        super.onStartTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void onStopTrackingTouch() {
        super.onStopTrackingTouch();
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
