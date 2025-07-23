package com.android.internal.view;

import android.content.Context;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.ScrollCaptureViewHelper;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ScrollCaptureViewSupport<V extends View> implements ScrollCaptureCallback {
    private static final String SETTING_CAPTURE_DELAY = "screenshot.scroll_capture_delay";
    private static final long SETTING_CAPTURE_DELAY_DEFAULT = 60;
    private static final String TAG = "SCViewSupport";
    private boolean mEnded;
    private final long mPostScrollDelayMillis;
    private final ViewRenderer mRenderer = new ViewRenderer();
    private boolean mStarted;
    private final ScrollCaptureViewHelper<V> mViewHelper;
    private final WeakReference<V> mWeakView;

    ScrollCaptureViewSupport(V v, ScrollCaptureViewHelper<V> scrollCaptureViewHelper) {
        this.mWeakView = new WeakReference<>(v);
        this.mViewHelper = scrollCaptureViewHelper;
        long j = Settings.Global.getLong(v.getContext().getContentResolver(), SETTING_CAPTURE_DELAY, SETTING_CAPTURE_DELAY_DEFAULT);
        this.mPostScrollDelayMillis = j;
        Log.d(TAG, "screenshot.scroll_capture_delay = " + j);
    }

    private static int getColorMode(View view) {
        Context context = view.getContext();
        int colorMode = view.getViewRootImpl().mWindowAttributes.getColorMode();
        if (context.getResources().getConfiguration().isScreenWideColorGamut()) {
            return colorMode;
        }
        return 0;
    }

    public static Rect transformFromRequestToContainer(int i, Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect2);
        rect3.offset(0, -i);
        rect3.offset(rect.left, rect.top);
        return rect3;
    }

    public static Rect transformFromContainerToRequest(int i, Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect2);
        rect3.offset(-rect.left, -rect.top);
        rect3.offset(0, i);
        return rect3;
    }

    public static int computeScrollAmount(Rect rect, Rect rect2) {
        int height = rect.height();
        int i = rect.top;
        int i2 = rect.bottom;
        if (rect2.bottom > i2 && rect2.top > i) {
            if (rect2.height() > height) {
                return rect2.top - i;
            }
            return rect2.bottom - i2;
        }
        if (rect2.top >= i || rect2.bottom >= i2) {
            return 0;
        }
        if (rect2.height() > height) {
            return 0 - (i2 - rect2.bottom);
        }
        return 0 - (i - rect2.top);
    }

    public static View findScrollingReferenceView(ViewGroup viewGroup, int i) {
        viewGroup.getLocalVisibleRect(new Rect());
        int childCount = viewGroup.getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (view != null) {
                if (i < 0) {
                    if (childAt.getTop() >= view.getTop()) {
                    }
                } else if (childAt.getBottom() <= view.getBottom()) {
                }
            }
            view = childAt;
        }
        return view;
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureSearch(CancellationSignal cancellationSignal, Consumer<Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            return;
        }
        V v = this.mWeakView.get();
        this.mStarted = false;
        this.mEnded = false;
        if (v != null && v.isVisibleToUser() && this.mViewHelper.onAcceptSession(v)) {
            consumer.accept(this.mViewHelper.onComputeScrollBounds(v));
        } else {
            consumer.accept(null);
        }
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureStart(ScrollCaptureSession scrollCaptureSession, CancellationSignal cancellationSignal, Runnable runnable) {
        if (cancellationSignal.isCanceled()) {
            return;
        }
        V v = this.mWeakView.get();
        this.mEnded = false;
        this.mStarted = true;
        if (v != null && v.isVisibleToUser()) {
            this.mRenderer.setSurface(scrollCaptureSession.getSurface());
            this.mViewHelper.onPrepareForStart(v, scrollCaptureSession.getScrollBounds());
        }
        runnable.run();
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureImageRequest(ScrollCaptureSession scrollCaptureSession, final CancellationSignal cancellationSignal, Rect rect, final Consumer<Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            Log.w(TAG, "onScrollCaptureImageRequest: cancelled!");
            return;
        }
        final V v = this.mWeakView.get();
        if (v == null || !v.isVisibleToUser()) {
            consumer.accept(new Rect());
        } else {
            this.mViewHelper.onScrollRequested(v, scrollCaptureSession.getScrollBounds(), rect, cancellationSignal, new Consumer() { // from class: com.android.internal.view.ScrollCaptureViewSupport$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ScrollCaptureViewSupport.this.lambda$onScrollCaptureImageRequest$0(v, cancellationSignal, consumer, (ScrollCaptureViewHelper.ScrollResult) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onScrollResult, reason: merged with bridge method [inline-methods] */
    public void lambda$onScrollCaptureImageRequest$0(final ScrollCaptureViewHelper.ScrollResult scrollResult, final V v, CancellationSignal cancellationSignal, final Consumer<Rect> consumer) {
        if (cancellationSignal.isCanceled()) {
            Log.w(TAG, "onScrollCaptureImageRequest: cancelled! skipping render.");
        } else {
            if (scrollResult.availableArea.isEmpty()) {
                consumer.accept(scrollResult.availableArea);
                return;
            }
            final Rect rect = new Rect(scrollResult.availableArea);
            rect.offset(0, -scrollResult.scrollDelta);
            v.postOnAnimationDelayed(new Runnable() { // from class: com.android.internal.view.ScrollCaptureViewSupport$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScrollCaptureViewSupport.this.lambda$onScrollResult$1(scrollResult, v, rect, consumer);
                }
            }, this.mPostScrollDelayMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doCapture, reason: merged with bridge method [inline-methods] */
    public void lambda$onScrollResult$1(ScrollCaptureViewHelper.ScrollResult scrollResult, V v, Rect rect, Consumer<Rect> consumer) {
        int renderView = this.mRenderer.renderView(v, rect);
        if (renderView == 0 || renderView == 1) {
            consumer.accept(new Rect(scrollResult.availableArea));
            return;
        }
        Log.e(TAG, "syncAndDraw(): SyncAndDrawResult = " + renderView);
        consumer.accept(new Rect());
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureEnd(Runnable runnable) {
        V v = this.mWeakView.get();
        if (this.mStarted && !this.mEnded) {
            if (v != null) {
                this.mViewHelper.onPrepareForEnd(v);
                v.invalidate();
            }
            this.mEnded = true;
            this.mRenderer.destroy();
        }
        runnable.run();
    }

    static final class ViewRenderer {
        private static final float AMBIENT_SHADOW_ALPHA = 0.039f;
        private static final float LIGHT_RADIUS_DP = 800.0f;
        private static final float LIGHT_Z_DP = 400.0f;
        private static final float SPOT_SHADOW_ALPHA = 0.039f;
        private static final String TAG = "ViewRenderer";
        private final RenderNode mCaptureRenderNode;
        private final HardwareRenderer mRenderer;
        private Surface mSurface;
        private final Rect mTempRect = new Rect();
        private final int[] mTempLocation = new int[2];
        private long mLastRenderedSourceDrawingId = -1;

        ViewRenderer() {
            HardwareRenderer hardwareRenderer = new HardwareRenderer();
            this.mRenderer = hardwareRenderer;
            hardwareRenderer.setName("ScrollCapture");
            RenderNode renderNode = new RenderNode("ScrollCaptureRoot");
            this.mCaptureRenderNode = renderNode;
            hardwareRenderer.setContentRoot(renderNode);
            hardwareRenderer.setOpaque(false);
        }

        public void setSurface(Surface surface) {
            this.mSurface = surface;
            this.mRenderer.setSurface(surface);
        }

        private boolean updateForView(View view) {
            if (this.mLastRenderedSourceDrawingId == view.getUniqueDrawingId()) {
                return false;
            }
            this.mLastRenderedSourceDrawingId = view.getUniqueDrawingId();
            return true;
        }

        private void setupLighting(View view) {
            this.mLastRenderedSourceDrawingId = view.getUniqueDrawingId();
            DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
            view.getLocationOnScreen(this.mTempLocation);
            this.mRenderer.setLightSourceGeometry((displayMetrics.widthPixels / 2.0f) - this.mTempLocation[0], displayMetrics.heightPixels - this.mTempLocation[1], (int) (displayMetrics.density * 400.0f), (int) (displayMetrics.density * LIGHT_RADIUS_DP));
            this.mRenderer.setLightSourceAlpha(0.039f, 0.039f);
        }

        private void updateRootNode(View view, Rect rect) {
            View rootView = view.getRootView();
            transformToRoot(view, rect, this.mTempRect);
            this.mCaptureRenderNode.setPosition(0, 0, this.mTempRect.width(), this.mTempRect.height());
            RecordingCanvas beginRecording = this.mCaptureRenderNode.beginRecording();
            beginRecording.enableZ();
            beginRecording.translate(-this.mTempRect.left, -this.mTempRect.top);
            RenderNode updateDisplayListIfDirty = rootView.updateDisplayListIfDirty();
            if (updateDisplayListIfDirty.hasDisplayList()) {
                beginRecording.drawRenderNode(updateDisplayListIfDirty);
            }
            this.mCaptureRenderNode.endRecording();
        }

        public int renderView(View view, Rect rect) {
            HardwareRenderer.FrameRenderRequest createRenderRequest = this.mRenderer.createRenderRequest();
            createRenderRequest.setVsyncTime(System.nanoTime());
            if (updateForView(view)) {
                setupLighting(view);
            }
            view.invalidate();
            updateRootNode(view, rect);
            return createRenderRequest.syncAndDraw();
        }

        public void trimMemory() {
            this.mRenderer.clearContent();
        }

        public void destroy() {
            this.mSurface = null;
            this.mRenderer.destroy();
        }

        private void transformToRoot(View view, Rect rect, Rect rect2) {
            view.getLocationInWindow(this.mTempLocation);
            rect2.set(rect);
            int[] iArr = this.mTempLocation;
            rect2.offset(iArr[0], iArr[1]);
        }

        public void setColorMode(int i) {
            this.mRenderer.setColorMode(i);
        }
    }

    public String toString() {
        return "ScrollCaptureViewSupport{view=" + this.mWeakView.get() + ", helper=" + this.mViewHelper + '}';
    }
}
