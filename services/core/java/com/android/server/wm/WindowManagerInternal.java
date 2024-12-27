package com.android.server.wm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.ContentRecordingSession;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.IInputFilter;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.inputmethod.ImeTracker;
import android.window.ScreenCapture;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.input.InputManagerService;
import com.android.server.wallpaper.WallpaperCropper;
import com.android.server.wm.DragState;
import com.android.server.wm.DragState.InputInterceptor;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class WindowManagerInternal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AccessibilityControllerInternal {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public interface UiChangesForAccessibilityCallbacks {
            void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class AppTransitionListener {
        public void onAppTransitionCancelledLocked(boolean z) {
        }

        public void onAppTransitionFinishedLocked(IBinder iBinder) {
        }

        public void onAppTransitionPendingLocked() {
        }

        public void onAppTransitionStartingLocked(long j) {
        }

        public void onAppTransitionTimeoutLocked() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IDragDropCallback {
        static CompletableFuture registerInputChannel(final DragState dragState, Display display, final InputManagerService inputManagerService, final InputChannel inputChannel) {
            CompletableFuture completableFuture;
            MagnificationSpec magnificationSpec;
            display.getRealSize(dragState.mDisplaySize);
            Slog.d("WindowManager", "Registering drag input channel");
            if (dragState.mInputInterceptor != null) {
                Slog.e("WindowManager", "Duplicate register of drag input channel");
                completableFuture = CompletableFuture.completedFuture(null);
            } else {
                dragState.mInputInterceptor = dragState.new InputInterceptor(display);
                if (dragState.mInputSurface == null) {
                    dragState.mInputSurface = dragState.mService.makeSurfaceBuilder(dragState.mDisplayContent.mSession).setContainerLayer().setName("Drag and Drop Input Consumer").setCallsite("DragState.showInputSurface").setParent(dragState.mDisplayContent.mOverlayLayer).build();
                }
                DragState.InputInterceptor inputInterceptor = dragState.mInputInterceptor;
                InputWindowHandle inputWindowHandle = inputInterceptor == null ? null : inputInterceptor.mDragWindowHandle;
                if (inputWindowHandle == null) {
                    Slog.w("WindowManager", "Drag is in progress but there is no drag window handle.");
                    completableFuture = CompletableFuture.completedFuture(null);
                } else {
                    Rect rect = dragState.mTmpClipRect;
                    Point point = dragState.mDisplaySize;
                    rect.set(0, 0, point.x, point.y);
                    inputWindowHandle.setTrustedOverlay(dragState.mTransaction, dragState.mInputSurface, true);
                    dragState.mTransaction.show(dragState.mInputSurface).setInputWindowInfo(dragState.mInputSurface, inputWindowHandle).setLayer(dragState.mInputSurface, Integer.MAX_VALUE).setCrop(dragState.mInputSurface, dragState.mTmpClipRect);
                    if (dragState.mDisplayContent.hasOneHandOpSpec() && (magnificationSpec = dragState.mDisplayContent.mMagnificationSpec) != null) {
                        SurfaceControl.Transaction transaction = dragState.mTransaction;
                        SurfaceControl surfaceControl = dragState.mInputSurface;
                        float f = magnificationSpec.scale;
                        transaction.setMatrix(surfaceControl, f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f).setPosition(dragState.mInputSurface, magnificationSpec.offsetX, magnificationSpec.offsetY);
                    }
                    completableFuture = new CompletableFuture();
                    dragState.mTransaction.addWindowInfosReportedListener(new DragState$$ExternalSyntheticLambda2(1, completableFuture)).apply();
                }
            }
            return completableFuture.thenApply(new Function() { // from class: com.android.server.wm.WindowManagerInternal$IDragDropCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    InputManagerService inputManagerService2 = InputManagerService.this;
                    InputChannel inputChannel2 = inputChannel;
                    DragState.InputInterceptor inputInterceptor2 = dragState.mInputInterceptor;
                    return Boolean.valueOf(inputManagerService2.startDragAndDrop(inputChannel2, inputInterceptor2 == null ? null : inputInterceptor2.mClientChannel));
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImeTargetInfo {
        public final String focusedWindowName;
        public final String imeControlTargetName;
        public final String imeLayerTargetName;
        public final String imeSurfaceParentName;
        public final String requestWindowName;

        public ImeTargetInfo(String str, String str2, String str3, String str4, String str5) {
            this.focusedWindowName = str;
            this.requestWindowName = str2;
            this.imeControlTargetName = str3;
            this.imeLayerTargetName = str4;
            this.imeSurfaceParentName = str5;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface MagnificationCallbacks {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnHardKeyboardStatusChangeListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnImeRequestedChangedListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnWindowRemovedListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface TaskSystemBarsListener {
        void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface WindowsForAccessibilityCallback {
    }

    public abstract void addBlockScreenCaptureForApps(ArraySet arraySet);

    public abstract void addFixedRefreshRatePackageInternal(String str, int i);

    public abstract void addRefreshRateRangeForPackage(String str, float f, float f2);

    public abstract void addTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage);

    public abstract void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle);

    public abstract void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener);

    public abstract void clearBlockedApps();

    public abstract void clearDisplaySettings(String str, int i);

    public abstract void clearForcedDisplaySize(int i);

    public abstract void clearSnapshotCache();

    public abstract void computeWindowsForAccessibility(int i);

    public abstract void freezeDisplayRotation(int i, int i2, String str);

    public abstract SurfaceControl getA11yOverlayLayer(int i);

    public abstract AccessibilityControllerInternal getAccessibilityController();

    public abstract int getDisplayIdForWindow(IBinder iBinder);

    public abstract int getDisplayImePolicy(int i);

    public abstract IBinder getFocusedWindowToken();

    public abstract IBinder getFocusedWindowTokenFromWindowStates();

    public abstract SurfaceControl getHandwritingSurfaceForDisplay(int i);

    public abstract int[] getInitialDisplayProperties(int i);

    public abstract int getInputMethodWindowVisibleHeight(int i);

    public abstract KeyInterceptionInfo getKeyInterceptionInfoFromToken(IBinder iBinder);

    public abstract void getMagnificationRegion(int i, Region region);

    public abstract IBinder getTargetWindowTokenFromInputToken(IBinder iBinder);

    public abstract int getTopFocusedDisplayId();

    public abstract Context getTopFocusedDisplayUiContext();

    public abstract void getWindowFrame(IBinder iBinder, Rect rect);

    public abstract String getWindowName(IBinder iBinder);

    public abstract int getWindowOwnerUserId(IBinder iBinder);

    public abstract Pair getWindowTransformationMatrixAndMagnificationSpec(IBinder iBinder);

    public abstract int hasInputMethodClientFocus(IBinder iBinder, int i, int i2, int i3);

    public abstract boolean hasNavigationBar(int i);

    public abstract void hideIme(IBinder iBinder, int i, ImeTracker.Token token);

    public abstract boolean isEmbeddedWindowType(IBinder iBinder);

    public abstract boolean isHardKeyboardAvailable();

    public abstract boolean isHomeSupportedOnDisplay(int i);

    public abstract boolean isKeyguardLocked();

    public abstract boolean isKeyguardSecure(int i);

    public abstract boolean isKeyguardShowingAndNotOccluded();

    public abstract boolean isPointInsideWindow(IBinder iBinder, int i, float f, float f2);

    public abstract boolean isTouchOrFaketouchDevice();

    public abstract boolean isUidAllowedOnDisplay(int i, int i2);

    public abstract boolean isUidFocused(int i);

    public abstract void launchHomeForDesktopMode(int i);

    public abstract void lockNow();

    public abstract void moveDisplayToTopIfAllowed(int i);

    public abstract boolean moveFocusToAdjacentEmbeddedActivityIfNeeded();

    public abstract void moveWindowTokenToDisplay(IBinder iBinder, int i);

    public abstract void onDisplayManagerReceivedDeviceState(int i);

    public abstract ImeTargetInfo onToggleImeRequested(boolean z, IBinder iBinder, IBinder iBinder2, int i);

    public abstract void registerAppTransitionListener(AppTransitionListener appTransitionListener);

    public abstract void registerAppTransitionListener(AppTransitionListener appTransitionListener, int i);

    public abstract void registerDragDropControllerCallback(IDragDropCallback iDragDropCallback);

    public abstract void registerOnWindowRemovedListener(OnWindowRemovedListener onWindowRemovedListener);

    public abstract void registerTaskSystemBarsListener(TaskSystemBarsListener taskSystemBarsListener);

    public abstract void removeBlockScreenCaptureForApps(ArraySet arraySet);

    public abstract void removeFixedRefreshRatePackageInternal(String str);

    public abstract void removeRefreshRateRangeForPackage(String str);

    public abstract void removeTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage);

    public final void removeWindowToken(IBinder iBinder, boolean z, int i) {
        removeWindowToken(iBinder, z, true, i);
    }

    public abstract void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i);

    public abstract void reportPasswordChanged(int i);

    public abstract void requestTraversalFromDisplayManager();

    public abstract void requestWindowFocus(IBinder iBinder);

    public abstract void setAccessibilityIdToSurfaceMetadata(IBinder iBinder, int i);

    public abstract void setBlockScreenCaptureForAppsSessionId(long j);

    public abstract boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession);

    public abstract void setDeviceStateListener(DeviceStateManager.DeviceStateCallback deviceStateCallback);

    public abstract void setDismissImeOnBackKeyPressed(boolean z, boolean z2);

    public abstract void setDisplaySizeAndDensityInDex(int i, int i2, int i3, int i4);

    public abstract void setForcedDisplaySize(int i, int i2, int i3);

    public abstract void setFullscreenMagnificationActivated(int i, boolean z);

    public abstract void setHomeSupportedOnDisplay(String str, int i, boolean z);

    public abstract void setInputFilter(IInputFilter iInputFilter);

    public abstract void setInputMethodTargetChangeListener(ImeTargetChangeListener imeTargetChangeListener);

    public abstract boolean setMagnificationCallbacks(int i, MagnificationCallbacks magnificationCallbacks);

    public abstract void setMagnificationSpec(int i, MagnificationSpec magnificationSpec);

    public abstract void setOnHardKeyboardStatusChangeListener(OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener);

    public abstract void setOnImeRequestedChangedListener(OnImeRequestedChangedListener onImeRequestedChangedListener);

    public abstract void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2);

    public abstract void setVr2dDisplayId(int i);

    public abstract void setWallpaperCropHints(IBinder iBinder, SparseArray sparseArray);

    public abstract void setWallpaperCropUtils(WallpaperCropper.WallpaperCropUtils wallpaperCropUtils);

    public abstract void setWallpaperDisplayAddress(IBinder iBinder, DisplayAddress displayAddress);

    public abstract void setWallpaperShowWhenLocked(IBinder iBinder, boolean z);

    public abstract void setWindowsForAccessibilityCallback(int i, WindowsForAccessibilityCallback windowsForAccessibilityCallback);

    public abstract boolean shouldRestoreImeVisibility(IBinder iBinder);

    public abstract boolean shouldStayAwakeOnFold();

    public abstract void showBootDialog(int i);

    public abstract void showGlobalActions();

    public abstract void showImePostLayout(IBinder iBinder, ImeTracker.Token token);

    public abstract ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot(Set set);

    public abstract Bitmap takeScreenshotToTargetWindowInternal(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2);

    public abstract void unregisterAppTransitionListener(AppTransitionListener appTransitionListener, int i);

    public abstract void unregisterOnWindowRemovedListener(OnWindowRemovedListener onWindowRemovedListener);

    public abstract void unregisterTaskSystemBarsListener(TaskSystemBarsListener taskSystemBarsListener);

    public abstract void updateInputMethodTargetWindow(IBinder iBinder, IBinder iBinder2);

    public abstract void updateMirroredSurface(int i);

    public abstract void waitForAllWindowsDrawn(Message message, long j, int i);
}
