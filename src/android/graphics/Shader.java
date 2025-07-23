package android.graphics;

import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Shader {
    private Runnable mCleaner;
    private final ColorSpace mColorSpace;
    private Matrix mLocalMatrix;
    private long mNativeInstance;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    protected long createNativeInstance(long j, boolean z) {
        return 0L;
    }

    protected boolean shouldDiscardNativeInstance(boolean z) {
        return false;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Shader.class.getClassLoader(), Shader.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    @Deprecated
    public Shader() {
        this.mColorSpace = null;
    }

    protected Shader(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (colorSpace == null) {
            throw new IllegalArgumentException("Use Shader() to create a Shader with no ColorSpace");
        }
        colorSpace.getNativeInstance();
    }

    protected ColorSpace colorSpace() {
        return this.mColorSpace;
    }

    public enum TileMode {
        CLAMP(0),
        REPEAT(1),
        MIRROR(2),
        DECAL(3);

        final int nativeInt;

        TileMode(int i) {
            this.nativeInt = i;
        }
    }

    public boolean getLocalMatrix(Matrix matrix) {
        Matrix matrix2 = this.mLocalMatrix;
        if (matrix2 == null) {
            return false;
        }
        matrix.set(matrix2);
        return true;
    }

    public void setLocalMatrix(Matrix matrix) {
        if (matrix == null || matrix.isIdentity()) {
            if (this.mLocalMatrix != null) {
                this.mLocalMatrix = null;
                discardNativeInstance();
                return;
            }
            return;
        }
        Matrix matrix2 = this.mLocalMatrix;
        if (matrix2 == null) {
            this.mLocalMatrix = new Matrix(matrix);
            discardNativeInstance();
        } else {
            if (matrix2.equals(matrix)) {
                return;
            }
            this.mLocalMatrix.set(matrix);
            discardNativeInstance();
        }
    }

    protected final synchronized void discardNativeInstance() {
        discardNativeInstanceLocked();
    }

    private void discardNativeInstanceLocked() {
        if (this.mNativeInstance != 0) {
            this.mCleaner.run();
            this.mCleaner = null;
            this.mNativeInstance = 0L;
        }
    }

    public final synchronized long getNativeInstance(boolean z) {
        if (shouldDiscardNativeInstance(z)) {
            discardNativeInstanceLocked();
        }
        if (this.mNativeInstance == 0) {
            Matrix matrix = this.mLocalMatrix;
            long createNativeInstance = createNativeInstance(matrix == null ? 0L : matrix.ni(), z);
            this.mNativeInstance = createNativeInstance;
            if (createNativeInstance != 0) {
                this.mCleaner = NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }

    public final long getNativeInstance() {
        return getNativeInstance(false);
    }

    protected static long[] convertColors(int[] iArr) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("needs >= 2 number of colors");
        }
        long[] jArr = new long[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            jArr[i] = Color.pack(iArr[i]);
        }
        return jArr;
    }

    protected static ColorSpace detectColorSpace(long[] jArr) {
        if (jArr.length < 2) {
            throw new IllegalArgumentException("needs >= 2 number of colors");
        }
        ColorSpace colorSpace = Color.colorSpace(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            if (Color.colorSpace(jArr[i]) != colorSpace) {
                throw new IllegalArgumentException("All colors must be in the same ColorSpace!");
            }
        }
        return colorSpace;
    }
}
