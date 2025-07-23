package android.graphics;

import android.graphics.ColorSpace;
import android.util.ArrayMap;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class RuntimeShader extends Shader {
    private ArrayMap<String, ColorFilter> mColorFilterUniforms;
    private long mNativeInstanceRuntimeShaderBuilder;
    private ArrayMap<String, Shader> mShaderUniforms;
    private ColorSpace mWorkingColorSpace;
    private ArrayMap<String, RuntimeXfermode> mXfermodeUniforms;

    private static native long nativeCreateBuilder(String str);

    private static native long nativeCreateShader(long j, long j2);

    private static native long nativeCreateShader(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native void nativeUpdateChild(long j, String str, long j2);

    private static native void nativeUpdateColorFilter(long j, String str, long j2);

    private static native void nativeUpdateShader(long j, String str, long j2);

    private static native void nativeUpdateUniforms(long j, String str, float f, float f2, float f3, float f4, int i);

    private static native void nativeUpdateUniforms(long j, String str, int i, int i2, int i3, int i4, int i5);

    private static native void nativeUpdateUniforms(long j, String str, float[] fArr, boolean z);

    private static native void nativeUpdateUniforms(long j, String str, int[] iArr);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(RuntimeShader.class.getClassLoader(), RuntimeShader.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public RuntimeShader(String str) {
        super(ColorSpace.get(ColorSpace.Named.SRGB));
        this.mShaderUniforms = new ArrayMap<>();
        this.mColorFilterUniforms = new ArrayMap<>();
        this.mXfermodeUniforms = new ArrayMap<>();
        this.mWorkingColorSpace = null;
        if (str == null) {
            throw new NullPointerException("RuntimeShader requires a non-null AGSL string");
        }
        this.mNativeInstanceRuntimeShaderBuilder = nativeCreateBuilder(str);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstanceRuntimeShaderBuilder);
    }

    public void setWorkingColorSpace(ColorSpace colorSpace) {
        if (colorSpace != null && colorSpace.getModel() != ColorSpace.Model.RGB) {
            throw new IllegalArgumentException("ColorSpace must be RGB, given " + colorSpace);
        }
        if (this.mWorkingColorSpace != colorSpace) {
            this.mWorkingColorSpace = colorSpace;
            if (colorSpace != null) {
                colorSpace.getNativeInstance();
            }
            discardNativeInstance();
        }
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
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, f, f2, f3, f4, i);
        discardNativeInstance();
    }

    private void setUniform(String str, float[] fArr, boolean z) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        if (fArr == null) {
            throw new NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, fArr, z);
        discardNativeInstance();
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
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, iArr);
        discardNativeInstance();
    }

    private void setIntUniform(String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, i, i2, i3, i4, i5);
        discardNativeInstance();
    }

    public void setInputShader(String str, Shader shader) {
        if (str == null) {
            throw new NullPointerException("The shaderName parameter must not be null");
        }
        if (shader == null) {
            throw new NullPointerException("The shader parameter must not be null");
        }
        this.mShaderUniforms.put(str, shader);
        nativeUpdateShader(this.mNativeInstanceRuntimeShaderBuilder, str, shader.getNativeInstance());
        discardNativeInstance();
    }

    public void setInputBuffer(String str, BitmapShader bitmapShader) {
        if (str == null) {
            throw new NullPointerException("The shaderName parameter must not be null");
        }
        if (bitmapShader == null) {
            throw new NullPointerException("The shader parameter must not be null");
        }
        this.mShaderUniforms.put(str, bitmapShader);
        nativeUpdateShader(this.mNativeInstanceRuntimeShaderBuilder, str, bitmapShader.getNativeInstanceWithDirectSampling());
        discardNativeInstance();
    }

    public void setInputColorFilter(String str, ColorFilter colorFilter) {
        if (str == null) {
            throw new NullPointerException("The filterName parameter must not be null");
        }
        if (colorFilter == null) {
            throw new NullPointerException("The colorFilter parameter must not be null");
        }
        this.mColorFilterUniforms.put(str, colorFilter);
        nativeUpdateColorFilter(this.mNativeInstanceRuntimeShaderBuilder, str, colorFilter.getNativeInstance());
        discardNativeInstance();
    }

    public void setInputXfermode(String str, RuntimeXfermode runtimeXfermode) {
        if (str == null) {
            throw new NullPointerException("The xfermodeName parameter must not be null");
        }
        if (runtimeXfermode == null) {
            throw new NullPointerException("The xfermode parameter must not be null");
        }
        this.mXfermodeUniforms.put(str, runtimeXfermode);
        nativeUpdateChild(this.mNativeInstanceRuntimeShaderBuilder, str, runtimeXfermode.createNativeInstance());
        discardNativeInstance();
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        long j2 = this.mNativeInstanceRuntimeShaderBuilder;
        ColorSpace colorSpace = this.mWorkingColorSpace;
        return nativeCreateShader(j2, j, colorSpace != null ? colorSpace.getNativeInstance() : 0L);
    }

    protected long getNativeShaderBuilder() {
        return this.mNativeInstanceRuntimeShaderBuilder;
    }
}
