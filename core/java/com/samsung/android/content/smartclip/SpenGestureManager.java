package com.samsung.android.content.smartclip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.InputEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.android.internal.inputmethod.IRemoteInputConnection;

import com.samsung.android.util.SemLog;

import java.io.FileDescriptor;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class SpenGestureManager {
    private static String TAG = "SpenGestureManager";
    private ISpenGestureService mService = null;

    public SpenGestureManager(Context context) {
        getService();
    }

    public synchronized boolean isServiceAvailable() {
        ISpenGestureService service =
                ISpenGestureService.Stub.asInterface(
                        ServiceManager.getService(Context.SEM_SPEN_GESTURE_SERVICE));
        if (service != null) {
            return true;
        }
        SemLog.w(TAG, "isServiceAvailable : Service not available");
        return false;
    }

    private synchronized ISpenGestureService getService() {
        if (this.mService == null) {
            this.mService =
                    ISpenGestureService.Stub.asInterface(
                            ServiceManager.getService(Context.SEM_SPEN_GESTURE_SERVICE));
            if (this.mService == null) {
                SemLog.w("SpenGestureManager", "warning: no SpenGestureManager");
            }
        }
        return this.mService;
    }

    public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult result) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.sendSmartClipRemoteRequestResult(result);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(
            Rect rect, IBinder skipWindowToken, int extractionMode) {
        return getSmartClipDataByScreenRect(rect, skipWindowToken, extractionMode, 0);
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(
            Rect rect, IBinder skipWindowToken, int extractionMode, int windowTargetingType) {
        try {
            ISpenGestureService svc = getService();
            if (svc == null) {
                return null;
            }
            return svc.getSmartClipDataByScreenRect(
                    rect, skipWindowToken, extractionMode, windowTargetingType);
        } catch (RemoteException e) {
            return null;
        } catch (RuntimeException e2) {
            return null;
        }
    }

    public Bundle getScrollableAreaInfo(Rect rect, IBinder skipWindowToken) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getScrollableAreaInfo(rect, skipWindowToken);
            }
            return null;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Bundle getScrollableViewInfo(Rect rect, int viewHash, IBinder skipWindowToken) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getScrollableViewInfo(rect, viewHash, skipWindowToken);
            }
            return null;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void injectInputEvent(
            int targetX,
            int targetY,
            ArrayList<InputEvent> inputEvents,
            boolean waitUntilConsume,
            IBinder skipWindowToken) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                InputEvent[] array = new InputEvent[inputEvents.size()];
                svc.injectInputEvent(
                        targetX,
                        targetY,
                        (InputEvent[]) inputEvents.toArray(array),
                        waitUntilConsume,
                        skipWindowToken);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setHoverStayDetectEnabled(boolean enable) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setHoverStayDetectEnabled(enable);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setHoverStayValues(int x, int y, int hoverTime) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setHoverStayValues(x, y, hoverTime);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerHoverListener(ISpenGestureHoverListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.registerHoverListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterHoverListener(ISpenGestureHoverListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.unregisterHoverListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setSpenPowerSavingModeEnabled(boolean enable) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setSpenPowerSavingModeEnabled(enable);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showTouchPointer(boolean isShow) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.showTouchPointer(isShow);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setScreenOffDoubleTabTime() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setScreenOffDoubleTabTime();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public long getScreenOffDoubleTabTime() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getScreenOffDoubleTabTime();
            }
            return 0L;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setSpenInsertionState(boolean isInserted) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setSpenInsertionState(isInserted);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isSpenInserted() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.isSpenInserted();
            }
            return false;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isSpenReversed() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.isSpenReversed();
            }
            return false;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getBleSpenAddress() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getBleSpenAddress();
            }
            return null;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setBleSpenAddress(String address) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setBleSpenAddress(address);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getBleSpenCmfCode() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getBleSpenCmfCode();
            }
            return null;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setBleSpenCmfCode(String cmfCode) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setBleSpenCmfCode(cmfCode);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isSupportBleSpen() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.isSupportBleSpen();
            }
            return false;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeBleSpenCommand(String command) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.writeBleSpenCommand(command);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setSpenPdctLowSensitivityEnable() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setSpenPdctLowSensitivityEnable();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveBleSpenLogFile(byte[] buffer) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.saveBleSpenLogFile(buffer);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void notifyBleSpenChargeLockState(boolean isLocked) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.notifyBleSpenChargeLockState(isLocked);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerBleSpenChargeLockStateChangedListener(
            IBleSpenChargeLockStateChangedListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.registerBleSpenChargeLockStateChangedListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterBleSpenChargeLockStateChangedListener(
            IBleSpenChargeLockStateChangedListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.unregisterBleSpenChargeLockStateChangedListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getScreenOffReason() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.getScreenOffReason();
            }
            return -1;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setScreenOffReason(int reason) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setScreenOffReason(reason);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setCurrentInputInfo(
            IRemoteInputConnection inputConnection, EditorInfo editorInfo, int missingMethodFlags) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setCurrentInputInfo(inputConnection, editorInfo, missingMethodFlags);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.registerInputMethodInfoChangeListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.unregisterInputMethodInfoChangeListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public InputConnection getCurrentInputConnection() {
        return null;
    }

    public EditorInfo getEditorInfo() {
        try {
            ISpenGestureService svc = getService();
            if (svc == null) {
                return null;
            }
            EditorInfo editorInfo = svc.getCurrentEditorInfo();
            return editorInfo;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void notifyKeyboardClosed() {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.notifyKeyboardClosed();
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerAirGestureListener(IAirGestureListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.registerAirGestureListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterAirGestureListener(IAirGestureListener listener) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.unregisterAirGestureListener(listener);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void notifyAirGesture(String gesture) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.notifyAirGesture(gesture);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Bitmap screenshot(
            int displayId,
            int targetWindowType,
            boolean containsTargetSystemWindow,
            Rect sourceCrop,
            int width,
            int height,
            boolean useIdentityTransform) {
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                return svc.screenshot(
                        displayId,
                        targetWindowType,
                        containsTargetSystemWindow,
                        sourceCrop,
                        width,
                        height,
                        useIdentityTransform);
            }
            return null;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPenHoverIcon(
            Context context, FileDescriptor fd, float hotspotX, float hotspotY) {
        if (context == null || fd == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setPenHoverIcon(context.getPackageName(), fd, hotspotX, hotspotY);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void resetPenHoverIcon(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.resetPenHoverIcon(context.getPackageName());
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPenAttachSound(Context context, FileDescriptor fd) {
        if (context == null || fd == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setPenAttachSound(context.getPackageName(), fd);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void resetPenAttachSound(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.resetPenAttachSound(context.getPackageName());
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPenDetachSound(Context context, FileDescriptor fd) {
        if (context == null || fd == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.setPenDetachSound(context.getPackageName(), fd);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void resetPenDetachSound(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService svc = getService();
            if (svc != null) {
                svc.resetPenDetachSound(context.getPackageName());
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }
}
