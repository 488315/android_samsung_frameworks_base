package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Object.ToneInfo;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class ToneAnimation extends Animation<ToneInfo> {
    public ToneAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, AnimationType.TONE, i, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            ToneType[] toneTypeArrValues = ToneType.values();
            int length = toneTypeArrValues.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                ((Item) this.firstTarget).setToneIntensity(toneTypeArrValues[i], (int) ((ToneInfo) this.rollbackValue).getToneArray()[i2]);
                i++;
                i2++;
            }
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setTarget(Element element) {
        return (ToneAnimation) super.setTarget(element);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setKeyFrameList(ArrayList<KeyFrame<ToneInfo>> arrayList) {
        return (ToneAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setKeyFrame(KeyFrame<ToneInfo> keyFrame, KeyFrame<ToneInfo> keyFrame2) {
        return (ToneAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation addKeyFrame(KeyFrame<ToneInfo> keyFrame) {
        return (ToneAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (ToneAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public ToneAnimation setStartTime(long j) {
        return (ToneAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation, com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object obj) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        updateTargetValue(obj);
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
        float[] fArr = (float[]) obj;
        ToneType[] toneTypeArrValues = ToneType.values();
        int length = toneTypeArrValues.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            ((Item) this.firstTarget).setToneIntensity(toneTypeArrValues[i], (int) fArr[i2]);
            i++;
            i2++;
        }
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public ToneAnimation setKeyFrame(KeyFrame<ToneInfo> keyFrame) {
        return (ToneAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
