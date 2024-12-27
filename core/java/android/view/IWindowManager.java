package android.view;

import android.Manifest;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.VerifiedDisplayHash;
import android.window.AddToSurfaceSyncGroupResult;
import android.window.IGlobalDragListener;
import android.window.IScreenRecordingCallback;
import android.window.ISurfaceSyncGroupCompletedListener;
import android.window.ITaskFpsCallback;
import android.window.ITrustedPresentationListener;
import android.window.InputTransferToken;
import android.window.ScreenCapture;
import android.window.TrustedPresentationThresholds;
import android.window.WindowContextInfo;

import com.android.internal.os.IResultReceiver;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardLockedStateListener;
import com.android.internal.policy.IShortcutService;

import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener;
import com.samsung.android.onehandop.IOneHandOpWatcher;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;
import com.samsung.android.view.ScreenshotResult;
import com.samsung.android.view.SemWindowManager;

import java.util.List;

/* loaded from: classes4.dex */
public interface IWindowManager extends IInterface {
    public static final int FIXED_TO_USER_ROTATION_DEFAULT = 0;
    public static final int FIXED_TO_USER_ROTATION_DISABLED = 1;
    public static final int FIXED_TO_USER_ROTATION_ENABLED = 2;
    public static final int FIXED_TO_USER_ROTATION_IF_NO_AUTO_ROTATION = 3;

