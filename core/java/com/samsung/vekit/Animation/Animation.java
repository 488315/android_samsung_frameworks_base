package com.samsung.vekit.Animation;

import com.samsung.vekit.Common.Object.BezierControlPoint;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.InterpolationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Listener.AnimationStatusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class Animation<T> extends Element implements AnimationStatusListener {
  protected AnimationType animationType;
  protected BezierControlPoint bezierControlPoint;
  protected long duration;
  protected Element firstTarget;
  protected InterpolationType interpolationType;
  protected ArrayList<KeyFrame<T>> keyFrameList;
  protected AnimationStatusListener listener;
  protected long startTime;

  public enum AnimationStatus {
    INITIALIZED,
    STARTED,
    ANIMATING,
    CANCELED,
    FINISHED
  }

  protected Animation(VEContext context, AnimationType type, int id, String name) {
    super(context, ElementType.ANIMATION, id, name);
    this.bezierControlPoint = new BezierControlPoint();
    this.keyFrameList = new ArrayList<>();
    setAnimationType(type);
    this.startTime = 0L;
    this.duration = 0L;
    this.TAG = getClass().getSimpleName();
    this.interpolationType = InterpolationType.LINEAR;
  }

  public Element getTarget() {
    return this.firstTarget;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public Animation<?> setTarget(Element target) {
    this.firstTarget = target;
    return this;
  }

  public void setListener(AnimationStatusListener listener) {
    this.listener = listener;
  }

  public InterpolationType getInterpolationType() {
    return this.interpolationType;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public Animation<?> setBezierControlPoint(
      float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
    this.bezierControlPoint.setValues(
        controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    this.interpolationType = InterpolationType.BEZIER;
    return this;
  }

  public BezierControlPoint getBezierControlPoint() {
    return this.bezierControlPoint;
  }

  public AnimationType getAnimationType() {
    return this.animationType;
  }

  protected void setAnimationType(AnimationType animationType) {
    this.animationType = animationType;
  }

  public long getStartTime() {
    return this.startTime;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public Animation<?> setStartTime(long startTime) {
    this.startTime = startTime;
    return this;
  }

  public long getDuration() {
    return this.duration;
  }

  @Deprecated
  public T getFrom() {
    return null;
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Deprecated
  public Animation<?> setFrom(T from) {
    return this;
  }

  @Deprecated
  public T getTo() {
    return null;
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Deprecated
  public Animation<?> setTo(T to) {
    return this;
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Deprecated
  public Animation<?> setDuration(long duration) {
    return this;
  }

  public void onAnimationStarted(Object interpolatedValue) {
    AnimationStatusListener animationStatusListener = this.listener;
    if (animationStatusListener == null) {
      return;
    }
    animationStatusListener.onAnimationStarted(interpolatedValue);
  }

  public void onAnimationUpdated(Object interpolatedValue) {
    AnimationStatusListener animationStatusListener = this.listener;
    if (animationStatusListener == null) {
      return;
    }
    animationStatusListener.onAnimationUpdated(interpolatedValue);
  }

  public void onAnimationFinished(Object interpolatedValue) {
    AnimationStatusListener animationStatusListener = this.listener;
    if (animationStatusListener == null) {
      return;
    }
    animationStatusListener.onAnimationFinished(interpolatedValue);
  }

  public void onAnimationCanceled(Object interpolatedValue) {
    AnimationStatusListener animationStatusListener = this.listener;
    if (animationStatusListener == null) {
      return;
    }
    animationStatusListener.onAnimationCanceled(interpolatedValue);
  }

  public void updateTargetValue(Object interpolatedValue) {}

  /* JADX WARN: Multi-variable type inference failed */
  public Animation<?> setKeyFrame(KeyFrame<T> keyFrame) {
    this.keyFrameList.clear();
    this.keyFrameList.add(keyFrame);
    this.duration = 0L;
    Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
    while (it.hasNext()) {
      KeyFrame<T> data = it.next();
      this.duration += data.getDuration();
    }
    return this;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public Animation<?> setKeyFrameList(ArrayList<KeyFrame<T>> keyFrameList) {
    this.keyFrameList.clear();
    this.keyFrameList.addAll(keyFrameList);
    this.duration = 0L;
    Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
    while (it.hasNext()) {
      KeyFrame<T> data = it.next();
      this.duration += data.getDuration();
    }
    return this;
  }

  public List<KeyFrame<T>> getKeyFrameList() {
    return Collections.unmodifiableList(this.keyFrameList);
  }

  public void clearKeyFrameList() {
    this.keyFrameList.clear();
  }

  public int getKeyFrameCount() {
    return this.keyFrameList.size();
  }
}
