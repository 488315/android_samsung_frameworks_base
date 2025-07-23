package android.os.vibrator;

import java.util.Objects;

/* loaded from: classes3.dex */
public final class PwlePoint {
    private final float mAmplitude;
    private final float mFrequencyHz;
    private final int mTimeMillis;

    public PwlePoint(float f, float f2, int i) {
        this.mAmplitude = f;
        this.mFrequencyHz = f2;
        this.mTimeMillis = i;
    }

    public float getAmplitude() {
        return this.mAmplitude;
    }

    public float getFrequencyHz() {
        return this.mFrequencyHz;
    }

    public int getTimeMillis() {
        return this.mTimeMillis;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PwlePoint)) {
            return false;
        }
        PwlePoint pwlePoint = (PwlePoint) obj;
        return Float.compare(this.mAmplitude, pwlePoint.mAmplitude) == 0 && Float.compare(this.mFrequencyHz, pwlePoint.mFrequencyHz) == 0 && this.mTimeMillis == pwlePoint.mTimeMillis;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mAmplitude), Float.valueOf(this.mFrequencyHz), Integer.valueOf(this.mTimeMillis));
    }

    public String toString() {
        return "PwlePoint{amplitude=" + this.mAmplitude + ", frequency=" + this.mFrequencyHz + ", time=" + this.mTimeMillis + "}";
    }
}
