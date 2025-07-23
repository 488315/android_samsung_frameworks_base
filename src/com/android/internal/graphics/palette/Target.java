package com.android.internal.graphics.palette;

/* loaded from: classes5.dex */
public final class Target {
    private static final float DEFAULT_CHROMA_MAX = 130.0f;
    private static final float DEFAULT_CHROMA_MIN = 0.0f;
    private static final float DEFAULT_CHROMA_TARGET = 30.0f;
    private static final float WEIGHT_CHROMA = 0.5f;
    private static final float WEIGHT_HUE = 0.2f;
    private static final float WEIGHT_POPULATION = 0.3f;
    private static final float WEIGHT_RELATIVE_LUMINANCE = 0.5f;
    private float mChromaMax;
    private float mChromaMin;
    private float mChromaTarget;
    private float mChromaWeight;
    private float mHueWeight;
    private float mPopulationWeight;
    private float mRelativeLuminanceWeight;
    private float mTargetHue;
    private float mTargetRelativeLuminance;

    Target() {
        this.mTargetRelativeLuminance = -1.0f;
        this.mChromaMax = DEFAULT_CHROMA_MAX;
        this.mChromaMin = 0.0f;
        this.mChromaTarget = 30.0f;
        this.mChromaWeight = 0.5f;
        this.mRelativeLuminanceWeight = 0.5f;
        this.mPopulationWeight = WEIGHT_POPULATION;
        this.mHueWeight = 0.2f;
    }

    Target(Target target) {
        this.mTargetRelativeLuminance = -1.0f;
        this.mTargetRelativeLuminance = target.mTargetRelativeLuminance;
        this.mChromaWeight = target.mChromaWeight;
        this.mRelativeLuminanceWeight = target.mRelativeLuminanceWeight;
        this.mPopulationWeight = target.mPopulationWeight;
        this.mHueWeight = target.mHueWeight;
        this.mChromaTarget = target.mChromaTarget;
        this.mChromaMin = target.mChromaMin;
        this.mChromaMax = target.mChromaMax;
    }

    public float getTargetRelativeLuminance() {
        return this.mTargetRelativeLuminance;
    }

    public float getTargetPerceptualLuminance() {
        return Contrast.yToLstar(this.mTargetRelativeLuminance);
    }

    public float getMinimumChroma() {
        return this.mChromaMin;
    }

    public float getTargetChroma() {
        return this.mChromaTarget;
    }

    public float getMaximumChroma() {
        return this.mChromaMax;
    }

    public float getTargetHue() {
        return this.mTargetHue;
    }

    public float getChromaWeight() {
        return this.mChromaWeight;
    }

    public float getLightnessWeight() {
        return this.mRelativeLuminanceWeight;
    }

    public float getPopulationWeight() {
        return this.mPopulationWeight;
    }

    public float getHueWeight() {
        return this.mHueWeight;
    }

    public static class Builder {
        private final Target mTarget;

        public Builder() {
            this.mTarget = new Target();
        }

        public Builder(Target target) {
            this.mTarget = new Target(target);
        }

        public Builder setMinimumChroma(float f) {
            this.mTarget.mChromaMin = f;
            return this;
        }

        public Builder setTargetChroma(float f) {
            this.mTarget.mChromaTarget = f;
            return this;
        }

        public Builder setMaximumChroma(float f) {
            this.mTarget.mChromaMax = f;
            return this;
        }

        public Builder setTargetRelativeLuminance(float f) {
            this.mTarget.mTargetRelativeLuminance = f;
            return this;
        }

        public Builder setTargetPerceptualLuminance(float f) {
            this.mTarget.mTargetRelativeLuminance = Contrast.lstarToY(f);
            return this;
        }

        public Builder setTargetHue(int i) {
            this.mTarget.mTargetHue = i;
            return this;
        }

        public Builder setContrastRatio(float f, float f2) {
            float darkerY;
            if (Contrast.yToLstar(f2) < 50.0f) {
                darkerY = Contrast.lighterY(f2, f);
            } else {
                darkerY = Contrast.darkerY(f2, f);
            }
            this.mTarget.mTargetRelativeLuminance = darkerY;
            return this;
        }

        public Builder setChromaWeight(float f) {
            this.mTarget.mChromaWeight = f;
            return this;
        }

        public Builder setLightnessWeight(float f) {
            this.mTarget.mRelativeLuminanceWeight = f;
            return this;
        }

        public Builder setPopulationWeight(float f) {
            this.mTarget.mPopulationWeight = f;
            return this;
        }

        public Target build() {
            return this.mTarget;
        }
    }
}
