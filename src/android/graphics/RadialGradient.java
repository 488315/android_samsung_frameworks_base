package android.graphics;

import android.graphics.ColorSpace;
import android.graphics.Shader;

/* loaded from: classes.dex */
public class RadialGradient extends Shader {
    private int mCenterColor;
    private final long[] mColorLongs;
    private int[] mColors;
    private int mEdgeColor;
    private final float mFocalRadius;
    private final float mFocalX;
    private final float mFocalY;
    private float[] mPositions;
    private float mRadius;
    private Shader.TileMode mTileMode;
    private float mX;
    private float mY;

    private static native long nativeCreate(long j, float f, float f2, float f3, float f4, float f5, float f6, long[] jArr, float[] fArr, int i, long j2);

    public RadialGradient(float f, float f2, float f3, int[] iArr, float[] fArr, Shader.TileMode tileMode) {
        this(f, f2, 0.0f, f, f2, f3, convertColors(iArr), fArr, tileMode, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public RadialGradient(float f, float f2, float f3, long[] jArr, float[] fArr, Shader.TileMode tileMode) {
        this(f, f2, 0.0f, f, f2, f3, (long[]) jArr.clone(), fArr, tileMode, detectColorSpace(jArr));
    }

    public RadialGradient(float f, float f2, float f3, float f4, float f5, float f6, long[] jArr, float[] fArr, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, f5, f6, (long[]) jArr.clone(), fArr, tileMode, detectColorSpace(jArr));
    }

    private RadialGradient(float f, float f2, float f3, float f4, float f5, float f6, long[] jArr, float[] fArr, Shader.TileMode tileMode, ColorSpace colorSpace) {
        super(colorSpace);
        if (f3 < 0.0f) {
            throw new IllegalArgumentException("starting/focal radius must be >= 0");
        }
        if (f6 <= 0.0f) {
            throw new IllegalArgumentException("ending radius must be > 0");
        }
        if (fArr != null && jArr.length != fArr.length) {
            throw new IllegalArgumentException("color and position arrays must be of equal length");
        }
        this.mX = f4;
        this.mY = f5;
        this.mRadius = f6;
        this.mFocalX = f;
        this.mFocalY = f2;
        this.mFocalRadius = f3;
        this.mColorLongs = jArr;
        this.mPositions = fArr != null ? (float[]) fArr.clone() : null;
        this.mTileMode = tileMode;
    }

    public RadialGradient(float f, float f2, float f3, int i, int i2, Shader.TileMode tileMode) {
        this(f, f2, f3, Color.pack(i), Color.pack(i2), tileMode);
    }

    public RadialGradient(float f, float f2, float f3, long j, long j2, Shader.TileMode tileMode) {
        this(f, f2, f3, new long[]{j, j2}, (float[]) null, tileMode);
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        return nativeCreate(j, this.mFocalX, this.mFocalY, this.mFocalRadius, this.mX, this.mY, this.mRadius, this.mColorLongs, this.mPositions, this.mTileMode.nativeInt, colorSpace().getNativeInstance());
    }
}
