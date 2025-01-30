package android.media.tv.interactive;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppManagerCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.media.tv.interactive.ITvInteractiveAppManagerCallback";

  void onInteractiveAppServiceAdded(String str) throws RemoteException;

  void onInteractiveAppServiceRemoved(String str) throws RemoteException;

  void onInteractiveAppServiceUpdated(String str) throws RemoteException;

  void onStateChanged(String str, int i, int i2, int i3) throws RemoteException;

  void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo)
      throws RemoteException;

  public static class Default implements ITvInteractiveAppManagerCallback {
    @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
    public void onInteractiveAppServiceAdded(String iAppServiceId) throws RemoteException {}

    @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
    public void onInteractiveAppServiceRemoved(String iAppServiceId) throws RemoteException {}

    @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
    public void onInteractiveAppServiceUpdated(String iAppServiceId) throws RemoteException {}

    @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
    public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvIAppInfo)
        throws RemoteException {}

    @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
    public void onStateChanged(String iAppServiceId, int type, int state, int err)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITvInteractiveAppManagerCallback {
    static final int TRANSACTION_onInteractiveAppServiceAdded = 1;
    static final int TRANSACTION_onInteractiveAppServiceRemoved = 2;
    static final int TRANSACTION_onInteractiveAppServiceUpdated = 3;
    static final int TRANSACTION_onStateChanged = 5;
    static final int TRANSACTION_onTvInteractiveAppServiceInfoUpdated = 4;

    public Stub() {
      attachInterface(this, ITvInteractiveAppManagerCallback.DESCRIPTOR);
    }

    public static ITvInteractiveAppManagerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITvInteractiveAppManagerCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ITvInteractiveAppManagerCallback)) {
        return (ITvInteractiveAppManagerCallback) iin;
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
          return "onInteractiveAppServiceAdded";
        case 2:
          return "onInteractiveAppServiceRemoved";
        case 3:
          return "onInteractiveAppServiceUpdated";
        case 4:
          return "onTvInteractiveAppServiceInfoUpdated";
        case 5:
          return "onStateChanged";
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
        data.enforceInterface(ITvInteractiveAppManagerCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              onInteractiveAppServiceAdded(_arg0);
              return true;
            case 2:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              onInteractiveAppServiceRemoved(_arg02);
              return true;
            case 3:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              onInteractiveAppServiceUpdated(_arg03);
              return true;
            case 4:
              TvInteractiveAppServiceInfo _arg04 =
                  (TvInteractiveAppServiceInfo)
                      data.readTypedObject(TvInteractiveAppServiceInfo.CREATOR);
              data.enforceNoDataAvail();
              onTvInteractiveAppServiceInfoUpdated(_arg04);
              return true;
            case 5:
              String _arg05 = data.readString();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              onStateChanged(_arg05, _arg1, _arg2, _arg3);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITvInteractiveAppManagerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITvInteractiveAppManagerCallback.DESCRIPTOR;
      }

      @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
      public void onInteractiveAppServiceAdded(String iAppServiceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          _data.writeString(iAppServiceId);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
      public void onInteractiveAppServiceRemoved(String iAppServiceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          _data.writeString(iAppServiceId);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
      public void onInteractiveAppServiceUpdated(String iAppServiceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          _data.writeString(iAppServiceId);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
      public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvIAppInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          _data.writeTypedObject(tvIAppInfo, 0);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
      public void onStateChanged(String iAppServiceId, int type, int state, int err)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITvInteractiveAppManagerCallback.DESCRIPTOR);
          _data.writeString(iAppServiceId);
          _data.writeInt(type);
          _data.writeInt(state);
          _data.writeInt(err);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
