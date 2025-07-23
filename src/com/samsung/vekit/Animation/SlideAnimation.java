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
    protected SlideType slideType;

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public SlideAnimation setFrom(Float f) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public SlideAnimation setTo(Float f) {
        return this;
    }

    public SlideAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, i, str, TransitionType.SLIDE);
        this.slideType = SlideType.VERTICAL;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    public SlideAnimation setSlideType(SlideType slideType) {
        this.slideType = slideType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public SlideAnimation setTargets(Item item, Item item2) {
        return (SlideAnimation) super.setTargets(item, item2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public SlideAnimation setSecondTarget(Item item) {
        return (SlideAnimation) super.setSecondTarget(item);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public SlideAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (SlideAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public SlideAnimation setStartTime(long j) {
        return (SlideAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> arrayList) {
        return (SlideAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation setKeyFrame(KeyFrame<Float> keyFrame, KeyFrame<Float> keyFrame2) {
        return (SlideAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public SlideAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (SlideAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object obj) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        super.onAnimationStarted(obj);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object obj) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        super.onAnimationUpdated(obj);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object obj) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        super.onAnimationFinished(obj);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object obj) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        super.onAnimationCanceled(obj);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    @Deprecated
    public SlideAnimation setDuration(long j) {
        return (SlideAnimation) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public SlideAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (SlideAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
