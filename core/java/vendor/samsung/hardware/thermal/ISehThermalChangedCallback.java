package vendor.samsung.hardware.thermal;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehThermalChangedCallback extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$thermal$ISehThermalChangedCallback".replace('$', '.');
  public static final String HASH = "179442d052a6252d8efb5d74ab634019e2e640bb";
  public static final int VERSION = 1;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void notifyTemperatures(SehTemperature[] sehTemperatureArr) throws RemoteException;

  public static class Default implements ISehThermalChangedCallback {
    @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
    public void notifyTemperatures(SehTemperature[] temperatures) throws RemoteException {}

    @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehThermalChangedCallback {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_notifyTemperatures = 1;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehThermalChangedCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehThermalChangedCallback)) {
        return (ISehThermalChangedCallback) iin;
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
          return "notifyTemperatures";
        case 16777214:
          return "getInterfaceHash";
        case 16777215:
          return "getInterfaceVersion";
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
      String descriptor = DESCRIPTOR;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(descriptor);
      }
      switch (code) {
        case 16777214:
          reply.writeNoException();
          reply.writeString(getInterfaceHash());
          return true;
        case 16777215:
          reply.writeNoException();
          reply.writeInt(getInterfaceVersion());
          return true;
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          return true;
        default:
          switch (code) {
            case 1:
              SehTemperature[] _arg0 =
                  (SehTemperature[]) data.createTypedArray(SehTemperature.CREATOR);
              data.enforceNoDataAvail();
              notifyTemperatures(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehThermalChangedCallback {
      private IBinder mRemote;
      private int mCachedVersion = -1;
      private String mCachedHash = "-1";

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
      public void notifyTemperatures(SehTemperature[] temperatures) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedArray(temperatures, 0);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method notifyTemperatures is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
      public int getInterfaceVersion() throws RemoteException {
        if (this.mCachedVersion == -1) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(DESCRIPTOR);
            this.mRemote.transact(16777215, data, reply, 0);
            reply.readException();
            this.mCachedVersion = reply.readInt();
          } finally {
            reply.recycle();
            data.recycle();
          }
        }
        return this.mCachedVersion;
      }

      @Override // vendor.samsung.hardware.thermal.ISehThermalChangedCallback
      public synchronized String getInterfaceHash() throws RemoteException {
        if ("-1".equals(this.mCachedHash)) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(DESCRIPTOR);
            this.mRemote.transact(16777214, data, reply, 0);
            reply.readException();
            this.mCachedHash = reply.readString();
            reply.recycle();
            data.recycle();
          } catch (Throwable th) {
            reply.recycle();
            data.recycle();
            throw th;
          }
        }
        return this.mCachedHash;
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 16777214;
    }
  }
}
