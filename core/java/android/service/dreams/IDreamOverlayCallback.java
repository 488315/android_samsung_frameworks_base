package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDreamOverlayCallback extends IInterface {
  public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayCallback";

  void onExitRequested() throws RemoteException;

  public static class Default implements IDreamOverlayCallback {
    @Override // android.service.dreams.IDreamOverlayCallback
    public void onExitRequested() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDreamOverlayCallback {
    static final int TRANSACTION_onExitRequested = 1;

    public Stub() {
      attachInterface(this, IDreamOverlayCallback.DESCRIPTOR);
    }

    public static IDreamOverlayCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDreamOverlayCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IDreamOverlayCallback)) {
        return (IDreamOverlayCallback) iin;
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
          return "onExitRequested";
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
        data.enforceInterface(IDreamOverlayCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDreamOverlayCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onExitRequested();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDreamOverlayCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDreamOverlayCallback.DESCRIPTOR;
      }

      @Override // android.service.dreams.IDreamOverlayCallback
      public void onExitRequested() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDreamOverlayCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
