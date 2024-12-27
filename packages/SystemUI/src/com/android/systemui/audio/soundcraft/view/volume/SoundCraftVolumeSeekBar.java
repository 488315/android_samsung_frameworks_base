package com.android.systemui.audio.soundcraft.view.volume;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SemSystemProperties;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.Toast;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.reflect.view.SeslViewReflector;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$recheckCallback$1;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.util.SeekBarUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public final class SoundCraftVolumeSeekBar extends SeslSeekBar implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Runnable buttonAnimatorRunnable;
    public int currentProgress;
    public final Handler handler;
    public boolean init;
    public boolean isTracking;
    public final SpringAnimation progressBarSpring;
    public final float scaledTouchSlop;
    public final SeekbarChangeListener seekbarChangeListener;
    public int springFinalPosition;
    public float touchedX;
    public final Lazy viewModel$delegate;

    public final class SeekbarChangeListener implements SeslSeekBar.OnSeekBarChangeListener {
        public SeekbarChangeListener() {
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            SoundCraftVolumeSeekBar soundCraftVolumeSeekBar = SoundCraftVolumeSeekBar.this;
            if (z || soundCraftVolumeSeekBar.isTracking) {
                int i2 = SoundCraftVolumeSeekBar.$r8$clinit;
                VolumeBarViewModel viewModel$1 = soundCraftVolumeSeekBar.getViewModel$1();
                viewModel$1.progress.setValue(Integer.valueOf(i));
                int i3 = i * 10;
                VolumeManager volumeManager = viewModel$1.volumeManager;
                volumeManager.getClass();
                boolean isTagEnabled = Trace.isTagEnabled(4096L);
                Lazy lazy = volumeManager.volumeController$delegate;
                if (isTagEnabled) {
                    Trace.traceBegin(4096L, "#soundCraft.VolumeManager_setStreamVolume");
                    try {
                        ((VolumeDialogController) lazy.getValue()).setStreamVolume(3, i3);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        Trace.traceEnd(4096L);
                    }
                } else {
                    ((VolumeDialogController) lazy.getValue()).setStreamVolume(3, i3);
                    Unit unit2 = Unit.INSTANCE;
                }
                SeekBarUtil.INSTANCE.getClass();
                if (i == seslSeekBar.getMin() || i == seslSeekBar.getMax()) {
                    seslSeekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                }
            }
            int i4 = SoundCraftVolumeSeekBar.$r8$clinit;
            SoundCraftRoundedCornerSeekBarDrawable soundCraftRoundedCornerSeekBarDrawable = (SoundCraftRoundedCornerSeekBarDrawable) ((LayerDrawable) soundCraftVolumeSeekBar.mProgressDrawable).findDrawableByLayerId(R.id.progress);
            soundCraftRoundedCornerSeekBarDrawable.setContext(soundCraftVolumeSeekBar.getContext());
            if (soundCraftVolumeSeekBar.getViewModel$1().isVolumeShocked(soundCraftVolumeSeekBar.getProgress())) {
                soundCraftRoundedCornerSeekBarDrawable.setShockColor(true);
                return;
            }
            int progress = soundCraftVolumeSeekBar.getProgress();
            Integer num = (Integer) soundCraftVolumeSeekBar.getViewModel$1().progressMax.getValue();
            if (num != null && progress == num.intValue()) {
                soundCraftRoundedCornerSeekBarDrawable.setShockColor(true);
            } else {
                soundCraftRoundedCornerSeekBarDrawable.setShockColor(false);
            }
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch() {
            int i = SoundCraftVolumeSeekBar.$r8$clinit;
            SoundCraftVolumeSeekBar soundCraftVolumeSeekBar = SoundCraftVolumeSeekBar.this;
            VolumeBarViewModel viewModel$1 = soundCraftVolumeSeekBar.getViewModel$1();
            viewModel$1.volumeManager.isTracking = true;
            ((Handler) viewModel$1.mainThreadHandler$delegate.getValue()).removeCallbacks(viewModel$1.recheckCallback);
            soundCraftVolumeSeekBar.getViewModel$1().isTouching.setValue(Boolean.TRUE);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            int i = SoundCraftVolumeSeekBar.$r8$clinit;
            SoundCraftVolumeSeekBar soundCraftVolumeSeekBar = SoundCraftVolumeSeekBar.this;
            VolumeBarViewModel viewModel$1 = soundCraftVolumeSeekBar.getViewModel$1();
            viewModel$1.volumeManager.isTracking = true;
            Lazy lazy = viewModel$1.mainThreadHandler$delegate;
            Handler handler = (Handler) lazy.getValue();
            VolumeBarViewModel$recheckCallback$1 volumeBarViewModel$recheckCallback$1 = viewModel$1.recheckCallback;
            handler.removeCallbacks(volumeBarViewModel$recheckCallback$1);
            ((Handler) lazy.getValue()).postDelayed(volumeBarViewModel$recheckCallback$1, 1000L);
            soundCraftVolumeSeekBar.getViewModel$1().isTouching.setValue(Boolean.FALSE);
            SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_VOLUME_SLIDER, "location", "detail panel", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
        }
    }

    public SoundCraftVolumeSeekBar(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$special$$inlined$lazyViewModel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.init = true;
        this.seekbarChangeListener = new SeekbarChangeListener();
        this.handler = new Handler(Looper.getMainLooper());
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                SoundCraftVolumeSeekBar.this.setProgress((int) f);
            }
        });
        this.progressBarSpring = springAnimation;
        this.buttonAnimatorRunnable = SoundCraftVolumeSeekBar$buttonAnimatorRunnable$1.INSTANCE;
    }

    public final void animateSeekBarButton(final boolean z) {
        final SoundCraftRoundedCornerSeekBarDrawable soundCraftRoundedCornerSeekBarDrawable = (SoundCraftRoundedCornerSeekBarDrawable) ((LayerDrawable) this.mProgressDrawable).findDrawableByLayerId(R.id.progress);
        this.handler.removeCallbacks(this.buttonAnimatorRunnable);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$animateSeekBarButton$1
            @Override // java.lang.Runnable
            public final void run() {
                final SoundCraftRoundedCornerSeekBarDrawable soundCraftRoundedCornerSeekBarDrawable2 = SoundCraftRoundedCornerSeekBarDrawable.this;
                boolean z2 = z;
                if (soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator.isRunning()) {
                    soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator.cancel();
                }
                soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator.setInterpolator(SoundCraftRoundedCornerSeekBarDrawable.LINEAR_INTERPOLATOR);
                if (z2) {
                    int i = soundCraftRoundedCornerSeekBarDrawable2.buttonColor;
                    int i2 = soundCraftRoundedCornerSeekBarDrawable2.endColor;
                    if (i != i2) {
                        ValueAnimator duration = ValueAnimator.ofArgb(i, i2).setDuration(200L);
                        soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator = duration;
                        duration.start();
                    }
                } else {
                    int i3 = soundCraftRoundedCornerSeekBarDrawable2.buttonColor;
                    int i4 = soundCraftRoundedCornerSeekBarDrawable2.buttonStartColor;
                    if (i3 != i4) {
                        ValueAnimator duration2 = ValueAnimator.ofArgb(i3, i4).setDuration(200L);
                        soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator = duration2;
                        duration2.start();
                    }
                }
                soundCraftRoundedCornerSeekBarDrawable2.buttonAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftRoundedCornerSeekBarDrawable$animateButton$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SoundCraftRoundedCornerSeekBarDrawable.this.buttonColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        SoundCraftRoundedCornerSeekBarDrawable.this.invalidateSelf();
                    }
                });
            }
        };
        this.buttonAnimatorRunnable = runnable;
        this.handler.post(runnable);
    }

    public final VolumeBarViewModel getViewModel$1() {
        return (VolumeBarViewModel) this.viewModel$delegate.getValue();
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            if (!Intrinsics.areEqual(getViewModel$1().smartViewEnabled.getValue(), Boolean.TRUE)) {
                this.seekbarChangeListener.onStartTrackingTouch();
                this.touchedX = motionEvent.getX();
                this.currentProgress = getProgress();
                if (!this.isTracking) {
                    animateSeekBarButton(true);
                }
                return true;
            }
            setFocusable(false);
            setFocusableInTouchMode(false);
            clearFocus();
            String smartViewDeviceName = getViewModel$1().volumeManager.displayManagerWrapper.getSmartViewDeviceName();
            getViewModel$1().getClass();
            (StringsKt__StringsKt.contains(SemSystemProperties.get("ro.build.characteristics"), "tablet", false) ? Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_tablet_volume_smart_view, smartViewDeviceName), 0) : Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_phone_volume_smart_view, smartViewDeviceName), 0)).show();
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                if (Intrinsics.areEqual(getViewModel$1().smartViewEnabled.getValue(), Boolean.TRUE)) {
                    return true;
                }
                float x = motionEvent.getX() - this.touchedX;
                if (this.isTracking || Math.abs(x) > this.scaledTouchSlop) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.isTracking = true;
                    float width = x / ((getWidth() - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this));
                    int max = getMax() - getMin();
                    setProgress(Math.round(getResources().getConfiguration().getLayoutDirection() == 1 ? ((width * max) - this.currentProgress) * (-1) : (width * max) + this.currentProgress));
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(motionEvent);
            }
        }
        if (Intrinsics.areEqual(getViewModel$1().smartViewEnabled.getValue(), Boolean.TRUE)) {
            return true;
        }
        this.seekbarChangeListener.onStopTrackingTouch(this);
        this.isTracking = false;
        animateSeekBarButton(false);
        return true;
    }

    public SoundCraftVolumeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$special$$inlined$lazyViewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.init = true;
        SeekbarChangeListener seekbarChangeListener = new SeekbarChangeListener();
        this.seekbarChangeListener = seekbarChangeListener;
        this.handler = new Handler(Looper.getMainLooper());
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar$progressBarSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                SoundCraftVolumeSeekBar.this.setProgress((int) f);
            }
        });
        this.progressBarSpring = springAnimation;
        this.buttonAnimatorRunnable = SoundCraftVolumeSeekBar$buttonAnimatorRunnable$1.INSTANCE;
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        this.isTracking = false;
        this.mOnSeekBarChangeListener = seekbarChangeListener;
    }
}
