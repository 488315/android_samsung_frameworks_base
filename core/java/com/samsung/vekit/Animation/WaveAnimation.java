package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Object.WaveInfo;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;

import java.util.ArrayList;

public class WaveAnimation extends Animation<WaveInfo> {
    public WaveAnimation(VEContext context, int id, String name) {
        super(context, AnimationType.WAVE, id, name);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        isEnableRollback();
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WaveAnimation setTarget(Element target) {
        return (WaveAnimation) super.setTarget(target);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WaveAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (WaveAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WaveAnimation setStartTime(long startTime) {
        return (WaveAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
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
    public WaveAnimation setKeyFrameList(ArrayList<KeyFrame<WaveInfo>> keyFrameList) {
        return (WaveAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WaveAnimation setKeyFrame(
            KeyFrame<WaveInfo> firstKeyFrame, KeyFrame<WaveInfo> secondKeyFrame) {
        return (WaveAnimation)
                super.setKeyFrame((KeyFrame) firstKeyFrame, (KeyFrame) secondKeyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WaveAnimation addKeyFrame(KeyFrame<WaveInfo> keyFrame) {
        return (WaveAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void updateTargetValue(Object interpolatedValue) {}
}
