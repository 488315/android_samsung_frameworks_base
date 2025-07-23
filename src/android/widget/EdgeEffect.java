package android.widget;

import android.animation.ValueAnimator;
import android.compat.Compatibility;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.hardware.scontext.SContextConstants;
import android.media.audio.Enums;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class EdgeEffect {
    private static final double DAMPING_RATIO = 0.98d;
    private static final boolean DEBUG = false;
    private static final float EDGE_CONTROL_POINT_HEIGHT_NON_TAB_IN_DIP = 29.0f;
    private static final float EDGE_CONTROL_POINT_HEIGHT_TAB_IN_DIP = 19.0f;
    private static final String EDGE_GLOW_COLOR_DARK = "#fafafa";
    private static final String EDGE_GLOW_COLOR_LIGHT = "#000000";
    private static final float EDGE_MAX_ALPAH_DARK = 0.08f;
    private static final float EDGE_MAX_ALPAH_LIGHT = 0.05f;
    private static final float EDGE_PADDING_NON_TAB_IN_DIP = 5.0f;
    private static final float EDGE_PADDING_TAB_IN_DIP = 3.0f;
    private static final float EPSILON = 0.001f;
    private static final float EXP_STRETCH_INTENSITY = 0.016f;
    private static final float GLOW_ALPHA_START = 0.09f;
    private static final double LINEAR_DISTANCE_TAKE_OVER = 8.0d;
    private static final float LINEAR_STRETCH_INTENSITY = 0.016f;
    private static final float LINEAR_VELOCITY_TAKE_OVER = 200.0f;
    private static final float MAX_ALPHA = 0.15f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final int MSG_CALL_ONRELEASE = 1;
    private static final double NATURAL_FREQUENCY = 24.657d;
    private static final float ON_ABSORB_VELOCITY_ADJUSTMENT = 2.0f;
    private static final int PULL_DECAY_TIME = 2000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 0.8f;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final float RADIUS_FACTOR = 0.6f;
    private static final int RECEDE_TIME = 600;
    private static final float SCROLL_DIST_AFFECTED_BY_EXP_STRETCH = 0.33f;
    private static final int SEM_APPEAR_TIME = 250;
    private static final int SEM_KEEP_TIME = 0;
    private static final int SEM_RECEDE_TIME = 450;
    private static final int SEM_STATE_APPEAR = 5;
    private static final int SEM_STATE_KEEP = 6;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final float TAB_HEIGHT_BUFFER_IN_DIP = 5.0f;
    private static final String TAG = "EdgeEffect";
    private static final int TYPE_GLOW = 0;
    private static final int TYPE_NONE = -1;
    private static final int TYPE_STRETCH = 1;
    public static final long USE_STRETCH_EDGE_EFFECT_BY_DEFAULT = 171228096;
    private static final double VALUE_THRESHOLD = 0.001d;
    private static final int VELOCITY_GLOW_FACTOR = 6;
    private static final double VELOCITY_THRESHOLD = 0.01d;
    private float mBaseGlowScale;
    private final Rect mBounds;
    private float mDisplacement;
    private float mDistance;
    private float mDuration;
    private int mEdgeEffectType;
    private Runnable mForceCallOnRelease;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private Handler mHandler;
    private float mHeight;
    private View mHostView;
    private final Interpolator mInterpolator;
    private final Paint mPaint;
    private final Path mPath;
    private float mPullDistance;
    private float mRadius;
    private long mStartTime;
    private int mState;
    private float mTargetDisplacement;
    private float mTempDeltaDistance;
    private float mTempDisplacement;
    private Matrix mTmpMatrix;
    private float[] mTmpPoints;
    private float mVelocity;
    private float mWidth;
    public static final BlendMode DEFAULT_BLEND_MODE = BlendMode.SRC_ATOP;
    private static final double ANGLE = 0.5235987755982988d;
    private static final float SIN = (float) Math.sin(ANGLE);
    private static final float COS = (float) Math.cos(ANGLE);

    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeEffectType {
    }

    private float calculateEdgeEffectMargin(int i) {
        return ((float) (i * 0.136d)) / 2.0f;
    }

    public EdgeEffect(Context context) {
        this(context, null);
    }

    public EdgeEffect(Context context, AttributeSet attributeSet) {
        this.mInterpolator = new DecelerateInterpolator();
        this.mState = 0;
        this.mBounds = new Rect();
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mDisplacement = 0.5f;
        this.mTargetDisplacement = 0.5f;
        this.mEdgeEffectType = 0;
        this.mTmpMatrix = null;
        this.mTmpPoints = null;
        this.mPath = new Path();
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: android.widget.EdgeEffect.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                EdgeEffect.this.onRelease();
            }
        };
        this.mForceCallOnRelease = new Runnable() { // from class: android.widget.EdgeEffect.2
            @Override // java.lang.Runnable
            public void run() {
                EdgeEffect edgeEffect = EdgeEffect.this;
                edgeEffect.onPull(edgeEffect.mTempDeltaDistance, EdgeEffect.this.mTempDisplacement);
                EdgeEffect.this.mHandler.sendEmptyMessageDelayed(1, 700L);
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EdgeEffect);
        int color = obtainStyledAttributes.getColor(0, -10066330);
        this.mEdgeEffectType = Compatibility.isChangeEnabled(USE_STRETCH_EDGE_EFFECT_BY_DEFAULT) ? 1 : 0;
        obtainStyledAttributes.recycle();
        paint.setAntiAlias(true);
        paint.setColor((16777215 & color) | Enums.AUDIO_FORMAT_DTS_UHD_P2);
        paint.setStyle(Paint.Style.FILL);
        paint.setBlendMode(DEFAULT_BLEND_MODE);
    }

    public void semSetHostView(View view, boolean z) {
        this.mHostView = view;
    }

    private int getCurrentEdgeEffectBehavior() {
        if (ValueAnimator.areAnimatorsEnabled()) {
            return this.mEdgeEffectType;
        }
        return -1;
    }

    public void setSize(int i, int i2) {
        float f = i;
        float f2 = SIN;
        float f3 = (f * 0.6f) / f2;
        float f4 = COS;
        float f5 = f3 - (f4 * f3);
        float f6 = i2;
        float f7 = (0.6f * f6) / f2;
        float f8 = f7 - (f4 * f7);
        this.mRadius = f3;
        this.mBaseGlowScale = f5 > 0.0f ? Math.min(f8 / f5, 1.0f) : 1.0f;
        Rect rect = this.mBounds;
        rect.set(rect.left, this.mBounds.top, i, (int) Math.min(f6, f5));
        this.mWidth = f;
        this.mHeight = f6;
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void finish() {
        this.mState = 0;
        this.mDistance = 0.0f;
        this.mVelocity = 0.0f;
    }

    public void onPull(float f) {
        onPull(f, 0.5f);
    }

    private boolean isEdgeEffectRunning() {
        int i = this.mState;
        return i == 5 || i == 6 || i == 3 || i == 2;
    }

    public void onPullCallOnRelease(float f, float f2, int i) {
        this.mTempDeltaDistance = f;
        this.mTempDisplacement = f2;
        this.mHandler.postDelayed(this.mForceCallOnRelease, i);
    }

    public void onPull(float f, float f2) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == -1) {
            finish();
            return;
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = f2;
        int i = this.mState;
        if (i == 4 && currentAnimationTimeMillis - this.mStartTime < this.mDuration && this.mEdgeEffectType == 0) {
            return;
        }
        if (i != 1) {
            if (this.mEdgeEffectType == 1) {
                this.mPullDistance = this.mDistance;
            } else {
                this.mGlowScaleY = Math.max(0.0f, this.mGlowScaleY);
            }
        }
        this.mState = 1;
        this.mStartTime = currentAnimationTimeMillis;
        this.mDuration = 167.0f;
        float f3 = this.mPullDistance + f;
        this.mPullDistance = f3;
        if (currentEdgeEffectBehavior == 1) {
            this.mPullDistance = Math.min(1.0f, f3);
        }
        this.mDistance = Math.max(0.0f, this.mPullDistance);
        this.mVelocity = 0.0f;
        if (this.mPullDistance == 0.0f) {
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleY = 0.0f;
            this.mGlowAlphaStart = 0.0f;
            this.mGlowAlpha = 0.0f;
        } else {
            float min = Math.min(MAX_ALPHA, this.mGlowAlpha + (Math.abs(f) * 0.8f));
            this.mGlowAlphaStart = min;
            this.mGlowAlpha = min;
            float max = (float) (Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (1.0d - (1.0d / Math.sqrt(Math.abs(this.mPullDistance) * this.mBounds.height()))) - 0.3d) / 0.7d);
            this.mGlowScaleYStart = max;
            this.mGlowScaleY = max;
        }
        this.mGlowAlphaFinish = this.mGlowAlpha;
        this.mGlowScaleYFinish = this.mGlowScaleY;
        if (currentEdgeEffectBehavior == 1 && this.mDistance == 0.0f) {
            this.mState = 0;
        }
    }

    public float onPullDistance(float f, float f2) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == -1) {
            return 0.0f;
        }
        float max = Math.max(0.0f, f + this.mDistance);
        float f3 = this.mDistance;
        float f4 = max - f3;
        if (f4 == 0.0f && f3 == 0.0f) {
            return 0.0f;
        }
        int i = this.mState;
        if (i != 1 && i != 4 && currentEdgeEffectBehavior == 0) {
            this.mPullDistance = f3;
            this.mState = 1;
        }
        onPull(f4, f2);
        return f4;
    }

    public float getDistance() {
        return this.mDistance;
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        int i = this.mState;
        if (i == 1 || i == 4) {
            this.mState = 3;
            this.mGlowAlphaStart = this.mGlowAlpha;
            this.mGlowScaleYStart = this.mGlowScaleY;
            this.mGlowAlphaFinish = 0.0f;
            this.mGlowScaleYFinish = 0.0f;
            this.mVelocity = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 600.0f;
        }
    }

    public void onAbsorb(int i) {
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == 1) {
            this.mState = 3;
            this.mVelocity = i * 2.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        } else {
            if (currentEdgeEffectBehavior == 0) {
                this.mState = 2;
                this.mVelocity = 0.0f;
                int min = Math.min(Math.max(100, Math.abs(i)), 10000);
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = (min * 0.02f) + MAX_ALPHA;
                this.mGlowAlphaStart = GLOW_ALPHA_START;
                this.mGlowScaleYStart = Math.max(this.mGlowScaleY, 0.0f);
                this.mGlowScaleYFinish = Math.min(((((min / 100) * min) * 1.5E-4f) / 2.0f) + 0.025f, 1.0f);
                this.mGlowAlphaFinish = Math.max(this.mGlowAlphaStart, Math.min(min * 6 * 1.0E-5f, MAX_ALPHA));
                this.mTargetDisplacement = 0.5f;
                return;
            }
            finish();
        }
    }

    public void setColor(int i) {
        this.mPaint.setColor(i);
    }

    public void setBlendMode(BlendMode blendMode) {
        this.mPaint.setBlendMode(blendMode);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public BlendMode getBlendMode() {
        return this.mPaint.getBlendMode();
    }

    public boolean draw(Canvas canvas) {
        boolean z;
        boolean z2;
        int currentEdgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (currentEdgeEffectBehavior == 0) {
            update();
            int save = canvas.save();
            float centerX = this.mBounds.centerX();
            float height = this.mBounds.height() - this.mRadius;
            canvas.scale(1.0f, Math.min(this.mGlowScaleY, 1.0f) * this.mBaseGlowScale, centerX, 0.0f);
            float width = (this.mBounds.width() * (Math.max(0.0f, Math.min(this.mDisplacement, 1.0f)) - 0.5f)) / 2.0f;
            canvas.clipRect(this.mBounds);
            canvas.translate(width, 0.0f);
            this.mPaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            canvas.drawCircle(centerX, height, this.mRadius, this.mPaint);
            canvas.restoreToCount(save);
        } else {
            if (currentEdgeEffectBehavior == 1 && (canvas instanceof RecordingCanvas)) {
                if (this.mState == 3) {
                    updateSpring();
                }
                if (this.mDistance != 0.0f) {
                    RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
                    if (this.mTmpMatrix == null) {
                        this.mTmpMatrix = new Matrix();
                        this.mTmpPoints = new float[12];
                    }
                    recordingCanvas.getMatrix(this.mTmpMatrix);
                    float[] fArr = this.mTmpPoints;
                    fArr[0] = 0.0f;
                    fArr[1] = 0.0f;
                    float f = this.mWidth;
                    fArr[2] = f;
                    fArr[3] = 0.0f;
                    fArr[4] = f;
                    float f2 = this.mHeight;
                    fArr[5] = f2;
                    fArr[6] = 0.0f;
                    fArr[7] = f2;
                    float f3 = this.mDisplacement;
                    fArr[8] = f * f3;
                    fArr[9] = 0.0f;
                    fArr[10] = f * f3;
                    fArr[11] = f2 * this.mDistance;
                    this.mTmpMatrix.mapPoints(fArr);
                    RenderNode renderNode = recordingCanvas.mNode;
                    float left = renderNode.getLeft();
                    float[] fArr2 = this.mTmpPoints;
                    z = true;
                    float min = left + min(fArr2[0], fArr2[2], fArr2[4], fArr2[6]);
                    float top = renderNode.getTop();
                    float[] fArr3 = this.mTmpPoints;
                    float min2 = top + min(fArr3[1], fArr3[3], fArr3[5], fArr3[7]);
                    float left2 = renderNode.getLeft();
                    float[] fArr4 = this.mTmpPoints;
                    float max = left2 + max(fArr4[0], fArr4[2], fArr4[4], fArr4[6]);
                    float top2 = renderNode.getTop();
                    float[] fArr5 = this.mTmpPoints;
                    float max2 = top2 + max(fArr5[1], fArr5[3], fArr5[5], fArr5[7]);
                    float[] fArr6 = this.mTmpPoints;
                    float dampStretchVector = dampStretchVector(Math.max(-1.0f, Math.min(1.0f, (fArr6[10] - fArr6[8]) / (max - min))));
                    float[] fArr7 = this.mTmpPoints;
                    float dampStretchVector2 = dampStretchVector(Math.max(-1.0f, Math.min(1.0f, (fArr7[11] - fArr7[9]) / (max2 - min2))));
                    boolean z3 = Float.isFinite(dampStretchVector) && Float.isFinite(dampStretchVector2);
                    if (max > min && max2 > min2) {
                        float f4 = this.mWidth;
                        if (f4 > 0.0f) {
                            float f5 = this.mHeight;
                            if (f5 > 0.0f && z3) {
                                renderNode.stretch(dampStretchVector, dampStretchVector2, f4, f5);
                            }
                        }
                    }
                }
            } else {
                z = true;
                this.mState = 0;
                this.mDistance = 0.0f;
                this.mVelocity = 0.0f;
            }
            if (this.mState != 3 && this.mDistance == 0.0f && this.mVelocity == 0.0f) {
                this.mState = 0;
                z2 = z;
            } else {
                z2 = false;
            }
            if (this.mState == 0 || z2) {
                return z;
            }
            return false;
        }
        z = true;
        if (this.mState != 3) {
        }
        z2 = false;
        if (this.mState == 0) {
        }
        return z;
    }

    private float min(float f, float f2, float f3, float f4) {
        return Math.min(Math.min(Math.min(f, f2), f3), f4);
    }

    private float max(float f, float f2, float f3, float f4) {
        return Math.max(Math.max(Math.max(f, f2), f3), f4);
    }

    public int getMaxHeight() {
        return (int) this.mHeight;
    }

    private void update() {
        float min = Math.min((AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / this.mDuration, 1.0f);
        float interpolation = this.mInterpolator.getInterpolation(min);
        float f = this.mGlowAlphaStart;
        float f2 = f + ((this.mGlowAlphaFinish - f) * interpolation);
        this.mGlowAlpha = f2;
        float f3 = this.mGlowScaleYStart;
        float f4 = f3 + ((this.mGlowScaleYFinish - f3) * interpolation);
        this.mGlowScaleY = f4;
        if (this.mState != 1) {
            this.mDistance = calculateDistanceFromGlowValues(f4, f2);
        }
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / 2.0f;
        if (min >= 0.999f) {
            int i = this.mState;
            if (i == 1) {
                this.mState = 4;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = 2000.0f;
                this.mGlowAlphaStart = this.mGlowAlpha;
                this.mGlowScaleYStart = this.mGlowScaleY;
                this.mGlowAlphaFinish = 0.0f;
                this.mGlowScaleYFinish = 0.0f;
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    this.mState = 0;
                    return;
                } else {
                    if (i != 4) {
                        return;
                    }
                    this.mState = 3;
                    return;
                }
            }
            this.mState = 3;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 600.0f;
            this.mGlowAlphaStart = this.mGlowAlpha;
            this.mGlowScaleYStart = this.mGlowScaleY;
            this.mGlowAlphaFinish = 0.0f;
            this.mGlowScaleYFinish = 0.0f;
        }
    }

    private void updateSpring() {
        float f;
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        float f2 = (currentAnimationTimeMillis - this.mStartTime) / 1000.0f;
        if (f2 < 0.001f) {
            return;
        }
        this.mStartTime = currentAnimationTimeMillis;
        if (Math.abs(this.mVelocity) <= 200.0f && Math.abs(this.mDistance * this.mHeight) < LINEAR_DISTANCE_TAKE_OVER && Math.signum(this.mVelocity) == (-Math.signum(this.mDistance))) {
            float signum = Math.signum(this.mVelocity) * 200.0f;
            this.mVelocity = signum;
            float f3 = this.mDistance + ((signum * f2) / this.mHeight);
            if (Math.signum(f3) != Math.signum(this.mDistance)) {
                this.mDistance = 0.0f;
                this.mVelocity = 0.0f;
                return;
            } else {
                this.mDistance = f3;
                return;
            }
        }
        double sqrt = Math.sqrt(0.03960000000000008d) * NATURAL_FREQUENCY;
        float f4 = this.mDistance;
        float f5 = this.mHeight;
        double d = f4 * f5;
        double d2 = (1.0d / sqrt) * ((f4 * 24.16386d * f5) + this.mVelocity);
        double d3 = f2;
        double d4 = (-24.16386d) * d3;
        double d5 = d3 * sqrt;
        double pow = Math.pow(2.718281828459045d, d4) * ((Math.cos(d5) * d) + (Math.sin(d5) * d2));
        double pow2 = ((-24.657d) * pow * DAMPING_RATIO) + (Math.pow(2.718281828459045d, d4) * (((-sqrt) * d * Math.sin(d5)) + (sqrt * d2 * Math.cos(d5))));
        float f6 = ((float) pow) / this.mHeight;
        this.mDistance = f6;
        this.mVelocity = (float) pow2;
        if (f6 > 1.0f) {
            this.mDistance = 1.0f;
            f = 0.0f;
            this.mVelocity = 0.0f;
        } else {
            f = 0.0f;
        }
        if (isAtEquilibrium()) {
            this.mDistance = f;
            this.mVelocity = f;
        }
    }

    private float calculateDistanceFromGlowValues(float f, float f2) {
        float f3;
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f > 0.0f) {
            float f4 = 1.4285715f / (this.mGlowScaleY - 1.0f);
            f2 = f4 * f4;
            f3 = this.mBounds.height();
        } else {
            f3 = 0.8f;
        }
        return f2 / f3;
    }

    private boolean isAtEquilibrium() {
        double d = this.mDistance * this.mHeight;
        double d2 = this.mVelocity;
        if (d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return Math.abs(d2) < VELOCITY_THRESHOLD && d < VALUE_THRESHOLD;
        }
        return true;
    }

    private float dampStretchVector(float f) {
        float f2 = f > 0.0f ? 1.0f : -1.0f;
        float abs = Math.abs(f);
        return f2 * ((float) ((0.016f * abs) + ((1.0d - Math.exp((-abs) * 8.237217334679498d)) * 0.01600000075995922d)));
    }
}
