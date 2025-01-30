package com.samsung.android.gamesdk;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IGameSDKStateListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.gamesdk.IGameSDKStateListener";

  void onGameSDKFinalized() throws RemoteException;

  void onGameSDKInitialized() throws RemoteException;

  public static class Default implements IGameSDKStateListener {
    @Override // com.samsung.android.gamesdk.IGameSDKStateListener
    public void onGameSDKInitialized() throws RemoteException {}

    @Override // com.samsung.android.gamesdk.IGameSDKStateListener
    public void onGameSDKFinalized() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGameSDKStateListener {
    static final int TRANSACTION_onGameSDKFinalized = 2;
    static final int TRANSACTION_onGameSDKInitialized = 1;

    public Stub() {
      attachInterface(this, IGameSDKStateListener.DESCRIPTOR);
    }

    public static IGameSDKStateListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IGameSDKStateListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IGameSDKStateListener)) {
        return (IGameSDKStateListener) iin;
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
          return "onGameSDKInitialized";
        case 2:
          return "onGameSDKFinalized";
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
        data.enforceInterface(IGameSDKStateListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IGameSDKStateListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onGameSDKInitialized();
              return true;
            case 2:
              onGameSDKFinalized();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGameSDKStateListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IGameSDKStateListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.gamesdk.IGameSDKStateListener
      public void onGameSDKInitialized() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IGameSDKStateListener.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKStateListener
      public void onGameSDKFinalized() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IGameSDKStateListener.DESCRIPTOR);
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
