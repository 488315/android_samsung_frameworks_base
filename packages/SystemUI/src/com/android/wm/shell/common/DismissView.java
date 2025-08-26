package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes3.dex */
public class DismissView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAccessibilityTextResId;
    public float mCurrentFontScale;
    public final Rect mDismissArea;
    public int mDismissType;
    public float mElevation;
    public Animation mEnterAnimation;
    public boolean mFocusChangeHapticDisabled;
    public final Rect mHiddenDropTargetArea;
    public Runnable mHideAnimationEnd;
    public ImageView mIconView;
    public Animation mInsideHideAnimation;
    public boolean mIsEnterDismissButton;
    public boolean mIsNightModeOn;
    public Animation mOutsideHideAnimation;
    public Interpolator mSineOut60;
    public TextView mTextView;
    public boolean mVisible;
    public final WindowManager mWindowManager;

    public DismissView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDismissType = 0;
        this.mDismissArea = new Rect();
        this.mAccessibilityTextResId = Integer.MIN_VALUE;
        this.mHiddenDropTargetArea = new Rect();
        this.mWindowManager = (WindowManager) getContext().getSystemService("window");
    }

    public final void hide(Runnable runnable) {
        if (this.mVisible) {
            this.mVisible = false;
            Log.i("DismissView", "hide");
            clearAnimation();
            Animation animation = this.mIsEnterDismissButton ? this.mInsideHideAnimation : this.mOutsideHideAnimation;
            this.mHideAnimationEnd = runnable;
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DismissView.2
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation2) {
                    int i = DismissView.$r8$clinit;
                    Log.i("DismissView", "hide-Run callback");
                    DismissView.this.setVisibility(4);
                    Runnable runnable2 = DismissView.this.mHideAnimationEnd;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation2) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation2) {
                }
            });
            startAnimation(animation);
            return;
        }
        if (this.mIsEnterDismissButton) {
            this.mIsEnterDismissButton = false;
        }
        if (runnable != null) {
            Log.i("DismissView", "already mVisible=false but the callback should be run.");
            Animation animation2 = getAnimation();
            if (animation2 == null || animation2.hasEnded()) {
                runnable.run();
            } else {
                this.mHideAnimationEnd = runnable;
            }
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        boolean z = (configuration.uiMode & 32) != 0;
        if (this.mIsNightModeOn != z) {
            this.mIsNightModeOn = z;
            updateNightModeUI();
        }
        float f = this.mCurrentFontScale;
        float f2 = configuration.fontScale;
        if (f != f2) {
            this.mCurrentFontScale = f2;
            float dimension = getResources().getDimension(R.dimen.dismiss_view_text_size);
            this.mTextView.setTextSize(0, (float) (this.mCurrentFontScale > 1.3f ? Math.floor(Math.ceil(dimension / r0) * 1.2999999523162842d) : Math.ceil(dimension)));
        }
        setDismissType(this.mDismissType);
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.mSineOut60 = AnimationUtils.loadInterpolator(getContext(), R.interpolator.sine_in_out_60);
        this.mTextView = (TextView) findViewById(R.id.dismiss_view_text);
        this.mIconView = (ImageView) findViewById(R.id.dismiss_view_icon);
        this.mElevation = getResources().getDimension(R.dimen.dismiss_elevation);
        this.mCurrentFontScale = this.mTextView.getResources().getConfiguration().fontScale;
        this.mIsNightModeOn = (getResources().getConfiguration().uiMode & 32) != 0;
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_show);
        this.mEnterAnimation = animationLoadAnimation;
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DismissView.1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                int i = DismissView.$r8$clinit;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onAnimationEnd, mVisible="), DismissView.this.mVisible, "DismissView");
                DismissView dismissView = DismissView.this;
                if (dismissView.mVisible) {
                    dismissView.getGlobalVisibleRect(dismissView.mDismissArea);
                    DismissView.this.setVisibility(0);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        this.mInsideHideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.entered_dismiss_view_hide);
        this.mOutsideHideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_hide);
        float dimension = getResources().getDimension(R.dimen.dismiss_view_text_size);
        this.mTextView.setTextSize(0, (float) (this.mCurrentFontScale > 1.3f ? Math.floor(Math.ceil(dimension / r2) * 1.2999999523162842d) : Math.ceil(dimension)));
        updateNightModeUI();
    }

    public final void setDismissType(int i) {
        this.mDismissType = i;
        if (i == 1 || i == 4) {
            this.mTextView.setText(getResources().getString(R.string.dnd_cancel));
            this.mAccessibilityTextResId = R.string.accessibility_drop_now_to_cancel;
        } else if (i == 2 || i == 3) {
            this.mTextView.setText(getResources().getString(R.string.dnd_close));
        }
    }

    public final void updateMarginBottom() {
        Insets insetsIgnoringVisibility = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        int iHeight = 0;
        boolean z = Settings.Global.getInt(getContext().getContentResolver(), "taskbar_style_type", 1) == 0;
        if (Settings.Global.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) == 1 && z) {
            iHeight = this.mHiddenDropTargetArea.height();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.freeform_dismiss_container_margin_bottom) + insetsIgnoringVisibility.bottom + iHeight;
        setLayoutParams(marginLayoutParams);
    }

    public final void updateNightModeUI() {
        setBackgroundResource(0);
        setBackgroundResource(R.drawable.dismiss_view_bg);
        this.mTextView.setTextColor(getResources().getColor(R.color.dismiss_view_text_color));
        this.mIconView.setBackgroundTintList(getResources().getColorStateList(R.color.dismiss_view_icon_color));
    }

    public final void updateResources(boolean z) {
        float f = this.mElevation;
        if (z) {
            f *= 1.15f;
        }
        setElevation(f);
        setBackgroundResource(z ? R.drawable.dismiss_view_bg_over : R.drawable.dismiss_view_bg);
        this.mTextView.setTextColor(getResources().getColor(z ? R.color.dismiss_view_text_color_focused : R.color.dismiss_view_text_color));
        this.mIconView.setBackgroundTintList(getResources().getColorStateList(z ? R.color.dismiss_view_icon_color_focused : R.color.dismiss_view_icon_color));
    }

    public final void updateView(Rect rect) {
        Rect rect2 = this.mDismissArea;
        if (rect2 == null || rect2.isEmpty()) {
            getGlobalVisibleRect(this.mDismissArea);
        }
        updateView(isShown() && Rect.intersects(this.mDismissArea, rect), true);
    }

    public final void updateView(final boolean z, boolean z2) {
        if (this.mIsEnterDismissButton == z) {
            return;
        }
        if (z2) {
            animate().cancel();
            animate().scaleX(z ? 1.15f : 1.0f).scaleY(z ? 1.15f : 1.0f).alpha(z ? 0.8f : 1.0f).setInterpolator(this.mSineOut60).setDuration(250L).withStartAction(new Runnable() { // from class: com.android.wm.shell.common.DismissView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DismissView dismissView = this.f$0;
                    boolean z3 = z;
                    if (!z3) {
                        int i = DismissView.$r8$clinit;
                    } else if (dismissView.mAccessibilityTextResId > 0) {
                        dismissView.announceForAccessibility(dismissView.getResources().getText(dismissView.mAccessibilityTextResId));
                    }
                    dismissView.updateResources(z3);
                }
            }).start();
            if (!this.mFocusChangeHapticDisabled) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
        } else {
            updateResources(z);
            setScaleX(z ? 1.15f : 1.0f);
            setScaleY(z ? 1.15f : 1.0f);
            setAlpha(z ? 0.8f : 1.0f);
        }
        if (this.mDismissType == 1) {
            try {
                WindowManagerGlobal.getWindowManagerService().setDragSurfaceToOverlay(!z);
            } catch (RemoteException e) {
                Log.w("Failed to setDragSurfaceToOverlay.", e.getMessage());
            }
        }
        if (this.mIsEnterDismissButton != z) {
            this.mIsEnterDismissButton = z;
        }
    }
}
