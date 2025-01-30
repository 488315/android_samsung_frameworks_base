package android.hardware.camera2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICameraInjectionSession extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.camera2.ICameraInjectionSession";

  void stopInjection() throws RemoteException;

  public static class Default implements ICameraInjectionSession {
    @Override // android.hardware.camera2.ICameraInjectionSession
    public void stopInjection() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICameraInjectionSession {
    static final int TRANSACTION_stopInjection = 1;

    public Stub() {
      attachInterface(this, ICameraInjectionSession.DESCRIPTOR);
    }

    public static ICameraInjectionSession asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICameraInjectionSession.DESCRIPTOR);
      if (iin != null && (iin instanceof ICameraInjectionSession)) {
        return (ICameraInjectionSession) iin;
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
          return "stopInjection";
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
        data.enforceInterface(ICameraInjectionSession.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICameraInjectionSession.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              stopInjection();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICameraInjectionSession {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICameraInjectionSession.DESCRIPTOR;
      }

      @Override // android.hardware.camera2.ICameraInjectionSession
      public void stopInjection() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICameraInjectionSession.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
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
