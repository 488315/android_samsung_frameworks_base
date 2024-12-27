package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.Type.WipeType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class WipeAnimation extends TransitionAnimation {
    protected WipeType mWipeType;

    public WipeAnimation(VEContext context, int id, String name) {
        super(context, id, name, TransitionType.WIPE);
        this.mWipeType = WipeType.RIGHT;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> keyFrameList) {
        return (WipeAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation setKeyFrame(
            KeyFrame<Float> firstKeyFrame, KeyFrame<Float> secondKeyFrame) {
        return (WipeAnimation)
                super.setKeyFrame((KeyFrame) firstKeyFrame, (KeyFrame) secondKeyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (WipeAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    public WipeAnimation setWipeType(WipeType wipeType) {
        this.mWipeType = wipeType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public WipeAnimation setTargets(Item firstTarget, Item secondTarget) {
        return (WipeAnimation) super.setTargets(firstTarget, secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public WipeAnimation setSecondTarget(Item secondTarget) {
        return (WipeAnimation) super.setSecondTarget(secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation
    public WipeAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (WipeAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation,
              // com.samsung.vekit.Animation.Animation
    public WipeAnimation setStartTime(long startTime) {
        return (WipeAnimation) super.setStartTime(startTime);
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
    public WipeAnimation setDuration(long duration) {
        return (WipeAnimation) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public WipeAnimation setFrom(Float from) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public WipeAnimation setTo(Float to) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public WipeAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (WipeAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
