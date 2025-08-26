package com.samsung.android.nexus.particle.emitter;

import android.graphics.Color;
import android.graphics.RectF;
import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.particle.emitter.FactorType;

/* loaded from: classes4.dex */
public class Status {
    public float acc;
    public float alpha;
    public int color;
    public int colorMode;
    public float drawingHeight;
    public float drawingWidth;
    public final Factor factor;
    public final FactorKeyFrameSetList factorKeyFrameSetList;
    public float height;
    public final RectF mBounds;
    public boolean mUpdateBounds;
    public int posMode;
    public float posX;
    public float posY;
    public float rotation;
    public float scaleX;
    public float scaleY;
    public float speed;
    public float vecTanX;
    public float vecTanY;
    public float width;

    public Status() {
        this.mUpdateBounds = false;
        this.factor = new Factor();
        this.vecTanX = 1.0f;
        this.vecTanY = 0.0f;
        this.width = 1.0f;
        this.height = 1.0f;
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.alpha = 0.0f;
        this.rotation = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.speed = 0.0f;
        this.acc = 0.0f;
        this.color = -1;
        this.posMode = 10;
        this.colorMode = 10;
        this.factorKeyFrameSetList = new FactorKeyFrameSetList();
        this.mBounds = new RectF();
    }

    public static int AHSVToColor(float f, float f2, float f3, float f4) {
        double d = f2 / 60.0f;
        int iFloor = (int) (Math.floor(d) % 6.0d);
        float fFloor = (float) (d - Math.floor(d));
        int i = (int) f;
        int i2 = (int) ((f4 * 255.0f) + 0.5f);
        int i3 = (int) (((1.0f - f3) * f4 * 255.0f) + 0.5f);
        int i4 = (int) (((1.0f - (fFloor * f3)) * f4 * 255.0f) + 0.5f);
        int i5 = (int) (((1.0f - ((1.0f - fFloor) * f3)) * f4 * 255.0f) + 0.5f);
        return iFloor != 0 ? iFloor != 1 ? iFloor != 2 ? iFloor != 3 ? iFloor != 4 ? (i << 24) | (i2 << 16) | (i3 << 8) | i4 : (i << 24) | (i5 << 16) | (i3 << 8) | i2 : (i << 24) | (i3 << 16) | (i4 << 8) | i2 : (i << 24) | (i3 << 16) | (i2 << 8) | i5 : (i << 24) | (i4 << 16) | (i2 << 8) | i3 : (i << 24) | (i2 << 16) | (i5 << 8) | i3;
    }

