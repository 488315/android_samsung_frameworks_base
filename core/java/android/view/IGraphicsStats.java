package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IGraphicsStats extends IInterface {
  ParcelFileDescriptor requestBufferForProcess(
      String str, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException;

  public static class Default implements IGraphicsStats {
    @Override // android.view.IGraphicsStats
    public ParcelFileDescriptor requestBufferForProcess(
        String packageName, IGraphicsStatsCallback callback) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGraphicsStats {
    public static final String DESCRIPTOR = "android.view.IGraphicsStats";
    static final int TRANSACTION_requestBufferForProcess = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IGraphicsStats asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGraphicsStats)) {
        return (IGraphicsStats) iin;
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
          return "requestBufferForProcess";
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
              String _arg0 = data.readString();
              IGraphicsStatsCallback _arg1 =
                  IGraphicsStatsCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result = requestBufferForProcess(_arg0, _arg1);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGraphicsStats {
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

      @Override // android.view.IGraphicsStats
      public ParcelFileDescriptor requestBufferForProcess(
          String packageName, IGraphicsStatsCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
