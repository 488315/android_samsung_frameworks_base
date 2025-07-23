package android.graphics;

import android.graphics.PorterDuff;

/* loaded from: classes.dex */
public class ComposeShader extends Shader {
    private long mNativeInstanceShaderA;
    private long mNativeInstanceShaderB;
    private int mPorterDuffMode;
    Shader mShaderA;
    Shader mShaderB;

    private static native long nativeCreate(long j, long j2, long j3, int i);

    @Deprecated
    public ComposeShader(Shader shader, Shader shader2, Xfermode xfermode) {
        this(shader, shader2, xfermode instanceof PorterDuffXfermode ? ((PorterDuffXfermode) xfermode).porterDuffMode : BlendMode.SRC_OVER.getXfermode().porterDuffMode);
    }

    public ComposeShader(Shader shader, Shader shader2, PorterDuff.Mode mode) {
        this(shader, shader2, mode.nativeInt);
    }

    public ComposeShader(Shader shader, Shader shader2, BlendMode blendMode) {
        this(shader, shader2, blendMode.getXfermode().porterDuffMode);
    }

    private ComposeShader(Shader shader, Shader shader2, int i) {
        if (shader == null || shader2 == null) {
            throw new IllegalArgumentException("Shader parameters must not be null");
        }
        this.mShaderA = shader;
        this.mShaderB = shader2;
        this.mPorterDuffMode = i;
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        this.mNativeInstanceShaderA = this.mShaderA.getNativeInstance(z);
        this.mNativeInstanceShaderB = this.mShaderB.getNativeInstance(z);
        return nativeCreate(j, this.mShaderA.getNativeInstance(), this.mShaderB.getNativeInstance(), this.mPorterDuffMode);
    }

    @Override // android.graphics.Shader
    protected boolean shouldDiscardNativeInstance(boolean z) {
        return (this.mShaderA.getNativeInstance(z) == this.mNativeInstanceShaderA && this.mShaderB.getNativeInstance(z) == this.mNativeInstanceShaderB) ? false : true;
    }
}
