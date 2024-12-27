package com.samsung.android.graphics.spr.document.animator;

import com.samsung.android.graphics.spr.document.SprInputStream;

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

    public SprAnimatorAlpha(SprInputStream in) throws IOException {
        super((byte) 6);
        this.from = 0.0f;
        this.to = 0.0f;
        fromSPR(in);
        init();
    }

    private void init() {
        this.from = this.from < 0.0f ? 0.0f : this.from;
        this.from = this.from > 1.0f ? 1.0f : this.from;
        this.to = this.to >= 0.0f ? this.to : 0.0f;
        this.to = this.to <= 1.0f ? this.to : 1.0f;
        setFloatValues(this.from, this.to);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream in) throws IOException {
        super.fromSPR(in);
        this.from = in.readFloat();
        this.to = in.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream out) throws IOException {
        super.toSPR(out);
        this.from = this.from < 0.0f ? 0.0f : this.from;
        this.from = this.from > 1.0f ? 1.0f : this.from;
        this.to = this.to >= 0.0f ? this.to : 0.0f;
        this.to = this.to <= 1.0f ? this.to : 1.0f;
        out.writeFloat(this.from);
        out.writeFloat(this.to);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 8;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
        if (parameter.isLastFrame) {
            parameter.alpha = this.to;
            return false;
        }
        parameter.alpha = ((Float) getAnimatedValue()).floatValue();
        return false;
    }

    public void set(float from, float to) {
        this.from = from;
        this.to = to;
        init();
    }
}
