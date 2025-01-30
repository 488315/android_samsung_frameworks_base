package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.MagnificationConfig;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.inputmethod.EditorInfo;
import android.window.ScreenCapture;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.util.DumpUtils;
import com.android.server.wm.WindowManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ProxyAccessibilityServiceConnection extends AccessibilityServiceConnection {
  public int mDeviceId;
  public int mDisplayId;
  public int mFocusColor;
  public int mFocusStrokeWidth;
  public List mInstalledAndEnabledServices;
  public int mInteractiveTimeout;
  public int mNonInteractiveTimeout;

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // android.os.IBinder.DeathRecipient
  public void binderDied() {}

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean hasRightsToCurrentUserLocked() {
    return true;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean supportsFlagForNotImportantViews(
      AccessibilityServiceInfo accessibilityServiceInfo) {
    return true;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void attachAccessibilityOverlayToDisplay(
      int i, SurfaceControl surfaceControl) {
    super.attachAccessibilityOverlayToDisplay(i, surfaceControl);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void attachAccessibilityOverlayToWindow(
      int i, SurfaceControl surfaceControl) {
    super.attachAccessibilityOverlayToWindow(i, surfaceControl);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void bindInputLocked() {
    super.bindInputLocked();
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void bindLocked() {
    super.bindLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean canReceiveEventsLocked() {
    return super.canReceiveEventsLocked();
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean canRetrieveInteractiveWindowsLocked() {
    return super.canRetrieveInteractiveWindowsLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void createImeSessionLocked() {
    super.createImeSessionLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ String[] findAccessibilityNodeInfoByAccessibilityId(
      int i,
      long j,
      int i2,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      int i3,
      long j2,
      Bundle bundle) {
    return super.findAccessibilityNodeInfoByAccessibilityId(
        i, j, i2, iAccessibilityInteractionConnectionCallback, i3, j2, bundle);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ String[] findAccessibilityNodeInfosByText(
      int i,
      long j,
      String str,
      int i2,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      long j2) {
    return super.findAccessibilityNodeInfosByText(
        i, j, str, i2, iAccessibilityInteractionConnectionCallback, j2);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ String[] findAccessibilityNodeInfosByViewId(
      int i,
      long j,
      String str,
      int i2,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      long j2) {
    return super.findAccessibilityNodeInfosByViewId(
        i, j, str, i2, iAccessibilityInteractionConnectionCallback, j2);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ String[] findFocus(
      int i,
      long j,
      int i2,
      int i3,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      long j2) {
    return super.findFocus(i, j, i2, i3, iAccessibilityInteractionConnectionCallback, j2);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ String[] focusSearch(
      int i,
      long j,
      int i2,
      int i3,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      long j2) {
    return super.focusSearch(i, j, i2, i3, iAccessibilityInteractionConnectionCallback, j2);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ int getCapabilities() {
    return super.getCapabilities();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ ComponentName getComponentName() {
    return super.getComponentName();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ IBinder getOverlayWindowToken(int i) {
    return super.getOverlayWindowToken(i);
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ AccessibilityServiceInfo getServiceInfo() {
    return super.getServiceInfo();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ AccessibilityWindowInfo getWindow(int i) {
    return super.getWindow(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ int getWindowIdForLeashToken(IBinder iBinder) {
    return super.getWindowIdForLeashToken(iBinder);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ List getWindowsMainDisplay(int i) {
    return super.getWindowsMainDisplay(i);
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isAccessibilityButtonAvailableLocked(
      AccessibilityUserState accessibilityUserState) {
    return super.isAccessibilityButtonAvailableLocked(accessibilityUserState);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isConnectedLocked() {
    return super.isConnectedLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isMultiFingerGesturesEnabled() {
    return super.isMultiFingerGesturesEnabled();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isSendMotionEventsEnabled() {
    return super.isSendMotionEventsEnabled();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isServiceDetectsGesturesEnabled(int i) {
    return super.isServiceDetectsGesturesEnabled(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isServiceHandlesDoubleTapEnabled() {
    return super.isServiceHandlesDoubleTapEnabled();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean isTwoFingerPassthroughEnabled() {
    return super.isTwoFingerPassthroughEnabled();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void logTrace(
      long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) {
    super.logTrace(j, str, j2, str2, i, j3, i2, bundle);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyAccessibilityButtonAvailabilityChangedLocked(
      boolean z) {
    super.notifyAccessibilityButtonAvailabilityChangedLocked(z);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyAccessibilityButtonClickedLocked(int i) {
    super.notifyAccessibilityButtonClickedLocked(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyAccessibilityEvent(
      AccessibilityEvent accessibilityEvent) {
    super.notifyAccessibilityEvent(accessibilityEvent);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyClearAccessibilityNodeInfoCache() {
    super.notifyClearAccessibilityNodeInfoCache();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyGesture(
      AccessibilityGestureEvent accessibilityGestureEvent) {
    super.notifyGesture(accessibilityGestureEvent);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyMagnificationChangedLocked(
      int i, Region region, MagnificationConfig magnificationConfig) {
    super.notifyMagnificationChangedLocked(i, region, magnificationConfig);
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyMotionEvent(MotionEvent motionEvent) {
    super.notifyMotionEvent(motionEvent);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifySoftKeyboardShowModeChangedLocked(int i) {
    super.notifySoftKeyboardShowModeChangedLocked(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifySystemActionsChangedLocked() {
    super.notifySystemActionsChangedLocked();
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void notifyTouchState(int i, int i2) {
    super.notifyTouchState(i, i2);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void onAdded() {
    super.onAdded();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void onDisplayAdded(int i) {
    super.onDisplayAdded(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void onDisplayRemoved(int i) {
    super.onDisplayRemoved(i);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void onRemoved() {
    super.onRemoved();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean performAccessibilityAction(
      int i,
      long j,
      int i2,
      Bundle bundle,
      int i3,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
      long j2) {
    return super.performAccessibilityAction(
        i, j, i2, bundle, i3, iAccessibilityInteractionConnectionCallback, j2);
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean requestImeApis() {
    return super.requestImeApis();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void resetLocked() {
    super.resetLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void setAttributionTag(String str) {
    super.setAttributionTag(str);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void setCacheEnabled(boolean z) {
    super.setCacheEnabled(z);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void setDynamicallyConfigurableProperties(
      AccessibilityServiceInfo accessibilityServiceInfo) {
    super.setDynamicallyConfigurableProperties(accessibilityServiceInfo);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void setImeSessionEnabledLocked(
      IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) {
    super.setImeSessionEnabledLocked(iAccessibilityInputMethodSession, z);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void startInputLocked(
      IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
      EditorInfo editorInfo,
      boolean z) {
    super.startInputLocked(iRemoteAccessibilityInputConnection, editorInfo, z);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void takeScreenshotOfWindow(
      int i,
      int i2,
      ScreenCapture.ScreenCaptureListener screenCaptureListener,
      IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
    super.takeScreenshotOfWindow(
        i, i2, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void unbindInputLocked() {
    super.unbindInputLocked();
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public /* bridge */ /* synthetic */ void unbindLocked() {
    super.unbindLocked();
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public /* bridge */ /* synthetic */ boolean wantsGenericMotionEvent(MotionEvent motionEvent) {
    return super.wantsGenericMotionEvent(motionEvent);
  }

  public ProxyAccessibilityServiceConnection(
      Context context,
      ComponentName componentName,
      AccessibilityServiceInfo accessibilityServiceInfo,
      int i,
      Handler handler,
      Object obj,
      AccessibilitySecurityPolicy accessibilitySecurityPolicy,
      AbstractAccessibilityServiceConnection.SystemSupport systemSupport,
      AccessibilityTrace accessibilityTrace,
      WindowManagerInternal windowManagerInternal,
      AccessibilityWindowManager accessibilityWindowManager,
      int i2,
      int i3) {
    super(
        null,
        context,
        componentName,
        accessibilityServiceInfo,
        i,
        handler,
        obj,
        accessibilitySecurityPolicy,
        systemSupport,
        accessibilityTrace,
        windowManagerInternal,
        null,
        accessibilityWindowManager,
        null);
    this.mDisplayId = i2;
    setDisplayTypes(2);
    this.mFocusStrokeWidth =
        this.mContext
            .getResources()
            .getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
    this.mFocusColor =
        this.mContext.getResources().getColor(R.color.accent_primary_variant_dark_device_default);
    this.mDeviceId = i3;
  }

  public int getDisplayId() {
    return this.mDisplayId;
  }

  public int getDeviceId() {
    return this.mDeviceId;
  }

  public void initializeServiceInterface(IAccessibilityServiceClient iAccessibilityServiceClient) {
    this.mServiceInterface = iAccessibilityServiceClient;
    this.mService = iAccessibilityServiceClient.asBinder();
    this.mServiceInterface.init(
        this, this.mId, (IBinder) this.mOverlayWindowTokens.get(this.mDisplayId));
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setInstalledAndEnabledServices(List list) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        this.mInstalledAndEnabledServices = list;
        AccessibilityServiceInfo accessibilityServiceInfo = this.mAccessibilityServiceInfo;
        accessibilityServiceInfo.flags = 0;
        accessibilityServiceInfo.eventTypes = 0;
        accessibilityServiceInfo.notificationTimeout = 0L;
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        boolean z = false;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        while (it.hasNext()) {
          AccessibilityServiceInfo accessibilityServiceInfo2 = (AccessibilityServiceInfo) it.next();
          z |= accessibilityServiceInfo2.isAccessibilityTool();
          String[] strArr = accessibilityServiceInfo2.packageNames;
          if (strArr != null && strArr.length != 0) {
            if (!z2) {
              hashSet.addAll(Arrays.asList(strArr));
            }
            i = Math.max(i, accessibilityServiceInfo2.getInteractiveUiTimeoutMillis());
            i2 = Math.max(i2, accessibilityServiceInfo2.getNonInteractiveUiTimeoutMillis());
            accessibilityServiceInfo.notificationTimeout =
                Math.max(
                    accessibilityServiceInfo.notificationTimeout,
                    accessibilityServiceInfo2.notificationTimeout);
            accessibilityServiceInfo.eventTypes |= accessibilityServiceInfo2.eventTypes;
            accessibilityServiceInfo.feedbackType |= accessibilityServiceInfo2.feedbackType;
            accessibilityServiceInfo.flags |= accessibilityServiceInfo2.flags;
            setDefaultPropertiesIfNullLocked(accessibilityServiceInfo2);
            hashSet = hashSet;
          }
          z2 = true;
          i = Math.max(i, accessibilityServiceInfo2.getInteractiveUiTimeoutMillis());
          i2 = Math.max(i2, accessibilityServiceInfo2.getNonInteractiveUiTimeoutMillis());
          accessibilityServiceInfo.notificationTimeout =
              Math.max(
                  accessibilityServiceInfo.notificationTimeout,
                  accessibilityServiceInfo2.notificationTimeout);
          accessibilityServiceInfo.eventTypes |= accessibilityServiceInfo2.eventTypes;
          accessibilityServiceInfo.feedbackType |= accessibilityServiceInfo2.feedbackType;
          accessibilityServiceInfo.flags |= accessibilityServiceInfo2.flags;
          setDefaultPropertiesIfNullLocked(accessibilityServiceInfo2);
          hashSet = hashSet;
        }
        HashSet hashSet2 = hashSet;
        accessibilityServiceInfo.setAccessibilityTool(z);
        accessibilityServiceInfo.setInteractiveUiTimeoutMillis(i);
        accessibilityServiceInfo.setNonInteractiveUiTimeoutMillis(i2);
        this.mInteractiveTimeout = i;
        this.mNonInteractiveTimeout = i2;
        if (z2) {
          accessibilityServiceInfo.packageNames = null;
        } else {
          accessibilityServiceInfo.packageNames = (String[]) hashSet2.toArray(new String[0]);
        }
        setDynamicallyConfigurableProperties(accessibilityServiceInfo);
        this.mSystemSupport.onProxyChanged(this.mDeviceId);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void setDefaultPropertiesIfNullLocked(
      AccessibilityServiceInfo accessibilityServiceInfo) {
    String str = "ProxyClass" + this.mDisplayId;
    if (accessibilityServiceInfo.getResolveInfo() == null) {
      ResolveInfo resolveInfo = new ResolveInfo();
      ServiceInfo serviceInfo = new ServiceInfo();
      ApplicationInfo applicationInfo = new ApplicationInfo();
      serviceInfo.packageName = "ProxyPackage";
      serviceInfo.name = str;
      applicationInfo.processName = "ProxyPackage";
      applicationInfo.className = str;
      resolveInfo.serviceInfo = serviceInfo;
      serviceInfo.applicationInfo = applicationInfo;
      accessibilityServiceInfo.setResolveInfo(resolveInfo);
    }
    if (accessibilityServiceInfo.getComponentName() == null) {
      accessibilityServiceInfo.setComponentName(new ComponentName("ProxyPackage", str));
    }
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public List getInstalledAndEnabledServices() {
    List list;
    synchronized (this.mLock) {
      list = this.mInstalledAndEnabledServices;
    }
    return list;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public AccessibilityWindowInfo.WindowListSparseArray getWindows() {
    AccessibilityWindowInfo.WindowListSparseArray windows = super.getWindows();
    AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray =
        new AccessibilityWindowInfo.WindowListSparseArray();
    int i = this.mDisplayId;
    windowListSparseArray.put(i, (List) windows.get(i, Collections.emptyList()));
    return windowListSparseArray;
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setFocusAppearance(int i, int i2) {
    synchronized (this.mLock) {
      if (hasRightsToCurrentUserLocked()) {
        if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
          if (getFocusStrokeWidthLocked() == i && getFocusColorLocked() == i2) {
            return;
          }
          this.mFocusStrokeWidth = i;
          this.mFocusColor = i2;
          this.mSystemSupport.onProxyChanged(this.mDeviceId);
        }
      }
    }
  }

  public int getFocusStrokeWidthLocked() {
    return this.mFocusStrokeWidth;
  }

  public int getFocusColorLocked() {
    return this.mFocusColor;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public int resolveAccessibilityWindowIdForFindFocusLocked(int i, int i2) {
    if (i == -2) {
      i = this.mA11yWindowManager.getFocusedWindowId(i2, this.mDisplayId);
      if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(i, this.mDisplayTypes)) {
        return -1;
      }
    }
    return i;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection,
  // com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter
  public boolean onKeyEvent(KeyEvent keyEvent, int i) {
    throw new UnsupportedOperationException("onKeyEvent is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
  public boolean isCapturingFingerprintGestures() {
    throw new UnsupportedOperationException("isCapturingFingerprintGestures is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
  public void onFingerprintGestureDetectionActiveChanged(boolean z) {
    throw new UnsupportedOperationException(
        "onFingerprintGestureDetectionActiveChanged is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
  public void onFingerprintGesture(int i) {
    throw new UnsupportedOperationException("onFingerprintGesture is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean isFingerprintGestureDetectionAvailable() {
    throw new UnsupportedOperationException(
        "isFingerprintGestureDetectionAvailable is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // android.content.ServiceConnection
  public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    throw new UnsupportedOperationException("onServiceConnected is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // android.content.ServiceConnection
  public void onServiceDisconnected(ComponentName componentName) {
    throw new UnsupportedOperationException("onServiceDisconnected is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
    throw new UnsupportedOperationException("setServiceInfo is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public void disableSelf() {
    throw new UnsupportedOperationException("disableSelf is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean performGlobalAction(int i) {
    throw new UnsupportedOperationException("performGlobalAction is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setOnKeyEventResult(boolean z, int i) {
    throw new UnsupportedOperationException("setOnKeyEventResult is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public List getSystemActions() {
    throw new UnsupportedOperationException("getSystemActions is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public MagnificationConfig getMagnificationConfig(int i) {
    throw new UnsupportedOperationException("getMagnificationConfig is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public float getMagnificationScale(int i) {
    throw new UnsupportedOperationException("getMagnificationScale is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public float getMagnificationCenterX(int i) {
    throw new UnsupportedOperationException("getMagnificationCenterX is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public float getMagnificationCenterY(int i) {
    throw new UnsupportedOperationException("getMagnificationCenterY is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public Region getMagnificationRegion(int i) {
    throw new UnsupportedOperationException("getMagnificationRegion is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public Region getCurrentMagnificationRegion(int i) {
    throw new UnsupportedOperationException("getCurrentMagnificationRegion is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean resetMagnification(int i, boolean z) {
    throw new UnsupportedOperationException("resetMagnification is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean resetCurrentMagnification(int i, boolean z) {
    throw new UnsupportedOperationException("resetCurrentMagnification is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) {
    throw new UnsupportedOperationException("setMagnificationConfig is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setMagnificationCallbackEnabled(int i, boolean z) {
    throw new UnsupportedOperationException("setMagnificationCallbackEnabled is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public boolean isMagnificationCallbackEnabled(int i) {
    throw new UnsupportedOperationException("isMagnificationCallbackEnabled is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public boolean setSoftKeyboardShowMode(int i) {
    throw new UnsupportedOperationException("setSoftKeyboardShowMode is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public int getSoftKeyboardShowMode() {
    throw new UnsupportedOperationException("getSoftKeyboardShowMode is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setSoftKeyboardCallbackEnabled(boolean z) {
    throw new UnsupportedOperationException("setSoftKeyboardCallbackEnabled is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public boolean switchToInputMethod(String str) {
    throw new UnsupportedOperationException("switchToInputMethod is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public int setInputMethodEnabled(String str, boolean z) {
    throw new UnsupportedOperationException("setInputMethodEnabled is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection
  public boolean isAccessibilityButtonAvailable() {
    throw new UnsupportedOperationException("isAccessibilityButtonAvailable is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void sendGesture(int i, ParceledListSlice parceledListSlice) {
    throw new UnsupportedOperationException("sendGesture is not supported");
  }

  @Override // com.android.server.accessibility.AccessibilityServiceConnection,
  // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) {
    throw new UnsupportedOperationException("dispatchGesture is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void takeScreenshot(int i, RemoteCallback remoteCallback) {
    throw new UnsupportedOperationException("takeScreenshot is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setGestureDetectionPassthroughRegion(int i, Region region) {
    throw new UnsupportedOperationException(
        "setGestureDetectionPassthroughRegion is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setTouchExplorationPassthroughRegion(int i, Region region) {
    throw new UnsupportedOperationException(
        "setTouchExplorationPassthroughRegion is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setServiceDetectsGesturesEnabled(int i, boolean z) {
    throw new UnsupportedOperationException("setServiceDetectsGesturesEnabled is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void requestTouchExploration(int i) {
    throw new UnsupportedOperationException("requestTouchExploration is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void requestDragging(int i, int i2) {
    throw new UnsupportedOperationException("requestDragging is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void requestDelegating(int i) {
    throw new UnsupportedOperationException("requestDelegating is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void onDoubleTap(int i) {
    throw new UnsupportedOperationException("onDoubleTap is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void onDoubleTapAndHold(int i) {
    throw new UnsupportedOperationException("onDoubleTapAndHold is not supported");
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void setAnimationScale(float f) {
    throw new UnsupportedOperationException("setAnimationScale is not supported");
  }

  public int getInteractiveTimeout() {
    return this.mInteractiveTimeout;
  }

  public int getNonInteractiveTimeout() {
    return this.mNonInteractiveTimeout;
  }

  public boolean updateTimeouts(int i, int i2) {
    boolean z;
    if (i2 == 0) {
      i2 = this.mAccessibilityServiceInfo.getInteractiveUiTimeoutMillis();
    }
    if (i == 0) {
      i = this.mAccessibilityServiceInfo.getNonInteractiveUiTimeoutMillis();
    }
    if (this.mInteractiveTimeout != i2) {
      this.mInteractiveTimeout = i2;
      z = true;
    } else {
      z = false;
    }
    if (this.mNonInteractiveTimeout == i) {
      return z;
    }
    this.mNonInteractiveTimeout = i;
    return true;
  }

  @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(
        this.mContext, "ProxyAccessibilityServiceConnection", printWriter)) {
      synchronized (this.mLock) {
        printWriter.append((CharSequence) ("Proxy[displayId=" + this.mDisplayId));
        printWriter.append((CharSequence) (", deviceId=" + this.mDeviceId));
        printWriter.append(
            (CharSequence)
                (", feedbackType"
                    + AccessibilityServiceInfo.feedbackTypeToString(this.mFeedbackType)));
        printWriter.append(
            (CharSequence) (", capabilities=" + this.mAccessibilityServiceInfo.getCapabilities()));
        printWriter.append(
            (CharSequence)
                (", eventTypes=" + AccessibilityEvent.eventTypeToString(this.mEventTypes)));
        printWriter.append((CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
        printWriter
            .append(", nonInteractiveUiTimeout=")
            .append((CharSequence) String.valueOf(this.mNonInteractiveTimeout));
        printWriter
            .append(", interactiveUiTimeout=")
            .append((CharSequence) String.valueOf(this.mInteractiveTimeout));
        printWriter
            .append(", focusStrokeWidth=")
            .append((CharSequence) String.valueOf(this.mFocusStrokeWidth));
        printWriter.append(", focusColor=").append((CharSequence) String.valueOf(this.mFocusColor));
        printWriter
            .append(", installedAndEnabledServiceCount=")
            .append((CharSequence) String.valueOf(this.mInstalledAndEnabledServices.size()));
        printWriter
            .append(", installedAndEnabledServices=")
            .append((CharSequence) this.mInstalledAndEnabledServices.toString());
        printWriter.append("]");
      }
    }
  }
}
