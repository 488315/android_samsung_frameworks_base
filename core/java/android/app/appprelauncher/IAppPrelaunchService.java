package android.app.appprelauncher;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IAppPrelaunchService extends IInterface {
  public static final String DESCRIPTOR = "android.app.appprelauncher.IAppPrelaunchService";

  boolean isAppPrelaunched(int i) throws RemoteException;

  boolean killApp(int i, String str) throws RemoteException;

  boolean prelaunchApp(String str, int i) throws RemoteException;

  boolean prelaunchAppTillStage(String str, int i, int i2) throws RemoteException;

  public static class Default implements IAppPrelaunchService {
    @Override // android.app.appprelauncher.IAppPrelaunchService
    public boolean prelaunchApp(String packageName, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.appprelauncher.IAppPrelaunchService
    public boolean prelaunchAppTillStage(String packageName, int uid, int stage)
        throws RemoteException {
      return false;
    }

    @Override // android.app.appprelauncher.IAppPrelaunchService
    public boolean isAppPrelaunched(int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.appprelauncher.IAppPrelaunchService
    public boolean killApp(int uid, String reason) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAppPrelaunchService {
    static final int TRANSACTION_isAppPrelaunched = 3;
    static final int TRANSACTION_killApp = 4;
    static final int TRANSACTION_prelaunchApp = 1;
    static final int TRANSACTION_prelaunchAppTillStage = 2;

    public Stub() {
      attachInterface(this, IAppPrelaunchService.DESCRIPTOR);
    }

    public static IAppPrelaunchService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IAppPrelaunchService.DESCRIPTOR);
      if (iin != null && (iin instanceof IAppPrelaunchService)) {
        return (IAppPrelaunchService) iin;
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
          return "prelaunchApp";
        case 2:
          return "prelaunchAppTillStage";
        case 3:
          return "isAppPrelaunched";
        case 4:
          return "killApp";
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
        data.enforceInterface(IAppPrelaunchService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IAppPrelaunchService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = prelaunchApp(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = prelaunchAppTillStage(_arg02, _arg12, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = isAppPrelaunched(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              int _arg04 = data.readInt();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 = killApp(_arg04, _arg13);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAppPrelaunchService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IAppPrelaunchService.DESCRIPTOR;
      }

      @Override // android.app.appprelauncher.IAppPrelaunchService
      public boolean prelaunchApp(String packageName, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAppPrelaunchService.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(uid);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.appprelauncher.IAppPrelaunchService
      public boolean prelaunchAppTillStage(String packageName, int uid, int stage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAppPrelaunchService.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(uid);
          _data.writeInt(stage);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.appprelauncher.IAppPrelaunchService
      public boolean isAppPrelaunched(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAppPrelaunchService.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.appprelauncher.IAppPrelaunchService
      public boolean killApp(int uid, String reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAppPrelaunchService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(reason);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
