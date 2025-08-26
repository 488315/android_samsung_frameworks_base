package androidx.compose.ui.graphics.colorspace;

/* loaded from: classes.dex */
public final class Illuminant {
    public static final WhitePoint C;
    public static final WhitePoint D50;
    public static final float[] D50Xyz;
    public static final WhitePoint D60;
    public static final WhitePoint D65;
    public static final Illuminant INSTANCE = new Illuminant();

    static {
        new WhitePoint(0.44757f, 0.40745f);
        new WhitePoint(0.34842f, 0.35161f);
        C = new WhitePoint(0.31006f, 0.31616f);
        D50 = new WhitePoint(0.34567f, 0.3585f);
        new WhitePoint(0.33242f, 0.34743f);
        D60 = new WhitePoint(0.32168f, 0.33767f);
        D65 = new WhitePoint(0.31271f, 0.32902f);
        new WhitePoint(0.29902f, 0.31485f);
        new WhitePoint(0.33333f, 0.33333f);
        D50Xyz = new float[]{0.964212f, 1.0f, 0.825188f};
    }

    private Illuminant() {
    }
}
