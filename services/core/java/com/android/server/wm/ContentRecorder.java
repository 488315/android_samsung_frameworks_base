package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.projection.IMediaProjectionManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.util.Slog;
import android.view.ContentRecordingSession;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class ContentRecorder implements WindowContainerListener {
    static final String KEY_RECORD_TASK_FEATURE = "record_task_content";
    public ContentRecordingSession mContentRecordingSession;
    public final DisplayContent mDisplayContent;
    public int mLastOrientation;
    public Rect mLastRecordedBounds;
    public final MediaProjectionManagerWrapper mMediaProjectionManager;
    public SurfaceControl mRecordedSurface;
    public WindowContainer mRecordedWindowContainer;

    interface MediaProjectionManagerWrapper {
        void notifyActiveProjectionCapturedContentResized(int i, int i2);

        void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z);

        void stopActiveProjection();
    }

    public ContentRecorder(DisplayContent displayContent) {
        this(displayContent, new RemoteMediaProjectionManagerWrapper());
    }

    public ContentRecorder(DisplayContent displayContent, MediaProjectionManagerWrapper mediaProjectionManagerWrapper) {
        this.mContentRecordingSession = null;
        this.mRecordedWindowContainer = null;
        this.mRecordedSurface = null;
        this.mLastRecordedBounds = null;
        this.mLastOrientation = 0;
        this.mDisplayContent = displayContent;
        this.mMediaProjectionManager = mediaProjectionManagerWrapper;
    }

    public void setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
        this.mContentRecordingSession = contentRecordingSession;
    }

    public boolean isContentRecordingSessionSet() {
        return this.mContentRecordingSession != null;
    }

    public boolean isCurrentlyRecording() {
        return (this.mContentRecordingSession == null || this.mRecordedSurface == null) ? false : true;
    }

    public void updateRecording() {
        if (isCurrentlyRecording() && (this.mDisplayContent.getLastHasContent() || this.mDisplayContent.getDisplayInfo().state == 1)) {
            pauseRecording();
        } else {
            startRecordingIfNeeded();
        }
    }

    public void onConfigurationChanged(int i) {
        if (!isCurrentlyRecording() || this.mLastRecordedBounds == null) {
            return;
        }
        if (this.mRecordedWindowContainer == null) {
            if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1156314529, 1, "Content Recording: Unexpectedly null window container; unable to update recording for display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
                return;
            }
            return;
        }
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 339482207, 1, "Content Recording: Display %d was already recording, so apply transformations if necessary", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
        }
        Rect bounds = this.mRecordedWindowContainer.getBounds();
        int orientation = this.mRecordedWindowContainer.getOrientation();
        if (this.mLastRecordedBounds.equals(bounds) && i == orientation) {
            return;
        }
        Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
        if (fetchSurfaceSizeIfPresent != null) {
            if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -452750194, 17, "Content Recording: Going ahead with updating recording for display %d to new bounds %s and/or orientation %d.", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId()), String.valueOf(bounds), Long.valueOf(orientation)});
            }
            updateMirroredSurface(this.mRecordedWindowContainer.getSyncTransaction(), bounds, fetchSurfaceSizeIfPresent);
            return;
        }
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1480264178, 17, "Content Recording: Unable to update recording for display %d to new bounds %s and/or orientation %d, since the surface is not available.", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId()), String.valueOf(bounds), Long.valueOf(orientation)});
        }
    }

    public void pauseRecording() {
        if (this.mRecordedSurface == null) {
            return;
        }
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -517666355, 13, "Content Recording: Display %d has content (%b) so pause recording", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId()), Boolean.valueOf(this.mDisplayContent.getLastHasContent())});
        }
        ((SurfaceControl.Transaction) this.mDisplayContent.mWmService.mTransactionFactory.get()).remove(this.mRecordedSurface).reparent(this.mDisplayContent.getWindowingLayer(), this.mDisplayContent.getSurfaceControl()).reparent(this.mDisplayContent.getOverlayLayer(), this.mDisplayContent.getSurfaceControl()).apply();
        this.mRecordedSurface = null;
    }

    public void stopRecording() {
        if (CoreRune.FW_SCREENSHOT_FOR_HDR) {
            invalidateForRecording(false);
        }
        unregisterListener();
        if (this.mRecordedSurface != null) {
            ((SurfaceControl.Transaction) this.mDisplayContent.mWmService.mTransactionFactory.get()).remove(this.mRecordedSurface).apply();
            this.mRecordedSurface = null;
            clearContentRecordingSession();
        }
    }

    public final void stopMediaProjection() {
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 612856628, 1, "Content Recording: Stop MediaProjection on virtual display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
        }
        MediaProjectionManagerWrapper mediaProjectionManagerWrapper = this.mMediaProjectionManager;
        if (mediaProjectionManagerWrapper != null) {
            mediaProjectionManagerWrapper.stopActiveProjection();
        }
    }

    public final void clearContentRecordingSession() {
        this.mContentRecordingSession = null;
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mWmService.mContentRecordingController.isValid(displayContent)) {
            WindowManagerService windowManagerService = this.mDisplayContent.mWmService;
            windowManagerService.mContentRecordingController.setContentRecordingSessionLocked(null, windowManagerService);
        }
    }

    public final void unregisterListener() {
        WindowContainer windowContainer = this.mRecordedWindowContainer;
        Task asTask = windowContainer != null ? windowContainer.asTask() : null;
        if (asTask == null || !isRecordingContentTask()) {
            return;
        }
        asTask.unregisterWindowContainerListener(this);
        this.mRecordedWindowContainer = null;
    }

    public final void startRecordingIfNeeded() {
        ContentRecordingSession contentRecordingSession;
        if (this.mDisplayContent.getLastHasContent() || isCurrentlyRecording()) {
            return;
        }
        if (this.mDisplayContent.getDisplayInfo().state == 1 || (contentRecordingSession = this.mContentRecordingSession) == null) {
            return;
        }
        if (contentRecordingSession.isWaitingForConsent()) {
            if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -125383273, 0, "Content Recording: waiting to record, so do nothing", (Object[]) null);
                return;
            }
            return;
        }
        WindowContainer retrieveRecordedWindowContainer = retrieveRecordedWindowContainer();
        this.mRecordedWindowContainer = retrieveRecordedWindowContainer;
        if (retrieveRecordedWindowContainer == null) {
            return;
        }
        Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
        if (fetchSurfaceSizeIfPresent == null) {
            if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1712935427, 1, "Content Recording: Unable to start recording for display %d since the surface is not available.", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
                return;
            }
            return;
        }
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1217596375, 5, "Content Recording: Display %d has no content and is on, so start recording for state %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId()), Long.valueOf(this.mDisplayContent.getDisplayInfo().state)});
        }
        if (CoreRune.FW_SCREENSHOT_FOR_HDR) {
            invalidateForRecording(true);
        }
        this.mRecordedSurface = SurfaceControl.mirrorSurface(this.mRecordedWindowContainer.getSurfaceControl());
        SurfaceControl.Transaction reparent = ((SurfaceControl.Transaction) this.mDisplayContent.mWmService.mTransactionFactory.get()).reparent(this.mRecordedSurface, this.mDisplayContent.getSurfaceControl()).reparent(this.mDisplayContent.getWindowingLayer(), null).reparent(this.mDisplayContent.getOverlayLayer(), null);
        updateMirroredSurface(reparent, this.mRecordedWindowContainer.getBounds(), fetchSurfaceSizeIfPresent);
        reparent.apply();
        if (this.mContentRecordingSession.getContentToRecord() == 1) {
            this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(this.mRecordedWindowContainer.asTask().isVisibleRequested());
        } else {
            this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(this.mRecordedWindowContainer.asDisplayContent().getDisplayInfo().state != 1);
        }
    }

    public final WindowContainer retrieveRecordedWindowContainer() {
        int contentToRecord = this.mContentRecordingSession.getContentToRecord();
        IBinder tokenToRecord = this.mContentRecordingSession.getTokenToRecord();
        if (contentToRecord == 0) {
            DisplayContent displayContent = this.mDisplayContent.mWmService.mRoot.getDisplayContent(this.mContentRecordingSession.getDisplayToRecord());
            if (displayContent != null) {
                return displayContent;
            }
            DisplayContent displayContent2 = this.mDisplayContent;
            displayContent2.mWmService.mDisplayManagerInternal.setWindowManagerMirroring(displayContent2.getDisplayId(), false);
            handleStartRecordingFailed();
            if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -732715767, 1, "Unable to retrieve window container to start recording for display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
            }
            return null;
        }
        if (contentToRecord == 1) {
            if (!DeviceConfig.getBoolean("window_manager", KEY_RECORD_TASK_FEATURE, false)) {
                handleStartRecordingFailed();
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1563836923, 1, "Content Recording: Unable to record task since feature is disabled %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
                }
                return null;
            }
            if (tokenToRecord == null) {
                handleStartRecordingFailed();
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1097851684, 1, "Content Recording: Unable to start recording due to null token for display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
                }
                return null;
            }
            Task asTask = WindowContainer.fromBinder(tokenToRecord).asTask();
            if (asTask == null) {
                handleStartRecordingFailed();
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -2074882083, 1, "Content Recording: Unable to retrieve task to start recording for display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
                }
            } else {
                asTask.registerWindowContainerListener(this);
            }
            return asTask;
        }
        handleStartRecordingFailed();
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -869242375, 1, "Content Recording: Unable to start recording due to invalid region for display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
        }
        return null;
    }

    public final void handleStartRecordingFailed() {
        boolean isRecordingContentTask = isRecordingContentTask();
        unregisterListener();
        clearContentRecordingSession();
        if (isRecordingContentTask) {
            stopMediaProjection();
        }
    }

    public void updateMirroredSurface(SurfaceControl.Transaction transaction, Rect rect, Point point) {
        float min = Math.min(point.x / rect.width(), point.y / rect.height());
        int round = Math.round(rect.width() * min);
        int round2 = Math.round(rect.height() * min);
        int i = point.x;
        int i2 = round != i ? (i - round) / 2 : 0;
        int i3 = point.y;
        int i4 = round2 != i3 ? (i3 - round2) / 2 : 0;
        transaction.setWindowCrop(this.mRecordedSurface, rect.width(), rect.height()).setMatrix(this.mRecordedSurface, min, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, min).setPosition(this.mRecordedSurface, i2, i4);
        Rect rect2 = new Rect(rect);
        this.mLastRecordedBounds = rect2;
        this.mMediaProjectionManager.notifyActiveProjectionCapturedContentResized(rect2.width(), this.mLastRecordedBounds.height());
        if (this.mDisplayContent.getDisplayInfo().type == 5) {
            Slog.d(StartingSurfaceController.TAG, "updateMirroredSurface: scale=" + min + ", x=" + i2 + ", y=" + i4 + ", bounds=" + rect + ", surfaceSize=" + point + ", " + this.mDisplayContent);
        }
    }

    public final Point fetchSurfaceSizeIfPresent() {
        DisplayContent displayContent = this.mDisplayContent;
        Point displaySurfaceDefaultSize = displayContent.mWmService.mDisplayManagerInternal.getDisplaySurfaceDefaultSize(displayContent.getDisplayId());
        if (displaySurfaceDefaultSize != null) {
            return displaySurfaceDefaultSize;
        }
        if (!ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            return null;
        }
        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1750878635, 1, "Content Recording: Provided surface for recording on display %d is not present, so do not update the surface", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
        return null;
    }

    @Override // com.android.server.wm.WindowContainerListener
    public void onRemoved() {
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 937080808, 1, "Content Recording: Recorded task is removed, so stop recording on display %d", new Object[]{Long.valueOf(this.mDisplayContent.getDisplayId())});
        }
        unregisterListener();
        clearContentRecordingSession();
        stopMediaProjection();
    }

    @Override // com.android.server.wm.ConfigurationContainerListener
    public void onMergedOverrideConfigurationChanged(Configuration configuration) {
        super.onMergedOverrideConfigurationChanged(configuration);
        onConfigurationChanged(this.mLastOrientation);
        this.mLastOrientation = configuration.orientation;
    }

    @Override // com.android.server.wm.WindowContainerListener
    public void onVisibleRequestedChanged(boolean z) {
        if (!isCurrentlyRecording() || this.mLastRecordedBounds == null) {
            return;
        }
        this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
    }

    public void updateMirroredSurfaceFromDisplayManager() {
        WindowContainer windowContainer;
        if (!isCurrentlyRecording() || (windowContainer = this.mRecordedWindowContainer) == null) {
            return;
        }
        Rect bounds = windowContainer.getBounds();
        Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
        if (fetchSurfaceSizeIfPresent != null) {
            Slog.d(StartingSurfaceController.TAG, "updateMirroredSurfaceFromDisplayManager: surfaceSize=" + fetchSurfaceSizeIfPresent + ", recordedContentBounds=" + bounds + ", " + this.mDisplayContent);
            updateMirroredSurface(this.mRecordedWindowContainer.getSyncTransaction(), bounds, fetchSurfaceSizeIfPresent);
        }
    }

    public final class RemoteMediaProjectionManagerWrapper implements MediaProjectionManagerWrapper {
        public IMediaProjectionManager mIMediaProjectionManager;

        public RemoteMediaProjectionManagerWrapper() {
            this.mIMediaProjectionManager = null;
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void stopActiveProjection() {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.stopActiveProjection();
            } catch (RemoteException e) {
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -88873335, 0, "Content Recording: Unable to tell MediaProjectionManagerService to stop the active projection: %s", new Object[]{String.valueOf(e)});
                }
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void notifyActiveProjectionCapturedContentResized(int i, int i2) {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.notifyActiveProjectionCapturedContentResized(i, i2);
            } catch (RemoteException e) {
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1661414284, 0, "Content Recording: Unable to tell MediaProjectionManagerService about resizing the active projection: %s", new Object[]{String.valueOf(e)});
                }
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
            fetchMediaProjectionManager();
            IMediaProjectionManager iMediaProjectionManager = this.mIMediaProjectionManager;
            if (iMediaProjectionManager == null) {
                return;
            }
            try {
                iMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
            } catch (RemoteException e) {
                if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
                    ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -180594244, 0, "Content Recording: Unable to tell MediaProjectionManagerService about visibility change on the active projection: %s", new Object[]{String.valueOf(e)});
                }
            }
        }

        public final void fetchMediaProjectionManager() {
            IBinder service;
            if (this.mIMediaProjectionManager == null && (service = ServiceManager.getService("media_projection")) != null) {
                this.mIMediaProjectionManager = IMediaProjectionManager.Stub.asInterface(service);
            }
        }
    }

    public final boolean isRecordingContentTask() {
        ContentRecordingSession contentRecordingSession = this.mContentRecordingSession;
        return contentRecordingSession != null && contentRecordingSession.getContentToRecord() == 1;
    }

    public final void invalidateForRecording(final boolean z) {
        SystemProperties.set("debug.sf.hdr_capture", z ? "true" : "false");
        DisplayContent displayContent = (DisplayContent) this.mRecordedWindowContainer;
        if (displayContent != null) {
            final boolean[] zArr = {false};
            WindowManagerGlobalLock windowManagerGlobalLock = displayContent.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.ContentRecorder$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ContentRecorder.this.lambda$invalidateForRecording$0(z, zArr, (WindowState) obj);
                        }
                    }, true);
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invalidateForRecording$0(boolean z, boolean[] zArr, WindowState windowState) {
        if (windowState.isVisible() && isHdrColorMode(windowState)) {
            try {
                windowState.mClient.invalidateForScreenShot(z);
                zArr[0] = true;
            } catch (Exception e) {
                Slog.e("ContentRecorder", "Exception " + e.getMessage());
            }
        }
    }

    public final boolean isHdrColorMode(WindowState windowState) {
        int i;
        if (windowState.getAttrs() != null) {
            i = windowState.getAttrs().getColorMode();
            if (i == 2 || i == 3) {
                Slog.i("ContentRecorder", "isHdrColorMode w=" + windowState.getName() + " colorMode=" + i);
            }
        } else {
            i = 0;
        }
        return i == 2 || i == 3;
    }
}
