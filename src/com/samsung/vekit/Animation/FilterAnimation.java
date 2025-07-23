package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.FilterInfo;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class FilterAnimation extends Animation<FilterInfo> {
    public FilterAnimation(VEContext vEContext, int i, String str) {
        super(vEContext, AnimationType.FILTER, i, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            ((Item) this.firstTarget).setFilter(((FilterInfo) this.rollbackValue).getFilter());
            ((Item) this.firstTarget).setFilterIntensity(((FilterInfo) this.rollbackValue).getIntensity());
            this.firstTarget.update();
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setTarget(Element element) {
        return (FilterAnimation) super.setTarget(element);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setBezierControlPoint(float f, float f2, float f3, float f4) {
        return (FilterAnimation) super.setBezierControlPoint(f, f2, f3, f4);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setStartTime(long j) {
        return (FilterAnimation) super.setStartTime(j);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setKeyFrameList(ArrayList<KeyFrame<FilterInfo>> arrayList) {
        return (FilterAnimation) super.setKeyFrameList((ArrayList) arrayList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setKeyFrame(KeyFrame<FilterInfo> keyFrame, KeyFrame<FilterInfo> keyFrame2) {
        return (FilterAnimation) super.setKeyFrame((KeyFrame) keyFrame, (KeyFrame) keyFrame2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation addKeyFrame(KeyFrame<FilterInfo> keyFrame) {
        return (FilterAnimation) super.addKeyFrame((KeyFrame) keyFrame);
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
    public void updateTargetValue(Object obj) {
        if (this.firstTarget == null || obj == null) {
            return;
        }
        float[] fArr = (float[]) obj;
        ((Item) this.firstTarget).setFilter(this.context.getFilterManager().get((int) fArr[0]));
        ((Item) this.firstTarget).setFilterIntensity(fArr[1]);
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public FilterAnimation setKeyFrame(KeyFrame<FilterInfo> keyFrame) {
        return (FilterAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
