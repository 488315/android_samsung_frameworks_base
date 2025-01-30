package com.samsung.android.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISCurrentLocListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.location.ISCurrentLocListener";

  void onCurrentLocation(Location location) throws RemoteException;

  public static class Default implements ISCurrentLocListener {
    @Override // com.samsung.android.location.ISCurrentLocListener
    public void onCurrentLocation(Location location) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISCurrentLocListener {
    static final int TRANSACTION_onCurrentLocation = 1;

    public Stub() {
      attachInterface(this, ISCurrentLocListener.DESCRIPTOR);
    }

    public static ISCurrentLocListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISCurrentLocListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISCurrentLocListener)) {
        return (ISCurrentLocListener) iin;
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
          return "onCurrentLocation";
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
        data.enforceInterface(ISCurrentLocListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISCurrentLocListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              Location _arg0 = (Location) data.readTypedObject(Location.CREATOR);
              data.enforceNoDataAvail();
              onCurrentLocation(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISCurrentLocListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISCurrentLocListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.location.ISCurrentLocListener
      public void onCurrentLocation(Location location) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISCurrentLocListener.DESCRIPTOR);
          _data.writeTypedObject(location, 0);
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
