package android.p009os;

/* loaded from: classes3.dex */
public interface IWakeLockCallback extends IInterface {
  public static final String DESCRIPTOR = "android.os.IWakeLockCallback";

  void onStateChanged(boolean z) throws RemoteException;

  public static class Default implements IWakeLockCallback {
    @Override // android.p009os.IWakeLockCallback
    public void onStateChanged(boolean enabled) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IWakeLockCallback {
    static final int TRANSACTION_onStateChanged = 1;

    public Stub() {
      attachInterface(this, IWakeLockCallback.DESCRIPTOR);
    }

    public static IWakeLockCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IWakeLockCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IWakeLockCallback)) {
        return (IWakeLockCallback) iin;
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
          return "onStateChanged";
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
        data.enforceInterface(IWakeLockCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IWakeLockCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onStateChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IWakeLockCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IWakeLockCallback.DESCRIPTOR;
      }

      @Override // android.p009os.IWakeLockCallback
      public void onStateChanged(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IWakeLockCallback.DESCRIPTOR);
          _data.writeBoolean(enabled);
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
