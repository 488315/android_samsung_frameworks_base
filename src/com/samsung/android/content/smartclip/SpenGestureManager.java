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
import com.samsung.android.content.smartclip.ISpenGestureService;
import com.samsung.android.util.SemLog;
import java.io.FileDescriptor;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SpenGestureManager {
    private static String TAG = "SpenGestureManager";
    private ISpenGestureService mService = null;

    public InputConnection getCurrentInputConnection() {
        return null;
    }

    public SpenGestureManager(Context context) {
        getService();
    }

    public synchronized boolean isServiceAvailable() {
        if (ISpenGestureService.Stub.asInterface(ServiceManager.getService(Context.SEM_SPEN_GESTURE_SERVICE)) != null) {
            return true;
        }
        SemLog.w(TAG, "isServiceAvailable : Service not available");
        return false;
    }

    private synchronized ISpenGestureService getService() {
        if (this.mService == null) {
            ISpenGestureService iSpenGestureServiceAsInterface = ISpenGestureService.Stub.asInterface(ServiceManager.getService(Context.SEM_SPEN_GESTURE_SERVICE));
            this.mService = iSpenGestureServiceAsInterface;
            if (iSpenGestureServiceAsInterface == null) {
                SemLog.w("SpenGestureManager", "warning: no SpenGestureManager");
            }
        }
        return this.mService;
    }

    public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.sendSmartClipRemoteRequestResult(smartClipRemoteRequestResult);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i) {
        return getSmartClipDataByScreenRect(rect, iBinder, i, 0);
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getSmartClipDataByScreenRect(rect, iBinder, i, i2);
            }
        } catch (RemoteException | RuntimeException unused) {
        }
        return null;
    }

    public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getScrollableAreaInfo(rect, iBinder);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getScrollableViewInfo(rect, i, iBinder);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void injectInputEvent(int i, int i2, ArrayList<InputEvent> arrayList, boolean z, IBinder iBinder) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.injectInputEvent(i, i2, (InputEvent[]) arrayList.toArray(new InputEvent[arrayList.size()]), z, iBinder);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHoverStayDetectEnabled(boolean z) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setHoverStayDetectEnabled(z);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHoverStayValues(int i, int i2, int i3) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setHoverStayValues(i, i2, i3);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.registerHoverListener(iSpenGestureHoverListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.unregisterHoverListener(iSpenGestureHoverListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSpenPowerSavingModeEnabled(boolean z) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setSpenPowerSavingModeEnabled(z);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTouchPointer(boolean z) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.showTouchPointer(z);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScreenOffDoubleTabTime() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setScreenOffDoubleTabTime();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public long getScreenOffDoubleTabTime() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getScreenOffDoubleTabTime();
            }
            return 0L;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSpenInsertionState(boolean z) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setSpenInsertionState(z);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSpenInserted() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.isSpenInserted();
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSpenReversed() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.isSpenReversed();
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBleSpenAddress() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getBleSpenAddress();
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBleSpenAddress(String str) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setBleSpenAddress(str);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBleSpenCmfCode() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getBleSpenCmfCode();
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBleSpenCmfCode(String str) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setBleSpenCmfCode(str);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSupportBleSpen() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.isSupportBleSpen();
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBleSpenCommand(String str) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.writeBleSpenCommand(str);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSpenPdctLowSensitivityEnable() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setSpenPdctLowSensitivityEnable();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBleSpenLogFile(byte[] bArr) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.saveBleSpenLogFile(bArr);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyBleSpenChargeLockState(boolean z) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.notifyBleSpenChargeLockState(z);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.registerBleSpenChargeLockStateChangedListener(iBleSpenChargeLockStateChangedListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.unregisterBleSpenChargeLockStateChangedListener(iBleSpenChargeLockStateChangedListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int getScreenOffReason() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getScreenOffReason();
            }
            return -1;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScreenOffReason(int i) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setScreenOffReason(i);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setCurrentInputInfo(iRemoteInputConnection, editorInfo, i);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.registerInputMethodInfoChangeListener(iInputMethodInfoChangeListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.unregisterInputMethodInfoChangeListener(iInputMethodInfoChangeListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorInfo getEditorInfo() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.getCurrentEditorInfo();
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyKeyboardClosed() {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.notifyKeyboardClosed();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerAirGestureListener(IAirGestureListener iAirGestureListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.registerAirGestureListener(iAirGestureListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.unregisterAirGestureListener(iAirGestureListener);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyAirGesture(String str) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.notifyAirGesture(str);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                return service.screenshot(i, i2, z, rect, i3, i4, z2);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPenHoverIcon(Context context, FileDescriptor fileDescriptor, float f, float f2) {
        if (context == null || fileDescriptor == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setPenHoverIcon(context.getPackageName(), fileDescriptor, f, f2);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetPenHoverIcon(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.resetPenHoverIcon(context.getPackageName());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPenAttachSound(Context context, FileDescriptor fileDescriptor) {
        if (context == null || fileDescriptor == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setPenAttachSound(context.getPackageName(), fileDescriptor);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetPenAttachSound(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.resetPenAttachSound(context.getPackageName());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPenDetachSound(Context context, FileDescriptor fileDescriptor) {
        if (context == null || fileDescriptor == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.setPenDetachSound(context.getPackageName(), fileDescriptor);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetPenDetachSound(Context context) {
        if (context == null) {
            return;
        }
        try {
            ISpenGestureService service = getService();
            if (service != null) {
                service.resetPenDetachSound(context.getPackageName());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
