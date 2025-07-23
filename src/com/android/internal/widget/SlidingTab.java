package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class SlidingTab extends ViewGroup {
    private static final int ANIM_DURATION = 250;
    private static final int ANIM_TARGET_TIME = 500;
    private static final boolean DBG = false;
    private static final int HORIZONTAL = 0;
    private static final String LOG_TAG = "SlidingTab";
    private static final float THRESHOLD = 0.6666667f;
    private static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    private static final int TRACKING_MARGIN = 50;
    private static final int VERTICAL = 1;
    private static final long VIBRATE_LONG = 40;
    private static final long VIBRATE_SHORT = 30;
    private boolean mAnimating;
    private final Animation.AnimationListener mAnimationDoneListener;
    private Slider mCurrentSlider;
    private final float mDensity;
    private int mGrabbedState;
    private boolean mHoldLeftOnTransition;
    private boolean mHoldRightOnTransition;
    private final Slider mLeftSlider;
    private OnTriggerListener mOnTriggerListener;
    private final int mOrientation;
    private Slider mOtherSlider;
    private final Slider mRightSlider;
    private float mThreshold;
    private final Rect mTmpRect;
    private boolean mTracking;
    private boolean mTriggered;
    private Vibrator mVibrator;

    public interface OnTriggerListener {
        public static final int LEFT_HANDLE = 1;
        public static final int NO_HANDLE = 0;
        public static final int RIGHT_HANDLE = 2;

        void onGrabbedStateChange(View view, int i);

        void onTrigger(View view, int i);
    }

    private static class Slider {
        public static final int ALIGN_BOTTOM = 3;
        public static final int ALIGN_LEFT = 0;
        public static final int ALIGN_RIGHT = 1;
        public static final int ALIGN_TOP = 2;
        public static final int ALIGN_UNKNOWN = 4;
        private static final int STATE_ACTIVE = 2;
        private static final int STATE_NORMAL = 0;
        private static final int STATE_PRESSED = 1;
        private int alignment_value;
        private final ImageView tab;
        private final ImageView target;
        private final TextView text;
        private int currentState = 0;
        private int alignment = 4;

        Slider(ViewGroup viewGroup, int i, int i2, int i3) {
            ImageView imageView = new ImageView(viewGroup.getContext());
            this.tab = imageView;
            imageView.setBackgroundResource(i);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            TextView textView = new TextView(viewGroup.getContext());
            this.text = textView;
            textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            textView.setBackgroundResource(i2);
            textView.setTextAppearance(viewGroup.getContext(), R.style.TextAppearance_SlidingTabNormal);
            ImageView imageView2 = new ImageView(viewGroup.getContext());
            this.target = imageView2;
            imageView2.setImageResource(i3);
            imageView2.setScaleType(ImageView.ScaleType.CENTER);
            imageView2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            imageView2.setVisibility(4);
            viewGroup.addView(imageView2);
            viewGroup.addView(imageView);
            viewGroup.addView(textView);
        }

        void setIcon(int i) {
            this.tab.setImageResource(i);
        }

        void setTabBackgroundResource(int i) {
            this.tab.setBackgroundResource(i);
        }

        void setBarBackgroundResource(int i) {
            this.text.setBackgroundResource(i);
        }

        void setHintText(int i) {
            this.text.setText(i);
        }

        void hide() {
            int i;
            int i2;
            int top;
            int i3;
            int left;
            int i4 = this.alignment;
            int i5 = 0;
            boolean z = i4 == 0 || i4 == 1;
            if (z) {
                if (i4 == 0) {
                    i3 = this.alignment_value;
                    left = this.tab.getRight();
                } else {
                    i3 = this.alignment_value;
                    left = this.tab.getLeft();
                }
                i = i3 - left;
            } else {
                i = 0;
            }
            if (!z) {
                if (this.alignment == 2) {
                    i2 = this.alignment_value;
                    top = this.tab.getBottom();
                } else {
                    i2 = this.alignment_value;
                    top = this.tab.getTop();
                }
                i5 = i2 - top;
            }
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, i, 0.0f, i5);
            translateAnimation.setDuration(250L);
            translateAnimation.setFillAfter(true);
            this.tab.startAnimation(translateAnimation);
            this.text.startAnimation(translateAnimation);
            this.target.setVisibility(4);
        }

        void show(boolean z) {
            int i;
            int i2 = 0;
            this.text.setVisibility(0);
            this.tab.setVisibility(0);
            if (z) {
                int i3 = this.alignment;
                boolean z2 = true;
                if (i3 != 0 && i3 != 1) {
                    z2 = false;
                }
                if (z2) {
                    i = i3 == 0 ? this.tab.getWidth() : -this.tab.getWidth();
                } else {
                    i = 0;
                }
                if (!z2) {
                    i2 = this.alignment == 2 ? this.tab.getHeight() : -this.tab.getHeight();
                }
                TranslateAnimation translateAnimation = new TranslateAnimation(-i, 0.0f, -i2, 0.0f);
                translateAnimation.setDuration(250L);
                this.tab.startAnimation(translateAnimation);
                this.text.startAnimation(translateAnimation);
            }
        }

        void setState(int i) {
            this.text.setPressed(i == 1);
            this.tab.setPressed(i == 1);
            if (i == 2) {
                int[] iArr = {16842914};
                if (this.text.getBackground().isStateful()) {
                    this.text.getBackground().setState(iArr);
                }
                if (this.tab.getBackground().isStateful()) {
                    this.tab.getBackground().setState(iArr);
                }
                TextView textView = this.text;
                textView.setTextAppearance(textView.getContext(), R.style.TextAppearance_SlidingTabActive);
            } else {
                TextView textView2 = this.text;
                textView2.setTextAppearance(textView2.getContext(), R.style.TextAppearance_SlidingTabNormal);
            }
            this.currentState = i;
        }

        void showTarget() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500L);
            this.target.startAnimation(alphaAnimation);
            this.target.setVisibility(0);
        }

        void reset(boolean z) {
            int i;
            int i2;
            int bottom;
            int i3;
            int i4;
            int right;
            setState(0);
            this.text.setVisibility(0);
            TextView textView = this.text;
            textView.setTextAppearance(textView.getContext(), R.style.TextAppearance_SlidingTabNormal);
            this.tab.setVisibility(0);
            this.target.setVisibility(4);
            int i5 = this.alignment;
            boolean z2 = true;
            if (i5 != 0 && i5 != 1) {
                z2 = false;
            }
            if (z2) {
                if (i5 == 0) {
                    i4 = this.alignment_value;
                    right = this.tab.getLeft();
                } else {
                    i4 = this.alignment_value;
                    right = this.tab.getRight();
                }
                i = i4 - right;
            } else {
                i = 0;
            }
            if (z2) {
                i3 = 0;
            } else {
                if (this.alignment == 2) {
                    i2 = this.alignment_value;
                    bottom = this.tab.getTop();
                } else {
                    i2 = this.alignment_value;
                    bottom = this.tab.getBottom();
                }
                i3 = i2 - bottom;
            }
            if (z) {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, i, 0.0f, i3);
                translateAnimation.setDuration(250L);
                translateAnimation.setFillAfter(false);
                this.text.startAnimation(translateAnimation);
                this.tab.startAnimation(translateAnimation);
                return;
            }
            if (z2) {
                this.text.offsetLeftAndRight(i);
                this.tab.offsetLeftAndRight(i);
            } else {
                this.text.offsetTopAndBottom(i3);
                this.tab.offsetTopAndBottom(i3);
            }
            this.text.clearAnimation();
            this.tab.clearAnimation();
            this.target.clearAnimation();
        }

        void setTarget(int i) {
            this.target.setImageResource(i);
        }

        void layout(int i, int i2, int i3, int i4, int i5) {
            this.alignment = i5;
            Drawable background = this.tab.getBackground();
            int intrinsicWidth = background.getIntrinsicWidth();
            int intrinsicHeight = background.getIntrinsicHeight();
            Drawable drawable = this.target.getDrawable();
            int intrinsicWidth2 = drawable.getIntrinsicWidth();
            int intrinsicHeight2 = drawable.getIntrinsicHeight();
            int i6 = i3 - i;
            int i7 = i4 - i2;
            float f = i6;
            int i8 = intrinsicWidth / 2;
            int i9 = (((int) (f * SlidingTab.THRESHOLD)) - intrinsicWidth2) + i8;
            int i10 = ((int) (f * 0.3333333f)) - i8;
            int i11 = i6 - intrinsicWidth;
            int i12 = i11 / 2;
            int i13 = i12 + intrinsicWidth;
            if (i5 == 0 || i5 == 1) {
                int i14 = (i7 - intrinsicHeight2) / 2;
                int i15 = i14 + intrinsicHeight2;
                int i16 = (i7 - intrinsicHeight) / 2;
                int i17 = (i7 + intrinsicHeight) / 2;
                if (i5 == 0) {
                    this.tab.layout(0, i16, intrinsicWidth, i17);
                    this.text.layout(0 - i6, i16, 0, i17);
                    this.text.setGravity(5);
                    this.target.layout(i9, i14, i9 + intrinsicWidth2, i15);
                    this.alignment_value = i;
                    return;
                }
                this.tab.layout(i11, i16, i6, i17);
                this.text.layout(i6, i16, i6 + i6, i17);
                this.target.layout(i10, i14, i10 + intrinsicWidth2, i15);
                this.text.setGravity(48);
                this.alignment_value = i3;
                return;
            }
            int i18 = (i6 - intrinsicWidth2) / 2;
            int i19 = (i6 + intrinsicWidth2) / 2;
            float f2 = i7;
            int i20 = intrinsicHeight / 2;
            int i21 = (((int) (f2 * SlidingTab.THRESHOLD)) + i20) - intrinsicHeight2;
            int i22 = ((int) (f2 * 0.3333333f)) - i20;
            if (i5 == 2) {
                this.tab.layout(i12, 0, i13, intrinsicHeight);
                this.text.layout(i12, 0 - i7, i13, 0);
                this.target.layout(i18, i21, i19, i21 + intrinsicHeight2);
                this.alignment_value = i2;
                return;
            }
            this.tab.layout(i12, i7 - intrinsicHeight, i13, i7);
            this.text.layout(i12, i7, i13, i7 + i7);
            this.target.layout(i18, i22, i19, i22 + intrinsicHeight2);
            this.alignment_value = i4;
        }

        public void updateDrawableStates() {
            setState(this.currentState);
        }

        public void measure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            this.tab.measure(View.MeasureSpec.makeSafeMeasureSpec(size, 0), View.MeasureSpec.makeSafeMeasureSpec(size2, 0));
            this.text.measure(View.MeasureSpec.makeSafeMeasureSpec(size, 0), View.MeasureSpec.makeSafeMeasureSpec(size2, 0));
        }

        public int getTabWidth() {
            return this.tab.getMeasuredWidth();
        }

        public int getTabHeight() {
            return this.tab.getMeasuredHeight();
        }

        public void startAnimation(Animation animation, Animation animation2) {
            this.tab.startAnimation(animation);
            this.text.startAnimation(animation2);
        }

        public void hideTarget() {
            this.target.clearAnimation();
            this.target.setVisibility(4);
        }
    }

    public SlidingTab(Context context) {
        this(context, null);
    }

    public SlidingTab(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHoldLeftOnTransition = true;
        this.mHoldRightOnTransition = true;
        this.mGrabbedState = 0;
        this.mTriggered = false;
        this.mAnimationDoneListener = new Animation.AnimationListener() { // from class: com.android.internal.widget.SlidingTab.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SlidingTab.this.onAnimationDone();
            }
        };
        this.mTmpRect = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingTab);
        this.mOrientation = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mLeftSlider = new Slider(this, R.drawable.jog_tab_left_generic, R.drawable.jog_tab_bar_left_generic, R.drawable.jog_tab_target_gray);
        this.mRightSlider = new Slider(this, R.drawable.jog_tab_right_generic, R.drawable.jog_tab_bar_right_generic, R.drawable.jog_tab_target_gray);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int max;
        int max2;
        View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        this.mLeftSlider.measure(i, i2);
        this.mRightSlider.measure(i, i2);
        int tabWidth = this.mLeftSlider.getTabWidth();
        int tabWidth2 = this.mRightSlider.getTabWidth();
        int tabHeight = this.mLeftSlider.getTabHeight();
        int tabHeight2 = this.mRightSlider.getTabHeight();
        if (isHorizontal()) {
            max = Math.max(size, tabWidth + tabWidth2);
            max2 = Math.max(tabHeight, tabHeight2);
        } else {
            max = Math.max(tabWidth, tabHeight2);
            max2 = Math.max(size2, tabHeight + tabHeight2);
        }
        setMeasuredDimension(max, max2);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mAnimating) {
            return false;
        }
        this.mLeftSlider.tab.getHitRect(this.mTmpRect);
        int i = (int) x;
        int i2 = (int) y;
        boolean contains = this.mTmpRect.contains(i, i2);
        this.mRightSlider.tab.getHitRect(this.mTmpRect);
        boolean contains2 = this.mTmpRect.contains(i, i2);
        if (!this.mTracking && !contains && !contains2) {
            return false;
        }
        if (action == 0) {
            this.mTracking = true;
            this.mTriggered = false;
            vibrate(VIBRATE_SHORT);
            if (contains) {
                this.mCurrentSlider = this.mLeftSlider;
                this.mOtherSlider = this.mRightSlider;
                this.mThreshold = isHorizontal() ? 0.6666667f : 0.3333333f;
                setGrabbedState(1);
            } else {
                this.mCurrentSlider = this.mRightSlider;
                this.mOtherSlider = this.mLeftSlider;
                this.mThreshold = isHorizontal() ? 0.3333333f : 0.6666667f;
                setGrabbedState(2);
            }
            this.mCurrentSlider.setState(1);
            this.mCurrentSlider.showTarget();
            this.mOtherSlider.hide();
        }
        return true;
    }

    public void reset(boolean z) {
        this.mLeftSlider.reset(z);
        this.mRightSlider.reset(z);
        if (z) {
            return;
        }
        this.mAnimating = false;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility() && i == 4) {
            reset(false);
        }
        super.setVisibility(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
    
        if (r0 != 3) goto L55;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.mTracking
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L98
            int r0 = r8.getAction()
            float r3 = r8.getX()
            float r4 = r8.getY()
            if (r0 == r2) goto L95
            r5 = 2
            if (r0 == r5) goto L1c
            r3 = 3
            if (r0 == r3) goto L95
            goto L98
        L1c:
            boolean r0 = r7.withinView(r3, r4, r7)
            if (r0 == 0) goto L95
            r7.moveHandle(r3, r4)
            boolean r0 = r7.isHorizontal()
            if (r0 == 0) goto L2c
            goto L2d
        L2c:
            r3 = r4
        L2d:
            float r0 = r7.mThreshold
            boolean r4 = r7.isHorizontal()
            if (r4 == 0) goto L3a
            int r4 = r7.getWidth()
            goto L3e
        L3a:
            int r4 = r7.getHeight()
        L3e:
            float r4 = (float) r4
            float r0 = r0 * r4
            boolean r4 = r7.isHorizontal()
            if (r4 == 0) goto L59
            com.android.internal.widget.SlidingTab$Slider r4 = r7.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r6 = r7.mLeftSlider
            if (r4 != r6) goto L51
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L57
            goto L55
        L51:
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 >= 0) goto L57
        L55:
            r0 = r2
            goto L69
        L57:
            r0 = r1
            goto L69
        L59:
            com.android.internal.widget.SlidingTab$Slider r4 = r7.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r6 = r7.mLeftSlider
            if (r4 != r6) goto L64
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 >= 0) goto L57
            goto L55
        L64:
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L57
            goto L55
        L69:
            boolean r3 = r7.mTriggered
            if (r3 != 0) goto L98
            if (r0 == 0) goto L98
            r7.mTriggered = r2
            r7.mTracking = r1
            com.android.internal.widget.SlidingTab$Slider r0 = r7.mCurrentSlider
            r0.setState(r5)
            com.android.internal.widget.SlidingTab$Slider r0 = r7.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r3 = r7.mLeftSlider
            if (r0 != r3) goto L80
            r0 = r2
            goto L81
        L80:
            r0 = r1
        L81:
            if (r0 == 0) goto L84
            r5 = r2
        L84:
            r7.dispatchTriggerEvent(r5)
            if (r0 == 0) goto L8c
            boolean r0 = r7.mHoldLeftOnTransition
            goto L8e
        L8c:
            boolean r0 = r7.mHoldRightOnTransition
        L8e:
            r7.startAnimating(r0)
            r7.setGrabbedState(r1)
            goto L98
        L95:
            r7.cancelGrab()
        L98:
            boolean r0 = r7.mTracking
            if (r0 != 0) goto La4
            boolean r7 = super.onTouchEvent(r8)
            if (r7 == 0) goto La3
            goto La4
        La3:
            return r1
        La4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.SlidingTab.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void cancelGrab() {
        this.mTracking = false;
        this.mTriggered = false;
        this.mOtherSlider.show(true);
        this.mCurrentSlider.reset(false);
        this.mCurrentSlider.hideTarget();
        this.mCurrentSlider = null;
        this.mOtherSlider = null;
        setGrabbedState(0);
    }

    void startAnimating(final boolean z) {
        final int i;
        this.mAnimating = true;
        Slider slider = this.mCurrentSlider;
        final int i2 = 0;
        if (isHorizontal()) {
            int right = slider.tab.getRight();
            int width = slider.tab.getWidth();
            int left = slider.tab.getLeft();
            int width2 = getWidth();
            if (z) {
                width = 0;
            }
            i2 = slider == this.mRightSlider ? -((right + width2) - width) : ((width2 - left) + width2) - width;
            i = 0;
        } else {
            int top = slider.tab.getTop();
            int bottom = slider.tab.getBottom();
            int height = slider.tab.getHeight();
            int height2 = getHeight();
            if (z) {
                height = 0;
            }
            i = slider == this.mRightSlider ? (top + height2) - height : -(((height2 - bottom) + height2) - height);
        }
        float f = i2;
        float f2 = i;
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, f, 0.0f, f2);
        translateAnimation.setDuration(250L);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);
        TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, f, 0.0f, f2);
        translateAnimation2.setDuration(250L);
        translateAnimation2.setInterpolator(new LinearInterpolator());
        translateAnimation2.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.internal.widget.SlidingTab.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                Animation alphaAnimation;
                if (z) {
                    int i3 = i2;
                    int i4 = i;
                    alphaAnimation = new TranslateAnimation(i3, i3, i4, i4);
                    alphaAnimation.setDuration(1000L);
                    SlidingTab.this.mAnimating = false;
                } else {
                    alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
                    alphaAnimation.setDuration(250L);
                    SlidingTab.this.resetView();
                }
                alphaAnimation.setAnimationListener(SlidingTab.this.mAnimationDoneListener);
                SlidingTab.this.mLeftSlider.startAnimation(alphaAnimation, alphaAnimation);
                SlidingTab.this.mRightSlider.startAnimation(alphaAnimation, alphaAnimation);
            }
        });
        slider.hideTarget();
        slider.startAnimation(translateAnimation, translateAnimation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationDone() {
        resetView();
        this.mAnimating = false;
    }

    private boolean withinView(float f, float f2, View view) {
        if (!isHorizontal() || f2 <= -50.0f || f2 >= view.getHeight() + 50) {
            return !isHorizontal() && f > -50.0f && f < ((float) (view.getWidth() + 50));
        }
        return true;
    }

    private boolean isHorizontal() {
        return this.mOrientation == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetView() {
        this.mLeftSlider.reset(false);
        this.mRightSlider.reset(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.mLeftSlider.layout(i, i2, i3, i4, isHorizontal() ? 0 : 3);
            this.mRightSlider.layout(i, i2, i3, i4, isHorizontal() ? 1 : 2);
        }
    }

    private void moveHandle(float f, float f2) {
        ImageView imageView = this.mCurrentSlider.tab;
        TextView textView = this.mCurrentSlider.text;
        if (isHorizontal()) {
            int left = (((int) f) - imageView.getLeft()) - (imageView.getWidth() / 2);
            imageView.offsetLeftAndRight(left);
            textView.offsetLeftAndRight(left);
        } else {
            int top = (((int) f2) - imageView.getTop()) - (imageView.getHeight() / 2);
            imageView.offsetTopAndBottom(top);
            textView.offsetTopAndBottom(top);
        }
        invalidate();
    }

    public void setLeftTabResources(int i, int i2, int i3, int i4) {
        this.mLeftSlider.setIcon(i);
        this.mLeftSlider.setTarget(i2);
        this.mLeftSlider.setBarBackgroundResource(i3);
        this.mLeftSlider.setTabBackgroundResource(i4);
        this.mLeftSlider.updateDrawableStates();
    }

    public void setLeftHintText(int i) {
        if (isHorizontal()) {
            this.mLeftSlider.setHintText(i);
        }
    }

    public void setRightTabResources(int i, int i2, int i3, int i4) {
        this.mRightSlider.setIcon(i);
        this.mRightSlider.setTarget(i2);
        this.mRightSlider.setBarBackgroundResource(i3);
        this.mRightSlider.setTabBackgroundResource(i4);
        this.mRightSlider.updateDrawableStates();
    }

    public void setRightHintText(int i) {
        if (isHorizontal()) {
            this.mRightSlider.setHintText(i);
        }
    }

    public void setHoldAfterTrigger(boolean z, boolean z2) {
        this.mHoldLeftOnTransition = z;
        this.mHoldRightOnTransition = z2;
    }

    private synchronized void vibrate(long j) {
        if (this.mVibrator == null) {
            this.mVibrator = (Vibrator) getContext().getSystemService(Vibrator.class);
        }
        this.mVibrator.vibrate(VibrationEffect.createOneShot(j, -1), TOUCH_VIBRATION_ATTRIBUTES);
    }

    public void setOnTriggerListener(OnTriggerListener onTriggerListener) {
        this.mOnTriggerListener = onTriggerListener;
    }

    private void dispatchTriggerEvent(int i) {
        vibrate(VIBRATE_LONG);
        OnTriggerListener onTriggerListener = this.mOnTriggerListener;
        if (onTriggerListener != null) {
            onTriggerListener.onTrigger(this, i);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view != this || i == 0 || this.mGrabbedState == 0) {
            return;
        }
        cancelGrab();
    }

    private void setGrabbedState(int i) {
        if (i != this.mGrabbedState) {
            this.mGrabbedState = i;
            OnTriggerListener onTriggerListener = this.mOnTriggerListener;
            if (onTriggerListener != null) {
                onTriggerListener.onGrabbedStateChange(this, i);
            }
        }
    }

    private void log(String str) {
        Log.d(LOG_TAG, str);
    }
}
