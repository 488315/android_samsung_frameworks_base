package android.media;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IResourceManagerClient extends IInterface {
  public static final String DESCRIPTOR = "android.media.IResourceManagerClient";

  int getCodecState() throws RemoteException;

  String getName() throws RemoteException;

  boolean reclaimResource() throws RemoteException;

  void startWatchingMode() throws RemoteException;

  void stopWatchingMode() throws RemoteException;

  public static class Default implements IResourceManagerClient {
    @Override // android.media.IResourceManagerClient
    public boolean reclaimResource() throws RemoteException {
      return false;
    }

    @Override // android.media.IResourceManagerClient
    public String getName() throws RemoteException {
      return null;
    }

    @Override // android.media.IResourceManagerClient
    public int getCodecState() throws RemoteException {
      return 0;
    }

    @Override // android.media.IResourceManagerClient
    public void startWatchingMode() throws RemoteException {}

    @Override // android.media.IResourceManagerClient
    public void stopWatchingMode() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IResourceManagerClient {
    static final int TRANSACTION_getCodecState = 3;
    static final int TRANSACTION_getName = 2;
    static final int TRANSACTION_reclaimResource = 1;
    static final int TRANSACTION_startWatchingMode = 4;
    static final int TRANSACTION_stopWatchingMode = 5;

    public Stub() {
      attachInterface(this, IResourceManagerClient.DESCRIPTOR);
    }

    public static IResourceManagerClient asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IResourceManagerClient.DESCRIPTOR);
      if (iin != null && (iin instanceof IResourceManagerClient)) {
        return (IResourceManagerClient) iin;
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
          return "reclaimResource";
        case 2:
          return "getName";
        case 3:
          return "getCodecState";
        case 4:
          return "startWatchingMode";
        case 5:
          return "stopWatchingMode";
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
        data.enforceInterface(IResourceManagerClient.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IResourceManagerClient.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = reclaimResource();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              String _result2 = getName();
              reply.writeNoException();
              reply.writeString(_result2);
              return true;
            case 3:
              int _result3 = getCodecState();
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 4:
              startWatchingMode();
              return true;
            case 5:
              stopWatchingMode();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IResourceManagerClient {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IResourceManagerClient.DESCRIPTOR;
      }

      @Override // android.media.IResourceManagerClient
      public boolean reclaimResource() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerClient
      public String getName() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerClient
      public int getCodecState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerClient
      public void startWatchingMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerClient
      public void stopWatchingMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IResourceManagerClient.DESCRIPTOR);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
