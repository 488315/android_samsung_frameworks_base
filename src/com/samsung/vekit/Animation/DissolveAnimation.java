package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DissolveAnimation extends TransitionAnimation {
    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public DissolveAnimation setFrom(Float f) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public DissolveAnimation setTo(Float f) {
        return this;
    }

    public DissolveAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, i, str, TransitionType.DISSOLVE);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public DissolveAnimation setTargets(Item item, Item item2) {
        return (DissolveAnimation) super.setTargets(item, item2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public DissolveAnimation setSecondTarget(Item item) {
        return (DissolveAnimation) super.setSecondTarget(item);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public DissolveAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (DissolveAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public DissolveAnimation setStartTime(long j) {
        return (DissolveAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> arrayList) {
        return (DissolveAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation setKeyFrame(KeyFrame<Float> keyFrame, KeyFrame<Float> keyFrame2) {
        return (DissolveAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public DissolveAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (DissolveAnimation) super.addKeyFrame((KeyFrame) keyFrame);
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
    public DissolveAnimation setDuration(long j) {
        return (DissolveAnimation) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public DissolveAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (DissolveAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
