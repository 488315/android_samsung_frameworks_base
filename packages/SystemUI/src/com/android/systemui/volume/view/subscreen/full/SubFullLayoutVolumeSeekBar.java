package com.android.systemui.volume.view.subscreen.full;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SubFullLayoutVolumeSeekBar extends SeekBar implements VolumeObserver<VolumePanelState> {
    public int currentProgress;
    public boolean enabled;
    public boolean isTracking;
    public final float scaledTouchSlop;
    public SeekBar.OnSeekBarChangeListener seekbarChangeListener;
    public final StoreInteractor storeInteractor;
    public int stream;
    public float touchedX;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class VolumeSeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeSeekbarChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = SubFullLayoutVolumeSeekBar.this;
            if (subFullLayoutVolumeSeekBar.isTracking || z) {
                subFullLayoutVolumeSeekBar.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(SubFullLayoutVolumeSeekBar.this.stream).progress(i).isFromOutside(true).build(), false);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            SubFullLayoutVolumeSeekBar.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING).stream(SubFullLayoutVolumeSeekBar.this.stream).isFromOutside(true).build(), false);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            SubFullLayoutVolumeSeekBar.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING).stream(SubFullLayoutVolumeSeekBar.this.stream).isFromOutside(true).build(), true);
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SubFullLayoutVolumeSeekBar(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.stream = -1;
        this.enabled = true;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        if (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()] == 1) {
            this.storeInteractor.dispose();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enabled) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.touchedX = motionEvent.getX();
            this.currentProgress = getProgress();
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.seekbarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStartTrackingTouch(this);
            }
            this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_DOWN).stream(this.stream).build(), false);
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                float x = motionEvent.getX() - this.touchedX;
                if (this.isTracking) {
                    trackTouchEvent$3(x);
                } else if (Math.abs(x) > this.scaledTouchSlop) {
                    trackTouchEvent$3(x);
                    this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_START_PROGRESS).stream(this.stream).build(), false);
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(motionEvent);
            }
        }
        this.isTracking = false;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = this.seekbarChangeListener;
        if (onSeekBarChangeListener2 != null) {
            onSeekBarChangeListener2.onStopTrackingTouch(this);
        }
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_UP).stream(this.stream).build(), true);
        return true;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        this.enabled = z;
        setAlpha(z ? 1.0f : 0.7f);
    }

    @Override // android.widget.SeekBar
    public final void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.seekbarChangeListener = onSeekBarChangeListener;
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public final void trackTouchEvent$3(float f) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        this.isTracking = true;
        setProgress(Math.round(((f / ((getWidth() - getPaddingLeft()) - getPaddingRight())) * (getMax() - getMin())) + this.currentProgress));
    }

    public SubFullLayoutVolumeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.stream = -1;
        this.enabled = true;
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        setOnSeekBarChangeListener(new VolumeSeekbarChangeListener());
    }
}
