package android.media;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteVolumeObserver extends IInterface {
  void dispatchRemoteVolumeUpdate(int i, int i2) throws RemoteException;

  public static class Default implements IRemoteVolumeObserver {
    @Override // android.media.IRemoteVolumeObserver
    public void dispatchRemoteVolumeUpdate(int direction, int value) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRemoteVolumeObserver {
    public static final String DESCRIPTOR = "android.media.IRemoteVolumeObserver";
    static final int TRANSACTION_dispatchRemoteVolumeUpdate = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IRemoteVolumeObserver asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IRemoteVolumeObserver)) {
        return (IRemoteVolumeObserver) iin;
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
          return "dispatchRemoteVolumeUpdate";
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
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              dispatchRemoteVolumeUpdate(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRemoteVolumeObserver {
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

      @Override // android.media.IRemoteVolumeObserver
      public void dispatchRemoteVolumeUpdate(int direction, int value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(direction);
          _data.writeInt(value);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
