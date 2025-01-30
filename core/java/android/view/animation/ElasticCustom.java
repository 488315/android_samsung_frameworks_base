package android.view.animation;

/* loaded from: classes4.dex */
public class ElasticCustom extends BaseInterpolator {
  private float mAmplitude;
  private float mPeriod;

  public ElasticCustom() {
    this.mAmplitude = 1.0f;
    this.mPeriod = 0.2f;
  }

  public ElasticCustom(float amplitude, float period) {
    this.mAmplitude = 1.0f;
    this.mPeriod = 0.2f;
    this.mAmplitude = amplitude;
    this.mPeriod = period;
  }

  public void setAmplitude(float value) {
    this.mAmplitude = value;
  }

  public void setPeriod(float value) {
    this.mPeriod = value;
  }

  public float getAmplitude() {
    return this.mAmplitude;
  }

  public float getPeriod() {
    return this.mPeriod;
  }

  @Override // android.animation.TimeInterpolator
  public float getInterpolation(float t) {
    return out(t, this.mAmplitude, this.mPeriod);
  }

  private float out(float t, float a, float p) {
    float s;
    if (t == 0.0f) {
      return 0.0f;
    }
    if (t >= 1.0f) {
      return 1.0f;
    }
    if (p == 0.0f) {
      p = 0.3f;
    }
    if (a != 0.0f && a >= 1.0f) {
      s = (float) ((p / 6.283185307179586d) * Math.asin(1.0f / a));
    } else {
      a = 1.0f;
      s = p / 4.0f;
    }
    return (float)
        ((a * Math.pow(2.0d, (-10.0f) * t) * Math.sin(((t - s) * 6.283185307179586d) / p)) + 1.0d);
  }
}
