package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.SemRILConstants;

/* loaded from: classes3.dex */
public interface IDataShareCallback extends IInterface {
  public static final String DESCRIPTOR = "android.service.contentcapture.IDataShareCallback";

  void accept(IDataShareReadAdapter iDataShareReadAdapter) throws RemoteException;

  void reject() throws RemoteException;

  public static class Default implements IDataShareCallback {
    @Override // android.service.contentcapture.IDataShareCallback
    public void accept(IDataShareReadAdapter adapter) throws RemoteException {}

    @Override // android.service.contentcapture.IDataShareCallback
    public void reject() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDataShareCallback {
    static final int TRANSACTION_accept = 1;
    static final int TRANSACTION_reject = 2;

    public Stub() {
      attachInterface(this, IDataShareCallback.DESCRIPTOR);
    }

    public static IDataShareCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDataShareCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IDataShareCallback)) {
        return (IDataShareCallback) iin;
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
          return "accept";
        case 2:
          return SemRILConstants.CmcCall.CMC_CALL_SD_REJECT;
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
        data.enforceInterface(IDataShareCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDataShareCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IDataShareReadAdapter _arg0 =
                  IDataShareReadAdapter.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              accept(_arg0);
              return true;
            case 2:
              reject();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDataShareCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDataShareCallback.DESCRIPTOR;
      }

      @Override // android.service.contentcapture.IDataShareCallback
      public void accept(IDataShareReadAdapter adapter) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDataShareCallback.DESCRIPTOR);
          _data.writeStrongInterface(adapter);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.contentcapture.IDataShareCallback
      public void reject() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDataShareCallback.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
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
