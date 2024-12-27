package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.SlideType;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SlideAnimation extends TransitionAnimation {
    protected SlideType mSlideType;

    public SlideAnimation(VEContext context, int id, String name) {
        super(context, id, name, TransitionType.SLIDE);
        this.mSlideType = SlideType.VERTICAL;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    public SlideAnimation setSlideType(SlideType slideType) {
        this.mSlideType = slideType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public SlideAnimation setTargets(Item firstTarget, Item secondTarget) {
        return (SlideAnimation) super.setTargets(firstTarget, secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public SlideAnimation setSecondTarget(Item secondTarget) {
        return (SlideAnimation) super.setSecondTarget(secondTarget);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public SlideAnimation setBezierControlPoint(float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
        return (SlideAnimation) super.setBezierControlPoint(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public SlideAnimation setStartTime(long startTime) {
        return (SlideAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> keyFrameList) {
        return (SlideAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation setKeyFrame(KeyFrame<Float> firstKeyFrame, KeyFrame<Float> secondKeyFrame) {
        return (SlideAnimation) super.setKeyFrame((KeyFrame) firstKeyFrame, (KeyFrame) secondKeyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (SlideAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        super.onAnimationStarted(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        super.onAnimationUpdated(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        super.onAnimationFinished(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        super.onAnimationCanceled(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    @Deprecated
    public SlideAnimation setDuration(long duration) {
        return (SlideAnimation) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public SlideAnimation setFrom(Float from) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public SlideAnimation setTo(Float to) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public SlideAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (SlideAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