    public final void onStep(float f, float f2, float[] fArr) {
        float f3;
        float f4;
        float f5 = this.posX;
        float f6 = this.posY;
        Factor factor = this.factor;
        if (factor.mNeedValidate) {
            factor.validate();
        }
        float[] fArr2 = factor.values;
        if (f2 != 0.0f) {
            FactorType factorType = FactorType.WIDTH;
            FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
            int length = factorTypeArr.length;
            boolean[] zArr = factor.mStepSkipList;
            int i = 0;
            if (fArr == null) {
                while (i < length) {
                    FactorType factorType2 = factorTypeArr[i];
                    if (!zArr[i]) {
                        int i2 = factorType2.valueIdx;
                        float f7 = fArr2[i2];
                        int i3 = factorType2.speedIdx;
                        fArr2[i2] = (fArr2[i3] * f2) + f7;
                        fArr2[i3] = (fArr2[factorType2.accelerationIdx] * f2) + fArr2[i3];
                    }
                    i++;
                }
            } else {
                while (i < length) {
                    FactorType factorType3 = factorTypeArr[i];
                    int i4 = factorType3.speedIdx;
                    float f8 = fArr[i4];
                    int i5 = factorType3.accelerationIdx;
                    float f9 = fArr[i5];
                    if (!zArr[i] || 0.0f != f8 || 0.0f != f9) {
                        int i6 = factorType3.valueIdx;
                        fArr2[i6] = ((fArr2[i4] + f8) * f2) + fArr2[i6];
                        fArr2[i4] = ((fArr2[i5] + f9) * f2) + fArr2[i4];
                    }
                    i++;
                }
            }
            float f10 = fArr2[FactorType.COLOR_HUE.valueIdx];
            while (f10 < 360.0f) {
                f10 += 360.0f;
            }
            while (f10 > 360.0f) {
                f10 -= 360.0f;
            }
            fArr2[FactorType.COLOR_HUE.valueIdx] = f10;
        }
        FactorKeyFrameSetList factorKeyFrameSetList = this.factorKeyFrameSetList;
        FloatKeyFrameSet[] floatKeyFrameSetArr = factorKeyFrameSetList.list;
        boolean zIsEmpty = factorKeyFrameSetList.isEmpty();
        FactorType factorType4 = FactorType.WIDTH;
        int i7 = factorType4.valueIdx;
        FloatKeyFrameSet floatKeyFrameSet = floatKeyFrameSetArr[factorType4.idx];
        if (floatKeyFrameSet != null) {
            float f11 = floatKeyFrameSet.get(f);
            fArr2[i7] = f11;
            this.width = f11;
        } else {
            this.width = fArr2[i7];
        }
        FactorType factorType5 = FactorType.HEIGHT;
        int i8 = factorType5.valueIdx;
        FloatKeyFrameSet floatKeyFrameSet2 = floatKeyFrameSetArr[factorType5.idx];
        if (floatKeyFrameSet2 != null) {
            float f12 = floatKeyFrameSet2.get(f);
            fArr2[i8] = f12;
            this.height = f12;
        } else {
            this.height = fArr2[i8];
        }
        if (this.posMode == 0) {
            FactorType factorType6 = FactorType.POS;
            int i9 = factorType6.valueIdx;
            FloatKeyFrameSet floatKeyFrameSet3 = floatKeyFrameSetArr[factorType6.idx];
            if (floatKeyFrameSet3 != null) {
                f4 = floatKeyFrameSet3.get(f);
                fArr2[i9] = f4;
            } else {
                f4 = fArr2[i9];
            }
            this.posX = (this.vecTanX * f4) + fArr2[FactorType.POS_X.valueIdx];
            this.posY = (this.vecTanY * f4) + fArr2[FactorType.POS_Y.valueIdx];
        } else {
            FactorType factorType7 = FactorType.POS_X;
            int i10 = factorType7.valueIdx;
            FloatKeyFrameSet floatKeyFrameSet4 = floatKeyFrameSetArr[factorType7.idx];
            if (floatKeyFrameSet4 != null) {
                float f13 = floatKeyFrameSet4.get(f);
                fArr2[i10] = f13;
                this.posX = f13;
            } else {
                this.posX = fArr2[i10];
            }
            FactorType factorType8 = FactorType.POS_Y;
            int i11 = factorType8.valueIdx;
            FloatKeyFrameSet floatKeyFrameSet5 = floatKeyFrameSetArr[factorType8.idx];
            if (floatKeyFrameSet5 != null) {
                float f14 = floatKeyFrameSet5.get(f);
                fArr2[i11] = f14;
                this.posY = f14;
            } else {
                this.posY = fArr2[i11];
            }
        }
        FactorType factorType9 = FactorType.ROTATION;
        int i12 = factorType9.valueIdx;
        FloatKeyFrameSet floatKeyFrameSet6 = floatKeyFrameSetArr[factorType9.idx];
        if (floatKeyFrameSet6 != null) {
            float f15 = floatKeyFrameSet6.get(f);
            fArr2[i12] = f15;
            this.rotation = f15;
        } else {
            this.rotation = fArr2[i12];
        }
        FactorType factorType10 = FactorType.ALPHA;
        int i13 = factorType10.valueIdx;
        FloatKeyFrameSet floatKeyFrameSet7 = floatKeyFrameSetArr[factorType10.idx];
        if (floatKeyFrameSet7 != null) {
            float f16 = floatKeyFrameSet7.get(f);
            fArr2[i13] = f16;
            this.alpha = f16;
        } else {
            this.alpha = fArr2[i13];
        }
        FactorType factorType11 = FactorType.SCALE;
        int i14 = factorType11.valueIdx;
        FloatKeyFrameSet floatKeyFrameSet8 = floatKeyFrameSetArr[factorType11.idx];
        if (floatKeyFrameSet8 != null) {
            f3 = floatKeyFrameSet8.get(f);
            fArr2[i14] = f3;
        } else {
            f3 = fArr2[i14];
        }
        this.scaleX = f3;
        this.scaleY = f3;
        if (fArr != null) {
            this.posX += fArr[FactorType.POS_X.valueIdx];
            this.posY += fArr[FactorType.POS_Y.valueIdx];
            this.rotation += fArr[factorType9.valueIdx];
            this.alpha *= fArr[factorType10.valueIdx];
            float f17 = fArr[factorType11.valueIdx];
            this.scaleX = f3 * f17;
            this.scaleY = f3 * f17;
        }
        int i15 = this.colorMode;
        if (i15 != 1) {
            if (i15 != 2) {
                if (i15 == 10) {
                    this.color = Color.argb(fArr2[FactorType.COLOR_ALPHA.valueIdx] / 255.0f, fArr2[FactorType.COLOR_RED.valueIdx] / 255.0f, fArr2[FactorType.COLOR_GREEN.valueIdx] / 255.0f, fArr2[FactorType.COLOR_BLUE.valueIdx] / 255.0f);
                } else if (i15 == 20) {
                    this.color = AHSVToColor(fArr2[FactorType.COLOR_ALPHA.valueIdx], fArr2[FactorType.COLOR_HUE.valueIdx], fArr2[FactorType.COLOR_SATURATION.valueIdx], fArr2[FactorType.COLOR_VALUE.valueIdx]);
                }
            } else {
                if (zIsEmpty) {
                    throw new IllegalArgumentException("null key frame set");
                }
                this.color = AHSVToColor(floatKeyFrameSetArr[FactorType.COLOR_ALPHA.idx].get(f), floatKeyFrameSetArr[FactorType.COLOR_HUE.idx].get(f), floatKeyFrameSetArr[FactorType.COLOR_SATURATION.idx].get(f), floatKeyFrameSetArr[FactorType.COLOR_VALUE.idx].get(f));
            }
        } else {
            if (zIsEmpty) {
                throw new IllegalArgumentException("null key frame set");
            }
            this.color = Color.argb(floatKeyFrameSetArr[FactorType.COLOR_ALPHA.idx].get(f) / 255.0f, floatKeyFrameSetArr[FactorType.COLOR_RED.idx].get(f) / 255.0f, floatKeyFrameSetArr[FactorType.COLOR_GREEN.idx].get(f) / 255.0f, floatKeyFrameSetArr[FactorType.COLOR_BLUE.idx].get(f) / 255.0f);
        }
        this.drawingWidth = this.width * this.scaleX;
        this.drawingHeight = this.height * this.scaleY;
        this.mUpdateBounds = true;
        updateBounds();
        if (f > 0.0f) {
            float f18 = this.speed;
            float fHypot = ((float) Math.hypot(this.posX - f5, this.posY - f6)) / f2;
            this.speed = fHypot;
            this.acc = fHypot - f18;
        }
    }

