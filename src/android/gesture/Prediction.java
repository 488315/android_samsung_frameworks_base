package android.gesture;

/* loaded from: classes.dex */
public class Prediction {
    public final String name;
    public double score;

    Prediction(String str, double d) {
        this.name = str;
        this.score = d;
    }

    public String toString() {
        return this.name;
    }
}
