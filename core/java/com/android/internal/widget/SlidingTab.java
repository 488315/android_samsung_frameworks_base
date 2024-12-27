package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
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

public class SlidingTab extends ViewGroup {
    private static final int ANIM_DURATION = 250;
    private static final int ANIM_TARGET_TIME = 500;
    private static final boolean DBG = false;
    private static final int HORIZONTAL = 0;
    private static final String LOG_TAG = "SlidingTab";
    private static final float THRESHOLD = 0.6666667f;
    private static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(18);
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

        Slider(ViewGroup parent, int tabId, int barId, int targetId) {
            this.tab = new ImageView(parent.getContext());
            this.tab.setBackgroundResource(tabId);
            this.tab.setScaleType(ImageView.ScaleType.CENTER);
            this.tab.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.text = new TextView(parent.getContext());
            this.text.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            this.text.setBackgroundResource(barId);
            this.text.setTextAppearance(
                    parent.getContext(), R.style.TextAppearance_SlidingTabNormal);
            this.target = new ImageView(parent.getContext());
            this.target.setImageResource(targetId);
            this.target.setScaleType(ImageView.ScaleType.CENTER);
            this.target.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.target.setVisibility(4);
            parent.addView(this.target);
            parent.addView(this.tab);
            parent.addView(this.text);
        }

        void setIcon(int iconId) {
            this.tab.setImageResource(iconId);
        }

        void setTabBackgroundResource(int tabId) {
            this.tab.setBackgroundResource(tabId);
        }

        void setBarBackgroundResource(int barId) {
            this.text.setBackgroundResource(barId);
        }

        void setHintText(int resId) {
            this.text.setText(resId);
        }

        void hide() {
            int dx;
            int dy = 0;
            boolean horiz = this.alignment == 0 || this.alignment == 1;
            if (horiz) {
                dx =
                        this.alignment == 0
                                ? this.alignment_value - this.tab.getRight()
                                : this.alignment_value - this.tab.getLeft();
            } else {
                dx = 0;
            }
            if (!horiz) {
                dy =
                        this.alignment == 2
                                ? this.alignment_value - this.tab.getBottom()
                                : this.alignment_value - this.tab.getTop();
            }
            Animation trans = new TranslateAnimation(0.0f, dx, 0.0f, dy);
            trans.setDuration(250L);
            trans.setFillAfter(true);
            this.tab.startAnimation(trans);
            this.text.startAnimation(trans);
            this.target.setVisibility(4);
        }

        void show(boolean animate) {
            this.text.setVisibility(0);
            this.tab.setVisibility(0);
            if (animate) {
                boolean z = true;
                if (this.alignment != 0 && this.alignment != 1) {
                    z = false;
                }
                boolean horiz = z;
                int dx =
                        horiz
                                ? this.alignment == 0 ? this.tab.getWidth() : -this.tab.getWidth()
                                : 0;
                int dy =
                        horiz
                                ? 0
                                : this.alignment == 2
                                        ? this.tab.getHeight()
                                        : -this.tab.getHeight();
                Animation trans = new TranslateAnimation(-dx, 0.0f, -dy, 0.0f);
                trans.setDuration(250L);
                this.tab.startAnimation(trans);
                this.text.startAnimation(trans);
            }
        }

        void setState(int state) {
            this.text.setPressed(state == 1);
            this.tab.setPressed(state == 1);
            if (state == 2) {
                int[] activeState = {16842914};
                if (this.text.getBackground().isStateful()) {
                    this.text.getBackground().setState(activeState);
                }
                if (this.tab.getBackground().isStateful()) {
                    this.tab.getBackground().setState(activeState);
                }
                this.text.setTextAppearance(
                        this.text.getContext(), R.style.TextAppearance_SlidingTabActive);
            } else {
                this.text.setTextAppearance(
                        this.text.getContext(), R.style.TextAppearance_SlidingTabNormal);
            }
            this.currentState = state;
        }

        void showTarget() {
            AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
            alphaAnim.setDuration(500L);
            this.target.startAnimation(alphaAnim);
            this.target.setVisibility(0);
        }

