package com.samsung.android.graphics.spr.document.animator;

import android.animation.ArgbEvaluator;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAnimatorStrokeColor extends SprAnimatorBase {
    private int from;
    private int to;

    public SprAnimatorStrokeColor() {
        super((byte) 4);
    }

    public SprAnimatorStrokeColor(SprInputStream in) throws IOException {
        super((byte) 4);
        fromSPR(in);
        init();
    }

    private void init() {
        setIntValues(this.from, this.to);
        setEvaluator(new ArgbEvaluator());
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void fromSPR(SprInputStream in) throws IOException {
        super.fromSPR(in);
        this.from = in.readInt();
        this.to = in.readInt();
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public void toSPR(DataOutputStream out) throws IOException {
        super.toSPR(out);
        out.writeInt(this.from);
        out.writeInt(this.to);
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public int getSPRSize() {
        return super.getSPRSize() + 8;
    }

    @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
    public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
        parameter.isUpdatedStrokeColor = true;
        if (parameter.isLastFrame) {
            parameter.strokeColor = this.to;
        } else {
            parameter.strokeColor = ((Integer) getAnimatedValue()).intValue();
        }
        return true;
    }

    public void set(int from, int to) {
        this.from = from;
        this.to = to;
        init();
    }
}
