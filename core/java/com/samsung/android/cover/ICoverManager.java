package com.samsung.android.cover;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ICoverManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverManager";

  void addLedNotification(Bundle bundle) throws RemoteException;

  void disableCoverManager(boolean z, IBinder iBinder, String str) throws RemoteException;

  boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException;

  boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) throws RemoteException;

  CoverState getCoverState() throws RemoteException;

  CoverState getCoverStateForExternal() throws RemoteException;

  boolean getCoverSwitchState() throws RemoteException;

  int getVersion() throws RemoteException;

  boolean isCoverManagerDisabled() throws RemoteException;

  int onCoverAppCovered(boolean z) throws RemoteException;

  void registerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException;

  void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i)
      throws RemoteException;

  void registerListenerCallbackForExternal(IBinder iBinder, ComponentName componentName, int i)
      throws RemoteException;

  void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName)
      throws RemoteException;

  void removeLedNotification(Bundle bundle) throws RemoteException;

  boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName)
      throws RemoteException;

  void sendDataToCover(int i, byte[] bArr) throws RemoteException;

  void sendDataToNfcLedCover(int i, byte[] bArr) throws RemoteException;

  void sendPowerKeyToCover() throws RemoteException;

  void sendSystemEvent(Bundle bundle) throws RemoteException;

  boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName)
      throws RemoteException;

  boolean unregisterCallback(IBinder iBinder) throws RemoteException;

  boolean unregisterCallbackForExternal(IBinder iBinder) throws RemoteException;

  boolean unregisterNfcTouchListenerCallback(IBinder iBinder) throws RemoteException;

  public static class Default implements ICoverManager {
    @Override // com.samsung.android.cover.ICoverManager
    public void registerCallback(IBinder binder, ComponentName component) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void registerListenerCallback(IBinder binder, ComponentName component, int type)
        throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public boolean unregisterCallback(IBinder binder) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public CoverState getCoverState() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public boolean getCoverSwitchState() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public boolean isCoverManagerDisabled() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public void disableCoverManager(boolean disable, IBinder token, String pkg)
        throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public int getVersion() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public void sendDataToCover(int command, byte[] data) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void sendPowerKeyToCover() throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void registerNfcTouchListenerCallback(
        int command, IBinder token, ComponentName component) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public boolean unregisterNfcTouchListenerCallback(IBinder token) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public void sendDataToNfcLedCover(int command, byte[] data) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void addLedNotification(Bundle data) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void removeLedNotification(Bundle data) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public void sendSystemEvent(Bundle data) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public boolean disableLcdOffByCover(IBinder binder, ComponentName component)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public boolean enableLcdOffByCover(IBinder binder, ComponentName component)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public boolean requestCoverAuthentication(IBinder binder, ComponentName component)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public boolean setFotaInProgress(boolean inProgress, IBinder binder, ComponentName component)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public int onCoverAppCovered(boolean covered) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public void registerListenerCallbackForExternal(
        IBinder binder, ComponentName component, int type) throws RemoteException {}

    @Override // com.samsung.android.cover.ICoverManager
    public boolean unregisterCallbackForExternal(IBinder binder) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.cover.ICoverManager
    public CoverState getCoverStateForExternal() throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICoverManager {
    static final int TRANSACTION_addLedNotification = 14;
    static final int TRANSACTION_disableCoverManager = 7;
    static final int TRANSACTION_disableLcdOffByCover = 17;
    static final int TRANSACTION_enableLcdOffByCover = 18;
    static final int TRANSACTION_getCoverState = 4;
    static final int TRANSACTION_getCoverStateForExternal = 24;
    static final int TRANSACTION_getCoverSwitchState = 5;
    static final int TRANSACTION_getVersion = 8;
    static final int TRANSACTION_isCoverManagerDisabled = 6;
    static final int TRANSACTION_onCoverAppCovered = 21;
    static final int TRANSACTION_registerCallback = 1;
    static final int TRANSACTION_registerListenerCallback = 2;
    static final int TRANSACTION_registerListenerCallbackForExternal = 22;
    static final int TRANSACTION_registerNfcTouchListenerCallback = 11;
    static final int TRANSACTION_removeLedNotification = 15;
    static final int TRANSACTION_requestCoverAuthentication = 19;
    static final int TRANSACTION_sendDataToCover = 9;
    static final int TRANSACTION_sendDataToNfcLedCover = 13;
    static final int TRANSACTION_sendPowerKeyToCover = 10;
    static final int TRANSACTION_sendSystemEvent = 16;
    static final int TRANSACTION_setFotaInProgress = 20;
    static final int TRANSACTION_unregisterCallback = 3;
    static final int TRANSACTION_unregisterCallbackForExternal = 23;
    static final int TRANSACTION_unregisterNfcTouchListenerCallback = 12;

    public Stub() {
      attachInterface(this, ICoverManager.DESCRIPTOR);
    }

    public static ICoverManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICoverManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ICoverManager)) {
        return (ICoverManager) iin;
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
          return "registerCallback";
        case 2:
          return "registerListenerCallback";
        case 3:
          return "unregisterCallback";
        case 4:
          return "getCoverState";
        case 5:
          return "getCoverSwitchState";
        case 6:
          return "isCoverManagerDisabled";
        case 7:
          return "disableCoverManager";
        case 8:
          return "getVersion";
        case 9:
          return "sendDataToCover";
        case 10:
          return "sendPowerKeyToCover";
        case 11:
          return "registerNfcTouchListenerCallback";
        case 12:
          return "unregisterNfcTouchListenerCallback";
        case 13:
          return "sendDataToNfcLedCover";
        case 14:
          return "addLedNotification";
        case 15:
          return "removeLedNotification";
        case 16:
          return "sendSystemEvent";
        case 17:
          return "disableLcdOffByCover";
        case 18:
          return "enableLcdOffByCover";
        case 19:
          return "requestCoverAuthentication";
        case 20:
          return "setFotaInProgress";
        case 21:
          return "onCoverAppCovered";
        case 22:
          return "registerListenerCallbackForExternal";
        case 23:
          return "unregisterCallbackForExternal";
        case 24:
          return "getCoverStateForExternal";
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
        data.enforceInterface(ICoverManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICoverManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IBinder _arg0 = data.readStrongBinder();
              ComponentName _arg1 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              registerCallback(_arg0, _arg1);
              reply.writeNoException();
              return true;
            case 2:
              IBinder _arg02 = data.readStrongBinder();
              ComponentName _arg12 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              registerListenerCallback(_arg02, _arg12, _arg2);
              reply.writeNoException();
              return true;
            case 3:
              IBinder _arg03 = data.readStrongBinder();
              data.enforceNoDataAvail();
              boolean _result = unregisterCallback(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 4:
              CoverState _result2 = getCoverState();
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 5:
              boolean _result3 = getCoverSwitchState();
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 6:
              boolean _result4 = isCoverManagerDisabled();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 7:
              boolean _arg04 = data.readBoolean();
              IBinder _arg13 = data.readStrongBinder();
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              disableCoverManager(_arg04, _arg13, _arg22);
              reply.writeNoException();
              return true;
            case 8:
              int _result5 = getVersion();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 9:
              int _arg05 = data.readInt();
              byte[] _arg14 = data.createByteArray();
              data.enforceNoDataAvail();
              sendDataToCover(_arg05, _arg14);
              reply.writeNoException();
              return true;
            case 10:
              sendPowerKeyToCover();
              reply.writeNoException();
              return true;
            case 11:
              int _arg06 = data.readInt();
              IBinder _arg15 = data.readStrongBinder();
              ComponentName _arg23 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              registerNfcTouchListenerCallback(_arg06, _arg15, _arg23);
              reply.writeNoException();
              return true;
            case 12:
              IBinder _arg07 = data.readStrongBinder();
              data.enforceNoDataAvail();
              boolean _result6 = unregisterNfcTouchListenerCallback(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 13:
              int _arg08 = data.readInt();
              byte[] _arg16 = data.createByteArray();
              data.enforceNoDataAvail();
              sendDataToNfcLedCover(_arg08, _arg16);
              reply.writeNoException();
              return true;
            case 14:
              Bundle _arg09 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              addLedNotification(_arg09);
              reply.writeNoException();
              return true;
            case 15:
              Bundle _arg010 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              removeLedNotification(_arg010);
              reply.writeNoException();
              return true;
            case 16:
              Bundle _arg011 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              sendSystemEvent(_arg011);
              reply.writeNoException();
              return true;
            case 17:
              IBinder _arg012 = data.readStrongBinder();
              ComponentName _arg17 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result7 = disableLcdOffByCover(_arg012, _arg17);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 18:
              IBinder _arg013 = data.readStrongBinder();
              ComponentName _arg18 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result8 = enableLcdOffByCover(_arg013, _arg18);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 19:
              IBinder _arg014 = data.readStrongBinder();
              ComponentName _arg19 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result9 = requestCoverAuthentication(_arg014, _arg19);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 20:
              boolean _arg015 = data.readBoolean();
              IBinder _arg110 = data.readStrongBinder();
              ComponentName _arg24 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result10 = setFotaInProgress(_arg015, _arg110, _arg24);
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 21:
              boolean _arg016 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result11 = onCoverAppCovered(_arg016);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 22:
              IBinder _arg017 = data.readStrongBinder();
              ComponentName _arg111 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              registerListenerCallbackForExternal(_arg017, _arg111, _arg25);
              reply.writeNoException();
              return true;
            case 23:
              IBinder _arg018 = data.readStrongBinder();
              data.enforceNoDataAvail();
              boolean _result12 = unregisterCallbackForExternal(_arg018);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 24:
              CoverState _result13 = getCoverStateForExternal();
              reply.writeNoException();
              reply.writeTypedObject(_result13, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICoverManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICoverManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void registerCallback(IBinder binder, ComponentName component) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void registerListenerCallback(IBinder binder, ComponentName component, int type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          _data.writeInt(type);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean unregisterCallback(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public CoverState getCoverState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          CoverState _result = (CoverState) _reply.readTypedObject(CoverState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean getCoverSwitchState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean isCoverManagerDisabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void disableCoverManager(boolean disable, IBinder token, String pkg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeBoolean(disable);
          _data.writeStrongBinder(token);
          _data.writeString(pkg);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public int getVersion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void sendDataToCover(int command, byte[] data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeInt(command);
          _data.writeByteArray(data);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void sendPowerKeyToCover() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void registerNfcTouchListenerCallback(
          int command, IBinder token, ComponentName component) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeInt(command);
          _data.writeStrongBinder(token);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean unregisterNfcTouchListenerCallback(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void sendDataToNfcLedCover(int command, byte[] data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeInt(command);
          _data.writeByteArray(data);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void addLedNotification(Bundle data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void removeLedNotification(Bundle data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void sendSystemEvent(Bundle data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean disableLcdOffByCover(IBinder binder, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean enableLcdOffByCover(IBinder binder, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean requestCoverAuthentication(IBinder binder, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean setFotaInProgress(boolean inProgress, IBinder binder, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeBoolean(inProgress);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public int onCoverAppCovered(boolean covered) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeBoolean(covered);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public void registerListenerCallbackForExternal(
          IBinder binder, ComponentName component, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          _data.writeInt(type);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public boolean unregisterCallbackForExternal(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.ICoverManager
      public CoverState getCoverStateForExternal() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICoverManager.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          CoverState _result = (CoverState) _reply.readTypedObject(CoverState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 23;
    }
  }
}
