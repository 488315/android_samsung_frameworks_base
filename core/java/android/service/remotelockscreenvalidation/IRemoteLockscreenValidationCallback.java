package android.service.remotelockscreenvalidation;

import android.app.RemoteLockscreenValidationResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback";

  void onFailure(String str) throws RemoteException;

  void onSuccess(RemoteLockscreenValidationResult remoteLockscreenValidationResult)
      throws RemoteException;

  public static class Default implements IRemoteLockscreenValidationCallback {
    @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
    public void onSuccess(RemoteLockscreenValidationResult result) throws RemoteException {}

    @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
    public void onFailure(String message) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRemoteLockscreenValidationCallback {
    static final int TRANSACTION_onFailure = 2;
    static final int TRANSACTION_onSuccess = 1;

    public Stub() {
      attachInterface(this, IRemoteLockscreenValidationCallback.DESCRIPTOR);
    }

    public static IRemoteLockscreenValidationCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRemoteLockscreenValidationCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IRemoteLockscreenValidationCallback)) {
        return (IRemoteLockscreenValidationCallback) iin;
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
          return "onSuccess";
        case 2:
          return "onFailure";
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
        data.enforceInterface(IRemoteLockscreenValidationCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRemoteLockscreenValidationCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              RemoteLockscreenValidationResult _arg0 =
                  (RemoteLockscreenValidationResult)
                      data.readTypedObject(RemoteLockscreenValidationResult.CREATOR);
              data.enforceNoDataAvail();
              onSuccess(_arg0);
              return true;
            case 2:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              onFailure(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRemoteLockscreenValidationCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRemoteLockscreenValidationCallback.DESCRIPTOR;
      }

      @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
      public void onSuccess(RemoteLockscreenValidationResult result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRemoteLockscreenValidationCallback.DESCRIPTOR);
          _data.writeTypedObject(result, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback
      public void onFailure(String message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRemoteLockscreenValidationCallback.DESCRIPTOR);
          _data.writeString(message);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
