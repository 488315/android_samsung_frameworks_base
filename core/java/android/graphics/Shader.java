package android.graphics;

import libcore.util.NativeAllocationRegistry;

public class Shader {
    private Runnable mCleaner;
    private final ColorSpace mColorSpace;
    private Matrix mLocalMatrix;
    private long mNativeInstance;

    public static native long nativeGetFinalizer();

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry =
                NativeAllocationRegistry.createMalloced(
                        Shader.class.getClassLoader(), Shader.nativeGetFinalizer());

        private NoImagePreloadHolder() {}
    }

    @Deprecated
    public Shader() {
        this.mColorSpace = null;
    }

    protected Shader(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (colorSpace == null) {
            throw new IllegalArgumentException(
                    "Use Shader() to create a Shader with no ColorSpace");
        }
        this.mColorSpace.getNativeInstance();
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

        TileMode(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public boolean getLocalMatrix(Matrix localM) {
        if (this.mLocalMatrix != null) {
            localM.set(this.mLocalMatrix);
            return true;
        }
        return false;
    }

    public void setLocalMatrix(Matrix localM) {
        if (localM == null || localM.isIdentity()) {
            if (this.mLocalMatrix != null) {
                this.mLocalMatrix = null;
                discardNativeInstance();
                return;
            }
            return;
        }
        if (this.mLocalMatrix == null) {
            this.mLocalMatrix = new Matrix(localM);
            discardNativeInstance();
        } else if (!this.mLocalMatrix.equals(localM)) {
            this.mLocalMatrix.set(localM);
            discardNativeInstance();
        }
    }

    protected long createNativeInstance(long nativeMatrix, boolean filterFromPaint) {
        return 0L;
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

    protected boolean shouldDiscardNativeInstance(boolean filterBitmap) {
        return false;
    }

    public final synchronized long getNativeInstance(boolean filterFromPaint) {
        if (shouldDiscardNativeInstance(filterFromPaint)) {
            discardNativeInstanceLocked();
        }
        if (this.mNativeInstance == 0) {
            this.mNativeInstance =
                    createNativeInstance(
                            this.mLocalMatrix == null ? 0L : this.mLocalMatrix.ni(),
                            filterFromPaint);
            if (this.mNativeInstance != 0) {
                this.mCleaner =
                        NoImagePreloadHolder.sRegistry.registerNativeAllocation(
                                this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }

    public final long getNativeInstance() {
        return getNativeInstance(false);
    }

    protected static long[] convertColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("needs >= 2 number of colors");
        }
        long[] colorLongs = new long[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colorLongs[i] = Color.pack(colors[i]);
        }
        return colorLongs;
    }

    protected static ColorSpace detectColorSpace(long[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("needs >= 2 number of colors");
        }
        ColorSpace colorSpace = Color.colorSpace(colors[0]);
        for (int i = 1; i < colors.length; i++) {
            if (Color.colorSpace(colors[i]) != colorSpace) {
                throw new IllegalArgumentException("All colors must be in the same ColorSpace!");
            }
        }
        return colorSpace;
    }
}
