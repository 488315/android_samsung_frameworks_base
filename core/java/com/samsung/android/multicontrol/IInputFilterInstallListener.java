package com.samsung.android.multicontrol;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IInputFilterInstallListener extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.multicontrol.IInputFilterInstallListener";

  void onInstalled() throws RemoteException;

  void onUninstalled() throws RemoteException;

  public static class Default implements IInputFilterInstallListener {
    @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
    public void onInstalled() throws RemoteException {}

    @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
    public void onUninstalled() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInputFilterInstallListener {
    static final int TRANSACTION_onInstalled = 1;
    static final int TRANSACTION_onUninstalled = 2;

    public Stub() {
      attachInterface(this, IInputFilterInstallListener.DESCRIPTOR);
    }

    public static IInputFilterInstallListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IInputFilterInstallListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IInputFilterInstallListener)) {
        return (IInputFilterInstallListener) iin;
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
          return "onInstalled";
        case 2:
          return "onUninstalled";
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
        data.enforceInterface(IInputFilterInstallListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IInputFilterInstallListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onInstalled();
              return true;
            case 2:
              onUninstalled();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInputFilterInstallListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IInputFilterInstallListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
      public void onInstalled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
      public void onUninstalled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
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
