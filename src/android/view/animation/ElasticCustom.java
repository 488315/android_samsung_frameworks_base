package android.view.animation;

/* loaded from: classes4.dex */
public class ElasticCustom extends BaseInterpolator {
    private float mAmplitude;
    private float mPeriod;

    public ElasticCustom() {
        this.mAmplitude = 1.0f;
        this.mPeriod = 0.2f;
    }

    public ElasticCustom(float f, float f2) {
        this.mAmplitude = f;
        this.mPeriod = f2;
    }

    public void setAmplitude(float f) {
        this.mAmplitude = f;
    }

    public void setPeriod(float f) {
        this.mPeriod = f;
    }

    public float getAmplitude() {
        return this.mAmplitude;
    }

    public float getPeriod() {
        return this.mPeriod;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f, this.mAmplitude, this.mPeriod);
    }

    private float out(float f, float f2, float f3) {
        float f4;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f3 == 0.0f) {
            f3 = 0.3f;
        }
        if (f2 == 0.0f || f2 < 1.0f) {
            f4 = f3 / 4.0f;
            f2 = 1.0f;
        } else {
            f4 = (float) ((f3 / 6.283185307179586d) * Math.asin(1.0f / f2));
        }
        return (float) ((f2 * Math.pow(2.0d, (-10.0f) * f) * Math.sin(((f - f4) * 6.283185307179586d) / f3)) + 1.0d);
    }
}
