package androidx.compose.animation.graphics.res;

import android.R;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.BounceInterpolator;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.Easing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.graphics.vector.Animator;
import androidx.compose.animation.graphics.vector.compat.XmlAnimatorParser_androidKt;
import androidx.compose.animation.graphics.vector.compat.XmlPullParserUtils_androidKt;
import java.util.HashMap;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimatorResources_androidKt {
    public static final AnimatorResources_androidKt$$ExternalSyntheticLambda3 AccelerateDecelerateEasing;
    public static final AnimatorResources_androidKt$$ExternalSyntheticLambda3 AccelerateEasing;
    public static final AnimatorResources_androidKt$$ExternalSyntheticLambda0 BounceEasing = new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new BounceInterpolator());
    public static final AnimatorResources_androidKt$$ExternalSyntheticLambda3 DecelerateEasing;
    public static final HashMap builtinInterpolators;

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3] */
    static {
        final int i = 0;
        AccelerateDecelerateEasing = new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                switch (i) {
                    case 0:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return (float) ((Math.cos((f + 1) * 3.141592653589793d) / 2.0f) + 0.5f);
                    case 1:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return f * f;
                    default:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        float f2 = 1.0f - f;
                        return 1.0f - (f2 * f2);
                }
            }
        };
        final int i2 = 1;
        AccelerateEasing = new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                switch (i2) {
                    case 0:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return (float) ((Math.cos((f + 1) * 3.141592653589793d) / 2.0f) + 0.5f);
                    case 1:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return f * f;
                    default:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        float f2 = 1.0f - f;
                        return 1.0f - (f2 * f2);
                }
            }
        };
        final int i3 = 2;
        DecelerateEasing = new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda3
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                switch (i3) {
                    case 0:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return (float) ((Math.cos((f + 1) * 3.141592653589793d) / 2.0f) + 0.5f);
                    case 1:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        return f * f;
                    default:
                        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                        float f2 = 1.0f - f;
                        return 1.0f - (f2 * f2);
                }
            }
        };
        Integer valueOf = Integer.valueOf(R.anim.linear_interpolator);
        EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
        Pair pair = new Pair(valueOf, easingKt$$ExternalSyntheticLambda0);
        Integer valueOf2 = Integer.valueOf(R.interpolator.fast_out_linear_in);
        CubicBezierEasing cubicBezierEasing = EasingKt.FastOutLinearInEasing;
        Pair pair2 = new Pair(valueOf2, cubicBezierEasing);
        Integer valueOf3 = Integer.valueOf(R.interpolator.fast_out_slow_in);
        CubicBezierEasing cubicBezierEasing2 = EasingKt.FastOutSlowInEasing;
        Pair pair3 = new Pair(valueOf3, cubicBezierEasing2);
        Pair pair4 = new Pair(Integer.valueOf(R.interpolator.linear), easingKt$$ExternalSyntheticLambda0);
        Integer valueOf4 = Integer.valueOf(R.interpolator.linear_out_slow_in);
        CubicBezierEasing cubicBezierEasing3 = EasingKt.LinearOutSlowInEasing;
        builtinInterpolators = MapsKt__MapsKt.hashMapOf(pair, pair2, pair3, pair4, new Pair(valueOf4, cubicBezierEasing3), new Pair(Integer.valueOf(R.interpolator.fast_out_linear_in), cubicBezierEasing), new Pair(Integer.valueOf(R.interpolator.fast_out_slow_in), cubicBezierEasing2), new Pair(Integer.valueOf(R.interpolator.linear_out_slow_in), cubicBezierEasing3));
    }

    public static final Animator loadAnimatorResource(int i, Resources.Theme theme, Resources resources) {
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
        XmlPullParserUtils_androidKt.seekToStartTag(xml);
        String name = xml.getName();
        if (Intrinsics.areEqual(name, "set")) {
            return XmlAnimatorParser_androidKt.parseAnimatorSet(resources, theme, asAttributeSet, xml);
        }
        if (Intrinsics.areEqual(name, "objectAnimator")) {
            return XmlAnimatorParser_androidKt.parseObjectAnimator(resources, theme, asAttributeSet, xml);
        }
        throw new XmlPullParserException("Unknown tag: " + xml.getName());
    }
}
