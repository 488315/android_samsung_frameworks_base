package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITvInputServiceCallback extends IInterface {
  void addHardwareInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

  void addHdmiInput(int i, TvInputInfo tvInputInfo) throws RemoteException;

  void removeHardwareInput(String str) throws RemoteException;

  public static class Default implements ITvInputServiceCallback {
    @Override // android.media.tv.ITvInputServiceCallback
    public void addHardwareInput(int deviceId, TvInputInfo inputInfo) throws RemoteException {}

    @Override // android.media.tv.ITvInputServiceCallback
    public void addHdmiInput(int id, TvInputInfo inputInfo) throws RemoteException {}

    @Override // android.media.tv.ITvInputServiceCallback
    public void removeHardwareInput(String inputId) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITvInputServiceCallback {
    public static final String DESCRIPTOR = "android.media.tv.ITvInputServiceCallback";
    static final int TRANSACTION_addHardwareInput = 1;
    static final int TRANSACTION_addHdmiInput = 2;
    static final int TRANSACTION_removeHardwareInput = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ITvInputServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ITvInputServiceCallback)) {
        return (ITvInputServiceCallback) iin;
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
          return "addHardwareInput";
        case 2:
          return "addHdmiInput";
        case 3:
          return "removeHardwareInput";
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
              int _arg0 = data.readInt();
              TvInputInfo _arg1 = (TvInputInfo) data.readTypedObject(TvInputInfo.CREATOR);
              data.enforceNoDataAvail();
              addHardwareInput(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              TvInputInfo _arg12 = (TvInputInfo) data.readTypedObject(TvInputInfo.CREATOR);
              data.enforceNoDataAvail();
              addHdmiInput(_arg02, _arg12);
              return true;
            case 3:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              removeHardwareInput(_arg03);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITvInputServiceCallback {
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

      @Override // android.media.tv.ITvInputServiceCallback
      public void addHardwareInput(int deviceId, TvInputInfo inputInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeTypedObject(inputInfo, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.ITvInputServiceCallback
      public void addHdmiInput(int id, TvInputInfo inputInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(id);
          _data.writeTypedObject(inputInfo, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.tv.ITvInputServiceCallback
      public void removeHardwareInput(String inputId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputId);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
