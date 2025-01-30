package com.android.server.wm;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Pair;
import android.view.ContentRecordingSession;
import android.view.Display;
import android.view.IInputFilter;
import android.view.IWindow;
import android.view.InputChannel;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.inputmethod.ImeTracker;
import android.window.ScreenCapture;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.server.input.InputManagerService;
import com.android.server.wm.WindowManagerInternal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* loaded from: classes3.dex */
public abstract class WindowManagerInternal {

    public interface AccessibilityControllerInternal {

        public interface UiChangesForAccessibilityCallbacks {
            void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5);
        }

        void logTrace(String str, long j, String str2, byte[] bArr, int i, StackTraceElement[] stackTraceElementArr, long j2, int i2, long j3, Set set);

        void logTrace(String str, long j, String str2, byte[] bArr, int i, StackTraceElement[] stackTraceElementArr, Set set);

        void setUiChangesForAccessibilityCallbacks(UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks);

        void startTrace(long j);

        void stopTrace();
    }

    public abstract class AppTransitionListener {
        public void onAppTransitionCancelledLocked(boolean z) {
        }

        public void onAppTransitionFinishedLocked(IBinder iBinder) {
        }

        public void onAppTransitionPendingLocked() {
        }

        public int onAppTransitionStartingLocked(long j, long j2) {
            return 0;
        }

        public void onAppTransitionTimeoutLocked() {
        }
    }

    public interface KeyguardExitAnimationStartListener {
    }

    public interface MagnificationCallbacks {
        void onDisplaySizeChanged();

        void onImeWindowVisibilityChanged(boolean z);

        void onMagnificationRegionChanged(Region region);

        void onRectangleOnScreenRequested(int i, int i2, int i3, int i4);

        void onUserContextChanged();
    }

    public interface OnHardKeyboardStatusChangeListener {
        void onHardKeyboardStatusChange(boolean z);
    }

    public interface TaskSystemBarsListener {
        void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2);
    }

    public interface WindowsForAccessibilityCallback {
        void onWindowsForAccessibilityChanged(boolean z, int i, IBinder iBinder, List list);
    }

    public abstract void addFixedRefreshRatePackageInternal(String str, int i);

    public abstract void addRefreshRateRangeForPackage(String str, float f, float f2);

    public abstract void addTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage);

    public abstract void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle);

    public abstract void clearForcedDisplaySize(int i);

    public abstract void clearSnapshotCache();

    public abstract void computeWindowsForAccessibility(int i);

    public abstract void freezeDisplayRotation(int i, int i2);

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

    public abstract int getTopFocusedDisplayId();

    public abstract Context getTopFocusedDisplayUiContext();

    public abstract void getWindowFrame(IBinder iBinder, Rect rect);

    public abstract String getWindowName(IBinder iBinder);

    public abstract int getWindowOwnerUserId(IBinder iBinder);

    public abstract Pair getWindowTransformationMatrixAndMagnificationSpec(IBinder iBinder);

    public abstract int hasInputMethodClientFocus(IBinder iBinder, int i, int i2, int i3);

    public abstract void hideIme(IBinder iBinder, int i, ImeTracker.Token token);

    public abstract boolean isEmbeddedWindowType(IBinder iBinder);

    public abstract boolean isHardKeyboardAvailable();

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

    public abstract void moveWindowTokenToDisplay(IBinder iBinder, int i);

    public abstract void onDisplayManagerReceivedDeviceState(int i);

    public abstract ImeTargetInfo onToggleImeRequested(boolean z, IBinder iBinder, IBinder iBinder2, int i);

    public abstract void registerAppTransitionListener(AppTransitionListener appTransitionListener);

    public abstract void registerAppTransitionListener(AppTransitionListener appTransitionListener, int i);

    public abstract void registerDragDropControllerCallback(IDragDropCallback iDragDropCallback);

    public abstract void registerKeyguardExitAnimationStartListener(KeyguardExitAnimationStartListener keyguardExitAnimationStartListener);

    public abstract void registerTaskSystemBarsListener(TaskSystemBarsListener taskSystemBarsListener);

    public abstract void removeFixedRefreshRatePackageInternal(String str);

    public abstract void removeRefreshRateRangeForPackage(String str);

    public abstract void removeTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage);

    public abstract void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i);

    public abstract void reportPasswordChanged(int i);

    public abstract void requestTraversalFromDisplayManager();

    public abstract void requestWindowFocus(IBinder iBinder);

    public abstract void setAccessibilityIdToSurfaceMetadata(IBinder iBinder, int i);

    public abstract boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession);

    public abstract void setDeviceStateListener(DeviceStateManager.DeviceStateCallback deviceStateCallback);

    public abstract void setDismissImeOnBackKeyPressed(boolean z, boolean z2);

    public abstract void setDisplaySizeAndDensityInDex(int i, int i2, int i3, int i4);

    public abstract void setForceShowMagnifiableBounds(int i, boolean z);

    public abstract void setForcedDisplaySize(int i, int i2, int i3);

    public abstract void setInputFilter(IInputFilter iInputFilter);

    public abstract void setInputMethodTargetChangeListener(ImeTargetChangeListener imeTargetChangeListener);

    public abstract boolean setMagnificationCallbacks(int i, MagnificationCallbacks magnificationCallbacks);

    public abstract void setMagnificationSpec(int i, MagnificationSpec magnificationSpec);

    public abstract void setOnHardKeyboardStatusChangeListener(OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener);

    public abstract void setVr2dDisplayId(int i);

    public abstract void setWallpaperFoldedType(IBinder iBinder, boolean z);

    public abstract void setWallpaperShowWhenLocked(IBinder iBinder, boolean z);

    public abstract void setWindowsForAccessibilityCallback(int i, WindowsForAccessibilityCallback windowsForAccessibilityCallback);

    public abstract boolean shouldRestoreImeVisibility(IBinder iBinder);

    public abstract boolean shouldShowSystemDecorOnDisplay(int i);

    public abstract void showBootDialog(int i);

    public abstract void showGlobalActions();

    public abstract void showImePostLayout(IBinder iBinder, ImeTracker.Token token);

    public abstract ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot();

    public abstract Bitmap takeScreenshotToTargetWindowInternal(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2);

    public abstract void unregisterAppTransitionListener(AppTransitionListener appTransitionListener, int i);

    public abstract void unregisterTaskSystemBarsListener(TaskSystemBarsListener taskSystemBarsListener);

    public abstract void updateInputMethodTargetWindow(IBinder iBinder, IBinder iBinder2);

    public abstract void updateMirroredSurface(int i);

    public abstract void waitForAllWindowsDrawn(Message message, long j, int i);

    public interface IDragDropCallback {
        default void dragRecipientEntered(IWindow iWindow) {
        }

        default void dragRecipientExited(IWindow iWindow) {
        }

        default void postCancelDragAndDrop() {
        }

        default void postPerformDrag() {
        }

        default void postReportDropResult() {
        }

        default void preCancelDragAndDrop(IBinder iBinder) {
        }

        default boolean prePerformDrag(IWindow iWindow, IBinder iBinder, int i, float f, float f2, float f3, float f4, ClipData clipData) {
            return true;
        }

        default void preReportDropResult(IWindow iWindow, boolean z) {
        }

        default CompletableFuture registerInputChannel(final DragState dragState, Display display, final InputManagerService inputManagerService, final InputChannel inputChannel) {
            return dragState.register(display).thenApply(new Function() { // from class: com.android.server.wm.WindowManagerInternal$IDragDropCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$registerInputChannel$0;
                    lambda$registerInputChannel$0 = WindowManagerInternal.IDragDropCallback.lambda$registerInputChannel$0(InputManagerService.this, inputChannel, dragState, (Void) obj);
                    return lambda$registerInputChannel$0;
                }
            });
        }

        static /* synthetic */ Boolean lambda$registerInputChannel$0(InputManagerService inputManagerService, InputChannel inputChannel, DragState dragState, Void r3) {
            return Boolean.valueOf(inputManagerService.transferTouchFocus(inputChannel, dragState.getInputChannel(), true));
        }
    }

    public final void removeWindowToken(IBinder iBinder, boolean z, int i) {
        removeWindowToken(iBinder, z, true, i);
    }

    public class ImeTargetInfo {
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
}
