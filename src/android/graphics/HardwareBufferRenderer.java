package android.graphics;

import android.graphics.ColorSpace;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class HardwareBufferRenderer implements AutoCloseable {
    private static final ColorSpace DEFAULT_COLORSPACE = ColorSpace.get(ColorSpace.Named.SRGB);
    private final Runnable mCleaner;
    private final HardwareBuffer mHardwareBuffer;
    private long mProxy;
    private final RenderRequest mRenderRequest;
    private final RenderNode mRootNode;

    private static native long nCreateHardwareBufferRenderer(HardwareBuffer hardwareBuffer, long j);

    private static native long nCreateRootRenderNode();

    private static native void nDestroyRootRenderNode(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    static native int nRender(long j, int i, int i2, int i3, long j2, Consumer<RenderResult> consumer);

    private static native void nSetLightAlpha(long j, float f, float f2);

    private static native void nSetLightGeometry(long j, float f, float f2, float f3, float f4);

    private static class HardwareBufferRendererHolder {
        public static final NativeAllocationRegistry REGISTRY = NativeAllocationRegistry.createMalloced(HardwareBufferRenderer.class.getClassLoader(), HardwareBufferRenderer.nGetFinalizer());

        private HardwareBufferRendererHolder() {
        }
    }

    public HardwareBufferRenderer(HardwareBuffer hardwareBuffer) {
        RenderNode adopt = RenderNode.adopt(nCreateRootRenderNode());
        adopt.setClipToBounds(false);
        this.mProxy = nCreateHardwareBufferRenderer(hardwareBuffer, adopt.mNativeRenderNode);
        this.mCleaner = HardwareBufferRendererHolder.REGISTRY.registerNativeAllocation(this, this.mProxy);
        this.mRenderRequest = new RenderRequest();
        this.mRootNode = adopt;
        this.mHardwareBuffer = hardwareBuffer;
    }

    public void setContentRoot(RenderNode renderNode) {
        RecordingCanvas beginRecording = this.mRootNode.beginRecording();
        if (renderNode != null) {
            beginRecording.drawRenderNode(renderNode);
        }
        this.mRootNode.endRecording();
    }

    public RenderRequest obtainRenderRequest() {
        this.mRenderRequest.reset();
        return this.mRenderRequest;
    }

    public boolean isClosed() {
        return this.mProxy == 0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        nDestroyRootRenderNode(this.mRootNode.mNativeRenderNode);
        if (this.mProxy != 0) {
            this.mCleaner.run();
            this.mProxy = 0L;
        }
    }

    public void setLightSourceGeometry(float f, float f2, float f3, float f4) {
        validateFinite(f, "lightX");
        validateFinite(f2, "lightY");
        validatePositive(f3, "lightZ");
        validatePositive(f4, "lightRadius");
        nSetLightGeometry(this.mProxy, f, f2, f3, f4);
    }

    public void setLightSourceAlpha(float f, float f2) {
        validateAlpha(f, "ambientShadowAlpha");
        validateAlpha(f2, "spotShadowAlpha");
        nSetLightAlpha(this.mProxy, f, f2);
    }

    public static final class RenderResult {
        public static final int ERROR_UNKNOWN = 1;
        public static final int SUCCESS = 0;
        private final SyncFence mFence;
        private final int mResultStatus;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RenderResultStatus {
        }

        private RenderResult(SyncFence syncFence, int i) {
            this.mFence = syncFence;
            this.mResultStatus = i;
        }

        public SyncFence getFence() {
            return this.mFence;
        }

        public int getStatus() {
            return this.mResultStatus;
        }
    }

    public final class RenderRequest {
        private ColorSpace mColorSpace;
        private int mTransform;

        private RenderRequest() {
            this.mColorSpace = HardwareBufferRenderer.DEFAULT_COLORSPACE;
            this.mTransform = 0;
        }

        public void draw(final Executor executor, final Consumer<RenderResult> consumer) {
            int height;
            int width;
            Consumer consumer2 = new Consumer() { // from class: android.graphics.HardwareBufferRenderer$RenderRequest$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    executor.execute(new Runnable() { // from class: android.graphics.HardwareBufferRenderer$RenderRequest$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(r2);
                        }
                    });
                }
            };
            if (!HardwareBufferRenderer.this.isClosed()) {
                int i = this.mTransform;
                if (i == 4 || i == 7) {
                    height = HardwareBufferRenderer.this.mHardwareBuffer.getHeight();
                    width = HardwareBufferRenderer.this.mHardwareBuffer.getWidth();
                } else {
                    height = HardwareBufferRenderer.this.mHardwareBuffer.getWidth();
                    width = HardwareBufferRenderer.this.mHardwareBuffer.getHeight();
                }
                HardwareBufferRenderer.nRender(HardwareBufferRenderer.this.mProxy, this.mTransform, height, width, this.mColorSpace.getNativeInstance(), consumer2);
                return;
            }
            throw new IllegalStateException("Attempt to draw with a HardwareBufferRenderer instance that has already been closed");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mColorSpace = HardwareBufferRenderer.DEFAULT_COLORSPACE;
            this.mTransform = 0;
        }

        public RenderRequest setColorSpace(ColorSpace colorSpace) {
            if (colorSpace == null) {
                this.mColorSpace = HardwareBufferRenderer.DEFAULT_COLORSPACE;
                return this;
            }
            this.mColorSpace = colorSpace;
            return this;
        }

        public RenderRequest setBufferTransform(int i) {
            if (i == 0 || i == 4 || i == 3 || i == 7) {
                this.mTransform = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid transform provided, must be one ofthe SurfaceControl.BufferTransform values");
        }
    }

    private static void invokeRenderCallback(Consumer<RenderResult> consumer, int i, int i2) {
        consumer.accept(new RenderResult(SyncFence.adopt(i), i2));
    }

    private static void validateAlpha(float f, String str) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException(str + " must be a valid alpha, " + f + " is not in the range of 0.0f to 1.0f");
        }
    }

    private static void validateFinite(float f, String str) {
        if (Float.isFinite(f)) {
            return;
        }
        throw new IllegalArgumentException(str + " must be finite, given=" + f);
    }

    private static void validatePositive(float f, String str) {
        if (!Float.isFinite(f) || f < 0.0f) {
            throw new IllegalArgumentException(str + " must be a finite positive, given=" + f);
        }
    }
}
