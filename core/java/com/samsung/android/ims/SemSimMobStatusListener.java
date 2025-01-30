package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface SemSimMobStatusListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.ims.SemSimMobStatusListener";

  void onSimMobilityStateChanged(boolean z) throws RemoteException;

  public static class Default implements SemSimMobStatusListener {
    @Override // com.samsung.android.ims.SemSimMobStatusListener
    public void onSimMobilityStateChanged(boolean simMobility) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements SemSimMobStatusListener {
    static final int TRANSACTION_onSimMobilityStateChanged = 1;

    public Stub() {
      attachInterface(this, SemSimMobStatusListener.DESCRIPTOR);
    }

    public static SemSimMobStatusListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(SemSimMobStatusListener.DESCRIPTOR);
      if (iin != null && (iin instanceof SemSimMobStatusListener)) {
        return (SemSimMobStatusListener) iin;
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
          return "onSimMobilityStateChanged";
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
        data.enforceInterface(SemSimMobStatusListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(SemSimMobStatusListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onSimMobilityStateChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements SemSimMobStatusListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return SemSimMobStatusListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.ims.SemSimMobStatusListener
      public void onSimMobilityStateChanged(boolean simMobility) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(SemSimMobStatusListener.DESCRIPTOR);
          _data.writeBoolean(simMobility);
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
