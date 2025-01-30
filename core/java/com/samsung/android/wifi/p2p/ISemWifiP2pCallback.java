package com.samsung.android.wifi.p2p;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiP2pCallback extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.wifi.p2p.ISemWifiP2pCallback";

  void onFailure(int i) throws RemoteException;

  void onSuccess() throws RemoteException;

  public static class Default implements ISemWifiP2pCallback {
    @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
    public void onSuccess() throws RemoteException {}

    @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
    public void onFailure(int reason) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemWifiP2pCallback {
    static final int TRANSACTION_onFailure = 2;
    static final int TRANSACTION_onSuccess = 1;

    public Stub() {
      attachInterface(this, ISemWifiP2pCallback.DESCRIPTOR);
    }

    public static ISemWifiP2pCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemWifiP2pCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemWifiP2pCallback)) {
        return (ISemWifiP2pCallback) iin;
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
          return "onSuccess";
        case 2:
          return "onFailure";
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
        data.enforceInterface(ISemWifiP2pCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemWifiP2pCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onSuccess();
              return true;
            case 2:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onFailure(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemWifiP2pCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemWifiP2pCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
      public void onSuccess() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemWifiP2pCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
      public void onFailure(int reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemWifiP2pCallback.DESCRIPTOR);
          _data.writeInt(reason);
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
