package com.samsung.android.graphics.spr.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeAnimatorSet;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeFill;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeMatrix;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStroke;
import com.samsung.android.graphics.spr.document.shape.SprObjectBase;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SprDrawableAnimationValue extends SprDrawableAnimation {
  private final ArrayList<AnimatorData> mAnimatingList;

  private static class AnimatorData {
    public AnimatorSet animatorSet;
    public long duration;
    public SprAttributeFill fillPaint;
    public boolean isRunning;
    public SprAttributeMatrix matrix;
    public SprObjectBase object;
    public int repeatCount;
    public long startTime;
    public SprAttributeStroke strokePaint;
    public SprAnimatorBase.UpdateParameter updateParameter;

    private AnimatorData() {
      this.updateParameter = new SprAnimatorBase.UpdateParameter();
    }
  }

  public SprDrawableAnimationValue(Drawable drawable, SprDocument document) {
    super((byte) 1, drawable, document);
    this.mAnimatingList = new ArrayList<>();
  }

  @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
  public void start() {
    SprAttributeStroke stroke;
    SprAttributeFill fill;
    super.start();
    this.mAnimatingList.clear();
    long now = SystemClock.uptimeMillis();
    Iterator<SprObjectBase> it = this.mDocument.getValueAnimationObjects().iterator();
    while (it.hasNext()) {
      SprObjectBase object = it.next();
      SprAttributeAnimatorSet animator = null;
      SprAttributeMatrix matrix = null;
      SprAttributeFill fill2 = null;
      SprAttributeStroke stroke2 = null;
      Iterator<SprAttributeBase> it2 = object.mAttributeList.iterator();
      while (it2.hasNext()) {
        SprAttributeBase attr = it2.next();
        switch (attr.mType) {
          case 32:
            fill2 = (SprAttributeFill) attr;
            break;
          case 35:
            stroke2 = (SprAttributeStroke) attr;
            break;
          case 64:
            matrix = (SprAttributeMatrix) attr;
            break;
          case 97:
            animator = (SprAttributeAnimatorSet) attr;
            break;
        }
      }
      if (animator != null) {
        AnimatorData animatorData = new AnimatorData();
        animatorData.animatorSet = new AnimatorSet();
        animatorData.animatorSet.playTogether(animator.getAnimators());
        if (fill2 == null) {
          if (object.hasFillAnimation) {
            fill = new SprAttributeFill();
          } else {
            fill = new SprAttributeFill((byte) 0, 0);
          }
          object.getIntrinsic().appendAttribute(fill);
          try {
            fill2 = (SprAttributeFill) fill.mo8903clone();
            object.appendAttribute(fill2);
          } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
          }
        }
        if (stroke2 == null) {
          if (object.hasStrokeAnimation) {
            stroke = new SprAttributeStroke();
          } else {
            stroke = new SprAttributeStroke((byte) 0, 0);
          }
          object.getIntrinsic().appendAttribute(stroke);
          try {
            stroke2 = (SprAttributeStroke) stroke.mo8903clone();
            object.appendAttribute(stroke2);
          } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
          }
        }
        if (matrix == null) {
          SprAttributeMatrix matrix2 = new SprAttributeMatrix();
          object.getIntrinsic().appendAttribute(matrix2);
          try {
            matrix = matrix2.mo8903clone();
            object.appendAttribute(matrix);
          } catch (CloneNotSupportedException e3) {
            throw new RuntimeException(e3);
          }
        }
        animatorData.matrix = matrix;
        animatorData.fillPaint = fill2;
        animatorData.strokePaint = stroke2;
        animatorData.object = object;
        animatorData.startTime = now;
        animatorData.duration = animator.duration;
        animatorData.repeatCount = animator.repeatCount;
        this.mAnimatingList.add(animatorData);
      }
    }
    this.mDrawable.scheduleSelf(this, now);
  }

  @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
  public void stop() {
    super.stop();
    Iterator<AnimatorData> it = this.mAnimatingList.iterator();
    while (it.hasNext()) {
      AnimatorData animatorData = it.next();
      animatorData.animatorSet.cancel();
    }
    this.mAnimatingList.clear();
  }

  @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
  public void update() {
    super.update();
    Iterator<AnimatorData> it = this.mAnimatingList.iterator();
    while (it.hasNext()) {
      AnimatorData animatorData = it.next();
      updateAnimatorData(animatorData, false);
    }
  }

  public boolean updateAnimatorData(AnimatorData animatorData, boolean isLastFrame) {
    animatorData.updateParameter.isLastFrame = isLastFrame;
    SprAnimatorBase.UpdateParameter updateParameter = animatorData.updateParameter;
    SprAnimatorBase.UpdateParameter updateParameter2 = animatorData.updateParameter;
    SprAnimatorBase.UpdateParameter updateParameter3 = animatorData.updateParameter;
    SprAnimatorBase.UpdateParameter updateParameter4 = animatorData.updateParameter;
    animatorData.updateParameter.isUpdatedStrokeColor = false;
    updateParameter4.isUpdatedFillColor = false;
    updateParameter3.isUpdatedTranslate = false;
    updateParameter2.isUpdatedRotate = false;
    updateParameter.isUpdatedScale = false;
    animatorData.updateParameter.alpha = animatorData.object.alpha;
    boolean preDraw = false;
    Iterator<Animator> it = animatorData.animatorSet.getChildAnimations().iterator();
    while (it.hasNext()) {
      Animator animator = it.next();
      if (((SprAnimatorBase) animator).update(animatorData.updateParameter)) {
        preDraw = true;
      }
    }
    animatorData.matrix.reset();
    if (animatorData.updateParameter.isUpdatedScale) {
      animatorData.matrix.matrix.postScale(
          animatorData.updateParameter.scaleX,
          animatorData.updateParameter.scaleY,
          animatorData.updateParameter.scalePivotX,
          animatorData.updateParameter.scalePivotY);
    }
    if (animatorData.updateParameter.isUpdatedRotate) {
      animatorData.matrix.matrix.postRotate(
          animatorData.updateParameter.rotateDegree,
          animatorData.updateParameter.rotatePivotX,
          animatorData.updateParameter.rotatePivotY);
    }
    if (animatorData.updateParameter.isUpdatedTranslate) {
      animatorData.matrix.matrix.postTranslate(
          animatorData.updateParameter.translateDx, animatorData.updateParameter.translateDy);
    }
    if (animatorData.updateParameter.isUpdatedFillColor) {
      animatorData.fillPaint.color = animatorData.updateParameter.fillColor;
    }
    if (animatorData.updateParameter.isUpdatedStrokeColor) {
      animatorData.strokePaint.color = animatorData.updateParameter.strokeColor;
    }
    animatorData.object.alpha = animatorData.updateParameter.alpha;
    return preDraw;
  }

  @Override // java.lang.Runnable
  public void run() {
    boolean preDraw;
    if (this.mAnimatingList.size() == 0) {
      this.mIsRunning = false;
      return;
    }
    long now = SystemClock.uptimeMillis();
    int i = 0;
    while (i < this.mAnimatingList.size()) {
      AnimatorData animatorData = this.mAnimatingList.get(i);
      if (animatorData.isRunning) {
        if (now > animatorData.startTime + animatorData.duration) {
          preDraw = updateAnimatorData(animatorData, true);
          animatorData.animatorSet.cancel();
          if (animatorData.repeatCount != 0) {
            animatorData.animatorSet.start();
            animatorData.startTime = now;
            if (animatorData.repeatCount > 0) {
              animatorData.repeatCount--;
            }
          } else {
            this.mAnimatingList.remove(i);
            i--;
          }
        } else {
          preDraw = updateAnimatorData(animatorData, false);
        }
        if (preDraw) {
          animatorData.object.preDraw(this.mDocument);
        }
      } else if (now > animatorData.startTime) {
        animatorData.animatorSet.start();
        animatorData.startTime = now;
        animatorData.isRunning = true;
      }
      i++;
    }
    if (this.mAnimatingList.size() > 0) {
      this.mDrawable.scheduleSelf(this, this.mInterval + now);
    }
    this.mDrawable.invalidateSelf();
  }
}
