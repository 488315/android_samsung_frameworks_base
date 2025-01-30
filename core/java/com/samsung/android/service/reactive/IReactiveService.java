package com.samsung.android.service.reactive;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IReactiveService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.service.reactive.IReactiveService";

  int getErrorCode() throws RemoteException;

  int getFlag(int i) throws RemoteException;

  byte[] getRandom() throws RemoteException;

  int getServiceSupport() throws RemoteException;

  String getString() throws RemoteException;

  int removeString() throws RemoteException;

  byte[] sessionAccept(byte[] bArr) throws RemoteException;

  int sessionComplete(byte[] bArr) throws RemoteException;

  int setFlag(int i, int i2, String str) throws RemoteException;

  int setString(String str) throws RemoteException;

  int verify(String str, int i) throws RemoteException;

  public static class Default implements IReactiveService {
    @Override // com.samsung.android.service.reactive.IReactiveService
    public int getServiceSupport() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int getFlag(int flag) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int setFlag(int flag, int value, String string) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public String getString() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int setString(String string) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int removeString() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public byte[] sessionAccept(byte[] input) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int sessionComplete(byte[] input) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int getErrorCode() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public int verify(String vt, int operation) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.reactive.IReactiveService
    public byte[] getRandom() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IReactiveService {
    static final int TRANSACTION_getErrorCode = 9;
    static final int TRANSACTION_getFlag = 2;
    static final int TRANSACTION_getRandom = 11;
    static final int TRANSACTION_getServiceSupport = 1;
    static final int TRANSACTION_getString = 4;
    static final int TRANSACTION_removeString = 6;
    static final int TRANSACTION_sessionAccept = 7;
    static final int TRANSACTION_sessionComplete = 8;
    static final int TRANSACTION_setFlag = 3;
    static final int TRANSACTION_setString = 5;
    static final int TRANSACTION_verify = 10;

    public Stub() {
      attachInterface(this, IReactiveService.DESCRIPTOR);
    }

    public static IReactiveService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IReactiveService.DESCRIPTOR);
      if (iin != null && (iin instanceof IReactiveService)) {
        return (IReactiveService) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "getServiceSupport";
        case 2:
          return "getFlag";
        case 3:
          return "setFlag";
        case 4:
          return "getString";
        case 5:
          return "setString";
        case 6:
          return "removeString";
        case 7:
          return "sessionAccept";
        case 8:
          return "sessionComplete";
        case 9:
          return "getErrorCode";
        case 10:
          return "verify";
        case 11:
          return "getRandom";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IReactiveService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IReactiveService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _result = getServiceSupport();
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              int _result2 = getFlag(_arg0);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              int _arg02 = data.readInt();
              int _arg1 = data.readInt();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              int _result3 = setFlag(_arg02, _arg1, _arg2);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 4:
              String _result4 = getString();
              reply.writeNoException();
              reply.writeString(_result4);
              return true;
            case 5:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              int _result5 = setString(_arg03);
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              int _result6 = removeString();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 7:
              byte[] _arg04 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result7 = sessionAccept(_arg04);
              reply.writeNoException();
              reply.writeByteArray(_result7);
              return true;
            case 8:
              byte[] _arg05 = data.createByteArray();
              data.enforceNoDataAvail();
              int _result8 = sessionComplete(_arg05);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              int _result9 = getErrorCode();
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 10:
              String _arg06 = data.readString();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              int _result10 = verify(_arg06, _arg12);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 11:
              byte[] _result11 = getRandom();
              reply.writeNoException();
              reply.writeByteArray(_result11);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IReactiveService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IReactiveService.DESCRIPTOR;
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int getServiceSupport() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int getFlag(int flag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeInt(flag);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int setFlag(int flag, int value, String string) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeInt(flag);
          _data.writeInt(value);
          _data.writeString(string);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public String getString() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int setString(String string) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeString(string);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int removeString() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public byte[] sessionAccept(byte[] input) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeByteArray(input);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int sessionComplete(byte[] input) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeByteArray(input);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int getErrorCode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public int verify(String vt, int operation) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          _data.writeString(vt);
          _data.writeInt(operation);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.reactive.IReactiveService
      public byte[] getRandom() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IReactiveService.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 10;
    }
  }
}
