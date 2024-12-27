package com.samsung.vekit.Animation;

import android.util.Log;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.FilterInfo;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;

import java.util.ArrayList;

public class FilterAnimation extends Animation<FilterInfo> {
    public FilterAnimation(VEContext context, int id, String name) {
        super(context, AnimationType.FILTER, id, name);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void rollback() {
        if (isEnableRollback()) {
            ((Item) this.firstTarget).setFilter(((FilterInfo) this.rollbackValue).getFilter());
            ((Item) this.firstTarget)
                    .setFilterIntensity(((FilterInfo) this.rollbackValue).getIntensity());
            this.firstTarget.update();
        }
        this.enableRollback = false;
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setTarget(Element target) {
        return (FilterAnimation) super.setTarget(target);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        return (FilterAnimation)
                super.setBezierControlPoint(
                        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setStartTime(long startTime) {
        return (FilterAnimation) super.setStartTime(startTime);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setKeyFrameList(ArrayList<KeyFrame<FilterInfo>> keyFrameList) {
        return (FilterAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation setKeyFrame(
            KeyFrame<FilterInfo> firstKeyFrame, KeyFrame<FilterInfo> secondKeyFrame) {
        return (FilterAnimation)
                super.setKeyFrame((KeyFrame) firstKeyFrame, (KeyFrame) secondKeyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public FilterAnimation addKeyFrame(KeyFrame<FilterInfo> keyFrame) {
        return (FilterAnimation) super.addKeyFrame((KeyFrame) keyFrame);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationStarted(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationStarted : " + this.id + ", " + this.name);
        super.onAnimationStarted(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationUpdated(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationUpdated : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationUpdated(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationFinished(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationFinished : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationFinished(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation,
              // com.samsung.vekit.Listener.AnimationStatusListener
    public void onAnimationCanceled(Object interpolatedValue) {
        Log.i(this.TAG, "onAnimationCanceled : " + this.id + ", " + this.name);
        updateTargetValue(interpolatedValue);
        super.onAnimationCanceled(interpolatedValue);
    }

    @Override // com.samsung.vekit.Animation.Animation
    public void updateTargetValue(Object interpolatedValue) {
        if (this.firstTarget == null || interpolatedValue == null) {
            return;
        }
        float[] interpolatedFilterInfo = (float[]) interpolatedValue;
        int filterId = (int) interpolatedFilterInfo[0];
        Filter filter = this.context.getFilterManager().get(filterId);
        ((Item) this.firstTarget).setFilter(filter);
        float intensity = interpolatedFilterInfo[1];
        ((Item) this.firstTarget).setFilterIntensity(intensity);
    }

    @Override // com.samsung.vekit.Animation.Animation
    @Deprecated
    public FilterAnimation setKeyFrame(KeyFrame<FilterInfo> keyFrame) {
        return (FilterAnimation) super.setKeyFrame((KeyFrame) keyFrame);
    }
}
