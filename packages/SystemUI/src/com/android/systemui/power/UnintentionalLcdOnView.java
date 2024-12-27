package com.android.systemui.power;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.samsung.android.graphics.spr.animation.interpolator.CubicEaseIn;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class UnintentionalLcdOnView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mCenterXOnScreen;
    public float mCenterYOnScreen;
    public ImageView mCircleImage;
    public final Context mContext;
    public final CubicEaseIn mCubicEaseIn;
    public ValueAnimator mDCircleAnimator;
    public final AnonymousClass2 mDCircleEndListener;
    public float mDCircleRadius;
    public float mDragDistanceY;
    public boolean mIsCoverState;
    public boolean mIsLockerSelected;
    public ImageView mLockerImage;
    public ImageView mLockerRing;
    public final Paint mPaintColor;
    public final RectF mPreviewClipRect;
    public final Path mRoundRectPath;
    public final PathInterpolator mSineInOut33;
    public float mStartY;
    public SecPowerNotificationWarnings mTouchListener;
    public VelocityTracker mVelocityTracker;

    /* renamed from: -$$Nest$monLockerActionMove, reason: not valid java name */
    public static void m2056$$Nest$monLockerActionMove(UnintentionalLcdOnView unintentionalLcdOnView, MotionEvent motionEvent) {
        if (unintentionalLcdOnView.mIsLockerSelected) {
            unintentionalLcdOnView.mDragDistanceY = unintentionalLcdOnView.mStartY - motionEvent.getRawY();
            if (motionEvent.getRawX() < unintentionalLcdOnView.mCenterXOnScreen || motionEvent.getRawX() > unintentionalLcdOnView.mCenterXOnScreen + unintentionalLcdOnView.mLockerRing.getWidth()) {
                unintentionalLcdOnView.onLockerActionUp(motionEvent);
                return;
            }
            if (unintentionalLcdOnView.mDragDistanceY + unintentionalLcdOnView.mLockerRing.getHeight() >= unintentionalLcdOnView.mCircleImage.getHeight()) {
                unintentionalLcdOnView.setPreviewClipRect();
                View[] viewArr = new View[1];
                unintentionalLcdOnView.mLockerImage.setVisibility(4);
                unintentionalLcdOnView.invalidate();
                return;
            }
            if (unintentionalLcdOnView.mLockerImage.getVisibility() == 4) {
                View[] viewArr2 = new View[1];
                unintentionalLcdOnView.mLockerImage.setVisibility(0);
            }
            unintentionalLcdOnView.setPreviewClipRect();
            unintentionalLcdOnView.invalidate();
        }
    }

    public UnintentionalLcdOnView(Context context) {
        this(context, null);
    }

    public final void animateWhiteCircle(boolean z) {
        this.mCircleImage.animate().cancel();
        if (z) {
            this.mCircleImage.animate().scaleX(1.0f).scaleY(1.0f).alpha(0.35f).setDuration(250L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.UnintentionalLcdOnView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    UnintentionalLcdOnView.this.mCircleImage.setVisibility(0);
                }
            });
        } else {
            this.mCircleImage.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(250L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.UnintentionalLcdOnView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    UnintentionalLcdOnView.this.mCircleImage.setVisibility(4);
                    UnintentionalLcdOnView.this.mLockerRing.setVisibility(0);
                }
            });
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mDCircleRadius > 0.0f) {
            canvas.save();
            canvas.translate(0.0f, 0.0f);
            canvas.clipPath(this.mRoundRectPath);
            canvas.drawCircle((this.mLockerRing.getWidth() / 2.0f) + this.mCenterXOnScreen, this.mCenterYOnScreen + this.mLockerRing.getHeight(), this.mDCircleRadius, this.mPaintColor);
            canvas.restore();
        } else {
            float height = this.mIsLockerSelected ? this.mDragDistanceY + this.mLockerRing.getHeight() : this.mDragDistanceY;
            canvas.save();
            canvas.translate(0.0f, 0.0f);
            canvas.clipPath(this.mRoundRectPath);
            canvas.drawCircle((this.mLockerRing.getWidth() / 2.0f) + this.mCenterXOnScreen, this.mCenterYOnScreen + this.mLockerRing.getHeight(), height, this.mPaintColor);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mLockerImage = (ImageView) findViewById(R.id.locker_img_view);
        this.mCircleImage = (ImageView) findViewById(R.id.locker_image_circle);
        this.mLockerRing = (ImageView) findViewById(R.id.locker_image_ring);
        this.mLockerImage.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.power.UnintentionalLcdOnView.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    int action = motionEvent.getAction();
                    if (action != 0) {
                        if (action != 1) {
                            if (action == 2) {
                                UnintentionalLcdOnView.m2056$$Nest$monLockerActionMove(UnintentionalLcdOnView.this, motionEvent);
                                VelocityTracker velocityTracker = UnintentionalLcdOnView.this.mVelocityTracker;
                                if (velocityTracker != null) {
                                    velocityTracker.addMovement(motionEvent);
                                }
                            } else if (action != 6) {
                            }
                        }
                        UnintentionalLcdOnView unintentionalLcdOnView = UnintentionalLcdOnView.this;
                        int i = UnintentionalLcdOnView.$r8$clinit;
                        unintentionalLcdOnView.onLockerActionUp(motionEvent);
                    } else {
                        UnintentionalLcdOnView.this.mLockerRing.setVisibility(4);
                        UnintentionalLcdOnView unintentionalLcdOnView2 = UnintentionalLcdOnView.this;
                        unintentionalLcdOnView2.mDragDistanceY = 0.0f;
                        unintentionalLcdOnView2.mStartY = motionEvent.getRawY();
                        unintentionalLcdOnView2.animateWhiteCircle(true);
                        unintentionalLcdOnView2.mIsLockerSelected = true;
                        UnintentionalLcdOnView unintentionalLcdOnView3 = UnintentionalLcdOnView.this;
                        VelocityTracker velocityTracker2 = unintentionalLcdOnView3.mVelocityTracker;
                        if (velocityTracker2 != null) {
                            velocityTracker2.recycle();
                        }
                        unintentionalLcdOnView3.mVelocityTracker = VelocityTracker.obtain();
                        VelocityTracker velocityTracker3 = UnintentionalLcdOnView.this.mVelocityTracker;
                        if (velocityTracker3 != null) {
                            velocityTracker3.addMovement(motionEvent);
                        }
                        UnintentionalLcdOnView.this.setDCircleAnimator(true);
                    }
                } catch (Exception e) {
                    Log.e("PowerUI.UnintentionalLcdOnView", "Caught Exception In Touch Event", e);
                }
                return true;
            }
        });
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mLockerRing.getLocationOnScreen(new int[2]);
        this.mCenterXOnScreen = r1[0];
        this.mCenterYOnScreen = r1[1];
    }

    public final void onLockerActionUp(MotionEvent motionEvent) {
        Log.d("PowerUI.UnintentionalLcdOnView", "onLockerActionUp - mIsLockerSelected  = " + this.mIsLockerSelected + " mDragDistanceY = " + this.mDragDistanceY + " mLockerImage = " + this.mLockerRing.getHeight() + " mCircleImage = " + this.mCircleImage.getHeight());
        if (this.mIsLockerSelected) {
            boolean z = this.mDragDistanceY + ((float) this.mLockerRing.getHeight()) >= ((float) this.mCircleImage.getHeight());
            animateWhiteCircle(false);
            ValueAnimator valueAnimator = this.mDCircleAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            setDCircleAnimator(false);
            this.mDCircleAnimator.start();
            this.mDragDistanceY = 0.0f;
            this.mIsLockerSelected = false;
            if (!z || motionEvent.getRawX() < this.mCenterXOnScreen || motionEvent.getRawX() > this.mCenterXOnScreen + this.mLockerRing.getWidth()) {
                Log.d("PowerUI.UnintentionalLcdOnView", "showDescription of unintentional locker");
                this.mLockerImage.setAlpha(1.0f);
                this.mLockerImage.setVisibility(0);
            } else {
                Log.d("PowerUI.UnintentionalLcdOnView", "drag distance > WhiteCircleRadius");
                SecPowerNotificationWarnings secPowerNotificationWarnings = this.mTouchListener;
                if (secPowerNotificationWarnings != null) {
                    secPowerNotificationWarnings.dismissUnintentionalLcdOnWindow();
                }
                this.mContext.sendBroadcast(new Intent("com.samsung.intent.action.KSO_CLICK_OK"));
            }
        }
    }

    public final void setCoverState(boolean z) {
        this.mIsCoverState = z;
        ((TextView) findViewById(R.id.unintentional_body)).setText(this.mIsCoverState ? this.mContext.getResources().getString(R.string.unintentional_lcd_on_alert_window_body_drag_to_unlock) : DeviceType.isTablet() ? this.mContext.getResources().getString(R.string.unintentional_lcd_on_alert_window_body_tablet) : this.mContext.getResources().getString(R.string.unintentional_lcd_on_alert_window_body));
    }

    public final void setDCircleAnimator(boolean z) {
        if (!z) {
            this.mDCircleRadius = this.mDragDistanceY;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mDCircleRadius, 0.0f);
        this.mDCircleAnimator = ofFloat;
        ofFloat.setDuration(100L);
        ofFloat.setInterpolator(z ? this.mSineInOut33 : this.mCubicEaseIn);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.power.UnintentionalLcdOnView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UnintentionalLcdOnView.this.mDCircleRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                UnintentionalLcdOnView.this.invalidate();
            }
        });
        ofFloat.addListener(this.mDCircleEndListener);
    }

    public final void setPreviewClipRect() {
        this.mPreviewClipRect.set(this.mCenterXOnScreen, (this.mCenterYOnScreen + this.mLockerRing.getHeight()) - this.mCircleImage.getHeight(), this.mCenterXOnScreen + this.mLockerRing.getWidth(), this.mCenterYOnScreen + this.mLockerRing.getHeight());
        this.mRoundRectPath.addRoundRect(this.mPreviewClipRect, 1000.0f, 1000.0f, Path.Direction.CW);
    }

    public UnintentionalLcdOnView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.power.UnintentionalLcdOnView$2] */
    public UnintentionalLcdOnView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCircleImage = null;
        this.mLockerImage = null;
        this.mLockerRing = null;
        this.mDragDistanceY = 0.0f;
        this.mPreviewClipRect = new RectF();
        this.mRoundRectPath = new Path();
        this.mIsLockerSelected = false;
        this.mDCircleEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.power.UnintentionalLcdOnView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                UnintentionalLcdOnView.this.mDCircleAnimator = null;
            }
        };
        this.mContext = context;
        this.mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        this.mCubicEaseIn = new CubicEaseIn();
        Paint paint = new Paint();
        this.mPaintColor = paint;
        paint.setColor(context.getColor(R.color.accidental_touch_rectngle_background));
        setWillNotDraw(false);
    }
}
