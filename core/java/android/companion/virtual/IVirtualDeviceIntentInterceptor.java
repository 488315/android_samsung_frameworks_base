package android.companion.virtual;

import android.content.Intent;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IVirtualDeviceIntentInterceptor extends IInterface {
  public static final String DESCRIPTOR =
      "android.companion.virtual.IVirtualDeviceIntentInterceptor";

  void onIntentIntercepted(Intent intent) throws RemoteException;

  public static class Default implements IVirtualDeviceIntentInterceptor {
    @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
    public void onIntentIntercepted(Intent intent) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IVirtualDeviceIntentInterceptor {
    static final int TRANSACTION_onIntentIntercepted = 1;

    public Stub() {
      attachInterface(this, IVirtualDeviceIntentInterceptor.DESCRIPTOR);
    }

    public static IVirtualDeviceIntentInterceptor asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
      if (iin != null && (iin instanceof IVirtualDeviceIntentInterceptor)) {
        return (IVirtualDeviceIntentInterceptor) iin;
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
          return "onIntentIntercepted";
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
        data.enforceInterface(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
              data.enforceNoDataAvail();
              onIntentIntercepted(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IVirtualDeviceIntentInterceptor {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IVirtualDeviceIntentInterceptor.DESCRIPTOR;
      }

      @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
      public void onIntentIntercepted(Intent intent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
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
