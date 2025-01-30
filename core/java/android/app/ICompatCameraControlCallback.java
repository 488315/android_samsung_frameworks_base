package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICompatCameraControlCallback extends IInterface {
  public static final String DESCRIPTOR = "android.app.ICompatCameraControlCallback";

  void applyCameraCompatTreatment() throws RemoteException;

  void revertCameraCompatTreatment() throws RemoteException;

  public static class Default implements ICompatCameraControlCallback {
    @Override // android.app.ICompatCameraControlCallback
    public void applyCameraCompatTreatment() throws RemoteException {}

    @Override // android.app.ICompatCameraControlCallback
    public void revertCameraCompatTreatment() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICompatCameraControlCallback {
    static final int TRANSACTION_applyCameraCompatTreatment = 1;
    static final int TRANSACTION_revertCameraCompatTreatment = 2;

    public Stub() {
      attachInterface(this, ICompatCameraControlCallback.DESCRIPTOR);
    }

    public static ICompatCameraControlCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICompatCameraControlCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ICompatCameraControlCallback)) {
        return (ICompatCameraControlCallback) iin;
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
          return "applyCameraCompatTreatment";
        case 2:
          return "revertCameraCompatTreatment";
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
        data.enforceInterface(ICompatCameraControlCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICompatCameraControlCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              applyCameraCompatTreatment();
              return true;
            case 2:
              revertCameraCompatTreatment();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICompatCameraControlCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICompatCameraControlCallback.DESCRIPTOR;
      }

      @Override // android.app.ICompatCameraControlCallback
      public void applyCameraCompatTreatment() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICompatCameraControlCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.ICompatCameraControlCallback
      public void revertCameraCompatTreatment() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICompatCameraControlCallback.DESCRIPTOR);
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
