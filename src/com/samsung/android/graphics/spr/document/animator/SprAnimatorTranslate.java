package com.samsung.android.graphics.spr.document.animator;

import android.animation.PropertyValuesHolder;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
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

    public SprAnimatorTranslate(SprInputStream sprInputStream) throws IOException {
        super((byte) 1);
        fromSPR(sprInputStream);
        init();
    }

    private void init() {
        setValues(PropertyValuesHolder.ofFloat("x", this.fromX, this.toX), PropertyValuesHolder.ofFloat("y", this.fromY, this.toY));
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        super.fromSPR(sprInputStream);
        this.fromX = sprInputStream.readFloat();
        this.fromY = sprInputStream.readFloat();
        this.toX = sprInputStream.readFloat();
        this.toY = sprInputStream.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        super.toSPR(dataOutputStream);
        dataOutputStream.writeFloat(this.fromX);
        dataOutputStream.writeFloat(this.fromY);
        dataOutputStream.writeFloat(this.toX);
        dataOutputStream.writeFloat(this.toY);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter updateParameter) {
        updateParameter.isUpdatedTranslate = true;
        if (updateParameter.isLastFrame) {
            updateParameter.translateDx = this.toX;
            updateParameter.translateDy = this.toY;
            return false;
        }
        updateParameter.translateDx = ((Float) getAnimatedValue("x")).floatValue();
        updateParameter.translateDy = ((Float) getAnimatedValue("y")).floatValue();
        return false;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.fromX = f;
        this.fromY = f2;
        this.toX = f3;
        this.toY = f4;
        init();
    }
}
