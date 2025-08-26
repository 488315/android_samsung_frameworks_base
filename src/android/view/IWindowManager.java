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
        public void clearDesktopWindowSettings() throws RemoteException {
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

    void clearDesktopWindowSettings() throws RemoteException;

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
        static final int TRANSACTION_changeDisplayScale = 179;
        static final int TRANSACTION_clearDesktopWindowSettings = 165;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 13;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearForcedDisplaySizeDensity = 173;
        static final int TRANSACTION_clearKeyCustomizationInfoByAction = 199;
        static final int TRANSACTION_clearKeyCustomizationInfoByKeyCode = 198;
        static final int TRANSACTION_clearWindowContentFrameStats = 77;
        static final int TRANSACTION_closeSystemDialogs = 36;
        static final int TRANSACTION_closeSystemDialogsInDisplay = 37;
        static final int TRANSACTION_createInputConsumer = 86;
        static final int TRANSACTION_destroyInputConsumer = 87;
        static final int TRANSACTION_detachWindowContext = 133;
        static final int TRANSACTION_disableKeyguard = 27;
        static final int TRANSACTION_dismissKeyguard = 32;
        static final int TRANSACTION_dispatchSPenGestureEvent = 203;
        static final int TRANSACTION_dispatchSmartClipRemoteRequest = 125;
        static final int TRANSACTION_endProlongedAnimations = 26;
        static final int TRANSACTION_exitKeyguardSecurely = 29;
        static final int TRANSACTION_finishRemoteWallpaperAnimation = 183;
        static final int TRANSACTION_freezeDisplayRotation = 58;
        static final int TRANSACTION_freezeRotation = 55;
        static final int TRANSACTION_getAnimationScale = 38;
        static final int TRANSACTION_getAnimationScales = 39;
        static final int TRANSACTION_getAppContinuityMode = 214;
        static final int TRANSACTION_getApplicationLaunchKeyboardShortcuts = 163;
        static final int TRANSACTION_getBackupKeyCustomizationInfoList = 200;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 42;
        static final int TRANSACTION_getCurrentImeTouchRegion = 88;
        static final int TRANSACTION_getDefaultDisplayRotation = 49;
        static final int TRANSACTION_getDisplayIdByUniqueId = 11;
        static final int TRANSACTION_getDisplayImePolicy = 109;
        static final int TRANSACTION_getDisplayUserRotation = 50;
        static final int TRANSACTION_getDockedStackSide = 79;
        static final int TRANSACTION_getFullScreenAppsSupportMode = 178;
        static final int TRANSACTION_getIgnoreOrientationRequest = 164;
        static final int TRANSACTION_getImeDisplayId = 138;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getKeyCustomizationInfo = 193;
        static final int TRANSACTION_getKeyCustomizationInfoByPackage = 194;
        static final int TRANSACTION_getLastKeyCustomizationInfo = 195;
        static final int TRANSACTION_getLetterboxBackgroundColorInArgb = 145;
        static final int TRANSACTION_getMaxAspectRatioPolicy = 167;
        static final int TRANSACTION_getOverrideStableInsets = 84;
        static final int TRANSACTION_getPossibleDisplayInfo = 120;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 54;
        static final int TRANSACTION_getRemoveContentMode = 102;
        static final int TRANSACTION_getRotationLockOrientation = 184;
        static final int TRANSACTION_getStableInsets = 83;
        static final int TRANSACTION_getSupportedDisplayHashAlgorithms = 127;
        static final int TRANSACTION_getSupportsFlexPanel = 176;
        static final int TRANSACTION_getTopFocusedDisplayId = 207;
        static final int TRANSACTION_getUserDisplayDensity = 172;
        static final int TRANSACTION_getUserDisplaySize = 171;
        static final int TRANSACTION_getVisibleWindowInfoList = 144;
        static final int TRANSACTION_getWindowContentFrameStats = 78;
        static final int TRANSACTION_getWindowInsets = 119;
        static final int TRANSACTION_getWindowingMode = 100;
        static final int TRANSACTION_hasNavigationBar = 74;
        static final int TRANSACTION_hasTaskbarTarget = 185;
        static final int TRANSACTION_hideTransientBars = 70;
        static final int TRANSACTION_holdLock = 126;
        static final int TRANSACTION_isDisplayRotationFrozen = 60;
        static final int TRANSACTION_isEdgeToEdgeDisabled = 219;
        static final int TRANSACTION_isEligibleForDesktopMode = 108;
        static final int TRANSACTION_isFolded = 211;
        static final int TRANSACTION_isGlobalKey = 148;
        static final int TRANSACTION_isInTouchMode = 45;
        static final int TRANSACTION_isKeyguardLocked = 30;
        static final int TRANSACTION_isKeyguardSecure = 31;
        static final int TRANSACTION_isKeyguardShowingAndNotOccluded = 205;
        static final int TRANSACTION_isLayerTracing = 113;
        static final int TRANSACTION_isLetterboxBackgroundMultiColored = 146;
        static final int TRANSACTION_isMetaKeyEventRequested = 191;
        static final int TRANSACTION_isRotationFrozen = 57;
        static final int TRANSACTION_isSafeModeEnabled = 76;
        static final int TRANSACTION_isSystemKeyEventRequested = 187;
        static final int TRANSACTION_isTableMode = 212;
        static final int TRANSACTION_isTaskSnapshotSupported = 137;
        static final int TRANSACTION_isTransitionTraceEnabled = 99;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowToken = 18;
        static final int TRANSACTION_isWindowTraceEnabled = 96;
        static final int TRANSACTION_lockNow = 75;
        static final int TRANSACTION_markSurfaceSyncGroupReady = 150;
        static final int TRANSACTION_mirrorDisplay = 115;
        static final int TRANSACTION_mirrorWallpaperSurface = 64;
        static final int TRANSACTION_moveDisplayToTop = 208;
        static final int TRANSACTION_notifyScreenshotListeners = 151;
        static final int TRANSACTION_omniRequestAssistScreenshot = 202;
        static final int TRANSACTION_onNotificationShadeExpanded = 111;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 24;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 25;
        static final int TRANSACTION_putKeyCustomizationInfo = 192;
        static final int TRANSACTION_reenableKeyguard = 28;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 48;
        static final int TRANSACTION_registerAuthTouchEventListener = 216;
        static final int TRANSACTION_registerCrossWindowBlurEnabledListener = 135;
        static final int TRANSACTION_registerDecorViewGestureListener = 153;
        static final int TRANSACTION_registerDisplayFoldListener = 89;
        static final int TRANSACTION_registerDisplayWindowListener = 91;
        static final int TRANSACTION_registerKnoxRemoteScreenCallback = 159;
        static final int TRANSACTION_registerOneHandOpWatcher = 180;
        static final int TRANSACTION_registerPinnedTaskListener = 80;
        static final int TRANSACTION_registerProposedRotationListener = 53;
        static final int TRANSACTION_registerScreenRecordingCallback = 157;
        static final int TRANSACTION_registerShortcutKey = 85;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 67;
        static final int TRANSACTION_registerSystemKeyEvent = 188;
        static final int TRANSACTION_registerTaskFpsCallback = 140;
        static final int TRANSACTION_registerTrustedPresentationListener = 155;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 65;
        static final int TRANSACTION_removeKeyCustomizationInfo = 196;
        static final int TRANSACTION_removeKeyCustomizationInfoByPackage = 197;
        static final int TRANSACTION_removeKeyguardLockedStateListener = 34;
        static final int TRANSACTION_removeRotationWatcher = 52;
        static final int TRANSACTION_removeWindowToken = 20;
        static final int TRANSACTION_reparentWindowContextToDisplayArea = 134;
        static final int TRANSACTION_replaceContentOnDisplay = 152;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 81;
        static final int TRANSACTION_requestAssistScreenshot = 69;
        static final int TRANSACTION_requestImeKeyboardShortcuts = 82;
        static final int TRANSACTION_requestMetaKeyEvent = 190;
        static final int TRANSACTION_requestScrollCapture = 124;
        static final int TRANSACTION_requestSystemKeyEvent = 186;
        static final int TRANSACTION_restoreKeyCustomizationInfo = 201;
        static final int TRANSACTION_saveWindowTraceToFile = 95;
        static final int TRANSACTION_screenshotWallpaper = 63;
        static final int TRANSACTION_setActiveTransactionTracing = 123;
        static final int TRANSACTION_setAnimationScale = 40;
        static final int TRANSACTION_setAnimationScales = 41;
        static final int TRANSACTION_setAppContinuityMode = 215;
        static final int TRANSACTION_setConfigurationChangeSettingsForUser = 15;
        static final int TRANSACTION_setDeadzoneHole = 166;
        static final int TRANSACTION_setDisplayChangeWindowController = 21;
        static final int TRANSACTION_setDisplayColorToSystemProperties = 218;
        static final int TRANSACTION_setDisplayHashThrottlingEnabled = 129;
        static final int TRANSACTION_setDisplayImePolicy = 110;
        static final int TRANSACTION_setDisplayWindowInsetsController = 116;
        static final int TRANSACTION_setDragSurfaceToOverlay = 209;
        static final int TRANSACTION_setEventDispatching = 17;
        static final int TRANSACTION_setFixedToUserRotation = 61;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_setForcedDisplayDensityRatio = 14;
        static final int TRANSACTION_setForcedDisplayScalingMode = 16;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setForcedDisplaySizeDensity = 174;
        static final int TRANSACTION_setForcedDisplaySizeDensityWithInfo = 175;
        static final int TRANSACTION_setGlobalDragListener = 161;
        static final int TRANSACTION_setIgnoreOrientationRequest = 62;
        static final int TRANSACTION_setInTouchMode = 43;
        static final int TRANSACTION_setInTouchModeOnAllDisplays = 44;
        static final int TRANSACTION_setLayerTracing = 114;
        static final int TRANSACTION_setLayerTracingFlags = 122;
        static final int TRANSACTION_setMaxAspectRatioPolicy = 168;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 73;
        static final int TRANSACTION_setPendingIntentAfterUnlock = 204;
        static final int TRANSACTION_setRecentsAppBehindSystemBars = 143;
        static final int TRANSACTION_setRecentsVisibility = 71;
        static final int TRANSACTION_setRemoveContentMode = 103;
        static final int TRANSACTION_setShellRootAccessibilityWindow = 23;
        static final int TRANSACTION_setShouldShowSystemDecors = 107;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 105;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 47;
        static final int TRANSACTION_setSupportsFlexPanel = 177;
        static final int TRANSACTION_setSwitchingUser = 35;
        static final int TRANSACTION_setTableModeEnabled = 213;
        static final int TRANSACTION_setTaskSnapshotEnabled = 139;
        static final int TRANSACTION_setWindowingMode = 101;
        static final int TRANSACTION_shouldShowSystemDecors = 106;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 104;
        static final int TRANSACTION_showGlobalActions = 121;
        static final int TRANSACTION_showStrictModeViolation = 46;
        static final int TRANSACTION_snapshotTaskForRecents = 142;
        static final int TRANSACTION_startLockscreenFingerprintAuth = 206;
        static final int TRANSACTION_startRemoteWallpaperAnimation = 182;
        static final int TRANSACTION_startSurfaceAnimation = 210;
        static final int TRANSACTION_startTransitionTrace = 97;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 93;
        static final int TRANSACTION_stopTransitionTrace = 98;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 94;
        static final int TRANSACTION_syncInputTransactions = 112;
        static final int TRANSACTION_takeScreenshotToTargetWindow = 169;
        static final int TRANSACTION_takeScreenshotToTargetWindowFromCapture = 170;
        static final int TRANSACTION_thawDisplayRotation = 59;
        static final int TRANSACTION_thawRotation = 56;
        static final int TRANSACTION_transferTouchGesture = 162;
        static final int TRANSACTION_unregisterAuthTouchEventListener = 217;
        static final int TRANSACTION_unregisterCrossWindowBlurEnabledListener = 136;
        static final int TRANSACTION_unregisterDecorViewGestureListener = 154;
        static final int TRANSACTION_unregisterDisplayFoldListener = 90;
        static final int TRANSACTION_unregisterDisplayWindowListener = 92;
        static final int TRANSACTION_unregisterKnoxRemoteScreenCallback = 160;
        static final int TRANSACTION_unregisterOneHandOpWatcher = 181;
        static final int TRANSACTION_unregisterScreenRecordingCallback = 158;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 68;
        static final int TRANSACTION_unregisterSystemKeyEvent = 189;
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
            return 218;
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindowManager)) {
                return (IWindowManager) iInterfaceQueryLocalInterface;
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
                    return "clearDesktopWindowSettings";
                case 166:
                    return "setDeadzoneHole";
                case 167:
                    return "getMaxAspectRatioPolicy";
                case 168:
                    return "setMaxAspectRatioPolicy";
                case 169:
                    return "takeScreenshotToTargetWindow";
                case 170:
                    return "takeScreenshotToTargetWindowFromCapture";
                case 171:
                    return "getUserDisplaySize";
                case 172:
                    return "getUserDisplayDensity";
                case 173:
                    return "clearForcedDisplaySizeDensity";
                case 174:
                    return "setForcedDisplaySizeDensity";
                case 175:
                    return "setForcedDisplaySizeDensityWithInfo";
                case 176:
                    return "getSupportsFlexPanel";
                case 177:
                    return "setSupportsFlexPanel";
                case 178:
                    return "getFullScreenAppsSupportMode";
                case 179:
                    return "changeDisplayScale";
                case 180:
                    return "registerOneHandOpWatcher";
                case 181:
                    return "unregisterOneHandOpWatcher";
                case 182:
                    return "startRemoteWallpaperAnimation";
                case 183:
                    return "finishRemoteWallpaperAnimation";
                case 184:
                    return "getRotationLockOrientation";
                case 185:
                    return "hasTaskbarTarget";
                case 186:
                    return "requestSystemKeyEvent";
                case 187:
                    return "isSystemKeyEventRequested";
                case 188:
                    return "registerSystemKeyEvent";
                case 189:
                    return "unregisterSystemKeyEvent";
                case 190:
                    return "requestMetaKeyEvent";
                case 191:
                    return "isMetaKeyEventRequested";
                case 192:
                    return "putKeyCustomizationInfo";
                case 193:
                    return "getKeyCustomizationInfo";
                case 194:
                    return "getKeyCustomizationInfoByPackage";
                case 195:
                    return "getLastKeyCustomizationInfo";
                case 196:
                    return "removeKeyCustomizationInfo";
                case 197:
                    return "removeKeyCustomizationInfoByPackage";
                case 198:
                    return "clearKeyCustomizationInfoByKeyCode";
                case 199:
                    return "clearKeyCustomizationInfoByAction";
                case 200:
                    return "getBackupKeyCustomizationInfoList";
                case 201:
                    return "restoreKeyCustomizationInfo";
                case 202:
                    return "omniRequestAssistScreenshot";
                case 203:
                    return "dispatchSPenGestureEvent";
                case 204:
                    return "setPendingIntentAfterUnlock";
                case 205:
                    return "isKeyguardShowingAndNotOccluded";
                case 206:
                    return "startLockscreenFingerprintAuth";
                case 207:
                    return "getTopFocusedDisplayId";
                case 208:
                    return "moveDisplayToTop";
                case 209:
                    return "setDragSurfaceToOverlay";
                case 210:
                    return "startSurfaceAnimation";
                case 211:
                    return "isFolded";
                case 212:
                    return "isTableMode";
                case 213:
                    return "setTableModeEnabled";
                case 214:
                    return "getAppContinuityMode";
                case 215:
                    return "setAppContinuityMode";
                case 216:
                    return "registerAuthTouchEventListener";
                case 217:
                    return "unregisterAuthTouchEventListener";
                case 218:
                    return "setDisplayColorToSystemProperties";
                case 219:
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStartViewServer = startViewServer(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartViewServer);
                    return true;
                case 2:
                    boolean zStopViewServer = stopViewServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopViewServer);
                    return true;
                case 3:
                    boolean zIsViewServerRunning = isViewServerRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsViewServerRunning);
                    return true;
                case 4:
                    IWindowSessionCallback iWindowSessionCallbackAsInterface = IWindowSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IWindowSession iWindowSessionOpenSession = openSession(iWindowSessionCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iWindowSessionOpenSession);
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    Point point = new Point();
                    parcel.enforceNoDataAvail();
                    getInitialDisplaySize(i4, point);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point, 1);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    Point point2 = new Point();
                    parcel.enforceNoDataAvail();
                    getBaseDisplaySize(i5, point2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point2, 1);
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySize(i6, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplaySize(i9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialDisplayDensity = getInitialDisplayDensity(i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialDisplayDensity);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int baseDisplayDensity = getBaseDisplayDensity(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(baseDisplayDensity);
                    return true;
                case 11:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int displayIdByUniqueId = getDisplayIdByUniqueId(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayIdByUniqueId);
                    return true;
                case 12:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayDensityForUser(i12, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplayDensityForUser(i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i17 = parcel.readInt();
                    float f = parcel.readFloat();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayDensityRatio(i17, f, i18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ConfigurationChangeSetting.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setConfigurationChangeSettingsForUser(arrayListCreateTypedArrayList, i19);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayScalingMode(i20, i21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEventDispatching(z);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsWindowToken = isWindowToken(strongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWindowToken);
                    return true;
                case 19:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addWindowToken(strongBinder2, i22, i23, bundle);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWindowToken(strongBinder3, i24);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IDisplayChangeWindowController iDisplayChangeWindowControllerAsInterface = IDisplayChangeWindowController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayChangeWindowController(iDisplayChangeWindowControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i25 = parcel.readInt();
                    IWindow iWindowAsInterface = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceControl surfaceControlAddShellRoot = addShellRoot(i25, iWindowAsInterface, i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceControlAddShellRoot, 1);
                    return true;
                case 23:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    IWindow iWindowAsInterface2 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setShellRootAccessibilityWindow(i27, i28, iWindowAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFutureAsInterface = IAppTransitionAnimationSpecsFuture.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z2 = parcel.readBoolean();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFutureAsInterface, iRemoteCallbackAsInterface, z2, i29);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionRemote(remoteAnimationAdapter, i30);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    endProlongedAnimations();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string2 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableKeyguard(strongBinder4, string2, i31);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reenableKeyguard(strongBinder5, i32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IOnKeyguardExitResult iOnKeyguardExitResultAsInterface = IOnKeyguardExitResult.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    exitKeyguardSecurely(iOnKeyguardExitResultAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean zIsKeyguardLocked = isKeyguardLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyguardLocked);
                    return true;
                case 31:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKeyguardSecure = isKeyguardSecure(i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyguardSecure);
                    return true;
                case 32:
                    IKeyguardDismissCallback iKeyguardDismissCallbackAsInterface = IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(iKeyguardDismissCallbackAsInterface, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IKeyguardLockedStateListener iKeyguardLockedStateListenerAsInterface = IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addKeyguardLockedStateListener(iKeyguardLockedStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IKeyguardLockedStateListener iKeyguardLockedStateListenerAsInterface2 = IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeKeyguardLockedStateListener(iKeyguardLockedStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSwitchingUser(z3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(string3);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String string4 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(string4, i34);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float animationScale = getAnimationScale(i35);
                    parcel2.writeNoException();
                    parcel2.writeFloat(animationScale);
                    return true;
                case 39:
                    float[] animationScales = getAnimationScales();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(animationScales);
                    return true;
                case 40:
                    int i36 = parcel.readInt();
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(i36, f2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setAnimationScales(fArrCreateFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    float currentAnimatorScale = getCurrentAnimatorScale();
                    parcel2.writeNoException();
                    parcel2.writeFloat(currentAnimatorScale);
                    return true;
                case 43:
                    boolean z4 = parcel.readBoolean();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInTouchMode(z4, i37);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInTouchModeOnAllDisplays(z5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInTouchMode = isInTouchMode(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInTouchMode);
                    return true;
                case 46:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showStrictModeViolation(z6);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStrictModeVisualIndicatorPreference(string5);
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
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayUserRotation = getDisplayUserRotation(i39);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayUserRotation);
                    return true;
                case 51:
                    IRotationWatcher iRotationWatcherAsInterface = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iWatchRotation = watchRotation(iRotationWatcherAsInterface, i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(iWatchRotation);
                    return true;
                case 52:
                    IRotationWatcher iRotationWatcherAsInterface2 = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRotationWatcher(iRotationWatcherAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    IRotationWatcher iRotationWatcherAsInterface3 = IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterProposedRotationListener = registerProposedRotationListener(strongBinder6, iRotationWatcherAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterProposedRotationListener);
                    return true;
                case 54:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int preferredOptionsPanelGravity = getPreferredOptionsPanelGravity(i41);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredOptionsPanelGravity);
                    return true;
                case 55:
                    int i42 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeRotation(i42, string6);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawRotation(string7);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    boolean zIsRotationFrozen = isRotationFrozen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRotationFrozen);
                    return true;
                case 58:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeDisplayRotation(i43, i44, string8);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int i45 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawDisplayRotation(i45, string9);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDisplayRotationFrozen = isDisplayRotationFrozen(i46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDisplayRotationFrozen);
                    return true;
                case 61:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFixedToUserRotation(i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i49 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreOrientationRequest(i49, z7);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    Bitmap bitmapScreenshotWallpaper = screenshotWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapScreenshotWallpaper, 1);
                    return true;
                case 64:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceControl surfaceControlMirrorWallpaperSurface = mirrorWallpaperSurface(i50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceControlMirrorWallpaperSurface, 1);
                    return true;
                case 65:
                    IWallpaperVisibilityListener iWallpaperVisibilityListenerAsInterface = IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterWallpaperVisibilityListener = registerWallpaperVisibilityListener(iWallpaperVisibilityListenerAsInterface, i51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterWallpaperVisibilityListener);
                    return true;
                case 66:
                    IWallpaperVisibilityListener iWallpaperVisibilityListenerAsInterface2 = IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperVisibilityListener(iWallpaperVisibilityListenerAsInterface2, i52);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    ISystemGestureExclusionListener iSystemGestureExclusionListenerAsInterface = ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemGestureExclusionListener(iSystemGestureExclusionListenerAsInterface, i53);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    ISystemGestureExclusionListener iSystemGestureExclusionListenerAsInterface2 = ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemGestureExclusionListener(iSystemGestureExclusionListenerAsInterface2, i54);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    IAssistDataReceiver iAssistDataReceiverAsInterface = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRequestAssistScreenshot = requestAssistScreenshot(iAssistDataReceiverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAssistScreenshot);
                    return true;
                case 70:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideTransientBars(i55);
                    return true;
                case 71:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsVisibility(z8);
                    return true;
                case 72:
                    int i56 = parcel.readInt();
                    Rect[] rectArr = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateStaticPrivacyIndicatorBounds(i56, rectArr);
                    return true;
                case 73:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavBarVirtualKeyHapticFeedbackEnabled(z9);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasNavigationBar = hasNavigationBar(i57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasNavigationBar);
                    return true;
                case 75:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    lockNow(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean zIsSafeModeEnabled = isSafeModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSafeModeEnabled);
                    return true;
                case 77:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zClearWindowContentFrameStats = clearWindowContentFrameStats(strongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearWindowContentFrameStats);
                    return true;
                case 78:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    WindowContentFrameStats windowContentFrameStats = getWindowContentFrameStats(strongBinder8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContentFrameStats, 1);
                    return true;
                case 79:
                    int dockedStackSide = getDockedStackSide();
                    parcel2.writeNoException();
                    parcel2.writeInt(dockedStackSide);
                    return true;
                case 80:
                    int i58 = parcel.readInt();
                    IPinnedTaskListener iPinnedTaskListenerAsInterface = IPinnedTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPinnedTaskListener(i58, iPinnedTaskListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(iResultReceiverAsInterface, i59);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    IResultReceiver iResultReceiverAsInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestImeKeyboardShortcuts(iResultReceiverAsInterface2, i60);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int i61 = parcel.readInt();
                    Rect rect = new Rect();
                    parcel.enforceNoDataAvail();
                    getStableInsets(i61, rect);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect, 1);
                    return true;
                case 84:
                    int i62 = parcel.readInt();
                    Rect rect2 = new Rect();
                    parcel.enforceNoDataAvail();
                    getOverrideStableInsets(i62, rect2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect2, 1);
                    return true;
                case 85:
                    long j = parcel.readLong();
                    IShortcutService iShortcutServiceAsInterface = IShortcutService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerShortcutKey(j, iShortcutServiceAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    String string10 = parcel.readString();
                    int i63 = parcel.readInt();
                    InputChannel inputChannel = new InputChannel();
                    parcel.enforceNoDataAvail();
                    createInputConsumer(strongBinder9, string10, i63, inputChannel);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel, 1);
                    return true;
                case 87:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDestroyInputConsumer = destroyInputConsumer(strongBinder10, i64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDestroyInputConsumer);
                    return true;
                case 88:
                    Region currentImeTouchRegion = getCurrentImeTouchRegion();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentImeTouchRegion, 1);
                    return true;
                case 89:
                    IDisplayFoldListener iDisplayFoldListenerAsInterface = IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDisplayFoldListener(iDisplayFoldListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    IDisplayFoldListener iDisplayFoldListenerAsInterface2 = IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayFoldListener(iDisplayFoldListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    IDisplayWindowListener iDisplayWindowListenerAsInterface = IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int[] iArrRegisterDisplayWindowListener = registerDisplayWindowListener(iDisplayWindowListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrRegisterDisplayWindowListener);
                    return true;
                case 92:
                    IDisplayWindowListener iDisplayWindowListenerAsInterface2 = IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayWindowListener(iDisplayWindowListenerAsInterface2);
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
                    boolean zIsWindowTraceEnabled = isWindowTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWindowTraceEnabled);
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
                    boolean zIsTransitionTraceEnabled = isTransitionTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTransitionTraceEnabled);
                    return true;
                case 100:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int windowingMode = getWindowingMode(i65);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowingMode);
                    return true;
                case 101:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowingMode(i66, i67);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeContentMode = getRemoveContentMode(i68);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContentMode);
                    return true;
                case 103:
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRemoveContentMode(i69, i70);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldShowWithInsecureKeyguard = shouldShowWithInsecureKeyguard(i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldShowWithInsecureKeyguard);
                    return true;
                case 105:
                    int i72 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowWithInsecureKeyguard(i72, z10);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldShowSystemDecors = shouldShowSystemDecors(i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldShowSystemDecors);
                    return true;
                case 107:
                    int i74 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowSystemDecors(i74, z11);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsEligibleForDesktopMode = isEligibleForDesktopMode(i75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEligibleForDesktopMode);
                    return true;
                case 109:
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayImePolicy = getDisplayImePolicy(i76);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayImePolicy);
                    return true;
                case 110:
                    int i77 = parcel.readInt();
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(i77, i78);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationShadeExpanded(strongBinder11, z12);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    syncInputTransactions(z13);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    boolean zIsLayerTracing = isLayerTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLayerTracing);
                    return true;
                case 114:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLayerTracing(z14);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    int i79 = parcel.readInt();
                    SurfaceControl surfaceControl = new SurfaceControl();
                    parcel.enforceNoDataAvail();
                    boolean zMirrorDisplay = mirrorDisplay(i79, surfaceControl);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMirrorDisplay);
                    parcel2.writeTypedObject(surfaceControl, 1);
                    return true;
                case 116:
                    int i80 = parcel.readInt();
                    IDisplayWindowInsetsController iDisplayWindowInsetsControllerAsInterface = IDisplayWindowInsetsController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayWindowInsetsController(i80, iDisplayWindowInsetsControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    int i83 = parcel.readInt();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDisplayWindowRequestedVisibleTypes(i81, i82, i83, token);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    int i84 = parcel.readInt();
                    int i85 = parcel.readInt();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDisplayWindowAnimatingTypes(i84, i85, token2);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    int i86 = parcel.readInt();
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    InsetsState insetsState = new InsetsState();
                    parcel.enforceNoDataAvail();
                    boolean windowInsets = getWindowInsets(i86, strongBinder12, insetsState);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(windowInsets);
                    parcel2.writeTypedObject(insetsState, 1);
                    return true;
                case 120:
                    int i87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DisplayInfo> possibleDisplayInfo = getPossibleDisplayInfo(i87);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(possibleDisplayInfo, 1);
                    return true;
                case 121:
                    showGlobalActions();
                    parcel2.writeNoException();
                    return true;
                case 122:
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLayerTracingFlags(i88);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActiveTransactionTracing(z15);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    int i89 = parcel.readInt();
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i90 = parcel.readInt();
                    IScrollCaptureResponseListener iScrollCaptureResponseListenerAsInterface = IScrollCaptureResponseListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestScrollCapture(i89, strongBinder13, i90, iScrollCaptureResponseListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    int i91 = parcel.readInt();
                    int i92 = parcel.readInt();
                    SmartClipRemoteRequestInfo smartClipRemoteRequestInfo = (SmartClipRemoteRequestInfo) parcel.readTypedObject(SmartClipRemoteRequestInfo.CREATOR);
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    dispatchSmartClipRemoteRequest(i91, i92, smartClipRemoteRequestInfo, strongBinder14);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(strongBinder15, i93);
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
                    VerifiedDisplayHash verifiedDisplayHashVerifyDisplayHash = verifyDisplayHash(displayHash);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifiedDisplayHashVerifyDisplayHash, 1);
                    return true;
                case 129:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisplayHashThrottlingEnabled(z16);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    int i94 = parcel.readInt();
                    int i95 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    WindowContextInfo windowContextInfoAttachWindowContextToDisplayArea = attachWindowContextToDisplayArea(iApplicationThreadAsInterface, strongBinder16, i94, i95, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContextInfoAttachWindowContextToDisplayArea, 1);
                    return true;
                case 131:
                    IApplicationThread iApplicationThreadAsInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    WindowContextInfo windowContextInfoAttachWindowContextToWindowToken = attachWindowContextToWindowToken(iApplicationThreadAsInterface2, strongBinder17, strongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContextInfoAttachWindowContextToWindowToken, 1);
                    return true;
                case 132:
                    IApplicationThread iApplicationThreadAsInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    int i96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowContextInfo windowContextInfoAttachWindowContextToDisplayContent = attachWindowContextToDisplayContent(iApplicationThreadAsInterface3, strongBinder19, i96);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContextInfoAttachWindowContextToDisplayContent, 1);
                    return true;
                case 133:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachWindowContext(strongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    IApplicationThread iApplicationThreadAsInterface4 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zReparentWindowContextToDisplayArea = reparentWindowContextToDisplayArea(iApplicationThreadAsInterface4, strongBinder21, i97);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReparentWindowContextToDisplayArea);
                    return true;
                case 135:
                    ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListenerAsInterface = ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterCrossWindowBlurEnabledListener = registerCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterCrossWindowBlurEnabledListener);
                    return true;
                case 136:
                    ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListenerAsInterface2 = ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 137:
                    boolean zIsTaskSnapshotSupported = isTaskSnapshotSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTaskSnapshotSupported);
                    return true;
                case 138:
                    int imeDisplayId = getImeDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(imeDisplayId);
                    return true;
                case 139:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTaskSnapshotEnabled(z17);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    int i98 = parcel.readInt();
                    ITaskFpsCallback iTaskFpsCallbackAsInterface = ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskFpsCallback(i98, iTaskFpsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    ITaskFpsCallback iTaskFpsCallbackAsInterface2 = ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskFpsCallback(iTaskFpsCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    int i99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap bitmapSnapshotTaskForRecents = snapshotTaskForRecents(i99);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapSnapshotTaskForRecents, 1);
                    return true;
                case 143:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsAppBehindSystemBars(z18);
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
                    boolean zIsLetterboxBackgroundMultiColored = isLetterboxBackgroundMultiColored();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLetterboxBackgroundMultiColored);
                    return true;
                case 147:
                    int i100 = parcel.readInt();
                    ScreenCapture.CaptureArgs captureArgs = (ScreenCapture.CaptureArgs) parcel.readTypedObject(ScreenCapture.CaptureArgs.CREATOR);
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    captureDisplay(i100, captureArgs, screenCaptureListener);
                    return true;
                case 148:
                    int i101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsGlobalKey = isGlobalKey(i101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGlobalKey);
                    return true;
                case 149:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    boolean z19 = parcel.readBoolean();
                    ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListenerAsInterface = ISurfaceSyncGroupCompletedListener.Stub.asInterface(parcel.readStrongBinder());
                    AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new AddToSurfaceSyncGroupResult();
                    parcel.enforceNoDataAvail();
                    boolean zAddToSurfaceSyncGroup = addToSurfaceSyncGroup(strongBinder22, z19, iSurfaceSyncGroupCompletedListenerAsInterface, addToSurfaceSyncGroupResult);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddToSurfaceSyncGroup);
                    parcel2.writeTypedObject(addToSurfaceSyncGroupResult, 1);
                    return true;
                case 150:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    markSurfaceSyncGroupReady(strongBinder23);
                    return true;
                case 151:
                    int i102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> listNotifyScreenshotListeners = notifyScreenshotListeners(i102);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listNotifyScreenshotListeners, 1);
                    return true;
                case 152:
                    int i103 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zReplaceContentOnDisplay = replaceContentOnDisplay(i103, surfaceControl2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReplaceContentOnDisplay);
                    return true;
                case 153:
                    IDecorViewGestureListener iDecorViewGestureListenerAsInterface = IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int i104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDecorViewGestureListener(iDecorViewGestureListenerAsInterface, i104);
                    parcel2.writeNoException();
                    return true;
                case 154:
                    IDecorViewGestureListener iDecorViewGestureListenerAsInterface2 = IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int i105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterDecorViewGestureListener(iDecorViewGestureListenerAsInterface2, i105);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    ITrustedPresentationListener iTrustedPresentationListenerAsInterface = ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    TrustedPresentationThresholds trustedPresentationThresholds = (TrustedPresentationThresholds) parcel.readTypedObject(TrustedPresentationThresholds.CREATOR);
                    int i106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTrustedPresentationListener(strongBinder24, iTrustedPresentationListenerAsInterface, trustedPresentationThresholds, i106);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    ITrustedPresentationListener iTrustedPresentationListenerAsInterface2 = ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTrustedPresentationListener(iTrustedPresentationListenerAsInterface2, i107);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    IScreenRecordingCallback iScreenRecordingCallbackAsInterface = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterScreenRecordingCallback = registerScreenRecordingCallback(iScreenRecordingCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterScreenRecordingCallback);
                    return true;
                case 158:
                    IScreenRecordingCallback iScreenRecordingCallbackAsInterface2 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenRecordingCallback(iScreenRecordingCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    IScreenRecordingCallback iScreenRecordingCallbackAsInterface3 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterKnoxRemoteScreenCallback = registerKnoxRemoteScreenCallback(iScreenRecordingCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterKnoxRemoteScreenCallback);
                    return true;
                case 160:
                    IScreenRecordingCallback iScreenRecordingCallbackAsInterface4 = IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKnoxRemoteScreenCallback(iScreenRecordingCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    IGlobalDragListener iGlobalDragListenerAsInterface = IGlobalDragListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setGlobalDragListener(iGlobalDragListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    InputTransferToken inputTransferToken = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    InputTransferToken inputTransferToken2 = (InputTransferToken) parcel.readTypedObject(InputTransferToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zTransferTouchGesture = transferTouchGesture(inputTransferToken, inputTransferToken2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTransferTouchGesture);
                    return true;
                case 163:
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyboardShortcutGroup applicationLaunchKeyboardShortcuts = getApplicationLaunchKeyboardShortcuts(i108);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationLaunchKeyboardShortcuts, 1);
                    return true;
                case 164:
                    int i109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean ignoreOrientationRequest = getIgnoreOrientationRequest(i109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ignoreOrientationRequest);
                    return true;
                case 165:
                    clearDesktopWindowSettings();
                    parcel2.writeNoException();
                    return true;
                case 166:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeadzoneHole(bundle4);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    String string11 = parcel.readString();
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxAspectRatioPolicy = getMaxAspectRatioPolicy(string11, i110);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxAspectRatioPolicy);
                    return true;
                case 168:
                    String string12 = parcel.readString();
                    int i111 = parcel.readInt();
                    boolean z20 = parcel.readBoolean();
                    int i112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxAspectRatioPolicy(string12, i111, z20, i112);
                    parcel2.writeNoException();
                    return true;
                case 169:
                    int i113 = parcel.readInt();
                    int i114 = parcel.readInt();
                    boolean z21 = parcel.readBoolean();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i115 = parcel.readInt();
                    int i116 = parcel.readInt();
                    boolean z22 = parcel.readBoolean();
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ScreenshotResult screenshotResultTakeScreenshotToTargetWindow = takeScreenshotToTargetWindow(i113, i114, z21, rect3, i115, i116, z22, z23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotResultTakeScreenshotToTargetWindow, 1);
                    return true;
                case 170:
                    int i117 = parcel.readInt();
                    int i118 = parcel.readInt();
                    boolean z24 = parcel.readBoolean();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i119 = parcel.readInt();
                    int i120 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    boolean z26 = parcel.readBoolean();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ScreenshotResult screenshotResultTakeScreenshotToTargetWindowFromCapture = takeScreenshotToTargetWindowFromCapture(i117, i118, z24, rect4, i119, i120, z25, z26, z27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotResultTakeScreenshotToTargetWindowFromCapture, 1);
                    return true;
                case 171:
                    Point point3 = new Point();
                    parcel.enforceNoDataAvail();
                    getUserDisplaySize(point3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point3, 1);
                    return true;
                case 172:
                    int userDisplayDensity = getUserDisplayDensity();
                    parcel2.writeNoException();
                    parcel2.writeInt(userDisplayDensity);
                    return true;
                case 173:
                    int i121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplaySizeDensity(i121);
                    parcel2.writeNoException();
                    return true;
                case 174:
                    int i122 = parcel.readInt();
                    int i123 = parcel.readInt();
                    int i124 = parcel.readInt();
                    int i125 = parcel.readInt();
                    boolean z28 = parcel.readBoolean();
                    int i126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySizeDensity(i122, i123, i124, i125, z28, i126);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo = (MultiResolutionChangeRequestInfo) parcel.readTypedObject(MultiResolutionChangeRequestInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySizeDensityWithInfo(multiResolutionChangeRequestInfo);
                    parcel2.writeNoException();
                    return true;
                case 176:
                    int i127 = parcel.readInt();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int supportsFlexPanel = getSupportsFlexPanel(i127, string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportsFlexPanel);
                    return true;
                case 177:
                    int i128 = parcel.readInt();
                    String string14 = parcel.readString();
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSupportsFlexPanel(i128, string14, z29);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    int fullScreenAppsSupportMode = getFullScreenAppsSupportMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(fullScreenAppsSupportMode);
                    return true;
                case 179:
                    MagnificationSpec magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    boolean z30 = parcel.readBoolean();
                    IInputFilter iInputFilterAsInterface = IInputFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeDisplayScale(magnificationSpec, z30, iInputFilterAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    IOneHandOpWatcher iOneHandOpWatcherAsInterface = IOneHandOpWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerOneHandOpWatcher(iOneHandOpWatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 181:
                    IOneHandOpWatcher iOneHandOpWatcherAsInterface2 = IOneHandOpWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOneHandOpWatcher(iOneHandOpWatcherAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    IRemoteAnimationRunner iRemoteAnimationRunnerAsInterface = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStartRemoteWallpaperAnimation = startRemoteWallpaperAnimation(iRemoteAnimationRunnerAsInterface, i129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartRemoteWallpaperAnimation);
                    return true;
                case 183:
                    IRemoteAnimationRunner iRemoteAnimationRunnerAsInterface2 = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zFinishRemoteWallpaperAnimation = finishRemoteWallpaperAnimation(iRemoteAnimationRunnerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFinishRemoteWallpaperAnimation);
                    return true;
                case 184:
                    int i130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rotationLockOrientation = getRotationLockOrientation(i130);
                    parcel2.writeNoException();
                    parcel2.writeInt(rotationLockOrientation);
                    return true;
                case 185:
                    boolean zHasTaskbarTarget = hasTaskbarTarget();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasTaskbarTarget);
                    return true;
                case 186:
                    int i131 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRequestSystemKeyEvent = requestSystemKeyEvent(i131, componentName, z31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestSystemKeyEvent);
                    return true;
                case 187:
                    int i132 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemKeyEventRequested = isSystemKeyEventRequested(i132, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemKeyEventRequested);
                    return true;
                case 188:
                    int i133 = parcel.readInt();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemKeyEvent(i133, componentName3, i134);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    int i135 = parcel.readInt();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterSystemKeyEvent(i135, componentName4);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestMetaKeyEvent(componentName5, z32);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMetaKeyEventRequested = isMetaKeyEventRequested(componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMetaKeyEventRequested);
                    return true;
                case 192:
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = (SemWindowManager.KeyCustomizationInfo) parcel.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    putKeyCustomizationInfo(keyCustomizationInfo);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    int i136 = parcel.readInt();
                    int i137 = parcel.readInt();
                    int i138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 = getKeyCustomizationInfo(i136, i137, i138);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCustomizationInfo2, 1);
                    return true;
                case 194:
                    String string15 = parcel.readString();
                    int i139 = parcel.readInt();
                    int i140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfoByPackage = getKeyCustomizationInfoByPackage(string15, i139, i140);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCustomizationInfoByPackage, 1);
                    return true;
                case 195:
                    int i141 = parcel.readInt();
                    int i142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = getLastKeyCustomizationInfo(i141, i142);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastKeyCustomizationInfo, 1);
                    return true;
                case 196:
                    int i143 = parcel.readInt();
                    int i144 = parcel.readInt();
                    int i145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeKeyCustomizationInfo(i143, i144, i145);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    String string16 = parcel.readString();
                    int i146 = parcel.readInt();
                    int i147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeKeyCustomizationInfoByPackage(string16, i146, i147);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    int i148 = parcel.readInt();
                    int i149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearKeyCustomizationInfoByKeyCode(i148, i149);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    int i150 = parcel.readInt();
                    int i151 = parcel.readInt();
                    int i152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearKeyCustomizationInfoByAction(i150, i151, i152);
                    parcel2.writeNoException();
                    return true;
                case 200:
                    List<SemWindowManager.KeyCustomizationInfo> backupKeyCustomizationInfoList = getBackupKeyCustomizationInfoList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(backupKeyCustomizationInfoList, 1);
                    return true;
                case 201:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(SemWindowManager.KeyCustomizationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    restoreKeyCustomizationInfo(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 202:
                    IAssistDataReceiver iAssistDataReceiverAsInterface2 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    boolean z33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zOmniRequestAssistScreenshot = omniRequestAssistScreenshot(iAssistDataReceiverAsInterface2, z33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOmniRequestAssistScreenshot);
                    return true;
                case 203:
                    int i153 = parcel.readInt();
                    int i154 = parcel.readInt();
                    InputEvent[] inputEventArr = (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR);
                    IBinder strongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    dispatchSPenGestureEvent(i153, i154, inputEventArr, strongBinder25);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPendingIntentAfterUnlock(pendingIntent, intent);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    boolean zIsKeyguardShowingAndNotOccluded = isKeyguardShowingAndNotOccluded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyguardShowingAndNotOccluded);
                    return true;
                case 206:
                    startLockscreenFingerprintAuth();
                    parcel2.writeNoException();
                    return true;
                case 207:
                    int topFocusedDisplayId = getTopFocusedDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(topFocusedDisplayId);
                    return true;
                case 208:
                    int i155 = parcel.readInt();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveDisplayToTop(i155, string17);
                    return true;
                case 209:
                    boolean z34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDragSurfaceToOverlay(z34);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    IBinder strongBinder26 = parcel.readStrongBinder();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startSurfaceAnimation(strongBinder26, string18);
                    return true;
                case 211:
                    boolean zIsFolded = isFolded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFolded);
                    return true;
                case 212:
                    boolean zIsTableMode = isTableMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTableMode);
                    return true;
                case 213:
                    boolean z35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTableModeEnabled(z35);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    int i156 = parcel.readInt();
                    String string19 = parcel.readString();
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int appContinuityMode = getAppContinuityMode(i156, string19, activityInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(appContinuityMode);
                    return true;
                case 215:
                    int i157 = parcel.readInt();
                    String string20 = parcel.readString();
                    boolean z36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppContinuityMode(i157, string20, z36);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    IAuthTouchEventListener iAuthTouchEventListenerAsInterface = IAuthTouchEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthTouchEventListener(iAuthTouchEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    IAuthTouchEventListener iAuthTouchEventListenerAsInterface2 = IAuthTouchEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthTouchEventListener(iAuthTouchEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    int i158 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayColorToSystemProperties(i158);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEdgeToEdgeDisabled = isEdgeToEdgeDisabled(string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEdgeToEdgeDisabled);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean stopViewServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isViewServerRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindowSessionCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IWindowSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getInitialDisplaySize(int i, Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        point.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getBaseDisplaySize(int i, Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        point.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySize(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySize(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getInitialDisplayDensity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getBaseDisplayDensity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayIdByUniqueId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityForUser(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplayDensityForUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityRatio(int i, float f, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setConfigurationChangeSettingsForUser(List<ConfigurationChangeSetting> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayScalingMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setEventDispatching(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowToken(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeWindowToken(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayChangeWindowController);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SurfaceControl) parcelObtain2.readTypedObject(SurfaceControl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppTransitionAnimationSpecsFuture);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteAnimationAdapter, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void endProlongedAnimations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void disableKeyguard(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void reenableKeyguard(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnKeyguardExitResult);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardLocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardSecure(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSwitchingUser(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getAnimationScale(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float[] getAnimationScales() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScale(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScales(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getCurrentAnimatorScale() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchMode(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchModeOnAllDisplays(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isInTouchMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showStrictModeViolation(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setStrictModeVisualIndicatorPreference(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void refreshScreenCaptureDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDefaultDisplayRotation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayUserRotation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int watchRotation(IRotationWatcher iRotationWatcher, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRotationWatcher);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getPreferredOptionsPanelGravity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeRotation(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawRotation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isRotationFrozen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeDisplayRotation(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawDisplayRotation(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isDisplayRotationFrozen(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setFixedToUserRotation(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setIgnoreOrientationRequest(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap screenshotWallpaper() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SurfaceControl mirrorWallpaperSurface(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SurfaceControl) parcelObtain2.readTypedObject(SurfaceControl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperVisibilityListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWallpaperVisibilityListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSystemGestureExclusionListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSystemGestureExclusionListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void hideTransientBars(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsVisibility(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(rectArr, 0);
                    this.mRemote.transact(72, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasNavigationBar(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void lockNow(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSafeModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContentFrameStats) parcelObtain2.readTypedObject(WindowContentFrameStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDockedStackSide() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iPinnedTaskListener);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getStableInsets(int i, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        rect.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getOverrideStableInsets(int i, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        rect.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iShortcutService);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        inputChannel.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean destroyInputConsumer(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Region getCurrentImeTouchRegion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Region) parcelObtain2.readTypedObject(Region.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startWindowTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopWindowTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void saveWindowTraceToFile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowTraceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startTransitionTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopTransitionTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTransitionTraceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getWindowingMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setWindowingMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRemoveContentMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRemoveContentMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowWithInsecureKeyguard(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowWithInsecureKeyguard(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowSystemDecors(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowSystemDecors(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isEligibleForDesktopMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayImePolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayImePolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void onNotificationShadeExpanded(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void syncInputTransactions(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLayerTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracing(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean mirrorDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    if (parcelObtain2.readInt() != 0) {
                        surfaceControl.readFromParcel(parcelObtain2);
                    }
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDisplayWindowInsetsController);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowRequestedVisibleTypes(int i, int i2, int i3, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowAnimatingTypes(int i, int i2, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    if (parcelObtain2.readInt() != 0) {
                        insetsState.readFromParcel(parcelObtain2);
                    }
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<DisplayInfo> getPossibleDisplayInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DisplayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showGlobalActions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracingFlags(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setActiveTransactionTracing(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iScrollCaptureResponseListener);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(smartClipRemoteRequestInfo, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public String[] getSupportedDisplayHashAlgorithms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayHash, 0);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifiedDisplayHash) parcelObtain2.readTypedObject(VerifiedDisplayHash.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayHashThrottlingEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContextInfo) parcelObtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToWindowToken(IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContextInfo) parcelObtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public WindowContextInfo attachWindowContextToDisplayContent(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContextInfo) parcelObtain2.readTypedObject(WindowContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void detachWindowContext(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean reparentWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTaskSnapshotSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getImeDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTaskSnapshotEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public Bitmap snapshotTaskForRecents(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsAppBehindSystemBars(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.VisibleWindowInfo> getVisibleWindowInfoList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemWindowManager.VisibleWindowInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getLetterboxBackgroundColorInArgb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLetterboxBackgroundMultiColored() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(captureArgs, 0);
                    parcelObtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(147, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isGlobalKey(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iSurfaceSyncGroupCompletedListener);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z2 = parcelObtain2.readBoolean();
                    if (parcelObtain2.readInt() != 0) {
                        addToSurfaceSyncGroupResult.readFromParcel(parcelObtain2);
                    }
                    return z2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void markSurfaceSyncGroupReady(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(150, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<ComponentName> notifyScreenshotListeners(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDecorViewGestureListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDecorViewGestureListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTrustedPresentationListener(IBinder iBinder, ITrustedPresentationListener iTrustedPresentationListener, TrustedPresentationThresholds trustedPresentationThresholds, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iTrustedPresentationListener);
                    parcelObtain.writeTypedObject(trustedPresentationThresholds, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTrustedPresentationListener(ITrustedPresentationListener iTrustedPresentationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTrustedPresentationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGlobalDragListener);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputTransferToken, 0);
                    parcelObtain.writeTypedObject(inputTransferToken2, 0);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyboardShortcutGroup) parcelObtain2.readTypedObject(KeyboardShortcutGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getIgnoreOrientationRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearDesktopWindowSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDeadzoneHole(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getMaxAspectRatioPolicy(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ScreenshotResult) parcelObtain2.readTypedObject(ScreenshotResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ScreenshotResult) parcelObtain2.readTypedObject(ScreenshotResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getUserDisplaySize(Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        point.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getUserDisplayDensity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySizeDensity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(multiResolutionChangeRequestInfo, 0);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getSupportsFlexPanel(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSupportsFlexPanel(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getFullScreenAppsSupportMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iInputFilter);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOneHandOpWatcher);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOneHandOpWatcher);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAnimationRunner);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAnimationRunner);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRotationLockOrientation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasTaskbarTarget() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSystemKeyEventRequested(int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemKeyEvent(int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestMetaKeyEvent(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isMetaKeyEventRequested(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyCustomizationInfo, 0);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) parcelObtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) parcelObtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWindowManager.KeyCustomizationInfo) parcelObtain2.readTypedObject(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfo(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByKeyCode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public List<SemWindowManager.KeyCustomizationInfo> getBackupKeyCustomizationInfoList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemWindowManager.KeyCustomizationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void restoreKeyCustomizationInfo(List<SemWindowManager.KeyCustomizationInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(inputEventArr, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardShowingAndNotOccluded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startLockscreenFingerprintAuth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getTopFocusedDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void moveDisplayToTop(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(208, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDragSurfaceToOverlay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startSurfaceAnimation(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(210, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isFolded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTableMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTableModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(activityInfo, 0);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAppContinuityMode(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAuthTouchEventListener);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAuthTouchEventListener);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayColorToSystemProperties(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isEdgeToEdgeDisabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
