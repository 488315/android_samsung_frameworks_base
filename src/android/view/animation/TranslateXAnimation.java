package android.view.animation;

/* loaded from: classes4.dex */
public class TranslateXAnimation extends TranslateAnimation {
    float[] mTmpValues;

    public TranslateXAnimation(float f, float f2) {
        super(f, f2, 0.0f, 0.0f);
        this.mTmpValues = new float[9];
    }

    public TranslateXAnimation(int i, float f, int i2, float f2) {
        super(i, f, i2, f2, 0, 0.0f, 0, 0.0f);
        this.mTmpValues = new float[9];
    }

    @Override // android.view.animation.TranslateAnimation, android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.getMatrix().getValues(this.mTmpValues);
        transformation.getMatrix().setTranslate(this.mFromXDelta + ((this.mToXDelta - this.mFromXDelta) * f), this.mTmpValues[5]);
    }
}
