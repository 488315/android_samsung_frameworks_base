package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.ImageReader;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.IScrollCaptureConnection;
import android.view.ScrollCaptureResponse;
import android.view.View;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import com.android.systemui.screenshot.scroll.ScrollCaptureController;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Result;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyScreenshotController$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ LegacyScreenshotController f$0;
    public final /* synthetic */ UserHandle f$1;
    public final /* synthetic */ ScrollCaptureResponse f$2;

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda15(LegacyScreenshotController legacyScreenshotController, UserHandle userHandle, ScrollCaptureResponse scrollCaptureResponse) {
        this.f$0 = legacyScreenshotController;
        this.f$1 = userHandle;
        this.f$2 = scrollCaptureResponse;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 2;
        switch (this.$r8$classId) {
            case 0:
                LegacyScreenshotController legacyScreenshotController = this.f$0;
                UserHandle userHandle = this.f$1;
                ScrollCaptureResponse scrollCaptureResponse = this.f$2;
                legacyScreenshotController.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_REQUESTED, 0, scrollCaptureResponse.getPackageName());
                int displayId = legacyScreenshotController.mDisplay.getDisplayId();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                legacyScreenshotController.mDisplay.getRealMetrics(displayMetrics);
                Bitmap bitmapCaptureDisplay = ((ImageCaptureImpl) legacyScreenshotController.mImageCapture).captureDisplay(displayId, new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels));
                if (bitmapCaptureDisplay != null) {
                    final LegacyScreenshotController$$ExternalSyntheticLambda15 legacyScreenshotController$$ExternalSyntheticLambda15 = new LegacyScreenshotController$$ExternalSyntheticLambda15(legacyScreenshotController, scrollCaptureResponse, userHandle);
                    ScreenshotShelfViewProxy screenshotShelfViewProxy = legacyScreenshotController.mViewProxy;
                    ScreenshotViewModel screenshotViewModel = screenshotShelfViewProxy.viewModel;
                    screenshotViewModel._scrollingScrim.setValue(bitmapCaptureDisplay);
                    Rect rect = new Rect(scrollCaptureResponse.getBoundsInWindow());
                    Rect windowBounds = scrollCaptureResponse.getWindowBounds();
                    rect.offset(windowBounds != null ? windowBounds.left : 0, windowBounds != null ? windowBounds.top : 0);
                    rect.intersect(new Rect(0, 0, screenshotShelfViewProxy.context.getResources().getDisplayMetrics().widthPixels, screenshotShelfViewProxy.context.getResources().getDisplayMetrics().heightPixels));
                    screenshotViewModel._scrollableRect.setValue(rect);
                    final ScreenshotAnimationController screenshotAnimationController = screenshotShelfViewProxy.animationController;
                    screenshotAnimationController.scrollingScrim.setImageTintBlendMode(BlendMode.SRC_ATOP);
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 0.3f);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$fadeForLongScreenshotTransition$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            screenshotAnimationController.scrollingScrim.setImageTintList(ColorStateList.valueOf(Color.argb(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f, 0.0f, 0.0f)));
                        }
                    });
                    Iterator it = screenshotAnimationController.fadeUI.iterator();
                    while (it.hasNext()) {
                        ((View) it.next()).setAlpha(0.0f);
                    }
                    screenshotAnimationController.screenshotPreview.setAlpha(0.0f);
                    valueAnimatorOfFloat.setDuration(200L);
                    valueAnimatorOfFloat.start();
                    screenshotShelfViewProxy.view.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$prepareScrollingTransition$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            legacyScreenshotController$$ExternalSyntheticLambda15.run();
                        }
                    });
                    break;
                } else {
                    Log.e("Screenshot", "Failed to capture current screenshot for scroll transition!");
                    break;
                }
            default:
                LegacyScreenshotController legacyScreenshotController2 = this.f$0;
                final ScrollCaptureResponse scrollCaptureResponse2 = this.f$2;
                UserHandle userHandle2 = this.f$1;
                final ScrollCaptureExecutor scrollCaptureExecutor = legacyScreenshotController2.mScrollCaptureExecutor;
                final LegacyScreenshotController$$ExternalSyntheticLambda9 legacyScreenshotController$$ExternalSyntheticLambda9 = new LegacyScreenshotController$$ExternalSyntheticLambda9(legacyScreenshotController2, userHandle2, i);
                ScreenshotShelfViewProxy screenshotShelfViewProxy2 = legacyScreenshotController2.mViewProxy;
                Objects.requireNonNull(screenshotShelfViewProxy2);
                final LegacyScreenshotController$$ExternalSyntheticLambda0 legacyScreenshotController$$ExternalSyntheticLambda0 = new LegacyScreenshotController$$ExternalSyntheticLambda0(screenshotShelfViewProxy2, 3);
                ScreenshotShelfViewProxy screenshotShelfViewProxy3 = legacyScreenshotController2.mViewProxy;
                Objects.requireNonNull(screenshotShelfViewProxy3);
                final LegacyScreenshotController$$ExternalSyntheticLambda19 legacyScreenshotController$$ExternalSyntheticLambda19 = new LegacyScreenshotController$$ExternalSyntheticLambda19(screenshotShelfViewProxy3);
                scrollCaptureExecutor.lastScrollCaptureResponse = null;
                CallbackToFutureAdapter.SafeFuture safeFuture = scrollCaptureExecutor.longScreenshotFuture;
                if (safeFuture != null) {
                    safeFuture.cancel(true);
                }
                final ScrollCaptureController scrollCaptureController = scrollCaptureExecutor.scrollCaptureController;
                scrollCaptureController.mCancelled = false;
                final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureController$$ExternalSyntheticLambda0
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        final ScrollCaptureResponse scrollCaptureResponse3 = scrollCaptureResponse2;
                        final ScrollCaptureController scrollCaptureController2 = scrollCaptureController;
                        scrollCaptureController2.mCaptureCompleter = completer;
                        scrollCaptureController2.mWindowOwner = scrollCaptureResponse3.getPackageName();
                        CallbackToFutureAdapter.Completer completer2 = scrollCaptureController2.mCaptureCompleter;
                        ScrollCaptureController$$ExternalSyntheticLambda1 scrollCaptureController$$ExternalSyntheticLambda1 = new ScrollCaptureController$$ExternalSyntheticLambda1(scrollCaptureController2, 0);
                        Executor executor = scrollCaptureController2.mBgExecutor;
                        ResolvableFuture resolvableFuture = completer2.cancellationFuture;
                        if (resolvableFuture != null) {
                            resolvableFuture.addListener(scrollCaptureController$$ExternalSyntheticLambda1, executor);
                        }
                        scrollCaptureController2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureController$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScrollCaptureController scrollCaptureController3 = scrollCaptureController2;
                                final ScrollCaptureResponse scrollCaptureResponse4 = scrollCaptureResponse3;
                                final float f = Settings.Secure.getFloat(scrollCaptureController3.mContext.getContentResolver(), "screenshot.scroll_max_pages", 3.0f);
                                final ScrollCaptureClient scrollCaptureClient = scrollCaptureController3.mClient;
                                scrollCaptureClient.getClass();
                                final IScrollCaptureConnection connection = scrollCaptureResponse4.getConnection();
                                CallbackToFutureAdapter.SafeFuture future2 = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureClient$$ExternalSyntheticLambda0
                                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer3) {
                                        IScrollCaptureConnection iScrollCaptureConnection = connection;
                                        ScrollCaptureResponse scrollCaptureResponse5 = scrollCaptureResponse4;
                                        ScrollCaptureClient scrollCaptureClient2 = scrollCaptureClient;
                                        scrollCaptureClient2.getClass();
                                        if (iScrollCaptureConnection == null || !iScrollCaptureConnection.asBinder().isBinderAlive()) {
                                            completer3.setException(new DeadObjectException("No active connection!"));
                                            return "";
                                        }
                                        ScrollCaptureClient.SessionWrapper sessionWrapper = new ScrollCaptureClient.SessionWrapper(iScrollCaptureConnection, scrollCaptureResponse5.getWindowBounds(), scrollCaptureResponse5.getBoundsInWindow(), f, scrollCaptureClient2.mBgExecutor, 0);
                                        ImageReader imageReaderNewInstance = ImageReader.newInstance(sessionWrapper.mTileWidth, sessionWrapper.mTileHeight, 1, 30, 256L);
                                        sessionWrapper.mReader = imageReaderNewInstance;
                                        sessionWrapper.mStartCompleter = completer3;
                                        imageReaderNewInstance.setOnImageAvailableListenerWithExecutor(sessionWrapper, sessionWrapper.mBgExecutor);
                                        try {
                                            sessionWrapper.mCancellationSignal = sessionWrapper.mConnection.startCapture(sessionWrapper.mReader.getSurface(), sessionWrapper);
                                            ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0 scrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0 = new ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0(sessionWrapper, 0);
                                            ProfileInstallReceiver$$ExternalSyntheticLambda0 profileInstallReceiver$$ExternalSyntheticLambda0 = new ProfileInstallReceiver$$ExternalSyntheticLambda0();
                                            ResolvableFuture resolvableFuture2 = completer3.cancellationFuture;
                                            if (resolvableFuture2 != null) {
                                                resolvableFuture2.addListener(scrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0, profileInstallReceiver$$ExternalSyntheticLambda0);
                                            }
                                            sessionWrapper.mStarted = true;
                                            return "IScrollCaptureCallbacks#onCaptureStarted";
                                        } catch (RemoteException e) {
                                            sessionWrapper.mReader.close();
                                            completer3.setException(e);
                                            return "IScrollCaptureCallbacks#onCaptureStarted";
                                        }
                                    }
                                });
                                scrollCaptureController3.mSessionFuture = future2;
                                future2.delegate.addListener(new ScrollCaptureController$$ExternalSyntheticLambda1(scrollCaptureController3, 1), scrollCaptureController3.mContext.getMainExecutor());
                            }
                        });
                        return "<batch scroll capture>";
                    }
                });
                future.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.scroll.ScrollCaptureExecutor$executeBatchScrollCapture$1$1
                    /* JADX WARN: Removed duplicated region for block: B:13:0x002a  */
                    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
                    /* JADX WARN: Removed duplicated region for block: B:21:0x0048  */
                    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        Object obj;
                        Object failure;
                        Throwable thM3441exceptionOrNullimpl;
                        ScrollCaptureExecutor scrollCaptureExecutor2 = scrollCaptureExecutor;
                        ListenableFuture listenableFuture = future;
                        listenableFuture.getClass();
                        Runnable runnable = legacyScreenshotController$$ExternalSyntheticLambda0;
                        int i2 = ScrollCaptureExecutor.$r8$clinit;
                        scrollCaptureExecutor2.getClass();
                        ScrollCaptureController.LongScreenshot longScreenshot = null;
                        try {
                            int i3 = Result.$r8$clinit;
                            obj = listenableFuture.get();
                            try {
                                failure = Unit.INSTANCE;
                            } catch (Throwable th) {
                                th = th;
                                int i4 = Result.$r8$clinit;
                                failure = new Result.Failure(th);
                                thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                                if (thM3441exceptionOrNullimpl == null) {
                                }
                                if (longScreenshot == null) {
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            obj = null;
                        }
                        thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                        if (thM3441exceptionOrNullimpl == null) {
                            Log.e("ScrollCaptureExecutor", "Caught exception", thM3441exceptionOrNullimpl);
                            runnable.run();
                        } else {
                            ScrollCaptureController.LongScreenshot longScreenshot2 = (ScrollCaptureController.LongScreenshot) obj;
                            if (longScreenshot2 == null || longScreenshot2.mImageTileSet.getHeight() != 0) {
                                longScreenshot = longScreenshot2;
                            } else {
                                runnable.run();
                            }
                        }
                        if (longScreenshot == null) {
                            ScrollCaptureExecutor scrollCaptureExecutor3 = scrollCaptureExecutor;
                            Runnable runnable2 = legacyScreenshotController$$ExternalSyntheticLambda9;
                            ScrollCaptureExecutor.ScrollTransitionReady scrollTransitionReady = legacyScreenshotController$$ExternalSyntheticLambda19;
                            scrollCaptureExecutor3.longScreenshotHolder.mLongScreenshot.set(longScreenshot);
                            scrollCaptureExecutor3.longScreenshotHolder.mTransitionDestinationCallback.set(new ScrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1(scrollTransitionReady, longScreenshot));
                            runnable2.run();
                        }
                    }
                }, scrollCaptureExecutor.mainExecutor);
                scrollCaptureExecutor.longScreenshotFuture = future;
                break;
        }
    }

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda15(LegacyScreenshotController legacyScreenshotController, ScrollCaptureResponse scrollCaptureResponse, UserHandle userHandle) {
        this.f$0 = legacyScreenshotController;
        this.f$2 = scrollCaptureResponse;
        this.f$1 = userHandle;
    }
}
