package com.android.wm.shell.controlpanel.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.feature.SemFloatingFeature;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class BrightnessVolumeView extends ConstraintLayout {
    public static final boolean mIsFold = ControlPanelUtils.isTypeFold();
    public final AccessibilityManager mAccessibilityManager;
    public final AudioManager mAudioManager;
    public final ImageView mBrightnessIcon;
    public final AnonymousClass1 mBrightnessRunnable;
    public final SeslSeekBar mBrightnessSeekBar;
    public final AnonymousClass3 mBrightnessSeekBarChangeListener;
    public final Context mContext;
    public int mCurrentMediaIconState;
    public int mCurrentRingerMode;
    public int mCurrentRingtoneIconState;
    public int mCurrentVolumeLevel;
    public final DisplayManager mDisplayManager;
    public int mGetRingerMode;
    public FlexPanelActivity mGridUIManager;
    public final int mMaxBrightness;
    public final LinearLayout mMediaBrightnessLayout;
    public final FrameLayout mMediaVolumeAnimatedIconLayout;
    public final FrameLayout mMediaVolumeLayout;
    public final int mMinBrightness;
    public final IconMotion mMotion;
    public final ImageView mMute;
    public final ImageView mNote;
    public final ImageView mSplash;
    public int mStreamType;
    public final int mTouchSlop;
    public final ImageView mVolumeIcon;
    public final AnonymousClass2 mVolumeRunnable;
    public final SeslSeekBar mVolumeSeekBar;
    public final AnonymousClass5 mVolumeSeekBarChangeListener;
    public float mVolumeSeekBarScale;
    public float mVolumeSeekBarStartTouch;
    public final AnonymousClass4 mVolumeSeekBarTouchListener;
    public boolean mVolumeSeekBarTracking;
    public final ImageView mWaveL;
    public final ImageView mWaveS;

    /* renamed from: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$5, reason: invalid class name */
    public class AnonymousClass5 implements SeslSeekBar.OnSeekBarChangeListener {
        public AnonymousClass5() {
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            StringBuilder sb = new StringBuilder("mVolumeSeekBarChangeListener onProgressChanged mVolumeSeekBarTracking : ");
            BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
            sb.append(brightnessVolumeView.mVolumeSeekBarTracking);
            sb.append(", mStreamType : ");
            sb.append(brightnessVolumeView.mStreamType);
            sb.append(", progress : ");
            sb.append(i);
            Log.i("BrightnessVolumeView", sb.toString());
            if (ControlPanelUtils.isAccessibilityEnabled(brightnessVolumeView.mContext) && z) {
                brightnessVolumeView.mVolumeSeekBarTracking = true;
            }
            if (brightnessVolumeView.mVolumeSeekBarTracking) {
                if (i > 0 && i < 10) {
                    i = 10;
                }
                brightnessVolumeView.mAudioManager.setStreamVolume(brightnessVolumeView.mStreamType, i / 10, 0);
            }
            brightnessVolumeView.setVolumeIcon(brightnessVolumeView.mStreamType);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch() {
            Log.i("BrightnessVolumeView", "mVolumeSeekBarChangeListener onStartTrackingTouch");
            BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
            brightnessVolumeView.mVolumeSeekBarTracking = true;
            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, false);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            Log.i("BrightnessVolumeView", "mVolumeSeekBarChangeListener onStopTrackingTouch");
            BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
            brightnessVolumeView.mVolumeSeekBarTracking = false;
            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, true);
        }
    }

    public class IconMotion {
        public final Handler mHandler = new Handler(Looper.getMainLooper());
        public Runnable mIconRunnable;
        public final Resources mResources;

        public IconMotion(Context context) {
            this.mResources = context.getResources();
        }

        public static Animator getVibrationAnimator(View view, float f, float f2, int i) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2 != 0.0f ? (-f) + f2 : 0.0f);
            objectAnimatorOfFloat.setDuration(i);
            objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
            return objectAnimatorOfFloat;
        }

        public final void startMaxAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_max_x);
            int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_s_max_x);
            int dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_l_max_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_max_x);
                dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_l_max_x);
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorOfFloat);
            animatorSet.playTogether(objectAnimatorOfFloat2);
            animatorSet.setDuration(z ? 0L : 150L);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
            ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(objectAnimatorOfFloat3);
            animatorSet2.playTogether(objectAnimatorOfFloat4);
            animatorSet2.playTogether(objectAnimatorOfFloat5);
            animatorSet2.setDuration(z ? 0L : 200L);
            animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(animatorSet);
            animatorSet3.playTogether(animatorSet2);
            animatorSet3.start();
        }

        public final void startMidAnimation(int i, int i2, View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
            int i3;
            long j;
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_mid_x);
            int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_s_mid_x);
            int dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_l_mid_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_mid_x);
                dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_l_mid_x);
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorOfFloat);
            animatorSet.playTogether(objectAnimatorOfFloat2);
            if (z) {
                i3 = 1;
                j = 0;
            } else {
                i3 = 1;
                j = 100;
            }
            animatorSet.setDuration(j);
            animatorSet.setInterpolator(new LinearInterpolator());
            float[] fArr = new float[2];
            fArr[0] = view.getX();
            fArr[i3] = dimensionPixelSize;
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", fArr);
            float[] fArr2 = new float[2];
            fArr2[0] = view2.getX();
            fArr2[i3] = dimensionPixelSize2;
            ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view2, "x", fArr2);
            float[] fArr3 = new float[2];
            fArr3[0] = view3.getX();
            fArr3[i3] = dimensionPixelSize3;
            ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view3, "x", fArr3);
            AnimatorSet animatorSet2 = new AnimatorSet();
            Animator[] animatorArr = new Animator[i3];
            animatorArr[0] = objectAnimatorOfFloat3;
            animatorSet2.playTogether(animatorArr);
            Animator[] animatorArr2 = new Animator[i3];
            animatorArr2[0] = objectAnimatorOfFloat4;
            animatorSet2.playTogether(animatorArr2);
            Animator[] animatorArr3 = new Animator[i3];
            animatorArr3[0] = objectAnimatorOfFloat5;
            animatorSet2.playTogether(animatorArr3);
            animatorSet2.setDuration(z ? 0L : 200L);
            animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(animatorSet2);
            animatorSet3.playTogether(animatorSet);
            this.mHandler.removeCallbacks(this.mIconRunnable);
            this.mIconRunnable = new BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0(this, i2, i, view, view2, view3, view4, view5, view6, 0);
            animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.IconMotion.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    IconMotion iconMotion = IconMotion.this;
                    iconMotion.mHandler.postDelayed(iconMotion.mIconRunnable, 200L);
                }
            });
            animatorSet3.start();
        }

        public final void startMinAnimation(int i, int i2, View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
            float f;
            AnimatorSet animatorSet;
            long j;
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_min_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_spk_min_x);
                f = 0.3f;
            } else {
                f = 0.0f;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(objectAnimatorOfFloat);
            animatorSet2.playTogether(objectAnimatorOfFloat2);
            animatorSet2.setDuration(z ? 0L : 100L);
            animatorSet2.setInterpolator(new LinearInterpolator());
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(objectAnimatorOfFloat3);
            if (i == 2) {
                animatorSet3.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_min_x)));
            }
            if (z) {
                animatorSet = animatorSet2;
                j = 0;
            } else {
                animatorSet = animatorSet2;
                j = 200;
            }
            animatorSet3.setDuration(j);
            animatorSet3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mHandler.removeCallbacks(this.mIconRunnable);
            this.mIconRunnable = new BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0(this, i2, i, view, view2, view3, view4, view5, view6, 1);
            AnimatorSet animatorSet4 = new AnimatorSet();
            animatorSet4.playTogether(animatorSet);
            animatorSet4.playTogether(animatorSet3);
            animatorSet4.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.IconMotion.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    IconMotion iconMotion = IconMotion.this;
                    iconMotion.mHandler.postDelayed(iconMotion.mIconRunnable, 200L);
                }
            });
            animatorSet4.start();
        }

        public final void startMuteAnimation(int i, View view, View view2, View view3, View view4, View view5, final View view6, boolean z) {
            view5.setVisibility(0);
            view.setVisibility(4);
            view6.setVisibility(0);
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_min_x);
            if (i == 2) {
                dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_spk_min_x);
                view4.setVisibility(8);
                view2.setVisibility(4);
                view3.setVisibility(4);
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorOfFloat);
            animatorSet.playTogether(objectAnimatorOfFloat2);
            animatorSet.setDuration(z ? 0L : 100L);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            objectAnimatorOfFloat3.setDuration(z ? 0L : 200L);
            KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.22f, 0.25f, 0.0f, 1.0f, objectAnimatorOfFloat3);
            this.mHandler.removeCallbacks(this.mIconRunnable);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(animatorSet);
            animatorSet2.playTogether(objectAnimatorOfFloat3);
            animatorSet2.start();
            if (z) {
                return;
            }
            view6.setScaleX(0.0f);
            SpringAnimation springAnimation = new SpringAnimation(view6, DynamicAnimation.SCALE_X);
            springAnimation.cancel();
            springAnimation.mVelocity = 0.0f;
            springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda1
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                    View view7 = view6;
                    if (f2 == 0.0f) {
                        view7.setPivotX(0.0f);
                        view7.setPivotY(0.0f);
                    }
                }
            });
            SpringForce springForce = new SpringForce();
            springForce.setStiffness(300.0f);
            springForce.setDampingRatio(0.58f);
            springAnimation.mSpring = springForce;
            springAnimation.setStartValue(0.0f);
            springAnimation.animateToFinalPosition(1.0f);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [androidx.appcompat.widget.SeslSeekBar$OnSeekBarChangeListener, com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$3] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$4] */
    public BrightnessVolumeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentMediaIconState = -1;
        this.mCurrentRingtoneIconState = -1;
        this.mBrightnessRunnable = new Runnable() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.1
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessVolumeView.this.mBrightnessSeekBar.setVisibility(4);
                BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                FlexPanelActivity flexPanelActivity = brightnessVolumeView.mGridUIManager;
                if (flexPanelActivity != null) {
                    flexPanelActivity.returnToMenu();
                } else {
                    brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                    BrightnessVolumeView.this.setDefaultBrightnessView();
                }
            }
        };
        this.mVolumeRunnable = new Runnable() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.2
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessVolumeView.this.mVolumeSeekBar.setVisibility(4);
                BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                FlexPanelActivity flexPanelActivity = brightnessVolumeView.mGridUIManager;
                if (flexPanelActivity != null) {
                    flexPanelActivity.returnToMenu();
                    return;
                }
                brightnessVolumeView.mVolumeSeekBar.setEnabled(true);
                BrightnessVolumeView.this.mVolumeSeekBar.setAlpha(1.0f);
                BrightnessVolumeView.this.mVolumeIcon.setAlpha(1.0f);
                BrightnessVolumeView.this.mMediaBrightnessLayout.setVisibility(0);
                BrightnessVolumeView.this.setDefaultVolumeIcon();
            }
        };
        ?? r1 = new SeslSeekBar.OnSeekBarChangeListener() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.3
            public int currentBrightness = -1;

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
                if (z) {
                    BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                    int i2 = brightnessVolumeView.mMaxBrightness;
                    int i3 = brightnessVolumeView.mMinBrightness;
                    brightnessVolumeView.mDisplayManager.semSetTemporaryBrightness((i - i3) / (i2 - i3));
                    brightnessVolumeView.setBrightnessViewColor(i);
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch() {
                Log.i("BrightnessVolumeView", "mBrightnessSeekBarChangeListener onStartTrackingTouch");
                BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                this.currentBrightness = Settings.System.getInt(brightnessVolumeView.mContext.getContentResolver(), "screen_brightness", 0);
                brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, false);
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
                TooltipPopup$$ExternalSyntheticOutline0.m(this.currentBrightness, "BrightnessVolumeView", new StringBuilder("mBrightnessSeekBarChangeListener onStopTrackingTouch currentBrightness : "));
                BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                this.currentBrightness = brightnessVolumeView.mBrightnessSeekBar.getProgress();
                int i = brightnessVolumeView.mMaxBrightness;
                int i2 = brightnessVolumeView.mMinBrightness;
                brightnessVolumeView.mDisplayManager.semSetTemporaryBrightness((r0 - i2) / (i - i2));
                Settings.System.putInt(brightnessVolumeView.mContext.getContentResolver(), "screen_brightness", brightnessVolumeView.mBrightnessSeekBar.getProgress());
                brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, true);
            }
        };
        this.mBrightnessSeekBarChangeListener = r1;
        this.mVolumeSeekBarTouchListener = new View.OnTouchListener() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.4
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (!(view instanceof SeslSeekBar)) {
                    Log.e("BrightnessVolumeView", "View is not an instance of SeslSeekBar");
                    return false;
                }
                SeslSeekBar seslSeekBar = (SeslSeekBar) view;
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    BrightnessVolumeView.this.mVolumeSeekBarChangeListener.onStartTrackingTouch();
                    BrightnessVolumeView.this.mCurrentVolumeLevel = seslSeekBar.getProgress();
                    int max = seslSeekBar.getMax() - seslSeekBar.getMin();
                    if (BrightnessVolumeView.mIsFold) {
                        int height = seslSeekBar.getHeight() - (seslSeekBar.getPaddingBottom() + seslSeekBar.getPaddingTop());
                        BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                        brightnessVolumeView.mVolumeSeekBarScale = max / height;
                        brightnessVolumeView.mVolumeSeekBarStartTouch = motionEvent.getY();
                        return true;
                    }
                    int width = seslSeekBar.getWidth() - (seslSeekBar.getPaddingEnd() + seslSeekBar.getPaddingStart());
                    BrightnessVolumeView brightnessVolumeView2 = BrightnessVolumeView.this;
                    brightnessVolumeView2.mVolumeSeekBarScale = max / width;
                    brightnessVolumeView2.mVolumeSeekBarStartTouch = motionEvent.getX();
                    return true;
                }
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        BrightnessVolumeView brightnessVolumeView3 = BrightnessVolumeView.this;
                        float f = brightnessVolumeView3.mCurrentVolumeLevel;
                        if (BrightnessVolumeView.mIsFold) {
                            float y = brightnessVolumeView3.mVolumeSeekBarStartTouch - motionEvent.getY();
                            float fAbs = Math.abs(y);
                            BrightnessVolumeView brightnessVolumeView4 = BrightnessVolumeView.this;
                            if (fAbs > brightnessVolumeView4.mTouchSlop) {
                                f += y * brightnessVolumeView4.mVolumeSeekBarScale;
                            }
                        } else {
                            float x = motionEvent.getX() - BrightnessVolumeView.this.mVolumeSeekBarStartTouch;
                            float fAbs2 = Math.abs(x);
                            BrightnessVolumeView brightnessVolumeView5 = BrightnessVolumeView.this;
                            if (fAbs2 > brightnessVolumeView5.mTouchSlop) {
                                f = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(brightnessVolumeView5.mContext) == 1 ? f - (x * BrightnessVolumeView.this.mVolumeSeekBarScale) : f + (x * BrightnessVolumeView.this.mVolumeSeekBarScale);
                            }
                        }
                        seslSeekBar.setProgressInternal(Math.round(f), false, true);
                        return true;
                    }
                    if (actionMasked != 3) {
                        return false;
                    }
                }
                BrightnessVolumeView.this.mVolumeSeekBarChangeListener.onStopTrackingTouch(seslSeekBar);
                return true;
            }
        };
        this.mVolumeSeekBarChangeListener = new AnonymousClass5();
        this.mContext = context;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        AudioManager audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mAudioManager = audioManager;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mMotion = new IconMotion(context);
        this.mStreamType = AudioManager.semGetActiveStreamType();
        this.mCurrentRingerMode = audioManager.getRingerMode();
        Log.i("BrightnessVolumeView", "BrightnessVolumeView mStreamType : " + AudioManager.semGetActiveStreamType() + ", mCurrentRingerMode : " + this.mCurrentRingerMode);
        LayoutInflater.from(getContext()).inflate(R.layout.brightness_volume_area, this);
        this.mMediaBrightnessLayout = (LinearLayout) findViewById(R.id.media_brightness_layout);
        this.mBrightnessSeekBar = (SeslSeekBar) findViewById(R.id.media_brightness_seekbar);
        this.mMediaVolumeLayout = (FrameLayout) findViewById(R.id.media_volume_layout);
        this.mMediaVolumeAnimatedIconLayout = (FrameLayout) findViewById(R.id.media_volume_animated_icon_layout);
        this.mVolumeSeekBar = (SeslSeekBar) findViewById(R.id.media_volume_seekbar);
        this.mBrightnessIcon = (ImageView) findViewById(R.id.brightness_icon);
        this.mVolumeIcon = (ImageView) findViewById(R.id.volume_icon);
        this.mNote = (ImageView) findViewById(R.id.volume_media_icon_note);
        this.mWaveL = (ImageView) findViewById(R.id.volume_media_icon_wave_l);
        this.mWaveS = (ImageView) findViewById(R.id.volume_media_icon_wave_s);
        this.mMute = (ImageView) findViewById(R.id.volume_media_icon_mute);
        this.mSplash = (ImageView) findViewById(R.id.volume_icon_mute_splash);
        this.mMaxBrightness = powerManager.semGetMaximumScreenBrightnessSetting();
        this.mMinBrightness = powerManager.semGetMinimumScreenBrightnessSetting();
        this.mBrightnessSeekBar.setMax(this.mMaxBrightness);
        this.mBrightnessSeekBar.setMin(this.mMinBrightness);
        this.mBrightnessSeekBar.setProgress(Settings.System.getInt(context.getContentResolver(), "screen_brightness", 0));
        this.mBrightnessSeekBar.mOnSeekBarChangeListener = r1;
        final int i = 0;
        this.mMediaBrightnessLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$$ExternalSyntheticLambda0
            public final /* synthetic */ BrightnessVolumeView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                BrightnessVolumeView brightnessVolumeView = this.f$0;
                switch (i2) {
                    case 0:
                        brightnessVolumeView.mMediaBrightnessLayout.setVisibility(0);
                        if (brightnessVolumeView.mBrightnessSeekBar.getVisibility() != 0) {
                            brightnessVolumeView.mMediaVolumeLayout.setVisibility(4);
                            brightnessVolumeView.mBrightnessSeekBar.setVisibility(0);
                            brightnessVolumeView.setBrightnessViewColor(Settings.System.getInt(brightnessVolumeView.mContext.getContentResolver(), "screen_brightness", 0));
                            if (brightnessVolumeView.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView.mBrightnessSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, false);
                            brightnessVolumeView.mBrightnessSeekBar.setVisibility(4);
                            FlexPanelActivity flexPanelActivity = brightnessVolumeView.mGridUIManager;
                            if (flexPanelActivity == null) {
                                brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultBrightnessView();
                                break;
                            } else {
                                flexPanelActivity.returnToMenu();
                                break;
                            }
                        }
                    default:
                        brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                        if (brightnessVolumeView.mVolumeSeekBar.getVisibility() != 0) {
                            brightnessVolumeView.mMediaBrightnessLayout.setVisibility(4);
                            brightnessVolumeView.mVolumeSeekBar.setVisibility(0);
                            brightnessVolumeView.mStreamType = AudioManager.semGetActiveStreamType();
                            if (ControlPanelUtils.isClockActivity(brightnessVolumeView.mContext)) {
                                brightnessVolumeView.mVolumeSeekBar.setEnabled(false);
                                brightnessVolumeView.mVolumeSeekBar.setAlpha(0.4f);
                                brightnessVolumeView.mVolumeIcon.setAlpha(0.4f);
                            } else {
                                brightnessVolumeView.setVolumeSeekBar(brightnessVolumeView.mStreamType);
                                brightnessVolumeView.setVolumeIcon(brightnessVolumeView.mStreamType);
                            }
                            if (brightnessVolumeView.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView.mVolumeSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, false);
                            brightnessVolumeView.mVolumeSeekBar.setVisibility(4);
                            FlexPanelActivity flexPanelActivity2 = brightnessVolumeView.mGridUIManager;
                            if (flexPanelActivity2 == null) {
                                brightnessVolumeView.mMediaBrightnessLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultVolumeIcon();
                                break;
                            } else {
                                flexPanelActivity2.returnToMenu();
                                break;
                            }
                        }
                }
            }
        });
        final int i2 = 1;
        this.mMediaVolumeLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$$ExternalSyntheticLambda0
            public final /* synthetic */ BrightnessVolumeView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                BrightnessVolumeView brightnessVolumeView = this.f$0;
                switch (i22) {
                    case 0:
                        brightnessVolumeView.mMediaBrightnessLayout.setVisibility(0);
                        if (brightnessVolumeView.mBrightnessSeekBar.getVisibility() != 0) {
                            brightnessVolumeView.mMediaVolumeLayout.setVisibility(4);
                            brightnessVolumeView.mBrightnessSeekBar.setVisibility(0);
                            brightnessVolumeView.setBrightnessViewColor(Settings.System.getInt(brightnessVolumeView.mContext.getContentResolver(), "screen_brightness", 0));
                            if (brightnessVolumeView.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView.mBrightnessSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, false);
                            brightnessVolumeView.mBrightnessSeekBar.setVisibility(4);
                            FlexPanelActivity flexPanelActivity = brightnessVolumeView.mGridUIManager;
                            if (flexPanelActivity == null) {
                                brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultBrightnessView();
                                break;
                            } else {
                                flexPanelActivity.returnToMenu();
                                break;
                            }
                        }
                    default:
                        brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                        if (brightnessVolumeView.mVolumeSeekBar.getVisibility() != 0) {
                            brightnessVolumeView.mMediaBrightnessLayout.setVisibility(4);
                            brightnessVolumeView.mVolumeSeekBar.setVisibility(0);
                            brightnessVolumeView.mStreamType = AudioManager.semGetActiveStreamType();
                            if (ControlPanelUtils.isClockActivity(brightnessVolumeView.mContext)) {
                                brightnessVolumeView.mVolumeSeekBar.setEnabled(false);
                                brightnessVolumeView.mVolumeSeekBar.setAlpha(0.4f);
                                brightnessVolumeView.mVolumeIcon.setAlpha(0.4f);
                            } else {
                                brightnessVolumeView.setVolumeSeekBar(brightnessVolumeView.mStreamType);
                                brightnessVolumeView.setVolumeIcon(brightnessVolumeView.mStreamType);
                            }
                            if (brightnessVolumeView.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView.mVolumeSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView.handlerExcute(brightnessVolumeView.mVolumeRunnable, false);
                            brightnessVolumeView.mVolumeSeekBar.setVisibility(4);
                            FlexPanelActivity flexPanelActivity2 = brightnessVolumeView.mGridUIManager;
                            if (flexPanelActivity2 == null) {
                                brightnessVolumeView.mMediaBrightnessLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultVolumeIcon();
                                break;
                            } else {
                                flexPanelActivity2.returnToMenu();
                                break;
                            }
                        }
                }
            }
        });
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP")) {
            ControlPanelUtils.setRatioPadding(context, this.mBrightnessSeekBar, 3.9d, 0.0d, 7.2d, 0.0d);
            ControlPanelUtils.setRatioPadding(context, this.mVolumeSeekBar, 3.9d, 0.0d, 7.2d, 0.0d);
            ControlPanelUtils.setRatioPadding(context, findViewById(R.id.flex_panel_brightness_area), 7.2d, 0.0d, 0.0d, 0.0d);
            ControlPanelUtils.setRatioPadding(context, findViewById(R.id.flex_panel_volume_area), 7.2d, 0.0d, 0.0d, 0.0d);
        }
        setDefaultBrightnessView();
        setDefaultVolumeIcon();
    }

    public final void handlerExcute(Runnable runnable, boolean z) {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            if (!z || ControlPanelUtils.isAccessibilityEnabled(this.mContext)) {
                return;
            }
            handler.postDelayed(runnable, 3000L);
        }
    }

    public final void setBrightnessViewColor(int i) {
        if (i < 217) {
            setDefaultBrightnessView();
            return;
        }
        this.mBrightnessIcon.setImageTintList(ContextCompat.getColorStateList(R.color.brightness_volume_other_color, this.mContext));
        this.mBrightnessSeekBar.setProgressTintList(ContextCompat.getColorStateList(R.color.brightness_volume_other_color, this.mContext));
        this.mBrightnessSeekBar.setThumbTintList(ContextCompat.getColorStateList(R.color.brightness_volume_other_color, this.mContext));
        this.mBrightnessSeekBar.setProgressBackgroundTintList(ContextCompat.getColorStateList(R.color.brightness_volume_other_color_background, this.mContext));
    }

    public final void setDefaultBrightnessView() {
        this.mBrightnessIcon.setImageTintList(ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, this.mContext));
        this.mBrightnessSeekBar.setProgressTintList(ContextCompat.getColorStateList(R.color.seekbar_color_expand, this.mContext));
        this.mBrightnessSeekBar.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_color_expand, this.mContext));
        this.mBrightnessSeekBar.setProgressBackgroundTintList(ContextCompat.getColorStateList(R.color.seekbar_background_color_expand, this.mContext));
    }

    public final void setDefaultVolumeIcon() {
        this.mVolumeIcon.setVisibility(0);
        this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_sound));
    }

    public final void setVolumeIcon(int i) {
        int i2;
        BluetoothDevice bluetoothDevice;
        AudioDeviceInfo audioDeviceInfo;
        int i3;
        ColorStateList colorStateList = ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, this.mContext);
        this.mStreamType = i;
        this.mGetRingerMode = this.mAudioManager.getRingerMode();
        StringBuilder sb = new StringBuilder("BrightnessVolumeView setVolumeIcon mStreamType : ");
        sb.append(this.mStreamType);
        sb.append(", mGetRingerMode : ");
        TooltipPopup$$ExternalSyntheticOutline0.m(this.mGetRingerMode, "BrightnessVolumeView", sb);
        int i4 = this.mStreamType;
        if (i4 == 10) {
            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_accessibility));
            return;
        }
        if (i4 == 16) {
            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_bixby));
            return;
        }
        int i5 = 3;
        switch (i4) {
            case 0:
            case 6:
                this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_call));
                break;
            case 1:
                if (this.mAudioManager.getStreamVolume(1) != 0) {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_system_sound));
                    break;
                } else {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_system_sound_mute));
                    break;
                }
            case 2:
                this.mNote.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_sound_rintone));
                this.mMute.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_sound_mute));
                this.mNote.setImageTintList(colorStateList);
                this.mMute.setImageTintList(colorStateList);
                this.mSplash.setImageTintList(colorStateList);
                this.mWaveL.setImageTintList(colorStateList);
                this.mWaveS.setImageTintList(colorStateList);
                if (this.mAudioManager.getStreamVolume(2) == 0) {
                    this.mVolumeIcon.setVisibility(0);
                    this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
                    int i6 = this.mGetRingerMode;
                    if (i6 == 1) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_vibrate));
                        IconMotion iconMotion = this.mMotion;
                        ImageView imageView = this.mVolumeIcon;
                        if (this.mCurrentRingerMode != this.mGetRingerMode) {
                            float dimensionPixelSize = iconMotion.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_vibrate_init);
                            float dimensionPixelSize2 = iconMotion.mResources.getDimensionPixelSize(R.dimen.flex_panel_volume_vibrate_offset);
                            float f = -dimensionPixelSize;
                            Animator vibrationAnimator = IconMotion.getVibrationAnimator(imageView, 0.0f, f, 60);
                            float f2 = dimensionPixelSize - dimensionPixelSize2;
                            Animator vibrationAnimator2 = IconMotion.getVibrationAnimator(imageView, f, f2, 80);
                            float f3 = -(dimensionPixelSize - (dimensionPixelSize2 * 2.0f));
                            Animator vibrationAnimator3 = IconMotion.getVibrationAnimator(imageView, f2, f3, 100);
                            Animator vibrationAnimator4 = IconMotion.getVibrationAnimator(imageView, f3, 0.0f, 120);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(vibrationAnimator);
                            arrayList.add(vibrationAnimator2);
                            arrayList.add(vibrationAnimator3);
                            arrayList.add(vibrationAnimator4);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playSequentially(arrayList);
                            animatorSet.start();
                        } else {
                            iconMotion.getClass();
                        }
                    } else if (i6 == 0) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_mute));
                    }
                    i5 = -1;
                } else {
                    this.mMediaVolumeAnimatedIconLayout.setVisibility(0);
                    this.mVolumeIcon.setVisibility(4);
                    if (this.mAudioManager.getStreamVolume(2) > 0 && this.mAudioManager.getStreamVolume(2) <= 3) {
                        i2 = 1;
                        this.mMotion.startMinAnimation(this.mStreamType, 1, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 1);
                    } else if (this.mAudioManager.getStreamVolume(2) <= 3 || this.mAudioManager.getStreamVolume(2) > 7) {
                        this.mMotion.startMaxAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 3);
                    } else {
                        i2 = 2;
                        this.mMotion.startMidAnimation(this.mStreamType, 2, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 2);
                    }
                    i5 = i2;
                }
                this.mCurrentRingtoneIconState = i5;
                this.mCurrentRingerMode = this.mGetRingerMode;
                break;
            case 3:
                boolean z = this.mAudioManager.semIsSafeMediaVolumeDeviceOn() && !(this.mAudioManager.semIsFmRadioActive() && this.mAudioManager.semGetRadioOutputPath() == 2);
                ColorStateList colorStateList2 = ContextCompat.getColorStateList(R.color.seekbar_color_expand, this.mContext);
                ColorStateList colorStateList3 = ContextCompat.getColorStateList(R.color.seekbar_background_color_expand, this.mContext);
                if (z) {
                    int i7 = this.mStreamType;
                    int iSemGetEarProtectLimit = ((AudioManager.semGetEarProtectLimit() - 1) * 100) + 9;
                    int streamVolume = this.mAudioManager.getStreamVolume(i7);
                    if (iSemGetEarProtectLimit > 0 && iSemGetEarProtectLimit < streamVolume * 100) {
                        colorStateList = ContextCompat.getColorStateList(R.color.brightness_volume_other_color, this.mContext);
                        colorStateList2 = ContextCompat.getColorStateList(R.color.brightness_volume_other_color, this.mContext);
                        colorStateList3 = ContextCompat.getColorStateList(R.color.brightness_volume_other_color_background, this.mContext);
                    }
                }
                this.mVolumeIcon.setImageTintList(colorStateList);
                this.mNote.setImageTintList(colorStateList);
                this.mMute.setImageTintList(colorStateList);
                this.mSplash.setImageTintList(colorStateList);
                this.mWaveL.setImageTintList(colorStateList);
                this.mWaveS.setImageTintList(colorStateList);
                this.mVolumeSeekBar.setProgressTintList(colorStateList2);
                this.mVolumeSeekBar.setThumbTintList(colorStateList2);
                this.mVolumeSeekBar.setProgressBackgroundTintList(colorStateList3);
                int iSemGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
                ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iSemGetCurrentDeviceType, "setVolumeIcon currentDeviceType : ", "BrightnessVolumeView");
                if (iSemGetCurrentDeviceType != 3 && iSemGetCurrentDeviceType != 22 && iSemGetCurrentDeviceType != 4) {
                    if (iSemGetCurrentDeviceType != 23) {
                        if (iSemGetCurrentDeviceType != 8 && iSemGetCurrentDeviceType != 7) {
                            this.mMediaVolumeAnimatedIconLayout.setVisibility(0);
                            this.mVolumeIcon.setVisibility(4);
                            if (this.mAudioManager.getStreamVolume(3) == 0) {
                                this.mMotion.startMuteAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 0);
                                i5 = 0;
                            } else {
                                if (this.mAudioManager.getStreamVolume(3) > 0 && this.mAudioManager.getStreamVolume(3) <= 3) {
                                    i3 = 1;
                                    this.mMotion.startMinAnimation(this.mStreamType, 1, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 1);
                                } else if (this.mAudioManager.getStreamVolume(3) <= 3 || this.mAudioManager.getStreamVolume(3) > 7) {
                                    this.mMotion.startMaxAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 3);
                                } else {
                                    i3 = 2;
                                    this.mMotion.startMidAnimation(this.mStreamType, 2, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 2);
                                }
                                i5 = i3;
                            }
                            this.mCurrentMediaIconState = i5;
                            break;
                        } else {
                            AudioDeviceInfo[] devices = this.mAudioManager.getDevices(2);
                            int length = devices.length;
                            int i8 = 0;
                            while (true) {
                                bluetoothDevice = null;
                                if (i8 < length) {
                                    audioDeviceInfo = devices[i8];
                                    if (audioDeviceInfo.getType() != iSemGetCurrentDeviceType || (audioDeviceInfo.getType() != 8 && audioDeviceInfo.getType() != 7)) {
                                        i8++;
                                    }
                                } else {
                                    audioDeviceInfo = null;
                                }
                            }
                            if (audioDeviceInfo != null) {
                                Iterator<BluetoothDevice> it = BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        BluetoothDevice next = it.next();
                                        if (next.getAddress().equals(audioDeviceInfo.getAddress())) {
                                            bluetoothDevice = next;
                                        }
                                    }
                                }
                                if (bluetoothDevice != null) {
                                    this.mVolumeIcon.setVisibility(0);
                                    this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
                                    byte[] bArrSemGetManufacturerDeviceIconIndex = bluetoothDevice.semGetManufacturerDeviceIconIndex();
                                    short sShortValue = ((bArrSemGetManufacturerDeviceIconIndex == null || bArrSemGetManufacturerDeviceIconIndex.length < 2) ? (short) 0 : Short.valueOf((short) ((bArrSemGetManufacturerDeviceIconIndex[1] & 255) | (bArrSemGetManufacturerDeviceIconIndex[0] << 8)))).shortValue();
                                    if (sShortValue == 5381) {
                                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.tw_ic_audio_buds3_solid));
                                    } else if (sShortValue == 5379 || sShortValue == 5380) {
                                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.tw_ic_audio_buds_solid));
                                    } else if (sShortValue == 10242) {
                                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_galaxy_home_mini));
                                    } else {
                                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_bluetooth));
                                    }
                                }
                            }
                        }
                    } else {
                        this.mVolumeIcon.setVisibility(0);
                        this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.tw_ic_audio_hearing_aids));
                    }
                } else {
                    this.mVolumeIcon.setVisibility(0);
                    this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.tw_ic_audio_wire_earphone_mtrl));
                }
                i5 = -1;
                this.mCurrentMediaIconState = i5;
                break;
            case 4:
                if (this.mAudioManager.getStreamVolume(4) != 0) {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_sound));
                    break;
                } else {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_mute));
                    break;
                }
            case 5:
                if (this.mAudioManager.getStreamVolume(5) != 0) {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_sound));
                    break;
                } else {
                    int i9 = this.mGetRingerMode;
                    if (i9 != 1) {
                        if (i9 == 0) {
                            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_mute));
                            break;
                        }
                    } else {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_vibrate));
                        break;
                    }
                }
                break;
        }
    }

    public final void setVolumeSeekBar(int i) {
        int streamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
        this.mStreamType = i;
        this.mGetRingerMode = this.mAudioManager.getRingerMode();
        StringBuilder sb = new StringBuilder("setVolumeSeekBar mStreamType : ");
        ViewPager$$ExternalSyntheticOutline0.m(sb, this.mStreamType, ", volumeValue : ", streamVolume, ", mGetRingerMode : ");
        TooltipPopup$$ExternalSyntheticOutline0.m(this.mGetRingerMode, "BrightnessVolumeView", sb);
        int i2 = this.mStreamType;
        if (i2 == 0 || i2 == 6) {
            this.mVolumeSeekBar.setMax((this.mAudioManager.getStreamMaxVolume(i2) * 10) + 1);
            this.mVolumeSeekBar.setProgress((streamVolume * 10) + 1);
        } else {
            this.mVolumeSeekBar.setMax(this.mAudioManager.getStreamMaxVolume(i2) * 10);
            this.mVolumeSeekBar.setProgress(streamVolume * 10);
        }
        SeslSeekBar seslSeekBar = this.mVolumeSeekBar;
        seslSeekBar.mOnSeekBarChangeListener = this.mVolumeSeekBarChangeListener;
        seslSeekBar.setOnTouchListener(this.mVolumeSeekBarTouchListener);
    }
}
