package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IProcessObserver extends IInterface {
  void onForegroundActivitiesChanged(int i, int i2, boolean z) throws RemoteException;

  void onForegroundServicesChanged(int i, int i2, int i3) throws RemoteException;

  void onProcessDied(int i, int i2) throws RemoteException;

  public static class Default implements IProcessObserver {
    @Override // android.app.IProcessObserver
    public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities)
        throws RemoteException {}

    @Override // android.app.IProcessObserver
    public void onForegroundServicesChanged(int pid, int uid, int serviceTypes)
        throws RemoteException {}

    @Override // android.app.IProcessObserver
    public void onProcessDied(int pid, int uid) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IProcessObserver {
    public static final String DESCRIPTOR = "android.app.IProcessObserver";
    static final int TRANSACTION_onForegroundActivitiesChanged = 1;
    static final int TRANSACTION_onForegroundServicesChanged = 2;
    static final int TRANSACTION_onProcessDied = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IProcessObserver asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IProcessObserver)) {
        return (IProcessObserver) iin;
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
          return "onForegroundActivitiesChanged";
        case 2:
          return "onForegroundServicesChanged";
        case 3:
          return "onProcessDied";
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
              int _arg1 = data.readInt();
              boolean _arg2 = data.readBoolean();
              data.enforceNoDataAvail();
              onForegroundActivitiesChanged(_arg0, _arg1, _arg2);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              onForegroundServicesChanged(_arg02, _arg12, _arg22);
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              onProcessDied(_arg03, _arg13);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IProcessObserver {
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

      @Override // android.app.IProcessObserver
      public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(pid);
          _data.writeInt(uid);
          _data.writeBoolean(foregroundActivities);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IProcessObserver
      public void onForegroundServicesChanged(int pid, int uid, int serviceTypes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(pid);
          _data.writeInt(uid);
          _data.writeInt(serviceTypes);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IProcessObserver
      public void onProcessDied(int pid, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(pid);
          _data.writeInt(uid);
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
