package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        double sin;
        double cos;
        if (!this.mInitialized) {
            if (this.mFinalPosition == Double.MAX_VALUE) {
                throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
            }
            double d3 = this.mDampingRatio;
            if (d3 > 1.0d) {
                double d4 = this.mNaturalFreq;
                this.mGammaPlus = (Math.sqrt((d3 * d3) - 1.0d) * d4) + ((-d3) * d4);
                double d5 = this.mDampingRatio;
                double d6 = this.mNaturalFreq;
                this.mGammaMinus = ((-d5) * d6) - (Math.sqrt((d5 * d5) - 1.0d) * d6);
            } else if (d3 >= 0.0d && d3 < 1.0d) {
                this.mDampedFreq = Math.sqrt(1.0d - (d3 * d3)) * this.mNaturalFreq;
            }
            this.mInitialized = true;
        }
        double d7 = j / 1000.0d;
        double d8 = d - this.mFinalPosition;
        double d9 = this.mDampingRatio;
        if (d9 > 1.0d) {
            double d10 = this.mGammaMinus;
            double d11 = ((d10 * d8) - d2) / (d10 - this.mGammaPlus);
            double d12 = d8 - d11;
            sin = (Math.pow(2.718281828459045d, this.mGammaPlus * d7) * d11) + (Math.pow(2.718281828459045d, d10 * d7) * d12);
            double d13 = this.mGammaMinus;
            double pow = Math.pow(2.718281828459045d, d13 * d7) * d12 * d13;
            double d14 = this.mGammaPlus;
            cos = (Math.pow(2.718281828459045d, d14 * d7) * d11 * d14) + pow;
        } else if (d9 == 1.0d) {
            double d15 = this.mNaturalFreq;
            double d16 = (d15 * d8) + d2;
            double d17 = (d16 * d7) + d8;
            double pow2 = Math.pow(2.718281828459045d, (-d15) * d7) * d17;
            double pow3 = Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d7) * d17;
            double d18 = -this.mNaturalFreq;
            cos = (Math.pow(2.718281828459045d, d18 * d7) * d16) + (pow3 * d18);
            sin = pow2;
        } else {
            double d19 = 1.0d / this.mDampedFreq;
            double d20 = this.mNaturalFreq;
            double d21 = ((d9 * d20 * d8) + d2) * d19;
            sin = ((Math.sin(this.mDampedFreq * d7) * d21) + (Math.cos(this.mDampedFreq * d7) * d8)) * Math.pow(2.718281828459045d, (-d9) * d20 * d7);
            double d22 = this.mNaturalFreq;
            double d23 = this.mDampingRatio;
            double d24 = (-d22) * sin * d23;
            double pow4 = Math.pow(2.718281828459045d, (-d23) * d22 * d7);
            double d25 = this.mDampedFreq;
            double sin2 = Math.sin(d25 * d7) * (-d25) * d8;
            double d26 = this.mDampedFreq;
            cos = (((Math.cos(d26 * d7) * d21 * d26) + sin2) * pow4) + d24;
        }
        float f = (float) (sin + this.mFinalPosition);
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
