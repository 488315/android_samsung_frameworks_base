package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

/* loaded from: classes6.dex */
public class GeneralEasing extends Easing {
    float[] mEasingData = new float[0];
    Easing mEasingCurve = new CubicEasing(1);

    public void setCurveSpecification(float[] fArr) {
        this.mEasingData = fArr;
        createEngine();
    }

    public float[] getCurveSpecification() {
        return this.mEasingData;
    }

    void createEngine() {
        int floatToRawIntBits = Float.floatToRawIntBits(this.mEasingData[0]);
        if (floatToRawIntBits == 11) {
            float[] fArr = this.mEasingData;
            this.mEasingCurve = new CubicEasing(fArr[1], fArr[2], fArr[3], fArr[5]);
        } else {
            if (floatToRawIntBits != 13) {
                switch (floatToRawIntBits) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        this.mEasingCurve = new CubicEasing(floatToRawIntBits);
                        break;
                }
                return;
            }
            this.mEasingCurve = new BounceCurve(floatToRawIntBits);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float f) {
        return this.mEasingCurve.get(f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float f) {
        return this.mEasingCurve.getDiff(f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public int getType() {
        return this.mEasingCurve.getType();
    }
}
