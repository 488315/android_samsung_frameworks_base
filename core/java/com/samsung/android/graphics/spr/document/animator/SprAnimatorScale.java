package com.samsung.android.graphics.spr.document.animator;

import android.animation.PropertyValuesHolder;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAnimatorScale extends SprAnimatorBase {
    private float fromX;
    private float fromY;
    private float pivotX;
    private float pivotY;
    private float toX;
    private float toY;

    public SprAnimatorScale() {
        super((byte) 2);
    }

    public SprAnimatorScale(SprInputStream in) throws IOException {
        super((byte) 2);
        fromSPR(in);
        init();
    }

    private void init() {
        setValues(PropertyValuesHolder.ofFloat("x", this.fromX, this.toX), PropertyValuesHolder.ofFloat("y", this.fromY, this.toY));
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream in) throws IOException {
        super.fromSPR(in);
        this.pivotX = in.readFloat();
        this.pivotY = in.readFloat();
        this.fromX = in.readFloat();
        this.fromY = in.readFloat();
        this.toX = in.readFloat();
        this.toY = in.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream out) throws IOException {
        super.toSPR(out);
        out.writeFloat(this.pivotX);
        out.writeFloat(this.pivotY);
        out.writeFloat(this.fromX);
        out.writeFloat(this.fromY);
        out.writeFloat(this.toX);
        out.writeFloat(this.toY);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 24;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
        parameter.isUpdatedScale = true;
        parameter.scalePivotX = this.pivotX;
        parameter.scalePivotY = this.pivotY;
        if (parameter.isLastFrame) {
            parameter.scaleX = this.toX;
            parameter.scaleY = this.toY;
            return false;
        }
        parameter.scaleX = ((Float) getAnimatedValue("x")).floatValue();
        parameter.scaleY = ((Float) getAnimatedValue("y")).floatValue();
        return false;
    }

    public void set(float fromX, float fromY, float toX, float toY, float pivotX, float pivotY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        init();
    }
}