        void reset(boolean animate) {
            int dx;
            int dy;
            setState(0);
            this.text.setVisibility(0);
            this.text.setTextAppearance(
                    this.text.getContext(), R.style.TextAppearance_SlidingTabNormal);
            this.tab.setVisibility(0);
            this.target.setVisibility(4);
            boolean z = true;
            if (this.alignment != 0 && this.alignment != 1) {
                z = false;
            }
            boolean horiz = z;
            if (!horiz) {
                dx = 0;
            } else {
                dx =
                        this.alignment == 0
                                ? this.alignment_value - this.tab.getLeft()
                                : this.alignment_value - this.tab.getRight();
            }
            if (horiz) {
                dy = 0;
            } else {
                dy =
                        this.alignment == 2
                                ? this.alignment_value - this.tab.getTop()
                                : this.alignment_value - this.tab.getBottom();
            }
            if (animate) {
                TranslateAnimation trans = new TranslateAnimation(0.0f, dx, 0.0f, dy);
                trans.setDuration(250L);
                trans.setFillAfter(false);
                this.text.startAnimation(trans);
                this.tab.startAnimation(trans);
                return;
            }
            if (horiz) {
                this.text.offsetLeftAndRight(dx);
                this.tab.offsetLeftAndRight(dx);
            } else {
                this.text.offsetTopAndBottom(dy);
                this.tab.offsetTopAndBottom(dy);
            }
            this.text.clearAnimation();
            this.tab.clearAnimation();
            this.target.clearAnimation();
        }

        void setTarget(int targetId) {
            this.target.setImageResource(targetId);
        }

        void layout(int l, int t, int r, int b, int alignment) {
            int handleWidth;
            int targetWidth;
            int parentWidth;
            int leftTarget;
            int rightTarget;
            this.alignment = alignment;
            Drawable tabBackground = this.tab.getBackground();
            int handleWidth2 = tabBackground.getIntrinsicWidth();
            int handleHeight = tabBackground.getIntrinsicHeight();
            Drawable targetDrawable = this.target.getDrawable();
            int targetWidth2 = targetDrawable.getIntrinsicWidth();
            int targetHeight = targetDrawable.getIntrinsicHeight();
            int parentWidth2 = r - l;
            int parentHeight = b - t;
            int leftTarget2 =
                    (((int) (parentWidth2 * SlidingTab.THRESHOLD)) - targetWidth2)
                            + (handleWidth2 / 2);
            int rightTarget2 = ((int) (parentWidth2 * 0.3333333f)) - (handleWidth2 / 2);
            int left = (parentWidth2 - handleWidth2) / 2;
            int right = left + handleWidth2;
            if (alignment == 0) {
                handleWidth = handleWidth2;
                targetWidth = targetWidth2;
                parentWidth = parentWidth2;
                leftTarget = leftTarget2;
                rightTarget = rightTarget2;
            } else {
                if (alignment != 1) {
                    int targetLeft = (parentWidth2 - targetWidth2) / 2;
                    int rightTarget3 = (parentWidth2 + targetWidth2) / 2;
                    int top =
                            (((int) (parentHeight * SlidingTab.THRESHOLD)) + (handleHeight / 2))
                                    - targetHeight;
                    int bottom = ((int) (parentHeight * 0.3333333f)) - (handleHeight / 2);
                    if (alignment != 2) {
                        this.tab.layout(left, parentHeight - handleHeight, right, parentHeight);
                        this.text.layout(left, parentHeight, right, parentHeight + parentHeight);
                        this.target.layout(targetLeft, bottom, rightTarget3, bottom + targetHeight);
                        this.alignment_value = b;
                        return;
                    }
                    this.tab.layout(left, 0, right, handleHeight);
                    this.text.layout(left, 0 - parentHeight, right, 0);
                    this.target.layout(targetLeft, top, rightTarget3, top + targetHeight);
                    this.alignment_value = t;
                    return;
                }
                handleWidth = handleWidth2;
                targetWidth = targetWidth2;
                parentWidth = parentWidth2;
                leftTarget = leftTarget2;
                rightTarget = rightTarget2;
            }
            int targetTop = (parentHeight - targetHeight) / 2;
            int targetBottom = targetTop + targetHeight;
            int top2 = (parentHeight - handleHeight) / 2;
            int bottom2 = (parentHeight + handleHeight) / 2;
            if (alignment != 0) {
                int parentWidth3 = parentWidth;
                this.tab.layout(parentWidth - handleWidth, top2, parentWidth3, bottom2);
                this.text.layout(parentWidth3, top2, parentWidth3 + parentWidth3, bottom2);
                this.target.layout(rightTarget, targetTop, rightTarget + targetWidth, targetBottom);
                this.text.setGravity(48);
                this.alignment_value = r;
                return;
            }
            this.tab.layout(0, top2, handleWidth, bottom2);
            this.text.layout(0 - parentWidth, top2, 0, bottom2);
            this.text.setGravity(5);
            this.target.layout(leftTarget, targetTop, leftTarget + targetWidth, targetBottom);
            this.alignment_value = l;
        }

