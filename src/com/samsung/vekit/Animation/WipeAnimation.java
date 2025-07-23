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
    protected WipeType wipeType;

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public WipeAnimation setFrom(Float f) {
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    @Deprecated
    public WipeAnimation setTo(Float f) {
        return this;
    }

    public WipeAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, i, str, TransitionType.WIPE);
        this.wipeType = WipeType.RIGHT;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public TransitionType getTransitionType() {
        return this.transitionType;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation setKeyFrameList(ArrayList<KeyFrame<Float>> arrayList) {
        return (WipeAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation setKeyFrame(KeyFrame<Float> keyFrame, KeyFrame<Float> keyFrame2) {
        return (WipeAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public WipeAnimation addKeyFrame(KeyFrame<Float> keyFrame) {
        return (WipeAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    public WipeAnimation setWipeType(WipeType wipeType) {
        this.wipeType = wipeType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public WipeAnimation setTargets(Item item, Item item2) {
        return (WipeAnimation) super.setTargets(item, item2);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public WipeAnimation setSecondTarget(Item item) {
        return (WipeAnimation) super.setSecondTarget(item);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation
    public Item getSecondTarget() {
        return this.secondTarget;
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public WipeAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (WipeAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.TransitionAnimation, com.samsung.vekit.Animation.Animation
    public WipeAnimation setStartTime(long j) {
        return (WipeAnimation) super.setStartTime(j);
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
    public WipeAnimation setDuration(long j) {
        return (WipeAnimation) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public WipeAnimation setKeyFrame(KeyFrame<Float> keyFrame) {
        return (WipeAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
