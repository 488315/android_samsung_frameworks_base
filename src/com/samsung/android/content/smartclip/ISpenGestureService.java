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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpenGestureService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpenGestureService)) {
                return (ISpenGestureService) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemSmartClipDataRepository smartClipDataByScreenRect = getSmartClipDataByScreenRect(rect, readStrongBinder, readInt, readInt2);
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
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    InputEvent[] inputEventArr = (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    injectInputEvent(readInt3, readInt4, inputEventArr, readBoolean, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Bundle scrollableAreaInfo = getScrollableAreaInfo(rect2, readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(scrollableAreaInfo, 1);
                    return true;
                case 5:
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt5 = parcel.readInt();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Bundle scrollableViewInfo = getScrollableViewInfo(rect3, readInt5, readStrongBinder4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(scrollableViewInfo, 1);
                    return true;
                case 6:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHoverStayDetectEnabled(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHoverStayValues(readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ISpenGestureHoverListener asInterface = ISpenGestureHoverListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHoverListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ISpenGestureHoverListener asInterface2 = ISpenGestureHoverListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterHoverListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpenPowerSavingModeEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showTouchPointer(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpenInsertionState(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean isSpenInserted = isSpenInserted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpenInserted);
                    return true;
                case 14:
                    boolean isSpenReversed = isSpenReversed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpenReversed);
                    return true;
                case 15:
                    boolean isSupportBleSpen = isSupportBleSpen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportBleSpen);
                    return true;
                case 16:
                    String bleSpenAddress = getBleSpenAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(bleSpenAddress);
                    return true;
                case 17:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBleSpenAddress(readString);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String bleSpenCmfCode = getBleSpenCmfCode();
                    parcel2.writeNoException();
                    parcel2.writeString(bleSpenCmfCode);
                    return true;
                case 19:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBleSpenCmfCode(readString2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    writeBleSpenCommand(readString3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    setSpenPdctLowSensitivityEnable();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    saveBleSpenLogFile(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyBleSpenChargeLockState(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBleSpenChargeLockStateChangedListener asInterface3 = IBleSpenChargeLockStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBleSpenChargeLockStateChangedListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBleSpenChargeLockStateChangedListener asInterface4 = IBleSpenChargeLockStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBleSpenChargeLockStateChangedListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IAirGestureListener asInterface5 = IAirGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAirGestureListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IAirGestureListener asInterface6 = IAirGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAirGestureListener(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyAirGesture(readString4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int screenOffReason = getScreenOffReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenOffReason);
                    return true;
                case 30:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenOffReason(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IInputMethodInfoChangeListener asInterface7 = IInputMethodInfoChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerInputMethodInfoChangeListener(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IInputMethodInfoChangeListener asInterface8 = IInputMethodInfoChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterInputMethodInfoChangeListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IRemoteInputConnection asInterface9 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentInputInfo(asInterface9, editorInfo, readInt10);
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
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Bitmap screenshot = screenshot(readInt11, readInt12, readBoolean7, rect4, readInt13, readInt14, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshot, 1);
                    return true;
                case 39:
                    String readString5 = parcel.readString();
                    FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setPenHoverIcon(readString5, readRawFileDescriptor, readFloat, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenHoverIcon(readString6);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString7 = parcel.readString();
                    FileDescriptor readRawFileDescriptor2 = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setPenAttachSound(readString7, readRawFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenAttachSound(readString8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String readString9 = parcel.readString();
                    FileDescriptor readRawFileDescriptor3 = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setPenDetachSound(readString9, readRawFileDescriptor3);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPenDetachSound(readString10);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemSmartClipDataRepository) obtain2.readTypedObject(SemSmartClipDataRepository.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeTypedObject(smartClipRemoteRequestResult, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(inputEventArr, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayDetectEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayValues(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
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

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpenGestureHoverListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpenGestureHoverListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPowerSavingModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void showTouchPointer(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenInsertionState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenInserted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenReversed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSupportBleSpen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenAddress(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenCmfCode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenCmfCode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void writeBleSpenCommand(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPdctLowSensitivityEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void saveBleSpenLogFile(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyBleSpenChargeLockState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBleSpenChargeLockStateChangedListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBleSpenChargeLockStateChangedListener);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAirGestureListener);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAirGestureListener);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyAirGesture(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getScreenOffReason() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffReason(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodInfoChangeListener);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodInfoChangeListener);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public EditorInfo getCurrentEditorInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EditorInfo) obtain2.readTypedObject(EditorInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public IRemoteInputConnection getCurrentInputContext() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRemoteInputConnection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getCurrentMissingMethodFlags() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyKeyboardClosed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenHoverIcon(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenAttachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenAttachSound(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenDetachSound(String str, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenDetachSound(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffDoubleTabTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public long getScreenOffDoubleTabTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
