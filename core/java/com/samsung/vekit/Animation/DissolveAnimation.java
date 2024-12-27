package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DissolveAnimation extends TransitionAnimation {
    public DissolveAnimation(VEContext context, int id, String name) {
        super(context, id, name, TransitionType.DISSOLVE);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public DissolveAnimation setTargets(Item firstTarget, Item secondTarget) {
        return (DissolveAnimation) super.setTargets(firstTarget, secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public DissolveAnimation setSecondTarget(Item secondTarget) {
        return (DissolveAnimation) super.setSecondTarget(secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (DissolveAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setStartTime(long startTime) {
        return (DissolveAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> keyFrameList) {
        return (DissolveAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setKeyFrame(
            KeyFrame<Float> firstKeyFrame, KeyFrame<Float> secondKeyFrame) {
        return (DissolveAnimation)
                super.setKeyFrame((KeyFrame) firstKeyFrame, (KeyFrame) secondKeyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (DissolveAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        super.onAnimationStarted(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        super.onAnimationUpdated(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        super.onAnimationFinished(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        super.onAnimationCanceled(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation
    @Deprecated
    public DissolveAnimation setDuration(long duration) {
        return (DissolveAnimation) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public DissolveAnimation setFrom(Float from) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public DissolveAnimation setTo(Float to) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public DissolveAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (DissolveAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
