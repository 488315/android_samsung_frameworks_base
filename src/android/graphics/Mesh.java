package android.graphics;

import android.graphics.ColorSpace;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.Buffer;
import java.nio.ShortBuffer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Mesh {
    public static final int TRIANGLES = 0;
    public static final int TRIANGLE_STRIP = 1;
    private boolean mIsIndexed;
    private long mNativeMeshWrapper;

    @Retention(RetentionPolicy.SOURCE)
    private @interface Mode {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native long nativeMake(long j, int i, Buffer buffer, boolean z, int i2, int i3, float f, float f2, float f3, float f4);

    private static native long nativeMakeIndexed(long j, int i, Buffer buffer, boolean z, int i2, int i3, ShortBuffer shortBuffer, boolean z2, int i4, int i5, float f, float f2, float f3, float f4);

    private static native void nativeUpdateUniforms(long j, String str, float f, float f2, float f3, float f4, int i);

    private static native void nativeUpdateUniforms(long j, String str, int i, int i2, int i3, int i4, int i5);

    private static native void nativeUpdateUniforms(long j, String str, float[] fArr, boolean z);

    private static native void nativeUpdateUniforms(long j, String str, int[] iArr);

    private static class MeshHolder {
        public static final NativeAllocationRegistry MESH_SPECIFICATION_REGISTRY = NativeAllocationRegistry.createMalloced(MeshSpecification.class.getClassLoader(), Mesh.nativeGetFinalizer());

        private MeshHolder() {
        }
    }

    public Mesh(MeshSpecification meshSpecification, int i, Buffer buffer, int i2, RectF rectF) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid value passed in for mode parameter");
        }
        long nativeMake = nativeMake(meshSpecification.mNativeMeshSpec, i, buffer, buffer.isDirect(), i2, buffer.position(), rectF.left, rectF.top, rectF.right, rectF.bottom);
        if (nativeMake == 0) {
            throw new IllegalArgumentException("Mesh construction failed.");
        }
        meshSetup(nativeMake, false);
    }

    public Mesh(MeshSpecification meshSpecification, int i, Buffer buffer, int i2, ShortBuffer shortBuffer, RectF rectF) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid value passed in for mode parameter");
        }
        long nativeMakeIndexed = nativeMakeIndexed(meshSpecification.mNativeMeshSpec, i, buffer, buffer.isDirect(), i2, buffer.position(), shortBuffer, shortBuffer.isDirect(), shortBuffer.capacity(), shortBuffer.position(), rectF.left, rectF.top, rectF.right, rectF.bottom);
        if (nativeMakeIndexed == 0) {
            throw new IllegalArgumentException("Mesh construction failed.");
        }
        meshSetup(nativeMakeIndexed, true);
    }

    public void setColorUniform(String str, int i) {
        setUniform(str, Color.valueOf(i).getComponents(), true);
    }

    public void setColorUniform(String str, long j) {
        setUniform(str, Color.valueOf(j).convert(ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB)).getComponents(), true);
    }

    public void setColorUniform(String str, Color color) {
        if (color == null) {
            throw new NullPointerException("The color parameter must not be null");
        }
        setUniform(str, color.convert(ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB)).getComponents(), true);
    }

    public void setFloatUniform(String str, float f) {
        setFloatUniform(str, f, 0.0f, 0.0f, 0.0f, 1);
    }

    public void setFloatUniform(String str, float f, float f2) {
        setFloatUniform(str, f, f2, 0.0f, 0.0f, 2);
    }

    public void setFloatUniform(String str, float f, float f2, float f3) {
        setFloatUniform(str, f, f2, f3, 0.0f, 3);
    }

    public void setFloatUniform(String str, float f, float f2, float f3, float f4) {
        setFloatUniform(str, f, f2, f3, f4, 4);
    }

    public void setFloatUniform(String str, float[] fArr) {
        setUniform(str, fArr, false);
    }

    private void setFloatUniform(String str, float f, float f2, float f3, float f4, int i) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, f, f2, f3, f4, i);
    }

    private void setUniform(String str, float[] fArr, boolean z) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        if (fArr == null) {
            throw new NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, fArr, z);
    }

    public void setIntUniform(String str, int i) {
        setIntUniform(str, i, 0, 0, 0, 1);
    }

    public void setIntUniform(String str, int i, int i2) {
        setIntUniform(str, i, i2, 0, 0, 2);
    }

    public void setIntUniform(String str, int i, int i2, int i3) {
        setIntUniform(str, i, i2, i3, 0, 3);
    }

    public void setIntUniform(String str, int i, int i2, int i3, int i4) {
        setIntUniform(str, i, i2, i3, i4, 4);
    }

    public void setIntUniform(String str, int[] iArr) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        if (iArr == null) {
            throw new NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, iArr);
    }

    long getNativeWrapperInstance() {
        return this.mNativeMeshWrapper;
    }

    private void setIntUniform(String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, i, i2, i3, i4, i5);
    }

    private void meshSetup(long j, boolean z) {
        this.mNativeMeshWrapper = j;
        this.mIsIndexed = z;
        MeshHolder.MESH_SPECIFICATION_REGISTRY.registerNativeAllocation(this, this.mNativeMeshWrapper);
    }
}
