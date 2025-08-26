package com.android.systemui.screenshot.scroll;

import android.content.Context;
import android.graphics.Rect;
import android.media.Image;
import android.media.ImageReader;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import android.view.IScrollCaptureCallbacks;
import android.view.IScrollCaptureConnection;
import android.view.IWindowManager;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class ScrollCaptureClient {
    static final int MATCH_ANY_TASK = -1;
    public final Executor mBgExecutor;
    public IBinder mHostWindowToken;
    public final IWindowManager mWindowManagerService;

    public class CaptureResult {
        public final Rect captured;
        public final Image image;
        public final Rect requested;

        public CaptureResult(Image image, Rect rect, Rect rect2) {
            this.image = image;
            this.requested = rect;
            this.captured = rect2;
        }

        public final String toString() {
            return "CaptureResult{requested=" + this.requested + " (" + this.requested.width() + "x" + this.requested.height() + "), captured=" + this.captured + " (" + this.captured.width() + "x" + this.captured.height() + "), image=" + this.image + '}';
        }
    }

    public interface Session {
    }

    public class SessionWrapper extends IScrollCaptureCallbacks.Stub implements Session, IBinder.DeathRecipient, ImageReader.OnImageAvailableListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Executor mBgExecutor;
        public final Rect mBoundsInWindow;
        public ICancellationSignal mCancellationSignal;
        public Rect mCapturedArea;
        public Image mCapturedImage;
        public IScrollCaptureConnection mConnection;
        public CallbackToFutureAdapter.Completer mEndCompleter;
        public final Object mLock;
        public ImageReader mReader;
        public Rect mRequestRect;
        public CallbackToFutureAdapter.Completer mStartCompleter;
        public boolean mStarted;
        public final int mTargetHeight;
        public final int mTileHeight;
        public CallbackToFutureAdapter.Completer mTileRequestCompleter;
        public final int mTileWidth;

        public /* synthetic */ SessionWrapper(IScrollCaptureConnection iScrollCaptureConnection, Rect rect, Rect rect2, float f, Executor executor, int i) {
            this(iScrollCaptureConnection, rect, rect2, f, executor);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.d("Screenshot", "binderDied! The target process just crashed :-(");
            this.mConnection = null;
            CallbackToFutureAdapter.Completer completer = this.mStartCompleter;
            if (completer != null) {
                completer.setException(new DeadObjectException("The remote process died"));
            }
            CallbackToFutureAdapter.Completer completer2 = this.mTileRequestCompleter;
            if (completer2 != null) {
                completer2.setException(new DeadObjectException("The remote process died"));
            }
            CallbackToFutureAdapter.Completer completer3 = this.mEndCompleter;
            if (completer3 != null) {
                completer3.setException(new DeadObjectException("The remote process died"));
            }
        }

        public final void onCaptureEnded() {
            try {
                this.mConnection.close();
            } catch (RemoteException unused) {
            }
            this.mConnection = null;
            this.mEndCompleter.set(null);
        }

        public final void onCaptureStarted() {
            Log.d("Screenshot", "onCaptureStarted");
            this.mStartCompleter.set(this);
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public final void onImageAvailable(ImageReader imageReader) {
            synchronized (this.mLock) {
                try {
                    Image image = this.mCapturedImage;
                    if (image != null) {
                        image.close();
                    }
                    Image imageAcquireLatestImage = this.mReader.acquireLatestImage();
                    this.mCapturedImage = imageAcquireLatestImage;
                    Rect rect = this.mCapturedArea;
                    if (rect != null) {
                        CaptureResult captureResult = new CaptureResult(imageAcquireLatestImage, this.mRequestRect, rect);
                        this.mCapturedImage = null;
                        this.mRequestRect = null;
                        this.mCapturedArea = null;
                        this.mTileRequestCompleter.set(captureResult);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onImageRequestCompleted(int i, Rect rect) {
            synchronized (this.mLock) {
                try {
                    this.mCapturedArea = rect;
                    if (this.mCapturedImage != null || rect == null || rect.isEmpty()) {
                        CaptureResult captureResult = new CaptureResult(this.mCapturedImage, this.mRequestRect, this.mCapturedArea);
                        this.mCapturedImage = null;
                        this.mRequestRect = null;
                        this.mCapturedArea = null;
                        this.mTileRequestCompleter.set(captureResult);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private SessionWrapper(IScrollCaptureConnection iScrollCaptureConnection, Rect rect, Rect rect2, float f, Executor executor) throws RemoteException {
            this.mLock = new Object();
            Objects.requireNonNull(iScrollCaptureConnection);
            IScrollCaptureConnection iScrollCaptureConnection2 = iScrollCaptureConnection;
            this.mConnection = iScrollCaptureConnection2;
            iScrollCaptureConnection2.asBinder().linkToDeath(this, 0);
            Objects.requireNonNull(rect);
            Objects.requireNonNull(rect2);
            this.mBoundsInWindow = rect2;
            int iMin = Math.min(4194304, (rect2.height() * rect2.width()) / 2);
            this.mTileWidth = rect2.width();
            this.mTileHeight = iMin / rect2.width();
            this.mTargetHeight = (int) (rect2.height() * f);
            this.mBgExecutor = executor;
        }
    }

    public ScrollCaptureClient(IWindowManager iWindowManager, Executor executor, Context context) {
        Objects.requireNonNull(context.getDisplay(), "context must be associated with a Display!");
        this.mBgExecutor = executor;
        this.mWindowManagerService = iWindowManager;
    }
}
