package com.android.systemui.screenshot.scroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class ScrollCaptureController {
    public final Executor mBgExecutor;
    public volatile boolean mCancelled;
    public CallbackToFutureAdapter.Completer mCaptureCompleter;
    public final ScrollCaptureClient mClient;
    public final Context mContext;
    public CallbackToFutureAdapter.SafeFuture mEndFuture;
    public final UiEventLogger mEventLogger;
    public boolean mFinishOnBoundary;
    public final ImageTileSet mImageTileSet;
    public boolean mScrollingUp = true;
    public ScrollCaptureClient.Session mSession;
    public CallbackToFutureAdapter.SafeFuture mSessionFuture;
    public CallbackToFutureAdapter.SafeFuture mTileFuture;
    public String mWindowOwner;

    public class LongScreenshot {
        public final ImageTileSet mImageTileSet;
        public final ScrollCaptureClient.Session mSession;

        public LongScreenshot(ScrollCaptureClient.Session session, ImageTileSet imageTileSet) {
            this.mSession = session;
            this.mImageTileSet = imageTileSet;
        }

        public final Bitmap toBitmap() {
            ImageTileSet imageTileSet = this.mImageTileSet;
            imageTileSet.getClass();
            Rect rect = new Rect(0, 0, imageTileSet.getWidth(), imageTileSet.getHeight());
            if (((ArrayList) imageTileSet.mTiles).isEmpty()) {
                return null;
            }
            RenderNode renderNode = new RenderNode("Bitmap Export");
            renderNode.setPosition(0, 0, rect.width(), rect.height());
            RecordingCanvas recordingCanvasBeginRecording = renderNode.beginRecording();
            TiledImageDrawable tiledImageDrawable = new TiledImageDrawable(imageTileSet);
            tiledImageDrawable.setBounds(rect);
            tiledImageDrawable.draw(recordingCanvasBeginRecording);
            renderNode.endRecording();
            return HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LongScreenshot{l=");
            ImageTileSet imageTileSet = this.mImageTileSet;
            sb.append(imageTileSet.mRegion.getBounds().left);
            sb.append(", t=");
            sb.append(imageTileSet.getTop());
            sb.append(", r=");
            sb.append(imageTileSet.mRegion.getBounds().right);
            sb.append(", b=");
            sb.append(imageTileSet.mRegion.getBounds().bottom);
            sb.append(", w=");
            sb.append(imageTileSet.getWidth());
            sb.append(", h=");
            sb.append(imageTileSet.getHeight());
            sb.append("}");
            return sb.toString();
        }
    }

    public ScrollCaptureController(Context context, Executor executor, ScrollCaptureClient scrollCaptureClient, ImageTileSet imageTileSet, UiEventLogger uiEventLogger) {
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mClient = scrollCaptureClient;
        this.mImageTileSet = imageTileSet;
        this.mEventLogger = uiEventLogger;
    }

    public final void finishCapture() {
        if (this.mImageTileSet.getHeight() > 0) {
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_COMPLETED, 0, this.mWindowOwner);
        } else {
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_FAILURE, 0, this.mWindowOwner);
        }
        ScrollCaptureClient.SessionWrapper sessionWrapper = (ScrollCaptureClient.SessionWrapper) this.mSession;
        sessionWrapper.getClass();
        Log.d("Screenshot", "end()");
        CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1(sessionWrapper, 1));
        this.mEndFuture = future;
        future.delegate.addListener(new ScrollCaptureController$$ExternalSyntheticLambda1(this, 3), this.mContext.getMainExecutor());
    }

    public float getTargetTopSizeRatio() {
        return 0.4f;
    }

    public final void onCaptureResult(ScrollCaptureClient.CaptureResult captureResult) {
        int i;
        int top;
        int i2;
        boolean z = captureResult.captured.height() == 0;
        ImageTileSet imageTileSet = this.mImageTileSet;
        if (!z) {
            int size = ((ArrayList) imageTileSet.mTiles).size() + 1;
            this.mSession.getClass();
            if (size >= 30) {
                finishCapture();
                return;
            } else if (this.mScrollingUp && !this.mFinishOnBoundary) {
                if (captureResult.captured.height() + imageTileSet.getHeight() >= ((ScrollCaptureClient.SessionWrapper) this.mSession).mTargetHeight * 0.4f) {
                    imageTileSet.clear();
                    this.mScrollingUp = false;
                }
            }
        } else if (this.mFinishOnBoundary) {
            finishCapture();
            return;
        } else {
            imageTileSet.clear();
            this.mFinishOnBoundary = true;
            this.mScrollingUp = !this.mScrollingUp;
        }
        if (!z) {
            imageTileSet.addTile(new ImageTile(captureResult.image, captureResult.captured));
        }
        imageTileSet.getClass();
        Region region = new Region();
        region.op(imageTileSet.mRegion.getBounds(), imageTileSet.mRegion, Region.Op.DIFFERENCE);
        Rect bounds = region.getBounds();
        if (!bounds.isEmpty()) {
            requestNextTile(bounds.top);
            return;
        }
        int height = imageTileSet.getHeight();
        ScrollCaptureClient.SessionWrapper sessionWrapper = (ScrollCaptureClient.SessionWrapper) this.mSession;
        if (height >= sessionWrapper.mTargetHeight) {
            finishCapture();
            return;
        }
        if (z) {
            if (this.mScrollingUp) {
                top = captureResult.requested.top;
                i2 = sessionWrapper.mTileHeight;
                i = top - i2;
            } else {
                i = captureResult.requested.bottom;
            }
        } else if (this.mScrollingUp) {
            top = imageTileSet.getTop();
            i2 = ((ScrollCaptureClient.SessionWrapper) this.mSession).mTileHeight;
            i = top - i2;
        } else {
            i = imageTileSet.mRegion.getBounds().bottom;
        }
        requestNextTile(i);
    }

    public final void requestNextTile(int i) {
        if (this.mCancelled) {
            Log.d("Screenshot", "requestNextTile: CANCELLED");
            return;
        }
        ScrollCaptureClient.SessionWrapper sessionWrapper = (ScrollCaptureClient.SessionWrapper) this.mSession;
        sessionWrapper.getClass();
        sessionWrapper.mRequestRect = new Rect(0, i, sessionWrapper.mTileWidth, sessionWrapper.mTileHeight + i);
        CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1(sessionWrapper, 0));
        this.mTileFuture = future;
        future.delegate.addListener(new ScrollCaptureController$$ExternalSyntheticLambda1(this, 2), this.mBgExecutor);
    }
}
