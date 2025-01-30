package android.hardware.biometrics;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IBiometricContextListener extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricContextListener";

  public @interface FoldState {
    public static final int FULLY_CLOSED = 3;
    public static final int FULLY_OPENED = 2;
    public static final int HALF_OPENED = 1;
    public static final int UNKNOWN = 0;
  }

  void onDisplayStateChanged(int i) throws RemoteException;

  void onFoldChanged(int i) throws RemoteException;

  public static class Default implements IBiometricContextListener {
    @Override // android.hardware.biometrics.IBiometricContextListener
    public void onFoldChanged(int FoldState) throws RemoteException {}

    @Override // android.hardware.biometrics.IBiometricContextListener
    public void onDisplayStateChanged(int displayState) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBiometricContextListener {
    static final int TRANSACTION_onDisplayStateChanged = 2;
    static final int TRANSACTION_onFoldChanged = 1;

    public Stub() {
      attachInterface(this, IBiometricContextListener.DESCRIPTOR);
    }

    public static IBiometricContextListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IBiometricContextListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IBiometricContextListener)) {
        return (IBiometricContextListener) iin;
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
          return "onFoldChanged";
        case 2:
          return "onDisplayStateChanged";
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
        data.enforceInterface(IBiometricContextListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IBiometricContextListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onFoldChanged(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onDisplayStateChanged(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBiometricContextListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IBiometricContextListener.DESCRIPTOR;
      }

      @Override // android.hardware.biometrics.IBiometricContextListener
      public void onFoldChanged(int FoldState) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
          _data.writeInt(FoldState);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.IBiometricContextListener
      public void onDisplayStateChanged(int displayState) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
          _data.writeInt(displayState);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
