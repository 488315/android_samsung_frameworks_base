package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class AlphaAnimation extends Animation<Integer> {
  public AlphaAnimation(VEContext context, int id, String name) {
    super(context, AnimationType.ALPHA, id, name);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public AlphaAnimation setTarget(Element target) {
    return (AlphaAnimation) super.setTarget(target);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public AlphaAnimation setBezierControlPoint(
      float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
    return (AlphaAnimation)
        super.setBezierControlPoint(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public AlphaAnimation setStartTime(long startTime) {
    return (AlphaAnimation) super.setStartTime(startTime);
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

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.vekit.Animation.Animation
  public AlphaAnimation setKeyFrameList(ArrayList<KeyFrame<Integer>> keyFrameList) {
    return (AlphaAnimation) super.setKeyFrameList((ArrayList) keyFrameList);
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.vekit.Animation.Animation
  public AlphaAnimation setKeyFrame(KeyFrame<Integer> keyFrame) {
    return (AlphaAnimation) super.setKeyFrame((KeyFrame) keyFrame);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public void updateTargetValue(Object interpolatedValue) {
    if (this.firstTarget == null || interpolatedValue == null) {
      return;
    }
    float opacity = ((Float) interpolatedValue).floatValue();
    ((Item) this.firstTarget).setOpacity(opacity);
  }
}
