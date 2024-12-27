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
import com.android.systemui.volume.util.ViewVisibilityUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class VolumeSeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeSeekbarChangeListener() {
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
        /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onProgressChanged(android.widget.SeekBar r4, int r5, boolean r6) {
            /*
                r3 = this;
                if (r6 != 0) goto L8
                com.android.systemui.qs.bar.VolumeToggleSeekBar r6 = com.android.systemui.qs.bar.VolumeToggleSeekBar.this
                boolean r6 = r6.isTracking
                if (r6 == 0) goto L55
            L8:
                com.android.systemui.qs.bar.VolumeToggleSeekBar r6 = com.android.systemui.qs.bar.VolumeToggleSeekBar.this
                boolean r6 = r6.isEnabled()
                if (r6 == 0) goto L55
                com.android.systemui.qs.bar.VolumeToggleSeekBar r6 = com.android.systemui.qs.bar.VolumeToggleSeekBar.this
                com.android.systemui.qs.bar.VolumeSeekBar r0 = r6.volumeSeekBar
                if (r0 != 0) goto L17
                goto L19
            L17:
                r0.progress = r5
            L19:
                com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager r0 = r6.volumeManager
                r1 = 0
                if (r0 != 0) goto L20
                r2 = r1
                goto L21
            L20:
                r2 = r0
            L21:
                boolean r2 = r2.remoteStreamEnabled
                if (r2 == 0) goto L3b
                if (r0 != 0) goto L29
                r2 = r1
                goto L2a
            L29:
                r2 = r0
            L2a:
                boolean r2 = r2.isRemoteStreamPlaying
                if (r2 == 0) goto L3b
                com.android.systemui.plugins.VolumeDialogController r6 = r6.volumeController
                if (r6 == 0) goto L45
                if (r0 != 0) goto L35
                r0 = r1
            L35:
                int r0 = r0.remoteStream
                r6.setStreamVolume(r0, r5)
                goto L45
            L3b:
                com.android.systemui.plugins.VolumeDialogController r6 = r6.volumeController
                if (r6 == 0) goto L45
                int r0 = r5 * 10
                r1 = 3
                r6.setStreamVolume(r1, r0)
            L45:
                com.android.systemui.qs.bar.VolumeToggleSeekBar r6 = com.android.systemui.qs.bar.VolumeToggleSeekBar.this
                int r6 = r6.stream
                com.android.systemui.volume.util.SeekBarUtil.vibrateIfNeeded(r4, r6, r5)
                com.android.systemui.qs.bar.VolumeToggleSeekBar r3 = com.android.systemui.qs.bar.VolumeToggleSeekBar.this
                com.android.systemui.qs.bar.VolumeSeekBar r3 = r3.volumeSeekBar
                if (r3 == 0) goto L55
                r3.setProgressChanged(r5)
            L55:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.VolumeToggleSeekBar.VolumeSeekbarChangeListener.onProgressChanged(android.widget.SeekBar, int, boolean):void");
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
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(VolumeToggleSeekBar$mainThreadHandler$2.INSTANCE);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                int i = VolumeToggleSeekBar.$r8$clinit;
                VolumeToggleSeekBar.this.setProgress((int) f);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = VolumeToggleSeekBar.this.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
                int i = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = VolumeToggleSeekBar.this.volumeSeekBar;
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

    @Override // android.widget.AbsSeekBar, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return true;
        }
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
        if (action != 1) {
            if (action == 2) {
                if (this.isSmartViewPlaying) {
                    return true;
                }
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
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(motionEvent);
            }
        }
        if (this.isSmartViewPlaying) {
            return true;
        }
        this.isTracking = false;
        this.seekbarChangeListener.onStopTrackingTouch(this);
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
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(VolumeToggleSeekBar$mainThreadHandler$2.INSTANCE);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                int i = VolumeToggleSeekBar.$r8$clinit;
                VolumeToggleSeekBar.this.setProgress((int) f);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = VolumeToggleSeekBar.this.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
                int i = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = VolumeToggleSeekBar.this.volumeSeekBar;
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
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(VolumeToggleSeekBar$mainThreadHandler$2.INSTANCE);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                int i2 = VolumeToggleSeekBar.$r8$clinit;
                VolumeToggleSeekBar.this.setProgress((int) f);
            }
        });
        this.progressBarSpring = springAnimation;
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.qs.bar.VolumeToggleSeekBar$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeManager volumeManager = VolumeToggleSeekBar.this.volumeManager;
                (volumeManager == null ? null : volumeManager).isTracking = false;
                if (volumeManager == null) {
                    volumeManager = null;
                }
                VolumeModel volumeModel = volumeManager.getVolumeModel();
                VolumeToggleSeekBar volumeToggleSeekBar = VolumeToggleSeekBar.this;
                int i2 = volumeModel.volume;
                volumeToggleSeekBar.springFinalPosition = i2;
                volumeToggleSeekBar.progressBarSpring.setStartValue(volumeToggleSeekBar.getProgress());
                volumeToggleSeekBar.progressBarSpring.animateToFinalPosition(volumeToggleSeekBar.springFinalPosition);
                VolumeSeekBar volumeSeekBar = VolumeToggleSeekBar.this.volumeSeekBar;
                if (volumeSeekBar != null) {
                    volumeSeekBar.setProgressChanged(i2);
                }
            }
        };
    }
}
