package android.hardware.p006tv.tuner;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IFilterCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$tv$tuner$IFilterCallback".replace('$', '.');
  public static final String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
  public static final int VERSION = 2;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void onFilterEvent(DemuxFilterEvent[] demuxFilterEventArr) throws RemoteException;

  void onFilterStatus(byte b) throws RemoteException;

  public static class Default implements IFilterCallback {
    @Override // android.hardware.p006tv.tuner.IFilterCallback
    public void onFilterEvent(DemuxFilterEvent[] events) throws RemoteException {}

    @Override // android.hardware.p006tv.tuner.IFilterCallback
    public void onFilterStatus(byte status) throws RemoteException {}

    @Override // android.hardware.p006tv.tuner.IFilterCallback
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.hardware.p006tv.tuner.IFilterCallback
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IFilterCallback {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_onFilterEvent = 1;
    static final int TRANSACTION_onFilterStatus = 2;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static IFilterCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IFilterCallback)) {
        return (IFilterCallback) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
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
              DemuxFilterEvent[] _arg0 =
                  (DemuxFilterEvent[]) data.createTypedArray(DemuxFilterEvent.CREATOR);
              data.enforceNoDataAvail();
              onFilterEvent(_arg0);
              return true;
            case 2:
              byte _arg02 = data.readByte();
              data.enforceNoDataAvail();
              onFilterStatus(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IFilterCallback {
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

      @Override // android.hardware.p006tv.tuner.IFilterCallback
      public void onFilterEvent(DemuxFilterEvent[] events) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedArray(events, 0);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method onFilterEvent is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.p006tv.tuner.IFilterCallback
      public void onFilterStatus(byte status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByte(status);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method onFilterStatus is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.p006tv.tuner.IFilterCallback
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

      @Override // android.hardware.p006tv.tuner.IFilterCallback
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
  }
}
