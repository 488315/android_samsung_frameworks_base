package com.samsung.android.sesl.visualeffect.surfaceeffects.ripple;

import android.graphics.RuntimeShader;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RippleShader extends RuntimeShader {
    public static final Companion Companion = new Companion(null);
    public final FadeParams baseRingFadeParams;
    public final FadeParams centerFillFadeParams;
    public final RippleSize rippleSize;
    public final FadeParams sparkleRingFadeParams;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RippleSize {
        public final List sizes = new ArrayList();
        public final SizeAtProgress initialSize = new SizeAtProgress(0.0f, 0.0f, 0.0f);

        public RippleSize(RippleShader rippleShader) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SizeAtProgress {
        public final float height;
        public final float t;
        public final float width;

        public SizeAtProgress(float f, float f2, float f3) {
            this.t = f;
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
            return Float.compare(this.t, sizeAtProgress.t) == 0 && Float.compare(this.width, sizeAtProgress.width) == 0 && Float.compare(this.height, sizeAtProgress.height) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.height) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.width, Float.hashCode(this.t) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SizeAtProgress(t=");
            sb.append(this.t);
            sb.append(", width=");
            sb.append(this.width);
            sb.append(", height=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.height, ")", sb);
        }
    }

    static {
        Reflection.getOrCreateKotlinClass(RippleShader.class).getSimpleName();
        new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RippleShader() {
        super("\n            uniform vec2 in_center;\n            uniform vec2 in_size;\n            uniform vec2 in_rippleMove;\n            uniform float in_cornerRadius;\n            uniform float in_thickness;\n            uniform float in_time;\n            uniform float in_distort_radial;\n            uniform float in_distort_xy;\n            uniform float in_fadeSparkle;\n            uniform float in_fadeFill;\n            uniform float in_fadeRing;\n            uniform float in_blur;\n            uniform float in_pixelDensity;\n            layout(color) uniform vec4 in_color;\n            uniform float in_sparkle_strength;\n            \n          \n        \n        float triangleNoise(vec2 n) {\n            n  = fract(n * vec2(5.3987, 5.4421));\n            n += dot(n.yx, n.xy + vec2(21.5351, 14.3137));\n            float xy = n.x * n.y;\n            // compute in [0..2[ and remap to [-1.0..1.0[\n            return fract(xy * 95.4307) + fract(xy * 75.04961) - 1.0;\n        }\n\n        const float PI = 3.1415926535897932384626;\n\n        float sparkles(vec2 uv, float t) {\n            float n = triangleNoise(uv);\n            float s = 0.0;\n            for (float i = 0; i < 4; i += 1) {\n                float l = i * 0.01;\n                float h = l + 0.1;\n                float o = smoothstep(n - l, h, n);\n                o *= abs(sin(PI * o * (t + 0.55 * i)));\n                s += o;\n            }\n            return s;\n        }\n\n        vec2 distort(vec2 p, float time, float distort_amount_radial,\n            float distort_amount_xy) {\n                float angle = atan(p.y, p.x);\n                  return p + vec2(sin(angle * 8 + time * 0.003 + 1.641),\n                            cos(angle * 5 + 2.14 + time * 0.00412)) * distort_amount_radial\n                     + vec2(sin(p.x * 0.01 + time * 0.00215 + 0.8123),\n                            cos(p.y * 0.01 + time * 0.005931)) * distort_amount_xy;\n        }\n\n        // Perceived luminosity (L��), not absolute luminosity.\n        half getLuminosity(vec3 c) {\n            return 0.3 * c.r + 0.59 * c.g + 0.11 * c.b;\n        }\n\n        // Creates a luminosity mask and clamp to the legal range.\n        vec3 maskLuminosity(vec3 dest, float lum) {\n            dest.rgb *= vec3(lum);\n            // Clip back into the legal range\n            dest = clamp(dest, vec3(0.), vec3(1.0));\n            return dest;\n        }\n\n        // Return range [-1, 1].\n        vec3 hash(vec3 p) {\n            p = fract(p * vec3(.3456, .1234, .9876));\n            p += dot(p, p.yxz + 43.21);\n            p = (p.xxy + p.yxx) * p.zyx;\n            return (fract(sin(p) * 4567.1234567) - .5) * 2.;\n        }\n\n        // Skew factors (non-uniform).\n        const half SKEW = 0.3333333;  // 1/3\n        const half UNSKEW = 0.1666667;  // 1/6\n\n        // Return range roughly [-1,1].\n        // It's because the hash function (that returns a random gradient vector) returns\n        // different magnitude of vectors. Noise doesn't have to be in the precise range thus\n        // skipped normalize.\n        half simplex3d(vec3 p) {\n            // Skew the input coordinate, so that we get squashed cubical grid\n            vec3 s = floor(p + (p.x + p.y + p.z) * SKEW);\n\n            // Unskew back\n            vec3 u = s - (s.x + s.y + s.z) * UNSKEW;\n\n            // Unskewed coordinate that is relative to p, to compute the noise contribution\n            // based on the distance.\n            vec3 c0 = p - u;\n\n            // We have six simplices (in this case tetrahedron, since we are in 3D) that we\n            // could possibly in.\n            // Here, we are finding the correct tetrahedron (simplex shape), and traverse its\n            // four vertices (c0..3) when computing noise contribution.\n            // The way we find them is by comparing c0's x,y,z values.\n            // For example in 2D, we can find the triangle (simplex shape in 2D) that we are in\n            // by comparing x and y values. i.e. x>y lower, x<y, upper triangle.\n            // Same applies in 3D.\n            //\n            // Below indicates the offsets (or offset directions) when c0=(x0,y0,z0)\n            // x0>y0>z0: (1,0,0), (1,1,0), (1,1,1)\n            // x0>z0>y0: (1,0,0), (1,0,1), (1,1,1)\n            // z0>x0>y0: (0,0,1), (1,0,1), (1,1,1)\n            // z0>y0>x0: (0,0,1), (0,1,1), (1,1,1)\n            // y0>z0>x0: (0,1,0), (0,1,1), (1,1,1)\n            // y0>x0>z0: (0,1,0), (1,1,0), (1,1,1)\n            //\n            // The rule is:\n            // * For offset1, set 1 at the max component, otherwise 0.\n            // * For offset2, set 0 at the min component, otherwise 1.\n            // * For offset3, set 1 for all.\n            //\n            // Encode x0-y0, y0-z0, z0-x0 in a vec3\n            vec3 en = c0 - c0.yzx;\n            // Each represents whether x0>y0, y0>z0, z0>x0\n            en = step(vec3(0.), en);\n            // en.zxy encodes z0>x0, x0>y0, y0>x0\n            vec3 offset1 = en * (1. - en.zxy); // find max\n            vec3 offset2 = 1. - en.zxy * (1. - en); // 1-(find min)\n            vec3 offset3 = vec3(1.);\n\n            vec3 c1 = c0 - offset1 + UNSKEW;\n            vec3 c2 = c0 - offset2 + UNSKEW * 2.;\n            vec3 c3 = c0 - offset3 + UNSKEW * 3.;\n\n            // Kernel summation: dot(max(0, r^2-d^2))^4, noise contribution)\n            //\n            // First compute d^2, squared distance to the point.\n            vec4 w; // w = max(0, r^2 - d^2))\n            w.x = dot(c0, c0);\n            w.y = dot(c1, c1);\n            w.z = dot(c2, c2);\n            w.w = dot(c3, c3);\n\n            // Noise contribution should decay to zero before they cross the simplex boundary.\n            // Usually r^2 is 0.5 or 0.6;\n            // 0.5 ensures continuity but 0.6 increases the visual quality for the application\n            // where discontinuity isn't noticeable.\n            w = max(0.6 - w, 0.);\n\n            // Noise contribution from each point.\n            vec4 nc;\n            nc.x = dot(hash(s), c0);\n            nc.y = dot(hash(s + offset1), c1);\n            nc.z = dot(hash(s + offset2), c2);\n            nc.w = dot(hash(s + offset3), c3);\n\n            nc *= w*w*w*w;\n\n            // Add all the noise contributions.\n            // Should multiply by the possible max contribution to adjust the range in [-1,1].\n            return dot(vec4(32.), nc);\n        }\n\n        // Random rotations.\n        // The way you create fractal noise is layering simplex noise with some rotation.\n        // To make random cloud looking noise, the rotations should not align. (Otherwise it\n        // creates patterned noise).\n        // Below rotations only rotate in one axis.\n        const mat3 rot1 = mat3(1.0, 0. ,0., 0., 0.15, -0.98, 0., 0.98, 0.15);\n        const mat3 rot2 = mat3(-0.95, 0. ,-0.3, 0., 1., 0., 0.3, 0., -0.95);\n        const mat3 rot3 = mat3(1.0, 0. ,0., 0., -0.44, -0.89, 0., 0.89, -0.44);\n\n        // Octave = 4\n        // Divide each coefficient by 3 to produce more grainy noise.\n        half simplex3d_fractal(vec3 p) {\n            return 0.675 * simplex3d(p * rot1) + 0.225 * simplex3d(2.0 * p * rot2)\n                    + 0.075 * simplex3d(4.0 * p * rot3) + 0.025 * simplex3d(8.0 * p);\n        }\n\n        // Screen blend\n        vec3 screen(vec3 dest, vec3 src) {\n            return dest + src - dest * src;\n        }\n    \n            float soften(float d, float blur) {\n                float blurHalf = blur * 0.5;\n                return smoothstep(-blurHalf, blurHalf, d);\n            }\n\n            float subtract(float outer, float inner) {\n                return max(outer, -inner);\n            }\n        \n            float sdCircle(vec2 p, float r) {\n                return (length(p)-r) / r;\n            }\n\n            float circleRing(vec2 p, float radius) {\n                float thicknessHalf = radius * 0.25;\n\n                float outerCircle = sdCircle(p, radius + thicknessHalf);\n                float innerCircle = sdCircle(p, radius);\n\n                return subtract(outerCircle, innerCircle);\n            }\n        \n            uniform vec4 in_sparkleColor;\n                       \n            vec4 main(vec2 p) {\n                vec2 p_distorted = distort(p, in_time, in_distort_radial, in_distort_xy);\n                float radius = in_size.x * 0.5;\n                float sparkleRing = soften(circleRing(p_distorted-(in_rippleMove), radius), in_blur);\n                \n                // WORKAROUND: Optimization for \"sparkle-only\" rendering.\n                // return outside of valid area for better performance.\n                // this may cause unintended rendering only if the user put both sparkle and ripple effect.\n                if (in_sparkle_strength > 0.0 && sparkleRing > 0.95) {\n                    return vec4(0.0, 0.0, 0.0, 0.0); // keyword discard is not allowed in AGSL.\n                }\n                \n                float inside = soften(sdCircle(p_distorted-(in_rippleMove), radius * 1.25), in_blur);\n                float sparkle = sparkles(p - mod(p, in_pixelDensity * 0.8), in_time * 0.00175)\n                    * (1.-sparkleRing) * in_fadeSparkle;\n\n                float rippleInsideAlpha = (1.-inside) * in_fadeFill;\n                float rippleRingAlpha = (1.-sparkleRing) * in_fadeRing;\n                float rippleAlpha = max(rippleInsideAlpha, rippleRingAlpha) * in_color.a;\n                vec4 ripple = vec4(in_color.rgb, 1.0) * rippleAlpha;\n\n            if (in_sparkle_strength>0.0) {\n                vec3 wSparkleColor = mix(in_sparkleColor.rgb, vec3(1.0, 1.0, 1.0), 0.3);\n                return mix(ripple, vec4(wSparkleColor.rgb, sparkle * in_sparkle_strength), sparkle * in_sparkle_strength);\n            }\n            else{\n                return ripple;\n            }\n            \n//                return vec4(in_sparkleColor.rgb, sparkle * in_sparkle_strength);\n//return mix(ripple, vec4(sparkle), sparkle * in_sparkle_strength);\n       \n\n            }\n        ");
        Companion.getClass();
        this.rippleSize = new RippleSize(this);
        this.sparkleRingFadeParams = new FadeParams(0.0f, 0.1f, 0.4f, 1.0f);
        this.baseRingFadeParams = new FadeParams(0.0f, 0.1f, 0.3f, 1.0f);
        this.centerFillFadeParams = new FadeParams(0.0f, 0.0f, 0.0f, 0.6f);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return Float.hashCode(this.fadeOutEnd) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fadeOutStart, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fadeInEnd, Float.hashCode(this.fadeInStart) * 31, 31), 31);
        }

        public final String toString() {
            float f = this.fadeInStart;
            float f2 = this.fadeInEnd;
            float f3 = this.fadeOutStart;
            float f4 = this.fadeOutEnd;
            StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("FadeParams(fadeInStart=", f, ", fadeInEnd=", f2, ", fadeOutStart=");
            m.append(f3);
            m.append(", fadeOutEnd=");
            m.append(f4);
            m.append(")");
            return m.toString();
        }

        public /* synthetic */ FadeParams(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, f2, f3, (i & 8) != 0 ? 1.0f : f4);
        }
    }
}
