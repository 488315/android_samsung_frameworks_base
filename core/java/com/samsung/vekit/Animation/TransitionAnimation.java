package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.Type.TransitionType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.Item;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class TransitionAnimation extends Animation<Float> {
  protected Item secondTarget;
  protected TransitionType transitionType;

  public TransitionAnimation(
      VEContext context, int id, String name, TransitionType transitionType) {
    super(context, AnimationType.TRANSITION, id, name);
    this.transitionType = transitionType;
  }

  public TransitionType getTransitionType() {
    return this.transitionType;
  }

  public TransitionAnimation setTargets(Item firstTarget, Item secondTarget) {
    try {
      checkValidItem(firstTarget);
      checkValidItem(secondTarget);
      this.firstTarget = firstTarget;
      this.secondTarget = secondTarget;
      return this;
    } catch (Exception e) {
      Log.m97e(this.TAG, "setTargets: ", e);
      return this;
    }
  }

  public TransitionAnimation setFirstTarget(Item firstTarget) {
    try {
      checkValidItem(firstTarget);
      this.firstTarget = firstTarget;
      return this;
    } catch (Exception e) {
      Log.m97e(this.TAG, "setFirstTarget: ", e);
      return this;
    }
  }

  public TransitionAnimation setSecondTarget(Item secondTarget) {
    try {
      checkValidItem(secondTarget);
      this.secondTarget = secondTarget;
      return this;
    } catch (Exception e) {
      Log.m97e(this.TAG, "setSecondTarget: ", e);
      return this;
    }
  }

  public Element getFirstTarget() {
    return this.firstTarget;
  }

  public Item getSecondTarget() {
    return this.secondTarget;
  }

  @Override // com.samsung.vekit.Animation.Animation
  @Deprecated
  public TransitionAnimation setDuration(long duration) {
    setKeyFrame(new KeyFrame(Float.valueOf(0.0f), Float.valueOf(1.0f), duration));
    return this;
  }

  @Override // com.samsung.vekit.Animation.Animation
  public TransitionAnimation setTarget(Element target) {
    return (TransitionAnimation) super.setTarget(target);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public TransitionAnimation setBezierControlPoint(
      float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
    return (TransitionAnimation)
        super.setBezierControlPoint(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
  }

  @Override // com.samsung.vekit.Animation.Animation
  public TransitionAnimation setStartTime(long startTime) {
    return (TransitionAnimation) super.setStartTime(startTime);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationStarted(Object interpolatedValue) {
    super.onAnimationStarted(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationUpdated(Object interpolatedValue) {
    super.onAnimationUpdated(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationFinished(Object interpolatedValue) {
    super.onAnimationFinished(interpolatedValue);
  }

  @Override // com.samsung.vekit.Animation.Animation,
            // com.samsung.vekit.Listener.AnimationStatusListener
  public void onAnimationCanceled(Object interpolatedValue) {
    super.onAnimationCanceled(interpolatedValue);
  }

  public void checkValidItem(final Item item) throws Exception {
    ItemType[] availableTypes = {ItemType.IMAGE, ItemType.VIDEO, ItemType.COLOR};
    boolean valid =
        Arrays.stream(availableTypes)
            .anyMatch(
                new Predicate() { // from class:
                                  // com.samsung.vekit.Animation.TransitionAnimation$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return TransitionAnimation.lambda$checkValidItem$0(Item.this, (ItemType) obj);
                  }
                });
    boolean isOnMediaLayer = item.getParent().getLayerType() == LayerType.MEDIA;
    if (!valid || !isOnMediaLayer) {
      throw new Exception("isInvalidElement : please set correct Items to TransitionAnimation.");
    }
  }

  static /* synthetic */ boolean lambda$checkValidItem$0(Item item, ItemType type) {
    return type == item.getItemType();
  }
}
