package android.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IRequestFinishCallback extends IInterface {
  public static final String DESCRIPTOR = "android.app.IRequestFinishCallback";

  void requestFinish() throws RemoteException;

  public static class Default implements IRequestFinishCallback {
    @Override // android.app.IRequestFinishCallback
    public void requestFinish() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRequestFinishCallback {
    static final int TRANSACTION_requestFinish = 1;

    public Stub() {
      attachInterface(this, IRequestFinishCallback.DESCRIPTOR);
    }

    public static IRequestFinishCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRequestFinishCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IRequestFinishCallback)) {
        return (IRequestFinishCallback) iin;
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
          return "requestFinish";
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
        data.enforceInterface(IRequestFinishCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRequestFinishCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              requestFinish();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRequestFinishCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRequestFinishCallback.DESCRIPTOR;
      }

      @Override // android.app.IRequestFinishCallback
      public void requestFinish() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRequestFinishCallback.DESCRIPTOR);
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
