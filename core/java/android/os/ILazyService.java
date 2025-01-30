package android.os;

/* loaded from: classes3.dex */
public interface ILazyService extends IInterface {
  public static final String DESCRIPTOR = "android.os.ILazyService";

  IBinder getService(String str) throws RemoteException;

  public static class Default implements ILazyService {
    @Override // android.os.ILazyService
    public IBinder getService(String name) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ILazyService {
    static final int TRANSACTION_getService = 1;

    public Stub() {
      attachInterface(this, ILazyService.DESCRIPTOR);
    }

    public static ILazyService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ILazyService.DESCRIPTOR);
      if (iin != null && (iin instanceof ILazyService)) {
        return (ILazyService) iin;
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
          return "getService";
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
        data.enforceInterface(ILazyService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ILazyService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              IBinder _result = getService(_arg0);
              reply.writeNoException();
              reply.writeStrongBinder(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ILazyService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ILazyService.DESCRIPTOR;
      }

      @Override // android.os.ILazyService
      public IBinder getService(String name) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ILazyService.DESCRIPTOR);
          _data.writeString(name);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          IBinder _result = _reply.readStrongBinder();
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
