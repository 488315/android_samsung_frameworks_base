package com.android.server.display.color;

/* loaded from: classes2.dex */
public abstract class ColorTemperatureTintController extends TintController {
    public abstract float[] computeMatrixForCct(int i);

    public abstract int getAppliedCct();

    public abstract int getDisabledCct();

    public abstract CctEvaluator getEvaluator();

    public abstract int getTargetCct();

    public abstract void setAppliedCct(int i);
}
