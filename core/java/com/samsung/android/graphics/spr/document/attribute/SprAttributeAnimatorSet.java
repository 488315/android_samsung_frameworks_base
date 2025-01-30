package com.samsung.android.graphics.spr.document.attribute;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorAlpha;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorFillColor;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorRotate;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorScale;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorStrokeColor;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorTranslate;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SprAttributeAnimatorSet extends SprAttributeBase {
    public int duration;
    private ArrayList<Animator> mAnimators;
    public int repeatCount;
    public int startOffset;

    public SprAttributeAnimatorSet(byte type) {
        super(SprAttributeBase.TYPE_ANIMATOR_SET);
        this.mAnimators = new ArrayList<>();
    }

    public SprAttributeAnimatorSet(SprInputStream in) throws IOException {
        super(SprAttributeBase.TYPE_ANIMATOR_SET);
        this.mAnimators = new ArrayList<>();
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.startOffset = in.readInt();
        this.duration = in.readInt();
        this.repeatCount = in.readInt();
        int n = in.readInt();
        for (int o = 0; o < n; o++) {
            int type = in.readByte();
            int size = in.readInt();
            switch (type) {
                case 1:
                    this.mAnimators.add(new SprAnimatorTranslate(in));
                    break;
                case 2:
                    this.mAnimators.add(new SprAnimatorScale(in));
                    break;
                case 3:
                    this.mAnimators.add(new SprAnimatorRotate(in));
                    break;
                case 4:
                    this.mAnimators.add(new SprAnimatorStrokeColor(in));
                    break;
                case 5:
                    this.mAnimators.add(new SprAnimatorFillColor(in));
                    break;
                case 6:
                    this.mAnimators.add(new SprAnimatorAlpha(in));
                    break;
                default:
                    in.skip(size);
                    break;
            }
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.startOffset);
        out.writeInt(this.duration);
        out.writeInt(this.repeatCount);
        out.writeInt(this.mAnimators.size());
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator animator = it.next();
            SprAnimatorBase animatorBase = (SprAnimatorBase) animator;
            out.writeByte(animatorBase.mType);
            out.writeInt(animatorBase.getSPRSize());
            animatorBase.toSPR(out);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        int size = 16;
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator animator = it.next();
            size += ((SprAnimatorBase) animator).getSPRSize() + 5;
        }
        return size;
    }

    public ArrayList<Animator> getAnimators() {
        return this.mAnimators;
    }

    public void addAnimatorData(SprAnimatorBase animator) {
        this.mAnimators.add(animator.mo547clone());
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeAnimatorSet mo8903clone() throws CloneNotSupportedException {
        SprAttributeAnimatorSet attribute = (SprAttributeAnimatorSet) super.mo8903clone();
        attribute.mAnimators = new ArrayList<>();
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator animator = it.next();
            attribute.mAnimators.add(animator.mo547clone());
        }
        return attribute;
    }

    public void updateAnimatorInterpolator(TimeInterpolator interpolator) {
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator animator = it.next();
            animator.setInterpolator(interpolator);
        }
    }
}
