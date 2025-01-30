package android.hardware.fingerprint;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.ThreadedRenderer;

/* loaded from: classes2.dex */
public interface ISidefpsController extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.fingerprint.ISidefpsController";

  void hide(int i) throws RemoteException;

  void show(int i, int i2) throws RemoteException;

  public static class Default implements ISidefpsController {
    @Override // android.hardware.fingerprint.ISidefpsController
    public void show(int sensorId, int reason) throws RemoteException {}

    @Override // android.hardware.fingerprint.ISidefpsController
    public void hide(int sensorId) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISidefpsController {
    static final int TRANSACTION_hide = 2;
    static final int TRANSACTION_show = 1;

    public Stub() {
      attachInterface(this, ISidefpsController.DESCRIPTOR);
    }

    public static ISidefpsController asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISidefpsController.DESCRIPTOR);
      if (iin != null && (iin instanceof ISidefpsController)) {
        return (ISidefpsController) iin;
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
          return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
        case 2:
          return "hide";
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
        data.enforceInterface(ISidefpsController.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISidefpsController.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              show(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              hide(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISidefpsController {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISidefpsController.DESCRIPTOR;
      }

      @Override // android.hardware.fingerprint.ISidefpsController
      public void show(int sensorId, int reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISidefpsController.DESCRIPTOR);
          _data.writeInt(sensorId);
          _data.writeInt(reason);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.fingerprint.ISidefpsController
      public void hide(int sensorId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISidefpsController.DESCRIPTOR);
          _data.writeInt(sensorId);
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
