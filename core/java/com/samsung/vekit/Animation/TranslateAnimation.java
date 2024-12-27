package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Object.Matrix4;
import com.samsung.vekit.Common.Object.Vector3;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class TranslateAnimation extends Animation<Vector3<Float>> {
    public TranslateAnimation(VEContext context, int id, String name) {
        super(context, AnimationType.TRANSLATE, id, name);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            this.firstTarget.getPanel().setPosition((Vector3) this.rollbackValue);
            this.firstTarget.update();
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TranslateAnimation setKeyFrameList(ArrayList<KeyFrame<Vector3<Float>>> keyFrameList) {
        return (TranslateAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TranslateAnimation setKeyFrame(KeyFrame<Vector3<Float>> keyFrame) {
        return (TranslateAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TranslateAnimation setTarget(Element target) {
        return (TranslateAnimation) super.setTarget(target);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TranslateAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (TranslateAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public TranslateAnimation setStartTime(long startTime) {
        return (TranslateAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        if (this.firstTarget == null) {
            return;
        }
        super.onAnimationStarted(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationUpdated(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationFinished(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationCanceled(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void updateTargetValue(Object interpolatedValue) {
        if (this.firstTarget == null || interpolatedValue == null) {
            return;
        }
        float[] data = (float[]) interpolatedValue;
        Matrix4 matrix = new Matrix4(data);
        this.firstTarget.getPanel().setMatrix(matrix);
    }
}
