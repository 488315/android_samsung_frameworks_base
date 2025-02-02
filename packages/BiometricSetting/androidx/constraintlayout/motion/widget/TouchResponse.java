package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R$styleable;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
final class TouchResponse {
    private int mAutoCompleteMode;
    private float mDragScale;
    private float mDragThreshold;
    private int mFlags;
    boolean mIsRotateMode;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mLimitBoundsTo;
    private float mMaxAcceleration;
    private float mMaxVelocity;
    private final MotionLayout mMotionLayout;
    private boolean mMoveWhenScrollAtTop;
    private int mOnTouchUp;
    private int mRotationCenterId;
    private int mSpringBoundary;
    private float mSpringDamping;
    private float mSpringMass;
    private float mSpringStiffness;
    private float mSpringStopThreshold;
    private int mTouchAnchorId;
    private int mTouchAnchorSide;
    private float mTouchAnchorX;
    private float mTouchAnchorY;
    private float mTouchDirectionX;
    private float mTouchDirectionY;
    private int mTouchRegionId;
    private int mTouchSide;
    private static final float[][] TOUCH_SIDES = {new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}, new float[]{0.5f, 1.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}};
    private static final float[][] TOUCH_DIRECTION = {new float[]{0.0f, -1.0f}, new float[]{0.0f, 1.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}};
    private boolean mDragStarted = false;
    private float[] mAnchorDpDt = new float[2];
    private int[] mTempLoc = new int[2];

    /* renamed from: androidx.constraintlayout.motion.widget.TouchResponse$1 */
    final class ViewOnTouchListenerC00821 implements View.OnTouchListener {
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    /* renamed from: androidx.constraintlayout.motion.widget.TouchResponse$2 */
    final class C00832 implements NestedScrollView.OnScrollChangeListener {
    }

    TouchResponse(Context context, MotionLayout motionLayout, XmlPullParser xmlPullParser) {
        this.mTouchAnchorSide = 0;
        this.mTouchSide = 0;
        this.mOnTouchUp = 0;
        this.mTouchAnchorId = -1;
        this.mTouchRegionId = -1;
        this.mLimitBoundsTo = -1;
        this.mTouchAnchorY = 0.5f;
        this.mTouchAnchorX = 0.5f;
        this.mRotationCenterId = -1;
        this.mIsRotateMode = false;
        this.mTouchDirectionX = 0.0f;
        this.mTouchDirectionY = 1.0f;
        this.mMaxVelocity = 4.0f;
        this.mMaxAcceleration = 1.2f;
        this.mMoveWhenScrollAtTop = true;
        this.mDragScale = 1.0f;
        this.mFlags = 0;
        this.mDragThreshold = 10.0f;
        this.mSpringDamping = 10.0f;
        this.mSpringMass = 1.0f;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = 0;
        this.mAutoCompleteMode = 0;
        this.mMotionLayout = motionLayout;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.OnSwipe);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 16) {
                this.mTouchAnchorId = obtainStyledAttributes.getResourceId(index, this.mTouchAnchorId);
            } else if (index == 17) {
                int i2 = obtainStyledAttributes.getInt(index, this.mTouchAnchorSide);
                this.mTouchAnchorSide = i2;
                float[] fArr = TOUCH_SIDES[i2];
                this.mTouchAnchorX = fArr[0];
                this.mTouchAnchorY = fArr[1];
            } else if (index == 1) {
                int i3 = obtainStyledAttributes.getInt(index, this.mTouchSide);
                this.mTouchSide = i3;
                if (i3 < 6) {
                    float[] fArr2 = TOUCH_DIRECTION[i3];
                    this.mTouchDirectionX = fArr2[0];
                    this.mTouchDirectionY = fArr2[1];
                } else {
                    this.mTouchDirectionY = Float.NaN;
                    this.mTouchDirectionX = Float.NaN;
                    this.mIsRotateMode = true;
                }
            } else if (index == 6) {
                this.mMaxVelocity = obtainStyledAttributes.getFloat(index, this.mMaxVelocity);
            } else if (index == 5) {
                this.mMaxAcceleration = obtainStyledAttributes.getFloat(index, this.mMaxAcceleration);
            } else if (index == 7) {
                this.mMoveWhenScrollAtTop = obtainStyledAttributes.getBoolean(index, this.mMoveWhenScrollAtTop);
            } else if (index == 2) {
                this.mDragScale = obtainStyledAttributes.getFloat(index, this.mDragScale);
            } else if (index == 3) {
                this.mDragThreshold = obtainStyledAttributes.getFloat(index, this.mDragThreshold);
            } else if (index == 18) {
                this.mTouchRegionId = obtainStyledAttributes.getResourceId(index, this.mTouchRegionId);
            } else if (index == 9) {
                this.mOnTouchUp = obtainStyledAttributes.getInt(index, this.mOnTouchUp);
            } else if (index == 8) {
                this.mFlags = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == 4) {
                this.mLimitBoundsTo = obtainStyledAttributes.getResourceId(index, 0);
            } else if (index == 10) {
                this.mRotationCenterId = obtainStyledAttributes.getResourceId(index, this.mRotationCenterId);
            } else if (index == 12) {
                this.mSpringDamping = obtainStyledAttributes.getFloat(index, this.mSpringDamping);
            } else if (index == 13) {
                this.mSpringMass = obtainStyledAttributes.getFloat(index, this.mSpringMass);
            } else if (index == 14) {
                this.mSpringStiffness = obtainStyledAttributes.getFloat(index, this.mSpringStiffness);
            } else if (index == 15) {
                this.mSpringStopThreshold = obtainStyledAttributes.getFloat(index, this.mSpringStopThreshold);
            } else if (index == 11) {
                this.mSpringBoundary = obtainStyledAttributes.getInt(index, this.mSpringBoundary);
            } else if (index == 0) {
                this.mAutoCompleteMode = obtainStyledAttributes.getInt(index, this.mAutoCompleteMode);
            }
        }
        obtainStyledAttributes.recycle();
    }

    final float dot(float f, float f2) {
        return (f2 * this.mTouchDirectionY) + (f * this.mTouchDirectionX);
    }

    public final int getAutoCompleteMode() {
        return this.mAutoCompleteMode;
    }

    public final int getFlags() {
        return this.mFlags;
    }

    final RectF getLimitBoundsTo(MotionLayout motionLayout, RectF rectF) {
        View findViewById;
        int i = this.mLimitBoundsTo;
        if (i == -1 || (findViewById = motionLayout.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    final float getMaxAcceleration() {
        return this.mMaxAcceleration;
    }

    public final float getMaxVelocity() {
        return this.mMaxVelocity;
    }

    final boolean getMoveWhenScrollAtTop() {
        return this.mMoveWhenScrollAtTop;
    }

    final float getProgressDirection(float f, float f2) {
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, this.mMotionLayout.getProgress(), this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f3 = this.mTouchDirectionX;
        if (f3 != 0.0f) {
            float[] fArr = this.mAnchorDpDt;
            if (fArr[0] == 0.0f) {
                fArr[0] = 1.0E-7f;
            }
            return (f * f3) / fArr[0];
        }
        float[] fArr2 = this.mAnchorDpDt;
        if (fArr2[1] == 0.0f) {
            fArr2[1] = 1.0E-7f;
        }
        return (f2 * this.mTouchDirectionY) / fArr2[1];
    }

    public final int getSpringBoundary() {
        return this.mSpringBoundary;
    }

    public final float getSpringDamping() {
        return this.mSpringDamping;
    }

    public final float getSpringMass() {
        return this.mSpringMass;
    }

    public final float getSpringStiffness() {
        return this.mSpringStiffness;
    }

    public final float getSpringStopThreshold() {
        return this.mSpringStopThreshold;
    }

    final RectF getTouchRegion(ViewGroup viewGroup, RectF rectF) {
        View findViewById;
        int i = this.mTouchRegionId;
        if (i == -1 || (findViewById = viewGroup.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    final int getTouchRegionId() {
        return this.mTouchRegionId;
    }

    final boolean isDragStarted() {
        return this.mDragStarted;
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void processTouchEvent(MotionEvent motionEvent, MotionLayout.MotionTracker motionTracker) {
        char c;
        char c2;
        int i;
        char c3;
        char c4;
        char c5;
        char c6;
        float right;
        float f;
        int top;
        int bottom;
        int i2;
        float f2;
        int i3;
        char c7;
        boolean z = this.mIsRotateMode;
        MotionLayout.TransitionState transitionState = MotionLayout.TransitionState.FINISHED;
        MotionLayout motionLayout = this.mMotionLayout;
        if (!z) {
            MotionLayout.MyTracker myTracker = (MotionLayout.MyTracker) motionTracker;
            VelocityTracker velocityTracker = myTracker.mTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                this.mDragStarted = false;
                return;
            }
            if (action == 1) {
                this.mDragStarted = false;
                VelocityTracker velocityTracker2 = myTracker.mTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.computeCurrentVelocity(1000);
                }
                VelocityTracker velocityTracker3 = myTracker.mTracker;
                float xVelocity = velocityTracker3 != null ? velocityTracker3.getXVelocity() : 0.0f;
                VelocityTracker velocityTracker4 = myTracker.mTracker;
                float yVelocity = velocityTracker4 != null ? velocityTracker4.getYVelocity() : 0.0f;
                float progress = motionLayout.getProgress();
                int i4 = this.mTouchAnchorId;
                if (i4 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i4, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    c2 = 0;
                    c = 1;
                } else {
                    float min = Math.min(motionLayout.getWidth(), motionLayout.getHeight());
                    float[] fArr = this.mAnchorDpDt;
                    c = 1;
                    fArr[1] = this.mTouchDirectionY * min;
                    c2 = 0;
                    fArr[0] = min * this.mTouchDirectionX;
                }
                float f3 = this.mTouchDirectionX;
                float[] fArr2 = this.mAnchorDpDt;
                float f4 = f3 != 0.0f ? xVelocity / fArr2[c2] : yVelocity / fArr2[c];
                float f5 = !Float.isNaN(f4) ? (f4 / 3.0f) + progress : progress;
                if (f5 == 0.0f || f5 == 1.0f || (i = this.mOnTouchUp) == 3) {
                    if (0.0f >= f5 || 1.0f <= f5) {
                        motionLayout.setState(transitionState);
                        return;
                    }
                    return;
                }
                float f6 = ((double) f5) < 0.5d ? 0.0f : 1.0f;
                if (i == 6) {
                    if (progress + f4 < 0.0f) {
                        f4 = Math.abs(f4);
                    }
                    f6 = 1.0f;
                }
                if (this.mOnTouchUp == 7) {
                    if (progress + f4 > 1.0f) {
                        f4 = -Math.abs(f4);
                    }
                    f6 = 0.0f;
                }
                motionLayout.touchAnimateTo(this.mOnTouchUp, f6, f4);
                if (0.0f >= progress || 1.0f <= progress) {
                    motionLayout.setState(transitionState);
                    return;
                }
                return;
            }
            if (action != 2) {
                return;
            }
            float rawY = motionEvent.getRawY() - this.mLastTouchY;
            float rawX = motionEvent.getRawX() - this.mLastTouchX;
            if (Math.abs((this.mTouchDirectionY * rawY) + (this.mTouchDirectionX * rawX)) > this.mDragThreshold || this.mDragStarted) {
                float progress2 = motionLayout.getProgress();
                if (!this.mDragStarted) {
                    this.mDragStarted = true;
                    motionLayout.setProgress(progress2);
                }
                int i5 = this.mTouchAnchorId;
                if (i5 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i5, progress2, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    c4 = 0;
                    c3 = 1;
                } else {
                    float min2 = Math.min(motionLayout.getWidth(), motionLayout.getHeight());
                    float[] fArr3 = this.mAnchorDpDt;
                    c3 = 1;
                    fArr3[1] = this.mTouchDirectionY * min2;
                    c4 = 0;
                    fArr3[0] = min2 * this.mTouchDirectionX;
                }
                float f7 = this.mTouchDirectionX;
                float[] fArr4 = this.mAnchorDpDt;
                if (Math.abs(((this.mTouchDirectionY * fArr4[c3]) + (f7 * fArr4[c4])) * this.mDragScale) < 0.01d) {
                    float[] fArr5 = this.mAnchorDpDt;
                    c5 = 0;
                    fArr5[0] = 0.01f;
                    c6 = 1;
                    fArr5[1] = 0.01f;
                } else {
                    c5 = 0;
                    c6 = 1;
                }
                float max = Math.max(Math.min(progress2 + (this.mTouchDirectionX != 0.0f ? rawX / this.mAnchorDpDt[c5] : rawY / this.mAnchorDpDt[c6]), 1.0f), 0.0f);
                if (this.mOnTouchUp == 6) {
                    max = Math.max(max, 0.01f);
                }
                if (this.mOnTouchUp == 7) {
                    max = Math.min(max, 0.99f);
                }
                float progress3 = motionLayout.getProgress();
                if (max != progress3) {
                    if (progress3 == 0.0f || progress3 == 1.0f) {
                        motionLayout.endTrigger(progress3 == 0.0f);
                    }
                    motionLayout.setProgress(max);
                    VelocityTracker velocityTracker5 = myTracker.mTracker;
                    if (velocityTracker5 != null) {
                        velocityTracker5.computeCurrentVelocity(1000);
                    }
                    VelocityTracker velocityTracker6 = myTracker.mTracker;
                    float xVelocity2 = velocityTracker6 != null ? velocityTracker6.getXVelocity() : 0.0f;
                    VelocityTracker velocityTracker7 = myTracker.mTracker;
                    motionLayout.mLastVelocity = this.mTouchDirectionX != 0.0f ? xVelocity2 / this.mAnchorDpDt[0] : (velocityTracker7 != null ? velocityTracker7.getYVelocity() : 0.0f) / this.mAnchorDpDt[1];
                } else {
                    motionLayout.mLastVelocity = 0.0f;
                }
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                return;
            }
            return;
        }
        MotionLayout.MyTracker myTracker2 = (MotionLayout.MyTracker) motionTracker;
        VelocityTracker velocityTracker8 = myTracker2.mTracker;
        if (velocityTracker8 != null) {
            velocityTracker8.addMovement(motionEvent);
        }
        int action2 = motionEvent.getAction();
        if (action2 == 0) {
            this.mLastTouchX = motionEvent.getRawX();
            this.mLastTouchY = motionEvent.getRawY();
            this.mDragStarted = false;
            return;
        }
        if (action2 != 1) {
            if (action2 != 2) {
                return;
            }
            motionEvent.getRawY();
            motionEvent.getRawX();
            float width = motionLayout.getWidth() / 2.0f;
            float height = motionLayout.getHeight() / 2.0f;
            int i6 = this.mRotationCenterId;
            if (i6 != -1) {
                View findViewById = motionLayout.findViewById(i6);
                motionLayout.getLocationOnScreen(this.mTempLoc);
                height = ((findViewById.getBottom() + findViewById.getTop()) / 2.0f) + this.mTempLoc[1];
                width = this.mTempLoc[0] + ((findViewById.getRight() + findViewById.getLeft()) / 2.0f);
            } else {
                int i7 = this.mTouchAnchorId;
                if (i7 != -1) {
                    if (motionLayout.findViewById(motionLayout.mFrameArrayList.get(motionLayout.findViewById(i7)).getAnimateRelativeTo()) == null) {
                        Log.e("TouchResponse", "could not find view to animate to");
                    } else {
                        motionLayout.getLocationOnScreen(this.mTempLoc);
                        width = this.mTempLoc[0] + ((r14.getRight() + r14.getLeft()) / 2.0f);
                        height = this.mTempLoc[1] + ((r14.getBottom() + r14.getTop()) / 2.0f);
                    }
                }
            }
            float rawX2 = motionEvent.getRawX() - width;
            float rawY2 = motionEvent.getRawY() - height;
            float atan2 = (float) (((Math.atan2(motionEvent.getRawY() - height, motionEvent.getRawX() - width) - Math.atan2(this.mLastTouchY - height, this.mLastTouchX - width)) * 180.0d) / 3.141592653589793d);
            if (atan2 > 330.0f) {
                atan2 -= 360.0f;
            } else if (atan2 < -330.0f) {
                atan2 += 360.0f;
            }
            if (Math.abs(atan2) > 0.01d || this.mDragStarted) {
                float progress4 = motionLayout.getProgress();
                if (!this.mDragStarted) {
                    this.mDragStarted = true;
                    motionLayout.setProgress(progress4);
                }
                int i8 = this.mTouchAnchorId;
                if (i8 != -1) {
                    this.mMotionLayout.getAnchorDpDt(i8, progress4, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                    c7 = 1;
                    this.mAnchorDpDt[1] = (float) Math.toDegrees(r6[1]);
                } else {
                    c7 = 1;
                    this.mAnchorDpDt[1] = 360.0f;
                }
                float max2 = Math.max(Math.min(((atan2 * this.mDragScale) / this.mAnchorDpDt[c7]) + progress4, 1.0f), 0.0f);
                float progress5 = motionLayout.getProgress();
                if (max2 != progress5) {
                    if (progress5 == 0.0f || progress5 == 1.0f) {
                        motionLayout.endTrigger(progress5 == 0.0f);
                    }
                    motionLayout.setProgress(max2);
                    VelocityTracker velocityTracker9 = myTracker2.mTracker;
                    if (velocityTracker9 != null) {
                        velocityTracker9.computeCurrentVelocity(1000);
                    }
                    VelocityTracker velocityTracker10 = myTracker2.mTracker;
                    float xVelocity3 = velocityTracker10 != null ? velocityTracker10.getXVelocity() : 0.0f;
                    VelocityTracker velocityTracker11 = myTracker2.mTracker;
                    double yVelocity2 = velocityTracker11 != null ? velocityTracker11.getYVelocity() : 0.0f;
                    double d = xVelocity3;
                    motionLayout.mLastVelocity = (float) Math.toDegrees((float) ((Math.sin(Math.atan2(yVelocity2, d) - r7) * Math.hypot(yVelocity2, d)) / Math.hypot(rawX2, rawY2)));
                } else {
                    motionLayout.mLastVelocity = 0.0f;
                }
                this.mLastTouchX = motionEvent.getRawX();
                this.mLastTouchY = motionEvent.getRawY();
                return;
            }
            return;
        }
        this.mDragStarted = false;
        VelocityTracker velocityTracker12 = myTracker2.mTracker;
        if (velocityTracker12 != null) {
            velocityTracker12.computeCurrentVelocity(16);
        }
        VelocityTracker velocityTracker13 = myTracker2.mTracker;
        float xVelocity4 = velocityTracker13 != null ? velocityTracker13.getXVelocity() : 0.0f;
        VelocityTracker velocityTracker14 = myTracker2.mTracker;
        float yVelocity3 = velocityTracker14 != null ? velocityTracker14.getYVelocity() : 0.0f;
        float progress6 = motionLayout.getProgress();
        float width2 = motionLayout.getWidth() / 2.0f;
        float height2 = motionLayout.getHeight() / 2.0f;
        int i9 = this.mRotationCenterId;
        if (i9 == -1) {
            int i10 = this.mTouchAnchorId;
            if (i10 != -1) {
                View findViewById2 = motionLayout.findViewById(motionLayout.mFrameArrayList.get(motionLayout.findViewById(i10)).getAnimateRelativeTo());
                motionLayout.getLocationOnScreen(this.mTempLoc);
                right = ((findViewById2.getRight() + findViewById2.getLeft()) / 2.0f) + this.mTempLoc[0];
                f = this.mTempLoc[1];
                top = findViewById2.getTop();
                bottom = findViewById2.getBottom();
            }
            float rawX3 = motionEvent.getRawX() - width2;
            double degrees = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height2, rawX3));
            i2 = this.mTouchAnchorId;
            if (i2 == -1) {
                this.mMotionLayout.getAnchorDpDt(i2, progress6, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                this.mAnchorDpDt[1] = (float) Math.toDegrees(r10[1]);
            } else {
                this.mAnchorDpDt[1] = 360.0f;
            }
            float degrees2 = ((float) (Math.toDegrees(Math.atan2(yVelocity3 + r1, xVelocity4 + rawX3)) - degrees)) * 62.5f;
            f2 = Float.isNaN(degrees2) ? (((degrees2 * 3.0f) * this.mDragScale) / this.mAnchorDpDt[1]) + progress6 : progress6;
            if (f2 != 0.0f || f2 == 1.0f || (i3 = this.mOnTouchUp) == 3) {
                if (0.0f < f2 || 1.0f <= f2) {
                    motionLayout.setState(transitionState);
                }
                return;
            }
            float f8 = (degrees2 * this.mDragScale) / this.mAnchorDpDt[1];
            float f9 = ((double) f2) < 0.5d ? 0.0f : 1.0f;
            if (i3 == 6) {
                if (progress6 + f8 < 0.0f) {
                    f8 = Math.abs(f8);
                }
                f9 = 1.0f;
            }
            if (this.mOnTouchUp == 7) {
                if (progress6 + f8 > 1.0f) {
                    f8 = -Math.abs(f8);
                }
                f9 = 0.0f;
            }
            motionLayout.touchAnimateTo(this.mOnTouchUp, f9, f8 * 3.0f);
            if (0.0f >= progress6 || 1.0f <= progress6) {
                motionLayout.setState(transitionState);
                return;
            }
            return;
        }
        View findViewById3 = motionLayout.findViewById(i9);
        motionLayout.getLocationOnScreen(this.mTempLoc);
        right = ((findViewById3.getRight() + findViewById3.getLeft()) / 2.0f) + this.mTempLoc[0];
        f = this.mTempLoc[1];
        top = findViewById3.getTop();
        bottom = findViewById3.getBottom();
        height2 = f + ((bottom + top) / 2.0f);
        width2 = right;
        float rawX32 = motionEvent.getRawX() - width2;
        double degrees3 = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height2, rawX32));
        i2 = this.mTouchAnchorId;
        if (i2 == -1) {
        }
        float degrees22 = ((float) (Math.toDegrees(Math.atan2(yVelocity3 + r1, xVelocity4 + rawX32)) - degrees3)) * 62.5f;
        if (Float.isNaN(degrees22)) {
        }
        if (f2 != 0.0f) {
        }
        if (0.0f < f2) {
        }
        motionLayout.setState(transitionState);
    }

    final void scrollMove(float f, float f2) {
        MotionLayout motionLayout = this.mMotionLayout;
        float progress = motionLayout.getProgress();
        if (!this.mDragStarted) {
            this.mDragStarted = true;
            motionLayout.setProgress(progress);
        }
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f3 = this.mTouchDirectionX;
        float[] fArr = this.mAnchorDpDt;
        if (Math.abs((this.mTouchDirectionY * fArr[1]) + (f3 * fArr[0])) < 0.01d) {
            float[] fArr2 = this.mAnchorDpDt;
            fArr2[0] = 0.01f;
            fArr2[1] = 0.01f;
        }
        float f4 = this.mTouchDirectionX;
        float max = Math.max(Math.min(progress + (f4 != 0.0f ? (f * f4) / this.mAnchorDpDt[0] : (f2 * this.mTouchDirectionY) / this.mAnchorDpDt[1]), 1.0f), 0.0f);
        if (max != motionLayout.getProgress()) {
            motionLayout.setProgress(max);
        }
    }

    final void scrollUp(float f, float f2) {
        int i;
        this.mDragStarted = false;
        MotionLayout motionLayout = this.mMotionLayout;
        float progress = motionLayout.getProgress();
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, progress, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f3 = this.mTouchDirectionX;
        float[] fArr = this.mAnchorDpDt;
        float f4 = f3 != 0.0f ? (f * f3) / fArr[0] : (f2 * this.mTouchDirectionY) / fArr[1];
        if (!Float.isNaN(f4)) {
            progress += f4 / 3.0f;
        }
        if (progress == 0.0f || progress == 1.0f || (i = this.mOnTouchUp) == 3) {
            return;
        }
        motionLayout.touchAnimateTo(i, ((double) progress) >= 0.5d ? 1.0f : 0.0f, f4);
    }

    final void setDown(float f, float f2) {
        this.mLastTouchX = f;
        this.mLastTouchY = f2;
    }

    public final void setRTL(boolean z) {
        float[][] fArr = TOUCH_SIDES;
        float[][] fArr2 = TOUCH_DIRECTION;
        if (z) {
            fArr2[4] = fArr2[3];
            fArr2[5] = fArr2[2];
            fArr[5] = fArr[2];
            fArr[6] = fArr[1];
        } else {
            fArr2[4] = fArr2[2];
            fArr2[5] = fArr2[3];
            fArr[5] = fArr[1];
            fArr[6] = fArr[2];
        }
        float[] fArr3 = fArr[this.mTouchAnchorSide];
        this.mTouchAnchorX = fArr3[0];
        this.mTouchAnchorY = fArr3[1];
        int i = this.mTouchSide;
        if (i >= 6) {
            return;
        }
        float[] fArr4 = fArr2[i];
        this.mTouchDirectionX = fArr4[0];
        this.mTouchDirectionY = fArr4[1];
    }

    public final void setTouchUpMode() {
        this.mOnTouchUp = 5;
    }

    final void setUpTouchEvent(float f, float f2) {
        this.mLastTouchX = f;
        this.mLastTouchY = f2;
        this.mDragStarted = false;
    }

    final void setupTouch() {
        View view;
        int i = this.mTouchAnchorId;
        if (i != -1) {
            MotionLayout motionLayout = this.mMotionLayout;
            view = motionLayout.findViewById(i);
            if (view == null) {
                Log.e("TouchResponse", "cannot find TouchAnchorId @id/" + Debug.getName(motionLayout.getContext(), this.mTouchAnchorId));
            }
        } else {
            view = null;
        }
        if (view instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            nestedScrollView.setOnTouchListener(new ViewOnTouchListenerC00821());
            nestedScrollView.setOnScrollChangeListener(new C00832());
        }
    }

    public final String toString() {
        if (Float.isNaN(this.mTouchDirectionX)) {
            return "rotation";
        }
        return this.mTouchDirectionX + " , " + this.mTouchDirectionY;
    }
}
