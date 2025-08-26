package android.graphics;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Path {
    static final FillType[] sFillTypeArray = {FillType.WINDING, FillType.EVEN_ODD, FillType.INVERSE_WINDING, FillType.INVERSE_EVEN_ODD};
    public final long mNativePath;

    public enum Op {
        DIFFERENCE,
        INTERSECT,
        UNION,
        XOR,
        REVERSE_DIFFERENCE
    }

    private static native void nAddArc(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nAddCircle(long j, float f, float f2, float f3, int i);

    private static native void nAddOval(long j, float f, float f2, float f3, float f4, int i);

    private static native void nAddPath(long j, long j2);

    private static native void nAddPath(long j, long j2, float f, float f2);

    private static native void nAddPath(long j, long j2, long j3);

    private static native void nAddRect(long j, float f, float f2, float f3, float f4, int i);

    private static native void nAddRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, int i);

    private static native void nAddRoundRect(long j, float f, float f2, float f3, float f4, float[] fArr, int i);

    private static native float[] nApproximate(long j, float f);

    private static native void nArcTo(long j, float f, float f2, float f3, float f4, float f5, float f6, boolean z);

    private static native void nClose(long j);

    private static native void nComputeBounds(long j, RectF rectF);

    private static native void nConicTo(long j, float f, float f2, float f3, float f4, float f5);

    private static native void nCubicTo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    @CriticalNative
    private static native int nGetFillType(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    @CriticalNative
    private static native int nGetGenerationID(long j);

    private static native void nIncReserve(long j, int i);

    private static native long nInit();

    private static native long nInit(long j);

    private static native boolean nInterpolate(long j, long j2, float f, long j3);

    @CriticalNative
    private static native boolean nIsConvex(long j);

    @CriticalNative
    private static native boolean nIsEmpty(long j);

    @CriticalNative
    private static native boolean nIsInterpolatable(long j, long j2);

    @FastNative
    private static native boolean nIsRect(long j, RectF rectF);

    private static native void nLineTo(long j, float f, float f2);

    private static native void nMoveTo(long j, float f, float f2);

    private static native void nOffset(long j, float f, float f2);

    private static native boolean nOp(long j, long j2, int i, long j3);

    private static native void nQuadTo(long j, float f, float f2, float f3, float f4);

    private static native void nRConicTo(long j, float f, float f2, float f3, float f4, float f5);

    private static native void nRCubicTo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nRLineTo(long j, float f, float f2);

    private static native void nRMoveTo(long j, float f, float f2);

    private static native void nRQuadTo(long j, float f, float f2, float f3, float f4);

    @CriticalNative
    private static native void nReset(long j);

    @CriticalNative
    private static native void nRewind(long j);

    private static native void nSet(long j, long j2);

    @CriticalNative
    private static native void nSetFillType(long j, int i);

    private static native void nSetLastPoint(long j, float f, float f2);

    private static native void nTransform(long j, long j2);

    private static native void nTransform(long j, long j2, long j3);

    private static class NoImagePreloadHolder {
        static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Path.class.getClassLoader(), Path.nGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public Path() {
        long jNInit = nInit();
        this.mNativePath = jNInit;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, jNInit);
    }

    public Path(Path path) {
        long jNInit = nInit(path != null ? path.mNativePath : 0L);
        this.mNativePath = jNInit;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, jNInit);
    }

    public void reset() {
        FillType fillType = getFillType();
        nReset(this.mNativePath);
        setFillType(fillType);
    }

    public void rewind() {
        nRewind(this.mNativePath);
    }

    public void set(Path path) {
        if (this == path) {
            return;
        }
        nSet(this.mNativePath, path.mNativePath);
    }

    public PathIterator getPathIterator() {
        return new PathIterator(this);
    }

    public boolean op(Path path, Op op) {
        return op(this, path, op);
    }

    public boolean op(Path path, Path path2, Op op) {
        return nOp(path.mNativePath, path2.mNativePath, op.ordinal(), this.mNativePath);
    }

    @Deprecated
    public boolean isConvex() {
        return nIsConvex(this.mNativePath);
    }

    public enum FillType {
        WINDING(0),
        EVEN_ODD(1),
        INVERSE_WINDING(2),
        INVERSE_EVEN_ODD(3);

        final int nativeInt;

        FillType(int i) {
            this.nativeInt = i;
        }
    }

    public FillType getFillType() {
        return sFillTypeArray[nGetFillType(this.mNativePath)];
    }

    public void setFillType(FillType fillType) {
        nSetFillType(this.mNativePath, fillType.nativeInt);
    }

    public boolean isInverseFillType() {
        return (nGetFillType(this.mNativePath) & FillType.INVERSE_WINDING.nativeInt) != 0;
    }

    public void toggleInverseFillType() {
        nSetFillType(this.mNativePath, nGetFillType(this.mNativePath) ^ FillType.INVERSE_WINDING.nativeInt);
    }

    public boolean isEmpty() {
        return nIsEmpty(this.mNativePath);
    }

    public boolean isRect(RectF rectF) {
        return nIsRect(this.mNativePath, rectF);
    }

    public void computeBounds(RectF rectF, boolean z) {
        computeBounds(rectF);
    }

    public void computeBounds(RectF rectF) {
        nComputeBounds(this.mNativePath, rectF);
    }

    public void incReserve(int i) {
        nIncReserve(this.mNativePath, i);
    }

    public void moveTo(float f, float f2) {
        nMoveTo(this.mNativePath, f, f2);
    }

    public void rMoveTo(float f, float f2) {
        nRMoveTo(this.mNativePath, f, f2);
    }

    public void lineTo(float f, float f2) {
        nLineTo(this.mNativePath, f, f2);
    }

    public void rLineTo(float f, float f2) {
        nRLineTo(this.mNativePath, f, f2);
    }

    public void quadTo(float f, float f2, float f3, float f4) {
        nQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public void rQuadTo(float f, float f2, float f3, float f4) {
        nRQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public void conicTo(float f, float f2, float f3, float f4, float f5) {
        nConicTo(this.mNativePath, f, f2, f3, f4, f5);
    }

    public void rConicTo(float f, float f2, float f3, float f4, float f5) {
        nRConicTo(this.mNativePath, f, f2, f3, f4, f5);
    }

    public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        nCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void rCubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        nRCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void arcTo(RectF rectF, float f, float f2, boolean z) {
        arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, z);
    }

    public void arcTo(RectF rectF, float f, float f2) {
        arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, false);
    }

    public void arcTo(float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
        nArcTo(this.mNativePath, f, f2, f3, f4, f5, f6, z);
    }

    public void close() {
        nClose(this.mNativePath);
    }

    public enum Direction {
        CW(0),
        CCW(1);

        final int nativeInt;

        Direction(int i) {
            this.nativeInt = i;
        }
    }

    public void addRect(RectF rectF, Direction direction) {
        addRect(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addRect(float f, float f2, float f3, float f4, Direction direction) {
        nAddRect(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addOval(RectF rectF, Direction direction) {
        addOval(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addOval(float f, float f2, float f3, float f4, Direction direction) {
        nAddOval(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addCircle(float f, float f2, float f3, Direction direction) {
        nAddCircle(this.mNativePath, f, f2, f3, direction.nativeInt);
    }

    public void addArc(RectF rectF, float f, float f2) {
        addArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2);
    }

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        nAddArc(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void addRoundRect(RectF rectF, float f, float f2, Direction direction) {
        addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, direction);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Direction direction) {
        nAddRoundRect(this.mNativePath, f, f2, f3, f4, f5, f6, direction.nativeInt);
    }

    public void addRoundRect(RectF rectF, float[] fArr, Direction direction) {
        if (rectF == null) {
            throw new NullPointerException("need rect parameter");
        }
        addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, fArr, direction);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float[] fArr, Direction direction) {
        if (fArr.length < 8) {
            throw new ArrayIndexOutOfBoundsException("radii[] needs 8 values");
        }
        nAddRoundRect(this.mNativePath, f, f2, f3, f4, fArr, direction.nativeInt);
    }

    public void addPath(Path path, float f, float f2) {
        nAddPath(this.mNativePath, path.mNativePath, f, f2);
    }

    public void addPath(Path path) {
        nAddPath(this.mNativePath, path.mNativePath);
    }

    public void addPath(Path path, Matrix matrix) {
        nAddPath(this.mNativePath, path.mNativePath, matrix.ni());
    }

    public void offset(float f, float f2, Path path) {
        if (path != null) {
            path.set(this);
            this = path;
        }
        this.offset(f, f2);
    }

    public void offset(float f, float f2) {
        nOffset(this.mNativePath, f, f2);
    }

    public void setLastPoint(float f, float f2) {
        nSetLastPoint(this.mNativePath, f, f2);
    }

    public void transform(Matrix matrix, Path path) {
        nTransform(this.mNativePath, matrix.ni(), path != null ? path.mNativePath : 0L);
    }

    public void transform(Matrix matrix) {
        nTransform(this.mNativePath, matrix.ni());
    }

    public final long readOnlyNI() {
        return this.mNativePath;
    }

    final long mutateNI() {
        return this.mNativePath;
    }

    public float[] approximate(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("AcceptableError must be greater than or equal to 0");
        }
        return nApproximate(this.mNativePath, f);
    }

    public int getGenerationId() {
        return nGetGenerationID(this.mNativePath);
    }

    public boolean isInterpolatable(Path path) {
        return nIsInterpolatable(this.mNativePath, path.mNativePath);
    }

    public boolean interpolate(Path path, float f, Path path2) {
        return nInterpolate(this.mNativePath, path.mNativePath, f, path2.mNativePath);
    }
}
