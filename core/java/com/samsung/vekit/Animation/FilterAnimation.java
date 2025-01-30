package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class FilterAnimation extends Animation<Integer> {
  protected Filter filter;

  public FilterAnimation(VEContext context, int id, String name) {
    super(context, AnimationType.FILTER, id, name);
  }

  public Filter getFilter() {
    return this.filter;
  }

  public FilterAnimation setFilter(Filter filter) {
    this.filter = filter;
    return this;
  }

  @Override // com.samsung.vekit.Animation.Animation
  public FilterAnimation setTarget(Element target) {
    return (FilterAnimation) super.setTarget(target);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public FilterAnimation setBezierControlPoint(
      float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
    return (FilterAnimation)
        super.setBezierControlPoint(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public FilterAnimation setStartTime(long startTime) {
    return (FilterAnimation) super.setStartTime(startTime);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public FilterAnimation setKeyFrameList(ArrayList<KeyFrame<Integer>> keyFrameList) {
    return (FilterAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public FilterAnimation setKeyFrame(KeyFrame<Integer> keyFrame) {
    return (FilterAnimation) super.setKeyFrame((KeyFrame) keyFrame);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationStarted(Object interpolatedValue) {
    Log.m98i(this.TAG, "onAnimationStarted : " + this.f3097id + ", " + this.name);
    super.onAnimationStarted(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationUpdated(Object interpolatedValue) {
    Log.m98i(this.TAG, "onAnimationUpdated : " + this.f3097id + ", " + this.name);
    updateTargetValue(interpolatedValue);
    super.onAnimationUpdated(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationFinished(Object interpolatedValue) {
    Log.m98i(this.TAG, "onAnimationFinished : " + this.f3097id + ", " + this.name);
    updateTargetValue(interpolatedValue);
    super.onAnimationFinished(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationCanceled(Object interpolatedValue) {
    Log.m98i(this.TAG, "onAnimationCanceled : " + this.f3097id + ", " + this.name);
    updateTargetValue(interpolatedValue);
    super.onAnimationCanceled(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public void updateTargetValue(Object interpolatedValue) {
    if (this.firstTarget == null || interpolatedValue == null) {
      return;
    }
    float intensity = ((Float) interpolatedValue).floatValue();
    ((Item) this.firstTarget).setFilterIntensity(intensity);
  }
}
