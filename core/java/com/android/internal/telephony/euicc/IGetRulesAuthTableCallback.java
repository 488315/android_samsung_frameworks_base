package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.EuiccRulesAuthTable;

/* loaded from: classes5.dex */
public interface IGetRulesAuthTableCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.android.internal.telephony.euicc.IGetRulesAuthTableCallback";

  void onComplete(int i, EuiccRulesAuthTable euiccRulesAuthTable) throws RemoteException;

  public static class Default implements IGetRulesAuthTableCallback {
    @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
    public void onComplete(int resultCode, EuiccRulesAuthTable rat) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGetRulesAuthTableCallback {
    static final int TRANSACTION_onComplete = 1;

    public Stub() {
      attachInterface(this, IGetRulesAuthTableCallback.DESCRIPTOR);
    }

    public static IGetRulesAuthTableCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IGetRulesAuthTableCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IGetRulesAuthTableCallback)) {
        return (IGetRulesAuthTableCallback) iin;
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
          return "onComplete";
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
        data.enforceInterface(IGetRulesAuthTableCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IGetRulesAuthTableCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              EuiccRulesAuthTable _arg1 =
                  (EuiccRulesAuthTable) data.readTypedObject(EuiccRulesAuthTable.CREATOR);
              data.enforceNoDataAvail();
              onComplete(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGetRulesAuthTableCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IGetRulesAuthTableCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
      public void onComplete(int resultCode, EuiccRulesAuthTable rat) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IGetRulesAuthTableCallback.DESCRIPTOR);
          _data.writeInt(resultCode);
          _data.writeTypedObject(rat, 0);
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
