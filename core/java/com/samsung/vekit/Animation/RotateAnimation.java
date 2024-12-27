package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Object.Matrix4;
import com.samsung.vekit.Common.Object.Vector3;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;

import java.util.ArrayList;

public class RotateAnimation extends Animation<Vector3> {
    public RotateAnimation(VEContext context, int id, String name) {
        super(context, AnimationType.ROTATE, id, name);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            this.firstTarget.getPanel().setRotation((Vector3) this.rollbackValue);
            this.firstTarget.update();
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setKeyFrameList(ArrayList<KeyFrame<Vector3>> keyFrameList) {
        return (RotateAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setKeyFrame(KeyFrame<Vector3> keyFrame) {
        return (RotateAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setTarget(Element target) {
        return (RotateAnimation) super.setTarget(target);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (RotateAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setStartTime(long startTime) {
        return (RotateAnimation) super.setStartTime(startTime);
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
