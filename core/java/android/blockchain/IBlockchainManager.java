package android.blockchain;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBlockchainManager extends IInterface {
  public static final String DESCRIPTOR = "android.blockchain.IBlockchainManager";

  byte[] getCredential(int i) throws RemoteException;

  byte[] getMeasurementFile() throws RemoteException;

  boolean putCredential(int i, byte[] bArr) throws RemoteException;

  BlockchainTZServiceCommnInfo registerBlockchainFW(
      BlockchainTZServiceConfig blockchainTZServiceConfig) throws RemoteException;

  int sspExit() throws RemoteException;

  int sspInit() throws RemoteException;

  public static class Default implements IBlockchainManager {
    @Override // android.blockchain.IBlockchainManager
    public BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig config)
        throws RemoteException {
      return null;
    }

    @Override // android.blockchain.IBlockchainManager
    public byte[] getMeasurementFile() throws RemoteException {
      return null;
    }

    @Override // android.blockchain.IBlockchainManager
    public boolean putCredential(int type, byte[] cred) throws RemoteException {
      return false;
    }

    @Override // android.blockchain.IBlockchainManager
    public byte[] getCredential(int type) throws RemoteException {
      return null;
    }

    @Override // android.blockchain.IBlockchainManager
    public int sspInit() throws RemoteException {
      return 0;
    }

    @Override // android.blockchain.IBlockchainManager
    public int sspExit() throws RemoteException {
      return 0;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBlockchainManager {
    static final int TRANSACTION_getCredential = 4;
    static final int TRANSACTION_getMeasurementFile = 2;
    static final int TRANSACTION_putCredential = 3;
    static final int TRANSACTION_registerBlockchainFW = 1;
    static final int TRANSACTION_sspExit = 6;
    static final int TRANSACTION_sspInit = 5;

    public Stub() {
      attachInterface(this, IBlockchainManager.DESCRIPTOR);
    }

    public static IBlockchainManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IBlockchainManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IBlockchainManager)) {
        return (IBlockchainManager) iin;
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
          return "registerBlockchainFW";
        case 2:
          return "getMeasurementFile";
        case 3:
          return "putCredential";
        case 4:
          return "getCredential";
        case 5:
          return "sspInit";
        case 6:
          return "sspExit";
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
        data.enforceInterface(IBlockchainManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IBlockchainManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              BlockchainTZServiceConfig _arg0 =
                  (BlockchainTZServiceConfig)
                      data.readTypedObject(BlockchainTZServiceConfig.CREATOR);
              data.enforceNoDataAvail();
              BlockchainTZServiceCommnInfo _result = registerBlockchainFW(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              byte[] _result2 = getMeasurementFile();
              reply.writeNoException();
              reply.writeByteArray(_result2);
              return true;
            case 3:
              int _arg02 = data.readInt();
              byte[] _arg1 = data.createByteArray();
              data.enforceNoDataAvail();
              boolean _result3 = putCredential(_arg02, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              byte[] _result4 = getCredential(_arg03);
              reply.writeNoException();
              reply.writeByteArray(_result4);
              return true;
            case 5:
              int _result5 = sspInit();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              int _result6 = sspExit();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBlockchainManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IBlockchainManager.DESCRIPTOR;
      }

      @Override // android.blockchain.IBlockchainManager
      public BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig config)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
          _data.writeTypedObject(config, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          BlockchainTZServiceCommnInfo _result =
              (BlockchainTZServiceCommnInfo)
                  _reply.readTypedObject(BlockchainTZServiceCommnInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.blockchain.IBlockchainManager
      public byte[] getMeasurementFile() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.blockchain.IBlockchainManager
      public boolean putCredential(int type, byte[] cred) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeByteArray(cred);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.blockchain.IBlockchainManager
      public byte[] getCredential(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.blockchain.IBlockchainManager
      public int sspInit() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.blockchain.IBlockchainManager
      public int sspExit() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
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

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
