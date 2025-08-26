package android.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BLASTBufferQueue;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class ThreadedRenderer extends HardwareRenderer {
    public static final String DEBUG_DIRTY_REGIONS_PROPERTY = "debug.hwui.show_dirty_regions";
    public static final String DEBUG_FORCE_DARK = "debug.hwui.force_dark";
    public static final String DEBUG_FPS_DIVISOR = "debug.hwui.fps_divisor";
    public static final String DEBUG_OVERDRAW_PROPERTY = "debug.hwui.overdraw";
    public static final String DEBUG_SHOW_LAYERS_UPDATES_PROPERTY = "debug.hwui.show_layers_updates";
    public static final String DEBUG_SHOW_NON_RECTANGULAR_CLIP_PROPERTY = "debug.hwui.show_non_rect_clip";
    public static int EGL_CONTEXT_PRIORITY_HIGH_IMG = 12545;
    public static int EGL_CONTEXT_PRIORITY_LOW_IMG = 12547;
    public static int EGL_CONTEXT_PRIORITY_MEDIUM_IMG = 12546;
    public static int EGL_CONTEXT_PRIORITY_REALTIME_NV = 13143;
    public static final String OVERDRAW_PROPERTY_SHOW = "show";
    static final String PRINT_CONFIG_PROPERTY = "debug.hwui.print_config";
    static final String PROFILE_MAXFRAMES_PROPERTY = "debug.hwui.profile.maxframes";
    public static final String PROFILE_PROPERTY = "debug.hwui.profile";
    public static final String PROFILE_PROPERTY_VISUALIZE_BARS = "visual_bars";
    private static final String[] VISUALIZERS = {PROFILE_PROPERTY_VISUALIZE_BARS};
    public static boolean sRendererEnabled = true;
    private boolean mEnabled;
    private int mHeight;
    private int mInsetLeft;
    private int mInsetTop;
    private final float mLightRadius;
    private final float mLightY;
    private final float mLightZ;
    private ArrayList<HardwareRenderer.FrameDrawingCallback> mNextRtFrameCallbacks;
    private boolean mRootNodeNeedsUpdate;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    private int mWidth;
    private boolean mInitialized = false;
    private boolean mRequested = true;
    private final WebViewOverlayProvider mWebViewOverlayProvider = new WebViewOverlayProvider();
    private boolean mWebViewOverlaysEnabled = false;

    interface DrawCallbacks {
        void onPostDraw(RecordingCanvas recordingCanvas);

        void onPreDraw(RecordingCanvas recordingCanvas);
    }

    public static void enableForegroundTrimming() {
    }

    Picture captureRenderingCommands() {
        return null;
    }

    public static void initForSystemProcess() {
        if (!ActivityManager.isHighEndGfx()) {
            sRendererEnabled = false;
        }
        setIsSystemOrPersistent();
    }

    public static ThreadedRenderer create(Context context, boolean z, String str) {
        return new ThreadedRenderer(context, z, str);
    }

    private static final class WebViewOverlayProvider implements HardwareRenderer.PrepareSurfaceControlForWebviewCallback, HardwareRenderer.ASurfaceTransactionCallback {
        private static final boolean sOverlaysAreEnabled = ThreadedRenderer.isWebViewOverlaysEnabled();
        private BLASTBufferQueue mBLASTBufferQueue;
        private boolean mHasWebViewOverlays;
        private SurfaceControl mSurfaceControl;
        private final SurfaceControl.Transaction mTransaction;

        private WebViewOverlayProvider() {
            this.mTransaction = new SurfaceControl.Transaction();
            this.mHasWebViewOverlays = false;
        }

        public boolean setSurfaceControlOpaque(boolean z) {
            synchronized (this) {
                if (this.mHasWebViewOverlays) {
                    return false;
                }
                this.mTransaction.setOpaque(this.mSurfaceControl, z).apply();
                return z;
            }
        }

        public boolean shouldEnableOverlaySupport() {
            return (!sOverlaysAreEnabled || this.mSurfaceControl == null || this.mBLASTBufferQueue == null) ? false : true;
        }

        public void setSurfaceControl(SurfaceControl surfaceControl) {
            synchronized (this) {
                this.mSurfaceControl = surfaceControl;
                if (surfaceControl != null && this.mHasWebViewOverlays) {
                    this.mTransaction.setOpaque(surfaceControl, false).apply();
                }
            }
        }

        public void setBLASTBufferQueue(BLASTBufferQueue bLASTBufferQueue) {
            synchronized (this) {
                this.mBLASTBufferQueue = bLASTBufferQueue;
            }
        }

        @Override // android.graphics.HardwareRenderer.PrepareSurfaceControlForWebviewCallback
        public void prepare() {
            synchronized (this) {
                this.mHasWebViewOverlays = true;
                SurfaceControl surfaceControl = this.mSurfaceControl;
                if (surfaceControl != null) {
                    this.mTransaction.setOpaque(surfaceControl, false).apply();
                }
            }
        }

        @Override // android.graphics.HardwareRenderer.ASurfaceTransactionCallback
        public boolean onMergeTransaction(long j, long j2, long j3) {
            synchronized (this) {
                BLASTBufferQueue bLASTBufferQueue = this.mBLASTBufferQueue;
                if (bLASTBufferQueue == null) {
                    return false;
                }
                bLASTBufferQueue.mergeWithNextTransaction(j, j3);
                return true;
            }
        }
    }

    ThreadedRenderer(Context context, boolean z, String str) {
        setName(str);
        setOpaque(!z);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.Lighting, 0, 0);
        this.mLightY = typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        this.mLightZ = typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        this.mLightRadius = typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
        float f = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
        float f2 = typedArrayObtainStyledAttributes.getFloat(1, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        setLightSourceAlpha(f, f2);
    }

    @Override // android.graphics.HardwareRenderer
    public void destroy() {
        this.mInitialized = false;
        updateEnabledState(null);
        super.destroy();
    }

    boolean isEnabled() {
        return this.mEnabled;
    }

    void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    boolean isRequested() {
        return this.mRequested;
    }

    void setRequested(boolean z) {
        this.mRequested = z;
    }

    private void updateEnabledState(Surface surface) {
        if (surface == null || !surface.isValid()) {
            setEnabled(false);
        } else {
            setEnabled(this.mInitialized);
        }
    }

    boolean initialize(Surface surface) throws Surface.OutOfResourcesException {
        boolean z = !this.mInitialized;
        this.mInitialized = true;
        updateEnabledState(surface);
        setSurface(surface);
        return z;
    }

    boolean initializeIfNeeded(int i, int i2, View.AttachInfo attachInfo, Surface surface, Rect rect) throws Surface.OutOfResourcesException {
        if (!isRequested() || isEnabled() || !initialize(surface)) {
            return false;
        }
        setup(i, i2, attachInfo, rect);
        return true;
    }

    void updateSurface(Surface surface) throws Surface.OutOfResourcesException {
        updateEnabledState(surface);
        setSurface(surface);
    }

    @Override // android.graphics.HardwareRenderer
    public void setSurface(Surface surface) {
        if (surface != null && surface.isValid()) {
            super.setSurface(surface);
        } else {
            super.setSurface(null);
        }
    }

    void registerRtFrameCallback(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mNextRtFrameCallbacks == null) {
            this.mNextRtFrameCallbacks = new ArrayList<>();
        }
        this.mNextRtFrameCallbacks.add(frameDrawingCallback);
    }

    void unregisterRtFrameCallback(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        ArrayList<HardwareRenderer.FrameDrawingCallback> arrayList = this.mNextRtFrameCallbacks;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(frameDrawingCallback);
    }

    void destroyHardwareResources(View view) {
        destroyResources(view);
        clearContent();
    }

    private static void destroyResources(View view) {
        view.destroyHardwareResources();
    }

    void setup(int i, int i2, View.AttachInfo attachInfo, Rect rect) {
        this.mWidth = i;
        this.mHeight = i2;
        if (rect != null && (rect.left != 0 || rect.right != 0 || rect.top != 0 || rect.bottom != 0)) {
            this.mInsetLeft = rect.left;
            this.mInsetTop = rect.top;
            this.mSurfaceWidth = i + this.mInsetLeft + rect.right;
            this.mSurfaceHeight = i2 + this.mInsetTop + rect.bottom;
            setOpaque(false);
        } else {
            this.mInsetLeft = 0;
            this.mInsetTop = 0;
            this.mSurfaceWidth = i;
            this.mSurfaceHeight = i2;
        }
        this.mRootNode.setLeftTopRightBottom(-this.mInsetLeft, -this.mInsetTop, this.mSurfaceWidth, this.mSurfaceHeight);
        setLightCenter(attachInfo);
    }

    public boolean rendererOwnsSurfaceControlOpacity() {
        return this.mWebViewOverlayProvider.mSurfaceControl != null;
    }

    public boolean setSurfaceControlOpaque(boolean z) {
        return this.mWebViewOverlayProvider.setSurfaceControlOpaque(z);
    }

    private void updateWebViewOverlayCallbacks() {
        boolean zShouldEnableOverlaySupport = this.mWebViewOverlayProvider.shouldEnableOverlaySupport();
        if (zShouldEnableOverlaySupport != this.mWebViewOverlaysEnabled) {
            this.mWebViewOverlaysEnabled = zShouldEnableOverlaySupport;
            if (zShouldEnableOverlaySupport) {
                setASurfaceTransactionCallback(this.mWebViewOverlayProvider);
                setPrepareSurfaceControlForWebviewCallback(this.mWebViewOverlayProvider);
            } else {
                setASurfaceTransactionCallback(null);
                setPrepareSurfaceControlForWebviewCallback(null);
            }
        }
    }

    @Override // android.graphics.HardwareRenderer
    public void setSurfaceControl(SurfaceControl surfaceControl, BLASTBufferQueue bLASTBufferQueue) {
        super.setSurfaceControl(surfaceControl, bLASTBufferQueue);
        this.mWebViewOverlayProvider.setSurfaceControl(surfaceControl);
        this.mWebViewOverlayProvider.setBLASTBufferQueue(bLASTBufferQueue);
        updateWebViewOverlayCallbacks();
    }

    @Override // android.graphics.HardwareRenderer
    public void notifyCallbackPending() {
        if (isEnabled()) {
            super.notifyCallbackPending();
        }
    }

    @Override // android.graphics.HardwareRenderer
    public void notifyExpensiveFrame() {
        if (isEnabled()) {
            super.notifyExpensiveFrame();
        }
    }

    void setLightCenter(View.AttachInfo attachInfo) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        attachInfo.mDisplay.getRealMetrics(displayMetrics);
        setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - attachInfo.mWindowLeft, this.mLightY - attachInfo.mWindowTop, this.mLightZ * (((Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / (displayMetrics.density * 450.0f)) + 2.0f) / 3.0f), this.mLightRadius);
    }

    int getWidth() {
        return this.mWidth;
    }

    int getHeight() {
        return this.mHeight;
    }

    private static int dumpArgsToFlags(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return 1;
        }
        int i = 0;
        for (String str : strArr) {
            str.hashCode();
            switch (str) {
                case "framestats":
                    i |= 1;
                    break;
                case "-a":
                    i = 1;
                    break;
                case "reset":
                    i |= 2;
                    break;
            }
        }
        return i;
    }

    public static void handleDumpGfxInfo(FileDescriptor fileDescriptor, String[] strArr) {
        dumpGlobalProfileInfo(fileDescriptor, dumpArgsToFlags(strArr));
        WindowManagerGlobal.getInstance().dumpGfxInfo(fileDescriptor, strArr);
    }

    void dumpGfxInfo(PrintWriter printWriter, FileDescriptor fileDescriptor, String[] strArr) {
        printWriter.flush();
        dumpProfileInfo(fileDescriptor, dumpArgsToFlags(strArr));
    }

    @Override // android.graphics.HardwareRenderer
    public boolean loadSystemProperties() {
        boolean zLoadSystemProperties = super.loadSystemProperties();
        if (zLoadSystemProperties) {
            invalidateRoot();
        }
        return zLoadSystemProperties;
    }

    private void updateViewTreeDisplayList(View view) {
        view.mPrivateFlags |= 32;
        view.mRecreateDisplayList = (view.mPrivateFlags & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    private void updateRootDisplayList(View view, DrawCallbacks drawCallbacks) {
        Trace.traceBegin(8L, "Record View#draw()");
        updateViewTreeDisplayList(view);
        ArrayList<HardwareRenderer.FrameDrawingCallback> arrayList = this.mNextRtFrameCallbacks;
        if (arrayList != null) {
            this.mNextRtFrameCallbacks = null;
            setFrameCallback(new AnonymousClass1(this, arrayList));
        }
        if (this.mRootNodeNeedsUpdate || !this.mRootNode.hasDisplayList()) {
            RecordingCanvas recordingCanvasBeginRecording = this.mRootNode.beginRecording(this.mSurfaceWidth, this.mSurfaceHeight);
            try {
                int iSave = recordingCanvasBeginRecording.save();
                recordingCanvasBeginRecording.translate(this.mInsetLeft, this.mInsetTop);
                drawCallbacks.onPreDraw(recordingCanvasBeginRecording);
                recordingCanvasBeginRecording.enableZ();
                recordingCanvasBeginRecording.drawRenderNode(view.updateDisplayListIfDirty());
                recordingCanvasBeginRecording.disableZ();
                drawCallbacks.onPostDraw(recordingCanvasBeginRecording);
                recordingCanvasBeginRecording.restoreToCount(iSave);
                this.mRootNodeNeedsUpdate = false;
            } finally {
                this.mRootNode.endRecording();
            }
        }
        Trace.traceEnd(8L);
    }

    /* renamed from: android.view.ThreadedRenderer$1, reason: invalid class name */
    class AnonymousClass1 implements HardwareRenderer.FrameDrawingCallback {
        final /* synthetic */ ArrayList val$frameCallbacks;

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public void onFrameDraw(long j) {
        }

        AnonymousClass1(ThreadedRenderer threadedRenderer, ArrayList arrayList) {
            this.val$frameCallbacks = arrayList;
        }

        @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
        public HardwareRenderer.FrameCommitCallback onFrameDraw(int i, long j) {
            final ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.val$frameCallbacks.size(); i2++) {
                HardwareRenderer.FrameCommitCallback frameCommitCallbackOnFrameDraw = ((HardwareRenderer.FrameDrawingCallback) this.val$frameCallbacks.get(i2)).onFrameDraw(i, j);
                if (frameCommitCallbackOnFrameDraw != null) {
                    arrayList.add(frameCommitCallbackOnFrameDraw);
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return new HardwareRenderer.FrameCommitCallback() { // from class: android.view.ThreadedRenderer$1$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameCommitCallback
                public final void onFrameCommit(boolean z) {
                    ThreadedRenderer.AnonymousClass1.lambda$onFrameDraw$0(arrayList, z);
                }
            };
        }

        static /* synthetic */ void lambda$onFrameDraw$0(ArrayList arrayList, boolean z) {
            for (int i = 0; i < arrayList.size(); i++) {
                ((HardwareRenderer.FrameCommitCallback) arrayList.get(i)).onFrameCommit(z);
            }
        }
    }

    void invalidateRoot() {
        this.mRootNodeNeedsUpdate = true;
    }

    void draw(View view, View.AttachInfo attachInfo, DrawCallbacks drawCallbacks) {
        attachInfo.mViewRootImpl.mViewFrameInfo.markDrawStart();
        updateRootDisplayList(view, drawCallbacks);
        if (attachInfo.mPendingAnimatingRenderNodes != null) {
            int size = attachInfo.mPendingAnimatingRenderNodes.size();
            for (int i = 0; i < size; i++) {
                registerAnimatingRenderNode(attachInfo.mPendingAnimatingRenderNodes.get(i));
            }
            attachInfo.mPendingAnimatingRenderNodes.clear();
            attachInfo.mPendingAnimatingRenderNodes = null;
        }
        int iSyncAndDrawFrame = syncAndDrawFrame(attachInfo.mViewRootImpl.getUpdatedFrameInfo());
        if ((iSyncAndDrawFrame & 2) != 0) {
            Log.w("HWUI", "Surface lost, forcing relayout");
            attachInfo.mViewRootImpl.mForceNextWindowRelayout = true;
            attachInfo.mViewRootImpl.requestLayout();
        }
        if ((iSyncAndDrawFrame & 1) != 0) {
            attachInfo.mViewRootImpl.invalidate();
        }
    }

    public RenderNode getRootNode() {
        return this.mRootNode;
    }

    public static class SimpleRenderer extends HardwareRenderer {
        private final float mLightRadius;
        private final float mLightY;
        private final float mLightZ;

        public SimpleRenderer(Context context, String str, Surface surface) {
            setName(str);
            setOpaque(false);
            setSurface(surface);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.Lighting, 0, 0);
            this.mLightY = typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
            this.mLightZ = typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
            this.mLightRadius = typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
            float f = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
            float f2 = typedArrayObtainStyledAttributes.getFloat(1, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
            setLightSourceAlpha(f, f2);
        }

        public void setLightCenter(Display display, int i, int i2) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - i, this.mLightY - i2, this.mLightZ * (((Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / (displayMetrics.density * 450.0f)) + 2.0f) / 3.0f), this.mLightRadius);
        }

        public RenderNode getRootNode() {
            return this.mRootNode;
        }

        public void draw(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() * 1000000;
            if (frameDrawingCallback != null) {
                setFrameCallback(frameDrawingCallback);
            }
            createRenderRequest().setVsyncTime(jCurrentAnimationTimeMillis).syncAndDraw();
        }
    }
}
