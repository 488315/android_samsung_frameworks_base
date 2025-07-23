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

/* loaded from: classes6.dex */
public class SprAttributeAnimatorSet extends SprAttributeBase {
    public int duration;
    private ArrayList<Animator> mAnimators;
    public int repeatCount;
    public int startOffset;

    public SprAttributeAnimatorSet(byte b) {
        super(SprAttributeBase.TYPE_ANIMATOR_SET);
        this.mAnimators = new ArrayList<>();
    }

    public SprAttributeAnimatorSet(SprInputStream sprInputStream) throws IOException {
        super(SprAttributeBase.TYPE_ANIMATOR_SET);
        this.mAnimators = new ArrayList<>();
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.startOffset = sprInputStream.readInt();
        this.duration = sprInputStream.readInt();
        this.repeatCount = sprInputStream.readInt();
        int readInt = sprInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            byte readByte = sprInputStream.readByte();
            int readInt2 = sprInputStream.readInt();
            switch (readByte) {
                case 1:
                    this.mAnimators.add(new SprAnimatorTranslate(sprInputStream));
                    break;
                case 2:
                    this.mAnimators.add(new SprAnimatorScale(sprInputStream));
                    break;
                case 3:
                    this.mAnimators.add(new SprAnimatorRotate(sprInputStream));
                    break;
                case 4:
                    this.mAnimators.add(new SprAnimatorStrokeColor(sprInputStream));
                    break;
                case 5:
                    this.mAnimators.add(new SprAnimatorFillColor(sprInputStream));
                    break;
                case 6:
                    this.mAnimators.add(new SprAnimatorAlpha(sprInputStream));
                    break;
                default:
                    sprInputStream.skip(readInt2);
                    break;
            }
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.startOffset);
        dataOutputStream.writeInt(this.duration);
        dataOutputStream.writeInt(this.repeatCount);
        dataOutputStream.writeInt(this.mAnimators.size());
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            SprAnimatorBase sprAnimatorBase = (SprAnimatorBase) it.next();
            dataOutputStream.writeByte(sprAnimatorBase.mType);
            dataOutputStream.writeInt(sprAnimatorBase.getSPRSize());
            sprAnimatorBase.toSPR(dataOutputStream);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        Iterator<Animator> it = this.mAnimators.iterator();
        int i = 16;
        while (it.hasNext()) {
            i += ((SprAnimatorBase) it.next()).getSPRSize() + 5;
        }
        return i;
    }

    public ArrayList<Animator> getAnimators() {
        return this.mAnimators;
    }

    public void addAnimatorData(SprAnimatorBase sprAnimatorBase) {
        this.mAnimators.add(sprAnimatorBase.mo76clone());
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeAnimatorSet mo9221clone() throws CloneNotSupportedException {
        SprAttributeAnimatorSet sprAttributeAnimatorSet = (SprAttributeAnimatorSet) super.mo9221clone();
        sprAttributeAnimatorSet.mAnimators = new ArrayList<>();
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            sprAttributeAnimatorSet.mAnimators.add(it.next().mo76clone());
        }
        return sprAttributeAnimatorSet;
    }

    public void updateAnimatorInterpolator(TimeInterpolator timeInterpolator) {
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            it.next().setInterpolator(timeInterpolator);
        }
    }
}
