package android.hardware.biometrics;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IBiometricStateListener extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricStateListener";

  void onBiometricAction(int i) throws RemoteException;

  void onEnrollmentsChanged(int i, int i2, boolean z) throws RemoteException;

  void onStateChanged(int i) throws RemoteException;

  public static class Default implements IBiometricStateListener {
    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onStateChanged(int newState) throws RemoteException {}

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onBiometricAction(int action) throws RemoteException {}

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onEnrollmentsChanged(int userId, int sensorId, boolean hasEnrollments)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBiometricStateListener {
    static final int TRANSACTION_onBiometricAction = 2;
    static final int TRANSACTION_onEnrollmentsChanged = 3;
    static final int TRANSACTION_onStateChanged = 1;

    public Stub() {
      attachInterface(this, IBiometricStateListener.DESCRIPTOR);
    }

    public static IBiometricStateListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IBiometricStateListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IBiometricStateListener)) {
        return (IBiometricStateListener) iin;
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
          return "onStateChanged";
        case 2:
          return "onBiometricAction";
        case 3:
          return "onEnrollmentsChanged";
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
        data.enforceInterface(IBiometricStateListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IBiometricStateListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onStateChanged(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onBiometricAction(_arg02);
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg1 = data.readInt();
              boolean _arg2 = data.readBoolean();
              data.enforceNoDataAvail();
              onEnrollmentsChanged(_arg03, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBiometricStateListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IBiometricStateListener.DESCRIPTOR;
      }

      @Override // android.hardware.biometrics.IBiometricStateListener
      public void onStateChanged(int newState) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
          _data.writeInt(newState);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.IBiometricStateListener
      public void onBiometricAction(int action) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
          _data.writeInt(action);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.IBiometricStateListener
      public void onEnrollmentsChanged(int userId, int sensorId, boolean hasEnrollments)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(sensorId);
          _data.writeBoolean(hasEnrollments);
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
