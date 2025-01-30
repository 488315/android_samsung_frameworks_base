package com.android.internal.telephony.euicc;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IDisableProfileCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.android.internal.telephony.euicc.IDisableProfileCallback";

  void onComplete(int i) throws RemoteException;

  public static class Default implements IDisableProfileCallback {
    @Override // com.android.internal.telephony.euicc.IDisableProfileCallback
    public void onComplete(int resultCode) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDisableProfileCallback {
    static final int TRANSACTION_onComplete = 1;

    public Stub() {
      attachInterface(this, IDisableProfileCallback.DESCRIPTOR);
    }

    public static IDisableProfileCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDisableProfileCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IDisableProfileCallback)) {
        return (IDisableProfileCallback) iin;
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
          return "onComplete";
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
        data.enforceInterface(IDisableProfileCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDisableProfileCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onComplete(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDisableProfileCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDisableProfileCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.telephony.euicc.IDisableProfileCallback
      public void onComplete(int resultCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDisableProfileCallback.DESCRIPTOR);
          _data.writeInt(resultCode);
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
