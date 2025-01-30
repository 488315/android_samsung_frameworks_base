package android.media;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteDisplayProvider extends IInterface {
  void adjustVolume(String str, int i) throws RemoteException;

  void connect(String str) throws RemoteException;

  void disconnect(String str) throws RemoteException;

  void setCallback(IRemoteDisplayCallback iRemoteDisplayCallback) throws RemoteException;

  void setDiscoveryMode(int i) throws RemoteException;

  void setVolume(String str, int i) throws RemoteException;

  public static class Default implements IRemoteDisplayProvider {
    @Override // android.media.IRemoteDisplayProvider
    public void setCallback(IRemoteDisplayCallback callback) throws RemoteException {}

    @Override // android.media.IRemoteDisplayProvider
    public void setDiscoveryMode(int mode) throws RemoteException {}

    @Override // android.media.IRemoteDisplayProvider
    public void connect(String id) throws RemoteException {}

    @Override // android.media.IRemoteDisplayProvider
    public void disconnect(String id) throws RemoteException {}

    @Override // android.media.IRemoteDisplayProvider
    public void setVolume(String id, int volume) throws RemoteException {}

    @Override // android.media.IRemoteDisplayProvider
    public void adjustVolume(String id, int delta) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRemoteDisplayProvider {
    public static final String DESCRIPTOR = "android.media.IRemoteDisplayProvider";
    static final int TRANSACTION_adjustVolume = 6;
    static final int TRANSACTION_connect = 3;
    static final int TRANSACTION_disconnect = 4;
    static final int TRANSACTION_setCallback = 1;
    static final int TRANSACTION_setDiscoveryMode = 2;
    static final int TRANSACTION_setVolume = 5;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IRemoteDisplayProvider asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IRemoteDisplayProvider)) {
        return (IRemoteDisplayProvider) iin;
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
          return "setCallback";
        case 2:
          return "setDiscoveryMode";
        case 3:
          return MediaMetrics.Value.CONNECT;
        case 4:
          return MediaMetrics.Value.DISCONNECT;
        case 5:
          return "setVolume";
        case 6:
          return "adjustVolume";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IRemoteDisplayCallback _arg0 =
                  IRemoteDisplayCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setCallback(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              setDiscoveryMode(_arg02);
              return true;
            case 3:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              connect(_arg03);
              return true;
            case 4:
              String _arg04 = data.readString();
              data.enforceNoDataAvail();
              disconnect(_arg04);
              return true;
            case 5:
              String _arg05 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              setVolume(_arg05, _arg1);
              return true;
            case 6:
              String _arg06 = data.readString();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              adjustVolume(_arg06, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRemoteDisplayProvider {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.media.IRemoteDisplayProvider
      public void setCallback(IRemoteDisplayCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IRemoteDisplayProvider
      public void setDiscoveryMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IRemoteDisplayProvider
      public void connect(String id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IRemoteDisplayProvider
      public void disconnect(String id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IRemoteDisplayProvider
      public void setVolume(String id, int volume) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          _data.writeInt(volume);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IRemoteDisplayProvider
      public void adjustVolume(String id, int delta) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          _data.writeInt(delta);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
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
