package android.graphics;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.DataSpace;
import android.hardware.OverlayProperties;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Display;
import android.view.IGraphicsStats;
import android.view.IGraphicsStatsCallback;
import android.view.NativeVectorDrawableAnimator;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.animation.AnimationUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import sun.misc.Cleaner;

/* loaded from: classes.dex */
public class HardwareRenderer {
    public static final int CACHE_LIMIT_DEFAULT = 0;
    public static final int CACHE_LIMIT_HIGHER_BIG = 3;
    public static final int CACHE_LIMIT_HIGHER_HUGE = 4;
    public static final int CACHE_LIMIT_HIGHER_MID = 2;
    public static final int CACHE_LIMIT_HIGHER_SMALL = 1;
    private static final String CACHE_PATH_SHADERS = "com.android.opengl.shaders_cache";
    private static final String CACHE_PATH_SKIASHADERS = "com.android.skia.shaders_cache";
    public static final int CACHE_TRIM_ALL = 0;
    public static final int CACHE_TRIM_FONT = 1;
    public static final int CACHE_TRIM_RESOURCES = 2;
    public static final int FLAG_DUMP_ALL = 1;
    public static final int FLAG_DUMP_FRAMESTATS = 1;
    public static final int FLAG_DUMP_RESET = 2;
    private static final String LOG_TAG = "HardwareRenderer";
    public static final int SYNC_CONTEXT_IS_STOPPED = 4;
    public static final int SYNC_FRAME_DROPPED = 8;
    public static final int SYNC_LOST_SURFACE_REWARD_IF_FOUND = 2;
    public static final int SYNC_OK = 0;
    public static final int SYNC_REDRAW_REQUESTED = 1;
    private static final boolean USE_LOGICAL_SCREEN_RESOLUTION;
    private static int sDensityDpi;
    private final long mNativeProxy;
    protected RenderNode mRootNode;
    private boolean mOpaque = true;
    private int mForceDark = 0;
    private int mColorMode = 0;
    private float mDesiredSdrHdrRatio = 1.0f;
    private final boolean ENABLE_STB_ANIMATION = SystemProperties.getBoolean("debug.stb.animation", true);
    private FrameRenderRequest mRenderRequest = new FrameRenderRequest();

    public interface ASurfaceTransactionCallback {
        boolean onMergeTransaction(long j, long j2, long j3);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheLimitLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheTrimLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DumpFlags {
    }

    public interface FrameCommitCallback {
        void onFrameCommit(boolean z);
    }

    public interface FrameCompleteCallback {
        void onFrameComplete();
    }

    public interface PictureCapturedCallback {
        void onPictureCaptured(Picture picture);
    }

    public interface PrepareSurfaceControlForWebviewCallback {
        void prepare();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SyncAndDrawResult {
    }

    public static native void disableVsync();

    /* JADX INFO: Access modifiers changed from: protected */
    public static native boolean isWebViewOverlaysEnabled();

    private static native void nAddObserver(long j, long j2);

    private static native void nAddRenderNode(long j, long j2, boolean z);

    private static native void nAllocateBuffers(long j);

    private static native void nBuildLayer(long j, long j2);

    private static native void nCancelLayerUpdate(long j, long j2);

    private static native boolean nCopyLayerInto(long j, long j2, long j3);

    private static native void nCopySurfaceInto(Surface surface, int i, int i2, int i3, int i4, CopyRequest copyRequest);

    private static native Bitmap nCreateHardwareBitmap(long j, int i, int i2);

    private static native long nCreateProxy(boolean z, long j);

    private static native long nCreateRootRenderNode();

