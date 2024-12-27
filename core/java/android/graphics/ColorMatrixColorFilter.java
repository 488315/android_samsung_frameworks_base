package android.graphics;

public class ColorMatrixColorFilter extends ColorFilter {
    private final ColorMatrix mMatrix = new ColorMatrix();

    private static native long nativeColorMatrixFilter(float[] fArr);

    private static native void nativeSetColorMatrix(long j, float[] fArr);

    public ColorMatrixColorFilter(ColorMatrix matrix) {
        this.mMatrix.set(matrix);
    }

    public ColorMatrixColorFilter(float[] array) {
        if (array.length < 20) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.mMatrix.set(array);
    }

    public void getColorMatrix(ColorMatrix colorMatrix) {
        colorMatrix.set(this.mMatrix);
    }

    public void setColorMatrix(ColorMatrix matrix) {
        if (matrix == null) {
            this.mMatrix.reset();
        } else {
            this.mMatrix.set(matrix);
        }
        nativeSetColorMatrix(getNativeInstance(), this.mMatrix.getArray());
    }

    public void setColorMatrixArray(float[] array) {
        if (array == null) {
            this.mMatrix.reset();
        } else {
            if (array.length < 20) {
                throw new ArrayIndexOutOfBoundsException();
            }
            this.mMatrix.set(array);
        }
        nativeSetColorMatrix(getNativeInstance(), this.mMatrix.getArray());
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return nativeColorMatrixFilter(this.mMatrix.getArray());
    }
}
