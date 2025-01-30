package com.android.systemui.settings.brightness;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.qs.SecDarkModeEasel;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BrightnessSliderView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public RelativeLayout mBrightnessDetailContainerView;
    public ImageView mBrightnessDetailView;
    public LinearLayout mBrightnessTileLayoutView;
    public final Context mContext;
    public final SecDarkModeEasel mDarkModelEasel;
    public int mDualSeekBarThreshold;
    public boolean mIsCollapsed;
    public boolean mIsMirror;
    public boolean mIsSliderWarning;
    public boolean mIsTouchSlider;
    public BrightnessSliderController$$ExternalSyntheticLambda1 mListener;
    public Gefingerpoken mOnInterceptListener;
    public int mOrientation;
    public float mScale;
    public ToggleSeekBar mSlider;
    public BrightnessBar mToggleDetailListener;
    public boolean mTouchDownDetailView;

    public BrightnessSliderView(Context context) {
        this(context, null);
    }

    public static boolean isTouched(View view, float f, float f2) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        if (f <= iArr[0] || f >= view.getWidth() + r2) {
            return false;
        }
        int i = iArr[1];
        return f2 > ((float) i) && f2 < ((float) (view.getHeight() + i));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        BrightnessSliderController$$ExternalSyntheticLambda1 brightnessSliderController$$ExternalSyntheticLambda1 = this.mListener;
        if (brightnessSliderController$$ExternalSyntheticLambda1 != null) {
            brightnessSliderController$$ExternalSyntheticLambda1.onDispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        boolean z = false;
        if (action != 0) {
            if (action == 1) {
                this.mIsTouchSlider = false;
                if (this.mSlider.mIsDragging || !this.mTouchDownDetailView || !isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY()) || this.mToggleDetailListener == null || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isEmergencyMode()) {
                    this.mTouchDownDetailView = false;
                    ToggleSeekBar toggleSeekBar = this.mSlider;
                    toggleSeekBar.mIsDetailViewTouched = false;
                    toggleSeekBar.mIsDragging = false;
                } else {
                    ImageView imageView = this.mBrightnessDetailView;
                    if (imageView != null) {
                        imageView.setBackgroundColor(0);
                    }
                    BrightnessBar brightnessBar = this.mToggleDetailListener;
                    ImageView imageView2 = brightnessBar.mBrightnessDetailIcon;
                    if (imageView2 != null) {
                        imageView2.setClickable(true);
                        brightnessBar.mBrightnessDetailIcon.setEnabled(true);
                        brightnessBar.mBrightnessDetailIcon.performClick();
                    }
                    this.mTouchDownDetailView = false;
                    ToggleSeekBar toggleSeekBar2 = this.mSlider;
                    toggleSeekBar2.mIsDetailViewTouched = false;
                    toggleSeekBar2.mIsDragging = false;
                    z = true;
                }
            } else if (action == 2) {
                if (!isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY()) || !this.mTouchDownDetailView) {
                    this.mSlider.mIsDragging = true;
                    BrightnessBar brightnessBar2 = this.mToggleDetailListener;
                    if (brightnessBar2 != null && this.mTouchDownDetailView) {
                        ImageView imageView3 = brightnessBar2.mBrightnessDetailIcon;
                        if (imageView3 != null) {
                            imageView3.setClickable(false);
                            brightnessBar2.mBrightnessDetailIcon.setEnabled(false);
                        }
                        this.mSlider.mIsDetailViewTouched = !this.mTouchDownDetailView;
                    }
                }
                z = true;
            } else if (action == 3) {
                this.mIsTouchSlider = false;
            }
        } else if (isTouched(this.mSlider, motionEvent.getRawX(), motionEvent.getRawY()) || isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY())) {
            this.mIsTouchSlider = true;
            if (isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY())) {
                this.mTouchDownDetailView = true;
                ImageView imageView4 = this.mBrightnessDetailView;
                if (imageView4 != null) {
                    imageView4.setBackground(this.mContext.getDrawable(R.drawable.ripple_drawable_20dp));
                    this.mBrightnessDetailView.getBackground().setVisible(false, false);
                }
                this.mSlider.mIsDetailViewTouched = this.mTouchDownDetailView;
            }
        } else if (isTouched(this.mBrightnessTileLayoutView, motionEvent.getRawX(), motionEvent.getRawY())) {
            Log.d("BrightnessSliderView", "ACTION_DOWN: Brightness Layout is touched");
        } else {
            Log.d("BrightnessSliderView", "ACTION_DOWN: Slider and detail not touched");
            z = true;
        }
        if (z) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public float getSliderScaleY() {
        return this.mScale;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDarkModelEasel.updateColors(configuration);
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mOrientation = i2;
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) requireViewById(R.id.slider);
        this.mSlider = toggleSeekBar;
        toggleSeekBar.mAccessibilityLabel = getContentDescription().toString();
        ImageView imageView = (ImageView) requireViewById(R.id.brightness_detail);
        this.mBrightnessDetailView = imageView;
        ViewCompat.setAccessibilityDelegate(imageView, new AccessibilityDelegateCompat(this) { // from class: com.android.systemui.settings.brightness.BrightnessSliderView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
                AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfoCompat.setClassName(Button.class.getName());
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfo.setEnabled(true);
            }
        });
        this.mSlider.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.settings.brightness.BrightnessSliderView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return BrightnessSliderView.isTouched(BrightnessSliderView.this.mBrightnessDetailView, motionEvent.getRawX(), motionEvent.getRawY());
            }
        });
        this.mBrightnessDetailContainerView = (RelativeLayout) requireViewById(R.id.brightness_detail_container);
        this.mBrightnessTileLayoutView = (LinearLayout) requireViewById(R.id.brightness_tile_layout);
        updateSliderResources();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.mOnInterceptListener;
        return gefingerpoken != null ? gefingerpoken.onInterceptTouchEvent(motionEvent) : super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(z);
        }
    }

    public final void setDualSeekBarResources(boolean z) {
        if (z != this.mIsSliderWarning) {
            this.mIsSliderWarning = z;
            if (z) {
                this.mSlider.setProgressDrawable(this.mContext.getDrawable(R.drawable.sec_brightness_progress_warning_drawable));
            }
        }
        if (this.mIsSliderWarning) {
            return;
        }
        if (this.mIsMirror) {
            this.mSlider.setProgressDrawable(this.mContext.getDrawable(R.drawable.sec_brightness_progress_drawable_mirror));
        } else if (this.mIsCollapsed) {
            this.mSlider.setProgressDrawable(this.mContext.getDrawable(R.drawable.sec_brightness_progress_drawable_collapsed));
        } else {
            this.mSlider.setProgressDrawable(this.mContext.getDrawable(R.drawable.sec_brightness_progress_drawable));
        }
    }

    public void setSliderScaleY(float f) {
        if (f != this.mScale) {
            this.mScale = f;
        }
    }

    public final void updateSliderResources() {
        this.mDualSeekBarThreshold = (int) Math.floor((this.mSlider.getMax() * this.mContext.getResources().getInteger(R.integer.sec_brightness_slider_warning_percent)) / 100.0d);
        this.mContext.getResources().getColor(R.color.sec_brightness_dual_slider_background_color, null);
        this.mContext.getResources().getColor(R.color.sec_brightness_dual_slider_foreground_color, null);
        this.mContext.getResources().getColor(R.color.tw_progress_color_control_normal);
        this.mContext.getResources().getColor(R.color.tw_progress_color_control_activated);
        setDualSeekBarResources(this.mDualSeekBarThreshold <= this.mSlider.getProgress());
    }

    public BrightnessSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScale = 1.0f;
        this.mIsTouchSlider = false;
        this.mDarkModelEasel = new SecDarkModeEasel(new SecDarkModeEasel.PictureSubject() { // from class: com.android.systemui.settings.brightness.BrightnessSliderView$$ExternalSyntheticLambda1
            @Override // com.android.systemui.qs.SecDarkModeEasel.PictureSubject
            public final void drawDarkModelPicture() {
                int i = BrightnessSliderView.$r8$clinit;
                BrightnessSliderView brightnessSliderView = BrightnessSliderView.this;
                brightnessSliderView.setDualSeekBarResources(false);
                brightnessSliderView.mBrightnessDetailView.setColorFilter(brightnessSliderView.mContext.getColor(R.color.tw_check_box_tint));
            }
        });
        this.mContext = context;
    }
}
