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

/* loaded from: classes6.dex */
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

    public SprDrawableAnimationValue(Drawable drawable, SprDocument sprDocument) {
        super((byte) 1, drawable, sprDocument);
        this.mAnimatingList = new ArrayList<>();
    }

    @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
    public void start() {
        SprAttributeStroke sprAttributeStroke;
        SprAttributeFill sprAttributeFill;
        super.start();
        this.mAnimatingList.clear();
        long jUptimeMillis = SystemClock.uptimeMillis();
        Iterator<SprObjectBase> it = this.mDocument.getValueAnimationObjects().iterator();
        while (it.hasNext()) {
            SprObjectBase next = it.next();
            Iterator<SprAttributeBase> it2 = next.mAttributeList.iterator();
            SprAttributeAnimatorSet sprAttributeAnimatorSet = null;
            SprAttributeFill sprAttributeFill2 = null;
            SprAttributeStroke sprAttributeStroke2 = null;
            SprAttributeMatrix sprAttributeMatrixMo9233clone = null;
            while (it2.hasNext()) {
                SprAttributeBase next2 = it2.next();
                byte b = next2.mType;
                if (b == 32) {
                    sprAttributeFill2 = (SprAttributeFill) next2;
                } else if (b == 35) {
                    sprAttributeStroke2 = (SprAttributeStroke) next2;
                } else if (b == 64) {
                    sprAttributeMatrixMo9233clone = (SprAttributeMatrix) next2;
                } else if (b == 97) {
                    sprAttributeAnimatorSet = (SprAttributeAnimatorSet) next2;
                }
            }
            if (sprAttributeAnimatorSet != null) {
                AnimatorData animatorData = new AnimatorData();
                animatorData.animatorSet = new AnimatorSet();
                animatorData.animatorSet.playTogether(sprAttributeAnimatorSet.getAnimators());
                if (sprAttributeFill2 == null) {
                    if (next.hasFillAnimation) {
                        sprAttributeFill = new SprAttributeFill();
                    } else {
                        sprAttributeFill = new SprAttributeFill((byte) 0, 0);
                    }
                    next.getIntrinsic().appendAttribute(sprAttributeFill);
                    try {
                        sprAttributeFill2 = (SprAttributeFill) sprAttributeFill.mo9233clone();
                        next.appendAttribute(sprAttributeFill2);
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (sprAttributeStroke2 == null) {
                    if (next.hasStrokeAnimation) {
                        sprAttributeStroke = new SprAttributeStroke();
                    } else {
                        sprAttributeStroke = new SprAttributeStroke((byte) 0, 0);
                    }
                    next.getIntrinsic().appendAttribute(sprAttributeStroke);
                    try {
                        sprAttributeStroke2 = (SprAttributeStroke) sprAttributeStroke.mo9233clone();
                        next.appendAttribute(sprAttributeStroke2);
                    } catch (CloneNotSupportedException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                if (sprAttributeMatrixMo9233clone == null) {
                    SprAttributeMatrix sprAttributeMatrix = new SprAttributeMatrix();
                    next.getIntrinsic().appendAttribute(sprAttributeMatrix);
                    try {
                        sprAttributeMatrixMo9233clone = sprAttributeMatrix.mo9233clone();
                        next.appendAttribute(sprAttributeMatrixMo9233clone);
                    } catch (CloneNotSupportedException e3) {
                        throw new RuntimeException(e3);
                    }
                }
                animatorData.matrix = sprAttributeMatrixMo9233clone;
                animatorData.fillPaint = sprAttributeFill2;
                animatorData.strokePaint = sprAttributeStroke2;
                animatorData.object = next;
                animatorData.startTime = jUptimeMillis;
                animatorData.duration = sprAttributeAnimatorSet.duration;
                animatorData.repeatCount = sprAttributeAnimatorSet.repeatCount;
                this.mAnimatingList.add(animatorData);
            }
        }
        this.mDrawable.scheduleSelf(this, jUptimeMillis);
    }

    @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
    public void stop() {
        super.stop();
        Iterator<AnimatorData> it = this.mAnimatingList.iterator();
        while (it.hasNext()) {
            it.next().animatorSet.cancel();
        }
        this.mAnimatingList.clear();
    }

    @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
    public void update() {
        super.update();
        Iterator<AnimatorData> it = this.mAnimatingList.iterator();
        while (it.hasNext()) {
            updateAnimatorData(it.next(), false);
        }
    }

    public boolean updateAnimatorData(AnimatorData animatorData, boolean z) {
        animatorData.updateParameter.isLastFrame = z;
        SprAnimatorBase.UpdateParameter updateParameter = animatorData.updateParameter;
        SprAnimatorBase.UpdateParameter updateParameter2 = animatorData.updateParameter;
        SprAnimatorBase.UpdateParameter updateParameter3 = animatorData.updateParameter;
        SprAnimatorBase.UpdateParameter updateParameter4 = animatorData.updateParameter;
        boolean z2 = false;
        animatorData.updateParameter.isUpdatedStrokeColor = false;
        updateParameter4.isUpdatedFillColor = false;
        updateParameter3.isUpdatedTranslate = false;
        updateParameter2.isUpdatedRotate = false;
        updateParameter.isUpdatedScale = false;
        animatorData.updateParameter.alpha = animatorData.object.alpha;
        Iterator<Animator> it = animatorData.animatorSet.getChildAnimations().iterator();
        while (it.hasNext()) {
            if (((SprAnimatorBase) it.next()).update(animatorData.updateParameter)) {
                z2 = true;
            }
        }
        animatorData.matrix.reset();
        if (animatorData.updateParameter.isUpdatedScale) {
            animatorData.matrix.matrix.postScale(animatorData.updateParameter.scaleX, animatorData.updateParameter.scaleY, animatorData.updateParameter.scalePivotX, animatorData.updateParameter.scalePivotY);
        }
        if (animatorData.updateParameter.isUpdatedRotate) {
            animatorData.matrix.matrix.postRotate(animatorData.updateParameter.rotateDegree, animatorData.updateParameter.rotatePivotX, animatorData.updateParameter.rotatePivotY);
        }
        if (animatorData.updateParameter.isUpdatedTranslate) {
            animatorData.matrix.matrix.postTranslate(animatorData.updateParameter.translateDx, animatorData.updateParameter.translateDy);
        }
        if (animatorData.updateParameter.isUpdatedFillColor) {
            animatorData.fillPaint.color = animatorData.updateParameter.fillColor;
        }
        if (animatorData.updateParameter.isUpdatedStrokeColor) {
            animatorData.strokePaint.color = animatorData.updateParameter.strokeColor;
        }
        animatorData.object.alpha = animatorData.updateParameter.alpha;
        return z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zUpdateAnimatorData;
        if (this.mAnimatingList.size() == 0) {
            this.mIsRunning = false;
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        int i = 0;
        while (i < this.mAnimatingList.size()) {
            AnimatorData animatorData = this.mAnimatingList.get(i);
            if (animatorData.isRunning) {
                if (jUptimeMillis > animatorData.startTime + animatorData.duration) {
                    zUpdateAnimatorData = updateAnimatorData(animatorData, true);
                    animatorData.animatorSet.cancel();
                    if (animatorData.repeatCount != 0) {
                        animatorData.animatorSet.start();
                        animatorData.startTime = jUptimeMillis;
                        if (animatorData.repeatCount > 0) {
                            animatorData.repeatCount--;
                        }
                    } else {
                        this.mAnimatingList.remove(i);
                        i--;
                    }
                } else {
                    zUpdateAnimatorData = updateAnimatorData(animatorData, false);
                }
                if (zUpdateAnimatorData) {
                    animatorData.object.preDraw(this.mDocument);
                }
            } else if (jUptimeMillis > animatorData.startTime) {
                animatorData.animatorSet.start();
                animatorData.startTime = jUptimeMillis;
                animatorData.isRunning = true;
            }
            i++;
        }
        if (this.mAnimatingList.size() > 0) {
            this.mDrawable.scheduleSelf(this, jUptimeMillis + this.mInterval);
        }
        this.mDrawable.invalidateSelf();
    }
}