    private static native long nCreateTextureLayer(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nDeleteProxy(long j);

    private static native void nDestroy(long j, long j2);

    private static native void nDestroyHardwareResources(long j);

    private static native void nDetachSurfaceTexture(long j, long j2);

    private static native void nDrawRenderNode(long j, long j2);

    private static native void nDumpGlobalProfileInfo(FileDescriptor fileDescriptor, int i);

    private static native void nDumpProfileInfo(long j, FileDescriptor fileDescriptor, int i);

    private static native void nFence(long j);

    private static native void nForceDrawNextFrame(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nGetRenderThreadTid(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nInitDisplayInfo(int i, int i2, float f, int i3, long j, long j2, boolean z, boolean z2, boolean z3);

    private static native boolean nIsDrawingEnabled();

    private static native boolean nIsHighContrastTextEnabled();

    private static native boolean nLoadSystemProperties(long j);

    private static native void nNotifyCallbackPending(long j);

    private static native void nNotifyExpensiveFrame(long j);

    private static native void nNotifyFramePending(long j);

    private static native void nOverrideProperty(String str, String str2);

    private static native boolean nPause(long j);

    private static native void nPushLayerUpdate(long j, long j2);

    private static native void nRegisterAnimatingRenderNode(long j, long j2);

    private static native void nRegisterVectorDrawableAnimator(long j, long j2);

    private static native void nRemoveObserver(long j, long j2);

    private static native void nRemoveRenderNode(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nRotateProcessStatsBuffer();

    private static native long nSemGetCurrentResourceCacheMax();

    private static native long nSemGetCurrentResourceCacheUsage();

    private static native int nSemGetResourceCacheLimit();

    private static native boolean nSemSetResourceCacheLimit(int i);

    private static native void nSetASurfaceTransactionCallback(long j, ASurfaceTransactionCallback aSurfaceTransactionCallback);

    private static native float nSetColorMode(long j, int i);

    private static native void nSetContentDrawBounds(long j, int i, int i2, int i3, int i4);

    private static native void nSetContextPriority(int i);

    private static native void nSetDebuggingEnabled(boolean z);

    private static native void nSetDisplayDensityDpi(int i);

    private static native void nSetDrawingEnabled(boolean z);

    private static native void nSetForceDark(long j, int i);

    private static native void nSetFrameCallback(long j, FrameDrawingCallback frameDrawingCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetFrameCommitCallback(long j, FrameCommitCallback frameCommitCallback);

    private static native void nSetFrameCompleteCallback(long j, FrameCompleteCallback frameCompleteCallback);

    private static native void nSetHighContrastText(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetIsHighEndGfx(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetIsLowRam(boolean z);

    private static native void nSetIsSystemOrPersistent(boolean z);

    private static native void nSetIsolatedProcess(boolean z);

    private static native void nSetLightAlpha(long j, float f, float f2);

    private static native void nSetLightGeometry(long j, float f, float f2, float f3, float f4);

    private static native void nSetName(long j, String str);

    private static native void nSetNightDimText(int i);

    private static native void nSetOpaque(long j, boolean z);

    private static native void nSetPictureCaptureCallback(long j, PictureCapturedCallback pictureCapturedCallback);

    private static native void nSetPrepareSurfaceControlForWebviewCallback(long j, PrepareSurfaceControlForWebviewCallback prepareSurfaceControlForWebviewCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetProcessStatsBuffer(int i);

    private static native void nSetRendererAsGl(boolean z);

    private static native void nSetRtAnimationsEnabled(boolean z);

    private static native void nSetSdrWhitePoint(long j, float f);

    private static native void nSetStopped(long j, boolean z);

    private static native void nSetSurface(long j, Surface surface, boolean z);

    private static native void nSetSurfaceControl(long j, long j2);

    private static native void nSetTargetSdrHdrRatio(long j, float f);

    private static native void nStopDrawing(long j);

    private static native int nSyncAndDrawFrame(long j, long[] jArr, int i);

    private static native void nTrimCaches(int i);

    private static native void nTrimMemory(int i);

    public static native void preInitBufferAllocator();

    public static native void preload();

    protected static native void setupShadersDiskCache(String str, String str2);

    static {
        USE_LOGICAL_SCREEN_RESOLUTION = SystemProperties.getInt("ro.hwui.logical_resolution_surface_area", 1) != 0;
        sDensityDpi = 0;
    }

    public HardwareRenderer() {
        ProcessInitializer.sInstance.initUsingContext();
        RenderNode renderNodeAdopt = RenderNode.adopt(nCreateRootRenderNode());
        this.mRootNode = renderNodeAdopt;
        renderNodeAdopt.setClipToBounds(false);
        long jNCreateProxy = nCreateProxy(true ^ this.mOpaque, this.mRootNode.mNativeRenderNode);
        this.mNativeProxy = jNCreateProxy;
        if (jNCreateProxy == 0) {
            throw new OutOfMemoryError("Unable to create hardware renderer");
        }
        Cleaner.create(this, new DestroyContextRunnable(jNCreateProxy));
        ProcessInitializer.sInstance.init(jNCreateProxy);
    }

    public void destroy() {
        nDestroy(this.mNativeProxy, this.mRootNode.mNativeRenderNode);
    }

    public void setName(String str) {
        nSetName(this.mNativeProxy, str);
    }

    public void setLightSourceGeometry(float f, float f2, float f3, float f4) {
        validateFinite(f, "lightX");
        validateFinite(f2, "lightY");
        validatePositive(f3, "lightZ");
        validatePositive(f4, "lightRadius");
        nSetLightGeometry(this.mNativeProxy, f, f2, f3, f4);
    }

    public void setLightSourceAlpha(float f, float f2) {
        validateAlpha(f, "ambientShadowAlpha");
        validateAlpha(f2, "spotShadowAlpha");
        nSetLightAlpha(this.mNativeProxy, f, f2);
    }

    public void setContentRoot(RenderNode renderNode) {
        RecordingCanvas recordingCanvasBeginRecording = this.mRootNode.beginRecording();
        if (renderNode != null) {
            recordingCanvasBeginRecording.drawRenderNode(renderNode);
        }
        this.mRootNode.endRecording();
    }

    public void setSurface(Surface surface) {
        setSurface(surface, false);
    }

    public void setSurface(Surface surface, boolean z) {
        if (surface != null && !surface.isValid()) {
            throw new IllegalArgumentException("Surface is invalid. surface.isValid() == false.");
        }
        nSetSurface(this.mNativeProxy, surface, z);
    }

    public void setSurfaceControl(SurfaceControl surfaceControl, BLASTBufferQueue bLASTBufferQueue) {
        nSetSurfaceControl(this.mNativeProxy, surfaceControl != null ? surfaceControl.mNativeObject : 0L);
    }

    public final class FrameRenderRequest {
        private FrameInfo mFrameInfo;
        private boolean mWaitForPresent;

        private FrameRenderRequest() {
            this.mFrameInfo = new FrameInfo();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mWaitForPresent = false;
            HardwareRenderer.this.mRenderRequest.setVsyncTime(AnimationUtils.currentAnimationTimeMillis() * 1000000);
        }

        public void setFrameInfo(FrameInfo frameInfo) {
            System.arraycopy(frameInfo.frameInfo, 0, this.mFrameInfo.frameInfo, 0, frameInfo.frameInfo.length);
        }

        public FrameRenderRequest setVsyncTime(long j) {
            this.mFrameInfo.setVsync(j, j, -1L, Long.MAX_VALUE, j, -1L);
            this.mFrameInfo.addFlags(4L);
            return this;
        }

        public FrameRenderRequest setFrameCommitCallback(final Executor executor, final Runnable runnable) {
            HardwareRenderer.nSetFrameCommitCallback(HardwareRenderer.this.mNativeProxy, new FrameCommitCallback() { // from class: android.graphics.HardwareRenderer$FrameRenderRequest$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    executor.execute(runnable);
                }
            });
            return this;
        }

        public FrameRenderRequest setWaitForPresent(boolean z) {
            this.mWaitForPresent = z;
            return this;
        }

        public int syncAndDraw() {
            int iSyncAndDrawFrame = HardwareRenderer.this.syncAndDrawFrame(this.mFrameInfo);
            if (this.mWaitForPresent && (iSyncAndDrawFrame & 8) == 0) {
                HardwareRenderer.this.fence();
            }
            return iSyncAndDrawFrame;
        }
    }

    public FrameRenderRequest createRenderRequest() {
        this.mRenderRequest.reset();
        return this.mRenderRequest;
    }

    public int syncAndDrawFrame(FrameInfo frameInfo) {
        return nSyncAndDrawFrame(this.mNativeProxy, frameInfo.frameInfo, frameInfo.frameInfo.length);
    }

    public boolean pause() {
        return nPause(this.mNativeProxy);
    }

    public void setStopped(boolean z) {
        nSetStopped(this.mNativeProxy, z);
    }

    public void stop() {
        nSetStopped(this.mNativeProxy, true);
    }

    public void start() {
        nSetStopped(this.mNativeProxy, false);
    }

    public void clearContent() {
        nDestroyHardwareResources(this.mNativeProxy);
    }

    public boolean setForceDark(int i) {
        if (this.mForceDark == i) {
            return false;
        }
        this.mForceDark = i;
        nSetForceDark(this.mNativeProxy, i);
        return true;
    }

    public void allocateBuffers() {
        nAllocateBuffers(this.mNativeProxy);
    }

    public void notifyFramePending() {
        nNotifyFramePending(this.mNativeProxy);
    }

    public void setOpaque(boolean z) {
        if (this.mOpaque != z) {
            this.mOpaque = z;
            nSetOpaque(this.mNativeProxy, z);
        }
    }

    public boolean isOpaque() {
        return this.mOpaque;
    }

    public void setFrameCommitCallback(FrameCommitCallback frameCommitCallback) {
        nSetFrameCommitCallback(this.mNativeProxy, frameCommitCallback);
    }

    public void setFrameCompleteCallback(FrameCompleteCallback frameCompleteCallback) {
        nSetFrameCompleteCallback(this.mNativeProxy, frameCompleteCallback);
    }

    public void addObserver(HardwareRendererObserver hardwareRendererObserver) {
        nAddObserver(this.mNativeProxy, hardwareRendererObserver.getNativeInstance());
    }

    public void addObserver(long j) {
        nAddObserver(this.mNativeProxy, j);
    }

    public void removeObserver(HardwareRendererObserver hardwareRendererObserver) {
        nRemoveObserver(this.mNativeProxy, hardwareRendererObserver.getNativeInstance());
    }

    public void removeObserver(long j) {
        nRemoveObserver(this.mNativeProxy, j);
    }

    public float setColorMode(int i) {
        if (this.mColorMode != i) {
            this.mColorMode = i;
            this.mDesiredSdrHdrRatio = nSetColorMode(this.mNativeProxy, i);
        }
        return this.mDesiredSdrHdrRatio;
    }

    public void setColorMode(int i, float f) {
        nSetSdrWhitePoint(this.mNativeProxy, f);
        this.mColorMode = i;
        nSetColorMode(this.mNativeProxy, i);
    }

    public void setTargetHdrSdrRatio(float f) {
        if (f < 1.0f || !Float.isFinite(f)) {
            f = 1.0f;
        }
        nSetTargetSdrHdrRatio(this.mNativeProxy, f);
    }

    public void fence() {
        nFence(this.mNativeProxy);
    }

    public void registerAnimatingRenderNode(RenderNode renderNode) {
        nRegisterAnimatingRenderNode(this.mRootNode.mNativeRenderNode, renderNode.mNativeRenderNode);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        nRegisterVectorDrawableAnimator(this.mRootNode.mNativeRenderNode, nativeVectorDrawableAnimator.getAnimatorNativePtr());
    }

    public void stopDrawing() {
        nStopDrawing(this.mNativeProxy);
    }

    public TextureLayer createTextureLayer() {
        return TextureLayer.adoptTextureLayer(this, nCreateTextureLayer(this.mNativeProxy));
    }

    public void detachSurfaceTexture(long j) {
        nDetachSurfaceTexture(this.mNativeProxy, j);
    }

    public void buildLayer(RenderNode renderNode) {
        if (renderNode.hasDisplayList()) {
            nBuildLayer(this.mNativeProxy, renderNode.mNativeRenderNode);
        }
    }

    public boolean copyLayerInto(TextureLayer textureLayer, Bitmap bitmap) {
        return nCopyLayerInto(this.mNativeProxy, textureLayer.getDeferredLayerUpdater(), bitmap.getNativeInstance());
    }

    public void pushLayerUpdate(TextureLayer textureLayer) {
        nPushLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    public void onLayerDestroyed(TextureLayer textureLayer) {
        nCancelLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    protected void setASurfaceTransactionCallback(ASurfaceTransactionCallback aSurfaceTransactionCallback) {
        nSetASurfaceTransactionCallback(this.mNativeProxy, aSurfaceTransactionCallback);
    }

    protected void setPrepareSurfaceControlForWebviewCallback(PrepareSurfaceControlForWebviewCallback prepareSurfaceControlForWebviewCallback) {
        nSetPrepareSurfaceControlForWebviewCallback(this.mNativeProxy, prepareSurfaceControlForWebviewCallback);
    }

    public void setFrameCallback(FrameDrawingCallback frameDrawingCallback) {
        nSetFrameCallback(this.mNativeProxy, frameDrawingCallback);
    }

    public void addRenderNode(RenderNode renderNode, boolean z) {
        nAddRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode, z);
    }

    public void removeRenderNode(RenderNode renderNode) {
        nRemoveRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public void drawRenderNode(RenderNode renderNode) {
        nDrawRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public boolean loadSystemProperties() {
        return nLoadSystemProperties(this.mNativeProxy);
    }

    public static void dumpGlobalProfileInfo(FileDescriptor fileDescriptor, int i) {
        nDumpGlobalProfileInfo(fileDescriptor, i);
    }

    public void dumpProfileInfo(FileDescriptor fileDescriptor, int i) {
        nDumpProfileInfo(this.mNativeProxy, fileDescriptor, i);
    }

    public void setContentDrawBounds(int i, int i2, int i3, int i4) {
        nSetContentDrawBounds(this.mNativeProxy, i, i2, i3, i4);
    }

    public void forceDrawNextFrame() {
        nForceDrawNextFrame(this.mNativeProxy);
    }

    public void setPictureCaptureCallback(PictureCapturedCallback pictureCapturedCallback) {
        nSetPictureCaptureCallback(this.mNativeProxy, pictureCapturedCallback);
    }

    static void invokePictureCapturedCallback(long j, PictureCapturedCallback pictureCapturedCallback) {
        pictureCapturedCallback.onPictureCaptured(new Picture(j));
    }

    public interface FrameDrawingCallback {
        void onFrameDraw(long j);

        default FrameCommitCallback onFrameDraw(int i, long j) {
            onFrameDraw(j);
            return null;
        }
    }

    private static void validateAlpha(float f, String str) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException(str + " must be a valid alpha, " + f + " is not in the range of 0.0f to 1.0f");
        }
    }

    private static void validatePositive(float f, String str) {
        if (!Float.isFinite(f) || f < 0.0f) {
            throw new IllegalArgumentException(str + " must be a finite positive, given=" + f);
        }
    }

    private static void validateFinite(float f, String str) {
        if (Float.isFinite(f)) {
            return;
        }
        throw new IllegalArgumentException(str + " must be finite, given=" + f);
    }

    public void notifyCallbackPending() {
        nNotifyCallbackPending(this.mNativeProxy);
    }

    public void notifyExpensiveFrame() {
        nNotifyExpensiveFrame(this.mNativeProxy);
    }

    public static void setFPSDivisor(int i) {
        nSetRtAnimationsEnabled(i <= 1);
    }

    public static void setContextPriority(int i) {
        nSetContextPriority(i);
    }

    public static void setHighContrastText(boolean z) {
        nSetHighContrastText(z);
    }

    public static boolean isHighContrastTextEnabled() {
        return nIsHighContrastTextEnabled();
    }

    public static void setNightDimText(int i) {
        nSetNightDimText(i);
    }

    public static void setIsolatedProcess(boolean z) {
        nSetIsolatedProcess(z);
        ProcessInitializer.sInstance.setIsolated(z);
    }

    public static void sendDeviceConfigurationForDebugging(Configuration configuration) {
        if (configuration.densityDpi == 0 || configuration.densityDpi == sDensityDpi) {
            return;
        }
        sDensityDpi = configuration.densityDpi;
        nSetDisplayDensityDpi(configuration.densityDpi);
    }

    public static void setDebuggingEnabled(boolean z) {
        nSetDebuggingEnabled(z);
    }

    public static abstract class CopyRequest {
        protected Bitmap mDestinationBitmap;
        final Rect mSrcRect;

        public abstract void onCopyFinished(int i);

        protected CopyRequest(Rect rect, Bitmap bitmap) {
            this.mDestinationBitmap = bitmap;
            if (rect != null) {
                this.mSrcRect = rect;
            } else {
                this.mSrcRect = new Rect();
            }
        }

        public long getDestinationBitmap(int i, int i2) {
            if (this.mDestinationBitmap == null) {
                this.mDestinationBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            }
            return this.mDestinationBitmap.getNativeInstance();
        }
    }

    public static void copySurfaceInto(Surface surface, CopyRequest copyRequest) {
        Rect rect = copyRequest.mSrcRect;
        nCopySurfaceInto(surface, rect.left, rect.top, rect.right, rect.bottom, copyRequest);
    }

    public static Bitmap createHardwareBitmap(RenderNode renderNode, int i, int i2) {
        return nCreateHardwareBitmap(renderNode.mNativeRenderNode, i, i2);
    }

    public static void trimMemory(int i) {
        nTrimMemory(i);
    }

    public static boolean semSetResourceCacheLimit(int i) {
        return nSemSetResourceCacheLimit(i);
    }

    public static int semGetResourceCacheLimit() {
        return nSemGetResourceCacheLimit();
    }

    public static long semGetCurrentResourceCacheUsage() {
        return nSemGetCurrentResourceCacheUsage();
    }

    public static long semGetCurrentResourceCacheMax() {
        return nSemGetCurrentResourceCacheMax();
    }

    public static void trimCaches(int i) {
        nTrimCaches(i);
    }

    public static void overrideProperty(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("name and value must be non-null");
        }
        nOverrideProperty(str, str2);
    }

    public static void setupDiskCache(File file) {
        setupShadersDiskCache(new File(file, CACHE_PATH_SHADERS).getAbsolutePath(), new File(file, CACHE_PATH_SKIASHADERS).getAbsolutePath());
    }

    public static void setPackageName(String str) {
        ProcessInitializer.sInstance.setPackageName(str);
    }

    public static void setContextForInit(Context context) {
        ProcessInitializer.sInstance.setContext(context);
    }

    public static void setIsSystemOrPersistent() {
        nSetIsSystemOrPersistent(true);
    }

    public static void setRendererAsGl(boolean z) {
        nSetRendererAsGl(z);
    }

    public static boolean isDrawingEnabled() {
        return nIsDrawingEnabled();
    }

    public static void setDrawingEnabled(boolean z) {
        nSetDrawingEnabled(z);
    }

    public static void setRtAnimationsEnabled(boolean z) {
        nSetRtAnimationsEnabled(z);
    }

    private static final class DestroyContextRunnable implements Runnable {
        private final long mNativeInstance;

        DestroyContextRunnable(long j) {
            this.mNativeInstance = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            HardwareRenderer.nDeleteProxy(this.mNativeInstance);
        }
    }

    private static class ProcessInitializer {
        static ProcessInitializer sInstance = new ProcessInitializer();
        private Context mContext;
        private IGraphicsStats mGraphicsStatsService;
        private String mPackageName;
        private boolean mInitialized = false;
        private boolean mDisplayInitialized = false;
        private boolean mIsolated = false;
        private IGraphicsStatsCallback mGraphicsStatsCallback = new IGraphicsStatsCallback.Stub() { // from class: android.graphics.HardwareRenderer.ProcessInitializer.1
            @Override // android.view.IGraphicsStatsCallback
            public void onRotateGraphicsStatsBuffer() throws RemoteException {
                ProcessInitializer.this.rotateBuffer();
            }
        };
        private int mLargestWidth = 0;
        private int mLargestHeight = 0;
        private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.graphics.HardwareRenderer.ProcessInitializer.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                if (ProcessInitializer.this.mContext == null) {
                    Log.d(HardwareRenderer.LOG_TAG, "mContext is null.");
                    return;
                }
                DisplayManager displayManager = (DisplayManager) ProcessInitializer.this.mContext.getSystemService(Context.DISPLAY_SERVICE);
                if (displayManager == null) {
                    Log.d(HardwareRenderer.LOG_TAG, "Failed to find DisplayManager for display-based configuration");
                    return;
                }
                Display display = displayManager.getDisplay(0);
                if (display == null) {
                    Log.d(HardwareRenderer.LOG_TAG, "Failed to find default display for display-based configuration");
                    return;
                }
                Point point = new Point();
                display.getRealSize(point);
                int i2 = point.x;
                int i3 = point.y;
                if (i2 * i3 != ProcessInitializer.this.mLargestWidth * ProcessInitializer.this.mLargestHeight) {
                    Log.d(HardwareRenderer.LOG_TAG, "onDisplayChanged. displayId=" + i + " current wxh=" + i2 + "x" + i3 + " mLargest wxh=" + ProcessInitializer.this.mLargestWidth + "x" + ProcessInitializer.this.mLargestHeight);
                    ProcessInitializer.this.mLargestWidth = i2;
                    ProcessInitializer.this.mLargestHeight = i3;
                    ColorSpace preferredWideGamutColorSpace = display.getPreferredWideGamutColorSpace();
                    int dataSpace = preferredWideGamutColorSpace != null ? preferredWideGamutColorSpace.getDataSpace() : 0;
                    OverlayProperties overlaySupport = display.getOverlaySupport();
                    HardwareRenderer.nInitDisplayInfo(ProcessInitializer.this.mLargestWidth, ProcessInitializer.this.mLargestHeight, display.getRefreshRate(), dataSpace, display.getAppVsyncOffsetNanos(), display.getPresentationDeadlineNanos(), overlaySupport.isCombinationSupported(411107328, 22), overlaySupport.isCombinationSupported(DataSpace.pack(655360, 8388608, 402653184), 59), overlaySupport.isMixedColorSpacesSupported());
                }
            }
        };

        private ProcessInitializer() {
        }

        protected void finalize() throws Throwable {
            if (HardwareRenderer.USE_LOGICAL_SCREEN_RESOLUTION) {
                DisplayManagerGlobal.getInstance().unregisterDisplayListener(this.mDisplayListener);
                this.mContext = null;
            }
            super.finalize();
        }

        synchronized void setPackageName(String str) {
            if (this.mInitialized) {
                return;
            }
            this.mPackageName = str;
        }

        synchronized void setIsolated(boolean z) {
            if (this.mInitialized) {
                return;
            }
            this.mIsolated = z;
        }

        synchronized void setContext(Context context) {
            if (this.mInitialized) {
                return;
            }
            this.mContext = context;
        }

        synchronized void init(long j) {
            if (this.mInitialized) {
                return;
            }
            this.mInitialized = true;
            initSched(j);
            initGraphicsStats();
        }

        private void initSched(long j) {
            try {
                ActivityManager.getService().setRenderThread(HardwareRenderer.nGetRenderThreadTid(j));
            } catch (Throwable th) {
                Log.w(HardwareRenderer.LOG_TAG, "Failed to set scheduler for RenderThread", th);
            }
        }

        private void initGraphicsStats() {
            if (this.mPackageName == null) {
                return;
            }
            try {
                IBinder service = ServiceManager.getService(GraphicsStatsService.GRAPHICS_STATS_SERVICE);
                if (service == null) {
                    return;
                }
                this.mGraphicsStatsService = IGraphicsStats.Stub.asInterface(service);
                requestBuffer();
            } catch (Throwable th) {
                Log.w(HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", th);
            }
        }

        synchronized void initUsingContext() {
            if (this.mContext == null) {
                return;
            }
            initDisplayInfo();
            HardwareRenderer.nSetIsHighEndGfx(ActivityManager.isHighEndGfx());
            HardwareRenderer.nSetIsLowRam(ActivityManager.isLowRamDeviceStatic());
            if (HardwareRenderer.USE_LOGICAL_SCREEN_RESOLUTION) {
                DisplayManagerGlobal.getInstance().registerDisplayListener(this.mDisplayListener, new Handler(Looper.getMainLooper()), 7L, this.mContext.getBasePackageName());
            } else {
                this.mContext = null;
            }
        }

        private void initDisplayInfo() {
            ColorSpace preferredWideGamutColorSpace;
            if (this.mDisplayInitialized) {
                return;
            }
            if (this.mIsolated) {
                this.mDisplayInitialized = true;
                return;
            }
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
            if (displayManager == null) {
                Log.d(HardwareRenderer.LOG_TAG, "Failed to find DisplayManager for display-based configuration");
                return;
            }
            int i = 0;
            Display display = displayManager.getDisplay(0);
            if (display == null) {
                Log.d(HardwareRenderer.LOG_TAG, "Failed to find default display for display-based configuration");
                return;
            }
            if (HardwareRenderer.USE_LOGICAL_SCREEN_RESOLUTION) {
                this.mDisplayListener.onDisplayChanged(display.getDisplayId());
                if (this.mLargestWidth != 0 && this.mLargestHeight != 0) {
                    this.mDisplayInitialized = true;
                    Log.d(HardwareRenderer.LOG_TAG, "Set largestWidth and largestHeight as logical resolution. (" + this.mLargestWidth + "x" + this.mLargestHeight + NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
            }
            Display[] displays = displayManager.getDisplays();
            if (displays.length == 0) {
                Log.d(HardwareRenderer.LOG_TAG, "Failed to query displays");
                return;
            }
            Display.Mode mode = display.getMode();
            ColorSpace preferredWideGamutColorSpace2 = display.getPreferredWideGamutColorSpace();
            int dataSpace = preferredWideGamutColorSpace2 != null ? preferredWideGamutColorSpace2.getDataSpace() : 0;
            int physicalWidth = mode.getPhysicalWidth();
            int physicalHeight = mode.getPhysicalHeight();
            OverlayProperties overlaySupport = display.getOverlaySupport();
            int i2 = physicalHeight;
            int dataSpace2 = dataSpace;
            int i3 = physicalWidth;
            int i4 = 0;
            while (i4 < displays.length) {
                Display display2 = displays[i4];
                if (dataSpace2 == 0 && (preferredWideGamutColorSpace = display2.getPreferredWideGamutColorSpace()) != null) {
                    dataSpace2 = preferredWideGamutColorSpace.getDataSpace();
                }
                Display.Mode[] supportedModes = display2.getSupportedModes();
                for (int i5 = i; i5 < supportedModes.length; i5++) {
                    Display.Mode mode2 = supportedModes[i5];
                    int physicalWidth2 = mode2.getPhysicalWidth();
                    int physicalHeight2 = mode2.getPhysicalHeight();
                    if (physicalWidth2 * physicalHeight2 > i3 * i2) {
                        i2 = physicalHeight2;
                        i3 = physicalWidth2;
                    }
                }
                i4++;
                i = 0;
            }
            Log.d(HardwareRenderer.LOG_TAG, "Set largestWidth and largestHeight as physical resolution. (" + i3 + "x" + i2 + NavigationBarInflaterView.KEY_CODE_END);
            HardwareRenderer.nInitDisplayInfo(i3, i2, display.getRefreshRate(), dataSpace2, display.getAppVsyncOffsetNanos(), display.getPresentationDeadlineNanos(), overlaySupport.isCombinationSupported(411107328, 22), overlaySupport.isCombinationSupported(DataSpace.pack(655360, 8388608, 402653184), 59), overlaySupport.isMixedColorSpacesSupported());
            this.mDisplayInitialized = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void rotateBuffer() {
            HardwareRenderer.nRotateProcessStatsBuffer();
            requestBuffer();
        }

        private void requestBuffer() {
            try {
                ParcelFileDescriptor parcelFileDescriptorRequestBufferForProcess = this.mGraphicsStatsService.requestBufferForProcess(this.mPackageName, this.mGraphicsStatsCallback);
                HardwareRenderer.nSetProcessStatsBuffer(parcelFileDescriptorRequestBufferForProcess.getFd());
                parcelFileDescriptorRequestBufferForProcess.close();
            } catch (Throwable th) {
                Log.w(HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", th);
            }
        }
    }
}
