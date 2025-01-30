package android.app.backup;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IBackupCallback extends IInterface {
  public static final String DESCRIPTOR = "android.app.backup.IBackupCallback";

  void operationComplete(long j) throws RemoteException;

  public static class Default implements IBackupCallback {
    @Override // android.app.backup.IBackupCallback
    public void operationComplete(long result) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBackupCallback {
    static final int TRANSACTION_operationComplete = 1;

    public Stub() {
      attachInterface(this, IBackupCallback.DESCRIPTOR);
    }

    public static IBackupCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IBackupCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IBackupCallback)) {
        return (IBackupCallback) iin;
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
          return "operationComplete";
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
        data.enforceInterface(IBackupCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IBackupCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              long _arg0 = data.readLong();
              data.enforceNoDataAvail();
              operationComplete(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBackupCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IBackupCallback.DESCRIPTOR;
      }

      @Override // android.app.backup.IBackupCallback
      public void operationComplete(long result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBackupCallback.DESCRIPTOR);
          _data.writeLong(result);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
