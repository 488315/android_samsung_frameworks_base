package com.samsung.android.graphics.spr.document.animator;

import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAnimatorAlpha extends SprAnimatorBase {
    private float from;
    private float to;

    public SprAnimatorAlpha() {
        super((byte) 6);
        this.from = 0.0f;
        this.to = 0.0f;
    }

    public SprAnimatorAlpha(SprInputStream sprInputStream) throws IOException {
        super((byte) 6);
        this.from = 0.0f;
        this.to = 0.0f;
        fromSPR(sprInputStream);
        init();
    }

    private void init() {
        float f = this.from;
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.from = f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.from = f;
        float f2 = this.to;
        float f3 = f2 >= 0.0f ? f2 : 0.0f;
        this.to = f3;
        float f4 = f3 <= 1.0f ? f3 : 1.0f;
        this.to = f4;
        setFloatValues(f, f4);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        super.fromSPR(sprInputStream);
        this.from = sprInputStream.readFloat();
        this.to = sprInputStream.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        super.toSPR(dataOutputStream);
        float f = this.from;
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.from = f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.from = f;
        float f2 = this.to;
        float f3 = f2 >= 0.0f ? f2 : 0.0f;
        this.to = f3;
        this.to = f3 <= 1.0f ? f3 : 1.0f;
        dataOutputStream.writeFloat(f);
        dataOutputStream.writeFloat(this.to);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 8;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter updateParameter) {
        if (updateParameter.isLastFrame) {
            updateParameter.alpha = this.to;
            return false;
        }
        updateParameter.alpha = ((Float) getAnimatedValue()).floatValue();
        return false;
    }

    public void set(float f, float f2) {
        this.from = f;
        this.to = f2;
        init();
    }
}
