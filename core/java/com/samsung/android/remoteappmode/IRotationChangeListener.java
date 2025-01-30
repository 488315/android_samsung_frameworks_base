package com.samsung.android.remoteappmode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IRotationChangeListener extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.remoteappmode.IRotationChangeListener";

  void onRotationChanged(int i, int i2) throws RemoteException;

  public static class Default implements IRotationChangeListener {
    @Override // com.samsung.android.remoteappmode.IRotationChangeListener
    public void onRotationChanged(int displayId, int rotation) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRotationChangeListener {
    static final int TRANSACTION_onRotationChanged = 1;

    public Stub() {
      attachInterface(this, IRotationChangeListener.DESCRIPTOR);
    }

    public static IRotationChangeListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRotationChangeListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IRotationChangeListener)) {
        return (IRotationChangeListener) iin;
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
          return "onRotationChanged";
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
        data.enforceInterface(IRotationChangeListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRotationChangeListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              onRotationChanged(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRotationChangeListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRotationChangeListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.remoteappmode.IRotationChangeListener
      public void onRotationChanged(int displayId, int rotation) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRotationChangeListener.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(rotation);
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
