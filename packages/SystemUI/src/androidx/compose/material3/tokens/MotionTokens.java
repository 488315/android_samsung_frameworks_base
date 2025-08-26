package androidx.compose.material3.tokens;

import androidx.compose.animation.core.CubicBezierEasing;

/* loaded from: classes.dex */
public final class MotionTokens {
    public static final CubicBezierEasing EasingEmphasizedAccelerateCubicBezier;
    public static final CubicBezierEasing EasingEmphasizedDecelerateCubicBezier;
    public static final CubicBezierEasing EasingStandardCubicBezier;
    public static final MotionTokens INSTANCE = new MotionTokens();

    static {
        new CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f);
        EasingEmphasizedAccelerateCubicBezier = new CubicBezierEasing(0.3f, 0.0f, 0.8f, 0.15f);
        EasingEmphasizedDecelerateCubicBezier = new CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f);
        new CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f);
        new CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f);
        new CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f);
        new CubicBezierEasing(0.0f, 0.0f, 1.0f, 1.0f);
        EasingStandardCubicBezier = new CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f);
        new CubicBezierEasing(0.3f, 0.0f, 1.0f, 1.0f);
        new CubicBezierEasing(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private MotionTokens() {
    }
}
