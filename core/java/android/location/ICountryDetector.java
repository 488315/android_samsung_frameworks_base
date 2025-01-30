package android.location;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ICountryDetector extends IInterface {
  void addCountryListener(ICountryListener iCountryListener) throws RemoteException;

  Country detectCountry() throws RemoteException;

  void removeCountryListener(ICountryListener iCountryListener) throws RemoteException;

  public static class Default implements ICountryDetector {
    @Override // android.location.ICountryDetector
    public Country detectCountry() throws RemoteException {
      return null;
    }

    @Override // android.location.ICountryDetector
    public void addCountryListener(ICountryListener listener) throws RemoteException {}

    @Override // android.location.ICountryDetector
    public void removeCountryListener(ICountryListener listener) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICountryDetector {
    public static final String DESCRIPTOR = "android.location.ICountryDetector";
    static final int TRANSACTION_addCountryListener = 2;
    static final int TRANSACTION_detectCountry = 1;
    static final int TRANSACTION_removeCountryListener = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ICountryDetector asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ICountryDetector)) {
        return (ICountryDetector) iin;
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
          return "detectCountry";
        case 2:
          return "addCountryListener";
        case 3:
          return "removeCountryListener";
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
              Country _result = detectCountry();
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              ICountryListener _arg0 = ICountryListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              addCountryListener(_arg0);
              reply.writeNoException();
              return true;
            case 3:
              ICountryListener _arg02 = ICountryListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removeCountryListener(_arg02);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICountryDetector {
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

      @Override // android.location.ICountryDetector
      public Country detectCountry() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          Country _result = (Country) _reply.readTypedObject(Country.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.ICountryDetector
      public void addCountryListener(ICountryListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.ICountryDetector
      public void removeCountryListener(ICountryListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
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
