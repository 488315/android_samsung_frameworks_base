package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Object.Matrix4;
import com.samsung.vekit.Common.Object.Vector3;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class RotateAnimation extends Animation<Vector3> {
    public RotateAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, AnimationType.ROTATE, i, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            this.firstTarget.getPanel().setRotation((Vector3) this.rollbackValue);
            this.firstTarget.update();
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setKeyFrameList(ArrayList<KeyFrame<Vector3>> arrayList) {
        return (RotateAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setKeyFrame(KeyFrame<Vector3> keyFrame) {
        return (RotateAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setTarget(Element element) {
        return (RotateAnimation) super.setTarget(element);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (RotateAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public RotateAnimation setStartTime(long j) {
        return (RotateAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object obj) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        if (this.firstTarget == null) {
            return;
        }
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
    public void updateTargetValue(Object obj) {
        if (this.firstTarget == null || obj == null) {
            return;
        }
        this.firstTarget.getPanel().setMatrix(new Matrix4((float[]) obj));
    }
}
