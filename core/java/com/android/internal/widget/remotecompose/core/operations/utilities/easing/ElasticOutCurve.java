package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

/* loaded from: classes5.dex */
public class ElasticOutCurve extends Easing {
    private static final float C4 = 2.0943952f;
    private static final float F_PI = 3.1415927f;
    private static final float LOG_8 = (float) Math.log(8.0d);
    private static final float TWENTY_PI = 62.831856f;

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float x) {
        if (x <= 0.0f) {
            return 0.0f;
        }
        if (x >= 1.0f) {
            return 1.0f;
        }
        return (float) ((Math.pow(2.0d, (-10.0f) * x) * Math.sin(((10.0f * x) - 0.75f) * C4)) + 1.0d);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float x) {
        if (x < 0.0f || x > 1.0f) {
            return 0.0f;
        }
        return (float) (((Math.pow(2.0d, 1.0f - (10.0f * x)) * 5.0d) * ((LOG_8 * Math.cos((x * TWENTY_PI) / 3.0f)) + (Math.sin((TWENTY_PI * x) / 3.0f) * 6.2831854820251465d))) / 3.0d);
    }
}
