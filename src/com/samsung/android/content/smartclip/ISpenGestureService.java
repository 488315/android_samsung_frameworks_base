package com.samsung.android.content.smartclip;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputEvent;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.samsung.android.content.smartclip.IAirGestureListener;
import com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener;
import com.samsung.android.content.smartclip.IInputMethodInfoChangeListener;
import com.samsung.android.content.smartclip.ISpenGestureHoverListener;
import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public interface ISpenGestureService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.ISpenGestureService";

    public static class Default implements ISpenGestureService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public String getBleSpenAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public String getBleSpenCmfCode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public EditorInfo getCurrentEditorInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public IRemoteInputConnection getCurrentInputContext() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public int getCurrentMissingMethodFlags() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public long getScreenOffDoubleTabTime() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public int getScreenOffReason() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public boolean isSpenInserted() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public boolean isSpenReversed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public boolean isSupportBleSpen() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void notifyAirGesture(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void notifyBleSpenChargeLockState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void notifyKeyboardClosed() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenAttachSound(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenDetachSound(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenHoverIcon(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void saveBleSpenLogFile(byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setBleSpenAddress(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setBleSpenCmfCode(String str) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setHoverStayDetectEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setHoverStayValues(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenAttachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenDetachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setScreenOffDoubleTabTime() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setScreenOffReason(int i) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenInsertionState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenPdctLowSensitivityEnable() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenPowerSavingModeEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void showTouchPointer(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void writeBleSpenCommand(String str) throws RemoteException {
        }
    }

    String getBleSpenAddress() throws RemoteException;

    String getBleSpenCmfCode() throws RemoteException;

    EditorInfo getCurrentEditorInfo() throws RemoteException;

    IRemoteInputConnection getCurrentInputContext() throws RemoteException;

    int getCurrentMissingMethodFlags() throws RemoteException;

    long getScreenOffDoubleTabTime() throws RemoteException;

    int getScreenOffReason() throws RemoteException;

    Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) throws RemoteException;

    Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) throws RemoteException;

    SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) throws RemoteException;

    void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) throws RemoteException;

    boolean isSpenInserted() throws RemoteException;

    boolean isSpenReversed() throws RemoteException;

    boolean isSupportBleSpen() throws RemoteException;

    void notifyAirGesture(String str) throws RemoteException;

    void notifyBleSpenChargeLockState(boolean z) throws RemoteException;

    void notifyKeyboardClosed() throws RemoteException;

    void registerAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException;

    void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException;

    void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException;

    void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException;

    void resetPenAttachSound(String str) throws RemoteException;

    void resetPenDetachSound(String str) throws RemoteException;

    void resetPenHoverIcon(String str) throws RemoteException;

    void saveBleSpenLogFile(byte[] bArr) throws RemoteException;

    Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) throws RemoteException;

    void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) throws RemoteException;

    void setBleSpenAddress(String str) throws RemoteException;

    void setBleSpenCmfCode(String str) throws RemoteException;

    void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) throws RemoteException;

    void setHoverStayDetectEnabled(boolean z) throws RemoteException;

    void setHoverStayValues(int i, int i2, int i3) throws RemoteException;

    void setPenAttachSound(String str, FileDescriptor fileDescriptor) throws RemoteException;

    void setPenDetachSound(String str, FileDescriptor fileDescriptor) throws RemoteException;

    void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) throws RemoteException;

    void setScreenOffDoubleTabTime() throws RemoteException;

    void setScreenOffReason(int i) throws RemoteException;

    void setSpenInsertionState(boolean z) throws RemoteException;

    void setSpenPdctLowSensitivityEnable() throws RemoteException;

    void setSpenPowerSavingModeEnabled(boolean z) throws RemoteException;

    void showTouchPointer(boolean z) throws RemoteException;

    void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException;

    void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException;

    void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException;

    void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException;

    void writeBleSpenCommand(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpenGestureService {
        static final int TRANSACTION_getBleSpenAddress = 16;
        static final int TRANSACTION_getBleSpenCmfCode = 18;
        static final int TRANSACTION_getCurrentEditorInfo = 34;
        static final int TRANSACTION_getCurrentInputContext = 35;
        static final int TRANSACTION_getCurrentMissingMethodFlags = 36;
        static final int TRANSACTION_getScreenOffDoubleTabTime = 46;
        static final int TRANSACTION_getScreenOffReason = 29;
        static final int TRANSACTION_getScrollableAreaInfo = 4;
        static final int TRANSACTION_getScrollableViewInfo = 5;
        static final int TRANSACTION_getSmartClipDataByScreenRect = 1;
        static final int TRANSACTION_injectInputEvent = 3;
        static final int TRANSACTION_isSpenInserted = 13;
        static final int TRANSACTION_isSpenReversed = 14;
        static final int TRANSACTION_isSupportBleSpen = 15;
        static final int TRANSACTION_notifyAirGesture = 28;
        static final int TRANSACTION_notifyBleSpenChargeLockState = 23;
        static final int TRANSACTION_notifyKeyboardClosed = 37;
        static final int TRANSACTION_registerAirGestureListener = 26;
        static final int TRANSACTION_registerBleSpenChargeLockStateChangedListener = 24;
        static final int TRANSACTION_registerHoverListener = 8;
        static final int TRANSACTION_registerInputMethodInfoChangeListener = 31;
        static final int TRANSACTION_resetPenAttachSound = 42;
        static final int TRANSACTION_resetPenDetachSound = 44;
        static final int TRANSACTION_resetPenHoverIcon = 40;
        static final int TRANSACTION_saveBleSpenLogFile = 22;
        static final int TRANSACTION_screenshot = 38;
        static final int TRANSACTION_sendSmartClipRemoteRequestResult = 2;
        static final int TRANSACTION_setBleSpenAddress = 17;
        static final int TRANSACTION_setBleSpenCmfCode = 19;
        static final int TRANSACTION_setCurrentInputInfo = 33;
        static final int TRANSACTION_setHoverStayDetectEnabled = 6;
        static final int TRANSACTION_setHoverStayValues = 7;
        static final int TRANSACTION_setPenAttachSound = 41;
        static final int TRANSACTION_setPenDetachSound = 43;
        static final int TRANSACTION_setPenHoverIcon = 39;
        static final int TRANSACTION_setScreenOffDoubleTabTime = 45;
        static final int TRANSACTION_setScreenOffReason = 30;
        static final int TRANSACTION_setSpenInsertionState = 12;
        static final int TRANSACTION_setSpenPdctLowSensitivityEnable = 21;
        static final int TRANSACTION_setSpenPowerSavingModeEnabled = 10;
        static final int TRANSACTION_showTouchPointer = 11;
        static final int TRANSACTION_unregisterAirGestureListener = 27;
        static final int TRANSACTION_unregisterBleSpenChargeLockStateChangedListener = 25;
        static final int TRANSACTION_unregisterHoverListener = 9;
        static final int TRANSACTION_unregisterInputMethodInfoChangeListener = 32;
        static final int TRANSACTION_writeBleSpenCommand = 20;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }

        public Stub() {
            attachInterface(this, ISpenGestureService.DESCRIPTOR);
        }

        public static ISpenGestureService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpenGestureService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpenGestureService)) {
                return (ISpenGestureService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSmartClipDataByScreenRect";
                case 2:
                    return "sendSmartClipRemoteRequestResult";
                case 3:
                    return "injectInputEvent";
                case 4:
                    return "getScrollableAreaInfo";
                case 5:
                    return "getScrollableViewInfo";
                case 6:
                    return "setHoverStayDetectEnabled";
                case 7:
                    return "setHoverStayValues";
                case 8:
                    return "registerHoverListener";
                case 9:
                    return "unregisterHoverListener";
                case 10:
                    return "setSpenPowerSavingModeEnabled";
                case 11:
                    return "showTouchPointer";
                case 12:
                    return "setSpenInsertionState";
                case 13:
                    return "isSpenInserted";
                case 14:
                    return "isSpenReversed";
                case 15:
                    return "isSupportBleSpen";
                case 16:
                    return "getBleSpenAddress";
                case 17:
                    return "setBleSpenAddress";
                case 18:
                    return "getBleSpenCmfCode";
                case 19:
                    return "setBleSpenCmfCode";
                case 20:
                    return "writeBleSpenCommand";
                case 21:
                    return "setSpenPdctLowSensitivityEnable";
                case 22:
                    return "saveBleSpenLogFile";
                case 23:
                    return "notifyBleSpenChargeLockState";
                case 24:
                    return "registerBleSpenChargeLockStateChangedListener";
                case 25:
                    return "unregisterBleSpenChargeLockStateChangedListener";
                case 26:
                    return "registerAirGestureListener";
                case 27:
                    return "unregisterAirGestureListener";
                case 28:
                    return "notifyAirGesture";
                case 29:
                    return "getScreenOffReason";
                case 30:
                    return "setScreenOffReason";
                case 31:
                    return "registerInputMethodInfoChangeListener";
                case 32:
                    return "unregisterInputMethodInfoChangeListener";
                case 33:
                    return "setCurrentInputInfo";
                case 34:
                    return "getCurrentEditorInfo";
                case 35:
                    return "getCurrentInputContext";
                case 36:
                    return "getCurrentMissingMethodFlags";
                case 37:
                    return "notifyKeyboardClosed";
                case 38:
                    return "screenshot";
                case 39:
                    return "setPenHoverIcon";
                case 40:
                    return "resetPenHoverIcon";
                case 41:
                    return "setPenAttachSound";
                case 42:
                    return "resetPenAttachSound";
                case 43:
                    return "setPenDetachSound";
                case 44:
                    return "resetPenDetachSound";
                case 45:
                    return "setScreenOffDoubleTabTime";
                case 46:
                    return "getScreenOffDoubleTabTime";
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
                parcel.enforceInterface(ISpenGestureService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpenGestureService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemSmartClipDataRepository smartClipDataByScreenRect = getSmartClipDataByScreenRect(rect, strongBinder, i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(smartClipDataByScreenRect, 1);
                    return true;
                case 2:
                    SmartClipRemoteRequestResult smartClipRemoteRequestResult = (SmartClipRemoteRequestResult) parcel.readTypedObject(SmartClipRemoteRequestResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmartClipRemoteRequestResult(smartClipRemoteRequestResult);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    InputEvent[] inputEventArr = (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR);
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    injectInputEvent(i5, i6, inputEventArr, z, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Bundle scrollableAreaInfo = getScrollableAreaInfo(rect2, strongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(scrollableAreaInfo, 1);
                    return true;
                case 5:
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i7 = parcel.readInt();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Bundle scrollableViewInfo = getScrollableViewInfo(rect3, i7, strongBinder4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(scrollableViewInfo, 1);
                    return true;
                case 6:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHoverStayDetectEnabled(z2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHoverStayValues(i8, i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ISpenGestureHoverListener iSpenGestureHoverListenerAsInterface = ISpenGestureHoverListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHoverListener(iSpenGestureHoverListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ISpenGestureHoverListener iSpenGestureHoverListenerAsInterface2 = ISpenGestureHoverListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterHoverListener(iSpenGestureHoverListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpenPowerSavingModeEnabled(z3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showTouchPointer(z4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpenInsertionState(z5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean zIsSpenInserted = isSpenInserted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpenInserted);
                    return true;
                case 14:
                    boolean zIsSpenReversed = isSpenReversed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpenReversed);
                    return true;
                case 15:
                    boolean zIsSupportBleSpen = isSupportBleSpen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportBleSpen);
                    return true;
                case 16:
                    String bleSpenAddress = getBleSpenAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(bleSpenAddress);
                    return true;
                case 17:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBleSpenAddress(string);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String bleSpenCmfCode = getBleSpenCmfCode();
                    parcel2.writeNoException();
                    parcel2.writeString(bleSpenCmfCode);
                    return true;
                case 19:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBleSpenCmfCode(string2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    writeBleSpenCommand(string3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    setSpenPdctLowSensitivityEnable();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    saveBleSpenLogFile(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyBleSpenChargeLockState(z6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListenerAsInterface = IBleSpenChargeLockStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBleSpenChargeLockStateChangedListener(iBleSpenChargeLockStateChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListenerAsInterface2 = IBleSpenChargeLockStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBleSpenChargeLockStateChangedListener(iBleSpenChargeLockStateChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IAirGestureListener iAirGestureListenerAsInterface = IAirGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAirGestureListener(iAirGestureListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IAirGestureListener iAirGestureListenerAsInterface2 = IAirGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAirGestureListener(iAirGestureListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyAirGesture(string4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int screenOffReason = getScreenOffReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenOffReason);
                    return true;
                case 30:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenOffReason(i11);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IInputMethodInfoChangeListener iInputMethodInfoChangeListenerAsInterface = IInputMethodInfoChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerInputMethodInfoChangeListener(iInputMethodInfoChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IInputMethodInfoChangeListener iInputMethodInfoChangeListenerAsInterface2 = IInputMethodInfoChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterInputMethodInfoChangeListener(iInputMethodInfoChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IRemoteInputConnection iRemoteInputConnectionAsInterface = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentInputInfo(iRemoteInputConnectionAsInterface, editorInfo, i12);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    EditorInfo currentEditorInfo = getCurrentEditorInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentEditorInfo, 1);
                    return true;
                case 35:
                    IRemoteInputConnection currentInputContext = getCurrentInputContext();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(currentInputContext);
                    return true;
                case 36:
                    int currentMissingMethodFlags = getCurrentMissingMethodFlags();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentMissingMethodFlags);
                    return true;
                case 37:
                    notifyKeyboardClosed();
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Bitmap bitmapScreenshot = screenshot(i13, i14, z7, rect4, i15, i16, z8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapScreenshot, 1);
                    return true;
                case 39:
                    String string5 = parcel.readString();
                    FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setPenHoverIcon(string5, rawFileDescriptor, f, f2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenHoverIcon(string6);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String string7 = parcel.readString();
                    FileDescriptor rawFileDescriptor2 = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setPenAttachSound(string7, rawFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenAttachSound(string8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String string9 = parcel.readString();
                    FileDescriptor rawFileDescriptor3 = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setPenDetachSound(string9, rawFileDescriptor3);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenDetachSound(string10);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    setScreenOffDoubleTabTime();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    long screenOffDoubleTabTime = getScreenOffDoubleTabTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(screenOffDoubleTabTime);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISpenGestureService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpenGestureService.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemSmartClipDataRepository) parcelObtain2.readTypedObject(SemSmartClipDataRepository.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartClipRemoteRequestResult, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(inputEventArr, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayDetectEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayValues(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
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

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpenGestureHoverListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpenGestureHoverListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPowerSavingModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void showTouchPointer(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenInsertionState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenInserted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenReversed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSupportBleSpen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenAddress(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenCmfCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenCmfCode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void writeBleSpenCommand(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPdctLowSensitivityEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void saveBleSpenLogFile(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyBleSpenChargeLockState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBleSpenChargeLockStateChangedListener);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBleSpenChargeLockStateChangedListener);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAirGestureListener);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAirGestureListener);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyAirGesture(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getScreenOffReason() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffReason(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodInfoChangeListener);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodInfoChangeListener);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeTypedObject(editorInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public EditorInfo getCurrentEditorInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EditorInfo) parcelObtain2.readTypedObject(EditorInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public IRemoteInputConnection getCurrentInputContext() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRemoteInputConnection.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getCurrentMissingMethodFlags() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyKeyboardClosed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenHoverIcon(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenAttachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenAttachSound(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenDetachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenDetachSound(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffDoubleTabTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public long getScreenOffDoubleTabTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
