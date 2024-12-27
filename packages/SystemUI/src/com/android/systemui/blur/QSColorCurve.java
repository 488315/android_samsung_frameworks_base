package com.android.systemui.blur;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSColorCurve {
    public final Context context;
    public float curve;
    public float fraction;
    public float minX;
    public float minY;
    public float saturation;
    public float radius = 0.0f;
    public float maxX = 255.0f;
    public float maxY = 255.0f;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSColorCurve(Context context) {
        this.context = context;
    }

    public final void setFraction(float f) {
        this.fraction = f;
        this.radius = (250.0f * f) + 0.0f;
        this.saturation = 0.25f * f;
        this.curve = 10.0f * f;
        this.minX = 15.0f * f;
        float f2 = (this.context.getResources().getConfiguration().uiMode & 48) == 32 ? 34.4f : 40.2f;
        float f3 = this.fraction;
        this.minY = f2 * f3;
        this.maxX = 255.0f - (0.0f * f3);
        this.maxY = 255.0f - ((255.0f - ((this.context.getResources().getConfiguration().uiMode & 48) == 32 ? 174.7f : 205.9f)) * this.fraction);
    }
}
