package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

public class GeneralEasing extends Easing {
    float[] mEasingData = new float[0];
    Easing mEasingCurve = new CubicEasing(1);

    public void setCurveSpecification(float[] data) {
        this.mEasingData = data;
        createEngine();
    }

    public float[] getCurveSpecification() {
        return this.mEasingData;
    }

    void createEngine() {
        int type = Float.floatToRawIntBits(this.mEasingData[0]);
        switch (type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.mEasingCurve = new CubicEasing(type);
                break;
            case 11:
                this.mEasingCurve =
                        new CubicEasing(
                                this.mEasingData[1],
                                this.mEasingData[2],
                                this.mEasingData[3],
                                this.mEasingData[5]);
                break;
            case 13:
                this.mEasingCurve = new BounceCurve(type);
                break;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float x) {
        return this.mEasingCurve.get(x);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float x) {
        return this.mEasingCurve.getDiff(x);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public int getType() {
        return this.mEasingCurve.getType();
    }
}
