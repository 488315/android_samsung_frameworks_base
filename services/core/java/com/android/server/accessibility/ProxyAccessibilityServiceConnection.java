package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.MagnificationConfig;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityWindowInfo;

import com.android.internal.util.DumpUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProxyAccessibilityServiceConnection extends AccessibilityServiceConnection {
    public int mDeviceId;
    public int mDisplayId;
    public int mFocusColor;
    public int mFocusStrokeWidth;
    public List mInstalledAndEnabledServices;
    public int mInteractiveTimeout;
    public int mNonInteractiveTimeout;

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // android.os.IBinder.DeathRecipient
    public final void binderDied() {}

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final void disableSelf() {
        throw new UnsupportedOperationException("disableSelf is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) {
        throw new UnsupportedOperationException("dispatchGesture is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(
                this.mContext, "ProxyAccessibilityServiceConnection", printWriter)) {
            synchronized (this.mLock) {
                printWriter.append((CharSequence) ("Proxy[displayId=" + this.mDisplayId));
                printWriter.append((CharSequence) (", deviceId=" + this.mDeviceId));
                printWriter.append(
                        (CharSequence)
                                (", feedbackType"
                                        + AccessibilityServiceInfo.feedbackTypeToString(
                                                this.mFeedbackType)));
                printWriter.append(
                        (CharSequence)
                                (", capabilities="
                                        + this.mAccessibilityServiceInfo.getCapabilities()));
                printWriter.append(
                        (CharSequence)
                                (", eventTypes="
                                        + AccessibilityEvent.eventTypeToString(this.mEventTypes)));
                printWriter.append(
                        (CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                printWriter
                        .append(", nonInteractiveUiTimeout=")
                        .append((CharSequence) String.valueOf(this.mNonInteractiveTimeout));
                printWriter
                        .append(", interactiveUiTimeout=")
                        .append((CharSequence) String.valueOf(this.mInteractiveTimeout));
                printWriter
                        .append(", focusStrokeWidth=")
                        .append((CharSequence) String.valueOf(this.mFocusStrokeWidth));
                printWriter
                        .append(", focusColor=")
                        .append((CharSequence) String.valueOf(this.mFocusColor));
                printWriter
                        .append(", installedAndEnabledServiceCount=")
                        .append(
                                (CharSequence)
                                        String.valueOf(this.mInstalledAndEnabledServices.size()));
                printWriter
                        .append(", installedAndEnabledServices=")
                        .append((CharSequence) this.mInstalledAndEnabledServices.toString());
                printWriter.append("]");
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final Region getCurrentMagnificationRegion(int i) {
        throw new UnsupportedOperationException("getCurrentMagnificationRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final List getInstalledAndEnabledServices() {
        List list;
        synchronized (this.mLock) {
            list = this.mInstalledAndEnabledServices;
            if (list == null) {
                list = Collections.emptyList();
            }
        }
        return list;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final float getMagnificationCenterX(int i) {
        throw new UnsupportedOperationException("getMagnificationCenterX is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final float getMagnificationCenterY(int i) {
        throw new UnsupportedOperationException("getMagnificationCenterY is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final MagnificationConfig getMagnificationConfig(int i) {
        throw new UnsupportedOperationException("getMagnificationConfig is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final Region getMagnificationRegion(int i) {
        throw new UnsupportedOperationException("getMagnificationRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final float getMagnificationScale(int i) {
        throw new UnsupportedOperationException("getMagnificationScale is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final int getSoftKeyboardShowMode() {
        throw new UnsupportedOperationException("getSoftKeyboardShowMode is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final List getSystemActions() {
        throw new UnsupportedOperationException("getSystemActions is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final AccessibilityWindowInfo.WindowListSparseArray getWindows() {
        AccessibilityWindowInfo.WindowListSparseArray windows = super.getWindows();
        AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray =
                new AccessibilityWindowInfo.WindowListSparseArray();
        int i = this.mDisplayId;
        windowListSparseArray.put(i, (List) windows.get(i, Collections.emptyList()));
        return windowListSparseArray;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean hasRightsToCurrentUserLocked() {
        return true;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final boolean isAccessibilityButtonAvailable() {
        throw new UnsupportedOperationException("isAccessibilityButtonAvailable is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public final boolean isCapturingFingerprintGestures() {
        throw new UnsupportedOperationException("isCapturingFingerprintGestures is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean isFingerprintGestureDetectionAvailable() {
        throw new UnsupportedOperationException(
                "isFingerprintGestureDetectionAvailable is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean isMagnificationCallbackEnabled(int i) {
        throw new UnsupportedOperationException("isMagnificationCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void onDoubleTap(int i) {
        throw new UnsupportedOperationException("onDoubleTap is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void onDoubleTapAndHold(int i) {
        throw new UnsupportedOperationException("onDoubleTapAndHold is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public final void onFingerprintGesture(int i) {
        throw new UnsupportedOperationException("onFingerprintGesture is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public final void onFingerprintGestureDetectionActiveChanged(boolean z) {
        throw new UnsupportedOperationException(
                "onFingerprintGestureDetectionActiveChanged is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection,
              // com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter
    public final boolean onKeyEvent(KeyEvent keyEvent, int i) {
        throw new UnsupportedOperationException("onKeyEvent is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        throw new UnsupportedOperationException("onServiceConnected is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        throw new UnsupportedOperationException("onServiceDisconnected is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean performGlobalAction(int i) {
        throw new UnsupportedOperationException("performGlobalAction is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void requestDelegating(int i) {
        throw new UnsupportedOperationException("requestDelegating is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void requestDragging(int i, int i2) {
        throw new UnsupportedOperationException("requestDragging is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void requestTouchExploration(int i) {
        throw new UnsupportedOperationException("requestTouchExploration is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean resetCurrentMagnification(int i, boolean z) {
        throw new UnsupportedOperationException("resetCurrentMagnification is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean resetMagnification(int i, boolean z) {
        throw new UnsupportedOperationException("resetMagnification is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final int resolveAccessibilityWindowIdForFindFocusLocked(int i, int i2) {
        if (i == -2) {
            AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
            int i3 = this.mDisplayId;
            accessibilityWindowManager.getClass();
            if (i3 == -1 || i3 == 0 || !accessibilityWindowManager.mHasProxy) {
                i = accessibilityWindowManager.getDefaultFocus(i2);
            } else {
                AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver =
                        (AccessibilityWindowManager.DisplayWindowsObserver)
                                accessibilityWindowManager.mDisplayWindowsObservers.get(i3);
                i =
                        (displayWindowsObserver == null || !displayWindowsObserver.mIsProxy)
                                ? accessibilityWindowManager.getDefaultFocus(i2)
                                : i2 == 1
                                        ? accessibilityWindowManager.mTopFocusedWindowId
                                        : i2 == 2
                                                ? displayWindowsObserver
                                                        .mProxyDisplayAccessibilityFocusedWindow
                                                : -1;
            }
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(i, this.mDisplayTypes)) {
                return -1;
            }
        }
        return i;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void sendGesture(int i, ParceledListSlice parceledListSlice) {
        throw new UnsupportedOperationException("sendGesture is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setAnimationScale(float f) {
        throw new UnsupportedOperationException("setAnimationScale is not supported");
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

    @Override // com.android.server.accessibility.AccessibilityServiceConnection,
              // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setFocusAppearance(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    if (this.mFocusStrokeWidth == i && this.mFocusColor == i2) {
                        return;
                    }
                    this.mFocusStrokeWidth = i;
                    this.mFocusColor = i2;
                    AbstractAccessibilityServiceConnection.SystemSupport systemSupport =
                            this.mSystemSupport;
                    ((AccessibilityManagerService) systemSupport)
                            .mProxyManager.onProxyChanged(this.mDeviceId, false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setGestureDetectionPassthroughRegion(int i, Region region) {
        throw new UnsupportedOperationException(
                "setGestureDetectionPassthroughRegion is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final int setInputMethodEnabled(String str, boolean z) {
        throw new UnsupportedOperationException("setInputMethodEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setInstalledAndEnabledServices(List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    this.mInstalledAndEnabledServices = list;
                    AccessibilityServiceInfo accessibilityServiceInfo =
                            this.mAccessibilityServiceInfo;
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
                        AccessibilityServiceInfo accessibilityServiceInfo2 =
                                (AccessibilityServiceInfo) it.next();
                        z |= accessibilityServiceInfo2.isAccessibilityTool();
                        String[] strArr = accessibilityServiceInfo2.packageNames;
                        if (strArr != null && strArr.length != 0) {
                            if (!z2) {
                                hashSet.addAll(Arrays.asList(strArr));
                            }
                            i =
                                    Math.max(
                                            i,
                                            accessibilityServiceInfo2
                                                    .getInteractiveUiTimeoutMillis());
                            i2 =
                                    Math.max(
                                            i2,
                                            accessibilityServiceInfo2
                                                    .getNonInteractiveUiTimeoutMillis());
                            accessibilityServiceInfo.notificationTimeout =
                                    Math.max(
                                            accessibilityServiceInfo.notificationTimeout,
                                            accessibilityServiceInfo2.notificationTimeout);
                            accessibilityServiceInfo.eventTypes |=
                                    accessibilityServiceInfo2.eventTypes;
                            accessibilityServiceInfo.feedbackType |=
                                    accessibilityServiceInfo2.feedbackType;
                            accessibilityServiceInfo.flags |= accessibilityServiceInfo2.flags;
                            setDefaultPropertiesIfNullLocked(accessibilityServiceInfo2);
                            hashSet = hashSet;
                        }
                        z2 = true;
                        i = Math.max(i, accessibilityServiceInfo2.getInteractiveUiTimeoutMillis());
                        i2 =
                                Math.max(
                                        i2,
                                        accessibilityServiceInfo2
                                                .getNonInteractiveUiTimeoutMillis());
                        accessibilityServiceInfo.notificationTimeout =
                                Math.max(
                                        accessibilityServiceInfo.notificationTimeout,
                                        accessibilityServiceInfo2.notificationTimeout);
                        accessibilityServiceInfo.eventTypes |= accessibilityServiceInfo2.eventTypes;
                        accessibilityServiceInfo.feedbackType |=
                                accessibilityServiceInfo2.feedbackType;
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
                        accessibilityServiceInfo.packageNames =
                                (String[]) hashSet2.toArray(new String[0]);
                    }
                    setDynamicallyConfigurableProperties(accessibilityServiceInfo);
                    ((AccessibilityManagerService) this.mSystemSupport)
                            .mProxyManager.onProxyChanged(this.mDeviceId, false);
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setMagnificationCallbackEnabled(int i, boolean z) {
        throw new UnsupportedOperationException("setMagnificationCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean setMagnificationConfig(
            int i, MagnificationConfig magnificationConfig, boolean z) {
        throw new UnsupportedOperationException("setMagnificationConfig is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setOnKeyEventResult(boolean z, int i) {
        throw new UnsupportedOperationException("setOnKeyEventResult is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setServiceDetectsGesturesEnabled(int i, boolean z) {
        throw new UnsupportedOperationException(
                "setServiceDetectsGesturesEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        throw new UnsupportedOperationException("setServiceInfo is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setSoftKeyboardCallbackEnabled(boolean z) {
        throw new UnsupportedOperationException("setSoftKeyboardCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final boolean setSoftKeyboardShowMode(int i) {
        throw new UnsupportedOperationException("setSoftKeyboardShowMode is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setTouchExplorationPassthroughRegion(int i, Region region) {
        throw new UnsupportedOperationException(
                "setTouchExplorationPassthroughRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final boolean supportsFlagForNotImportantViews(
            AccessibilityServiceInfo accessibilityServiceInfo) {
        return true;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public final boolean switchToInputMethod(String str) {
        throw new UnsupportedOperationException("switchToInputMethod is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void takeScreenshot(int i, RemoteCallback remoteCallback) {
        throw new UnsupportedOperationException("takeScreenshot is not supported");
    }
}
