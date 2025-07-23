package com.samsung.android.graphics.spr.document.animator;

import android.animation.PropertyValuesHolder;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
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

    public SprAnimatorScale(SprInputStream sprInputStream) throws IOException {
        super((byte) 2);
        fromSPR(sprInputStream);
        init();
    }

    private void init() {
        setValues(PropertyValuesHolder.ofFloat("x", this.fromX, this.toX), PropertyValuesHolder.ofFloat("y", this.fromY, this.toY));
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        super.fromSPR(sprInputStream);
        this.pivotX = sprInputStream.readFloat();
        this.pivotY = sprInputStream.readFloat();
        this.fromX = sprInputStream.readFloat();
        this.fromY = sprInputStream.readFloat();
        this.toX = sprInputStream.readFloat();
        this.toY = sprInputStream.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        super.toSPR(dataOutputStream);
        dataOutputStream.writeFloat(this.pivotX);
        dataOutputStream.writeFloat(this.pivotY);
        dataOutputStream.writeFloat(this.fromX);
        dataOutputStream.writeFloat(this.fromY);
        dataOutputStream.writeFloat(this.toX);
        dataOutputStream.writeFloat(this.toY);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 24;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter updateParameter) {
        updateParameter.isUpdatedScale = true;
        updateParameter.scalePivotX = this.pivotX;
        updateParameter.scalePivotY = this.pivotY;
        if (updateParameter.isLastFrame) {
            updateParameter.scaleX = this.toX;
            updateParameter.scaleY = this.toY;
            return false;
        }
        updateParameter.scaleX = ((Float) getAnimatedValue("x")).floatValue();
        updateParameter.scaleY = ((Float) getAnimatedValue("y")).floatValue();
        return false;
    }

    public void set(float f, float f2, float f3, float f4, float f5, float f6) {
        this.fromX = f;
        this.fromY = f2;
        this.toX = f3;
        this.toY = f4;
        this.pivotX = f5;
        this.pivotY = f6;
        init();
    }
}
