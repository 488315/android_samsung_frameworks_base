package com.skms.android.agent;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISkpmService extends IInterface {
  public static final String DESCRIPTOR = "com.skms.android.agent.ISkpmService";

  int SkpmChangeOtaServer(String str, String str2) throws RemoteException;

  int SkpmReadInjectedKeyUID(byte b, byte b2, String str, byte[] bArr) throws RemoteException;

  int SkpmServiceCreateGetKeySession(byte b, String str, byte[] bArr, byte b2)
      throws RemoteException;

  int SkpmServiceInjectedKeyVerification(byte b, byte b2, String str) throws RemoteException;

  int SkpmServiceKeyInjection(byte b, byte b2, String str, byte[] bArr, byte b3)
      throws RemoteException;

  int SkpmServiceReleaseGetKeySession() throws RemoteException;

  public static class Default implements ISkpmService {
    @Override // com.skms.android.agent.ISkpmService
    public int SkpmServiceKeyInjection(
        byte injection_type, byte key_type, String key_name, byte[] key_data, byte encoding_type)
        throws RemoteException {
      return 0;
    }

    @Override // com.skms.android.agent.ISkpmService
    public int SkpmServiceInjectedKeyVerification(
        byte injection_type, byte key_type, String key_name) throws RemoteException {
      return 0;
    }

    @Override // com.skms.android.agent.ISkpmService
    public int SkpmReadInjectedKeyUID(
        byte injection_type, byte key_type, String key_name, byte[] uid) throws RemoteException {
      return 0;
    }

    @Override // com.skms.android.agent.ISkpmService
    public int SkpmServiceCreateGetKeySession(
        byte key_type, String key_name, byte[] key_blob, byte encoding_type)
        throws RemoteException {
      return 0;
    }

    @Override // com.skms.android.agent.ISkpmService
    public int SkpmServiceReleaseGetKeySession() throws RemoteException {
      return 0;
    }

    @Override // com.skms.android.agent.ISkpmService
    public int SkpmChangeOtaServer(String server_address, String server_port)
        throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISkpmService {
    static final int TRANSACTION_SkpmChangeOtaServer = 6;
    static final int TRANSACTION_SkpmReadInjectedKeyUID = 3;
    static final int TRANSACTION_SkpmServiceCreateGetKeySession = 4;
    static final int TRANSACTION_SkpmServiceInjectedKeyVerification = 2;
    static final int TRANSACTION_SkpmServiceKeyInjection = 1;
    static final int TRANSACTION_SkpmServiceReleaseGetKeySession = 5;

    public Stub() {
      attachInterface(this, ISkpmService.DESCRIPTOR);
    }

    public static ISkpmService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISkpmService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISkpmService)) {
        return (ISkpmService) iin;
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
          return "SkpmServiceKeyInjection";
        case 2:
          return "SkpmServiceInjectedKeyVerification";
        case 3:
          return "SkpmReadInjectedKeyUID";
        case 4:
          return "SkpmServiceCreateGetKeySession";
        case 5:
          return "SkpmServiceReleaseGetKeySession";
        case 6:
          return "SkpmChangeOtaServer";
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
        data.enforceInterface(ISkpmService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISkpmService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              byte _arg0 = data.readByte();
              byte _arg1 = data.readByte();
              String _arg2 = data.readString();
              byte[] _arg3 = data.createByteArray();
              byte _arg4 = data.readByte();
              data.enforceNoDataAvail();
              int _result = SkpmServiceKeyInjection(_arg0, _arg1, _arg2, _arg3, _arg4);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              byte _arg02 = data.readByte();
              byte _arg12 = data.readByte();
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              int _result2 = SkpmServiceInjectedKeyVerification(_arg02, _arg12, _arg22);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              byte _arg03 = data.readByte();
              byte _arg13 = data.readByte();
              String _arg23 = data.readString();
              byte[] _arg32 = data.createByteArray();
              data.enforceNoDataAvail();
              int _result3 = SkpmReadInjectedKeyUID(_arg03, _arg13, _arg23, _arg32);
              reply.writeNoException();
              reply.writeInt(_result3);
              reply.writeByteArray(_arg32);
              return true;
            case 4:
              byte _arg04 = data.readByte();
              String _arg14 = data.readString();
              byte[] _arg24 = data.createByteArray();
              byte _arg33 = data.readByte();
              data.enforceNoDataAvail();
              int _result4 = SkpmServiceCreateGetKeySession(_arg04, _arg14, _arg24, _arg33);
              reply.writeNoException();
              reply.writeInt(_result4);
              reply.writeByteArray(_arg24);
              return true;
            case 5:
              int _result5 = SkpmServiceReleaseGetKeySession();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              String _arg05 = data.readString();
              String _arg15 = data.readString();
              data.enforceNoDataAvail();
              int _result6 = SkpmChangeOtaServer(_arg05, _arg15);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISkpmService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISkpmService.DESCRIPTOR;
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmServiceKeyInjection(
          byte injection_type, byte key_type, String key_name, byte[] key_data, byte encoding_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          _data.writeByte(injection_type);
          _data.writeByte(key_type);
          _data.writeString(key_name);
          _data.writeByteArray(key_data);
          _data.writeByte(encoding_type);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmServiceInjectedKeyVerification(
          byte injection_type, byte key_type, String key_name) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          _data.writeByte(injection_type);
          _data.writeByte(key_type);
          _data.writeString(key_name);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmReadInjectedKeyUID(
          byte injection_type, byte key_type, String key_name, byte[] uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          _data.writeByte(injection_type);
          _data.writeByte(key_type);
          _data.writeString(key_name);
          _data.writeByteArray(uid);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          _reply.readByteArray(uid);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmServiceCreateGetKeySession(
          byte key_type, String key_name, byte[] key_blob, byte encoding_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          _data.writeByte(key_type);
          _data.writeString(key_name);
          _data.writeByteArray(key_blob);
          _data.writeByte(encoding_type);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          _reply.readByteArray(key_blob);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmServiceReleaseGetKeySession() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.skms.android.agent.ISkpmService
      public int SkpmChangeOtaServer(String server_address, String server_port)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISkpmService.DESCRIPTOR);
          _data.writeString(server_address);
          _data.writeString(server_port);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
