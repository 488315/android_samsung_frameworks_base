package androidx.compose.animation.core;

/* loaded from: classes.dex */
public abstract class EasingKt {
    public static final CubicBezierEasing FastOutSlowInEasing = new CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f);
    public static final CubicBezierEasing LinearOutSlowInEasing = new CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f);
    public static final CubicBezierEasing FastOutLinearInEasing = new CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f);
    public static final EasingKt$$ExternalSyntheticLambda0 LinearEasing = new EasingKt$$ExternalSyntheticLambda0();
}
