package android.graphics;

import android.graphics.ColorSpace;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class RuntimeXfermode extends Xfermode {
    private long mBuilderNativeInstance;

    private static native long nativeCreateBlenderBuilder(String str);

    private static native long nativeCreateNativeInstance(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native void nativeUpdateChild(long j, String str, long j2);

    private static native void nativeUpdateColorFilter(long j, String str, long j2);

    private static native void nativeUpdateUniforms(long j, String str, float f, float f2, float f3, float f4, int i);

    private static native void nativeUpdateUniforms(long j, String str, int i, int i2, int i3, int i4, int i5);

    private static native void nativeUpdateUniforms(long j, String str, float[] fArr, boolean z);

    private static native void nativeUpdateUniforms(long j, String str, int[] iArr);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(RuntimeXfermode.class.getClassLoader(), RuntimeXfermode.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public RuntimeXfermode(String str) {
        if (str == null) {
            throw new NullPointerException("RuntimeShader requires a non-null AGSL string");
        }
        this.mBuilderNativeInstance = nativeCreateBlenderBuilder(str);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mBuilderNativeInstance);
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
        nativeUpdateUniforms(this.mBuilderNativeInstance, str, f, f2, f3, f4, i);
    }

    private void setUniform(String str, float[] fArr, boolean z) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        if (fArr == null) {
            throw new NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mBuilderNativeInstance, str, fArr, z);
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
        nativeUpdateUniforms(this.mBuilderNativeInstance, str, iArr);
    }

    private void setIntUniform(String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mBuilderNativeInstance, str, i, i2, i3, i4, i5);
    }

    public void setInputShader(String str, Shader shader) {
        if (str == null) {
            throw new NullPointerException("The shaderName parameter must not be null");
        }
        if (shader == null) {
            throw new NullPointerException("The shader parameter must not be null");
        }
        nativeUpdateChild(this.mBuilderNativeInstance, str, shader.getNativeInstance());
    }

    public void setInputColorFilter(String str, ColorFilter colorFilter) {
        if (str == null) {
            throw new NullPointerException("The filterName parameter must not be null");
        }
        if (colorFilter == null) {
            throw new NullPointerException("The colorFilter parameter must not be null");
        }
        nativeUpdateColorFilter(this.mBuilderNativeInstance, str, colorFilter.getNativeInstance());
    }

    public void setInputXfermode(String str, RuntimeXfermode runtimeXfermode) {
        if (str == null) {
            throw new NullPointerException("The xfermodeName parameter must not be null");
        }
        if (runtimeXfermode == null) {
            throw new NullPointerException("The xfermode parameter must not be null");
        }
        nativeUpdateChild(this.mBuilderNativeInstance, str, runtimeXfermode.createNativeInstance());
    }

    public long createNativeInstance() {
        return nativeCreateNativeInstance(this.mBuilderNativeInstance);
    }
}
