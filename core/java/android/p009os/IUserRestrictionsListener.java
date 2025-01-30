package android.p009os;

/* loaded from: classes3.dex */
public interface IUserRestrictionsListener extends IInterface {
  public static final String DESCRIPTOR = "android.os.IUserRestrictionsListener";

  void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

  public static class Default implements IUserRestrictionsListener {
    @Override // android.p009os.IUserRestrictionsListener
    public void onUserRestrictionsChanged(
        int userId, Bundle newRestrictions, Bundle prevRestrictions) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUserRestrictionsListener {
    static final int TRANSACTION_onUserRestrictionsChanged = 1;

    public Stub() {
      attachInterface(this, IUserRestrictionsListener.DESCRIPTOR);
    }

    public static IUserRestrictionsListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IUserRestrictionsListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IUserRestrictionsListener)) {
        return (IUserRestrictionsListener) iin;
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
          return "onUserRestrictionsChanged";
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
        data.enforceInterface(IUserRestrictionsListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IUserRestrictionsListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onUserRestrictionsChanged(_arg0, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUserRestrictionsListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IUserRestrictionsListener.DESCRIPTOR;
      }

      @Override // android.p009os.IUserRestrictionsListener
      public void onUserRestrictionsChanged(
          int userId, Bundle newRestrictions, Bundle prevRestrictions) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUserRestrictionsListener.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(newRestrictions, 0);
          _data.writeTypedObject(prevRestrictions, 0);
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
