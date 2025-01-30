package com.samsung.android.ims.p038ft;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface SemImsFtListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.ims.ft.SemImsFtListener";

  void onFtStateChanged(boolean z) throws RemoteException;

  public static class Default implements SemImsFtListener {
    @Override // com.samsung.android.ims.p038ft.SemImsFtListener
    public void onFtStateChanged(boolean event) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements SemImsFtListener {
    static final int TRANSACTION_onFtStateChanged = 1;

    public Stub() {
      attachInterface(this, SemImsFtListener.DESCRIPTOR);
    }

    public static SemImsFtListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(SemImsFtListener.DESCRIPTOR);
      if (iin != null && (iin instanceof SemImsFtListener)) {
        return (SemImsFtListener) iin;
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
          return "onFtStateChanged";
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
        data.enforceInterface(SemImsFtListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(SemImsFtListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onFtStateChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements SemImsFtListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return SemImsFtListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.ims.p038ft.SemImsFtListener
      public void onFtStateChanged(boolean event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(SemImsFtListener.DESCRIPTOR);
          _data.writeBoolean(event);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
