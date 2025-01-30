package android.hardware.display;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IVirtualDisplayCallback extends IInterface {
  void onPaused() throws RemoteException;

  void onResumed() throws RemoteException;

  void onStopped() throws RemoteException;

  public static class Default implements IVirtualDisplayCallback {
    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onPaused() throws RemoteException {}

    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onResumed() throws RemoteException {}

    @Override // android.hardware.display.IVirtualDisplayCallback
    public void onStopped() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IVirtualDisplayCallback {
    public static final String DESCRIPTOR = "android.hardware.display.IVirtualDisplayCallback";
    static final int TRANSACTION_onPaused = 1;
    static final int TRANSACTION_onResumed = 2;
    static final int TRANSACTION_onStopped = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IVirtualDisplayCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IVirtualDisplayCallback)) {
        return (IVirtualDisplayCallback) iin;
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
          return "onPaused";
        case 2:
          return "onResumed";
        case 3:
          return "onStopped";
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
              onPaused();
              return true;
            case 2:
              onResumed();
              return true;
            case 3:
              onStopped();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IVirtualDisplayCallback {
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

      @Override // android.hardware.display.IVirtualDisplayCallback
      public void onPaused() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IVirtualDisplayCallback
      public void onResumed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IVirtualDisplayCallback
      public void onStopped() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
