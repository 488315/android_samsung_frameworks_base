package android.view;

import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.Rect;
import android.os.Handler;
import android.view.PixelCopy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class PixelCopy {
    public static final int ERROR_DESTINATION_INVALID = 5;
    public static final int ERROR_SOURCE_INVALID = 4;
    public static final int ERROR_SOURCE_NO_DATA = 3;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_UNKNOWN = 1;
    public static final int SUCCESS = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CopyResultStatus {
    }

    public interface OnPixelCopyFinishedListener {
        void onPixelCopyFinished(int i);
    }

    public static void request(SurfaceView surfaceView, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        request(surfaceView.getHolder().getSurface(), bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(SurfaceView surfaceView, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        request(surfaceView.getHolder().getSurface(), rect, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Surface surface, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        request(surface, (Rect) null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Surface surface, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        validateBitmapDest(bitmap);
        if (!surface.isValid()) {
            throw new IllegalArgumentException("Surface isn't valid, source.isValid() == false");
        }
        if (rect != null && rect.isEmpty()) {
            throw new IllegalArgumentException("sourceRect is empty");
        }
        HardwareRenderer.copySurfaceInto(surface, new AnonymousClass1(rect, bitmap, handler, onPixelCopyFinishedListener));
    }

    /* renamed from: android.view.PixelCopy$1, reason: invalid class name */
    class AnonymousClass1 extends HardwareRenderer.CopyRequest {
        final /* synthetic */ OnPixelCopyFinishedListener val$listener;
        final /* synthetic */ Handler val$listenerThread;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Rect rect, Bitmap bitmap, Handler handler, OnPixelCopyFinishedListener onPixelCopyFinishedListener) {
            super(rect, bitmap);
            this.val$listenerThread = handler;
            this.val$listener = onPixelCopyFinishedListener;
        }

        @Override // android.graphics.HardwareRenderer.CopyRequest
        public void onCopyFinished(final int i) {
            Handler handler = this.val$listenerThread;
            final OnPixelCopyFinishedListener onPixelCopyFinishedListener = this.val$listener;
            handler.post(new Runnable() { // from class: android.view.PixelCopy$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PixelCopy.OnPixelCopyFinishedListener.this.onPixelCopyFinished(i);
                }
            });
        }
    }

    public static void request(Window window, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        request(window, (Rect) null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Window window, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        validateBitmapDest(bitmap);
        Rect rect2 = new Rect();
        request(sourceForWindow(window, rect2), adjustSourceRectForInsets(rect2, rect), bitmap, onPixelCopyFinishedListener, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateBitmapDest(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap cannot be null");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("Bitmap is recycled");
        }
        if (!bitmap.isMutable()) {
            throw new IllegalArgumentException("Bitmap is immutable");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Surface sourceForWindow(Window window, Rect rect) {
        Surface surface;
        if (window == null) {
            throw new IllegalArgumentException("source is null");
        }
        if (window.peekDecorView() == null) {
            throw new IllegalArgumentException("Only able to copy windows with decor views");
        }
        ViewRootImpl viewRootImpl = window.peekDecorView().getViewRootImpl();
        if (viewRootImpl != null) {
            surface = viewRootImpl.mSurface;
            Rect rect2 = viewRootImpl.mWindowAttributes.surfaceInsets;
            rect.set(rect2.left, rect2.top, viewRootImpl.mWidth + rect2.left, viewRootImpl.mHeight + rect2.top);
        } else {
            surface = null;
        }
        if (surface == null || !surface.isValid()) {
            throw new IllegalArgumentException("Window doesn't have a backing surface!");
        }
        return surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Rect adjustSourceRectForInsets(Rect rect, Rect rect2) {
        if (rect2 == null) {
            return rect;
        }
        if (rect != null) {
            rect2.offset(rect.left, rect.top);
        }
        return rect2;
    }

    public static final class Result {
        private Bitmap mBitmap;
        private int mStatus;

        private Result(int i, Bitmap bitmap) {
            this.mStatus = i;
            this.mBitmap = bitmap;
        }

        public int getStatus() {
            return this.mStatus;
        }

        private void validateStatus() {
            if (this.mStatus == 0) {
                return;
            }
            throw new IllegalStateException("Copy request didn't succeed, status = " + this.mStatus);
        }

        public Bitmap getBitmap() {
            validateStatus();
            return this.mBitmap;
        }
    }

    public static final class Request {
        private Bitmap mDest;
        private final Surface mSource;
        private final Rect mSourceInsets;
        private Rect mSrcRect;

        private Request(Surface surface, Rect rect) {
            this.mSource = surface;
            this.mSourceInsets = rect;
        }

        public static final class Builder {
            private Request mRequest;

            private Builder(Request request) {
                this.mRequest = request;
            }

            public static Builder ofWindow(Window window) {
                Rect rect = new Rect();
                return new Builder(new Request(PixelCopy.sourceForWindow(window, rect), rect));
            }

            public static Builder ofWindow(View view) {
                Surface surface;
                if (view == null || !view.isAttachedToWindow()) {
                    throw new IllegalArgumentException("View must not be null & must be attached to window");
                }
                Rect rect = new Rect();
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl != null) {
                    surface = viewRootImpl.mSurface;
                    rect.set(viewRootImpl.mWindowAttributes.surfaceInsets);
                } else {
                    surface = null;
                }
                if (surface == null || !surface.isValid()) {
                    throw new IllegalArgumentException("Window doesn't have a backing surface!");
                }
                return new Builder(new Request(surface, rect));
            }

            public static Builder ofSurface(Surface surface) {
                if (surface == null || !surface.isValid()) {
                    throw new IllegalArgumentException("Source must not be null & must be valid");
                }
                return new Builder(new Request(surface, null));
            }

            public static Builder ofSurface(SurfaceView surfaceView) {
                return ofSurface(surfaceView.getHolder().getSurface());
            }

            private void requireNotBuilt() {
                if (this.mRequest == null) {
                    throw new IllegalStateException("build() already called on this builder");
                }
            }

            public Builder setSourceRect(Rect rect) {
                requireNotBuilt();
                this.mRequest.mSrcRect = rect;
                return this;
            }

            public Builder setDestinationBitmap(Bitmap bitmap) {
                requireNotBuilt();
                if (bitmap != null) {
                    PixelCopy.validateBitmapDest(bitmap);
                }
                this.mRequest.mDest = bitmap;
                return this;
            }

            public Request build() {
                requireNotBuilt();
                Request request = this.mRequest;
                this.mRequest = null;
                return request;
            }
        }

        public Bitmap getDestinationBitmap() {
            return this.mDest;
        }

        public Rect getSourceRect() {
            return this.mSrcRect;
        }

        public void request(Executor executor, final Consumer<Result> consumer) {
            if (!this.mSource.isValid()) {
                executor.execute(new Runnable() { // from class: android.view.PixelCopy$Request$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(new PixelCopy.Result(4, null));
                    }
                });
            } else {
                HardwareRenderer.copySurfaceInto(this.mSource, new AnonymousClass1(this, PixelCopy.adjustSourceRectForInsets(this.mSourceInsets, this.mSrcRect), this.mDest, executor, consumer));
            }
        }

        /* renamed from: android.view.PixelCopy$Request$1, reason: invalid class name */
        class AnonymousClass1 extends HardwareRenderer.CopyRequest {
            final /* synthetic */ Executor val$callbackExecutor;
            final /* synthetic */ Consumer val$listener;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(Request request, Rect rect, Bitmap bitmap, Executor executor, Consumer consumer) {
                super(rect, bitmap);
                this.val$callbackExecutor = executor;
                this.val$listener = consumer;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCopyFinished$0(Consumer consumer, int i) {
                consumer.accept(new Result(i, this.mDestinationBitmap));
            }

            @Override // android.graphics.HardwareRenderer.CopyRequest
            public void onCopyFinished(final int i) {
                Executor executor = this.val$callbackExecutor;
                final Consumer consumer = this.val$listener;
                executor.execute(new Runnable() { // from class: android.view.PixelCopy$Request$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PixelCopy.Request.AnonymousClass1.this.lambda$onCopyFinished$0(consumer, i);
                    }
                });
            }
        }
    }

    public static void request(Request request, Executor executor, Consumer<Result> consumer) {
        request.request(executor, consumer);
    }

    private PixelCopy() {
    }
}
