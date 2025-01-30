package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IDesktopMode extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopMode";

  Bundle getDesktopModeKillPolicy() throws RemoteException;

  SemDesktopModeState getDesktopModeState() throws RemoteException;

  boolean isAllowed() throws RemoteException;

  boolean isDesktopDockConnected() throws RemoteException;

  boolean isDesktopMode() throws RemoteException;

  boolean isDeviceConnected() throws RemoteException;

  void onSecuredAppLaunched(IBinder iBinder, String str) throws RemoteException;

  boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str)
      throws RemoteException;

  void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) throws RemoteException;

  boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str)
      throws RemoteException;

  void scheduleUpdateDesktopMode(boolean z) throws RemoteException;

  Bundle sendMessage(Bundle bundle) throws RemoteException;

  boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) throws RemoteException;

  boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener)
      throws RemoteException;

  public static class Default implements IDesktopMode {
    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean isDesktopDockConnected() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean isDesktopMode() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public SemDesktopModeState getDesktopModeState() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean registerDesktopModeListener(IDesktopModeListener callback, String name)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean registerBlocker(IDesktopModeBlocker blocker, String name)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean unregisterDesktopModeListener(IDesktopModeListener callback)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean unregisterBlocker(IDesktopModeBlocker blocker) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean isDeviceConnected() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public boolean isAllowed() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public void scheduleUpdateDesktopMode(boolean enter) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public Bundle getDesktopModeKillPolicy() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public void registerDesktopLauncher(IDesktopModeLauncher launcher) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public Bundle sendMessage(Bundle message) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.desktopmode.IDesktopMode
    public void onSecuredAppLaunched(IBinder activityToken, String packageName)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDesktopMode {
    static final int TRANSACTION_getDesktopModeKillPolicy = 11;
    static final int TRANSACTION_getDesktopModeState = 3;
    static final int TRANSACTION_isAllowed = 9;
    static final int TRANSACTION_isDesktopDockConnected = 1;
    static final int TRANSACTION_isDesktopMode = 2;
    static final int TRANSACTION_isDeviceConnected = 8;
    static final int TRANSACTION_onSecuredAppLaunched = 14;
    static final int TRANSACTION_registerBlocker = 5;
    static final int TRANSACTION_registerDesktopLauncher = 12;
    static final int TRANSACTION_registerDesktopModeListener = 4;
    static final int TRANSACTION_scheduleUpdateDesktopMode = 10;
    static final int TRANSACTION_sendMessage = 13;
    static final int TRANSACTION_unregisterBlocker = 7;
    static final int TRANSACTION_unregisterDesktopModeListener = 6;

    public Stub() {
      attachInterface(this, IDesktopMode.DESCRIPTOR);
    }

    public static IDesktopMode asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDesktopMode.DESCRIPTOR);
      if (iin != null && (iin instanceof IDesktopMode)) {
        return (IDesktopMode) iin;
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
          return "isDesktopDockConnected";
        case 2:
          return "isDesktopMode";
        case 3:
          return "getDesktopModeState";
        case 4:
          return "registerDesktopModeListener";
        case 5:
          return "registerBlocker";
        case 6:
          return "unregisterDesktopModeListener";
        case 7:
          return "unregisterBlocker";
        case 8:
          return "isDeviceConnected";
        case 9:
          return "isAllowed";
        case 10:
          return "scheduleUpdateDesktopMode";
        case 11:
          return "getDesktopModeKillPolicy";
        case 12:
          return "registerDesktopLauncher";
        case 13:
          return "sendMessage";
        case 14:
          return "onSecuredAppLaunched";
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
        data.enforceInterface(IDesktopMode.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDesktopMode.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isDesktopDockConnected();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              boolean _result2 = isDesktopMode();
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              SemDesktopModeState _result3 = getDesktopModeState();
              reply.writeNoException();
              reply.writeTypedObject(_result3, 1);
              return true;
            case 4:
              IDesktopModeListener _arg0 =
                  IDesktopModeListener.Stub.asInterface(data.readStrongBinder());
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 = registerDesktopModeListener(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              IDesktopModeBlocker _arg02 =
                  IDesktopModeBlocker.Stub.asInterface(data.readStrongBinder());
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              boolean _result5 = registerBlocker(_arg02, _arg12);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 6:
              IDesktopModeListener _arg03 =
                  IDesktopModeListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result6 = unregisterDesktopModeListener(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 7:
              IDesktopModeBlocker _arg04 =
                  IDesktopModeBlocker.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result7 = unregisterBlocker(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              boolean _result8 = isDeviceConnected();
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 9:
              boolean _result9 = isAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 10:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              scheduleUpdateDesktopMode(_arg05);
              reply.writeNoException();
              return true;
            case 11:
              Bundle _result10 = getDesktopModeKillPolicy();
              reply.writeNoException();
              reply.writeTypedObject(_result10, 1);
              return true;
            case 12:
              IDesktopModeLauncher _arg06 =
                  IDesktopModeLauncher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerDesktopLauncher(_arg06);
              reply.writeNoException();
              return true;
            case 13:
              Bundle _arg07 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              Bundle _result11 = sendMessage(_arg07);
              reply.writeNoException();
              reply.writeTypedObject(_result11, 1);
              return true;
            case 14:
              IBinder _arg08 = data.readStrongBinder();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              onSecuredAppLaunched(_arg08, _arg13);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDesktopMode {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDesktopMode.DESCRIPTOR;
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean isDesktopDockConnected() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean isDesktopMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public SemDesktopModeState getDesktopModeState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          SemDesktopModeState _result =
              (SemDesktopModeState) _reply.readTypedObject(SemDesktopModeState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean registerDesktopModeListener(IDesktopModeListener callback, String name)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          _data.writeString(name);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean registerBlocker(IDesktopModeBlocker blocker, String name)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongInterface(blocker);
          _data.writeString(name);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean unregisterDesktopModeListener(IDesktopModeListener callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean unregisterBlocker(IDesktopModeBlocker blocker) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongInterface(blocker);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean isDeviceConnected() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public boolean isAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public void scheduleUpdateDesktopMode(boolean enter) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeBoolean(enter);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public Bundle getDesktopModeKillPolicy() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public void registerDesktopLauncher(IDesktopModeLauncher launcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongInterface(launcher);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public Bundle sendMessage(Bundle message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeTypedObject(message, 0);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopMode
      public void onSecuredAppLaunched(IBinder activityToken, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopMode.DESCRIPTOR);
          _data.writeStrongBinder(activityToken);
          _data.writeString(packageName);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 13;
    }
  }
}
