package com.android.internal.widget.remotecompose.core.operations.layout.measure;

import com.android.internal.widget.remotecompose.core.operations.layout.Component;

/* loaded from: classes6.dex */
public class ComponentMeasure {
    float mH;
    int mId;
    int mVisibility;
    float mW;
    float mX;
    float mY;

    public void setX(float f) {
        this.mX = f;
    }

    public void setY(float f) {
        this.mY = f;
    }

    public void setW(float f) {
        this.mW = f;
    }

    public void setH(float f) {
        this.mH = f;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getW() {
        return this.mW;
    }

    public float getH() {
        return this.mH;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public void setVisibility(int i) {
        this.mVisibility = i;
    }

    public ComponentMeasure(int i, float f, float f2, float f3, float f4, int i2) {
        this.mId = i;
        this.mX = f;
        this.mY = f2;
        this.mW = f3;
        this.mH = f4;
        this.mVisibility = i2;
    }

    public ComponentMeasure(int i, float f, float f2, float f3, float f4) {
        this(i, f, f2, f3, f4, 1);
    }

    public ComponentMeasure(Component component) {
        this(component.getComponentId(), component.getX(), component.getY(), component.getWidth(), component.getHeight(), component.mVisibility);
    }

    public void copyFrom(ComponentMeasure componentMeasure) {
        this.mX = componentMeasure.mX;
        this.mY = componentMeasure.mY;
        this.mW = componentMeasure.mW;
        this.mH = componentMeasure.mH;
        this.mVisibility = componentMeasure.mVisibility;
    }

    public boolean same(ComponentMeasure componentMeasure) {
        return this.mX == componentMeasure.mX && this.mY == componentMeasure.mY && this.mW == componentMeasure.mW && this.mH == componentMeasure.mH && this.mVisibility == componentMeasure.mVisibility;
    }

    public boolean isGone() {
        return Component.Visibility.isGone(this.mVisibility);
    }

    public boolean isVisible() {
        return Component.Visibility.isVisible(this.mVisibility);
    }

    public boolean isInvisible() {
        return Component.Visibility.isInvisible(this.mVisibility);
    }

    public void clearVisibilityOverride() {
        this.mVisibility = Component.Visibility.clearOverride(this.mVisibility);
    }

    public void addVisibilityOverride(int i) {
        int iClearOverride = Component.Visibility.clearOverride(this.mVisibility);
        this.mVisibility = iClearOverride;
        this.mVisibility = Component.Visibility.add(iClearOverride, i);
    }
}
