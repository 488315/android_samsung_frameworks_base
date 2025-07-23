package android.graphics;

import android.graphics.ColorSpace;
import android.graphics.Shader;

/* loaded from: classes.dex */
public class LinearGradient extends Shader {
    private int mColor0;
    private int mColor1;
    private final long[] mColorLongs;
    private int[] mColors;
    private float[] mPositions;
    private Shader.TileMode mTileMode;
    private float mX0;
    private float mX1;
    private float mY0;
    private float mY1;

    private native long nativeCreate(long j, float f, float f2, float f3, float f4, long[] jArr, float[] fArr, int i, long j2);

    public LinearGradient(float f, float f2, float f3, float f4, int[] iArr, float[] fArr, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, convertColors(iArr), fArr, tileMode, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public LinearGradient(float f, float f2, float f3, float f4, long[] jArr, float[] fArr, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, (long[]) jArr.clone(), fArr, tileMode, detectColorSpace(jArr));
    }

    private LinearGradient(float f, float f2, float f3, float f4, long[] jArr, float[] fArr, Shader.TileMode tileMode, ColorSpace colorSpace) {
        super(colorSpace);
        if (fArr != null && jArr.length != fArr.length) {
            throw new IllegalArgumentException("color and position arrays must be of equal length");
        }
        this.mX0 = f;
        this.mY0 = f2;
        this.mX1 = f3;
        this.mY1 = f4;
        this.mColorLongs = jArr;
        this.mPositions = fArr != null ? (float[]) fArr.clone() : null;
        this.mTileMode = tileMode;
    }

    public LinearGradient(float f, float f2, float f3, float f4, int i, int i2, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, Color.pack(i), Color.pack(i2), tileMode);
    }

    public LinearGradient(float f, float f2, float f3, float f4, long j, long j2, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, new long[]{j, j2}, (float[]) null, tileMode);
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        return nativeCreate(j, this.mX0, this.mY0, this.mX1, this.mY1, this.mColorLongs, this.mPositions, this.mTileMode.nativeInt, colorSpace().getNativeInstance());
    }
}