    public final void reset() {
        this.mUpdateBounds = false;
        this.vecTanX = 1.0f;
        this.vecTanY = 0.0f;
        this.width = 0.0f;
        this.height = 0.0f;
        this.drawingWidth = 0.0f;
        this.drawingHeight = 0.0f;
        this.mBounds.setEmpty();
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.alpha = 0.0f;
        this.rotation = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.speed = 0.0f;
        this.acc = 0.0f;
        this.color = -1;
        this.posMode = 10;
        this.colorMode = 10;
        this.factor.reset();
        this.factorKeyFrameSetList.clear();
    }

    public final String toString() {
        return "Factor{drawingWidth=" + this.drawingWidth + ", drawingHeight=" + this.drawingHeight + ", mUpdateBounds=" + this.mUpdateBounds + ", mBounds=" + this.mBounds + ", width=" + this.width + ", height=" + this.height + ", posX=" + this.posX + ", posY=" + this.posY + ", alpha=" + this.alpha + ", rotation=" + this.rotation + ", scaleX=" + this.scaleX + ", scaleY=" + this.scaleY + ", speed=" + this.speed + ", acc=" + this.acc + ", color=" + this.color + ", scaleMode=0, colorMode=" + this.colorMode + ", factorValueList=" + this.factor + ", factorKeyFrameSetList=" + this.factorKeyFrameSetList + '}';
    }

    public final void updateBounds() {
        float f = this.drawingWidth / 2.0f;
        float f2 = this.drawingHeight / 2.0f;
        RectF rectF = this.mBounds;
        float f3 = this.posX;
        float f4 = this.posY;
        rectF.set(f3 - f, f4 - f2, f3 + f, f4 + f2);
        this.mUpdateBounds = false;
    }

    public Status(Status status) {
        this.mUpdateBounds = false;
        Factor factor = new Factor();
        this.factor = factor;
        this.vecTanX = 1.0f;
        this.vecTanY = 0.0f;
        this.width = 1.0f;
        this.height = 1.0f;
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.alpha = 0.0f;
        this.rotation = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.speed = 0.0f;
        this.acc = 0.0f;
        this.color = -1;
        this.posMode = 10;
        this.colorMode = 10;
        FactorKeyFrameSetList factorKeyFrameSetList = new FactorKeyFrameSetList();
        this.factorKeyFrameSetList = factorKeyFrameSetList;
        RectF rectF = new RectF();
        this.mBounds = rectF;
        reset();
        if (status == null) {
            return;
        }
        this.mUpdateBounds = false;
        this.vecTanX = status.vecTanX;
        this.vecTanY = status.vecTanY;
        this.width = status.width;
        this.height = status.height;
        this.drawingWidth = status.drawingWidth;
        this.drawingHeight = status.drawingHeight;
        if (status.mUpdateBounds) {
            status.updateBounds();
        }
        rectF.set(status.mBounds);
        this.posX = status.posX;
        this.posY = status.posY;
        this.alpha = status.alpha;
        this.rotation = status.rotation;
        this.scaleX = status.scaleX;
        this.scaleY = status.scaleY;
        this.posMode = status.posMode;
        this.colorMode = status.colorMode;
        float[] fArr = status.factor.values;
        float[] fArr2 = factor.values;
        System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
        FactorKeyFrameSetList factorKeyFrameSetList2 = status.factorKeyFrameSetList;
        if (factorKeyFrameSetList2.isEmpty()) {
            factorKeyFrameSetList.clear();
            return;
        }
        FloatKeyFrameSet[] floatKeyFrameSetArr = factorKeyFrameSetList2.list;
        int length = floatKeyFrameSetArr.length;
        factorKeyFrameSetList.floatKeyFrameSetSize = factorKeyFrameSetList2.floatKeyFrameSetSize;
        System.arraycopy(floatKeyFrameSetArr, 0, factorKeyFrameSetList.list, 0, length);
    }
}
