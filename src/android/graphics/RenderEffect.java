package android.graphics;

import android.graphics.Shader;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class RenderEffect {
    private final long mNativeRenderEffect;

    private static native long nativeCreateBitmapEffect(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private static native long nativeCreateBlendModeEffect(long j, long j2, int i);

    private static native long nativeCreateBlurEffect(float f, float f2, long j, int i);

    private static native long nativeCreateChainEffect(long j, long j2);

    private static native long nativeCreateColorFilterEffect(long j, long j2);

    private static native long nativeCreateOffsetEffect(float f, float f2, long j);

    private static native long nativeCreateRuntimeShaderEffect(long j, String str);

    private static native long nativeCreateShaderEffect(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static class RenderEffectHolder {
        public static final NativeAllocationRegistry RENDER_EFFECT_REGISTRY = NativeAllocationRegistry.createMalloced(RenderEffect.class.getClassLoader(), RenderEffect.nativeGetFinalizer());

        private RenderEffectHolder() {
        }
    }

    public static RenderEffect createOffsetEffect(float f, float f2) {
        return new RenderEffect(nativeCreateOffsetEffect(f, f2, 0L));
    }

    public static RenderEffect createOffsetEffect(float f, float f2, RenderEffect renderEffect) {
        return new RenderEffect(nativeCreateOffsetEffect(f, f2, renderEffect.getNativeInstance()));
    }

    public static RenderEffect createBlurEffect(float f, float f2, RenderEffect renderEffect, Shader.TileMode tileMode) {
        return new RenderEffect(nativeCreateBlurEffect(f, f2, renderEffect != null ? renderEffect.mNativeRenderEffect : 0L, tileMode.nativeInt));
    }

    public static RenderEffect createBlurEffect(float f, float f2, Shader.TileMode tileMode) {
        return new RenderEffect(nativeCreateBlurEffect(f, f2, 0L, tileMode.nativeInt));
    }

    public static RenderEffect createBitmapEffect(Bitmap bitmap) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        return new RenderEffect(nativeCreateBitmapEffect(bitmap.getNativeInstance(), 0.0f, 0.0f, width, height, 0.0f, 0.0f, width, height));
    }

    public static RenderEffect createBitmapEffect(Bitmap bitmap, Rect rect, Rect rect2) {
        return new RenderEffect(nativeCreateBitmapEffect(bitmap.getNativeInstance(), rect == null ? 0 : rect.left, rect != null ? rect.top : 0, rect == null ? bitmap.getWidth() : rect.right, rect == null ? bitmap.getHeight() : rect.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom));
    }

    public static RenderEffect createColorFilterEffect(ColorFilter colorFilter, RenderEffect renderEffect) {
        return new RenderEffect(nativeCreateColorFilterEffect(colorFilter.getNativeInstance(), renderEffect.getNativeInstance()));
    }

    public static RenderEffect createColorFilterEffect(ColorFilter colorFilter) {
        return new RenderEffect(nativeCreateColorFilterEffect(colorFilter.getNativeInstance(), 0L));
    }

    public static RenderEffect createBlendModeEffect(RenderEffect renderEffect, RenderEffect renderEffect2, BlendMode blendMode) {
        return new RenderEffect(nativeCreateBlendModeEffect(renderEffect.getNativeInstance(), renderEffect2.getNativeInstance(), blendMode.getXfermode().porterDuffMode));
    }

    public static RenderEffect createChainEffect(RenderEffect renderEffect, RenderEffect renderEffect2) {
        return new RenderEffect(nativeCreateChainEffect(renderEffect.getNativeInstance(), renderEffect2.getNativeInstance()));
    }

    public static RenderEffect createShaderEffect(Shader shader) {
        return new RenderEffect(nativeCreateShaderEffect(shader.getNativeInstance()));
    }

    public static RenderEffect createRuntimeShaderEffect(RuntimeShader runtimeShader, String str) {
        return new RenderEffect(nativeCreateRuntimeShaderEffect(runtimeShader.getNativeShaderBuilder(), str));
    }

    private RenderEffect(long j) {
        this.mNativeRenderEffect = j;
        RenderEffectHolder.RENDER_EFFECT_REGISTRY.registerNativeAllocation(this, j);
    }

    long getNativeInstance() {
        return this.mNativeRenderEffect;
    }
}
