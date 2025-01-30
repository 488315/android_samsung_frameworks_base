package android.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IUiModeManagerCallback extends IInterface {
  public static final String DESCRIPTOR = "android.app.IUiModeManagerCallback";

  void notifyContrastChanged(float f) throws RemoteException;

  public static class Default implements IUiModeManagerCallback {
    @Override // android.app.IUiModeManagerCallback
    public void notifyContrastChanged(float contrast) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUiModeManagerCallback {
    static final int TRANSACTION_notifyContrastChanged = 1;

    public Stub() {
      attachInterface(this, IUiModeManagerCallback.DESCRIPTOR);
    }

    public static IUiModeManagerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IUiModeManagerCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IUiModeManagerCallback)) {
        return (IUiModeManagerCallback) iin;
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
          return "notifyContrastChanged";
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
        data.enforceInterface(IUiModeManagerCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IUiModeManagerCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              float _arg0 = data.readFloat();
              data.enforceNoDataAvail();
              notifyContrastChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUiModeManagerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IUiModeManagerCallback.DESCRIPTOR;
      }

      @Override // android.app.IUiModeManagerCallback
      public void notifyContrastChanged(float contrast) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUiModeManagerCallback.DESCRIPTOR);
          _data.writeFloat(contrast);
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
