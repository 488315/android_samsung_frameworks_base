package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITetheringStatsProvider extends IInterface {
  public static final int QUOTA_UNLIMITED = -1;

  NetworkStats getTetherStats(int i) throws RemoteException;

  void setInterfaceQuota(String str, long j) throws RemoteException;

  public static class Default implements ITetheringStatsProvider {
    @Override // android.net.ITetheringStatsProvider
    public NetworkStats getTetherStats(int how) throws RemoteException {
      return null;
    }

    @Override // android.net.ITetheringStatsProvider
    public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITetheringStatsProvider {
    public static final String DESCRIPTOR = "android.net.ITetheringStatsProvider";
    static final int TRANSACTION_getTetherStats = 1;
    static final int TRANSACTION_setInterfaceQuota = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ITetheringStatsProvider asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ITetheringStatsProvider)) {
        return (ITetheringStatsProvider) iin;
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
          return "getTetherStats";
        case 2:
          return "setInterfaceQuota";
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
              data.enforceNoDataAvail();
              NetworkStats _result = getTetherStats(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              String _arg02 = data.readString();
              long _arg1 = data.readLong();
              data.enforceNoDataAvail();
              setInterfaceQuota(_arg02, _arg1);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITetheringStatsProvider {
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

      @Override // android.net.ITetheringStatsProvider
      public NetworkStats getTetherStats(int how) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(how);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          NetworkStats _result = (NetworkStats) _reply.readTypedObject(NetworkStats.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.net.ITetheringStatsProvider
      public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeLong(quotaBytes);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
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
