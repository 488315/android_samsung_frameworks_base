package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssNavigationMessageListener extends IInterface {
  void onGnssNavigationMessageReceived(GnssNavigationMessage gnssNavigationMessage)
      throws RemoteException;

  void onStatusChanged(int i) throws RemoteException;

  public static class Default implements IGnssNavigationMessageListener {
    @Override // android.location.IGnssNavigationMessageListener
    public void onGnssNavigationMessageReceived(GnssNavigationMessage event)
        throws RemoteException {}

    @Override // android.location.IGnssNavigationMessageListener
    public void onStatusChanged(int status) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGnssNavigationMessageListener {
    public static final String DESCRIPTOR = "android.location.IGnssNavigationMessageListener";
    static final int TRANSACTION_onGnssNavigationMessageReceived = 1;
    static final int TRANSACTION_onStatusChanged = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IGnssNavigationMessageListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGnssNavigationMessageListener)) {
        return (IGnssNavigationMessageListener) iin;
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
          return "onGnssNavigationMessageReceived";
        case 2:
          return "onStatusChanged";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              GnssNavigationMessage _arg0 =
                  (GnssNavigationMessage) data.readTypedObject(GnssNavigationMessage.CREATOR);
              data.enforceNoDataAvail();
              onGnssNavigationMessageReceived(_arg0);
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

    private static class Proxy implements IGnssNavigationMessageListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.location.IGnssNavigationMessageListener
      public void onGnssNavigationMessageReceived(GnssNavigationMessage event)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(event, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.location.IGnssNavigationMessageListener
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

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
