package android.content.pm;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageMoveObserver extends IInterface {
  void onCreated(int i, Bundle bundle) throws RemoteException;

  void onStatusChanged(int i, int i2, long j) throws RemoteException;

  public static class Default implements IPackageMoveObserver {
    @Override // android.content.pm.IPackageMoveObserver
    public void onCreated(int moveId, Bundle extras) throws RemoteException {}

    @Override // android.content.pm.IPackageMoveObserver
    public void onStatusChanged(int moveId, int status, long estMillis) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IPackageMoveObserver {
    public static final String DESCRIPTOR = "android.content.pm.IPackageMoveObserver";
    static final int TRANSACTION_onCreated = 1;
    static final int TRANSACTION_onStatusChanged = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IPackageMoveObserver asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IPackageMoveObserver)) {
        return (IPackageMoveObserver) iin;
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
          return "onCreated";
        case 2:
          return "onStatusChanged";
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
              int _arg0 = data.readInt();
              Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onCreated(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              long _arg2 = data.readLong();
              data.enforceNoDataAvail();
              onStatusChanged(_arg02, _arg12, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IPackageMoveObserver {
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

      @Override // android.content.pm.IPackageMoveObserver
      public void onCreated(int moveId, Bundle extras) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(moveId);
          _data.writeTypedObject(extras, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.content.pm.IPackageMoveObserver
      public void onStatusChanged(int moveId, int status, long estMillis) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(moveId);
          _data.writeInt(status);
          _data.writeLong(estMillis);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
