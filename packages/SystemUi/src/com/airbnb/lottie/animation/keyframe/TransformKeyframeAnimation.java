package com.airbnb.lottie.animation.keyframe;

import android.graphics.Matrix;
import android.graphics.PointF;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TransformKeyframeAnimation {
    public BaseKeyframeAnimation anchorPoint;
    public BaseKeyframeAnimation endOpacity;
    public final Matrix matrix = new Matrix();
    public BaseKeyframeAnimation opacity;
    public BaseKeyframeAnimation position;
    public BaseKeyframeAnimation rotation;
    public BaseKeyframeAnimation scale;
    public FloatKeyframeAnimation skew;
    public FloatKeyframeAnimation skewAngle;
    public final Matrix skewMatrix1;
    public final Matrix skewMatrix2;
    public final Matrix skewMatrix3;
    public final float[] skewValues;
    public BaseKeyframeAnimation startOpacity;

    public TransformKeyframeAnimation(AnimatableTransform animatableTransform) {
        AnimatablePathValue animatablePathValue = animatableTransform.anchorPoint;
        this.anchorPoint = animatablePathValue == null ? null : animatablePathValue.createAnimation();
        AnimatableValue animatableValue = animatableTransform.position;
        this.position = animatableValue == null ? null : animatableValue.createAnimation();
        AnimatableScaleValue animatableScaleValue = animatableTransform.scale;
        this.scale = animatableScaleValue == null ? null : animatableScaleValue.createAnimation();
        AnimatableFloatValue animatableFloatValue = animatableTransform.rotation;
        this.rotation = animatableFloatValue == null ? null : animatableFloatValue.createAnimation();
        AnimatableFloatValue animatableFloatValue2 = animatableTransform.skew;
        FloatKeyframeAnimation floatKeyframeAnimation = animatableFloatValue2 == null ? null : (FloatKeyframeAnimation) animatableFloatValue2.createAnimation();
        this.skew = floatKeyframeAnimation;
        if (floatKeyframeAnimation != null) {
            this.skewMatrix1 = new Matrix();
            this.skewMatrix2 = new Matrix();
            this.skewMatrix3 = new Matrix();
            this.skewValues = new float[9];
        } else {
            this.skewMatrix1 = null;
            this.skewMatrix2 = null;
            this.skewMatrix3 = null;
            this.skewValues = null;
        }
        AnimatableFloatValue animatableFloatValue3 = animatableTransform.skewAngle;
        this.skewAngle = animatableFloatValue3 == null ? null : (FloatKeyframeAnimation) animatableFloatValue3.createAnimation();
        AnimatableIntegerValue animatableIntegerValue = animatableTransform.opacity;
        if (animatableIntegerValue != null) {
            this.opacity = animatableIntegerValue.createAnimation();
        }
        AnimatableFloatValue animatableFloatValue4 = animatableTransform.startOpacity;
        if (animatableFloatValue4 != null) {
            this.startOpacity = animatableFloatValue4.createAnimation();
        } else {
            this.startOpacity = null;
        }
        AnimatableFloatValue animatableFloatValue5 = animatableTransform.endOpacity;
        if (animatableFloatValue5 != null) {
            this.endOpacity = animatableFloatValue5.createAnimation();
        } else {
            this.endOpacity = null;
        }
    }

    public final void addAnimationsToLayer(BaseLayer baseLayer) {
        baseLayer.addAnimation(this.opacity);
        baseLayer.addAnimation(this.startOpacity);
        baseLayer.addAnimation(this.endOpacity);
        baseLayer.addAnimation(this.anchorPoint);
        baseLayer.addAnimation(this.position);
        baseLayer.addAnimation(this.scale);
        baseLayer.addAnimation(this.rotation);
        baseLayer.addAnimation(this.skew);
        baseLayer.addAnimation(this.skewAngle);
    }

    public final void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        BaseKeyframeAnimation baseKeyframeAnimation = this.opacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.startOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.endOpacity;
        if (baseKeyframeAnimation3 != null) {
            baseKeyframeAnimation3.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation4 = this.anchorPoint;
        if (baseKeyframeAnimation4 != null) {
            baseKeyframeAnimation4.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation5 = this.position;
        if (baseKeyframeAnimation5 != null) {
            baseKeyframeAnimation5.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation6 = this.scale;
        if (baseKeyframeAnimation6 != null) {
            baseKeyframeAnimation6.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation baseKeyframeAnimation7 = this.rotation;
        if (baseKeyframeAnimation7 != null) {
            baseKeyframeAnimation7.addUpdateListener(animationListener);
        }
        FloatKeyframeAnimation floatKeyframeAnimation = this.skew;
        if (floatKeyframeAnimation != null) {
            floatKeyframeAnimation.addUpdateListener(animationListener);
        }
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.skewAngle;
        if (floatKeyframeAnimation2 != null) {
            floatKeyframeAnimation2.addUpdateListener(animationListener);
        }
    }

    public final boolean applyValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        if (obj == LottieProperty.TRANSFORM_ANCHOR_POINT) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.anchorPoint;
            if (baseKeyframeAnimation == null) {
                this.anchorPoint = new ValueCallbackKeyframeAnimation(lottieValueCallback, new PointF());
                return true;
            }
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_POSITION) {
            BaseKeyframeAnimation baseKeyframeAnimation2 = this.position;
            if (baseKeyframeAnimation2 == null) {
                this.position = new ValueCallbackKeyframeAnimation(lottieValueCallback, new PointF());
                return true;
            }
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_POSITION_X) {
            BaseKeyframeAnimation baseKeyframeAnimation3 = this.position;
            if (baseKeyframeAnimation3 instanceof SplitDimensionPathKeyframeAnimation) {
                SplitDimensionPathKeyframeAnimation splitDimensionPathKeyframeAnimation = (SplitDimensionPathKeyframeAnimation) baseKeyframeAnimation3;
                LottieValueCallback lottieValueCallback2 = splitDimensionPathKeyframeAnimation.xValueCallback;
                splitDimensionPathKeyframeAnimation.xValueCallback = lottieValueCallback;
                return true;
            }
        }
        if (obj == LottieProperty.TRANSFORM_POSITION_Y) {
            BaseKeyframeAnimation baseKeyframeAnimation4 = this.position;
            if (baseKeyframeAnimation4 instanceof SplitDimensionPathKeyframeAnimation) {
                SplitDimensionPathKeyframeAnimation splitDimensionPathKeyframeAnimation2 = (SplitDimensionPathKeyframeAnimation) baseKeyframeAnimation4;
                LottieValueCallback lottieValueCallback3 = splitDimensionPathKeyframeAnimation2.yValueCallback;
                splitDimensionPathKeyframeAnimation2.yValueCallback = lottieValueCallback;
                return true;
            }
        }
        if (obj == LottieProperty.TRANSFORM_SCALE) {
            BaseKeyframeAnimation baseKeyframeAnimation5 = this.scale;
            if (baseKeyframeAnimation5 == null) {
                this.scale = new ValueCallbackKeyframeAnimation(lottieValueCallback, new ScaleXY());
                return true;
            }
            baseKeyframeAnimation5.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_ROTATION) {
            BaseKeyframeAnimation baseKeyframeAnimation6 = this.rotation;
            if (baseKeyframeAnimation6 == null) {
                this.rotation = new ValueCallbackKeyframeAnimation(lottieValueCallback, Float.valueOf(0.0f));
                return true;
            }
            baseKeyframeAnimation6.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_OPACITY) {
            BaseKeyframeAnimation baseKeyframeAnimation7 = this.opacity;
            if (baseKeyframeAnimation7 == null) {
                this.opacity = new ValueCallbackKeyframeAnimation(lottieValueCallback, 100);
                return true;
            }
            baseKeyframeAnimation7.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_START_OPACITY) {
            BaseKeyframeAnimation baseKeyframeAnimation8 = this.startOpacity;
            if (baseKeyframeAnimation8 == null) {
                this.startOpacity = new ValueCallbackKeyframeAnimation(lottieValueCallback, Float.valueOf(100.0f));
                return true;
            }
            baseKeyframeAnimation8.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_END_OPACITY) {
            BaseKeyframeAnimation baseKeyframeAnimation9 = this.endOpacity;
            if (baseKeyframeAnimation9 == null) {
                this.endOpacity = new ValueCallbackKeyframeAnimation(lottieValueCallback, Float.valueOf(100.0f));
                return true;
            }
            baseKeyframeAnimation9.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj == LottieProperty.TRANSFORM_SKEW) {
            if (this.skew == null) {
                this.skew = new FloatKeyframeAnimation(Collections.singletonList(new Keyframe(Float.valueOf(0.0f))));
            }
            this.skew.setValueCallback(lottieValueCallback);
            return true;
        }
        if (obj != LottieProperty.TRANSFORM_SKEW_ANGLE) {
            return false;
        }
        if (this.skewAngle == null) {
            this.skewAngle = new FloatKeyframeAnimation(Collections.singletonList(new Keyframe(Float.valueOf(0.0f))));
        }
        this.skewAngle.setValueCallback(lottieValueCallback);
        return true;
    }

    public final Matrix getMatrix() {
        PointF pointF;
        float[] fArr;
        PointF pointF2;
        Matrix matrix = this.matrix;
        matrix.reset();
        BaseKeyframeAnimation baseKeyframeAnimation = this.position;
        if (baseKeyframeAnimation != null && (pointF2 = (PointF) baseKeyframeAnimation.getValue()) != null) {
            float f = pointF2.x;
            if (f != 0.0f || pointF2.y != 0.0f) {
                matrix.preTranslate(f, pointF2.y);
            }
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.rotation;
        if (baseKeyframeAnimation2 != null) {
            float floatValue = baseKeyframeAnimation2 instanceof ValueCallbackKeyframeAnimation ? ((Float) baseKeyframeAnimation2.getValue()).floatValue() : ((FloatKeyframeAnimation) baseKeyframeAnimation2).getFloatValue();
            if (floatValue != 0.0f) {
                matrix.preRotate(floatValue);
            }
        }
        if (this.skew != null) {
            float cos = this.skewAngle == null ? 0.0f : (float) Math.cos(Math.toRadians((-r5.getFloatValue()) + 90.0f));
            float sin = this.skewAngle == null ? 1.0f : (float) Math.sin(Math.toRadians((-r7.getFloatValue()) + 90.0f));
            float tan = (float) Math.tan(Math.toRadians(r2.getFloatValue()));
            int i = 0;
            while (true) {
                fArr = this.skewValues;
                if (i >= 9) {
                    break;
                }
                fArr[i] = 0.0f;
                i++;
            }
            fArr[0] = cos;
            fArr[1] = sin;
            float f2 = -sin;
            fArr[3] = f2;
            fArr[4] = cos;
            fArr[8] = 1.0f;
            Matrix matrix2 = this.skewMatrix1;
            matrix2.setValues(fArr);
            for (int i2 = 0; i2 < 9; i2++) {
                fArr[i2] = 0.0f;
            }
            fArr[0] = 1.0f;
            fArr[3] = tan;
            fArr[4] = 1.0f;
            fArr[8] = 1.0f;
            Matrix matrix3 = this.skewMatrix2;
            matrix3.setValues(fArr);
            for (int i3 = 0; i3 < 9; i3++) {
                fArr[i3] = 0.0f;
            }
            fArr[0] = cos;
            fArr[1] = f2;
            fArr[3] = sin;
            fArr[4] = cos;
            fArr[8] = 1.0f;
            Matrix matrix4 = this.skewMatrix3;
            matrix4.setValues(fArr);
            matrix3.preConcat(matrix2);
            matrix4.preConcat(matrix3);
            matrix.preConcat(matrix4);
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.scale;
        if (baseKeyframeAnimation3 != null) {
            ScaleXY scaleXY = (ScaleXY) baseKeyframeAnimation3.getValue();
            float f3 = scaleXY.scaleX;
            if (f3 != 1.0f || scaleXY.scaleY != 1.0f) {
                matrix.preScale(f3, scaleXY.scaleY);
            }
        }
        BaseKeyframeAnimation baseKeyframeAnimation4 = this.anchorPoint;
        if (baseKeyframeAnimation4 != null && (((pointF = (PointF) baseKeyframeAnimation4.getValue()) != null && pointF.x != 0.0f) || pointF.y != 0.0f)) {
            matrix.preTranslate(-pointF.x, -pointF.y);
        }
        return matrix;
    }

    public final Matrix getMatrixForRepeater(float f) {
        BaseKeyframeAnimation baseKeyframeAnimation = this.position;
        PointF pointF = baseKeyframeAnimation == null ? null : (PointF) baseKeyframeAnimation.getValue();
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.scale;
        ScaleXY scaleXY = baseKeyframeAnimation2 == null ? null : (ScaleXY) baseKeyframeAnimation2.getValue();
        Matrix matrix = this.matrix;
        matrix.reset();
        if (pointF != null) {
            matrix.preTranslate(pointF.x * f, pointF.y * f);
        }
        if (scaleXY != null) {
            double d = f;
            matrix.preScale((float) Math.pow(scaleXY.scaleX, d), (float) Math.pow(scaleXY.scaleY, d));
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.rotation;
        if (baseKeyframeAnimation3 != null) {
            float floatValue = ((Float) baseKeyframeAnimation3.getValue()).floatValue();
            BaseKeyframeAnimation baseKeyframeAnimation4 = this.anchorPoint;
            PointF pointF2 = baseKeyframeAnimation4 != null ? (PointF) baseKeyframeAnimation4.getValue() : null;
            matrix.preRotate(floatValue * f, pointF2 == null ? 0.0f : pointF2.x, pointF2 != null ? pointF2.y : 0.0f);
        }
        return matrix;
    }
}
