package com.android.systemui.statusbar.phone;

import android.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.AlphaOptimizedImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SettingsButton extends AlphaOptimizedImageView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ObjectAnimator mAnimator;
    public final RunnableC31323 mLongPressCallback;
    public final float mSlop;
    public boolean mUpToSpeed;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.phone.SettingsButton$3] */
    public SettingsButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLongPressCallback = new Runnable() { // from class: com.android.systemui.statusbar.phone.SettingsButton.3
            @Override // java.lang.Runnable
            public final void run() {
                SettingsButton.this.startAccelSpin();
            }
        };
        this.mSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float f = -this.mSlop;
                if (x < f || y < f || x > getWidth() + this.mSlop || y > getHeight() + this.mSlop) {
                    ObjectAnimator objectAnimator = this.mAnimator;
                    if (objectAnimator != null) {
                        objectAnimator.removeAllListeners();
                        this.mAnimator.cancel();
                        this.mAnimator = null;
                    }
                    this.mUpToSpeed = false;
                    removeCallbacks(this.mLongPressCallback);
                }
            } else if (actionMasked == 3) {
                ObjectAnimator objectAnimator2 = this.mAnimator;
                if (objectAnimator2 != null) {
                    objectAnimator2.removeAllListeners();
                    this.mAnimator.cancel();
                    this.mAnimator = null;
                }
                this.mUpToSpeed = false;
                removeCallbacks(this.mLongPressCallback);
            }
        } else if (this.mUpToSpeed) {
            animate().translationX(((View) getParent().getParent()).getWidth() - getX()).alpha(0.0f).setDuration(350L).setInterpolator(AnimationUtils.loadInterpolator(((ImageView) this).mContext, R.interpolator.accelerate_cubic)).setListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.SettingsButton.1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SettingsButton.this.setAlpha(1.0f);
                    SettingsButton.this.setTranslationX(0.0f);
                    SettingsButton settingsButton = SettingsButton.this;
                    int i = SettingsButton.$r8$clinit;
                    ObjectAnimator objectAnimator3 = settingsButton.mAnimator;
                    if (objectAnimator3 != null) {
                        objectAnimator3.removeAllListeners();
                        settingsButton.mAnimator.cancel();
                        settingsButton.mAnimator = null;
                    }
                    settingsButton.mUpToSpeed = false;
                    settingsButton.removeCallbacks(settingsButton.mLongPressCallback);
                    SettingsButton.this.animate().setListener(null);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            }).start();
        } else {
            ObjectAnimator objectAnimator3 = this.mAnimator;
            if (objectAnimator3 != null) {
                objectAnimator3.removeAllListeners();
                this.mAnimator.cancel();
                this.mAnimator = null;
            }
            this.mUpToSpeed = false;
            removeCallbacks(this.mLongPressCallback);
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void startAccelSpin() {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<SettingsButton, Float>) View.ROTATION, 0.0f, 360.0f);
        this.mAnimator = ofFloat;
        ofFloat.setInterpolator(AnimationUtils.loadInterpolator(((ImageView) this).mContext, R.interpolator.accelerate_quad));
        this.mAnimator.setDuration(750L);
        this.mAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.SettingsButton.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SettingsButton settingsButton = SettingsButton.this;
                ObjectAnimator objectAnimator2 = settingsButton.mAnimator;
                if (objectAnimator2 != null) {
                    objectAnimator2.removeAllListeners();
                    settingsButton.mAnimator.cancel();
                    settingsButton.mAnimator = null;
                }
                settingsButton.performHapticFeedback(0);
                settingsButton.mUpToSpeed = true;
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(settingsButton, (Property<SettingsButton, Float>) View.ROTATION, 0.0f, 360.0f);
                settingsButton.mAnimator = ofFloat2;
                ofFloat2.setInterpolator(Interpolators.LINEAR);
                settingsButton.mAnimator.setDuration(375L);
                settingsButton.mAnimator.setRepeatCount(-1);
                settingsButton.mAnimator.start();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        this.mAnimator.start();
    }
}
