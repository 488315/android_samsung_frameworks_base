package com.samsung.android.graphics.spr.document.animator;

import android.animation.PropertyValuesHolder;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAnimatorTranslate extends SprAnimatorBase {
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;

    public SprAnimatorTranslate() {
        super((byte) 1);
    }

    public SprAnimatorTranslate(SprInputStream in) throws IOException {
        super((byte) 1);
        fromSPR(in);
        init();
    }

    private void init() {
        setValues(
                PropertyValuesHolder.ofFloat("x", this.fromX, this.toX),
                PropertyValuesHolder.ofFloat("y", this.fromY, this.toY));
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream in) throws IOException {
        super.fromSPR(in);
        this.fromX = in.readFloat();
        this.fromY = in.readFloat();
        this.toX = in.readFloat();
        this.toY = in.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream out) throws IOException {
        super.toSPR(out);
        out.writeFloat(this.fromX);
        out.writeFloat(this.fromY);
        out.writeFloat(this.toX);
        out.writeFloat(this.toY);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
        parameter.isUpdatedTranslate = true;
        if (parameter.isLastFrame) {
            parameter.translateDx = this.toX;
            parameter.translateDy = this.toY;
            return false;
        }
        parameter.translateDx = ((Float) getAnimatedValue("x")).floatValue();
        parameter.translateDy = ((Float) getAnimatedValue("y")).floatValue();
        return false;
    }

    public void set(float fromX, float fromY, float toX, float toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        init();
    }
}
