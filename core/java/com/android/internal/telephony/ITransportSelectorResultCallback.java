package com.android.internal.telephony;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ITransportSelectorResultCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.android.internal.telephony.ITransportSelectorResultCallback";

  void onCompleted(IWwanSelectorCallback iWwanSelectorCallback) throws RemoteException;

  public static class Default implements ITransportSelectorResultCallback {
    @Override // com.android.internal.telephony.ITransportSelectorResultCallback
    public void onCompleted(IWwanSelectorCallback cb) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITransportSelectorResultCallback {
    static final int TRANSACTION_onCompleted = 1;

    public Stub() {
      attachInterface(this, ITransportSelectorResultCallback.DESCRIPTOR);
    }

    public static ITransportSelectorResultCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITransportSelectorResultCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ITransportSelectorResultCallback)) {
        return (ITransportSelectorResultCallback) iin;
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
          return "onCompleted";
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
        data.enforceInterface(ITransportSelectorResultCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITransportSelectorResultCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IWwanSelectorCallback _arg0 =
                  IWwanSelectorCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onCompleted(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITransportSelectorResultCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITransportSelectorResultCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.telephony.ITransportSelectorResultCallback
      public void onCompleted(IWwanSelectorCallback cb) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITransportSelectorResultCallback.DESCRIPTOR);
          _data.writeStrongInterface(cb);
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