    void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener)
            throws RemoteException;

    SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) throws RemoteException;

    boolean addToSurfaceSyncGroup(
            IBinder iBinder,
            boolean z,
            ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener,
            AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult)
            throws RemoteException;

    void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException;

    WindowContextInfo attachWindowContextToDisplayArea(
            IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle)
            throws RemoteException;

    WindowContextInfo attachWindowContextToDisplayContent(
            IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException;

    WindowContextInfo attachWindowContextToWindowToken(
            IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2)
            throws RemoteException;

    void captureDisplay(
            int i,
            ScreenCapture.CaptureArgs captureArgs,
            ScreenCapture.ScreenCaptureListener screenCaptureListener)
            throws RemoteException;

    void changeDisplayScale(
            MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter)
            throws RemoteException;

    void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException;

    void clearForcedDisplaySize(int i) throws RemoteException;

    void clearForcedDisplaySizeDensity(int i) throws RemoteException;

    void clearKeyCustomizationInfoByAction(int i, int i2, int i3) throws RemoteException;

    void clearKeyCustomizationInfoByKeyCode(int i, int i2) throws RemoteException;

    boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException;

    void closeSystemDialogs(String str) throws RemoteException;

    void closeSystemDialogsInDisplay(String str, int i) throws RemoteException;

    void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel)
            throws RemoteException;

    boolean destroyInputConsumer(IBinder iBinder, int i) throws RemoteException;

    void detachWindowContext(IBinder iBinder) throws RemoteException;

    @Deprecated
    void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException;

    void dismissKeyguard(
            IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence)
            throws RemoteException;

    void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder)
            throws RemoteException;

    void dispatchSmartClipRemoteRequest(
            int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder)
            throws RemoteException;

    @Deprecated
    void endProlongedAnimations() throws RemoteException;

    void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException;

    boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner)
            throws RemoteException;

    void freezeDisplayRotation(int i, int i2, String str) throws RemoteException;

    void freezeRotation(int i, String str) throws RemoteException;

    float getAnimationScale(int i) throws RemoteException;

    float[] getAnimationScales() throws RemoteException;

    int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) throws RemoteException;

    List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList()
            throws RemoteException;

    int getBaseDisplayDensity(int i) throws RemoteException;

    void getBaseDisplaySize(int i, Point point) throws RemoteException;

    float getCurrentAnimatorScale() throws RemoteException;

    Region getCurrentImeTouchRegion() throws RemoteException;

    int getDefaultDisplayRotation() throws RemoteException;

    int getDisplayIdByUniqueId(String str) throws RemoteException;

    int getDisplayImePolicy(int i) throws RemoteException;

    int getDisplayUserRotation(int i) throws RemoteException;

    int getDockedStackSide() throws RemoteException;

    int getFullScreenAppsSupportMode() throws RemoteException;

    int getImeDisplayId() throws RemoteException;

    int getInitialDisplayDensity(int i) throws RemoteException;

    void getInitialDisplaySize(int i, Point point) throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3)
            throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(
            String str, int i, int i2) throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2)
            throws RemoteException;

    int getLetterboxBackgroundColorInArgb() throws RemoteException;

    @Deprecated
    int getMaxAspectRatioPolicy(String str, int i) throws RemoteException;

    void getOverrideStableInsets(int i, Rect rect) throws RemoteException;

    List<DisplayInfo> getPossibleDisplayInfo(int i) throws RemoteException;

    int getPreferredOptionsPanelGravity(int i) throws RemoteException;

    int getRemoveContentMode(int i) throws RemoteException;

    int getRotationLockOrientation(int i) throws RemoteException;

    void getStableInsets(int i, Rect rect) throws RemoteException;

    String[] getSupportedDisplayHashAlgorithms() throws RemoteException;

    int getSupportsFlexPanel(int i, String str) throws RemoteException;

    int getTopFocusedDisplayId() throws RemoteException;

    int getUserDisplayDensity() throws RemoteException;

    void getUserDisplaySize(Point point) throws RemoteException;

    List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList() throws RemoteException;

    WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException;

    boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) throws RemoteException;

    int getWindowingMode(int i) throws RemoteException;

    boolean hasNavigationBar(int i) throws RemoteException;

    boolean hasTaskbarTarget() throws RemoteException;

    void hideTransientBars(int i) throws RemoteException;

    void holdLock(IBinder iBinder, int i) throws RemoteException;

    boolean isDisplayRotationFrozen(int i) throws RemoteException;

    boolean isFolded() throws RemoteException;

    boolean isGlobalKey(int i) throws RemoteException;

    boolean isInTouchMode(int i) throws RemoteException;

    boolean isKeyguardLocked() throws RemoteException;

    boolean isKeyguardSecure(int i) throws RemoteException;

    boolean isKeyguardShowingAndNotOccluded() throws RemoteException;

    boolean isLayerTracing() throws RemoteException;

    boolean isLetterboxBackgroundMultiColored() throws RemoteException;

    boolean isMetaKeyEventRequested(ComponentName componentName) throws RemoteException;

    boolean isRotationFrozen() throws RemoteException;

    boolean isSafeModeEnabled() throws RemoteException;

    boolean isSystemKeyEventRequested(int i, ComponentName componentName) throws RemoteException;

    boolean isTableMode() throws RemoteException;

    boolean isTaskSnapshotSupported() throws RemoteException;

    boolean isTransitionTraceEnabled() throws RemoteException;

    boolean isViewServerRunning() throws RemoteException;

    boolean isWindowToken(IBinder iBinder) throws RemoteException;

    boolean isWindowTraceEnabled() throws RemoteException;

    void lockNow(Bundle bundle) throws RemoteException;

    void markSurfaceSyncGroupReady(IBinder iBinder) throws RemoteException;

    boolean mirrorDisplay(int i, SurfaceControl surfaceControl) throws RemoteException;

    SurfaceControl mirrorWallpaperSurface(int i) throws RemoteException;

    void moveDisplayToTop(int i, String str) throws RemoteException;

    List<ComponentName> notifyScreenshotListeners(int i) throws RemoteException;

    boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z)
            throws RemoteException;

    IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback)
            throws RemoteException;

    void overridePendingAppTransitionMultiThumbFuture(
            IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture,
            IRemoteCallback iRemoteCallback,
            boolean z,
            int i)
            throws RemoteException;

    void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i)
            throws RemoteException;

    void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo)
            throws RemoteException;

    @Deprecated
    void reenableKeyguard(IBinder iBinder, int i) throws RemoteException;

    void refreshScreenCaptureDisabled() throws RemoteException;

    void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener)
            throws RemoteException;

    boolean registerCrossWindowBlurEnabledListener(
            ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException;

    void registerDecorViewGestureListener(
            IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException;

    void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener)
            throws RemoteException;

    int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener)
            throws RemoteException;

    boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback)
            throws RemoteException;

    void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException;

    void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener)
            throws RemoteException;

    int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher)
            throws RemoteException;

    boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback)
            throws RemoteException;

    void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException;

    void registerSystemGestureExclusionListener(
            ISystemGestureExclusionListener iSystemGestureExclusionListener, int i)
            throws RemoteException;

    void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws RemoteException;

    void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) throws RemoteException;

    void registerTrustedPresentationListener(
            IBinder iBinder,
            ITrustedPresentationListener iTrustedPresentationListener,
            TrustedPresentationThresholds trustedPresentationThresholds,
            int i)
            throws RemoteException;

    boolean registerWallpaperVisibilityListener(
            IWallpaperVisibilityListener iWallpaperVisibilityListener, int i)
            throws RemoteException;

    void removeKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException;

    void removeKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException;

    void removeKeyguardLockedStateListener(
            IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException;

    void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException;

    void removeWindowToken(IBinder iBinder, int i) throws RemoteException;

    boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException;

    void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void requestMetaKeyEvent(ComponentName componentName, boolean z) throws RemoteException;

    void requestScrollCapture(
            int i,
            IBinder iBinder,
            int i2,
            IScrollCaptureResponseListener iScrollCaptureResponseListener)
            throws RemoteException;

    boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z)
            throws RemoteException;

    void restoreKeyCustomizationInfo(List<SemWindowManager.KeyCustomizationInfo> list)
            throws RemoteException;

    void saveWindowTraceToFile() throws RemoteException;

    Bitmap screenshotWallpaper() throws RemoteException;

    void setActiveTransactionTracing(boolean z) throws RemoteException;

    void setAnimationScale(int i, float f) throws RemoteException;

    void setAnimationScales(float[] fArr) throws RemoteException;

    void setAppContinuityMode(int i, String str, boolean z) throws RemoteException;

    void setDeadzoneHole(Bundle bundle) throws RemoteException;

    void setDisplayChangeWindowController(
            IDisplayChangeWindowController iDisplayChangeWindowController) throws RemoteException;

    void setDisplayColorToSystemProperties(int i) throws RemoteException;

    void setDisplayHashThrottlingEnabled(boolean z) throws RemoteException;

    void setDisplayImePolicy(int i, int i2) throws RemoteException;

    void setDisplayWindowInsetsController(
            int i, IDisplayWindowInsetsController iDisplayWindowInsetsController)
            throws RemoteException;

    void setDragSurfaceToOverlay(boolean z) throws RemoteException;

    void setEventDispatching(boolean z) throws RemoteException;

    void setFixedToUserRotation(int i, int i2) throws RemoteException;

    void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException;

    void setForcedDisplayScalingMode(int i, int i2) throws RemoteException;

    void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException;

    void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5)
            throws RemoteException;

    void setForcedDisplaySizeDensityWithInfo(
            MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo)
            throws RemoteException;

    void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException;

    void setIgnoreOrientationRequest(int i, boolean z) throws RemoteException;

    void setInTouchMode(boolean z, int i) throws RemoteException;

    void setInTouchModeOnAllDisplays(boolean z) throws RemoteException;

    void setLayerTracing(boolean z) throws RemoteException;

    void setLayerTracingFlags(int i) throws RemoteException;

    @Deprecated
    void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) throws RemoteException;

    void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException;

    void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent)
            throws RemoteException;

    void setRecentsAppBehindSystemBars(boolean z) throws RemoteException;

    void setRecentsVisibility(boolean z) throws RemoteException;

    void setRemoveContentMode(int i, int i2) throws RemoteException;

    void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) throws RemoteException;

    void setShouldShowSystemDecors(int i, boolean z) throws RemoteException;

    void setShouldShowWithInsecureKeyguard(int i, boolean z) throws RemoteException;

    void setStrictModeVisualIndicatorPreference(String str) throws RemoteException;

    void setSupportsFlexPanel(int i, String str, boolean z) throws RemoteException;

    void setSwitchingUser(boolean z) throws RemoteException;

    void setTableModeEnabled(boolean z) throws RemoteException;

    void setTaskSnapshotEnabled(boolean z) throws RemoteException;

    void setWindowingMode(int i, int i2) throws RemoteException;

    boolean shouldShowSystemDecors(int i) throws RemoteException;

    boolean shouldShowWithInsecureKeyguard(int i) throws RemoteException;

    void showGlobalActions() throws RemoteException;

    void showStrictModeViolation(boolean z) throws RemoteException;

    Bitmap snapshotTaskForRecents(int i) throws RemoteException;

    void startFreezingScreen(int i, int i2) throws RemoteException;

    void startLockscreenFingerprintAuth() throws RemoteException;

    boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i)
            throws RemoteException;

    void startSurfaceAnimation(IBinder iBinder, String str) throws RemoteException;

    void startTransitionTrace() throws RemoteException;

    boolean startViewServer(int i) throws RemoteException;

    void startWindowTrace() throws RemoteException;

    void stopFreezingScreen() throws RemoteException;

    void stopTransitionTrace() throws RemoteException;

    boolean stopViewServer() throws RemoteException;

    void stopWindowTrace() throws RemoteException;

    void syncInputTransactions(boolean z) throws RemoteException;

    ScreenshotResult takeScreenshotToTargetWindow(
            int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3)
            throws RemoteException;

    ScreenshotResult takeScreenshotToTargetWindowFromCapture(
            int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4)
            throws RemoteException;

    void thawDisplayRotation(int i, String str) throws RemoteException;

    void thawRotation(String str) throws RemoteException;

    boolean transferTouchGesture(
            InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2)
            throws RemoteException;

    void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener)
            throws RemoteException;

    void unregisterCrossWindowBlurEnabledListener(
            ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException;

    void unregisterDecorViewGestureListener(
            IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException;

    void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener)
            throws RemoteException;

    void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener)
            throws RemoteException;

    void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback)
            throws RemoteException;

    void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException;

    void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback)
            throws RemoteException;

    void unregisterSystemGestureExclusionListener(
            ISystemGestureExclusionListener iSystemGestureExclusionListener, int i)
            throws RemoteException;

    void unregisterSystemKeyEvent(int i, ComponentName componentName) throws RemoteException;

    void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) throws RemoteException;

    void unregisterTrustedPresentationListener(
            ITrustedPresentationListener iTrustedPresentationListener, int i)
            throws RemoteException;

    void unregisterWallpaperVisibilityListener(
            IWallpaperVisibilityListener iWallpaperVisibilityListener, int i)
            throws RemoteException;

    void updateDisplayWindowRequestedVisibleTypes(int i, int i2) throws RemoteException;

    void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) throws RemoteException;

    VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) throws RemoteException;

    int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException;

    public static class Default implements IWindowManager {
        @Override // android.view.IWindowManager
        public boolean startViewServer(int port) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean stopViewServer() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isViewServerRunning() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public IWindowSession openSession(IWindowSessionCallback callback) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void getInitialDisplaySize(int displayId, Point size) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void getBaseDisplaySize(int displayId, Point size) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setForcedDisplaySize(int displayId, int width, int height)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void clearForcedDisplaySize(int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getInitialDisplayDensity(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getBaseDisplayDensity(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayIdByUniqueId(String uniqueId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayDensityForUser(int displayId, int density, int userId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void clearForcedDisplayDensityForUser(int displayId, int userId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setForcedDisplayScalingMode(int displayId, int mode) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setEventDispatching(boolean enabled) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isWindowToken(IBinder binder) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void addWindowToken(IBinder token, int type, int displayId, Bundle options)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void removeWindowToken(IBinder token, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setDisplayChangeWindowController(IDisplayChangeWindowController controller)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public SurfaceControl addShellRoot(int displayId, IWindow client, int shellRootLayer)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setShellRootAccessibilityWindow(
                int displayId, int shellRootLayer, IWindow target) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionMultiThumbFuture(
                IAppTransitionAnimationSpecsFuture specsFuture,
                IRemoteCallback startedCallback,
                boolean scaleUp,
                int displayId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionRemote(
                RemoteAnimationAdapter remoteAnimationAdapter, int displayId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void endProlongedAnimations() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void startFreezingScreen(int exitAnim, int enterAnim) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void stopFreezingScreen() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void disableKeyguard(IBinder token, String tag, int userId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void reenableKeyguard(IBinder token, int userId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void exitKeyguardSecurely(IOnKeyguardExitResult callback) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isKeyguardLocked() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardSecure(int userId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void dismissKeyguard(IKeyguardDismissCallback callback, CharSequence message)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void addKeyguardLockedStateListener(IKeyguardLockedStateListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setSwitchingUser(boolean switching) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void closeSystemDialogs(String reason) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void closeSystemDialogsInDisplay(String reason, int displayId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public float getAnimationScale(int which) throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public float[] getAnimationScales() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setAnimationScale(int which, float scale) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setAnimationScales(float[] scales) throws RemoteException {}

        @Override // android.view.IWindowManager
        public float getCurrentAnimatorScale() throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public void setInTouchMode(boolean inTouch, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setInTouchModeOnAllDisplays(boolean inTouch) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isInTouchMode(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void showStrictModeViolation(boolean on) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setStrictModeVisualIndicatorPreference(String enabled) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void refreshScreenCaptureDisabled() throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getDefaultDisplayRotation() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayUserRotation(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int watchRotation(IRotationWatcher watcher, int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void removeRotationWatcher(IRotationWatcher watcher) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int registerProposedRotationListener(IBinder contextToken, IRotationWatcher listener)
                throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getPreferredOptionsPanelGravity(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void freezeRotation(int rotation, String caller) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void thawRotation(String caller) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isRotationFrozen() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void freezeDisplayRotation(int displayId, int rotation, String caller)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void thawDisplayRotation(int displayId, String caller) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isDisplayRotationFrozen(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setFixedToUserRotation(int displayId, int fixedToUserRotation)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setIgnoreOrientationRequest(int displayId, boolean ignoreOrientationRequest)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public Bitmap screenshotWallpaper() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public SurfaceControl mirrorWallpaperSurface(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean registerWallpaperVisibilityListener(
                IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterWallpaperVisibilityListener(
                IWallpaperVisibilityListener listener, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerSystemGestureExclusionListener(
                ISystemGestureExclusionListener listener, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterSystemGestureExclusionListener(
                ISystemGestureExclusionListener listener, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean requestAssistScreenshot(IAssistDataReceiver receiver)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void hideTransientBars(int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setRecentsVisibility(boolean visible) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void updateStaticPrivacyIndicatorBounds(int displayId, Rect[] staticBounds)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean enabled)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean hasNavigationBar(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void lockNow(Bundle options) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isSafeModeEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean clearWindowContentFrameStats(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public WindowContentFrameStats getWindowContentFrameStats(IBinder token)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getDockedStackSide() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void registerPinnedTaskListener(int displayId, IPinnedTaskListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void requestImeKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void getStableInsets(int displayId, Rect outInsets) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void getOverrideStableInsets(int displayId, Rect outInsets) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerShortcutKey(long shortcutCode, IShortcutService keySubscriber)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void createInputConsumer(
                IBinder token, String name, int displayId, InputChannel inputChannel)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean destroyInputConsumer(IBinder token, int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public Region getCurrentImeTouchRegion() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void registerDisplayFoldListener(IDisplayFoldListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterDisplayFoldListener(IDisplayFoldListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public int[] registerDisplayWindowListener(IDisplayWindowListener listener)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void unregisterDisplayWindowListener(IDisplayWindowListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void startWindowTrace() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void stopWindowTrace() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void saveWindowTraceToFile() throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isWindowTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void startTransitionTrace() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void stopTransitionTrace() throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isTransitionTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getWindowingMode(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setWindowingMode(int displayId, int mode) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getRemoveContentMode(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setRemoveContentMode(int displayId, int mode) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean shouldShowWithInsecureKeyguard(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean shouldShowSystemDecors(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setShouldShowSystemDecors(int displayId, boolean shouldShow)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getDisplayImePolicy(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setDisplayImePolicy(int displayId, int imePolicy) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void syncInputTransactions(boolean waitForAnimations) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isLayerTracing() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setLayerTracing(boolean enabled) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean mirrorDisplay(int displayId, SurfaceControl outSurfaceControl)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setDisplayWindowInsetsController(
                int displayId, IDisplayWindowInsetsController displayWindowInsetsController)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void updateDisplayWindowRequestedVisibleTypes(
                int displayId, int requestedVisibleTypes) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean getWindowInsets(int displayId, IBinder token, InsetsState outInsetsState)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public List<DisplayInfo> getPossibleDisplayInfo(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void showGlobalActions() throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setLayerTracingFlags(int flags) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setActiveTransactionTracing(boolean active) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void requestScrollCapture(
                int displayId,
                IBinder behindClient,
                int taskId,
                IScrollCaptureResponseListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void dispatchSmartClipRemoteRequest(
                int targetX,
                int targetY,
                SmartClipRemoteRequestInfo request,
                IBinder skipWindowToken)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void holdLock(IBinder token, int durationMs) throws RemoteException {}

        @Override // android.view.IWindowManager
        public String[] getSupportedDisplayHashAlgorithms() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setDisplayHashThrottlingEnabled(boolean enable) throws RemoteException {}

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToDisplayArea(
                IApplicationThread appThread,
                IBinder clientToken,
                int type,
                int displayId,
                Bundle options)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToWindowToken(
                IApplicationThread appThread, IBinder clientToken, IBinder token)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToDisplayContent(
                IApplicationThread appThread, IBinder clientToken, int displayId)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void detachWindowContext(IBinder clientToken) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean registerCrossWindowBlurEnabledListener(
                ICrossWindowBlurEnabledListener listener) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterCrossWindowBlurEnabledListener(
                ICrossWindowBlurEnabledListener listener) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isTaskSnapshotSupported() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getImeDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setTaskSnapshotEnabled(boolean enabled) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerTaskFpsCallback(int taskId, ITaskFpsCallback callback)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterTaskFpsCallback(ITaskFpsCallback listener) throws RemoteException {}

        @Override // android.view.IWindowManager
        public Bitmap snapshotTaskForRecents(int taskId) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setRecentsAppBehindSystemBars(boolean behindSystemBars)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList()
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getLetterboxBackgroundColorInArgb() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean isLetterboxBackgroundMultiColored() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void captureDisplay(
                int displayId,
                ScreenCapture.CaptureArgs captureArgs,
                ScreenCapture.ScreenCaptureListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isGlobalKey(int keyCode) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean addToSurfaceSyncGroup(
                IBinder syncGroupToken,
                boolean parentSyncGroupMerge,
                ISurfaceSyncGroupCompletedListener completedListener,
                AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void markSurfaceSyncGroupReady(IBinder syncGroupToken) throws RemoteException {}

        @Override // android.view.IWindowManager
        public List<ComponentName> notifyScreenshotListeners(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean replaceContentOnDisplay(int displayId, SurfaceControl sc)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerDecorViewGestureListener(
                IDecorViewGestureListener listener, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterDecorViewGestureListener(
                IDecorViewGestureListener listener, int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerTrustedPresentationListener(
                IBinder window,
                ITrustedPresentationListener listener,
                TrustedPresentationThresholds thresholds,
                int id)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterTrustedPresentationListener(
                ITrustedPresentationListener listener, int id) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean registerScreenRecordingCallback(IScreenRecordingCallback callback)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterScreenRecordingCallback(IScreenRecordingCallback callback)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback callback)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback callback)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setGlobalDragListener(IGlobalDragListener listener) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean transferTouchGesture(
                InputTransferToken transferFromToken, InputTransferToken transferToToken)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setDeadzoneHole(Bundle deadzoneHole) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getMaxAspectRatioPolicy(String pkg, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setMaxAspectRatioPolicy(String pkg, int uid, boolean enable, int restartTaskId)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public ScreenshotResult takeScreenshotToTargetWindow(
                int displayId,
                int targetWindowType,
                boolean containsTarget,
                Rect sourceCrop,
                int width,
                int height,
                boolean useIdentityTransform,
                boolean ignorePolicy)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public ScreenshotResult takeScreenshotToTargetWindowFromCapture(
                int displayId,
                int targetWindowType,
                boolean containsTarget,
                Rect sourceCrop,
                int width,
                int height,
                boolean useIdentityTransform,
                boolean ignorePolicy,
                boolean fromCapture)
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void getUserDisplaySize(Point point) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getUserDisplayDensity() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplaySizeDensity(int displayId) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setForcedDisplaySizeDensity(
                int displayId,
                int width,
                int height,
                int density,
                boolean saveToSettings,
                int forcedHideCutout)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo info)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getSupportsFlexPanel(int userId, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setSupportsFlexPanel(
                int userId, String packageName, boolean isSupportsFlexPanel)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getFullScreenAppsSupportMode() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void changeDisplayScale(
                MagnificationSpec spec, boolean registerInput, IInputFilter filter)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerOneHandOpWatcher(IOneHandOpWatcher watcher) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterOneHandOpWatcher(IOneHandOpWatcher watcher) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner runner, int displayId)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner runner)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getRotationLockOrientation(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean hasTaskbarTarget() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean requestSystemKeyEvent(
                int keyCode, ComponentName componentName, boolean request) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isSystemKeyEventRequested(int keyCode, ComponentName componentName)
                throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerSystemKeyEvent(int keyCode, ComponentName componentName, int press)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterSystemKeyEvent(int keyCode, ComponentName componentName)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void requestMetaKeyEvent(ComponentName componentName, boolean request)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isMetaKeyEventRequested(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void putKeyCustomizationInfo(
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(
                int id, int press, int keyCode) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(
                String ownerPackage, int press, int keyCode) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(
                int press, int keyCode) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void removeKeyCustomizationInfo(int id, int press, int keyCode)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void removeKeyCustomizationInfoByPackage(String ownerPackage, int press, int keyCode)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void clearKeyCustomizationInfoByKeyCode(int id, int keyCode)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void clearKeyCustomizationInfoByAction(int id, int keyCode, int action)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList()
                throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void restoreKeyCustomizationInfo(
                List<SemWindowManager.KeyCustomizationInfo> keyInfoArray) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean omniRequestAssistScreenshot(
                IAssistDataReceiver receiver, boolean appWindowOnly) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void dispatchSPenGestureEvent(
                int targetX, int targetY, InputEvent[] inputEvents, IBinder topToken)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent fillInIntent)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isKeyguardShowingAndNotOccluded() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void startLockscreenFingerprintAuth() throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getTopFocusedDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void moveDisplayToTop(int displayId, String reason) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setDragSurfaceToOverlay(boolean toOverlay) throws RemoteException {}

        @Override // android.view.IWindowManager
        public void startSurfaceAnimation(IBinder window, String args) throws RemoteException {}

        @Override // android.view.IWindowManager
        public boolean isFolded() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isTableMode() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setTableModeEnabled(boolean isTableModeEnabled) throws RemoteException {}

        @Override // android.view.IWindowManager
        public int getAppContinuityMode(int userId, String packageName, ActivityInfo aInfo)
                throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setAppContinuityMode(int userId, String packageName, boolean applied)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void registerAuthTouchEventListener(IAuthTouchEventListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void unregisterAuthTouchEventListener(IAuthTouchEventListener listener)
                throws RemoteException {}

        @Override // android.view.IWindowManager
        public void setDisplayColorToSystemProperties(int color) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWindowManager {
        public static final String DESCRIPTOR = "android.view.IWindowManager";
        static final int TRANSACTION_addKeyguardLockedStateListener = 33;
        static final int TRANSACTION_addShellRoot = 20;
        static final int TRANSACTION_addToSurfaceSyncGroup = 145;
        static final int TRANSACTION_addWindowToken = 17;
        static final int TRANSACTION_attachWindowContextToDisplayArea = 127;
        static final int TRANSACTION_attachWindowContextToDisplayContent = 129;
        static final int TRANSACTION_attachWindowContextToWindowToken = 128;
        static final int TRANSACTION_captureDisplay = 143;
        static final int TRANSACTION_changeDisplayScale = 172;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 13;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearForcedDisplaySizeDensity = 166;
        static final int TRANSACTION_clearKeyCustomizationInfoByAction = 192;
        static final int TRANSACTION_clearKeyCustomizationInfoByKeyCode = 191;
        static final int TRANSACTION_clearWindowContentFrameStats = 77;
        static final int TRANSACTION_closeSystemDialogs = 36;
        static final int TRANSACTION_closeSystemDialogsInDisplay = 37;
        static final int TRANSACTION_createInputConsumer = 86;
        static final int TRANSACTION_destroyInputConsumer = 87;
        static final int TRANSACTION_detachWindowContext = 130;
        static final int TRANSACTION_disableKeyguard = 27;
        static final int TRANSACTION_dismissKeyguard = 32;
        static final int TRANSACTION_dispatchSPenGestureEvent = 196;
        static final int TRANSACTION_dispatchSmartClipRemoteRequest = 122;
        static final int TRANSACTION_endProlongedAnimations = 24;
        static final int TRANSACTION_exitKeyguardSecurely = 29;
        static final int TRANSACTION_finishRemoteWallpaperAnimation = 176;
        static final int TRANSACTION_freezeDisplayRotation = 58;
        static final int TRANSACTION_freezeRotation = 55;
        static final int TRANSACTION_getAnimationScale = 38;
        static final int TRANSACTION_getAnimationScales = 39;
        static final int TRANSACTION_getAppContinuityMode = 207;
        static final int TRANSACTION_getBackupKeyCustomizationInfoList = 193;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 42;
        static final int TRANSACTION_getCurrentImeTouchRegion = 88;
        static final int TRANSACTION_getDefaultDisplayRotation = 49;
        static final int TRANSACTION_getDisplayIdByUniqueId = 11;
        static final int TRANSACTION_getDisplayImePolicy = 108;
        static final int TRANSACTION_getDisplayUserRotation = 50;
        static final int TRANSACTION_getDockedStackSide = 79;
        static final int TRANSACTION_getFullScreenAppsSupportMode = 171;
        static final int TRANSACTION_getImeDisplayId = 134;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getKeyCustomizationInfo = 186;
        static final int TRANSACTION_getKeyCustomizationInfoByPackage = 187;
        static final int TRANSACTION_getLastKeyCustomizationInfo = 188;
        static final int TRANSACTION_getLetterboxBackgroundColorInArgb = 141;
        static final int TRANSACTION_getMaxAspectRatioPolicy = 160;
        static final int TRANSACTION_getOverrideStableInsets = 84;
        static final int TRANSACTION_getPossibleDisplayInfo = 117;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 54;
        static final int TRANSACTION_getRemoveContentMode = 102;
        static final int TRANSACTION_getRotationLockOrientation = 177;
        static final int TRANSACTION_getStableInsets = 83;
        static final int TRANSACTION_getSupportedDisplayHashAlgorithms = 124;
        static final int TRANSACTION_getSupportsFlexPanel = 169;
        static final int TRANSACTION_getTopFocusedDisplayId = 200;
        static final int TRANSACTION_getUserDisplayDensity = 165;
        static final int TRANSACTION_getUserDisplaySize = 164;
        static final int TRANSACTION_getVisibleWindowInfoList = 140;
        static final int TRANSACTION_getWindowContentFrameStats = 78;
        static final int TRANSACTION_getWindowInsets = 116;
        static final int TRANSACTION_getWindowingMode = 100;
        static final int TRANSACTION_hasNavigationBar = 74;
        static final int TRANSACTION_hasTaskbarTarget = 178;
        static final int TRANSACTION_hideTransientBars = 70;
        static final int TRANSACTION_holdLock = 123;
        static final int TRANSACTION_isDisplayRotationFrozen = 60;
        static final int TRANSACTION_isFolded = 204;
        static final int TRANSACTION_isGlobalKey = 144;
        static final int TRANSACTION_isInTouchMode = 45;
        static final int TRANSACTION_isKeyguardLocked = 30;
        static final int TRANSACTION_isKeyguardSecure = 31;
        static final int TRANSACTION_isKeyguardShowingAndNotOccluded = 198;
        static final int TRANSACTION_isLayerTracing = 111;
        static final int TRANSACTION_isLetterboxBackgroundMultiColored = 142;
        static final int TRANSACTION_isMetaKeyEventRequested = 184;
        static final int TRANSACTION_isRotationFrozen = 57;
        static final int TRANSACTION_isSafeModeEnabled = 76;
        static final int TRANSACTION_isSystemKeyEventRequested = 180;
        static final int TRANSACTION_isTableMode = 205;
        static final int TRANSACTION_isTaskSnapshotSupported = 133;
        static final int TRANSACTION_isTransitionTraceEnabled = 99;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowToken = 16;
        static final int TRANSACTION_isWindowTraceEnabled = 96;
        static final int TRANSACTION_lockNow = 75;
        static final int TRANSACTION_markSurfaceSyncGroupReady = 146;
        static final int TRANSACTION_mirrorDisplay = 113;
        static final int TRANSACTION_mirrorWallpaperSurface = 64;
        static final int TRANSACTION_moveDisplayToTop = 201;
        static final int TRANSACTION_notifyScreenshotListeners = 147;
        static final int TRANSACTION_omniRequestAssistScreenshot = 195;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 22;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 23;
        static final int TRANSACTION_putKeyCustomizationInfo = 185;
        static final int TRANSACTION_reenableKeyguard = 28;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 48;
        static final int TRANSACTION_registerAuthTouchEventListener = 209;
        static final int TRANSACTION_registerCrossWindowBlurEnabledListener = 131;
        static final int TRANSACTION_registerDecorViewGestureListener = 149;
        static final int TRANSACTION_registerDisplayFoldListener = 89;
        static final int TRANSACTION_registerDisplayWindowListener = 91;
        static final int TRANSACTION_registerKnoxRemoteScreenCallback = 155;
        static final int TRANSACTION_registerOneHandOpWatcher = 173;
        static final int TRANSACTION_registerPinnedTaskListener = 80;
        static final int TRANSACTION_registerProposedRotationListener = 53;
        static final int TRANSACTION_registerScreenRecordingCallback = 153;
        static final int TRANSACTION_registerShortcutKey = 85;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 67;
        static final int TRANSACTION_registerSystemKeyEvent = 181;
        static final int TRANSACTION_registerTaskFpsCallback = 136;
        static final int TRANSACTION_registerTrustedPresentationListener = 151;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 65;
        static final int TRANSACTION_removeKeyCustomizationInfo = 189;
        static final int TRANSACTION_removeKeyCustomizationInfoByPackage = 190;
        static final int TRANSACTION_removeKeyguardLockedStateListener = 34;
        static final int TRANSACTION_removeRotationWatcher = 52;
        static final int TRANSACTION_removeWindowToken = 18;
        static final int TRANSACTION_replaceContentOnDisplay = 148;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 81;
        static final int TRANSACTION_requestAssistScreenshot = 69;
        static final int TRANSACTION_requestImeKeyboardShortcuts = 82;
        static final int TRANSACTION_requestMetaKeyEvent = 183;
        static final int TRANSACTION_requestScrollCapture = 121;
        static final int TRANSACTION_requestSystemKeyEvent = 179;
        static final int TRANSACTION_restoreKeyCustomizationInfo = 194;
        static final int TRANSACTION_saveWindowTraceToFile = 95;
        static final int TRANSACTION_screenshotWallpaper = 63;
        static final int TRANSACTION_setActiveTransactionTracing = 120;
        static final int TRANSACTION_setAnimationScale = 40;
        static final int TRANSACTION_setAnimationScales = 41;
        static final int TRANSACTION_setAppContinuityMode = 208;
        static final int TRANSACTION_setDeadzoneHole = 159;
        static final int TRANSACTION_setDisplayChangeWindowController = 19;
        static final int TRANSACTION_setDisplayColorToSystemProperties = 211;
        static final int TRANSACTION_setDisplayHashThrottlingEnabled = 126;
        static final int TRANSACTION_setDisplayImePolicy = 109;
        static final int TRANSACTION_setDisplayWindowInsetsController = 114;
        static final int TRANSACTION_setDragSurfaceToOverlay = 202;
        static final int TRANSACTION_setEventDispatching = 15;
        static final int TRANSACTION_setFixedToUserRotation = 61;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_setForcedDisplayScalingMode = 14;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setForcedDisplaySizeDensity = 167;
        static final int TRANSACTION_setForcedDisplaySizeDensityWithInfo = 168;
        static final int TRANSACTION_setGlobalDragListener = 157;
        static final int TRANSACTION_setIgnoreOrientationRequest = 62;
        static final int TRANSACTION_setInTouchMode = 43;
        static final int TRANSACTION_setInTouchModeOnAllDisplays = 44;
        static final int TRANSACTION_setLayerTracing = 112;
        static final int TRANSACTION_setLayerTracingFlags = 119;
        static final int TRANSACTION_setMaxAspectRatioPolicy = 161;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 73;
        static final int TRANSACTION_setPendingIntentAfterUnlock = 197;
        static final int TRANSACTION_setRecentsAppBehindSystemBars = 139;
        static final int TRANSACTION_setRecentsVisibility = 71;
        static final int TRANSACTION_setRemoveContentMode = 103;
        static final int TRANSACTION_setShellRootAccessibilityWindow = 21;
        static final int TRANSACTION_setShouldShowSystemDecors = 107;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 105;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 47;
        static final int TRANSACTION_setSupportsFlexPanel = 170;
        static final int TRANSACTION_setSwitchingUser = 35;
        static final int TRANSACTION_setTableModeEnabled = 206;
        static final int TRANSACTION_setTaskSnapshotEnabled = 135;
        static final int TRANSACTION_setWindowingMode = 101;
        static final int TRANSACTION_shouldShowSystemDecors = 106;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 104;
        static final int TRANSACTION_showGlobalActions = 118;
        static final int TRANSACTION_showStrictModeViolation = 46;
        static final int TRANSACTION_snapshotTaskForRecents = 138;
        static final int TRANSACTION_startFreezingScreen = 25;
        static final int TRANSACTION_startLockscreenFingerprintAuth = 199;
        static final int TRANSACTION_startRemoteWallpaperAnimation = 175;
        static final int TRANSACTION_startSurfaceAnimation = 203;
        static final int TRANSACTION_startTransitionTrace = 97;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 93;
        static final int TRANSACTION_stopFreezingScreen = 26;
        static final int TRANSACTION_stopTransitionTrace = 98;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 94;
        static final int TRANSACTION_syncInputTransactions = 110;
        static final int TRANSACTION_takeScreenshotToTargetWindow = 162;
        static final int TRANSACTION_takeScreenshotToTargetWindowFromCapture = 163;
        static final int TRANSACTION_thawDisplayRotation = 59;
        static final int TRANSACTION_thawRotation = 56;
        static final int TRANSACTION_transferTouchGesture = 158;
        static final int TRANSACTION_unregisterAuthTouchEventListener = 210;
        static final int TRANSACTION_unregisterCrossWindowBlurEnabledListener = 132;
        static final int TRANSACTION_unregisterDecorViewGestureListener = 150;
        static final int TRANSACTION_unregisterDisplayFoldListener = 90;
        static final int TRANSACTION_unregisterDisplayWindowListener = 92;
        static final int TRANSACTION_unregisterKnoxRemoteScreenCallback = 156;
        static final int TRANSACTION_unregisterOneHandOpWatcher = 174;
        static final int TRANSACTION_unregisterScreenRecordingCallback = 154;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 68;
        static final int TRANSACTION_unregisterSystemKeyEvent = 182;
        static final int TRANSACTION_unregisterTaskFpsCallback = 137;
        static final int TRANSACTION_unregisterTrustedPresentationListener = 152;
        static final int TRANSACTION_unregisterWallpaperVisibilityListener = 66;
        static final int TRANSACTION_updateDisplayWindowRequestedVisibleTypes = 115;
        static final int TRANSACTION_updateStaticPrivacyIndicatorBounds = 72;
        static final int TRANSACTION_verifyDisplayHash = 125;
        static final int TRANSACTION_watchRotation = 51;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IWindowManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWindowManager)) {
                return (IWindowManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startViewServer";
                case 2:
                    return "stopViewServer";
                case 3:
                    return "isViewServerRunning";
                case 4:
                    return "openSession";
                case 5:
                    return "getInitialDisplaySize";
                case 6:
                    return "getBaseDisplaySize";
                case 7:
                    return "setForcedDisplaySize";
                case 8:
                    return "clearForcedDisplaySize";
                case 9:
                    return "getInitialDisplayDensity";
                case 10:
                    return "getBaseDisplayDensity";
                case 11:
                    return "getDisplayIdByUniqueId";
                case 12:
                    return "setForcedDisplayDensityForUser";
                case 13:
                    return "clearForcedDisplayDensityForUser";
                case 14:
                    return "setForcedDisplayScalingMode";
                case 15:
                    return "setEventDispatching";
                case 16:
                    return "isWindowToken";
                case 17:
                    return "addWindowToken";
                case 18:
                    return "removeWindowToken";
                case 19:
                    return "setDisplayChangeWindowController";
                case 20:
                    return "addShellRoot";
                case 21:
                    return "setShellRootAccessibilityWindow";
                case 22:
                    return "overridePendingAppTransitionMultiThumbFuture";
                case 23:
                    return "overridePendingAppTransitionRemote";
                case 24:
                    return "endProlongedAnimations";
                case 25:
                    return "startFreezingScreen";
                case 26:
                    return "stopFreezingScreen";
                case 27:
                    return "disableKeyguard";
                case 28:
                    return "reenableKeyguard";
                case 29:
                    return "exitKeyguardSecurely";
                case 30:
                    return "isKeyguardLocked";
                case 31:
                    return "isKeyguardSecure";
                case 32:
                    return "dismissKeyguard";
                case 33:
                    return "addKeyguardLockedStateListener";
                case 34:
                    return "removeKeyguardLockedStateListener";
                case 35:
                    return "setSwitchingUser";
                case 36:
                    return "closeSystemDialogs";
                case 37:
                    return "closeSystemDialogsInDisplay";
                case 38:
                    return "getAnimationScale";
                case 39:
                    return "getAnimationScales";
                case 40:
                    return "setAnimationScale";
                case 41:
                    return "setAnimationScales";
                case 42:
                    return "getCurrentAnimatorScale";
                case 43:
                    return "setInTouchMode";
                case 44:
                    return "setInTouchModeOnAllDisplays";
                case 45:
                    return "isInTouchMode";
                case 46:
                    return "showStrictModeViolation";
                case 47:
                    return "setStrictModeVisualIndicatorPreference";
                case 48:
                    return "refreshScreenCaptureDisabled";
                case 49:
                    return "getDefaultDisplayRotation";
                case 50:
                    return "getDisplayUserRotation";
                case 51:
                    return "watchRotation";
                case 52:
                    return "removeRotationWatcher";
                case 53:
                    return "registerProposedRotationListener";
                case 54:
                    return "getPreferredOptionsPanelGravity";
                case 55:
                    return "freezeRotation";
                case 56:
                    return "thawRotation";
                case 57:
                    return "isRotationFrozen";
                case 58:
                    return "freezeDisplayRotation";
                case 59:
                    return "thawDisplayRotation";
                case 60:
                    return "isDisplayRotationFrozen";
                case 61:
                    return "setFixedToUserRotation";
                case 62:
                    return "setIgnoreOrientationRequest";
                case 63:
                    return "screenshotWallpaper";
                case 64:
                    return "mirrorWallpaperSurface";
                case 65:
                    return "registerWallpaperVisibilityListener";
                case 66:
                    return "unregisterWallpaperVisibilityListener";
                case 67:
                    return "registerSystemGestureExclusionListener";
                case 68:
                    return "unregisterSystemGestureExclusionListener";
                case 69:
                    return "requestAssistScreenshot";
                case 70:
                    return "hideTransientBars";
                case 71:
                    return "setRecentsVisibility";
                case 72:
                    return "updateStaticPrivacyIndicatorBounds";
                case 73:
                    return "setNavBarVirtualKeyHapticFeedbackEnabled";
                case 74:
                    return "hasNavigationBar";
                case 75:
                    return "lockNow";
                case 76:
                    return "isSafeModeEnabled";
                case 77:
                    return "clearWindowContentFrameStats";
                case 78:
                    return "getWindowContentFrameStats";
                case 79:
                    return "getDockedStackSide";
                case 80:
                    return "registerPinnedTaskListener";
                case 81:
                    return "requestAppKeyboardShortcuts";
                case 82:
                    return "requestImeKeyboardShortcuts";
                case 83:
                    return "getStableInsets";
                case 84:
                    return "getOverrideStableInsets";
                case 85:
                    return "registerShortcutKey";
                case 86:
                    return "createInputConsumer";
                case 87:
                    return "destroyInputConsumer";
                case 88:
                    return "getCurrentImeTouchRegion";
                case 89:
                    return "registerDisplayFoldListener";
                case 90:
                    return "unregisterDisplayFoldListener";
                case 91:
                    return "registerDisplayWindowListener";
                case 92:
                    return "unregisterDisplayWindowListener";
                case 93:
                    return "startWindowTrace";
                case 94:
                    return "stopWindowTrace";
                case 95:
                    return "saveWindowTraceToFile";
                case 96:
                    return "isWindowTraceEnabled";
                case 97:
                    return "startTransitionTrace";
                case 98:
                    return "stopTransitionTrace";
                case 99:
                    return "isTransitionTraceEnabled";
                case 100:
                    return "getWindowingMode";
                case 101:
                    return "setWindowingMode";
                case 102:
                    return "getRemoveContentMode";
                case 103:
                    return "setRemoveContentMode";
                case 104:
                    return "shouldShowWithInsecureKeyguard";
                case 105:
                    return "setShouldShowWithInsecureKeyguard";
                case 106:
                    return "shouldShowSystemDecors";
                case 107:
                    return "setShouldShowSystemDecors";
                case 108:
                    return "getDisplayImePolicy";
                case 109:
                    return "setDisplayImePolicy";
                case 110:
                    return "syncInputTransactions";
                case 111:
                    return "isLayerTracing";
                case 112:
                    return "setLayerTracing";
                case 113:
                    return "mirrorDisplay";
                case 114:
                    return "setDisplayWindowInsetsController";
                case 115:
                    return "updateDisplayWindowRequestedVisibleTypes";
                case 116:
                    return "getWindowInsets";
                case 117:
                    return "getPossibleDisplayInfo";
                case 118:
                    return "showGlobalActions";
                case 119:
                    return "setLayerTracingFlags";
                case 120:
                    return "setActiveTransactionTracing";
                case 121:
                    return "requestScrollCapture";
                case 122:
                    return "dispatchSmartClipRemoteRequest";
                case 123:
                    return "holdLock";
                case 124:
                    return "getSupportedDisplayHashAlgorithms";
                case 125:
                    return "verifyDisplayHash";
                case 126:
                    return "setDisplayHashThrottlingEnabled";
                case 127:
                    return "attachWindowContextToDisplayArea";
                case 128:
                    return "attachWindowContextToWindowToken";
                case 129:
                    return "attachWindowContextToDisplayContent";
                case 130:
                    return "detachWindowContext";
                case 131:
                    return "registerCrossWindowBlurEnabledListener";
                case 132:
                    return "unregisterCrossWindowBlurEnabledListener";
                case 133:
                    return "isTaskSnapshotSupported";
                case 134:
                    return "getImeDisplayId";
                case 135:
                    return "setTaskSnapshotEnabled";
                case 136:
                    return "registerTaskFpsCallback";
                case 137:
                    return "unregisterTaskFpsCallback";
                case 138:
                    return "snapshotTaskForRecents";
                case 139:
                    return "setRecentsAppBehindSystemBars";
                case 140:
                    return "getVisibleWindowInfoList";
                case 141:
                    return "getLetterboxBackgroundColorInArgb";
                case 142:
                    return "isLetterboxBackgroundMultiColored";
                case 143:
                    return "captureDisplay";
                case 144:
                    return "isGlobalKey";
                case 145:
                    return "addToSurfaceSyncGroup";
                case 146:
                    return "markSurfaceSyncGroupReady";
                case 147:
                    return "notifyScreenshotListeners";
                case 148:
                    return "replaceContentOnDisplay";
                case 149:
                    return "registerDecorViewGestureListener";
                case 150:
                    return "unregisterDecorViewGestureListener";
                case 151:
                    return "registerTrustedPresentationListener";
                case 152:
                    return "unregisterTrustedPresentationListener";
                case 153:
                    return "registerScreenRecordingCallback";
                case 154:
                    return "unregisterScreenRecordingCallback";
                case 155:
                    return "registerKnoxRemoteScreenCallback";
                case 156:
                    return "unregisterKnoxRemoteScreenCallback";
                case 157:
                    return "setGlobalDragListener";
                case 158:
                    return "transferTouchGesture";
                case 159:
                    return "setDeadzoneHole";
                case 160:
                    return "getMaxAspectRatioPolicy";
                case 161:
                    return "setMaxAspectRatioPolicy";
                case 162:
                    return "takeScreenshotToTargetWindow";
                case 163:
                    return "takeScreenshotToTargetWindowFromCapture";
                case 164:
                    return "getUserDisplaySize";
                case 165:
                    return "getUserDisplayDensity";
                case 166:
                    return "clearForcedDisplaySizeDensity";
                case 167:
                    return "setForcedDisplaySizeDensity";
                case 168:
                    return "setForcedDisplaySizeDensityWithInfo";
                case 169:
                    return "getSupportsFlexPanel";
                case 170:
                    return "setSupportsFlexPanel";
                case 171:
                    return "getFullScreenAppsSupportMode";
                case 172:
                    return "changeDisplayScale";
                case 173:
                    return "registerOneHandOpWatcher";
                case 174:
                    return "unregisterOneHandOpWatcher";
                case 175:
                    return "startRemoteWallpaperAnimation";
                case 176:
                    return "finishRemoteWallpaperAnimation";
                case 177:
                    return "getRotationLockOrientation";
                case 178:
                    return "hasTaskbarTarget";
                case 179:
                    return "requestSystemKeyEvent";
                case 180:
                    return "isSystemKeyEventRequested";
                case 181:
                    return "registerSystemKeyEvent";
                case 182:
                    return "unregisterSystemKeyEvent";
                case 183:
                    return "requestMetaKeyEvent";
                case 184:
                    return "isMetaKeyEventRequested";
                case 185:
                    return "putKeyCustomizationInfo";
                case 186:
                    return "getKeyCustomizationInfo";
                case 187:
                    return "getKeyCustomizationInfoByPackage";
                case 188:
                    return "getLastKeyCustomizationInfo";
                case 189:
                    return "removeKeyCustomizationInfo";
                case 190:
                    return "removeKeyCustomizationInfoByPackage";
                case 191:
                    return "clearKeyCustomizationInfoByKeyCode";
                case 192:
                    return "clearKeyCustomizationInfoByAction";
                case 193:
                    return "getBackupKeyCustomizationInfoList";
                case 194:
                    return "restoreKeyCustomizationInfo";
                case 195:
                    return "omniRequestAssistScreenshot";
                case 196:
                    return "dispatchSPenGestureEvent";
                case 197:
                    return "setPendingIntentAfterUnlock";
                case 198:
                    return "isKeyguardShowingAndNotOccluded";
                case 199:
                    return "startLockscreenFingerprintAuth";
                case 200:
                    return "getTopFocusedDisplayId";
                case 201:
                    return "moveDisplayToTop";
                case 202:
                    return "setDragSurfaceToOverlay";
                case 203:
                    return "startSurfaceAnimation";
                case 204:
                    return "isFolded";
                case 205:
                    return "isTableMode";
                case 206:
                    return "setTableModeEnabled";
                case 207:
                    return "getAppContinuityMode";
                case 208:
                    return "setAppContinuityMode";
                case 209:
                    return "registerAuthTouchEventListener";
                case 210:
                    return "unregisterAuthTouchEventListener";
                case 211:
                    return "setDisplayColorToSystemProperties";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = startViewServer(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    boolean _result2 = stopViewServer();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    boolean _result3 = isViewServerRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    IWindowSessionCallback _arg02 =
                            IWindowSessionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    IWindowSession _result4 = openSession(_arg02);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 5:
                    int _arg03 = data.readInt();
                    Point _arg1 = new Point();
                    data.enforceNoDataAvail();
                    getInitialDisplaySize(_arg03, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg1, 1);
                    return true;
                case 6:
                    int _arg04 = data.readInt();
                    Point _arg12 = new Point();
                    data.enforceNoDataAvail();
                    getBaseDisplaySize(_arg04, _arg12);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg12, 1);
                    return true;
                case 7:
                    int _arg05 = data.readInt();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    setForcedDisplaySize(_arg05, _arg13, _arg2);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    clearForcedDisplaySize(_arg06);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getInitialDisplayDensity(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 10:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result6 = getBaseDisplayDensity(_arg08);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 11:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = getDisplayIdByUniqueId(_arg09);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 12:
                    int _arg010 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    setForcedDisplayDensityForUser(_arg010, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 13:
                    int _arg011 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    clearForcedDisplayDensityForUser(_arg011, _arg15);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg012 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    setForcedDisplayScalingMode(_arg012, _arg16);
                    reply.writeNoException();
                    return true;
                case 15:
                    boolean _arg013 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setEventDispatching(_arg013);
                    reply.writeNoException();
                    return true;
                case 16:
                    IBinder _arg014 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result8 = isWindowToken(_arg014);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 17:
                    IBinder _arg015 = data.readStrongBinder();
                    int _arg17 = data.readInt();
                    int _arg23 = data.readInt();
                    Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    addWindowToken(_arg015, _arg17, _arg23, _arg3);
                    reply.writeNoException();
                    return true;
                case 18:
                    IBinder _arg016 = data.readStrongBinder();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    removeWindowToken(_arg016, _arg18);
                    reply.writeNoException();
                    return true;
                case 19:
                    IDisplayChangeWindowController _arg017 =
                            IDisplayChangeWindowController.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setDisplayChangeWindowController(_arg017);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg018 = data.readInt();
                    IWindow _arg19 = IWindow.Stub.asInterface(data.readStrongBinder());
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    SurfaceControl _result9 = addShellRoot(_arg018, _arg19, _arg24);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 21:
                    int _arg019 = data.readInt();
                    int _arg110 = data.readInt();
                    IWindow _arg25 = IWindow.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setShellRootAccessibilityWindow(_arg019, _arg110, _arg25);
                    reply.writeNoException();
                    return true;
                case 22:
                    IAppTransitionAnimationSpecsFuture _arg020 =
                            IAppTransitionAnimationSpecsFuture.Stub.asInterface(
                                    data.readStrongBinder());
                    IRemoteCallback _arg111 =
                            IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    boolean _arg26 = data.readBoolean();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    overridePendingAppTransitionMultiThumbFuture(_arg020, _arg111, _arg26, _arg32);
                    reply.writeNoException();
                    return true;
                case 23:
                    RemoteAnimationAdapter _arg021 =
                            (RemoteAnimationAdapter)
                                    data.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    overridePendingAppTransitionRemote(_arg021, _arg112);
                    reply.writeNoException();
                    return true;
                case 24:
                    endProlongedAnimations();
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg022 = data.readInt();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    startFreezingScreen(_arg022, _arg113);
                    reply.writeNoException();
                    return true;
                case 26:
                    stopFreezingScreen();
                    reply.writeNoException();
                    return true;
                case 27:
                    IBinder _arg023 = data.readStrongBinder();
                    String _arg114 = data.readString();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    disableKeyguard(_arg023, _arg114, _arg27);
                    reply.writeNoException();
                    return true;
                case 28:
                    IBinder _arg024 = data.readStrongBinder();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    reenableKeyguard(_arg024, _arg115);
                    reply.writeNoException();
                    return true;
                case 29:
                    IOnKeyguardExitResult _arg025 =
                            IOnKeyguardExitResult.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    exitKeyguardSecurely(_arg025);
                    reply.writeNoException();
                    return true;
                case 30:
                    boolean _result10 = isKeyguardLocked();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 31:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = isKeyguardSecure(_arg026);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 32:
                    IKeyguardDismissCallback _arg027 =
                            IKeyguardDismissCallback.Stub.asInterface(data.readStrongBinder());
                    CharSequence _arg116 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    dismissKeyguard(_arg027, _arg116);
                    reply.writeNoException();
                    return true;
                case 33:
                    IKeyguardLockedStateListener _arg028 =
                            IKeyguardLockedStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addKeyguardLockedStateListener(_arg028);
                    reply.writeNoException();
                    return true;
                case 34:
                    IKeyguardLockedStateListener _arg029 =
                            IKeyguardLockedStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeKeyguardLockedStateListener(_arg029);
                    reply.writeNoException();
                    return true;
                case 35:
                    boolean _arg030 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSwitchingUser(_arg030);
                    reply.writeNoException();
                    return true;
                case 36:
                    String _arg031 = data.readString();
                    data.enforceNoDataAvail();
                    closeSystemDialogs(_arg031);
                    reply.writeNoException();
                    return true;
                case 37:
                    String _arg032 = data.readString();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(_arg032, _arg117);
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    float _result12 = getAnimationScale(_arg033);
                    reply.writeNoException();
                    reply.writeFloat(_result12);
                    return true;
                case 39:
                    float[] _result13 = getAnimationScales();
                    reply.writeNoException();
                    reply.writeFloatArray(_result13);
                    return true;
                case 40:
                    int _arg034 = data.readInt();
                    float _arg118 = data.readFloat();
                    data.enforceNoDataAvail();
                    setAnimationScale(_arg034, _arg118);
                    reply.writeNoException();
                    return true;
                case 41:
                    float[] _arg035 = data.createFloatArray();
                    data.enforceNoDataAvail();
                    setAnimationScales(_arg035);
                    reply.writeNoException();
                    return true;
                case 42:
                    float _result14 = getCurrentAnimatorScale();
                    reply.writeNoException();
                    reply.writeFloat(_result14);
                    return true;
                case 43:
                    boolean _arg036 = data.readBoolean();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    setInTouchMode(_arg036, _arg119);
                    reply.writeNoException();
                    return true;
                case 44:
                    boolean _arg037 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInTouchModeOnAllDisplays(_arg037);
                    reply.writeNoException();
                    return true;
                case 45:
                    int _arg038 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = isInTouchMode(_arg038);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 46:
                    boolean _arg039 = data.readBoolean();
                    data.enforceNoDataAvail();
                    showStrictModeViolation(_arg039);
                    reply.writeNoException();
                    return true;
                case 47:
                    String _arg040 = data.readString();
                    data.enforceNoDataAvail();
                    setStrictModeVisualIndicatorPreference(_arg040);
                    reply.writeNoException();
                    return true;
                case 48:
                    refreshScreenCaptureDisabled();
                    reply.writeNoException();
                    return true;
                case 49:
                    int _result16 = getDefaultDisplayRotation();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 50:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result17 = getDisplayUserRotation(_arg041);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 51:
                    IRotationWatcher _arg042 =
                            IRotationWatcher.Stub.asInterface(data.readStrongBinder());
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result18 = watchRotation(_arg042, _arg120);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 52:
                    IBinder _arg043 = data.readStrongBinder();
                    IRotationWatcher _arg044 = IRotationWatcher.Stub.asInterface(_arg043);
                    data.enforceNoDataAvail();
                    removeRotationWatcher(_arg044);
                    reply.writeNoException();
                    return true;
                case 53:
                    IBinder _arg045 = data.readStrongBinder();
                    IRotationWatcher _arg121 =
                            IRotationWatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result19 = registerProposedRotationListener(_arg045, _arg121);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 54:
                    int _arg046 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result20 = getPreferredOptionsPanelGravity(_arg046);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 55:
                    int _arg047 = data.readInt();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    freezeRotation(_arg047, _arg122);
                    reply.writeNoException();
                    return true;
                case 56:
                    String _arg048 = data.readString();
                    data.enforceNoDataAvail();
                    thawRotation(_arg048);
                    reply.writeNoException();
                    return true;
                case 57:
                    boolean _result21 = isRotationFrozen();
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 58:
                    int _arg049 = data.readInt();
                    int _arg123 = data.readInt();
                    String _arg28 = data.readString();
                    data.enforceNoDataAvail();
                    freezeDisplayRotation(_arg049, _arg123, _arg28);
                    reply.writeNoException();
                    return true;
                case 59:
                    int _arg050 = data.readInt();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    thawDisplayRotation(_arg050, _arg124);
                    reply.writeNoException();
                    return true;
                case 60:
                    int _arg051 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result22 = isDisplayRotationFrozen(_arg051);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 61:
                    int _arg052 = data.readInt();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    setFixedToUserRotation(_arg052, _arg125);
                    reply.writeNoException();
                    return true;
                case 62:
                    int _arg053 = data.readInt();
                    boolean _arg126 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setIgnoreOrientationRequest(_arg053, _arg126);
                    reply.writeNoException();
                    return true;
                case 63:
                    Bitmap _result23 = screenshotWallpaper();
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 64:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    SurfaceControl _result24 = mirrorWallpaperSurface(_arg054);
                    reply.writeNoException();
                    reply.writeTypedObject(_result24, 1);
                    return true;
                case 65:
                    IWallpaperVisibilityListener _arg055 =
                            IWallpaperVisibilityListener.Stub.asInterface(data.readStrongBinder());
                    int _arg127 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result25 = registerWallpaperVisibilityListener(_arg055, _arg127);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 66:
                    IWallpaperVisibilityListener _arg056 =
                            IWallpaperVisibilityListener.Stub.asInterface(data.readStrongBinder());
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterWallpaperVisibilityListener(_arg056, _arg128);
                    reply.writeNoException();
                    return true;
                case 67:
                    ISystemGestureExclusionListener _arg057 =
                            ISystemGestureExclusionListener.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg129 = data.readInt();
                    data.enforceNoDataAvail();
                    registerSystemGestureExclusionListener(_arg057, _arg129);
                    reply.writeNoException();
                    return true;
                case 68:
                    ISystemGestureExclusionListener _arg058 =
                            ISystemGestureExclusionListener.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg130 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterSystemGestureExclusionListener(_arg058, _arg130);
                    reply.writeNoException();
                    return true;
                case 69:
                    IAssistDataReceiver _arg059 =
                            IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result26 = requestAssistScreenshot(_arg059);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 70:
                    int _arg060 = data.readInt();
                    data.enforceNoDataAvail();
                    hideTransientBars(_arg060);
                    return true;
                case 71:
                    boolean _arg061 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRecentsVisibility(_arg061);
                    return true;
                case 72:
                    int _arg062 = data.readInt();
                    Rect[] _arg131 = (Rect[]) data.createTypedArray(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    updateStaticPrivacyIndicatorBounds(_arg062, _arg131);
                    return true;
                case 73:
                    boolean _arg063 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setNavBarVirtualKeyHapticFeedbackEnabled(_arg063);
                    reply.writeNoException();
                    return true;
                case 74:
                    int _arg064 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result27 = hasNavigationBar(_arg064);
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 75:
                    Bundle _arg065 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    lockNow(_arg065);
                    reply.writeNoException();
                    return true;
                case 76:
                    boolean _result28 = isSafeModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 77:
                    IBinder _arg066 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result29 = clearWindowContentFrameStats(_arg066);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 78:
                    IBinder _arg067 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    WindowContentFrameStats _result30 = getWindowContentFrameStats(_arg067);
                    reply.writeNoException();
                    reply.writeTypedObject(_result30, 1);
                    return true;
                case 79:
                    int _result31 = getDockedStackSide();
                    reply.writeNoException();
                    reply.writeInt(_result31);
                    return true;
                case 80:
                    int _arg068 = data.readInt();
                    IPinnedTaskListener _arg132 =
                            IPinnedTaskListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPinnedTaskListener(_arg068, _arg132);
                    reply.writeNoException();
                    return true;
                case 81:
                    IResultReceiver _arg069 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg133 = data.readInt();
                    data.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(_arg069, _arg133);
                    reply.writeNoException();
                    return true;
                case 82:
                    IResultReceiver _arg070 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg134 = data.readInt();
                    data.enforceNoDataAvail();
                    requestImeKeyboardShortcuts(_arg070, _arg134);
                    reply.writeNoException();
                    return true;
                case 83:
                    int _arg071 = data.readInt();
                    Rect _arg135 = new Rect();
                    data.enforceNoDataAvail();
                    getStableInsets(_arg071, _arg135);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg135, 1);
                    return true;
                case 84:
                    int _arg072 = data.readInt();
                    Rect _arg136 = new Rect();
                    data.enforceNoDataAvail();
                    getOverrideStableInsets(_arg072, _arg136);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg136, 1);
                    return true;
                case 85:
                    long _arg073 = data.readLong();
                    IShortcutService _arg137 =
                            IShortcutService.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerShortcutKey(_arg073, _arg137);
                    reply.writeNoException();
                    return true;
                case 86:
                    IBinder _arg074 = data.readStrongBinder();
                    String _arg138 = data.readString();
                    int _arg29 = data.readInt();
                    InputChannel _arg33 = new InputChannel();
                    data.enforceNoDataAvail();
                    createInputConsumer(_arg074, _arg138, _arg29, _arg33);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg33, 1);
                    return true;
                case 87:
                    IBinder _arg075 = data.readStrongBinder();
                    int _arg139 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result32 = destroyInputConsumer(_arg075, _arg139);
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 88:
                    Region _result33 = getCurrentImeTouchRegion();
                    reply.writeNoException();
                    reply.writeTypedObject(_result33, 1);
                    return true;
                case 89:
                    IDisplayFoldListener _arg076 =
                            IDisplayFoldListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerDisplayFoldListener(_arg076);
                    reply.writeNoException();
                    return true;
                case 90:
                    IDisplayFoldListener _arg077 =
                            IDisplayFoldListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterDisplayFoldListener(_arg077);
                    reply.writeNoException();
                    return true;
                case 91:
                    IDisplayWindowListener _arg078 =
                            IDisplayWindowListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int[] _result34 = registerDisplayWindowListener(_arg078);
                    reply.writeNoException();
                    reply.writeIntArray(_result34);
                    return true;
                case 92:
                    IDisplayWindowListener _arg079 =
                            IDisplayWindowListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterDisplayWindowListener(_arg079);
                    reply.writeNoException();
                    return true;
                case 93:
                    startWindowTrace();
                    reply.writeNoException();
                    return true;
                case 94:
                    stopWindowTrace();
                    reply.writeNoException();
                    return true;
                case 95:
                    saveWindowTraceToFile();
                    reply.writeNoException();
                    return true;
                case 96:
                    boolean _result35 = isWindowTraceEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 97:
                    startTransitionTrace();
                    reply.writeNoException();
                    return true;
                case 98:
                    stopTransitionTrace();
                    reply.writeNoException();
                    return true;
                case 99:
                    boolean _result36 = isTransitionTraceEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 100:
                    int _arg080 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result37 = getWindowingMode(_arg080);
                    reply.writeNoException();
                    reply.writeInt(_result37);
                    return true;
                case 101:
                    int _arg081 = data.readInt();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    setWindowingMode(_arg081, _arg140);
                    reply.writeNoException();
                    return true;
                case 102:
                    int _arg082 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result38 = getRemoveContentMode(_arg082);
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 103:
                    int _arg083 = data.readInt();
                    int _arg141 = data.readInt();
                    data.enforceNoDataAvail();
                    setRemoveContentMode(_arg083, _arg141);
                    reply.writeNoException();
                    return true;
                case 104:
                    int _arg084 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result39 = shouldShowWithInsecureKeyguard(_arg084);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 105:
                    int _arg085 = data.readInt();
                    boolean _arg142 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShouldShowWithInsecureKeyguard(_arg085, _arg142);
                    reply.writeNoException();
                    return true;
                case 106:
                    int _arg086 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result40 = shouldShowSystemDecors(_arg086);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 107:
                    int _arg087 = data.readInt();
                    boolean _arg143 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShouldShowSystemDecors(_arg087, _arg143);
                    reply.writeNoException();
                    return true;
                case 108:
                    int _arg088 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result41 = getDisplayImePolicy(_arg088);
                    reply.writeNoException();
                    reply.writeInt(_result41);
                    return true;
                case 109:
                    int _arg089 = data.readInt();
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    setDisplayImePolicy(_arg089, _arg144);
                    reply.writeNoException();
                    return true;
                case 110:
                    boolean _arg090 = data.readBoolean();
                    data.enforceNoDataAvail();
                    syncInputTransactions(_arg090);
                    reply.writeNoException();
                    return true;
                case 111:
                    boolean _result42 = isLayerTracing();
                    reply.writeNoException();
                    reply.writeBoolean(_result42);
                    return true;
                case 112:
                    boolean _arg091 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLayerTracing(_arg091);
                    reply.writeNoException();
                    return true;
                case 113:
                    int _arg092 = data.readInt();
                    SurfaceControl _arg145 = new SurfaceControl();
                    data.enforceNoDataAvail();
                    boolean _result43 = mirrorDisplay(_arg092, _arg145);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    reply.writeTypedObject(_arg145, 1);
                    return true;
                case 114:
                    int _arg093 = data.readInt();
                    IDisplayWindowInsetsController _arg146 =
                            IDisplayWindowInsetsController.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setDisplayWindowInsetsController(_arg093, _arg146);
                    reply.writeNoException();
                    return true;
                case 115:
                    int _arg094 = data.readInt();
                    int _arg147 = data.readInt();
                    data.enforceNoDataAvail();
                    updateDisplayWindowRequestedVisibleTypes(_arg094, _arg147);
                    reply.writeNoException();
                    return true;
                case 116:
                    int _arg095 = data.readInt();
                    IBinder _arg148 = data.readStrongBinder();
                    InsetsState _arg210 = new InsetsState();
                    data.enforceNoDataAvail();
                    boolean _result44 = getWindowInsets(_arg095, _arg148, _arg210);
                    reply.writeNoException();
                    reply.writeBoolean(_result44);
                    reply.writeTypedObject(_arg210, 1);
                    return true;
                case 117:
                    int _arg096 = data.readInt();
                    data.enforceNoDataAvail();
                    List<DisplayInfo> _result45 = getPossibleDisplayInfo(_arg096);
                    reply.writeNoException();
                    reply.writeTypedList(_result45, 1);
                    return true;
                case 118:
                    showGlobalActions();
                    reply.writeNoException();
                    return true;
                case 119:
                    int _arg097 = data.readInt();
                    data.enforceNoDataAvail();
                    setLayerTracingFlags(_arg097);
                    reply.writeNoException();
                    return true;
                case 120:
                    boolean _arg098 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setActiveTransactionTracing(_arg098);
                    reply.writeNoException();
                    return true;
                case 121:
                    int _arg099 = data.readInt();
                    IBinder _arg149 = data.readStrongBinder();
                    int _arg211 = data.readInt();
                    IScrollCaptureResponseListener _arg34 =
                            IScrollCaptureResponseListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestScrollCapture(_arg099, _arg149, _arg211, _arg34);
                    reply.writeNoException();
                    return true;
                case 122:
                    int _arg0100 = data.readInt();
                    int _arg150 = data.readInt();
                    SmartClipRemoteRequestInfo _arg212 =
                            (SmartClipRemoteRequestInfo)
                                    data.readTypedObject(SmartClipRemoteRequestInfo.CREATOR);
                    IBinder _arg35 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    dispatchSmartClipRemoteRequest(_arg0100, _arg150, _arg212, _arg35);
                    reply.writeNoException();
                    return true;
                case 123:
                    IBinder _arg0101 = data.readStrongBinder();
                    int _arg151 = data.readInt();
                    data.enforceNoDataAvail();
                    holdLock(_arg0101, _arg151);
                    reply.writeNoException();
                    return true;
                case 124:
                    String[] _result46 = getSupportedDisplayHashAlgorithms();
                    reply.writeNoException();
                    reply.writeStringArray(_result46);
                    return true;
                case 125:
                    DisplayHash _arg0102 = (DisplayHash) data.readTypedObject(DisplayHash.CREATOR);
                    data.enforceNoDataAvail();
                    VerifiedDisplayHash _result47 = verifyDisplayHash(_arg0102);
                    reply.writeNoException();
                    reply.writeTypedObject(_result47, 1);
                    return true;
                case 126:
                    boolean _arg0103 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDisplayHashThrottlingEnabled(_arg0103);
                    reply.writeNoException();
                    return true;
                case 127:
                    IApplicationThread _arg0104 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg152 = data.readStrongBinder();
                    int _arg213 = data.readInt();
                    int _arg36 = data.readInt();
                    Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    WindowContextInfo _result48 =
                            attachWindowContextToDisplayArea(
                                    _arg0104, _arg152, _arg213, _arg36, _arg4);
                    reply.writeNoException();
                    reply.writeTypedObject(_result48, 1);
                    return true;
                case 128:
                    IApplicationThread _arg0105 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg153 = data.readStrongBinder();
                    IBinder _arg214 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    WindowContextInfo _result49 =
                            attachWindowContextToWindowToken(_arg0105, _arg153, _arg214);
                    reply.writeNoException();
                    reply.writeTypedObject(_result49, 1);
                    return true;
                case 129:
                    IBinder _arg0106 = data.readStrongBinder();
                    IApplicationThread _arg0107 = IApplicationThread.Stub.asInterface(_arg0106);
                    IBinder _arg154 = data.readStrongBinder();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    WindowContextInfo _result50 =
                            attachWindowContextToDisplayContent(_arg0107, _arg154, _arg215);
                    reply.writeNoException();
                    reply.writeTypedObject(_result50, 1);
                    return true;
                case 130:
                    IBinder _arg0108 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    detachWindowContext(_arg0108);
                    reply.writeNoException();
                    return true;
                case 131:
                    ICrossWindowBlurEnabledListener _arg0109 =
                            ICrossWindowBlurEnabledListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result51 = registerCrossWindowBlurEnabledListener(_arg0109);
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 132:
                    ICrossWindowBlurEnabledListener _arg0110 =
                            ICrossWindowBlurEnabledListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCrossWindowBlurEnabledListener(_arg0110);
                    reply.writeNoException();
                    return true;
                case 133:
                    boolean _result52 = isTaskSnapshotSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 134:
                    int _result53 = getImeDisplayId();
                    reply.writeNoException();
                    reply.writeInt(_result53);
                    return true;
                case 135:
                    boolean _arg0111 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTaskSnapshotEnabled(_arg0111);
                    reply.writeNoException();
                    return true;
                case 136:
                    int _arg0112 = data.readInt();
                    ITaskFpsCallback _arg155 =
                            ITaskFpsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerTaskFpsCallback(_arg0112, _arg155);
                    reply.writeNoException();
                    return true;
                case 137:
                    ITaskFpsCallback _arg0113 =
                            ITaskFpsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterTaskFpsCallback(_arg0113);
                    reply.writeNoException();
                    return true;
                case 138:
                    int _arg0114 = data.readInt();
                    data.enforceNoDataAvail();
                    Bitmap _result54 = snapshotTaskForRecents(_arg0114);
                    reply.writeNoException();
                    reply.writeTypedObject(_result54, 1);
                    return true;
                case 139:
                    boolean _arg0115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRecentsAppBehindSystemBars(_arg0115);
                    reply.writeNoException();
                    return true;
                case 140:
                    List<SemWindowManager.VisibleWindowInfo> _result55 = getVisibleWindowInfoList();
                    reply.writeNoException();
                    reply.writeTypedList(_result55, 1);
                    return true;
                case 141:
                    int _result56 = getLetterboxBackgroundColorInArgb();
                    reply.writeNoException();
                    reply.writeInt(_result56);
                    return true;
                case 142:
                    boolean _result57 = isLetterboxBackgroundMultiColored();
                    reply.writeNoException();
                    reply.writeBoolean(_result57);
                    return true;
                case 143:
                    int _arg0116 = data.readInt();
                    ScreenCapture.CaptureArgs _arg156 =
                            (ScreenCapture.CaptureArgs)
                                    data.readTypedObject(ScreenCapture.CaptureArgs.CREATOR);
                    ScreenCapture.ScreenCaptureListener _arg216 =
                            (ScreenCapture.ScreenCaptureListener)
                                    data.readTypedObject(
                                            ScreenCapture.ScreenCaptureListener.CREATOR);
                    data.enforceNoDataAvail();
                    captureDisplay(_arg0116, _arg156, _arg216);
                    return true;
                case 144:
                    int _arg0117 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result58 = isGlobalKey(_arg0117);
                    reply.writeNoException();
                    reply.writeBoolean(_result58);
                    return true;
                case 145:
                    IBinder _arg0118 = data.readStrongBinder();
                    boolean _arg157 = data.readBoolean();
                    ISurfaceSyncGroupCompletedListener _arg217 =
                            ISurfaceSyncGroupCompletedListener.Stub.asInterface(
                                    data.readStrongBinder());
                    AddToSurfaceSyncGroupResult _arg37 = new AddToSurfaceSyncGroupResult();
                    data.enforceNoDataAvail();
                    boolean _result59 = addToSurfaceSyncGroup(_arg0118, _arg157, _arg217, _arg37);
                    reply.writeNoException();
                    reply.writeBoolean(_result59);
                    reply.writeTypedObject(_arg37, 1);
                    return true;
                case 146:
                    IBinder _arg0119 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    markSurfaceSyncGroupReady(_arg0119);
                    return true;
                case 147:
                    int _arg0120 = data.readInt();
                    data.enforceNoDataAvail();
                    List<ComponentName> _result60 = notifyScreenshotListeners(_arg0120);
                    reply.writeNoException();
                    reply.writeTypedList(_result60, 1);
                    return true;
                case 148:
                    int _arg0121 = data.readInt();
                    SurfaceControl _arg158 =
                            (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result61 = replaceContentOnDisplay(_arg0121, _arg158);
                    reply.writeNoException();
                    reply.writeBoolean(_result61);
                    return true;
                case 149:
                    IDecorViewGestureListener _arg0122 =
                            IDecorViewGestureListener.Stub.asInterface(data.readStrongBinder());
                    int _arg159 = data.readInt();
                    data.enforceNoDataAvail();
                    registerDecorViewGestureListener(_arg0122, _arg159);
                    reply.writeNoException();
                    return true;
                case 150:
                    IBinder _arg0123 = data.readStrongBinder();
                    IDecorViewGestureListener _arg0124 =
                            IDecorViewGestureListener.Stub.asInterface(_arg0123);
                    int _arg160 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterDecorViewGestureListener(_arg0124, _arg160);
                    reply.writeNoException();
                    return true;
                case 151:
                    IBinder _arg0125 = data.readStrongBinder();
                    ITrustedPresentationListener _arg161 =
                            ITrustedPresentationListener.Stub.asInterface(data.readStrongBinder());
                    TrustedPresentationThresholds _arg218 =
                            (TrustedPresentationThresholds)
                                    data.readTypedObject(TrustedPresentationThresholds.CREATOR);
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    registerTrustedPresentationListener(_arg0125, _arg161, _arg218, _arg38);
                    reply.writeNoException();
                    return true;
                case 152:
                    ITrustedPresentationListener _arg0126 =
                            ITrustedPresentationListener.Stub.asInterface(data.readStrongBinder());
                    int _arg162 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterTrustedPresentationListener(_arg0126, _arg162);
                    reply.writeNoException();
                    return true;
                case 153:
                    IScreenRecordingCallback _arg0127 =
                            IScreenRecordingCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result62 = registerScreenRecordingCallback(_arg0127);
                    reply.writeNoException();
                    reply.writeBoolean(_result62);
                    return true;
                case 154:
                    IScreenRecordingCallback _arg0128 =
                            IScreenRecordingCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterScreenRecordingCallback(_arg0128);
                    reply.writeNoException();
                    return true;
                case 155:
                    IScreenRecordingCallback _arg0129 =
                            IScreenRecordingCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result63 = registerKnoxRemoteScreenCallback(_arg0129);
                    reply.writeNoException();
                    reply.writeBoolean(_result63);
                    return true;
                case 156:
                    IScreenRecordingCallback _arg0130 =
                            IScreenRecordingCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterKnoxRemoteScreenCallback(_arg0130);
                    reply.writeNoException();
                    return true;
                case 157:
                    IGlobalDragListener _arg0131 =
                            IGlobalDragListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setGlobalDragListener(_arg0131);
                    reply.writeNoException();
                    return true;
                case 158:
                    InputTransferToken _arg0132 =
                            (InputTransferToken) data.readTypedObject(InputTransferToken.CREATOR);
                    InputTransferToken _arg163 =
                            (InputTransferToken) data.readTypedObject(InputTransferToken.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result64 = transferTouchGesture(_arg0132, _arg163);
                    reply.writeNoException();
                    reply.writeBoolean(_result64);
                    return true;
                case 159:
                    Bundle _arg0133 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    setDeadzoneHole(_arg0133);
                    reply.writeNoException();
                    return true;
                case 160:
                    String _arg0134 = data.readString();
                    int _arg164 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result65 = getMaxAspectRatioPolicy(_arg0134, _arg164);
                    reply.writeNoException();
                    reply.writeInt(_result65);
                    return true;
                case 161:
                    String _arg0135 = data.readString();
                    int _arg165 = data.readInt();
                    boolean _arg219 = data.readBoolean();
                    int _arg39 = data.readInt();
                    data.enforceNoDataAvail();
                    setMaxAspectRatioPolicy(_arg0135, _arg165, _arg219, _arg39);
                    reply.writeNoException();
                    return true;
                case 162:
                    int _arg0136 = data.readInt();
                    int _arg166 = data.readInt();
                    boolean _arg220 = data.readBoolean();
                    Rect _arg310 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg42 = data.readInt();
                    int _arg5 = data.readInt();
                    boolean _arg6 = data.readBoolean();
                    boolean _arg7 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ScreenshotResult _result66 =
                            takeScreenshotToTargetWindow(
                                    _arg0136, _arg166, _arg220, _arg310, _arg42, _arg5, _arg6,
                                    _arg7);
                    reply.writeNoException();
                    reply.writeTypedObject(_result66, 1);
                    return true;
                case 163:
                    int _arg0137 = data.readInt();
                    int _arg167 = data.readInt();
                    boolean _arg221 = data.readBoolean();
                    Rect _arg311 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg43 = data.readInt();
                    int _arg52 = data.readInt();
                    boolean _arg62 = data.readBoolean();
                    boolean _arg72 = data.readBoolean();
                    boolean _arg8 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ScreenshotResult _result67 =
                            takeScreenshotToTargetWindowFromCapture(
                                    _arg0137, _arg167, _arg221, _arg311, _arg43, _arg52, _arg62,
                                    _arg72, _arg8);
                    reply.writeNoException();
                    reply.writeTypedObject(_result67, 1);
                    return true;
                case 164:
                    Point _arg0138 = new Point();
                    data.enforceNoDataAvail();
                    getUserDisplaySize(_arg0138);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg0138, 1);
                    return true;
                case 165:
                    int _result68 = getUserDisplayDensity();
                    reply.writeNoException();
                    reply.writeInt(_result68);
                    return true;
                case 166:
                    int _arg0139 = data.readInt();
                    data.enforceNoDataAvail();
                    clearForcedDisplaySizeDensity(_arg0139);
                    reply.writeNoException();
                    return true;
                case 167:
                    int _arg0140 = data.readInt();
                    int _arg168 = data.readInt();
                    int _arg222 = data.readInt();
                    int _arg312 = data.readInt();
                    boolean _arg44 = data.readBoolean();
                    int _arg53 = data.readInt();
                    data.enforceNoDataAvail();
                    setForcedDisplaySizeDensity(
                            _arg0140, _arg168, _arg222, _arg312, _arg44, _arg53);
                    reply.writeNoException();
                    return true;
                case 168:
                    MultiResolutionChangeRequestInfo _arg0141 =
                            (MultiResolutionChangeRequestInfo)
                                    data.readTypedObject(MultiResolutionChangeRequestInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setForcedDisplaySizeDensityWithInfo(_arg0141);
                    reply.writeNoException();
                    return true;
                case 169:
                    int _arg0142 = data.readInt();
                    String _arg169 = data.readString();
                    data.enforceNoDataAvail();
                    int _result69 = getSupportsFlexPanel(_arg0142, _arg169);
                    reply.writeNoException();
                    reply.writeInt(_result69);
                    return true;
                case 170:
                    int _arg0143 = data.readInt();
                    String _arg170 = data.readString();
                    boolean _arg223 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSupportsFlexPanel(_arg0143, _arg170, _arg223);
                    reply.writeNoException();
                    return true;
                case 171:
                    int _result70 = getFullScreenAppsSupportMode();
                    reply.writeNoException();
                    reply.writeInt(_result70);
                    return true;
                case 172:
                    MagnificationSpec _arg0144 =
                            (MagnificationSpec) data.readTypedObject(MagnificationSpec.CREATOR);
                    boolean _arg171 = data.readBoolean();
                    IInputFilter _arg224 = IInputFilter.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    changeDisplayScale(_arg0144, _arg171, _arg224);
                    reply.writeNoException();
                    return true;
                case 173:
                    IOneHandOpWatcher _arg0145 =
                            IOneHandOpWatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerOneHandOpWatcher(_arg0145);
                    reply.writeNoException();
                    return true;
                case 174:
                    IOneHandOpWatcher _arg0146 =
                            IOneHandOpWatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterOneHandOpWatcher(_arg0146);
                    reply.writeNoException();
                    return true;
                case 175:
                    IRemoteAnimationRunner _arg0147 =
                            IRemoteAnimationRunner.Stub.asInterface(data.readStrongBinder());
                    int _arg172 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result71 = startRemoteWallpaperAnimation(_arg0147, _arg172);
                    reply.writeNoException();
                    reply.writeBoolean(_result71);
                    return true;
                case 176:
                    IRemoteAnimationRunner _arg0148 =
                            IRemoteAnimationRunner.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result72 = finishRemoteWallpaperAnimation(_arg0148);
                    reply.writeNoException();
                    reply.writeBoolean(_result72);
                    return true;
                case 177:
                    int _arg0149 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result73 = getRotationLockOrientation(_arg0149);
                    reply.writeNoException();
                    reply.writeInt(_result73);
                    return true;
                case 178:
                    boolean _result74 = hasTaskbarTarget();
                    reply.writeNoException();
                    reply.writeBoolean(_result74);
                    return true;
                case 179:
                    int _arg0150 = data.readInt();
                    ComponentName _arg173 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg225 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result75 = requestSystemKeyEvent(_arg0150, _arg173, _arg225);
                    reply.writeNoException();
                    reply.writeBoolean(_result75);
                    return true;
                case 180:
                    int _arg0151 = data.readInt();
                    ComponentName _arg174 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result76 = isSystemKeyEventRequested(_arg0151, _arg174);
                    reply.writeNoException();
                    reply.writeBoolean(_result76);
                    return true;
                case 181:
                    int _arg0152 = data.readInt();
                    ComponentName _arg175 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg226 = data.readInt();
                    data.enforceNoDataAvail();
                    registerSystemKeyEvent(_arg0152, _arg175, _arg226);
                    reply.writeNoException();
                    return true;
                case 182:
                    int _arg0153 = data.readInt();
                    ComponentName _arg176 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    unregisterSystemKeyEvent(_arg0153, _arg176);
                    reply.writeNoException();
                    return true;
                case 183:
                    ComponentName _arg0154 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg177 = data.readBoolean();
                    data.enforceNoDataAvail();
                    requestMetaKeyEvent(_arg0154, _arg177);
                    reply.writeNoException();
                    return true;
                case 184:
                    ComponentName _arg0155 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result77 = isMetaKeyEventRequested(_arg0155);
                    reply.writeNoException();
                    reply.writeBoolean(_result77);
                    return true;
                case 185:
                    SemWindowManager.KeyCustomizationInfo _arg0156 =
                            (SemWindowManager.KeyCustomizationInfo)
                                    data.readTypedObject(
                                            SemWindowManager.KeyCustomizationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    putKeyCustomizationInfo(_arg0156);
                    reply.writeNoException();
                    return true;
                case 186:
                    int _arg0157 = data.readInt();
                    int _arg178 = data.readInt();
                    int _arg227 = data.readInt();
                    data.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo _result78 =
                            getKeyCustomizationInfo(_arg0157, _arg178, _arg227);
                    reply.writeNoException();
                    reply.writeTypedObject(_result78, 1);
                    return true;
                case 187:
                    String _arg0158 = data.readString();
                    int _arg179 = data.readInt();
                    int _arg228 = data.readInt();
                    data.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo _result79 =
                            getKeyCustomizationInfoByPackage(_arg0158, _arg179, _arg228);
                    reply.writeNoException();
                    reply.writeTypedObject(_result79, 1);
                    return true;
                case 188:
                    int _arg0159 = data.readInt();
                    int _arg180 = data.readInt();
                    data.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo _result80 =
                            getLastKeyCustomizationInfo(_arg0159, _arg180);
                    reply.writeNoException();
                    reply.writeTypedObject(_result80, 1);
                    return true;
                case 189:
                    int _arg0160 = data.readInt();
                    int _arg181 = data.readInt();
                    int _arg229 = data.readInt();
                    data.enforceNoDataAvail();
                    removeKeyCustomizationInfo(_arg0160, _arg181, _arg229);
                    reply.writeNoException();
                    return true;
                case 190:
                    String _arg0161 = data.readString();
                    int _arg182 = data.readInt();
                    int _arg230 = data.readInt();
                    data.enforceNoDataAvail();
                    removeKeyCustomizationInfoByPackage(_arg0161, _arg182, _arg230);
                    reply.writeNoException();
                    return true;
                case 191:
                    int _arg0162 = data.readInt();
                    int _arg183 = data.readInt();
                    data.enforceNoDataAvail();
                    clearKeyCustomizationInfoByKeyCode(_arg0162, _arg183);
                    reply.writeNoException();
                    return true;
                case 192:
                    int _arg0163 = data.readInt();
                    int _arg184 = data.readInt();
                    int _arg231 = data.readInt();
                    data.enforceNoDataAvail();
                    clearKeyCustomizationInfoByAction(_arg0163, _arg184, _arg231);
                    reply.writeNoException();
                    return true;
                case 193:
                    List<SemWindowManager.KeyCustomizationInfo> _result81 =
                            getBackupKeyCustomizationInfoList();
                    reply.writeNoException();
                    reply.writeTypedList(_result81, 1);
                    return true;
                case 194:
                    List<SemWindowManager.KeyCustomizationInfo> _arg0164 =
                            data.createTypedArrayList(
                                    SemWindowManager.KeyCustomizationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    restoreKeyCustomizationInfo(_arg0164);
                    reply.writeNoException();
                    return true;
                case 195:
                    IAssistDataReceiver _arg0165 =
                            IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
                    boolean _arg185 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result82 = omniRequestAssistScreenshot(_arg0165, _arg185);
                    reply.writeNoException();
                    reply.writeBoolean(_result82);
                    return true;
                case 196:
                    int _arg0166 = data.readInt();
                    int _arg186 = data.readInt();
                    InputEvent[] _arg232 = (InputEvent[]) data.createTypedArray(InputEvent.CREATOR);
                    IBinder _arg313 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    dispatchSPenGestureEvent(_arg0166, _arg186, _arg232, _arg313);
                    reply.writeNoException();
                    return true;
                case 197:
                    PendingIntent _arg0167 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    Intent _arg187 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    setPendingIntentAfterUnlock(_arg0167, _arg187);
                    reply.writeNoException();
                    return true;
                case 198:
                    boolean _result83 = isKeyguardShowingAndNotOccluded();
                    reply.writeNoException();
                    reply.writeBoolean(_result83);
                    return true;
                case 199:
                    startLockscreenFingerprintAuth();
                    reply.writeNoException();
                    return true;
                case 200:
                    int _result84 = getTopFocusedDisplayId();
                    reply.writeNoException();
                    reply.writeInt(_result84);
                    return true;
                case 201:
                    int _arg0168 = data.readInt();
                    String _arg188 = data.readString();
                    data.enforceNoDataAvail();
                    moveDisplayToTop(_arg0168, _arg188);
                    return true;
                case 202:
                    boolean _arg0169 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDragSurfaceToOverlay(_arg0169);
                    reply.writeNoException();
                    return true;
                case 203:
                    IBinder _arg0170 = data.readStrongBinder();
                    String _arg189 = data.readString();
                    data.enforceNoDataAvail();
                    startSurfaceAnimation(_arg0170, _arg189);
                    return true;
                case 204:
                    boolean _result85 = isFolded();
                    reply.writeNoException();
                    reply.writeBoolean(_result85);
                    return true;
                case 205:
                    boolean _result86 = isTableMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result86);
                    return true;
                case 206:
                    boolean _arg0171 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTableModeEnabled(_arg0171);
                    reply.writeNoException();
                    return true;
                case 207:
                    int _arg0172 = data.readInt();
                    String _arg190 = data.readString();
                    ActivityInfo _arg233 =
                            (ActivityInfo) data.readTypedObject(ActivityInfo.CREATOR);
                    data.enforceNoDataAvail();
                    int _result87 = getAppContinuityMode(_arg0172, _arg190, _arg233);
                    reply.writeNoException();
                    reply.writeInt(_result87);
                    return true;
                case 208:
                    int _arg0173 = data.readInt();
                    String _arg191 = data.readString();
                    boolean _arg234 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAppContinuityMode(_arg0173, _arg191, _arg234);
                    reply.writeNoException();
                    return true;
                case 209:
                    IAuthTouchEventListener _arg0174 =
                            IAuthTouchEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerAuthTouchEventListener(_arg0174);
                    reply.writeNoException();
                    return true;
                case 210:
                    IAuthTouchEventListener _arg0175 =
                            IAuthTouchEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAuthTouchEventListener(_arg0175);
                    reply.writeNoException();
                    return true;
                case 211:
                    int _arg0176 = data.readInt();
                    data.enforceNoDataAvail();
                    setDisplayColorToSystemProperties(_arg0176);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWindowManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowManager
            public boolean startViewServer(int port) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(port);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean stopViewServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isViewServerRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public IWindowSession openSession(IWindowSessionCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    IWindowSession _result =
                            IWindowSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getInitialDisplaySize(int displayId, Point size) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        size.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getBaseDisplaySize(int displayId, Point size) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        size.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySize(int displayId, int width, int height)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySize(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getInitialDisplayDensity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getBaseDisplayDensity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayIdByUniqueId(String uniqueId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uniqueId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityForUser(int displayId, int density, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(density);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplayDensityForUser(int displayId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayScalingMode(int displayId, int mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setEventDispatching(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowToken(IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addWindowToken(IBinder token, int type, int displayId, Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(type);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeWindowToken(IBinder token, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(displayId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayChangeWindowController(IDisplayChangeWindowController controller)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl addShellRoot(int displayId, IWindow client, int shellRootLayer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(client);
                    _data.writeInt(shellRootLayer);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    SurfaceControl _result =
                            (SurfaceControl) _reply.readTypedObject(SurfaceControl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShellRootAccessibilityWindow(
                    int displayId, int shellRootLayer, IWindow target) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(shellRootLayer);
                    _data.writeStrongInterface(target);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionMultiThumbFuture(
                    IAppTransitionAnimationSpecsFuture specsFuture,
                    IRemoteCallback startedCallback,
                    boolean scaleUp,
                    int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(specsFuture);
                    _data.writeStrongInterface(startedCallback);
                    _data.writeBoolean(scaleUp);
                    _data.writeInt(displayId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionRemote(
                    RemoteAnimationAdapter remoteAnimationAdapter, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(remoteAnimationAdapter, 0);
                    _data.writeInt(displayId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void endProlongedAnimations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startFreezingScreen(int exitAnim, int enterAnim) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(exitAnim);
                    _data.writeInt(enterAnim);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopFreezingScreen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void disableKeyguard(IBinder token, String tag, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    _data.writeInt(userId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void reenableKeyguard(IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void exitKeyguardSecurely(IOnKeyguardExitResult callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardLocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardSecure(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dismissKeyguard(IKeyguardDismissCallback callback, CharSequence message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addKeyguardLockedStateListener(IKeyguardLockedStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSwitchingUser(boolean switching) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(switching);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogsInDisplay(String reason, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    _data.writeInt(displayId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getAnimationScale(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float[] getAnimationScales() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScale(int which, float scale) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    _data.writeFloat(scale);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScales(float[] scales) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloatArray(scales);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getCurrentAnimatorScale() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchMode(boolean inTouch, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(inTouch);
                    _data.writeInt(displayId);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchModeOnAllDisplays(boolean inTouch) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(inTouch);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isInTouchMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showStrictModeViolation(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setStrictModeVisualIndicatorPreference(String enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(enabled);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void refreshScreenCaptureDisabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDefaultDisplayRotation() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayUserRotation(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int watchRotation(IRotationWatcher watcher, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    _data.writeInt(displayId);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeRotationWatcher(IRotationWatcher watcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int registerProposedRotationListener(
                    IBinder contextToken, IRotationWatcher listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(contextToken);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getPreferredOptionsPanelGravity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeRotation(int rotation, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    _data.writeString(caller);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawRotation(String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(caller);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isRotationFrozen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeDisplayRotation(int displayId, int rotation, String caller)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(rotation);
                    _data.writeString(caller);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawDisplayRotation(int displayId, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeString(caller);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isDisplayRotationFrozen(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setFixedToUserRotation(int displayId, int fixedToUserRotation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(fixedToUserRotation);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setIgnoreOrientationRequest(int displayId, boolean ignoreOrientationRequest)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(ignoreOrientationRequest);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap screenshotWallpaper() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl mirrorWallpaperSurface(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    SurfaceControl _result =
                            (SurfaceControl) _reply.readTypedObject(SurfaceControl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerWallpaperVisibilityListener(
                    IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterWallpaperVisibilityListener(
                    IWallpaperVisibilityListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemGestureExclusionListener(
                    ISystemGestureExclusionListener listener, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemGestureExclusionListener(
                    ISystemGestureExclusionListener listener, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestAssistScreenshot(IAssistDataReceiver receiver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void hideTransientBars(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(70, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(71, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateStaticPrivacyIndicatorBounds(int displayId, Rect[] staticBounds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedArray(staticBounds, 0);
                    this.mRemote.transact(72, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasNavigationBar(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void lockNow(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSafeModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean clearWindowContentFrameStats(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContentFrameStats getWindowContentFrameStats(IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    WindowContentFrameStats _result =
                            (WindowContentFrameStats)
                                    _reply.readTypedObject(WindowContentFrameStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDockedStackSide() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerPinnedTaskListener(int displayId, IPinnedTaskListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestImeKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getStableInsets(int displayId, Rect outInsets) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInsets.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getOverrideStableInsets(int displayId, Rect outInsets)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInsets.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerShortcutKey(long shortcutCode, IShortcutService keySubscriber)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(shortcutCode);
                    _data.writeStrongInterface(keySubscriber);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void createInputConsumer(
                    IBinder token, String name, int displayId, InputChannel inputChannel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        inputChannel.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean destroyInputConsumer(IBinder token, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(displayId);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Region getCurrentImeTouchRegion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    Region _result = (Region) _reply.readTypedObject(Region.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDisplayFoldListener(IDisplayFoldListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayFoldListener(IDisplayFoldListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int[] registerDisplayWindowListener(IDisplayWindowListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayWindowListener(IDisplayWindowListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startWindowTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopWindowTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void saveWindowTraceToFile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowTraceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startTransitionTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopTransitionTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTransitionTraceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getWindowingMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setWindowingMode(int displayId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRemoveContentMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRemoveContentMode(int displayId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(mode);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowWithInsecureKeyguard(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(shouldShow);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowSystemDecors(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowSystemDecors(int displayId, boolean shouldShow)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(shouldShow);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayImePolicy(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayImePolicy(int displayId, int imePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(imePolicy);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void syncInputTransactions(boolean waitForAnimations) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(waitForAnimations);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLayerTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracing(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean mirrorDisplay(int displayId, SurfaceControl outSurfaceControl)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    if (_reply.readInt() != 0) {
                        outSurfaceControl.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayWindowInsetsController(
                    int displayId, IDisplayWindowInsetsController displayWindowInsetsController)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(displayWindowInsetsController);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowRequestedVisibleTypes(
                    int displayId, int requestedVisibleTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(requestedVisibleTypes);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getWindowInsets(int displayId, IBinder token, InsetsState outInsetsState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    if (_reply.readInt() != 0) {
                        outInsetsState.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<DisplayInfo> getPossibleDisplayInfo(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                    List<DisplayInfo> _result = _reply.createTypedArrayList(DisplayInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showGlobalActions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracingFlags(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setActiveTransactionTracing(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(active);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestScrollCapture(
                    int displayId,
                    IBinder behindClient,
                    int taskId,
                    IScrollCaptureResponseListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(behindClient);
                    _data.writeInt(taskId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSmartClipRemoteRequest(
                    int targetX,
                    int targetY,
                    SmartClipRemoteRequestInfo request,
                    IBinder skipWindowToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(targetX);
                    _data.writeInt(targetY);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongBinder(skipWindowToken);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void holdLock(IBinder token, int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(durationMs);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public String[] getSupportedDisplayHashAlgorithms() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(displayHash, 0);
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                    VerifiedDisplayHash _result =
                            (VerifiedDisplayHash)
                                    _reply.readTypedObject(VerifiedDisplayHash.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayHashThrottlingEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayArea(
                    IApplicationThread appThread,
                    IBinder clientToken,
                    int type,
                    int displayId,
                    Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(appThread);
                    _data.writeStrongBinder(clientToken);
                    _data.writeInt(type);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                    WindowContextInfo _result =
                            (WindowContextInfo) _reply.readTypedObject(WindowContextInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToWindowToken(
                    IApplicationThread appThread, IBinder clientToken, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(appThread);
                    _data.writeStrongBinder(clientToken);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                    WindowContextInfo _result =
                            (WindowContextInfo) _reply.readTypedObject(WindowContextInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayContent(
                    IApplicationThread appThread, IBinder clientToken, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(appThread);
                    _data.writeStrongBinder(clientToken);
                    _data.writeInt(displayId);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                    WindowContextInfo _result =
                            (WindowContextInfo) _reply.readTypedObject(WindowContextInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void detachWindowContext(IBinder clientToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(clientToken);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerCrossWindowBlurEnabledListener(
                    ICrossWindowBlurEnabledListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterCrossWindowBlurEnabledListener(
                    ICrossWindowBlurEnabledListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTaskSnapshotSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getImeDisplayId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTaskSnapshotEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTaskFpsCallback(int taskId, ITaskFpsCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTaskFpsCallback(ITaskFpsCallback listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap snapshotTaskForRecents(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsAppBehindSystemBars(boolean behindSystemBars)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(behindSystemBars);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                    List<SemWindowManager.VisibleWindowInfo> _result =
                            _reply.createTypedArrayList(SemWindowManager.VisibleWindowInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getLetterboxBackgroundColorInArgb() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(141, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLetterboxBackgroundMultiColored() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(142, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void captureDisplay(
                    int displayId,
                    ScreenCapture.CaptureArgs captureArgs,
                    ScreenCapture.ScreenCaptureListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(captureArgs, 0);
                    _data.writeTypedObject(listener, 0);
                    this.mRemote.transact(143, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isGlobalKey(int keyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean addToSurfaceSyncGroup(
                    IBinder syncGroupToken,
                    boolean parentSyncGroupMerge,
                    ISurfaceSyncGroupCompletedListener completedListener,
                    AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(syncGroupToken);
                    _data.writeBoolean(parentSyncGroupMerge);
                    _data.writeStrongInterface(completedListener);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    if (_reply.readInt() != 0) {
                        addToSurfaceSyncGroupResult.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void markSurfaceSyncGroupReady(IBinder syncGroupToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(syncGroupToken);
                    this.mRemote.transact(146, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<ComponentName> notifyScreenshotListeners(int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                    List<ComponentName> _result =
                            _reply.createTypedArrayList(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean replaceContentOnDisplay(int displayId, SurfaceControl sc)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(sc, 0);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDecorViewGestureListener(
                    IDecorViewGestureListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDecorViewGestureListener(
                    IDecorViewGestureListener listener, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(displayId);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTrustedPresentationListener(
                    IBinder window,
                    ITrustedPresentationListener listener,
                    TrustedPresentationThresholds thresholds,
                    int id)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    _data.writeStrongInterface(listener);
                    _data.writeTypedObject(thresholds, 0);
                    _data.writeInt(id);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTrustedPresentationListener(
                    ITrustedPresentationListener listener, int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(id);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerScreenRecordingCallback(IScreenRecordingCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterScreenRecordingCallback(IScreenRecordingCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setGlobalDragListener(IGlobalDragListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean transferTouchGesture(
                    InputTransferToken transferFromToken, InputTransferToken transferToToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(transferFromToken, 0);
                    _data.writeTypedObject(transferToToken, 0);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDeadzoneHole(Bundle deadzoneHole) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(deadzoneHole, 0);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getMaxAspectRatioPolicy(String pkg, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    this.mRemote.transact(160, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setMaxAspectRatioPolicy(
                    String pkg, int uid, boolean enable, int restartTaskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(uid);
                    _data.writeBoolean(enable);
                    _data.writeInt(restartTaskId);
                    this.mRemote.transact(161, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindow(
                    int displayId,
                    int targetWindowType,
                    boolean containsTarget,
                    Rect sourceCrop,
                    int width,
                    int height,
                    boolean useIdentityTransform,
                    boolean ignorePolicy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(targetWindowType);
                    _data.writeBoolean(containsTarget);
                    _data.writeTypedObject(sourceCrop, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeBoolean(useIdentityTransform);
                    _data.writeBoolean(ignorePolicy);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                    ScreenshotResult _result =
                            (ScreenshotResult) _reply.readTypedObject(ScreenshotResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindowFromCapture(
                    int displayId,
                    int targetWindowType,
                    boolean containsTarget,
                    Rect sourceCrop,
                    int width,
                    int height,
                    boolean useIdentityTransform,
                    boolean ignorePolicy,
                    boolean fromCapture)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(targetWindowType);
                    _data.writeBoolean(containsTarget);
                    _data.writeTypedObject(sourceCrop, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeBoolean(useIdentityTransform);
                    _data.writeBoolean(ignorePolicy);
                    _data.writeBoolean(fromCapture);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                    ScreenshotResult _result =
                            (ScreenshotResult) _reply.readTypedObject(ScreenshotResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getUserDisplaySize(Point point) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(164, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        point.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getUserDisplayDensity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySizeDensity(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensity(
                    int displayId,
                    int width,
                    int height,
                    int density,
                    boolean saveToSettings,
                    int forcedHideCutout)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(density);
                    _data.writeBoolean(saveToSettings);
                    _data.writeInt(forcedHideCutout);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getSupportsFlexPanel(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(169, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSupportsFlexPanel(
                    int userId, String packageName, boolean isSupportsFlexPanel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeBoolean(isSupportsFlexPanel);
                    this.mRemote.transact(170, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getFullScreenAppsSupportMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void changeDisplayScale(
                    MagnificationSpec spec, boolean registerInput, IInputFilter filter)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(spec, 0);
                    _data.writeBoolean(registerInput);
                    _data.writeStrongInterface(filter);
                    this.mRemote.transact(172, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerOneHandOpWatcher(IOneHandOpWatcher watcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    this.mRemote.transact(173, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterOneHandOpWatcher(IOneHandOpWatcher watcher)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    this.mRemote.transact(174, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean startRemoteWallpaperAnimation(
                    IRemoteAnimationRunner runner, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(runner);
                    _data.writeInt(displayId);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner runner)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(runner);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRotationLockOrientation(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasTaskbarTarget() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestSystemKeyEvent(
                    int keyCode, ComponentName componentName, boolean request)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeBoolean(request);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSystemKeyEventRequested(int keyCode, ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemKeyEvent(int keyCode, ComponentName componentName, int press)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(press);
                    this.mRemote.transact(181, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemKeyEvent(int keyCode, ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestMetaKeyEvent(ComponentName componentName, boolean request)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeBoolean(request);
                    this.mRemote.transact(183, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isMetaKeyEventRequested(ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void putKeyCustomizationInfo(
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(keyCustomizationInfo, 0);
                    this.mRemote.transact(185, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(
                    int id, int press, int keyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(press);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                    SemWindowManager.KeyCustomizationInfo _result =
                            (SemWindowManager.KeyCustomizationInfo)
                                    _reply.readTypedObject(
                                            SemWindowManager.KeyCustomizationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(
                    String ownerPackage, int press, int keyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ownerPackage);
                    _data.writeInt(press);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                    SemWindowManager.KeyCustomizationInfo _result =
                            (SemWindowManager.KeyCustomizationInfo)
                                    _reply.readTypedObject(
                                            SemWindowManager.KeyCustomizationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(
                    int press, int keyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(press);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                    SemWindowManager.KeyCustomizationInfo _result =
                            (SemWindowManager.KeyCustomizationInfo)
                                    _reply.readTypedObject(
                                            SemWindowManager.KeyCustomizationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfo(int id, int press, int keyCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(press);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfoByPackage(
                    String ownerPackage, int press, int keyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ownerPackage);
                    _data.writeInt(press);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByKeyCode(int id, int keyCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(keyCode);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByAction(int id, int keyCode, int action)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(keyCode);
                    _data.writeInt(action);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                    List<SemWindowManager.KeyCustomizationInfo> _result =
                            _reply.createTypedArrayList(
                                    SemWindowManager.KeyCustomizationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void restoreKeyCustomizationInfo(
                    List<SemWindowManager.KeyCustomizationInfo> keyInfoArray)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(keyInfoArray, 0);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean omniRequestAssistScreenshot(
                    IAssistDataReceiver receiver, boolean appWindowOnly) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    _data.writeBoolean(appWindowOnly);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSPenGestureEvent(
                    int targetX, int targetY, InputEvent[] inputEvents, IBinder topToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(targetX);
                    _data.writeInt(targetY);
                    _data.writeTypedArray(inputEvents, 0);
                    _data.writeStrongBinder(topToken);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setPendingIntentAfterUnlock(
                    PendingIntent pendingIntent, Intent fillInIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    _data.writeTypedObject(fillInIntent, 0);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardShowingAndNotOccluded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(198, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startLockscreenFingerprintAuth() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getTopFocusedDisplayId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void moveDisplayToTop(int displayId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeString(reason);
                    this.mRemote.transact(201, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDragSurfaceToOverlay(boolean toOverlay) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(toOverlay);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startSurfaceAnimation(IBinder window, String args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    _data.writeString(args);
                    this.mRemote.transact(203, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isFolded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTableMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTableModeEnabled(boolean isTableModeEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isTableModeEnabled);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getAppContinuityMode(int userId, String packageName, ActivityInfo aInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeTypedObject(aInfo, 0);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAppContinuityMode(int userId, String packageName, boolean applied)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeBoolean(applied);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerAuthTouchEventListener(IAuthTouchEventListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterAuthTouchEventListener(IAuthTouchEventListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayColorToSystemProperties(int color) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(color);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setForcedDisplaySize_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplaySize_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayDensityForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplayDensityForUser_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayScalingMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void addShellRoot_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void setShellRootAccessibilityWindow_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void exitKeyguardSecurely_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DISABLE_KEYGUARD, getCallingPid(), getCallingUid());
        }

        protected void setNavBarVirtualKeyHapticFeedbackEnabled_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STATUS_BAR, getCallingPid(), getCallingUid());
        }

        protected void getCurrentImeTouchRegion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.RESTRICTED_VR_ACCESS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayWindowInsetsController_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void updateDisplayWindowRequestedVisibleTypes_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void registerScreenRecordingCallback_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void unregisterScreenRecordingCallback_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void registerKnoxRemoteScreenCallback_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void unregisterKnoxRemoteScreenCallback_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 210;
        }
    }
}
