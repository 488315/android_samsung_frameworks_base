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

import java.io.FileDescriptor;

public interface ISpenGestureService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.content.smartclip.ISpenGestureService";

    String getBleSpenAddress() throws RemoteException;

    String getBleSpenCmfCode() throws RemoteException;

    EditorInfo getCurrentEditorInfo() throws RemoteException;

    IRemoteInputConnection getCurrentInputContext() throws RemoteException;

    int getCurrentMissingMethodFlags() throws RemoteException;

    long getScreenOffDoubleTabTime() throws RemoteException;

    int getScreenOffReason() throws RemoteException;

    Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) throws RemoteException;

    Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) throws RemoteException;

    SemSmartClipDataRepository getSmartClipDataByScreenRect(
            Rect rect, IBinder iBinder, int i, int i2) throws RemoteException;

    void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder)
            throws RemoteException;

    boolean isSpenInserted() throws RemoteException;

    boolean isSpenReversed() throws RemoteException;

    boolean isSupportBleSpen() throws RemoteException;

    void notifyAirGesture(String str) throws RemoteException;

    void notifyBleSpenChargeLockState(boolean z) throws RemoteException;

    void notifyKeyboardClosed() throws RemoteException;

    void registerAirGestureListener(IAirGestureListener iAirGestureListener) throws RemoteException;

    void registerBleSpenChargeLockStateChangedListener(
            IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener)
            throws RemoteException;

    void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener)
            throws RemoteException;

    void registerInputMethodInfoChangeListener(
            IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException;

    void resetPenAttachSound(String str) throws RemoteException;

    void resetPenDetachSound(String str) throws RemoteException;

    void resetPenHoverIcon(String str) throws RemoteException;

    void saveBleSpenLogFile(byte[] bArr) throws RemoteException;

    Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2)
            throws RemoteException;

    void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult)
            throws RemoteException;

    void setBleSpenAddress(String str) throws RemoteException;

    void setBleSpenCmfCode(String str) throws RemoteException;

    void setCurrentInputInfo(
            IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i)
            throws RemoteException;

    void setHoverStayDetectEnabled(boolean z) throws RemoteException;

    void setHoverStayValues(int i, int i2, int i3) throws RemoteException;

    void setPenAttachSound(String str, FileDescriptor fileDescriptor) throws RemoteException;

    void setPenDetachSound(String str, FileDescriptor fileDescriptor) throws RemoteException;

    void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2)
            throws RemoteException;

    void setScreenOffDoubleTabTime() throws RemoteException;

    void setScreenOffReason(int i) throws RemoteException;

    void setSpenInsertionState(boolean z) throws RemoteException;

    void setSpenPdctLowSensitivityEnable() throws RemoteException;

    void setSpenPowerSavingModeEnabled(boolean z) throws RemoteException;

    void showTouchPointer(boolean z) throws RemoteException;

    void unregisterAirGestureListener(IAirGestureListener iAirGestureListener)
            throws RemoteException;

    void unregisterBleSpenChargeLockStateChangedListener(
            IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener)
            throws RemoteException;

    void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener)
            throws RemoteException;

    void unregisterInputMethodInfoChangeListener(
            IInputMethodInfoChangeListener iInputMethodInfoChangeListener) throws RemoteException;

    void writeBleSpenCommand(String str) throws RemoteException;

    public static class Default implements ISpenGestureService {
        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public SemSmartClipDataRepository getSmartClipDataByScreenRect(
                Rect rect, IBinder skipWindowToken, int extractionMode, int windowTargetingType)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult result)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void injectInputEvent(
                int targetX,
                int targetY,
                InputEvent[] inputEvents,
                boolean waitUntilConsume,
                IBinder skipWindowToken)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bundle getScrollableAreaInfo(Rect rect, IBinder skipWindowToken)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bundle getScrollableViewInfo(Rect rect, int viewHash, IBinder skipWindowToken)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setHoverStayDetectEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setHoverStayValues(int x, int y, int hoverTime) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerHoverListener(ISpenGestureHoverListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterHoverListener(ISpenGestureHoverListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenPowerSavingModeEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void showTouchPointer(boolean isShow) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenInsertionState(boolean isInserted) throws RemoteException {}

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
        public String getBleSpenAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setBleSpenAddress(String address) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public String getBleSpenCmfCode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setBleSpenCmfCode(String cmfCode) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void writeBleSpenCommand(String command) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setSpenPdctLowSensitivityEnable() throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void saveBleSpenLogFile(byte[] buffer) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void notifyBleSpenChargeLockState(boolean isLocked) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerBleSpenChargeLockStateChangedListener(
                IBleSpenChargeLockStateChangedListener listner) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterBleSpenChargeLockStateChangedListener(
                IBleSpenChargeLockStateChangedListener listner) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerAirGestureListener(IAirGestureListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterAirGestureListener(IAirGestureListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void notifyAirGesture(String gesture) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public int getScreenOffReason() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setScreenOffReason(int reason) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setCurrentInputInfo(
                IRemoteInputConnection inputConnection,
                EditorInfo editorInfo,
                int missingMethodFlags)
                throws RemoteException {}

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
        public void notifyKeyboardClosed() throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public Bitmap screenshot(
                int displayId,
                int targetWindowType,
                boolean containsTargetSystemWindow,
                Rect sourceCrop,
                int width,
                int height,
                boolean useIdentityTransform)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenHoverIcon(
                String pkgName, FileDescriptor fd, float hotspotX, float hotspotY)
                throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenHoverIcon(String pkgName) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenAttachSound(String pkgName, FileDescriptor fd) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenAttachSound(String pkgName) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setPenDetachSound(String pkgName, FileDescriptor fd) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void resetPenDetachSound(String pkgName) throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public void setScreenOffDoubleTabTime() throws RemoteException {}

        @Override // com.samsung.android.content.smartclip.ISpenGestureService
        public long getScreenOffDoubleTabTime() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISpenGestureService {
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

        public Stub() {
            attachInterface(this, ISpenGestureService.DESCRIPTOR);
        }

        public static ISpenGestureService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpenGestureService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpenGestureService)) {
                return (ISpenGestureService) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISpenGestureService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISpenGestureService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Rect _arg0 = (Rect) data.readTypedObject(Rect.CREATOR);
                    IBinder _arg1 = data.readStrongBinder();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    SemSmartClipDataRepository _result =
                            getSmartClipDataByScreenRect(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    SmartClipRemoteRequestResult _arg02 =
                            (SmartClipRemoteRequestResult)
                                    data.readTypedObject(SmartClipRemoteRequestResult.CREATOR);
                    data.enforceNoDataAvail();
                    sendSmartClipRemoteRequestResult(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    InputEvent[] _arg22 = (InputEvent[]) data.createTypedArray(InputEvent.CREATOR);
                    boolean _arg32 = data.readBoolean();
                    IBinder _arg4 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    injectInputEvent(_arg03, _arg12, _arg22, _arg32, _arg4);
                    reply.writeNoException();
                    return true;
                case 4:
                    Rect _arg04 = (Rect) data.readTypedObject(Rect.CREATOR);
                    IBinder _arg13 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    Bundle _result2 = getScrollableAreaInfo(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 5:
                    Rect _arg05 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg14 = data.readInt();
                    IBinder _arg23 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    Bundle _result3 = getScrollableViewInfo(_arg05, _arg14, _arg23);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 6:
                    boolean _arg06 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHoverStayDetectEnabled(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg15 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    setHoverStayValues(_arg07, _arg15, _arg24);
                    reply.writeNoException();
                    return true;
                case 8:
                    ISpenGestureHoverListener _arg08 =
                            ISpenGestureHoverListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerHoverListener(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    ISpenGestureHoverListener _arg09 =
                            ISpenGestureHoverListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterHoverListener(_arg09);
                    reply.writeNoException();
                    return true;
                case 10:
                    boolean _arg010 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSpenPowerSavingModeEnabled(_arg010);
                    reply.writeNoException();
                    return true;
                case 11:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    showTouchPointer(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    boolean _arg012 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSpenInsertionState(_arg012);
                    reply.writeNoException();
                    return true;
                case 13:
                    boolean _result4 = isSpenInserted();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 14:
                    boolean _result5 = isSpenReversed();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 15:
                    boolean _result6 = isSupportBleSpen();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 16:
                    String _result7 = getBleSpenAddress();
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 17:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    setBleSpenAddress(_arg013);
                    reply.writeNoException();
                    return true;
                case 18:
                    String _result8 = getBleSpenCmfCode();
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 19:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    setBleSpenCmfCode(_arg014);
                    reply.writeNoException();
                    return true;
                case 20:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    writeBleSpenCommand(_arg015);
                    reply.writeNoException();
                    return true;
                case 21:
                    setSpenPdctLowSensitivityEnable();
                    reply.writeNoException();
                    return true;
                case 22:
                    byte[] _arg016 = data.createByteArray();
                    data.enforceNoDataAvail();
                    saveBleSpenLogFile(_arg016);
                    reply.writeNoException();
                    return true;
                case 23:
                    boolean _arg017 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyBleSpenChargeLockState(_arg017);
                    reply.writeNoException();
                    return true;
                case 24:
                    IBleSpenChargeLockStateChangedListener _arg018 =
                            IBleSpenChargeLockStateChangedListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerBleSpenChargeLockStateChangedListener(_arg018);
                    reply.writeNoException();
                    return true;
                case 25:
                    IBleSpenChargeLockStateChangedListener _arg019 =
                            IBleSpenChargeLockStateChangedListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterBleSpenChargeLockStateChangedListener(_arg019);
                    reply.writeNoException();
                    return true;
                case 26:
                    IAirGestureListener _arg020 =
                            IAirGestureListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerAirGestureListener(_arg020);
                    reply.writeNoException();
                    return true;
                case 27:
                    IAirGestureListener _arg021 =
                            IAirGestureListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAirGestureListener(_arg021);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    notifyAirGesture(_arg022);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _result9 = getScreenOffReason();
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 30:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    setScreenOffReason(_arg023);
                    reply.writeNoException();
                    return true;
                case 31:
                    IInputMethodInfoChangeListener _arg024 =
                            IInputMethodInfoChangeListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerInputMethodInfoChangeListener(_arg024);
                    reply.writeNoException();
                    return true;
                case 32:
                    IInputMethodInfoChangeListener _arg025 =
                            IInputMethodInfoChangeListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterInputMethodInfoChangeListener(_arg025);
                    reply.writeNoException();
                    return true;
                case 33:
                    IRemoteInputConnection _arg026 =
                            IRemoteInputConnection.Stub.asInterface(data.readStrongBinder());
                    EditorInfo _arg16 = (EditorInfo) data.readTypedObject(EditorInfo.CREATOR);
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    setCurrentInputInfo(_arg026, _arg16, _arg25);
                    reply.writeNoException();
                    return true;
                case 34:
                    EditorInfo _result10 = getCurrentEditorInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 35:
                    IRemoteInputConnection _result11 = getCurrentInputContext();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result11);
                    return true;
                case 36:
                    int _result12 = getCurrentMissingMethodFlags();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 37:
                    notifyKeyboardClosed();
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg027 = data.readInt();
                    int _arg17 = data.readInt();
                    boolean _arg26 = data.readBoolean();
                    Rect _arg33 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg42 = data.readInt();
                    int _arg5 = data.readInt();
                    boolean _arg6 = data.readBoolean();
                    data.enforceNoDataAvail();
                    Bitmap _result13 =
                            screenshot(_arg027, _arg17, _arg26, _arg33, _arg42, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 39:
                    String _arg028 = data.readString();
                    FileDescriptor _arg18 = data.readRawFileDescriptor();
                    float _arg27 = data.readFloat();
                    float _arg34 = data.readFloat();
                    data.enforceNoDataAvail();
                    setPenHoverIcon(_arg028, _arg18, _arg27, _arg34);
                    reply.writeNoException();
                    return true;
                case 40:
                    String _arg029 = data.readString();
                    data.enforceNoDataAvail();
                    resetPenHoverIcon(_arg029);
                    reply.writeNoException();
                    return true;
                case 41:
                    String _arg030 = data.readString();
                    FileDescriptor _arg19 = data.readRawFileDescriptor();
                    data.enforceNoDataAvail();
                    setPenAttachSound(_arg030, _arg19);
                    reply.writeNoException();
                    return true;
                case 42:
                    String _arg031 = data.readString();
                    data.enforceNoDataAvail();
                    resetPenAttachSound(_arg031);
                    reply.writeNoException();
                    return true;
                case 43:
                    String _arg032 = data.readString();
                    FileDescriptor _arg110 = data.readRawFileDescriptor();
                    data.enforceNoDataAvail();
                    setPenDetachSound(_arg032, _arg110);
                    reply.writeNoException();
                    return true;
                case 44:
                    String _arg033 = data.readString();
                    data.enforceNoDataAvail();
                    resetPenDetachSound(_arg033);
                    reply.writeNoException();
                    return true;
                case 45:
                    setScreenOffDoubleTabTime();
                    reply.writeNoException();
                    return true;
                case 46:
                    long _result14 = getScreenOffDoubleTabTime();
                    reply.writeNoException();
                    reply.writeLong(_result14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpenGestureService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpenGestureService.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public SemSmartClipDataRepository getSmartClipDataByScreenRect(
                    Rect rect, IBinder skipWindowToken, int extractionMode, int windowTargetingType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeTypedObject(rect, 0);
                    _data.writeStrongBinder(skipWindowToken);
                    _data.writeInt(extractionMode);
                    _data.writeInt(windowTargetingType);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    SemSmartClipDataRepository _result =
                            (SemSmartClipDataRepository)
                                    _reply.readTypedObject(SemSmartClipDataRepository.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void injectInputEvent(
                    int targetX,
                    int targetY,
                    InputEvent[] inputEvents,
                    boolean waitUntilConsume,
                    IBinder skipWindowToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeInt(targetX);
                    _data.writeInt(targetY);
                    _data.writeTypedArray(inputEvents, 0);
                    _data.writeBoolean(waitUntilConsume);
                    _data.writeStrongBinder(skipWindowToken);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableAreaInfo(Rect rect, IBinder skipWindowToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeTypedObject(rect, 0);
                    _data.writeStrongBinder(skipWindowToken);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bundle getScrollableViewInfo(Rect rect, int viewHash, IBinder skipWindowToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeTypedObject(rect, 0);
                    _data.writeInt(viewHash);
                    _data.writeStrongBinder(skipWindowToken);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayDetectEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setHoverStayValues(int x, int y, int hoverTime) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(hoverTime);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerHoverListener(ISpenGestureHoverListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterHoverListener(ISpenGestureHoverListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPowerSavingModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void showTouchPointer(boolean isShow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeBoolean(isShow);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenInsertionState(boolean isInserted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeBoolean(isInserted);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenInserted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSpenReversed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public boolean isSupportBleSpen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenAddress(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public String getBleSpenCmfCode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setBleSpenCmfCode(String cmfCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(cmfCode);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void writeBleSpenCommand(String command) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(command);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setSpenPdctLowSensitivityEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void saveBleSpenLogFile(byte[] buffer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeByteArray(buffer);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyBleSpenChargeLockState(boolean isLocked) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeBoolean(isLocked);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerBleSpenChargeLockStateChangedListener(
                    IBleSpenChargeLockStateChangedListener listner) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterBleSpenChargeLockStateChangedListener(
                    IBleSpenChargeLockStateChangedListener listner) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerAirGestureListener(IAirGestureListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterAirGestureListener(IAirGestureListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyAirGesture(String gesture) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(gesture);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getScreenOffReason() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffReason(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void registerInputMethodInfoChangeListener(
                    IInputMethodInfoChangeListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void unregisterInputMethodInfoChangeListener(
                    IInputMethodInfoChangeListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setCurrentInputInfo(
                    IRemoteInputConnection inputConnection,
                    EditorInfo editorInfo,
                    int missingMethodFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeStrongInterface(inputConnection);
                    _data.writeTypedObject(editorInfo, 0);
                    _data.writeInt(missingMethodFlags);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public EditorInfo getCurrentEditorInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    EditorInfo _result = (EditorInfo) _reply.readTypedObject(EditorInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public IRemoteInputConnection getCurrentInputContext() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    IRemoteInputConnection _result =
                            IRemoteInputConnection.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public int getCurrentMissingMethodFlags() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void notifyKeyboardClosed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public Bitmap screenshot(
                    int displayId,
                    int targetWindowType,
                    boolean containsTargetSystemWindow,
                    Rect sourceCrop,
                    int width,
                    int height,
                    boolean useIdentityTransform)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(targetWindowType);
                    _data.writeBoolean(containsTargetSystemWindow);
                    _data.writeTypedObject(sourceCrop, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeBoolean(useIdentityTransform);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenHoverIcon(
                    String pkgName, FileDescriptor fd, float hotspotX, float hotspotY)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeRawFileDescriptor(fd);
                    _data.writeFloat(hotspotX);
                    _data.writeFloat(hotspotY);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenHoverIcon(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenAttachSound(String pkgName, FileDescriptor fd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeRawFileDescriptor(fd);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenAttachSound(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setPenDetachSound(String pkgName, FileDescriptor fd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeRawFileDescriptor(fd);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void resetPenDetachSound(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public void setScreenOffDoubleTabTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureService
            public long getScreenOffDoubleTabTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISpenGestureService.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }
    }
}
