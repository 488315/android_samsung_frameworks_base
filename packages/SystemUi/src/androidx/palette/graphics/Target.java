package androidx.palette.graphics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Target {
    public static final Target DARK_MUTED;
    public static final Target DARK_VIBRANT;
    public static final Target LIGHT_MUTED;
    public static final Target LIGHT_VIBRANT;
    public static final Target MUTED;
    public static final Target VIBRANT;
    public final boolean mIsExclusive;
    public final float[] mLightnessTargets;
    public final float[] mSaturationTargets;
    public final float[] mWeights;

    static {
        Target target = new Target();
        LIGHT_VIBRANT = target;
        float[] fArr = target.mLightnessTargets;
        fArr[0] = 0.55f;
        fArr[1] = 0.74f;
        float[] fArr2 = target.mSaturationTargets;
        fArr2[0] = 0.35f;
        fArr2[1] = 1.0f;
        Target target2 = new Target();
        VIBRANT = target2;
        float[] fArr3 = target2.mLightnessTargets;
        fArr3[0] = 0.3f;
        fArr3[1] = 0.5f;
        fArr3[2] = 0.7f;
        float[] fArr4 = target2.mSaturationTargets;
        fArr4[0] = 0.35f;
        fArr4[1] = 1.0f;
        Target target3 = new Target();
        DARK_VIBRANT = target3;
        float[] fArr5 = target3.mLightnessTargets;
        fArr5[1] = 0.26f;
        fArr5[2] = 0.45f;
        float[] fArr6 = target3.mSaturationTargets;
        fArr6[0] = 0.35f;
        fArr6[1] = 1.0f;
        Target target4 = new Target();
        LIGHT_MUTED = target4;
        float[] fArr7 = target4.mLightnessTargets;
        fArr7[0] = 0.55f;
        fArr7[1] = 0.74f;
        float[] fArr8 = target4.mSaturationTargets;
        fArr8[1] = 0.3f;
        fArr8[2] = 0.4f;
        Target target5 = new Target();
        MUTED = target5;
        float[] fArr9 = target5.mLightnessTargets;
        fArr9[0] = 0.3f;
        fArr9[1] = 0.5f;
        fArr9[2] = 0.7f;
        float[] fArr10 = target5.mSaturationTargets;
        fArr10[1] = 0.3f;
        fArr10[2] = 0.4f;
        Target target6 = new Target();
        DARK_MUTED = target6;
        float[] fArr11 = target6.mLightnessTargets;
        fArr11[1] = 0.26f;
        fArr11[2] = 0.45f;
        float[] fArr12 = target6.mSaturationTargets;
        fArr12[1] = 0.3f;
        fArr12[2] = 0.4f;
    }

    public Target() {
        this.mSaturationTargets = new float[]{0.0f, 0.5f, 1.0f};
        this.mLightnessTargets = new float[]{0.0f, 0.5f, 1.0f};
        this.mWeights = new float[]{0.24f, 0.0f, 0.0f};
        this.mIsExclusive = true;
    }

    public Target(Target target) {
        float[] fArr = new float[3];
        this.mSaturationTargets = fArr;
        float[] fArr2 = new float[3];
        this.mLightnessTargets = fArr2;
        float[] fArr3 = new float[3];
        this.mWeights = fArr3;
        this.mIsExclusive = true;
        System.arraycopy(target.mSaturationTargets, 0, fArr, 0, fArr.length);
        System.arraycopy(target.mLightnessTargets, 0, fArr2, 0, fArr2.length);
        System.arraycopy(target.mWeights, 0, fArr3, 0, fArr3.length);
    }
}
