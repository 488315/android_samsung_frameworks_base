package com.android.internal.widget.remotecompose.core.operations.layout.animation;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.DecoratorComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.animation.AnimationSpec;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.PaddingModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class AnimateMeasure {
    private final Component mComponent;
    private float mDuration;
    private float mDurationVisibilityChange;
    private AnimationSpec.ANIMATION mEnterAnimation;
    private AnimationSpec.ANIMATION mExitAnimation;
    private FloatAnimation mMotionEasing;
    private int mMotionEasingType;
    private final ComponentMeasure mOriginal;
    private ParticleAnimation mParticleAnimation;
    private long mStartTime;
    private final ComponentMeasure mTarget;
    private FloatAnimation mVisibilityEasing;
    private int mVisibilityEasingType;
    private float mP = 0.0f;
    private float mVp = 0.0f;
    public PaintBundle paint = new PaintBundle();

    public AnimateMeasure(long j, Component component, ComponentMeasure componentMeasure, ComponentMeasure componentMeasure2, float f, float f2, AnimationSpec.ANIMATION animation, AnimationSpec.ANIMATION animation2, int i, int i2) {
        this.mStartTime = System.currentTimeMillis();
        this.mDurationVisibilityChange = this.mDuration;
        this.mEnterAnimation = AnimationSpec.ANIMATION.FADE_IN;
        this.mExitAnimation = AnimationSpec.ANIMATION.FADE_OUT;
        this.mMotionEasingType = 1;
        this.mVisibilityEasingType = 2;
        this.mMotionEasing = new FloatAnimation(this.mMotionEasingType, this.mDuration / 1000.0f, null, 0.0f, Float.NaN);
        this.mVisibilityEasing = new FloatAnimation(this.mVisibilityEasingType, this.mDurationVisibilityChange / 1000.0f, null, 0.0f, Float.NaN);
        this.mStartTime = j;
        this.mComponent = component;
        this.mOriginal = componentMeasure;
        this.mTarget = componentMeasure2;
        this.mDuration = f;
        this.mDurationVisibilityChange = f2;
        this.mEnterAnimation = animation;
        this.mExitAnimation = animation2;
        this.mMotionEasingType = i;
        this.mVisibilityEasingType = i2;
        this.mMotionEasing = new FloatAnimation(this.mMotionEasingType, f / 1000.0f, null, 0.0f, Float.NaN);
        this.mVisibilityEasing = new FloatAnimation(this.mVisibilityEasingType, f2 / 1000.0f, null, 0.0f, Float.NaN);
        this.mMotionEasing.setTargetValue(1.0f);
        this.mVisibilityEasing.setTargetValue(1.0f);
        component.mVisibility = componentMeasure2.getVisibility();
    }

    public void update(long j) {
        float f = j - this.mStartTime;
        float f2 = f / this.mDuration;
        float f3 = f / this.mDurationVisibilityChange;
        this.mP = this.mMotionEasing.get(f2);
        this.mVp = this.mVisibilityEasing.get(f3);
    }

    public void apply(RemoteContext remoteContext) {
        update(remoteContext.currentTime);
        this.mComponent.setX(getX());
        this.mComponent.setY(getY());
        this.mComponent.setWidth(getWidth());
        this.mComponent.setHeight(getHeight());
        this.mComponent.updateVariables(remoteContext);
        float width = this.mComponent.getWidth();
        float height = this.mComponent.getHeight();
        Iterator<Operation> it = this.mComponent.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof PaddingModifierOperation) {
                PaddingModifierOperation paddingModifierOperation = (PaddingModifierOperation) obj;
                width -= paddingModifierOperation.getLeft() + paddingModifierOperation.getRight();
                height -= paddingModifierOperation.getTop() + paddingModifierOperation.getBottom();
            }
            if (obj instanceof DecoratorComponent) {
                ((DecoratorComponent) obj).layout(remoteContext, this.mComponent, width, height);
            }
        }
    }

    public void paint(PaintContext paintContext) {
        if (this.mOriginal.getVisibility() != this.mTarget.getVisibility()) {
            if (this.mTarget.isGone()) {
                switch (AnonymousClass1.$SwitchMap$com$android$internal$widget$remotecompose$core$operations$layout$animation$AnimationSpec$ANIMATION[this.mExitAnimation.ordinal()]) {
                    case 1:
                        if (this.mParticleAnimation == null) {
                            this.mParticleAnimation = new ParticleAnimation();
                        }
                        this.mParticleAnimation.animate(paintContext, this.mComponent, this.mOriginal, this.mTarget, this.mVp);
                        break;
                    case 2:
                        paintContext.save();
                        paintContext.savePaint();
                        this.paint.reset();
                        this.paint.setColor(0.0f, 0.0f, 0.0f, 1.0f - this.mVp);
                        paintContext.applyPaint(this.paint);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restorePaint();
                        paintContext.restore();
                        break;
                    case 3:
                        paintContext.save();
                        paintContext.translate((-this.mVp) * this.mComponent.getParent().getWidth(), 0.0f);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case 4:
                        paintContext.save();
                        paintContext.savePaint();
                        this.paint.reset();
                        this.paint.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                        paintContext.applyPaint(this.paint);
                        paintContext.translate(this.mVp * this.mComponent.getParent().getWidth(), 0.0f);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restorePaint();
                        paintContext.restore();
                        break;
                    case 5:
                        paintContext.save();
                        paintContext.translate(0.0f, (-this.mVp) * this.mComponent.getParent().getHeight());
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case 6:
                        paintContext.save();
                        paintContext.translate(0.0f, this.mVp * this.mComponent.getParent().getHeight());
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    default:
                        if (this.mParticleAnimation == null) {
                            this.mParticleAnimation = new ParticleAnimation();
                        }
                        this.mParticleAnimation.animate(paintContext, this.mComponent, this.mOriginal, this.mTarget, this.mVp);
                        break;
                }
            } else if (this.mOriginal.isGone() && this.mTarget.isVisible()) {
                switch (this.mEnterAnimation) {
                    case SLIDE_LEFT:
                        paintContext.save();
                        paintContext.translate((1.0f - this.mVp) * this.mComponent.getParent().getWidth(), 0.0f);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case SLIDE_RIGHT:
                        paintContext.save();
                        paintContext.translate((-(1.0f - this.mVp)) * this.mComponent.getParent().getWidth(), 0.0f);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case SLIDE_TOP:
                        paintContext.save();
                        paintContext.translate(0.0f, (1.0f - this.mVp) * this.mComponent.getParent().getHeight());
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case SLIDE_BOTTOM:
                        paintContext.save();
                        paintContext.translate(0.0f, (-(1.0f - this.mVp)) * this.mComponent.getParent().getHeight());
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restore();
                        break;
                    case ROTATE:
                        float x = this.mTarget.getX() + (this.mTarget.getW() / 2.0f);
                        float y = this.mTarget.getY() + (this.mTarget.getH() / 2.0f);
                        paintContext.save();
                        paintContext.savePaint();
                        paintContext.matrixRotate(this.mVp * 360.0f, x, y);
                        float f = this.mVp;
                        paintContext.matrixScale(f * 1.0f, f * 1.0f, x, y);
                        this.paint.reset();
                        this.paint.setColor(0.0f, 0.0f, 0.0f, this.mVp);
                        paintContext.applyPaint(this.paint);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restorePaint();
                        paintContext.restore();
                        break;
                    case FADE_IN:
                        paintContext.save();
                        paintContext.savePaint();
                        this.paint.reset();
                        this.paint.setColor(0.0f, 0.0f, 0.0f, this.mVp);
                        paintContext.applyPaint(this.paint);
                        paintContext.saveLayer(this.mComponent.getX(), this.mComponent.getY(), this.mComponent.getWidth(), this.mComponent.getHeight());
                        this.mComponent.paintingComponent(paintContext);
                        paintContext.restore();
                        paintContext.restorePaint();
                        paintContext.restore();
                        break;
                }
            } else {
                this.mComponent.paintingComponent(paintContext);
            }
        } else if (this.mTarget.isVisible()) {
            this.mComponent.paintingComponent(paintContext);
        }
        if (this.mP < 1.0f || this.mVp < 1.0f) {
            return;
        }
        this.mComponent.mVisibility = this.mTarget.getVisibility();
    }

    public boolean isDone() {
        return this.mP >= 1.0f && this.mVp >= 1.0f;
    }

    public float getX() {
        return (this.mOriginal.getX() * (1.0f - this.mP)) + (this.mTarget.getX() * this.mP);
    }

    public float getY() {
        return (this.mOriginal.getY() * (1.0f - this.mP)) + (this.mTarget.getY() * this.mP);
    }

    public float getWidth() {
        return (this.mOriginal.getW() * (1.0f - this.mP)) + (this.mTarget.getW() * this.mP);
    }

    public float getHeight() {
        return (this.mOriginal.getH() * (1.0f - this.mP)) + (this.mTarget.getH() * this.mP);
    }

    public float getVisibility() {
        if (this.mOriginal.getVisibility() == this.mTarget.getVisibility()) {
            return 1.0f;
        }
        if (this.mTarget.isVisible()) {
            return this.mVp;
        }
        return 1.0f - this.mVp;
    }

    public void updateTarget(ComponentMeasure componentMeasure, long j) {
        this.mOriginal.setX(getX());
        this.mOriginal.setY(getY());
        this.mOriginal.setW(getWidth());
        this.mOriginal.setH(getHeight());
        float x = this.mTarget.getX();
        float y = this.mTarget.getY();
        float w = this.mTarget.getW();
        float h = this.mTarget.getH();
        int visibility = this.mTarget.getVisibility();
        if (x == componentMeasure.getX() && y == componentMeasure.getY() && w == componentMeasure.getW() && h == componentMeasure.getH() && visibility == componentMeasure.getVisibility()) {
            return;
        }
        this.mTarget.setX(componentMeasure.getX());
        this.mTarget.setY(componentMeasure.getY());
        this.mTarget.setW(componentMeasure.getW());
        this.mTarget.setH(componentMeasure.getH());
        this.mTarget.setVisibility(componentMeasure.getVisibility());
    }
}
