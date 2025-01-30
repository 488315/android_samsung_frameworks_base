package android.sec.enterprise.proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProxyCredentialsCallback extends IInterface {
  public static final String DESCRIPTOR = "android.sec.enterprise.proxy.IProxyCredentialsCallback";

  void onAuthenticationResult(int i) throws RemoteException;

  public static class Default implements IProxyCredentialsCallback {
    @Override // android.sec.enterprise.proxy.IProxyCredentialsCallback
    public void onAuthenticationResult(int result) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IProxyCredentialsCallback {
    static final int TRANSACTION_onAuthenticationResult = 1;

    public Stub() {
      attachInterface(this, IProxyCredentialsCallback.DESCRIPTOR);
    }

    public static IProxyCredentialsCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IProxyCredentialsCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IProxyCredentialsCallback)) {
        return (IProxyCredentialsCallback) iin;
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
          return "onAuthenticationResult";
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
        data.enforceInterface(IProxyCredentialsCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IProxyCredentialsCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onAuthenticationResult(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IProxyCredentialsCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IProxyCredentialsCallback.DESCRIPTOR;
      }

      @Override // android.sec.enterprise.proxy.IProxyCredentialsCallback
      public void onAuthenticationResult(int result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IProxyCredentialsCallback.DESCRIPTOR);
          _data.writeInt(result);
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
