package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class ToneAnimation extends Animation<Integer> {
    private ToneType toneType;

    public ToneAnimation(VEContext context, int id, String name) {
        super(context, AnimationType.TONE, id, name);
    }

    public ToneType getToneType() {
        return this.toneType;
    }

    public ToneAnimation setToneType(ToneType toneType) {
        this.toneType = toneType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setTarget(Element target) {
        return (ToneAnimation) super.setTarget(target);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setKeyFrameList(ArrayList<KeyFrame<Integer>> keyFrameList) {
        return (ToneAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setKeyFrame(KeyFrame<Integer> keyFrame) {
        return (ToneAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setBezierControlPoint(float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
        return (ToneAnimation) super.setBezierControlPoint(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setStartTime(long startTime) {
        return (ToneAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.m98i(this.TAG, "onAnimationStarted : " + this.f3097id + ", " + this.name);
        super.onAnimationStarted(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object interpolatedValue) {
        Log.m98i(this.TAG, "onAnimationUpdated : " + this.f3097id + ", " + this.name);
        super.onAnimationUpdated(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object interpolatedValue) {
        Log.m98i(this.TAG, "onAnimationFinished : " + this.f3097id + ", " + this.name);
        super.onAnimationFinished(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object interpolatedValue) {
        Log.m98i(this.TAG, "onAnimationCanceled : " + this.f3097id + ", " + this.name);
        super.onAnimationCanceled(interpolatedValue);
    }
}
