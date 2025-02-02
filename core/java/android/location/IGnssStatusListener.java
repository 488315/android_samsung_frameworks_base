package android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssStatusListener extends IInterface {
  void onFirstFix(int i) throws RemoteException;

  void onGnssStarted() throws RemoteException;

  void onGnssStopped() throws RemoteException;

  void onSvStatusChanged(GnssStatus gnssStatus) throws RemoteException;

  public static class Default implements IGnssStatusListener {
    @Override // android.location.IGnssStatusListener
    public void onGnssStarted() throws RemoteException {}

    @Override // android.location.IGnssStatusListener
    public void onGnssStopped() throws RemoteException {}

    @Override // android.location.IGnssStatusListener
    public void onFirstFix(int ttff) throws RemoteException {}

    @Override // android.location.IGnssStatusListener
    public void onSvStatusChanged(GnssStatus gnssStatus) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGnssStatusListener {
    public static final String DESCRIPTOR = "android.location.IGnssStatusListener";
    static final int TRANSACTION_onFirstFix = 3;
    static final int TRANSACTION_onGnssStarted = 1;
    static final int TRANSACTION_onGnssStopped = 2;
    static final int TRANSACTION_onSvStatusChanged = 4;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IGnssStatusListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGnssStatusListener)) {
        return (IGnssStatusListener) iin;
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
          return "onGnssStarted";
        case 2:
          return "onGnssStopped";
        case 3:
          return "onFirstFix";
        case 4:
          return "onSvStatusChanged";
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
              onGnssStarted();
              return true;
            case 2:
              onGnssStopped();
              return true;
            case 3:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onFirstFix(_arg0);
              return true;
            case 4:
              GnssStatus _arg02 = (GnssStatus) data.readTypedObject(GnssStatus.CREATOR);
              data.enforceNoDataAvail();
              onSvStatusChanged(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGnssStatusListener {
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

      @Override // android.location.IGnssStatusListener
      public void onGnssStarted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.location.IGnssStatusListener
      public void onGnssStopped() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.location.IGnssStatusListener
      public void onFirstFix(int ttff) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(ttff);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.location.IGnssStatusListener
      public void onSvStatusChanged(GnssStatus gnssStatus) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(gnssStatus, 0);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
