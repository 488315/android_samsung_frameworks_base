package com.android.wm.shell.controlpanel.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.wm.shell.controlpanel.GridUIManager;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.systemui.R;
import com.samsung.android.feature.SemFloatingFeature;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BrightnessVolumeView extends ConstraintLayout {
    public final AccessibilityManager mAccessibilityManager;
    public final AudioManager mAudioManager;
    public ImageView mBrightnessIcon;
    public final RunnableC39441 mBrightnessRunnable;
    public SeslSeekBar mBrightnessSeekBar;
    public final C39463 mBrightnessSeekBarChangeListener;
    public final Context mContext;
    public int mCurrentMediaIconState;
    public int mCurrentRingerMode;
    public int mCurrentRingtoneIconState;
    public final DisplayManager mDisplayManager;
    public int mGetRingerMode;
    public GridUIManager mGridUIManager;
    public int mMaxBrightness;
    public LinearLayout mMediaBrightnessLayout;
    public FrameLayout mMediaVolumeAnimatedIconLayout;
    public FrameLayout mMediaVolumeLayout;
    public int mMinBrightness;
    public final IconMotion mMotion;
    public ImageView mMute;
    public ImageView mNote;
    public ImageView mSplash;
    public int mStreamType;
    public ImageView mVolumeIcon;
    public final RunnableC39452 mVolumeRunnable;
    public SeslSeekBar mVolumeSeekBar;
    public final C39474 mVolumeSeekBarChangeListener;
    public boolean mVolumeSeekBarTracking;
    public ImageView mWaveL;
    public ImageView mWaveS;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IconMotion {
        public final Handler mHandler = new Handler(Looper.getMainLooper());
        public BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0 mIconRunnable;
        public final Resources mResources;

        public IconMotion(Context context) {
            this.mResources = context.getResources();
        }

        public static Animator getVibrationAnimator(float f, float f2, int i, View view) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2 != 0.0f ? (-f) + f2 : 0.0f);
            ofFloat.setDuration(i);
            ofFloat.setInterpolator(new LinearInterpolator());
            return ofFloat;
        }

        public final void startMaxAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            Resources resources = this.mResources;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_max_x);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_s_max_x);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_l_max_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_max_x);
                dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_l_max_x);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat);
            animatorSet.playTogether(ofFloat2);
            animatorSet.setDuration(z ? 0L : 150L);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofFloat3);
            animatorSet2.playTogether(ofFloat4);
            animatorSet2.playTogether(ofFloat5);
            animatorSet2.setDuration(z ? 0L : 200L);
            animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(animatorSet);
            animatorSet3.playTogether(animatorSet2);
            animatorSet3.start();
        }

        public final void startMidAnimation(int i, int i2, View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            Resources resources = this.mResources;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_mid_x);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_s_mid_x);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_wave_l_mid_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_mid_x);
                dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_l_mid_x);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat);
            animatorSet.playTogether(ofFloat2);
            animatorSet.setDuration(z ? 0L : 100L);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofFloat3);
            animatorSet2.playTogether(ofFloat4);
            animatorSet2.playTogether(ofFloat5);
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
            Resources resources;
            long j;
            view5.setVisibility(8);
            view.setVisibility(0);
            view6.setVisibility(8);
            Resources resources2 = this.mResources;
            int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_min_x);
            if (i == 2) {
                view4.setVisibility(8);
                view2.setVisibility(0);
                view3.setVisibility(0);
                dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_spk_min_x);
                f = 0.3f;
            } else {
                f = 0.0f;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat);
            animatorSet.playTogether(ofFloat2);
            if (z) {
                resources = resources2;
                j = 0;
            } else {
                resources = resources2;
                j = 100;
            }
            animatorSet.setDuration(j);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofFloat3);
            if (i == 2) {
                animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_wave_s_min_x)));
            }
            animatorSet2.setDuration(z ? 0L : 200L);
            animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mHandler.removeCallbacks(this.mIconRunnable);
            this.mIconRunnable = new BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0(this, i2, i, view, view2, view3, view4, view5, view6, 1);
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(animatorSet);
            animatorSet3.playTogether(animatorSet2);
            animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.IconMotion.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    IconMotion iconMotion = IconMotion.this;
                    iconMotion.mHandler.postDelayed(iconMotion.mIconRunnable, 200L);
                }
            });
            animatorSet3.start();
        }

        public final void startMuteAnimation(int i, View view, View view2, View view3, View view4, View view5, final View view6, boolean z) {
            view5.setVisibility(0);
            view.setVisibility(4);
            view6.setVisibility(0);
            Resources resources = this.mResources;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_media_icon_note_min_x);
            if (i == 2) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_sound_icon_spk_min_x);
                view4.setVisibility(8);
                view2.setVisibility(4);
                view3.setVisibility(4);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat);
            animatorSet.playTogether(ofFloat2);
            animatorSet.setDuration(z ? 0L : 100L);
            animatorSet.setInterpolator(new LinearInterpolator());
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
            ofFloat3.setDuration(z ? 0L : 200L);
            SecLockIconView$$ExternalSyntheticOutline0.m82m(0.22f, 0.25f, 0.0f, 1.0f, ofFloat3);
            this.mHandler.removeCallbacks(this.mIconRunnable);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(animatorSet);
            animatorSet2.playTogether(ofFloat3);
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
                public final void onAnimationUpdate(float f, float f2) {
                    if (f2 == 0.0f) {
                        View view7 = view6;
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
                GridUIManager gridUIManager = brightnessVolumeView.mGridUIManager;
                if (gridUIManager != null) {
                    ((FlexPanelActivity) gridUIManager).returnToMenu();
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
                GridUIManager gridUIManager = brightnessVolumeView.mGridUIManager;
                if (gridUIManager != null) {
                    ((FlexPanelActivity) gridUIManager).returnToMenu();
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
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
                Log.i("BrightnessVolumeView", "mBrightnessSeekBarChangeListener onStartTrackingTouch");
                BrightnessVolumeView brightnessVolumeView = BrightnessVolumeView.this;
                this.currentBrightness = Settings.System.getInt(brightnessVolumeView.mContext.getContentResolver(), "screen_brightness", 0);
                brightnessVolumeView.handlerExcute(brightnessVolumeView.mBrightnessRunnable, false);
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
                TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("mBrightnessSeekBarChangeListener onStopTrackingTouch currentBrightness : "), this.currentBrightness, "BrightnessVolumeView");
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
        this.mVolumeSeekBarChangeListener = new SeslSeekBar.OnSeekBarChangeListener() { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView.4
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
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
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
        };
        this.mContext = context;
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
        final int i = 0;
        this.mBrightnessSeekBar.setProgress(Settings.System.getInt(context.getContentResolver(), "screen_brightness", 0));
        this.mBrightnessSeekBar.mOnSeekBarChangeListener = r1;
        this.mMediaBrightnessLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.widget.BrightnessVolumeView$$ExternalSyntheticLambda0
            public final /* synthetic */ BrightnessVolumeView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        BrightnessVolumeView brightnessVolumeView = this.f$0;
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
                            GridUIManager gridUIManager = brightnessVolumeView.mGridUIManager;
                            if (gridUIManager == null) {
                                brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultBrightnessView();
                                break;
                            } else {
                                ((FlexPanelActivity) gridUIManager).returnToMenu();
                                break;
                            }
                        }
                    default:
                        BrightnessVolumeView brightnessVolumeView2 = this.f$0;
                        brightnessVolumeView2.mMediaVolumeLayout.setVisibility(0);
                        if (brightnessVolumeView2.mVolumeSeekBar.getVisibility() != 0) {
                            brightnessVolumeView2.mMediaBrightnessLayout.setVisibility(4);
                            brightnessVolumeView2.mVolumeSeekBar.setVisibility(0);
                            brightnessVolumeView2.mStreamType = AudioManager.semGetActiveStreamType();
                            if (ControlPanelUtils.isClockActivity(brightnessVolumeView2.mContext)) {
                                brightnessVolumeView2.mVolumeSeekBar.setEnabled(false);
                                brightnessVolumeView2.mVolumeSeekBar.setAlpha(0.4f);
                                brightnessVolumeView2.mVolumeIcon.setAlpha(0.4f);
                            } else {
                                brightnessVolumeView2.setVolumeSeekBar(brightnessVolumeView2.mStreamType);
                                brightnessVolumeView2.setVolumeIcon(brightnessVolumeView2.mStreamType);
                            }
                            if (brightnessVolumeView2.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView2.mVolumeSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mVolumeRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mVolumeRunnable, false);
                            brightnessVolumeView2.mVolumeSeekBar.setVisibility(4);
                            GridUIManager gridUIManager2 = brightnessVolumeView2.mGridUIManager;
                            if (gridUIManager2 == null) {
                                brightnessVolumeView2.mMediaBrightnessLayout.setVisibility(0);
                                brightnessVolumeView2.setDefaultVolumeIcon();
                                break;
                            } else {
                                ((FlexPanelActivity) gridUIManager2).returnToMenu();
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
                switch (i2) {
                    case 0:
                        BrightnessVolumeView brightnessVolumeView = this.f$0;
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
                            GridUIManager gridUIManager = brightnessVolumeView.mGridUIManager;
                            if (gridUIManager == null) {
                                brightnessVolumeView.mMediaVolumeLayout.setVisibility(0);
                                brightnessVolumeView.setDefaultBrightnessView();
                                break;
                            } else {
                                ((FlexPanelActivity) gridUIManager).returnToMenu();
                                break;
                            }
                        }
                    default:
                        BrightnessVolumeView brightnessVolumeView2 = this.f$0;
                        brightnessVolumeView2.mMediaVolumeLayout.setVisibility(0);
                        if (brightnessVolumeView2.mVolumeSeekBar.getVisibility() != 0) {
                            brightnessVolumeView2.mMediaBrightnessLayout.setVisibility(4);
                            brightnessVolumeView2.mVolumeSeekBar.setVisibility(0);
                            brightnessVolumeView2.mStreamType = AudioManager.semGetActiveStreamType();
                            if (ControlPanelUtils.isClockActivity(brightnessVolumeView2.mContext)) {
                                brightnessVolumeView2.mVolumeSeekBar.setEnabled(false);
                                brightnessVolumeView2.mVolumeSeekBar.setAlpha(0.4f);
                                brightnessVolumeView2.mVolumeIcon.setAlpha(0.4f);
                            } else {
                                brightnessVolumeView2.setVolumeSeekBar(brightnessVolumeView2.mStreamType);
                                brightnessVolumeView2.setVolumeIcon(brightnessVolumeView2.mStreamType);
                            }
                            if (brightnessVolumeView2.mAccessibilityManager.semIsScreenReaderEnabled()) {
                                brightnessVolumeView2.mVolumeSeekBar.semRequestAccessibilityFocus();
                            }
                            brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mVolumeRunnable, true);
                            break;
                        } else {
                            brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mVolumeRunnable, false);
                            brightnessVolumeView2.mVolumeSeekBar.setVisibility(4);
                            GridUIManager gridUIManager2 = brightnessVolumeView2.mGridUIManager;
                            if (gridUIManager2 == null) {
                                brightnessVolumeView2.mMediaBrightnessLayout.setVisibility(0);
                                brightnessVolumeView2.setDefaultVolumeIcon();
                                break;
                            } else {
                                ((FlexPanelActivity) gridUIManager2).returnToMenu();
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
            handler.postDelayed(runnable, 2000L);
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

    /* JADX WARN: Removed duplicated region for block: B:61:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setVolumeIcon(int i) {
        boolean z;
        ColorStateList colorStateList = ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, this.mContext);
        this.mStreamType = i;
        this.mGetRingerMode = this.mAudioManager.getRingerMode();
        StringBuilder sb = new StringBuilder("BrightnessVolumeView setVolumeIcon mStreamType : ");
        sb.append(this.mStreamType);
        sb.append(", mGetRingerMode : ");
        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mGetRingerMode, "BrightnessVolumeView");
        int i2 = this.mStreamType;
        if (i2 == 10) {
            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_accessibility));
        }
        if (i2 == 16) {
            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_bixby));
            return;
        }
        switch (i2) {
            case 0:
            case 6:
                this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_call));
                break;
            case 1:
                if (this.mAudioManager.getStreamVolume(1) == 0) {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_system_sound_mute));
                    break;
                } else {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_system_sound));
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
                    int i3 = this.mGetRingerMode;
                    if (i3 == 1) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_vibrate));
                        IconMotion iconMotion = this.mMotion;
                        ImageView imageView = this.mVolumeIcon;
                        if ((this.mCurrentRingerMode == this.mGetRingerMode ? 0 : 1) != 0) {
                            Resources resources = iconMotion.mResources;
                            float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_vibrate_init);
                            float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.flex_panel_volume_vibrate_offset);
                            float f = -dimensionPixelSize;
                            Animator vibrationAnimator = IconMotion.getVibrationAnimator(0.0f, f, 60, imageView);
                            float f2 = dimensionPixelSize - dimensionPixelSize2;
                            Animator vibrationAnimator2 = IconMotion.getVibrationAnimator(f, f2, 80, imageView);
                            float f3 = -(dimensionPixelSize - (dimensionPixelSize2 * 2.0f));
                            Animator vibrationAnimator3 = IconMotion.getVibrationAnimator(f2, f3, 100, imageView);
                            Animator vibrationAnimator4 = IconMotion.getVibrationAnimator(f3, 0.0f, 120, imageView);
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
                    } else if (i3 == 0) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_mute));
                    }
                    r3 = -1;
                } else {
                    this.mMediaVolumeAnimatedIconLayout.setVisibility(0);
                    this.mVolumeIcon.setVisibility(4);
                    if (this.mAudioManager.getStreamVolume(2) > 0 && this.mAudioManager.getStreamVolume(2) <= 3) {
                        this.mMotion.startMinAnimation(this.mStreamType, 1, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 1);
                    } else if (this.mAudioManager.getStreamVolume(2) <= 3 || this.mAudioManager.getStreamVolume(2) > 7) {
                        this.mMotion.startMaxAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 3);
                        r3 = 3;
                    } else {
                        this.mMotion.startMidAnimation(this.mStreamType, 2, this.mNote, this.mWaveS, this.mWaveL, this.mVolumeIcon, this.mMute, this.mSplash, this.mCurrentRingtoneIconState == 2);
                        r3 = 2;
                    }
                }
                this.mCurrentRingtoneIconState = r3;
                this.mCurrentRingerMode = this.mGetRingerMode;
                break;
            case 3:
                boolean z2 = this.mAudioManager.semIsSafeMediaVolumeDeviceOn() && !(this.mAudioManager.semIsFmRadioActive() && this.mAudioManager.semGetRadioOutputPath() == 2);
                ColorStateList colorStateList2 = ContextCompat.getColorStateList(R.color.seekbar_color_expand, this.mContext);
                ColorStateList colorStateList3 = ContextCompat.getColorStateList(R.color.seekbar_background_color_expand, this.mContext);
                if (z2) {
                    int i4 = this.mStreamType;
                    int semGetEarProtectLimit = ((AudioManager.semGetEarProtectLimit() - 1) * 100) + 9;
                    if (semGetEarProtectLimit > 0 && semGetEarProtectLimit < this.mAudioManager.getStreamVolume(i4) * 100) {
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
                int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("setVolumeIcon currentDeviceType : ", semGetCurrentDeviceType, "BrightnessVolumeView");
                for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
                    if (audioDeviceInfo.getType() == 8 || audioDeviceInfo.getType() == 7) {
                        z = true;
                        if (z || !(semGetCurrentDeviceType == 8 || semGetCurrentDeviceType == 7)) {
                            this.mMediaVolumeAnimatedIconLayout.setVisibility(0);
                            this.mVolumeIcon.setVisibility(4);
                            if (this.mAudioManager.getStreamVolume(3) != 0) {
                                this.mMotion.startMuteAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 0);
                                r3 = 0;
                            } else if (this.mAudioManager.getStreamVolume(3) > 0 && this.mAudioManager.getStreamVolume(3) <= 3) {
                                this.mMotion.startMinAnimation(this.mStreamType, 1, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 1);
                            } else if (this.mAudioManager.getStreamVolume(3) <= 3 || this.mAudioManager.getStreamVolume(3) > 7) {
                                this.mMotion.startMaxAnimation(this.mStreamType, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 3);
                                r3 = 3;
                            } else {
                                this.mMotion.startMidAnimation(this.mStreamType, 2, this.mNote, this.mWaveS, this.mWaveL, null, this.mMute, this.mSplash, this.mCurrentMediaIconState == 2);
                                r3 = 2;
                            }
                        } else {
                            this.mVolumeIcon.setVisibility(0);
                            this.mMediaVolumeAnimatedIconLayout.setVisibility(4);
                            this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_audio_bluetooth));
                            r3 = -1;
                        }
                        this.mCurrentMediaIconState = r3;
                        break;
                    }
                }
                z = false;
                if (z) {
                }
                this.mMediaVolumeAnimatedIconLayout.setVisibility(0);
                this.mVolumeIcon.setVisibility(4);
                if (this.mAudioManager.getStreamVolume(3) != 0) {
                }
                this.mCurrentMediaIconState = r3;
                break;
            case 4:
                if (this.mAudioManager.getStreamVolume(4) == 0) {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_mute));
                    break;
                } else {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_ringtone_sound));
                    break;
                }
            case 5:
                if (this.mAudioManager.getStreamVolume(5) == 0) {
                    int i5 = this.mGetRingerMode;
                    if (i5 == 1) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_vibrate));
                        break;
                    } else if (i5 == 0) {
                        this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_mute));
                        break;
                    }
                } else {
                    this.mVolumeIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_notifications_sound));
                    break;
                }
                break;
        }
    }

    public final void setVolumeSeekBar(int i) {
        int streamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
        this.mStreamType = i;
        this.mGetRingerMode = this.mAudioManager.getRingerMode();
        StringBuilder sb = new StringBuilder("setVolumeSeekBar mStreamType : ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mStreamType, ", volumeValue : ", streamVolume, ", mGetRingerMode : ");
        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mGetRingerMode, "BrightnessVolumeView");
        int i2 = this.mStreamType;
        if (i2 == 0 || i2 == 6) {
            this.mVolumeSeekBar.setMax((this.mAudioManager.getStreamMaxVolume(i2) * 10) + 1);
            this.mVolumeSeekBar.setProgress((streamVolume * 10) + 1);
        } else {
            this.mVolumeSeekBar.setMax(this.mAudioManager.getStreamMaxVolume(i2) * 10);
            this.mVolumeSeekBar.setProgress(streamVolume * 10);
        }
        this.mVolumeSeekBar.mOnSeekBarChangeListener = this.mVolumeSeekBarChangeListener;
    }
}
