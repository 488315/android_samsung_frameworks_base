package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITvInputHardwareCallback extends IInterface {
  void onReleased() throws RemoteException;

  void onStreamConfigChanged(TvStreamConfig[] tvStreamConfigArr) throws RemoteException;

  public static class Default implements ITvInputHardwareCallback {
    @Override // android.media.tv.ITvInputHardwareCallback
    public void onReleased() throws RemoteException {}

    @Override // android.media.tv.ITvInputHardwareCallback
    public void onStreamConfigChanged(TvStreamConfig[] configs) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITvInputHardwareCallback {
    public static final String DESCRIPTOR = "android.media.tv.ITvInputHardwareCallback";
    static final int TRANSACTION_onReleased = 1;
    static final int TRANSACTION_onStreamConfigChanged = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ITvInputHardwareCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ITvInputHardwareCallback)) {
        return (ITvInputHardwareCallback) iin;
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
          return "onReleased";
        case 2:
          return "onStreamConfigChanged";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onReleased();
              return true;
            case 2:
              TvStreamConfig[] _arg0 =
                  (TvStreamConfig[]) data.createTypedArray(TvStreamConfig.CREATOR);
              data.enforceNoDataAvail();
              onStreamConfigChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITvInputHardwareCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.media.tv.ITvInputHardwareCallback
      public void onReleased() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.ITvInputHardwareCallback
      public void onStreamConfigChanged(TvStreamConfig[] configs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedArray(configs, 0);
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
