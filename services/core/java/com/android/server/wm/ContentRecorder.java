package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.projection.IMediaProjectionManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Slog;
import android.view.ContentRecordingSession;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.WindowManager;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class ContentRecorder implements WindowContainerListener {
    public final boolean mCorrectForAnisotropicPixels;
    public final DisplayContent mDisplayContent;
    public final MediaProjectionManagerWrapper mMediaProjectionManager;
    public ContentRecordingSession mContentRecordingSession = null;
    public WindowContainer mRecordedWindowContainer = null;
    public SurfaceControl mRecordedSurface = null;
    public Rect mLastRecordedBounds = null;
    public final Point mLastConsumingSurfaceSize = new Point(0, 0);
    public int mLastOrientation = 0;
    public int mLastWindowingMode = 0;

    interface MediaProjectionManagerWrapper {
        void notifyActiveProjectionCapturedContentResized(int i, int i2);

        void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z);

        void notifyWindowingModeChanged(int i, int i2, int i3);

        void stopActiveProjection();
    }

    public final class RemoteMediaProjectionManagerWrapper
            implements MediaProjectionManagerWrapper {
        public final int mDisplayId;
        public IMediaProjectionManager mIMediaProjectionManager = null;

        public RemoteMediaProjectionManagerWrapper(int i) {
            this.mDisplayId = i;
        }

        public final void fetchMediaProjectionManager() {
            IBinder service;
            if (this.mIMediaProjectionManager == null
                    && (service = ServiceManager.getService("media_projection")) != null) {
                this.mIMediaProjectionManager = IMediaProjectionManager.Stub.asInterface(service);
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public final void notifyActiveProjectionCapturedContentResized(int i, int i2) {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.notifyActiveProjectionCapturedContentResized(i, i2);
            } catch (RemoteException e) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            6721270269112237694L,
                            0,
                            "Content Recording: Unable to tell MediaProjectionManagerService about"
                                + " resizing the active projection: %s",
                            String.valueOf(e));
                }
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public final void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
            } catch (RemoteException e) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            1600318776990120244L,
                            0,
                            "Content Recording: Unable to tell MediaProjectionManagerService about"
                                + " visibility change on the active projection: %s",
                            String.valueOf(e));
                }
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public final void notifyWindowingModeChanged(int i, int i2, int i3) {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.notifyWindowingModeChanged(i, i2, i3);
            } catch (RemoteException e) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            -1451477179301743956L,
                            0,
                            "Content Recording: Unable to tell log windowing mode change: %s",
                            String.valueOf(e));
                }
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public final void stopActiveProjection() {
            int i = this.mDisplayId;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled;
            fetchMediaProjectionManager();
            if (this.mIMediaProjectionManager == null) {
                return;
            }
            try {
                if (zArr[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            3197882223327917085L,
                            1,
                            "Content Recording: stopping active projection for display %d",
                            Long.valueOf(i));
                }
                this.mIMediaProjectionManager.stopActiveProjection();
            } catch (RemoteException e) {
                if (zArr[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            4391984931064789228L,
                            1,
                            "Content Recording: Unable to tell MediaProjectionManagerService to"
                                + " stop the active projection for display %d: %s",
                            Long.valueOf(i),
                            String.valueOf(e));
                }
            }
        }
    }

    public ContentRecorder(
            DisplayContent displayContent,
            MediaProjectionManagerWrapper mediaProjectionManagerWrapper,
            boolean z) {
        this.mDisplayContent = displayContent;
        this.mMediaProjectionManager = mediaProjectionManagerWrapper;
        this.mCorrectForAnisotropicPixels = z;
    }

    public final Point fetchSurfaceSizeIfPresent() {
        DisplayContent displayContent = this.mDisplayContent;
        Point displaySurfaceDefaultSize =
                displayContent.mWmService.mDisplayManagerInternal.getDisplaySurfaceDefaultSize(
                        displayContent.mDisplayId);
        if (displaySurfaceDefaultSize != null) {
            return displaySurfaceDefaultSize;
        }
        if (!ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1]) {
            return null;
        }
        ProtoLogImpl_54989576.v(
                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                2330946591287751995L,
                1,
                "Content Recording: Provided surface for recording on display %d is not present, so"
                    + " do not update the surface",
                Long.valueOf(displayContent.mDisplayId));
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0009, code lost:

       if (r0.getContentToRecord() == 1) goto L8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleStartRecordingFailed() {
        /*
            r4 = this;
            android.view.ContentRecordingSession r0 = r4.mContentRecordingSession
            if (r0 == 0) goto Lc
            int r0 = r0.getContentToRecord()
            r1 = 1
            if (r0 != r1) goto Lc
            goto Ld
        Lc:
            r1 = 0
        Ld:
            r4.unregisterListener()
            r0 = 0
            r4.mContentRecordingSession = r0
            com.android.server.wm.DisplayContent r2 = r4.mDisplayContent
            com.android.server.wm.WindowManagerService r2 = r2.mWmService
            com.android.server.wm.ContentRecordingController r3 = r2.mContentRecordingController
            r3.setContentRecordingSessionLocked(r0, r2)
            if (r1 == 0) goto L21
            r4.stopMediaProjection()
        L21:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ContentRecorder.handleStartRecordingFailed():void");
    }

    public final void invalidateForRecording(final boolean z) {
        WindowContainer windowContainer = this.mRecordedWindowContainer;
        if (windowContainer == null || !(windowContainer instanceof DisplayContent)) {
            return;
        }
        SystemProperties.set("debug.sf.hdr_capture", z ? "true" : "false");
        DisplayContent displayContent = (DisplayContent) this.mRecordedWindowContainer;
        final boolean[] zArr = {false};
        WindowManagerGlobalLock windowManagerGlobalLock = displayContent.mWmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                displayContent.forAllWindows(
                        new Consumer() { // from class:
                                         // com.android.server.wm.ContentRecorder$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i;
                                ContentRecorder contentRecorder = ContentRecorder.this;
                                boolean z2 = z;
                                boolean[] zArr2 = zArr;
                                WindowState windowState = (WindowState) obj;
                                contentRecorder.getClass();
                                if (windowState.isVisible()) {
                                    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                                    if (layoutParams != null) {
                                        i = layoutParams.getColorMode();
                                        if (i == 2 || i == 3) {
                                            Slog.i(
                                                    "ContentRecorder",
                                                    "isHdrColorMode w="
                                                            + windowState.getName()
                                                            + " colorMode="
                                                            + i);
                                        }
                                    } else {
                                        i = 0;
                                    }
                                    if (i == 2 || i == 3) {
                                        try {
                                            windowState.mClient.invalidateForScreenShot(z2);
                                            zArr2[0] = true;
                                        } catch (Exception e) {
                                            NandswapManager$$ExternalSyntheticOutline0.m(
                                                    e,
                                                    new StringBuilder("Exception "),
                                                    "ContentRecorder");
                                        }
                                    }
                                }
                            }
                        },
                        true);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (zArr[0] && z) {
            try {
                new CountDownLatch(1).await(50L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Slog.e("ContentRecorder", "InterruptedException " + e.getMessage());
            }
        }
    }

    public final boolean isCurrentlyRecording() {
        return (this.mContentRecordingSession == null || this.mRecordedSurface == null)
                ? false
                : true;
    }

    public final void onConfigurationChanged(int i, int i2) {
        if (!isCurrentlyRecording() || this.mLastRecordedBounds == null) {
            return;
        }
        WindowContainer windowContainer = this.mRecordedWindowContainer;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled;
        DisplayContent displayContent = this.mDisplayContent;
        if (windowContainer == null) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        -6620483833570774987L,
                        1,
                        "Content Recording: Unexpectedly null window container; unable to update"
                            + " recording for display %d",
                        Long.valueOf(displayContent.mDisplayId));
                return;
            }
            return;
        }
        if (this.mContentRecordingSession.getContentToRecord() == 1
                && this.mRecordedWindowContainer.asTask().inPinnedWindowingMode()) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        7226080178642957768L,
                        1,
                        "Content Recording: Display %d was already recording, but pause capture"
                            + " since the task is in PIP",
                        Long.valueOf(displayContent.mDisplayId));
            }
            pauseRecording();
            return;
        }
        int windowingMode = this.mRecordedWindowContainer.getWindowingMode();
        if (i2 != windowingMode) {
            this.mMediaProjectionManager.notifyWindowingModeChanged(
                    this.mContentRecordingSession.getContentToRecord(),
                    this.mContentRecordingSession.getTargetUid(),
                    windowingMode);
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    -311001578548807570L,
                    1,
                    "Content Recording: Display %d was already recording, so apply transformations"
                        + " if necessary",
                    Long.valueOf(displayContent.mDisplayId));
        }
        Rect bounds = this.mRecordedWindowContainer.getBounds();
        int i3 = this.mRecordedWindowContainer.getConfiguration().orientation;
        Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
        if (this.mLastRecordedBounds.equals(bounds)
                && i == i3
                && this.mLastConsumingSurfaceSize.equals(fetchSurfaceSizeIfPresent)) {
            return;
        }
        if (fetchSurfaceSizeIfPresent == null) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        8446758574558556540L,
                        17,
                        "Content Recording: Unable to update recording for display %d to new bounds"
                            + " %s and/or orientation %d and/or surface size %s, since the surface"
                            + " is not available.",
                        Long.valueOf(displayContent.mDisplayId),
                        String.valueOf(bounds),
                        Long.valueOf(i3),
                        String.valueOf(fetchSurfaceSizeIfPresent));
                return;
            }
            return;
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    2350883351096538149L,
                    17,
                    "Content Recording: Going ahead with updating recording for display %d to new"
                        + " bounds %s and/or orientation %d and/or surface size %s",
                    Long.valueOf(displayContent.mDisplayId),
                    String.valueOf(bounds),
                    Long.valueOf(i3),
                    String.valueOf(fetchSurfaceSizeIfPresent));
        }
        updateMirroredSurface(
                this.mRecordedWindowContainer.getSyncTransaction(),
                bounds,
                fetchSurfaceSizeIfPresent);
    }

    @Override // com.android.server.wm.ConfigurationContainerListener
    public final void onMergedOverrideConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(this.mLastOrientation, this.mLastWindowingMode);
        this.mLastOrientation = configuration.orientation;
        this.mLastWindowingMode = configuration.windowConfiguration.getWindowingMode();
    }

    @Override // com.android.server.wm.WindowContainerListener
    public final void onRemoved() {
        boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1];
        DisplayContent displayContent = this.mDisplayContent;
        if (z) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    7993045936648632984L,
                    1,
                    "Content Recording: Recorded task is removed, so stop recording on display %d",
                    Long.valueOf(displayContent.mDisplayId));
        }
        unregisterListener();
        this.mContentRecordingSession = null;
        WindowManagerService windowManagerService = displayContent.mWmService;
        windowManagerService.mContentRecordingController.setContentRecordingSessionLocked(
                null, windowManagerService);
        stopMediaProjection();
    }

    @Override // com.android.server.wm.WindowContainerListener
    public final void onVisibleRequestedChanged(boolean z) {
        if (!isCurrentlyRecording() || this.mLastRecordedBounds == null) {
            return;
        }
        this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
        if (this.mContentRecordingSession.getContentToRecord() == 1) {
            this.mRecordedWindowContainer
                    .getSyncTransaction()
                    .setVisibility(this.mRecordedSurface, z);
            this.mRecordedWindowContainer.scheduleAnimation();
        }
    }

    public final void pauseRecording() {
        if (this.mRecordedSurface == null) {
            return;
        }
        boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1];
        DisplayContent displayContent = this.mDisplayContent;
        if (z) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    -4320004054011530388L,
                    13,
                    "Content Recording: Display %d has content (%b) so pause recording",
                    Long.valueOf(displayContent.mDisplayId),
                    Boolean.valueOf(displayContent.mLastHasContent));
        }
        ((SurfaceControl.Transaction) displayContent.mWmService.mTransactionFactory.get())
                .remove(this.mRecordedSurface)
                .reparent(displayContent.getWindowingLayer(), displayContent.getSurfaceControl())
                .reparent(displayContent.mOverlayLayer, displayContent.getSurfaceControl())
                .apply();
        this.mRecordedSurface = null;
    }

    public final void stopMediaProjection() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    5951434375221687741L,
                    1,
                    "Content Recording: Stop MediaProjection on virtual display %d",
                    Long.valueOf(this.mDisplayContent.mDisplayId));
        }
        MediaProjectionManagerWrapper mediaProjectionManagerWrapper = this.mMediaProjectionManager;
        if (mediaProjectionManagerWrapper != null) {
            mediaProjectionManagerWrapper.stopActiveProjection();
        }
    }

    public final void unregisterListener() {
        ContentRecordingSession contentRecordingSession;
        WindowContainer windowContainer = this.mRecordedWindowContainer;
        Task asTask = windowContainer != null ? windowContainer.asTask() : null;
        if (asTask == null
                || (contentRecordingSession = this.mContentRecordingSession) == null
                || contentRecordingSession.getContentToRecord() != 1) {
            return;
        }
        asTask.unregisterWindowContainerListener(this);
        this.mRecordedWindowContainer = null;
    }

    public void updateMirroredSurface(
            SurfaceControl.Transaction transaction, Rect rect, Point point) {
        PointF pointF;
        int i;
        int i2;
        DisplayInfo displayInfo = this.mRecordedWindowContainer.mDisplayContent.mDisplayInfo;
        DisplayInfo displayInfo2 = this.mDisplayContent.mDisplayInfo;
        PointF pointF2 = new PointF();
        int width = rect.width();
        int height = rect.height();
        float f = displayInfo.physicalXDpi;
        float f2 = displayInfo.physicalYDpi;
        int i3 = point.x;
        int i4 = point.y;
        float f3 = displayInfo2.physicalXDpi;
        float f4 = displayInfo2.physicalYDpi;
        float f5 = (f2 / f) / (f4 / f3);
        if (!this.mCorrectForAnisotropicPixels || (f5 > 0.975f && f5 < 1.025f)) {
            float min = Math.min(i3 / width, i4 / height);
            pointF2.x = min;
            pointF2.y = min;
        } else {
            float f6 = f3 / f;
            float f7 = f4 / f2;
            float min2 = Math.min((i3 / f6) / width, (i4 / f7) / height);
            pointF2.x = f6 * min2;
            pointF2.y = min2 * f7;
        }
        int round = Math.round(pointF2.x * rect.width());
        int round2 = Math.round(pointF2.y * rect.height());
        int i5 = point.x;
        int i6 = round != i5 ? (i5 - round) / 2 : 0;
        int i7 = point.y;
        int i8 = round2 != i7 ? (i7 - round2) / 2 : 0;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1]) {
            i2 = i8;
            i = i6;
            pointF = pointF2;
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                    1100676037289065396L,
                    1398181,
                    "Content Recording: Apply transformations of shift %d x %d, scale %f x %f, crop"
                        + " (aka recorded content size) %d x %d for display %d; display has size %d"
                        + " x %d; surface has size %d x %d",
                    Long.valueOf(i6),
                    Long.valueOf(i8),
                    Double.valueOf(pointF2.x),
                    Double.valueOf(pointF2.y),
                    Long.valueOf(rect.width()),
                    Long.valueOf(rect.height()),
                    Long.valueOf(r3.mDisplayId),
                    Long.valueOf(r3.getConfiguration().screenWidthDp),
                    Long.valueOf(r3.getConfiguration().screenHeightDp),
                    Long.valueOf(point.x),
                    Long.valueOf(point.y));
        } else {
            pointF = pointF2;
            i = i6;
            i2 = i8;
        }
        PointF pointF3 = pointF;
        transaction
                .setWindowCrop(this.mRecordedSurface, rect.width(), rect.height())
                .setMatrix(
                        this.mRecordedSurface,
                        pointF3.x,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        pointF3.y)
                .setPosition(this.mRecordedSurface, i, i2);
        Rect rect2 = new Rect(rect);
        this.mLastRecordedBounds = rect2;
        Point point2 = this.mLastConsumingSurfaceSize;
        point2.x = point.x;
        point2.y = point.y;
        this.mMediaProjectionManager.notifyActiveProjectionCapturedContentResized(
                rect2.width(), this.mLastRecordedBounds.height());
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateRecording() {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.ContentRecorder.updateRecording():void");
    }
}
