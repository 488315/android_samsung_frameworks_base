package android.service.carrier;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApnSourceService extends IInterface {
  public static final String DESCRIPTOR = "android.service.carrier.IApnSourceService";

  ContentValues[] getApns(int i) throws RemoteException;

  public static class Default implements IApnSourceService {
    @Override // android.service.carrier.IApnSourceService
    public ContentValues[] getApns(int subId) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IApnSourceService {
    static final int TRANSACTION_getApns = 1;

    public Stub() {
      attachInterface(this, IApnSourceService.DESCRIPTOR);
    }

    public static IApnSourceService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IApnSourceService.DESCRIPTOR);
      if (iin != null && (iin instanceof IApnSourceService)) {
        return (IApnSourceService) iin;
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
          return "getApns";
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
        data.enforceInterface(IApnSourceService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IApnSourceService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              ContentValues[] _result = getApns(_arg0);
              reply.writeNoException();
              reply.writeTypedArray(_result, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IApnSourceService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IApnSourceService.DESCRIPTOR;
      }

      @Override // android.service.carrier.IApnSourceService
      public ContentValues[] getApns(int subId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IApnSourceService.DESCRIPTOR);
          _data.writeInt(subId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          ContentValues[] _result =
              (ContentValues[]) _reply.createTypedArray(ContentValues.CREATOR);
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
