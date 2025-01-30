package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IDesktopModeUiService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeUiService";

  void dismissDialog(int i, int i2) throws RemoteException;

  void dismissOverlay(int i, int i2) throws RemoteException;

  void finishActivity(int i) throws RemoteException;

  int getCurrentDialogType() throws RemoteException;

  int getCurrentOverlayType(int i) throws RemoteException;

  boolean hasOverlay(int i, int i2) throws RemoteException;

  boolean hasUiElement() throws RemoteException;

  boolean isActivityShowing(int i) throws RemoteException;

  void removeNavBarIcon(int i) throws RemoteException;

  void removeNotification(int i) throws RemoteException;

  void showDialog(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback)
      throws RemoteException;

  void showNavBarIcon(int i) throws RemoteException;

  void showNotification(int i) throws RemoteException;

  void showOverlay(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback)
      throws RemoteException;

  void startActivity(int i, int i2, IDesktopModeUiServiceCallback iDesktopModeUiServiceCallback)
      throws RemoteException;

  public static class Default implements IDesktopModeUiService {
    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public boolean hasUiElement() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void showDialog(int displayId, int type, IDesktopModeUiServiceCallback callback)
        throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void dismissDialog(int displayId, int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public int getCurrentDialogType() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void showOverlay(int where, int type, IDesktopModeUiServiceCallback callback)
        throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void dismissOverlay(int where, int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public int getCurrentOverlayType(int where) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public boolean hasOverlay(int where, int type) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void showNotification(int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void removeNotification(int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void startActivity(int displayId, int type, IDesktopModeUiServiceCallback callback)
        throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void finishActivity(int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public boolean isActivityShowing(int type) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void showNavBarIcon(int type) throws RemoteException {}

    @Override // com.samsung.android.desktopmode.IDesktopModeUiService
    public void removeNavBarIcon(int type) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDesktopModeUiService {
    static final int TRANSACTION_dismissDialog = 3;
    static final int TRANSACTION_dismissOverlay = 6;
    static final int TRANSACTION_finishActivity = 12;
    static final int TRANSACTION_getCurrentDialogType = 4;
    static final int TRANSACTION_getCurrentOverlayType = 7;
    static final int TRANSACTION_hasOverlay = 8;
    static final int TRANSACTION_hasUiElement = 1;
    static final int TRANSACTION_isActivityShowing = 13;
    static final int TRANSACTION_removeNavBarIcon = 15;
    static final int TRANSACTION_removeNotification = 10;
    static final int TRANSACTION_showDialog = 2;
    static final int TRANSACTION_showNavBarIcon = 14;
    static final int TRANSACTION_showNotification = 9;
    static final int TRANSACTION_showOverlay = 5;
    static final int TRANSACTION_startActivity = 11;

    public Stub() {
      attachInterface(this, IDesktopModeUiService.DESCRIPTOR);
    }

    public static IDesktopModeUiService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDesktopModeUiService.DESCRIPTOR);
      if (iin != null && (iin instanceof IDesktopModeUiService)) {
        return (IDesktopModeUiService) iin;
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
          return "hasUiElement";
        case 2:
          return "showDialog";
        case 3:
          return "dismissDialog";
        case 4:
          return "getCurrentDialogType";
        case 5:
          return "showOverlay";
        case 6:
          return "dismissOverlay";
        case 7:
          return "getCurrentOverlayType";
        case 8:
          return "hasOverlay";
        case 9:
          return "showNotification";
        case 10:
          return "removeNotification";
        case 11:
          return "startActivity";
        case 12:
          return "finishActivity";
        case 13:
          return "isActivityShowing";
        case 14:
          return "showNavBarIcon";
        case 15:
          return "removeNavBarIcon";
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
        data.enforceInterface(IDesktopModeUiService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDesktopModeUiService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = hasUiElement();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              IDesktopModeUiServiceCallback _arg2 =
                  IDesktopModeUiServiceCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              showDialog(_arg0, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 3:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              dismissDialog(_arg02, _arg12);
              reply.writeNoException();
              return true;
            case 4:
              int _result2 = getCurrentDialogType();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 5:
              int _arg03 = data.readInt();
              int _arg13 = data.readInt();
              IDesktopModeUiServiceCallback _arg22 =
                  IDesktopModeUiServiceCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              showOverlay(_arg03, _arg13, _arg22);
              reply.writeNoException();
              return true;
            case 6:
              int _arg04 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              dismissOverlay(_arg04, _arg14);
              reply.writeNoException();
              return true;
            case 7:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              int _result3 = getCurrentOverlayType(_arg05);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 8:
              int _arg06 = data.readInt();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = hasOverlay(_arg06, _arg15);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 9:
              int _arg07 = data.readInt();
              data.enforceNoDataAvail();
              showNotification(_arg07);
              reply.writeNoException();
              return true;
            case 10:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              removeNotification(_arg08);
              reply.writeNoException();
              return true;
            case 11:
              int _arg09 = data.readInt();
              int _arg16 = data.readInt();
              IDesktopModeUiServiceCallback _arg23 =
                  IDesktopModeUiServiceCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              startActivity(_arg09, _arg16, _arg23);
              reply.writeNoException();
              return true;
            case 12:
              int _arg010 = data.readInt();
              data.enforceNoDataAvail();
              finishActivity(_arg010);
              reply.writeNoException();
              return true;
            case 13:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result5 = isActivityShowing(_arg011);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 14:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              showNavBarIcon(_arg012);
              reply.writeNoException();
              return true;
            case 15:
              int _arg013 = data.readInt();
              data.enforceNoDataAvail();
              removeNavBarIcon(_arg013);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDesktopModeUiService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDesktopModeUiService.DESCRIPTOR;
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public boolean hasUiElement() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void showDialog(int displayId, int type, IDesktopModeUiServiceCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(type);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void dismissDialog(int displayId, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(type);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public int getCurrentDialogType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void showOverlay(int where, int type, IDesktopModeUiServiceCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(where);
          _data.writeInt(type);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void dismissOverlay(int where, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(where);
          _data.writeInt(type);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public int getCurrentOverlayType(int where) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(where);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public boolean hasOverlay(int where, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(where);
          _data.writeInt(type);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void showNotification(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void removeNotification(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void startActivity(int displayId, int type, IDesktopModeUiServiceCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(type);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void finishActivity(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public boolean isActivityShowing(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void showNavBarIcon(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeUiService
      public void removeNavBarIcon(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeUiService.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 14;
    }
  }
}
