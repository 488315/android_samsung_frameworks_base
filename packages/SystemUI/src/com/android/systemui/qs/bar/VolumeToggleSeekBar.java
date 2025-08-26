package com.android.systemui.qs.bar;

import android.content.Context;
import android.os.Handler;
import android.os.SemSystemProperties;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.util.SeekBarUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class VolumeToggleSeekBar extends SeekBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int currentProgress;
    public boolean isSmartViewPlaying;
    public final boolean isTabletModel;
    public boolean isTracking;
    public final Lazy mainThreadHandler$delegate;
    public final SpringAnimation progressBarSpring;
    public final VolumeToggleSeekBar$recheckCallback$1 recheckCallback;
    public float scaledTouchSlop;
    public final VolumeSeekbarChangeListener seekbarChangeListener;
    public int springFinalPosition;
    public final int stream;
    public float touchedX;
    public VolumeDialogController volumeController;
    public VolumeManager volumeManager;
    public VolumeSeekBar volumeSeekBar;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class VolumeSeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeSeekbarChangeListener() {
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x003a  */
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if ((z || VolumeToggleSeekBar.this.isTracking) && VolumeToggleSeekBar.this.isEnabled()) {
                VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
                VolumeSeekBar volumeSeekBar = volumeToggleSeekBar.volumeSeekBar;
                if (volumeSeekBar != null) {
                    volumeSeekBar.progress = i;
                }
                VolumeManager volumeManager = volumeToggleSeekBar.volumeManager;
                if ((volumeManager == null ? null : volumeManager).remoteStreamEnabled) {
                    if ((volumeManager == null ? null : volumeManager).isRemoteStreamPlaying) {
                        VolumeDialogController volumeDialogController = volumeToggleSeekBar.volumeController;
                        if (volumeDialogController != null) {
                            if (volumeManager == null) {
                                volumeManager = null;
                            }
                            volumeDialogController.setStreamVolume(volumeManager.remoteStream, i);
                        }
                    }
                } else {
                    VolumeDialogController volumeDialogController2 = volumeToggleSeekBar.volumeController;
                    if (volumeDialogController2 != null) {
                        volumeDialogController2.setStreamVolume(3, i * 10);
                    }
                }
                SeekBarUtil.vibrateIfNeeded(seekBar, VolumeToggleSeekBar.this.stream, i);
                VolumeSeekBar volumeSeekBar2 = VolumeToggleSeekBar.this.volumeSeekBar;
                if (volumeSeekBar2 != null) {
                    volumeSeekBar2.setProgressChanged(i);
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
            VolumeManager volumeManager = volumeToggleSeekBar.volumeManager;
            if (volumeManager == null) {
                volumeManager = null;
            }
            volumeManager.isTracking = true;
            ((Handler) volumeToggleSeekBar.mainThreadHandler$delegate.getValue()).removeCallbacks(volumeToggleSeekBar.recheckCallback);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
            VolumeManager volumeManager = volumeToggleSeekBar.volumeManager;
            if (volumeManager == null) {
                volumeManager = null;
            }
            volumeManager.isTracking = true;
            ((Handler) volumeToggleSeekBar.mainThreadHandler$delegate.getValue()).removeCallbacks(volumeToggleSeekBar.recheckCallback);
            ((Handler) volumeToggleSeekBar.mainThreadHandler$delegate.getValue()).postDelayed(volumeToggleSeekBar.recheckCallback, 1000L);
            SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_VOLUME_SLIDER, "location", "quick panel", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1] */
    public VolumeToggleSeekBar(Context context) {
        super(context);
        this.stream = 3;
        this.isTabletModel = StringsKt__StringsKt.contains(SemSystemProperties.get("ro.build.characteristics"), "tablet", false);
        this.seekbarChangeListener = new VolumeSeekbarChangeListener();
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new VolumeToggleSeekBar$$ExternalSyntheticLambda0());
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                int i = (int) f;
                int i2 = VolumeToggleSeekBar.$r8$clinit;
                this.this$0.setProgress(i);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = this.this$0.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = this.this$0;
                int i = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = this.this$0.volumeSeekBar;
                if (volumeSeekBar != null) {
                    volumeSeekBar.setProgressChanged(i);
                }
            }
        };
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9) {
            setHovered(true);
        } else if (motionEvent.getAction() == 10) {
            setHovered(false);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0086  */
    @Override // android.widget.AbsSeekBar, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            int action = motionEvent.getAction();
            if (action == 0) {
                if (!this.isSmartViewPlaying) {
                    this.seekbarChangeListener.onStartTrackingTouch(this);
                    this.touchedX = motionEvent.getX();
                    this.currentProgress = getProgress();
                    return true;
                }
                setFocusable(false);
                setFocusableInTouchMode(false);
                clearFocus();
                VolumeManager volumeManager = this.volumeManager;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                String smartViewDeviceName = volumeManager.displayManagerWrapper.getSmartViewDeviceName();
                (this.isTabletModel ? Toast.makeText(getContext(), getContext().getString(R.string.volume_use_your_tablet_volume_smart_view, smartViewDeviceName), 0) : Toast.makeText(getContext(), getContext().getString(R.string.volume_use_your_phone_volume_smart_view, smartViewDeviceName), 0)).show();
                return true;
            }
            if (action == 1) {
                if (!this.isSmartViewPlaying) {
                    this.isTracking = false;
                    this.seekbarChangeListener.onStopTrackingTouch(this);
                    return true;
                }
            } else if (action != 2) {
                if (action != 3) {
                    return super.onTouchEvent(motionEvent);
                }
                if (!this.isSmartViewPlaying) {
                }
            } else if (!this.isSmartViewPlaying) {
                float x = motionEvent.getX() - this.touchedX;
                if (this.isTracking || Math.abs(x) > this.scaledTouchSlop) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.isTracking = true;
                    float width = x / ((getWidth() - getPaddingLeft()) - getPaddingRight());
                    int max = getMax() - getMin();
                    setProgress(Math.round(getResources().getConfiguration().getLayoutDirection() == 1 ? ((width * max) - this.currentProgress) * (-1) : (width * max) + this.currentProgress));
                    return true;
                }
            }
        }
        return true;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        ViewVisibilityUtil.INSTANCE.getClass();
        if (getVisibility() == 0) {
            VolumeManager volumeManager = this.volumeManager;
            if (volumeManager == null) {
                volumeManager = null;
            }
            int i2 = volumeManager.getVolumeModel().volume;
            setProgress(i2);
            VolumeSeekBar volumeSeekBar = this.volumeSeekBar;
            if (volumeSeekBar != null) {
                volumeSeekBar.setProgressChanged(i2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1] */
    public VolumeToggleSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.stream = 3;
        this.isTabletModel = StringsKt__StringsKt.contains(SemSystemProperties.get("ro.build.characteristics"), "tablet", false);
        this.seekbarChangeListener = new VolumeSeekbarChangeListener();
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new VolumeToggleSeekBar$$ExternalSyntheticLambda0());
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                int i = (int) f;
                int i2 = VolumeToggleSeekBar.$r8$clinit;
                this.this$0.setProgress(i);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = this.this$0.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = this.this$0;
                int i = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = this.this$0.volumeSeekBar;
                if (volumeSeekBar != null) {
                    volumeSeekBar.setProgressChanged(i);
                }
            }
        };
    }

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1] */
    public VolumeToggleSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.stream = 3;
        this.isTabletModel = StringsKt__StringsKt.contains(SemSystemProperties.get("ro.build.characteristics"), "tablet", false);
        this.seekbarChangeListener = new VolumeSeekbarChangeListener();
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new VolumeToggleSeekBar$$ExternalSyntheticLambda0());
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                int i2 = (int) f;
                int i22 = VolumeToggleSeekBar.$r8$clinit;
                this.this$0.setProgress(i2);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = this.this$0.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = this.this$0;
                int i2 = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i2;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = this.this$0.volumeSeekBar;
                if (volumeSeekBar != null) {
                    volumeSeekBar.setProgressChanged(i2);
                }
            }
        };
    }
}
