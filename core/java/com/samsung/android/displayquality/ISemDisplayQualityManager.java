package com.samsung.android.displayquality;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemDisplayQualityManager extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.displayquality.ISemDisplayQualityManager";

  void enhanceDisplayOutdoorVisibilityByLux(int i) throws RemoteException;

  void setAdaptiveSync(boolean z) throws RemoteException;

  public static class Default implements ISemDisplayQualityManager {
    @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
    public void enhanceDisplayOutdoorVisibilityByLux(int lux) throws RemoteException {}

    @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
    public void setAdaptiveSync(boolean enable) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemDisplayQualityManager {
    static final int TRANSACTION_enhanceDisplayOutdoorVisibilityByLux = 1;
    static final int TRANSACTION_setAdaptiveSync = 2;

    public Stub() {
      attachInterface(this, ISemDisplayQualityManager.DESCRIPTOR);
    }

    public static ISemDisplayQualityManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemDisplayQualityManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemDisplayQualityManager)) {
        return (ISemDisplayQualityManager) iin;
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
          return "enhanceDisplayOutdoorVisibilityByLux";
        case 2:
          return "setAdaptiveSync";
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
        data.enforceInterface(ISemDisplayQualityManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemDisplayQualityManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              enhanceDisplayOutdoorVisibilityByLux(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              boolean _arg02 = data.readBoolean();
              data.enforceNoDataAvail();
              setAdaptiveSync(_arg02);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemDisplayQualityManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemDisplayQualityManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
      public void enhanceDisplayOutdoorVisibilityByLux(int lux) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDisplayQualityManager.DESCRIPTOR);
          _data.writeInt(lux);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
      public void setAdaptiveSync(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDisplayQualityManager.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
