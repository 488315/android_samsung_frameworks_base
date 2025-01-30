package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SpringForce {
    public double mDampedFreq;
    public double mDampingRatio;
    public double mFinalPosition;
    public double mGammaMinus;
    public double mGammaPlus;
    public boolean mInitialized;
    public final DynamicAnimation.MassState mMassState;
    public double mNaturalFreq;
    public double mValueThreshold;
    public double mVelocityThreshold;

    public SpringForce() {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
    }

    public final void setDampingRatio(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Damping ratio must be non-negative");
        }
        this.mDampingRatio = f;
        this.mInitialized = false;
    }

    public final void setStiffness(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Spring stiffness constant must be positive.");
        }
        this.mNaturalFreq = Math.sqrt(f);
        this.mInitialized = false;
    }

    public final DynamicAnimation.MassState updateValues(double d, double d2, long j) {
        double cos;
        double d3;
        if (!this.mInitialized) {
            if (this.mFinalPosition == Double.MAX_VALUE) {
                throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
            }
            double d4 = this.mDampingRatio;
            if (d4 > 1.0d) {
                double d5 = this.mNaturalFreq;
                this.mGammaPlus = (Math.sqrt((d4 * d4) - 1.0d) * d5) + ((-d4) * d5);
                double d6 = this.mDampingRatio;
                double d7 = this.mNaturalFreq;
                this.mGammaMinus = ((-d6) * d7) - (Math.sqrt((d6 * d6) - 1.0d) * d7);
            } else if (d4 >= 0.0d && d4 < 1.0d) {
                this.mDampedFreq = Math.sqrt(1.0d - (d4 * d4)) * this.mNaturalFreq;
            }
            this.mInitialized = true;
        }
        double d8 = j / 1000.0d;
        double d9 = d - this.mFinalPosition;
        double d10 = this.mDampingRatio;
        if (d10 > 1.0d) {
            double d11 = this.mGammaMinus;
            double d12 = this.mGammaPlus;
            double d13 = d9 - (((d11 * d9) - d2) / (d11 - d12));
            double d14 = ((d9 * d11) - d2) / (d11 - d12);
            d3 = (Math.pow(2.718281828459045d, this.mGammaPlus * d8) * d14) + (Math.pow(2.718281828459045d, d11 * d8) * d13);
            double d15 = this.mGammaMinus;
            double pow = Math.pow(2.718281828459045d, d15 * d8) * d13 * d15;
            double d16 = this.mGammaPlus;
            cos = (Math.pow(2.718281828459045d, d16 * d8) * d14 * d16) + pow;
        } else if (d10 == 1.0d) {
            double d17 = this.mNaturalFreq;
            double d18 = (d17 * d9) + d2;
            double d19 = (d18 * d8) + d9;
            double pow2 = Math.pow(2.718281828459045d, (-d17) * d8) * d19;
            double pow3 = Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d8) * d19;
            double d20 = this.mNaturalFreq;
            cos = (Math.pow(2.718281828459045d, (-d20) * d8) * d18) + (pow3 * (-d20));
            d3 = pow2;
        } else {
            double d21 = 1.0d / this.mDampedFreq;
            double d22 = this.mNaturalFreq;
            double d23 = ((d10 * d22 * d9) + d2) * d21;
            double sin = ((Math.sin(this.mDampedFreq * d8) * d23) + (Math.cos(this.mDampedFreq * d8) * d9)) * Math.pow(2.718281828459045d, (-d10) * d22 * d8);
            double d24 = this.mNaturalFreq;
            double d25 = this.mDampingRatio;
            double d26 = (-d24) * sin * d25;
            double pow4 = Math.pow(2.718281828459045d, (-d25) * d24 * d8);
            double d27 = this.mDampedFreq;
            double sin2 = Math.sin(d27 * d8) * (-d27) * d9;
            double d28 = this.mDampedFreq;
            cos = (((Math.cos(d28 * d8) * d23 * d28) + sin2) * pow4) + d26;
            d3 = sin;
        }
        float f = (float) (d3 + this.mFinalPosition);
        DynamicAnimation.MassState massState = this.mMassState;
        massState.mValue = f;
        massState.mVelocity = (float) cos;
        return massState;
    }

    public SpringForce(float f) {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
        this.mFinalPosition = f;
    }
}
