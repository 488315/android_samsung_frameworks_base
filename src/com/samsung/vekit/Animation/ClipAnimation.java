package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ClipType;
import com.samsung.vekit.Common.VEContext;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class ClipAnimation extends Animation<Integer> {
    protected float clipIntensity;
    protected ClipType clipType;

    public ClipAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, AnimationType.CLIP, i, str);
        this.clipType = ClipType.VERTICAL_FROM_CENTER;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            setClipIntensity(((Integer) this.rollbackValue).intValue());
        }
        this.enableRollback = false;
    }

    public ClipAnimation setClipType(ClipType clipType) {
        this.clipType = clipType;
        return this;
    }

    @Deprecated
    public ClipAnimation setSlideType(ClipType clipType) {
        this.clipType = clipType;
        return this;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation setTarget(Element element) {
        return (ClipAnimation) super.setTarget(element);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (ClipAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation setStartTime(long j) {
        return (ClipAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object obj) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        super.onAnimationStarted(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object obj) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        updateTargetValue(obj);
        super.onAnimationUpdated(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object obj) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        updateTargetValue(obj);
        super.onAnimationFinished(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object obj) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        updateTargetValue(obj);
        super.onAnimationCanceled(obj);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation setKeyFrameList(ArrayList<KeyFrame<Integer>> arrayList) {
        return (ClipAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation setKeyFrame(KeyFrame<Integer> keyFrame, KeyFrame<Integer> keyFrame2) {
        return (ClipAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ClipAnimation addKeyFrame(KeyFrame<Integer> keyFrame) {
        return (ClipAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    public void setClipIntensity(float f) {
        String strValueOf = String.valueOf(f);
        Log.i(this.TAG, "setClipIntensity : " + strValueOf);
        this.clipIntensity = f;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void updateTargetValue(Object obj) {
        if (this.firstTarget == null || obj == null) {
            return;
        }
        setClipIntensity(((Float) obj).floatValue());
    }
}
