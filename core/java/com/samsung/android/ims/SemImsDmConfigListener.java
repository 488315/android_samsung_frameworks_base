package com.samsung.android.ims;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface SemImsDmConfigListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsDmConfigListener";

  void onChangeDmValue(String str, boolean z) throws RemoteException;

  public static class Default implements SemImsDmConfigListener {
    @Override // com.samsung.android.ims.SemImsDmConfigListener
    public void onChangeDmValue(String uri, boolean state) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements SemImsDmConfigListener {
    static final int TRANSACTION_onChangeDmValue = 1;

    public Stub() {
      attachInterface(this, SemImsDmConfigListener.DESCRIPTOR);
    }

    public static SemImsDmConfigListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(SemImsDmConfigListener.DESCRIPTOR);
      if (iin != null && (iin instanceof SemImsDmConfigListener)) {
        return (SemImsDmConfigListener) iin;
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
          return "onChangeDmValue";
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
        data.enforceInterface(SemImsDmConfigListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(SemImsDmConfigListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              boolean _arg1 = data.readBoolean();
              data.enforceNoDataAvail();
              onChangeDmValue(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements SemImsDmConfigListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return SemImsDmConfigListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.ims.SemImsDmConfigListener
      public void onChangeDmValue(String uri, boolean state) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(SemImsDmConfigListener.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeBoolean(state);
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
