package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITransientNotificationCallback extends IInterface {
  public static final String DESCRIPTOR = "android.app.ITransientNotificationCallback";

  void onToastHidden() throws RemoteException;

  void onToastShown() throws RemoteException;

  public static class Default implements ITransientNotificationCallback {
    @Override // android.app.ITransientNotificationCallback
    public void onToastShown() throws RemoteException {}

    @Override // android.app.ITransientNotificationCallback
    public void onToastHidden() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITransientNotificationCallback {
    static final int TRANSACTION_onToastHidden = 2;
    static final int TRANSACTION_onToastShown = 1;

    public Stub() {
      attachInterface(this, ITransientNotificationCallback.DESCRIPTOR);
    }

    public static ITransientNotificationCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITransientNotificationCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ITransientNotificationCallback)) {
        return (ITransientNotificationCallback) iin;
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
          return "onToastShown";
        case 2:
          return "onToastHidden";
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
        data.enforceInterface(ITransientNotificationCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITransientNotificationCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onToastShown();
              return true;
            case 2:
              onToastHidden();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITransientNotificationCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITransientNotificationCallback.DESCRIPTOR;
      }

      @Override // android.app.ITransientNotificationCallback
      public void onToastShown() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITransientNotificationCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.ITransientNotificationCallback
      public void onToastHidden() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITransientNotificationCallback.DESCRIPTOR);
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
