package com.android.systemui.surfaceeffects.ripple;

import android.graphics.RuntimeShader;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RippleShader extends RuntimeShader {
    public final FadeParams baseRingFadeParams;
    public final FadeParams centerFillFadeParams;
    public float rawProgress;
    public final RippleSize rippleSize;
    public final FadeParams sparkleRingFadeParams;
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(RippleShader.class).getSimpleName();
    public static final Interpolator STANDARD = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[RippleShape.values().length];
                try {
                    iArr[RippleShape.CIRCLE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[RippleShape.ROUNDED_BOX.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[RippleShape.ELLIPSE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final float access$getFade(Companion companion, FadeParams fadeParams, float f) {
            companion.getClass();
            return Math.min(subProgress(fadeParams.fadeInStart, fadeParams.fadeInEnd, f), 1.0f - subProgress(fadeParams.fadeOutStart, fadeParams.fadeOutEnd, f));
        }

        public static float subProgress(float f, float f2, float f3) {
            if (f == f2) {
                return f3 > f ? 1.0f : 0.0f;
            }
            return (Math.min(Math.max(f3, Math.min(f, f2)), Math.max(f, f2)) - f) / (f2 - f);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum RippleShape {
        CIRCLE,
        ROUNDED_BOX,
        ELLIPSE
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SizeAtProgress {
        public float height;

        /* renamed from: t */
        public float f361t;
        public float width;

        public SizeAtProgress(float f, float f2, float f3) {
            this.f361t = f;
            this.width = f2;
            this.height = f3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SizeAtProgress)) {
                return false;
            }
            SizeAtProgress sizeAtProgress = (SizeAtProgress) obj;
            return Float.compare(this.f361t, sizeAtProgress.f361t) == 0 && Float.compare(this.width, sizeAtProgress.width) == 0 && Float.compare(this.height, sizeAtProgress.height) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.height) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.width, Float.hashCode(this.f361t) * 31, 31);
        }

        public final String toString() {
            float f = this.f361t;
            float f2 = this.width;
            float f3 = this.height;
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("SizeAtProgress(t=", f, ", width=", f2, ", height=");
            m88m.append(f3);
            m88m.append(")");
            return m88m.toString();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RippleShader() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final void setPixelDensity(float f) {
        setFloatUniform("in_pixelDensity", f);
    }

    public final void setRawProgress(float f) {
        int i;
        this.rawProgress = f;
        float interpolation = ((PathInterpolator) STANDARD).getInterpolation(f);
        RippleSize rippleSize = this.rippleSize;
        ArrayList arrayList = (ArrayList) rippleSize.sizes;
        if (arrayList.isEmpty()) {
            if (interpolation > 0.0f) {
                Log.e(TAG, "Did you forget to set the ripple size? Use [setMaxSize] or [setSizeAtProgresses] before playing the animation.");
            }
            rippleSize.setSizeAtProgresses(rippleSize.initialSize);
            i = rippleSize.currentSizeIndex;
        } else {
            Object obj = arrayList.get(rippleSize.currentSizeIndex);
            while (interpolation > ((SizeAtProgress) obj).f361t) {
                int min = Math.min(rippleSize.currentSizeIndex + 1, arrayList.size() - 1);
                rippleSize.currentSizeIndex = min;
                obj = arrayList.get(min);
            }
            i = rippleSize.currentSizeIndex;
        }
        int max = Math.max(i - 1, 0);
        SizeAtProgress sizeAtProgress = (SizeAtProgress) arrayList.get(i);
        SizeAtProgress sizeAtProgress2 = (SizeAtProgress) arrayList.get(max);
        Companion companion = Companion;
        float f2 = sizeAtProgress2.f361t;
        float f3 = sizeAtProgress.f361t;
        companion.getClass();
        float subProgress = Companion.subProgress(f2, f3, interpolation);
        rippleSize.currentWidth = (sizeAtProgress.width * subProgress) + sizeAtProgress2.width;
        rippleSize.currentHeight = (sizeAtProgress.height * subProgress) + sizeAtProgress2.height;
        RippleSize rippleSize2 = this.rippleSize;
        setFloatUniform("in_size", rippleSize2.currentWidth, rippleSize2.currentHeight);
        setFloatUniform("in_thickness", this.rippleSize.currentHeight * 0.5f);
        RippleSize rippleSize3 = this.rippleSize;
        setFloatUniform("in_cornerRadius", Math.min(rippleSize3.currentWidth, rippleSize3.currentHeight));
        setFloatUniform("in_blur", (interpolation * (-0.75f)) + 1.25f);
        setFloatUniform("in_fadeSparkle", Companion.access$getFade(companion, this.sparkleRingFadeParams, f));
        setFloatUniform("in_fadeRing", Companion.access$getFade(companion, this.baseRingFadeParams, f));
        setFloatUniform("in_fadeFill", Companion.access$getFade(companion, this.centerFillFadeParams, f));
    }

    public /* synthetic */ RippleShader(RippleShape rippleShape, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? RippleShape.CIRCLE : rippleShape);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RippleShader(RippleShape rippleShape) {
        super(r5);
        String str;
        Companion.getClass();
        int i = Companion.WhenMappings.$EnumSwitchMapping$0[rippleShape.ordinal()];
        if (i == 1) {
            str = "\n            uniform vec2 in_center;\n            uniform vec2 in_size;\n            uniform float in_cornerRadius;\n            uniform float in_thickness;\n            uniform float in_time;\n            uniform float in_distort_radial;\n            uniform float in_distort_xy;\n            uniform float in_fadeSparkle;\n            uniform float in_fadeFill;\n            uniform float in_fadeRing;\n            uniform float in_blur;\n            uniform float in_pixelDensity;\n            layout(color) uniform vec4 in_color;\n            uniform float in_sparkle_strength;\n        \n        float triangleNoise(vec2 n) {\n            n  = fract(n * vec2(5.3987, 5.4421));\n            n += dot(n.yx, n.xy + vec2(21.5351, 14.3137));\n            float xy = n.x * n.y;\n            // compute in [0..2[ and remap to [-1.0..1.0[\n            return fract(xy * 95.4307) + fract(xy * 75.04961) - 1.0;\n        }\n\n        const float PI = 3.1415926535897932384626;\n\n        float sparkles(vec2 uv, float t) {\n            float n = triangleNoise(uv);\n            float s = 0.0;\n            for (float i = 0; i < 4; i += 1) {\n                float l = i * 0.01;\n                float h = l + 0.1;\n                float o = smoothstep(n - l, h, n);\n                o *= abs(sin(PI * o * (t + 0.55 * i)));\n                s += o;\n            }\n            return s;\n        }\n\n        vec2 distort(vec2 p, float time, float distort_amount_radial,\n            float distort_amount_xy) {\n                float angle = atan(p.y, p.x);\n                  return p + vec2(sin(angle * 8 + time * 0.003 + 1.641),\n                            cos(angle * 5 + 2.14 + time * 0.00412)) * distort_amount_radial\n                     + vec2(sin(p.x * 0.01 + time * 0.00215 + 0.8123),\n                            cos(p.y * 0.01 + time * 0.005931)) * distort_amount_xy;\n        }\n\n        // Perceived luminosity (L′), not absolute luminosity.\n        half getLuminosity(vec3 c) {\n            return 0.3 * c.r + 0.59 * c.g + 0.11 * c.b;\n        }\n\n        // Creates a luminosity mask and clamp to the legal range.\n        vec3 maskLuminosity(vec3 dest, float lum) {\n            dest.rgb *= vec3(lum);\n            // Clip back into the legal range\n            dest = clamp(dest, vec3(0.), vec3(1.0));\n            return dest;\n        }\n\n        // Return range [-1, 1].\n        vec3 hash(vec3 p) {\n            p = fract(p * vec3(.3456, .1234, .9876));\n            p += dot(p, p.yxz + 43.21);\n            p = (p.xxy + p.yxx) * p.zyx;\n            return (fract(sin(p) * 4567.1234567) - .5) * 2.;\n        }\n\n        // Skew factors (non-uniform).\n        const half SKEW = 0.3333333;  // 1/3\n        const half UNSKEW = 0.1666667;  // 1/6\n\n        // Return range roughly [-1,1].\n        // It's because the hash function (that returns a random gradient vector) returns\n        // different magnitude of vectors. Noise doesn't have to be in the precise range thus\n        // skipped normalize.\n        half simplex3d(vec3 p) {\n            // Skew the input coordinate, so that we get squashed cubical grid\n            vec3 s = floor(p + (p.x + p.y + p.z) * SKEW);\n\n            // Unskew back\n            vec3 u = s - (s.x + s.y + s.z) * UNSKEW;\n\n            // Unskewed coordinate that is relative to p, to compute the noise contribution\n            // based on the distance.\n            vec3 c0 = p - u;\n\n            // We have six simplices (in this case tetrahedron, since we are in 3D) that we\n            // could possibly in.\n            // Here, we are finding the correct tetrahedron (simplex shape), and traverse its\n            // four vertices (c0..3) when computing noise contribution.\n            // The way we find them is by comparing c0's x,y,z values.\n            // For example in 2D, we can find the triangle (simplex shape in 2D) that we are in\n            // by comparing x and y values. i.e. x>y lower, x<y, upper triangle.\n            // Same applies in 3D.\n            //\n            // Below indicates the offsets (or offset directions) when c0=(x0,y0,z0)\n            // x0>y0>z0: (1,0,0), (1,1,0), (1,1,1)\n            // x0>z0>y0: (1,0,0), (1,0,1), (1,1,1)\n            // z0>x0>y0: (0,0,1), (1,0,1), (1,1,1)\n            // z0>y0>x0: (0,0,1), (0,1,1), (1,1,1)\n            // y0>z0>x0: (0,1,0), (0,1,1), (1,1,1)\n            // y0>x0>z0: (0,1,0), (1,1,0), (1,1,1)\n            //\n            // The rule is:\n            // * For offset1, set 1 at the max component, otherwise 0.\n            // * For offset2, set 0 at the min component, otherwise 1.\n            // * For offset3, set 1 for all.\n            //\n            // Encode x0-y0, y0-z0, z0-x0 in a vec3\n            vec3 en = c0 - c0.yzx;\n            // Each represents whether x0>y0, y0>z0, z0>x0\n            en = step(vec3(0.), en);\n            // en.zxy encodes z0>x0, x0>y0, y0>x0\n            vec3 offset1 = en * (1. - en.zxy); // find max\n            vec3 offset2 = 1. - en.zxy * (1. - en); // 1-(find min)\n            vec3 offset3 = vec3(1.);\n\n            vec3 c1 = c0 - offset1 + UNSKEW;\n            vec3 c2 = c0 - offset2 + UNSKEW * 2.;\n            vec3 c3 = c0 - offset3 + UNSKEW * 3.;\n\n            // Kernel summation: dot(max(0, r^2-d^2))^4, noise contribution)\n            //\n            // First compute d^2, squared distance to the point.\n            vec4 w; // w = max(0, r^2 - d^2))\n            w.x = dot(c0, c0);\n            w.y = dot(c1, c1);\n            w.z = dot(c2, c2);\n            w.w = dot(c3, c3);\n\n            // Noise contribution should decay to zero before they cross the simplex boundary.\n            // Usually r^2 is 0.5 or 0.6;\n            // 0.5 ensures continuity but 0.6 increases the visual quality for the application\n            // where discontinuity isn't noticeable.\n            w = max(0.6 - w, 0.);\n\n            // Noise contribution from each point.\n            vec4 nc;\n            nc.x = dot(hash(s), c0);\n            nc.y = dot(hash(s + offset1), c1);\n            nc.z = dot(hash(s + offset2), c2);\n            nc.w = dot(hash(s + offset3), c3);\n\n            nc *= w*w*w*w;\n\n            // Add all the noise contributions.\n            // Should multiply by the possible max contribution to adjust the range in [-1,1].\n            return dot(vec4(32.), nc);\n        }\n\n        // Random rotations.\n        // The way you create fractal noise is layering simplex noise with some rotation.\n        // To make random cloud looking noise, the rotations should not align. (Otherwise it\n        // creates patterned noise).\n        // Below rotations only rotate in one axis.\n        const mat3 rot1 = mat3(1.0, 0. ,0., 0., 0.15, -0.98, 0., 0.98, 0.15);\n        const mat3 rot2 = mat3(-0.95, 0. ,-0.3, 0., 1., 0., 0.3, 0., -0.95);\n        const mat3 rot3 = mat3(1.0, 0. ,0., 0., -0.44, -0.89, 0., 0.89, -0.44);\n\n        // Octave = 4\n        // Divide each coefficient by 3 to produce more grainy noise.\n        half simplex3d_fractal(vec3 p) {\n            return 0.675 * simplex3d(p * rot1) + 0.225 * simplex3d(2.0 * p * rot2)\n                    + 0.075 * simplex3d(4.0 * p * rot3) + 0.025 * simplex3d(8.0 * p);\n        }\n\n        // Screen blend\n        vec3 screen(vec3 dest, vec3 src) {\n            return dest + src - dest * src;\n        }\n    \n            float soften(float d, float blur) {\n                float blurHalf = blur * 0.5;\n                return smoothstep(-blurHalf, blurHalf, d);\n            }\n\n            float subtract(float outer, float inner) {\n                return max(outer, -inner);\n            }\n        \n            float sdCircle(vec2 p, float r) {\n                return (length(p)-r) / r;\n            }\n\n            float circleRing(vec2 p, float radius) {\n                float thicknessHalf = radius * 0.25;\n\n                float outerCircle = sdCircle(p, radius + thicknessHalf);\n                float innerCircle = sdCircle(p, radius);\n\n                return subtract(outerCircle, innerCircle);\n            }\n        \n            vec4 main(vec2 p) {\n                vec2 p_distorted = distort(p, in_time, in_distort_radial, in_distort_xy);\n                float radius = in_size.x * 0.5;\n                float sparkleRing = soften(circleRing(p_distorted-in_center, radius), in_blur);\n                float inside = soften(sdCircle(p_distorted-in_center, radius * 1.25), in_blur);\n                float sparkle = sparkles(p - mod(p, in_pixelDensity * 0.8), in_time * 0.00175)\n                    * (1.-sparkleRing) * in_fadeSparkle;\n\n                float rippleInsideAlpha = (1.-inside) * in_fadeFill;\n                float rippleRingAlpha = (1.-sparkleRing) * in_fadeRing;\n                float rippleAlpha = max(rippleInsideAlpha, rippleRingAlpha) * in_color.a;\n                vec4 ripple = vec4(in_color.rgb, 1.0) * rippleAlpha;\n                return mix(ripple, vec4(sparkle), sparkle * in_sparkle_strength);\n            }\n        ";
        } else if (i == 2) {
            str = "\n            uniform vec2 in_center;\n            uniform vec2 in_size;\n            uniform float in_cornerRadius;\n            uniform float in_thickness;\n            uniform float in_time;\n            uniform float in_distort_radial;\n            uniform float in_distort_xy;\n            uniform float in_fadeSparkle;\n            uniform float in_fadeFill;\n            uniform float in_fadeRing;\n            uniform float in_blur;\n            uniform float in_pixelDensity;\n            layout(color) uniform vec4 in_color;\n            uniform float in_sparkle_strength;\n        \n        float triangleNoise(vec2 n) {\n            n  = fract(n * vec2(5.3987, 5.4421));\n            n += dot(n.yx, n.xy + vec2(21.5351, 14.3137));\n            float xy = n.x * n.y;\n            // compute in [0..2[ and remap to [-1.0..1.0[\n            return fract(xy * 95.4307) + fract(xy * 75.04961) - 1.0;\n        }\n\n        const float PI = 3.1415926535897932384626;\n\n        float sparkles(vec2 uv, float t) {\n            float n = triangleNoise(uv);\n            float s = 0.0;\n            for (float i = 0; i < 4; i += 1) {\n                float l = i * 0.01;\n                float h = l + 0.1;\n                float o = smoothstep(n - l, h, n);\n                o *= abs(sin(PI * o * (t + 0.55 * i)));\n                s += o;\n            }\n            return s;\n        }\n\n        vec2 distort(vec2 p, float time, float distort_amount_radial,\n            float distort_amount_xy) {\n                float angle = atan(p.y, p.x);\n                  return p + vec2(sin(angle * 8 + time * 0.003 + 1.641),\n                            cos(angle * 5 + 2.14 + time * 0.00412)) * distort_amount_radial\n                     + vec2(sin(p.x * 0.01 + time * 0.00215 + 0.8123),\n                            cos(p.y * 0.01 + time * 0.005931)) * distort_amount_xy;\n        }\n\n        // Perceived luminosity (L′), not absolute luminosity.\n        half getLuminosity(vec3 c) {\n            return 0.3 * c.r + 0.59 * c.g + 0.11 * c.b;\n        }\n\n        // Creates a luminosity mask and clamp to the legal range.\n        vec3 maskLuminosity(vec3 dest, float lum) {\n            dest.rgb *= vec3(lum);\n            // Clip back into the legal range\n            dest = clamp(dest, vec3(0.), vec3(1.0));\n            return dest;\n        }\n\n        // Return range [-1, 1].\n        vec3 hash(vec3 p) {\n            p = fract(p * vec3(.3456, .1234, .9876));\n            p += dot(p, p.yxz + 43.21);\n            p = (p.xxy + p.yxx) * p.zyx;\n            return (fract(sin(p) * 4567.1234567) - .5) * 2.;\n        }\n\n        // Skew factors (non-uniform).\n        const half SKEW = 0.3333333;  // 1/3\n        const half UNSKEW = 0.1666667;  // 1/6\n\n        // Return range roughly [-1,1].\n        // It's because the hash function (that returns a random gradient vector) returns\n        // different magnitude of vectors. Noise doesn't have to be in the precise range thus\n        // skipped normalize.\n        half simplex3d(vec3 p) {\n            // Skew the input coordinate, so that we get squashed cubical grid\n            vec3 s = floor(p + (p.x + p.y + p.z) * SKEW);\n\n            // Unskew back\n            vec3 u = s - (s.x + s.y + s.z) * UNSKEW;\n\n            // Unskewed coordinate that is relative to p, to compute the noise contribution\n            // based on the distance.\n            vec3 c0 = p - u;\n\n            // We have six simplices (in this case tetrahedron, since we are in 3D) that we\n            // could possibly in.\n            // Here, we are finding the correct tetrahedron (simplex shape), and traverse its\n            // four vertices (c0..3) when computing noise contribution.\n            // The way we find them is by comparing c0's x,y,z values.\n            // For example in 2D, we can find the triangle (simplex shape in 2D) that we are in\n            // by comparing x and y values. i.e. x>y lower, x<y, upper triangle.\n            // Same applies in 3D.\n            //\n            // Below indicates the offsets (or offset directions) when c0=(x0,y0,z0)\n            // x0>y0>z0: (1,0,0), (1,1,0), (1,1,1)\n            // x0>z0>y0: (1,0,0), (1,0,1), (1,1,1)\n            // z0>x0>y0: (0,0,1), (1,0,1), (1,1,1)\n            // z0>y0>x0: (0,0,1), (0,1,1), (1,1,1)\n            // y0>z0>x0: (0,1,0), (0,1,1), (1,1,1)\n            // y0>x0>z0: (0,1,0), (1,1,0), (1,1,1)\n            //\n            // The rule is:\n            // * For offset1, set 1 at the max component, otherwise 0.\n            // * For offset2, set 0 at the min component, otherwise 1.\n            // * For offset3, set 1 for all.\n            //\n            // Encode x0-y0, y0-z0, z0-x0 in a vec3\n            vec3 en = c0 - c0.yzx;\n            // Each represents whether x0>y0, y0>z0, z0>x0\n            en = step(vec3(0.), en);\n            // en.zxy encodes z0>x0, x0>y0, y0>x0\n            vec3 offset1 = en * (1. - en.zxy); // find max\n            vec3 offset2 = 1. - en.zxy * (1. - en); // 1-(find min)\n            vec3 offset3 = vec3(1.);\n\n            vec3 c1 = c0 - offset1 + UNSKEW;\n            vec3 c2 = c0 - offset2 + UNSKEW * 2.;\n            vec3 c3 = c0 - offset3 + UNSKEW * 3.;\n\n            // Kernel summation: dot(max(0, r^2-d^2))^4, noise contribution)\n            //\n            // First compute d^2, squared distance to the point.\n            vec4 w; // w = max(0, r^2 - d^2))\n            w.x = dot(c0, c0);\n            w.y = dot(c1, c1);\n            w.z = dot(c2, c2);\n            w.w = dot(c3, c3);\n\n            // Noise contribution should decay to zero before they cross the simplex boundary.\n            // Usually r^2 is 0.5 or 0.6;\n            // 0.5 ensures continuity but 0.6 increases the visual quality for the application\n            // where discontinuity isn't noticeable.\n            w = max(0.6 - w, 0.);\n\n            // Noise contribution from each point.\n            vec4 nc;\n            nc.x = dot(hash(s), c0);\n            nc.y = dot(hash(s + offset1), c1);\n            nc.z = dot(hash(s + offset2), c2);\n            nc.w = dot(hash(s + offset3), c3);\n\n            nc *= w*w*w*w;\n\n            // Add all the noise contributions.\n            // Should multiply by the possible max contribution to adjust the range in [-1,1].\n            return dot(vec4(32.), nc);\n        }\n\n        // Random rotations.\n        // The way you create fractal noise is layering simplex noise with some rotation.\n        // To make random cloud looking noise, the rotations should not align. (Otherwise it\n        // creates patterned noise).\n        // Below rotations only rotate in one axis.\n        const mat3 rot1 = mat3(1.0, 0. ,0., 0., 0.15, -0.98, 0., 0.98, 0.15);\n        const mat3 rot2 = mat3(-0.95, 0. ,-0.3, 0., 1., 0., 0.3, 0., -0.95);\n        const mat3 rot3 = mat3(1.0, 0. ,0., 0., -0.44, -0.89, 0., 0.89, -0.44);\n\n        // Octave = 4\n        // Divide each coefficient by 3 to produce more grainy noise.\n        half simplex3d_fractal(vec3 p) {\n            return 0.675 * simplex3d(p * rot1) + 0.225 * simplex3d(2.0 * p * rot2)\n                    + 0.075 * simplex3d(4.0 * p * rot3) + 0.025 * simplex3d(8.0 * p);\n        }\n\n        // Screen blend\n        vec3 screen(vec3 dest, vec3 src) {\n            return dest + src - dest * src;\n        }\n    \n            float soften(float d, float blur) {\n                float blurHalf = blur * 0.5;\n                return smoothstep(-blurHalf, blurHalf, d);\n            }\n\n            float subtract(float outer, float inner) {\n                return max(outer, -inner);\n            }\n        \n            float sdRoundedBox(vec2 p, vec2 size, float cornerRadius) {\n                size *= 0.5;\n                cornerRadius *= 0.5;\n                vec2 d = abs(p)-size+cornerRadius;\n\n                float outside = length(max(d, 0.0));\n                float inside = min(max(d.x, d.y), 0.0);\n\n                return (outside+inside-cornerRadius)/size.y;\n            }\n\n            float roundedBoxRing(vec2 p, vec2 size, float cornerRadius,\n                float borderThickness) {\n                float outerRoundBox = sdRoundedBox(p, size + vec2(borderThickness),\n                    cornerRadius + borderThickness);\n                float innerRoundBox = sdRoundedBox(p, size, cornerRadius);\n                return subtract(outerRoundBox, innerRoundBox);\n            }\n        \n            vec4 main(vec2 p) {\n                float sparkleRing = soften(roundedBoxRing(p-in_center, in_size, in_cornerRadius,\n                    in_thickness), in_blur);\n                float inside = soften(sdRoundedBox(p-in_center, in_size * 1.25, in_cornerRadius),\n                    in_blur);\n                float sparkle = sparkles(p - mod(p, in_pixelDensity * 0.8), in_time * 0.00175)\n                    * (1.-sparkleRing) * in_fadeSparkle;\n\n                float rippleInsideAlpha = (1.-inside) * in_fadeFill;\n                float rippleRingAlpha = (1.-sparkleRing) * in_fadeRing;\n                float rippleAlpha = max(rippleInsideAlpha, rippleRingAlpha) * in_color.a;\n                vec4 ripple = vec4(in_color.rgb, 1.0) * rippleAlpha;\n                return mix(ripple, vec4(sparkle), sparkle * in_sparkle_strength);\n            }\n        ";
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            str = "\n            uniform vec2 in_center;\n            uniform vec2 in_size;\n            uniform float in_cornerRadius;\n            uniform float in_thickness;\n            uniform float in_time;\n            uniform float in_distort_radial;\n            uniform float in_distort_xy;\n            uniform float in_fadeSparkle;\n            uniform float in_fadeFill;\n            uniform float in_fadeRing;\n            uniform float in_blur;\n            uniform float in_pixelDensity;\n            layout(color) uniform vec4 in_color;\n            uniform float in_sparkle_strength;\n        \n        float triangleNoise(vec2 n) {\n            n  = fract(n * vec2(5.3987, 5.4421));\n            n += dot(n.yx, n.xy + vec2(21.5351, 14.3137));\n            float xy = n.x * n.y;\n            // compute in [0..2[ and remap to [-1.0..1.0[\n            return fract(xy * 95.4307) + fract(xy * 75.04961) - 1.0;\n        }\n\n        const float PI = 3.1415926535897932384626;\n\n        float sparkles(vec2 uv, float t) {\n            float n = triangleNoise(uv);\n            float s = 0.0;\n            for (float i = 0; i < 4; i += 1) {\n                float l = i * 0.01;\n                float h = l + 0.1;\n                float o = smoothstep(n - l, h, n);\n                o *= abs(sin(PI * o * (t + 0.55 * i)));\n                s += o;\n            }\n            return s;\n        }\n\n        vec2 distort(vec2 p, float time, float distort_amount_radial,\n            float distort_amount_xy) {\n                float angle = atan(p.y, p.x);\n                  return p + vec2(sin(angle * 8 + time * 0.003 + 1.641),\n                            cos(angle * 5 + 2.14 + time * 0.00412)) * distort_amount_radial\n                     + vec2(sin(p.x * 0.01 + time * 0.00215 + 0.8123),\n                            cos(p.y * 0.01 + time * 0.005931)) * distort_amount_xy;\n        }\n\n        // Perceived luminosity (L′), not absolute luminosity.\n        half getLuminosity(vec3 c) {\n            return 0.3 * c.r + 0.59 * c.g + 0.11 * c.b;\n        }\n\n        // Creates a luminosity mask and clamp to the legal range.\n        vec3 maskLuminosity(vec3 dest, float lum) {\n            dest.rgb *= vec3(lum);\n            // Clip back into the legal range\n            dest = clamp(dest, vec3(0.), vec3(1.0));\n            return dest;\n        }\n\n        // Return range [-1, 1].\n        vec3 hash(vec3 p) {\n            p = fract(p * vec3(.3456, .1234, .9876));\n            p += dot(p, p.yxz + 43.21);\n            p = (p.xxy + p.yxx) * p.zyx;\n            return (fract(sin(p) * 4567.1234567) - .5) * 2.;\n        }\n\n        // Skew factors (non-uniform).\n        const half SKEW = 0.3333333;  // 1/3\n        const half UNSKEW = 0.1666667;  // 1/6\n\n        // Return range roughly [-1,1].\n        // It's because the hash function (that returns a random gradient vector) returns\n        // different magnitude of vectors. Noise doesn't have to be in the precise range thus\n        // skipped normalize.\n        half simplex3d(vec3 p) {\n            // Skew the input coordinate, so that we get squashed cubical grid\n            vec3 s = floor(p + (p.x + p.y + p.z) * SKEW);\n\n            // Unskew back\n            vec3 u = s - (s.x + s.y + s.z) * UNSKEW;\n\n            // Unskewed coordinate that is relative to p, to compute the noise contribution\n            // based on the distance.\n            vec3 c0 = p - u;\n\n            // We have six simplices (in this case tetrahedron, since we are in 3D) that we\n            // could possibly in.\n            // Here, we are finding the correct tetrahedron (simplex shape), and traverse its\n            // four vertices (c0..3) when computing noise contribution.\n            // The way we find them is by comparing c0's x,y,z values.\n            // For example in 2D, we can find the triangle (simplex shape in 2D) that we are in\n            // by comparing x and y values. i.e. x>y lower, x<y, upper triangle.\n            // Same applies in 3D.\n            //\n            // Below indicates the offsets (or offset directions) when c0=(x0,y0,z0)\n            // x0>y0>z0: (1,0,0), (1,1,0), (1,1,1)\n            // x0>z0>y0: (1,0,0), (1,0,1), (1,1,1)\n            // z0>x0>y0: (0,0,1), (1,0,1), (1,1,1)\n            // z0>y0>x0: (0,0,1), (0,1,1), (1,1,1)\n            // y0>z0>x0: (0,1,0), (0,1,1), (1,1,1)\n            // y0>x0>z0: (0,1,0), (1,1,0), (1,1,1)\n            //\n            // The rule is:\n            // * For offset1, set 1 at the max component, otherwise 0.\n            // * For offset2, set 0 at the min component, otherwise 1.\n            // * For offset3, set 1 for all.\n            //\n            // Encode x0-y0, y0-z0, z0-x0 in a vec3\n            vec3 en = c0 - c0.yzx;\n            // Each represents whether x0>y0, y0>z0, z0>x0\n            en = step(vec3(0.), en);\n            // en.zxy encodes z0>x0, x0>y0, y0>x0\n            vec3 offset1 = en * (1. - en.zxy); // find max\n            vec3 offset2 = 1. - en.zxy * (1. - en); // 1-(find min)\n            vec3 offset3 = vec3(1.);\n\n            vec3 c1 = c0 - offset1 + UNSKEW;\n            vec3 c2 = c0 - offset2 + UNSKEW * 2.;\n            vec3 c3 = c0 - offset3 + UNSKEW * 3.;\n\n            // Kernel summation: dot(max(0, r^2-d^2))^4, noise contribution)\n            //\n            // First compute d^2, squared distance to the point.\n            vec4 w; // w = max(0, r^2 - d^2))\n            w.x = dot(c0, c0);\n            w.y = dot(c1, c1);\n            w.z = dot(c2, c2);\n            w.w = dot(c3, c3);\n\n            // Noise contribution should decay to zero before they cross the simplex boundary.\n            // Usually r^2 is 0.5 or 0.6;\n            // 0.5 ensures continuity but 0.6 increases the visual quality for the application\n            // where discontinuity isn't noticeable.\n            w = max(0.6 - w, 0.);\n\n            // Noise contribution from each point.\n            vec4 nc;\n            nc.x = dot(hash(s), c0);\n            nc.y = dot(hash(s + offset1), c1);\n            nc.z = dot(hash(s + offset2), c2);\n            nc.w = dot(hash(s + offset3), c3);\n\n            nc *= w*w*w*w;\n\n            // Add all the noise contributions.\n            // Should multiply by the possible max contribution to adjust the range in [-1,1].\n            return dot(vec4(32.), nc);\n        }\n\n        // Random rotations.\n        // The way you create fractal noise is layering simplex noise with some rotation.\n        // To make random cloud looking noise, the rotations should not align. (Otherwise it\n        // creates patterned noise).\n        // Below rotations only rotate in one axis.\n        const mat3 rot1 = mat3(1.0, 0. ,0., 0., 0.15, -0.98, 0., 0.98, 0.15);\n        const mat3 rot2 = mat3(-0.95, 0. ,-0.3, 0., 1., 0., 0.3, 0., -0.95);\n        const mat3 rot3 = mat3(1.0, 0. ,0., 0., -0.44, -0.89, 0., 0.89, -0.44);\n\n        // Octave = 4\n        // Divide each coefficient by 3 to produce more grainy noise.\n        half simplex3d_fractal(vec3 p) {\n            return 0.675 * simplex3d(p * rot1) + 0.225 * simplex3d(2.0 * p * rot2)\n                    + 0.075 * simplex3d(4.0 * p * rot3) + 0.025 * simplex3d(8.0 * p);\n        }\n\n        // Screen blend\n        vec3 screen(vec3 dest, vec3 src) {\n            return dest + src - dest * src;\n        }\n    \n            float soften(float d, float blur) {\n                float blurHalf = blur * 0.5;\n                return smoothstep(-blurHalf, blurHalf, d);\n            }\n\n            float subtract(float outer, float inner) {\n                return max(outer, -inner);\n            }\n        float sdEllipse(vec2 p, vec2 wh) {\n            wh *= 0.5;\n\n            // symmetry\n            (wh.x > wh.y) ? wh = wh.yx, p = abs(p.yx) : p = abs(p);\n\n            vec2 u = wh*p, v = wh*wh;\n\n            float U1 = u.y/2.0;\n            float U2 = v.y-v.x;\n            float U3 = u.x-U2;\n            float U4 = u.x+U2;\n            float U5 = 4.0*U1;\n            float U6 = 6.0*U1;\n            float U7 = 3.0*U3;\n\n            float t = 0.5;\n            for (int i = 0; i < 3; i ++) {\n                float F1 = t*(t*t*(U1*t+U3)+U4)-U1;\n                float F2 = t*t*(U5*t+U7)+U4;\n                float F3 = t*(U6*t+U7);\n\n                t += (F1*F2)/(F1*F3-F2*F2);\n            }\n\n            t = clamp(t, 0.0, 1.0);\n\n            float d = distance(p, wh*vec2(1.0-t*t,2.0*t)/(t*t+1.0));\n            d /= wh.y;\n\n            return (dot(p/wh,p/wh)>1.0) ? d : -d;\n        }\n\n        float ellipseRing(vec2 p, vec2 wh) {\n            vec2 thicknessHalf = wh * 0.25;\n\n            float outerEllipse = sdEllipse(p, wh + thicknessHalf);\n            float innerEllipse = sdEllipse(p, wh);\n\n            return subtract(outerEllipse, innerEllipse);\n        }\n        \n            vec4 main(vec2 p) {\n                vec2 p_distorted = distort(p, in_time, in_distort_radial, in_distort_xy);\n\n                float sparkleRing = soften(ellipseRing(p_distorted-in_center, in_size), in_blur);\n                float inside = soften(sdEllipse(p_distorted-in_center, in_size * 1.2), in_blur);\n                float sparkle = sparkles(p - mod(p, in_pixelDensity * 0.8), in_time * 0.00175)\n                    * (1.-sparkleRing) * in_fadeSparkle;\n\n                float rippleInsideAlpha = (1.-inside) * in_fadeFill;\n                float rippleRingAlpha = (1.-sparkleRing) * in_fadeRing;\n                float rippleAlpha = max(rippleInsideAlpha, rippleRingAlpha) * in_color.a;\n                vec4 ripple = vec4(in_color.rgb, 1.0) * rippleAlpha;\n                return mix(ripple, vec4(sparkle), sparkle * in_sparkle_strength);\n            }\n        ";
        }
        this.rippleSize = new RippleSize(this);
        this.sparkleRingFadeParams = new FadeParams(0.0f, 0.1f, 0.4f, 1.0f);
        this.baseRingFadeParams = new FadeParams(0.0f, 0.1f, 0.3f, 1.0f);
        this.centerFillFadeParams = new FadeParams(0.0f, 0.0f, 0.0f, 0.6f);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FadeParams {
        public float fadeInEnd;
        public float fadeInStart;
        public float fadeOutEnd;
        public float fadeOutStart;

        public FadeParams(float f, float f2, float f3, float f4) {
            this.fadeInStart = f;
            this.fadeInEnd = f2;
            this.fadeOutStart = f3;
            this.fadeOutEnd = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FadeParams)) {
                return false;
            }
            FadeParams fadeParams = (FadeParams) obj;
            return Float.compare(this.fadeInStart, fadeParams.fadeInStart) == 0 && Float.compare(this.fadeInEnd, fadeParams.fadeInEnd) == 0 && Float.compare(this.fadeOutStart, fadeParams.fadeOutStart) == 0 && Float.compare(this.fadeOutEnd, fadeParams.fadeOutEnd) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.fadeOutEnd) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.fadeOutStart, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.fadeInEnd, Float.hashCode(this.fadeInStart) * 31, 31), 31);
        }

        public final String toString() {
            float f = this.fadeInStart;
            float f2 = this.fadeInEnd;
            float f3 = this.fadeOutStart;
            float f4 = this.fadeOutEnd;
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("FadeParams(fadeInStart=", f, ", fadeInEnd=", f2, ", fadeOutStart=");
            m88m.append(f3);
            m88m.append(", fadeOutEnd=");
            m88m.append(f4);
            m88m.append(")");
            return m88m.toString();
        }

        public /* synthetic */ FadeParams(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, f2, f3, (i & 8) != 0 ? 1.0f : f4);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RippleSize {
        public float currentHeight;
        public int currentSizeIndex;
        public float currentWidth;
        public final List sizes = new ArrayList();
        public final SizeAtProgress initialSize = new SizeAtProgress(0.0f, 0.0f, 0.0f);

        public RippleSize(RippleShader rippleShader) {
        }

        public final void setMaxSize(float f, float f2) {
            setSizeAtProgresses(this.initialSize, new SizeAtProgress(1.0f, f, f2));
        }

        public final void setSizeAtProgresses(SizeAtProgress... sizeAtProgressArr) {
            List list = this.sizes;
            ((ArrayList) list).clear();
            this.currentSizeIndex = 0;
            list.addAll(Arrays.asList(sizeAtProgressArr));
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.android.systemui.surfaceeffects.ripple.RippleShader$RippleSize$setSizeAtProgresses$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Float.valueOf(((RippleShader.SizeAtProgress) obj).f361t), Float.valueOf(((RippleShader.SizeAtProgress) obj2).f361t));
                    }
                });
            }
        }

        public static /* synthetic */ void getCurrentSizeIndex$annotations() {
        }

        public static /* synthetic */ void getInitialSize$annotations() {
        }

        public static /* synthetic */ void getSizes$annotations() {
        }
    }
}
