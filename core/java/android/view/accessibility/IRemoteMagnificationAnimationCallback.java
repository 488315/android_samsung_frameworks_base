package android.view.accessibility;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface IRemoteMagnificationAnimationCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.view.accessibility.IRemoteMagnificationAnimationCallback";

  void onResult(boolean z) throws RemoteException;

  public static class Default implements IRemoteMagnificationAnimationCallback {
    @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
    public void onResult(boolean success) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder
      implements IRemoteMagnificationAnimationCallback {
    static final int TRANSACTION_onResult = 1;

    public Stub() {
      attachInterface(this, IRemoteMagnificationAnimationCallback.DESCRIPTOR);
    }

    public static IRemoteMagnificationAnimationCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IRemoteMagnificationAnimationCallback)) {
        return (IRemoteMagnificationAnimationCallback) iin;
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
          return "onResult";
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
        data.enforceInterface(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onResult(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRemoteMagnificationAnimationCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRemoteMagnificationAnimationCallback.DESCRIPTOR;
      }

      @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
      public void onResult(boolean success) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRemoteMagnificationAnimationCallback.DESCRIPTOR);
          _data.writeBoolean(success);
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
