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
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.ICrossWindowBlurEnabledListener;
import android.view.IDecorViewGestureListener;
import android.view.IDisplayChangeWindowController;
import android.view.IDisplayFoldListener;
import android.view.IDisplayWindowInsetsController;
import android.view.IDisplayWindowListener;
import android.view.IInputFilter;
import android.view.IOnKeyguardExitResult;
import android.view.IPinnedTaskListener;
import android.view.IRemoteAnimationRunner;
import android.view.IRotationWatcher;
import android.view.IScrollCaptureResponseListener;
import android.view.ISystemGestureExclusionListener;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.IWindowSessionCallback;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.VerifiedDisplayHash;
import android.view.inputmethod.ImeTracker;
import android.window.AddToSurfaceSyncGroupResult;
import android.window.ConfigurationChangeSetting;
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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IWindowManager extends IInterface {
    public static final int FIXED_TO_USER_ROTATION_DEFAULT = 0;
    public static final int FIXED_TO_USER_ROTATION_DISABLED = 1;
    public static final int FIXED_TO_USER_ROTATION_ENABLED = 2;
    public static final int FIXED_TO_USER_ROTATION_IF_NO_AUTO_ROTATION = 3;

    public static class Default implements IWindowManager {
        @Override // android.view.IWindowManager
        public void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToDisplayContent(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContextInfo attachWindowContextToWindowToken(IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplaySize(int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplaySizeDensity(int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearKeyCustomizationInfoByKeyCode(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void closeSystemDialogs(String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean destroyInputConsumer(IBinder iBinder, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void detachWindowContext(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void endProlongedAnimations() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void freezeDisplayRotation(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void freezeRotation(int i, String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public float getAnimationScale(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public float[] getAnimationScales() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getBaseDisplayDensity(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void getBaseDisplaySize(int i, Point point) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public float getCurrentAnimatorScale() throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public Region getCurrentImeTouchRegion() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getDefaultDisplayRotation() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayIdByUniqueId(String str) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayImePolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayUserRotation(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDockedStackSide() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getFullScreenAppsSupportMode() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean getIgnoreOrientationRequest(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getImeDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getInitialDisplayDensity(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void getInitialDisplaySize(int i, Point point) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getLetterboxBackgroundColorInArgb() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getMaxAspectRatioPolicy(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void getOverrideStableInsets(int i, Rect rect) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public List<DisplayInfo> getPossibleDisplayInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getPreferredOptionsPanelGravity(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getRemoveContentMode(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getRotationLockOrientation(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void getStableInsets(int i, Rect rect) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public String[] getSupportedDisplayHashAlgorithms() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getSupportsFlexPanel(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getTopFocusedDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getUserDisplayDensity() throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void getUserDisplaySize(Point point) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getWindowingMode(int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean hasNavigationBar(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean hasTaskbarTarget() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void hideTransientBars(int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void holdLock(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isDisplayRotationFrozen(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isEdgeToEdgeDisabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isEligibleForDesktopMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isFolded() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isGlobalKey(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isInTouchMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardLocked() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardSecure(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardShowingAndNotOccluded() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isLayerTracing() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isLetterboxBackgroundMultiColored() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isMetaKeyEventRequested(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isRotationFrozen() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isSafeModeEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isSystemKeyEventRequested(int i, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isTableMode() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isTaskSnapshotSupported() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isTransitionTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isViewServerRunning() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isWindowToken(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isWindowTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void lockNow(Bundle bundle) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void markSurfaceSyncGroupReady(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean mirrorDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public SurfaceControl mirrorWallpaperSurface(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void moveDisplayToTop(int i, String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public List<ComponentName> notifyScreenshotListeners(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void onNotificationShadeExpanded(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void reenableKeyguard(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void refreshScreenCaptureDisabled() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) throws RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerTrustedPresentationListener(IBinder iBinder, ITrustedPresentationListener iTrustedPresentationListener, TrustedPresentationThresholds trustedPresentationThresholds, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void removeKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeWindowToken(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean reparentWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void requestMetaKeyEvent(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void restoreKeyCustomizationInfo(List<SemWindowManager.KeyCustomizationInfo> list) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void saveWindowTraceToFile() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public Bitmap screenshotWallpaper() throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setActiveTransactionTracing(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setAnimationScale(int i, float f) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setAnimationScales(float[] fArr) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setAppContinuityMode(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setConfigurationChangeSettingsForUser(List<ConfigurationChangeSetting> list, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDeadzoneHole(Bundle bundle) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayColorToSystemProperties(int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayHashThrottlingEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayImePolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDragSurfaceToOverlay(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setEventDispatching(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setFixedToUserRotation(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayDensityRatio(int i, float f, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayScalingMode(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setIgnoreOrientationRequest(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setInTouchMode(boolean z, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setInTouchModeOnAllDisplays(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setLayerTracing(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setLayerTracingFlags(int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setRecentsAppBehindSystemBars(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setRecentsVisibility(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setRemoveContentMode(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setShouldShowSystemDecors(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setShouldShowWithInsecureKeyguard(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setStrictModeVisualIndicatorPreference(String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setSupportsFlexPanel(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setSwitchingUser(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setTableModeEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setTaskSnapshotEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setWindowingMode(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean shouldShowSystemDecors(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean shouldShowWithInsecureKeyguard(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void showGlobalActions() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void showStrictModeViolation(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public Bitmap snapshotTaskForRecents(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void startLockscreenFingerprintAuth() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void startSurfaceAnimation(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void startTransitionTrace() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean startViewServer(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void startWindowTrace() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void stopTransitionTrace() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean stopViewServer() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void stopWindowTrace() throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void syncInputTransactions(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void thawDisplayRotation(int i, String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void thawRotation(String str) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterSystemKeyEvent(int i, ComponentName componentName) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterTrustedPresentationListener(ITrustedPresentationListener iTrustedPresentationListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void updateDisplayWindowAnimatingTypes(int i, int i2, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void updateDisplayWindowRequestedVisibleTypes(int i, int i2, int i3, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) throws RemoteException {
        }

        @Override // android.view.IWindowManager
        public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) throws RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException {
            return 0;
        }
    }

    void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException;

    SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) throws RemoteException;

    boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws RemoteException;

    void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException;

    WindowContextInfo attachWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException;

    WindowContextInfo attachWindowContextToDisplayContent(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException;

    WindowContextInfo attachWindowContextToWindowToken(IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2) throws RemoteException;

    void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException;

    void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) throws RemoteException;

    void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException;

    void clearForcedDisplaySize(int i) throws RemoteException;

    void clearForcedDisplaySizeDensity(int i) throws RemoteException;

    void clearKeyCustomizationInfoByAction(int i, int i2, int i3) throws RemoteException;

    void clearKeyCustomizationInfoByKeyCode(int i, int i2) throws RemoteException;

    boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException;

    void closeSystemDialogs(String str) throws RemoteException;

    void closeSystemDialogsInDisplay(String str, int i) throws RemoteException;

    void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) throws RemoteException;

    boolean destroyInputConsumer(IBinder iBinder, int i) throws RemoteException;

    void detachWindowContext(IBinder iBinder) throws RemoteException;

    @Deprecated
    void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException;

    void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

    void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) throws RemoteException;

    void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) throws RemoteException;

    @Deprecated
    void endProlongedAnimations() throws RemoteException;

    void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException;

    boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) throws RemoteException;

    void freezeDisplayRotation(int i, int i2, String str) throws RemoteException;

    void freezeRotation(int i, String str) throws RemoteException;

    float getAnimationScale(int i) throws RemoteException;

    float[] getAnimationScales() throws RemoteException;

    int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) throws RemoteException;

    KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i) throws RemoteException;

    List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList() throws RemoteException;

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

    boolean getIgnoreOrientationRequest(int i) throws RemoteException;

    int getImeDisplayId() throws RemoteException;

    int getInitialDisplayDensity(int i) throws RemoteException;

    void getInitialDisplaySize(int i, Point point) throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException;

    SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) throws RemoteException;

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

    boolean isEdgeToEdgeDisabled(String str) throws RemoteException;

    boolean isEligibleForDesktopMode(int i) throws RemoteException;

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

    boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z) throws RemoteException;

    void onNotificationShadeExpanded(IBinder iBinder, boolean z) throws RemoteException;

    IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException;

    void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) throws RemoteException;

    void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) throws RemoteException;

    void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) throws RemoteException;

    @Deprecated
    void reenableKeyguard(IBinder iBinder, int i) throws RemoteException;

    void refreshScreenCaptureDisabled() throws RemoteException;

    void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException;

    boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException;

    void registerDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException;

    void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException;

    int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException;

    boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException;

    void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException;

    void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) throws RemoteException;

    int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) throws RemoteException;

    boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException;

    void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException;

    void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException;

    void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws RemoteException;

    void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) throws RemoteException;

    void registerTrustedPresentationListener(IBinder iBinder, ITrustedPresentationListener iTrustedPresentationListener, TrustedPresentationThresholds trustedPresentationThresholds, int i) throws RemoteException;

    boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException;

    void removeKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException;

    void removeKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException;

    void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException;

    void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException;

    void removeWindowToken(IBinder iBinder, int i) throws RemoteException;

    boolean reparentWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException;

    boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException;

    void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void requestMetaKeyEvent(ComponentName componentName, boolean z) throws RemoteException;

    void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException;

    boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) throws RemoteException;

    void restoreKeyCustomizationInfo(List<SemWindowManager.KeyCustomizationInfo> list) throws RemoteException;

    void saveWindowTraceToFile() throws RemoteException;

    Bitmap screenshotWallpaper() throws RemoteException;

    void setActiveTransactionTracing(boolean z) throws RemoteException;

    void setAnimationScale(int i, float f) throws RemoteException;

    void setAnimationScales(float[] fArr) throws RemoteException;

    void setAppContinuityMode(int i, String str, boolean z) throws RemoteException;

    void setConfigurationChangeSettingsForUser(List<ConfigurationChangeSetting> list, int i) throws RemoteException;

    void setDeadzoneHole(Bundle bundle) throws RemoteException;

    void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) throws RemoteException;

    void setDisplayColorToSystemProperties(int i) throws RemoteException;

    void setDisplayHashThrottlingEnabled(boolean z) throws RemoteException;

    void setDisplayImePolicy(int i, int i2) throws RemoteException;

    void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) throws RemoteException;

    void setDragSurfaceToOverlay(boolean z) throws RemoteException;

    void setEventDispatching(boolean z) throws RemoteException;

    void setFixedToUserRotation(int i, int i2) throws RemoteException;

    void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException;

    void setForcedDisplayDensityRatio(int i, float f, int i2) throws RemoteException;

    void setForcedDisplayScalingMode(int i, int i2) throws RemoteException;

    void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException;

    void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) throws RemoteException;

    void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) throws RemoteException;

    void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException;

    void setIgnoreOrientationRequest(int i, boolean z) throws RemoteException;

    void setInTouchMode(boolean z, int i) throws RemoteException;

    void setInTouchModeOnAllDisplays(boolean z) throws RemoteException;

    void setLayerTracing(boolean z) throws RemoteException;

    void setLayerTracingFlags(int i) throws RemoteException;

    @Deprecated
    void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) throws RemoteException;

    void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException;

    void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException;

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

    void startLockscreenFingerprintAuth() throws RemoteException;

    boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i) throws RemoteException;

    void startSurfaceAnimation(IBinder iBinder, String str) throws RemoteException;

    void startTransitionTrace() throws RemoteException;

    boolean startViewServer(int i) throws RemoteException;

    void startWindowTrace() throws RemoteException;

    void stopTransitionTrace() throws RemoteException;

    boolean stopViewServer() throws RemoteException;

    void stopWindowTrace() throws RemoteException;

    void syncInputTransactions(boolean z) throws RemoteException;

    ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) throws RemoteException;

    ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) throws RemoteException;

    void thawDisplayRotation(int i, String str) throws RemoteException;

    void thawRotation(String str) throws RemoteException;

    boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) throws RemoteException;

    void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException;

    void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException;

    void unregisterDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException;

    void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException;

    void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException;

    void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException;

    void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException;

    void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException;

    void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException;

    void unregisterSystemKeyEvent(int i, ComponentName componentName) throws RemoteException;

    void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) throws RemoteException;

    void unregisterTrustedPresentationListener(ITrustedPresentationListener iTrustedPresentationListener, int i) throws RemoteException;

    void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException;

    void updateDisplayWindowAnimatingTypes(int i, int i2, ImeTracker.Token token) throws RemoteException;

    void updateDisplayWindowRequestedVisibleTypes(int i, int i2, int i3, ImeTracker.Token token) throws RemoteException;

    void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) throws RemoteException;

    VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) throws RemoteException;

    int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowManager {
        public static final String DESCRIPTOR = "android.view.IWindowManager";
        static final int TRANSACTION_addKeyguardLockedStateListener = 33;
        static final int TRANSACTION_addShellRoot = 22;
        static final int TRANSACTION_addToSurfaceSyncGroup = 149;
        static final int TRANSACTION_addWindowToken = 19;
        static final int TRANSACTION_attachWindowContextToDisplayArea = 130;
        static final int TRANSACTION_attachWindowContextToDisplayContent = 132;
        static final int TRANSACTION_attachWindowContextToWindowToken = 131;
        static final int TRANSACTION_captureDisplay = 147;
        static final int TRANSACTION_changeDisplayScale = 178;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 13;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearForcedDisplaySizeDensity = 172;
        static final int TRANSACTION_clearKeyCustomizationInfoByAction = 198;
        static final int TRANSACTION_clearKeyCustomizationInfoByKeyCode = 197;
        static final int TRANSACTION_clearWindowContentFrameStats = 77;
        static final int TRANSACTION_closeSystemDialogs = 36;
        static final int TRANSACTION_closeSystemDialogsInDisplay = 37;
        static final int TRANSACTION_createInputConsumer = 86;
        static final int TRANSACTION_destroyInputConsumer = 87;
        static final int TRANSACTION_detachWindowContext = 133;
        static final int TRANSACTION_disableKeyguard = 27;
        static final int TRANSACTION_dismissKeyguard = 32;
        static final int TRANSACTION_dispatchSPenGestureEvent = 202;
        static final int TRANSACTION_dispatchSmartClipRemoteRequest = 125;
        static final int TRANSACTION_endProlongedAnimations = 26;
        static final int TRANSACTION_exitKeyguardSecurely = 29;
        static final int TRANSACTION_finishRemoteWallpaperAnimation = 182;
        static final int TRANSACTION_freezeDisplayRotation = 58;
        static final int TRANSACTION_freezeRotation = 55;
        static final int TRANSACTION_getAnimationScale = 38;
        static final int TRANSACTION_getAnimationScales = 39;
        static final int TRANSACTION_getAppContinuityMode = 213;
        static final int TRANSACTION_getApplicationLaunchKeyboardShortcuts = 163;
        static final int TRANSACTION_getBackupKeyCustomizationInfoList = 199;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 42;
        static final int TRANSACTION_getCurrentImeTouchRegion = 88;
        static final int TRANSACTION_getDefaultDisplayRotation = 49;
        static final int TRANSACTION_getDisplayIdByUniqueId = 11;
        static final int TRANSACTION_getDisplayImePolicy = 109;
        static final int TRANSACTION_getDisplayUserRotation = 50;
        static final int TRANSACTION_getDockedStackSide = 79;
        static final int TRANSACTION_getFullScreenAppsSupportMode = 177;
        static final int TRANSACTION_getIgnoreOrientationRequest = 164;
        static final int TRANSACTION_getImeDisplayId = 138;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getKeyCustomizationInfo = 192;
        static final int TRANSACTION_getKeyCustomizationInfoByPackage = 193;
        static final int TRANSACTION_getLastKeyCustomizationInfo = 194;
        static final int TRANSACTION_getLetterboxBackgroundColorInArgb = 145;
        static final int TRANSACTION_getMaxAspectRatioPolicy = 166;
        static final int TRANSACTION_getOverrideStableInsets = 84;
        static final int TRANSACTION_getPossibleDisplayInfo = 120;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 54;
        static final int TRANSACTION_getRemoveContentMode = 102;
        static final int TRANSACTION_getRotationLockOrientation = 183;
        static final int TRANSACTION_getStableInsets = 83;
        static final int TRANSACTION_getSupportedDisplayHashAlgorithms = 127;
        static final int TRANSACTION_getSupportsFlexPanel = 175;
        static final int TRANSACTION_getTopFocusedDisplayId = 206;
        static final int TRANSACTION_getUserDisplayDensity = 171;
        static final int TRANSACTION_getUserDisplaySize = 170;
        static final int TRANSACTION_getVisibleWindowInfoList = 144;
        static final int TRANSACTION_getWindowContentFrameStats = 78;
        static final int TRANSACTION_getWindowInsets = 119;
        static final int TRANSACTION_getWindowingMode = 100;
        static final int TRANSACTION_hasNavigationBar = 74;
        static final int TRANSACTION_hasTaskbarTarget = 184;
        static final int TRANSACTION_hideTransientBars = 70;
        static final int TRANSACTION_holdLock = 126;
        static final int TRANSACTION_isDisplayRotationFrozen = 60;
        static final int TRANSACTION_isEdgeToEdgeDisabled = 218;
        static final int TRANSACTION_isEligibleForDesktopMode = 108;
        static final int TRANSACTION_isFolded = 210;
        static final int TRANSACTION_isGlobalKey = 148;
        static final int TRANSACTION_isInTouchMode = 45;
        static final int TRANSACTION_isKeyguardLocked = 30;
        static final int TRANSACTION_isKeyguardSecure = 31;
        static final int TRANSACTION_isKeyguardShowingAndNotOccluded = 204;
        static final int TRANSACTION_isLayerTracing = 113;
        static final int TRANSACTION_isLetterboxBackgroundMultiColored = 146;
        static final int TRANSACTION_isMetaKeyEventRequested = 190;
        static final int TRANSACTION_isRotationFrozen = 57;
        static final int TRANSACTION_isSafeModeEnabled = 76;
        static final int TRANSACTION_isSystemKeyEventRequested = 186;
        static final int TRANSACTION_isTableMode = 211;
        static final int TRANSACTION_isTaskSnapshotSupported = 137;
        static final int TRANSACTION_isTransitionTraceEnabled = 99;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowToken = 18;
        static final int TRANSACTION_isWindowTraceEnabled = 96;
        static final int TRANSACTION_lockNow = 75;
        static final int TRANSACTION_markSurfaceSyncGroupReady = 150;
        static final int TRANSACTION_mirrorDisplay = 115;
        static final int TRANSACTION_mirrorWallpaperSurface = 64;
        static final int TRANSACTION_moveDisplayToTop = 207;
        static final int TRANSACTION_notifyScreenshotListeners = 151;
        static final int TRANSACTION_omniRequestAssistScreenshot = 201;
        static final int TRANSACTION_onNotificationShadeExpanded = 111;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 24;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 25;
        static final int TRANSACTION_putKeyCustomizationInfo = 191;
        static final int TRANSACTION_reenableKeyguard = 28;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 48;
        static final int TRANSACTION_registerAuthTouchEventListener = 215;
        static final int TRANSACTION_registerCrossWindowBlurEnabledListener = 135;
        static final int TRANSACTION_registerDecorViewGestureListener = 153;
        static final int TRANSACTION_registerDisplayFoldListener = 89;
        static final int TRANSACTION_registerDisplayWindowListener = 91;
        static final int TRANSACTION_registerKnoxRemoteScreenCallback = 159;
        static final int TRANSACTION_registerOneHandOpWatcher = 179;
        static final int TRANSACTION_registerPinnedTaskListener = 80;
        static final int TRANSACTION_registerProposedRotationListener = 53;
        static final int TRANSACTION_registerScreenRecordingCallback = 157;
        static final int TRANSACTION_registerShortcutKey = 85;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 67;
        static final int TRANSACTION_registerSystemKeyEvent = 187;
        static final int TRANSACTION_registerTaskFpsCallback = 140;
        static final int TRANSACTION_registerTrustedPresentationListener = 155;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 65;
        static final int TRANSACTION_removeKeyCustomizationInfo = 195;
        static final int TRANSACTION_removeKeyCustomizationInfoByPackage = 196;
        static final int TRANSACTION_removeKeyguardLockedStateListener = 34;
        static final int TRANSACTION_removeRotationWatcher = 52;
        static final int TRANSACTION_removeWindowToken = 20;
        static final int TRANSACTION_reparentWindowContextToDisplayArea = 134;
        static final int TRANSACTION_replaceContentOnDisplay = 152;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 81;
        static final int TRANSACTION_requestAssistScreenshot = 69;
        static final int TRANSACTION_requestImeKeyboardShortcuts = 82;
        static final int TRANSACTION_requestMetaKeyEvent = 189;
        static final int TRANSACTION_requestScrollCapture = 124;
        static final int TRANSACTION_requestSystemKeyEvent = 185;
        static final int TRANSACTION_restoreKeyCustomizationInfo = 200;
        static final int TRANSACTION_saveWindowTraceToFile = 95;
        static final int TRANSACTION_screenshotWallpaper = 63;
        static final int TRANSACTION_setActiveTransactionTracing = 123;
        static final int TRANSACTION_setAnimationScale = 40;
        static final int TRANSACTION_setAnimationScales = 41;
        static final int TRANSACTION_setAppContinuityMode = 214;
        static final int TRANSACTION_setConfigurationChangeSettingsForUser = 15;
        static final int TRANSACTION_setDeadzoneHole = 165;
        static final int TRANSACTION_setDisplayChangeWindowController = 21;
        static final int TRANSACTION_setDisplayColorToSystemProperties = 217;
        static final int TRANSACTION_setDisplayHashThrottlingEnabled = 129;
        static final int TRANSACTION_setDisplayImePolicy = 110;
        static final int TRANSACTION_setDisplayWindowInsetsController = 116;
        static final int TRANSACTION_setDragSurfaceToOverlay = 208;
        static final int TRANSACTION_setEventDispatching = 17;
        static final int TRANSACTION_setFixedToUserRotation = 61;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_setForcedDisplayDensityRatio = 14;
        static final int TRANSACTION_setForcedDisplayScalingMode = 16;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setForcedDisplaySizeDensity = 173;
        static final int TRANSACTION_setForcedDisplaySizeDensityWithInfo = 174;
        static final int TRANSACTION_setGlobalDragListener = 161;
        static final int TRANSACTION_setIgnoreOrientationRequest = 62;
        static final int TRANSACTION_setInTouchMode = 43;
        static final int TRANSACTION_setInTouchModeOnAllDisplays = 44;
        static final int TRANSACTION_setLayerTracing = 114;
        static final int TRANSACTION_setLayerTracingFlags = 122;
        static final int TRANSACTION_setMaxAspectRatioPolicy = 167;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 73;
        static final int TRANSACTION_setPendingIntentAfterUnlock = 203;
        static final int TRANSACTION_setRecentsAppBehindSystemBars = 143;
        static final int TRANSACTION_setRecentsVisibility = 71;
        static final int TRANSACTION_setRemoveContentMode = 103;
        static final int TRANSACTION_setShellRootAccessibilityWindow = 23;
        static final int TRANSACTION_setShouldShowSystemDecors = 107;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 105;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 47;
        static final int TRANSACTION_setSupportsFlexPanel = 176;
        static final int TRANSACTION_setSwitchingUser = 35;
        static final int TRANSACTION_setTableModeEnabled = 212;
        static final int TRANSACTION_setTaskSnapshotEnabled = 139;
        static final int TRANSACTION_setWindowingMode = 101;
        static final int TRANSACTION_shouldShowSystemDecors = 106;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 104;
        static final int TRANSACTION_showGlobalActions = 121;
        static final int TRANSACTION_showStrictModeViolation = 46;
        static final int TRANSACTION_snapshotTaskForRecents = 142;
        static final int TRANSACTION_startLockscreenFingerprintAuth = 205;
        static final int TRANSACTION_startRemoteWallpaperAnimation = 181;
        static final int TRANSACTION_startSurfaceAnimation = 209;
        static final int TRANSACTION_startTransitionTrace = 97;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 93;
        static final int TRANSACTION_stopTransitionTrace = 98;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 94;
        static final int TRANSACTION_syncInputTransactions = 112;
        static final int TRANSACTION_takeScreenshotToTargetWindow = 168;
        static final int TRANSACTION_takeScreenshotToTargetWindowFromCapture = 169;
        static final int TRANSACTION_thawDisplayRotation = 59;
        static final int TRANSACTION_thawRotation = 56;
        static final int TRANSACTION_transferTouchGesture = 162;
        static final int TRANSACTION_unregisterAuthTouchEventListener = 216;
        static final int TRANSACTION_unregisterCrossWindowBlurEnabledListener = 136;
        static final int TRANSACTION_unregisterDecorViewGestureListener = 154;
        static final int TRANSACTION_unregisterDisplayFoldListener = 90;
        static final int TRANSACTION_unregisterDisplayWindowListener = 92;
        static final int TRANSACTION_unregisterKnoxRemoteScreenCallback = 160;
        static final int TRANSACTION_unregisterOneHandOpWatcher = 180;
        static final int TRANSACTION_unregisterScreenRecordingCallback = 158;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 68;
        static final int TRANSACTION_unregisterSystemKeyEvent = 188;
        static final int TRANSACTION_unregisterTaskFpsCallback = 141;
        static final int TRANSACTION_unregisterTrustedPresentationListener = 156;
        static final int TRANSACTION_unregisterWallpaperVisibilityListener = 66;
        static final int TRANSACTION_updateDisplayWindowAnimatingTypes = 118;
        static final int TRANSACTION_updateDisplayWindowRequestedVisibleTypes = 117;
        static final int TRANSACTION_updateStaticPrivacyIndicatorBounds = 72;
        static final int TRANSACTION_verifyDisplayHash = 128;
        static final int TRANSACTION_watchRotation = 51;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 217;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IWindowManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWindowManager)) {
                return (IWindowManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "setForcedDisplayDensityRatio";
                case 15:
                    return "setConfigurationChangeSettingsForUser";
                case 16:
                    return "setForcedDisplayScalingMode";
                case 17:
                    return "setEventDispatching";
                case 18:
                    return "isWindowToken";
                case 19:
                    return "addWindowToken";
                case 20:
                    return "removeWindowToken";
                case 21:
                    return "setDisplayChangeWindowController";
                case 22:
                    return "addShellRoot";
                case 23:
                    return "setShellRootAccessibilityWindow";
                case 24:
                    return "overridePendingAppTransitionMultiThumbFuture";
                case 25:
                    return "overridePendingAppTransitionRemote";
                case 26:
                    return "endProlongedAnimations";
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
                    return "isEligibleForDesktopMode";
                case 109:
                    return "getDisplayImePolicy";
                case 110:
                    return "setDisplayImePolicy";
                case 111:
                    return "onNotificationShadeExpanded";
                case 112:
                    return "syncInputTransactions";
                case 113:
                    return "isLayerTracing";
                case 114:
                    return "setLayerTracing";
                case 115:
                    return "mirrorDisplay";
                case 116:
                    return "setDisplayWindowInsetsController";
                case 117:
                    return "updateDisplayWindowRequestedVisibleTypes";
                case 118:
                    return "updateDisplayWindowAnimatingTypes";
                case 119:
                    return "getWindowInsets";
                case 120:
                    return "getPossibleDisplayInfo";
                case 121:
                    return "showGlobalActions";
                case 122:
                    return "setLayerTracingFlags";
                case 123:
                    return "setActiveTransactionTracing";
                case 124:
                    return "requestScrollCapture";
                case 125:
                    return "dispatchSmartClipRemoteRequest";
                case 126:
                    return "holdLock";
                case 127:
                    return "getSupportedDisplayHashAlgorithms";
                case 128:
                    return "verifyDisplayHash";
                case 129:
                    return "setDisplayHashThrottlingEnabled";
                case 130:
                    return "attachWindowContextToDisplayArea";
                case 131:
                    return "attachWindowContextToWindowToken";
                case 132:
                    return "attachWindowContextToDisplayContent";
                case 133:
                    return "detachWindowContext";
                case 134:
                    return "reparentWindowContextToDisplayArea";
                case 135:
                    return "registerCrossWindowBlurEnabledListener";
                case 136:
                    return "unregisterCrossWindowBlurEnabledListener";
                case 137:
                    return "isTaskSnapshotSupported";
                case 138:
                    return "getImeDisplayId";
                case 139:
                    return "setTaskSnapshotEnabled";
                case 140:
                    return "registerTaskFpsCallback";
                case 141:
                    return "unregisterTaskFpsCallback";
                case 142:
                    return "snapshotTaskForRecents";
                case 143:
                    return "setRecentsAppBehindSystemBars";
                case 144:
                    return "getVisibleWindowInfoList";
                case 145:
                    return "getLetterboxBackgroundColorInArgb";
                case 146:
                    return "isLetterboxBackgroundMultiColored";
                case 147:
                    return "captureDisplay";
                case 148:
                    return "isGlobalKey";
                case 149:
                    return "addToSurfaceSyncGroup";
                case 150:
                    return "markSurfaceSyncGroupReady";
                case 151:
                    return "notifyScreenshotListeners";
                case 152:
                    return "replaceContentOnDisplay";
                case 153:
                    return "registerDecorViewGestureListener";
                case 154:
                    return "unregisterDecorViewGestureListener";
                case 155:
                    return "registerTrustedPresentationListener";
                case 156:
                    return "unregisterTrustedPresentationListener";
                case 157:
                    return "registerScreenRecordingCallback";
                case 158:
                    return "unregisterScreenRecordingCallback";
                case 159:
                    return "registerKnoxRemoteScreenCallback";
                case 160:
                    return "unregisterKnoxRemoteScreenCallback";
                case 161:
                    return "setGlobalDragListener";
                case 162:
                    return "transferTouchGesture";
                case 163:
                    return "getApplicationLaunchKeyboardShortcuts";
                case 164:
                    return "getIgnoreOrientationRequest";
                case 165:
                    return "setDeadzoneHole";
                case 166:
                    return "getMaxAspectRatioPolicy";
                case 167:
                    return "setMaxAspectRatioPolicy";
                case 168:
                    return "takeScreenshotToTargetWindow";
                case 169:
                    return "takeScreenshotToTargetWindowFromCapture";
                case 170:
                    return "getUserDisplaySize";
                case 171:
                    return "getUserDisplayDensity";
                case 172:
                    return "clearForcedDisplaySizeDensity";
                case 173:
                    return "setForcedDisplaySizeDensity";
                case 174:
                    return "setForcedDisplaySizeDensityWithInfo";
                case 175:
                    return "getSupportsFlexPanel";
                case 176:
                    return "setSupportsFlexPanel";
                case 177:
                    return "getFullScreenAppsSupportMode";
                case 178:
                    return "changeDisplayScale";
                case 179:
                    return "registerOneHandOpWatcher";
                case 180:
                    return "unregisterOneHandOpWatcher";
                case 181:
                    return "startRemoteWallpaperAnimation";
                case 182:
                    return "finishRemoteWallpaperAnimation";
                case 183:
                    return "getRotationLockOrientation";
                case 184:
                    return "hasTaskbarTarget";
                case 185:
                    return "requestSystemKeyEvent";
                case 186:
                    return "isSystemKeyEventRequested";
                case 187:
                    return "registerSystemKeyEvent";
                case 188:
                    return "unregisterSystemKeyEvent";
                case 189:
                    return "requestMetaKeyEvent";
                case 190:
                    return "isMetaKeyEventRequested";
                case 191:
                    return "putKeyCustomizationInfo";
                case 192:
                    return "getKeyCustomizationInfo";
                case 193:
                    return "getKeyCustomizationInfoByPackage";
                case 194:
                    return "getLastKeyCustomizationInfo";
                case 195:
                    return "removeKeyCustomizationInfo";
                case 196:
                    return "removeKeyCustomizationInfoByPackage";
                case 197:
                    return "clearKeyCustomizationInfoByKeyCode";
                case 198:
                    return "clearKeyCustomizationInfoByAction";
                case 199:
                    return "getBackupKeyCustomizationInfoList";
                case 200:
                    return "restoreKeyCustomizationInfo";
                case 201:
                    return "omniRequestAssistScreenshot";
                case 202:
                    return "dispatchSPenGestureEvent";
                case 203:
                    return "setPendingIntentAfterUnlock";
                case 204:
                    return "isKeyguardShowingAndNotOccluded";
                case 205:
                    return "startLockscreenFingerprintAuth";
                case 206:
                    return "getTopFocusedDisplayId";
                case 207:
                    return "moveDisplayToTop";
                case 208:
                    return "setDragSurfaceToOverlay";
                case 209:
                    return "startSurfaceAnimation";
                case 210:
                    return "isFolded";
                case 211:
                    return "isTableMode";
                case 212:
                    return "setTableModeEnabled";
                case 213:
                    return "getAppContinuityMode";
                case 214:
                    return "setAppContinuityMode";
                case 215:
                    return "registerAuthTouchEventListener";
                case 216:
                    return "unregisterAuthTouchEventListener";
                case 217:
                    return "setDisplayColorToSystemProperties";
                case 218:
                    return "isEdgeToEdgeDisabled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startViewServer = startViewServer(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startViewServer);
                    return true;
                case 2:
                    boolean stopViewServer = stopViewServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopViewServer);
                    return true;
                case 3:
                    boolean isViewServerRunning = isViewServerRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isViewServerRunning);
                    return true;
                case 4:
                    IWindowSessionCallback asInterface = IWindowSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IWindowSession openSession = openSession(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    Point point = new Point();
                    parcel.enforceNoDataAvail();
                    getInitialDisplaySize(readInt2, point);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point, 1);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    Point point2 = new Point();
                    parcel.enforceNoDataAvail();
                    getBaseDisplaySize(readInt3, point2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point2, 1);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySize(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplaySize(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialDisplayDensity = getInitialDisplayDensity(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialDisplayDensity);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int baseDisplayDensity = getBaseDisplayDensity(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(baseDisplayDensity);
                    return true;
                case 11:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int displayIdByUniqueId = getDisplayIdByUniqueId(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayIdByUniqueId);
                    return true;
                case 12:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayDensityForUser(readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplayDensityForUser(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayDensityRatio(readInt15, readFloat, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ConfigurationChangeSetting.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setConfigurationChangeSettingsForUser(createTypedArrayList, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayScalingMode(readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEventDispatching(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isWindowToken = isWindowToken(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWindowToken);
                    return true;
                case 19:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addWindowToken(readStrongBinder2, readInt20, readInt21, bundle);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWindowToken(readStrongBinder3, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IDisplayChangeWindowController asInterface2 = IDisplayChangeWindowController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayChangeWindowController(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt23 = parcel.readInt();
                    IWindow asInterface3 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceControl addShellRoot = addShellRoot(readInt23, asInterface3, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addShellRoot, 1);
                    return true;
                case 23:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    IWindow asInterface4 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setShellRootAccessibilityWindow(readInt25, readInt26, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IAppTransitionAnimationSpecsFuture asInterface5 = IAppTransitionAnimationSpecsFuture.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteCallback asInterface6 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionMultiThumbFuture(asInterface5, asInterface6, readBoolean2, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionRemote(remoteAnimationAdapter, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    endProlongedAnimations();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString2 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableKeyguard(readStrongBinder4, readString2, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reenableKeyguard(readStrongBinder5, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IOnKeyguardExitResult asInterface7 = IOnKeyguardExitResult.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    exitKeyguardSecurely(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean isKeyguardLocked = isKeyguardLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyguardLocked);
                    return true;
                case 31:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKeyguardSecure = isKeyguardSecure(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyguardSecure);
                    return true;
                case 32:
                    IKeyguardDismissCallback asInterface8 = IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(asInterface8, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IKeyguardLockedStateListener asInterface9 = IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addKeyguardLockedStateListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IKeyguardLockedStateListener asInterface10 = IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeKeyguardLockedStateListener(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSwitchingUser(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readString3);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString4 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(readString4, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float animationScale = getAnimationScale(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeFloat(animationScale);
                    return true;
                case 39:
                    float[] animationScales = getAnimationScales();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(animationScales);
                    return true;
                case 40:
                    int readInt34 = parcel.readInt();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(readInt34, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setAnimationScales(createFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    float currentAnimatorScale = getCurrentAnimatorScale();
                    parcel2.writeNoException();
                    parcel2.writeFloat(currentAnimatorScale);
                    return true;
                case 43:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInTouchMode(readBoolean4, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInTouchModeOnAllDisplays(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInTouchMode = isInTouchMode(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInTouchMode);
                    return true;
                case 46:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showStrictModeViolation(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStrictModeVisualIndicatorPreference(readString5);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    refreshScreenCaptureDisabled();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int defaultDisplayRotation = getDefaultDisplayRotation();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultDisplayRotation);
                    return true;
                case 50:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayUserRotation = getDisplayUserRotation(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayUserRotation);
                    return true;
                case 51:
                    IRotationWatcher asInterface11 = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int watchRotation = watchRotation(asInterface11, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(watchRotation);
                    return true;
                case 52:
                    IRotationWatcher asInterface12 = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRotationWatcher(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    IRotationWatcher asInterface13 = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerProposedRotationListener = registerProposedRotationListener(readStrongBinder6, asInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerProposedRotationListener);
                    return true;
                case 54:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int preferredOptionsPanelGravity = getPreferredOptionsPanelGravity(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredOptionsPanelGravity);
                    return true;
                case 55:
                    int readInt40 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeRotation(readInt40, readString6);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawRotation(readString7);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    boolean isRotationFrozen = isRotationFrozen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRotationFrozen);
                    return true;
                case 58:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeDisplayRotation(readInt41, readInt42, readString8);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt43 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawDisplayRotation(readInt43, readString9);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDisplayRotationFrozen = isDisplayRotationFrozen(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayRotationFrozen);
                    return true;
                case 61:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFixedToUserRotation(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt47 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreOrientationRequest(readInt47, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    Bitmap screenshotWallpaper = screenshotWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotWallpaper, 1);
                    return true;
                case 64:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceControl mirrorWallpaperSurface = mirrorWallpaperSurface(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mirrorWallpaperSurface, 1);
                    return true;
                case 65:
                    IWallpaperVisibilityListener asInterface14 = IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerWallpaperVisibilityListener = registerWallpaperVisibilityListener(asInterface14, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerWallpaperVisibilityListener);
                    return true;
                case 66:
                    IWallpaperVisibilityListener asInterface15 = IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperVisibilityListener(asInterface15, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    ISystemGestureExclusionListener asInterface16 = ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemGestureExclusionListener(asInterface16, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    ISystemGestureExclusionListener asInterface17 = ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemGestureExclusionListener(asInterface17, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    IAssistDataReceiver asInterface18 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean requestAssistScreenshot = requestAssistScreenshot(asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistScreenshot);
                    return true;
                case 70:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideTransientBars(readInt53);
                    return true;
                case 71:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsVisibility(readBoolean8);
                    return true;
                case 72:
                    int readInt54 = parcel.readInt();
                    Rect[] rectArr = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateStaticPrivacyIndicatorBounds(readInt54, rectArr);
                    return true;
                case 73:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavBarVirtualKeyHapticFeedbackEnabled(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasNavigationBar = hasNavigationBar(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNavigationBar);
                    return true;
                case 75:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    lockNow(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean isSafeModeEnabled = isSafeModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeModeEnabled);
                    return true;
                case 77:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean clearWindowContentFrameStats = clearWindowContentFrameStats(readStrongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearWindowContentFrameStats);
                    return true;
                case 78:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    WindowContentFrameStats windowContentFrameStats = getWindowContentFrameStats(readStrongBinder8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContentFrameStats, 1);
                    return true;
                case 79:
                    int dockedStackSide = getDockedStackSide();
                    parcel2.writeNoException();
                    parcel2.writeInt(dockedStackSide);
                    return true;
                case 80:
                    int readInt56 = parcel.readInt();
                    IPinnedTaskListener asInterface19 = IPinnedTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPinnedTaskListener(readInt56, asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    IResultReceiver asInterface20 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(asInterface20, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    IResultReceiver asInterface21 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestImeKeyboardShortcuts(asInterface21, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int readInt59 = parcel.readInt();
                    Rect rect = new Rect();
                    parcel.enforceNoDataAvail();
                    getStableInsets(readInt59, rect);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect, 1);
                    return true;
                case 84:
                    int readInt60 = parcel.readInt();
                    Rect rect2 = new Rect();
                    parcel.enforceNoDataAvail();
                    getOverrideStableInsets(readInt60, rect2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect2, 1);
                    return true;
                case 85:
                    long readLong = parcel.readLong();
                    IShortcutService asInterface22 = IShortcutService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerShortcutKey(readLong, asInterface22);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    String readString10 = parcel.readString();
                    int readInt61 = parcel.readInt();
                    InputChannel inputChannel = new InputChannel();
                    parcel.enforceNoDataAvail();
                    createInputConsumer(readStrongBinder9, readString10, readInt61, inputChannel);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel, 1);
                    return true;
                case 87:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean destroyInputConsumer = destroyInputConsumer(readStrongBinder10, readInt62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(destroyInputConsumer);
                    return true;
                case 88:
                    Region currentImeTouchRegion = getCurrentImeTouchRegion();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentImeTouchRegion, 1);
                    return true;
                case 89:
                    IDisplayFoldListener asInterface23 = IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDisplayFoldListener(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    IDisplayFoldListener asInterface24 = IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayFoldListener(asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    IDisplayWindowListener asInterface25 = IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int[] registerDisplayWindowListener = registerDisplayWindowListener(asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(registerDisplayWindowListener);
                    return true;
                case 92:
                    IDisplayWindowListener asInterface26 = IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayWindowListener(asInterface26);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    startWindowTrace();
                    parcel2.writeNoException();
                    return true;
                case 94:
                    stopWindowTrace();
                    parcel2.writeNoException();
                    return true;
                case 95:
                    saveWindowTraceToFile();
                    parcel2.writeNoException();
                    return true;
                case 96:
                    boolean isWindowTraceEnabled = isWindowTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWindowTraceEnabled);
                    return true;
                case 97:
                    startTransitionTrace();
                    parcel2.writeNoException();
                    return true;
                case 98:
                    stopTransitionTrace();
                    parcel2.writeNoException();
                    return true;
                case 99:
                    boolean isTransitionTraceEnabled = isTransitionTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTransitionTraceEnabled);
                    return true;
                case 100:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int windowingMode = getWindowingMode(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowingMode);
                    return true;
                case 101:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowingMode(readInt64, readInt65);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeContentMode = getRemoveContentMode(readInt66);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContentMode);
                    return true;
                case 103:
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRemoveContentMode(readInt67, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowWithInsecureKeyguard = shouldShowWithInsecureKeyguard(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowWithInsecureKeyguard);
                    return true;
                case 105:
                    int readInt70 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowWithInsecureKeyguard(readInt70, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowSystemDecors = shouldShowSystemDecors(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowSystemDecors);
                    return true;
                case 107:
                    int readInt72 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowSystemDecors(readInt72, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isEligibleForDesktopMode = isEligibleForDesktopMode(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEligibleForDesktopMode);
                    return true;
                case 109:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayImePolicy = getDisplayImePolicy(readInt74);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayImePolicy);
                    return true;
                case 110:
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(readInt75, readInt76);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationShadeExpanded(readStrongBinder11, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    syncInputTransactions(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    boolean isLayerTracing = isLayerTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLayerTracing);
                    return true;
                case 114:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLayerTracing(readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    int readInt77 = parcel.readInt();
                    SurfaceControl surfaceControl = new SurfaceControl();
                    parcel.enforceNoDataAvail();
                    boolean mirrorDisplay = mirrorDisplay(readInt77, surfaceControl);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mirrorDisplay);
                    parcel2.writeTypedObject(surfaceControl, 1);
                    return true;
                case 116:
                    int readInt78 = parcel.readInt();
                    IDisplayWindowInsetsController asInterface27 = IDisplayWindowInsetsController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayWindowInsetsController(readInt78, asInterface27);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDisplayWindowRequestedVisibleTypes(readInt79, readInt80, readInt81, token);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDisplayWindowAnimatingTypes(readInt82, readInt83, token2);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    int readInt84 = parcel.readInt();
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    InsetsState insetsState = new InsetsState();
                    parcel.enforceNoDataAvail();
                    boolean windowInsets = getWindowInsets(readInt84, readStrongBinder12, insetsState);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(windowInsets);
                    parcel2.writeTypedObject(insetsState, 1);
                    return true;
                case 120:
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DisplayInfo> possibleDisplayInfo = getPossibleDisplayInfo(readInt85);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(possibleDisplayInfo, 1);
                    return true;
                case 121:
                    showGlobalActions();
                    parcel2.writeNoException();
                    return true;
                case 122:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLayerTracingFlags(readInt86);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActiveTransactionTracing(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    int readInt87 = parcel.readInt();
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt88 = parcel.readInt();
                    IScrollCaptureResponseListener asInterface28 = IScrollCaptureResponseListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestScrollCapture(readInt87, readStrongBinder13, readInt88, asInterface28);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    int readInt89 = parcel.readInt();
                    int readInt90 = parcel.readInt();
                    SmartClipRemoteRequestInfo smartClipRemoteRequestInfo = (SmartClipRemoteRequestInfo) parcel.readTypedObject(SmartClipRemoteRequestInfo.CREATOR);
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    dispatchSmartClipRemoteRequest(readInt89, readInt90, smartClipRemoteRequestInfo, readStrongBinder14);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder15, readInt91);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    String[] supportedDisplayHashAlgorithms = getSupportedDisplayHashAlgorithms();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(supportedDisplayHashAlgorithms);
                    return true;
                case 128:
                    DisplayHash displayHash = (DisplayHash) parcel.readTypedObject(DisplayHash.CREATOR);
                    parcel.enforceNoDataAvail();
                    VerifiedDisplayHash verifyDisplayHash = verifyDisplayHash(displayHash);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyDisplayHash, 1);
                    return true;
                case 129:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisplayHashThrottlingEnabled(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    IApplicationThread asInterface29 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    WindowContextInfo attachWindowContextToDisplayArea = attachWindowContextToDisplayArea(asInterface29, readStrongBinder16, readInt92, readInt93, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToDisplayArea, 1);
                    return true;
                case 131:
                    IApplicationThread asInterface30 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    WindowContextInfo attachWindowContextToWindowToken = attachWindowContextToWindowToken(asInterface30, readStrongBinder17, readStrongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToWindowToken, 1);
                    return true;
                case 132:
                    IApplicationThread asInterface31 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowContextInfo attachWindowContextToDisplayContent = attachWindowContextToDisplayContent(asInterface31, readStrongBinder19, readInt94);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToDisplayContent, 1);
                    return true;
                case 133:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachWindowContext(readStrongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    IApplicationThread asInterface32 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reparentWindowContextToDisplayArea = reparentWindowContextToDisplayArea(asInterface32, readStrongBinder21, readInt95);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reparentWindowContextToDisplayArea);
                    return true;
                case 135:
                    ICrossWindowBlurEnabledListener asInterface33 = ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCrossWindowBlurEnabledListener = registerCrossWindowBlurEnabledListener(asInterface33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCrossWindowBlurEnabledListener);
                    return true;
                case 136:
                    ICrossWindowBlurEnabledListener asInterface34 = ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCrossWindowBlurEnabledListener(asInterface34);
                    parcel2.writeNoException();
                    return true;
                case 137:
                    boolean isTaskSnapshotSupported = isTaskSnapshotSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTaskSnapshotSupported);
                    return true;
                case 138:
                    int imeDisplayId = getImeDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(imeDisplayId);
                    return true;
                case 139:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTaskSnapshotEnabled(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    int readInt96 = parcel.readInt();
                    ITaskFpsCallback asInterface35 = ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskFpsCallback(readInt96, asInterface35);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    ITaskFpsCallback asInterface36 = ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskFpsCallback(asInterface36);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap snapshotTaskForRecents = snapshotTaskForRecents(readInt97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(snapshotTaskForRecents, 1);
                    return true;
                case 143:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsAppBehindSystemBars(readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    List<SemWindowManager.VisibleWindowInfo> visibleWindowInfoList = getVisibleWindowInfoList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(visibleWindowInfoList, 1);
                    return true;
                case 145:
                    int letterboxBackgroundColorInArgb = getLetterboxBackgroundColorInArgb();
                    parcel2.writeNoException();
                    parcel2.writeInt(letterboxBackgroundColorInArgb);
                    return true;
                case 146:
                    boolean isLetterboxBackgroundMultiColored = isLetterboxBackgroundMultiColored();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLetterboxBackgroundMultiColored);
                    return true;
                case 147:
                    int readInt98 = parcel.readInt();
                    ScreenCapture.CaptureArgs captureArgs = (ScreenCapture.CaptureArgs) parcel.readTypedObject(ScreenCapture.CaptureArgs.CREATOR);
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    captureDisplay(readInt98, captureArgs, screenCaptureListener);
                    return true;
                case 148:
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGlobalKey = isGlobalKey(readInt99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGlobalKey);
                    return true;
                case 149:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    boolean readBoolean19 = parcel.readBoolean();
                    ISurfaceSyncGroupCompletedListener asInterface37 = ISurfaceSyncGroupCompletedListener.Stub.asInterface(parcel.readStrongBinder());
                    AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new AddToSurfaceSyncGroupResult();
                    parcel.enforceNoDataAvail();
                    boolean addToSurfaceSyncGroup = addToSurfaceSyncGroup(readStrongBinder22, readBoolean19, asInterface37, addToSurfaceSyncGroupResult);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addToSurfaceSyncGroup);
                    parcel2.writeTypedObject(addToSurfaceSyncGroupResult, 1);
                    return true;
                case 150:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    markSurfaceSyncGroupReady(readStrongBinder23);
                    return true;
                case 151:
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> notifyScreenshotListeners = notifyScreenshotListeners(readInt100);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(notifyScreenshotListeners, 1);
                    return true;
                case 152:
                    int readInt101 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean replaceContentOnDisplay = replaceContentOnDisplay(readInt101, surfaceControl2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(replaceContentOnDisplay);
                    return true;
                case 153:
                    IDecorViewGestureListener asInterface38 = IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDecorViewGestureListener(asInterface38, readInt102);
                    parcel2.writeNoException();
                    return true;
                case 154:
                    IDecorViewGestureListener asInterface39 = IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterDecorViewGestureListener(asInterface39, readInt103);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    IBinder readStrongBinder24 = parcel.readStrongBinder();
                    ITrustedPresentationListener asInterface40 = ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    TrustedPresentationThresholds trustedPresentationThresholds = (TrustedPresentationThresholds) parcel.readTypedObject(TrustedPresentationThresholds.CREATOR);
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTrustedPresentationListener(readStrongBinder24, asInterface40, trustedPresentationThresholds, readInt104);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    ITrustedPresentationListener asInterface41 = ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTrustedPresentationListener(asInterface41, readInt105);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    IScreenRecordingCallback asInterface42 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerScreenRecordingCallback = registerScreenRecordingCallback(asInterface42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerScreenRecordingCallback);
                    return true;
                case 158:
                    IScreenRecordingCallback asInterface43 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenRecordingCallback(asInterface43);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    IScreenRecordingCallback asInterface44 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerKnoxRemoteScreenCallback = registerKnoxRemoteScreenCallback(asInterface44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerKnoxRemoteScreenCallback);
                    return true;
                case 160:
                    IScreenRecordingCallback asInterface45 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKnoxRemoteScreenCallback(asInterface45);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    IGlobalDragListener asInterface46 = IGlobalDragListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setGlobalDragListener(asInterface46);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    InputTransferToken inputTransferToken = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    InputTransferToken inputTransferToken2 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean transferTouchGesture = transferTouchGesture(inputTransferToken, inputTransferToken2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transferTouchGesture);
                    return true;
                case 163:
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyboardShortcutGroup applicationLaunchKeyboardShortcuts = getApplicationLaunchKeyboardShortcuts(readInt106);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationLaunchKeyboardShortcuts, 1);
                    return true;
                case 164:
                    int readInt107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean ignoreOrientationRequest = getIgnoreOrientationRequest(readInt107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ignoreOrientationRequest);
                    return true;
                case 165:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeadzoneHole(bundle4);
                    parcel2.writeNoException();
                    return true;
                case 166:
                    String readString11 = parcel.readString();
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxAspectRatioPolicy = getMaxAspectRatioPolicy(readString11, readInt108);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxAspectRatioPolicy);
                    return true;
                case 167:
                    String readString12 = parcel.readString();
                    int readInt109 = parcel.readInt();
                    boolean readBoolean20 = parcel.readBoolean();
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxAspectRatioPolicy(readString12, readInt109, readBoolean20, readInt110);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    int readInt111 = parcel.readInt();
                    int readInt112 = parcel.readInt();
                    boolean readBoolean21 = parcel.readBoolean();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt113 = parcel.readInt();
                    int readInt114 = parcel.readInt();
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ScreenshotResult takeScreenshotToTargetWindow = takeScreenshotToTargetWindow(readInt111, readInt112, readBoolean21, rect3, readInt113, readInt114, readBoolean22, readBoolean23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeScreenshotToTargetWindow, 1);
                    return true;
                case 169:
                    int readInt115 = parcel.readInt();
                    int readInt116 = parcel.readInt();
                    boolean readBoolean24 = parcel.readBoolean();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt117 = parcel.readInt();
                    int readInt118 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    boolean readBoolean26 = parcel.readBoolean();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ScreenshotResult takeScreenshotToTargetWindowFromCapture = takeScreenshotToTargetWindowFromCapture(readInt115, readInt116, readBoolean24, rect4, readInt117, readInt118, readBoolean25, readBoolean26, readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeScreenshotToTargetWindowFromCapture, 1);
                    return true;
                case 170:
                    Point point3 = new Point();
                    parcel.enforceNoDataAvail();
                    getUserDisplaySize(point3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point3, 1);
                    return true;
                case 171:
                    int userDisplayDensity = getUserDisplayDensity();
                    parcel2.writeNoException();
                    parcel2.writeInt(userDisplayDensity);
                    return true;
                case 172:
                    int readInt119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplaySizeDensity(readInt119);
                    parcel2.writeNoException();
                    return true;
                case 173:
                    int readInt120 = parcel.readInt();
                    int readInt121 = parcel.readInt();
                    int readInt122 = parcel.readInt();
                    int readInt123 = parcel.readInt();
                    boolean readBoolean28 = parcel.readBoolean();
                    int readInt124 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySizeDensity(readInt120, readInt121, readInt122, readInt123, readBoolean28, readInt124);
                    parcel2.writeNoException();
                    return true;
                case 174:
                    MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo = (MultiResolutionChangeRequestInfo) parcel.readTypedObject(MultiResolutionChangeRequestInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySizeDensityWithInfo(multiResolutionChangeRequestInfo);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    int readInt125 = parcel.readInt();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int supportsFlexPanel = getSupportsFlexPanel(readInt125, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportsFlexPanel);
                    return true;
                case 176:
                    int readInt126 = parcel.readInt();
                    String readString14 = parcel.readString();
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSupportsFlexPanel(readInt126, readString14, readBoolean29);
                    parcel2.writeNoException();
                    return true;
                case 177:
                    int fullScreenAppsSupportMode = getFullScreenAppsSupportMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(fullScreenAppsSupportMode);
                    return true;
                case 178:
                    MagnificationSpec magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    boolean readBoolean30 = parcel.readBoolean();
                    IInputFilter asInterface47 = IInputFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeDisplayScale(magnificationSpec, readBoolean30, asInterface47);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    IOneHandOpWatcher asInterface48 = IOneHandOpWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerOneHandOpWatcher(asInterface48);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    IOneHandOpWatcher asInterface49 = IOneHandOpWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOneHandOpWatcher(asInterface49);
                    parcel2.writeNoException();
                    return true;
                case 181:
                    IRemoteAnimationRunner asInterface50 = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                    int readInt127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startRemoteWallpaperAnimation = startRemoteWallpaperAnimation(asInterface50, readInt127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startRemoteWallpaperAnimation);
                    return true;
                case 182:
                    IRemoteAnimationRunner asInterface51 = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean finishRemoteWallpaperAnimation = finishRemoteWallpaperAnimation(asInterface51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishRemoteWallpaperAnimation);
                    return true;
                case 183:
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rotationLockOrientation = getRotationLockOrientation(readInt128);
                    parcel2.writeNoException();
                    parcel2.writeInt(rotationLockOrientation);
                    return true;
                case 184:
                    boolean hasTaskbarTarget = hasTaskbarTarget();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasTaskbarTarget);
                    return true;
                case 185:
                    int readInt129 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestSystemKeyEvent = requestSystemKeyEvent(readInt129, componentName, readBoolean31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestSystemKeyEvent);
                    return true;
                case 186:
                    int readInt130 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSystemKeyEventRequested = isSystemKeyEventRequested(readInt130, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemKeyEventRequested);
                    return true;
                case 187:
                    int readInt131 = parcel.readInt();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemKeyEvent(readInt131, componentName3, readInt132);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    int readInt133 = parcel.readInt();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterSystemKeyEvent(readInt133, componentName4);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestMetaKeyEvent(componentName5, readBoolean32);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMetaKeyEventRequested = isMetaKeyEventRequested(componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMetaKeyEventRequested);
                    return true;
                case 191:
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = (SemWindowManager.KeyCustomizationInfo) parcel.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    putKeyCustomizationInfo(keyCustomizationInfo);
                    parcel2.writeNoException();
                    return true;
                case 192:
                    int readInt134 = parcel.readInt();
                    int readInt135 = parcel.readInt();
                    int readInt136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 = getKeyCustomizationInfo(readInt134, readInt135, readInt136);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCustomizationInfo2, 1);
                    return true;
                case 193:
                    String readString15 = parcel.readString();
                    int readInt137 = parcel.readInt();
                    int readInt138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfoByPackage = getKeyCustomizationInfoByPackage(readString15, readInt137, readInt138);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCustomizationInfoByPackage, 1);
                    return true;
                case 194:
                    int readInt139 = parcel.readInt();
                    int readInt140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(readInt139, readInt140);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastKeyCustomizationInfo, 1);
                    return true;
                case 195:
                    int readInt141 = parcel.readInt();
                    int readInt142 = parcel.readInt();
                    int readInt143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeKeyCustomizationInfo(readInt141, readInt142, readInt143);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    String readString16 = parcel.readString();
                    int readInt144 = parcel.readInt();
                    int readInt145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeKeyCustomizationInfoByPackage(readString16, readInt144, readInt145);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    int readInt146 = parcel.readInt();
                    int readInt147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearKeyCustomizationInfoByKeyCode(readInt146, readInt147);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    int readInt148 = parcel.readInt();
                    int readInt149 = parcel.readInt();
                    int readInt150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearKeyCustomizationInfoByAction(readInt148, readInt149, readInt150);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    List<SemWindowManager.KeyCustomizationInfo> backupKeyCustomizationInfoList = getBackupKeyCustomizationInfoList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(backupKeyCustomizationInfoList, 1);
                    return true;
                case 200:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(SemWindowManager.KeyCustomizationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    restoreKeyCustomizationInfo(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    IAssistDataReceiver asInterface52 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean omniRequestAssistScreenshot = omniRequestAssistScreenshot(asInterface52, readBoolean33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(omniRequestAssistScreenshot);
                    return true;
                case 202:
                    int readInt151 = parcel.readInt();
                    int readInt152 = parcel.readInt();
                    InputEvent[] inputEventArr = (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR);
                    IBinder readStrongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    dispatchSPenGestureEvent(readInt151, readInt152, inputEventArr, readStrongBinder25);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPendingIntentAfterUnlock(pendingIntent, intent);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    boolean isKeyguardShowingAndNotOccluded = isKeyguardShowingAndNotOccluded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyguardShowingAndNotOccluded);
                    return true;
                case 205:
                    startLockscreenFingerprintAuth();
                    parcel2.writeNoException();
                    return true;
                case 206:
                    int topFocusedDisplayId = getTopFocusedDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(topFocusedDisplayId);
                    return true;
                case 207:
                    int readInt153 = parcel.readInt();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveDisplayToTop(readInt153, readString17);
                    return true;
                case 208:
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDragSurfaceToOverlay(readBoolean34);
                    parcel2.writeNoException();
                    return true;
                case 209:
                    IBinder readStrongBinder26 = parcel.readStrongBinder();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startSurfaceAnimation(readStrongBinder26, readString18);
                    return true;
                case 210:
                    boolean isFolded = isFolded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFolded);
                    return true;
                case 211:
                    boolean isTableMode = isTableMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTableMode);
                    return true;
                case 212:
                    boolean readBoolean35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTableModeEnabled(readBoolean35);
                    parcel2.writeNoException();
                    return true;
                case 213:
                    int readInt154 = parcel.readInt();
                    String readString19 = parcel.readString();
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int appContinuityMode = getAppContinuityMode(readInt154, readString19, activityInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(appContinuityMode);
                    return true;
                case 214:
                    int readInt155 = parcel.readInt();
                    String readString20 = parcel.readString();
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppContinuityMode(readInt155, readString20, readBoolean36);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    IAuthTouchEventListener asInterface53 = IAuthTouchEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthTouchEventListener(asInterface53);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    IAuthTouchEventListener asInterface54 = IAuthTouchEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthTouchEventListener(asInterface54);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    int readInt156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayColorToSystemProperties(readInt156);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEdgeToEdgeDisabled = isEdgeToEdgeDisabled(readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEdgeToEdgeDisabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWindowManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowManager
            public boolean startViewServer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean stopViewServer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isViewServerRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowSessionCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWindowSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getInitialDisplaySize(int i, Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        point.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getBaseDisplaySize(int i, Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        point.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySize(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getInitialDisplayDensity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getBaseDisplayDensity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayIdByUniqueId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityRatio(int i, float f, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setConfigurationChangeSettingsForUser(List<ConfigurationChangeSetting> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayScalingMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setEventDispatching(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowToken(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeWindowToken(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayChangeWindowController);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SurfaceControl) obtain2.readTypedObject(SurfaceControl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppTransitionAnimationSpecsFuture);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void endProlongedAnimations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void reenableKeyguard(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnKeyguardExitResult);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardSecure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSwitchingUser(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getAnimationScale(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float[] getAnimationScales() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScale(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScales(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getCurrentAnimatorScale() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchMode(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchModeOnAllDisplays(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isInTouchMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showStrictModeViolation(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setStrictModeVisualIndicatorPreference(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void refreshScreenCaptureDisabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDefaultDisplayRotation() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayUserRotation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationWatcher);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getPreferredOptionsPanelGravity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeRotation(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawRotation(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isRotationFrozen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeDisplayRotation(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawDisplayRotation(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isDisplayRotationFrozen(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setFixedToUserRotation(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setIgnoreOrientationRequest(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap screenshotWallpaper() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl mirrorWallpaperSurface(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SurfaceControl) obtain2.readTypedObject(SurfaceControl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperVisibilityListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperVisibilityListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemGestureExclusionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemGestureExclusionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void hideTransientBars(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsVisibility(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(rectArr, 0);
                    this.mRemote.transact(72, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasNavigationBar(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void lockNow(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSafeModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowContentFrameStats) obtain2.readTypedObject(WindowContentFrameStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDockedStackSide() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPinnedTaskListener);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getStableInsets(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getOverrideStableInsets(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iShortcutService);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean destroyInputConsumer(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Region getCurrentImeTouchRegion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Region) obtain2.readTypedObject(Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startWindowTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopWindowTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void saveWindowTraceToFile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowTraceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startTransitionTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopTransitionTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTransitionTraceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getWindowingMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setWindowingMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRemoveContentMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRemoveContentMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowWithInsecureKeyguard(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowWithInsecureKeyguard(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowSystemDecors(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowSystemDecors(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isEligibleForDesktopMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayImePolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayImePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void onNotificationShadeExpanded(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void syncInputTransactions(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLayerTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracing(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean mirrorDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        surfaceControl.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDisplayWindowInsetsController);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowRequestedVisibleTypes(int i, int i2, int i3, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowAnimatingTypes(int i, int i2, ImeTracker.Token token) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<DisplayInfo> getPossibleDisplayInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DisplayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showGlobalActions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracingFlags(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setActiveTransactionTracing(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iScrollCaptureResponseListener);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(smartClipRemoteRequestInfo, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public String[] getSupportedDisplayHashAlgorithms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(displayHash, 0);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifiedDisplayHash) obtain2.readTypedObject(VerifiedDisplayHash.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayHashThrottlingEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowContextInfo) obtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToWindowToken(IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowContextInfo) obtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayContent(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowContextInfo) obtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void detachWindowContext(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean reparentWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTaskSnapshotSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getImeDisplayId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTaskSnapshotEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap snapshotTaskForRecents(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsAppBehindSystemBars(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWindowManager.VisibleWindowInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getLetterboxBackgroundColorInArgb() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLetterboxBackgroundMultiColored() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureArgs, 0);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(147, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isGlobalKey(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSurfaceSyncGroupCompletedListener);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        addToSurfaceSyncGroupResult.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void markSurfaceSyncGroupReady(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(150, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<ComponentName> notifyScreenshotListeners(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDecorViewGestureListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDecorViewGestureListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTrustedPresentationListener(IBinder iBinder, ITrustedPresentationListener iTrustedPresentationListener, TrustedPresentationThresholds trustedPresentationThresholds, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTrustedPresentationListener);
                    obtain.writeTypedObject(trustedPresentationThresholds, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTrustedPresentationListener(ITrustedPresentationListener iTrustedPresentationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustedPresentationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iGlobalDragListener);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeTypedObject(inputTransferToken2, 0);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyboardShortcutGroup) obtain2.readTypedObject(KeyboardShortcutGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getIgnoreOrientationRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDeadzoneHole(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getMaxAspectRatioPolicy(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ScreenshotResult) obtain2.readTypedObject(ScreenshotResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ScreenshotResult) obtain2.readTypedObject(ScreenshotResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getUserDisplaySize(Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        point.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getUserDisplayDensity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySizeDensity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i5);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(multiResolutionChangeRequestInfo, 0);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getSupportsFlexPanel(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSupportsFlexPanel(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getFullScreenAppsSupportMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iInputFilter);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOneHandOpWatcher);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOneHandOpWatcher);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAnimationRunner);
                    obtain.writeInt(i);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAnimationRunner);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRotationLockOrientation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasTaskbarTarget() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSystemKeyEventRequested(int i, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemKeyEvent(int i, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestMetaKeyEvent(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isMetaKeyEventRequested(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyCustomizationInfo, 0);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) obtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) obtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) obtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByKeyCode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void restoreKeyCustomizationInfo(List<SemWindowManager.KeyCustomizationInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(inputEventArr, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardShowingAndNotOccluded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startLockscreenFingerprintAuth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getTopFocusedDisplayId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void moveDisplayToTop(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(207, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDragSurfaceToOverlay(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startSurfaceAnimation(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(209, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isFolded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTableMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTableModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(activityInfo, 0);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAppContinuityMode(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAuthTouchEventListener);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAuthTouchEventListener);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayColorToSystemProperties(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isEdgeToEdgeDisabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setForcedDisplaySize_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplaySize_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayDensityForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplayDensityForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayDensityRatio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setConfigurationChangeSettingsForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayScalingMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void addShellRoot_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void setShellRootAccessibilityWindow_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void exitKeyguardSecurely_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DISABLE_KEYGUARD, getCallingPid(), getCallingUid());
        }

        protected void setNavBarVirtualKeyHapticFeedbackEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR, getCallingPid(), getCallingUid());
        }

        protected void getCurrentImeTouchRegion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RESTRICTED_VR_ACCESS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayWindowInsetsController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void updateDisplayWindowRequestedVisibleTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void updateDisplayWindowAnimatingTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void registerScreenRecordingCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void unregisterScreenRecordingCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void registerKnoxRemoteScreenCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void unregisterKnoxRemoteScreenCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }
    }
}
