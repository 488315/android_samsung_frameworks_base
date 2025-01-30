package android.p009os.logcat;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ILogcatManagerService extends IInterface {
  public static final String DESCRIPTOR = "android.os.logcat.ILogcatManagerService";

  void finishThread(int i, int i2, int i3, int i4) throws RemoteException;

  void startThread(int i, int i2, int i3, int i4) throws RemoteException;

  public static class Default implements ILogcatManagerService {
    @Override // android.p009os.logcat.ILogcatManagerService
    public void startThread(int uid, int gid, int pid, int fd) throws RemoteException {}

    @Override // android.p009os.logcat.ILogcatManagerService
    public void finishThread(int uid, int gid, int pid, int fd) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ILogcatManagerService {
    static final int TRANSACTION_finishThread = 2;
    static final int TRANSACTION_startThread = 1;

    public Stub() {
      attachInterface(this, ILogcatManagerService.DESCRIPTOR);
    }

    public static ILogcatManagerService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ILogcatManagerService.DESCRIPTOR);
      if (iin != null && (iin instanceof ILogcatManagerService)) {
        return (ILogcatManagerService) iin;
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
          return "startThread";
        case 2:
          return "finishThread";
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
        data.enforceInterface(ILogcatManagerService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ILogcatManagerService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              startThread(_arg0, _arg1, _arg2, _arg3);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              int _arg22 = data.readInt();
              int _arg32 = data.readInt();
              data.enforceNoDataAvail();
              finishThread(_arg02, _arg12, _arg22, _arg32);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ILogcatManagerService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ILogcatManagerService.DESCRIPTOR;
      }

      @Override // android.p009os.logcat.ILogcatManagerService
      public void startThread(int uid, int gid, int pid, int fd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ILogcatManagerService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(gid);
          _data.writeInt(pid);
          _data.writeInt(fd);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.p009os.logcat.ILogcatManagerService
      public void finishThread(int uid, int gid, int pid, int fd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ILogcatManagerService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(gid);
          _data.writeInt(pid);
          _data.writeInt(fd);
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
