package android.view.accessibility;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice.MagnificationConfig;
import android.annotation.SystemApi;
import android.content.Context;
import android.graphics.Region;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.RemoteAccessibilityInputConnection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public abstract class AccessibilityDisplayProxy {
    private static final int INVALID_CONNECTION_ID = -1;
    private static final String LOG_TAG = "AccessibilityDisplayProxy";
    private int mConnectionId = -1;
    private int mDisplayId;
    private Executor mExecutor;
    private List<AccessibilityServiceInfo> mInstalledAndEnabledServices;
    IAccessibilityServiceClient mServiceClient;

    public void interrupt() {
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public void onProxyConnected() {
    }

    public AccessibilityDisplayProxy(int i, Executor executor, List<AccessibilityServiceInfo> list) {
        this.mDisplayId = i;
        this.mExecutor = executor;
        this.mServiceClient = new IAccessibilityServiceClientImpl(this, null, this.mExecutor);
        this.mInstalledAndEnabledServices = list;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public AccessibilityNodeInfo findFocus(int i) {
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public List<AccessibilityWindowInfo> getWindows() {
        return AccessibilityInteractionClient.getInstance().getWindowsOnDisplay(this.mConnectionId, this.mDisplayId);
    }

    public void setInstalledAndEnabledServices(List<AccessibilityServiceInfo> list) {
        this.mInstalledAndEnabledServices = list;
        sendServiceInfos();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendServiceInfos() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        List<AccessibilityServiceInfo> list = this.mInstalledAndEnabledServices;
        if (list != null && list.size() > 0 && connection != null) {
            try {
                connection.setInstalledAndEnabledServices(this.mInstalledAndEnabledServices);
                AccessibilityInteractionClient.getInstance().clearCache(this.mConnectionId);
            } catch (RemoteException e) {
                Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfos", e);
                e.rethrowFromSystemServer();
            }
        }
        this.mInstalledAndEnabledServices = null;
    }

    public final List<AccessibilityServiceInfo> getInstalledAndEnabledServices() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.getInstalledAndEnabledServices();
            } catch (RemoteException e) {
                Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", e);
                e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public void setAccessibilityFocusAppearance(int i, int i2) {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setFocusAppearance(i, i2);
            } catch (RemoteException e) {
                Log.w(LOG_TAG, "Error while setting the strokeWidth and color of the accessibility focus rectangle", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    private class IAccessibilityServiceClientImpl extends AccessibilityService.IAccessibilityServiceClientWrapper {
        IAccessibilityServiceClientImpl(final AccessibilityDisplayProxy accessibilityDisplayProxy, Context context, Executor executor) {
            super(context, executor, new AccessibilityService.Callbacks() { // from class: android.view.accessibility.AccessibilityDisplayProxy.IAccessibilityServiceClientImpl.1
                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonClicked(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintCapturingGesturesChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintGesture(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onGesture(AccessibilityGestureEvent accessibilityGestureEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onKeyEvent(KeyEvent keyEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMotionEvent(MotionEvent motionEvent) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onPerformGestureResult(int i, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSoftKeyboardShowModeChanged(int i) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSystemActionsChanged() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onTouchStateChanged(int i, int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void startInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                    AccessibilityDisplayProxy.this.onAccessibilityEvent(accessibilityEvent);
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onInterrupt() {
                    AccessibilityDisplayProxy.this.interrupt();
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onServiceConnected() {
                    AccessibilityDisplayProxy.this.sendServiceInfos();
                    AccessibilityDisplayProxy.this.onProxyConnected();
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void init(int i, IBinder iBinder) {
                    AccessibilityDisplayProxy.this.mConnectionId = i;
                }
            });
        }
    }
}
