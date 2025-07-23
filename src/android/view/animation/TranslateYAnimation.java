package android.view.animation;

/* loaded from: classes4.dex */
public class TranslateYAnimation extends TranslateAnimation {
    float[] mTmpValues;

    public TranslateYAnimation(float f, float f2) {
        super(0.0f, 0.0f, f, f2);
        this.mTmpValues = new float[9];
    }

    public TranslateYAnimation(int i, float f, int i2, float f2) {
        super(0, 0.0f, 0, 0.0f, i, f, i2, f2);
        this.mTmpValues = new float[9];
    }

    @Override // android.view.animation.TranslateAnimation, android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.getMatrix().getValues(this.mTmpValues);
        transformation.getMatrix().setTranslate(this.mTmpValues[2], this.mFromYDelta + ((this.mToYDelta - this.mFromYDelta) * f));
    }
}
