package android.nfc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactPresenceTuple;

/* loaded from: classes3.dex */
public interface INfcAdapterExtras extends IInterface {
  void authenticate(String str, byte[] bArr) throws RemoteException;

  Bundle close(String str, IBinder iBinder) throws RemoteException;

  Bundle closeWithPackageName(String str, IBinder iBinder, String str2) throws RemoteException;

  Bundle getAtr(String str) throws RemoteException;

  Bundle getAtrWithPackageName(String str, String str2) throws RemoteException;

  int getCardEmulationRoute(String str) throws RemoteException;

  String getDriverName(String str) throws RemoteException;

  Bundle open(String str, IBinder iBinder) throws RemoteException;

  Bundle openWithPackageName(String str, IBinder iBinder, String str2) throws RemoteException;

  void setCardEmulationRoute(String str, int i) throws RemoteException;

  Bundle transceive(String str, byte[] bArr) throws RemoteException;

  Bundle transceiveWithPackageName(String str, byte[] bArr, String str2) throws RemoteException;

  public static class Default implements INfcAdapterExtras {
    @Override // android.nfc.INfcAdapterExtras
    public Bundle open(String pkg, IBinder b) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle close(String pkg, IBinder b) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle transceive(String pkg, byte[] data_in) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public int getCardEmulationRoute(String pkg) throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcAdapterExtras
    public void setCardEmulationRoute(String pkg, int route) throws RemoteException {}

    @Override // android.nfc.INfcAdapterExtras
    public void authenticate(String pkg, byte[] token) throws RemoteException {}

    @Override // android.nfc.INfcAdapterExtras
    public String getDriverName(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle getAtr(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle openWithPackageName(String pkg, IBinder b, String callerPkgName)
        throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle closeWithPackageName(String pkg, IBinder b, String callerPkgName)
        throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle transceiveWithPackageName(String pkg, byte[] data_in, String callerPkgName)
        throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapterExtras
    public Bundle getAtrWithPackageName(String pkg, String callerPkgName) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INfcAdapterExtras {
    public static final String DESCRIPTOR = "android.nfc.INfcAdapterExtras";
    static final int TRANSACTION_authenticate = 6;
    static final int TRANSACTION_close = 2;
    static final int TRANSACTION_closeWithPackageName = 10;
    static final int TRANSACTION_getAtr = 8;
    static final int TRANSACTION_getAtrWithPackageName = 12;
    static final int TRANSACTION_getCardEmulationRoute = 4;
    static final int TRANSACTION_getDriverName = 7;
    static final int TRANSACTION_open = 1;
    static final int TRANSACTION_openWithPackageName = 9;
    static final int TRANSACTION_setCardEmulationRoute = 5;
    static final int TRANSACTION_transceive = 3;
    static final int TRANSACTION_transceiveWithPackageName = 11;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static INfcAdapterExtras asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INfcAdapterExtras)) {
        return (INfcAdapterExtras) iin;
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
          return RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
        case 2:
          return "close";
        case 3:
          return "transceive";
        case 4:
          return "getCardEmulationRoute";
        case 5:
          return "setCardEmulationRoute";
        case 6:
          return "authenticate";
        case 7:
          return "getDriverName";
        case 8:
          return "getAtr";
        case 9:
          return "openWithPackageName";
        case 10:
          return "closeWithPackageName";
        case 11:
          return "transceiveWithPackageName";
        case 12:
          return "getAtrWithPackageName";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              IBinder _arg1 = data.readStrongBinder();
              data.enforceNoDataAvail();
              Bundle _result = open(_arg0, _arg1);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              String _arg02 = data.readString();
              IBinder _arg12 = data.readStrongBinder();
              data.enforceNoDataAvail();
              Bundle _result2 = close(_arg02, _arg12);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 3:
              String _arg03 = data.readString();
              byte[] _arg13 = data.createByteArray();
              data.enforceNoDataAvail();
              Bundle _result3 = transceive(_arg03, _arg13);
              reply.writeNoException();
              reply.writeTypedObject(_result3, 1);
              return true;
            case 4:
              String _arg04 = data.readString();
              data.enforceNoDataAvail();
              int _result4 = getCardEmulationRoute(_arg04);
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 5:
              String _arg05 = data.readString();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              setCardEmulationRoute(_arg05, _arg14);
              reply.writeNoException();
              return true;
            case 6:
              String _arg06 = data.readString();
              byte[] _arg15 = data.createByteArray();
              data.enforceNoDataAvail();
              authenticate(_arg06, _arg15);
              reply.writeNoException();
              return true;
            case 7:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              String _result5 = getDriverName(_arg07);
              reply.writeNoException();
              reply.writeString(_result5);
              return true;
            case 8:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result6 = getAtr(_arg08);
              reply.writeNoException();
              reply.writeTypedObject(_result6, 1);
              return true;
            case 9:
              String _arg09 = data.readString();
              IBinder _arg16 = data.readStrongBinder();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result7 = openWithPackageName(_arg09, _arg16, _arg2);
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 10:
              String _arg010 = data.readString();
              IBinder _arg17 = data.readStrongBinder();
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result8 = closeWithPackageName(_arg010, _arg17, _arg22);
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 11:
              String _arg011 = data.readString();
              byte[] _arg18 = data.createByteArray();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result9 = transceiveWithPackageName(_arg011, _arg18, _arg23);
              reply.writeNoException();
              reply.writeTypedObject(_result9, 1);
              return true;
            case 12:
              String _arg012 = data.readString();
              String _arg19 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result10 = getAtrWithPackageName(_arg012, _arg19);
              reply.writeNoException();
              reply.writeTypedObject(_result10, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INfcAdapterExtras {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle open(String pkg, IBinder b) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(b);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle close(String pkg, IBinder b) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(b);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle transceive(String pkg, byte[] data_in) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeByteArray(data_in);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public int getCardEmulationRoute(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public void setCardEmulationRoute(String pkg, int route) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(route);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public void authenticate(String pkg, byte[] token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeByteArray(token);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public String getDriverName(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle getAtr(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle openWithPackageName(String pkg, IBinder b, String callerPkgName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(b);
          _data.writeString(callerPkgName);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle closeWithPackageName(String pkg, IBinder b, String callerPkgName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(b);
          _data.writeString(callerPkgName);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle transceiveWithPackageName(String pkg, byte[] data_in, String callerPkgName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeByteArray(data_in);
          _data.writeString(callerPkgName);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapterExtras
      public Bundle getAtrWithPackageName(String pkg, String callerPkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(callerPkgName);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 11;
    }
  }
}
