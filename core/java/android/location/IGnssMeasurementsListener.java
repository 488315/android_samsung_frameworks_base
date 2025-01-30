package android.location;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssMeasurementsListener extends IInterface {
  void onGnssMeasurementsReceived(GnssMeasurementsEvent gnssMeasurementsEvent)
      throws RemoteException;

  void onStatusChanged(int i) throws RemoteException;

  public static class Default implements IGnssMeasurementsListener {
    @Override // android.location.IGnssMeasurementsListener
    public void onGnssMeasurementsReceived(GnssMeasurementsEvent event) throws RemoteException {}

    @Override // android.location.IGnssMeasurementsListener
    public void onStatusChanged(int status) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGnssMeasurementsListener {
    public static final String DESCRIPTOR = "android.location.IGnssMeasurementsListener";
    static final int TRANSACTION_onGnssMeasurementsReceived = 1;
    static final int TRANSACTION_onStatusChanged = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IGnssMeasurementsListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGnssMeasurementsListener)) {
        return (IGnssMeasurementsListener) iin;
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
          return "onGnssMeasurementsReceived";
        case 2:
          return "onStatusChanged";
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
              GnssMeasurementsEvent _arg0 =
                  (GnssMeasurementsEvent) data.readTypedObject(GnssMeasurementsEvent.CREATOR);
              data.enforceNoDataAvail();
              onGnssMeasurementsReceived(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onStatusChanged(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGnssMeasurementsListener {
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

      @Override // android.location.IGnssMeasurementsListener
      public void onGnssMeasurementsReceived(GnssMeasurementsEvent event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(event, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.location.IGnssMeasurementsListener
      public void onStatusChanged(int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(status);
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
