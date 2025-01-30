package com.samsung.android.remoteappmode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISecureAppChangedListener extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.remoteappmode.ISecureAppChangedListener";

  void onSecuredAppLaunched(int i, String str) throws RemoteException;

  void onSecuredAppRemoved(int i, String str) throws RemoteException;

  public static class Default implements ISecureAppChangedListener {
    @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
    public void onSecuredAppLaunched(int taskId, String packageName) throws RemoteException {}

    @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
    public void onSecuredAppRemoved(int taskId, String packageName) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISecureAppChangedListener {
    static final int TRANSACTION_onSecuredAppLaunched = 1;
    static final int TRANSACTION_onSecuredAppRemoved = 2;

    public Stub() {
      attachInterface(this, ISecureAppChangedListener.DESCRIPTOR);
    }

    public static ISecureAppChangedListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISecureAppChangedListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISecureAppChangedListener)) {
        return (ISecureAppChangedListener) iin;
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
          return "onSecuredAppLaunched";
        case 2:
          return "onSecuredAppRemoved";
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
        data.enforceInterface(ISecureAppChangedListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISecureAppChangedListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              onSecuredAppLaunched(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              onSecuredAppRemoved(_arg02, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISecureAppChangedListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISecureAppChangedListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
      public void onSecuredAppLaunched(int taskId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISecureAppChangedListener.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeString(packageName);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
      public void onSecuredAppRemoved(int taskId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISecureAppChangedListener.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeString(packageName);
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
