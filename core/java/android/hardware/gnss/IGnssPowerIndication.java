package android.hardware.gnss;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssPowerIndication extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$gnss$IGnssPowerIndication".replace('$', '.');
  public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
  public static final int VERSION = 2;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void requestGnssPowerStats() throws RemoteException;

  void setCallback(IGnssPowerIndicationCallback iGnssPowerIndicationCallback)
      throws RemoteException;

  public static class Default implements IGnssPowerIndication {
    @Override // android.hardware.gnss.IGnssPowerIndication
    public void setCallback(IGnssPowerIndicationCallback callback) throws RemoteException {}

    @Override // android.hardware.gnss.IGnssPowerIndication
    public void requestGnssPowerStats() throws RemoteException {}

    @Override // android.hardware.gnss.IGnssPowerIndication
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.hardware.gnss.IGnssPowerIndication
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGnssPowerIndication {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_requestGnssPowerStats = 2;
    static final int TRANSACTION_setCallback = 1;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static IGnssPowerIndication asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGnssPowerIndication)) {
        return (IGnssPowerIndication) iin;
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
          return "setCallback";
        case 2:
          return "requestGnssPowerStats";
        case 16777214:
          return "getInterfaceHash";
        case 16777215:
          return "getInterfaceVersion";
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
              IGnssPowerIndicationCallback _arg0 =
                  IGnssPowerIndicationCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setCallback(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              requestGnssPowerStats();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGnssPowerIndication {
      private IBinder mRemote;
      private int mCachedVersion = -1;
      private String mCachedHash = "-1";

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.hardware.gnss.IGnssPowerIndication
      public void setCallback(IGnssPowerIndicationCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = this.mRemote.transact(1, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method setCallback is unimplemented.");
          }
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.gnss.IGnssPowerIndication
      public void requestGnssPowerStats() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method requestGnssPowerStats is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.gnss.IGnssPowerIndication
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

      @Override // android.hardware.gnss.IGnssPowerIndication
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

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 16777214;
    }
  }
}