        public void updateDrawableStates() {
            setState(this.currentState);
        }

        public void measure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = View.MeasureSpec.getSize(widthMeasureSpec);
            int height = View.MeasureSpec.getSize(heightMeasureSpec);
            this.tab.measure(
                    View.MeasureSpec.makeSafeMeasureSpec(width, 0),
                    View.MeasureSpec.makeSafeMeasureSpec(height, 0));
            this.text.measure(
                    View.MeasureSpec.makeSafeMeasureSpec(width, 0),
                    View.MeasureSpec.makeSafeMeasureSpec(height, 0));
        }

        public int getTabWidth() {
            return this.tab.getMeasuredWidth();
        }

        public int getTabHeight() {
            return this.tab.getMeasuredHeight();
        }

        public void startAnimation(Animation anim1, Animation anim2) {
            this.tab.startAnimation(anim1);
            this.text.startAnimation(anim2);
        }

        public void hideTarget() {
            this.target.clearAnimation();
            this.target.setVisibility(4);
        }
    }

    public SlidingTab(Context context) {
        this(context, null);
    }

    public SlidingTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHoldLeftOnTransition = true;
        this.mHoldRightOnTransition = true;
        this.mGrabbedState = 0;
        this.mTriggered = false;
        this.mAnimationDoneListener =
                new Animation
                        .AnimationListener() { // from class:
                                               // com.android.internal.widget.SlidingTab.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {}

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {}

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        SlidingTab.this.onAnimationDone();
                    }
                };
        this.mTmpRect = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingTab);
        this.mOrientation = a.getInt(0, 0);
        a.recycle();
        Resources r = getResources();
        this.mDensity = r.getDisplayMetrics().density;
        this.mLeftSlider =
                new Slider(
                        this,
                        R.drawable.jog_tab_left_generic,
                        R.drawable.jog_tab_bar_left_generic,
                        R.drawable.jog_tab_target_gray);
        this.mRightSlider =
                new Slider(
                        this,
                        R.drawable.jog_tab_right_generic,
                        R.drawable.jog_tab_bar_right_generic,
                        R.drawable.jog_tab_target_gray);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
        View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
        this.mLeftSlider.measure(widthMeasureSpec, heightMeasureSpec);
        this.mRightSlider.measure(widthMeasureSpec, heightMeasureSpec);
        int leftTabWidth = this.mLeftSlider.getTabWidth();
        int rightTabWidth = this.mRightSlider.getTabWidth();
        int leftTabHeight = this.mLeftSlider.getTabHeight();
        int rightTabHeight = this.mRightSlider.getTabHeight();
        if (isHorizontal()) {
            width = Math.max(widthSpecSize, leftTabWidth + rightTabWidth);
            height = Math.max(leftTabHeight, rightTabHeight);
        } else {
            width = Math.max(leftTabWidth, rightTabHeight);
            height = Math.max(heightSpecSize, leftTabHeight + rightTabHeight);
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if (this.mAnimating) {
            return false;
        }
        View leftHandle = this.mLeftSlider.tab;
        leftHandle.getHitRect(this.mTmpRect);
        boolean leftHit = this.mTmpRect.contains((int) x, (int) y);
        View rightHandle = this.mRightSlider.tab;
        rightHandle.getHitRect(this.mTmpRect);
        boolean rightHit = this.mTmpRect.contains((int) x, (int) y);
        if (!this.mTracking && !leftHit && !rightHit) {
            return false;
        }
        switch (action) {
            case 0:
                this.mTracking = true;
                this.mTriggered = false;
                vibrate(VIBRATE_SHORT);
                if (leftHit) {
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
            default:
                return true;
        }
    }

    public void reset(boolean animate) {
        this.mLeftSlider.reset(animate);
        this.mRightSlider.reset(animate);
        if (!animate) {
            this.mAnimating = false;
        }
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        if (visibility != getVisibility() && visibility == 4) {
            reset(false);
        }
        super.setVisibility(visibility);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0051, code lost:

       r7 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0064, code lost:

       r7 = true;
    */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
            r11 = this;
            boolean r0 = r11.mTracking
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L99
            int r0 = r12.getAction()
            float r3 = r12.getX()
            float r4 = r12.getY()
            switch(r0) {
                case 1: goto L96;
                case 2: goto L17;
                case 3: goto L96;
                default: goto L15;
            }
        L15:
            goto L99
        L17:
            boolean r5 = r11.withinView(r3, r4, r11)
            if (r5 == 0) goto L96
            r11.moveHandle(r3, r4)
            boolean r5 = r11.isHorizontal()
            if (r5 == 0) goto L28
            r5 = r3
            goto L29
        L28:
            r5 = r4
        L29:
            float r6 = r11.mThreshold
            boolean r7 = r11.isHorizontal()
            if (r7 == 0) goto L36
            int r7 = r11.getWidth()
            goto L3a
        L36:
            int r7 = r11.getHeight()
        L3a:
            float r7 = (float) r7
            float r6 = r6 * r7
            boolean r7 = r11.isHorizontal()
            if (r7 == 0) goto L55
            com.android.internal.widget.SlidingTab$Slider r7 = r11.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r8 = r11.mLeftSlider
            if (r7 != r8) goto L4d
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 <= 0) goto L53
            goto L51
        L4d:
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 >= 0) goto L53
        L51:
            r7 = r2
            goto L54
        L53:
            r7 = r1
        L54:
            goto L67
        L55:
            com.android.internal.widget.SlidingTab$Slider r7 = r11.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r8 = r11.mLeftSlider
            if (r7 != r8) goto L60
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 >= 0) goto L66
            goto L64
        L60:
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 <= 0) goto L66
        L64:
            r7 = r2
            goto L67
        L66:
            r7 = r1
        L67:
            boolean r8 = r11.mTriggered
            if (r8 != 0) goto L99
            if (r7 == 0) goto L99
            r11.mTriggered = r2
            r11.mTracking = r1
            com.android.internal.widget.SlidingTab$Slider r8 = r11.mCurrentSlider
            r9 = 2
            r8.setState(r9)
            com.android.internal.widget.SlidingTab$Slider r8 = r11.mCurrentSlider
            com.android.internal.widget.SlidingTab$Slider r10 = r11.mLeftSlider
            if (r8 != r10) goto L7f
            r8 = r2
            goto L80
        L7f:
            r8 = r1
        L80:
            if (r8 == 0) goto L84
            r9 = r2
            goto L85
        L84:
        L85:
            r11.dispatchTriggerEvent(r9)
            if (r8 == 0) goto L8d
            boolean r9 = r11.mHoldLeftOnTransition
            goto L8f
        L8d:
            boolean r9 = r11.mHoldRightOnTransition
        L8f:
            r11.startAnimating(r9)
            r11.setGrabbedState(r1)
            goto L99
        L96:
            r11.cancelGrab()
        L99:
            boolean r0 = r11.mTracking
            if (r0 != 0) goto La3
            boolean r0 = super.onTouchEvent(r12)
            if (r0 == 0) goto La4
        La3:
            r1 = r2
        La4:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.widget.SlidingTab.onTouchEvent(android.view.MotionEvent):boolean");
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

    void startAnimating(final boolean holdAfter) {
        int holdOffset;
        final int dx;
        final int right;
        this.mAnimating = true;
        Slider slider = this.mCurrentSlider;
        Slider slider2 = this.mOtherSlider;
        if (isHorizontal()) {
            int right2 = slider.tab.getRight();
            int width = slider.tab.getWidth();
            int left = slider.tab.getLeft();
            int viewWidth = getWidth();
            holdOffset = holdAfter ? 0 : width;
            dx =
                    slider == this.mRightSlider
                            ? -((right2 + viewWidth) - holdOffset)
                            : ((viewWidth - left) + viewWidth) - holdOffset;
            right = 0;
        } else {
            int top = slider.tab.getTop();
            int bottom = slider.tab.getBottom();
            int height = slider.tab.getHeight();
            int viewHeight = getHeight();
            holdOffset = holdAfter ? 0 : height;
            dx = 0;
            right =
                    slider == this.mRightSlider
                            ? (top + viewHeight) - holdOffset
                            : -(((viewHeight - bottom) + viewHeight) - holdOffset);
        }
        Animation trans1 = new TranslateAnimation(0.0f, dx, 0.0f, right);
        trans1.setDuration(250L);
        trans1.setInterpolator(new LinearInterpolator());
        trans1.setFillAfter(true);
        Animation trans2 = new TranslateAnimation(0.0f, dx, 0.0f, right);
        trans2.setDuration(250L);
        trans2.setInterpolator(new LinearInterpolator());
        trans2.setFillAfter(true);
        trans1.setAnimationListener(
                new Animation
                        .AnimationListener() { // from class:
                                               // com.android.internal.widget.SlidingTab.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        Animation anim;
                        if (holdAfter) {
                            anim = new TranslateAnimation(dx, dx, right, right);
                            anim.setDuration(1000L);
                            SlidingTab.this.mAnimating = false;
                        } else {
                            anim = new AlphaAnimation(0.5f, 1.0f);
                            anim.setDuration(250L);
                            SlidingTab.this.resetView();
                        }
                        anim.setAnimationListener(SlidingTab.this.mAnimationDoneListener);
                        SlidingTab.this.mLeftSlider.startAnimation(anim, anim);
                        SlidingTab.this.mRightSlider.startAnimation(anim, anim);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {}

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {}
                });
        slider.hideTarget();
        slider.startAnimation(trans1, trans2);
    }

    public void onAnimationDone() {
        resetView();
        this.mAnimating = false;
    }

    private boolean withinView(float x, float y, View view) {
        return (isHorizontal() && y > -50.0f && y < ((float) (view.getHeight() + 50)))
                || (!isHorizontal() && x > -50.0f && x < ((float) (view.getWidth() + 50)));
    }

    private boolean isHorizontal() {
        return this.mOrientation == 0;
    }

    public void resetView() {
        this.mLeftSlider.reset(false);
        this.mRightSlider.reset(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            this.mLeftSlider.layout(l, t, r, b, isHorizontal() ? 0 : 3);
            this.mRightSlider.layout(l, t, r, b, isHorizontal() ? 1 : 2);
        }
    }

    private void moveHandle(float x, float y) {
        View handle = this.mCurrentSlider.tab;
        View content = this.mCurrentSlider.text;
        if (isHorizontal()) {
            int deltaX = (((int) x) - handle.getLeft()) - (handle.getWidth() / 2);
            handle.offsetLeftAndRight(deltaX);
            content.offsetLeftAndRight(deltaX);
        } else {
            int deltaY = (((int) y) - handle.getTop()) - (handle.getHeight() / 2);
            handle.offsetTopAndBottom(deltaY);
            content.offsetTopAndBottom(deltaY);
        }
        invalidate();
    }

    public void setLeftTabResources(int iconId, int targetId, int barId, int tabId) {
        this.mLeftSlider.setIcon(iconId);
        this.mLeftSlider.setTarget(targetId);
        this.mLeftSlider.setBarBackgroundResource(barId);
        this.mLeftSlider.setTabBackgroundResource(tabId);
        this.mLeftSlider.updateDrawableStates();
    }

    public void setLeftHintText(int resId) {
        if (isHorizontal()) {
            this.mLeftSlider.setHintText(resId);
        }
    }

    public void setRightTabResources(int iconId, int targetId, int barId, int tabId) {
        this.mRightSlider.setIcon(iconId);
        this.mRightSlider.setTarget(targetId);
        this.mRightSlider.setBarBackgroundResource(barId);
        this.mRightSlider.setTabBackgroundResource(tabId);
        this.mRightSlider.updateDrawableStates();
    }

    public void setRightHintText(int resId) {
        if (isHorizontal()) {
            this.mRightSlider.setHintText(resId);
        }
    }

    public void setHoldAfterTrigger(boolean holdLeft, boolean holdRight) {
        this.mHoldLeftOnTransition = holdLeft;
        this.mHoldRightOnTransition = holdRight;
    }

    private synchronized void vibrate(long duration) {
        if (this.mVibrator == null) {
            this.mVibrator = (Vibrator) getContext().getSystemService(Vibrator.class);
        }
        this.mVibrator.vibrate(
                VibrationEffect.createOneShot(duration, -1), TOUCH_VIBRATION_ATTRIBUTES);
    }

    public void setOnTriggerListener(OnTriggerListener listener) {
        this.mOnTriggerListener = listener;
    }

    private void dispatchTriggerEvent(int whichHandle) {
        vibrate(VIBRATE_LONG);
        if (this.mOnTriggerListener != null) {
            this.mOnTriggerListener.onTrigger(this, whichHandle);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility != 0 && this.mGrabbedState != 0) {
            cancelGrab();
        }
    }

    private void setGrabbedState(int newState) {
        if (newState != this.mGrabbedState) {
            this.mGrabbedState = newState;
            if (this.mOnTriggerListener != null) {
                this.mOnTriggerListener.onGrabbedStateChange(this, this.mGrabbedState);
            }
        }
    }

    private void log(String msg) {
        Log.d(LOG_TAG, msg);
    }
}
